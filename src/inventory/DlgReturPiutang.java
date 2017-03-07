package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgCariReturPiutang;
import inventory.riwayatobat;
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
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgPasien;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

public class DlgReturPiutang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private riwayatobat Trackobat=new riwayatobat();
    private Jurnal jur=new Jurnal();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private DlgPasien member=new DlgPasien(null,false);
    private DlgCariReturPiutang form=new DlgCariReturPiutang(null,false);   
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private double ttlretur=0;
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgReturPiutang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Nota",
                    "Kode Barang",
                    "Nama Barang",
                    "Satuan",
                    "Hrg.Retur(Rp)",
                    "Jml.Retur",
                    "Total Retur(Rp)"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(60);
            }else if(i==6){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoRetur.setDocument(new batasInput((byte)20).getKata(NoRetur));
        kdmem.setDocument(new batasInput((byte)15).getKata(kdmem));
        NoFaktur.setDocument(new batasInput((byte)20).getKata(NoFaktur));
        Kdptg.setDocument(new batasInput((byte)25).getKata(Kdptg));
        Kdbar.setDocument(new batasInput((byte)15).getKata(Kdbar));
        //Jmlbeli.setDocument(new batasInput((byte)13).getKata(Jmlbeli));
        Jmlretur.setDocument(new batasInput((byte)13).getKata(Jmlretur));
        Hargaretur.setDocument(new batasInput((byte)13).getKata(Hargaretur));
        
        Jmlretur.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        
        Hargaretur.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void removeUpdate(DocumentEvent e) {isHitung();}
            @Override
            public void changedUpdate(DocumentEvent e) {isHitung();}
        });
        
        form.barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgReturPiutang")){
                    if(form.barang.getTable().getSelectedRow()!= -1){                   
                        Kdbar.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),1).toString());                    
                        nmbar.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),2).toString());
                        Satuanbar.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),3).toString());
                        
                        if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),15).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Karyawan")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),16).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),7).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),8).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),9).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),10).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),11).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("VIP")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),12).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),13).toString()); 
                        }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                           Hargaretur.setText(form.barang.getTable().getValueAt(form.barang.getTable().getSelectedRow(),14).toString()); 
                        }
                    }                
                    Kdbar.requestFocus();
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
        
        form.barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgReturPiutang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        form.barang.dispose();
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgReturPiutang")){
                    if(member.getTable().getSelectedRow()!= -1){                   
                        kdmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),1).toString());
                        nmmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),2).toString());
                    } 
                    kdmem.requestFocus();
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
        
        member.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgReturPiutang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        member.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        form.petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgReturPiutang")){
                    if(form.petugas.getTable().getSelectedRow()!= -1){                   
                        Kdptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(),0).toString());
                        Nmptg.setText(form.petugas.getTable().getValueAt(form.petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    Kdptg.requestFocus();
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgReturPiutang")){
                    if(bangsal.getTable().getSelectedRow()!= -1){                   
                        kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                        nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                    } 
                    kdgudang.requestFocus();
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
       

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppTambah = new javax.swing.JMenuItem();
        ppHapus = new javax.swing.JMenuItem();
        ppCetakNota = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnTambah = new widget.Button();
        BtnHapus = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        label21 = new widget.Label();
        Satuanbar = new widget.TextBox();
        label25 = new widget.Label();
        Subtotal = new widget.TextBox();
        label26 = new widget.Label();
        Jmlretur = new widget.TextBox();
        label27 = new widget.Label();
        Hargaretur = new widget.TextBox();
        Kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        BtnBrg = new widget.Button();
        panelisiBeli = new widget.panelisi();
        label15 = new widget.Label();
        NoRetur = new widget.TextBox();
        label18 = new widget.Label();
        NoFaktur = new widget.TextBox();
        Kdptg = new widget.TextBox();
        Nmptg = new widget.TextBox();
        BtnPtg = new widget.Button();
        label11 = new widget.Label();
        TglRetur = new widget.Tanggal();
        label16 = new widget.Label();
        kdmem = new widget.TextBox();
        nmmem = new widget.TextBox();
        BtnMmb = new widget.Button();
        Jenisjual = new widget.ComboBox();
        label13 = new widget.Label();
        label12 = new widget.Label();
        label32 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppTambah.setBackground(new java.awt.Color(255, 255, 255));
        ppTambah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTambah.setForeground(new java.awt.Color(102, 51, 0));
        ppTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        ppTambah.setText("Tambah");
        ppTambah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTambah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTambah.setIconTextGap(8);
        ppTambah.setName("ppTambah"); // NOI18N
        ppTambah.setPreferredSize(new java.awt.Dimension(150, 25));
        ppTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        Popup.add(ppTambah);

        ppHapus.setBackground(new java.awt.Color(255, 255, 255));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(102, 51, 0));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppHapus.setText("Hapus");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setIconTextGap(8);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(150, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        Popup.add(ppHapus);

        ppCetakNota.setBackground(new java.awt.Color(255, 255, 255));
        ppCetakNota.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCetakNota.setForeground(new java.awt.Color(102, 51, 0));
        ppCetakNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        ppCetakNota.setText("Cetak Nota");
        ppCetakNota.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCetakNota.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCetakNota.setIconTextGap(8);
        ppCetakNota.setName("ppCetakNota"); // NOI18N
        ppCetakNota.setPreferredSize(new java.awt.Dimension(150, 25));
        ppCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCetakNotaActionPerformed(evt);
            }
        });
        Popup.add(ppCetakNota);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Retur Piutang Obat, Alkes & BHP Medis Dari Pembeli ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 130));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambah.setMnemonic('T');
        BtnTambah.setText("Tambah");
        BtnTambah.setToolTipText("Alt+T");
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
        panelisi1.add(BtnTambah);

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

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi1.add(LTotal);

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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Clear");
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
        panelisi1.add(BtnBatal);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('E');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+E");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 10, 63, 23);

        label21.setText("Satuan :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label21);
        label21.setBounds(0, 40, 63, 23);

        Satuanbar.setEditable(false);
        Satuanbar.setName("Satuanbar"); // NOI18N
        Satuanbar.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(Satuanbar);
        Satuanbar.setBounds(67, 40, 120, 23);

        label25.setText("SubTotal :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label25);
        label25.setBounds(572, 40, 80, 23);

        Subtotal.setEditable(false);
        Subtotal.setName("Subtotal"); // NOI18N
        Subtotal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(Subtotal);
        Subtotal.setBounds(656, 40, 117, 23);

        label26.setText("Jml.Retur :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label26);
        label26.setBounds(340, 40, 70, 23);

        Jmlretur.setName("Jmlretur"); // NOI18N
        Jmlretur.setPreferredSize(new java.awt.Dimension(80, 23));
        Jmlretur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JmlreturKeyPressed(evt);
            }
        });
        panelisi4.add(Jmlretur);
        Jmlretur.setBounds(414, 40, 100, 23);

        label27.setText("Hrga.Retur :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label27);
        label27.setBounds(572, 10, 80, 23);

        Hargaretur.setName("Hargaretur"); // NOI18N
        Hargaretur.setPreferredSize(new java.awt.Dimension(80, 23));
        Hargaretur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HargareturKeyPressed(evt);
            }
        });
        panelisi4.add(Hargaretur);
        Hargaretur.setBounds(656, 10, 117, 23);

        Kdbar.setName("Kdbar"); // NOI18N
        Kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        Kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdbarKeyPressed(evt);
            }
        });
        panelisi4.add(Kdbar);
        Kdbar.setBounds(67, 10, 120, 23);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);
        nmbar.setBounds(189, 10, 325, 23);

        BtnBrg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnBrg.setMnemonic('3');
        BtnBrg.setToolTipText("Alt+3");
        BtnBrg.setName("BtnBrg"); // NOI18N
        BtnBrg.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnBrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBrgActionPerformed(evt);
            }
        });
        panelisi4.add(BtnBrg);
        BtnBrg.setBounds(516, 10, 28, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisiBeli.setName("panelisiBeli"); // NOI18N
        panelisiBeli.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisiBeli.setLayout(null);

        label15.setText("No.Retur :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisiBeli.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoRetur.setName("NoRetur"); // NOI18N
        NoRetur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoReturKeyPressed(evt);
            }
        });
        panelisiBeli.add(NoRetur);
        NoRetur.setBounds(74, 10, 220, 23);

        label18.setText("No.Nota :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisiBeli.add(label18);
        label18.setBounds(0, 40, 70, 23);

        NoFaktur.setName("NoFaktur"); // NOI18N
        NoFaktur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoFaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoFakturKeyPressed(evt);
            }
        });
        panelisiBeli.add(NoFaktur);
        NoFaktur.setBounds(74, 40, 220, 23);

        Kdptg.setName("Kdptg"); // NOI18N
        Kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        Kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdptgKeyPressed(evt);
            }
        });
        panelisiBeli.add(Kdptg);
        Kdptg.setBounds(409, 40, 80, 23);

        Nmptg.setEditable(false);
        Nmptg.setName("Nmptg"); // NOI18N
        Nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisiBeli.add(Nmptg);
        Nmptg.setBounds(491, 40, 250, 23);

        BtnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg.setMnemonic('2');
        BtnPtg.setToolTipText("Alt+2");
        BtnPtg.setName("BtnPtg"); // NOI18N
        BtnPtg.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtgActionPerformed(evt);
            }
        });
        panelisiBeli.add(BtnPtg);
        BtnPtg.setBounds(744, 40, 28, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label11);
        label11.setBounds(177, 70, 55, 23);

        TglRetur.setEditable(false);
        TglRetur.setDisplayFormat("dd-MM-yyyy");
        TglRetur.setName("TglRetur"); // NOI18N
        TglRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglReturKeyPressed(evt);
            }
        });
        panelisiBeli.add(TglRetur);
        TglRetur.setBounds(236, 70, 90, 23);

        label16.setText("Pasien :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisiBeli.add(label16);
        label16.setBounds(335, 10, 70, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisiBeli.add(kdmem);
        kdmem.setBounds(409, 10, 80, 23);

        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisiBeli.add(nmmem);
        nmmem.setBounds(491, 10, 250, 23);

        BtnMmb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMmb.setMnemonic('1');
        BtnMmb.setToolTipText("Alt+1");
        BtnMmb.setName("BtnMmb"); // NOI18N
        BtnMmb.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnMmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMmbActionPerformed(evt);
            }
        });
        panelisiBeli.add(BtnMmb);
        BtnMmb.setBounds(744, 10, 28, 23);

        Jenisjual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rawat Jalan", "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP", "Beli Luar", "Jual Bebas", "Karyawan" }));
        Jenisjual.setName("Jenisjual"); // NOI18N
        Jenisjual.setPreferredSize(new java.awt.Dimension(45, 23));
        Jenisjual.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisjualItemStateChanged(evt);
            }
        });
        panelisiBeli.add(Jenisjual);
        Jenisjual.setBounds(74, 70, 100, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label13);
        label13.setBounds(335, 40, 70, 23);

        label12.setText("Jenis :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label12);
        label12.setBounds(0, 70, 70, 23);

        label32.setText("Lokasi :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisiBeli.add(label32);
        label32.setBounds(335, 70, 70, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisiBeli.add(kdgudang);
        kdgudang.setBounds(409, 70, 80, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisiBeli.add(nmgudang);
        nmgudang.setBounds(491, 70, 250, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        panelisiBeli.add(BtnGudang);
        BtnGudang.setBounds(744, 70, 28, 23);

        internalFrame1.add(panelisiBeli, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        if(nmbar.getText().trim().equals("")){
            Valid.textKosong(Kdbar,"barang");
        }else if(Jmlretur.getText().trim().equals("")){
            Valid.textKosong(Jmlretur,"jumlah retur");
        }else if(Jmlretur.getText().trim().equals("0")){
            Valid.textKosong(Jmlretur,"jumlah retur");
        }else if(Hargaretur.getText().trim().equals("")){
            Valid.textKosong(Hargaretur,"harga retur");
        }else{
            Sequel.menyimpan("tampreturpiutang","'"+NoFaktur.getText()+"','"+Kdbar.getText()+"','"+nmbar.getText()+"','0','0','"+
                             Jmlretur.getText()+"','"+Hargaretur.getText()+"','"+Satuanbar.getText()+"','"+Subtotal.getText()+"'",
                             "nama_brng='"+nmbar.getText()+"',satuan='"+Satuanbar.getText()+"',h_piutang='0',jml_piutang='0',h_retur='"+Hargaretur.getText()+
                             "',jml_retur='"+Jmlretur.getText()+"',subtotal='"+Subtotal.getText()+"'",
                             "kode_brng='"+Kdbar.getText()+"' and nota_piutang='"+NoFaktur.getText()+"'");
            emptTeks();            
            tampil();
        }
}//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnTambahActionPerformed(null);
        }else{
            Valid.pindah(evt,Hargaretur, BtnHapus);
        }
}//GEN-LAST:event_BtnTambahKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(nmbar.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.hapusTable(tabMode,Kdbar," tampreturpiutang","nota_piutang='"+NoFaktur.getText()+"' and kode_brng");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnTambah, BtnCari);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));    
        form.isCek();
        form.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){            
            dispose();              
        }else{Valid.pindah(evt,BtnBatal,Kdbar);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtgActionPerformed
       var.setform("DlgReturPiutang");
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        form.petugas.emptTeks();
        form.petugas.isCek();
        form.petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.petugas.setLocationRelativeTo(internalFrame1);
        form.petugas.setAlwaysOnTop(false);
        form.petugas.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPtgActionPerformed

    private void TglReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglReturKeyPressed
        Valid.pindah(evt,NoRetur,Kdptg);
    }//GEN-LAST:event_TglReturKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRetur.getText().trim().equals("")){
            Valid.textKosong(NoRetur,"No.Retur");
        }else if(Nmptg.getText().trim().equals("")){
            Valid.textKosong(Kdptg,"Petugas");
        }else if(nmgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Lokasi");
        }else if(nmmem.getText().trim().equals("")){
            Valid.textKosong(kdmem,"Member");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            Kdbar.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if(Sequel.menyimpantf("returpiutang","'"+NoRetur.getText()+"','"+Valid.SetTgl(TglRetur.getSelectedItem()+"")+"','"+Kdptg.getText()+"','"+kdmem.getText()+"','"+kdgudang.getText()+"'","data")==true){
                    try {
                        ps=koneksi.prepareStatement("select  nota_piutang,kode_brng,satuan,jml_piutang,h_piutang,jml_retur,h_retur,subtotal from  tampreturpiutang ");
                        try {
                            rs=ps.executeQuery();
                            while(rs.next()){
                                if(Sequel.menyimpantf("detreturpiutang","'"+NoRetur.getText()+"','"+rs.getString(1) +"','"+rs.getString(2) +"','"+rs.getString(3) +
                                    "','"+rs.getString(4)+"','"+rs.getString(5)+"','"+rs.getString(6)+"','"+rs.getString(7)+"','"+rs.getString(8)+"'","data")==true){
                                    Trackobat.catatRiwayat(rs.getString(2),rs.getDouble(6),0,"Retur Piutang",var.getkode(),kdgudang.getText(),"Simpan");
                                    Sequel.menyimpan("gudangbarang","'"+rs.getString(2)+"','"+kdgudang.getText()+"','"+rs.getString(6)+"'", 
                                                    "stok=stok+'"+rs.getString(6)+"'","kode_brng='"+rs.getString(2)+"' and kd_bangsal='"+kdgudang.getText()+"'");
                                }
                            }

                            Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select Retur_Piutang_Obat from set_akun")+"','RETUR PIUTANG','"+ttlretur+"','0'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select Kontra_Retur_Piutang_Obat from set_akun")+"','KAS DI TANGAN','0','"+ttlretur+"'","Rekening"); 
                            jur.simpanJurnal(NoRetur.getText(),Valid.SetTgl(TglRetur.getSelectedItem()+""),"U","RETUR PIUTANG DI "+nmgudang.getText().toUpperCase());

                            BtnBatalActionPerformed(evt);                
                        } catch (Exception e) {
                            System.out.println("Notif Detail : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(ps!=null){
                                ps.close();
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);                
                    }
                }                    
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Kdbar,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        Sequel.queryu("delete from  tampreturpiutang");
        tampil();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_retur_piutang,6),signed)),0) from returpiutang ","RP",6,NoRetur); 
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void NoReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoReturKeyPressed
        Valid.pindah(evt, BtnTambah, TglRetur);
    }//GEN-LAST:event_NoReturKeyPressed

    private void KdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", Nmptg,Kdptg.getText());          
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", Nmptg,Kdptg.getText());   
            TglRetur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", Nmptg,Kdptg.getText());   
            NoFaktur.requestFocus();  
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPtgActionPerformed(null);
        }
    }//GEN-LAST:event_KdptgKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, Kdbar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void NoFakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoFakturKeyPressed
        Valid.pindah(evt, Kdptg, Jmlretur);
    }//GEN-LAST:event_NoFakturKeyPressed

    private void JmlreturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JmlreturKeyPressed
        Valid.pindah(evt, NoFaktur,Hargaretur);
    }//GEN-LAST:event_JmlreturKeyPressed

    private void HargareturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HargareturKeyPressed
        Valid.pindah(evt, Jmlretur, BtnTambah);
    }//GEN-LAST:event_HargareturKeyPressed

