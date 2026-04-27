package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.akses;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author windiartonugroho
 */
public class ApiOrthanc {

    private HttpHeaders headers;
    private JsonNode root;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private sekuel Sequel = new sekuel();
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;
    private String auth, authEncrypt, requestJson;
    private byte[] encodedBytes;
    private int i = 1;

    public ApiOrthanc() {
        try {
            auth = koneksiDB.USERORTHANC() + ":" + koneksiDB.PASSORTHANC();
            encodedBytes = Base64.encodeBase64(auth.getBytes());
            authEncrypt = new String(encodedBytes);
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
        }
    }

    public String Auth() {
        return authEncrypt;
    }

    public JsonNode AmbilSeries(String Norm, String Tanggal1, String Tanggal2) {
        System.out.println("Percobaan Mengambil Photo Pasien : " + Norm);
        try {
            headers = new HttpHeaders();
            System.out.println("Auth : " + authEncrypt);
            headers.add("Authorization", "Basic " + authEncrypt);
            requestJson = "{"
                    + "\"Level\": \"Study\","
                    + "\"Expand\": true,"
                    + "\"Query\": {"
                    + "\"StudyDate\": \"" + Tanggal1 + "-" + Tanggal2 + "\","
                    + "\"PatientID\": \"" + Norm + "\""
                    + "}"
                    + "}";
            System.out.println("Request JSON : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/tools/find");
            requestJson = getRest().exchange(koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/tools/find",
                    HttpMethod.POST, requestEntity, String.class).getBody();
            System.out.println("Result JSON : " + requestJson);
            root = mapper.readTree(requestJson);
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(null,
                    "Gagal mengambil data dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }

    public JsonNode AmbilPng(String NoRawat, String Series) {
        System.out.println("Percobaan Mengambil Gambar PNG : " + NoRawat + ", Series : " + Series);
        try {
            headers = new HttpHeaders();
            System.out.println("Auth : " + authEncrypt);
            headers.add("Authorization", "Basic " + authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series);
            requestJson = getRest()
                    .exchange(koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series,
                            HttpMethod.GET, requestEntity, String.class)
                    .getBody();
            System.out.println("Result JSON : " + requestJson);
            root = mapper.readTree(requestJson);
            i = 1;
            for (JsonNode list : root.path("Instances")) {
                System.out.println("Mengambil Gambar PNG " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC()
                        + "/instances/" + list.asText() + "/preview");
                headers = new HttpHeaders();
                headers.add("Authorization", "Basic " + authEncrypt);
                headers.add("Accept", "image/png");
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC() + ":"
                        + koneksiDB.PORTORTHANC() + "/instances/" + list.asText() + "/preview", HttpMethod.GET, entity,
                        byte[].class);
                Files.write(Paths.get("./gambarradiologi/" + NoRawat + i + ".png"), response.getBody());
                i++;
            }
            JOptionPane.showMessageDialog(null,
                    "Pengambilan Gambar PNG dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(null,
                    "Gagal mengambil Gambar PNG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }
    
    public JsonNode AmbilJpg(String NoRawat, String Series, String norawatslash, String tanggalPeriksa, String jamPeriksa) {
        System.out.println("Percobaan Mengambil Gambar JPG : " + NoRawat + ", Series : " + Series);
        try {
            headers = new HttpHeaders();
            System.out.println("Auth : " + authEncrypt);
            headers.add("Authorization", "Basic " + authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series);
            requestJson = getRest()
                    .exchange(koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series,
                            HttpMethod.GET, requestEntity, String.class)
                    .getBody();
            System.out.println("Result JSON : " + requestJson);
            root = mapper.readTree(requestJson);
            i = 1;
            for (JsonNode list : root.path("Instances")) {
                System.out.println("Mengambil Gambar JPG " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC()
                        + "/instances/" + list.asText() + "/preview");
                headers = new HttpHeaders();
                headers.add("Authorization", "Basic " + authEncrypt);
                headers.add("Accept", "image/jpeg");
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC() + ":"
                        + koneksiDB.PORTORTHANC() + "/instances/" + list.asText() + "/preview", HttpMethod.GET, entity,
                        byte[].class);
                String uniqueName = NoRawat + "_" + System.currentTimeMillis() + "_" + i + ".jpg";
                Files.write(Paths.get("./gambarradiologi/" + uniqueName), response.getBody());
                uploadImageRadiologi(uniqueName, "pages/upload");

                System.out.println("Menyimpan ke DB:");
                System.out.println("  no_rawat     : " + norawatslash);
                System.out.println("  tgl_periksa  : " + tanggalPeriksa);
                System.out.println("  jam          : " + jamPeriksa);
                System.out.println("  lokasi_gambar: " + "pages/upload/" + uniqueName);

                Sequel.menyimpantf("gambar_radiologi", "?,?,?,?", "No.Rawat", 4,
                        new String[]{
                            norawatslash,
                            tanggalPeriksa,
                            jamPeriksa,
                            "pages/upload/" + uniqueName
                        });
                i++;
            }
            JOptionPane.showMessageDialog(null, "Penyimpanan Gambar JPG dari Orthanc berhasil");
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            System.out.println("Error detail: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Gagal mengambil Gambar JPG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }

    public JsonNode AmbilBmp(String NoRawat, String Series) {
        System.out.println("Percobaan Mengambil Gambar BMP : " + NoRawat + ", Series : " + Series);
        try {
            headers = new HttpHeaders();
            System.out.println("Auth : " + authEncrypt);
            headers.add("Authorization", "Basic " + authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series);
            requestJson = getRest()
                    .exchange(koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series,
                            HttpMethod.GET, requestEntity, String.class)
                    .getBody();
            System.out.println("Result JSON : " + requestJson);
            root = mapper.readTree(requestJson);
            i = 1;
            for (JsonNode list : root.path("Instances")) {
                System.out.println("Mengambil Gambar BMP " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC()
                        + "/instances/" + list.asText() + "/preview");
                headers = new HttpHeaders();
                headers.add("Authorization", "Basic " + authEncrypt);
                headers.add("Accept", "image/bmp");
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC() + ":"
                        + koneksiDB.PORTORTHANC() + "/instances/" + list.asText() + "/preview", HttpMethod.GET, entity,
                        byte[].class);
                Files.write(Paths.get("./gambarradiologi/" + NoRawat + i + ".bmp"), response.getBody());
                i++;
            }
            JOptionPane.showMessageDialog(null,
                    "Pengambilan Gambar BMP dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(null,
                    "Gagal mengambil Gambar BMP dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }

    public JsonNode AmbilDcm(String NoRawat, String Series) {
        System.out.println("Percobaan Mengambil Gambar DCM : " + NoRawat + ", Series : " + Series);
        try {
            headers = new HttpHeaders();
            System.out.println("Auth : " + authEncrypt);
            headers.add("Authorization", "Basic " + authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series);
            requestJson = getRest()
                    .exchange(koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series,
                            HttpMethod.GET, requestEntity, String.class)
                    .getBody();
            System.out.println("Result JSON : " + requestJson);
            root = mapper.readTree(requestJson);
            i = 1;
            for (JsonNode list : root.path("Instances")) {
                System.out.println("Mengambil Gambar DCM " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC()
                        + "/instances/" + list.asText() + "/file");
                headers = new HttpHeaders();
                headers.add("Authorization", "Basic " + authEncrypt);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC() + ":"
                        + koneksiDB.PORTORTHANC() + "/instances/" + list.asText() + "/file", HttpMethod.GET, entity,
                        byte[].class);
                Files.write(Paths.get("./gambarradiologi/" + NoRawat + i + ".dcm"), response.getBody());
                i++;
            }
            JOptionPane.showMessageDialog(null,
                    "Pengambilan Gambar DCM dari Orthanc berhasil, silahkan lihat di dalam folder Aplikasi..!!");
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(null,
                    "Gagal mengambil Gambar DCM dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }

    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("SSL");
        TrustManager[] trustManagers = {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            }
        };
        sslContext.init(null, trustManagers, new SecureRandom());
        sslFactory = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        scheme = new Scheme("https", 443, sslFactory);
        factory = new HttpComponentsClientHttpRequestFactory();
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }

    public JsonNode AmbilPngUsg(String NoRawat, String Series, String norawatslash) {
        System.out.println("Percobaan Mengambil Gambar PNG : " + NoRawat + ", Series : " + Series);
        try {
            headers = new HttpHeaders();
            System.out.println("Auth : " + authEncrypt);
            headers.add("Authorization", "Basic " + authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series);
            requestJson = getRest()
                    .exchange(koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series,
                            HttpMethod.GET, requestEntity, String.class)
                    .getBody();
            System.out.println("Result JSON : " + requestJson);
            root = mapper.readTree(requestJson);
            i = 1;
            for (JsonNode list : root.path("Instances")) {
                System.out.println("Mengambil Gambar PNG " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC()
                        + "/instances/" + list.asText() + "/preview");
                headers = new HttpHeaders();
                headers.add("Authorization", "Basic " + authEncrypt);
                headers.add("Accept", "image/png");
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC() + ":"
                        + koneksiDB.PORTORTHANC() + "/instances/" + list.asText() + "/preview", HttpMethod.GET, entity,
                        byte[].class);
                Files.write(Paths.get("./gambarradiologi/" + NoRawat + i + ".png"), response.getBody());
                // Menambahkan fitur simpan gambar radiologi dari orthanc
                uploadImageUsg(NoRawat + i + ".png", "pages/upload");
                Sequel.menyimpantf("hasil_pemeriksaan_usg_gambar", "?,?", "No.Rawat", 2, new String[]{
                    norawatslash, "pages/upload/" + NoRawat + i + ".png"
                });
                i++;
            }
            JOptionPane.showMessageDialog(null, "Penyimpanan Gambar PNG dari Orthanc ke Webapps berhasil");
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(null,
                    "Gagal mengambil Gambar PNG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }

    public JsonNode AmbilJpgUsg(String NoRawat, String Series, String norawatslash) {

        System.out.println("Percobaan Mengambil Gambar JPG : " + NoRawat + ", Series : " + Series);
        try {
            headers = new HttpHeaders();
            System.out.println("Auth : " + authEncrypt);
            headers.add("Authorization", "Basic " + authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series);
            requestJson = getRest()
                    .exchange(koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series,
                            HttpMethod.GET, requestEntity, String.class)
                    .getBody();
            System.out.println("Result JSON : " + requestJson);
            root = mapper.readTree(requestJson);
            i = 1;
            for (JsonNode list : root.path("Instances")) {
                System.out.println("Mengambil Gambar JPG " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC()
                        + "/instances/" + list.asText() + "/preview");
                headers = new HttpHeaders();
                headers.add("Authorization", "Basic " + authEncrypt);
                headers.add("Accept", "image/jpeg");
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC() + ":"
                        + koneksiDB.PORTORTHANC() + "/instances/" + list.asText() + "/preview", HttpMethod.GET, entity,
                        byte[].class);
                String uniqueName = NoRawat + "_" + System.currentTimeMillis() + "_" + i + ".jpg";
                Files.write(Paths.get("./gambarradiologi/" + uniqueName), response.getBody());

                uploadImageUsg(uniqueName, "pages/upload");
                Sequel.menyimpantf("hasil_pemeriksaan_usg_gambar", "?,?", "No.Rawat", 2, new String[]{
                    norawatslash, "pages/upload/" + uniqueName
                });
                i++;
            }
            JOptionPane.showMessageDialog(null, "Penyimpanan Gambar JPG dari Orthanc berhasil");
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(null,
                    "Gagal mengambil Gambar JPG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }

    void uploadImageUsg(String FileName, String docpath) {
        try {
            File file = new File("gambarradiologi/" + FileName);
            byte[] data = new byte[(int) file.length()];
            data = FileUtils.readFileToByteArray(file);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/"
                    + koneksiDB.HYBRIDWEB() + "/hasilpemeriksaanusg/upload.php?doc=" + docpath);
            ByteArrayBody fileData = new ByteArrayBody(data, FileName);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);
            httpClient.execute(postRequest);
            deleteFile();
        } catch (Exception e) {
            System.out.println("Upload error" + e);
        }
    }

    public JsonNode AmbilJpgUsg2(String NoRawat, String Series, String norawatslash) {

        System.out.println("Percobaan Mengambil Gambar JPG : " + NoRawat + ", Series : " + Series);
        try {
            headers = new HttpHeaders();
            System.out.println("Auth : " + authEncrypt);
            headers.add("Authorization", "Basic " + authEncrypt);
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series);
            requestJson = getRest()
                    .exchange(koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/series/" + Series,
                            HttpMethod.GET, requestEntity, String.class)
                    .getBody();
            System.out.println("Result JSON : " + requestJson);
            root = mapper.readTree(requestJson);
            i = 1;
            for (JsonNode list : root.path("Instances")) {
                System.out.println("Mengambil Gambar JPG " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC()
                        + "/instances/" + list.asText() + "/preview");
                headers = new HttpHeaders();
                headers.add("Authorization", "Basic " + authEncrypt);
                headers.add("Accept", "image/jpeg");
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_JPEG));
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<byte[]> response = getRest().exchange(koneksiDB.URLORTHANC() + ":"
                        + koneksiDB.PORTORTHANC() + "/instances/" + list.asText() + "/preview", HttpMethod.GET, entity,
                        byte[].class);
                Files.write(Paths.get("./gambarradiologi/" + NoRawat + i + ".jpg"), response.getBody());

                uploadImageUsg2(NoRawat + i + ".jpg", "pages/upload");
                Sequel.menyimpantf("hasil_pemeriksaan_usg_gynecologi_gambar", "?,?", "No.Rawat", 2, new String[]{
                    norawatslash, "pages/upload/" + NoRawat + i + ".jpg"
                });
                i++;
            }
            JOptionPane.showMessageDialog(null, "Penyimpanan Gambar JPG dari Orthanc berhasil");
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(null,
                    "Gagal mengambil Gambar JPG dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
    }

    void uploadImageUsg2(String FileName, String docpath) {
        try {
            File file = new File("gambarradiologi/" + FileName);
            byte[] data = new byte[(int) file.length()];
            data = FileUtils.readFileToByteArray(file);
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/"
                    + koneksiDB.HYBRIDWEB() + "/hasilpemeriksaanusggynecologi/upload.php?doc=" + docpath);
            ByteArrayBody fileData = new ByteArrayBody(data, FileName);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);
            httpClient.execute(postRequest);
            deleteFile();
        } catch (Exception e) {
            System.out.println("Upload error" + e);
        }
    }

    void uploadImageRadiologi(String FileName, String docpath) {
        try {
            File file = new File("gambarradiologi/" + FileName);
            byte[] data = FileUtils.readFileToByteArray(file);
            HttpClient httpClient = new DefaultHttpClient();

            // Samakan URL dengan uploadImageUsg, hanya beda folder (radiologi bukan hasilpemeriksaanusg)
            HttpPost postRequest = new HttpPost("http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/"
                    + koneksiDB.HYBRIDWEB() + "/radiologi/upload.php?doc=" + docpath + "/");
            //                                                                                            ↑ tambah slash di sini
            ByteArrayBody fileData = new ByteArrayBody(data, FileName);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileData);
            postRequest.setEntity(reqEntity);
            httpClient.execute(postRequest);
            deleteFile();
        } catch (Exception e) {
            System.out.println("Upload Radiologi error: " + e);
        }
    }

    public String UbahAccession(String studyId, String accessionBaru) {
        System.out.println("Inject AccessionNumber: " + accessionBaru + " ke Study: " + studyId);
        try {
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + authEncrypt);
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestJson = "{"
                    + "\"Replace\": {"
                    + "\"AccessionNumber\": \"" + accessionBaru + "\""
                    + "},"
                    + "\"KeepSource\": false"
                    + "}";
            System.out.println("Request JSON : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/studies/" + studyId
                    + "/modify");
            String response = getRest().exchange(
                    koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/studies/" + studyId + "/modify",
                    HttpMethod.POST, requestEntity, String.class).getBody();
            System.out.println("Response : " + response);
            // /modify returns a new Study ID (KeepSource: false deletes the old one)
            JsonNode resp = mapper.readTree(response);
            String newStudyId = resp.path("ID").asText();
            System.out.println("New Study ID setelah inject: " + newStudyId);
            return newStudyId;
        } catch (Exception e) {
            System.out.println("Notifikasi UbahAccession : " + e);
            return "";
        }
    }

    public void KirimDicom(String Series, String ModalityName) {
        try {
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + authEncrypt);
            requestJson = "{\"Resources\": [\"" + Series + "\"]}";
            requestEntity = new HttpEntity(requestJson, headers);
            getRest().exchange(
                    koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/modalities/" + ModalityName + "/store",
                    HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public boolean kirimKeModality(String studyId) {
        System.out.println("Kirim Study ke Modality : " + studyId);
        try {
            headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + authEncrypt);
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestJson = "[\"" + studyId + "\"]";
            requestEntity = new HttpEntity(requestJson, headers);
            System.out.println("URL : " + koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/modalities/DCMROUTER/store");
            System.out.println("Request JSON : " + requestJson);
            String response = getRest().exchange(koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/modalities/DCMROUTER/store", HttpMethod.POST, requestEntity, String.class).getBody();
            System.out.println("Response : " + response);
            JOptionPane.showMessageDialog(null, "Proses kirim ke Modality selesai..!!");
            return true;
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            JOptionPane.showMessageDialog(null, "Gagal kirim ke Modality..!!");
            return false;
        }
    }

    public String getModality(String nmPemeriksaan) {
        String modality = "OT"; // Default Other
        String name = nmPemeriksaan.toUpperCase();
        if (name.contains("USG") || name.contains("ULTRASONO")) {
            modality = "US";
        } else if (name.contains("CR") || name.contains("DX") || name.contains("RONTGEN") || name.contains("ROENTGEN")
                || name.contains("THORAX") || name.contains("TORAKS") || name.contains("FOTO") || name.contains("PHOTO")
                || name.contains("PANORAMIC") || name.contains("RADIOGRAFI") || name.contains("RADIOGRAPHY")) {
            modality = "XR";
        } else if (name.contains("CT") || name.contains("MSCT") || name.contains("SCAN")) {
            modality = "CT";
        } else if (name.contains("MRI") || name.contains("MRA") || name.contains("MRCP")) {
            modality = "MR";
        } else if (name.contains("EKG") || name.contains("ECG") || name.contains("ELEKTROKAR")) {
            modality = "ECG";
        }
        return modality;
    }

    public String KirimKeOrthanc(String noRawat, String nmPasien, String noRm, String tglLahir, String jk,
            String accession, String fileURL, String tglPasien, String studyDesc, String seriesDesc, String modality,
            String instanceNum) {
        System.out.println("Kirim Gambar ke Orthanc: " + fileURL);
        try {
            // 1. Download file JPG dari web server ke folder lokal temporary
            String fileName = noRawat.replaceAll("/", "") + "_" + System.currentTimeMillis() + ".jpg";
            File localFile = new File("./gambarradiologi/" + fileName);

            headers = new HttpHeaders();
            requestEntity = new HttpEntity(headers);
            ResponseEntity<byte[]> responseFile = getRest().exchange(fileURL, HttpMethod.GET, requestEntity,
                    byte[].class);
            Files.write(Paths.get("./gambarradiologi/" + fileName), responseFile.getBody());

            // 2. Baca file dan konversi ke Base64
            byte[] fileContent = FileUtils.readFileToByteArray(localFile);
            String encodedString = Base64.encodeBase64String(fileContent);

            // 3. Persiapkan JSON untuk Orthanc /tools/create-dicom
            // Gunakan prefix 1.2.826.0.1.3680043.2 (disediakan untuk UID privat)
            String cleanAccession = accession.replaceAll("[^0-9]", "");
            if (cleanAccession.isEmpty()) {
                cleanAccession = "0";
            }

            String studyUID = "1.2.826.0.1.3680043.2." + cleanAccession;
            String seriesUID = studyUID + ".1";
            String sopUID = seriesUID + "." + instanceNum;

            headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + authEncrypt);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> dicomData = new HashMap<>();
            Map<String, Object> tags = new HashMap<>(); // Gunakan Object agar bisa kirim Integer
            tags.put("PatientID", noRm);
            tags.put("PatientName", nmPasien);
            tags.put("PatientBirthDate", tglLahir.replaceAll("-", ""));
            tags.put("PatientSex", jk.equals("L") ? "M" : "F");
            tags.put("AccessionNumber", accession);
            tags.put("StudyInstanceUID", studyUID);
            tags.put("SeriesInstanceUID", seriesUID);
            // tags.put("0008,0018", sopUID);    // SOPInstanceUID (Orthanc handles this automatically)
            tags.put("StudyDate", tglPasien.replaceAll("-", ""));
            tags.put("StudyDescription", studyDesc);
            tags.put("Modality", modality);
            tags.put("SeriesDescription", seriesDesc);
            tags.put("SeriesNumber", "1");
            tags.put("InstanceNumber", instanceNum);
            tags.put("Manufacturer", "SIMRS Khanza");
            tags.put("InstitutionName", akses.getnamars());

            dicomData.put("Tags", tags);
            dicomData.put("Content", "data:image/jpeg;base64," + encodedString);
            dicomData.put("Force", true);

            requestJson = mapper.writeValueAsString(dicomData);
            System.out.println("JSON Request: " + requestJson);

            requestEntity = new HttpEntity(requestJson, headers);
            String response = "";
            try {
                response = getRest().exchange(
                        koneksiDB.URLORTHANC() + ":" + koneksiDB.PORTORTHANC() + "/tools/create-dicom",
                        HttpMethod.POST, requestEntity, String.class).getBody();
            } catch (org.springframework.web.client.HttpClientErrorException e) {
                System.out.println("Detail Error Orthanc (400/404/401): " + e.getResponseBodyAsString());
                throw e;
            }
            System.out.println("Response Upload Orthanc: " + response);

            // 4. Hapus file temporary
            localFile.delete();

            JsonNode resp = mapper.readTree(response);
            return resp.path("ID").asText(); // Mengembalikan Instance ID (bukan Study ID)
        } catch (Exception e) {
            System.out.println("Notifikasi KirimKeOrthanc: " + e);
            return "";
        }
    }

    void deleteFile() {
        File file = new File("gambarradiologi");
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }
}
