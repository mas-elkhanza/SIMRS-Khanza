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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ApiBRI {        
    private Connection koneksi=koneksiDB.condb();
    private long GetUTCdatetimeAsString;
    private String kd_rek,nm_rek, consumer_key, consumer_secret, institution_code, briva_no, urlapi,token,signature,timestamp,timestamp2,json="";
    private String generateHmacSHA256Signature;
    private byte[] hmacData;
    private Mac mac;
    private long millis;
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private SecretKeySpec secretKey;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;
    private PreparedStatement ps;
    private ResultSet rs;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private JsonNode root;
    private sekuel Sequel=new sekuel();
    private JsonNode response;
    private ObjectMapper mapper = new ObjectMapper();
    private Date date,date2;
    private SimpleDateFormat sdf,sdf2;
    
    public ApiBRI(){
        try {
            ps=koneksi.prepareStatement(
                   "select set_akun_bankbri.kd_rek,rekening.nm_rek,aes_decrypt(consumer_key,'nur') as consumer_key,"+
                   "aes_decrypt(consumer_secret,'windi') as consumer_secret,aes_decrypt(institution_code,'nur') as institution_code,"+
                   "aes_decrypt(briva_no,'windi') as briva_no,aes_decrypt(urlapi,'dewi') as urlapi "+
                   "from set_akun_bankbri inner join rekening on set_akun_bankbri.kd_rek=rekening.kd_rek");
            try {
               rs=ps.executeQuery();
               if(rs.next()){
                   kd_rek=rs.getString("kd_rek");
                   nm_rek=rs.getString("nm_rek");
                   consumer_key=rs.getString("consumer_key");
                   consumer_secret=rs.getString("consumer_secret");
                   institution_code=rs.getString("institution_code");
                   briva_no=rs.getString("briva_no");
                   urlapi=rs.getString("urlapi");
               }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
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
        }
    }

    public String generateHmacSHA256Signature(String data, String key)throws GeneralSecurityException {
        hmacData = null;
	try {
            secretKey = new SecretKeySpec(key.getBytes("UTF-8"),"HmacSHA256");
	    mac = Mac.getInstance("HmacSHA256");
	    mac.init(secretKey);
	    hmacData = mac.doFinal(data.getBytes("UTF-8"));
	    return new String(Base64.encode(hmacData), "UTF-8");
	} catch (UnsupportedEncodingException e) {
            System.out.println("Error Generate HMac: e");
	    throw new GeneralSecurityException(e);
	}
    }
        
    public long GetUTCdatetimeAsString(){    
        millis = System.currentTimeMillis();   
        return millis/1000;
    }
    
    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("SSL");
        TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        scheme=new Scheme("https",443,sslFactory);
        factory=new HttpComponentsClientHttpRequestFactory();
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }
    
    public String Token(){
        token="";
        try{
            headers = new HttpHeaders();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("client_id",consumer_key);
            map.add("client_secret",consumer_secret);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
            token=getRest().postForEntity(urlapi+"/oauth/client_credential/accesstoken?grant_type=client_credentials", request , String.class ).getBody();
            System.out.println("Get Token : "+token);
            root = mapper.readTree(token);
            token=root.path("access_token").asText();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return token;
    }
    
    public String Signature(String path,String verb,String token,String timestamp,String body){
        signature="";
        try {
            System.out.println("Payload : "+"path="+path+"&verb="+verb+"&token=Bearer "+token+"&timestamp="+timestamp+"&body="+body);
            signature=generateHmacSHA256Signature("path="+path+"&verb="+verb+"&token=Bearer "+token+"&timestamp="+timestamp+"&body="+body,consumer_secret);
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        return signature;
    }
    
    public void buatVA(String norawat,String nama,String bayar,String keterangan){
        try{
            token=Token();
            date = new Date(System.currentTimeMillis());
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            timestamp=sdf.format(date);
            date2 = new Date(System.currentTimeMillis()+86400000);
            sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timestamp2=sdf2.format(date2);
            System.out.println("consumer_key : "+consumer_key);
            System.out.println("consumer_secret : "+consumer_secret);
            json="{" +
                    "\"institutionCode\": \""+institution_code+"\"," +
                    "\"brivaNo\": \""+briva_no+"\"," +
                    "\"custCode\": \""+norawat+"\"," +
                    "\"nama\": \""+nama+"\"," +
                    "\"amount\": \""+bayar+"\"," +
                    "\"keterangan\": \""+keterangan+"\"," +
                    "\"expiredDate\": \""+timestamp2+"\"" +
                 "}";
            System.out.println("JSON : "+json);
            signature=Signature("/v1/briva","POST",token,timestamp,json);
            
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("BRI-Timestamp",timestamp);
            headers.add("BRI-Signature",signature);
            headers.add("Authorization","Bearer "+token); 
            System.out.println("URL : "+urlapi+"/v1/briva");
            System.out.println("BRI-Timestamp : "+timestamp);
            System.out.println("BRI-Signature : "+signature);
            System.out.println("Authorization : "+"Bearer "+token);
            requestEntity = new HttpEntity(json,headers);
            json = mapper.readTree(getRest().exchange(urlapi+"/v1/briva", HttpMethod.POST, requestEntity, String.class).getBody()).toString();
            System.out.println("Respon : "+json);
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }

}
