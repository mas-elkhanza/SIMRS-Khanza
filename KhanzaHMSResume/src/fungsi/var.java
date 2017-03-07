package fungsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author Owner
 */

/**
 *
 * @author Owner
 */
public final class var {
    private static final Connection koneksi=koneksiDB.condb();
    private static PreparedStatement ps,ps2;
    private static ResultSet rs,rs2;
    
    private static String kode="",kdbangsal="",namars="",alamatrs="",kabupatenrs="",propinsirs="",kontakrs="",emailrs="",form=""; 
    private static int jml1=0,jml2=0,lebar=0,tinggi=0;
    private static boolean admin=false,user=false,vakum=false,aplikasi=false,penyakit=false,obat_penyakit=false,dokter=false,jadwal_praktek=false,petugas=false,pasien=false,registrasi=false,
            tindakan_ralan=false,kamar_inap=false,tindakan_ranap=false,operasi=false,rujukan_keluar=false,rujukan_masuk=false,beri_obat=false,
            resep_pulang=false,pasien_meninggal=false,diet_pasien=false,kelahiran_bayi=false,periksa_lab=false,periksa_radiologi=false,
            kasir_ralan=false,deposit_pasien=false,piutang_pasien=false,peminjaman_berkas=false,barcode=false,presensi_harian=false,
            presensi_bulanan=false,pegawai_admin=false,pegawai_user=false,suplier=false,satuan_barang=false,konversi_satuan=false,jenis_barang=false,
            obat=false,stok_opname_obat=false,stok_obat_pasien=false,pengadaan_obat=false,pemesanan_obat=false,penjualan_obat=false,piutang_obat=false,
            retur_ke_suplier=false,retur_dari_pembeli=false,retur_obat_ranap=false,retur_piutang_pasien=false,keuntungan_penjualan=false,keuntungan_beri_obat=false,
            sirkulasi_obat=false,ipsrs_barang=false,ipsrs_jenis_barang=false,ipsrs_pengadaan_barang=false,ipsrs_stok_keluar=false,ipsrs_rekap_pengadaan=false,ipsrs_rekap_stok_keluar=false,
            ipsrs_pengeluaran_harian=false,inventaris_jenis=false,inventaris_kategori=false,inventaris_merk=false,inventaris_ruang=false,inventaris_produsen=false,
            inventaris_koleksi=false,inventaris_inventaris=false,inventaris_sirkulasi=false,parkir_jenis=false,parkir_in=false,parkir_out=false,
            parkir_rekap_harian=false,parkir_rekap_bulanan=false,informasi_kamar=false,harian_tindakan_poli=false,obat_per_poli=false,obat_per_kamar=false,
            obat_per_dokter_ralan=false,obat_per_dokter_ranap=false,harian_dokter=false,bulanan_dokter=false,harian_paramedis=false,bulanan_paramedis=false,
            pembayaran_ralan=false,pembayaran_ranap=false,rekap_pembayaran_ralan=false,rekap_pembayaran_ranap=false,tagihan_masuk=false,tambahan_biaya=false,
            potongan_biaya=false,resep_obat=false,resume_pasien=false,penyakit_ralan=false,penyakit_ranap=false,kamar=false,tarif_ralan=false,tarif_ranap=false,
            tarif_lab=false,tarif_radiologi=false,tarif_operasi=false,akun_rekening=false,rekening_tahun=false,posting_jurnal=false,buku_besar=false,
            cashflow=false,keuangan=false,pengeluaran=false,setup_pjlab=false,setup_otolokasi=false,setup_jam_kamin=false,setup_embalase=false,tracer_login=false,
            display=false,set_harga_obat=false,set_penggunaan_tarif=false,set_oto_ralan=false,biaya_harian=false,biaya_masuk_sekali=false,set_no_rm=false,
            billing_ralan=false,billing_ranap=false,status=false,jm_ranap_dokter=false,igd=false,barcoderalan=false,barcoderanap=false,set_harga_obat_ralan=false,
            set_harga_obat_ranap=false,penyakit_pd3i=false,surveilans_pd3i=false,surveilans_ralan=false,diagnosa_pasien=false,surveilans_ranap=false,
            pny_takmenular_ranap=false,pny_takmenular_ralan=false,kunjungan_ralan=false,rl32=false,rl33=false,rl37=false,rl38=false,harian_tindakan_dokter=false,
            sms=false,sidikjari=false,jam_masuk=false,jadwal_pegawai=false,parkir_barcode=false,set_nota=false,dpjp_ranap=false,mutasi_barang=false,rl34=false,rl36=false,
            fee_visit_dokter=false,fee_bacaan_ekg=false,fee_rujukan_rontgen=false,fee_rujukan_ranap=false,fee_ralan=false,akun_bayar=false,bayar_pemesanan_obat=false,
            obat_per_dokter_peresep=false,pemasukan_lain=false,pengaturan_rekening=false,closing_kasir=false,keterlambatan_presensi=false,set_harga_kamar=false,
            rekap_per_shift=false,bpjs_cek_nik=false,bpjs_cek_kartu=false,bpjs_cek_riwayat=false,obat_per_cara_bayar=false,kunjungan_ranap=false,bayar_piutang=false,
            payment_point=false,bpjs_cek_nomor_rujukan=false,icd9=false;
    
