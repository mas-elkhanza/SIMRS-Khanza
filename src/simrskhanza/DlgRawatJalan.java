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

package simrskhanza;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgRawatJalan extends javax.swing.JDialog {
    private final DefaultTableModel tabModeDr,tabModePr,tabModeDrPr,tabModePemeriksaan;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgPasien pasien=new DlgPasien(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgCariPerawatanRalan perawatan=new DlgCariPerawatanRalan(null,false);    
    private PreparedStatement ps,ps2,ps3,ps4;
    private ResultSet rs;
    private int i=0;

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public DlgRawatJalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);
        tabModeDr=new DefaultTableModel(null,new Object[]{
                      "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","Kode Dokter","Dokter Yg Menangani","Biaya","Tgl.Rawat","Kode"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDr.setModel(tabModeDr);
        //tampilDr();

        tbRawatDr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbRawatDr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatDr.setDefaultRenderer(Object.class, new WarnaTable());
        tabModePr=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","NIP","Perawat Yg Menangani","Biaya","Tgl.Rawat","Kode"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,  java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatPr.setModel(tabModePr);
        //tampilPr();

        tbRawatPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbRawatPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatPr.setDefaultRenderer(Object.class, new WarnaTable());
        tabModeDrPr=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Perawatan/Tindakan","Kode Dokter","Dokter Yg Menangani",
                       "NIP","Petugas Yg Menangani","Biaya","Tgl.Rawat","Kode"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,  java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDrPr.setModel(tabModeDrPr);
        //tampilPr();

        tbRawatDrPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDrPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbRawatDrPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRawatDrPr.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePemeriksaan=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Tgl.Rawat","Suhu(C)","Tensi","Nadi(/menit)",
            "Respirasi(/menit)","Tinggi(Cm)","Berat(Kg)","GCS(E,V,M)","Keluhan","Pemeriksaan","Alergi"
        }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPemeriksaan.setModel(tabModePemeriksaan);
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(75);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(180);
            }else if(i==13){
                column.setPreferredWidth(180);
            }else if(i==14){
                column.setPreferredWidth(130);
            }
        }
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TKdPrw.setDocument(new batasInput((byte)15).getKata(TKdPrw));
        kdptg.setDocument(new batasInput((byte)20).getKata(kdptg));
        kdptg2.setDocument(new batasInput((byte)20).getKata(kdptg2));
        KdDok.setDocument(new batasInput((byte)20).getKata(KdDok));
        KdDok2.setDocument(new batasInput((byte)20).getKata(KdDok2));
        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
        TTensi.setDocument(new batasInput((byte)7).getKata(TTensi));
        TKeluhan.setDocument(new batasInput((int)400).getKata(TKeluhan));
        TPemeriksaan.setDocument(new batasInput((int)400).getKata(TPemeriksaan));   
        TAlergi.setDocument(new batasInput((int)50).getKata(TAlergi));        
        TCari.setDocument(new batasInput((int)100).getKata(TCari));       
        TGCS.setDocument(new batasInput((byte)10).getKata(TGCS));
        TTinggi.setDocument(new batasInput((byte)3).getOnlyAngka(TTinggi));
        TBerat.setDocument(new batasInput((byte)3).getOnlyAngka(TBerat));
        TNadi.setDocument(new batasInput((byte)3).getOnlyAngka(TNadi));
        TRespirasi.setDocument(new batasInput((byte)3).getOnlyAngka(TRespirasi));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TabRawat.getSelectedIndex()==0){
                        tampilDr();
                    }else if(TabRawat.getSelectedIndex()==1){
                        tampilPr();
                    }else if(TabRawat.getSelectedIndex()==2){
                        tampilDrPr();
                    }else if(TabRawat.getSelectedIndex()==3){
                        tampilPemeriksaan();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TabRawat.getSelectedIndex()==0){
                        tampilDr();
                    }else if(TabRawat.getSelectedIndex()==1){
                        tampilPr();
                    }else if(TabRawat.getSelectedIndex()==2){
                        tampilDrPr();
                    }else if(TabRawat.getSelectedIndex()==3){
                        tampilPemeriksaan();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TabRawat.getSelectedIndex()==0){
                        tampilDr();
                    }else if(TabRawat.getSelectedIndex()==1){
                        tampilPr();
                    }else if(TabRawat.getSelectedIndex()==2){
                        tampilDrPr();
                    }else if(TabRawat.getSelectedIndex()==3){
                        tampilPemeriksaan();
                    }
                }
            });
        }  
        
        perawatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgRawatJalan")){
                    if(perawatan.getTable().getSelectedRow()!= -1){                   
                        TKdPrw.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),1).toString());   
                        TNmPrw.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),2).toString());
                        BagianRS.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),5).toString());
                        Bhp.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),6).toString());
                        JmDokter.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),7).toString());
                        JmPerawat.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),8).toString());
                        KSO.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),9).toString());
                        Menejemen.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),10).toString());
                        TTnd.setText(perawatan.getTable().getValueAt(perawatan.getTable().getSelectedRow(),4).toString());
                    }  
                    TKdPrw.requestFocus();  
                }
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgRawatJalan")){
                    if(dokter.getTable().getSelectedRow()!= -1){
                        if(TabRawat.getSelectedIndex()==0){
                            KdDok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KdDok.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==2){
                            KdDok2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            TDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            KdDok2.requestFocus();
                        }                        
                    }                      
                }
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgRawatJalan")){
                    if(petugas.getTable().getSelectedRow()!= -1){   
                        if(TabRawat.getSelectedIndex()==1){
                            kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            TPerawat.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            kdptg.requestFocus();
                        }else if(TabRawat.getSelectedIndex()==2){
                            kdptg2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            TPerawat2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            kdptg2.requestFocus();
                        }                            
                    }            
                    
                }
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
        
        ChkInput.setSelected(false);
        isForm(); 
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BagianRS = new javax.swing.JTextField();
        Bhp = new javax.swing.JTextField();
        JmDokter = new javax.swing.JTextField();
        JmPerawat = new javax.swing.JTextField();
        TTnd = new javax.swing.JTextField();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPemberianObat = new javax.swing.JMenuItem();
        KSO = new javax.swing.JTextField();
        Menejemen = new javax.swing.JTextField();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbRawatDr = new widget.Table();
        panelGlass7 = new widget.panelisi();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        TDokter = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbRawatPr = new widget.Table();
        panelGlass10 = new widget.panelisi();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        TPerawat = new widget.TextBox();
        internalFrame4 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbRawatDrPr = new widget.Table();
        panelGlass11 = new widget.panelisi();
        jLabel14 = new widget.Label();
        kdptg2 = new widget.TextBox();
        BtnSeekPetugas2 = new widget.Button();
        TPerawat2 = new widget.TextBox();
        jLabel12 = new widget.Label();
        KdDok2 = new widget.TextBox();
        TDokter2 = new widget.TextBox();
        BtnSeekDokter2 = new widget.Button();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TSuhu = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        TTensi = new widget.TextBox();
        jLabel11 = new widget.Label();
        btnTindakan = new widget.Button();
        jLabel7 = new widget.Label();
        TKdPrw = new widget.TextBox();
        TNmPrw = new widget.TextBox();
        TKeluhan = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        TPemeriksaan = new widget.TextBox();
        TAlergi = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        TBerat = new widget.TextBox();
        jLabel17 = new widget.Label();
        TTinggi = new widget.TextBox();
        jLabel18 = new widget.Label();
        TNadi = new widget.TextBox();
        jLabel20 = new widget.Label();
        TRespirasi = new widget.TextBox();
        jLabel22 = new widget.Label();
        TGCS = new widget.TextBox();

        BagianRS.setEditable(false);
        BagianRS.setText("0");
        BagianRS.setName("BagianRS"); // NOI18N

        Bhp.setEditable(false);
        Bhp.setText("0");
        Bhp.setName("Bhp"); // NOI18N

        JmDokter.setEditable(false);
        JmDokter.setText("0");
        JmDokter.setName("JmDokter"); // NOI18N

        JmPerawat.setEditable(false);
        JmPerawat.setText("0");
        JmPerawat.setName("JmPerawat"); // NOI18N

        TTnd.setEditable(false);
        TTnd.setText("0");
        TTnd.setName("TTnd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(60, 80, 50));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat/BHP");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setIconTextGap(5);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat);

        KSO.setEditable(false);
        KSO.setText("0");
        KSO.setName("KSO"); // NOI18N

        Menejemen.setEditable(false);
        Menejemen.setText("0");
        Menejemen.setName("Menejemen"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perawatan/Tindakan Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnBatal);

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
        panelGlass8.add(BtnHapus);

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
        panelGlass8.add(BtnEdit);

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
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
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
        panelGlass8.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
        panelGlass8.add(LCount);

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
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-02-2017" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-02-2017" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(380, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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
        panelGlass9.add(BtnCari);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(250, 255, 245));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)));
        TabRawat.setForeground(new java.awt.Color(50, 70, 40));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbRawatDr.setAutoCreateRowSorter(true);
        tbRawatDr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDr.setComponentPopupMenu(jPopupMenu1);
        tbRawatDr.setName("tbRawatDr"); // NOI18N
        tbRawatDr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrMouseClicked(evt);
            }
        });
        tbRawatDr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatDrKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbRawatDr);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(null);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 70, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        panelGlass7.add(KdDok);
        KdDok.setBounds(74, 10, 130, 23);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.setName("BtnSeekDokter"); // NOI18N
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeekDokter);
        BtnSeekDokter.setBounds(728, 10, 28, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        panelGlass7.add(TDokter);
        TDokter.setBounds(206, 10, 520, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab(".: Penanganan Dokter  ", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setComponentPopupMenu(jPopupMenu1);
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbRawatPr.setAutoCreateRowSorter(true);
        tbRawatPr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatPr.setComponentPopupMenu(jPopupMenu1);
        tbRawatPr.setName("tbRawatPr"); // NOI18N
        tbRawatPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatPrMouseClicked(evt);
            }
        });
        tbRawatPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatPrKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbRawatPr);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(null);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass10.add(jLabel13);
        jLabel13.setBounds(0, 10, 70, 23);

        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelGlass10.add(kdptg);
        kdptg.setBounds(74, 10, 130, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); // NOI18N
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(728, 10, 28, 23);

        TPerawat.setEditable(false);
        TPerawat.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat.setHighlighter(null);
        TPerawat.setName("TPerawat"); // NOI18N
        panelGlass10.add(TPerawat);
        TPerawat.setBounds(206, 10, 520, 23);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab(".: Penanganan Petugas  ", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setComponentPopupMenu(jPopupMenu1);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbRawatDrPr.setAutoCreateRowSorter(true);
        tbRawatDrPr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDrPr.setComponentPopupMenu(jPopupMenu1);
        tbRawatDrPr.setName("tbRawatDrPr"); // NOI18N
        tbRawatDrPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrPrMouseClicked(evt);
            }
        });
        tbRawatDrPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatDrPrKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbRawatDrPr);

        internalFrame4.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 74));
        panelGlass11.setLayout(null);

        jLabel14.setText("Petugas :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass11.add(jLabel14);
        jLabel14.setBounds(0, 40, 70, 23);

        kdptg2.setHighlighter(null);
        kdptg2.setName("kdptg2"); // NOI18N
        kdptg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptg2KeyPressed(evt);
            }
        });
        panelGlass11.add(kdptg2);
        kdptg2.setBounds(74, 40, 130, 23);

        BtnSeekPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas2.setMnemonic('5');
        BtnSeekPetugas2.setToolTipText("ALt+5");
        BtnSeekPetugas2.setName("BtnSeekPetugas2"); // NOI18N
        BtnSeekPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekPetugas2);
        BtnSeekPetugas2.setBounds(728, 40, 28, 23);

        TPerawat2.setEditable(false);
        TPerawat2.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat2.setHighlighter(null);
        TPerawat2.setName("TPerawat2"); // NOI18N
        panelGlass11.add(TPerawat2);
        TPerawat2.setBounds(206, 40, 520, 23);

        jLabel12.setText("Dokter :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass11.add(jLabel12);
        jLabel12.setBounds(0, 10, 70, 23);

        KdDok2.setHighlighter(null);
        KdDok2.setName("KdDok2"); // NOI18N
        KdDok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok2KeyPressed(evt);
            }
        });
        panelGlass11.add(KdDok2);
        KdDok2.setBounds(74, 10, 130, 23);

        TDokter2.setEditable(false);
        TDokter2.setHighlighter(null);
        TDokter2.setName("TDokter2"); // NOI18N
        panelGlass11.add(TDokter2);
        TDokter2.setBounds(206, 10, 520, 23);

        BtnSeekDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter2.setMnemonic('4');
        BtnSeekDokter2.setToolTipText("ALt+4");
        BtnSeekDokter2.setName("BtnSeekDokter2"); // NOI18N
        BtnSeekDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekDokter2);
        BtnSeekDokter2.setBounds(728, 10, 28, 23);

        internalFrame4.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab(".: Penanganan Dokter & Petugas  ", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setComponentPopupMenu(jPopupMenu1);
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPemeriksaan.setAutoCreateRowSorter(true);
        tbPemeriksaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaan.setComponentPopupMenu(jPopupMenu1);
        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        tbPemeriksaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanMouseClicked(evt);
            }
        });
        tbPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPemeriksaanKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbPemeriksaan);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab(".: Pemeriksaan  ", internalFrame5);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(260, 167));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 95, 23);

        jLabel4.setText("Tensi :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(699, 42, 100, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(98, 12, 153, 23);

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); // NOI18N
        TSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSuhuKeyPressed(evt);
            }
        });
        FormInput.add(TSuhu);
        TSuhu.setBounds(628, 42, 70, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(253, 12, 140, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(395, 12, 477, 23);

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); // NOI18N
        TTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTensiKeyPressed(evt);
            }
        });
        FormInput.add(TTensi);
        TTensi.setBounds(802, 42, 70, 23);

        jLabel11.setText("Tndkn/Tghan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 42, 95, 23);

        btnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTindakan.setMnemonic('3');
        btnTindakan.setToolTipText("Alt+3");
        btnTindakan.setName("btnTindakan"); // NOI18N
        btnTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakanActionPerformed(evt);
            }
        });
        btnTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTindakanKeyPressed(evt);
            }
        });
        FormInput.add(btnTindakan);
        btnTindakan.setBounds(502, 42, 28, 23);

        jLabel7.setText("Suhu Badan(C) :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(533, 42, 92, 23);

        TKdPrw.setName("TKdPrw"); // NOI18N
        TKdPrw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPrwKeyPressed(evt);
            }
        });
        FormInput.add(TKdPrw);
        TKdPrw.setBounds(98, 42, 100, 23);

        TNmPrw.setEditable(false);
        TNmPrw.setName("TNmPrw"); // NOI18N
        FormInput.add(TNmPrw);
        TNmPrw.setBounds(200, 42, 300, 23);

        TKeluhan.setHighlighter(null);
        TKeluhan.setName("TKeluhan"); // NOI18N
        TKeluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeluhanKeyPressed(evt);
            }
        });
        FormInput.add(TKeluhan);
        TKeluhan.setBounds(98, 72, 402, 23);

        jLabel8.setText("Keluhan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 72, 95, 23);

        jLabel9.setText("Pemeriksaan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 102, 95, 23);

        TPemeriksaan.setHighlighter(null);
        TPemeriksaan.setName("TPemeriksaan"); // NOI18N
        TPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPemeriksaanKeyPressed(evt);
            }
        });
        FormInput.add(TPemeriksaan);
        TPemeriksaan.setBounds(98, 102, 402, 23);

        TAlergi.setHighlighter(null);
        TAlergi.setName("TAlergi"); // NOI18N
        TAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlergiKeyPressed(evt);
            }
        });
        FormInput.add(TAlergi);
        TAlergi.setBounds(98, 132, 402, 23);

        jLabel15.setText("Alergi :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 132, 95, 23);

        jLabel16.setText("Berat Badan(Kg) :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(533, 72, 92, 23);

        TBerat.setFocusTraversalPolicyProvider(true);
        TBerat.setName("TBerat"); // NOI18N
        TBerat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBeratKeyPressed(evt);
            }
        });
        FormInput.add(TBerat);
        TBerat.setBounds(628, 72, 70, 23);

        jLabel17.setText("Tinggi Badan(Cm) :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(699, 72, 100, 23);

        TTinggi.setHighlighter(null);
        TTinggi.setName("TTinggi"); // NOI18N
        TTinggi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTinggiKeyPressed(evt);
            }
        });
        FormInput.add(TTinggi);
        TTinggi.setBounds(802, 72, 70, 23);

        jLabel18.setText("Nadi(/menit) :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(533, 102, 92, 23);

        TNadi.setFocusTraversalPolicyProvider(true);
        TNadi.setName("TNadi"); // NOI18N
        TNadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNadiKeyPressed(evt);
            }
        });
        FormInput.add(TNadi);
        TNadi.setBounds(628, 102, 70, 23);

        jLabel20.setText("Respirasi(/menit) :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(699, 102, 100, 23);

        TRespirasi.setHighlighter(null);
        TRespirasi.setName("TRespirasi"); // NOI18N
        TRespirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRespirasiKeyPressed(evt);
            }
        });
        FormInput.add(TRespirasi);
        TRespirasi.setBounds(802, 102, 70, 23);

        jLabel22.setText("GCS(E,V,M) :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(533, 132, 92, 23);

        TGCS.setFocusTraversalPolicyProvider(true);
        TGCS.setName("TGCS"); // NOI18N
        TGCS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TGCSKeyPressed(evt);
            }
        });
        FormInput.add(TGCS);
        TGCS.setBounds(628, 132, 244, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,TKdPrw);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyPressed
        Valid.pindah(evt,TAlergi,TTensi);
}//GEN-LAST:event_TSuhuKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if((!TKeluhan.getText().trim().equals(""))||(!TPemeriksaan.getText().trim().equals(""))||
                    (!TSuhu.getText().trim().equals(""))||(!TTensi.getText().trim().equals(""))||
                    (!TAlergi.getText().trim().equals(""))||(!TTinggi.getText().trim().equals(""))||
                    (!TBerat.getText().trim().equals(""))||(!TRespirasi.getText().trim().equals(""))||
                    (!TNadi.getText().trim().equals(""))||(!TGCS.getText().trim().equals(""))){
                Sequel.menyimpan("pemeriksaan_ralan","?,?,?,?,?,?,?,?,?,?,?","Data Pemeriksaan",11,new String[]{
                    TNoRw.getText(),TSuhu.getText(),TTensi.getText(),TNadi.getText(),TRespirasi.getText(),TTinggi.getText(),
                    TBerat.getText(),TGCS.getText(),TKeluhan.getText(),TPemeriksaan.getText(),TAlergi.getText()
                });
            }
            if(TabRawat.getSelectedIndex()==0){
                if(KdDok.getText().trim().equals("")||TDokter.getText().trim().equals("")){
                    Valid.textKosong(KdDok,"Dokter");
                }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
                    Valid.textKosong(TKdPrw,"perawatan");
                }else{
                    Sequel.menyimpan("rawat_jl_dr","?,?,?,?,?,?,?,?,?","No.Rawat, Jenis Perawatan dan Dokter",9,new String[]{
                        TNoRw.getText(),TKdPrw.getText(),KdDok.getText(),BagianRS.getText(),Bhp.getText(),JmDokter.getText(),KSO.getText(),Menejemen.getText(),TTnd.getText()
                    });
                    tampilDr();
                    BtnBatalActionPerformed(evt);
                }                    
            }else if(TabRawat.getSelectedIndex()==1){
                if(kdptg.getText().trim().equals("")||TPerawat.getText().trim().equals("")){
                    Valid.textKosong(kdptg,"Petugas");
                }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
                    Valid.textKosong(TKdPrw,"perawatan");
                }else{
                    Sequel.menyimpan("rawat_jl_pr","?,?,?,?,?,?,?,?,?","No.Rawat, Jenis Perawatan dan Petugas",9,new String[]{
                        TNoRw.getText(),TKdPrw.getText(),kdptg.getText(),BagianRS.getText(),Bhp.getText(),JmPerawat.getText(),KSO.getText(),Menejemen.getText(),TTnd.getText()
                    });
                    tampilPr();
                    BtnBatalActionPerformed(evt);
                }                
            }else if(TabRawat.getSelectedIndex()==2){
                if(KdDok2.getText().trim().equals("")||TDokter2.getText().trim().equals("")){
                    Valid.textKosong(KdDok2,"Dokter");
                }else if(kdptg2.getText().trim().equals("")||TPerawat2.getText().trim().equals("")){
                    Valid.textKosong(kdptg2,"Petugas");
                }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
                    Valid.textKosong(TKdPrw,"perawatan");
                }else{
                    Sequel.menyimpan("rawat_jl_drpr","?,?,?,?,?,?,?,?,?,?,?","No.Rawat, Jenis Perawatan, Dokter & Petugas",11,new String[]{
                        TNoRw.getText(),TKdPrw.getText(),KdDok2.getText(),kdptg2.getText(),BagianRS.getText(),Bhp.getText(),JmDokter.getText(),JmPerawat.getText(),KSO.getText(),Menejemen.getText(),TTnd.getText()
                    });
                    tampilDrPr();
                    BtnBatalActionPerformed(evt);
                }
            }else if(TabRawat.getSelectedIndex()==3){                
                tampilPemeriksaan();
                BtnBatalActionPerformed(evt);
            }           
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,KdDok,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,kdptg,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,kdptg2,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,TGCS,BtnBatal);
            }
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        TSuhu.setText("");
        TKdPrw.setText("");
        TNmPrw.setText("");
        TTensi.setText("");
        TKeluhan.setText("");
        TPemeriksaan.setText("");
        TAlergi.setText("");
        TBerat.setText("");
        TTinggi.setText("");
        TNadi.setText("");
        TRespirasi.setText("");
        TGCS.setText("");
        TTnd.setText("0");
        BagianRS.setText("0");
        Bhp.setText("0");
        JmDokter.setText("0");
        JmPerawat.setText("0");
        TNoRw.requestFocus(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(tabModeDr.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                TNoRw.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                    for(i=0;i<tbRawatDr.getRowCount();i++){ 
                        if(tbRawatDr.getValueAt(i,0).toString().equals("true")){
                            if(var.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from rawat_jl_dr where no_rawat='"+tbRawatDr.getValueAt(i,1).toString()+
                                    "' and kd_jenis_prw='"+tbRawatDr.getValueAt(i,9).toString()+
                                    "' and kd_dokter='"+tbRawatDr.getValueAt(i,5).toString()+"'");
                            }else{
                                if(Sequel.cariRegistrasi(tbRawatDr.getValueAt(i,1).toString())>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                }else{
                                    Sequel.queryu("delete from rawat_jl_dr where no_rawat='"+tbRawatDr.getValueAt(i,1).toString()+
                                        "' and kd_jenis_prw='"+tbRawatDr.getValueAt(i,9).toString()+
                                        "' and kd_dokter='"+tbRawatDr.getValueAt(i,5).toString()+"'");
                                }
                            }                            
                        }
                    }                     
                    tampilDr(); 
            }               
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabModePr.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                TNoRw.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                    for(i=0;i<tbRawatPr.getRowCount();i++){ 
                        if(tbRawatPr.getValueAt(i,0).toString().equals("true")){
                            if(var.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from rawat_jl_pr where no_rawat='"+tbRawatPr.getValueAt(i,1).toString()+
                                    "' and kd_jenis_prw='"+tbRawatPr.getValueAt(i,9).toString()+
                                    "' and nip='"+tbRawatPr.getValueAt(i,5).toString()+"'");
                            }else{
                                if(Sequel.cariRegistrasi(tbRawatPr.getValueAt(i,1).toString())>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                }else{
                                    Sequel.queryu("delete from rawat_jl_pr where no_rawat='"+tbRawatPr.getValueAt(i,1).toString()+
                                        "' and kd_jenis_prw='"+tbRawatPr.getValueAt(i,9).toString()+
                                        "' and nip='"+tbRawatPr.getValueAt(i,5).toString()+"'");
                                }
                            }                            
                        }
                    }                      
                    tampilPr();
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tabModeDrPr.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                TNoRw.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                    for(i=0;i<tbRawatDrPr.getRowCount();i++){ 
                        if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){
                            if(var.getkode().equals("Admin Utama")){
                                Sequel.queryu("delete from rawat_jl_drpr where no_rawat='"+tbRawatDrPr.getValueAt(i,1).toString()+
                                    "' and kd_jenis_prw='"+tbRawatDrPr.getValueAt(i,11).toString()+
                                    "' and kd_dokter='"+tbRawatDrPr.getValueAt(i,5).toString()+"' and nip='"+tbRawatDrPr.getValueAt(i,7).toString()+"'");
                            }else{
                                if(Sequel.cariRegistrasi(tbRawatDrPr.getValueAt(i,1).toString())>0){
                                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                    TCari.requestFocus();
                                }else{
                                    Sequel.queryu("delete from rawat_jl_drpr where no_rawat='"+tbRawatDrPr.getValueAt(i,1).toString()+
                                        "' and kd_jenis_prw='"+tbRawatDrPr.getValueAt(i,11).toString()+
                                        "' and kd_dokter='"+tbRawatDrPr.getValueAt(i,5).toString()+"' and nip='"+tbRawatDrPr.getValueAt(i,7).toString()+"'");
                                }
                            }                            
                        }
                    }                      
                    tampilDrPr();
            }
        }else if(TabRawat.getSelectedIndex()==3){
            if(tabModePemeriksaan.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                TNoRw.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                for(i=0;i<tbPemeriksaan.getRowCount();i++){ 
                    if(tbPemeriksaan.getValueAt(i,0).toString().equals("true")){
                        Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='"+tbPemeriksaan.getValueAt(i,1).toString()+"'");
                    }
                }
                tampilPemeriksaan();
            }
        }

        BtnBatalActionPerformed(evt);
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(TabRawat.getSelectedIndex()==0){
            if(tabModeDr.getRowCount()==0){
                 JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                 BtnBatal.requestFocus();
            }else if(tabModeDr.getRowCount()!=0){
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                String tgl=" reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
                Valid.MyReport("rptJlnDr.jrxml","report","::[ Data Rawat Jalan Yang Ditangani Dokter ]::",
                           "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                           "jns_perawatan.nm_perawatan,rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.biaya_rawat,reg_periksa.tgl_registrasi "+
                           "from pasien inner join reg_periksa "+
                           "inner join jns_perawatan inner join dokter inner join rawat_jl_dr "+
                           "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                           "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                           "where "+tgl+"and rawat_jl_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' order by rawat_jl_dr.no_rawat desc",param);
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabModePr.getRowCount()==0){
                 JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                 BtnBatal.requestFocus();
            }else if(tabModePr.getRowCount()!=0){
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                String tgl=" reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
                Valid.MyReport("rptJlnPr.jrxml","report","::[ Data Rawat Jalan Yang Ditangani Perawat ]::",
                           "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                           "jns_perawatan.nm_perawatan,rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.biaya_rawat,reg_periksa.tgl_registrasi "+
                           "from pasien inner join reg_periksa "+
                           "inner join jns_perawatan inner join petugas inner join rawat_jl_pr "+
                           "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                           "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "and rawat_jl_pr.nip=petugas.nip "+
                           "where "+tgl+"and rawat_jl_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' order by rawat_jl_pr.no_rawat desc",param);
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(tabModeDrPr.getRowCount()==0){
                 JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                 BtnBatal.requestFocus();
            }else if(tabModeDrPr.getRowCount()!=0){
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                String tgl=" reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
                    Valid.MyReport("rptJlnDrPr.jrxml","report","::[ Data Rawat Jalan Yang Ditangani Perawat ]::",
                           "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                           "jns_perawatan.nm_perawatan,rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.biaya_rawat,reg_periksa.tgl_registrasi "+
                           "from pasien inner join reg_periksa inner join dokter "+
                           "inner join jns_perawatan inner join petugas inner join rawat_jl_drpr "+
                           "on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                           "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                           "and rawat_jl_drpr.nip=petugas.nip "+
                           "where "+tgl+"and rawat_jl_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                           "order by rawat_jl_drpr.no_rawat desc",param);
            }
        }else if(TabRawat.getSelectedIndex()==3){
            if(tabModePemeriksaan.getRowCount()==0){
                 JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                 BtnBatal.requestFocus();
            }else if(tabModePemeriksaan.getRowCount()!=0){
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                String tgl=" reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
                    Valid.MyReport("rptJlnPemeriksaan.jrxml","report","::[ Data Pemeriksaan Rawat Jalan ]::",
                            "select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                            "reg_periksa.tgl_registrasi,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi, " +
                            "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, " +
                            "pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.keluhan, " +
                            "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi from pasien inner join reg_periksa inner join pemeriksaan_ralan "+
                            "on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                             tgl+"and pemeriksaan_ralan.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                             tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                             tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                             tgl+"and pemeriksaan_ralan.keluhan like '%"+TCari.getText().trim()+"%' or "+
                             tgl+"and pemeriksaan_ralan.pemeriksaan like '%"+TCari.getText().trim()+"%' "+
                            "order by pemeriksaan_ralan.no_rawat desc",param);
            }
        }

        
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
        }else if(TabRawat.getSelectedIndex()==2){
            tampilDrPr();
        }else if(TabRawat.getSelectedIndex()==3){
            tampilPemeriksaan();
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
        }else if(TabRawat.getSelectedIndex()==2){
            tampilDrPr();
        }else if(TabRawat.getSelectedIndex()==3){
            tampilPemeriksaan();
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyPressed
        Valid.pindah(evt,TSuhu,TBerat);
    }//GEN-LAST:event_TTensiKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(!TKdPrw.getText().trim().equals("")){
            isJns();
        }
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
        }else if(TabRawat.getSelectedIndex()==2){
            tampilDrPr();
        }else if(TabRawat.getSelectedIndex()==3){
            tampilPemeriksaan();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbRawatDrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrMouseClicked
        if(tabModeDr.getRowCount()!=0){
            try {
                getDataDr();
            } catch (java.lang.NullPointerException e) {
            }
            
            
        }
}//GEN-LAST:event_tbRawatDrMouseClicked

    private void tbRawatDrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrKeyPressed
        if(tabModeDr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataDr();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
        }
}//GEN-LAST:event_tbRawatDrKeyPressed

    private void tbRawatPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatPrMouseClicked
        if(tabModePr.getRowCount()!=0){
            try {
                getDataPr();
            } catch (java.lang.NullPointerException e) {
            }
            
        }
}//GEN-LAST:event_tbRawatPrMouseClicked

    private void tbRawatPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatPrKeyPressed
        if(tabModePr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
           
        }
}//GEN-LAST:event_tbRawatPrKeyPressed

