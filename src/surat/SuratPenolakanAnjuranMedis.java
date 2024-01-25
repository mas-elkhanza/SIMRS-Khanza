/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package surat;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPegawai;


/**
 * 
 * @author windiartohugroho
 */
public final class SuratPenolakanAnjuranMedis extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPegawai petugas=new DlgCariPegawai(null,false);
    private DlgCariMasterMenolakAnjuranMedis penolakan=new DlgCariMasterMenolakAnjuranMedis(null,false);
    private StringBuilder htmlContent;
    private String finger="",lokasifile="";
    
    public SuratPenolakanAnjuranMedis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Persetujuan","No.Rawat","No.R.M.","Nama Pasien","Umur","J.K.","Tgl.Lahir","Tanggal","Kode","Penolakan Anjuran","Alasan Penolakan",
            "Risiko Penolakan","Nama Penanggung Jawab","Umur P.J.","Nomor KTP P.J.","J.K. P.J.","Nomor Telp/HP","Hubungan","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(45);
            }else if(i==5){
                column.setPreferredWidth(25);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(40);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(45);
            }else if(i==16){
                column.setPreferredWidth(100);
            }else if(i==17){
                column.setPreferredWidth(88);
            }else if(i==18){
                column.setPreferredWidth(90);
            }else if(i==19){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));  
        NoPernyataan.setDocument(new batasInput((byte)20).getKata(NoPernyataan));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        NamaPJ.setDocument(new batasInput((byte)50).getKata(NamaPJ));
        NoKTP.setDocument(new batasInput((byte)20).getKata(NoKTP)); 
        UmurPJ.setDocument(new batasInput((byte)3).getKata(UmurPJ));  
        NoTelp.setDocument(new batasInput((byte)30).getKata(NoTelp));  
        AlasanPenolakan.setDocument(new batasInput((byte)60).getKata(AlasanPenolakan));
        RisikoPenolakan.setDocument(new batasInput((byte)100).getKata(RisikoPenolakan));  
        
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
                    KodePetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                KodePenolakan.requestFocus();
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
        
        penolakan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penolakan.getTable().getSelectedRow()!= -1){                   
                    KodePenolakan.setText(penolakan.getTable().getValueAt(penolakan.getTable().getSelectedRow(),0).toString());
                    NamaPenolakan.setText(penolakan.getTable().getValueAt(penolakan.getTable().getSelectedRow(),1).toString());
                }  
                KodePenolakan.requestFocus();
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
        
        ChkAccor.setSelected(false);
        isPhoto();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
        LoadHTML2.setDocument(doc);
    }
    
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JK = new widget.TextBox();
        Umur = new widget.TextBox();
        LoadHTML = new widget.editorpane();
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
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        NamaPJ = new widget.TextBox();
        jLabel9 = new widget.Label();
        JKPJ = new widget.ComboBox();
        jLabel10 = new widget.Label();
        Hubungan = new widget.ComboBox();
        jLabel17 = new widget.Label();
        LahirPasien = new widget.TextBox();
        jLabel44 = new widget.Label();
        UmurPJ = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel3 = new widget.Label();
        NoPernyataan = new widget.TextBox();
        jLabel15 = new widget.Label();
        NoKTP = new widget.TextBox();
        NoTelp = new widget.TextBox();
        jLabel20 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel16 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel23 = new widget.Label();
        KodePetugas = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel24 = new widget.Label();
        KodePenolakan = new widget.TextBox();
        NamaPenolakan = new widget.TextBox();
        btnPenolakan = new widget.Button();
        jLabel5 = new widget.Label();
        AlasanPenolakan = new widget.TextBox();
        jLabel11 = new widget.Label();
        RisikoPenolakan = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnPrint1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Penolakan Anjuran Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(57, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2023" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(215, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 246));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(325, 10, 255, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(212, 10, 111, 23);

        jLabel8.setText("Hubungan Dengan Pasien :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(459, 190, 160, 23);

        NamaPJ.setName("NamaPJ"); // NOI18N
        NamaPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPJKeyPressed(evt);
            }
        });
        FormInput.add(NamaPJ);
        NamaPJ.setBounds(79, 160, 260, 23);

        jLabel9.setText("J.K. :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 190, 75, 23);

        JKPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki-laki", "Perempuan" }));
        JKPJ.setName("JKPJ"); // NOI18N
        JKPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKPJKeyPressed(evt);
            }
        });
        FormInput.add(JKPJ);
        JKPJ.setBounds(79, 190, 110, 23);

        jLabel10.setText("Nama :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 160, 75, 23);

        Hubungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Suami", "Istri", "Anak", "Ayah", "Saudara", "Keponakan" }));
        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(623, 190, 110, 23);

        jLabel17.setText("Tgl.Lahir :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(584, 10, 60, 23);

        LahirPasien.setHighlighter(null);
        LahirPasien.setName("LahirPasien"); // NOI18N
        FormInput.add(LahirPasien);
        LahirPasien.setBounds(648, 10, 85, 23);

        jLabel44.setText("Umur (Tahun) :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(353, 160, 90, 23);

        UmurPJ.setName("UmurPJ"); // NOI18N
        UmurPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurPJKeyPressed(evt);
            }
        });
        FormInput.add(UmurPJ);
        UmurPJ.setBounds(447, 160, 47, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Penanggung Jawab Pasien :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(16, 140, 152, 23);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Nomor ");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(16, 80, 50, 23);

        NoPernyataan.setHighlighter(null);
        NoPernyataan.setName("NoPernyataan"); // NOI18N
        NoPernyataan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPernyataanKeyPressed(evt);
            }
        });
        FormInput.add(NoPernyataan);
        NoPernyataan.setBounds(59, 80, 136, 23);

        jLabel15.setText("Nomor KTP :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(509, 160, 70, 23);

        NoKTP.setName("NoKTP"); // NOI18N
        NoKTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKTPKeyPressed(evt);
            }
        });
        FormInput.add(NoKTP);
        NoKTP.setBounds(583, 160, 150, 23);

        NoTelp.setName("NoTelp"); // NOI18N
        NoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTelpKeyPressed(evt);
            }
        });
        FormInput.add(NoTelp);
        NoTelp.setBounds(300, 190, 160, 23);

        jLabel20.setText("Nomor Telp/HP :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(200, 190, 96, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 752, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 752, 1);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 70, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2023 18:57:02" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 40, 130, 23);

        jLabel23.setText("Staf/Dokter/Petugas :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(211, 40, 140, 23);

        KodePetugas.setEditable(false);
        KodePetugas.setHighlighter(null);
        KodePetugas.setName("KodePetugas"); // NOI18N
        FormInput.add(KodePetugas);
        KodePetugas.setBounds(355, 40, 127, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(484, 40, 219, 23);

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
        btnPetugas.setBounds(705, 40, 28, 23);

        jLabel24.setText("Penolakan Anjuran :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(211, 80, 140, 23);

        KodePenolakan.setEditable(false);
        KodePenolakan.setHighlighter(null);
        KodePenolakan.setName("KodePenolakan"); // NOI18N
        FormInput.add(KodePenolakan);
        KodePenolakan.setBounds(355, 80, 56, 23);

        NamaPenolakan.setEditable(false);
        NamaPenolakan.setName("NamaPenolakan"); // NOI18N
        FormInput.add(NamaPenolakan);
        NamaPenolakan.setBounds(413, 80, 290, 23);

        btnPenolakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenolakan.setMnemonic('2');
        btnPenolakan.setToolTipText("ALt+2");
        btnPenolakan.setName("btnPenolakan"); // NOI18N
        btnPenolakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenolakanActionPerformed(evt);
            }
        });
        btnPenolakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPenolakanKeyPressed(evt);
            }
        });
        FormInput.add(btnPenolakan);
        btnPenolakan.setBounds(705, 80, 28, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 110, 109, 23);

        AlasanPenolakan.setHighlighter(null);
        AlasanPenolakan.setName("AlasanPenolakan"); // NOI18N
        AlasanPenolakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanPenolakanKeyPressed(evt);
            }
        });
        FormInput.add(AlasanPenolakan);
        AlasanPenolakan.setBounds(113, 110, 245, 23);

        jLabel11.setText("Risiko Penolakan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(374, 110, 110, 23);

        RisikoPenolakan.setHighlighter(null);
        RisikoPenolakan.setName("RisikoPenolakan"); // NOI18N
        RisikoPenolakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RisikoPenolakanKeyPressed(evt);
            }
        });
        FormInput.add(RisikoPenolakan);
        RisikoPenolakan.setBounds(488, 110, 245, 23);

        jLabel12.setText(":");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 80, 55, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Alasan Penolakan");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(16, 110, 110, 23);

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

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(430, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Pengambilan Penolakan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Surat");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnPrint1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NamaPJ.getText().trim().equals("")){
            Valid.textKosong(NamaPJ,"Nama Penanggung Jawab");
        }else if(UmurPJ.getText().trim().equals("")){
            Valid.textKosong(UmurPJ,"Umur");
        }else if(NoTelp.getText().trim().equals("")){
            Valid.textKosong(NoTelp,"Nomor Telp");
        }else if(NoKTP.getText().trim().equals("")){
            Valid.textKosong(NoKTP,"Nomor KTP");
        }else if(NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Petugas");
        }else if(NamaPenolakan.getText().trim().equals("")){
            Valid.textKosong(btnPenolakan,"Penolakan Anjuran");
        }else if(NoPernyataan.getText().trim().equals("")){
            Valid.textKosong(NoPernyataan,"No.Penolakan");
        }else if(AlasanPenolakan.getText().trim().equals("")){
            Valid.textKosong(AlasanPenolakan,"Alasan Penolakan");
        }else if(RisikoPenolakan.getText().trim().equals("")){
            Valid.textKosong(RisikoPenolakan,"Risiko Penolakan");
        }else{
            if(Sequel.menyimpantf("surat_penolakan_anjuran_medis","?,?,?,?,?,?,?,?,?,?,?,?,?","Data",13,new String[]{
                    NoPernyataan.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    Hubungan.getSelectedItem().toString(),NamaPJ.getText(),UmurPJ.getText(),NoKTP.getText(),JKPJ.getSelectedItem().toString().substring(0,1),
                    NoTelp.getText(),KodePenolakan.getText(),AlasanPenolakan.getText(),RisikoPenolakan.getText(),KodePetugas.getText()
                })==true){
                tabMode.addRow(new String[]{
                    NoPernyataan.getText(),TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),JK.getText(),LahirPasien.getText(),
                    Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),KodePenolakan.getText(),
                    NamaPenolakan.getText(),AlasanPenolakan.getText(),RisikoPenolakan.getText(),NamaPJ.getText(),UmurPJ.getText(),NoKTP.getText(),
                    JKPJ.getSelectedItem().toString().substring(0,1),NoTelp.getText(),Hubungan.getSelectedItem().toString(),KodePetugas.getText(),NamaPetugas.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,JKPJ,BtnBatal);
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
                if(KodePetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString())){
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
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NamaPJ.getText().trim().equals("")){
            Valid.textKosong(NamaPJ,"Nama Penanggung Jawab");
        }else if(UmurPJ.getText().trim().equals("")){
            Valid.textKosong(UmurPJ,"Umur");
        }else if(NoTelp.getText().trim().equals("")){
            Valid.textKosong(NoTelp,"Nomor Telp");
        }else if(NoKTP.getText().trim().equals("")){
            Valid.textKosong(NoKTP,"Nomor KTP");
        }else if(NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Petugas");
        }else if(NamaPenolakan.getText().trim().equals("")){
            Valid.textKosong(btnPenolakan,"Penolakan Anjuran");
        }else if(NoPernyataan.getText().trim().equals("")){
            Valid.textKosong(NoPernyataan,"No.Penolakan");
        }else if(AlasanPenolakan.getText().trim().equals("")){
            Valid.textKosong(AlasanPenolakan,"Alasan Penolakan");
        }else if(RisikoPenolakan.getText().trim().equals("")){
            Valid.textKosong(RisikoPenolakan,"Risiko Penolakan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KodePetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString())){
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
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Persetujuan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.R.M.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penolakan Anjuran</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alasan Penolakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Risiko Penolakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Penanggung Jawab</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Umur P.J.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nomor KTP P.J.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K. P.J.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nomor Telp/HP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
                    "</tr>"
                );
                
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                           "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                );
                bg.close();

                File f = new File("DataPenolakanAnjuranMedis.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENOLAKAN ANJURAN MEDIS<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());

            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
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
   
                                  
    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            try {
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
       isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

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

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,NoTelp,BtnSimpan);
    }//GEN-LAST:event_HubunganKeyPressed

    private void JKPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKPJKeyPressed
        Valid.pindah(evt,NoKTP,NoTelp);
    }//GEN-LAST:event_JKPJKeyPressed

    private void NamaPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPJKeyPressed
        Valid.pindah(evt,RisikoPenolakan,UmurPJ);
    }//GEN-LAST:event_NamaPJKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,Tanggal);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void NoPernyataanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPernyataanKeyPressed
        Valid.pindah(evt,btnPetugas,btnPenolakan);
    }//GEN-LAST:event_NoPernyataanKeyPressed

    private void NoKTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKTPKeyPressed
        Valid.pindah(evt,UmurPJ,JKPJ);
    }//GEN-LAST:event_NoKTPKeyPressed

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed
        Valid.pindah(evt,JKPJ,Hubungan);
    }//GEN-LAST:event_NoTelpKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                Sequel.queryu("delete from antripenolakananjuranmedis");
                Sequel.queryu("insert into antripenolakananjuranmedis values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"','"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+"')");
                Sequel.queryu("delete from surat_penolakan_anjuran_medis_pembuat_pernyataan where no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void UmurPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurPJKeyPressed
        Valid.pindah(evt,NamaPJ,NoKTP);
    }//GEN-LAST:event_UmurPJKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,btnPetugas);
    }//GEN-LAST:event_TanggalKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Tanggal,NoPernyataan);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPenolakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenolakanActionPerformed
        penolakan.emptTeks();
        penolakan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penolakan.setLocationRelativeTo(internalFrame1);
        penolakan.setVisible(true);
    }//GEN-LAST:event_btnPenolakanActionPerformed

    private void btnPenolakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPenolakanKeyPressed
        Valid.pindah(evt,NoPernyataan,AlasanPenolakan);
    }//GEN-LAST:event_btnPenolakanKeyPressed

    private void AlasanPenolakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanPenolakanKeyPressed
        Valid.pindah(evt,btnPenolakan,RisikoPenolakan);
    }//GEN-LAST:event_AlasanPenolakanKeyPressed

    private void RisikoPenolakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RisikoPenolakanKeyPressed
        Valid.pindah(evt,AlasanPenolakan,NamaPJ);
    }//GEN-LAST:event_RisikoPenolakanKeyPressed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(lokasifile.equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan ambil photo bukti penolakan anjuran medis terlebih dahulu..!!!!");
            }else{
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                param.put("photo","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penolakananjuranmedis/"+lokasifile);
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),19).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),18).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())+" "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,19));
                Valid.MyReportqry("rptSuratPenolakanAnjuranMedis.jasper","report","::[ Surat Penolakan Anjuran Medis ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pasien.pekerjaan,pasien.tmp_lahir,reg_periksa.umurdaftar,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.sttsumur,"+
                    "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat_pasien,surat_penolakan_anjuran_medis.no_surat,surat_penolakan_anjuran_medis.tanggal,surat_penolakan_anjuran_medis.kode_penolakan,"+
                    "master_menolak_anjuran_medis.nama_penolakan,surat_penolakan_anjuran_medis.alasan_penolakan,surat_penolakan_anjuran_medis.informasi_risiko_penolakan,surat_penolakan_anjuran_medis.nama_pj,surat_penolakan_anjuran_medis.umur_pj,surat_penolakan_anjuran_medis.no_ktppj,"+
                    "surat_penolakan_anjuran_medis.jkpj,surat_penolakan_anjuran_medis.hubungan,surat_penolakan_anjuran_medis.no_telp,surat_penolakan_anjuran_medis.nik,pegawai.nama from surat_penolakan_anjuran_medis inner join reg_periksa on surat_penolakan_anjuran_medis.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join master_menolak_anjuran_medis on master_menolak_anjuran_medis.kode_penolakan=surat_penolakan_anjuran_medis.kode_penolakan "+
                    "inner join pegawai on surat_penolakan_anjuran_medis.nik=pegawai.nik inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "inner join propinsi on pasien.kd_prop=propinsi.kd_prop where surat_penolakan_anjuran_medis.no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratPenolakanAnjuranMedis dialog = new SuratPenolakanAnjuranMedis(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlasanPenolakan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox Hubungan;
    private widget.TextBox JK;
    private widget.ComboBox JKPJ;
    private widget.TextBox KodePenolakan;
    private widget.TextBox KodePetugas;
    private widget.Label LCount;
    private widget.TextBox LahirPasien;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.TextBox NamaPJ;
    private widget.TextBox NamaPenolakan;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NoKTP;
    private widget.TextBox NoPernyataan;
    private widget.TextBox NoTelp;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox RisikoPenolakan;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox Umur;
    private widget.TextBox UmurPJ;
    private widget.Button btnAmbil;
    private widget.Button btnPenolakan;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel44;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select surat_penolakan_anjuran_medis.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_penolakan_anjuran_medis.tanggal,surat_penolakan_anjuran_medis.kode_penolakan,"+
                    "master_menolak_anjuran_medis.nama_penolakan,surat_penolakan_anjuran_medis.alasan_penolakan,surat_penolakan_anjuran_medis.informasi_risiko_penolakan,"+
                    "surat_penolakan_anjuran_medis.nama_pj,surat_penolakan_anjuran_medis.umur_pj,surat_penolakan_anjuran_medis.no_ktppj,"+
                    "surat_penolakan_anjuran_medis.jkpj,surat_penolakan_anjuran_medis.hubungan,surat_penolakan_anjuran_medis.no_telp,surat_penolakan_anjuran_medis.nik,"+
                    "pegawai.nama from surat_penolakan_anjuran_medis inner join reg_periksa on surat_penolakan_anjuran_medis.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join master_menolak_anjuran_medis on master_menolak_anjuran_medis.kode_penolakan=surat_penolakan_anjuran_medis.kode_penolakan "+
                    "inner join pegawai on surat_penolakan_anjuran_medis.nik=pegawai.nik where "+
                    "surat_penolakan_anjuran_medis.tanggal between ? and ? order by surat_penolakan_anjuran_medis.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select surat_penolakan_anjuran_medis.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,surat_penolakan_anjuran_medis.tanggal,surat_penolakan_anjuran_medis.kode_penolakan,"+
                    "master_menolak_anjuran_medis.nama_penolakan,surat_penolakan_anjuran_medis.alasan_penolakan,surat_penolakan_anjuran_medis.informasi_risiko_penolakan,"+
                    "surat_penolakan_anjuran_medis.nama_pj,surat_penolakan_anjuran_medis.umur_pj,surat_penolakan_anjuran_medis.no_ktppj,"+
                    "surat_penolakan_anjuran_medis.jkpj,surat_penolakan_anjuran_medis.hubungan,surat_penolakan_anjuran_medis.no_telp,surat_penolakan_anjuran_medis.nik,"+
                    "pegawai.nama from surat_penolakan_anjuran_medis inner join reg_periksa on surat_penolakan_anjuran_medis.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join master_menolak_anjuran_medis on master_menolak_anjuran_medis.kode_penolakan=surat_penolakan_anjuran_medis.kode_penolakan "+
                    "inner join pegawai on surat_penolakan_anjuran_medis.nik=pegawai.nik where "+
                    "surat_penolakan_anjuran_medis.tanggal between ? and ? and "+
                    "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                    "surat_penolakan_anjuran_medis.no_telp like ? or surat_penolakan_anjuran_medis.nama_pj like ? or "+
                    "surat_penolakan_anjuran_medis.nik like ? or pegawai.nama like ? or master_menolak_anjuran_medis.nama_penolakan like ?) "+
                    "order by surat_penolakan_anjuran_medis.tanggal");
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
                }
                  
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_surat"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),rs.getString("tgl_lahir"),
                        rs.getString("tanggal"),rs.getString("kode_penolakan"),rs.getString("nama_penolakan"),rs.getString("alasan_penolakan"),
                        rs.getString("informasi_risiko_penolakan"),rs.getString("nama_pj"),rs.getString("umur_pj"),rs.getString("no_ktppj"),
                        rs.getString("jkpj"),rs.getString("no_telp"),rs.getString("hubungan"),rs.getString("nik"),rs.getString("nama") 
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
        NamaPJ.setText("");
        UmurPJ.setText("");
        Tanggal.setDate(new Date());
        KodePenolakan.setText("");
        NamaPenolakan.setText("");
        AlasanPenolakan.setText("");
        RisikoPenolakan.setText("");
        Hubungan.setSelectedIndex(0);
        JKPJ.setSelectedIndex(0);
        NoTelp.setText("");
        NoKTP.setText("");
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(surat_penolakan_anjuran_medis.no_surat,3),signed)),0) from surat_penolakan_anjuran_medis where surat_penolakan_anjuran_medis.tanggal like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"%' ",
                "PAM"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoPernyataan);
        AlasanPenolakan.requestFocus();
    }

 
    private void getData() {
         if(tbObat.getSelectedRow()!= -1){
            NoPernyataan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            LahirPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            KodePenolakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString()); 
            NamaPenolakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()); 
            AlasanPenolakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString()); 
            RisikoPenolakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
            NamaPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            UmurPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());  
            NoKTP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            JKPJ.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString().replaceAll("L","Laki-laki").replaceAll("P","Perempuan")); 
            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString()); 
            Hubungan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());  
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.umurdaftar,reg_periksa.sttsumur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    LahirPasien.setText(rs.getString("tgl_lahir"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
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
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,246));
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
        BtnSimpan.setEnabled(akses.getpenolakan_anjuran_medis());
        BtnHapus.setEnabled(akses.getpenolakan_anjuran_medis());
        BtnEdit.setEnabled(akses.getpenolakan_anjuran_medis());
        BtnPrint.setEnabled(akses.getpenolakan_anjuran_medis()); 
        if(akses.getjml2()>=1){
            KodePetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KodePetugas.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(KodePetugas.getText()));
        }            
    }
  
    private void ganti() {
        if(Sequel.mengedittf("surat_penolakan_anjuran_medis","no_surat=?","no_surat=?,no_rawat=?,tanggal=?,hubungan=?,nama_pj=?,umur_pj=?,no_ktppj=?,jkpj=?,no_telp=?,kode_penolakan=?,"+
            "alasan_penolakan=?,informasi_risiko_penolakan=?,nik=?",14,new String[]{
            NoPernyataan.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
            Hubungan.getSelectedItem().toString(),NamaPJ.getText(),UmurPJ.getText(),NoKTP.getText(),JKPJ.getSelectedItem().toString().substring(0,1),NoTelp.getText(),
            KodePenolakan.getText(),AlasanPenolakan.getText(),RisikoPenolakan.getText(),KodePetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tbObat.setValueAt(NoPernyataan.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(LahirPasien.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(KodePenolakan.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NamaPenolakan.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(AlasanPenolakan.getText(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(RisikoPenolakan.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(NamaPJ.getText(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(UmurPJ.getText(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(NoKTP.getText(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(JKPJ.getSelectedItem().toString().substring(0,1),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(NoTelp.getText(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(Hubungan.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),19);
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from surat_penolakan_anjuran_medis where no_surat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(480,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            lokasifile="";
            try {
                ps=koneksi.prepareStatement("select surat_penolakan_anjuran_medis_pembuat_pernyataan.photo from surat_penolakan_anjuran_medis_pembuat_pernyataan where surat_penolakan_anjuran_medis_pembuat_pernyataan.no_surat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            lokasifile="";
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            lokasifile=rs.getString("photo");
                            LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penolakananjuranmedis/"+rs.getString("photo")+"' alt='photo' width='500' height='500'/></center></body></html>");
                        }  
                    }else{
                        lokasifile="";
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                    }
                } catch (Exception e) {
                    lokasifile="";
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }
    }
}



