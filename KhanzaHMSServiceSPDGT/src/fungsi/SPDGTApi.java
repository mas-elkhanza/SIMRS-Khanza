package fungsi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class SPDGTApi {        
    private static final Properties prop = new Properties();
    private String user,pass,url,token;
    private HttpHeaders header ;
    private JsonNode root;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    
    public SPDGTApi(){
        try {            
            prop.loadFromXML(new FileInputStream("setting/database.xml"));   
            url  = koneksiDB.URLAPISPDGT();
            pass = koneksiDB.PASSSPDGT();
            user = koneksiDB.USERSPDGT();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
    
    public String TokenSPDGT(){
        try {    
            header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            requestEntity = new HttpEntity("username="+user+"&password="+pass,header);
            root = mapper.readTree(getRest().exchange(url+"/Api/login", HttpMethod.POST, requestEntity, String.class).getBody());
            token=root.path("token").asText();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        return token;
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