private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokterActionPerformed(null);
        }else{            
            Valid.pindah(evt,TGCS,BtnSimpan);
        }
}//GEN-LAST:event_KdDokKeyPressed

private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokterActionPerformed
        var.setform("DlgRawatJalan");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeekDokterActionPerformed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",TPerawat,kdptg.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,TGCS,BtnSimpan);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        var.setform("DlgRawatJalan");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnSeekPetugasActionPerformed

private void TKdPrwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPrwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isJns();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnTindakanActionPerformed(null);
        }else{            
            Valid.pindah(evt,TNoRw,TKeluhan);
        }
}//GEN-LAST:event_TKdPrwKeyPressed

private void btnTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                if(TDokter.getText().trim().equals("")){
                    Valid.textKosong(KdDok,"Dokter");
                }else{
                    if(var.getkode().equals("Admin Utama")){
                        perawatan.setNoRm(TNoRw.getText(),KdDok.getText(),TDokter.getText(),"rawat_jl_dr",TSuhu.getText(),TTensi.getText(),TKeluhan.getText(),TPemeriksaan.getText(),"","","-","-",
                                TBerat.getText(),TTinggi.getText(),TNadi.getText(),TRespirasi.getText(),TGCS.getText(),TAlergi.getText());                        
                        var.setform("DlgRawatJalan");
                        perawatan.isCek();
                        perawatan.tampil();
                        perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                        perawatan.setLocationRelativeTo(internalFrame1);
                        perawatan.setVisible(true);
                    }else{
                        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        }else{                        
                            perawatan.setNoRm(TNoRw.getText(),KdDok.getText(),TDokter.getText(),"rawat_jl_dr",TSuhu.getText(),TTensi.getText(),TKeluhan.getText(),TPemeriksaan.getText(),"","","-","-",
                                TBerat.getText(),TTinggi.getText(),TNadi.getText(),TRespirasi.getText(),TGCS.getText(),TAlergi.getText());                        
                            var.setform("DlgRawatJalan");
                            perawatan.isCek();
                            perawatan.tampil();
                            perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            perawatan.setLocationRelativeTo(internalFrame1);
                            perawatan.setVisible(true);
                        }
                    }
                }                
            }else if(TabRawat.getSelectedIndex()==1){
                if(TPerawat.getText().trim().equals("")){
                    Valid.textKosong(kdptg,"perawat");
                }else{
                    if(var.getkode().equals("Admin Utama")){
                        perawatan.setNoRm(TNoRw.getText(),kdptg.getText(),TPerawat.getText(),"rawat_jl_pr",TSuhu.getText(),TTensi.getText(),TKeluhan.getText(),TPemeriksaan.getText(),"","","-","-",
                            TBerat.getText(),TTinggi.getText(),TNadi.getText(),TRespirasi.getText(),TGCS.getText(),TAlergi.getText());                        
                        perawatan.isCek();
                        perawatan.tampil();
                        perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                        perawatan.setLocationRelativeTo(internalFrame1);
                        perawatan.setVisible(true);
                    }else{
                        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        }else{ 
                            perawatan.setNoRm(TNoRw.getText(),kdptg.getText(),TPerawat.getText(),"rawat_jl_pr",TSuhu.getText(),TTensi.getText(),TKeluhan.getText(),TPemeriksaan.getText(),"","","-","-",
                            TBerat.getText(),TTinggi.getText(),TNadi.getText(),TRespirasi.getText(),TGCS.getText(),TAlergi.getText());                        
                            perawatan.isCek();
                            perawatan.tampil();
                            perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            perawatan.setLocationRelativeTo(internalFrame1);
                            perawatan.setVisible(true);
                        }
                    }                    
                }                
            }else if(TabRawat.getSelectedIndex()==2){
                if(TDokter2.getText().trim().equals("")){
                    Valid.textKosong(KdDok2,"Dokter");
                }else if(TPerawat2.getText().trim().equals("")){
                    Valid.textKosong(kdptg2,"perawat");
                }else{
                    if(var.getkode().equals("Admin Utama")){
                        perawatan.setNoRm(TNoRw.getText(),KdDok2.getText(),TDokter2.getText(),"rawat_jl_drpr",TSuhu.getText(),TTensi.getText(),TKeluhan.getText(),TPemeriksaan.getText(),"","",kdptg2.getText(),TPerawat2.getText(),
                                TBerat.getText(),TTinggi.getText(),TNadi.getText(),TRespirasi.getText(),TGCS.getText(),TAlergi.getText());                        
                        perawatan.isCek();
                        perawatan.tampil();
                        perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                        perawatan.setLocationRelativeTo(internalFrame1);
                        perawatan.setVisible(true);
                    }else{
                        if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                            TCari.requestFocus();
                        }else{ 
                            perawatan.setNoRm(TNoRw.getText(),KdDok2.getText(),TDokter2.getText(),"rawat_jl_drpr",TSuhu.getText(),TTensi.getText(),TKeluhan.getText(),TPemeriksaan.getText(),"","",kdptg2.getText(),TPerawat2.getText(),
                                TBerat.getText(),TTinggi.getText(),TNadi.getText(),TRespirasi.getText(),TGCS.getText(),TAlergi.getText());                        
                            perawatan.isCek();
                            perawatan.tampil();
                            perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            perawatan.setLocationRelativeTo(internalFrame1);
                            perawatan.setVisible(true);
                        }
                    }
                }                
            }else if(TabRawat.getSelectedIndex()==3){
                JOptionPane.showMessageDialog(rootPane,"Silahkan pilih penanganan..!!!");
                TCari.requestFocus();
            }                  
        }    
}//GEN-LAST:event_btnTindakanActionPerformed

