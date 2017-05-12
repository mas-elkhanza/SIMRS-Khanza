/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author khanzasoft
 */
public final class koneksiDB {
    public koneksiDB(){}    
    private static Connection connection=null;
    private static final Properties prop = new Properties();  
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    private static String caricepat="";
    public static Connection condb(){      
        if(connection == null){
            try{
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                dataSource.setURL("jdbc:mysql://"+prop.getProperty("HOST")+":"+prop.getProperty("PORT")+"/"+prop.getProperty("DATABASE")+"?zeroDateTimeBehavior=convertToNull");
                dataSource.setUser(prop.getProperty("USER"));
                dataSource.setPassword(prop.getProperty("PAS"));
                connection=dataSource.getConnection();       
                System.out.println("Koneksi Berhasil. Sorry bro loading, silahkan baca dulu.... \n\n"+
                        "	Software ini adalah Software Menejemen Rumah Sakit/Klinik/\n" +
                        "  Puskesmas yang  gratis dan boleh digunakan siapa saja tanpa dikenai \n" +
                        "  biaya apapun. Dilarang keras memperjualbelikan/mengambil \n" +
                        "  keuntungan dari Software ini dalam bentuk apapun tanpa seijin pembuat \n" +
                        "  software (Khanza.Soft Media). Bagi yang sengaja memperjualbelikan/\n" +
                        "  mengambil keuntangan dari softaware ini tanpa ijin, kami sumpahi sial \n" +
                        "  1000 turunan, miskin sampai 500 turunan. Selalu mendapat kecelakaan \n" +
                        "  sampai 400 turunan. Anak pertamanya cacat tidak punya kaki sampai 300 \n" +
                        "  turunan. Susah cari jodoh sampai umur 50 tahun sampai 200 turunan.\n" +
                        "  Ya Alloh maafkan kami karena telah berdoa buruk, semua ini kami lakukan\n" +
                        "  karena kami tidak pernah rela karya kami dibajak tanpa ijin.\n\n");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Notifikasi : "+e);
            }
        }
        return connection;        
    }
    
    public static String cariCepat(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            caricepat=prop.getProperty("CARICEPAT");
        }catch(Exception e){
            caricepat="tidak aktif"; 
        }
        return caricepat;
    }
    
}
