package keuangan;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public class DlgPengaturanRekening extends javax.swing.JDialog {
    private DefaultTableModel tabMode,tabModeRalan,tabModeRanap;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,barisdicopy=-1;
    private String Tindakan_Ralan, Laborat_Ralan, Radiologi_Ralan, Obat_Ralan, Registrasi_Ralan, 
            Tambahan_Ralan, Potongan_Ralan, Tindakan_Ranap, Laborat_Ranap, Radiologi_Ranap, 
            Obat_Ranap, Registrasi_Ranap, Tambahan_Ranap, Potongan_Ranap, Retur_Obat_Ranap, 
            Resep_Pulang_Ranap, Kamar_Inap, Operasi_Ranap, Harian_Ranap, Pengadaan_Obat, 
            Pemesanan_Obat, Kontra_Pemesanan_Obat, Bayar_Pemesanan_Obat, Penjualan_Obat, 
            Piutang_Obat, Kontra_Piutang_Obat, Retur_Ke_Suplayer, Kontra_Retur_Ke_Suplayer, 
            Retur_Dari_pembeli, Kontra_Retur_Dari_Pembeli,Hibah_Obat, Kontra_Hibah_Obat, 
            Retur_Piutang_Obat, Kontra_Retur_Piutang_Obat,Pengadaan_Ipsrs, Stok_Keluar_Ipsrs, 
            Kontra_Stok_Keluar_Ipsrs,Uang_Muka_Ranap,
            Piutang_Pasien_Ranap,Bayar_Piutang_Pasien,Service_Ranap,Pengambilan_Utd,
            Kontra_Pengambilan_Utd,Pengambilan_Penunjang_Utd,Kontra_Pengambilan_Penunjang_Utd,
            Operasi_Ralan,Penyerahan_Darah,Beban_Jasa_Medik_Dokter_Tindakan_Ralan,
            Utang_Jasa_Medik_Dokter_Tindakan_Ralan,Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,
            Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,Beban_KSO_Tindakan_Ralan, 
            Utang_KSO_Tindakan_Ralan,Beban_Jasa_Medik_Dokter_Laborat_Ralan, 
            Utang_Jasa_Medik_Dokter_Laborat_Ralan, Beban_Jasa_Medik_Petugas_Laborat_Ralan, 
            Utang_Jasa_Medik_Petugas_Laborat_Ralan, Beban_Kso_Laborat_Ralan, 
            Utang_Kso_Laborat_Ralan, HPP_Persediaan_Laborat_Rawat_Jalan, 
            Persediaan_BHP_Laborat_Rawat_Jalan,Beban_Jasa_Medik_Dokter_Radiologi_Ralan, 
            Utang_Jasa_Medik_Dokter_Radiologi_Ralan, Beban_Jasa_Medik_Petugas_Radiologi_Ralan, 
            Utang_Jasa_Medik_Petugas_Radiologi_Ralan, Beban_Kso_Radiologi_Ralan, Utang_Kso_Radiologi_Ralan, 
            HPP_Persediaan_Radiologi_Rawat_Jalan, Persediaan_BHP_Radiologi_Rawat_Jalan,
            HPP_Obat_Rawat_Jalan, Persediaan_Obat_Rawat_Jalan,Beban_Jasa_Medik_Dokter_Operasi_Ralan,
            Utang_Jasa_Medik_Dokter_Operasi_Ralan,Beban_Jasa_Medik_Paramedis_Operasi_Ralan,
            Utang_Jasa_Medik_Paramedis_Operasi_Ralan,HPP_Obat_Operasi_Ralan,Persediaan_Obat_Kamar_Operasi_Ralan,
            Suspen_Piutang_Tindakan_Ranap, Beban_Jasa_Medik_Dokter_Tindakan_Ranap, Utang_Jasa_Medik_Dokter_Tindakan_Ranap, 
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap, Utang_Jasa_Medik_Paramedis_Tindakan_Ranap, Beban_KSO_Tindakan_Ranap, 
            Utang_KSO_Tindakan_Ranap,Suspen_Piutang_Laborat_Ranap, Beban_Jasa_Medik_Dokter_Laborat_Ranap, 
            Utang_Jasa_Medik_Dokter_Laborat_Ranap, Beban_Jasa_Medik_Petugas_Laborat_Ranap, 
            Utang_Jasa_Medik_Petugas_Laborat_Ranap, Beban_Kso_Laborat_Ranap, Utang_Kso_Laborat_Ranap, 
            HPP_Persediaan_Laborat_Rawat_inap, Persediaan_BHP_Laborat_Rawat_Inap,
            Suspen_Piutang_Radiologi_Ranap, Beban_Jasa_Medik_Dokter_Radiologi_Ranap, 
            Utang_Jasa_Medik_Dokter_Radiologi_Ranap, Beban_Jasa_Medik_Petugas_Radiologi_Ranap, 
            Utang_Jasa_Medik_Petugas_Radiologi_Ranap, Beban_Kso_Radiologi_Ranap, Utang_Kso_Radiologi_Ranap, 
            HPP_Persediaan_Radiologi_Rawat_Inap, Persediaan_BHP_Radiologi_Rawat_Inap,HPP_Obat_Rawat_Inap,
            Persediaan_Obat_Rawat_Inap,Suspen_Piutang_Obat_Ranap,Suspen_Piutang_Operasi_Ranap, 
            Beban_Jasa_Medik_Dokter_Operasi_Ranap, Utang_Jasa_Medik_Dokter_Operasi_Ranap, 
            Beban_Jasa_Medik_Paramedis_Operasi_Ranap, Utang_Jasa_Medik_Paramedis_Operasi_Ranap, 
            HPP_Obat_Operasi_Ranap, Persediaan_Obat_Kamar_Operasi_Ranap,Stok_Keluar_Medis,
            Kontra_Stok_Keluar_Medis,HPP_Obat_Jual_Bebas,Persediaan_Obat_Jual_Bebas,
            Penerimaan_NonMedis,Kontra_Penerimaan_NonMedis,Bayar_Pemesanan_Non_Medis,
            Penerimaan_Toko,Kontra_Penerimaan_Toko,Pengadaan_Toko,Bayar_Pemesanan_Toko,
            Penjualan_Toko,HPP_Barang_Toko,Persediaan_Barang_Toko,Piutang_Toko,Kontra_Piutang_Toko,
            Retur_Beli_Toko,Kontra_Retur_Beli_Toko,Retur_Beli_Non_Medis,Kontra_Retur_Beli_Non_Medis,
            Retur_Jual_Toko,Kontra_Retur_Jual_Toko,Retur_Piutang_Toko,Kontra_Retur_Piutang_Toko,
            kode_pendapatan_tindakan,nama_pendapatan_tindakan,kode_beban_jasa_dokter,nama_beban_jasa_dokter, kode_utang_jasa_dokter,
            nama_utang_jasa_dokter,kode_beban_jasa_paramedis,nama_beban_jasa_paramedis,kode_utang_jasa_paramedis,nama_utang_jasa_paramedis, 
            kode_beban_kso,nama_beban_kso,kode_utang_kso,nama_utang_kso,kode_hpp_persediaan,nama_hpp_persediaan,kode_persediaan_bhp,
            nama_persediaan_bhp,kode_beban_jasa_sarana,nama_beban_jasa_sarana,kode_utang_jasa_sarana,nama_utang_jasa_sarana,
            kode_beban_menejemen,nama_beban_menejemen,kode_utang_menejemen,nama_utang_menejemen,
            Suspen_Piutang_Tindakan_Ralan,Beban_Jasa_Sarana_Tindakan_Ralan,Utang_Jasa_Sarana_Tindakan_Ralan,
            HPP_BHP_Tindakan_Ralan,Persediaan_BHP_Tindakan_Ralan,Beban_Jasa_Menejemen_Tindakan_Ralan,
            Utang_Jasa_Menejemen_Tindakan_Ralan,Suspen_Piutang_Laborat_Ralan,Beban_Jasa_Sarana_Laborat_Ralan,
            Utang_Jasa_Sarana_Laborat_Ralan,Beban_Jasa_Perujuk_Laborat_Ralan,Utang_Jasa_Perujuk_Laborat_Ralan,
            Beban_Jasa_Menejemen_Laborat_Ralan,Utang_Jasa_Menejemen_Laborat_Ralan,Suspen_Piutang_Radiologi_Ralan,
            Beban_Jasa_Sarana_Radiologi_Ralan,Utang_Jasa_Sarana_Radiologi_Ralan,Beban_Jasa_Perujuk_Radiologi_Ralan,
            Utang_Jasa_Perujuk_Radiologi_Ralan,Beban_Jasa_Menejemen_Radiologi_Ralan,Utang_Jasa_Menejemen_Radiologi_Ralan,
            Suspen_Piutang_Obat_Ralan,Suspen_Piutang_Operasi_Ralan,Beban_Jasa_Sarana_Tindakan_Ranap,
            Utang_Jasa_Sarana_Tindakan_Ranap,Beban_Jasa_Menejemen_Tindakan_Ranap,Utang_Jasa_Menejemen_Tindakan_Ranap,
            HPP_BHP_Tindakan_Ranap,Persediaan_BHP_Tindakan_Ranap,Beban_Jasa_Sarana_Laborat_Ranap,
            Utang_Jasa_Sarana_Laborat_Ranap,Beban_Jasa_Perujuk_Laborat_Ranap,Utang_Jasa_Perujuk_Laborat_Ranap,
            Beban_Jasa_Menejemen_Laborat_Ranap,Utang_Jasa_Menejemen_Laborat_Ranap,Beban_Jasa_Sarana_Radiologi_Ranap,
            Utang_Jasa_Sarana_Radiologi_Ranap,Beban_Jasa_Perujuk_Radiologi_Ranap,Utang_Jasa_Perujuk_Radiologi_Ranap,
            Beban_Jasa_Menejemen_Radiologi_Ranap,Utang_Jasa_Menejemen_Radiologi_Ranap;
    private String copyakun="";
    private DlgRekeningTahun rekening=new DlgRekeningTahun(null,false);

    /** Creates new form DlgJadwal
     * @param parent
     * @param modal */
    public DlgPengaturanRekening(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new String[]{"Letak Akun Rekening","Kode Akun","Nama Akun","Tipe","Balance"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengaturan.setModel(tabMode);
        tbPengaturan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengaturan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 5; i++) {
            TableColumn column = tbPengaturan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(600);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(380);
            }else if(i==3){
                column.setPreferredWidth(40);
            }else if(i==4){
                column.setPreferredWidth(45);
            }
        }

        tbPengaturan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRalan=new DefaultTableModel(null,new String[]{
                "Kode Tindakan","Nama Tnd/Prw/Tagihan","Kategori","Jenis Bayar","Unit/Poli",
                "Kode Akun","Nama Akun Pendapatan Tindakan Ralan","Kode Akun","Nama Akun Beban Jasa Dokter",
                "Kode Akun","Nama Akun Utang Jasa Dokter","Kode Akun","Nama Akun Beban Jasa Paramedis",
                "Kode Akun","Nama Akun Utang Jasa Paramedis","Kode Akun","Nama Akun Beban KSO",
                "Kode Akun","Nama Akun Utang KSO","Kode Akun","Nama Akun HPP Persediaan Ralan",
                "Kode Akun","Nama Akun Persediaan BHP Ralan","Kode Akun","Nama Akun Beban Jasa Sarana",
                "Kode Akun","Nama Akun Utang Jasa Sarana","Kode Akun","Nama Akun Utang Beban Jasa Menejemen",
                "Kode Akun","Nama Akun Utang Jasa Menejemen"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengaturanRalan.setModel(tabModeRalan);
        tbPengaturanRalan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengaturanRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 31; i++) {
            TableColumn column = tbPengaturanRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(330);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(300);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(300);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(300);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(300);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(300);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(300);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(300);
            }else if(i==19){
                column.setPreferredWidth(80);
            }else if(i==20){
                column.setPreferredWidth(300);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(300);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(300);
            }else if(i==25){
                column.setPreferredWidth(80);
            }else if(i==26){
                column.setPreferredWidth(300);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(300);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(300);
            }
        }

        tbPengaturanRalan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeRanap=new DefaultTableModel(null,new String[]{
                "Kode Tindakan","Nama Tnd/Prw/Tagihan","Kategori","Jenis Bayar","Ruang","Kelas",
                "Kode Akun","Nama Akun Pendapatan Tindakan Ranap","Kode Akun","Nama Akun Beban Jasa Dokter",
                "Kode Akun","Nama Akun Utang Jasa Dokter","Kode Akun","Nama Akun Beban Jasa Paramedis",
                "Kode Akun","Nama Akun Utang Jasa Paramedis","Kode Akun","Nama Akun Beban KSO",
                "Kode Akun","Nama Akun Utang KSO","Kode Akun","Nama Akun HPP Persediaan Ranap",
                "Kode Akun","Nama Akun Persediaan BHP Ranap","Kode Akun","Nama Akun Beban Jasa Sarana",
                "Kode Akun","Nama Akun Utang Jasa Sarana","Kode Akun","Nama Akun Utang Beban Jasa Menejemen",
                "Kode Akun","Nama Akun Utang Jasa Menejemen"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengaturanRanap.setModel(tabModeRanap);
        tbPengaturanRanap.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengaturanRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 32; i++) {
            TableColumn column = tbPengaturanRanap.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(330);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(300);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(300);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if(i==11){
                column.setPreferredWidth(300);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(300);
            }else if(i==14){
                column.setPreferredWidth(80);
            }else if(i==15){
                column.setPreferredWidth(300);
            }else if(i==16){
                column.setPreferredWidth(80);
            }else if(i==17){
                column.setPreferredWidth(300);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(300);
            }else if(i==20){
                column.setPreferredWidth(80);
            }else if(i==21){
                column.setPreferredWidth(300);
            }else if(i==22){
                column.setPreferredWidth(80);
            }else if(i==23){
                column.setPreferredWidth(300);
            }else if(i==24){
                column.setPreferredWidth(80);
            }else if(i==25){
                column.setPreferredWidth(300);
            }else if(i==26){
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(300);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(300);
            }else if(i==30){
                column.setPreferredWidth(80);
            }else if(i==31){
                column.setPreferredWidth(300);
            }
        }

        tbPengaturanRanap.setDefaultRenderer(Object.class, new WarnaTable());
        
        rekening.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPengaturanRekening")){
                    if(rekening.getTabel().getSelectedRow()!= -1){    
                        if(tbPengaturan.getSelectedColumn()==1){
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturan.getSelectedRow(),1);
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturan.getSelectedRow(),2);
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),3).toString(),tbPengaturan.getSelectedRow(),3);
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),4).toString(),tbPengaturan.getSelectedRow(),4);
                        }                    
                    }   
                    tbPengaturan.requestFocus();
                }else if(akses.getform().equals("DlgPengaturanRekeningRalan")){
                    if(rekening.getTabel().getSelectedRow()!= -1){    
                        if(tbPengaturanRalan.getSelectedColumn()==5){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),5);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),6);
                        }else if(tbPengaturanRalan.getSelectedColumn()==7){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),7);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),8);
                        }else if(tbPengaturanRalan.getSelectedColumn()==9){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),9);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),10);
                        }else if(tbPengaturanRalan.getSelectedColumn()==11){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),11);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),12);
                        }else if(tbPengaturanRalan.getSelectedColumn()==13){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),13);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),14);
                        }else if(tbPengaturanRalan.getSelectedColumn()==15){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),15);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),16);
                        }else if(tbPengaturanRalan.getSelectedColumn()==17){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),17);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),18);
                        }else if(tbPengaturanRalan.getSelectedColumn()==19){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),19);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),20);
                        }else if(tbPengaturanRalan.getSelectedColumn()==21){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),21);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),22);
                        }else if(tbPengaturanRalan.getSelectedColumn()==23){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),23);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),24);
                        }else if(tbPengaturanRalan.getSelectedColumn()==25){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),25);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),26);
                        }else if(tbPengaturanRalan.getSelectedColumn()==27){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),27);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),28);
                        }else if(tbPengaturanRalan.getSelectedColumn()==29){
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRalan.getSelectedRow(),29);
                            tabModeRalan.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRalan.getSelectedRow(),30);
                        }                      
                    }   
                    tbPengaturanRalan.requestFocus();
                }else if(akses.getform().equals("DlgPengaturanRekeningRanap")){
                    if(rekening.getTabel().getSelectedRow()!= -1){    
                        if(tbPengaturanRanap.getSelectedColumn()==6){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),6);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),7);
                        }else if(tbPengaturanRanap.getSelectedColumn()==8){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),8);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),9);
                        }else if(tbPengaturanRanap.getSelectedColumn()==10){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),10);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),11);
                        }else if(tbPengaturanRanap.getSelectedColumn()==12){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),12);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),13);
                        }else if(tbPengaturanRanap.getSelectedColumn()==14){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),14);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),15);
                        }else if(tbPengaturanRanap.getSelectedColumn()==16){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),16);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),17);
                        }else if(tbPengaturanRanap.getSelectedColumn()==18){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),18);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),19);
                        }else if(tbPengaturanRanap.getSelectedColumn()==20){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),20);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),21);
                        }else if(tbPengaturanRanap.getSelectedColumn()==22){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),22);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),23);
                        }else if(tbPengaturanRanap.getSelectedColumn()==24){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),24);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),25);
                        }else if(tbPengaturanRanap.getSelectedColumn()==26){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),26);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),27);
                        }else if(tbPengaturanRanap.getSelectedColumn()==28){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),28);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),29);
                        }else if(tbPengaturanRanap.getSelectedColumn()==30){
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturanRanap.getSelectedRow(),30);
                            tabModeRanap.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturanRanap.getSelectedRow(),31);
                        }                      
                    }   
                    tbPengaturanRanap.requestFocus();
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
        
        rekening.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPengaturanRekening")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        rekening.dispose();
                    }
                }else if(akses.getform().equals("DlgPengaturanRekeningRalan")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        rekening.dispose();
                    }
                }else if(akses.getform().equals("DlgPengaturanRekeningRanap")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        rekening.dispose();
                    }
                }
                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        
    }
   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCopyRekening = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbPengaturan = new widget.Table();
        Scroll1 = new widget.ScrollPane();
        tbPengaturanRalan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbPengaturanRanap = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCopyRekening.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCopyRekening.setForeground(new java.awt.Color(50, 50, 50));
        MnCopyRekening.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCopyRekening.setText("Copy Rekening");
        MnCopyRekening.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCopyRekening.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCopyRekening.setName("MnCopyRekening"); // NOI18N
        MnCopyRekening.setPreferredSize(new java.awt.Dimension(170, 26));
        MnCopyRekening.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCopyRekeningActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCopyRekening);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengaturan Rekening ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 56));
        panelGlass8.setLayout(null);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Update");
        BtnSimpan.setToolTipText("Alt+U");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        BtnSimpan.setBounds(6, 10, 100, 30);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
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
        BtnKeluar.setBounds(109, 10, 100, 30);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

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

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPengaturan.setToolTipText("Semua akun harus terisi");
        tbPengaturan.setName("tbPengaturan"); // NOI18N
        tbPengaturan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPengaturanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPengaturan);

        TabRawat.addTab("Pengaturan Umum", Scroll);

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbPengaturanRalan.setToolTipText("Semua akun harus terisi");
        tbPengaturanRalan.setComponentPopupMenu(jPopupMenu1);
        tbPengaturanRalan.setName("tbPengaturanRalan"); // NOI18N
        tbPengaturanRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPengaturanRalanMouseClicked(evt);
            }
        });
        tbPengaturanRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPengaturanRalanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbPengaturanRalan);

        TabRawat.addTab("Tarif Ralan", Scroll1);

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPengaturanRanap.setToolTipText("Semua akun harus terisi");
        tbPengaturanRanap.setComponentPopupMenu(jPopupMenu1);
        tbPengaturanRanap.setName("tbPengaturanRanap"); // NOI18N
        tbPengaturanRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPengaturanRanapKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbPengaturanRanap);

        TabRawat.addTab("Tarif Ranap", Scroll2);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            Suspen_Piutang_Tindakan_Ralan=tbPengaturan.getValueAt(0,1).toString();
            Tindakan_Ralan=tbPengaturan.getValueAt(1,1).toString();
            Beban_Jasa_Medik_Dokter_Tindakan_Ralan=tbPengaturan.getValueAt(2,1).toString();
            Utang_Jasa_Medik_Dokter_Tindakan_Ralan=tbPengaturan.getValueAt(3,1).toString();
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan=tbPengaturan.getValueAt(4,1).toString();
            Utang_Jasa_Medik_Paramedis_Tindakan_Ralan=tbPengaturan.getValueAt(5,1).toString();
            Beban_KSO_Tindakan_Ralan=tbPengaturan.getValueAt(6,1).toString();
            Utang_KSO_Tindakan_Ralan=tbPengaturan.getValueAt(7,1).toString();
            Beban_Jasa_Sarana_Tindakan_Ralan=tbPengaturan.getValueAt(8,1).toString();
            Utang_Jasa_Sarana_Tindakan_Ralan=tbPengaturan.getValueAt(9,1).toString();
            HPP_BHP_Tindakan_Ralan=tbPengaturan.getValueAt(10,1).toString();
            Persediaan_BHP_Tindakan_Ralan=tbPengaturan.getValueAt(11,1).toString();
            Beban_Jasa_Menejemen_Tindakan_Ralan=tbPengaturan.getValueAt(12,1).toString();
            Utang_Jasa_Menejemen_Tindakan_Ralan=tbPengaturan.getValueAt(13,1).toString();
            Suspen_Piutang_Laborat_Ralan=tbPengaturan.getValueAt(14,1).toString();
            Laborat_Ralan=tbPengaturan.getValueAt(15,1).toString();
            Beban_Jasa_Medik_Dokter_Laborat_Ralan=tbPengaturan.getValueAt(16,1).toString();
            Utang_Jasa_Medik_Dokter_Laborat_Ralan=tbPengaturan.getValueAt(17,1).toString();
            Beban_Jasa_Medik_Petugas_Laborat_Ralan=tbPengaturan.getValueAt(18,1).toString();
            Utang_Jasa_Medik_Petugas_Laborat_Ralan=tbPengaturan.getValueAt(19,1).toString();
            Beban_Kso_Laborat_Ralan=tbPengaturan.getValueAt(20,1).toString();
            Utang_Kso_Laborat_Ralan=tbPengaturan.getValueAt(21,1).toString();
            HPP_Persediaan_Laborat_Rawat_Jalan=tbPengaturan.getValueAt(22,1).toString();
            Persediaan_BHP_Laborat_Rawat_Jalan=tbPengaturan.getValueAt(23,1).toString();
            Beban_Jasa_Sarana_Laborat_Ralan=tbPengaturan.getValueAt(24,1).toString();
            Utang_Jasa_Sarana_Laborat_Ralan=tbPengaturan.getValueAt(25,1).toString();
            Beban_Jasa_Perujuk_Laborat_Ralan=tbPengaturan.getValueAt(26,1).toString();
            Utang_Jasa_Perujuk_Laborat_Ralan=tbPengaturan.getValueAt(27,1).toString();
            Beban_Jasa_Menejemen_Laborat_Ralan=tbPengaturan.getValueAt(28,1).toString();
            Utang_Jasa_Menejemen_Laborat_Ralan=tbPengaturan.getValueAt(29,1).toString();
            Suspen_Piutang_Radiologi_Ralan=tbPengaturan.getValueAt(30,1).toString();
            Radiologi_Ralan=tbPengaturan.getValueAt(31,1).toString();
            Beban_Jasa_Medik_Dokter_Radiologi_Ralan=tbPengaturan.getValueAt(32,1).toString();
            Utang_Jasa_Medik_Dokter_Radiologi_Ralan=tbPengaturan.getValueAt(33,1).toString();
            Beban_Jasa_Medik_Petugas_Radiologi_Ralan=tbPengaturan.getValueAt(34,1).toString();
            Utang_Jasa_Medik_Petugas_Radiologi_Ralan=tbPengaturan.getValueAt(35,1).toString();
            Beban_Kso_Radiologi_Ralan=tbPengaturan.getValueAt(36,1).toString();
            Utang_Kso_Radiologi_Ralan=tbPengaturan.getValueAt(37,1).toString();
            HPP_Persediaan_Radiologi_Rawat_Jalan=tbPengaturan.getValueAt(38,1).toString();
            Persediaan_BHP_Radiologi_Rawat_Jalan=tbPengaturan.getValueAt(39,1).toString();
            Beban_Jasa_Sarana_Radiologi_Ralan=tbPengaturan.getValueAt(40,1).toString();
            Utang_Jasa_Sarana_Radiologi_Ralan=tbPengaturan.getValueAt(41,1).toString();
            Beban_Jasa_Perujuk_Radiologi_Ralan=tbPengaturan.getValueAt(42,1).toString();
            Utang_Jasa_Perujuk_Radiologi_Ralan=tbPengaturan.getValueAt(43,1).toString();
            Beban_Jasa_Menejemen_Radiologi_Ralan=tbPengaturan.getValueAt(44,1).toString();
            Utang_Jasa_Menejemen_Radiologi_Ralan=tbPengaturan.getValueAt(45,1).toString();
            Suspen_Piutang_Obat_Ralan=tbPengaturan.getValueAt(46,1).toString();
            Obat_Ralan=tbPengaturan.getValueAt(47,1).toString();
            HPP_Obat_Rawat_Jalan=tbPengaturan.getValueAt(48,1).toString();
            Persediaan_Obat_Rawat_Jalan=tbPengaturan.getValueAt(49,1).toString();
            Registrasi_Ralan=tbPengaturan.getValueAt(50,1).toString();
            Suspen_Piutang_Operasi_Ralan=tbPengaturan.getValueAt(51,1).toString();
            Operasi_Ralan=tbPengaturan.getValueAt(52,1).toString();
            Beban_Jasa_Medik_Dokter_Operasi_Ralan=tbPengaturan.getValueAt(53,1).toString();
            Utang_Jasa_Medik_Dokter_Operasi_Ralan=tbPengaturan.getValueAt(54,1).toString();
            Beban_Jasa_Medik_Paramedis_Operasi_Ralan=tbPengaturan.getValueAt(55,1).toString();
            Utang_Jasa_Medik_Paramedis_Operasi_Ralan=tbPengaturan.getValueAt(56,1).toString();
            HPP_Obat_Operasi_Ralan=tbPengaturan.getValueAt(57,1).toString();
            Persediaan_Obat_Kamar_Operasi_Ralan=tbPengaturan.getValueAt(58,1).toString();
            Tambahan_Ralan=tbPengaturan.getValueAt(59,1).toString();
            Potongan_Ralan=tbPengaturan.getValueAt(60,1).toString();
            Suspen_Piutang_Tindakan_Ranap=tbPengaturan.getValueAt(61,1).toString();
            Tindakan_Ranap=tbPengaturan.getValueAt(62,1).toString();
            Beban_Jasa_Medik_Dokter_Tindakan_Ranap=tbPengaturan.getValueAt(63,1).toString();
            Utang_Jasa_Medik_Dokter_Tindakan_Ranap=tbPengaturan.getValueAt(64,1).toString();
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap=tbPengaturan.getValueAt(65,1).toString();
            Utang_Jasa_Medik_Paramedis_Tindakan_Ranap=tbPengaturan.getValueAt(66,1).toString();
            Beban_KSO_Tindakan_Ranap=tbPengaturan.getValueAt(67,1).toString();
            Utang_KSO_Tindakan_Ranap=tbPengaturan.getValueAt(68,1).toString();
            Beban_Jasa_Sarana_Tindakan_Ranap=tbPengaturan.getValueAt(69,1).toString();
            Utang_Jasa_Sarana_Tindakan_Ranap=tbPengaturan.getValueAt(70,1).toString();
            Beban_Jasa_Menejemen_Tindakan_Ranap=tbPengaturan.getValueAt(71,1).toString();
            Utang_Jasa_Menejemen_Tindakan_Ranap=tbPengaturan.getValueAt(72,1).toString();
            HPP_BHP_Tindakan_Ranap=tbPengaturan.getValueAt(73,1).toString();
            Persediaan_BHP_Tindakan_Ranap=tbPengaturan.getValueAt(74,1).toString();
            Suspen_Piutang_Laborat_Ranap=tbPengaturan.getValueAt(75,1).toString();
            Laborat_Ranap=tbPengaturan.getValueAt(76,1).toString();
            Beban_Jasa_Medik_Dokter_Laborat_Ranap=tbPengaturan.getValueAt(77,1).toString();
            Utang_Jasa_Medik_Dokter_Laborat_Ranap=tbPengaturan.getValueAt(78,1).toString();
            Beban_Jasa_Medik_Petugas_Laborat_Ranap=tbPengaturan.getValueAt(79,1).toString();
            Utang_Jasa_Medik_Petugas_Laborat_Ranap=tbPengaturan.getValueAt(80,1).toString();
            Beban_Kso_Laborat_Ranap=tbPengaturan.getValueAt(81,1).toString();
            Utang_Kso_Laborat_Ranap=tbPengaturan.getValueAt(82,1).toString();
            HPP_Persediaan_Laborat_Rawat_inap=tbPengaturan.getValueAt(83,1).toString();
            Persediaan_BHP_Laborat_Rawat_Inap=tbPengaturan.getValueAt(84,1).toString();
            Beban_Jasa_Sarana_Laborat_Ranap=tbPengaturan.getValueAt(85,1).toString();
            Utang_Jasa_Sarana_Laborat_Ranap=tbPengaturan.getValueAt(86,1).toString();
            Beban_Jasa_Perujuk_Laborat_Ranap=tbPengaturan.getValueAt(87,1).toString();
            Utang_Jasa_Perujuk_Laborat_Ranap=tbPengaturan.getValueAt(88,1).toString();
            Beban_Jasa_Menejemen_Laborat_Ranap=tbPengaturan.getValueAt(89,1).toString();
            Utang_Jasa_Menejemen_Laborat_Ranap=tbPengaturan.getValueAt(90,1).toString();
            Suspen_Piutang_Radiologi_Ranap=tbPengaturan.getValueAt(91,1).toString();
            Radiologi_Ranap=tbPengaturan.getValueAt(92,1).toString();
            Beban_Jasa_Medik_Dokter_Radiologi_Ranap=tbPengaturan.getValueAt(93,1).toString();
            Utang_Jasa_Medik_Dokter_Radiologi_Ranap=tbPengaturan.getValueAt(94,1).toString();
            Beban_Jasa_Medik_Petugas_Radiologi_Ranap=tbPengaturan.getValueAt(95,1).toString();
            Utang_Jasa_Medik_Petugas_Radiologi_Ranap=tbPengaturan.getValueAt(96,1).toString();
            Beban_Kso_Radiologi_Ranap=tbPengaturan.getValueAt(97,1).toString();
            Utang_Kso_Radiologi_Ranap=tbPengaturan.getValueAt(98,1).toString();
            HPP_Persediaan_Radiologi_Rawat_Inap=tbPengaturan.getValueAt(99,1).toString();
            Persediaan_BHP_Radiologi_Rawat_Inap=tbPengaturan.getValueAt(100,1).toString();
            Beban_Jasa_Sarana_Radiologi_Ranap=tbPengaturan.getValueAt(101,1).toString();
            Utang_Jasa_Sarana_Radiologi_Ranap=tbPengaturan.getValueAt(102,1).toString();
            Beban_Jasa_Perujuk_Radiologi_Ranap=tbPengaturan.getValueAt(103,1).toString();
            Utang_Jasa_Perujuk_Radiologi_Ranap=tbPengaturan.getValueAt(104,1).toString();
            Beban_Jasa_Menejemen_Radiologi_Ranap=tbPengaturan.getValueAt(105,1).toString();
            Utang_Jasa_Menejemen_Radiologi_Ranap=tbPengaturan.getValueAt(106,1).toString();
            Suspen_Piutang_Obat_Ranap=tbPengaturan.getValueAt(107,1).toString();
            Obat_Ranap=tbPengaturan.getValueAt(108,1).toString();
            HPP_Obat_Rawat_Inap=tbPengaturan.getValueAt(109,1).toString();
            Persediaan_Obat_Rawat_Inap=tbPengaturan.getValueAt(110,1).toString();
            Registrasi_Ranap=tbPengaturan.getValueAt(111,1).toString();
            Service_Ranap=tbPengaturan.getValueAt(112,1).toString();
            Tambahan_Ranap=tbPengaturan.getValueAt(113,1).toString();
            Potongan_Ranap=tbPengaturan.getValueAt(114,1).toString();
            Retur_Obat_Ranap=tbPengaturan.getValueAt(115,1).toString();
            Resep_Pulang_Ranap=tbPengaturan.getValueAt(116,1).toString();
            Kamar_Inap=tbPengaturan.getValueAt(117,1).toString();
            Suspen_Piutang_Operasi_Ranap=tbPengaturan.getValueAt(118,1).toString();
            Operasi_Ranap=tbPengaturan.getValueAt(119,1).toString();
            Beban_Jasa_Medik_Dokter_Operasi_Ranap=tbPengaturan.getValueAt(120,1).toString();
            Utang_Jasa_Medik_Dokter_Operasi_Ranap=tbPengaturan.getValueAt(121,1).toString();
            Beban_Jasa_Medik_Paramedis_Operasi_Ranap=tbPengaturan.getValueAt(122,1).toString();
            Utang_Jasa_Medik_Paramedis_Operasi_Ranap=tbPengaturan.getValueAt(123,1).toString();
            HPP_Obat_Operasi_Ranap=tbPengaturan.getValueAt(124,1).toString();
            Persediaan_Obat_Kamar_Operasi_Ranap=tbPengaturan.getValueAt(125,1).toString();
            Harian_Ranap=tbPengaturan.getValueAt(126,1).toString();
            Uang_Muka_Ranap=tbPengaturan.getValueAt(127,1).toString();
            Piutang_Pasien_Ranap=tbPengaturan.getValueAt(128,1).toString();
            Pengadaan_Obat=tbPengaturan.getValueAt(129,1).toString();
            Pemesanan_Obat=tbPengaturan.getValueAt(130,1).toString();
            Kontra_Pemesanan_Obat=tbPengaturan.getValueAt(131,1).toString();
            Bayar_Pemesanan_Obat=tbPengaturan.getValueAt(132,1).toString();
            Penjualan_Obat=tbPengaturan.getValueAt(133,1).toString();
            Piutang_Obat=tbPengaturan.getValueAt(134,1).toString();
            Kontra_Piutang_Obat=tbPengaturan.getValueAt(135,1).toString();
            Retur_Ke_Suplayer=tbPengaturan.getValueAt(136,1).toString();
            Kontra_Retur_Ke_Suplayer=tbPengaturan.getValueAt(137,1).toString();
            Retur_Dari_pembeli=tbPengaturan.getValueAt(138,1).toString();
            Kontra_Retur_Dari_Pembeli=tbPengaturan.getValueAt(139,1).toString();
            Retur_Piutang_Obat=tbPengaturan.getValueAt(140,1).toString();
            Kontra_Retur_Piutang_Obat=tbPengaturan.getValueAt(141,1).toString();
            Pengadaan_Ipsrs=tbPengaturan.getValueAt(142,1).toString();
            Stok_Keluar_Ipsrs=tbPengaturan.getValueAt(143,1).toString();
            Kontra_Stok_Keluar_Ipsrs=tbPengaturan.getValueAt(144,1).toString();
            Bayar_Piutang_Pasien=tbPengaturan.getValueAt(145,1).toString();
            Pengambilan_Utd=tbPengaturan.getValueAt(146,1).toString();
            Kontra_Pengambilan_Utd=tbPengaturan.getValueAt(147,1).toString();        
            Pengambilan_Penunjang_Utd=tbPengaturan.getValueAt(148,1).toString();
            Kontra_Pengambilan_Penunjang_Utd=tbPengaturan.getValueAt(149,1).toString();
            Penyerahan_Darah=tbPengaturan.getValueAt(150,1).toString();
            Stok_Keluar_Medis=tbPengaturan.getValueAt(151,1).toString();
            Kontra_Stok_Keluar_Medis=tbPengaturan.getValueAt(152,1).toString();
            HPP_Obat_Jual_Bebas=tbPengaturan.getValueAt(153,1).toString();
            Persediaan_Obat_Jual_Bebas=tbPengaturan.getValueAt(154,1).toString();
            Penerimaan_NonMedis=tbPengaturan.getValueAt(155,1).toString();
            Kontra_Penerimaan_NonMedis=tbPengaturan.getValueAt(156,1).toString();
            Bayar_Pemesanan_Non_Medis=tbPengaturan.getValueAt(157,1).toString();
            Hibah_Obat=tbPengaturan.getValueAt(158,1).toString();
            Kontra_Hibah_Obat=tbPengaturan.getValueAt(159,1).toString();
            Penerimaan_Toko=tbPengaturan.getValueAt(160,1).toString();
            Kontra_Penerimaan_Toko=tbPengaturan.getValueAt(161,1).toString();
            Pengadaan_Toko=tbPengaturan.getValueAt(162,1).toString();
            Bayar_Pemesanan_Toko=tbPengaturan.getValueAt(163,1).toString();
            Penjualan_Toko=tbPengaturan.getValueAt(164,1).toString();
            HPP_Barang_Toko=tbPengaturan.getValueAt(165,1).toString();
            Persediaan_Barang_Toko=tbPengaturan.getValueAt(166,1).toString();
            Piutang_Toko=tbPengaturan.getValueAt(167,1).toString();
            Kontra_Piutang_Toko=tbPengaturan.getValueAt(168,1).toString();
            Retur_Beli_Toko=tbPengaturan.getValueAt(169,1).toString();
            Kontra_Retur_Beli_Toko=tbPengaturan.getValueAt(170,1).toString();
            Retur_Beli_Non_Medis=tbPengaturan.getValueAt(171,1).toString();
            Kontra_Retur_Beli_Non_Medis=tbPengaturan.getValueAt(172,1).toString();
            Retur_Jual_Toko=tbPengaturan.getValueAt(173,1).toString();
            Kontra_Retur_Jual_Toko=tbPengaturan.getValueAt(174,1).toString();
            Retur_Piutang_Toko=tbPengaturan.getValueAt(175,1).toString();
            Kontra_Retur_Piutang_Toko=tbPengaturan.getValueAt(176,1).toString();
            
            if(Pengadaan_Obat.equals("")||Pemesanan_Obat.equals("")||Kontra_Pemesanan_Obat.equals("")||Bayar_Pemesanan_Obat.equals("")||Penjualan_Obat.equals("")||
                    Piutang_Obat.equals("")||Kontra_Piutang_Obat.equals("")||Retur_Ke_Suplayer.equals("")||Kontra_Retur_Ke_Suplayer.equals("")||
                    Retur_Dari_pembeli.equals("")||Kontra_Retur_Dari_Pembeli.equals("")||Retur_Piutang_Obat.equals("")||Kontra_Retur_Piutang_Obat.equals("")||
                    Pengadaan_Ipsrs.equals("")||Stok_Keluar_Ipsrs.equals("")||Kontra_Stok_Keluar_Ipsrs.equals("")||Bayar_Piutang_Pasien.equals("")||
                    Pengambilan_Utd.equals("")||Kontra_Pengambilan_Utd.equals("")||Pengambilan_Penunjang_Utd.equals("")||
                    Kontra_Pengambilan_Penunjang_Utd.equals("")||Penyerahan_Darah.equals("")||Stok_Keluar_Medis.equals("")||Kontra_Stok_Keluar_Medis.equals("")||
                    HPP_Obat_Jual_Bebas.equals("")||Persediaan_Obat_Jual_Bebas.equals("")||Penerimaan_NonMedis.equals("")||Kontra_Penerimaan_NonMedis.equals("")||
                    Bayar_Pemesanan_Non_Medis.equals("")||Hibah_Obat.equals("")||Kontra_Hibah_Obat.equals("")||Penerimaan_Toko.equals("")||Kontra_Penerimaan_Toko.equals("")||
                    Pengadaan_Toko.equals("")||Bayar_Pemesanan_Toko.equals("")||Penjualan_Toko.equals("")||HPP_Barang_Toko.equals("")||Persediaan_Barang_Toko.equals("")||
                    Piutang_Toko.equals("")||Kontra_Piutang_Toko.equals("")||Retur_Beli_Toko.equals("")||Kontra_Retur_Beli_Toko.equals("")||Retur_Beli_Non_Medis.equals("")||
                    Kontra_Retur_Beli_Non_Medis.equals("")||Retur_Jual_Toko.equals("")||Kontra_Retur_Jual_Toko.equals("")||Retur_Piutang_Toko.equals("")||
                    Kontra_Retur_Piutang_Toko.equals("")||Suspen_Piutang_Tindakan_Ralan.equals("")||Tindakan_Ralan.equals("")||Beban_Jasa_Medik_Dokter_Tindakan_Ralan.equals("")||
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan.equals("")||Beban_Jasa_Medik_Paramedis_Tindakan_Ralan.equals("")||Utang_Jasa_Medik_Paramedis_Tindakan_Ralan.equals("")||
                    Beban_KSO_Tindakan_Ralan.equals("")||Utang_KSO_Tindakan_Ralan.equals("")||Beban_Jasa_Sarana_Tindakan_Ralan.equals("")||Utang_Jasa_Sarana_Tindakan_Ralan.equals("")||
                    HPP_BHP_Tindakan_Ralan.equals("")||Persediaan_BHP_Tindakan_Ralan.equals("")||Beban_Jasa_Menejemen_Tindakan_Ralan.equals("")||
                    Utang_Jasa_Menejemen_Tindakan_Ralan.equals("")||Suspen_Piutang_Laborat_Ralan.equals("")||Laborat_Ralan.equals("")||
                    Beban_Jasa_Medik_Dokter_Laborat_Ralan.equals("")||Utang_Jasa_Medik_Dokter_Laborat_Ralan.equals("")||Beban_Jasa_Medik_Petugas_Laborat_Ralan.equals("")||
                    Utang_Jasa_Medik_Petugas_Laborat_Ralan.equals("")||Beban_Kso_Laborat_Ralan.equals("")||Utang_Kso_Laborat_Ralan.equals("")||
                    HPP_Persediaan_Laborat_Rawat_Jalan.equals("")||Persediaan_BHP_Laborat_Rawat_Jalan.equals("")||Beban_Jasa_Sarana_Laborat_Ralan.equals("")||
                    Utang_Jasa_Sarana_Laborat_Ralan.equals("")||Beban_Jasa_Perujuk_Laborat_Ralan.equals("")||Utang_Jasa_Perujuk_Laborat_Ralan.equals("")||
                    Beban_Jasa_Menejemen_Laborat_Ralan.equals("")||Utang_Jasa_Menejemen_Laborat_Ralan.equals("")||Suspen_Piutang_Radiologi_Ralan.equals("")||
                    Radiologi_Ralan.equals("")||Beban_Jasa_Medik_Dokter_Radiologi_Ralan.equals("")||Utang_Jasa_Medik_Dokter_Radiologi_Ralan.equals("")||
                    Beban_Jasa_Medik_Petugas_Radiologi_Ralan.equals("")||Utang_Jasa_Medik_Petugas_Radiologi_Ralan.equals("")||Beban_Kso_Radiologi_Ralan.equals("")||
                    Utang_Kso_Radiologi_Ralan.equals("")||HPP_Persediaan_Radiologi_Rawat_Jalan.equals("")||Persediaan_BHP_Radiologi_Rawat_Jalan.equals("")||
                    Beban_Jasa_Sarana_Radiologi_Ralan.equals("")||Utang_Jasa_Sarana_Radiologi_Ralan.equals("")||Beban_Jasa_Perujuk_Radiologi_Ralan.equals("")||
                    Utang_Jasa_Perujuk_Radiologi_Ralan.equals("")||Beban_Jasa_Menejemen_Radiologi_Ralan.equals("")||Utang_Jasa_Menejemen_Radiologi_Ralan.equals("")||
                    Suspen_Piutang_Obat_Ralan.equals("")||Obat_Ralan.equals("")||HPP_Obat_Rawat_Jalan.equals("")||Persediaan_Obat_Rawat_Jalan.equals("")||
                    Registrasi_Ralan.equals("")||Suspen_Piutang_Operasi_Ralan.equals("")||Operasi_Ralan.equals("")||Beban_Jasa_Medik_Dokter_Operasi_Ralan.equals("")||
                    Utang_Jasa_Medik_Dokter_Operasi_Ralan.equals("")||Beban_Jasa_Medik_Paramedis_Operasi_Ralan.equals("")||Utang_Jasa_Medik_Paramedis_Operasi_Ralan.equals("")||
                    HPP_Obat_Operasi_Ralan.equals("")||Persediaan_Obat_Kamar_Operasi_Ralan.equals("")||Tambahan_Ralan.equals("")||Potongan_Ralan.equals("")||
                    Suspen_Piutang_Tindakan_Ranap.equals("")||Tindakan_Ranap.equals("")||Beban_Jasa_Medik_Dokter_Tindakan_Ranap.equals("")||
                    Utang_Jasa_Medik_Dokter_Tindakan_Ranap.equals("")||Beban_Jasa_Medik_Paramedis_Tindakan_Ranap.equals("")||Utang_Jasa_Medik_Paramedis_Tindakan_Ranap.equals("")||
                    Beban_KSO_Tindakan_Ranap.equals("")||Utang_KSO_Tindakan_Ranap.equals("")||Beban_Jasa_Sarana_Tindakan_Ranap.equals("")||
                    Utang_Jasa_Sarana_Tindakan_Ranap.equals("")||Beban_Jasa_Menejemen_Tindakan_Ranap.equals("")||Utang_Jasa_Menejemen_Tindakan_Ranap.equals("")||
                    HPP_BHP_Tindakan_Ranap.equals("")||Persediaan_BHP_Tindakan_Ranap.equals("")||Suspen_Piutang_Laborat_Ranap.equals("")||Laborat_Ranap.equals("")||
                    Beban_Jasa_Medik_Dokter_Laborat_Ranap.equals("")||Utang_Jasa_Medik_Dokter_Laborat_Ranap.equals("")||Beban_Jasa_Medik_Petugas_Laborat_Ranap.equals("")||
                    Utang_Jasa_Medik_Petugas_Laborat_Ranap.equals("")||Beban_Kso_Laborat_Ranap.equals("")||Utang_Kso_Laborat_Ranap.equals("")||
                    HPP_Persediaan_Laborat_Rawat_inap.equals("")||Persediaan_BHP_Laborat_Rawat_Inap.equals("")||Beban_Jasa_Sarana_Laborat_Ranap.equals("")||
                    Utang_Jasa_Sarana_Laborat_Ranap.equals("")||Beban_Jasa_Perujuk_Laborat_Ranap.equals("")||Utang_Jasa_Perujuk_Laborat_Ranap.equals("")||
                    Beban_Jasa_Menejemen_Laborat_Ranap.equals("")||Utang_Jasa_Menejemen_Laborat_Ranap.equals("")||Suspen_Piutang_Radiologi_Ranap.equals("")||
                    Radiologi_Ranap.equals("")||Beban_Jasa_Medik_Dokter_Radiologi_Ranap.equals("")||Utang_Jasa_Medik_Dokter_Radiologi_Ranap.equals("")||
                    Beban_Jasa_Medik_Petugas_Radiologi_Ranap.equals("")||Utang_Jasa_Medik_Petugas_Radiologi_Ranap.equals("")||Beban_Kso_Radiologi_Ranap.equals("")||
                    Utang_Kso_Radiologi_Ranap.equals("")||HPP_Persediaan_Radiologi_Rawat_Inap.equals("")||Persediaan_BHP_Radiologi_Rawat_Inap.equals("")||Beban_Jasa_Sarana_Radiologi_Ranap.equals("")||Utang_Jasa_Sarana_Radiologi_Ranap.equals("")||Beban_Jasa_Perujuk_Radiologi_Ranap.equals("")||Utang_Jasa_Perujuk_Radiologi_Ranap.equals("")||Beban_Jasa_Menejemen_Radiologi_Ranap.equals("")||Utang_Jasa_Menejemen_Radiologi_Ranap.equals("")||Suspen_Piutang_Obat_Ranap.equals("")||Obat_Ranap.equals("")||HPP_Obat_Rawat_Inap.equals("")||Persediaan_Obat_Rawat_Inap.equals("")||Registrasi_Ranap.equals("")||Service_Ranap.equals("")||Tambahan_Ranap.equals("")||Potongan_Ranap.equals("")||Retur_Obat_Ranap.equals("")||Resep_Pulang_Ranap.equals("")||Kamar_Inap.equals("")||Suspen_Piutang_Operasi_Ranap.equals("")||Operasi_Ranap.equals("")||Beban_Jasa_Medik_Dokter_Operasi_Ranap.equals("")||Utang_Jasa_Medik_Dokter_Operasi_Ranap.equals("")||Beban_Jasa_Medik_Paramedis_Operasi_Ranap.equals("")||Utang_Jasa_Medik_Paramedis_Operasi_Ranap.equals("")||HPP_Obat_Operasi_Ranap.equals("")||Persediaan_Obat_Kamar_Operasi_Ranap.equals("")||Harian_Ranap.equals("")||Uang_Muka_Ranap.equals("")||Piutang_Pasien_Ranap.equals("")){
                    JOptionPane.showMessageDialog(null,"Silahkan lengkapi seluruh data Akun...!!!!");
                    tbPengaturan.requestFocus();
            }else{
                Sequel.queryu("delete from set_akun_ralan");
                Sequel.menyimpan("set_akun_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",61,new String[]{
                    Suspen_Piutang_Tindakan_Ralan,Tindakan_Ralan,Beban_Jasa_Medik_Dokter_Tindakan_Ralan,Utang_Jasa_Medik_Dokter_Tindakan_Ralan,Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,Beban_KSO_Tindakan_Ralan,Utang_KSO_Tindakan_Ralan,Beban_Jasa_Sarana_Tindakan_Ralan,Utang_Jasa_Sarana_Tindakan_Ralan,
                    HPP_BHP_Tindakan_Ralan,Persediaan_BHP_Tindakan_Ralan,Beban_Jasa_Menejemen_Tindakan_Ralan,Utang_Jasa_Menejemen_Tindakan_Ralan,Suspen_Piutang_Laborat_Ralan,Laborat_Ralan,
                    Beban_Jasa_Medik_Dokter_Laborat_Ralan,Utang_Jasa_Medik_Dokter_Laborat_Ralan,Beban_Jasa_Medik_Petugas_Laborat_Ralan,Utang_Jasa_Medik_Petugas_Laborat_Ralan,
                    Beban_Kso_Laborat_Ralan,Utang_Kso_Laborat_Ralan,HPP_Persediaan_Laborat_Rawat_Jalan,Persediaan_BHP_Laborat_Rawat_Jalan,Beban_Jasa_Sarana_Laborat_Ralan,
                    Utang_Jasa_Sarana_Laborat_Ralan,Beban_Jasa_Perujuk_Laborat_Ralan,Utang_Jasa_Perujuk_Laborat_Ralan,Beban_Jasa_Menejemen_Laborat_Ralan,Utang_Jasa_Menejemen_Laborat_Ralan,
                    Suspen_Piutang_Radiologi_Ralan,Radiologi_Ralan,Beban_Jasa_Medik_Dokter_Radiologi_Ralan,Utang_Jasa_Medik_Dokter_Radiologi_Ralan,Beban_Jasa_Medik_Petugas_Radiologi_Ralan,
                    Utang_Jasa_Medik_Petugas_Radiologi_Ralan,Beban_Kso_Radiologi_Ralan,Utang_Kso_Radiologi_Ralan,HPP_Persediaan_Radiologi_Rawat_Jalan,Persediaan_BHP_Radiologi_Rawat_Jalan,
                    Beban_Jasa_Sarana_Radiologi_Ralan,Utang_Jasa_Sarana_Radiologi_Ralan,Beban_Jasa_Perujuk_Radiologi_Ralan,Utang_Jasa_Perujuk_Radiologi_Ralan,Beban_Jasa_Menejemen_Radiologi_Ralan,
                    Utang_Jasa_Menejemen_Radiologi_Ralan,Suspen_Piutang_Obat_Ralan,Obat_Ralan,HPP_Obat_Rawat_Jalan,Persediaan_Obat_Rawat_Jalan,Registrasi_Ralan,Suspen_Piutang_Operasi_Ralan,
                    Operasi_Ralan,Beban_Jasa_Medik_Dokter_Operasi_Ralan,Utang_Jasa_Medik_Dokter_Operasi_Ralan,Beban_Jasa_Medik_Paramedis_Operasi_Ralan,Utang_Jasa_Medik_Paramedis_Operasi_Ralan,
                    HPP_Obat_Operasi_Ralan,Persediaan_Obat_Kamar_Operasi_Ralan,Tambahan_Ralan,Potongan_Ralan
                });
                Sequel.queryu("delete from set_akun_ranap");
                Sequel.menyimpan("set_akun_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",64,new String[]{
                    Suspen_Piutang_Tindakan_Ranap,Tindakan_Ranap,Beban_Jasa_Medik_Dokter_Tindakan_Ranap,Utang_Jasa_Medik_Dokter_Tindakan_Ranap,Beban_Jasa_Medik_Paramedis_Tindakan_Ranap,
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ranap,Beban_KSO_Tindakan_Ranap,Utang_KSO_Tindakan_Ranap,Beban_Jasa_Sarana_Tindakan_Ranap,Utang_Jasa_Sarana_Tindakan_Ranap,
                    Beban_Jasa_Menejemen_Tindakan_Ranap,Utang_Jasa_Menejemen_Tindakan_Ranap,HPP_BHP_Tindakan_Ranap,Persediaan_BHP_Tindakan_Ranap,Suspen_Piutang_Laborat_Ranap,Laborat_Ranap,
                    Beban_Jasa_Medik_Dokter_Laborat_Ranap,Utang_Jasa_Medik_Dokter_Laborat_Ranap,Beban_Jasa_Medik_Petugas_Laborat_Ranap,Utang_Jasa_Medik_Petugas_Laborat_Ranap,
                    Beban_Kso_Laborat_Ranap,Utang_Kso_Laborat_Ranap,HPP_Persediaan_Laborat_Rawat_inap,Persediaan_BHP_Laborat_Rawat_Inap,Beban_Jasa_Sarana_Laborat_Ranap,
                    Utang_Jasa_Sarana_Laborat_Ranap,Beban_Jasa_Perujuk_Laborat_Ranap,Utang_Jasa_Perujuk_Laborat_Ranap,Beban_Jasa_Menejemen_Laborat_Ranap,Utang_Jasa_Menejemen_Laborat_Ranap,
                    Suspen_Piutang_Radiologi_Ranap,Radiologi_Ranap,Beban_Jasa_Medik_Dokter_Radiologi_Ranap,Utang_Jasa_Medik_Dokter_Radiologi_Ranap,Beban_Jasa_Medik_Petugas_Radiologi_Ranap,
                    Utang_Jasa_Medik_Petugas_Radiologi_Ranap,Beban_Kso_Radiologi_Ranap,Utang_Kso_Radiologi_Ranap,HPP_Persediaan_Radiologi_Rawat_Inap,Persediaan_BHP_Radiologi_Rawat_Inap,
                    Beban_Jasa_Sarana_Radiologi_Ranap,Utang_Jasa_Sarana_Radiologi_Ranap,Beban_Jasa_Perujuk_Radiologi_Ranap,Utang_Jasa_Perujuk_Radiologi_Ranap,Beban_Jasa_Menejemen_Radiologi_Ranap,
                    Utang_Jasa_Menejemen_Radiologi_Ranap,Suspen_Piutang_Obat_Ranap,Obat_Ranap,HPP_Obat_Rawat_Inap,Persediaan_Obat_Rawat_Inap,Registrasi_Ranap,Service_Ranap,Tambahan_Ranap,
                    Potongan_Ranap,Retur_Obat_Ranap,Resep_Pulang_Ranap,Kamar_Inap,Suspen_Piutang_Operasi_Ranap,Operasi_Ranap,Beban_Jasa_Medik_Dokter_Operasi_Ranap,Utang_Jasa_Medik_Dokter_Operasi_Ranap,
                    Beban_Jasa_Medik_Paramedis_Operasi_Ranap,Utang_Jasa_Medik_Paramedis_Operasi_Ranap,HPP_Obat_Operasi_Ranap
                });
                Sequel.queryu("delete from set_akun_ranap2");
                Sequel.menyimpan("set_akun_ranap2","?,?,?,?",4,new String[]{
                   Persediaan_Obat_Kamar_Operasi_Ranap,Harian_Ranap,Uang_Muka_Ranap,Piutang_Pasien_Ranap
                });
                Sequel.queryu("delete from set_akun");
                Sequel.menyimpan("set_akun","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",48,new String[]{
                    Pengadaan_Obat,
                    Pemesanan_Obat,Kontra_Pemesanan_Obat,Bayar_Pemesanan_Obat,Penjualan_Obat,Piutang_Obat,
                    Kontra_Piutang_Obat,Retur_Ke_Suplayer,Kontra_Retur_Ke_Suplayer,Retur_Dari_pembeli,
                    Kontra_Retur_Dari_Pembeli,Retur_Piutang_Obat,Kontra_Retur_Piutang_Obat,Pengadaan_Ipsrs,
                    Stok_Keluar_Ipsrs,Kontra_Stok_Keluar_Ipsrs,Bayar_Piutang_Pasien,Pengambilan_Utd,
                    Kontra_Pengambilan_Utd,Pengambilan_Penunjang_Utd,Kontra_Pengambilan_Penunjang_Utd,
                    Penyerahan_Darah,Stok_Keluar_Medis,Kontra_Stok_Keluar_Medis,HPP_Obat_Jual_Bebas,
                    Persediaan_Obat_Jual_Bebas,Penerimaan_NonMedis,Kontra_Penerimaan_NonMedis,
                    Bayar_Pemesanan_Non_Medis,Hibah_Obat,Kontra_Hibah_Obat,Penerimaan_Toko,Kontra_Penerimaan_Toko,
                    Pengadaan_Toko,Bayar_Pemesanan_Toko,Penjualan_Toko,HPP_Barang_Toko,Persediaan_Barang_Toko,
                    Piutang_Toko,Kontra_Piutang_Toko,Retur_Beli_Toko,Kontra_Retur_Beli_Toko,Retur_Beli_Non_Medis,
                    Kontra_Retur_Beli_Non_Medis,Retur_Jual_Toko,Kontra_Retur_Jual_Toko,Retur_Piutang_Toko,
                    Kontra_Retur_Piutang_Toko
                });
                JOptionPane.showMessageDialog(null,"Proses selesai...!!!!");
                tampil();
            }
        }else if(TabRawat.getSelectedIndex()==1){
            for(i=0;i<tbPengaturanRalan.getRowCount();i++){ 
                if((!tbPengaturanRalan.getValueAt(i,5).equals(""))&&(!tbPengaturanRalan.getValueAt(i,6).equals(""))&&(!tbPengaturanRalan.getValueAt(i,7).equals(""))&&
                    (!tbPengaturanRalan.getValueAt(i,8).equals(""))&&(!tbPengaturanRalan.getValueAt(i,9).equals(""))&&(!tbPengaturanRalan.getValueAt(i,10).equals(""))&&
                     (!tbPengaturanRalan.getValueAt(i,11).equals(""))&&(!tbPengaturanRalan.getValueAt(i,12).equals(""))&&(!tbPengaturanRalan.getValueAt(i,13).equals(""))&&
                      (!tbPengaturanRalan.getValueAt(i,14).equals(""))&&(!tbPengaturanRalan.getValueAt(i,15).equals(""))&&(!tbPengaturanRalan.getValueAt(i,16).equals(""))&&
                       (!tbPengaturanRalan.getValueAt(i,17).equals(""))&&(!tbPengaturanRalan.getValueAt(i,18).equals(""))&&(!tbPengaturanRalan.getValueAt(i,19).equals(""))&&
                        (!tbPengaturanRalan.getValueAt(i,20).equals(""))&&(!tbPengaturanRalan.getValueAt(i,21).equals(""))&&(!tbPengaturanRalan.getValueAt(i,22).equals(""))&&
                         (!tbPengaturanRalan.getValueAt(i,23).equals(""))&&(!tbPengaturanRalan.getValueAt(i,24).equals(""))&&(!tbPengaturanRalan.getValueAt(i,25).equals(""))&&
                          (!tbPengaturanRalan.getValueAt(i,26).equals(""))&&(!tbPengaturanRalan.getValueAt(i,27).equals(""))&&(!tbPengaturanRalan.getValueAt(i,28).equals(""))&&
                           (!tbPengaturanRalan.getValueAt(i,29).equals(""))&&(!tbPengaturanRalan.getValueAt(i,30).equals(""))){
                    Sequel.meghapus("matrik_akun_jns_perawatan","kd_jenis_prw",tbPengaturanRalan.getValueAt(i,0).toString());
                    Sequel.menyimpan("matrik_akun_jns_perawatan","?,?,?,?,?,?,?,?,?,?,?,?,?,?", 14,new String[]{
                        tbPengaturanRalan.getValueAt(i,0).toString(),tbPengaturanRalan.getValueAt(i,5).toString(),
                        tbPengaturanRalan.getValueAt(i,7).toString(),tbPengaturanRalan.getValueAt(i,9).toString(),
                        tbPengaturanRalan.getValueAt(i,11).toString(),tbPengaturanRalan.getValueAt(i,13).toString(),
                        tbPengaturanRalan.getValueAt(i,15).toString(),tbPengaturanRalan.getValueAt(i,17).toString(),
                        tbPengaturanRalan.getValueAt(i,19).toString(),tbPengaturanRalan.getValueAt(i,21).toString(),
                        tbPengaturanRalan.getValueAt(i,23).toString(),tbPengaturanRalan.getValueAt(i,25).toString(),
                        tbPengaturanRalan.getValueAt(i,27).toString(),tbPengaturanRalan.getValueAt(i,29).toString()
                    });
                }
            }
            tampilralan();
        }else if(TabRawat.getSelectedIndex()==2){
            for(i=0;i<tbPengaturanRanap.getRowCount();i++){ 
                if((!tbPengaturanRanap.getValueAt(i,6).equals(""))&&(!tbPengaturanRanap.getValueAt(i,7).equals(""))&&(!tbPengaturanRanap.getValueAt(i,8).equals(""))&&
                    (!tbPengaturanRanap.getValueAt(i,9).equals(""))&&(!tbPengaturanRanap.getValueAt(i,10).equals(""))&&(!tbPengaturanRanap.getValueAt(i,11).equals(""))&&
                     (!tbPengaturanRanap.getValueAt(i,12).equals(""))&&(!tbPengaturanRanap.getValueAt(i,13).equals(""))&&(!tbPengaturanRanap.getValueAt(i,14).equals(""))&&
                      (!tbPengaturanRanap.getValueAt(i,15).equals(""))&&(!tbPengaturanRanap.getValueAt(i,16).equals(""))&&(!tbPengaturanRanap.getValueAt(i,17).equals(""))&&
                       (!tbPengaturanRanap.getValueAt(i,18).equals(""))&&(!tbPengaturanRanap.getValueAt(i,19).equals(""))&&(!tbPengaturanRanap.getValueAt(i,20).equals(""))&&
                        (!tbPengaturanRanap.getValueAt(i,21).equals(""))&&(!tbPengaturanRanap.getValueAt(i,22).equals(""))&&(!tbPengaturanRanap.getValueAt(i,23).equals(""))&&
                         (!tbPengaturanRanap.getValueAt(i,24).equals(""))&&(!tbPengaturanRanap.getValueAt(i,25).equals(""))&&(!tbPengaturanRanap.getValueAt(i,26).equals(""))&&
                          (!tbPengaturanRanap.getValueAt(i,27).equals(""))&&(!tbPengaturanRanap.getValueAt(i,28).equals(""))&&(!tbPengaturanRanap.getValueAt(i,29).equals(""))&&
                           (!tbPengaturanRanap.getValueAt(i,30).equals(""))&&(!tbPengaturanRanap.getValueAt(i,31).equals(""))){
                    Sequel.meghapus("matrik_akun_jns_perawatan_inap","kd_jenis_prw",tbPengaturanRanap.getValueAt(i,0).toString());
                    Sequel.menyimpan("matrik_akun_jns_perawatan_inap","?,?,?,?,?,?,?,?,?,?,?,?,?,?", 14,new String[]{
                        tbPengaturanRanap.getValueAt(i,0).toString(),tbPengaturanRanap.getValueAt(i,6).toString(),
                        tbPengaturanRanap.getValueAt(i,8).toString(),tbPengaturanRanap.getValueAt(i,10).toString(),
                        tbPengaturanRanap.getValueAt(i,12).toString(),tbPengaturanRanap.getValueAt(i,14).toString(),
                        tbPengaturanRanap.getValueAt(i,16).toString(),tbPengaturanRanap.getValueAt(i,18).toString(),
                        tbPengaturanRanap.getValueAt(i,20).toString(),tbPengaturanRanap.getValueAt(i,22).toString(),
                        tbPengaturanRanap.getValueAt(i,24).toString(),tbPengaturanRanap.getValueAt(i,26).toString(),
                        tbPengaturanRanap.getValueAt(i,28).toString(),tbPengaturanRanap.getValueAt(i,30).toString()
                    });
                }
            }
            tampilranap();
        }     
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbPengaturanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPengaturanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                akses.setform("DlgPengaturanRekening");
                rekening.emptTeks();
                rekening.tampil();
                rekening.isCek();
                rekening.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                rekening.setLocationRelativeTo(internalFrame1);
                rekening.setVisible(true);
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                tabMode.setValueAt("",tbPengaturan.getSelectedRow(),1);
                tabMode.setValueAt("",tbPengaturan.getSelectedRow(),2);
            }
        }
    }//GEN-LAST:event_tbPengaturanKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilralan();
        }else if(TabRawat.getSelectedIndex()==2){
            tampilranap();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbPengaturanRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPengaturanRalanKeyPressed
        if(tabModeRalan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                akses.setform("DlgPengaturanRekeningRalan");
                rekening.emptTeks();
                rekening.tampil();
                rekening.isCek();
                rekening.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                rekening.setLocationRelativeTo(internalFrame1);
                rekening.setVisible(true);
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),5);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),6);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),7);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),8);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),9);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),10);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),11);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),12);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),13);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),14);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),15);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),16);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),17);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),18);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),19);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),20);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),21);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),22);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),23);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),24);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),25);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),26);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),27);                
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),28);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),29);
                tabModeRalan.setValueAt("",tbPengaturanRalan.getSelectedRow(),30);

            }
        }
    }//GEN-LAST:event_tbPengaturanRalanKeyPressed

    private void tbPengaturanRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPengaturanRanapKeyPressed
        if(tabModeRanap.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                akses.setform("DlgPengaturanRekeningRanap");
                rekening.emptTeks();
                rekening.tampil();
                rekening.isCek();
                rekening.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                rekening.setLocationRelativeTo(internalFrame1);
                rekening.setVisible(true);
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),6);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),7);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),8);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),9);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),10);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),11);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),12);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),13);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),14);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),15);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),16);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),17);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),18);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),19);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),20);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),21);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),22);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),23);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),24);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),25);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),26);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),27);                
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),28);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),29);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),30);
                tabModeRanap.setValueAt("",tbPengaturanRanap.getSelectedRow(),31);
            }
        }
    }//GEN-LAST:event_tbPengaturanRanapKeyPressed

    private void MnCopyRekeningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCopyRekeningActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            if(tbPengaturanRalan.getSelectedRow()!= -1){
                i=tbPengaturanRalan.getSelectedRow();
                if((!tbPengaturanRalan.getValueAt(i,5).equals(""))&&(!tbPengaturanRalan.getValueAt(i,6).equals(""))&&(!tbPengaturanRalan.getValueAt(i,7).equals(""))&&
                        (!tbPengaturanRalan.getValueAt(i,8).equals(""))&&(!tbPengaturanRalan.getValueAt(i,9).equals(""))&&(!tbPengaturanRalan.getValueAt(i,10).equals(""))&&
                         (!tbPengaturanRalan.getValueAt(i,11).equals(""))&&(!tbPengaturanRalan.getValueAt(i,12).equals(""))&&(!tbPengaturanRalan.getValueAt(i,13).equals(""))&&
                          (!tbPengaturanRalan.getValueAt(i,14).equals(""))&&(!tbPengaturanRalan.getValueAt(i,15).equals(""))&&(!tbPengaturanRalan.getValueAt(i,16).equals(""))&&
                           (!tbPengaturanRalan.getValueAt(i,17).equals(""))&&(!tbPengaturanRalan.getValueAt(i,18).equals(""))&&(!tbPengaturanRalan.getValueAt(i,19).equals(""))&&
                            (!tbPengaturanRalan.getValueAt(i,20).equals(""))&&(!tbPengaturanRalan.getValueAt(i,21).equals(""))&&(!tbPengaturanRalan.getValueAt(i,22).equals(""))&&
                             (!tbPengaturanRalan.getValueAt(i,23).equals(""))&&(!tbPengaturanRalan.getValueAt(i,24).equals(""))&&(!tbPengaturanRalan.getValueAt(i,25).equals(""))&&
                              (!tbPengaturanRalan.getValueAt(i,26).equals(""))&&(!tbPengaturanRalan.getValueAt(i,27).equals(""))&&(!tbPengaturanRalan.getValueAt(i,28).equals(""))&&
                               (!tbPengaturanRalan.getValueAt(i,29).equals(""))&&(!tbPengaturanRalan.getValueAt(i,30).equals(""))){
                    copyakun="copy";
                    barisdicopy=i;
                    JOptionPane.showMessageDialog(null,"Silahkan pilih tindakan tujuan..!!"); 
                }else{
                    barisdicopy=-1;
                    copyakun="";
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data tindakan yang mau dicopy akun rekeningnya...!!!");
                    tbPengaturanRalan.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_MnCopyRekeningActionPerformed

    private void tbPengaturanRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPengaturanRalanMouseClicked
        if(tabModeRalan.getRowCount()!=0){
            if(evt.getClickCount()==1){
                if(copyakun.equals("copy")){
                    int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data copy akun rekeningnya..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        Sequel.meghapus("matrik_akun_jns_perawatan","kd_jenis_prw",tbPengaturanRalan.getValueAt(tbPengaturanRalan.getSelectedRow(),0).toString());
                        Sequel.menyimpan("matrik_akun_jns_perawatan","?,?,?,?,?,?,?,?,?,?,?,?,?,?", 14,new String[]{
                            tbPengaturanRalan.getValueAt(tbPengaturanRalan.getSelectedRow(),0).toString(),tbPengaturanRalan.getValueAt(barisdicopy,5).toString(),
                            tbPengaturanRalan.getValueAt(barisdicopy,7).toString(),tbPengaturanRalan.getValueAt(barisdicopy,9).toString(),
                            tbPengaturanRalan.getValueAt(barisdicopy,11).toString(),tbPengaturanRalan.getValueAt(barisdicopy,13).toString(),
                            tbPengaturanRalan.getValueAt(barisdicopy,15).toString(),tbPengaturanRalan.getValueAt(barisdicopy,17).toString(),
                            tbPengaturanRalan.getValueAt(barisdicopy,19).toString(),tbPengaturanRalan.getValueAt(barisdicopy,21).toString(),
                            tbPengaturanRalan.getValueAt(barisdicopy,23).toString(),tbPengaturanRalan.getValueAt(barisdicopy,25).toString(),
                            tbPengaturanRalan.getValueAt(barisdicopy,27).toString(),tbPengaturanRalan.getValueAt(barisdicopy,29).toString()
                        });
                        tampilralan();
                        barisdicopy=-1;
                        copyakun="";
                    }else{
                        barisdicopy=-1;
                        copyakun="";
                    }
                }
            }
        }
    }//GEN-LAST:event_tbPengaturanRalanMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPengaturanRekening dialog = new DlgPengaturanRekening(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private javax.swing.JMenuItem MnCopyRekening;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.Table tbPengaturan;
    private widget.Table tbPengaturanRalan;
    private widget.Table tbPengaturanRanap;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            Suspen_Piutang_Tindakan_Ralan="";
            Tindakan_Ralan="";
            Beban_Jasa_Medik_Dokter_Tindakan_Ralan="";
            Utang_Jasa_Medik_Dokter_Tindakan_Ralan="";
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan="";
            Utang_Jasa_Medik_Paramedis_Tindakan_Ralan="";
            Beban_KSO_Tindakan_Ralan="";
            Utang_KSO_Tindakan_Ralan="";
            Beban_Jasa_Sarana_Tindakan_Ralan="";
            Utang_Jasa_Sarana_Tindakan_Ralan="";
            HPP_BHP_Tindakan_Ralan="";
            Persediaan_BHP_Tindakan_Ralan="";
            Beban_Jasa_Menejemen_Tindakan_Ralan="";
            Utang_Jasa_Menejemen_Tindakan_Ralan="";
            Suspen_Piutang_Laborat_Ralan="";
            Laborat_Ralan="";
            Beban_Jasa_Medik_Dokter_Laborat_Ralan="";
            Utang_Jasa_Medik_Dokter_Laborat_Ralan="";
            Beban_Jasa_Medik_Petugas_Laborat_Ralan="";
            Utang_Jasa_Medik_Petugas_Laborat_Ralan="";
            Beban_Kso_Laborat_Ralan="";
            Utang_Kso_Laborat_Ralan="";
            HPP_Persediaan_Laborat_Rawat_Jalan="";
            Persediaan_BHP_Laborat_Rawat_Jalan="";
            Beban_Jasa_Sarana_Laborat_Ralan="";
            Utang_Jasa_Sarana_Laborat_Ralan="";
            Beban_Jasa_Perujuk_Laborat_Ralan="";
            Utang_Jasa_Perujuk_Laborat_Ralan="";
            Beban_Jasa_Menejemen_Laborat_Ralan="";
            Utang_Jasa_Menejemen_Laborat_Ralan="";
            Suspen_Piutang_Radiologi_Ralan="";
            Radiologi_Ralan="";
            Beban_Jasa_Medik_Dokter_Radiologi_Ralan="";
            Utang_Jasa_Medik_Dokter_Radiologi_Ralan="";
            Beban_Jasa_Medik_Petugas_Radiologi_Ralan="";
            Utang_Jasa_Medik_Petugas_Radiologi_Ralan="";
            Beban_Kso_Radiologi_Ralan="";
            Utang_Kso_Radiologi_Ralan="";
            HPP_Persediaan_Radiologi_Rawat_Jalan="";
            Persediaan_BHP_Radiologi_Rawat_Jalan="";
            Beban_Jasa_Sarana_Radiologi_Ralan="";
            Utang_Jasa_Sarana_Radiologi_Ralan="";
            Beban_Jasa_Perujuk_Radiologi_Ralan="";
            Utang_Jasa_Perujuk_Radiologi_Ralan="";
            Beban_Jasa_Menejemen_Radiologi_Ralan="";
            Utang_Jasa_Menejemen_Radiologi_Ralan="";
            Suspen_Piutang_Obat_Ralan="";
            Obat_Ralan="";
            HPP_Obat_Rawat_Jalan="";
            Persediaan_Obat_Rawat_Jalan="";
            Registrasi_Ralan="";
            Suspen_Piutang_Operasi_Ralan="";
            Operasi_Ralan="";
            Beban_Jasa_Medik_Dokter_Operasi_Ralan="";
            Utang_Jasa_Medik_Dokter_Operasi_Ralan="";
            Beban_Jasa_Medik_Paramedis_Operasi_Ralan="";
            Utang_Jasa_Medik_Paramedis_Operasi_Ralan="";
            HPP_Obat_Operasi_Ralan="";
            Persediaan_Obat_Kamar_Operasi_Ralan="";
            Tambahan_Ralan="";
            Potongan_Ralan="";
            Suspen_Piutang_Tindakan_Ranap="";
            Tindakan_Ranap="";
            Beban_Jasa_Medik_Dokter_Tindakan_Ranap="";
            Utang_Jasa_Medik_Dokter_Tindakan_Ranap="";
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap="";
            Utang_Jasa_Medik_Paramedis_Tindakan_Ranap="";
            Beban_KSO_Tindakan_Ranap="";
            Utang_KSO_Tindakan_Ranap="";
            Beban_Jasa_Sarana_Tindakan_Ranap="";
            Utang_Jasa_Sarana_Tindakan_Ranap="";
            Beban_Jasa_Menejemen_Tindakan_Ranap="";
            Utang_Jasa_Menejemen_Tindakan_Ranap="";
            HPP_BHP_Tindakan_Ranap="";
            Persediaan_BHP_Tindakan_Ranap="";
            Suspen_Piutang_Laborat_Ranap="";
            Laborat_Ranap="";
            Beban_Jasa_Medik_Dokter_Laborat_Ranap="";
            Utang_Jasa_Medik_Dokter_Laborat_Ranap="";
            Beban_Jasa_Medik_Petugas_Laborat_Ranap="";
            Utang_Jasa_Medik_Petugas_Laborat_Ranap="";
            Beban_Kso_Laborat_Ranap="";
            Utang_Kso_Laborat_Ranap="";
            HPP_Persediaan_Laborat_Rawat_inap="";
            Persediaan_BHP_Laborat_Rawat_Inap="";
            Beban_Jasa_Sarana_Laborat_Ranap="";
            Utang_Jasa_Sarana_Laborat_Ranap="";
            Beban_Jasa_Perujuk_Laborat_Ranap="";
            Utang_Jasa_Perujuk_Laborat_Ranap="";
            Beban_Jasa_Menejemen_Laborat_Ranap="";
            Utang_Jasa_Menejemen_Laborat_Ranap="";
            Suspen_Piutang_Radiologi_Ranap="";
            Radiologi_Ranap="";
            Beban_Jasa_Medik_Dokter_Radiologi_Ranap="";
            Utang_Jasa_Medik_Dokter_Radiologi_Ranap="";
            Beban_Jasa_Medik_Petugas_Radiologi_Ranap="";
            Utang_Jasa_Medik_Petugas_Radiologi_Ranap="";
            Beban_Kso_Radiologi_Ranap="";
            Utang_Kso_Radiologi_Ranap="";
            HPP_Persediaan_Radiologi_Rawat_Inap="";
            Persediaan_BHP_Radiologi_Rawat_Inap="";
            Beban_Jasa_Sarana_Radiologi_Ranap="";
            Utang_Jasa_Sarana_Radiologi_Ranap="";
            Beban_Jasa_Perujuk_Radiologi_Ranap="";
            Utang_Jasa_Perujuk_Radiologi_Ranap="";
            Beban_Jasa_Menejemen_Radiologi_Ranap="";
            Utang_Jasa_Menejemen_Radiologi_Ranap="";
            Suspen_Piutang_Obat_Ranap="";
            Obat_Ranap="";
            HPP_Obat_Rawat_Inap="";
            Persediaan_Obat_Rawat_Inap="";
            Registrasi_Ranap="";
            Service_Ranap="";
            Tambahan_Ranap="";
            Potongan_Ranap="";
            Retur_Obat_Ranap="";
            Resep_Pulang_Ranap="";
            Kamar_Inap="";
            Suspen_Piutang_Operasi_Ranap="";
            Operasi_Ranap="";
            Beban_Jasa_Medik_Dokter_Operasi_Ranap="";
            Utang_Jasa_Medik_Dokter_Operasi_Ranap="";
            Beban_Jasa_Medik_Paramedis_Operasi_Ranap="";
            Utang_Jasa_Medik_Paramedis_Operasi_Ranap="";
            HPP_Obat_Operasi_Ranap="";
            Persediaan_Obat_Kamar_Operasi_Ranap="";
            Harian_Ranap="";
            Uang_Muka_Ranap="";
            Piutang_Pasien_Ranap="";
            Pengadaan_Obat="";
            Pemesanan_Obat="";
            Kontra_Pemesanan_Obat="";
            Bayar_Pemesanan_Obat="";
            Penjualan_Obat="";
            Piutang_Obat="";
            Kontra_Piutang_Obat="";
            Retur_Ke_Suplayer="";
            Kontra_Retur_Ke_Suplayer="";
            Retur_Dari_pembeli="";
            Kontra_Retur_Dari_Pembeli="";
            Retur_Piutang_Obat="";
            Kontra_Retur_Piutang_Obat="";
            Pengadaan_Ipsrs="";
            Stok_Keluar_Ipsrs="";
            Kontra_Stok_Keluar_Ipsrs="";
            Bayar_Piutang_Pasien="";
            Pengambilan_Utd="";
            Kontra_Pengambilan_Utd="";
            Pengambilan_Penunjang_Utd="";
            Kontra_Pengambilan_Penunjang_Utd="";
            Penyerahan_Darah="";
            Stok_Keluar_Medis="";
            Kontra_Stok_Keluar_Medis="";
            HPP_Obat_Jual_Bebas="";
            Persediaan_Obat_Jual_Bebas="";
            Penerimaan_NonMedis="";
            Kontra_Penerimaan_NonMedis="";
            Bayar_Pemesanan_Non_Medis="";
            Hibah_Obat="";
            Kontra_Hibah_Obat="";
            Penerimaan_Toko="";
            Kontra_Penerimaan_Toko="";
            Pengadaan_Toko="";
            Bayar_Pemesanan_Toko="";
            Penjualan_Toko="";
            HPP_Barang_Toko="";
            Persediaan_Barang_Toko="";
            Piutang_Toko="";
            Kontra_Piutang_Toko="";
            Retur_Beli_Toko="";
            Kontra_Retur_Beli_Toko="";
            Retur_Beli_Non_Medis="";
            Kontra_Retur_Beli_Non_Medis="";
            Retur_Jual_Toko="";
            Kontra_Retur_Jual_Toko="";
            Retur_Piutang_Toko="";
            Kontra_Retur_Piutang_Toko="";
            
            ps=koneksi.prepareStatement("select * from set_akun_ralan");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    Suspen_Piutang_Tindakan_Ralan=rs.getString("Suspen_Piutang_Tindakan_Ralan");                    
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
                    Persediaan_BHP_Tindakan_Ralan=rs.getString("Persediaan_BHP_Tindakan_Ralan");                    
                    Beban_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");                    
                    Utang_Jasa_Menejemen_Tindakan_Ralan=rs.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");                    
                    Suspen_Piutang_Laborat_Ralan=rs.getString("Suspen_Piutang_Laborat_Ralan");                    
                    Laborat_Ralan=rs.getString("Laborat_Ralan");                    
                    Beban_Jasa_Medik_Dokter_Laborat_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Laborat_Ralan");                    
                    Utang_Jasa_Medik_Dokter_Laborat_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Laborat_Ralan");                    
                    Beban_Jasa_Medik_Petugas_Laborat_Ralan=rs.getString("Beban_Jasa_Medik_Petugas_Laborat_Ralan");                    
                    Utang_Jasa_Medik_Petugas_Laborat_Ralan=rs.getString("Utang_Jasa_Medik_Petugas_Laborat_Ralan");                    
                    Beban_Kso_Laborat_Ralan=rs.getString("Beban_Kso_Laborat_Ralan");                    
                    Utang_Kso_Laborat_Ralan=rs.getString("Utang_Kso_Laborat_Ralan");                    
                    HPP_Persediaan_Laborat_Rawat_Jalan=rs.getString("HPP_Persediaan_Laborat_Rawat_Jalan");                    
                    Persediaan_BHP_Laborat_Rawat_Jalan=rs.getString("Persediaan_BHP_Laborat_Rawat_Jalan");                    
                    Beban_Jasa_Sarana_Laborat_Ralan=rs.getString("Beban_Jasa_Sarana_Laborat_Ralan");                    
                    Utang_Jasa_Sarana_Laborat_Ralan=rs.getString("Utang_Jasa_Sarana_Laborat_Ralan");                    
                    Beban_Jasa_Perujuk_Laborat_Ralan=rs.getString("Beban_Jasa_Perujuk_Laborat_Ralan");                    
                    Utang_Jasa_Perujuk_Laborat_Ralan=rs.getString("Utang_Jasa_Perujuk_Laborat_Ralan");                    
                    Beban_Jasa_Menejemen_Laborat_Ralan=rs.getString("Beban_Jasa_Menejemen_Laborat_Ralan");                    
                    Utang_Jasa_Menejemen_Laborat_Ralan=rs.getString("Utang_Jasa_Menejemen_Laborat_Ralan");                    
                    Suspen_Piutang_Radiologi_Ralan=rs.getString("Suspen_Piutang_Radiologi_Ralan");                    
                    Radiologi_Ralan=rs.getString("Radiologi_Ralan");                    
                    Beban_Jasa_Medik_Dokter_Radiologi_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ralan");                    
                    Utang_Jasa_Medik_Dokter_Radiologi_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ralan");                    
                    Beban_Jasa_Medik_Petugas_Radiologi_Ralan=rs.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ralan");                    
                    Utang_Jasa_Medik_Petugas_Radiologi_Ralan=rs.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ralan");                    
                    Beban_Kso_Radiologi_Ralan=rs.getString("Beban_Kso_Radiologi_Ralan");                    
                    Utang_Kso_Radiologi_Ralan=rs.getString("Utang_Kso_Radiologi_Ralan");                    
                    HPP_Persediaan_Radiologi_Rawat_Jalan=rs.getString("HPP_Persediaan_Radiologi_Rawat_Jalan");                    
                    Persediaan_BHP_Radiologi_Rawat_Jalan=rs.getString("Persediaan_BHP_Radiologi_Rawat_Jalan");                    
                    Beban_Jasa_Sarana_Radiologi_Ralan=rs.getString("Beban_Jasa_Sarana_Radiologi_Ralan");                    
                    Utang_Jasa_Sarana_Radiologi_Ralan=rs.getString("Utang_Jasa_Sarana_Radiologi_Ralan");                    
                    Beban_Jasa_Perujuk_Radiologi_Ralan=rs.getString("Beban_Jasa_Perujuk_Radiologi_Ralan");                    
                    Utang_Jasa_Perujuk_Radiologi_Ralan=rs.getString("Utang_Jasa_Perujuk_Radiologi_Ralan");                    
                    Beban_Jasa_Menejemen_Radiologi_Ralan=rs.getString("Beban_Jasa_Menejemen_Radiologi_Ralan");                    
                    Utang_Jasa_Menejemen_Radiologi_Ralan=rs.getString("Utang_Jasa_Menejemen_Radiologi_Ralan");                    
                    Suspen_Piutang_Obat_Ralan=rs.getString("Suspen_Piutang_Obat_Ralan");                    
                    Obat_Ralan=rs.getString("Obat_Ralan");                    
                    HPP_Obat_Rawat_Jalan=rs.getString("HPP_Obat_Rawat_Jalan");                    
                    Persediaan_Obat_Rawat_Jalan=rs.getString("Persediaan_Obat_Rawat_Jalan");                    
                    Registrasi_Ralan=rs.getString("Registrasi_Ralan");                    
                    Suspen_Piutang_Operasi_Ralan=rs.getString("Suspen_Piutang_Operasi_Ralan");                    
                    Operasi_Ralan=rs.getString("Operasi_Ralan");                    
                    Beban_Jasa_Medik_Dokter_Operasi_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Operasi_Ralan");                    
                    Utang_Jasa_Medik_Dokter_Operasi_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Operasi_Ralan");                    
                    Beban_Jasa_Medik_Paramedis_Operasi_Ralan=rs.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ralan");                    
                    Utang_Jasa_Medik_Paramedis_Operasi_Ralan=rs.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ralan");                    
                    HPP_Obat_Operasi_Ralan=rs.getString("HPP_Obat_Operasi_Ralan");                    
                    Persediaan_Obat_Kamar_Operasi_Ralan=rs.getString("Persediaan_Obat_Kamar_Operasi_Ralan");                    
                    Tambahan_Ralan=rs.getString("Tambahan_Ralan");                    
                    Potongan_Ralan=rs.getString("Potongan_Ralan"); 
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
            
            ps=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rs=ps.executeQuery();
                if(rs.next()){                    
                    Suspen_Piutang_Tindakan_Ranap=rs.getString("Suspen_Piutang_Tindakan_Ranap");                    
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
                    Persediaan_BHP_Tindakan_Ranap=rs.getString("Persediaan_BHP_Tindakan_Ranap");                    
                    Suspen_Piutang_Laborat_Ranap=rs.getString("Suspen_Piutang_Laborat_Ranap");                    
                    Laborat_Ranap=rs.getString("Laborat_Ranap");                    
                    Beban_Jasa_Medik_Dokter_Laborat_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Laborat_Ranap");                    
                    Utang_Jasa_Medik_Dokter_Laborat_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Laborat_Ranap");                    
                    Beban_Jasa_Medik_Petugas_Laborat_Ranap=rs.getString("Beban_Jasa_Medik_Petugas_Laborat_Ranap");                    
                    Utang_Jasa_Medik_Petugas_Laborat_Ranap=rs.getString("Utang_Jasa_Medik_Petugas_Laborat_Ranap");                    
                    Beban_Kso_Laborat_Ranap=rs.getString("Beban_Kso_Laborat_Ranap");                    
                    Utang_Kso_Laborat_Ranap=rs.getString("Utang_Kso_Laborat_Ranap");                    
                    HPP_Persediaan_Laborat_Rawat_inap=rs.getString("HPP_Persediaan_Laborat_Rawat_inap");                    
                    Persediaan_BHP_Laborat_Rawat_Inap=rs.getString("Persediaan_BHP_Laborat_Rawat_Inap");                    
                    Beban_Jasa_Sarana_Laborat_Ranap=rs.getString("Beban_Jasa_Sarana_Laborat_Ranap");                    
                    Utang_Jasa_Sarana_Laborat_Ranap=rs.getString("Utang_Jasa_Sarana_Laborat_Ranap");                    
                    Beban_Jasa_Perujuk_Laborat_Ranap=rs.getString("Beban_Jasa_Perujuk_Laborat_Ranap");                    
                    Utang_Jasa_Perujuk_Laborat_Ranap=rs.getString("Utang_Jasa_Perujuk_Laborat_Ranap");                    
                    Beban_Jasa_Menejemen_Laborat_Ranap=rs.getString("Beban_Jasa_Menejemen_Laborat_Ranap");                    
                    Utang_Jasa_Menejemen_Laborat_Ranap=rs.getString("Utang_Jasa_Menejemen_Laborat_Ranap");                    
                    Suspen_Piutang_Radiologi_Ranap=rs.getString("Suspen_Piutang_Radiologi_Ranap");                    
                    Radiologi_Ranap=rs.getString("Radiologi_Ranap");                    
                    Beban_Jasa_Medik_Dokter_Radiologi_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ranap");                    
                    Utang_Jasa_Medik_Dokter_Radiologi_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ranap");                    
                    Beban_Jasa_Medik_Petugas_Radiologi_Ranap=rs.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ranap");                    
                    Utang_Jasa_Medik_Petugas_Radiologi_Ranap=rs.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ranap");                    
                    Beban_Kso_Radiologi_Ranap=rs.getString("Beban_Kso_Radiologi_Ranap");                    
                    Utang_Kso_Radiologi_Ranap=rs.getString("Utang_Kso_Radiologi_Ranap");                    
                    HPP_Persediaan_Radiologi_Rawat_Inap=rs.getString("HPP_Persediaan_Radiologi_Rawat_Inap");                    
                    Persediaan_BHP_Radiologi_Rawat_Inap=rs.getString("Persediaan_BHP_Radiologi_Rawat_Inap");                    
                    Beban_Jasa_Sarana_Radiologi_Ranap=rs.getString("Beban_Jasa_Sarana_Radiologi_Ranap");                    
                    Utang_Jasa_Sarana_Radiologi_Ranap=rs.getString("Utang_Jasa_Sarana_Radiologi_Ranap");                    
                    Beban_Jasa_Perujuk_Radiologi_Ranap=rs.getString("Beban_Jasa_Perujuk_Radiologi_Ranap");                    
                    Utang_Jasa_Perujuk_Radiologi_Ranap=rs.getString("Utang_Jasa_Perujuk_Radiologi_Ranap");                    
                    Beban_Jasa_Menejemen_Radiologi_Ranap=rs.getString("Beban_Jasa_Menejemen_Radiologi_Ranap");                    
                    Utang_Jasa_Menejemen_Radiologi_Ranap=rs.getString("Utang_Jasa_Menejemen_Radiologi_Ranap");                    
                    Suspen_Piutang_Obat_Ranap=rs.getString("Suspen_Piutang_Obat_Ranap");                    
                    Obat_Ranap=rs.getString("Obat_Ranap");                    
                    HPP_Obat_Rawat_Inap=rs.getString("HPP_Obat_Rawat_Inap");                    
                    Persediaan_Obat_Rawat_Inap=rs.getString("Persediaan_Obat_Rawat_Inap");                    
                    Registrasi_Ranap=rs.getString("Registrasi_Ranap");                    
                    Service_Ranap=rs.getString("Service_Ranap");                    
                    Tambahan_Ranap=rs.getString("Tambahan_Ranap");                    
                    Potongan_Ranap=rs.getString("Potongan_Ranap");                    
                    Retur_Obat_Ranap=rs.getString("Retur_Obat_Ranap");                    
                    Resep_Pulang_Ranap=rs.getString("Resep_Pulang_Ranap");                    
                    Kamar_Inap=rs.getString("Kamar_Inap");                    
                    Suspen_Piutang_Operasi_Ranap=rs.getString("Suspen_Piutang_Operasi_Ranap");                    
                    Operasi_Ranap=rs.getString("Operasi_Ranap");                    
                    Beban_Jasa_Medik_Dokter_Operasi_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Operasi_Ranap");                    
                    Utang_Jasa_Medik_Dokter_Operasi_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Operasi_Ranap");                    
                    Beban_Jasa_Medik_Paramedis_Operasi_Ranap=rs.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ranap");                    
                    Utang_Jasa_Medik_Paramedis_Operasi_Ranap=rs.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ranap");                    
                    HPP_Obat_Operasi_Ranap=rs.getString("HPP_Obat_Operasi_Ranap");       
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
            
            ps=koneksi.prepareStatement("select * from set_akun_ranap2");
            try {
                rs=ps.executeQuery();
                if(rs.next()){                    
                    Persediaan_Obat_Kamar_Operasi_Ranap=rs.getString("Persediaan_Obat_Kamar_Operasi_Ranap");                    
                    Harian_Ranap=rs.getString("Harian_Ranap");                    
                    Uang_Muka_Ranap=rs.getString("Uang_Muka_Ranap");                    
                    Piutang_Pasien_Ranap=rs.getString("Piutang_Pasien_Ranap");  
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
            
            ps=koneksi.prepareStatement("select * from set_akun");
            try {
                rs=ps.executeQuery();
                if(rs.next()){                    
                    Pengadaan_Obat=rs.getString("Pengadaan_Obat");
                    Pemesanan_Obat=rs.getString("Pemesanan_Obat");
                    Kontra_Pemesanan_Obat=rs.getString("Kontra_Pemesanan_Obat");
                    Bayar_Pemesanan_Obat=rs.getString("Bayar_Pemesanan_Obat");
                    Penjualan_Obat=rs.getString("Penjualan_Obat");
                    Piutang_Obat=rs.getString("Piutang_Obat");
                    Kontra_Piutang_Obat=rs.getString("Kontra_Piutang_Obat");
                    Retur_Ke_Suplayer=rs.getString("Retur_Ke_Suplayer");
                    Kontra_Retur_Ke_Suplayer=rs.getString("Kontra_Retur_Ke_Suplayer");
                    Retur_Dari_pembeli=rs.getString("Retur_Dari_pembeli");
                    Kontra_Retur_Dari_Pembeli=rs.getString("Kontra_Retur_Dari_Pembeli");
                    Retur_Piutang_Obat=rs.getString("Retur_Piutang_Obat");
                    Kontra_Retur_Piutang_Obat=rs.getString("Kontra_Retur_Piutang_Obat");
                    Pengadaan_Ipsrs=rs.getString("Pengadaan_Ipsrs");
                    Stok_Keluar_Ipsrs=rs.getString("Stok_Keluar_Ipsrs");
                    Kontra_Stok_Keluar_Ipsrs=rs.getString("Kontra_Stok_Keluar_Ipsrs");
                    Bayar_Piutang_Pasien=rs.getString("Bayar_Piutang_Pasien");
                    Pengambilan_Utd=rs.getString("Pengambilan_Utd");
                    Kontra_Pengambilan_Utd=rs.getString("Kontra_Pengambilan_Utd");
                    Pengambilan_Penunjang_Utd=rs.getString("Pengambilan_Penunjang_Utd");
                    Kontra_Pengambilan_Penunjang_Utd=rs.getString("Kontra_Pengambilan_Penunjang_Utd");
                    Penyerahan_Darah=rs.getString("Penyerahan_Darah");
                    Stok_Keluar_Medis=rs.getString("Stok_Keluar_Medis");
                    Kontra_Stok_Keluar_Medis=rs.getString("Kontra_Stok_Keluar_Medis");
                    HPP_Obat_Jual_Bebas=rs.getString("HPP_Obat_Jual_Bebas");
                    Persediaan_Obat_Jual_Bebas=rs.getString("Persediaan_Obat_Jual_Bebas");
                    Penerimaan_NonMedis=rs.getString("Penerimaan_NonMedis");
                    Kontra_Penerimaan_NonMedis=rs.getString("Kontra_Penerimaan_NonMedis");
                    Bayar_Pemesanan_Non_Medis=rs.getString("Bayar_Pemesanan_Non_Medis");
                    Hibah_Obat=rs.getString("Hibah_Obat");
                    Kontra_Hibah_Obat=rs.getString("Kontra_Hibah_Obat");
                    Penerimaan_Toko=rs.getString("Penerimaan_Toko");
                    Kontra_Penerimaan_Toko=rs.getString("Kontra_Penerimaan_Toko");
                    Pengadaan_Toko=rs.getString("Pengadaan_Toko");
                    Bayar_Pemesanan_Toko=rs.getString("Bayar_Pemesanan_Toko");
                    Penjualan_Toko=rs.getString("Penjualan_Toko");
                    HPP_Barang_Toko=rs.getString("HPP_Barang_Toko");
                    Persediaan_Barang_Toko=rs.getString("Persediaan_Barang_Toko");
                    Piutang_Toko=rs.getString("Piutang_Toko");
                    Kontra_Piutang_Toko=rs.getString("Kontra_Piutang_Toko");
                    Retur_Beli_Toko=rs.getString("Retur_Beli_Toko");
                    Kontra_Retur_Beli_Toko=rs.getString("Kontra_Retur_Beli_Toko");
                    Retur_Beli_Non_Medis=rs.getString("Retur_Beli_Non_Medis");
                    Kontra_Retur_Beli_Non_Medis=rs.getString("Kontra_Retur_Beli_Non_Medis");
                    Retur_Jual_Toko=rs.getString("Retur_Jual_Toko");
                    Kontra_Retur_Jual_Toko=rs.getString("Kontra_Retur_Jual_Toko");
                    Retur_Piutang_Toko=rs.getString("Retur_Piutang_Toko");
                    Kontra_Retur_Piutang_Toko=rs.getString("Kontra_Retur_Piutang_Toko");
                }               
            } catch (Exception e) {
                System.out.println("Notif Set Akun :"+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }    
            
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Tindakan Rawat Jalan",Suspen_Piutang_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Tindakan pada menu Billing Rawat Jalan",Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Tindakan Rawat Jalan",Beban_Jasa_Medik_Dokter_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Tindakan Rawat Jalan",Utang_Jasa_Medik_Dokter_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Paramedis Tindakan Rawat Jalan",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Paramedis Tindakan Rawat Jalan",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Tindakan Rawat Jalan",Beban_KSO_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_KSO_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_KSO_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_KSO_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Tindakan Rawat Jalan",Utang_KSO_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_KSO_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_KSO_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_KSO_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Sarana Tindakan Rawat Jalan",Beban_Jasa_Sarana_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Sarana_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Sarana_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Sarana_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Sarana Tindakan Rawat Jalan",Utang_Jasa_Sarana_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Sarana_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Sarana_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Sarana_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP BHP Tindakan Rawat Jalan",HPP_BHP_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_BHP_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_BHP_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_BHP_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Tindakan Rawat Jalan",Persediaan_BHP_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Menejemen Tindakan Rawat Jalan",Beban_Jasa_Menejemen_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Menejemen_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Menejemen_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Menejemen_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Menejemen Tindakan Rawat Jalan",Utang_Jasa_Menejemen_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Menejemen_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Menejemen_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Menejemen_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Laborat Ralan",Suspen_Piutang_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Laborat pada menu Billing Rawat Jalan",Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Laborat Rawat Jalan",Beban_Jasa_Medik_Dokter_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Laborat Rawat Jalan",Utang_Jasa_Medik_Dokter_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Petugas Laborat Rawat Jalan",Beban_Jasa_Medik_Petugas_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Petugas Laborat Rawat Jalan",Utang_Jasa_Medik_Petugas_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Laborat Rawat Jalan",Beban_Kso_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Kso_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Kso_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Kso_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Laborat Rawat Jalan",Utang_Kso_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Kso_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Kso_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Kso_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP BHP Laborat Rawat Jalan",HPP_Persediaan_Laborat_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Laborat Rawat Jalan",Persediaan_BHP_Laborat_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Sarana Laborat Rawat Jalan",Beban_Jasa_Sarana_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Sarana_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Sarana_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Sarana_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Sarana Laborat Rawat Jalan",Utang_Jasa_Sarana_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Sarana_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Sarana_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Sarana_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Perujuk Laborat Rawat Jalan",Beban_Jasa_Perujuk_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Perujuk_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Perujuk_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Perujuk_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Perujuk Laborat Rawat Jalan",Utang_Jasa_Perujuk_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Perujuk_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Perujuk_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Perujuk_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Menejemen Laborat Rawat Jalan",Beban_Jasa_Menejemen_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Menejemen_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Menejemen_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Menejemen_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Menejemen Laborat Rawat Jalan",Utang_Jasa_Menejemen_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Menejemen_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Menejemen_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Menejemen_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Radiologi Rawat Jalan",Suspen_Piutang_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Radiologi pada menu Billing Rawat Jalan",Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Radiologi Rawat Jalan",Beban_Jasa_Medik_Dokter_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Radiologi Rawat Jalan",Utang_Jasa_Medik_Dokter_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Petugas Radiologi Rawat Jalan",Beban_Jasa_Medik_Petugas_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Petugas Radiologi Rawat Jalan",Utang_Jasa_Medik_Petugas_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Radiologi Rawat Jalan",Beban_Kso_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Kso_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Kso_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Kso_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Radiologi Rawat Jalan",Utang_Kso_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Kso_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Kso_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Kso_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP BHP Radiologi Rawat Jalan",HPP_Persediaan_Radiologi_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Radiologi Rawat Jalan",Persediaan_BHP_Radiologi_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Sarana Radiologi Rawat Jalan",Beban_Jasa_Sarana_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Sarana_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Sarana_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Sarana_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Sarana Radiologi Rawat Jalan",Utang_Jasa_Sarana_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Sarana_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Sarana_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Sarana_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Perujuk Radiologi Rawat Jalan",Beban_Jasa_Perujuk_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Perujuk_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Perujuk_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Perujuk_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Perujuk Radiologi Rawat Jalan",Utang_Jasa_Perujuk_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Perujuk_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Perujuk_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Perujuk_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Menejemen Radiologi Rawat Jalan",Beban_Jasa_Menejemen_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Menejemen_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Menejemen_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Menejemen_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Menejemen Radiologi Rawat Jalan",Utang_Jasa_Menejemen_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Menejemen_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Menejemen_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Menejemen_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Obat Rawat Jalan",Suspen_Piutang_Obat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Obat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Obat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Obat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Obat pada menu Billing Rawat Jalan",Obat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Obat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Obat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Obat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Rawat Jalan",HPP_Obat_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Rawat Jalan",Persediaan_Obat_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Rawat_Jalan)
            });            
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Registrasi pada menu Billing Rawat Jalan",Registrasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Registrasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Registrasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Registrasi_Ralan)
            });   
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Operasi Rawat Jalan",Suspen_Piutang_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Operasi_Ralan)
            });   
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Operasi pada menu Billing Rawat Jalan",Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Operasi_Ralan)
            });            
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Operasi Ralan",Beban_Jasa_Medik_Dokter_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Operasi Ralan",Utang_Jasa_Medik_Dokter_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ralan)
            });             
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Paramedis Operasi Ralan",Beban_Jasa_Medik_Paramedis_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Paramedis Operasi Ralan",Utang_Jasa_Medik_Paramedis_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ralan)
            });             
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Operasi Ralan",HPP_Obat_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Operasi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Kamar Operasi Ralan",Persediaan_Obat_Kamar_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ralan)
            }); 
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Tambahan Biaya pada menu Billing Rawat Jalan",Tambahan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tambahan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tambahan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tambahan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Potongan Biaya pada Billing menu Rawat Jalan",Potongan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Potongan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Potongan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Potongan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Tindakan Rawat Inap",Suspen_Piutang_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Tindakan Rawat Inap",Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Tindakan Ranap",Beban_Jasa_Medik_Dokter_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Tindakan Ranap",Utang_Jasa_Medik_Dokter_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Paramedis Tindakan Ranap",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Paramedis Tindakan Ranap",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Tindakan Ranap",Beban_KSO_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_KSO_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_KSO_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_KSO_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Tindakan Ranap",Utang_KSO_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_KSO_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_KSO_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_KSO_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Sarana Tindakan Ranap",Beban_Jasa_Sarana_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Sarana_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Sarana_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Sarana_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Sarana Tindakan Ranap",Utang_Jasa_Sarana_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Sarana_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Sarana_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Sarana_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Menejemen Tindakan Ranap",Beban_Jasa_Menejemen_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Menejemen_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Menejemen_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Menejemen_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Menejemen Tindakan Ranap",Utang_Jasa_Menejemen_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Menejemen_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Menejemen_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Menejemen_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP BHP Tindakan Ranap",HPP_BHP_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_BHP_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_BHP_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_BHP_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Tindakan Ranap",Persediaan_BHP_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Laborat Ranap",Suspen_Piutang_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Laborat_Ranap)
            });            
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Laborat Rawat Inap",Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Laborat_Ranap)
            });            
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Laborat Ranap",Beban_Jasa_Medik_Dokter_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Laborat Ranap",Utang_Jasa_Medik_Dokter_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Petugas Laborat Ranap",Beban_Jasa_Medik_Petugas_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Petugas Laborat Ranap",Utang_Jasa_Medik_Petugas_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Laborat Ranap",Beban_Kso_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Kso_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Kso_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Kso_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Laborat Ranap",Utang_Kso_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Kso_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Kso_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Kso_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Persediaan Laborat Rawat Inap",HPP_Persediaan_Laborat_Rawat_inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_inap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Laborat Rawat Inap",Persediaan_BHP_Laborat_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Inap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Sarana Laborat Ranap",Beban_Jasa_Sarana_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Sarana_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Sarana_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Sarana_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Sarana Laborat Ranap",Utang_Jasa_Sarana_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Sarana_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Sarana_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Sarana_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Perujuk Laborat Ranap",Beban_Jasa_Perujuk_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Perujuk_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Perujuk_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Perujuk_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Perujuk Laborat Ranap",Utang_Jasa_Perujuk_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Perujuk_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Perujuk_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Perujuk_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Menejemen Laborat Ranap",Beban_Jasa_Menejemen_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Menejemen_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Menejemen_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Menejemen_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Menejemen Laborat Ranap",Utang_Jasa_Menejemen_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Menejemen_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Menejemen_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Menejemen_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Radiologi Ranap",Suspen_Piutang_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Radiologi Rawat Inap",Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Radiologi Ranap",Beban_Jasa_Medik_Dokter_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Radiologi Ranap",Utang_Jasa_Medik_Dokter_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Petugas Radiologi Ranap",Beban_Jasa_Medik_Petugas_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Petugas Radiologi Ranap",Utang_Jasa_Medik_Petugas_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Radiologi Ranap",Beban_Kso_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Kso_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Kso_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Kso_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Radiologi Ranap",Utang_Kso_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Kso_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Kso_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Kso_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Persediaan Radiologi Rawat Inap",HPP_Persediaan_Radiologi_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Inap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Radiologi Rawat Inap",Persediaan_BHP_Radiologi_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Inap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Sarana Radiologi Ranap",Beban_Jasa_Sarana_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Sarana_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Sarana_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Sarana_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Sarana Radiologi Ranap",Utang_Jasa_Sarana_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Sarana_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Sarana_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Sarana_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Perujuk Radiologi Ranap",Beban_Jasa_Perujuk_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Perujuk_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Perujuk_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Perujuk_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Perujuk Radiologi Ranap",Utang_Jasa_Perujuk_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Perujuk_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Perujuk_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Perujuk_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Menejemen Radiologi Ranap",Beban_Jasa_Menejemen_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Menejemen_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Menejemen_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Menejemen_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Menejemen Radiologi Ranap",Utang_Jasa_Menejemen_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Menejemen_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Menejemen_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Menejemen_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Obat Ranap",Suspen_Piutang_Obat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Obat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Obat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Obat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Obat pada menu Billing Rawat Inap",Obat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Obat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Obat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Obat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Rawat Inap",HPP_Obat_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Rawat_Inap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Rawat Inap",Persediaan_Obat_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Rawat_Inap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Registrasi pada menu Billing Rawat Inap",Registrasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Registrasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Registrasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Registrasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Biaya Service pada menu Billing Rawat Inap",Service_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Service_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Service_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Service_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Tambahan Biaya pada menu Billing Rawat Inap",Tambahan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tambahan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tambahan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tambahan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Potongan Biaya pada menu Billing Rawat Inap",Potongan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Potongan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Potongan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Potongan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Retur Obat pada menu Billing Rawat Inap",Retur_Obat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Obat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Obat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Obat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Resep Pulang pada menu Billing Rawat Inap",Resep_Pulang_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Resep_Pulang_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Resep_Pulang_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Resep_Pulang_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Kamar Inap pada menu Billing Rawat Inap",Kamar_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kamar_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kamar_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kamar_Inap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Operasi Ranap",Suspen_Piutang_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Operasi Rawat Inap",Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Operasi Ranap",Beban_Jasa_Medik_Dokter_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Operasi Ranap",Utang_Jasa_Medik_Dokter_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Paramedis Operasi Ranap",Beban_Jasa_Medik_Paramedis_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Paramedis Operasi Ranap",Utang_Jasa_Medik_Paramedis_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Operasi Ranap",HPP_Obat_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Kamar Operasi Ranap",Persediaan_Obat_Kamar_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Harian Ranap pada menu Billing Rawat Inap",Harian_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Harian_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Harian_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Harian_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Uang Muka Pasien pada Billing menu Rawat Inap",Uang_Muka_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Uang_Muka_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Uang_Muka_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Uang_Muka_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Piutang Pasien pada Billing menu Rawat Inap",Piutang_Pasien_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Piutang_Pasien_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Piutang_Pasien_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Piutang_Pasien_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Pengadaan Obat & BHP pada menu Pengadaan Obat & BHP",Pengadaan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengadaan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengadaan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengadaan_Obat)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Penerimaan Obat & BHP pada menu Penerimaan Obat & BHP",Pemesanan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pemesanan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pemesanan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pemesanan_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Penerimaan Obat & BHP pada menu Penerimaan Obat & BHP",Kontra_Pemesanan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Pemesanan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Pemesanan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Pemesanan_Obat)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Bayar Pemesanan Obat/BHP pada menu Bayar Pesan Obat/BHP",Bayar_Pemesanan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Bayar_Pemesanan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Bayar_Pemesanan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Bayar_Pemesanan_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Penjualan Obat & BHP pada menu Penjualan Obat & BHP",Penjualan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Penjualan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Penjualan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Penjualan_Obat)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Piutang Obat & BHP pada menu Piutang Obat & BHP",Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Piutang Obat & BHP pada menu Piutang Obat & BHP",Kontra_Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Retur Obat & BHP ke Suplier pada menu Retur Ke Suplier",Retur_Ke_Suplayer,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Ke_Suplayer),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Ke_Suplayer),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Ke_Suplayer)
            });
            tabMode.addRow(new Object[]{" [Debet] Kontra Akun Retur Obat & BHP ke Suplier pada menu Retur Ke Suplier",Kontra_Retur_Ke_Suplayer,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Ke_Suplayer),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Ke_Suplayer),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Ke_Suplayer)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Retur Obat & BHP dari Pasien/Pembeli pada menu Retur Dari Pembeli",Retur_Dari_pembeli,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Dari_pembeli),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Dari_pembeli),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Dari_pembeli)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Retur Obat & BHP dari Pasien/Pembeli pada menu Retur Dari Pembeli",Kontra_Retur_Dari_Pembeli,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Dari_Pembeli),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Dari_Pembeli),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Dari_Pembeli)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Retur Piutang Obat & BHP pada menu Retur Piutang Pembeli",Retur_Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Retur Piutang Obat & BHP pada menu Retur Piutang Pembeli",Kontra_Retur_Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Pengadaan Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Pengadaan Barang",Pengadaan_Ipsrs,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengadaan_Ipsrs),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengadaan_Ipsrs),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengadaan_Ipsrs)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Stok Keluar Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Stok Keluar",Stok_Keluar_Ipsrs,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Stok_Keluar_Ipsrs)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Stok Keluar Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Stok Keluar",Kontra_Stok_Keluar_Ipsrs,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Stok_Keluar_Ipsrs)
            });        
            tabMode.addRow(new Object[]{" [Kredit] Akun Pembayaran Piutang Pasien pada menu Piutang Pasien",Bayar_Piutang_Pasien,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Bayar_Piutang_Pasien),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Bayar_Piutang_Pasien),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Bayar_Piutang_Pasien)
            });  
            tabMode.addRow(new Object[]{" [Debet] Akun Pengambilan BHP Medis UTD pada menu Pengambilan BHP UTD",Pengambilan_Utd,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengambilan_Utd),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengambilan_Utd),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengambilan_Utd)
            });  
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Pengambilan BHP Medis UTD pada menu Pengambilan BHP UTD",Kontra_Pengambilan_Utd,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Pengambilan_Utd),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Pengambilan_Utd),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Pengambilan_Utd)
            });  
            tabMode.addRow(new Object[]{" [Debet] Akun Pengambilan Barang Penunjang/Non Medis UTD pada menu Pengambilan Non Medis UTD",Pengambilan_Penunjang_Utd,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengambilan_Penunjang_Utd),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengambilan_Penunjang_Utd),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengambilan_Penunjang_Utd)
            });  
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Pengambilan Barang Penunjang/Non Medis UTD pada menu Pengambilan Non Medis UTD",Kontra_Pengambilan_Penunjang_Utd,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Pengambilan_Penunjang_Utd),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Pengambilan_Penunjang_Utd),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Pengambilan_Penunjang_Utd)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Penjualan Darah pada menu Penyerahan Darah",Penyerahan_Darah,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Penyerahan_Darah),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Penyerahan_Darah),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Penyerahan_Darah)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Stok Keluar Barang Medis (Obat, Alkes & BHP) pada menu Stok Keluar Medis",Stok_Keluar_Medis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Stok_Keluar_Medis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Stok_Keluar_Medis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Stok_Keluar_Medis)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Stok Keluar Barang Medis (Obat, Alkes & BHP) pada menu Stok Keluar Medis",Kontra_Stok_Keluar_Medis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Stok_Keluar_Medis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Stok_Keluar_Medis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Stok_Keluar_Medis)
            }); 
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Jual Bebas pada menu Penjualan Obat Bebas",HPP_Obat_Jual_Bebas,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Jual_Bebas),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Jual_Bebas),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Jual_Bebas)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Jual Bebas pada menu Penjualan Obat Bebas",Persediaan_Obat_Jual_Bebas,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Jual_Bebas),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Jual_Bebas),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Jual_Bebas)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Penerimaan Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Penerimaan Barang Non Medis",Penerimaan_NonMedis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Penerimaan_NonMedis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Penerimaan_NonMedis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Penerimaan_NonMedis)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Penerimaan Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Penerimaan Barang Non Medis",Kontra_Penerimaan_NonMedis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Penerimaan_NonMedis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Penerimaan_NonMedis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Penerimaan_NonMedis)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Bayar Pemesanan Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Bayar Pesan Non Medis",Bayar_Pemesanan_Non_Medis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Bayar_Pemesanan_Non_Medis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Bayar_Pemesanan_Non_Medis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Bayar_Pemesanan_Non_Medis)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Hibah Obat & BHP pada menu Hibah Obat & BHP",Hibah_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Hibah_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Hibah_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Hibah_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Hibah Obat & BHP pada menu Hibah Obat & BHP",Kontra_Hibah_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Hibah_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Hibah_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Hibah_Obat)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Penerimaan Barang Toko pada menu Penerimaan Barang Toko",Penerimaan_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Penerimaan_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Penerimaan_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Penerimaan_Toko)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Penerimaan Barang Toko pada menu Penerimaan Barang Toko",Kontra_Penerimaan_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Penerimaan_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Penerimaan_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Penerimaan_Toko)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Pengadaan Barang Toko pada menu Pengadaan Barang Toko",Pengadaan_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengadaan_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengadaan_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengadaan_Toko)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Bayar Pemesanan Barang Toko pada menu Bayar Pesan Toko",Bayar_Pemesanan_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Bayar_Pemesanan_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Bayar_Pemesanan_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Bayar_Pemesanan_Toko)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Penjualan Toko pada menu Penjualan Toko",Penjualan_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Penjualan_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Penjualan_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Penjualan_Toko)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Barang Toko pada menu Penjualan Toko",HPP_Barang_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Barang_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Barang_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Barang_Toko)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Barang Toko pada menu Penjualan Toko",Persediaan_Barang_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Barang_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Barang_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Barang_Toko)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Piutang Toko pada menu Piutang Toko",Piutang_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Piutang_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Piutang_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Piutang_Toko)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Piutang Toko pada menu Piutang Toko",Kontra_Piutang_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Piutang_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Piutang_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Piutang_Toko)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Retur Barang Toko ke Suplier pada menu Retur Ke Suplier Toko",Retur_Beli_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Beli_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Beli_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Beli_Toko)
            });
            tabMode.addRow(new Object[]{" [Debet] Kontra Akun Retur Barang Toko ke Suplier pada menu Retur Ke Suplier Toko",Kontra_Retur_Beli_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Beli_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Beli_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Beli_Toko)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Retur Barang Non Medis dan Penunjang ( Lab & RO ) Ke Suplier pada menu Retur Ke Suplier Non Medis",Retur_Beli_Non_Medis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Beli_Non_Medis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Beli_Non_Medis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Beli_Non_Medis)
            });
            tabMode.addRow(new Object[]{" [Debet] Kontra Akun Retur Barang Non Medis dan Penunjang ( Lab & RO ) ke Suplier pada menu Retur Ke Suplier Non Medis",Kontra_Retur_Beli_Non_Medis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Beli_Non_Medis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Beli_Non_Medis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Beli_Non_Medis)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Retur Jual Toko pada menu Retur Jual Toko",Retur_Jual_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Jual_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Jual_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Jual_Toko)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Retur Jual Toko pada menu Retur Jual Toko",Kontra_Retur_Jual_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Jual_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Jual_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Jual_Toko)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Retur Piutang Toko pada menu Retur Piutang Toko",Retur_Piutang_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Piutang_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Piutang_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Piutang_Toko)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Retur Piutang Toko pada menu Retur Piutang Toko",Kontra_Retur_Piutang_Toko,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Piutang_Toko),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Piutang_Toko),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Piutang_Toko)
            });
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    public void isCek(){
        BtnSimpan.setEnabled(akses.getpengaturan_rekening());
    }

    private void tampilralan() {
        Valid.tabelKosong(tabModeRalan);
        try{    
            ps=koneksi.prepareStatement(
               "select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,penjab.png_jawab,poliklinik.nm_poli "+
               "from jns_perawatan inner join kategori_perawatan on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori "+
               "inner join penjab on penjab.kd_pj=jns_perawatan.kd_pj inner join poliklinik on poliklinik.kd_poli=jns_perawatan.kd_poli "+
              "where jns_perawatan.status='1' order by jns_perawatan.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    kode_pendapatan_tindakan="";nama_pendapatan_tindakan="";kode_beban_jasa_dokter="";nama_beban_jasa_dokter="";kode_utang_jasa_dokter="";
                    nama_utang_jasa_dokter="";kode_beban_jasa_paramedis="";nama_beban_jasa_paramedis="";kode_utang_jasa_paramedis="";nama_utang_jasa_paramedis=""; 
                    kode_beban_kso="";nama_beban_kso="";kode_utang_kso="";nama_utang_kso="";kode_hpp_persediaan="";nama_hpp_persediaan="";kode_persediaan_bhp="";
                    nama_persediaan_bhp="";kode_beban_jasa_sarana="";nama_beban_jasa_sarana="";kode_utang_jasa_sarana="";nama_utang_jasa_sarana="";
                    kode_beban_menejemen="";nama_beban_menejemen="";kode_utang_menejemen="";nama_utang_menejemen="";
                    ps2=koneksi.prepareStatement(
                        "select matrik_akun_jns_perawatan.pendapatan_tindakan,pendapatantindakan.nm_rek as nama_pendapatan_tindakan, "+
                        "matrik_akun_jns_perawatan.beban_jasa_dokter,bebanjasadokter.nm_rek as nama_beban_jasa_dokter,"+
                        "matrik_akun_jns_perawatan.utang_jasa_dokter,utangjasadokter.nm_rek as nama_utang_jasa_dokter,"+
                        "matrik_akun_jns_perawatan.beban_jasa_paramedis,bebanjasaparamedis.nm_rek as nama_beban_jasa_paramedis,"+
                        "matrik_akun_jns_perawatan.utang_jasa_paramedis,utangjasaparamedis.nm_rek as nama_utang_jasa_paramedis,"+
                        "matrik_akun_jns_perawatan.beban_kso,bebankso.nm_rek as nama_beban_kso,"+
                        "matrik_akun_jns_perawatan.utang_kso,utangkso.nm_rek as nama_utang_kso,"+
                        "matrik_akun_jns_perawatan.hpp_persediaan,hpppersediaan.nm_rek as nama_hpp_persediaan,"+
                        "matrik_akun_jns_perawatan.persediaan_bhp,persediaanbhp.nm_rek as nama_persediaan_bhp,"+
                        "matrik_akun_jns_perawatan.beban_jasa_sarana,bebanjasasarana.nm_rek as nama_beban_jasa_sarana,"+
                        "matrik_akun_jns_perawatan.utang_jasa_sarana,utangjasasarana.nm_rek as nama_utang_jasa_sarana,"+
                        "matrik_akun_jns_perawatan.beban_menejemen,bebanmenejemen.nm_rek as nama_beban_menejemen,"+
                        "matrik_akun_jns_perawatan.utang_menejemen,utangmenejemen.nm_rek as nama_utang_menejemen "+
                        "from matrik_akun_jns_perawatan inner join rekening as pendapatantindakan on matrik_akun_jns_perawatan.pendapatan_tindakan=pendapatantindakan.kd_rek "+
                        "inner join rekening as bebanjasadokter on matrik_akun_jns_perawatan.beban_jasa_dokter=bebanjasadokter.kd_rek "+
                        "inner join rekening as utangjasadokter on matrik_akun_jns_perawatan.utang_jasa_dokter=utangjasadokter.kd_rek "+
                        "inner join rekening as bebanjasaparamedis on matrik_akun_jns_perawatan.beban_jasa_paramedis=bebanjasaparamedis.kd_rek "+
                        "inner join rekening as utangjasaparamedis on matrik_akun_jns_perawatan.utang_jasa_paramedis=utangjasaparamedis.kd_rek "+
                        "inner join rekening as bebankso on matrik_akun_jns_perawatan.beban_kso=bebankso.kd_rek "+
                        "inner join rekening as utangkso on matrik_akun_jns_perawatan.utang_kso=utangkso.kd_rek "+
                        "inner join rekening as hpppersediaan on matrik_akun_jns_perawatan.hpp_persediaan=hpppersediaan.kd_rek "+
                        "inner join rekening as persediaanbhp on matrik_akun_jns_perawatan.persediaan_bhp=persediaanbhp.kd_rek "+
                        "inner join rekening as bebanjasasarana on matrik_akun_jns_perawatan.beban_jasa_sarana=bebanjasasarana.kd_rek "+
                        "inner join rekening as utangjasasarana on matrik_akun_jns_perawatan.utang_jasa_sarana=utangjasasarana.kd_rek "+
                        "inner join rekening as bebanmenejemen on matrik_akun_jns_perawatan.beban_menejemen=bebanmenejemen.kd_rek "+
                        "inner join rekening as utangmenejemen on matrik_akun_jns_perawatan.utang_menejemen=utangmenejemen.kd_rek "+
                        "where matrik_akun_jns_perawatan.kd_jenis_prw=?");
                    try {
                        ps2.setString(1,rs.getString("kd_jenis_prw"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            kode_pendapatan_tindakan=rs2.getString("pendapatan_tindakan");
                            nama_pendapatan_tindakan=rs2.getString("nama_pendapatan_tindakan");
                            kode_beban_jasa_dokter=rs2.getString("beban_jasa_dokter");
                            nama_beban_jasa_dokter=rs2.getString("nama_beban_jasa_dokter");
                            kode_utang_jasa_dokter=rs2.getString("utang_jasa_dokter");
                            nama_utang_jasa_dokter=rs2.getString("nama_utang_jasa_dokter");
                            kode_beban_jasa_paramedis=rs2.getString("beban_jasa_paramedis");
                            nama_beban_jasa_paramedis=rs2.getString("nama_beban_jasa_paramedis");
                            kode_utang_jasa_paramedis=rs2.getString("utang_jasa_paramedis");
                            nama_utang_jasa_paramedis=rs2.getString("nama_utang_jasa_paramedis");
                            kode_beban_kso=rs2.getString("beban_kso");
                            nama_beban_kso=rs2.getString("nama_beban_kso");
                            kode_utang_kso=rs2.getString("utang_kso");
                            nama_utang_kso=rs2.getString("nama_utang_kso");
                            kode_hpp_persediaan=rs2.getString("hpp_persediaan");
                            nama_hpp_persediaan=rs2.getString("nama_hpp_persediaan");
                            kode_persediaan_bhp=rs2.getString("persediaan_bhp");
                            nama_persediaan_bhp=rs2.getString("nama_persediaan_bhp");
                            kode_beban_jasa_sarana=rs2.getString("beban_jasa_sarana");
                            nama_beban_jasa_sarana=rs2.getString("nama_beban_jasa_sarana");
                            kode_utang_jasa_sarana=rs2.getString("utang_jasa_sarana");
                            nama_utang_jasa_sarana=rs2.getString("nama_utang_jasa_sarana");
                            kode_beban_menejemen=rs2.getString("beban_menejemen");
                            nama_beban_menejemen=rs2.getString("nama_beban_menejemen");
                            kode_utang_menejemen=rs2.getString("utang_menejemen");
                            nama_utang_menejemen=rs2.getString("nama_utang_menejemen");
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
                    
                    tabModeRalan.addRow(new Object[]{
                        rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan"),rs.getString("nm_kategori"),rs.getString("png_jawab"),rs.getString("nm_poli"),
                        kode_pendapatan_tindakan,nama_pendapatan_tindakan,kode_beban_jasa_dokter,nama_beban_jasa_dokter,kode_utang_jasa_dokter,nama_utang_jasa_dokter,
                        kode_beban_jasa_paramedis,nama_beban_jasa_paramedis,kode_utang_jasa_paramedis,nama_utang_jasa_paramedis,kode_beban_kso,nama_beban_kso,
                        kode_utang_kso,nama_utang_kso,kode_hpp_persediaan,nama_hpp_persediaan,kode_persediaan_bhp,nama_persediaan_bhp,kode_beban_jasa_sarana,
                        nama_beban_jasa_sarana,kode_utang_jasa_sarana,nama_utang_jasa_sarana,kode_beban_menejemen,nama_beban_menejemen,kode_utang_menejemen,
                        nama_utang_menejemen
                    });
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
    
    private void tampilranap() {
        Valid.tabelKosong(tabModeRanap);
        try{    
            ps=koneksi.prepareStatement(
               "select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,penjab.png_jawab,bangsal.nm_bangsal, "+
               "jns_perawatan_inap.kelas from jns_perawatan_inap inner join kategori_perawatan on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori "+
               "inner join penjab on penjab.kd_pj=jns_perawatan_inap.kd_pj inner join bangsal on bangsal.kd_bangsal=jns_perawatan_inap.kd_bangsal "+
               "where jns_perawatan_inap.status='1' order by jns_perawatan_inap.kd_jenis_prw");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    kode_pendapatan_tindakan="";nama_pendapatan_tindakan="";kode_beban_jasa_dokter="";nama_beban_jasa_dokter="";kode_utang_jasa_dokter="";
                    nama_utang_jasa_dokter="";kode_beban_jasa_paramedis="";nama_beban_jasa_paramedis="";kode_utang_jasa_paramedis="";nama_utang_jasa_paramedis=""; 
                    kode_beban_kso="";nama_beban_kso="";kode_utang_kso="";nama_utang_kso="";kode_hpp_persediaan="";nama_hpp_persediaan="";kode_persediaan_bhp="";
                    nama_persediaan_bhp="";kode_beban_jasa_sarana="";nama_beban_jasa_sarana="";kode_utang_jasa_sarana="";nama_utang_jasa_sarana="";
                    kode_beban_menejemen="";nama_beban_menejemen="";kode_utang_menejemen="";nama_utang_menejemen="";
                    ps2=koneksi.prepareStatement(
                        "select matrik_akun_jns_perawatan_inap.pendapatan_tindakan,pendapatantindakan.nm_rek as nama_pendapatan_tindakan, "+
                        "matrik_akun_jns_perawatan_inap.beban_jasa_dokter,bebanjasadokter.nm_rek as nama_beban_jasa_dokter,"+
                        "matrik_akun_jns_perawatan_inap.utang_jasa_dokter,utangjasadokter.nm_rek as nama_utang_jasa_dokter,"+
                        "matrik_akun_jns_perawatan_inap.beban_jasa_paramedis,bebanjasaparamedis.nm_rek as nama_beban_jasa_paramedis,"+
                        "matrik_akun_jns_perawatan_inap.utang_jasa_paramedis,utangjasaparamedis.nm_rek as nama_utang_jasa_paramedis,"+
                        "matrik_akun_jns_perawatan_inap.beban_kso,bebankso.nm_rek as nama_beban_kso,"+
                        "matrik_akun_jns_perawatan_inap.utang_kso,utangkso.nm_rek as nama_utang_kso,"+
                        "matrik_akun_jns_perawatan_inap.hpp_persediaan,hpppersediaan.nm_rek as nama_hpp_persediaan,"+
                        "matrik_akun_jns_perawatan_inap.persediaan_bhp,persediaanbhp.nm_rek as nama_persediaan_bhp,"+
                        "matrik_akun_jns_perawatan_inap.beban_jasa_sarana,bebanjasasarana.nm_rek as nama_beban_jasa_sarana,"+
                        "matrik_akun_jns_perawatan_inap.utang_jasa_sarana,utangjasasarana.nm_rek as nama_utang_jasa_sarana,"+
                        "matrik_akun_jns_perawatan_inap.beban_menejemen,bebanmenejemen.nm_rek as nama_beban_menejemen,"+
                        "matrik_akun_jns_perawatan_inap.utang_menejemen,utangmenejemen.nm_rek as nama_utang_menejemen "+
                        "from matrik_akun_jns_perawatan_inap inner join rekening as pendapatantindakan on matrik_akun_jns_perawatan_inap.pendapatan_tindakan=pendapatantindakan.kd_rek "+
                        "inner join rekening as bebanjasadokter on matrik_akun_jns_perawatan_inap.beban_jasa_dokter=bebanjasadokter.kd_rek "+
                        "inner join rekening as utangjasadokter on matrik_akun_jns_perawatan_inap.utang_jasa_dokter=utangjasadokter.kd_rek "+
                        "inner join rekening as bebanjasaparamedis on matrik_akun_jns_perawatan_inap.beban_jasa_paramedis=bebanjasaparamedis.kd_rek "+
                        "inner join rekening as utangjasaparamedis on matrik_akun_jns_perawatan_inap.utang_jasa_paramedis=utangjasaparamedis.kd_rek "+
                        "inner join rekening as bebankso on matrik_akun_jns_perawatan_inap.beban_kso=bebankso.kd_rek "+
                        "inner join rekening as utangkso on matrik_akun_jns_perawatan_inap.utang_kso=utangkso.kd_rek "+
                        "inner join rekening as hpppersediaan on matrik_akun_jns_perawatan_inap.hpp_persediaan=hpppersediaan.kd_rek "+
                        "inner join rekening as persediaanbhp on matrik_akun_jns_perawatan_inap.persediaan_bhp=persediaanbhp.kd_rek "+
                        "inner join rekening as bebanjasasarana on matrik_akun_jns_perawatan_inap.beban_jasa_sarana=bebanjasasarana.kd_rek "+
                        "inner join rekening as utangjasasarana on matrik_akun_jns_perawatan_inap.utang_jasa_sarana=utangjasasarana.kd_rek "+
                        "inner join rekening as bebanmenejemen on matrik_akun_jns_perawatan_inap.beban_menejemen=bebanmenejemen.kd_rek "+
                        "inner join rekening as utangmenejemen on matrik_akun_jns_perawatan_inap.utang_menejemen=utangmenejemen.kd_rek "+
                        "where matrik_akun_jns_perawatan_inap.kd_jenis_prw=?");
                    try {
                        ps2.setString(1,rs.getString("kd_jenis_prw"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            kode_pendapatan_tindakan=rs2.getString("pendapatan_tindakan");
                            nama_pendapatan_tindakan=rs2.getString("nama_pendapatan_tindakan");
                            kode_beban_jasa_dokter=rs2.getString("beban_jasa_dokter");
                            nama_beban_jasa_dokter=rs2.getString("nama_beban_jasa_dokter");
                            kode_utang_jasa_dokter=rs2.getString("utang_jasa_dokter");
                            nama_utang_jasa_dokter=rs2.getString("nama_utang_jasa_dokter");
                            kode_beban_jasa_paramedis=rs2.getString("beban_jasa_paramedis");
                            nama_beban_jasa_paramedis=rs2.getString("nama_beban_jasa_paramedis");
                            kode_utang_jasa_paramedis=rs2.getString("utang_jasa_paramedis");
                            nama_utang_jasa_paramedis=rs2.getString("nama_utang_jasa_paramedis");
                            kode_beban_kso=rs2.getString("beban_kso");
                            nama_beban_kso=rs2.getString("nama_beban_kso");
                            kode_utang_kso=rs2.getString("utang_kso");
                            nama_utang_kso=rs2.getString("nama_utang_kso");
                            kode_hpp_persediaan=rs2.getString("hpp_persediaan");
                            nama_hpp_persediaan=rs2.getString("nama_hpp_persediaan");
                            kode_persediaan_bhp=rs2.getString("persediaan_bhp");
                            nama_persediaan_bhp=rs2.getString("nama_persediaan_bhp");
                            kode_beban_jasa_sarana=rs2.getString("beban_jasa_sarana");
                            nama_beban_jasa_sarana=rs2.getString("nama_beban_jasa_sarana");
                            kode_utang_jasa_sarana=rs2.getString("utang_jasa_sarana");
                            nama_utang_jasa_sarana=rs2.getString("nama_utang_jasa_sarana");
                            kode_beban_menejemen=rs2.getString("beban_menejemen");
                            nama_beban_menejemen=rs2.getString("nama_beban_menejemen");
                            kode_utang_menejemen=rs2.getString("utang_menejemen");
                            nama_utang_menejemen=rs2.getString("nama_utang_menejemen");
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
                    
                    tabModeRanap.addRow(new Object[]{
                        rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan"),rs.getString("nm_kategori"),rs.getString("png_jawab"),rs.getString("nm_bangsal"),rs.getString("kelas"),
                        kode_pendapatan_tindakan,nama_pendapatan_tindakan,kode_beban_jasa_dokter,nama_beban_jasa_dokter,kode_utang_jasa_dokter,nama_utang_jasa_dokter,
                        kode_beban_jasa_paramedis,nama_beban_jasa_paramedis,kode_utang_jasa_paramedis,nama_utang_jasa_paramedis,kode_beban_kso,nama_beban_kso,
                        kode_utang_kso,nama_utang_kso,kode_hpp_persediaan,nama_hpp_persediaan,kode_persediaan_bhp,nama_persediaan_bhp,kode_beban_jasa_sarana,
                        nama_beban_jasa_sarana,kode_utang_jasa_sarana,nama_utang_jasa_sarana,kode_beban_menejemen,nama_beban_menejemen,kode_utang_menejemen,
                        nama_utang_menejemen
                    });
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
