

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
import kepegawaian.DlgCariPetugas;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgPenanggungJawab;

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
    private int i=0;
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    switch (TabRawat.getSelectedIndex()) {
                        case 0:
                            KdDokterRalanDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterRalanDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            BtnDokterRalanDokter.requestFocus();
                            break;
                        case 2:
                            KdDokterRalanDokterParamedis.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterRalanDokterParamedis.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            BtnDokterRalanDokterParamedis.requestFocus();
                            break;
                        case 3:
                            KdOperatorOperasi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmOperatorOperasi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            BtnOperatorOperasi.requestFocus();
                            break;
                        case 4:
                            KdDokterRanapDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterRanapDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            BtnDokterRanapDokter.requestFocus();
                            break;
                        case 6:
                            KdDokterRanapDokterParamedis.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterRanapDokterParamedis.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            BtnDokterRanapDokterParamedis.requestFocus();
                            break;
                        case 7:
                            KdDokterPerujukRad.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterPerujukRad.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            BtnDokterPerujukRad.requestFocus();
                            break;
                        case 8:
                            KdDokterPerujukLab.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterPerujukLab.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            BtnDokterPerujukLab.requestFocus();
                            break;
                        case 9:
                            KdDokterPerujukDetailLab.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            NmDokterPerujukDetailLab.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            BtnDokterPerujukDetailLab.requestFocus();
                            break;
                        default:
                            break;
                    }
                }      
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    switch (TabRawat.getSelectedIndex()) {
                        case 1:
                            KdPetugasRalanParamedis.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPetugasRalanParamedis.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            BtnPetugasRalanParamedis.requestFocus();
                            break;
                        case 2:
                            KdPetugasRalanDokterParamedis.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPetugasRalanDokterParamedis.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            BtnPetugasRalanDokterParamedis.requestFocus();
                            break;
                        case 3:
                            KdAsistenOperasi.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmAsistenOperasi.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            BtnAsistenOperasi.requestFocus();
                            break;
                        case 5:
                            KdPetugasRanapParamedis.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPetugasRanapParamedis.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            BtnPetugasRanapParamedis.requestFocus();
                            break;
                        case 6:
                            KdPetugasRanapDokterParamedis.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPetugasRanapDokterParamedis.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            BtnPetugasRanapDokterParamedis.requestFocus();
                            break;
                        case 7:
                            KdPetugasRad.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPetugasRad.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            BtnPetugasRad.requestFocus();
                            break;
                        case 8:
                            KdPetugasLab.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPetugasLab.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            BtnPetugasLab.requestFocus();
                            break;
                        case 9:
                            KdPetugasDetailLab.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                            NmPetugasDetailLab.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                            BtnPetugasDetailLab.requestFocus();
                            break;
                        default:
                            break;
                    }
                }      
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
        
        petugas.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    petugas.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){
                    switch (TabRawat.getSelectedIndex()) {
                        case 0:
                            KdPoliRalanDokter.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                            NmPoliRalanDokter.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                            BtnPoliRalanDokter.requestFocus();
                            break;
                        case 1:
                            KdPoliRalanParamedis.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                            NmPoliRalanParamedis.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                            BtnPoliRalanParamedis.requestFocus();
                            break;
                        case 2:
                            KdPoliRalanDokterParamedis.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                            NmPoliRalanDokterParamedis.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                            BtnPoliRalanDokterParamedis.requestFocus();
                            break;
                        default:
                            break;
                    }
                }      
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
                    switch (TabRawat.getSelectedIndex()) {
                        case 0:
                            KdCaraBayarRalanDokter.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarRalanDokter.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarRalanDokter.requestFocus();
                            break;
                        case 1:
                            KdCaraBayarRalanParamedis.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarRalanParamedis.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarRalanParamedis.requestFocus();
                            break;
                        case 2:
                            KdCaraBayarRalanDokterParamedis.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarRalanDokterParamedis.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarRalanDokterParamedis.requestFocus();
                            break;
                        case 3:
                            KdCaraBayarOperasi.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarOperasi.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarOperasi.requestFocus();
                            break;
                        case 4:
                            KdCaraBayarRanapDokter.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarRanapDokter.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarRanapDokter.requestFocus();
                            break;
                        case 5:
                            KdCaraBayarRanapParamedis.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarRanapParamedis.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarRanapParamedis.requestFocus();
                            break;
                        case 6:
                            KdCaraBayarRanapDokterParamedis.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarRanapDokterParamedis.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarRanapDokterParamedis.requestFocus();
                            break;
                        case 7:
                            KdCaraBayarRad.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarRad.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarRad.requestFocus();
                            break;
                        case 8:
                            KdCaraBayarLab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarLab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarLab.requestFocus();
                            break;
                        case 9:
                            KdCaraBayarDetailLab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                            NmCaraBayarDetailLab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                            BtnCaraBayarDetailLab.requestFocus();
                            break;
                        default:
                            break;
                    }
                }      
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
        
            
        ChkInput.setSelected(false);
        isForm();
        ChkInput1.setSelected(false);
        isForm2();
        ChkInput2.setSelected(false);
        isForm3();
        ChkInput3.setSelected(false);
        isForm4();
        ChkInput4.setSelected(false);
        isForm5();
        ChkInput5.setSelected(false);
        isForm6();
        ChkInput7.setSelected(false);
        isForm7();
        ChkInput8.setSelected(false);
        isForm8();
        ChkInput9.setSelected(false);
        isForm9();
        ChkInput10.setSelected(false);
        isForm10();
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
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
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
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        KdDokterRalanDokter = new widget.TextBox();
        NmDokterRalanDokter = new widget.TextBox();
        BtnDokterRalanDokter = new widget.Button();
        label19 = new widget.Label();
        KdCaraBayarRalanDokter = new widget.TextBox();
        NmCaraBayarRalanDokter = new widget.TextBox();
        BtnCaraBayarRalanDokter = new widget.Button();
        label20 = new widget.Label();
        KdPoliRalanDokter = new widget.TextBox();
        NmPoliRalanDokter = new widget.TextBox();
        BtnPoliRalanDokter = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbRalanParamedis = new widget.Table();
        PanelInput1 = new javax.swing.JPanel();
        ChkInput1 = new widget.CekBox();
        FormInput1 = new widget.panelisi();
        label21 = new widget.Label();
        KdPetugasRalanParamedis = new widget.TextBox();
        NmPetugasRalanParamedis = new widget.TextBox();
        BtnPetugasRalanParamedis = new widget.Button();
        label22 = new widget.Label();
        KdCaraBayarRalanParamedis = new widget.TextBox();
        NmCaraBayarRalanParamedis = new widget.TextBox();
        BtnCaraBayarRalanParamedis = new widget.Button();
        label23 = new widget.Label();
        KdPoliRalanParamedis = new widget.TextBox();
        NmPoliRalanParamedis = new widget.TextBox();
        BtnPoliRalanParamedis = new widget.Button();
        internalFrame4 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbRalanDokterParamedis = new widget.Table();
        PanelInput2 = new javax.swing.JPanel();
        ChkInput2 = new widget.CekBox();
        FormInput2 = new widget.panelisi();
        label24 = new widget.Label();
        KdPetugasRalanDokterParamedis = new widget.TextBox();
        NmPetugasRalanDokterParamedis = new widget.TextBox();
        BtnPetugasRalanDokterParamedis = new widget.Button();
        label25 = new widget.Label();
        KdCaraBayarRalanDokterParamedis = new widget.TextBox();
        NmCaraBayarRalanDokterParamedis = new widget.TextBox();
        BtnCaraBayarRalanDokterParamedis = new widget.Button();
        label26 = new widget.Label();
        KdPoliRalanDokterParamedis = new widget.TextBox();
        NmPoliRalanDokterParamedis = new widget.TextBox();
        BtnPoliRalanDokterParamedis = new widget.Button();
        label27 = new widget.Label();
        KdDokterRalanDokterParamedis = new widget.TextBox();
        NmDokterRalanDokterParamedis = new widget.TextBox();
        BtnDokterRalanDokterParamedis = new widget.Button();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbOperasi = new widget.Table();
        PanelInput10 = new javax.swing.JPanel();
        ChkInput10 = new widget.CekBox();
        FormInput10 = new widget.panelisi();
        label48 = new widget.Label();
        KdAsistenOperasi = new widget.TextBox();
        NmAsistenOperasi = new widget.TextBox();
        BtnAsistenOperasi = new widget.Button();
        label49 = new widget.Label();
        KdCaraBayarOperasi = new widget.TextBox();
        NmCaraBayarOperasi = new widget.TextBox();
        BtnCaraBayarOperasi = new widget.Button();
        label50 = new widget.Label();
        KdOperatorOperasi = new widget.TextBox();
        NmOperatorOperasi = new widget.TextBox();
        BtnOperatorOperasi = new widget.Button();
        internalFrame6 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbRanapDokter = new widget.Table();
        PanelInput3 = new javax.swing.JPanel();
        ChkInput3 = new widget.CekBox();
        FormInput3 = new widget.panelisi();
        label28 = new widget.Label();
        KdDokterRanapDokter = new widget.TextBox();
        NmDokterRanapDokter = new widget.TextBox();
        BtnDokterRanapDokter = new widget.Button();
        label29 = new widget.Label();
        KdCaraBayarRanapDokter = new widget.TextBox();
        NmCaraBayarRanapDokter = new widget.TextBox();
        BtnCaraBayarRanapDokter = new widget.Button();
        internalFrame7 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbRanapParamedis = new widget.Table();
        PanelInput4 = new javax.swing.JPanel();
        ChkInput4 = new widget.CekBox();
        FormInput4 = new widget.panelisi();
        label30 = new widget.Label();
        KdPetugasRanapParamedis = new widget.TextBox();
        NmPetugasRanapParamedis = new widget.TextBox();
        BtnPetugasRanapParamedis = new widget.Button();
        label31 = new widget.Label();
        KdCaraBayarRanapParamedis = new widget.TextBox();
        NmCaraBayarRanapParamedis = new widget.TextBox();
        BtnCaraBayarRanapParamedis = new widget.Button();
        internalFrame8 = new widget.InternalFrame();
        Scroll6 = new widget.ScrollPane();
        tbRanapDokterParamedis = new widget.Table();
        PanelInput5 = new javax.swing.JPanel();
        ChkInput5 = new widget.CekBox();
        FormInput5 = new widget.panelisi();
        label32 = new widget.Label();
        KdPetugasRanapDokterParamedis = new widget.TextBox();
        NmPetugasRanapDokterParamedis = new widget.TextBox();
        BtnPetugasRanapDokterParamedis = new widget.Button();
        label33 = new widget.Label();
        KdCaraBayarRanapDokterParamedis = new widget.TextBox();
        NmCaraBayarRanapDokterParamedis = new widget.TextBox();
        BtnCaraBayarRanapDokterParamedis = new widget.Button();
        label35 = new widget.Label();
        KdDokterRanapDokterParamedis = new widget.TextBox();
        NmDokterRanapDokterParamedis = new widget.TextBox();
        BtnDokterRanapDokterParamedis = new widget.Button();
        internalFrame9 = new widget.InternalFrame();
        Scroll7 = new widget.ScrollPane();
        tbRadiologi = new widget.Table();
        PanelInput7 = new javax.swing.JPanel();
        ChkInput7 = new widget.CekBox();
        FormInput7 = new widget.panelisi();
        label39 = new widget.Label();
        KdPetugasRad = new widget.TextBox();
        NmPetugasRad = new widget.TextBox();
        BtnPetugasRad = new widget.Button();
        label40 = new widget.Label();
        KdCaraBayarRad = new widget.TextBox();
        NmCaraBayarRad = new widget.TextBox();
        BtnCaraBayarRad = new widget.Button();
        label41 = new widget.Label();
        KdDokterPerujukRad = new widget.TextBox();
        NmDokterPerujukRad = new widget.TextBox();
        BtnDokterPerujukRad = new widget.Button();
        internalFrame10 = new widget.InternalFrame();
        Scroll8 = new widget.ScrollPane();
        tbLaborat = new widget.Table();
        PanelInput8 = new javax.swing.JPanel();
        ChkInput8 = new widget.CekBox();
        FormInput8 = new widget.panelisi();
        label42 = new widget.Label();
        KdPetugasLab = new widget.TextBox();
        NmPetugasLab = new widget.TextBox();
        BtnPetugasLab = new widget.Button();
        label43 = new widget.Label();
        KdCaraBayarLab = new widget.TextBox();
        NmCaraBayarLab = new widget.TextBox();
        BtnCaraBayarLab = new widget.Button();
        label44 = new widget.Label();
        KdDokterPerujukLab = new widget.TextBox();
        NmDokterPerujukLab = new widget.TextBox();
        BtnDokterPerujukLab = new widget.Button();
        internalFrame11 = new widget.InternalFrame();
        Scroll9 = new widget.ScrollPane();
        tbDetailLaborat = new widget.Table();
        PanelInput9 = new javax.swing.JPanel();
        ChkInput9 = new widget.CekBox();
        FormInput9 = new widget.panelisi();
        label45 = new widget.Label();
        KdPetugasDetailLab = new widget.TextBox();
        NmPetugasDetailLab = new widget.TextBox();
        BtnPetugasDetailLab = new widget.Button();
        label46 = new widget.Label();
        KdCaraBayarDetailLab = new widget.TextBox();
        NmCaraBayarDetailLab = new widget.TextBox();
        BtnCaraBayarDetailLab = new widget.Button();
        label47 = new widget.Label();
        KdDokterPerujukDetailLab = new widget.TextBox();
        NmDokterPerujukDetailLab = new widget.TextBox();
        BtnDokterPerujukDetailLab = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Detail Tindakan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jLabel12.setText("Status :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Belum Lunas", "Sudah Lunas" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(115, 23));
        panelGlass5.add(cmbStatus);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass5.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(160, 23));
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
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
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

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 66));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput.setLayout(null);

        label17.setText("Dokter :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(label17);
        label17.setBounds(0, 10, 55, 23);

        KdDokterRalanDokter.setEditable(false);
        KdDokterRalanDokter.setName("KdDokterRalanDokter"); // NOI18N
        KdDokterRalanDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokterRalanDokter);
        KdDokterRalanDokter.setBounds(58, 10, 65, 23);

        NmDokterRalanDokter.setEditable(false);
        NmDokterRalanDokter.setName("NmDokterRalanDokter"); // NOI18N
        NmDokterRalanDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmDokterRalanDokter);
        NmDokterRalanDokter.setBounds(125, 10, 150, 23);

        BtnDokterRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterRalanDokter.setMnemonic('3');
        BtnDokterRalanDokter.setToolTipText("Alt+3");
        BtnDokterRalanDokter.setName("BtnDokterRalanDokter"); // NOI18N
        BtnDokterRalanDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterRalanDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokterRalanDokter);
        BtnDokterRalanDokter.setBounds(278, 10, 28, 23);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label19);
        label19.setBounds(640, 10, 70, 23);

        KdCaraBayarRalanDokter.setEditable(false);
        KdCaraBayarRalanDokter.setName("KdCaraBayarRalanDokter"); // NOI18N
        KdCaraBayarRalanDokter.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(KdCaraBayarRalanDokter);
        KdCaraBayarRalanDokter.setBounds(713, 10, 65, 23);

        NmCaraBayarRalanDokter.setEditable(false);
        NmCaraBayarRalanDokter.setName("NmCaraBayarRalanDokter"); // NOI18N
        NmCaraBayarRalanDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmCaraBayarRalanDokter);
        NmCaraBayarRalanDokter.setBounds(780, 10, 150, 23);

        BtnCaraBayarRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRalanDokter.setMnemonic('3');
        BtnCaraBayarRalanDokter.setToolTipText("Alt+3");
        BtnCaraBayarRalanDokter.setName("BtnCaraBayarRalanDokter"); // NOI18N
        BtnCaraBayarRalanDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRalanDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnCaraBayarRalanDokter);
        BtnCaraBayarRalanDokter.setBounds(933, 10, 28, 23);

        label20.setText("Unit/Poli :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(45, 23));
        FormInput.add(label20);
        label20.setBounds(310, 10, 62, 23);

        KdPoliRalanDokter.setEditable(false);
        KdPoliRalanDokter.setName("KdPoliRalanDokter"); // NOI18N
        KdPoliRalanDokter.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(KdPoliRalanDokter);
        KdPoliRalanDokter.setBounds(375, 10, 65, 23);

        NmPoliRalanDokter.setEditable(false);
        NmPoliRalanDokter.setName("NmPoliRalanDokter"); // NOI18N
        NmPoliRalanDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput.add(NmPoliRalanDokter);
        NmPoliRalanDokter.setBounds(442, 10, 150, 23);

        BtnPoliRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoliRalanDokter.setMnemonic('3');
        BtnPoliRalanDokter.setToolTipText("Alt+3");
        BtnPoliRalanDokter.setName("BtnPoliRalanDokter"); // NOI18N
        BtnPoliRalanDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPoliRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliRalanDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnPoliRalanDokter);
        BtnPoliRalanDokter.setBounds(595, 10, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame2.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Ralan Dokter", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

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

        PanelInput1.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput1.setName("PanelInput1"); // NOI18N
        PanelInput1.setOpaque(false);
        PanelInput1.setPreferredSize(new java.awt.Dimension(192, 65));
        PanelInput1.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setMnemonic('M');
        ChkInput1.setText(".: Filter Data");
        ChkInput1.setBorderPainted(true);
        ChkInput1.setBorderPaintedFlat(true);
        ChkInput1.setFocusable(false);
        ChkInput1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput1.setName("ChkInput1"); // NOI18N
        ChkInput1.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput1ActionPerformed(evt);
            }
        });
        PanelInput1.add(ChkInput1, java.awt.BorderLayout.PAGE_END);

        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput1.setLayout(null);

        label21.setText("Petugas :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput1.add(label21);
        label21.setBounds(0, 10, 60, 23);

        KdPetugasRalanParamedis.setEditable(false);
        KdPetugasRalanParamedis.setName("KdPetugasRalanParamedis"); // NOI18N
        KdPetugasRalanParamedis.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput1.add(KdPetugasRalanParamedis);
        KdPetugasRalanParamedis.setBounds(63, 10, 65, 23);

        NmPetugasRalanParamedis.setEditable(false);
        NmPetugasRalanParamedis.setName("NmPetugasRalanParamedis"); // NOI18N
        NmPetugasRalanParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput1.add(NmPetugasRalanParamedis);
        NmPetugasRalanParamedis.setBounds(130, 10, 150, 23);

        BtnPetugasRalanParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasRalanParamedis.setMnemonic('3');
        BtnPetugasRalanParamedis.setToolTipText("Alt+3");
        BtnPetugasRalanParamedis.setName("BtnPetugasRalanParamedis"); // NOI18N
        BtnPetugasRalanParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasRalanParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasRalanParamedisActionPerformed(evt);
            }
        });
        FormInput1.add(BtnPetugasRalanParamedis);
        BtnPetugasRalanParamedis.setBounds(283, 10, 28, 23);

        label22.setText("Cara Bayar :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput1.add(label22);
        label22.setBounds(640, 10, 70, 23);

        KdCaraBayarRalanParamedis.setEditable(false);
        KdCaraBayarRalanParamedis.setName("KdCaraBayarRalanParamedis"); // NOI18N
        KdCaraBayarRalanParamedis.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput1.add(KdCaraBayarRalanParamedis);
        KdCaraBayarRalanParamedis.setBounds(713, 10, 65, 23);

        NmCaraBayarRalanParamedis.setEditable(false);
        NmCaraBayarRalanParamedis.setName("NmCaraBayarRalanParamedis"); // NOI18N
        NmCaraBayarRalanParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput1.add(NmCaraBayarRalanParamedis);
        NmCaraBayarRalanParamedis.setBounds(780, 10, 150, 23);

        BtnCaraBayarRalanParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRalanParamedis.setMnemonic('3');
        BtnCaraBayarRalanParamedis.setToolTipText("Alt+3");
        BtnCaraBayarRalanParamedis.setName("BtnCaraBayarRalanParamedis"); // NOI18N
        BtnCaraBayarRalanParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRalanParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRalanParamedisActionPerformed(evt);
            }
        });
        FormInput1.add(BtnCaraBayarRalanParamedis);
        BtnCaraBayarRalanParamedis.setBounds(933, 10, 28, 23);

        label23.setText("Unit/Poli :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(45, 23));
        FormInput1.add(label23);
        label23.setBounds(315, 10, 62, 23);

        KdPoliRalanParamedis.setEditable(false);
        KdPoliRalanParamedis.setName("KdPoliRalanParamedis"); // NOI18N
        KdPoliRalanParamedis.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput1.add(KdPoliRalanParamedis);
        KdPoliRalanParamedis.setBounds(380, 10, 65, 23);

        NmPoliRalanParamedis.setEditable(false);
        NmPoliRalanParamedis.setName("NmPoliRalanParamedis"); // NOI18N
        NmPoliRalanParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput1.add(NmPoliRalanParamedis);
        NmPoliRalanParamedis.setBounds(447, 10, 150, 23);

        BtnPoliRalanParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoliRalanParamedis.setMnemonic('3');
        BtnPoliRalanParamedis.setToolTipText("Alt+3");
        BtnPoliRalanParamedis.setName("BtnPoliRalanParamedis"); // NOI18N
        BtnPoliRalanParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPoliRalanParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliRalanParamedisActionPerformed(evt);
            }
        });
        FormInput1.add(BtnPoliRalanParamedis);
        BtnPoliRalanParamedis.setBounds(600, 10, 28, 23);

        PanelInput1.add(FormInput1, java.awt.BorderLayout.CENTER);

        internalFrame3.add(PanelInput1, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Ralan Paramedis", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

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

        PanelInput2.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput2.setName("PanelInput2"); // NOI18N
        PanelInput2.setOpaque(false);
        PanelInput2.setPreferredSize(new java.awt.Dimension(192, 95));
        PanelInput2.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput2.setMnemonic('M');
        ChkInput2.setText(".: Filter Data");
        ChkInput2.setBorderPainted(true);
        ChkInput2.setBorderPaintedFlat(true);
        ChkInput2.setFocusable(false);
        ChkInput2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput2.setName("ChkInput2"); // NOI18N
        ChkInput2.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput2ActionPerformed(evt);
            }
        });
        PanelInput2.add(ChkInput2, java.awt.BorderLayout.PAGE_END);

        FormInput2.setName("FormInput2"); // NOI18N
        FormInput2.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput2.setLayout(null);

        label24.setText("Petugas :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput2.add(label24);
        label24.setBounds(0, 40, 60, 23);

        KdPetugasRalanDokterParamedis.setEditable(false);
        KdPetugasRalanDokterParamedis.setName("KdPetugasRalanDokterParamedis"); // NOI18N
        KdPetugasRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput2.add(KdPetugasRalanDokterParamedis);
        KdPetugasRalanDokterParamedis.setBounds(63, 40, 120, 23);

        NmPetugasRalanDokterParamedis.setEditable(false);
        NmPetugasRalanDokterParamedis.setName("NmPetugasRalanDokterParamedis"); // NOI18N
        NmPetugasRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput2.add(NmPetugasRalanDokterParamedis);
        NmPetugasRalanDokterParamedis.setBounds(185, 40, 290, 23);

        BtnPetugasRalanDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasRalanDokterParamedis.setMnemonic('3');
        BtnPetugasRalanDokterParamedis.setToolTipText("Alt+3");
        BtnPetugasRalanDokterParamedis.setName("BtnPetugasRalanDokterParamedis"); // NOI18N
        BtnPetugasRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasRalanDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasRalanDokterParamedisActionPerformed(evt);
            }
        });
        FormInput2.add(BtnPetugasRalanDokterParamedis);
        BtnPetugasRalanDokterParamedis.setBounds(478, 40, 28, 23);

        label25.setText("Cara Bayar :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput2.add(label25);
        label25.setBounds(560, 40, 70, 23);

        KdCaraBayarRalanDokterParamedis.setEditable(false);
        KdCaraBayarRalanDokterParamedis.setName("KdCaraBayarRalanDokterParamedis"); // NOI18N
        KdCaraBayarRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput2.add(KdCaraBayarRalanDokterParamedis);
        KdCaraBayarRalanDokterParamedis.setBounds(633, 40, 65, 23);

        NmCaraBayarRalanDokterParamedis.setEditable(false);
        NmCaraBayarRalanDokterParamedis.setName("NmCaraBayarRalanDokterParamedis"); // NOI18N
        NmCaraBayarRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput2.add(NmCaraBayarRalanDokterParamedis);
        NmCaraBayarRalanDokterParamedis.setBounds(700, 40, 230, 23);

        BtnCaraBayarRalanDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRalanDokterParamedis.setMnemonic('3');
        BtnCaraBayarRalanDokterParamedis.setToolTipText("Alt+3");
        BtnCaraBayarRalanDokterParamedis.setName("BtnCaraBayarRalanDokterParamedis"); // NOI18N
        BtnCaraBayarRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRalanDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRalanDokterParamedisActionPerformed(evt);
            }
        });
        FormInput2.add(BtnCaraBayarRalanDokterParamedis);
        BtnCaraBayarRalanDokterParamedis.setBounds(933, 40, 28, 23);

        label26.setText("Unit/Poli :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(45, 23));
        FormInput2.add(label26);
        label26.setBounds(560, 10, 70, 23);

        KdPoliRalanDokterParamedis.setEditable(false);
        KdPoliRalanDokterParamedis.setName("KdPoliRalanDokterParamedis"); // NOI18N
        KdPoliRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput2.add(KdPoliRalanDokterParamedis);
        KdPoliRalanDokterParamedis.setBounds(633, 10, 65, 23);

        NmPoliRalanDokterParamedis.setEditable(false);
        NmPoliRalanDokterParamedis.setName("NmPoliRalanDokterParamedis"); // NOI18N
        NmPoliRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput2.add(NmPoliRalanDokterParamedis);
        NmPoliRalanDokterParamedis.setBounds(700, 10, 230, 23);

        BtnPoliRalanDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoliRalanDokterParamedis.setMnemonic('3');
        BtnPoliRalanDokterParamedis.setToolTipText("Alt+3");
        BtnPoliRalanDokterParamedis.setName("BtnPoliRalanDokterParamedis"); // NOI18N
        BtnPoliRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPoliRalanDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliRalanDokterParamedisActionPerformed(evt);
            }
        });
        FormInput2.add(BtnPoliRalanDokterParamedis);
        BtnPoliRalanDokterParamedis.setBounds(933, 10, 28, 23);

        label27.setText("Dokter :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput2.add(label27);
        label27.setBounds(0, 10, 60, 23);

        KdDokterRalanDokterParamedis.setEditable(false);
        KdDokterRalanDokterParamedis.setName("KdDokterRalanDokterParamedis"); // NOI18N
        KdDokterRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput2.add(KdDokterRalanDokterParamedis);
        KdDokterRalanDokterParamedis.setBounds(63, 10, 120, 23);

        NmDokterRalanDokterParamedis.setEditable(false);
        NmDokterRalanDokterParamedis.setName("NmDokterRalanDokterParamedis"); // NOI18N
        NmDokterRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput2.add(NmDokterRalanDokterParamedis);
        NmDokterRalanDokterParamedis.setBounds(185, 10, 290, 23);

        BtnDokterRalanDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterRalanDokterParamedis.setMnemonic('3');
        BtnDokterRalanDokterParamedis.setToolTipText("Alt+3");
        BtnDokterRalanDokterParamedis.setName("BtnDokterRalanDokterParamedis"); // NOI18N
        BtnDokterRalanDokterParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterRalanDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterRalanDokterParamedisActionPerformed(evt);
            }
        });
        FormInput2.add(BtnDokterRalanDokterParamedis);
        BtnDokterRalanDokterParamedis.setBounds(478, 10, 28, 23);

        PanelInput2.add(FormInput2, java.awt.BorderLayout.CENTER);

        internalFrame4.add(PanelInput2, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Ralan Dokter & Paramedis", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

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

        PanelInput10.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput10.setName("PanelInput10"); // NOI18N
        PanelInput10.setOpaque(false);
        PanelInput10.setPreferredSize(new java.awt.Dimension(192, 65));
        PanelInput10.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput10.setMnemonic('M');
        ChkInput10.setText(".: Filter Data");
        ChkInput10.setBorderPainted(true);
        ChkInput10.setBorderPaintedFlat(true);
        ChkInput10.setFocusable(false);
        ChkInput10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput10.setName("ChkInput10"); // NOI18N
        ChkInput10.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput10.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput10.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput10.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput10ActionPerformed(evt);
            }
        });
        PanelInput10.add(ChkInput10, java.awt.BorderLayout.PAGE_END);

        FormInput10.setName("FormInput10"); // NOI18N
        FormInput10.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput10.setLayout(null);

        label48.setText("Asisten 1 :");
        label48.setName("label48"); // NOI18N
        label48.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput10.add(label48);
        label48.setBounds(320, 10, 60, 23);

        KdAsistenOperasi.setEditable(false);
        KdAsistenOperasi.setName("KdAsistenOperasi"); // NOI18N
        KdAsistenOperasi.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput10.add(KdAsistenOperasi);
        KdAsistenOperasi.setBounds(385, 10, 65, 23);

        NmAsistenOperasi.setEditable(false);
        NmAsistenOperasi.setName("NmAsistenOperasi"); // NOI18N
        NmAsistenOperasi.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput10.add(NmAsistenOperasi);
        NmAsistenOperasi.setBounds(452, 10, 150, 23);

        BtnAsistenOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAsistenOperasi.setMnemonic('3');
        BtnAsistenOperasi.setToolTipText("Alt+3");
        BtnAsistenOperasi.setName("BtnAsistenOperasi"); // NOI18N
        BtnAsistenOperasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAsistenOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsistenOperasiActionPerformed(evt);
            }
        });
        FormInput10.add(BtnAsistenOperasi);
        BtnAsistenOperasi.setBounds(605, 10, 28, 23);

        label49.setText("Cara Bayar :");
        label49.setName("label49"); // NOI18N
        label49.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput10.add(label49);
        label49.setBounds(640, 10, 70, 23);

        KdCaraBayarOperasi.setEditable(false);
        KdCaraBayarOperasi.setName("KdCaraBayarOperasi"); // NOI18N
        KdCaraBayarOperasi.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput10.add(KdCaraBayarOperasi);
        KdCaraBayarOperasi.setBounds(713, 10, 65, 23);

        NmCaraBayarOperasi.setEditable(false);
        NmCaraBayarOperasi.setName("NmCaraBayarOperasi"); // NOI18N
        NmCaraBayarOperasi.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput10.add(NmCaraBayarOperasi);
        NmCaraBayarOperasi.setBounds(780, 10, 150, 23);

        BtnCaraBayarOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarOperasi.setMnemonic('3');
        BtnCaraBayarOperasi.setToolTipText("Alt+3");
        BtnCaraBayarOperasi.setName("BtnCaraBayarOperasi"); // NOI18N
        BtnCaraBayarOperasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarOperasiActionPerformed(evt);
            }
        });
        FormInput10.add(BtnCaraBayarOperasi);
        BtnCaraBayarOperasi.setBounds(933, 10, 28, 23);

        label50.setText("Operator :");
        label50.setName("label50"); // NOI18N
        label50.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput10.add(label50);
        label50.setBounds(0, 10, 62, 23);

        KdOperatorOperasi.setEditable(false);
        KdOperatorOperasi.setName("KdOperatorOperasi"); // NOI18N
        KdOperatorOperasi.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput10.add(KdOperatorOperasi);
        KdOperatorOperasi.setBounds(65, 10, 65, 23);

        NmOperatorOperasi.setEditable(false);
        NmOperatorOperasi.setName("NmOperatorOperasi"); // NOI18N
        NmOperatorOperasi.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput10.add(NmOperatorOperasi);
        NmOperatorOperasi.setBounds(132, 10, 150, 23);

        BtnOperatorOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperatorOperasi.setMnemonic('3');
        BtnOperatorOperasi.setToolTipText("Alt+3");
        BtnOperatorOperasi.setName("BtnOperatorOperasi"); // NOI18N
        BtnOperatorOperasi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperatorOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperatorOperasiActionPerformed(evt);
            }
        });
        FormInput10.add(BtnOperatorOperasi);
        BtnOperatorOperasi.setBounds(285, 10, 28, 23);

        PanelInput10.add(FormInput10, java.awt.BorderLayout.CENTER);

        internalFrame5.add(PanelInput10, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Operasi & VK", internalFrame5);

        internalFrame6.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

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

        PanelInput3.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput3.setName("PanelInput3"); // NOI18N
        PanelInput3.setOpaque(false);
        PanelInput3.setPreferredSize(new java.awt.Dimension(192, 65));
        PanelInput3.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput3.setMnemonic('M');
        ChkInput3.setText(".: Filter Data");
        ChkInput3.setBorderPainted(true);
        ChkInput3.setBorderPaintedFlat(true);
        ChkInput3.setFocusable(false);
        ChkInput3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput3.setName("ChkInput3"); // NOI18N
        ChkInput3.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput3.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput3ActionPerformed(evt);
            }
        });
        PanelInput3.add(ChkInput3, java.awt.BorderLayout.PAGE_END);

        FormInput3.setName("FormInput3"); // NOI18N
        FormInput3.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput3.setLayout(null);

        label28.setText("Dokter :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput3.add(label28);
        label28.setBounds(0, 10, 55, 23);

        KdDokterRanapDokter.setEditable(false);
        KdDokterRanapDokter.setName("KdDokterRanapDokter"); // NOI18N
        KdDokterRanapDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput3.add(KdDokterRanapDokter);
        KdDokterRanapDokter.setBounds(58, 10, 115, 23);

        NmDokterRanapDokter.setEditable(false);
        NmDokterRanapDokter.setName("NmDokterRanapDokter"); // NOI18N
        NmDokterRanapDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput3.add(NmDokterRanapDokter);
        NmDokterRanapDokter.setBounds(175, 10, 300, 23);

        BtnDokterRanapDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterRanapDokter.setMnemonic('3');
        BtnDokterRanapDokter.setToolTipText("Alt+3");
        BtnDokterRanapDokter.setName("BtnDokterRanapDokter"); // NOI18N
        BtnDokterRanapDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterRanapDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterRanapDokterActionPerformed(evt);
            }
        });
        FormInput3.add(BtnDokterRanapDokter);
        BtnDokterRanapDokter.setBounds(478, 10, 28, 23);

        label29.setText("Cara Bayar :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput3.add(label29);
        label29.setBounds(560, 10, 70, 23);

        KdCaraBayarRanapDokter.setEditable(false);
        KdCaraBayarRanapDokter.setName("KdCaraBayarRanapDokter"); // NOI18N
        KdCaraBayarRanapDokter.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput3.add(KdCaraBayarRanapDokter);
        KdCaraBayarRanapDokter.setBounds(633, 10, 65, 23);

        NmCaraBayarRanapDokter.setEditable(false);
        NmCaraBayarRanapDokter.setName("NmCaraBayarRanapDokter"); // NOI18N
        NmCaraBayarRanapDokter.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput3.add(NmCaraBayarRanapDokter);
        NmCaraBayarRanapDokter.setBounds(700, 10, 230, 23);

        BtnCaraBayarRanapDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRanapDokter.setMnemonic('3');
        BtnCaraBayarRanapDokter.setToolTipText("Alt+3");
        BtnCaraBayarRanapDokter.setName("BtnCaraBayarRanapDokter"); // NOI18N
        BtnCaraBayarRanapDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRanapDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRanapDokterActionPerformed(evt);
            }
        });
        FormInput3.add(BtnCaraBayarRanapDokter);
        BtnCaraBayarRanapDokter.setBounds(933, 10, 28, 23);

        PanelInput3.add(FormInput3, java.awt.BorderLayout.CENTER);

        internalFrame6.add(PanelInput3, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Ranap Dokter", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

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

        PanelInput4.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput4.setName("PanelInput4"); // NOI18N
        PanelInput4.setOpaque(false);
        PanelInput4.setPreferredSize(new java.awt.Dimension(192, 65));
        PanelInput4.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput4.setMnemonic('M');
        ChkInput4.setText(".: Filter Data");
        ChkInput4.setBorderPainted(true);
        ChkInput4.setBorderPaintedFlat(true);
        ChkInput4.setFocusable(false);
        ChkInput4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput4.setName("ChkInput4"); // NOI18N
        ChkInput4.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput4.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput4ActionPerformed(evt);
            }
        });
        PanelInput4.add(ChkInput4, java.awt.BorderLayout.PAGE_END);

        FormInput4.setName("FormInput4"); // NOI18N
        FormInput4.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput4.setLayout(null);

        label30.setText("Petugas :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput4.add(label30);
        label30.setBounds(0, 10, 60, 23);

        KdPetugasRanapParamedis.setEditable(false);
        KdPetugasRanapParamedis.setName("KdPetugasRanapParamedis"); // NOI18N
        KdPetugasRanapParamedis.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput4.add(KdPetugasRanapParamedis);
        KdPetugasRanapParamedis.setBounds(63, 10, 115, 23);

        NmPetugasRanapParamedis.setEditable(false);
        NmPetugasRanapParamedis.setName("NmPetugasRanapParamedis"); // NOI18N
        NmPetugasRanapParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput4.add(NmPetugasRanapParamedis);
        NmPetugasRanapParamedis.setBounds(180, 10, 295, 23);

        BtnPetugasRanapParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasRanapParamedis.setMnemonic('3');
        BtnPetugasRanapParamedis.setToolTipText("Alt+3");
        BtnPetugasRanapParamedis.setName("BtnPetugasRanapParamedis"); // NOI18N
        BtnPetugasRanapParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasRanapParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasRanapParamedisActionPerformed(evt);
            }
        });
        FormInput4.add(BtnPetugasRanapParamedis);
        BtnPetugasRanapParamedis.setBounds(478, 10, 28, 23);

        label31.setText("Cara Bayar :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput4.add(label31);
        label31.setBounds(560, 10, 70, 23);

        KdCaraBayarRanapParamedis.setEditable(false);
        KdCaraBayarRanapParamedis.setName("KdCaraBayarRanapParamedis"); // NOI18N
        KdCaraBayarRanapParamedis.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput4.add(KdCaraBayarRanapParamedis);
        KdCaraBayarRanapParamedis.setBounds(633, 10, 65, 23);

        NmCaraBayarRanapParamedis.setEditable(false);
        NmCaraBayarRanapParamedis.setName("NmCaraBayarRanapParamedis"); // NOI18N
        NmCaraBayarRanapParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput4.add(NmCaraBayarRanapParamedis);
        NmCaraBayarRanapParamedis.setBounds(700, 10, 230, 23);

        BtnCaraBayarRanapParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRanapParamedis.setMnemonic('3');
        BtnCaraBayarRanapParamedis.setToolTipText("Alt+3");
        BtnCaraBayarRanapParamedis.setName("BtnCaraBayarRanapParamedis"); // NOI18N
        BtnCaraBayarRanapParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRanapParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRanapParamedisActionPerformed(evt);
            }
        });
        FormInput4.add(BtnCaraBayarRanapParamedis);
        BtnCaraBayarRanapParamedis.setBounds(933, 10, 28, 23);

        PanelInput4.add(FormInput4, java.awt.BorderLayout.CENTER);

        internalFrame7.add(PanelInput4, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Ranap Paramedis", internalFrame7);

        internalFrame8.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

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

        PanelInput5.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput5.setName("PanelInput5"); // NOI18N
        PanelInput5.setOpaque(false);
        PanelInput5.setPreferredSize(new java.awt.Dimension(192, 65));
        PanelInput5.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput5.setMnemonic('M');
        ChkInput5.setText(".: Filter Data");
        ChkInput5.setBorderPainted(true);
        ChkInput5.setBorderPaintedFlat(true);
        ChkInput5.setFocusable(false);
        ChkInput5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput5.setName("ChkInput5"); // NOI18N
        ChkInput5.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput5.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput5.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput5ActionPerformed(evt);
            }
        });
        PanelInput5.add(ChkInput5, java.awt.BorderLayout.PAGE_END);

        FormInput5.setName("FormInput5"); // NOI18N
        FormInput5.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput5.setLayout(null);

        label32.setText("Petugas :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput5.add(label32);
        label32.setBounds(315, 10, 60, 23);

        KdPetugasRanapDokterParamedis.setEditable(false);
        KdPetugasRanapDokterParamedis.setName("KdPetugasRanapDokterParamedis"); // NOI18N
        KdPetugasRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput5.add(KdPetugasRanapDokterParamedis);
        KdPetugasRanapDokterParamedis.setBounds(380, 10, 65, 23);

        NmPetugasRanapDokterParamedis.setEditable(false);
        NmPetugasRanapDokterParamedis.setName("NmPetugasRanapDokterParamedis"); // NOI18N
        NmPetugasRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput5.add(NmPetugasRanapDokterParamedis);
        NmPetugasRanapDokterParamedis.setBounds(447, 10, 150, 23);

        BtnPetugasRanapDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasRanapDokterParamedis.setMnemonic('3');
        BtnPetugasRanapDokterParamedis.setToolTipText("Alt+3");
        BtnPetugasRanapDokterParamedis.setName("BtnPetugasRanapDokterParamedis"); // NOI18N
        BtnPetugasRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasRanapDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasRanapDokterParamedisActionPerformed(evt);
            }
        });
        FormInput5.add(BtnPetugasRanapDokterParamedis);
        BtnPetugasRanapDokterParamedis.setBounds(600, 10, 28, 23);

        label33.setText("Cara Bayar :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput5.add(label33);
        label33.setBounds(640, 10, 70, 23);

        KdCaraBayarRanapDokterParamedis.setEditable(false);
        KdCaraBayarRanapDokterParamedis.setName("KdCaraBayarRanapDokterParamedis"); // NOI18N
        KdCaraBayarRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput5.add(KdCaraBayarRanapDokterParamedis);
        KdCaraBayarRanapDokterParamedis.setBounds(713, 10, 65, 23);

        NmCaraBayarRanapDokterParamedis.setEditable(false);
        NmCaraBayarRanapDokterParamedis.setName("NmCaraBayarRanapDokterParamedis"); // NOI18N
        NmCaraBayarRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput5.add(NmCaraBayarRanapDokterParamedis);
        NmCaraBayarRanapDokterParamedis.setBounds(780, 10, 150, 23);

        BtnCaraBayarRanapDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRanapDokterParamedis.setMnemonic('3');
        BtnCaraBayarRanapDokterParamedis.setToolTipText("Alt+3");
        BtnCaraBayarRanapDokterParamedis.setName("BtnCaraBayarRanapDokterParamedis"); // NOI18N
        BtnCaraBayarRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRanapDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRanapDokterParamedisActionPerformed(evt);
            }
        });
        FormInput5.add(BtnCaraBayarRanapDokterParamedis);
        BtnCaraBayarRanapDokterParamedis.setBounds(933, 10, 28, 23);

        label35.setText("Dokter :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput5.add(label35);
        label35.setBounds(0, 10, 60, 23);

        KdDokterRanapDokterParamedis.setEditable(false);
        KdDokterRanapDokterParamedis.setName("KdDokterRanapDokterParamedis"); // NOI18N
        KdDokterRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput5.add(KdDokterRanapDokterParamedis);
        KdDokterRanapDokterParamedis.setBounds(63, 10, 65, 23);

        NmDokterRanapDokterParamedis.setEditable(false);
        NmDokterRanapDokterParamedis.setName("NmDokterRanapDokterParamedis"); // NOI18N
        NmDokterRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput5.add(NmDokterRanapDokterParamedis);
        NmDokterRanapDokterParamedis.setBounds(130, 10, 150, 23);

        BtnDokterRanapDokterParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterRanapDokterParamedis.setMnemonic('3');
        BtnDokterRanapDokterParamedis.setToolTipText("Alt+3");
        BtnDokterRanapDokterParamedis.setName("BtnDokterRanapDokterParamedis"); // NOI18N
        BtnDokterRanapDokterParamedis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterRanapDokterParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterRanapDokterParamedisActionPerformed(evt);
            }
        });
        FormInput5.add(BtnDokterRanapDokterParamedis);
        BtnDokterRanapDokterParamedis.setBounds(283, 10, 28, 23);

        PanelInput5.add(FormInput5, java.awt.BorderLayout.CENTER);

        internalFrame8.add(PanelInput5, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Ranap Dokter & Paramedis", internalFrame8);

        internalFrame9.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame9.setBorder(null);
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

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

        PanelInput7.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput7.setName("PanelInput7"); // NOI18N
        PanelInput7.setOpaque(false);
        PanelInput7.setPreferredSize(new java.awt.Dimension(192, 65));
        PanelInput7.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput7.setMnemonic('M');
        ChkInput7.setText(".: Filter Data");
        ChkInput7.setBorderPainted(true);
        ChkInput7.setBorderPaintedFlat(true);
        ChkInput7.setFocusable(false);
        ChkInput7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput7.setName("ChkInput7"); // NOI18N
        ChkInput7.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput7.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput7.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput7.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput7ActionPerformed(evt);
            }
        });
        PanelInput7.add(ChkInput7, java.awt.BorderLayout.PAGE_END);

        FormInput7.setName("FormInput7"); // NOI18N
        FormInput7.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput7.setLayout(null);

        label39.setText("Petugas :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput7.add(label39);
        label39.setBounds(315, 10, 60, 23);

        KdPetugasRad.setEditable(false);
        KdPetugasRad.setName("KdPetugasRad"); // NOI18N
        KdPetugasRad.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput7.add(KdPetugasRad);
        KdPetugasRad.setBounds(380, 10, 65, 23);

        NmPetugasRad.setEditable(false);
        NmPetugasRad.setName("NmPetugasRad"); // NOI18N
        NmPetugasRad.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput7.add(NmPetugasRad);
        NmPetugasRad.setBounds(447, 10, 150, 23);

        BtnPetugasRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasRad.setMnemonic('3');
        BtnPetugasRad.setToolTipText("Alt+3");
        BtnPetugasRad.setName("BtnPetugasRad"); // NOI18N
        BtnPetugasRad.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasRadActionPerformed(evt);
            }
        });
        FormInput7.add(BtnPetugasRad);
        BtnPetugasRad.setBounds(600, 10, 28, 23);

        label40.setText("Cara Bayar :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput7.add(label40);
        label40.setBounds(640, 10, 70, 23);

        KdCaraBayarRad.setEditable(false);
        KdCaraBayarRad.setName("KdCaraBayarRad"); // NOI18N
        KdCaraBayarRad.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput7.add(KdCaraBayarRad);
        KdCaraBayarRad.setBounds(713, 10, 65, 23);

        NmCaraBayarRad.setEditable(false);
        NmCaraBayarRad.setName("NmCaraBayarRad"); // NOI18N
        NmCaraBayarRad.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput7.add(NmCaraBayarRad);
        NmCaraBayarRad.setBounds(780, 10, 150, 23);

        BtnCaraBayarRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRad.setMnemonic('3');
        BtnCaraBayarRad.setToolTipText("Alt+3");
        BtnCaraBayarRad.setName("BtnCaraBayarRad"); // NOI18N
        BtnCaraBayarRad.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRadActionPerformed(evt);
            }
        });
        FormInput7.add(BtnCaraBayarRad);
        BtnCaraBayarRad.setBounds(933, 10, 28, 23);

        label41.setText("Perujuk :");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput7.add(label41);
        label41.setBounds(0, 10, 60, 23);

        KdDokterPerujukRad.setEditable(false);
        KdDokterPerujukRad.setName("KdDokterPerujukRad"); // NOI18N
        KdDokterPerujukRad.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput7.add(KdDokterPerujukRad);
        KdDokterPerujukRad.setBounds(63, 10, 65, 23);

        NmDokterPerujukRad.setEditable(false);
        NmDokterPerujukRad.setName("NmDokterPerujukRad"); // NOI18N
        NmDokterPerujukRad.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput7.add(NmDokterPerujukRad);
        NmDokterPerujukRad.setBounds(130, 10, 150, 23);

        BtnDokterPerujukRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterPerujukRad.setMnemonic('3');
        BtnDokterPerujukRad.setToolTipText("Alt+3");
        BtnDokterPerujukRad.setName("BtnDokterPerujukRad"); // NOI18N
        BtnDokterPerujukRad.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterPerujukRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterPerujukRadActionPerformed(evt);
            }
        });
        FormInput7.add(BtnDokterPerujukRad);
        BtnDokterPerujukRad.setBounds(283, 10, 28, 23);

        PanelInput7.add(FormInput7, java.awt.BorderLayout.CENTER);

        internalFrame9.add(PanelInput7, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Radiologi", internalFrame9);

        internalFrame10.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame10.setBorder(null);
        internalFrame10.setName("internalFrame10"); // NOI18N
        internalFrame10.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

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

        PanelInput8.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput8.setName("PanelInput8"); // NOI18N
        PanelInput8.setOpaque(false);
        PanelInput8.setPreferredSize(new java.awt.Dimension(192, 65));
        PanelInput8.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput8.setMnemonic('M');
        ChkInput8.setText(".: Filter Data");
        ChkInput8.setBorderPainted(true);
        ChkInput8.setBorderPaintedFlat(true);
        ChkInput8.setFocusable(false);
        ChkInput8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput8.setName("ChkInput8"); // NOI18N
        ChkInput8.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput8.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput8.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput8.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput8ActionPerformed(evt);
            }
        });
        PanelInput8.add(ChkInput8, java.awt.BorderLayout.PAGE_END);

        FormInput8.setName("FormInput8"); // NOI18N
        FormInput8.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput8.setLayout(null);

        label42.setText("Petugas :");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput8.add(label42);
        label42.setBounds(315, 10, 60, 23);

        KdPetugasLab.setEditable(false);
        KdPetugasLab.setName("KdPetugasLab"); // NOI18N
        KdPetugasLab.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput8.add(KdPetugasLab);
        KdPetugasLab.setBounds(380, 10, 65, 23);

        NmPetugasLab.setEditable(false);
        NmPetugasLab.setName("NmPetugasLab"); // NOI18N
        NmPetugasLab.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput8.add(NmPetugasLab);
        NmPetugasLab.setBounds(447, 10, 150, 23);

        BtnPetugasLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasLab.setMnemonic('3');
        BtnPetugasLab.setToolTipText("Alt+3");
        BtnPetugasLab.setName("BtnPetugasLab"); // NOI18N
        BtnPetugasLab.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasLabActionPerformed(evt);
            }
        });
        FormInput8.add(BtnPetugasLab);
        BtnPetugasLab.setBounds(600, 10, 28, 23);

        label43.setText("Cara Bayar :");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput8.add(label43);
        label43.setBounds(640, 10, 70, 23);

        KdCaraBayarLab.setEditable(false);
        KdCaraBayarLab.setName("KdCaraBayarLab"); // NOI18N
        KdCaraBayarLab.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput8.add(KdCaraBayarLab);
        KdCaraBayarLab.setBounds(713, 10, 65, 23);

        NmCaraBayarLab.setEditable(false);
        NmCaraBayarLab.setName("NmCaraBayarLab"); // NOI18N
        NmCaraBayarLab.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput8.add(NmCaraBayarLab);
        NmCaraBayarLab.setBounds(780, 10, 150, 23);

        BtnCaraBayarLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarLab.setMnemonic('3');
        BtnCaraBayarLab.setToolTipText("Alt+3");
        BtnCaraBayarLab.setName("BtnCaraBayarLab"); // NOI18N
        BtnCaraBayarLab.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarLabActionPerformed(evt);
            }
        });
        FormInput8.add(BtnCaraBayarLab);
        BtnCaraBayarLab.setBounds(933, 10, 28, 23);

        label44.setText("Perujuk :");
        label44.setName("label44"); // NOI18N
        label44.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput8.add(label44);
        label44.setBounds(0, 10, 60, 23);

        KdDokterPerujukLab.setEditable(false);
        KdDokterPerujukLab.setName("KdDokterPerujukLab"); // NOI18N
        KdDokterPerujukLab.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput8.add(KdDokterPerujukLab);
        KdDokterPerujukLab.setBounds(63, 10, 65, 23);

        NmDokterPerujukLab.setEditable(false);
        NmDokterPerujukLab.setName("NmDokterPerujukLab"); // NOI18N
        NmDokterPerujukLab.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput8.add(NmDokterPerujukLab);
        NmDokterPerujukLab.setBounds(130, 10, 150, 23);

        BtnDokterPerujukLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterPerujukLab.setMnemonic('3');
        BtnDokterPerujukLab.setToolTipText("Alt+3");
        BtnDokterPerujukLab.setName("BtnDokterPerujukLab"); // NOI18N
        BtnDokterPerujukLab.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterPerujukLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterPerujukLabActionPerformed(evt);
            }
        });
        FormInput8.add(BtnDokterPerujukLab);
        BtnDokterPerujukLab.setBounds(283, 10, 28, 23);

        PanelInput8.add(FormInput8, java.awt.BorderLayout.CENTER);

        internalFrame10.add(PanelInput8, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pemeriksaan Laboratorium", internalFrame10);

        internalFrame11.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame11.setBorder(null);
        internalFrame11.setName("internalFrame11"); // NOI18N
        internalFrame11.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

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

        PanelInput9.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput9.setName("PanelInput9"); // NOI18N
        PanelInput9.setOpaque(false);
        PanelInput9.setPreferredSize(new java.awt.Dimension(192, 65));
        PanelInput9.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput9.setMnemonic('M');
        ChkInput9.setText(".: Filter Data");
        ChkInput9.setBorderPainted(true);
        ChkInput9.setBorderPaintedFlat(true);
        ChkInput9.setFocusable(false);
        ChkInput9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput9.setName("ChkInput9"); // NOI18N
        ChkInput9.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput9.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput9.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput9.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput9ActionPerformed(evt);
            }
        });
        PanelInput9.add(ChkInput9, java.awt.BorderLayout.PAGE_END);

        FormInput9.setName("FormInput9"); // NOI18N
        FormInput9.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput9.setLayout(null);

        label45.setText("Petugas :");
        label45.setName("label45"); // NOI18N
        label45.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput9.add(label45);
        label45.setBounds(315, 10, 60, 23);

        KdPetugasDetailLab.setEditable(false);
        KdPetugasDetailLab.setName("KdPetugasDetailLab"); // NOI18N
        KdPetugasDetailLab.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput9.add(KdPetugasDetailLab);
        KdPetugasDetailLab.setBounds(380, 10, 65, 23);

        NmPetugasDetailLab.setEditable(false);
        NmPetugasDetailLab.setName("NmPetugasDetailLab"); // NOI18N
        NmPetugasDetailLab.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput9.add(NmPetugasDetailLab);
        NmPetugasDetailLab.setBounds(447, 10, 150, 23);

        BtnPetugasDetailLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugasDetailLab.setMnemonic('3');
        BtnPetugasDetailLab.setToolTipText("Alt+3");
        BtnPetugasDetailLab.setName("BtnPetugasDetailLab"); // NOI18N
        BtnPetugasDetailLab.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugasDetailLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasDetailLabActionPerformed(evt);
            }
        });
        FormInput9.add(BtnPetugasDetailLab);
        BtnPetugasDetailLab.setBounds(600, 10, 28, 23);

        label46.setText("Cara Bayar :");
        label46.setName("label46"); // NOI18N
        label46.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput9.add(label46);
        label46.setBounds(640, 10, 70, 23);

        KdCaraBayarDetailLab.setEditable(false);
        KdCaraBayarDetailLab.setName("KdCaraBayarDetailLab"); // NOI18N
        KdCaraBayarDetailLab.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput9.add(KdCaraBayarDetailLab);
        KdCaraBayarDetailLab.setBounds(713, 10, 65, 23);

        NmCaraBayarDetailLab.setEditable(false);
        NmCaraBayarDetailLab.setName("NmCaraBayarDetailLab"); // NOI18N
        NmCaraBayarDetailLab.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput9.add(NmCaraBayarDetailLab);
        NmCaraBayarDetailLab.setBounds(780, 10, 150, 23);

        BtnCaraBayarDetailLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarDetailLab.setMnemonic('3');
        BtnCaraBayarDetailLab.setToolTipText("Alt+3");
        BtnCaraBayarDetailLab.setName("BtnCaraBayarDetailLab"); // NOI18N
        BtnCaraBayarDetailLab.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarDetailLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarDetailLabActionPerformed(evt);
            }
        });
        FormInput9.add(BtnCaraBayarDetailLab);
        BtnCaraBayarDetailLab.setBounds(933, 10, 28, 23);

        label47.setText("Perujuk :");
        label47.setName("label47"); // NOI18N
        label47.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput9.add(label47);
        label47.setBounds(0, 10, 60, 23);

        KdDokterPerujukDetailLab.setEditable(false);
        KdDokterPerujukDetailLab.setName("KdDokterPerujukDetailLab"); // NOI18N
        KdDokterPerujukDetailLab.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput9.add(KdDokterPerujukDetailLab);
        KdDokterPerujukDetailLab.setBounds(63, 10, 65, 23);

        NmDokterPerujukDetailLab.setEditable(false);
        NmDokterPerujukDetailLab.setName("NmDokterPerujukDetailLab"); // NOI18N
        NmDokterPerujukDetailLab.setPreferredSize(new java.awt.Dimension(150, 23));
        FormInput9.add(NmDokterPerujukDetailLab);
        NmDokterPerujukDetailLab.setBounds(130, 10, 150, 23);

        BtnDokterPerujukDetailLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokterPerujukDetailLab.setMnemonic('3');
        BtnDokterPerujukDetailLab.setToolTipText("Alt+3");
        BtnDokterPerujukDetailLab.setName("BtnDokterPerujukDetailLab"); // NOI18N
        BtnDokterPerujukDetailLab.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokterPerujukDetailLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterPerujukDetailLabActionPerformed(evt);
            }
        });
        FormInput9.add(BtnDokterPerujukDetailLab);
        BtnDokterPerujukDetailLab.setBounds(283, 10, 28, 23);

        PanelInput9.add(FormInput9, java.awt.BorderLayout.CENTER);

        internalFrame11.add(PanelInput9, java.awt.BorderLayout.PAGE_START);

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
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    if(KdDokterRalanDokter.getText().equals("")&&NmDokterRalanDokter.getText().equals("")&&KdPoliRalanDokter.getText().equals("")&&NmPoliRalanDokter.getText().equals("")&&KdCaraBayarRalanDokter.getText().equals("")&&NmCaraBayarRalanDokter.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailTindakanRalanDokter.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Dokter ]::",
                           "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                           "rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.tgl_perawatan,"+
                           "rawat_jl_dr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                           "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                           "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_jl_dr on reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                           "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                           "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "where rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by rawat_jl_dr.no_rawat desc",param);
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailTindakanRalanDokter.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Dokter ]::",
                                "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                                "pasien.nm_pasien,rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                                "rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.tgl_perawatan,"+
                                "rawat_jl_dr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                                "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                                "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                                "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join rawat_jl_dr on reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                                "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                                "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                "where rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and rawat_jl_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                "rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                "rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                "rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                "rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and rawat_jl_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                "rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                "rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                "rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' "+
                               " order by rawat_jl_dr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRalanDokter.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Dokter ]::",
                                "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                                "pasien.nm_pasien,rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                                "rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.tgl_perawatan,"+
                                "rawat_jl_dr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                                "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                                "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                                "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join rawat_jl_dr on reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                                "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                                "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and rawat_jl_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and rawat_jl_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' "+
                               " order by rawat_jl_dr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRalanDokter.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Dokter ]::",
                                "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                                "pasien.nm_pasien,rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                                "rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.tgl_perawatan,"+
                                "rawat_jl_dr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                                "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                                "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                                "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join rawat_jl_dr on reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                                "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                                "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and rawat_jl_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and rawat_jl_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%' and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' "+
                               " order by rawat_jl_dr.no_rawat desc",param);
                        }
                    }
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
                    if(KdPetugasRalanParamedis.getText().equals("")&&NmPetugasRalanParamedis.getText().equals("")&&KdPoliRalanParamedis.getText().equals("")&&NmPoliRalanParamedis.getText().equals("")&&KdCaraBayarRalanParamedis.getText().equals("")&&NmCaraBayarRalanParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailTindakanRalanParamedis.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Petugas ]::",
                                "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                               "pasien.nm_pasien,rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                               "rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.tgl_perawatan,"+
                               "rawat_jl_pr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                               "rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.tarif_tindakanpr,"+
                               "rawat_jl_pr.kso,rawat_jl_pr.menejemen,rawat_jl_pr.biaya_rawat "+
                               "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                               "inner join rawat_jl_pr on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                               "inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                               "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                               "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                               "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                               "where rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by rawat_jl_pr.no_rawat desc",param);  
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailTindakanRalanParamedis.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Petugas ]::",
                                "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                                   "rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.tgl_perawatan,"+
                                   "rawat_jl_pr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                                   "rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.tarif_tindakanpr,"+
                                   "rawat_jl_pr.kso,rawat_jl_pr.menejemen,rawat_jl_pr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_jl_pr on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                   "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "where rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and rawat_jl_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and rawat_jl_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_jl_pr.no_rawat desc",param);  
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRalanParamedis.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Petugas ]::",
                                "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                                   "rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.tgl_perawatan,"+
                                   "rawat_jl_pr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                                   "rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.tarif_tindakanpr,"+
                                   "rawat_jl_pr.kso,rawat_jl_pr.menejemen,rawat_jl_pr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_jl_pr on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                   "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and rawat_jl_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and rawat_jl_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_jl_pr.no_rawat desc",param);  
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRalanParamedis.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Petugas ]::",
                                "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                                   "rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.tgl_perawatan,"+
                                   "rawat_jl_pr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                                   "rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.tarif_tindakanpr,"+
                                   "rawat_jl_pr.kso,rawat_jl_pr.menejemen,rawat_jl_pr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_jl_pr on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                   "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and rawat_jl_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and rawat_jl_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%' and concat(rawat_jl_pr.nip,petugas.nama) like '%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_jl_pr.no_rawat desc",param);  
                        }
                    }
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
                    if(KdDokterRalanDokterParamedis.getText().equals("")&&NmDokterRalanDokterParamedis.getText().equals("")&&KdPetugasRalanDokterParamedis.getText().equals("")&&NmPetugasRalanDokterParamedis.getText().equals("")&&KdPoliRalanDokterParamedis.getText().equals("")&&NmPoliRalanDokterParamedis.getText().equals("")&&KdCaraBayarRalanDokterParamedis.getText().equals("")&&NmCaraBayarRalanDokterParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailTindakanRalanDokterParamedis.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Dokter & Petugas ]::",
                               "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                               "pasien.nm_pasien,rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                               "rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,"+
                               "rawat_jl_drpr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                               "rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,"+
                               "rawat_jl_drpr.kso,rawat_jl_drpr.menejemen,rawat_jl_drpr.biaya_rawat "+
                               "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                               "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                               "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                               "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                               "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                               "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                               "inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                               "where rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by rawat_jl_drpr.no_rawat desc",param);
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailTindakanRalanDokterParamedis.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Dokter & Petugas ]::",
                                   "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                                   "rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,"+
                                   "rawat_jl_drpr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                                   "rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,"+
                                   "rawat_jl_drpr.kso,rawat_jl_drpr.menejemen,rawat_jl_drpr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                   "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                                   "where rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and rawat_jl_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and rawat_jl_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and rawat_jl_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_jl_drpr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRalanDokterParamedis.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Dokter & Petugas ]::",
                                   "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                                   "rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,"+
                                   "rawat_jl_drpr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                                   "rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,"+
                                   "rawat_jl_drpr.kso,rawat_jl_drpr.menejemen,rawat_jl_drpr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                   "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                                   "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and rawat_jl_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and rawat_jl_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and rawat_jl_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_jl_drpr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRalanDokterParamedis.jasper","report","::[ Detail Tindakan Ralan Yang Ditangani Dokter & Petugas ]::",
                                   "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                                   "rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,"+
                                   "rawat_jl_drpr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                                   "rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,"+
                                   "rawat_jl_drpr.kso,rawat_jl_drpr.menejemen,rawat_jl_drpr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                   "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and rawat_jl_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and rawat_jl_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and rawat_jl_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like '%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%' and concat(rawat_jl_drpr.nip,petugas.nama) like '%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_jl_drpr.no_rawat desc",param);
                        }

                    }
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
                    if(KdOperatorOperasi.getText().equals("")&&NmOperatorOperasi.getText().equals("")&&KdAsistenOperasi.getText().equals("")&&NmAsistenOperasi.getText().equals("")&&KdCaraBayarOperasi.getText().equals("")&&NmCaraBayarOperasi.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailTindakanOperasi.jasper","report","::[ Detail Tindakan Operasi ]::",
                               "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                               "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                               "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                               "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                               "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                               "operator1.nm_dokter as operator1,operasi.biayaoperator1, "+
                               "operator2.nm_dokter as operator2,operasi.biayaoperator2, "+
                               "operator3.nm_dokter as operator3,operasi.biayaoperator3,"+
                               "asisten_operator1.nama as asisten_operator1,operasi.biayaasisten_operator1, "+
                               "asisten_operator2.nama as asisten_operator2,operasi.biayaasisten_operator2, "+
                               "asisten_operator3.nama as asisten_operator3,operasi.biayaasisten_operator3, "+
                               "instrumen.nama as instrumen,operasi.biayainstrumen, "+
                               "dokter_anak.nm_dokter as dokter_anak,operasi.biayadokter_anak, "+
                               "perawaat_resusitas.nama as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                               "dokter_anestesi.nm_dokter as dokter_anestesi,operasi.biayadokter_anestesi, "+
                               "asisten_anestesi.nama as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                               "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                               "bidan.nama as bidan,operasi.biayabidan, "+
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
                               "operasi.biayaalat,operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biayasarpras, "+
                               "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biayasarpras) as total "+
                               "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                               "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                               "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                               "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                               "inner join dokter as operator1 on operator1.kd_dokter=operasi.operator1 "+
                               "inner join dokter as operator2 on operator2.kd_dokter=operasi.operator2 "+
                               "inner join dokter as operator3 on operator3.kd_dokter=operasi.operator3 "+
                               "inner join dokter as dokter_anak on dokter_anak.kd_dokter=operasi.dokter_anak "+
                               "inner join dokter as dokter_anestesi on dokter_anestesi.kd_dokter=operasi.dokter_anestesi "+
                               "inner join petugas as asisten_operator1 on asisten_operator1.nip=operasi.asisten_operator1 "+
                               "inner join petugas as asisten_operator2 on asisten_operator2.nip=operasi.asisten_operator2 "+
                               "inner join petugas as asisten_operator3 on asisten_operator3.nip=operasi.asisten_operator3 "+
                               "inner join petugas as asisten_anestesi on asisten_anestesi.nip=operasi.asisten_anestesi "+
                               "inner join petugas as bidan on bidan.nip=operasi.bidan "+
                               "inner join petugas as instrumen on instrumen.nip=operasi.instrumen "+
                               "inner join petugas as perawaat_resusitas on perawaat_resusitas.nip=operasi.perawaat_resusitas "+
                               "where operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' order by operasi.no_rawat desc",param);
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailTindakanOperasi.jasper","report","::[ Detail Tindakan Operasi ]::",
                                   "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                                   "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                                   "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                                   "operator1.nm_dokter as operator1,operasi.biayaoperator1, "+
                                   "operator2.nm_dokter as operator2,operasi.biayaoperator2, "+
                                   "operator3.nm_dokter as operator3,operasi.biayaoperator3,"+
                                   "asisten_operator1.nama as asisten_operator1,operasi.biayaasisten_operator1, "+
                                   "asisten_operator2.nama as asisten_operator2,operasi.biayaasisten_operator2, "+
                                   "asisten_operator3.nama as asisten_operator3,operasi.biayaasisten_operator3, "+
                                   "instrumen.nama as instrumen,operasi.biayainstrumen, "+
                                   "dokter_anak.nm_dokter as dokter_anak,operasi.biayadokter_anak, "+
                                   "perawaat_resusitas.nama as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                                   "dokter_anestesi.nm_dokter as dokter_anestesi,operasi.biayadokter_anestesi, "+
                                   "asisten_anestesi.nama as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                                   "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                                   "bidan.nama as bidan,operasi.biayabidan, "+
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
                                   "operasi.biayaalat,operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biayasarpras, "+
                                   "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biayasarpras) as total "+
                                   "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join dokter as operator1 on operator1.kd_dokter=operasi.operator1 "+
                                   "inner join dokter as operator2 on operator2.kd_dokter=operasi.operator2 "+
                                   "inner join dokter as operator3 on operator3.kd_dokter=operasi.operator3 "+
                                   "inner join dokter as dokter_anak on dokter_anak.kd_dokter=operasi.dokter_anak "+
                                   "inner join dokter as dokter_anestesi on dokter_anestesi.kd_dokter=operasi.dokter_anestesi "+
                                   "inner join petugas as asisten_operator1 on asisten_operator1.nip=operasi.asisten_operator1 "+
                                   "inner join petugas as asisten_operator2 on asisten_operator2.nip=operasi.asisten_operator2 "+
                                   "inner join petugas as asisten_operator3 on asisten_operator3.nip=operasi.asisten_operator3 "+
                                   "inner join petugas as asisten_anestesi on asisten_anestesi.nip=operasi.asisten_anestesi "+
                                   "inner join petugas as bidan on bidan.nip=operasi.bidan "+
                                   "inner join petugas as instrumen on instrumen.nip=operasi.instrumen "+
                                   "inner join petugas as perawaat_resusitas on perawaat_resusitas.nip=operasi.perawaat_resusitas "+
                                   "where operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operasi.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operasi.kode_paket like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and paket_operasi.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operator1.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operator2.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operator3.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and dokter_anak.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_operator1.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_operator2.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_operator3.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and instrumen.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and perawaat_resusitas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_anestesi.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and bidan.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and dokter_anestesi.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                                   "order by operasi.no_rawat desc",param);   
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailTindakanOperasi.jasper","report","::[ Detail Tindakan Operasi ]::",
                                   "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                                   "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                                   "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                                   "operator1.nm_dokter as operator1,operasi.biayaoperator1, "+
                                   "operator2.nm_dokter as operator2,operasi.biayaoperator2, "+
                                   "operator3.nm_dokter as operator3,operasi.biayaoperator3,"+
                                   "asisten_operator1.nama as asisten_operator1,operasi.biayaasisten_operator1, "+
                                   "asisten_operator2.nama as asisten_operator2,operasi.biayaasisten_operator2, "+
                                   "asisten_operator3.nama as asisten_operator3,operasi.biayaasisten_operator3, "+
                                   "instrumen.nama as instrumen,operasi.biayainstrumen, "+
                                   "dokter_anak.nm_dokter as dokter_anak,operasi.biayadokter_anak, "+
                                   "perawaat_resusitas.nama as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                                   "dokter_anestesi.nm_dokter as dokter_anestesi,operasi.biayadokter_anestesi, "+
                                   "asisten_anestesi.nama as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                                   "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                                   "bidan.nama as bidan,operasi.biayabidan, "+
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
                                   "operasi.biayaalat,operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biayasarpras, "+
                                   "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biayasarpras) as total "+
                                   "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join dokter as operator1 on operator1.kd_dokter=operasi.operator1 "+
                                   "inner join dokter as operator2 on operator2.kd_dokter=operasi.operator2 "+
                                   "inner join dokter as operator3 on operator3.kd_dokter=operasi.operator3 "+
                                   "inner join dokter as dokter_anak on dokter_anak.kd_dokter=operasi.dokter_anak "+
                                   "inner join dokter as dokter_anestesi on dokter_anestesi.kd_dokter=operasi.dokter_anestesi "+
                                   "inner join petugas as asisten_operator1 on asisten_operator1.nip=operasi.asisten_operator1 "+
                                   "inner join petugas as asisten_operator2 on asisten_operator2.nip=operasi.asisten_operator2 "+
                                   "inner join petugas as asisten_operator3 on asisten_operator3.nip=operasi.asisten_operator3 "+
                                   "inner join petugas as asisten_anestesi on asisten_anestesi.nip=operasi.asisten_anestesi "+
                                   "inner join petugas as bidan on bidan.nip=operasi.bidan "+
                                   "inner join petugas as instrumen on instrumen.nip=operasi.instrumen "+
                                   "inner join petugas as perawaat_resusitas on perawaat_resusitas.nip=operasi.perawaat_resusitas "+
                                   "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operasi.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operasi.kode_paket like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and paket_operasi.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operator1.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operator2.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operator3.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and dokter_anak.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_operator1.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_operator2.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_operator3.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and instrumen.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and perawaat_resusitas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_anestesi.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and bidan.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and dokter_anestesi.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                                   "order by operasi.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailTindakanOperasi.jasper","report","::[ Detail Tindakan Operasi ]::",
                                   "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                                   "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                                   "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                                   "operator1.nm_dokter as operator1,operasi.biayaoperator1, "+
                                   "operator2.nm_dokter as operator2,operasi.biayaoperator2, "+
                                   "operator3.nm_dokter as operator3,operasi.biayaoperator3,"+
                                   "asisten_operator1.nama as asisten_operator1,operasi.biayaasisten_operator1, "+
                                   "asisten_operator2.nama as asisten_operator2,operasi.biayaasisten_operator2, "+
                                   "asisten_operator3.nama as asisten_operator3,operasi.biayaasisten_operator3, "+
                                   "instrumen.nama as instrumen,operasi.biayainstrumen, "+
                                   "dokter_anak.nm_dokter as dokter_anak,operasi.biayadokter_anak, "+
                                   "perawaat_resusitas.nama as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                                   "dokter_anestesi.nm_dokter as dokter_anestesi,operasi.biayadokter_anestesi, "+
                                   "asisten_anestesi.nama as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                                   "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                                   "bidan.nama as bidan,operasi.biayabidan, "+
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
                                   "operasi.biayaalat,operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biayasarpras, "+
                                   "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biayasarpras) as total "+
                                   "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join dokter as operator1 on operator1.kd_dokter=operasi.operator1 "+
                                   "inner join dokter as operator2 on operator2.kd_dokter=operasi.operator2 "+
                                   "inner join dokter as operator3 on operator3.kd_dokter=operasi.operator3 "+
                                   "inner join dokter as dokter_anak on dokter_anak.kd_dokter=operasi.dokter_anak "+
                                   "inner join dokter as dokter_anestesi on dokter_anestesi.kd_dokter=operasi.dokter_anestesi "+
                                   "inner join petugas as asisten_operator1 on asisten_operator1.nip=operasi.asisten_operator1 "+
                                   "inner join petugas as asisten_operator2 on asisten_operator2.nip=operasi.asisten_operator2 "+
                                   "inner join petugas as asisten_operator3 on asisten_operator3.nip=operasi.asisten_operator3 "+
                                   "inner join petugas as asisten_anestesi on asisten_anestesi.nip=operasi.asisten_anestesi "+
                                   "inner join petugas as bidan on bidan.nip=operasi.bidan "+
                                   "inner join petugas as instrumen on instrumen.nip=operasi.instrumen "+
                                   "inner join petugas as perawaat_resusitas on perawaat_resusitas.nip=operasi.perawaat_resusitas "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operasi.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operasi.kode_paket like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and paket_operasi.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operator1.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operator2.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and operator3.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and dokter_anak.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_operator1.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_operator2.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_operator3.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and instrumen.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and perawaat_resusitas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and asisten_anestesi.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and bidan.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' and concat(operasi.operator1,operator1.nm_dokter) like '%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%' and concat(operasi.asisten_operator1,asisten_operator1.nama) like '%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%' and dokter_anestesi.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                                   "order by operasi.no_rawat desc",param);
                        }   
                    }
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
                    if(KdDokterRanapDokter.getText().equals("")&&NmDokterRanapDokter.getText().equals("")&&KdCaraBayarRanapDokter.getText().equals("")&&NmCaraBayarRanapDokter.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailTindakanRanapDokter.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Dokter ]::",
                               "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                               "pasien.nm_pasien,rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                               "rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.tgl_perawatan,"+
                               "rawat_inap_dr.jam_rawat,penjab.png_jawab,"+
                               "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                               "inner join kamar inner join bangsal on "+
                               "kamar_inap.kd_kamar=kamar.kd_kamar "+
                               "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_dr.no_rawat limit 1),'Ruang Terhapus' ) as nm_bangsal , " +
                               "rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.tarif_tindakandr,"+
                               "rawat_inap_dr.kso,rawat_inap_dr.menejemen,rawat_inap_dr.biaya_rawat "+
                               "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                               "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                               "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                               "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                               "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                               "where rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by rawat_inap_dr.no_rawat desc",param);
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailTindakanRanapDokter.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Dokter ]::",
                                   "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                   "rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.tgl_perawatan,"+
                                   "rawat_inap_dr.jam_rawat,penjab.png_jawab,"+
                                   "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                                   "inner join kamar inner join bangsal on "+
                                   "kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_dr.no_rawat limit 1),'Ruang Terhapus' ) as nm_bangsal , " +
                                   "rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.tarif_tindakandr,"+
                                   "rawat_inap_dr.kso,rawat_inap_dr.menejemen,rawat_inap_dr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                   "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "where rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and rawat_inap_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and rawat_inap_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_inap_dr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRanapDokter.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Dokter ]::",
                                   "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                   "rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.tgl_perawatan,"+
                                   "rawat_inap_dr.jam_rawat,penjab.png_jawab,"+
                                   "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                                   "inner join kamar inner join bangsal on "+
                                   "kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_dr.no_rawat limit 1),'Ruang Terhapus' ) as nm_bangsal , " +
                                   "rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.tarif_tindakandr,"+
                                   "rawat_inap_dr.kso,rawat_inap_dr.menejemen,rawat_inap_dr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                   "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and rawat_inap_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and rawat_inap_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_inap_dr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRanapDokter.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Dokter ]::",
                                   "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                   "rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.tgl_perawatan,"+
                                   "rawat_inap_dr.jam_rawat,penjab.png_jawab,"+
                                   "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                                   "inner join kamar inner join bangsal on "+
                                   "kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_dr.no_rawat limit 1),'Ruang Terhapus' ) as nm_bangsal , " +
                                   "rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.tarif_tindakandr,"+
                                   "rawat_inap_dr.kso,rawat_inap_dr.menejemen,rawat_inap_dr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                   "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and rawat_inap_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and rawat_inap_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_inap_dr.no_rawat desc",param);
                        }
                    }
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
                    if(KdPetugasRanapParamedis.getText().equals("")&&NmPetugasRanapParamedis.getText().equals("")&&KdCaraBayarRanapParamedis.getText().equals("")&&NmCaraBayarRanapParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailTindakanRanapParamedis.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Paramedis ]::",
                              "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                               "pasien.nm_pasien,rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                               "rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.tgl_perawatan,"+
                               "rawat_inap_pr.jam_rawat,penjab.png_jawab,"+
                               "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                               "inner join kamar inner join bangsal on "+
                               "kamar_inap.kd_kamar=kamar.kd_kamar "+
                               "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_pr.no_rawat limit 1 ),'Ruang Terhapus' ) as nm_bangsal, " +
                               "rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.tarif_tindakanpr,"+
                               "rawat_inap_pr.kso,rawat_inap_pr.menejemen,rawat_inap_pr.biaya_rawat "+
                               "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                               "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                               "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                               "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                               "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                               "where rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by rawat_inap_pr.no_rawat desc",param);
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailTindakanRanapParamedis.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Paramedis ]::",
                                   "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                   "rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.tgl_perawatan,"+
                                   "rawat_inap_pr.jam_rawat,penjab.png_jawab,"+
                                   "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                                   "inner join kamar inner join bangsal on "+
                                   "kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_pr.no_rawat limit 1 ),'Ruang Terhapus' ) as nm_bangsal, " +
                                   "rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.tarif_tindakanpr,"+
                                   "rawat_inap_pr.kso,rawat_inap_pr.menejemen,rawat_inap_pr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                   "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "where rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and rawat_inap_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and rawat_inap_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_inap_pr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRanapParamedis.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Paramedis ]::",
                                   "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                   "rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.tgl_perawatan,"+
                                   "rawat_inap_pr.jam_rawat,penjab.png_jawab,"+
                                   "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                                   "inner join kamar inner join bangsal on "+
                                   "kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_pr.no_rawat limit 1 ),'Ruang Terhapus' ) as nm_bangsal, " +
                                   "rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.tarif_tindakanpr,"+
                                   "rawat_inap_pr.kso,rawat_inap_pr.menejemen,rawat_inap_pr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                   "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and rawat_inap_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and rawat_inap_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_inap_pr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRanapParamedis.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Paramedis ]::",
                                   "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                   "rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.tgl_perawatan,"+
                                   "rawat_inap_pr.jam_rawat,penjab.png_jawab,"+
                                   "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                                   "inner join kamar inner join bangsal on "+
                                   "kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_pr.no_rawat limit 1 ),'Ruang Terhapus' ) as nm_bangsal, " +
                                   "rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.tarif_tindakanpr,"+
                                   "rawat_inap_pr.kso,rawat_inap_pr.menejemen,rawat_inap_pr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                   "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and rawat_inap_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and rawat_inap_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_pr.nip,petugas.nama) like '%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by rawat_inap_pr.no_rawat desc",param);
                        }
                    }
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
                    if(KdDokterRanapDokterParamedis.getText().equals("")&&NmDokterRanapDokterParamedis.getText().equals("")&&KdPetugasRanapDokterParamedis.getText().equals("")&&NmPetugasRanapDokterParamedis.getText().equals("")&&KdCaraBayarRanapDokterParamedis.getText().equals("")&&NmCaraBayarRanapDokterParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailTindakanRanapDokterParamedis.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Dokter & Paramedis ]::",
                               "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                               "pasien.nm_pasien,rawat_inap_drpr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                               "rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,"+
                               "rawat_inap_drpr.jam_rawat,penjab.png_jawab,ifnull((select bangsal.nm_bangsal from kamar_inap "+
                               "inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                               "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_drpr.no_rawat limit 1 ),'Ruang Terhapus' ) as nm_bangsal , " +
                               "rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,"+
                               "rawat_inap_drpr.kso,rawat_inap_drpr.menejemen,rawat_inap_drpr.biaya_rawat "+
                               "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                               "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                               "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                               "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                               "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                               "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                               "inner join petugas on rawat_inap_drpr.nip=petugas.nip  "+
                               "where rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by rawat_inap_drpr.no_rawat desc",param);
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailTindakanRanapDokterParamedis.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Dokter & Paramedis ]::",
                                   "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_inap_drpr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                   "rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,"+
                                   "rawat_inap_drpr.jam_rawat,penjab.png_jawab,ifnull((select bangsal.nm_bangsal from kamar_inap "+
                                   "inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_drpr.no_rawat limit 1 ),'Ruang Terhapus' ) as nm_bangsal , " +
                                   "rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,"+
                                   "rawat_inap_drpr.kso,rawat_inap_drpr.menejemen,rawat_inap_drpr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                   "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join petugas on rawat_inap_drpr.nip=petugas.nip  "+
                                   "where rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and rawat_inap_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and rawat_inap_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and rawat_inap_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%'  "+
                                   " order by rawat_inap_drpr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRanapDokterParamedis.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Dokter & Paramedis ]::",
                                   "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_inap_drpr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                   "rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,"+
                                   "rawat_inap_drpr.jam_rawat,penjab.png_jawab,ifnull((select bangsal.nm_bangsal from kamar_inap "+
                                   "inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_drpr.no_rawat limit 1 ),'Ruang Terhapus' ) as nm_bangsal , " +
                                   "rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,"+
                                   "rawat_inap_drpr.kso,rawat_inap_drpr.menejemen,rawat_inap_drpr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                   "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join petugas on rawat_inap_drpr.nip=petugas.nip  "+
                                   "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and rawat_inap_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and rawat_inap_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and rawat_inap_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%'  "+
                                   " order by rawat_inap_drpr.no_rawat desc",param);
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailTindakanRanapDokterParamedis.jasper","report","::[ Detail Tindakan Ranap Yang Ditangani Dokter & Paramedis ]::",
                                   "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                                   "pasien.nm_pasien,rawat_inap_drpr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                   "rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,"+
                                   "rawat_inap_drpr.jam_rawat,penjab.png_jawab,ifnull((select bangsal.nm_bangsal from kamar_inap "+
                                   "inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_drpr.no_rawat limit 1 ),'Ruang Terhapus' ) as nm_bangsal , " +
                                   "rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,"+
                                   "rawat_inap_drpr.kso,rawat_inap_drpr.menejemen,rawat_inap_drpr.biaya_rawat "+
                                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                                   "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                   "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join petugas on rawat_inap_drpr.nip=petugas.nip  "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and rawat_inap_drpr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and jns_perawatan_inap.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and rawat_inap_drpr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and rawat_inap_drpr.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like '%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%' and concat(rawat_inap_drpr.nip,petugas.nama) like '%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%'  "+
                                   " order by rawat_inap_drpr.no_rawat desc",param);
                        }   
                    }
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
                    if(KdDokterPerujukRad.getText().equals("")&&NmDokterPerujukRad.getText().equals("")&&KdPetugasRad.getText().equals("")&&NmPetugasRad.getText().equals("")&&KdCaraBayarRad.getText().equals("")&&NmCaraBayarRad.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailRadiologi.jasper","report","::[ Detail Pemeriksaan Radiologi ]::",
                               "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                               "periksa_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                               "periksa_radiologi.kd_dokter,dokter.nm_dokter,periksa_radiologi.nip,"+
                               "petugas.nama,periksa_radiologi.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                               "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,penjab.png_jawab,"+
                               "periksa_radiologi.bagian_rs,periksa_radiologi.bhp,periksa_radiologi.tarif_perujuk,"+
                               "periksa_radiologi.tarif_tindakan_dokter,periksa_radiologi.tarif_tindakan_petugas,"+
                               "periksa_radiologi.kso,periksa_radiologi.menejemen,periksa_radiologi.biaya,"+
                               "if(periksa_radiologi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                               "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                               "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_radiologi.no_rawat limit 1 )) as ruangan "+
                               "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                               "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                               "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                               "inner join dokter as perujuk on periksa_radiologi.dokter_perujuk=perujuk.kd_dokter "+
                               "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                               "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                               "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                               "where periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by periksa_radiologi.tgl_periksa",param);
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailRadiologi.jasper","report","::[ Detail Pemeriksaan Radiologi ]::",
                                   "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                                   "periksa_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                                   "periksa_radiologi.kd_dokter,dokter.nm_dokter,periksa_radiologi.nip,"+
                                   "petugas.nama,periksa_radiologi.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                                   "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,penjab.png_jawab,"+
                                   "periksa_radiologi.bagian_rs,periksa_radiologi.bhp,periksa_radiologi.tarif_perujuk,"+
                                   "periksa_radiologi.tarif_tindakan_dokter,periksa_radiologi.tarif_tindakan_petugas,"+
                                   "periksa_radiologi.kso,periksa_radiologi.menejemen,periksa_radiologi.biaya,"+
                                   "if(periksa_radiologi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_radiologi.no_rawat limit 1 )) as ruangan "+
                                   "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                                   "inner join dokter as perujuk on periksa_radiologi.dokter_perujuk=perujuk.kd_dokter "+
                                   "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                                   "where periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and jns_perawatan_radiologi.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.dokter_perujuk like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and perujuk.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by periksa_radiologi.tgl_periksa",param);
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailRadiologi.jasper","report","::[ Detail Pemeriksaan Radiologi ]::",
                                   "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                                   "periksa_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                                   "periksa_radiologi.kd_dokter,dokter.nm_dokter,periksa_radiologi.nip,"+
                                   "petugas.nama,periksa_radiologi.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                                   "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,penjab.png_jawab,"+
                                   "periksa_radiologi.bagian_rs,periksa_radiologi.bhp,periksa_radiologi.tarif_perujuk,"+
                                   "periksa_radiologi.tarif_tindakan_dokter,periksa_radiologi.tarif_tindakan_petugas,"+
                                   "periksa_radiologi.kso,periksa_radiologi.menejemen,periksa_radiologi.biaya,"+
                                   "if(periksa_radiologi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_radiologi.no_rawat limit 1 )) as ruangan "+
                                   "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                                   "inner join dokter as perujuk on periksa_radiologi.dokter_perujuk=perujuk.kd_dokter "+
                                   "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                                   "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and jns_perawatan_radiologi.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.dokter_perujuk like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and perujuk.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by periksa_radiologi.tgl_periksa",param);
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailRadiologi.jasper","report","::[ Detail Pemeriksaan Radiologi ]::",
                                   "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                                   "periksa_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                                   "periksa_radiologi.kd_dokter,dokter.nm_dokter,periksa_radiologi.nip,"+
                                   "petugas.nama,periksa_radiologi.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                                   "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,penjab.png_jawab,"+
                                   "periksa_radiologi.bagian_rs,periksa_radiologi.bhp,periksa_radiologi.tarif_perujuk,"+
                                   "periksa_radiologi.tarif_tindakan_dokter,periksa_radiologi.tarif_tindakan_petugas,"+
                                   "periksa_radiologi.kso,periksa_radiologi.menejemen,periksa_radiologi.biaya,"+
                                   "if(periksa_radiologi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_radiologi.no_rawat limit 1 )) as ruangan "+
                                   "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                                   "inner join dokter as perujuk on periksa_radiologi.dokter_perujuk=perujuk.kd_dokter "+
                                   "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and jns_perawatan_radiologi.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and periksa_radiologi.dokter_perujuk like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and perujuk.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%' and concat(periksa_radiologi.nip,petugas.nama) like '%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by periksa_radiologi.tgl_periksa",param);
                        }
                    } 
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
                    if(KdDokterPerujukLab.getText().equals("")&&NmDokterPerujukLab.getText().equals("")&&KdPetugasLab.getText().equals("")&&NmPetugasLab.getText().equals("")&&KdCaraBayarLab.getText().equals("")&&NmCaraBayarLab.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailLaborat.jasper","report","::[ Detail Pemeriksaan Laborat ]::",
                               "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                               "periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan, "+
                               "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                               "petugas.nama,periksa_lab.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                               "periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                               "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                               "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas,"+
                               "periksa_lab.kso,periksa_lab.menejemen,periksa_lab.biaya,"+
                               "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                               "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                               "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan "+
                               "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                               "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                               "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                               "inner join dokter as perujuk on periksa_lab.dokter_perujuk=perujuk.kd_dokter "+
                               "inner join petugas on periksa_lab.nip=petugas.nip "+
                               "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                               "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                               "where periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by periksa_lab.tgl_periksa",param);
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailLaborat.jasper","report","::[ Detail Pemeriksaan Laborat ]::",
                                   "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                                   "periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan, "+
                                   "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                                   "petugas.nama,periksa_lab.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                                   "periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                                   "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                                   "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas,"+
                                   "periksa_lab.kso,periksa_lab.menejemen,periksa_lab.biaya,"+
                                   "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan "+
                                   "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                                   "inner join dokter as perujuk on periksa_lab.dokter_perujuk=perujuk.kd_dokter "+
                                   "inner join petugas on periksa_lab.nip=petugas.nip "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                                   "where periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and jns_perawatan_lab.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.dokter_perujuk like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and perujuk.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by periksa_lab.tgl_periksa",param);
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailLaborat.jasper","report","::[ Detail Pemeriksaan Laborat ]::",
                                   "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                                   "periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan, "+
                                   "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                                   "petugas.nama,periksa_lab.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                                   "periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                                   "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                                   "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas,"+
                                   "periksa_lab.kso,periksa_lab.menejemen,periksa_lab.biaya,"+
                                   "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan "+
                                   "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                                   "inner join dokter as perujuk on periksa_lab.dokter_perujuk=perujuk.kd_dokter "+
                                   "inner join petugas on periksa_lab.nip=petugas.nip "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                                   "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and jns_perawatan_lab.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.dokter_perujuk like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and perujuk.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by periksa_lab.tgl_periksa",param);
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailLaborat.jasper","report","::[ Detail Pemeriksaan Laborat ]::",
                                   "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                                   "periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan, "+
                                   "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                                   "petugas.nama,periksa_lab.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                                   "periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                                   "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                                   "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas,"+
                                   "periksa_lab.kso,periksa_lab.menejemen,periksa_lab.biaya,"+
                                   "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                   "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                   "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan "+
                                   "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                   "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                                   "inner join dokter as perujuk on periksa_lab.dokter_perujuk=perujuk.kd_dokter "+
                                   "inner join petugas on periksa_lab.nip=petugas.nip "+
                                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                                   "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                                   "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and jns_perawatan_lab.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.nip like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and periksa_lab.dokter_perujuk like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and perujuk.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                   "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                   " order by periksa_lab.tgl_periksa",param);
                        }   
                    }
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
                    if(KdDokterPerujukDetailLab.getText().equals("")&&NmDokterPerujukDetailLab.getText().equals("")&&KdPetugasDetailLab.getText().equals("")&&NmPetugasDetailLab.getText().equals("")&&KdCaraBayarDetailLab.getText().equals("")&&NmCaraBayarDetailLab.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                        Valid.MyReportqry("rptDetailDetailLaborat.jasper","report","::[ Detail Detail Pemeriksaan Laborat ]::",
                                "select detail_periksa_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                                "pasien.nm_pasien,detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                                "petugas.nama,periksa_lab.dokter_perujuk,penjab.png_jawab,perujuk.nm_dokter as perujuk,"+
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
                                "inner join dokter inner join petugas inner join penjab inner join dokter as perujuk "+
                                "on detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                                "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and perujuk.kd_dokter=periksa_lab.dokter_perujuk "+
                                "and reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.kd_dokter=dokter.kd_dokter "+
                                "and periksa_lab.nip=petugas.nip and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                                "and periksa_lab.jam=detail_periksa_lab.jam and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                "and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                "where detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by detail_periksa_lab.tgl_periksa",param);   
                    }else{
                        if(cmbStatus.getSelectedItem().equals("Semua")){
                            Valid.MyReportqry("rptDetailDetailLaborat.jasper","report","::[ Detail Detail Pemeriksaan Laborat ]::",
                                    "select detail_periksa_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                                    "pasien.nm_pasien,detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                    "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                                    "petugas.nama,periksa_lab.dokter_perujuk,penjab.png_jawab,perujuk.nm_dokter as perujuk,"+
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
                                    "inner join dokter inner join petugas inner join penjab inner join dokter as perujuk "+
                                    "on detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                                    "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and perujuk.kd_dokter=periksa_lab.dokter_perujuk "+
                                    "and reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.kd_dokter=dokter.kd_dokter "+
                                    "and periksa_lab.nip=petugas.nip and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                                    "and periksa_lab.jam=detail_periksa_lab.jam and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                    "where detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and detail_periksa_lab.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and detail_periksa_lab.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and jns_perawatan_lab.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and periksa_lab.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and periksa_lab.nip like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and periksa_lab.dokter_perujuk like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and perujuk.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    "detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                    " order by detail_periksa_lab.tgl_periksa",param);   
                        }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                            Valid.MyReportqry("rptDetailDetailLaborat.jasper","report","::[ Detail Detail Pemeriksaan Laborat ]::",
                                    "select detail_periksa_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                                    "pasien.nm_pasien,detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                    "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                                    "petugas.nama,periksa_lab.dokter_perujuk,penjab.png_jawab,perujuk.nm_dokter as perujuk,"+
                                    "detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,"+
                                    "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                                    "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                    "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan,"+
                                    "detail_periksa_lab.bagian_rs,detail_periksa_lab.bhp,detail_periksa_lab.bagian_perujuk,"+
                                    "detail_periksa_lab.bagian_dokter,detail_periksa_lab.bagian_laborat,"+
                                    "detail_periksa_lab.kso,detail_periksa_lab.menejemen,"+
                                    "detail_periksa_lab.biaya_item from detail_periksa_lab "+
                                    "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                                    "inner join jns_perawatan_lab inner join periksa_lab inner join piutang_pasien "+
                                    "inner join dokter inner join petugas inner join penjab inner join dokter as perujuk "+
                                    "on detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                                    "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and perujuk.kd_dokter=periksa_lab.dokter_perujuk "+
                                    "and reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.kd_dokter=dokter.kd_dokter "+
                                    "and periksa_lab.nip=petugas.nip and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                                    "and periksa_lab.jam=detail_periksa_lab.jam and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                                    "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and detail_periksa_lab.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and detail_periksa_lab.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and jns_perawatan_lab.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and periksa_lab.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and periksa_lab.nip like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and periksa_lab.dokter_perujuk like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and perujuk.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                    " order by detail_periksa_lab.tgl_periksa",param);   
                        }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                            Valid.MyReportqry("rptDetailDetailLaborat.jasper","report","::[ Detail Detail Pemeriksaan Laborat ]::",
                                    "select detail_periksa_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                                    "pasien.nm_pasien,detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan,"+
                                    "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                                    "petugas.nama,periksa_lab.dokter_perujuk,penjab.png_jawab,perujuk.nm_dokter as perujuk,"+
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
                                    "inner join dokter inner join petugas inner join penjab inner join dokter as perujuk "+
                                    "on detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                                    "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                                    "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and perujuk.kd_dokter=periksa_lab.dokter_perujuk "+
                                    "and reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.kd_dokter=dokter.kd_dokter "+
                                    "and periksa_lab.nip=petugas.nip and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                                    "and periksa_lab.jam=detail_periksa_lab.jam and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                                    "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and detail_periksa_lab.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and detail_periksa_lab.kd_jenis_prw like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and jns_perawatan_lab.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and periksa_lab.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and periksa_lab.nip like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and periksa_lab.dokter_perujuk like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and perujuk.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                                    "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like '%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%' and concat(periksa_lab.nip,petugas.nama) like '%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%' and concat(reg_periksa.kd_pj,penjab.png_jawab) like '%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' "+
                                    " order by detail_periksa_lab.tgl_periksa",param);   
                        }   
                    }
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
        cmbStatus.setSelectedIndex(0);
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                KdDokterRalanDokter.setText("");
                NmDokterRalanDokter.setText("");
                KdPoliRalanDokter.setText("");
                NmPoliRalanDokter.setText("");
                KdCaraBayarRalanDokter.setText("");
                NmCaraBayarRalanDokter.setText("");
                break;
            case 1:
                KdPetugasRalanParamedis.setText("");
                NmPetugasRalanParamedis.setText("");
                KdPoliRalanParamedis.setText("");
                NmPoliRalanParamedis.setText("");
                KdCaraBayarRalanParamedis.setText("");
                NmCaraBayarRalanParamedis.setText("");
                break;
            case 2:
                KdDokterRalanDokterParamedis.setText("");
                NmDokterRalanDokterParamedis.setText("");
                KdPetugasRalanDokterParamedis.setText("");
                NmPetugasRalanDokterParamedis.setText("");
                KdPoliRalanDokterParamedis.setText("");
                NmPoliRalanDokterParamedis.setText("");
                KdCaraBayarRalanDokterParamedis.setText("");
                NmCaraBayarRalanDokterParamedis.setText("");
                break;
            case 3:
                KdOperatorOperasi.setText("");
                NmOperatorOperasi.setText("");
                KdAsistenOperasi.setText("");
                NmAsistenOperasi.setText("");
                KdCaraBayarOperasi.setText("");
                NmCaraBayarOperasi.setText("");
                break;
            case 4:
                KdDokterRanapDokter.setText("");
                NmDokterRanapDokter.setText("");
                KdCaraBayarRanapDokter.setText("");
                NmCaraBayarRanapDokter.setText("");
                break;
            case 5:
                KdPetugasRanapParamedis.setText("");
                NmPetugasRanapParamedis.setText("");
                KdCaraBayarRanapParamedis.setText("");
                NmCaraBayarRanapParamedis.setText("");
                break;
            case 6:
                KdPetugasRanapDokterParamedis.setText("");
                NmPetugasRanapDokterParamedis.setText("");
                KdDokterRanapDokterParamedis.setText("");
                NmDokterRanapDokterParamedis.setText("");
                KdCaraBayarRanapDokterParamedis.setText("");
                NmCaraBayarRanapDokterParamedis.setText("");
                break;
           case 7:
                KdPetugasRad.setText("");
                NmPetugasRad.setText("");
                KdDokterPerujukRad.setText("");
                NmDokterPerujukRad.setText("");
                KdCaraBayarRad.setText("");
                NmCaraBayarRad.setText("");
                break;
            case 8:
                KdPetugasLab.setText("");
                NmPetugasLab.setText("");
                KdDokterPerujukLab.setText("");
                NmDokterPerujukLab.setText("");
                KdCaraBayarLab.setText("");
                NmCaraBayarLab.setText("");
                break;
            case 9:
                KdPetugasDetailLab.setText("");
                NmPetugasDetailLab.setText("");
                KdDokterPerujukDetailLab.setText("");
                NmDokterPerujukDetailLab.setText("");
                KdCaraBayarDetailLab.setText("");
                NmCaraBayarDetailLab.setText("");
                break;
            default:
                break;
        }
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

    private void BtnDokterRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterRalanDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterRalanDokterActionPerformed

    private void BtnCaraBayarRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRalanDokterActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnCaraBayarRalanDokterActionPerformed

    private void BtnPoliRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliRalanDokterActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnPoliRalanDokterActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void ChkInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput1ActionPerformed
        isForm2();
    }//GEN-LAST:event_ChkInput1ActionPerformed

    private void BtnPetugasRalanParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasRalanParamedisActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasRalanParamedisActionPerformed

    private void BtnCaraBayarRalanParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRalanParamedisActionPerformed
        BtnCaraBayarRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnCaraBayarRalanParamedisActionPerformed

    private void BtnPoliRalanParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliRalanParamedisActionPerformed
        BtnPoliRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnPoliRalanParamedisActionPerformed

    private void ChkInput2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput2ActionPerformed
        isForm3();
    }//GEN-LAST:event_ChkInput2ActionPerformed

    private void BtnPetugasRalanDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasRalanDokterParamedisActionPerformed
        BtnPetugasRalanParamedisActionPerformed(null);
    }//GEN-LAST:event_BtnPetugasRalanDokterParamedisActionPerformed

    private void BtnCaraBayarRalanDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRalanDokterParamedisActionPerformed
        BtnCaraBayarRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnCaraBayarRalanDokterParamedisActionPerformed

    private void BtnPoliRalanDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliRalanDokterParamedisActionPerformed
        BtnPoliRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnPoliRalanDokterParamedisActionPerformed

    private void BtnDokterRalanDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterRalanDokterParamedisActionPerformed
        BtnDokterRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnDokterRalanDokterParamedisActionPerformed

    private void ChkInput3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput3ActionPerformed
        isForm4();
    }//GEN-LAST:event_ChkInput3ActionPerformed

    private void BtnDokterRanapDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterRanapDokterActionPerformed
        BtnDokterRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnDokterRanapDokterActionPerformed

    private void BtnCaraBayarRanapDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRanapDokterActionPerformed
        BtnCaraBayarRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnCaraBayarRanapDokterActionPerformed

    private void ChkInput4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput4ActionPerformed
        isForm5();
    }//GEN-LAST:event_ChkInput4ActionPerformed

    private void BtnPetugasRanapParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasRanapParamedisActionPerformed
        BtnPetugasRalanParamedisActionPerformed(null);
    }//GEN-LAST:event_BtnPetugasRanapParamedisActionPerformed

    private void BtnCaraBayarRanapParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRanapParamedisActionPerformed
        BtnCaraBayarRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnCaraBayarRanapParamedisActionPerformed

    private void ChkInput5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput5ActionPerformed
        isForm6();
    }//GEN-LAST:event_ChkInput5ActionPerformed

    private void BtnPetugasRanapDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasRanapDokterParamedisActionPerformed
        BtnPetugasRalanParamedisActionPerformed(null);
    }//GEN-LAST:event_BtnPetugasRanapDokterParamedisActionPerformed

    private void BtnCaraBayarRanapDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRanapDokterParamedisActionPerformed
        BtnCaraBayarRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnCaraBayarRanapDokterParamedisActionPerformed

    private void BtnDokterRanapDokterParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterRanapDokterParamedisActionPerformed
        BtnDokterRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnDokterRanapDokterParamedisActionPerformed

    private void ChkInput7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput7ActionPerformed
        isForm7();
    }//GEN-LAST:event_ChkInput7ActionPerformed

    private void BtnPetugasRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasRadActionPerformed
        BtnPetugasRalanParamedisActionPerformed(null);
    }//GEN-LAST:event_BtnPetugasRadActionPerformed

    private void BtnCaraBayarRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRadActionPerformed
        BtnCaraBayarRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnCaraBayarRadActionPerformed

    private void BtnDokterPerujukRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterPerujukRadActionPerformed
        BtnDokterRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnDokterPerujukRadActionPerformed

    private void ChkInput8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput8ActionPerformed
        isForm8();
    }//GEN-LAST:event_ChkInput8ActionPerformed

    private void BtnPetugasLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasLabActionPerformed
        BtnPetugasRalanParamedisActionPerformed(null);
    }//GEN-LAST:event_BtnPetugasLabActionPerformed

    private void BtnCaraBayarLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarLabActionPerformed
        BtnCaraBayarRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnCaraBayarLabActionPerformed

    private void BtnDokterPerujukLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterPerujukLabActionPerformed
        BtnDokterRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnDokterPerujukLabActionPerformed

    private void ChkInput9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput9ActionPerformed
        isForm9();
    }//GEN-LAST:event_ChkInput9ActionPerformed

    private void BtnPetugasDetailLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasDetailLabActionPerformed
        BtnPetugasRalanParamedisActionPerformed(null);
    }//GEN-LAST:event_BtnPetugasDetailLabActionPerformed

    private void BtnCaraBayarDetailLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarDetailLabActionPerformed
        BtnCaraBayarRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnCaraBayarDetailLabActionPerformed

    private void BtnDokterPerujukDetailLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterPerujukDetailLabActionPerformed
        BtnDokterRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnDokterPerujukDetailLabActionPerformed

    private void ChkInput10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput10ActionPerformed
        isForm10();
    }//GEN-LAST:event_ChkInput10ActionPerformed

    private void BtnAsistenOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsistenOperasiActionPerformed
        BtnPetugasRalanParamedisActionPerformed(null);
    }//GEN-LAST:event_BtnAsistenOperasiActionPerformed

    private void BtnCaraBayarOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarOperasiActionPerformed
        BtnCaraBayarRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnCaraBayarOperasiActionPerformed

    private void BtnOperatorOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperatorOperasiActionPerformed
        BtnDokterRalanDokterActionPerformed(null);
    }//GEN-LAST:event_BtnOperatorOperasiActionPerformed

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
    private widget.Button BtnAsistenOperasi;
    private widget.Button BtnCaraBayarDetailLab;
    private widget.Button BtnCaraBayarLab;
    private widget.Button BtnCaraBayarOperasi;
    private widget.Button BtnCaraBayarRad;
    private widget.Button BtnCaraBayarRalanDokter;
    private widget.Button BtnCaraBayarRalanDokterParamedis;
    private widget.Button BtnCaraBayarRalanParamedis;
    private widget.Button BtnCaraBayarRanapDokter;
    private widget.Button BtnCaraBayarRanapDokterParamedis;
    private widget.Button BtnCaraBayarRanapParamedis;
    private widget.Button BtnCari;
    private widget.Button BtnDokterPerujukDetailLab;
    private widget.Button BtnDokterPerujukLab;
    private widget.Button BtnDokterPerujukRad;
    private widget.Button BtnDokterRalanDokter;
    private widget.Button BtnDokterRalanDokterParamedis;
    private widget.Button BtnDokterRanapDokter;
    private widget.Button BtnDokterRanapDokterParamedis;
    private widget.Button BtnKeluar;
    private widget.Button BtnOperatorOperasi;
    private widget.Button BtnPetugasDetailLab;
    private widget.Button BtnPetugasLab;
    private widget.Button BtnPetugasRad;
    private widget.Button BtnPetugasRalanDokterParamedis;
    private widget.Button BtnPetugasRalanParamedis;
    private widget.Button BtnPetugasRanapDokterParamedis;
    private widget.Button BtnPetugasRanapParamedis;
    private widget.Button BtnPoliRalanDokter;
    private widget.Button BtnPoliRalanDokterParamedis;
    private widget.Button BtnPoliRalanParamedis;
    private widget.Button BtnPrint;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkInput1;
    private widget.CekBox ChkInput10;
    private widget.CekBox ChkInput2;
    private widget.CekBox ChkInput3;
    private widget.CekBox ChkInput4;
    private widget.CekBox ChkInput5;
    private widget.CekBox ChkInput7;
    private widget.CekBox ChkInput8;
    private widget.CekBox ChkInput9;
    private widget.panelisi FormInput;
    private widget.panelisi FormInput1;
    private widget.panelisi FormInput10;
    private widget.panelisi FormInput2;
    private widget.panelisi FormInput3;
    private widget.panelisi FormInput4;
    private widget.panelisi FormInput5;
    private widget.panelisi FormInput7;
    private widget.panelisi FormInput8;
    private widget.panelisi FormInput9;
    private widget.TextBox KdAsistenOperasi;
    private widget.TextBox KdCaraBayarDetailLab;
    private widget.TextBox KdCaraBayarLab;
    private widget.TextBox KdCaraBayarOperasi;
    private widget.TextBox KdCaraBayarRad;
    private widget.TextBox KdCaraBayarRalanDokter;
    private widget.TextBox KdCaraBayarRalanDokterParamedis;
    private widget.TextBox KdCaraBayarRalanParamedis;
    private widget.TextBox KdCaraBayarRanapDokter;
    private widget.TextBox KdCaraBayarRanapDokterParamedis;
    private widget.TextBox KdCaraBayarRanapParamedis;
    private widget.TextBox KdDokterPerujukDetailLab;
    private widget.TextBox KdDokterPerujukLab;
    private widget.TextBox KdDokterPerujukRad;
    private widget.TextBox KdDokterRalanDokter;
    private widget.TextBox KdDokterRalanDokterParamedis;
    private widget.TextBox KdDokterRanapDokter;
    private widget.TextBox KdDokterRanapDokterParamedis;
    private widget.TextBox KdOperatorOperasi;
    private widget.TextBox KdPetugasDetailLab;
    private widget.TextBox KdPetugasLab;
    private widget.TextBox KdPetugasRad;
    private widget.TextBox KdPetugasRalanDokterParamedis;
    private widget.TextBox KdPetugasRalanParamedis;
    private widget.TextBox KdPetugasRanapDokterParamedis;
    private widget.TextBox KdPetugasRanapParamedis;
    private widget.TextBox KdPoliRalanDokter;
    private widget.TextBox KdPoliRalanDokterParamedis;
    private widget.TextBox KdPoliRalanParamedis;
    private widget.TextBox NmAsistenOperasi;
    private widget.TextBox NmCaraBayarDetailLab;
    private widget.TextBox NmCaraBayarLab;
    private widget.TextBox NmCaraBayarOperasi;
    private widget.TextBox NmCaraBayarRad;
    private widget.TextBox NmCaraBayarRalanDokter;
    private widget.TextBox NmCaraBayarRalanDokterParamedis;
    private widget.TextBox NmCaraBayarRalanParamedis;
    private widget.TextBox NmCaraBayarRanapDokter;
    private widget.TextBox NmCaraBayarRanapDokterParamedis;
    private widget.TextBox NmCaraBayarRanapParamedis;
    private widget.TextBox NmDokterPerujukDetailLab;
    private widget.TextBox NmDokterPerujukLab;
    private widget.TextBox NmDokterPerujukRad;
    private widget.TextBox NmDokterRalanDokter;
    private widget.TextBox NmDokterRalanDokterParamedis;
    private widget.TextBox NmDokterRanapDokter;
    private widget.TextBox NmDokterRanapDokterParamedis;
    private widget.TextBox NmOperatorOperasi;
    private widget.TextBox NmPetugasDetailLab;
    private widget.TextBox NmPetugasLab;
    private widget.TextBox NmPetugasRad;
    private widget.TextBox NmPetugasRalanDokterParamedis;
    private widget.TextBox NmPetugasRalanParamedis;
    private widget.TextBox NmPetugasRanapDokterParamedis;
    private widget.TextBox NmPetugasRanapParamedis;
    private widget.TextBox NmPoliRalanDokter;
    private widget.TextBox NmPoliRalanDokterParamedis;
    private widget.TextBox NmPoliRalanParamedis;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPanel PanelInput1;
    private javax.swing.JPanel PanelInput10;
    private javax.swing.JPanel PanelInput2;
    private javax.swing.JPanel PanelInput3;
    private javax.swing.JPanel PanelInput4;
    private javax.swing.JPanel PanelInput5;
    private javax.swing.JPanel PanelInput7;
    private javax.swing.JPanel PanelInput8;
    private javax.swing.JPanel PanelInput9;
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
    private widget.ComboBox cmbStatus;
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
    private widget.Label jLabel12;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label35;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label42;
    private widget.Label label43;
    private widget.Label label44;
    private widget.Label label45;
    private widget.Label label46;
    private widget.Label label47;
    private widget.Label label48;
    private widget.Label label49;
    private widget.Label label50;
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
            if(KdDokterRalanDokter.getText().equals("")&&NmDokterRalanDokter.getText().equals("")&&KdPoliRalanDokter.getText().equals("")&&NmPoliRalanDokter.getText().equals("")&&KdCaraBayarRalanDokter.getText().equals("")&&NmCaraBayarRalanDokter.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                   "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                   "pasien.nm_pasien,rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                   "rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.tgl_perawatan,"+
                   "rawat_jl_dr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                   "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                   "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                   "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join rawat_jl_dr on reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                   "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                   "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                   "where rawat_jl_dr.tgl_perawatan between ? and ? order by rawat_jl_dr.no_rawat desc");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                        "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                        "pasien.nm_pasien,rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                        "rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.tgl_perawatan,"+
                        "rawat_jl_dr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                        "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                        "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                        "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join rawat_jl_dr on reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                        "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                        "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                        "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "where rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.no_rawat like ? or "+
                        "rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                        "rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                        "rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan.nm_perawatan like ? or "+
                        "rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.kd_dokter like ? or "+
                        "rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                        "rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                        "rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and poliklinik.nm_poli like ? "+
                       " order by rawat_jl_dr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                        "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                        "pasien.nm_pasien,rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                        "rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.tgl_perawatan,"+
                        "rawat_jl_dr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                        "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                        "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                        "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join rawat_jl_dr on reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                        "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                        "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                        "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.no_rawat like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan.nm_perawatan like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.kd_dokter like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and poliklinik.nm_poli like ? "+
                       " order by rawat_jl_dr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                        "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                        "pasien.nm_pasien,rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                        "rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.tgl_perawatan,"+
                        "rawat_jl_dr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                        "rawat_jl_dr.material,rawat_jl_dr.bhp,rawat_jl_dr.tarif_tindakandr,"+
                        "rawat_jl_dr.kso,rawat_jl_dr.menejemen,rawat_jl_dr.biaya_rawat "+
                        "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join rawat_jl_dr on reg_periksa.no_rawat=rawat_jl_dr.no_rawat "+
                        "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                        "inner join jns_perawatan on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                        "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                        "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                        "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.no_rawat like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan.nm_perawatan like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.kd_dokter like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                        "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_dr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and poliklinik.nm_poli like ? "+
                       " order by rawat_jl_dr.no_rawat desc");
                }
            }
            
            try {
                if(KdDokterRalanDokter.getText().equals("")&&NmDokterRalanDokter.getText().equals("")&&KdPoliRalanDokter.getText().equals("")&&NmPoliRalanDokter.getText().equals("")&&KdCaraBayarRalanDokter.getText().equals("")&&NmCaraBayarRalanDokter.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%");
                    ps.setString(4,"%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%");
                    ps.setString(5,"%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(9,"%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%");
                    ps.setString(10,"%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%");
                    ps.setString(11,"%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(15,"%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%");
                    ps.setString(16,"%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%");
                    ps.setString(17,"%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(21,"%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%");
                    ps.setString(22,"%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%");
                    ps.setString(23,"%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%");
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(27,"%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%");
                    ps.setString(28,"%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%");
                    ps.setString(29,"%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(33,"%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%");
                    ps.setString(34,"%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%");
                    ps.setString(35,"%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(39,"%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%");
                    ps.setString(40,"%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%");
                    ps.setString(41,"%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%");
                    ps.setString(42,"%"+TCari.getText().trim()+"%");
                    ps.setString(43,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(44,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(45,"%"+KdPoliRalanDokter.getText()+NmPoliRalanDokter.getText()+"%");
                    ps.setString(46,"%"+KdDokterRalanDokter.getText()+NmDokterRalanDokter.getText()+"%");
                    ps.setString(47,"%"+KdCaraBayarRalanDokter.getText()+NmCaraBayarRalanDokter.getText()+"%");
                    ps.setString(48,"%"+TCari.getText().trim()+"%");
                }
                    
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
            if(KdPetugasRalanParamedis.getText().equals("")&&NmPetugasRalanParamedis.getText().equals("")&&KdPoliRalanParamedis.getText().equals("")&&NmPoliRalanParamedis.getText().equals("")&&KdCaraBayarRalanParamedis.getText().equals("")&&NmCaraBayarRalanParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                       "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                       "pasien.nm_pasien,rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                       "rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.tgl_perawatan,"+
                       "rawat_jl_pr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                       "rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.tarif_tindakanpr,"+
                       "rawat_jl_pr.kso,rawat_jl_pr.menejemen,rawat_jl_pr.biaya_rawat "+
                       "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "inner join rawat_jl_pr on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                       "inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                       "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                       "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                       "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                       "where rawat_jl_pr.tgl_perawatan between ? and ? order by rawat_jl_pr.no_rawat desc");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                           "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                           "rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.tgl_perawatan,"+
                           "rawat_jl_pr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                           "rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.tarif_tindakanpr,"+
                           "rawat_jl_pr.kso,rawat_jl_pr.menejemen,rawat_jl_pr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_jl_pr on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "where rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_pr.no_rawat like ? or "+
                           "rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan.nm_perawatan like ? or "+
                           "rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_pr.nip like ? or "+
                           "rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                           "rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and poliklinik.nm_poli like ? "+
                           " order by rawat_jl_pr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                           "rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.tgl_perawatan,"+
                           "rawat_jl_pr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                           "rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.tarif_tindakanpr,"+
                           "rawat_jl_pr.kso,rawat_jl_pr.menejemen,rawat_jl_pr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_jl_pr on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_pr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_pr.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and poliklinik.nm_poli like ? "+
                           " order by rawat_jl_pr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                           "rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.tgl_perawatan,"+
                           "rawat_jl_pr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                           "rawat_jl_pr.material,rawat_jl_pr.bhp,rawat_jl_pr.tarif_tindakanpr,"+
                           "rawat_jl_pr.kso,rawat_jl_pr.menejemen,rawat_jl_pr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_jl_pr on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_pr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_pr.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_pr.tgl_perawatan between ? and ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and poliklinik.nm_poli like ? "+
                           " order by rawat_jl_pr.no_rawat desc");
                }
            }
                
            try {
                if(KdPetugasRalanParamedis.getText().equals("")&&NmPetugasRalanParamedis.getText().equals("")&&KdPoliRalanParamedis.getText().equals("")&&NmPoliRalanParamedis.getText().equals("")&&KdCaraBayarRalanParamedis.getText().equals("")&&NmCaraBayarRalanParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%");
                    ps.setString(4,"%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%");
                    ps.setString(5,"%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(9,"%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%");
                    ps.setString(10,"%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%");
                    ps.setString(11,"%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(15,"%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%");
                    ps.setString(16,"%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%");
                    ps.setString(17,"%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(21,"%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%");
                    ps.setString(22,"%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%");
                    ps.setString(23,"%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%");
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(27,"%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%");
                    ps.setString(28,"%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%");
                    ps.setString(29,"%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(33,"%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%");
                    ps.setString(34,"%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%");
                    ps.setString(35,"%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(39,"%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%");
                    ps.setString(40,"%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%");
                    ps.setString(41,"%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%");
                    ps.setString(42,"%"+TCari.getText().trim()+"%");
                    ps.setString(43,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(44,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(45,"%"+KdPoliRalanParamedis.getText()+NmPoliRalanParamedis.getText()+"%");
                    ps.setString(46,"%"+KdPetugasRalanParamedis.getText()+NmPetugasRalanParamedis.getText()+"%");
                    ps.setString(47,"%"+KdCaraBayarRalanParamedis.getText()+NmCaraBayarRalanParamedis.getText()+"%");
                    ps.setString(48,"%"+TCari.getText().trim()+"%");
                }
                    
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
            if(KdDokterRalanDokterParamedis.getText().equals("")&&NmDokterRalanDokterParamedis.getText().equals("")&&KdPetugasRalanDokterParamedis.getText().equals("")&&NmPetugasRalanDokterParamedis.getText().equals("")&&KdPoliRalanDokterParamedis.getText().equals("")&&NmPoliRalanDokterParamedis.getText().equals("")&&KdCaraBayarRalanDokterParamedis.getText().equals("")&&NmCaraBayarRalanDokterParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                       "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                       "pasien.nm_pasien,rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                       "rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,"+
                       "rawat_jl_drpr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                       "rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,"+
                       "rawat_jl_drpr.kso,rawat_jl_drpr.menejemen,rawat_jl_drpr.biaya_rawat "+
                       "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                       "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                       "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                       "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                       "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                       "inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                       "where rawat_jl_drpr.tgl_perawatan between ? and ? order by rawat_jl_drpr.no_rawat desc");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                           "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                           "rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,"+
                           "rawat_jl_drpr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                           "rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,"+
                           "rawat_jl_drpr.kso,rawat_jl_drpr.menejemen,rawat_jl_drpr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                           "where rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.no_rawat like ? or "+
                           "rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan.nm_perawatan like ? or "+
                           "rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.kd_dokter like ? or "+
                           "rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.nip like ? or "+
                           "rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                           "rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and poliklinik.nm_poli like ? "+
                           " order by rawat_jl_drpr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                           "rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,"+
                           "rawat_jl_drpr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                           "rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,"+
                           "rawat_jl_drpr.kso,rawat_jl_drpr.menejemen,rawat_jl_drpr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                           "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and poliklinik.nm_poli like ? "+
                           " order by rawat_jl_drpr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_jl_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,"+
                           "rawat_jl_drpr.kd_dokter,dokter.nm_dokter,rawat_jl_drpr.nip,petugas.nama,rawat_jl_drpr.tgl_perawatan,"+
                           "rawat_jl_drpr.jam_rawat,penjab.png_jawab,poliklinik.nm_poli, " +
                           "rawat_jl_drpr.material,rawat_jl_drpr.bhp,rawat_jl_drpr.tarif_tindakandr,rawat_jl_drpr.tarif_tindakanpr,"+
                           "rawat_jl_drpr.kso,rawat_jl_drpr.menejemen,rawat_jl_drpr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "inner join dokter on rawat_jl_drpr.kd_dokter=dokter.kd_dokter "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join petugas on rawat_jl_drpr.nip=petugas.nip "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_jl_drpr.tgl_perawatan between ? and ? and concat(rawat_jl_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_poli,poliklinik.nm_poli) like ? and concat(rawat_jl_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and poliklinik.nm_poli like ? "+
                           " order by rawat_jl_drpr.no_rawat desc");
                }
                    
            }
                
            try {
                if(KdDokterRalanDokterParamedis.getText().equals("")&&NmDokterRalanDokterParamedis.getText().equals("")&&KdPetugasRalanDokterParamedis.getText().equals("")&&NmPetugasRalanDokterParamedis.getText().equals("")&&KdPoliRalanDokterParamedis.getText().equals("")&&NmPoliRalanDokterParamedis.getText().equals("")&&KdCaraBayarRalanDokterParamedis.getText().equals("")&&NmCaraBayarRalanDokterParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(4,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(5,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(6,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(9,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(10,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(11,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(12,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(13,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(14,"%"+TCari.getText().trim()+"%");
                    ps.setString(15,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(16,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(17,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(18,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(19,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(20,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(21,"%"+TCari.getText().trim()+"%");
                    ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(24,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(25,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(26,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(27,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(28,"%"+TCari.getText().trim()+"%");
                    ps.setString(29,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(30,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(31,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(32,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(33,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(34,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(35,"%"+TCari.getText().trim()+"%");
                    ps.setString(36,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(37,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(38,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(39,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(40,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(41,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(42,"%"+TCari.getText().trim()+"%");
                    ps.setString(43,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(44,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(45,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(46,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(47,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(48,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(49,"%"+TCari.getText().trim()+"%");
                    ps.setString(50,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(51,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(52,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(53,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(54,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(55,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(56,"%"+TCari.getText().trim()+"%");
                    ps.setString(57,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(58,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(59,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(60,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(61,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(62,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(63,"%"+TCari.getText().trim()+"%");
                    ps.setString(64,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(65,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(66,"%"+KdDokterRalanDokterParamedis.getText()+NmDokterRalanDokterParamedis.getText()+"%");
                    ps.setString(67,"%"+KdPoliRalanDokterParamedis.getText()+NmPoliRalanDokterParamedis.getText()+"%");
                    ps.setString(68,"%"+KdPetugasRalanDokterParamedis.getText()+NmPetugasRalanDokterParamedis.getText()+"%");
                    ps.setString(69,"%"+KdCaraBayarRalanDokterParamedis.getText()+NmCaraBayarRalanDokterParamedis.getText()+"%");
                    ps.setString(70,"%"+TCari.getText().trim()+"%");
                }
                    
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
        try{
            if(KdOperatorOperasi.getText().equals("")&&NmOperatorOperasi.getText().equals("")&&KdAsistenOperasi.getText().equals("")&&NmAsistenOperasi.getText().equals("")&&KdCaraBayarOperasi.getText().equals("")&&NmCaraBayarOperasi.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                       "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                       "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                       "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                       "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                       "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                       "operator1.nm_dokter as operator1,operasi.biayaoperator1, "+
                       "operator2.nm_dokter as operator2,operasi.biayaoperator2, "+
                       "operator3.nm_dokter as operator3,operasi.biayaoperator3,"+
                       "asisten_operator1.nama as asisten_operator1,operasi.biayaasisten_operator1, "+
                       "asisten_operator2.nama as asisten_operator2,operasi.biayaasisten_operator2, "+
                       "asisten_operator3.nama as asisten_operator3,operasi.biayaasisten_operator3, "+
                       "instrumen.nama as instrumen,operasi.biayainstrumen, "+
                       "dokter_anak.nm_dokter as dokter_anak,operasi.biayadokter_anak, "+
                       "perawaat_resusitas.nama as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                       "dokter_anestesi.nm_dokter as dokter_anestesi,operasi.biayadokter_anestesi, "+
                       "asisten_anestesi.nama as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                       "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                       "bidan.nama as bidan,operasi.biayabidan, "+
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
                       "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                       "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                       "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                       "inner join dokter as operator1 on operator1.kd_dokter=operasi.operator1 "+
                       "inner join dokter as operator2 on operator2.kd_dokter=operasi.operator2 "+
                       "inner join dokter as operator3 on operator3.kd_dokter=operasi.operator3 "+
                       "inner join dokter as dokter_anak on dokter_anak.kd_dokter=operasi.dokter_anak "+
                       "inner join dokter as dokter_anestesi on dokter_anestesi.kd_dokter=operasi.dokter_anestesi "+
                       "inner join petugas as asisten_operator1 on asisten_operator1.nip=operasi.asisten_operator1 "+
                       "inner join petugas as asisten_operator2 on asisten_operator2.nip=operasi.asisten_operator2 "+
                       "inner join petugas as asisten_operator3 on asisten_operator3.nip=operasi.asisten_operator3 "+
                       "inner join petugas as asisten_anestesi on asisten_anestesi.nip=operasi.asisten_anestesi "+
                       "inner join petugas as bidan on bidan.nip=operasi.bidan "+
                       "inner join petugas as instrumen on instrumen.nip=operasi.instrumen "+
                       "inner join petugas as perawaat_resusitas on perawaat_resusitas.nip=operasi.perawaat_resusitas "+
                       "where operasi.tgl_operasi between ? and ? order by operasi.no_rawat desc");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                           "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                           "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                           "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                           "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                           "operator1.nm_dokter as operator1,operasi.biayaoperator1, "+
                           "operator2.nm_dokter as operator2,operasi.biayaoperator2, "+
                           "operator3.nm_dokter as operator3,operasi.biayaoperator3,"+
                           "asisten_operator1.nama as asisten_operator1,operasi.biayaasisten_operator1, "+
                           "asisten_operator2.nama as asisten_operator2,operasi.biayaasisten_operator2, "+
                           "asisten_operator3.nama as asisten_operator3,operasi.biayaasisten_operator3, "+
                           "instrumen.nama as instrumen,operasi.biayainstrumen, "+
                           "dokter_anak.nm_dokter as dokter_anak,operasi.biayadokter_anak, "+
                           "perawaat_resusitas.nama as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                           "dokter_anestesi.nm_dokter as dokter_anestesi,operasi.biayadokter_anestesi, "+
                           "asisten_anestesi.nama as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                           "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                           "bidan.nama as bidan,operasi.biayabidan, "+
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
                           "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                           "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join dokter as operator1 on operator1.kd_dokter=operasi.operator1 "+
                           "inner join dokter as operator2 on operator2.kd_dokter=operasi.operator2 "+
                           "inner join dokter as operator3 on operator3.kd_dokter=operasi.operator3 "+
                           "inner join dokter as dokter_anak on dokter_anak.kd_dokter=operasi.dokter_anak "+
                           "inner join dokter as dokter_anestesi on dokter_anestesi.kd_dokter=operasi.dokter_anestesi "+
                           "inner join petugas as asisten_operator1 on asisten_operator1.nip=operasi.asisten_operator1 "+
                           "inner join petugas as asisten_operator2 on asisten_operator2.nip=operasi.asisten_operator2 "+
                           "inner join petugas as asisten_operator3 on asisten_operator3.nip=operasi.asisten_operator3 "+
                           "inner join petugas as asisten_anestesi on asisten_anestesi.nip=operasi.asisten_anestesi "+
                           "inner join petugas as bidan on bidan.nip=operasi.bidan "+
                           "inner join petugas as instrumen on instrumen.nip=operasi.instrumen "+
                           "inner join petugas as perawaat_resusitas on perawaat_resusitas.nip=operasi.perawaat_resusitas "+
                           "where operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.no_rawat like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.kode_paket like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and paket_operasi.nm_perawatan like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operator1.nm_dokter like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operator2.nm_dokter like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operator3.nm_dokter like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter_anak.nm_dokter like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_operator1.nama like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_operator2.nama like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_operator3.nama like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and instrumen.nama like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perawaat_resusitas.nama like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_anestesi.nama like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and bidan.nama like ? or "+
                           "operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter_anestesi.nm_dokter like ? "+
                           "order by operasi.no_rawat desc");    
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                           "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                           "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                           "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                           "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                           "operator1.nm_dokter as operator1,operasi.biayaoperator1, "+
                           "operator2.nm_dokter as operator2,operasi.biayaoperator2, "+
                           "operator3.nm_dokter as operator3,operasi.biayaoperator3,"+
                           "asisten_operator1.nama as asisten_operator1,operasi.biayaasisten_operator1, "+
                           "asisten_operator2.nama as asisten_operator2,operasi.biayaasisten_operator2, "+
                           "asisten_operator3.nama as asisten_operator3,operasi.biayaasisten_operator3, "+
                           "instrumen.nama as instrumen,operasi.biayainstrumen, "+
                           "dokter_anak.nm_dokter as dokter_anak,operasi.biayadokter_anak, "+
                           "perawaat_resusitas.nama as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                           "dokter_anestesi.nm_dokter as dokter_anestesi,operasi.biayadokter_anestesi, "+
                           "asisten_anestesi.nama as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                           "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                           "bidan.nama as bidan,operasi.biayabidan, "+
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
                           "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                           "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join dokter as operator1 on operator1.kd_dokter=operasi.operator1 "+
                           "inner join dokter as operator2 on operator2.kd_dokter=operasi.operator2 "+
                           "inner join dokter as operator3 on operator3.kd_dokter=operasi.operator3 "+
                           "inner join dokter as dokter_anak on dokter_anak.kd_dokter=operasi.dokter_anak "+
                           "inner join dokter as dokter_anestesi on dokter_anestesi.kd_dokter=operasi.dokter_anestesi "+
                           "inner join petugas as asisten_operator1 on asisten_operator1.nip=operasi.asisten_operator1 "+
                           "inner join petugas as asisten_operator2 on asisten_operator2.nip=operasi.asisten_operator2 "+
                           "inner join petugas as asisten_operator3 on asisten_operator3.nip=operasi.asisten_operator3 "+
                           "inner join petugas as asisten_anestesi on asisten_anestesi.nip=operasi.asisten_anestesi "+
                           "inner join petugas as bidan on bidan.nip=operasi.bidan "+
                           "inner join petugas as instrumen on instrumen.nip=operasi.instrumen "+
                           "inner join petugas as perawaat_resusitas on perawaat_resusitas.nip=operasi.perawaat_resusitas "+
                           "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.kode_paket like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and paket_operasi.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operator1.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operator2.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operator3.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter_anak.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_operator1.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_operator2.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_operator3.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and instrumen.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perawaat_resusitas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_anestesi.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and bidan.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter_anestesi.nm_dokter like ? "+
                           "order by operasi.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                           "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                           "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                           "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                           "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                           "operator1.nm_dokter as operator1,operasi.biayaoperator1, "+
                           "operator2.nm_dokter as operator2,operasi.biayaoperator2, "+
                           "operator3.nm_dokter as operator3,operasi.biayaoperator3,"+
                           "asisten_operator1.nama as asisten_operator1,operasi.biayaasisten_operator1, "+
                           "asisten_operator2.nama as asisten_operator2,operasi.biayaasisten_operator2, "+
                           "asisten_operator3.nama as asisten_operator3,operasi.biayaasisten_operator3, "+
                           "instrumen.nama as instrumen,operasi.biayainstrumen, "+
                           "dokter_anak.nm_dokter as dokter_anak,operasi.biayadokter_anak, "+
                           "perawaat_resusitas.nama as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                           "dokter_anestesi.nm_dokter as dokter_anestesi,operasi.biayadokter_anestesi, "+
                           "asisten_anestesi.nama as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                           "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                           "bidan.nama as bidan,operasi.biayabidan, "+
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
                           "from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                           "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join paket_operasi on operasi.kode_paket=paket_operasi.kode_paket "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join dokter as operator1 on operator1.kd_dokter=operasi.operator1 "+
                           "inner join dokter as operator2 on operator2.kd_dokter=operasi.operator2 "+
                           "inner join dokter as operator3 on operator3.kd_dokter=operasi.operator3 "+
                           "inner join dokter as dokter_anak on dokter_anak.kd_dokter=operasi.dokter_anak "+
                           "inner join dokter as dokter_anestesi on dokter_anestesi.kd_dokter=operasi.dokter_anestesi "+
                           "inner join petugas as asisten_operator1 on asisten_operator1.nip=operasi.asisten_operator1 "+
                           "inner join petugas as asisten_operator2 on asisten_operator2.nip=operasi.asisten_operator2 "+
                           "inner join petugas as asisten_operator3 on asisten_operator3.nip=operasi.asisten_operator3 "+
                           "inner join petugas as asisten_anestesi on asisten_anestesi.nip=operasi.asisten_anestesi "+
                           "inner join petugas as bidan on bidan.nip=operasi.bidan "+
                           "inner join petugas as instrumen on instrumen.nip=operasi.instrumen "+
                           "inner join petugas as perawaat_resusitas on perawaat_resusitas.nip=operasi.perawaat_resusitas "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.kode_paket like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and paket_operasi.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operator1.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operator2.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operator3.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter_anak.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_operator1.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_operator2.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_operator3.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and instrumen.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perawaat_resusitas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and asisten_anestesi.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and bidan.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and operasi.tgl_operasi between ? and ? and concat(operasi.operator1,operator1.nm_dokter) like ? and concat(operasi.asisten_operator1,asisten_operator1.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter_anestesi.nm_dokter like ? "+
                           "order by operasi.no_rawat desc");
                }   
            }
                
            try {
                if(KdOperatorOperasi.getText().equals("")&&NmOperatorOperasi.getText().equals("")&&KdAsistenOperasi.getText().equals("")&&NmAsistenOperasi.getText().equals("")&&KdCaraBayarOperasi.getText().equals("")&&NmCaraBayarOperasi.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(4,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(5,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(9,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(10,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(11,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(15,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(16,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(17,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(21,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(22,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(23,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(27,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(28,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(29,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(33,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(34,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(35,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(39,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(40,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(41,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(42,"%"+TCari.getText().trim()+"%");
                    ps.setString(43,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(44,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(45,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(46,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(47,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(48,"%"+TCari.getText().trim()+"%");
                    ps.setString(49,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(50,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(51,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(52,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(53,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(54,"%"+TCari.getText().trim()+"%");
                    ps.setString(55,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(56,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(57,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(58,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(59,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(60,"%"+TCari.getText().trim()+"%");
                    ps.setString(61,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(62,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(63,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(64,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(65,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(66,"%"+TCari.getText().trim()+"%");
                    ps.setString(67,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(68,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(69,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(70,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(71,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(72,"%"+TCari.getText().trim()+"%");
                    ps.setString(73,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(74,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(75,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(76,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(77,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(78,"%"+TCari.getText().trim()+"%");
                    ps.setString(79,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(80,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(81,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(82,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(83,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(84,"%"+TCari.getText().trim()+"%");
                    ps.setString(85,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(86,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(87,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(88,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(89,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(90,"%"+TCari.getText().trim()+"%");
                    ps.setString(91,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(92,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(93,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(94,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(95,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(96,"%"+TCari.getText().trim()+"%");
                    ps.setString(97,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(98,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(99,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(100,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(101,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(102,"%"+TCari.getText().trim()+"%");
                    ps.setString(103,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(104,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(105,"%"+KdOperatorOperasi.getText()+NmOperatorOperasi.getText()+"%");
                    ps.setString(106,"%"+KdAsistenOperasi.getText()+NmAsistenOperasi.getText()+"%");
                    ps.setString(107,"%"+KdCaraBayarOperasi.getText()+NmCaraBayarOperasi.getText()+"%");
                    ps.setString(108,"%"+TCari.getText().trim()+"%");
                }
                    
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
            if(KdDokterRanapDokter.getText().equals("")&&NmDokterRanapDokter.getText().equals("")&&KdCaraBayarRanapDokter.getText().equals("")&&NmCaraBayarRanapDokter.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                       "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                       "pasien.nm_pasien,rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                       "rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.tgl_perawatan,"+
                       "rawat_inap_dr.jam_rawat,penjab.png_jawab,"+
                       "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                       "inner join kamar inner join bangsal on "+
                       "kamar_inap.kd_kamar=kamar.kd_kamar "+
                       "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_dr.no_rawat limit 1),'Ruang Terhapus' ) as ruang , " +
                       "rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.tarif_tindakandr,"+
                       "rawat_inap_dr.kso,rawat_inap_dr.menejemen,rawat_inap_dr.biaya_rawat "+
                       "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                       "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                       "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                       "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                       "where rawat_inap_dr.tgl_perawatan between ? and ? order by rawat_inap_dr.no_rawat desc");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                           "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                           "rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.tgl_perawatan,"+
                           "rawat_inap_dr.jam_rawat,penjab.png_jawab,"+
                           "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                           "inner join kamar inner join bangsal on "+
                           "kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_dr.no_rawat limit 1),'Ruang Terhapus' ) as ruang , " +
                           "rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.tarif_tindakandr,"+
                           "rawat_inap_dr.kso,rawat_inap_dr.menejemen,rawat_inap_dr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                           "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "where rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.no_rawat like ? or "+
                           "rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                           "rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.kd_dokter like ? or "+
                           "rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by rawat_inap_dr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                           "rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.tgl_perawatan,"+
                           "rawat_inap_dr.jam_rawat,penjab.png_jawab,"+
                           "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                           "inner join kamar inner join bangsal on "+
                           "kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_dr.no_rawat limit 1),'Ruang Terhapus' ) as ruang , " +
                           "rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.tarif_tindakandr,"+
                           "rawat_inap_dr.kso,rawat_inap_dr.menejemen,rawat_inap_dr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                           "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by rawat_inap_dr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_inap_dr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                           "rawat_inap_dr.kd_dokter,dokter.nm_dokter,rawat_inap_dr.tgl_perawatan,"+
                           "rawat_inap_dr.jam_rawat,penjab.png_jawab,"+
                           "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                           "inner join kamar inner join bangsal on "+
                           "kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_dr.no_rawat limit 1),'Ruang Terhapus' ) as ruang , " +
                           "rawat_inap_dr.material,rawat_inap_dr.bhp,rawat_inap_dr.tarif_tindakandr,"+
                           "rawat_inap_dr.kso,rawat_inap_dr.menejemen,rawat_inap_dr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_inap_dr on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan_inap on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                           "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_dr.tgl_perawatan between ? and ? and concat(rawat_inap_dr.kd_dokter,dokter.nm_dokter) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by rawat_inap_dr.no_rawat desc");
                }
            }
                
            try {
                if(KdDokterRanapDokter.getText().equals("")&&NmDokterRanapDokter.getText().equals("")&&KdCaraBayarRanapDokter.getText().equals("")&&NmCaraBayarRanapDokter.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%");
                    ps.setString(4,"%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(7,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(8,"%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%");
                    ps.setString(9,"%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(12,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(13,"%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%");
                    ps.setString(14,"%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%");
                    ps.setString(15,"%"+TCari.getText().trim()+"%");
                    ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(18,"%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%");
                    ps.setString(19,"%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%");
                    ps.setString(20,"%"+TCari.getText().trim()+"%");
                    ps.setString(21,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(22,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(23,"%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%");
                    ps.setString(24,"%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%");
                    ps.setString(25,"%"+TCari.getText().trim()+"%");
                    ps.setString(26,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(27,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(28,"%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%");
                    ps.setString(29,"%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(33,"%"+KdDokterRanapDokter.getText()+NmDokterRanapDokter.getText()+"%");
                    ps.setString(34,"%"+KdCaraBayarRanapDokter.getText()+NmCaraBayarRanapDokter.getText()+"%");
                    ps.setString(35,"%"+TCari.getText().trim()+"%");
                }
                    
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
            if(KdPetugasRanapParamedis.getText().equals("")&&NmPetugasRanapParamedis.getText().equals("")&&KdCaraBayarRanapParamedis.getText().equals("")&&NmCaraBayarRanapParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                       "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                       "pasien.nm_pasien,rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                       "rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.tgl_perawatan,"+
                       "rawat_inap_pr.jam_rawat,penjab.png_jawab,"+
                       "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                       "inner join kamar inner join bangsal on "+
                       "kamar_inap.kd_kamar=kamar.kd_kamar "+
                       "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_pr.no_rawat limit 1 ),'Ruang Terhapus' ) as ruang , " +
                       "rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.tarif_tindakanpr,"+
                       "rawat_inap_pr.kso,rawat_inap_pr.menejemen,rawat_inap_pr.biaya_rawat "+
                       "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                       "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                       "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                       "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                       "where rawat_inap_pr.tgl_perawatan between ? and ? order by rawat_inap_pr.no_rawat desc");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                           "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                           "rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.tgl_perawatan,"+
                           "rawat_inap_pr.jam_rawat,penjab.png_jawab,"+
                           "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                           "inner join kamar inner join bangsal on "+
                           "kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_pr.no_rawat limit 1 ),'Ruang Terhapus' ) as ruang , " +
                           "rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.tarif_tindakanpr,"+
                           "rawat_inap_pr.kso,rawat_inap_pr.menejemen,rawat_inap_pr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                           "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "where rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_pr.no_rawat like ? or "+
                           "rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                           "rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_pr.nip like ? or "+
                           "rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by rawat_inap_pr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                           "rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.tgl_perawatan,"+
                           "rawat_inap_pr.jam_rawat,penjab.png_jawab,"+
                           "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                           "inner join kamar inner join bangsal on "+
                           "kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_pr.no_rawat limit 1 ),'Ruang Terhapus' ) as ruang , " +
                           "rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.tarif_tindakanpr,"+
                           "rawat_inap_pr.kso,rawat_inap_pr.menejemen,rawat_inap_pr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                           "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_pr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_pr.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by rawat_inap_pr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_inap_pr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                           "rawat_inap_pr.nip,petugas.nama,rawat_inap_pr.tgl_perawatan,"+
                           "rawat_inap_pr.jam_rawat,penjab.png_jawab,"+
                           "ifnull((select bangsal.nm_bangsal from kamar_inap "+
                           "inner join kamar inner join bangsal on "+
                           "kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_pr.no_rawat limit 1 ),'Ruang Terhapus' ) as ruang , " +
                           "rawat_inap_pr.material,rawat_inap_pr.bhp,rawat_inap_pr.tarif_tindakanpr,"+
                           "rawat_inap_pr.kso,rawat_inap_pr.menejemen,rawat_inap_pr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_inap_pr on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                           "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_pr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_pr.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_pr.tgl_perawatan between ? and ? and concat(rawat_inap_pr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by rawat_inap_pr.no_rawat desc");
                }   
            }
                
            try {
                if(KdPetugasRanapParamedis.getText().equals("")&&NmPetugasRanapParamedis.getText().equals("")&&KdCaraBayarRanapParamedis.getText().equals("")&&NmCaraBayarRanapParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%");
                    ps.setString(4,"%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(7,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(8,"%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%");
                    ps.setString(9,"%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(12,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(13,"%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%");
                    ps.setString(14,"%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%");
                    ps.setString(15,"%"+TCari.getText().trim()+"%");
                    ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(18,"%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%");
                    ps.setString(19,"%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%");
                    ps.setString(20,"%"+TCari.getText().trim()+"%");
                    ps.setString(21,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(22,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(23,"%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%");
                    ps.setString(24,"%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%");
                    ps.setString(25,"%"+TCari.getText().trim()+"%");
                    ps.setString(26,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(27,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(28,"%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%");
                    ps.setString(29,"%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(33,"%"+KdPetugasRanapParamedis.getText()+NmPetugasRanapParamedis.getText()+"%");
                    ps.setString(34,"%"+KdCaraBayarRanapParamedis.getText()+NmCaraBayarRanapParamedis.getText()+"%");
                    ps.setString(35,"%"+TCari.getText().trim()+"%");
                }
                    
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
            if(KdDokterRanapDokterParamedis.getText().equals("")&&NmDokterRanapDokterParamedis.getText().equals("")&&KdPetugasRanapDokterParamedis.getText().equals("")&&NmPetugasRanapDokterParamedis.getText().equals("")&&KdCaraBayarRanapDokterParamedis.getText().equals("")&&NmCaraBayarRanapDokterParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                       "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                       "pasien.nm_pasien,rawat_inap_drpr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                       "rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,"+
                       "rawat_inap_drpr.jam_rawat,penjab.png_jawab,ifnull((select bangsal.nm_bangsal from kamar_inap "+
                       "inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                       "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_drpr.no_rawat limit 1 ),'Ruang Terhapus' ) as ruang , " +
                       "rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,"+
                       "rawat_inap_drpr.kso,rawat_inap_drpr.menejemen,rawat_inap_drpr.biaya_rawat "+
                       "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                       "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                       "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                       "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                       "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                       "inner join petugas on rawat_inap_drpr.nip=petugas.nip  "+
                       "where rawat_inap_drpr.tgl_perawatan between ? and ? order by rawat_inap_drpr.no_rawat desc");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                           "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_inap_drpr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                           "rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,"+
                           "rawat_inap_drpr.jam_rawat,penjab.png_jawab,ifnull((select bangsal.nm_bangsal from kamar_inap "+
                           "inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_drpr.no_rawat limit 1 ),'Ruang Terhapus' ) as ruang , " +
                           "rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,"+
                           "rawat_inap_drpr.kso,rawat_inap_drpr.menejemen,rawat_inap_drpr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                           "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join petugas on rawat_inap_drpr.nip=petugas.nip  "+
                           "where rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.no_rawat like ? or "+
                           "rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                           "rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.kd_dokter like ? or "+
                           "rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.nip like ? or "+
                           "rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ?  "+
                           " order by rawat_inap_drpr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_inap_drpr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                           "rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,"+
                           "rawat_inap_drpr.jam_rawat,penjab.png_jawab,ifnull((select bangsal.nm_bangsal from kamar_inap "+
                           "inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_drpr.no_rawat limit 1 ),'Ruang Terhapus' ) as ruang , " +
                           "rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,"+
                           "rawat_inap_drpr.kso,rawat_inap_drpr.menejemen,rawat_inap_drpr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                           "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join petugas on rawat_inap_drpr.nip=petugas.nip  "+
                           "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ?  "+
                           " order by rawat_inap_drpr.no_rawat desc");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                           "select rawat_inap_drpr.no_rawat,reg_periksa.no_rkm_medis,"+
                           "pasien.nm_pasien,rawat_inap_drpr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                           "rawat_inap_drpr.kd_dokter,dokter.nm_dokter,rawat_inap_drpr.nip,petugas.nama,rawat_inap_drpr.tgl_perawatan,"+
                           "rawat_inap_drpr.jam_rawat,penjab.png_jawab,ifnull((select bangsal.nm_bangsal from kamar_inap "+
                           "inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=rawat_inap_drpr.no_rawat limit 1 ),'Ruang Terhapus' ) as ruang , " +
                           "rawat_inap_drpr.material,rawat_inap_drpr.bhp,rawat_inap_drpr.tarif_tindakandr,rawat_inap_drpr.tarif_tindakanpr,"+
                           "rawat_inap_drpr.kso,rawat_inap_drpr.menejemen,rawat_inap_drpr.biaya_rawat "+
                           "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join rawat_inap_drpr on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                           "inner join jns_perawatan_inap on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                           "inner join dokter on rawat_inap_drpr.kd_dokter=dokter.kd_dokter "+
                           "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join petugas on rawat_inap_drpr.nip=petugas.nip  "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_inap.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and rawat_inap_drpr.tgl_perawatan between ? and ? and concat(rawat_inap_drpr.kd_dokter,dokter.nm_dokter) like ? and concat(rawat_inap_drpr.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ?  "+
                           " order by rawat_inap_drpr.no_rawat desc");
                }   
            }
                
            try {
                if(KdDokterRanapDokterParamedis.getText().equals("")&&NmDokterRanapDokterParamedis.getText().equals("")&&KdPetugasRanapDokterParamedis.getText().equals("")&&NmPetugasRanapDokterParamedis.getText().equals("")&&KdCaraBayarRanapDokterParamedis.getText().equals("")&&NmCaraBayarRanapDokterParamedis.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%");
                    ps.setString(4,"%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%");
                    ps.setString(5,"%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(9,"%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%");
                    ps.setString(10,"%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%");
                    ps.setString(11,"%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(15,"%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%");
                    ps.setString(16,"%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%");
                    ps.setString(17,"%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(21,"%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%");
                    ps.setString(22,"%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%");
                    ps.setString(23,"%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%");
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(27,"%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%");
                    ps.setString(28,"%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%");
                    ps.setString(29,"%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(33,"%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%");
                    ps.setString(34,"%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%");
                    ps.setString(35,"%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(39,"%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%");
                    ps.setString(40,"%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%");
                    ps.setString(41,"%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%");
                    ps.setString(42,"%"+TCari.getText().trim()+"%");
                    ps.setString(43,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(44,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(45,"%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%");
                    ps.setString(46,"%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%");
                    ps.setString(47,"%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%");
                    ps.setString(48,"%"+TCari.getText().trim()+"%");
                    ps.setString(49,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(50,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(51,"%"+KdDokterRanapDokterParamedis.getText()+NmDokterRanapDokterParamedis.getText()+"%");
                    ps.setString(52,"%"+KdPetugasRanapDokterParamedis.getText()+NmPetugasRanapDokterParamedis.getText()+"%");
                    ps.setString(53,"%"+KdCaraBayarRanapDokterParamedis.getText()+NmCaraBayarRanapDokterParamedis.getText()+"%");
                    ps.setString(54,"%"+TCari.getText().trim()+"%");
                }
                    
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
            if(KdDokterPerujukRad.getText().equals("")&&NmDokterPerujukRad.getText().equals("")&&KdPetugasRad.getText().equals("")&&NmPetugasRad.getText().equals("")&&KdCaraBayarRad.getText().equals("")&&NmCaraBayarRad.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                       "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                       "periksa_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                       "periksa_radiologi.kd_dokter,dokter.nm_dokter,periksa_radiologi.nip,"+
                       "petugas.nama,periksa_radiologi.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                       "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,penjab.png_jawab,"+
                       "periksa_radiologi.bagian_rs,periksa_radiologi.bhp,periksa_radiologi.tarif_perujuk,"+
                       "periksa_radiologi.tarif_tindakan_dokter,periksa_radiologi.tarif_tindakan_petugas,"+
                       "periksa_radiologi.kso,periksa_radiologi.menejemen,periksa_radiologi.biaya,"+
                       "if(periksa_radiologi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                       "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                       "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_radiologi.no_rawat limit 1 )) as ruangan "+
                       "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                       "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                       "inner join dokter as perujuk on periksa_radiologi.dokter_perujuk=perujuk.kd_dokter "+
                       "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                       "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                       "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                       "where periksa_radiologi.tgl_periksa between ? and ? order by periksa_radiologi.tgl_periksa");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                           "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                           "periksa_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                           "periksa_radiologi.kd_dokter,dokter.nm_dokter,periksa_radiologi.nip,"+
                           "petugas.nama,periksa_radiologi.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                           "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,penjab.png_jawab,"+
                           "periksa_radiologi.bagian_rs,periksa_radiologi.bhp,periksa_radiologi.tarif_perujuk,"+
                           "periksa_radiologi.tarif_tindakan_dokter,periksa_radiologi.tarif_tindakan_petugas,"+
                           "periksa_radiologi.kso,periksa_radiologi.menejemen,periksa_radiologi.biaya,"+
                           "if(periksa_radiologi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                           "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_radiologi.no_rawat limit 1 )) as ruangan "+
                           "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                           "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                           "inner join dokter as perujuk on periksa_radiologi.dokter_perujuk=perujuk.kd_dokter "+
                           "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                           "where periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.no_rawat like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.kd_jenis_prw like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_radiologi.nm_perawatan like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.kd_dokter like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.nip like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.dokter_perujuk like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perujuk.nm_dokter like ? or "+
                           "periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by periksa_radiologi.tgl_periksa");
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                           "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                           "periksa_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                           "periksa_radiologi.kd_dokter,dokter.nm_dokter,periksa_radiologi.nip,"+
                           "petugas.nama,periksa_radiologi.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                           "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,penjab.png_jawab,"+
                           "periksa_radiologi.bagian_rs,periksa_radiologi.bhp,periksa_radiologi.tarif_perujuk,"+
                           "periksa_radiologi.tarif_tindakan_dokter,periksa_radiologi.tarif_tindakan_petugas,"+
                           "periksa_radiologi.kso,periksa_radiologi.menejemen,periksa_radiologi.biaya,"+
                           "if(periksa_radiologi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                           "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_radiologi.no_rawat limit 1 )) as ruangan "+
                           "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                           "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                           "inner join dokter as perujuk on periksa_radiologi.dokter_perujuk=perujuk.kd_dokter "+
                           "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                           "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.kd_jenis_prw like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_radiologi.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.dokter_perujuk like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perujuk.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by periksa_radiologi.tgl_periksa");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                           "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                           "periksa_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan, "+
                           "periksa_radiologi.kd_dokter,dokter.nm_dokter,periksa_radiologi.nip,"+
                           "petugas.nama,periksa_radiologi.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                           "periksa_radiologi.tgl_periksa,periksa_radiologi.jam,penjab.png_jawab,"+
                           "periksa_radiologi.bagian_rs,periksa_radiologi.bhp,periksa_radiologi.tarif_perujuk,"+
                           "periksa_radiologi.tarif_tindakan_dokter,periksa_radiologi.tarif_tindakan_petugas,"+
                           "periksa_radiologi.kso,periksa_radiologi.menejemen,periksa_radiologi.biaya,"+
                           "if(periksa_radiologi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                           "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_radiologi.no_rawat limit 1 )) as ruangan "+
                           "from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                           "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                           "inner join dokter as perujuk on periksa_radiologi.dokter_perujuk=perujuk.kd_dokter "+
                           "inner join petugas on periksa_radiologi.nip=petugas.nip "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.kd_jenis_prw like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_radiologi.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.dokter_perujuk like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perujuk.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_radiologi.tgl_periksa between ? and ? and concat(periksa_radiologi.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_radiologi.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by periksa_radiologi.tgl_periksa");
                }
                    
            }    
                
            try {
                if(KdDokterPerujukRad.getText().equals("")&&NmDokterPerujukRad.getText().equals("")&&KdPetugasRad.getText().equals("")&&NmPetugasRad.getText().equals("")&&KdCaraBayarRad.getText().equals("")&&NmCaraBayarRad.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(4,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(5,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(9,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(10,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(11,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(15,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(16,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(17,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(21,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(22,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(23,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(27,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(28,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(29,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(33,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(34,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(35,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(39,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(40,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(41,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(42,"%"+TCari.getText().trim()+"%");
                    ps.setString(43,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(44,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(45,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(46,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(47,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(48,"%"+TCari.getText().trim()+"%");
                    ps.setString(49,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(50,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(51,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(52,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(53,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(54,"%"+TCari.getText().trim()+"%");
                    ps.setString(55,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(56,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(57,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(58,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(59,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(60,"%"+TCari.getText().trim()+"%");
                    ps.setString(61,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(62,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(63,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(64,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(65,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(66,"%"+TCari.getText().trim()+"%");
                    ps.setString(67,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(68,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(69,"%"+KdDokterPerujukRad.getText()+NmDokterPerujukRad.getText()+"%");
                    ps.setString(70,"%"+KdPetugasRad.getText()+NmPetugasRad.getText()+"%");
                    ps.setString(71,"%"+KdCaraBayarRad.getText()+NmCaraBayarRad.getText()+"%");
                    ps.setString(72,"%"+TCari.getText().trim()+"%");
                }   
                    
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
            if(KdDokterPerujukLab.getText().equals("")&&NmDokterPerujukLab.getText().equals("")&&KdPetugasLab.getText().equals("")&&NmPetugasLab.getText().equals("")&&KdCaraBayarLab.getText().equals("")&&NmCaraBayarLab.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                       "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                       "periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan, "+
                       "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                       "petugas.nama,periksa_lab.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                       "periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                       "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                       "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas,"+
                       "periksa_lab.kso,periksa_lab.menejemen,periksa_lab.biaya,"+
                       "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                       "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                       "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan "+
                       "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                       "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                       "inner join dokter as perujuk on periksa_lab.dokter_perujuk=perujuk.kd_dokter "+
                       "inner join petugas on periksa_lab.nip=petugas.nip "+
                       "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                       "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                       "where periksa_lab.tgl_periksa between ? and ? order by periksa_lab.tgl_periksa");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                           "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                           "periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan, "+
                           "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                           "petugas.nama,periksa_lab.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                           "periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                           "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                           "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas,"+
                           "periksa_lab.kso,periksa_lab.menejemen,periksa_lab.biaya,"+
                           "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                           "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan "+
                           "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                           "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                           "inner join dokter as perujuk on periksa_lab.dokter_perujuk=perujuk.kd_dokter "+
                           "inner join petugas on periksa_lab.nip=petugas.nip "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                           "where periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.no_rawat like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.kd_jenis_prw like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_lab.nm_perawatan like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.kd_dokter like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.nip like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.dokter_perujuk like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perujuk.nm_dokter like ? or "+
                           "periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by periksa_lab.tgl_periksa");
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                           "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                           "periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan, "+
                           "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                           "petugas.nama,periksa_lab.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                           "periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                           "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                           "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas,"+
                           "periksa_lab.kso,periksa_lab.menejemen,periksa_lab.biaya,"+
                           "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                           "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan "+
                           "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                           "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                           "inner join dokter as perujuk on periksa_lab.dokter_perujuk=perujuk.kd_dokter "+
                           "inner join petugas on periksa_lab.nip=petugas.nip "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                           "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.kd_jenis_prw like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_lab.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.dokter_perujuk like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perujuk.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by periksa_lab.tgl_periksa");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                           "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                           "periksa_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan, "+
                           "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                           "petugas.nama,periksa_lab.dokter_perujuk,perujuk.nm_dokter as perujuk,"+
                           "periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                           "periksa_lab.bagian_rs,periksa_lab.bhp,periksa_lab.tarif_perujuk,"+
                           "periksa_lab.tarif_tindakan_dokter,periksa_lab.tarif_tindakan_petugas,"+
                           "periksa_lab.kso,periksa_lab.menejemen,periksa_lab.biaya,"+
                           "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                           "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                           "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan "+
                           "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                           "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter "+
                           "inner join dokter as perujuk on periksa_lab.dokter_perujuk=perujuk.kd_dokter "+
                           "inner join petugas on periksa_lab.nip=petugas.nip "+
                           "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                           "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                           "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.no_rawat like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.kd_jenis_prw like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_lab.nm_perawatan like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.kd_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.nip like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.dokter_perujuk like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perujuk.nm_dokter like ? or "+
                           "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                           " order by periksa_lab.tgl_periksa");
                }   
            }
                
            try {
                if(KdDokterPerujukLab.getText().equals("")&&NmDokterPerujukLab.getText().equals("")&&KdPetugasLab.getText().equals("")&&NmPetugasLab.getText().equals("")&&KdCaraBayarLab.getText().equals("")&&NmCaraBayarLab.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(4,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(5,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(9,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(10,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(11,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(15,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(16,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(17,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(21,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(22,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(23,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(27,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(28,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(29,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(33,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(34,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(35,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(39,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(40,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(41,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(42,"%"+TCari.getText().trim()+"%");
                    ps.setString(43,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(44,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(45,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(46,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(47,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(48,"%"+TCari.getText().trim()+"%");
                    ps.setString(49,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(50,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(51,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(52,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(53,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(54,"%"+TCari.getText().trim()+"%");
                    ps.setString(55,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(56,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(57,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(58,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(59,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(60,"%"+TCari.getText().trim()+"%");
                    ps.setString(61,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(62,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(63,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(64,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(65,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(66,"%"+TCari.getText().trim()+"%");
                    ps.setString(67,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(68,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(69,"%"+KdDokterPerujukLab.getText()+NmDokterPerujukLab.getText()+"%");
                    ps.setString(70,"%"+KdPetugasLab.getText()+NmPetugasLab.getText()+"%");
                    ps.setString(71,"%"+KdCaraBayarLab.getText()+NmCaraBayarLab.getText()+"%");
                    ps.setString(72,"%"+TCari.getText().trim()+"%");
                }
                    
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
            if(KdDokterPerujukDetailLab.getText().equals("")&&NmDokterPerujukDetailLab.getText().equals("")&&KdPetugasDetailLab.getText().equals("")&&NmPetugasDetailLab.getText().equals("")&&KdCaraBayarDetailLab.getText().equals("")&&NmCaraBayarDetailLab.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                ps=koneksi.prepareStatement(
                        "select detail_periksa_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                        "pasien.nm_pasien,detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan,"+
                        "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                        "petugas.nama,periksa_lab.dokter_perujuk,penjab.png_jawab,perujuk.nm_dokter as perujuk,"+
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
                        "inner join dokter inner join petugas inner join penjab inner join dokter as perujuk "+
                        "on detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                        "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                        "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and perujuk.kd_dokter=periksa_lab.dokter_perujuk "+
                        "and reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.kd_dokter=dokter.kd_dokter "+
                        "and periksa_lab.nip=petugas.nip and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                        "and periksa_lab.jam=detail_periksa_lab.jam and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                        "and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                        "where detail_periksa_lab.tgl_periksa between ? and ? order by detail_periksa_lab.tgl_periksa");
            }else{
                if(cmbStatus.getSelectedItem().equals("Semua")){
                    ps=koneksi.prepareStatement(
                            "select detail_periksa_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                            "pasien.nm_pasien,detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan,"+
                            "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                            "petugas.nama,periksa_lab.dokter_perujuk,penjab.png_jawab,perujuk.nm_dokter as perujuk,"+
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
                            "inner join dokter inner join petugas inner join penjab inner join dokter as perujuk "+
                            "on detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                            "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and perujuk.kd_dokter=periksa_lab.dokter_perujuk "+
                            "and reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.kd_dokter=dokter.kd_dokter "+
                            "and periksa_lab.nip=petugas.nip and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                            "and periksa_lab.jam=detail_periksa_lab.jam and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                            "and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                            "where detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.no_rawat like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.kd_jenis_prw like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_lab.nm_perawatan like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.kd_dokter like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.nip like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.dokter_perujuk like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perujuk.nm_dokter like ? or "+
                            "detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                            " order by detail_periksa_lab.tgl_periksa");
                }else if(cmbStatus.getSelectedItem().equals("Belum Lunas")){
                    ps=koneksi.prepareStatement(
                            "select detail_periksa_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                            "pasien.nm_pasien,detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan,"+
                            "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                            "petugas.nama,periksa_lab.dokter_perujuk,penjab.png_jawab,perujuk.nm_dokter as perujuk,"+
                            "detail_periksa_lab.tgl_periksa,detail_periksa_lab.jam,"+
                            "if(periksa_lab.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                            "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                            "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=periksa_lab.no_rawat limit 1 )) as ruangan,"+
                            "detail_periksa_lab.bagian_rs,detail_periksa_lab.bhp,detail_periksa_lab.bagian_perujuk,"+
                            "detail_periksa_lab.bagian_dokter,detail_periksa_lab.bagian_laborat,"+
                            "detail_periksa_lab.kso,detail_periksa_lab.menejemen,"+
                            "detail_periksa_lab.biaya_item from detail_periksa_lab "+
                            "inner join reg_periksa inner join pasien inner join template_laboratorium "+
                            "inner join jns_perawatan_lab inner join periksa_lab inner join piutang_pasien "+
                            "inner join dokter inner join petugas inner join penjab inner join dokter as perujuk "+
                            "on detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                            "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and perujuk.kd_dokter=periksa_lab.dokter_perujuk "+
                            "and reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.kd_dokter=dokter.kd_dokter "+
                            "and periksa_lab.nip=petugas.nip and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                            "and periksa_lab.jam=detail_periksa_lab.jam and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                            "and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                            "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.no_rawat like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.kd_jenis_prw like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_lab.nm_perawatan like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.kd_dokter like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.nip like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.dokter_perujuk like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perujuk.nm_dokter like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                            " order by detail_periksa_lab.tgl_periksa");
                }else if(cmbStatus.getSelectedItem().equals("Sudah Lunas")){
                    ps=koneksi.prepareStatement(
                            "select detail_periksa_lab.no_rawat,reg_periksa.no_rkm_medis,"+
                            "pasien.nm_pasien,detail_periksa_lab.id_template,template_laboratorium.Pemeriksaan,"+
                            "periksa_lab.kd_dokter,dokter.nm_dokter,periksa_lab.nip,"+
                            "petugas.nama,periksa_lab.dokter_perujuk,penjab.png_jawab,perujuk.nm_dokter as perujuk,"+
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
                            "inner join dokter inner join petugas inner join penjab inner join dokter as perujuk "+
                            "on detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.no_rawat=detail_periksa_lab.no_rawat "+
                            "and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and perujuk.kd_dokter=periksa_lab.dokter_perujuk "+
                            "and reg_periksa.kd_pj=penjab.kd_pj and periksa_lab.kd_dokter=dokter.kd_dokter "+
                            "and periksa_lab.nip=petugas.nip and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa "+
                            "and periksa_lab.jam=detail_periksa_lab.jam and detail_periksa_lab.id_template=template_laboratorium.id_template "+
                            "and jns_perawatan_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                            "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.no_rawat like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and reg_periksa.no_rkm_medis like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and pasien.nm_pasien like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and detail_periksa_lab.kd_jenis_prw like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and jns_perawatan_lab.nm_perawatan like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.kd_dokter like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and dokter.nm_dokter like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.nip like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and petugas.nama like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.dokter_perujuk like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and perujuk.nm_dokter like ? or "+
                            "reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien where status='Belum Lunas') and detail_periksa_lab.tgl_periksa between ? and ? and concat(periksa_lab.dokter_perujuk,perujuk.nm_dokter) like ? and concat(periksa_lab.nip,petugas.nama) like ? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and penjab.png_jawab like ? "+
                            " order by detail_periksa_lab.tgl_periksa");
                }   
            }
                
            try {
                if(KdDokterPerujukDetailLab.getText().equals("")&&NmDokterPerujukDetailLab.getText().equals("")&&KdPetugasDetailLab.getText().equals("")&&NmPetugasDetailLab.getText().equals("")&&KdCaraBayarDetailLab.getText().equals("")&&NmCaraBayarDetailLab.getText().equals("")&&TCari.getText().equals("")&&cmbStatus.getSelectedItem().equals("Semua")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(4,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(5,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(9,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(10,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(11,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(15,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(16,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(17,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(21,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(22,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(23,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(27,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(28,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(29,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(33,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(34,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(35,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(39,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(40,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(41,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(42,"%"+TCari.getText().trim()+"%");
                    ps.setString(43,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(44,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(45,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(46,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(47,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(48,"%"+TCari.getText().trim()+"%");
                    ps.setString(49,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(50,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(51,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(52,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(53,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(54,"%"+TCari.getText().trim()+"%");
                    ps.setString(55,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(56,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(57,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(58,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(59,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(60,"%"+TCari.getText().trim()+"%");
                    ps.setString(61,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(62,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(63,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(64,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(65,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(66,"%"+TCari.getText().trim()+"%");
                    ps.setString(67,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(68,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(69,"%"+KdDokterPerujukDetailLab.getText()+NmDokterPerujukDetailLab.getText()+"%");
                    ps.setString(70,"%"+KdPetugasDetailLab.getText()+NmPetugasDetailLab.getText()+"%");
                    ps.setString(71,"%"+KdCaraBayarDetailLab.getText()+NmCaraBayarDetailLab.getText()+"%");
                    ps.setString(72,"%"+TCari.getText().trim()+"%");
                }
                    
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
                if(tabModeDetailLaborat.getRowCount()>0){
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
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,65));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    private void isForm2(){
        if(ChkInput1.isSelected()==true){
            ChkInput1.setVisible(false);
            PanelInput1.setPreferredSize(new Dimension(WIDTH,65));
            FormInput1.setVisible(true);      
            ChkInput1.setVisible(true);
        }else if(ChkInput1.isSelected()==false){           
            ChkInput1.setVisible(false);            
            PanelInput1.setPreferredSize(new Dimension(WIDTH,20));
            FormInput1.setVisible(false);      
            ChkInput1.setVisible(true);
        }
    }
    
    private void isForm3(){
        if(ChkInput2.isSelected()==true){
            ChkInput2.setVisible(false);
            PanelInput2.setPreferredSize(new Dimension(WIDTH,95));
            FormInput2.setVisible(true);      
            ChkInput2.setVisible(true);
        }else if(ChkInput2.isSelected()==false){           
            ChkInput2.setVisible(false);            
            PanelInput2.setPreferredSize(new Dimension(WIDTH,20));
            FormInput2.setVisible(false);      
            ChkInput2.setVisible(true);
        }
    }
    
    private void isForm4(){
        if(ChkInput3.isSelected()==true){
            ChkInput3.setVisible(false);
            PanelInput3.setPreferredSize(new Dimension(WIDTH,65));
            FormInput3.setVisible(true);      
            ChkInput3.setVisible(true);
        }else if(ChkInput3.isSelected()==false){           
            ChkInput3.setVisible(false);            
            PanelInput3.setPreferredSize(new Dimension(WIDTH,20));
            FormInput3.setVisible(false);      
            ChkInput3.setVisible(true);
        }
    }
    
    private void isForm5(){
        if(ChkInput4.isSelected()==true){
            ChkInput4.setVisible(false);
            PanelInput4.setPreferredSize(new Dimension(WIDTH,65));
            FormInput4.setVisible(true);      
            ChkInput4.setVisible(true);
        }else if(ChkInput4.isSelected()==false){           
            ChkInput4.setVisible(false);            
            PanelInput4.setPreferredSize(new Dimension(WIDTH,20));
            FormInput4.setVisible(false);      
            ChkInput4.setVisible(true);
        }
    }
    
    private void isForm6(){
        if(ChkInput5.isSelected()==true){
            ChkInput5.setVisible(false);
            PanelInput5.setPreferredSize(new Dimension(WIDTH,65));
            FormInput5.setVisible(true);      
            ChkInput5.setVisible(true);
        }else if(ChkInput5.isSelected()==false){           
            ChkInput5.setVisible(false);            
            PanelInput5.setPreferredSize(new Dimension(WIDTH,20));
            FormInput5.setVisible(false);      
            ChkInput5.setVisible(true);
        }
    }
    
    private void isForm7(){
        if(ChkInput7.isSelected()==true){
            ChkInput7.setVisible(false);
            PanelInput7.setPreferredSize(new Dimension(WIDTH,65));
            FormInput7.setVisible(true);      
            ChkInput7.setVisible(true);
        }else if(ChkInput7.isSelected()==false){           
            ChkInput7.setVisible(false);            
            PanelInput7.setPreferredSize(new Dimension(WIDTH,20));
            FormInput7.setVisible(false);      
            ChkInput7.setVisible(true);
        }
    }
    
    private void isForm8(){
        if(ChkInput8.isSelected()==true){
            ChkInput8.setVisible(false);
            PanelInput8.setPreferredSize(new Dimension(WIDTH,65));
            FormInput8.setVisible(true);      
            ChkInput8.setVisible(true);
        }else if(ChkInput8.isSelected()==false){           
            ChkInput8.setVisible(false);            
            PanelInput8.setPreferredSize(new Dimension(WIDTH,20));
            FormInput8.setVisible(false);      
            ChkInput8.setVisible(true);
        }
    }
    
    private void isForm9(){
        if(ChkInput9.isSelected()==true){
            ChkInput9.setVisible(false);
            PanelInput9.setPreferredSize(new Dimension(WIDTH,65));
            FormInput9.setVisible(true);      
            ChkInput9.setVisible(true);
        }else if(ChkInput9.isSelected()==false){           
            ChkInput9.setVisible(false);            
            PanelInput9.setPreferredSize(new Dimension(WIDTH,20));
            FormInput9.setVisible(false);      
            ChkInput9.setVisible(true);
        }
    }
    
    private void isForm10(){
        if(ChkInput10.isSelected()==true){
            ChkInput10.setVisible(false);
            PanelInput10.setPreferredSize(new Dimension(WIDTH,65));
            FormInput10.setVisible(true);      
            ChkInput10.setVisible(true);
        }else if(ChkInput10.isSelected()==false){           
            ChkInput10.setVisible(false);            
            PanelInput10.setPreferredSize(new Dimension(WIDTH,20));
            FormInput10.setVisible(false);      
            ChkInput10.setVisible(true);
        }
    }
 
}
