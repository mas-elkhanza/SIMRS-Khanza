/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
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
public class ApiCareStream {
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private String URL="",requestJson="",stringbalik="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private JsonNode root;
    private JsonNode response;
    private ObjectMapper mapper = new ObjectMapper();
    
    public ApiCareStream(){
        super();
        try {
            URL = koneksiDB.URLCARESTREAM();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public void kirimRalan(String nopermintaan) {
        try {
             ps=koneksi.prepareStatement(
                    "select permintaan_radiologi.noorder,permintaan_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_radiologi.tgl_permintaan,"+
                    "if(permintaan_radiologi.jam_permintaan='00:00:00','',permintaan_radiologi.jam_permintaan) as jam_permintaan,pasien.tgl_lahir,pasien.jk,pasien.alamat,"+
                    "if(permintaan_radiologi.tgl_sampel='0000-00-00','',permintaan_radiologi.tgl_sampel) as tgl_sampel,if(permintaan_radiologi.jam_sampel='00:00:00','',permintaan_radiologi.jam_sampel) as jam_sampel,"+
                    "if(permintaan_radiologi.tgl_hasil='0000-00-00','',permintaan_radiologi.tgl_hasil) as tgl_hasil,if(permintaan_radiologi.jam_hasil='00:00:00','',permintaan_radiologi.jam_hasil) as jam_hasil,"+
                    "permintaan_radiologi.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli,pasien.no_tlp,penjab.png_jawab,permintaan_pemeriksaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                    "permintaan_radiologi.diagnosa_klinis,permintaan_radiologi.informasi_tambahan from permintaan_radiologi "+
                    "inner join reg_periksa on permintaan_radiologi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on permintaan_radiologi.dokter_perujuk=dokter.kd_dokter "+
                    "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "inner join permintaan_pemeriksaan_radiologi on permintaan_pemeriksaan_radiologi.noorder=permintaan_radiologi.noorder "+
                    "inner join jns_perawatan_radiologi on permintaan_pemeriksaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                    "where permintaan_radiologi.noorder=?");
             try {
                ps.setString(1,nopermintaan);
                rs=ps.executeQuery();
                while(rs.next()){
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Content-Type","application/json;charset=UTF-8");
                    requestJson="{" +
                                    "\"Order\":{"+
                                        "\"patient\": {" +
                                            "\"id\": \""+rs.getString("no_rkm_medis")+"\"," +
                                            "\"first_name\": \""+rs.getString("nm_pasien")+"\"," +
                                            "\"middle_name\": \"\"," +
                                            "\"last_name\": \"\"," +
                                            "\"sex\": \""+rs.getString("jk").replaceAll("L","M").replaceAll("P","F")+"\"," +
                                            "\"birthDate\": \""+rs.getString("tgl_lahir")+"\"," +
                                            "\"phone\": \""+rs.getString("no_tlp")+"\"," +
                                            "\"address\": \""+rs.getString("alamat")+"\"," +
                                            "\"height\": \"\"," +
                                            "\"weight\": \"\"," +
                                            "\"priority\": \""+(rs.getString("informasi_tambahan").toLowerCase().contains("cito")?"CITO":"")+"\"," +
                                            "\"department\": \""+rs.getString("nm_poli")+"\"" +
                                        "}," +
                                        "\"order\": {" +
                                            "\"id\": \""+rs.getString("noorder")+"\"," +
                                            "\"serviceCode\": \""+rs.getString("kd_jenis_prw")+"\"," +
                                            "\"serviceName\": \""+rs.getString("nm_perawatan")+"\"," +
                                            "\"status\": \"NEW\"," +
                                            "\"orderDate\": \""+rs.getString("tgl_permintaan")+" "+rs.getString("jam_permintaan")+"\"," +
                                            "\"doctor\": \""+rs.getString("nm_dokter")+"\"," +
                                            "\"modality\": \""+rs.getString("kd_jenis_prw").substring(0,2)+"\"," +
                                            "\"clinicalDiagnosis\": \""+rs.getString("diagnosa_klinis")+"\"" +
                                        "}" +
                                    "}"+
                                "}"; 
                    System.out.println("JSON : "+requestJson);
                    System.out.println("URL : "+URL+"/putOrder/");
                    requestEntity = new HttpEntity(requestJson,headers);	    
                    stringbalik=getRest().exchange(URL+"/putOrder/", HttpMethod.POST, requestEntity, String.class).getBody();
                    JOptionPane.showMessageDialog(null,stringbalik);
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server Care Stream terputus...!");
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
                JOptionPane.showMessageDialog(null,"Koneksi ke server Care Stream terputus...!");
            }
        }
    }
    
    public void kirimRanap(String nopermintaan) {
        try {
             ps=koneksi.prepareStatement(
                    "select permintaan_radiologi.noorder,permintaan_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_radiologi.tgl_permintaan,"+
                    "if(permintaan_radiologi.jam_permintaan='00:00:00','',permintaan_radiologi.jam_permintaan) as jam_permintaan,pasien.jk,pasien.alamat,"+
                    "if(permintaan_radiologi.tgl_sampel='0000-00-00','',permintaan_radiologi.tgl_sampel) as tgl_sampel,if(permintaan_radiologi.jam_sampel='00:00:00','',permintaan_radiologi.jam_sampel) as jam_sampel,"+
                    "if(permintaan_radiologi.tgl_hasil='0000-00-00','',permintaan_radiologi.tgl_hasil) as tgl_hasil,if(permintaan_radiologi.jam_hasil='00:00:00','',permintaan_radiologi.jam_hasil) as jam_hasil,"+
                    "permintaan_radiologi.dokter_perujuk,dokter.nm_dokter,bangsal.nm_bangsal,pasien.no_tlp,penjab.png_jawab,pasien.tgl_lahir,permintaan_pemeriksaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                    "permintaan_radiologi.diagnosa_klinis,permintaan_radiologi.informasi_tambahan from permintaan_radiologi "+
                    "inner join reg_periksa on permintaan_radiologi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on permintaan_radiologi.dokter_perujuk=dokter.kd_dokter "+
                    "inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat "+
                    "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "inner join permintaan_pemeriksaan_radiologi on permintaan_pemeriksaan_radiologi.noorder=permintaan_radiologi.noorder "+
                    "inner join jns_perawatan_radiologi on permintaan_pemeriksaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                    "where permintaan_radiologi.noorder=?");
             try {
                ps.setString(1,nopermintaan);
                rs=ps.executeQuery();
                while(rs.next()){
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Content-Type","application/json;charset=UTF-8");
                    requestJson="{" +
                                    "\"Order\":{"+
                                        "\"patient\": {" +
                                            "\"id\": \""+rs.getString("no_rkm_medis")+"\"," +
                                            "\"first_name\": \""+rs.getString("nm_pasien")+"\"," +
                                            "\"middle_name\": \"\"," +
                                            "\"last_name\": \"\"," +
                                            "\"sex\": \""+rs.getString("jk").replaceAll("L","M").replaceAll("P","F")+"\"," +
                                            "\"birthDate\": \""+rs.getString("tgl_lahir")+"\"," +
                                            "\"phone\": \""+rs.getString("no_tlp")+"\"," +
                                            "\"address\": \""+rs.getString("alamat")+"\"," +
                                            "\"height\": \"\"," +
                                            "\"weight\": \"\"," +
                                            "\"priority\": \""+(rs.getString("informasi_tambahan").toLowerCase().contains("cito")?"CITO":"")+"\"," +
                                            "\"department\": \""+rs.getString("nm_bangsal")+"\"" +
                                        "}," +
                                        "\"order\": {" +
                                            "\"id\": \""+rs.getString("noorder")+"\"," +
                                            "\"serviceCode\": \""+rs.getString("kd_jenis_prw")+"\"," +
                                            "\"serviceName\": \""+rs.getString("nm_perawatan")+"\"," +
                                            "\"status\": \"NEW\"," +
                                            "\"orderDate\": \""+rs.getString("tgl_permintaan")+" "+rs.getString("jam_permintaan")+"\"," +
                                            "\"doctor\": \""+rs.getString("nm_dokter")+"\"," +
                                            "\"modality\": \""+rs.getString("kd_jenis_prw").substring(0,2)+"\"," +
                                            "\"clinicalDiagnosis\": \""+rs.getString("diagnosa_klinis")+"\"" +
                                        "}" +
                                    "}"+
                                "}"; 
                    System.out.println("JSON : "+requestJson);
                    System.out.println("URL : "+URL+"/putOrder/");
                    requestEntity = new HttpEntity(requestJson,headers);	    
                    stringbalik=getRest().exchange(URL+"/putOrder/", HttpMethod.POST, requestEntity, String.class).getBody();
                    JOptionPane.showMessageDialog(null,stringbalik);
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server Care Stream terputus...!");
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
                JOptionPane.showMessageDialog(null,"Koneksi ke server Care Stream terputus...!");
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
