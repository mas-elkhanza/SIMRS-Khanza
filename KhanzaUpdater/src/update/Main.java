
package update;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author Thomas Otero (H3R3T1C)
 */
public class Main {
private static final Properties propVer = new Properties();  
private static final Properties propConfig = new Properties();  
public static String version,URLSERVER,versionURL;

    public static void main(String[] args) throws Exception {
        try {
            propVer.loadFromXML(new FileInputStream("settingupdate/version.xml"));
            propConfig.loadFromXML(new FileInputStream("settingupdate/config.xml"));
        } catch (Exception e) {
            System.out.println("Notif Setting : "+e);
        } 
        version = propVer.getProperty("VERSION");
        
        if(propConfig.getProperty("AUTOUPDATESISTEM").equals("aktif"))
         {
             System.out.println("Update versi Local :"+version +"Versi Server :"+getLatestVersion());
             
              try {
            if (!Updater.getLatestVersion().equals(version) ) {
                new UpdateInfo(Updater.getWhatsNew());
            }
            else
            {
               
            }
            } catch (Exception ex) {
                    ex.printStackTrace();
                }
//        try {
//            if (Integer.parseInt(Updater.getLatestVersion()) > 0) {
//                new UpdateInfo(Updater.getWhatsNew());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }
        else{
           System.out.println("No Update");
        }
    }
     public static String getLatestVersion() throws Exception
    {
        try  {
              propConfig.loadFromXML(new FileInputStream("settingupdate/config.xml"));
            URLSERVER=propConfig.getProperty("URLSERVERUPDATE");
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
        versionURL= "http://"+URLSERVER+"/version.html";
        String data = getData(versionURL);
        return data.substring(data.indexOf("[version]")+9,data.indexOf("[/version]"));
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
