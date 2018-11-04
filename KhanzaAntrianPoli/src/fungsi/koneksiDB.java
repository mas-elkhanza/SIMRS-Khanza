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
public final class koneksiDB {
    public koneksiDB(){}    
    private static Connection connection=null;
    private static final Properties prop = new Properties();  
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    private static String caricepat="",var="";
    public static Connection condb(){      
        if(connection == null){
            try{
                prop.loadFromXML(new FileInputStream("setting/database.xml"));
                dataSource.setURL("jdbc:mysql://"+EnkripsiAES.decrypt(prop.getProperty("HOST"))+":"+EnkripsiAES.decrypt(prop.getProperty("PORT"))+"/"+EnkripsiAES.decrypt(prop.getProperty("DATABASE"))+"?zeroDateTimeBehavior=convertToNull");
                dataSource.setUser(EnkripsiAES.decrypt(prop.getProperty("USER")));
                dataSource.setPassword(EnkripsiAES.decrypt(prop.getProperty("PAS")));
                connection=dataSource.getConnection();       
                System.out.println("  Koneksi Berhasil. Sorry bro loading, silahkan baca dulu.... \n\n"+
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
                        "  karena kami tidak pernah rela karya kami dibajak tanpa ijin.\n\n"+
                        "                                                                           \n"+
                        "  #    ____  ___  __  __  ____   ____    _  __ _                              \n" +
                        "  #   / ___||_ _||  \\/  ||  _ \\ / ___|  | |/ /| |__    __ _  _ __   ____ __ _ \n" +
                        "  #   \\___ \\ | | | |\\/| || |_) |\\___ \\  | ' / | '_ \\  / _` || '_ \\ |_  // _` |\n" +
                        "  #    ___) || | | |  | ||  _ <  ___) | | . \\ | | | || (_| || | | | / /| (_| |\n" +
                        "  #   |____/|___||_|  |_||_| \\_\\|____/  |_|\\_\\|_| |_| \\__,_||_| |_|/___|\\__,_|\n" +
                        "  #                                                                           \n"+
                        "                                                                           \n"+
                        "  Licensi yang dianut di software ini https://en.wikipedia.org/wiki/Aladdin_Free_Public_License \n"+
                        "  Informasi dan panduan bisa dicek di halaman https://github.com/mas-elkhanza/SIMRS-Khanza/wiki \n"+
                        "                                                                           ");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Koneksi Putus : "+e);
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
    
    public static String HOST(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=EnkripsiAES.decrypt(prop.getProperty("HOSTHYBRIDWEB"));
        }catch(Exception e){
            var="localhost"; 
        }
        return var;
    }
    
    public static String PORT(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=EnkripsiAES.decrypt(prop.getProperty("PORT"));
        }catch(Exception e){
            var="3306"; 
        }
        return var;
    }
    
    public static String DATABASE(){
        try{
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            var=EnkripsiAES.decrypt(prop.getProperty("DATABASE"));
        }catch(Exception e){
            var="sik"; 
        }
        return var;
    }
    
    
}
