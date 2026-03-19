package wa;

import fungsi.koneksiDBWa;
import fungsi.sekuel;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class GoWAService {

    private static final sekuel Sequel = new sekuel();

    private static final int CONNECT_TIMEOUT = 7000;
    private static final int READ_TIMEOUT = 20000;

    // =====================================================
    // ENGINE KIRIM FILE (PDF / JPG / PNG / DLL)
    // =====================================================
    private static boolean kirimFile(File file, String noHP, String caption) {

        try {

            if (!isReady(noHP)) {
                return false;
            }

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
            conn.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=" + boundary
            );

            try (
                    OutputStream output = conn.getOutputStream(); PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(output, StandardCharsets.UTF_8),
                    true
            )) {

                // PHONE
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"phone\"\r\n\r\n");
                writer.append(phone).append("\r\n");

                // CAPTION
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"caption\"\r\n\r\n");
                writer.append(caption).append("\r\n");

                // FILE
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                        .append(file.getName()).append("\"\r\n");
                writer.append("Content-Type: application/octet-stream\r\n\r\n");
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

    // =====================================================
    // PUBLIC METHODS BARU
    // =====================================================
    public static boolean kirimGambar(String noHP, File file, String caption) {
        return kirimFile(file, noHP, caption);
    }
    
    public static boolean kirimPDF(String noHP, File file, String caption) {
        return kirimFile(file, noHP, caption);
    }

    // =====================================================
    // KOMPATIBILITAS DENGAN KODE LAMA
    // =====================================================
    public static boolean kirimDariNoRM(
            String namaFileReport,
            String jenisDokumen,
            String noRM,
            String noHPForm,
            boolean usePassword,
            String pesan
    ) {

        File file = new File("report/" + namaFileReport);

        if (!file.exists()) {
            System.out.println("FILE REPORT TIDAK DITEMUKAN");
            return false;
        }

        return kirimFile(file, noHPForm, pesan);
    }

    public static boolean kirimDariNoRawat(
            String namaFileReport,
            String jenisDokumen,
            String noRawat,
            String noHPForm,
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

        File file = new File("report/" + namaFileReport);

        if (!file.exists()) {

            System.out.println("FILE REPORT TIDAK DITEMUKAN");
            return false;

        }

        return kirimFile(file, noHPForm, pesan);
    }

    // =====================================================
    // VALIDATION
    // =====================================================
    private static boolean isReady(String noHP) {

        if (koneksiDBWa.GOWA_BASE_URL().isEmpty()
                || koneksiDBWa.GOWA_USERNAME().isEmpty()
                || koneksiDBWa.GOWA_PASSWORD().isEmpty()) {

            System.out.println("CONFIG GOWA BELUM LENGKAP");
            return false;
        }

        if (!isSessionReady()) {

            System.out.println("SESSION GOWA BELUM CONNECTED");
            return false;
        }

        if (noHP == null || noHP.trim().isEmpty()) {

            System.out.println("Nomor HP kosong");
            return false;
        }

        return true;
    }

    private static boolean isSessionReady() {

        try {

            URL url = new URL(
                    koneksiDBWa.GOWA_BASE_URL()
                    + "/app/status?device_id=" + deviceId()
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", basicAuth());

            if (conn.getResponseCode() != 200) {
                return false;
            }

            String response = new String(
                    conn.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            return response.contains("\"is_connected\":true")
                    && response.contains("\"is_logged_in\":true");

        } catch (Exception e) {

            return false;

        }
    }

    // =====================================================
    // UTIL
    // =====================================================
    private static String deviceId() {

        try {

            String id = koneksiDBWa.GOWA_DEVICE_ID();

            if (id == null || id.trim().isEmpty()) {
                return "default";
            }

            return id.trim();

        } catch (Exception e) {

            return "default";

        }
    }

    private static String basicAuth() {

        String auth
                = koneksiDBWa.GOWA_USERNAME()
                + ":"
                + koneksiDBWa.GOWA_PASSWORD();

        return "Basic "
                + Base64.getEncoder()
                        .encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

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
}
