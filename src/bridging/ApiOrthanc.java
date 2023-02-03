package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author windiartonugroho
 */
public class ApiOrthanc {
    private HttpHeaders headers ;
    private JsonNode root;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;
    private String auth,authEncrypt,requestJson;
    private byte[] encodedBytes;
    
    public ApiOrthanc(){
        try {
            auth=koneksiDB.USERORTHANC()+":"+koneksiDB.PASSORTHANC();
            encodedBytes = Base64.encodeBase64(auth.getBytes());
            authEncrypt= new String(encodedBytes);
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
    
    public String Auth(){
        return authEncrypt;
    }
    
    public JsonNode AmbilSeries(String Norm,String Tanggal1,String Tanggal2){
        System.out.println("Percobaan Mengampil Photo Pasien : "+Norm);
        try{
            headers = new HttpHeaders();
            System.out.println("Auth : "+authEncrypt);
            headers.add("Authorization", "Basic "+authEncrypt);
            requestEntity = new HttpEntity(headers);
            requestJson = "{"+
                              "\"Level\": \"Study\","+
                              "\"Expand\": true,"+
                              "\"Query\": {"+
                                   "\"StudyDate\": \""+Tanggal1+"-"+Tanggal2+"\","+
                                   "\"PatientID\": \""+Norm+"\""+
                              "}"+
                          "}";
            System.out.println("Request JSON : "+requestJson);
            requestEntity = new HttpEntity(requestJson,headers);
            System.out.println("URL : "+koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/tools/find");
            requestJson=getRest().exchange(koneksiDB.URLORTHANC()+":"+koneksiDB.PORTORTHANC()+"/tools/find", HttpMethod.POST, requestEntity, String.class).getBody();
            System.out.println("Result JSON : "+requestJson);
            root = mapper.readTree(requestJson);
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
            JOptionPane.showMessageDialog(null,"Gagal mengambil data dari Orthanc, silahkan hubungi administrator ..!!");
        }
        return root;
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
}
