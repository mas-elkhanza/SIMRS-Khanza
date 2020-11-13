/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inhealth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author RSUI HA
 */
public class InhealtsAPI {

    private final Properties prop = new Properties();
    String requestJson = "";
    HttpHeaders headers;
    String response = null;
    String token;

    public InhealtsAPI() {
        this.token = prop.getProperty("TOKENINHEALTH");
        headers = new HttpHeaders();
    }

    public JsonNode SimpanTindakan(String token, String kodeProvider, String jnsPelayanan, String noSjp, String tglMasukRawat, String date, String kodeTindakan, String poli, String kodeDokter, String tarip) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/SimpanTindakan";
//        token = prop.getProperty("TOKENINHEALTH");

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"jenispelayanan\": \"" + jnsPelayanan + "\","
                + "\"nosjp\": \"" + noSjp + "\","
                + "\"tglmasukrawat\": \"" + tglMasukRawat + "\","
                + "\"tanggalpelayanan\": \"" + date + "\","
                + "\"kodetindakan\": \"" + kodeTindakan + "\","
                + "\"poli\": \"" + poli + "\","
                + "\"kodedokter\": \"" + kodeDokter + "\","
                + "\"biayaaju\": \"" + tarip + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response Simpan Tindakan :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            System.out.println("Response Simpan Tindakan :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        }
        return root;
    }

