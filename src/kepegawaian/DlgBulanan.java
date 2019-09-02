/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgBangsal.java
 *
 * Created on May 22, 2010, 9:58:42 PM
 */

package kepegawaian;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgBulanan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,psketerlambatan;
    private ResultSet rs,rsketerlambatan;
    private String masuk="",pulang="";
    private DlgBarcode bar=new DlgBarcode(null,false);
    private String[] id;
    private String[] tgl;
    private String pilih="";
    
    int no=0,i=0,toleransi=0,terlambat1=0,terlambat2=0;
    /** Creates new form DlgBangsal
     * @param parent
     * @param modal */
    public DlgBulanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Object[] row={"NIP","Nama","Shift","Jam Datang","Jam Pulang","Status","Keterlambatan","Durasi","Catatan"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbBangsal.setModel(tabMode);
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(300);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(300);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        Nik.setDocument(new batasInput((int)25).getKata(Nik));
        catatan.setDocument(new batasInput((int)100).getKata(catatan));
        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }  
        
        Valid.loadCombo(Departemen,"nama","departemen");
        Departemen.addItem("Semua");
        Departemen.setSelectedItem("Semua");
        
        bar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bar.getTable().getSelectedRow()!= -1){                   
                    Idpresensi.setText(bar.getTable().getValueAt(bar.getTable().getSelectedRow(),0).toString());                        
                    Nik.setText(bar.getTable().getValueAt(bar.getTable().getSelectedRow(),1).toString());                        
                    Nm.setText(bar.getTable().getValueAt(bar.getTable().getSelectedRow(),2).toString());                        
                }  
                catatan.requestFocus();
                
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
        
        bar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    bar.dispose();                    
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        loadTahun();
        try {
           ps=koneksi.prepareStatement(
                "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id where "+
                " pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and pegawai.nik like ? and rekap_presensi.jam_datang like ?  "+
                "or pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and pegawai.nama like ? and  rekap_presensi.jam_datang like ?  "+                   
                "or pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and rekap_presensi.shift like ? and  rekap_presensi.jam_datang like ?  "+
                "or pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and rekap_presensi.status like ? and  rekap_presensi.jam_datang like ?  "+
                "or pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and rekap_presensi.keterlambatan like ? and  rekap_presensi.jam_datang like ?  "+
                "or pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and rekap_presensi.jam_datang like ? and  rekap_presensi.jam_datang like ? "+
                "or pegawai.stts_aktif<>'KELUAR' and departemen.nama like ? and rekap_presensi.jam_pulang like ? and  rekap_presensi.jam_datang like ?  order by pegawai.nama ");
            psketerlambatan=koneksi.prepareStatement("select * from set_keterlambatan");
            rsketerlambatan=psketerlambatan.executeQuery();
            if(rsketerlambatan.next()){
                toleransi=rsketerlambatan.getInt(1);
                terlambat1=rsketerlambatan.getInt(2);
                terlambat2=rsketerlambatan.getInt(3);
            }else{
                toleransi=0;
                terlambat1=0;
                terlambat2=0;
            }
        } catch (Exception e) {
            System.out.println(e);
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

        DlgInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        label2 = new widget.Label();
        BtnClose = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        label23 = new widget.Label();
        Nm = new widget.TextBox();
        Cari = new widget.Button();
        Nik = new widget.TextBox();
        tglMasuk = new widget.Tanggal();
        Jam = new widget.ComboBox();
        label26 = new widget.Label();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        Jam2 = new widget.ComboBox();
        Menit2 = new widget.ComboBox();
        Detik2 = new widget.ComboBox();
        label28 = new widget.Label();
        label24 = new widget.Label();
        catatan = new widget.TextBox();
        jamdatang = new widget.TextBox();
        label25 = new widget.Label();
        Shift = new widget.ComboBox();
        jampulang = new widget.TextBox();
        tglPulang = new widget.Tanggal();
        Idpresensi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelGlass7 = new widget.panelisi();
        label11 = new widget.Label();
        ThnCari = new widget.ComboBox();
        BlnCari = new widget.ComboBox();
        label12 = new widget.Label();
        Departemen = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelGlass5 = new widget.panelisi();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnTambah = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        DlgInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgInput.setName("DlgInput"); // NOI18N
        DlgInput.setUndecorated(true);
        DlgInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Input Presensi Harian ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setWarnaBawah(new java.awt.Color(255, 254, 255));
        internalFrame2.setLayout(null);

        label2.setText("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        label2.setName("label2"); // NOI18N
        internalFrame2.add(label2);
        label2.setBounds(-10, 114, 820, 14);

        BtnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnClose.setMnemonic('U');
        BtnClose.setText("Tutup");
        BtnClose.setToolTipText("Alt+U");
        BtnClose.setName("BtnClose"); // NOI18N
        BtnClose.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseActionPerformed(evt);
            }
        });
        BtnClose.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnClose);
        BtnClose.setBounds(630, 135, 100, 30);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnSimpan);
        BtnSimpan.setBounds(13, 135, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnBatal);
        BtnBatal.setBounds(125, 135, 100, 30);

        label23.setText("NIP :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        internalFrame2.add(label23);
        label23.setBounds(-7, 27, 69, 23);

        Nm.setEditable(false);
        Nm.setName("Nm"); // NOI18N
        Nm.setPreferredSize(new java.awt.Dimension(207, 23));
        Nm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKeyPressed(evt);
            }
        });
        internalFrame2.add(Nm);
        Nm.setBounds(178, 27, 348, 23);

        Cari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        Cari.setMnemonic('C');
        Cari.setToolTipText("Alt+C");
        Cari.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Cari.setName("Cari"); // NOI18N
        Cari.setPreferredSize(new java.awt.Dimension(100, 30));
        Cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CariActionPerformed(evt);
            }
        });
        internalFrame2.add(Cari);
        Cari.setBounds(530, 27, 25, 23);

        Nik.setEditable(false);
        Nik.setName("Nik"); // NOI18N
        Nik.setPreferredSize(new java.awt.Dimension(207, 23));
        Nik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NikKeyPressed(evt);
            }
        });
        internalFrame2.add(Nik);
        Nik.setBounds(65, 27, 110, 23);

        tglMasuk.setDisplayFormat("dd-MM-yyyy");
        tglMasuk.setName("tglMasuk"); // NOI18N
        tglMasuk.setPreferredSize(new java.awt.Dimension(105, 23));
        internalFrame2.add(tglMasuk);
        tglMasuk.setBounds(65, 57, 90, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        internalFrame2.add(Jam);
        Jam.setBounds(159, 57, 62, 23);

        label26.setText("Masuk :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(65, 23));
        internalFrame2.add(label26);
        label26.setBounds(-7, 57, 69, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        Menit.setName("Menit"); // NOI18N
        internalFrame2.add(Menit);
        Menit.setBounds(224, 57, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        Detik.setName("Detik"); // NOI18N
        internalFrame2.add(Detik);
        Detik.setBounds(289, 57, 62, 23);

        Jam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam2.setName("Jam2"); // NOI18N
        internalFrame2.add(Jam2);
        Jam2.setBounds(536, 57, 62, 23);

        Menit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        Menit2.setName("Menit2"); // NOI18N
        internalFrame2.add(Menit2);
        Menit2.setBounds(601, 57, 62, 23);

        Detik2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60" }));
        Detik2.setName("Detik2"); // NOI18N
        internalFrame2.add(Detik2);
        Detik2.setBounds(666, 57, 62, 23);

        label28.setText("Pulang :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(65, 23));
        internalFrame2.add(label28);
        label28.setBounds(379, 57, 60, 23);

        label24.setText("Catatan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(65, 23));
        internalFrame2.add(label24);
        label24.setBounds(-7, 87, 69, 23);

        catatan.setFont(new java.awt.Font("Comic Sans MS", 0, 11)); // NOI18N
        catatan.setName("catatan"); // NOI18N
        internalFrame2.add(catatan);
        catatan.setBounds(65, 87, 663, 23);

        jamdatang.setFont(new java.awt.Font("Comic Sans MS", 0, 11)); // NOI18N
        jamdatang.setName("jamdatang"); // NOI18N
        jamdatang.setPreferredSize(new java.awt.Dimension(207, 23));
        jamdatang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jamdatangKeyPressed(evt);
            }
        });
        internalFrame2.add(jamdatang);
        jamdatang.setBounds(390, 340, 207, 23);

        label25.setText("Shift :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(65, 23));
        internalFrame2.add(label25);
        label25.setBounds(563, 27, 40, 23);

        Shift.setBackground(new java.awt.Color(245, 255, 235));
        Shift.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pagi", "Pagi2", "Pagi3", "Pagi4", "Pagi5", "Pagi6", "Pagi7", "Pagi8", "Pagi9", "Pagi10", "Siang", "Siang2", "Siang3", "Siang4", "Siang5", "Siang6", "Siang7", "Siang8", "Siang9", "Siang10", "Malam", "Malam2", "Malam3", "Malam4", "Malam5", "Malam6", "Malam7", "Malam8", "Malam9", "Malam10", "Midle Pagi1", "Midle Pagi2", "Midle Pagi3", "Midle Pagi4", "Midle Pagi5", "Midle Pagi6", "Midle Pagi7", "Midle Pagi8", "Midle Pagi9", "Midle Pagi10", "Midle Siang1", "Midle Siang2", "Midle Siang3", "Midle Siang4", "Midle Siang5", "Midle Siang6", "Midle Siang7", "Midle Siang8", "Midle Siang9", "Midle Siang10", "Midle Malam1", "Midle Malam2", "Midle Malam3", "Midle Malam4", "Midle Malam5", "Midle Malam6", "Midle Malam7", "Midle Malam8", "Midle Malam9", "Midle Malam10" }));
        Shift.setName("Shift"); // NOI18N
        Shift.setPreferredSize(new java.awt.Dimension(80, 23));
        internalFrame2.add(Shift);
        Shift.setBounds(606, 27, 122, 23);

        jampulang.setFont(new java.awt.Font("Comic Sans MS", 0, 11)); // NOI18N
        jampulang.setName("jampulang"); // NOI18N
        jampulang.setPreferredSize(new java.awt.Dimension(207, 23));
        internalFrame2.add(jampulang);
        jampulang.setBounds(220, 340, 207, 23);

        tglPulang.setDisplayFormat("dd-MM-yyyy");
        tglPulang.setName("tglPulang"); // NOI18N
        tglPulang.setPreferredSize(new java.awt.Dimension(105, 23));
        internalFrame2.add(tglPulang);
        tglPulang.setBounds(442, 57, 90, 23);

        DlgInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        Idpresensi.setFont(new java.awt.Font("Comic Sans MS", 0, 11)); // NOI18N
        Idpresensi.setName("Idpresensi"); // NOI18N
        Idpresensi.setPreferredSize(new java.awt.Dimension(207, 23));
        Idpresensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IdpresensiKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Presensi Bulanan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setAutoCreateRowSorter(true);
        tbBangsal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBangsalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tahun & Bulan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(label11);

        ThnCari.setName("ThnCari"); // NOI18N
        ThnCari.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass7.add(ThnCari);

        BlnCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        BlnCari.setName("BlnCari"); // NOI18N
        BlnCari.setPreferredSize(new java.awt.Dimension(62, 23));
        panelGlass7.add(BlnCari);

        label12.setText("Departemen :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(77, 23));
        panelGlass7.add(label12);

        Departemen.setName("Departemen"); // NOI18N
        Departemen.setPreferredSize(new java.awt.Dimension(130, 23));
        panelGlass7.add(Departemen);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(66, 23));
        jLabel6.setRequestFocusEnabled(false);
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass7.add(BtnCari);

        jPanel1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(57, 23));
        panelGlass5.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass5.add(LCount);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambah.setMnemonic('A');
        BtnTambah.setText("Tambah");
        BtnTambah.setToolTipText("Alt+A");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        BtnTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTambahKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnTambah);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('m');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnKeluar);

        jPanel1.add(panelGlass5, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseActionPerformed
        emptTeks();
        DlgInput.dispose();
    }//GEN-LAST:event_BtnCloseActionPerformed

    private void BtnCloseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            DlgInput.dispose();
        }else{Valid.pindah(evt,BtnBatal,BtnClose);}
    }//GEN-LAST:event_BtnCloseKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Nik.getText().trim().equals("")){
            Valid.textKosong(Nik,"ID");
        }else{
            Sequel.cariIsi("select jam_jaga.jam_masuk from jam_jaga inner join pegawai on pegawai.departemen=jam_jaga.dep_id "+
                " where jam_jaga.shift='"+Shift.getSelectedItem().toString()+"' and pegawai.id='"+Idpresensi.getText()+"'", jamdatang);
            Sequel.cariIsi("select jam_jaga.jam_pulang from jam_jaga inner join pegawai on pegawai.departemen=jam_jaga.dep_id "+
                " where jam_jaga.shift='"+Shift.getSelectedItem().toString()+"' and pegawai.id='"+Idpresensi.getText()+"'", jampulang);
            String jam="now()";
            if(!jamdatang.getText().equals("")){
                jam="'"+Valid.SetTgl(tglMasuk.getSelectedItem()+"")+" "+jamdatang.getText()+"'";
            }

            String jam2="now()";
            if(!jampulang.getText().equals("")){
                jam2="'"+Valid.SetTgl(tglPulang.getSelectedItem()+"")+" "+jampulang.getText()+"'";
            }

            masuk=Valid.SetTgl(tglMasuk.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem();
            pulang=Valid.SetTgl(tglPulang.getSelectedItem()+"")+" "+Jam2.getSelectedItem()+":"+Menit2.getSelectedItem()+":"+Detik2.getSelectedItem();

            if (tglMasuk.isEnabled()==true) {
                //----------------simpan-------------------------------------------
                Sequel.menyimpan("rekap_presensi", "'" + Idpresensi.getText()+"',"+
                    "'"+Shift.getSelectedItem()+"',"+
                    "'"+masuk+"',"+
                    "'"+pulang+"',"+
                    "if(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")>"+(toleransi*60)+",if(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")>"+(terlambat1*60)+",if(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")>"+(terlambat2*60)+",concat('Terlambat II',if(TIME_TO_SEC('"+pulang+"')-TIME_TO_SEC("+jam2+")<0,' & PSW',' ')),concat('Terlambat I',if(TIME_TO_SEC('"+pulang+"')-TIME_TO_SEC("+jam2+")<0,' & PSW',' '))),concat('Terlambat Toleransi',if(TIME_TO_SEC('"+pulang+"')-TIME_TO_SEC("+jam2+")<0,' & PSW',' '))),concat('Tepat Waktu',if(TIME_TO_SEC('"+pulang+"')-TIME_TO_SEC("+jam2+")<0,' & PSW',' '))),"+
                    "if(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")>"+(toleransi*60)+",SEC_TO_TIME(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")),''),"+
                    "(SEC_TO_TIME(unix_timestamp('"+pulang+"') - unix_timestamp('"+masuk+"'))),"+
                    "'"+catatan.getText()+"',''", "ID/Presensi");
            } else if (tglMasuk.isEnabled()==false) {
                Valid.editTable(tabMode, "rekap_presensi", "jam_datang='"+masuk+"' and id", Idpresensi,"jam_pulang='" +pulang
                    + "',durasi=(SEC_TO_TIME(unix_timestamp('"+pulang+ "') - unix_timestamp('"+masuk+"'))),"
                    +"keterangan='"+catatan.getText()+"',"
                    +"shift='"+Shift.getSelectedItem()+"',"
                    +"status=if(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")>"+(toleransi*60)+",if(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")>"+(terlambat1*60)+",if(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")>"+(terlambat2*60)+",concat('Terlambat II',if(TIME_TO_SEC('"+pulang+"')-TIME_TO_SEC("+jam2+")<0,' & PSW',' ')),concat('Terlambat I',if(TIME_TO_SEC('"+pulang+"')-TIME_TO_SEC("+jam2+")<0,' & PSW',' '))),concat('Terlambat Toleransi',if(TIME_TO_SEC('"+pulang+"')-TIME_TO_SEC("+jam2+")<0,' & PSW',' '))),concat('Tepat Waktu',if(TIME_TO_SEC('"+pulang+"')-TIME_TO_SEC("+jam2+")<0,' & PSW',' '))),"
                    +"keterlambatan=if(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")>"+(toleransi*60)+",SEC_TO_TIME(TIME_TO_SEC('"+masuk+"')-TIME_TO_SEC("+jam+")),'')");
                DlgInput.dispose();
            }
            tampil();
            emptTeks();
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnSimpanActionPerformed(null);
        }else{
            //  Valid.pindah(evt,Pas,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(tglMasuk.isEditable()==true){
            emptTeks();
        }else if(tglMasuk.isEditable()==false){
            emptTeks();
            DlgInput.dispose();
        }
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnClose);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void NmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmKeyPressed

    private void CariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CariActionPerformed
        bar.emptTeks();
        bar.tampil();
        bar.isCek();
        bar.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
        bar.setLocationRelativeTo(internalFrame1);
        bar.setVisible(true);
    }//GEN-LAST:event_CariActionPerformed

    private void NikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NikKeyPressed

    private void jamdatangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jamdatangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jamdatangKeyPressed

    private void IdpresensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IdpresensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdpresensiKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        Jam.requestFocus();
        Cari.setEnabled(true);
        tglMasuk.setEnabled(true);
        Jam.setEnabled(true);
        Menit.setEnabled(true);
        Detik.setEnabled(true);
        Idpresensi.setText("");
        Nik.setText("");
        Nm.setText("");
        DlgInput.setSize(744,185);
        DlgInput.setLocationRelativeTo(internalFrame1);
        DlgInput.setVisible(true);

    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnTambahActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnEdit);
        }
    }//GEN-LAST:event_BtnTambahKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(Nm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbBangsal.requestFocus();
        }else{
            Valid.hapusTable(tabMode,Idpresensi,"rekap_presensi","jam_datang='"+tgl[no]+"' and  id");
            tampil();
            emptTeks();
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnTambah, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(tbBangsal.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Tabel kosong. Tidak ada data yang bisa Anda edit..!!!!");
            BtnTambah.requestFocus();
        }else if(Nm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda edit dengan menklik data pada tabel...!!!");
            tbBangsal.requestFocus();
        }else if(! Nm.getText().trim().equals("")){
            Cari.setEnabled(false);
            tglMasuk.setEnabled(false);
            Jam.setEnabled(false);
            Menit.setEnabled(false);
            Detik.setEnabled(false);
            Nm.requestFocus();
            DlgInput.setSize(744,185);
            DlgInput.setLocationRelativeTo(null);
            DlgInput.setVisible(true);
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnTambah, BtnAll);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tbBangsal.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tbBangsal.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>();   
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                String say=" rekap_presensi.jam_datang like '%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%' ";
                try{
                      pilih = (String)JOptionPane.showInputDialog(null,"Urutkan berdasakan","Laporan",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"NIP","Nama","Shift","Jam Datang","Jam Pulang","Status","Keterlambatan","Durasi","Catatan"},"NIP");
                      switch (pilih) {
                            case "NIP":
                                  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                  Valid.MyReportqry("rptHarian.jasper","report","::[ Rekap Harian ]::",
                                        "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                                        "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                                        "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                                        "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id  where "+
                                        " departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nik like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nama like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.shift like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.status like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.keterlambatan like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_datang like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_pulang like '%"+TCari.getText().trim()+"%' and "+say+" order by pegawai.nik  ",param);            
                                 this.setCursor(Cursor.getDefaultCursor()); break;
                            case "Nama":
                                  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                  Valid.MyReportqry("rptHarian.jasper","report","::[ Rekap Harian ]::",
                                        "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                                        "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                                        "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                                        "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id  where "+
                                        " departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nik like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nama like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.shift like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.status like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.keterlambatan like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_datang like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_pulang like '%"+TCari.getText().trim()+"%' and "+say+" order by pegawai.nama  ",param);            
                                  this.setCursor(Cursor.getDefaultCursor());
                                  break;
                            case "Shift":
                                  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                  Valid.MyReportqry("rptHarian.jasper","report","::[ Rekap Harian ]::",
                                        "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                                        "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                                        "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                                        "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id  where "+
                                        " departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nik like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nama like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.shift like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.status like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.keterlambatan like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_datang like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_pulang like '%"+TCari.getText().trim()+"%' and "+say+" order by rekap_presensi.shift  ",param);            
                                  this.setCursor(Cursor.getDefaultCursor());
                                  break;
                            case "Jam Datang":
                                  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                  Valid.MyReportqry("rptHarian.jasper","report","::[ Rekap Harian ]::",
                                        "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                                        "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                                        "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                                        "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id  where "+
                                        " departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nik like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nama like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.shift like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.status like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.keterlambatan like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_datang like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_pulang like '%"+TCari.getText().trim()+"%' and "+say+" order by rekap_presensi.jam_datang  ",param); 
                                  this.setCursor(Cursor.getDefaultCursor());
                                  break;
                            case "Jam Pulang":
                                  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                  Valid.MyReportqry("rptHarian.jasper","report","::[ Rekap Harian ]::",
                                        "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                                        "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                                        "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                                        "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id  where "+
                                        " departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nik like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nama like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.shift like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.status like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.keterlambatan like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_datang like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_pulang like '%"+TCari.getText().trim()+"%' and "+say+" order by rekap_presensi.jam_pulang  ",param); 
                                  this.setCursor(Cursor.getDefaultCursor());
                                  break;
                            case "Status":
                                  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                  Valid.MyReportqry("rptHarian.jasper","report","::[ Rekap Harian ]::",
                                        "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                                        "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                                        "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                                        "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id  where "+
                                        " departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nik like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nama like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.shift like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.status like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.keterlambatan like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_datang like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_pulang like '%"+TCari.getText().trim()+"%' and "+say+" order by rekap_presensi.status  ",param); 
                                  this.setCursor(Cursor.getDefaultCursor());
                                  break;
                            case "Keterlambatan":
                                  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                  Valid.MyReportqry("rptHarian.jasper","report","::[ Rekap Harian ]::",
                                        "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                                        "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                                        "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                                        "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id  where "+
                                        " departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nik like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nama like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.shift like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.status like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.keterlambatan like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_datang like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_pulang like '%"+TCari.getText().trim()+"%' and "+say+" order by rekap_presensi.keterlambatan ",param); 
                                  this.setCursor(Cursor.getDefaultCursor());
                                  break;
                            case "Durasi":
                                  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                  Valid.MyReportqry("rptHarian.jasper","report","::[ Rekap Harian ]::",
                                        "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                                        "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                                        "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                                        "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id  where "+
                                        " departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nik like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nama like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.shift like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.status like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.keterlambatan like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_datang like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_pulang like '%"+TCari.getText().trim()+"%' and "+say+" order by rekap_presensi.durasi ",param); 
                                  this.setCursor(Cursor.getDefaultCursor());
                                  break;
                            case "Catatan":
                                  this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                  Valid.MyReportqry("rptHarian.jasper","report","::[ Rekap Harian ]::",
                                        "select  pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift, rekap_presensi.jam_datang, "+
                                        "rekap_presensi.jam_pulang, rekap_presensi.status, rekap_presensi.keterlambatan, rekap_presensi.durasi, "+
                                        "rekap_presensi.keterangan from pegawai inner join rekap_presensi inner join departemen "+
                                        "on pegawai.departemen=departemen.dep_id and pegawai.id=rekap_presensi.id  where "+
                                        " departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nik like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and pegawai.nama like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.shift like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.status like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.keterlambatan like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_datang like '%"+TCari.getText().trim()+"%' and "+say+
                                        "or departemen.nama like '%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%' and rekap_presensi.jam_pulang like '%"+TCari.getText().trim()+"%' and "+say+" order by rekap_presensi.keterangan ",param); 
                                  this.setCursor(Cursor.getDefaultCursor());
                                  break;
                      }
                }catch(Exception e){
                      System.out.println(e);
                }                   
        }        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnEdit,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbBangsalKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBulanan dialog = new DlgBulanan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox BlnCari;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnClose;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button Cari;
    private widget.ComboBox Departemen;
    private widget.ComboBox Detik;
    private widget.ComboBox Detik2;
    private javax.swing.JDialog DlgInput;
    private widget.TextBox Idpresensi;
    private widget.ComboBox Jam;
    private widget.ComboBox Jam2;
    private widget.Label LCount;
    private widget.ComboBox Menit;
    private widget.ComboBox Menit2;
    private widget.TextBox Nik;
    private widget.TextBox Nm;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Shift;
    private widget.TextBox TCari;
    private widget.ComboBox ThnCari;
    private widget.TextBox catatan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox jamdatang;
    private widget.TextBox jampulang;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label2;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label28;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass7;
    private widget.Table tbBangsal;
    private widget.Tanggal tglMasuk;
    private widget.Tanggal tglPulang;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{  
            ps.setString(1,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
            ps.setString(4,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(5,"%"+TCari.getText().trim()+"%");
            ps.setString(6,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
            ps.setString(7,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(8,"%"+TCari.getText().trim()+"%");
            ps.setString(9,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
            ps.setString(10,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(11,"%"+TCari.getText().trim()+"%");
            ps.setString(12,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
            ps.setString(13,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(14,"%"+TCari.getText().trim()+"%");
            ps.setString(15,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
            ps.setString(16,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(17,"%"+TCari.getText().trim()+"%");
            ps.setString(18,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
            ps.setString(19,"%"+Departemen.getSelectedItem().toString().replaceAll("Semua","")+"%");
            ps.setString(20,"%"+TCari.getText().trim()+"%");
            ps.setString(21,"%"+ThnCari.getSelectedItem()+"-"+BlnCari.getSelectedItem()+"%");
            rs=ps.executeQuery();            
            rs.last();            
            id=new String[rs.getRow()];
            tgl=new String[rs.getRow()];            
            rs.beforeFirst();            
            i=0;            
            while(rs.next()){
                tabMode.addRow(new String[]{rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               rs.getString(7),
                               rs.getString(8),
                               rs.getString(9),
                               rs.getString(10)});
                id[i]=rs.getString(1);
                tgl[i]=rs.getString(5);
                i++;
             }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());

    }

    public void emptTeks() {       
        Jam.setSelectedItem("00");
        Jam2.setSelectedItem("00");
        Menit.setSelectedItem("00");
        Menit2.setSelectedItem("00");
        Detik.setSelectedItem("00");
        Detik2.setSelectedItem("00");
        catatan.setText("");
    }
   
    private void getData() {
        int row=tbBangsal.getSelectedRow();
        if(row!= -1){
            no=row;
            Idpresensi.setText(id[no]);
            Nik.setText(tbBangsal.getValueAt(row,0).toString());
            Nm.setText(tbBangsal.getValueAt(row,1).toString());
            Shift.setSelectedItem(tbBangsal.getValueAt(row,2).toString());
            if(!tbBangsal.getValueAt(row,3).toString().equals("")){
                Jam.setSelectedItem(tbBangsal.getValueAt(row,3).toString().substring(11,13));
                Menit.setSelectedItem(tbBangsal.getValueAt(row,3).toString().substring(14,16));
                Detik.setSelectedItem(tbBangsal.getValueAt(row,3).toString().substring(17,19));
            }else if(tbBangsal.getValueAt(row,3).toString().equals("")){
                Jam.setSelectedItem("00");
                Menit.setSelectedItem("00");
                Detik.setSelectedItem("00");
            }            
                        
            if(!tbBangsal.getValueAt(row,4).toString().equals("")){
                Jam2.setSelectedItem(tbBangsal.getValueAt(row,4).toString().substring(11,13));
                Menit2.setSelectedItem(tbBangsal.getValueAt(row,4).toString().substring(14,16));
                Detik2.setSelectedItem(tbBangsal.getValueAt(row,4).toString().substring(17,19));
            }else if(tbBangsal.getValueAt(row,4).toString().equals("")){
                Jam2.setSelectedItem("00");
                Menit2.setSelectedItem("00");
                Detik2.setSelectedItem("00");
            }
            catatan.setText(tbBangsal.getValueAt(row,8).toString());
            
            try {
                tglMasuk.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(
                                tbBangsal.getValueAt(row,3).toString().substring(0,4)+"-"+
                                tbBangsal.getValueAt(row,3).toString().substring(5,7)+"-"+
                                tbBangsal.getValueAt(row,3).toString().substring(8,10)));
            } catch (ParseException ex) {
                Logger.getLogger(DlgHarian.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                tglPulang.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(
                                tbBangsal.getValueAt(row,4).toString().substring(0,4)+"-"+
                                tbBangsal.getValueAt(row,4).toString().substring(5,7)+"-"+
                                tbBangsal.getValueAt(row,4).toString().substring(8,10)));
            } catch (ParseException ex) {
                Logger.getLogger(DlgHarian.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
    }
    
    private  void loadTahun(){
        Valid.LoadTahun(ThnCari);
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpresensi_bulanan());
        BtnHapus.setEnabled(akses.getpresensi_bulanan());
        BtnEdit.setEnabled(akses.getpresensi_bulanan());
        BtnPrint.setEnabled(akses.getpresensi_bulanan());
     }

}
