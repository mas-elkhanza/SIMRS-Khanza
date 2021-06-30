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
public class ApiLICA {
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String URL="",KEY="",requestJson="",requestJson2="",stringbalik="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private JsonNode root;
    private sekuel Sequel=new sekuel();
    private JsonNode response;
    private ObjectMapper mapper = new ObjectMapper();
    
    public ApiLICA(){
        super();
        try {
            URL = koneksiDB.HOSTWSLICA();
            KEY = koneksiDB.KEYWSLICA();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public void kirimRalan(String nopermintaan) {
        try {
             ps=koneksi.prepareStatement(
                    "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,pasien.tgl_lahir,pasien.jk,pasien.alamat,"+
                    "if(permintaan_lab.tgl_sampel='0000-00-00','',permintaan_lab.tgl_sampel) as tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,"+
                    "if(permintaan_lab.tgl_hasil='0000-00-00','',permintaan_lab.tgl_hasil) as tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli,pasien.no_tlp,penjab.png_jawab from permintaan_lab "+
                    "inner join reg_periksa inner join pasien inner join dokter inner join poliklinik inner join penjab "+
                    "on permintaan_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                    "and permintaan_lab.dokter_perujuk=dokter.kd_dokter and reg_periksa.kd_poli=poliklinik.kd_poli where permintaan_lab.noorder=?");
             try {
                ps.setString(1,nopermintaan);
                rs=ps.executeQuery();
                while(rs.next()){
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Content-Type","application/json;charset=UTF-8");
                    headers.add("x-api-key",KEY);
                    ps2=koneksi.prepareStatement(
                            "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                            "template_laboratorium.urut from permintaan_detail_permintaan_lab "+
                            "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                            "where permintaan_detail_permintaan_lab.noorder=? order by template_laboratorium.kd_jenis_prw,template_laboratorium.urut desc");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        requestJson2="";
                        while(rs2.next()){
                            requestJson2=
                                "{" +
                                    "\"id\": \""+rs2.getString("urut")+"\"," +
                                    "\"test_id\": \""+rs2.getString("id_template")+"\"," +
                                    "\"test_nm\": \""+rs2.getString("Pemeriksaan")+"\"," +
                                    "\"cito\": \"1\"" +
                                "},"+requestJson2;
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
                                    "\"demografi\": {" +
                                        "\"no_rkm_medis\": \""+rs.getString("no_rkm_medis")+"\"," +
                                        "\"nm_pasien\": \""+rs.getString("nm_pasien")+"\"," +
                                        "\"tgl_lahir\": \""+rs.getString("tgl_lahir")+"\"," +
                                        "\"jk\": \""+rs.getString("jk")+"\"," +
                                        "\"alamat\": \""+rs.getString("alamat")+"\"," +
                                        "\"no_telp\": \""+rs.getString("no_tlp")+"\"" +
                                    "}," +
                                    "\"transaksi\": {" +
                                        "\"no_order\": \""+rs.getString("noorder")+"\"," +
                                        "\"tgl_permintaan\": \""+rs.getString("tgl_permintaan")+"\"," +
                                        "\"jam_permintaan\": \""+rs.getString("jam_permintaan")+"\"," +
                                        "\"pembayaran\": \""+rs.getString("png_jawab")+"\"," +
                                        "\"ruangan\": \""+rs.getString("nm_poli")+"\"," +
                                        "\"jnsreg\": \"1\"," +
                                        "\"dokter\": \""+rs.getString("nm_dokter")+"\"" +
                                    "}," +
                                    "\"test\": ["+
                                        requestJson2+
                                    "]" +
                                "}"; 
                    System.out.println("JSON : "+requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);	    
                    stringbalik=getRest().exchange(URL+"/insert", HttpMethod.POST, requestEntity, String.class).getBody();
                    JOptionPane.showMessageDialog(null,stringbalik);
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server LICA terputus...!");
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
                JOptionPane.showMessageDialog(null,"Koneksi ke server LICA terputus...!");
            }
        }
    }
    
    public void kirimRanap(String nopermintaan) {
        try {
             ps=koneksi.prepareStatement(
                    "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,pasien.jk,pasien.alamat,"+
                    "if(permintaan_lab.tgl_sampel='0000-00-00','',permintaan_lab.tgl_sampel) as tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,"+
                    "if(permintaan_lab.tgl_hasil='0000-00-00','',permintaan_lab.tgl_hasil) as tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter,bangsal.nm_bangsal,pasien.no_tlp,penjab.png_jawab,pasien.tgl_lahir from permintaan_lab "+
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
                    headers.add("Content-Type","application/json;charset=UTF-8");
                    headers.add("x-api-key",KEY);
                    ps2=koneksi.prepareStatement(
                            "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                            "template_laboratorium.urut from permintaan_detail_permintaan_lab "+
                            "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                            "where permintaan_detail_permintaan_lab.noorder=? order by template_laboratorium.kd_jenis_prw,template_laboratorium.urut desc");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        requestJson2="";
                        while(rs2.next()){
                            requestJson2=
                                "{" +
                                    "\"id\": \""+rs2.getString("urut")+"\"," +
                                    "\"test_id\": \""+rs2.getString("id_template")+"\"," +
                                    "\"test_nm\": \""+rs2.getString("Pemeriksaan")+"\"," +
                                    "\"cito\": \"1\"" +
                                "},"+requestJson2;
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
                                    "\"demografi\": {" +
                                        "\"no_rkm_medis\": \""+rs.getString("no_rkm_medis")+"\"," +
                                        "\"nm_pasien\": \""+rs.getString("nm_pasien")+"\"," +
                                        "\"tgl_lahir\": \""+rs.getString("tgl_lahir")+"\"," +
                                        "\"jk\": \""+rs.getString("jk")+"\"," +
                                        "\"alamat\": \""+rs.getString("alamat")+"\"," +
                                        "\"no_telp\": \""+rs.getString("no_tlp")+"\"" +
                                    "}," +
                                    "\"transaksi\": {" +
                                        "\"no_order\": \""+rs.getString("noorder")+"\"," +
                                        "\"tgl_permintaan\": \""+rs.getString("tgl_permintaan")+"\"," +
                                        "\"jam_permintaan\": \""+rs.getString("jam_permintaan")+"\"," +
                                        "\"pembayaran\": \""+rs.getString("png_jawab")+"\"," +
                                        "\"ruangan\": \""+rs.getString("nm_bangsal")+"\"," +
                                        "\"jnsreg\": \"1\"," +
                                        "\"dokter\": \""+rs.getString("nm_dokter")+"\"" +
                                    "}," +
                                    "\"test\": ["+
                                        requestJson2+
                                    "]" +
                                "}"; 
                    System.out.println("JSON : "+requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);	    
                    stringbalik=getRest().exchange(URL+"/insert", HttpMethod.POST, requestEntity, String.class).getBody();
                    JOptionPane.showMessageDialog(null,stringbalik);
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server LICA terputus...!");
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
                JOptionPane.showMessageDialog(null,"Koneksi ke server LICA terputus...!");
            }
        }
    }
    
    public void ambil(String nopermintaan) {
        try{
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Content-Type","application/json;charset=UTF-8");
            headers.add("x-api-key",KEY);
            requestEntity = new HttpEntity(headers);
            stringbalik=getRest().exchange(URL+"/get/"+nopermintaan, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("JSON : "+stringbalik);
            root = mapper.readTree(stringbalik);
            response = root.path("tpas");
            Sequel.queryu("truncate table temporary_permintaan_lab");
            if(response.isArray()){
                for(JsonNode list:response){
                    Sequel.menyimpan("temporary_permintaan_lab","'0','"+root.path("id_kunjungan").asText()+"','"+
                            list.path("nmdisplay").asText()+"','"+
                            list.path("hasil").asText()+"','"+
                            list.path("nn").asText()+"','"+
                            list.path("satuan").asText()+"','"+
                            list.path("keterangan").asText()+"','"+
                            list.path("tindakan_id").asText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")||ex.toString().contains("404")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server LICA terputus...!");
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
