

package laporan;
import kepegawaian.DlgCariPetugas;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventaris.InventarisRuang;
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
import simrskhanza.DlgPasien;

/**
 *
 * @author perpustakaan
 */
public class DlgSirkulasiBerkas extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pspasien,ps;
    private ResultSet rs;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private InventarisRuang ruang=new InventarisRuang(null,false); 

    /** Creates new form DlgKamarInap
     * @param parent
     * @param modal */
    public DlgSirkulasiBerkas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"Peminjam","Id Ruang","Nama Ruang","No.R.M.","Nama Pasien","Tgl.Pinjam","Tgl.Kembali","Kode Petugas","Nama Petugas"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamIn.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKamIn.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamIn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbKamIn.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(150);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(170);
            }
        }
        tbKamIn.setDefaultRenderer(Object.class, new WarnaTable());

        peminjam.setDocument(new batasInput((byte)60).getKata(peminjam));
        Nip.setDocument(new batasInput((byte)20).getKata(Nip));
        KdRuang.setDocument(new batasInput((byte)5).getKata(KdRuang));
        NoRm.setDocument(new batasInput((byte)10).getKata(NoRm));
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
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){                   
                    if(pilihan==1){                           
                           try {
                                NoRm.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                                NmPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),2).toString());
                                Umur.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),15).toString());
                                Alamat.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),8).toString());
                                Pekerjaan.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),10).toString());   
                                PertamaDaftar.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),13).toString());                       
                           } catch (Exception z) {
                           }                           
                           NoRm.requestFocus();
                    }else if(pilihan==2){
                        RmCari.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                        RmCari.requestFocus();
                    }
                } 
                if(pasien.getTable2().getSelectedRow()!= -1){                   
                    if(pilihan==1){                           
                           try {
                                NoRm.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),1).toString());
                                NmPasien.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),2).toString());
                                Umur.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),15).toString());
                                Alamat.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),8).toString());
                                Pekerjaan.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),10).toString());   
                                PertamaDaftar.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),13).toString());                       
                           } catch (Exception z) {
                           }                           
                           NoRm.requestFocus();
                    }else if(pilihan==2){
                        RmCari.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(),1).toString());
                        RmCari.requestFocus();
                    }
                } 
                if(pasien.getTable3().getSelectedRow()!= -1){                   
                    if(pilihan==1){                           
                           try {
                                NoRm.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),1).toString());
                                NmPasien.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),2).toString());
                                Umur.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),15).toString());
                                Alamat.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),8).toString());
                                Pekerjaan.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),10).toString());   
                                PertamaDaftar.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),13).toString());                       
                           } catch (Exception z) {
                           }                           
                           NoRm.requestFocus();
                    }else if(pilihan==2){
                        RmCari.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(),1).toString());
                        RmCari.requestFocus();
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
        pasien.getTable2().addKeyListener(new KeyListener() {
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
        pasien.getTable3().addKeyListener(new KeyListener() {
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    Nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }   
                Nip.requestFocus();
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
        
        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(ruang.getTable().getSelectedRow()!= -1){ 
                    KdRuang.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),0).toString());                    
                    NmRuang.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(),1).toString());
                    KdRuang.requestFocus();
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
        
        ruang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    ruang.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        WindowInput.setSize(735,275);
        WindowInput.setLocationRelativeTo(null);         
        
    }

    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgPasien pasien=new DlgPasien(null,false);
    private int pilihan=0;

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
        NoRm = new widget.TextBox();
        label1 = new widget.Label();
        Pekerjaan = new widget.TextBox();
        Umur = new widget.TextBox();
        peminjam = new widget.TextBox();
        LblTgl = new widget.Label();
        label7 = new widget.Label();
        NmPasien = new widget.TextBox();
        btnInv = new widget.Button();
        Alamat = new widget.TextBox();
        label11 = new widget.Label();
        PertamaDaftar = new widget.TextBox();
        label6 = new widget.Label();
        Nip = new widget.TextBox();
        label8 = new widget.Label();
        NmPetugas = new widget.TextBox();
        btnPtg = new widget.Button();
        label12 = new widget.Label();
        Tanggal = new widget.Tanggal();
        LblTgl1 = new widget.Label();
        label9 = new widget.Label();
        KdRuang = new widget.TextBox();
        NmRuang = new widget.TextBox();
        btnRuang = new widget.Button();
        TOut = new widget.TextBox();
        TIn = new widget.TextBox();
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
        RmCari = new widget.TextBox();
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

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Transaki Peminjaman & Pengembalian Berkas Rekam Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        BtnCloseIn.setBounds(620, 225, 100, 30);

        jLabel19.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame2.add(jLabel19);
        jLabel19.setBounds(-10, 203, 850, 14);

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
        BtnSimpan.setBounds(14, 225, 100, 30);

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
        BtnBatal.setBounds(117, 225, 100, 30);

        NoRm.setName("NoRm"); // NOI18N
        NoRm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRmKeyPressed(evt);
            }
        });
        internalFrame2.add(NoRm);
        NoRm.setBounds(113, 25, 130, 23);

        label1.setText("No.Rekam Medis :");
        label1.setName("label1"); // NOI18N
        internalFrame2.add(label1);
        label1.setBounds(10, 25, 100, 23);

        Pekerjaan.setEditable(false);
        Pekerjaan.setName("Pekerjaan"); // NOI18N
        internalFrame2.add(Pekerjaan);
        Pekerjaan.setBounds(113, 85, 320, 23);

        Umur.setEditable(false);
        Umur.setName("Umur"); // NOI18N
        internalFrame2.add(Umur);
        Umur.setBounds(560, 55, 120, 23);

        peminjam.setName("peminjam"); // NOI18N
        peminjam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                peminjamKeyPressed(evt);
            }
        });
        internalFrame2.add(peminjam);
        peminjam.setBounds(113, 115, 320, 23);

        LblTgl.setText("Tgl.Pinjam :");
        LblTgl.setName("LblTgl"); // NOI18N
        internalFrame2.add(LblTgl);
        LblTgl.setBounds(457, 115, 100, 23);

        label7.setText("Peminjam Berkas :");
        label7.setName("label7"); // NOI18N
        internalFrame2.add(label7);
        label7.setBounds(10, 115, 100, 23);

        NmPasien.setEditable(false);
        NmPasien.setName("NmPasien"); // NOI18N
        internalFrame2.add(NmPasien);
        NmPasien.setBounds(245, 25, 435, 23);

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
        btnInv.setBounds(683, 25, 25, 23);

        Alamat.setEditable(false);
        Alamat.setName("Alamat"); // NOI18N
        internalFrame2.add(Alamat);
        Alamat.setBounds(113, 55, 320, 23);

        label11.setText("Alamat :");
        label11.setName("label11"); // NOI18N
        internalFrame2.add(label11);
        label11.setBounds(10, 55, 100, 23);

        PertamaDaftar.setEditable(false);
        PertamaDaftar.setName("PertamaDaftar"); // NOI18N
        internalFrame2.add(PertamaDaftar);
        PertamaDaftar.setBounds(560, 85, 120, 23);

        label6.setText("Pekerjaan:");
        label6.setName("label6"); // NOI18N
        internalFrame2.add(label6);
        label6.setBounds(10, 85, 100, 23);

        Nip.setName("Nip"); // NOI18N
        Nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NipKeyPressed(evt);
            }
        });
        internalFrame2.add(Nip);
        Nip.setBounds(113, 175, 130, 23);

        label8.setText("Petugas :");
        label8.setName("label8"); // NOI18N
        internalFrame2.add(label8);
        label8.setBounds(10, 175, 100, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        internalFrame2.add(NmPetugas);
        NmPetugas.setBounds(245, 175, 435, 23);

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
        btnPtg.setBounds(683, 175, 25, 23);

        label12.setText("Umur :");
        label12.setName("label12"); // NOI18N
        internalFrame2.add(label12);
        label12.setBounds(457, 55, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-05-2019" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        internalFrame2.add(Tanggal);
        Tanggal.setBounds(560, 115, 120, 23);

        LblTgl1.setText("Pertama Daftar :");
        LblTgl1.setName("LblTgl1"); // NOI18N
        internalFrame2.add(LblTgl1);
        LblTgl1.setBounds(457, 85, 100, 23);

        label9.setText("Ruang Peminjam :");
        label9.setName("label9"); // NOI18N
        internalFrame2.add(label9);
        label9.setBounds(10, 145, 100, 23);

        KdRuang.setName("KdRuang"); // NOI18N
        KdRuang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRuangKeyPressed(evt);
            }
        });
        internalFrame2.add(KdRuang);
        KdRuang.setBounds(113, 145, 130, 23);

        NmRuang.setEditable(false);
        NmRuang.setName("NmRuang"); // NOI18N
        internalFrame2.add(NmRuang);
        NmRuang.setBounds(245, 145, 435, 23);

        btnRuang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRuang.setMnemonic('3');
        btnRuang.setToolTipText("Alt+3");
        btnRuang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnRuang.setName("btnRuang"); // NOI18N
        btnRuang.setPreferredSize(new java.awt.Dimension(100, 30));
        btnRuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuangActionPerformed(evt);
            }
        });
        internalFrame2.add(btnRuang);
        btnRuang.setBounds(683, 145, 25, 23);

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Peminjaman & Pengembalian Berkas Rekam Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jLabel21.setText("No.R.M. :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass11.add(jLabel21);

        RmCari.setName("RmCari"); // NOI18N
        RmCari.setPreferredSize(new java.awt.Dimension(100, 23));
        RmCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RmCariKeyPressed(evt);
            }
        });
        panelGlass11.add(RmCari);

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

        jLabel6.setText("Keyword :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass11.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
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

        TglPinjam1.setForeground(new java.awt.Color(50, 70, 50));
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

        TglPinjam2.setForeground(new java.awt.Color(50, 70, 50));
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
        });
        Scroll.setViewportView(tbKamIn);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInActionPerformed
        LblTgl.setText("Tgl.Pinjam :");
        NoRm.setEditable(true);
        peminjam.setEditable(true);
        KdRuang.setEditable(true);
        btnInv.setEnabled(true);
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
        if(tbKamIn.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, Tabel kosong. Tidak ada data yang bisa Anda edit..!!!!");
            BtnIn.requestFocus();
        }else if(NmPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data peminjaman_berkas yang akan dikembalikan dengan menklik data pada tabel...!!!");
            tbKamIn.requestFocus();
        }else if(! TOut.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Berkas sudah dikembalikan pada tanggal "+tbKamIn.getValueAt(tbKamIn.getSelectedRow(),4).toString()+"...!!!");
            tbKamIn.requestFocus();
        }else {
            NoRm.setEditable(false);
            peminjam.setEditable(false);
            KdRuang.setEditable(false);
            LblTgl.setText("Tgl.Kembali :");
            btnInv.setEnabled(false);
            WindowInput.setVisible(true);
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
       if(tbKamIn.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            NoRm.requestFocus();
        }else if(NmPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(TOut.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Data berkas dengan No.R.M. "+NoRm.getText()+" belum kembali. Data belum bisa anda hapus...!!!!");
        }else{
            Sequel.queryu2("delete from peminjaman_berkas where nip=? and peminjam=? and no_rkm_medis=? and tgl_pinjam=? and id_ruang=?",5,new String[]{
                tbKamIn.getValueAt(tbKamIn.getSelectedRow(),7).toString(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString(),
                tbKamIn.getValueAt(tbKamIn.getSelectedRow(),3).toString(),TIn.getText(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),1).toString()
            });
            tampil();
            emptTeks();            
        }
        
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
        if(tbKamIn.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tbKamIn.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                if(ChkTanggal.isSelected()==true){
                    Valid.MyReportqry("rptSirkulasiBerkas.jasper","report","::[ Data Peminjaman Dan Pengembalian Berkas Rekam Medis ]::",
                        "select peminjaman_berkas.peminjam,peminjaman_berkas.id_ruang,inventaris_ruang.nama_ruang,"+
                        "peminjaman_berkas.no_rkm_medis,pasien.nm_pasien,peminjaman_berkas.tgl_pinjam,"+
                        "peminjaman_berkas.tgl_kembali,peminjaman_berkas.nip,petugas.nama,peminjaman_berkas.status_pinjam from peminjaman_berkas inner join inventaris_ruang "+
                        "inner join pasien inner join petugas on peminjaman_berkas.no_rkm_medis=pasien.no_rkm_medis and peminjaman_berkas.nip=petugas.nip and inventaris_ruang.id_ruang=peminjaman_berkas.id_ruang "+
                        "where peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and peminjaman_berkas.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+                    
                        " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and peminjaman_berkas.nip like '%"+TCari.getText().trim()+"%' or "+
                        " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                        " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and peminjaman_berkas.id_ruang like '%"+TCari.getText().trim()+"%' or "+
                        " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and inventaris_ruang.nama_ruang like '%"+TCari.getText().trim()+"%' or "+
                        " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and tgl_pinjam between '"+Valid.SetTgl(TglPinjam1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(TglPinjam2.getSelectedItem()+"")+"' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and peminjaman_berkas.peminjam like '%"+TCari.getText().trim()+"%' order by peminjaman_berkas.tgl_pinjam desc ",param);
                }else{
                    Valid.MyReportqry("rptSirkulasiBerkas.jasper","report","::[ Data Peminjaman Dan Pengembalian Berkas Rekam Medis ]::",
                    "select peminjaman_berkas.peminjam,peminjaman_berkas.id_ruang,inventaris_ruang.nama_ruang,"+
                    "peminjaman_berkas.no_rkm_medis,pasien.nm_pasien,peminjaman_berkas.tgl_pinjam,"+
                    "peminjaman_berkas.tgl_kembali,peminjaman_berkas.nip,petugas.nama,peminjaman_berkas.status_pinjam from peminjaman_berkas inner join inventaris_ruang "+
                    "inner join pasien inner join petugas on peminjaman_berkas.no_rkm_medis=pasien.no_rkm_medis and peminjaman_berkas.nip=petugas.nip and inventaris_ruang.id_ruang=peminjaman_berkas.id_ruang "+
                    "where peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and peminjaman_berkas.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+                    
                    " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and peminjaman_berkas.nip like '%"+TCari.getText().trim()+"%' or "+
                    " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                    " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and peminjaman_berkas.id_ruang like '%"+TCari.getText().trim()+"%' or "+
                    " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and inventaris_ruang.nama_ruang like '%"+TCari.getText().trim()+"%' or "+
                    " peminjaman_berkas.status_pinjam like '%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%' and peminjaman_berkas.no_rkm_medis like '%"+RmCari.getText()+"%' and peminjaman_berkas.peminjam like '%"+TCari.getText().trim()+"%' order by peminjaman_berkas.tgl_pinjam desc ",param);
                }
                

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
        RmCari.setText("");
        ChkTanggal.setSelected(false);
        StatusCari.setSelectedItem("Semua");
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
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal, NoRm);}
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRm.getText().trim().equals("")||NmPasien.getText().trim().equals("")){
            Valid.textKosong(NoRm,"Berkas Pasien");
        }else if(peminjam.getText().trim().equals("")){
            Valid.textKosong(peminjam,"Peminjam");
        }else if(Nip.getText().trim().equals("")||NmPetugas.getText().trim().equals("")){
            Valid.textKosong(Nip,"Petugas");
        }else if(KdRuang.getText().trim().equals("")||NmRuang.getText().trim().equals("")){
            Valid.textKosong(KdRuang,"Ruang Peminjam");
        }else {
           if(NoRm.isEditable()==true){
                if(Sequel.cariInteger("select count(no_rkm_medis) from peminjaman_berkas where status_pinjam='Masih Dipinjam' and no_rkm_medis=?",NoRm.getText())>0){
                    JOptionPane.showMessageDialog(null,"Maaf, berkas rekam medis "+NmPasien.getText()+" masih dipinjam...!!!");
                    NoRm.requestFocus();
                }else{
                    //menyimpan data peminjaman berkas
                    Sequel.menyimpan("peminjaman_berkas","?,?,?,?,?,?,?",7,new String[]{
                        peminjam.getText(),KdRuang.getText(),NoRm.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),"0000-00-00",Nip.getText(),"Masih Dipinjam"
                    });
                    NoRm.requestFocus();  
                    emptTeks();
                }
            }else if(NoRm.isEditable()==false){
                //mengedit-------------------------------------------------
                Sequel.mengedit("peminjaman_berkas","nip=? and peminjam=? and no_rkm_medis=? and tgl_pinjam=? and id_ruang=?","tgl_kembali=?,nip=?,status_pinjam=?",8,new String[]{
                    Valid.SetTgl(Tanggal.getSelectedItem()+""),Nip.getText(),"Sudah Kembali",
                    tbKamIn.getValueAt(tbKamIn.getSelectedRow(),7).toString(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString(),
                    tbKamIn.getValueAt(tbKamIn.getSelectedRow(),3).toString(),TIn.getText(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),1).toString()
                });
                emptTeks();
                WindowInput.dispose();
            }
           
            tampil();
            
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{Valid.pindah(evt,Nip,BtnBatal);}
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(NoRm.isEditable()==true){
            emptTeks();
        }else if(NoRm.isEditable()==false){
            emptTeks();
            WindowInput.dispose();
        }
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void DTPTglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTglItemStateChanged
        
    }//GEN-LAST:event_DTPTglItemStateChanged

    private void TglPinjam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam1KeyPressed
        Valid.pindah(evt,RmCari,TglPinjam2);
}//GEN-LAST:event_TglPinjam1KeyPressed

    private void TglPinjam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPinjam2KeyPressed
        Valid.pindah(evt,TglPinjam1,RmCari);
}//GEN-LAST:event_TglPinjam2KeyPressed

    private void tbKamInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamInMouseClicked
        if(tbKamIn.getRowCount()!=0){
            try {                
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKamInMouseClicked

    private void tbKamInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyPressed
        if(tbKamIn.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
                TCari.requestFocus();
            }                    
        }
}//GEN-LAST:event_tbKamInKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        pilihan=2;
    pasien.isCek();
    pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    pasien.setLocationRelativeTo(internalFrame1);
    pasien.setAlwaysOnTop(false);
    pasien.setVisible(true);     
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   Valid.pindah(evt,TglPinjam2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void RmCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RmCariKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            tampil();
        }else{Valid.pindah(evt, TglPinjam2, TCari);}
}//GEN-LAST:event_RmCariKeyPressed

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    tampil();
}//GEN-LAST:event_formWindowOpened