    public static void setData(String user, String pass) {
       try {                
                ps=koneksi.prepareStatement("select * from admin where usere=AES_ENCRYPT(?,'nur') and passworde=AES_ENCRYPT(?,'windi')");               
                ps2=koneksi.prepareStatement("select * from user where id_user=AES_ENCRYPT(?,'nur') and password=AES_ENCRYPT(?,'windi')");
                try {
                    ps.setString(1,user);
                    ps.setString(2,pass);
                    rs=ps.executeQuery();
                    rs.last();           

                    ps2.setString(1,user);
                    ps2.setString(2,pass);
                    rs2=ps2.executeQuery();
                    rs2.last();

                    var.jml1=rs.getRow();
                    var.jml2=rs2.getRow();               

                    if(user.equals("admin") && pass.equals("akusayangsamakamu122456")){
                        var.kode="Admin Utama";
                        var.penyakit=true;
                        var.obat_penyakit=true;
                        var.dokter=true;
                        var.jadwal_praktek=true;
                        var.petugas=true;
                        var.pasien=true;
                        var.registrasi=true;
                        var.tindakan_ralan=true;
                        var.kamar_inap=true;
                        var.tindakan_ranap=true;
                        var.operasi=true;
                        var.rujukan_keluar=true;
                        var.rujukan_masuk=true;
                        var.beri_obat=true;
                        var.resep_pulang=true;
                        var.pasien_meninggal=true;
                        var.diet_pasien=true;
                        var.kelahiran_bayi=true;
                        var.periksa_lab=true;
                        var.periksa_radiologi=true;
                        var.kasir_ralan=true;
                        var.deposit_pasien=true;
                        var.piutang_pasien=true;
                        var.peminjaman_berkas=true;
                        var.barcode=true;
                        var.presensi_harian=true;
                        var.presensi_bulanan=true;
                        var.pegawai_admin=true;
                        var.pegawai_user=true;
                        var.suplier=true;
                        var.satuan_barang=true;
                        var.konversi_satuan=true;
                        var.jenis_barang=true;
                        var.obat=true;
                        var.stok_opname_obat=true;
                        var.stok_obat_pasien=true;
                        var.pengadaan_obat=true;
                        var.pemesanan_obat=true;
                        var.penjualan_obat=true;
                        var.piutang_obat=true;
                        var.retur_ke_suplier=true;
                        var.retur_dari_pembeli=true;
                        var.retur_obat_ranap=true;
                        var.retur_piutang_pasien=true;
                        var.keuntungan_penjualan=true;
                        var.keuntungan_beri_obat=true;
                        var.sirkulasi_obat=true;
                        var.ipsrs_barang=true;
                        var.ipsrs_jenis_barang=true;
                        var.ipsrs_pengadaan_barang=true;
                        var.ipsrs_stok_keluar=true;
                        var.ipsrs_rekap_pengadaan=true;
                        var.ipsrs_rekap_stok_keluar=true;
                        var.ipsrs_pengeluaran_harian=true;
                        var.inventaris_jenis=true;
                        var.inventaris_kategori=true;
                        var.inventaris_merk=true;
                        var.inventaris_ruang=true;
                        var.inventaris_produsen=true;
                        var.inventaris_koleksi=true;
                        var.inventaris_inventaris=true;
                        var.inventaris_sirkulasi=true;
                        var.parkir_jenis=true;
                        var.parkir_in=true;
                        var.parkir_out=true;
                        var.parkir_rekap_harian=true;
                        var.parkir_rekap_bulanan=true;
                        var.informasi_kamar=true;
                        var.harian_tindakan_poli=true;
                        var.obat_per_poli=true;
                        var.obat_per_kamar=true;
                        var.obat_per_dokter_ralan=true;
                        var.obat_per_dokter_ranap=true;
                        var.harian_dokter=true;
                        var.bulanan_dokter=true;
                        var.harian_paramedis=true;
                        var.bulanan_paramedis=true;
                        var.pembayaran_ralan=true;
                        var.pembayaran_ranap=true;
                        var.rekap_pembayaran_ralan=true;
                        var.rekap_pembayaran_ranap=true;
                        var.tagihan_masuk=true;
                        var.tambahan_biaya=true;
                        var.potongan_biaya=true;
                        var.resep_obat=true;
                        var.resume_pasien=true;
                        var.penyakit_ralan=true;
                        var.penyakit_ranap=true;
                        var.kamar=true;
                        var.tarif_ralan=true;
                        var.tarif_ranap=true;
                        var.tarif_lab=true;
                        var.tarif_radiologi=true;
                        var.tarif_operasi=true;
                        var.akun_rekening=true;
                        var.rekening_tahun=true;
                        var.posting_jurnal=true;
                        var.buku_besar=true;
                        var.cashflow=true;
                        var.keuangan=true;
                        var.pengeluaran=true;
                        var.setup_pjlab=true;
                        var.setup_otolokasi=true;
                        var.setup_jam_kamin=true;
                        var.setup_embalase=true;
                        var.tracer_login=true;
                        var.display=true;
                        var.set_harga_obat=true;
                        var.set_penggunaan_tarif=true;
                        var.set_oto_ralan=true;
                        var.biaya_harian=true;
                        var.biaya_masuk_sekali=true;
                        var.set_no_rm=true;
                        var.billing_ralan=true;
                        var.billing_ranap=true;
                        var.jm_ranap_dokter=true;
                        var.igd=true;
                        var.barcoderalan=true;
                        var.barcoderanap=true;
                        var.set_harga_obat_ralan=true;
                        var.set_harga_obat_ranap=true;
                        var.penyakit_pd3i=true;
                        var.surveilans_pd3i=true;
                        var.surveilans_ralan=true;
                        var.diagnosa_pasien=true;
                        var.admin=true;
                        var.user=true;
                        var.vakum=true;
                        var.aplikasi=true;
                        var.surveilans_ranap=true;
                        var.pny_takmenular_ranap=true;
                        var.pny_takmenular_ralan=true;
                        var.kunjungan_ralan=true;
                        var.rl32=true;
                        var.rl33=true;
                        var.rl37=true;
                        var.rl38=true;
                        var.harian_tindakan_dokter=true;
                        var.sms=true;
                        var.sidikjari=true;
                        var.jam_masuk=true;
                        var.jadwal_pegawai=true;
                        var.parkir_barcode=true;
                        var.set_nota=true;
                        var.dpjp_ranap=true;
                        var.mutasi_barang=true;
                        var.rl34=true;
                        var.rl36=true;
                        var.fee_visit_dokter=true;
                        var.fee_bacaan_ekg=true;
                        var.fee_rujukan_rontgen=true;
                        var.fee_rujukan_ranap=true;
                        var.fee_ralan=true;
                        var.akun_bayar=true;
                        var.bayar_pemesanan_obat=true;
                        var.obat_per_dokter_peresep=true;
                        var.pemasukan_lain=true;
                        var.pengaturan_rekening=true;
                        var.closing_kasir=true;
                        var.keterlambatan_presensi=true; 
                        var.set_harga_kamar=true;
                        var.rekap_per_shift=true;
                        var.bpjs_cek_nik=true;
                        var.bpjs_cek_kartu=true;
                        var.bpjs_cek_riwayat=true;
                        var.obat_per_cara_bayar=true;
                        var.kunjungan_ranap=true;
                        var.bayar_piutang=true;
                        var.payment_point=true;
                        var.bpjs_cek_nomor_rujukan=true;
                        var.icd9=true;
                    }else if(rs.getRow()>=1){
                        var.kode="Admin Utama";
                        var.penyakit=true;
                        var.obat_penyakit=true;
                        var.dokter=true;
                        var.jadwal_praktek=true;
                        var.petugas=true;
                        var.pasien=true;
                        var.registrasi=true;
                        var.tindakan_ralan=true;
                        var.kamar_inap=true;
                        var.tindakan_ranap=true;
                        var.operasi=true;
                        var.rujukan_keluar=true;
                        var.rujukan_masuk=true;
                        var.beri_obat=true;
                        var.resep_pulang=true;
                        var.pasien_meninggal=true;
                        var.diet_pasien=true;
                        var.kelahiran_bayi=true;
                        var.periksa_lab=true;
                        var.periksa_radiologi=true;
                        var.kasir_ralan=true;
                        var.deposit_pasien=true;
                        var.piutang_pasien=true;
                        var.peminjaman_berkas=true;
                        var.barcode=true;
                        var.presensi_harian=true;
                        var.presensi_bulanan=true;
                        var.pegawai_admin=true;
                        var.pegawai_user=true;
                        var.suplier=true;
                        var.satuan_barang=true;
                        var.konversi_satuan=true;
                        var.jenis_barang=true;
                        var.obat=true;
                        var.stok_opname_obat=true;
                        var.stok_obat_pasien=true;
                        var.pengadaan_obat=true;
                        var.pemesanan_obat=true;
                        var.penjualan_obat=true;
                        var.piutang_obat=true;
                        var.retur_ke_suplier=true;
                        var.retur_dari_pembeli=true;
                        var.retur_obat_ranap=true;
                        var.retur_piutang_pasien=true;
                        var.keuntungan_penjualan=true;
                        var.keuntungan_beri_obat=true;
                        var.sirkulasi_obat=true;
                        var.ipsrs_barang=true;
                        var.ipsrs_pengadaan_barang=true;
                        var.ipsrs_stok_keluar=true;
                        var.ipsrs_rekap_pengadaan=true;
                        var.ipsrs_rekap_stok_keluar=true;
                        var.ipsrs_pengeluaran_harian=true;
                        var.ipsrs_jenis_barang=true;
                        var.inventaris_jenis=true;
                        var.inventaris_kategori=true;
                        var.inventaris_merk=true;
                        var.inventaris_ruang=true;
                        var.inventaris_produsen=true;
                        var.inventaris_koleksi=true;
                        var.inventaris_inventaris=true;
                        var.inventaris_sirkulasi=true;
                        var.parkir_jenis=true;
                        var.parkir_in=true;
                        var.parkir_out=true;
                        var.parkir_rekap_harian=true;
                        var.parkir_rekap_bulanan=true;
                        var.informasi_kamar=true;
                        var.harian_tindakan_poli=true;
                        var.obat_per_poli=true;
                        var.obat_per_kamar=true;
                        var.obat_per_dokter_ralan=true;
                        var.obat_per_dokter_ranap=true;
                        var.harian_dokter=true;
                        var.bulanan_dokter=true;
                        var.harian_paramedis=true;
                        var.bulanan_paramedis=true;
                        var.pembayaran_ralan=true;
                        var.pembayaran_ranap=true;
                        var.rekap_pembayaran_ralan=true;
                        var.rekap_pembayaran_ranap=true;
                        var.tagihan_masuk=true;
                        var.tambahan_biaya=true;
                        var.potongan_biaya=true;
                        var.resep_obat=true;
                        var.resume_pasien=true;
                        var.penyakit_ralan=true;
                        var.penyakit_ranap=true;
                        var.kamar=true;
                        var.tarif_ralan=true;
                        var.tarif_ranap=true;
                        var.tarif_lab=true;
                        var.tarif_radiologi=true;
                        var.tarif_operasi=true;
                        var.akun_rekening=true;
                        var.rekening_tahun=true;
                        var.posting_jurnal=true;
                        var.buku_besar=true;
                        var.cashflow=true;
                        var.keuangan=true;
                        var.pengeluaran=true;
                        var.setup_pjlab=true;
                        var.setup_otolokasi=true;
                        var.setup_jam_kamin=true;
                        var.setup_embalase=true;
                        var.tracer_login=true;
                        var.display=true;
                        var.set_harga_obat=true;
                        var.set_penggunaan_tarif=true;
                        var.set_oto_ralan=true;
                        var.biaya_harian=true;
                        var.biaya_masuk_sekali=true;
                        var.set_no_rm=true;
                        var.billing_ralan=true;
                        var.billing_ranap=true;
                        var.jm_ranap_dokter=true;
                        var.igd=true;                    
                        var.barcoderalan=true;
                        var.barcoderanap=true;
                        var.set_harga_obat_ralan=true;
                        var.set_harga_obat_ranap=true;
                        var.penyakit_pd3i=true;
                        var.surveilans_pd3i=true;
                        var.surveilans_ralan=true;
                        var.diagnosa_pasien=true;
                        var.admin=true;
                        var.user=true;
                        var.vakum=true;
                        var.aplikasi=true;
                        var.surveilans_ranap=true;
                        var.pny_takmenular_ranap=true;
                        var.pny_takmenular_ralan=true;
                        var.kunjungan_ralan=true;
                        var.rl32=true;
                        var.rl33=true;
                        var.rl37=true;
                        var.rl38=true;
                        var.harian_tindakan_dokter=true;
                        var.sms=true;
                        var.sidikjari=true;
                        var.jam_masuk=true;
                        var.jadwal_pegawai=true;                    
                        var.parkir_barcode=true;
                        var.set_nota=true;
                        var.dpjp_ranap=true;
                        var.mutasi_barang=true;
                        var.rl34=true;
                        var.rl36=true;
                        var.fee_visit_dokter=true;
                        var.fee_bacaan_ekg=true;
                        var.fee_rujukan_rontgen=true;
                        var.fee_rujukan_ranap=true;
                        var.fee_ralan=true;
                        var.akun_bayar=true;
                        var.bayar_pemesanan_obat=true;
                        var.obat_per_dokter_peresep=true;
                        var.pemasukan_lain=true;
                        var.pengaturan_rekening=true;
                        var.closing_kasir=true;
                        var.keterlambatan_presensi=true; 
                        var.set_harga_kamar=true;
                        var.rekap_per_shift=true;
                        var.bpjs_cek_nik=true;
                        var.bpjs_cek_kartu=true;
                        var.bpjs_cek_riwayat=true;
                        var.obat_per_cara_bayar=true;
                        var.kunjungan_ranap=true;
                        var.bayar_piutang=true;
                        var.payment_point=true;
                        var.bpjs_cek_nomor_rujukan=true;
                        var.icd9=true;
                    }else if(rs2.getRow()>=1){   
                        rs2.beforeFirst();
                        rs2.next();
                        var.kode=user;
                        var.penyakit=rs2.getBoolean("penyakit");
                        var.obat_penyakit=rs2.getBoolean("obat_penyakit");
                        var.dokter=rs2.getBoolean("dokter");
                        var.jadwal_praktek=rs2.getBoolean("jadwal_praktek");
                        var.petugas=rs2.getBoolean("petugas");
                        var.pasien=rs2.getBoolean("pasien");
                        var.registrasi=rs2.getBoolean("registrasi");
                        var.tindakan_ralan=rs2.getBoolean("tindakan_ralan");
                        var.kamar_inap=rs2.getBoolean("kamar_inap");
                        var.tindakan_ranap=rs2.getBoolean("tindakan_ranap");
                        var.operasi=rs2.getBoolean("operasi");
                        var.rujukan_keluar=rs2.getBoolean("rujukan_keluar");
                        var.rujukan_masuk=rs2.getBoolean("rujukan_masuk");
                        var.beri_obat=rs2.getBoolean("beri_obat");
                        var.resep_pulang=rs2.getBoolean("resep_pulang");
                        var.pasien_meninggal=rs2.getBoolean("pasien_meninggal");
                        var.diet_pasien=rs2.getBoolean("diet_pasien");
                        var.kelahiran_bayi=rs2.getBoolean("kelahiran_bayi");
                        var.periksa_lab=rs2.getBoolean("periksa_lab");
                        var.periksa_radiologi=rs2.getBoolean("periksa_radiologi");
                        var.kasir_ralan=rs2.getBoolean("kasir_ralan");
                        var.deposit_pasien=rs2.getBoolean("deposit_pasien");
                        var.piutang_pasien=rs2.getBoolean("piutang_pasien");
                        var.peminjaman_berkas=rs2.getBoolean("peminjaman_berkas");
                        var.barcode=rs2.getBoolean("barcode");
                        var.presensi_harian=rs2.getBoolean("presensi_harian");
                        var.presensi_bulanan=rs2.getBoolean("presensi_bulanan");
                        var.pegawai_admin=rs2.getBoolean("pegawai_admin");
                        var.pegawai_user=rs2.getBoolean("pegawai_user");
                        var.suplier=rs2.getBoolean("suplier");
                        var.satuan_barang=rs2.getBoolean("satuan_barang");
                        var.konversi_satuan=rs2.getBoolean("konversi_satuan");
                        var.jenis_barang=rs2.getBoolean("jenis_barang");
                        var.obat=rs2.getBoolean("obat");
                        var.stok_opname_obat=rs2.getBoolean("stok_opname_obat");
                        var.stok_obat_pasien=rs2.getBoolean("stok_obat_pasien");
                        var.pengadaan_obat=rs2.getBoolean("pengadaan_obat");
                        var.pemesanan_obat=rs2.getBoolean("pemesanan_obat");
                        var.penjualan_obat=rs2.getBoolean("penjualan_obat");
                        var.piutang_obat=rs2.getBoolean("piutang_obat");
                        var.retur_ke_suplier=rs2.getBoolean("retur_ke_suplier");
                        var.retur_dari_pembeli=rs2.getBoolean("retur_dari_pembeli");
                        var.retur_obat_ranap=rs2.getBoolean("retur_obat_ranap");
                        var.retur_piutang_pasien=rs2.getBoolean("retur_piutang_pasien");
                        var.keuntungan_penjualan=rs2.getBoolean("keuntungan_penjualan");
                        var.keuntungan_beri_obat=rs2.getBoolean("keuntungan_beri_obat");
                        var.sirkulasi_obat=rs2.getBoolean("sirkulasi_obat");
                        var.ipsrs_barang=rs2.getBoolean("ipsrs_barang");
                        var.ipsrs_pengadaan_barang=rs2.getBoolean("ipsrs_pengadaan_barang");
                        var.ipsrs_stok_keluar=rs2.getBoolean("ipsrs_stok_keluar");
                        var.ipsrs_jenis_barang=rs2.getBoolean("ipsrs_jenis_barang");
                        var.ipsrs_rekap_pengadaan=rs2.getBoolean("ipsrs_rekap_pengadaan");
                        var.ipsrs_rekap_stok_keluar=rs2.getBoolean("ipsrs_rekap_stok_keluar");
                        var.ipsrs_pengeluaran_harian=rs2.getBoolean("ipsrs_pengeluaran_harian");
                        var.inventaris_jenis=rs2.getBoolean("inventaris_jenis");
                        var.inventaris_kategori=rs2.getBoolean("inventaris_kategori");
                        var.inventaris_merk=rs2.getBoolean("inventaris_merk");
                        var.inventaris_ruang=rs2.getBoolean("inventaris_ruang");
                        var.inventaris_produsen=rs2.getBoolean("inventaris_produsen");
                        var.inventaris_koleksi=rs2.getBoolean("inventaris_koleksi");
                        var.inventaris_inventaris=rs2.getBoolean("inventaris_inventaris");
                        var.inventaris_sirkulasi=rs2.getBoolean("inventaris_sirkulasi");
                        var.parkir_jenis=rs2.getBoolean("parkir_jenis");
                        var.parkir_in=rs2.getBoolean("parkir_in");
                        var.parkir_out=rs2.getBoolean("parkir_out");
                        var.parkir_rekap_harian=rs2.getBoolean("parkir_rekap_harian");
                        var.parkir_rekap_bulanan=rs2.getBoolean("parkir_rekap_bulanan");
                        var.informasi_kamar=rs2.getBoolean("informasi_kamar");
                        var.harian_tindakan_poli=rs2.getBoolean("harian_tindakan_poli");
                        var.obat_per_poli=rs2.getBoolean("obat_per_poli");
                        var.obat_per_kamar=rs2.getBoolean("obat_per_kamar");
                        var.obat_per_dokter_ralan=rs2.getBoolean("obat_per_dokter_ralan");
                        var.obat_per_dokter_ranap=rs2.getBoolean("obat_per_dokter_ranap");
                        var.harian_dokter=rs2.getBoolean("harian_dokter");
                        var.bulanan_dokter=rs2.getBoolean("bulanan_dokter");
                        var.harian_paramedis=rs2.getBoolean("harian_paramedis");
                        var.bulanan_paramedis=rs2.getBoolean("bulanan_paramedis");
                        var.pembayaran_ralan=rs2.getBoolean("pembayaran_ralan");
                        var.pembayaran_ranap=rs2.getBoolean("pembayaran_ranap");
                        var.rekap_pembayaran_ralan=rs2.getBoolean("rekap_pembayaran_ralan");
                        var.rekap_pembayaran_ranap=rs2.getBoolean("rekap_pembayaran_ranap");
                        var.tagihan_masuk=rs2.getBoolean("tagihan_masuk");
                        var.tambahan_biaya=rs2.getBoolean("tambahan_biaya");
                        var.potongan_biaya=rs2.getBoolean("potongan_biaya");
                        var.resep_obat=rs2.getBoolean("resep_obat");
                        var.resume_pasien=rs2.getBoolean("resume_pasien");
                        var.penyakit_ralan=rs2.getBoolean("penyakit_ralan");
                        var.penyakit_ranap=rs2.getBoolean("penyakit_ranap");
                        var.kamar=rs2.getBoolean("kamar");
                        var.tarif_ralan=rs2.getBoolean("tarif_ralan");
                        var.tarif_ranap=rs2.getBoolean("tarif_ranap");
                        var.tarif_lab=rs2.getBoolean("tarif_lab");
                        var.tarif_radiologi=rs2.getBoolean("tarif_radiologi");
                        var.tarif_operasi=rs2.getBoolean("tarif_operasi");
                        var.akun_rekening=rs2.getBoolean("akun_rekening");
                        var.rekening_tahun=rs2.getBoolean("rekening_tahun");
                        var.posting_jurnal=rs2.getBoolean("posting_jurnal");
                        var.buku_besar=rs2.getBoolean("buku_besar");
                        var.cashflow=rs2.getBoolean("cashflow");
                        var.keuangan=rs2.getBoolean("keuangan");
                        var.pengeluaran=rs2.getBoolean("pengeluaran");
                        var.setup_pjlab=rs2.getBoolean("setup_pjlab");
                        var.setup_otolokasi=rs2.getBoolean("setup_otolokasi");
                        var.setup_jam_kamin=rs2.getBoolean("setup_jam_kamin");
                        var.setup_embalase=rs2.getBoolean("setup_embalase");
                        var.tracer_login=rs2.getBoolean("tracer_login");
                        var.display=rs2.getBoolean("display");
                        var.set_harga_obat=rs2.getBoolean("set_harga_obat");
                        var.set_penggunaan_tarif=rs2.getBoolean("set_penggunaan_tarif");
                        var.set_oto_ralan=rs2.getBoolean("set_oto_ralan");
                        var.biaya_harian=rs2.getBoolean("biaya_harian");
                        var.biaya_masuk_sekali=rs2.getBoolean("biaya_masuk_sekali");
                        var.set_no_rm=rs2.getBoolean("set_no_rm");                    
                        var.billing_ralan=rs2.getBoolean("billing_ralan"); 
                        var.billing_ranap=rs2.getBoolean("billing_ranap"); 
                        var.jm_ranap_dokter=rs2.getBoolean("jm_ranap_dokter");   
                        var.igd=rs2.getBoolean("igd");                    
                        var.barcoderalan=rs2.getBoolean("barcoderalan"); 
                        var.barcoderanap=rs2.getBoolean("barcoderanap"); 
                        var.set_harga_obat_ralan=rs2.getBoolean("set_harga_obat_ralan"); 
                        var.set_harga_obat_ranap=rs2.getBoolean("set_harga_obat_ranap");
                        var.penyakit_pd3i=rs2.getBoolean("penyakit_pd3i");
                        var.surveilans_pd3i=rs2.getBoolean("surveilans_pd3i");
                        var.surveilans_ralan=rs2.getBoolean("surveilans_ralan");
                        var.diagnosa_pasien=rs2.getBoolean("diagnosa_pasien");
                        var.surveilans_ranap=rs2.getBoolean("surveilans_ranap");
                        var.admin=false;
                        var.user=false;
                        var.vakum=false;
                        var.aplikasi=false;
                        var.pny_takmenular_ranap=rs2.getBoolean("pny_takmenular_ranap");
                        var.pny_takmenular_ralan=rs2.getBoolean("pny_takmenular_ralan");
                        var.kunjungan_ralan=rs2.getBoolean("kunjungan_ralan");
                        var.rl32=rs2.getBoolean("rl32");
                        var.rl33=rs2.getBoolean("rl33");
                        var.rl37=rs2.getBoolean("rl37");
                        var.rl38=rs2.getBoolean("rl38");
                        var.harian_tindakan_dokter=rs2.getBoolean("harian_tindakan_dokter");
                        var.sms=rs2.getBoolean("sms");                    
                        var.sidikjari=rs2.getBoolean("sidikjari");  
                        var.jam_masuk=rs2.getBoolean("jam_masuk");  
                        var.jadwal_pegawai=rs2.getBoolean("jadwal_pegawai");  
                        var.parkir_barcode=rs2.getBoolean("parkir_barcode"); 
                        var.set_nota=rs2.getBoolean("set_nota");
                        var.dpjp_ranap=rs2.getBoolean("dpjp_ranap");
                        var.mutasi_barang=rs2.getBoolean("mutasi_barang");
                        var.rl34=rs2.getBoolean("rl34");
                        var.rl36=rs2.getBoolean("rl36");
                        var.fee_visit_dokter=rs2.getBoolean("fee_visit_dokter");
                        var.fee_bacaan_ekg=rs2.getBoolean("fee_bacaan_ekg");
                        var.fee_rujukan_rontgen=rs2.getBoolean("fee_rujukan_rontgen");
                        var.fee_rujukan_ranap=rs2.getBoolean("fee_rujukan_ranap");
                        var.fee_ralan=rs2.getBoolean("fee_ralan");
                        var.akun_bayar=rs2.getBoolean("akun_bayar");
                        var.bayar_pemesanan_obat=rs2.getBoolean("bayar_pemesanan_obat");
                        var.obat_per_dokter_peresep=rs2.getBoolean("obat_per_dokter_peresep");
                        var.pemasukan_lain=rs2.getBoolean("pemasukan_lain");
                        var.pengaturan_rekening=rs2.getBoolean("pengaturan_rekening");
                        var.closing_kasir=rs2.getBoolean("closing_kasir");
                        var.keterlambatan_presensi=rs2.getBoolean("keterlambatan_presensi");
                        var.set_harga_kamar=rs2.getBoolean("set_harga_kamar");
                        var.rekap_per_shift=rs2.getBoolean("rekap_per_shift");
                        var.bpjs_cek_nik=rs2.getBoolean("bpjs_cek_nik");
                        var.bpjs_cek_kartu=rs2.getBoolean("bpjs_cek_kartu");
                        var.bpjs_cek_riwayat=rs2.getBoolean("bpjs_cek_riwayat");
                        var.obat_per_cara_bayar=rs2.getBoolean("obat_per_cara_bayar");
                        var.kunjungan_ranap=rs2.getBoolean("kunjungan_ranap");
                        var.bayar_piutang=rs2.getBoolean("bayar_piutang");
                        var.payment_point=rs2.getBoolean("payment_point");
                        var.bpjs_cek_nomor_rujukan=rs2.getBoolean("bpjs_cek_nomor_rujukan");
                        var.icd9=rs2.getBoolean("icd9");
                    }else if((rs.getRow()==0)&&(rs2.getRow()==0)){
                        var.kode="";                  
                        var.penyakit= false;
                        var.obat_penyakit= false;
                        var.dokter= false;
                        var.jadwal_praktek= false;
                        var.petugas= false;
                        var.pasien= false;
                        var.registrasi= false;
                        var.tindakan_ralan= false;
                        var.kamar_inap= false;
                        var.tindakan_ranap= false;
                        var.operasi= false;
                        var.rujukan_keluar= false;
                        var.rujukan_masuk= false;
                        var.beri_obat= false;
                        var.resep_pulang= false;
                        var.pasien_meninggal= false;
                        var.diet_pasien= false;
                        var.kelahiran_bayi= false;
                        var.periksa_lab= false;
                        var.periksa_radiologi= false;
                        var.kasir_ralan= false;
                        var.deposit_pasien= false;
                        var.piutang_pasien= false;
                        var.peminjaman_berkas= false;
                        var.barcode= false;
                        var.presensi_harian= false;
                        var.presensi_bulanan= false;
                        var.pegawai_admin= false;
                        var.pegawai_user= false;
                        var.suplier= false;
                        var.satuan_barang= false;
                        var.konversi_satuan= false;
                        var.jenis_barang= false;
                        var.obat= false;
                        var.stok_opname_obat= false;
                        var.stok_obat_pasien= false;
                        var.pengadaan_obat= false;
                        var.pemesanan_obat= false;
                        var.penjualan_obat= false;
                        var.piutang_obat= false;
                        var.retur_ke_suplier= false;
                        var.retur_dari_pembeli= false;
                        var.retur_obat_ranap= false;
                        var.retur_piutang_pasien= false;
                        var.keuntungan_penjualan= false;
                        var.keuntungan_beri_obat= false;
                        var.sirkulasi_obat= false;
                        var.ipsrs_barang= false;
                        var.ipsrs_pengadaan_barang= false;
                        var.ipsrs_stok_keluar= false;
                        var.ipsrs_rekap_pengadaan= false;
                        var.ipsrs_rekap_stok_keluar= false;
                        var.ipsrs_pengeluaran_harian= false;
                        var.ipsrs_jenis_barang=false;
                        var.inventaris_jenis= false;
                        var.inventaris_kategori= false;
                        var.inventaris_merk= false;
                        var.inventaris_ruang= false;
                        var.inventaris_produsen= false;
                        var.inventaris_koleksi= false;
                        var.inventaris_inventaris= false;
                        var.inventaris_sirkulasi= false;
                        var.parkir_jenis= false;
                        var.parkir_in= false;
                        var.parkir_out= false;
                        var.parkir_rekap_harian= false;
                        var.parkir_rekap_bulanan= false;
                        var.informasi_kamar= false;
                        var.harian_tindakan_poli= false;
                        var.obat_per_poli= false;
                        var.obat_per_kamar= false;
                        var.obat_per_dokter_ralan= false;
                        var.obat_per_dokter_ranap= false;
                        var.harian_dokter= false;
                        var.bulanan_dokter= false;
                        var.harian_paramedis= false;
                        var.bulanan_paramedis= false;
                        var.pembayaran_ralan= false;
                        var.pembayaran_ranap= false;
                        var.rekap_pembayaran_ralan= false;
                        var.rekap_pembayaran_ranap= false;
                        var.tagihan_masuk= false;
                        var.tambahan_biaya= false;
                        var.potongan_biaya= false;
                        var.resep_obat= false;
                        var.resume_pasien= false;
                        var.penyakit_ralan= false;
                        var.penyakit_ranap= false;
                        var.kamar= false;
                        var.tarif_ralan= false;
                        var.tarif_ranap= false;
                        var.tarif_lab= false;
                        var.tarif_radiologi= false;
                        var.tarif_operasi= false;
                        var.akun_rekening= false;
                        var.rekening_tahun= false;
                        var.posting_jurnal= false;
                        var.buku_besar= false;
                        var.cashflow= false;
                        var.keuangan= false;
                        var.pengeluaran= false;
                        var.setup_pjlab= false;
                        var.setup_otolokasi= false;
                        var.setup_jam_kamin= false;
                        var.setup_embalase= false;
                        var.tracer_login= false;
                        var.display= false;
                        var.set_harga_obat= false;
                        var.set_penggunaan_tarif= false;
                        var.set_oto_ralan= false;
                        var.biaya_harian= false;
                        var.biaya_masuk_sekali= false;
                        var.set_no_rm= false;
                        var.billing_ralan=false;
                        var.billing_ranap=false;
                        var.jm_ranap_dokter=false;   
                        var.igd=false;   
                        var.barcoderalan=false; 
                        var.barcoderanap=false;
                        var.set_harga_obat_ralan=false; 
                        var.set_harga_obat_ranap=false;
                        var.admin= false;
                        var.user= false;
                        var.vakum= false;
                        var.aplikasi= false;
                        var.penyakit_pd3i=false;
                        var.surveilans_pd3i=false;
                        var.surveilans_ralan=false;
                        var.diagnosa_pasien=false;
                        var.surveilans_ranap=false;
                        var.pny_takmenular_ranap=false;
                        var.pny_takmenular_ralan=false;
                        var.kunjungan_ralan=false;
                        var.rl32=false;
                        var.rl33=false;
                        var.rl37=false;
                        var.rl38=false;
                        var.harian_tindakan_dokter=false;
                        var.sms=false;             
                        var.sidikjari=false;  
                        var.jam_masuk=false;  
                        var.jadwal_pegawai=false;   
                        var.parkir_barcode=false;
                        var.set_nota=false;
                        var.dpjp_ranap=false;
                        var.mutasi_barang=false;
                        var.rl34=false;
                        var.rl36=false;
                        var.fee_visit_dokter=false;
                        var.fee_bacaan_ekg=false;
                        var.fee_rujukan_rontgen=false;
                        var.fee_rujukan_ranap=false;
                        var.fee_ralan=false;
                        var.akun_bayar=false;
                        var.bayar_pemesanan_obat=false;
                        var.obat_per_dokter_peresep=false;
                        var.pemasukan_lain=false;
                        var.pengaturan_rekening=false;
                        var.closing_kasir=false;
                        var.keterlambatan_presensi=false; 
                        var.set_harga_kamar=false;
                        var.rekap_per_shift=false;
                        var.bpjs_cek_nik=false;
                        var.bpjs_cek_kartu=false;
                        var.bpjs_cek_riwayat=false;
                        var.obat_per_cara_bayar=false;
                        var.kunjungan_ranap=false;
                        var.bayar_piutang=false;
                        var.payment_point=false;
                        var.bpjs_cek_nomor_rujukan=false;
                        var.icd9=false;
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(rs2!=null){
                        rs2.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                    if(ps2!=null){
                        ps2.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }

    }
    
    public static int getjml1() {return var.jml1;}    
    public static int getjml2() {return var.jml2;}    
    public static boolean getadmin(){return var.admin;}        
    public static boolean getuser(){return var.user;} 
    public static boolean getvakum(){return var.vakum;} 
    public static boolean getaplikasi(){return var.aplikasi;} 
    public static boolean getpenyakit(){return var.penyakit;} 
    public static boolean getobat_penyakit(){return var.obat_penyakit;} 
    public static boolean getdokter(){return var.dokter;} 
    public static boolean getjadwal_praktek(){return var.jadwal_praktek;} 
    public static boolean getpetugas(){return var.petugas;} 
    public static boolean getpasien(){return var.pasien;} 
    public static boolean getregistrasi(){return var.registrasi;} 
    public static boolean gettindakan_ralan(){return var.tindakan_ralan;} 
    public static boolean getkamar_inap(){return var.kamar_inap;} 
    public static boolean gettindakan_ranap(){return var.tindakan_ranap;} 
    public static boolean getoperasi(){return var.operasi;} 
    public static boolean getrujukan_keluar(){return var.rujukan_keluar;} 
    public static boolean getrujukan_masuk(){return var.rujukan_masuk;} 
    public static boolean getberi_obat(){return var.beri_obat;} 
    public static boolean getresep_pulang(){return var.resep_pulang;} 
    public static boolean getpasien_meninggal(){return var.pasien_meninggal;} 
    public static boolean getdiet_pasien(){return var.diet_pasien;} 
    public static boolean getkelahiran_bayi(){return var.kelahiran_bayi;} 
    public static boolean getperiksa_lab(){return var.periksa_lab;} 
    public static boolean getperiksa_radiologi(){return var.periksa_radiologi;} 
    public static boolean getkasir_ralan(){return var.kasir_ralan;} 
    public static boolean getdeposit_pasien(){return var.deposit_pasien;} 
    public static boolean getpiutang_pasien(){return var.piutang_pasien;} 
    public static boolean getpeminjaman_berkas(){return var.peminjaman_berkas;} 
    public static boolean getbarcode(){return var.barcode;} 
    public static boolean getpresensi_harian(){return var.presensi_harian;} 
    public static boolean getpresensi_bulanan(){return var.presensi_bulanan;} 
    public static boolean getpegawai_admin(){return var.pegawai_admin;} 
    public static boolean getpegawai_user(){return var.pegawai_user;} 
    public static boolean getsuplier(){return var.suplier;} 
    public static boolean getsatuan_barang(){return var.satuan_barang;} 
    public static boolean getkonversi_satuan(){return var.konversi_satuan;} 
    public static boolean getjenis_barang(){return var.jenis_barang;} 
    public static boolean getobat(){return var.obat;} 
    public static boolean getstok_opname_obat(){return var.stok_opname_obat;} 
    public static boolean getstok_obat_pasien(){return var.stok_obat_pasien;} 
    public static boolean getpengadaan_obat(){return var.pengadaan_obat;} 
    public static boolean getpemesanan_obat(){return var.pemesanan_obat;} 
    public static boolean getpenjualan_obat(){return var.penjualan_obat;} 
    public static boolean getpiutang_obat(){return var.piutang_obat;} 
    public static boolean getretur_ke_suplier(){return var.retur_ke_suplier;} 
    public static boolean getretur_dari_pembeli(){return var.retur_dari_pembeli;} 
    public static boolean getretur_obat_ranap(){return var.retur_obat_ranap;} 
    public static boolean getretur_piutang_pasien(){return var.retur_piutang_pasien;} 
    public static boolean getkeuntungan_penjualan(){return var.keuntungan_penjualan;} 
    public static boolean getkeuntungan_beri_obat(){return var.keuntungan_beri_obat;} 
    public static boolean getsirkulasi_obat(){return var.sirkulasi_obat;} 
    public static boolean getipsrs_barang(){return var.ipsrs_barang;} 
    public static boolean getipsrs_pengadaan_barang(){return var.ipsrs_pengadaan_barang;} 
    public static boolean getipsrs_stok_keluar(){return var.ipsrs_stok_keluar;} 
    public static boolean getipsrs_rekap_pengadaan(){return var.ipsrs_rekap_pengadaan;} 
    public static boolean getipsrs_rekap_stok_keluar(){return var.ipsrs_rekap_stok_keluar;} 
    public static boolean getipsrs_pengeluaran_harian(){return var.ipsrs_pengeluaran_harian;} 
    public static boolean getipsrs_jenis_barang(){return var.ipsrs_jenis_barang;} 
    public static boolean getinventaris_jenis(){return var.inventaris_jenis;} 
    public static boolean getinventaris_kategori(){return var.inventaris_kategori;} 
    public static boolean getinventaris_merk(){return var.inventaris_merk;} 
    public static boolean getinventaris_ruang(){return var.inventaris_ruang;} 
    public static boolean getinventaris_produsen(){return var.inventaris_produsen;} 
    public static boolean getinventaris_koleksi(){return var.inventaris_koleksi;} 
    public static boolean getinventaris_inventaris(){return var.inventaris_inventaris;} 
    public static boolean getinventaris_sirkulasi(){return var.inventaris_sirkulasi;} 
    public static boolean getparkir_jenis(){return var.parkir_jenis;} 
    public static boolean getparkir_in(){return var.parkir_in;} 
    public static boolean getparkir_out(){return var.parkir_out;} 
    public static boolean getparkir_rekap_harian(){return var.parkir_rekap_harian;} 
    public static boolean getparkir_rekap_bulanan(){return var.parkir_rekap_bulanan;} 
    public static boolean getinformasi_kamar(){return var.informasi_kamar;} 
    public static boolean getharian_tindakan_poli(){return var.harian_tindakan_poli;} 
    public static boolean getobat_per_poli(){return var.obat_per_poli;} 
    public static boolean getobat_per_kamar(){return var.obat_per_kamar;} 
    public static boolean getobat_per_dokter_ralan(){return var.obat_per_dokter_ralan;} 
    public static boolean getobat_per_dokter_ranap(){return var.obat_per_dokter_ranap;} 
    public static boolean getharian_dokter(){return var.harian_dokter;} 
    public static boolean getbulanan_dokter(){return var.bulanan_dokter;} 
    public static boolean getharian_paramedis(){return var.harian_paramedis;} 
    public static boolean getbulanan_paramedis(){return var.bulanan_paramedis;} 
    public static boolean getpembayaran_ralan(){return var.pembayaran_ralan;} 
    public static boolean getpembayaran_ranap(){return var.pembayaran_ranap;} 
    public static boolean getrekap_pembayaran_ralan(){return var.rekap_pembayaran_ralan;} 
    public static boolean getrekap_pembayaran_ranap(){return var.rekap_pembayaran_ranap;} 
    public static boolean gettagihan_masuk(){return var.tagihan_masuk;} 
    public static boolean gettambahan_biaya(){return var.tambahan_biaya;} 
    public static boolean getpotongan_biaya(){return var.potongan_biaya;} 
    public static boolean getresep_obat(){return var.resep_obat;} 
    public static boolean getresume_pasien(){return var.resume_pasien;} 
    public static boolean getpenyakit_ralan(){return var.penyakit_ralan;} 
    public static boolean getpenyakit_ranap(){return var.penyakit_ranap;} 
    public static boolean getkamar(){return var.kamar;} 
    public static boolean gettarif_ralan(){return var.tarif_ralan;} 
    public static boolean gettarif_ranap(){return var.tarif_ranap;} 
    public static boolean gettarif_lab(){return var.tarif_lab;} 
    public static boolean gettarif_radiologi(){return var.tarif_radiologi;} 
    public static boolean gettarif_operasi(){return var.tarif_operasi;} 
    public static boolean getakun_rekening(){return var.akun_rekening;} 
    public static boolean getrekening_tahun(){return var.rekening_tahun;} 
    public static boolean getposting_jurnal(){return var.posting_jurnal;} 
    public static boolean getbuku_besar(){return var.buku_besar;} 
    public static boolean getcashflow(){return var.cashflow;} 
    public static boolean getkeuangan(){return var.keuangan;} 
    public static boolean getpengeluaran(){return var.pengeluaran;} 
    public static boolean getsetup_pjlab(){return var.setup_pjlab;} 
    public static boolean getsetup_otolokasi(){return var.setup_otolokasi;} 
    public static boolean getsetup_jam_kamin(){return var.setup_jam_kamin;} 
    public static boolean getsetup_embalase(){return var.setup_embalase;} 
    public static boolean gettracer_login(){return var.tracer_login;} 
    public static boolean getdisplay(){return var.display;} 
    public static boolean getset_harga_obat(){return var.set_harga_obat;} 
    public static boolean getset_penggunaan_tarif(){return var.set_penggunaan_tarif;} 
    public static boolean getset_oto_ralan(){return var.set_oto_ralan;} 
    public static boolean getbiaya_harian(){return var.biaya_harian;} 
    public static boolean getbiaya_masuk_sekali(){return var.biaya_masuk_sekali;} 
    public static boolean getset_no_rm(){return var.set_no_rm;} 
    public static boolean getbilling_ralan(){return var.billing_ralan;} 
    public static boolean getbilling_ranap(){return var.billing_ranap;}
    public static String getkode(){return var.kode;}
    public static void setkdbangsal(String kdbangsal){var.kdbangsal=kdbangsal;}
    public static String getkdbangsal(){return var.kdbangsal;}     
    public static void setform(String form){var.form=form;}
    public static String getform(){return var.form;}   
    public static void setstatus(boolean status){var.status=status;}
    public static boolean getstatus(){return var.status;}
    public static boolean getjm_ranap_dokter(){return var.jm_ranap_dokter;}     
    public static boolean getigd(){return var.igd;}     
    public static boolean getbarcoderalan(){return var.barcoderalan;}     
    public static boolean getbarcoderanap(){return var.barcoderanap;}    
    public static boolean getset_harga_obat_ralan(){return var.set_harga_obat_ralan;}  
    public static boolean getset_harga_obat_ranap(){return var.set_harga_obat_ranap;}  
    public static boolean getpenyakit_pd3i(){return var.penyakit_pd3i;}  
    public static boolean getsurveilans_pd3i(){return var.surveilans_pd3i;}  
    public static boolean getsurveilans_ralan(){return var.surveilans_ralan;}  
    public static boolean getdiagnosa_pasien(){return var.diagnosa_pasien;}  
    public static boolean getsurveilans_ranap(){return var.surveilans_ranap;}  
    public static boolean getpny_takmenular_ranap(){return var.pny_takmenular_ranap;}  
    public static boolean getpny_takmenular_ralan(){return var.pny_takmenular_ralan;}
    public static void setnamars(String namars){var.namars=namars;}
    public static void setalamatrs(String alamatrs){var.alamatrs=alamatrs;}
    public static void setkabupatenrs(String kabupatenrs){var.kabupatenrs=kabupatenrs;}
    public static void setpropinsirs(String propinsirs){var.propinsirs=propinsirs;}
    public static void setkontakrs(String kontakrs){var.kontakrs=kontakrs;}
    public static void setemailrs(String emailrs){var.emailrs=emailrs;}
    public static String getnamars(){return var.namars;}
    public static String getalamatrs(){return var.alamatrs;}
    public static String getkabupatenrs(){return var.kabupatenrs;}
    public static String getpropinsirs(){return var.propinsirs;}
    public static String getkontakrs(){return var.kontakrs;}
    public static String getemailrs(){return var.emailrs;}
    public static boolean getkunjungan_ralan(){return var.kunjungan_ralan;}
    public static boolean getrl32(){return var.rl32;}
    public static boolean getrl33(){return var.rl33;}
    public static boolean getrl37(){return var.rl37;}
    public static boolean getrl38(){return var.rl38;}
    public static boolean getharian_tindakan_dokter(){return var.harian_tindakan_dokter;}
    public static boolean getsms(){return var.sms;}
    public static boolean getsidikjari(){return var.sidikjari;} 
    public static boolean getjam_masuk(){return var.jam_masuk;}  
    public static boolean getjadwal_pegawai(){return var.jadwal_pegawai;}
    public static boolean getparkir_barcode(){return var.parkir_barcode;}
    public static boolean getset_nota(){return var.set_nota;}
    public static boolean getdpjp_ranap(){return var.dpjp_ranap;}
    public static boolean getmutasi_barang(){return var.mutasi_barang;}
    public static boolean getrl34(){return var.rl34;}
    public static boolean getrl36(){return var.rl36;}
    public static boolean getfee_visit_dokter(){return var.fee_visit_dokter;}
    public static boolean getfee_bacaan_ekg(){return var.fee_bacaan_ekg;}
    public static boolean getfee_rujukan_rontgen(){return var.fee_rujukan_rontgen;}
    public static boolean getfee_rujukan_ranap(){return var.fee_rujukan_ranap;}
    public static boolean getfee_ralan(){return var.fee_ralan;}
    public static boolean getakun_bayar(){return var.akun_bayar;}
    public static boolean getbayar_pemesanan_obat(){return var.bayar_pemesanan_obat;}
    public static boolean getobat_per_dokter_peresep(){return var.obat_per_dokter_peresep;}
    public static boolean getpemasukan_lain(){return var.pemasukan_lain;}
    public static boolean getpengaturan_rekening(){return var.pengaturan_rekening;}
    public static boolean getclosing_kasir(){return var.closing_kasir;}
    public static boolean getketerlambatan_presensi(){return var.keterlambatan_presensi;} 
    public static boolean getset_harga_kamar(){return var.set_harga_kamar;} 
    public static boolean getrekap_per_shift(){return var.rekap_per_shift;}
    public static boolean getbpjs_cek_nik(){return var.bpjs_cek_nik;} 
    public static boolean getbpjs_cek_kartu(){return var.bpjs_cek_kartu;} 
    public static boolean getbpjs_cek_riwayat(){return var.bpjs_cek_riwayat;} 
    public static boolean getobat_per_cara_bayar(){return var.obat_per_cara_bayar;} 
    public static boolean getkunjungan_ranap(){return var.kunjungan_ranap;} 
    public static boolean getbayar_piutang(){return var.bayar_piutang;} 
    public static boolean getpayment_point(){return var.payment_point;} 
    public static boolean getbpjs_cek_nomor_rujukan(){return var.bpjs_cek_nomor_rujukan;}
    public static boolean geticd9(){return var.icd9;}
}
