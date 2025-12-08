/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public final class DlgPengeluaranPengeluaran extends javax.swing.JDialog {
    private final DefaultTableModel tabModeBayarPesanObat,tabModeBayarPesanNonMedis,tabModeBayarPesanAset,tabModeBayarPesanDapur,tabModeBayarJM,
                                    tabModePengeluaranHarian,tabModeBebanHutang,tabModePengadaanObat,tabModePengadaanNonMedis,tabModePengadaanInventaris,
                                    tabModePengadaanDapur,tabModeBayarPesanToko,tabModePengadaanToko;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private double bayarobat=0,bayarnonmedis=0,bayaraset=0,bayardapur=0,bayarjm=0,pengeluaranharian=0,bayarbebanhutang=0,pengadaanobat=0,pengadaannonmedis=0,
                   pengadaaninventaris=0,pengadaandapur,bayartoko=0,pengadaantoko=0;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgPengeluaranPengeluaran(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabModeBayarPesanObat=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","No.Bukti","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBayarPesanObat.setModel(tabModeBayarPesanObat);
        tbBayarPesanObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBayarPesanObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbBayarPesanObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(152);
            }else if(i==7){
                column.setPreferredWidth(100);
            }
        }
        tbBayarPesanObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeBayarPesanNonMedis=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","No.Bukti","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBayarPesanNonMedis.setModel(tabModeBayarPesanNonMedis);
        tbBayarPesanNonMedis.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBayarPesanNonMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbBayarPesanNonMedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(100);
            }
        }
        tbBayarPesanNonMedis.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeBayarPesanAset=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","No.Bukti","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBayarPesanAset.setModel(tabModeBayarPesanAset);
        tbBayarPesanAset.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBayarPesanAset.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbBayarPesanAset.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(100);
            }
        }
        tbBayarPesanAset.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeBayarPesanDapur=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","No.Bukti","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBayarPesanDapur.setModel(tabModeBayarPesanDapur);
        tbBayarPesanDapur.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBayarPesanDapur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbBayarPesanDapur.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(100);
            }
        }
        tbBayarPesanDapur.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeBayarJM=new DefaultTableModel(null,new Object[]{"Tanggal","Nomor J.M.","Kode Dokter","Nama Dokter","Akun Bayar","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class, 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBayarJM.setModel(tabModeBayarJM);
        tbBayarJM.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBayarJM.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 6; i++) {
            TableColumn column = tbBayarJM.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(100);
            }
        }
        tbBayarJM.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePengeluaranHarian=new DefaultTableModel(null,new Object[]{"Tanggal","No.Pengeluaran","NIP","Nama Petugas","Kategori Pengeluaran","Pengeluaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPengeluaranHarian.setModel(tabModePengeluaranHarian);
        tbPengeluaranHarian.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengeluaranHarian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 6; i++) {
            TableColumn column = tbPengeluaranHarian.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(240);
            }else if(i==5){
                column.setPreferredWidth(100);
            }
        }
        tbPengeluaranHarian.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeBebanHutang=new DefaultTableModel(null,new Object[]{
            "Tgl.Bayar","Kode","Pemberi Hutang","Pembayaran(Rp)","Keterangan","No.Hutang","Kode Akun","Akun Bayar","No.Bukti"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBebanHutang.setModel(tabModeBebanHutang);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbBebanHutang.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBebanHutang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbBebanHutang.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(220);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(120);
            }
        }
        tbBebanHutang.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePengadaanObat=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPengadaanObat.setModel(tabModePengadaanObat);
        tbPengadaanObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengadaanObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbPengadaanObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(100);
            }
        }
        tbPengadaanObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePengadaanNonMedis=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPengadaanNonMedis.setModel(tabModePengadaanNonMedis);
        tbPengadaanNonMedis.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengadaanNonMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbPengadaanNonMedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(100);
            }
        }
        tbPengadaanNonMedis.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePengadaanInventaris=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPengadaanAsetInventaris.setModel(tabModePengadaanInventaris);
        tbPengadaanAsetInventaris.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengadaanAsetInventaris.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbPengadaanAsetInventaris.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(100);
            }
        }
        tbPengadaanAsetInventaris.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePengadaanDapur=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPengadaanDapur.setModel(tabModePengadaanDapur);
        tbPengadaanDapur.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengadaanDapur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbPengadaanDapur.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(100);
            }
        }
        tbPengadaanDapur.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeBayarPesanToko=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","No.Bukti","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBayarPesanToko.setModel(tabModeBayarPesanToko);
        tbBayarPesanToko.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBayarPesanToko.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbBayarPesanToko.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(100);
            }
        }
        tbBayarPesanToko.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModePengadaanToko=new DefaultTableModel(null,new Object[]{"Tanggal","No.Faktur","Suplier","NIP","Nama Petugas","Akun Bayar","Pembayaran"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Double.class 
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPengadaanToko.setModel(tabModePengadaanToko);
        tbPengadaanToko.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengadaanToko.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbPengadaanToko.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(100);
            }
        }
        tbPengadaanToko.setDefaultRenderer(Object.class, new WarnaTable());
    }    
    
     

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        BtnCari = new widget.Button();
        jLabel16 = new javax.swing.JLabel();
        LCountTotal = new javax.swing.JLabel();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCountPesanObat = new javax.swing.JLabel();
        LCountBayarNonMedis = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        LCountBayarAset = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        LCountBayarDapur = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        LCountBayarJM = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        LCountPengeluaranHarian = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        LCountBayarBebanHutang = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        LCountPengadaanObat = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        LCountPengadaanNonMedis = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        LCountPengadaanInventaris = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        LCountPengadaanDapur = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        LCountBayarPesanToko = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        LCountPengadaanToko = new javax.swing.JLabel();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbBayarPesanObat = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbBayarPesanNonMedis = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbBayarPesanAset = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbBayarPesanDapur = new widget.Table();
        Scroll7 = new widget.ScrollPane();
        tbBayarJM = new widget.Table();
        Scroll8 = new widget.ScrollPane();
        tbPengeluaranHarian = new widget.Table();
        Scroll9 = new widget.ScrollPane();
        tbBebanHutang = new widget.Table();
        Scroll10 = new widget.ScrollPane();
        tbPengadaanObat = new widget.Table();
        Scroll11 = new widget.ScrollPane();
        tbPengadaanNonMedis = new widget.Table();
        Scroll12 = new widget.ScrollPane();
        tbPengadaanAsetInventaris = new widget.Table();
        Scroll13 = new widget.ScrollPane();
        tbPengadaanDapur = new widget.Table();
        Scroll14 = new widget.ScrollPane();
        tbBayarPesanToko = new widget.Table();
        Scroll15 = new widget.ScrollPane();
        tbPengadaanToko = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengeluaran-pengeluaran/Kas Keluar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 190));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass8.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-12-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass8.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-12-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(DTPCari2);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        panelGlass8.add(BtnCari);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(50, 50, 50));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Total Pengeluaran :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass8.add(jLabel16);

        LCountTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountTotal.setForeground(new java.awt.Color(50, 50, 50));
        LCountTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountTotal.setText("0");
        LCountTotal.setName("LCountTotal"); // NOI18N
        LCountTotal.setPreferredSize(new java.awt.Dimension(170, 23));
        panelGlass8.add(LCountTotal);

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
        panelGlass8.add(BtnPrint);

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
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 134));
        panelGlass9.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Pesan Obat :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel10);
        jLabel10.setBounds(0, 10, 125, 23);

        LCountPesanObat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountPesanObat.setForeground(new java.awt.Color(50, 50, 50));
        LCountPesanObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountPesanObat.setText("0");
        LCountPesanObat.setName("LCountPesanObat"); // NOI18N
        LCountPesanObat.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountPesanObat);
        LCountPesanObat.setBounds(129, 10, 110, 23);

        LCountBayarNonMedis.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountBayarNonMedis.setForeground(new java.awt.Color(50, 50, 50));
        LCountBayarNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountBayarNonMedis.setText("0");
        LCountBayarNonMedis.setName("LCountBayarNonMedis"); // NOI18N
        LCountBayarNonMedis.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountBayarNonMedis);
        LCountBayarNonMedis.setBounds(341, 10, 110, 23);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(50, 50, 50));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Pesan Non Medis :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel11);
        jLabel11.setBounds(237, 10, 100, 23);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(50, 50, 50));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Pesan Aset :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel12);
        jLabel12.setBounds(455, 10, 115, 23);

        LCountBayarAset.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountBayarAset.setForeground(new java.awt.Color(50, 50, 50));
        LCountBayarAset.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountBayarAset.setText("0");
        LCountBayarAset.setName("LCountBayarAset"); // NOI18N
        LCountBayarAset.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountBayarAset);
        LCountBayarAset.setBounds(574, 10, 110, 23);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(50, 50, 50));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Pesan Dapur :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel13);
        jLabel13.setBounds(0, 40, 125, 23);

        LCountBayarDapur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountBayarDapur.setForeground(new java.awt.Color(50, 50, 50));
        LCountBayarDapur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountBayarDapur.setText("0");
        LCountBayarDapur.setName("LCountBayarDapur"); // NOI18N
        LCountBayarDapur.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountBayarDapur);
        LCountBayarDapur.setBounds(129, 40, 110, 23);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(50, 50, 50));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("JM Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel14);
        jLabel14.setBounds(237, 40, 100, 23);

        LCountBayarJM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountBayarJM.setForeground(new java.awt.Color(50, 50, 50));
        LCountBayarJM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountBayarJM.setText("0");
        LCountBayarJM.setName("LCountBayarJM"); // NOI18N
        LCountBayarJM.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountBayarJM);
        LCountBayarJM.setBounds(341, 40, 110, 23);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(50, 50, 50));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Pengeluaran Harian :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel15);
        jLabel15.setBounds(455, 40, 115, 23);

        LCountPengeluaranHarian.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountPengeluaranHarian.setForeground(new java.awt.Color(50, 50, 50));
        LCountPengeluaranHarian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountPengeluaranHarian.setText("0");
        LCountPengeluaranHarian.setName("LCountPengeluaranHarian"); // NOI18N
        LCountPengeluaranHarian.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountPengeluaranHarian);
        LCountPengeluaranHarian.setBounds(574, 40, 110, 23);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(50, 50, 50));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Beban Hutang :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel17);
        jLabel17.setBounds(678, 10, 100, 23);

        LCountBayarBebanHutang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountBayarBebanHutang.setForeground(new java.awt.Color(50, 50, 50));
        LCountBayarBebanHutang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountBayarBebanHutang.setText("0");
        LCountBayarBebanHutang.setName("LCountBayarBebanHutang"); // NOI18N
        LCountBayarBebanHutang.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountBayarBebanHutang);
        LCountBayarBebanHutang.setBounds(782, 10, 110, 23);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(50, 50, 50));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Pengadaan Obat :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel18);
        jLabel18.setBounds(668, 40, 110, 23);

        LCountPengadaanObat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountPengadaanObat.setForeground(new java.awt.Color(50, 50, 50));
        LCountPengadaanObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountPengadaanObat.setText("0");
        LCountPengadaanObat.setName("LCountPengadaanObat"); // NOI18N
        LCountPengadaanObat.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountPengadaanObat);
        LCountPengadaanObat.setBounds(782, 40, 110, 23);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(50, 50, 50));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Pengadaan Non Medis :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel20);
        jLabel20.setBounds(0, 70, 125, 23);

        LCountPengadaanNonMedis.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountPengadaanNonMedis.setForeground(new java.awt.Color(50, 50, 50));
        LCountPengadaanNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountPengadaanNonMedis.setText("0");
        LCountPengadaanNonMedis.setName("LCountPengadaanNonMedis"); // NOI18N
        LCountPengadaanNonMedis.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountPengadaanNonMedis);
        LCountPengadaanNonMedis.setBounds(129, 70, 110, 23);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(50, 50, 50));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Pengadaan Invent :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel22);
        jLabel22.setBounds(227, 70, 110, 23);

        LCountPengadaanInventaris.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountPengadaanInventaris.setForeground(new java.awt.Color(50, 50, 50));
        LCountPengadaanInventaris.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountPengadaanInventaris.setText("0");
        LCountPengadaanInventaris.setName("LCountPengadaanInventaris"); // NOI18N
        LCountPengadaanInventaris.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountPengadaanInventaris);
        LCountPengadaanInventaris.setBounds(341, 70, 110, 23);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(50, 50, 50));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Pengadaan Dapur :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel23);
        jLabel23.setBounds(455, 70, 115, 23);

        LCountPengadaanDapur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountPengadaanDapur.setForeground(new java.awt.Color(50, 50, 50));
        LCountPengadaanDapur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountPengadaanDapur.setText("0");
        LCountPengadaanDapur.setName("LCountPengadaanDapur"); // NOI18N
        LCountPengadaanDapur.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountPengadaanDapur);
        LCountPengadaanDapur.setBounds(574, 70, 110, 23);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(50, 50, 50));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Pesan Toko :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel24);
        jLabel24.setBounds(668, 70, 110, 23);

        LCountBayarPesanToko.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountBayarPesanToko.setForeground(new java.awt.Color(50, 50, 50));
        LCountBayarPesanToko.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountBayarPesanToko.setText("0");
        LCountBayarPesanToko.setName("LCountBayarPesanToko"); // NOI18N
        LCountBayarPesanToko.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountBayarPesanToko);
        LCountBayarPesanToko.setBounds(782, 70, 110, 23);

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(50, 50, 50));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Pengadaan Toko :");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel25);
        jLabel25.setBounds(0, 100, 125, 23);

        LCountPengadaanToko.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LCountPengadaanToko.setForeground(new java.awt.Color(50, 50, 50));
        LCountPengadaanToko.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCountPengadaanToko.setText("0");
        LCountPengadaanToko.setName("LCountPengadaanToko"); // NOI18N
        LCountPengadaanToko.setPreferredSize(new java.awt.Dimension(180, 23));
        panelGlass9.add(LCountPengadaanToko);
        LCountPengadaanToko.setBounds(129, 100, 110, 23);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBayarPesanObat.setName("tbBayarPesanObat"); // NOI18N
        Scroll.setViewportView(tbBayarPesanObat);

        TabRawat.addTab("Bayar Pesan Obat", Scroll);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbBayarPesanNonMedis.setName("tbBayarPesanNonMedis"); // NOI18N
        Scroll2.setViewportView(tbBayarPesanNonMedis);

        TabRawat.addTab("Bayar Pesan Non Medis", Scroll2);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbBayarPesanAset.setName("tbBayarPesanAset"); // NOI18N
        Scroll3.setViewportView(tbBayarPesanAset);

        TabRawat.addTab("Bayar Pesan Aset/Inventaris", Scroll3);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbBayarPesanDapur.setName("tbBayarPesanDapur"); // NOI18N
        Scroll4.setViewportView(tbBayarPesanDapur);

        TabRawat.addTab("Bayar Pesan Dapur", Scroll4);

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbBayarJM.setName("tbBayarJM"); // NOI18N
        Scroll7.setViewportView(tbBayarJM);

        TabRawat.addTab("Bayar JM Dokter", Scroll7);

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbPengeluaranHarian.setName("tbPengeluaranHarian"); // NOI18N
        Scroll8.setViewportView(tbPengeluaranHarian);

        TabRawat.addTab("Pengeluaran Harian", Scroll8);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbBebanHutang.setName("tbBebanHutang"); // NOI18N
        Scroll9.setViewportView(tbBebanHutang);

        TabRawat.addTab("Bayar Beban Hutang", Scroll9);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbPengadaanObat.setName("tbPengadaanObat"); // NOI18N
        Scroll10.setViewportView(tbPengadaanObat);

        TabRawat.addTab("Pengadaan Obat", Scroll10);

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbPengadaanNonMedis.setName("tbPengadaanNonMedis"); // NOI18N
        Scroll11.setViewportView(tbPengadaanNonMedis);

        TabRawat.addTab("Pengadaan Non Medis", Scroll11);

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll12.setName("Scroll12"); // NOI18N
        Scroll12.setOpaque(true);

        tbPengadaanAsetInventaris.setName("tbPengadaanAsetInventaris"); // NOI18N
        Scroll12.setViewportView(tbPengadaanAsetInventaris);

        TabRawat.addTab("Pengadaan Aset/Inventaris", Scroll12);

        Scroll13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll13.setName("Scroll13"); // NOI18N
        Scroll13.setOpaque(true);

        tbPengadaanDapur.setName("tbPengadaanDapur"); // NOI18N
        Scroll13.setViewportView(tbPengadaanDapur);

        TabRawat.addTab("Pengadaan Dapur", Scroll13);

        Scroll14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll14.setName("Scroll14"); // NOI18N
        Scroll14.setOpaque(true);

        tbBayarPesanToko.setName("tbBayarPesanToko"); // NOI18N
        Scroll14.setViewportView(tbBayarPesanToko);

        TabRawat.addTab("Bayar Pesan Toko", Scroll14);

        Scroll15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll15.setName("Scroll15"); // NOI18N
        Scroll15.setOpaque(true);

        tbPengadaanToko.setName("tbPengadaanToko"); // NOI18N
        Scroll15.setViewportView(tbPengadaanToko);

        TabRawat.addTab("Pengadaan Toko", Scroll15);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, DTPCari2, BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,DTPCari2);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabModeBayarPesanObat.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPesanObat.jasper","report","::[ Pembayaran Pemesanan Obat & BHP ]::",
                "select bayar_pemesanan.tgl_bayar,bayar_pemesanan.no_faktur,datasuplier.nama_suplier,bayar_pemesanan.nip,"+
                "petugas.nama,bayar_pemesanan.nama_bayar,bayar_pemesanan.no_bukti,bayar_pemesanan.besar_bayar "+
                "from bayar_pemesanan inner join pemesanan on bayar_pemesanan.no_faktur=pemesanan.no_faktur "+
                "inner join datasuplier on pemesanan.kode_suplier=datasuplier.kode_suplier "+
                "inner join petugas on petugas.nip=bayar_pemesanan.nip "+
                "where bayar_pemesanan.tgl_bayar between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by bayar_pemesanan.tgl_bayar ",param);
        }
        
        if(tabModeBayarPesanNonMedis.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPesanNonMedis.jasper","report","::[ Pembayaran Pemesanan Non Medis ]::",
                "select bayar_pemesanan_non_medis.tgl_bayar,bayar_pemesanan_non_medis.no_faktur,ipsrssuplier.nama_suplier,bayar_pemesanan_non_medis.nip,"+
                "petugas.nama,bayar_pemesanan_non_medis.nama_bayar,bayar_pemesanan_non_medis.no_bukti,bayar_pemesanan_non_medis.besar_bayar "+
                "from bayar_pemesanan_non_medis inner join ipsrspemesanan on bayar_pemesanan_non_medis.no_faktur=ipsrspemesanan.no_faktur "+
                "inner join ipsrssuplier on ipsrspemesanan.kode_suplier=ipsrssuplier.kode_suplier "+
                "inner join petugas on petugas.nip=bayar_pemesanan_non_medis.nip "+
                "where bayar_pemesanan_non_medis.tgl_bayar between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by bayar_pemesanan_non_medis.tgl_bayar",param);
        }
        
        if(tabModeBayarPesanAset.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPesanAset.jasper","report","::[ Pembayaran Pemesanan Aset ]::",
                "select bayar_pemesanan_inventaris.tgl_bayar,bayar_pemesanan_inventaris.no_faktur,inventaris_suplier.nama_suplier,bayar_pemesanan_inventaris.nip,"+
                "petugas.nama,bayar_pemesanan_inventaris.nama_bayar,bayar_pemesanan_inventaris.no_bukti,bayar_pemesanan_inventaris.besar_bayar "+
                "from bayar_pemesanan_inventaris inner join inventaris_pemesanan on bayar_pemesanan_inventaris.no_faktur=inventaris_pemesanan.no_faktur "+
                "inner join inventaris_suplier on inventaris_pemesanan.kode_suplier=inventaris_suplier.kode_suplier "+
                "inner join petugas on petugas.nip=bayar_pemesanan_inventaris.nip "+
                "where bayar_pemesanan_inventaris.tgl_bayar between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by bayar_pemesanan_inventaris.tgl_bayar",param);
        }
        
        if(tabModeBayarPesanDapur.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPesanDapur.jasper","report","::[ Pembayaran Pemesanan Dapur ]::",
                "select bayar_pemesanan_dapur.tgl_bayar,bayar_pemesanan_dapur.no_faktur,dapursuplier.nama_suplier,bayar_pemesanan_dapur.nip,"+
                "petugas.nama,bayar_pemesanan_dapur.nama_bayar,bayar_pemesanan_dapur.no_bukti,bayar_pemesanan_dapur.besar_bayar "+
                "from bayar_pemesanan_dapur inner join dapurpemesanan on bayar_pemesanan_dapur.no_faktur=dapurpemesanan.no_faktur "+
                "inner join dapursuplier on dapurpemesanan.kode_suplier=dapursuplier.kode_suplier "+
                "inner join petugas on petugas.nip=bayar_pemesanan_dapur.nip "+
                "where bayar_pemesanan_dapur.tgl_bayar between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by bayar_pemesanan_dapur.tgl_bayar",param);
        }
        
        if(tabModeBayarJM.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarJMDokter.jasper","report","::[ Pembayaran JM Dokter ]::",
                "select bayar_jm_dokter.tanggal,bayar_jm_dokter.no_bayar,bayar_jm_dokter.kd_dokter,dokter.nm_dokter,bayar_jm_dokter.nama_bayar, "+
                "bayar_jm_dokter.besar_bayar from bayar_jm_dokter inner join dokter on bayar_jm_dokter.kd_dokter=dokter.kd_dokter "+
                "where bayar_jm_dokter.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by bayar_jm_dokter.tanggal",param);
        }
        
        if(tabModePengeluaranHarian.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranPengeluaranHarian.jasper","report","::[ Pengeluaran Harian ]::",
                "select DATE_FORMAT(pengeluaran_harian.tanggal,'%Y-%m-%d') as tanggal,pengeluaran_harian.no_keluar,pengeluaran_harian.nip,"+
                "petugas.nama,kategori_pengeluaran_harian.nama_kategori,pengeluaran_harian.biaya from pengeluaran_harian "+
                "inner join kategori_pengeluaran_harian on pengeluaran_harian.kode_kategori=kategori_pengeluaran_harian.kode_kategori "+
                "inner join petugas on pengeluaran_harian.nip=petugas.nip "+
                "where pengeluaran_harian.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by pengeluaran_harian.tanggal ",param);
        }
        
        if(tabModeBebanHutang.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBayarBebanHutangLain.jasper","report","::[ Bayar Beban Hutang Lain ]::",
                "select bayar_beban_hutang_lain.tgl_bayar, bayar_beban_hutang_lain.kode_pemberi_hutang,pemberi_hutang_lain.nama_pemberi_hutang, bayar_beban_hutang_lain.besar_cicilan,"+
                "bayar_beban_hutang_lain.keterangan, bayar_beban_hutang_lain.no_hutang,bayar_beban_hutang_lain.nama_bayar,bayar_beban_hutang_lain.no_bukti from bayar_beban_hutang_lain "+
                "inner join pemberi_hutang_lain on bayar_beban_hutang_lain.kode_pemberi_hutang=pemberi_hutang_lain.kode_pemberi_hutang where "+
                "bayar_beban_hutang_lain.tgl_bayar between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by bayar_beban_hutang_lain.tgl_bayar ",param);
        }
        
        if(tabModePengadaanObat.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPengadaanObat.jasper","report","::[ Pembayaran Pengadaan Obat & BHP ]::",
                "select pembelian.tgl_beli,pembelian.no_faktur,datasuplier.nama_suplier,pembelian.nip,"+
                "petugas.nama,rekening.nm_rek,pembelian.tagihan from pembelian "+
                "inner join datasuplier on pembelian.kode_suplier=datasuplier.kode_suplier "+
                "inner join petugas on petugas.nip=pembelian.nip "+
                "inner join rekening on rekening.kd_rek=pembelian.kd_rek "+
                "where pembelian.tgl_beli between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by pembelian.tgl_beli",param);
        }
        
        if(tabModePengadaanNonMedis.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPengadaanNonMedis.jasper","report","::[ Pembayaran Pengadaan Barang Non Medis ]::",
                "select ipsrspembelian.tgl_beli,ipsrspembelian.no_faktur,ipsrssuplier.nama_suplier,ipsrspembelian.nip,"+
                "petugas.nama,rekening.nm_rek,ipsrspembelian.tagihan from ipsrspembelian "+
                "inner join ipsrssuplier on ipsrspembelian.kode_suplier=ipsrssuplier.kode_suplier "+
                "inner join petugas on petugas.nip=ipsrspembelian.nip "+
                "inner join rekening on rekening.kd_rek=ipsrspembelian.kd_rek "+
                "where ipsrspembelian.tgl_beli between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by ipsrspembelian.tgl_beli",param);
        }
        
        if(tabModePengadaanInventaris.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPengadaanInventaris.jasper","report","::[ Pembayaran Pengadaan Aset/Inventaris ]::",
                "select inventaris_pembelian.tgl_beli,inventaris_pembelian.no_faktur,inventaris_suplier.nama_suplier,inventaris_pembelian.nip,"+
                "petugas.nama,rekening.nm_rek,inventaris_pembelian.tagihan from inventaris_pembelian "+
                "inner join inventaris_suplier on inventaris_pembelian.kode_suplier=inventaris_suplier.kode_suplier "+
                "inner join petugas on petugas.nip=inventaris_pembelian.nip "+
                "inner join rekening on rekening.kd_rek=inventaris_pembelian.kd_rek "+
                "where inventaris_pembelian.tgl_beli between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by inventaris_pembelian.tgl_beli",param);
        }
        
        if(tabModePengadaanDapur.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPengadaanDapur.jasper","report","::[ Pembayaran Pengadaan Dapur ]::",
                "select dapurpembelian.tgl_beli,dapurpembelian.no_faktur,dapursuplier.nama_suplier,dapurpembelian.nip,"+
                "petugas.nama,rekening.nm_rek,dapurpembelian.tagihan from dapurpembelian "+
                "inner join dapursuplier on dapurpembelian.kode_suplier=dapursuplier.kode_suplier "+
                "inner join petugas on petugas.nip=dapurpembelian.nip "+
                "inner join rekening on rekening.kd_rek=dapurpembelian.kd_rek "+
                "where dapurpembelian.tgl_beli between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by dapurpembelian.tgl_beli",param);
        }
        
        if(tabModeBayarPesanToko.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPesanToko.jasper","report","::[ Pembayaran Pemesanan Toko ]::",
                "select toko_bayar_pemesanan.tgl_bayar,toko_bayar_pemesanan.no_faktur,tokosuplier.nama_suplier,toko_bayar_pemesanan.nip,"+
                "petugas.nama,toko_bayar_pemesanan.nama_bayar,toko_bayar_pemesanan.no_bukti,toko_bayar_pemesanan.besar_bayar "+
                "from toko_bayar_pemesanan inner join tokopemesanan on toko_bayar_pemesanan.no_faktur=tokopemesanan.no_faktur "+
                "inner join tokosuplier on tokopemesanan.kode_suplier=tokosuplier.kode_suplier "+
                "inner join petugas on petugas.nip=toko_bayar_pemesanan.nip "+
                "where toko_bayar_pemesanan.tgl_bayar between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by toko_bayar_pemesanan.tgl_bayar",param);
        }
        
        if(tabModePengadaanToko.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptPengeluaranBayarPengadaanToko.jasper","report","::[ Pembayaran Pengadaan Toko ]::",
                "select tokopembelian.tgl_beli,tokopembelian.no_faktur,tokosuplier.nama_suplier,tokopembelian.nip,"+
                "petugas.nama,rekening.nm_rek,tokopembelian.tagihan from tokopembelian "+
                "inner join tokosuplier on tokopembelian.kode_suplier=tokosuplier.kode_suplier "+
                "inner join petugas on petugas.nip=tokopembelian.nip "+
                "inner join rekening on rekening.kd_rek=tokopembelian.kd_rek "+
                "where tokopembelian.tgl_beli between '"+Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+"' order by tokopembelian.tgl_beli",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPengeluaranPengeluaran dialog = new DlgPengeluaranPengeluaran(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JLabel LCountBayarAset;
    private javax.swing.JLabel LCountBayarBebanHutang;
    private javax.swing.JLabel LCountBayarDapur;
    private javax.swing.JLabel LCountBayarJM;
    private javax.swing.JLabel LCountBayarNonMedis;
    private javax.swing.JLabel LCountBayarPesanToko;
    private javax.swing.JLabel LCountPengadaanDapur;
    private javax.swing.JLabel LCountPengadaanInventaris;
    private javax.swing.JLabel LCountPengadaanNonMedis;
    private javax.swing.JLabel LCountPengadaanObat;
    private javax.swing.JLabel LCountPengadaanToko;
    private javax.swing.JLabel LCountPengeluaranHarian;
    private javax.swing.JLabel LCountPesanObat;
    private javax.swing.JLabel LCountTotal;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll13;
    private widget.ScrollPane Scroll14;
    private widget.ScrollPane Scroll15;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private widget.Label jLabel19;
    private javax.swing.JLabel jLabel20;
    private widget.Label jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbBayarJM;
    private widget.Table tbBayarPesanAset;
    private widget.Table tbBayarPesanDapur;
    private widget.Table tbBayarPesanNonMedis;
    private widget.Table tbBayarPesanObat;
    private widget.Table tbBayarPesanToko;
    private widget.Table tbBebanHutang;
    private widget.Table tbPengadaanAsetInventaris;
    private widget.Table tbPengadaanDapur;
    private widget.Table tbPengadaanNonMedis;
    private widget.Table tbPengadaanObat;
    private widget.Table tbPengadaanToko;
    private widget.Table tbPengeluaranHarian;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        try{    
            Valid.tabelKosong(tabModeBayarPesanObat);
            bayarobat=0;
            ps=koneksi.prepareStatement(
                    "select bayar_pemesanan.tgl_bayar,bayar_pemesanan.no_faktur,datasuplier.nama_suplier,bayar_pemesanan.nip,"+
                    "petugas.nama,bayar_pemesanan.nama_bayar,bayar_pemesanan.no_bukti,bayar_pemesanan.besar_bayar "+
                    "from bayar_pemesanan inner join pemesanan on bayar_pemesanan.no_faktur=pemesanan.no_faktur "+
                    "inner join datasuplier on pemesanan.kode_suplier=datasuplier.kode_suplier "+
                    "inner join petugas on petugas.nip=bayar_pemesanan.nip "+
                    "where bayar_pemesanan.tgl_bayar between ? and ? order by bayar_pemesanan.tgl_bayar");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeBayarPesanObat.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getDouble(8)
                    });
                    bayarobat=bayarobat+rs.getDouble(8);
                }
            } catch (Exception e) {
                System.out.println("Notif Bayar Obat : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountPesanObat.setText(Valid.SetAngka(bayarobat));
            
            Valid.tabelKosong(tabModeBayarPesanNonMedis);
            bayarnonmedis=0;
            ps=koneksi.prepareStatement(
                    "select bayar_pemesanan_non_medis.tgl_bayar,bayar_pemesanan_non_medis.no_faktur,ipsrssuplier.nama_suplier,bayar_pemesanan_non_medis.nip,"+
                    "petugas.nama,bayar_pemesanan_non_medis.nama_bayar,bayar_pemesanan_non_medis.no_bukti,bayar_pemesanan_non_medis.besar_bayar "+
                    "from bayar_pemesanan_non_medis inner join ipsrspemesanan on bayar_pemesanan_non_medis.no_faktur=ipsrspemesanan.no_faktur "+
                    "inner join ipsrssuplier on ipsrspemesanan.kode_suplier=ipsrssuplier.kode_suplier "+
                    "inner join petugas on petugas.nip=bayar_pemesanan_non_medis.nip "+
                    "where bayar_pemesanan_non_medis.tgl_bayar between ? and ? order by bayar_pemesanan_non_medis.tgl_bayar");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeBayarPesanNonMedis.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getDouble(8)
                    });
                    bayarnonmedis=bayarnonmedis+rs.getDouble(8);
                }
            } catch (Exception e) {
                System.out.println("Notif Bayar Non Medis : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountBayarNonMedis.setText(Valid.SetAngka(bayarnonmedis));
            
            Valid.tabelKosong(tabModeBayarPesanAset);
            bayaraset=0;
            ps=koneksi.prepareStatement(
                    "select bayar_pemesanan_inventaris.tgl_bayar,bayar_pemesanan_inventaris.no_faktur,inventaris_suplier.nama_suplier,bayar_pemesanan_inventaris.nip,"+
                    "petugas.nama,bayar_pemesanan_inventaris.nama_bayar,bayar_pemesanan_inventaris.no_bukti,bayar_pemesanan_inventaris.besar_bayar "+
                    "from bayar_pemesanan_inventaris inner join inventaris_pemesanan on bayar_pemesanan_inventaris.no_faktur=inventaris_pemesanan.no_faktur "+
                    "inner join inventaris_suplier on inventaris_pemesanan.kode_suplier=inventaris_suplier.kode_suplier "+
                    "inner join petugas on petugas.nip=bayar_pemesanan_inventaris.nip "+
                    "where bayar_pemesanan_inventaris.tgl_bayar between ? and ? order by bayar_pemesanan_inventaris.tgl_bayar");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeBayarPesanAset.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getDouble(8)
                    });
                    bayaraset=bayaraset+rs.getDouble(8);
                }
            } catch (Exception e) {
                System.out.println("Notif Bayar Aset : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountBayarAset.setText(Valid.SetAngka(bayaraset));
            
            Valid.tabelKosong(tabModeBayarPesanDapur);
            bayardapur=0;
            ps=koneksi.prepareStatement(
                    "select bayar_pemesanan_dapur.tgl_bayar,bayar_pemesanan_dapur.no_faktur,dapursuplier.nama_suplier,bayar_pemesanan_dapur.nip,"+
                    "petugas.nama,bayar_pemesanan_dapur.nama_bayar,bayar_pemesanan_dapur.no_bukti,bayar_pemesanan_dapur.besar_bayar "+
                    "from bayar_pemesanan_dapur inner join dapurpemesanan on bayar_pemesanan_dapur.no_faktur=dapurpemesanan.no_faktur "+
                    "inner join dapursuplier on dapurpemesanan.kode_suplier=dapursuplier.kode_suplier "+
                    "inner join petugas on petugas.nip=bayar_pemesanan_dapur.nip "+
                    "where bayar_pemesanan_dapur.tgl_bayar between ? and ? order by bayar_pemesanan_dapur.tgl_bayar");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeBayarPesanDapur.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getDouble(8)
                    });
                    bayardapur=bayardapur+rs.getDouble(8);
                }
            } catch (Exception e) {
                System.out.println("Notif Bayar Dapur : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountBayarDapur.setText(Valid.SetAngka(bayardapur));
            
            Valid.tabelKosong(tabModeBayarJM);
            bayarjm=0;
            ps=koneksi.prepareStatement(
                    "select bayar_jm_dokter.tanggal,bayar_jm_dokter.no_bayar,bayar_jm_dokter.kd_dokter,dokter.nm_dokter,bayar_jm_dokter.nama_bayar, "+
                    "bayar_jm_dokter.besar_bayar from bayar_jm_dokter inner join dokter on bayar_jm_dokter.kd_dokter=dokter.kd_dokter "+
                    "where bayar_jm_dokter.tanggal between ? and ? order by bayar_jm_dokter.tanggal");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeBayarJM.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6)
                    });
                    bayarjm=bayarjm+rs.getDouble(6);
                }
            } catch (Exception e) {
                System.out.println("Notif Deposit : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountBayarJM.setText(Valid.SetAngka(bayarjm));
            
            Valid.tabelKosong(tabModePengeluaranHarian);
            pengeluaranharian=0;
            ps=koneksi.prepareStatement(
                    "select DATE_FORMAT(pengeluaran_harian.tanggal,'%Y-%m-%d'),pengeluaran_harian.no_keluar,pengeluaran_harian.nip,"+
                    "petugas.nama,kategori_pengeluaran_harian.nama_kategori,pengeluaran_harian.biaya from pengeluaran_harian "+
                    "inner join kategori_pengeluaran_harian on pengeluaran_harian.kode_kategori=kategori_pengeluaran_harian.kode_kategori "+
                    "inner join petugas on pengeluaran_harian.nip=petugas.nip "+
                    "where pengeluaran_harian.tanggal between ? and ? order by pengeluaran_harian.tanggal ");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+"")+" 23:59:59");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengeluaranHarian.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6)
                    });
                    pengeluaranharian=pengeluaranharian+rs.getDouble(6);
                }
            } catch (Exception e) {
                System.out.println("Notif Deposit : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountPengeluaranHarian.setText(Valid.SetAngka(pengeluaranharian));
            
            Valid.tabelKosong(tabModeBebanHutang);
            bayarbebanhutang=0;
            ps=koneksi.prepareStatement(
                    "select bayar_beban_hutang_lain.tgl_bayar, bayar_beban_hutang_lain.kode_pemberi_hutang,pemberi_hutang_lain.nama_pemberi_hutang, bayar_beban_hutang_lain.besar_cicilan,"+
                    "bayar_beban_hutang_lain.keterangan, bayar_beban_hutang_lain.no_hutang,bayar_beban_hutang_lain.kd_rek,bayar_beban_hutang_lain.nama_bayar,bayar_beban_hutang_lain.no_bukti "+
                    "from bayar_beban_hutang_lain inner join pemberi_hutang_lain on bayar_beban_hutang_lain.kode_pemberi_hutang=pemberi_hutang_lain.kode_pemberi_hutang where "+
                    "bayar_beban_hutang_lain.tgl_bayar between ? and ? order by bayar_beban_hutang_lain.tgl_bayar ");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeBebanHutang.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)
                    });
                    bayarbebanhutang=bayarbebanhutang+rs.getDouble(4);
                }
            } catch (Exception e) {
                System.out.println("Notif Deposit : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountBayarBebanHutang.setText(Valid.SetAngka(bayarbebanhutang));
            
            Valid.tabelKosong(tabModePengadaanObat);
            pengadaanobat=0;
            ps=koneksi.prepareStatement(
                    "select pembelian.tgl_beli,pembelian.no_faktur,datasuplier.nama_suplier,pembelian.nip,"+
                    "petugas.nama,rekening.nm_rek,pembelian.tagihan from pembelian "+
                    "inner join datasuplier on pembelian.kode_suplier=datasuplier.kode_suplier "+
                    "inner join petugas on petugas.nip=pembelian.nip "+
                    "inner join rekening on rekening.kd_rek=pembelian.kd_rek "+
                    "where pembelian.tgl_beli between ? and ? order by pembelian.tgl_beli");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengadaanObat.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDouble(7)
                    });
                    pengadaanobat=pengadaanobat+rs.getDouble(7);
                }
            } catch (Exception e) {
                System.out.println("Notif Pengadaan Obat : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountPengadaanObat.setText(Valid.SetAngka(pengadaanobat));
            
            Valid.tabelKosong(tabModePengadaanNonMedis);
            pengadaannonmedis=0;
            ps=koneksi.prepareStatement(
                    "select ipsrspembelian.tgl_beli,ipsrspembelian.no_faktur,ipsrssuplier.nama_suplier,ipsrspembelian.nip,"+
                    "petugas.nama,rekening.nm_rek,ipsrspembelian.tagihan from ipsrspembelian "+
                    "inner join ipsrssuplier on ipsrspembelian.kode_suplier=ipsrssuplier.kode_suplier "+
                    "inner join petugas on petugas.nip=ipsrspembelian.nip "+
                    "inner join rekening on rekening.kd_rek=ipsrspembelian.kd_rek "+
                    "where ipsrspembelian.tgl_beli between ? and ? order by ipsrspembelian.tgl_beli");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengadaanNonMedis.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDouble(7)
                    });
                    pengadaannonmedis=pengadaannonmedis+rs.getDouble(7);
                }
            } catch (Exception e) {
                System.out.println("Notif Pengadaan Non Medis : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountPengadaanNonMedis.setText(Valid.SetAngka(pengadaannonmedis));
            
            Valid.tabelKosong(tabModePengadaanInventaris);
            pengadaaninventaris=0;
            ps=koneksi.prepareStatement(
                    "select inventaris_pembelian.tgl_beli,inventaris_pembelian.no_faktur,inventaris_suplier.nama_suplier,inventaris_pembelian.nip,"+
                    "petugas.nama,rekening.nm_rek,inventaris_pembelian.tagihan from inventaris_pembelian "+
                    "inner join inventaris_suplier on inventaris_pembelian.kode_suplier=inventaris_suplier.kode_suplier "+
                    "inner join petugas on petugas.nip=inventaris_pembelian.nip "+
                    "inner join rekening on rekening.kd_rek=inventaris_pembelian.kd_rek "+
                    "where inventaris_pembelian.tgl_beli between ? and ? order by inventaris_pembelian.tgl_beli");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengadaanInventaris.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDouble(7)
                    });
                    pengadaaninventaris=pengadaaninventaris+rs.getDouble(7);
                }
            } catch (Exception e) {
                System.out.println("Notif Pengadaan Inventaris : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountPengadaanInventaris.setText(Valid.SetAngka(pengadaaninventaris));
            
            Valid.tabelKosong(tabModePengadaanDapur);
            pengadaandapur=0;
            ps=koneksi.prepareStatement(
                    "select dapurpembelian.tgl_beli,dapurpembelian.no_faktur,dapursuplier.nama_suplier,dapurpembelian.nip,"+
                    "petugas.nama,rekening.nm_rek,dapurpembelian.tagihan from dapurpembelian "+
                    "inner join dapursuplier on dapurpembelian.kode_suplier=dapursuplier.kode_suplier "+
                    "inner join petugas on petugas.nip=dapurpembelian.nip "+
                    "inner join rekening on rekening.kd_rek=dapurpembelian.kd_rek "+
                    "where dapurpembelian.tgl_beli between ? and ? order by dapurpembelian.tgl_beli");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengadaanDapur.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDouble(7)
                    });
                    pengadaandapur=pengadaandapur+rs.getDouble(7);
                }
            } catch (Exception e) {
                System.out.println("Notif Pengadaan Dapur : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountPengadaanDapur.setText(Valid.SetAngka(pengadaandapur));
            
            Valid.tabelKosong(tabModeBayarPesanToko);
            bayartoko=0;
            ps=koneksi.prepareStatement(
                    "select toko_bayar_pemesanan.tgl_bayar,toko_bayar_pemesanan.no_faktur,tokosuplier.nama_suplier,toko_bayar_pemesanan.nip,"+
                    "petugas.nama,toko_bayar_pemesanan.nama_bayar,toko_bayar_pemesanan.no_bukti,toko_bayar_pemesanan.besar_bayar "+
                    "from toko_bayar_pemesanan inner join tokopemesanan on toko_bayar_pemesanan.no_faktur=tokopemesanan.no_faktur "+
                    "inner join tokosuplier on tokopemesanan.kode_suplier=tokosuplier.kode_suplier "+
                    "inner join petugas on petugas.nip=toko_bayar_pemesanan.nip "+
                    "where toko_bayar_pemesanan.tgl_bayar between ? and ? order by toko_bayar_pemesanan.tgl_bayar");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeBayarPesanToko.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getDouble(8)
                    });
                    bayartoko=bayartoko+rs.getDouble(8);
                }
            } catch (Exception e) {
                System.out.println("Notif Bayar Toko : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountBayarPesanToko.setText(Valid.SetAngka(bayartoko));
            
            Valid.tabelKosong(tabModePengadaanToko);
            pengadaantoko=0;
            ps=koneksi.prepareStatement(
                    "select tokopembelian.tgl_beli,tokopembelian.no_faktur,tokosuplier.nama_suplier,tokopembelian.nip,"+
                    "petugas.nama,rekening.nm_rek,tokopembelian.tagihan from tokopembelian "+
                    "inner join tokosuplier on tokopembelian.kode_suplier=tokosuplier.kode_suplier "+
                    "inner join petugas on petugas.nip=tokopembelian.nip "+
                    "inner join rekening on rekening.kd_rek=tokopembelian.kd_rek "+
                    "where tokopembelian.tgl_beli between ? and ? order by tokopembelian.tgl_beli");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem().toString()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem().toString()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModePengadaanToko.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDouble(7)
                    });
                    pengadaantoko=pengadaantoko+rs.getDouble(7);
                }
            } catch (Exception e) {
                System.out.println("Notif Pengadaan Toko : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            LCountPengadaanToko.setText(Valid.SetAngka(pengadaantoko));
            
            LCountTotal.setText(Valid.SetAngka(bayarobat+bayarnonmedis+bayaraset+bayardapur+bayarjm+pengeluaranharian+bayarbebanhutang+pengadaanobat+pengadaannonmedis+pengadaaninventaris+pengadaandapur+bayartoko+pengadaantoko));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    

}
