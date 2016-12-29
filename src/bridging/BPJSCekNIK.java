/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author khanzasoft
 */
public class BPJSCekNIK {
    public String nokartu="",nama="",pekerjaan="",tgl_lahir="",jk="",umur="";
    private final Properties prop = new Properties();
    
    public BPJSCekNIK(){
        super();
    }
    
    public void tampil(String nik) {
        BPJSApi api=new BPJSApi();
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            String URL = prop.getProperty("URLAPIBPJS")+"/Peserta/Peserta/nik/"+nik;	

	    HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
	    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
	    headers.add("X-Signature",api.getHmac());
	    HttpEntity requestEntity = new HttpEntity(headers);
	    RestTemplate rest = new RestTemplate();	
            
            //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metadata");
            //System.out.println("code : "+nameNode.path("code").asText());
            //System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("message").asText().equals("OK")){
                JsonNode response = root.path("response");
                nokartu=response.path("peserta").path("noKartu").asText();
                nama=response.path("peserta").path("nama").asText();
                pekerjaan=response.path("peserta").path("jenisPeserta").path("nmJenisPeserta").asText();
                tgl_lahir=response.path("peserta").path("tglLahir").asText();
                jk=response.path("peserta").path("sex").asText().replaceAll("L","LAKI-LAKI").replaceAll("P","PEREMPUAN");
                try {
                    umur=response.path("peserta").path("umur").path("umurSekarang").asText().substring(0,2);
                } catch (Exception e) {
                    umur="0";
                }
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }    
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
}
