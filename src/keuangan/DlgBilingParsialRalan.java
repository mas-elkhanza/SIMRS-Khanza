/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keuangan;

import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author khanzamedia
 */
public class DlgBilingParsialRalan extends javax.swing.JDialog {
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private WarnaTable2 warna=new WarnaTable2();
    private final DefaultTableModel tabModeAkunBayar,TabModeTindakanDr,
            tabModeBilling,TabModeTindakanDrBayar,TabModeTindakanPr,
            TabModeTindakanPrBayar,TabModeTindakanDrPr,TabModeTindakanDrPrBayar,
            TabModeRadiologi,TabModeRadiologiBayar,TabModeLaborat,TabModeDetailLaborat,
            TabModeLaboratBayar,TabModeDetailLaboratBayar,TabModeObat,TabModeObatBayar;
    private int row2=0,r=0,i=0,countbayar=0,z=0,jml=0,jmlradiologi=0,jmllaborat=0,index=0,jmlreg=0;
    private String[] Nama_Akun_Bayar,Kode_Rek_Bayar,Bayar,PPN_Persen,PPN_Besar;
    private boolean[] pilih; 
    private boolean statushapus=false;
    private String[] kode,nama,kategori,tanggal,jam,noorder;
    private double[] totaltnd,bagianrs,bhp,jmdokter,jmperawat,kso,menejemen,tarif_perujuk;
    private PreparedStatement psakunbayar,pstindakan,psset_tarif,psreg,
            pscaripoli,pscarialamat,psdokterralan,psrekening,psbiling;
    private ResultSet rsakunbayar,rstindakan,rsset_tarif,rsbayar,rsreg,rscaripoli,
            rscarialamat,rsdokterralan,rsrekening;
    private String noorderradiologi="",noorderlaborat="",jmls="",kd_pj="",kd_poli="",poli_ralan="Yes",cara_bayar_ralan="Yes",cara_bayar_lab="Yes",kelas_lab="Yes",
            NoNota="",sqlpscaripoli="select nm_poli from poliklinik where kd_poli=?",
            sqlpscarialamat="select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?",notaralan="No",centangdokterralan="No",
            rinciandokterralan="No",sqlpsdokterralan="select dokter.nm_dokter from reg_periksa "+
                            "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                            "where no_rawat=?",
            sqlpsbiling="insert into billing values(?,?,?,?,?,?,?,?,?,?,?)",
            biaya="",tambahan="",totals="",sqlpsrekening="select * from set_akun_ralan",
            cara_bayar_radiologi="No",kelas_radiologi="No",tampilkan_ppnobat_ralan="",
            Tindakan_Ralan="",Laborat_Ralan="",Radiologi_Ralan="",
            Registrasi_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",
            Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan="",
            Utang_Jasa_Medik_Paramedis_Tindakan_Ralan="",Beban_KSO_Tindakan_Ralan="",
            Utang_KSO_Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Laborat_Ralan="",
            Utang_Jasa_Medik_Dokter_Laborat_Ralan="",Beban_Jasa_Medik_Petugas_Laborat_Ralan="",
            Utang_Jasa_Medik_Petugas_Laborat_Ralan="",Beban_Kso_Laborat_Ralan="",
            Utang_Kso_Laborat_Ralan="",HPP_Persediaan_Laborat_Rawat_Jalan="",
            Persediaan_BHP_Laborat_Rawat_Jalan="",Beban_Jasa_Medik_Dokter_Radiologi_Ralan="",
            Utang_Jasa_Medik_Dokter_Radiologi_Ralan="",Beban_Jasa_Medik_Petugas_Radiologi_Ralan="",
            Utang_Jasa_Medik_Petugas_Radiologi_Ralan="",Beban_Kso_Radiologi_Ralan="",
            Utang_Kso_Radiologi_Ralan="",HPP_Persediaan_Radiologi_Rawat_Jalan="",
            Persediaan_BHP_Radiologi_Rawat_Jalan="",HPP_Obat_Rawat_Jalan="",
            Persediaan_Obat_Rawat_Jalan="",Beban_Jasa_Sarana_Tindakan_Ralan,
            Utang_Jasa_Sarana_Tindakan_Ralan,Beban_Jasa_Menejemen_Tindakan_Ralan,
            Utang_Jasa_Menejemen_Tindakan_Ralan,HPP_BHP_Tindakan_Ralan,
            Persediaan_BHP_Tindakan_Ralan,Beban_Jasa_Sarana_Laborat_Ralan,
            Utang_Jasa_Sarana_Laborat_Ralan,Beban_Jasa_Perujuk_Laborat_Ralan,
            Utang_Jasa_Perujuk_Laborat_Ralan,Beban_Jasa_Menejemen_Laborat_Ralan,
            Utang_Jasa_Menejemen_Laborat_Ralan,Beban_Jasa_Sarana_Radiologi_Ralan,
            Utang_Jasa_Sarana_Radiologi_Ralan,Beban_Jasa_Perujuk_Radiologi_Ralan,
            Utang_Jasa_Perujuk_Radiologi_Ralan,Beban_Jasa_Menejemen_Radiologi_Ralan,
            Utang_Jasa_Menejemen_Radiologi_Ralan,Suspen_Piutang_Tindakan_Ralan,
            Suspen_Piutang_Obat_Ralan;
    private double ppnobat=0,ttl=0,y=0,ttlLaborat=0,ttlRadiologi=0,
            ttlObat=0,ttlRalan_Dokter=0,ttlRalan_Paramedis=0,ttlRegistrasi=0,
            ttlRalan_Dokter_Param=0,bayar=0,total=0,ppn=0,besarppn=0,subttl=0,
            tagihanppn=0,kekurangan=0,itembayar=0,Jasa_Medik_Dokter_Tindakan_Ralan=0,Jasa_Medik_Paramedis_Tindakan_Ralan=0,
            KSO_Tindakan_Ralan=0,Jasa_Sarana_Tindakan_Ralan=0,BHP_Tindakan_Ralan=0,Jasa_Menejemen_Tindakan_Ralan=0,
            Jasa_Medik_Dokter_Laborat_Ralan=0,Jasa_Medik_Petugas_Laborat_Ralan=0,Kso_Laborat_Ralan=0,Persediaan_Laborat_Rawat_Jalan=0,
            Jasa_Sarana_Laborat_Ralan=0,Jasa_Perujuk_Laborat_Ralan=0,Jasa_Menejemen_Laborat_Ralan=0,Jasa_Medik_Dokter_Radiologi_Ralan=0,
            Jasa_Medik_Petugas_Radiologi_Ralan=0,Kso_Radiologi_Ralan=0,Persediaan_Radiologi_Rawat_Jalan=0,Jasa_Sarana_Radiologi_Ralan=0,
            Jasa_Perujuk_Radiologi_Ralan=0,Jasa_Menejemen_Radiologi_Ralan=0,Obat_Rawat_Jalan=0,Suspen_Tindakan_Ralan;
    private boolean sukses=false;
    
