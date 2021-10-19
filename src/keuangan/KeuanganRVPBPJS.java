

package keuangan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
 

/**
 *
 * @author perpustakaan
 */
public final class KeuanganRVPBPJS extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int row=0,i;
    private String koderekening="",norawatbayi="";
    private Jurnal jur=new Jurnal();
    private String status="",tampilkan_administrasi_di_billingranap="",tampilkan_ppnobat_ralan="",tampilkan_ppnobat_ranap="",
                   Piutang_BPJS_RVP="",Kerugian_Klaim_BPJS_RVP="",Lebih_Bayar_Klaim_BPJS_RVP="",Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",
                   Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan="",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan="",
                   Beban_KSO_Tindakan_Ralan="",Utang_KSO_Tindakan_Ralan="",Beban_Jasa_Sarana_Tindakan_Ralan="",Utang_Jasa_Sarana_Tindakan_Ralan="",
                   HPP_BHP_Tindakan_Ralan="",Beban_Jasa_Menejemen_Tindakan_Ralan="",Utang_Jasa_Menejemen_Tindakan_Ralan="",
                   Laborat_Ralan="",Beban_Jasa_Medik_Dokter_Laborat_Ralan="",Utang_Jasa_Medik_Dokter_Laborat_Ralan="",Beban_Jasa_Medik_Petugas_Laborat_Ralan="",
                   Utang_Jasa_Medik_Petugas_Laborat_Ralan="",Beban_Kso_Laborat_Ralan="",Utang_Kso_Laborat_Ralan="",HPP_Persediaan_Laborat_Rawat_Jalan="",
                   Beban_Jasa_Sarana_Laborat_Ralan="",Utang_Jasa_Sarana_Laborat_Ralan="",Beban_Jasa_Perujuk_Laborat_Ralan="",
                   Utang_Jasa_Perujuk_Laborat_Ralan="",Beban_Jasa_Menejemen_Laborat_Ralan="",Utang_Jasa_Menejemen_Laborat_Ralan="",Radiologi_Ralan="",
                   Beban_Jasa_Medik_Dokter_Radiologi_Ralan="",Utang_Jasa_Medik_Dokter_Radiologi_Ralan="",Beban_Jasa_Medik_Petugas_Radiologi_Ralan="",
                   Utang_Jasa_Medik_Petugas_Radiologi_Ralan="",Beban_Kso_Radiologi_Ralan="",Utang_Kso_Radiologi_Ralan="",HPP_Persediaan_Radiologi_Rawat_Jalan="",
                   Beban_Jasa_Sarana_Radiologi_Ralan="",Utang_Jasa_Sarana_Radiologi_Ralan="",Beban_Jasa_Perujuk_Radiologi_Ralan="",
                   Utang_Jasa_Perujuk_Radiologi_Ralan="",Beban_Jasa_Menejemen_Radiologi_Ralan="",Utang_Jasa_Menejemen_Radiologi_Ralan="",Obat_Ralan="",
                   HPP_Obat_Rawat_Jalan="",Registrasi_Ralan="",Operasi_Ralan="",Beban_Jasa_Medik_Dokter_Operasi_Ralan="",
                   Utang_Jasa_Medik_Dokter_Operasi_Ralan="",Beban_Jasa_Medik_Paramedis_Operasi_Ralan="",Utang_Jasa_Medik_Paramedis_Operasi_Ralan="",
                   HPP_Obat_Operasi_Ralan="",Tambahan_Ralan="",Potongan_Ralan="",Tindakan_Ranap="",
                   Beban_Jasa_Medik_Dokter_Tindakan_Ranap="",Utang_Jasa_Medik_Dokter_Tindakan_Ranap="",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap="",
                   Utang_Jasa_Medik_Paramedis_Tindakan_Ranap="",Beban_KSO_Tindakan_Ranap="",Utang_KSO_Tindakan_Ranap="",Beban_Jasa_Sarana_Tindakan_Ranap="",
                   Utang_Jasa_Sarana_Tindakan_Ranap="",Beban_Jasa_Menejemen_Tindakan_Ranap="",Utang_Jasa_Menejemen_Tindakan_Ranap="",HPP_BHP_Tindakan_Ranap="",
                   Laborat_Ranap="",Beban_Jasa_Medik_Dokter_Laborat_Ranap="",Utang_Jasa_Medik_Dokter_Laborat_Ranap="",
                   Beban_Jasa_Medik_Petugas_Laborat_Ranap="",Utang_Jasa_Medik_Petugas_Laborat_Ranap="",Beban_Kso_Laborat_Ranap="",Utang_Kso_Laborat_Ranap="",
                   HPP_Persediaan_Laborat_Rawat_inap="",Beban_Jasa_Sarana_Laborat_Ranap="",Utang_Jasa_Sarana_Laborat_Ranap="",
                   Beban_Jasa_Perujuk_Laborat_Ranap="",Utang_Jasa_Perujuk_Laborat_Ranap="",Beban_Jasa_Menejemen_Laborat_Ranap="",Utang_Jasa_Menejemen_Laborat_Ranap="",
                   Radiologi_Ranap="",Beban_Jasa_Medik_Dokter_Radiologi_Ranap="",Utang_Jasa_Medik_Dokter_Radiologi_Ranap="",Beban_Jasa_Medik_Petugas_Radiologi_Ranap="",
                   Utang_Jasa_Medik_Petugas_Radiologi_Ranap="",Beban_Kso_Radiologi_Ranap="",Utang_Kso_Radiologi_Ranap="",HPP_Persediaan_Radiologi_Rawat_Inap="",
                   Beban_Jasa_Sarana_Radiologi_Ranap="",Utang_Jasa_Sarana_Radiologi_Ranap="",Beban_Jasa_Perujuk_Radiologi_Ranap="",
                   Utang_Jasa_Perujuk_Radiologi_Ranap="",Beban_Jasa_Menejemen_Radiologi_Ranap="",Utang_Jasa_Menejemen_Radiologi_Ranap="",Obat_Ranap="",
                   HPP_Obat_Rawat_Inap="",Registrasi_Ranap="",Tambahan_Ranap="",Potongan_Ranap="",
                   Retur_Obat_Ranap="",Resep_Pulang_Ranap="",Kamar_Inap="",Operasi_Ranap="",Beban_Jasa_Medik_Dokter_Operasi_Ranap="",Utang_Jasa_Medik_Dokter_Operasi_Ranap="",
                   Beban_Jasa_Medik_Paramedis_Operasi_Ranap="",Utang_Jasa_Medik_Paramedis_Operasi_Ranap="",HPP_Obat_Operasi_Ranap="",Service_Ranap="",
                   Harian_Ranap="",json="";
    private double total=0,sisapiutang=0,cicilan=0,rugi=0,lebih=0,selisih=0,materialralan=0,bhpralan=0,tarif_tindakandrralan=0,tarif_tindakanprralan=0,ksoralan=0,menejemenralan=0,biaya_rawatralan=0,
                   materialranap=0,bhpranap=0,tarif_tindakandrranap=0,tarif_tindakanprranap=0,ksoranap=0,menejemenranap=0,biaya_rawatranap=0,bagian_rslabralan=0,bhplabralan=0,tarif_perujuklabralan=0,
                   tarif_tindakan_dokterlabralan=0,tarif_tindakan_petugaslabralan=0,ksolabralan=0,menejemenlabralan=0,biayalabralan=0,bagian_rslabranap=0,bhplabranap=0,tarif_perujuklabranap=0,
                   tarif_tindakan_dokterlabranap=0,tarif_tindakan_petugaslabranap=0,ksolabranap=0,menejemenlabranap=0,biayalabranap=0,bagian_rsradiologiralan=0,bhpradiologiralan=0,tarif_perujukradiologiralan=0,
                   tarif_tindakan_dokterradiologiralan=0,tarif_tindakan_petugasradiologiralan=0,ksoradiologiralan=0,menejemenradiologiralan=0,biayaradiologiralan=0,bagian_rsradiologiranap=0,bhpradiologiranap=0,
                   tarif_perujukradiologiranap=0,tarif_tindakan_dokterradiologiranap=0,tarif_tindakan_petugasradiologiranap=0,ksoradiologiranap=0,menejemenradiologiranap=0,biayaradiologiranap=0,
                   jmdokteroperasiralan=0,jmparamedisoperasiralan=0,bhpoperasiralan=0,pendapatanoperasiralan=0,jmdokteroperasiranap=0,jmparamedisoperasiranap=0,bhpoperasiranap=0,pendapatanoperasiranap=0,
                   obatlangsung=0,obatralan=0,hppobatralan=0,obatranap=0,hppobatranap=0,returobat=0,tambahanbiaya=0,potonganbiaya=0,kamar=0,reseppulang=0,totalbiaya=0,registrasi=0,harianranap=0,rugihppralan=0,
                   rugihppranap=0,serviceranap=0,ttlpiutang=0,ttliur=0,ttlsudahdibayar=0,ttlsisapiutang=0,ttlinacbg=0,persenbayar=0;
    private boolean sukses=true;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private javax.swing.JFileChooser jfc = new JFileChooser();    
    private FileFilter excelFilter = new FileNameExtensionFilter("File CSV", "csv");
    private JsonNode root;
    private ObjectMapper mapper = new ObjectMapper();
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private JsonNode response;
    private FileReader myObj;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganRVPBPJS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","No.Rawat/No.tagihan","No.SEP VClaim","Tgl.Piutang","Pasien","Total Piutang","Iur/Ekses",
                "Sudah Dibayar","Sisa Piutang","Tarif InaCBG","Dibayar BPJS","% Bayar","Kerugian","Lebih Bayar",
                "Status","Registrasi","materialralan","bhpralan","tarif_tindakandrralan","tarif_tindakanprralan",
                "ksoralan","menejemenralan","biaya_rawatralan","materialranap","bhpranap","tarif_tindakandrranap",
                "tarif_tindakanprranap","ksoranap","menejemenranap","biaya_rawatranap","bagian_rslabralan","bhplabralan",
                "tarif_perujuklabralan","tarif_tindakan_dokterlabralan","tarif_tindakan_petugaslabralan","ksolabralan",
                "menejemenlabralan","biayalabralan","bagian_rslabranap","bhplabranap","tarif_perujuklabranap",
                "tarif_tindakan_dokterlabranap","tarif_tindakan_petugaslabranap","ksolabranap","menejemenlabranap",
                "biayalabranap","bagian_rsradiologiralan","bhpradiologiralan","tarif_perujukradiologiralan",
                "tarif_tindakan_dokterradiologiralan","tarif_tindakan_petugasradiologiralan","ksoradiologiralan",
                "menejemenradiologiralan","biayaradiologiralan","bagian_rsradiologiranap","bhpradiologiranap",
                "tarif_perujukradiologiranap","tarif_tindakan_dokterradiologiranap","tarif_tindakan_petugasradiologiranap",
                "ksoradiologiranap","menejemenradiologiranap","biayaradiologiranap","jmdokteroperasiralan","jmparamedisoperasiralan",
                "bhpoperasiralan","pendapatanoperasiralan","jmdokteroperasiranap","jmparamedisoperasiranap","bhpoperasiranap",
                "pendapatanoperasiranap","obatlangsung","obatralan","hppobatralan","obatranap","hppobatranap","returobat",
                "tambahanbiaya","potonganbiaya","kamar","reseppulang","harianranap","registrasi","service"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==10)||(colIndex==0)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 83; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(140);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(48);
            }else if(i==12){
                column.setPreferredWidth(75);
            }else if(i==13){
                column.setPreferredWidth(75);
            }else if(i==14){
                column.setPreferredWidth(40);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                kdptg.requestFocus();
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

        try {
            ps=koneksi.prepareStatement("select * from set_nota");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    tampilkan_administrasi_di_billingranap=rs.getString("tampilkan_administrasi_di_billingranap");
                    tampilkan_ppnobat_ralan=rs.getString("tampilkan_ppnobat_ralan");
                    tampilkan_ppnobat_ranap=rs.getString("tampilkan_ppnobat_ranap");
                }else{
                    tampilkan_administrasi_di_billingranap="No";
                    tampilkan_ppnobat_ralan="No";
                    tampilkan_ppnobat_ranap="No";
                }
            } catch (Exception e) {
                tampilkan_administrasi_di_billingranap="No";
                tampilkan_ppnobat_ralan="No";
                tampilkan_ppnobat_ranap="No";
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
        
        try {
            ps=koneksi.prepareStatement("select * from set_akun");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    Piutang_BPJS_RVP=rs.getString("Piutang_BPJS_RVP");
                    Kerugian_Klaim_BPJS_RVP=rs.getString("Kerugian_Klaim_BPJS_RVP");
                    Lebih_Bayar_Klaim_BPJS_RVP=rs.getString("Lebih_Bayar_Klaim_BPJS_RVP");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }   
            
            ps=koneksi.prepareStatement("select * from set_akun_ralan");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                   Tindakan_Ralan=rs.getString("Tindakan_Ralan");
                   Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                   Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                   Beban_Jasa_Medik_Paramedis_Tindakan_Ralan=rs.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ralan");
                   Utang_Jasa_Medik_Paramedis_Tindakan_Ralan=rs.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ralan");
                   Beban_KSO_Tindakan_Ralan=rs.getString("Beban_KSO_Tindakan_Ralan");
                   Utang_KSO_Tindakan_Ralan=rs.getString("Utang_KSO_Tindakan_Ralan");
                   Beban_Jasa_Sarana_Tindakan_Ralan=rs.getString("Beban_Jasa_Sarana_Tindakan_Ralan");
                   Utang_Jasa_Sarana_Tindakan_Ralan=rs.getString("Utang_Jasa_Sarana_Tindakan_Ralan");
                   HPP_BHP_Tindakan_Ralan=rs.getString("HPP_BHP_Tindakan_Ralan");
                   Beban_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");
                   Utang_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");
                   Laborat_Ralan=rs.getString("Laborat_Ralan");
                   Beban_Jasa_Medik_Dokter_Laborat_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Laborat_Ralan");
                   Utang_Jasa_Medik_Dokter_Laborat_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Laborat_Ralan");
                   Beban_Jasa_Medik_Petugas_Laborat_Ralan=rs.getString("Beban_Jasa_Medik_Petugas_Laborat_Ralan");
                   Utang_Jasa_Medik_Petugas_Laborat_Ralan=rs.getString("Utang_Jasa_Medik_Petugas_Laborat_Ralan");
                   Beban_Kso_Laborat_Ralan=rs.getString("Beban_Kso_Laborat_Ralan");
                   Utang_Kso_Laborat_Ralan=rs.getString("Utang_Kso_Laborat_Ralan");
                   HPP_Persediaan_Laborat_Rawat_Jalan=rs.getString("HPP_Persediaan_Laborat_Rawat_Jalan");
                   Beban_Jasa_Sarana_Laborat_Ralan=rs.getString("Beban_Jasa_Sarana_Laborat_Ralan");
                   Utang_Jasa_Sarana_Laborat_Ralan=rs.getString("Utang_Jasa_Sarana_Laborat_Ralan");
                   Beban_Jasa_Perujuk_Laborat_Ralan=rs.getString("Beban_Jasa_Perujuk_Laborat_Ralan");
                   Utang_Jasa_Perujuk_Laborat_Ralan=rs.getString("Utang_Jasa_Perujuk_Laborat_Ralan");
                   Beban_Jasa_Menejemen_Laborat_Ralan=rs.getString("Beban_Jasa_Menejemen_Laborat_Ralan");
                   Utang_Jasa_Menejemen_Laborat_Ralan=rs.getString("Utang_Jasa_Menejemen_Laborat_Ralan");
                   Radiologi_Ralan=rs.getString("Radiologi_Ralan");
                   Beban_Jasa_Medik_Dokter_Radiologi_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ralan");
                   Utang_Jasa_Medik_Dokter_Radiologi_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ralan");
                   Beban_Jasa_Medik_Petugas_Radiologi_Ralan=rs.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ralan");
                   Utang_Jasa_Medik_Petugas_Radiologi_Ralan=rs.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ralan");
                   Beban_Kso_Radiologi_Ralan=rs.getString("Beban_Kso_Radiologi_Ralan");
                   Utang_Kso_Radiologi_Ralan=rs.getString("Utang_Kso_Radiologi_Ralan");
                   HPP_Persediaan_Radiologi_Rawat_Jalan=rs.getString("HPP_Persediaan_Radiologi_Rawat_Jalan");
                   Beban_Jasa_Sarana_Radiologi_Ralan=rs.getString("Beban_Jasa_Sarana_Radiologi_Ralan");
                   Utang_Jasa_Sarana_Radiologi_Ralan=rs.getString("Utang_Jasa_Sarana_Radiologi_Ralan");
                   Beban_Jasa_Perujuk_Radiologi_Ralan=rs.getString("Beban_Jasa_Perujuk_Radiologi_Ralan");
                   Utang_Jasa_Perujuk_Radiologi_Ralan=rs.getString("Utang_Jasa_Perujuk_Radiologi_Ralan");
                   Beban_Jasa_Menejemen_Radiologi_Ralan=rs.getString("Beban_Jasa_Menejemen_Radiologi_Ralan");
                   Utang_Jasa_Menejemen_Radiologi_Ralan=rs.getString("Utang_Jasa_Menejemen_Radiologi_Ralan");
                   Obat_Ralan=rs.getString("Obat_Ralan");
                   HPP_Obat_Rawat_Jalan=rs.getString("HPP_Obat_Rawat_Jalan");
                   Registrasi_Ralan=rs.getString("Registrasi_Ralan");
                   Operasi_Ralan=rs.getString("Operasi_Ralan");
                   Beban_Jasa_Medik_Dokter_Operasi_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Operasi_Ralan");
                   Utang_Jasa_Medik_Dokter_Operasi_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Operasi_Ralan");
                   Beban_Jasa_Medik_Paramedis_Operasi_Ralan=rs.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ralan");
                   Utang_Jasa_Medik_Paramedis_Operasi_Ralan=rs.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ralan");
                   HPP_Obat_Operasi_Ralan=rs.getString("HPP_Obat_Operasi_Ralan");
                   Tambahan_Ralan=rs.getString("Tambahan_Ralan");
                   Potongan_Ralan=rs.getString("Potongan_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            } 
            
            ps=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                   Tindakan_Ranap=rs.getString("Tindakan_Ranap");
                   Beban_Jasa_Medik_Dokter_Tindakan_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ranap");
                   Utang_Jasa_Medik_Dokter_Tindakan_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ranap");
                   Beban_Jasa_Medik_Paramedis_Tindakan_Ranap=rs.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ranap");
                   Utang_Jasa_Medik_Paramedis_Tindakan_Ranap=rs.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ranap");
                   Beban_KSO_Tindakan_Ranap=rs.getString("Beban_KSO_Tindakan_Ranap");
                   Utang_KSO_Tindakan_Ranap=rs.getString("Utang_KSO_Tindakan_Ranap");
                   Beban_Jasa_Sarana_Tindakan_Ranap=rs.getString("Beban_Jasa_Sarana_Tindakan_Ranap");
                   Utang_Jasa_Sarana_Tindakan_Ranap=rs.getString("Utang_Jasa_Sarana_Tindakan_Ranap");
                   Beban_Jasa_Menejemen_Tindakan_Ranap=rs.getString("Beban_Jasa_Menejemen_Tindakan_Ranap");
                   Utang_Jasa_Menejemen_Tindakan_Ranap=rs.getString("Utang_Jasa_Menejemen_Tindakan_Ranap");
                   HPP_BHP_Tindakan_Ranap=rs.getString("HPP_BHP_Tindakan_Ranap");
                   Laborat_Ranap=rs.getString("Laborat_Ranap");
                   Beban_Jasa_Medik_Dokter_Laborat_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Laborat_Ranap");
                   Utang_Jasa_Medik_Dokter_Laborat_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Laborat_Ranap");
                   Beban_Jasa_Medik_Petugas_Laborat_Ranap=rs.getString("Beban_Jasa_Medik_Petugas_Laborat_Ranap");
                   Utang_Jasa_Medik_Petugas_Laborat_Ranap=rs.getString("Utang_Jasa_Medik_Petugas_Laborat_Ranap");
                   Beban_Kso_Laborat_Ranap=rs.getString("Beban_Kso_Laborat_Ranap");
                   Utang_Kso_Laborat_Ranap=rs.getString("Utang_Kso_Laborat_Ranap");
                   HPP_Persediaan_Laborat_Rawat_inap=rs.getString("HPP_Persediaan_Laborat_Rawat_inap");
                   Beban_Jasa_Sarana_Laborat_Ranap=rs.getString("Beban_Jasa_Sarana_Laborat_Ranap");
                   Utang_Jasa_Sarana_Laborat_Ranap=rs.getString("Utang_Jasa_Sarana_Laborat_Ranap");
                   Beban_Jasa_Perujuk_Laborat_Ranap=rs.getString("Beban_Jasa_Perujuk_Laborat_Ranap");
                   Utang_Jasa_Perujuk_Laborat_Ranap=rs.getString("Utang_Jasa_Perujuk_Laborat_Ranap");
                   Beban_Jasa_Menejemen_Laborat_Ranap=rs.getString("Beban_Jasa_Menejemen_Laborat_Ranap");
                   Utang_Jasa_Menejemen_Laborat_Ranap=rs.getString("Utang_Jasa_Menejemen_Laborat_Ranap");
                   Radiologi_Ranap=rs.getString("Radiologi_Ranap");
                   Beban_Jasa_Medik_Dokter_Radiologi_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ranap");
                   Utang_Jasa_Medik_Dokter_Radiologi_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ranap");
                   Beban_Jasa_Medik_Petugas_Radiologi_Ranap=rs.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ranap");
                   Utang_Jasa_Medik_Petugas_Radiologi_Ranap=rs.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ranap");
                   Beban_Kso_Radiologi_Ranap=rs.getString("Beban_Kso_Radiologi_Ranap");
                   Utang_Kso_Radiologi_Ranap=rs.getString("Utang_Kso_Radiologi_Ranap");
                   HPP_Persediaan_Radiologi_Rawat_Inap=rs.getString("HPP_Persediaan_Radiologi_Rawat_Inap");
                   Beban_Jasa_Sarana_Radiologi_Ranap=rs.getString("Beban_Jasa_Sarana_Radiologi_Ranap");
                   Utang_Jasa_Sarana_Radiologi_Ranap=rs.getString("Utang_Jasa_Sarana_Radiologi_Ranap");
                   Beban_Jasa_Perujuk_Radiologi_Ranap=rs.getString("Beban_Jasa_Perujuk_Radiologi_Ranap");
                   Utang_Jasa_Perujuk_Radiologi_Ranap=rs.getString("Utang_Jasa_Perujuk_Radiologi_Ranap");
                   Beban_Jasa_Menejemen_Radiologi_Ranap=rs.getString("Beban_Jasa_Menejemen_Radiologi_Ranap");
                   Utang_Jasa_Menejemen_Radiologi_Ranap=rs.getString("Utang_Jasa_Menejemen_Radiologi_Ranap");
                   Obat_Ranap=rs.getString("Obat_Ranap");HPP_Obat_Rawat_Inap=rs.getString("HPP_Obat_Rawat_Inap");
                   Registrasi_Ranap=rs.getString("Registrasi_Ranap");
                   Tambahan_Ranap=rs.getString("Tambahan_Ranap");
                   Potongan_Ranap=rs.getString("Potongan_Ranap");
                   Retur_Obat_Ranap=rs.getString("Retur_Obat_Ranap");
                   Resep_Pulang_Ranap=rs.getString("Resep_Pulang_Ranap");
                   Kamar_Inap=rs.getString("Kamar_Inap");
                   Operasi_Ranap=rs.getString("Operasi_Ranap");
                   Beban_Jasa_Medik_Dokter_Operasi_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Operasi_Ranap");
                   Utang_Jasa_Medik_Dokter_Operasi_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Operasi_Ranap");
                   Beban_Jasa_Medik_Paramedis_Operasi_Ranap=rs.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ranap");
                   Utang_Jasa_Medik_Paramedis_Operasi_Ranap=rs.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ranap");
                   HPP_Obat_Operasi_Ranap=rs.getString("HPP_Obat_Operasi_Ranap");
                   Service_Ranap=rs.getString("Service_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            } 
            
            ps=koneksi.prepareStatement("select * from set_akun_ranap2");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                   Harian_Ranap=rs.getString("Harian_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
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

        TKd = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDetailPiutang = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        ppPilihSemua = new javax.swing.JMenuItem();
        ppUmbal = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelisi4 = new widget.panelisi();
        label33 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel11 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        label19 = new widget.Label();
        kdptg = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPetugas = new widget.Button();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        LCount1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        LCount2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        LCount3 = new javax.swing.JLabel();
        panelisi1 = new widget.panelisi();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel15 = new javax.swing.JLabel();
        BtnBayar = new widget.Button();
        BtnPrint = new widget.Button();
        BtnCari1 = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDetailPiutang.setBackground(new java.awt.Color(255, 255, 254));
        MnDetailPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDetailPiutang.setForeground(new java.awt.Color(50, 50, 50));
        MnDetailPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDetailPiutang.setText("Detail Piutang");
        MnDetailPiutang.setName("MnDetailPiutang"); // NOI18N
        MnDetailPiutang.setPreferredSize(new java.awt.Dimension(160, 26));
        MnDetailPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDetailPiutangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDetailPiutang);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Hilangkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(160, 26));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBersihkan);

        ppPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilihSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPilihSemua.setText("Pilih Semua");
        ppPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilihSemua.setName("ppPilihSemua"); // NOI18N
        ppPilihSemua.setPreferredSize(new java.awt.Dimension(160, 26));
        ppPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPilihSemua);

        ppUmbal.setBackground(new java.awt.Color(255, 255, 254));
        ppUmbal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUmbal.setForeground(new java.awt.Color(50, 50, 50));
        ppUmbal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUmbal.setText("Cek Umpan Balik");
        ppUmbal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUmbal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUmbal.setName("ppUmbal"); // NOI18N
        ppUmbal.setPreferredSize(new java.awt.Dimension(160, 26));
        ppUmbal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUmbalActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppUmbal);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ RVP Piutang BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setComponentPopupMenu(jPopupMenu1);
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbBangsalPropertyChange(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label33.setText("Tanggal :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label33);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);

        jLabel11.setText("Akun Bayar :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(73, 23));
        panelisi4.add(jLabel11);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.setPreferredSize(new java.awt.Dimension(195, 23));
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        panelisi4.add(AkunBayar);

        label19.setText("Petugas :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(58, 23));
        panelisi4.add(label19);

        kdptg.setEditable(false);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(100, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi4.add(kdptg);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(160, 23));
        panelisi4.add(nmptg);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('3');
        btnPetugas.setToolTipText("Alt+3");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        panelisi4.add(btnPetugas);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(99, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Belum Dibayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(87, 23));
        panelisi3.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(142, 23));
        panelisi3.add(LCount);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(50, 50, 50));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Dibayar :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi3.add(jLabel12);

        LCount1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount1.setForeground(new java.awt.Color(50, 50, 50));
        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(142, 23));
        panelisi3.add(LCount1);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(50, 50, 50));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Kerugian :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(jLabel13);

        LCount2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount2.setForeground(new java.awt.Color(50, 50, 50));
        LCount2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount2.setText("0");
        LCount2.setName("LCount2"); // NOI18N
        LCount2.setPreferredSize(new java.awt.Dimension(102, 23));
        panelisi3.add(LCount2);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(50, 50, 50));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Lebih Bayar :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(jLabel14);

        LCount3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount3.setForeground(new java.awt.Color(50, 50, 50));
        LCount3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount3.setText("0");
        LCount3.setName("LCount3"); // NOI18N
        LCount3.setPreferredSize(new java.awt.Dimension(102, 23));
        panelisi3.add(LCount3);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
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

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(50, 50, 50));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi1.add(jLabel15);

        BtnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnBayar.setMnemonic('B');
        BtnBayar.setText("Bayar");
        BtnBayar.setToolTipText("Alt+B");
        BtnBayar.setName("BtnBayar"); // NOI18N
        BtnBayar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBayarActionPerformed(evt);
            }
        });
        BtnBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBayarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBayar);

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

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari1.setMnemonic('C');
        BtnCari1.setText("Cari");
        BtnCari1.setToolTipText("Alt+C");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);

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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("truncate table temporary");
            row=tabMode.getRowCount();
            ttlpiutang=0;ttliur=0;ttlsudahdibayar=0;ttlsisapiutang=0;ttlinacbg=0;total=0;
            for(int i=0;i<row;i++){  
                sisapiutang=0;
                try {
                    sisapiutang=Double.parseDouble(tabMode.getValueAt(i,10).toString());
                } catch (Exception e) {
                    sisapiutang=0;
                }
                ttlpiutang=ttlpiutang+Double.parseDouble(tabMode.getValueAt(i,5).toString());
                ttliur=ttliur+Double.parseDouble(tabMode.getValueAt(i,6).toString());
                ttlsudahdibayar=ttlsudahdibayar+Double.parseDouble(tabMode.getValueAt(i,7).toString());
                ttlsisapiutang=ttlsisapiutang+Double.parseDouble(tabMode.getValueAt(i,8).toString());
                ttlinacbg=ttlinacbg+Double.parseDouble(tabMode.getValueAt(i,9).toString());
                total=total+sisapiutang;
                Sequel.menyimpan("temporary","'0','"+
                            tabMode.getValueAt(i,1).toString()+"','"+
                            tabMode.getValueAt(i,2).toString()+"','"+
                            tabMode.getValueAt(i,3).toString()+"','"+
                            tabMode.getValueAt(i,4).toString()+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,5).toString()))+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()))+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()))+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()))+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString()))+"','"+
                            Valid.SetAngka(sisapiutang)+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,11).toString()))+"','','','','','','','','','','','','','','','','','','','','','','','','','',''","RVP Piutang"); 
            }
            Sequel.menyimpan("temporary","'0','Total :','','','','"+Valid.SetAngka(ttlpiutang)+"','"+Valid.SetAngka(ttliur)+"','"+Valid.SetAngka(ttlsudahdibayar)+"','"+Valid.SetAngka(ttlsisapiutang)+"','"+Valid.SetAngka(ttlinacbg)+"','"+Valid.SetAngka(total)+"','','','','','','','','','','','','','','','','','','','','','','','','','','',''","RVP Piutangr"); 
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRVPPiutang.jasper","report","::[ Data Piutang BPJS Sebelum RVP ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBayar, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdptg.setText("");
        nmptg.setText("");
        tampil();

}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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
        tampil();
}//GEN-LAST:event_BtnCariKeyPressed

