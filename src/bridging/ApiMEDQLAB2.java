package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;

public class ApiMEDQLAB2 {        
    private Connection koneksi=koneksiDB.condb();
    private String Consid,Secretkey;
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private String URL="",requestJson="",requestJson2="",kodeicd="",namaicd="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private JsonNode root;
    private sekuel Sequel=new sekuel();
    private JsonNode response;
    private ObjectMapper mapper = new ObjectMapper();
    
    public ApiMEDQLAB2(){
        try {                       
            Secretkey = koneksiDB.SECRETKEYAPIMEDQLAB();
            Consid = koneksiDB.CONSIDAPIMEDQLAB();       
            URL = koneksiDB.URLAPIMEDQLAB();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
    public String getSignature() {        
        String generateHmacSHA256Signature = null;
	try {
	    generateHmacSHA256Signature = generateHmacSHA256Signature(Consid,Secretkey);
	} catch (GeneralSecurityException e) {
	    System.out.println("Error Signature : "+e);
	    e.printStackTrace();
	}
	return generateHmacSHA256Signature;
    }

    public String generateHmacSHA256Signature(String data, String key)throws GeneralSecurityException {
	byte[] hmacData = null;

	try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"),"HmacSHA256");
	    Mac mac = Mac.getInstance("HmacSHA256");
	    mac.init(secretKey);
	    hmacData = mac.doFinal(data.getBytes("UTF-8"));
	    return new String(Base64.encode(hmacData), "UTF-8");
	} catch (UnsupportedEncodingException e) {
            System.out.println("Error Generate HMac: e");
	    throw new GeneralSecurityException(e);
	}
    }
        
    public long GetUTCdatetimeAsString(){    
        long millis = System.currentTimeMillis();   
        return millis/1000;
    }
    
    public void kirim(String nopermintaan) {
        try {
             ps=koneksi.prepareStatement(
                    "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,reg_periksa.kd_pj,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,pasien.jk,pasien.alamat,"+
                    "if(permintaan_lab.tgl_sampel='0000-00-00','',permintaan_lab.tgl_sampel) as tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,"+
                    "if(permintaan_lab.tgl_hasil='0000-00-00','',permintaan_lab.tgl_hasil) as tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter,bangsal.nm_bangsal,pasien.no_tlp,penjab.png_jawab,pasien.tgl_lahir,pasien.tmp_lahir,kamar_inap.kd_kamar,"+
                    "permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis from permintaan_lab "+
                    "inner join reg_periksa inner join pasien inner join dokter inner join bangsal inner join kamar inner join kamar_inap inner join penjab  "+
                    "on permintaan_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                    "and permintaan_lab.dokter_perujuk=dokter.kd_dokter and kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar where "+
                    "permintaan_lab.noorder=?");
             try {
                ps.setString(1,nopermintaan);
                rs=ps.executeQuery();
                if(rs.next()){
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("X-cons",Consid);
                    headers.add("X-key",Secretkey);
                    headers.add("X-Time",String.valueOf(GetUTCdatetimeAsString()));
                    headers.add("X-Sign",getSignature());
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
                                    "\"id_test\": \""+rs2.getString("id_template")+"\"," +
                                    "\"nama_test\": \""+rs2.getString("Pemeriksaan")+"\"" +
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
                    
                    ps2=koneksi.prepareStatement(
                            "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit from diagnosa_pasien "+
                            "inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                            "where diagnosa_pasien.no_rawat=? and diagnosa_pasien.prioritas='1' ");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        kodeicd="";
                        namaicd="";
                        if(rs2.next()){
                            kodeicd=rs2.getString("kd_penyakit");
                            namaicd=rs2.getString("nm_penyakit");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 4 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    requestJson="{" +
                                    "\"no_pendaftaran\": \""+rs.getString("noorder")+"\"," +
                                    "\"no_rm\": \""+rs.getString("no_rkm_medis")+"\"," +
                                    "\"nama_pasien\": \""+rs.getString("nm_pasien")+"\"," +
                                    "\"tempat_lahir\": \""+rs.getString("tmp_lahir")+"\"," +
                                    "\"tgl_lahir\": \""+rs.getString("tgl_lahir")+"\"," +
                                    "\"jk\": \""+rs.getString("jk")+"\"," +
                                    "\"alamat\": \""+rs.getString("alamat")+"\"," +
                                    "\"id_ward\": \""+rs.getString("kd_kamar")+"\"," +
                                    "\"ward\": \""+rs.getString("nm_bangsal")+"\"," +
                                    "\"id_dokter\": \""+rs.getString("dokter_perujuk")+"\"," +
                                    "\"nama_dokter\": \""+rs.getString("nm_dokter")+"\"," +
                                    "\"id_jenis_pasien\": \""+rs.getString("kd_pj")+"\"," +
                                    "\"jenis_pasien\": \""+rs.getString("png_jawab")+"\"," +
                                    "\"id_penjamin\": \""+rs.getString("kd_pj")+"\"," +
                                    "\"penjamin\": \""+rs.getString("png_jawab")+"\"," +
                                    "\"cito\": \""+(rs.getString("informasi_tambahan").toLowerCase().contains("cito")?"true":"false")+"\"," +
                                    "\"diagnose\": \""+rs.getString("diagnosa_klinis")+"\"," +
                                    "\"icd10\": {" +
                                        "\"code\": \""+kodeicd+"\"," +
                                        "\"text\": \""+namaicd+"\"" +
                                    "}," +
                                    "\"order\": ["+
                                        requestJson2+
                                    "]" +
                                "}"; 
                    System.out.println("URL : "+URL+"/api/v1/saveOrder");
                    System.out.println("JSON : "+requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);	    
                    root = mapper.readTree(getRest().exchange(URL+"/api/v1/saveOrder", HttpMethod.POST, requestEntity, String.class).getBody());
                    if(root.path("metaData").path("code").asText().equals("200")){
                        JOptionPane.showMessageDialog(null,"Berhasil terkirim..");   
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal terkirim. Silahkan hubungi administrator");  
                    }
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server MEDQLAB terputus...!");
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
                JOptionPane.showMessageDialog(null,"Koneksi ke server MEDQLAB terputus...!");
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
