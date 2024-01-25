package update;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
/**
 *
 * @author Thomas Otero H3R3T1C
 */
public class Updater {
     private final static  Properties propServ = new Properties();
     static String URLSERVER;
      
        
    
     
   
    private static String versionURL ;
    private static String historyURL ;
    
    public static String getLatestVersion() throws Exception
    {
        try  {
            propServ.loadFromXML(new FileInputStream("settingupdate/config.xml"));
            URLSERVER=propServ.getProperty("URLSERVERUPDATE");
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        versionURL= "http://"+URLSERVER+"/version.html";
        String data = getData(versionURL);
        return data.substring(data.indexOf("[version]")+9,data.indexOf("[/version]"));
    }
    public static String getWhatsNew() throws Exception
    {
         try  {
            propServ.loadFromXML(new FileInputStream("settingupdate/config.xml"));
            URLSERVER=propServ.getProperty("URLSERVERUPDATE");
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
      historyURL  = "http://"+URLSERVER+"/version.html";
        String data = getData(historyURL);
        return data.substring(data.indexOf("[history]")+9,data.indexOf("[/history]"));
    }
    private static String getData(String address)throws Exception
    {
        URL url = new URL(address);
        
        InputStream html = null;

        html = url.openStream();
        
        int c = 0;
        StringBuffer buffer = new StringBuffer("");

        while(c != -1) {
            c = html.read();
            
        buffer.append((char)c);
        }
        return buffer.toString();
    }
}
