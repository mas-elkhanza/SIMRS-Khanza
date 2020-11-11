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
    HttpHeaders headers = new HttpHeaders();
    String token = prop.getProperty("TOKENINHEALTH");
    String response = null;

    public String SimpanTindakan(String kodeProvider, String jnsPelayanan, String noSjp, String tglMasukRawat, String date, String kodeTindakan, String poli, String kodeDokter, String tarip) throws FileNotFoundException, IOException {
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

}
