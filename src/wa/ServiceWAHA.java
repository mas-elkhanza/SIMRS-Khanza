package wa;

import fungsi.PdfProtectorBox;
import fungsi.koneksiDBWa;
import fungsi.sekuel;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceWAHA {

    private sekuel Sequel = new sekuel();
    private static final int CONNECT_TIMEOUT = 7000;
    private static final int READ_TIMEOUT = 15000;

    /* =====================================================
       ================= TEXT ONLY =========================
       ===================================================== */
    public boolean kirimTextOnly(String noHP, String pesan) {

        if (!isSessionReady()) {
            System.out.println("SESSION TIDAK READY");
            return false;
        }

        String baseUrl = koneksiDBWa.WAHA_BASE_URL();
        String apiKey = koneksiDBWa.WAHA_API_KEY();
        String session = koneksiDBWa.SESSION();

        String phone = normalizePhone(noHP);

        String payload = "{"
                + "\"chatId\":\"" + phone + "@c.us\","
                + "\"text\":\"" + escapeJson(pesan) + "\","
                + "\"session\":\"" + session + "\""
                + "}";

        SendResult r = postJson(
                baseUrl + "/api/sendText",
                payload,
                apiKey
        );
        System.out.println("FINAL RESULT: " + r.ok);
        return r.ok;
    }
    public boolean kirimDokumenDariNoRM(
            String namaFileReport,
            String jenisDokumen,
            String noRM,
            String idDokumen,
            String pesan
    ) {

        if (!isSessionReady()) {
            return false;
        }

        File file = new File("report/" + namaFileReport);

        String noHP = Sequel.cariIsi(
                "select no_tlp from pasien where no_rkm_medis=?",
                noRM
        );

        String tglLahir = Sequel.cariIsi(
                "select tgl_lahir from pasien where no_rkm_medis=?",
                noRM
        );

        String password = generatePasswordFromBirthDate(tglLahir);

        return kirimDokumenCore(
                file,
                jenisDokumen,
                idDokumen,
                pesan,
                normalizePhone(noHP),
                password
        );
    }
    /* =====================================================
       ================= CORE DOKUMEN ======================
       ===================================================== */
    private boolean kirimDokumenCore(
            File existingFile,
            String jenisDokumen,
            String idDokumen,
            String pesan,
            String noHP,
            String passwordPdf
    ) {
        try {

            if (!existingFile.exists() || existingFile.length() == 0) {
                return false;
            }

            String phone = normalizePhone(noHP);

            File securePdf = preparePdf(
                    existingFile,
                    jenisDokumen,
                    idDokumen,
                    passwordPdf
            );

            if (securePdf == null) {
                return false;
            }

            String fileUrl = uploadPDFToServer(securePdf);
            if (fileUrl == null) {
                return false;
            }

            String finalMessage = pesan + "\n\n" + fileUrl;

            return kirimTextOnly(phone, finalMessage);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =====================================================
       ================= WRAPPER METHOD ====================
       ===================================================== */
    

    public boolean kirimDokumenDariNoRawat(
            String namaFileReport,
            String jenisDokumen,
            String noRawat,
            String idDokumen,
            String pesan
    ) {

        String noRM = Sequel.cariIsi(
                "select no_rkm_medis from reg_periksa where no_rawat=?",
                noRawat
        );

        return kirimDokumenDariNoRM(
                namaFileReport,
                jenisDokumen,
                noRM,
                idDokumen,
                pesan
        );
    }

    public boolean kirimDokumenLAB(
            String namaFileReport,
            String jenisDokumen,
            String noRawat,
            String idDokumen,
            String pesan,
            String noHPForm
    ) {

        if (!isSessionReady()) {
            return false;
        }

        File file = new File("report/" + namaFileReport);

        String noRM = Sequel.cariIsi(
                "select no_rkm_medis from reg_periksa where no_rawat=?",
                noRawat
        );

        String tglLahir = Sequel.cariIsi(
                "select tgl_lahir from pasien where no_rkm_medis=?",
                noRM
        );

        String password = generatePasswordFromBirthDate(tglLahir);

        return kirimDokumenCore(
                file,
                jenisDokumen,
                idDokumen,
                pesan,
                normalizePhone(noHPForm),
                password
        );
    }

    /* =====================================================
       ================= PDF PROTECT =======================
       ===================================================== */
    private File preparePdf(
            File source,
            String jenisDokumen,
            String idDokumen,
            String password
    ) {

        try {

            File folder = new File("tmpPDF");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            String safeId = (idDokumen == null || idDokumen.trim().isEmpty())
                    ? "DOC"
                    : idDokumen.replaceAll("[^0-9A-Za-z]", "_");

            File outputFile = new File(folder,
                    jenisDokumen.replaceAll("\\s+", "_")
                    + "_" + safeId
                    + "_" + timestamp + ".pdf");

            // ===== TANPA PASSWORD =====
            if (password == null || password.trim().isEmpty()) {

                Files.copy(
                        source.toPath(),
                        outputFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );

                return outputFile;
            }

            // ===== DENGAN PASSWORD =====
            PdfProtectorBox.encrypt(
                    source,
                    outputFile,
                    password,
                    password,
                    128
            );

            return outputFile;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* =====================================================
       ================= UPLOAD ============================
       ===================================================== */
    private String uploadPDFToServer(File file) {

        try {

            String boundary = "----SIMRS-" + System.currentTimeMillis();
            String uploadUrl = koneksiDBWa.FILE_BASE_URL()
                    + "/generatePDF/generate_upload.php";

            HttpURLConnection conn
                    = (HttpURLConnection) new URL(uploadUrl).openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization",
                    "Bearer " + koneksiDBWa.TOKEN());
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            try (OutputStream output = conn.getOutputStream(); PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(output, StandardCharsets.UTF_8), true)) {

                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                        .append(file.getName()).append("\"\r\n");
                writer.append("Content-Type: application/pdf\r\n\r\n").flush();

                Files.copy(file.toPath(), output);
                output.flush();

                writer.append("\r\n--").append(boundary).append("--\r\n").flush();
            }

            if (conn.getResponseCode() == 200) {
                String response = new String(
                        conn.getInputStream().readAllBytes(),
                        StandardCharsets.UTF_8
                );

                if (response.contains("\"url\"")) {
                    int idx = response.indexOf("\"url\"");
                    int start = response.indexOf("\"", idx + 6) + 1;
                    int end = response.indexOf("\"", start);
                    return response.substring(start, end);
                }
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* =====================================================
       ================= SESSION ===========================
       ===================================================== */
//    private boolean isSessionReady() {
//
//        try {
//
//            URL url = new URL(
//                    koneksiDBWa.WAHA_BASE_URL()
//                    + "/api/sessions/"
//                    + koneksiDBWa.SESSION()
//            );
//
//            HttpURLConnection conn
//                    = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("X-API-Key",
//                    koneksiDBWa.WAHA_API_KEY());
//
//            if (conn.getResponseCode() != 200) {
//                return false;
//            }
//
//            String response = new String(
//                    conn.getInputStream().readAllBytes(),
//                    StandardCharsets.UTF_8
//            );
//
//            return response.contains("\"status\":\"WORKING\"");
//
//        } catch (Exception e) {
//            return false;
//        }
//    }
    private boolean isSessionReady() {
        try {
            URL url = new URL(
                    koneksiDBWa.WAHA_BASE_URL()
                    + "/api/sessions/"
                    + koneksiDBWa.SESSION()
            );

            HttpURLConnection conn
                    = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-API-Key",
                    koneksiDBWa.WAHA_API_KEY());

            int code = conn.getResponseCode();
            System.out.println("SESSION CHECK CODE: " + code);

            String response = new String(
                    conn.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            System.out.println("SESSION RESPONSE: " + response);

            return response.contains("\"status\":\"WORKING\"");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =====================================================
       ================= UTIL ==============================
       ===================================================== */
    private String normalizePhone(String raw) {

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

    private String escapeJson(String text) {

        if (text == null) {
            return "";
        }

        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "\\n");
    }

    private String generatePasswordFromBirthDate(String tgl) {

        try {
            tgl = tgl.substring(0, 10);
            java.time.LocalDate date
                    = java.time.LocalDate.parse(tgl);

            return date.format(
                    java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy")
            );

        } catch (Exception e) {
            return "00000000";
        }
    }

    /* =====================================================
       ================= RESULT ============================
       ===================================================== */
    public static class SendResult {

        public boolean ok;
        public int httpCode;
        public String responseBody;
        public String error;

        public SendResult(boolean ok, int httpCode,
                String responseBody, String error) {
            this.ok = ok;
            this.httpCode = httpCode;
            this.responseBody = responseBody;
            this.error = error;
        }
    }

    private SendResult postJson(String urlStr, String payload, String apiKey) {

        try {

            HttpURLConnection conn
                    = (HttpURLConnection) new URL(urlStr).openConnection();

            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type",
                    "application/json; charset=UTF-8");
            conn.setRequestProperty("X-API-Key", apiKey);

            conn.getOutputStream().write(
                    payload.getBytes(StandardCharsets.UTF_8)
            );

            int code = conn.getResponseCode();

            InputStream is = (code >= 200 && code < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            String response = "";

            if (is != null) {
                response = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }

            System.out.println("WAHA RESPONSE CODE: " + code);
            System.out.println("WAHA RESPONSE BODY: " + response);

            return new SendResult(
                    code >= 200 && code < 300,
                    code,
                    response,
                    null
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new SendResult(false, 0, null, e.getMessage());
        }
    }

    public boolean kirimDokumenNoPassword(
            String namaFileReport,
            String jenisDokumen,
            String noHP,
            String idDokumen,
            String pesan
    ) {

        if (!isSessionReady()) {
            return false;
        }

        File file = new File("report/" + namaFileReport);

        return kirimDokumenCore(
                file,
                jenisDokumen,
                idDokumen,
                pesan,
                normalizePhone(noHP),
                null // <<< TANPA PASSWORD
        );
    }
}
