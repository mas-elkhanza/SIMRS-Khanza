/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package fungsi;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import uz.ncipro.calendar.JDateTimePicker;

/**
 *
 * @author Owner
 */
public final class sekuel {

    private javax.swing.ImageIcon icon = null;
    private javax.swing.ImageIcon iconThumbnail = null;
    private String folder, AKTIFKANTRACKSQL = koneksiDB.AKTIFKANTRACKSQL();
    private final Connection connect = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int angka = 0;
    private double angka2 = 0;
    private String dicari = "";
    private Date tanggal = new Date();
    private boolean bool = false;
    private DecimalFormat df2 = new DecimalFormat("####");

    /**
     *
     */
    public sekuel() {
        super();
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     */
    public void menyimpan(String table, String value, String sama) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            try {
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, gagal menyimpan data. Kemungkinan ada " + sama + " yang sama dimasukkan sebelumnya...!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack("insert into " + table + " values(" + value + ")");

        } catch (SQLException ex) {

            System.out.println(sekuel.class.getName() + ", " + ex);
        }

    }
    public void menyimpanx(String colom, String table, String value, String sama) {
        try {
            ps = connect.prepareStatement("insert into ("+colom +") "+ table + " values(" + value + ")");
            try {
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, gagal menyimpan data. Kemungkinan ada " + sama + " yang sama dimasukkan sebelumnya...!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack("insert into " + table + " values(" + value + ")");

        } catch (SQLException ex) {

            System.out.println(sekuel.class.getName() + ", " + ex);
        }

    }
    
    /**
     *
     * @param table
     * @param value
     * @param sama
     */
    public void menyimpan2(String table, String value, String sama) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            ps.executeUpdate();
            if (ps != null) {
                ps.close();
            }

            SimpanTrack("insert into " + table + " values(" + value + ")");
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     * @return
     */
    public boolean menyimpantf(String table, String value, String sama) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            ps.executeUpdate();
            if (ps != null) {
                ps.close();
            }

            SimpanTrack("insert into " + table + " values(" + value + ")");
            return true;
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
            JOptionPane.showMessageDialog(null, "Maaf, gagal menyimpan data. Kemungkinan ada " + sama + " yang sama dimasukkan sebelumnya...!");
            return false;
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     * @return
     */
    public boolean menyimpantf2(String table, String value, String sama) {
        try {
            System.out.println("Query : \ninsert into " + table + " \nvalues(\n" + value + "\n)\n");
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            ps.executeUpdate();
            if (ps != null) {
                ps.close();
            }

            SimpanTrack("insert into " + table + " values(" + value + ")");
            return true;
        } catch (Exception e) {
            System.out.println(sekuel.class.getName() + ", " + e.toString());
            return false;
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param i
     * @param a
     * @param acuan_field
     * @param update
     * @param j
     * @param b
     * @return
     */
    public boolean menyimpantf(String table, String value, int i, String[] a, String acuan_field, String update, int j,
            String[] b) {
        bool = false;
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            for (angka = 1; angka <= i; angka++) {
                ps.setString(angka, a[angka - 1]);
            }
            ps.executeUpdate();

            if (ps != null) {
                ps.close();
            }

            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");
            bool = true;
        } catch (Exception e) {
            try {
                ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
                for (angka = 1; angka <= j; angka++) {
                    ps.setString(angka, b[angka - 1]);
                }
                ps.executeUpdate();

                if (ps != null) {
                    ps.close();
                }

                if (AKTIFKANTRACKSQL.equals("yes")) {
                    dicari = "";
                    for (angka = 1; angka <= i; angka++) {
                        dicari = dicari + "|" + b[angka - 1];
                    }
                }
                SimpanTrack("update " + table + " set " + update + " " + dicari + " where " + acuan_field);
                bool = true;
            } catch (Exception e2) {
                bool = false;
                System.out.println("Notifikasi : " + e2);
            }
        }
        return bool;
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     * @param i
     * @param a
     */
    public void menyimpan(String table, String value, String sama, int i, String[] a) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            try {
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();

            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, gagal menyimpan data. Kemungkinan ada " + sama + " yang sama dimasukkan sebelumnya...!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     * @param i
     * @param a
     */
    public void menyimpan2(String table, String value, String sama, int i, String[] a) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            try {
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     * @param i
     * @param a
     * @return
     */
    public boolean menyimpantf(String table, String value, String sama, int i, String[] a) {
        try {
            System.out.println("Query: " + "insert into " + table + " values(" + value + ")");
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            for (angka = 1; angka <= i; angka++) {
                ps.setString(angka, a[angka - 1]);
            }
            ps.executeUpdate();

            if (ps != null) {
                ps.close();
            }

            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");
            return true;
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
            if (e.toString().contains("Duplicate")) {
                JOptionPane.showMessageDialog(null,
                        "Maaf, gagal menyimpan data. Kemungkinan ada " + sama + " yang sama dimasukkan sebelumnya...!");
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, gagal menyimpan data. Ada kesalahan Query...!");
            }
            return false;
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     * @param i
     * @param a
     * @return
     */
    public boolean menyimpantf2(String table, String value, String sama, int i, String[] a) {
        bool = true;
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            try {
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
                bool = true;
            } catch (Exception e) {
                bool = false;

                System.out.println(sekuel.class.getName() + ", " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");
        } catch (Exception e) {
            bool = false;
            System.out.println(sekuel.class.getName() + ", " + e);
        }
        return bool;
    }

    /**
     *
     * @param table
     * @param value
     * @param i
     * @param a
     */
    public void menyimpan(String table, String value, int i, String[] a) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");

            for (angka = 1; angka <= i; angka++) {
                ps.setString(angka, a[angka - 1]);
            }
            ps.executeUpdate();
            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");

        } catch (SQLException e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param i
     * @param a
     */
    public void menyimpan2(String table, String value, int i, String[] a) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            try {
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("Notifikasi " + table + " : " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param i
     * @param a
     * @param acuan_field
     * @param update
     * @param j
     * @param b
     */
    public void menyimpan(String table, String value, int i, String[] a, String acuan_field, String update, int j,
            String[] b) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            for (angka = 1; angka <= i; angka++) {
                ps.setString(angka, a[angka - 1]);
            }
            ps.executeUpdate();

            if (ps != null) {
                ps.close();
            }

            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");
        } catch (Exception e) {
            try {
                ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
                for (angka = 1; angka <= j; angka++) {
                    ps.setString(angka, b[angka - 1]);
                }
                ps.executeUpdate();

                if (ps != null) {
                    ps.close();
                }

                if (AKTIFKANTRACKSQL.equals("yes")) {
                    dicari = "";
                    for (angka = 1; angka <= i; angka++) {
                        dicari = dicari + "|" + b[angka - 1];
                    }
                }
                SimpanTrack("update " + table + " set " + update + " " + dicari + " where " + acuan_field);
            } catch (Exception e2) {
                System.out.println("Notifikasi : " + e2);
            }
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param i
     * @param a
     * @param acuan_field
     * @param update
     * @param j
     * @param b
     */
    public void menyimpan2(String table, String value, int i, String[] a, String acuan_field, String update, int j,
            String[] b) {
        try {
            dicari = "";
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            for (angka = 1; angka <= i; angka++) {
                dicari = dicari + ", " + a[angka - 1];
                ps.setString(angka, a[angka - 1]);
            }
            ps.executeUpdate();

            if (ps != null) {
                ps.close();
            }

            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");
        } catch (Exception e) {
            try {
                ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
                for (angka = 1; angka <= j; angka++) {
                    ps.setString(angka, b[angka - 1]);
                }
                ps.executeUpdate();

                if (ps != null) {
                    ps.close();
                }

                if (AKTIFKANTRACKSQL.equals("yes")) {
                    dicari = "";
                    for (angka = 1; angka <= i; angka++) {
                        dicari = dicari + "|" + b[angka - 1];
                    }
                }
                SimpanTrack("update " + table + " set " + update + " " + dicari + " where " + acuan_field);
            } catch (Exception e2) {
                System.out.println("Notifikasi : " + e2);
            }
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param i
     * @param a
     * @param acuan_field
     * @param update
     * @param j
     * @param b
     */
    public void menyimpan3(String table, String value, int i, String[] a, String acuan_field, String update, int j,
            String[] b) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            for (angka = 1; angka <= i; angka++) {
                ps.setString(angka, a[angka - 1]);
            }
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Proses simpan berhasil..!!");
            if (ps != null) {
                ps.close();
            }

            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("insert into " + table + " values(" + dicari + ")");
        } catch (Exception e) {
            try {
                ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
                for (angka = 1; angka <= j; angka++) {
                    ps.setString(angka, b[angka - 1]);
                }
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Proses simpan berhasil..!!");
                if (ps != null) {
                    ps.close();
                }
                if (AKTIFKANTRACKSQL.equals("yes")) {
                    dicari = "";
                    for (angka = 1; angka <= i; angka++) {
                        dicari = dicari + "|" + b[angka - 1];
                    }
                }
                SimpanTrack("update " + table + " set " + update + " " + dicari + " where " + acuan_field);
            } catch (Exception e2) {
                System.out.println("Notifikasi : " + e2);
            }
        }
    }

    /**
     *
     * @param table
     * @param value
     */
    public void menyimpan(String table, String value) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ")");
            try {
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack("insert into " + table + " values(" + value + ")");
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param isisimpan
     * @param isiedit
     * @param acuan_field
     */
    public void menyimpan(String table, String isisimpan, String isiedit, String acuan_field) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + isisimpan + ")");
            ps.executeUpdate();
            if (ps != null) {
                ps.close();
            }
            SimpanTrack("insert into " + table + " values(" + isisimpan + ")");
        } catch (Exception e) {
            try {
                ps = connect.prepareStatement("update " + table + " set " + isiedit + " where " + acuan_field);
                ps.executeUpdate();
                if (ps != null) {
                    ps.close();
                }
                SimpanTrack("update " + table + " set " + isiedit + " where " + acuan_field);
            } catch (Exception ex) {
                System.out.println("Notifikasi Edit : " + ex);
            }
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     * @param AlmGb
     */
    public void menyimpan(String table, String value, String sama, JTextField AlmGb) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ",?)");
            try {
                ps.setBinaryStream(1, new FileInputStream(AlmGb.getText()), new File(AlmGb.getText()).length());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(AlmGb, "Simpan Berhasil...");
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, gagal menyimpan data. Kemungkinan ada " + sama + " yang sama dimasukkan sebelumnya...!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     * @param AlmGb
     * @return
     */
    public boolean menyimpantf(String table, String value, String sama, JTextField AlmGb) {
        bool = true;
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ",?)");
            try {
                ps.setBinaryStream(1, new FileInputStream(AlmGb.getText()), new File(AlmGb.getText()).length());
                ps.executeUpdate();
                bool = true;
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                bool = false;
                JOptionPane.showMessageDialog(null,
                        "Maaf, gagal menyimpan data. Kemungkinan ada " + sama + " yang sama dimasukkan sebelumnya...!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
            bool = false;
        }
        return bool;
    }

    /**
     *
     * @param table
     * @param value
     * @param sama
     * @param AlmGb
     * @param AlmPhoto
     */
    public void menyimpan(String table, String value, String sama, JTextField AlmGb, JTextField AlmPhoto) {
        try {
            ps = connect.prepareStatement("insert into " + table + " values(" + value + ",?,?)");
            try {
                ps.setBinaryStream(1, new FileInputStream(AlmGb.getText()), new File(AlmGb.getText()).length());
                ps.setBinaryStream(2, new FileInputStream(AlmPhoto.getText()), new File(AlmPhoto.getText()).length());
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, gagal menyimpan data. Kemungkinan ada " + sama + " yang sama dimasukkan sebelumnya...!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param field
     * @param nilai_field
     */
    public void meghapus(String table, String field, String nilai_field) {
        try {
            ps = connect.prepareStatement("delete from " + table + " where " + field + "=?");
            try {
                ps.setString(1, nilai_field);
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, data gagal dihapus. Kemungkinan data tersebut masih dipakai di table lain...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack("delete from " + table + " where " + field + "='" + nilai_field + "'");
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param field
     * @param field2
     * @param nilai_field
     * @param nilai_field2
     */
    public void meghapus(String table, String field, String field2, String nilai_field, String nilai_field2) {
        try {
            ps = connect.prepareStatement("delete from " + table + " where " + field + "=? and " + field2 + "=?");
            try {
                ps.setString(1, nilai_field);
                ps.setString(2, nilai_field2);
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, data gagal dihapus. Kemungkinan data tersebut masih dipakai di table lain...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack("delete from " + table + " where " + field + "='" + nilai_field + "' and " + field2 + "='"
                    + nilai_field2 + "'");
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param field
     * @param nilai_field
     */
    public void meghapus2(String table, String field, String nilai_field) {
        try {
            ps = connect.prepareStatement("delete from " + table + " where " + field + "=?");
            try {
                ps.setString(1, nilai_field);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Proses hapus berhasil...!!!!");
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, data gagal dihapus. Kemungkinan data tersebut masih dipakai di table lain...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack("delete from " + table + " where " + field + "='" + nilai_field + "'");
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param field
     * @param nilai_field
     */
    public void meghapus3(String table, String field, String nilai_field) {
        try {
            ps = connect.prepareStatement("delete from " + table + " where " + field + "=?");
            try {
                ps.setString(1, nilai_field);
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack("delete from " + table + " where " + field + "='" + nilai_field + "'");
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param acuan_field
     * @param update
     */
    public void mengedit(String table, String acuan_field, String update) {
        try {
//            System.out.println("update " + table + " set " + update + " where " + acuan_field);
            ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
            try {
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, Gagal Mengedit. Mungkin kode sudah digunakan sebelumnya...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack("update " + table + " set " + update + " where " + acuan_field);
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param acuan_field
     * @param update
     * @return
     */
    public boolean mengedittf(String table, String acuan_field, String update) {
        bool = true;
        try {
            System.out.println("Query : " + "update " + table + " set " + update + " where " + acuan_field);
            ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
            try {
                ps.executeUpdate();
                bool = true;
            } catch (Exception e) {
                bool = false;

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, Gagal Mengedit. Mungkin kode sudah digunakan sebelumnya...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack("update " + table + " set " + update + " where " + acuan_field);
        } catch (Exception e) {
            bool = false;

            System.out.println(sekuel.class.getName() + ", " + e);
        }
        return bool;
    }

    /**
     *
     * @param table
     * @param acuan_field
     * @param update
     * @param i
     * @param a
     */
    public void mengedit(String table, String acuan_field, String update, int i, String[] a) {
        try {
//            System.out.println("update " + table + " set " + update + " where " + acuan_field);
            ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
            try {
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, Gagal Mengedit. Periksa kembali data...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("update " + table + " set " + update + " " + dicari + " where " + acuan_field);
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param acuan_field
     * @param update
     * @param i
     * @param a
     */
    public void mengedit2(String table, String acuan_field, String update, int i, String[] a) {
        try {
//            System.out.println("update " + table + " set " + update + " where " + acuan_field);
            ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
            try {
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Proses edit berhasil...!!!!");
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, Gagal mengedit. Periksa kembali data...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("update " + table + " set " + update + " " + dicari + " where " + acuan_field);
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param acuan_field
     * @param update
     * @param i
     * @param a
     */
    public void mengedit3(String table, String acuan_field, String update, int i, String[] a) {
        try {
            ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
            try {
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("update " + table + " set " + update + " " + dicari + " where " + acuan_field);
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }
    }

    /**
     *
     * @param table
     * @param acuan_field
     * @param update
     * @param i
     * @param a
     * @return
     */
    public boolean mengedittf(String table, String acuan_field, String update, int i, String[] a) {
        bool = true;
        try {
//            System.out.println("update " + table + " set " + update + " where " + acuan_field);
            ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
            try {
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
                bool = true;
            } catch (Exception e) {
                bool = false;

                System.out.println(sekuel.class.getName() + ", " + e);
                JOptionPane.showMessageDialog(null, "Maaf, Gagal Mengedit. Periksa kembali data...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack("update " + table + " set " + update + " " + dicari + " where " + acuan_field);
        } catch (Exception e) {
            bool = false;

        }
        return bool;
    }

    /**
     *
     * @param table
     * @param acuan_field
     * @param update
     * @param AlmGb
     */
    public void mengedit(String table, String acuan_field, String update, JTextField AlmGb) {
        try {
            ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
            try {
                ps.setBinaryStream(1, new FileInputStream(AlmGb.getText()), new File(AlmGb.getText()).length());
                ps.executeUpdate();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "Maaf, Pilih dulu data yang mau anda edit...\n Klik data pada table untuk memilih...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param table
     * @param acuan_field
     * @param update
     * @param AlmGb
     * @return
     */
    public boolean mengedittf(String table, String acuan_field, String update, JTextField AlmGb) {
        bool = true;
        try {
            ps = connect.prepareStatement("update " + table + " set " + update + " where " + acuan_field);
            try {
                ps.setBinaryStream(1, new FileInputStream(AlmGb.getText()), new File(AlmGb.getText()).length());
                ps.executeUpdate();
                bool = true;
            } catch (Exception e) {
                bool = false;

                JOptionPane.showMessageDialog(null, "Maaf, Pilih dulu data yang mau anda edit...\n Klik data pada table untuk memilih...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            bool = false;

        }
        return bool;
    }

    /**
     *
     * @param qry
     */
    public void query(String qry) {
        try {
            ps = connect.prepareStatement(qry);
            try {
                ps.executeQuery();
            } catch (Exception e) {
                System.out.println("error :" + e.toString());
                JOptionPane.showMessageDialog(null, "Maaf, Query tidak bisa dijalankan...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack(qry);
        } catch (Exception e) {
                System.out.println("error :" + e.toString());
        }
    }

    /**
     *
     * @param qry
     */
    public void queryu(String qry) {
        try {
            ps = connect.prepareStatement(qry);
            try {
                ps.executeUpdate();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "Maaf, Query tidak bisa dijalankan...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

            SimpanTrack(qry);
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param qry
     * @return
     */
    public boolean queryutf(String qry) {
        bool = false;
        try {
            ps = connect.prepareStatement(qry);
            try {
                ps.executeUpdate();
                bool = true;
            } catch (Exception e) {
                bool = false;

                JOptionPane.showMessageDialog(null, "Maaf, Query tidak bisa dijalankan...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack(qry);
        } catch (Exception e) {
            bool = false;

        }
        return bool;
    }

    /**
     *
     * @param qry
     * @param parameter
     */
    public void queryu(String qry, String parameter) {
        try {
            ps = connect.prepareStatement(qry);
            try {
                ps.setString(1, parameter);
                ps.executeUpdate();
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "Maaf, Query tidak bisa dijalankan...!!!!");
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack(qry);
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param qry
     */
    public void queryu2(String qry) {
        try {
            ps = connect.prepareStatement(qry);
            try {
                ps.executeUpdate();
            } catch (Exception e) {

            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            SimpanTrack(qry);
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param qry
     * @param i
     * @param a
     */
    public void queryu2(String qry, int i, String[] a) {
        try {
            try {
                ps = connect.prepareStatement(qry);
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
            } catch (Exception e) {

            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack(qry + " " + dicari);
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param qry
     * @param i
     * @param a
     * @return
     */
    public boolean queryu2tf(String qry, int i, String[] a) {
        bool = false;
//        System.out.println("Query: "+qry);
        try {
            try {
                ps = connect.prepareStatement(qry);
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
                bool = true;
            } catch (Exception e) {
                bool = false;

            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack(qry + " " + dicari);
        } catch (Exception e) {

        }
        return bool;
    }

    /**
     *
     * @param qry
     * @param i
     * @param a
     */
    public void queryu3(String qry, int i, String[] a) {
        try {
            try {
                ps = connect.prepareStatement(qry);
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
            } catch (Exception e) {

            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack(qry + " " + dicari);
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param qry
     * @param i
     * @param a
     */
    public void queryu4(String qry, int i, String[] a) {
        try {
            try {
                ps = connect.prepareStatement(qry);
                for (angka = 1; angka <= i; angka++) {
                    ps.setString(angka, a[angka - 1]);
                }
                ps.executeUpdate();
            } catch (Exception e) {
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
            if (AKTIFKANTRACKSQL.equals("yes")) {
                dicari = "";
                for (angka = 1; angka <= i; angka++) {
                    dicari = dicari + "|" + a[angka - 1];
                }
            }
            SimpanTrack(qry + " " + dicari);
        } catch (Exception e) {
        }
    }

    /**
     *
     */
    public void AutoComitFalse() {
        try {
            connect.setAutoCommit(false);
        } catch (Exception e) {
        }
    }

    /**
     *
     */
    public void AutoComitTrue() {
        try {
            connect.setAutoCommit(true);
        } catch (Exception e) {
        }
    }

    /**
     *
     */
    public void Commit() {
        try {
            connect.commit();
        } catch (Exception e) {
            System.err.println("Comit Connect Error : " + e.toString());
        }
    }

    /**
     *
     */
    public void RollBack() {
        try {
            connect.rollback();
            System.out.println("Rollback success, Cancel Transactions");
        } catch (Exception e) {
            System.err.println("Rollback Error : " + e.toString());
            JOptionPane.showMessageDialog(null, "Gagal melakukan rollback..!");
        }
    }

    /**
     *
     * @param sql
     * @param cmb
     */
    public void cariIsi(String sql, JComboBox cmb) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    String dicari = rs.getString(1);
                    cmb.setSelectedItem(dicari);
                } else {
                    cmb.setSelectedItem("");
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param sql
     * @param dtp
     */
    public void cariIsi(String sql, JDateTimePicker dtp) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    try {
                        dtp.setDisplayFormat("yyyy-MM-dd");
                        dtp.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(1)));
                        dtp.setDisplayFormat("dd-MM-yyyy");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param sql
     * @param txt
     */
    public void cariIsi(String sql, JTextField txt) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    txt.setText(rs.getString(1));
                } else {
                    txt.setText("");
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    public void cariIsi(String sql, JTextField txt1, JTextField txt2) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    txt1.setText(rs.getString(1));
                    txt2.setText(rs.getString(2));
                } else {
                    txt1.setText("");
                    txt2.setText("");
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    public void cariIsi(String sql, JTextArea txt) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    txt.setText(rs.getString(1));
                } else {
                    txt.setText("");
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param norawat
     * @return
     */
    public int cariRegistrasi(String norawat) {
        angka = 0;
        try {
            ps = connect.prepareStatement("select count(billing.no_rawat) from billing where billing.no_rawat=?");
            try {
                ps.setString(1, norawat);
                rs = ps.executeQuery();
                if (rs.next()) {
                    angka = rs.getInt(1);
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return angka;
    }

    /**
     *
     * @param sql
     * @param txt
     * @param kunci
     */
    public void cariIsi(String sql, JTextField txt, String kunci) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                ps.setString(1, kunci);
                rs = ps.executeQuery();
                if (rs.next()) {
                    txt.setText(rs.getString(1));
                } else {
                    txt.setText("");
                }
            } catch (SQLException e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param sql
     * @param txt
     * @param kunci
     */
    public void cariIsi(String sql, JTextArea txt, String kunci) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                ps.setString(1, kunci);
                rs = ps.executeQuery();
                if (rs.next()) {
                    txt.setText(rs.getString(1));
                } else {
                    txt.setText("");
                }
            } catch (SQLException e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param sql
     * @param txt
     */
    public void cariIsi(String sql, JLabel txt) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    txt.setText(rs.getString(1));
                } else {
                    txt.setText("");
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param sql
     * @return
     */
    public String cariIsi(String sql) {
        dicari = "";
        System.out.println("SQL="+sql);
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    dicari = rs.getString(1);
                } else {
                    dicari = "";
                }
            } catch (Exception e) {
                dicari = "";

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return dicari;
    }

    /**
     *
     * @param sql
     * @return
     */
    public ByteArrayInputStream cariGambar(String sql) {
        ByteArrayInputStream inputStream = null;
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    inputStream = new ByteArrayInputStream(rs.getBytes(1));
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return inputStream;
    }

    /**
     *
     * @param sql
     * @param data
     * @return
     */
    public String cariIsi(String sql, String data) {
        dicari = "";
        try {
            ps = connect.prepareStatement(sql);
            try {
                ps.setString(1, data);
                rs = ps.executeQuery();
                if (rs.next()) {
                    dicari = rs.getString(1);
                } else {
                    dicari = "";
                }
            } catch (Exception e) {
                dicari = "";

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Cari isi error = " + e);
        }

        return dicari;
    }

    /**
     *
     * @param sql
     * @return
     */
    public Date cariIsi2(String sql) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    tanggal = rs.getDate(1);
                } else {
                    tanggal = new Date();
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
        return tanggal;
    }

    /**
     *
     * @param sql
     * @return
     */
    public Integer cariInteger(String sql) {
        angka = 0;
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    angka = rs.getInt(1);
                } else {
                    angka = 0;
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return angka;
    }

    /**
     *
     * @param sql
     * @return
     */
    public Integer cariIntegerCount(String sql) {
        angka = 0;
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    angka = angka + rs.getInt(1);
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return angka;
    }

    /**
     *
     * @param sql
     * @param data
     * @return
     */
    public Integer cariInteger(String sql, String data) {
        angka = 0;
        try {
            ps = connect.prepareStatement(sql);
            try {
                ps.setString(1, data);
                rs = ps.executeQuery();
                if (rs.next()) {
                    angka = rs.getInt(1);
                } else {
                    angka = 0;
                }
            } catch (Exception e) {
                angka = 0;

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return angka;
    }

    /**
     *
     * @param sql
     * @param data
     * @param data2
     * @return
     */
    public Integer cariInteger(String sql, String data, String data2) {
        angka = 0;
        try {
            ps = connect.prepareStatement(sql);
            try {
                ps.setString(1, data);
                ps.setString(2, data2);
                rs = ps.executeQuery();
                if (rs.next()) {
                    angka = rs.getInt(1);
                } else {
                    angka = 0;
                }
            } catch (Exception e) {
                angka = 0;

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return angka;
    }

    /**
     *
     * @param sql
     * @param data
     * @param data2
     * @param data3
     * @return
     */
    public Integer cariInteger(String sql, String data, String data2, String data3) {
        angka = 0;
        try {
            ps = connect.prepareStatement(sql);
            try {
                ps.setString(1, data);
                ps.setString(2, data2);
                ps.setString(3, data3);
                rs = ps.executeQuery();
                if (rs.next()) {
                    angka = rs.getInt(1);
                } else {
                    angka = 0;
                }
            } catch (Exception e) {
                angka = 0;

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return angka;
    }

    /**
     *
     * @param sql
     * @return
     */
    public Integer cariInteger2(String sql) {
        angka = 0;
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                rs.last();
                angka = rs.getRow();
                if (angka < 1) {
                    angka = 0;
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return angka;
    }

    /**
     *
     * @param sql
     * @param txt
     */
    public void cariIsiAngka(String sql, JTextField txt) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    txt.setText(df2.format(rs.getDouble(1)));
                } else {
                    txt.setText("0");
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param sql
     * @param txt
     */
    public void cariIsiAngka(String sql, JLabel txt) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    txt.setText(df2.format(rs.getDouble(1)));
                } else {
                    txt.setText("0");
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param sql
     * @return
     */
    public double cariIsiAngka(String sql) {
        angka2 = 0;
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    angka2 = rs.getDouble(1);
                } else {
                    angka2 = 0;
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return angka2;
    }

    /**
     *
     * @param sql
     * @param data
     * @return
     */
    public double cariIsiAngka(String sql, String data) {
        angka2 = 0;
        try {
            ps = connect.prepareStatement(sql);
            try {
                ps.setString(1, data);
                rs = ps.executeQuery();
                if (rs.next()) {
                    angka2 = rs.getDouble(1);
                } else {
                    angka2 = 0;
                }
                // rs.close();
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return angka2;
    }

    /**
     *
     * @param sql
     * @param data
     * @param data2
     * @return
     */
    public double cariIsiAngka2(String sql, String data, String data2) {
        angka2 = 0;
        try {
            ps = connect.prepareStatement(sql);
            try {
                ps.setString(1, data);
                ps.setString(2, data2);
                rs = ps.executeQuery();
                if (rs.next()) {
                    angka2 = rs.getDouble(1);
                } else {
                    angka2 = 0;
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

        return angka2;
    }

    /**
     *
     * @param sql
     * @param txt
     */
    public void cariGambar(String sql, JLabel txt) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    icon = new javax.swing.ImageIcon(rs.getBlob(1).getBytes(1L, (int) rs.getBlob(1).length()));
                    createThumbnail();
                    txt.setIcon(icon);
                } else {
                    txt.setText(null);
                }
            } catch (Exception e) {

            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     *
     * @param sql
     * @param txt
     * @param text
     */
    public void cariGambar(String sql, java.awt.Canvas txt, String text) {
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                for (int I = 0; rs.next(); I++) {
                    ((Painter) txt).setImage(gambar(text));
                    Blob blob = rs.getBlob(5);
                    ((Painter) txt).setImageIcon(new javax.swing.ImageIcon(blob.getBytes(1, (int) (blob.length()))));
                }
            } catch (Exception e) {
                cetak(e.toString());
            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

        }

    }

    private void SimpanTrack(String sql) {
        if (AKTIFKANTRACKSQL.equals("yes")) {
            try {
                ps = connect.prepareStatement("insert into trackersql values(null,now(),?,?)");
                try {
                    ps.setString(1, sql);
                    ps.setString(2, akses.getkode());
                    ps.executeUpdate();
                    //Logger.getLogger(sekuel.class.getName()).log(Level.INFO, sql);
                    //System.out.println(sekuel.class.getName() + ", " + sql);
                } catch (Exception e) {
                    System.out.println(sekuel.class.getName() + ", " + "QUERY SQL: " + sql + "\nERROR: " + e.toString());
                    Logger.getLogger(sekuel.class.getName()).log(Level.FATAL, sql);
                } finally {
                    if (ps != null) {
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(sekuel.class.getName() + ", " + e);
                Logger.getLogger(sekuel.class.getName()).log(Level.FATAL, e);
            }
        }
    }

    /**
     *
     * @param sql
     * @return
     */
    public String cariString(String sql) {
        dicari = "";
        try {
            ps = connect.prepareStatement(sql);
            try {
                rs = ps.executeQuery();
                if (rs.next()) {
                    dicari = rs.getString(1);
                } else {
                    dicari = "";
                }
            } catch (Exception e) {

                System.out.println(sekuel.class.getName() + ", " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {

            System.out.println(sekuel.class.getName() + ", " + e);
        }

        return dicari;
    }

    private String gambar(String id) {
        return folder + File.separator + id.trim() + ".jpg";
    }

    /**
     *
     * @param tb
     * @param lebar
     */
    public void Tabel(javax.swing.JTable tb, int lebar[]) {
        tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        angka = tb.getColumnCount();
        for (int i = 0; i < angka; i++) {
            javax.swing.table.TableColumn tbc = tb.getColumnModel().getColumn(i);
            tbc.setPreferredWidth(lebar[i]);
            // tb.setRowHeight(17);
        }
    }

    private void createThumbnail() {
        int maxDim = 150;
        try {
            Image inImage = icon.getImage();

            double scale = (double) maxDim / (double) inImage.getHeight(null);
            if (inImage.getWidth(null) > inImage.getHeight(null)) {
                scale = (double) maxDim / (double) inImage.getWidth(null);
            }

            int scaledW = (int) (scale * inImage.getWidth(null));
            int scaledH = (int) (scale * inImage.getHeight(null));

            BufferedImage outImage = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);

            AffineTransform tx = new AffineTransform();

            if (scale < 1.0d) {
                tx.scale(scale, scale);
            }

            Graphics2D g2d = outImage.createGraphics();
            g2d.drawImage(inImage, tx, null);
            g2d.dispose();

            iconThumbnail = new javax.swing.ImageIcon(outImage);
        } catch (Exception e) {
        }
    }

    private void cetak(String str) {
        System.out.println(str);
    }

    /**
     *
     */
    public class Painter extends Canvas {

        Image image;

        private void setImage(String file) {
            URL url = null;
            try {
                url = new File(file).toURI().toURL();
            } catch (MalformedURLException e) {
                cetak(e.toString());
            }
            image = getToolkit().getImage(url);
            repaint();
        }

        private void setImageIcon(ImageIcon file) {
            image = file.getImage();
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            double d = image.getHeight(this) / this.getHeight();
            double w = image.getWidth(this) / d;
            double x = this.getWidth() / 2 - w / 2;
            g.drawImage(image, (int) x, 0, (int) (w), this.getHeight(), this);
        }

        private void cetak(String str) {
            System.out.println(str);
        }
    }

    /**
     *
     */
    public class NIOCopier {

        /**
         *
         * @param asal
         * @param tujuan
         * @throws IOException
         */
        public NIOCopier(String asal, String tujuan) throws IOException {
            FileOutputStream outFile;
            try (FileInputStream inFile = new FileInputStream(asal)) {
                outFile = new FileOutputStream(tujuan);
                FileChannel outChannel;
                try (FileChannel inChannel = inFile.getChannel()) {
                    outChannel = outFile.getChannel();
                    for (ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024); inChannel.read(buffer) != -1; buffer
                            .clear()) {
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            outChannel.write(buffer);
                        }
                    }
                }
                outChannel.close();
            }
            outFile.close();
        }
    }

}
