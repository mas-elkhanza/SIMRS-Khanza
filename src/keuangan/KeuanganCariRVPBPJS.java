

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
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public final class KeuanganCariRVPBPJS extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int row=0,i;
    private String koderekening="";
    private Jurnal jur=new Jurnal();
    private String Piutang_BPJS_RVP="",Kerugian_Klaim_BPJS_RVP="",Lebih_Bayar_Klaim_BPJS_RVP="",Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",
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
                   Harian_Ranap="";
    private double total=0,sisapiutang=0,rugihppralan=0,rugihppranap=0,ttlpiutang=0,ttliur=0,ttlsudahdibayar=0,ttlsisapiutang=0,ttlinacbg=0,rugi=0,lebih=0;
    private boolean sukses=true;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganCariRVPBPJS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","No.Rawat/No.tagihan","No.SEP VClaim","Tgl.Bayar","Pasien","Total Piutang","Iur/Ekses",
                "Sudah Dibayar","Sisa Piutang","Tarif InaCBG","Dibayar BPJS","% Bayar","Kerugian","Lebih Bayar",
                "Status","materialralan","bhpralan","tarif_tindakandrralan","tarif_tindakanprralan",
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
                "tambahanbiaya","potonganbiaya","kamar","reseppulang","harianranap","registrasi","Petugas Validasi",
                "Akun Rekening","Kontra Akun","Service Ranap"
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
                java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
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

        for (i = 0; i < 85; i++) {
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
            }else if(i==81){
                column.setPreferredWidth(170);
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
        ppBersihkan = new javax.swing.JMenuItem();
        ppPilihSemua = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        LCount2 = new javax.swing.JLabel();
        panelisi1 = new widget.panelisi();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel15 = new javax.swing.JLabel();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data RVP Piutang BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(99, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelisi3.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Piutang BPJS :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi3.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(140, 23));
        panelisi3.add(LCount);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(50, 50, 50));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Dibayar BPJS :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(jLabel13);

        LCount2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount2.setForeground(new java.awt.Color(50, 50, 50));
        LCount2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount2.setText("0");
        LCount2.setName("LCount2"); // NOI18N
        LCount2.setPreferredSize(new java.awt.Dimension(140, 23));
        panelisi3.add(LCount2);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(62, 23));
        panelisi1.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(250, 23));
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
            int row=tabMode.getRowCount();
            ttlpiutang=0;ttliur=0;ttlsudahdibayar=0;ttlsisapiutang=0;ttlinacbg=0;total=0;rugi=0;lebih=0;
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
                rugi=rugi+Double.parseDouble(tabMode.getValueAt(i,12).toString());
                lebih=lebih+Double.parseDouble(tabMode.getValueAt(i,13).toString());
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
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,11).toString()))+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,12).toString()))+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,13).toString()))+"','','','','','','','','','','','','','','','','','','','','','','','',''","RVP Piutang"); 
            }
            Sequel.menyimpan("temporary","'0','Total :','','','','"+Valid.SetAngka(ttlpiutang)+"','"+Valid.SetAngka(ttliur)+"','"+Valid.SetAngka(ttlsudahdibayar)+"','"+Valid.SetAngka(ttlsisapiutang)+"','"+Valid.SetAngka(ttlinacbg)+"','"+Valid.SetAngka(total)+"','','"+Valid.SetAngka(rugi)+"','"+Valid.SetAngka(lebih)+"','','','','','','','','','','','','','','','','','','','','','','','',''","RVP Piutangr"); 
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptRVPPiutang2.jasper","report","::[ Data RVP Piutang BPJS ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnBayar, BtnAll);
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            if(tbBangsal.getSelectedColumn()==0){
                getdata();
            }
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbBangsalPropertyChange
        
    }//GEN-LAST:event_tbBangsalPropertyChange

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        //Valid.pindah(evt, Tgl1,kddokter);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis..!!!!");
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.AutoComitFalse();
            sukses=true;
            row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                if(tabMode.getValueAt(i,0).toString().equals("true")){
                    Sequel.mengedit("piutang_pasien","no_rawat='"+tabMode.getValueAt(i,1).toString()+"'","status='Belum Lunas'");
                    Sequel.mengedit("detail_piutang_pasien","no_rawat='"+tabMode.getValueAt(i,1).toString()+"' and nama_bayar='"+Sequel.cariIsi("select nama_bayar from akun_piutang where kd_rek=?",tabMode.getValueAt(i,83).toString())+"'","sisapiutang='"+tabMode.getValueAt(i,8).toString()+"'");
                    if(Valid.SetAngka(tabMode.getValueAt(i,11).toString())>=100){
                        Sequel.queryu("delete from tampjurnal"); 
                        Sequel.menyimpan("tampjurnal","'"+tabMode.getValueAt(i,83).toString()+"','PIUTANG BPJS','"+tabMode.getValueAt(i,8).toString()+"','0'","debet=debet+'"+tabMode.getValueAt(i,8).toString()+"'","kd_rek='"+tabMode.getValueAt(i,83).toString()+"'");     
                        if(Valid.SetAngka(tabMode.getValueAt(i,13).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Lebih_Bayar_Klaim_BPJS_RVP+"','LEBIH BAYAR BPJS','"+tabMode.getValueAt(i,13).toString()+"','0'","debet=debet+'"+tabMode.getValueAt(i,13).toString()+"'","kd_rek='"+Lebih_Bayar_Klaim_BPJS_RVP+"'"); 
                        }
                        Sequel.menyimpan("tampjurnal","'"+tabMode.getValueAt(i,82).toString()+"','Akun Bayar','0','"+tabMode.getValueAt(i,10).toString()+"'","kredit=kredit+'"+tabMode.getValueAt(i,82).toString()+"'","kd_rek='"+koderekening+"'"); 
                        sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","PEMBATALAN RVP PIUTANG BPJS"+", OLEH "+akses.getkode());      
                    }else if(Valid.SetAngka(tabMode.getValueAt(i,11).toString())<100){
                        Sequel.queryu("delete from tampjurnal");
                        //tindakan ralan
                        if(Valid.SetAngka(tabMode.getValueAt(i,15).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,15).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,15).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,15).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,15).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,17).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban_Jasa_Medik_Dokter_Tindakan_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,17).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,17).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang_Jasa_Medik_Dokter_Tindakan_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,17).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,17).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,18).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban_Jasa_Medik_Paramedis_Tindakan_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang_Jasa_Medik_Paramedis_Tindakan_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,19).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban_KSO_Tindakan_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang_KSO_Tindakan_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,20).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban_Jasa_Menejemen_Tindakan_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang_Jasa_Menejemen_Tindakan_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,21).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','PENDAPATAN RAWAT JALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"'","kd_rek='"+Tindakan_Ralan+"'");   
                        }
                        //tindakan ranap
                        if(Valid.SetAngka(tabMode.getValueAt(i,22).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban_Jasa_Sarana_Tindakan_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang_Jasa_Sarana_Tindakan_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,24).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban_Jasa_Medik_Dokter_Tindakan_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,24).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,24).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang_Jasa_Medik_Dokter_Tindakan_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,24).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,24).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,25).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban_Jasa_Medik_Paramedis_Tindakan_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang_Jasa_Medik_Paramedis_Tindakan_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,26).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban_KSO_Tindakan_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang_KSO_Tindakan_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,27).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban_Jasa_Menejemen_Tindakan_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang_Jasa_Menejemen_Tindakan_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,28).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','PENDAPATAN RAWAT INAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"'","kd_rek='"+Tindakan_Ranap+"'");   
                        }
                        //laborat ralan
                        if(Valid.SetAngka(tabMode.getValueAt(i,29).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ralan+"','Beban_Jasa_Sarana_Laborat_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ralan+"','Utang_Jasa_Sarana_Laborat_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,31).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ralan+"','Beban_Jasa_Perujuk_Laborat_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,31).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,31).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ralan+"','Utang_Jasa_Perujuk_Laborat_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,31).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,31).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,32).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"','Beban_Jasa_Medik_Dokter_Laborat_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang_Jasa_Medik_Dokter_Laborat_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,33).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"','Beban_Jasa_Medik_Petugas_Laborat_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"','Utang_Jasa_Medik_Petugas_Laborat_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,34).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ralan+"','Beban_Kso_Laborat_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"'","kd_rek='"+Beban_Kso_Laborat_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ralan+"','Utang_Kso_Laborat_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"'","kd_rek='"+Utang_Kso_Laborat_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,35).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ralan+"','Beban_Jasa_Menejemen_Laborat_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ralan+"','Utang_Jasa_Menejemen_Laborat_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,36).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','PENDAPATAN LABORAT RAWAT JALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"'","kd_rek='"+Laborat_Ralan+"'");   
                        }
                        //laborat ranap
                        if(Valid.SetAngka(tabMode.getValueAt(i,37).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ranap+"','Beban_Jasa_Sarana_Laborat_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ranap+"','Utang_Jasa_Sarana_Laborat_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,39).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ranap+"','Beban_Jasa_Perujuk_Laborat_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,39).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,39).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ranap+"','Utang_Jasa_Perujuk_Laborat_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,39).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,39).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,40).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"','Beban_Jasa_Medik_Dokter_Laborat_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang_Jasa_Medik_Dokter_Laborat_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,41).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"','Beban_Jasa_Medik_Petugas_Laborat_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"','Utang_Jasa_Medik_Petugas_Laborat_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,42).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ranap+"','Beban_Kso_Laborat_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"'","kd_rek='"+Beban_Kso_Laborat_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ranap+"','Utang_Kso_Laborat_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"'","kd_rek='"+Utang_Kso_Laborat_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,43).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ranap+"','Beban_Jasa_Menejemen_Laborat_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ranap+"','Utang_Jasa_Menejemen_Laborat_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,44).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','PENDAPATAN LABORAT RAWAT INAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"'","kd_rek='"+Laborat_Ranap+"'");   
                        }
                        //radiologi ralan
                        if(Valid.SetAngka(tabMode.getValueAt(i,45).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ralan+"','Beban_Jasa_Sarana_Radiologi_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ralan+"','Utang_Jasa_Sarana_Radiologi_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,47).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ralan+"','Beban_Jasa_Perujuk_Radiologi_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,47).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,47).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ralan+"','Utang_Jasa_Perujuk_Radiologi_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,47).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,47).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,48).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"','Beban_Jasa_Medik_Dokter_Radiologi_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Utang_Jasa_Medik_Dokter_Radiologi_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,49).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"','Beban_Jasa_Medik_Petugas_Radiologi_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"','Utang_Jasa_Medik_Petugas_Radiologi_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,50).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ralan+"','Beban_Kso_Radiologi_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"'","kd_rek='"+Beban_Kso_Radiologi_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ralan+"','Utang_Kso_Radiologi_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"'","kd_rek='"+Utang_Kso_Radiologi_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,51).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ralan+"','Beban_Jasa_Menejemen_Radiologi_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ralan+"','Utang_Jasa_Menejemen_Radiologi_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,52).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Radiologi_Ralan+"','PENDAPATAN RADIOLOGI RAWAT JALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"'","kd_rek='"+Radiologi_Ralan+"'");   
                        }
                        //radiologi ranap
                        if(Valid.SetAngka(tabMode.getValueAt(i,53).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ranap+"','Beban_Jasa_Sarana_Radiologi_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ranap+"','Utang_Jasa_Sarana_Radiologi_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,55).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ranap+"','Beban_Jasa_Perujuk_Radiologi_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,55).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,55).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ranap+"','Utang_Jasa_Perujuk_Radiologi_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,55).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,55).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,56).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"','Beban_Jasa_Medik_Dokter_Radiologi_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"','Utang_Jasa_Medik_Dokter_Radiologi_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,57).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"','Beban_Jasa_Medik_Petugas_Radiologi_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"','Utang_Jasa_Medik_Petugas_Radiologi_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,58).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ranap+"','Beban_Kso_Radiologi_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"'","kd_rek='"+Beban_Kso_Radiologi_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ranap+"','Utang_Kso_Radiologi_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"'","kd_rek='"+Utang_Kso_Radiologi_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,59).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ranap+"','Beban_Jasa_Menejemen_Radiologi_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ranap+"','Utang_Jasa_Menejemen_Radiologi_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,60).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Radiologi_Ranap+"','PENDAPATAN RADIOLOGI RAWAT INAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"'","kd_rek='"+Radiologi_Ranap+"'");   
                        }
                        //operasi ralan
                        if(Valid.SetAngka(tabMode.getValueAt(i,61).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"','Beban_Jasa_Medik_Dokter_Operasi_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"','Utang_Jasa_Medik_Dokter_Operasi_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,62).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"','Beban_Jasa_Medik_Paramedis_Operasi_Ralan','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"','Utang_Jasa_Medik_Paramedis_Operasi_Ralan','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,64).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG RAWAT JALAN','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *(Valid.SetAngka(tabMode.getValueAt(i,63).toString())+Valid.SetAngka(tabMode.getValueAt(i,64).toString())))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *(Valid.SetAngka(tabMode.getValueAt(i,63).toString())+Valid.SetAngka(tabMode.getValueAt(i,64).toString())))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Operasi_Ralan+"','PENDAPATAN OPERASI RAWAT JALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *(Valid.SetAngka(tabMode.getValueAt(i,63).toString())+Valid.SetAngka(tabMode.getValueAt(i,64).toString())))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *(Valid.SetAngka(tabMode.getValueAt(i,63).toString())+Valid.SetAngka(tabMode.getValueAt(i,64).toString())))+"'","kd_rek='"+Operasi_Ralan+"'");   
                        }
                        //operasi ranap
                        if(Valid.SetAngka(tabMode.getValueAt(i,65).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"','Beban_Jasa_Medik_Dokter_Operasi_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang_Jasa_Medik_Dokter_Operasi_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,66).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"','Beban_Jasa_Medik_Paramedis_Operasi_Ranap','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"','Utang_Jasa_Medik_Paramedis_Operasi_Ranap','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"'");   
                        }
                        if(Valid.SetAngka(tabMode.getValueAt(i,68).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *(Valid.SetAngka(tabMode.getValueAt(i,67).toString())+Valid.SetAngka(tabMode.getValueAt(i,68).toString())))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *(Valid.SetAngka(tabMode.getValueAt(i,67).toString())+Valid.SetAngka(tabMode.getValueAt(i,68).toString())))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','PENDAPATAN OPERASI RAWAT INAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *(Valid.SetAngka(tabMode.getValueAt(i,67).toString())+Valid.SetAngka(tabMode.getValueAt(i,68).toString())))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *(Valid.SetAngka(tabMode.getValueAt(i,67).toString())+Valid.SetAngka(tabMode.getValueAt(i,68).toString())))+"'","kd_rek='"+Operasi_Ranap+"'");   
                        }
                        //kamar
                        if(Valid.SetAngka(tabMode.getValueAt(i,77).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Kamar_Inap+"','PENDAPATAN KAMAR INAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kd_rek='"+Kamar_Inap+"'");   
                        }
                        //harian
                        if(Valid.SetAngka(tabMode.getValueAt(i,79).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Harian_Ranap+"','PENDAPATAN HARIAN KAMAR INAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"'","kd_rek='"+Harian_Ranap+"'");   
                        }
                        //registrasi
                        if(Valid.SetAngka(tabMode.getValueAt(i,80).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                Sequel.menyimpan("tampjurnal","'"+Registrasi_Ralan+"','PENDAPATAN REGISTRASI RALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kd_rek='"+Registrasi_Ralan+"'");
                            }else{
                                Sequel.menyimpan("tampjurnal","'"+Registrasi_Ranap+"','PENDAPATAN REGISTRASI RALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kd_rek='"+Registrasi_Ranap+"'");
                            }
                        }
                        //tambahan biaya
                        if(Valid.SetAngka(tabMode.getValueAt(i,75).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                Sequel.menyimpan("tampjurnal","'"+Tambahan_Ralan+"','PENDAPATAN TAMBAHAN RALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kd_rek='"+Tambahan_Ralan+"'");
                            }else{
                                Sequel.menyimpan("tampjurnal","'"+Tambahan_Ranap+"','PENDAPATAN TAMBAHAN RANAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kd_rek='"+Tambahan_Ranap+"'");
                            }
                        }
                        //potongan biaya
                        if(Valid.SetAngka(tabMode.getValueAt(i,76).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                Sequel.menyimpan("tampjurnal","'"+Potongan_Ralan+"','POTONGAN RALAN','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kd_rek='"+Potongan_Ralan+"'");
                            }else{
                                Sequel.menyimpan("tampjurnal","'"+Potongan_Ranap+"','POTONGAN RANAP','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kd_rek='"+Potongan_Ranap+"'");
                            }
                        }
                        //resep pulang
                        if(Valid.SetAngka(tabMode.getValueAt(i,78).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Resep_Pulang_Ranap+"','PENDAPATAN RESEP PULANG','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"'","kd_rek='"+Resep_Pulang_Ranap+"'");   
                        }
                        //obat langsung
                        if(Valid.SetAngka(tabMode.getValueAt(i,69).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','OBAT LANGSUNG RALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kd_rek='"+Obat_Ralan+"'");
                            }else{
                                Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','OBAT LANGSUNG RANAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kd_rek='"+Obat_Ranap+"'");
                            }
                        }
                        //obat ralan
                        if(Valid.SetAngka(tabMode.getValueAt(i,70).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','PENDAPATAN OBAT RALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kd_rek='"+Obat_Ralan+"'");   
                        }
                        //obat ranap
                        if(Valid.SetAngka(tabMode.getValueAt(i,72).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,72).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,72).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','PENDAPATAN OBAT RANAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,72).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,72).toString()))+"'","kd_rek='"+Obat_Ranap+"'");   
                        }
                        //retur obat ranap
                        if(Valid.SetAngka(tabMode.getValueAt(i,74).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,74).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,74).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Retur_Obat_Ranap+"','PENDAPATAN OBAT RANAP','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,74).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,74).toString()))+"'","kd_rek='"+Retur_Obat_Ranap+"'");   
                        }
                        //service
                        if(Valid.SetAngka(tabMode.getValueAt(i,84).toString())>0){
                            Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,84).toString()))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,84).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Service_Ranap+"','PENDAPATAN SERVICE INAP','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,84).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,84).toString()))+"'","kd_rek='"+Service_Ranap+"'");   
                        }
                        //jurnal pembatalan RVP beban, utang, piutang, pendapatan
                        sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","PEMBATALAN RVP PIUTANG BPJS, OLEH "+akses.getkode());     

                        if(sukses==true){
                            Sequel.queryu("delete from tampjurnal");
                            //tindakan ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,15).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,15).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,15).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,15).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,15).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,17).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban_Jasa_Medik_Dokter_Tindakan_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,17).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,17).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang_Jasa_Medik_Dokter_Tindakan_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,17).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,17).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,18).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban_Jasa_Medik_Paramedis_Tindakan_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang_Jasa_Medik_Paramedis_Tindakan_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,18).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,19).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban_KSO_Tindakan_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang_KSO_Tindakan_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,19).toString()))+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,20).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban_Jasa_Menejemen_Tindakan_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang_Jasa_Menejemen_Tindakan_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,20).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,21).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','PENDAPATAN RAWAT JALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,21).toString()))+"'","kd_rek='"+Tindakan_Ralan+"'");   
                            }
                            //tindakan ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,22).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ranap+"','Beban_Jasa_Sarana_Tindakan_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ranap+"','Utang_Jasa_Sarana_Tindakan_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,22).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,24).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban_Jasa_Medik_Dokter_Tindakan_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,24).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,24).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang_Jasa_Medik_Dokter_Tindakan_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,24).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,24).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,25).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban_Jasa_Medik_Paramedis_Tindakan_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang_Jasa_Medik_Paramedis_Tindakan_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,25).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,26).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban_KSO_Tindakan_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"'","kd_rek='"+Beban_KSO_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang_KSO_Tindakan_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,26).toString()))+"'","kd_rek='"+Utang_KSO_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,27).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ranap+"','Beban_Jasa_Menejemen_Tindakan_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ranap+"','Utang_Jasa_Menejemen_Tindakan_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,27).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,28).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','PENDAPATAN RAWAT INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,28).toString()))+"'","kd_rek='"+Tindakan_Ranap+"'");   
                            }
                            //laborat ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,29).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ralan+"','Beban_Jasa_Sarana_Laborat_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ralan+"','Utang_Jasa_Sarana_Laborat_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,29).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,31).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ralan+"','Beban_Jasa_Perujuk_Laborat_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,31).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,31).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ralan+"','Utang_Jasa_Perujuk_Laborat_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,31).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,31).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,32).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"','Beban_Jasa_Medik_Dokter_Laborat_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang_Jasa_Medik_Dokter_Laborat_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,32).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,33).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"','Beban_Jasa_Medik_Petugas_Laborat_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"','Utang_Jasa_Medik_Petugas_Laborat_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,33).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,34).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ralan+"','Beban_Kso_Laborat_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"'","kd_rek='"+Beban_Kso_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ralan+"','Utang_Kso_Laborat_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,34).toString()))+"'","kd_rek='"+Utang_Kso_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,35).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ralan+"','Beban_Jasa_Menejemen_Laborat_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ralan+"','Utang_Jasa_Menejemen_Laborat_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,35).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,36).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','PENDAPATAN LABORAT RAWAT JALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,36).toString()))+"'","kd_rek='"+Laborat_Ralan+"'");   
                            }
                            //laborat ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,37).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ranap+"','Beban_Jasa_Sarana_Laborat_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ranap+"','Utang_Jasa_Sarana_Laborat_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,37).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,39).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ranap+"','Beban_Jasa_Perujuk_Laborat_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,39).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,39).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ranap+"','Utang_Jasa_Perujuk_Laborat_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,39).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,39).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,40).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"','Beban_Jasa_Medik_Dokter_Laborat_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang_Jasa_Medik_Dokter_Laborat_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,40).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,41).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"','Beban_Jasa_Medik_Petugas_Laborat_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"','Utang_Jasa_Medik_Petugas_Laborat_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,41).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,42).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ranap+"','Beban_Kso_Laborat_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"'","kd_rek='"+Beban_Kso_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ranap+"','Utang_Kso_Laborat_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,42).toString()))+"'","kd_rek='"+Utang_Kso_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,43).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ranap+"','Beban_Jasa_Menejemen_Laborat_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ranap+"','Utang_Jasa_Menejemen_Laborat_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,43).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,44).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','PENDAPATAN LABORAT RAWAT INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,44).toString()))+"'","kd_rek='"+Laborat_Ranap+"'");   
                            }
                            //radiologi ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,45).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ralan+"','Beban_Jasa_Sarana_Radiologi_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ralan+"','Utang_Jasa_Sarana_Radiologi_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,45).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,47).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ralan+"','Beban_Jasa_Perujuk_Radiologi_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,47).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,47).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ralan+"','Utang_Jasa_Perujuk_Radiologi_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,47).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,47).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,48).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"','Beban_Jasa_Medik_Dokter_Radiologi_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Utang_Jasa_Medik_Dokter_Radiologi_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,48).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,49).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"','Beban_Jasa_Medik_Petugas_Radiologi_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"','Utang_Jasa_Medik_Petugas_Radiologi_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,49).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,50).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ralan+"','Beban_Kso_Radiologi_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"'","kd_rek='"+Beban_Kso_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ralan+"','Utang_Kso_Radiologi_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,50).toString()))+"'","kd_rek='"+Utang_Kso_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,51).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ralan+"','Beban_Jasa_Menejemen_Radiologi_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ralan+"','Utang_Jasa_Menejemen_Radiologi_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,51).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,52).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Radiologi_Ralan+"','PENDAPATAN RADIOLOGI RAWAT JALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,52).toString()))+"'","kd_rek='"+Radiologi_Ralan+"'");   
                            }
                            //radiologi ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,53).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Radiologi_Ranap+"','Beban_Jasa_Sarana_Radiologi_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"'","kd_rek='"+Beban_Jasa_Sarana_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Radiologi_Ranap+"','Utang_Jasa_Sarana_Radiologi_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,53).toString()))+"'","kd_rek='"+Utang_Jasa_Sarana_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,55).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Radiologi_Ranap+"','Beban_Jasa_Perujuk_Radiologi_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,55).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,55).toString()))+"'","kd_rek='"+Beban_Jasa_Perujuk_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Radiologi_Ranap+"','Utang_Jasa_Perujuk_Radiologi_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,55).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,55).toString()))+"'","kd_rek='"+Utang_Jasa_Perujuk_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,56).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"','Beban_Jasa_Medik_Dokter_Radiologi_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"','Utang_Jasa_Medik_Dokter_Radiologi_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,56).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,57).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"','Beban_Jasa_Medik_Petugas_Radiologi_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"','Utang_Jasa_Medik_Petugas_Radiologi_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,57).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,58).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Radiologi_Ranap+"','Beban_Kso_Radiologi_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"'","kd_rek='"+Beban_Kso_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Radiologi_Ranap+"','Utang_Kso_Radiologi_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,58).toString()))+"'","kd_rek='"+Utang_Kso_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,59).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Radiologi_Ranap+"','Beban_Jasa_Menejemen_Radiologi_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"'","kd_rek='"+Beban_Jasa_Menejemen_Radiologi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Radiologi_Ranap+"','Utang_Jasa_Menejemen_Radiologi_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,59).toString()))+"'","kd_rek='"+Utang_Jasa_Menejemen_Radiologi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,60).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Radiologi_Ranap+"','PENDAPATAN RADIOLOGI RAWAT INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,60).toString()))+"'","kd_rek='"+Radiologi_Ranap+"'");   
                            }
                            //operasi ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,61).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"','Beban_Jasa_Medik_Dokter_Operasi_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"','Utang_Jasa_Medik_Dokter_Operasi_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,61).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,62).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"','Beban_Jasa_Medik_Paramedis_Operasi_Ralan','"+(Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"','Utang_Jasa_Medik_Paramedis_Operasi_Ralan','0','"+(Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,62).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,64).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG RAWAT JALAN','"+((Valid.SetAngka(tabMode.getValueAt(i,63).toString())+Valid.SetAngka(tabMode.getValueAt(i,64).toString())))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,63).toString())+Valid.SetAngka(tabMode.getValueAt(i,64).toString())))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Operasi_Ralan+"','PENDAPATAN OPERASI RAWAT JALAN','0','"+((Valid.SetAngka(tabMode.getValueAt(i,63).toString())+Valid.SetAngka(tabMode.getValueAt(i,64).toString())))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,63).toString())+Valid.SetAngka(tabMode.getValueAt(i,64).toString())))+"'","kd_rek='"+Operasi_Ralan+"'");   
                            }
                            //operasi ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,65).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"','Beban_Jasa_Medik_Dokter_Operasi_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang_Jasa_Medik_Dokter_Operasi_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,65).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,66).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"','Beban_Jasa_Medik_Paramedis_Operasi_Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"','Utang_Jasa_Medik_Paramedis_Operasi_Ranap','0','"+(Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,66).toString()))+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"'");   
                            }
                            if(Valid.SetAngka(tabMode.getValueAt(i,68).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+((Valid.SetAngka(tabMode.getValueAt(i,67).toString())+Valid.SetAngka(tabMode.getValueAt(i,68).toString())))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,67).toString())+Valid.SetAngka(tabMode.getValueAt(i,68).toString())))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','PENDAPATAN OPERASI RAWAT INAP','0','"+((Valid.SetAngka(tabMode.getValueAt(i,67).toString())+Valid.SetAngka(tabMode.getValueAt(i,68).toString())))+"'","kredit=kredit+'"+((Valid.SetAngka(tabMode.getValueAt(i,67).toString())+Valid.SetAngka(tabMode.getValueAt(i,68).toString())))+"'","kd_rek='"+Operasi_Ranap+"'");   
                            }
                            //kamar
                            if(Valid.SetAngka(tabMode.getValueAt(i,77).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Kamar_Inap+"','PENDAPATAN KAMAR INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,77).toString()))+"'","kd_rek='"+Kamar_Inap+"'");   
                            }
                            //harian
                            if(Valid.SetAngka(tabMode.getValueAt(i,79).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Harian_Ranap+"','PENDAPATAN HARIAN KAMAR INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,79).toString()))+"'","kd_rek='"+Harian_Ranap+"'");   
                            }
                            //registrasi
                            if(Valid.SetAngka(tabMode.getValueAt(i,80).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                    Sequel.menyimpan("tampjurnal","'"+Registrasi_Ralan+"','PENDAPATAN REGISTRASI RALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kd_rek='"+Registrasi_Ralan+"'");
                                }else{
                                    Sequel.menyimpan("tampjurnal","'"+Registrasi_Ranap+"','PENDAPATAN REGISTRASI RALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,80).toString()))+"'","kd_rek='"+Registrasi_Ranap+"'");
                                }
                            }
                            //tambahan biaya
                            if(Valid.SetAngka(tabMode.getValueAt(i,75).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                    Sequel.menyimpan("tampjurnal","'"+Tambahan_Ralan+"','PENDAPATAN TAMBAHAN RALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kd_rek='"+Tambahan_Ralan+"'");
                                }else{
                                    Sequel.menyimpan("tampjurnal","'"+Tambahan_Ranap+"','PENDAPATAN TAMBAHAN RANAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,75).toString()))+"'","kd_rek='"+Tambahan_Ranap+"'");
                                }
                            }
                            //potongan biaya
                            if(Valid.SetAngka(tabMode.getValueAt(i,76).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+(Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                    Sequel.menyimpan("tampjurnal","'"+Potongan_Ralan+"','POTONGAN RALAN','"+(Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kd_rek='"+Potongan_Ralan+"'");
                                }else{
                                    Sequel.menyimpan("tampjurnal","'"+Potongan_Ranap+"','POTONGAN RANAP','"+(Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,76).toString()))+"'","kd_rek='"+Potongan_Ranap+"'");
                                }
                            }
                            //resep pulang
                            if(Valid.SetAngka(tabMode.getValueAt(i,78).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Resep_Pulang_Ranap+"','PENDAPATAN RESEP PULANG','0','"+(Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,78).toString()))+"'","kd_rek='"+Resep_Pulang_Ranap+"'");   
                            }
                            //obat langsung
                            if(Valid.SetAngka(tabMode.getValueAt(i,69).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                if(tbBangsal.getValueAt(i,14).toString().equals("Ralan")){
                                    Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','OBAT LANGSUNG RALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kd_rek='"+Obat_Ralan+"'");
                                }else{
                                    Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','OBAT LANGSUNG RANAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,69).toString()))+"'","kd_rek='"+Obat_Ranap+"'");
                                }
                            }
                            //obat ralan
                            if(Valid.SetAngka(tabMode.getValueAt(i,70).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Obat_Ralan+"','PENDAPATAN OBAT RALAN','0','"+(Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,70).toString()))+"'","kd_rek='"+Obat_Ralan+"'");   
                            }
                            //obat ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,72).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,72).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,72).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Obat_Ranap+"','PENDAPATAN OBAT RANAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,72).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,72).toString()))+"'","kd_rek='"+Obat_Ranap+"'");   
                            }
                            //retur obat ranap
                            if(Valid.SetAngka(tabMode.getValueAt(i,74).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','0','"+(Valid.SetAngka(tabMode.getValueAt(i,74).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,74).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Retur_Obat_Ranap+"','PENDAPATAN OBAT RANAP','"+(Valid.SetAngka(tabMode.getValueAt(i,74).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,74).toString()))+"'","kd_rek='"+Retur_Obat_Ranap+"'");   
                            }
                            //service
                            if(Valid.SetAngka(tabMode.getValueAt(i,84).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_BPJS_RVP+"','PIUTANG BPJS','"+(Valid.SetAngka(tabMode.getValueAt(i,84).toString()))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,84).toString()))+"'","kd_rek='"+Piutang_BPJS_RVP+"'");     
                                Sequel.menyimpan("tampjurnal","'"+Service_Ranap+"','PENDAPATAN SERVICE INAP','0','"+(Valid.SetAngka(tabMode.getValueAt(i,84).toString()))+"'","kredit=kredit+'"+(Valid.SetAngka(tabMode.getValueAt(i,84).toString()))+"'","kd_rek='"+Service_Ranap+"'");   
                            }
                            //jurnal pembatalan RVU beban, utang, piutang, pendapatan
                            sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","PEMBATALAN RVP PIUTANG BPJS, OLEH "+akses.getkode());     
                            
                            if(sukses==true){
                                //jurnal kerugian 
                                Sequel.queryu("delete from tampjurnal");
                                if(Valid.SetAngka(tabMode.getValueAt(i,12).toString())>0){
                                    Sequel.menyimpan("tampjurnal","'"+Kerugian_Klaim_BPJS_RVP+"','KERUGIAN KLAIM BPJS','0','"+tabMode.getValueAt(i,12).toString()+"'","kredit=kredit+'"+tabMode.getValueAt(i,12).toString()+"'","kd_rek='"+Kerugian_Klaim_BPJS_RVP+"'");  
                                    if(Valid.SetAngka(tabMode.getValueAt(i,16).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP RAWAT JALAN','"+(Valid.SetAngka(tabMode.getValueAt(i,16).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,16).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");  
                                    }
                                    if(Valid.SetAngka(tabMode.getValueAt(i,23).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ranap+"','HPP RAWAT INAP','"+(Valid.SetAngka(tabMode.getValueAt(i,23).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,23).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_BHP_Tindakan_Ranap+"'");  
                                    }
                                    if(Valid.SetAngka(tabMode.getValueAt(i,30).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_Jalan+"','HPP LAB RAWAT JALAN','"+(Valid.SetAngka(tabMode.getValueAt(i,30).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,30).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_Jalan+"'");  
                                    }
                                    if(Valid.SetAngka(tabMode.getValueAt(i,38).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_inap+"','HPP LAB RAWAT INAP','"+(Valid.SetAngka(tabMode.getValueAt(i,38).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,38).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_inap+"'");  
                                    }
                                    if(Valid.SetAngka(tabMode.getValueAt(i,46).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Radiologi_Rawat_Jalan+"','HPP RADIOLOGI RAWAT JALAN','"+(Valid.SetAngka(tabMode.getValueAt(i,46).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,46).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Persediaan_Radiologi_Rawat_Jalan+"'");  
                                    }
                                    if(Valid.SetAngka(tabMode.getValueAt(i,54).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Radiologi_Rawat_Inap+"','HPP RADIOLOGI RAWAT INAP','"+(Valid.SetAngka(tabMode.getValueAt(i,54).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,54).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Persediaan_Radiologi_Rawat_Inap+"'");  
                                    }
                                    if(Valid.SetAngka(tabMode.getValueAt(i,63).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ralan+"','HPP OPERASI RAWAT JALAN','"+(Valid.SetAngka(tabMode.getValueAt(i,63).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,63).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Obat_Operasi_Ralan+"'");  
                                    }
                                    if(Valid.SetAngka(tabMode.getValueAt(i,67).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ranap+"','HPP OPERASI RAWAT INAP','"+(Valid.SetAngka(tabMode.getValueAt(i,67).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,67).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+HPP_Obat_Operasi_Ranap+"'");  
                                    }
                                    if(Valid.SetAngka(tabMode.getValueAt(i,78).toString())>0){
                                        Sequel.menyimpan("tampjurnal","'"+Resep_Pulang_Ranap+"','Resep Pulang Ranap','"+(Valid.SetAngka(tabMode.getValueAt(i,78).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"','0'","debet=debet+'"+(Valid.SetAngka(tabMode.getValueAt(i,78).toString())*((100-Valid.SetAngka(tabMode.getValueAt(i,11).toString()))/100))+"'","kd_rek='"+Resep_Pulang_Ranap+"'");  
                                    }
                                    rugihppralan=(Valid.SetAngka(tabMode.getValueAt(i,71).toString())-(Valid.SetAngka(tabMode.getValueAt(i,70).toString())*(Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100)));
                                    if(rugihppralan>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Rawat_Jalan+"','HPP Obat Ralan','"+rugihppralan+"','0'","debet=debet+'"+rugihppralan+"'","kd_rek='"+HPP_Obat_Rawat_Jalan+"'");  
                                    }
                                    rugihppranap=((Valid.SetAngka(tabMode.getValueAt(i,73).toString())-Valid.SetAngka(tabMode.getValueAt(i,74).toString()))-((Valid.SetAngka(tabMode.getValueAt(i,69).toString())+Valid.SetAngka(tabMode.getValueAt(i,72).toString())-Valid.SetAngka(tabMode.getValueAt(i,74).toString()))*(Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100)));    
                                    if(rugihppranap>0){
                                        Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Rawat_Inap+"','HPP Obat Ranap','"+rugihppranap+"','0'","debet=debet+'"+rugihppranap+"'","kd_rek='"+HPP_Obat_Rawat_Inap+"'");  
                                    }
                                }
                                
                                Sequel.menyimpan("tampjurnal","'"+tabMode.getValueAt(i,83).toString()+"','PIUTANG BPJS','"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,8).toString()))+"','0'","debet=debet+'"+((Valid.SetAngka(tabMode.getValueAt(i,11).toString())/100) *Valid.SetAngka(tabMode.getValueAt(i,8).toString()))+"'","kd_rek='"+tabMode.getValueAt(i,83).toString()+"'");     
                                Sequel.menyimpan("tampjurnal","'"+tabMode.getValueAt(i,82).toString()+"','Akun Bayar','0','"+tabMode.getValueAt(i,10).toString()+"'","kredit=kredit+'"+tabMode.getValueAt(i,82).toString()+"'","kd_rek='"+koderekening+"'"); 
                                sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","PEMBATALAN RVP PIUTANG BPJS"+", OLEH "+akses.getkode());      
                    
                                if(sukses==true){
                                    //update RVP Rawat jalan
                                    if(Sequel.queryutf("update rawat_jl_dr set material=material*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakandr=tarif_tindakandr*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",kso=kso*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",menejemen=menejemen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_rawat=biaya_rawat*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    if(Sequel.queryutf("update rawat_jl_pr set material=material*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakanpr=tarif_tindakanpr*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",kso=kso*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",menejemen=menejemen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_rawat=biaya_rawat*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    if(Sequel.queryutf("update rawat_jl_drpr set material=material*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakandr=tarif_tindakandr*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakanpr=tarif_tindakanpr*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",kso=kso*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",menejemen=menejemen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_rawat=biaya_rawat*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update RVP Rawat inap
                                    if(Sequel.queryutf("update rawat_inap_dr set material=material*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakandr=tarif_tindakandr*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",kso=kso*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",menejemen=menejemen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_rawat=biaya_rawat*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    if(Sequel.queryutf("update rawat_inap_pr set material=material*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakanpr=tarif_tindakanpr*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",kso=kso*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",menejemen=menejemen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_rawat=biaya_rawat*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    if(Sequel.queryutf("update rawat_inap_drpr set material=material*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakandr=tarif_tindakandr*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakanpr=tarif_tindakanpr*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",kso=kso*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",menejemen=menejemen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_rawat=biaya_rawat*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update rvp laborat
                                    if(Sequel.queryutf("update periksa_lab set bagian_rs=bagian_rs*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_perujuk=tarif_perujuk*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakan_dokter=tarif_tindakan_dokter*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakan_petugas=tarif_tindakan_petugas*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",kso=kso*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",menejemen=menejemen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya=biaya*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    if(Sequel.queryutf("update detail_periksa_lab set bagian_rs=bagian_rs*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",bagian_perujuk=bagian_perujuk*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",bagian_dokter=bagian_dokter*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",bagian_laborat=bagian_laborat*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",kso=kso*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",menejemen=menejemen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_item=biaya_item*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update rvp radiologi
                                    if(Sequel.queryutf("update periksa_radiologi set bagian_rs=bagian_rs*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_perujuk=tarif_perujuk*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakan_dokter=tarif_tindakan_dokter*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tarif_tindakan_petugas=tarif_tindakan_petugas*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",kso=kso*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",menejemen=menejemen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya=biaya*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update rvp operasi
                                    if(Sequel.queryutf("update operasi set biayaoperator1=biayaoperator1*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaoperator2=biayaoperator2*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaoperator3=biayaoperator3*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaasisten_operator1=biayaasisten_operator1*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaasisten_operator2=biayaasisten_operator2*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaasisten_operator3=biayaasisten_operator3*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayainstrumen=biayainstrumen*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayadokter_anak=biayadokter_anak*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaperawaat_resusitas=biayaperawaat_resusitas*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayadokter_anestesi=biayadokter_anestesi*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaasisten_anestesi=biayaasisten_anestesi*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaasisten_anestesi2=biayaasisten_anestesi2*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayabidan=biayabidan*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayabidan2=biayabidan2*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayabidan3=biayabidan3*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaperawat_luar=biayaperawat_luar*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayaalat=biayaalat*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayasewaok=biayasewaok*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",akomodasi=akomodasi*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",bagian_rs=bagian_rs*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_omloop=biaya_omloop*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_omloop2=biaya_omloop2*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_omloop3=biaya_omloop3*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_omloop4=biaya_omloop4*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_omloop5=biaya_omloop5*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biayasarpras=biayasarpras*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_dokter_pjanak=biaya_dokter_pjanak*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",biaya_dokter_umum=biaya_dokter_umum*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update rvp kamar
                                    if(Sequel.queryutf("update kamar_inap set trf_kamar=trf_kamar*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",ttl_biaya=ttl_biaya*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update rvp registrasi
                                    if(Sequel.queryutf("update reg_periksa set biaya_reg=biaya_reg*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update rvp tambahan biaya
                                    if(Sequel.queryutf("update tambahan_biaya set besar_biaya=besar_biaya*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update rvp pengurangan biaya
                                    if(Sequel.queryutf("update pengurangan_biaya set besar_pengurangan=besar_pengurangan*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update rvp obat langsung
                                    if(Sequel.queryutf("update tagihan_obat_langsung set besar_tagihan=besar_tagihan*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                    //update rvp obat
                                    if(Sequel.queryutf("update detail_pemberian_obat set biaya_obat=biaya_obat*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",total=total*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",embalase=embalase*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+",tuslah=tuslah*"+(100/Valid.SetAngka(tabMode.getValueAt(i,11).toString()))+" where no_rawat='"+tabMode.getValueAt(i,1).toString()+"'")==false){
                                        sukses=false;
                                    }
                                }
                            }
                        }
                    }
                    
                    if(sukses==true){
                        if(Sequel.queryu2tf("delete from bayar_piutang where tgl_bayar=? and no_rawat=? and kd_rek=? and kd_rek_kontra=?",4,new String[]{
                            tabMode.getValueAt(i,3).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,82).toString(),tabMode.getValueAt(i,83).toString()
                        })==true){
                            Sequel.meghapus("rvp_klaim_bpjs","no_rawat",tabMode.getValueAt(i,1).toString());
                        }else{
                            sukses=false;
                        }
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
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbBangsal.getRowCount();i++){
            tbBangsal.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        for(i=0;i<tbBangsal.getRowCount();i++){
            tbBangsal.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganCariRVPBPJS dialog = new KeuanganCariRVPBPJS(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private javax.swing.JLabel LCount;
    private javax.swing.JLabel LCount2;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                   "select rvp_klaim_bpjs.no_rawat,rvp_klaim_bpjs.no_sep,rvp_klaim_bpjs.tanggal_rvp,concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien) as namapasien,"+
                   "rvp_klaim_bpjs.totalpiutang,rvp_klaim_bpjs.uangmuka,rvp_klaim_bpjs.sudahdibayar,rvp_klaim_bpjs.sisapiutang,rvp_klaim_bpjs.tarifinacbg,"+
                   "rvp_klaim_bpjs.dibayarbpjs,rvp_klaim_bpjs.persenbayar,rvp_klaim_bpjs.rugi,rvp_klaim_bpjs.lebih,rvp_klaim_bpjs.materialralan,"+
                   "rvp_klaim_bpjs.bhpralan,rvp_klaim_bpjs.tarif_tindakandrralan,rvp_klaim_bpjs.tarif_tindakanprralan,rvp_klaim_bpjs.ksoralan,"+
                   "rvp_klaim_bpjs.menejemenralan,rvp_klaim_bpjs.biaya_rawatralan,rvp_klaim_bpjs.materialranap,rvp_klaim_bpjs.bhpranap,"+
                   "rvp_klaim_bpjs.tarif_tindakandrranap,rvp_klaim_bpjs.tarif_tindakanprranap,rvp_klaim_bpjs.ksoranap,rvp_klaim_bpjs.menejemenranap,"+
                   "rvp_klaim_bpjs.biaya_rawatranap,rvp_klaim_bpjs.bagian_rslabralan,rvp_klaim_bpjs.bhplabralan,rvp_klaim_bpjs.tarif_perujuklabralan,"+
                   "rvp_klaim_bpjs.tarif_tindakan_dokterlabralan,rvp_klaim_bpjs.tarif_tindakan_petugaslabralan,rvp_klaim_bpjs.ksolabralan,"+
                   "rvp_klaim_bpjs.menejemenlabralan,rvp_klaim_bpjs.biayalabralan,rvp_klaim_bpjs.bagian_rslabranap,rvp_klaim_bpjs.bhplabranap,"+
                   "rvp_klaim_bpjs.tarif_perujuklabranap,rvp_klaim_bpjs.tarif_tindakan_dokterlabranap,rvp_klaim_bpjs.tarif_tindakan_petugaslabranap,"+
                   "rvp_klaim_bpjs.ksolabranap,rvp_klaim_bpjs.menejemenlabranap,rvp_klaim_bpjs.biayalabranap,rvp_klaim_bpjs.bagian_rsradiologiralan,"+
                   "rvp_klaim_bpjs.bhpradiologiralan,rvp_klaim_bpjs.tarif_perujukradiologiralan,rvp_klaim_bpjs.tarif_tindakan_dokterradiologiralan,"+
                   "rvp_klaim_bpjs.tarif_tindakan_petugasradiologiralan,rvp_klaim_bpjs.ksoradiologiralan,rvp_klaim_bpjs.menejemenradiologiralan,"+
                   "rvp_klaim_bpjs.biayaradiologiralan,rvp_klaim_bpjs.bagian_rsradiologiranap,rvp_klaim_bpjs.bhpradiologiranap,rvp_klaim_bpjs.tarif_perujukradiologiranap,"+
                   "rvp_klaim_bpjs.tarif_tindakan_dokterradiologiranap,rvp_klaim_bpjs.tarif_tindakan_petugasradiologiranap,rvp_klaim_bpjs.ksoradiologiranap,"+
                   "rvp_klaim_bpjs.menejemenradiologiranap,rvp_klaim_bpjs.biayaradiologiranap,rvp_klaim_bpjs.jmdokteroperasiralan,rvp_klaim_bpjs.jmparamedisoperasiralan,"+
                   "rvp_klaim_bpjs.bhpoperasiralan,rvp_klaim_bpjs.pendapatanoperasiralan,rvp_klaim_bpjs.jmdokteroperasiranap,rvp_klaim_bpjs.jmparamedisoperasiranap,"+
                   "rvp_klaim_bpjs.bhpoperasiranap,rvp_klaim_bpjs.pendapatanoperasiranap,rvp_klaim_bpjs.obatlangsung,rvp_klaim_bpjs.obatralan,"+
                   "rvp_klaim_bpjs.hppobatralan,rvp_klaim_bpjs.obatranap,rvp_klaim_bpjs.hppobatranap,rvp_klaim_bpjs.returobat,rvp_klaim_bpjs.tambahanbiaya,"+
                   "rvp_klaim_bpjs.potonganbiaya,rvp_klaim_bpjs.kamar,rvp_klaim_bpjs.reseppulang,rvp_klaim_bpjs.registrasi,rvp_klaim_bpjs.harianranap,"+
                   "concat(rvp_klaim_bpjs.nip,' ',petugas.nama) as petugas,reg_periksa.status_lanjut,rvp_klaim_bpjs.kd_rek,rvp_klaim_bpjs.kd_rek_kontra,"+
                   "rvp_klaim_bpjs.service from rvp_klaim_bpjs inner join reg_periksa on rvp_klaim_bpjs.no_rawat=reg_periksa.no_rawat "+
                   "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join petugas on petugas.nip=rvp_klaim_bpjs.nip "+
                   "where rvp_klaim_bpjs.tanggal_rvp between ? and ? "+
                   (TCari.getText().trim().equals("")?"":"and (rvp_klaim_bpjs.no_rawat like ? or reg_periksa.no_rkm_medis like ? "+
                   "or pasien.nm_pasien like ? or rvp_klaim_bpjs.no_sep like ? or rvp_klaim_bpjs.no_sep like ? or reg_periksa.status_lanjut like ?)")+
                   " order by rvp_klaim_bpjs.tanggal_rvp");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                }
                    
                sisapiutang=0;
                total=0;
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("tanggal_rvp"),rs.getString("namapasien"),
                        rs.getDouble("totalpiutang"),rs.getDouble("uangmuka"),rs.getDouble("sudahdibayar"),rs.getDouble("sisapiutang"),
                        rs.getDouble("tarifinacbg"),rs.getDouble("dibayarbpjs"),rs.getDouble("persenbayar"),rs.getDouble("rugi"),
                        rs.getDouble("lebih"),rs.getString("status_lanjut"),rs.getDouble("materialralan"),rs.getDouble("bhpralan"),
                        rs.getDouble("tarif_tindakandrralan"),rs.getDouble("tarif_tindakanprralan"),rs.getDouble("ksoralan"),
                        rs.getDouble("menejemenralan"),rs.getDouble("biaya_rawatralan"),rs.getDouble("materialranap"),rs.getDouble("bhpranap"),
                        rs.getDouble("tarif_tindakandrranap"),rs.getDouble("tarif_tindakanprranap"),rs.getDouble("ksoranap"),
                        rs.getDouble("menejemenranap"),rs.getDouble("biaya_rawatranap"),rs.getDouble("bagian_rslabralan"),
                        rs.getDouble("bhplabralan"),rs.getDouble("tarif_perujuklabralan"),rs.getDouble("tarif_tindakan_dokterlabralan"),
                        rs.getDouble("tarif_tindakan_petugaslabralan"),rs.getDouble("ksolabralan"),rs.getDouble("menejemenlabralan"),
                        rs.getDouble("biayalabralan"),rs.getDouble("bagian_rslabranap"),rs.getDouble("bhplabranap"),rs.getDouble("tarif_perujuklabranap"),
                        rs.getDouble("tarif_tindakan_dokterlabranap"),rs.getDouble("tarif_tindakan_petugaslabranap"),rs.getDouble("ksolabranap"),
                        rs.getDouble("menejemenlabranap"),rs.getDouble("biayalabranap"),rs.getDouble("bagian_rsradiologiralan"),
                        rs.getDouble("bhpradiologiralan"),rs.getDouble("tarif_perujukradiologiralan"),rs.getDouble("tarif_tindakan_dokterradiologiralan"),
                        rs.getDouble("tarif_tindakan_petugasradiologiralan"),rs.getDouble("ksoradiologiralan"),rs.getDouble("menejemenradiologiralan"),
                        rs.getDouble("biayaradiologiralan"),rs.getDouble("bagian_rsradiologiranap"),rs.getDouble("bhpradiologiranap"),
                        rs.getDouble("tarif_perujukradiologiranap"),rs.getDouble("tarif_tindakan_dokterradiologiranap"),
                        rs.getDouble("tarif_tindakan_petugasradiologiranap"),rs.getDouble("ksoradiologiranap"),rs.getDouble("menejemenradiologiranap"),
                        rs.getDouble("biayaradiologiranap"),rs.getDouble("jmdokteroperasiralan"),rs.getDouble("jmparamedisoperasiralan"),
                        rs.getDouble("bhpoperasiralan"),rs.getDouble("pendapatanoperasiralan"),rs.getDouble("jmdokteroperasiranap"),
                        rs.getDouble("jmparamedisoperasiranap"),rs.getDouble("bhpoperasiranap"),rs.getDouble("pendapatanoperasiranap"),
                        rs.getDouble("obatlangsung"),rs.getDouble("obatralan"),rs.getDouble("hppobatralan"),rs.getDouble("obatranap"),
                        rs.getDouble("hppobatranap"),rs.getDouble("returobat"),rs.getDouble("tambahanbiaya"),rs.getDouble("potonganbiaya"),
                        rs.getDouble("kamar"),rs.getDouble("reseppulang"),rs.getDouble("harianranap"),rs.getDouble("registrasi"),
                        rs.getString("petugas"),rs.getString("kd_rek"),rs.getString("kd_rek_kontra"),rs.getDouble("service")
                    });
                    sisapiutang=sisapiutang+rs.getDouble("sisapiutang");
                    total=total+rs.getDouble("dibayarbpjs");
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
            LCount2.setText(Valid.SetAngka(total));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getdata() {
    }
    
    public void isCek(){
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            BtnHapus.setEnabled(akses.getrvu_bpjs());
        } 
    }
}
