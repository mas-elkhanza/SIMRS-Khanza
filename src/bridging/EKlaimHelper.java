package bridging;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class EKlaimHelper {
    private static final Properties prop = new Properties();
    
    public static String WS_KEY(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            return prop.getProperty("SECRETKEYAPIEKLAIM");
        } catch (Exception ex) {
            System.out.println("Notifikasi Key Eklaim : "+ex);
            return "";
        }
    }
    
    public static String WS_URL(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            return prop.getProperty("URLAPIEKLAIM");
        } catch (Exception ex) {
            System.out.println("Notifikasi Key Eklaim : "+ex);
            return "";
        }
    }
    
    public static byte[] hmacDigest(byte[] msg, byte[] key, String algo) {
        byte[] bytes = null;
        try {
            SecretKeySpec keyspec = new SecretKeySpec(key, algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(keyspec);
            bytes = mac.doFinal(msg);
            bytes = Arrays.copyOfRange(bytes, 0, 10);
        } catch (InvalidKeyException e) {
            System.out.println("Notifikasi Invalid Key : "+e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Notifikasi Algoritma : "+e);
        }
        return bytes;
    }
        
    public static String mcEncrypt(String data, String key) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] ivbytes = new byte[cipher.getBlockSize()];
            randomSecureRandom.nextBytes(ivbytes);
            IvParameterSpec ivParams = new IvParameterSpec(ivbytes);
            byte[] keybytes = new BigInteger(key, 16).toByteArray();
            SecretKeySpec keyspec = new SecretKeySpec(keybytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivParams);
            byte[] encryptedbytes = cipher.doFinal(data.getBytes());
            byte[] signature = hmacDigest(encryptedbytes, keybytes, "HmacSHA256");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(signature);
            outputStream.write(ivbytes);
            outputStream.write(encryptedbytes);
            byte[] mixedBytes = outputStream.toByteArray();
            return Base64.encodeBase64String(mixedBytes);
        } catch (Exception e) {
            System.out.println("Notifikasi Encrypt : "+e);
            return "";
        }
    }

    public static String mcDecrypt(String data, String key) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            byte[] decodebytes = Base64.decodeBase64(data);
            byte[] signaturebytes = Arrays.copyOfRange(decodebytes, 0, 10);
            byte[] ivbytes = Arrays.copyOfRange(decodebytes, 10, (cipher.getBlockSize() + 10));
            byte[] encryptedbytes = Arrays.copyOfRange(decodebytes, (cipher.getBlockSize() + 10), decodebytes.length);
            byte[] keybytes = new BigInteger(key, 16).toByteArray();
            byte[] calcSignature = hmacDigest(encryptedbytes, keybytes, "HmacSHA256");

            if (!MessageDigest.isEqual(signaturebytes, calcSignature)) {
                    return "SIGNATURE_NOT_MATCH";
            }

            IvParameterSpec ivParams = new IvParameterSpec(ivbytes);
            SecretKeySpec keyspec = new SecretKeySpec(keybytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivParams);
            byte[] decryptedbytes = cipher.doFinal(encryptedbytes);
            String decryptedStr = new String(decryptedbytes);

            return decryptedStr;
        } catch (Exception e) {
            System.out.println("Notifikasi Decrypt : "+e);
            return "";
        }
    }
    
    public static String implode(String separator, String[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length - 1; i++) {
            if (!data[i].matches(" *")) {
                sb.append(data[i]);
                sb.append(separator);
            }
        }
        sb.append(data[data.length - 1].trim());
        return sb.toString();
    }
	
	
    public static String implode(String separator, List<String> data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size() - 1; i++) {
            if (!data.get(i).matches(" *")) {
                sb.append(data.get(i));
                sb.append(separator);
            }
        }
        sb.append(data.get(data.size() - 1).trim());
        return sb.toString();
    }
}
