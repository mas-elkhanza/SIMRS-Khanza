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
public class koneksiDBLISYPM {
    private static Connection connection=null;
    private static final Properties prop = new Properties();  
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    
    public koneksiDBLISYPM(){} 
    public static Connection condb(){ 
        if(connection == null){
            try{
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                dataSource.setURL("jdbc:mysql://"+EnkripsiAES.decrypt(prop.getProperty("HOSTLISYPM"))+":"+EnkripsiAES.decrypt(prop.getProperty("PORTLISYPM"))+"/"+EnkripsiAES.decrypt(prop.getProperty("DATABASELISYPM"))+"?zeroDateTimeBehavior=convertToNull&autoReconnect=true&useCompression=true");
                dataSource.setUser(EnkripsiAES.decrypt(prop.getProperty("USERLISYPM")));
                dataSource.setPassword(EnkripsiAES.decrypt(prop.getProperty("PASLISYPM")));
                dataSource.setCachePreparedStatements(true);
                dataSource.setUseCompression(true);
                dataSource.setUseLocalSessionState(true);
                dataSource.setUseLocalTransactionState(true);
                connection=dataSource.getConnection();       
                System.out.println("  Koneksi Berhasil. Menyambungkan ke database bridging LISYPM...!!!");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Koneksi ke server bridging LISYPM terputus : "+e);
            }
        }
        return connection;        
    }
    
}
