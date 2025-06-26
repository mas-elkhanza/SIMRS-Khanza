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
public class koneksiDBDutaParking {
    private static Connection connection=null;
    private static final Properties prop = new Properties();  
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    
    public koneksiDBDutaParking(){} 
    public static Connection condb(){ 
        if(connection == null){
            try{
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                dataSource.setURL("jdbc:mysql://"+EnkripsiAES.decrypt(prop.getProperty("HOSTDUTAPARKING"))+":"+EnkripsiAES.decrypt(prop.getProperty("PORTDUTAPARKING"))+"/"+EnkripsiAES.decrypt(prop.getProperty("DATABASEDUTAPARKING"))+"?zeroDateTimeBehavior=convertToNull&autoReconnect=true&useCompression=true");
                dataSource.setUser(EnkripsiAES.decrypt(prop.getProperty("USERDUTAPARKING")));
                dataSource.setPassword(EnkripsiAES.decrypt(prop.getProperty("PASDUTAPARKING")));
                dataSource.setCachePreparedStatements(true);
                dataSource.setUseCompression(true);
                dataSource.setUseLocalSessionState(true);
                dataSource.setUseLocalTransactionState(true);
                connection=dataSource.getConnection();       
                System.out.println("  Koneksi Berhasil. Menyambungkan ke database bridging ELIMS...!!!");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Koneksi ke server bridging ELIMS terputus : "+e);
            }
        }
        return connection;        
    }
    
}
