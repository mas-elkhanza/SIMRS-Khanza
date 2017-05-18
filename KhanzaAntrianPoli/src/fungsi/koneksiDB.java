/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

/**
 *
 * @author Owner
 */
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Owner
 */
public final class koneksiDB {
    public koneksiDB(){}    
    private static Connection connection=null;
    private static final Properties prop = new Properties();  
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    public static Connection condb(){      
        if(connection == null){
            try{
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                dataSource.setURL("jdbc:mysql://"+prop.getProperty("HOST")+":"+prop.getProperty("PORT")+"/"+prop.getProperty("DATABASE")+"?zeroDateTimeBehavior=convertToNull");
                dataSource.setUser(prop.getProperty("USER"));
                dataSource.setPassword(prop.getProperty("PAS"));
                connection=dataSource.getConnection();       
                System.out.println("panggil driver");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Error : "+e);
            }
        }
        return connection;        
    }


}
