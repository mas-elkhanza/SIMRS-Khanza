package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import fungsi.sekuel;
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

public class ApiMEDQLAB {        
    private Connection koneksi=koneksiDB.condb();
    private String Consid,Secretkey;
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private String URL="",requestJson="",requestJson2="",hasil="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private JsonNode root;
    private sekuel Sequel=new sekuel();
    private JsonNode response,response2,response3,response4;
    private ObjectMapper mapper = new ObjectMapper();
    
    public ApiMEDQLAB(){
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
    
    public void kirimRalan(String nopermintaan) {
        try {
             ps=koneksi.prepareStatement(
                    "select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,"+
                    "if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,pasien.tgl_lahir,pasien.jk,pasien.alamat,"+
                    "if(permintaan_lab.tgl_sampel='0000-00-00','',permintaan_lab.tgl_sampel) as tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,"+
                    "if(permintaan_lab.tgl_hasil='0000-00-00','',permintaan_lab.tgl_hasil) as tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,"+
                    "permintaan_lab.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli,pasien.no_tlp,penjab.png_jawab,pasien.tmp_lahir,reg_periksa.kd_poli,reg_periksa.kd_pj, "+
                    "permintaan_lab.informasi_tambahan,permintaan_lab.diagnosa_klinis from permintaan_lab "+
                    "inner join reg_periksa inner join pasien inner join dokter inner join poliklinik inner join penjab "+
                    "on permintaan_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                    "and permintaan_lab.dokter_perujuk=dokter.kd_dokter and reg_periksa.kd_poli=poliklinik.kd_poli where permintaan_lab.noorder=?");
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
                            "select permintaan_pemeriksaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan "+
                            "from permintaan_pemeriksaan_lab inner join jns_perawatan_lab on permintaan_pemeriksaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_lab.noorder=? order by permintaan_pemeriksaan_lab.kd_jenis_prw desc");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        requestJson2="";
                        while(rs2.next()){
                            requestJson2=requestJson2+
                                "{" +
                                    "\"id_test\": \""+rs2.getString("kd_jenis_prw")+"\"," +
                                    "\"nama_test\": \""+rs2.getString("nm_perawatan")+"\"" +
                                "},";
                            ps3=koneksi.prepareStatement(
                                    "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                    "template_laboratorium.urut from permintaan_detail_permintaan_lab "+
                                    "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                                    "where permintaan_detail_permintaan_lab.noorder=? and permintaan_detail_permintaan_lab.kd_jenis_prw=? "+
                                    "order by template_laboratorium.urut desc");
                            try {
                                ps3.setString(1,rs.getString("noorder"));
                                ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    requestJson2=requestJson2+
                                        "{" +
                                            "\"id_test\": \""+rs3.getString("id_template")+"\"," +
                                            "\"nama_test\": \""+rs3.getString("Pemeriksaan")+"\"" +
                                        "},";
                                }
                            } catch (Exception e) {
                                System.out.println("Notif 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
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
                                    "\"no_pendaftaran\": \""+rs.getString("noorder").substring(4,14)+"\"," +
                                    "\"no_rm\": \""+rs.getString("no_rkm_medis")+"\"," +
                                    "\"nama_pasien\": \""+rs.getString("nm_pasien")+"\"," +
                                    "\"tempat_lahir\": \""+rs.getString("tmp_lahir")+"\"," +
                                    "\"tgl_lahir\": \""+rs.getString("tgl_lahir")+"\"," +
                                    "\"jk\": \""+rs.getString("jk")+"\"," +
                                    "\"alamat\": \""+rs.getString("alamat")+"\"," +
                                    "\"id_ward\": \""+rs.getString("kd_poli")+"\"," +
                                    "\"ward\": \""+rs.getString("nm_poli")+"\"," +
                                    "\"id_dokter\": \""+rs.getString("dokter_perujuk")+"\"," +
                                    "\"nama_dokter\": \""+rs.getString("nm_dokter")+"\"," +
                                    "\"id_jenis_pasien\": \""+rs.getString("kd_pj")+"\"," +
                                    "\"jenis_pasien\": \""+rs.getString("png_jawab")+"\"," +
                                    "\"id_penjamin\": \""+rs.getString("kd_pj")+"\"," +
                                    "\"penjamin\": \""+rs.getString("png_jawab")+"\"," +
                                    "\"cito\": \""+(rs.getString("informasi_tambahan").toLowerCase().contains("cito")?"true":"false")+"\"," +
                                    "\"diagnose\": \"\"," +
                                    "\"icd10\": []," +
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
                        JOptionPane.showMessageDialog(null,root.path("metaData").path("message").asText());  
                    }
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server MEDQLAB terputus...!");
                 }else{
                    JOptionPane.showMessageDialog(null,"Pengiriman gagal, silahkan hubungi Administrator...!"); 
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
    
    public void kirimRanap(String nopermintaan) {
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
                            "select permintaan_pemeriksaan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan "+
                            "from permintaan_pemeriksaan_lab inner join jns_perawatan_lab on permintaan_pemeriksaan_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where permintaan_pemeriksaan_lab.noorder=? order by permintaan_pemeriksaan_lab.kd_jenis_prw desc");
                    try {
                        ps2.setString(1,rs.getString("noorder"));
                        rs2=ps2.executeQuery();
                        requestJson2="";
                        while(rs2.next()){
                            requestJson2=requestJson2+
                                "{" +
                                    "\"id_test\": \""+rs2.getString("kd_jenis_prw")+"\"," +
                                    "\"nama_test\": \""+rs2.getString("nm_perawatan")+"\"" +
                                "},";
                            ps3=koneksi.prepareStatement(
                                    "select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                    "template_laboratorium.urut from permintaan_detail_permintaan_lab "+
                                    "inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template "+
                                    "where permintaan_detail_permintaan_lab.noorder=? and permintaan_detail_permintaan_lab.kd_jenis_prw=? "+
                                    "order by template_laboratorium.urut desc");
                            try {
                                ps3.setString(1,rs.getString("noorder"));
                                ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    requestJson2=requestJson2+
                                        "{" +
                                            "\"id_test\": \""+rs3.getString("id_template")+"\"," +
                                            "\"nama_test\": \""+rs3.getString("Pemeriksaan")+"\"" +
                                        "},";
                                }
                            } catch (Exception e) {
                                System.out.println("Notif 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
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
                                    "\"no_pendaftaran\": \""+rs.getString("noorder").substring(4,14)+"\"," +
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
                                    "\"diagnose\": \"\"," +
                                    "\"icd10\": []," +
                                    "\"order\": ["+
                                        requestJson2+
                                    "]" +
                                "}"; 
                    System.out.println("URL : "+URL+"/api/v1/saveOrder");
                    System.out.println("JSON : "+requestJson);
                    requestEntity = new HttpEntity(requestJson,headers);	    
                    root = mapper.readTree(getRest().exchange(URL+"/api/v1/saveOrder", HttpMethod.POST, requestEntity, String.class).getBody());
                    if(root.path("metaData").path("code").asText().equals("200")){
                        JOptionPane.showMessageDialog(null,root.path("metaData").path("message").asText());  
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal terkirim. Silahkan hubungi administrator");  
                    }
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server MEDQLAB terputus...!");
                 }else{
                    JOptionPane.showMessageDialog(null,"Pengiriman gagal, silahkan hubungi Administrator...!"); 
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
    
    public void ambil(String nopermintaan) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-cons",Consid);
            headers.add("X-key",Secretkey);
            headers.add("X-Time",String.valueOf(GetUTCdatetimeAsString()));
            headers.add("X-Sign",getSignature());
            System.out.println("URL : "+URL+"/api/v1/getResult/json?no_laboratorium="+nopermintaan);
            requestEntity = new HttpEntity(headers);
            requestJson=getRest().exchange(URL+"/api/v1/getResult/json?no_laboratorium="+nopermintaan, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("Result : "+requestJson);
            root = mapper.readTree(requestJson);
            if(root.path("metaData").path("code").asText().equals("200")){
                Sequel.queryu("truncate table temporary_permintaan_lab");
                response = root.path("response").path("data").path("pemeriksaan"); 
                if(response.isArray()){
                    for(JsonNode list:response){
                        hasil="";
                        if(!list.path("value").asText().equals("")){
                            hasil=list.path("value").asText();
                        }
                        if(!list.path("value_string").asText().equals("")){
                            hasil=list.path("value_string").asText();
                        }
                        if(!list.path("value_memo").asText().equals("")){
                            hasil=list.path("value_memo").asText();
                        }
                        
                        System.out.println(" id : "+list.path("testid_simrs").asText()+", name : "+list.path("name").asText()+", value : "+list.path("value").asText()+", value_string : "+list.path("value_string").asText()+", value_memo : "+list.path("value_memo").asText()+", keterangan : "+list.path("flag").asText()+", nilai_normal : "+list.path("nilai_normal").asText());
                        Sequel.menyimpan(
                            "temporary_permintaan_lab","'0','"+list.path("testid_simrs").asText()+"','"+list.path("name").asText()+"','"+hasil+"','"+list.path("flag").asText()+"','"+list.path("nilai_normal").asText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"
                        ); 
                        
                        response2 = list.path("childs");
                        if(response2.isArray()){
                            for(JsonNode list2:response2){
                                hasil="";
                                if(!list2.path("value").asText().equals("")){
                                    hasil=list2.path("value").asText();
                                }
                                if(!list2.path("value_string").asText().equals("")){
                                    hasil=list2.path("value_string").asText();
                                }
                                if(!list2.path("value_memo").asText().equals("")){
                                    hasil=list2.path("value_memo").asText();
                                }
                                
                                System.out.println(" id : "+list2.path("testid_simrs").asText()+", name : "+list2.path("name").asText()+", value : "+list2.path("value").asText()+", value_string : "+list2.path("value_string").asText()+", value_memo : "+list2.path("value_memo").asText()+", keterangan : "+list2.path("flag").asText()+", nilai_normal : "+list2.path("nilai_normal").asText());
                                Sequel.menyimpan(
                                    "temporary_permintaan_lab","'0','"+list2.path("testid_simrs").asText()+"','"+list2.path("name").asText()+"','"+hasil+"','"+list2.path("flag").asText()+"','"+list2.path("nilai_normal").asText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"
                                );
                                
                                response3 = list2.path("childs");
                                if(response3.isArray()){
                                    for(JsonNode list3:response3){
                                        hasil="";
                                        if(!list3.path("value").asText().equals("")){
                                            hasil=list3.path("value").asText();
                                        }
                                        if(!list3.path("value_string").asText().equals("")){
                                            hasil=list3.path("value_string").asText();
                                        }
                                        if(!list3.path("value_memo").asText().equals("")){
                                            hasil=list3.path("value_memo").asText();
                                        }

                                        System.out.println(" id : "+list3.path("testid_simrs").asText()+", name : "+list3.path("name").asText()+", value : "+list3.path("value").asText()+", value_string : "+list3.path("value_string").asText()+", value_memo : "+list3.path("value_memo").asText()+", keterangan : "+list3.path("flag").asText()+", nilai_normal : "+list3.path("nilai_normal").asText());
                                        Sequel.menyimpan(
                                            "temporary_permintaan_lab","'0','"+list3.path("testid_simrs").asText()+"','"+list3.path("name").asText()+"','"+hasil+"','"+list3.path("flag").asText()+"','"+list3.path("nilai_normal").asText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"
                                        );
                                        
                                        response4 = list3.path("childs");
                                        if(response4.isArray()){
                                            for(JsonNode list4:response4){
                                                hasil="";
                                                if(!list4.path("value").asText().equals("")){
                                                    hasil=list4.path("value").asText();
                                                }
                                                if(!list4.path("value_string").asText().equals("")){
                                                    hasil=list4.path("value_string").asText();
                                                }
                                                if(!list4.path("value_memo").asText().equals("")){
                                                    hasil=list4.path("value_memo").asText();
                                                }

                                                System.out.println(" id : "+list4.path("testid_simrs").asText()+", name : "+list4.path("name").asText()+", value : "+list4.path("value").asText()+", value_string : "+list4.path("value_string").asText()+", value_memo : "+list4.path("value_memo").asText()+", keterangan : "+list4.path("flag").asText()+", nilai_normal : "+list4.path("nilai_normal").asText());
                                                Sequel.menyimpan(
                                                    "temporary_permintaan_lab","'0','"+list4.path("testid_simrs").asText()+"','"+list4.path("name").asText()+"','"+hasil+"','"+list4.path("flag").asText()+"','"+list4.path("nilai_normal").asText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"
                                                );
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,root.path("metaData").path("message").asText());  
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
