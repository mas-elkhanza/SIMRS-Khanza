/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author khanzasoft
 */
public class tessaja5 {
    public static SisruteApi api=new SisruteApi();
    private static Properties prop = new Properties();
    public static void tessaja() throws FileNotFoundException, IOException{
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        System.out.println("X-cons-id:"+prop.getProperty("CONSIDAPIPCARE"));
	System.out.println("X-Timestamp:"+String.valueOf(api.GetUTCdatetimeAsString()));            
	System.out.println("X-Signature:"+api.getHmac());
        String otorisasi=prop.getProperty("USERPCARE")+":"+prop.getProperty("PASSPCARE")+":095";
        System.out.println("X-Authorization:Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
    }
    public static void main(String[] args) throws IOException {
        tessaja();
    }
    
}
