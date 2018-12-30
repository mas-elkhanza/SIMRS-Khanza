/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.sekuel;
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
    
    public DUKCAPILJakartaPostLahir(){
        super();
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
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            String URL = prop.getProperty("URLDUKCAPILJAKARTA")+"?usernm="+prop.getProperty("USERDUKCAPILJAKARTA")+"&pass="+prop.getProperty("PASSDUKCAPILJAKARTA")+"&app=SILaporLahir&pget=Kelahiran&pusr=admin&proc=POSTLAHIR&nokk="+nokk+"&nmbayi="+nmbayi+"&tgllhr="+tgllhr+"&jamlhr="+jamlhr+"&jk="+jk+"&jnslhr="+jnslhr+"&lahirke="+lahirke+"&brt="+brt+"&pjg="+pjg+"&pnlglhr="+pnlglhr+"&nikibu="+nikibu+"&nmibu="+nmibu+"&alamatibu="+alamatibu+"&kerjaibu="+kerjaibu+"&nikayah="+nikayah+"&nmayah="+nmayah+"&alamatayah="+alamatayah+"&kerjaayah="+kerjaayah+"&noskl="+noskl+"&pnlgnama="+pnlgnama+"&tindaklhr="+tindaklhr+"&bpjsibu="+bpjsibu+"&bpjsayah="+bpjsayah+"&notlp="+notlp+"&bpjsby="+bpjsby+"&nikplpr="+nikplpr+"&nmplpr="+nmplpr+"&almtplpr="+almtplpr+"&krjplpr="+krjplpr+"&niks1="+niks1+"&nms1="+nms1+"&almts1="+almts1+"&krjs1="+krjs1+"&niks2="+niks2+"&nms2="+nms2+"&almts2="+almts2+"&krjs2="+krjs2+"&umribu="+umribu+"&umrayah="+umrayah+"&umrplpr="+umrplpr+"&umrs1="+umrs1+"&umrs2="+umrs2+"&pkey="+Sequel.cariIsi("select md5(concat('"+prop.getProperty("VAR1DUKCAPILJAKARTA")+"',md5(date_format(current_date(),'%d%m%Y')),'"+prop.getProperty("VAR2DUKCAPILJAKARTA")+"'))");	

	    HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
	    HttpEntity requestEntity = new HttpEntity(headers);
	    RestTemplate rest = new RestTemplate();            
            System.out.println(URL);
            String data=rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody();
            JSONObject xmlJSONObj = XML.toJSONObject(data);
            String jsonPrettyPrintString = xmlJSONObj.toString(4);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonPrettyPrintString);
            JsonNode nameNode = root.path("DATA");
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
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
        
        return status;
    }
    
}
