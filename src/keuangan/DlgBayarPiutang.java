/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package keuangan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariPasien;

/**
 *
 * @author dosen
 */
public final class DlgBayarPiutang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();    
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private double cicilan=0,sisapiutang=0,diskon=0,tidakterbayar=0;
    private PreparedStatement ps;
    private ResultSet rs;
    private String koderekening="",kontraakun="",status="",Diskon_Piutang="",Piutang_Tidak_Terbayar="";
    private boolean sukses=true;
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;
    private int i=0;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgBayarPiutang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "Tgl.Bayar","No.RM","Pasien","Cicilan(Rp)","Keterangan","No.Tagihan","Kode Akun","Kontra AKun",
                "Diskon(Rp)","Kode Akun Diskon","Tidak Terbayar(Rp)","Kode Akun Tidak Terbayar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class,
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(120);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoRawat.setDocument(new batasInput((byte)17).getKata(NoRawat));
        Cicilan.setDocument(new batasInput((byte)15).getOnlyAngka(Cicilan));
        DiskonBayar.setDocument(new batasInput((byte)15).getOnlyAngka(DiskonBayar));
        TidakTerbayar.setDocument(new batasInput((byte)15).getOnlyAngka(TidakTerbayar));
        Keterangan.setDocument(new batasInput((byte)100).getKata(Keterangan));
        Kdmem.setDocument(new batasInput((byte)15).getKata(Kdmem));
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
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
        
        Cicilan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                Sisa.setText(Valid.SetAngka(sisapiutang));
                if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                     Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                Sisa.setText(Valid.SetAngka(sisapiutang));
                if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                     Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                Sisa.setText(Valid.SetAngka(sisapiutang));
                if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                     Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                }
            }
        });
        
        DiskonBayar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                Sisa.setText(Valid.SetAngka(sisapiutang));
                if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                     Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                Sisa.setText(Valid.SetAngka(sisapiutang));
                if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                     Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                Sisa.setText(Valid.SetAngka(sisapiutang));
                if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                     Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                }
            }
        });
        
        TidakTerbayar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                Sisa.setText(Valid.SetAngka(sisapiutang));
                if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                     Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                Sisa.setText(Valid.SetAngka(sisapiutang));
                if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                     Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                Sisa.setText(Valid.SetAngka(sisapiutang));
                if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                     Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                }
            }
        });
        
        ChkInput.setSelected(false);
        isForm();
        
        try {
            ps=koneksi.prepareStatement(
                    "select set_akun.Diskon_Piutang,set_akun.Piutang_Tidak_Terbayar from set_akun");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    Diskon_Piutang=rs.getString("Diskon_Piutang");
                    Piutang_Tidak_Terbayar=rs.getString("Piutang_Tidak_Terbayar");
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


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppNotaPiutang = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        LCount = new widget.Label();
        label12 = new widget.Label();
        LTotal = new widget.Label();
        label13 = new widget.Label();
        LDiskon = new widget.Label();
        label14 = new widget.Label();
        LTTerbayar = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label15 = new widget.Label();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        NoRawat = new widget.TextBox();
        label36 = new widget.Label();
        Keterangan = new widget.TextBox();
        label35 = new widget.Label();
        Cicilan = new widget.TextBox();
        label16 = new widget.Label();
        Kdmem = new widget.TextBox();
        Nmmem = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        label38 = new widget.Label();
        Sisa = new widget.TextBox();
        BtnSeek = new widget.Button();
        jLabel10 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        jLabel11 = new widget.Label();
        AkunPiutang = new widget.ComboBox();
        BtnCari1 = new widget.Button();
        BtnAll1 = new widget.Button();
        label39 = new widget.Label();
        DiskonBayar = new widget.TextBox();
        label37 = new widget.Label();
        TidakTerbayar = new widget.TextBox();

        Popup.setName("Popup"); // NOI18N

        ppNotaPiutang.setBackground(new java.awt.Color(255, 255, 254));
        ppNotaPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppNotaPiutang.setForeground(new java.awt.Color(50, 50, 50));
        ppNotaPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppNotaPiutang.setText("Nota Bayar Piutang");
        ppNotaPiutang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppNotaPiutang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppNotaPiutang.setName("ppNotaPiutang"); // NOI18N
        ppNotaPiutang.setPreferredSize(new java.awt.Dimension(160, 25));
        ppNotaPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppNotaPiutangBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppNotaPiutang);

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Bayar Piutang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setComponentPopupMenu(Popup);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(27, 23));
        panelisi3.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(Tgl2);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi3.add(LCount);

        label12.setText("Total :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi3.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi3.add(LTotal);

        label13.setText("Diskon :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi3.add(label13);

        LDiskon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LDiskon.setText("0");
        LDiskon.setName("LDiskon"); // NOI18N
        LDiskon.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi3.add(LDiskon);

        label14.setText("T.Terbayar :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label14);

        LTTerbayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTTerbayar.setText("0");
        LTTerbayar.setName("LTTerbayar"); // NOI18N
        LTTerbayar.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi3.add(LTTerbayar);

        jPanel1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnSimpan);

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
        panelisi1.add(BtnHapus);

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
        panelisi1.add(BtnPrint);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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
        panelisi1.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('1');
        BtnAll.setToolTipText("Alt+1");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi1.add(BtnAll);

        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(20, 23));
        panelisi1.add(label15);

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
        panelisi1.add(BtnKeluar);

        jPanel1.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 154));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 194));
        FormInput.setLayout(null);

        label34.setText("No.Tagihan :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label34);
        label34.setBounds(0, 10, 85, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label32);
        label32.setBounds(0, 40, 85, 23);

        NoRawat.setHighlighter(null);
        NoRawat.setName("NoRawat"); // NOI18N
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });
        FormInput.add(NoRawat);
        NoRawat.setBounds(89, 10, 208, 23);

        label36.setText("Keterangan :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label36);
        label36.setBounds(0, 100, 85, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(89, 100, 270, 23);

        label35.setText("Cicilan :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label35);
        label35.setBounds(400, 40, 100, 23);

        Cicilan.setHighlighter(null);
        Cicilan.setName("Cicilan"); // NOI18N
        Cicilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CicilanKeyPressed(evt);
            }
        });
        FormInput.add(Cicilan);
        Cicilan.setBounds(504, 40, 120, 23);

        label16.setText("Pasien :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(400, 10, 100, 23);

        Kdmem.setName("Kdmem"); // NOI18N
        Kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        Kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdmemKeyPressed(evt);
            }
        });
        FormInput.add(Kdmem);
        Kdmem.setBounds(504, 10, 90, 23);

        Nmmem.setEditable(false);
        Nmmem.setName("Nmmem"); // NOI18N
        Nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(Nmmem);
        Nmmem.setBounds(596, 10, 204, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(89, 40, 110, 23);

        label38.setText("Sisa Piutang :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label38);
        label38.setBounds(630, 70, 77, 23);

        Sisa.setHighlighter(null);
        Sisa.setName("Sisa"); // NOI18N
        Sisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SisaKeyPressed(evt);
            }
        });
        FormInput.add(Sisa);
        Sisa.setBounds(710, 70, 120, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("ALt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek);
        BtnSeek.setBounds(802, 10, 28, 23);

        jLabel10.setText("Akun Bayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(400, 100, 100, 23);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        FormInput.add(AkunBayar);
        AkunBayar.setBounds(504, 100, 296, 23);

        jLabel11.setText("Akun Piutang :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 70, 85, 23);

        AkunPiutang.setName("AkunPiutang"); // NOI18N
        AkunPiutang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                AkunPiutangItemStateChanged(evt);
            }
        });
        AkunPiutang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunPiutangKeyPressed(evt);
            }
        });
        FormInput.add(AkunPiutang);
        AkunPiutang.setBounds(89, 70, 270, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        FormInput.add(BtnCari1);
        BtnCari1.setBounds(300, 10, 28, 23);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnAll1.setMnemonic('M');
        BtnAll1.setToolTipText("Alt+M");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnAll1);
        BtnAll1.setBounds(802, 100, 28, 23);

        label39.setText("Diskon Bayar :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label39);
        label39.setBounds(630, 40, 77, 23);

        DiskonBayar.setHighlighter(null);
        DiskonBayar.setName("DiskonBayar"); // NOI18N
        DiskonBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiskonBayarKeyPressed(evt);
            }
        });
        FormInput.add(DiskonBayar);
        DiskonBayar.setBounds(710, 40, 120, 23);

        label37.setText("Tak Terbayar :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label37);
        label37.setBounds(400, 70, 100, 23);

        TidakTerbayar.setHighlighter(null);
        TidakTerbayar.setName("TidakTerbayar"); // NOI18N
        TidakTerbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TidakTerbayarKeyPressed(evt);
            }
        });
        FormInput.add(TidakTerbayar);
        TidakTerbayar.setBounds(504, 70, 120, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        Valid.pindah(evt,TCari,NoRawat);
}//GEN-LAST:event_NoRawatKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRawat.getText().trim().equals("")){
            Valid.textKosong(NoRawat,"No.Tagihan/No.Rawat");
        }else if(Cicilan.getText().trim().equals("")||Cicilan.getText().trim().equals("0")){
            Valid.textKosong(Cicilan,"Besar Cicilan");
        }else if(Nmmem.getText().trim().equals("")){
            Valid.textKosong(Kdmem,"Member");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else if(AkunBayar.getSelectedItem()==null){
            Valid.textKosong(AkunBayar,"Akun Bayar");
        }else if(AkunPiutang.getSelectedItem()==null){
            Valid.textKosong(AkunPiutang,"Akun Piutang");
        }else{            
            sisapiutang=(Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang_pasien.sisapiutang),0) FROM piutang_pasien where piutang_pasien.no_rawat=?",NoRawat.getText())
                         +Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang.sisapiutang),0) FROM piutang where piutang.nota_piutang=?",NoRawat.getText())
                         -Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",NoRawat.getText())
                         -Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText()));
            koderekening="";
            try {
                myObj = new FileReader("./cache/akunbayar.iyem");
                root = mapper.readTree(myObj);
                response = root.path("akunbayar");
                if(response.isArray()){
                   for(JsonNode list:response){
                       if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                            koderekening=list.path("KodeRek").asText();  
                       }
                   }
                }
                myObj.close();
            } catch (Exception e) {
                sukses=false;
            }
            Sequel.AutoComitFalse();
            sukses=true;
            if(Sequel.menyimpantf("bayar_piutang","?,?,?,?,?,?,?,?,?,?,?","Pembayaran",11,new String[]{
                    Valid.SetTgl(Tanggal.getSelectedItem()+""),Kdmem.getText(),Cicilan.getText(),
                    Keterangan.getText(),NoRawat.getText(),koderekening,kontraakun,DiskonBayar.getText(),
                    Diskon_Piutang,TidakTerbayar.getText(),Piutang_Tidak_Terbayar
                })==true){
                    if(sisapiutang<=1){
                        Sequel.mengedit("piutang_pasien","no_rawat='"+NoRawat.getText()+"'","status='Lunas'");
                    }   
                    Sequel.mengedit("detail_piutang_pasien","no_rawat='"+NoRawat.getText()+"' and nama_bayar='"+AkunPiutang.getSelectedItem().toString()+"'","sisapiutang=sisapiutang-"+(Double.parseDouble(Cicilan.getText())+Double.parseDouble(DiskonBayar.getText())+Double.parseDouble(TidakTerbayar.getText())));
                    Sequel.queryu("delete from tampjurnal");                    
                    if(Sequel.menyimpantf2("tampjurnal","'"+kontraakun+"','BAYAR PIUTANG','0','"+(Double.parseDouble(Cicilan.getText())+Double.parseDouble(DiskonBayar.getText())+Double.parseDouble(TidakTerbayar.getText()))+"'","Rekening")==false){
                        sukses=false;
                    }    
                    if(Double.parseDouble(Cicilan.getText())>0){
                        if(Sequel.menyimpantf2("tampjurnal","'"+koderekening+"','"+AkunBayar.getSelectedItem()+"','"+Cicilan.getText()+"','0'","Rekening")==false){
                            sukses=false;
                        } 
                    }
                    if(Double.parseDouble(DiskonBayar.getText())>0){
                        if(Sequel.menyimpantf2("tampjurnal","'"+Diskon_Piutang+"','Diskon Piutang Belum Lunas','"+DiskonBayar.getText()+"','0'","Rekening")==false){
                            sukses=false;
                        }  
                    }
                    if(Double.parseDouble(TidakTerbayar.getText())>0){
                        if(Sequel.menyimpantf2("tampjurnal","'"+Piutang_Tidak_Terbayar+"','Kerugian/Piutang Tidak Terbayar','"+TidakTerbayar.getText()+"','0'","Rekening")==false){
                            sukses=false;
                        }  
                    }   
                    if(sukses==true){
                        sukses=jur.simpanJurnal(NoRawat.getText(),"U","BAYAR PIUTANG"+", OLEH "+akses.getkode());
                    }                   
            }else{
                sukses=false;
            }  
            
            if(sukses==true){
                Sequel.Commit();
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();
            
            if(sukses==true){
                tabMode.addRow(new Object[]{
                    Valid.SetTgl(Tanggal.getSelectedItem()+""),Kdmem.getText(),Nmmem.getText(),Valid.SetAngka(Cicilan.getText()),
                    Keterangan.getText(),NoRawat.getText(),koderekening,kontraakun,Valid.SetAngka(DiskonBayar.getText()),
                    Diskon_Piutang,Valid.SetAngka(TidakTerbayar.getText()),Piutang_Tidak_Terbayar
                });
                totalpiutang();
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Sisa,BtnKeluar);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(NoRawat.getText().trim().equals("")){
            Valid.textKosong(NoRawat,"No.Tagihan/No.Rawat");
        }else if(Cicilan.getText().trim().equals("")||Cicilan.getText().trim().equals("0")){
            Valid.textKosong(Cicilan,"Besar Cicilan");
        }else if(Nmmem.getText().trim().equals("")){
            Valid.textKosong(Kdmem,"Member");
        }else{  
            if(tbKamar.getSelectedRow()>-1){
                Sequel.AutoComitFalse();
                sukses=true;
                
                if(Sequel.queryu2tf("delete from bayar_piutang where tgl_bayar=? and no_rkm_medis=? and no_rawat=? and kd_rek=? and kd_rek_kontra=?", 5,new String[]{
                    tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString(),
                    tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString(),tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()
                })==true){
                    Sequel.mengedit("piutang_pasien","no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()+"'","status='Belum Lunas'");                      
                    Sequel.mengedit("detail_piutang_pasien","no_rawat='"+tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString()+"' and nama_bayar='"+Sequel.cariIsi("select akun_piutang.nama_bayar from akun_piutang where akun_piutang.kd_rek=?",tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString())+"'","sisapiutang=sisapiutang+"+(Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString())+Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString())+Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString())));
                    Sequel.queryu("delete from tampjurnal");                    
                    if(Sequel.menyimpantf2("tampjurnal","'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString()+"','BAYAR PIUTANG','"+(Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString())+Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString())+Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString()))+"','0'","Rekening")==false){
                        sukses=false;
                    } 
                    if(Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString())>0){
                        if(Sequel.menyimpantf2("tampjurnal","'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),9).toString()+"','DISKON BAYAR','0','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString()+"'","Rekening")==false){
                            sukses=false;
                        } 
                    }
                    if(Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString())>0){
                        if(Sequel.menyimpantf2("tampjurnal","'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),11).toString()+"','TIDAK TERBAYAR','0','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),10).toString()+"'","Rekening")==false){
                            sukses=false;
                        } 
                    }   
                    if(Double.parseDouble(tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString())>0){
                        if(Sequel.menyimpantf2("tampjurnal","'"+tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString()+"','Kontra Akun','0','"+tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString()+"'","Rekening")==false){
                            sukses=false;
                        } 
                    }
                    if(sukses==true){
                        sukses=jur.simpanJurnal(NoRawat.getText(),"U","PEMBATALAN BAYAR PIUTANG"+", OLEH "+akses.getkode());
                    }     
                }else{
                    sukses=false;
                }
             
                if(sukses==true){
                    Sequel.Commit();
                }else{
                    sukses=false;
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
                
                if(sukses==true){
                    tabMode.removeRow(tbKamar.getSelectedRow());
                    totalpiutang();
                    emptTeks();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!");
            }   
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnKeluar, BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnKeluar.requestFocus();
        }else if(tabMode.getRowCount()!=0){     
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());        
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));   
            Valid.MyReportqry("rptBayar.jasper","report","::[ Bayar Piutang ]::","select bayar_piutang.tgl_bayar, bayar_piutang.no_rkm_medis,pasien.nm_pasien, bayar_piutang.besar_cicilan,"+
                "bayar_piutang.catatan, bayar_piutang.no_rawat,bayar_piutang.diskon_piutang,bayar_piutang.tidak_terbayar "+
                "from bayar_piutang inner join pasien on bayar_piutang.no_rkm_medis=pasien.no_rkm_medis where "+
                "bayar_piutang.tgl_bayar between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and "+
                "(bayar_piutang.no_rawat like '%"+TCari.getText()+"%' or bayar_piutang.no_rkm_medis like '%"+TCari.getText()+"%' or pasien.nm_pasien like '%"+TCari.getText()+"%') "+
                "order by bayar_piutang.tgl_bayar,bayar_piutang.no_rkm_medis,bayar_piutang.no_rawat ",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbKamarKeyPressed

private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
   Valid.pindah(evt,Cicilan,Kdmem);
}//GEN-LAST:event_KeteranganKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,NoRawat,Kdmem);
    }//GEN-LAST:event_TanggalKeyPressed

    private void CicilanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CicilanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
            DiskonBayar.requestFocus();
        }
    }//GEN-LAST:event_CicilanKeyPressed

    private void KdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdmemKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", Nmmem,Kdmem.getText());
            sisapiutang=Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang_pasien.sisapiutang),0) FROM piutang_pasien where piutang_pasien.no_rkm_medis=?",Kdmem.getText())
                        +
                       Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang.sisapiutang),0) FROM piutang where piutang.no_rkm_medis=?",Kdmem.getText())
                       - 
                       Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rkm_medis=?",Kdmem.getText());
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
            Sequel.cariIsi("select reg_periksa.no_rawat from reg_periksa where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc limit 1", NoRawat,Kdmem.getText());            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", Nmmem,Kdmem.getText());
            sisapiutang=Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang_pasien.sisapiutang),0) FROM piutang_pasien where piutang_pasien.no_rkm_medis=?",Kdmem.getText())
                        +
                       Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang.sisapiutang),0) FROM piutang where piutang.no_rkm_medis=?",Kdmem.getText())
                       - 
                       Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rkm_medis=?",Kdmem.getText());
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
            Sequel.cariIsi("select reg_periksa.no_rawat from reg_periksa where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc limit 1", NoRawat,Kdmem.getText()); 
            Keterangan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", Nmmem,Kdmem.getText());
            sisapiutang=Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang_pasien.sisapiutang),0) FROM piutang_pasien where piutang_pasien.no_rkm_medis=?",Kdmem.getText())
                        +
                       Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang.sisapiutang),0) FROM piutang where piutang.no_rkm_medis=?",Kdmem.getText())
                       - 
                       Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rkm_medis=?",Kdmem.getText());
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
            Sequel.cariIsi("select reg_periksa.no_rawat from reg_periksa where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc limit 1", NoRawat,Kdmem.getText()); 
            NoRawat.requestFocus();
        }
    }//GEN-LAST:event_KdmemKeyPressed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void SisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SisaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SisaKeyPressed

