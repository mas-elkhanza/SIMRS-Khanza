

package perpustakaan;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public class PerpustakaanSirkulasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String inventariscari="",tglcari="";
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private PerpustakaanInventaris perpustakaan_inventaris=new PerpustakaanInventaris(null,false);
    private PerpustakaanAnggota anggota=new PerpustakaanAnggota(null,false);
    private int pilihan=0;

    /** Creates new form DlgKamarInap
     * @param parent
     * @param modal */
    public PerpustakaanSirkulasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Inventaris","Kode Koleksi","Judul Koleksi","Penerbit","Pengarang","Terbit","ISBN",
                "Kategori Koleksi","Jenis Koleksi","No.Anggota","Nama Anggota","Tgl.Pinjam","Tgl.Kembali","Petugas"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamIn.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKamIn.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamIn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 14; i++) {
            TableColumn column = tbKamIn.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(65);
            }else if(i==12){
                column.setPreferredWidth(65);
            }else if(i==13){
                column.setPreferredWidth(150);
            }
        }
        tbKamIn.setDefaultRenderer(Object.class, new WarnaTable());

        
        TNoA.setDocument(new batasInput((byte)10).getFilter(TNoA));
        TNIP.setDocument(new batasInput((byte)20).getKata(TNIP));
        TNoI.setDocument(new batasInput((byte)20).getKata(TNoI));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        InventarisCari.setDocument(new batasInput((byte)100).getKata(InventarisCari));
        
        WindowInput.setSize(735,245);
        WindowInput.setLocationRelativeTo(null);  
        
        perpustakaan_inventaris.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(perpustakaan_inventaris.getTable().getSelectedRow()!= -1){                   
                    if(pilihan==1){
                        TNoI.setText(perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),0).toString());
                        TJudul.setText(perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),1).toString()+", "+perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),2).toString());
                        Penerbit.setText(perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),4).toString());
                        Pengarang.setText(perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),8).toString());
                        StatusBuku.setText(perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),12).toString());                        
                        TNoI.requestFocus();
                    }else if(pilihan==2){
                        InventarisCari.setText(perpustakaan_inventaris.getTable().getValueAt(perpustakaan_inventaris.getTable().getSelectedRow(),2).toString());
                        InventarisCari.requestFocus();
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
        
        perpustakaan_inventaris.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    perpustakaan_inventaris.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    TNIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    TNmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }   
                TNIP.requestFocus();
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
        
        anggota.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(anggota.getTable().getSelectedRow()!= -1){                   
                    TNoA.setText(anggota.getTable().getValueAt(anggota.getTable().getSelectedRow(),0).toString());
                    TNmA.setText(anggota.getTable().getValueAt(anggota.getTable().getSelectedRow(),1).toString());
                    lblTglKdl.setText(anggota.getTable().getValueAt(anggota.getTable().getSelectedRow(),9).toString());
                }   
                TNoA.requestFocus();
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
        
        anggota.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    anggota.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
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
        
    }

    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        BtnCloseIn = new widget.Button();
        jLabel19 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        TNoI = new widget.TextBox();
        label1 = new widget.Label();
        StatusBuku = new widget.TextBox();
        Penerbit = new widget.TextBox();
        LblTgl = new widget.Label();
        TJudul = new widget.TextBox();
        btnInv = new widget.Button();
        Pengarang = new widget.TextBox();
        label11 = new widget.Label();
        label6 = new widget.Label();
        TNIP = new widget.TextBox();
        label8 = new widget.Label();
        TNmPetugas = new widget.TextBox();
        btnPtg = new widget.Button();
        label12 = new widget.Label();
        tgl = new widget.Tanggal();
        LblHari = new widget.Label();
        BtnPerpanjang = new widget.Button();
        LblTerlambat = new widget.Label();
        LblJmlT = new widget.Label();
        LblDenda = new widget.Label();
        LblJmlDenda = new widget.Label();
        btnAng = new widget.Button();
        TNmA = new widget.TextBox();
        TNoA = new widget.TextBox();
        label9 = new widget.Label();
        TOut = new widget.TextBox();
        TIn = new widget.TextBox();
        LHrIni = new javax.swing.JLabel();
        LblSetPnjm = new javax.swing.JLabel();
        LblPnjm = new javax.swing.JLabel();
        lblIjnKdl = new javax.swing.JLabel();
        lblTglKdl = new javax.swing.JLabel();
        internalFrame1 = new widget.InternalFrame();
        PanelCariUtama = new javax.swing.JPanel();
        panelGlass10 = new widget.panelisi();
        BtnIn = new widget.Button();
        BtnOut = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass11 = new widget.panelisi();
        jLabel21 = new widget.Label();
        InventarisCari = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelCari = new widget.panelisi();
        jLabel17 = new widget.Label();
        StatusCari = new widget.ComboBox();
        ChkTanggal = new widget.CekBox();
        TglPinjam1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        TglPinjam2 = new widget.Tanggal();
        Scroll = new widget.ScrollPane();
        tbKamIn = new widget.Table();

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Transaki Peminjaman & Pengembalian Koleksi Perpustakaan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('U');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+U");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(620, 195, 100, 30);

        jLabel19.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame2.add(jLabel19);
        jLabel19.setBounds(-10, 175, 850, 14);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
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
        BtnSimpan.setBounds(14, 195, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Batal");
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
        BtnBatal.setBounds(117, 195, 100, 30);

        TNoI.setName("TNoI"); // NOI18N
        TNoI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoIKeyPressed(evt);
            }
        });
        internalFrame2.add(TNoI);
        TNoI.setBounds(103, 25, 130, 23);

        label1.setText("No.Inventaris :");
        label1.setName("label1"); // NOI18N
        internalFrame2.add(label1);
        label1.setBounds(0, 25, 100, 23);

        StatusBuku.setEditable(false);
        StatusBuku.setName("StatusBuku"); // NOI18N
        internalFrame2.add(StatusBuku);
        StatusBuku.setBounds(103, 85, 240, 23);

        Penerbit.setEditable(false);
        Penerbit.setName("Penerbit"); // NOI18N
        internalFrame2.add(Penerbit);
        Penerbit.setBounds(450, 55, 235, 23);

        LblTgl.setText("Tgl.Pinjam :");
        LblTgl.setName("LblTgl"); // NOI18N
        internalFrame2.add(LblTgl);
        LblTgl.setBounds(367, 85, 80, 23);

        TJudul.setEditable(false);
        TJudul.setName("TJudul"); // NOI18N
        internalFrame2.add(TJudul);
        TJudul.setBounds(235, 25, 450, 23);

        btnInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInv.setMnemonic('1');
        btnInv.setToolTipText("Alt+1");
        btnInv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInv.setName("btnInv"); // NOI18N
        btnInv.setPreferredSize(new java.awt.Dimension(100, 30));
        btnInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvActionPerformed(evt);
            }
        });
        internalFrame2.add(btnInv);
        btnInv.setBounds(690, 25, 25, 23);

        Pengarang.setEditable(false);
        Pengarang.setName("Pengarang"); // NOI18N
        internalFrame2.add(Pengarang);
        Pengarang.setBounds(103, 55, 240, 23);

        label11.setText("Pengarang :");
        label11.setName("label11"); // NOI18N
        internalFrame2.add(label11);
        label11.setBounds(0, 55, 100, 23);

        label6.setText("Status Inv :");
        label6.setName("label6"); // NOI18N
        internalFrame2.add(label6);
        label6.setBounds(0, 85, 100, 23);

        TNIP.setName("TNIP"); // NOI18N
        TNIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNIPKeyPressed(evt);
            }
        });
        internalFrame2.add(TNIP);
        TNIP.setBounds(103, 145, 130, 23);

        label8.setText("Petugas :");
        label8.setName("label8"); // NOI18N
        internalFrame2.add(label8);
        label8.setBounds(0, 145, 100, 23);

        TNmPetugas.setEditable(false);
        TNmPetugas.setName("TNmPetugas"); // NOI18N
        internalFrame2.add(TNmPetugas);
        TNmPetugas.setBounds(235, 145, 450, 23);

        btnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPtg.setMnemonic('3');
        btnPtg.setToolTipText("Alt+3");
        btnPtg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPtg.setName("btnPtg"); // NOI18N
        btnPtg.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPtgActionPerformed(evt);
            }
        });
        internalFrame2.add(btnPtg);
        btnPtg.setBounds(690, 145, 25, 23);

        label12.setText("Penerbit :");
        label12.setName("label12"); // NOI18N
        internalFrame2.add(label12);
        label12.setBounds(367, 55, 80, 23);

        tgl.setForeground(new java.awt.Color(50, 70, 50));
        tgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2019" }));
        tgl.setDisplayFormat("dd-MM-yyyy");
        tgl.setName("tgl"); // NOI18N
        tgl.setOpaque(false);
        tgl.setPreferredSize(new java.awt.Dimension(90, 23));
        tgl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tglItemStateChanged(evt);
            }
        });
        tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tglKeyPressed(evt);
            }
        });
        internalFrame2.add(tgl);
        tgl.setBounds(450, 85, 90, 23);

        LblHari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblHari.setText("Hari");
        LblHari.setName("LblHari"); // NOI18N
        internalFrame2.add(LblHari);
        LblHari.setBounds(658, 85, 30, 23);

        BtnPerpanjang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/19.png"))); // NOI18N
        BtnPerpanjang.setMnemonic('B');
        BtnPerpanjang.setText("Perpanjang");
        BtnPerpanjang.setToolTipText("Alt+B");
        BtnPerpanjang.setName("BtnPerpanjang"); // NOI18N
        BtnPerpanjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerpanjangActionPerformed(evt);
            }
        });
        BtnPerpanjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerpanjangKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnPerpanjang);
        BtnPerpanjang.setBounds(220, 195, 115, 30);

        LblTerlambat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblTerlambat.setText("Keterlambatan :");
        LblTerlambat.setName("LblTerlambat"); // NOI18N
        internalFrame2.add(LblTerlambat);
        LblTerlambat.setBounds(549, 85, 210, 23);

        LblJmlT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblJmlT.setText("0");
        LblJmlT.setName("LblJmlT"); // NOI18N
        internalFrame2.add(LblJmlT);
        LblJmlT.setBounds(631, 85, 27, 23);

        LblDenda.setText("Besarnya Denda : Rp.");
        LblDenda.setName("LblDenda"); // NOI18N
        internalFrame2.add(LblDenda);
        LblDenda.setBounds(340, 198, 150, 23);

        LblJmlDenda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblJmlDenda.setText("0");
        LblJmlDenda.setName("LblJmlDenda"); // NOI18N
        internalFrame2.add(LblJmlDenda);
        LblJmlDenda.setBounds(493, 198, 120, 23);

        btnAng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAng.setMnemonic('3');
        btnAng.setToolTipText("Alt+3");
        btnAng.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAng.setName("btnAng"); // NOI18N
        btnAng.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAngActionPerformed(evt);
            }
        });
        internalFrame2.add(btnAng);
        btnAng.setBounds(690, 115, 25, 23);

        TNmA.setEditable(false);
        TNmA.setName("TNmA"); // NOI18N
        internalFrame2.add(TNmA);
        TNmA.setBounds(235, 115, 450, 23);

        TNoA.setName("TNoA"); // NOI18N
        TNoA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoAKeyPressed(evt);
            }
        });
        internalFrame2.add(TNoA);
        TNoA.setBounds(103, 115, 130, 23);

        label9.setText("Peminjam :");
        label9.setName("label9"); // NOI18N
        internalFrame2.add(label9);
        label9.setBounds(0, 115, 100, 23);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        TOut.setEditable(false);
        TOut.setForeground(new java.awt.Color(255, 255, 255));
        TOut.setHighlighter(null);
        TOut.setName("TOut"); // NOI18N
        TOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOutKeyPressed(evt);
            }
        });

        TIn.setEditable(false);
        TIn.setForeground(new java.awt.Color(255, 255, 255));
        TIn.setHighlighter(null);
        TIn.setName("TIn"); // NOI18N
        TIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInKeyPressed(evt);
            }
        });

        LHrIni.setText("jLabel4");
        LHrIni.setName("LHrIni"); // NOI18N

        LblSetPnjm.setForeground(new java.awt.Color(255, 255, 255));
        LblSetPnjm.setText("0");
        LblSetPnjm.setName("LblSetPnjm"); // NOI18N

        LblPnjm.setForeground(new java.awt.Color(255, 255, 255));
        LblPnjm.setText("0");
        LblPnjm.setName("LblPnjm"); // NOI18N

        lblIjnKdl.setForeground(new java.awt.Color(255, 255, 255));
        lblIjnKdl.setText("0");
        lblIjnKdl.setName("lblIjnKdl"); // NOI18N

        lblTglKdl.setForeground(new java.awt.Color(255, 255, 255));
        lblTglKdl.setText("0");
        lblTglKdl.setName("lblTglKdl"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Peminjaman Koleksi perpustakaan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 143));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        BtnIn.setMnemonic('M');
        BtnIn.setText("Pinjam");
        BtnIn.setToolTipText("Alt+M");
        BtnIn.setName("BtnIn"); // NOI18N
        BtnIn.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInActionPerformed(evt);
            }
        });
        BtnIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnInKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnIn);

        BtnOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/36.png"))); // NOI18N
        BtnOut.setMnemonic('K');
        BtnOut.setText("Kembali");
        BtnOut.setToolTipText("Alt+K");
        BtnOut.setName("BtnOut"); // NOI18N
        BtnOut.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOutActionPerformed(evt);
            }
        });
        BtnOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOutKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnOut);

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
        panelGlass10.add(BtnHapus);

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
        panelGlass10.add(BtnPrint);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass10.add(LCount);

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
        panelGlass10.add(BtnKeluar);

        PanelCariUtama.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel21.setText("Koleksi :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass11.add(jLabel21);

        InventarisCari.setName("InventarisCari"); // NOI18N
        InventarisCari.setPreferredSize(new java.awt.Dimension(200, 23));
        InventarisCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InventarisCariKeyPressed(evt);
            }
        });
        panelGlass11.add(InventarisCari);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        panelGlass11.add(BtnSeek2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass11.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass11.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        panelGlass11.add(BtnCari);

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
        panelGlass11.add(BtnAll);

        PanelCariUtama.add(panelGlass11, java.awt.BorderLayout.CENTER);

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel17.setText("Stts.Pinjam :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(77, 23));
        panelCari.add(jLabel17);

        StatusCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Masih Dipinjam", "Sudah Kembali" }));
        StatusCari.setName("StatusCari"); // NOI18N
        StatusCari.setPreferredSize(new java.awt.Dimension(175, 23));
        panelCari.add(StatusCari);

        ChkTanggal.setSelected(true);
        ChkTanggal.setText("Tgl.Pinjam :");
        ChkTanggal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkTanggal.setName("ChkTanggal"); // NOI18N
        ChkTanggal.setPreferredSize(new java.awt.Dimension(135, 23));
        panelCari.add(ChkTanggal);

        TglPinjam1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2019" }));
        TglPinjam1.setDisplayFormat("dd-MM-yyyy");
        TglPinjam1.setName("TglPinjam1"); // NOI18N
        TglPinjam1.setOpaque(false);
        TglPinjam1.setPreferredSize(new java.awt.Dimension(100, 23));
        TglPinjam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPinjam1KeyPressed(evt);
            }
        });
        panelCari.add(TglPinjam1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(35, 23));
        panelCari.add(jLabel22);

        TglPinjam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2019" }));
        TglPinjam2.setDisplayFormat("dd-MM-yyyy");
        TglPinjam2.setName("TglPinjam2"); // NOI18N
        TglPinjam2.setOpaque(false);
        TglPinjam2.setPreferredSize(new java.awt.Dimension(100, 23));
        TglPinjam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPinjam2KeyPressed(evt);
            }
        });
        panelCari.add(TglPinjam2);

        PanelCariUtama.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamIn.setAutoCreateRowSorter(true);
        tbKamIn.setName("tbKamIn"); // NOI18N
        tbKamIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamInMouseClicked(evt);
            }
        });
        tbKamIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamInKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbKamInKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbKamIn);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInActionPerformed
        LblTgl.setText("Tgl.Pinjam :");
        LblJmlT.setVisible(false);
        LblHari.setVisible(false);
        LblDenda.setVisible(false);
        LblJmlDenda.setVisible(false);
        TNoA.setEditable(true);
        TNoI.setEditable(true);
        btnInv.setEnabled(true);
        BtnPerpanjang.setVisible(false);
        btnAng.setEnabled(true);
        isDay();
        emptTeks();
        WindowInput.setAlwaysOnTop(false);
        WindowInput.setVisible(true);
}//GEN-LAST:event_BtnInActionPerformed

    private void BtnInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnInActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnOut);
        }
}//GEN-LAST:event_BtnInKeyPressed

    private void BtnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOutActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Tabel kosong. Tidak ada data yang bisa Anda edit..!!!!");
            BtnIn.requestFocus();
        }else if(Penerbit.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data perpustakaan_peminjaman yang akan dikembalikan dengan menklik data pada tabel...!!!");
            tbKamIn.requestFocus();
        }else if(! TOut.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Inventaris sudah dikembalikan pada tanggal "+TOut.getText()+"...!!!");
            tbKamIn.requestFocus();
        }else if((! Penerbit.getText().trim().equals(""))&&(TOut.getText().trim().equals(""))){
            TNoI.setEditable(false);
            TNoA.setEditable(false);
            LblTgl.setText("Tgl.Kembali :");
            LblJmlT.setVisible(true);
            LblHari.setVisible(true);
            LblDenda.setVisible(true);
            LblJmlDenda.setVisible(true);
            BtnPerpanjang.setVisible(true);
            LblTerlambat.setText("Keterlambatan :");
            btnInv.setEnabled(false);
            btnAng.setEnabled(false);
            isDaySub();
            WindowInput.setVisible(true);
            tgl.requestFocus();
        }
}//GEN-LAST:event_BtnOutActionPerformed

    private void BtnOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOutKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnOutActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnIn,BtnHapus);
        }
}//GEN-LAST:event_BtnOutKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TNoI.requestFocus();
        }else if(TJudul.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(TOut.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Data sirkulasi dengan No.Inventaris "+TNoI.getText()+" belum kembali. Data belum bisa anda hapus...!!!!");
        }else if((! TJudul.getText().trim().equals(""))&&(! TOut.getText().trim().equals(""))){
            try{
                Sequel.queryu("delete from perpustakaan_peminjaman where no_anggota='"+TNoA.getText()+"' and no_inventaris='"+TNoI.getText()+"' and tgl_pinjam='"+TIn.getText()+"'");
                tampil();
            }catch(Exception e){
                System.out.println("Pesan Error : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }

        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnOut,BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInput.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
                inventariscari="";
                tglcari="";
                
                if(ChkTanggal.isSelected()==true){
                    tglcari=" perpustakaan_peminjaman.tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and ";
                }
                
                if(!InventarisCari.getText().equals("")){
                    inventariscari="perpustakaan_buku.judul_buku='"+InventarisCari.getText()+"' and ";
                }

                Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReportqry("rptPeminjamanPerpustakaan.jasper","report","::[ Data Peminjaman Koleksi Perpustakaan ]::","select perpustakaan_peminjaman.no_inventaris,perpustakaan_inventaris.kode_buku,perpustakaan_buku.judul_buku,perpustakaan_penerbit.nama_penerbit,"+
                       "perpustakaan_pengarang.nama_pengarang,perpustakaan_buku.thn_terbit,perpustakaan_buku.isbn,perpustakaan_kategori.nama_kategori,"+
                       "perpustakaan_jenis_buku.nama_jenis,perpustakaan_peminjaman.no_anggota,perpustakaan_anggota.nama_anggota,perpustakaan_peminjaman.tgl_pinjam,"+
                       "perpustakaan_peminjaman.tgl_kembali,petugas.nama from perpustakaan_peminjaman inner join perpustakaan_inventaris inner join perpustakaan_buku "+
                       "inner join perpustakaan_penerbit inner join perpustakaan_pengarang inner join perpustakaan_kategori inner join perpustakaan_jenis_buku "+
                       "inner join petugas inner join perpustakaan_anggota on perpustakaan_peminjaman.no_inventaris=perpustakaan_inventaris.no_inventaris "+
                       "and perpustakaan_buku.kode_buku=perpustakaan_inventaris.kode_buku and perpustakaan_peminjaman.no_anggota=perpustakaan_anggota.no_anggota "+
                       "and perpustakaan_buku.kode_penerbit=perpustakaan_penerbit.kode_penerbit and perpustakaan_buku.kode_pengarang=perpustakaan_pengarang.kode_pengarang "+
                       "and perpustakaan_buku.id_kategori=perpustakaan_kategori.id_kategori and perpustakaan_buku.id_jenis=perpustakaan_jenis_buku.id_jenis "+
                       "and petugas.nip=perpustakaan_peminjaman.nip where "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_peminjaman.no_inventaris like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_buku.kode_buku like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_buku.judul_buku like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_penerbit.nama_penerbit like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_pengarang.nama_pengarang like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_buku.thn_terbit like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_buku.isbn like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_jenis_buku.nama_jenis like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_anggota.nama_anggota like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_peminjaman.no_anggota like '%"+TCari.getText().trim()+"%' "+
                       " order by perpustakaan_peminjaman.tgl_pinjam",param);

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

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        InventarisCari.setText("");
        StatusCari.setSelectedItem("Semua");
        ChkTanggal.setSelected(false);
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnIn);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
     // Valid.pindah(evt,kdkamar,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        emptTeks();
        WindowInput.dispose();
    }//GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal, TNoI);}
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        isSetPinjam();
        if(LblSetPnjm.getText().trim().equals("")||LblSetPnjm.getText().trim().equals("0")){
            Valid.textKosong(TNoI,"Setup Peminjaman");
        }else if(TNoI.getText().trim().equals("")||TJudul.getText().trim().equals("")){
            Valid.textKosong(TNoI,"Inventaris");
        }else if(TNoA.getText().trim().equals("")||TNmA.getText().trim().equals("")){
            Valid.textKosong(TNoA,"Anggota");
        }else if(TNIP.getText().trim().equals("")||TNmPetugas.getText().trim().equals("")){
            Valid.textKosong(TNIP,"Petugas");
        }else {
            if(TNoI.isEditable()==true){
                if(lblIjnKdl.getText().trim().equals("tidak")){
                    JOptionPane.showMessageDialog(null,"Maaf, keanggotaan dengan No.Anggota "+TNoA.getText()+" telah habis pada tanggal "+lblTglKdl.getText()+".\nSilahkan anda perpanjang keanggotaan dulu ..!!!");
                    TNoI.requestFocus();
                }else if(LblPnjm.getText().trim().equals("tidak")){
                    JOptionPane.showMessageDialog(null,"Maaf, maksimal peminjaman "+LblSetPnjm.getText()+"...!!!");
                    TNoI.requestFocus();
                }else if(! StatusBuku.getText().trim().equals("Ada")){
                    JOptionPane.showMessageDialog(null,"Maaf, Koleksi "+StatusBuku.getText()+"...!!!");
                    TNoI.requestFocus();
                }else if(StatusBuku.getText().trim().equals("Ada")){
                    if(Sequel.menyimpantf("perpustakaan_peminjaman","?,?,?,?,?,?","Peminjaman",6,new String[]{
                            TNoA.getText(),TNoI.getText(),Valid.SetTgl(tgl.getSelectedItem()+""),"0000-00-00",TNIP.getText(),"Masih Dipinjam"
                        })==true){
                            Sequel.queryu("update perpustakaan_inventaris set status_buku='Dipinjam' where no_inventaris='"+TNoI.getText()+"'");
                            TNoI.requestFocus();
                            TNoI.setText("");
                            TJudul.setText("");
                            Pengarang.setText("");
                            Penerbit.setText("");
                            StatusBuku.setText("");
                    }
                }
            }else if(TNoI.isEditable()==false){
                Sequel.queryu("update perpustakaan_peminjaman set tgl_kembali='"+Valid.SetTgl(tgl.getSelectedItem()+"")+"',nip='"+TNIP.getText()+"',status_pinjam='Sudah Kembali' where no_anggota='"+TNoA.getText()+"' and no_inventaris='"+TNoI.getText()+"' and tgl_pinjam='"+TIn.getText()+"'");
                Sequel.queryu("update perpustakaan_inventaris set status_buku='Ada' where no_inventaris='"+TNoI.getText()+"'");
                emptTeks();
                WindowInput.dispose();
            }
            tampil();
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        //Valid.pindah(evt,cmbDtk,BtnBatal);
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(TNoI.isEditable()==true){
            emptTeks();
        }else if(TNoI.isEditable()==false){
            emptTeks();
            WindowInput.dispose();
        }
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void TInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TInKeyPressed

    private void DTPTglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTglItemStateChanged
        
    }//GEN-LAST:event_DTPTglItemStateChanged

    private void TglPinjam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam1KeyPressed
        Valid.pindah(evt,InventarisCari,TglPinjam2);
}//GEN-LAST:event_TglPinjam1KeyPressed

    private void TglPinjam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam2KeyPressed
        Valid.pindah(evt,TglPinjam1,InventarisCari);
}//GEN-LAST:event_TglPinjam2KeyPressed

    private void TOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOutKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_TOutKeyPressed

    private void tbKamInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamInMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKamInMouseClicked

    private void tbKamInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.requestFocus();
            }                    
        }
}//GEN-LAST:event_tbKamInKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
    pilihan=2;
    perpustakaan_inventaris.isCek();
    perpustakaan_inventaris.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    perpustakaan_inventaris.setLocationRelativeTo(internalFrame1);
    perpustakaan_inventaris.setAlwaysOnTop(false);
    perpustakaan_inventaris.setVisible(true);     
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   Valid.pindah(evt,TglPinjam2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void InventarisCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InventarisCariKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            tampil();
        }else{Valid.pindah(evt, TglPinjam2, TCari);}
}//GEN-LAST:event_InventarisCariKeyPressed

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    tampil();
}//GEN-LAST:event_formWindowOpened

