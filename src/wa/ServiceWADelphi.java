package wa;

import fungsi.koneksiDBWa;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ServiceWADelphi {
    
    public static boolean isNotifAktif() {
        try {
            Properties prop = new Properties();
            prop.loadFromXML(new FileInputStream("setting/database.xml"));

            return prop.getProperty("NOTIFWAKONTROL", "no")
                    .equalsIgnoreCase("yes");

        } catch (Exception e) {
            return false;
        }
    }
    private String now() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String formatNomor(String nomor) {

        nomor = nomor.replaceAll("[^0-9]", "");

        if (nomor.startsWith("0")) {
            nomor = "62" + nomor.substring(1);
        }

        return nomor + "@c.us";
    }

    // =============================
    // KIRIM TEXT
    // =============================
    public void kirimText(String nomor, String pesan, String tanggalJam, String source) throws Exception {

        Connection koneksi = koneksiDBWa.condb();

        String sql = "INSERT INTO wa_outbox "
                + "(NOWA,PESAN,TANGGAL_JAM,STATUS,SOURCE,SENDER,SUCCESS,RESPONSE,REQUEST,FILE,TYPE) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = koneksi.prepareStatement(sql);

        ps.setString(1, formatNomor(nomor));
        ps.setString(2, pesan);
        ps.setString(3, tanggalJam);
        ps.setString(4, "ANTRIAN");
        ps.setString(5, source);
        ps.setString(6, "NODEJS");
        ps.setString(7, null);
        ps.setString(8, null);
        ps.setString(9, null);
        ps.setString(10, null);
        ps.setString(11, "TEXT");

        ps.executeUpdate();
        ps.close();
    }


    // =============================
    // KIRIM FILE
    // =============================
    public void kirimFile(String nomor, String pesan, String namaFile) throws Exception {

        Connection koneksi = koneksiDBWa.condb();

        String sql = "INSERT INTO wa_outbox "
                + "(NOWA,PESAN,TANGGAL_JAM,STATUS,SOURCE,SENDER,SUCCESS,RESPONSE,REQUEST,TYPE,FILE) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = koneksi.prepareStatement(sql);

        ps.setString(1, formatNomor(nomor));
        ps.setString(2, pesan);
        ps.setString(3, now());
        ps.setString(4, "ANTRIAN");
        ps.setString(5, "KHANZA");
        ps.setString(6, "NODEJS");
        ps.setString(7, null);
        ps.setString(8, null);
        ps.setString(9, null);
        ps.setString(10, "FILE");
        ps.setString(11, namaFile);

        ps.executeUpdate();
        ps.close();
    }

}
