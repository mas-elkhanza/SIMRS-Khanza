package laporan;
import keuangan.Jurnal;
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
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgKabupaten;
import simrskhanza.DlgKecamatan;
import simrskhanza.DlgKelurahan;
import simrskhanza.DlgCariCaraBayar;

public class DlgFrekuensiPenyakitRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6,ps7,ps8;
    private ResultSet rs,rs2,rs3,rs4,rs5,rs6,rs7,rs8;
    private String diagnosa="";
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgKabupaten kabupaten=new DlgKabupaten(null,false);
    private DlgKecamatan kecamatan=new DlgKecamatan(null,false);
    private DlgKelurahan kelurahan=new DlgKelurahan(null,false);
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgFrekuensiPenyakitRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode","Nama Penyakit","Diagnosa Lain","Lk2(Hidup)","Pr(Hidup)","Lk2(Mati)","Pr(Mati)","Jumlah"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, java.lang.Integer.class, 
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int m = 0; m < 8; m++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(m);
            if(m==0){
                column.setPreferredWidth(60);
            }else if(m==1){
                column.setPreferredWidth(350);
            }else if(m==2){
                column.setPreferredWidth(400);
            }else{
                column.setPreferredWidth(60);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
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
    private int i=0,a=0,b=0,c=0,d=0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppGrafikTerbanyakBatang = new javax.swing.JMenuItem();
        ppGrafikTerbanyakPie = new javax.swing.JMenuItem();
        ppGrafikTerkecilBatang = new javax.swing.JMenuItem();
        ppGrafikTerkecilPie = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label9 = new widget.Label();
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

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppGrafikTerbanyakBatang.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikTerbanyakBatang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikTerbanyakBatang.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikTerbanyakBatang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerbanyakBatang.setText("Grafik Batang 10 Penyakit Terbanyak");
        ppGrafikTerbanyakBatang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerbanyakBatang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerbanyakBatang.setName("ppGrafikTerbanyakBatang"); // NOI18N
        ppGrafikTerbanyakBatang.setPreferredSize(new java.awt.Dimension(300, 26));
        ppGrafikTerbanyakBatang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerbanyakBatangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerbanyakBatang);

        ppGrafikTerbanyakPie.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikTerbanyakPie.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikTerbanyakPie.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikTerbanyakPie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerbanyakPie.setText("Grafik Pie 10 Penyakit Terbanyak");
        ppGrafikTerbanyakPie.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerbanyakPie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerbanyakPie.setName("ppGrafikTerbanyakPie"); // NOI18N
        ppGrafikTerbanyakPie.setPreferredSize(new java.awt.Dimension(300, 26));
        ppGrafikTerbanyakPie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerbanyakPieActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerbanyakPie);

        ppGrafikTerkecilBatang.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikTerkecilBatang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikTerkecilBatang.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikTerkecilBatang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerkecilBatang.setText("Grafik Batang 10 Penyakit Tersedikit");
        ppGrafikTerkecilBatang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerkecilBatang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerkecilBatang.setName("ppGrafikTerkecilBatang"); // NOI18N
        ppGrafikTerkecilBatang.setPreferredSize(new java.awt.Dimension(300, 26));
        ppGrafikTerkecilBatang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerkecilBatangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerkecilBatang);

        ppGrafikTerkecilPie.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikTerkecilPie.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikTerkecilPie.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikTerkecilPie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerkecilPie.setText("Grafik Pie 10 Penyakit Tersedikit");
        ppGrafikTerkecilPie.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerkecilPie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerkecilPie.setName("ppGrafikTerkecilPie"); // NOI18N
        ppGrafikTerkecilPie.setPreferredSize(new java.awt.Dimension(300, 26));
        ppGrafikTerkecilPie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerkecilPieActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerkecilPie);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Frekuensi Penyakit Di Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setAutoCreateRowSorter(true);
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
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi1.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(26, 23));
        panelisi1.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi1.add(Tgl2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(135, 23));
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
        panelisi1.add(BtnAll);

        label9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label9.setText("Record : 0");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(80, 30));
        panelisi1.add(label9);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

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
        label21.setBounds(449, 10, 87, 23);

        nmkabupaten.setEditable(false);
        nmkabupaten.setName("nmkabupaten"); // NOI18N
        nmkabupaten.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkabupaten);
        nmkabupaten.setBounds(539, 10, 260, 23);

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
        BtnSeek5.setBounds(802, 10, 28, 23);

        label22.setText("Kecamatan :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label22);
        label22.setBounds(449, 40, 87, 23);

        nmkecamatan.setEditable(false);
        nmkecamatan.setName("nmkecamatan"); // NOI18N
        nmkecamatan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkecamatan);
        nmkecamatan.setBounds(539, 40, 260, 23);

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
        BtnSeek6.setBounds(802, 40, 28, 23);

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
        FormInput.add(BtnSeek7);
        BtnSeek7.setBounds(802, 70, 28, 23);

        nmkelurahan.setEditable(false);
        nmkelurahan.setName("nmkelurahan"); // NOI18N
        nmkelurahan.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmkelurahan);
        nmkelurahan.setBounds(539, 70, 260, 23);

        label23.setText("Kelurahan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label23);
        label23.setBounds(449, 70, 87, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tbDokter.getRowCount()!=0){
            Sequel.queryu("truncate table temporary");
            int row=tbDokter.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tbDokter.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                tbDokter.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                tbDokter.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                tbDokter.getValueAt(r,3).toString()+"','"+
                                tbDokter.getValueAt(r,4).toString()+"','"+
                                tbDokter.getValueAt(r,5).toString()+"','"+
                                tbDokter.getValueAt(r,6).toString()+"','"+
                                tbDokter.getValueAt(r,7).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Frekuensi Penyakit"); 
            }
            Valid.panggilUrl("billing/LaporanPenyakitRalan.php?tanggal1="+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"&tanggal2="+Valid.SetTgl(Tgl2.getSelectedItem()+""));                       
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void ppGrafikTerbanyakBatangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerbanyakBatangActionPerformed
        if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())>=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())>=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,7).toString())>=Integer.parseInt(tbDokter.getValueAt(8,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(8,7).toString())>=Integer.parseInt(tbDokter.getValueAt(9,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,7).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,7).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,7).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(9,7).toString()),tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));


            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);           
            
        }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())>=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())>=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,7).toString())>=Integer.parseInt(tbDokter.getValueAt(8,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,7).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,7).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,7).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
           
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())>=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())>=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,7).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,7).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())>=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,7).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
             
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                
            
        }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
               
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                  
            
        }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
               
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                
            
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
        }        
        
    }//GEN-LAST:event_ppGrafikTerbanyakBatangActionPerformed

private void ppGrafikTerbanyakPieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerbanyakPieActionPerformed
     if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())>=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())>=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,7).toString())>=Integer.parseInt(tbDokter.getValueAt(8,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(8,7).toString())>=Integer.parseInt(tbDokter.getValueAt(9,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Integer.parseInt(tbDokter.getValueAt(6,7).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Integer.parseInt(tbDokter.getValueAt(7,7).toString()));
            dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,7).toString(),Integer.parseInt(tbDokter.getValueAt(8,7).toString()));
            dpd.setValue(tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,7).toString(),Integer.parseInt(tbDokter.getValueAt(9,7).toString()));

            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
        }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())>=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())>=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,7).toString())>=Integer.parseInt(tbDokter.getValueAt(8,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Integer.parseInt(tbDokter.getValueAt(6,7).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Integer.parseInt(tbDokter.getValueAt(7,7).toString()));
            dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,7).toString(),Integer.parseInt(tbDokter.getValueAt(8,7).toString()));
            
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())>=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())>=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Integer.parseInt(tbDokter.getValueAt(6,7).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Integer.parseInt(tbDokter.getValueAt(7,7).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())>=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Integer.parseInt(tbDokter.getValueAt(6,7).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())>=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())>=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())>=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())>=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
               
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);         
            
        }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())>=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);        
            
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
        }    
}//GEN-LAST:event_ppGrafikTerbanyakPieActionPerformed

private void ppGrafikTerkecilBatangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerkecilBatangActionPerformed
    if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())<=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())<=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,7).toString())<=Integer.parseInt(tbDokter.getValueAt(8,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(8,7).toString())<=Integer.parseInt(tbDokter.getValueAt(9,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,7).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,7).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,7).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(9,7).toString()),tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));


            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())<=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())<=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,7).toString())<=Integer.parseInt(tbDokter.getValueAt(8,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,7).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,7).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,7).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
           
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())<=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())<=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,7).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,7).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())<=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,7).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
             
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,7).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,7).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);      
            
        }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,7).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
              
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                
            
        }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,7).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
               
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                 
            
        }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))){
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,7).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
            dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,7).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
               
            JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Jalan","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan",freeChart);

            cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setVisible(true);                  
            
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
        }     
}//GEN-LAST:event_ppGrafikTerkecilBatangActionPerformed

private void ppGrafikTerkecilPieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerkecilPieActionPerformed
   if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())<=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())<=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,7).toString())<=Integer.parseInt(tbDokter.getValueAt(8,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(8,7).toString())<=Integer.parseInt(tbDokter.getValueAt(9,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Integer.parseInt(tbDokter.getValueAt(6,7).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Integer.parseInt(tbDokter.getValueAt(7,7).toString()));
            dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,7).toString(),Integer.parseInt(tbDokter.getValueAt(8,7).toString()));
            dpd.setValue(tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,7).toString(),Integer.parseInt(tbDokter.getValueAt(9,7).toString()));

            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
        }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())<=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())<=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(7,7).toString())<=Integer.parseInt(tbDokter.getValueAt(8,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Integer.parseInt(tbDokter.getValueAt(6,7).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Integer.parseInt(tbDokter.getValueAt(7,7).toString()));
            dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,7).toString(),Integer.parseInt(tbDokter.getValueAt(8,7).toString()));
            
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())<=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(6,7).toString())<=Integer.parseInt(tbDokter.getValueAt(7,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Integer.parseInt(tbDokter.getValueAt(6,7).toString()));
            dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,7).toString(),Integer.parseInt(tbDokter.getValueAt(7,7).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(5,7).toString())<=Integer.parseInt(tbDokter.getValueAt(6,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
            dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,7).toString(),Integer.parseInt(tbDokter.getValueAt(6,7).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(4,7).toString())<=Integer.parseInt(tbDokter.getValueAt(5,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
            dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,7).toString(),Integer.parseInt(tbDokter.getValueAt(5,7).toString()));
             
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(3,7).toString())<=Integer.parseInt(tbDokter.getValueAt(4,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
            dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,7).toString(),Integer.parseInt(tbDokter.getValueAt(4,7).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);
            
        }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(2,7).toString())<=Integer.parseInt(tbDokter.getValueAt(3,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
            dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,7).toString(),Integer.parseInt(tbDokter.getValueAt(3,7).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);       
            
        }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))
                && (Integer.parseInt(tbDokter.getValueAt(1,7).toString())<=Integer.parseInt(tbDokter.getValueAt(2,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
            dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,7).toString(),Integer.parseInt(tbDokter.getValueAt(2,7).toString()));
               
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);          
            
        }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,7).toString())<=Integer.parseInt(tbDokter.getValueAt(1,7).toString()))){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,7).toString(),Integer.parseInt(tbDokter.getValueAt(0,7).toString()));
            dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,7).toString(),Integer.parseInt(tbDokter.getValueAt(1,7).toString()));
              
            JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Jalan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Jalan ",freeChart);
            cf.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true);          
            
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
        }    
}//GEN-LAST:event_ppGrafikTerkecilPieActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

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
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt,kdpoli, BtnPrint);
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

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

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgFrekuensiPenyakitRalan dialog = new DlgFrekuensiPenyakitRalan(new javax.swing.JFrame(), true);
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
    private widget.TextBox Kd2;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
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
    private widget.Label label9;
    private widget.TextBox nmdokter;
    private widget.TextBox nmkabupaten;
    private widget.TextBox nmkecamatan;
    private widget.TextBox nmkelurahan;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.panelisi panelisi1;
    private javax.swing.JMenuItem ppGrafikTerbanyakBatang;
    private javax.swing.JMenuItem ppGrafikTerbanyakPie;
    private javax.swing.JMenuItem ppGrafikTerkecilBatang;
    private javax.swing.JMenuItem ppGrafikTerkecilPie;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.tabelKosong(tabMode);      
        try{ 
            ps=koneksi.prepareStatement(
                "select penyakit.kd_penyakit,SUBSTRING(penyakit.nm_penyakit,1,80) as penyakit from penyakit inner join diagnosa_pasien inner join reg_periksa "+
                "inner join dokter inner join pasien inner join poliklinik inner join penjab inner join kabupaten inner join kecamatan inner join kelurahan "+
                "on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat and reg_periksa.kd_dokter=dokter.kd_dokter " +
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli and "+
                "pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                "where reg_periksa.status_lanjut='Ralan' and diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and "+
                "(penyakit.kd_penyakit like ? or penyakit.nm_penyakit like ?) group by penyakit.kd_penyakit order by penyakit.kd_penyakit");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+nmpoli.getText().trim()+"%");
                ps.setString(4,"%"+nmdokter.getText().trim()+"%");
                ps.setString(5,"%"+nmpenjab.getText().trim()+"%");
                ps.setString(6,"%"+nmkabupaten.getText().trim()+"%");
                ps.setString(7,"%"+nmkecamatan.getText().trim()+"%");
                ps.setString(8,"%"+nmkelurahan.getText().trim()+"%");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();            
                while(rs.next()){
                    i=0;
                    ps2=koneksi.prepareStatement("select diagnosa_pasien.no_rawat as jumlah from penyakit inner join diagnosa_pasien inner join reg_periksa "+
                        "inner join dokter inner join pasien inner join poliklinik inner join penjab inner join kabupaten inner join kecamatan inner join kelurahan "+
                        "on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat and reg_periksa.kd_dokter=dokter.kd_dokter " +
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli and "+
                        "pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                        "where reg_periksa.status_lanjut='Ralan' and diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and reg_periksa.tgl_registrasi between ? and ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat");    
                    try {
                        ps2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(3,"%"+nmpoli.getText().trim()+"%");
                        ps2.setString(4,"%"+nmdokter.getText().trim()+"%");
                        ps2.setString(5,"%"+nmpenjab.getText().trim()+"%");
                        ps2.setString(6,"%"+nmkabupaten.getText().trim()+"%");
                        ps2.setString(7,"%"+nmkecamatan.getText().trim()+"%");
                        ps2.setString(8,"%"+nmkelurahan.getText().trim()+"%");
                        ps2.setString(9,rs.getString("kd_penyakit"));
                        rs2=ps2.executeQuery();
                        Sequel.queryu("delete from temporary_surveilens_penyakit");
                        while(rs2.next()){
                            ps7=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.prioritas<>'1' and diagnosa_pasien.status='Ralan' and diagnosa_pasien.no_rawat=?");    
                            try {
                                ps7.setString(1,rs2.getString(1));
                                rs7=ps7.executeQuery();
                                while(rs7.next()){
                                    Sequel.menyimpan("temporary_surveilens_penyakit","?,?",2,new String[]{
                                        rs.getString("kd_penyakit"),rs7.getString("kd_penyakit")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif ps7 : "+e);
                            } finally{
                                if(rs7!=null){
                                    rs7.close();
                                }
                                if(ps7!=null){
                                    ps7.close();
                                }
                            }
                        }
                        diagnosa="";
                        rs2.last();
                        if(rs2.getRow()>0){
                            ps8=koneksi.prepareStatement("select temporary_surveilens_penyakit.kd_penyakit2 from temporary_surveilens_penyakit where temporary_surveilens_penyakit.kd_penyakit=? group by temporary_surveilens_penyakit.kd_penyakit2");    
                            try{
                                ps8.setString(1,rs.getString("kd_penyakit"));
                                rs8=ps8.executeQuery();
                                while(rs8.next()){
                                    if(diagnosa.equals("")){
                                        diagnosa=rs8.getString(1);
                                    }else{
                                        diagnosa=diagnosa+", "+rs8.getString(1);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif ps8 : "+e);
                            } finally{
                                if(rs8!=null){
                                    rs8.close();
                                }
                                if(ps8!=null){
                                    ps8.close();
                                }
                            }                                
                            i=rs2.getRow();
                        }
                    } catch (Exception e) {
                        System.out.println("Notif ps2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }   

                    a=0;
                    ps3=koneksi.prepareStatement("select diagnosa_pasien.no_rawat as jumlah from penyakit inner join diagnosa_pasien inner join reg_periksa inner join pasien_mati "+
                        "inner join dokter inner join pasien inner join poliklinik inner join penjab inner join kabupaten inner join kecamatan inner join kelurahan "+
                        "on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat and reg_periksa.kd_dokter=dokter.kd_dokter " +
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli and "+
                        "pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel and pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                        "where reg_periksa.status_lanjut='Ralan' and diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and pasien.jk='L' and reg_periksa.tgl_registrasi between ? and ? "+
                        "and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? "+
                        "and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat");  
                    try{
                        ps3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps3.setString(3,"%"+nmpoli.getText().trim()+"%");
                        ps3.setString(4,"%"+nmdokter.getText().trim()+"%");
                        ps3.setString(5,"%"+nmpenjab.getText().trim()+"%");
                        ps3.setString(6,"%"+nmkabupaten.getText().trim()+"%");
                        ps3.setString(7,"%"+nmkecamatan.getText().trim()+"%");
                        ps3.setString(8,"%"+nmkelurahan.getText().trim()+"%");
                        ps3.setString(9,rs.getString("kd_penyakit"));
                        rs3=ps3.executeQuery();
                        rs3.last();
                        if(rs3.getRow()>0) a=rs3.getRow();
                    } catch (Exception e) {
                        System.out.println("Notif ps3 : "+e);
                    } finally{
                        if(rs3!=null){
                            rs3.close();
                        }
                        if(ps3!=null){
                            ps3.close();
                        }
                    }
                        

                    b=0;
                    ps4=koneksi.prepareStatement("select diagnosa_pasien.no_rawat as jumlah from penyakit inner join diagnosa_pasien inner join reg_periksa inner join pasien_mati "+
                        "inner join dokter inner join pasien inner join poliklinik inner join penjab inner join kabupaten inner join kecamatan inner join kelurahan "+
                        "on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat and reg_periksa.kd_dokter=dokter.kd_dokter " +
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli and "+
                        "pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel and pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                        "where reg_periksa.status_lanjut='Ralan' and diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and pasien.jk='P' and reg_periksa.tgl_registrasi between ? and ? "+
                        "and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? "+
                        "and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat"); 
                    try {
                        ps4.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps4.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps4.setString(3,"%"+nmpoli.getText().trim()+"%");
                        ps4.setString(4,"%"+nmdokter.getText().trim()+"%");
                        ps4.setString(5,"%"+nmpenjab.getText().trim()+"%");
                        ps4.setString(6,"%"+nmkabupaten.getText().trim()+"%");
                        ps4.setString(7,"%"+nmkecamatan.getText().trim()+"%");
                        ps4.setString(8,"%"+nmkelurahan.getText().trim()+"%");
                        ps4.setString(9,rs.getString("kd_penyakit"));
                        rs4=ps4.executeQuery();
                        rs4.last();       
                        if(rs4.getRow()>0) b=rs4.getRow();
                    } catch (Exception e) {
                        System.out.println("Notif ps4 : "+e);
                    } finally{
                        if(rs4!=null){
                            rs4.close();
                        }
                        if(ps4!=null){
                            ps4.close();
                        }
                    }                        

                    c=0;
                    ps5=koneksi.prepareStatement("select diagnosa_pasien.no_rawat as jumlah from penyakit inner join diagnosa_pasien inner join reg_periksa "+
                        "inner join dokter inner join pasien inner join poliklinik inner join penjab inner join kabupaten inner join kecamatan inner join kelurahan "+
                        "on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat and reg_periksa.kd_dokter=dokter.kd_dokter " +
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli and "+
                        "pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                        "where reg_periksa.status_lanjut='Ralan' and diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and pasien.jk='L' and reg_periksa.tgl_registrasi between ? and ? "+
                        "and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? "+
                        "and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat");
                    try{
                        ps5.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps5.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps5.setString(3,"%"+nmpoli.getText().trim()+"%");
                        ps5.setString(4,"%"+nmdokter.getText().trim()+"%");
                        ps5.setString(5,"%"+nmpenjab.getText().trim()+"%");
                        ps5.setString(6,"%"+nmkabupaten.getText().trim()+"%");
                        ps5.setString(7,"%"+nmkecamatan.getText().trim()+"%");
                        ps5.setString(8,"%"+nmkelurahan.getText().trim()+"%");
                        ps5.setString(9,rs.getString("kd_penyakit"));
                        rs5=ps5.executeQuery();
                        rs5.last();
                        if(rs5.getRow()>0)  c=rs5.getRow()-a;
                    } catch (Exception e) {
                        System.out.println("Notif ps5 : "+e);
                    } finally{
                        if(rs5!=null){
                            rs5.close();
                        }
                        if(ps5!=null){
                            ps5.close();
                        }
                    }

                    d=0;
                    ps6=koneksi.prepareStatement("select diagnosa_pasien.no_rawat as jumlah from penyakit inner join diagnosa_pasien inner join reg_periksa "+
                        "inner join dokter inner join pasien inner join poliklinik inner join penjab inner join kabupaten inner join kecamatan inner join kelurahan "+
                        "on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat and reg_periksa.kd_dokter=dokter.kd_dokter " +
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli and "+
                        "pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                        "where reg_periksa.status_lanjut='Ralan' and diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and pasien.jk='P' and reg_periksa.tgl_registrasi between ? and ? "+
                        "and poliklinik.nm_poli like ? and dokter.nm_dokter like ? and penjab.png_jawab like ? and kabupaten.nm_kab like ? and kecamatan.nm_kec like ? and kelurahan.nm_kel like ? "+
                        "and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat");
                    try{
                        ps6.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps6.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps6.setString(3,"%"+nmpoli.getText().trim()+"%");
                        ps6.setString(4,"%"+nmdokter.getText().trim()+"%");
                        ps6.setString(5,"%"+nmpenjab.getText().trim()+"%");
                        ps6.setString(6,"%"+nmkabupaten.getText().trim()+"%");
                        ps6.setString(7,"%"+nmkecamatan.getText().trim()+"%");
                        ps6.setString(8,"%"+nmkelurahan.getText().trim()+"%");
                        ps6.setString(9,rs.getString("kd_penyakit"));
                        rs6=ps6.executeQuery();
                        rs6.last();
                        if(rs6.getRow()>0) d=rs6.getRow()-b;
                    } catch (Exception e) {
                        System.out.println("Notif ps6 : "+e);
                    } finally{
                        if(rs6!=null){
                            rs6.close();
                        }
                        if(ps6!=null){
                            ps6.close();
                        }
                    }
                    
                    tabMode.addRow(new Object[]{rs.getString("kd_penyakit"),rs.getString("penyakit"),diagnosa,c,d,a,b,i});                  
                }    
            } catch (Exception e) {
                System.out.println("Notif ps : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }    
            label9.setText("Record : "+tabMode.getRowCount());
        }catch(SQLException e){
            System.out.println("Catatan  "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(akses.getpenyakit_ralan());
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
