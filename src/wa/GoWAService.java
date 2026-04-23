package wa;

import fungsi.PdfProtectorBox;
import fungsi.koneksiDBWa;
import fungsi.sekuel;
import java.awt.Font;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class GoWAService {

    private static final sekuel Sequel = new sekuel();
    private static final int CONNECT_TIMEOUT = 7000;
    private static final int READ_TIMEOUT = 20000;

    private static String deviceId() {
        try {
            String id = koneksiDBWa.GOWA_DEVICE_ID();
            return (id == null || id.trim().isEmpty()) ? "default" : id.trim();
        } catch (Exception e) {
            return "default";
        }
    }

    // ================= HANDLE NO HP (SATU PINTU) =================
    private static String prepareNoHP(String noRM, String inputNoHP) {
    try {

        // ================= SET FONT GLOBAL =================
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("Button.font", font);

        // ================= AMBIL DATA =================
        String noHP = (inputNoHP == null || inputNoHP.trim().isEmpty())
                ? Sequel.cariIsi(
                        "SELECT IFNULL(no_tlp,'') FROM pasien WHERE no_rkm_medis=?",
                        noRM
                )
                : inputNoHP;

        // ================= KOMPONEN =================
        JTextField fieldNoHp = new JTextField(noHP, 20);
        JCheckBox checkUpdate = new JCheckBox("Update No.HP");

        JLabel lblRM = new JLabel("No. RM : " + noRM);
        JLabel lblHP = new JLabel("Nomor WhatsApp:");

        // ================= PANEL =================
        JPanel panel = new JPanel(new java.awt.GridLayout(0, 1, 5, 5));
        panel.add(lblRM);
        panel.add(lblHP);
        panel.add(fieldNoHp);
        panel.add(checkUpdate);

        // ================= DIALOG =================
        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Konfirmasi Pengiriman WhatsApp",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        noHP = fieldNoHp.getText().trim();

        if (noHP.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nomor HP tidak boleh kosong!");
            return null;
        }

        // ================= UPDATE =================
        if (checkUpdate.isSelected()) {
            Sequel.queryu(
                    "UPDATE pasien SET no_tlp='" + noHP + "' WHERE no_rkm_medis='" + noRM + "'"
            );
        }

        return noHP;

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    // ================= CORE =================
    private static boolean kirimCore(
            String namaFileReport,
            String jenisDokumen,
            String noRM,
            String noHP,
            boolean usePassword,
            String pesan
    ) {

        try {

            // 🔥 SATU PINTU NO HP
            noHP = prepareNoHP(noRM, noHP);
            if (noHP == null) {
                return false;
            }

            if (!isReady(noHP)) {
                return false;
            }

            File original = getReportFile(namaFileReport);
            if (original == null) {
                return false;
            }

            File fileToSend = original;

            // ================= PASSWORD =================
            if (usePassword) {
                String tglLahir = Sequel.cariIsi(
                        "select tgl_lahir from pasien where no_rkm_medis=?",
                        noRM
                );

                String password = generatePasswordFromBirthDate(tglLahir);

                if (password != null) {
                    fileToSend = protectPdf(original, password);
                }
            }

            boolean sukses = kirimFile(fileToSend, noHP, pesan);

            if (usePassword) {
                cleanupTmp();
            }

            return sukses;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= PUBLIC =================
    public static boolean kirimDariNoRM(
            String namaFileReport,
            String jenisDokumen,
            String noRM,
            String noHP,
            boolean usePassword,
            String pesan
    ) {
        return kirimCore(namaFileReport, jenisDokumen, noRM, noHP, usePassword, pesan);
    }

    public static boolean kirimDariNoRawat(
            String namaFileReport,
            String jenisDokumen,
            String noRawat,
            String noHP,
            boolean usePassword,
            String pesan
    ) {

        String noRM = Sequel.cariIsi(
                "select no_rkm_medis from reg_periksa where no_rawat=?",
                noRawat
        );

        if (noRM == null || noRM.trim().isEmpty()) {
            System.out.println("No RM tidak ditemukan");
            return false;
        }

        return kirimCore(namaFileReport, jenisDokumen, noRM, noHP, usePassword, pesan);
    }

    // ================= KIRIM =================
    private static boolean kirimFile(File file, String noHP, String caption) {
        try {

            String boundary = "----SIMRS-" + System.currentTimeMillis();
            String phone = normalizePhone(noHP) + "@s.whatsapp.net";

            URL url = new URL(
                    koneksiDBWa.GOWA_BASE_URL()
                    + "/send/file?device_id=" + deviceId()
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Authorization", basicAuth());
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (
                    OutputStream output = conn.getOutputStream(); PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(output, StandardCharsets.UTF_8), true
            )) {

                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"phone\"\r\n\r\n");
                writer.append(phone).append("\r\n");

                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"caption\"\r\n\r\n");
                writer.append(caption).append("\r\n");

                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                        .append(file.getName()).append("\"\r\n");
                writer.append("Content-Type: application/pdf\r\n\r\n");
                writer.flush();

                Files.copy(file.toPath(), output);
                output.flush();

                writer.append("\r\n--").append(boundary).append("--\r\n");
                writer.flush();
            }

            int code = conn.getResponseCode();
            System.out.println("WA RESPONSE CODE : " + code);

            return code >= 200 && code < 300;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= UTIL =================
    private static String normalizePhone(String raw) {
        String d = raw.replaceAll("[^0-9]", "");

        if (d.startsWith("0")) {
            d = "62" + d.substring(1);
        }

        if (!d.startsWith("62")) {
            d = "62" + d;
        }

        return d;
    }

    private static String basicAuth() {
        String auth = koneksiDBWa.GOWA_USERNAME()
                + ":" + koneksiDBWa.GOWA_PASSWORD();

        return "Basic " + Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    private static File getReportFile(String namaFile) {
        File file = new File("report/" + namaFile);
        if (!file.exists() || file.length() == 0) {
            System.out.println("FILE REPORT TIDAK ADA / KOSONG");
            return null;
        }
        return file;
    }

    private static boolean isReady(String noHP) {
        if (koneksiDBWa.GOWA_BASE_URL().isEmpty()
                || koneksiDBWa.GOWA_USERNAME().isEmpty()
                || koneksiDBWa.GOWA_PASSWORD().isEmpty()) {
            System.out.println("CONFIG GOWA BELUM LENGKAP");
            return false;
        }

        if (noHP == null || noHP.trim().isEmpty()) {
            System.out.println("Nomor HP kosong");
            return false;
        }

        return true;
    }

    private static String generatePasswordFromBirthDate(String tgl) {
        try {
            if (tgl == null || tgl.trim().isEmpty()) {
                return null;
            }

            String clean = tgl.length() >= 10 ? tgl.substring(0, 10) : tgl;

            DateTimeFormatter[] formats = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
            };

            for (DateTimeFormatter f : formats) {
                try {
                    LocalDate d = LocalDate.parse(clean, f);
                    return d.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
                } catch (Exception ignored) {
                }
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    private static File protectPdf(File source, String password) {
        try {
            File folder = new File("tmpPDF");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File output = new File(folder, "protected_" + source.getName());

            PdfProtectorBox.encrypt(source, output, password, password, 128);

            return output;

        } catch (Exception e) {
            return source;
        }
    }

    private static void cleanupTmp() {
        File folder = new File("tmpPDF");
        if (!folder.exists()) {
            return;
        }

        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }

        for (File f : files) {
            if (f.getName().startsWith("protected_")) {
                f.delete();
            }
        }
    }
    // =====================================================
    // PUBLIC METHODS BARU
    // =====================================================
    public static boolean kirimGambar(String noHP, File file, String caption) {
        return kirimFile(file, noHP, caption);
    }
    
    public static boolean kirimPDF(String noHP, File file, String caption) {
        return kirimFile(file, noHP, caption);
    }
}
