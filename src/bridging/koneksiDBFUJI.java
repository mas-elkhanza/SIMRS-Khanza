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
public class koneksiDBFUJI {
    private static Connection connection=null;
    private static final Properties prop = new Properties();  
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    
    public koneksiDBFUJI(){} 
    public static Connection condb(){ 
        if(connection == null){
            try{
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                dataSource.setURL("jdbc:mysql://"+EnkripsiAES.decrypt(prop.getProperty("HOSTFUJI"))+":"+EnkripsiAES.decrypt(prop.getProperty("PORTFUJI"))+"/"+EnkripsiAES.decrypt(prop.getProperty("DATABASEFUJI"))+"?zeroDateTimeBehavior=convertToNull&amp;autoReconnect=true");
                dataSource.setUser(EnkripsiAES.decrypt(prop.getProperty("USERFUJI")));
                dataSource.setPassword(EnkripsiAES.decrypt(prop.getProperty("PASFUJI")));
                connection=dataSource.getConnection();       
                System.out.println("  Koneksi Berhasil. Menyambungkan ke database bridging radiologi...!!!");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Koneksi ke server bridging radiologi terputus : "+e);
            }
        }
        return connection;        
    }
    
}
