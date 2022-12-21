/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgAdmin.java
 *
 * Created on 04 Des 13, 12:59:34
 */
package khanzahmsanjungan;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Kode
 */
public class DlgRegistrasi extends javax.swing.JDialog {
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private String umur="0",sttsumur="Th";
    private DlgPilihBayar pilihbayar=new DlgPilihBayar(null,true);
    private String status="Baru",BASENOREG="",URUTNOREG="",aktifjadwal="";
    private Properties prop = new Properties();

    /** Creates new form DlgAdmin
     * @param parent
     * @param id */
    public DlgRegistrasi(java.awt.Frame parent, boolean id) {
        super(parent, id);
        initComponents();
       
        try{
           ps=koneksi.prepareStatement(
                   "select nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"+
                        "namakeluarga,keluarga,pasien.kd_pj,penjab.png_jawab,if(tgl_daftar=?,'Baru','Lama') as daftar, "+
                        "TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) as tahun, "+
                        "(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12)) as bulan, "+
                        "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten inner join penjab "+
                        "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_pj=penjab.kd_pj "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?"); 
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        pilihbayar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pilihbayar.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pilihbayar.getTable().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                pilihbayar.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
        });
        
        pilihbayar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                if(pilihbayar.getTable().getSelectedRow()!= -1){
                    KdBayar.setText(pilihbayar.getTable().getValueAt(pilihbayar.getTable().getSelectedRow(),0).toString());
                    NmBayar.setText(pilihbayar.getTable().getValueAt(pilihbayar.getTable().getSelectedRow(),1).toString());
                }  
                pilihbayar.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifjadwal=prop.getProperty("JADWALDOKTERDIREGISTRASI");
            URUTNOREG=prop.getProperty("URUTNOREG");
            BASENOREG=prop.getProperty("BASENOREG");
        } catch (Exception ex) {
            aktifjadwal="";         
            URUTNOREG="";
            BASENOREG="";
        }
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LblKdPoli = new component.Label();
        LblKdDokter = new component.Label();
        Tanggal = new component.Tanggal();
        NoReg = new component.TextBox();
        NoRawat = new component.TextBox();
        Biaya = new component.TextBox();
        jPanel1 = new component.Panel();
        jPanel2 = new component.Panel();
        jLabel2 = new component.Label();
        jLabel6 = new component.Label();
        LblNama = new component.Label();
        jLabel8 = new component.Label();
        jLabel9 = new component.Label();
        LblNoRm = new component.Label();
        jLabel5 = new component.Label();
        LblNamaPoli = new component.Label();
        jLabel7 = new component.Label();
        jLabel10 = new component.Label();
        jLabel11 = new component.Label();
        LblNoReg = new component.Label();
        jLabel12 = new component.Label();
        jLabel13 = new component.Label();
        LblNoRawat = new component.Label();
        LblTanggal = new component.Label();
        jLabel14 = new component.Label();
        jLabel15 = new component.Label();
        LblJam = new component.Label();
        jLabel16 = new component.Label();
        LblDokter = new component.Label();
        jLabel17 = new component.Label();
        jLabel18 = new component.Label();
        jLabel19 = new component.Label();
        PngJawab = new component.TextBox();
        jLabel20 = new component.Label();
        jLabel21 = new component.Label();
        HubunganPngJawab = new component.TextBox();
        jLabel22 = new component.Label();
        jLabel23 = new component.Label();
        AlamatPngJawab = new component.TextBox();
        Status = new component.TextBox();
        jLabel24 = new component.Label();
        jLabel25 = new component.Label();
        jLabel26 = new component.Label();
        KdBayar = new component.TextBox();
        jLabel27 = new component.Label();
        NmBayar = new component.TextBox();
        btnSemua = new component.Button();
        jPanel4 = new component.Panel();
        btnSimpan = new component.Button();
        btnKeluar = new component.Button();

        LblKdPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblKdPoli.setText("Norm");
        LblKdPoli.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblKdPoli.setPreferredSize(new java.awt.Dimension(20, 14));

        LblKdDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblKdDokter.setText("Norm");
        LblKdDokter.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblKdDokter.setPreferredSize(new java.awt.Dimension(20, 14));

        Tanggal.setDisplayFormat("yyyy-MM-dd");

        NoReg.setPreferredSize(new java.awt.Dimension(320, 30));
        NoReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoRegActionPerformed(evt);
            }
        });
        NoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRegKeyPressed(evt);
            }
        });

        NoRawat.setPreferredSize(new java.awt.Dimension(320, 30));
        NoRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoRawatActionPerformed(evt);
            }
        });
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });

        Biaya.setPreferredSize(new java.awt.Dimension(320, 30));
        Biaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BiayaActionPerformed(evt);
            }
        });
        Biaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BiayaKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 215, 255)), "::[ Cek Kembali & Simpan Registrasi Anda !!! ]::", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24), new java.awt.Color(160, 130, 160))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 70));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 1));

        jPanel2.setPreferredSize(new java.awt.Dimension(390, 120));
        jPanel2.setLayout(null);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Nama Pasien");
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 195, 160, 23);

        jLabel6.setForeground(new java.awt.Color(160, 110, 160));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText(":");
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel6);
        jLabel6.setBounds(180, 195, 20, 23);

        LblNama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblNama.setText("Nama");
        LblNama.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblNama.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblNama);
        LblNama.setBounds(195, 195, 440, 23);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("No. RM");
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel8);
        jLabel8.setBounds(20, 165, 160, 23);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText(":");
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel9);
        jLabel9.setBounds(180, 165, 20, 23);

        LblNoRm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblNoRm.setText("Norm");
        LblNoRm.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblNoRm.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblNoRm);
        LblNoRm.setBounds(195, 165, 440, 23);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Unit/Poliklinik");
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jLabel5);
        jLabel5.setBounds(20, 135, 160, 23);

        LblNamaPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblNamaPoli.setText("Poliklinik");
        LblNamaPoli.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblNamaPoli.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblNamaPoli);
        LblNamaPoli.setBounds(195, 135, 440, 23);

        jLabel7.setForeground(new java.awt.Color(160, 110, 160));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText(":");
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel7);
        jLabel7.setBounds(180, 135, 20, 23);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("No. Registrasi");
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel10);
        jLabel10.setBounds(20, 15, 160, 23);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText(":");
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel11);
        jLabel11.setBounds(180, 15, 20, 23);

        LblNoReg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblNoReg.setText("Noreg");
        LblNoReg.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblNoReg.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblNoReg);
        LblNoReg.setBounds(195, 15, 440, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("No. Rawat");
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel12);
        jLabel12.setBounds(20, 45, 160, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText(":");
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel13);
        jLabel13.setBounds(180, 45, 20, 23);

        LblNoRawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblNoRawat.setText("Norawat");
        LblNoRawat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblNoRawat.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblNoRawat);
        LblNoRawat.setBounds(195, 45, 440, 23);

        LblTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblTanggal.setText("Tanggal");
        LblTanggal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblTanggal.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblTanggal);
        LblTanggal.setBounds(195, 75, 160, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText(":");
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel14);
        jLabel14.setBounds(180, 75, 20, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Tgl. Registrasi");
        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel15);
        jLabel15.setBounds(20, 75, 160, 23);

        LblJam.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblJam.setText("Jam");
        LblJam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblJam.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblJam);
        LblJam.setBounds(320, 75, 100, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Dr Dituju");
        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel16);
        jLabel16.setBounds(20, 105, 160, 23);

        LblDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblDokter.setText("Dokter");
        LblDokter.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblDokter.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblDokter);
        LblDokter.setBounds(195, 105, 440, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText(":");
        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel17);
        jLabel17.setBounds(180, 105, 20, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Penanggung Jawab");
        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jLabel18);
        jLabel18.setBounds(20, 224, 160, 25);

        jLabel19.setForeground(new java.awt.Color(160, 110, 160));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText(":");
        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel19);
        jLabel19.setBounds(180, 224, 20, 25);

        PngJawab.setPreferredSize(new java.awt.Dimension(320, 30));
        PngJawab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PngJawabActionPerformed(evt);
            }
        });
        PngJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PngJawabKeyPressed(evt);
            }
        });
        jPanel2.add(PngJawab);
        PngJawab.setBounds(195, 224, 350, 26);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Hubungan P. J.");
        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jLabel20);
        jLabel20.setBounds(20, 254, 160, 25);

        jLabel21.setForeground(new java.awt.Color(160, 110, 160));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText(":");
        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel21);
        jLabel21.setBounds(180, 254, 20, 25);

        HubunganPngJawab.setPreferredSize(new java.awt.Dimension(320, 30));
        HubunganPngJawab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HubunganPngJawabActionPerformed(evt);
            }
        });
        HubunganPngJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganPngJawabKeyPressed(evt);
            }
        });
        jPanel2.add(HubunganPngJawab);
        HubunganPngJawab.setBounds(195, 254, 350, 26);

        jLabel22.setForeground(new java.awt.Color(160, 110, 160));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText(":");
        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel22);
        jLabel22.setBounds(180, 284, 20, 25);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Alamat P. J.");
        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jLabel23);
        jLabel23.setBounds(20, 284, 160, 25);

        AlamatPngJawab.setPreferredSize(new java.awt.Dimension(320, 30));
        AlamatPngJawab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlamatPngJawabActionPerformed(evt);
            }
        });
        AlamatPngJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPngJawabKeyPressed(evt);
            }
        });
        jPanel2.add(AlamatPngJawab);
        AlamatPngJawab.setBounds(195, 284, 350, 26);

        Status.setPreferredSize(new java.awt.Dimension(320, 30));
        Status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusActionPerformed(evt);
            }
        });
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        jPanel2.add(Status);
        Status.setBounds(195, 314, 350, 26);

        jLabel24.setForeground(new java.awt.Color(160, 110, 160));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(":");
        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel24);
        jLabel24.setBounds(180, 314, 20, 25);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Status Pasien");
        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jLabel25);
        jLabel25.setBounds(20, 314, 160, 25);

        jLabel26.setForeground(new java.awt.Color(160, 110, 160));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText(":");
        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel26);
        jLabel26.setBounds(180, 344, 20, 25);

        KdBayar.setEditable(false);
        KdBayar.setPreferredSize(new java.awt.Dimension(320, 30));
        KdBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KdBayarActionPerformed(evt);
            }
        });
        KdBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdBayarKeyPressed(evt);
            }
        });
        jPanel2.add(KdBayar);
        KdBayar.setBounds(195, 344, 68, 26);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Jenis Bayar");
        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jLabel27);
        jLabel27.setBounds(20, 344, 160, 25);

        NmBayar.setEditable(false);
        NmBayar.setPreferredSize(new java.awt.Dimension(320, 30));
        NmBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmBayarActionPerformed(evt);
            }
        });
        NmBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmBayarKeyPressed(evt);
            }
        });
        jPanel2.add(NmBayar);
        NmBayar.setBounds(265, 344, 248, 26);

        btnSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSemua.setMnemonic('M');
        btnSemua.setToolTipText("Alt+M");
        btnSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSemua.setMargin(new java.awt.Insets(0, 3, 0, 0));
        btnSemua.setPreferredSize(new java.awt.Dimension(30, 30));
        btnSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSemuaActionPerformed(evt);
            }
        });
        btnSemua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSemuaKeyPressed(evt);
            }
        });
        jPanel2.add(btnSemua);
        btnSemua.setBounds(515, 344, 30, 26);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(390, 56));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        btnSimpan.setMnemonic('S');
        btnSimpan.setText("Cetak");
        btnSimpan.setToolTipText("Alt+S");
        btnSimpan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSimpan.setPreferredSize(new java.awt.Dimension(110, 35));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        btnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSimpanKeyPressed(evt);
            }
        });
        jPanel4.add(btnSimpan);

        btnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        btnKeluar.setMnemonic('K');
        btnKeluar.setText("Batal");
        btnKeluar.setToolTipText("Alt+K");
        btnKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnKeluar.setPreferredSize(new java.awt.Dimension(110, 35));
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        btnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKeluarKeyPressed(evt);
            }
        });
        jPanel4.add(btnKeluar);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        PngJawab.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void PngJawabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PngJawabActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PngJawabActionPerformed

    private void PngJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PngJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PngJawabKeyPressed

    private void HubunganPngJawabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HubunganPngJawabActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HubunganPngJawabActionPerformed

    private void HubunganPngJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganPngJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HubunganPngJawabKeyPressed

    private void AlamatPngJawabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlamatPngJawabActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlamatPngJawabActionPerformed

    private void AlamatPngJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPngJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlamatPngJawabKeyPressed

    private void StatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusActionPerformed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusKeyPressed

    private void KdBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KdBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdBayarActionPerformed

    private void KdBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdBayarKeyPressed

    private void NmBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmBayarActionPerformed

    private void NmBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmBayarKeyPressed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        status="Baru";
        if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and kd_poli=?",LblNoRm.getText(),LblKdPoli.getText())>0){
            status="Lama";
        }
        LblJam.setText(Sequel.cariIsi("select current_time()"));
        isNumber();
        LblNoReg.setText(NoReg.getText());
        LblNoRawat.setText(NoRawat.getText());
        if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
            new String[]{LblNoReg.getText(),LblNoRawat.getText(),LblTanggal.getText(),LblJam.getText(),
            LblKdDokter.getText(),LblNoRm.getText(),LblKdPoli.getText(),PngJawab.getText(),
            AlamatPngJawab.getText(),HubunganPngJawab.getText(),Biaya.getText(),"Belum",
            Status.getText(),"Ralan",KdBayar.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                UpdateUmur();
                DlgCetak cetak=new DlgCetak(null,true);
                cetak.setSize(this.getWidth(),this.getHeight());
                cetak.setLocationRelativeTo(this);
                cetak.setPasien(LblNoRawat.getText(),LblNamaPoli.getText(),LblNoReg.getText(),LblNama.getText(),
                        LblNoRm.getText(),LblDokter.getText(),NmBayar.getText(),PngJawab.getText());
                cetak.setVisible(true);
        }else{
            LblJam.setText(Sequel.cariIsi("select current_time()"));
            isNumber();
            LblNoReg.setText(NoReg.getText());
            LblNoRawat.setText(NoRawat.getText());
            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                new String[]{LblNoReg.getText(),LblNoRawat.getText(),LblTanggal.getText(),LblJam.getText(),
                LblKdDokter.getText(),LblNoRm.getText(),LblKdPoli.getText(),PngJawab.getText(),
                AlamatPngJawab.getText(),HubunganPngJawab.getText(),Biaya.getText(),"Belum",
                Status.getText(),"Ralan",KdBayar.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                    UpdateUmur();
                    DlgCetak cetak=new DlgCetak(null,true);
                    cetak.setSize(this.getWidth(),this.getHeight());
                    cetak.setLocationRelativeTo(this);
                    cetak.setPasien(LblNoRawat.getText(),LblNamaPoli.getText(),LblNoReg.getText(),LblNama.getText(),
                        LblNoRm.getText(),LblDokter.getText(),NmBayar.getText(),PngJawab.getText());
                    cetak.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan hubungi petugas kami, terjadi masalah pada system..!!!");
                PngJawab.requestFocus();
            }
        }            
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnSimpanActionPerformed(null);
        }
    }//GEN-LAST:event_btnSimpanKeyPressed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnKeluarActionPerformed(null);
        }
    }//GEN-LAST:event_btnKeluarKeyPressed

    private void NoRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoRegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoRegActionPerformed

    private void NoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRegKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoRegKeyPressed

    private void NoRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoRawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoRawatActionPerformed

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoRawatKeyPressed

    private void BiayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BiayaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BiayaActionPerformed

    private void BiayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BiayaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BiayaKeyPressed

    private void btnSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSemuaActionPerformed
        pilihbayar.setSize(this.getWidth()-20,this.getHeight()-20);
        pilihbayar.setLocationRelativeTo(this);
        pilihbayar.tampil();
        pilihbayar.setVisible(true);
    }//GEN-LAST:event_btnSemuaActionPerformed

    private void btnSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSemuaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnSemuaActionPerformed(null);
        }
    }//GEN-LAST:event_btnSemuaKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRegistrasi dialog = new DlgRegistrasi(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.TextBox AlamatPngJawab;
    private component.TextBox Biaya;
    private component.TextBox HubunganPngJawab;
    private component.TextBox KdBayar;
    private component.Label LblDokter;
    private component.Label LblJam;
    private component.Label LblKdDokter;
    private component.Label LblKdPoli;
    private component.Label LblNama;
    private component.Label LblNamaPoli;
    private component.Label LblNoRawat;
    private component.Label LblNoReg;
    private component.Label LblNoRm;
    private component.Label LblTanggal;
    private component.TextBox NmBayar;
    private component.TextBox NoRawat;
    private component.TextBox NoReg;
    private component.TextBox PngJawab;
    private component.TextBox Status;
    private component.Tanggal Tanggal;
    private component.Button btnKeluar;
    private component.Button btnSemua;
    private component.Button btnSimpan;
    private component.Label jLabel10;
    private component.Label jLabel11;
    private component.Label jLabel12;
    private component.Label jLabel13;
    private component.Label jLabel14;
    private component.Label jLabel15;
    private component.Label jLabel16;
    private component.Label jLabel17;
    private component.Label jLabel18;
    private component.Label jLabel19;
    private component.Label jLabel2;
    private component.Label jLabel20;
    private component.Label jLabel21;
    private component.Label jLabel22;
    private component.Label jLabel23;
    private component.Label jLabel24;
    private component.Label jLabel25;
    private component.Label jLabel26;
    private component.Label jLabel27;
    private component.Label jLabel5;
    private component.Label jLabel6;
    private component.Label jLabel7;
    private component.Label jLabel8;
    private component.Label jLabel9;
    private component.Panel jPanel1;
    private component.Panel jPanel2;
    private component.Panel jPanel4;
    // End of variables declaration//GEN-END:variables
    
    
    public void setPasien(String norm,String kodepoli,String kddokter){
        LblNoRm.setText(norm);
        try {
            ps.setString(1,Valid.SetTgl(Tanggal.getSelectedItem()+""));
            ps.setString(2,norm);
            rs=ps.executeQuery();
            if(rs.next()){
                LblNama.setText(rs.getString("nm_pasien"));
                PngJawab.setText(rs.getString("namakeluarga"));
                HubunganPngJawab.setText(rs.getString("keluarga"));
                AlamatPngJawab.setText(rs.getString("asal"));
                Status.setText(rs.getString("daftar"));
                umur="0";
                sttsumur="Th";
                if(rs.getInt("tahun")>0){
                    umur=rs.getString("tahun");
                    sttsumur="Th";
                }else if(rs.getInt("tahun")==0){
                    if(rs.getInt("bulan")>0){
                        umur=rs.getString("bulan");
                        sttsumur="Bl";
                    }else if(rs.getInt("bulan")==0){
                        umur=rs.getString("hari");
                        sttsumur="Hr";
                    }
                }
                KdBayar.setText(rs.getString("kd_pj"));
                NmBayar.setText(Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",KdBayar.getText()));
            }
        } catch (Exception e) {
            System.out.println("Notifikasi a : "+e);
        }
        LblKdPoli.setText(kodepoli);
        LblNamaPoli.setText(Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?", kodepoli));
        if(Status.getText().equals("Baru")){
            Biaya.setText(""+Sequel.cariIsiAngka("select registrasi from poliklinik where kd_poli=?",kodepoli));
        }else{
            Biaya.setText(""+Sequel.cariIsiAngka("select poliklinik.registrasilama from poliklinik where poliklinik.kd_poli=?",kodepoli));
        }
        LblKdDokter.setText(kddokter);
        LblDokter.setText(Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",kddokter));
        Tanggal.setDate(new Date());
        LblTanggal.setText(Tanggal.getSelectedItem().toString());
        LblJam.setText(Sequel.cariIsi("select current_time()"));
        isNumber();
        LblNoReg.setText(NoReg.getText());
        LblNoRawat.setText(NoRawat.getText());
    }
    
    private void UpdateUmur(){
        Sequel.mengedit("pasien","no_rkm_medis=?","umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr'))",1,new String[]{LblNoRm.getText()});
    }
    
    private void isNumber(){         
        if(BASENOREG.equals("booking")){
            switch (URUTNOREG) {
                case "poli":
                    if(Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='"+LblKdPoli.getText()+"' and tanggal_periksa='"+LblTanggal.getText()+"'")>=
                            Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='"+LblKdPoli.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'")){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='"+LblKdPoli.getText()+"' and tanggal_periksa='"+LblTanggal.getText()+"'","",3,NoReg);
                    }else{
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='"+LblKdPoli.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'","",3,NoReg);
                    }
                    break;
                case "dokter":
                    if(Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+LblKdDokter.getText()+"' and tanggal_periksa='"+LblTanggal.getText()+"'")>=
                            Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+LblKdDokter.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'")){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+LblKdDokter.getText()+"' and tanggal_periksa='"+LblTanggal.getText()+"'","",3,NoReg);
                    }else{
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+LblKdDokter.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'","",3,NoReg);
                    }
                    break;
                case "dokter + poli":  
                    if(Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+LblKdDokter.getText()+"' and kd_poli='"+LblKdPoli.getText()+"' and tanggal_periksa='"+LblTanggal.getText()+"'")>=
                            Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+LblKdDokter.getText()+"' and kd_poli='"+LblKdPoli.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'")){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+LblKdDokter.getText()+"' and kd_poli='"+LblKdPoli.getText()+"' and tanggal_periksa='"+LblTanggal.getText()+"'","",3,NoReg);
                    }else{
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+LblKdDokter.getText()+"' and kd_poli='"+LblKdPoli.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'","",3,NoReg);
                    }                    
                    break;
                default:
                    if(Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='"+LblKdPoli.getText()+"' and tanggal_periksa='"+LblTanggal.getText()+"'")>=
                            Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='"+LblKdPoli.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'")){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='"+LblKdPoli.getText()+"' and tanggal_periksa='"+LblTanggal.getText()+"'","",3,NoReg);
                    }else{
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='"+LblKdPoli.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'","",3,NoReg);
                    }
                    break;
            }
        }else{
            switch (URUTNOREG) {
                case "poli":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='"+LblKdPoli.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'","",3,NoReg);
                    break;
                case "dokter":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+LblKdDokter.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'","",3,NoReg);
                    break;
                case "dokter + poli":             
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+LblKdDokter.getText()+"' and kd_poli='"+LblKdPoli.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'","",3,NoReg);
                    break;
                default:
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+LblKdDokter.getText()+"' and tgl_registrasi='"+LblTanggal.getText()+"'","",3,NoReg);
                    break;
            }
        }    
        
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0) from reg_periksa where tgl_registrasi='"+LblTanggal.getText()+"' ",LblTanggal.getText().replaceAll("-","/")+"/",6,NoRawat);           
    }
}
