/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * kontribusi dari dokter Salim Mulyana
 */

package surat;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariDokter2;
import laporan.DlgCariPenyakit;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgCariPoli2;


/**
 * 
 * @author salimmulyana
 */
public final class SuratLepasRawat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,kuota=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariDokter2 dokter2=new DlgCariDokter2(null,false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariPoli2 poli2=new DlgCariPoli2(null,false);
    private String URUTNOREG="",kddokter="",nmdokter="",kdpoli="",finger="",nmpoli="",aktifjadwal="";
    private DlgCariPenyakit penyakit=new DlgCariPenyakit(null,false);
    
    public SuratLepasRawat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Kode Dokter","Nama Dokter",
            "Umur","Tgl.Lahir","Alamat","Pekerjaan","Tgl.Rawat","Tgl.Pulang",
            "Tgl.Control","Tempat","Kode Poli","Nama Poli","Diagnosa Akhir","Terapi","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(155);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(60);
            }else if(i==11){
                column.setPreferredWidth(140);
            }else if(i==12){
                column.setPreferredWidth(60);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(160);
            }else if(i==16){
                column.setPreferredWidth(160);
            }else if(i==17){
                column.setPreferredWidth(90);
            }else if(i==18){
                column.setPreferredWidth(90);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));  
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        Tempat.setDocument(new batasInput((int)50).getKata(Tempat)); 
        KdPoli.setDocument(new batasInput((int)5).getKata(KdPoli));  
        Diagnosa.setDocument(new batasInput((int)50).getKata(Diagnosa));
        Terapi.setDocument(new batasInput((int)50).getKata(Terapi));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
      
        
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
        
           petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                NIP.requestFocus();
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
           
           penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if( penyakit.getTable().getSelectedRow()!= -1){ 
                    if((penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString()+" - "+penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString()).length()<50){
                        Diagnosa.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString()+" - "+penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                    }else{
                        Diagnosa.setText((penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString()+" - "+penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString()).substring(0,50));
                    }   
                }  
                Diagnosa.requestFocus();
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
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
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
        
        dokter2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter2.getTable().getSelectedRow()!= -1){                    
                    KdDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),1).toString());
                    if(aktifjadwal.equals("aktif")){
                        kuota=Integer.parseInt(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),13).toString());
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){                    
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
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
        
        poli2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli2.getTable().getSelectedRow()!= -1){                    
                    KdPoli.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(),0).toString());
                    NmPoli.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(),1).toString());
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
        
        try {
            aktifjadwal=koneksiDB.JADWALDOKTERDIREGISTRASI();
            URUTNOREG=koneksiDB.URUTNOREG();
        } catch (Exception ex) {
            aktifjadwal="";
            URUTNOREG="";
        }
        
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSuratLepasRawat = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        Terapi = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel17 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel16 = new widget.Label();
        jLabel13 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        Diagnosa = new widget.TextBox();
        jLabel23 = new widget.Label();
        btnDiagnosa = new widget.Button();
        BtnPoli = new widget.Button();
        NmPoli = new widget.TextBox();
        KdPoli = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel20 = new widget.Label();
        TanggalControl = new widget.Tanggal();
        jLabel14 = new widget.Label();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        KdDokter = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        TanggalRawat = new widget.Tanggal();
        Tempat = new widget.TextBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSuratLepasRawat.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratLepasRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratLepasRawat.setForeground(new java.awt.Color(50, 50, 50));
        MnSuratLepasRawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratLepasRawat.setText("SURAT LEPAS RAWAT");
        MnSuratLepasRawat.setName("MnSuratLepasRawat"); // NOI18N
        MnSuratLepasRawat.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSuratLepasRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratLepasRawatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSuratLepasRawat);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Surat Lepas Rawat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

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

        jLabel19.setText("Tgl. Surat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-09-2022" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-09-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 186));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

        Terapi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Terapi.setName("Terapi"); // NOI18N
        Terapi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerapiActionPerformed(evt);
            }
        });
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
        FormInput.add(Terapi);
        Terapi.setBounds(400, 130, 630, 23);

        jLabel10.setText("Terapi :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(350, 130, 40, 20);

        jLabel11.setText("Tempat :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(10, 130, 100, 23);

        jLabel17.setText("Tgl.Lahir :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(10, 30, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(70, 30, 130, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(670, 30, 70, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormInput.add(NIP);
        NIP.setBounds(750, 30, 94, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(850, 30, 187, 24);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(1040, 30, 28, 23);

        jLabel16.setText("Dirawat Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(220, 30, 90, 23);

        jLabel13.setText("s/d");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(420, 30, 20, 23);

        TanggalPulang.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-09-2022" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalPulangActionPerformed(evt);
            }
        });
        TanggalPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPulangKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPulang);
        TanggalPulang.setBounds(460, 30, 90, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiagnosaActionPerformed(evt);
            }
        });
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(750, 100, 249, 23);

        jLabel23.setText("Diagnosa :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(680, 100, 60, 23);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa.setMnemonic('3');
        btnDiagnosa.setToolTipText("Alt+3");
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        btnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyTyped(evt);
            }
        });
        FormInput.add(btnDiagnosa);
        btnDiagnosa.setBounds(1000, 100, 28, 23);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli.setMnemonic('X');
        BtnPoli.setToolTipText("Alt+X");
        BtnPoli.setName("BtnPoli"); // NOI18N
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliActionPerformed(evt);
            }
        });
        BtnPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPoliKeyPressed(evt);
            }
        });
        FormInput.add(BtnPoli);
        BtnPoli.setBounds(640, 100, 28, 23);

        NmPoli.setEditable(false);
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(470, 100, 165, 23);

        KdPoli.setEditable(false);
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormInput.add(KdPoli);
        KdPoli.setBounds(400, 100, 70, 23);

        jLabel15.setText("Unit/Poli :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(340, 100, 50, 23);

        jLabel20.setText("Tanggal Control :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel20);
        jLabel20.setBounds(10, 100, 100, 23);

        TanggalControl.setForeground(new java.awt.Color(50, 70, 50));
        TanggalControl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-09-2022" }));
        TanggalControl.setDisplayFormat("dd-MM-yyyy");
        TanggalControl.setName("TanggalControl"); // NOI18N
        TanggalControl.setOpaque(false);
        TanggalControl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalControlKeyPressed(evt);
            }
        });
        FormInput.add(TanggalControl);
        TanggalControl.setBounds(120, 100, 90, 23);

        jLabel14.setText("Mohon Kembali Control :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(10, 70, 130, 20);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(840, 0, 160, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('X');
        BtnDokter.setToolTipText("Alt+X");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(1000, 0, 28, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(750, 0, 87, 23);

        jLabel9.setText("Dokter :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(650, 0, 92, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(10, 0, 60, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(70, 0, 136, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(210, 0, 111, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(320, 0, 365, 23);

        TanggalRawat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRawat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-09-2022" }));
        TanggalRawat.setDisplayFormat("dd-MM-yyyy");
        TanggalRawat.setName("TanggalRawat"); // NOI18N
        TanggalRawat.setOpaque(false);
        TanggalRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalRawatActionPerformed(evt);
            }
        });
        TanggalRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRawatKeyPressed(evt);
            }
        });
        FormInput.add(TanggalRawat);
        TanggalRawat.setBounds(320, 30, 90, 23);

        Tempat.setName("Tempat"); // NOI18N
        Tempat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TempatActionPerformed(evt);
            }
        });
        FormInput.add(Tempat);
        Tempat.setBounds(120, 130, 200, 24);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.PAGE_START);
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(Tempat.getText().trim().equals("")){
            Valid.textKosong(Tempat,"Tempat");
        }else if(KdPoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
            Valid.textKosong(KdPoli,"Poli");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(Terapi.getText().trim().equals("")){
            Valid.textKosong(Terapi,"Terapi");
        }else{
            if(Sequel.menyimpantf("surat_lepas_rawat","?,?,?,?,?,?,?,?,?,?","Data",10,new String[]{
                    TNoRw.getText(),KdDokter.getText(),Valid.SetTgl(TanggalRawat.getSelectedItem()+""),Valid.SetTgl(TanggalPulang.getSelectedItem()+""),
                    Valid.SetTgl(TanggalControl.getSelectedItem()+""),Tempat.getText(),KdPoli.getText(),Diagnosa.getText(),Terapi.getText(),
                    NIP.getText()
                })==true){
                    tampil();
                    emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Terapi,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();  
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }   

}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(Tempat.getText().trim().equals("")){
            Valid.textKosong(Tempat,"Tempat");
        }else if(KdPoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
            Valid.textKosong(KdPoli,"Poli");
        }else if(Diagnosa.getText().trim().equals("")){
            Valid.textKosong(Diagnosa,"Diagnosa");
        }else if(Terapi.getText().trim().equals("")){
            Valid.textKosong(Terapi,"Terapi");
        }else{ 
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptDataSuratLepasRawat.jasper","report","::[ Data Surat Lepas Rawat ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,surat_lepas_rawat.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur, "+
                    "pasien.tgl_lahir,pasien.alamat,pasien.pekerjaan,surat_lepas_rawat.tanggal_rawat,surat_lepas_rawat.tanggal_pulang,surat_lepas_rawat.tanggal_control, "+
                    "surat_lepas_rawat.tempat,surat_lepas_rawat.kd_poli,poliklinik.nm_poli,surat_lepas_rawat.diagnosa,surat_lepas_rawat.terapi, "+
                    "surat_lepas_rawat.nip,petugas.nama from surat_lepas_rawat inner join reg_periksa on surat_lepas_rawat.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on surat_lepas_rawat.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on surat_lepas_rawat.kd_poli=poliklinik.kd_poli "+
                    "inner join petugas on surat_lepas_rawat.nip=petugas.nip where "+
                    "surat_lepas_rawat.tanggal_rawat between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by surat_lepas_rawat.tanggal_rawat",param);
            }else{
                Valid.MyReportqry("rptDataSuratLepasRawat.jasper","report","::[ Data Surat Lepas Rawat ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,surat_lepas_rawat.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur, "+
                    "pasien.tgl_lahir,pasien.alamat,pasien.pekerjaan,surat_lepas_rawat.tanggal_rawat,surat_lepas_rawat.tanggal_pulang,surat_lepas_rawat.tanggal_control, "+
                    "surat_lepas_rawat.tempat,surat_lepas_rawat.kd_poli,poliklinik.nm_poli,surat_lepas_rawat.diagnosa,surat_lepas_rawat.terapi, "+
                    "surat_lepas_rawat.nip,petugas.nama from surat_lepas_rawat inner join reg_periksa on surat_lepas_rawat.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on surat_lepas_rawat.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on surat_lepas_rawat.kd_poli=poliklinik.kd_poli "+
                    "inner join petugas on surat_lepas_rawat.nip=petugas.nip where "+
                    "surat_lepas_rawat.tanggal_rawat between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and "+
                    "(reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or surat_lepas_rawat.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%') "+
                    "order by surat_lepas_rawat.tanggal_rawat ",param);
            }  
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed
   
                                  
    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
       isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void MnSuratLepasRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratLepasRawatActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),6).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()));  
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptSuratLepasRawat.jasper","report","::[ Surat Lepas Rawat ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,surat_lepas_rawat.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur, "+
                    "pasien.tgl_lahir,pasien.alamat,pasien.pekerjaan,surat_lepas_rawat.tanggal_rawat,surat_lepas_rawat.tanggal_pulang,surat_lepas_rawat.tanggal_control, "+
                    "surat_lepas_rawat.tempat,surat_lepas_rawat.kd_poli,poliklinik.nm_poli,surat_lepas_rawat.diagnosa,surat_lepas_rawat.terapi, "+
                    "surat_lepas_rawat.nip,petugas.nama from surat_lepas_rawat inner join reg_periksa on surat_lepas_rawat.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on surat_lepas_rawat.kd_dokter=dokter.kd_dokter "+
                    "inner join poliklinik on surat_lepas_rawat.kd_poli=poliklinik.kd_poli "+
                    "inner join petugas on surat_lepas_rawat.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnSuratLepasRawatActionPerformed

    private void TanggalPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPulangKeyPressed
        Valid.pindah(evt,TCari,TanggalPulang);
    }//GEN-LAST:event_TanggalPulangKeyPressed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,TanggalPulang,Terapi);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",NamaPetugas,NIP.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TglLahir.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Terapi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_NIPKeyPressed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        Valid.pindah(evt,Tempat,Diagnosa);
    }//GEN-LAST:event_TerapiKeyPressed

    private void TerapiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerapiActionPerformed

    }//GEN-LAST:event_TerapiActionPerformed

    private void DiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiagnosaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaActionPerformed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        Valid.pindah(evt,KdPoli,Terapi);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        penyakit.isCek();
        penyakit.emptTeks();
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        Valid.pindah(evt,KdPoli,Terapi);
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void btnDiagnosaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosaKeyReleased

    private void btnDiagnosaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosaKeyTyped

    private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        if(aktifjadwal.equals("aktif")){
            if(akses.getkode().equals("Admin Utama")){
                poli.isCek();
                poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                poli.setLocationRelativeTo(internalFrame1);
                poli.setVisible(true);
            }else{
                poli2.isCek();
                poli2.SetHari(TanggalControl.getDate());
                poli2.tampil();
                poli2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                poli2.setLocationRelativeTo(internalFrame1);
                poli2.setVisible(true);
            }
        }else{
            poli.isCek();
            poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            poli.setLocationRelativeTo(internalFrame1);
            poli.setVisible(true);
        }
    }//GEN-LAST:event_BtnPoliActionPerformed

    private void BtnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPoliActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnDokter,TanggalControl);
        }
    }//GEN-LAST:event_BtnPoliKeyPressed

    private void TanggalPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalPulangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPulangActionPerformed

    private void TanggalControlKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalControlKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalControlKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        if(aktifjadwal.equals("aktif")){
            if(akses.getkode().equals("Admin Utama")){
                dokter.isCek();
                dokter.TCari.requestFocus();
                dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setVisible(true);
            }else{
                dokter2.setPoli(NmPoli.getText());
                dokter2.isCek();
                dokter2.SetHari(TanggalControl.getDate());
                dokter2.tampil();
                dokter2.TCari.requestFocus();
                dokter2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dokter2.setLocationRelativeTo(internalFrame1);
                dokter2.setVisible(true);
            }
        }else{
            dokter.isCek();
            dokter.TCari.requestFocus();
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
            dokter.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,NIP,BtnPoli);
        }
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{
            Valid.pindah(evt,TCari,TglLahir);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TanggalRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalRawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalRawatActionPerformed

    private void TanggalRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRawatKeyPressed

    }//GEN-LAST:event_TanggalRawatKeyPressed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbObatMouseClicked

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void TempatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TempatActionPerformed

    }//GEN-LAST:event_TempatActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratLepasRawat dialog = new SuratLepasRawat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPoli;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Diagnosa;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPoli;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnSuratLepasRawat;
    private widget.TextBox NIP;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPoli;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalControl;
    private widget.Tanggal TanggalPulang;
    private widget.Tanggal TanggalRawat;
    private widget.TextBox Tempat;
    private widget.TextBox Terapi;
    private widget.TextBox TglLahir;
    private widget.Button btnDiagnosa;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,surat_lepas_rawat.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.tgl_lahir,pasien.alamat,pasien.pekerjaan,surat_lepas_rawat.tanggal_rawat,surat_lepas_rawat.tanggal_pulang,surat_lepas_rawat.tanggal_control, "+
		    "surat_lepas_rawat.tempat,surat_lepas_rawat.kd_poli,poliklinik.nm_poli,surat_lepas_rawat.diagnosa,surat_lepas_rawat.terapi, "+
                    "surat_lepas_rawat.nip,petugas.nama from surat_lepas_rawat inner join reg_periksa on surat_lepas_rawat.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
		    "inner join dokter on surat_lepas_rawat.kd_dokter=dokter.kd_dokter "+
		    "inner join poliklinik on surat_lepas_rawat.kd_poli=poliklinik.kd_poli "+
                    "inner join petugas on surat_lepas_rawat.nip=petugas.nip where "+
                    "surat_lepas_rawat.tanggal_rawat between ? and ? order by surat_lepas_rawat.tanggal_rawat");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,surat_lepas_rawat.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
            	    "pasien.tgl_lahir,pasien.alamat,pasien.pekerjaan,surat_lepas_rawat.tanggal_rawat,surat_lepas_rawat.tanggal_pulang,surat_lepas_rawat.tanggal_control, "+
		    "surat_lepas_rawat.tempat,surat_lepas_rawat.kd_poli,poliklinik.nm_poli,surat_lepas_rawat.diagnosa,surat_lepas_rawat.terapi, "+
                    "surat_lepas_rawat.nip,petugas.nama from surat_lepas_rawat inner join reg_periksa on surat_lepas_rawat.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
		    "inner join dokter on surat_lepas_rawat.kd_dokter=dokter.kd_dokter "+
		    "inner join poliklinik on surat_lepas_rawat.kd_poli=poliklinik.kd_poli "+
                    "inner join petugas on surat_lepas_rawat.nip=petugas.nip where "+
                    "surat_lepas_rawat.tanggal_rawat between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or pasien.alamat like ? or pasien.pekerjaan like ? or surat_lepas_rawat.kd_dokter like ? or dokter.nm_dokter like ? or surat_lepas_rawat.tanggal_rawat like ? or surat_lepas_rawat.tanggal_pulang like ? or surat_lepas_rawat.tanggal_control like ? or surat_lepas_rawat.tempat like ? or surat_lepas_rawat.kd_poli like ? or surat_lepas_rawat.nip like ? or petugas.nama like ?) "+
                    "order by surat_lepas_rawat.tanggal_rawat ");
            }
                
            try {
                  if(TCari.getText().toString().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                    ps.setString(11,"%"+TCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,"%"+TCari.getText()+"%");
                    ps.setString(14,"%"+TCari.getText()+"%");
                    ps.setString(15,"%"+TCari.getText()+"%");
                    ps.setString(16,"%"+TCari.getText()+"%");
                }
                  
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"), 
                        rs.getString("tgl_lahir"),rs.getString("alamat"),rs.getString("pekerjaan"),
                        rs.getString("tanggal_rawat"),rs.getString("tanggal_pulang"),rs.getString("tanggal_control"),rs.getString("tempat"),
                        rs.getString("kd_poli"),rs.getString("nm_poli"),rs.getString("diagnosa"),rs.getString("terapi"),rs.getString("nip"),rs.getString("nama") 
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TanggalRawat.setDate(new Date());
        TanggalPulang.setDate(new Date());
        TanggalControl.setDate(new Date());
        Tempat.setText("");
        Diagnosa.setText("");
        Terapi.setText("");
        Tempat.requestFocus();
    }

 
    private void getData() {
         if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()); 
            Tempat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KdPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
            Valid.SetTgl(TanggalRawat,tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Valid.SetTgl(TanggalPulang,tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Valid.SetTgl(TanggalControl,tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
       Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
       Sequel.cariIsi("select date_format(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt,String tempat, Date tgl1, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien(); 
        ChkInput.setSelected(true);
        isForm();
    }
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,186));
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
        BtnSimpan.setEnabled(akses.getsurat_pulang_atas_permintaan_sendiri());
        BtnHapus.setEnabled(akses.getsurat_pulang_atas_permintaan_sendiri());
        BtnEdit.setEnabled(akses.getsurat_pulang_atas_permintaan_sendiri());
        BtnPrint.setEnabled(akses.getsurat_pulang_atas_permintaan_sendiri()); 
        if(akses.getjml2()>=1){
            NIP.setEditable(false);
            btnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", NamaPetugas,NIP.getText());
            if(NamaPetugas.getText().equals("")){
                NIP.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }
    

   private void ganti() {
        Sequel.mengedit("surat_lepas_rawat","tanggal_rawat=? and no_rawat=?","no_rawat=?,tanggal_rawat=?,kd_dokter=?,tanggal_pulang=?,tanggal_control=?,tempat=?,kd_poli=?,diagnosa=?,terapi=?,nip=?",12,new String[]{
            TNoRw.getText(),Valid.SetTgl(TanggalRawat.getSelectedItem()+""),KdDokter.getText(),
            Valid.SetTgl(TanggalPulang.getSelectedItem()+""),Valid.SetTgl(TanggalControl.getSelectedItem()+""),Tempat.getText(),KdPoli.getText(),
            Diagnosa.getText(),Terapi.getText(),NIP.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),9).toString(),
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        });
        if(tabMode.getRowCount()!=0){tampil();}
        emptTeks();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from surat_lepas_rawat where tanggal_rawat=? and no_rawat=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),9).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tampil();
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    
}



