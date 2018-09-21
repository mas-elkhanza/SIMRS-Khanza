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
import fungsi.var;
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
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private WarnaTable2 warna=new WarnaTable2();
    private final DefaultTableModel tabModeDrBayar,tabModeAkunBayar,TabModeTindakanDr,
            TabModeTindakanDrPr,TabModeTindakanPr,tabModePrBayar,tabModeDrPrBayar,
            tabModeBilling,tabModeRadiologi,tabModeRadiologiBayar,tabModeLaborat,
            tabModeSubLaborat,tabModeLaboratBayar,tabModeSubLaboratBayar,tabModeObat;
    private int i=0,countbayar=0,z=0,jml=0,index=0;
    private String[] Nama_Akun_Bayar,Kode_Rek_Bayar,Bayar,PPN_Persen,PPN_Besar;
    private boolean[] pilih; 
    private String[] kode,nama,kategori,tglpermintaan,jampermintaan;
    private double[] totaltnd,bagianrs,bhp,jmdokter,jmperawat,kso,menejemen,tarif_perujuk;
    private PreparedStatement psakunbayar,pstindakan,pstindakan2,pstindakan3,
            pstindakan4,psset_tarif,psBayarDr,psBayarPr,psBayarDrPr,psreg,
            pscaripoli,pscarialamat,psdokterralan,psradiologi,psBayarradiologi,
            pslaborat,pssublaborat,psobat;
    private ResultSet rsakunbayar,rstindakan,rsset_tarif,rsbayar,rsreg,rscaripoli,
            rscarialamat,rsdokterralan,rsradilogi,rslaborat,rssublaborat,rsobat;
    private String jmls="",kd_pj="",kd_poli="",poli_ralan="Yes",cara_bayar_ralan="Yes",cara_bayar_lab="Yes",kelas_lab="Yes",
            NoNota="",sqlpscaripoli="select nm_poli from poliklinik where kd_poli=?",
            sqlpscarialamat="select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?",notaralan="No",centangdokterralan="No",
            rinciandokterralan="No",sqlpsdokterralan="select dokter.nm_dokter from reg_periksa "+
                            "inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter "+
                            "where no_rawat=? group by reg_periksa.kd_dokter",
            biaya="",tambahan="",totals="",
            cara_bayar_radiologi="No",kelas_radiologi="No",tampilkan_ppnobat_ralan="";
    private double subttl=0,ppnobat=0,ttl=0,y=0,ttlLaborat=0,ttlRadiologi=0,
            ttlObat=0,ttlRalan_Dokter=0,ttlRalan_Paramedis=0,ttlRegistrasi=0,
            ttlRalan_Dokter_Param=0,bayar=0,total=0,ppn=0,besarppn=0,
            tagihanppn=0,kekurangan=0,Jasa_Medik_Dokter_Tindakan_Ralan=0,
            Jasa_Medik_Paramedis_Tindakan_Ralan=0,KSO_Tindakan_Ralan=0,
            Jasa_Medik_Dokter_Laborat_Ralan=0,Jasa_Medik_Petugas_Laborat_Ralan=0,
            Kso_Laborat_Ralan=0,Persediaan_Laborat_Rawat_Jalan=0,
            Jasa_Medik_Dokter_Radiologi_Ralan=0,Jasa_Medik_Petugas_Radiologi_Ralan=0,
            Kso_Radiologi_Ralan=0,Persediaan_Radiologi_Rawat_Jalan=0,Obat_Rawat_Jalan=0;
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
        
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {BtnCariBayarActionPerformed(null);}
                @Override
                public void removeUpdate(DocumentEvent e) {BtnCariBayarActionPerformed(null);}
                @Override
                public void changedUpdate(DocumentEvent e) {BtnCariBayarActionPerformed(null);}
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
                        KdDokPerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokterPerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        KdDokPerujuk.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==4){
                        KdDokPerujuk1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokterPerujuk1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        KdDokPerujuk1.requestFocus();
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
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
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
                "JM Dokter","JM Perawat","KSO","Menejemen","Tgl.Permintaan","Jam Permintaan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class, java.lang.Object.class, 
                java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDr.setModel(TabModeTindakanDr);
        tbRawatDr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 13; i++) {
            TableColumn column = tbRawatDr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==5){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==6){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==7){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==8){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==9){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==10){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==11){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==12){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbRawatDr.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakanPr=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan","Kategori","Tarif","Bagian RS","BHP",
                "JM Dokter","JM Perawat","KSO","Menejemen","Tgl.Permintaan","Jam Permintaan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Object.class,
                java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatPr.setModel(TabModeTindakanPr);
        tbRawatPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 13; i++) {
            TableColumn column = tbRawatPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==5){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==6){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==7){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==8){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==9){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==10){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==11){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==12){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbRawatPr.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakanDrPr=new DefaultTableModel(null,new Object[]{
                "P","Kode","Nama Perawatan","Kategori","Tarif","Bagian RS","BHP",
                "JM Dokter","JM Perawat","KSO","Menejemen","Tgl.Permintaan","Jam Permintaan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Object.class,
                java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDrPr.setModel(TabModeTindakanDrPr);
        tbRawatDrPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDrPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 13; i++) {
            TableColumn column = tbRawatDrPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==5){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==6){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==7){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==8){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==9){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==10){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==11){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else if(i==13){
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(90);
            }
        }
        tbRawatDrPr.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDrBayar=new DefaultTableModel(null,new Object[]{
                "P","No.Rawat","No.R.M.","Nama Pasien","Kode Tarif","Perawatan/Tindakan","Kode Dokter",
                "Dokter Yg Menangani","Tgl.Bayar","Jam Bayar","material","bhp","tarif_tindakandr",
                "kso","menejemen","Biaya"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDrBayar.setModel(tabModeDrBayar);
        //tampilDr();

        tbRawatDrBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDrBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbRawatDrBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else{
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }
        }
        tbRawatDrBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePrBayar=new DefaultTableModel(null,new Object[]{
                "P","No.Rawat","No.R.M.","Nama Pasien","Kode Tarif","Perawatan/Tindakan","NIP",
                "Petugas Yg Menangani","Tgl.Bayar","Jam Bayar","material",
                "bhp","tarif_tindakandr","kso","menejemen","Biaya"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatPrBayar.setModel(tabModePrBayar);
        //tampilDr();

        tbRawatPrBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatPrBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbRawatPrBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else{
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }
        }
        tbRawatPrBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDrPrBayar=new DefaultTableModel(null,new Object[]{
                "P","No.Rawat","No.R.M.","Nama Pasien","Kode Tarif","Perawatan/Tindakan",
                "Kode Dokter","Dokter Yang Menangani","NIP","Petugas Yg Menangani",
                "Tgl.Bayar","Jam Bayar","material",
                "bhp","tarif_tindakandr","tarif_tindakanpr","kso","menejemen","Biaya"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, 
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class, 
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRawatDrPrBayar.setModel(tabModeDrPrBayar);
        //tampilDr();

        tbRawatDrPrBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDrPrBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbRawatDrPrBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(180);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else{
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }
        }
        tbRawatDrPrBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRadiologi=new DefaultTableModel(null,new Object[]{"P","Kode Periksa","Nama Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk","Tarif Dokter","Tarif Petugas","Kso","Menejemen"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==3)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRadiologi.setModel(tabModeRadiologi);        
        
        tbRadiologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 11; i++) {
            TableColumn column = tbRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(310);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else {
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }
        }
        tbRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeLaborat=new DefaultTableModel(null,new Object[]{"P","Kode Periksa","Nama Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk","Tarif Dokter","Tarif Petugas","Kso","Menejemen"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==3)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbLaborat.setModel(tabModeLaborat);        
        
        tbLaborat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLaborat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 11; i++) {
            TableColumn column = tbLaborat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(310);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else {
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }
        }
        tbLaborat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeSubLaborat=new DefaultTableModel(null,new Object[]{
                "P","ID","Nama Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk","Tarif Dokter","Tarif Petugas","Kso","Menejemen"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==3)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbSubLaborat.setModel(tabModeSubLaborat);        
        
        tbSubLaborat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSubLaborat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 11; i++) {
            TableColumn column = tbSubLaborat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(390);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else {
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }
        }
        tbSubLaborat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRadiologiBayar=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Kode Periksa","Nama Pemeriksaan","Kode Dokter",
            "Dokter Perujuk","Tgl.Bayar","Jam Bayar","bagian_rs","bhp","tarif_perujuk",
            "tarif_tindakan_dokter","tarif_tindakan_petugas","kso","menejemen","Biaya"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRadilogiBayar.setModel(tabModeRadiologiBayar);
        //tampilDr();

        tbRadilogiBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRadilogiBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbRadilogiBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else{
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }
        }
        tbRadilogiBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeLaboratBayar=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Kode Periksa","Nama Pemeriksaan","Kode Dokter",
            "Dokter Perujuk","Tgl.Bayar","Jam Bayar","Biaya"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class,
                 java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbLaboratBayar.setModel(tabModeLaboratBayar);
        //tampilDr();

        tbLaboratBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLaboratBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbLaboratBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(180);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==10){
                column.setPreferredWidth(80);
            }
        }
        tbLaboratBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeSubLaboratBayar=new DefaultTableModel(null,new Object[]{
            "P","No.Rawat","No.R.M.","Nama Pasien","Kode Periksa","ID","Nama Pemeriksaan","Kode Dokter",
            "Dokter Perujuk","Tgl.Bayar","Jam Bayar","Biaya"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbSubLaboratBayar.setModel(tabModeSubLaboratBayar);
        //tampilDr();

        tbSubLaboratBayar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSubLaboratBayar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbSubLaboratBayar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==4){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==5){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(75);
            }else if(i==10){
                column.setPreferredWidth(60);
            }else if(i==11){
                column.setPreferredWidth(80);
            }
        }
        tbSubLaboratBayar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeBilling=new DefaultTableModel(null,new Object[]{"Keterangan","Tagihan/Tindakan/Terapi","","Biaya","Jml","Total Biaya",""}){
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
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }
        }

        tbBilling.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeObat=new DefaultTableModel(null,new String[]{
                "P","Tgl.Beri","Jam Beri","No.Rawat","No.R.M.",
                "Nama Pasien","Kode Obat","Nama Obat/Alkes","Embalase",
                "Tuslah","Jml","Biaya Obat","Total","Harga Beli","Gudang"
            }){
                
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
            }
            
            Class[] types = new Class[]{
                java.lang.Boolean.class,java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Object.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbObat.setModel(tabModeObat);
        //tampilPO("");

        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 15; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(40);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(70);
                //column.setMinWidth(0);
                //column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(55);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        tbRawatDr = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbRawatDrBayar = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        panelGlass10 = new widget.panelisi();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        TPerawat = new widget.TextBox();
        Scroll5 = new widget.ScrollPane();
        tbRawatPr = new widget.Table();
        Scroll6 = new widget.ScrollPane();
        tbRawatPrBayar = new widget.Table();
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
        tbRawatDrPr = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbRawatDrPrBayar = new widget.Table();
        internalFrame5 = new widget.InternalFrame();
        Scroll10 = new widget.ScrollPane();
        tbRadiologi = new widget.Table();
        Scroll11 = new widget.ScrollPane();
        tbRadilogiBayar = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel8 = new widget.Label();
        KdDokPerujuk = new widget.TextBox();
        BtnSeekDokter1 = new widget.Button();
        TDokterPerujuk = new widget.TextBox();
        internalFrame6 = new widget.InternalFrame();
        panelGlass12 = new widget.panelisi();
        jLabel10 = new widget.Label();
        KdDokPerujuk1 = new widget.TextBox();
        BtnSeekDokter3 = new widget.Button();
        TDokterPerujuk1 = new widget.TextBox();
        internalFrame7 = new widget.InternalFrame();
        TabRawatLaborat = new javax.swing.JTabbedPane();
        Scroll12 = new widget.ScrollPane();
        tbLaborat = new widget.Table();
        Scroll14 = new widget.ScrollPane();
        tbSubLaborat = new widget.Table();
        internalFrame8 = new widget.InternalFrame();
        TabRawatLaborat1 = new javax.swing.JTabbedPane();
        Scroll15 = new widget.ScrollPane();
        tbLaboratBayar = new widget.Table();
        Scroll16 = new widget.ScrollPane();
        tbSubLaboratBayar = new widget.Table();
        internalFrame9 = new widget.InternalFrame();
        Scroll13 = new widget.ScrollPane();
        tbObat = new widget.Table();
        Scroll17 = new widget.ScrollPane();
        tbRawatDrBayar1 = new widget.Table();
        panelGlass13 = new widget.panelisi();
        chkPoli = new widget.CekBox();
        TBiaya = new widget.TextBox();
        label11 = new widget.Label();
        LabelStatus = new widget.Label();
        BtnHapusReg = new widget.Button();
        Scroll9 = new widget.ScrollPane();
        tbBilling = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Billing/Pembayaran Parsial Rawat Jalan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
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

        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-05-2018 01:02:19" }));
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
        BtnHapus.setBounds(670, 177, 100, 30);

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
        BtnKeluar.setBounds(775, 177, 100, 30);

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
        scrollPane3.setBounds(91, 70, 784, 100);

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
        jLabel7.setBounds(0, 177, 90, 23);

        TKembali.setEditable(false);
        TKembali.setText("0");
        TKembali.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TKembali.setHighlighter(null);
        panelGlass8.add(TKembali);
        TKembali.setBounds(91, 177, 230, 23);

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
        BtnSimpan.setBounds(460, 177, 100, 30);

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
        BtnNota.setBounds(565, 177, 100, 30);

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
        TabRawat.setForeground(new java.awt.Color(130,100,100));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setForeground(new java.awt.Color(130,100,100));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(null);

        jLabel5.setText("Dokter :");
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 70, 23);

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

        Scroll3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Tindakan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll3.setOpaque(true);
        Scroll3.setPreferredSize(new java.awt.Dimension(540, 500));
        Scroll3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Scroll3MouseClicked(evt);
            }
        });

        tbRawatDr.setAutoCreateRowSorter(true);
        tbRawatDr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrMouseClicked(evt);
            }
        });
        tbRawatDr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatDrKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbRawatDr);

        internalFrame2.add(Scroll3, java.awt.BorderLayout.WEST);

        Scroll4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(400, 404));

        tbRawatDrBayar.setAutoCreateRowSorter(true);
        tbRawatDrBayar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDrBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrBayarMouseClicked(evt);
            }
        });
        tbRawatDrBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatDrBayarKeyPressed(evt);
            }
        });
        Scroll4.setViewportView(tbRawatDrBayar);

        internalFrame2.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakan Dokter", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(null);

        jLabel13.setText("Petugas :");
        panelGlass10.add(jLabel13);
        jLabel13.setBounds(0, 10, 70, 23);

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

        Scroll5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Tindakan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(540, 500));

        tbRawatPr.setAutoCreateRowSorter(true);
        tbRawatPr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatPrMouseClicked(evt);
            }
        });
        tbRawatPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatPrKeyPressed(evt);
            }
        });
        Scroll5.setViewportView(tbRawatPr);

        internalFrame3.add(Scroll5, java.awt.BorderLayout.WEST);

        Scroll6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll6.setOpaque(true);
        Scroll6.setPreferredSize(new java.awt.Dimension(400, 404));

        tbRawatPrBayar.setAutoCreateRowSorter(true);
        tbRawatPrBayar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatPrBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatPrBayarMouseClicked(evt);
            }
        });
        tbRawatPrBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatPrBayarKeyPressed(evt);
            }
        });
        Scroll6.setViewportView(tbRawatPrBayar);

        internalFrame3.add(Scroll6, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakan Petugas", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass11.setLayout(null);

        jLabel14.setText("Petugas :");
        panelGlass11.add(jLabel14);
        jLabel14.setBounds(460, 10, 50, 23);

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

        Scroll7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Tindakan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll7.setOpaque(true);
        Scroll7.setPreferredSize(new java.awt.Dimension(540, 500));

        tbRawatDrPr.setAutoCreateRowSorter(true);
        tbRawatDrPr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDrPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrPrMouseClicked(evt);
            }
        });
        tbRawatDrPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatDrPrKeyPressed(evt);
            }
        });
        Scroll7.setViewportView(tbRawatDrPr);

        internalFrame4.add(Scroll7, java.awt.BorderLayout.WEST);

        Scroll8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll8.setOpaque(true);
        Scroll8.setPreferredSize(new java.awt.Dimension(400, 404));

        tbRawatDrPrBayar.setAutoCreateRowSorter(true);
        tbRawatDrPrBayar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDrPrBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrPrBayarMouseClicked(evt);
            }
        });
        tbRawatDrPrBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatDrPrBayarKeyPressed(evt);
            }
        });
        Scroll8.setViewportView(tbRawatDrPrBayar);

        internalFrame4.add(Scroll8, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakkan Dokter & Petugas", internalFrame4);

        internalFrame5.setBorder(null);
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Pemeriksaan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll10.setOpaque(true);
        Scroll10.setPreferredSize(new java.awt.Dimension(540, 500));

        tbRadiologi.setAutoCreateRowSorter(true);
        tbRadiologi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRadiologi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRadiologiMouseClicked(evt);
            }
        });
        tbRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRadiologiKeyPressed(evt);
            }
        });
        Scroll10.setViewportView(tbRadiologi);

        internalFrame5.add(Scroll10, java.awt.BorderLayout.WEST);

        Scroll11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll11.setOpaque(true);
        Scroll11.setPreferredSize(new java.awt.Dimension(400, 404));

        tbRadilogiBayar.setAutoCreateRowSorter(true);
        tbRadilogiBayar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRadilogiBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRadilogiBayarMouseClicked(evt);
            }
        });
        tbRadilogiBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRadilogiBayarKeyPressed(evt);
            }
        });
        Scroll11.setViewportView(tbRadilogiBayar);

        internalFrame5.add(Scroll11, java.awt.BorderLayout.CENTER);

        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(null);

        jLabel8.setText("Perujuk :");
        panelGlass9.add(jLabel8);
        jLabel8.setBounds(0, 10, 70, 23);

        KdDokPerujuk.setHighlighter(null);
        KdDokPerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokPerujukKeyPressed(evt);
            }
        });
        panelGlass9.add(KdDokPerujuk);
        KdDokPerujuk.setBounds(74, 10, 130, 23);

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

        TDokterPerujuk.setEditable(false);
        TDokterPerujuk.setHighlighter(null);
        panelGlass9.add(TDokterPerujuk);
        TDokterPerujuk.setBounds(206, 10, 641, 23);

        internalFrame5.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Radiologi", internalFrame5);

        internalFrame6.setBorder(null);
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass12.setLayout(null);

        jLabel10.setText("Perujuk :");
        panelGlass12.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        KdDokPerujuk1.setHighlighter(null);
        KdDokPerujuk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokPerujuk1KeyPressed(evt);
            }
        });
        panelGlass12.add(KdDokPerujuk1);
        KdDokPerujuk1.setBounds(74, 10, 130, 23);

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

        TDokterPerujuk1.setEditable(false);
        TDokterPerujuk1.setHighlighter(null);
        panelGlass12.add(TDokterPerujuk1);
        TDokterPerujuk1.setBounds(206, 10, 641, 23);

        internalFrame6.add(panelGlass12, java.awt.BorderLayout.PAGE_START);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Pemeriksaan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawatLaborat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatLaborat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(234, 250, 233)));
        TabRawatLaborat.setForeground(new java.awt.Color(130,100,100));
        TabRawatLaborat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatLaborat.setPreferredSize(new java.awt.Dimension(540, 500));
        TabRawatLaborat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatLaboratMouseClicked(evt);
            }
        });

        Scroll12.setOpaque(true);
        Scroll12.setPreferredSize(new java.awt.Dimension(540, 500));

        tbLaborat.setAutoCreateRowSorter(true);
        tbLaborat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLaborat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLaboratMouseClicked(evt);
            }
        });
        tbLaborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLaboratKeyPressed(evt);
            }
        });
        Scroll12.setViewportView(tbLaborat);

        TabRawatLaborat.addTab("Pemeriksaan", Scroll12);

        Scroll14.setOpaque(true);
        Scroll14.setPreferredSize(new java.awt.Dimension(540, 500));

        tbSubLaborat.setAutoCreateRowSorter(true);
        tbSubLaborat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbSubLaborat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSubLaboratMouseClicked(evt);
            }
        });
        tbSubLaborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbSubLaboratKeyPressed(evt);
            }
        });
        Scroll14.setViewportView(tbSubLaborat);

        TabRawatLaborat.addTab("Sub Pemeriksaan", Scroll14);

        internalFrame7.add(TabRawatLaborat, java.awt.BorderLayout.CENTER);

        internalFrame6.add(internalFrame7, java.awt.BorderLayout.WEST);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawatLaborat1.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatLaborat1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawatLaborat1.setForeground(new java.awt.Color(130,100,100));
        TabRawatLaborat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawatLaborat1.setPreferredSize(new java.awt.Dimension(540, 500));
        TabRawatLaborat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatLaborat1MouseClicked(evt);
            }
        });

        Scroll15.setOpaque(true);
        Scroll15.setPreferredSize(new java.awt.Dimension(540, 500));

        tbLaboratBayar.setAutoCreateRowSorter(true);
        tbLaboratBayar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLaboratBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLaboratBayarMouseClicked(evt);
            }
        });
        tbLaboratBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLaboratBayarKeyPressed(evt);
            }
        });
        Scroll15.setViewportView(tbLaboratBayar);

        TabRawatLaborat1.addTab("Pemeriksaan", Scroll15);

        Scroll16.setOpaque(true);
        Scroll16.setPreferredSize(new java.awt.Dimension(540, 500));

        tbSubLaboratBayar.setAutoCreateRowSorter(true);
        tbSubLaboratBayar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbSubLaboratBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSubLaboratBayarMouseClicked(evt);
            }
        });
        tbSubLaboratBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbSubLaboratBayarKeyPressed(evt);
            }
        });
        Scroll16.setViewportView(tbSubLaboratBayar);

        TabRawatLaborat1.addTab("Sub Pemeriksaan", Scroll16);

        internalFrame8.add(TabRawatLaborat1, java.awt.BorderLayout.CENTER);

        internalFrame6.add(internalFrame8, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Laboratorium", internalFrame6);

        internalFrame9.setBorder(null);
        internalFrame9.setForeground(new java.awt.Color(130,100,100));
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Daftar Obat ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll13.setOpaque(true);
        Scroll13.setPreferredSize(new java.awt.Dimension(540, 500));
        Scroll13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Scroll13MouseClicked(evt);
            }
        });

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll13.setViewportView(tbObat);

        internalFrame9.add(Scroll13, java.awt.BorderLayout.WEST);

        Scroll17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), " Sudah Dibayar ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130,100,100))); // NOI18N
        Scroll17.setOpaque(true);
        Scroll17.setPreferredSize(new java.awt.Dimension(400, 404));

        tbRawatDrBayar1.setAutoCreateRowSorter(true);
        tbRawatDrBayar1.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDrBayar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrBayar1MouseClicked(evt);
            }
        });
        tbRawatDrBayar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatDrBayar1KeyPressed(evt);
            }
        });
        Scroll17.setViewportView(tbRawatDrBayar1);

        internalFrame9.add(Scroll17, java.awt.BorderLayout.CENTER);

        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass13.setLayout(null);

        chkPoli.setText("Poliklinik :");
        chkPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPoli.setOpaque(false);
        chkPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPoliActionPerformed(evt);
            }
        });
        panelGlass13.add(chkPoli);
        chkPoli.setBounds(15, 10, 280, 23);

        TBiaya.setEditable(false);
        TBiaya.setHighlighter(null);
        TBiaya.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass13.add(TBiaya);
        TBiaya.setBounds(410, 10, 100, 23);

        label11.setText("Biaya Registrasi :");
        label11.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass13.add(label11);
        label11.setBounds(317, 10, 90, 23);

        LabelStatus.setText("Status : Belum Bayar");
        LabelStatus.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass13.add(LabelStatus);
        LabelStatus.setBounds(510, 10, 120, 23);

        BtnHapusReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusReg.setMnemonic('H');
        BtnHapusReg.setToolTipText("Alt+H");
        BtnHapusReg.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapusReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusRegActionPerformed(evt);
            }
        });
        panelGlass13.add(BtnHapusReg);
        BtnHapusReg.setBounds(670, 10, 28, 23);

        internalFrame9.add(panelGlass13, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Obat & Registrasi", internalFrame9);

        Scroll9.setOpaque(true);
        Scroll9.setPreferredSize(new java.awt.Dimension(440, 404));

        tbBilling.setAutoCreateRowSorter(true);
        tbBilling.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        if((TabRawat.getSelectedIndex()==0)||(TabRawat.getSelectedIndex()==1)||(TabRawat.getSelectedIndex()==2)){
            tampil();
        }else if(TabRawat.getSelectedIndex()==3){
            tampilradiologi();
        }else if(TabRawat.getSelectedIndex()==4){
            TabRawatLaboratMouseClicked(null);
        }else if(TabRawat.getSelectedIndex()==5){
            tampilObat();
        }else if(TabRawat.getSelectedIndex()==6){
            tampilbilling();
        }
        tampilBayar();        
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbRawatDrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrMouseClicked
        if(TabModeTindakanDr.getRowCount()!=0){
            try {
                tampilbilling();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbRawatDrMouseClicked

    private void tbRawatDrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatDrKeyPressed

    private void TCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariTindakanKeyPressed
        BtnCariTindakanActionPerformed(null);
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

    private void tbRawatDrBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrBayarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatDrBayarMouseClicked

    private void tbRawatDrBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatDrBayarKeyPressed

    private void tbRawatPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatPrMouseClicked
       if(TabModeTindakanPr.getRowCount()!=0){
            try {
                tampilbilling();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbRawatPrMouseClicked

    private void tbRawatPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatPrKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatPrKeyPressed

    private void tbRawatPrBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatPrBayarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatPrBayarMouseClicked

    private void tbRawatPrBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatPrBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatPrBayarKeyPressed

    private void tbRawatDrPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrPrMouseClicked
        if(TabModeTindakanDrPr.getRowCount()!=0){
            try {
                tampilbilling();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbRawatDrPrMouseClicked

    private void tbRawatDrPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrPrKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatDrPrKeyPressed

    private void tbRawatDrPrBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrPrBayarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatDrPrBayarMouseClicked

    private void tbRawatDrPrBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrPrBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatDrPrBayarKeyPressed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed

    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose(); 
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed

    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed

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
        if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
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
                Sequel.queryu2("delete from temporary_bayar_ralan where temp9='"+var.getkode()+"'"); 
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
                    
                    //System.out.println("Data : "+tabModeBilling.getValueAt(i,4).toString().replaceAll("'","`"));
                    //"Keterangan","Tagihan/Tindakan/Terapi","","Biaya","Jml","Total Biaya",""
                    Sequel.menyimpan("temporary_bayar_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",18,new String[]{
                        "0",tabModeBilling.getValueAt(i,0).toString().replaceAll("'","`"),
                        tabModeBilling.getValueAt(i,1).toString().replaceAll("'","`"),
                        tabModeBilling.getValueAt(i,2).toString().replaceAll("'","`"),
                        biaya,jmls,tambahan,totals,
                        tabModeBilling.getValueAt(i,6).toString().replaceAll("'","`"),
                        var.getkode(),"","","","","","","","",""
                    });                                                    
                }

                Sequel.menyimpan("temporary_bayar_ralan","'0','TOTAL TAGIHAN',':','','','','','"+TtlSemua.getText()+"','Tagihan','"+var.getkode()+"','','','','','','','',''","Tagihan"); 
                Sequel.menyimpan("temporary_bayar_ralan","'0','PPN',':','','','','','"+Valid.SetAngka(besarppn)+"','Tagihan','"+var.getkode()+"','','','','','','','',''","Tagihan"); 
                Sequel.menyimpan("temporary_bayar_ralan","'0','TOTAL BAYAR',':','','','','','"+TagihanPPN.getText()+"','Tagihan','"+var.getkode()+"','','','','','','','',''","Tagihan"); 
                
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
                        Valid.panggilUrl("billing/LaporanBilling9.php?petugas="+var.getkode().replaceAll(" ","_")+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                    }else if(i==2){
                        Valid.panggilUrl("billing/LaporanBilling5.php?petugas="+var.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(reg_periksa.no_rawat) from reg_periksa "+
                                "where reg_periksa.kd_pj='"+kd_pj+"' and reg_periksa.tgl_registrasi like '%"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,7)+"%'")+"/RJ/"+kd_pj+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(5,7)+"/"+Valid.SetTgl(DTPTgl.getSelectedItem()+"").substring(0,4));
                    }else if(i==3){
                        Valid.panggilUrl("billing/LaporanBilling9.php?petugas="+var.getkode().replaceAll(" ","_")+"&tanggal="+DTPTgl.getSelectedItem().toString().replaceAll(" ","_"));
                        Valid.panggilUrl("billing/LaporanBilling5.php?petugas="+var.getkode().replaceAll(" ","_")+"&nonota="+Sequel.cariIsi("select count(reg_periksa.no_rawat) from reg_periksa "+
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilAkunBayar();
        TabRawatMouseClicked(null);
        tampilBayar();
    }//GEN-LAST:event_formWindowOpened

    private void tbRadiologiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadiologiMouseClicked
        if(tabModeRadiologi.getRowCount()!=0){
            try {
                tampilbilling();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbRadiologiMouseClicked

    private void tbRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRadiologiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiKeyPressed

    private void tbRadilogiBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadilogiBayarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadilogiBayarMouseClicked

    private void tbRadilogiBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRadilogiBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadilogiBayarKeyPressed

    private void KdDokPerujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokPerujukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokPerujukKeyPressed

    private void BtnSeekDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter1ActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter1ActionPerformed

    private void tbLaboratMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLaboratMouseClicked
        if(tabModeLaborat.getRowCount()!=0){
            try {
                tampilbilling();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbLaboratMouseClicked

    private void tbLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLaboratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLaboratKeyPressed

    private void KdDokPerujuk1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokPerujuk1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokPerujuk1KeyPressed

    private void BtnSeekDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokter3ActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeekDokter3ActionPerformed

    private void TabRawatLaboratMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatLaboratMouseClicked
        if(TabRawatLaborat.getSelectedIndex()==0){
            tampillaborat();
        }else if(TabRawatLaborat.getSelectedIndex()==1){
            tampilsublaborat();
        }
    }//GEN-LAST:event_TabRawatLaboratMouseClicked

    private void tbSubLaboratMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSubLaboratMouseClicked
        if(tabModeSubLaborat.getRowCount()!=0){
            try {
                tampilbilling();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbSubLaboratMouseClicked

    private void tbSubLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSubLaboratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbSubLaboratKeyPressed

    private void tbLaboratBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLaboratBayarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLaboratBayarMouseClicked

    private void tbLaboratBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLaboratBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLaboratBayarKeyPressed

    private void tbSubLaboratBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSubLaboratBayarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbSubLaboratBayarMouseClicked

    private void tbSubLaboratBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSubLaboratBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbSubLaboratBayarKeyPressed

    private void TabRawatLaborat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatLaborat1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabRawatLaborat1MouseClicked

    private void Scroll3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Scroll3MouseClicked
        if(TabModeTindakanDr.getRowCount()!=0){
            try {
                tampilbilling();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_Scroll3MouseClicked

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabModeObat.getRowCount()!=0){
            try {
                tampilbilling();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbObatKeyPressed

    private void Scroll13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Scroll13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Scroll13MouseClicked

    private void tbRawatDrBayar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrBayar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatDrBayar1MouseClicked

    private void tbRawatDrBayar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrBayar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRawatDrBayar1KeyPressed

    private void chkPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPoliActionPerformed
        tampilbilling();
    }//GEN-LAST:event_chkPoliActionPerformed

    private void BtnHapusRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusRegActionPerformed
        
    }//GEN-LAST:event_BtnHapusRegActionPerformed

    private void TtlSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TtlSemuaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TtlSemuaKeyPressed

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
    private widget.Button BtnHapusReg;
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
    private widget.TextBox KdDokPerujuk;
    private widget.TextBox KdDokPerujuk1;
    private widget.Label LabelStatus;
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
    private widget.TextBox TBiaya;
    private widget.TextBox TCari;
    private widget.TextBox TCariTindakan;
    private widget.TextBox TDokter;
    private widget.TextBox TDokter2;
    private widget.TextBox TDokterPerujuk;
    private widget.TextBox TDokterPerujuk1;
    public widget.TextBox TKembali;
    private widget.TextBox TNoRM;
    public widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPerawat;
    private widget.TextBox TPerawat2;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRawatLaborat;
    private javax.swing.JTabbedPane TabRawatLaborat1;
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
    private widget.ScrollPane scrollPane3;
    private widget.Table tbAkunBayar;
    private widget.Table tbBilling;
    private widget.Table tbLaborat;
    private widget.Table tbLaboratBayar;
    private widget.Table tbObat;
    private widget.Table tbRadilogiBayar;
    private widget.Table tbRadiologi;
    private widget.Table tbRawatDr;
    private widget.Table tbRawatDrBayar;
    private widget.Table tbRawatDrBayar1;
    private widget.Table tbRawatDrPr;
    private widget.Table tbRawatDrPrBayar;
    private widget.Table tbRawatPr;
    private widget.Table tbRawatPrBayar;
    private widget.Table tbSubLaborat;
    private widget.Table tbSubLaboratBayar;
    // End of variables declaration//GEN-END:variables

    public void setNoRm(String norwt,String kodedokter, String namadokter,String KodePoli) {
        TNoRw.setText(norwt);
        this.kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt);
        this.kd_poli=KodePoli;
        KdDok.setText(kodedokter);
        KdDok2.setText(kodedokter);
        TDokter.setText(namadokter);
        TDokter2.setText(namadokter);
        isRawat();
        isPsien();  
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
    
    private void tampil() {
        try{     
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    jml=0;
                    for(i=0;i<tbRawatDr.getRowCount();i++){
                        if(tbRawatDr.getValueAt(i,0).toString().equals("true")){
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
                    tglpermintaan=null;
                    tglpermintaan=new String[jml];
                    jampermintaan=null;
                    jampermintaan=new String[jml];

                    index=0;        
                    for(i=0;i<tbRawatDr.getRowCount();i++){
                        if(tbRawatDr.getValueAt(i,0).toString().equals("true")){
                            pilih[index]=true;
                            kode[index]=tbRawatDr.getValueAt(i,1).toString();
                            nama[index]=tbRawatDr.getValueAt(i,2).toString();
                            kategori[index]=tbRawatDr.getValueAt(i,3).toString();
                            totaltnd[index]=Double.parseDouble(tbRawatDr.getValueAt(i,4).toString());
                            bagianrs[index]=Double.parseDouble(tbRawatDr.getValueAt(i,5).toString());
                            bhp[index]=Double.parseDouble(tbRawatDr.getValueAt(i,6).toString());
                            jmdokter[index]=Double.parseDouble(tbRawatDr.getValueAt(i,7).toString());
                            jmperawat[index]=Double.parseDouble(tbRawatDr.getValueAt(i,8).toString());  
                            kso[index]=Double.parseDouble(tbRawatDr.getValueAt(i,9).toString());
                            menejemen[index]=Double.parseDouble(tbRawatDr.getValueAt(i,10).toString()); 
                            tglpermintaan[index]=tbRawatDr.getValueAt(i,11).toString();
                            jampermintaan[index]=tbRawatDr.getValueAt(i,12).toString(); 
                            index++;
                        }
                    }       

                    Valid.tabelKosong(TabModeTindakanDr);

                    for(i=0;i<jml;i++){
                        TabModeTindakanDr.addRow(new Object[] {
                            pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i],tglpermintaan[i],jampermintaan[i]
                        });
                    }
                    break;
                case 1:
                    jml=0;
                    for(i=0;i<tbRawatPr.getRowCount();i++){
                        if(tbRawatPr.getValueAt(i,0).toString().equals("true")){
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
                    tglpermintaan=null;
                    tglpermintaan=new String[jml];
                    jampermintaan=null;
                    jampermintaan=new String[jml];

                    index=0;        
                    for(i=0;i<tbRawatPr.getRowCount();i++){
                        if(tbRawatPr.getValueAt(i,0).toString().equals("true")){
                            pilih[index]=true;
                            kode[index]=tbRawatPr.getValueAt(i,1).toString();
                            nama[index]=tbRawatPr.getValueAt(i,2).toString();
                            kategori[index]=tbRawatPr.getValueAt(i,3).toString();
                            totaltnd[index]=Double.parseDouble(tbRawatPr.getValueAt(i,4).toString());
                            bagianrs[index]=Double.parseDouble(tbRawatPr.getValueAt(i,5).toString());
                            bhp[index]=Double.parseDouble(tbRawatPr.getValueAt(i,6).toString());
                            jmdokter[index]=Double.parseDouble(tbRawatPr.getValueAt(i,7).toString());
                            jmperawat[index]=Double.parseDouble(tbRawatPr.getValueAt(i,8).toString());  
                            kso[index]=Double.parseDouble(tbRawatPr.getValueAt(i,9).toString());
                            menejemen[index]=Double.parseDouble(tbRawatPr.getValueAt(i,10).toString());  
                            tglpermintaan[index]=tbRawatPr.getValueAt(i,11).toString();
                            jampermintaan[index]=tbRawatPr.getValueAt(i,12).toString(); 
                            index++;
                        }
                    }       

                    Valid.tabelKosong(TabModeTindakanPr);

                    for(i=0;i<jml;i++){
                        TabModeTindakanPr.addRow(new Object[] {
                            pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i],tglpermintaan[i],jampermintaan[i]
                        });
                    }
                    break;
                case 2:
                    jml=0;
                    for(i=0;i<tbRawatDrPr.getRowCount();i++){
                        if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){
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
                    tglpermintaan=null;
                    tglpermintaan=new String[jml];
                    jampermintaan=null;
                    jampermintaan=new String[jml];

                    index=0;        
                    for(i=0;i<tbRawatDrPr.getRowCount();i++){
                        if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){
                            pilih[index]=true;
                            kode[index]=tbRawatDrPr.getValueAt(i,1).toString();
                            nama[index]=tbRawatDrPr.getValueAt(i,2).toString();
                            kategori[index]=tbRawatDrPr.getValueAt(i,3).toString();
                            totaltnd[index]=Double.parseDouble(tbRawatDrPr.getValueAt(i,4).toString());
                            bagianrs[index]=Double.parseDouble(tbRawatDrPr.getValueAt(i,5).toString());
                            bhp[index]=Double.parseDouble(tbRawatDrPr.getValueAt(i,6).toString());
                            jmdokter[index]=Double.parseDouble(tbRawatDrPr.getValueAt(i,7).toString());
                            jmperawat[index]=Double.parseDouble(tbRawatDrPr.getValueAt(i,8).toString());  
                            kso[index]=Double.parseDouble(tbRawatDrPr.getValueAt(i,9).toString());
                            menejemen[index]=Double.parseDouble(tbRawatDrPr.getValueAt(i,10).toString());  
                            tglpermintaan[index]=tbRawatDrPr.getValueAt(i,11).toString();
                            jampermintaan[index]=tbRawatDrPr.getValueAt(i,12).toString(); 
                            index++;
                        }
                    }       

                    Valid.tabelKosong(TabModeTindakanDrPr);

                    for(i=0;i<jml;i++){
                        TabModeTindakanDrPr.addRow(new Object[] {
                            pilih[i],kode[i],nama[i],kategori[i],totaltnd[i],bagianrs[i],bhp[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i],tglpermintaan[i],jampermintaan[i]
                        });
                    }
                    break;
            }    
            
            pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            pstindakan2=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_pj=? or jns_perawatan.kd_pj='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");        
            pstindakan3=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and (jns_perawatan.kd_poli=? or jns_perawatan.kd_poli='-') and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");     
            pstindakan4=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr,jns_perawatan.total_byrdrpr,jns_perawatan.bhp,jns_perawatan.material,"+
                   "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.kso,jns_perawatan.menejemen from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.status='1' and jns_perawatan.kd_jenis_prw like ? or "+
                    "jns_perawatan.status='1' and jns_perawatan.nm_perawatan like ? or "+
                    "jns_perawatan.status='1' and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan "); 
            try {
                if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("Yes")){
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
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("Yes")){
                    pstindakan2.setString(1,kd_pj.trim());
                    pstindakan2.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan2.setString(3,kd_pj.trim());
                    pstindakan2.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan2.setString(5,kd_pj.trim());
                    pstindakan2.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan2.executeQuery();
                }else if(poli_ralan.equals("Yes")&&cara_bayar_ralan.equals("No")){
                    pstindakan3.setString(1,kd_poli.trim());
                    pstindakan3.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan3.setString(3,kd_poli.trim());
                    pstindakan3.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan3.setString(5,kd_poli.trim());
                    pstindakan3.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan3.executeQuery();
                }else if(poli_ralan.equals("No")&&cara_bayar_ralan.equals("No")){
                    pstindakan4.setString(1,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan4.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pstindakan4.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                    rstindakan=pstindakan4.executeQuery();
                }
                
                switch (TabRawat.getSelectedIndex()) {
                    case 0:
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
                        break;
                    case 1:
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
                        break;
                    case 2:
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
                        break; 
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
                if(pstindakan2 != null){
                    pstindakan2.close();
                }
                if(pstindakan3 != null){
                    pstindakan3.close();
                }
                if(pstindakan4 != null){
                    pstindakan4.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getbilling_parsial());
    }
    
    private void tampilBayar() {        
        /*try {
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    Valid.tabelKosong(tabModeDrBayar);
                    psBayarDr=koneksi.prepareStatement(
                        "select permintaan_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                        "permintaan_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,permintaan_jl_dr.kd_dokter,"+
                        "dokter.nm_dokter,permintaan_jl_dr.tgl_bayar,permintaan_jl_dr.jam_bayar,"+
                        "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                        "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                        "from pasien inner join reg_periksa inner join "+
                        "jns_perawatan inner join dokter inner join permintaan_jl_dr inner join rawat_jl_dr "+
                        "on permintaan_jl_dr.no_rawat=reg_periksa.no_rawat "+
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "and permintaan_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                        "and permintaan_jl_dr.kd_dokter=dokter.kd_dokter "+
                        "and permintaan_jl_dr.no_rawat=rawat_jl_dr.no_rawat "+
                        "and permintaan_jl_dr.kd_jenis_prw=rawat_jl_dr.kd_jenis_prw "+
                        "and rawat_jl_dr.tgl_perawatan=permintaan_jl_dr.tgl_bayar "+
                        "and rawat_jl_dr.jam_rawat=permintaan_jl_dr.jam_bayar "+
                        "where permintaan_jl_dr.no_rawat=?");
                    try {
                        psBayarDr.setString(1,TNoRw.getText());
                        rsbayar=psBayarDr.executeQuery();
                        while(rsbayar.next()){
                            tabModeDrBayar.addRow(new Object[]{
                                false,rsbayar.getString("no_rawat"),rsbayar.getString("no_rkm_medis"),
                                rsbayar.getString("nm_pasien"),rsbayar.getString("kd_jenis_prw"),
                                rsbayar.getString("nm_perawatan"),rsbayar.getString("kd_dokter"),
                                rsbayar.getString("nm_dokter"),rsbayar.getString("tgl_bayar"),
                                rsbayar.getString("jam_bayar"),rsbayar.getDouble("material"),
                                rsbayar.getDouble("bhp"),rsbayar.getDouble("tarif_tindakandr"),
                                rsbayar.getDouble("kso"),rsbayar.getDouble("menejemen"),
                                rsbayar.getDouble("biaya_rawat")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("keuangan.DlgBilingParsialRalan.tampilBayar() : "+e);
                    } finally{
                        if(rsbayar!=null){
                            rsbayar.close();
                        }
                        if(psBayarDr!=null){
                            psBayarDr.close();
                        }                
                    }
                    break;
                case 1:
                    Valid.tabelKosong(tabModePrBayar);
                    psBayarPr=koneksi.prepareStatement(
                        "select permintaan_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                        "permintaan_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,permintaan_jl_pr.nip,"+
                        "petugas.nama,permintaan_jl_pr.tgl_bayar,permintaan_jl_pr.jam_bayar,"+
                        "permintaan_jl_pr.biaya_rawat from pasien inner join reg_periksa inner join "+
                        "jns_perawatan inner join petugas inner join permintaan_jl_pr "+
                        "on permintaan_jl_pr.no_rawat=reg_periksa.no_rawat "+
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "and permintaan_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                        "and permintaan_jl_pr.nip=petugas.nip where permintaan_jl_pr.no_rawat=?");
                    try {
                        psBayarPr.setString(1,TNoRw.getText());
                        rsbayar=psBayarPr.executeQuery();
                        while(rsbayar.next()){
                            tabModePrBayar.addRow(new Object[]{
                                false,rsbayar.getString("no_rawat"),rsbayar.getString("no_rkm_medis"),
                                rsbayar.getString("nm_pasien"),rsbayar.getString("kd_jenis_prw"),
                                rsbayar.getString("nm_perawatan"),rsbayar.getString("nip"),
                                rsbayar.getString("nama"),rsbayar.getString("tgl_bayar"),
                                rsbayar.getString("jam_bayar"),rsbayar.getDouble("biaya_rawat")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("keuangan.DlgBilingParsialRalan.tampilBayar() : "+e);
                    } finally{
                        if(rsbayar!=null){
                            rsbayar.close();
                        }
                        if(psBayarPr!=null){
                            psBayarPr.close();
                        }                
                    }
                    break;
                case 2:
                    Valid.tabelKosong(tabModeDrPrBayar);
                    psBayarDrPr=koneksi.prepareStatement(
                        "select permintaan_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                        "permintaan_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,permintaan_jl_drpr.kd_dokter,"+
                        "dokter.nm_dokter,permintaan_jl_drpr.nip,"+
                        "petugas.nama,permintaan_jl_drpr.tgl_bayar,permintaan_jl_drpr.jam_bayar,"+
                        "permintaan_jl_drpr.biaya_rawat from pasien inner join reg_periksa inner join "+
                        "jns_perawatan inner join petugas inner join permintaan_jl_drpr inner join dokter "+
                        "on permintaan_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "and permintaan_jl_drpr.kd_dokter=dokter.kd_dokter "+
                        "and permintaan_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                        "and permintaan_jl_drpr.nip=petugas.nip where permintaan_jl_drpr.no_rawat=?");
                    try {
                        psBayarDrPr.setString(1,TNoRw.getText());
                        rsbayar=psBayarDrPr.executeQuery();
                        while(rsbayar.next()){
                            tabModeDrPrBayar.addRow(new Object[]{
                                false,rsbayar.getString("no_rawat"),rsbayar.getString("no_rkm_medis"),
                                rsbayar.getString("nm_pasien"),rsbayar.getString("kd_jenis_prw"),
                                rsbayar.getString("nm_perawatan"),rsbayar.getString("kd_dokter"),
                                rsbayar.getString("nm_dokter"),rsbayar.getString("nip"),
                                rsbayar.getString("nama"),rsbayar.getString("tgl_bayar"),
                                rsbayar.getString("jam_bayar"),rsbayar.getDouble("biaya_rawat")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("keuangan.DlgBilingParsialRalan.tampilBayar() : "+e);
                    } finally{
                        if(rsbayar!=null){
                            rsbayar.close();
                        }
                        if(psBayarDrPr!=null){
                            psBayarDrPr.close();
                        }                
                    }
                    break;
                case 3:
                    Valid.tabelKosong(tabModeRadiologiBayar);
                    psBayarradiologi=koneksi.prepareStatement(
                        "select permintaan_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                        "permintaan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,permintaan_radiologi.dokter_perujuk,"+
                        "dokter.nm_dokter,permintaan_radiologi.tgl_bayar,permintaan_radiologi.jam_bayar,"+
                        "permintaan_radiologi.biaya from pasien inner join reg_periksa inner join "+
                        "jns_perawatan_radiologi inner join permintaan_radiologi inner join dokter "+
                        "on permintaan_radiologi.no_rawat=reg_periksa.no_rawat "+
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "and permintaan_radiologi.dokter_perujuk=dokter.kd_dokter "+
                        "and permintaan_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                        "where permintaan_radiologi.no_rawat=?");
                    try {
                        psBayarradiologi.setString(1,TNoRw.getText());
                        rsbayar=psBayarradiologi.executeQuery();
                        while(rsbayar.next()){
                            tabModeRadiologiBayar.addRow(new Object[]{
                                false,rsbayar.getString("no_rawat"),rsbayar.getString("no_rkm_medis"),
                                rsbayar.getString("nm_pasien"),rsbayar.getString("kd_jenis_prw"),
                                rsbayar.getString("nm_perawatan"),rsbayar.getString("dokter_perujuk"),
                                rsbayar.getString("nm_dokter"),rsbayar.getString("tgl_bayar"),
                                rsbayar.getString("jam_bayar"),rsbayar.getDouble("biaya")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("keuangan.DlgBilingParsialRalan.tampilBayar() : "+e);
                    } finally{
                        if(rsbayar!=null){
                            rsbayar.close();
                        }
                        if(psBayarradiologi!=null){
                            psBayarradiologi.close();
                        }                
                    }
                    break;
                default:
                    break;
            } 
                    
        } catch (Exception e) {
            System.out.println("keuangan.DlgBilingParsialRalan.tampilBayar() : "+e);
        } */
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
                            }
                            rsdokterralan.beforeFirst();
                            while(rsdokterralan.next()){
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
                    
                    if(chkPoli.isSelected()==true){
                        tabModeBilling.addRow(new Object[]{"Registrasi",":","",null,null,Double.parseDouble(TBiaya.getText()),"Registrasi"});
                    }
                    
                    jml=0;
                    for(i=0;i<tbRawatDr.getRowCount();i++){
                        if(tbRawatDr.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    for(i=0;i<tbRawatPr.getRowCount();i++){
                        if(tbRawatPr.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    for(i=0;i<tbRawatDrPr.getRowCount();i++){
                        if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    for(i=0;i<tbRadiologi.getRowCount();i++){
                        if(tbRadiologi.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    for(i=0;i<tbLaborat.getRowCount();i++){
                        if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    for(i=0;i<tbSubLaborat.getRowCount();i++){
                        if(tbSubLaborat.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    
                    if(jml>0){
                        tabModeBilling.addRow(new Object[]{"Tindakan",":","",null,null,null,"Ralan Dokter"});
                    }
                    
                    Jasa_Medik_Dokter_Tindakan_Ralan=0;
                    Jasa_Medik_Paramedis_Tindakan_Ralan=0;
                    KSO_Tindakan_Ralan=0;
                    Jasa_Medik_Dokter_Laborat_Ralan=0;
                    Jasa_Medik_Petugas_Laborat_Ralan=0;
                    Kso_Laborat_Ralan=0;
                    Persediaan_Laborat_Rawat_Jalan=0;
                    Jasa_Medik_Dokter_Radiologi_Ralan=0;
                    Jasa_Medik_Petugas_Radiologi_Ralan=0;
                    Kso_Radiologi_Ralan=0;
                    Persediaan_Radiologi_Rawat_Jalan=0;
                    Obat_Rawat_Jalan=0;
                    
                    for(i=0;i<tbRawatDr.getRowCount();i++){
                        if(tbRawatDr.getValueAt(i,0).toString().equals("true")){
                            Jasa_Medik_Dokter_Tindakan_Ralan=Jasa_Medik_Dokter_Tindakan_Ralan+Double.parseDouble(tbRawatDr.getValueAt(i,7).toString());
                            KSO_Tindakan_Ralan=KSO_Tindakan_Ralan+Double.parseDouble(tbRawatDr.getValueAt(i,9).toString());
                            tabModeBilling.addRow(new Object[]{
                                "",tbRawatDr.getValueAt(i,2).toString(),":",
                                Double.parseDouble(tbRawatDr.getValueAt(i,4).toString()),1,
                                Double.parseDouble(tbRawatDr.getValueAt(i,4).toString()),"Ralan Dokter"
                            });
                        }
                    }
                    
                    for(i=0;i<tbRawatPr.getRowCount();i++){
                        if(tbRawatPr.getValueAt(i,0).toString().equals("true")){
                            Jasa_Medik_Paramedis_Tindakan_Ralan=Jasa_Medik_Paramedis_Tindakan_Ralan+Double.parseDouble(tbRawatDr.getValueAt(i,8).toString());
                            KSO_Tindakan_Ralan=KSO_Tindakan_Ralan+Double.parseDouble(tbRawatDr.getValueAt(i,9).toString());
                            tabModeBilling.addRow(new Object[]{
                                "",tbRawatPr.getValueAt(i,2).toString(),":",
                                Double.parseDouble(tbRawatPr.getValueAt(i,4).toString()),1,
                                Double.parseDouble(tbRawatPr.getValueAt(i,4).toString()),"Ralan Paramedis"
                            });
                        }
                    }
                    
                    for(i=0;i<tbRawatDrPr.getRowCount();i++){
                        if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){
                            Jasa_Medik_Dokter_Tindakan_Ralan=Jasa_Medik_Dokter_Tindakan_Ralan+Double.parseDouble(tbRawatDr.getValueAt(i,7).toString());
                            Jasa_Medik_Paramedis_Tindakan_Ralan=Jasa_Medik_Paramedis_Tindakan_Ralan+Double.parseDouble(tbRawatDr.getValueAt(i,8).toString());
                            KSO_Tindakan_Ralan=KSO_Tindakan_Ralan+Double.parseDouble(tbRawatDr.getValueAt(i,9).toString());
                            tabModeBilling.addRow(new Object[]{
                                "",tbRawatDrPr.getValueAt(i,2).toString(),":",
                                Double.parseDouble(tbRawatDrPr.getValueAt(i,4).toString()),1,
                                Double.parseDouble(tbRawatDrPr.getValueAt(i,4).toString()),"Ralan Dokter Paramedis"
                            });
                        }
                    }
                    
                    for(i=0;i<tbRadiologi.getRowCount();i++){
                        if(tbRadiologi.getValueAt(i,0).toString().equals("true")){
                            Jasa_Medik_Dokter_Radiologi_Ralan=Jasa_Medik_Dokter_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,6).toString())+Double.parseDouble(tbRadiologi.getValueAt(i,7).toString());
                            Jasa_Medik_Petugas_Radiologi_Ralan=Jasa_Medik_Petugas_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,8).toString());
                            Kso_Radiologi_Ralan=Kso_Radiologi_Ralan+Double.parseDouble(tbRadiologi.getValueAt(i,9).toString());
                            Persediaan_Radiologi_Rawat_Jalan=Persediaan_Radiologi_Rawat_Jalan+Double.parseDouble(tbRadiologi.getValueAt(i,5).toString());
                            tabModeBilling.addRow(new Object[]{
                                "",tbRadiologi.getValueAt(i,2).toString(),":",
                                Double.parseDouble(tbRadiologi.getValueAt(i,3).toString()),1,
                                Double.parseDouble(tbRadiologi.getValueAt(i,3).toString()),"Radiologi"
                            });
                        }
                    }
                    
                    for(i=0;i<tbLaborat.getRowCount();i++){
                        if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                            Jasa_Medik_Dokter_Laborat_Ralan=Jasa_Medik_Dokter_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,6).toString())+Double.parseDouble(tbLaborat.getValueAt(i,7).toString());
                            Jasa_Medik_Petugas_Laborat_Ralan=Jasa_Medik_Petugas_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,8).toString());
                            Kso_Laborat_Ralan=Kso_Laborat_Ralan+Double.parseDouble(tbLaborat.getValueAt(i,9).toString());
                            Persediaan_Laborat_Rawat_Jalan=Persediaan_Laborat_Rawat_Jalan+Double.parseDouble(tbLaborat.getValueAt(i,5).toString());
                            tabModeBilling.addRow(new Object[]{
                                "",tbLaborat.getValueAt(i,2).toString(),":",
                                Double.parseDouble(tbLaborat.getValueAt(i,3).toString()),1,
                                Double.parseDouble(tbLaborat.getValueAt(i,3).toString()),"Laborat"
                            });
                        }
                    }
                    for(i=0;i<tbSubLaborat.getRowCount();i++){
                        if(tbSubLaborat.getValueAt(i,0).toString().equals("true")){
                            Jasa_Medik_Dokter_Laborat_Ralan=Jasa_Medik_Dokter_Laborat_Ralan+Double.parseDouble(tbSubLaborat.getValueAt(i,6).toString())+Double.parseDouble(tbSubLaborat.getValueAt(i,7).toString());
                            Jasa_Medik_Petugas_Laborat_Ralan=Jasa_Medik_Petugas_Laborat_Ralan+Double.parseDouble(tbSubLaborat.getValueAt(i,8).toString());
                            Kso_Laborat_Ralan=Kso_Laborat_Ralan+Double.parseDouble(tbSubLaborat.getValueAt(i,9).toString());
                            Persediaan_Laborat_Rawat_Jalan=Persediaan_Laborat_Rawat_Jalan+Double.parseDouble(tbSubLaborat.getValueAt(i,5).toString());
                            tabModeBilling.addRow(new Object[]{
                                "",tbSubLaborat.getValueAt(i,2).toString(),":",
                                Double.parseDouble(tbSubLaborat.getValueAt(i,3).toString()),1,
                                Double.parseDouble(tbSubLaborat.getValueAt(i,3).toString()),"Laborat"
                            });
                        }
                    }
                    
                    for(i=0;i<tbObat.getRowCount();i++){
                        if(tbObat.getValueAt(i,0).toString().equals("true")){
                            jml++;
                        }
                    }
                    
                    if(jml>0){
                        tabModeBilling.addRow(new Object[]{"Obat & BHP",":","",null,null,null,"Ralan Dokter"});
                    }
                    
                    subttl=0;
                    for(i=0;i<tbObat.getRowCount();i++){
                        if(tbObat.getValueAt(i,0).toString().equals("true")){
                            Obat_Rawat_Jalan=Obat_Rawat_Jalan+(Double.parseDouble(tbObat.getValueAt(i,10).toString())*Double.parseDouble(tbObat.getValueAt(i,13).toString()));
                            tabModeBilling.addRow(new Object[]{
                                "",tbObat.getValueAt(i,7).toString(),":",
                                (Double.parseDouble(tbObat.getValueAt(i,12).toString())/Double.parseDouble(tbObat.getValueAt(i,10).toString())),
                                Double.parseDouble(tbObat.getValueAt(i,10).toString()),
                                Double.parseDouble(tbObat.getValueAt(i,12).toString()),"Obat"
                            });
                            subttl=subttl+Double.parseDouble(tbObat.getValueAt(i,12).toString());
                        }
                    }
                    if(subttl>0){ 
                        if(tampilkan_ppnobat_ralan.equals("Yes")){
                            ppnobat=Valid.roundUp(subttl*0.1,100);
                            tabModeBilling.addRow(new Object[]{"","PPN Obat",":",ppnobat,1,ppnobat,"Obat"});           
                        }
                    }
                    isHitung();
                    isKembali();
                    //"Keterangan","Tagihan/Tindakan/Terapi","","Biaya","Jml","Total Biaya",""
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

    private void tampilradiologi() {
        try{
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
                    index++;
                }
            }

            Valid.tabelKosong(tabModeRadiologi);
            for(i=0;i<jml;i++){                
                tabModeRadiologi.addRow(new Object[] {pilih[i],kode[i],nama[i],totaltnd[i],bagianrs[i],bhp[i],tarif_perujuk[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]});
            }    
        
            try {
                if(cara_bayar_radiologi.equals("Yes")&&kelas_radiologi.equals("No")){
                    psradiologi=koneksi.prepareStatement(
                        "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                        "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                        "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                        "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kd_pj=? or jns_perawatan_radiologi.kd_pj='-') and jns_perawatan_radiologi.kd_jenis_prw like ? or "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kd_pj=? or jns_perawatan_radiologi.kd_pj='-') and jns_perawatan_radiologi.nm_perawatan like ? "+
                        "order by jns_perawatan_radiologi.kd_jenis_prw");
                    psradiologi.setString(1,kd_pj.trim());
                    psradiologi.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    psradiologi.setString(3,kd_pj.trim());
                    psradiologi.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                }else if(cara_bayar_radiologi.equals("No")&&kelas_radiologi.equals("No")){
                    psradiologi=koneksi.prepareStatement(
                        "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                        "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                        "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                        "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where "+
                        " jns_perawatan_radiologi.status='1' and jns_perawatan_radiologi.kd_jenis_prw like ? or "+
                        " jns_perawatan_radiologi.status='1' and jns_perawatan_radiologi.nm_perawatan like ?  "+
                        "order by jns_perawatan_radiologi.kd_jenis_prw");
                    psradiologi.setString(1,"%"+TCariTindakan.getText().trim()+"%");                
                    psradiologi.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                }else if(cara_bayar_radiologi.equals("Yes")&&kelas_radiologi.equals("Yes")){
                    psradiologi=koneksi.prepareStatement(
                        "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                        "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                        "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                        "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kd_pj=? or jns_perawatan_radiologi.kd_pj='-') and (jns_perawatan_radiologi.kelas=? or jns_perawatan_radiologi.kelas='-') and jns_perawatan_radiologi.kd_jenis_prw like ? or "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kd_pj=? or jns_perawatan_radiologi.kd_pj='-') and (jns_perawatan_radiologi.kelas=? or jns_perawatan_radiologi.kelas='-') and jns_perawatan_radiologi.nm_perawatan like ? "+
                        "order by jns_perawatan_radiologi.kd_jenis_prw");
                    psradiologi.setString(1,kd_pj.trim());
                    psradiologi.setString(2,"Rawat Jalan");
                    psradiologi.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                    psradiologi.setString(4,kd_pj.trim());
                    psradiologi.setString(5,"Rawat Jalan");
                    psradiologi.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                }else if(cara_bayar_radiologi.equals("No")&&kelas_radiologi.equals("Yes")){
                    psradiologi=koneksi.prepareStatement(
                        "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,"+
                        "jns_perawatan_radiologi.bagian_rs,jns_perawatan_radiologi.bhp,jns_perawatan_radiologi.tarif_perujuk,"+
                        "jns_perawatan_radiologi.tarif_tindakan_dokter,jns_perawatan_radiologi.tarif_tindakan_petugas,"+
                        "jns_perawatan_radiologi.kso,jns_perawatan_radiologi.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kelas=? or jns_perawatan_radiologi.kelas='-') and jns_perawatan_radiologi.kd_jenis_prw like ? or "+
                        " jns_perawatan_radiologi.status='1' and (jns_perawatan_radiologi.kelas=? or jns_perawatan_radiologi.kelas='-') and jns_perawatan_radiologi.nm_perawatan like ?  "+
                        "order by jns_perawatan_radiologi.kd_jenis_prw");
                    psradiologi.setString(1,"Rawat Jalan");
                    psradiologi.setString(2,"%"+TCariTindakan.getText().trim()+"%"); 
                    psradiologi.setString(3,"Rawat Jalan");               
                    psradiologi.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                }   
                rsradilogi=psradiologi.executeQuery();
                while(rsradilogi.next()){                
                    tabModeRadiologi.addRow(new Object[]{
                        false,rsradilogi.getString(1),rsradilogi.getString(2),
                        rsradilogi.getDouble(3),rsradilogi.getDouble(4),
                        rsradilogi.getDouble(5),rsradilogi.getDouble(6),
                        rsradilogi.getDouble(7),rsradilogi.getDouble(8),
                        rsradilogi.getDouble(9),rsradilogi.getDouble(10)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rsradilogi!=null){
                    rsradilogi.close();
                }
                if(psradiologi!=null){
                    psradiologi.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }
    
    private void tampillaborat() {
        try{
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
                    index++;
                }
            }

            Valid.tabelKosong(tabModeLaborat);
            for(i=0;i<jml;i++){                
                tabModeLaborat.addRow(new Object[] {pilih[i],kode[i],nama[i],totaltnd[i],bagianrs[i],bhp[i],tarif_perujuk[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]});
            }    
        
            try {
                if(cara_bayar_lab.equals("Yes")&&kelas_lab.equals("No")){
                    pslaborat=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                        "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                        "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                        "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and jns_perawatan_lab.kd_jenis_prw like ? or "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and jns_perawatan_lab.nm_perawatan like ? "+
                        "order by jns_perawatan_lab.kd_jenis_prw");
                    pslaborat.setString(1,kd_pj.trim());
                    pslaborat.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                    pslaborat.setString(3,kd_pj.trim());
                    pslaborat.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                }else if(cara_bayar_lab.equals("No")&&kelas_lab.equals("No")){
                    pslaborat=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                        "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                        "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                        "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                        " jns_perawatan_lab.status='1' and jns_perawatan_lab.kd_jenis_prw like ? or "+
                        " jns_perawatan_lab.status='1' and jns_perawatan_lab.nm_perawatan like ?  "+
                        "order by jns_perawatan_lab.kd_jenis_prw");
                    pslaborat.setString(1,"%"+TCariTindakan.getText().trim()+"%");                
                    pslaborat.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                }else if(cara_bayar_lab.equals("Yes")&&kelas_lab.equals("Yes")){
                    pslaborat=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                        "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                        "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                        "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') and jns_perawatan_lab.kd_jenis_prw like ? or "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kd_pj=? or jns_perawatan_lab.kd_pj='-') and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') and jns_perawatan_lab.nm_perawatan like ? "+
                        "order by jns_perawatan_lab.kd_jenis_prw");
                    pslaborat.setString(1,kd_pj.trim());
                    pslaborat.setString(2,"Rawat Jalan");
                    pslaborat.setString(3,"%"+TCariTindakan.getText().trim()+"%");
                    pslaborat.setString(4,kd_pj.trim());
                    pslaborat.setString(5,"Rawat Jalan");
                    pslaborat.setString(6,"%"+TCariTindakan.getText().trim()+"%");
                }else if(cara_bayar_lab.equals("No")&&kelas_lab.equals("Yes")){
                    pslaborat=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,"+
                        "jns_perawatan_lab.bagian_rs,jns_perawatan_lab.bhp,jns_perawatan_lab.tarif_perujuk,"+
                        "jns_perawatan_lab.tarif_tindakan_dokter,jns_perawatan_lab.tarif_tindakan_petugas,"+
                        "jns_perawatan_lab.kso,jns_perawatan_lab.menejemen,penjab.png_jawab "+
                        "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') and jns_perawatan_lab.kd_jenis_prw like ? or "+
                        " jns_perawatan_lab.status='1' and (jns_perawatan_lab.kelas=? or jns_perawatan_lab.kelas='-') and jns_perawatan_lab.nm_perawatan like ?  "+
                        "order by jns_perawatan_lab.kd_jenis_prw");
                    pslaborat.setString(1,"Rawat Jalan");
                    pslaborat.setString(2,"%"+TCariTindakan.getText().trim()+"%"); 
                    pslaborat.setString(3,"Rawat Jalan");               
                    pslaborat.setString(4,"%"+TCariTindakan.getText().trim()+"%");
                }   
                rslaborat=pslaborat.executeQuery();
                while(rslaborat.next()){                
                    tabModeLaborat.addRow(new Object[]{
                        false,rslaborat.getString(1),rslaborat.getString(2),
                        rslaborat.getDouble(3),rslaborat.getDouble(4),
                        rslaborat.getDouble(5),rslaborat.getDouble(6),
                        rslaborat.getDouble(7),rslaborat.getDouble(8),
                        rslaborat.getDouble(9),rslaborat.getDouble(10)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : "+e);
            } finally{
                if(rslaborat!=null){
                    rslaborat.close();
                }
                if(pslaborat!=null){
                    pslaborat.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi 2 : "+e);
        }
    }

    private void tampilsublaborat() {
        //"P","ID","Nama Pemeriksaan","Tarif","Bagian RS","BHP","Tarif Perujuk","Tarif Dokter","Tarif Petugas","Kso","Menejemen"
        try {
            jml=0;
            for(i=0;i<tbSubLaborat.getRowCount();i++){
                if(tbSubLaborat.getValueAt(i,0).toString().equals("true")){
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
            
            index=0; 
            for(i=0;i<tbSubLaborat.getRowCount();i++){
                if(tbSubLaborat.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbSubLaborat.getValueAt(i,1).toString();
                    nama[index]=tbSubLaborat.getValueAt(i,2).toString();
                    totaltnd[index]=Double.parseDouble(tbSubLaborat.getValueAt(i,3).toString());
                    bagianrs[index]=Double.parseDouble(tbSubLaborat.getValueAt(i,4).toString());
                    bhp[index]=Double.parseDouble(tbSubLaborat.getValueAt(i,5).toString());
                    tarif_perujuk[index]=Double.parseDouble(tbSubLaborat.getValueAt(i,6).toString());
                    jmdokter[index]=Double.parseDouble(tbSubLaborat.getValueAt(i,7).toString());
                    jmperawat[index]=Double.parseDouble(tbSubLaborat.getValueAt(i,8).toString());
                    kso[index]=Double.parseDouble(tbSubLaborat.getValueAt(i,9).toString());
                    menejemen[index]=Double.parseDouble(tbSubLaborat.getValueAt(i,10).toString());
                    index++;
                }
            }

            Valid.tabelKosong(tabModeSubLaborat);
            for(i=0;i<jml;i++){                
                tabModeSubLaborat.addRow(new Object[] {pilih[i],kode[i],nama[i],totaltnd[i],bagianrs[i],bhp[i],tarif_perujuk[i],jmdokter[i],jmperawat[i],kso[i],menejemen[i]});
            }    
            
            for(i=0;i<tbLaborat.getRowCount();i++){ 
                if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                    tabModeSubLaborat.addRow(new Object[]{false,"",tbLaborat.getValueAt(i,2).toString(),0,0,0,0,0,0,0,0});
                    pssublaborat=koneksi.prepareStatement("select id_template, Pemeriksaan,biaya_item, "+
                                "bagian_rs,bhp,bagian_perujuk,bagian_dokter,bagian_laborat,kso,menejemen "+
                                "from template_laboratorium where kd_jenis_prw=? and Pemeriksaan like ? order by urut");
                    try{
                        pssublaborat.setString(1,tbLaborat.getValueAt(i,1).toString());
                        pssublaborat.setString(2,"%"+TCariTindakan.getText().trim()+"%");
                        rssublaborat=pssublaborat.executeQuery();
                        while(rssublaborat.next()){
                            tabModeSubLaborat.addRow(new Object[]{
                                false,rssublaborat.getString("id_template"),"   "+rssublaborat.getString("Pemeriksaan"),
                                rssublaborat.getDouble("biaya_item"),rssublaborat.getDouble("bagian_rs"),
                                rssublaborat.getDouble("bhp"),rssublaborat.getDouble("bagian_perujuk"),
                                rssublaborat.getDouble("bagian_dokter"),rssublaborat.getDouble("bagian_laborat"),
                                rssublaborat.getDouble("kso"),rssublaborat.getDouble("menejemen")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rssublaborat!=null){
                            rssublaborat.close();
                        }
                        if(pssublaborat!=null){
                            pssublaborat.close();
                        }
                    }                           
                }
            }
        } catch (Exception e) {
            System.out.println("Error Detail : "+e);
        }    
    }
    
    private void tampilObat() {
        Valid.tabelKosong(tabModeObat);
        try{        
            jml=Sequel.cariInteger("select count(*) from permintaan_registrasi where no_rawat='"+TNoRw.getText()+"' and kd_poli='"+kd_poli+"'");
            if(jml==0){
                LabelStatus.setText("Status : Belum Bayar");
                chkPoli.setEnabled(true);
                BtnHapusReg.setVisible(false);
            }else if(jml>0){
                LabelStatus.setText("Status : Sudah Bayar");
                chkPoli.setEnabled(false);
                chkPoli.setSelected(true);
                BtnHapusReg.setVisible(true);
            }
            
            psobat=koneksi.prepareStatement("select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,"+
                   "detail_pemberian_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "detail_pemberian_obat.kode_brng,databarang.nama_brng,detail_pemberian_obat.embalase,detail_pemberian_obat.tuslah,"+
                   "detail_pemberian_obat.jml,detail_pemberian_obat.biaya_obat,detail_pemberian_obat.total,detail_pemberian_obat.h_beli,detail_pemberian_obat.kd_bangsal "+
                   "from detail_pemberian_obat inner join reg_periksa inner join pasien inner join databarang "+
                   "on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                   "where detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.kode_brng like ? or "+
                   "detail_pemberian_obat.no_rawat=? and databarang.nama_brng like ? "+
                   "order by databarang.nama_brng");
            try {
                psobat.setString(1,TNoRw.getText());
                psobat.setString(2,"%"+TCari.getText().trim()+"%");
                psobat.setString(3,TNoRw.getText());
                psobat.setString(4,"%"+TCari.getText().trim()+"%");
                rsobat=psobat.executeQuery();
                while(rsobat.next()){                    
                    tabModeObat.addRow(new Object[]{
                        true,rsobat.getString(1),rsobat.getString(2),rsobat.getString(3),
                        rsobat.getString(4),rsobat.getString(5),rsobat.getString(6),
                        rsobat.getString(7),rsobat.getDouble(8),rsobat.getDouble(9),
                        rsobat.getDouble(10),rsobat.getDouble(11),rsobat.getDouble(12),
                        rsobat.getDouble(13),rsobat.getString(14)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rsobat!=null){
                    rsobat.close();
                }
                if(psobat!=null){
                    psobat.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
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
        jLabel5.setText("Bayar : Rp.");
        if(kekurangan<0){
            jLabel6.setText("Kekurangan : Rp.");
        }else{
            jLabel6.setText("Kembali : Rp.");
        }

        TKembali.setText(Valid.SetAngka3(kekurangan));  
    }

    private void isSimpan() {
        if(notaralan.equals("Yes")){
            BtnNotaActionPerformed(null);
        }
        if(ttl<=0){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan masukkan tagihan ...!!!");
        }else{
            int jawab=JOptionPane.showConfirmDialog(null, "Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if(jawab==JOptionPane.YES_OPTION){
                for(i=0;i<tbRawatDr.getRowCount();i++){
                    if(tbRawatDr.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("permintaan_jl_dr","?,?,?,?,?,?,?","Tindakan dokter",7,new String[]{
                             TNoRw.getText(),tbRawatDr.getValueAt(i,1).toString(),KdDok.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                             DTPTgl.getSelectedItem().toString().substring(11,19),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                             DTPTgl.getSelectedItem().toString().substring(11,19)
                          })==false){
                            tbRawatDr.setValueAt(false,i,0);
                        }
                    }
                }
                for(i=0;i<tbRawatPr.getRowCount();i++){
                    if(tbRawatPr.getValueAt(i,0).toString().equals("true")){
                        if(Sequel.menyimpantf2("permintaan_jl_pr","?,?,?,?,?,?,?","Tindakan paramedis",7,new String[]{
                             TNoRw.getText(),tbRawatPr.getValueAt(i,1).toString(),kdptg.getText(),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                             DTPTgl.getSelectedItem().toString().substring(11,19),Valid.SetTgl(DTPTgl.getSelectedItem()+""),
                             DTPTgl.getSelectedItem().toString().substring(11,19)
                          })==false){
                            tbRawatPr.setValueAt(false,i,0);
                        }
                    }
                }
                for(i=0;i<tbRawatDrPr.getRowCount();i++){
                    if(tbRawatDrPr.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }
                for(i=0;i<tbRadiologi.getRowCount();i++){
                    if(tbRadiologi.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }
                for(i=0;i<tbLaborat.getRowCount();i++){
                    if(tbLaborat.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }
                for(i=0;i<tbSubLaborat.getRowCount();i++){
                    if(tbSubLaborat.getValueAt(i,0).toString().equals("true")){
                        jml++;
                    }
                }
            }                
        }
    }
    
}
