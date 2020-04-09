package keuangan;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgPenanggungJawab;

public class DlgDetailJMDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgPenanggungJawab carabayar=new DlgPenanggungJawab(null,false);
    private int i=0,a=0,c=0;
    private double ttljml=0,ttlbhp=0,ttlbiaya=0,ttljm=0,ttluangrs=0,detailjm=0,detailbhp=0,detailrs=0,detailtotal=0,biayaitem=0;
    private PreparedStatement ps,pspasienranap,pskamar,pstindakanranap,pspasienradiologi,pspasienralan,pstindakanralan,pstindakanradiologi,
            pspasienradiologi2,pstindakanradiologi2,pspasienlab,pstindakanlab,psdetaillab,pspasienlab2,pstindakanlab2,psdetaillab2,
            pspasienoperator1,pstindakanoperator1,pspasienoperator2,pstindakanoperator2,pspasienoperator3,pstindakanoperator3,
            pspasiendokter_anak,pstindakandokter_anak,pspasiendokter_anestesi,pstindakandokter_anestesi,pstindakanranapdrpr,
            pspasienranapdrpr,pspasienralandrpr,pstindakanralandrpr,psanak,pskamar2,pstindakandokter_pjanak,pspasiendokter_pjanak,pstindakandokter_umum,pspasiendokter_umum;
    private ResultSet rs,rspasien,rskamar,rstindakan,rsdetaillab,rsanak,rskamar2/*,rsperiksa_radiologi,rsperiksa_radiologiperujuk,rsperiksa_lab,
            rsperiksa_labperujuk,rsdetaillab*/;
    private String jml="",biaya="",jm="",uangrs="",bhp="",tarif="",tanggal="",kamar="",penjab="",pasien="",pilihancarabayar="",norawatbayi="";
    private boolean kelas=false,ranapgabung=false;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgDetailJMDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Nama Dokter","Nama Pasien","Ruang","Jenis Bayar","Nama Tindakan","Tarif","Jml","Biaya","Paket BHP/Obat","J.M.Dokter","J.S.Rmh Skt"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(280);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(25);
            }else if(i==8){
                column.setPreferredWidth(85);
            }else if(i==9){
                column.setPreferredWidth(85);
            }else if(i==10){
                column.setPreferredWidth(85);
            }else if(i==11){
                column.setPreferredWidth(85);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kddokter.setDocument(new batasInput((byte)10).getKata(kddokter));
                
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
                    kelas=false;
                    ranapgabung=false;
                    prosesCari();
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
        
        carabayar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carabayar.getTable().getSelectedRow()!= -1){
                    pilihancarabayar=carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(),1).toString();
                }     
                prosesCari();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {carabayar.onCari();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        carabayar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    carabayar.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppTampilkanSeleksi = new javax.swing.JMenuItem();
        ppTampilkanRanapGabung = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        BtnGaji = new widget.Button();
        BtnGaji1 = new widget.Button();
        panelisi1 = new widget.panelisi();
        chkRalan = new widget.CekBox();
        chkRadiologi = new widget.CekBox();
        chkLaborat = new widget.CekBox();
        chkOperasi = new widget.CekBox();
        chkRanap = new widget.CekBox();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppTampilkanSeleksi.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanSeleksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanSeleksi.setForeground(java.awt.Color.darkGray);
        ppTampilkanSeleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanSeleksi.setText("Tampilkan Per Jenis Bayar");
        ppTampilkanSeleksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanSeleksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanSeleksi.setName("ppTampilkanSeleksi"); // NOI18N
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(360, 25));
        ppTampilkanSeleksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanSeleksiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanSeleksi);

        ppTampilkanRanapGabung.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanRanapGabung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanRanapGabung.setForeground(java.awt.Color.darkGray);
        ppTampilkanRanapGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanRanapGabung.setText("Tampilkan Per Ranap Gabung (Ibu Harus Ada Tindakan Ranap)");
        ppTampilkanRanapGabung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanRanapGabung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanRanapGabung.setName("ppTampilkanRanapGabung"); // NOI18N
        ppTampilkanRanapGabung.setPreferredSize(new java.awt.Dimension(360, 25));
        ppTampilkanRanapGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanRanapGabungBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanRanapGabung);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Detail J.M Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
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
        tbDokter.setToolTipText("");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tgl.Tindakan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(95, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(95, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi4.add(Tgl2);

        label17.setText("Dokter :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);

        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(80, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        panelisi4.add(kddokter);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmdokter);

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
        panelisi4.add(BtnSeek2);

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
        panelisi4.add(BtnCari);

        BtnGaji.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/gaji.png"))); // NOI18N
        BtnGaji.setMnemonic('2');
        BtnGaji.setToolTipText("Alt+2");
        BtnGaji.setName("BtnGaji"); // NOI18N
        BtnGaji.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGajiActionPerformed(evt);
            }
        });
        BtnGaji.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnGajiKeyPressed(evt);
            }
        });
        panelisi4.add(BtnGaji);

        BtnGaji1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/gaji.png"))); // NOI18N
        BtnGaji1.setMnemonic('2');
        BtnGaji1.setToolTipText("Alt+2");
        BtnGaji1.setName("BtnGaji1"); // NOI18N
        BtnGaji1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGaji1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGaji1ActionPerformed(evt);
            }
        });
        BtnGaji1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnGaji1KeyPressed(evt);
            }
        });
        panelisi4.add(BtnGaji1);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        chkRalan.setSelected(true);
        chkRalan.setText("Ralan");
        chkRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRalan.setName("chkRalan"); // NOI18N
        chkRalan.setOpaque(false);
        chkRalan.setPreferredSize(new java.awt.Dimension(95, 30));
        chkRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRalanActionPerformed(evt);
            }
        });
        panelisi1.add(chkRalan);

        chkRadiologi.setSelected(true);
        chkRadiologi.setText("Radiologi");
        chkRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRadiologi.setName("chkRadiologi"); // NOI18N
        chkRadiologi.setOpaque(false);
        chkRadiologi.setPreferredSize(new java.awt.Dimension(95, 30));
        chkRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRadiologiActionPerformed(evt);
            }
        });
        panelisi1.add(chkRadiologi);

        chkLaborat.setSelected(true);
        chkLaborat.setText("Laboratorium");
        chkLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLaborat.setName("chkLaborat"); // NOI18N
        chkLaborat.setOpaque(false);
        chkLaborat.setPreferredSize(new java.awt.Dimension(100, 30));
        chkLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLaboratActionPerformed(evt);
            }
        });
        panelisi1.add(chkLaborat);

        chkOperasi.setSelected(true);
        chkOperasi.setText("Operasi");
        chkOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkOperasi.setName("chkOperasi"); // NOI18N
        chkOperasi.setOpaque(false);
        chkOperasi.setPreferredSize(new java.awt.Dimension(95, 30));
        chkOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOperasiActionPerformed(evt);
            }
        });
        panelisi1.add(chkOperasi);

        chkRanap.setSelected(true);
        chkRanap.setText("Ranap");
        chkRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRanap.setName("chkRanap"); // NOI18N
        chkRanap.setOpaque(false);
        chkRanap.setPreferredSize(new java.awt.Dimension(95, 30));
        chkRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRanapActionPerformed(evt);
            }
        });
        panelisi1.add(chkRanap);

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
        panelisi1.add(BtnAll);

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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            for(i=0;i<tabMode.getRowCount();i++){  
                try {
                    uangrs=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,11).toString()));
                } catch (Exception e) {
                    uangrs="";
                }
                try {
                    jm=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,10).toString()));
                } catch (Exception e) {
                    jm="";
                }
                try {
                    bhp=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString()));
                } catch (Exception e) {
                    bhp="";
                }
                try {
                    biaya=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()));
                } catch (Exception e) {
                    biaya="";
                }
                try {
                    jml=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()));
                } catch (Exception e) {
                    jml="";
                }
                try {
                    tarif=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()));
                } catch (Exception e) {
                    tarif="";
                }
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString().replaceAll("'","`") +"','"+
                                tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"','"+
                                tarif+"','"+
                                jml+"','"+
                                biaya+"','"+
                                bhp+"','"+
                                jm+"','"+
                                uangrs+"','','','','','','','','','','','','','','','','','','','','','','','','',''","JM Dokter"); 
            }
            
            Map<String, Object> param = new HashMap<>();   
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptJMRanapDokter.jasper","report","[ Detail J.M. Dokter  ]",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnKeluar);
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

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kddokter.setText("");
        nmdokter.setText("");
        kelas=false;
        ranapgabung=false;
        pilihancarabayar="";
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, kddokter, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        kelas=false;
        ranapgabung=false;
        prosesCari();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kddokter, BtnGaji);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kddokter);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
        kelas=false;
        ranapgabung=false;
        pilihancarabayar="";
    }//GEN-LAST:event_formWindowOpened

    private void chkRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRalanActionPerformed
        kelas=false;
        ranapgabung=false;
        prosesCari();
    }//GEN-LAST:event_chkRalanActionPerformed

    private void chkRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRadiologiActionPerformed
        kelas=false;
        ranapgabung=false;
        prosesCari();
    }//GEN-LAST:event_chkRadiologiActionPerformed

    private void chkLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLaboratActionPerformed
        kelas=false;
        ranapgabung=false;
        prosesCari();
    }//GEN-LAST:event_chkLaboratActionPerformed

    private void chkOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOperasiActionPerformed
        kelas=false;
        ranapgabung=false;
        prosesCari();
    }//GEN-LAST:event_chkOperasiActionPerformed

    private void chkRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRanapActionPerformed
        kelas=false;
        ranapgabung=false;
        prosesCari();
    }//GEN-LAST:event_chkRanapActionPerformed

    private void BtnGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGajiActionPerformed
        if(kddokter.getText().trim().equals("")||nmdokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"Dokter");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){
                
                Sequel.queryu("truncate table temporary");
                for(i=1;i<tabMode.getRowCount();i++){  
                    try {
                        uangrs=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,11).toString()));
                    } catch (Exception e) {
                        uangrs="";
                    }
                    try {
                        jm=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,10).toString()));
                    } catch (Exception e) {
                        jm="";
                    }
                    try {
                        bhp=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString()));
                    } catch (Exception e) {
                        bhp="";
                    }
                    try {
                        biaya=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()));
                    } catch (Exception e) {
                        biaya="";
                    }
                    try {
                        jml=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()));
                    } catch (Exception e) {
                        jml="";
                    }
                    try {
                        tarif=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()));
                    } catch (Exception e) {
                        tarif="";
                    }
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(i,0).toString().replaceAll("'","`") +"','"+
                                    tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"','"+
                                    tarif+"','"+
                                    jml+"','"+
                                    biaya+"','"+
                                    bhp+"','"+
                                    jm+"','"+
                                    uangrs+"','','','','','','','','','','','','','','','','','','','','','','','','',''","JM Dokter"); 
                }
                
                Map<String, Object> param = new HashMap<>();   
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("dokter",nmdokter.getText());   
                    param.put("bulan",Tgl1.getSelectedItem().toString().substring(3,10));   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptSlipJMDokter.jasper","report","[ Slip J.M. Dokter  ]",param);
            }
            this.setCursor(Cursor.getDefaultCursor());            
        }
    }//GEN-LAST:event_BtnGajiActionPerformed

    private void BtnGajiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnGajiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnGajiActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnAll);
        }
    }//GEN-LAST:event_BtnGajiKeyPressed

    private void BtnGaji1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGaji1ActionPerformed
        if(kddokter.getText().trim().equals("")||nmdokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"Dokter");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            kelas=true;
            prosesCari();
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){                
                
                Sequel.queryu("truncate table temporary");
                for(i=1;i<tabMode.getRowCount();i++){  
                    try {
                        uangrs=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,11).toString()));
                    } catch (Exception e) {
                        uangrs="";
                    }
                    try {
                        jm=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,10).toString()));
                    } catch (Exception e) {
                        jm="";
                    }
                    try {
                        bhp=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString()));
                    } catch (Exception e) {
                        bhp="";
                    }
                    try {
                        biaya=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()));
                    } catch (Exception e) {
                        biaya="";
                    }
                    try {
                        jml=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()));
                    } catch (Exception e) {
                        jml="";
                    }
                    try {
                        tarif=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()));
                    } catch (Exception e) {
                        tarif="";
                    }
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(i,0).toString().replaceAll("'","`") +"','"+
                                    tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"','"+
                                    tarif+"','"+
                                    jml+"','"+
                                    biaya+"','"+
                                    bhp+"','"+
                                    jm+"','"+
                                    uangrs+"','','','','','','','','','','','','','','','','','','','','','','','','',''","JM Dokter"); 
                }
                
                Map<String, Object> param = new HashMap<>();   
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("dokter",nmdokter.getText());   
                    param.put("bulan",Tgl1.getSelectedItem().toString().substring(3,10));   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptSlipJMDokter.jasper","report","[ Slip J.M. Dokter  ]",param);
            }
            this.setCursor(Cursor.getDefaultCursor());            
        }
    }//GEN-LAST:event_BtnGaji1ActionPerformed

    private void BtnGaji1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnGaji1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnGaji1KeyPressed

    private void ppTampilkanSeleksiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanSeleksiBtnPrintActionPerformed
        carabayar.isCek();
        carabayar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carabayar.setLocationRelativeTo(internalFrame1);
        carabayar.setVisible(true);
    }//GEN-LAST:event_ppTampilkanSeleksiBtnPrintActionPerformed

    private void ppTampilkanRanapGabungBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanRanapGabungBtnPrintActionPerformed
        ranapgabung=true;
        prosesCari();
    }//GEN-LAST:event_ppTampilkanRanapGabungBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDetailJMDokter dialog = new DlgDetailJMDokter(new javax.swing.JFrame(), true);
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
    private widget.Button BtnGaji;
    private widget.Button BtnGaji1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.CekBox chkLaborat;
    private widget.CekBox chkOperasi;
    private widget.CekBox chkRadiologi;
    private widget.CekBox chkRalan;
    private widget.CekBox chkRanap;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppTampilkanRanapGabung;
    private javax.swing.JMenuItem ppTampilkanSeleksi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{
           ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and kd_dokter like ? order by nm_dokter");
           try {
                ps.setString(1,"%"+kddokter.getText()+"%");
                rs=ps.executeQuery();
                i=1;
                ttlbiaya=0;
                ttljm=0;
                ttljml=0;
                ttlbhp=0;
                ttluangrs=0;
                while(rs.next()){
                   tabMode.addRow(new Object[]{i+".",rs.getString("nm_dokter"),"","","","",null,null,null,null,null,null});                 
                   c=1;
                   //jmralan
                   if(chkRalan.isSelected()==true){
                        pspasienralan=koneksi.prepareStatement(
                            "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,penjab.png_jawab,poliklinik.nm_poli "+
                            "from rawat_jl_dr inner join reg_periksa on rawat_jl_dr.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where rawat_jl_dr.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_pj like ? group by rawat_jl_dr.no_rawat");
                        try {
                            pspasienralan.setString(1,rs.getString("kd_dokter"));
                            pspasienralan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pspasienralan.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pspasienralan.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasienralan.executeQuery();
                            while(rspasien.next()){ 
                                pstindakanralan=koneksi.prepareStatement(
                                    "select rawat_jl_dr.kd_jenis_prw,count(rawat_jl_dr.kd_jenis_prw) as jml,"+
                                    "jns_perawatan.nm_perawatan,sum(rawat_jl_dr.bhp)as bhp,sum(rawat_jl_dr.material)as material,"+
                                    "rawat_jl_dr.biaya_rawat as total_byrdr,sum(rawat_jl_dr.tarif_tindakandr)as tarif_tindakandr,"+
                                    "sum(rawat_jl_dr.biaya_rawat) as total from rawat_jl_dr inner join jns_perawatan on jns_perawatan.kd_jenis_prw=rawat_jl_dr.kd_jenis_prw "+
                                    "inner join reg_periksa on rawat_jl_dr.no_rawat=reg_periksa.no_rawat where "+
                                    "rawat_jl_dr.tarif_tindakandr>0 and rawat_jl_dr.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.no_rawat=? "+
                                    "group by rawat_jl_dr.kd_jenis_prw order by jns_perawatan.nm_perawatan");   
                                try {
                                    a=1;
                                    pstindakanralan.setString(1,rs.getString("kd_dokter"));
                                    pstindakanralan.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    pstindakanralan.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    pstindakanralan.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakanralan.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("tarif_tindakandr");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("material");
                                        ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_registrasi"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi Tindakan Rawat Jalan : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakanralan!=null){
                                        pstindakanralan.close();
                                    }
                                }                                        
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Pasien Ralan : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasienralan!=null){
                                pspasienralan.close();
                            }
                        }

                        pspasienralandrpr=koneksi.prepareStatement(
                            "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,pasien.nm_pasien,penjab.png_jawab,poliklinik.nm_poli "+
                            "from rawat_jl_drpr inner join reg_periksa on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where rawat_jl_drpr.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_pj like ? group by rawat_jl_drpr.no_rawat");
                        try {
                            pspasienralandrpr.setString(1,rs.getString("kd_dokter"));
                            pspasienralandrpr.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pspasienralandrpr.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pspasienralandrpr.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasienralandrpr.executeQuery();
                            while(rspasien.next()){ 
                                a=1; 
                                pstindakanralandrpr=koneksi.prepareStatement("select rawat_jl_drpr.kd_jenis_prw,"+
                                        "count(rawat_jl_drpr.kd_jenis_prw) as jml,"+
                                        "jns_perawatan.nm_perawatan,sum(rawat_jl_drpr.bhp)as bhp,sum(rawat_jl_drpr.material)as material,"+
                                        "rawat_jl_drpr.biaya_rawat as total_byrdr,sum(rawat_jl_drpr.tarif_tindakandr)as tarif_tindakandr,"+
                                        "sum(rawat_jl_drpr.biaya_rawat) as total "+
                                        "from rawat_jl_drpr inner join jns_perawatan on jns_perawatan.kd_jenis_prw=rawat_jl_drpr.kd_jenis_prw "+
                                        "inner join reg_periksa on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat where "+
                                        "rawat_jl_drpr.tarif_tindakandr>0 and rawat_jl_drpr.kd_dokter=? and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.no_rawat=? "+
                                        "group by rawat_jl_drpr.kd_jenis_prw order by jns_perawatan.nm_perawatan");   
                                try {
                                    pstindakanralandrpr.setString(1,rs.getString("kd_dokter"));
                                    pstindakanralandrpr.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    pstindakanralandrpr.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    pstindakanralandrpr.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakanralandrpr.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("tarif_tindakandr");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("material");
                                        ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_registrasi"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rspasien.getString("no_rkm_medis")+" "+rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi tindakan ralan drpr : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakanralandrpr!=null){
                                        pstindakanralandrpr.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi tidakan ralan drpr : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasienralandrpr!=null){
                                pspasienralandrpr.close();
                            }
                        }                            
                   }

                   //jmradiologi
                   if(chkRadiologi.isSelected()==true){
                        pspasienradiologi=koneksi.prepareStatement(
                            "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.tgl_periksa,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Radiolgi' as nm_poli "+
                            "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where periksa_radiologi.kd_dokter=? and periksa_radiologi.tgl_periksa between ? and ? and reg_periksa.kd_pj like ? group by periksa_radiologi.no_rawat");
                        try {
                            pspasienradiologi.setString(1,rs.getString("kd_dokter"));
                            pspasienradiologi.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pspasienradiologi.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pspasienradiologi.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasienradiologi.executeQuery();
                            while(rspasien.next()){ 
                                a=1;   
                                pstindakanradiologi=koneksi.prepareStatement(
                                        "select periksa_radiologi.kd_jenis_prw,"+
                                        "count(periksa_radiologi.kd_jenis_prw) as jml, "+
                                        "jns_perawatan_radiologi.nm_perawatan,"+
                                        "sum(periksa_radiologi.bhp) as bhp,"+
                                        "sum(periksa_radiologi.bagian_rs) as bagian_rs,"+
                                        "periksa_radiologi.biaya as total_byr,"+
                                        "sum(periksa_radiologi.tarif_tindakan_dokter)as tarif_tindakan_dokter,"+
                                        "sum(periksa_radiologi.biaya) as total  "+
                                        "from periksa_radiologi inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where "+
                                        "periksa_radiologi.tarif_tindakan_dokter>0 and periksa_radiologi.kd_dokter=? and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.no_rawat=? "+
                                        "group by periksa_radiologi.kd_jenis_prw order by jns_perawatan_radiologi.nm_perawatan  ");
                                try {
                                    pstindakanradiologi.setString(1,rs.getString("kd_dokter"));
                                    pstindakanradiologi.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    pstindakanradiologi.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    pstindakanradiologi.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakanradiologi.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("tarif_tindakan_dokter");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs");
                                        ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_periksa"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (P.J. Rad)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakan_dokter"),rstindakan.getDouble("bagian_rs")}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (P.J. Rad)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakan_dokter"),rstindakan.getDouble("bagian_rs")}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi tindakan radiologi : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakanradiologi!=null){
                                        pstindakanradiologi.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi radiologi : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasienradiologi!=null){
                                pspasienradiologi.close();
                            }
                        }

                        //jmradiologiperujuk
                        pspasienradiologi2=koneksi.prepareStatement(
                            "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,periksa_radiologi.tgl_periksa,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Radiolgi' as nm_poli "+
                            "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where periksa_radiologi.dokter_perujuk=? and periksa_radiologi.tgl_periksa between ? and ? and reg_periksa.kd_pj like ? group by periksa_radiologi.no_rawat");
                        try {
                            pspasienradiologi2.setString(1,rs.getString("kd_dokter"));
                            pspasienradiologi2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pspasienradiologi2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pspasienradiologi2.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasienradiologi2.executeQuery();
                            while(rspasien.next()){ 
                                a=1;     
                                pstindakanradiologi2=koneksi.prepareStatement("select periksa_radiologi.kd_jenis_prw,"+
                                        "count(periksa_radiologi.kd_jenis_prw) as jml, "+
                                        "jns_perawatan_radiologi.nm_perawatan,"+
                                        "0 as bhp,"+
                                        "0 as bagian_rs,"+
                                        "0 as total_byr,"+
                                        "sum(periksa_radiologi.tarif_perujuk)as tarif_perujuk,"+
                                        "0 as total  "+
                                        "from periksa_radiologi inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where "+
                                        "periksa_radiologi.tarif_perujuk>0 and periksa_radiologi.dokter_perujuk=? and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.no_rawat=? "+
                                        "group by periksa_radiologi.kd_jenis_prw order by jns_perawatan_radiologi.nm_perawatan  ");
                                try {
                                    pstindakanradiologi2.setString(1,rs.getString("kd_dokter"));
                                    pstindakanradiologi2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    pstindakanradiologi2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    pstindakanradiologi2.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakanradiologi2.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("tarif_perujuk");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs");
                                        ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_periksa"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (Perujuk Rad)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_perujuk"),0}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (Perujuk Rad)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_perujuk"),0}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi tindakan radiologi : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakanradiologi2!=null){
                                        pstindakanradiologi2.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi pasien radiologi : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasienradiologi2!=null){
                                pspasienradiologi2.close();
                            }
                        }
                   }                 

                   //jm laborat
                   if(chkLaborat.isSelected()==true){
                        pspasienlab=koneksi.prepareStatement(
                            "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.tgl_periksa,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Laborat' as nm_poli "+
                            "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where periksa_lab.kd_dokter=? and periksa_lab.tgl_periksa between ? and ? and reg_periksa.kd_pj like ? group by periksa_lab.no_rawat");                    
                        try {
                            pspasienlab.setString(1,rs.getString("kd_dokter"));
                            pspasienlab.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pspasienlab.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pspasienlab.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasienlab.executeQuery();
                            while(rspasien.next()){                        
                                a=1;   
                                pstindakanlab=koneksi.prepareStatement("select periksa_lab.kd_jenis_prw,"+
                                        "count(periksa_lab.kd_jenis_prw) as jml, "+
                                        "jns_perawatan_lab.nm_perawatan,"+
                                        "sum(periksa_lab.bhp) as bhp,"+
                                        "sum(periksa_lab.bagian_rs) as bagian_rs,"+
                                        "periksa_lab.biaya as total_byr,"+
                                        "sum(periksa_lab.tarif_tindakan_dokter)as tarif_tindakan_dokter,"+
                                        "sum(periksa_lab.biaya) as total  "+
                                        "from periksa_lab inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where "+
                                        "periksa_lab.kd_dokter=? and periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat=? "+
                                        "group by periksa_lab.kd_jenis_prw order by jns_perawatan_lab.nm_perawatan  ");
                                try {
                                    pstindakanlab.setString(1,rs.getString("kd_dokter"));
                                    pstindakanlab.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    pstindakanlab.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    pstindakanlab.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakanlab.executeQuery();
                                    while(rstindakan.next()){
                                        detailjm=0;
                                        detailrs=0;
                                        detailtotal=0;
                                        detailbhp=0;
                                        biayaitem=0;
                                        psdetaillab=koneksi.prepareStatement("select sum(detail_periksa_lab.bagian_dokter) as totaldokter,"+
                                            "sum(detail_periksa_lab.bagian_rs) as totalrs,"+
                                            "sum(detail_periksa_lab.bhp) as bhp, "+
                                            "sum(detail_periksa_lab.biaya_item) as biaya_item,"+
                                            "sum(detail_periksa_lab.biaya_item) as totalbayar from detail_periksa_lab inner join jns_perawatan_lab inner join "+
                                            "periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                                            "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa where "+
                                            "periksa_lab.jam=detail_periksa_lab.jam and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                            "and periksa_lab.kd_dokter=? and detail_periksa_lab.tgl_periksa between ? and ? and detail_periksa_lab.kd_jenis_prw=? and periksa_lab.no_rawat=? ");
                                        try {
                                            psdetaillab.setString(1,rs.getString("kd_dokter"));
                                            psdetaillab.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                            psdetaillab.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                            psdetaillab.setString(4,rstindakan.getString("kd_jenis_prw"));
                                            psdetaillab.setString(5,rspasien.getString("no_rawat"));
                                            rsdetaillab=psdetaillab.executeQuery();
                                            while(rsdetaillab.next()){
                                                 detailjm=rsdetaillab.getDouble("totaldokter");
                                                 detailrs=rsdetaillab.getDouble("totalrs");
                                                 detailtotal=rsdetaillab.getDouble("totalbayar");
                                                 biayaitem=rsdetaillab.getDouble("biaya_item");
                                                 detailbhp=rsdetaillab.getDouble("bhp");
                                            }
                                            ttljml=ttljml+rstindakan.getDouble("jml");
                                            ttlbiaya=ttlbiaya+rstindakan.getDouble("total")+detailtotal;
                                            ttljm=ttljm+rstindakan.getDouble("tarif_tindakan_dokter")+detailjm;
                                            ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs")+detailrs;
                                            ttlbhp=ttlbhp+rstindakan.getDouble("bhp")+detailbhp;
                                            if(a==1){
                                                tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_periksa"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (P.J. Lab)",(rstindakan.getDouble("total_byr")+biayaitem),rstindakan.getDouble("jml"),(rstindakan.getDouble("total")+detailtotal),(rstindakan.getDouble("bhp")+detailbhp),(rstindakan.getDouble("tarif_tindakan_dokter")+detailjm),(rstindakan.getDouble("bagian_rs")+detailrs)}); 
                                                c++;
                                            }else{
                                                tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (P.J. Lab)",(rstindakan.getDouble("total_byr")+biayaitem),rstindakan.getDouble("jml"),(rstindakan.getDouble("total")+detailtotal),(rstindakan.getDouble("bhp")+detailbhp),(rstindakan.getDouble("tarif_tindakan_dokter")+detailjm),(rstindakan.getDouble("bagian_rs")+detailrs)}); 
                                            }                           
                                            a++;
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi detail lab : "+e);
                                        } finally{
                                            if(rsdetaillab!=null){
                                                rsdetaillab.close();
                                            }
                                            if(psdetaillab!=null){
                                                psdetaillab.close();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi tindakan lab : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakanlab!=null){
                                        pstindakanlab.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi pasien lab : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasienlab!=null){
                                pspasienlab.close();
                            }
                        }
                            
                        //jm perujuk
                        pspasienlab2=koneksi.prepareStatement(
                            "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,periksa_lab.tgl_periksa,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Laborat' as nm_poli "+
                            "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where periksa_lab.dokter_perujuk=? and periksa_lab.tgl_periksa between ? and ? and reg_periksa.kd_pj like ? group by periksa_lab.no_rawat");                    
                        try {
                            pspasienlab2.setString(1,rs.getString("kd_dokter"));
                            pspasienlab2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            pspasienlab2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            pspasienlab2.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasienlab2.executeQuery();
                            while(rspasien.next()){                        
                                a=1;  
                                pstindakanlab2=koneksi.prepareStatement("select periksa_lab.kd_jenis_prw,"+
                                        "count(periksa_lab.kd_jenis_prw) as jml, "+
                                        "jns_perawatan_lab.nm_perawatan,"+
                                        "0 as bhp,"+
                                        "0 as bagian_rs,"+
                                        "0 as total_byr,"+
                                        "sum(periksa_lab.tarif_perujuk)as tarif_perujuk,"+
                                        "0 as total  "+
                                        "from periksa_lab inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where "+
                                        "periksa_lab.dokter_perujuk=? and periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat=? "+
                                        "group by periksa_lab.kd_jenis_prw order by jns_perawatan_lab.nm_perawatan  ");
                                try {
                                    pstindakanlab2.setString(1,rs.getString("kd_dokter"));
                                    pstindakanlab2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    pstindakanlab2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    pstindakanlab2.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakanlab2.executeQuery();
                                    while(rstindakan.next()){
                                        detailjm=0;
                                        detailrs=0;
                                        detailtotal=0;
                                        biayaitem=0;
                                        detailbhp=0;
                                        psdetaillab2=koneksi.prepareStatement("select sum(detail_periksa_lab.bagian_perujuk) as totaldokter,"+
                                            "0 as bhp, "+
                                            "0 as totalrs, "+
                                            "0 as biaya_item,"+
                                            "0 as totalbayar from detail_periksa_lab inner join jns_perawatan_lab inner join "+
                                            "periksa_lab on periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                                            "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa where "+
                                            "periksa_lab.jam=detail_periksa_lab.jam and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                            "and periksa_lab.dokter_perujuk=? and detail_periksa_lab.tgl_periksa between ? and ? and detail_periksa_lab.kd_jenis_prw=? and periksa_lab.no_rawat=? ");
                                        try {
                                            psdetaillab2.setString(1,rs.getString("kd_dokter"));
                                            psdetaillab2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                            psdetaillab2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                            psdetaillab2.setString(4,rstindakan.getString("kd_jenis_prw"));
                                            psdetaillab2.setString(5,rspasien.getString("no_rawat"));
                                            rsdetaillab=psdetaillab2.executeQuery();
                                            while(rsdetaillab.next()){
                                                 detailjm=rsdetaillab.getDouble("totaldokter");
                                                 detailrs=rsdetaillab.getDouble("totalrs");
                                                 detailtotal=rsdetaillab.getDouble("totalbayar");
                                                 biayaitem=rsdetaillab.getDouble("biaya_item");
                                                 detailbhp=rsdetaillab.getDouble("bhp");
                                            }
                                            ttljml=ttljml+rstindakan.getDouble("jml");
                                            ttlbiaya=ttlbiaya+rstindakan.getDouble("total")+detailtotal;
                                            ttljm=ttljm+rstindakan.getDouble("tarif_perujuk")+detailjm;
                                            ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs")+detailrs;
                                            ttlbhp=ttlbhp+rstindakan.getDouble("bhp")+detailbhp;
                                            if(a==1){
                                                tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_periksa"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (Perujuk Lab)",(rstindakan.getDouble("total_byr")+biayaitem),rstindakan.getDouble("jml"),(rstindakan.getDouble("total")+detailtotal),(rstindakan.getDouble("bhp")+detailbhp),(rstindakan.getDouble("tarif_perujuk")+detailjm),0}); 
                                                c++;
                                            }else{
                                                tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (Perujuk Lab)",(rstindakan.getDouble("total_byr")+biayaitem),rstindakan.getDouble("jml"),(rstindakan.getDouble("total")+detailtotal),(rstindakan.getDouble("bhp")+detailbhp),(rstindakan.getDouble("tarif_perujuk")+detailjm),0}); 
                                            }                           
                                            a++;
                                        } catch (Exception e) {
                                            System.out.println("Notif detail rujukan : "+e);
                                        } finally{
                                            if(rsdetaillab!=null){
                                                rsdetaillab.close();
                                            }
                                            if(psdetaillab2!=null){
                                                psdetaillab2.close();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi tindakan perujuk : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakanlab2!=null){
                                        pstindakanlab2.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi pasien perujuk : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasienlab2!=null){
                                pspasienlab2.close();
                            }
                        }                   
                   }

                   if(chkOperasi.isSelected()==true){
                        //jmoperator1
                        pspasienoperator1=koneksi.prepareStatement(
                            "select operasi.no_rawat,reg_periksa.no_rkm_medis,operasi.tgl_operasi,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Kamar Operasi' as nm_poli "+
                            "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where operasi.operator1=? and operasi.tgl_operasi between ? and ? and reg_periksa.kd_pj like ? group by operasi.no_rawat");
                        try {
                            pspasienoperator1.setString(1,rs.getString("kd_dokter"));
                            pspasienoperator1.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            pspasienoperator1.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            pspasienoperator1.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasienoperator1.executeQuery();
                            while(rspasien.next()){ 
                                a=1;    
                                pstindakanoperator1=koneksi.prepareStatement("select operasi.kode_paket,"+
                                        "count(operasi.kode_paket) as jml, "+
                                        "paket_operasi.nm_perawatan,"+
                                        "(count(operasi.kode_paket)*(operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biayasarpras)) as bagian_rs,"+
                                        "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                                        "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayainstrumen+"+
                                        "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                                        "operasi.biayaasisten_anestesi+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+"+
                                        "operasi.biayaperawat_luar+operasi.biayaalat+"+
                                        "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+"+
                                        "operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biayasarpras) as total_byr,"+
                                        "(count(operasi.kode_paket)*operasi.biayaoperator1)as biayaoperator1,"+
                                        "(count(operasi.kode_paket)*(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                                        "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayainstrumen+"+
                                        "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                                        "operasi.biayaasisten_anestesi+operasi.biayabidan+operasi.biayabidan2+"+
                                        "operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+"+
                                        "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+"+
                                        "operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biayasarpras)) as total  "+
                                        "from operasi inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket where "+
                                        "operasi.biayaoperator1>0 and operasi.operator1=? and operasi.tgl_operasi between ? and ? and operasi.no_rawat=? "+
                                        "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                                try {
                                    pstindakanoperator1.setString(1,rs.getString("kd_dokter"));
                                    pstindakanoperator1.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                                    pstindakanoperator1.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    pstindakanoperator1.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakanoperator1.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("biayaoperator1");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_operasi"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (Operator 1)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayaoperator1"),rstindakan.getDouble("bagian_rs")}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (Operator 1)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayaoperator1"),rstindakan.getDouble("bagian_rs")}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif tindakan operasi : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakanoperator1!=null){
                                        pstindakanoperator1.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif pasien operasi : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasienoperator1!=null){
                                pspasienoperator1.close();
                            }
                        }

                        //jmoperator2
                        pspasienoperator2=koneksi.prepareStatement(
                            "select operasi.no_rawat,reg_periksa.no_rkm_medis,operasi.tgl_operasi,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Kamar Operasi' as nm_poli "+
                            "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where operasi.operator2=? and operasi.tgl_operasi between ? and ? and reg_periksa.kd_pj like ? group by operasi.no_rawat");
                        try {
                            pspasienoperator2.setString(1,rs.getString("kd_dokter"));
                            pspasienoperator2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            pspasienoperator2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            pspasienoperator2.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasienoperator2.executeQuery();
                            while(rspasien.next()){ 
                                a=1;  
                                pstindakanoperator2=koneksi.prepareStatement("select operasi.kode_paket,"+
                                        "count(operasi.kode_paket) as jml, "+
                                        "paket_operasi.nm_perawatan,"+
                                        "0 as bagian_rs,"+
                                        "0 as total_byr,"+
                                        "(count(operasi.kode_paket)*operasi.biayaoperator2)as biayaoperator2,"+
                                        "0 as total  "+
                                        "from operasi inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket where "+
                                        "operasi.biayaoperator2>0 and operasi.operator2=? and operasi.tgl_operasi between ? and ? and operasi.no_rawat=? "+
                                        "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                                try {
                                    pstindakanoperator2.setString(1,rs.getString("kd_dokter"));
                                    pstindakanoperator2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                                    pstindakanoperator2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    pstindakanoperator2.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakanoperator2.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("biayaoperator2");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_operasi"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (Operator 2)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayaoperator2"),0}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (Operator 2)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayaoperator2"),0}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif tindakan operator 2 : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakanoperator2!=null){
                                        pstindakanoperator2.close();
                                    }
                                }   
                            }
                        } catch (Exception e) {
                            System.out.println("Notif pasien operator 2 : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasienoperator2!=null){
                                pspasienoperator2.close();
                            }
                        }

                        //jmoperator3
                        pspasienoperator3=koneksi.prepareStatement(
                            "select operasi.no_rawat,reg_periksa.no_rkm_medis,operasi.tgl_operasi,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Kamar Operasi' as nm_poli "+
                            "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where operasi.operator3=? and operasi.tgl_operasi between ? and ? and reg_periksa.kd_pj like ? group by operasi.no_rawat");
                        try {
                            pspasienoperator3.setString(1,rs.getString("kd_dokter"));
                            pspasienoperator3.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            pspasienoperator3.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            pspasienoperator3.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasienoperator3.executeQuery();
                            while(rspasien.next()){ 
                                a=1;      
                                pstindakanoperator3=koneksi.prepareStatement("select operasi.kode_paket,"+
                                        "count(operasi.kode_paket) as jml, "+
                                        "paket_operasi.nm_perawatan,"+
                                        "0 as bagian_rs,"+
                                        "0 as total_byr,"+
                                        "(count(operasi.kode_paket)*operasi.biayaoperator3)as biayaoperator3,"+
                                        "0 as total  "+
                                        "from operasi inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket where "+
                                        "operasi.biayaoperator3>0 and operasi.operator3=? and operasi.tgl_operasi between ? and ? and operasi.no_rawat=? "+
                                        "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                                try {
                                    pstindakanoperator3.setString(1,rs.getString("kd_dokter"));
                                    pstindakanoperator3.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                                    pstindakanoperator3.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    pstindakanoperator3.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakanoperator3.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("biayaoperator3");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_operasi"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (Operator 3)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayaoperator3"),0}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (Operator 3)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayaoperator3"),0}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif tindakan operator 3");
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakanoperator3!=null){
                                        pstindakanoperator3.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif operator 3 : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasienoperator3!=null){
                                pspasienoperator3.close();
                            }
                        }

                        //jmdokter_anak
                        pspasiendokter_anak=koneksi.prepareStatement(
                            "select operasi.no_rawat,reg_periksa.no_rkm_medis,operasi.tgl_operasi,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Kamar Operasi' as nm_poli "+
                            "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where operasi.dokter_anak=? and operasi.tgl_operasi between ? and ? and reg_periksa.kd_pj like ? group by operasi.no_rawat");
                        try {
                            pspasiendokter_anak.setString(1,rs.getString("kd_dokter"));
                            pspasiendokter_anak.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            pspasiendokter_anak.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            pspasiendokter_anak.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasiendokter_anak.executeQuery();
                            while(rspasien.next()){ 
                                a=1;   
                                pstindakandokter_anak=koneksi.prepareStatement("select operasi.kode_paket,"+
                                        "count(operasi.kode_paket) as jml, "+
                                        "paket_operasi.nm_perawatan,"+
                                        "0 as bagian_rs,"+
                                        "0 as total_byr,"+
                                        "(count(operasi.kode_paket)*operasi.biayadokter_anak)as biayadokter_anak,"+
                                        "0 as total  "+
                                        "from operasi inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket where "+
                                        "operasi.biayadokter_anak>0 and operasi.dokter_anak=? and operasi.tgl_operasi between ? and ? and operasi.no_rawat=? "+
                                        "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                                try {
                                    pstindakandokter_anak.setString(1,rs.getString("kd_dokter"));
                                    pstindakandokter_anak.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                                    pstindakandokter_anak.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    pstindakandokter_anak.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakandokter_anak.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("biayadokter_anak");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_operasi"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (Dokter Anak)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayadokter_anak"),0}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (Dokter Anak)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayadokter_anak"),0}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif tindakan dokter anak : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakandokter_anak!=null){
                                        pstindakandokter_anak.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif dokter anak : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasiendokter_anak!=null){
                                pspasiendokter_anak.close();
                            }
                        }

                        //jmdokter_anestesi
                        pspasiendokter_anestesi=koneksi.prepareStatement(
                            "select operasi.no_rawat,reg_periksa.no_rkm_medis,operasi.tgl_operasi,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Kamar Operasi' as nm_poli "+
                            "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where operasi.dokter_anestesi=? and operasi.tgl_operasi between ? and ? and reg_periksa.kd_pj like ? group by operasi.no_rawat");
                        try {
                            pspasiendokter_anestesi.setString(1,rs.getString("kd_dokter"));
                            pspasiendokter_anestesi.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            pspasiendokter_anestesi.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            pspasiendokter_anestesi.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasiendokter_anestesi.executeQuery();
                            while(rspasien.next()){ 
                                a=1;  
                                pstindakandokter_anestesi=koneksi.prepareStatement("select operasi.kode_paket,"+
                                        "count(operasi.kode_paket) as jml, "+
                                        "paket_operasi.nm_perawatan,"+
                                        "0 as bagian_rs,"+
                                        "0 as total_byr,"+
                                        "(count(operasi.kode_paket)*operasi.biayadokter_anestesi)as biayadokter_anestesi,"+
                                        "0 as total  "+
                                        "from operasi inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket where "+
                                        "operasi.biayadokter_anestesi>0 and operasi.dokter_anestesi=? and operasi.tgl_operasi between ? and ? and operasi.no_rawat=? "+
                                        "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                                try {
                                    pstindakandokter_anestesi.setString(1,rs.getString("kd_dokter"));
                                    pstindakandokter_anestesi.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                                    pstindakandokter_anestesi.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    pstindakandokter_anestesi.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakandokter_anestesi.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("biayadokter_anestesi");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_operasi"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (Dokter Anestesi)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayadokter_anestesi"),0}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (Dokter Anestesi)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biayadokter_anestesi"),0}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi tindakan anastesi : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakandokter_anestesi!=null){
                                        pstindakandokter_anestesi.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif pasien anastesi : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasiendokter_anestesi!=null){
                                pspasiendokter_anestesi.close();
                            }
                        }

                        //jmdokter_pjanak
                        pspasiendokter_pjanak=koneksi.prepareStatement(
                            "select operasi.no_rawat,reg_periksa.no_rkm_medis,operasi.tgl_operasi,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Kamar Operasi' as nm_poli "+
                            "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where operasi.dokter_pjanak=? and operasi.tgl_operasi between ? and ? and reg_periksa.kd_pj like ? group by operasi.no_rawat");
                        try {
                            pspasiendokter_pjanak.setString(1,rs.getString("kd_dokter"));
                            pspasiendokter_pjanak.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                            pspasiendokter_pjanak.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                            pspasiendokter_pjanak.setString(4,"%"+pilihancarabayar+"%");
                            rspasien=pspasiendokter_pjanak.executeQuery();
                            while(rspasien.next()){ 
                                a=1;    
                                pstindakandokter_pjanak=koneksi.prepareStatement("select operasi.kode_paket,"+
                                        "count(operasi.kode_paket) as jml, "+
                                        "paket_operasi.nm_perawatan,"+
                                        "0 as bagian_rs,"+
                                        "0 as total_byr,"+
                                        "(count(operasi.kode_paket)*operasi.biaya_dokter_pjanak)as biaya_dokter_pjanak,"+
                                        "0 as total  "+
                                        "from operasi inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket where "+
                                        "operasi.biaya_dokter_pjanak>0 and operasi.dokter_pjanak=? and operasi.tgl_operasi between ? and ? and operasi.no_rawat=? "+
                                        "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                                try {
                                    pstindakandokter_pjanak.setString(1,rs.getString("kd_dokter"));
                                    pstindakandokter_pjanak.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                                    pstindakandokter_pjanak.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    pstindakandokter_pjanak.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakandokter_pjanak.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("biaya_dokter_pjanak");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_operasi"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (Dokter Pj Anak)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biaya_dokter_pjanak"),0}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (Dokter Pj Anak)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biaya_dokter_pjanak"),0}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif tindakan dokter pj umum : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakandokter_pjanak!=null){
                                        pstindakandokter_pjanak.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif pasien pj anak : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasiendokter_pjanak!=null){
                                pspasiendokter_pjanak.close();
                            }
                        }

                        //jmdokter_umum
                        pspasiendokter_umum=koneksi.prepareStatement(
                            "select operasi.no_rawat,reg_periksa.no_rkm_medis,operasi.tgl_operasi,"+
                            "pasien.nm_pasien,penjab.png_jawab,'Kamar Operasi' as nm_poli "+
                            "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                            "where operasi.dokter_umum=? and operasi.tgl_operasi between ? and ? and reg_periksa.kd_pj like ? group by operasi.no_rawat");
                        pspasiendokter_umum.setString(1,rs.getString("kd_dokter"));
                        pspasiendokter_umum.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                        pspasiendokter_umum.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                        pspasiendokter_umum.setString(4,"%"+pilihancarabayar+"%");
                        rspasien=pspasiendokter_umum.executeQuery();
                        try {
                            while(rspasien.next()){ 
                                a=1;   
                                pstindakandokter_umum=koneksi.prepareStatement("select operasi.kode_paket,"+
                                        "count(operasi.kode_paket) as jml, "+
                                        "paket_operasi.nm_perawatan,"+
                                        "0 as bagian_rs,"+
                                        "0 as total_byr,"+
                                        "(count(operasi.kode_paket)*operasi.biaya_dokter_umum)as biaya_dokter_umum,"+
                                        "0 as total  "+
                                        "from operasi inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket where "+
                                        "operasi.biaya_dokter_umum>0 and operasi.dokter_umum=? and operasi.tgl_operasi between ? and ? and operasi.no_rawat=? "+
                                        "group by operasi.kode_paket order by paket_operasi.nm_perawatan  ");
                                try {
                                    pstindakandokter_umum.setString(1,rs.getString("kd_dokter"));
                                    pstindakandokter_umum.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                                    pstindakandokter_umum.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                                    pstindakandokter_umum.setString(4,rspasien.getString("no_rawat"));
                                    rstindakan=pstindakandokter_umum.executeQuery();
                                    while(rstindakan.next()){
                                        ttljml=ttljml+rstindakan.getDouble("jml");
                                        ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                        ttljm=ttljm+rstindakan.getDouble("biaya_dokter_umum");
                                        ttluangrs=ttluangrs+rstindakan.getDouble("bagian_rs");
                                        if(a==1){
                                            tabMode.addRow(new Object[]{"",c+". "+rspasien.getString("tgl_operasi"),rspasien.getString("no_rkm_medis")+" "+rspasien.getString("nm_pasien"),rspasien.getString("nm_poli"),rspasien.getString("png_jawab"),rstindakan.getString("nm_perawatan")+" (Dokter Umum)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biaya_dokter_umum"),0}); 
                                            c++;
                                        }else{
                                            tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan")+" (Dokter Umum)",rstindakan.getDouble("total_byr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),0,rstindakan.getDouble("biaya_dokter_umum"),0}); 
                                        }                           
                                        a++;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif tindakan dokter umum : "+e);
                                } finally{
                                    if(rstindakan!=null){
                                        rstindakan.close();
                                    }
                                    if(pstindakandokter_umum!=null){
                                        pstindakandokter_umum.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif pasien dokter umum : "+e);
                        } finally{
                            if(rspasien!=null){
                                rspasien.close();
                            }
                            if(pspasiendokter_umum!=null){
                                pspasiendokter_umum.close();
                            }
                        }
                   }                    

                   //jmranap
                   if(chkRanap.isSelected()==true){
                        if(ranapgabung==false){
                            pspasienranap=koneksi.prepareStatement(
                                    "select rawat_inap_dr.no_rawat from rawat_inap_dr inner join reg_periksa on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                                    "where rawat_inap_dr.kd_dokter=? and rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.kd_pj like ? group by rawat_inap_dr.no_rawat");
                            try {
                                pspasienranap.setString(1,rs.getString("kd_dokter"));
                                pspasienranap.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                pspasienranap.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                pspasienranap.setString(4,"%"+pilihancarabayar+"%");
                                rspasien=pspasienranap.executeQuery();
                                while(rspasien.next()){  
                                    pskamar=koneksi.prepareStatement("select kamar_inap.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,penjab.png_jawab,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,kamar_inap.tgl_masuk,if(kamar_inap.tgl_keluar='0000-00-00','-',kamar_inap.tgl_keluar) as tgl_keluar,kamar.kelas "+
                                        "from kamar_inap inner join kamar inner join bangsal inner join reg_periksa inner join pasien inner join penjab on kamar_inap.no_rawat=reg_periksa.no_rawat and "+
                                        "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                                        "where kamar_inap.no_rawat=? group by kamar_inap.no_rawat");
                                    try {
                                        pskamar.setString(1,rspasien.getString("no_rawat"));
                                        rskamar=pskamar.executeQuery();
                                        tanggal="";
                                        kamar="";
                                        penjab="";
                                        pasien="";
                                        if(rskamar.next()){  
                                            tanggal=rskamar.getString("tgl_masuk")+" "+rskamar.getString("tgl_keluar");
                                            if(kelas==false){
                                                kamar=rskamar.getString("kd_kamar")+" "+rskamar.getString("nm_bangsal");
                                            }else if(kelas==true){
                                                kamar=rskamar.getString("kelas");
                                            }                                            
                                            penjab=rskamar.getString("png_jawab");
                                            pasien=rskamar.getString("no_rkm_medis")+" "+rskamar.getString("nm_pasien");
                                        }else{
                                            psanak=koneksi.prepareStatement(
                                                "select pasien.no_rkm_medis,pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.no_peserta, "+
                                                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "+
                                                "from reg_periksa inner join pasien inner join ranap_gabung on "+
                                                "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat2=?");                        
                                            try {
                                                psanak.setString(1,rspasien.getString("no_rawat"));
                                                rsanak=psanak.executeQuery();
                                                if(rsanak.next()){
                                                    pasien=rsanak.getString("no_rkm_medis")+" "+rsanak.getString("nm_pasien");  
                                                    pskamar2=koneksi.prepareStatement("select kamar_inap.no_rawat,pasien.nm_pasien,penjab.png_jawab,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,kamar_inap.tgl_masuk,if(kamar_inap.tgl_keluar='0000-00-00','-',kamar_inap.tgl_keluar) as tgl_keluar,kamar.kelas "+
                                                        "from kamar_inap inner join kamar inner join bangsal inner join reg_periksa inner join pasien inner join penjab on kamar_inap.no_rawat=reg_periksa.no_rawat and "+
                                                        "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                                                        "where kamar_inap.no_rawat=? group by kamar_inap.no_rawat");
                                                    try {
                                                        pskamar2.setString(1,rsanak.getString("no_rawat"));
                                                        rskamar2=pskamar2.executeQuery();
                                                        if(rskamar2.next()){
                                                            tanggal=rskamar2.getString("tgl_masuk")+" "+rskamar2.getString("tgl_keluar");
                                                            if(kelas==false){
                                                                kamar=rskamar2.getString("kd_kamar")+" "+rskamar2.getString("nm_bangsal");
                                                            }else if(kelas==true){
                                                                kamar=rskamar2.getString("kelas");
                                                            }                                                            
                                                            penjab=rskamar2.getString("png_jawab");
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("Notif kamar2 : "+e);
                                                    } finally{
                                                        if(rskamar2!=null){
                                                            rskamar2.close();
                                                        }
                                                        if(pskamar2!=null){
                                                            pskamar2.close();
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif pasien anak : "+e);
                                            } finally{
                                                if(rsanak!=null){
                                                    rsanak.close();
                                                }
                                                if(psanak!=null){
                                                    psanak.close();
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif kamar : "+e);
                                    } finally{
                                        if(rskamar!=null){
                                            rskamar.close();
                                        }
                                        if(pskamar!=null){
                                            pskamar.close();
                                        }
                                    }  

                                    a=1;        
                                    pstindakanranap=koneksi.prepareStatement("select rawat_inap_dr.kd_jenis_prw,"+
                                        "count(rawat_inap_dr.kd_jenis_prw) as jml,"+
                                        "jns_perawatan_inap.nm_perawatan,"+
                                        "sum(rawat_inap_dr.bhp)as bhp,"+
                                        "sum(rawat_inap_dr.material)as material,"+
                                        "rawat_inap_dr.biaya_rawat as total_byrdr,"+
                                        "sum(rawat_inap_dr.tarif_tindakandr)as tarif_tindakandr,"+
                                        "sum(rawat_inap_dr.biaya_rawat) as total  "+
                                        "from rawat_inap_dr inner join jns_perawatan_inap on  jns_perawatan_inap.kd_jenis_prw=rawat_inap_dr.kd_jenis_prw where "+
                                        "rawat_inap_dr.tarif_tindakandr>0 and rawat_inap_dr.kd_dokter=? and rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.no_rawat=? "+
                                        "group by rawat_inap_dr.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan"); 
                                    try {
                                        pstindakanranap.setString(1,rs.getString("kd_dokter"));
                                        pstindakanranap.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                        pstindakanranap.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                        pstindakanranap.setString(4,rspasien.getString("no_rawat"));
                                        rstindakan=pstindakanranap.executeQuery();
                                        while(rstindakan.next()){
                                            ttljml=ttljml+rstindakan.getDouble("jml");
                                            ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                            ttljm=ttljm+rstindakan.getDouble("tarif_tindakandr");
                                            ttluangrs=ttluangrs+rstindakan.getDouble("material");
                                            ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                            if(a==1){
                                                tabMode.addRow(new Object[]{"",c+". "+tanggal,pasien,kamar,penjab,rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                                c++;
                                            }else{
                                                tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")});    
                                            }                           
                                            a++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif tindakan ranap : "+e);
                                    } finally{
                                        if(rstindakan!=null){
                                            rstindakan.close();
                                        }
                                        if(pstindakanranap!=null){
                                            pstindakanranap.close();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif pasien ranap : "+e);
                            } finally{
                                if(rspasien!=null){
                                    rspasien.close();
                                }
                                if(pspasienranap!=null){
                                    pspasienranap.close();
                                }
                            }

                            pspasienranapdrpr=koneksi.prepareStatement(
                                    "select rawat_inap_drpr.no_rawat from rawat_inap_drpr inner join reg_periksa on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                                    "where rawat_inap_drpr.kd_dokter=? and rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.kd_pj like ? group by rawat_inap_drpr.no_rawat");            
                            try {
                                pspasienranapdrpr.setString(1,rs.getString("kd_dokter"));
                                pspasienranapdrpr.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                pspasienranapdrpr.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                pspasienranapdrpr.setString(4,"%"+pilihancarabayar+"%");
                                rspasien=pspasienranapdrpr.executeQuery();
                                while(rspasien.next()){   
                                    pskamar=koneksi.prepareStatement("select kamar_inap.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,penjab.png_jawab,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,kamar_inap.tgl_masuk,if(kamar_inap.tgl_keluar='0000-00-00','-',kamar_inap.tgl_keluar) as tgl_keluar,kamar.kelas "+
                                        "from kamar_inap inner join kamar inner join bangsal inner join reg_periksa inner join pasien inner join penjab on kamar_inap.no_rawat=reg_periksa.no_rawat and "+
                                        "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                                        "where kamar_inap.no_rawat=? group by kamar_inap.no_rawat");
                                    try {
                                        pskamar.setString(1,rspasien.getString("no_rawat"));
                                        rskamar=pskamar.executeQuery();
                                        tanggal="";
                                        kamar="";
                                        penjab="";
                                        pasien="";
                                        if(rskamar.next()){  
                                            tanggal=rskamar.getString("tgl_masuk")+" "+rskamar.getString("tgl_keluar");
                                            if(kelas==false){
                                                kamar=rskamar.getString("kd_kamar")+" "+rskamar.getString("nm_bangsal");
                                            }else if(kelas==true){
                                                kamar=rskamar.getString("kelas");
                                            }                                            
                                            penjab=rskamar.getString("png_jawab");
                                            pasien=rskamar.getString("no_rkm_medis")+" "+rskamar.getString("nm_pasien");
                                        }else{
                                            psanak=koneksi.prepareStatement(
                                                "select pasien.no_rkm_medis,pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.no_peserta, "+
                                                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "+
                                                "from reg_periksa inner join pasien inner join ranap_gabung on "+
                                                "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat2=?");                        
                                            try {
                                                psanak.setString(1,rspasien.getString("no_rawat"));
                                                rsanak=psanak.executeQuery();
                                                if(rsanak.next()){
                                                    pasien=rsanak.getString("no_rkm_medis")+" "+rsanak.getString("nm_pasien");   
                                                    pskamar2=koneksi.prepareStatement("select kamar_inap.no_rawat,pasien.nm_pasien,penjab.png_jawab,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,kamar_inap.tgl_masuk,if(kamar_inap.tgl_keluar='0000-00-00','-',kamar_inap.tgl_keluar) as tgl_keluar,kamar.kelas "+
                                                        "from kamar_inap inner join kamar inner join bangsal inner join reg_periksa inner join pasien inner join penjab on kamar_inap.no_rawat=reg_periksa.no_rawat and "+
                                                        "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                                                        "where kamar_inap.no_rawat=? group by kamar_inap.no_rawat");
                                                    try {
                                                        pskamar2.setString(1,rsanak.getString("no_rawat"));
                                                        rskamar2=pskamar2.executeQuery();
                                                        if(rskamar2.next()){
                                                            tanggal=rskamar2.getString("tgl_masuk")+" "+rskamar2.getString("tgl_keluar");
                                                            if(kelas==false){
                                                                kamar=rskamar2.getString("kd_kamar")+" "+rskamar2.getString("nm_bangsal");
                                                            }else if(kelas==true){
                                                                kamar=rskamar2.getString("kelas");
                                                            }                                                            
                                                            penjab=rskamar2.getString("png_jawab");
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("Notif kamar bayi : "+e);
                                                    } finally{
                                                        if(rskamar2!=null){
                                                            rskamar2.close();
                                                        }
                                                        if(pskamar2!=null){
                                                            pskamar2.close();
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif Pasien anak : "+e);
                                            } finally{
                                                if(rsanak!=null){
                                                    rsanak.close();
                                                }
                                                if(psanak!=null){
                                                    psanak.close();
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif Kamar : "+e);
                                    } finally{
                                        if(rskamar!=null){
                                            rskamar.close();
                                        }
                                        if(pskamar!=null){
                                            pskamar.close();
                                        }
                                    }  

                                    a=1;   
                                    pstindakanranapdrpr=koneksi.prepareStatement("select rawat_inap_drpr.kd_jenis_prw,"+
                                        "count(rawat_inap_drpr.kd_jenis_prw) as jml,"+
                                        "jns_perawatan_inap.nm_perawatan,"+
                                        "sum(rawat_inap_drpr.bhp)as bhp,"+
                                        "sum(rawat_inap_drpr.material)as material,"+
                                        "rawat_inap_drpr.biaya_rawat as total_byrdr,"+
                                        "sum(rawat_inap_drpr.tarif_tindakandr)as tarif_tindakandr,"+
                                        "sum(rawat_inap_drpr.biaya_rawat) as total  "+
                                        "from rawat_inap_drpr inner join jns_perawatan_inap on  jns_perawatan_inap.kd_jenis_prw=rawat_inap_drpr.kd_jenis_prw where "+
                                        "rawat_inap_drpr.tarif_tindakandr>0 and rawat_inap_drpr.kd_dokter=? and rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.no_rawat=? "+
                                        "group by rawat_inap_drpr.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan"); 
                                    try {
                                        pstindakanranapdrpr.setString(1,rs.getString("kd_dokter"));
                                        pstindakanranapdrpr.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                        pstindakanranapdrpr.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                        pstindakanranapdrpr.setString(4,rspasien.getString("no_rawat"));
                                        rstindakan=pstindakanranapdrpr.executeQuery();
                                        while(rstindakan.next()){
                                            ttljml=ttljml+rstindakan.getDouble("jml");
                                            ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                            ttljm=ttljm+rstindakan.getDouble("tarif_tindakandr");
                                            ttluangrs=ttluangrs+rstindakan.getDouble("material");
                                            ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                            if(a==1){
                                                tabMode.addRow(new Object[]{"",c+". "+tanggal,pasien,kamar,penjab,rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                                c++;
                                            }else{
                                                tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")});    
                                            }                           
                                            a++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif tindakan dokter pj umum : "+e);
                                    } finally{
                                        if(rstindakan!=null){
                                            rstindakan.close();
                                        }
                                        if(pstindakanranapdrpr!=null){
                                            pstindakanranapdrpr.close();
                                        }
                                    }                   
                                }
                            } catch (Exception e) {
                                System.out.println("Notif pasien dokter umum : "+e);
                            } finally{
                                if(rspasien!=null){
                                    rspasien.close();
                                }
                                if(pspasienranapdrpr!=null){
                                    pspasienranapdrpr.close();
                                }
                            }
                        }else if(ranapgabung==true){
                            pspasienranap=koneksi.prepareStatement(
                                    "select rawat_inap_dr.no_rawat from rawat_inap_dr inner join reg_periksa on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                                    "where rawat_inap_dr.kd_dokter=? and rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.kd_pj like ? "+
                                    "and rawat_inap_dr.no_rawat not in (select no_rawat2 from ranap_gabung) group by rawat_inap_dr.no_rawat");
                            try {
                                pspasienranap.setString(1,rs.getString("kd_dokter"));
                                pspasienranap.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                pspasienranap.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                pspasienranap.setString(4,"%"+pilihancarabayar+"%");
                                rspasien=pspasienranap.executeQuery();
                                while(rspasien.next()){  
                                    norawatbayi="";
                                    pskamar=koneksi.prepareStatement("select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,penjab.png_jawab,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,kamar_inap.tgl_masuk,if(kamar_inap.tgl_keluar='0000-00-00','-',kamar_inap.tgl_keluar) as tgl_keluar,kamar.kelas "+
                                        "from kamar_inap inner join kamar inner join bangsal inner join reg_periksa inner join pasien inner join penjab on kamar_inap.no_rawat=reg_periksa.no_rawat and "+
                                        "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                                        "where kamar_inap.no_rawat=? group by kamar_inap.no_rawat");
                                    try {
                                        pskamar.setString(1,rspasien.getString("no_rawat"));
                                        rskamar=pskamar.executeQuery();
                                        tanggal="";
                                        kamar="";
                                        penjab="";
                                        pasien="";
                                        if(rskamar.next()){  
                                            tanggal=rskamar.getString("tgl_masuk")+" "+rskamar.getString("tgl_keluar");
                                            if(kelas==false){
                                                kamar=rskamar.getString("kd_kamar")+" "+rskamar.getString("nm_bangsal");
                                            }else if(kelas==true){
                                                kamar=rskamar.getString("kelas");
                                            }                                            
                                            penjab=rskamar.getString("png_jawab");
                                            pasien=rskamar.getString("no_rkm_medis")+" "+rskamar.getString("nm_pasien");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif kamar : "+e);
                                    } finally{
                                        if(rskamar!=null){
                                            rskamar.close();
                                        }
                                        if(pskamar!=null){
                                            pskamar.close();
                                        }
                                    }  

                                    a=1;        
                                    pstindakanranap=koneksi.prepareStatement("select rawat_inap_dr.kd_jenis_prw,"+
                                        "count(rawat_inap_dr.kd_jenis_prw) as jml,"+
                                        "jns_perawatan_inap.nm_perawatan,"+
                                        "sum(rawat_inap_dr.bhp)as bhp,"+
                                        "sum(rawat_inap_dr.material)as material,"+
                                        "rawat_inap_dr.biaya_rawat as total_byrdr,"+
                                        "sum(rawat_inap_dr.tarif_tindakandr)as tarif_tindakandr,"+
                                        "sum(rawat_inap_dr.biaya_rawat) as total  "+
                                        "from rawat_inap_dr inner join jns_perawatan_inap on  jns_perawatan_inap.kd_jenis_prw=rawat_inap_dr.kd_jenis_prw where "+
                                        "rawat_inap_dr.tarif_tindakandr>0 and rawat_inap_dr.kd_dokter=? and rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.no_rawat=? "+
                                        "group by rawat_inap_dr.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan"); 
                                    try {
                                        pstindakanranap.setString(1,rs.getString("kd_dokter"));
                                        pstindakanranap.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                        pstindakanranap.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                        pstindakanranap.setString(4,rspasien.getString("no_rawat"));
                                        rstindakan=pstindakanranap.executeQuery();
                                        while(rstindakan.next()){
                                            ttljml=ttljml+rstindakan.getDouble("jml");
                                            ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                            ttljm=ttljm+rstindakan.getDouble("tarif_tindakandr");
                                            ttluangrs=ttluangrs+rstindakan.getDouble("material");
                                            ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                            if(a==1){
                                                tabMode.addRow(new Object[]{"",c+". "+tanggal,pasien,kamar,penjab,rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                                c++;
                                            }else{
                                                tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")});    
                                            }                           
                                            a++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif tindakan ranap : "+e);
                                    } finally{
                                        if(rstindakan!=null){
                                            rstindakan.close();
                                        }
                                        if(pstindakanranap!=null){
                                            pstindakanranap.close();
                                        }
                                    }
                                    
                                    norawatbayi=Sequel.cariIsi("select no_rawat2 from ranap_gabung where no_rawat=?",rspasien.getString("no_rawat"));
                                    if(!norawatbayi.equals("")){
                                        pasien=Sequel.cariIsi(
                                            "select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien on "+
                                            "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=? ",norawatbayi);
                                        a=1;        
                                        pstindakanranap=koneksi.prepareStatement(
                                            "select rawat_inap_dr.kd_jenis_prw,"+
                                            "count(rawat_inap_dr.kd_jenis_prw) as jml,"+
                                            "jns_perawatan_inap.nm_perawatan,"+
                                            "sum(rawat_inap_dr.bhp)as bhp,"+
                                            "sum(rawat_inap_dr.material)as material,"+
                                            "rawat_inap_dr.biaya_rawat as total_byrdr,"+
                                            "sum(rawat_inap_dr.tarif_tindakandr)as tarif_tindakandr,"+
                                            "sum(rawat_inap_dr.biaya_rawat) as total  "+
                                            "from rawat_inap_dr inner join jns_perawatan_inap on  jns_perawatan_inap.kd_jenis_prw=rawat_inap_dr.kd_jenis_prw where "+
                                            "rawat_inap_dr.tarif_tindakandr>0 and rawat_inap_dr.kd_dokter=? and rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.no_rawat=? "+
                                            "group by rawat_inap_dr.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan"); 
                                        try {
                                            pstindakanranap.setString(1,rs.getString("kd_dokter"));
                                            pstindakanranap.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                            pstindakanranap.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                            pstindakanranap.setString(4,norawatbayi);
                                            rstindakan=pstindakanranap.executeQuery();
                                            while(rstindakan.next()){
                                                ttljml=ttljml+rstindakan.getDouble("jml");
                                                ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                                ttljm=ttljm+rstindakan.getDouble("tarif_tindakandr");
                                                ttluangrs=ttluangrs+rstindakan.getDouble("material");
                                                ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                                if(a==1){
                                                    tabMode.addRow(new Object[]{"",c+". "+tanggal,pasien,kamar,penjab,rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                                    c++;
                                                }else{
                                                    tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")});    
                                                }                           
                                                a++;
                                            }
                                        } catch (Exception e) {
                                                System.out.println("Notif tindakan ranap : "+e);
                                        } finally{
                                            if(rstindakan!=null){
                                                rstindakan.close();
                                            }
                                            if(pstindakanranap!=null){
                                                pstindakanranap.close();
                                            }
                                        }

                                    }
                                    
                                    
                                }
                            } catch (Exception e) {
                                System.out.println("Notif pasien ranap : "+e);
                            } finally{
                                if(rspasien!=null){
                                    rspasien.close();
                                }
                                if(pspasienranap!=null){
                                    pspasienranap.close();
                                }
                            }

                            pspasienranapdrpr=koneksi.prepareStatement(
                                    "select rawat_inap_drpr.no_rawat from rawat_inap_drpr inner join reg_periksa on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                                    "where rawat_inap_drpr.kd_dokter=? and rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.kd_pj like ? "+
                                    "and rawat_inap_drpr.no_rawat not in (select no_rawat2 from ranap_gabung) group by rawat_inap_drpr.no_rawat");            
                            try {
                                pspasienranapdrpr.setString(1,rs.getString("kd_dokter"));
                                pspasienranapdrpr.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                pspasienranapdrpr.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                pspasienranapdrpr.setString(4,"%"+pilihancarabayar+"%");
                                rspasien=pspasienranapdrpr.executeQuery();
                                while(rspasien.next()){   
				    norawatbayi="";
                                    pskamar=koneksi.prepareStatement("select kamar_inap.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,penjab.png_jawab,kamar_inap.kd_kamar,bangsal.kd_bangsal,bangsal.nm_bangsal,kamar_inap.tgl_masuk,if(kamar_inap.tgl_keluar='0000-00-00','-',kamar_inap.tgl_keluar) as tgl_keluar,kamar.kelas "+
                                        "from kamar_inap inner join kamar inner join bangsal inner join reg_periksa inner join pasien inner join penjab on kamar_inap.no_rawat=reg_periksa.no_rawat and "+
                                        "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                                        "where kamar_inap.no_rawat=? group by kamar_inap.no_rawat");
                                    try {
                                        pskamar.setString(1,rspasien.getString("no_rawat"));
                                        rskamar=pskamar.executeQuery();
                                        tanggal="";
                                        kamar="";
                                        penjab="";
                                        pasien="";
                                        if(rskamar.next()){  
                                            tanggal=rskamar.getString("tgl_masuk")+" "+rskamar.getString("tgl_keluar");
                                            if(kelas==false){
                                                kamar=rskamar.getString("kd_kamar")+" "+rskamar.getString("nm_bangsal");
                                            }else if(kelas==true){
                                                kamar=rskamar.getString("kelas");
                                            }                                            
                                            penjab=rskamar.getString("png_jawab");
                                            pasien=rskamar.getString("no_rkm_medis")+" "+rskamar.getString("nm_pasien");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif Kamar : "+e);
                                    } finally{
                                        if(rskamar!=null){
                                            rskamar.close();
                                        }
                                        if(pskamar!=null){
                                            pskamar.close();
                                        }
                                    }  

                                    a=1;   
                                    pstindakanranapdrpr=koneksi.prepareStatement("select rawat_inap_drpr.kd_jenis_prw,"+
                                        "count(rawat_inap_drpr.kd_jenis_prw) as jml,"+
                                        "jns_perawatan_inap.nm_perawatan,"+
                                        "sum(rawat_inap_drpr.bhp)as bhp,"+
                                        "sum(rawat_inap_drpr.material)as material,"+
                                        "rawat_inap_drpr.biaya_rawat as total_byrdr,"+
                                        "sum(rawat_inap_drpr.tarif_tindakandr)as tarif_tindakandr,"+
                                        "sum(rawat_inap_drpr.biaya_rawat) as total  "+
                                        "from rawat_inap_drpr inner join jns_perawatan_inap on  jns_perawatan_inap.kd_jenis_prw=rawat_inap_drpr.kd_jenis_prw where "+
                                        "rawat_inap_drpr.tarif_tindakandr>0 and rawat_inap_drpr.kd_dokter=? and rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.no_rawat=? "+
                                        "group by rawat_inap_drpr.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan"); 
                                    try {
                                        pstindakanranapdrpr.setString(1,rs.getString("kd_dokter"));
                                        pstindakanranapdrpr.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                        pstindakanranapdrpr.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                        pstindakanranapdrpr.setString(4,rspasien.getString("no_rawat"));
                                        rstindakan=pstindakanranapdrpr.executeQuery();
                                        while(rstindakan.next()){
                                            ttljml=ttljml+rstindakan.getDouble("jml");
                                            ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                            ttljm=ttljm+rstindakan.getDouble("tarif_tindakandr");
                                            ttluangrs=ttluangrs+rstindakan.getDouble("material");
                                            ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                            if(a==1){
                                                tabMode.addRow(new Object[]{"",c+". "+tanggal,pasien,kamar,penjab,rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                                c++;
                                            }else{
                                                tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")});    
                                            }                           
                                            a++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif tindakan dokter pj umum : "+e);
                                    } finally{
                                        if(rstindakan!=null){
                                            rstindakan.close();
                                        }
                                        if(pstindakanranapdrpr!=null){
                                            pstindakanranapdrpr.close();
                                        }
                                    }  
                                    
                                    norawatbayi=Sequel.cariIsi("select no_rawat2 from ranap_gabung where no_rawat=?",rspasien.getString("no_rawat"));
                                    if(!norawatbayi.equals("")){
                                        pasien=Sequel.cariIsi(
                                            "select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien on "+
                                            "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=? ",norawatbayi);
                                        a=1;                                           
                                        pstindakanranapdrpr=koneksi.prepareStatement(
                                            "select rawat_inap_drpr.kd_jenis_prw,"+
                                            "count(rawat_inap_drpr.kd_jenis_prw) as jml,"+
                                            "jns_perawatan_inap.nm_perawatan,"+
                                            "sum(rawat_inap_drpr.bhp)as bhp,"+
                                            "sum(rawat_inap_drpr.material)as material,"+
                                            "rawat_inap_drpr.biaya_rawat as total_byrdr,"+
                                            "sum(rawat_inap_drpr.tarif_tindakandr)as tarif_tindakandr,"+
                                            "sum(rawat_inap_drpr.biaya_rawat) as total  "+
                                            "from rawat_inap_drpr inner join jns_perawatan_inap on  jns_perawatan_inap.kd_jenis_prw=rawat_inap_drpr.kd_jenis_prw where "+
                                            "rawat_inap_drpr.tarif_tindakandr>0 and rawat_inap_drpr.kd_dokter=? and rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.no_rawat=? "+
                                            "group by rawat_inap_drpr.kd_jenis_prw order by jns_perawatan_inap.nm_perawatan"); 
                                        try {
                                            pstindakanranapdrpr.setString(1,rs.getString("kd_dokter"));
                                            pstindakanranapdrpr.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                            pstindakanranapdrpr.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                            pstindakanranapdrpr.setString(4,norawatbayi);
                                            rstindakan=pstindakanranapdrpr.executeQuery();
                                            while(rstindakan.next()){
                                                ttljml=ttljml+rstindakan.getDouble("jml");
                                                ttlbiaya=ttlbiaya+rstindakan.getDouble("total");
                                                ttljm=ttljm+rstindakan.getDouble("tarif_tindakandr");
                                                ttluangrs=ttluangrs+rstindakan.getDouble("material");
                                                ttlbhp=ttlbhp+rstindakan.getDouble("bhp");
                                                if(a==1){
                                                    tabMode.addRow(new Object[]{"",c+". "+tanggal,pasien,kamar,penjab,rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")}); 
                                                    c++;
                                                }else{
                                                    tabMode.addRow(new Object[]{"","","","","",rstindakan.getString("nm_perawatan"),rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("material")});    
                                                }                           
                                                a++;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notif tindakan dokter pj umum : "+e);
                                        } finally{
                                            if(rstindakan!=null){
                                                rstindakan.close();
                                            }
                                            if(pstindakanranapdrpr!=null){
                                                pstindakanranapdrpr.close();
                                            }
                                        } 
                                    }                 
                                }
                            } catch (Exception e) {
                                System.out.println("Notif pasien dokter umum : "+e);
                            } finally{
                                if(rspasien!=null){
                                    rspasien.close();
                                }
                                if(pspasienranapdrpr!=null){
                                    pspasienranapdrpr.close();
                                }
                            }
                        }
                        
                        
                   }
                   i++;
                }
                if(i>0){
                    tabMode.addRow(new Object[]{">>","Jumlah :","","","","",null,ttljml,ttlbiaya,ttlbhp,ttljm,ttluangrs});    
                }
           } catch (Exception e) {
               System.out.println("Notifikasi ps : "+e);
           } finally{
               if(rs!=null){
                   rs.close();
               }
               if(ps!=null){
                   ps.close();
               }
           }    
        }catch(SQLException e){
            System.out.println("Catatan  "+e);
        }
        
    }    
    
    public void isCek(){
        //BtnPrint.setEnabled(var.getjm_ranap_dokter());
    }
    
}
