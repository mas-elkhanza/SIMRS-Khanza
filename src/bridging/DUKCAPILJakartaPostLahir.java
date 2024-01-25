/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.io.FileInputStream;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author khanzasoft
 */
public class DUKCAPILJakartaPostLahir {
    private final Properties prop = new Properties();
    private final sekuel Sequel=new sekuel();
    private final Properties prop2 = new Properties();
    public String UID="";
    private boolean status=false;
    private String URL;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private RestTemplate rest = new RestTemplate();
    private String data;
    private JSONObject xmlJSONObj;
    private String jsonPrettyPrintString;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    
    public DUKCAPILJakartaPostLahir(){
        super();
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));            
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public boolean post(String nokk,String nmbayi,String tgllhr,String jamlhr,String jk,String jnslhr,String lahirke,
                       String brt,String pjg,String pnlglhr,String nikibu,String nmibu,String alamatibu,String kerjaibu,
                       String nikayah,String nmayah,String alamatayah,String kerjaayah,String noskl,String pnlgnama,
                       String tindaklhr,String bpjsibu,String bpjsayah,String notlp,String bpjsby,String nikplpr,String nmplpr,
                       String almtplpr,String krjplpr,String niks1,String nms1,String almts1,String krjs1,String niks2,
                       String nms2,String almts2,String krjs2,String umribu,String umrayah,String umrplpr,String umrs1,
                       String umrs2) {
        status=false;
        try {
            URL = prop.getProperty("URLDUKCAPILJAKARTA")+"?usernm="+koneksiDB.USERDUKCAPILJAKARTA()+"&pass="+koneksiDB.PASSDUKCAPILJAKARTA()+"&app=SILaporLahir&pget=Kelahiran&pusr=admin&proc=POSTLAHIR&nokk="+nokk+"&nmbayi="+nmbayi+"&tgllhr="+tgllhr+"&jamlhr="+jamlhr+"&jk="+jk+"&jnslhr="+jnslhr+"&lahirke="+lahirke+"&brt="+brt+"&pjg="+pjg+"&pnlglhr="+pnlglhr+"&nikibu="+nikibu+"&nmibu="+nmibu+"&alamatibu="+alamatibu+"&kerjaibu="+kerjaibu+"&nikayah="+nikayah+"&nmayah="+nmayah+"&alamatayah="+alamatayah+"&kerjaayah="+kerjaayah+"&noskl="+noskl+"&pnlgnama="+pnlgnama+"&tindaklhr="+tindaklhr+"&bpjsibu="+bpjsibu+"&bpjsayah="+bpjsayah+"&notlp="+notlp+"&bpjsby="+bpjsby+"&nikplpr="+nikplpr+"&nmplpr="+nmplpr+"&almtplpr="+almtplpr+"&krjplpr="+krjplpr+"&niks1="+niks1+"&nms1="+nms1+"&almts1="+almts1+"&krjs1="+krjs1+"&niks2="+niks2+"&nms2="+nms2+"&almts2="+almts2+"&krjs2="+krjs2+"&umribu="+umribu+"&umrayah="+umrayah+"&umrplpr="+umrplpr+"&umrs1="+umrs1+"&umrs2="+umrs2+"&pkey="+Sequel.cariIsi("select md5(concat('"+prop.getProperty("VAR1DUKCAPILJAKARTA")+"',md5(date_format(current_date(),'%d%m%Y')),'"+prop.getProperty("VAR2DUKCAPILJAKARTA")+"'))");	
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
	    requestEntity = new HttpEntity(headers);
	    System.out.println(URL);
            data=rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody();
            xmlJSONObj = XML.toJSONObject(data);
            jsonPrettyPrintString = xmlJSONObj.toString(4);
            root = mapper.readTree(jsonPrettyPrintString);
            nameNode = root.path("DATA");
            //System.out.println(jsonPrettyPrintString);
            UID="";
            if(nameNode.path("STATUS").asText().equals("POST PERMOHONAN AKTA BERHASIL !")){
                UID=nameNode.path("UID").asText();
                status=true;
                JOptionPane.showMessageDialog(null,"POST PERMOHONAN AKTA BERHASIL !");
            }else{
                status=false;
                JOptionPane.showMessageDialog(null,nameNode.path("STATUS").asText());
            }                     
        } catch (Exception ex) {
            status=false;
            System.out.println("Notifikasi Peserta : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server Dukcapil terputus...!");
            }
        }
        
        return status;
    }
    
}
