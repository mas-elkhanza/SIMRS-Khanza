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
public class DUKCAPILAcehCekNIK {
    public String EKTP_STATUS="",NO_KK="",NIK="",NAMA_LGKP="",KAB_NAME="",AGAMA="",
            NO_RW="",KEC_NAME="",JENIS_PKRJN="",NO_RT="",NO_KEL="",ALAMAT="",NO_KEC="",
            TMPT_LHR="",PDDK_AKH="",STATUS_KAWIN="",NO_PROP="",NAMA_LGKP_IBU="",
            PROP_NAME="",NO_KAB="",KEL_NAME="",JENIS_KLMIN="",TGL_LHR="",requestJson="";
    private final Properties prop = new Properties();
    
    public DUKCAPILAcehCekNIK(){
        super();
    }
    
    public void tampil(String nik) {
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            String URL = prop.getProperty("URLDUKCAPILACEH")+"/CALL_NIK";	

	    HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Accept","application/json");
            requestJson="{"+
                          "\"nik\": \""+nik+"\"," +
                            "\"user_id\" : \""+prop.getProperty("USERDUKCAPILACEH")+"\"," +
                            "\"password\": \""+prop.getProperty("PASSDUKCAPILACEH")+"\"," +
                            "\"IP_USER\":\""+prop.getProperty("IPUSERDUKCAPILACEH")+"\"" +
                            "}"; 
	    HttpEntity requestEntity = new HttpEntity(requestJson,headers);
	    RestTemplate rest = new RestTemplate();	
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("content");
            if(nameNode.isArray()){
                for(JsonNode list:nameNode){                    
                    try {
                        if(list.path("RESPON").asText().equals("Data Tidak Ditemukan")){
                            JOptionPane.showMessageDialog(null,"Data Tidak Ditemukan");
                        }else if(list.path("RESPON").asText().equals("Login Gagal")){
                            JOptionPane.showMessageDialog(null,"Login Gagal");
                        }else if(list.path("RESPON").asText().equals("IP Address Tidak Sesuai")){
                            JOptionPane.showMessageDialog(null,"IP Address Tidak Sesuai");
                        }else if(list.path("RESPON").asText().equals("IP Client  Tidak terdaftar")){
                            JOptionPane.showMessageDialog(null,"IP Client  Tidak terdaftar");
                        }else{
                            EKTP_STATUS=list.path("EKTP_STATUS").asText();
                            NO_KK=list.path("").asText();
                            NIK=list.path("").asText();
                            NAMA_LGKP=list.path("").asText();
                            KAB_NAME=list.path("").asText();
                            AGAMA=list.path("").asText();
                            NO_RW=list.path("").asText();
                            KEC_NAME=list.path("").asText();
                            JENIS_PKRJN=list.path("").asText();
                            NO_RT=list.path("").asText();
                            NO_KEL=list.path("").asText();
                            ALAMAT=list.path("").asText();
                            NO_KEC=list.path("").asText();
                            TMPT_LHR=list.path("").asText();
                            PDDK_AKH=list.path("").asText();
                            STATUS_KAWIN=list.path("").asText();
                            NO_PROP=list.path("").asText();
                            NAMA_LGKP_IBU=list.path("").asText();
                            PROP_NAME=list.path("").asText();
                            NO_KAB=list.path("").asText();
                            KEL_NAME=list.path("").asText();
                            JENIS_KLMIN=list.path("").asText();
                            TGL_LHR=list.path("").asText();
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,e+" "+list.path("RESPON").asText());
                    }                            
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
    
}
