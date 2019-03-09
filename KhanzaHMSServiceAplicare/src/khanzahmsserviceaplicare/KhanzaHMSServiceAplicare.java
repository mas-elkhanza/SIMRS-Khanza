/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanzahmsserviceaplicare;

import fungsi.BPJSApiAplicare;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.Timer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author windiartonugroho
 */
public class KhanzaHMSServiceAplicare {
    private static Properties prop = new Properties();
    private static Connection koneksi=koneksiDB.condb();
    private static sekuel Sequel=new sekuel();
    private static String requestJson,URL="",kodeppk=Sequel.cariIsi("select kode_ppk from setting");
    private static BPJSApiAplicare api=new BPJSApiAplicare();
    private static HttpHeaders headers;
    private static HttpEntity requestEntity;
    private static ObjectMapper mapper= new ObjectMapper();
    private static JsonNode root;
    private static JsonNode nameNode;
    private static JsonNode response;
    private static PreparedStatement ps;
    private static ResultSet rs;

    /**
     * @param args the command line arguments
     */
    
    public KhanzaHMSServiceAplicare(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            URL = prop.getProperty("URLAPIAPLICARE");	
        } catch (Exception e) {
            System.out.println("E : "+e);
        }
    }
    
    public static void main(String[] args) {
        jam();
    }
    
    private static void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                Date now = Calendar.getInstance().getTime();
                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                nilai_jam = now.getHours();
                nilai_menit = now.getMinutes();
                nilai_detik = now.getSeconds();
                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                if(jam.equals("01")||jam.equals("03")||jam.equals("05")||jam.equals("07")||jam.equals("09")||jam.equals("11")||jam.equals("13")||jam.equals("15")||jam.equals("17")||jam.equals("19")||jam.equals("21")||jam.equals("23")){
                    try {
                        ps=koneksi.prepareStatement(
                                "select aplicare_ketersediaan_kamar.kode_kelas_aplicare,aplicare_ketersediaan_kamar.kd_bangsal," +
                                "bangsal.nm_bangsal,aplicare_ketersediaan_kamar.kelas,aplicare_ketersediaan_kamar.kapasitas," +
                                "aplicare_ketersediaan_kamar.tersedia,aplicare_ketersediaan_kamar.tersediapria," +
                                "aplicare_ketersediaan_kamar.tersediawanita,aplicare_ketersediaan_kamar.tersediapriawanita " +
                                "from aplicare_ketersediaan_kamar inner join bangsal on aplicare_ketersediaan_kamar.kd_bangsal=bangsal.kd_bangsal");
                        try {
                            rs=ps.executeQuery();
                            while(rs.next()){
                                try {     
                                    headers = new HttpHeaders();
                                    headers.setContentType(MediaType.APPLICATION_JSON);
                                    headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIAPLICARE"));
                                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                                    headers.add("X-Signature",api.getHmac());
                                    requestJson ="{\"kodekelas\":\""+rs.getString("kode_kelas_aplicare")+"\", "+
                                                  "\"koderuang\":\""+rs.getString("kd_bangsal")+"\","+ 
                                                  "\"namaruang\":\""+rs.getString("nm_bangsal")+"\","+ 
                                                  "\"kapasitas\":\""+Sequel.cariIsi("select count(kd_kamar) from kamar where statusdata='1' and kelas='"+rs.getString("kode_kelas_aplicare")+"' and kd_bangsal='"+rs.getString("kd_bangsal")+"'")+"\","+ 
                                                  "\"tersedia\":\""+Sequel.cariIsi("select count(kd_kamar) from kamar where statusdata='1' and kelas='"+rs.getString("kode_kelas_aplicare")+"' and kd_bangsal='"+rs.getString("kd_bangsal")+"' and status='KOSONG'")+"\","+
                                                  "\"tersediapria\":\""+rs.getString("tersediapria")+"\","+ 
                                                  "\"tersediawanita\":\""+rs.getString("tersediawanita")+"\","+ 
                                                  "\"tersediapriawanita\":\""+Sequel.cariIsi("select count(kd_kamar) from kamar where statusdata='1' and kelas='"+rs.getString("kode_kelas_aplicare")+"' and kd_bangsal='"+rs.getString("kd_bangsal")+"' and status='KOSONG'")+"\""+
                                                  "}";
                                    requestEntity = new HttpEntity(requestJson,headers);
                                    //System.out.println(rest.exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                                    root = mapper.readTree(api.getRest().exchange(URL+"/rest/bed/update/"+kodeppk, HttpMethod.POST, requestEntity, String.class).getBody());
                                    nameNode = root.path("metadata");
                                    //System.out.println("code : "+nameNode.path("code").asText());
                                    //System.out.println("message : "+nameNode.path("message").asText());
                                    response = root.path("response");
                                    System.out.println(nameNode.path("message").asText());
                                }catch (Exception ex) {
                                    System.out.println("Notifikasi Bridging : "+ex);
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Notif Ketersediaan : "+ex);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }
                    } catch (Exception ez) {
                        System.out.println("Notif : "+ez);
                    }
                }
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
}