    /**
     * Creates new form DlgBillingParsialRalan
     */
    public DlgBilingParsialRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        kdptg.setDocument(new batasInput((byte)20).getKata(kdptg));
        kdptg2.setDocument(new batasInput((byte)20).getKata(kdptg2));
        KdDok.setDocument(new batasInput((byte)20).getKata(KdDok));
        KdDok2.setDocument(new batasInput((byte)20).getKata(KdDok2));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCariBayarActionPerformed(null);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCariBayarActionPerformed(null);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCariBayarActionPerformed(null);
                    }
                }
            });
        } 
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    if(TabRawat.getSelectedIndex()==0){
                        KdDok.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        KdDok.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==2){
                        KdDok2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        KdDok2.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==3){
                        KdDokPerujukRad.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokterPerujukRad.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        KdDokPerujukRad.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==4){
                        KdDokPerujukLab.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokterPerujukLab.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        KdDokPerujukLab.requestFocus();
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){   
                    if(TabRawat.getSelectedIndex()==1){
                        kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        TPerawat.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdptg.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==2){
                        kdptg2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        TPerawat2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdptg2.requestFocus();
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
        
        tabModeAkunBayar=new DefaultTableModel(null,new Object[]{"Nama Akun","Kode Rek","Bayar","PPN(%)","PPN(Rp)"}){             
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==2)) {
                    a=true;
                }
                return a;
            }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbAkunBayar.setModel(tabModeAkunBayar);
        tbAkunBayar.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbAkunBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbAkunBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(405);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(142);
            }else if(i==3){
                column.setPreferredWidth(60);
            }else if(i==4){
                column.setPreferredWidth(110);
            }
        }
        warna.kolom=2;
        tbAkunBayar.setDefaultRenderer(Object.class,warna);
        
        TabModeTindakanDr=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan","Kategori","Tarif","Bagian RS","BHP",
                "JM Dokter","JM Perawat","KSO","Menejemen","Tanggal","Jam"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Object.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakanDr.setModel(TabModeTindakanDr);
        tbTindakanDr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakanDr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 13; i++) {
            TableColumn column = tbTindakanDr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbTindakanDr.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakanPr=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan","Kategori","Tarif","Bagian RS","BHP",
                "JM Dokter","JM Perawat","KSO","Menejemen","Tanggal","Jam"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Object.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakanPr.setModel(TabModeTindakanPr);
        tbTindakanPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakanPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 13; i++) {
            TableColumn column = tbTindakanPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbTindakanPr.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakanDrPr=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan","Kategori","Tarif","Bagian RS","BHP",
                "JM Dokter","JM Perawat","KSO","Menejemen","Tanggal","Jam"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Object.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakanDrPr.setModel(TabModeTindakanDrPr);
        tbTindakanDrPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakanDrPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 13; i++) {
            TableColumn column = tbTindakanDrPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbTindakanDrPr.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeRadiologi=new DefaultTableModel(null,new Object[]{
            "P","Kode Periksa","Nama Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk",
            "Tarif Dokter","Tarif Petugas","Kso","Menejemen","Tanggal","Jam","No.Order"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRadiologi.setModel(TabModeRadiologi);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbRadiologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 14; i++) {
            TableColumn column = tbRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(305);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeLaborat=new DefaultTableModel(null,new Object[]{
            "P","Kode Periksa","Nama Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk",
            "Tarif Dokter","Tarif Petugas","Kso","Menejemen","Tanggal","Jam","No.Order"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbLaborat.setModel(TabModeLaborat);        
        
        tbLaborat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLaborat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 14; i++) {
            TableColumn column = tbLaborat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(305);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbLaborat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeDetailLaborat=new DefaultTableModel(null,new Object[]{
            "P","ID Periksa","Nama Sub Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk",
            "Tarif Dokter","Tarif Petugas","Kso","Menejemen","Tanggal","Jam","No.Order","Kode Jenis"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDetailLaborat.setModel(TabModeDetailLaborat);        
        
        tbDetailLaborat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailLaborat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 15; i++) {
            TableColumn column = tbDetailLaborat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==2){
                column.setPreferredWidth(405);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDetailLaborat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeObat=new DefaultTableModel(null,new Object[]{
                "P","Kode Obat","Nama Obat","Jml","Biaya","HPP","Tanggal","Jam"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,  
                java.lang.Object.class, java.lang.Double.class,java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(TabModeObat);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 8; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(295);
            }else if(i==3){
                column.setPreferredWidth(35);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeObatBayar=new DefaultTableModel(null,new Object[]{
                "P","Kode Obat","Nama Obat","Jml","Biaya","HPP","Tanggal","Jam"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,  
                java.lang.Object.class, java.lang.Double.class,java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObatBayar.setModel(TabModeObatBayar);
        tbObatBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 8; i++) {
            TableColumn column = tbObatBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(295);
            }else if(i==3){
                column.setPreferredWidth(35);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObatBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeDetailLaboratBayar=new DefaultTableModel(null,new Object[]{
            "P","ID Periksa","Nama Sub Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk",
            "Tarif Dokter","Tarif Petugas","Kso","Menejemen","Tanggal","Jam","No.Order","Kode Jenis","Status"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDetailLaboratBayar.setModel(TabModeDetailLaboratBayar);        
        
        tbDetailLaboratBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailLaboratBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 16; i++) {
            TableColumn column = tbDetailLaboratBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==2){
                column.setPreferredWidth(405);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDetailLaboratBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakanDrBayar=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan","Kategori","Tarif","Bagian RS","BHP",
                "JM Dokter","JM Perawat","KSO","Menejemen","Tanggal","Jam","Status"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Object.class, 
                java.lang.Object.class,java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakanDrBayar.setModel(TabModeTindakanDrBayar);
        tbTindakanDrBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakanDrBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 14; i++) {
            TableColumn column = tbTindakanDrBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbTindakanDrBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakanPrBayar=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan","Kategori","Tarif","Bagian RS","BHP",
                "JM Dokter","JM Perawat","KSO","Menejemen","Tanggal","Jam","Status"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Object.class, 
                java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakanPrBayar.setModel(TabModeTindakanPrBayar);
        tbTindakanPrBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakanPrBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 14; i++) {
            TableColumn column = tbTindakanPrBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbTindakanPrBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakanDrPrBayar=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan","Kategori","Tarif","Bagian RS","BHP",
                "JM Dokter","JM Perawat","KSO","Menejemen","Tanggal","Jam","Status"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Object.class, 
                java.lang.Object.class,java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakanDrPrBayar.setModel(TabModeTindakanDrPrBayar);
        tbTindakanDrPrBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakanDrPrBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 14; i++) {
            TableColumn column = tbTindakanDrPrBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(240);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbTindakanDrPrBayar.setDefaultRenderer(Object.class, new WarnaTable());
                
        TabModeRadiologiBayar=new DefaultTableModel(null,new Object[]{
            "P","Kode Periksa","Nama Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk",
            "Tarif Dokter","Tarif Petugas","Kso","Menejemen","Tanggal","Jam","No.Order","Status"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRadiologiBayar.setModel(TabModeRadiologiBayar);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbRadiologiBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRadiologiBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 15; i++) {
            TableColumn column = tbRadiologiBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(305);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbRadiologiBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeLaboratBayar=new DefaultTableModel(null,new Object[]{
            "P","Kode Periksa","Nama Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk",
            "Tarif Dokter","Tarif Petugas","Kso","Menejemen","Tanggal","Jam","No.Order","Status"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbLaboratBayar.setModel(TabModeLaboratBayar);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbLaboratBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLaboratBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 15; i++) {
            TableColumn column = tbLaboratBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(305);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbLaboratBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeBilling=new DefaultTableModel(null,new Object[]{
            "Keterangan","Tagihan/Tindakan/Terapi","","Biaya","Jml","Total Biaya",""}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if ((colIndex==6)||(colIndex==0)) {
                        a=true;
                    }
                    return a;
              }
              
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBilling.setModel(tabModeBilling);
        tbBilling.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbBilling.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbBilling.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(160);
            }else if(i==1){
                column.setPreferredWidth(452);
            }else if(i==2){
                column.setPreferredWidth(10);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(30);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                //column.setPreferredWidth(70);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }

        tbBilling.setDefaultRenderer(Object.class, new WarnaTable());
        
        try {
            psset_tarif=koneksi.prepareStatement("select * from set_tarif");
            try {
                rsset_tarif=psset_tarif.executeQuery();
                if(rsset_tarif.next()){
                    poli_ralan=rsset_tarif.getString("poli_ralan");
                    cara_bayar_ralan=rsset_tarif.getString("cara_bayar_ralan");
                    cara_bayar_radiologi=rsset_tarif.getString("cara_bayar_radiologi");
                    kelas_radiologi=rsset_tarif.getString("kelas_radiologi");
                    cara_bayar_lab=rsset_tarif.getString("cara_bayar_lab");
                    kelas_lab=rsset_tarif.getString("kelas_lab");
                }else{
                    poli_ralan="Yes";
                    cara_bayar_ralan="Yes";
                    cara_bayar_radiologi="Yes";
                    kelas_radiologi="Yes";
                    cara_bayar_lab="Yes";
                    kelas_lab="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsset_tarif != null){
                    rsset_tarif.close();
                }
                if(psset_tarif != null){
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        } 
        
        try {
            notaralan=Sequel.cariIsi("select cetaknotasimpanralan from set_nota"); 
            centangdokterralan=Sequel.cariIsi("select centangdokterralan from set_nota"); 
            rinciandokterralan=Sequel.cariIsi("select rinciandokterralan from set_nota"); 
            tampilkan_ppnobat_ralan=Sequel.cariIsi("select tampilkan_ppnobat_ralan from set_nota"); 
        } catch (Exception e) {
            notaralan="No"; 
            centangdokterralan="No";
            rinciandokterralan="No";
            tampilkan_ppnobat_ralan="No";
        }
        
        try {
            psrekening=koneksi.prepareStatement(sqlpsrekening);
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Tindakan_Ralan=rsrekening.getString("Tindakan_Ralan");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Beban_KSO_Tindakan_Ralan=rsrekening.getString("Beban_KSO_Tindakan_Ralan");
                    Utang_KSO_Tindakan_Ralan=rsrekening.getString("Utang_KSO_Tindakan_Ralan");
                    Beban_Jasa_Sarana_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Sarana_Tindakan_Ralan");
                    Utang_Jasa_Sarana_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Sarana_Tindakan_Ralan");
                    Beban_Jasa_Menejemen_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");
                    Utang_Jasa_Menejemen_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");
                    HPP_BHP_Tindakan_Ralan=rsrekening.getString("HPP_BHP_Tindakan_Ralan");
                    Persediaan_BHP_Tindakan_Ralan=rsrekening.getString("Persediaan_BHP_Tindakan_Ralan");
                    Laborat_Ralan=rsrekening.getString("Laborat_Ralan");
                    Beban_Jasa_Medik_Dokter_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Laborat_Ralan");
                    Utang_Jasa_Medik_Dokter_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Laborat_Ralan");
                    Beban_Jasa_Medik_Petugas_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Medik_Petugas_Laborat_Ralan");
                    Utang_Jasa_Medik_Petugas_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Medik_Petugas_Laborat_Ralan");
                    Beban_Kso_Laborat_Ralan=rsrekening.getString("Beban_Kso_Laborat_Ralan");
                    Utang_Kso_Laborat_Ralan=rsrekening.getString("Utang_Kso_Laborat_Ralan");
                    HPP_Persediaan_Laborat_Rawat_Jalan=rsrekening.getString("HPP_Persediaan_Laborat_Rawat_Jalan");
                    Persediaan_BHP_Laborat_Rawat_Jalan=rsrekening.getString("Persediaan_BHP_Laborat_Rawat_Jalan");
                    Beban_Jasa_Sarana_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Sarana_Laborat_Ralan");
                    Utang_Jasa_Sarana_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Sarana_Laborat_Ralan");
                    Beban_Jasa_Perujuk_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Perujuk_Laborat_Ralan");
                    Utang_Jasa_Perujuk_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Perujuk_Laborat_Ralan");
                    Beban_Jasa_Menejemen_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Menejemen_Laborat_Ralan");
                    Utang_Jasa_Menejemen_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Menejemen_Laborat_Ralan");
                    Radiologi_Ralan=rsrekening.getString("Radiologi_Ralan");
                    Beban_Jasa_Medik_Dokter_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ralan");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ralan");
                    Beban_Jasa_Medik_Petugas_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ralan");
                    Utang_Jasa_Medik_Petugas_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ralan");
                    Beban_Kso_Radiologi_Ralan=rsrekening.getString("Beban_Kso_Radiologi_Ralan");
                    Utang_Kso_Radiologi_Ralan=rsrekening.getString("Utang_Kso_Radiologi_Ralan");
                    HPP_Persediaan_Radiologi_Rawat_Jalan=rsrekening.getString("HPP_Persediaan_Radiologi_Rawat_Jalan");
                    Persediaan_BHP_Radiologi_Rawat_Jalan=rsrekening.getString("Persediaan_BHP_Radiologi_Rawat_Jalan");
                    Beban_Jasa_Sarana_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Sarana_Radiologi_Ralan");
                    Utang_Jasa_Sarana_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Sarana_Radiologi_Ralan");
                    Beban_Jasa_Perujuk_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Perujuk_Radiologi_Ralan");
                    Utang_Jasa_Perujuk_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Perujuk_Radiologi_Ralan");
                    Beban_Jasa_Menejemen_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Menejemen_Radiologi_Ralan");
                    Utang_Jasa_Menejemen_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Menejemen_Radiologi_Ralan");
                    HPP_Obat_Rawat_Jalan=rsrekening.getString("HPP_Obat_Rawat_Jalan");
                    Persediaan_Obat_Rawat_Jalan=rsrekening.getString("Persediaan_Obat_Rawat_Jalan");
                    Suspen_Piutang_Tindakan_Ralan=rsrekening.getString("Suspen_Piutang_Tindakan_Ralan");
                    Suspen_Piutang_Obat_Ralan=rsrekening.getString("Suspen_Piutang_Obat_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopupTindakanDr = new javax.swing.JPopupMenu();
        ppBersihkanTindakanDr = new javax.swing.JMenuItem();
        ppSemuaTindakanDr = new javax.swing.JMenuItem();
        PopupTindakanDrBayar = new javax.swing.JPopupMenu();
        ppBersihkanTindakanDrBayar = new javax.swing.JMenuItem();
        ppSemuaTindakanDrBayar = new javax.swing.JMenuItem();
        PopupTindakanPr = new javax.swing.JPopupMenu();
        ppBersihkanTindakanPr = new javax.swing.JMenuItem();
        ppSemuaTindakanPr = new javax.swing.JMenuItem();
        PopupTindakanPrBayar = new javax.swing.JPopupMenu();
        ppBersihkanTindakanPrBayar = new javax.swing.JMenuItem();
        ppSemuaTindakanPrBayar = new javax.swing.JMenuItem();
        PopupTindakanDrPr = new javax.swing.JPopupMenu();
        ppBersihkanTindakanDrPr = new javax.swing.JMenuItem();
        ppSemuaTindakanDrPr = new javax.swing.JMenuItem();
        PopupTindakanDrPrBayar = new javax.swing.JPopupMenu();
        ppBersihkanTindakanDrPrBayar = new javax.swing.JMenuItem();
        ppSemuaTindakanDrPrBayar = new javax.swing.JMenuItem();
        PopupRadiologi = new javax.swing.JPopupMenu();
        ppBersihkanRadiologi = new javax.swing.JMenuItem();
        ppSemuaRadiologi = new javax.swing.JMenuItem();
        PopupRadiologiBayar = new javax.swing.JPopupMenu();
        ppBersihkanRadiologiBayar = new javax.swing.JMenuItem();
        ppSemuaRadiologiBayar = new javax.swing.JMenuItem();
        PopupLaborat = new javax.swing.JPopupMenu();
        ppBersihkanLaborat = new javax.swing.JMenuItem();
        ppSemuaLaborat = new javax.swing.JMenuItem();
        PopupDetailLaborat = new javax.swing.JPopupMenu();
        ppBersihkanDetailLaborat = new javax.swing.JMenuItem();
        ppSemuaDetailLaborat = new javax.swing.JMenuItem();
        PopupLaboratBayar = new javax.swing.JPopupMenu();
        ppBersihkanLaboratBayar = new javax.swing.JMenuItem();
        ppSemuaLaboratBayar = new javax.swing.JMenuItem();
        PopupDetailLaboratBayar = new javax.swing.JPopupMenu();
        ppBersihkanDetailLaboratBayar = new javax.swing.JMenuItem();
        ppSemuaDetailLaboratBayar = new javax.swing.JMenuItem();
        PopupObat = new javax.swing.JPopupMenu();
        ppBersihkanObat = new javax.swing.JMenuItem();
        ppSemuaObat = new javax.swing.JMenuItem();
        PopupObatBayar = new javax.swing.JPopupMenu();
        ppBersihkanObatBayar = new javax.swing.JMenuItem();
        ppSemuaObatBayar = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass1 = new widget.panelisi();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel4 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        panelGlass8 = new widget.panelisi();
        label9 = new widget.Label();
        TCariTindakan = new widget.TextBox();
        BtnCariTindakan = new widget.Button();
        BtnAllTindakan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnKeluar = new widget.Button();
        jLabel9 = new widget.Label();
        TagihanPPN = new widget.TextBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        scrollPane3 = new widget.ScrollPane();
        tbAkunBayar = new widget.Table();
        BtnCariBayar = new widget.Button();
        jLabel7 = new widget.Label();
        TKembali = new widget.TextBox();
        BtnSimpan = new widget.Button();
        BtnNota = new widget.Button();
        TtlSemua = new widget.TextBox();
        jLabel11 = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass7 = new widget.panelisi();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        TDokter = new widget.TextBox();
        Scroll3 = new widget.ScrollPane();
        tbTindakanDr = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbTindakanDrBayar = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        panelGlass10 = new widget.panelisi();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        TPerawat = new widget.TextBox();
        Scroll5 = new widget.ScrollPane();
        tbTindakanPr = new widget.Table();
        Scroll6 = new widget.ScrollPane();
        tbTindakanPrBayar = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        panelGlass11 = new widget.panelisi();
        jLabel14 = new widget.Label();
        kdptg2 = new widget.TextBox();
        BtnSeekPetugas2 = new widget.Button();
        TPerawat2 = new widget.TextBox();
        jLabel12 = new widget.Label();
        KdDok2 = new widget.TextBox();
        TDokter2 = new widget.TextBox();
        BtnSeekDokter2 = new widget.Button();
        Scroll7 = new widget.ScrollPane();
        tbTindakanDrPr = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbTindakanDrPrBayar = new widget.Table();
        internalFrame5 = new widget.InternalFrame();
        Scroll10 = new widget.ScrollPane();
        tbRadiologi = new widget.Table();
        Scroll11 = new widget.ScrollPane();
        tbRadiologiBayar = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel8 = new widget.Label();
        KdDokPerujukRad = new widget.TextBox();
        BtnSeekDokter1 = new widget.Button();
        TDokterPerujukRad = new widget.TextBox();
        internalFrame6 = new widget.InternalFrame();
        panelGlass12 = new widget.panelisi();
        jLabel10 = new widget.Label();
        KdDokPerujukLab = new widget.TextBox();
        BtnSeekDokter3 = new widget.Button();
        TDokterPerujukLab = new widget.TextBox();
        internalFrame7 = new widget.InternalFrame();
        TabRawatLaborat = new javax.swing.JTabbedPane();
        Scroll12 = new widget.ScrollPane();
        tbLaborat = new widget.Table();
        Scroll14 = new widget.ScrollPane();
        tbDetailLaborat = new widget.Table();
        internalFrame8 = new widget.InternalFrame();
        TabRawatLaboratBayar = new javax.swing.JTabbedPane();
        Scroll15 = new widget.ScrollPane();
        tbLaboratBayar = new widget.Table();
        Scroll16 = new widget.ScrollPane();
        tbDetailLaboratBayar = new widget.Table();
        internalFrame9 = new widget.InternalFrame();
        Scroll13 = new widget.ScrollPane();
        tbObat = new widget.Table();
        Scroll17 = new widget.ScrollPane();
        tbObatBayar = new widget.Table();
        panelGlass13 = new widget.panelisi();
        chkPoli = new widget.CekBox();
        TBiaya = new widget.TextBox();
        label11 = new widget.Label();
        SttsPoli = new widget.Label();
        Scroll9 = new widget.ScrollPane();
        tbBilling = new widget.Table();

        PopupTindakanDr.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanTindakanDr.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanTindakanDr.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanTindakanDr.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanTindakanDr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanTindakanDr.setText("Bersihkan Pilihan");
        ppBersihkanTindakanDr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanTindakanDr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanTindakanDr.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanTindakanDr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanTindakanDrActionPerformed(evt);
            }
        });
        PopupTindakanDr.add(ppBersihkanTindakanDr);

        ppSemuaTindakanDr.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaTindakanDr.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaTindakanDr.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaTindakanDr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaTindakanDr.setText("Pilih Semua");
        ppSemuaTindakanDr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaTindakanDr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaTindakanDr.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaTindakanDr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaTindakanDrActionPerformed(evt);
            }
        });
        PopupTindakanDr.add(ppSemuaTindakanDr);

        PopupTindakanDrBayar.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanTindakanDrBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanTindakanDrBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanTindakanDrBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanTindakanDrBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanTindakanDrBayar.setText("Bersihkan Pilihan");
        ppBersihkanTindakanDrBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanTindakanDrBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanTindakanDrBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanTindakanDrBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanTindakanDrBayarActionPerformed(evt);
            }
        });
        PopupTindakanDrBayar.add(ppBersihkanTindakanDrBayar);

        ppSemuaTindakanDrBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaTindakanDrBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaTindakanDrBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaTindakanDrBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaTindakanDrBayar.setText("Pilih Semua");
        ppSemuaTindakanDrBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaTindakanDrBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaTindakanDrBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaTindakanDrBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaTindakanDrBayarActionPerformed(evt);
            }
        });
        PopupTindakanDrBayar.add(ppSemuaTindakanDrBayar);

        PopupTindakanPr.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanTindakanPr.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanTindakanPr.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanTindakanPr.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanTindakanPr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanTindakanPr.setText("Bersihkan Pilihan");
        ppBersihkanTindakanPr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanTindakanPr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanTindakanPr.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanTindakanPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanTindakanPrActionPerformed(evt);
            }
        });
        PopupTindakanPr.add(ppBersihkanTindakanPr);

        ppSemuaTindakanPr.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaTindakanPr.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaTindakanPr.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaTindakanPr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaTindakanPr.setText("Pilih Semua");
        ppSemuaTindakanPr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaTindakanPr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaTindakanPr.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaTindakanPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaTindakanPrActionPerformed(evt);
            }
        });
        PopupTindakanPr.add(ppSemuaTindakanPr);

        PopupTindakanPrBayar.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanTindakanPrBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanTindakanPrBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanTindakanPrBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanTindakanPrBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanTindakanPrBayar.setText("Bersihkan Pilihan");
        ppBersihkanTindakanPrBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanTindakanPrBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanTindakanPrBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanTindakanPrBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanTindakanPrBayarActionPerformed(evt);
            }
        });
        PopupTindakanPrBayar.add(ppBersihkanTindakanPrBayar);

        ppSemuaTindakanPrBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaTindakanPrBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaTindakanPrBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaTindakanPrBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaTindakanPrBayar.setText("Pilih Semua");
        ppSemuaTindakanPrBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaTindakanPrBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaTindakanPrBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaTindakanPrBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaTindakanPrBayarActionPerformed(evt);
            }
        });
        PopupTindakanPrBayar.add(ppSemuaTindakanPrBayar);

        PopupTindakanDrPr.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanTindakanDrPr.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanTindakanDrPr.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanTindakanDrPr.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanTindakanDrPr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanTindakanDrPr.setText("Bersihkan Pilihan");
        ppBersihkanTindakanDrPr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanTindakanDrPr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanTindakanDrPr.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanTindakanDrPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanTindakanDrPrActionPerformed(evt);
            }
        });
        PopupTindakanDrPr.add(ppBersihkanTindakanDrPr);

        ppSemuaTindakanDrPr.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaTindakanDrPr.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaTindakanDrPr.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaTindakanDrPr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaTindakanDrPr.setText("Pilih Semua");
        ppSemuaTindakanDrPr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaTindakanDrPr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaTindakanDrPr.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaTindakanDrPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaTindakanDrPrActionPerformed(evt);
            }
        });
        PopupTindakanDrPr.add(ppSemuaTindakanDrPr);

        PopupTindakanDrPrBayar.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanTindakanDrPrBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanTindakanDrPrBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanTindakanDrPrBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanTindakanDrPrBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanTindakanDrPrBayar.setText("Bersihkan Pilihan");
        ppBersihkanTindakanDrPrBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanTindakanDrPrBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanTindakanDrPrBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanTindakanDrPrBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanTindakanDrPrBayarActionPerformed(evt);
            }
        });
        PopupTindakanDrPrBayar.add(ppBersihkanTindakanDrPrBayar);

        ppSemuaTindakanDrPrBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaTindakanDrPrBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaTindakanDrPrBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaTindakanDrPrBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaTindakanDrPrBayar.setText("Pilih Semua");
        ppSemuaTindakanDrPrBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaTindakanDrPrBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaTindakanDrPrBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaTindakanDrPrBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaTindakanDrPrBayarActionPerformed(evt);
            }
        });
        PopupTindakanDrPrBayar.add(ppSemuaTindakanDrPrBayar);

        PopupRadiologi.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanRadiologi.setText("Bersihkan Pilihan");
        ppBersihkanRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanRadiologi.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanRadiologiActionPerformed(evt);
            }
        });
        PopupRadiologi.add(ppBersihkanRadiologi);

        ppSemuaRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaRadiologi.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaRadiologi.setText("Pilih Semua");
        ppSemuaRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaRadiologi.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaRadiologiActionPerformed(evt);
            }
        });
        PopupRadiologi.add(ppSemuaRadiologi);

        PopupRadiologiBayar.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanRadiologiBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanRadiologiBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanRadiologiBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanRadiologiBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanRadiologiBayar.setText("Bersihkan Pilihan");
        ppBersihkanRadiologiBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanRadiologiBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanRadiologiBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanRadiologiBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanRadiologiBayarActionPerformed(evt);
            }
        });
        PopupRadiologiBayar.add(ppBersihkanRadiologiBayar);

        ppSemuaRadiologiBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaRadiologiBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaRadiologiBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaRadiologiBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaRadiologiBayar.setText("Pilih Semua");
        ppSemuaRadiologiBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaRadiologiBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaRadiologiBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaRadiologiBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaRadiologiBayarActionPerformed(evt);
            }
        });
        PopupRadiologiBayar.add(ppSemuaRadiologiBayar);

        PopupLaborat.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanLaborat.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanLaborat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanLaborat.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanLaborat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanLaborat.setText("Bersihkan Pilihan");
        ppBersihkanLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanLaborat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanLaborat.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanLaboratActionPerformed(evt);
            }
        });
        PopupLaborat.add(ppBersihkanLaborat);

        ppSemuaLaborat.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaLaborat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaLaborat.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaLaborat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaLaborat.setText("Pilih Semua");
        ppSemuaLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaLaborat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaLaborat.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaLaboratActionPerformed(evt);
            }
        });
        PopupLaborat.add(ppSemuaLaborat);

        PopupDetailLaborat.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanDetailLaborat.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanDetailLaborat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanDetailLaborat.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanDetailLaborat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanDetailLaborat.setText("Bersihkan Pilihan");
        ppBersihkanDetailLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanDetailLaborat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanDetailLaborat.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanDetailLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanDetailLaboratActionPerformed(evt);
            }
        });
        PopupDetailLaborat.add(ppBersihkanDetailLaborat);

        ppSemuaDetailLaborat.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaDetailLaborat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaDetailLaborat.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaDetailLaborat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaDetailLaborat.setText("Pilih Semua");
        ppSemuaDetailLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaDetailLaborat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaDetailLaborat.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaDetailLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaDetailLaboratActionPerformed(evt);
            }
        });
        PopupDetailLaborat.add(ppSemuaDetailLaborat);

        PopupLaboratBayar.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanLaboratBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanLaboratBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanLaboratBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanLaboratBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanLaboratBayar.setText("Bersihkan Pilihan");
        ppBersihkanLaboratBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanLaboratBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanLaboratBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanLaboratBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanLaboratBayarActionPerformed(evt);
            }
        });
        PopupLaboratBayar.add(ppBersihkanLaboratBayar);

        ppSemuaLaboratBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaLaboratBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaLaboratBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaLaboratBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaLaboratBayar.setText("Pilih Semua");
        ppSemuaLaboratBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaLaboratBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaLaboratBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaLaboratBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaLaboratBayarActionPerformed(evt);
            }
        });
        PopupLaboratBayar.add(ppSemuaLaboratBayar);

        PopupDetailLaboratBayar.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanDetailLaboratBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanDetailLaboratBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanDetailLaboratBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanDetailLaboratBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanDetailLaboratBayar.setText("Bersihkan Pilihan");
        ppBersihkanDetailLaboratBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanDetailLaboratBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanDetailLaboratBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanDetailLaboratBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanDetailLaboratBayarActionPerformed(evt);
            }
        });
        PopupDetailLaboratBayar.add(ppBersihkanDetailLaboratBayar);

        ppSemuaDetailLaboratBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaDetailLaboratBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaDetailLaboratBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaDetailLaboratBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaDetailLaboratBayar.setText("Pilih Semua");
        ppSemuaDetailLaboratBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaDetailLaboratBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaDetailLaboratBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaDetailLaboratBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaDetailLaboratBayarActionPerformed(evt);
            }
        });
        PopupDetailLaboratBayar.add(ppSemuaDetailLaboratBayar);

        PopupObat.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanObat.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanObat.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanObat.setText("Bersihkan Pilihan");
        ppBersihkanObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanObat.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanObatActionPerformed(evt);
            }
        });
        PopupObat.add(ppBersihkanObat);

        ppSemuaObat.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaObat.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaObat.setText("Pilih Semua");
        ppSemuaObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaObat.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaObatActionPerformed(evt);
            }
        });
        PopupObat.add(ppSemuaObat);

        PopupObatBayar.setBackground(new java.awt.Color(255, 255, 254));

        ppBersihkanObatBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkanObatBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkanObatBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkanObatBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkanObatBayar.setText("Bersihkan Pilihan");
        ppBersihkanObatBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkanObatBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkanObatBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppBersihkanObatBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanObatBayarActionPerformed(evt);
            }
        });
        PopupObatBayar.add(ppBersihkanObatBayar);

        ppSemuaObatBayar.setBackground(new java.awt.Color(255, 255, 254));
        ppSemuaObatBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemuaObatBayar.setForeground(new java.awt.Color(50, 50, 50));
        ppSemuaObatBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemuaObatBayar.setText("Pilih Semua");
        ppSemuaObatBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemuaObatBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemuaObatBayar.setPreferredSize(new java.awt.Dimension(160, 25));
        ppSemuaObatBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaObatBayarActionPerformed(evt);
            }
        });
        PopupObatBayar.add(ppSemuaObatBayar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Billing/Pembayaran Parsial Rawat Jalan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass1.setPreferredSize(new java.awt.Dimension(100, 45));
        panelGlass1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 10));

        jLabel3.setText("No.Rawat :");
        jLabel3.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass1.add(jLabel3);

        TNoRw.setHighlighter(null);
        TNoRw.setPreferredSize(new java.awt.Dimension(150, 23));
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        panelGlass1.add(TNoRw);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass1.add(TNoRM);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setPreferredSize(new java.awt.Dimension(300, 23));
        panelGlass1.add(TPasien);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('R');
        BtnCari.setToolTipText("Alt+R");
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
        panelGlass1.add(BtnCari);

        jLabel4.setText("Tanggal :");
        jLabel4.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass1.add(jLabel4);

        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2020 23:07:49" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DTPTgl.setOpaque(false);
        DTPTgl.setPreferredSize(new java.awt.Dimension(135, 23));
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        panelGlass1.add(DTPTgl);

        internalFrame1.add(panelGlass1, java.awt.BorderLayout.PAGE_START);

        panelGlass8.setPreferredSize(new java.awt.Dimension(155, 225));
        panelGlass8.setLayout(null);

        label9.setText("Key Word :");
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass8.add(label9);
        label9.setBounds(0, 10, 70, 23);

        TCariTindakan.setPreferredSize(new java.awt.Dimension(320, 23));
        TCariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariTindakanKeyPressed(evt);
            }
        });
        panelGlass8.add(TCariTindakan);
        TCariTindakan.setBounds(74, 10, 210, 23);

        BtnCariTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariTindakan.setMnemonic('1');
        BtnCariTindakan.setToolTipText("Alt+1");
        BtnCariTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariTindakanActionPerformed(evt);
            }
        });
        BtnCariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariTindakanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnCariTindakan);
        BtnCariTindakan.setBounds(286, 10, 28, 23);

        BtnAllTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllTindakan.setMnemonic('2');
        BtnAllTindakan.setToolTipText("Alt+2");
        BtnAllTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllTindakanActionPerformed(evt);
            }
        });
        BtnAllTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllTindakanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAllTindakan);
        BtnAllTindakan.setBounds(317, 10, 28, 23);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
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
        BtnHapus.setBounds(670, 180, 100, 30);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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
        BtnKeluar.setBounds(775, 180, 100, 30);

        jLabel9.setText("Tagihan + PPN : Rp.");
        jLabel9.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(jLabel9);
        jLabel9.setBounds(613, 10, 110, 23);

        TagihanPPN.setEditable(false);
        TagihanPPN.setText("0");
        TagihanPPN.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TagihanPPN.setHighlighter(null);
        TagihanPPN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TagihanPPNKeyPressed(evt);
            }
        });
        panelGlass8.add(TagihanPPN);
        TagihanPPN.setBounds(725, 10, 150, 23);

        jLabel6.setText("Bayar : Rp.");
        jLabel6.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(jLabel6);
        jLabel6.setBounds(0, 40, 90, 23);

        TCari.setPreferredSize(new java.awt.Dimension(340, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);
        TCari.setBounds(91, 40, 756, 23);

        scrollPane3.setOpaque(true);

        tbAkunBayar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAkunBayar.setToolTipText("");
        tbAkunBayar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbAkunBayarPropertyChange(evt);
            }
        });
        tbAkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAkunBayarKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(tbAkunBayar);

        panelGlass8.add(scrollPane3);
        scrollPane3.setBounds(91, 69, 784, 105);

        BtnCariBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariBayar.setMnemonic('3');
        BtnCariBayar.setToolTipText("Alt+3");
        BtnCariBayar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariBayarActionPerformed(evt);
            }
        });
        BtnCariBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariBayarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnCariBayar);
        BtnCariBayar.setBounds(850, 40, 25, 23);

        jLabel7.setText("Kembali : Rp.");
        jLabel7.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(jLabel7);
        jLabel7.setBounds(0, 180, 90, 23);

        TKembali.setEditable(false);
        TKembali.setText("0");
        TKembali.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TKembali.setHighlighter(null);
        panelGlass8.add(TKembali);
        TKembali.setBounds(91, 180, 230, 23);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnSimpan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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
        BtnSimpan.setBounds(460, 180, 100, 30);

        BtnNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnNota.setMnemonic('N');
        BtnNota.setText(" Nota");
        BtnNota.setToolTipText("Alt+N");
        BtnNota.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnNota.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnNota.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNotaActionPerformed(evt);
            }
        });
        BtnNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnNotaKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnNota);
        BtnNota.setBounds(565, 180, 100, 30);

        TtlSemua.setEditable(false);
        TtlSemua.setText("0");
        TtlSemua.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TtlSemua.setHighlighter(null);
        TtlSemua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TtlSemuaKeyPressed(evt);
            }
        });
        panelGlass8.add(TtlSemua);
        TtlSemua.setBounds(460, 10, 150, 23);

        jLabel11.setText("Total Tagihan : Rp.");
        jLabel11.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(jLabel11);
        jLabel11.setBounds(350, 10, 110, 23);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setForeground(new java.awt.Color(50, 50, 50));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass7.setBorder(null);
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 40));
        panelGlass7.setLayout(null);

        jLabel5.setText("Dokter :");
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 70, 23);

        KdDok.setEditable(false);
        KdDok.setHighlighter(null);
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        panelGlass7.add(KdDok);
        KdDok.setBounds(74, 10, 130, 23);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeekDokter);
        BtnSeekDokter.setBounds(849, 10, 28, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        panelGlass7.add(TDokter);
        TDokter.setBounds(206, 10, 641, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        Scroll3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Tindakan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll3.setComponentPopupMenu(PopupTindakanDr);
        Scroll3.setOpaque(true);
        Scroll3.setPreferredSize(new java.awt.Dimension(540, 500));

        tbTindakanDr.setComponentPopupMenu(PopupTindakanDr);
        Scroll3.setViewportView(tbTindakanDr);

        internalFrame2.add(Scroll3, java.awt.BorderLayout.WEST);

        Scroll4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll4.setComponentPopupMenu(PopupTindakanDrBayar);
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(400, 404));

        tbTindakanDrBayar.setComponentPopupMenu(PopupTindakanDrBayar);
        tbTindakanDrBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTindakanDrBayarMouseClicked(evt);
            }
        });
        Scroll4.setViewportView(tbTindakanDrBayar);

        internalFrame2.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakan Dokter", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setBorder(null);
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 40));
        panelGlass10.setLayout(null);

        jLabel13.setText("Petugas :");
        panelGlass10.add(jLabel13);
        jLabel13.setBounds(0, 10, 70, 23);

        kdptg.setEditable(false);
        kdptg.setHighlighter(null);
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelGlass10.add(kdptg);
        kdptg.setBounds(74, 10, 130, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(849, 10, 28, 23);

        TPerawat.setEditable(false);
        TPerawat.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat.setHighlighter(null);
        panelGlass10.add(TPerawat);
        TPerawat.setBounds(206, 10, 641, 23);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        Scroll5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Tindakan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll5.setComponentPopupMenu(PopupTindakanPr);
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(540, 500));

        tbTindakanPr.setComponentPopupMenu(PopupTindakanPr);
        tbTindakanPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTindakanPrMouseClicked(evt);
            }
        });
        tbTindakanPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakanPrKeyPressed(evt);
            }
        });
        Scroll5.setViewportView(tbTindakanPr);

        internalFrame3.add(Scroll5, java.awt.BorderLayout.WEST);

        Scroll6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll6.setComponentPopupMenu(PopupTindakanPrBayar);
        Scroll6.setOpaque(true);
        Scroll6.setPreferredSize(new java.awt.Dimension(400, 404));

        tbTindakanPrBayar.setComponentPopupMenu(PopupTindakanPrBayar);
        tbTindakanPrBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTindakanPrBayarMouseClicked(evt);
            }
        });
        Scroll6.setViewportView(tbTindakanPrBayar);

        internalFrame3.add(Scroll6, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakan Petugas", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass11.setBorder(null);
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 40));
        panelGlass11.setLayout(null);

        jLabel14.setText("Petugas :");
        panelGlass11.add(jLabel14);
        jLabel14.setBounds(460, 10, 50, 23);

        kdptg2.setEditable(false);
        kdptg2.setHighlighter(null);
        kdptg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptg2KeyPressed(evt);
            }
        });
        panelGlass11.add(kdptg2);
        kdptg2.setBounds(514, 10, 110, 23);

        BtnSeekPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas2.setMnemonic('5');
        BtnSeekPetugas2.setToolTipText("ALt+5");
        BtnSeekPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekPetugas2);
        BtnSeekPetugas2.setBounds(849, 10, 28, 23);

        TPerawat2.setEditable(false);
        TPerawat2.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat2.setHighlighter(null);
        panelGlass11.add(TPerawat2);
        TPerawat2.setBounds(626, 10, 221, 23);

        jLabel12.setText("Dokter :");
        panelGlass11.add(jLabel12);
        jLabel12.setBounds(0, 10, 50, 23);

        KdDok2.setEditable(false);
        KdDok2.setHighlighter(null);
        KdDok2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDok2KeyPressed(evt);
            }
        });
        panelGlass11.add(KdDok2);
        KdDok2.setBounds(54, 10, 110, 23);

        TDokter2.setEditable(false);
        TDokter2.setHighlighter(null);
        panelGlass11.add(TDokter2);
        TDokter2.setBounds(166, 10, 221, 23);

        BtnSeekDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter2.setMnemonic('4');
        BtnSeekDokter2.setToolTipText("ALt+4");
        BtnSeekDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter2ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeekDokter2);
        BtnSeekDokter2.setBounds(389, 10, 28, 23);

        internalFrame4.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        Scroll7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Tindakan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll7.setComponentPopupMenu(PopupTindakanDrPr);
        Scroll7.setOpaque(true);
        Scroll7.setPreferredSize(new java.awt.Dimension(540, 500));

        tbTindakanDrPr.setComponentPopupMenu(PopupTindakanDrPr);
        Scroll7.setViewportView(tbTindakanDrPr);

        internalFrame4.add(Scroll7, java.awt.BorderLayout.WEST);

        Scroll8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll8.setComponentPopupMenu(PopupTindakanDrPrBayar);
        Scroll8.setOpaque(true);
        Scroll8.setPreferredSize(new java.awt.Dimension(400, 404));

        tbTindakanDrPrBayar.setComponentPopupMenu(PopupTindakanDrPrBayar);
        tbTindakanDrPrBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTindakanDrPrBayarMouseClicked(evt);
            }
        });
        Scroll8.setViewportView(tbTindakanDrPrBayar);

        internalFrame4.add(Scroll8, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakkan Dokter & Petugas", internalFrame4);

        internalFrame5.setBorder(null);
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Pemeriksaan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll10.setComponentPopupMenu(PopupRadiologi);
        Scroll10.setOpaque(true);
        Scroll10.setPreferredSize(new java.awt.Dimension(540, 500));

        tbRadiologi.setComponentPopupMenu(PopupRadiologi);
        Scroll10.setViewportView(tbRadiologi);

        internalFrame5.add(Scroll10, java.awt.BorderLayout.WEST);

        Scroll11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll11.setComponentPopupMenu(PopupRadiologiBayar);
        Scroll11.setOpaque(true);
        Scroll11.setPreferredSize(new java.awt.Dimension(400, 404));

        tbRadiologiBayar.setComponentPopupMenu(PopupRadiologiBayar);
        tbRadiologiBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRadiologiBayarMouseClicked(evt);
            }
        });
        Scroll11.setViewportView(tbRadiologiBayar);

        internalFrame5.add(Scroll11, java.awt.BorderLayout.CENTER);

        panelGlass9.setBorder(null);
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 40));
        panelGlass9.setLayout(null);

        jLabel8.setText("Perujuk :");
        panelGlass9.add(jLabel8);
        jLabel8.setBounds(0, 10, 70, 23);

        KdDokPerujukRad.setEditable(false);
        KdDokPerujukRad.setHighlighter(null);
        KdDokPerujukRad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokPerujukRadKeyPressed(evt);
            }
        });
        panelGlass9.add(KdDokPerujukRad);
        KdDokPerujukRad.setBounds(74, 10, 130, 23);

        BtnSeekDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter1.setMnemonic('4');
        BtnSeekDokter1.setToolTipText("ALt+4");
        BtnSeekDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter1ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnSeekDokter1);
        BtnSeekDokter1.setBounds(849, 10, 28, 23);

        TDokterPerujukRad.setEditable(false);
        TDokterPerujukRad.setHighlighter(null);
        panelGlass9.add(TDokterPerujukRad);
        TDokterPerujukRad.setBounds(206, 10, 641, 23);

        internalFrame5.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Radiologi", internalFrame5);

        internalFrame6.setBorder(null);
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass12.setBorder(null);
        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 40));
        panelGlass12.setLayout(null);

        jLabel10.setText("Perujuk :");
        panelGlass12.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        KdDokPerujukLab.setEditable(false);
        KdDokPerujukLab.setHighlighter(null);
        KdDokPerujukLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokPerujukLabKeyPressed(evt);
            }
        });
        panelGlass12.add(KdDokPerujukLab);
        KdDokPerujukLab.setBounds(74, 10, 130, 23);

        BtnSeekDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter3.setMnemonic('4');
        BtnSeekDokter3.setToolTipText("ALt+4");
        BtnSeekDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokter3ActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnSeekDokter3);
        BtnSeekDokter3.setBounds(849, 10, 28, 23);

        TDokterPerujukLab.setEditable(false);
        TDokterPerujukLab.setHighlighter(null);
        panelGlass12.add(TDokterPerujukLab);
        TDokterPerujukLab.setBounds(206, 10, 641, 23);

        internalFrame6.add(panelGlass12, java.awt.BorderLayout.PAGE_START);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Pemeriksaan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawatLaborat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatLaborat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatLaborat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatLaborat.setPreferredSize(new java.awt.Dimension(540, 500));
        TabRawatLaborat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatLaboratMouseClicked(evt);
            }
        });

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll12.setComponentPopupMenu(PopupLaborat);
        Scroll12.setOpaque(true);
        Scroll12.setPreferredSize(new java.awt.Dimension(540, 500));

        tbLaborat.setComponentPopupMenu(PopupLaborat);
        Scroll12.setViewportView(tbLaborat);

        TabRawatLaborat.addTab("Pemeriksaan", Scroll12);

        Scroll14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll14.setComponentPopupMenu(PopupDetailLaborat);
        Scroll14.setOpaque(true);
        Scroll14.setPreferredSize(new java.awt.Dimension(540, 500));

        tbDetailLaborat.setComponentPopupMenu(PopupDetailLaborat);
        Scroll14.setViewportView(tbDetailLaborat);

        TabRawatLaborat.addTab("Sub Pemeriksaan", Scroll14);

        internalFrame7.add(TabRawatLaborat, java.awt.BorderLayout.CENTER);

        internalFrame6.add(internalFrame7, java.awt.BorderLayout.WEST);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawatLaboratBayar.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatLaboratBayar.setForeground(new java.awt.Color(50, 50, 50));
        TabRawatLaboratBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatLaboratBayar.setPreferredSize(new java.awt.Dimension(540, 500));
        TabRawatLaboratBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatLaboratBayarMouseClicked(evt);
            }
        });

        Scroll15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll15.setComponentPopupMenu(PopupLaboratBayar);
        Scroll15.setOpaque(true);
        Scroll15.setPreferredSize(new java.awt.Dimension(540, 500));

        tbLaboratBayar.setComponentPopupMenu(PopupLaboratBayar);
        tbLaboratBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLaboratBayarMouseClicked(evt);
            }
        });
        Scroll15.setViewportView(tbLaboratBayar);

        TabRawatLaboratBayar.addTab("Pemeriksaan", Scroll15);

        Scroll16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll16.setComponentPopupMenu(PopupDetailLaboratBayar);
        Scroll16.setOpaque(true);
        Scroll16.setPreferredSize(new java.awt.Dimension(540, 500));

        tbDetailLaboratBayar.setComponentPopupMenu(PopupDetailLaboratBayar);
        tbDetailLaboratBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailLaboratBayarMouseClicked(evt);
            }
        });
        Scroll16.setViewportView(tbDetailLaboratBayar);

        TabRawatLaboratBayar.addTab("Sub Pemeriksaan", Scroll16);

        internalFrame8.add(TabRawatLaboratBayar, java.awt.BorderLayout.CENTER);

        internalFrame6.add(internalFrame8, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Laboratorium", internalFrame6);

        internalFrame9.setBorder(null);
        internalFrame9.setForeground(new java.awt.Color(50, 50, 50));
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Obat ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll13.setComponentPopupMenu(PopupObat);
        Scroll13.setOpaque(true);
        Scroll13.setPreferredSize(new java.awt.Dimension(540, 500));
        Scroll13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Scroll13MouseClicked(evt);
            }
        });

        tbObat.setComponentPopupMenu(PopupObat);
        Scroll13.setViewportView(tbObat);

        internalFrame9.add(Scroll13, java.awt.BorderLayout.WEST);

        Scroll17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll17.setComponentPopupMenu(PopupObatBayar);
        Scroll17.setOpaque(true);
        Scroll17.setPreferredSize(new java.awt.Dimension(400, 404));

        tbObatBayar.setComponentPopupMenu(PopupObatBayar);
        tbObatBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatBayarMouseClicked(evt);
            }
        });
        Scroll17.setViewportView(tbObatBayar);

        internalFrame9.add(Scroll17, java.awt.BorderLayout.CENTER);

        panelGlass13.setBorder(null);
        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 40));
        panelGlass13.setLayout(null);

        chkPoli.setText("Poliklinik :");
        chkPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPoli.setOpaque(false);
        chkPoli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkPoliMouseClicked(evt);
            }
        });
        panelGlass13.add(chkPoli);
        chkPoli.setBounds(25, 10, 370, 23);

        TBiaya.setEditable(false);
        TBiaya.setHighlighter(null);
        TBiaya.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass13.add(TBiaya);
        TBiaya.setBounds(775, 10, 100, 23);

        label11.setText("Biaya Registrasi Poli/Unit :");
        label11.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass13.add(label11);
        label11.setBounds(612, 10, 160, 23);

        SttsPoli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SttsPoli.setText("Stts Bayar Poli :");
        SttsPoli.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass13.add(SttsPoli);
        SttsPoli.setBounds(452, 10, 160, 23);

        internalFrame9.add(panelGlass13, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Obat & Registrasi", internalFrame9);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll9.setOpaque(true);
        Scroll9.setPreferredSize(new java.awt.Dimension(440, 404));

        tbBilling.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBillingMouseClicked(evt);
            }
        });
        tbBilling.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBillingKeyPressed(evt);
            }
        });
        Scroll9.setViewportView(tbBilling);

        TabRawat.addTab("Tagihan", Scroll9);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isRawat();
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        isRawat();
        tampilbilling();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
       
    }//GEN-LAST:event_BtnCariKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        
    }//GEN-LAST:event_DTPTglKeyPressed

    private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
       
    }//GEN-LAST:event_KdDokKeyPressed

    private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokterActionPerformed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        
    }//GEN-LAST:event_kdptgKeyPressed

    private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugasActionPerformed

    private void kdptg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptg2KeyPressed
        
    }//GEN-LAST:event_kdptg2KeyPressed

    private void BtnSeekPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas2ActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugas2ActionPerformed

    private void KdDok2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDok2KeyPressed
        
    }//GEN-LAST:event_KdDok2KeyPressed

    private void BtnSeekDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter2ActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter2ActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
            tampilDrBayar();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
            tampilPrBayar();
        }else if(TabRawat.getSelectedIndex()==2){
            tampilDrPr();
            tampilDrPrBayar();
        }else if(TabRawat.getSelectedIndex()==3){
            tampilRadiologi();
            tampilRadiologiBayar();
        }else if(TabRawat.getSelectedIndex()==4){
            if(TabRawatLaborat.getSelectedIndex()==0){
                tampilLaborat();
            }else if(TabRawatLaborat.getSelectedIndex()==1){
                tampilDetailLaborat();
            }         
            if(TabRawatLaboratBayar.getSelectedIndex()==0){
                tampilLaboratBayar();
            }else if(TabRawatLaboratBayar.getSelectedIndex()==1){
                tampilDetailLaboratBayar();
            } 
        }else if(TabRawat.getSelectedIndex()==5){
            tampilObat();
            tampilObatBayar();
            chkPoli.setSelected(false);
            SttsPoli.setText("Status Bayar Poli : "+Sequel.cariIsi("select if(count(no_rawat)>0,'Sudah','Belum') from permintaan_registrasi where no_rawat=?",TNoRw.getText()));
        }else if(TabRawat.getSelectedIndex()==6){
            tampilbilling();
        }     
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariTindakanActionPerformed(null);
        }
    }//GEN-LAST:event_TCariTindakanKeyPressed

    private void BtnCariTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariTindakanActionPerformed
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnCariTindakanActionPerformed

    private void BtnCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariTindakanKeyPressed
        
    }//GEN-LAST:event_BtnCariTindakanKeyPressed

    private void BtnAllTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllTindakanActionPerformed
        TCariTindakan.setText("");
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnAllTindakanActionPerformed

    private void BtnAllTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllTindakanKeyPressed
        
    }//GEN-LAST:event_BtnAllTindakanKeyPressed

    private void tbTindakanDrBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTindakanDrBayarMouseClicked
        if(TabModeTindakanDrBayar.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(tbTindakanDrBayar.getSelectedColumn()==0){
                    tampilhapus();
                }
            }
        }
    }//GEN-LAST:event_tbTindakanDrBayarMouseClicked

    private void tbTindakanPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTindakanPrMouseClicked
       
    }//GEN-LAST:event_tbTindakanPrMouseClicked

    private void tbTindakanPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakanPrKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTindakanPrKeyPressed

    private void tbTindakanPrBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTindakanPrBayarMouseClicked
        if(TabModeTindakanPrBayar.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(tbTindakanPrBayar.getSelectedColumn()==0){
                    tampilhapus();
                }
            }
        }
    }//GEN-LAST:event_tbTindakanPrBayarMouseClicked

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed

    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose(); 
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed

    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(statushapus==false){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan lanjutkan proses simpan terlebih dahulu ...!!!");
        }else{
            if(ttl<=0){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan masukkan tagihan yang mau dihapus ...!!!");
            }else{
                if(kekurangan<0){
                    JOptionPane.showMessageDialog(null,"Maaf, pembayaran/dana pengembalian pasien masih kurang ...!!!");
                }else if(kekurangan>0){
                    JOptionPane.showMessageDialog(null,"Maaf, kembali harus bernilai 0 untuk cara bayar lebih dari 1...!!!");
                }else if(kekurangan==0){
                    isSimpanHapus();
                } 
            }
        }            
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void TagihanPPNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TagihanPPNKeyPressed
        Valid.pindah(evt,BtnKeluar,BtnNota);
    }//GEN-LAST:event_TagihanPPNKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        
    }//GEN-LAST:event_TCariKeyPressed

    private void tbAkunBayarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbAkunBayarPropertyChange
        if(this.isVisible()==true){
              isKembali();
        }
    }//GEN-LAST:event_tbAkunBayarPropertyChange

    private void tbAkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAkunBayarKeyPressed
        if(tabModeAkunBayar.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                if(tbAkunBayar.getRowCount()!=0){
                    if((tbAkunBayar.getSelectedColumn()==2)||(tbAkunBayar.getSelectedColumn()==3)||(tbAkunBayar.getSelectedColumn()==4)){
                        if(!tabModeAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),2).toString().equals("")){
                            tbAkunBayar.setValueAt(
                                    Valid.roundUp((Valid.SetAngka(tbAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),3).toString())/100)*
                                    Valid.SetAngka(tbAkunBayar.getValueAt(tbAkunBayar.getSelectedRow(),2).toString()),100),tbAkunBayar.getSelectedRow(),4);
                        }else{
                            tbAkunBayar.setValueAt("",tbAkunBayar.getSelectedRow(),4);                        
                        }                            
                    }
                }
                isKembali();
            }
        }
    }//GEN-LAST:event_tbAkunBayarKeyPressed

    private void BtnCariBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariBayarActionPerformed
        tampilAkunBayar();
    }//GEN-LAST:event_BtnCariBayarActionPerformed

    private void BtnCariBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariBayarKeyPressed

    }//GEN-LAST:event_BtnCariBayarKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(statushapus==true){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan lanjutkan proses hapus terlebih dahulu ...!!!");
        }else{
            if(TabRawat.getSelectedIndex()==6){
                if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
                    Valid.textKosong(TNoRw,"Pasien");
                }else{
                    if(ttl<=0){
                        JOptionPane.showMessageDialog(null,"Maaf, silahkan masukkan tagihan ...!!!");
                    }else{
                        if(kekurangan<0){
                            JOptionPane.showMessageDialog(null,"Maaf, pembayaran pasien masih kurang ...!!!");
                        }else if(kekurangan>0){
                            if(countbayar>1){
                                JOptionPane.showMessageDialog(null,"Maaf, kembali harus bernilai 0 untuk cara bayar lebih dari 1...!!!");
                            }else{
                                isSimpan();
                            }                        
                        }else if(kekurangan==0){
                            isSimpan();
                        } 
                    }                        
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan buka tagihan ...!!");
                TCari.requestFocus();
            }
        }            
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNotaActionPerformed
        if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(tabModeBilling.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabModeBilling.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try{
                koneksi.setAutoCommit(false);
                Sequel.queryu2("delete from temporary_bayar_ralan where temp9='"+akses.getkode()+"'"); 
                z=tabModeBilling.getRowCount();
                for(i=0;i<z;i++){
                    biaya="";
                    try {
                        biaya=Valid.SetAngka(Double.parseDouble(tabModeBilling.getValueAt(i,3).toString())); 
                    } catch (Exception e) {
                        biaya="";
                    }     
                    
                    tambahan="0";
                    
                    totals="";
                    try {
                        totals=Valid.SetAngka(Double.parseDouble(tabModeBilling.getValueAt(i,5).toString())); 
                    } catch (Exception e) {
                        totals="";
                    }
                    
                    jmls="";
                    try {
                        jmls=Valid.SetAngka(Double.parseDouble(tabModeBilling.getValueAt(i,4).toString())); 
                    } catch (Exception e) {
                        jmls="";
                    }
                    
                    Sequel.menyimpan("temporary_bayar_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                        "0",tabModeBilling.getValueAt(i,0).toString().replaceAll("'",""),
                        tabModeBilling.getValueAt(i,1).toString().replaceAll("'",""),
                        tabModeBilling.getValueAt(i,2).toString().replaceAll("'",""),
                        biaya,jmls,tambahan,totals,
                        tabModeBilling.getValueAt(i,6).toString().replaceAll("'",""),
                        akses.getkode(),"","","","","","","","",""
                    });                                                    
                }

                Sequel.menyimpan("temporary_bayar_ralan","'0','TOTAL TAGIHAN',':','','','','','"+TtlSemua.getText()+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                Sequel.menyimpan("temporary_bayar_ralan","'0','PPN',':','','','','','"+Valid.SetAngka(besarppn)+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                Sequel.menyimpan("temporary_bayar_ralan","'0','TOTAL BAYAR',':','','','','','"+TagihanPPN.getText()+"','Tagihan','"+akses.getkode()+"','','','','','','','',''","Tagihan"); 
                
                i = 0;
                try{
                      biaya = (String)JOptionPane.showInputDialog(null,"Silahkan pilih nota yang mau dicetak!","Nota",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Nota", "Kwitansi", "Nota & Kwitansi"},"Nota");
                      switch (biaya) {
                            case "Nota":
                                  i=1;
                                  break;
                            case "Kwitansi":
                                  i=2;
                                  break;
                            case "Nota & Kwitansi":
                                  i=3;
                                  break;
                      }
                }catch(Exception e){
                      i=0;
                }            

                if(i>0){                       
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText());
                    if(i==1){
                        Valid.panggilUrl("billing/LaporanBilling9.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                    }else if(i==2){
                        Valid.panggilUrl("billing/LaporanBilling5.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(reg_periksa.no_rawat) from reg_periksa "+
                                "where reg_periksa.kd_pj='"+kd_pj+"' and reg_periksa.tgl_registrasi like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RJ/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4));
                    }else if(i==3){
                        Valid.panggilUrl("billing/LaporanBilling9.php?petugas="+akses.getkode().replaceAll(" ","_")+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                        Valid.panggilUrl("billing/LaporanBilling5.php?petugas="+akses.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(reg_periksa.no_rawat) from reg_periksa "+
                                "where reg_periksa.kd_pj='"+kd_pj+"' and reg_periksa.tgl_registrasi like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RJ/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4));
                    }
                    this.setCursor(Cursor.getDefaultCursor());
                }
                
                koneksi.setAutoCommit(true);
                this.setCursor(Cursor.getDefaultCursor());
            }catch(Exception ex){
                System.out.println(ex);
            }      
        }        
    }//GEN-LAST:event_BtnNotaActionPerformed

    private void BtnNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnNotaKeyPressed
        
    }//GEN-LAST:event_BtnNotaKeyPressed

    private void tbBillingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBillingMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbBillingMouseClicked

    private void tbBillingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBillingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbBillingKeyPressed

    private void tbRadiologiBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadiologiBayarMouseClicked
        if(TabModeRadiologiBayar.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(tbRadiologiBayar.getSelectedColumn()==0){
                    tampilhapus();
                }
            }
        }
    }//GEN-LAST:event_tbRadiologiBayarMouseClicked

    private void KdDokPerujukRadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokPerujukRadKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokPerujukRadKeyPressed

    private void BtnSeekDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter1ActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter1ActionPerformed

    private void KdDokPerujukLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokPerujukLabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokPerujukLabKeyPressed

    private void BtnSeekDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter3ActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter3ActionPerformed

    private void TabRawatLaboratMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatLaboratMouseClicked
        if(TabRawatLaborat.getSelectedIndex()==0){
            tampilLaborat();
        }else if(TabRawatLaborat.getSelectedIndex()==1){
            tampilDetailLaborat();
        }  
    }//GEN-LAST:event_TabRawatLaboratMouseClicked

    private void TabRawatLaboratBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatLaboratBayarMouseClicked
        if(TabRawatLaboratBayar.getSelectedIndex()==0){
            tampilLaboratBayar();
        }else if(TabRawatLaboratBayar.getSelectedIndex()==1){
            tampilDetailLaboratBayar();
        } 
        tampilhapus();
    }//GEN-LAST:event_TabRawatLaboratBayarMouseClicked

    private void Scroll13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Scroll13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Scroll13MouseClicked

    private void tbObatBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatBayarMouseClicked
        if(TabModeObatBayar.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(tbObatBayar.getSelectedColumn()==0){
                    tampilhapus();
                }
            }
        }
    }//GEN-LAST:event_tbObatBayarMouseClicked

    private void TtlSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TtlSemuaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TtlSemuaKeyPressed

    private void tbTindakanDrPrBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTindakanDrPrBayarMouseClicked
        if(TabModeTindakanDrPrBayar.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(tbTindakanDrPrBayar.getSelectedColumn()==0){
                    tampilhapus();
                }
            }
        }
    }//GEN-LAST:event_tbTindakanDrPrBayarMouseClicked

    private void ppBersihkanTindakanDrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanTindakanDrActionPerformed
        for(i=0;i<tbTindakanDr.getRowCount();i++){
            tbTindakanDr.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanTindakanDrActionPerformed

    private void ppSemuaTindakanDrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaTindakanDrActionPerformed
        for(i=0;i<tbTindakanDr.getRowCount();i++){
            tbTindakanDr.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaTindakanDrActionPerformed

    private void ppBersihkanTindakanDrBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanTindakanDrBayarActionPerformed
        for(i=0;i<tbTindakanDrBayar.getRowCount();i++){
            tbTindakanDrBayar.setValueAt(false,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppBersihkanTindakanDrBayarActionPerformed

    private void ppSemuaTindakanDrBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaTindakanDrBayarActionPerformed
        for(i=0;i<tbTindakanDrBayar.getRowCount();i++){
            tbTindakanDrBayar.setValueAt(true,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppSemuaTindakanDrBayarActionPerformed

    private void ppBersihkanTindakanPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanTindakanPrActionPerformed
        for(i=0;i<tbTindakanPr.getRowCount();i++){
            tbTindakanPr.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanTindakanPrActionPerformed

    private void ppSemuaTindakanPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaTindakanPrActionPerformed
        for(i=0;i<tbTindakanPr.getRowCount();i++){
            tbTindakanPr.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaTindakanPrActionPerformed

    private void ppBersihkanTindakanPrBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanTindakanPrBayarActionPerformed
        for(i=0;i<tbTindakanPrBayar.getRowCount();i++){
            tbTindakanPrBayar.setValueAt(false,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppBersihkanTindakanPrBayarActionPerformed

    private void ppSemuaTindakanPrBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaTindakanPrBayarActionPerformed
        for(i=0;i<tbTindakanPrBayar.getRowCount();i++){
            tbTindakanPrBayar.setValueAt(true,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppSemuaTindakanPrBayarActionPerformed

    private void ppBersihkanTindakanDrPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanTindakanDrPrActionPerformed
        for(i=0;i<tbTindakanDrPr.getRowCount();i++){
            tbTindakanDrPr.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanTindakanDrPrActionPerformed

    private void ppSemuaTindakanDrPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaTindakanDrPrActionPerformed
        for(i=0;i<tbTindakanDrPr.getRowCount();i++){
            tbTindakanDrPr.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaTindakanDrPrActionPerformed

    private void ppBersihkanTindakanDrPrBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanTindakanDrPrBayarActionPerformed
        for(i=0;i<tbTindakanDrPrBayar.getRowCount();i++){
            tbTindakanDrPrBayar.setValueAt(false,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppBersihkanTindakanDrPrBayarActionPerformed

    private void ppSemuaTindakanDrPrBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaTindakanDrPrBayarActionPerformed
        for(i=0;i<tbTindakanDrPrBayar.getRowCount();i++){
            tbTindakanDrPrBayar.setValueAt(true,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppSemuaTindakanDrPrBayarActionPerformed

    private void ppBersihkanRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanRadiologiActionPerformed
        for(i=0;i<tbRadiologi.getRowCount();i++){
            tbRadiologi.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanRadiologiActionPerformed

    private void ppSemuaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaRadiologiActionPerformed
        for(i=0;i<tbRadiologi.getRowCount();i++){
            tbRadiologi.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaRadiologiActionPerformed

    private void ppBersihkanRadiologiBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanRadiologiBayarActionPerformed
        for(i=0;i<tbRadiologiBayar.getRowCount();i++){
            tbRadiologiBayar.setValueAt(false,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppBersihkanRadiologiBayarActionPerformed

    private void ppSemuaRadiologiBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaRadiologiBayarActionPerformed
        for(i=0;i<tbRadiologiBayar.getRowCount();i++){
            tbRadiologiBayar.setValueAt(true,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppSemuaRadiologiBayarActionPerformed

    private void ppBersihkanLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanLaboratActionPerformed
        for(i=0;i<tbLaborat.getRowCount();i++){
            tbLaborat.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanLaboratActionPerformed

    private void ppSemuaLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaLaboratActionPerformed
        for(i=0;i<tbLaborat.getRowCount();i++){
            tbLaborat.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaLaboratActionPerformed

    private void ppBersihkanDetailLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanDetailLaboratActionPerformed
        for(i=0;i<tbDetailLaborat.getRowCount();i++){
            tbDetailLaborat.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanDetailLaboratActionPerformed

    private void ppSemuaDetailLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaDetailLaboratActionPerformed
        for(i=0;i<tbDetailLaborat.getRowCount();i++){
            tbDetailLaborat.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaDetailLaboratActionPerformed

    private void tbLaboratBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLaboratBayarMouseClicked
        if(TabModeLaboratBayar.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(tbLaboratBayar.getSelectedColumn()==0){
                    tampilhapus();
                }
            }
        }
    }//GEN-LAST:event_tbLaboratBayarMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilAkunBayar();
    }//GEN-LAST:event_formWindowOpened

    private void tbDetailLaboratBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailLaboratBayarMouseClicked
        if(TabModeDetailLaboratBayar.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(tbDetailLaboratBayar.getSelectedColumn()==0){
                    tampilhapus();
                }
            }
        }
    }//GEN-LAST:event_tbDetailLaboratBayarMouseClicked

    private void chkPoliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkPoliMouseClicked
        jmlreg=Sequel.cariInteger("select count(no_rawat) from permintaan_registrasi where no_rawat=?",TNoRw.getText());
        if(jmlreg==1){
            tampilhapus();           
        }
    }//GEN-LAST:event_chkPoliMouseClicked

    private void ppBersihkanLaboratBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanLaboratBayarActionPerformed
        for(i=0;i<tbLaboratBayar.getRowCount();i++){
            tbLaboratBayar.setValueAt(false,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppBersihkanLaboratBayarActionPerformed

    private void ppSemuaLaboratBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaLaboratBayarActionPerformed
        for(i=0;i<tbLaboratBayar.getRowCount();i++){
            tbLaboratBayar.setValueAt(true,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppSemuaLaboratBayarActionPerformed

    private void ppBersihkanDetailLaboratBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanDetailLaboratBayarActionPerformed
        for(i=0;i<tbDetailLaboratBayar.getRowCount();i++){
            tbDetailLaboratBayar.setValueAt(false,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppBersihkanDetailLaboratBayarActionPerformed

    private void ppSemuaDetailLaboratBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaDetailLaboratBayarActionPerformed
        for(i=0;i<tbDetailLaboratBayar.getRowCount();i++){
            tbDetailLaboratBayar.setValueAt(true,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppSemuaDetailLaboratBayarActionPerformed

    private void ppBersihkanObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanObatActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){
            tbObat.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanObatActionPerformed

    private void ppSemuaObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaObatActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){
            tbObat.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaObatActionPerformed

    private void ppBersihkanObatBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanObatBayarActionPerformed
        for(i=0;i<tbObatBayar.getRowCount();i++){
            tbObatBayar.setValueAt(false,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppBersihkanObatBayarActionPerformed

    private void ppSemuaObatBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaObatBayarActionPerformed
        for(i=0;i<tbObatBayar.getRowCount();i++){
            tbObatBayar.setValueAt(true,i,0);
        }
        tampilhapus();
    }//GEN-LAST:event_ppSemuaObatBayarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgBilingParsialRalan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgBilingParsialRalan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgBilingParsialRalan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgBilingParsialRalan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgBilingParsialRalan dialog = new DlgBilingParsialRalan(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAllTindakan;
    private widget.Button BtnCari;
    private widget.Button BtnCariBayar;
    private widget.Button BtnCariTindakan;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnNota;
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSeekDokter1;
    private widget.Button BtnSeekDokter2;
    private widget.Button BtnSeekDokter3;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSeekPetugas2;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPTgl;
    private widget.TextBox KdDok;
    private widget.TextBox KdDok2;
    private widget.TextBox KdDokPerujukLab;
    private widget.TextBox KdDokPerujukRad;
    private javax.swing.JPopupMenu PopupDetailLaborat;
    private javax.swing.JPopupMenu PopupDetailLaboratBayar;
    private javax.swing.JPopupMenu PopupLaborat;
    private javax.swing.JPopupMenu PopupLaboratBayar;
    private javax.swing.JPopupMenu PopupObat;
    private javax.swing.JPopupMenu PopupObatBayar;
    private javax.swing.JPopupMenu PopupRadiologi;
    private javax.swing.JPopupMenu PopupRadiologiBayar;
    private javax.swing.JPopupMenu PopupTindakanDr;
    private javax.swing.JPopupMenu PopupTindakanDrBayar;
    private javax.swing.JPopupMenu PopupTindakanDrPr;
    private javax.swing.JPopupMenu PopupTindakanDrPrBayar;
    private javax.swing.JPopupMenu PopupTindakanPr;
    private javax.swing.JPopupMenu PopupTindakanPrBayar;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll13;
    private widget.ScrollPane Scroll14;
    private widget.ScrollPane Scroll15;
    private widget.ScrollPane Scroll16;
    private widget.ScrollPane Scroll17;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.Label SttsPoli;
    private widget.TextBox TBiaya;
    private widget.TextBox TCari;
    private widget.TextBox TCariTindakan;
    private widget.TextBox TDokter;
    private widget.TextBox TDokter2;
    private widget.TextBox TDokterPerujukLab;
    private widget.TextBox TDokterPerujukRad;
    public widget.TextBox TKembali;
    private widget.TextBox TNoRM;
    public widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPerawat;
    private widget.TextBox TPerawat2;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRawatLaborat;
    private javax.swing.JTabbedPane TabRawatLaboratBayar;
    private widget.TextBox TagihanPPN;
    private widget.TextBox TtlSemua;
    private widget.CekBox chkPoli;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.TextBox kdptg;
    private widget.TextBox kdptg2;
    private widget.Label label11;
    private widget.Label label9;
    private widget.panelisi panelGlass1;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBersihkanDetailLaborat;
    private javax.swing.JMenuItem ppBersihkanDetailLaboratBayar;
    private javax.swing.JMenuItem ppBersihkanLaborat;
    private javax.swing.JMenuItem ppBersihkanLaboratBayar;
    private javax.swing.JMenuItem ppBersihkanObat;
    private javax.swing.JMenuItem ppBersihkanObatBayar;
    private javax.swing.JMenuItem ppBersihkanRadiologi;
    private javax.swing.JMenuItem ppBersihkanRadiologiBayar;
    private javax.swing.JMenuItem ppBersihkanTindakanDr;
    private javax.swing.JMenuItem ppBersihkanTindakanDrBayar;
    private javax.swing.JMenuItem ppBersihkanTindakanDrPr;
    private javax.swing.JMenuItem ppBersihkanTindakanDrPrBayar;
    private javax.swing.JMenuItem ppBersihkanTindakanPr;
    private javax.swing.JMenuItem ppBersihkanTindakanPrBayar;
    private javax.swing.JMenuItem ppSemuaDetailLaborat;
    private javax.swing.JMenuItem ppSemuaDetailLaboratBayar;
    private javax.swing.JMenuItem ppSemuaLaborat;
    private javax.swing.JMenuItem ppSemuaLaboratBayar;
    private javax.swing.JMenuItem ppSemuaObat;
    private javax.swing.JMenuItem ppSemuaObatBayar;
    private javax.swing.JMenuItem ppSemuaRadiologi;
    private javax.swing.JMenuItem ppSemuaRadiologiBayar;
    private javax.swing.JMenuItem ppSemuaTindakanDr;
    private javax.swing.JMenuItem ppSemuaTindakanDrBayar;
    private javax.swing.JMenuItem ppSemuaTindakanDrPr;
    private javax.swing.JMenuItem ppSemuaTindakanDrPrBayar;
    private javax.swing.JMenuItem ppSemuaTindakanPr;
    private javax.swing.JMenuItem ppSemuaTindakanPrBayar;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbAkunBayar;
    private widget.Table tbBilling;
    private widget.Table tbDetailLaborat;
    private widget.Table tbDetailLaboratBayar;
    private widget.Table tbLaborat;
    private widget.Table tbLaboratBayar;
    private widget.Table tbObat;
    private widget.Table tbObatBayar;
    private widget.Table tbRadiologi;
    private widget.Table tbRadiologiBayar;
    private widget.Table tbTindakanDr;
    private widget.Table tbTindakanDrBayar;
    private widget.Table tbTindakanDrPr;
    private widget.Table tbTindakanDrPrBayar;
    private widget.Table tbTindakanPr;
    private widget.Table tbTindakanPrBayar;
    // End of variables declaration//GEN-END:variables

    public void setNoRm(String norwt,String kodedokter, String namadokter,String KodePoli) {
        TNoRw.setText(norwt);
        this.kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt);
        this.kd_poli=KodePoli;
        KdDok.setText(kodedokter);
        TDokter.setText(namadokter);
        KdDok2.setText(kodedokter);
        TDokter2.setText(namadokter);
        KdDokPerujukLab.setText(kodedokter);
        TDokterPerujukLab.setText(namadokter);
        KdDokPerujukRad.setText(kodedokter);
        TDokterPerujukRad.setText(namadokter);
        isRawat();
        isPsien(); 
        TabRawat.setSelectedIndex(0);
        TabRawatMouseClicked(null);
    }
    
    private void isRawat(){
        DTPTgl.setDate(new Date());
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
        chkPoli.setText("Unit/Instansi : "+Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", kd_poli));
        TBiaya.setText(Sequel.cariIsi("select biaya_reg from reg_periksa where no_rawat=?", TNoRw.getText()));
    }

    private void isPsien(){
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }

    private void tampilAkunBayar() {
        if(statushapus==true){
            try {
                Valid.tabelKosong(tabModeAkunBayar);
                psakunbayar=koneksi.prepareStatement(
                        "select akun_bayar.nama_bayar,akun_bayar.kd_rek,akun_bayar.ppn from akun_bayar "+
                        "inner join detail_nota_jalan on akun_bayar.nama_bayar=detail_nota_jalan.nama_bayar "+
                        "where detail_nota_jalan.no_rawat=? and akun_bayar.nama_bayar like ? order by akun_bayar.nama_bayar");
                try{
                    psakunbayar.setString(1,TNoRw.getText());
                    psakunbayar.setString(2,"%"+TCari.getText()+"%");
                    rsakunbayar=psakunbayar.executeQuery();
                    while(rsakunbayar.next()){                    
                        tabModeAkunBayar.addRow(new Object[]{rsakunbayar.getString(1),rsakunbayar.getString(2),"",rsakunbayar.getDouble(3),""});
                    } 
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rsakunbayar != null){
                        rsakunbayar.close();
                    } 
                    if(psakunbayar != null){
                        psakunbayar.close();
                    } 
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }else{
            try{           
                jml=0;
                for(z=0;z<tbAkunBayar.getRowCount();z++){
                    if(!tbAkunBayar.getValueAt(z,2).toString().equals("")){
                        jml++;
                    }
                }
                Nama_Akun_Bayar=null;
                Kode_Rek_Bayar=null;
                Bayar=null;
                PPN_Persen=null;
                PPN_Besar=null;
                Nama_Akun_Bayar=new String[jml];
                Kode_Rek_Bayar=new String[jml];
                Bayar=new String[jml];
                PPN_Persen=new String[jml];
                PPN_Besar=new String[jml];

                jml=0;
                for(z=0;z<tbAkunBayar.getRowCount();z++){
                    if(!tbAkunBayar.getValueAt(z,2).toString().equals("")){
                        Nama_Akun_Bayar[jml]=tbAkunBayar.getValueAt(z,0).toString();
                        Kode_Rek_Bayar[jml]=tbAkunBayar.getValueAt(z,1).toString();
                        Bayar[jml]=tbAkunBayar.getValueAt(z,2).toString();
                        PPN_Persen[jml]=tbAkunBayar.getValueAt(z,3).toString();
                        PPN_Besar[jml]=tbAkunBayar.getValueAt(z,4).toString();
                        jml++;
                    }
                }

                Valid.tabelKosong(tabModeAkunBayar);

                for(z=0;z<jml;z++){
                    tabModeAkunBayar.addRow(new Object[] {
                        Nama_Akun_Bayar[z],Kode_Rek_Bayar[z],Bayar[z],PPN_Persen[z],PPN_Besar[z]
                    });
                }

                psakunbayar=koneksi.prepareStatement("select * from akun_bayar where nama_bayar like ? order by nama_bayar");
                try{
                    psakunbayar.setString(1,"%"+TCari.getText()+"%");
                    rsakunbayar=psakunbayar.executeQuery();
                    while(rsakunbayar.next()){                    
                        tabModeAkunBayar.addRow(new Object[]{rsakunbayar.getString(1),rsakunbayar.getString(2),"",rsakunbayar.getDouble(3),""});
                    } 
                }catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rsakunbayar != null){
                        rsakunbayar.close();
                    } 
                    if(psakunbayar != null){
                        psakunbayar.close();
                    } 
                }
            }catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }
        }            
    }
    
    private void tampilDr() {
        try{  
            if(Sequel.cariInteger("select count(*) from rawat_jl_dr where no_rawat=? and stts_bayar='Belum'",TNoRw.getText())>0){
                Valid.tabelKosong(TabModeTindakanDr);
                pstindakan=koneksi.prepareStatement(
                    "select rawat_jl_dr.kd_jenis_prw, jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                    "rawat_jl_dr.biaya_rawat, rawat_jl_dr.bhp, rawat_jl_dr.material,"+
                    "rawat_jl_dr.tarif_tindakandr, rawat_jl_dr.kso, rawat_jl_dr.menejemen, "+
                    "rawat_jl_dr.tgl_perawatan, rawat_jl_dr.jam_rawat from rawat_jl_dr inner join jns_perawatan "+
                    "inner join kategori_perawatan on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori "+
                    "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                    "rawat_jl_dr.no_rawat=? and rawat_jl_dr.stts_bayar='Belum' order by rawat_jl_dr.kd_jenis_prw");
                try {
                    pstindakan.setString(1,TNoRw.getText());
                    rstindakan=pstindakan.executeQuery();
                    while(rstindakan.next()){
                        TabModeTindakanDr.addRow(new Object[] {
                            false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                            rstindakan.getDouble("biaya_rawat"),rstindakan.getDouble("material"),
                            rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                            0,rstindakan.getDouble("kso"),rstindakan.getDouble("menejemen"),
                            rstindakan.getString("tgl_perawatan"),rstindakan.getString("jam_rawat")
                        });    
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rstindakan != null){
                        rstindakan.close();
                    }
                    if(pstindakan != null){
                        pstindakan.close();
                    }
                }
            }else{
                jml=0;
                for(i=0;i<tbTindakanDr.getRowCount();i++){
                    if(tbTindakanDr.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }

                pilih=null;
                pilih=new boolean[jml]; 
                kode=null;
                kode=new String[jml];
                nama=null;
                nama=new String[jml];
                kategori=null;
                kategori=new String[jml];
                totaltnd=null;
                totaltnd=new double[jml];  
                bagianrs=null;
                bagianrs=new double[jml];
                bhp=null;
                bhp=new double[jml];
                jmdokter=null;
                jmdokter=new double[jml];
                jmperawat=null;
                jmperawat=new double[jml];
                kso=null;
                kso=new double[jml];
                menejemen=null;
                menejemen=new double[jml];
                tanggal=null;
                tanggal=new String[jml];
                jam=null;
                jam=new String[jml];

                index=0;        
                for(i=0;i<tbTindakanDr.getRowCount();i++){
                    if(tbTindakanDr.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbTindakanDr.getValueAt(i,1).toString();
                        nama[index]=tbTindakanDr.getValueAt(i,2).toString();
                        kategori[index]=tbTindakanDr.getValueAt(i,3).toString();
                        totaltnd[index]=Double.parseDouble(tbTindakanDr.getValueAt(i,4).toString());
                        bagianrs[index]=Double.parseDouble(tbTindakanDr.getValueAt(i,5).toString());
                        bhp[index]=Double.parseDouble(tbTindakanDr.getValueAt(i,6).toString());
                        jmdokter[index]=Double.parseDouble(tbTindakanDr.getValueAt(i,7).toString());
                        jmperawat[index]=Double.parseDouble(tbTindakanDr.getValueAt(i,8).toString());  
                        kso[index]=Double.parseDouble(tbTindakanDr.getValueAt(i,9).toString());
                        menejemen[index]=Double.parseDouble(tbTindakanDr.getValueAt(i,10).toString()); 
                        tanggal[index]=tbTindakanDr.getValueAt(i,11).toString();
                        jam[index]=tbTindakanDr.getValueAt(i,12).toString();
                        index++;
                    }
                }       

                Valid.tabelKosong(TabModeTindakanDr);

                for(i=0;i<jml;i++){
                    TabModeTindakanDr.addRow(new Object[] {
                        pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i],tanggal[i],jam[i]
                    });
                }            

                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                        "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                        "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                        "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                        "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                         "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                         "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,kd_poli.trim());
                        pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(4,kd_pj.trim());
                        pstindakan.setString(5,kd_poli.trim());
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(7,kd_pj.trim());
                        pstindakan.setString(8,kd_poli.trim());
                        pstindakan.setString(9,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdr")>0){
                                TabModeTindakanDr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                       "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                       "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");        
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,kd_pj.trim());
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(5,kd_pj.trim());
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdr")>0){
                                TabModeTindakanDr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                       "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                       "where jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");     
                    try {
                        pstindakan.setString(1,kd_poli.trim());
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,kd_poli.trim());
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(5,kd_poli.trim());
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdr")>0){
                                TabModeTindakanDr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                       "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                       "where jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
                    try {
                        pstindakan.setString(1,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdr")>0){
                                TabModeTindakanDr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }    
            }                 
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilPr() {
        try{    
            if(Sequel.cariInteger("select count(*) from rawat_jl_pr where no_rawat=? and stts_bayar='Belum'",TNoRw.getText())>0){
                Valid.tabelKosong(TabModeTindakanPr);
                pstindakan=koneksi.prepareStatement(
                    "select rawat_jl_pr.kd_jenis_prw, jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                    "rawat_jl_pr.biaya_rawat, rawat_jl_pr.bhp, rawat_jl_pr.material,"+
                    "rawat_jl_pr.tarif_tindakanpr, rawat_jl_pr.kso, rawat_jl_pr.menejemen, "+
                    "rawat_jl_pr.tgl_perawatan, rawat_jl_pr.jam_rawat from rawat_jl_pr inner join jns_perawatan "+
                    "inner join kategori_perawatan on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori "+
                    "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                    "rawat_jl_pr.no_rawat=? and rawat_jl_pr.stts_bayar='Belum' order by rawat_jl_pr.kd_jenis_prw");
                try {
                    pstindakan.setString(1,TNoRw.getText());
                    rstindakan=pstindakan.executeQuery();
                    while(rstindakan.next()){
                        TabModeTindakanPr.addRow(new Object[] {
                            false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                            rstindakan.getDouble("biaya_rawat"),rstindakan.getDouble("material"),
                            rstindakan.getDouble("bhp"),0,rstindakan.getDouble("tarif_tindakanpr"),
                            rstindakan.getDouble("kso"),rstindakan.getDouble("menejemen"),
                            rstindakan.getString("tgl_perawatan"),rstindakan.getString("jam_rawat")
                        });    
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rstindakan != null){
                        rstindakan.close();
                    }
                    if(pstindakan != null){
                        pstindakan.close();
                    }
                }
            }else{
                jml=0;
                for(i=0;i<tbTindakanPr.getRowCount();i++){
                    if(tbTindakanPr.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }

                pilih=null;
                pilih=new boolean[jml]; 
                kode=null;
                kode=new String[jml];
                nama=null;
                nama=new String[jml];
                kategori=null;
                kategori=new String[jml];
                totaltnd=null;
                totaltnd=new double[jml];  
                bagianrs=null;
                bagianrs=new double[jml];
                bhp=null;
                bhp=new double[jml];
                jmdokter=null;
                jmdokter=new double[jml];
                jmperawat=null;
                jmperawat=new double[jml];
                kso=null;
                kso=new double[jml];
                menejemen=null;
                menejemen=new double[jml];
                tanggal=null;
                tanggal=new String[jml];
                jam=null;
                jam=new String[jml];

                index=0;        
                for(i=0;i<tbTindakanPr.getRowCount();i++){
                    if(tbTindakanPr.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbTindakanPr.getValueAt(i,1).toString();
                        nama[index]=tbTindakanPr.getValueAt(i,2).toString();
                        kategori[index]=tbTindakanPr.getValueAt(i,3).toString();
                        totaltnd[index]=Double.parseDouble(tbTindakanPr.getValueAt(i,4).toString());
                        bagianrs[index]=Double.parseDouble(tbTindakanPr.getValueAt(i,5).toString());
                        bhp[index]=Double.parseDouble(tbTindakanPr.getValueAt(i,6).toString());
                        jmdokter[index]=Double.parseDouble(tbTindakanPr.getValueAt(i,7).toString());
                        jmperawat[index]=Double.parseDouble(tbTindakanPr.getValueAt(i,8).toString());  
                        kso[index]=Double.parseDouble(tbTindakanPr.getValueAt(i,9).toString());
                        menejemen[index]=Double.parseDouble(tbTindakanPr.getValueAt(i,10).toString()); 
                        tanggal[index]=tbTindakanPr.getValueAt(i,11).toString();
                        jam[index]=tbTindakanPr.getValueAt(i,12).toString();
                        index++;
                    }
                }       

                Valid.tabelKosong(TabModeTindakanPr);

                for(i=0;i<jml;i++){
                    TabModeTindakanPr.addRow(new Object[] {
                        pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i],tanggal[i],jam[i]
                    });
                }
                
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                        "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                        "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                        "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                        "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                         "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                         "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,kd_poli.trim());
                        pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(4,kd_pj.trim());
                        pstindakan.setString(5,kd_poli.trim());
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(7,kd_pj.trim());
                        pstindakan.setString(8,kd_poli.trim());
                        pstindakan.setString(9,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrpr")>0){
                                TabModeTindakanPr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                       "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                       "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");        
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,kd_pj.trim());
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(5,kd_pj.trim());
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrpr")>0){
                                TabModeTindakanPr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                       "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                       "where jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");     
                    try {
                        pstindakan.setString(1,kd_poli.trim());
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,kd_poli.trim());
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(5,kd_poli.trim());
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrpr")>0){
                                TabModeTindakanPr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                       "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                       "where jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
                    try {
                        pstindakan.setString(1,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrpr")>0){
                                TabModeTindakanPr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }  
            }           
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilDrPr() {
        try{                      
            if(Sequel.cariInteger("select count(*) from rawat_jl_drpr where no_rawat=? and stts_bayar='Belum'",TNoRw.getText())>0){
                Valid.tabelKosong(TabModeTindakanDrPr);
                pstindakan=koneksi.prepareStatement(
                    "select rawat_jl_drpr.kd_jenis_prw, jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                    "rawat_jl_drpr.biaya_rawat, rawat_jl_drpr.bhp, rawat_jl_drpr.material,"+
                    "rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr, rawat_jl_drpr.kso, rawat_jl_drpr.menejemen, "+
                    "rawat_jl_drpr.tgl_perawatan, rawat_jl_drpr.jam_rawat from rawat_jl_drpr inner join jns_perawatan "+
                    "inner join kategori_perawatan on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori "+
                    "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                    "rawat_jl_drpr.no_rawat=? and rawat_jl_drpr.stts_bayar='Belum' order by rawat_jl_drpr.kd_jenis_prw");
                try {
                    pstindakan.setString(1,TNoRw.getText());
                    rstindakan=pstindakan.executeQuery();
                    while(rstindakan.next()){
                        TabModeTindakanDrPr.addRow(new Object[] {
                            false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                            rstindakan.getDouble("biaya_rawat"),rstindakan.getDouble("material"),
                            rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                            rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),rstindakan.getDouble("menejemen"),
                            rstindakan.getString("tgl_perawatan"),rstindakan.getString("jam_rawat")
                        });    
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rstindakan != null){
                        rstindakan.close();
                    }
                    if(pstindakan != null){
                        pstindakan.close();
                    }
                }
            }else{
                jml=0;
                for(i=0;i<tbTindakanDrPr.getRowCount();i++){
                    if(tbTindakanDrPr.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }

                pilih=null;
                pilih=new boolean[jml]; 
                kode=null;
                kode=new String[jml];
                nama=null;
                nama=new String[jml];
                kategori=null;
                kategori=new String[jml];
                totaltnd=null;
                totaltnd=new double[jml];  
                bagianrs=null;
                bagianrs=new double[jml];
                bhp=null;
                bhp=new double[jml];
                jmdokter=null;
                jmdokter=new double[jml];
                jmperawat=null;
                jmperawat=new double[jml];
                kso=null;
                kso=new double[jml];
                menejemen=null;
                menejemen=new double[jml];
                tanggal=null;
                tanggal=new String[jml];
                jam=null;
                jam=new String[jml];

                index=0;        
                for(i=0;i<tbTindakanDrPr.getRowCount();i++){
                    if(tbTindakanDrPr.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbTindakanDrPr.getValueAt(i,1).toString();
                        nama[index]=tbTindakanDrPr.getValueAt(i,2).toString();
                        kategori[index]=tbTindakanDrPr.getValueAt(i,3).toString();
                        totaltnd[index]=Double.parseDouble(tbTindakanDrPr.getValueAt(i,4).toString());
                        bagianrs[index]=Double.parseDouble(tbTindakanDrPr.getValueAt(i,5).toString());
                        bhp[index]=Double.parseDouble(tbTindakanDrPr.getValueAt(i,6).toString());
                        jmdokter[index]=Double.parseDouble(tbTindakanDrPr.getValueAt(i,7).toString());
                        jmperawat[index]=Double.parseDouble(tbTindakanDrPr.getValueAt(i,8).toString());  
                        kso[index]=Double.parseDouble(tbTindakanDrPr.getValueAt(i,9).toString());
                        menejemen[index]=Double.parseDouble(tbTindakanDrPr.getValueAt(i,10).toString()); 
                        tanggal[index]=tbTindakanDrPr.getValueAt(i,11).toString();
                        jam[index]=tbTindakanDrPr.getValueAt(i,12).toString();
                        index++;
                    }
                }       

                Valid.tabelKosong(TabModeTindakanDrPr);

                for(i=0;i<jml;i++){
                    TabModeTindakanDrPr.addRow(new Object[] {
                        pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i],tanggal[i],jam[i]
                    });
                }
                
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                        "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                        "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                        "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                        "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                         "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                         "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,kd_poli.trim());
                        pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(4,kd_pj.trim());
                        pstindakan.setString(5,kd_poli.trim());
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(7,kd_pj.trim());
                        pstindakan.setString(8,kd_poli.trim());
                        pstindakan.setString(9,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdrpr")>0){
                                TabModeTindakanDrPr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                       "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                       "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");        
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,kd_pj.trim());
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(5,kd_pj.trim());
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdrpr")>0){
                                TabModeTindakanDrPr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                       "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                       "where jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");     
                    try {
                        pstindakan.setString(1,kd_poli.trim());
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,kd_poli.trim());
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(5,kd_poli.trim());
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdrpr")>0){
                                TabModeTindakanDrPr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                       "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                       "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                       "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                       "where jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                        "jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                        "jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
                    try {
                        pstindakan.setString(1,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){
                            if(rstindakan.getDouble("total_byrdrpr")>0){
                                TabModeTindakanDrPr.addRow(new Object[] {
                                    false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                                    rstindakan.getDouble("total_byrdrpr"),rstindakan.getDouble("material"),
                                    rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                                    rstindakan.getDouble("tarif_tindakanpr"),rstindakan.getDouble("kso"),
                                    rstindakan.getDouble("menejemen"),"0000-00-00","00:00:00"
                                });
                            }                        
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rstindakan != null){
                            rstindakan.close();
                        }
                        if(pstindakan != null){
                            pstindakan.close();
                        }
                    }
                }  
            }           
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampilRadiologi() {         
        try{    
            if(Sequel.cariInteger("select count(*) from permintaan_pemeriksaan_radiologi inner join permintaan_radiologi "+
                    "on permintaan_radiologi.noorder=permintaan_pemeriksaan_radiologi.noorder where permintaan_radiologi.no_rawat=? "+
                    "and permintaan_pemeriksaan_radiologi.stts_bayar='Belum'",TNoRw.getText())>0){
                Valid.tabelKosong(TabModeRadiologi);
                pstindakan=koneksi.prepareStatement(
                    "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                    "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                    "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                    "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,permintaan_radiologi.tgl_permintaan,"+
                    "permintaan_radiologi.jam_permintaan,permintaan_radiologi.noorder "+
                    "from jns_perawatan_radiologi inner join permintaan_radiologi inner join permintaan_pemeriksaan_radiologi "+
                    "on jns_perawatan_radiologi.kd_jenis_prw=permintaan_pemeriksaan_radiologi.kd_jenis_prw and "+
                    "permintaan_radiologi.noorder=permintaan_pemeriksaan_radiologi.noorder where "+
                    "permintaan_radiologi.no_rawat=? and permintaan_pemeriksaan_radiologi.stts_bayar='Belum' order by jns_perawatan_radiologi.kd_jenis_prw");
                try {
                    pstindakan.setString(1,TNoRw.getText());
                    rstindakan=pstindakan.executeQuery();
                    while(rstindakan.next()){                
                        TabModeRadiologi.addRow(new Object[]{
                            false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                            rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                            rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                            rstindakan.getDouble(10),rstindakan.getString(11),rstindakan.getString(12),
                            rstindakan.getString(13)
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi 1 : "+e);
                } finally{
                    if(rstindakan!=null){
                        rstindakan.close();
                    }
                    if(pstindakan!=null){
                        pstindakan.close();
                    }                
                }
            }else{
                jml=0;
                for(i=0;i<tbRadiologi.getRowCount();i++){
                    if(tbRadiologi.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }

                pilih=null;
                pilih=new boolean[jml];
                kode=null;
                kode=new String[jml];
                nama=null;
                nama=new String[jml];
                totaltnd=null;
                totaltnd=new double[jml];
                bagianrs=null;
                bagianrs=new double[jml];
                bhp=null;
                bhp=new double[jml];
                tarif_perujuk=null;
                tarif_perujuk=new double[jml];
                jmdokter=null;
                jmdokter=new double[jml];
                jmperawat=null;
                jmperawat=new double[jml];
                kso=null;
                kso=new double[jml];
                menejemen=null;
                menejemen=new double[jml];
                tanggal=null;
                tanggal=new String[jml];
                jam=null;
                jam=new String[jml];
                noorder=null;
                noorder=new String[jml];


                index=0; 
                for(i=0;i<tbRadiologi.getRowCount();i++){
                    if(tbRadiologi.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbRadiologi.getValueAt(i,1).toString();
                        nama[index]=tbRadiologi.getValueAt(i,2).toString();
                        totaltnd[index]=Double.parseDouble(tbRadiologi.getValueAt(i,3).toString());
                        bagianrs[index]=Double.parseDouble(tbRadiologi.getValueAt(i,4).toString());
                        bhp[index]=Double.parseDouble(tbRadiologi.getValueAt(i,5).toString());
                        tarif_perujuk[index]=Double.parseDouble(tbRadiologi.getValueAt(i,6).toString());
                        jmdokter[index]=Double.parseDouble(tbRadiologi.getValueAt(i,7).toString());
                        jmperawat[index]=Double.parseDouble(tbRadiologi.getValueAt(i,8).toString());
                        kso[index]=Double.parseDouble(tbRadiologi.getValueAt(i,9).toString());
                        menejemen[index]=Double.parseDouble(tbRadiologi.getValueAt(i,10).toString()); 
                        tanggal[index]=tbRadiologi.getValueAt(i,11).toString();
                        jam[index]=tbRadiologi.getValueAt(i,12).toString();
                        noorder[index]=tbRadiologi.getValueAt(i,13).toString();
                        index++;
                    }
                }

                Valid.tabelKosong(TabModeRadiologi);
                for(i=0;i<jml;i++){                
                    TabModeRadiologi.addRow(new Object[] {pilih[i],kode[i],nama[i],totaltnd[i],bagianrs[i],bhp[i],tarif_perujuk[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i],tanggal[i],jam[i],noorder[i]});
                }
                
                if(cara_bayar_radiologi.equals("Yes")&&kelas_radiologi.equals("No")){
                    pstindakan=koneksi.prepareStatement(
                        "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                        "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                        "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                        "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kd_pj=? or jns_perawatan_radiologi.kd_pj='-') and jns_perawatan_radiologi.kd_jenis_prw like ? or "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kd_pj=? or jns_perawatan_radiologi.kd_pj='-') and jns_perawatan_radiologi.nm_perawatan like ? "+
                        "order by jns_perawatan_radiologi.kd_jenis_prw");
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,kd_pj.trim());
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){                
                            TabModeRadiologi.addRow(new Object[]{
                                false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                                rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                                rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                                rstindakan.getDouble(10),"0000-00-00","00:00:00",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 1 : "+e);
                    } finally{
                        if(rstindakan!=null){
                            rstindakan.close();
                        }
                        if(pstindakan!=null){
                            pstindakan.close();
                        }                
                    }
                }else if(cara_bayar_radiologi.equals("No")&&kelas_radiologi.equals("No")){
                    pstindakan=koneksi.prepareStatement(
                        "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                        "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                        "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                        "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where "+
                        " jns_perawatan_radiologi.status='1' and jns_perawatan_radiologi.kd_jenis_prw like ? or "+
                        " jns_perawatan_radiologi.status='1' and jns_perawatan_radiologi.nm_perawatan like ?  "+
                        "order by jns_perawatan_radiologi.kd_jenis_prw");
                    try {
                        pstindakan.setString(1,"%"+TCariTindakan.getText().trim()+"%");                
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();            
                        while(rstindakan.next()){                
                            TabModeRadiologi.addRow(new Object[]{
                                false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                                rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                                rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                                rstindakan.getDouble(10),"0000-00-00","00:00:00",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 1 : "+e);
                    } finally{
                        if(rstindakan!=null){
                            rstindakan.close();
                        }
                        if(pstindakan!=null){
                            pstindakan.close();
                        }                
                    }
                }else if(cara_bayar_radiologi.equals("Yes")&&kelas_radiologi.equals("Yes")){
                    pstindakan=koneksi.prepareStatement(
                        "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                        "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                        "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                        "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kd_pj=? or jns_perawatan_radiologi.kd_pj='-') and (jns_perawatan_radiologi.kelas=? or jns_perawatan_radiologi.kelas='-') and jns_perawatan_radiologi.kd_jenis_prw like ? or "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kd_pj=? or jns_perawatan_radiologi.kd_pj='-') and (jns_perawatan_radiologi.kelas=? or jns_perawatan_radiologi.kelas='-') and jns_perawatan_radiologi.nm_perawatan like ? "+
                        "order by jns_perawatan_radiologi.kd_jenis_prw");
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,"Rawat Jalan");
                        pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(4,kd_pj.trim());
                        pstindakan.setString(5,"Rawat Jalan");
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){                
                            TabModeRadiologi.addRow(new Object[]{
                                false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                                rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                                rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                                rstindakan.getDouble(10),"0000-00-00","00:00:00",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 1 : "+e);
                    } finally{
                        if(rstindakan!=null){
                            rstindakan.close();
                        }
                        if(pstindakan!=null){
                            pstindakan.close();
                        }                
                    }
                }else if(cara_bayar_radiologi.equals("No")&&kelas_radiologi.equals("Yes")){
                    pstindakan=koneksi.prepareStatement(
                        "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                        "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                        "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                        "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kelas=? or jns_perawatan_radiologi.kelas='-') and jns_perawatan_radiologi.kd_jenis_prw like ? or "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kelas=? or jns_perawatan_radiologi.kelas='-') and jns_perawatan_radiologi.nm_perawatan like ?  "+
                        "order by jns_perawatan_radiologi.kd_jenis_prw");
                    try {
                        pstindakan.setString(1,"Rawat Jalan");
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%"); 
                        pstindakan.setString(3,"Rawat Jalan");               
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){                
                            TabModeRadiologi.addRow(new Object[]{
                                false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                                rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                                rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                                rstindakan.getDouble(10),"0000-00-00","00:00:00",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 1 : "+e);
                    } finally{
                        if(rstindakan!=null){
                            rstindakan.close();
                        }
                        if(pstindakan!=null){
                            pstindakan.close();
                        }                
                    }
                }  
            }          
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    public void tampilLaborat() {         
        try{    
            if(Sequel.cariInteger("select count(*) from permintaan_pemeriksaan_lab inner join permintaan_lab "+
                    "on permintaan_lab.noorder=permintaan_pemeriksaan_lab.noorder where permintaan_lab.no_rawat=? "+
                    "and permintaan_pemeriksaan_lab.stts_bayar='Belum'",TNoRw.getText())>0){
                Valid.tabelKosong(TabModeLaborat);
                pstindakan=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                    "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                    "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                    "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,permintaan_lab.tgl_permintaan,"+
                    "permintaan_lab.jam_permintaan,permintaan_lab.noorder "+
                    "from jns_perawatan_lab inner join permintaan_lab inner join permintaan_pemeriksaan_lab "+
                    "on jns_perawatan_lab.kd_jenis_prw=permintaan_pemeriksaan_lab.kd_jenis_prw and "+
                    "permintaan_lab.noorder=permintaan_pemeriksaan_lab.noorder where "+
                    "permintaan_lab.no_rawat=? and permintaan_pemeriksaan_lab.stts_bayar='Belum' order by jns_perawatan_lab.kd_jenis_prw");
                try {
                    pstindakan.setString(1,TNoRw.getText());
                    rstindakan=pstindakan.executeQuery();
                    while(rstindakan.next()){                
                        TabModeLaborat.addRow(new Object[]{
                            false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                            rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                            rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                            rstindakan.getDouble(10),rstindakan.getString(11),rstindakan.getString(12),
                            rstindakan.getString(13)
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi 1 : "+e);
                } finally{
                    if(rstindakan!=null){
                        rstindakan.close();
                    }
                    if(pstindakan!=null){
                        pstindakan.close();
                    }                
                }
            }else{
                jml=0;
                for(i=0;i<tbLaborat.getRowCount();i++){
                    if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }

                pilih=null;
                pilih=new boolean[jml];
                kode=null;
                kode=new String[jml];
                nama=null;
                nama=new String[jml];
                totaltnd=null;
                totaltnd=new double[jml];
                bagianrs=null;
                bagianrs=new double[jml];
                bhp=null;
                bhp=new double[jml];
                tarif_perujuk=null;
                tarif_perujuk=new double[jml];
                jmdokter=null;
                jmdokter=new double[jml];
                jmperawat=null;
                jmperawat=new double[jml];
                kso=null;
                kso=new double[jml];
                menejemen=null;
                menejemen=new double[jml];
                tanggal=null;
                tanggal=new String[jml];
                jam=null;
                jam=new String[jml];
                noorder=null;
                noorder=new String[jml];


                index=0; 
                for(i=0;i<tbLaborat.getRowCount();i++){
                    if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbLaborat.getValueAt(i,1).toString();
                        nama[index]=tbLaborat.getValueAt(i,2).toString();
                        totaltnd[index]=Double.parseDouble(tbLaborat.getValueAt(i,3).toString());
                        bagianrs[index]=Double.parseDouble(tbLaborat.getValueAt(i,4).toString());
                        bhp[index]=Double.parseDouble(tbLaborat.getValueAt(i,5).toString());
                        tarif_perujuk[index]=Double.parseDouble(tbLaborat.getValueAt(i,6).toString());
                        jmdokter[index]=Double.parseDouble(tbLaborat.getValueAt(i,7).toString());
                        jmperawat[index]=Double.parseDouble(tbLaborat.getValueAt(i,8).toString());
                        kso[index]=Double.parseDouble(tbLaborat.getValueAt(i,9).toString());
                        menejemen[index]=Double.parseDouble(tbLaborat.getValueAt(i,10).toString()); 
                        tanggal[index]=tbLaborat.getValueAt(i,11).toString();
                        jam[index]=tbLaborat.getValueAt(i,12).toString();
                        noorder[index]=tbLaborat.getValueAt(i,13).toString();
                        index++;
                    }
                }

                Valid.tabelKosong(TabModeLaborat);
                for(i=0;i<jml;i++){                
                    TabModeLaborat.addRow(new Object[] {pilih[i],kode[i],nama[i],totaltnd[i],bagianrs[i],bhp[i],tarif_perujuk[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i],tanggal[i],jam[i],noorder[i]});
                }
                
                if(cara_bayar_lab.equals("Yes")&&kelas_lab.equals("No")){
                    pstindakan=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                        "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                        "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                        "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and jns_perawatan_lab.kd_jenis_prw like ? or "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and jns_perawatan_lab.nm_perawatan like ? "+
                        "order by jns_perawatan_lab.kd_jenis_prw");
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(3,kd_pj.trim());
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){                
                            TabModeLaborat.addRow(new Object[]{
                                false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                                rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                                rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                                rstindakan.getDouble(10),"0000-00-00","00:00:00",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 1 : "+e);
                    } finally{
                        if(rstindakan!=null){
                            rstindakan.close();
                        }
                        if(pstindakan!=null){
                            pstindakan.close();
                        }                
                    }
                }else if(cara_bayar_lab.equals("No")&&kelas_lab.equals("No")){
                    pstindakan=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                        "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                        "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                        "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                        " jns_perawatan_lab.status='1' and jns_perawatan_lab.kd_jenis_prw like ? or "+
                        " jns_perawatan_lab.status='1' and jns_perawatan_lab.nm_perawatan like ?  "+
                        "order by jns_perawatan_lab.kd_jenis_prw");
                    try {
                        pstindakan.setString(1,"%"+TCariTindakan.getText().trim()+"%");                
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();            
                        while(rstindakan.next()){                
                            TabModeLaborat.addRow(new Object[]{
                                false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                                rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                                rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                                rstindakan.getDouble(10),"0000-00-00","00:00:00",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 1 : "+e);
                    } finally{
                        if(rstindakan!=null){
                            rstindakan.close();
                        }
                        if(pstindakan!=null){
                            pstindakan.close();
                        }                
                    }
                }else if(cara_bayar_lab.equals("Yes")&&kelas_lab.equals("Yes")){
                    pstindakan=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                        "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                        "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                        "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') and jns_perawatan_lab.kd_jenis_prw like ? or "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') and jns_perawatan_lab.nm_perawatan like ? "+
                        "order by jns_perawatan_lab.kd_jenis_prw");
                    try {
                        pstindakan.setString(1,kd_pj.trim());
                        pstindakan.setString(2,"Rawat Jalan");
                        pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                        pstindakan.setString(4,kd_pj.trim());
                        pstindakan.setString(5,"Rawat Jalan");
                        pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){                
                            TabModeLaborat.addRow(new Object[]{
                                false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                                rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                                rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                                rstindakan.getDouble(10),"0000-00-00","00:00:00",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 1 : "+e);
                    } finally{
                        if(rstindakan!=null){
                            rstindakan.close();
                        }
                        if(pstindakan!=null){
                            pstindakan.close();
                        }                
                    }
                }else if(cara_bayar_lab.equals("No")&&kelas_lab.equals("Yes")){
                    pstindakan=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                        "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                        "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                        "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') and jns_perawatan_lab.kd_jenis_prw like ? or "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') and jns_perawatan_lab.nm_perawatan like ?  "+
                        "order by jns_perawatan_lab.kd_jenis_prw");
                    try {
                        pstindakan.setString(1,"Rawat Jalan");
                        pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%"); 
                        pstindakan.setString(3,"Rawat Jalan");               
                        pstindakan.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                        rstindakan=pstindakan.executeQuery();
                        while(rstindakan.next()){                
                            TabModeLaborat.addRow(new Object[]{
                                false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                                rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                                rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                                rstindakan.getDouble(10),"0000-00-00","00:00:00",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 1 : "+e);
                    } finally{
                        if(rstindakan!=null){
                            rstindakan.close();
                        }
                        if(pstindakan!=null){
                            pstindakan.close();
                        }                
                    }
                }  
            }          
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    public void tampilDetailLaborat() {         
        try{    
            if(Sequel.cariInteger("select count(*) from permintaan_detail_permintaan_lab inner join permintaan_lab "+
                    "on permintaan_lab.noorder=permintaan_detail_permintaan_lab.noorder where permintaan_lab.no_rawat=? "+
                    "and permintaan_detail_permintaan_lab.stts_bayar='Belum'",TNoRw.getText())>0){
                Valid.tabelKosong(TabModeDetailLaborat);
                pstindakan=koneksi.prepareStatement(
                    "select template_laboratorium.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.biaya_item,"+
                    "template_laboratorium.bagian_rs,template_laboratorium.bhp,template_laboratorium.bagian_perujuk,"+
                    "template_laboratorium.bagian_dokter,template_laboratorium.bagian_laborat,"+
                    "template_laboratorium.kso,template_laboratorium.menejemen,permintaan_lab.tgl_permintaan,"+
                    "permintaan_lab.jam_permintaan,permintaan_lab.noorder,permintaan_detail_permintaan_lab.kd_jenis_prw "+
                    "from template_laboratorium inner join permintaan_lab inner join permintaan_detail_permintaan_lab "+
                    "on template_laboratorium.id_template=permintaan_detail_permintaan_lab.id_template and "+
                    "permintaan_lab.noorder=permintaan_detail_permintaan_lab.noorder where "+
                    "permintaan_lab.no_rawat=? and permintaan_detail_permintaan_lab.stts_bayar='Belum' order by template_laboratorium.urut");
                try {
                    pstindakan.setString(1,TNoRw.getText());
                    rstindakan=pstindakan.executeQuery();
                    while(rstindakan.next()){                
                        TabModeDetailLaborat.addRow(new Object[]{
                            false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                            rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                            rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                            rstindakan.getDouble(10),rstindakan.getString(11),rstindakan.getString(12),
                            rstindakan.getString(13),rstindakan.getString(14)
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi 1 : "+e);
                } finally{
                    if(rstindakan!=null){
                        rstindakan.close();
                    }
                    if(pstindakan!=null){
                        pstindakan.close();
                    }                
                }
            }else{
                jml=0;
                for(i=0;i<tbDetailLaborat.getRowCount();i++){
                    if(tbDetailLaborat.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }

                pilih=null;
                pilih=new boolean[jml];
                kode=null;
                kode=new String[jml];
                nama=null;
                nama=new String[jml];
                totaltnd=null;
                totaltnd=new double[jml];
                bagianrs=null;
                bagianrs=new double[jml];
                bhp=null;
                bhp=new double[jml];
                tarif_perujuk=null;
                tarif_perujuk=new double[jml];
                jmdokter=null;
                jmdokter=new double[jml];
                jmperawat=null;
                jmperawat=new double[jml];
                kso=null;
                kso=new double[jml];
                menejemen=null;
                menejemen=new double[jml];
                tanggal=null;
                tanggal=new String[jml];
                jam=null;
                jam=new String[jml];
                noorder=null;
                noorder=new String[jml];

                index=0; 
                for(i=0;i<tbDetailLaborat.getRowCount();i++){
                    if(tbDetailLaborat.getValueAt(i,0).toString().equals("true")){
                        pilih[index]=true;
                        kode[index]=tbDetailLaborat.getValueAt(i,1).toString();
                        nama[index]=tbDetailLaborat.getValueAt(i,2).toString();
                        totaltnd[index]=Double.parseDouble(tbDetailLaborat.getValueAt(i,3).toString());
                        bagianrs[index]=Double.parseDouble(tbDetailLaborat.getValueAt(i,4).toString());
                        bhp[index]=Double.parseDouble(tbDetailLaborat.getValueAt(i,5).toString());
                        tarif_perujuk[index]=Double.parseDouble(tbDetailLaborat.getValueAt(i,6).toString());
                        jmdokter[index]=Double.parseDouble(tbDetailLaborat.getValueAt(i,7).toString());
                        jmperawat[index]=Double.parseDouble(tbDetailLaborat.getValueAt(i,8).toString());
                        kso[index]=Double.parseDouble(tbDetailLaborat.getValueAt(i,9).toString());
                        menejemen[index]=Double.parseDouble(tbDetailLaborat.getValueAt(i,10).toString()); 
                        tanggal[index]=tbDetailLaborat.getValueAt(i,11).toString();
                        jam[index]=tbDetailLaborat.getValueAt(i,12).toString();
                        noorder[index]=tbDetailLaborat.getValueAt(i,13).toString();
                        index++;
                    }
                }

                Valid.tabelKosong(TabModeDetailLaborat);
                for(i=0;i<jml;i++){                
                    TabModeDetailLaborat.addRow(new Object[] {pilih[i],kode[i],nama[i],totaltnd[i],bagianrs[i],bhp[i],tarif_perujuk[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i],tanggal[i],jam[i],noorder[i]});
                }
                
                for(i=0;i<tbLaborat.getRowCount();i++){ 
                    if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                        TabModeDetailLaborat.addRow(new Object[]{
                            false,"",tbLaborat.getValueAt(i,2).toString(),0,0,0,0,0,0,0,0,"0000-00-00","00:00:00","",""
                        });
                        pstindakan=koneksi.prepareStatement(
                            "select template_laboratorium.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.biaya_item,"+
                            "template_laboratorium.bagian_rs,template_laboratorium.bhp,template_laboratorium.bagian_perujuk,"+
                            "template_laboratorium.bagian_dokter,template_laboratorium.bagian_laborat,"+
                            "template_laboratorium.kso,template_laboratorium.menejemen,template_laboratorium.kd_jenis_prw from template_laboratorium "+
                            "where template_laboratorium.kd_jenis_prw=? and template_laboratorium.Pemeriksaan like ?  "+
                            "order by template_laboratorium.urut");
                        try {
                            pstindakan.setString(1,tbLaborat.getValueAt(i,1).toString());
                            pstindakan.setString(2,"%"+TCariTindakan.getText().trim()+"%"); 
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){                
                                TabModeDetailLaborat.addRow(new Object[]{
                                    false,rstindakan.getString(1),"   "+rstindakan.getString(2),rstindakan.getDouble(3),
                                    rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                                    rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                                    rstindakan.getDouble(10),"0000-00-00","00:00:00","",rstindakan.getString(11)
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 1 : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }                
                        } 
                    }
                }                        
            }          
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    public void tampilDetailLaboratBayar() {         
        try{  
            Valid.tabelKosong(TabModeDetailLaboratBayar);
            pstindakan=koneksi.prepareStatement(
                "select template_laboratorium.id_template,template_laboratorium.Pemeriksaan,template_laboratorium.biaya_item,"+
                "template_laboratorium.bagian_rs,template_laboratorium.bhp,template_laboratorium.bagian_perujuk,"+
                "template_laboratorium.bagian_dokter,template_laboratorium.bagian_laborat,"+
                "template_laboratorium.kso,template_laboratorium.menejemen,permintaan_lab.tgl_permintaan,"+
                "permintaan_lab.jam_permintaan,permintaan_lab.noorder,permintaan_detail_permintaan_lab.kd_jenis_prw, "+
                "permintaan_detail_permintaan_lab.stts_bayar from template_laboratorium inner join permintaan_lab inner join permintaan_detail_permintaan_lab "+
                "on template_laboratorium.id_template=permintaan_detail_permintaan_lab.id_template and "+
                "permintaan_lab.noorder=permintaan_detail_permintaan_lab.noorder where "+
                "permintaan_lab.no_rawat=? and permintaan_detail_permintaan_lab.stts_bayar<>'Belum' order by template_laboratorium.urut");
            try {
                pstindakan.setString(1,TNoRw.getText());
                rstindakan=pstindakan.executeQuery();
                while(rstindakan.next()){                
                    TabModeDetailLaboratBayar.addRow(new Object[]{
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                        rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                        rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                        rstindakan.getDouble(10),rstindakan.getString(11),rstindakan.getString(12),
                        rstindakan.getString(13),rstindakan.getString(14),rstindakan.getString("stts_bayar")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rstindakan!=null){
                    rstindakan.close();
                }
                if(pstindakan!=null){
                    pstindakan.close();
                }                
            }            
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampilDrBayar() {
        try{  
            Valid.tabelKosong(TabModeTindakanDrBayar);
            pstindakan=koneksi.prepareStatement(
                "select rawat_jl_dr.kd_jenis_prw, jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                "rawat_jl_dr.biaya_rawat, rawat_jl_dr.bhp, rawat_jl_dr.material,"+
                "rawat_jl_dr.tarif_tindakandr, rawat_jl_dr.kso, rawat_jl_dr.menejemen, "+
                "rawat_jl_dr.tgl_perawatan, rawat_jl_dr.jam_rawat,rawat_jl_dr.stts_bayar from rawat_jl_dr inner join jns_perawatan "+
                "inner join kategori_perawatan on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori "+
                "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                "rawat_jl_dr.no_rawat=? and rawat_jl_dr.stts_bayar<>'Belum'");
            try {
                pstindakan.setString(1,TNoRw.getText());
                rstindakan=pstindakan.executeQuery();
                while(rstindakan.next()){
                    TabModeTindakanDrBayar.addRow(new Object[] {
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                        rstindakan.getDouble("biaya_rawat"),rstindakan.getDouble("material"),
                        rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),
                        0,rstindakan.getDouble("kso"),rstindakan.getDouble("menejemen"),
                        rstindakan.getString("tgl_perawatan"),rstindakan.getString("jam_rawat"),
                        rstindakan.getString("stts_bayar")
                    });    
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
            }         
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilPrBayar() {
        try{  
            Valid.tabelKosong(TabModeTindakanPrBayar);
            pstindakan=koneksi.prepareStatement(
                "select rawat_jl_pr.kd_jenis_prw, jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                "rawat_jl_pr.biaya_rawat, rawat_jl_pr.bhp, rawat_jl_pr.material,"+
                "rawat_jl_pr.tarif_tindakanpr, rawat_jl_pr.kso, rawat_jl_pr.menejemen, "+
                "rawat_jl_pr.tgl_perawatan, rawat_jl_pr.jam_rawat,rawat_jl_pr.stts_bayar from rawat_jl_pr inner join jns_perawatan "+
                "inner join kategori_perawatan on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori "+
                "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                "rawat_jl_pr.no_rawat=? and rawat_jl_pr.stts_bayar<>'Belum'");
            try {
                pstindakan.setString(1,TNoRw.getText());
                rstindakan=pstindakan.executeQuery();
                while(rstindakan.next()){
                    TabModeTindakanPrBayar.addRow(new Object[] {
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                        rstindakan.getDouble("biaya_rawat"),rstindakan.getDouble("material"),
                        rstindakan.getDouble("bhp"),0,rstindakan.getDouble("tarif_tindakanpr"),
                        rstindakan.getDouble("kso"),rstindakan.getDouble("menejemen"),
                        rstindakan.getString("tgl_perawatan"),rstindakan.getString("jam_rawat"),
                        rstindakan.getString("stts_bayar")
                    });    
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
            }         
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilDrPrBayar() {
        try{  
            Valid.tabelKosong(TabModeTindakanDrPrBayar);
            pstindakan=koneksi.prepareStatement(
                "select rawat_jl_drpr.kd_jenis_prw, jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                "rawat_jl_drpr.biaya_rawat, rawat_jl_drpr.bhp, rawat_jl_drpr.material,rawat_jl_drpr.tarif_tindakandr,"+
                "rawat_jl_drpr.tarif_tindakanpr, rawat_jl_drpr.kso, rawat_jl_drpr.menejemen, "+
                "rawat_jl_drpr.tgl_perawatan, rawat_jl_drpr.jam_rawat,rawat_jl_drpr.stts_bayar from rawat_jl_drpr inner join jns_perawatan "+
                "inner join kategori_perawatan on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori "+
                "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                "rawat_jl_drpr.no_rawat=? and rawat_jl_drpr.stts_bayar<>'Belum'");
            try {
                pstindakan.setString(1,TNoRw.getText());
                rstindakan=pstindakan.executeQuery();
                while(rstindakan.next()){
                    TabModeTindakanDrPrBayar.addRow(new Object[] {
                        false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3),
                        rstindakan.getDouble("biaya_rawat"),rstindakan.getDouble("material"),
                        rstindakan.getDouble("bhp"),rstindakan.getDouble("tarif_tindakandr"),rstindakan.getDouble("tarif_tindakanpr"),
                        rstindakan.getDouble("kso"),rstindakan.getDouble("menejemen"),
                        rstindakan.getString("tgl_perawatan"),rstindakan.getString("jam_rawat"),
                        rstindakan.getString("stts_bayar")
                    });    
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rstindakan != null){
                    rstindakan.close();
                }
                if(pstindakan != null){
                    pstindakan.close();
                }
            }         
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbilling_parsial());
        BtnHapus.setEnabled(akses.gethapus_nota_salah());
        BtnNota.setEnabled(akses.getbilling_parsial());
        if(Sequel.cariIsi("select tampilkan_tombol_nota_ralan from set_nota").equals("Yes")){
            BtnNota.setVisible(true);
        }else{
            if(akses.getkode().equals("Admin Utama")){
                BtnNota.setVisible(true);
            }else{
                BtnNota.setVisible(false);
            }            
        }
    }

    private void tampilbilling() {
        Valid.tabelKosong(tabModeBilling);
        try{   
            psreg=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,reg_periksa.no_rkm_medis,"+
                    "reg_periksa.kd_poli,reg_periksa.no_rawat,reg_periksa.biaya_reg,current_time() as jam "+
                    "from reg_periksa where reg_periksa.no_rawat=?");
            try{
                psreg.setString(1,TNoRw.getText());
                rsreg=psreg.executeQuery();            
                if(rsreg.next()){
                    NoNota=Sequel.cariIsi("select no_nota from nota_jalan where no_rawat=?",TNoRw.getText());
                    if(NoNota.equals("")){
                        tabModeBilling.addRow(new Object[]{"No.Nota",": "+Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_jalan where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RJ/",6),"",null,null,null,"-"});                
                    }else{
                        tabModeBilling.addRow(new Object[]{"No.Nota",": "+NoNota,"",null,null,null,"-"});                
                    }
                    
                    pscaripoli=koneksi.prepareStatement(sqlpscaripoli);
                    try{
                        pscaripoli.setString(1,rsreg.getString("kd_poli"));
                        rscaripoli=pscaripoli.executeQuery();
                        if(rscaripoli.next()){
                            tabModeBilling.addRow(new Object[]{"Unit/Instansi",": "+rscaripoli.getString(1),"",null,null,null,"-"});
                        }
                    }catch (Exception e) {
                        tabModeBilling.addRow(new Object[]{"Unit/Instansi",": ","",null,null,null,"-"});
                        System.out.println("Notifikasi 1 : "+e);
                    } finally{
                        if(rscaripoli != null){
                            rscaripoli.close();
                        }
                        if(pscaripoli != null){
                            pscaripoli.close();
                        }
                    }
                                        
                    tabModeBilling.addRow(new Object[]{"Tanggal & Jam",": "+rsreg.getString("tgl_registrasi")+" "+rsreg.getString("jam"),"",null,null,null,"-"});
                    tabModeBilling.addRow(new Object[]{"No.RM",": "+TNoRM.getText(),"",null,null,null,"-"});
                    tabModeBilling.addRow(new Object[]{"Nama Pasien",": "+TPasien.getText(),"",null,null,null,"-"});
                    pscarialamat=koneksi.prepareStatement(sqlpscarialamat); 
                    try{
                        pscarialamat.setString(1,TNoRM.getText());
                        rscarialamat=pscarialamat.executeQuery();
                        if(rscarialamat.next()){
                            tabModeBilling.addRow(new Object[]{"Alamat Pasien",": "+rscarialamat.getString(1),"",null,null,null,"-"});
                        }
                    }catch (Exception e) {
                        tabModeBilling.addRow(new Object[]{"Alamat Pasien",": ","",null,null,null,"-"});
                        System.out.println("Notifikasi 2 : "+e);
                    } finally{
                        if(rscarialamat != null){
                            rscarialamat.close();
                        }
                        if(pscarialamat != null){
                            pscarialamat.close();
                        }
                    }
                    //cari dokter yang menangandi  

                    if(centangdokterralan.equals("Yes")){
                        psdokterralan=koneksi.prepareStatement(sqlpsdokterralan);
                        try{
                            psdokterralan.setString(1,TNoRw.getText());
                            rsdokterralan=psdokterralan.executeQuery();
                            if(rsdokterralan.next()){
                                tabModeBilling.addRow(new Object[]{"Dokter ",":","",null,null,null,"-"});
                                tabModeBilling.addRow(new Object[]{"",rsdokterralan.getString("nm_dokter"),"",null,null,null,"Dokter"});   
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 3 : "+e);
                        } finally{
                            if(rsdokterralan != null){
                                rsdokterralan.close();
                            }
                            if(psdokterralan != null){
                                psdokterralan.close();
                            }
                        }
                    }
                    
                    jml=0;
                    for(i=0;i<tbTindakanDr.getRowCount();i++){
                        if(tbTindakanDr.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    
                    for(i=0;i<tbTindakanPr.getRowCount();i++){
                        if(tbTindakanPr.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    
                    for(i=0;i<tbTindakanDrPr.getRowCount();i++){
                        if(tbTindakanDrPr.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    
                    jmlradiologi=0;
                    for(i=0;i<tbRadiologi.getRowCount();i++){
                        if(tbRadiologi.getValueAt(i,0).toString().equals("true")){
                            jml++;
                            if(tbRadiologi.getValueAt(i,11).toString().equals("0000-00-00")){
                                jmlradiologi++;
                            }                                
                        }
                    }
                    
                    jmllaborat=0;
                    for(i=0;i<tbLaborat.getRowCount();i++){
                        if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                            jml++;
                            if(tbLaborat.getValueAt(i,11).toString().equals("0000-00-00")){
                                jmllaborat++;
                            }                                
                        }
                    }
                    
                    for(i=0;i<tbDetailLaborat.getRowCount();i++){
                        if(tbDetailLaborat.getValueAt(i,0).toString().equals("true")){
                            jml++;
                            if(tbDetailLaborat.getValueAt(i,11).toString().equals("0000-00-00")){
                                jmllaborat++;
                            }                                
                        }
                    }
                    
                    Jasa_Medik_Dokter_Tindakan_Ralan=0;
                    Jasa_Medik_Paramedis_Tindakan_Ralan=0;
                    KSO_Tindakan_Ralan=0;
                    Jasa_Sarana_Tindakan_Ralan=0;
                    BHP_Tindakan_Ralan=0;
                    Jasa_Menejemen_Tindakan_Ralan=0;
                    Jasa_Medik_Dokter_Laborat_Ralan=0;
                    Jasa_Medik_Petugas_Laborat_Ralan=0;
                    Kso_Laborat_Ralan=0;
                    Persediaan_Laborat_Rawat_Jalan=0;
                    Jasa_Sarana_Laborat_Ralan=0;
                    Jasa_Perujuk_Laborat_Ralan=0;
                    Jasa_Menejemen_Laborat_Ralan=0;
                    Jasa_Medik_Dokter_Radiologi_Ralan=0;
                    Jasa_Medik_Petugas_Radiologi_Ralan=0;
                    Kso_Radiologi_Ralan=0;
                    Persediaan_Radiologi_Rawat_Jalan=0;
                    Jasa_Sarana_Radiologi_Ralan=0;
                    Jasa_Perujuk_Radiologi_Ralan=0;
                    Jasa_Menejemen_Radiologi_Ralan=0;
                    Obat_Rawat_Jalan=0;
                    subttl=0;
                    ppnobat=0;
                    Suspen_Tindakan_Ralan=0;
                    
                    if(Sequel.cariInteger("select count(no_rawat) from permintaan_registrasi where no_rawat=?",TNoRw.getText())==0){
                        if(chkPoli.isSelected()==true){
                            tabModeBilling.addRow(new Object[]{
                                "Registrasi",":","",Double.parseDouble(TBiaya.getText()),1,Double.parseDouble(TBiaya.getText()),"Registrasi"
                            });
                        }                            
                    }else{
                        chkPoli.setSelected(false);
                    }
                    
                    if(jml>0){
                        tabModeBilling.addRow(new Object[]{"Tindakan",":","",null,null,null,"Ralan Dokter"});
                    }
                    
                    for(i=0;i<tbTindakanDr.getRowCount();i++){
                        if(tbTindakanDr.getValueAt(i,0).toString().equals("true")){
                            if(tbTindakanDr.getValueAt(i,11).toString().equals("0000-00-00")){
                                if((!KdDok.getText().equals(""))&&(!TDokter.getText().equals(""))){
                                    Jasa_Medik_Dokter_Tindakan_Ralan=Jasa_Medik_Dokter_Tindakan_Ralan+Double.parseDouble(tbTindakanDr.getValueAt(i,7).toString());
                                    KSO_Tindakan_Ralan=KSO_Tindakan_Ralan+Double.parseDouble(tbTindakanDr.getValueAt(i,9).toString());
                                    Jasa_Sarana_Tindakan_Ralan=Jasa_Sarana_Tindakan_Ralan+Double.parseDouble(tbTindakanDr.getValueAt(i,5).toString());
                                    BHP_Tindakan_Ralan=BHP_Tindakan_Ralan+Double.parseDouble(tbTindakanDr.getValueAt(i,6).toString());
                                    Jasa_Menejemen_Tindakan_Ralan=Jasa_Menejemen_Tindakan_Ralan+Double.parseDouble(tbTindakanDr.getValueAt(i,10).toString());
                                    tabModeBilling.addRow(new Object[]{
                                        "",tbTindakanDr.getValueAt(i,2).toString(),":",
                                        Double.parseDouble(tbTindakanDr.getValueAt(i,4).toString()),1,
                                        Double.parseDouble(tbTindakanDr.getValueAt(i,4).toString()),"Ralan Dokter"
                                    });
                                }
                            }else{
                                Suspen_Tindakan_Ralan=Suspen_Tindakan_Ralan+Double.parseDouble(tbTindakanDr.getValueAt(i,4).toString());
                                tabModeBilling.addRow(new Object[]{
                                    "",tbTindakanDr.getValueAt(i,2).toString(),":",
                                    Double.parseDouble(tbTindakanDr.getValueAt(i,4).toString()),1,
                                    Double.parseDouble(tbTindakanDr.getValueAt(i,4).toString()),"Ralan Dokter"
                                });
                            }                                    
                        }
                    }
                    
                    for(i=0;i<tbTindakanPr.getRowCount();i++){
                        if(tbTindakanPr.getValueAt(i,0).toString().equals("true")){
                            if(tbTindakanPr.getValueAt(i,11).toString().equals("0000-00-00")){
                                if((!kdptg.getText().equals(""))&&(!TPerawat.getText().equals(""))){
                                    Jasa_Medik_Paramedis_Tindakan_Ralan=Jasa_Medik_Paramedis_Tindakan_Ralan+Double.parseDouble(tbTindakanPr.getValueAt(i,8).toString());
                                    KSO_Tindakan_Ralan=KSO_Tindakan_Ralan+Double.parseDouble(tbTindakanPr.getValueAt(i,9).toString());
                                    Jasa_Sarana_Tindakan_Ralan=Jasa_Sarana_Tindakan_Ralan+Double.parseDouble(tbTindakanPr.getValueAt(i,5).toString());
                                    BHP_Tindakan_Ralan=BHP_Tindakan_Ralan+Double.parseDouble(tbTindakanPr.getValueAt(i,6).toString());
                                    Jasa_Menejemen_Tindakan_Ralan=Jasa_Menejemen_Tindakan_Ralan+Double.parseDouble(tbTindakanPr.getValueAt(i,10).toString());
                                    tabModeBilling.addRow(new Object[]{
                                        "",tbTindakanPr.getValueAt(i,2).toString(),":",
                                        Double.parseDouble(tbTindakanPr.getValueAt(i,4).toString()),1,
                                        Double.parseDouble(tbTindakanPr.getValueAt(i,4).toString()),"Ralan Paramedis"
                                    });
                                }
                            }else{
                                Suspen_Tindakan_Ralan=Suspen_Tindakan_Ralan+Double.parseDouble(tbTindakanPr.getValueAt(i,4).toString());
                                tabModeBilling.addRow(new Object[]{
                                    "",tbTindakanPr.getValueAt(i,2).toString(),":",
                                    Double.parseDouble(tbTindakanPr.getValueAt(i,4).toString()),1,
                                    Double.parseDouble(tbTindakanPr.getValueAt(i,4).toString()),"Ralan Paramedis"
                                });
                            }                                
                        }
                    }
                    
                    for(i=0;i<tbTindakanDrPr.getRowCount();i++){
                        if(tbTindakanDrPr.getValueAt(i,0).toString().equals("true")){
                            if(tbTindakanDrPr.getValueAt(i,11).toString().equals("0000-00-00")){
                                if((!kdptg2.getText().equals(""))&&(!TPerawat2.getText().equals(""))&&(!KdDok2.getText().equals(""))&&(!TDokter2.getText().equals(""))){
                                    Jasa_Medik_Dokter_Tindakan_Ralan=Jasa_Medik_Dokter_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPr.getValueAt(i,7).toString());
                                    Jasa_Medik_Paramedis_Tindakan_Ralan=Jasa_Medik_Paramedis_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPr.getValueAt(i,8).toString());
                                    KSO_Tindakan_Ralan=KSO_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPr.getValueAt(i,9).toString());
                                    Jasa_Sarana_Tindakan_Ralan=Jasa_Sarana_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPr.getValueAt(i,5).toString());
                                    BHP_Tindakan_Ralan=BHP_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPr.getValueAt(i,6).toString());
                                    Jasa_Menejemen_Tindakan_Ralan=Jasa_Menejemen_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPr.getValueAt(i,10).toString());
                                    tabModeBilling.addRow(new Object[]{
                                        "",tbTindakanDrPr.getValueAt(i,2).toString(),":",
                                        Double.parseDouble(tbTindakanDrPr.getValueAt(i,4).toString()),1,
                                        Double.parseDouble(tbTindakanDrPr.getValueAt(i,4).toString()),"Ralan Dokter Paramedis"
                                    });
                                }
                            }else{
                                Suspen_Tindakan_Ralan=Suspen_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPr.getValueAt(i,4).toString());
                                tabModeBilling.addRow(new Object[]{
                                    "",tbTindakanDrPr.getValueAt(i,2).toString(),":",
                                    Double.parseDouble(tbTindakanDrPr.getValueAt(i,4).toString()),1,
                                    Double.parseDouble(tbTindakanDrPr.getValueAt(i,4).toString()),"Ralan Dokter Paramedis"
                                });
                            }                                    
                        }
                    }
                    
                    for(i=0;i<tbRadiologi.getRowCount();i++){
                        if(tbRadiologi.getValueAt(i,0).toString().equals("true")){
                            if(tbRadiologi.getValueAt(i,11).toString().equals("0000-00-00")){
                                if((!KdDokPerujukRad.getText().equals(""))&&(!TDokterPerujukRad.getText().equals(""))){
                                    Jasa_Medik_Dokter_Radiologi_Ralan=Jasa_Medik_Dokter_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,7).toString());
                                    Jasa_Medik_Petugas_Radiologi_Ralan=Jasa_Medik_Petugas_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,8).toString());
                                    Kso_Radiologi_Ralan=Kso_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,9).toString());
                                    Persediaan_Radiologi_Rawat_Jalan=Persediaan_Radiologi_Rawat_Jalan+Double.parseDouble(tbRadiologi.getValueAt(i,5).toString());
                                    Jasa_Sarana_Radiologi_Ralan=Jasa_Sarana_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,4).toString());
                                    Jasa_Perujuk_Radiologi_Ralan=Jasa_Perujuk_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,6).toString());
                                    Jasa_Menejemen_Radiologi_Ralan=Jasa_Menejemen_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,10).toString());
                                    tabModeBilling.addRow(new Object[]{
                                        "",tbRadiologi.getValueAt(i,2).toString(),":",
                                        Double.parseDouble(tbRadiologi.getValueAt(i,3).toString()),1,
                                        Double.parseDouble(tbRadiologi.getValueAt(i,3).toString()),"Radiologi"
                                    });
                                }
                            }else{
                                Jasa_Medik_Dokter_Radiologi_Ralan=Jasa_Medik_Dokter_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,7).toString());
                                Jasa_Medik_Petugas_Radiologi_Ralan=Jasa_Medik_Petugas_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,8).toString());
                                Kso_Radiologi_Ralan=Kso_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,9).toString());
                                Persediaan_Radiologi_Rawat_Jalan=Persediaan_Radiologi_Rawat_Jalan+Double.parseDouble(tbRadiologi.getValueAt(i,5).toString());
                                Jasa_Sarana_Radiologi_Ralan=Jasa_Sarana_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,4).toString());
                                Jasa_Perujuk_Radiologi_Ralan=Jasa_Perujuk_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,6).toString());
                                Jasa_Menejemen_Radiologi_Ralan=Jasa_Menejemen_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,10).toString());
                                tabModeBilling.addRow(new Object[]{
                                    "",tbRadiologi.getValueAt(i,2).toString(),":",
                                    Double.parseDouble(tbRadiologi.getValueAt(i,3).toString()),1,
                                    Double.parseDouble(tbRadiologi.getValueAt(i,3).toString()),"Radiologi"
                                });
                            }                                
                        }
                    }
                    
                    for(i=0;i<tbLaborat.getRowCount();i++){
                        if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                            if(tbLaborat.getValueAt(i,11).toString().equals("0000-00-00")){
                                if((!KdDokPerujukLab.getText().equals(""))&&(!TDokterPerujukLab.getText().equals(""))){
                                    Jasa_Medik_Dokter_Laborat_Ralan=Jasa_Medik_Dokter_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,7).toString());
                                    Jasa_Medik_Petugas_Laborat_Ralan=Jasa_Medik_Petugas_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,8).toString());
                                    Kso_Laborat_Ralan=Kso_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,9).toString());
                                    Persediaan_Laborat_Rawat_Jalan=Persediaan_Laborat_Rawat_Jalan+Double.parseDouble(tbLaborat.getValueAt(i,5).toString());
                                    Jasa_Sarana_Laborat_Ralan=Jasa_Sarana_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,4).toString());
                                    Jasa_Perujuk_Laborat_Ralan=Jasa_Perujuk_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,6).toString());
                                    Jasa_Menejemen_Laborat_Ralan=Jasa_Menejemen_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,10).toString());
                                    tabModeBilling.addRow(new Object[]{
                                        "",tbLaborat.getValueAt(i,2).toString(),":",
                                        Double.parseDouble(tbLaborat.getValueAt(i,3).toString()),1,
                                        Double.parseDouble(tbLaborat.getValueAt(i,3).toString()),"Laborat"
                                    });
                                }
                            }else{
                                Jasa_Medik_Dokter_Laborat_Ralan=Jasa_Medik_Dokter_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,7).toString());
                                Jasa_Medik_Petugas_Laborat_Ralan=Jasa_Medik_Petugas_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,8).toString());
                                Kso_Laborat_Ralan=Kso_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,9).toString());
                                Persediaan_Laborat_Rawat_Jalan=Persediaan_Laborat_Rawat_Jalan+Double.parseDouble(tbLaborat.getValueAt(i,5).toString());
                                Jasa_Sarana_Laborat_Ralan=Jasa_Sarana_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,4).toString());
                                Jasa_Perujuk_Laborat_Ralan=Jasa_Perujuk_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,6).toString());
                                Jasa_Menejemen_Laborat_Ralan=Jasa_Menejemen_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,10).toString());
                                tabModeBilling.addRow(new Object[]{
                                    "",tbLaborat.getValueAt(i,2).toString(),":",
                                    Double.parseDouble(tbLaborat.getValueAt(i,3).toString()),1,
                                    Double.parseDouble(tbLaborat.getValueAt(i,3).toString()),"Laborat"
                                });
                            }                                
                        }
                    }
                    
                    for(i=0;i<tbDetailLaborat.getRowCount();i++){
                        if(tbDetailLaborat.getValueAt(i,0).toString().equals("true")){
                            if(tbDetailLaborat.getValueAt(i,11).toString().equals("0000-00-00")){
                                if(!tbDetailLaborat.getValueAt(i,1).toString().equals("")){
                                    if((!KdDokPerujukLab.getText().equals(""))&&(!TDokterPerujukLab.getText().equals(""))){
                                        Jasa_Medik_Dokter_Laborat_Ralan=Jasa_Medik_Dokter_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,7).toString());
                                        Jasa_Medik_Petugas_Laborat_Ralan=Jasa_Medik_Petugas_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,8).toString());
                                        Kso_Laborat_Ralan=Kso_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,9).toString());
                                        Persediaan_Laborat_Rawat_Jalan=Persediaan_Laborat_Rawat_Jalan+Double.parseDouble(tbDetailLaborat.getValueAt(i,5).toString());
                                        Jasa_Sarana_Laborat_Ralan=Jasa_Sarana_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,4).toString());
                                        Jasa_Perujuk_Laborat_Ralan=Jasa_Perujuk_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,6).toString());
                                        Jasa_Menejemen_Laborat_Ralan=Jasa_Menejemen_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,10).toString());
                                        tabModeBilling.addRow(new Object[]{
                                                "",tbDetailLaborat.getValueAt(i,2).toString().replaceAll("   ",""),":",
                                                Double.parseDouble(tbDetailLaborat.getValueAt(i,3).toString()),1,
                                                Double.parseDouble(tbDetailLaborat.getValueAt(i,3).toString()),"Laborat"
                                        });
                                    }
                                }
                            }else{
                                Jasa_Medik_Dokter_Laborat_Ralan=Jasa_Medik_Dokter_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,7).toString());
                                Jasa_Medik_Petugas_Laborat_Ralan=Jasa_Medik_Petugas_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,8).toString());
                                Kso_Laborat_Ralan=Kso_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,9).toString());
                                Persediaan_Laborat_Rawat_Jalan=Persediaan_Laborat_Rawat_Jalan+Double.parseDouble(tbDetailLaborat.getValueAt(i,5).toString());
                                Jasa_Sarana_Laborat_Ralan=Jasa_Sarana_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,4).toString());
                                Jasa_Perujuk_Laborat_Ralan=Jasa_Perujuk_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,6).toString());
                                Jasa_Menejemen_Laborat_Ralan=Jasa_Menejemen_Laborat_Ralan+Double.parseDouble(tbDetailLaborat.getValueAt(i,10).toString());
                                tabModeBilling.addRow(new Object[]{
                                    "",tbDetailLaborat.getValueAt(i,2).toString(),":",
                                    Double.parseDouble(tbDetailLaborat.getValueAt(i,3).toString()),1,
                                    Double.parseDouble(tbDetailLaborat.getValueAt(i,3).toString()),"Laborat"
                                });
                            }                                
                        }
                    }
                    
                    jml=0;
                    for(i=0;i<tbObat.getRowCount();i++){
                        if(tbObat.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    
                    if(jml>0){
                        tabModeBilling.addRow(new Object[]{"Obat & BHP ",":","",null,null,null,"Obat"});
                    }
                    
                    for(i=0;i<tbObat.getRowCount();i++){
                        if(tbObat.getValueAt(i,0).toString().equals("true")){
                            Obat_Rawat_Jalan=Obat_Rawat_Jalan+Double.parseDouble(tbObat.getValueAt(i,5).toString());
                            subttl=subttl+Double.parseDouble(tbObat.getValueAt(i,4).toString());
                            tabModeBilling.addRow(new Object[]{
                                "",tbObat.getValueAt(i,2).toString(),":",
                                (Double.parseDouble(tbObat.getValueAt(i,4).toString())/Double.parseDouble(tbObat.getValueAt(i,3).toString())),
                                Double.parseDouble(tbObat.getValueAt(i,3).toString()),Double.parseDouble(tbObat.getValueAt(i,4).toString()),"Obat"
                            });                          
                        }
                    }
                    
                    if(subttl>0){
                        if(tampilkan_ppnobat_ralan.equals("Yes")){
                            ppnobat=Valid.roundUp(subttl*0.1,100);
                            tabModeBilling.addRow(new Object[]{
                                "","PPN Obat",":",ppnobat,1,ppnobat,"Obat"
                            });   
                        }                        
                    }
                    
                    isHitung();
                    isKembali();
                }      
	    }catch (Exception e) {
                System.out.println("Notifikasi 4 : "+e);
            } finally{
                if(rsreg != null){
                    rsreg.close();
                }
                if(psreg != null){
                    psreg.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi 5 : "+e);
        }
    }

    
    private void isHitung() {   
        ttl=0;
        y=0;
        ttlLaborat=0;ttlRadiologi=0;ttlObat=0;ttlRalan_Dokter=0;
        ttlRalan_Paramedis=0;ttlRegistrasi=0;ttlRalan_Dokter_Param=0;
        z=tbBilling.getRowCount();
        for(i=0;i<z;i++){ 
            try {                
                y=Double.parseDouble(tabModeBilling.getValueAt(i,5).toString());  
            } catch (Exception e) {
                y=0; 
            }
            switch (tabModeBilling.getValueAt(i,6).toString()) {
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
                case "Registrasi":
                        ttlRegistrasi=ttlRegistrasi+y;
                        break;
            }                                
            ttl=ttl+y;             
        }
        TtlSemua.setText(Valid.SetAngka3(ttl));
    }    
    
    public void isKembali(){
        bayar=0;total=0;ppn=0;besarppn=0;tagihanppn=0;kekurangan=0;y=0;countbayar=0;
        
        z=tabModeAkunBayar.getRowCount();
        for(i=0;i<z;i++){ 
            if(!tabModeAkunBayar.getValueAt(i,2).toString().equals("")){
                countbayar++;
                try {
                    bayar=bayar+Double.parseDouble(tabModeAkunBayar.getValueAt(i,2).toString()); 
                } catch (Exception e) {
                    bayar=bayar+0;
                }               
            }  
            
            if(!tabModeAkunBayar.getValueAt(i,4).toString().equals("")){
                try {
                    besarppn=besarppn+Valid.roundUp(Double.parseDouble(tabModeAkunBayar.getValueAt(i,4).toString()),100); 
                } catch (Exception e) {
                    besarppn=besarppn+0;
                }               
            }   
        }
        
        if(ttl>0) {
            total=ttl; 
        }
        
        tagihanppn=besarppn+total;
        TagihanPPN.setText(Valid.SetAngka3(tagihanppn));
        
        kekurangan=(bayar+besarppn)-tagihanppn;
        jLabel6.setText("Bayar : Rp.");
        if(kekurangan<0){
            jLabel7.setText("Kekurangan : Rp.");
        }else{
            jLabel7.setText("Kembali : Rp.");
        }

        TKembali.setText(Valid.SetAngka3(kekurangan));  
    }

    private void isSimpan() {        
        int jawab=JOptionPane.showConfirmDialog(null, "Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
            if(notaralan.equals("Yes")){
                BtnNotaActionPerformed(null);
            }
            
            try {
                Sequel.AutoComitFalse();
                sukses=true;
                for(i=0;i<tbTindakanDr.getRowCount();i++){
                    if(tbTindakanDr.getValueAt(i,0).toString().equals("true")){
                        if(tbTindakanDr.getValueAt(i,11).toString().equals("0000-00-00")){
                            if((!KdDok.getText().equals(""))&&(!TDokter.getText().equals(""))){
                                if(Sequel.menyimpantf2("rawat_jl_dr","?,?,?,?,?,?,?,?,?,?,?,?","Tindakan dokter",12,new String[]{
                                    TNoRw.getText(),tbTindakanDr.getValueAt(i,1).toString(),KdDok.getText(),
                                    Valid.SetTgl(DTPTgl.getSelectedItem()+""),DTPTgl.getSelectedItem().toString().substring(11,19),
                                    tbTindakanDr.getValueAt(i,5).toString(),tbTindakanDr.getValueAt(i,6).toString(),
                                    tbTindakanDr.getValueAt(i,7).toString(),tbTindakanDr.getValueAt(i,9).toString(),
                                    tbTindakanDr.getValueAt(i,10).toString(),tbTindakanDr.getValueAt(i,4).toString(),"Sudah"
                                  })==true){
                                    tbTindakanDr.setValueAt(false,i,0);
                                }else{
                                    sukses=false;
                                }
                            }                                
                        }else{
                            if(Sequel.mengedittf("rawat_jl_dr","tgl_perawatan=? and jam_rawat=? and no_rawat=? and kd_jenis_prw=?","stts_bayar='Suspen'",4,new String[]{
                                    tbTindakanDr.getValueAt(i,11).toString(),tbTindakanDr.getValueAt(i,12).toString(),TNoRw.getText(),tbTindakanDr.getValueAt(i,1).toString()
                                })==true){
                                tbTindakanDr.setValueAt(false,i,0);
                            }else{
                                sukses=false;
                            }
                        }                            
                    }
                }
                
                for(i=0;i<tbTindakanPr.getRowCount();i++){
                    if(tbTindakanPr.getValueAt(i,0).toString().equals("true")){
                        if(tbTindakanPr.getValueAt(i,11).toString().equals("0000-00-00")){
                            if((!kdptg.getText().equals(""))&&(!TPerawat.getText().equals(""))){
                                if(Sequel.menyimpantf2("rawat_jl_pr","?,?,?,?,?,?,?,?,?,?,?,?","Tindakan paramedis",12,new String[]{
                                    TNoRw.getText(),tbTindakanPr.getValueAt(i,1).toString(),kdptg.getText(),
                                    Valid.SetTgl(DTPTgl.getSelectedItem()+""),DTPTgl.getSelectedItem().toString().substring(11,19),
                                    tbTindakanPr.getValueAt(i,5).toString(),tbTindakanPr.getValueAt(i,6).toString(),
                                    tbTindakanPr.getValueAt(i,8).toString(),tbTindakanPr.getValueAt(i,9).toString(),
                                    tbTindakanPr.getValueAt(i,10).toString(),tbTindakanPr.getValueAt(i,4).toString(),"Sudah"
                                  })==true){
                                    tbTindakanPr.setValueAt(false,i,0);
                                }else{
                                    sukses=false;
                                }
                            }                                
                        }else{
                            if(Sequel.mengedittf("rawat_jl_pr","tgl_perawatan=? and jam_rawat=? and no_rawat=? and kd_jenis_prw=?","stts_bayar='Suspen'",4,new String[]{
                                    tbTindakanPr.getValueAt(i,11).toString(),tbTindakanPr.getValueAt(i,12).toString(),TNoRw.getText(),tbTindakanPr.getValueAt(i,1).toString()
                                })==true){
                                tbTindakanPr.setValueAt(false,i,0);
                            }else{
                                sukses=false;
                            }
                        }                            
                    }
                }
                
                for(i=0;i<tbTindakanDrPr.getRowCount();i++){
                    if(tbTindakanDrPr.getValueAt(i,0).toString().equals("true")){
                        if(tbTindakanDrPr.getValueAt(i,11).toString().equals("0000-00-00")){
                            if((!kdptg2.getText().equals(""))&&(!TPerawat2.getText().equals(""))&&(!KdDok2.getText().equals(""))&&(!TDokter2.getText().equals(""))){
                                if(Sequel.menyimpantf2("rawat_jl_drpr","?,?,?,?,?,?,?,?,?,?,?,?,?,?","Tindakan dokter",14,new String[]{
                                    TNoRw.getText(),tbTindakanDrPr.getValueAt(i,1).toString(),KdDok2.getText(),kdptg2.getText(),
                                    Valid.SetTgl(DTPTgl.getSelectedItem()+""),DTPTgl.getSelectedItem().toString().substring(11,19),
                                    tbTindakanDrPr.getValueAt(i,5).toString(),tbTindakanDrPr.getValueAt(i,6).toString(),
                                    tbTindakanDrPr.getValueAt(i,7).toString(),tbTindakanDrPr.getValueAt(i,8).toString(),
                                    tbTindakanDrPr.getValueAt(i,9).toString(),tbTindakanDrPr.getValueAt(i,10).toString(),
                                    tbTindakanDrPr.getValueAt(i,4).toString(),"Sudah"
                                  })==true){
                                    tbTindakanDrPr.setValueAt(false,i,0);
                                }else{
                                    sukses=false;
                                }
                            }                                
                        }else{
                            if(Sequel.mengedittf("rawat_jl_drpr","tgl_perawatan=? and jam_rawat=? and no_rawat=? and kd_jenis_prw=?","stts_bayar='Suspen'",4,new String[]{
                                    tbTindakanDrPr.getValueAt(i,11).toString(),tbTindakanDrPr.getValueAt(i,12).toString(),TNoRw.getText(),tbTindakanDrPr.getValueAt(i,1).toString()
                                })==true){
                                tbTindakanDrPr.setValueAt(false,i,0);
                            }else{
                                sukses=false;
                            }
                        }                            
                    }
                }
                
                noorderradiologi="";
                if(jmlradiologi>0){
                    noorderradiologi=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(noorder,4),signed)),0) from permintaan_radiologi where tgl_permintaan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' ","PR"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").replaceAll("-",""),4);
                    if((!KdDokPerujukRad.getText().equals(""))&&(!TDokterPerujukRad.getText().equals(""))){
                        if(Sequel.menyimpantf("permintaan_radiologi","?,?,?,?,?,?,?,?,?,'ralan',?,?","No.Permintaan",11,new String[]{
                            noorderradiologi,TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                            DTPTgl.getSelectedItem().toString().substring(11,19), 
                            "0000-00-00","00:00:00","0000-00-00","00:00:00",KdDokPerujukRad.getText(),"",""
                        })==false){
                            sukses=false;
                        }
                    }                        
                }
                
                for(i=0;i<tbRadiologi.getRowCount();i++){
                    if(tbRadiologi.getValueAt(i,0).toString().equals("true")){
                        if(tbRadiologi.getValueAt(i,11).toString().equals("0000-00-00")){
                            if((!KdDokPerujukRad.getText().equals(""))&&(!TDokterPerujukRad.getText().equals(""))){
                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_radiologi","?,?,?","Permintaan Radiologi",3,new String[]{
                                    noorderradiologi,tbRadiologi.getValueAt(i,1).toString(),"Sudah"
                                  })==true){
                                    tbRadiologi.setValueAt(false,i,0);
                                }else{
                                    sukses=false;
                                }
                            }                                
                        }else{
                            if(Sequel.mengedittf("permintaan_pemeriksaan_radiologi","noorder=? and kd_jenis_prw=?","stts_bayar='Sudah'",2,new String[]{
                                    tbRadiologi.getValueAt(i,13).toString(),tbRadiologi.getValueAt(i,1).toString()
                                })==true){
                                tbRadiologi.setValueAt(false,i,0);
                            }else{
                                sukses=false;
                            }
                        }                            
                    }
                }
                
                noorderlaborat="";
                if(jmllaborat>0){
                    noorderlaborat=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(noorder,4),signed)),0) from permintaan_lab where tgl_permintaan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' ","PR"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").replaceAll("-",""),4);
                    if((!KdDokPerujukLab.getText().equals(""))&&(!TDokterPerujukLab.getText().equals(""))){
                        if(Sequel.menyimpantf("permintaan_lab","?,?,?,?,?,?,?,?,?,'ralan',?,?","No.Permintaan",11,new String[]{
                            noorderlaborat,TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                            DTPTgl.getSelectedItem().toString().substring(11,19), 
                            "0000-00-00","00:00:00","0000-00-00","00:00:00",KdDokPerujukLab.getText(),"",""
                        })==false){
                            sukses=false;
                        }
                    }                        
                }
                
                for(i=0;i<tbLaborat.getRowCount();i++){
                    if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                        if(tbLaborat.getValueAt(i,11).toString().equals("0000-00-00")){
                            if((!KdDokPerujukLab.getText().equals(""))&&(!TDokterPerujukLab.getText().equals(""))){
                                if(Sequel.menyimpantf2("permintaan_pemeriksaan_lab","?,?,?","Permintaan Laborat",3,new String[]{
                                    noorderlaborat,tbLaborat.getValueAt(i,1).toString(),"Sudah"
                                  })==true){
                                    tbLaborat.setValueAt(false,i,0);
                                }else{
                                    sukses=false;
                                }
                            }                                
                        }else{
                            if(Sequel.mengedittf("permintaan_pemeriksaan_lab","noorder=? and kd_jenis_prw=?","stts_bayar='Sudah'",2,new String[]{
                                    tbLaborat.getValueAt(i,13).toString(),tbLaborat.getValueAt(i,1).toString()
                                })==true){
                                tbLaborat.setValueAt(false,i,0);
                            }else{
                                sukses=false;
                            }
                        }                            
                    }
                }
                
                for(i=0;i<tbDetailLaborat.getRowCount();i++){
                    if(tbDetailLaborat.getValueAt(i,0).toString().equals("true")){
                        if(tbDetailLaborat.getValueAt(i,11).toString().equals("0000-00-00")){
                            if(!tbDetailLaborat.getValueAt(i,1).toString().equals("")){
                                if((!KdDokPerujukLab.getText().equals(""))&&(!TDokterPerujukLab.getText().equals(""))){
                                    if(Sequel.menyimpantf2("permintaan_detail_permintaan_lab","?,?,?,?","Permintaan Laborat",4,new String[]{
                                        noorderlaborat,tbDetailLaborat.getValueAt(i,14).toString(),tbDetailLaborat.getValueAt(i,1).toString(),"Sudah"
                                      })==true){
                                        tbDetailLaborat.setValueAt(false,i,0);
                                    }else{
                                        sukses=false;
                                    }
                                }    
                            }                                                            
                        }else{
                            if(Sequel.mengedittf("permintaan_detail_permintaan_lab","noorder=? and id_template=?","stts_bayar='Sudah'",2,new String[]{
                                    tbDetailLaborat.getValueAt(i,13).toString(),tbDetailLaborat.getValueAt(i,1).toString()
                                })==true){
                                tbDetailLaborat.setValueAt(false,i,0);
                            }else{
                                sukses=false;
                            }
                        }                            
                    }
                }
                
                for(i=0;i<tbObat.getRowCount();i++){
                    if(tbObat.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("permintaan_obat","?,?,?,?","Obat",4,new String[]{
                            tbObat.getValueAt(i,6).toString(),tbObat.getValueAt(i,7).toString(),TNoRw.getText(),tbObat.getValueAt(i,1).toString(),
                          })==true){
                            tbObat.setValueAt(false,i,0);
                        }else{
                            sukses=false;
                        }                                                    
                    }
                }
                
                if(Sequel.cariInteger("select count(no_rawat) from permintaan_registrasi where no_rawat=?",TNoRw.getText())==0){
                    if(chkPoli.isSelected()==true){
                        if(Sequel.menyimpantf2("permintaan_registrasi","?,?","registrasi",2,new String[]{
                                TNoRw.getText(),TBiaya.getText()
                            })==true){
                            chkPoli.setSelected(false);
                        }else{
                            sukses=false;
                        } 
                    }
                }
            
                if(sukses==true){
                    Sequel.menyimpan2("nota_jalan","?,?,?,?",4,new String[]{
                        TNoRw.getText(),Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_nota,6),signed)),0) from nota_jalan where left(tanggal,7)='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"' ",Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7).replaceAll("-","/")+"/RJ/",6),
                        Valid.SetTgl(DTPTgl.getSelectedItem()+""),DTPTgl.getSelectedItem().toString().substring(11,19)
                    });
                    //simpan billing
                    for(i=8;i<tbBilling.getRowCount();i++){  
                        psbiling=koneksi.prepareStatement(sqlpsbiling);
                        try {
                            psbiling.setInt(1,i);
                            psbiling.setString(2,TNoRw.getText());
                            psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                            psbiling.setString(4,tbBilling.getValueAt(i,0).toString());
                            psbiling.setString(5,tbBilling.getValueAt(i,1).toString().replaceAll("'",""));
                            psbiling.setString(6,tbBilling.getValueAt(i,2).toString());                    
                            try {                        
                                psbiling.setDouble(7,Valid.SetAngka(tbBilling.getValueAt(i,3).toString()));
                            } catch (Exception e) {
                                psbiling.setDouble(7,0);
                            }

                            try {
                                psbiling.setDouble(8,Valid.SetAngka(tbBilling.getValueAt(i,4).toString()));
                            } catch (Exception e) {
                                psbiling.setDouble(8,0);
                            }

                            psbiling.setDouble(9,0);                               

                            try {
                                psbiling.setDouble(10,Valid.SetAngka(tbBilling.getValueAt(i,5).toString())); 
                            } catch (Exception e) {
                                psbiling.setDouble(10,0);
                            }                    
                            psbiling.setString(11,tbBilling.getValueAt(i,6).toString());
                            psbiling.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(psbiling != null){
                                psbiling.close();
                            } 
                        }
                    }

                    Sequel.queryu2("delete from tampjurnal");
                    itembayar=0;besarppn=0;
                    row2=tbAkunBayar.getRowCount();                
                    for(r=0;r<row2;r++){
                        if(Valid.SetAngka(tbAkunBayar.getValueAt(r,2).toString())>0){
                            try {
                                itembayar=Double.parseDouble(tbAkunBayar.getValueAt(r,2).toString()); 
                            } catch (Exception e) {
                                itembayar=0;
                            }    

                            if(!tbAkunBayar.getValueAt(r,4).toString().equals("")){
                                try {
                                    besarppn=Valid.roundUp(Double.parseDouble(tbAkunBayar.getValueAt(r,4).toString()),100); 
                                } catch (Exception e) {
                                    besarppn=0;
                                }               
                            }  

                            if(countbayar>1){
                                if(Sequel.menyimpantf("detail_nota_jalan","?,?,?,?",4,new String[]{
                                        TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(besarppn),Double.toString(itembayar)
                                    },"no_rawat=? and nama_bayar=?","besarppn=besarppn+?,besar_bayar=besar_bayar+?",4,new String[]{
                                        Double.toString(besarppn),Double.toString(itembayar),TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString()
                                    })==true){
                                        Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','"+Double.toString(itembayar)+"','0'","Rekening");                 
                                }else{
                                    sukses=false;
                                }
                            }else if(countbayar==1){
                                if(Sequel.menyimpantf("detail_nota_jalan","?,?,?,?",4,new String[]{
                                        TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(besarppn),Double.toString(total)
                                    },"no_rawat=? and nama_bayar=?","besarppn=besarppn+?,besar_bayar=besar_bayar+?",4,new String[]{
                                        Double.toString(besarppn),Double.toString(total),TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString()
                                    })==true){
                                        Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','"+Double.toString(total)+"','0'","Rekening");                 
                                }                                                                
                            }else{
                                sukses=false;
                            }                        
                        }  
                    }
                }
                    

                if(sukses==true){
                    if((ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis-Suspen_Tindakan_Ralan)>0){
                        Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Tindakan Ralan','0','"+(ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis-Suspen_Tindakan_Ralan)+"'","kredit=kredit+'"+(ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis-Suspen_Tindakan_Ralan)+"'","kd_rek='"+Tindakan_Ralan+"'");    
                    }

                    if((ttlLaborat)>0){
                        Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','Laborat Ralan','0','"+(ttlLaborat)+"'","kredit=kredit+'"+(ttlLaborat)+"'","kd_rek='"+Laborat_Ralan+"'");    
                    }

                    if((ttlRadiologi)>0){
                        Sequel.menyimpan("tampjurnal","'"+Radiologi_Ralan+"','Radiologi Ralan','0','"+(ttlRadiologi)+"'","kredit=kredit+'"+(ttlRadiologi)+"'","kd_rek='"+Radiologi_Ralan+"'");    
                    }

                    if(ttlObat>0){
                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Obat_Ralan+"','Obat Ralan','0','"+ttlObat+"'","kredit=kredit+'"+(ttlObat)+"'","kd_rek='"+Suspen_Piutang_Obat_Ralan+"'");    
                    }

                    if(Suspen_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Obat Ralan','0','"+Suspen_Tindakan_Ralan+"'","kredit=kredit+'"+(Suspen_Tindakan_Ralan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");    
                    }

                    if(ttlRegistrasi>0){
                        Sequel.menyimpan("tampjurnal","'"+Registrasi_Ralan+"','Registrasi Ralan','0','"+ttlRegistrasi+"'","kredit=kredit+'"+(ttlRegistrasi)+"'","kd_rek='"+Registrasi_Ralan+"'");    
                    }

                    if(Jasa_Medik_Dokter_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Operasi Ralan','"+Jasa_Medik_Dokter_Tindakan_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Dokter_Tindakan_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Dokter_Tindakan_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Dokter_Tindakan_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");  
                    }

                    if(Jasa_Medik_Paramedis_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Operasi Ralan','"+Jasa_Medik_Paramedis_Tindakan_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Paramedis_Tindakan_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Paramedis_Tindakan_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Paramedis_Tindakan_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");  
                    }

                    if(KSO_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Operasi Ralan','"+KSO_Tindakan_Ralan+"','0'","debet=debet+'"+(KSO_Tindakan_Ralan)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Operasi Ralan','0','"+KSO_Tindakan_Ralan+"'","kredit=kredit+'"+(KSO_Tindakan_Ralan)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");  
                    }

                    if(Jasa_Sarana_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Operasi Ralan','"+Jasa_Sarana_Tindakan_Ralan+"','0'","debet=debet+'"+Jasa_Sarana_Tindakan_Ralan+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Operasi Ralan','0','"+Jasa_Sarana_Tindakan_Ralan+"'","kredit=kredit+'"+Jasa_Sarana_Tindakan_Ralan+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");  
                    }

                    if(BHP_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','Operasi Ralan','"+BHP_Tindakan_Ralan+"','0'","debet=debet+'"+BHP_Tindakan_Ralan+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Operasi Ralan','0','"+BHP_Tindakan_Ralan+"'","kredit=kredit+'"+BHP_Tindakan_Ralan+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");  
                    }

                    if(Jasa_Menejemen_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Operasi Ralan','"+Jasa_Menejemen_Tindakan_Ralan+"','0'","debet=debet+'"+Jasa_Menejemen_Tindakan_Ralan+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Operasi Ralan','0','"+Jasa_Menejemen_Tindakan_Ralan+"'","kredit=kredit+'"+Jasa_Menejemen_Tindakan_Ralan+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");  
                    }

                    if(Jasa_Medik_Dokter_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Medik_Dokter_Laborat_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Dokter_Laborat_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Dokter_Laborat_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Dokter_Laborat_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'");  
                    }

                    if(Jasa_Medik_Petugas_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Medik_Petugas_Laborat_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Petugas_Laborat_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Petugas_Laborat_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Petugas_Laborat_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"'");  
                    }

                    if(Kso_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ralan+"','Operasi Ralan','"+Kso_Laborat_Ralan+"','0'","debet=debet+'"+(Kso_Laborat_Ralan)+"'","kd_rek='"+Beban_Kso_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ralan+"','Operasi Ralan','0','"+Kso_Laborat_Ralan+"'","kredit=kredit+'"+(Kso_Laborat_Ralan)+"'","kd_rek='"+Utang_Kso_Laborat_Ralan+"'");  
                    }

                    if(Persediaan_Laborat_Rawat_Jalan>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_Jalan+"','Operasi Ralan','"+Persediaan_Laborat_Rawat_Jalan+"','0'","debet=debet+'"+(Persediaan_Laborat_Rawat_Jalan)+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_Jalan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Laborat_Rawat_Jalan+"','Operasi Ralan','0','"+Persediaan_Laborat_Rawat_Jalan+"'","kredit=kredit+'"+(Persediaan_Laborat_Rawat_Jalan)+"'","kd_rek='"+Persediaan_BHP_Laborat_Rawat_Jalan+"'");  
                    }

                    if(Jasa_Sarana_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Sarana_Laborat_Ralan+"','0'","debet=debet+'"+Jasa_Sarana_Laborat_Ralan+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Sarana_Laborat_Ralan+"'","kredit=kredit+'"+Jasa_Sarana_Laborat_Ralan+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ralan+"'");  
                    }

                    if(Jasa_Perujuk_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Perujuk_Laborat_Ralan+"','0'","debet=debet+'"+Jasa_Perujuk_Laborat_Ralan+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Perujuk_Laborat_Ralan+"'","kredit=kredit+'"+Jasa_Perujuk_Laborat_Ralan+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ralan+"'");  
                    }

                    if(Jasa_Menejemen_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Menejemen_Laborat_Ralan+"','0'","debet=debet+'"+Jasa_Menejemen_Laborat_Ralan+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Menejemen_Laborat_Ralan+"'","kredit=kredit+'"+Jasa_Menejemen_Laborat_Ralan+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ralan+"'");  
                    }

                    if(Jasa_Medik_Dokter_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Medik_Dokter_Radiologi_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Dokter_Radiologi_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Dokter_Radiologi_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Dokter_Radiologi_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'");  
                    }

                    if(Jasa_Medik_Petugas_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Medik_Petugas_Radiologi_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Petugas_Radiologi_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Petugas_Radiologi_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Petugas_Radiologi_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"'");  
                    }

                    if(Kso_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ralan+"','Operasi Ralan','"+Kso_Radiologi_Ralan+"','0'","debet=debet+'"+(Kso_Radiologi_Ralan)+"'","kd_rek='"+Beban_Kso_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ralan+"','Operasi Ralan','0','"+Kso_Radiologi_Ralan+"'","kredit=kredit+'"+(Kso_Radiologi_Ralan)+"'","kd_rek='"+Utang_Kso_Radiologi_Ralan+"'");  
                    }

                    if(Persediaan_Radiologi_Rawat_Jalan>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Radiologi_Rawat_Jalan+"','Operasi Ralan','"+Persediaan_Radiologi_Rawat_Jalan+"','0'","debet=debet+'"+(Persediaan_Radiologi_Rawat_Jalan)+"'","kd_rek='"+HPP_Persediaan_Radiologi_Rawat_Jalan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Radiologi_Rawat_Jalan+"','Operasi Ralan','0','"+Persediaan_Radiologi_Rawat_Jalan+"'","kredit=kredit+'"+(Persediaan_Radiologi_Rawat_Jalan)+"'","kd_rek='"+Persediaan_BHP_Radiologi_Rawat_Jalan+"'");  
                    }

                    if(Jasa_Sarana_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Sarana_Radiologi_Ralan+"','0'","debet=debet+'"+Jasa_Sarana_Radiologi_Ralan+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Sarana_Radiologi_Ralan+"'","kredit=kredit+'"+Jasa_Sarana_Radiologi_Ralan+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ralan+"'");  
                    }

                    if(Jasa_Perujuk_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Perujuk_Radiologi_Ralan+"','0'","debet=debet+'"+Jasa_Perujuk_Radiologi_Ralan+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Perujuk_Radiologi_Ralan+"'","kredit=kredit+'"+Jasa_Perujuk_Radiologi_Ralan+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ralan+"'");  
                    }

                    if(Jasa_Menejemen_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Menejemen_Radiologi_Ralan+"','0'","debet=debet+'"+Jasa_Menejemen_Radiologi_Ralan+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Menejemen_Radiologi_Ralan+"'","kredit=kredit+'"+Jasa_Menejemen_Radiologi_Ralan+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ralan+"'");  
                    }

                    if(Obat_Rawat_Jalan>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Rawat_Jalan+"','Operasi Ralan','"+Obat_Rawat_Jalan+"','0'","debet=debet+'"+(Obat_Rawat_Jalan)+"'","kd_rek='"+HPP_Obat_Rawat_Jalan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Rawat_Jalan+"','Operasi Ralan','0','"+Obat_Rawat_Jalan+"'","kredit=kredit+'"+(Obat_Rawat_Jalan)+"'","kd_rek='"+Persediaan_Obat_Rawat_Jalan+"'");  
                    }

                    sukses=jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBAYARAN PASIEN RAWAT JALAN, DIPOSTING OLEH "+akses.getkode());
                }
                    
                if(sukses==true){
                    String alamat=Sequel.cariIsi("select almt_pj from reg_periksa where no_rawat=? ",TNoRw.getText());
                    Sequel.menyimpan("tagihan_sadewa","'"+TNoRw.getText()+"','"+TNoRM.getText()+"','"+TPasien.getText().replaceAll("'","")+"','"+alamat.replaceAll("'","")+"',concat('"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+
                                    "',' ',CURTIME()),'Pelunasan','"+total+"','"+total+"','Sudah','"+akses.getkode()+"'","jumlah_tagihan=jumlah_tagihan+'"+total+"',jumlah_bayar=jumlah_bayar+'"+total+"'","no_nota='"+TNoRw.getText()+"'");
                    Valid.editTable(tabModeBilling,"reg_periksa","no_rawat",TNoRw,"status_bayar='Sudah Bayar'");
                    Sequel.Commit();
                    for(r=0;r<row2;r++){
                        tbAkunBayar.setValueAt("",r,2);
                        tbAkunBayar.setValueAt("",r,4);
                    }
                    JOptionPane.showMessageDialog(null,"Proses simpan selesai...!"); 
                    Valid.tabelKosong(TabModeDetailLaborat);
                    tampilbilling();
                }else{
                    sukses=false;
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
            }catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);            
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Data yang sama dimasukkan sebelumnya...!");
            }               
        } 
    }
    
    private void tampilhapus() {
        statushapus=true;
        Jasa_Medik_Dokter_Tindakan_Ralan=0;
        Jasa_Medik_Paramedis_Tindakan_Ralan=0;
        KSO_Tindakan_Ralan=0;
        Jasa_Sarana_Tindakan_Ralan=0;
        BHP_Tindakan_Ralan=0;
        Jasa_Menejemen_Tindakan_Ralan=0;
        Jasa_Medik_Dokter_Laborat_Ralan=0;
        Jasa_Medik_Petugas_Laborat_Ralan=0;
        Kso_Laborat_Ralan=0;
        Persediaan_Laborat_Rawat_Jalan=0;
        Jasa_Sarana_Laborat_Ralan=0;
        Jasa_Perujuk_Laborat_Ralan=0;
        Jasa_Menejemen_Laborat_Ralan=0;
        Jasa_Medik_Dokter_Radiologi_Ralan=0;
        Jasa_Medik_Petugas_Radiologi_Ralan=0;
        Kso_Radiologi_Ralan=0;
        Persediaan_Radiologi_Rawat_Jalan=0;
        Jasa_Sarana_Radiologi_Ralan=0;
        Jasa_Perujuk_Radiologi_Ralan=0;
        Jasa_Menejemen_Radiologi_Ralan=0;
        Obat_Rawat_Jalan=0;
        Suspen_Tindakan_Ralan=0;

        for(i=0;i<tbTindakanDrBayar.getRowCount();i++){
            if(tbTindakanDrBayar.getValueAt(i,0).toString().equals("true")){
                if(tbTindakanDrBayar.getValueAt(i,13).toString().equals("Suspen")){
                    Suspen_Tindakan_Ralan=Suspen_Tindakan_Ralan+Double.parseDouble(tbTindakanDrBayar.getValueAt(i,4).toString());
                }else{
                    Jasa_Medik_Dokter_Tindakan_Ralan=Jasa_Medik_Dokter_Tindakan_Ralan+Double.parseDouble(tbTindakanDrBayar.getValueAt(i,7).toString());
                    KSO_Tindakan_Ralan=KSO_Tindakan_Ralan+Double.parseDouble(tbTindakanDrBayar.getValueAt(i,9).toString());
                    Jasa_Sarana_Tindakan_Ralan=Jasa_Sarana_Tindakan_Ralan+Double.parseDouble(tbTindakanDrBayar.getValueAt(i,5).toString());
                    BHP_Tindakan_Ralan=BHP_Tindakan_Ralan+Double.parseDouble(tbTindakanDrBayar.getValueAt(i,6).toString());
                    Jasa_Menejemen_Tindakan_Ralan=Jasa_Menejemen_Tindakan_Ralan+Double.parseDouble(tbTindakanDrBayar.getValueAt(i,10).toString());
                }
            }
        }
        
        for(i=0;i<tbTindakanPrBayar.getRowCount();i++){
            if(tbTindakanPrBayar.getValueAt(i,0).toString().equals("true")){
                if(tbTindakanDrBayar.getValueAt(i,13).toString().equals("Suspen")){
                    Suspen_Tindakan_Ralan=Suspen_Tindakan_Ralan+Double.parseDouble(tbTindakanPrBayar.getValueAt(i,4).toString());
                }else{
                    Jasa_Medik_Paramedis_Tindakan_Ralan=Jasa_Medik_Paramedis_Tindakan_Ralan+Double.parseDouble(tbTindakanPrBayar.getValueAt(i,8).toString());
                    KSO_Tindakan_Ralan=KSO_Tindakan_Ralan+Double.parseDouble(tbTindakanPrBayar.getValueAt(i,9).toString());
                    Jasa_Sarana_Tindakan_Ralan=Jasa_Sarana_Tindakan_Ralan+Double.parseDouble(tbTindakanPrBayar.getValueAt(i,5).toString());
                    BHP_Tindakan_Ralan=BHP_Tindakan_Ralan+Double.parseDouble(tbTindakanPrBayar.getValueAt(i,6).toString());
                    Jasa_Menejemen_Tindakan_Ralan=Jasa_Menejemen_Tindakan_Ralan+Double.parseDouble(tbTindakanPrBayar.getValueAt(i,10).toString());
                }
            }
        }
        
        for(i=0;i<tbTindakanDrPrBayar.getRowCount();i++){
            if(tbTindakanDrPrBayar.getValueAt(i,0).toString().equals("true")){
                if(tbTindakanDrBayar.getValueAt(i,13).toString().equals("Suspen")){
                    Suspen_Tindakan_Ralan=Suspen_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPrBayar.getValueAt(i,4).toString());
                }else{
                    Jasa_Medik_Dokter_Tindakan_Ralan=Jasa_Medik_Dokter_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPrBayar.getValueAt(i,7).toString());
                    Jasa_Medik_Paramedis_Tindakan_Ralan=Jasa_Medik_Paramedis_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPrBayar.getValueAt(i,8).toString());
                    KSO_Tindakan_Ralan=KSO_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPrBayar.getValueAt(i,9).toString());
                    Jasa_Sarana_Tindakan_Ralan=Jasa_Sarana_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPrBayar.getValueAt(i,5).toString());
                    BHP_Tindakan_Ralan=BHP_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPrBayar.getValueAt(i,6).toString());
                    Jasa_Menejemen_Tindakan_Ralan=Jasa_Menejemen_Tindakan_Ralan+Double.parseDouble(tbTindakanDrPrBayar.getValueAt(i,10).toString());
                }
            }
        }
        
        for(i=0;i<tbRadiologiBayar.getRowCount();i++){
            if(tbRadiologiBayar.getValueAt(i,0).toString().equals("true")){
                Jasa_Medik_Dokter_Radiologi_Ralan=Jasa_Medik_Dokter_Radiologi_Ralan+Double.parseDouble(tbRadiologiBayar.getValueAt(i,7).toString());
                Jasa_Medik_Petugas_Radiologi_Ralan=Jasa_Medik_Petugas_Radiologi_Ralan+Double.parseDouble(tbRadiologiBayar.getValueAt(i,8).toString());
                Kso_Radiologi_Ralan=Kso_Radiologi_Ralan+Double.parseDouble(tbRadiologiBayar.getValueAt(i,9).toString());
                Persediaan_Radiologi_Rawat_Jalan=Persediaan_Radiologi_Rawat_Jalan+Double.parseDouble(tbRadiologiBayar.getValueAt(i,5).toString());
                Jasa_Sarana_Radiologi_Ralan=Jasa_Sarana_Radiologi_Ralan+Double.parseDouble(tbRadiologiBayar.getValueAt(i,4).toString());
                Jasa_Perujuk_Radiologi_Ralan=Jasa_Perujuk_Radiologi_Ralan+Double.parseDouble(tbRadiologiBayar.getValueAt(i,6).toString());
                Jasa_Menejemen_Radiologi_Ralan=Jasa_Menejemen_Radiologi_Ralan+Double.parseDouble(tbRadiologiBayar.getValueAt(i,10).toString());
            }
        }
        
        for(i=0;i<tbLaboratBayar.getRowCount();i++){
            if(tbLaboratBayar.getValueAt(i,0).toString().equals("true")){
                Jasa_Medik_Dokter_Laborat_Ralan=Jasa_Medik_Dokter_Laborat_Ralan+Double.parseDouble(tbLaboratBayar.getValueAt(i,7).toString());
                Jasa_Medik_Petugas_Laborat_Ralan=Jasa_Medik_Petugas_Laborat_Ralan+Double.parseDouble(tbLaboratBayar.getValueAt(i,8).toString());
                Kso_Laborat_Ralan=Kso_Laborat_Ralan+Double.parseDouble(tbLaboratBayar.getValueAt(i,9).toString());
                Persediaan_Laborat_Rawat_Jalan=Persediaan_Laborat_Rawat_Jalan+Double.parseDouble(tbLaboratBayar.getValueAt(i,5).toString());
                Jasa_Sarana_Laborat_Ralan=Jasa_Sarana_Laborat_Ralan+Double.parseDouble(tbLaboratBayar.getValueAt(i,4).toString());
                Jasa_Perujuk_Laborat_Ralan=Jasa_Perujuk_Laborat_Ralan+Double.parseDouble(tbLaboratBayar.getValueAt(i,6).toString());
                Jasa_Menejemen_Laborat_Ralan=Jasa_Menejemen_Laborat_Ralan+Double.parseDouble(tbLaboratBayar.getValueAt(i,10).toString());
            }
        }
        
        for(i=0;i<tbDetailLaboratBayar.getRowCount();i++){
            if(tbDetailLaboratBayar.getValueAt(i,0).toString().equals("true")){
                Jasa_Medik_Dokter_Laborat_Ralan=Jasa_Medik_Dokter_Laborat_Ralan+Double.parseDouble(tbDetailLaboratBayar.getValueAt(i,7).toString());
                Jasa_Medik_Petugas_Laborat_Ralan=Jasa_Medik_Petugas_Laborat_Ralan+Double.parseDouble(tbDetailLaboratBayar.getValueAt(i,8).toString());
                Kso_Laborat_Ralan=Kso_Laborat_Ralan+Double.parseDouble(tbDetailLaboratBayar.getValueAt(i,9).toString());
                Persediaan_Laborat_Rawat_Jalan=Persediaan_Laborat_Rawat_Jalan+Double.parseDouble(tbDetailLaboratBayar.getValueAt(i,5).toString());
                Jasa_Sarana_Laborat_Ralan=Jasa_Sarana_Laborat_Ralan+Double.parseDouble(tbDetailLaboratBayar.getValueAt(i,4).toString());
                Jasa_Perujuk_Laborat_Ralan=Jasa_Perujuk_Laborat_Ralan+Double.parseDouble(tbDetailLaboratBayar.getValueAt(i,6).toString());
                Jasa_Menejemen_Laborat_Ralan=Jasa_Menejemen_Laborat_Ralan+Double.parseDouble(tbDetailLaboratBayar.getValueAt(i,10).toString());
            }
        }
        
        for(i=0;i<tbObatBayar.getRowCount();i++){
            if(tbObatBayar.getValueAt(i,0).toString().equals("true")){
                Obat_Rawat_Jalan=Obat_Rawat_Jalan+Double.parseDouble(tbObatBayar.getValueAt(i,5).toString());
            }
        }

        isHitungHapus();
    }
    
    private void isHitungHapus() {   
        ttl=0;
        ttlLaborat=0;ttlRadiologi=0;ttlObat=0;ttlRalan_Dokter=0;
        ttlRalan_Paramedis=0;ttlRegistrasi=0;ttlRalan_Dokter_Param=0;
        ppnobat=0;
        if(jmlreg==1){
            if(chkPoli.isSelected()==true){
                ttlRegistrasi=ttlRegistrasi+Valid.SetAngka(TBiaya.getText());                                  
                ttl=ttl+Valid.SetAngka(TBiaya.getText()); 
            }else{
                ttlRegistrasi=ttlRegistrasi+0;                                  
                ttl=ttl+0; 
            }                            
        }
        z=tbTindakanDrBayar.getRowCount();
        for(i=0;i<z;i++){ 
            if(tbTindakanDrBayar.getValueAt(i,0).toString().equals("true")){
                y=0;        
                try {                
                    y=Double.parseDouble(tbTindakanDrBayar.getValueAt(i,4).toString());  
                } catch (Exception e) {
                    y=0; 
                }
                ttlRalan_Dokter=ttlRalan_Dokter+y;                                  
                ttl=ttl+y;  
            }                           
        }  
        z=tbTindakanPrBayar.getRowCount();
        for(i=0;i<z;i++){ 
            if(tbTindakanPrBayar.getValueAt(i,0).toString().equals("true")){
                y=0;
                try {                
                    y=Double.parseDouble(tbTindakanPrBayar.getValueAt(i,4).toString());  
                } catch (Exception e) {
                    y=0; 
                }
                ttlRalan_Paramedis=ttlRalan_Paramedis+y;                                  
                ttl=ttl+y;  
            }                           
        } 
        z=tbTindakanDrPrBayar.getRowCount();
        for(i=0;i<z;i++){ 
            if(tbTindakanDrPrBayar.getValueAt(i,0).toString().equals("true")){
                y=0;
                try {                
                    y=Double.parseDouble(tbTindakanDrPrBayar.getValueAt(i,4).toString());  
                } catch (Exception e) {
                    y=0; 
                }
                ttlRalan_Dokter_Param=ttlRalan_Dokter_Param+y;                                  
                ttl=ttl+y;  
            }                           
        } 
        z=tbRadiologiBayar.getRowCount();
        for(i=0;i<z;i++){ 
            if(tbRadiologiBayar.getValueAt(i,0).toString().equals("true")){
                y=0;
                try {                
                    y=Double.parseDouble(tbRadiologiBayar.getValueAt(i,3).toString());  
                } catch (Exception e) {
                    y=0; 
                }
                ttlRadiologi=ttlRadiologi+y;                                  
                ttl=ttl+y;  
            }                           
        }         
        z=tbLaboratBayar.getRowCount();
        for(i=0;i<z;i++){ 
            if(tbLaboratBayar.getValueAt(i,0).toString().equals("true")){
                y=0;
                try {                
                    y=Double.parseDouble(tbLaboratBayar.getValueAt(i,3).toString());  
                } catch (Exception e) {
                    y=0; 
                }
                ttlLaborat=ttlLaborat+y;                                  
                ttl=ttl+y;  
            }                           
        }         
        z=tbDetailLaboratBayar.getRowCount();
        for(i=0;i<z;i++){ 
            if(tbDetailLaboratBayar.getValueAt(i,0).toString().equals("true")){
                y=0;
                try {                
                    y=Double.parseDouble(tbDetailLaboratBayar.getValueAt(i,3).toString());  
                } catch (Exception e) {
                    y=0; 
                }
                ttlLaborat=ttlLaborat+y;                                  
                ttl=ttl+y;  
            }                           
        }         
        z=tbObatBayar.getRowCount();
        for(i=0;i<z;i++){ 
            if(tbObatBayar.getValueAt(i,0).toString().equals("true")){
                y=0;
                try {                
                    y=Double.parseDouble(tbObatBayar.getValueAt(i,4).toString());  
                } catch (Exception e) {
                    y=0; 
                }
                ttlObat=ttlObat+y;                                  
                ttl=ttl+y;  
            }                           
        } 
        if(ttlObat>0){
            if(tampilkan_ppnobat_ralan.equals("Yes")){
                ppnobat=Valid.roundUp(ttlObat*0.1,100); 
                ttlObat=ttlObat+ppnobat;
                ttl=ttl+ppnobat;
            }                        
        }
        
        TtlSemua.setText(Valid.SetAngka3(ttl));
        if(ttl<=0){
            statushapus=false;
        }
        tampilAkunBayar();
        isKembali();
    }   
    
    private void isSimpanHapus() {
        int jawab=JOptionPane.showConfirmDialog(null, "Eeiiiiiits, udah bener belum data yang mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
            try {
                Sequel.AutoComitFalse();
                sukses=true;
                if(jmlreg==1){
                    if(chkPoli.isSelected()==true){
                        if(Sequel.queryu2tf("delete from permintaan_registrasi where no_rawat=?",1,new String[]{TNoRw.getText()})==true){
                            try {
                                psbiling=koneksi.prepareStatement(sqlpsbiling);
                                try {
                                    psbiling.setInt(1,i);
                                    psbiling.setString(2,TNoRw.getText());
                                    psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                    psbiling.setString(4,"Registrasi");
                                    psbiling.setString(5,"Pembatalan Registrasi");
                                    psbiling.setString(6,":");                    
                                    try {                        
                                        psbiling.setDouble(7,-Valid.SetAngka(TBiaya.getText()));
                                    } catch (Exception e) {
                                        psbiling.setDouble(7,0);
                                    }
                                    try {
                                        psbiling.setDouble(8,1);
                                    } catch (Exception e) {
                                        psbiling.setDouble(8,0);
                                    }
                                    psbiling.setDouble(9,0); 
                                    try {
                                        psbiling.setDouble(10,-Valid.SetAngka(TBiaya.getText())); 
                                    } catch (Exception e) {
                                        psbiling.setDouble(10,0);
                                    }                    
                                    psbiling.setString(11,"Registrasi");
                                    psbiling.executeUpdate();
                                } catch (Exception e) {
                                    sukses=false;
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(psbiling != null){
                                        psbiling.close();
                                    } 
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            }
                        }else{
                            sukses=false;
                        }                            
                    }                            
                }
                
                for(i=0;i<tbTindakanDrBayar.getRowCount();i++){
                    if(tbTindakanDrBayar.getValueAt(i,0).toString().equals("true")){
                        if(tbTindakanDrBayar.getValueAt(i,13).toString().equals("Suspen")){
                            if(Sequel.queryu2tf("update rawat_jl_dr set stts_bayar='Belum' where tgl_perawatan=? and jam_rawat=? and no_rawat=? and kd_jenis_prw=?",4,new String[]{
                                tbTindakanDrBayar.getValueAt(i,11).toString(),tbTindakanDrBayar.getValueAt(i,12).toString(),TNoRw.getText(),tbTindakanDrBayar.getValueAt(i,1).toString()
                              })==true){
                                try {
                                    psbiling=koneksi.prepareStatement(sqlpsbiling);
                                    try {
                                        psbiling.setInt(1,i);
                                        psbiling.setString(2,TNoRw.getText());
                                        psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                        psbiling.setString(4,"Tindakan");
                                        psbiling.setString(5,": Pembatalan "+tbTindakanDrBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                        psbiling.setString(6,":");                    
                                        try {                        
                                            psbiling.setDouble(7,-Valid.SetAngka(tbTindakanDrBayar.getValueAt(i,4).toString()));
                                        } catch (Exception e) {
                                            psbiling.setDouble(7,0);
                                        }
                                        try {
                                            psbiling.setDouble(8,1);
                                        } catch (Exception e) {
                                            psbiling.setDouble(8,0);
                                        }
                                        psbiling.setDouble(9,0); 
                                        try {
                                            psbiling.setDouble(10,-Valid.SetAngka(tbTindakanDrBayar.getValueAt(i,4).toString())); 
                                        } catch (Exception e) {
                                            psbiling.setDouble(10,0);
                                        }                    
                                        psbiling.setString(11,"Ralan Dokter");
                                        psbiling.executeUpdate();
                                    } catch (Exception e) {
                                        sukses=false;
                                        System.out.println("Notifikasi : "+e);
                                    } finally{
                                        if(psbiling != null){
                                            psbiling.close();
                                        } 
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                }
                            }else{
                                sukses=false;
                            }
                        }else{
                            if(Sequel.queryu2tf("delete from rawat_jl_dr where tgl_perawatan=? and jam_rawat=? and no_rawat=? and kd_jenis_prw=?",4,new String[]{
                                tbTindakanDrBayar.getValueAt(i,11).toString(),tbTindakanDrBayar.getValueAt(i,12).toString(),TNoRw.getText(),tbTindakanDrBayar.getValueAt(i,1).toString()
                              })==true){
                                try {
                                    psbiling=koneksi.prepareStatement(sqlpsbiling);
                                    try {
                                        psbiling.setInt(1,i);
                                        psbiling.setString(2,TNoRw.getText());
                                        psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                        psbiling.setString(4,"Tindakan");
                                        psbiling.setString(5,": Pembatalan "+tbTindakanDrBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                        psbiling.setString(6,":");                    
                                        try {                        
                                            psbiling.setDouble(7,-Valid.SetAngka(tbTindakanDrBayar.getValueAt(i,4).toString()));
                                        } catch (Exception e) {
                                            psbiling.setDouble(7,0);
                                        }
                                        try {
                                            psbiling.setDouble(8,1);
                                        } catch (Exception e) {
                                            psbiling.setDouble(8,0);
                                        }
                                        psbiling.setDouble(9,0); 
                                        try {
                                            psbiling.setDouble(10,-Valid.SetAngka(tbTindakanDrBayar.getValueAt(i,4).toString())); 
                                        } catch (Exception e) {
                                            psbiling.setDouble(10,0);
                                        }                    
                                        psbiling.setString(11,"Ralan Dokter");
                                        psbiling.executeUpdate();
                                    } catch (Exception e) {
                                        sukses=false;
                                        System.out.println("Notifikasi : "+e);
                                    } finally{
                                        if(psbiling != null){
                                            psbiling.close();
                                        } 
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                }
                            }else{
                                sukses=false;
                            }
                        }
                    }                        
                }
                
                for(i=0;i<tbTindakanPrBayar.getRowCount();i++){
                    if(tbTindakanPrBayar.getValueAt(i,0).toString().equals("true")){
                        if(tbTindakanPrBayar.getValueAt(i,13).toString().equals("Suspen")){
                            if(Sequel.queryu2tf("update rawat_jl_pr set stts_bayar='Belum' where tgl_perawatan=? and jam_rawat=? and no_rawat=? and kd_jenis_prw=?",4,new String[]{
                                tbTindakanPrBayar.getValueAt(i,11).toString(),tbTindakanPrBayar.getValueAt(i,12).toString(),TNoRw.getText(),tbTindakanPrBayar.getValueAt(i,1).toString()
                              })==true){
                                try {
                                    psbiling=koneksi.prepareStatement(sqlpsbiling);
                                    try {
                                        psbiling.setInt(1,i);
                                        psbiling.setString(2,TNoRw.getText());
                                        psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                        psbiling.setString(4,"Tindakan");
                                        psbiling.setString(5,": Pembatalan "+tbTindakanPrBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                        psbiling.setString(6,":");                    
                                        try {                        
                                            psbiling.setDouble(7,-Valid.SetAngka(tbTindakanPrBayar.getValueAt(i,4).toString()));
                                        } catch (Exception e) {
                                            psbiling.setDouble(7,0);
                                        }
                                        try {
                                            psbiling.setDouble(8,1);
                                        } catch (Exception e) {
                                            psbiling.setDouble(8,0);
                                        }
                                        psbiling.setDouble(9,0); 
                                        try {
                                            psbiling.setDouble(10,-Valid.SetAngka(tbTindakanPrBayar.getValueAt(i,4).toString())); 
                                        } catch (Exception e) {
                                            psbiling.setDouble(10,0);
                                        }                    
                                        psbiling.setString(11,"Ralan Paramedis");
                                        psbiling.executeUpdate();
                                    } catch (Exception e) {
                                        sukses=false;
                                        System.out.println("Notifikasi : "+e);
                                    } finally{
                                        if(psbiling != null){
                                            psbiling.close();
                                        } 
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                }
                            }else{
                                sukses=false;
                            }
                        }else{
                            if(Sequel.queryu2tf("delete from rawat_jl_pr where tgl_perawatan=? and jam_rawat=? and no_rawat=? and kd_jenis_prw=?",4,new String[]{
                                tbTindakanPrBayar.getValueAt(i,11).toString(),tbTindakanPrBayar.getValueAt(i,12).toString(),TNoRw.getText(),tbTindakanPrBayar.getValueAt(i,1).toString()
                              })==true){
                                try {
                                    psbiling=koneksi.prepareStatement(sqlpsbiling);
                                    try {
                                        psbiling.setInt(1,i);
                                        psbiling.setString(2,TNoRw.getText());
                                        psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                        psbiling.setString(4,"Tindakan");
                                        psbiling.setString(5,": Pembatalan "+tbTindakanPrBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                        psbiling.setString(6,":");                    
                                        try {                        
                                            psbiling.setDouble(7,-Valid.SetAngka(tbTindakanPrBayar.getValueAt(i,4).toString()));
                                        } catch (Exception e) {
                                            psbiling.setDouble(7,0);
                                        }
                                        try {
                                            psbiling.setDouble(8,1);
                                        } catch (Exception e) {
                                            psbiling.setDouble(8,0);
                                        }
                                        psbiling.setDouble(9,0); 
                                        try {
                                            psbiling.setDouble(10,-Valid.SetAngka(tbTindakanPrBayar.getValueAt(i,4).toString())); 
                                        } catch (Exception e) {
                                            psbiling.setDouble(10,0);
                                        }                    
                                        psbiling.setString(11,"Ralan Paramedis");
                                        psbiling.executeUpdate();
                                    } catch (Exception e) {
                                        sukses=false;
                                        System.out.println("Notifikasi : "+e);
                                    } finally{
                                        if(psbiling != null){
                                            psbiling.close();
                                        } 
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                }
                            }else{
                                sukses=false;
                            }
                        }
                    }                        
                }
                
                for(i=0;i<tbTindakanDrPrBayar.getRowCount();i++){
                    if(tbTindakanDrPrBayar.getValueAt(i,0).toString().equals("true")){
                        if(tbTindakanDrPrBayar.getValueAt(i,13).toString().equals("Suspen")){
                            if(Sequel.queryu2tf("update rawat_jl_drpr set stts_bayar='Belum' where tgl_perawatan=? and jam_rawat=? and no_rawat=? and kd_jenis_prw=?",4,new String[]{
                                tbTindakanDrPrBayar.getValueAt(i,11).toString(),tbTindakanDrPrBayar.getValueAt(i,12).toString(),TNoRw.getText(),tbTindakanDrPrBayar.getValueAt(i,1).toString()
                              })==true){
                                try {
                                    psbiling=koneksi.prepareStatement(sqlpsbiling);
                                    try {
                                        psbiling.setInt(1,i);
                                        psbiling.setString(2,TNoRw.getText());
                                        psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                        psbiling.setString(4,"Tindakan");
                                        psbiling.setString(5,": Pembatalan "+tbTindakanDrPrBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                        psbiling.setString(6,":");                    
                                        try {                        
                                            psbiling.setDouble(7,-Valid.SetAngka(tbTindakanDrPrBayar.getValueAt(i,4).toString()));
                                        } catch (Exception e) {
                                            psbiling.setDouble(7,0);
                                        }
                                        try {
                                            psbiling.setDouble(8,1);
                                        } catch (Exception e) {
                                            psbiling.setDouble(8,0);
                                        }
                                        psbiling.setDouble(9,0); 
                                        try {
                                            psbiling.setDouble(10,-Valid.SetAngka(tbTindakanDrPrBayar.getValueAt(i,4).toString())); 
                                        } catch (Exception e) {
                                            psbiling.setDouble(10,0);
                                        }                    
                                        psbiling.setString(11,"Ralan Dokter Paramedis");
                                        psbiling.executeUpdate();
                                    } catch (Exception e) {
                                        sukses=false;
                                        System.out.println("Notifikasi : "+e);
                                    } finally{
                                        if(psbiling != null){
                                            psbiling.close();
                                        } 
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                }
                            }else{
                                sukses=false;
                            }
                        }else{
                            if(Sequel.queryu2tf("delete from rawat_jl_drpr where tgl_perawatan=? and jam_rawat=? and no_rawat=? and kd_jenis_prw=?",4,new String[]{
                                tbTindakanDrPrBayar.getValueAt(i,11).toString(),tbTindakanDrPrBayar.getValueAt(i,12).toString(),TNoRw.getText(),tbTindakanDrPrBayar.getValueAt(i,1).toString()
                              })==true){
                                try {
                                    psbiling=koneksi.prepareStatement(sqlpsbiling);
                                    try {
                                        psbiling.setInt(1,i);
                                        psbiling.setString(2,TNoRw.getText());
                                        psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                        psbiling.setString(4,"Tindakan");
                                        psbiling.setString(5,": Pembatalan "+tbTindakanDrPrBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                        psbiling.setString(6,":");                    
                                        try {                        
                                            psbiling.setDouble(7,-Valid.SetAngka(tbTindakanDrPrBayar.getValueAt(i,4).toString()));
                                        } catch (Exception e) {
                                            psbiling.setDouble(7,0);
                                        }
                                        try {
                                            psbiling.setDouble(8,1);
                                        } catch (Exception e) {
                                            psbiling.setDouble(8,0);
                                        }
                                        psbiling.setDouble(9,0); 
                                        try {
                                            psbiling.setDouble(10,-Valid.SetAngka(tbTindakanDrPrBayar.getValueAt(i,4).toString())); 
                                        } catch (Exception e) {
                                            psbiling.setDouble(10,0);
                                        }                    
                                        psbiling.setString(11,"Ralan Dokter Paramedis");
                                        psbiling.executeUpdate();
                                    } catch (Exception e) {
                                        sukses=false;
                                        System.out.println("Notifikasi : "+e);
                                    } finally{
                                        if(psbiling != null){
                                            psbiling.close();
                                        } 
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif : "+e);
                                }
                            }else{
                                sukses=false;
                            }
                        }
                    }                        
                }
                
                for(i=0;i<tbRadiologiBayar.getRowCount();i++){
                    if(tbRadiologiBayar.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.queryu2tf("delete from permintaan_pemeriksaan_radiologi where noorder=? and kd_jenis_prw=?",2,new String[]{
                            tbRadiologiBayar.getValueAt(i,13).toString(),tbRadiologiBayar.getValueAt(i,1).toString()
                          })==true){
                            try {
                                psbiling=koneksi.prepareStatement(sqlpsbiling);
                                try {
                                    psbiling.setInt(1,i);
                                    psbiling.setString(2,TNoRw.getText());
                                    psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                    psbiling.setString(4,"Tindakan");
                                    psbiling.setString(5,": Pembatalan "+tbRadiologiBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                    psbiling.setString(6,":");                    
                                    try {                        
                                        psbiling.setDouble(7,-Valid.SetAngka(tbRadiologiBayar.getValueAt(i,3).toString()));
                                    } catch (Exception e) {
                                        psbiling.setDouble(7,0);
                                    }
                                    try {
                                        psbiling.setDouble(8,1);
                                    } catch (Exception e) {
                                        psbiling.setDouble(8,0);
                                    }
                                    psbiling.setDouble(9,0); 
                                    try {
                                        psbiling.setDouble(10,-Valid.SetAngka(tbRadiologiBayar.getValueAt(i,3).toString())); 
                                    } catch (Exception e) {
                                        psbiling.setDouble(10,0);
                                    }                    
                                    psbiling.setString(11,"Radiologi");
                                    psbiling.executeUpdate();
                                } catch (Exception e) {
                                    sukses=false;
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(psbiling != null){
                                        psbiling.close();
                                    } 
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            }
                            if(Sequel.cariInteger("select count(*) from permintaan_pemeriksaan_radiologi where noorder=?",tbRadiologiBayar.getValueAt(i,13).toString())==0){
                                Sequel.meghapus3("permintaan_radiologi","noorder",tbRadiologiBayar.getValueAt(i,13).toString());
                            }
                        }else{
                            sukses=false;
                        }
                    }                        
                }
                
                for(i=0;i<tbLaboratBayar.getRowCount();i++){
                    if(tbLaboratBayar.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.queryu2tf("delete from permintaan_pemeriksaan_lab where noorder=? and kd_jenis_prw=?",2,new String[]{
                            tbLaboratBayar.getValueAt(i,13).toString(),tbLaboratBayar.getValueAt(i,1).toString()
                          })==true){
                            try {
                                psbiling=koneksi.prepareStatement(sqlpsbiling);
                                try {
                                    psbiling.setInt(1,i);
                                    psbiling.setString(2,TNoRw.getText());
                                    psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                    psbiling.setString(4,"Tindakan");
                                    psbiling.setString(5,": Pembatalan "+tbLaboratBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                    psbiling.setString(6,":");                    
                                    try {                        
                                        psbiling.setDouble(7,-Valid.SetAngka(tbLaboratBayar.getValueAt(i,3).toString()));
                                    } catch (Exception e) {
                                        psbiling.setDouble(7,0);
                                    }
                                    try {
                                        psbiling.setDouble(8,1);
                                    } catch (Exception e) {
                                        psbiling.setDouble(8,0);
                                    }
                                    psbiling.setDouble(9,0); 
                                    try {
                                        psbiling.setDouble(10,-Valid.SetAngka(tbLaboratBayar.getValueAt(i,3).toString())); 
                                    } catch (Exception e) {
                                        psbiling.setDouble(10,0);
                                    }                    
                                    psbiling.setString(11,"Laborat");
                                    psbiling.executeUpdate();
                                } catch (Exception e) {
                                    sukses=false;
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(psbiling != null){
                                        psbiling.close();
                                    } 
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            }
                            if((Sequel.cariInteger("select count(*) from permintaan_pemeriksaan_lab where noorder=?",tbLaboratBayar.getValueAt(i,13).toString())==0)&&
                                    (Sequel.cariInteger("select count(*) from permintaan_detail_permintaan_lab where noorder=?",tbLaboratBayar.getValueAt(i,13).toString())==0)){
                                Sequel.meghapus3("permintaan_lab","noorder",tbLaboratBayar.getValueAt(i,13).toString());
                            }
                        }else{
                            sukses=false;
                        }
                    }                        
                }
                
                for(i=0;i<tbDetailLaboratBayar.getRowCount();i++){
                    if(tbDetailLaboratBayar.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.queryu2tf("delete from permintaan_detail_permintaan_lab where noorder=? and id_template=?",2,new String[]{
                            tbDetailLaboratBayar.getValueAt(i,13).toString(),tbDetailLaboratBayar.getValueAt(i,1).toString()
                          })==true){
                            try {
                                psbiling=koneksi.prepareStatement(sqlpsbiling);
                                try {
                                    psbiling.setInt(1,i);
                                    psbiling.setString(2,TNoRw.getText());
                                    psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                    psbiling.setString(4,"Tindakan");
                                    psbiling.setString(5,": Pembatalan "+tbDetailLaboratBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                    psbiling.setString(6,":");                    
                                    try {                        
                                        psbiling.setDouble(7,-Valid.SetAngka(tbDetailLaboratBayar.getValueAt(i,3).toString()));
                                    } catch (Exception e) {
                                        psbiling.setDouble(7,0);
                                    }
                                    try {
                                        psbiling.setDouble(8,1);
                                    } catch (Exception e) {
                                        psbiling.setDouble(8,0);
                                    }
                                    psbiling.setDouble(9,0); 
                                    try {
                                        psbiling.setDouble(10,-Valid.SetAngka(tbDetailLaboratBayar.getValueAt(i,3).toString())); 
                                    } catch (Exception e) {
                                        psbiling.setDouble(10,0);
                                    }                    
                                    psbiling.setString(11,"Laborat");
                                    psbiling.executeUpdate();
                                } catch (Exception e) {
                                    sukses=false;
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(psbiling != null){
                                        psbiling.close();
                                    } 
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            }
                            if((Sequel.cariInteger("select count(*) from permintaan_detail_permintaan_lab where noorder=?",tbDetailLaboratBayar.getValueAt(i,13).toString())==0)&&
                                    (Sequel.cariInteger("select count(*) from permintaan_detail_permintaan_lab where noorder=?",tbDetailLaboratBayar.getValueAt(i,13).toString())==0)){
                                Sequel.meghapus3("permintaan_lab","noorder",tbDetailLaboratBayar.getValueAt(i,13).toString());
                            }
                        }else{
                            sukses=false;
                        }
                    }                        
                }
                
                for(i=0;i<tbObatBayar.getRowCount();i++){
                    if(tbObatBayar.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.queryu2tf("delete from permintaan_obat where tanggal=? and jam=? and no_rawat=? and kode_brng=?",4,new String[]{
                            tbObatBayar.getValueAt(i,6).toString(),tbObatBayar.getValueAt(i,7).toString(),TNoRw.getText(),tbObatBayar.getValueAt(i,1).toString()
                          })==true){
                            try {
                                psbiling=koneksi.prepareStatement(sqlpsbiling);
                                try {
                                    psbiling.setInt(1,i);
                                    psbiling.setString(2,TNoRw.getText());
                                    psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                    psbiling.setString(4,"Obat");
                                    psbiling.setString(5,": Pembatalan "+tbObatBayar.getValueAt(i,2).toString().replaceAll("'",""));
                                    psbiling.setString(6,":");                    
                                    try {                        
                                        psbiling.setDouble(7,-(Valid.SetAngka(tbObatBayar.getValueAt(i,4).toString())/Valid.SetAngka(tbObatBayar.getValueAt(i,3).toString())));
                                    } catch (Exception e) {
                                        psbiling.setDouble(7,0);
                                    }
                                    try {
                                        psbiling.setDouble(8,Valid.SetAngka(tbObatBayar.getValueAt(i,3).toString()));
                                    } catch (Exception e) {
                                        psbiling.setDouble(8,0);
                                    }
                                    psbiling.setDouble(9,0); 
                                    try {
                                        psbiling.setDouble(10,-Valid.SetAngka(tbObatBayar.getValueAt(i,4).toString())); 
                                    } catch (Exception e) {
                                        psbiling.setDouble(10,0);
                                    }                    
                                    psbiling.setString(11,"Obat");
                                    psbiling.executeUpdate();
                                } catch (Exception e) {
                                    sukses=false;
                                    System.out.println("Notifikasi : "+e);
                                } finally{
                                    if(psbiling != null){
                                        psbiling.close();
                                    } 
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            }
                        }else{
                            sukses=false;
                        }
                    }                        
                }
                
                if(ppnobat>0){
                    if(tampilkan_ppnobat_ralan.equals("Yes")){
                        try {
                            psbiling=koneksi.prepareStatement(sqlpsbiling);
                            try {
                                psbiling.setInt(1,i);
                                psbiling.setString(2,TNoRw.getText());
                                psbiling.setString(3,Valid.SetTgl(DTPTgl.getSelectedItem()+""));
                                psbiling.setString(4,"Obat");
                                psbiling.setString(5,": Pembatalan PPN Obat");
                                psbiling.setString(6,":");                    
                                try {                        
                                    psbiling.setDouble(7,-ppnobat);
                                } catch (Exception e) {
                                    psbiling.setDouble(7,0);
                                }
                                try {
                                    psbiling.setDouble(8,1);
                                } catch (Exception e) {
                                    psbiling.setDouble(8,0);
                                }
                                psbiling.setDouble(9,0); 
                                try {
                                    psbiling.setDouble(10,-ppnobat); 
                                } catch (Exception e) {
                                    psbiling.setDouble(10,0);
                                }                    
                                psbiling.setString(11,"Obat");
                                psbiling.executeUpdate();
                            } catch (Exception e) {
                                sukses=false;
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(psbiling != null){
                                    psbiling.close();
                                } 
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        }
                    }                        
                }
                
                if(sukses==true){
                    Sequel.queryu2("delete from tampjurnal");
                    itembayar=0;besarppn=0;
                    row2=tbAkunBayar.getRowCount();                
                    for(r=0;r<row2;r++){
                        if(Valid.SetAngka(tbAkunBayar.getValueAt(r,2).toString())>0){
                            try {
                                itembayar=Double.parseDouble(tbAkunBayar.getValueAt(r,2).toString()); 
                            } catch (Exception e) {
                                itembayar=0;
                            }    

                            if(!tbAkunBayar.getValueAt(r,4).toString().equals("")){
                                try {
                                    besarppn=Valid.roundUp(Double.parseDouble(tbAkunBayar.getValueAt(r,4).toString()),100); 
                                } catch (Exception e) {
                                    besarppn=0;
                                }               
                            }  

                            if(countbayar>1){
                                if(Sequel.menyimpantf("detail_nota_jalan","?,?,?,?",4,new String[]{
                                        TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(-besarppn),Double.toString(-itembayar)
                                    },"no_rawat=? and nama_bayar=?","besarppn=besarppn-?,besar_bayar=besar_bayar-?",4,new String[]{
                                        Double.toString(besarppn),Double.toString(itembayar),TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString()
                                    })==true){
                                        Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','0','"+Double.toString(itembayar)+"'","Rekening");                 
                                }
                            }else if(countbayar==1){
                                if(Sequel.menyimpantf("detail_nota_jalan","?,?,?,?",4,new String[]{
                                        TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString(),Double.toString(-besarppn),Double.toString(-total)
                                    },"no_rawat=? and nama_bayar=?","besarppn=besarppn-?,besar_bayar=besar_bayar-?",4,new String[]{
                                        Double.toString(besarppn),Double.toString(total),TNoRw.getText(),tbAkunBayar.getValueAt(r,0).toString()
                                    })==true){
                                        Sequel.menyimpan("tampjurnal","'"+tbAkunBayar.getValueAt(r,1).toString()+"','"+tbAkunBayar.getValueAt(r,0).toString()+"','0','"+Double.toString(total)+"'","Rekening");                 
                                }                                                                
                            }                        
                        }  
                    }

                    if((ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis-Suspen_Tindakan_Ralan)>0){
                        Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Tindakan Ralan','"+(ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis-Suspen_Tindakan_Ralan)+"','0'","debet=debet+'"+(ttlRalan_Dokter+ttlRalan_Dokter_Param+ttlRalan_Paramedis-Suspen_Tindakan_Ralan)+"'","kd_rek='"+Tindakan_Ralan+"'");    
                    }

                    if(Suspen_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Tindakan Ralan','"+(Suspen_Tindakan_Ralan)+"','0'","debet=debet+'"+(Suspen_Tindakan_Ralan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");    
                    }

                    if(ttlLaborat>0){
                        Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','Laborat Ralan','"+ttlLaborat+"','0'","debet=debet+'"+(ttlLaborat)+"'","kd_rek='"+Laborat_Ralan+"'");    
                    }

                    if(ttlRadiologi>0){
                        Sequel.menyimpan("tampjurnal","'"+Radiologi_Ralan+"','Radiologi Ralan','"+ttlRadiologi+"','0'","debet=debet+'"+(ttlRadiologi)+"'","kd_rek='"+Radiologi_Ralan+"'");    
                    }

                    if(ttlObat>0){
                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Obat_Ralan+"','Obat Ralan','"+ttlObat+"','0'","debet=debet+'"+(ttlObat)+"'","kd_rek='"+Suspen_Piutang_Obat_Ralan+"'");    
                    }

                    if(ttlRegistrasi>0){
                        Sequel.menyimpan("tampjurnal","'"+Registrasi_Ralan+"','Registrasi Ralan','"+ttlRegistrasi+"','0'","debet=debet+'"+(ttlRegistrasi)+"'","kd_rek='"+Registrasi_Ralan+"'");    
                    }

                    if(Jasa_Medik_Dokter_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Dokter_Tindakan_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Dokter_Tindakan_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Operasi Ralan','"+Jasa_Medik_Dokter_Tindakan_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Dokter_Tindakan_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");  
                    }

                    if(Jasa_Medik_Paramedis_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Paramedis_Tindakan_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Paramedis_Tindakan_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Operasi Ralan','"+Jasa_Medik_Paramedis_Tindakan_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Paramedis_Tindakan_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");  
                    }

                    if(KSO_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Operasi Ralan','0','"+KSO_Tindakan_Ralan+"'","kredit=kredit+'"+(KSO_Tindakan_Ralan)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Operasi Ralan','"+KSO_Tindakan_Ralan+"','0'","debet=debet+'"+(KSO_Tindakan_Ralan)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");  
                    }

                    if(Jasa_Sarana_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Operasi Ralan','0','"+Jasa_Sarana_Tindakan_Ralan+"'","kredit=kredit+'"+Jasa_Sarana_Tindakan_Ralan+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Operasi Ralan','"+Jasa_Sarana_Tindakan_Ralan+"','0'","debet=debet+'"+Jasa_Sarana_Tindakan_Ralan+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");  
                    }

                    if(BHP_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','Operasi Ralan','0','"+BHP_Tindakan_Ralan+"'","kredit=kredit+'"+BHP_Tindakan_Ralan+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Operasi Ralan','"+BHP_Tindakan_Ralan+"','0'","debet=debet+'"+BHP_Tindakan_Ralan+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");  
                    }

                    if(Jasa_Menejemen_Tindakan_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Operasi Ralan','0','"+Jasa_Menejemen_Tindakan_Ralan+"'","kredit=kredit+'"+(Jasa_Menejemen_Tindakan_Ralan)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Operasi Ralan','"+Jasa_Menejemen_Tindakan_Ralan+"','0'","debet=debet+'"+(Jasa_Menejemen_Tindakan_Ralan)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");  
                    }

                    if(Jasa_Medik_Dokter_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Dokter_Laborat_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Dokter_Laborat_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Medik_Dokter_Laborat_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Dokter_Laborat_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'");  
                    }

                    if(Jasa_Medik_Petugas_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Petugas_Laborat_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Petugas_Laborat_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Medik_Petugas_Laborat_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Petugas_Laborat_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"'");  
                    }

                    if(Kso_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ralan+"','Operasi Ralan','0','"+Kso_Laborat_Ralan+"'","kredit=kredit+'"+(Kso_Laborat_Ralan)+"'","kd_rek='"+Beban_Kso_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ralan+"','Operasi Ralan','"+Kso_Laborat_Ralan+"','0'","debet=debet+'"+(Kso_Laborat_Ralan)+"'","kd_rek='"+Utang_Kso_Laborat_Ralan+"'");  
                    }

                    if(Persediaan_Laborat_Rawat_Jalan>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_Jalan+"','Operasi Ralan','0','"+Persediaan_Laborat_Rawat_Jalan+"'","kredit=kredit+'"+(Persediaan_Laborat_Rawat_Jalan)+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_Jalan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Laborat_Rawat_Jalan+"','Operasi Ralan','"+Persediaan_Laborat_Rawat_Jalan+"','0'","debet=debet+'"+(Persediaan_Laborat_Rawat_Jalan)+"'","kd_rek='"+Persediaan_BHP_Laborat_Rawat_Jalan+"'");  
                    }

                    if(Jasa_Sarana_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Sarana_Laborat_Ralan+"'","kredit=kredit+'"+(Jasa_Sarana_Laborat_Ralan)+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Sarana_Laborat_Ralan+"','0'","debet=debet+'"+(Jasa_Sarana_Laborat_Ralan)+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ralan+"'");  
                    }

                    if(Jasa_Perujuk_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Perujuk_Laborat_Ralan+"'","kredit=kredit+'"+(Jasa_Perujuk_Laborat_Ralan)+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Perujuk_Laborat_Ralan+"','0'","debet=debet+'"+(Jasa_Perujuk_Laborat_Ralan)+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ralan+"'");  
                    }

                    if(Jasa_Menejemen_Laborat_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ralan+"','Operasi Ralan','0','"+Jasa_Menejemen_Laborat_Ralan+"'","kredit=kredit+'"+(Jasa_Menejemen_Laborat_Ralan)+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ralan+"','Operasi Ralan','"+Jasa_Menejemen_Laborat_Ralan+"','0'","debet=debet+'"+(Jasa_Menejemen_Laborat_Ralan)+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ralan+"'");  
                    }

                    if(Jasa_Medik_Dokter_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Dokter_Radiologi_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Dokter_Radiologi_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Medik_Dokter_Radiologi_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Dokter_Radiologi_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'");  
                    }

                    if(Jasa_Medik_Petugas_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Medik_Petugas_Radiologi_Ralan+"'","kredit=kredit+'"+(Jasa_Medik_Petugas_Radiologi_Ralan)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Medik_Petugas_Radiologi_Ralan+"','0'","debet=debet+'"+(Jasa_Medik_Petugas_Radiologi_Ralan)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"'");  
                    }

                    if(Kso_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ralan+"','Operasi Ralan','0','"+Kso_Radiologi_Ralan+"'","kredit=kredit+'"+(Kso_Radiologi_Ralan)+"'","kd_rek='"+Beban_Kso_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ralan+"','Operasi Ralan','"+Kso_Radiologi_Ralan+"','0'","debet=debet+'"+(Kso_Radiologi_Ralan)+"'","kd_rek='"+Utang_Kso_Radiologi_Ralan+"'");  
                    }

                    if(Persediaan_Radiologi_Rawat_Jalan>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Radiologi_Rawat_Jalan+"','Operasi Ralan','0','"+Persediaan_Radiologi_Rawat_Jalan+"'","kredit=kredit+'"+(Persediaan_Radiologi_Rawat_Jalan)+"'","kd_rek='"+HPP_Persediaan_Radiologi_Rawat_Jalan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Radiologi_Rawat_Jalan+"','Operasi Ralan','"+Persediaan_Radiologi_Rawat_Jalan+"','0'","debet=debet+'"+(Persediaan_Radiologi_Rawat_Jalan)+"'","kd_rek='"+Persediaan_BHP_Radiologi_Rawat_Jalan+"'");  
                    }

                    if(Jasa_Sarana_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Sarana_Radiologi_Ralan+"'","kredit=kredit+'"+(Jasa_Sarana_Radiologi_Ralan)+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Sarana_Radiologi_Ralan+"','0'","debet=debet+'"+(Jasa_Sarana_Radiologi_Ralan)+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ralan+"'");  
                    }

                    if(Jasa_Perujuk_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Perujuk_Radiologi_Ralan+"'","kredit=kredit+'"+(Jasa_Perujuk_Radiologi_Ralan)+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Perujuk_Radiologi_Ralan+"','0'","debet=debet+'"+(Jasa_Perujuk_Radiologi_Ralan)+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ralan+"'");  
                    }

                    if(Jasa_Menejemen_Radiologi_Ralan>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ralan+"','Operasi Ralan','0','"+Jasa_Menejemen_Radiologi_Ralan+"'","kredit=kredit+'"+(Jasa_Menejemen_Radiologi_Ralan)+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ralan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ralan+"','Operasi Ralan','"+Jasa_Menejemen_Radiologi_Ralan+"','0'","debet=debet+'"+(Jasa_Menejemen_Radiologi_Ralan)+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ralan+"'");  
                    }

                    if(Obat_Rawat_Jalan>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Rawat_Jalan+"','Operasi Ralan','0','"+Obat_Rawat_Jalan+"'","kredit=kredit+'"+(Obat_Rawat_Jalan)+"'","kd_rek='"+HPP_Obat_Rawat_Jalan+"'");  
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Rawat_Jalan+"','Operasi Ralan','"+Obat_Rawat_Jalan+"','0'","debet=debet+'"+(Obat_Rawat_Jalan)+"'","kd_rek='"+Persediaan_Obat_Rawat_Jalan+"'");  
                    }

                    sukses=jur.simpanJurnal(TNoRw.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),"U","PEMBATALAN PEMBAYARAN PASIEN RAWAT JALAN, DIPOSTING OLEH "+akses.getkode());
                }
                    
                if(sukses==true){
                    Sequel.mengedit("tagihan_sadewa","no_nota='"+TNoRw.getText()+"'","jumlah_tagihan=jumlah_tagihan-'"+total+"',jumlah_bayar=jumlah_bayar-'"+total+"'");
                    Sequel.queryu2("delete from tagihan_sadewa where no_nota=? and jumlah_tagihan='0'",1,new String[]{TNoRw.getText()});
                    Sequel.queryu2("delete from detail_nota_jalan where no_rawat=? and besar_bayar='0'",1,new String[]{TNoRw.getText()});
                    if(Sequel.cariIsiAngka("select sum(totalbiaya) from billing where no_rawat=?",TNoRw.getText())==0){
                        Sequel.queryu2("delete from billing where no_rawat=?",1,new String[]{TNoRw.getText()});
                        Sequel.queryu2("delete from nota_jalan where no_rawat=?",1,new String[]{TNoRw.getText()});
                    }
                    
                    Sequel.Commit();
                    
                    for(r=0;r<row2;r++){
                        tbAkunBayar.setValueAt("",r,2);
                        tbAkunBayar.setValueAt("",r,4);
                    }
                    JOptionPane.showMessageDialog(null,"Proses hapus selesai...!"); 
                    statushapus=false;

                    Valid.tabelKosong(TabModeTindakanDrBayar);
                    Valid.tabelKosong(TabModeTindakanPrBayar);
                    Valid.tabelKosong(TabModeTindakanDrPrBayar);
                    Valid.tabelKosong(TabModeRadiologiBayar);
                    Valid.tabelKosong(TabModeLaboratBayar);
                    Valid.tabelKosong(TabModeDetailLaboratBayar);
                    Valid.tabelKosong(TabModeObatBayar);

                    TabRawatMouseClicked(null);
                    isHitungHapus();
                }else{
                    sukses=false;
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
            }catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);            
                JOptionPane.showMessageDialog(null,"Maaf, gagal menghapus data. Data yang sama dimasukkan sebelumnya...!");
            } 
        }        
    }

    private void tampilRadiologiBayar() {
        try{  
            Valid.tabelKosong(TabModeRadiologiBayar);
                pstindakan=koneksi.prepareStatement(
                    "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                    "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                    "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                    "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,permintaan_radiologi.tgl_permintaan,"+
                    "permintaan_radiologi.jam_permintaan,permintaan_radiologi.noorder,permintaan_pemeriksaan_radiologi.stts_bayar "+
                    "from jns_perawatan_radiologi inner join permintaan_radiologi inner join permintaan_pemeriksaan_radiologi "+
                    "on jns_perawatan_radiologi.kd_jenis_prw=permintaan_pemeriksaan_radiologi.kd_jenis_prw and "+
                    "permintaan_radiologi.noorder=permintaan_pemeriksaan_radiologi.noorder where "+
                    "permintaan_radiologi.no_rawat=? and permintaan_pemeriksaan_radiologi.stts_bayar<>'Belum' order by jns_perawatan_radiologi.kd_jenis_prw");
                try {
                    pstindakan.setString(1,TNoRw.getText());
                    rstindakan=pstindakan.executeQuery();
                    while(rstindakan.next()){                
                        TabModeRadiologiBayar.addRow(new Object[]{
                            false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                            rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                            rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                            rstindakan.getDouble(10),rstindakan.getString(11),rstindakan.getString(12),
                            rstindakan.getString(13),rstindakan.getString("stts_bayar")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi 1 : "+e);
                } finally{
                    if(rstindakan!=null){
                        rstindakan.close();
                    }
                    if(pstindakan!=null){
                        pstindakan.close();
                    }                
                }      
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilLaboratBayar() {
        try{  
            Valid.tabelKosong(TabModeLaboratBayar);
                pstindakan=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                    "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                    "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                    "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,permintaan_lab.tgl_permintaan,"+
                    "permintaan_lab.jam_permintaan,permintaan_lab.noorder,permintaan_pemeriksaan_lab.stts_bayar "+
                    "from jns_perawatan_lab inner join permintaan_lab inner join permintaan_pemeriksaan_lab "+
                    "on jns_perawatan_lab.kd_jenis_prw=permintaan_pemeriksaan_lab.kd_jenis_prw and "+
                    "permintaan_lab.noorder=permintaan_pemeriksaan_lab.noorder where "+
                    "permintaan_lab.no_rawat=? and permintaan_pemeriksaan_lab.stts_bayar<>'Belum' order by jns_perawatan_lab.kd_jenis_prw");
                try {
                    pstindakan.setString(1,TNoRw.getText());
                    rstindakan=pstindakan.executeQuery();
                    while(rstindakan.next()){                
                        TabModeLaboratBayar.addRow(new Object[]{
                            false,rstindakan.getString(1),rstindakan.getString(2),rstindakan.getDouble(3),
                            rstindakan.getDouble(4),rstindakan.getDouble(5),rstindakan.getDouble(6),
                            rstindakan.getDouble(7),rstindakan.getDouble(8),rstindakan.getDouble(9),
                            rstindakan.getDouble(10),rstindakan.getString(11),rstindakan.getString(12),
                            rstindakan.getString(13),rstindakan.getString("stts_bayar")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi 1 : "+e);
                } finally{
                    if(rstindakan!=null){
                        rstindakan.close();
                    }
                    if(pstindakan!=null){
                        pstindakan.close();
                    }                
                }      
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilObat(){
        try{  
            Valid.tabelKosong(TabModeObat);
            pstindakan=koneksi.prepareStatement(
                    "select detail_pemberian_obat.kode_brng,databarang.nama_brng,detail_pemberian_obat.jml,detail_pemberian_obat.total,"+
                    "(detail_pemberian_obat.h_beli*detail_pemberian_obat.jml) as hpp,detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam "+
                    "from detail_pemberian_obat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                    "where detail_pemberian_obat.status='Ralan' and detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.kode_brng not in "+
                    "(select permintaan_obat.kode_brng from permintaan_obat where permintaan_obat.kode_brng=detail_pemberian_obat.kode_brng and "+
                    "permintaan_obat.tanggal=detail_pemberian_obat.tgl_perawatan and permintaan_obat.jam=detail_pemberian_obat.jam and "+
                    "permintaan_obat.no_rawat=?)");
            try {
                pstindakan.setString(1,TNoRw.getText());
                pstindakan.setString(2,TNoRw.getText());
                rstindakan=pstindakan.executeQuery();
                while(rstindakan.next()){                
                    TabModeObat.addRow(new Object[]{
                        false,rstindakan.getString("kode_brng"),rstindakan.getString("nama_brng"),
                        rstindakan.getString("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("hpp"),
                        rstindakan.getString("tgl_perawatan"),rstindakan.getString("jam")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rstindakan!=null){
                    rstindakan.close();
                }
                if(pstindakan!=null){
                    pstindakan.close();
                }
            }
        }catch(Exception ex){
            System.out.println("Notifikasi : "+ex);
        }    
    }
    
    private void tampilObatBayar(){
        try{  
            Valid.tabelKosong(TabModeObatBayar);
            pstindakan=koneksi.prepareStatement(
                    "select detail_pemberian_obat.kode_brng,databarang.nama_brng,detail_pemberian_obat.jml,detail_pemberian_obat.total,"+
                    "(detail_pemberian_obat.h_beli*detail_pemberian_obat.jml) as hpp,detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam "+
                    "from detail_pemberian_obat inner join databarang inner join permintaan_obat on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                    "and permintaan_obat.kode_brng=detail_pemberian_obat.kode_brng and permintaan_obat.tanggal=detail_pemberian_obat.tgl_perawatan "+
                    "and permintaan_obat.jam=detail_pemberian_obat.jam where detail_pemberian_obat.status='Ralan' and detail_pemberian_obat.no_rawat=? ");
            try {
                pstindakan.setString(1,TNoRw.getText());
                rstindakan=pstindakan.executeQuery();
                while(rstindakan.next()){                
                    TabModeObatBayar.addRow(new Object[]{
                        false,rstindakan.getString("kode_brng"),rstindakan.getString("nama_brng"),
                        rstindakan.getString("jml"),rstindakan.getDouble("total"),rstindakan.getDouble("hpp"),
                        rstindakan.getString("tgl_perawatan"),rstindakan.getString("jam")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rstindakan!=null){
                    rstindakan.close();
                }
                if(pstindakan!=null){
                    pstindakan.close();
                }
            }
        }catch(Exception ex){
            System.out.println("Notifikasi : "+ex);
        }  
    }
    
}
