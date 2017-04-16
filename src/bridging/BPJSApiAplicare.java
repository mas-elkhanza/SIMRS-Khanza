package bridging;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.crypto.codec.Base64;

public class BPJSApiAplicare {        
    private static final Properties prop = new Properties();
    public String getHmac() {
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        long GetUTCdatetimeAsString = GetUTCdatetimeAsString();
        
	String secretKey = prop.getProperty("SECRETKEYAPIAPLICARE");
        String Consid = prop.getProperty("CONSIDAPIAPLICARE");
        String salt = Consid +"&"+String.valueOf(GetUTCdatetimeAsString);	

	String generateHmacSHA256Signature = null;
	try {
	    generateHmacSHA256Signature = generateHmacSHA256Signature(salt,secretKey);
	} catch (GeneralSecurityException e) {
	    // TODO Auto-generated catch block
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

}
