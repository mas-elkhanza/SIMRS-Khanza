/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import fungsi.sekuel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
public class DUKCAPILJakartaCekNik {
    public String nokartu="",nama="",pekerjaan="",tgl_lahir="",jk="",umur="";
    private final Properties prop = new Properties();
    private final sekuel Sequel=new sekuel();
    private final Properties prop2 = new Properties();
    
    public DUKCAPILJakartaCekNik(){
        super();
    }
    
    public void tampil(String nik) {
        BPJSApi api=new BPJSApi();
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            String URL = prop.getProperty("URLDUKCAPILJAKARTA")+"?usernm="+prop.getProperty("USERDUKCAPILJAKARTA")+"&pass="+prop.getProperty("PASSDUKCAPILJAKARTA")+"&app=SILaporLahir&pget=Kelahiran&pusr=admin&proc=GETNIK&nik="+nik+"&pkey="+Sequel.cariIsi("select md5(concat('"+prop.getProperty("VAR1DUKCAPILJAKARTA")+"',md5(date_format(current_date(),'%d%m%Y')),'"+prop.getProperty("VAR2DUKCAPILJAKARTA")+"'))");	

	    HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
	    HttpEntity requestEntity = new HttpEntity(headers);
	    RestTemplate rest = new RestTemplate();
            String data=rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody();
            
            System.out.println("data : "+data);
            File g = new File("nik.xml");            
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(data);
            bg.close();
            
            prop.loadFromXML(new FileInputStream("nik.xml"));
            
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
}
