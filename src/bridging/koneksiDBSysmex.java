/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

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
public class koneksiDBSysmex {
    private static Connection connection=null;
    private static final Properties prop = new Properties();  
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    
    public koneksiDBSysmex(){} 
    public static Connection condb(){ 
        if(connection == null){
            try{
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                dataSource.setURL("jdbc:mysql://"+EnkripsiAES.decrypt(prop.getProperty("HOSTSYSMEX"))+":"+EnkripsiAES.decrypt(prop.getProperty("PORTSYSMEX"))+"/"+EnkripsiAES.decrypt(prop.getProperty("DATABASESYSMEX"))+"?zeroDateTimeBehavior=convertToNull&amp;autoReconnect=true");
                dataSource.setUser(EnkripsiAES.decrypt(prop.getProperty("USERSYSMEX")));
                dataSource.setPassword(EnkripsiAES.decrypt(prop.getProperty("PASSYSMEX")));
                connection=dataSource.getConnection();       
                System.out.println("  Koneksi Berhasil. Menyambungkan ke database bridging Sysmex...!!!");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Koneksi ke server bridging Sysmex terputus : "+e);
            }
        }
        return connection;        
    }
    
}
