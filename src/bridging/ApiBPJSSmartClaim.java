package bridging;

import fungsi.koneksiDB;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ApiBPJSSmartClaim {
    private String Key, Consid;
    private String salt;
    private String generateHmacSHA256Signature;
    private byte[] hmacData;
    private Mac mac;
    private long millis;
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private SecretKeySpec secretKey;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;
    private ApiBPJSAesKeySpec mykey;

    public ApiBPJSSmartClaim() {
        try {
            Key = koneksiDB.SECRETKEYAPISMARTCLAIM();
            Consid = koneksiDB.CONSIDAPISMARTCLAIM();
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
        }
    }

    public String getHmac(String utc) {
        salt = Consid + "&" + utc;
        generateHmacSHA256Signature = null;
        try {
            generateHmacSHA256Signature = generateHmacSHA256Signature(salt, Key);
        } catch (GeneralSecurityException e) {
            System.out.println("Notif : " + e);
        }
        return generateHmacSHA256Signature;
    }
    
    public String generateHmacSHA256Signature(String data, String key) throws GeneralSecurityException {
        hmacData = null;
        try {
            secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            hmacData = mac.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hmacData);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Notif : " + e);
            throw new GeneralSecurityException(e);
        }
    }

    public long GetUTCdatetimeAsString() {
        millis = System.currentTimeMillis();
        return millis / 1000;
    }

    public String Decrypt(String data, String utc) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        System.out.println("Decrypting data: " + data);
        mykey = ApiBPJSEnc.generateKey(Consid + Key + utc);
        data = ApiBPJSEnc.decrypt(data, mykey.getKey(), mykey.getIv());
        data = ApiBPJSLZString.decompressFromEncodedURIComponent(data);
        return data;
    }

    public String compressSmartClaim(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        }
        
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    public String EncryptCompressedData(String kodefaskes, String compressedBase64Data) {
        String encryptMethod = "AES/CBC/PKCS5Padding";
        String encryptKey = Consid + Key + kodefaskes;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyHashHex = digest.digest(encryptKey.getBytes(StandardCharsets.UTF_8));
            String keyHashHexString = bytesToHex(keyHashHex);
            
            byte[] keyHash = hexStringToByteArray(keyHashHexString);
            byte[] iv = new byte[16];
            System.arraycopy(keyHash, 0, iv, 0, 16);
            
            Cipher cipher = Cipher.getInstance(encryptMethod);
            SecretKeySpec key = new SecretKeySpec(keyHash, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            
            byte[] encryptedBytes = cipher.doFinal(compressedBase64Data.getBytes(StandardCharsets.UTF_8));
            String finalEncrypted = Base64.getEncoder().encodeToString(encryptedBytes);

            return finalEncrypted;
        } catch (Exception e) {
            System.out.println("Notif " + e);
            return null;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    @Deprecated
    public String encodeBase64(String data) {
        try {
            return Base64.getEncoder().encodeToString(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Notif : " + e);
            return null;
        }
    }

    @Deprecated
    public String encodeBase64FromBytes(byte[] data) {
        try {
            return Base64.getEncoder().encodeToString(data);
        } catch (Exception e) {
            System.out.println("Notif : " + e);
            return null;
        }
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
        sslContext.init(null, trustManagers, new SecureRandom());
        sslFactory = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        scheme = new Scheme("https", 443, sslFactory);
        factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(60000); 
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }

    public byte[] compressSmartClaimRaw(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return new byte[0];
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        }

        return out.toByteArray();
    }
    
    public String EncryptCompressedData(String kodefaskes, byte[] compressedBytes) {
        String encryptMethod = "AES/CBC/PKCS5Padding";
        String encryptKey = Consid + Key + kodefaskes;
        try {
            String compressedBase64 = Base64.getEncoder().encodeToString(compressedBytes);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyHashBytes = digest.digest(encryptKey.getBytes(StandardCharsets.UTF_8));
            String keyHashHex = bytesToHex(keyHashBytes);
            byte[] keyHash = hexStringToByteArray(keyHashHex);
            byte[] iv = new byte[16];
            System.arraycopy(keyHash, 0, iv, 0, 16);
            Cipher cipher = Cipher.getInstance(encryptMethod);
            SecretKeySpec key = new SecretKeySpec(keyHash, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] encryptedBytes = cipher.doFinal(compressedBase64.getBytes(StandardCharsets.UTF_8));
            String finalEncrypted = Base64.getEncoder().encodeToString(encryptedBytes);
            return finalEncrypted;
        } catch (Exception e) {
            System.out.println("Notif : " + e.getMessage());
            return null;
        }
    }
    
    public String encryptSmartClaimData(String jsonData, String kodefaskes) throws IOException {
        byte[] compressedBytes = compressSmartClaimRaw(jsonData);
        String encrypted = EncryptCompressedData(kodefaskes, compressedBytes);
        return encrypted;
    }
}
