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
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author khanzasoft
 */
public class koneksiDB {
    private static String var="";
    private static volatile Connection connection;
    private static final MysqlDataSource dataSource=new MysqlDataSource();
    private static final Properties prop=new Properties();
    private static final AtomicBoolean initialized=new AtomicBoolean(false);
    private static final Object LOCK=new Object();
    private static volatile long lastCheck =0;
    private static final long CHECK_INTERVAL =30000;
    
    private koneksiDB(){}
    
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(koneksiDB::closeConnection));
    }

    public static Connection condb() {
        try {
            if (!initialized.get()) {
                synchronized (LOCK) {
                    if (!initialized.get()) {
                        initDataSource();
                        reconnect();
                        initialized.set(true);
                    }
                }
            }

            long now = System.currentTimeMillis();
            if (now - lastCheck > CHECK_INTERVAL) {
                lastCheck = now;
                if (!isConnectionAlive()) {
                    synchronized (LOCK) {
                        if (!isConnectionAlive()) {
                            reconnect();
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            Logger.getLogger(koneksiDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return connection;
    }

    private static void initDataSource() throws Exception {
        try (FileInputStream fis =new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
        }
        dataSource.setURL("jdbc:mysql://"+EnkripsiAES.decrypt(prop.getProperty("HOST"))+":"+EnkripsiAES.decrypt(prop.getProperty("PORT"))+"/"+EnkripsiAES.decrypt(prop.getProperty("DATABASE"))+"?zeroDateTimeBehavior=convertToNull&tcpKeepAlive=true&connectTimeout=10000&socketTimeout=60000&maintainTimeStats=false");
        dataSource.setUser(EnkripsiAES.decrypt(prop.getProperty("USER")));
        dataSource.setPassword(EnkripsiAES.decrypt(prop.getProperty("PAS")));
        dataSource.setCachePreparedStatements(true);
        dataSource.setUseCompression(true);
    }

    private static boolean isConnectionAlive() {
        try {
            if (connection == null) return false;
            if (connection.isClosed()) return false;
            if (!connection.isValid(3)) return false;
            try (Statement st=connection.createStatement()) {
                st.executeQuery("SELECT 1");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void reconnect() throws SQLException {
        closeConnection();
        int retries = 5;
        while (retries > 0) {
            try {
                connection=dataSource.getConnection();
                connection.setAutoCommit(true);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                System.out.println(
                    "  Koneksi Berhasil. Sorry bro loading, silahkan baca dulu.... \n\n"+
                    "	Software ini adalah Software Menejemen Rumah Sakit/Klinik/\n" +
                    "  Puskesmas yang  gratis dan boleh digunakan siapa saja tanpa dikenai \n" +
                    "  biaya apapun. Dilarang keras memperjualbelikan/mengambil \n" +
                    "  keuntungan dari Software ini dalam bentuk apapun tanpa seijin pembuat \n" +
                    "  software (Khanza.Soft Media). Bagi yang sengaja memperjualbelikan/\n"+
                    "  mengambil keuntangan dari softaware ini tanpa ijin, kami  sumpahi sial\n"+
                    "  1000 turunan, miskin sampai 500 turunan.\n"+
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
                    "  Bagi yang ingin berdonasi untuk pengembangan aplikasi ini bisa ke BSI 1015369872 atas nama Windiarto\n"+
                    "                                                                           "
                );         
                return;
            } catch (SQLException e) {
                retries--;
                JOptionPane.showMessageDialog(null,"Gagal koneksi ke database. Sisa percobaan : " + retries);
                if (retries == 0) {
                    JOptionPane.showMessageDialog(null, "Koneksi ke database gagal. Silakan periksa koneksi jaringan atau konfigurasi database.");
                    throw new SQLException("Gagal koneksi ke database setelah beberapa percobaan.", e);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new SQLException("Thread terinterupsi saat mencoba koneksi."+ie);
                }
            }
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null &&!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(koneksiDB.class.getName()).log(Level.SEVERE, null, e);
        }
        connection = null;
    }
    
    public static String HOST(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("HOST"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String DATABASE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("DATABASE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PORT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PORT"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USER(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USER"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CARICEPAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("CARICEPAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String HOSTHYBRIDWEB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("HOSTHYBRIDWEB"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERHYBRIDWEB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERHYBRIDWEB"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASHYBRIDWEB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASHYBRIDWEB"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String HYBRIDWEB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("HYBRIDWEB");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PORTWEB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("PORTWEB");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ANTRIAN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ANTRIAN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ALARMAPOTEK(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ALARMAPOTEK");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String FORMALARMAPOTEK(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("FORMALARMAPOTEK");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ALARMLAB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ALARMLAB");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String FORMALARMLAB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("FORMALARMLAB");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ALARMRADIOLOGI(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ALARMRADIOLOGI");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String FORMALARMRADIOLOGI(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("FORMALARMRADIOLOGI");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ALARMRSISRUTE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ALARMRSISRUTE");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ALARMBOOKINGPERIKSA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ALARMBOOKINGPERIKSA");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ALARMPERMINTAANRANAP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ALARMPERMINTAANRANAP");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ALARMPENGADUANPASIEN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ALARMPENGADUANPASIEN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String MENUTRANSPARAN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("MENUTRANSPARAN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPIBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPIBPJS");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYAPIBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYAPIBPJS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CONSIDAPIBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CONSIDAPIBPJS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERKEYAPIBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERKEYAPIBPJS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPIAPLICARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPIAPLICARE");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYAPIAPLICARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYAPIAPLICARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CONSIDAPIAPLICARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CONSIDAPIAPLICARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERKEYAPIAPLICARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERKEYAPIAPLICARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPIMOBILEJKN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPIMOBILEJKN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYAPIMOBILEJKN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYAPIMOBILEJKN"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CONSIDAPIMOBILEJKN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CONSIDAPIMOBILEJKN"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERKEYAPIMOBILEJKN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERKEYAPIMOBILEJKN"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPIAPOTEKBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPIAPOTEKBPJS");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYAPIAPOTEKBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYAPIAPOTEKBPJS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CONSIDAPIAPOTEKBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CONSIDAPIAPOTEKBPJS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERKEYAPIAPOTEKBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERKEYAPIAPOTEKBPJS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String JADIKANPIUTANGAPOTEKBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("JADIKANPIUTANGAPOTEKBPJS"));
        }catch(Exception e){
            var="no"; 
        }
        return var;
    }
    
    public static String URLAPIPCARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPIPCARE");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYAPIPCARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYAPIPCARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CONSIDAPIPCARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CONSIDAPIPCARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERKEYAPIPCARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERKEYAPIPCARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSPCARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSPCARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERPCARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERPCARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String DIVREGPCARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("DIVREGPCARE");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KACABPCARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("KACABPCARE");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPISISRUTE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPISISRUTE");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String IDSISRUTE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("IDSISRUTE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSSISRUTE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSSISRUTE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPISIRS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPISIRS");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String IDSIRS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("IDSIRS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSSIRS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSSIRS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPICORONA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPICORONA");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String IDCORONA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("IDCORONA"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSCORONA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSCORONA"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPISITT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPISITT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String IDSITT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("IDSITT"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSSITT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSSITT"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KABUPATENSITT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("KABUPATENSITT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KAMARAKTIFRANAP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("KAMARAKTIFRANAP").replaceAll("'","");;
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String DOKTERAKTIFKASIRRALAN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("DOKTERAKTIFKASIRRALAN").replaceAll("'","");;
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String POLIAKTIFKASIRRALAN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("POLIAKTIFKASIRRALAN").replaceAll("'","");;
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String RUANGANAKTIFINVENTARIS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("RUANGANAKTIFINVENTARIS").replaceAll("'","");;
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String BASENOREG(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("BASENOREG");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String VALIDASIULANGBERIOBAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("VALIDASIULANGBERIOBAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URUTNOREG(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URUTNOREG");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String JADWALDOKTERDIREGISTRASI(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("JADWALDOKTERDIREGISTRASI");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String IPPRINTERTRACER(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("IPPRINTERTRACER");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPIINHEALTH(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPIINHEALTH");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String TOKENINHEALTH(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("TOKENINHEALTH"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PEMBULATANHARGAOBAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("PEMBULATANHARGAOBAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String AKTIFKANBATCHOBAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("AKTIFKANBATCHOBAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CETAKRINCIANOBAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("CETAKRINCIANOBAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String AKTIFKANBILLINGPARSIAL(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("AKTIFKANBILLINGPARSIAL");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLDUKCAPILJAKARTA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLDUKCAPILJAKARTA");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERDUKCAPILJAKARTA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERDUKCAPILJAKARTA"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSDUKCAPILJAKARTA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSDUKCAPILJAKARTA"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String VAR1DUKCAPILJAKARTA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("VAR1DUKCAPILJAKARTA");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String VAR2DUKCAPILJAKARTA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("VAR2DUKCAPILJAKARTA");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLDUKCAPIL(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLDUKCAPIL");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERDUKCAPIL(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERDUKCAPIL"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSDUKCAPIL(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSDUKCAPIL"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String IPUSERDUKCAPIL(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("IPUSERDUKCAPIL");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String AKTIFKANTRACKSQL(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("AKTIFKANTRACKSQL"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String HOSTWSLICA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("HOSTWSLICA");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KEYWSLICA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("KEYWSLICA"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String DEPOAKTIFOBAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("DEPOAKTIFOBAT").replaceAll("'","");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String STOKKOSONGRESEP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("STOKKOSONGRESEP");
        }catch(Exception e){
            var="no"; 
        }
        return var;
    }
    
    public static String TAMPILKANCOPYRESEPDOKTERLAIN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("TAMPILKANCOPYRESEPDOKTERLAIN");
        }catch(Exception e){
            var="no"; 
        }
        return var;
    }
    
    public static String HPPFARMASI(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            if(prop.getProperty("HPPFARMASI").equals("h_beli")){
                var="h_beli";
            }else{
                var="dasar";
            }
        }catch(Exception e){
            var="dasar"; 
        }
        return var;
    }
    
    public static String HPPTOKO(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            if(prop.getProperty("HPPTOKO").equals("h_beli")){
                var="h_beli";
            }else{
                var="dasar";
            }
        }catch(Exception e){
            var="dasar"; 
        }
        return var;
    }
    
    public static String URLAPIMEDQLAB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPIMEDQLAB");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYAPIMEDQLAB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYAPIMEDQLAB"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CONSIDAPIMEDQLAB(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CONSIDAPIMEDQLAB"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLCARESTREAM(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLCARESTREAM");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPISOFTMEDIX(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPISOFTMEDIX");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PRODUCTSOFTMEDIX(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PRODUCTSOFTMEDIX"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String VERSIONSOFTMEDIX(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("VERSIONSOFTMEDIX"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERIDSOFTMEDIX(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERIDSOFTMEDIX"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KEYSOFTMEDIX(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("KEYSOFTMEDIX"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String RESEPRAJALKEPLAN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("RESEPRAJALKEPLAN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String DIAGNOSARUJUKANMASUKAPIBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("DIAGNOSARUJUKANMASUKAPIBPJS");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String AKTIFKANWARNARALAN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("AKTIFKANWARNARALAN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CLIENTIDSATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CLIENTIDSATUSEHAT"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYSATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYSATUSEHAT"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String IDSATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("IDSATUSEHAT"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAUTHSATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAUTHSATUSEHAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLFHIRSATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLFHIRSATUSEHAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KELURAHANSATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("KELURAHANSATUSEHAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KECAMATANSATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("KECAMATANSATUSEHAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KABUPATENSATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("KABUPATENSATUSEHAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PROPINSISATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("PROPINSISATUSEHAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KODEPOSSATUSEHAT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("KODEPOSSATUSEHAT");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERORTHANC(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERORTHANC"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSORTHANC(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSORTHANC"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PORTORTHANC(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PORTORTHANC"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLORTHANC(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLORTHANC");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ADDANTRIANAPIMOBILEJKN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ADDANTRIANAPIMOBILEJKN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String JADIKANBOOKINGSURATKONTROL(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("JADIKANBOOKINGSURATKONTROL");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String JADIKANBOOKINGSURATKONTROLAPIBPJS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("JADIKANBOOKINGSURATKONTROLAPIBPJS");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPIICARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPIICARE");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYAPIICARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYAPIICARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CONSIDAPIICARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CONSIDAPIICARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERKEYAPIICARE(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERKEYAPIICARE"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPISMARTCLAIM(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPISMARTCLAIM");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYAPISMARTCLAIM(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYAPISMARTCLAIM"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CONSIDAPISMARTCLAIM(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CONSIDAPISMARTCLAIM"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERKEYAPISMARTCLAIM(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERKEYAPISMARTCLAIM"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String TANGGALMUNDUR(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("TANGGALMUNDUR");
        }catch(Exception e){
            var="yes"; 
        }
        return var;
    }
    
    public static String URLMOBILEJKNFKTP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLMOBILEJKNFKTP");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SECRETKEYMOBILEJKNFKTP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SECRETKEYMOBILEJKNFKTP"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String CONSIDMOBILEJKNFKTP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("CONSIDMOBILEJKNFKTP"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERKEYMOBILEJKNFKTP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERKEYMOBILEJKNFKTP"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSMOBILEJKNFKTP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSMOBILEJKNFKTP"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERMOBILEJKNFKTP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERMOBILEJKNFKTP"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPMANDIRIPATHPEMBAYARANPIHAKKETIGA(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPMANDIRIPATHPEMBAYARANPIHAKKETIGA"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPMANDIRIPATHPEMBAYARANPAJAK(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPMANDIRIPATHPEMBAYARANPAJAK"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPMANDIRIPATHPEMBAYARANVIRTUALACCOUNT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPMANDIRIPATHPEMBAYARANVIRTUALACCOUNT"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPMANDIRIPATHACK(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPMANDIRIPATHACK"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPMANDIRIPATHMT940(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPMANDIRIPATHMT940"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPMANDIRIHOST(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPMANDIRIHOST"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPMANDIRIPORT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPMANDIRIPORT"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPMANDIRIUSER(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPMANDIRIUSER"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPMANDIRIPAS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPMANDIRIPAS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String KUNCIDOKTERRANAP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("KUNCIDOKTERRANAP"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String ADDANTRIANAPIMOBILEJKNFKTP(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("ADDANTRIANAPIMOBILEJKNFKTP");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPIESIGN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPIESIGN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String USERNAMEAPIESIGN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("USERNAMEAPIESIGN"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String PASSAPIESIGN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("PASSAPIESIGN"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPFILEESIGNHOST(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPFILEESIGNHOST"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPFILEESIGNPORT(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPFILEESIGNPORT"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPFILEESIGNUSER(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPFILEESIGNUSER"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPFILEESIGNPAS(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPFILEESIGNPAS"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String SFTPFILEESIGNFOLDER(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("SFTPFILEESIGNFOLDER"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAKSESFILEESIGN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAKSESFILEESIGN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLAPISERTISIGN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLAPISERTISIGN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String APIKEYSERTISIGN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=EnkripsiAES.decrypt(prop.getProperty("APIKEYSERTISIGN"));
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
    
    public static String URLDOKUMENSERTISIGN(){
        try (FileInputStream fis = new FileInputStream("setting/database.xml")) {
            prop.loadFromXML(fis);
            var=prop.getProperty("URLDOKUMENSERTISIGN");
        }catch(Exception e){
            var=""; 
        }
        return var;
    }
}