package bridging;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * WsEKlaimHelper = Kumpulan fungsi static client java untuk ecnrypt dan decrypt sesuai dengan Web Service E-Klaim / INACBG-5.<br>
 * Menggunakan PKCS7Padding, dari Bouncy Castle Crypto API, jar dapat didownload di <a href="http://www.bouncycastle.org/latest_releases.html">Bouncy-JCE</a>.<br>
 * Untuk JDK 1.5 - JDK 1.8, bisa langsung download di <a href="http://www.bouncycastle.org/download/bcprov-jdk15on-154.jar">bcprov-jdk15on-154.jar</a>.<br>
 * Dependencies jar :<br>
 * - commons-lang<br>
 * - commons-beanutils<br>
 * - commons-collection<br>
 * - commons-logging<br>
 * - commons-codec<br>
 * - ezmorph<br>
 * - json-lib<br>
 * - bcprov-jdk15on-154<br>
 * Dependencies Extension untuk JDK 1.6 atau diatasnya :<br>
 * Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy<br>
 * jdk 8 : <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html">Unlimited-JCE-Policy-8</a><br>
 * jdk 7 : <a href="http://www.oracle.com/technetwork/java/embedded/embedded-se/downloads/jce-7-download-432124.html">Unlimited-JCE-Policy-7</a><br>
 * jdk 6 : <a href="http://www.oracle.com/technetwork/java/embedded/embedded-se/downloads/jce-6-download-429243.html">Unlimited-JCE-Policy-6</a><br>
 * Cara Instalasi JCE :<br>
 * 1. download <br>
 * 2. extract<br>
 * 3. copy hasil extract ke dalam folder {JDK_HOME}\jre\lib\security<br>
 * 
 * @author tubagus
 * 
 */
public class EKlaimHelper {
	/**
	 * WS_KEY = Variable yang disesuaikan dengan key yang ada di webservice E-Klaim.<br>
	 * - Silahkan di rubah sesuai dengan KEY yang sudah digenerate dan ada pada E-Klaim.<br>
	 * - Pada saat dijadikan production konstanta ini silahkan dimodifikasi untuk dijadikan parameterized varible <br>
	 * 
	 */
	public static String WS_KEY = "071554631c8d0b984a52ca739ba8a191a5d5db2d264c73c974ec037c54c93976";
	
	/**
	 * WS_URL = Variable target alamat URL yang disesuaikan dengan url webservice E-Klaim.<br>
	 * - Silahkan di rubah sesuai dengan URL E-Klaim masing-masing.<br>
	 * - Pada saat dijadikan production variable ini silahkan dimodifikasi untuk dijadikan parameterized varible <br>
	 */
	public static String WS_URL = "http://192.168.3.108/E-Klaim/ws.php";

	/**
	 * hmacDigest = Fungsi untuk menggenerate signature yang dibutuhkan oleh WS E-Klaim/INACBG-5.
	 * @param msg : Data yang sudah terenkripsi dalam bentuk ByteArray
	 * @param key : Data kunci dalam bentuk ByteArray
	 * @param algo : Nama algoritma yang akan dipakai. Contoh : AES, Blowfish, 
	 * HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512 dll.
	 * @return data signature dalam bentuk ByteArray
	 */
	public static byte[] hmacDigest(byte[] msg, byte[] key, String algo) {
		byte[] bytes = null;
		try {
			SecretKeySpec keyspec = new SecretKeySpec(key, algo);
			Mac mac = Mac.getInstance(algo);
			mac.init(keyspec);
			bytes = mac.doFinal(msg);
			bytes = Arrays.copyOfRange(bytes, 0, 10);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * mcEncrypt = Fungsi untuk mengenkripsi data JSON String yang akan dikirim ke WS E-Klaim/INACBG-5 
	 * @param data : data JSON String yang akan di enkripsi
	 * @param key : Kunci untuk mengenkripsi data
	 * @return String data yang sudah terenkripsi
	 */
	public static String mcEncrypt(String data, String key) {
		try {
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
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
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * <pre>
	 * mcDecrypt = Fungsi untuk menDekripsi data JSON String yang diterima dari WS E-Klaim/INACBG-5.
	 * Note :
	 * 1. Input parameter "data" harus sudah bersih, artinya sudah dihilangkan prefix dan sufix-nya
	 * 	----BEGIN ENCRYPTED DATA----
	 * 		[ISI-DATA]
	 * 	----END ENCRYPTED DATA----
	 * 2. Input parameter data harus sudah terimplode dengan ("").
	 * </pre>
	 * @param data : JSON String yang akan di dekripsi.
	 * @param key : Kunci untuk mendekripsi data
	 * @return String data yang sudah terdekripsi
	 */
	public static String mcDecrypt(String data, String key) {
		try {
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
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
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * implode = Fungsi untuk mengimplode String array, menggabungkan elemen array String menjadi sebuah String 
	 * dengan menggunakan separator (pemisah) antar element array. 
	 * Fungsi ini digunakan hampir sama fungsi dan penggunaannya dengan fungsi implode di PHP.
	 * @param separator : String pemisah antar element array
	 * @param data : Array String yang akan gabungkan
	 * @return String yang sudah digabungkan (joined)
	 */
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
	
	/**
	 * implode = Fungsi untuk mengimplode Object List berisi String, menggabungkan elemen Object List menjadi sebuah String 
	 * dengan menggunakan separator (pemisah) antar element Object List. 
	 * Fungsi ini digunakan hampir sama fungsi dan penggunaannya dengan fungsi implode di PHP.
	 * @param separator : String pemisah antar element array
	 * @param data : Array String yang akan gabungkan
	 * @return String yang sudah digabungkan (joined)
	 */
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
	
	public static void main(String[] args) throws JSONException {
		
		JSONObject metadata = new JSONObject();
		metadata.put("method" , "get_claim_data");
		
		JSONObject data = new JSONObject();
		data.put("nomor_sep" , "0301R00112140006067");
		
		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);
		System.out.println(obj.toString());
		
		String enc = mcEncrypt(obj.toString(), WS_KEY);
		System.out.println("encrypted : "+ enc);
		String dec = mcDecrypt(enc, WS_KEY);
		System.out.println("decrypted : "+ dec);
	}
}
