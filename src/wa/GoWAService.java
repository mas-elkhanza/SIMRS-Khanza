package wa;

import fungsi.PdfProtectorBox;
import fungsi.koneksiDBWa;
import fungsi.sekuel;

import java.awt.Font;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Base64;

public class GoWAService {

    private static final sekuel Sequel = new sekuel();

    private static final int CONNECT_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 180000;

    private static String noHPTerakhir = "";

    private static String deviceId() {
        try {
            String id = koneksiDBWa.GOWA_DEVICE_ID();
            return (id == null || id.trim().isEmpty()) ? "default" : id.trim();
        } catch (Exception e) {
            return "default";
        }
    }

    private static String amanSQL(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("'", "''");
    }

    // =====================================================
    // HANDLE NO HP UNTUK REPORT BERDASARKAN NO RM
    // =====================================================
    private static String prepareNoHP(String noRM, String inputNoHP) {
        try {
            Font font = new Font("Tahoma", Font.PLAIN, 12);
            UIManager.put("OptionPane.messageFont", font);
            UIManager.put("OptionPane.buttonFont", font);
            UIManager.put("Label.font", font);
            UIManager.put("TextField.font", font);
            UIManager.put("CheckBox.font", font);
            UIManager.put("Button.font", font);

            String noHP = (inputNoHP == null || inputNoHP.trim().isEmpty())
                    ? Sequel.cariIsi(
                            "SELECT IFNULL(no_tlp,'') FROM pasien WHERE no_rkm_medis=?",
                            noRM
                    )
                    : inputNoHP;

            JTextField fieldNoHp = new JTextField(noHP, 20);
            JCheckBox checkUpdate = new JCheckBox("Update No.HP Pasien");

            JLabel lblRM = new JLabel("No. RM : " + noRM);
            JLabel lblHP = new JLabel("Nomor WhatsApp:");

            JPanel panel = new JPanel(new java.awt.GridLayout(0, 1, 5, 5));
            panel.add(lblRM);
            panel.add(lblHP);
            panel.add(fieldNoHp);
            panel.add(checkUpdate);

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

            if (checkUpdate.isSelected()) {
                Sequel.queryu(
                        "UPDATE pasien SET no_tlp='" + amanSQL(noHP) + "' "
                        + "WHERE no_rkm_medis='" + amanSQL(noRM) + "'"
                );
            }

            return noHP;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // =====================================================
    // HANDLE NO HP UNTUK GAMBAR / PDF MANUAL
    // SUPPORT UPDATE PASIEN DAN PJ
    // =====================================================
    private static String prepareNoHPManual(String inputNoHP, String noRM, String kdPj) {
        try {
            if (inputNoHP != null && !inputNoHP.trim().isEmpty()) {
                noHPTerakhir = inputNoHP.trim();
                return noHPTerakhir;
            }

            if (noHPTerakhir != null && !noHPTerakhir.trim().isEmpty()) {
                return noHPTerakhir;
            }

            Font font = new Font("Tahoma", Font.PLAIN, 12);
            UIManager.put("OptionPane.messageFont", font);
            UIManager.put("OptionPane.buttonFont", font);
            UIManager.put("Label.font", font);
            UIManager.put("TextField.font", font);
            UIManager.put("CheckBox.font", font);
            UIManager.put("Button.font", font);

            String noHPPasien = "";
            String noHPPJ = "";
            String namaPasien = "";
            String namaPJ = "";

            if (noRM != null && !noRM.trim().isEmpty()) {
                noHPPasien = Sequel.cariIsi(
                        "SELECT IFNULL(no_tlp,'') FROM pasien WHERE no_rkm_medis=?",
                        noRM
                );

                namaPasien = Sequel.cariIsi(
                        "SELECT IFNULL(nm_pasien,'') FROM pasien WHERE no_rkm_medis=?",
                        noRM
                );
            }

            if (kdPj != null && !kdPj.trim().isEmpty()) {
                noHPPJ = Sequel.cariIsi(
                        "SELECT IFNULL(no_telp,'') FROM penjab WHERE kd_pj=?",
                        kdPj
                );

                namaPJ = Sequel.cariIsi(
                        "SELECT IFNULL(png_jawab,'') FROM penjab WHERE kd_pj=?",
                        kdPj
                );
            }

            String defaultNoHP = "";

            if (noHPPasien != null && !noHPPasien.trim().isEmpty()) {
                defaultNoHP = noHPPasien.trim();
            } else if (noHPPJ != null && !noHPPJ.trim().isEmpty()) {
                defaultNoHP = noHPPJ.trim();
            }

            JTextField fieldNoHp = new JTextField(defaultNoHP, 20);

            JCheckBox checkUpdatePasien = new JCheckBox("Update nomor pasien");
            JCheckBox checkUpdatePJ = new JCheckBox("Update nomor PJ / perusahaan");

            checkUpdatePasien.setEnabled(noRM != null && !noRM.trim().isEmpty());
            checkUpdatePJ.setEnabled(kdPj != null && !kdPj.trim().isEmpty());

            JPanel panel = new JPanel(new java.awt.GridLayout(0, 1, 5, 5));

            if (noRM != null && !noRM.trim().isEmpty()) {
                panel.add(new JLabel("No. RM : " + noRM));
                panel.add(new JLabel("Pasien : " + namaPasien));
            }

            if (kdPj != null && !kdPj.trim().isEmpty()) {
                panel.add(new JLabel("Kode PJ : " + kdPj));
                panel.add(new JLabel("PJ / Perusahaan : " + namaPJ));
            }

            panel.add(new JLabel("Nomor WhatsApp tujuan:"));
            panel.add(fieldNoHp);
            panel.add(checkUpdatePasien);
            panel.add(checkUpdatePJ);

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

            String noHP = fieldNoHp.getText().trim();

            if (noHP.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nomor HP tidak boleh kosong!");
                return null;
            }

            if (checkUpdatePasien.isSelected()) {
                Sequel.queryu(
                        "UPDATE pasien SET no_tlp='" + amanSQL(noHP) + "' "
                        + "WHERE no_rkm_medis='" + amanSQL(noRM) + "'"
                );
            }

            if (checkUpdatePJ.isSelected()) {
                Sequel.queryu(
                        "UPDATE penjab SET no_telp='" + amanSQL(noHP) + "' "
                        + "WHERE kd_pj='" + amanSQL(kdPj) + "'"
                );
            }

            noHPTerakhir = noHP;
            return noHPTerakhir;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resetNoHPTerakhir() {
        noHPTerakhir = "";
    }

    // =====================================================
    // CORE REPORT
    // =====================================================
    private static boolean kirimCore(
            String namaFileReport,
            String jenisDokumen,
            String noRM,
            String noHP,
            boolean usePassword,
            String pesan
    ) {
        try {
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

    // =====================================================
    // PUBLIC REPORT
    // =====================================================
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

    // =====================================================
    // PUBLIC GAMBAR / PDF
    // =====================================================
    public static boolean kirimGambar(String noHP, File file, String caption) {
        noHP = prepareNoHPManual(noHP, "", "");

        if (noHP == null || noHP.trim().isEmpty()) {
            return false;
        }

        return kirimFile(file, noHP, caption);
    }

    public static boolean kirimGambar(String noHP, File file, String caption, String noRM, String kdPj) {
        noHP = prepareNoHPManual(noHP, noRM, kdPj);

        if (noHP == null || noHP.trim().isEmpty()) {
            return false;
        }

        return kirimFile(file, noHP, caption);
    }

    public static boolean kirimPDF(String noHP, File file, String caption) {
        noHP = prepareNoHPManual(noHP, "", "");

        if (noHP == null || noHP.trim().isEmpty()) {
            return false;
        }

        return kirimFile(file, noHP, caption);
    }

    public static boolean kirimPDF(String noHP, File file, String caption, String noRM, String kdPj) {
        noHP = prepareNoHPManual(noHP, noRM, kdPj);

        if (noHP == null || noHP.trim().isEmpty()) {
            return false;
        }

        return kirimFile(file, noHP, caption);
    }

    // =====================================================
    // KIRIM FILE KE GOWA
    // =====================================================
    private static boolean kirimFile(File file, String noHP, String caption) {
        HttpURLConnection conn = null;

        try {
            if (file == null || !file.exists() || file.length() == 0) {
                System.out.println("File tidak ada atau kosong");
                return false;
            }

            if (!isReady(noHP)) {
                return false;
            }

            String boundary = "----SIMRS-" + System.currentTimeMillis();
            String phone = normalizePhone(noHP) + "@s.whatsapp.net";

            String baseUrl = koneksiDBWa.GOWA_BASE_URL();

            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }

            URL url = new URL(
                    baseUrl + "/send/file?device_id=" + deviceId()
            );

            System.out.println("GOWA URL        : " + url);
            System.out.println("GOWA PHONE      : " + phone);
            System.out.println("GOWA FILE       : " + file.getAbsolutePath());
            System.out.println("GOWA FILE SIZE  : " + file.length());
            System.out.println("GOWA MIME       : " + getMimeType(file));

            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            conn.setRequestProperty("Authorization", basicAuth());
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Accept", "application/json, text/plain, */*");

            try (
                    OutputStream output = conn.getOutputStream(); PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(output, StandardCharsets.UTF_8),
                    true
            )) {
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"phone\"\r\n\r\n");
                writer.append(phone).append("\r\n");
                writer.flush();

                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"caption\"\r\n\r\n");
                writer.append(caption == null ? "" : caption).append("\r\n");
                writer.flush();

                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                        .append(file.getName())
                        .append("\"\r\n");
                writer.append("Content-Type: ")
                        .append(getMimeType(file))
                        .append("\r\n\r\n");
                writer.flush();

                Files.copy(file.toPath(), output);
                output.flush();

                writer.append("\r\n--").append(boundary).append("--\r\n");
                writer.flush();
            }

            int code = conn.getResponseCode();
            String response = readResponse(conn, code);

            System.out.println("WA RESPONSE CODE : " + code);
            System.out.println("WA RESPONSE BODY : " + response);

            return code >= 200 && code < 300;

        } catch (SocketTimeoutException e) {
            System.out.println("GOWA TIMEOUT: " + e.getMessage());
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Timeout saat mengirim ke GoWA.\n"
                    + "Koneksi berhasil, tetapi server terlalu lama memberi respons.\n\n"
                    + "Coba ulangi, kecilkan ukuran file, atau cek server GoWA."
            );

            return false;

        } catch (Exception e) {
            System.out.println("GOWA ERROR: " + e.getMessage());
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Gagal mengirim WhatsApp:\n" + e.getMessage()
            );

            return false;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    // =====================================================
    // UTIL
    // =====================================================
    private static String normalizePhone(String raw) {
        if (raw == null) {
            return "";
        }

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

    private static boolean isReady(String noHP) {
        try {
            if (koneksiDBWa.GOWA_BASE_URL() == null
                    || koneksiDBWa.GOWA_BASE_URL().trim().isEmpty()
                    || koneksiDBWa.GOWA_USERNAME() == null
                    || koneksiDBWa.GOWA_USERNAME().trim().isEmpty()
                    || koneksiDBWa.GOWA_PASSWORD() == null
                    || koneksiDBWa.GOWA_PASSWORD().trim().isEmpty()) {

                System.out.println("CONFIG GOWA BELUM LENGKAP");
                JOptionPane.showMessageDialog(null, "Config GoWA belum lengkap.");
                return false;
            }

            if (noHP == null || noHP.trim().isEmpty()) {
                System.out.println("Nomor HP kosong");
                JOptionPane.showMessageDialog(null, "Nomor HP kosong.");
                return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static File getReportFile(String namaFile) {
        File file = new File("report/" + namaFile);

        if (!file.exists() || file.length() == 0) {
            System.out.println("FILE REPORT TIDAK ADA / KOSONG");
            JOptionPane.showMessageDialog(null, "File report tidak ada atau kosong.");
            return null;
        }

        return file;
    }

    private static String getMimeType(File file) {
        String name = file.getName().toLowerCase();

        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".gif")) {
            return "image/gif";
        }

        if (name.endsWith(".webp")) {
            return "image/webp";
        }

        if (name.endsWith(".pdf")) {
            return "application/pdf";
        }

        return "application/octet-stream";
    }

    private static String readResponse(HttpURLConnection conn, int code) {
        StringBuilder sb = new StringBuilder();

        try {
            InputStream stream;

            if (code >= 200 && code < 300) {
                stream = conn.getInputStream();
            } else {
                stream = conn.getErrorStream();
            }

            if (stream == null) {
                return "";
            }

            try (
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(stream, StandardCharsets.UTF_8)
                    )) {
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }

        } catch (Exception e) {
            sb.append("Gagal membaca response: ").append(e.getMessage());
        }

        return sb.toString();
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
            e.printStackTrace();
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
}