private void ppNotaPiutangBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppNotaPiutangBtnPrintActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnKeluar.requestFocus();
        }else if(Cicilan.getText().equals("")||Cicilan.getText().equals("0")){
            JOptionPane.showMessageDialog(null,"Maaf, pilih dulu data yang mau dicetak notanya...!!!!");
            tbKamar.requestFocus();
        }else if(tabMode.getRowCount()!=0){  
            if(tbKamar.getSelectedRow()>-1){
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                Sequel.menyimpan("temporary","'0','No.Rawat',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),5).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'1','Tgl.Bayar',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),0).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'2','No.Rekam Medik',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),1).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'3','Nama Pasien',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),2).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'4','Keterangan',': "+tabMode.getValueAt(tbKamar.getSelectedRow(),4).toString() +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
                Sequel.menyimpan("temporary","'5','Besar Cicilan',': "+Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(tbKamar.getSelectedRow(),3).toString())) +"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 

                Valid.panggilUrl("billing/LaporanPiutang.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+Tanggal.getSelectedItem().toString().replaceAll(" ","_")+"&alamatip="+akses.getalamatip()+"&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB());
            }   
        }
        this.setCursor(Cursor.getDefaultCursor());    
}//GEN-LAST:event_ppNotaPiutangBtnPrintActionPerformed

