/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;


import AESsecurity.EnkripsiAES;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;
import javax.swing.JOptionPane;


/**
 *
 * @author khanzasoft
 */
public class koneksiDBWa {
    private static Connection connection=null;
    private static final Properties prop = new Properties();  
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    private static String var="";
    
    public koneksiDBWa(){} 
    public static Connection newConnection() throws Exception {
        Properties setting = new Properties();
        MysqlDataSource source = new MysqlDataSource();

        setting.loadFromXML(new FileInputStream("setting/database.xml"));
        source.setURL("jdbc:mysql://"
                + EnkripsiAES.decrypt(setting.getProperty("HOSTWA")) + ":"
                + EnkripsiAES.decrypt(setting.getProperty("PORTWA")) + "/"
                + EnkripsiAES.decrypt(setting.getProperty("DATABASEWA"))
                + "?zeroDateTimeBehavior=convertToNull&amp;autoReconnect=true");
        source.setUser(EnkripsiAES.decrypt(setting.getProperty("USERWA")));
        source.setPassword(EnkripsiAES.decrypt(setting.getProperty("PASWA")));

        return source.getConnection();
    }

    public static Connection condb(){ 
        if(connection == null){
            try{
                connection=newConnection();
                System.out.println("  Koneksi Berhasil. Menyambungkan ke database bridging Gateway WA...!!!");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Koneksi ke server bridging Gateway WA terputus : "+e);
            }
        }
        return connection;        
    }
    private static void load() {
        try {
            if(prop.isEmpty()){
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Gagal load setting database WA : "+e);
        }
    }
    public static String FOLDERWA(){
        load();
        return prop.getProperty("FOLDERWA", "").trim();
    }

    public static String WAHA_BASE_URL() {
        load();
        return prop.getProperty("WAHA_BASE_URL", "").trim();
    }

    public static String SESSION() {
        load();
        return prop.getProperty("WAHA_SESSION", "").trim();
    }

    public static String getAPIKey() {
        load();
        return prop.getProperty("WAHA_API_KEY", "").trim();
    }
    public static String TOKEN() {
        load();
        return prop.getProperty("TOKEN", "").trim();
    }

    public static String FILE_BASE_URL() {
        load();
        return prop.getProperty("WAHA_FILE_BASE_URL", "").trim();
    }

    public static String WAHA_API_KEY() {
        load();
        return prop.getProperty("WAHA_API_KEY", "").trim();
    }
   
   //SETTING GOWA

//    public static String GOWA_BASE_URL() {
//        load();
//        return prop.getProperty("GOWA_BASE_URL", "").trim();
//    }
//
//    public static String GOWA_USERNAME() {
//        load();
//        return prop.getProperty("GOWA_USERNAME", "").trim();
//    }
//
//    public static String GOWA_PASSWORD() {
//        load();
//        return prop.getProperty("GOWA_PASSWORD", "").trim();
//    }
    public static String GOWA_BASE_URL() {
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var = EnkripsiAES.decrypt(prop.getProperty("GOWA_BASE_URL"));
        } catch (Exception e) {
            var = "";
        }
        return var;
    }

    public static String GOWA_USERNAME() {
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var = EnkripsiAES.decrypt(prop.getProperty("GOWA_USERNAME"));
        } catch (Exception e) {
            var = "";
        }
        return var;
    }

    public static String GOWA_PASSWORD() {
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var = EnkripsiAES.decrypt(prop.getProperty("GOWA_PASSWORD"));
        } catch (Exception e) {
            var = "";
        }
        return var;
    }

    public static String GOWA_DEVICE_ID() {
        String var = "";
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var = prop.getProperty("GOWA_DEVICE_ID");
        } catch (Exception e) {
            var = "";
        }
        return var;
    }
}
