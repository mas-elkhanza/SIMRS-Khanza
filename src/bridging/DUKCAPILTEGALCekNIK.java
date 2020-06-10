/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import AESsecurity.EnkripsiAES;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.sekuel;
import fungsi.akses;
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
public class DUKCAPILTEGALCekNIK {

    /**
     *
     */
    public String NO_KK="",

    /**
     *
     */
    NIK="",

    /**
     *
     */
    NAMA_LGKP="",

    /**
     *
     */
    KAB_NAME="",

    /**
     *
     */
    NO_RW="",

    /**
     *
     */
    KEC_NAME="",

    /**
     *
     */
    JENIS_PKRJN="",

    /**
     *
     */
    NO_RT="",

    /**
     *
     */
    ALAMAT="",

    /**
     *
     */
    TMPT_LHR="",

    /**
     *
     */
    NAMA_LGKP_IBU="",

    /**
     *
     */
    PROP_NAME="",

    /**
     *
     */
    KEL_NAME="",

    /**
     *
     */
    JENIS_KLMIN="",

    /**
     *
     */
    TGL_LHR="",

    /**
     *
     */
    GOL_DARAH="",

    /**
     *
     */
    requestJson="",

    /**
     *
     */
    stringbalik="";
    private final Properties prop = new Properties();
    private sekuel Sequel=new sekuel();
    private String URL;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private RestTemplate rest = new RestTemplate();	            
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;

    /**
     *
     */
    public DUKCAPILTEGALCekNIK(){
        super();
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            URL = EnkripsiAES.decrypt(prop.getProperty("URLDUKCAPILTEGAL"));
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    /**
     *
     * @param nik
     */
    public void tampil(String nik) {
        try {
            URL = EnkripsiAES.decrypt(prop.getProperty("URLDUKCAPILTEGAL"))+"?USER_ID="+EnkripsiAES.decrypt(prop.getProperty("USERDUKCAPILTEGAL"))
                    +"&PASSWORD="+EnkripsiAES.decrypt(prop.getProperty("PASSDUKCAPILTEGAL"))+"&NIK="+nik;
	    headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Accept","application/json");
          
	    requestEntity = new HttpEntity(headers);	    
            stringbalik=rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody();
            
            root = mapper.readTree(stringbalik);
            nameNode = root.path("content");
            if(nameNode.isArray()){
                for(JsonNode list:nameNode){                    
                    try {
                        if(list.path("RESPON").asText().equals("Data tidak ditemukan")){
                            JOptionPane.showMessageDialog(null,"Data Tidak Ditemukan");
                        }else if(list.path("RESPON").asText().equals("Login gagal")){
                            JOptionPane.showMessageDialog(null,"Login Gagal");
                        }else if(list.path("RESPON").asText().equals("Kuota akses hari ini telah habis")){
                            JOptionPane.showMessageDialog(null,"Kuota Akses Hari ini telah Habis");
                        }else{
                            NO_KK=list.path("NO_KK").asText();
                            NIK=list.path("NIK").asText();
                            NAMA_LGKP=list.path("NAMA_LGKP").asText();
                            KAB_NAME=list.path("KAB_NAME").asText();
                            NO_RW=list.path("NO_RW").asText();
                            KEC_NAME=list.path("KEC_NAME").asText();
                            JENIS_PKRJN=list.path("JENIS_PKRJN").asText();
                            NO_RT=list.path("NO_RT").asText();
                            ALAMAT=list.path("ALAMAT").asText();
                            TMPT_LHR=list.path("TMPT_LHR").asText();
                            NAMA_LGKP_IBU=list.path("NAMA_LGKP_IBU").asText();
                            PROP_NAME=list.path("PROP_NAME").asText();
                            KEL_NAME=list.path("KEL_NAME").asText();
                            JENIS_KLMIN=list.path("JENIS_KLMIN").asText();
                            TGL_LHR=list.path("TGL_LHR").asText();
                            GOL_DARAH=list.path("GOL_DARAH").asText();
                        }
                    } catch (Exception e) {
                        NO_KK="";
                        NIK="";
                        NAMA_LGKP="";
                        KAB_NAME="";
                        NO_RW="";
                        KEC_NAME="";
                        JENIS_PKRJN="";
                        NO_RT="";
                        ALAMAT="";
                        TMPT_LHR="";
                        NAMA_LGKP_IBU="";
                        PROP_NAME="";
                        KEL_NAME="";
                        JENIS_KLMIN="";
                        TGL_LHR="";
                        GOL_DARAH="";
                        
                        JOptionPane.showMessageDialog(null,e+" "+list.path("RESPON").asText());
                    }                            
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server Dukcapil terputus...!");
            }
        }
    }
    
}