private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        DlgCariPasien pasien=new DlgCariPasien(null,false);
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){                   
                    Kdmem.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),0).toString());
                    Nmmem.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                    sisapiutang=Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang_pasien.sisapiutang),0) FROM piutang_pasien where piutang_pasien.no_rkm_medis=?",Kdmem.getText())
                                +
                               Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang.sisapiutang),0) FROM piutang where piutang.no_rkm_medis=?",Kdmem.getText())
                               - 
                               Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rkm_medis=?",Kdmem.getText());
                   Sisa.setText(Valid.SetAngka(sisapiutang));
                   if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                        Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
                   }
                   Sequel.cariIsi("select reg_periksa.no_rawat from reg_periksa where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc limit 1", NoRawat,Kdmem.getText());
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
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pasien.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setAlwaysOnTop(false);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void AkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkunBayarKeyPressed
        Valid.pindah(evt,Tanggal,NoRawat);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void AkunPiutangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkunPiutangKeyPressed
        Valid.pindah(evt,Tanggal,Keterangan);
    }//GEN-LAST:event_AkunPiutangKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        if(NoRawat.getText().trim().equals("")){
            AkunPiutang.removeAllItems();
            Valid.textKosong(NoRawat,"No.Tagihan/No.Rawat");
        }else{
            kontraakun="";
            if(Sequel.cariInteger("select count(piutang.nota_piutang) from piutang where piutang.nota_piutang=?",NoRawat.getText())>0){
                kontraakun=Sequel.cariIsi("select set_akun.Piutang_Obat from set_akun");
                AkunPiutang.removeAllItems();
                AkunPiutang.addItem(Sequel.cariIsi("select rekening.nm_rek from rekening where rekening.kd_rek=?",kontraakun));
                Kdmem.setText(Sequel.cariIsi("select piutang.no_rkm_medis from piutang where piutang.nota_piutang=?",NoRawat.getText()));
                Nmmem.setText(Sequel.cariIsi("select piutang.nm_pasien from piutang where piutang.nota_piutang=?",NoRawat.getText()));
                sisapiutang=Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang.sisapiutang),0) FROM piutang where piutang.nota_piutang=?",NoRawat.getText())
                            -Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",NoRawat.getText());
                Sisa.setText(Valid.SetAngka(sisapiutang));
                status="obat";
            }else{
                Valid.loadCombo(AkunPiutang,"select detail_piutang_pasien.nama_bayar from detail_piutang_pasien where detail_piutang_pasien.no_rawat='"+NoRawat.getText()+"'");
                if(AkunPiutang.getSelectedItem()==null){
                    JOptionPane.showMessageDialog(null,"Nomor tagihan tidak ditemukan...!!");
                    NoRawat.requestFocus();
                }else{
                    Kdmem.setText(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?",NoRawat.getText()));
                    Nmmem.setText(Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?",Kdmem.getText()));
                    kontraakun=Sequel.cariIsi("select akun_piutang.kd_rek from akun_piutang where akun_piutang.nama_bayar=?",AkunPiutang.getSelectedItem().toString());
                    sisapiutang=Sequel.cariIsiAngka("select detail_piutang_pasien.sisapiutang from detail_piutang_pasien where detail_piutang_pasien.no_rawat='"+NoRawat.getText()+"' and detail_piutang_pasien.nama_bayar='"+AkunPiutang.getSelectedItem().toString()+"'");
                    Sisa.setText(Valid.SetAngka(sisapiutang));
                    status="pasien";
                }   
            }
        }
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void AkunPiutangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_AkunPiutangItemStateChanged
        if(this.isVisible()==true){
            if(AkunPiutang.getSelectedItem()!=null){
                if(!status.equals("obat")){
                    kontraakun=Sequel.cariIsi("select akun_piutang.kd_rek from akun_piutang where akun_piutang.nama_bayar=?",AkunPiutang.getSelectedItem().toString());
                    sisapiutang=Sequel.cariIsiAngka("select detail_piutang_pasien.sisapiutang from detail_piutang_pasien where detail_piutang_pasien.no_rawat='"+NoRawat.getText()+"' and detail_piutang_pasien.nama_bayar='"+AkunPiutang.getSelectedItem().toString()+"'");
                    Sisa.setText(Valid.SetAngka(sisapiutang));
                }
            }
        }
    }//GEN-LAST:event_AkunPiutangItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/akunbayar.iyem")<8){
                tampilAkunBayar2();
            }else{
                tampilAkunBayar();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        tampilAkunBayar();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void DiskonBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiskonBayarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
            Cicilan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
            TidakTerbayar.requestFocus();
        }
    }//GEN-LAST:event_DiskonBayarKeyPressed

    private void TidakTerbayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TidakTerbayarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
            DiskonBayar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sisa.setText(Valid.SetAngka(sisapiutang));
            if((!Cicilan.getText().equals(""))&&(!DiskonBayar.getText().equals(""))&&(!TidakTerbayar.getText().equals(""))){                           
                 Sisa.setText(Valid.SetAngka(sisapiutang-Double.parseDouble(Cicilan.getText())-Double.parseDouble(DiskonBayar.getText())-Double.parseDouble(TidakTerbayar.getText())));                           
            }
            BtnSimpan.requestFocus();
        }
    }//GEN-LAST:event_TidakTerbayarKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBayarPiutang dialog = new DlgBayarPiutang(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AkunBayar;
    private widget.ComboBox AkunPiutang;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.TextBox Cicilan;
    private widget.TextBox DiskonBayar;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.TextBox Kdmem;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LDiskon;
    private widget.Label LTTerbayar;
    private widget.Label LTotal;
    private widget.TextBox Nmmem;
    private widget.TextBox NoRawat;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox Sisa;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.TextBox TidakTerbayar;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label32;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppNotaPiutang;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            ps=koneksi.prepareStatement(
                    "select bayar_piutang.tgl_bayar, bayar_piutang.no_rkm_medis,pasien.nm_pasien, bayar_piutang.besar_cicilan,"+
                    "bayar_piutang.catatan, bayar_piutang.no_rawat,bayar_piutang.kd_rek,bayar_piutang.kd_rek_kontra,"+
                    "bayar_piutang.diskon_piutang,bayar_piutang.kd_rek_diskon_piutang,bayar_piutang.tidak_terbayar,"+
                    "bayar_piutang.kd_rek_tidak_terbayar from bayar_piutang "+
                    "inner join pasien on bayar_piutang.no_rkm_medis=pasien.no_rkm_medis where "+
                    "bayar_piutang.tgl_bayar between ? and ? and (bayar_piutang.no_rawat like ? or "+
                    "bayar_piutang.no_rkm_medis like ? or pasien.nm_pasien like ?) "+
                    "order by bayar_piutang.tgl_bayar,bayar_piutang.no_rkm_medis");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,"%"+TCari.getText()+"%");
                ps.setString(5,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){                
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getDouble(9),rs.getString(10),rs.getDouble(11),rs.getString(12)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif :"+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            totalpiutang();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void totalpiutang(){
        LCount.setText(""+tabMode.getRowCount());
        cicilan=0;diskon=0;tidakterbayar=0;
        for(i=0;i<tabMode.getRowCount();i++){
            cicilan=cicilan+Valid.SetAngka(tbKamar.getValueAt(i,3).toString());
            diskon=diskon+Valid.SetAngka(tbKamar.getValueAt(i,8).toString());
            tidakterbayar=tidakterbayar+Valid.SetAngka(tbKamar.getValueAt(i,10).toString());
        }
        LTotal.setText(Valid.SetAngka(cicilan));
        LDiskon.setText(Valid.SetAngka(diskon));
        LTTerbayar.setText(Valid.SetAngka(tidakterbayar));
    }

    public void emptTeks() {
        Kd2.setText("");
        Cicilan.setText("0");
        Kdmem.setText("");
        Nmmem.setText("");
        Sisa.setText("0");
        DiskonBayar.setText("0");
        TidakTerbayar.setText("0");
        Keterangan.setText("");
        Tanggal.setDate(new Date());
        sisapiutang=0;
        NoRawat.requestFocus();
    }
    
    public void setData(String norawat,String norm,String nama){
        NoRawat.setText(norawat);
        Kdmem.setText(norm);
        Nmmem.setText(nama);
        TCari.setText(norawat);
        ChkInput.setSelected(true);
        isForm();
        kontraakun="";
        if(Sequel.cariInteger("select count(piutang.nota_piutang) from piutang where piutang.nota_piutang=?",NoRawat.getText())==1){
            kontraakun=Sequel.cariIsi("select set_akun.Piutang_Obat from set_akun");
            AkunPiutang.removeAllItems();
            AkunPiutang.addItem(Sequel.cariIsi("select rekening.nm_rek from rekening where rekening.kd_rek=?",kontraakun));
            sisapiutang=Sequel.cariIsiAngka("SELECT ifnull(SUM(piutang.sisapiutang),0) FROM piutang where piutang.nota_piutang=?",NoRawat.getText())
                        -Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",NoRawat.getText());
            Sisa.setText(Valid.SetAngka(sisapiutang));
            status="obat";
        }else{
            Valid.loadCombo(AkunPiutang,"select detail_piutang_pasien.nama_bayar from detail_piutang_pasien where detail_piutang_pasien.no_rawat='"+NoRawat.getText()+"'");
            kontraakun=Sequel.cariIsi("select akun_piutang.kd_rek from akun_piutang where akun_piutang.nama_bayar=?",AkunPiutang.getSelectedItem().toString());
            sisapiutang=Sequel.cariIsiAngka("select detail_piutang_pasien.sisapiutang from detail_piutang_pasien where detail_piutang_pasien.no_rawat='"+NoRawat.getText()+"' and nama_bayar='"+AkunPiutang.getSelectedItem().toString()+"'");
            Sisa.setText(Valid.SetAngka(sisapiutang));
            status="pasien";
        }
    }

    private void getData() {
        int row=tbKamar.getSelectedRow();
        if(row!= -1){
            sisapiutang=0;
            kontraakun="";
            AkunPiutang.removeAllItems();
            Kdmem.setText(tbKamar.getValueAt(row,1).toString());
            Nmmem.setText(tbKamar.getValueAt(row,2).toString());
            Cicilan.setText(tbKamar.getValueAt(row,3).toString());
            Keterangan.setText(tbKamar.getValueAt(row,4).toString());
            NoRawat.setText(tbKamar.getValueAt(row,5).toString());
            DiskonBayar.setText(tbKamar.getValueAt(row,8).toString());
            TidakTerbayar.setText(tbKamar.getValueAt(row,10).toString());
            Sisa.setText("0");
            Valid.SetTgl(Tanggal,tbKamar.getValueAt(row,0).toString());
        }
    }

    public JTextField getTextField(){
        return NoRawat;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbayar_piutang());
        BtnHapus.setEnabled(akses.getbayar_piutang());
        BtnPrint.setEnabled(akses.getbayar_piutang());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,154));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    private void tampilAkunBayar() {         
         try{      
             file=new File("./cache/akunbayar.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             iyem="";
             ps=koneksi.prepareStatement("select * from akun_bayar order by akun_bayar.nama_bayar");
             try{
                 rs=ps.executeQuery();
                 AkunBayar.removeAllItems();
                 while(rs.next()){    
                     AkunBayar.addItem(rs.getString(1).replaceAll("\"",""));
                     iyem=iyem+"{\"NamaAkun\":\""+rs.getString(1).replaceAll("\"","")+"\",\"KodeRek\":\""+rs.getString(2)+"\",\"PPN\":\""+rs.getDouble(3)+"\"},";
                 }
             }catch (Exception e) {
                 System.out.println("Notifikasi : "+e);
             } finally{
                 if(rs != null){
                     rs.close();
                 } 
                 if(ps != null){
                     ps.close();
                 } 
             }

             fileWriter.write("{\"akunbayar\":["+iyem.substring(0,iyem.length()-1)+"]}");
             fileWriter.flush();
             fileWriter.close();
             iyem=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilAkunBayar2() {
        try {
            myObj = new FileReader("./cache/akunbayar.iyem");
            root = mapper.readTree(myObj);
            response = root.path("akunbayar");
            if(response.isArray()){
                for(JsonNode list:response){
                    AkunBayar.addItem(list.path("NamaAkun").asText().replaceAll("\"",""));
                }
            }
            myObj.close();
        } catch (Exception ex) {
            if(ex.toString().contains("java.io.FileNotFoundException")){
                tampilAkunBayar();
            }else{
                System.out.println("Notifikasi : "+ex);
            }
        }
    } 
}
