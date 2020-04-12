/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import fungsi.koneksiDB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author khanzasoft
 */
public class tessaja4 {
    private static Properties prop = new Properties();
    public static ApiPcare api=new ApiPcare();
    public static void tessaja() throws FileNotFoundException, IOException{
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        System.out.println("X-cons-id:"+koneksiDB.IDSISRUTE());
	System.out.println("X-Timestamp:"+String.valueOf(api.GetUTCdatetimeAsString())); 
	System.out.println("X-signature:"+api.getHmac()); 
	System.out.println("Content-type:application/json");             
	System.out.println("Content-length:"+null); 
    }
    public static void main(String[] args) throws IOException {
        tessaja();
    }
    
}