private void KdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            try {
                    ResultSet rs=koneksi.prepareStatement(
                                 "select nama_brng,kode_sat,jualbebas,karyawan from databarang where kode_brng='"+Kdbar.getText()+"'").executeQuery();
                    while(rs.next()){
                        nmbar.setText(rs.getString(1));
                        Satuanbar.setText(rs.getString(2));
                        if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                           Hargaretur.setText(rs.getString("jualbebas")); 
                        }else if(Jenisjual.getSelectedItem().equals("Karyawan")){
                           Hargaretur.setText(rs.getString("karyawan")); 
                        }
                    }
            } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
            }            
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            try {
                    ResultSet rs=koneksi.prepareStatement(
                                 "select nama_brng,kode_sat,jualbebas,karyawan from databarang where kode_brng='"+Kdbar.getText()+"'").executeQuery();
                    while(rs.next()){
                        nmbar.setText(rs.getString(1));
                        Satuanbar.setText(rs.getString(2));
                        if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                           Hargaretur.setText(rs.getString("jualbebas")); 
                        }else if(Jenisjual.getSelectedItem().equals("Karyawan")){
                           Hargaretur.setText(rs.getString("karyawan")); 
                        }
                    }
            } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
            }
            Kdptg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try {
                    ResultSet rs=koneksi.prepareStatement(
                                 "select nama_brng,kode_sat,jualbebas,karyawan from databarang where kode_brng='"+Kdbar.getText()+"'").executeQuery();
                    while(rs.next()){
                        nmbar.setText(rs.getString(1));
                        Satuanbar.setText(rs.getString(2));
                        if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                           Hargaretur.setText(rs.getString("jualbebas")); 
                        }else if(Jenisjual.getSelectedItem().equals("Karyawan")){
                           Hargaretur.setText(rs.getString("karyawan")); 
                        }
                    }
            } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
            }
            Jmlretur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnBrgActionPerformed(null);
        }
}//GEN-LAST:event_KdbarKeyPressed