private void MnDetailPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDetailPiutangActionPerformed
     if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    }else{
         if(tbBangsal.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            status=Sequel.cariIsi("select status_lanjut from reg_periksa where no_rawat=?",tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());   
            if(status.equals("Ralan")){
                DlgBilingRalan billing=new DlgBilingRalan(null,false);
                billing.TNoRw.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());
                billing.isCek();
                billing.isRawat();
                if(Sequel.cariInteger("select count(no_rawat) from piutang_pasien where no_rawat=?",billing.TNoRw.getText())>0){
                    billing.setPiutang();
                }
                billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                billing.setLocationRelativeTo(internalFrame1);
                billing.setVisible(true); 
            }else if(status.equals("Ranap")){
                DlgBilingRanap billing=new DlgBilingRanap(null,false);
                billing.TNoRw.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString());            
                billing.isCek();
                billing.isRawat();
                billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                billing.setLocationRelativeTo(internalFrame1);
                billing.setVisible(true); 
            }
            this.setCursor(Cursor.getDefaultCursor());
         }else{
             JOptionPane.showMessageDialog(null,"Silahkan pilih data terlebih dahulu...!!");
         }                   
    } 
}//GEN-LAST:event_MnDetailPiutangActionPerformed

    private void BtnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBayarActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis..!!!!");
        }else if(total<=0){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih piutang yang mau dibayar..!!!!");
            TCari.requestFocus();
        }else if(nmptg.getText().trim().equals("")){
            Valid.textKosong(kdptg,"Petugas");
        }else if(AkunBayar.getSelectedItem().toString().trim().equals("")){
            Valid.textKosong(AkunBayar,"Akun Bayar");
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.AutoComitFalse();
            sukses=true;
            
            koderekening="";
            try {
                myObj = new FileReader("./cache/akunbayar.iyem");
                root = mapper.readTree(myObj);
                response = root.path("akunbayar");
                if(response.isArray()){
                   for(JsonNode list:response){
                       if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                            koderekening=list.path("KodeRek").asText();  
                       }
                   }
                }
                myObj.close();
            } catch (Exception e) {
                sukses=false;
            } 
            
            row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                if(tabMode.getValueAt(i,0).toString().equals("true")){
                    if(Sequel.menyimpantf("bayar_piutang","?,?,?,?,?,?,?","Data",7,new String[]{
                        Valid.SetTgl(Tanggal.getSelectedItem()+""),Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?",tabMode.getValueAt(i,1).toString()),
                        tabMode.getValueAt(i,10).toString(),"diverifikasi oleh "+kdptg.getText(),tabMode.getValueAt(i,1).toString(),koderekening,Piutang_BPJS_RVP
                    })==true){
                        Sequel.mengedit("piutang_pasien","no_rawat='"+tabMode.getValueAt(i,1).toString()+"'","status='Lunas'");
                        Sequel.mengedit("detail_piutang_pasien","no_rawat='"+tabMode.getValueAt(i,1).toString()+"'","sisapiutang=0");
                        
                        if(Valid.SetAngka(tabMode.getValueAt(i,11).toString())>=100){
                            Sequel.queryu("delete from tampjurnal");
                            if(Valid.SetAngka(tabMode.getValueAt(i,13).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Lebih_Bayar_Klaim_BPJS_RVP+"','LEBIH BAYAR BPJS','0','"+tabMode.getValueAt(i,13).toString()+"'","kredit=kredit+'"+tabMode.getValueAt(i,13).toString()+"'","kd_rek='"+Lebih_Bayar_Klaim_BPJS_RVP+"'"); 
                            }
                                
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+tabMode.getValueAt(i,8).toString()+"'","kredit=kredit+'"+tabMode.getValueAt(i,8).toString()+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+koderekening+"','"+AkunBayar.getSelectedItem()+"','"+tabMode.getValueAt(i,10).toString()+"','0'","debet=debet+'"+tabMode.getValueAt(i,10).toString()+"'","kd_rek='"+koderekening+"'"); 
                            sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","BAYAR PIUTANG BPJS"+", OLEH "+kdptg.getText());  
                        }else if(Valid.SetAngka(tabMode.getValueAt(i,11).toString())<100){
                            Sequel.queryu("delete from tampjurnal");
                            //tindakan ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,16).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,16).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,16).toString())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,16).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,16).toString())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,18).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban_Jasa_Medik_Dokter_Tindakan_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,18).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,18).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang_Jasa_Medik_Dokter_Tindakan_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,18).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,18).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,19).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban_Jasa_Medik_Paramedis_Tindakan_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,19).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,19).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang_Jasa_Medik_Paramedis_Tindakan_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,19).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,19).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,20).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban_KSO_Tindakan_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,20).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,20).toString())+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang_KSO_Tindakan_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,20).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,20).toString())+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,21).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban_Jasa_Menejemen_Tindakan_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,21).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,21).toString())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang_Jasa_Menejemen_Tindakan_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,21).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,21).toString())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,22).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,22).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,22).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','PENDAPATAN RAWAT JALAN','"+Valid.SetAngka(tabMode.getValueAt(i,22).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,22).toString())+"'","kd_rek='"+Tindakan_Ralan+"'");   
                            }
                            //tindakan ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,23).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban_Jasa_Sarana_Tindakan_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,23).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,23).toString())+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang_Jasa_Sarana_Tindakan_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,23).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,23).toString())+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,25).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban_Jasa_Medik_Dokter_Tindakan_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,25).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,25).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang_Jasa_Medik_Dokter_Tindakan_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,25).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,25).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,26).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban_Jasa_Medik_Paramedis_Tindakan_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,26).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,26).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang_Jasa_Medik_Paramedis_Tindakan_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,26).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,26).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,27).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban_KSO_Tindakan_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,27).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,27).toString())+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang_KSO_Tindakan_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,27).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,27).toString())+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,28).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban_Jasa_Menejemen_Tindakan_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,28).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,28).toString())+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang_Jasa_Menejemen_Tindakan_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,28).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,28).toString())+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,29).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,29).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,29).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','PENDAPATAN RAWAT INAP','"+Valid.SetAngka(tabMode.getValueAt(i,29).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,29).toString())+"'","kd_rek='"+Tindakan_Ranap+"'");   
                            }
                            //laborat ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,30).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ralan+"','Beban_Jasa_Sarana_Laborat_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,30).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,30).toString())+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ralan+"','Utang_Jasa_Sarana_Laborat_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,30).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,30).toString())+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,32).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ralan+"','Beban_Jasa_Perujuk_Laborat_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,32).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,32).toString())+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ralan+"','Utang_Jasa_Perujuk_Laborat_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,32).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,32).toString())+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,33).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"','Beban_Jasa_Medik_Dokter_Laborat_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,33).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,33).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang_Jasa_Medik_Dokter_Laborat_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,33).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,33).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,34).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"','Beban_Jasa_Medik_Petugas_Laborat_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,34).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,34).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"','Utang_Jasa_Medik_Petugas_Laborat_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,34).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,34).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,35).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ralan+"','Beban_Kso_Laborat_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,35).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,35).toString())+"'","kd_rek='"+Beban_Kso_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ralan+"','Utang_Kso_Laborat_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,35).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,35).toString())+"'","kd_rek='"+Utang_Kso_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,36).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ralan+"','Beban_Jasa_Menejemen_Laborat_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,36).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,36).toString())+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ralan+"','Utang_Jasa_Menejemen_Laborat_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,36).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,36).toString())+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,37).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,37).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,37).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','PENDAPATAN LABORAT RAWAT JALAN','"+Valid.SetAngka(tabMode.getValueAt(i,37).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,37).toString())+"'","kd_rek='"+Laborat_Ralan+"'");   
                            }
                            //laborat ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,38).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ranap+"','Beban_Jasa_Sarana_Laborat_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,38).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,38).toString())+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ranap+"','Utang_Jasa_Sarana_Laborat_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,38).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,38).toString())+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,40).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ranap+"','Beban_Jasa_Perujuk_Laborat_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,40).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,40).toString())+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ranap+"','Utang_Jasa_Perujuk_Laborat_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,40).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,40).toString())+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,41).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"','Beban_Jasa_Medik_Dokter_Laborat_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,41).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,41).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang_Jasa_Medik_Dokter_Laborat_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,41).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,41).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,42).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"','Beban_Jasa_Medik_Petugas_Laborat_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,42).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,42).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"','Utang_Jasa_Medik_Petugas_Laborat_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,42).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,42).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,43).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ranap+"','Beban_Kso_Laborat_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,43).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,43).toString())+"'","kd_rek='"+Beban_Kso_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ranap+"','Utang_Kso_Laborat_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,43).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,43).toString())+"'","kd_rek='"+Utang_Kso_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,44).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ranap+"','Beban_Jasa_Menejemen_Laborat_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,44).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,44).toString())+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ranap+"','Utang_Jasa_Menejemen_Laborat_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,44).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,44).toString())+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,45).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,45).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,45).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','PENDAPATAN LABORAT RAWAT INAP','"+Valid.SetAngka(tabMode.getValueAt(i,45).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,45).toString())+"'","kd_rek='"+Laborat_Ranap+"'");   
                            }
                            //radiologi ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,46).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ralan+"','Beban_Jasa_Sarana_Radiologi_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,46).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,46).toString())+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ralan+"','Utang_Jasa_Sarana_Radiologi_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,46).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,46).toString())+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,48).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ralan+"','Beban_Jasa_Perujuk_Radiologi_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,48).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,48).toString())+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ralan+"','Utang_Jasa_Perujuk_Radiologi_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,48).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,48).toString())+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,49).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"','Beban_Jasa_Medik_Dokter_Radiologi_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,49).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,49).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Utang_Jasa_Medik_Dokter_Radiologi_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,49).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,49).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,50).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"','Beban_Jasa_Medik_Petugas_Radiologi_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,50).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,50).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"','Utang_Jasa_Medik_Petugas_Radiologi_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,50).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,50).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,51).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ralan+"','Beban_Kso_Radiologi_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,51).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,51).toString())+"'","kd_rek='"+Beban_Kso_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ralan+"','Utang_Kso_Radiologi_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,51).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,51).toString())+"'","kd_rek='"+Utang_Kso_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,52).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ralan+"','Beban_Jasa_Menejemen_Radiologi_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,52).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,52).toString())+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ralan+"','Utang_Jasa_Menejemen_Radiologi_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,52).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,52).toString())+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,53).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,53).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,53).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Radiologi_Ralan+"','PENDAPATAN RADIOLOGI RAWAT JALAN','"+Valid.SetAngka(tabMode.getValueAt(i,53).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,53).toString())+"'","kd_rek='"+Radiologi_Ralan+"'");   
                            }
                            //radiologi ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,54).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ranap+"','Beban_Jasa_Sarana_Radiologi_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,54).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,54).toString())+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ranap+"','Utang_Jasa_Sarana_Radiologi_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,54).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,54).toString())+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,56).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ranap+"','Beban_Jasa_Perujuk_Radiologi_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,56).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,56).toString())+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ranap+"','Utang_Jasa_Perujuk_Radiologi_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,56).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,56).toString())+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,57).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"','Beban_Jasa_Medik_Dokter_Radiologi_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,57).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,57).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"','Utang_Jasa_Medik_Dokter_Radiologi_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,57).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,57).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,58).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"','Beban_Jasa_Medik_Petugas_Radiologi_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,58).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,58).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"','Utang_Jasa_Medik_Petugas_Radiologi_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,58).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,58).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,59).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ranap+"','Beban_Kso_Radiologi_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,59).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,59).toString())+"'","kd_rek='"+Beban_Kso_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ranap+"','Utang_Kso_Radiologi_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,59).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,59).toString())+"'","kd_rek='"+Utang_Kso_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,60).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ranap+"','Beban_Jasa_Menejemen_Radiologi_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,60).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,60).toString())+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ranap+"','Utang_Jasa_Menejemen_Radiologi_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,60).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,60).toString())+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,61).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,61).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,61).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Radiologi_Ranap+"','PENDAPATAN RADIOLOGI RAWAT INAP','"+Valid.SetAngka(tabMode.getValueAt(i,61).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,61).toString())+"'","kd_rek='"+Radiologi_Ranap+"'");   
                            }
                            //operasi ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,62).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"','Beban_Jasa_Medik_Dokter_Operasi_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,62).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,62).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"','Utang_Jasa_Medik_Dokter_Operasi_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,62).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,62).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,63).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"','Beban_Jasa_Medik_Paramedis_Operasi_Ralan','0','"+Valid.SetAngka(tabMode.getValueAt(i,63).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,63).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"','Utang_Jasa_Medik_Paramedis_Operasi_Ralan','"+Valid.SetAngka(tabMode.getValueAt(i,63).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,63).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,65).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG RAWAT JALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,64).toString())+Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,64).toString())+Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Operasi_Ralan+"','PENDAPATAN OPERASI RAWAT JALAN','"+(Valid.SetAngka(tabMode.getValueAt(i,64).toString())+Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,64).toString())+Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"'","kd_rek='"+Operasi_Ralan+"'");   
                            }
                            //operasi ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,66).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"','Beban_Jasa_Medik_Dokter_Operasi_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,66).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,66).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang_Jasa_Medik_Dokter_Operasi_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,66).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,66).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,67).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"','Beban_Jasa_Medik_Paramedis_Operasi_Ranap','0','"+Valid.SetAngka(tabMode.getValueAt(i,67).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,67).toString())+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"','Utang_Jasa_Medik_Paramedis_Operasi_Ranap','"+Valid.SetAngka(tabMode.getValueAt(i,67).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,67).toString())+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,69).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+(Valid.SetAngka(tabMode.getValueAt(i,68).toString())+Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,68).toString())+Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','PENDAPATAN OPERASI RAWAT INAP','"+(Valid.SetAngka(tabMode.getValueAt(i,68).toString())+Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,68).toString())+Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kd_rek='"+Operasi_Ranap+"'");   
                            }
                            //kamar
                            if(Valid.SetAngka(tabMode.getValueAt(i,78).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,78).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,78).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Kamar_Inap+"','PENDAPATAN KAMAR INAP','"+Valid.SetAngka(tabMode.getValueAt(i,78).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,78).toString())+"'","kd_rek='"+Kamar_Inap+"'");   
                            }
                            //harian
                            if(Valid.SetAngka(tabMode.getValueAt(i,80).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,80).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,80).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Harian_Ranap+"','PENDAPATAN HARIAN KAMAR INAP','"+Valid.SetAngka(tabMode.getValueAt(i,80).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,80).toString())+"'","kd_rek='"+Harian_Ranap+"'");   
                            }
                            //registrasi
                            if(Valid.SetAngka(tabMode.getValueAt(i,81).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,81).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,81).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                    Sequel.menyimpan("tampjurnal","'"+Registrasi_Ralan+"','PENDAPATAN REGISTRASI RALAN','"+Valid.SetAngka(tabMode.getValueAt(i,81).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,81).toString())+"'","kd_rek='"+Registrasi_Ralan+"'");
                                }else{
                                    Sequel.menyimpan("tampjurnal","'"+Registrasi_Ranap+"','PENDAPATAN REGISTRASI RALAN','"+Valid.SetAngka(tabMode.getValueAt(i,81).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,81).toString())+"'","kd_rek='"+Registrasi_Ranap+"'");
                                }
                            }
                            //tambahan biaya
                            if(Valid.SetAngka(tabMode.getValueAt(i,76).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,76).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,76).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                    Sequel.menyimpan("tampjurnal","'"+Tambahan_Ralan+"','PENDAPATAN TAMBAHAN RALAN','"+Valid.SetAngka(tabMode.getValueAt(i,76).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,76).toString())+"'","kd_rek='"+Tambahan_Ralan+"'");
                                }else{
                                    Sequel.menyimpan("tampjurnal","'"+Tambahan_Ranap+"','PENDAPATAN TAMBAHAN RANAP','"+Valid.SetAngka(tabMode.getValueAt(i,76).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,76).toString())+"'","kd_rek='"+Tambahan_Ranap+"'");
                                }
                            }
                            //potongan biaya
                            if(Valid.SetAngka(tabMode.getValueAt(i,77).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+Valid.SetAngka(tabMode.getValueAt(i,77).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,77).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                    Sequel.menyimpan("tampjurnal","'"+Potongan_Ralan+"','POTONGAN RALAN','0','"+Valid.SetAngka(tabMode.getValueAt(i,77).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,77).toString())+"'","kd_rek='"+Potongan_Ralan+"'");
                                }else{
                                    Sequel.menyimpan("tampjurnal","'"+Potongan_Ranap+"','POTONGAN RANAP','0','"+Valid.SetAngka(tabMode.getValueAt(i,77).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,77).toString())+"'","kd_rek='"+Potongan_Ranap+"'");
                                }
                            }
                            //resep pulang
                            if(Valid.SetAngka(tabMode.getValueAt(i,79).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,79).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,79).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Resep_Pulang_Ranap+"','PENDAPATAN RESEP PULANG','"+Valid.SetAngka(tabMode.getValueAt(i,79).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,79).toString())+"'","kd_rek='"+Resep_Pulang_Ranap+"'");   
                            }
                            //obat langsung
                            if(Valid.SetAngka(tabMode.getValueAt(i,70).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,70).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,70).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                    Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','OBAT LANGSUNG RALAN','"+Valid.SetAngka(tabMode.getValueAt(i,70).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,70).toString())+"'","kd_rek='"+Obat_Ralan+"'");
                                }else{
                                    Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','OBAT LANGSUNG RANAP','"+Valid.SetAngka(tabMode.getValueAt(i,70).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,70).toString())+"'","kd_rek='"+Obat_Ranap+"'");
                                }
                            }
                            //obat ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,71).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,71).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,71).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','PENDAPATAN OBAT RALAN','"+Valid.SetAngka(tabMode.getValueAt(i,71).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,71).toString())+"'","kd_rek='"+Obat_Ralan+"'");   
                            }
                            //obat ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,73).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,73).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,73).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','PENDAPATAN OBAT RANAP','"+Valid.SetAngka(tabMode.getValueAt(i,73).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,73).toString())+"'","kd_rek='"+Obat_Ranap+"'");   
                            }
                            //retur obat ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,75).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+Valid.SetAngka(tabMode.getValueAt(i,75).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,75).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Retur_Obat_Ranap+"','PENDAPATAN OBAT RANAP','0','"+Valid.SetAngka(tabMode.getValueAt(i,75).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,75).toString())+"'","kd_rek='"+Retur_Obat_Ranap+"'");   
                            }
                            //service
                            if(Valid.SetAngka(tabMode.getValueAt(i,82).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+Valid.SetAngka(tabMode.getValueAt(i,82).toString())+"'","kredit=kredit+'"+Valid.SetAngka(tabMode.getValueAt(i,82).toString())+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Service_Ranap+"','PENDAPATAN SERVICE INAP','"+Valid.SetAngka(tabMode.getValueAt(i,82).toString())+"','0'","debet=debet+'"+Valid.SetAngka(tabMode.getValueAt(i,82).toString())+"'","kd_rek='"+Service_Ranap+"'");   
                            }
                            //jurnal pembatalan beban, utang, piutang, pendapatan
                            sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","RVP PIUTANG PASIEN BPJS, OLEH "+kdptg.getText());     
                            
                            if(sukses==true){
                                Sequel.queryu("delete from tampjurnal");
                                //tindakan ralan
                                persenbayar=(Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100);
                                if(Valid.SetAngka(tabMode.getValueAt(i,16).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,16).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,16).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,16).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,16).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,18).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban_Jasa_Medik_Dokter_Tindakan_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang_Jasa_Medik_Dokter_Tindakan_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,19).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban_Jasa_Medik_Paramedis_Tindakan_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang_Jasa_Medik_Paramedis_Tindakan_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,20).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban_KSO_Tindakan_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang_KSO_Tindakan_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,21).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban_Jasa_Menejemen_Tindakan_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang_Jasa_Menejemen_Tindakan_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,22).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','PENDAPATAN RAWAT JALAN','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"'","kd_rek='"+Tindakan_Ralan+"'");   
                                }
                                //tindakan ranap
                                if(Valid.SetAngka(tabMode.getValueAt(i,23).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban_Jasa_Sarana_Tindakan_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,23).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,23).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang_Jasa_Sarana_Tindakan_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,23).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,23).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,25).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban_Jasa_Medik_Dokter_Tindakan_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang_Jasa_Medik_Dokter_Tindakan_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,26).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban_Jasa_Medik_Paramedis_Tindakan_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang_Jasa_Medik_Paramedis_Tindakan_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,27).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban_KSO_Tindakan_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang_KSO_Tindakan_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,28).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban_Jasa_Menejemen_Tindakan_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang_Jasa_Menejemen_Tindakan_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,29).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','PENDAPATAN RAWAT INAP','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"'","kd_rek='"+Tindakan_Ranap+"'");   
                                }
                                //laborat ralan
                                if(Valid.SetAngka(tabMode.getValueAt(i,30).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ralan+"','Beban_Jasa_Sarana_Laborat_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,30).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,30).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ralan+"','Utang_Jasa_Sarana_Laborat_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,30).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,30).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,32).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ralan+"','Beban_Jasa_Perujuk_Laborat_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ralan+"','Utang_Jasa_Perujuk_Laborat_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,33).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"','Beban_Jasa_Medik_Dokter_Laborat_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang_Jasa_Medik_Dokter_Laborat_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,34).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"','Beban_Jasa_Medik_Petugas_Laborat_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"','Utang_Jasa_Medik_Petugas_Laborat_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,35).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ralan+"','Beban_Kso_Laborat_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"'","kd_rek='"+Beban_Kso_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ralan+"','Utang_Kso_Laborat_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"'","kd_rek='"+Utang_Kso_Laborat_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,36).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ralan+"','Beban_Jasa_Menejemen_Laborat_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ralan+"','Utang_Jasa_Menejemen_Laborat_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,37).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','PENDAPATAN LABORAT RAWAT JALAN','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"'","kd_rek='"+Laborat_Ralan+"'");   
                                }
                                //laborat ranap
                                if(Valid.SetAngka(tabMode.getValueAt(i,38).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ranap+"','Beban_Jasa_Sarana_Laborat_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,38).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,38).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ranap+"','Utang_Jasa_Sarana_Laborat_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,38).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,38).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,40).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ranap+"','Beban_Jasa_Perujuk_Laborat_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ranap+"','Utang_Jasa_Perujuk_Laborat_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,41).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"','Beban_Jasa_Medik_Dokter_Laborat_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang_Jasa_Medik_Dokter_Laborat_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,42).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"','Beban_Jasa_Medik_Petugas_Laborat_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"','Utang_Jasa_Medik_Petugas_Laborat_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,43).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ranap+"','Beban_Kso_Laborat_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"'","kd_rek='"+Beban_Kso_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ranap+"','Utang_Kso_Laborat_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"'","kd_rek='"+Utang_Kso_Laborat_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,44).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ranap+"','Beban_Jasa_Menejemen_Laborat_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ranap+"','Utang_Jasa_Menejemen_Laborat_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,45).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','PENDAPATAN LABORAT RAWAT INAP','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"'","kd_rek='"+Laborat_Ranap+"'");   
                                }
                                //radiologi ralan
                                if(Valid.SetAngka(tabMode.getValueAt(i,46).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ralan+"','Beban_Jasa_Sarana_Radiologi_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,46).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,46).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ralan+"','Utang_Jasa_Sarana_Radiologi_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,46).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,46).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,48).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ralan+"','Beban_Jasa_Perujuk_Radiologi_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ralan+"','Utang_Jasa_Perujuk_Radiologi_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,49).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"','Beban_Jasa_Medik_Dokter_Radiologi_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Utang_Jasa_Medik_Dokter_Radiologi_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,50).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"','Beban_Jasa_Medik_Petugas_Radiologi_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"','Utang_Jasa_Medik_Petugas_Radiologi_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,51).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ralan+"','Beban_Kso_Radiologi_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"'","kd_rek='"+Beban_Kso_Radiologi_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ralan+"','Utang_Kso_Radiologi_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"'","kd_rek='"+Utang_Kso_Radiologi_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,52).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ralan+"','Beban_Jasa_Menejemen_Radiologi_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ralan+"','Utang_Jasa_Menejemen_Radiologi_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,53).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Radiologi_Ralan+"','PENDAPATAN RADIOLOGI RAWAT JALAN','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"'","kd_rek='"+Radiologi_Ralan+"'");   
                                }
                                //radiologi ranap
                                if(Valid.SetAngka(tabMode.getValueAt(i,54).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ranap+"','Beban_Jasa_Sarana_Radiologi_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,54).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,54).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ranap+"','Utang_Jasa_Sarana_Radiologi_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,54).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,54).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,56).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ranap+"','Beban_Jasa_Perujuk_Radiologi_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ranap+"','Utang_Jasa_Perujuk_Radiologi_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,57).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"','Beban_Jasa_Medik_Dokter_Radiologi_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"','Utang_Jasa_Medik_Dokter_Radiologi_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,58).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"','Beban_Jasa_Medik_Petugas_Radiologi_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"','Utang_Jasa_Medik_Petugas_Radiologi_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,59).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ranap+"','Beban_Kso_Radiologi_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"'","kd_rek='"+Beban_Kso_Radiologi_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ranap+"','Utang_Kso_Radiologi_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"'","kd_rek='"+Utang_Kso_Radiologi_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,60).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ranap+"','Beban_Jasa_Menejemen_Radiologi_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ranap+"','Utang_Jasa_Menejemen_Radiologi_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,61).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Radiologi_Ranap+"','PENDAPATAN RADIOLOGI RAWAT INAP','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"'","kd_rek='"+Radiologi_Ranap+"'");   
                                }
                                //operasi ralan
                                if(Valid.SetAngka(tabMode.getValueAt(i,62).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"','Beban_Jasa_Medik_Dokter_Operasi_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"','Utang_Jasa_Medik_Dokter_Operasi_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,63).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"','Beban_Jasa_Medik_Paramedis_Operasi_Ralan','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,63).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,63).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"','Utang_Jasa_Medik_Paramedis_Operasi_Ralan','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,63).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,63).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,65).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG RAWAT JALAN','"+(persenbayar *(Valid.SetAngka(tabMode.getValueAt(i,64).toString())+Valid.SetAngka(tabMode.getValueAt(i,65).toString())))+"','0'","debet=debet+'"+(persenbayar *(Valid.SetAngka(tabMode.getValueAt(i,64).toString())+Valid.SetAngka(tabMode.getValueAt(i,65).toString())))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Operasi_Ralan+"','PENDAPATAN OPERASI RAWAT JALAN','0','"+(persenbayar *(Valid.SetAngka(tabMode.getValueAt(i,64).toString())+Valid.SetAngka(tabMode.getValueAt(i,65).toString())))+"'","kredit=kredit+'"+(persenbayar *(Valid.SetAngka(tabMode.getValueAt(i,64).toString())+Valid.SetAngka(tabMode.getValueAt(i,65).toString())))+"'","kd_rek='"+Operasi_Ralan+"'");   
                                }
                                //operasi ranap
                                if(Valid.SetAngka(tabMode.getValueAt(i,66).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"','Beban_Jasa_Medik_Dokter_Operasi_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang_Jasa_Medik_Dokter_Operasi_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,67).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"','Beban_Jasa_Medik_Paramedis_Operasi_Ranap','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,67).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,67).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"','Utang_Jasa_Medik_Paramedis_Operasi_Ranap','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,67).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,67).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"'");   
                                }
                                if(Valid.SetAngka(tabMode.getValueAt(i,69).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *(Valid.SetAngka(tabMode.getValueAt(i,68).toString())+Valid.SetAngka(tabMode.getValueAt(i,69).toString())))+"','0'","debet=debet+'"+(persenbayar *(Valid.SetAngka(tabMode.getValueAt(i,68).toString())+Valid.SetAngka(tabMode.getValueAt(i,69).toString())))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','PENDAPATAN OPERASI RAWAT INAP','0','"+(persenbayar *(Valid.SetAngka(tabMode.getValueAt(i,68).toString())+Valid.SetAngka(tabMode.getValueAt(i,69).toString())))+"'","kredit=kredit+'"+(persenbayar *(Valid.SetAngka(tabMode.getValueAt(i,68).toString())+Valid.SetAngka(tabMode.getValueAt(i,69).toString())))+"'","kd_rek='"+Operasi_Ranap+"'");   
                                }
                                //kamar
                                if(Valid.SetAngka(tabMode.getValueAt(i,78).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Kamar_Inap+"','PENDAPATAN KAMAR INAP','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"'","kd_rek='"+Kamar_Inap+"'");   
                                }
                                //harian
                                if(Valid.SetAngka(tabMode.getValueAt(i,80).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Harian_Ranap+"','PENDAPATAN HARIAN KAMAR INAP','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kd_rek='"+Harian_Ranap+"'");   
                                }
                                //registrasi
                                if(Valid.SetAngka(tabMode.getValueAt(i,81).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,81).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,81).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                        Sequel.menyimpan("tampjurnal","'"+Registrasi_Ralan+"','PENDAPATAN REGISTRASI RALAN','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,81).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,81).toString()))+"'","kd_rek='"+Registrasi_Ralan+"'");
                                    }else{
                                        Sequel.menyimpan("tampjurnal","'"+Registrasi_Ranap+"','PENDAPATAN REGISTRASI RALAN','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,81).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,81).toString()))+"'","kd_rek='"+Registrasi_Ranap+"'");
                                    }
                                }
                                //tambahan biaya
                                if(Valid.SetAngka(tabMode.getValueAt(i,76).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                        Sequel.menyimpan("tampjurnal","'"+Tambahan_Ralan+"','PENDAPATAN TAMBAHAN RALAN','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kd_rek='"+Tambahan_Ralan+"'");
                                    }else{
                                        Sequel.menyimpan("tampjurnal","'"+Tambahan_Ranap+"','PENDAPATAN TAMBAHAN RANAP','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kd_rek='"+Tambahan_Ranap+"'");
                                    }
                                }
                                //potongan biaya
                                if(Valid.SetAngka(tabMode.getValueAt(i,77).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                        Sequel.menyimpan("tampjurnal","'"+Potongan_Ralan+"','POTONGAN RALAN','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kd_rek='"+Potongan_Ralan+"'");
                                    }else{
                                        Sequel.menyimpan("tampjurnal","'"+Potongan_Ranap+"','POTONGAN RANAP','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kd_rek='"+Potongan_Ranap+"'");
                                    }
                                }
                                //resep pulang
                                if(Valid.SetAngka(tabMode.getValueAt(i,79).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Resep_Pulang_Ranap+"','PENDAPATAN RESEP PULANG','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"'","kd_rek='"+Resep_Pulang_Ranap+"'");   
                                }
                                //obat langsung
                                if(Valid.SetAngka(tabMode.getValueAt(i,70).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                        Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','OBAT LANGSUNG RALAN','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kd_rek='"+Obat_Ralan+"'");
                                    }else{
                                        Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','OBAT LANGSUNG RANAP','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kd_rek='"+Obat_Ranap+"'");
                                    }
                                }
                                //obat ralan
                                if(Valid.SetAngka(tabMode.getValueAt(i,71).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,71).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,71).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','PENDAPATAN OBAT RALAN','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,71).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,71).toString()))+"'","kd_rek='"+Obat_Ralan+"'");   
                                }
                                //obat ranap
                                if(Valid.SetAngka(tabMode.getValueAt(i,73).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,73).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,73).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','PENDAPATAN OBAT RANAP','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,73).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,73).toString()))+"'","kd_rek='"+Obat_Ranap+"'");   
                                }
                                //retur obat ranap
                                if(Valid.SetAngka(tabMode.getValueAt(i,75).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Retur_Obat_Ranap+"','PENDAPATAN OBAT RANAP','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kd_rek='"+Retur_Obat_Ranap+"'");   
                                }
                                //service ranap
                                if(Valid.SetAngka(tabMode.getValueAt(i,82).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,82).toString()))+"','0'","debet=debet+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,82).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+Service_Ranap+"','PENDAPATAN SERVICE INAP','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,82).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,82).toString()))+"'","kd_rek='"+Service_Ranap+"'");   
                                }
                                //jurnal ulang penyusutan beban, utang, piutang, pendapatan
                                sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","RVP PIUTANG PASIEN BPJS, OLEH "+kdptg.getText());     
                                if(sukses==true){
                                    //jurnal kerugian 
                                    Sequel.queryu("delete from tampjurnal");
                                    if(Valid.SetAngka(tabMode.getValueAt(i,12).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Kerugian_Klaim_BPJS_RVP+"','KERUGIAN KLAIM BPJS','"+tabMode.getValueAt(i,12).toString()+"','0'","debet=debet+'"+tabMode.getValueAt(i,12).toString()+"'","kd_rek='"+Kerugian_Klaim_BPJS_RVP+"'");  
                                        if(Valid.SetAngka(tabMode.getValueAt(i,17).toString())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP RAWAT JALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,17).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,17).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");  
                                        }
                                        if(Valid.SetAngka(tabMode.getValueAt(i,24).toString())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP RAWAT INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,24).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,24).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");  
                                        }
                                        if(Valid.SetAngka(tabMode.getValueAt(i,31).toString())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_Jalan+"','HPP LAB RAWAT JALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,31).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,31).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_Jalan+"'");  
                                        }
                                        if(Valid.SetAngka(tabMode.getValueAt(i,39).toString())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_inap+"','HPP LAB RAWAT INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,39).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,39).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_inap+"'");  
                                        }
                                        if(Valid.SetAngka(tabMode.getValueAt(i,47).toString())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Radiologi_Rawat_Jalan+"','HPP RADIOLOGI RAWAT JALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,47).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,47).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Persediaan_Radiologi_Rawat_Jalan+"'");  
                                        }
                                        if(Valid.SetAngka(tabMode.getValueAt(i,55).toString())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Radiologi_Rawat_Inap+"','HPP RADIOLOGI RAWAT INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,55).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,55).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Persediaan_Radiologi_Rawat_Inap+"'");  
                                        }
                                        if(Valid.SetAngka(tabMode.getValueAt(i,64).toString())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ralan+"','HPP OPERASI RAWAT JALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,64).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,64).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Obat_Operasi_Ralan+"'");  
                                        }
                                        if(Valid.SetAngka(tabMode.getValueAt(i,68).toString())>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ranap+"','HPP OPERASI RAWAT INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,68).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,68).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Obat_Operasi_Ranap+"'");  
                                        }
                                        if(Valid.SetAngka(tabMode.getValueAt(i,79).toString())>0){
                                            Sequel.menyimpan("tampjurnal","'"+Resep_Pulang_Ranap+"','Resep Pulang Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,79).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,79).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+Resep_Pulang_Ranap+"'");  
                                        }
                                        rugihppralan=(Valid.SetAngka(tabMode.getValueAt(i,72).toString())-(Valid.SetAngka(tabMode.getValueAt(i,71).toString())*persenbayar));
                                        if(rugihppralan>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Rawat_Jalan+"','HPP Obat Ralan','0','"+rugihppralan+"'","kredit=kredit+'"+rugihppralan+"'","kd_rek='"+HPP_Obat_Rawat_Jalan+"'");  
                                        }
                                        rugihppranap=((Valid.SetAngka(tabMode.getValueAt(i,74).toString())-Valid.SetAngka(tabMode.getValueAt(i,75).toString()))-((Valid.SetAngka(tabMode.getValueAt(i,70).toString())+Valid.SetAngka(tabMode.getValueAt(i,73).toString())-Valid.SetAngka(tabMode.getValueAt(i,75).toString()))*persenbayar));    
                                        if(rugihppranap>0){
                                            Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Rawat_Inap+"','HPP Obat Ranap','0','"+rugihppranap+"'","kredit=kredit+'"+rugihppranap+"'","kd_rek='"+HPP_Obat_Rawat_Inap+"'");  
                                        }
                                    }

                                    Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,8).toString()))+"'","kredit=kredit+'"+(persenbayar *Valid.SetAngka(tabMode.getValueAt(i,8).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                    Sequel.menyimpan("tampjurnal","'"+koderekening+"','"+AkunBayar.getSelectedItem()+"','"+tabMode.getValueAt(i,10).toString()+"','0'","debet=debet+'"+tabMode.getValueAt(i,10).toString()+"'","kd_rek='"+koderekening+"'"); 
                                    sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","RVP PIUTANG BPJS"+", OLEH "+kdptg.getText()); 
                                    
                                    if(sukses==true){
                                        //update RVP Rawat jalan
                                        if(Sequel.queryutf("update rawat_jl_dr set material=material*"+persenbayar+",tarif_tindakandr=tarif_tindakandr*"+persenbayar+",kso=kso*"+persenbayar+",menejemen=menejemen*"+persenbayar+",biaya_rawat=biaya_rawat*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        if(Sequel.queryutf("update rawat_jl_pr set material=material*"+persenbayar+",tarif_tindakanpr=tarif_tindakanpr*"+persenbayar+",kso=kso*"+persenbayar+",menejemen=menejemen*"+persenbayar+",biaya_rawat=biaya_rawat*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        if(Sequel.queryutf("update rawat_jl_drpr set material=material*"+persenbayar+",tarif_tindakandr=tarif_tindakandr*"+persenbayar+",tarif_tindakanpr=tarif_tindakanpr*"+persenbayar+",kso=kso*"+persenbayar+",menejemen=menejemen*"+persenbayar+",biaya_rawat=biaya_rawat*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update RVP Rawat inap
                                        if(Sequel.queryutf("update rawat_inap_dr set material=material*"+persenbayar+",tarif_tindakandr=tarif_tindakandr*"+persenbayar+",kso=kso*"+persenbayar+",menejemen=menejemen*"+persenbayar+",biaya_rawat=biaya_rawat*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        if(Sequel.queryutf("update rawat_inap_pr set material=material*"+persenbayar+",tarif_tindakanpr=tarif_tindakanpr*"+persenbayar+",kso=kso*"+persenbayar+",menejemen=menejemen*"+persenbayar+",biaya_rawat=biaya_rawat*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        if(Sequel.queryutf("update rawat_inap_drpr set material=material*"+persenbayar+",tarif_tindakandr=tarif_tindakandr*"+persenbayar+",tarif_tindakanpr=tarif_tindakanpr*"+persenbayar+",kso=kso*"+persenbayar+",menejemen=menejemen*"+persenbayar+",biaya_rawat=biaya_rawat*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update rvp laborat
                                        if(Sequel.queryutf("update periksa_lab set bagian_rs=bagian_rs*"+persenbayar+",tarif_perujuk=tarif_perujuk*"+persenbayar+",tarif_tindakan_dokter=tarif_tindakan_dokter*"+persenbayar+",tarif_tindakan_petugas=tarif_tindakan_petugas*"+persenbayar+",kso=kso*"+persenbayar+",menejemen=menejemen*"+persenbayar+",biaya=biaya*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        if(Sequel.queryutf("update detail_periksa_lab set bagian_rs=bagian_rs*"+persenbayar+",bagian_perujuk=bagian_perujuk*"+persenbayar+",bagian_dokter=bagian_dokter*"+persenbayar+",bagian_laborat=bagian_laborat*"+persenbayar+",kso=kso*"+persenbayar+",menejemen=menejemen*"+persenbayar+",biaya_item=biaya_item*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update rvp radiologi
                                        if(Sequel.queryutf("update periksa_radiologi set bagian_rs=bagian_rs*"+persenbayar+",tarif_perujuk=tarif_perujuk*"+persenbayar+",tarif_tindakan_dokter=tarif_tindakan_dokter*"+persenbayar+",tarif_tindakan_petugas=tarif_tindakan_petugas*"+persenbayar+",kso=kso*"+persenbayar+",menejemen=menejemen*"+persenbayar+",biaya=biaya*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update rvp operasi
                                        if(Sequel.queryutf("update operasi set biayaoperator1=biayaoperator1*"+persenbayar+",biayaoperator2=biayaoperator2*"+persenbayar+",biayaoperator3=biayaoperator3*"+persenbayar+",biayaasisten_operator1=biayaasisten_operator1*"+persenbayar+",biayaasisten_operator2=biayaasisten_operator2*"+persenbayar+",biayaasisten_operator3=biayaasisten_operator3*"+persenbayar+",biayainstrumen=biayainstrumen*"+persenbayar+",biayadokter_anak=biayadokter_anak*"+persenbayar+",biayaperawaat_resusitas=biayaperawaat_resusitas*"+persenbayar+",biayadokter_anestesi=biayadokter_anestesi*"+persenbayar+",biayaasisten_anestesi=biayaasisten_anestesi*"+persenbayar+",biayaasisten_anestesi2=biayaasisten_anestesi2*"+persenbayar+",biayabidan=biayabidan*"+persenbayar+",biayabidan2=biayabidan2*"+persenbayar+",biayabidan3=biayabidan3*"+persenbayar+",biayaperawat_luar=biayaperawat_luar*"+persenbayar+",biayaalat=biayaalat*"+persenbayar+",biayasewaok=biayasewaok*"+persenbayar+",akomodasi=akomodasi*"+persenbayar+",bagian_rs=bagian_rs*"+persenbayar+",biaya_omloop=biaya_omloop*"+persenbayar+",biaya_omloop2=biaya_omloop2*"+persenbayar+",biaya_omloop3=biaya_omloop3*"+persenbayar+",biaya_omloop4=biaya_omloop4*"+persenbayar+",biaya_omloop5=biaya_omloop5*"+persenbayar+",biayasarpras=biayasarpras*"+persenbayar+",biaya_dokter_pjanak=biaya_dokter_pjanak*"+persenbayar+",biaya_dokter_umum=biaya_dokter_umum*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update rvp kamar
                                        if(Sequel.queryutf("update kamar_inap set trf_kamar=trf_kamar*"+persenbayar+",ttl_biaya=ttl_biaya*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update rvp registrasi
                                        if(Sequel.queryutf("update reg_periksa set biaya_reg=biaya_reg*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update rvp tambahan biaya
                                        if(Sequel.queryutf("update tambahan_biaya set besar_biaya=besar_biaya*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update rvp pengurangan biaya
                                        if(Sequel.queryutf("update pengurangan_biaya set besar_pengurangan=besar_pengurangan*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update rvp obat langsung
                                        if(Sequel.queryutf("update tagihan_obat_langsung set besar_tagihan=besar_tagihan*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                        //update rvp obat
                                        if(Sequel.queryutf("update detail_pemberian_obat set biaya_obat=biaya_obat*"+persenbayar+",total=total*"+persenbayar+",embalase=embalase*"+persenbayar+",tuslah=tuslah*"+persenbayar+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                            sukses=false;
                                        }
                                    }
                                }
                            }
                        }       

                        if(sukses==true){
                            if(Sequel.menyimpantf2("rvp_klaim_bpjs","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",82,new String[]{
                                    tabMode.getValueAt(i,1).toString(),Valid.SetTgl(Tanggal.getSelectedItem()+""),kdptg.getText(),tabMode.getValueAt(i,5).toString(),tabMode.getValueAt(i,6).toString(),tabMode.getValueAt(i,7).toString(),tabMode.getValueAt(i,8).toString(),
                                    tabMode.getValueAt(i,9).toString(),tabMode.getValueAt(i,10).toString(),tabMode.getValueAt(i,11).toString(),tabMode.getValueAt(i,12).toString(),tabMode.getValueAt(i,13).toString(),tabMode.getValueAt(i,16).toString(),
                                    tabMode.getValueAt(i,17).toString(),tabMode.getValueAt(i,18).toString(),tabMode.getValueAt(i,19).toString(),tabMode.getValueAt(i,20).toString(),tabMode.getValueAt(i,21).toString(),tabMode.getValueAt(i,22).toString(),
                                    tabMode.getValueAt(i,23).toString(),tabMode.getValueAt(i,24).toString(),tabMode.getValueAt(i,25).toString(),tabMode.getValueAt(i,26).toString(),tabMode.getValueAt(i,27).toString(),tabMode.getValueAt(i,28).toString(),
                                    tabMode.getValueAt(i,29).toString(),tabMode.getValueAt(i,30).toString(),tabMode.getValueAt(i,31).toString(),tabMode.getValueAt(i,32).toString(),tabMode.getValueAt(i,33).toString(),tabMode.getValueAt(i,34).toString(),
                                    tabMode.getValueAt(i,35).toString(),tabMode.getValueAt(i,36).toString(),tabMode.getValueAt(i,37).toString(),tabMode.getValueAt(i,38).toString(),tabMode.getValueAt(i,39).toString(),tabMode.getValueAt(i,40).toString(),
                                    tabMode.getValueAt(i,41).toString(),tabMode.getValueAt(i,42).toString(),tabMode.getValueAt(i,43).toString(),tabMode.getValueAt(i,44).toString(),tabMode.getValueAt(i,45).toString(),tabMode.getValueAt(i,46).toString(),
                                    tabMode.getValueAt(i,47).toString(),tabMode.getValueAt(i,48).toString(),tabMode.getValueAt(i,49).toString(),tabMode.getValueAt(i,50).toString(),tabMode.getValueAt(i,51).toString(),tabMode.getValueAt(i,52).toString(),
                                    tabMode.getValueAt(i,53).toString(),tabMode.getValueAt(i,54).toString(),tabMode.getValueAt(i,55).toString(),tabMode.getValueAt(i,56).toString(),tabMode.getValueAt(i,57).toString(),tabMode.getValueAt(i,58).toString(),
                                    tabMode.getValueAt(i,59).toString(),tabMode.getValueAt(i,60).toString(),tabMode.getValueAt(i,61).toString(),tabMode.getValueAt(i,62).toString(),tabMode.getValueAt(i,63).toString(),tabMode.getValueAt(i,64).toString(),
                                    tabMode.getValueAt(i,65).toString(),tabMode.getValueAt(i,66).toString(),tabMode.getValueAt(i,67).toString(),tabMode.getValueAt(i,68).toString(),tabMode.getValueAt(i,69).toString(),tabMode.getValueAt(i,70).toString(),
                                    tabMode.getValueAt(i,71).toString(),tabMode.getValueAt(i,72).toString(),tabMode.getValueAt(i,73).toString(),tabMode.getValueAt(i,74).toString(),tabMode.getValueAt(i,75).toString(),tabMode.getValueAt(i,76).toString(),
                                    tabMode.getValueAt(i,77).toString(),tabMode.getValueAt(i,78).toString(),tabMode.getValueAt(i,79).toString(),tabMode.getValueAt(i,80).toString(),tabMode.getValueAt(i,81).toString(),tabMode.getValueAt(i,2).toString(),
                                    koderekening,Piutang_BPJS_RVP,tabMode.getValueAt(i,82).toString()
                                })==false){
                                    sukses=false;
                            }
                        }
                    }else{
                        sukses=false;
                    }
                }
            }
            
            if(sukses==true){
                Sequel.Commit();
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            
            Sequel.AutoComitTrue();
            
            if(sukses==true){
                tampil();
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnBayarActionPerformed

    private void BtnBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBayarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBayarKeyPressed

    private void AkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkunBayarKeyPressed
        Valid.pindah(evt,Tanggal,TCari);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,kdptg,AkunBayar);
    }//GEN-LAST:event_TanggalKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            if(tbBangsal.getSelectedColumn()==0){
                getdata(tbBangsal.getSelectedRow());
            }
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbBangsalPropertyChange
        if(this.isVisible()==true){
            try {
                if(tbBangsal.getSelectedRow()!= -1){
                    if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                        tbBangsal.setValueAt((( (Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString())+Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString())) / Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString()) )*100),tbBangsal.getSelectedRow(),11);
                        selisih=0;
                        selisih=(Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString())+Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString()))-Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString());
                        if(selisih>=0){
                            tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),12);
                            tbBangsal.setValueAt(selisih,tbBangsal.getSelectedRow(),13);
                        }else{
                            selisih=( (Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),17).toString())+Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),24).toString())+Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),31).toString())+Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),39).toString())+Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),47).toString())+Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),55).toString())+Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),64).toString())+Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),68).toString())+Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),79).toString())) * ((100-Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),11).toString()))/100) );
                            rugihppralan=(Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),72).toString())-(Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),71).toString())*(Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),11).toString())/100)));
                            if(rugihppralan>0){
                                selisih=selisih+rugihppralan;
                            }
                            rugihppranap=((Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),74).toString())-Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),75).toString()))-((Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),70).toString())+Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),73).toString())-Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),75).toString()))*(Valid.SetAngka(tabMode.getValueAt(tbBangsal.getSelectedRow(),11).toString())/100)));
                            if(rugihppranap>0){
                                selisih=selisih+rugihppranap;
                            }
                            tbBangsal.setValueAt(selisih,tbBangsal.getSelectedRow(),12);
                            tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),13);
                        }
                    }
                }
            } catch (Exception e) {
            }
            row=tbBangsal.getRowCount();
            total=0;
            lebih=0;
            rugi=0;
            for(i=0;i<row;i++){  
                if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                    try {
                        total=total+Valid.SetAngka(tbBangsal.getValueAt(i,10).toString()); 
                    } catch (Exception e) {
                    }

                    try {
                       rugi=rugi+Valid.SetAngka(tbBangsal.getValueAt(i,12).toString());
                    } catch (Exception e) {
                    }

                    try {
                       lebih=lebih+Valid.SetAngka(tbBangsal.getValueAt(i,13).toString());
                    } catch (Exception e) {
                    }
                }
            }
            LCount1.setText(Valid.SetAngka(total));
            LCount2.setText(Valid.SetAngka(rugi));
            LCount3.setText(Valid.SetAngka(lebih));
        }
    }//GEN-LAST:event_tbBangsalPropertyChange

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        KeuanganCariRVPBPJS form=new KeuanganCariRVPBPJS(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbBangsal.getRowCount();i++){
            tbBangsal.setValueAt(false,i,0);
            getdata(i);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        for(i=0;i<tbBangsal.getRowCount();i++){
            tbBangsal.setValueAt(true,i,0);
            getdata(i);
        }
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    private void ppUmbalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUmbalActionPerformed
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(excelFilter);
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String f = jfc.getSelectedFile().toString();
            try {
                Scanner sc = new Scanner(new File(f));  
                sc.useDelimiter(";");
                StringBuffer data=new StringBuffer();
                while (sc.hasNext()){  
                    data.append(sc.nextLine()+"},{\"nosep\":\"");
                }   
                json="{\"nosep\":\""+data.toString().replaceAll(";","\",\"dibayar\":");
                json="{\"data\":["+json.substring(0,json.length()-11)+"]}";
                sc.close();   
                if(!json.equals("")){
                    root = mapper.readTree(json);
                    if(root.path("data").isArray()){
                        Valid.tabelKosong(tabMode);
                        sisapiutang=0;
                        for(JsonNode list:root.path("data")){
                            ps=koneksi.prepareStatement(
                                "select piutang_pasien.no_rawat,piutang_pasien.tgl_piutang, concat(piutang_pasien.no_rkm_medis,' ',pasien.nm_pasien) as namapasien, "+
                                "piutang_pasien.totalpiutang,piutang_pasien.uangmuka,piutang_pasien.sisapiutang,bridging_sep.no_sep,inacbg_grouping_stage1.tarif, "+
                                "reg_periksa.biaya_reg,reg_periksa.status_lanjut from piutang_pasien inner join pasien on piutang_pasien.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat "+
                                "inner join bridging_sep on bridging_sep.no_rawat=reg_periksa.no_rawat "+
                                "inner join inacbg_grouping_stage1 on bridging_sep.no_sep=inacbg_grouping_stage1.no_sep "+
                                "where piutang_pasien.status='Belum Lunas' and bridging_sep.no_sep=?");
                            try {
                                ps.setString(1,list.path("nosep").asText());
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    cicilan=Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",rs.getString("no_rawat"));
                                    persenbayar=(( (list.path("dibayar").asDouble()+rs.getDouble("uangmuka")+cicilan) / rs.getDouble("totalpiutang") )*100);
                                    materialralan=0;bhpralan=0;tarif_tindakandrralan=0;tarif_tindakanprralan=0;ksoralan=0;menejemenralan=0;biaya_rawatralan=0;
                                    materialranap=0;bhpranap=0;tarif_tindakandrranap=0;tarif_tindakanprranap=0;ksoranap=0;menejemenranap=0;biaya_rawatranap=0;
                                    bagian_rslabralan=0;bhplabralan=0;tarif_perujuklabralan=0;tarif_tindakan_dokterlabralan=0;tarif_tindakan_petugaslabralan=0;ksolabralan=0;menejemenlabralan=0;biayalabralan=0;
                                    bagian_rslabranap=0;bhplabranap=0;tarif_perujuklabranap=0;tarif_tindakan_dokterlabranap=0;tarif_tindakan_petugaslabranap=0;ksolabranap=0;menejemenlabranap=0;biayalabranap=0;
                                    bagian_rsradiologiralan=0;bhpradiologiralan=0;tarif_perujukradiologiralan=0;tarif_tindakan_dokterradiologiralan=0;tarif_tindakan_petugasradiologiralan=0;ksoradiologiralan=0;menejemenradiologiralan=0;biayaradiologiralan=0;
                                    bagian_rsradiologiranap=0;bhpradiologiranap=0;tarif_perujukradiologiranap=0;tarif_tindakan_dokterradiologiranap=0;tarif_tindakan_petugasradiologiranap=0;ksoradiologiranap=0;menejemenradiologiranap=0;biayaradiologiranap=0;
                                    jmdokteroperasiralan=0;jmparamedisoperasiralan=0;bhpoperasiralan=0;pendapatanoperasiralan=0;
                                    jmdokteroperasiranap=0;jmparamedisoperasiranap=0;bhpoperasiranap=0;pendapatanoperasiranap=0;
                                    obatlangsung=0;obatralan=0;hppobatralan=0;obatranap=0;hppobatranap=0;returobat=0;tambahanbiaya=0;potonganbiaya=0;
                                    kamar=0;reseppulang=0;norawatbayi="";registrasi=0;harianranap=0;serviceranap=0;
                                    status=rs.getString("status_lanjut");
                                    registrasi=rs.getDouble("biaya_reg");
                                    //cek obat langsung
                                    obatlangsung=Sequel.cariIsiAngka("select besar_tagihan from tagihan_obat_langsung where no_rawat=? ",rs.getString("no_rawat"));
                                    //cek tambahan biaya
                                    tambahanbiaya=Sequel.cariIsiAngka("select sum(besar_biaya) from tambahan_biaya where no_rawat=? ",rs.getString("no_rawat"));
                                    //cek potongan biaya
                                    potonganbiaya=Sequel.cariIsiAngka("select sum(besar_pengurangan) from pengurangan_biaya where no_rawat=? ",rs.getString("no_rawat"));
                                    //cek rawat jalan
                                    setBiaya(rs.getString("no_rawat"));
                                    totalbiaya=Math.round(registrasi+biaya_rawatralan+biaya_rawatranap+biayalabralan+biayalabranap+biayaradiologiralan+biayaradiologiranap+
                                               bhpoperasiralan+pendapatanoperasiralan+bhpoperasiranap+pendapatanoperasiranap+obatlangsung+obatralan+obatranap-returobat+
                                               tambahanbiaya-potonganbiaya+kamar+reseppulang+harianranap+serviceranap);
                                    
                                    if(rs.getDouble("totalpiutang")==totalbiaya){
                                        rugi=0;
                                        lebih=0;
                                        selisih=(list.path("dibayar").asDouble()+rs.getDouble("uangmuka")+cicilan)-rs.getDouble("totalpiutang");
                                        if(selisih>=0){
                                            lebih=selisih;
                                        }else{
                                            selisih=( (bhpralan+bhpranap+bhplabralan+bhplabranap+bhpradiologiralan+bhpradiologiranap+bhpoperasiralan+bhpoperasiranap+reseppulang) * ((100-persenbayar)/100) );
                                            rugihppralan=(hppobatralan-(obatralan*(persenbayar/100)));
                                            if(rugihppralan>0){
                                                selisih=selisih+rugihppralan;
                                            }
                                            rugihppranap=((hppobatranap-returobat)-((obatlangsung+obatranap-returobat)*(persenbayar/100)));
                                            if(rugihppranap>0){
                                                selisih=selisih+rugihppranap;
                                            }
                                            rugi=selisih;
                                        } 
                                        tabMode.addRow(new Object[]{
                                            true,rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("tgl_piutang"),rs.getString("namapasien"),rs.getDouble("totalpiutang"),
                                            rs.getDouble("uangmuka"),cicilan,(rs.getDouble("sisapiutang")-cicilan),rs.getDouble("tarif"),list.path("dibayar").asDouble(),persenbayar,
                                            rugi,lebih,rs.getString("status_lanjut"),rs.getDouble("biaya_reg"),materialralan,bhpralan,tarif_tindakandrralan,tarif_tindakanprralan,
                                            ksoralan,menejemenralan,biaya_rawatralan,materialranap,bhpranap,tarif_tindakandrranap,tarif_tindakanprranap,ksoranap,menejemenranap,
                                            biaya_rawatranap,bagian_rslabralan,bhplabralan,tarif_perujuklabralan,tarif_tindakan_dokterlabralan,tarif_tindakan_petugaslabralan,ksolabralan,
                                            menejemenlabralan,biayalabralan,bagian_rslabranap,bhplabranap,tarif_perujuklabranap,tarif_tindakan_dokterlabranap,tarif_tindakan_petugaslabranap,
                                            ksolabranap,menejemenlabranap,biayalabranap,bagian_rsradiologiralan,bhpradiologiralan,tarif_perujukradiologiralan,tarif_tindakan_dokterradiologiralan,
                                            tarif_tindakan_petugasradiologiralan,ksoradiologiralan,menejemenradiologiralan,biayaradiologiralan,bagian_rsradiologiranap,bhpradiologiranap,
                                            tarif_perujukradiologiranap,tarif_tindakan_dokterradiologiranap,tarif_tindakan_petugasradiologiranap,ksoradiologiranap,menejemenradiologiranap,
                                            biayaradiologiranap,jmdokteroperasiralan,jmparamedisoperasiralan,bhpoperasiralan,pendapatanoperasiralan,jmdokteroperasiranap,jmparamedisoperasiranap,
                                            bhpoperasiranap,pendapatanoperasiranap,obatlangsung,obatralan,hppobatralan,obatranap,hppobatranap,returobat,tambahanbiaya,potonganbiaya,
                                            kamar,reseppulang,harianranap,registrasi,serviceranap
                                        });
                                        sisapiutang=sisapiutang+rs.getDouble("sisapiutang")-cicilan;
                                    }else{
                                        selisih=0;
                                        rugi=0;
                                        lebih=0;
                                        tabMode.addRow(new Object[]{
                                            false,rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("tgl_piutang"),rs.getString("namapasien"),rs.getDouble("totalpiutang"),
                                            rs.getDouble("uangmuka"),cicilan,(rs.getDouble("sisapiutang")-cicilan),rs.getDouble("tarif"),list.path("dibayar").asDouble(),persenbayar,
                                            rugi,selisih,rs.getString("status_lanjut"),rs.getDouble("biaya_reg"),materialralan,bhpralan,tarif_tindakandrralan,tarif_tindakanprralan,
                                            ksoralan,menejemenralan,biaya_rawatralan,materialranap,bhpranap,tarif_tindakandrranap,tarif_tindakanprranap,ksoranap,menejemenranap,
                                            biaya_rawatranap,bagian_rslabralan,bhplabralan,tarif_perujuklabralan,tarif_tindakan_dokterlabralan,tarif_tindakan_petugaslabralan,ksolabralan,
                                            menejemenlabralan,biayalabralan,bagian_rslabranap,bhplabranap,tarif_perujuklabranap,tarif_tindakan_dokterlabranap,tarif_tindakan_petugaslabranap,
                                            ksolabranap,menejemenlabranap,biayalabranap,bagian_rsradiologiralan,bhpradiologiralan,tarif_perujukradiologiralan,tarif_tindakan_dokterradiologiralan,
                                            tarif_tindakan_petugasradiologiralan,ksoradiologiralan,menejemenradiologiralan,biayaradiologiralan,bagian_rsradiologiranap,bhpradiologiranap,
                                            tarif_perujukradiologiranap,tarif_tindakan_dokterradiologiranap,tarif_tindakan_petugasradiologiranap,ksoradiologiranap,menejemenradiologiranap,
                                            biayaradiologiranap,jmdokteroperasiralan,jmparamedisoperasiralan,bhpoperasiralan,pendapatanoperasiralan,jmdokteroperasiranap,jmparamedisoperasiranap,
                                            bhpoperasiranap,pendapatanoperasiranap,obatlangsung,obatralan,hppobatralan,obatranap,hppobatranap,returobat,tambahanbiaya,potonganbiaya,
                                            kamar,reseppulang,harianranap,registrasi,serviceranap
                                        });
                                        sisapiutang=sisapiutang+rs.getDouble("sisapiutang")-cicilan;
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally {
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                            
                            ps=koneksi.prepareStatement(
                                "select piutang_pasien.no_rawat,piutang_pasien.tgl_piutang, concat(piutang_pasien.no_rkm_medis,' ',pasien.nm_pasien) as namapasien, "+
                                "piutang_pasien.totalpiutang,piutang_pasien.uangmuka,piutang_pasien.sisapiutang,inacbg_klaim_baru2.no_sep,inacbg_grouping_stage12.tarif, "+
                                "reg_periksa.biaya_reg,reg_periksa.status_lanjut from piutang_pasien inner join pasien on piutang_pasien.no_rkm_medis=pasien.no_rkm_medis "+
                                "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat "+
                                "inner join inacbg_klaim_baru2 on inacbg_klaim_baru2.no_rawat=reg_periksa.no_rawat "+
                                "inner join inacbg_grouping_stage12 on inacbg_klaim_baru2.no_sep=inacbg_grouping_stage12.no_sep "+
                                "where piutang_pasien.status='Belum Lunas' and inacbg_klaim_baru2.no_sep=?");
                            try {
                                ps.setString(1,list.path("nosep").asText());
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    cicilan=Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",rs.getString("no_rawat"));
                                    persenbayar=(( (list.path("dibayar").asDouble()+rs.getDouble("uangmuka")+cicilan) / rs.getDouble("totalpiutang") )*100);
                                    materialralan=0;bhpralan=0;tarif_tindakandrralan=0;tarif_tindakanprralan=0;ksoralan=0;menejemenralan=0;biaya_rawatralan=0;
                                    materialranap=0;bhpranap=0;tarif_tindakandrranap=0;tarif_tindakanprranap=0;ksoranap=0;menejemenranap=0;biaya_rawatranap=0;
                                    bagian_rslabralan=0;bhplabralan=0;tarif_perujuklabralan=0;tarif_tindakan_dokterlabralan=0;tarif_tindakan_petugaslabralan=0;ksolabralan=0;menejemenlabralan=0;biayalabralan=0;
                                    bagian_rslabranap=0;bhplabranap=0;tarif_perujuklabranap=0;tarif_tindakan_dokterlabranap=0;tarif_tindakan_petugaslabranap=0;ksolabranap=0;menejemenlabranap=0;biayalabranap=0;
                                    bagian_rsradiologiralan=0;bhpradiologiralan=0;tarif_perujukradiologiralan=0;tarif_tindakan_dokterradiologiralan=0;tarif_tindakan_petugasradiologiralan=0;ksoradiologiralan=0;menejemenradiologiralan=0;biayaradiologiralan=0;
                                    bagian_rsradiologiranap=0;bhpradiologiranap=0;tarif_perujukradiologiranap=0;tarif_tindakan_dokterradiologiranap=0;tarif_tindakan_petugasradiologiranap=0;ksoradiologiranap=0;menejemenradiologiranap=0;biayaradiologiranap=0;
                                    jmdokteroperasiralan=0;jmparamedisoperasiralan=0;bhpoperasiralan=0;pendapatanoperasiralan=0;
                                    jmdokteroperasiranap=0;jmparamedisoperasiranap=0;bhpoperasiranap=0;pendapatanoperasiranap=0;
                                    obatlangsung=0;obatralan=0;hppobatralan=0;obatranap=0;hppobatranap=0;returobat=0;tambahanbiaya=0;potonganbiaya=0;
                                    kamar=0;reseppulang=0;norawatbayi="";registrasi=0;harianranap=0;serviceranap=0;
                                    status=rs.getString("status_lanjut");
                                    registrasi=rs.getDouble("biaya_reg");
                                    //cek obat langsung
                                    obatlangsung=Sequel.cariIsiAngka("select besar_tagihan from tagihan_obat_langsung where no_rawat=? ",rs.getString("no_rawat"));
                                    //cek tambahan biaya
                                    tambahanbiaya=Sequel.cariIsiAngka("select sum(besar_biaya) from tambahan_biaya where no_rawat=? ",rs.getString("no_rawat"));
                                    //cek potongan biaya
                                    potonganbiaya=Sequel.cariIsiAngka("select sum(besar_pengurangan) from pengurangan_biaya where no_rawat=? ",rs.getString("no_rawat"));
                                    //cek rawat jalan
                                    setBiaya(rs.getString("no_rawat"));
                                    totalbiaya=Math.round(registrasi+biaya_rawatralan+biaya_rawatranap+biayalabralan+biayalabranap+biayaradiologiralan+biayaradiologiranap+
                                               bhpoperasiralan+pendapatanoperasiralan+bhpoperasiranap+pendapatanoperasiranap+obatlangsung+obatralan+obatranap-returobat+
                                               tambahanbiaya-potonganbiaya+kamar+reseppulang+harianranap+serviceranap);
                                    
                                    if(rs.getDouble("totalpiutang")==totalbiaya){
                                        rugi=0;
                                        lebih=0;
                                        selisih=(list.path("dibayar").asDouble()+rs.getDouble("uangmuka")+cicilan)-rs.getDouble("totalpiutang");
                                        if(selisih>=0){
                                            lebih=selisih;
                                        }else{
                                            selisih=( (bhpralan+bhpranap+bhplabralan+bhplabranap+bhpradiologiralan+bhpradiologiranap+bhpoperasiralan+bhpoperasiranap+reseppulang) * ((100-persenbayar)/100) );
                                            rugihppralan=(hppobatralan-(obatralan*(persenbayar/100)));
                                            if(rugihppralan>0){
                                                selisih=selisih+rugihppralan;
                                            }
                                            rugihppranap=((hppobatranap-returobat)-((obatlangsung+obatranap-returobat)*(persenbayar/100)));
                                            if(rugihppranap>0){
                                                selisih=selisih+rugihppranap;
                                            }
                                            rugi=selisih;
                                        } 
                                        tabMode.addRow(new Object[]{
                                            true,rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("tgl_piutang"),rs.getString("namapasien"),rs.getDouble("totalpiutang"),
                                            rs.getDouble("uangmuka"),cicilan,(rs.getDouble("sisapiutang")-cicilan),rs.getDouble("tarif"),list.path("dibayar").asDouble(),persenbayar,
                                            rugi,lebih,rs.getString("status_lanjut"),rs.getDouble("biaya_reg"),materialralan,bhpralan,tarif_tindakandrralan,tarif_tindakanprralan,
                                            ksoralan,menejemenralan,biaya_rawatralan,materialranap,bhpranap,tarif_tindakandrranap,tarif_tindakanprranap,ksoranap,menejemenranap,
                                            biaya_rawatranap,bagian_rslabralan,bhplabralan,tarif_perujuklabralan,tarif_tindakan_dokterlabralan,tarif_tindakan_petugaslabralan,ksolabralan,
                                            menejemenlabralan,biayalabralan,bagian_rslabranap,bhplabranap,tarif_perujuklabranap,tarif_tindakan_dokterlabranap,tarif_tindakan_petugaslabranap,
                                            ksolabranap,menejemenlabranap,biayalabranap,bagian_rsradiologiralan,bhpradiologiralan,tarif_perujukradiologiralan,tarif_tindakan_dokterradiologiralan,
                                            tarif_tindakan_petugasradiologiralan,ksoradiologiralan,menejemenradiologiralan,biayaradiologiralan,bagian_rsradiologiranap,bhpradiologiranap,
                                            tarif_perujukradiologiranap,tarif_tindakan_dokterradiologiranap,tarif_tindakan_petugasradiologiranap,ksoradiologiranap,menejemenradiologiranap,
                                            biayaradiologiranap,jmdokteroperasiralan,jmparamedisoperasiralan,bhpoperasiralan,pendapatanoperasiralan,jmdokteroperasiranap,jmparamedisoperasiranap,
                                            bhpoperasiranap,pendapatanoperasiranap,obatlangsung,obatralan,hppobatralan,obatranap,hppobatranap,returobat,tambahanbiaya,potonganbiaya,
                                            kamar,reseppulang,harianranap,registrasi,serviceranap
                                        });
                                        sisapiutang=sisapiutang+rs.getDouble("sisapiutang")-cicilan;
                                    }else{
                                        selisih=0;
                                        rugi=0;
                                        lebih=0;
                                        tabMode.addRow(new Object[]{
                                            false,rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("tgl_piutang"),rs.getString("namapasien"),rs.getDouble("totalpiutang"),
                                            rs.getDouble("uangmuka"),cicilan,(rs.getDouble("sisapiutang")-cicilan),rs.getDouble("tarif"),list.path("dibayar").asDouble(),persenbayar,
                                            rugi,selisih,rs.getString("status_lanjut"),rs.getDouble("biaya_reg"),materialralan,bhpralan,tarif_tindakandrralan,tarif_tindakanprralan,
                                            ksoralan,menejemenralan,biaya_rawatralan,materialranap,bhpranap,tarif_tindakandrranap,tarif_tindakanprranap,ksoranap,menejemenranap,
                                            biaya_rawatranap,bagian_rslabralan,bhplabralan,tarif_perujuklabralan,tarif_tindakan_dokterlabralan,tarif_tindakan_petugaslabralan,ksolabralan,
                                            menejemenlabralan,biayalabralan,bagian_rslabranap,bhplabranap,tarif_perujuklabranap,tarif_tindakan_dokterlabranap,tarif_tindakan_petugaslabranap,
                                            ksolabranap,menejemenlabranap,biayalabranap,bagian_rsradiologiralan,bhpradiologiralan,tarif_perujukradiologiralan,tarif_tindakan_dokterradiologiralan,
                                            tarif_tindakan_petugasradiologiralan,ksoradiologiralan,menejemenradiologiralan,biayaradiologiralan,bagian_rsradiologiranap,bhpradiologiranap,
                                            tarif_perujukradiologiranap,tarif_tindakan_dokterradiologiranap,tarif_tindakan_petugasradiologiranap,ksoradiologiranap,menejemenradiologiranap,
                                            biayaradiologiranap,jmdokteroperasiralan,jmparamedisoperasiralan,bhpoperasiralan,pendapatanoperasiralan,jmdokteroperasiranap,jmparamedisoperasiranap,
                                            bhpoperasiranap,pendapatanoperasiranap,obatlangsung,obatralan,hppobatralan,obatranap,hppobatranap,returobat,tambahanbiaya,potonganbiaya,
                                            kamar,reseppulang,harianranap,registrasi,serviceranap
                                        });
                                        sisapiutang=sisapiutang+rs.getDouble("sisapiutang")-cicilan;
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally {
                                if(rs!=null){
                                    rs.close();
                                }
                                if(ps!=null){
                                    ps.close();
                                }
                            }
                        }
                    }
                    row=tbBangsal.getRowCount();
                    total=0;
                    lebih=0;
                    rugi=0;
                    for(i=0;i<row;i++){  
                        if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                            try {
                                total=total+Valid.SetAngka(tbBangsal.getValueAt(i,10).toString()); 
                            } catch (Exception e) {
                            }

                            try {
                               rugi=rugi+Valid.SetAngka(tbBangsal.getValueAt(i,12).toString());
                            } catch (Exception e) {
                            }

                            try {
                               lebih=lebih+Valid.SetAngka(tbBangsal.getValueAt(i,13).toString());
                            } catch (Exception e) {
                            }
                        }
                    }
                    LCount1.setText(Valid.SetAngka(total));
                    LCount2.setText(Valid.SetAngka(rugi));
                    LCount3.setText(Valid.SetAngka(lebih));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }
    }//GEN-LAST:event_ppUmbalActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampilAkunBayar();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganRVPBPJS dialog = new KeuanganRVPBPJS(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AkunBayar;
    private widget.Button BtnAll;
    private widget.Button BtnBayar;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private javax.swing.JLabel LCount;
    private javax.swing.JLabel LCount1;
    private javax.swing.JLabel LCount2;
    private javax.swing.JLabel LCount3;
    private javax.swing.JMenuItem MnDetailPiutang;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal Tanggal;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private widget.Label jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdptg;
    private widget.Label label17;
    private widget.Label label19;
    private widget.Label label33;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPilihSemua;
    private javax.swing.JMenuItem ppUmbal;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    private void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            sisapiutang=0;
            ps=koneksi.prepareStatement(
                   "select piutang_pasien.no_rawat,piutang_pasien.tgl_piutang, concat(piutang_pasien.no_rkm_medis,' ',pasien.nm_pasien) as namapasien, "+
                   "piutang_pasien.totalpiutang,piutang_pasien.uangmuka,piutang_pasien.sisapiutang,bridging_sep.no_sep,inacbg_grouping_stage1.tarif, "+
                   "reg_periksa.biaya_reg,reg_periksa.status_lanjut from piutang_pasien inner join pasien on piutang_pasien.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat "+
                   "inner join bridging_sep on bridging_sep.no_rawat=reg_periksa.no_rawat "+
                   "left join inacbg_grouping_stage1 on bridging_sep.no_sep=inacbg_grouping_stage1.no_sep "+
                   "where piutang_pasien.status='Belum Lunas' "+
                   (TCari.getText().trim().equals("")?"":"and (piutang_pasien.no_rawat like ? or piutang_pasien.no_rkm_medis like ? "+
                   "or pasien.nm_pasien like ? or bridging_sep.no_sep like ? or reg_periksa.status_lanjut like ?)")+" order by piutang_pasien.tgl_piutang");
            try {
                if(!TCari.getText().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    cicilan=Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",rs.getString("no_rawat"));
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("tgl_piutang"),rs.getString("namapasien"),
                        rs.getDouble("totalpiutang"),rs.getDouble("uangmuka"),cicilan,(rs.getDouble("sisapiutang")-cicilan),
                        rs.getDouble("tarif"),null,0,0,0,rs.getString("status_lanjut"),rs.getDouble("biaya_reg"),0,0,0,0,0,
                        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,0,0,0,0
                    });
                    sisapiutang=sisapiutang+rs.getDouble("sisapiutang")-cicilan;
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
            
            ps=koneksi.prepareStatement(
                   "select piutang_pasien.no_rawat,piutang_pasien.tgl_piutang, concat(piutang_pasien.no_rkm_medis,' ',pasien.nm_pasien) as namapasien, "+
                   "piutang_pasien.totalpiutang,piutang_pasien.uangmuka,piutang_pasien.sisapiutang,inacbg_klaim_baru2.no_sep,inacbg_grouping_stage12.tarif, "+
                   "reg_periksa.biaya_reg,reg_periksa.status_lanjut from piutang_pasien inner join pasien on piutang_pasien.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat "+
                   "inner join inacbg_klaim_baru2 on inacbg_klaim_baru2.no_rawat=reg_periksa.no_rawat "+
                   "left join inacbg_grouping_stage12 on inacbg_klaim_baru2.no_sep=inacbg_grouping_stage12.no_sep "+
                   "where piutang_pasien.status='Belum Lunas' "+
                   (TCari.getText().trim().equals("")?"":"and (piutang_pasien.no_rawat like ? or piutang_pasien.no_rkm_medis like ? "+
                   "or pasien.nm_pasien like ? or inacbg_klaim_baru2.no_sep like ? or reg_periksa.status_lanjut like ?)")+" order by piutang_pasien.tgl_piutang");
            try {
                if(!TCari.getText().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    cicilan=Sequel.cariIsiAngka("SELECT ifnull(SUM(bayar_piutang.besar_cicilan),0) FROM bayar_piutang where bayar_piutang.no_rawat=?",rs.getString("no_rawat"));
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("tgl_piutang"),rs.getString("namapasien"),
                        rs.getDouble("totalpiutang"),rs.getDouble("uangmuka"),cicilan,(rs.getDouble("sisapiutang")-cicilan),
                        rs.getDouble("tarif"),null,0,0,0,rs.getString("status_lanjut"),rs.getDouble("biaya_reg"),0,0,0,0,0,
                        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,0,0,0,0
                    });
                    sisapiutang=sisapiutang+rs.getDouble("sisapiutang")-cicilan;
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
            LCount.setText(Valid.SetAngka(sisapiutang));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getdata(int pilih) {
        try {
            if(pilih!= -1){
                if(tbBangsal.getValueAt(pilih,0).toString().equals("true")){
                    tbBangsal.setValueAt(Double.parseDouble(tbBangsal.getValueAt(pilih,9).toString()),pilih,10);
                    tbBangsal.setValueAt((( (Valid.SetAngka(tbBangsal.getValueAt(pilih,10).toString())+Valid.SetAngka(tbBangsal.getValueAt(pilih,6).toString())+Valid.SetAngka(tbBangsal.getValueAt(pilih,7).toString())) / Valid.SetAngka(tbBangsal.getValueAt(pilih,5).toString()) )*100),pilih,11);
                    materialralan=0;bhpralan=0;tarif_tindakandrralan=0;tarif_tindakanprralan=0;ksoralan=0;menejemenralan=0;biaya_rawatralan=0;
                    materialranap=0;bhpranap=0;tarif_tindakandrranap=0;tarif_tindakanprranap=0;ksoranap=0;menejemenranap=0;biaya_rawatranap=0;
                    bagian_rslabralan=0;bhplabralan=0;tarif_perujuklabralan=0;tarif_tindakan_dokterlabralan=0;tarif_tindakan_petugaslabralan=0;ksolabralan=0;menejemenlabralan=0;biayalabralan=0;
                    bagian_rslabranap=0;bhplabranap=0;tarif_perujuklabranap=0;tarif_tindakan_dokterlabranap=0;tarif_tindakan_petugaslabranap=0;ksolabranap=0;menejemenlabranap=0;biayalabranap=0;
                    bagian_rsradiologiralan=0;bhpradiologiralan=0;tarif_perujukradiologiralan=0;tarif_tindakan_dokterradiologiralan=0;tarif_tindakan_petugasradiologiralan=0;ksoradiologiralan=0;menejemenradiologiralan=0;biayaradiologiralan=0;
                    bagian_rsradiologiranap=0;bhpradiologiranap=0;tarif_perujukradiologiranap=0;tarif_tindakan_dokterradiologiranap=0;tarif_tindakan_petugasradiologiranap=0;ksoradiologiranap=0;menejemenradiologiranap=0;biayaradiologiranap=0;
                    jmdokteroperasiralan=0;jmparamedisoperasiralan=0;bhpoperasiralan=0;pendapatanoperasiralan=0;
                    jmdokteroperasiranap=0;jmparamedisoperasiranap=0;bhpoperasiranap=0;pendapatanoperasiranap=0;
                    obatlangsung=0;obatralan=0;hppobatralan=0;obatranap=0;hppobatranap=0;returobat=0;tambahanbiaya=0;potonganbiaya=0;
                    kamar=0;reseppulang=0;norawatbayi="";registrasi=0;harianranap=0;serviceranap=0;
                    status=tbBangsal.getValueAt(pilih,14).toString();
                    registrasi=Valid.SetAngka(tbBangsal.getValueAt(pilih,15).toString());
                    //cek obat langsung
                    obatlangsung=Sequel.cariIsiAngka("select besar_tagihan from tagihan_obat_langsung where no_rawat=? ",tbBangsal.getValueAt(pilih,1).toString());
                    //cek tambahan biaya
                    tambahanbiaya=Sequel.cariIsiAngka("select sum(besar_biaya) from tambahan_biaya where no_rawat=? ",tbBangsal.getValueAt(pilih,1).toString());
                    //cek potongan biaya
                    potonganbiaya=Sequel.cariIsiAngka("select sum(besar_pengurangan) from pengurangan_biaya where no_rawat=? ",tbBangsal.getValueAt(pilih,1).toString());
                    //cek rawat jalan
                    ps=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_drpr where no_rawat=?");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            materialralan=rs.getDouble("material");
                            bhpralan=rs.getDouble("bhp");
                            tarif_tindakandrralan=rs.getDouble("tarif_tindakandr");
                            tarif_tindakanprralan=rs.getDouble("tarif_tindakanpr");
                            ksoralan=rs.getDouble("kso");
                            menejemenralan=rs.getDouble("menejemen");
                            biaya_rawatralan=rs.getDouble("biaya_rawat");
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
                    ps=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_dr where no_rawat=?");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            materialralan=+materialralan+rs.getDouble("material");
                            bhpralan=bhpralan+rs.getDouble("bhp");
                            tarif_tindakandrralan=tarif_tindakandrralan+rs.getDouble("tarif_tindakandr");
                            ksoralan=ksoralan+rs.getDouble("kso");
                            menejemenralan=menejemenralan+rs.getDouble("menejemen");
                            biaya_rawatralan=biaya_rawatralan+rs.getDouble("biaya_rawat");
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
                    ps=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_pr where no_rawat=?");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            materialralan=+materialralan+rs.getDouble("material");
                            bhpralan=bhpralan+rs.getDouble("bhp");
                            tarif_tindakanprralan=tarif_tindakanprralan+rs.getDouble("tarif_tindakanpr");
                            ksoralan=ksoralan+rs.getDouble("kso");
                            menejemenralan=menejemenralan+rs.getDouble("menejemen");
                            biaya_rawatralan=biaya_rawatralan+rs.getDouble("biaya_rawat");
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
                    //cek lab ralan
                    ps=koneksi.prepareStatement(
                            "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                            "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_lab "+
                            "where no_rawat=? and status='Ralan'");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            bagian_rslabralan=rs.getDouble("bagian_rs");
                            bhplabralan=rs.getDouble("bhp");
                            tarif_perujuklabralan=rs.getDouble("tarif_perujuk");
                            tarif_tindakan_dokterlabralan=rs.getDouble("tarif_tindakan_dokter");
                            tarif_tindakan_petugaslabralan=rs.getDouble("tarif_tindakan_petugas");
                            ksolabralan=rs.getDouble("kso");
                            menejemenlabralan=rs.getDouble("menejemen");
                            biayalabralan=rs.getDouble("biaya");
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
                    ps=koneksi.prepareStatement(
                            "select sum(detail_periksa_lab.bagian_rs) as bagian_rs,sum(detail_periksa_lab.bhp) as bhp,sum(detail_periksa_lab.bagian_perujuk) as bagian_perujuk,"+
                            "sum(detail_periksa_lab.bagian_dokter) as bagian_dokter,sum(detail_periksa_lab.bagian_laborat) as bagian_laborat,sum(detail_periksa_lab.kso) as kso,"+
                            "sum(detail_periksa_lab.menejemen) as menejemen,sum(detail_periksa_lab.biaya_item) as biaya_item from detail_periksa_lab inner join periksa_lab on "+
                            "detail_periksa_lab.no_rawat=periksa_lab.no_rawat and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa "+
                            "and detail_periksa_lab.jam=periksa_lab.jam where detail_periksa_lab.no_rawat=? and periksa_lab.status='Ralan'");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            bagian_rslabralan=bagian_rslabralan+rs.getDouble("bagian_rs");
                            bhplabralan=bhplabralan+rs.getDouble("bhp");
                            tarif_perujuklabralan=tarif_perujuklabralan+rs.getDouble("bagian_perujuk");
                            tarif_tindakan_dokterlabralan=tarif_tindakan_dokterlabralan+rs.getDouble("bagian_dokter");
                            tarif_tindakan_petugaslabralan=tarif_tindakan_petugaslabralan+rs.getDouble("bagian_laborat");
                            ksolabralan=ksolabralan+rs.getDouble("kso");
                            menejemenlabralan=menejemenlabralan+rs.getDouble("menejemen");
                            biayalabralan=biayalabralan+rs.getDouble("biaya_item");
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
                    //cek radiologi ralan
                    ps=koneksi.prepareStatement(
                            "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                            "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_radiologi "+
                            "where no_rawat=? and status='Ralan'");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            bagian_rsradiologiralan=rs.getDouble("bagian_rs");
                            bhpradiologiralan=rs.getDouble("bhp");
                            tarif_perujukradiologiralan=rs.getDouble("tarif_perujuk");
                            tarif_tindakan_dokterradiologiralan=rs.getDouble("tarif_tindakan_dokter");
                            tarif_tindakan_petugasradiologiralan=rs.getDouble("tarif_tindakan_petugas");
                            ksoradiologiralan=rs.getDouble("kso");
                            menejemenradiologiralan=rs.getDouble("menejemen");
                            biayaradiologiralan=rs.getDouble("biaya");
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
                    //cek operasi ralan
                    ps=koneksi.prepareStatement(
                            "select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as jmdokter, "+
                            "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) as jmparamedis,"+
                            "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayaalat+biayasewaok+akomodasi+bagian_rs+biayasarpras+biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as pendapatan "+
                            "from operasi where no_rawat=? and status='Ralan'");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            jmdokteroperasiralan=rs.getDouble("jmdokter");
                            jmparamedisoperasiralan=rs.getDouble("jmparamedis");
                            pendapatanoperasiralan=rs.getDouble("pendapatan");
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
                    bhpoperasiralan=Sequel.cariIsiAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi inner join operasi on beri_obat_operasi.no_rawat=operasi.no_rawat and beri_obat_operasi.tanggal=operasi.tgl_operasi where operasi.status='Ralan' and beri_obat_operasi.no_rawat=?",tbBangsal.getValueAt(pilih,1).toString());
                    //cek obat rawat jalan
                    ps=koneksi.prepareStatement("select sum(h_beli*jml) as hpp,sum(total) as total from detail_pemberian_obat where no_rawat=? and status='Ralan'");
                    try {
                        ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                        rs=ps.executeQuery();
                        while(rs.next()){
                            obatralan=rs.getDouble("total");
                            hppobatralan=rs.getDouble("hpp");
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
                    if(status.equals("Ranap")){
                        //cek rawat inap
                        ps=koneksi.prepareStatement(
                                "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                                "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_drpr where no_rawat=?");
                        try {
                            ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                materialranap=rs.getDouble("material");
                                bhpranap=rs.getDouble("bhp");
                                tarif_tindakandrranap=rs.getDouble("tarif_tindakandr");
                                tarif_tindakanprranap=rs.getDouble("tarif_tindakanpr");
                                ksoranap=rs.getDouble("kso");
                                menejemenranap=rs.getDouble("menejemen");
                                biaya_rawatranap=rs.getDouble("biaya_rawat");
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
                        ps=koneksi.prepareStatement(
                                "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                                "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_dr where no_rawat=?");
                        try {
                            ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                materialranap=+materialranap+rs.getDouble("material");
                                bhpranap=bhpranap+rs.getDouble("bhp");
                                tarif_tindakandrranap=tarif_tindakandrranap+rs.getDouble("tarif_tindakandr");
                                ksoranap=ksoranap+rs.getDouble("kso");
                                menejemenranap=menejemenranap+rs.getDouble("menejemen");
                                biaya_rawatranap=biaya_rawatranap+rs.getDouble("biaya_rawat");
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
                        ps=koneksi.prepareStatement(
                                "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                                "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_pr where no_rawat=?");
                        try {
                            ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                materialranap=+materialranap+rs.getDouble("material");
                                bhpranap=bhpranap+rs.getDouble("bhp");
                                tarif_tindakanprranap=tarif_tindakanprranap+rs.getDouble("tarif_tindakanpr");
                                ksoranap=ksoranap+rs.getDouble("kso");
                                menejemenranap=menejemenranap+rs.getDouble("menejemen");
                                biaya_rawatranap=biaya_rawatranap+rs.getDouble("biaya_rawat");
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
                        //cek lab ranap
                        ps=koneksi.prepareStatement(
                                "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                                "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_lab "+
                                "where no_rawat=? and status='Ranap'");
                        try {
                            ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                bagian_rslabranap=rs.getDouble("bagian_rs");
                                bhplabranap=rs.getDouble("bhp");
                                tarif_perujuklabranap=rs.getDouble("tarif_perujuk");
                                tarif_tindakan_dokterlabranap=rs.getDouble("tarif_tindakan_dokter");
                                tarif_tindakan_petugaslabranap=rs.getDouble("tarif_tindakan_petugas");
                                ksolabranap=rs.getDouble("kso");
                                menejemenlabranap=rs.getDouble("menejemen");
                                biayalabranap=rs.getDouble("biaya");
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
                        ps=koneksi.prepareStatement(
                                "select sum(detail_periksa_lab.bagian_rs) as bagian_rs,sum(detail_periksa_lab.bhp) as bhp,sum(detail_periksa_lab.bagian_perujuk) as bagian_perujuk,"+
                                "sum(detail_periksa_lab.bagian_dokter) as bagian_dokter,sum(detail_periksa_lab.bagian_laborat) as bagian_laborat,sum(detail_periksa_lab.kso) as kso,"+
                                "sum(detail_periksa_lab.menejemen) as menejemen,sum(detail_periksa_lab.biaya_item) as biaya_item from detail_periksa_lab inner join periksa_lab on "+
                                "detail_periksa_lab.no_rawat=periksa_lab.no_rawat and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa "+
                                "and detail_periksa_lab.jam=periksa_lab.jam where detail_periksa_lab.no_rawat=? and periksa_lab.status='Ranap'");
                        try {
                            ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                bagian_rslabranap=bagian_rslabranap+rs.getDouble("bagian_rs");
                                bhplabranap=bhplabranap+rs.getDouble("bhp");
                                tarif_perujuklabranap=tarif_perujuklabranap+rs.getDouble("bagian_perujuk");
                                tarif_tindakan_dokterlabranap=tarif_tindakan_dokterlabranap+rs.getDouble("bagian_dokter");
                                tarif_tindakan_petugaslabranap=tarif_tindakan_petugaslabranap+rs.getDouble("bagian_laborat");
                                ksolabranap=ksolabranap+rs.getDouble("kso");
                                menejemenlabranap=menejemenlabranap+rs.getDouble("menejemen");
                                biayalabranap=biayalabranap+rs.getDouble("biaya_item");
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
                        //cek radiologi ranap
                        ps=koneksi.prepareStatement(
                                "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                                "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_radiologi "+
                                "where no_rawat=? and status='Ranap'");
                        try {
                            ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                bagian_rsradiologiranap=rs.getDouble("bagian_rs");
                                bhpradiologiranap=rs.getDouble("bhp");
                                tarif_perujukradiologiranap=rs.getDouble("tarif_perujuk");
                                tarif_tindakan_dokterradiologiranap=rs.getDouble("tarif_tindakan_dokter");
                                tarif_tindakan_petugasradiologiranap=rs.getDouble("tarif_tindakan_petugas");
                                ksoradiologiranap=rs.getDouble("kso");
                                menejemenradiologiranap=rs.getDouble("menejemen");
                                biayaradiologiranap=rs.getDouble("biaya");
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
                        //cek operasi ranap
                        ps=koneksi.prepareStatement(
                                "select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as jmdokter, "+
                                "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) as jmparamedis,"+
                                "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayaalat+biayasewaok+akomodasi+bagian_rs+biayasarpras+biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as pendapatan "+
                                "from operasi where no_rawat=? and status='Ranap'");
                        try {
                            ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                            rs=ps.executeQuery();
                            if(rs.next()){
                                jmdokteroperasiranap=rs.getDouble("jmdokter");
                                jmparamedisoperasiranap=rs.getDouble("jmparamedis");
                                pendapatanoperasiranap=rs.getDouble("pendapatan");
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
                        bhpoperasiranap=Sequel.cariIsiAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi inner join operasi on beri_obat_operasi.no_rawat=operasi.no_rawat and beri_obat_operasi.tanggal=operasi.tgl_operasi where operasi.status='Ranap' and beri_obat_operasi.no_rawat=?",tbBangsal.getValueAt(pilih,1).toString());
                        //cek obat rawat ranap
                        ps=koneksi.prepareStatement("select sum(h_beli*jml) as hpp,sum(total) as total from detail_pemberian_obat where no_rawat=? and status='Ranap'");
                        try {
                            ps.setString(1,tbBangsal.getValueAt(pilih,1).toString());
                            rs=ps.executeQuery();
                            while(rs.next()){
                                obatranap=rs.getDouble("total");
                                hppobatranap=rs.getDouble("hpp");
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
                        //cek retur obat
                        returobat=Sequel.cariIsiAngka("select sum(detreturjual.subtotal) from detreturjual where no_retur_jual like ?","%"+tbBangsal.getValueAt(pilih,1).toString()+"%");
                        //cek kamar 
                        kamar=Sequel.cariIsiAngka("select sum(totalbiaya) from billing where status='Kamar' and no_rawat=?",tbBangsal.getValueAt(pilih,1).toString());
                        //cek harian 
                        harianranap=Sequel.cariIsiAngka("select sum(totalbiaya) from billing where status='Harian' and no_rawat=?",tbBangsal.getValueAt(pilih,1).toString());
                        //cek service 
                        serviceranap=Sequel.cariIsiAngka("select sum(totalbiaya) from billing where status='Service' and no_rawat=?",tbBangsal.getValueAt(pilih,1).toString());
                        //cek resep pulang 
                        reseppulang=Sequel.cariIsiAngka("select sum(total) from resep_pulang where no_rawat=?",tbBangsal.getValueAt(pilih,1).toString());
                        norawatbayi=Sequel.cariIsi("select no_rawat2 from ranap_gabung where no_rawat=?",tbBangsal.getValueAt(pilih,1).toString());
                        if(!norawatbayi.equals("")){
                            //cek obat langsung bayi
                            obatlangsung=obatlangsung+Sequel.cariIsiAngka("select besar_tagihan from tagihan_obat_langsung where no_rawat=? ",norawatbayi);
                            //cek tambahan biaya bayi
                            tambahanbiaya=tambahanbiaya+Sequel.cariIsiAngka("select sum(besar_biaya) from tambahan_biaya where no_rawat=? ",norawatbayi);
                            //cek potongan biaya bayi
                            potonganbiaya=potonganbiaya+Sequel.cariIsiAngka("select sum(besar_pengurangan) from pengurangan_biaya where no_rawat=? ",norawatbayi);
                            //cek rawat jalan bayi
                            ps=koneksi.prepareStatement(
                                    "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                                    "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_drpr where no_rawat=?");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    materialralan=materialralan+rs.getDouble("material");
                                    bhpralan=bhpralan+rs.getDouble("bhp");
                                    tarif_tindakandrralan=tarif_tindakandrralan+rs.getDouble("tarif_tindakandr");
                                    tarif_tindakanprralan=tarif_tindakanprralan+rs.getDouble("tarif_tindakanpr");
                                    ksoralan=ksoralan+rs.getDouble("kso");
                                    menejemenralan=menejemenralan+rs.getDouble("menejemen");
                                    biaya_rawatralan=biaya_rawatralan+rs.getDouble("biaya_rawat");
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
                            ps=koneksi.prepareStatement(
                                    "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                                    "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_dr where no_rawat=?");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    materialralan=+materialralan+rs.getDouble("material");
                                    bhpralan=bhpralan+rs.getDouble("bhp");
                                    tarif_tindakandrralan=tarif_tindakandrralan+rs.getDouble("tarif_tindakandr");
                                    ksoralan=ksoralan+rs.getDouble("kso");
                                    menejemenralan=menejemenralan+rs.getDouble("menejemen");
                                    biaya_rawatralan=biaya_rawatralan+rs.getDouble("biaya_rawat");
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
                            ps=koneksi.prepareStatement(
                                    "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                                    "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_pr where no_rawat=?");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    materialralan=+materialralan+rs.getDouble("material");
                                    bhpralan=bhpralan+rs.getDouble("bhp");
                                    tarif_tindakanprralan=tarif_tindakanprralan+rs.getDouble("tarif_tindakanpr");
                                    ksoralan=ksoralan+rs.getDouble("kso");
                                    menejemenralan=menejemenralan+rs.getDouble("menejemen");
                                    biaya_rawatralan=biaya_rawatralan+rs.getDouble("biaya_rawat");
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
                            //cek lab ralan bayi
                            ps=koneksi.prepareStatement(
                                    "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                                    "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_lab "+
                                    "where no_rawat=? and status='Ralan'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    bagian_rslabralan=bagian_rslabralan+rs.getDouble("bagian_rs");
                                    bhplabralan=bhplabralan+rs.getDouble("bhp");
                                    tarif_perujuklabralan=tarif_perujuklabralan+rs.getDouble("tarif_perujuk");
                                    tarif_tindakan_dokterlabralan=tarif_tindakan_dokterlabralan+rs.getDouble("tarif_tindakan_dokter");
                                    tarif_tindakan_petugaslabralan=tarif_tindakan_petugaslabralan+rs.getDouble("tarif_tindakan_petugas");
                                    ksolabralan=ksolabralan+rs.getDouble("kso");
                                    menejemenlabralan=menejemenlabralan+rs.getDouble("menejemen");
                                    biayalabralan=biayalabralan+rs.getDouble("biaya");
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
                            ps=koneksi.prepareStatement(
                                    "select sum(detail_periksa_lab.bagian_rs) as bagian_rs,sum(detail_periksa_lab.bhp) as bhp,sum(detail_periksa_lab.bagian_perujuk) as bagian_perujuk,"+
                                    "sum(detail_periksa_lab.bagian_dokter) as bagian_dokter,sum(detail_periksa_lab.bagian_laborat) as bagian_laborat,sum(detail_periksa_lab.kso) as kso,"+
                                    "sum(detail_periksa_lab.menejemen) as menejemen,sum(detail_periksa_lab.biaya_item) as biaya_item from detail_periksa_lab inner join periksa_lab on "+
                                    "detail_periksa_lab.no_rawat=periksa_lab.no_rawat and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa "+
                                    "and detail_periksa_lab.jam=periksa_lab.jam where detail_periksa_lab.no_rawat=? and periksa_lab.status='Ralan'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    bagian_rslabralan=bagian_rslabralan+rs.getDouble("bagian_rs");
                                    bhplabralan=bhplabralan+rs.getDouble("bhp");
                                    tarif_perujuklabralan=tarif_perujuklabralan+rs.getDouble("bagian_perujuk");
                                    tarif_tindakan_dokterlabralan=tarif_tindakan_dokterlabralan+rs.getDouble("bagian_dokter");
                                    tarif_tindakan_petugaslabralan=tarif_tindakan_petugaslabralan+rs.getDouble("bagian_laborat");
                                    ksolabralan=ksolabralan+rs.getDouble("kso");
                                    menejemenlabralan=menejemenlabralan+rs.getDouble("menejemen");
                                    biayalabralan=biayalabralan+rs.getDouble("biaya_item");
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
                            //cek radiologi ralan bayi
                            ps=koneksi.prepareStatement(
                                    "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                                    "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_radiologi "+
                                    "where no_rawat=? and status='Ralan'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    bagian_rsradiologiralan=bagian_rsradiologiralan+rs.getDouble("bagian_rs");
                                    bhpradiologiralan=bhpradiologiralan+rs.getDouble("bhp");
                                    tarif_perujukradiologiralan=tarif_perujukradiologiralan+rs.getDouble("tarif_perujuk");
                                    tarif_tindakan_dokterradiologiralan=tarif_tindakan_dokterradiologiralan+rs.getDouble("tarif_tindakan_dokter");
                                    tarif_tindakan_petugasradiologiralan=tarif_tindakan_petugasradiologiralan+rs.getDouble("tarif_tindakan_petugas");
                                    ksoradiologiralan=ksoradiologiralan+rs.getDouble("kso");
                                    menejemenradiologiralan=menejemenradiologiralan+rs.getDouble("menejemen");
                                    biayaradiologiralan=biayaradiologiralan+rs.getDouble("biaya");
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
                            //cek operasi ralan hayi
                            ps=koneksi.prepareStatement(
                                    "select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as jmdokter, "+
                                    "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) as jmparamedis,"+
                                    "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayaalat+biayasewaok+akomodasi+bagian_rs+biayasarpras+biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as pendapatan "+
                                    "from operasi where no_rawat=? and status='Ralan'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    jmdokteroperasiralan=jmdokteroperasiralan+rs.getDouble("jmdokter");
                                    jmparamedisoperasiralan=jmparamedisoperasiralan+rs.getDouble("jmparamedis");
                                    pendapatanoperasiralan=pendapatanoperasiralan+rs.getDouble("pendapatan");
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
                            bhpoperasiralan=bhpoperasiralan+Sequel.cariIsiAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi inner join operasi on beri_obat_operasi.no_rawat=operasi.no_rawat and beri_obat_operasi.tanggal=operasi.tgl_operasi where operasi.status='Ralan' and beri_obat_operasi.no_rawat=?",norawatbayi);
                            //cek obat rawat jalan bayi
                            ps=koneksi.prepareStatement("select sum(h_beli*jml) as hpp,sum(total) as total from detail_pemberian_obat where no_rawat=? and status='Ralan'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    obatralan=obatralan+rs.getDouble("total");
                                    hppobatralan=hppobatralan+rs.getDouble("hpp");
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
                            //cek rawat inap bayi
                            ps=koneksi.prepareStatement(
                                    "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                                    "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_drpr where no_rawat=?");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    materialranap=materialranap+rs.getDouble("material");
                                    bhpranap=bhpranap+rs.getDouble("bhp");
                                    tarif_tindakandrranap=tarif_tindakandrranap+rs.getDouble("tarif_tindakandr");
                                    tarif_tindakanprranap=tarif_tindakanprranap+rs.getDouble("tarif_tindakanpr");
                                    ksoranap=ksoranap+rs.getDouble("kso");
                                    menejemenranap=menejemenranap+rs.getDouble("menejemen");
                                    biaya_rawatranap=biaya_rawatranap+rs.getDouble("biaya_rawat");
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
                            ps=koneksi.prepareStatement(
                                    "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                                    "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_dr where no_rawat=?");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    materialranap=+materialranap+rs.getDouble("material");
                                    bhpranap=bhpranap+rs.getDouble("bhp");
                                    tarif_tindakandrranap=tarif_tindakandrranap+rs.getDouble("tarif_tindakandr");
                                    ksoranap=ksoranap+rs.getDouble("kso");
                                    menejemenranap=menejemenranap+rs.getDouble("menejemen");
                                    biaya_rawatranap=biaya_rawatranap+rs.getDouble("biaya_rawat");
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
                            ps=koneksi.prepareStatement(
                                    "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                                    "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_pr where no_rawat=?");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    materialranap=+materialranap+rs.getDouble("material");
                                    bhpranap=bhpranap+rs.getDouble("bhp");
                                    tarif_tindakanprranap=tarif_tindakanprranap+rs.getDouble("tarif_tindakanpr");
                                    ksoranap=ksoranap+rs.getDouble("kso");
                                    menejemenranap=menejemenranap+rs.getDouble("menejemen");
                                    biaya_rawatranap=biaya_rawatranap+rs.getDouble("biaya_rawat");
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
                            //cek lab ranap bayi
                            ps=koneksi.prepareStatement(
                                    "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                                    "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_lab "+
                                    "where no_rawat=? and status='Ranap'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    bagian_rslabranap=bagian_rslabranap+rs.getDouble("bagian_rs");
                                    bhplabranap=bhplabranap+rs.getDouble("bhp");
                                    tarif_perujuklabranap=tarif_perujuklabranap+rs.getDouble("tarif_perujuk");
                                    tarif_tindakan_dokterlabranap=tarif_tindakan_dokterlabranap+rs.getDouble("tarif_tindakan_dokter");
                                    tarif_tindakan_petugaslabranap=tarif_tindakan_petugaslabranap+rs.getDouble("tarif_tindakan_petugas");
                                    ksolabranap=ksolabranap+rs.getDouble("kso");
                                    menejemenlabranap=menejemenlabranap+rs.getDouble("menejemen");
                                    biayalabranap=biayalabranap+rs.getDouble("biaya");
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
                            ps=koneksi.prepareStatement(
                                    "select sum(detail_periksa_lab.bagian_rs) as bagian_rs,sum(detail_periksa_lab.bhp) as bhp,sum(detail_periksa_lab.bagian_perujuk) as bagian_perujuk,"+
                                    "sum(detail_periksa_lab.bagian_dokter) as bagian_dokter,sum(detail_periksa_lab.bagian_laborat) as bagian_laborat,sum(detail_periksa_lab.kso) as kso,"+
                                    "sum(detail_periksa_lab.menejemen) as menejemen,sum(detail_periksa_lab.biaya_item) as biaya_item from detail_periksa_lab inner join periksa_lab on "+
                                    "detail_periksa_lab.no_rawat=periksa_lab.no_rawat and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa "+
                                    "and detail_periksa_lab.jam=periksa_lab.jam where detail_periksa_lab.no_rawat=? and periksa_lab.status='Ranap'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    bagian_rslabranap=bagian_rslabranap+rs.getDouble("bagian_rs");
                                    bhplabranap=bhplabranap+rs.getDouble("bhp");
                                    tarif_perujuklabranap=tarif_perujuklabranap+rs.getDouble("bagian_perujuk");
                                    tarif_tindakan_dokterlabranap=tarif_tindakan_dokterlabranap+rs.getDouble("bagian_dokter");
                                    tarif_tindakan_petugaslabranap=tarif_tindakan_petugaslabranap+rs.getDouble("bagian_laborat");
                                    ksolabranap=ksolabranap+rs.getDouble("kso");
                                    menejemenlabranap=menejemenlabranap+rs.getDouble("menejemen");
                                    biayalabranap=biayalabranap+rs.getDouble("biaya_item");
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
                            //cek radiologi ranap bayi
                            ps=koneksi.prepareStatement(
                                    "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                                    "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_radiologi "+
                                    "where no_rawat=? and status='Ranap'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    bagian_rsradiologiranap=bagian_rsradiologiranap+rs.getDouble("bagian_rs");
                                    bhpradiologiranap=bhpradiologiranap+rs.getDouble("bhp");
                                    tarif_perujukradiologiranap=tarif_perujukradiologiranap+rs.getDouble("tarif_perujuk");
                                    tarif_tindakan_dokterradiologiranap=tarif_tindakan_dokterradiologiranap+rs.getDouble("tarif_tindakan_dokter");
                                    tarif_tindakan_petugasradiologiranap=tarif_tindakan_petugasradiologiranap+rs.getDouble("tarif_tindakan_petugas");
                                    ksoradiologiranap=ksoradiologiranap+rs.getDouble("kso");
                                    menejemenradiologiranap=menejemenradiologiranap+rs.getDouble("menejemen");
                                    biayaradiologiranap=biayaradiologiranap+rs.getDouble("biaya");
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
                            //cek operasi ranap bayi
                            ps=koneksi.prepareStatement(
                                    "select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as jmdokter, "+
                                    "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) as jmparamedis,"+
                                    "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayaalat+biayasewaok+akomodasi+bagian_rs+biayasarpras+biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as pendapatan "+
                                    "from operasi where no_rawat=? and status='Ranap'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                if(rs.next()){
                                    jmdokteroperasiranap=jmdokteroperasiranap+rs.getDouble("jmdokter");
                                    jmparamedisoperasiranap=jmparamedisoperasiranap+rs.getDouble("jmparamedis");
                                    pendapatanoperasiranap=pendapatanoperasiranap+rs.getDouble("pendapatan");
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
                            bhpoperasiranap=bhpoperasiranap+Sequel.cariIsiAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi inner join operasi on beri_obat_operasi.no_rawat=operasi.no_rawat and beri_obat_operasi.tanggal=operasi.tgl_operasi where operasi.status='Ranap' and beri_obat_operasi.no_rawat=?",norawatbayi);
                            //cek obat rawat ranap bayi
                            ps=koneksi.prepareStatement("select sum(h_beli*jml) as hpp,sum(total) as total from detail_pemberian_obat where no_rawat=? and status='Ranap'");
                            try {
                                ps.setString(1,norawatbayi);
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    obatranap=obatranap+rs.getDouble("total");
                                    hppobatranap=hppobatranap+rs.getDouble("hpp");
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
                            //cek retur obat bayi
                            returobat=returobat+Sequel.cariIsiAngka("select sum(detreturjual.subtotal) from detreturjual where no_retur_jual like ?","%"+norawatbayi+"%");
                            //cek resep pulang bayi
                            reseppulang=reseppulang+Sequel.cariIsiAngka("select sum(total) from resep_pulang where no_rawat=?",norawatbayi);
                        }
                    }
                    if(status.equals("Ralan")){
                        if(tampilkan_ppnobat_ralan.equals("Yes")){
                            obatlangsung=obatlangsung+(Valid.roundUp(obatralan*0.1,100));
                        }
                    }else if(status.equals("Ranap")){
                        if(tampilkan_ppnobat_ranap.equals("Yes")){
                            obatlangsung=obatlangsung+(Valid.roundUp((obatralan+obatranap-returobat)*0.1,100));
                        }
                        if(tampilkan_administrasi_di_billingranap.equals("No")){
                            registrasi=0;
                        }
                    }
                    totalbiaya=Math.round(registrasi+biaya_rawatralan+biaya_rawatranap+biayalabralan+biayalabranap+biayaradiologiralan+biayaradiologiranap+
                               bhpoperasiralan+pendapatanoperasiralan+bhpoperasiranap+pendapatanoperasiranap+obatlangsung+obatralan+obatranap-returobat+
                               tambahanbiaya-potonganbiaya+kamar+reseppulang+harianranap+serviceranap);
                    if(Math.round(Valid.SetAngka(tbBangsal.getValueAt(pilih,5).toString()))==totalbiaya){
                        tbBangsal.setValueAt(materialralan,pilih,16);
                        tbBangsal.setValueAt(bhpralan,pilih,17);
                        tbBangsal.setValueAt(tarif_tindakandrralan,pilih,18);
                        tbBangsal.setValueAt(tarif_tindakanprralan,pilih,19);
                        tbBangsal.setValueAt(ksoralan,pilih,20);
                        tbBangsal.setValueAt(menejemenralan,pilih,21);
                        tbBangsal.setValueAt(biaya_rawatralan,pilih,22);
                        tbBangsal.setValueAt(materialranap,pilih,23);
                        tbBangsal.setValueAt(bhpranap,pilih,24);
                        tbBangsal.setValueAt(tarif_tindakandrranap,pilih,25);
                        tbBangsal.setValueAt(tarif_tindakanprranap,pilih,26);
                        tbBangsal.setValueAt(ksoranap,pilih,27);
                        tbBangsal.setValueAt(menejemenranap,pilih,28);
                        tbBangsal.setValueAt(biaya_rawatranap,pilih,29);
                        tbBangsal.setValueAt(bagian_rslabralan,pilih,30);
                        tbBangsal.setValueAt(bhplabralan,pilih,31);
                        tbBangsal.setValueAt(tarif_perujuklabralan,pilih,32);
                        tbBangsal.setValueAt(tarif_tindakan_dokterlabralan,pilih,33);
                        tbBangsal.setValueAt(tarif_tindakan_petugaslabralan,pilih,34);
                        tbBangsal.setValueAt(ksolabralan,pilih,35);
                        tbBangsal.setValueAt(menejemenlabralan,pilih,36);
                        tbBangsal.setValueAt(biayalabralan,pilih,37);
                        tbBangsal.setValueAt(bagian_rslabranap,pilih,38);
                        tbBangsal.setValueAt(bhplabranap,pilih,39);
                        tbBangsal.setValueAt(tarif_perujuklabranap,pilih,40);
                        tbBangsal.setValueAt(tarif_tindakan_dokterlabranap,pilih,41);
                        tbBangsal.setValueAt(tarif_tindakan_petugaslabranap,pilih,42);
                        tbBangsal.setValueAt(ksolabranap,pilih,43);
                        tbBangsal.setValueAt(menejemenlabranap,pilih,44);
                        tbBangsal.setValueAt(biayalabranap,pilih,45);
                        tbBangsal.setValueAt(bagian_rsradiologiralan,pilih,46);
                        tbBangsal.setValueAt(bhpradiologiralan,pilih,47);
                        tbBangsal.setValueAt(tarif_perujukradiologiralan,pilih,48);
                        tbBangsal.setValueAt(tarif_tindakan_dokterradiologiralan,pilih,49);
                        tbBangsal.setValueAt(tarif_tindakan_petugasradiologiralan,pilih,50);
                        tbBangsal.setValueAt(ksoradiologiralan,pilih,51);
                        tbBangsal.setValueAt(menejemenradiologiralan,pilih,52);
                        tbBangsal.setValueAt(biayaradiologiralan,pilih,53);
                        tbBangsal.setValueAt(bagian_rsradiologiranap,pilih,54);
                        tbBangsal.setValueAt(bhpradiologiranap,pilih,55);
                        tbBangsal.setValueAt(tarif_perujukradiologiranap,pilih,56);
                        tbBangsal.setValueAt(tarif_tindakan_dokterradiologiranap,pilih,57);
                        tbBangsal.setValueAt(tarif_tindakan_petugasradiologiranap,pilih,58);
                        tbBangsal.setValueAt(ksoradiologiranap,pilih,59);
                        tbBangsal.setValueAt(menejemenradiologiranap,pilih,60);
                        tbBangsal.setValueAt(biayaradiologiranap,pilih,61);
                        tbBangsal.setValueAt(jmdokteroperasiralan,pilih,62);
                        tbBangsal.setValueAt(jmparamedisoperasiralan,pilih,63);
                        tbBangsal.setValueAt(bhpoperasiralan,pilih,64);
                        tbBangsal.setValueAt(pendapatanoperasiralan,pilih,65);
                        tbBangsal.setValueAt(jmdokteroperasiranap,pilih,66);
                        tbBangsal.setValueAt(jmparamedisoperasiranap,pilih,67);
                        tbBangsal.setValueAt(bhpoperasiranap,pilih,68);
                        tbBangsal.setValueAt(pendapatanoperasiranap,pilih,69);
                        tbBangsal.setValueAt(obatlangsung,pilih,70);
                        tbBangsal.setValueAt(obatralan,pilih,71);
                        tbBangsal.setValueAt(hppobatralan,pilih,72);
                        tbBangsal.setValueAt(obatranap,pilih,73);
                        tbBangsal.setValueAt(hppobatranap,pilih,74);
                        tbBangsal.setValueAt(returobat,pilih,75);
                        tbBangsal.setValueAt(tambahanbiaya,pilih,76);
                        tbBangsal.setValueAt(potonganbiaya,pilih,77);
                        tbBangsal.setValueAt(kamar,pilih,78);
                        tbBangsal.setValueAt(reseppulang,pilih,79);
                        tbBangsal.setValueAt(harianranap,pilih,80);
                        tbBangsal.setValueAt(registrasi,pilih,81);
                        tbBangsal.setValueAt(serviceranap,pilih,82);
                        
                        selisih=0;
                        selisih=(Valid.SetAngka(tbBangsal.getValueAt(pilih,10).toString())+Valid.SetAngka(tbBangsal.getValueAt(pilih,6).toString())+Valid.SetAngka(tbBangsal.getValueAt(pilih,7).toString()))-Valid.SetAngka(tbBangsal.getValueAt(pilih,5).toString());
                        if(selisih>=0){
                            tbBangsal.setValueAt(0,pilih,12);
                            tbBangsal.setValueAt(selisih,pilih,13);
                        }else{
                            selisih=( (bhpralan+bhpranap+bhplabralan+bhplabranap+bhpradiologiralan+bhpradiologiranap+bhpoperasiralan+bhpoperasiranap+reseppulang) * ((100-Valid.SetAngka(tabMode.getValueAt(pilih,11).toString()))/100) );
                            rugihppralan=(hppobatralan-(obatralan*(Valid.SetAngka(tabMode.getValueAt(pilih,11).toString())/100)));
                            if(rugihppralan>0){
                                selisih=selisih+rugihppralan;
                            }
                            rugihppranap=((hppobatranap-returobat)-((obatlangsung+obatranap-returobat)*(Valid.SetAngka(tabMode.getValueAt(pilih,11).toString())/100)));
                            if(rugihppranap>0){
                                selisih=selisih+rugihppranap;
                            }
                            tbBangsal.setValueAt(selisih,pilih,12);
                            tbBangsal.setValueAt(0,pilih,13);
                        } 
                    }else{
                        JOptionPane.showMessageDialog(null,"Ditemukan perbedaan Total Biaya pada closing billing,\nsilahkan cek data billing dengan Hapus Nota Salah... !!! ");
                        tbBangsal.setValueAt(false,pilih,0);
                        tbBangsal.setValueAt(null,pilih,10);
                        tbBangsal.setValueAt(0,pilih,11);
                        tbBangsal.setValueAt(0,pilih,16);
                        tbBangsal.setValueAt(0,pilih,17);
                        tbBangsal.setValueAt(0,pilih,18);
                        tbBangsal.setValueAt(0,pilih,19);
                        tbBangsal.setValueAt(0,pilih,20);
                        tbBangsal.setValueAt(0,pilih,21);
                        tbBangsal.setValueAt(0,pilih,22);
                        tbBangsal.setValueAt(0,pilih,23);
                        tbBangsal.setValueAt(0,pilih,24);
                        tbBangsal.setValueAt(0,pilih,25);
                        tbBangsal.setValueAt(0,pilih,26);
                        tbBangsal.setValueAt(0,pilih,27);
                        tbBangsal.setValueAt(0,pilih,28);
                        tbBangsal.setValueAt(0,pilih,29);
                        tbBangsal.setValueAt(0,pilih,30);
                        tbBangsal.setValueAt(0,pilih,31);
                        tbBangsal.setValueAt(0,pilih,32);
                        tbBangsal.setValueAt(0,pilih,33);
                        tbBangsal.setValueAt(0,pilih,34);
                        tbBangsal.setValueAt(0,pilih,35);
                        tbBangsal.setValueAt(0,pilih,36);
                        tbBangsal.setValueAt(0,pilih,37);
                        tbBangsal.setValueAt(0,pilih,38);
                        tbBangsal.setValueAt(0,pilih,39);
                        tbBangsal.setValueAt(0,pilih,40);
                        tbBangsal.setValueAt(0,pilih,41);
                        tbBangsal.setValueAt(0,pilih,42);
                        tbBangsal.setValueAt(0,pilih,43);
                        tbBangsal.setValueAt(0,pilih,44);
                        tbBangsal.setValueAt(0,pilih,45);
                        tbBangsal.setValueAt(0,pilih,46);
                        tbBangsal.setValueAt(0,pilih,47);
                        tbBangsal.setValueAt(0,pilih,48);
                        tbBangsal.setValueAt(0,pilih,49);
                        tbBangsal.setValueAt(0,pilih,50);
                        tbBangsal.setValueAt(0,pilih,51);
                        tbBangsal.setValueAt(0,pilih,52);
                        tbBangsal.setValueAt(0,pilih,53);
                        tbBangsal.setValueAt(0,pilih,54);
                        tbBangsal.setValueAt(0,pilih,55);
                        tbBangsal.setValueAt(0,pilih,56);
                        tbBangsal.setValueAt(0,pilih,57);
                        tbBangsal.setValueAt(0,pilih,58);
                        tbBangsal.setValueAt(0,pilih,59);
                        tbBangsal.setValueAt(0,pilih,60);
                        tbBangsal.setValueAt(0,pilih,61);
                        tbBangsal.setValueAt(0,pilih,62);
                        tbBangsal.setValueAt(0,pilih,63);
                        tbBangsal.setValueAt(0,pilih,64);
                        tbBangsal.setValueAt(0,pilih,65);
                        tbBangsal.setValueAt(0,pilih,66);
                        tbBangsal.setValueAt(0,pilih,67);
                        tbBangsal.setValueAt(0,pilih,68);
                        tbBangsal.setValueAt(0,pilih,69);
                        tbBangsal.setValueAt(0,pilih,70);
                        tbBangsal.setValueAt(0,pilih,71);
                        tbBangsal.setValueAt(0,pilih,72);
                        tbBangsal.setValueAt(0,pilih,73);
                        tbBangsal.setValueAt(0,pilih,74);
                        tbBangsal.setValueAt(0,pilih,75);
                        tbBangsal.setValueAt(0,pilih,76);
                        tbBangsal.setValueAt(0,pilih,77);
                        tbBangsal.setValueAt(0,pilih,78);
                        tbBangsal.setValueAt(0,pilih,79);
                        tbBangsal.setValueAt(0,pilih,80);
                        tbBangsal.setValueAt(0,pilih,81);
                        tbBangsal.setValueAt(0,pilih,82);
                    }  
                }else if(tbBangsal.getValueAt(pilih,0).toString().equals("false")){
                    tbBangsal.setValueAt(null,pilih,10);
                    tbBangsal.setValueAt(0,pilih,11);
                    tbBangsal.setValueAt(0,pilih,12);
                    tbBangsal.setValueAt(0,pilih,13);
                    tbBangsal.setValueAt(0,pilih,16);
                    tbBangsal.setValueAt(0,pilih,17);
                    tbBangsal.setValueAt(0,pilih,18);
                    tbBangsal.setValueAt(0,pilih,19);
                    tbBangsal.setValueAt(0,pilih,20);
                    tbBangsal.setValueAt(0,pilih,21);
                    tbBangsal.setValueAt(0,pilih,22);
                    tbBangsal.setValueAt(0,pilih,23);
                    tbBangsal.setValueAt(0,pilih,24);
                    tbBangsal.setValueAt(0,pilih,25);
                    tbBangsal.setValueAt(0,pilih,26);
                    tbBangsal.setValueAt(0,pilih,27);
                    tbBangsal.setValueAt(0,pilih,28);
                    tbBangsal.setValueAt(0,pilih,29);
                    tbBangsal.setValueAt(0,pilih,30);
                    tbBangsal.setValueAt(0,pilih,31);
                    tbBangsal.setValueAt(0,pilih,32);
                    tbBangsal.setValueAt(0,pilih,33);
                    tbBangsal.setValueAt(0,pilih,34);
                    tbBangsal.setValueAt(0,pilih,35);
                    tbBangsal.setValueAt(0,pilih,36);
                    tbBangsal.setValueAt(0,pilih,37);
                    tbBangsal.setValueAt(0,pilih,38);
                    tbBangsal.setValueAt(0,pilih,39);
                    tbBangsal.setValueAt(0,pilih,40);
                    tbBangsal.setValueAt(0,pilih,41);
                    tbBangsal.setValueAt(0,pilih,42);
                    tbBangsal.setValueAt(0,pilih,43);
                    tbBangsal.setValueAt(0,pilih,44);
                    tbBangsal.setValueAt(0,pilih,45);
                    tbBangsal.setValueAt(0,pilih,46);
                    tbBangsal.setValueAt(0,pilih,47);
                    tbBangsal.setValueAt(0,pilih,48);
                    tbBangsal.setValueAt(0,pilih,49);
                    tbBangsal.setValueAt(0,pilih,50);
                    tbBangsal.setValueAt(0,pilih,51);
                    tbBangsal.setValueAt(0,pilih,52);
                    tbBangsal.setValueAt(0,pilih,53);
                    tbBangsal.setValueAt(0,pilih,54);
                    tbBangsal.setValueAt(0,pilih,55);
                    tbBangsal.setValueAt(0,pilih,56);
                    tbBangsal.setValueAt(0,pilih,57);
                    tbBangsal.setValueAt(0,pilih,58);
                    tbBangsal.setValueAt(0,pilih,59);
                    tbBangsal.setValueAt(0,pilih,60);
                    tbBangsal.setValueAt(0,pilih,61);
                    tbBangsal.setValueAt(0,pilih,62);
                    tbBangsal.setValueAt(0,pilih,63);
                    tbBangsal.setValueAt(0,pilih,64);
                    tbBangsal.setValueAt(0,pilih,65);
                    tbBangsal.setValueAt(0,pilih,66);
                    tbBangsal.setValueAt(0,pilih,67);
                    tbBangsal.setValueAt(0,pilih,68);
                    tbBangsal.setValueAt(0,pilih,69);
                    tbBangsal.setValueAt(0,pilih,70);
                    tbBangsal.setValueAt(0,pilih,71);
                    tbBangsal.setValueAt(0,pilih,72);
                    tbBangsal.setValueAt(0,pilih,73);
                    tbBangsal.setValueAt(0,pilih,74);
                    tbBangsal.setValueAt(0,pilih,75);
                    tbBangsal.setValueAt(0,pilih,76);
                    tbBangsal.setValueAt(0,pilih,77);
                    tbBangsal.setValueAt(0,pilih,78);
                    tbBangsal.setValueAt(0,pilih,79);
                    tbBangsal.setValueAt(0,pilih,80);
                    tbBangsal.setValueAt(0,pilih,81);
                    tbBangsal.setValueAt(0,pilih,82);
                }
            }  
        } catch (Exception e) {
        }
        row=tbBangsal.getRowCount();
        total=0;
        lebih=0;
        rugi=0;
        for(i=0;i<row;i++){  
            if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                 try {
                     total=total+Valid.SetAngka(tbBangsal.getValueAt(i,10).toString()); 
                 } catch (Exception e) {
                 }
                  
                 try {
                    rugi=rugi+Valid.SetAngka(tbBangsal.getValueAt(i,12).toString());
                 } catch (Exception e) {
                 }
                 
                 try {
                    lebih=lebih+Valid.SetAngka(tbBangsal.getValueAt(i,13).toString());
                 } catch (Exception e) {
                 } 
            }
        }
        LCount1.setText(Valid.SetAngka(total));
        LCount2.setText(Valid.SetAngka(rugi));
        LCount3.setText(Valid.SetAngka(lebih));
    }
    
    public void isCek(){
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            BtnBayar.setEnabled(akses.getrvu_bpjs());
            kdptg.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        } 
    }
    
    private void setBiaya(String norawat){
        try {
            ps2=koneksi.prepareStatement(
                    "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                    "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_drpr where no_rawat=?");
            try {
                ps2.setString(1,norawat);
                rs2=ps2.executeQuery();
                if(rs2.next()){
                    materialralan=rs2.getDouble("material");
                    bhpralan=rs2.getDouble("bhp");
                    tarif_tindakandrralan=rs2.getDouble("tarif_tindakandr");
                    tarif_tindakanprralan=rs2.getDouble("tarif_tindakanpr");
                    ksoralan=rs2.getDouble("kso");
                    menejemenralan=rs2.getDouble("menejemen");
                    biaya_rawatralan=rs2.getDouble("biaya_rawat");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
            ps2=koneksi.prepareStatement(
                    "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                    "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_dr where no_rawat=?");
            try {
                ps2.setString(1,norawat);
                rs2=ps2.executeQuery();
                if(rs2.next()){
                    materialralan=+materialralan+rs2.getDouble("material");
                    bhpralan=bhpralan+rs2.getDouble("bhp");
                    tarif_tindakandrralan=tarif_tindakandrralan+rs2.getDouble("tarif_tindakandr");
                    ksoralan=ksoralan+rs2.getDouble("kso");
                    menejemenralan=menejemenralan+rs2.getDouble("menejemen");
                    biaya_rawatralan=biaya_rawatralan+rs2.getDouble("biaya_rawat");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
            ps2=koneksi.prepareStatement(
                    "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                    "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_pr where no_rawat=?");
            try {
                ps2.setString(1,norawat);
                rs2=ps2.executeQuery();
                if(rs2.next()){
                    materialralan=+materialralan+rs2.getDouble("material");
                    bhpralan=bhpralan+rs2.getDouble("bhp");
                    tarif_tindakanprralan=tarif_tindakanprralan+rs2.getDouble("tarif_tindakanpr");
                    ksoralan=ksoralan+rs2.getDouble("kso");
                    menejemenralan=menejemenralan+rs2.getDouble("menejemen");
                    biaya_rawatralan=biaya_rawatralan+rs2.getDouble("biaya_rawat");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
            //cek lab ralan
            ps2=koneksi.prepareStatement(
                    "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                    "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_lab "+
                    "where no_rawat=? and status='Ralan'");
            try {
                ps2.setString(1,norawat);
                rs2=ps2.executeQuery();
                if(rs2.next()){
                    bagian_rslabralan=rs2.getDouble("bagian_rs");
                    bhplabralan=rs2.getDouble("bhp");
                    tarif_perujuklabralan=rs2.getDouble("tarif_perujuk");
                    tarif_tindakan_dokterlabralan=rs2.getDouble("tarif_tindakan_dokter");
                    tarif_tindakan_petugaslabralan=rs2.getDouble("tarif_tindakan_petugas");
                    ksolabralan=rs2.getDouble("kso");
                    menejemenlabralan=rs2.getDouble("menejemen");
                    biayalabralan=rs2.getDouble("biaya");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
            ps2=koneksi.prepareStatement(
                    "select sum(detail_periksa_lab.bagian_rs) as bagian_rs,sum(detail_periksa_lab.bhp) as bhp,sum(detail_periksa_lab.bagian_perujuk) as bagian_perujuk,"+
                    "sum(detail_periksa_lab.bagian_dokter) as bagian_dokter,sum(detail_periksa_lab.bagian_laborat) as bagian_laborat,sum(detail_periksa_lab.kso) as kso,"+
                    "sum(detail_periksa_lab.menejemen) as menejemen,sum(detail_periksa_lab.biaya_item) as biaya_item from detail_periksa_lab inner join periksa_lab on "+
                    "detail_periksa_lab.no_rawat=periksa_lab.no_rawat and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa "+
                    "and detail_periksa_lab.jam=periksa_lab.jam where detail_periksa_lab.no_rawat=? and periksa_lab.status='Ralan'");
            try {
                ps2.setString(1,norawat);
                rs2=ps2.executeQuery();
                if(rs2.next()){
                    bagian_rslabralan=bagian_rslabralan+rs2.getDouble("bagian_rs");
                    bhplabralan=bhplabralan+rs2.getDouble("bhp");
                    tarif_perujuklabralan=tarif_perujuklabralan+rs2.getDouble("bagian_perujuk");
                    tarif_tindakan_dokterlabralan=tarif_tindakan_dokterlabralan+rs2.getDouble("bagian_dokter");
                    tarif_tindakan_petugaslabralan=tarif_tindakan_petugaslabralan+rs2.getDouble("bagian_laborat");
                    ksolabralan=ksolabralan+rs2.getDouble("kso");
                    menejemenlabralan=menejemenlabralan+rs2.getDouble("menejemen");
                    biayalabralan=biayalabralan+rs2.getDouble("biaya_item");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
            //cek radiologi ralan
            ps2=koneksi.prepareStatement(
                    "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                    "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_radiologi "+
                    "where no_rawat=? and status='Ralan'");
            try {
                ps2.setString(1,norawat);
                rs2=ps2.executeQuery();
                if(rs2.next()){
                    bagian_rsradiologiralan=rs2.getDouble("bagian_rs");
                    bhpradiologiralan=rs2.getDouble("bhp");
                    tarif_perujukradiologiralan=rs2.getDouble("tarif_perujuk");
                    tarif_tindakan_dokterradiologiralan=rs2.getDouble("tarif_tindakan_dokter");
                    tarif_tindakan_petugasradiologiralan=rs2.getDouble("tarif_tindakan_petugas");
                    ksoradiologiralan=rs2.getDouble("kso");
                    menejemenradiologiralan=rs2.getDouble("menejemen");
                    biayaradiologiralan=rs2.getDouble("biaya");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
            //cek operasi ralan
            ps2=koneksi.prepareStatement(
                    "select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as jmdokter, "+
                    "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) as jmparamedis,"+
                    "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayaalat+biayasewaok+akomodasi+bagian_rs+biayasarpras+biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as pendapatan "+
                    "from operasi where no_rawat=? and status='Ralan'");
            try {
                ps2.setString(1,norawat);
                rs2=ps2.executeQuery();
                if(rs2.next()){
                    jmdokteroperasiralan=rs2.getDouble("jmdokter");
                    jmparamedisoperasiralan=rs2.getDouble("jmparamedis");
                    pendapatanoperasiralan=rs2.getDouble("pendapatan");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
            bhpoperasiralan=Sequel.cariIsiAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi inner join operasi on beri_obat_operasi.no_rawat=operasi.no_rawat and beri_obat_operasi.tanggal=operasi.tgl_operasi where operasi.status='Ralan' and beri_obat_operasi.no_rawat=?",norawat);
            //cek obat rawat jalan
            ps2=koneksi.prepareStatement("select sum(h_beli*jml) as hpp,sum(total) as total from detail_pemberian_obat where no_rawat=? and status='Ralan'");
            try {
                ps2.setString(1,norawat);
                rs2=ps2.executeQuery();
                while(rs2.next()){
                    obatralan=rs2.getDouble("total");
                    hppobatralan=rs2.getDouble("hpp");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
            if(status.equals("Ranap")){
                //cek rawat inap
                ps2=koneksi.prepareStatement(
                        "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                        "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_drpr where no_rawat=?");
                try {
                    ps2.setString(1,norawat);
                    rs2=ps2.executeQuery();
                    if(rs2.next()){
                        materialranap=rs2.getDouble("material");
                        bhpranap=rs2.getDouble("bhp");
                        tarif_tindakandrranap=rs2.getDouble("tarif_tindakandr");
                        tarif_tindakanprranap=rs2.getDouble("tarif_tindakanpr");
                        ksoranap=rs2.getDouble("kso");
                        menejemenranap=rs2.getDouble("menejemen");
                        biaya_rawatranap=rs2.getDouble("biaya_rawat");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                ps2=koneksi.prepareStatement(
                        "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                        "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_dr where no_rawat=?");
                try {
                    ps2.setString(1,norawat);
                    rs2=ps2.executeQuery();
                    if(rs2.next()){
                        materialranap=+materialranap+rs2.getDouble("material");
                        bhpranap=bhpranap+rs2.getDouble("bhp");
                        tarif_tindakandrranap=tarif_tindakandrranap+rs2.getDouble("tarif_tindakandr");
                        ksoranap=ksoranap+rs2.getDouble("kso");
                        menejemenranap=menejemenranap+rs2.getDouble("menejemen");
                        biaya_rawatranap=biaya_rawatranap+rs2.getDouble("biaya_rawat");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                ps2=koneksi.prepareStatement(
                        "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                        "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_pr where no_rawat=?");
                try {
                    ps2.setString(1,norawat);
                    rs2=ps2.executeQuery();
                    if(rs2.next()){
                        materialranap=+materialranap+rs2.getDouble("material");
                        bhpranap=bhpranap+rs2.getDouble("bhp");
                        tarif_tindakanprranap=tarif_tindakanprranap+rs2.getDouble("tarif_tindakanpr");
                        ksoranap=ksoranap+rs2.getDouble("kso");
                        menejemenranap=menejemenranap+rs2.getDouble("menejemen");
                        biaya_rawatranap=biaya_rawatranap+rs2.getDouble("biaya_rawat");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                //cek lab ranap
                ps2=koneksi.prepareStatement(
                        "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                        "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_lab "+
                        "where no_rawat=? and status='Ranap'");
                try {
                    ps2.setString(1,norawat);
                    rs2=ps2.executeQuery();
                    if(rs2.next()){
                        bagian_rslabranap=rs2.getDouble("bagian_rs");
                        bhplabranap=rs2.getDouble("bhp");
                        tarif_perujuklabranap=rs2.getDouble("tarif_perujuk");
                        tarif_tindakan_dokterlabranap=rs2.getDouble("tarif_tindakan_dokter");
                        tarif_tindakan_petugaslabranap=rs2.getDouble("tarif_tindakan_petugas");
                        ksolabranap=rs2.getDouble("kso");
                        menejemenlabranap=rs2.getDouble("menejemen");
                        biayalabranap=rs2.getDouble("biaya");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                ps2=koneksi.prepareStatement(
                        "select sum(detail_periksa_lab.bagian_rs) as bagian_rs,sum(detail_periksa_lab.bhp) as bhp,sum(detail_periksa_lab.bagian_perujuk) as bagian_perujuk,"+
                        "sum(detail_periksa_lab.bagian_dokter) as bagian_dokter,sum(detail_periksa_lab.bagian_laborat) as bagian_laborat,sum(detail_periksa_lab.kso) as kso,"+
                        "sum(detail_periksa_lab.menejemen) as menejemen,sum(detail_periksa_lab.biaya_item) as biaya_item from detail_periksa_lab inner join periksa_lab on "+
                        "detail_periksa_lab.no_rawat=periksa_lab.no_rawat and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa "+
                        "and detail_periksa_lab.jam=periksa_lab.jam where detail_periksa_lab.no_rawat=? and periksa_lab.status='Ranap'");
                try {
                    ps2.setString(1,norawat);
                    rs2=ps2.executeQuery();
                    if(rs2.next()){
                        bagian_rslabranap=bagian_rslabranap+rs2.getDouble("bagian_rs");
                        bhplabranap=bhplabranap+rs2.getDouble("bhp");
                        tarif_perujuklabranap=tarif_perujuklabranap+rs2.getDouble("bagian_perujuk");
                        tarif_tindakan_dokterlabranap=tarif_tindakan_dokterlabranap+rs2.getDouble("bagian_dokter");
                        tarif_tindakan_petugaslabranap=tarif_tindakan_petugaslabranap+rs2.getDouble("bagian_laborat");
                        ksolabranap=ksolabranap+rs2.getDouble("kso");
                        menejemenlabranap=menejemenlabranap+rs2.getDouble("menejemen");
                        biayalabranap=biayalabranap+rs2.getDouble("biaya_item");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                //cek radiologi ranap
                ps2=koneksi.prepareStatement(
                        "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                        "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_radiologi "+
                        "where no_rawat=? and status='Ranap'");
                try {
                    ps2.setString(1,norawat);
                    rs2=ps2.executeQuery();
                    if(rs2.next()){
                        bagian_rsradiologiranap=rs2.getDouble("bagian_rs");
                        bhpradiologiranap=rs2.getDouble("bhp");
                        tarif_perujukradiologiranap=rs2.getDouble("tarif_perujuk");
                        tarif_tindakan_dokterradiologiranap=rs2.getDouble("tarif_tindakan_dokter");
                        tarif_tindakan_petugasradiologiranap=rs2.getDouble("tarif_tindakan_petugas");
                        ksoradiologiranap=rs2.getDouble("kso");
                        menejemenradiologiranap=rs2.getDouble("menejemen");
                        biayaradiologiranap=rs2.getDouble("biaya");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                //cek operasi ranap
                ps2=koneksi.prepareStatement(
                        "select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as jmdokter, "+
                        "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) as jmparamedis,"+
                        "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayaalat+biayasewaok+akomodasi+bagian_rs+biayasarpras+biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as pendapatan "+
                        "from operasi where no_rawat=? and status='Ranap'");
                try {
                    ps2.setString(1,norawat);
                    rs2=ps2.executeQuery();
                    if(rs2.next()){
                        jmdokteroperasiranap=rs2.getDouble("jmdokter");
                        jmparamedisoperasiranap=rs2.getDouble("jmparamedis");
                        pendapatanoperasiranap=rs2.getDouble("pendapatan");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                bhpoperasiranap=Sequel.cariIsiAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi inner join operasi on beri_obat_operasi.no_rawat=operasi.no_rawat and beri_obat_operasi.tanggal=operasi.tgl_operasi where operasi.status='Ranap' and beri_obat_operasi.no_rawat=?",norawat);
                //cek obat rawat ranap
                ps2=koneksi.prepareStatement("select sum(h_beli*jml) as hpp,sum(total) as total from detail_pemberian_obat where no_rawat=? and status='Ranap'");
                try {
                    ps2.setString(1,norawat);
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        obatranap=rs2.getDouble("total");
                        hppobatranap=rs2.getDouble("hpp");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
                //cek retur obat
                returobat=Sequel.cariIsiAngka("select sum(detreturjual.subtotal) from detreturjual where no_retur_jual like ?","%"+norawat+"%");
                //cek kamar 
                kamar=Sequel.cariIsiAngka("select sum(totalbiaya) from billing where status='Kamar' and no_rawat=?",norawat);
                //cek harian 
                harianranap=Sequel.cariIsiAngka("select sum(totalbiaya) from billing where status='Harian' and no_rawat=?",norawat);
                //cek service 
                serviceranap=Sequel.cariIsiAngka("select sum(totalbiaya) from billing where status='Service' and no_rawat=?",norawat);
                //cek resep pulang 
                reseppulang=Sequel.cariIsiAngka("select sum(total) from resep_pulang where no_rawat=?",norawat);
                norawatbayi=Sequel.cariIsi("select no_rawat2 from ranap_gabung where no_rawat=?",norawat);
                if(!norawatbayi.equals("")){
                    //cek obat langsung bayi
                    obatlangsung=obatlangsung+Sequel.cariIsiAngka("select besar_tagihan from tagihan_obat_langsung where no_rawat=? ",norawatbayi);
                    //cek tambahan biaya bayi
                    tambahanbiaya=tambahanbiaya+Sequel.cariIsiAngka("select sum(besar_biaya) from tambahan_biaya where no_rawat=? ",norawatbayi);
                    //cek potongan biaya bayi
                    potonganbiaya=potonganbiaya+Sequel.cariIsiAngka("select sum(besar_pengurangan) from pengurangan_biaya where no_rawat=? ",norawatbayi);
                    //cek rawat jalan bayi
                    ps2=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_drpr where no_rawat=?");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            materialralan=materialralan+rs2.getDouble("material");
                            bhpralan=bhpralan+rs2.getDouble("bhp");
                            tarif_tindakandrralan=tarif_tindakandrralan+rs2.getDouble("tarif_tindakandr");
                            tarif_tindakanprralan=tarif_tindakanprralan+rs2.getDouble("tarif_tindakanpr");
                            ksoralan=ksoralan+rs2.getDouble("kso");
                            menejemenralan=menejemenralan+rs2.getDouble("menejemen");
                            biaya_rawatralan=biaya_rawatralan+rs2.getDouble("biaya_rawat");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps2=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_dr where no_rawat=?");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            materialralan=+materialralan+rs2.getDouble("material");
                            bhpralan=bhpralan+rs2.getDouble("bhp");
                            tarif_tindakandrralan=tarif_tindakandrralan+rs2.getDouble("tarif_tindakandr");
                            ksoralan=ksoralan+rs2.getDouble("kso");
                            menejemenralan=menejemenralan+rs2.getDouble("menejemen");
                            biaya_rawatralan=biaya_rawatralan+rs2.getDouble("biaya_rawat");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps2=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_jl_pr where no_rawat=?");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            materialralan=+materialralan+rs2.getDouble("material");
                            bhpralan=bhpralan+rs2.getDouble("bhp");
                            tarif_tindakanprralan=tarif_tindakanprralan+rs2.getDouble("tarif_tindakanpr");
                            ksoralan=ksoralan+rs2.getDouble("kso");
                            menejemenralan=menejemenralan+rs2.getDouble("menejemen");
                            biaya_rawatralan=biaya_rawatralan+rs2.getDouble("biaya_rawat");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //cek lab ralan bayi
                    ps2=koneksi.prepareStatement(
                            "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                            "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_lab "+
                            "where no_rawat=? and status='Ralan'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            bagian_rslabralan=bagian_rslabralan+rs2.getDouble("bagian_rs");
                            bhplabralan=bhplabralan+rs2.getDouble("bhp");
                            tarif_perujuklabralan=tarif_perujuklabralan+rs2.getDouble("tarif_perujuk");
                            tarif_tindakan_dokterlabralan=tarif_tindakan_dokterlabralan+rs2.getDouble("tarif_tindakan_dokter");
                            tarif_tindakan_petugaslabralan=tarif_tindakan_petugaslabralan+rs2.getDouble("tarif_tindakan_petugas");
                            ksolabralan=ksolabralan+rs2.getDouble("kso");
                            menejemenlabralan=menejemenlabralan+rs2.getDouble("menejemen");
                            biayalabralan=biayalabralan+rs2.getDouble("biaya");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps2=koneksi.prepareStatement(
                            "select sum(detail_periksa_lab.bagian_rs) as bagian_rs,sum(detail_periksa_lab.bhp) as bhp,sum(detail_periksa_lab.bagian_perujuk) as bagian_perujuk,"+
                            "sum(detail_periksa_lab.bagian_dokter) as bagian_dokter,sum(detail_periksa_lab.bagian_laborat) as bagian_laborat,sum(detail_periksa_lab.kso) as kso,"+
                            "sum(detail_periksa_lab.menejemen) as menejemen,sum(detail_periksa_lab.biaya_item) as biaya_item from detail_periksa_lab inner join periksa_lab on "+
                            "detail_periksa_lab.no_rawat=periksa_lab.no_rawat and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa "+
                            "and detail_periksa_lab.jam=periksa_lab.jam where detail_periksa_lab.no_rawat=? and periksa_lab.status='Ralan'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            bagian_rslabralan=bagian_rslabralan+rs2.getDouble("bagian_rs");
                            bhplabralan=bhplabralan+rs2.getDouble("bhp");
                            tarif_perujuklabralan=tarif_perujuklabralan+rs2.getDouble("bagian_perujuk");
                            tarif_tindakan_dokterlabralan=tarif_tindakan_dokterlabralan+rs2.getDouble("bagian_dokter");
                            tarif_tindakan_petugaslabralan=tarif_tindakan_petugaslabralan+rs2.getDouble("bagian_laborat");
                            ksolabralan=ksolabralan+rs2.getDouble("kso");
                            menejemenlabralan=menejemenlabralan+rs2.getDouble("menejemen");
                            biayalabralan=biayalabralan+rs2.getDouble("biaya_item");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //cek radiologi ralan bayi
                    ps2=koneksi.prepareStatement(
                            "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                            "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_radiologi "+
                            "where no_rawat=? and status='Ralan'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            bagian_rsradiologiralan=bagian_rsradiologiralan+rs2.getDouble("bagian_rs");
                            bhpradiologiralan=bhpradiologiralan+rs2.getDouble("bhp");
                            tarif_perujukradiologiralan=tarif_perujukradiologiralan+rs2.getDouble("tarif_perujuk");
                            tarif_tindakan_dokterradiologiralan=tarif_tindakan_dokterradiologiralan+rs2.getDouble("tarif_tindakan_dokter");
                            tarif_tindakan_petugasradiologiralan=tarif_tindakan_petugasradiologiralan+rs2.getDouble("tarif_tindakan_petugas");
                            ksoradiologiralan=ksoradiologiralan+rs2.getDouble("kso");
                            menejemenradiologiralan=menejemenradiologiralan+rs2.getDouble("menejemen");
                            biayaradiologiralan=biayaradiologiralan+rs2.getDouble("biaya");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //cek operasi ralan hayi
                    ps2=koneksi.prepareStatement(
                            "select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as jmdokter, "+
                            "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) as jmparamedis,"+
                            "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayaalat+biayasewaok+akomodasi+bagian_rs+biayasarpras+biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as pendapatan "+
                            "from operasi where no_rawat=? and status='Ralan'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            jmdokteroperasiralan=jmdokteroperasiralan+rs2.getDouble("jmdokter");
                            jmparamedisoperasiralan=jmparamedisoperasiralan+rs2.getDouble("jmparamedis");
                            pendapatanoperasiralan=pendapatanoperasiralan+rs2.getDouble("pendapatan");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    bhpoperasiralan=bhpoperasiralan+Sequel.cariIsiAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi inner join operasi on beri_obat_operasi.no_rawat=operasi.no_rawat and beri_obat_operasi.tanggal=operasi.tgl_operasi where operasi.status='Ralan' and beri_obat_operasi.no_rawat=?",norawatbayi);
                    //cek obat rawat jalan bayi
                    ps2=koneksi.prepareStatement("select sum(h_beli*jml) as hpp,sum(total) as total from detail_pemberian_obat where no_rawat=? and status='Ralan'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            obatralan=obatralan+rs2.getDouble("total");
                            hppobatralan=hppobatralan+rs2.getDouble("hpp");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //cek rawat inap bayi
                    ps2=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_drpr where no_rawat=?");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            materialranap=materialranap+rs2.getDouble("material");
                            bhpranap=bhpranap+rs2.getDouble("bhp");
                            tarif_tindakandrranap=tarif_tindakandrranap+rs2.getDouble("tarif_tindakandr");
                            tarif_tindakanprranap=tarif_tindakanprranap+rs2.getDouble("tarif_tindakanpr");
                            ksoranap=ksoranap+rs2.getDouble("kso");
                            menejemenranap=menejemenranap+rs2.getDouble("menejemen");
                            biaya_rawatranap=biaya_rawatranap+rs2.getDouble("biaya_rawat");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps2=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakandr) as tarif_tindakandr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_dr where no_rawat=?");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            materialranap=+materialranap+rs2.getDouble("material");
                            bhpranap=bhpranap+rs2.getDouble("bhp");
                            tarif_tindakandrranap=tarif_tindakandrranap+rs2.getDouble("tarif_tindakandr");
                            ksoranap=ksoranap+rs2.getDouble("kso");
                            menejemenranap=menejemenranap+rs2.getDouble("menejemen");
                            biaya_rawatranap=biaya_rawatranap+rs2.getDouble("biaya_rawat");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps2=koneksi.prepareStatement(
                            "select sum(material) as material,sum(bhp) as bhp,sum(tarif_tindakanpr) as tarif_tindakanpr,"+
                            "sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya_rawat) as biaya_rawat from rawat_inap_pr where no_rawat=?");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            materialranap=+materialranap+rs2.getDouble("material");
                            bhpranap=bhpranap+rs2.getDouble("bhp");
                            tarif_tindakanprranap=tarif_tindakanprranap+rs2.getDouble("tarif_tindakanpr");
                            ksoranap=ksoranap+rs2.getDouble("kso");
                            menejemenranap=menejemenranap+rs2.getDouble("menejemen");
                            biaya_rawatranap=biaya_rawatranap+rs2.getDouble("biaya_rawat");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //cek lab ranap bayi
                    ps2=koneksi.prepareStatement(
                            "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                            "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_lab "+
                            "where no_rawat=? and status='Ranap'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            bagian_rslabranap=bagian_rslabranap+rs2.getDouble("bagian_rs");
                            bhplabranap=bhplabranap+rs2.getDouble("bhp");
                            tarif_perujuklabranap=tarif_perujuklabranap+rs2.getDouble("tarif_perujuk");
                            tarif_tindakan_dokterlabranap=tarif_tindakan_dokterlabranap+rs2.getDouble("tarif_tindakan_dokter");
                            tarif_tindakan_petugaslabranap=tarif_tindakan_petugaslabranap+rs2.getDouble("tarif_tindakan_petugas");
                            ksolabranap=ksolabranap+rs2.getDouble("kso");
                            menejemenlabranap=menejemenlabranap+rs2.getDouble("menejemen");
                            biayalabranap=biayalabranap+rs2.getDouble("biaya");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps2=koneksi.prepareStatement(
                            "select sum(detail_periksa_lab.bagian_rs) as bagian_rs,sum(detail_periksa_lab.bhp) as bhp,sum(detail_periksa_lab.bagian_perujuk) as bagian_perujuk,"+
                            "sum(detail_periksa_lab.bagian_dokter) as bagian_dokter,sum(detail_periksa_lab.bagian_laborat) as bagian_laborat,sum(detail_periksa_lab.kso) as kso,"+
                            "sum(detail_periksa_lab.menejemen) as menejemen,sum(detail_periksa_lab.biaya_item) as biaya_item from detail_periksa_lab inner join periksa_lab on "+
                            "detail_periksa_lab.no_rawat=periksa_lab.no_rawat and detail_periksa_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw and detail_periksa_lab.tgl_periksa=periksa_lab.tgl_periksa "+
                            "and detail_periksa_lab.jam=periksa_lab.jam where detail_periksa_lab.no_rawat=? and periksa_lab.status='Ranap'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            bagian_rslabranap=bagian_rslabranap+rs2.getDouble("bagian_rs");
                            bhplabranap=bhplabranap+rs2.getDouble("bhp");
                            tarif_perujuklabranap=tarif_perujuklabranap+rs2.getDouble("bagian_perujuk");
                            tarif_tindakan_dokterlabranap=tarif_tindakan_dokterlabranap+rs2.getDouble("bagian_dokter");
                            tarif_tindakan_petugaslabranap=tarif_tindakan_petugaslabranap+rs2.getDouble("bagian_laborat");
                            ksolabranap=ksolabranap+rs2.getDouble("kso");
                            menejemenlabranap=menejemenlabranap+rs2.getDouble("menejemen");
                            biayalabranap=biayalabranap+rs2.getDouble("biaya_item");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //cek radiologi ranap bayi
                    ps2=koneksi.prepareStatement(
                            "select sum(bagian_rs) as bagian_rs,sum(bhp) as bhp,sum(tarif_perujuk) as tarif_perujuk,sum(tarif_tindakan_dokter) as tarif_tindakan_dokter,"+
                            "sum(tarif_tindakan_petugas) as tarif_tindakan_petugas,sum(kso) as kso,sum(menejemen) as menejemen,sum(biaya) as biaya from periksa_radiologi "+
                            "where no_rawat=? and status='Ranap'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            bagian_rsradiologiranap=bagian_rsradiologiranap+rs2.getDouble("bagian_rs");
                            bhpradiologiranap=bhpradiologiranap+rs2.getDouble("bhp");
                            tarif_perujukradiologiranap=tarif_perujukradiologiranap+rs2.getDouble("tarif_perujuk");
                            tarif_tindakan_dokterradiologiranap=tarif_tindakan_dokterradiologiranap+rs2.getDouble("tarif_tindakan_dokter");
                            tarif_tindakan_petugasradiologiranap=tarif_tindakan_petugasradiologiranap+rs2.getDouble("tarif_tindakan_petugas");
                            ksoradiologiranap=ksoradiologiranap+rs2.getDouble("kso");
                            menejemenradiologiranap=menejemenradiologiranap+rs2.getDouble("menejemen");
                            biayaradiologiranap=biayaradiologiranap+rs2.getDouble("biaya");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //cek operasi ranap bayi
                    ps2=koneksi.prepareStatement(
                            "select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as jmdokter, "+
                            "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) as jmparamedis,"+
                            "sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayaalat+biayasewaok+akomodasi+bagian_rs+biayasarpras+biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) as pendapatan "+
                            "from operasi where no_rawat=? and status='Ranap'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            jmdokteroperasiranap=jmdokteroperasiranap+rs2.getDouble("jmdokter");
                            jmparamedisoperasiranap=jmparamedisoperasiranap+rs2.getDouble("jmparamedis");
                            pendapatanoperasiranap=pendapatanoperasiranap+rs2.getDouble("pendapatan");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    bhpoperasiranap=bhpoperasiranap+Sequel.cariIsiAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi inner join operasi on beri_obat_operasi.no_rawat=operasi.no_rawat and beri_obat_operasi.tanggal=operasi.tgl_operasi where operasi.status='Ranap' and beri_obat_operasi.no_rawat=?",norawatbayi);
                    //cek obat rawat ranap bayi
                    ps2=koneksi.prepareStatement("select sum(h_beli*jml) as hpp,sum(total) as total from detail_pemberian_obat where no_rawat=? and status='Ranap'");
                    try {
                        ps2.setString(1,norawatbayi);
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            obatranap=obatranap+rs2.getDouble("total");
                            hppobatranap=hppobatranap+rs2.getDouble("hpp");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //cek retur obat bayi
                    returobat=returobat+Sequel.cariIsiAngka("select sum(detreturjual.subtotal) from detreturjual where no_retur_jual like ?","%"+norawatbayi+"%");
                    //cek resep pulang bayi
                    reseppulang=reseppulang+Sequel.cariIsiAngka("select sum(total) from resep_pulang where no_rawat=?",norawatbayi);
                }
            }
            if(status.equals("Ralan")){
                if(tampilkan_ppnobat_ralan.equals("Yes")){
                    obatlangsung=obatlangsung+(Valid.roundUp(obatralan*0.1,100));
                }
            }else if(status.equals("Ranap")){
                if(tampilkan_ppnobat_ranap.equals("Yes")){
                    obatlangsung=obatlangsung+(Valid.roundUp((obatralan+obatranap-returobat)*0.1,100));
                }
                if(tampilkan_administrasi_di_billingranap.equals("No")){
                    registrasi=0;
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void tampilAkunBayar() {         
         try{      
             file=new File("./cache/akunbayar.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             iyem="";
             ps=koneksi.prepareStatement("select * from akun_bayar order by nama_bayar");
             try{
                 rs=ps.executeQuery();
                 AkunBayar.removeAllItems();
                 while(rs.next()){    
                     AkunBayar.addItem(rs.getString(1).replaceAll("\"",""));
                     iyem=iyem+"{\"NamaAkun\":\""+rs.getString(1).replaceAll("\"","")+"\",\"KodeRek\":\""+rs.getString(2)+"\",\"PPN\":\""+rs.getDouble(3)+"\"},";
                 }
             }catch (Exception e) {
                 System.out.println("Notifikasi : "+e);
             } finally{
                 if(rs != null){
                     rs.close();
                 } 
                 if(ps != null){
                     ps.close();
                 } 
             }

             fileWriter.write("{\"akunbayar\":["+iyem.substring(0,iyem.length()-1)+"]}");
             fileWriter.flush();
             fileWriter.close();
             iyem=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
}