private void TNoIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoIKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        isInventaris();
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        isInventaris();
        BtnCloseIn.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        isInventaris();
        //peminjam.requestFocus();
    } else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnInvActionPerformed(null);
    }
}//GEN-LAST:event_TNoIKeyPressed

private void btnInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvActionPerformed
    pilihan=1;
    perpustakaan_inventaris.isCek();
    perpustakaan_inventaris.tampil();
    perpustakaan_inventaris.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    perpustakaan_inventaris.setLocationRelativeTo(internalFrame1);
    perpustakaan_inventaris.setAlwaysOnTop(false);
    perpustakaan_inventaris.setVisible(true);        
}//GEN-LAST:event_btnInvActionPerformed

private void TNIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNIPKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select nama from petugas where nip=?",TNmPetugas,TNIP.getText());
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select nama from petugas where nip=?",TNmPetugas,TNIP.getText());
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnPtgActionPerformed(null);
    }
}//GEN-LAST:event_TNIPKeyPressed

private void btnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPtgActionPerformed
    petugas.isCek();
    petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    petugas.setLocationRelativeTo(internalFrame1);
    petugas.setAlwaysOnTop(false);
    petugas.setVisible(true);
}//GEN-LAST:event_btnPtgActionPerformed

private void tglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tglKeyPressed
    Valid.pindah(evt,InventarisCari,TglPinjam2);
}//GEN-LAST:event_tglKeyPressed

    private void tbKamInKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }                  
        }
    }//GEN-LAST:event_tbKamInKeyReleased

    private void BtnPerpanjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerpanjangActionPerformed
        if(TNoI.getText().trim().equals("")||TJudul.getText().trim().equals("")){
            Valid.textKosong(TNoI,"Inventaris");
        }else if(TNoA.getText().trim().equals("")||TNmA.getText().trim().equals("")){
            Valid.textKosong(TNoI,"Anggota");
        }else if(TNIP.getText().trim().equals("")||TNmPetugas.getText().trim().equals("")){
            Valid.textKosong(TNoI,"Petugas");
        }else {
            if(TNoI.isEditable()==false){
                Sequel.queryu("update perpustakaan_peminjaman set tgl_pinjam='"+Valid.SetTgl(tgl.getSelectedItem()+"")+"',nip='"+TNIP.getText()+"' where no_anggota='"+TNoA.getText()+"' and no_inventaris='"+TNoI.getText()+"' and tgl_pinjam='"+TIn.getText()+"'");
                WindowInput.dispose();
            }
            tampil();
            emptTeks();
        }
    }//GEN-LAST:event_BtnPerpanjangActionPerformed

    private void BtnPerpanjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerpanjangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPerpanjangKeyPressed

    private void btnAngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAngActionPerformed
        anggota.isCek();
        anggota.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        anggota.setLocationRelativeTo(internalFrame1);
        anggota.setAlwaysOnTop(false);
        anggota.setVisible(true);  
    }//GEN-LAST:event_btnAngActionPerformed

    private void TNoAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoAKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TNIP.requestFocus();
            Sequel.cariIsi("select nama_anggota from perpustakaan_anggota where no_anggota='"+TNoA.getText()+"'", TNmA);
            Sequel.cariIsi("select masa_berlaku from perpustakaan_anggota where no_anggota='"+TNoA.getText()+"'", lblTglKdl);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            TNoI.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAngActionPerformed(null);
        }
    }//GEN-LAST:event_TNoAKeyPressed

    private void tglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tglItemStateChanged
        if(TNoI.isEditable()==true){
            isDay();
        }else if(TNoI.isEditable()==false){
            isDaySub();
        }
    }//GEN-LAST:event_tglItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PerpustakaanSirkulasi dialog = new PerpustakaanSirkulasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn;
    private widget.Button BtnHapus;
    private widget.Button BtnIn;
    private widget.Button BtnKeluar;
    private widget.Button BtnOut;
    private widget.Button BtnPerpanjang;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkTanggal;
    private widget.TextBox InventarisCari;
    private widget.Label LCount;
    private javax.swing.JLabel LHrIni;
    private widget.Label LblDenda;
    private widget.Label LblHari;
    private widget.Label LblJmlDenda;
    private widget.Label LblJmlT;
    private javax.swing.JLabel LblPnjm;
    private javax.swing.JLabel LblSetPnjm;
    private widget.Label LblTerlambat;
    private widget.Label LblTgl;
    private javax.swing.JPanel PanelCariUtama;
    private widget.TextBox Penerbit;
    private widget.TextBox Pengarang;
    private widget.ScrollPane Scroll;
    private widget.TextBox StatusBuku;
    private widget.ComboBox StatusCari;
    private widget.TextBox TCari;
    private widget.TextBox TIn;
    private widget.TextBox TJudul;
    private widget.TextBox TNIP;
    private widget.TextBox TNmA;
    private widget.TextBox TNmPetugas;
    private widget.TextBox TNoA;
    private widget.TextBox TNoI;
    private widget.TextBox TOut;
    private widget.Tanggal TglPinjam1;
    private widget.Tanggal TglPinjam2;
    private javax.swing.JDialog WindowInput;
    private widget.Button btnAng;
    private widget.Button btnInv;
    private widget.Button btnPtg;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel6;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label6;
    private widget.Label label8;
    private widget.Label label9;
    private javax.swing.JLabel lblIjnKdl;
    private javax.swing.JLabel lblTglKdl;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.Table tbKamIn;
    private widget.Tanggal tgl;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            inventariscari="";
            tglcari="";
            if(ChkTanggal.isSelected()==true){
                tglcari=" perpustakaan_peminjaman.tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and ";
            }

            if(!InventarisCari.getText().equals("")){
                inventariscari="perpustakaan_buku.judul_buku='"+InventarisCari.getText()+"' and ";
            }
            ps=koneksi.prepareStatement(
                    "select perpustakaan_peminjaman.no_inventaris,perpustakaan_inventaris.kode_buku,perpustakaan_buku.judul_buku,perpustakaan_penerbit.nama_penerbit,"+
                       "perpustakaan_pengarang.nama_pengarang,perpustakaan_buku.thn_terbit,perpustakaan_buku.isbn,perpustakaan_kategori.nama_kategori,"+
                       "perpustakaan_jenis_buku.nama_jenis,perpustakaan_peminjaman.no_anggota,perpustakaan_anggota.nama_anggota,perpustakaan_peminjaman.tgl_pinjam,"+
                       "perpustakaan_peminjaman.tgl_kembali,petugas.nama from perpustakaan_peminjaman inner join perpustakaan_inventaris inner join perpustakaan_buku "+
                       "inner join perpustakaan_penerbit inner join perpustakaan_pengarang inner join perpustakaan_kategori inner join perpustakaan_jenis_buku "+
                       "inner join petugas inner join perpustakaan_anggota on perpustakaan_peminjaman.no_inventaris=perpustakaan_inventaris.no_inventaris "+
                       "and perpustakaan_buku.kode_buku=perpustakaan_inventaris.kode_buku and perpustakaan_peminjaman.no_anggota=perpustakaan_anggota.no_anggota "+
                       "and perpustakaan_buku.kode_penerbit=perpustakaan_penerbit.kode_penerbit and perpustakaan_buku.kode_pengarang=perpustakaan_pengarang.kode_pengarang "+
                       "and perpustakaan_buku.id_kategori=perpustakaan_kategori.id_kategori and perpustakaan_buku.id_jenis=perpustakaan_jenis_buku.id_jenis "+
                       "and petugas.nip=perpustakaan_peminjaman.nip where "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_peminjaman.no_inventaris like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_buku.kode_buku like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_buku.judul_buku like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_penerbit.nama_penerbit like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_pengarang.nama_pengarang like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_buku.thn_terbit like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_buku.isbn like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_kategori.nama_kategori like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_jenis_buku.nama_jenis like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_anggota.nama_anggota like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                       inventariscari+" perpustakaan_peminjaman.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and "+tglcari+" perpustakaan_peminjaman.no_anggota like '%"+TCari.getText().trim()+"%' "+
                       " order by perpustakaan_peminjaman.tgl_pinjam");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_inventaris"),rs.getString("kode_buku"),rs.getString("judul_buku"),rs.getString("nama_penerbit"),
                        rs.getString("nama_pengarang"),rs.getString("thn_terbit").substring(0,4),rs.getString("isbn"),rs.getString("nama_kategori"),
                        rs.getString("nama_jenis"),rs.getString("no_anggota"),rs.getString("nama_anggota"),rs.getString("tgl_pinjam"),
                        rs.getString("tgl_kembali"),rs.getString("nama")
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
        TNoI.setText("");
        TJudul.setText("");
        Pengarang.setText("");
        Penerbit.setText("");
        StatusBuku.setText("");
        TNoA.setText("");
        TNmA.setText("");
        TOut.setText("");
        TIn.setText("");
        tgl.setDate(new Date());
        TNoI.requestFocus();
    }

    private void getData() {
        TOut.setText("");
        TIn.setText("");
        if(tbKamIn.getSelectedRow()!= -1){
            TNoI.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString());
            TJudul.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),1).toString()+", "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),2).toString());
            Penerbit.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),3).toString());
            Pengarang.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),4).toString());
            StatusBuku.setText("Dipinjam");
            TNoA.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),9).toString());
            TNmA.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),10).toString());
            TIn.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),11).toString());
            TOut.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),12).toString());            
        }
    }

    
    public void isCek(){
        if(akses.getjml2()>=1){
            TNIP.setEditable(false);
            btnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.getpeminjaman_perpustakaan());
            BtnIn.setEnabled(akses.getpeminjaman_perpustakaan());
            BtnOut.setEnabled(akses.getpeminjaman_perpustakaan());
            TNIP.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", TNmPetugas,TNIP.getText());
        } 
    }
    
    private void isDay(){
        Sequel.cariIsi("select date_add('"+Valid.SetTgl(tgl.getSelectedItem()+"")+"',interval (select lama_pinjam from perpustakaan_set_peminjaman) day) as hari",LHrIni);
        LblTerlambat.setText("Harus Kembali : "+LHrIni.getText());
    }

    private void isDaySub(){
        Sequel.cariIsi("select TO_DAYS('"+Valid.SetTgl(tgl.getSelectedItem()+"")+"')-TO_DAYS(date_add('"+TIn.getText()+"',interval (select lama_pinjam from perpustakaan_set_peminjaman) day)) as day",LHrIni);         
        Sequel.cariIsi("select if("+LHrIni.getText()+" >0,"+LHrIni.getText()+",0)", LblJmlT);
        Sequel.cariIsi("select ifnull("+LblJmlT.getText()+"*(select denda_perhari from perpustakaan_set_peminjaman),0)", LblJmlDenda);
    }

    public void isSetPinjam(){
        Sequel.cariIsi("select max_pinjam from perpustakaan_set_peminjaman", LblSetPnjm);
        Sequel.cariIsi("select if(count(no_anggota)<'"+LblSetPnjm.getText()+"','boleh','tidak') as jml "+
                       "from perpustakaan_peminjaman where no_anggota='"+TNoA.getText()+"' and status_pinjam ='Masih Dipinjam'", LblPnjm);
        Sequel.cariIsi("select if('"+lblTglKdl.getText()+"'>'"+Valid.SetTgl(tgl.getSelectedItem()+"")+"','boleh','tidak') as jml "+
                       "from perpustakaan_anggota where no_anggota='"+TNoA.getText()+"' ", lblIjnKdl);
    }
    
    public void isInventaris(){
        try {
            ps=koneksi.prepareStatement(
               "select perpustakaan_inventaris.no_inventaris,perpustakaan_buku.kode_buku, perpustakaan_buku.judul_buku, "+
                "perpustakaan_penerbit.nama_penerbit, perpustakaan_pengarang.nama_pengarang,perpustakaan_inventaris.status_buku "+
                "from perpustakaan_inventaris inner join perpustakaan_buku inner join perpustakaan_penerbit inner join perpustakaan_pengarang "+
                "on perpustakaan_buku.kode_penerbit=perpustakaan_penerbit.kode_penerbit and perpustakaan_buku.kode_pengarang=perpustakaan_pengarang.kode_pengarang "+
                "and perpustakaan_buku.kode_buku=perpustakaan_inventaris.kode_buku where perpustakaan_inventaris.no_inventaris=?");
            try{
                ps.setString(1,TNoI.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TJudul.setText(rs.getString("kode_buku")+", "+rs.getString("judul_buku"));
                    Penerbit.setText(rs.getString("nama_penerbit"));
                    Pengarang.setText(rs.getString("nama_pengarang"));
                    StatusBuku.setText(rs.getString("status_buku"));
                }else{
                    TJudul.setText("");
                    Penerbit.setText("");
                    Pengarang.setText("");
                    StatusBuku.setText("");
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
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
}