    public String CekRestriksiEPrescriptions(String kodeProvider, String kodeObatrs, String user) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/CekRestriksiEPrescriptions";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"kodeobatrs\": \"" + kodeObatrs + "\","
                + "\"user\": \"" + user + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String CekRestriksiTransaksi(String kodeProvider, String kodeObatrs, String noSjp, String kodeObatRs, int jumlahObat, String user) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/CekRestriksiTransaksi";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"nosjp\": \"" + noSjp + "\","
                + "\"kodeobatrs\": \"" + kodeObatRs + "\","
                + "\"jumlahobat\": \"" + jumlahObat + "\","
                + "\"user\": \"" + user + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String CekSJP(String kodeProvider, String noKaInhealth, String tanggalSjp, String poli, String tkp) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/CekSJP";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"nokainhealth\": \"" + noKaInhealth + "\","
                + "\"tanggalsjp\": \"" + tanggalSjp + "\","
                + "\"poli\": \"" + poli + "\","
                + "\"tkp\": \"" + tkp + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String CetakSJP(String kodeProvider, String noSjp, String tkp, String tipeFile) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/CetakSJP";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"nosjp\": \"" + noSjp + "\","
                + "\"tkp\": \"" + tkp + "\","
                + "\"tipefile\": \"" + tipeFile + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String EligibilitasPeserta(String kodeProvider, String noKaInhealth, String tglPelayanan, String jenisPelayanan, String poli) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/EligibilitasPeserta";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"nokainhealth\": \"" + noKaInhealth + "\","
                + "\"tglpelayanan\": \"" + tglPelayanan + "\","
                + "\"jenispelayanan\": \"" + jenisPelayanan + "\","
                + "\"poli\": \"" + poli + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String SimpanSJP(String kodeProvider, String tanggalpelayanan, String jenispelayanan,
            String nokainhealth, String nomormedicalreport, String nomorasalrujukan, String kodeproviderasalrujukan,
            String tanggalasalrujukan, String kodediagnosautama, String poli,
            String username, String informasitambahan, String kodediagnosatambahan, String kecelakaankerja,
            String kelasrawat, String kodejenpelruangrawat) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/SimpanSJP";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"tanggalpelayanan\": \"" + tanggalpelayanan + "\","
                + "\"jenispelayanan\": \"" + jenispelayanan + "\","
                + "\"nokainhealth\": \"" + nokainhealth + "\","
                + "\"nomormedicalreport\": \"" + nomormedicalreport + "\","
                + "\"nomorasalrujukan\": \"" + nomorasalrujukan + "\","
                + "\"kodeproviderasalrujukan\": \"" + kodeproviderasalrujukan + "\","
                + "\"tanggalasalrujukan\": \"" + tanggalasalrujukan + "\","
                + "\"kodediagnosautama\": \"" + kodediagnosautama + "\","
                + "\"poli\": \"" + poli + "\","
                + "\"username\": \"" + username + "\","
                + "\"informasitambahan\": \"" + informasitambahan + "\","
                + "\"kodediagnosatambahan\": \"" + kodediagnosatambahan + "\","
                + "\"kecelakaankerja\": \"" + kecelakaankerja + "\","
                + "\"kelasrawat\": \"" + kelasrawat + "\","
                + "\"kodejenpelruangrawat\": \"" + kodejenpelruangrawat + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String SimpanObat(String kodeProvider, String nosjp, String noresep,
            String tanggalresep, String tanggalobat, String tipeobat, String jenisracikan,
            String kodeobatrs, String namaobat, String kodedokter,
            int jumlahobat, int signa1, int signa2, int jumlahhari,
            int hdasar, String confirmationcode, String username) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/SimpanObat";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"nosjp\": \"" + nosjp + "\","
                + "\"noresep\": \"" + noresep + "\","
                + "\"tanggalresep\": \"" + tanggalresep + "\","
                + "\"tanggalobat\": \"" + tanggalobat + "\","
                + "\"tipeobat\": \"" + tipeobat + "\","
                + "\"jenisracikan\": \"" + jenisracikan + "\","
                + "\"kodeobatrs\": \"" + kodeobatrs + "\","
                + "\"namaobat\": \"" + namaobat + "\","
                + "\"kodedokter\": \"" + kodedokter + "\","
                + "\"jumlahobat\": \"" + jumlahobat + "\","
                + "\"signa1\": \"" + signa1 + "\","
                + "\"signa2\": \"" + signa2 + "\","
                + "\"jumlahhari\": \"" + jumlahhari + "\","
                + "\"hdasar\": \"" + hdasar + "\","
                + "\"confirmationcode\": \"" + confirmationcode + "\","
                + "\"username\": \"" + username + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String SimpanTindakanRITL(String kodeProvider, String jnsPelayanan, String noSjp, String tglMasukRawat, String date, String kodeTindakan, String poli, String kodeDokter, int tarip) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/SimpanTindakan";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"jenispelayanan\": \"" + jnsPelayanan + "\","
                + "\"nosjp\": \"" + noSjp + "\","
                + "\"tglmasukrawat\": \"" + tglMasukRawat + "\","
                + "\"tanggalpelayanan\": \"" + date + "\","
                + "\"kodetindakan\": \"" + kodeTindakan + "\","
                + "\"poli\": \"" + poli + "\","
                + "\"kodedokter\": \"" + kodeDokter + "\","
                + "\"biayaaju\": \"" + tarip + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String UpdateTanggalPulang(String kodeProvider, int id, String noSjp, String tglmasuk, String tglkeluar) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/UpdateTanggalPulang";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"id\": \"" + id + "\","
                + "\"nosjp\": \"" + noSjp + "\","
                + "\"tglmasuk\": \"" + tglmasuk + "\","
                + "\"tglkeluar\": \"" + tglkeluar + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String SimpanRuangRawat(String kodeProvider, String noSjp, String tglmasuk, String kelasrawat, String kodejenispelayanan, String byharirawat) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/SimpanRuangRawat";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"nosjp\": \"" + noSjp + "\","
                + "\"tglmasuk\": \"" + tglmasuk + "\","
                + "\"kelasrawat\": \"" + kelasrawat + "\","
                + "\"kodejenispelayanan\": \"" + kodejenispelayanan + "\","
                + "\"byharirawat\": \"" + byharirawat + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String ConfirmAKTFirstPayor(String kodeProvider, String noSjp, String userid) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/ConfirmAKTFirstPayor";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"nosjp\": \"" + noSjp + "\","
                + "\"userid\": \"" + userid + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public String SimpanBiayaINACBGS(String kodeProvider, String noSjp, String kodeinacbg,
            String biayainacbgs, String nosep, String notes, String userid) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/SimpanBiayaINACBGS";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"nosjp\": \"" + noSjp + "\","
                + "\"kodeinacbg\": \"" + kodeinacbg + "\","
                + "\"biayainacbgs\": \"" + biayainacbgs + "\","
                + "\"nosep\": \"" + nosep + "\","
                + "\"notes\": \"" + notes + "\","
                + "\"userid\": \"" + userid + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response :" + root.path("ERRORCODE").asText());
            response = root.path("ERRORCODE").asText();
        } else {
            response = root.path("ERRORCODE").asText();
        }
        return response;
    }

    public JsonNode HapusTindakan(String kodeProvider, String noSjp, String kodetindakan,
            String tgltindakan, String notes, String userid) throws FileNotFoundException, IOException {
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        String URL = prop.getProperty("URLAPIINHEALTH") + "/api/HapusTindakan";

        headers.add("Content-Type", "application/json; utf-8");
        requestJson = "{ \"token\": \"" + token + "\","
                + "\"kodeprovider\": \"" + kodeProvider + "\","
                + "\"nosjp\": \"" + noSjp + "\","
                + "\"kodetindakan\": \"" + kodetindakan + "\","
                + "\"tgltindakan\": \"" + tgltindakan + "\","
                + "\"notes\": \"" + notes + "\","
                + "\"userid\": \"" + userid + "\""
                + "}";

        System.out.println("Request: " + requestJson);
        HttpEntity requestEntity = new HttpEntity(requestJson, headers);
        RestTemplate rest = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Data : "+rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
        if (root.path("ERRORCODE").asText().equals("00")) {
            System.out.println("Response hapus tindakan :" + root.path("ERRORCODE").asText());
        } else {
            System.out.println("Response hapus tindakan :" + root.path("ERRORCODE").asText());
        }
        return root;
    }
}
