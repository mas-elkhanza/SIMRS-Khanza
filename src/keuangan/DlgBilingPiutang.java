/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgBiling.java
 *
 * Created on 07 Jun 10, 19:07:06
 */

package keuangan;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public class DlgBilingPiutang extends javax.swing.JDialog {
    private final DefaultTableModel tabModeRwJlDr;
    private String biaya,tambahan,totalx,jml,Tindakan_Ralan="",Laborat_Ralan="",Radiologi_Ralan="",
            Obat_Ralan="",Registrasi_Ralan="",Tambahan_Ralan="",Potongan_Ralan="",Uang_Muka_Ralan="",
            Piutang_Pasien_Ralan="",Operasi_Ralan="",Tindakan_Ranap="",Laborat_Ranap="",Radiologi_Ranap="",Obat_Ranap="",Registrasi_Ranap="",
            Tambahan_Ranap="",Potongan_Ranap="",Retur_Obat_Ranap="",Resep_Pulang_Ranap="",Kamar_Inap="",Operasi_Ranap="",
            Service_Ranap="",Harian_Ranap="",Uang_Muka_Ranap="",Piutang_Pasien_Ranap="",status="",kode_rekening="";
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private double total=0,ttl=0,y=0,ttlLaborat=0,ttlRadiologi=0,ttlOperasi=0,ttlObat=0,
            ttlRanap_Dokter=0,ttlRanap_Paramedis=0,ttlRalan_Dokter=0,ttlRalan_Paramedis=0,
            ttlTambahan=0,ttlPotongan=0,ttlKamar=0,ttlRegistrasi=0,ttlHarian=0,ttlRetur_Obat=0,
            ttlResep_Pulang=0,ttlService=0,uangmuka=0,ttlRalan_Dokter_Param=0;
    private PreparedStatement ps,psrekening;
    private ResultSet rs,rsrekening;
    private int jawab,i=0;

    /** Creates new form DlgBiling
     * @param parent
     * @param modal */
    public DlgBilingPiutang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tabModeRwJlDr=new DefaultTableModel(null,new Object[]{
            "Pilih","Keterangan","Tagihan/Tindakan/Terapi","","Biaya","Jumlah","Tambahan","Total Biaya",""}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    return true;
              }
              
              Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbAdmin.setModel(tabModeRwJlDr);

        //tbPetugas.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPetugas.getBackground()));
        tbAdmin.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbAdmin.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 9; i++) {
            TableColumn column = tbAdmin.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(130);
            }else if(i==2){
                column.setPreferredWidth(450);
            }else if(i==3){
                column.setPreferredWidth(15);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }

        tbAdmin.setDefaultRenderer(Object.class, new WarnaTable());

        try {
            psrekening=koneksi.prepareStatement("select * from set_akun");
            try {                
                rsrekening=psrekening.executeQuery();
                if(rsrekening.next()){
                    Tindakan_Ralan=rsrekening.getString("Tindakan_Ralan");
                    Laborat_Ralan=rsrekening.getString("Laborat_Ralan");
                    Radiologi_Ralan=rsrekening.getString("Radiologi_Ralan");
                    Obat_Ralan=rsrekening.getString("Obat_Ralan");
                    Registrasi_Ralan=rsrekening.getString("Registrasi_Ralan");
                    Tambahan_Ralan=rsrekening.getString("Tambahan_Ralan");
                    Potongan_Ralan=rsrekening.getString("Potongan_Ralan");
                    Uang_Muka_Ralan=rsrekening.getString("Uang_Muka_Ralan");
                    Piutang_Pasien_Ralan=rsrekening.getString("Piutang_Pasien_Ralan");
                    Operasi_Ralan=rsrekening.getString("Operasi_Ralan");
                    Tindakan_Ranap=rsrekening.getString("Tindakan_Ranap");
                    Laborat_Ranap=rsrekening.getString("Laborat_Ranap");
                    Radiologi_Ranap=rsrekening.getString("Radiologi_Ranap");
                    Obat_Ranap=rsrekening.getString("Obat_Ranap");
                    Registrasi_Ranap=rsrekening.getString("Registrasi_Ranap");
                    Tambahan_Ranap=rsrekening.getString("Tambahan_Ranap");
                    Potongan_Ranap=rsrekening.getString("Potongan_Ranap");
                    Retur_Obat_Ranap=rsrekening.getString("Retur_Obat_Ranap");
                    Resep_Pulang_Ranap=rsrekening.getString("Resep_Pulang_Ranap");
                    Kamar_Inap=rsrekening.getString("Kamar_Inap");
                    Operasi_Ranap=rsrekening.getString("Operasi_Ranap");
                    Service_Ranap=rsrekening.getString("Service_Ranap");
                    Harian_Ranap=rsrekening.getString("Harian_Ranap");
                    Uang_Muka_Ranap=rsrekening.getString("Uang_Muka_Ranap");
                    Piutang_Pasien_Ranap=rsrekening.getString("Piutang_Pasien_Ranap");  
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                } 
                if(psrekening!=null){
                    psrekening.close();
                } 
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
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

        TNoRw = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbAdmin = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setPreferredSize(new java.awt.Dimension(130, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rincian Piutang Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbAdmin.setAutoCreateRowSorter(true);
        tbAdmin.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbAdmin.setName("tbAdmin"); // NOI18N
        Scroll.setViewportView(tbAdmin);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabModeRwJlDr.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnKeluar.requestFocus();
        }else if(tabModeRwJlDr.getRowCount()!=0){            
            Sequel.queryu("delete from temporary_bayar_ranap");
                int row=tabModeRwJlDr.getRowCount();
                for(int r=0;r<row;r++){  
                    if(tabModeRwJlDr.getValueAt(r,0).toString().equals("true")){
                        biaya="";
                        try{
                            biaya=Valid.SetAngka(Double.parseDouble(tabModeRwJlDr.getValueAt(r,4).toString()));
                        }catch(Exception e){
                            biaya="";
                        }
                        tambahan="";
                        try{
                            tambahan=Valid.SetAngka(Double.parseDouble(tabModeRwJlDr.getValueAt(r,6).toString()));
                        }catch(Exception e){
                            tambahan="";
                        }
                        totalx="";
                        try{
                            totalx=Valid.SetAngka(Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString()));
                        }catch(Exception e){
                            totalx="";
                        }
                        jml="";
                        try{
                            jml=tabModeRwJlDr.getValueAt(r,5).toString().replaceAll("'","`");
                        }catch(Exception e){
                            jml="";
                        }
                        Sequel.menyimpan("temporary_bayar_ranap","'0','"+
                                        tabModeRwJlDr.getValueAt(r,1).toString().replaceAll("'","`") +"','"+
                                        tabModeRwJlDr.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                        tabModeRwJlDr.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                        biaya+"','"+
                                        jml+"','"+
                                        tambahan+"','"+
                                        totalx+"','"+tabModeRwJlDr.getValueAt(r,8).toString().replaceAll("'","`")+"','','','','','','','','',''","Rekap Nota Pembayaran"); 
                    }              
                }
                Valid.panggilUrl("billing/LaporanBilling2.php?petugas="+akses.getkode().replaceAll(" ","_")+"&ttl="+totalx);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,BtnPrint);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        i=Sequel.cariInteger("select count(no_rawat) from bayar_piutang where no_rawat=?",TNoRw.getText());
        if(i==0){
            
            jawab=JOptionPane.showConfirmDialog(null, "Yakin anda mau menghapus PIUTANG ini ????","Konfirmasi",JOptionPane.YES_NO_OPTION);
            Valid.hapusTable(tabModeRwJlDr,TNoRw,"billing","no_rawat");  
            Valid.hapusTable(tabModeRwJlDr,TNoRw,"tagihan_sadewa","no_nota");  
            Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw,"stts='Belum'");
            if(jawab==JOptionPane.YES_OPTION){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if(status.equals("Ralan")){  
                    Sequel.queryu2("delete from piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
                    Sequel.queryu2("delete from tampjurnal");                    
                    Sequel.menyimpan("tampjurnal","'"+Piutang_Pasien_Ralan+"','PIUTANG PASIEN RAWAT JALAN','0','"+(ttlLaborat+ttlRadiologi+ttlObat+ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis+ttlTambahan+ttlPotongan+ttlRegistrasi+ttlOperasi)+"'","Rekening");    
                    if((-1*ttlPotongan)>0){
                        Sequel.menyimpan("tampjurnal","'"+Potongan_Ralan+"','Potongan_Ralan','0','"+(-1*ttlPotongan)+"'","Rekening");    
                    }

                    if((ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis)>0){
                        Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Tindakan Ralan','"+(ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis)+"','0'","Rekening");    
                    }

                    if(ttlLaborat>0){
                        Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','Laborat Ralan','"+ttlLaborat+"','0'","Rekening");    
                    }

                    if(ttlRadiologi>0){
                        Sequel.menyimpan("tampjurnal","'"+Radiologi_Ralan+"','Radiologi Ralan','"+ttlRadiologi+"','0'","Rekening");    
                    }

                    if(ttlObat>0){
                        Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','Obat Ralan','"+ttlObat+"','0'","Rekening");    
                    }

                    if(ttlRegistrasi>0){
                        Sequel.menyimpan("tampjurnal","'"+Registrasi_Ralan+"','Registrasi Ralan','"+ttlRegistrasi+"','0'","Rekening");    
                    }

                    if(ttlTambahan>0){
                        Sequel.menyimpan("tampjurnal","'"+Tambahan_Ralan+"','Tambahan Ralan','"+ttlTambahan+"','0'","Rekening");    
                    }

                    if(ttlOperasi>0){
                        Sequel.menyimpan("tampjurnal","'"+Operasi_Ralan+"','Operasi Ralan','"+ttlOperasi+"','0'","Rekening");    
                    }

                    if(uangmuka>0){
                        //Sequel.queryu2("delete from tampjurnal");                    
                        Sequel.menyimpan("tampjurnal","'"+Uang_Muka_Ralan+"','Uang Muka','"+(uangmuka)+"','0'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+kode_rekening+"','"+"Bayar"+"','0','"+(uangmuka)+"'","Rekening"); 
                        //jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBATALAN BAYAR PIUTANG RAWAT JALAN");
                    }

                    jur.simpanJurnal(TNoRw.getText(),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PIUTANG PASIEN RAWAT JALAN");
                }else if(status.equals("Ranap")){
                    Sequel.queryu2("delete from piutang_pasien where no_rawat='"+TNoRw.getText()+"'");
                    Sequel.queryu2("delete from tampjurnal");                    
                    Sequel.menyimpan("tampjurnal","'"+Piutang_Pasien_Ranap+"','PIUTANG PASIEN RAWAT INAP','0','"+(ttlLaborat+ttlRadiologi+ttlOperasi+ttlObat+ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+
                                ttlRalan_Paramedis+ttlTambahan+ttlPotongan+ttlKamar+ttlRegistrasi+ttlHarian+ttlRetur_Obat+ttlResep_Pulang+ttlService)+"'","Rekening");    
                    if((-1*ttlPotongan)>0){
                        Sequel.menyimpan("tampjurnal","'"+Potongan_Ranap+"','Potongan Ranap','0','"+(-1*ttlPotongan)+"'","Rekening");    
                    }

                    if((-1*ttlRetur_Obat)>0){
                        Sequel.menyimpan("tampjurnal","'"+Retur_Obat_Ranap+"','Retur Obat Ranap','0','"+(-1*ttlRetur_Obat)+"'","Rekening");    
                    }

                    if((ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis)>0){
                        Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Tindakan Ranap','"+(ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis)+"','0'","Rekening");    
                    }

                    if(ttlLaborat>0){
                        Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','Laborat Ranap','"+ttlLaborat+"','0'","Rekening");    
                    }

                    if(ttlRadiologi>0){
                        Sequel.menyimpan("tampjurnal","'"+Radiologi_Ranap+"','Radiologi_Ranap','"+ttlRadiologi+"','0'","Rekening");    
                    }

                    if(ttlObat>0){
                        Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','Obat Ranap','"+ttlObat+"','0'","Rekening");    
                    }

                    if(ttlRegistrasi>0){
                        Sequel.menyimpan("tampjurnal","'"+Registrasi_Ranap+"','Registrasi Ranap','"+ttlRegistrasi+"','0'","Rekening");    
                    }

                    if(ttlTambahan>0){
                        Sequel.menyimpan("tampjurnal","'"+Tambahan_Ranap+"','Tambahan Ranap','"+ttlTambahan+"','0'","Rekening");    
                    }

                    if(ttlResep_Pulang>0){
                        Sequel.menyimpan("tampjurnal","'"+Resep_Pulang_Ranap+"','Resep Pulang Ranap','"+ttlResep_Pulang+"','0'","Rekening");    
                    }

                    if(ttlKamar>0){
                        Sequel.menyimpan("tampjurnal","'"+Kamar_Inap+"','Kamar Inap','"+ttlKamar+"','0'","Rekening");    
                    }

                    if(ttlOperasi>0){
                        Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Operasi Ranap','"+ttlOperasi+"','0'","Rekening");    
                    }

                    if(ttlHarian>0){
                        Sequel.menyimpan("tampjurnal","'"+Harian_Ranap+"','Harian Ranap','"+ttlHarian+"','0'","Rekening");    
                    }    

                    if(ttlService>0){
                        Sequel.menyimpan("tampjurnal","'"+Service_Ranap+"','Biaya Service Ranap','"+ttlService+"','0'","Rekening");    
                    }  

                    if((uangmuka)>0){
                         //Sequel.queryu2("delete from tampjurnal");
                         Sequel.menyimpan("tampjurnal","'"+Uang_Muka_Ranap+"','BAYAR PIUTANG','"+(uangmuka)+"','0'","Rekening");    
                         Sequel.menyimpan("tampjurnal","'"+kode_rekening+"','"+"Bayar"+"','0','"+(uangmuka)+"'","Rekening");                  
                         //jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBATALAN BAYAR PIUTANG RAWAT INAP");
                    }  

                    jur.simpanJurnal(TNoRw.getText(),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PIUTANG PASIEN RAWAT INAP");
                }
                this.setCursor(Cursor.getDefaultCursor());
            }
            
            JOptionPane.showMessageDialog(null,"Proses hapus selesai, silahkan lakukan refresh\ndi form Data Tagihan Piutang Pasien..!!");
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Sudah ada data pembayaran piutang pasien.\nSilahkan hapus terlebih dahulu data pembayaran piutang tersebut..!!");
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed



    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBilingPiutang dialog = new DlgBilingPiutang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ScrollPane Scroll;
    public widget.TextBox TNoRw;
    private widget.InternalFrame internalFrame1;
    private widget.panelisi panelisi1;
    private widget.Table tbAdmin;
    // End of variables declaration//GEN-END:variables

    public void isRawat(String norawat,double uangmuka2) {
        uangmuka=uangmuka2;
        status=Sequel.cariIsi("select status_lanjut from reg_periksa where no_rawat=?",norawat);        
        TNoRw.setText(norawat);
             try{
                ps=koneksi.prepareStatement("select no,nm_perawatan, if(biaya<>0,biaya,null) as satu, if(jumlah<>0,jumlah,null) as dua,"+
                                 "if(tambahan<>0,tambahan,null) as tiga, if(totalbiaya<>0,totalbiaya,null) as empat,pemisah,status "+
                                 "from billing where no_rawat=?  order by noindex"); 
                try {
                    ps.setString(1, norawat);
                    rs=ps.executeQuery();
                    total=0;
                    while(rs.next()){
                        if((!rs.getString("no").contains("Tagihan + PPN"))&&(!rs.getString("no").contains("PPN("))){                    
                            tabModeRwJlDr.addRow(new Object[]{true,rs.getString("no"),
                                        rs.getString("nm_perawatan"),
                                        rs.getString("pemisah"),
                                        rs.getObject("satu"),
                                        rs.getObject("dua"),
                                        rs.getObject("tiga"),
                                        rs.getObject("empat"),
                                        rs.getString("status")});
                        }
                    }
                    
                    if(status.equals("Ralan")){
                        kode_rekening=Sequel.cariIsi("select kd_rek from nota_jalan where no_rawat=?",norawat);
                        ttl=0;
                        y=0;
                        ttlLaborat=0;ttlRadiologi=0;ttlObat=0;ttlRalan_Dokter=0;ttlRalan_Paramedis=0;ttlTambahan=0;
                        ttlPotongan=0;ttlRegistrasi=0;ttlRalan_Dokter_Param=0;ttlOperasi=0;
                        for(i=0;i<tbAdmin.getRowCount();i++){ 
                            try {                
                               y=Double.parseDouble(tabModeRwJlDr.getValueAt(i,7).toString());  
                            } catch (Exception e) {
                                y=0; 
                            }
                            switch (tabModeRwJlDr.getValueAt(i,8).toString()) {
                                case "Laborat":
                                        ttlLaborat=ttlLaborat+y;
                                        break;
                                case "Radiologi":
                                        ttlRadiologi=ttlRadiologi+y;
                                        break;
                                case "Obat":
                                        ttlObat=ttlObat+y;
                                        break;
                                case "Ralan Dokter":
                                        ttlRalan_Dokter=ttlRalan_Dokter+y;
                                        break;     
                                case "Ralan Dokter Paramedis":
                                        ttlRalan_Dokter_Param=ttlRalan_Dokter_Param+y;
                                        break;    
                                case "Ralan Paramedis":
                                        ttlRalan_Paramedis=ttlRalan_Paramedis+y;
                                        break;
                                case "Tambahan":
                                        ttlTambahan=ttlTambahan+y;
                                        break;
                                case "Potongan":
                                        ttlPotongan=ttlPotongan+y;
                                        break;
                                case "Registrasi":
                                        ttlRegistrasi=ttlRegistrasi+y;
                                        break;
                                case "Operasi":
                                        ttlOperasi=ttlOperasi+y;
                                        break;
                            }                                
                            ttl=ttl+y;             
                        }
                    }else if(status.equals("Ranap")){
                        kode_rekening=Sequel.cariIsi("select kd_rek from nota_inap where no_rawat=?",norawat);
                        ttl=0;
                        y=0;
                        ttlLaborat=0;ttlRadiologi=0;ttlOperasi=0;ttlObat=0;ttlRanap_Dokter=0;ttlRanap_Paramedis=0;ttlRalan_Dokter=0;
                        ttlRalan_Paramedis=0;ttlTambahan=0;ttlPotongan=0;ttlKamar=0;ttlRegistrasi=0;ttlHarian=0;ttlRetur_Obat=0;ttlResep_Pulang=0;
                        ttlService=0;
                        int row=tabModeRwJlDr.getRowCount();
                        if(row>0){
                            for(int r=0;r<row;r++){             
                                try {
                                    y=Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());  
                                } catch (Exception e) {
                                   y=0;
                                }  
                                switch (tabModeRwJlDr.getValueAt(r,8).toString()) {
                                    case "Laborat":
                                        ttlLaborat=ttlLaborat+y;
                                        break;
                                    case "Radiologi":
                                        ttlRadiologi=ttlRadiologi+y;
                                        break;
                                    case "Operasi":
                                        ttlOperasi=ttlOperasi+y;
                                        break;
                                    case "Obat":
                                        ttlObat=ttlObat+y;
                                        break;
                                    case "Ranap Dokter":
                                        ttlRanap_Dokter=ttlRanap_Dokter+y;
                                        break;
                                    case "Ranap Dokter Paramedis":
                                        ttlRanap_Dokter=ttlRanap_Dokter+y;
                                        break;
                                    case "Ranap Paramedis":
                                        ttlRanap_Paramedis=ttlRanap_Paramedis+y;
                                        break;
                                    case "Ralan Dokter":
                                        ttlRalan_Dokter=ttlRalan_Dokter+y;
                                        break;
                                    case "Ralan Dokter Paramedis":
                                        ttlRalan_Dokter=ttlRalan_Dokter+y;
                                        break;
                                    case "Ralan Paramedis":
                                        ttlRalan_Paramedis=ttlRalan_Paramedis+y;
                                        break;
                                    case "Tambahan":
                                        ttlTambahan=ttlTambahan+y;
                                        break;
                                    case "Potongan":
                                        ttlPotongan=ttlPotongan+y;
                                        break;
                                    case "Kamar":
                                        ttlKamar=ttlKamar+y;
                                        break;
                                    case "Registrasi":
                                        ttlRegistrasi=ttlRegistrasi+y;
                                        break;
                                    case "Harian":
                                        ttlHarian=ttlHarian+y;
                                        break;
                                    case "Retur Obat":
                                        ttlRetur_Obat=ttlRetur_Obat+y;
                                        break;
                                    case "Resep Pulang":
                                        ttlResep_Pulang=ttlResep_Pulang+y;
                                        break;
                                    case "Service":
                                        ttlService=ttlService+y;
                                        break;
                                }
                                ttl=ttl+y;
                            }              
                        }        
                    }        
                } catch (Exception e) {
                    System.out.println(e);
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
    }
}