private void btnTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTindakanKeyPressed
        Valid.pindah(evt,TKdPrw,TKeluhan);
}//GEN-LAST:event_btnTindakanKeyPressed

private void TKeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKeluhanKeyPressed
        Valid.pindah(evt,TKdPrw,TPemeriksaan);
}//GEN-LAST:event_TKeluhanKeyPressed

private void TPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPemeriksaanKeyPressed
    Valid.pindah(evt,TKeluhan,TAlergi); 
}//GEN-LAST:event_TPemeriksaanKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                if(KdDok.getText().trim().equals("")||TDokter.getText().trim().equals("")){
                    Valid.textKosong(KdDok,"Dokter");
                }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
                    Valid.textKosong(TKdPrw,"perawatan");
                }else{
                    if(tbRawatDr.getSelectedRow()>-1){
                        Sequel.mengedit("rawat_jl_dr","no_rawat=? and kd_jenis_prw=? and kd_dokter=?", 
                            "no_rawat=?,kd_jenis_prw=?,kd_dokter=?,"+
                            "material=?,bhp=?,tarif_tindakandr=?,biaya_rawat=?,kso=?,menejemen=?", 12,new String[]{                                    
                                TNoRw.getText(),TKdPrw.getText(),KdDok.getText(),BagianRS.getText(),Bhp.getText(),JmDokter.getText(),
                                TTnd.getText(),KSO.getText(),Menejemen.getText(),tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),1).toString(),
                                tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString(),
                                tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),5).toString()
                            }
                        );
                        tampilDr();
                        BtnBatalActionPerformed(evt);
                    }else{
                        JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                        TCari.requestFocus();
                    }                        
                }                
            }else if(TabRawat.getSelectedIndex()==1){
                if(kdptg.getText().trim().equals("")||TPerawat.getText().trim().equals("")){
                    Valid.textKosong(kdptg,"Petugas");
                }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
                    Valid.textKosong(TKdPrw,"perawatan");
                }else{
                    if(tbRawatPr.getSelectedRow()>-1){
                        Sequel.mengedit("rawat_jl_pr","no_rawat=? and kd_jenis_prw=? and nip=?", 
                            "no_rawat=?,kd_jenis_prw=?,nip=?,material=?,bhp=?,tarif_tindakanpr=?,biaya_rawat=?,kso=?,menejemen=?", 12,new String[]{                                    
                                TNoRw.getText(),TKdPrw.getText(),kdptg.getText(),BagianRS.getText(),Bhp.getText(),JmPerawat.getText(),
                                TTnd.getText(),KSO.getText(),Menejemen.getText(),tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),1).toString(),
                                tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString(),
                                tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),5).toString()
                            }
                        );
                        tampilPr();
                        BtnBatalActionPerformed(evt);
                    }else{
                        JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                        TCari.requestFocus();
                    }   
                }                    
            }else if(TabRawat.getSelectedIndex()==2){
                if(KdDok2.getText().trim().equals("")||TDokter2.getText().trim().equals("")){
                    Valid.textKosong(KdDok2,"Dokter");
                }else if(kdptg2.getText().trim().equals("")||TPerawat2.getText().trim().equals("")){
                    Valid.textKosong(kdptg2,"Petugas");
                }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
                    Valid.textKosong(TKdPrw,"perawatan");
                }else{
                    if(tbRawatDrPr.getSelectedRow()>-1){
                        Sequel.mengedit("rawat_jl_drpr","no_rawat=? and kd_jenis_prw=? and kd_dokter=? and nip=?", 
                            "no_rawat=?,kd_jenis_prw=?,kd_dokter=?,nip=?,"+
                            "material=?,bhp=?,tarif_tindakandr=?,tarif_tindakanpr=?,biaya_rawat=?,kso=?,menejemen=?", 15,new String[]{                                    
                                TNoRw.getText(),TKdPrw.getText(),KdDok2.getText(),kdptg2.getText(),BagianRS.getText(),Bhp.getText(),JmDokter.getText(),JmPerawat.getText(),
                                TTnd.getText(),KSO.getText(),Menejemen.getText(),tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),1).toString(),
                                tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),11).toString(),
                                tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),5).toString(),
                                tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),7).toString()
                            }
                        );
                        tampilDrPr();
                        BtnBatalActionPerformed(evt);
                    }else{
                        JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                        TCari.requestFocus();
                    } 
                }                    
            }else if(TabRawat.getSelectedIndex()==3){
                if(TKeluhan.getText().trim().equals("")&&TPemeriksaan.getText().trim().equals("")&&
                    TSuhu.getText().trim().equals("")&&TTensi.getText().trim().equals("")&&
                    TAlergi.getText().trim().equals("")&&TTinggi.getText().trim().equals("")&&
                    TBerat.getText().trim().equals("")&&TRespirasi.getText().trim().equals("")&&
                    TNadi.getText().trim().equals("")&&TGCS.getText().trim().equals("")){
                    Valid.textKosong(TKeluhan,"Hasil Periksa/Perkambangan/Suhu Badan/Tensi");
                }else{
                    if(tbPemeriksaan.getSelectedRow()>-1){
                        Sequel.mengedit("pemeriksaan_ralan","no_rawat='"+tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1)+"'",
                            "no_rawat='"+TNoRw.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+"',"+
                            "keluhan='"+TKeluhan.getText()+"',pemeriksaan='"+TPemeriksaan.getText()+"',"+
                            "nadi='"+TNadi.getText()+"',respirasi='"+TRespirasi.getText()+"',"+
                            "tinggi='"+TTinggi.getText()+"',berat='"+TBerat.getText()+"',"+
                            "gcs='"+TGCS.getText()+"',alergi='"+TAlergi.getText()+"'");
                        tampilPemeriksaan();
                        BtnBatalActionPerformed(evt);
                    }else{
                        JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data yang mau diganti..!!");
                        TCari.requestFocus();
                    } 
                }                
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void tbRawatDrPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrPrMouseClicked
        if(tabModeDrPr.getRowCount()!=0){
            try {
                getDataDrPr();
            } catch (java.lang.NullPointerException e) {
            }            
        }
    }//GEN-LAST:event_tbRawatDrPrMouseClicked

    private void tbRawatDrPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrPrKeyPressed
        if(tabModeDrPr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataDrPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbRawatDrPrKeyPressed

    private void kdptg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptg2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",TPerawat2,kdptg2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugas2ActionPerformed(null);
        }else{
            Valid.pindah(evt,KdDok2,BtnSimpan);
        }    
    }//GEN-LAST:event_kdptg2KeyPressed

    private void BtnSeekPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas2ActionPerformed
        var.setform("DlgRawatJalan");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugas2ActionPerformed

    private void KdDok2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter2,KdDok2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekDokter2ActionPerformed(null);
        }else{            
            Valid.pindah(evt,TGCS,kdptg2);
        }
    }//GEN-LAST:event_KdDok2KeyPressed

    private void BtnSeekDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter2ActionPerformed
        var.setform("DlgRawatJalan");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter2ActionPerformed

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanMouseClicked
        if(tabModePemeriksaan.getRowCount()!=0){
            try {
                getDataPemeriksaan();
            } catch (java.lang.NullPointerException e) {
            }

        }
    }//GEN-LAST:event_tbPemeriksaanMouseClicked

    private void tbPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyPressed
        if(tabModePemeriksaan.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPemeriksaan();
                } catch (java.lang.NullPointerException e) {
                }
            }

        }
    }//GEN-LAST:event_tbPemeriksaanKeyPressed

    private void TAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlergiKeyPressed
        Valid.pindah(evt,TPemeriksaan,TSuhu); 
    }//GEN-LAST:event_TAlergiKeyPressed

    private void TBeratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBeratKeyPressed
        Valid.pindah(evt,TTensi,TTinggi); 
    }//GEN-LAST:event_TBeratKeyPressed

    private void TTinggiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTinggiKeyPressed
        Valid.pindah(evt,TBerat,TNadi); 
    }//GEN-LAST:event_TTinggiKeyPressed

    private void TNadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNadiKeyPressed
        Valid.pindah(evt,TTinggi,TRespirasi); 
    }//GEN-LAST:event_TNadiKeyPressed

    private void TRespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRespirasiKeyPressed
        Valid.pindah(evt,TNadi,TGCS); 
    }//GEN-LAST:event_TRespirasiKeyPressed

    private void TGCSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TGCSKeyPressed
        if(TabRawat.getSelectedIndex()==0){
            Valid.pindah(evt,TRespirasi,KdDok);
        }else if(TabRawat.getSelectedIndex()==1){
            Valid.pindah(evt,TRespirasi,kdptg);
        }else if(TabRawat.getSelectedIndex()==2){
            Valid.pindah(evt,TRespirasi,KdDok2);
        }else if(TabRawat.getSelectedIndex()==3){
            Valid.pindah(evt,TRespirasi,BtnSimpan);
        }
    }//GEN-LAST:event_TGCSKeyPressed

    private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(TPasien.getText().trim().equals("")||TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.isCek();
            dlgrwinap.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ralan");
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPemberianObatActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRawatJalan dialog = new DlgRawatJalan(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField BagianRS;
    private javax.swing.JTextField Bhp;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSeekDokter2;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSeekPetugas2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private javax.swing.JTextField JmDokter;
    private javax.swing.JTextField JmPerawat;
    private javax.swing.JTextField KSO;
    private widget.TextBox KdDok;
    private widget.TextBox KdDok2;
    private widget.Label LCount;
    private javax.swing.JTextField Menejemen;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TAlergi;
    private widget.TextBox TBerat;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox TDokter2;
    private widget.TextBox TGCS;
    private widget.TextBox TKdPrw;
    private widget.TextBox TKeluhan;
    private widget.TextBox TNadi;
    private widget.TextBox TNmPrw;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPemeriksaan;
    private widget.TextBox TPerawat;
    private widget.TextBox TPerawat2;
    private widget.TextBox TRespirasi;
    private widget.TextBox TSuhu;
    private widget.TextBox TTensi;
    private widget.TextBox TTinggi;
    private javax.swing.JTextField TTnd;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Button btnTindakan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdptg;
    private widget.TextBox kdptg2;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbPemeriksaan;
    private widget.Table tbRawatDr;
    private widget.Table tbRawatDrPr;
    private widget.Table tbRawatPr;
    // End of variables declaration//GEN-END:variables

    public void tampilDr() {
        Valid.tabelKosong(tabModeDr);
        try{
            ps=koneksi.prepareStatement("select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_jl_dr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_dr.kd_dokter,"+
                   "dokter.nm_dokter,rawat_jl_dr.biaya_rawat,reg_periksa.tgl_registrasi,rawat_jl_dr.kd_jenis_prw "+
                   "from pasien inner join reg_periksa "+
                   "inner join jns_perawatan inner join dokter inner join rawat_jl_dr "+
                   "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                   "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.no_rawat like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and jns_perawatan.nm_perawatan like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_dokter like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and dokter.nm_dokter like ? order by rawat_jl_dr.no_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    Object[] data={false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getDouble(7),
                                   rs.getString(8),
                                   rs.getString("kd_jenis_prw")};
                    tabModeDr.addRow(data);
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModeDr.getRowCount());
    }

    private void getDataDr() {
        if(tbRawatDr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),1).toString());
            TNoRM.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),2).toString());
            TPasien.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),3).toString());   
            KdDok.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),5).toString());
            TDokter.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),6).toString());
            Sequel.cariIsi("select kd_jenis_prw from jns_perawatan where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TKdPrw,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());    
            isJns();
        }
    }

    private void tampilPr() {        
        Valid.tabelKosong(tabModePr);
        try{       
            ps2=koneksi.prepareStatement("select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_jl_pr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_pr.nip,petugas.nama,"+
                   "rawat_jl_pr.biaya_rawat,reg_periksa.tgl_registrasi,rawat_jl_pr.kd_jenis_prw "+
                   "from pasien inner join reg_periksa "+
                   "inner join jns_perawatan inner join petugas inner join rawat_jl_pr "+
                   "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_pr.nip=petugas.nip "+
                   "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.no_rawat like ? or "+
                   "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                   "reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and jns_perawatan.nm_perawatan like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.nip like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and petugas.nama like ? order by rawat_jl_pr.no_rawat desc");
            try {
                ps2.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(3,"%"+TCari.getText().trim()+"%");
                ps2.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(6,"%"+TCari.getText().trim()+"%");
                ps2.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(9,"%"+TCari.getText().trim()+"%");
                ps2.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(12,"%"+TCari.getText().trim()+"%");
                ps2.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(15,"%"+TCari.getText().trim()+"%");
                ps2.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps2.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps2.setString(18,"%"+TCari.getText().trim()+"%");
                rs=ps2.executeQuery();
                while(rs.next()){
                    Object[] data={false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getDouble(7),
                                   rs.getString(8),
                                   rs.getString("kd_jenis_prw")};
                    tabModePr.addRow(data);
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }                
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModePr.getRowCount());
    }

    private void getDataPr() {
        if(tbRawatPr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),1).toString());
            TNoRM.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),2).toString());
            TPasien.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),3).toString()); 
            kdptg.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),5).toString());
            TPerawat.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),6).toString());
            Sequel.cariIsi("select kd_jenis_prw from jns_perawatan where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TKdPrw,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());    
            isJns();
        }
    }
    
    private void tampilDrPr() {        
        Valid.tabelKosong(tabModeDrPr);
        try{      
            ps3=koneksi.prepareStatement("select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "concat(rawat_jl_drpr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_drpr.kd_dokter,dokter.nm_dokter,"+
                   "rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.biaya_rawat,reg_periksa.tgl_registrasi,rawat_jl_drpr.kd_jenis_prw "+
                   "from pasien inner join reg_periksa inner join petugas "+
                   "inner join jns_perawatan inner join dokter inner join rawat_jl_drpr "+
                   "on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                   "and rawat_jl_drpr.nip=petugas.nip "+
                   "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.no_rawat like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and jns_perawatan.nm_perawatan like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.kd_dokter like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and dokter.nm_dokter like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.nip like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and petugas.nama like ? order by rawat_jl_drpr.no_rawat desc");
            try{
                ps3.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(3,"%"+TCari.getText().trim()+"%");
                ps3.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(6,"%"+TCari.getText().trim()+"%");
                ps3.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(9,"%"+TCari.getText().trim()+"%");
                ps3.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(12,"%"+TCari.getText().trim()+"%");
                ps3.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(15,"%"+TCari.getText().trim()+"%");
                ps3.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(18,"%"+TCari.getText().trim()+"%");
                ps3.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(21,"%"+TCari.getText().trim()+"%");
                ps3.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps3.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps3.setString(24,"%"+TCari.getText().trim()+"%");
                rs=ps3.executeQuery();
                while(rs.next()){
                    Object[] data={false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getString(7),
                                   rs.getString(8),
                                   rs.getDouble(9),
                                   rs.getString(10),
                                   rs.getString("kd_jenis_prw")};
                    tabModeDrPr.addRow(data);
                }
            }catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps3!=null){
                    ps3.close();
                }
            }   
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModeDrPr.getRowCount());
    }
    
    private void getDataDrPr() {
        if(tbRawatDrPr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),1).toString());
            TNoRM.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),2).toString());
            TPasien.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),3).toString()); 
            KdDok2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),5).toString());
            TDokter2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),6).toString());
            kdptg2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),7).toString());
            TPerawat2.setText(tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),8).toString());
            Sequel.cariIsi("select kd_jenis_prw from jns_perawatan where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TKdPrw,tbRawatDrPr.getValueAt(tbRawatDrPr.getSelectedRow(),4).toString());    
            isJns();
        }
    }
    
    private void isRawat(){
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
    }

    private void isPsien(){
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }

    private void isJns(){
        Sequel.cariIsi("select nm_perawatan from jns_perawatan where kd_jenis_prw=? ",TNmPrw,TKdPrw.getText());
        Sequel.cariIsi("select bhp from jns_perawatan where kd_jenis_prw=? ",Bhp,TKdPrw.getText());
        Sequel.cariIsi("select material from jns_perawatan where kd_jenis_prw=? ",BagianRS,TKdPrw.getText());
        Sequel.cariIsi("select tarif_tindakandr from jns_perawatan where kd_jenis_prw=? ",JmDokter,TKdPrw.getText());
        Sequel.cariIsi("select tarif_tindakanpr from jns_perawatan where kd_jenis_prw=? ",JmPerawat,TKdPrw.getText());
        Sequel.cariIsi("select kso from jns_perawatan where kd_jenis_prw=? ",KSO,TKdPrw.getText());
        Sequel.cariIsi("select menejemen from jns_perawatan where kd_jenis_prw=? ",Menejemen,TKdPrw.getText());
        if(TabRawat.getSelectedIndex()==0){
            Sequel.cariIsi("select total_byrdr from jns_perawatan where kd_jenis_prw=? ",TTnd,TKdPrw.getText());
        }else if(TabRawat.getSelectedIndex()==1){
            Sequel.cariIsi("select total_byrpr from jns_perawatan where kd_jenis_prw=? ",TTnd,TKdPrw.getText());
        }else if(TabRawat.getSelectedIndex()==2){
            Sequel.cariIsi("select total_byrdrpr from jns_perawatan where kd_jenis_prw=? ",TTnd,TKdPrw.getText());
        }        
    }
    
    public void setNoRm(String norwt,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdDok.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",norwt));
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();  
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
        ChkInput.setSelected(true);
        isForm(); 
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,188));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.gettindakan_ralan());
        BtnHapus.setEnabled(var.gettindakan_ralan());
        BtnEdit.setEnabled(var.gettindakan_ralan());
        BtnPrint.setEnabled(var.gettindakan_ralan());
        
    }

    private void tampilPemeriksaan() {
        Valid.tabelKosong(tabModePemeriksaan);
        try{  
            ps4=koneksi.prepareStatement("select pemeriksaan_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "reg_periksa.tgl_registrasi,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi, " +
                   "pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,pemeriksaan_ralan.tinggi, " +
                   "pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.keluhan, " +
                   "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi from pasien inner join reg_periksa inner join pemeriksaan_ralan "+
                   "on pemeriksaan_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where  "+
                    "reg_periksa.tgl_registrasi between ? and ? and pemeriksaan_ralan.no_rawat like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and pemeriksaan_ralan.keluhan like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and pemeriksaan_ralan.pemeriksaan like ? "+
                   "order by pemeriksaan_ralan.no_rawat desc"); 
            try{
                ps4.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(3,"%"+TCari.getText().trim()+"%");
                ps4.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(6,"%"+TCari.getText().trim()+"%");
                ps4.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(9,"%"+TCari.getText().trim()+"%");
                ps4.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(12,"%"+TCari.getText().trim()+"%");
                ps4.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps4.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps4.setString(15,"%"+TCari.getText().trim()+"%");
                rs=ps4.executeQuery();
                while(rs.next()){
                    tabModePemeriksaan.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getString(14)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps4!=null){
                    ps4.close();
                }
            }                  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabModePemeriksaan.getRowCount());
    }
    
    private void getDataPemeriksaan() {
        if(tbPemeriksaan.getSelectedRow()!= -1){
            TNoRw.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),1).toString());
            TNoRM.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),2).toString());
            TPasien.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),3).toString());             
            TSuhu.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),5).toString()); 
            TTensi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),6).toString()); 
            TNadi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),7).toString()); 
            TRespirasi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),8).toString()); 
            TTinggi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),9).toString()); 
            TBerat.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),10).toString());  
            TGCS.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),11).toString()); 
            TKeluhan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),12).toString()); 
            TPemeriksaan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),13).toString());              
            TAlergi.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(),14).toString()); 
        }
    }

}
