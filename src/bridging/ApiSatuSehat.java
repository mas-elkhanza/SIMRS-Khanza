package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
import org.springframework.web.client.RestTemplate;

public class ApiSatuSehat {        
    private String key,clientid,urlauth,token;
    private long millis;
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;
    private ApiBPJSAesKeySpec mykey;
    private HttpHeaders header ;
    private JsonNode root;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    
    public ApiSatuSehat(){
        try {
            key = koneksiDB.SECRETKEYSATUSEHAT();
            clientid = koneksiDB.CLIENTIDSATUSEHAT();
            urlauth = koneksiDB.URLAUTHSATUSEHAT();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }

    public String TokenSatuSehat(){
        try {    
            header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            requestEntity = new HttpEntity("client_id="+clientid+"&client_secret="+key,header);
            root = mapper.readTree(getRest().exchange(urlauth+"/accesstoken?grant_type=client_credentials", HttpMethod.POST, requestEntity, String.class).getBody());
            token=root.path("access_token").asText();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return token;
    }
        
    public long GetUTCdatetimeAsString(){    
        millis = System.currentTimeMillis();   
        return millis/1000;
    }
    
    public String Decrypt(String data,String utc)throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        System.out.println(data);
        mykey = ApiBPJSEnc.generateKey(clientid+key+utc);
        data=ApiBPJSEnc.decrypt(data, mykey.getKey(), mykey.getIv());
        data=ApiBPJSLZString.decompressFromEncodedURIComponent(data);
        System.out.println(data);
        return data;
    }
    
    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("TLSv1.2");
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

}
