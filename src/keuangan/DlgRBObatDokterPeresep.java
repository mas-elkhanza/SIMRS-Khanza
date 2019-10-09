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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgPenanggungJawab;

public class DlgRBObatDokterPeresep extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psdokter,psresep,psresep2;
    private ResultSet rsdokter,rsresep; 
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private int i=0,a=0;
    private DlgPenanggungJawab carabayar=new DlgPenanggungJawab(null,false);
    private double subtotal=0,ttlbiaya=0,embalase=0,ttlembalase=0,tuslah=0,ttltuslah=0;
    private String pilihancarabayar="",jumlah,total,emb,tsl;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRBObatDokterPeresep(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Dokter","Tanggal","Nama Obat","Jml","Biaya Obat","Embalase","Tuslah"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, 
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 8; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(190);
            }else if(i==2){
                column.setPreferredWidth(120);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(40);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(80);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kddokter.setDocument(new batasInput((byte)8).getKata(kddokter));
                
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
        
        try {
             psdokter=koneksi.prepareStatement(
                     "select kd_dokter,nm_dokter from dokter where  kd_dokter<>'-' and status='1' and kd_dokter like ?");             
             psresep=koneksi.prepareStatement(
                     "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,"+
                     "resep_obat.no_rawat,pasien.nm_pasien,resep_obat.kd_dokter,databarang.nama_brng,"+
                     "detail_pemberian_obat.jml,detail_pemberian_obat.biaya_obat,detail_pemberian_obat.embalase,"+
                     "detail_pemberian_obat.tuslah,(detail_pemberian_obat.total-(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as total, "+
                     "databarang.kode_brng from resep_obat inner join reg_periksa inner join pasien inner join "+
                     "detail_pemberian_obat inner join databarang "+
                     "on detail_pemberian_obat.kode_brng=databarang.kode_brng and detail_pemberian_obat.no_rawat=resep_obat.no_rawat "+
                     "and resep_obat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where detail_pemberian_obat.tgl_perawatan=resep_obat.tgl_perawatan and detail_pemberian_obat.jam=resep_obat.jam "+
                     "and resep_obat.kd_dokter=? and resep_obat.tgl_perawatan between ? and ? and reg_periksa.kd_pj like ? order by resep_obat.tgl_perawatan");
             psresep2=koneksi.prepareStatement(
                     "select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,"+
                     "resep_obat.no_rawat,pasien.nm_pasien,resep_obat.kd_dokter,databarang.nama_brng,"+
                     "detail_pemberian_obat.jml,detail_pemberian_obat.biaya_obat,detail_pemberian_obat.embalase,"+
                     "detail_pemberian_obat.tuslah,(detail_pemberian_obat.total-(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as total, "+
                     "databarang.kode_brng from resep_obat inner join reg_periksa inner join pasien inner join "+
                     "detail_pemberian_obat inner join databarang "+
                     "on detail_pemberian_obat.kode_brng=databarang.kode_brng and detail_pemberian_obat.no_rawat=resep_obat.no_rawat "+
                     "and resep_obat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "where detail_pemberian_obat.tgl_perawatan=resep_obat.tgl_perawatan and detail_pemberian_obat.jam=resep_obat.jam "+
                     "and resep_obat.kd_dokter=? and resep_obat.tgl_perawatan between ? and ? and reg_periksa.kd_pj like ? order by resep_obat.no_rawat");
        } catch (Exception e) {
            System.out.println(e);
        }
     
    }
    

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
        MnUrut1 = new javax.swing.JMenuItem();
        MnUrut2 = new javax.swing.JMenuItem();
        ppTampilkanSeleksi = new javax.swing.JMenuItem();
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
        panelisi1 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnUrut1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrut1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut1.setForeground(new java.awt.Color(50,50,50));
        MnUrut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut1.setText("Urutkan Berdasar Tanggal Pemberian");
        MnUrut1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut1.setName("MnUrut1"); // NOI18N
        MnUrut1.setPreferredSize(new java.awt.Dimension(250, 26));
        MnUrut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrut1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUrut1);

        MnUrut2.setBackground(new java.awt.Color(255, 255, 254));
        MnUrut2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut2.setForeground(new java.awt.Color(50,50,50));
        MnUrut2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut2.setText("Urutkan Berdasar Nama Pasien");
        MnUrut2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut2.setName("MnUrut2"); // NOI18N
        MnUrut2.setPreferredSize(new java.awt.Dimension(250, 26));
        MnUrut2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrut2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUrut2);

        ppTampilkanSeleksi.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanSeleksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanSeleksi.setForeground(new java.awt.Color(50,50,50));
        ppTampilkanSeleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanSeleksi.setText("Tampilkan Per Jenis Bayar");
        ppTampilkanSeleksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanSeleksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanSeleksi.setName("ppTampilkanSeleksi"); // NOI18N
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(250, 26));
        ppTampilkanSeleksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanSeleksiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanSeleksi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Penggunaan Obat Per Dokter Peresep ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
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
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Beri Obat :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
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
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
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
        kddokter.setPreferredSize(new java.awt.Dimension(70, 23));
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

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(450, 30));
        panelisi1.add(label9);

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
                jumlah="";
                try {
                    jumlah=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,4).toString()));
                } catch (Exception e) {
                    jumlah="";
                }
                total="";
                try {
                    total=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,5).toString()));
                } catch (Exception e) {
                    total="";
                }
                emb="";
                try {
                    emb=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()));
                } catch (Exception e) {
                    emb="";
                }
                tsl="";
                try {
                    tsl=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()));
                } catch (Exception e) {
                    tsl="";
                }
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"','"+
                                jumlah+"','"+total+"','"+emb+"','"+tsl+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Obat Perdokter Poli"); 
            }
                        
            Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRBObatPerdokterPeresep.jasper","report","[ Rekap Obat Per Dokter Peresep ]",param);
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
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
       kddokter.setText("");
        nmdokter.setText("");
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
        prosesCari();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kddokter, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kddokter);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void MnUrut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrut1ActionPerformed
        prosesCari();
    }//GEN-LAST:event_MnUrut1ActionPerformed

    private void MnUrut2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrut2ActionPerformed
        prosesCari2();
    }//GEN-LAST:event_MnUrut2ActionPerformed

    private void ppTampilkanSeleksiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanSeleksiBtnPrintActionPerformed
        carabayar.isCek();
        carabayar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carabayar.setLocationRelativeTo(internalFrame1);
        carabayar.setVisible(true);
    }//GEN-LAST:event_ppTampilkanSeleksiBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRBObatDokterPeresep dialog = new DlgRBObatDokterPeresep(new javax.swing.JFrame(), true);
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
    private widget.TextBox Kd2;
    private javax.swing.JMenuItem MnUrut1;
    private javax.swing.JMenuItem MnUrut2;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppTampilkanSeleksi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {             
        try{   
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
           Valid.tabelKosong(tabMode);
           psdokter.setString(1,"%"+kddokter.getText()+"%"); 
           rsdokter=psdokter.executeQuery();
           i=1;
           ttlbiaya=0;
           ttlembalase=0;
           ttltuslah=0;
           while(rsdokter.next()){
               tabMode.addRow(new Object[]{i+". ",rsdokter.getString("nm_dokter"),"","",null,null,null});
               psresep.setString(1,rsdokter.getString("kd_dokter"));
               psresep.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
               psresep.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
               psresep.setString(4,"%"+pilihancarabayar+"%");
               a=1;
               rsresep=psresep.executeQuery();               
               subtotal=0;
               embalase=0;
               tuslah=0;
               //Object[] row={"No.","Dokter","Tanggal","Nama Obat","Jml","Biaya Obat","Embalase","Tuslah"};
               while(rsresep.next()){
                   subtotal=subtotal+rsresep.getDouble("total");
                   ttlbiaya=ttlbiaya+rsresep.getDouble("total");
                   embalase=embalase+rsresep.getDouble("embalase");
                   ttlembalase=ttlembalase+rsresep.getDouble("embalase");
                   tuslah=tuslah+rsresep.getDouble("tuslah");
                   ttltuslah=ttltuslah+rsresep.getDouble("tuslah");
                   tabMode.addRow(new Object[]{
                       "","   "+a+". ("+rsresep.getString("no_resep")+") "+rsresep.getString("nm_pasien"),
                       rsresep.getString("tgl_perawatan")+" "+rsresep.getString("jam"),rsresep.getString("kode_brng")+" "+rsresep.getString("nama_brng"),
                       rsresep.getDouble("jml"),rsresep.getDouble("total"),rsresep.getDouble("embalase"),rsresep.getDouble("tuslah")
                   });
                   a++;
               }
               if(subtotal>0){
                   tabMode.addRow(new Object[]{"","       "+"Subtotal ",":","",null,subtotal,embalase,tuslah});
               }  
               i++;
           }
           tabMode.addRow(new Object[]{">>","Total ",":","",null,ttlbiaya,ttlembalase,ttltuslah});
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }        
    }
    
    private void prosesCari2() {             
        try{   
           this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
           Valid.tabelKosong(tabMode);
           psdokter.setString(1,"%"+kddokter.getText()+"%"); 
           rsdokter=psdokter.executeQuery();
           i=1;
           ttlbiaya=0;
           ttlembalase=0;
           ttltuslah=0;
           while(rsdokter.next()){
               tabMode.addRow(new Object[]{i+". ",rsdokter.getString("nm_dokter"),"","",null,null,null});
               psresep2.setString(1,rsdokter.getString("kd_dokter"));
               psresep2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
               psresep2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
               psresep2.setString(4,"%"+pilihancarabayar+"%");
               a=1;
               rsresep=psresep2.executeQuery();               
               subtotal=0;
               embalase=0;
               tuslah=0;
               //Object[] row={"No.","Dokter","Tanggal","Nama Obat","Jml","Biaya Obat","Embalase","Tuslah"};
               while(rsresep.next()){
                   subtotal=subtotal+rsresep.getDouble("total");
                   ttlbiaya=ttlbiaya+rsresep.getDouble("total");
                   embalase=embalase+rsresep.getDouble("embalase");
                   ttlembalase=ttlembalase+rsresep.getDouble("embalase");
                   tuslah=tuslah+rsresep.getDouble("tuslah");
                   ttltuslah=ttltuslah+rsresep.getDouble("tuslah");
                   tabMode.addRow(new Object[]{
                       "","   "+a+". ("+rsresep.getString("no_resep")+") "+rsresep.getString("nm_pasien"),
                       rsresep.getString("tgl_perawatan")+" "+rsresep.getString("jam"),rsresep.getString("kode_brng")+" "+rsresep.getString("nama_brng"),
                       rsresep.getDouble("jml"),rsresep.getDouble("total"),rsresep.getDouble("embalase"),rsresep.getDouble("tuslah")
                   });
                   a++;
               }
               if(subtotal>0){
                   tabMode.addRow(new Object[]{"","       "+"Subtotal ",":","",null,subtotal,embalase,tuslah});
               }  
               i++;
           }
           tabMode.addRow(new Object[]{">>","Total ",":","",null,ttlbiaya,ttlembalase,ttltuslah});
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }        
    }
    
}