private void BtnBrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBrgActionPerformed
        var.setform("DlgReturPiutang");
        form.barang.emptTeks();
        form.barang.isCek();
        form.barang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        form.barang.setLocationRelativeTo(internalFrame1);
        form.barang.setAlwaysOnTop(false);
        form.barang.setVisible(true);
}//GEN-LAST:event_BtnBrgActionPerformed

private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());          
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());  
            TglRetur.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());  
            Kdptg.requestFocus();  
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnMmbActionPerformed(null);
        }
}//GEN-LAST:event_kdmemKeyPressed

private void BtnMmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMmbActionPerformed
        var.setform("DlgReturPiutang");
        member.emptTeks();
        member.isCek();
        member.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        member.setLocationRelativeTo(internalFrame1);
        member.setAlwaysOnTop(false);
        member.setVisible(true);
}//GEN-LAST:event_BtnMmbActionPerformed

private void JenisjualItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisjualItemStateChanged
                try {                    
                    ResultSet rs=koneksi.prepareStatement(
                                 "select nama_brng,kode_sat,ralan,kelas1,kelas2,kelas3,"+
                                 "utama,vip,vvip,beliluar,jualbebas,karyawan from "+
                                 "databarang where kode_brng='"+Kdbar.getText()+"'").executeQuery();
                    while(rs.next()){
                        nmbar.setText(rs.getString(1));
                        Satuanbar.setText(rs.getString(2));
                        
                        if(Jenisjual.getSelectedItem().equals("Jual Bebas")){
                           Hargaretur.setText(rs.getString("jualbebas")); 
                        }else if(Jenisjual.getSelectedItem().equals("Karyawan")){
                           Hargaretur.setText(rs.getString("karyawan")); 
                        }else if(Jenisjual.getSelectedItem().equals("Rawat Jalan")){
                           Hargaretur.setText(rs.getString("ralan")); 
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 1")){
                           Hargaretur.setText(rs.getString("kelas1")); 
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 2")){
                           Hargaretur.setText(rs.getString("kelas2")); 
                        }else if(Jenisjual.getSelectedItem().equals("Kelas 3")){
                           Hargaretur.setText(rs.getString("kelas3")); 
                        }else if(Jenisjual.getSelectedItem().equals("Utama/BPJS")){
                           Hargaretur.setText(rs.getString("utama")); 
                        }else if(Jenisjual.getSelectedItem().equals("VIP")){
                           Hargaretur.setText(rs.getString("vip")); 
                        }else if(Jenisjual.getSelectedItem().equals("VVIP")){
                           Hargaretur.setText(rs.getString("vvip")); 
                        }else if(Jenisjual.getSelectedItem().equals("Beli Luar")){
                           Hargaretur.setText(rs.getString("beliluar")); 
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
                }
}//GEN-LAST:event_JenisjualItemStateChanged

private void ppCetakNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCetakNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoRetur.getText().trim().equals("")){
            Valid.textKosong(NoRetur,"No.Retur");
        }else if(Nmptg.getText().trim().equals("")){
            Valid.textKosong(Kdptg,"Petugas");
        }else if(nmmem.getText().trim().equals("")){
            Valid.textKosong(kdmem,"Member");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            Kdbar.requestFocus();
        }else {               
             Valid.panggilUrl("billing/NotaApotek6.php?noretur="+NoRetur.getText()+"&nm_member="+nmmem.getText().replaceAll(" ","_")+"&tanggal="+Valid.SetTgl(TglRetur.getSelectedItem()+"")+"&petugas="+Nmptg.getText().replaceAll(" ","_"));                        
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_ppCetakNotaActionPerformed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        Kdptg.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        BtnSimpan.requestFocus();     
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        BtnGudangActionPerformed(null);
    }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
    var.setform("DlgReturPiutang");
    bangsal.isCek();
    bangsal.emptTeks();
    bangsal.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
    bangsal.setLocationRelativeTo(internalFrame1);
    bangsal.setAlwaysOnTop(false);
    bangsal.setVisible(true);
}//GEN-LAST:event_BtnGudangActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgReturPiutang dialog = new DlgReturPiutang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnBrg;
    private widget.Button BtnCari;
    private widget.Button BtnGudang;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnMmb;
    private widget.Button BtnPtg;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Hargaretur;
    private widget.ComboBox Jenisjual;
    private widget.TextBox Jmlretur;
    private widget.TextBox Kd2;
    private widget.TextBox Kdbar;
    private widget.TextBox Kdptg;
    private widget.Label LTotal;
    private widget.TextBox Nmptg;
    private widget.TextBox NoFaktur;
    private widget.TextBox NoRetur;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox Satuanbar;
    private widget.TextBox Subtotal;
    private widget.Tanggal TglRetur;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdgudang;
    private widget.TextBox kdmem;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label21;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label32;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.TextBox nmgudang;
    private widget.TextBox nmmem;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisiBeli;
    private javax.swing.JMenuItem ppCetakNota;
    private javax.swing.JMenuItem ppHapus;
    private javax.swing.JMenuItem ppTambah;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select  nota_piutang, kode_brng, nama_brng, satuan, h_piutang, jml_piutang, h_retur, jml_retur, subtotal from  tampreturpiutang ");
            ttlretur=0;
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    ttlretur=ttlretur+rs.getDouble(9);
                    tabMode.addRow(new Object[]{rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   df2.format(rs.getDouble(7)),
                                   rs.getString(8),
                                   df2.format(rs.getDouble(9))});
                }                  
                LTotal.setText(df2.format(ttlretur));
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
        
    }

    public void emptTeks() {
        Kdbar.setText("");
        nmbar.setText("");
        Satuanbar.setText("");  
        Hargaretur.setText("0");
        Jmlretur.setText("0");
        Subtotal.setText("0");
        Kdbar.requestFocus();        
    }

    private void getData() {
       int row=tbDokter.getSelectedRow();
        if(row!= -1){
             NoFaktur.setText(tabMode.getValueAt(row,0).toString());
             Kdbar.setText(tabMode.getValueAt(row,1).toString());
             nmbar.setText(tabMode.getValueAt(row,2).toString());
             Satuanbar.setText(tabMode.getValueAt(row,3).toString());
             Hargaretur.setText(tabMode.getValueAt(row,4).toString().replaceAll(",",""));
             Jmlretur.setText(tabMode.getValueAt(row,5).toString());
             isHitung();
        }
    }
   
    
    private void isHitung(){
        if((!Jmlretur.getText().equals(""))&&(!Hargaretur.getText().equals(""))){
            Subtotal.setText(Double.toString(Double.parseDouble(Jmlretur.getText())*Double.parseDouble(Hargaretur.getText())));                        
        }else{
            Subtotal.setText("0");
        }        
    }
    
     public void isCek(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_retur_piutang,6),signed)),0) from returpiutang ","RP",6,NoRetur); 
        Sequel.cariIsi("select kd_bangsal from set_lokasi",kdgudang);
        Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?",nmgudang,kdgudang.getText());
        if(var.getjml2()>=1){
            Kdptg.setEditable(false);
            BtnPtg.setEnabled(false);
            Kdptg.setText(var.getkode());
            BtnSimpan.setEnabled(var.getretur_piutang_pasien());
            BtnTambah.setEnabled(var.getretur_piutang_pasien());
            BtnHapus.setEnabled(var.getretur_piutang_pasien());
            BtnBatal.setEnabled(var.getretur_piutang_pasien());
            Sequel.cariIsi("select nama from petugas where nip='"+Kdptg.getText()+"'", Nmptg);
        }        
    }
    
    

 
}
