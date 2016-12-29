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
public class IBacorCekNIK {
    public String nama="",kelamin="",kelurahan="",kecamatan="",kabupaten_kota="",provinsi="";
    
    public IBacorCekNIK(){
        super();
    }
    
    public void tampil(String nik) {
        BPJSApi api=new BPJSApi();
        try {
            String URL = "http://ibacor.com/api/ktp?nik="+nik+"&k=dc58a8ddd0056f39162a5c23cf26b998";	

	    HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Cons-ID","");
	    headers.add("X-Timestamp","");            
	    headers.add("X-Signature","");
	    HttpEntity requestEntity = new HttpEntity(headers);
	    RestTemplate rest = new RestTemplate();	
            
            //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            System.out.println("status : "+root.path("status").asText());
            System.out.println("pesan : "+root.path("pesan").asText());
            if(root.path("pesan").asText().equals("data tidak ada")){
                JOptionPane.showMessageDialog(null,"Peserta Tidak Ditemukan");
            }else if(root.path("pesan").asText().equals("data ada")){
                JsonNode response = root.path("data");
                nama=response.path("nama").asText();
                kelamin=response.path("kelamin").asText().replaceAll("laki-laki","LAKI-LAKI").replaceAll("perempuan","PEREMPUAN");
                kelurahan=response.path("kelurahan").asText();
                kecamatan=response.path("kecamatan").asText();
                kabupaten_kota=response.path("kabupaten_kota").asText();
                provinsi=response.path("provinsi").asText();
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke IBacor terputus...!");
            }
        }
    }
    
}
