/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package laporan;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgKabupaten;
import simrskhanza.DlgKecamatan;
import simrskhanza.DlgKelurahan;
import simrskhanza.DlgCariCaraBayar;

/**
 *
 * @author perpustakaan
 */
public final class DlgKunjunganRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgKabupaten kabupaten=new DlgKabupaten(null,false);
    private DlgKecamatan kecamatan=new DlgKecamatan(null,false);
    private DlgKelurahan kelurahan=new DlgKelurahan(null,false);
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    private int i=0,lama=0,baru=0,laki=0,per=0;   
    private String setbaru="",setlama="",umurlk="",umurpr="",kddiangnosa="",diagnosa="",status="";
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgKunjunganRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabMode=new DefaultTableModel(null,new Object[]{"No.","Lama","Baru","Nama Pasien","L","P","Alamat","Kode","Diagnosa","Dokter Jaga"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        table1.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        table1.setPreferredScrollableViewportSize(new Dimension(500,500));
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = table1.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(40);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(200);
            }
        }
        table1.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{"No.","Lama","Baru","Nama Pasien","L","P","Alamat","Kode","Diagnosa","Dokter Jaga"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        table2.setModel(tabMode2);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        table2.setPreferredScrollableViewportSize(new Dimension(500,500));
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = table2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(40);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(200);
            }
        }
        table2.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((int)90).getKata(TCari));
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){
                    kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    nmpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                }      
                kdpoli.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {poli.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                }      
                kdpenjab.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kabupaten.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kabupaten.getTable().getSelectedRow()!= -1){
                    nmkabupaten.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(),0).toString());
                }      
                nmkabupaten.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {kabupaten.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        kabupaten.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kabupaten.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kecamatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kecamatan.getTable().getSelectedRow()!= -1){
                    nmkecamatan.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(),0).toString());
                }      
                nmkecamatan.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {kecamatan.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        kecamatan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kecamatan.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kelurahan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kelurahan.getTable().getSelectedRow()!= -1){
                    nmkelurahan.setText(kelurahan.getTable().getValueAt(kelurahan.getTable().getSelectedRow(),0).toString());
                }      
                nmkelurahan.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {kelurahan.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        kelurahan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kelurahan.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    nmdokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }      
                kddokter.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {dokter.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dokter.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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

        TKd = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppTampilkanBaru = new javax.swing.JMenuItem();
        ppTampilkanLama = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label19 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        label20 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        label21 = new widget.Label();
        nmkabupaten = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        label22 = new widget.Label();
        nmkecamatan = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        BtnSeek7 = new widget.Button();
        nmkelurahan = new widget.TextBox();
        label23 = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        table1 = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        table2 = new widget.Table();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppTampilkanBaru.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBaru.setForeground(java.awt.Color.darkGray);
        ppTampilkanBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBaru.setText("Tampilkan Pasien Baru");
        ppTampilkanBaru.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBaru.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBaru.setName("ppTampilkanBaru"); // NOI18N
        ppTampilkanBaru.setPreferredSize(new java.awt.Dimension(175, 25));
        ppTampilkanBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBaruBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBaru);

        ppTampilkanLama.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanLama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanLama.setForeground(java.awt.Color.darkGray);
        ppTampilkanLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanLama.setText("Tampilkan Pasien Lama");
        ppTampilkanLama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanLama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanLama.setName("ppTampilkanLama"); // NOI18N
        ppTampilkanLama.setPreferredSize(new java.awt.Dimension(175, 25));
        ppTampilkanLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanLamaBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanLama);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Kunjungan Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(155, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

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
        panelGlass5.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
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
        panelGlass5.add(BtnAll);

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(jLabel7);

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

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 104));
        FormInput.setLayout(null);

        label17.setText("Unit/Poli :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label17);
        label17.setBounds(0, 10, 75, 23);

        kdpoli.setEditable(false);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.setPreferredSize(new java.awt.Dimension(75, 23));
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        FormInput.add(kdpoli);
        kdpoli.setBounds(78, 10, 85, 23);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmpoli);
        nmpoli.setBounds(165, 10, 228, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(396, 10, 28, 23);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label19);
        label19.setBounds(0, 70, 75, 23);

        kdpenjab.setEditable(false);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(75, 23));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        FormInput.add(kdpenjab);
        kdpenjab.setBounds(78, 70, 85, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmpenjab);
        nmpenjab.setBounds(165, 70, 228, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek3);
        BtnSeek3.setBounds(396, 70, 28, 23);

        label20.setText("Dokter :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label20);
        label20.setBounds(0, 40, 75, 23);

        kddokter.setEditable(false);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(75, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormInput.add(kddokter);
        kddokter.setBounds(78, 40, 85, 23);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmdokter);
        nmdokter.setBounds(165, 40, 228, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('3');
        BtnSeek4.setToolTipText("Alt+3");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(396, 40, 28, 23);

        label21.setText("Kab/Kota :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label21);
        label21.setBounds(429, 10, 87, 23);

        nmkabupaten.setEditable(false);
        nmkabupaten.setName("nmkabupaten"); // NOI18N
        nmkabupaten.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkabupaten);
        nmkabupaten.setBounds(519, 10, 260, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('3');
        BtnSeek5.setToolTipText("Alt+3");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek5);
        BtnSeek5.setBounds(782, 10, 28, 23);

        label22.setText("Kecamatan :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label22);
        label22.setBounds(429, 40, 87, 23);

        nmkecamatan.setEditable(false);
        nmkecamatan.setName("nmkecamatan"); // NOI18N
        nmkecamatan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkecamatan);
        nmkecamatan.setBounds(519, 40, 260, 23);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('3');
        BtnSeek6.setToolTipText("Alt+3");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        BtnSeek6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek6KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek6);
        BtnSeek6.setBounds(782, 40, 28, 23);

        BtnSeek7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek7.setMnemonic('3');
        BtnSeek7.setToolTipText("Alt+3");
        BtnSeek7.setName("BtnSeek7"); // NOI18N
        BtnSeek7.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek7ActionPerformed(evt);
            }
        });
        BtnSeek7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek7KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek7);
        BtnSeek7.setBounds(782, 70, 28, 23);

        nmkelurahan.setEditable(false);
        nmkelurahan.setName("nmkelurahan"); // NOI18N
        nmkelurahan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkelurahan);
        nmkelurahan.setBounds(519, 70, 260, 23);

        label23.setText("Kelurahan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label23);
        label23.setBounds(429, 70, 87, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        table1.setComponentPopupMenu(jPopupMenu1);
        table1.setName("table1"); // NOI18N
        Scroll.setViewportView(table1);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Seluruh Kunjungan", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setComponentPopupMenu(jPopupMenu1);
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        table2.setComponentPopupMenu(jPopupMenu1);
        table2.setName("table2"); // NOI18N
        Scroll1.setViewportView(table2);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Kunjungan Non Batal", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Map<String, Object> param = new HashMap<>();         
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("periode",Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem());   
            param.put("lama",lama);   
            param.put("baru",baru);   
            param.put("total",(lama+baru));   
            param.put("laki",laki);   
            param.put("perempuan",per);   
            param.put("tanggal",Tgl2.getDate());   
            Sequel.queryu("truncate table temporary");
            if(TabRawat.getSelectedIndex()==0){
                for(int r=0;r<tabMode.getRowCount();r++){ 
                    if(!table1.getValueAt(r,0).toString().contains(">>")){
                        Sequel.menyimpan("temporary","'0','"+
                                        tabMode.getValueAt(r,0).toString()+"','"+
                                        tabMode.getValueAt(r,1).toString()+"','"+
                                        tabMode.getValueAt(r,2).toString()+"','"+
                                        tabMode.getValueAt(r,3).toString()+"','"+
                                        tabMode.getValueAt(r,4).toString()+"','"+
                                        tabMode.getValueAt(r,5).toString()+"','"+
                                        tabMode.getValueAt(r,6).toString()+"','"+
                                        tabMode.getValueAt(r,7).toString()+"','"+
                                        tabMode.getValueAt(r,8).toString()+"','"+
                                        tabMode.getValueAt(r,9).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
                    }                    
                }
            }else if(TabRawat.getSelectedIndex()==1){
                for(int r=0;r<tabMode2.getRowCount();r++){ 
                    if(!table2.getValueAt(r,0).toString().contains(">>")){
                        Sequel.menyimpan("temporary","'0','"+
                                        tabMode2.getValueAt(r,0).toString()+"','"+
                                        tabMode2.getValueAt(r,1).toString()+"','"+
                                        tabMode2.getValueAt(r,2).toString()+"','"+
                                        tabMode2.getValueAt(r,3).toString()+"','"+
                                        tabMode2.getValueAt(r,4).toString()+"','"+
                                        tabMode2.getValueAt(r,5).toString()+"','"+
                                        tabMode2.getValueAt(r,6).toString()+"','"+
                                        tabMode2.getValueAt(r,7).toString()+"','"+
                                        tabMode2.getValueAt(r,8).toString()+"','"+
                                        tabMode2.getValueAt(r,9).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
                    }                    
                }
            }
               
            Valid.MyReport("rptKunjunganRalan.jasper","report","::[ Laporan Kunjungan Rawat Jalan ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
       if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TKd, BtnPrint);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
           TCari.setText("");
           kdpoli.setText("");
           nmpoli.setText("");
           kddokter.setText("");
           nmdokter.setText("");
           kdpenjab.setText("");
           nmpenjab.setText("");
           nmkabupaten.setText("");
           nmkecamatan.setText("");
           nmkelurahan.setText("");
           status="";
           if(TabRawat.getSelectedIndex()==0){
                tampil();
            }else if(TabRawat.getSelectedIndex()==1){
                tampil2();
            }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpoliKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        kabupaten.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kabupaten.setLocationRelativeTo(internalFrame1);
        kabupaten.setAlwaysOnTop(false);
        kabupaten.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek5KeyPressed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        kecamatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kecamatan.setLocationRelativeTo(internalFrame1);
        kecamatan.setAlwaysOnTop(false);
        kecamatan.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void BtnSeek6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek6KeyPressed

    private void BtnSeek7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek7ActionPerformed
        kelurahan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kelurahan.setLocationRelativeTo(internalFrame1);
        kelurahan.setAlwaysOnTop(false);
        kelurahan.setVisible(true);
    }//GEN-LAST:event_BtnSeek7ActionPerformed

    private void BtnSeek7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek7KeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void ppTampilkanBaruBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBaruBtnPrintActionPerformed
        status="Baru";
        BtnCariActionPerformed(null);
    }//GEN-LAST:event_ppTampilkanBaruBtnPrintActionPerformed

    private void ppTampilkanLamaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanLamaBtnPrintActionPerformed
        status="Lama";
        BtnCariActionPerformed(null);
    }//GEN-LAST:event_ppTampilkanLamaBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKunjunganRalan dialog = new DlgKunjunganRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnSeek7;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.TextBox kdpenjab;
    private widget.TextBox kdpoli;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.TextBox nmdokter;
    private widget.TextBox nmkabupaten;
    private widget.TextBox nmkecamatan;
    private widget.TextBox nmkelurahan;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.panelisi panelGlass5;
    private javax.swing.JMenuItem ppTampilkanBaru;
    private javax.swing.JMenuItem ppTampilkanLama;
    private widget.Table table1;
    private widget.Table table2;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode);   
            if(nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.stts_daftar," +
                        "dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab)as almt_pj,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.tgl_daftar " +
                        "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                        "inner join kabupaten inner join kecamatan inner join kelurahan on reg_periksa.kd_dokter=dokter.kd_dokter " +
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj " +
                        "and reg_periksa.kd_poli=poliklinik.kd_poli and pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel " +
                        "where reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.stts_daftar," +
                        "dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab)as almt_pj,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.tgl_daftar " +
                        "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                        "inner join kabupaten inner join kecamatan inner join kelurahan on reg_periksa.kd_dokter=dokter.kd_dokter " +
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj " +
                        "and reg_periksa.kd_poli=poliklinik.kd_poli and pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel " +
                        "where reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and poliklinik.nm_poli like ? or " +
                        "reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and dokter.nm_dokter like ? or " +
                        "reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and reg_periksa.no_rkm_medis like ? or " +
                        "reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and pasien.nm_pasien like ? or " +
                        "reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and pasien.alamat like ? "+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
            }
                
            try {
                if(nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(4,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(5,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(6,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(7,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(8,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(12,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(13,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(14,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(15,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(16,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(17,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(21,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(22,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(23,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(24,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(25,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(26,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(27,"%"+TCari.getText().trim()+"%");
                    ps.setString(28,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(29,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(30,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(31,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(32,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(33,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(34,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(35,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(39,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(40,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(41,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(42,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(43,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(44,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(45,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                i=1;   
                lama=0;baru=0;laki=0;per=0;
                while(rs.next()){
                    setbaru="";
                    setlama="";
                    if(rs.getString("stts_daftar").equals("Baru")){
                        setbaru=rs.getString("no_rkm_medis");
                        baru++;
                    }else if(rs.getString("stts_daftar").equals("Lama")){
                        setlama=rs.getString("no_rkm_medis");
                        lama++;
                    }
                    umurlk="";
                    umurpr="";
                    switch (rs.getString("jk")) {
                        case "L":
                            umurlk=rs.getString("umur");
                            laki++;
                            break;
                        case "P":
                            umurpr=rs.getString("umur");
                            per++;
                            break;
                    }
                    diagnosa="";
                    kddiangnosa="";
                    ps2=koneksi.prepareStatement("select penyakit.kd_penyakit,penyakit.nm_penyakit from penyakit inner join diagnosa_pasien " +
                        "on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit " +
                        "where diagnosa_pasien.no_rawat=? order by prioritas asc limit 1");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            kddiangnosa=rs2.getString(1);
                            diagnosa=rs2.getString(2);
                        }
                    } catch (Exception e) {
                        System.out.println("laporan.DlgKunjunganRalan.tampil() 2 :"+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        
                    tabMode.addRow(new Object[]{
                        i,setlama,setbaru,rs.getString("nm_pasien"),umurlk,umurpr,rs.getString("almt_pj"),kddiangnosa,diagnosa,rs.getString("nm_dokter")
                    });                
                    i++;
                }
                if(i>=2){
                    tabMode.addRow(new Object[]{
                        ">>",lama,baru,"",laki,per,"","","",""
                    });
                }
            } catch (Exception e) {
                System.out.println("laporan.DlgKunjunganRalan.tampil() : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }       
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil2(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode2);   
            if(nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.stts_daftar," +
                        "dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab)as almt_pj,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.tgl_daftar " +
                        "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                        "inner join kabupaten inner join kecamatan inner join kelurahan on reg_periksa.kd_dokter=dokter.kd_dokter " +
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj " +
                        "and reg_periksa.kd_poli=poliklinik.kd_poli and pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel " +
                        "where reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.stts_daftar," +
                        "dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                        "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab)as almt_pj,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.tgl_daftar " +
                        "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                        "inner join kabupaten inner join kecamatan inner join kelurahan on reg_periksa.kd_dokter=dokter.kd_dokter " +
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj " +
                        "and reg_periksa.kd_poli=poliklinik.kd_poli and pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel " +
                        "where reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and poliklinik.nm_poli like ? or " +
                        "reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and dokter.nm_dokter like ? or " +
                        "reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and reg_periksa.no_rkm_medis like ? or " +
                        "reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and pasien.nm_pasien like ? or " +
                        "reg_periksa.stts_daftar like '%"+status+"%' and reg_periksa.status_lanjut='Ralan' and reg_periksa.stts<>'Batal' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and pasien.alamat like ? "+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg");
            }
                
            try {
                if(nmpoli.getText().trim().equals("")&&nmdokter.getText().trim().equals("")&&nmpenjab.getText().trim().equals("")&&nmkabupaten.getText().trim().equals("")&&nmkecamatan.getText().trim().equals("")&&nmkelurahan.getText().trim().equals("")&&TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(4,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(5,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(6,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(7,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(8,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(12,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(13,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(14,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(15,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(16,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(17,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(21,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(22,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(23,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(24,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(25,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(26,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(27,"%"+TCari.getText().trim()+"%");
                    ps.setString(28,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(29,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(30,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(31,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(32,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(33,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(34,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(35,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(39,"%"+nmpoli.getText().trim()+"%");
                    ps.setString(40,"%"+nmdokter.getText().trim()+"%");
                    ps.setString(41,"%"+nmpenjab.getText().trim()+"%");
                    ps.setString(42,"%"+nmkabupaten.getText().trim()+"%");
                    ps.setString(43,"%"+nmkecamatan.getText().trim()+"%");
                    ps.setString(44,"%"+nmkelurahan.getText().trim()+"%");
                    ps.setString(45,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                i=1;   
                lama=0;baru=0;laki=0;per=0;
                while(rs.next()){
                    setbaru="";
                    setlama="";
                    if(rs.getString("stts_daftar").equals("Baru")){
                        setbaru=rs.getString("no_rkm_medis");
                        baru++;
                    }else if(rs.getString("stts_daftar").equals("Lama")){
                        setlama=rs.getString("no_rkm_medis");
                        lama++;
                    }
                    umurlk="";
                    umurpr="";
                    switch (rs.getString("jk")) {
                        case "L":
                            umurlk=rs.getString("umur");
                            laki++;
                            break;
                        case "P":
                            umurpr=rs.getString("umur");
                            per++;
                            break;
                    }
                    diagnosa="";
                    kddiangnosa="";
                    ps2=koneksi.prepareStatement("select penyakit.kd_penyakit,penyakit.nm_penyakit from penyakit inner join diagnosa_pasien " +
                        "on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit " +
                        "where diagnosa_pasien.no_rawat=? order by prioritas asc limit 1");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            kddiangnosa=rs2.getString(1);
                            diagnosa=rs2.getString(2);
                        }
                    } catch (Exception e) {
                        System.out.println("laporan.DlgKunjunganRalan.tampil() 2 :"+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        
                    tabMode2.addRow(new Object[]{
                        i,setlama,setbaru,rs.getString("nm_pasien"),umurlk,umurpr,rs.getString("almt_pj"),kddiangnosa,diagnosa,rs.getString("nm_dokter")
                    });                
                    i++;
                }
                if(i>=2){
                    tabMode2.addRow(new Object[]{
                        ">>",lama,baru,"",laki,per,"","","",""
                    });
                }
            } catch (Exception e) {
                System.out.println("laporan.DlgKunjunganRalan.tampil() : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }       
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getData() {
        int row=table1.getSelectedRow();
        if(row!= -1){
            TKd.setText(tabMode.getValueAt(row,0).toString());
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,126));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }

}
