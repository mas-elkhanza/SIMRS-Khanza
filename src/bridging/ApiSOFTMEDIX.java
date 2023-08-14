/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.akses;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author khanzasoft
 */
public class ApiSOFTMEDIX {
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String URLAPISOFTMEDIX="",PRODUCTSOFTMEDIX="",VERSIONSOFTMEDIX="",USERIDSOFTMEDIX="",KEYSOFTMEDIX="",requestJson="",requestJson2="",stringbalik="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private JsonNode root;
    private sekuel Sequel=new sekuel();
    private JsonNode response;
    private ObjectMapper mapper = new ObjectMapper();
    private int i=0;
    
    public ApiSOFTMEDIX(){
        super();
        try {
            URLAPISOFTMEDIX = koneksiDB.URLAPISOFTMEDIX();
            PRODUCTSOFTMEDIX = koneksiDB.PRODUCTSOFTMEDIX();
            VERSIONSOFTMEDIX = koneksiDB.VERSIONSOFTMEDIX();
            USERIDSOFTMEDIX = koneksiDB.USERIDSOFTMEDIX();
            KEYSOFTMEDIX = koneksiDB.KEYSOFTMEDIX();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public void kirimRalan(String nopermintaan) {
        try {
             ps=koneksi.prepareStatement(
                    "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,date_format(permintaan_lab.tgl_permintaan,'%d.%m.%Y') as tgl_permintaan,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,date_format(pasien.tgl_lahir,'%d.%m.%Y') as tgl_lahir,pasien.jk,pasien.alamat,"+
                    "if(permintaan_lab.tgl_sampel='0000-00-00','',permintaan_lab.tgl_sampel) as tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,"+
                    "if(permintaan_lab.tgl_hasil='0000-00-00','',permintaan_lab.tgl_hasil) as tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli,pasien.no_tlp,penjab.png_jawab,reg_periksa.kd_pj,pasien.pekerjaan,reg_periksa.kd_poli,pasien.email,pasien.no_ktp "+
                    "from permintaan_lab inner join reg_periksa inner join pasien inner join dokter inner join poliklinik inner join penjab "+
                    "on permintaan_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                    "and permintaan_lab.dokter_perujuk=dokter.kd_dokter and reg_periksa.kd_poli=poliklinik.kd_poli where permintaan_lab.noorder=?");
             try {
                ps.setString(1,nopermintaan);
                rs=ps.executeQuery();
                while(rs.next()){
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    ps2=koneksi.prepareStatement(
                            "select permintaan_detail_permintaan_lab.id_template from permintaan_detail_permintaan_lab where permintaan_detail_permintaan_lab.noorder=? order by permintaan_detail_permintaan_lab.id_template desc");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        requestJson2="";
                        while(rs2.next()){
                            requestJson2=requestJson2+"\""+rs2.getString("id_template")+"\",";
                        }
                        if(requestJson2.endsWith(",")){
                            requestJson2 = requestJson2.substring(0,requestJson2.length() - 1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 3 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    requestJson="{" +
                                    "\"order\": {" +
                                        "\"msh\": {" +
                                            "\"product\": \""+PRODUCTSOFTMEDIX+"\","+
                                            "\"version\": \""+VERSIONSOFTMEDIX+"\","+
                                            "\"user_id\": \""+USERIDSOFTMEDIX+"\","+
                                            "\"key\": \""+KEYSOFTMEDIX+"\"" +
                                        "}, "+
                                        "\"pid\": {" +
                                            "\"pmrn\": \""+rs.getString("no_rkm_medis")+"\"," +
                                            "\"pname\": \""+rs.getString("nm_pasien")+"\"," +
                                            "\"sex\": \""+rs.getString("jk")+"\"," +
                                            "\"birth_dt\": \""+rs.getString("tgl_lahir")+"\","+
                                            "\"address\": \""+rs.getString("alamat")+"\","+
                                            "\"no_tlp\": \""+rs.getString("no_tlp")+"\"," +
                                            "\"no_hp\": \""+rs.getString("no_tlp")+"\"," +
                                            "\"email\": \""+rs.getString("email")+"\"," +
                                            "\"nik\": \""+rs.getString("no_ktp")+"\"" +
                                        "}, "+
                                        "\"obr\": {" +
                                            "\"order_control\": \"N\"," +
                                            "\"ptype\": \"OP\"," +
                                            "\"reg_no\": \""+rs.getString("no_rawat")+"\"," +
                                            "\"order_lab\": \""+rs.getString("noorder")+"\"," +
                                            "\"provider_id\": \""+rs.getString("kd_pj")+"\"," +
                                            "\"provider_name\": \""+rs.getString("png_jawab")+"^"+rs.getString("pekerjaan")+"\","+
                                            "\"order_date\": \""+rs.getString("tgl_permintaan")+" "+rs.getString("jam_permintaan")+"\","+
                                            "\"clinician_id\": \""+rs.getString("dokter_perujuk")+"\"," +
                                            "\"clinician_name\": \""+rs.getString("nm_dokter")+"\","+
                                            "\"bangsal_id\": \""+rs.getString("kd_poli")+"\"," +
                                            "\"bangsal_name\": \""+rs.getString("nm_poli")+"\", "+
                                            "\"bed_id\": \"0000\","+
                                            "\"bed_name\": \"0000\","+
                                            "\"class_id\": \"0\","+
                                            "\"class_name\": \"0\","+
                                            "\"cito\": \"N\"," +
                                            "\"med_legal\": \"N\","+
                                            "\"user_id\": \""+akses.getkode()+"\","+
                                            "\"reserve1\": \"\","+
                                            "\"reserve2\": \"\","+
                                            "\"reserve3\": \"\","+
                                            "\"reserve4\": \"\","+
                                            "\"order_test\": [" +
                                                requestJson2+
                                            "]"+
                                        "}" +
                                    "}"+
                                "}"; 
                    System.out.println("JSON : "+requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);	
                    System.out.println("URL : "+URLAPISOFTMEDIX+"/order");
                    stringbalik=getRest().exchange(URLAPISOFTMEDIX+"/order", HttpMethod.POST, requestEntity, String.class).getBody();
                    JOptionPane.showMessageDialog(null,stringbalik);
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server SOFTMEDIX terputus...!");
                 }
             } finally{
                 if(rs!=null){
                     rs.close();
                 }
                 if(ps!=null){
                     ps.close();
                 }
             }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")||ex.toString().contains("404")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server SOFTMEDIX terputus...!");
            }
        }
    }
    
    public void kirimRanap(String nopermintaan) {
        try {
             ps=koneksi.prepareStatement(
                    "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,date_format(permintaan_lab.tgl_permintaan,'%d.%m.%Y') as tgl_permintaan,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,pasien.jk,pasien.alamat,"+
                    "if(permintaan_lab.tgl_sampel='0000-00-00','',permintaan_lab.tgl_sampel) as tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,"+
                    "if(permintaan_lab.tgl_hasil='0000-00-00','',permintaan_lab.tgl_hasil) as tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter,bangsal.nm_bangsal,pasien.no_tlp,penjab.png_jawab,date_format(pasien.tgl_lahir,'%d.%m.%Y') as tgl_lahir,reg_periksa.kd_pj,pasien.pekerjaan,kamar.kd_bangsal,"+
                    "kamar_inap.kd_kamar,kamar.kelas,pasien.email,pasien.no_ktp from permintaan_lab "+
                    "inner join reg_periksa inner join pasien inner join dokter inner join bangsal inner join kamar inner join kamar_inap inner join penjab  "+
                    "on permintaan_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                    "and permintaan_lab.dokter_perujuk=dokter.kd_dokter and kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar where "+
                    "permintaan_lab.noorder=?");
             try {
                ps.setString(1,nopermintaan);
                rs=ps.executeQuery();
                while(rs.next()){
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    ps2=koneksi.prepareStatement(
                            "select permintaan_detail_permintaan_lab.id_template from permintaan_detail_permintaan_lab where permintaan_detail_permintaan_lab.noorder=? order by permintaan_detail_permintaan_lab.id_template desc");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        requestJson2="";
                        while(rs2.next()){
                            requestJson2=requestJson2+"\""+rs2.getString("id_template")+"\",";
                        }
                        if(requestJson2.endsWith(",")){
                            requestJson2 = requestJson2.substring(0,requestJson2.length() - 1);
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 3 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    requestJson="{" +
                                    "\"order\": {" +
                                        "\"msh\": {" +
                                            "\"product\": \""+PRODUCTSOFTMEDIX+"\","+
                                            "\"version\": \""+VERSIONSOFTMEDIX+"\","+
                                            "\"user_id\": \""+USERIDSOFTMEDIX+"\","+
                                            "\"key\": \""+KEYSOFTMEDIX+"\"" +
                                        "}, "+
                                        "\"pid\": {" +
                                            "\"pmrn\": \""+rs.getString("no_rkm_medis")+"\"," +
                                            "\"pname\": \""+rs.getString("nm_pasien")+"\"," +
                                            "\"sex\": \""+rs.getString("jk")+"\"," +
                                            "\"birth_dt\": \""+rs.getString("tgl_lahir")+"\","+
                                            "\"address\": \""+rs.getString("alamat")+"\","+
                                            "\"no_tlp\": \""+rs.getString("no_tlp")+"\"," +
                                            "\"no_hp\": \""+rs.getString("no_tlp")+"\"," +
                                            "\"email\": \""+rs.getString("email")+"\"," +
                                            "\"nik\": \""+rs.getString("no_ktp")+"\"" +
                                        "}, "+
                                        "\"obr\": {" +
                                            "\"order_control\": \"N\"," +
                                            "\"ptype\": \"OP\"," +
                                            "\"reg_no\": \""+rs.getString("no_rawat")+"\"," +
                                            "\"order_lab\": \""+rs.getString("noorder")+"\"," +
                                            "\"provider_id\": \""+rs.getString("kd_pj")+"\"," +
                                            "\"provider_name\": \""+rs.getString("png_jawab")+"^"+rs.getString("pekerjaan")+"\","+
                                            "\"order_date\": \""+rs.getString("tgl_permintaan")+" "+rs.getString("jam_permintaan")+"\","+
                                            "\"clinician_id\": \""+rs.getString("dokter_perujuk")+"\"," +
                                            "\"clinician_name\": \""+rs.getString("nm_dokter")+"\","+
                                            "\"bangsal_id\": \""+rs.getString("kd_bangsal")+"\"," +
                                            "\"bangsal_name\": \""+rs.getString("nm_bangsal")+"\", "+
                                            "\"bed_id\": \""+rs.getString("kd_kamar")+"\","+
                                            "\"bed_name\": \""+rs.getString("kd_kamar")+" "+rs.getString("nm_bangsal")+"\","+
                                            "\"class_id\": \""+rs.getString("kelas")+"\","+
                                            "\"class_name\": \""+rs.getString("kelas")+"\","+
                                            "\"cito\": \"N\"," +
                                            "\"med_legal\": \"N\","+
                                            "\"user_id\": \""+akses.getkode()+"\","+
                                            "\"reserve1\": \"\","+
                                            "\"reserve2\": \"\","+
                                            "\"reserve3\": \"\","+
                                            "\"reserve4\": \"\","+
                                            "\"order_test\": [" +
                                                requestJson2+
                                            "]"+
                                        "}" +
                                    "}"+
                                "}"; 
                    System.out.println("JSON : "+requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);	
                    System.out.println("URL : "+URLAPISOFTMEDIX+"/order");
                    stringbalik=getRest().exchange(URLAPISOFTMEDIX+"/order", HttpMethod.POST, requestEntity, String.class).getBody();
                    JOptionPane.showMessageDialog(null,stringbalik);
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server SOFTMEDIX terputus...!");
                 }
             } finally{
                 if(rs!=null){
                     rs.close();
                 }
                 if(ps!=null){
                     ps.close();
                 }
             }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")||ex.toString().contains("404")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server SOFTMEDIX terputus...!");
            }
        }
    }
    
    public void ambil(String nopermintaan) {
        try{
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            requestEntity = new HttpEntity(headers);
            stringbalik=getRest().exchange(URLAPISOFTMEDIX+"/result/"+USERIDSOFTMEDIX+"/"+KEYSOFTMEDIX+"/"+nopermintaan, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("JSON : "+stringbalik);
            root = mapper.readTree(stringbalik);
            response = root.path("response").path("sampel").path("result_test");
            Sequel.queryu("delete from temporary_permintaan_lab where temp37='"+akses.getalamatip()+"'");
            if(response.isArray()){
                i=0;
                System.out.println("Proses Ambil Data Soft Medix : ");
                for(JsonNode list:response){
                    System.out.println(i+" "+root.path("result").path("obx").path("order_lab").asText()+" | "+list.path("nama_test").asText()+
                            " | "+list.path("hasil").asText()+" | "+list.path("nilai_normal").asText()+" | "+list.path("satuan").asText()+
                            " | "+list.path("flag").asText().replaceAll("null","")+" | "+list.path("test_id").asText());
                    Sequel.menyimpan("temporary_permintaan_lab","'"+i+"','"+root.path("result").path("obx").path("order_lab").asText()+"','"+
                            list.path("nama_test").asText()+"','"+
                            list.path("hasil").asText()+"','"+
                            list.path("nilai_normal").asText()+"','"+
                            list.path("satuan").asText()+"','"+
                            list.path("flag").asText().replaceAll("null","")+"','"+
                            list.path("test_id").asText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Periksa Lab"); 
                    i++;
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")||ex.toString().contains("404")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server SOFTMEDIX terputus...!");
            }
        }
    }
    
    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        SSLSocketFactory sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme=new Scheme("https",443,sslFactory);
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory();
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }
    
}