private void NoRmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRmKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        isPasien();
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        isPasien();
        BtnCloseIn.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        isPasien();
        peminjam.requestFocus();
    } else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnInvActionPerformed(null);
    }
}//GEN-LAST:event_NoRmKeyPressed

private void peminjamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_peminjamKeyPressed
        Valid.pindah(evt,NoRm,Tanggal);
}//GEN-LAST:event_peminjamKeyPressed

private void btnInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvActionPerformed
    pilihan=1;
    pasien.isCek();
    pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    pasien.setLocationRelativeTo(internalFrame1);
    pasien.setAlwaysOnTop(false);
    pasien.setVisible(true);        
}//GEN-LAST:event_btnInvActionPerformed

private void NipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NipKeyPressed
   if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select nama from petugas where nip=?",NmPetugas,Nip.getText());
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select nama from petugas where nip=?",NmPetugas,Nip.getText());
        Tanggal.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select nama from petugas where nip=?",NmPetugas,Nip.getText());
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        btnPtgActionPerformed(null);
    }
}//GEN-LAST:event_NipKeyPressed

private void btnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPtgActionPerformed
    petugas.isCek();
    petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    petugas.setLocationRelativeTo(internalFrame1);
    petugas.setAlwaysOnTop(false);
    petugas.setVisible(true);
}//GEN-LAST:event_btnPtgActionPerformed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
    Valid.pindah(evt,peminjam,KdRuang);
}//GEN-LAST:event_TanggalKeyPressed

    private void TOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TOutKeyPressed

    private void TInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TInKeyPressed

    private void KdRuangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRuangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select inventaris_ruang.nama_ruang from inventaris_ruang where inventaris_ruang.id_ruang=?",NmRuang,KdRuang.getText());        
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Tanggal.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Nip.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnRuangActionPerformed(null);
        }
    }//GEN-LAST:event_KdRuangKeyPressed

    private void btnRuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuangActionPerformed
        ruang.isCek();
        ruang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setAlwaysOnTop(false);
        ruang.setVisible(true);
    }//GEN-LAST:event_btnRuangActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSirkulasiBerkas dialog = new DlgSirkulasiBerkas(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn;
    private widget.Button BtnHapus;
    private widget.Button BtnIn;
    private widget.Button BtnKeluar;
    private widget.Button BtnOut;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkTanggal;
    private widget.TextBox KdRuang;
    private widget.Label LCount;
    private widget.Label LblTgl;
    private widget.Label LblTgl1;
    private widget.TextBox Nip;
    private widget.TextBox NmPasien;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmRuang;
    private widget.TextBox NoRm;
    private javax.swing.JPanel PanelCariUtama;
    private widget.TextBox Pekerjaan;
    private widget.TextBox PertamaDaftar;
    private widget.TextBox RmCari;
    private widget.ScrollPane Scroll;
    private widget.ComboBox StatusCari;
    private widget.TextBox TCari;
    private widget.TextBox TIn;
    private widget.TextBox TOut;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TglPinjam1;
    private widget.Tanggal TglPinjam2;
    private widget.TextBox Umur;
    private javax.swing.JDialog WindowInput;
    private widget.Button btnInv;
    private widget.Button btnPtg;
    private widget.Button btnRuang;
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
    private widget.Label label7;
    private widget.Label label8;
    private widget.Label label9;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.TextBox peminjam;
    private widget.Table tbKamIn;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        try{
            Valid.tabelKosong(tabMode);
            if(ChkTanggal.isSelected()==true){
                ps=koneksi.prepareStatement(
                    "select peminjaman_berkas.peminjam,peminjaman_berkas.id_ruang,inventaris_ruang.nama_ruang,peminjaman_berkas.no_rkm_medis,pasien.nm_pasien,peminjaman_berkas.tgl_pinjam,"+
                    "peminjaman_berkas.tgl_kembali,peminjaman_berkas.nip,petugas.nama,peminjaman_berkas.status_pinjam from peminjaman_berkas inner join pasien inner join petugas inner join inventaris_ruang "+
                    "on peminjaman_berkas.no_rkm_medis=pasien.no_rkm_medis and peminjaman_berkas.nip=petugas.nip and inventaris_ruang.id_ruang=peminjaman_berkas.id_ruang "+
                    "where peminjaman_berkas.status_pinjam like ? and tgl_pinjam between ? and ? and peminjaman_berkas.no_rkm_medis like ? and peminjaman_berkas.no_rkm_medis like ? or "+                    
                    " peminjaman_berkas.status_pinjam like ? and tgl_pinjam between ? and ? and peminjaman_berkas.no_rkm_medis like ? and pasien.nm_pasien like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and tgl_pinjam between ? and ? and peminjaman_berkas.no_rkm_medis like ? and peminjaman_berkas.nip like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and tgl_pinjam between ? and ? and peminjaman_berkas.no_rkm_medis like ? and petugas.nama like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and tgl_pinjam between ? and ? and peminjaman_berkas.no_rkm_medis like ? and peminjaman_berkas.id_ruang like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and tgl_pinjam between ? and ? and peminjaman_berkas.no_rkm_medis like ? and inventaris_ruang.nama_ruang like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and tgl_pinjam between ? and ? and peminjaman_berkas.no_rkm_medis like ? and peminjaman_berkas.peminjam like ? order by peminjaman_berkas.tgl_pinjam desc ");
            }else{
                ps=koneksi.prepareStatement(
                    "select peminjaman_berkas.peminjam,peminjaman_berkas.id_ruang,inventaris_ruang.nama_ruang,peminjaman_berkas.no_rkm_medis,pasien.nm_pasien,peminjaman_berkas.tgl_pinjam,"+
                    "peminjaman_berkas.tgl_kembali,peminjaman_berkas.nip,petugas.nama,peminjaman_berkas.status_pinjam from peminjaman_berkas inner join pasien inner join petugas inner join inventaris_ruang "+
                    "on peminjaman_berkas.no_rkm_medis=pasien.no_rkm_medis and peminjaman_berkas.nip=petugas.nip and inventaris_ruang.id_ruang=peminjaman_berkas.id_ruang "+
                    "where peminjaman_berkas.status_pinjam like ? and peminjaman_berkas.no_rkm_medis like ? and peminjaman_berkas.no_rkm_medis like ? or "+                    
                    " peminjaman_berkas.status_pinjam like ? and peminjaman_berkas.no_rkm_medis like ? and pasien.nm_pasien like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and peminjaman_berkas.no_rkm_medis like ? and peminjaman_berkas.nip like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and peminjaman_berkas.no_rkm_medis like ? and petugas.nama like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and peminjaman_berkas.no_rkm_medis like ? and peminjaman_berkas.id_ruang like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and peminjaman_berkas.no_rkm_medis like ? and inventaris_ruang.nama_ruang like ? or "+
                    " peminjaman_berkas.status_pinjam like ? and peminjaman_berkas.no_rkm_medis like ? and peminjaman_berkas.peminjam like ? order by peminjaman_berkas.tgl_pinjam desc ");
            }
            
            try {
                if(ChkTanggal.isSelected()==true){
                    ps.setString(1,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(2,Valid.SetTgl(TglPinjam1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(TglPinjam2.getSelectedItem()+""));
                    ps.setString(4,"%"+RmCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(7,Valid.SetTgl(TglPinjam1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(TglPinjam2.getSelectedItem()+""));
                    ps.setString(9,"%"+RmCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(12,Valid.SetTgl(TglPinjam1.getSelectedItem()+""));
                    ps.setString(13,Valid.SetTgl(TglPinjam2.getSelectedItem()+""));
                    ps.setString(14,"%"+RmCari.getText()+"%");
                    ps.setString(15,"%"+TCari.getText().trim()+"%");
                    ps.setString(16,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(17,Valid.SetTgl(TglPinjam1.getSelectedItem()+""));
                    ps.setString(18,Valid.SetTgl(TglPinjam2.getSelectedItem()+""));
                    ps.setString(19,"%"+RmCari.getText()+"%");
                    ps.setString(20,"%"+TCari.getText().trim()+"%");
                    ps.setString(21,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(22,Valid.SetTgl(TglPinjam1.getSelectedItem()+""));
                    ps.setString(23,Valid.SetTgl(TglPinjam2.getSelectedItem()+""));
                    ps.setString(24,"%"+RmCari.getText()+"%");
                    ps.setString(25,"%"+TCari.getText().trim()+"%");
                    ps.setString(26,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(27,Valid.SetTgl(TglPinjam1.getSelectedItem()+""));
                    ps.setString(28,Valid.SetTgl(TglPinjam2.getSelectedItem()+""));
                    ps.setString(29,"%"+RmCari.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(32,Valid.SetTgl(TglPinjam1.getSelectedItem()+""));
                    ps.setString(33,Valid.SetTgl(TglPinjam2.getSelectedItem()+""));
                    ps.setString(34,"%"+RmCari.getText()+"%");
                    ps.setString(35,"%"+TCari.getText().trim()+"%");
                }else{
                    ps.setString(1,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(2,"%"+RmCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(5,"%"+RmCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(8,"%"+RmCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(11,"%"+RmCari.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(14,"%"+RmCari.getText()+"%");
                    ps.setString(15,"%"+TCari.getText().trim()+"%");
                    ps.setString(16,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(17,"%"+RmCari.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,"%"+StatusCari.getSelectedItem().toString().replaceAll("Semua","")+"%");
                    ps.setString(20,"%"+RmCari.getText()+"%");
                    ps.setString(21,"%"+TCari.getText().trim()+"%");
                }
                
                System.out.println("laporan.DlgSirkulasiBerkas.tampil() : "+ChkTanggal.isSelected());
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("peminjam"),rs.getString("id_ruang"),rs.getString("nama_ruang"),
                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_pinjam"),
                        rs.getString("tgl_kembali"),rs.getString("nip"),rs.getString("nama"),rs.getString("status_pinjam")
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
            LCount.setText(""+tbKamIn.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    public void emptTeks() {       
        NoRm.setText("");
        NmPasien.setText("");
        Alamat.setText("");
        Umur.setText("");
        Pekerjaan.setText("");
        peminjam.setText("");
        KdRuang.setText("");
        NmRuang.setText("");
        PertamaDaftar.setText("");
        Tanggal.setDate(new Date());
        NoRm.requestFocus();
    }

    private void getData() {
        if(tbKamIn.getSelectedRow()!= -1){    
            try {
                TOut.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),6).toString());
            } catch (Exception e) {
                TOut.setText("");
            }
            KdRuang.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),1).toString());
            NmRuang.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),2).toString());
            NoRm.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),3).toString());
            peminjam.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),0).toString());           
            TIn.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(),5).toString());     
            isPasien();         
        }
    }

    
    public void isCek(){
        if(akses.getjml2()>=1){
            Nip.setEditable(false);
            btnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.getpeminjaman_berkas());
            BtnIn.setEnabled(akses.getpeminjaman_berkas());
            BtnOut.setEnabled(akses.getpeminjaman_berkas());
            Nip.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas,Nip.getText());
        } 
    }
    
    public void isPasien(){
        try {   
            pspasien=koneksi.prepareStatement("select pasien.nm_pasien,pasien.pekerjaan, "+
                   "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,"+
                   "pasien.tgl_daftar,pasien.umur from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                   "where pasien.no_rkm_medis=?");
            try {
                pspasien.setString(1,NoRm.getText());
                rs=pspasien.executeQuery();
                if(rs.next()){
                    NmPasien.setText(rs.getString("nm_pasien"));
                    Umur.setText(rs.getString("umur"));
                    Alamat.setText(rs.getString("alamat"));
                    Pekerjaan.setText(rs.getString("pekerjaan"));
                    PertamaDaftar.setText(rs.getString("tgl_daftar"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pspasien!=null){
                    pspasien.close();
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
}
