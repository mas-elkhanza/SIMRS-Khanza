

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public final class DlgDetailTindakan extends javax.swing.JDialog {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private DefaultTableModel tabModeRalanDokter,tabModeRalanParamedis,
            tabModeRalanDokterParamedis,tabModeRanapDokter,tabModeRanapParamedis,
            tabModeRanapDokterParamedis,tabModeRadiologi,tabModeLaborat,
            tabModeDetailLaborat,tabModeOperasi;
    private validasi Valid=new validasi();
    private ResultSet rs;
    private PreparedStatement ps;
    private String sql;
    private int i=0;
    private double material=0,bhp=0,jmdokter=0,jmpetugas=0,jmperujuk=0,kso=0,
            menejemen=0,total=0,biayaoperator1=0,biayaoperator2=0, 
            biayaoperator3=0,biayaasisten_operator1=0,biayaasisten_operator2=0,
            biayaasisten_operator3=0,biayainstrumen=0,biayadokter_anak=0,
            biayaperawaat_resusitas=0,biayadokter_anestesi=0,biayaasisten_anestesi=0,
            biayaasisten_anestesi2=0,biayabidan=0,biayabidan2=0,biayabidan3=0,
            biayaperawat_luar=0,biayaalat=0,biayasewaok=0,akomodasi=0,
            bagian_rs=0,biaya_omloop=0,biaya_omloop2=0,biaya_omloop3=0,
            biaya_omloop4=0,biaya_omloop5=0,biayasarpras=0,biaya_dokter_pjanak=0,
            biaya_dokter_umum=0;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgDetailTindakan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        
        tabModeRalanDokter=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Kd.Tnd","Perawatan/Tindakan","Kode Dokter",
            "Dokter Yg Menangani","Tanggal","Jam","Cara Bayar","Ruangan",
            "Jasa Sarana","Paket BHP","JM Dokter","KSO","Menejemen","Total"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRalanDokter.setModel(tabModeRalanDokter);
        tbRalanDokter.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRalanDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbRalanDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(55);
            }else if(i==10){
                column.setPreferredWidth(110);
            }else if(i==11){
                column.setPreferredWidth(130);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(75);
            }else if(i==14){
                column.setPreferredWidth(75);
            }else if(i==15){
                column.setPreferredWidth(75);
            }else if(i==16){
                column.setPreferredWidth(75);
            }else if(i==17){
                column.setPreferredWidth(80);
            }
        }
        tbRalanDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRalanParamedis=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Kd.Tnd","Perawatan/Tindakan","NIP",
            "Paramedis Yg Menangani","Tanggal","Jam","Cara Bayar","Ruangan",
            "Jasa Sarana","Paket BHP","JM Paramedis","KSO","Menejemen","Total"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRalanParamedis.setModel(tabModeRalanParamedis);
        tbRalanParamedis.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRalanParamedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbRalanParamedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(55);
            }else if(i==10){
                column.setPreferredWidth(110);
            }else if(i==11){
                column.setPreferredWidth(130);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(75);
            }else if(i==14){
                column.setPreferredWidth(75);
            }else if(i==15){
                column.setPreferredWidth(75);
            }else if(i==16){
                column.setPreferredWidth(75);
            }else if(i==17){
                column.setPreferredWidth(80);
            }
        }
        tbRalanParamedis.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRalanDokterParamedis=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Kd.Tnd","Perawatan/Tindakan","Kode Dokter",
            "Dokter Yg Menangani","NIP","Paramedis Yg Menangani","Tanggal","Jam","Cara Bayar","Ruangan",
            "Jasa Sarana","Paket BHP","JM Dokter","JM Paramedis","KSO","Menejemen","Total"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRalanDokterParamedis.setModel(tabModeRalanDokterParamedis);
        tbRalanDokterParamedis.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRalanDokterParamedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbRalanDokterParamedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(55);
            }else if(i==12){
                column.setPreferredWidth(110);
            }else if(i==13){
                column.setPreferredWidth(130);
            }else if(i==14){
                column.setPreferredWidth(75);
            }else if(i==15){
                column.setPreferredWidth(75);
            }else if(i==16){
                column.setPreferredWidth(75);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(75);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else if(i==20){
                column.setPreferredWidth(80);
            }
        }
        tbRalanDokterParamedis.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRanapDokter=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Kd.Tnd","Perawatan/Tindakan","Kode Dokter",
            "Dokter Yg Menangani","Tanggal","Jam","Cara Bayar","Ruangan",
            "Jasa Sarana","Paket BHP","JM Dokter","KSO","Menejemen","Total"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRanapDokter.setModel(tabModeRanapDokter);
        tbRanapDokter.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRanapDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbRanapDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(55);
            }else if(i==10){
                column.setPreferredWidth(110);
            }else if(i==11){
                column.setPreferredWidth(130);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(75);
            }else if(i==14){
                column.setPreferredWidth(75);
            }else if(i==15){
                column.setPreferredWidth(75);
            }else if(i==16){
                column.setPreferredWidth(75);
            }else if(i==17){
                column.setPreferredWidth(80);
            }
        }
        tbRanapDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRanapParamedis=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Kd.Tnd","Perawatan/Tindakan","NIP",
            "Paramedis Yg Menangani","Tanggal","Jam","Cara Bayar","Ruangan",
            "Jasa Sarana","Paket BHP","JM Paramedis","KSO","Menejemen","Total"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRanapParamedis.setModel(tabModeRanapParamedis);
        tbRanapParamedis.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRanapParamedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbRanapParamedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(55);
            }else if(i==10){
                column.setPreferredWidth(110);
            }else if(i==11){
                column.setPreferredWidth(130);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(75);
            }else if(i==14){
                column.setPreferredWidth(75);
            }else if(i==15){
                column.setPreferredWidth(75);
            }else if(i==16){
                column.setPreferredWidth(75);
            }else if(i==17){
                column.setPreferredWidth(80);
            }
        }
        tbRanapParamedis.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRanapDokterParamedis=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Kd.Tnd","Perawatan/Tindakan","Kode Dokter",
            "Dokter Yg Menangani","NIP","Paramedis Yg Menangani","Tanggal","Jam","Cara Bayar","Ruangan",
            "Jasa Sarana","Paket BHP","JM Dokter","JM Paramedis","KSO","Menejemen","Total"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRanapDokterParamedis.setModel(tabModeRanapDokterParamedis);
        tbRanapDokterParamedis.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRanapDokterParamedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbRanapDokterParamedis.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(55);
            }else if(i==12){
                column.setPreferredWidth(110);
            }else if(i==13){
                column.setPreferredWidth(130);
            }else if(i==14){
                column.setPreferredWidth(75);
            }else if(i==15){
                column.setPreferredWidth(75);
            }else if(i==16){
                column.setPreferredWidth(75);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(75);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else if(i==20){
                column.setPreferredWidth(80);
            }
        }
        tbRanapDokterParamedis.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRadiologi=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Kd.Prk","Pemeriksaan","Kode P.J.",
            "Dokter P.J.Rad","NIP","Petugas Rad","Kode Perujuk","Dokter Perujuk","Tanggal","Jam","Cara Bayar","Ruangan",
            "Jasa Sarana","Paket BHP","JM P.J.Rad","JM Petugas","JM Perujuk","KSO","Menejemen","Total"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbRadiologi.setModel(tabModeRadiologi);
        tbRadiologi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRadiologi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbRadiologi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(75);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(75);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else if(i==20){
                column.setPreferredWidth(75);
            }else if(i==21){
                column.setPreferredWidth(75);
            }else if(i==22){
                column.setPreferredWidth(75);
            }else if(i==23){
                column.setPreferredWidth(80);
            }
        }
        tbRadiologi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeLaborat=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Kd.Prk","Pemeriksaan","Kode P.J.",
            "Dokter P.J.Lab","NIP","Petugas Lab","Kode Perujuk","Dokter Perujuk","Tanggal","Jam","Cara Bayar","Ruangan",
            "Jasa Sarana","Paket BHP","JM P.J.Lab","JM Petugas","JM Perujuk","KSO","Menejemen","Total"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbLaborat.setModel(tabModeLaborat);
        tbLaborat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLaborat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbLaborat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(75);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(75);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else if(i==20){
                column.setPreferredWidth(75);
            }else if(i==21){
                column.setPreferredWidth(75);
            }else if(i==22){
                column.setPreferredWidth(75);
            }else if(i==23){
                column.setPreferredWidth(80);
            }
        }
        tbLaborat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDetailLaborat=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Id","Pemeriksaan","Kode P.J.",
            "Dokter P.J.Lab","NIP","Petugas Lab","Kode Perujuk","Dokter Perujuk","Tanggal","Jam","Cara Bayar","Ruangan",
            "Jasa Sarana","Paket BHP","JM P.J.Lab","JM Petugas","JM Perujuk","KSO","Menejemen","Total"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDetailLaborat.setModel(tabModeDetailLaborat);
        tbDetailLaborat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailLaborat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbDetailLaborat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(75);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(55);
            }else if(i==14){
                column.setPreferredWidth(110);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(75);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(75);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else if(i==20){
                column.setPreferredWidth(75);
            }else if(i==21){
                column.setPreferredWidth(75);
            }else if(i==22){
                column.setPreferredWidth(75);
            }else if(i==23){
                column.setPreferredWidth(80);
            }
        }
        tbDetailLaborat.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeOperasi=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Kode Paket",
            "Paket Operasi/VK","Tanggal","Jam","Cara Bayar","Ruangan",
            "Operator 1","JM Operator 1","Operator 2","JM Operator 2",
            "Operator 3","JM Operator 3", "Asisten Operator 1","JM AO 1",
            "Asisten Operator 2","JM AO 2","Asisten Operator 3", "JM AO 3",
            "Instrumen","JM Instrumen","Dokter Anak", "JM dr Anak",
            "Perawat Resusitas","JM P.R.","Dokter Anestesi","JM dr Anastesi",
            "Asisten Anestesi 1", "JM A.A. 1","Asisten Anestesi 2","JM A.A. 2",
            "Bidan 1","JM Bidan 1","Bidan 2","JM Bidan 2","Bidan 3","JM Bidan 3",
            "Perawat Luar","JM P.L.","Onloop 1","JM Onloop 1","Onloop 2","JM Onloop 2",
            "Onloop 3","JM Onloop 3", "Onloop 4","JM Onloop 4", "Onloop 5","JM Onloop 5",
            "Dokter P.J. Anak","JM dr P.J. Anak","Dokter Umum", "JM dr Umum",
            "Sewa Alat", "Sewa OK/VK", "Akomodasi", "N.M.S.",  "Sarpras","Total" 
        }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbOperasi.setModel(tabModeOperasi);
        tbOperasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbOperasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 62; i++) {
            TableColumn column = tbOperasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(75);
            }else if(i==7){
                column.setPreferredWidth(55);
            }else if(i==8){
                column.setPreferredWidth(120);
            }else if(i==9){
                column.setPreferredWidth(120);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(80);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(80);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(80);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(80);
            }else if(i==36){
                column.setPreferredWidth(150);
            }else if(i==37){
                column.setPreferredWidth(80);
            }else if(i==38){
                column.setPreferredWidth(150);
            }else if(i==39){
                column.setPreferredWidth(80);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(80);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(80);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(80);
            }else if(i==46){
                column.setPreferredWidth(150);
            }else if(i==47){
                column.setPreferredWidth(80);
            }else if(i==48){
                column.setPreferredWidth(150);
            }else if(i==49){
                column.setPreferredWidth(80);
            }else if(i==50){
                column.setPreferredWidth(150);
            }else if(i==51){
                column.setPreferredWidth(80);
            }else if(i==52){
                column.setPreferredWidth(150);
            }else if(i==53){
                column.setPreferredWidth(80);
            }else if(i==54){
                column.setPreferredWidth(150);
            }else if(i==55){
                column.setPreferredWidth(80);
            }else if(i==56){
                column.setPreferredWidth(80);
            }else if(i==57){
                column.setPreferredWidth(80);
            }else if(i==58){
                column.setPreferredWidth(80);
            }else if(i==59){
                column.setPreferredWidth(80);
            }else if(i==60){
                column.setPreferredWidth(80);
            }else if(i==61){
                column.setPreferredWidth(100);
            }
        }
        tbOperasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
            
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
        panelGlass5 = new widget.panelisi();
        label9 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label11 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbRalanDokter = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbRalanParamedis = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbRalanDokterParamedis = new widget.Table();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbOperasi = new widget.Table();
        internalFrame6 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbRanapDokter = new widget.Table();
        internalFrame7 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbRanapParamedis = new widget.Table();
        internalFrame8 = new widget.InternalFrame();
        Scroll6 = new widget.ScrollPane();
        tbRanapDokterParamedis = new widget.Table();
        internalFrame9 = new widget.InternalFrame();
        Scroll7 = new widget.ScrollPane();
        tbRadiologi = new widget.Table();
        internalFrame10 = new widget.InternalFrame();
        Scroll8 = new widget.ScrollPane();
        tbLaborat = new widget.Table();
        internalFrame11 = new widget.InternalFrame();
        Scroll9 = new widget.ScrollPane();
        tbDetailLaborat = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Detail Tindakan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Tanggal :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(label9);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelGlass5.add(Tgl2);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass5.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelGlass5.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
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
        panelGlass5.add(BtnAll);

        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(20, 23));
        panelGlass5.add(label11);

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
        panelGlass5.add(BtnPrint);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbRalanDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRalanDokter.setName("tbRalanDokter"); // NOI18N
        tbRalanDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRalanDokterMouseClicked(evt);
            }
        });
        tbRalanDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRalanDokterKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbRalanDokter);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Ralan Dokter", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbRalanParamedis.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRalanParamedis.setName("tbRalanParamedis"); // NOI18N
        tbRalanParamedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRalanParamedisMouseClicked(evt);
            }
        });
        tbRalanParamedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRalanParamedisKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbRalanParamedis);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Ralan Paramedis", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbRalanDokterParamedis.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRalanDokterParamedis.setName("tbRalanDokterParamedis"); // NOI18N
        tbRalanDokterParamedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRalanDokterParamedisMouseClicked(evt);
            }
        });
        tbRalanDokterParamedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRalanDokterParamedisKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbRalanDokterParamedis);

        internalFrame4.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Ralan Dokter & Paramedis", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbOperasi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbOperasi.setName("tbOperasi"); // NOI18N
        tbOperasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbOperasiMouseClicked(evt);
            }
        });
        tbOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbOperasiKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbOperasi);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Operasi & VK", internalFrame5);

        internalFrame6.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbRanapDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRanapDokter.setName("tbRanapDokter"); // NOI18N
        tbRanapDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRanapDokterMouseClicked(evt);
            }
        });
        tbRanapDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRanapDokterKeyPressed(evt);
            }
        });
        Scroll4.setViewportView(tbRanapDokter);

        internalFrame6.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Ranap Dokter", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbRanapParamedis.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRanapParamedis.setName("tbRanapParamedis"); // NOI18N
        tbRanapParamedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRanapParamedisMouseClicked(evt);
            }
        });
        tbRanapParamedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRanapParamedisKeyPressed(evt);
            }
        });
        Scroll5.setViewportView(tbRanapParamedis);

        internalFrame7.add(Scroll5, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Ranap Paramedis", internalFrame7);

        internalFrame8.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbRanapDokterParamedis.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRanapDokterParamedis.setName("tbRanapDokterParamedis"); // NOI18N
        tbRanapDokterParamedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRanapDokterParamedisMouseClicked(evt);
            }
        });
        tbRanapDokterParamedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRanapDokterParamedisKeyPressed(evt);
            }
        });
        Scroll6.setViewportView(tbRanapDokterParamedis);

        internalFrame8.add(Scroll6, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Ranap Dokter & Paramedis", internalFrame8);

        internalFrame9.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame9.setBorder(null);
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbRadiologi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRadiologi.setName("tbRadiologi"); // NOI18N
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
        Scroll7.setViewportView(tbRadiologi);

        internalFrame9.add(Scroll7, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Pemeriksaan Radiologi", internalFrame9);

        internalFrame10.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame10.setBorder(null);
        internalFrame10.setName("internalFrame10"); // NOI18N
        internalFrame10.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbLaborat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLaborat.setName("tbLaborat"); // NOI18N
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
        Scroll8.setViewportView(tbLaborat);

        internalFrame10.add(Scroll8, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Pemeriksaan Laboratorium", internalFrame10);

        internalFrame11.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame11.setBorder(null);
        internalFrame11.setName("internalFrame11"); // NOI18N
        internalFrame11.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbDetailLaborat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailLaborat.setName("tbDetailLaborat"); // NOI18N
        tbDetailLaborat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailLaboratMouseClicked(evt);
            }
        });
        tbDetailLaborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailLaboratKeyPressed(evt);
            }
        });
        Scroll9.setViewportView(tbDetailLaborat);

        internalFrame11.add(Scroll9, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Detail Pemeriksaan Laboratorium", internalFrame11);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,Tgl1,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if(tabModeRalanDokter.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeRalanDokter.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailTindakanRalanDokter.jasper",param,"::[ Detail Tindakan Ralan Yang Ditangani Dokter ]::");                    
                }   break;
            case 1:
                if(tabModeRalanParamedis.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeRalanParamedis.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailTindakanRalanParamedis.jasper",param,"::[ Detail Tindakan Ralan Yang Ditangani Petugas ]::");                    
                }   break;
            case 2:
                if(tabModeRalanDokterParamedis.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeRalanDokterParamedis.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailTindakanRalanDokterParamedis.jasper",param,"::[ Detail Tindakan Ralan Yang Ditangani Dokter & Petugas ]::");                    
                }   break;
            case 3:
                if(tabModeOperasi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeOperasi.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailTindakanOperasi.jasper",param,"::[ Detail Tindakan Operasi ]::");                    
                }   break;
            case 4:
                if(tabModeRanapDokter.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeRanapDokter.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailTindakanRanapDokter.jasper",param,"::[ Detail Tindakan Ranap Yang Ditangani Dokter ]::");                    
                }   break;
            case 5:
                if(tabModeRanapParamedis.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeRanapParamedis.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailTindakanRanapParamedis.jasper",param,"::[ Detail Tindakan Ranap Yang Ditangani Paramedis ]::");                    
                }   break;
            case 6:
                if(tabModeRanapDokterParamedis.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeRanapDokterParamedis.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailTindakanRanapDokterParamedis.jasper",param,"::[ Detail Tindakan Ranap Yang Ditangani Dokter & Paramedis ]::");                    
                }   break;
            case 7:
                if(tabModeRadiologi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeRadiologi.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailRadiologi.jasper",param,"::[ Detail Pemeriksaan Radiologi ]::");                    
                }   break;
            case 8:
                if(tabModeLaborat.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeLaborat.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailLaborat.jasper",param,"::[ Detail Pemeriksaan Laborat ]::");                    
                }   break;
            case 9:
                if(tabModeDetailLaborat.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeDetailLaborat.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailDetailLaborat.jasper",param,"::[ Detail Pemeriksaan Laborat ]::");                    
                }   break;
            default:
                    break;
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        
    }//GEN-LAST:event_Tgl2KeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampil();
                break;
            case 1:
                tampil2();
                break;
            case 2:
                tampil3();
                break; 
            case 3:
                tampil4();
                break;
            case 4:
                tampil5();
                break;
            case 5:
                tampil6();
                break;
            case 6:
                tampil7();
                break;
            case 7:
                tampil8();
                break;
            case 8:
                tampil9();
                break;
            case 9:
                tampil10();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TabRawatMouseClicked(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void tbRalanDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRalanDokterMouseClicked
        
    }//GEN-LAST:event_tbRalanDokterMouseClicked

    private void tbRalanDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRalanDokterKeyPressed
        
    }//GEN-LAST:event_tbRalanDokterKeyPressed

    private void tbRalanParamedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRalanParamedisMouseClicked
        
    }//GEN-LAST:event_tbRalanParamedisMouseClicked

    private void tbRalanParamedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRalanParamedisKeyPressed
        
    }//GEN-LAST:event_tbRalanParamedisKeyPressed

    private void tbRalanDokterParamedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRalanDokterParamedisMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRalanDokterParamedisMouseClicked

    private void tbRalanDokterParamedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRalanDokterParamedisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRalanDokterParamedisKeyPressed

    private void tbOperasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbOperasiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbOperasiMouseClicked

    private void tbOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbOperasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbOperasiKeyPressed

    private void tbRanapDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRanapDokterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRanapDokterMouseClicked

    private void tbRanapDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRanapDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRanapDokterKeyPressed

    private void tbRanapParamedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRanapParamedisMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRanapParamedisMouseClicked

    private void tbRanapParamedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRanapParamedisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRanapParamedisKeyPressed

    private void tbRanapDokterParamedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRanapDokterParamedisMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRanapDokterParamedisMouseClicked

    private void tbRanapDokterParamedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRanapDokterParamedisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRanapDokterParamedisKeyPressed

    private void tbRadiologiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadiologiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiMouseClicked

    private void tbRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRadiologiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiKeyPressed

    private void tbLaboratMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLaboratMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLaboratMouseClicked

    private void tbLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLaboratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLaboratKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void tbDetailLaboratMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailLaboratMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailLaboratMouseClicked

    private void tbDetailLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailLaboratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailLaboratKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDetailTindakan dialog = new DlgDetailTindakan(new javax.swing.JFrame(), true);
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
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame10;
    private widget.InternalFrame internalFrame11;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.Label label9;
    private widget.panelisi panelGlass5;
    private widget.Table tbDetailLaborat;
    private widget.Table tbLaborat;
    private widget.Table tbOperasi;
    private widget.Table tbRadiologi;
    private widget.Table tbRalanDokter;
    private widget.Table tbRalanDokterParamedis;
    private widget.Table tbRalanParamedis;
    private widget.Table tbRanapDokter;
    private widget.Table tbRanapDokterParamedis;
    private widget.Table tbRanapParamedis;
    // End of variables declaration//GEN-END:variables

    public void tampil(){     
        Valid.tabelKosong(tabModeRalanDokter);
        try{
            ps=koneksi.prepareStatement(
                   "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                   "pasien.nm_pasien,rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                   "rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.tgl_perawatan,"+
                   "rawat_jl_dr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                   "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                   "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                   "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                   "dokter inner join rawat_jl_dr inner join poliklinik inner join penjab "+
                   "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                   "where rawat_jl_dr.tgl_perawatan between ? and ? and rawat_jl_dr.no_rawat like ? or "+
                    "rawat_jl_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_jl_dr.tgl_perawatan between ? and ? and pasien.nm_pasien like ? or "+
                    "rawat_jl_dr.tgl_perawatan between ? and ? and jns_perawatan.nm_perawatan like ? or "+
                    "rawat_jl_dr.tgl_perawatan between ? and ? and rawat_jl_dr.kd_dokter like ? or "+
                    "rawat_jl_dr.tgl_perawatan between ? and ? and dokter.nm_dokter like ? or "+
                    "rawat_jl_dr.tgl_perawatan between ? and ? and penjab.png_jawab like ? or "+
                    "rawat_jl_dr.tgl_perawatan between ? and ? and poliklinik.nm_poli like ? "+
                   " order by rawat_jl_dr.no_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                material=0;bhp=0;jmdokter=0;kso=0;menejemen=0;total=0;
                while(rs.next()){
                    material=material+rs.getDouble("material");
                    bhp=bhp+rs.getDouble("bhp");
                    jmdokter=jmdokter+rs.getDouble("tarif_tindakandr");
                    kso=kso+rs.getDouble("kso");
                    menejemen=menejemen+rs.getDouble("menejemen");
                    total=total+rs.getDouble("biaya_rawat");
                    tabModeRalanDokter.addRow(new Object[]{
                        i,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        rs.getString(9),rs.getString(10),rs.getString(11),rs.getDouble(12),
                        rs.getDouble(13),rs.getDouble(14),rs.getDouble(15),rs.getDouble(16),
                        rs.getDouble(17)
                    });
                    i++;
                }
                if(total>0){
                    tabModeRalanDokter.addRow(new Object[]{
                        "","","","","","","","","","","","Jumlah Total :",material,
                        bhp,jmdokter,kso,menejemen,total
                    });
                }
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    public void tampil2(){     
        Valid.tabelKosong(tabModeRalanParamedis);
        try{
            ps=koneksi.prepareStatement(
                   "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                   "pasien.nm_pasien,rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                   "rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.tgl_perawatan,"+
                   "rawat_jl_pr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                   "rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.tarif_tindakanpr,"+
                   "rawat_jl_pr.kso,rawat_jl_pr.menejemen,rawat_jl_pr.biaya_rawat "+
                   "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                   "petugas inner join rawat_jl_pr inner join poliklinik inner join penjab "+
                   "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_pr.nip=petugas.nip "+
                   "where rawat_jl_pr.tgl_perawatan between ? and ? and rawat_jl_pr.no_rawat like ? or "+
                    "rawat_jl_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_jl_pr.tgl_perawatan between ? and ? and pasien.nm_pasien like ? or "+
                    "rawat_jl_pr.tgl_perawatan between ? and ? and jns_perawatan.nm_perawatan like ? or "+
                    "rawat_jl_pr.tgl_perawatan between ? and ? and rawat_jl_pr.nip like ? or "+
                    "rawat_jl_pr.tgl_perawatan between ? and ? and petugas.nama like ? or "+
                    "rawat_jl_pr.tgl_perawatan between ? and ? and penjab.png_jawab like ? or "+
                    "rawat_jl_pr.tgl_perawatan between ? and ? and poliklinik.nm_poli like ? "+
                   " order by rawat_jl_pr.no_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                material=0;bhp=0;jmpetugas=0;kso=0;menejemen=0;total=0;
                while(rs.next()){
                    material=material+rs.getDouble("material");
                    bhp=bhp+rs.getDouble("bhp");
                    jmpetugas=jmpetugas+rs.getDouble("tarif_tindakanpr");
                    kso=kso+rs.getDouble("kso");
                    menejemen=menejemen+rs.getDouble("menejemen");
                    total=total+rs.getDouble("biaya_rawat");
                    tabModeRalanParamedis.addRow(new Object[]{
                        i,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        rs.getString(9),rs.getString(10),rs.getString(11),rs.getDouble(12),
                        rs.getDouble(13),rs.getDouble(14),rs.getDouble(15),rs.getDouble(16),
                        rs.getDouble(17)
                    });
                    i++;
                }
                if(total>0){
                    tabModeRalanParamedis.addRow(new Object[]{
                        "","","","","","","","","","","","Jumlah Total :",material,
                        bhp,jmpetugas,kso,menejemen,total
                    });
                }
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil3(){     
        Valid.tabelKosong(tabModeRalanDokterParamedis);
        try{
            ps=koneksi.prepareStatement(
                   "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                   "pasien.nm_pasien,rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                   "rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,"+
                   "rawat_jl_drpr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                   "rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,"+
                   "rawat_jl_drpr.kso,rawat_jl_drpr.menejemen,rawat_jl_drpr.biaya_rawat "+
                   "from pasien inner join reg_periksa inner join jns_perawatan inner join "+
                   "dokter inner join rawat_jl_drpr inner join poliklinik inner join penjab "+
                   "inner join petugas on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "and rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                   "and rawat_jl_drpr.nip=petugas.nip "+
                   "where rawat_jl_drpr.tgl_perawatan between ? and ? and rawat_jl_drpr.no_rawat like ? or "+
                    "rawat_jl_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_jl_drpr.tgl_perawatan between ? and ? and pasien.nm_pasien like ? or "+
                    "rawat_jl_drpr.tgl_perawatan between ? and ? and jns_perawatan.nm_perawatan like ? or "+
                    "rawat_jl_drpr.tgl_perawatan between ? and ? and rawat_jl_drpr.kd_dokter like ? or "+
                    "rawat_jl_drpr.tgl_perawatan between ? and ? and dokter.nm_dokter like ? or "+
                    "rawat_jl_drpr.tgl_perawatan between ? and ? and rawat_jl_drpr.nip like ? or "+
                    "rawat_jl_drpr.tgl_perawatan between ? and ? and petugas.nama like ? or "+
                    "rawat_jl_drpr.tgl_perawatan between ? and ? and penjab.png_jawab like ? or "+
                    "rawat_jl_drpr.tgl_perawatan between ? and ? and poliklinik.nm_poli like ? "+
                   " order by rawat_jl_drpr.no_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                material=0;bhp=0;jmdokter=0;jmpetugas=0;kso=0;menejemen=0;total=0;
                while(rs.next()){
                    material=material+rs.getDouble("material");
                    bhp=bhp+rs.getDouble("bhp");
                    jmdokter=jmdokter+rs.getDouble("tarif_tindakandr");
                    jmpetugas=jmpetugas+rs.getDouble("tarif_tindakanpr");
                    kso=kso+rs.getDouble("kso");
                    menejemen=menejemen+rs.getDouble("menejemen");
                    total=total+rs.getDouble("biaya_rawat");
                    tabModeRalanDokterParamedis.addRow(new Object[]{
                        i,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getDouble(14),rs.getDouble(15),rs.getDouble(16),
                        rs.getDouble(17),rs.getDouble(18),rs.getDouble(19),rs.getDouble(20)
                    });
                    i++;
                }
                if(total>0){
                    tabModeRalanDokterParamedis.addRow(new Object[]{
                        "","","","","","","","","","","","","","Jumlah Total :",material,
                        bhp,jmdokter,jmpetugas,kso,menejemen,total
                    });
                }
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil4(){     
        Valid.tabelKosong(tabModeOperasi);
        try{ps=koneksi.prepareStatement(
                   "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                   "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                   "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                   "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator1) as operator1,operasi.biayaoperator1, "+
                   "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator2) as operator2,operasi.biayaoperator2, "+
                   "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator3) as operator3,operasi.biayaoperator3,"+
                   "(select nama from petugas where petugas.nip=operasi.asisten_operator1) as asisten_operator1,operasi.biayaasisten_operator1, "+
                   "(select nama from petugas where petugas.nip=operasi.asisten_operator2) as asisten_operator2,operasi.biayaasisten_operator2, "+
                   "(select nama from petugas where petugas.nip=operasi.asisten_operator3) as asisten_operator3,operasi.biayaasisten_operator3, "+
                   "(select nama from petugas where petugas.nip=operasi.instrumen) as instrumen,operasi.biayainstrumen, "+
                   "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_anak) as dokter_anak,operasi.biayadokter_anak, "+
                   "(select nama from petugas where petugas.nip=operasi.perawaat_resusitas) as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                   "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_anestesi) as dokter_anestesi,operasi.biayadokter_anestesi, "+
                   "(select nama from petugas where petugas.nip=operasi.asisten_anestesi) as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                   "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                   "(select nama from petugas where petugas.nip=operasi.bidan) as bidan,operasi.biayabidan, "+
                   "(select nama from petugas where petugas.nip=operasi.bidan2) as bidan2,operasi.biayabidan2, "+
                   "(select nama from petugas where petugas.nip=operasi.bidan3) as bidan3,operasi.biayabidan3, "+
                   "(select nama from petugas where petugas.nip=operasi.perawat_luar) as perawat_luar,operasi.biayaperawat_luar, "+
                   "(select nama from petugas where petugas.nip=operasi.omloop) as omloop,operasi.biaya_omloop, "+
                   "(select nama from petugas where petugas.nip=operasi.omloop2) as omloop2,operasi.biaya_omloop2, "+
                   "(select nama from petugas where petugas.nip=operasi.omloop3) as omloop3,operasi.biaya_omloop3, "+
                   "(select nama from petugas where petugas.nip=operasi.omloop4) as omloop4,operasi.biaya_omloop4, "+
                   "(select nama from petugas where petugas.nip=operasi.omloop5) as omloop5,operasi.biaya_omloop5, "+
                   "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_pjanak) as dokter_pjanak,operasi.biaya_dokter_pjanak, "+
                   "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_umum) as dokter_umum,operasi.biaya_dokter_umum, "+
                   "operasi.biayaalat,operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biayasarpras "+
                   "from operasi inner join reg_periksa inner join pasien "+
                   "inner join paket_operasi inner join penjab "+
                   "on operasi.no_rawat=reg_periksa.no_rawat and "+
                   "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                   "operasi.kode_paket=paket_operasi.kode_paket and "+
                   "reg_periksa.kd_pj=penjab.kd_pj "+
                   "where operasi.tgl_operasi between ? and ? and operasi.no_rawat like ? or "+
                   "operasi.tgl_operasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                   "operasi.tgl_operasi between ? and ? and pasien.nm_pasien like ? or "+
                   "operasi.tgl_operasi between ? and ? and operasi.kode_paket like ? or "+
                   "operasi.tgl_operasi between ? and ? and paket_operasi.nm_perawatan like ? or "+
                   "operasi.tgl_operasi between ? and ? and penjab.png_jawab like ? or "+
                   "operasi.tgl_operasi between ? and ? and (select nm_dokter from dokter where dokter.kd_dokter=operasi.operator1) like ? or "+
                   "operasi.tgl_operasi between ? and ? and (select nm_dokter from dokter where dokter.kd_dokter=operasi.operator2) like ? or "+
                   "operasi.tgl_operasi between ? and ? and (select nm_dokter from dokter where dokter.kd_dokter=operasi.operator3) like ? or "+
                   "operasi.tgl_operasi between ? and ? and (select nama from petugas where petugas.nip=operasi.asisten_operator1) like ? or "+
                   "operasi.tgl_operasi between ? and ? and (select nama from petugas where petugas.nip=operasi.asisten_operator2) like ? or "+
                   "operasi.tgl_operasi between ? and ? and (select nama from petugas where petugas.nip=operasi.asisten_operator3) like ? or "+
                   "operasi.tgl_operasi between ? and ? and (select nama from petugas where petugas.nip=operasi.bidan) like ? or "+
                   "operasi.tgl_operasi between ? and ? and (select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_anestesi) like ? "+
                   "order by operasi.no_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(29,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(35,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(39,"%"+TCari.getText().trim()+"%");
                ps.setString(40,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(41,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                ps.setString(42,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                total=0;biayaoperator1=0;biayaoperator2=0; 
                biayaoperator3=0;biayaasisten_operator1=0;biayaasisten_operator2=0;
                biayaasisten_operator3=0;biayainstrumen=0;biayadokter_anak=0;
                biayaperawaat_resusitas=0;biayadokter_anestesi=0;biayaasisten_anestesi=0;
                biayaasisten_anestesi2=0;biayabidan=0;biayabidan2=0;biayabidan3=0;
                biayaperawat_luar=0;biayaalat=0;biayasewaok=0;akomodasi=0;
                bagian_rs=0;biaya_omloop=0;biaya_omloop2=0;biaya_omloop3=0;
                biaya_omloop4=0;biaya_omloop5=0;biayasarpras=0;biaya_dokter_pjanak=0;
                biaya_dokter_umum=0;
                while(rs.next()){  
                    biayaoperator1=biayaoperator1+rs.getDouble("biayaoperator1");
                    biayaoperator2=biayaoperator2+rs.getDouble("biayaoperator2");
                    biayaoperator3=biayaoperator3+rs.getDouble("biayaoperator3");
                    biayaasisten_operator1=biayaasisten_operator1+rs.getDouble("biayaasisten_operator1");
                    biayaasisten_operator2=biayaasisten_operator2+rs.getDouble("biayaasisten_operator2");
                    biayaasisten_operator3=biayaasisten_operator3+rs.getDouble("biayaasisten_operator3");
                    biayainstrumen=biayainstrumen+rs.getDouble("biayainstrumen");
                    biayadokter_anak=biayadokter_anak+rs.getDouble("biayadokter_anak");
                    biayaperawaat_resusitas=biayaperawaat_resusitas+rs.getDouble("biayaperawaat_resusitas");
                    biayadokter_anestesi=biayadokter_anestesi+rs.getDouble("biayadokter_anestesi");
                    biayaasisten_anestesi=biayaasisten_anestesi+rs.getDouble("biayaasisten_anestesi");
                    biayaasisten_anestesi2=biayaasisten_anestesi2+rs.getDouble("biayaasisten_anestesi2");
                    biayabidan=biayabidan+rs.getDouble("biayabidan");
                    biayabidan2=biayabidan2+rs.getDouble("biayabidan2");
                    biayabidan3=biayabidan3+rs.getDouble("biayabidan3");
                    biayaperawat_luar=biayaperawat_luar+rs.getDouble("biayaperawat_luar");
                    biaya_omloop=biaya_omloop+rs.getDouble("biaya_omloop");
                    biaya_omloop2=biaya_omloop2+rs.getDouble("biaya_omloop2");
                    biaya_omloop3=biaya_omloop3+rs.getDouble("biaya_omloop3");
                    biaya_omloop4=biaya_omloop4+rs.getDouble("biaya_omloop4");
                    biaya_omloop5=biaya_omloop5+rs.getDouble("biaya_omloop5");
                    biaya_dokter_pjanak=biaya_dokter_pjanak+rs.getDouble("biaya_dokter_pjanak");
                    biaya_dokter_umum=biaya_dokter_umum+rs.getDouble("biaya_dokter_umum");
                    biayaalat=biayaalat+rs.getDouble("biayaalat");
                    biayasewaok=biayasewaok+rs.getDouble("biayasewaok");
                    akomodasi=akomodasi+rs.getDouble("akomodasi");
                    bagian_rs=bagian_rs+rs.getDouble("bagian_rs");
                    biayasarpras=biayasarpras+rs.getDouble("biayasarpras");
                    total=total+rs.getDouble("biayaoperator1")+rs.getDouble("biayaoperator2")+rs.getDouble("biayaoperator3")+
                          rs.getDouble("biayaasisten_operator1")+rs.getDouble("biayaasisten_operator2")+rs.getDouble("biayaasisten_operator3")+
                          rs.getDouble("biayainstrumen")+rs.getDouble("biayadokter_anak")+rs.getDouble("biayaperawaat_resusitas")+
                          rs.getDouble("biayadokter_anestesi")+rs.getDouble("biayaasisten_anestesi")+rs.getDouble("biayaasisten_anestesi2")+
                          rs.getDouble("biayabidan")+rs.getDouble("biayabidan2")+rs.getDouble("biayabidan3")+
                          rs.getDouble("biayaperawat_luar")+rs.getDouble("biaya_omloop")+rs.getDouble("biaya_omloop2")+
                          rs.getDouble("biaya_omloop3")+rs.getDouble("biaya_omloop4")+rs.getDouble("biaya_omloop5")+
                          rs.getDouble("biaya_dokter_pjanak")+rs.getDouble("biaya_dokter_umum")+rs.getDouble("biayaalat")+
                          rs.getDouble("biayasewaok")+rs.getDouble("akomodasi")+rs.getDouble("bagian_rs")+rs.getDouble("biayasarpras");
                    tabModeOperasi.addRow(new Object[]{
                        i,rs.getString("no_rawat"),rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),rs.getString("kode_paket"),
                        rs.getString("nm_perawatan"),rs.getString("tgl_operasi").substring(0,10),
                        rs.getString("tgl_operasi").substring(11,19),rs.getString("png_jawab"),
                        rs.getString("ruangan"),rs.getString("operator1"),rs.getDouble("biayaoperator1"),
                        rs.getString("operator2"),rs.getDouble("biayaoperator2"),
                        rs.getString("operator3"),rs.getDouble("biayaoperator3"),
                        rs.getString("asisten_operator1"),rs.getDouble("biayaasisten_operator1"),
                        rs.getString("asisten_operator2"),rs.getDouble("biayaasisten_operator2"),
                        rs.getString("asisten_operator3"),rs.getDouble("biayaasisten_operator3"),
                        rs.getString("instrumen"),rs.getDouble("biayainstrumen"),
                        rs.getString("dokter_anak"),rs.getDouble("biayadokter_anak"),
                        rs.getString("perawaat_resusitas"),rs.getDouble("biayaperawaat_resusitas"),
                        rs.getString("dokter_anestesi"),rs.getDouble("biayadokter_anestesi"),
                        rs.getString("asisten_anestesi"),rs.getDouble("biayaasisten_anestesi"),
                        rs.getString("asisten_anestesi2"),rs.getDouble("biayaasisten_anestesi2"),
                        rs.getString("bidan"),rs.getDouble("biayabidan"),
                        rs.getString("bidan2"),rs.getDouble("biayabidan2"),
                        rs.getString("bidan3"),rs.getDouble("biayabidan3"),
                        rs.getString("perawat_luar"),rs.getDouble("biayaperawat_luar"),
                        rs.getString("omloop"),rs.getDouble("biaya_omloop"),
                        rs.getString("omloop2"),rs.getDouble("biaya_omloop2"),
                        rs.getString("omloop3"),rs.getDouble("biaya_omloop3"),
                        rs.getString("omloop4"),rs.getDouble("biaya_omloop4"),
                        rs.getString("omloop5"),rs.getDouble("biaya_omloop5"),
                        rs.getString("dokter_pjanak"),rs.getDouble("biaya_dokter_pjanak"),
                        rs.getString("dokter_umum"),rs.getDouble("biaya_dokter_umum"),
                        rs.getDouble("biayaalat"),rs.getDouble("biayasewaok"),
                        rs.getDouble("akomodasi"),rs.getDouble("bagian_rs"),
                        rs.getDouble("biayasarpras"),(rs.getDouble("biayaoperator1")+rs.getDouble("biayaoperator2")+rs.getDouble("biayaoperator3")+
                        rs.getDouble("biayaasisten_operator1")+rs.getDouble("biayaasisten_operator2")+rs.getDouble("biayaasisten_operator3")+
                        rs.getDouble("biayainstrumen")+rs.getDouble("biayadokter_anak")+rs.getDouble("biayaperawaat_resusitas")+
                        rs.getDouble("biayadokter_anestesi")+rs.getDouble("biayaasisten_anestesi")+rs.getDouble("biayaasisten_anestesi2")+
                        rs.getDouble("biayabidan")+rs.getDouble("biayabidan2")+rs.getDouble("biayabidan3")+
                        rs.getDouble("biayaperawat_luar")+rs.getDouble("biaya_omloop")+rs.getDouble("biaya_omloop2")+
                        rs.getDouble("biaya_omloop3")+rs.getDouble("biaya_omloop4")+rs.getDouble("biaya_omloop5")+
                        rs.getDouble("biaya_dokter_pjanak")+rs.getDouble("biaya_dokter_umum")+rs.getDouble("biayaalat")+
                        rs.getDouble("biayasewaok")+rs.getDouble("akomodasi")+rs.getDouble("bagian_rs")+rs.getDouble("biayasarpras"))
                    });
                    i++;
                }
                if(total>0){
                    tabModeOperasi.addRow(new Object[]{
                        "","","","","","","","","","Jumlah Total :","",biayaoperator1,
                        "",biayaoperator2,"",biayaoperator3,"",biayaasisten_operator1,
                        "",biayaasisten_operator2,"",biayaasisten_operator3,
                        "",biayainstrumen,"",biayadokter_anak,"",biayaperawaat_resusitas,
                        "",biayadokter_anestesi,"",biayaasisten_anestesi,"",
                        biayaasisten_anestesi2,"",biayabidan,"",biayabidan2,
                        "",biayabidan3,"",biayaperawat_luar,"",biaya_omloop,
                        "",biaya_omloop2,"",biaya_omloop3,"",biaya_omloop4,
                        "",biaya_omloop5,"",biaya_dokter_pjanak,"",biaya_dokter_umum,
                        biayaalat,biayasewaok,akomodasi,bagian_rs,biayasarpras,total
                    });
                }
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil5(){     
        Valid.tabelKosong(tabModeRanapDokter);
        try{
            ps=koneksi.prepareStatement(
                   "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                   "pasien.nm_pasien,rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                   "rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.tgl_perawatan,"+
                   "rawat_inap_dr.jam_rawat,penjab.png_jawab,"+
                   "(select bangsal.nm_bangsal from kamar_inap "+
                   "inner join kamar inner join bangsal on "+
                   "kamar_inap.kd_kamar=kamar.kd_kamar "+
                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_dr.no_rawat limit 1 ) , " +
                   "rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.tarif_tindakandr,"+
                   "rawat_inap_dr.kso,rawat_inap_dr.menejemen,rawat_inap_dr.biaya_rawat "+
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "dokter inner join rawat_inap_dr inner join penjab "+
                   "on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                   "where rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.no_rawat like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and pasien.nm_pasien like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and jns_perawatan_inap.nm_perawatan like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.kd_dokter like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and dokter.nm_dokter like ? or "+
                    "rawat_inap_dr.tgl_perawatan between ? and ? and penjab.png_jawab like ? "+
                   " order by rawat_inap_dr.no_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                material=0;bhp=0;jmdokter=0;kso=0;menejemen=0;total=0;
                while(rs.next()){
                    material=material+rs.getDouble("material");
                    bhp=bhp+rs.getDouble("bhp");
                    jmdokter=jmdokter+rs.getDouble("tarif_tindakandr");
                    kso=kso+rs.getDouble("kso");
                    menejemen=menejemen+rs.getDouble("menejemen");
                    total=total+rs.getDouble("biaya_rawat");
                    tabModeRanapDokter.addRow(new Object[]{
                        i,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        rs.getString(9),rs.getString(10),rs.getString(11),rs.getDouble(12),
                        rs.getDouble(13),rs.getDouble(14),rs.getDouble(15),rs.getDouble(16),
                        rs.getDouble(17)
                    });
                    i++;
                }
                if(total>0){
                    tabModeRanapDokter.addRow(new Object[]{
                        "","","","","","","","","","","","Jumlah Total :",material,
                        bhp,jmdokter,kso,menejemen,total
                    });
                }
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil6(){     
        Valid.tabelKosong(tabModeRanapParamedis);
        try{
            ps=koneksi.prepareStatement(
                   "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                   "pasien.nm_pasien,rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                   "rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.tgl_perawatan,"+
                   "rawat_inap_pr.jam_rawat,penjab.png_jawab,"+
                   "(select bangsal.nm_bangsal from kamar_inap "+
                   "inner join kamar inner join bangsal on "+
                   "kamar_inap.kd_kamar=kamar.kd_kamar "+
                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_pr.no_rawat limit 1 ) , " +
                   "rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.tarif_tindakanpr,"+
                   "rawat_inap_pr.kso,rawat_inap_pr.menejemen,rawat_inap_pr.biaya_rawat "+
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "petugas inner join rawat_inap_pr inner join penjab "+
                   "on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_pr.nip=petugas.nip "+
                   "where rawat_inap_pr.tgl_perawatan between ? and ? and rawat_inap_pr.no_rawat like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and pasien.nm_pasien like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and jns_perawatan_inap.nm_perawatan like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and rawat_inap_pr.nip like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and petugas.nama like ? or "+
                    "rawat_inap_pr.tgl_perawatan between ? and ? and penjab.png_jawab like ? "+
                   " order by rawat_inap_pr.no_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                material=0;bhp=0;jmpetugas=0;kso=0;menejemen=0;total=0;
                while(rs.next()){
                    material=material+rs.getDouble("material");
                    bhp=bhp+rs.getDouble("bhp");
                    jmpetugas=jmpetugas+rs.getDouble("tarif_tindakanpr");
                    kso=kso+rs.getDouble("kso");
                    menejemen=menejemen+rs.getDouble("menejemen");
                    total=total+rs.getDouble("biaya_rawat");
                    tabModeRanapParamedis.addRow(new Object[]{
                        i,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        rs.getString(9),rs.getString(10),rs.getString(11),rs.getDouble(12),
                        rs.getDouble(13),rs.getDouble(14),rs.getDouble(15),rs.getDouble(16),
                        rs.getDouble(17)
                    });
                    i++;
                }
                if(total>0){
                    tabModeRanapParamedis.addRow(new Object[]{
                        "","","","","","","","","","","","Jumlah Total :",material,
                        bhp,jmpetugas,kso,menejemen,total
                    });
                }
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil7(){     
        Valid.tabelKosong(tabModeRanapDokterParamedis);
        try{
            ps=koneksi.prepareStatement(
                   "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                   "pasien.nm_pasien,rawat_inap_drpr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                   "rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,"+
                   "rawat_inap_drpr.jam_rawat,penjab.png_jawab,(select bangsal.nm_bangsal from kamar_inap "+
                   "inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_drpr.no_rawat limit 1 ) , " +
                   "rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,"+
                   "rawat_inap_drpr.kso,rawat_inap_drpr.menejemen,rawat_inap_drpr.biaya_rawat "+
                   "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join "+
                   "dokter inner join rawat_inap_drpr inner join poliklinik inner join penjab "+
                   "inner join petugas on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "and rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                   "and rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                   "and rawat_inap_drpr.nip=petugas.nip "+
                   "where rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.no_rawat like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and pasien.nm_pasien like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and jns_perawatan_inap.nm_perawatan like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.kd_dokter like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and dokter.nm_dokter like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.nip like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and petugas.nama like ? or "+
                    "rawat_inap_drpr.tgl_perawatan between ? and ? and penjab.png_jawab like ?  "+
                   " order by rawat_inap_drpr.no_rawat desc");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                material=0;bhp=0;jmdokter=0;jmpetugas=0;kso=0;menejemen=0;total=0;
                while(rs.next()){
                    material=material+rs.getDouble("material");
                    bhp=bhp+rs.getDouble("bhp");
                    jmdokter=jmdokter+rs.getDouble("tarif_tindakandr");
                    jmpetugas=jmpetugas+rs.getDouble("tarif_tindakanpr");
                    kso=kso+rs.getDouble("kso");
                    menejemen=menejemen+rs.getDouble("menejemen");
                    total=total+rs.getDouble("biaya_rawat");
                    tabModeRanapDokterParamedis.addRow(new Object[]{
                        i,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
                        rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getDouble(14),rs.getDouble(15),rs.getDouble(16),
                        rs.getDouble(17),rs.getDouble(18),rs.getDouble(19),rs.getDouble(20)
                    });
                    i++;
                }
                if(total>0){
                    tabModeRanapDokterParamedis.addRow(new Object[]{
                        "","","","","","","","","","","","","","Jumlah Total :",material,
                        bhp,jmdokter,jmpetugas,kso,menejemen,total
                    });
                }
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    
    public void tampil8(){     
        Valid.tabelKosong(tabModeRadiologi);
        try{
            ps=koneksi.prepareStatement(
                   "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                   "periksa_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                   "periksa_radiologi.kd_dokter,dokter.nm_dokter,periksa_radiologi.nip,"+
                   "petugas.nama,periksa_radiologi.dokter_perujuk,"+
                   "(select nm_dokter from dokter where dokter.kd_dokter=periksa_radiologi.dokter_perujuk ) as perujuk,"+
                   "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,penjab.png_jawab,"+
                   "periksa_radiologi.bagian_rs,periksa_radiologi.bhp,periksa_radiologi.tarif_perujuk,"+
                   "periksa_radiologi.tarif_tindakan_dokter,periksa_radiologi.tarif_tindakan_petugas,"+
                   "periksa_radiologi.kso,periksa_radiologi.menejemen,periksa_radiologi.biaya,"+
                   "if(periksa_radiologi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_radiologi.no_rawat limit 1 )) as ruangan "+
                   "from periksa_radiologi inner join reg_periksa "+
                   "inner join pasien inner join dokter inner join petugas inner join penjab "+
                   "inner join jns_perawatan_radiologi on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and periksa_radiologi.kd_dokter=dokter.kd_dokter and periksa_radiologi.nip=petugas.nip "+
                   "and periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                   "where periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.no_rawat like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and pasien.nm_pasien like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.kd_jenis_prw like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and jns_perawatan_radiologi.nm_perawatan like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.kd_dokter like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and dokter.nm_dokter like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.nip like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and petugas.nama like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.dokter_perujuk like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and (select nm_dokter from dokter where dokter.kd_dokter=periksa_radiologi.dokter_perujuk ) like ? or "+
                   "periksa_radiologi.tgl_periksa between ? and ? and penjab.png_jawab like ? "+
                   " order by periksa_radiologi.tgl_periksa");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(35,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                material=0;bhp=0;jmdokter=0;jmpetugas=0;jmperujuk=0;
                kso=0;menejemen=0;total=0;
                while(rs.next()){
                    material=material+rs.getDouble("bagian_rs");
                    bhp=bhp+rs.getDouble("bhp");
                    jmdokter=jmdokter+rs.getDouble("tarif_tindakan_dokter");
                    jmpetugas=jmpetugas+rs.getDouble("tarif_tindakan_petugas");
                    jmperujuk=jmperujuk+rs.getDouble("tarif_perujuk");
                    kso=kso+rs.getDouble("kso");
                    menejemen=menejemen+rs.getDouble("menejemen");
                    total=total+rs.getDouble("biaya");
                    tabModeRadiologi.addRow(new Object[]{
                        i,rs.getString("no_rawat"),rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),rs.getString("kd_jenis_prw"),
                        rs.getString("nm_perawatan"),rs.getString("kd_dokter"),
                        rs.getString("nm_dokter"),rs.getString("nip"),rs.getString("nama"),
                        rs.getString("dokter_perujuk"),rs.getString("perujuk"),
                        rs.getString("tgl_periksa"),rs.getString("jam"),
                        rs.getString("png_jawab"),rs.getString("ruangan"),rs.getDouble("bagian_rs"),
                        rs.getDouble("bhp"),rs.getDouble("tarif_tindakan_dokter"),
                        rs.getDouble("tarif_tindakan_petugas"),rs.getDouble("tarif_perujuk"),
                        rs.getDouble("kso"),rs.getDouble("menejemen"),rs.getDouble("biaya")
                    });
                    i++;
                }
                if(total>0){
                    tabModeRadiologi.addRow(new Object[]{
                        "","","","","","","","","","","","","","","","Jumlah Total :",
                        material,bhp,jmdokter,jmpetugas,jmperujuk,kso,menejemen,total
                    });
                }
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil9(){     
        Valid.tabelKosong(tabModeLaborat);
        try{
            ps=koneksi.prepareStatement(
                   "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                   "periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan, "+
                   "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                   "petugas.nama,periksa_lab.dokter_perujuk,"+
                   "(select nm_dokter from dokter where dokter.kd_dokter=periksa_lab.dokter_perujuk ) as perujuk,"+
                   "periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                   "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                   "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas,"+
                   "periksa_lab.kso,periksa_lab.menejemen,periksa_lab.biaya,"+
                   "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan "+
                   "from periksa_lab inner join reg_periksa "+
                   "inner join pasien inner join dokter inner join petugas inner join penjab "+
                   "inner join jns_perawatan_lab on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and periksa_lab.kd_dokter=dokter.kd_dokter and periksa_lab.nip=petugas.nip "+
                   "and periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                   "where periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and pasien.nm_pasien like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_jenis_prw like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and jns_perawatan_lab.nm_perawatan like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and dokter.nm_dokter like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and periksa_lab.nip like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and petugas.nama like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and (select nm_dokter from dokter where dokter.kd_dokter=periksa_lab.dokter_perujuk ) like ? or "+
                   "periksa_lab.tgl_periksa between ? and ? and penjab.png_jawab like ? "+
                   " order by periksa_lab.tgl_periksa");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(35,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                material=0;bhp=0;jmdokter=0;jmpetugas=0;jmperujuk=0;
                kso=0;menejemen=0;total=0;
                while(rs.next()){
                    material=material+rs.getDouble("bagian_rs");
                    bhp=bhp+rs.getDouble("bhp");
                    jmdokter=jmdokter+rs.getDouble("tarif_tindakan_dokter");
                    jmpetugas=jmpetugas+rs.getDouble("tarif_tindakan_petugas");
                    jmperujuk=jmperujuk+rs.getDouble("tarif_perujuk");
                    kso=kso+rs.getDouble("kso");
                    menejemen=menejemen+rs.getDouble("menejemen");
                    total=total+rs.getDouble("biaya");
                    tabModeLaborat.addRow(new Object[]{
                        i,rs.getString("no_rawat"),rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),rs.getString("kd_jenis_prw"),
                        rs.getString("nm_perawatan"),rs.getString("kd_dokter"),
                        rs.getString("nm_dokter"),rs.getString("nip"),rs.getString("nama"),
                        rs.getString("dokter_perujuk"),rs.getString("perujuk"),
                        rs.getString("tgl_periksa"),rs.getString("jam"),
                        rs.getString("png_jawab"),rs.getString("ruangan"),rs.getDouble("bagian_rs"),
                        rs.getDouble("bhp"),rs.getDouble("tarif_tindakan_dokter"),
                        rs.getDouble("tarif_tindakan_petugas"),rs.getDouble("tarif_perujuk"),
                        rs.getDouble("kso"),rs.getDouble("menejemen"),rs.getDouble("biaya")
                    });
                    i++;
                }
                if(total>0){
                    tabModeLaborat.addRow(new Object[]{
                        "","","","","","","","","","","","","","","","Jumlah Total :",
                        material,bhp,jmdokter,jmpetugas,jmperujuk,kso,menejemen,total
                    });
                }
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil10(){     
        Valid.tabelKosong(tabModeDetailLaborat);
        try {
            ps=koneksi.prepareStatement(
                    "select detail_periksa_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                    "pasien.nm_pasien,detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan,"+
                    "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                    "petugas.nama,periksa_lab.dokter_perujuk,penjab.png_jawab,"+
                    "(select nm_dokter from dokter where dokter.kd_dokter=periksa_lab.dokter_perujuk ) as perujuk,"+
                    "detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,"+
                    "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                    "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                    "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan,"+
                    "detail_periksa_lab.bagian_rs,detail_periksa_lab.bhp,detail_periksa_lab.bagian_perujuk,"+
                    "detail_periksa_lab.bagian_dokter,detail_periksa_lab.bagian_laborat,"+
                    "detail_periksa_lab.kso,detail_periksa_lab.menejemen,"+
                    "detail_periksa_lab.biaya_item from detail_periksa_lab "+
                    "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                    "inner join jns_perawatan_lab inner join periksa_lab "+
                    "inner join dokter inner join petugas inner join penjab "+
                    "on detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                    "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                    "and reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and periksa_lab.nip=petugas.nip and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                    "and periksa_lab.jam=detail_periksa_lab.jam and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                    "and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                    "where detail_periksa_lab.tgl_periksa between ? and ? and detail_periksa_lab.no_rawat like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and pasien.nm_pasien like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and detail_periksa_lab.kd_jenis_prw like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and jns_perawatan_lab.nm_perawatan like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and dokter.nm_dokter like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.nip like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and petugas.nama like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and (select nm_dokter from dokter where dokter.kd_dokter=periksa_lab.dokter_perujuk ) like ? or "+
                    "detail_periksa_lab.tgl_periksa between ? and ? and penjab.png_jawab like ? "+
                    " order by detail_periksa_lab.tgl_periksa");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(35,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                material=0;bhp=0;jmdokter=0;jmpetugas=0;jmperujuk=0;
                kso=0;menejemen=0;total=0;
                while(rs.next()){
                    material=material+rs.getDouble("bagian_rs");
                    bhp=bhp+rs.getDouble("bhp");
                    jmdokter=jmdokter+rs.getDouble("bagian_dokter");
                    jmpetugas=jmpetugas+rs.getDouble("bagian_laborat");
                    jmperujuk=jmperujuk+rs.getDouble("bagian_perujuk");
                    kso=kso+rs.getDouble("kso");
                    menejemen=menejemen+rs.getDouble("menejemen");
                    total=total+rs.getDouble("biaya_item");
                    tabModeDetailLaborat.addRow(new Object[]{
                        i,rs.getString("no_rawat"),rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),rs.getString("id_template"),
                        rs.getString("Pemeriksaan"),rs.getString("kd_dokter"),
                        rs.getString("nm_dokter"),rs.getString("nip"),rs.getString("nama"),
                        rs.getString("dokter_perujuk"),rs.getString("perujuk"),
                        rs.getString("tgl_periksa"),rs.getString("jam"),
                        rs.getString("png_jawab"),rs.getString("ruangan"),rs.getDouble("bagian_rs"),
                        rs.getDouble("bhp"),rs.getDouble("bagian_dokter"),
                        rs.getDouble("bagian_laborat"),rs.getDouble("bagian_perujuk"),
                        rs.getDouble("kso"),rs.getDouble("menejemen"),rs.getDouble("biaya_item")
                    });
                    i++;
                }
                if(total>0){
                    tabModeDetailLaborat.addRow(new Object[]{
                        "","","","","","","","","","","","","","","","Jumlah Total :",
                        material,bhp,jmdokter,jmpetugas,jmperujuk,kso,menejemen,total
                    });
                }
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
        } catch (Exception e) {
            System.out.println("keuangan.DlgDetailTindakan.tampil10() : "+e);
        }            
    }
 
}
