/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */


package setting;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public class DlgUpdateUser extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,jml=0;
    private boolean[] akses;
    private String[] menu;
    private boolean[] pilih;
    private boolean penyakit=false,obat_penyakit=false,dokter=false,jadwal_praktek=false,petugas=false,pasien=false,registrasi=false,tindakan_ralan=false,kamar_inap=false,
            tindakan_ranap=false,operasi=false,rujukan_keluar=false,rujukan_masuk=false,beri_obat=false,resep_pulang=false,pasien_meninggal=false,diet_pasien=false,kelahiran_bayi=false,
            periksa_lab=false,periksa_radiologi=false,kasir_ralan=false,deposit_pasien=false,piutang_pasien=false,peminjaman_berkas=false,barcode=false,presensi_harian=false,presensi_bulanan=false,
            pegawai_admin=false,pegawai_user=false,suplier=false,satuan_barang=false,konversi_satuan=false,jenis_barang=false,obat=false,stok_opname_obat=false,stok_obat_pasien=false,
            pengadaan_obat=false,pemesanan_obat=false,penjualan_obat=false,piutang_obat=false,retur_ke_suplier=false,retur_dari_pembeli=false,retur_obat_ranap=false,retur_piutang_pasien=false,
            keuntungan_penjualan=false,keuntungan_beri_obat=false,sirkulasi_obat=false,ipsrs_barang=false,ipsrs_pengadaan_barang=false,ipsrs_stok_keluar=false,ipsrs_rekap_pengadaan=false,
            ipsrs_rekap_stok_keluar=false,ipsrs_pengeluaran_harian=false,inventaris_jenis=false,inventaris_kategori=false,inventaris_merk=false,inventaris_ruang=false,inventaris_produsen=false,
            inventaris_koleksi=false,inventaris_inventaris=false,inventaris_sirkulasi=false,parkir_jenis=false,parkir_in=false,parkir_out=false,parkir_rekap_harian=false,parkir_rekap_bulanan=false,
            informasi_kamar=false,harian_tindakan_poli=false,obat_per_poli=false,obat_per_kamar=false,obat_per_dokter_ralan=false,obat_per_dokter_ranap=false,harian_dokter=false,bulanan_dokter=false,
            harian_paramedis=false,bulanan_paramedis=false,pembayaran_ralan=false,pembayaran_ranap=false,rekap_pembayaran_ralan=false,rekap_pembayaran_ranap=false,tagihan_masuk=false,
            tambahan_biaya=false,potongan_biaya=false,resep_obat=false,resume_pasien=false,penyakit_ralan=false,penyakit_ranap=false,kamar=false,tarif_ralan=false,tarif_ranap=false,tarif_lab=false,
            tarif_radiologi=false,tarif_operasi=false,akun_rekening=false,rekening_tahun=false,posting_jurnal=false,buku_besar=false,cashflow=false,keuangan=false,pengeluaran=false,setup_pjlab=false,
            setup_otolokasi=false,setup_jam_kamin=false,setup_embalase=false,tracer_login=false,display=false,set_harga_obat=false,set_penggunaan_tarif=false,set_oto_ralan=false,biaya_harian=false,
            biaya_masuk_sekali=false,set_no_rm=false,billing_ralan=false,billing_ranap=false,jm_ranap_dokter=false,igd=false,barcoderalan=false,barcoderanap=false,set_harga_obat_ralan=false,
            set_harga_obat_ranap=false,penyakit_pd3i=false,surveilans_pd3i=false,surveilans_ralan=false,diagnosa_pasien=false,surveilans_ranap=false,pny_takmenular_ranap=false,
            pny_takmenular_ralan=false,kunjungan_ralan=false,rl32=false,rl33=false,rl37=false,rl38=false,harian_tindakan_dokter=false,sms=false,sidikjari=false,jam_masuk=false,
            jadwal_pegawai=false,parkir_barcode=false,set_nota=false,dpjp_ranap=false,mutasi_barang=false,rl34=false,rl36=false,fee_visit_dokter=false,fee_bacaan_ekg=false,fee_rujukan_rontgen=false,
            fee_rujukan_ranap=false,fee_ralan=false,akun_bayar=false,bayar_pemesanan_obat=false,obat_per_dokter_peresep=false,ipsrs_jenis_barang=false,pemasukan_lain=false,pengaturan_rekening=false,
            closing_kasir=false,keterlambatan_presensi=false,set_harga_kamar=false,rekap_per_shift=false,bpjs_cek_nik=false,bpjs_cek_kartu=false,bpjs_cek_riwayat=false,obat_per_cara_bayar=false,
            kunjungan_ranap=false,bayar_piutang=false,payment_point=false,bpjs_cek_nomor_rujukan=false,icd9=false,darurat_stok=false,retensi_rm=false,temporary_presensi=false,jurnal_harian=false,
            sirkulasi_obat2=false,edit_registrasi=false,bpjs_referensi_diagnosa=false,bpjs_referensi_poli=false,industrifarmasi=false,harian_js=false,bulanan_js=false,harian_paket_bhp=false,
            bulanan_paket_bhp=false,piutang_pasien2=false,bpjs_referensi_faskes=false,bpjs_sep=false,pengambilan_utd=false,tarif_utd=false,pengambilan_utd2=false,utd_medis_rusak=false,
            pengambilan_penunjang_utd=false,pengambilan_penunjang_utd2=false,utd_penunjang_rusak=false,suplier_penunjang=false,utd_donor=false,bpjs_monitoring_klaim=false,utd_cekal_darah=false,
            utd_komponen_darah=false,utd_stok_darah=false,utd_pemisahan_darah=false,harian_kamar=false,rincian_piutang_pasien=false,keuntungan_beri_obat_nonpiutang=false,reklasifikasi_ralan=false,
            reklasifikasi_ranap=false,utd_penyerahan_darah=false,hutang_obat=false,riwayat_obat_alkes_bhp=false,sensus_harian_poli=false,rl4a=false,aplicare_referensi_kamar=false,
            aplicare_ketersediaan_kamar=false,inacbg_klaim_baru_otomatis=false,inacbg_klaim_baru_manual=false,inacbg_coder_nik=false,mutasi_berkas=false,akun_piutang=false,harian_kso=false,
            bulanan_kso=false,harian_menejemen=false,bulanan_menejemen=false,inhealth_cek_eligibilitas=false,inhealth_referensi_jenpel_ruang_rawat=false,inhealth_referensi_poli=false,
            inhealth_referensi_faskes=false,inhealth_sjp=false,piutang_ralan=false,piutang_ranap=false,detail_piutang_penjab=false,lama_pelayanan_ralan=false,catatan_pasien=false,
            rl4b=false,rl4asebab=false,rl4bsebab=false,data_HAIs=false,harian_HAIs=false,bulanan_HAIs=false,hitung_bor=false,perusahaan_pasien=false,resep_dokter=false,lama_pelayanan_apotek=false,
            hitung_alos=false,detail_tindakan=false,rujukan_poli_internal=false,rekap_poli_anak=false,grafik_kunjungan_poli=false,grafik_kunjungan_perdokter=false,grafik_kunjungan_perpekerjaan=false,
            grafik_kunjungan_perpendidikan=false,grafik_kunjungan_pertahun=false,berkas_digital_perawatan=false,penyakit_menular_ranap=false,penyakit_menular_ralan=false,grafik_kunjungan_perbulan=false,
            grafik_kunjungan_pertanggal=false,grafik_kunjungan_demografi=false,grafik_kunjungan_statusdaftartahun=false,grafik_kunjungan_statusdaftartahun2=false,grafik_kunjungan_statusdaftarbulan=false,
            grafik_kunjungan_statusdaftarbulan2=false,grafik_kunjungan_statusdaftartanggal=false,grafik_kunjungan_statusdaftartanggal2=false,grafik_kunjungan_statusbataltahun=false,
            grafik_kunjungan_statusbatalbulan=false,pcare_cek_penyakit=false,grafik_kunjungan_statusbataltanggal=false,kategori_barang=false,golongan_barang=false,pemberian_obat_pertanggal=false,
            penjualan_obat_pertanggal=false,pcare_cek_kesadaran=false,pembatalan_periksa_dokter=false,pembayaran_per_unit=false,rekap_pembayaran_per_unit=false,grafik_kunjungan_percarabayar=false,
            ipsrs_pengadaan_pertanggal=false,ipsrs_stokkeluar_pertanggal=false,grafik_kunjungan_ranaptahun=false,pcare_cek_rujukan=false,grafik_lab_ralantahun=false,grafik_rad_ralantahun=false,
            cek_entry_ralan=false,inacbg_klaim_baru_manual2=false,permintaan_medis=false,rekap_permintaan_medis=false,surat_pemesanan_medis=false,permintaan_non_medis=false,rekap_permintaan_non_medis=false,
            surat_pemesanan_non_medis=false,grafik_per_perujuk=false,bpjs_cek_prosedur=false,bpjs_cek_kelas_rawat=false,bpjs_cek_dokter=false,bpjs_cek_spesialistik=false,bpjs_cek_ruangrawat=false,
            bpjs_cek_carakeluar=false,bpjs_cek_pasca_pulang=false,detail_tindakan_okvk=false,billing_parsial=false,bpjs_cek_nomor_rujukan_rs=false,bpjs_cek_rujukan_kartu_pcare=false,
            bpjs_cek_rujukan_kartu_rs=false,akses_depo_obat=false,bpjs_rujukan_keluar=false,grafik_lab_ralanbulan=false,pengeluaran_stok_apotek=false,grafik_rad_ralanbulan=false,detailjmdokter2=false,
            pengaduan_pasien=false,grafik_lab_ralanhari=false,grafik_rad_ralanhari=false,sensus_harian_ralan=false,metode_racik=false,pembayaran_akun_bayar=false,pengguna_obat_resep=false,
            rekap_pemesanan=false,master_berkas_pegawai=false,berkas_kepegawaian=false,riwayat_jabatan=false,riwayat_pendidikan=false,riwayat_naik_gaji=false,kegiatan_ilmiah=false,riwayat_penghargaan=false,
            riwayat_penelitian=false,penerimaan_non_medis=false,bayar_pesan_non_medis=false,hutang_barang_non_medis=false,rekap_pemesanan_non_medis=false,insiden_keselamatan=false,
            insiden_keselamatan_pasien=false,grafik_ikp_pertahun=false,grafik_ikp_perbulan=false,grafik_ikp_pertanggal=false,riwayat_data_batch=false,grafik_ikp_jenis=false,grafik_ikp_dampak=false,
            piutang_akun_piutang=false,grafik_kunjungan_per_agama=false,grafik_kunjungan_per_umur=false,suku_bangsa=false,bahasa_pasien=false,golongan_tni=false,satuan_tni=false,jabatan_tni=false,
            pangkat_tni=false,golongan_polri=false,satuan_polri=false,jabatan_polri=false,pangkat_polri=false,cacat_fisik=false,grafik_kunjungan_suku=false,grafik_kunjungan_bahasa=false,
            booking_operasi=false,mapping_poli_bpjs=false,grafik_kunjungan_per_cacat=false,barang_cssd=false,skdp_bpjs=false,booking_registrasi=false,bpjs_cek_propinsi=false,bpjs_cek_kabupaten=false,
            bpjs_cek_kecamatan=false,bpjs_cek_dokterdpjp=false,bpjs_cek_riwayat_rujukanrs=false,bpjs_cek_tanggal_rujukan=false,permintaan_lab=false,permintaan_radiologi=false,surat_indeks=false,
            surat_map=false,surat_almari=false,surat_rak=false,surat_ruang=false,surat_klasifikasi=false,surat_status=false,surat_sifat=false,surat_balas=false,surat_masuk=false,pcare_cek_dokter=false,
            pcare_cek_poli=false,pcare_cek_provider=false,pcare_cek_statuspulang=false,pcare_cek_spesialis=false,pcare_cek_subspesialis=false,pcare_cek_sarana=false,pcare_cek_khusus=false,
            pcare_cek_obat=false,pcare_cek_tindakan=false,pcare_cek_faskessubspesialis=false,pcare_cek_faskesalihrawat=false,pcare_cek_faskesthalasemia=false,pcare_mapping_obat=false,
            pcare_mapping_tindakan=false,pcare_club_prolanis=false,pcare_mapping_poli=false,pcare_kegiatan_kelompok=false,pcare_mapping_tindakan_ranap=false,pcare_peserta_kegiatan_kelompok=false,
            sirkulasi_obat3=false,bridging_pcare_daftar=false,pcare_mapping_dokter=false,ranap_per_ruang=false,penyakit_ranap_cara_bayar=false,anggota_militer_dirawat=false,set_input_parsial=false,
            lama_pelayanan_radiologi=false,lama_pelayanan_lab=false,bpjs_cek_sep=false,catatan_perawatan=false,surat_keluar=false,kegiatan_farmasi=false,stok_opname_logistik=false,sirkulasi_non_medis=false,
            rekap_lab_pertahun=false,perujuk_lab_pertahun=false,rekap_radiologi_pertahun=false,perujuk_radiologi_pertahun=false,jumlah_porsi_diet=false,jumlah_macam_diet=false,payment_point2=false,
            pembayaran_akun_bayar2=false,hapus_nota_salah=false,pengkajian_askep=false,hais_perbangsal=false,ppn_obat=false,saldo_akun_perbulan=false,display_apotek=false,sisrute_referensi_faskes=false,
            sisrute_referensi_alasanrujuk=false,sisrute_referensi_diagnosa=false,sisrute_rujukan_masuk=false,sisrute_rujukan_keluar=false,bpjs_cek_skdp=false,data_batch=false,kunjungan_permintaan_lab=false,
            kunjungan_permintaan_lab2=false,kunjungan_permintaan_radiologi=false,kunjungan_permintaan_radiologi2=false,pcare_pemberian_obat=false,pcare_pemberian_tindakan=false,pembayaran_akun_bayar3=false,
            password_asuransi=false,kemenkes_sitt=false,siranap_ketersediaan_kamar=false,grafik_tb_periodelaporan=false,grafik_tb_rujukan=false,grafik_tb_riwayat=false,grafik_tb_tipediagnosis=false,
            grafik_tb_statushiv=false,grafik_tb_skoringanak=false,grafik_tb_konfirmasiskoring5=false,grafik_tb_konfirmasiskoring6=false,grafik_tb_sumberobat=false,grafik_tb_hasilakhirpengobatan=false,
            grafik_tb_hasilteshiv=false,kadaluarsa_batch=false,sisa_stok=false,obat_per_resep=false,pemakaian_air_pdam=false,limbah_b3_medis=false,grafik_air_pdam_pertanggal=false,grafik_air_pdam_perbulan=false,
            grafik_limbahb3_pertanggal=false,grafik_limbahb3_perbulan=false,limbah_domestik=false,grafik_limbahdomestik_pertanggal=false,grafik_limbahdomestik_perbulan=false,mutu_air_limbah=false,
            pest_control=false,ruang_perpustakaan=false,kategori_perpustakaan=false,jenis_perpustakaan=false,pengarang_perpustakaan=false,penerbit_perpustakaan=false,koleksi_perpustakaan=false,
            inventaris_perpustakaan=false,set_peminjaman_perpustakaan=false,denda_perpustakaan=false,anggota_perpustakaan=false,peminjaman_perpustakaan=false,bayar_denda_perpustakaan=false,
            ebook_perpustakaan=false,jenis_cidera_k3rs=false,penyebab_k3rs=false,jenis_luka_k3rs=false,lokasi_kejadian_k3rs=false,dampak_cidera_k3rs=false,jenis_pekerjaan_k3rs=false,bagian_tubuh_k3rs=false,
            peristiwa_k3rs=false,grafik_k3_pertahun=false,grafik_k3_perbulan=false,grafik_k3_pertanggal=false,grafik_k3_perjeniscidera=false,grafik_k3_perpenyebab=false,grafik_k3_perjenisluka=false,
            grafik_k3_lokasikejadian=false,grafik_k3_dampakcidera=false,grafik_k3_perjenispekerjaan=false,grafik_k3_perbagiantubuh=false,jenis_cidera_k3rstahun=false,penyebab_k3rstahun=false,
            jenis_luka_k3rstahun=false,lokasi_kejadian_k3rstahun=false,dampak_cidera_k3rstahun=false,jenis_pekerjaan_k3rstahun=false,bagian_tubuh_k3rstahun=false,sekrining_rawat_jalan=false,
            bpjs_histori_pelayanan=false,rekap_mutasi_berkas=false,skrining_ralan_pernapasan_pertahun=false,pengajuan_barang_medis=false,pengajuan_barang_nonmedis=false,grafik_kunjungan_ranapbulan=false,
            grafik_kunjungan_ranaptanggal=false,grafik_kunjungan_ranap_peruang=false,kunjungan_bangsal_pertahun=false,grafik_jenjang_jabatanpegawai=false,grafik_bidangpegawai=false,grafik_departemenpegawai=false,
            grafik_pendidikanpegawai=false,grafik_sttswppegawai=false,grafik_sttskerjapegawai=false,grafik_sttspulangranap=false,kip_pasien_ranap=false,kip_pasien_ralan=false,bpjs_mapping_dokterdpjp=false,
            data_triase_igd=false,master_triase_skala1=false,master_triase_skala2=false,master_triase_skala3=false,master_triase_skala4=false,master_triase_skala5=false,master_triase_pemeriksaan=false,
            master_triase_macamkasus=false,rekap_permintaan_diet=false,daftar_pasien_ranap=false,daftar_pasien_ranaptni=false,pengajuan_asetinventaris=false,item_apotek_jenis=false,item_apotek_kategori=false,
            item_apotek_golongan=false,item_apotek_industrifarmasi=false,sepuluh_obat_terbanyak_poli=false,grafik_pengajuan_aset_urgensi=false,grafik_pengajuan_aset_status=false,grafik_pengajuan_aset_departemen=false,
            rekap_pengajuan_aset_departemen=false,grafik_kelompok_jabatanpegawai=false,grafik_resiko_kerjapegawai=false,grafik_emergency_indexpegawai=false,grafik_inventaris_ruang=false,harian_HAIs2=false,
            grafik_inventaris_jenis=false,data_resume_pasien=false,perkiraan_biaya_ranap=false,rekap_obat_poli=false,rekap_obat_pasien=false,permintaan_perbaikan_inventaris=false,grafik_HAIs_pasienbangsal=false,
            grafik_HAIs_pasienbulan=false,grafik_HAIs_laju_vap=false,grafik_HAIs_laju_iad=false,grafik_HAIs_laju_pleb=false,grafik_HAIs_laju_isk=false,grafik_HAIs_laju_ilo=false,grafik_HAIs_laju_hap=false,
            inhealth_mapping_poli=false,inhealth_mapping_dokter=false,inhealth_mapping_tindakan_ralan=false,inhealth_mapping_tindakan_ranap=false,inhealth_mapping_tindakan_radiologi=false,
            inhealth_mapping_tindakan_laborat=false,inhealth_mapping_tindakan_operasi=false,hibah_obat_bhp=false,asal_hibah=false,asuhan_gizi=false,inhealth_kirim_tagihan=false,sirkulasi_obat4=false,
            sirkulasi_obat5=false,sirkulasi_non_medis2=false,monitoring_asuhan_gizi=false,penerimaan_obat_perbulan=false,rekap_kunjungan=false,surat_sakit=false,penilaian_awal_keperawatan_ralan=false,
            permintaan_diet=false,master_masalah_keperawatan=false,pengajuan_cuti=false,kedatangan_pasien=false,utd_pendonor=false,toko_suplier=false,toko_jenis=false,toko_set_harga=false,toko_barang=false,
            penagihan_piutang_pasien=false,akun_penagihan_piutang=false,stok_opname_toko=false,toko_riwayat_barang=false,toko_surat_pemesanan=false,toko_pengajuan_barang=false,toko_penerimaan_barang=false,
            toko_pengadaan_barang=false,toko_hutang=false,toko_bayar_pemesanan=false,toko_member=false,toko_penjualan=false,registrasi_poli_per_tanggal=false,toko_piutang=false,toko_retur_beli=false,
            ipsrs_returbeli=false,ipsrs_riwayat_barang=false,pasien_corona=false,toko_pendapatan_harian=false,diagnosa_pasien_corona=false,perawatan_pasien_corona=false,penilaian_awal_keperawatan_gigi=false,
            master_masalah_keperawatan_gigi=false,toko_bayar_piutang=false,toko_piutang_harian=false,toko_penjualan_harian=false,deteksi_corona=false,penilaian_awal_keperawatan_kebidanan=false,pengumuman_epasien=false,
            surat_hamil=false,set_tarif_online=false,booking_periksa=false,toko_sirkulasi=false,toko_retur_jual=false,toko_retur_piutang=false,toko_sirkulasi2=false,toko_keuntungan_barang=false,
            zis_pengeluaran_penerima_dankes=false,zis_penghasilan_penerima_dankes=false,zis_ukuran_rumah_penerima_dankes=false,zis_dinding_rumah_penerima_dankes=false,zis_lantai_rumah_penerima_dankes=false,
            zis_atap_rumah_penerima_dankes=false,zis_kepemilikan_rumah_penerima_dankes=false,zis_kamar_mandi_penerima_dankes=false,zis_dapur_rumah_penerima_dankes=false,zis_kursi_rumah_penerima_dankes=false,
            zis_kategori_phbs_penerima_dankes=false,zis_elektronik_penerima_dankes=false,zis_ternak_penerima_dankes=false,zis_jenis_simpanan_penerima_dankes=false,penilaian_awal_keperawatan_anak=false,
            zis_kategori_asnaf_penerima_dankes=false,master_masalah_keperawatan_anak=false,master_imunisasi=false,zis_patologis_penerima_dankes=false,pcare_cek_kartu=false,surat_bebas_narkoba=false,
            surat_keterangan_covid=false,pemakaian_air_tanah=false,grafik_air_tanah_pertanggal=false,grafik_air_tanah_perbulan=false,lama_pelayanan_poli=false,hemodialisa=false,laporan_tahunan_irj=false,
            grafik_harian_hemodialisa=false,grafik_bulanan_hemodialisa=false,grafik_tahunan_hemodialisa=false,grafik_bulanan_meninggal=false,perbaikan_inventaris=false,surat_cuti_hamil=false,
            permintaan_stok_obat_pasien=false,pemeliharaan_inventaris=false,klasifikasi_pasien_ranap=false,bulanan_klasifikasi_pasien_ranap=false,harian_klasifikasi_pasien_ranap=false,klasifikasi_pasien_perbangsal=false,
            soap_perawatan=false,klaim_rawat_jalan=false,skrining_gizi=false,lama_penyiapan_rm=false,dosis_radiologi=false,demografi_umur_kunjungan=false,jam_diet_pasien=false,rvu_bpjs=false,
            verifikasi_penerimaan_farmasi=false,verifikasi_penerimaan_logistik=false,pemeriksaan_lab_pa=false,ringkasan_pengajuan_obat=false,ringkasan_pemesanan_obat=false,ringkasan_pengadaan_obat=false,
            ringkasan_penerimaan_obat=false,ringkasan_hibah_obat=false,ringkasan_penjualan_obat=false,ringkasan_beri_obat=false,ringkasan_piutang_obat=false,ringkasan_stok_keluar_obat=false,
            ringkasan_retur_suplier_obat=false,ringkasan_retur_pembeli_obat=false,penilaian_awal_keperawatan_ranapkebidanan=false,ringkasan_pengajuan_nonmedis=false,ringkasan_pemesanan_nonmedis=false,
            ringkasan_pengadaan_nonmedis=false,ringkasan_penerimaan_nonmedis=false,ringkasan_stokkeluar_nonmedis=false,ringkasan_returbeli_nonmedis=false,omset_penerimaan=false,validasi_penagihan_piutang=false,
            permintaan_ranap=false,bpjs_diagnosa_prb=false,bpjs_obat_prb=false,bpjs_surat_kontrol=false,penggunaan_bhp_ok=false,surat_keterangan_rawat_inap=false,surat_keterangan_sehat=false,pendapatan_per_carabayar=false,
            akun_host_to_host_bank_jateng=false,pembayaran_bank_jateng=false,bpjs_surat_pri=false,ringkasan_tindakan=false,lama_pelayanan_pasien=false,surat_sakit_pihak_2=false,tagihan_hutang_obat=false,
            referensi_mobilejkn_bpjs=false,batal_pendaftaran_mobilejkn_bpjs=false,lama_operasi=false,grafik_inventaris_kategori=false,grafik_inventaris_merk=false,grafik_inventaris_produsen=false,
            pengembalian_deposit_pasien=false,validasi_tagihan_hutang_obat=false,piutang_obat_belum_lunas=false,integrasi_briapi=false,pengadaan_aset_inventaris=false,akun_aset_inventaris=false,suplier_inventaris=false,
            penerimaan_aset_inventaris=false,bayar_pemesanan_iventaris=false,hutang_aset_inventaris=false,hibah_aset_inventaris=false,titip_faktur_non_medis=false,validasi_tagihan_non_medis=false,titip_faktur_aset=false,
            validasi_tagihan_aset=false,hibah_non_medis=false,pcare_alasan_tacc=false,resep_luar=false,surat_bebas_tbc=false,surat_buta_warna=false,surat_bebas_tato=false,surat_kewaspadaan_kesehatan=false,
            grafik_porsidiet_pertanggal=false,grafik_porsidiet_perbulan=false,grafik_porsidiet_pertahun=false,grafik_porsidiet_perbangsal=false,penilaian_awal_medis_ralan=false,master_masalah_keperawatan_mata=false,
            penilaian_awal_keperawatan_mata=false,penilaian_awal_medis_ranap=false,penilaian_awal_medis_ranap_kebidanan=false,penilaian_awal_medis_ralan_kebidanan=false,penilaian_awal_medis_igd=false,
            penilaian_awal_medis_ralan_anak=false,bpjs_referensi_poli_hfis=false,bpjs_referensi_dokter_hfis=false,bpjs_referensi_jadwal_hfis=false,penilaian_fisioterapi=false,bpjs_program_prb=false,
            bpjs_suplesi_jasaraharja=false,bpjs_data_induk_kecelakaan=false,bpjs_sep_internal=false,bpjs_klaim_jasa_raharja=false,bpjs_daftar_finger_print=false,bpjs_rujukan_khusus=false,pemeliharaan_gedung=false,
            grafik_perbaikan_inventaris_pertanggal=false,grafik_perbaikan_inventaris_perbulan=false,grafik_perbaikan_inventaris_pertahun=false,grafik_perbaikan_inventaris_perpelaksana_status=false,penilaian_mcu=false,
            peminjam_piutang=false,piutang_lainlain=false,cara_bayar=false,audit_kepatuhan_apd=false,bpjs_task_id=false,bayar_piutang_lain=false,pembayaran_akun_bayar4=false,stok_akhir_farmasi_pertanggal=false,
            riwayat_kamar_pasien=false,uji_fungsi_kfr=false,hapus_berkas_digital_perawatan=false,kategori_pengeluaran_harian=false,kategori_pemasukan_lain=false,pembayaran_akun_bayar5=false,ruang_ok=false,telaah_resep=false,
            jasa_tindakan_pasien=false,permintaan_resep_pulang=false,rekap_jm_dokter=false,status_data_rm=false,ubah_petugas_lab_pk=false,ubah_petugas_lab_pa=false,ubah_petugas_radiologi=false,gabung_norawat=false,
            gabung_rm=false,ringkasan_biaya_obat_pasien_pertanggal=false,master_masalah_keperawatan_igd=false,penilaian_awal_keperawatan_igd=false,bpjs_referensi_dpho_apotek=false,bpjs_referensi_poli_apotek=false,
            bayar_jm_dokter=false,bpjs_referensi_faskes_apotek=false,bpjs_referensi_spesialistik_apotek=false,pembayaran_briva=false,penilaian_awal_keperawatan_ranap=false,nilai_penerimaan_vendor_farmasi_perbulan=false,
            akun_bayar_hutang=false,master_rencana_keperawatan=false,laporan_tahunan_igd=false,obat_bhp_tidakbergerak=false,ringkasan_hutang_vendor_farmasi=false,nilai_penerimaan_vendor_nonmedis_perbulan=false,
            ringkasan_hutang_vendor_nonmedis=false,master_rencana_keperawatan_anak=false,anggota_polri_dirawat=false,daftar_pasien_ranap_polri=false,soap_ralan_polri=false,soap_ranap_polri=false,laporan_penyakit_polri=false,
            jumlah_pengunjung_ralan_polri=false,catatan_observasi_igd=false,catatan_observasi_ranap=false,catatan_observasi_ranap_kebidanan=false,catatan_observasi_ranap_postpartum=false,penilaian_awal_medis_ralan_tht=false,
            penilaian_psikologi=false,audit_cuci_tangan_medis=false,audit_pembuangan_limbah=false,ruang_audit_kepatuhan=false,audit_pembuangan_benda_tajam=false,audit_penanganan_darah=false,audit_pengelolaan_linen_kotor=false,
            audit_penempatan_pasien=false,audit_kamar_jenazah=false,audit_bundle_iadp=false,audit_bundle_ido=false,audit_fasilitas_kebersihan_tangan=false,audit_fasilitas_apd=false,audit_pembuangan_limbah_cair_infeksius=false,
            audit_sterilisasi_alat=false,penilaian_awal_medis_ralan_psikiatri=false,persetujuan_penolakan_tindakan=false,audit_bundle_isk=false,audit_bundle_plabsi=false,audit_bundle_vap=false,akun_host_to_host_bank_papua=false,
            pembayaran_bank_papua=false,penilaian_awal_medis_ralan_penyakit_dalam=false,penilaian_awal_medis_ralan_mata=false,penilaian_awal_medis_ralan_neurologi=false,sirkulasi_obat6=false,penilaian_awal_medis_ralan_orthopedi=false,
            penilaian_awal_medis_ralan_bedah=false,integrasi_khanza_health_services=false,soap_ralan_tni=false,soap_ranap_tni=false,jumlah_pengunjung_ralan_tni=false,laporan_penyakit_tni=false,catatan_keperawatan_ranap=false,
            master_rencana_keperawatan_gigi=false,master_rencana_keperawatan_mata=false,master_rencana_keperawatan_igd=false,master_masalah_keperawatan_psikiatri=false,master_rencana_keperawatan_psikiatri=false,
            penilaian_awal_keperawatan_psikiatri=false,pemantauan_pews_anak=false,surat_pulang_atas_permintaan_sendiri=false,template_hasil_radiologi=false,laporan_bulanan_irj=false,template_pemeriksaan=false,pemeriksaan_lab_mb=false,
            ubah_petugas_lab_mb=false,penilaian_pre_operasi=false,penilaian_pre_anestesi=false,perencanaan_pemulangan=false,penilaian_lanjutan_resiko_jatuh_dewasa=false,penilaian_lanjutan_resiko_jatuh_anak=false,
            penilaian_awal_medis_ralan_geriatri=false,penilaian_tambahan_pasien_geriatri=false,skrining_nutrisi_dewasa=false,skrining_nutrisi_lansia=false,hasil_pemeriksaan_usg=false,skrining_nutrisi_anak=false,
            akun_host_to_host_bank_jabar=false,pembayaran_bank_jabar=false,surat_pernyataan_pasien_umum=false,konseling_farmasi=false,pelayanan_informasi_obat=false,jawaban_pio_apoteker=false,surat_persetujuan_umum=false,
            transfer_pasien_antar_ruang=false,satu_sehat_referensi_dokter=false,satu_sehat_referensi_pasien=false,satu_sehat_mapping_departemen=false,satu_sehat_mapping_lokasi=false,satu_sehat_kirim_encounter=false,
            catatan_cek_gds=false,satu_sehat_kirim_condition=false,checklist_pre_operasi=false,satu_sehat_kirim_observationttv=false,signin_sebelum_anestesi=false,satu_sehat_kirim_procedure=false,operasi_per_bulan=false,
            timeout_sebelum_insisi=false,signout_sebelum_menutup_luka=false,dapur_barang=false,dapur_opname=false,satu_sehat_mapping_vaksin=false,dapur_suplier=false,satu_sehat_kirim_Immunization=false,checklist_post_operasi=false,
            dapur_pembelian=false,dapur_stok_keluar=false,dapur_riwayat_barang=false,permintaan_dapur=false,rekonsiliasi_obat=false,biaya_pengadaan_dapur=false,rekap_pengadaan_dapur=false,kesling_limbah_b3medis_cair=false,
            grafik_limbahb3cair_pertanggal=false,grafik_limbahb3cair_perbulan=false,rekap_biaya_registrasi=false,konfirmasi_rekonsiliasi_obat=false,satu_sehat_kirim_clinicalimpression=false,penilaian_pasien_terminal=false,
            surat_persetujuan_rawat_inap=false,monitoring_reaksi_tranfusi=false,penilaian_korban_kekerasan=false,penilaian_lanjutan_resiko_jatuh_lansia=false,penilaian_pasien_penyakit_menular=false,mpp_skrining=false,
            edukasi_pasien_keluarga_rj=false,pemantauan_pews_dewasa=false,penilaian_tambahan_bunuh_diri=false,bpjs_antrean_pertanggal=false,penilaian_tambahan_perilaku_kekerasan=false,penilaian_tambahan_beresiko_melarikan_diri=false,
            persetujuan_penundaan_pelayanan=false,sisa_diet_pasien=false,penilaian_awal_medis_ralan_bedah_mulut=false,penilaian_pasien_keracunan=false,pemantauan_meows_obstetri=false,catatan_adime_gizi=false,pengajuan_biaya=false,
            penilaian_awal_keperawatan_ralan_geriatri=false,master_masalah_keperawatan_geriatri=false,master_rencana_keperawatan_geriatri=false,checklist_kriteria_masuk_hcu=false,checklist_kriteria_keluar_hcu=false,
            penilaian_risiko_dekubitus=false,master_menolak_anjuran_medis=false,penolakan_anjuran_medis=false,laporan_tahunan_penolakan_anjuran_medis=false,template_laporan_operasi=false,hasil_tindakan_eswl=false,
            checklist_kriteria_masuk_icu=false,checklist_kriteria_keluar_icu=false,akses_dokter_lain_rawat_jalan=false,follow_up_dbd=false,penilaian_risiko_jatuh_neonatus=false,persetujuan_pengajuan_biaya=false,
            pemeriksaan_fisik_ralan_per_penyakit=false,penilaian_lanjutan_resiko_jatuh_geriatri=false,pemantauan_ews_neonatus=false,validasi_persetujuan_pengajuan_biaya=false,riwayat_perawatan_icare_bpjs=false,
            rekap_pengajuan_biaya=false,penilaian_awal_medis_ralan_kulit_kelamin=false,akun_host_to_host_bank_mandiri=false,penilaian_medis_hemodialisa=false,penilaian_level_kecemasan_ranap_anak=false,
            penilaian_lanjutan_resiko_jatuh_psikiatri=false,penilaian_lanjutan_skrining_fungsional=false,penilaian_medis_ralan_rehab_medik=false,laporan_anestesi=false,template_persetujuan_penolakan_tindakan=false,
            penilaian_medis_ralan_gawat_darurat_psikiatri=false,bpjs_referensi_setting_apotek=false,bpjs_referensi_obat_apotek=false,bpjs_mapping_obat_apotek=false,pembayaran_bank_mandiri=false,penilaian_ulang_nyeri=false,
            penilaian_terapi_wicara=false,bpjs_obat_23hari_apotek=false,pengkajian_restrain=false,bpjs_kunjungan_sep_apotek=false,bpjs_monitoring_klaim_apotek=false,bpjs_daftar_pelayanan_obat_apotek=false,
            penilaian_awal_medis_ralan_paru=false,catatan_keperawatan_ralan=false,catatan_persalinan=false,skor_aldrette_pasca_anestesi=false,skor_steward_pasca_anestesi=false,skor_bromage_pasca_anestesi=false,
            penilaian_pre_induksi=false,hasil_usg_urologi=false,hasil_usg_gynecologi=false,hasil_pemeriksaan_ekg=false,hapus_edit_sep_bpjs=false,satu_sehat_kirim_diet=false,satu_sehat_mapping_obat=false,dapur_ringkasan_pembelian=false,
            satu_sehat_kirim_medication=false,satu_sehat_kirim_medicationrequest=false,penatalaksanaan_terapi_okupasi=false,satu_sehat_kirim_medicationdispense=false,hasil_usg_neonatus=false,hasil_endoskopi_faring_laring=false,
            satu_sehat_mapping_radiologi=false,satu_sehat_kirim_servicerequest_radiologi=false,hasil_endoskopi_hidung=false,satu_sehat_kirim_specimen_radiologi=false,master_masalah_keperawatan_neonatus=false,
            master_rencana_keperawatan_neonatus=false,penilaian_awal_keperawatan_ranap_neonatus=false,satu_sehat_kirim_observation_radiologi=false,satu_sehat_kirim_diagnosticreport_radiologi=false,hasil_endoskopi_telinga=false,
            satu_sehat_mapping_lab=false,satu_sehat_kirim_servicerequest_lab=false,satu_sehat_kirim_servicerequest_labmb=false,satu_sehat_kirim_specimen_lab=false,satu_sehat_kirim_specimen_labmb=false,
            satu_sehat_kirim_observation_lab=false,satu_sehat_kirim_observation_labmb=false,satu_sehat_kirim_diagnosticreport_lab=false,satu_sehat_kirim_diagnosticreport_labmb=false,kepatuhan_kelengkapan_keselamatan_bedah=false,
            nilai_piutang_perjenis_bayar_per_bulan=false,ringkasan_piutang_jenis_bayar=false,penilaian_pasien_imunitas_rendah=false,balance_cairan=false,catatan_observasi_chbp=false,catatan_observasi_induksi_persalinan=false,
            skp_kategori_penilaian=false,skp_kriteria_penilaian=false,skp_penilaian=false,referensi_poli_mobilejknfktp=false,referensi_dokter_mobilejknfktp=false,skp_rekapitulasi_penilaian=false,pembayaran_pihak_ke3_bankmandiri=false,
            metode_pembayaran_bankmandiri=false,bank_tujuan_transfer_bankmandiri=false,kodetransaksi_tujuan_transfer_bankmandiri=false,konsultasi_medik=false,jawaban_konsultasi_medik=false,pcare_cek_alergi=false,
            pcare_cek_prognosa=false,data_sasaran_usiaproduktif=false,data_sasaran_usialansia=false,skrining_perilaku_merokok_sekolah_remaja=false,skrining_kekerasan_pada_perempuan=false,skrining_obesitas=false,
            skrining_risiko_kanker_payudara=false,skrining_risiko_kanker_paru=false,skrining_tbc=false,skrining_kesehatan_gigi_mulut_remaja=false,penilaian_awal_keperawatan_ranap_bayi=false,booking_mcu_perusahaan=false,
            catatan_observasi_restrain_nonfarma=false,catatan_observasi_ventilator=false,catatan_anestesi_sedasi=false,skrining_puma=false,satu_sehat_kirim_careplan=false,satu_sehat_kirim_medicationstatement=false,
            skrining_adiksi_nikotin=false,skrining_thalassemia=false,skrining_instrumen_sdq=false,skrining_instrumen_srq=false,checklist_pemberian_fibrinolitik=false,skrining_kanker_kolorektal=false,dapur_pemesanan=false,
            bayar_pesan_dapur=false,hutang_dapur=false;

    /** Creates new form DlgUser
     * @param parent
     * @param modal */
    public DlgUpdateUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{"P","Pilihan Hak Akses Menu di SIMKES","Status"}){
              @Override 
              public boolean isCellEditable(int rowIndex, int colIndex){
                  boolean a = true;
                    if (colIndex==1) {
                        a=false;
                    }
                    return a;
              }              
              Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Boolean.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbUser.setModel(tabMode);
        //tbJabatan.setDefaultRenderer(Object.class, new WarnaTable(Scroll.getBackground(),Color.GREEN));
        tbUser.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbUser.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3;i++) {
            TableColumn column = tbUser.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(350);
            }else if(i==2){
                column.setPreferredWidth(40);
            }
        }
        tbUser.setDefaultRenderer(Object.class, new WarnaTable());
        TPass.setDocument(new batasInput((byte)50).getKata(TPass));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));       
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do falseT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
        ppBersihkan1 = new javax.swing.JMenuItem();
        ppSemua1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbUser = new widget.Table();
        panelGlass5 = new widget.panelisi();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TPass = new widget.TextBox();
        TNmUser = new widget.TextBox();
        panelGlass8 = new widget.panelisi();
        jLabel8 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel9 = new widget.Label();
        LCount = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setEditable(false);
        TKd.setHighlighter(null);
        TKd.setName("TKd"); // NOI18N
        TKd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdKeyPressed(evt);
            }
        });

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setIconTextGap(8);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

        ppBersihkan1.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan1.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan1.setText("Bersihkan Semua Hak Akses");
        ppBersihkan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan1.setIconTextGap(8);
        ppBersihkan1.setName("ppBersihkan1"); // NOI18N
        ppBersihkan1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkan1ActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan1);

        ppSemua1.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua1.setForeground(new java.awt.Color(50, 50, 50));
        ppSemua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua1.setText("Pilih Semua Hak Akses");
        ppSemua1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua1.setIconTextGap(8);
        ppSemua1.setName("ppSemua1"); // NOI18N
        ppSemua1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSemua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemua1ActionPerformed(evt);
            }
        });
        Popup.add(ppSemua1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Hak Akses User ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbUser.setToolTipText("Untuk memegang hak akses agar tidak hilang karena pencarian data, silahkan klik di bagian P. \nUntuk memberikan hak akses klik pada akses. Gunakan klik kanan untuk menampilkan popup menu.");
        tbUser.setComponentPopupMenu(Popup);
        tbUser.setName("tbUser"); // NOI18N
        Scroll.setViewportView(tbUser);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 44));
        panelGlass5.setLayout(null);

        jLabel3.setText("User :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass5.add(jLabel3);
        jLabel3.setBounds(4, 10, 40, 23);

        jLabel4.setText("Password :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass5.add(jLabel4);
        jLabel4.setBounds(209, 10, 60, 23);

        TPass.setName("TPass"); // NOI18N
        TPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPassKeyPressed(evt);
            }
        });
        panelGlass5.add(TPass);
        TPass.setBounds(271, 10, 150, 23);

        TNmUser.setEditable(false);
        TNmUser.setName("TNmUser"); // NOI18N
        TNmUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmUserKeyPressed(evt);
            }
        });
        panelGlass5.add(TNmUser);
        TNmUser.setBounds(47, 10, 150, 23);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_START);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel8.setText("Key Word :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(60, 23));
        jLabel8.setRequestFocusEnabled(false);
        panelGlass8.add(jLabel8);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(135, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);

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
        panelGlass8.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
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
        panelGlass8.add(BtnAll);

        jLabel9.setText("Record :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(jLabel9);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass8.add(LCount);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
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

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TKdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
        Valid.pindah(evt,BtnSimpan,TPass);
}//GEN-LAST:event_TKdKeyPressed

    private void TPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPassKeyPressed
        Valid.pindah(evt,TKd,BtnSimpan);
}//GEN-LAST:event_TPassKeyPressed

    private void TNmUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmUserKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNmUserKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbUser.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil(TKd.getText());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil(TKd.getText());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari,BtnSimpan);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TKd.getText().trim().equals("")||TNmUser.getText().trim().equals("")){
            Valid.textKosong(TNmUser,"User");            
        }else if(TPass.getText().trim().equals("")){
            Valid.textKosong(TPass,"Password");            
        }else{ 
            Simpan();
            Simpan2();
            Simpan3();
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnAll,BtnKeluar);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,TCari);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbUser.getRowCount();i++){
            tbUser.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for(i=0;i<tbUser.getRowCount();i++){
            tbUser.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void ppSemua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemua1ActionPerformed
        for(i=0;i<tbUser.getRowCount();i++){
            tbUser.setValueAt(true,i,2);
        }
    }//GEN-LAST:event_ppSemua1ActionPerformed

    private void ppBersihkan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkan1ActionPerformed
        for(i=0;i<tbUser.getRowCount();i++){
            tbUser.setValueAt(false,i,2);
        }
    }//GEN-LAST:event_ppBersihkan1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgUpdateUser dialog = new DlgUpdateUser(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSimpan;
    private widget.Label LCount;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.TextBox TNmUser;
    private widget.TextBox TPass;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppBersihkan1;
    private javax.swing.JMenuItem ppSemua;
    private javax.swing.JMenuItem ppSemua1;
    private widget.Table tbUser;
    // End of variables declaration//GEN-END:variables

    private void tampil(String user) {  
        penyakit=false;obat_penyakit=false;dokter=false;jadwal_praktek=false;petugas=false;pasien=false;registrasi=false;tindakan_ralan=false;kamar_inap=false;
        tindakan_ranap=false;operasi=false;rujukan_keluar=false;rujukan_masuk=false;beri_obat=false;resep_pulang=false;pasien_meninggal=false;diet_pasien=false;kelahiran_bayi=false;
        periksa_lab=false;periksa_radiologi=false;kasir_ralan=false;deposit_pasien=false;piutang_pasien=false;peminjaman_berkas=false;barcode=false;presensi_harian=false;presensi_bulanan=false;
        pegawai_admin=false;pegawai_user=false;suplier=false;satuan_barang=false;konversi_satuan=false;jenis_barang=false;obat=false;stok_opname_obat=false;stok_obat_pasien=false;
        pengadaan_obat=false;pemesanan_obat=false;penjualan_obat=false;piutang_obat=false;retur_ke_suplier=false;retur_dari_pembeli=false;retur_obat_ranap=false;retur_piutang_pasien=false;
        keuntungan_penjualan=false;keuntungan_beri_obat=false;sirkulasi_obat=false;ipsrs_barang=false;ipsrs_pengadaan_barang=false;ipsrs_stok_keluar=false;ipsrs_rekap_pengadaan=false;
        ipsrs_rekap_stok_keluar=false;ipsrs_pengeluaran_harian=false;inventaris_jenis=false;inventaris_kategori=false;inventaris_merk=false;inventaris_ruang=false;inventaris_produsen=false;
        inventaris_koleksi=false;inventaris_inventaris=false;inventaris_sirkulasi=false;parkir_jenis=false;parkir_in=false;parkir_out=false;parkir_rekap_harian=false;parkir_rekap_bulanan=false;
        informasi_kamar=false;harian_tindakan_poli=false;obat_per_poli=false;obat_per_kamar=false;obat_per_dokter_ralan=false;obat_per_dokter_ranap=false;harian_dokter=false;bulanan_dokter=false;
        harian_paramedis=false;bulanan_paramedis=false;pembayaran_ralan=false;pembayaran_ranap=false;rekap_pembayaran_ralan=false;rekap_pembayaran_ranap=false;tagihan_masuk=false;
        tambahan_biaya=false;potongan_biaya=false;resep_obat=false;resume_pasien=false;penyakit_ralan=false;penyakit_ranap=false;kamar=false;tarif_ralan=false;tarif_ranap=false;tarif_lab=false;
        tarif_radiologi=false;tarif_operasi=false;akun_rekening=false;rekening_tahun=false;posting_jurnal=false;buku_besar=false;cashflow=false;keuangan=false;pengeluaran=false;setup_pjlab=false;
        setup_otolokasi=false;setup_jam_kamin=false;setup_embalase=false;tracer_login=false;display=false;set_harga_obat=false;set_penggunaan_tarif=false;set_oto_ralan=false;biaya_harian=false;
        biaya_masuk_sekali=false;set_no_rm=false;billing_ralan=false;billing_ranap=false;jm_ranap_dokter=false;igd=false;barcoderalan=false;barcoderanap=false;set_harga_obat_ralan=false;
        set_harga_obat_ranap=false;penyakit_pd3i=false;surveilans_pd3i=false;surveilans_ralan=false;diagnosa_pasien=false;surveilans_ranap=false;pny_takmenular_ranap=false;
        pny_takmenular_ralan=false;kunjungan_ralan=false;rl32=false;rl33=false;rl37=false;rl38=false;harian_tindakan_dokter=false;sms=false;sidikjari=false;jam_masuk=false;
        jadwal_pegawai=false;parkir_barcode=false;set_nota=false;dpjp_ranap=false;mutasi_barang=false;rl34=false;rl36=false;fee_visit_dokter=false;fee_bacaan_ekg=false;fee_rujukan_rontgen=false;
        fee_rujukan_ranap=false;fee_ralan=false;akun_bayar=false;bayar_pemesanan_obat=false;obat_per_dokter_peresep=false;ipsrs_jenis_barang=false;pemasukan_lain=false;pengaturan_rekening=false;
        closing_kasir=false;keterlambatan_presensi=false;set_harga_kamar=false;rekap_per_shift=false;bpjs_cek_nik=false;bpjs_cek_kartu=false;bpjs_cek_riwayat=false;obat_per_cara_bayar=false;
        kunjungan_ranap=false;bayar_piutang=false;payment_point=false;bpjs_cek_nomor_rujukan=false;icd9=false;darurat_stok=false;retensi_rm=false;temporary_presensi=false;jurnal_harian=false;
        sirkulasi_obat2=false;edit_registrasi=false;bpjs_referensi_diagnosa=false;bpjs_referensi_poli=false;industrifarmasi=false;harian_js=false;bulanan_js=false;harian_paket_bhp=false;
        bulanan_paket_bhp=false;piutang_pasien2=false;bpjs_referensi_faskes=false;bpjs_sep=false;pengambilan_utd=false;tarif_utd=false;pengambilan_utd2=false;utd_medis_rusak=false;
        pengambilan_penunjang_utd=false;pengambilan_penunjang_utd2=false;utd_penunjang_rusak=false;suplier_penunjang=false;utd_donor=false;bpjs_monitoring_klaim=false;utd_cekal_darah=false;
        utd_komponen_darah=false;utd_stok_darah=false;utd_pemisahan_darah=false;harian_kamar=false;rincian_piutang_pasien=false;keuntungan_beri_obat_nonpiutang=false;reklasifikasi_ralan=false;
        reklasifikasi_ranap=false;utd_penyerahan_darah=false;hutang_obat=false;riwayat_obat_alkes_bhp=false;sensus_harian_poli=false;rl4a=false;aplicare_referensi_kamar=false;
        aplicare_ketersediaan_kamar=false;inacbg_klaim_baru_otomatis=false;inacbg_klaim_baru_manual=false;inacbg_coder_nik=false;mutasi_berkas=false;akun_piutang=false;harian_kso=false;
        bulanan_kso=false;harian_menejemen=false;bulanan_menejemen=false;inhealth_cek_eligibilitas=false;inhealth_referensi_jenpel_ruang_rawat=false;inhealth_referensi_poli=false;
        inhealth_referensi_faskes=false;inhealth_sjp=false;piutang_ralan=false;piutang_ranap=false;detail_piutang_penjab=false;lama_pelayanan_ralan=false;catatan_pasien=false;
        rl4b=false;rl4asebab=false;rl4bsebab=false;data_HAIs=false;harian_HAIs=false;bulanan_HAIs=false;hitung_bor=false;perusahaan_pasien=false;resep_dokter=false;lama_pelayanan_apotek=false;
        hitung_alos=false;detail_tindakan=false;rujukan_poli_internal=false;rekap_poli_anak=false;grafik_kunjungan_poli=false;grafik_kunjungan_perdokter=false;grafik_kunjungan_perpekerjaan=false;
        grafik_kunjungan_perpendidikan=false;grafik_kunjungan_pertahun=false;berkas_digital_perawatan=false;penyakit_menular_ranap=false;penyakit_menular_ralan=false;grafik_kunjungan_perbulan=false;
        grafik_kunjungan_pertanggal=false;grafik_kunjungan_demografi=false;grafik_kunjungan_statusdaftartahun=false;grafik_kunjungan_statusdaftartahun2=false;grafik_kunjungan_statusdaftarbulan=false;
        grafik_kunjungan_statusdaftarbulan2=false;grafik_kunjungan_statusdaftartanggal=false;grafik_kunjungan_statusdaftartanggal2=false;grafik_kunjungan_statusbataltahun=false;
        grafik_kunjungan_statusbatalbulan=false;pcare_cek_penyakit=false;grafik_kunjungan_statusbataltanggal=false;kategori_barang=false;golongan_barang=false;pemberian_obat_pertanggal=false;
        penjualan_obat_pertanggal=false;pcare_cek_kesadaran=false;pembatalan_periksa_dokter=false;pembayaran_per_unit=false;rekap_pembayaran_per_unit=false;grafik_kunjungan_percarabayar=false;
        ipsrs_pengadaan_pertanggal=false;ipsrs_stokkeluar_pertanggal=false;grafik_kunjungan_ranaptahun=false;pcare_cek_rujukan=false;grafik_lab_ralantahun=false;grafik_rad_ralantahun=false;
        cek_entry_ralan=false;inacbg_klaim_baru_manual2=false;permintaan_medis=false;rekap_permintaan_medis=false;surat_pemesanan_medis=false;permintaan_non_medis=false;rekap_permintaan_non_medis=false;
        surat_pemesanan_non_medis=false;grafik_per_perujuk=false;bpjs_cek_prosedur=false;bpjs_cek_kelas_rawat=false;bpjs_cek_dokter=false;bpjs_cek_spesialistik=false;bpjs_cek_ruangrawat=false;
        bpjs_cek_carakeluar=false;bpjs_cek_pasca_pulang=false;detail_tindakan_okvk=false;billing_parsial=false;bpjs_cek_nomor_rujukan_rs=false;bpjs_cek_rujukan_kartu_pcare=false;
        bpjs_cek_rujukan_kartu_rs=false;akses_depo_obat=false;bpjs_rujukan_keluar=false;grafik_lab_ralanbulan=false;pengeluaran_stok_apotek=false;grafik_rad_ralanbulan=false;detailjmdokter2=false;
        pengaduan_pasien=false;grafik_lab_ralanhari=false;grafik_rad_ralanhari=false;sensus_harian_ralan=false;metode_racik=false;pembayaran_akun_bayar=false;pengguna_obat_resep=false;
        rekap_pemesanan=false;master_berkas_pegawai=false;berkas_kepegawaian=false;riwayat_jabatan=false;riwayat_pendidikan=false;riwayat_naik_gaji=false;kegiatan_ilmiah=false;riwayat_penghargaan=false;
        riwayat_penelitian=false;penerimaan_non_medis=false;bayar_pesan_non_medis=false;hutang_barang_non_medis=false;rekap_pemesanan_non_medis=false;insiden_keselamatan=false;
        insiden_keselamatan_pasien=false;grafik_ikp_pertahun=false;grafik_ikp_perbulan=false;grafik_ikp_pertanggal=false;riwayat_data_batch=false;grafik_ikp_jenis=false;grafik_ikp_dampak=false;
        piutang_akun_piutang=false;grafik_kunjungan_per_agama=false;grafik_kunjungan_per_umur=false;suku_bangsa=false;bahasa_pasien=false;golongan_tni=false;satuan_tni=false;jabatan_tni=false;
        pangkat_tni=false;golongan_polri=false;satuan_polri=false;jabatan_polri=false;pangkat_polri=false;cacat_fisik=false;grafik_kunjungan_suku=false;grafik_kunjungan_bahasa=false;
        booking_operasi=false;mapping_poli_bpjs=false;grafik_kunjungan_per_cacat=false;barang_cssd=false;skdp_bpjs=false;booking_registrasi=false;bpjs_cek_propinsi=false;bpjs_cek_kabupaten=false;
        bpjs_cek_kecamatan=false;bpjs_cek_dokterdpjp=false;bpjs_cek_riwayat_rujukanrs=false;bpjs_cek_tanggal_rujukan=false;permintaan_lab=false;permintaan_radiologi=false;surat_indeks=false;
        surat_map=false;surat_almari=false;surat_rak=false;surat_ruang=false;surat_klasifikasi=false;surat_status=false;surat_sifat=false;surat_balas=false;surat_masuk=false;pcare_cek_dokter=false;
        pcare_cek_poli=false;pcare_cek_provider=false;pcare_cek_statuspulang=false;pcare_cek_spesialis=false;pcare_cek_subspesialis=false;pcare_cek_sarana=false;pcare_cek_khusus=false;
        pcare_cek_obat=false;pcare_cek_tindakan=false;pcare_cek_faskessubspesialis=false;pcare_cek_faskesalihrawat=false;pcare_cek_faskesthalasemia=false;pcare_mapping_obat=false;
        pcare_mapping_tindakan=false;pcare_club_prolanis=false;pcare_mapping_poli=false;pcare_kegiatan_kelompok=false;pcare_mapping_tindakan_ranap=false;pcare_peserta_kegiatan_kelompok=false;
        sirkulasi_obat3=false;bridging_pcare_daftar=false;pcare_mapping_dokter=false;ranap_per_ruang=false;penyakit_ranap_cara_bayar=false;anggota_militer_dirawat=false;set_input_parsial=false;
        lama_pelayanan_radiologi=false;lama_pelayanan_lab=false;bpjs_cek_sep=false;catatan_perawatan=false;surat_keluar=false;kegiatan_farmasi=false;stok_opname_logistik=false;sirkulasi_non_medis=false;
        rekap_lab_pertahun=false;perujuk_lab_pertahun=false;rekap_radiologi_pertahun=false;perujuk_radiologi_pertahun=false;jumlah_porsi_diet=false;jumlah_macam_diet=false;payment_point2=false;
        pembayaran_akun_bayar2=false;hapus_nota_salah=false;pengkajian_askep=false;hais_perbangsal=false;ppn_obat=false;saldo_akun_perbulan=false;display_apotek=false;sisrute_referensi_faskes=false;
        sisrute_referensi_alasanrujuk=false;sisrute_referensi_diagnosa=false;sisrute_rujukan_masuk=false;sisrute_rujukan_keluar=false;bpjs_cek_skdp=false;data_batch=false;kunjungan_permintaan_lab=false;
        kunjungan_permintaan_lab2=false;kunjungan_permintaan_radiologi=false;kunjungan_permintaan_radiologi2=false;pcare_pemberian_obat=false;pcare_pemberian_tindakan=false;pembayaran_akun_bayar3=false;
        password_asuransi=false;kemenkes_sitt=false;siranap_ketersediaan_kamar=false;grafik_tb_periodelaporan=false;grafik_tb_rujukan=false;grafik_tb_riwayat=false;grafik_tb_tipediagnosis=false;
        grafik_tb_statushiv=false;grafik_tb_skoringanak=false;grafik_tb_konfirmasiskoring5=false;grafik_tb_konfirmasiskoring6=false;grafik_tb_sumberobat=false;grafik_tb_hasilakhirpengobatan=false;
        grafik_tb_hasilteshiv=false;kadaluarsa_batch=false;sisa_stok=false;obat_per_resep=false;pemakaian_air_pdam=false;limbah_b3_medis=false;grafik_air_pdam_pertanggal=false;grafik_air_pdam_perbulan=false;
        grafik_limbahb3_pertanggal=false;grafik_limbahb3_perbulan=false;limbah_domestik=false;grafik_limbahdomestik_pertanggal=false;grafik_limbahdomestik_perbulan=false;mutu_air_limbah=false;
        pest_control=false;ruang_perpustakaan=false;kategori_perpustakaan=false;jenis_perpustakaan=false;pengarang_perpustakaan=false;penerbit_perpustakaan=false;koleksi_perpustakaan=false;
        inventaris_perpustakaan=false;set_peminjaman_perpustakaan=false;denda_perpustakaan=false;anggota_perpustakaan=false;peminjaman_perpustakaan=false;bayar_denda_perpustakaan=false;
        ebook_perpustakaan=false;jenis_cidera_k3rs=false;penyebab_k3rs=false;jenis_luka_k3rs=false;lokasi_kejadian_k3rs=false;dampak_cidera_k3rs=false;jenis_pekerjaan_k3rs=false;bagian_tubuh_k3rs=false;
        peristiwa_k3rs=false;grafik_k3_pertahun=false;grafik_k3_perbulan=false;grafik_k3_pertanggal=false;grafik_k3_perjeniscidera=false;grafik_k3_perpenyebab=false;grafik_k3_perjenisluka=false;
        grafik_k3_lokasikejadian=false;grafik_k3_dampakcidera=false;grafik_k3_perjenispekerjaan=false;grafik_k3_perbagiantubuh=false;jenis_cidera_k3rstahun=false;penyebab_k3rstahun=false;
        jenis_luka_k3rstahun=false;lokasi_kejadian_k3rstahun=false;dampak_cidera_k3rstahun=false;jenis_pekerjaan_k3rstahun=false;bagian_tubuh_k3rstahun=false;sekrining_rawat_jalan=false;
        bpjs_histori_pelayanan=false;rekap_mutasi_berkas=false;skrining_ralan_pernapasan_pertahun=false;pengajuan_barang_medis=false;pengajuan_barang_nonmedis=false;grafik_kunjungan_ranapbulan=false;
        grafik_kunjungan_ranaptanggal=false;grafik_kunjungan_ranap_peruang=false;kunjungan_bangsal_pertahun=false;grafik_jenjang_jabatanpegawai=false;grafik_bidangpegawai=false;grafik_departemenpegawai=false;
        grafik_pendidikanpegawai=false;grafik_sttswppegawai=false;grafik_sttskerjapegawai=false;grafik_sttspulangranap=false;kip_pasien_ranap=false;kip_pasien_ralan=false;bpjs_mapping_dokterdpjp=false;
        data_triase_igd=false;master_triase_skala1=false;master_triase_skala2=false;master_triase_skala3=false;master_triase_skala4=false;master_triase_skala5=false;master_triase_pemeriksaan=false;
        master_triase_macamkasus=false;rekap_permintaan_diet=false;daftar_pasien_ranap=false;daftar_pasien_ranaptni=false;pengajuan_asetinventaris=false;item_apotek_jenis=false;item_apotek_kategori=false;
        item_apotek_golongan=false;item_apotek_industrifarmasi=false;sepuluh_obat_terbanyak_poli=false;grafik_pengajuan_aset_urgensi=false;grafik_pengajuan_aset_status=false;grafik_pengajuan_aset_departemen=false;
        rekap_pengajuan_aset_departemen=false;grafik_kelompok_jabatanpegawai=false;grafik_resiko_kerjapegawai=false;grafik_emergency_indexpegawai=false;grafik_inventaris_ruang=false;harian_HAIs2=false;
        grafik_inventaris_jenis=false;data_resume_pasien=false;perkiraan_biaya_ranap=false;rekap_obat_poli=false;rekap_obat_pasien=false;permintaan_perbaikan_inventaris=false;grafik_HAIs_pasienbangsal=false;
        grafik_HAIs_pasienbulan=false;grafik_HAIs_laju_vap=false;grafik_HAIs_laju_iad=false;grafik_HAIs_laju_pleb=false;grafik_HAIs_laju_isk=false;grafik_HAIs_laju_ilo=false;grafik_HAIs_laju_hap=false;
        inhealth_mapping_poli=false;inhealth_mapping_dokter=false;inhealth_mapping_tindakan_ralan=false;inhealth_mapping_tindakan_ranap=false;inhealth_mapping_tindakan_radiologi=false;
        inhealth_mapping_tindakan_laborat=false;inhealth_mapping_tindakan_operasi=false;hibah_obat_bhp=false;asal_hibah=false;asuhan_gizi=false;inhealth_kirim_tagihan=false;sirkulasi_obat4=false;
        sirkulasi_obat5=false;sirkulasi_non_medis2=false;monitoring_asuhan_gizi=false;penerimaan_obat_perbulan=false;rekap_kunjungan=false;surat_sakit=false;penilaian_awal_keperawatan_ralan=false;
        permintaan_diet=false;master_masalah_keperawatan=false;pengajuan_cuti=false;kedatangan_pasien=false;utd_pendonor=false;toko_suplier=false;toko_jenis=false;toko_set_harga=false;toko_barang=false;
        penagihan_piutang_pasien=false;akun_penagihan_piutang=false;stok_opname_toko=false;toko_riwayat_barang=false;toko_surat_pemesanan=false;toko_pengajuan_barang=false;toko_penerimaan_barang=false;
        toko_pengadaan_barang=false;toko_hutang=false;toko_bayar_pemesanan=false;toko_member=false;toko_penjualan=false;registrasi_poli_per_tanggal=false;toko_piutang=false;toko_retur_beli=false;
        ipsrs_returbeli=false;ipsrs_riwayat_barang=false;pasien_corona=false;toko_pendapatan_harian=false;diagnosa_pasien_corona=false;perawatan_pasien_corona=false;penilaian_awal_keperawatan_gigi=false;
        master_masalah_keperawatan_gigi=false;toko_bayar_piutang=false;toko_piutang_harian=false;toko_penjualan_harian=false;deteksi_corona=false;penilaian_awal_keperawatan_kebidanan=false;pengumuman_epasien=false;
        surat_hamil=false;set_tarif_online=false;booking_periksa=false;toko_sirkulasi=false;toko_retur_jual=false;toko_retur_piutang=false;toko_sirkulasi2=false;toko_keuntungan_barang=false;
        zis_pengeluaran_penerima_dankes=false;zis_penghasilan_penerima_dankes=false;zis_ukuran_rumah_penerima_dankes=false;zis_dinding_rumah_penerima_dankes=false;zis_lantai_rumah_penerima_dankes=false;
        zis_atap_rumah_penerima_dankes=false;zis_kepemilikan_rumah_penerima_dankes=false;zis_kamar_mandi_penerima_dankes=false;zis_dapur_rumah_penerima_dankes=false;zis_kursi_rumah_penerima_dankes=false;
        zis_kategori_phbs_penerima_dankes=false;zis_elektronik_penerima_dankes=false;zis_ternak_penerima_dankes=false;zis_jenis_simpanan_penerima_dankes=false;penilaian_awal_keperawatan_anak=false;
        zis_kategori_asnaf_penerima_dankes=false;master_masalah_keperawatan_anak=false;master_imunisasi=false;zis_patologis_penerima_dankes=false;pcare_cek_kartu=false;surat_bebas_narkoba=false;
        surat_keterangan_covid=false;pemakaian_air_tanah=false;grafik_air_tanah_pertanggal=false;grafik_air_tanah_perbulan=false;lama_pelayanan_poli=false;hemodialisa=false;laporan_tahunan_irj=false;
        grafik_harian_hemodialisa=false;grafik_bulanan_hemodialisa=false;grafik_tahunan_hemodialisa=false;grafik_bulanan_meninggal=false;perbaikan_inventaris=false;surat_cuti_hamil=false;
        permintaan_stok_obat_pasien=false;pemeliharaan_inventaris=false;klasifikasi_pasien_ranap=false;bulanan_klasifikasi_pasien_ranap=false;harian_klasifikasi_pasien_ranap=false;klasifikasi_pasien_perbangsal=false;
        soap_perawatan=false;klaim_rawat_jalan=false;skrining_gizi=false;lama_penyiapan_rm=false;dosis_radiologi=false;demografi_umur_kunjungan=false;jam_diet_pasien=false;rvu_bpjs=false;
        verifikasi_penerimaan_farmasi=false;verifikasi_penerimaan_logistik=false;pemeriksaan_lab_pa=false;ringkasan_pengajuan_obat=false;ringkasan_pemesanan_obat=false;ringkasan_pengadaan_obat=false;
        ringkasan_penerimaan_obat=false;ringkasan_hibah_obat=false;ringkasan_penjualan_obat=false;ringkasan_beri_obat=false;ringkasan_piutang_obat=false;ringkasan_stok_keluar_obat=false;
        ringkasan_retur_suplier_obat=false;ringkasan_retur_pembeli_obat=false;penilaian_awal_keperawatan_ranapkebidanan=false;ringkasan_pengajuan_nonmedis=false;ringkasan_pemesanan_nonmedis=false;;
        ringkasan_pengadaan_nonmedis=false;ringkasan_penerimaan_nonmedis=false;ringkasan_stokkeluar_nonmedis=false;ringkasan_returbeli_nonmedis=false;omset_penerimaan=false;validasi_penagihan_piutang=false;
        permintaan_ranap=false;bpjs_diagnosa_prb=false;bpjs_obat_prb=false;bpjs_surat_kontrol=false;penggunaan_bhp_ok=false;surat_keterangan_rawat_inap=false;surat_keterangan_sehat=false;pendapatan_per_carabayar=false;
        akun_host_to_host_bank_jateng=false;pembayaran_bank_jateng=false;bpjs_surat_pri=false;ringkasan_tindakan=false;lama_pelayanan_pasien=false;surat_sakit_pihak_2=false;tagihan_hutang_obat=false;
        referensi_mobilejkn_bpjs=false;batal_pendaftaran_mobilejkn_bpjs=false;lama_operasi=false;grafik_inventaris_kategori=false;grafik_inventaris_merk=false;grafik_inventaris_produsen=false;
        pengembalian_deposit_pasien=false;validasi_tagihan_hutang_obat=false;piutang_obat_belum_lunas=false;integrasi_briapi=false;pengadaan_aset_inventaris=false;akun_aset_inventaris=false;suplier_inventaris=false;
        penerimaan_aset_inventaris=false;bayar_pemesanan_iventaris=false;hutang_aset_inventaris=false;hibah_aset_inventaris=false;titip_faktur_non_medis=false;validasi_tagihan_non_medis=false;titip_faktur_aset=false;
        validasi_tagihan_aset=false;hibah_non_medis=false;pcare_alasan_tacc=false;resep_luar=false;surat_bebas_tbc=false;surat_buta_warna=false;surat_bebas_tato=false;surat_kewaspadaan_kesehatan=false;
        grafik_porsidiet_pertanggal=false;grafik_porsidiet_perbulan=false;grafik_porsidiet_pertahun=false;grafik_porsidiet_perbangsal=false;penilaian_awal_medis_ralan=false;master_masalah_keperawatan_mata=false;
        penilaian_awal_keperawatan_mata=false;penilaian_awal_medis_ranap=false;penilaian_awal_medis_ranap_kebidanan=false;penilaian_awal_medis_ralan_kebidanan=false;penilaian_awal_medis_igd=false;
        penilaian_awal_medis_ralan_anak=false;bpjs_referensi_poli_hfis=false;bpjs_referensi_dokter_hfis=false;bpjs_referensi_jadwal_hfis=false;penilaian_fisioterapi=false;bpjs_program_prb=false;
        bpjs_suplesi_jasaraharja=false;bpjs_data_induk_kecelakaan=false;bpjs_sep_internal=false;bpjs_klaim_jasa_raharja=false;bpjs_daftar_finger_print=false;bpjs_rujukan_khusus=false;pemeliharaan_gedung=false;
        grafik_perbaikan_inventaris_pertanggal=false;grafik_perbaikan_inventaris_perbulan=false;grafik_perbaikan_inventaris_pertahun=false;grafik_perbaikan_inventaris_perpelaksana_status=false;penilaian_mcu=false;
        peminjam_piutang=false;piutang_lainlain=false;cara_bayar=false;audit_kepatuhan_apd=false;bpjs_task_id=false;bayar_piutang_lain=false;pembayaran_akun_bayar4=false;stok_akhir_farmasi_pertanggal=false;
        riwayat_kamar_pasien=false;uji_fungsi_kfr=false;hapus_berkas_digital_perawatan=false;kategori_pengeluaran_harian=false;kategori_pemasukan_lain=false;pembayaran_akun_bayar5=false;ruang_ok=false;telaah_resep=false;
        jasa_tindakan_pasien=false;permintaan_resep_pulang=false;rekap_jm_dokter=false;status_data_rm=false;ubah_petugas_lab_pk=false;ubah_petugas_lab_pa=false;ubah_petugas_radiologi=false;gabung_norawat=false;
        gabung_rm=false;ringkasan_biaya_obat_pasien_pertanggal=false;master_masalah_keperawatan_igd=false;penilaian_awal_keperawatan_igd=false;bpjs_referensi_dpho_apotek=false;bpjs_referensi_poli_apotek=false;
        bayar_jm_dokter=false;bpjs_referensi_faskes_apotek=false;bpjs_referensi_spesialistik_apotek=false;pembayaran_briva=false;penilaian_awal_keperawatan_ranap=false;nilai_penerimaan_vendor_farmasi_perbulan=false;
        akun_bayar_hutang=false;master_rencana_keperawatan=false;laporan_tahunan_igd=false;obat_bhp_tidakbergerak=false;ringkasan_hutang_vendor_farmasi=false;nilai_penerimaan_vendor_nonmedis_perbulan=false;
        ringkasan_hutang_vendor_nonmedis=false;master_rencana_keperawatan_anak=false;anggota_polri_dirawat=false;daftar_pasien_ranap_polri=false;soap_ralan_polri=false;soap_ranap_polri=false;laporan_penyakit_polri=false;
        jumlah_pengunjung_ralan_polri=false;catatan_observasi_igd=false;catatan_observasi_ranap=false;catatan_observasi_ranap_kebidanan=false;catatan_observasi_ranap_postpartum=false;penilaian_awal_medis_ralan_tht=false;
        penilaian_psikologi=false;audit_cuci_tangan_medis=false;audit_pembuangan_limbah=false;ruang_audit_kepatuhan=false;audit_pembuangan_benda_tajam=false;audit_penanganan_darah=false;audit_pengelolaan_linen_kotor=false;
        audit_penempatan_pasien=false;audit_kamar_jenazah=false;audit_bundle_iadp=false;audit_bundle_ido=false;audit_fasilitas_kebersihan_tangan=false;audit_fasilitas_apd=false;audit_pembuangan_limbah_cair_infeksius=false;
        audit_sterilisasi_alat=false;penilaian_awal_medis_ralan_psikiatri=false;persetujuan_penolakan_tindakan=false;audit_bundle_isk=false;audit_bundle_plabsi=false;audit_bundle_vap=false;akun_host_to_host_bank_papua=false;
        pembayaran_bank_papua=false;penilaian_awal_medis_ralan_penyakit_dalam=false;penilaian_awal_medis_ralan_mata=false;penilaian_awal_medis_ralan_neurologi=false;sirkulasi_obat6=false;penilaian_awal_medis_ralan_orthopedi=false;
        penilaian_awal_medis_ralan_bedah=false;integrasi_khanza_health_services=false;soap_ralan_tni=false;soap_ranap_tni=false;jumlah_pengunjung_ralan_tni=false;laporan_penyakit_tni=false;catatan_keperawatan_ranap=false;
        master_rencana_keperawatan_gigi=false;master_rencana_keperawatan_mata=false;master_rencana_keperawatan_igd=false;master_masalah_keperawatan_psikiatri=false;master_rencana_keperawatan_psikiatri=false;
        penilaian_awal_keperawatan_psikiatri=false;pemantauan_pews_anak=false;surat_pulang_atas_permintaan_sendiri=false;template_hasil_radiologi=false;laporan_bulanan_irj=false;template_pemeriksaan=false;pemeriksaan_lab_mb=false;
        ubah_petugas_lab_mb=false;penilaian_pre_operasi=false;penilaian_pre_anestesi=false;perencanaan_pemulangan=false;penilaian_lanjutan_resiko_jatuh_dewasa=false;penilaian_lanjutan_resiko_jatuh_anak=false;
        penilaian_awal_medis_ralan_geriatri=false;penilaian_tambahan_pasien_geriatri=false;skrining_nutrisi_dewasa=false;skrining_nutrisi_lansia=false;hasil_pemeriksaan_usg=false;skrining_nutrisi_anak=false;
        akun_host_to_host_bank_jabar=false;pembayaran_bank_jabar=false;surat_pernyataan_pasien_umum=false;konseling_farmasi=false;pelayanan_informasi_obat=false;jawaban_pio_apoteker=false;surat_persetujuan_umum=false;
        transfer_pasien_antar_ruang=false;satu_sehat_referensi_dokter=false;satu_sehat_referensi_pasien=false;satu_sehat_mapping_departemen=false;satu_sehat_mapping_lokasi=false;satu_sehat_kirim_encounter=false;
        catatan_cek_gds=false;satu_sehat_kirim_condition=false;checklist_pre_operasi=false;satu_sehat_kirim_observationttv=false;signin_sebelum_anestesi=false;satu_sehat_kirim_procedure=false;operasi_per_bulan=false;
        timeout_sebelum_insisi=false;signout_sebelum_menutup_luka=false;dapur_barang=false;dapur_opname=false;satu_sehat_mapping_vaksin=false;dapur_suplier=false;satu_sehat_kirim_Immunization=false;checklist_post_operasi=false;
        dapur_pembelian=false;dapur_stok_keluar=false;dapur_riwayat_barang=false;permintaan_dapur=false;rekonsiliasi_obat=false;biaya_pengadaan_dapur=false;rekap_pengadaan_dapur=false;kesling_limbah_b3medis_cair=false;
        grafik_limbahb3cair_pertanggal=false;grafik_limbahb3cair_perbulan=false;rekap_biaya_registrasi=false;konfirmasi_rekonsiliasi_obat=false;satu_sehat_kirim_clinicalimpression=false;penilaian_pasien_terminal=false;
        surat_persetujuan_rawat_inap=false;monitoring_reaksi_tranfusi=false;penilaian_korban_kekerasan=false;penilaian_lanjutan_resiko_jatuh_lansia=false;penilaian_pasien_penyakit_menular=false;mpp_skrining=false;
        edukasi_pasien_keluarga_rj=false;pemantauan_pews_dewasa=false;penilaian_tambahan_bunuh_diri=false;bpjs_antrean_pertanggal=false;penilaian_tambahan_perilaku_kekerasan=false;penilaian_tambahan_beresiko_melarikan_diri=false;
        persetujuan_penundaan_pelayanan=false;sisa_diet_pasien=false;penilaian_awal_medis_ralan_bedah_mulut=false;penilaian_pasien_keracunan=false;pemantauan_meows_obstetri=false;catatan_adime_gizi=false;pengajuan_biaya=false;
        penilaian_awal_keperawatan_ralan_geriatri=false;master_masalah_keperawatan_geriatri=false;master_rencana_keperawatan_geriatri=false;checklist_kriteria_masuk_hcu=false;checklist_kriteria_keluar_hcu=false;
        penilaian_risiko_dekubitus=false;master_menolak_anjuran_medis=false;penolakan_anjuran_medis=false;laporan_tahunan_penolakan_anjuran_medis=false;template_laporan_operasi=false;hasil_tindakan_eswl=false;
        checklist_kriteria_masuk_icu=false;checklist_kriteria_keluar_icu=false;akses_dokter_lain_rawat_jalan=false;follow_up_dbd=false;penilaian_risiko_jatuh_neonatus=false;persetujuan_pengajuan_biaya=false;
        pemeriksaan_fisik_ralan_per_penyakit=false;penilaian_lanjutan_resiko_jatuh_geriatri=false;pemantauan_ews_neonatus=false;validasi_persetujuan_pengajuan_biaya=false;riwayat_perawatan_icare_bpjs=false;
        rekap_pengajuan_biaya=false;penilaian_awal_medis_ralan_kulit_kelamin=false;akun_host_to_host_bank_mandiri=false;penilaian_medis_hemodialisa=false;penilaian_level_kecemasan_ranap_anak=false;
        penilaian_lanjutan_resiko_jatuh_psikiatri=false;penilaian_lanjutan_skrining_fungsional=false;penilaian_medis_ralan_rehab_medik=false;laporan_anestesi=false;template_persetujuan_penolakan_tindakan=false;
        penilaian_medis_ralan_gawat_darurat_psikiatri=false;bpjs_referensi_setting_apotek=false;bpjs_referensi_obat_apotek=false;bpjs_mapping_obat_apotek=false;pembayaran_bank_mandiri=false;penilaian_ulang_nyeri=false;
        penilaian_terapi_wicara=false;bpjs_obat_23hari_apotek=false;pengkajian_restrain=false;bpjs_kunjungan_sep_apotek=false;bpjs_monitoring_klaim_apotek=false;bpjs_daftar_pelayanan_obat_apotek=false;
        penilaian_awal_medis_ralan_paru=false;catatan_keperawatan_ralan=false;catatan_persalinan=false;skor_aldrette_pasca_anestesi=false;skor_steward_pasca_anestesi=false;skor_bromage_pasca_anestesi=false;
        penilaian_pre_induksi=false;hasil_usg_urologi=false;hasil_usg_gynecologi=false;hasil_pemeriksaan_ekg=false;hapus_edit_sep_bpjs=false;satu_sehat_kirim_diet=false;satu_sehat_mapping_obat=false;dapur_ringkasan_pembelian=false;
        satu_sehat_kirim_medication=false;satu_sehat_kirim_medicationrequest=false;penatalaksanaan_terapi_okupasi=false;satu_sehat_kirim_medicationdispense=false;hasil_usg_neonatus=false;hasil_endoskopi_faring_laring=false;
        satu_sehat_mapping_radiologi=false;satu_sehat_kirim_servicerequest_radiologi=false;hasil_endoskopi_hidung=false;satu_sehat_kirim_specimen_radiologi=false;master_masalah_keperawatan_neonatus=false;
        master_rencana_keperawatan_neonatus=false;penilaian_awal_keperawatan_ranap_neonatus=false;satu_sehat_kirim_observation_radiologi=false;satu_sehat_kirim_diagnosticreport_radiologi=false;hasil_endoskopi_telinga=false;
        satu_sehat_mapping_lab=false;satu_sehat_kirim_servicerequest_lab=false;satu_sehat_kirim_servicerequest_labmb=false;satu_sehat_kirim_specimen_lab=false;satu_sehat_kirim_specimen_labmb=false;
        satu_sehat_kirim_observation_lab=false;satu_sehat_kirim_observation_labmb=false;satu_sehat_kirim_diagnosticreport_lab=false;satu_sehat_kirim_diagnosticreport_labmb=false;kepatuhan_kelengkapan_keselamatan_bedah=false;
        nilai_piutang_perjenis_bayar_per_bulan=false;ringkasan_piutang_jenis_bayar=false;penilaian_pasien_imunitas_rendah=false;balance_cairan=false;catatan_observasi_chbp=false;catatan_observasi_induksi_persalinan=false;
        skp_kategori_penilaian=false;skp_kriteria_penilaian=false;skp_penilaian=false;referensi_poli_mobilejknfktp=false;referensi_dokter_mobilejknfktp=false;skp_rekapitulasi_penilaian=false;pembayaran_pihak_ke3_bankmandiri=false;
        metode_pembayaran_bankmandiri=false;bank_tujuan_transfer_bankmandiri=false;kodetransaksi_tujuan_transfer_bankmandiri=false;konsultasi_medik=false;jawaban_konsultasi_medik=false;pcare_cek_alergi=false;
        pcare_cek_prognosa=false;data_sasaran_usiaproduktif=false;data_sasaran_usialansia=false;skrining_perilaku_merokok_sekolah_remaja=false;skrining_kekerasan_pada_perempuan=false;skrining_obesitas=false;
        skrining_risiko_kanker_payudara=false;skrining_risiko_kanker_paru=false;skrining_tbc=false;skrining_kesehatan_gigi_mulut_remaja=false;penilaian_awal_keperawatan_ranap_bayi=false;booking_mcu_perusahaan=false;
        catatan_observasi_restrain_nonfarma=false;catatan_observasi_ventilator=false;catatan_anestesi_sedasi=false;skrining_puma=false;satu_sehat_kirim_careplan=false;satu_sehat_kirim_medicationstatement=false;
        skrining_adiksi_nikotin=false;skrining_thalassemia=false;skrining_instrumen_sdq=false;skrining_instrumen_srq=false;checklist_pemberian_fibrinolitik=false;skrining_kanker_kolorektal=false;dapur_pemesanan=false;
        bayar_pesan_dapur=false;hutang_dapur=false;
        try{    
            jml=0;
            for(i=0;i<tbUser.getRowCount();i++){
                if(tbUser.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }
            
            pilih=null;
            pilih=new boolean[jml]; 
            akses=null;
            akses=new boolean[jml];
            menu=null;
            menu=new String[jml];
            
            jml=0;
            for(i=0;i<tbUser.getRowCount();i++){
                if(tbUser.getValueAt(i,0).toString().equals("true")){
                    pilih[jml]=true;
                    menu[jml]=tbUser.getValueAt(i,1).toString();
                    akses[jml]=Boolean.parseBoolean(tbUser.getValueAt(i,2).toString());
                    jml++;
                }
            }
            
            Valid.tabelKosong(tabMode);
            
            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[] {
                    pilih[i],menu[i],akses[i]
                });
            }
            
            ps=koneksi.prepareStatement(
                "select user.penyakit,user.obat_penyakit,user.dokter,"+
                "user.jadwal_praktek,user.petugas,user.pasien,user.registrasi,user.tindakan_ralan,user.kamar_inap,user.tindakan_ranap,user.operasi,"+
                "user.rujukan_keluar,user.rujukan_masuk,user.beri_obat,user.resep_pulang,user.pasien_meninggal,user.diet_pasien,user.kelahiran_bayi,"+
                "user.periksa_lab,user.periksa_radiologi,user.kasir_ralan,user.deposit_pasien,user.piutang_pasien,user.peminjaman_berkas,user.barcode,"+
                "user.presensi_harian,user.presensi_bulanan,user.pegawai_admin,user.pegawai_user,user.suplier,user.satuan_barang,user.konversi_satuan,"+
                "user.jenis_barang,user.obat,user.stok_opname_obat,user.stok_obat_pasien,user.pengadaan_obat,user.pemesanan_obat,user.penjualan_obat,"+
                "user.piutang_obat,user.retur_ke_suplier,user.retur_dari_pembeli,user.retur_obat_ranap,user.retur_piutang_pasien,user.keuntungan_penjualan,"+
                "user.keuntungan_beri_obat,user.sirkulasi_obat,user.ipsrs_barang,user.ipsrs_pengadaan_barang,user.ipsrs_stok_keluar,user.ipsrs_rekap_pengadaan,"+
                "user.ipsrs_rekap_stok_keluar,user.ipsrs_pengeluaran_harian,user.inventaris_jenis,user.inventaris_kategori,user.inventaris_merk,"+
                "user.inventaris_ruang,user.inventaris_produsen,user.inventaris_koleksi,user.inventaris_inventaris,user.inventaris_sirkulasi,user.parkir_jenis,"+
                "user.parkir_in,user.parkir_out,user.parkir_rekap_harian,user.parkir_rekap_bulanan,user.informasi_kamar,user.harian_tindakan_poli,"+
                "user.obat_per_poli,user.obat_per_kamar,user.obat_per_dokter_ralan,user.obat_per_dokter_ranap,user.harian_dokter,user.bulanan_dokter,"+
                "user.harian_paramedis,user.bulanan_paramedis,user.pembayaran_ralan,user.pembayaran_ranap,user.rekap_pembayaran_ralan,user.rekap_pembayaran_ranap,"+
                "user.tagihan_masuk,user.tambahan_biaya,user.potongan_biaya,user.resep_obat,user.resume_pasien,user.penyakit_ralan,user.penyakit_ranap,user.kamar,"+
                "user.tarif_ralan,user.tarif_ranap,user.tarif_lab,user.tarif_radiologi,user.tarif_operasi,user.akun_rekening,user.rekening_tahun,"+
                "user.posting_jurnal,user.buku_besar,user.cashflow,user.keuangan,user.pengeluaran,user.setup_pjlab,user.setup_otolokasi,user.setup_jam_kamin,"+
                "user.setup_embalase,user.tracer_login,user.display,user.set_harga_obat,user.set_penggunaan_tarif,user.set_oto_ralan,user.biaya_harian,"+
                "user.biaya_masuk_sekali,user.set_no_rm,user.billing_ralan,user.billing_ranap,user.jm_ranap_dokter,user.igd,user.barcoderalan,user.barcoderanap,"+
                "user.set_harga_obat_ralan,user.set_harga_obat_ranap,user.penyakit_pd3i,user.surveilans_pd3i,user.surveilans_ralan,user.diagnosa_pasien,"+
                "user.surveilans_ranap,user.pny_takmenular_ranap,user.pny_takmenular_ralan,user.kunjungan_ralan,user.rl32,user.rl33,user.rl37,user.rl38,"+
                "user.harian_tindakan_dokter,user.sms,user.sidikjari,user.jam_masuk,user.jadwal_pegawai,user.parkir_barcode,user.set_nota,user.dpjp_ranap,"+
                "user.mutasi_barang,user.rl34,user.rl36,user.fee_visit_dokter,user.fee_bacaan_ekg,user.fee_rujukan_rontgen,user.fee_rujukan_ranap,"+
                "user.fee_ralan,user.akun_bayar,user.bayar_pemesanan_obat,user.obat_per_dokter_peresep,user.ipsrs_jenis_barang,user.pemasukan_lain,"+
                "user.pengaturan_rekening,user.closing_kasir,user.keterlambatan_presensi,user.set_harga_kamar,user.rekap_per_shift,user.bpjs_cek_nik,"+
                "user.bpjs_cek_kartu,user.bpjs_cek_riwayat,user.obat_per_cara_bayar,user.kunjungan_ranap,user.bayar_piutang,user.payment_point,"+
                "user.bpjs_cek_nomor_rujukan,user.icd9,user.darurat_stok,user.retensi_rm,user.temporary_presensi,user.jurnal_harian,user.sirkulasi_obat2,"+
                "user.edit_registrasi,user.bpjs_referensi_diagnosa,user.bpjs_referensi_poli,user.industrifarmasi,user.harian_js,user.bulanan_js,"+
                "user.harian_paket_bhp,user.bulanan_paket_bhp,user.piutang_pasien2,user.bpjs_referensi_faskes,user.bpjs_sep,user.pengambilan_utd,"+
                "user.tarif_utd,user.pengambilan_utd2,user.utd_medis_rusak,user.pengambilan_penunjang_utd,user.pengambilan_penunjang_utd2,user.utd_penunjang_rusak,"+
                "user.suplier_penunjang,user.utd_donor,user.bpjs_monitoring_klaim,user.utd_cekal_darah,user.utd_komponen_darah,user.utd_stok_darah,"+
                "user.utd_pemisahan_darah,user.harian_kamar,user.rincian_piutang_pasien,user.keuntungan_beri_obat_nonpiutang,user.reklasifikasi_ralan,"+
                "user.reklasifikasi_ranap,user.utd_penyerahan_darah,user.hutang_obat,user.riwayat_obat_alkes_bhp,user.sensus_harian_poli,user.rl4a,"+
                "user.aplicare_referensi_kamar,user.aplicare_ketersediaan_kamar,user.inacbg_klaim_baru_otomatis,user.inacbg_klaim_baru_manual,"+
                "user.inacbg_coder_nik,user.mutasi_berkas,user.akun_piutang,user.harian_kso,user.bulanan_kso,user.harian_menejemen,user.bulanan_menejemen,"+
                "user.inhealth_cek_eligibilitas,user.inhealth_referensi_jenpel_ruang_rawat,user.inhealth_referensi_poli,user.inhealth_referensi_faskes,"+
                "user.inhealth_sjp,user.piutang_ralan,user.piutang_ranap,user.detail_piutang_penjab,user.lama_pelayanan_ralan,user.catatan_pasien,"+
                "user.rl4b,user.rl4asebab,user.rl4bsebab,user.data_HAIs,user.harian_HAIs,user.bulanan_HAIs,user.hitung_bor,user.perusahaan_pasien,"+
                "user.resep_dokter,user.lama_pelayanan_apotek,user.hitung_alos,user.detail_tindakan,user.rujukan_poli_internal,user.rekap_poli_anak,"+
                "user.grafik_kunjungan_poli,user.grafik_kunjungan_perdokter,user.grafik_kunjungan_perpekerjaan,user.grafik_kunjungan_perpendidikan,"+
                "user.grafik_kunjungan_pertahun,user.berkas_digital_perawatan,user.penyakit_menular_ranap,user.penyakit_menular_ralan,user.grafik_kunjungan_perbulan,"+
                "user.grafik_kunjungan_pertanggal,user.grafik_kunjungan_demografi,user.grafik_kunjungan_statusdaftartahun,user.grafik_kunjungan_statusdaftartahun2,"+
                "user.grafik_kunjungan_statusdaftarbulan,user.grafik_kunjungan_statusdaftarbulan2,user.grafik_kunjungan_statusdaftartanggal,"+
                "user.grafik_kunjungan_statusdaftartanggal2,user.grafik_kunjungan_statusbataltahun,user.grafik_kunjungan_statusbatalbulan,user.pcare_cek_penyakit,"+
                "user.grafik_kunjungan_statusbataltanggal,user.kategori_barang,user.golongan_barang,user.pemberian_obat_pertanggal,user.penjualan_obat_pertanggal,"+
                "user.pcare_cek_kesadaran,user.pembatalan_periksa_dokter,user.pembayaran_per_unit,user.rekap_pembayaran_per_unit,user.grafik_kunjungan_percarabayar,"+
                "user.ipsrs_pengadaan_pertanggal,user.ipsrs_stokkeluar_pertanggal,user.grafik_kunjungan_ranaptahun,user.pcare_cek_rujukan,user.grafik_lab_ralantahun,"+
                "user.grafik_rad_ralantahun,user.cek_entry_ralan,user.inacbg_klaim_baru_manual2,user.permintaan_medis,user.rekap_permintaan_medis,"+
                "user.surat_pemesanan_medis,user.permintaan_non_medis,user.rekap_permintaan_non_medis,user.surat_pemesanan_non_medis,user.grafik_per_perujuk,"+
                "user.bpjs_cek_prosedur,user.bpjs_cek_kelas_rawat,user.bpjs_cek_dokter,user.bpjs_cek_spesialistik,user.bpjs_cek_ruangrawat,user.bpjs_cek_carakeluar,"+
                "user.bpjs_cek_pasca_pulang,user.detail_tindakan_okvk,user.billing_parsial,user.bpjs_cek_nomor_rujukan_rs,user.bpjs_cek_rujukan_kartu_pcare,"+
                "user.bpjs_cek_rujukan_kartu_rs,user.akses_depo_obat,user.bpjs_rujukan_keluar,user.grafik_lab_ralanbulan,user.pengeluaran_stok_apotek,"+
                "user.grafik_rad_ralanbulan,user.detailjmdokter2,user.pengaduan_pasien,user.grafik_lab_ralanhari,user.grafik_rad_ralanhari,user.sensus_harian_ralan,"+
                "user.metode_racik,user.pembayaran_akun_bayar,user.pengguna_obat_resep,user.rekap_pemesanan,user.master_berkas_pegawai,user.berkas_kepegawaian,"+
                "user.riwayat_jabatan,user.riwayat_pendidikan,user.riwayat_naik_gaji,user.kegiatan_ilmiah,user.riwayat_penghargaan,user.riwayat_penelitian,"+
                "user.penerimaan_non_medis,user.bayar_pesan_non_medis,user.hutang_barang_non_medis,user.rekap_pemesanan_non_medis,user.insiden_keselamatan,"+
                "user.insiden_keselamatan_pasien,user.grafik_ikp_pertahun,user.grafik_ikp_perbulan,user.grafik_ikp_pertanggal,user.riwayat_data_batch,"+
                "user.grafik_ikp_jenis,user.grafik_ikp_dampak,user.piutang_akun_piutang,user.grafik_kunjungan_per_agama,user.grafik_kunjungan_per_umur,"+
                "user.suku_bangsa,user.bahasa_pasien,user.golongan_tni,user.satuan_tni,user.jabatan_tni,user.pangkat_tni,user.golongan_polri,user.satuan_polri,"+
                "user.jabatan_polri,user.pangkat_polri,user.cacat_fisik,user.grafik_kunjungan_suku,user.grafik_kunjungan_bahasa,user.booking_operasi,"+
                "user.mapping_poli_bpjs,user.grafik_kunjungan_per_cacat,user.barang_cssd,user.skdp_bpjs,user.booking_registrasi,user.bpjs_cek_propinsi,"+
                "user.bpjs_cek_kabupaten,user.bpjs_cek_kecamatan,user.bpjs_cek_dokterdpjp,user.bpjs_cek_riwayat_rujukanrs,user.bpjs_cek_tanggal_rujukan,"+
                "user.permintaan_lab,user.permintaan_radiologi,user.surat_indeks,user.surat_map,user.surat_almari,user.surat_rak,user.surat_ruang,"+
                "user.surat_klasifikasi,user.surat_status,user.surat_sifat,user.surat_balas,user.surat_masuk,user.pcare_cek_dokter,user.pcare_cek_poli,"+
                "user.pcare_cek_provider,user.pcare_cek_statuspulang,user.pcare_cek_spesialis,user.pcare_cek_subspesialis,user.pcare_cek_sarana,user.pcare_cek_khusus,"+
                "user.pcare_cek_obat,user.pcare_cek_tindakan,user.pcare_cek_faskessubspesialis,user.pcare_cek_faskesalihrawat,user.pcare_cek_faskesthalasemia,"+
                "user.pcare_mapping_obat,user.pcare_mapping_tindakan,user.pcare_club_prolanis,user.pcare_mapping_poli,user.pcare_kegiatan_kelompok,"+
                "user.pcare_mapping_tindakan_ranap,user.pcare_peserta_kegiatan_kelompok,user.sirkulasi_obat3,user.bridging_pcare_daftar,user.pcare_mapping_dokter,"+
                "user.ranap_per_ruang,user.penyakit_ranap_cara_bayar,user.anggota_militer_dirawat,user.set_input_parsial,user.lama_pelayanan_radiologi,"+
                "user.lama_pelayanan_lab,user.bpjs_cek_sep,user.catatan_perawatan,user.surat_keluar,user.kegiatan_farmasi,user.stok_opname_logistik,"+
                "user.sirkulasi_non_medis,user.rekap_lab_pertahun,user.perujuk_lab_pertahun,user.rekap_radiologi_pertahun,user.perujuk_radiologi_pertahun,"+
                "user.jumlah_porsi_diet,user.jumlah_macam_diet,user.payment_point2,user.pembayaran_akun_bayar2,user.hapus_nota_salah,user.pengkajian_askep,"+
                "user.hais_perbangsal,user.ppn_obat,user.saldo_akun_perbulan,user.display_apotek,user.sisrute_referensi_faskes,user.sisrute_referensi_alasanrujuk,"+
                "user.sisrute_referensi_diagnosa,user.sisrute_rujukan_masuk,user.sisrute_rujukan_keluar,user.bpjs_cek_skdp,user.data_batch,user.kunjungan_permintaan_lab,"+
                "user.kunjungan_permintaan_lab2,user.kunjungan_permintaan_radiologi,user.kunjungan_permintaan_radiologi2,user.pcare_pemberian_obat,"+
                "user.pcare_pemberian_tindakan,user.pembayaran_akun_bayar3,user.password_asuransi,user.kemenkes_sitt,user.siranap_ketersediaan_kamar,"+
                "user.grafik_tb_periodelaporan,user.grafik_tb_rujukan,user.grafik_tb_riwayat,user.grafik_tb_tipediagnosis,user.grafik_tb_statushiv,"+
                "user.grafik_tb_skoringanak,user.grafik_tb_konfirmasiskoring5,user.grafik_tb_konfirmasiskoring6,user.grafik_tb_sumberobat,"+
                "user.grafik_tb_hasilakhirpengobatan,user.grafik_tb_hasilteshiv,user.kadaluarsa_batch,user.sisa_stok,user.obat_per_resep,user.pemakaian_air_pdam,"+
                "user.limbah_b3_medis,user.grafik_air_pdam_pertanggal,user.grafik_air_pdam_perbulan,user.grafik_limbahb3_pertanggal,user.grafik_limbahb3_perbulan,"+
                "user.limbah_domestik,user.grafik_limbahdomestik_pertanggal,user.grafik_limbahdomestik_perbulan,user.mutu_air_limbah,user.pest_control,"+
                "user.ruang_perpustakaan,user.kategori_perpustakaan,user.jenis_perpustakaan,user.pengarang_perpustakaan,user.penerbit_perpustakaan,"+
                "user.koleksi_perpustakaan,user.inventaris_perpustakaan,user.set_peminjaman_perpustakaan,user.denda_perpustakaan,user.anggota_perpustakaan,"+
                "user.peminjaman_perpustakaan,user.bayar_denda_perpustakaan,user.ebook_perpustakaan,user.jenis_cidera_k3rs,user.penyebab_k3rs,user.jenis_luka_k3rs,"+
                "user.lokasi_kejadian_k3rs,user.dampak_cidera_k3rs,user.jenis_pekerjaan_k3rs,user.bagian_tubuh_k3rs,user.peristiwa_k3rs,user.grafik_k3_pertahun,"+
                "user.grafik_k3_perbulan,user.grafik_k3_pertanggal,user.grafik_k3_perjeniscidera,user.grafik_k3_perpenyebab,user.grafik_k3_perjenisluka,"+
                "user.grafik_k3_lokasikejadian,user.grafik_k3_dampakcidera,user.grafik_k3_perjenispekerjaan,user.grafik_k3_perbagiantubuh,user.jenis_cidera_k3rstahun,"+
                "user.penyebab_k3rstahun,user.jenis_luka_k3rstahun,user.lokasi_kejadian_k3rstahun,user.dampak_cidera_k3rstahun,user.jenis_pekerjaan_k3rstahun,"+
                "user.bagian_tubuh_k3rstahun,user.sekrining_rawat_jalan,user.bpjs_histori_pelayanan,user.rekap_mutasi_berkas,user.skrining_ralan_pernapasan_pertahun,"+
                "user.pengajuan_barang_medis,user.pengajuan_barang_nonmedis,user.grafik_kunjungan_ranapbulan,user.grafik_kunjungan_ranaptanggal,"+
                "user.grafik_kunjungan_ranap_peruang,user.kunjungan_bangsal_pertahun,user.grafik_jenjang_jabatanpegawai,user.grafik_bidangpegawai,"+
                "user.grafik_departemenpegawai,user.grafik_pendidikanpegawai,user.grafik_sttswppegawai,user.grafik_sttskerjapegawai,user.grafik_sttspulangranap,"+
                "user.kip_pasien_ranap,user.kip_pasien_ralan,user.bpjs_mapping_dokterdpjp,user.data_triase_igd,user.master_triase_skala1,user.master_triase_skala2,"+
                "user.master_triase_skala3,user.master_triase_skala4,user.master_triase_skala5,user.master_triase_pemeriksaan,user.master_triase_macamkasus,"+
                "user.rekap_permintaan_diet,user.daftar_pasien_ranap,user.daftar_pasien_ranaptni,user.pengajuan_asetinventaris,user.item_apotek_jenis,"+
                "user.item_apotek_kategori,user.item_apotek_golongan,user.item_apotek_industrifarmasi,user.10_obat_terbanyak_poli,user.grafik_pengajuan_aset_urgensi,"+
                "user.grafik_pengajuan_aset_status,user.grafik_pengajuan_aset_departemen,user.rekap_pengajuan_aset_departemen,user.grafik_kelompok_jabatanpegawai,"+
                "user.grafik_resiko_kerjapegawai,user.grafik_emergency_indexpegawai,user.grafik_inventaris_ruang,user.harian_HAIs2,user.grafik_inventaris_jenis,"+
                "user.data_resume_pasien,user.perkiraan_biaya_ranap,user.rekap_obat_poli,user.rekap_obat_pasien,user.permintaan_perbaikan_inventaris,"+
                "user.grafik_HAIs_pasienbangsal,user.grafik_HAIs_pasienbulan,user.grafik_HAIs_laju_vap,user.grafik_HAIs_laju_iad,user.grafik_HAIs_laju_pleb,"+
                "user.grafik_HAIs_laju_isk,user.grafik_HAIs_laju_ilo,user.grafik_HAIs_laju_hap,user.inhealth_mapping_poli,user.inhealth_mapping_dokter,"+
                "user.inhealth_mapping_tindakan_ralan,user.inhealth_mapping_tindakan_ranap,user.inhealth_mapping_tindakan_radiologi,user.inhealth_mapping_tindakan_laborat,"+
                "user.inhealth_mapping_tindakan_operasi,user.hibah_obat_bhp,user.asal_hibah,user.asuhan_gizi,user.inhealth_kirim_tagihan,user.sirkulasi_obat4,"+
                "user.sirkulasi_obat5,user.sirkulasi_non_medis2,user.monitoring_asuhan_gizi,user.penerimaan_obat_perbulan,user.rekap_kunjungan,user.surat_sakit,"+
                "user.penilaian_awal_keperawatan_ralan,user.permintaan_diet,user.master_masalah_keperawatan,user.pengajuan_cuti,user.kedatangan_pasien,user.utd_pendonor,"+
                "user.toko_suplier,user.toko_jenis,user.toko_set_harga,user.toko_barang,user.penagihan_piutang_pasien,user.akun_penagihan_piutang,user.stok_opname_toko,"+
                "user.toko_riwayat_barang,user.toko_surat_pemesanan,user.toko_pengajuan_barang,user.toko_penerimaan_barang,user.toko_pengadaan_barang,user.toko_hutang,"+
                "user.toko_bayar_pemesanan,user.toko_member,user.toko_penjualan,user.registrasi_poli_per_tanggal,user.toko_piutang,user.toko_retur_beli,user.ipsrs_returbeli,"+
                "user.ipsrs_riwayat_barang,user.pasien_corona,user.toko_pendapatan_harian,user.diagnosa_pasien_corona,user.perawatan_pasien_corona,"+
                "user.penilaian_awal_keperawatan_gigi,user.master_masalah_keperawatan_gigi,user.toko_bayar_piutang,user.toko_piutang_harian,user.toko_penjualan_harian,"+
                "user.deteksi_corona,user.penilaian_awal_keperawatan_kebidanan,user.pengumuman_epasien,user.surat_hamil,user.set_tarif_online,user.booking_periksa,"+
                "user.toko_sirkulasi,user.toko_retur_jual,user.toko_retur_piutang,user.toko_sirkulasi2,user.toko_keuntungan_barang,user.zis_pengeluaran_penerima_dankes,"+
                "user.zis_penghasilan_penerima_dankes,user.zis_ukuran_rumah_penerima_dankes,user.zis_dinding_rumah_penerima_dankes,user.zis_lantai_rumah_penerima_dankes,"+
                "user.zis_atap_rumah_penerima_dankes,user.zis_kepemilikan_rumah_penerima_dankes,user.zis_kamar_mandi_penerima_dankes,user.zis_dapur_rumah_penerima_dankes,"+
                "user.zis_kursi_rumah_penerima_dankes,user.zis_kategori_phbs_penerima_dankes,user.zis_elektronik_penerima_dankes,user.zis_ternak_penerima_dankes,"+
                "user.zis_jenis_simpanan_penerima_dankes,user.penilaian_awal_keperawatan_anak,user.zis_kategori_asnaf_penerima_dankes,user.master_masalah_keperawatan_anak,"+
                "user.master_imunisasi,user.zis_patologis_penerima_dankes,user.pcare_cek_kartu,user.surat_bebas_narkoba,user.surat_keterangan_covid,user.pemakaian_air_tanah,"+
                "user.grafik_air_tanah_pertanggal,user.grafik_air_tanah_perbulan,user.lama_pelayanan_poli,user.hemodialisa,user.laporan_tahunan_irj,"+
                "user.grafik_harian_hemodialisa,user.grafik_bulanan_hemodialisa,user.grafik_tahunan_hemodialisa,user.grafik_bulanan_meninggal,user.perbaikan_inventaris,"+
                "user.surat_cuti_hamil,user.permintaan_stok_obat_pasien,user.pemeliharaan_inventaris,user.klasifikasi_pasien_ranap,user.bulanan_klasifikasi_pasien_ranap,"+
                "user.harian_klasifikasi_pasien_ranap,user.klasifikasi_pasien_perbangsal,user.soap_perawatan,user.klaim_rawat_jalan,user.skrining_gizi,"+
                "user.lama_penyiapan_rm,user.dosis_radiologi,user.demografi_umur_kunjungan,user.jam_diet_pasien,user.rvu_bpjs,user.verifikasi_penerimaan_farmasi,"+
                "user.verifikasi_penerimaan_logistik,user.pemeriksaan_lab_pa,user.ringkasan_pengajuan_obat,user.ringkasan_pemesanan_obat,user.ringkasan_pengadaan_obat,"+
                "user.ringkasan_penerimaan_obat,user.ringkasan_hibah_obat,user.ringkasan_penjualan_obat,user.ringkasan_beri_obat,user.ringkasan_piutang_obat,"+
                "user.ringkasan_stok_keluar_obat,user.ringkasan_retur_suplier_obat,user.ringkasan_retur_pembeli_obat,user.penilaian_awal_keperawatan_ranapkebidanan,"+
                "user.ringkasan_pengajuan_nonmedis,user.ringkasan_pemesanan_nonmedis,user.ringkasan_pengadaan_nonmedis,user.ringkasan_penerimaan_nonmedis,"+
                "user.ringkasan_stokkeluar_nonmedis,user.ringkasan_returbeli_nonmedis,user.omset_penerimaan,user.validasi_penagihan_piutang,user.permintaan_ranap,"+
                "user.bpjs_diagnosa_prb,user.bpjs_obat_prb,user.bpjs_surat_kontrol,user.penggunaan_bhp_ok,user.surat_keterangan_rawat_inap,user.surat_keterangan_sehat,"+
                "user.pendapatan_per_carabayar,user.akun_host_to_host_bank_jateng,user.pembayaran_bank_jateng,user.bpjs_surat_pri,user.ringkasan_tindakan,"+
                "user.lama_pelayanan_pasien,user.surat_sakit_pihak_2,user.tagihan_hutang_obat,user.referensi_mobilejkn_bpjs,user.batal_pendaftaran_mobilejkn_bpjs,"+
                "user.lama_operasi,user.grafik_inventaris_kategori,user.grafik_inventaris_merk,user.grafik_inventaris_produsen,user.pengembalian_deposit_pasien,"+
                "user.validasi_tagihan_hutang_obat,user.piutang_obat_belum_lunas,user.integrasi_briapi,user.pengadaan_aset_inventaris,user.akun_aset_inventaris,"+
                "user.suplier_inventaris,user.penerimaan_aset_inventaris,user.bayar_pemesanan_iventaris,user.hutang_aset_inventaris,user.hibah_aset_inventaris,"+
                "user.titip_faktur_non_medis,user.validasi_tagihan_non_medis,user.titip_faktur_aset,user.validasi_tagihan_aset,user.hibah_non_medis,"+
                "user.pcare_alasan_tacc,user.resep_luar,user.surat_bebas_tbc,user.surat_buta_warna,user.surat_bebas_tato,user.surat_kewaspadaan_kesehatan,"+
                "user.grafik_porsidiet_pertanggal,user.grafik_porsidiet_perbulan,user.grafik_porsidiet_pertahun,user.grafik_porsidiet_perbangsal,"+
                "user.penilaian_awal_medis_ralan,user.master_masalah_keperawatan_mata,user.penilaian_awal_keperawatan_mata,user.penilaian_awal_medis_ranap,"+
                "user.penilaian_awal_medis_ranap_kebidanan,user.penilaian_awal_medis_ralan_kebidanan,user.penilaian_awal_medis_igd,user.penilaian_awal_medis_ralan_anak,"+
                "user.bpjs_referensi_poli_hfis,user.bpjs_referensi_dokter_hfis,user.bpjs_referensi_jadwal_hfis,user.penilaian_fisioterapi,user.bpjs_program_prb,"+
                "user.bpjs_suplesi_jasaraharja,user.bpjs_data_induk_kecelakaan,user.bpjs_sep_internal,user.bpjs_klaim_jasa_raharja,user.bpjs_daftar_finger_print,"+
                "user.bpjs_rujukan_khusus,user.pemeliharaan_gedung,user.grafik_perbaikan_inventaris_pertanggal,user.grafik_perbaikan_inventaris_perbulan,"+
                "user.grafik_perbaikan_inventaris_pertahun,user.grafik_perbaikan_inventaris_perpelaksana_status,user.penilaian_mcu,user.peminjam_piutang,"+
                "user.piutang_lainlain,user.cara_bayar,user.audit_kepatuhan_apd,user.bpjs_task_id,user.bayar_piutang_lain,user.pembayaran_akun_bayar4,"+
                "user.stok_akhir_farmasi_pertanggal,user.riwayat_kamar_pasien,user.uji_fungsi_kfr,user.hapus_berkas_digital_perawatan,user.kategori_pengeluaran_harian,"+
                "user.kategori_pemasukan_lain,user.pembayaran_akun_bayar5,user.ruang_ok,user.telaah_resep,user.jasa_tindakan_pasien,user.permintaan_resep_pulang,"+
                "user.rekap_jm_dokter,user.status_data_rm,user.ubah_petugas_lab_pk,user.ubah_petugas_lab_pa,user.ubah_petugas_radiologi,user.gabung_norawat,"+
                "user.gabung_rm,user.ringkasan_biaya_obat_pasien_pertanggal,user.master_masalah_keperawatan_igd,user.penilaian_awal_keperawatan_igd,"+
                "user.bpjs_referensi_dpho_apotek,user.bpjs_referensi_poli_apotek,user.bayar_jm_dokter,user.bpjs_referensi_faskes_apotek,user.bpjs_referensi_spesialistik_apotek,"+
                "user.pembayaran_briva,user.penilaian_awal_keperawatan_ranap,user.nilai_penerimaan_vendor_farmasi_perbulan,user.akun_bayar_hutang,user.master_rencana_keperawatan,"+
                "user.laporan_tahunan_igd,user.obat_bhp_tidakbergerak,user.ringkasan_hutang_vendor_farmasi,user.nilai_penerimaan_vendor_nonmedis_perbulan,"+
                "user.ringkasan_hutang_vendor_nonmedis,user.master_rencana_keperawatan_anak,user.anggota_polri_dirawat,user.daftar_pasien_ranap_polri,user.soap_ralan_polri,"+
                "user.soap_ranap_polri,user.laporan_penyakit_polri,user.jumlah_pengunjung_ralan_polri,user.catatan_observasi_igd,user.catatan_observasi_ranap,"+
                "user.catatan_observasi_ranap_kebidanan,user.catatan_observasi_ranap_postpartum,user.penilaian_awal_medis_ralan_tht,user.penilaian_psikologi,"+
                "user.audit_cuci_tangan_medis,user.audit_pembuangan_limbah,user.ruang_audit_kepatuhan,user.audit_pembuangan_benda_tajam,user.audit_penanganan_darah,"+
                "user.audit_pengelolaan_linen_kotor,user.audit_penempatan_pasien,user.audit_kamar_jenazah,user.audit_bundle_iadp,user.audit_bundle_ido,"+
                "user.audit_fasilitas_kebersihan_tangan,user.audit_fasilitas_apd,user.audit_pembuangan_limbah_cair_infeksius,user.audit_sterilisasi_alat,"+
                "user.penilaian_awal_medis_ralan_psikiatri,user.persetujuan_penolakan_tindakan,user.audit_bundle_isk,user.audit_bundle_plabsi,user.audit_bundle_vap,"+
                "user.akun_host_to_host_bank_papua,user.pembayaran_bank_papua,user.penilaian_awal_medis_ralan_penyakit_dalam,user.penilaian_awal_medis_ralan_mata,"+
                "user.penilaian_awal_medis_ralan_neurologi,user.sirkulasi_obat6,user.penilaian_awal_medis_ralan_orthopedi,user.penilaian_awal_medis_ralan_bedah,"+
                "user.integrasi_khanza_health_services,user.soap_ralan_tni,user.soap_ranap_tni,user.jumlah_pengunjung_ralan_tni,user.laporan_penyakit_tni,"+
                "user.catatan_keperawatan_ranap,user.master_rencana_keperawatan_gigi,user.master_rencana_keperawatan_mata,user.master_rencana_keperawatan_igd,"+
                "user.master_masalah_keperawatan_psikiatri,user.master_rencana_keperawatan_psikiatri,user.penilaian_awal_keperawatan_psikiatri,user.pemantauan_pews_anak,"+
                "user.surat_pulang_atas_permintaan_sendiri,user.template_hasil_radiologi,user.laporan_bulanan_irj,user.template_pemeriksaan,user.pemeriksaan_lab_mb,"+
                "user.ubah_petugas_lab_mb,user.penilaian_pre_operasi,user.penilaian_pre_anestesi,user.perencanaan_pemulangan,user.penilaian_lanjutan_resiko_jatuh_dewasa,"+
                "user.penilaian_lanjutan_resiko_jatuh_anak,user.penilaian_awal_medis_ralan_geriatri,user.penilaian_tambahan_pasien_geriatri,user.skrining_nutrisi_dewasa,"+
                "user.skrining_nutrisi_lansia,user.hasil_pemeriksaan_usg,user.skrining_nutrisi_anak,user.akun_host_to_host_bank_jabar,user.pembayaran_bank_jabar,"+
                "user.surat_pernyataan_pasien_umum,user.konseling_farmasi,user.pelayanan_informasi_obat,user.jawaban_pio_apoteker,user.surat_persetujuan_umum,"+
                "user.transfer_pasien_antar_ruang,user.satu_sehat_referensi_dokter,user.satu_sehat_referensi_pasien,user.satu_sehat_mapping_departemen,"+
                "user.satu_sehat_mapping_lokasi,user.satu_sehat_kirim_encounter,user.catatan_cek_gds,user.satu_sehat_kirim_condition,user.checklist_pre_operasi,"+
                "user.satu_sehat_kirim_observationttv,user.signin_sebelum_anestesi,user.satu_sehat_kirim_procedure,user.operasi_per_bulan,user.timeout_sebelum_insisi,"+
                "user.signout_sebelum_menutup_luka,user.dapur_barang,user.dapur_opname,user.satu_sehat_mapping_vaksin,user.dapur_suplier,user.satu_sehat_kirim_Immunization,"+
                "user.checklist_post_operasi,user.dapur_pembelian,user.dapur_stok_keluar,user.dapur_riwayat_barang,user.permintaan_dapur,user.rekonsiliasi_obat,"+
                "user.biaya_pengadaan_dapur,user.rekap_pengadaan_dapur,user.kesling_limbah_b3medis_cair,user.grafik_limbahb3cair_pertanggal,user.grafik_limbahb3cair_perbulan,"+
                "user.rekap_biaya_registrasi,user.konfirmasi_rekonsiliasi_obat,user.satu_sehat_kirim_clinicalimpression,user.penilaian_pasien_terminal,"+
                "user.surat_persetujuan_rawat_inap,user.monitoring_reaksi_tranfusi,user.penilaian_korban_kekerasan,user.penilaian_lanjutan_resiko_jatuh_lansia,"+
                "user.penilaian_pasien_penyakit_menular,user.mpp_skrining,user.edukasi_pasien_keluarga_rj,user.pemantauan_pews_dewasa,user.penilaian_tambahan_bunuh_diri,"+
                "user.bpjs_antrean_pertanggal,user.penilaian_tambahan_perilaku_kekerasan,user.penilaian_tambahan_beresiko_melarikan_diri,user.persetujuan_penundaan_pelayanan,"+
                "user.sisa_diet_pasien,user.penilaian_awal_medis_ralan_bedah_mulut,user.penilaian_pasien_keracunan,user.pemantauan_meows_obstetri,user.catatan_adime_gizi,"+
                "user.pengajuan_biaya,user.penilaian_awal_keperawatan_ralan_geriatri,user.master_masalah_keperawatan_geriatri,user.master_rencana_keperawatan_geriatri,"+
                "user.checklist_kriteria_masuk_hcu,user.checklist_kriteria_keluar_hcu,user.penilaian_risiko_dekubitus,user.master_menolak_anjuran_medis,user.penolakan_anjuran_medis,"+
                "user.laporan_tahunan_penolakan_anjuran_medis,user.template_laporan_operasi,user.hasil_tindakan_eswl,user.checklist_kriteria_masuk_icu,"+
                "user.checklist_kriteria_keluar_icu,user.akses_dokter_lain_rawat_jalan,user.follow_up_dbd,user.penilaian_risiko_jatuh_neonatus,user.persetujuan_pengajuan_biaya,"+
                "user.pemeriksaan_fisik_ralan_per_penyakit,user.penilaian_lanjutan_resiko_jatuh_geriatri,user.pemantauan_ews_neonatus,user.validasi_persetujuan_pengajuan_biaya,"+
                "user.riwayat_perawatan_icare_bpjs,user.rekap_pengajuan_biaya,user.penilaian_awal_medis_ralan_kulit_kelamin,user.akun_host_to_host_bank_mandiri,"+
                "user.penilaian_medis_hemodialisa,user.penilaian_level_kecemasan_ranap_anak,user.penilaian_lanjutan_resiko_jatuh_psikiatri,user.penilaian_lanjutan_skrining_fungsional,"+
                "user.penilaian_medis_ralan_rehab_medik,user.laporan_anestesi,user.template_persetujuan_penolakan_tindakan,user.penilaian_medis_ralan_gawat_darurat_psikiatri,"+
                "user.bpjs_referensi_setting_apotek,user.bpjs_referensi_obat_apotek,user.bpjs_mapping_obat_apotek,user.pembayaran_bank_mandiri,user.penilaian_ulang_nyeri,"+
                "user.penilaian_terapi_wicara,user.bpjs_obat_23hari_apotek,user.pengkajian_restrain,user.bpjs_kunjungan_sep_apotek,user.bpjs_monitoring_klaim_apotek,"+
                "user.bpjs_daftar_pelayanan_obat_apotek,user.penilaian_awal_medis_ralan_paru,user.catatan_keperawatan_ralan,user.catatan_persalinan,user.skor_aldrette_pasca_anestesi,"+
                "user.skor_steward_pasca_anestesi,user.skor_bromage_pasca_anestesi,user.penilaian_pre_induksi,user.hasil_usg_urologi,user.hasil_usg_gynecologi,user.hasil_pemeriksaan_ekg,"+
                "user.hapus_edit_sep_bpjs,user.satu_sehat_kirim_diet,user.satu_sehat_mapping_obat,user.dapur_ringkasan_pembelian,user.satu_sehat_kirim_medication,"+
                "user.satu_sehat_kirim_medicationrequest,user.penatalaksanaan_terapi_okupasi,user.satu_sehat_kirim_medicationdispense,user.hasil_usg_neonatus,"+
                "user.hasil_endoskopi_faring_laring,user.satu_sehat_mapping_radiologi,user.satu_sehat_kirim_servicerequest_radiologi,user.hasil_endoskopi_hidung,"+
                "user.satu_sehat_kirim_specimen_radiologi,user.master_masalah_keperawatan_neonatus,user.master_rencana_keperawatan_neonatus,user.penilaian_awal_keperawatan_ranap_neonatus,"+
                "user.satu_sehat_kirim_observation_radiologi,user.satu_sehat_kirim_diagnosticreport_radiologi,user.hasil_endoskopi_telinga,user.satu_sehat_mapping_lab,"+
                "user.satu_sehat_kirim_servicerequest_lab,user.satu_sehat_kirim_servicerequest_labmb,user.satu_sehat_kirim_specimen_lab,user.satu_sehat_kirim_specimen_labmb,"+
                "user.satu_sehat_kirim_observation_lab,user.satu_sehat_kirim_observation_labmb,user.satu_sehat_kirim_diagnosticreport_lab,user.satu_sehat_kirim_diagnosticreport_labmb,"+
                "user.kepatuhan_kelengkapan_keselamatan_bedah,user.nilai_piutang_perjenis_bayar_per_bulan,user.ringkasan_piutang_jenis_bayar,user.penilaian_pasien_imunitas_rendah,"+
                "user.balance_cairan,user.catatan_observasi_chbp,user.catatan_observasi_induksi_persalinan,user.skp_kategori_penilaian,user.skp_kriteria_penilaian,"+
                "user.skp_penilaian,user.referensi_poli_mobilejknfktp,user.referensi_dokter_mobilejknfktp,user.skp_rekapitulasi_penilaian,user.pembayaran_pihak_ke3_bankmandiri,"+
                "user.metode_pembayaran_bankmandiri,user.bank_tujuan_transfer_bankmandiri,user.kodetransaksi_tujuan_transfer_bankmandiri,user.konsultasi_medik,user.jawaban_konsultasi_medik,"+
                "user.pcare_cek_alergi,user.pcare_cek_prognosa,user.data_sasaran_usiaproduktif,user.data_sasaran_usialansia,user.skrining_perilaku_merokok_sekolah_remaja,"+
                "user.skrining_kekerasan_pada_perempuan,user.skrining_obesitas,user.skrining_risiko_kanker_payudara,user.skrining_risiko_kanker_paru,user.skrining_tbc,"+
                "user.skrining_kesehatan_gigi_mulut_remaja,user.penilaian_awal_keperawatan_ranap_bayi,user.booking_mcu_perusahaan,user.catatan_observasi_restrain_nonfarma,"+
                "user.catatan_observasi_ventilator,user.catatan_anestesi_sedasi,user.skrining_puma,user.satu_sehat_kirim_careplan,user.satu_sehat_kirim_medicationstatement,"+
                "user.skrining_adiksi_nikotin,user.skrining_thalassemia,user.skrining_instrumen_sdq,user.skrining_instrumen_srq,user.checklist_pemberian_fibrinolitik,"+
                "user.skrining_kanker_kolorektal,user.dapur_pemesanan,user.bayar_pesan_dapur,user.hutang_dapur from user where user.id_user=AES_ENCRYPT(?,'nur')");
            try {
                ps.setString(1,user);
                rs=ps.executeQuery();
                if(rs.next()){
                    penyakit=rs.getBoolean("penyakit");obat_penyakit=rs.getBoolean("obat_penyakit");dokter=rs.getBoolean("dokter");jadwal_praktek=rs.getBoolean("jadwal_praktek");petugas=rs.getBoolean("petugas");pasien=rs.getBoolean("pasien");registrasi=rs.getBoolean("registrasi");tindakan_ralan=rs.getBoolean("tindakan_ralan");kamar_inap=rs.getBoolean("kamar_inap");tindakan_ranap=rs.getBoolean("tindakan_ranap");operasi=rs.getBoolean("operasi");rujukan_keluar=rs.getBoolean("rujukan_keluar");rujukan_masuk=rs.getBoolean("rujukan_masuk");
                    beri_obat=rs.getBoolean("beri_obat");resep_pulang=rs.getBoolean("resep_pulang");pasien_meninggal=rs.getBoolean("pasien_meninggal");diet_pasien=rs.getBoolean("diet_pasien");kelahiran_bayi=rs.getBoolean("kelahiran_bayi");periksa_lab=rs.getBoolean("periksa_lab");periksa_radiologi=rs.getBoolean("periksa_radiologi");kasir_ralan=rs.getBoolean("kasir_ralan");deposit_pasien=rs.getBoolean("deposit_pasien");piutang_pasien=rs.getBoolean("piutang_pasien");peminjaman_berkas=rs.getBoolean("peminjaman_berkas");barcode=rs.getBoolean("barcode");
                    presensi_harian=rs.getBoolean("presensi_harian");presensi_bulanan=rs.getBoolean("presensi_bulanan");pegawai_admin=rs.getBoolean("pegawai_admin");pegawai_user=rs.getBoolean("pegawai_user");suplier=rs.getBoolean("suplier");satuan_barang=rs.getBoolean("satuan_barang");konversi_satuan=rs.getBoolean("konversi_satuan");jenis_barang=rs.getBoolean("jenis_barang");obat=rs.getBoolean("obat");stok_opname_obat=rs.getBoolean("stok_opname_obat");stok_obat_pasien=rs.getBoolean("stok_obat_pasien");pengadaan_obat=rs.getBoolean("pengadaan_obat");
                    pemesanan_obat=rs.getBoolean("pemesanan_obat");penjualan_obat=rs.getBoolean("penjualan_obat");piutang_obat=rs.getBoolean("piutang_obat");retur_ke_suplier=rs.getBoolean("retur_ke_suplier");retur_dari_pembeli=rs.getBoolean("retur_dari_pembeli");retur_obat_ranap=rs.getBoolean("retur_obat_ranap");retur_piutang_pasien=rs.getBoolean("retur_piutang_pasien");keuntungan_penjualan=rs.getBoolean("keuntungan_penjualan");keuntungan_beri_obat=rs.getBoolean("keuntungan_beri_obat");sirkulasi_obat=rs.getBoolean("sirkulasi_obat");
                    ipsrs_barang=rs.getBoolean("ipsrs_barang");ipsrs_pengadaan_barang=rs.getBoolean("ipsrs_pengadaan_barang");ipsrs_stok_keluar=rs.getBoolean("ipsrs_stok_keluar");ipsrs_rekap_pengadaan=rs.getBoolean("ipsrs_rekap_pengadaan");ipsrs_rekap_stok_keluar=rs.getBoolean("ipsrs_rekap_stok_keluar");ipsrs_pengeluaran_harian=rs.getBoolean("ipsrs_pengeluaran_harian");inventaris_jenis=rs.getBoolean("inventaris_jenis");inventaris_kategori=rs.getBoolean("inventaris_kategori");inventaris_merk=rs.getBoolean("inventaris_merk");
                    inventaris_ruang=rs.getBoolean("inventaris_ruang");inventaris_produsen=rs.getBoolean("inventaris_produsen");inventaris_koleksi=rs.getBoolean("inventaris_koleksi");inventaris_inventaris=rs.getBoolean("inventaris_inventaris");inventaris_sirkulasi=rs.getBoolean("inventaris_sirkulasi");parkir_jenis=rs.getBoolean("parkir_jenis");parkir_in=rs.getBoolean("parkir_in");parkir_out=rs.getBoolean("parkir_out");parkir_rekap_harian=rs.getBoolean("parkir_rekap_harian");parkir_rekap_bulanan=rs.getBoolean("parkir_rekap_bulanan");
                    informasi_kamar=rs.getBoolean("informasi_kamar");harian_tindakan_poli=rs.getBoolean("harian_tindakan_poli");obat_per_poli=rs.getBoolean("obat_per_poli");obat_per_kamar=rs.getBoolean("obat_per_kamar");obat_per_dokter_ralan=rs.getBoolean("obat_per_dokter_ralan");obat_per_dokter_ranap=rs.getBoolean("obat_per_dokter_ranap");harian_dokter=rs.getBoolean("harian_dokter");bulanan_dokter=rs.getBoolean("bulanan_dokter");harian_paramedis=rs.getBoolean("harian_paramedis");bulanan_paramedis=rs.getBoolean("bulanan_paramedis");
                    pembayaran_ralan=rs.getBoolean("pembayaran_ralan");pembayaran_ranap=rs.getBoolean("pembayaran_ranap");rekap_pembayaran_ralan=rs.getBoolean("rekap_pembayaran_ralan");rekap_pembayaran_ranap=rs.getBoolean("rekap_pembayaran_ranap");tagihan_masuk=rs.getBoolean("tagihan_masuk");tambahan_biaya=rs.getBoolean("tambahan_biaya");potongan_biaya=rs.getBoolean("potongan_biaya");resep_obat=rs.getBoolean("resep_obat");resume_pasien=rs.getBoolean("resume_pasien");penyakit_ralan=rs.getBoolean("penyakit_ralan");
                    penyakit_ranap=rs.getBoolean("penyakit_ranap");kamar=rs.getBoolean("kamar");tarif_ralan=rs.getBoolean("tarif_ralan");tarif_ranap=rs.getBoolean("tarif_ranap");tarif_lab=rs.getBoolean("tarif_lab");tarif_radiologi=rs.getBoolean("tarif_radiologi");tarif_operasi=rs.getBoolean("tarif_operasi");akun_rekening=rs.getBoolean("akun_rekening");rekening_tahun=rs.getBoolean("rekening_tahun");posting_jurnal=rs.getBoolean("posting_jurnal");buku_besar=rs.getBoolean("buku_besar");cashflow=rs.getBoolean("cashflow");
                    keuangan=rs.getBoolean("keuangan");pengeluaran=rs.getBoolean("pengeluaran");setup_pjlab=rs.getBoolean("setup_pjlab");setup_otolokasi=rs.getBoolean("setup_otolokasi");setup_jam_kamin=rs.getBoolean("setup_jam_kamin");setup_embalase=rs.getBoolean("setup_embalase");tracer_login=rs.getBoolean("tracer_login");display=rs.getBoolean("display");set_harga_obat=rs.getBoolean("set_harga_obat");set_penggunaan_tarif=rs.getBoolean("set_penggunaan_tarif");set_oto_ralan=rs.getBoolean("set_oto_ralan");biaya_harian=rs.getBoolean("biaya_harian");
                    biaya_masuk_sekali=rs.getBoolean("biaya_masuk_sekali");set_no_rm=rs.getBoolean("set_no_rm");billing_ralan=rs.getBoolean("billing_ralan");billing_ranap=rs.getBoolean("billing_ranap");jm_ranap_dokter=rs.getBoolean("jm_ranap_dokter");igd=rs.getBoolean("igd");barcoderalan=rs.getBoolean("barcoderalan");barcoderanap=rs.getBoolean("barcoderanap");set_harga_obat_ralan=rs.getBoolean("set_harga_obat_ralan");set_harga_obat_ranap=rs.getBoolean("set_harga_obat_ranap");penyakit_pd3i=rs.getBoolean("penyakit_pd3i");
                    surveilans_pd3i=rs.getBoolean("surveilans_pd3i");surveilans_ralan=rs.getBoolean("surveilans_ralan");diagnosa_pasien=rs.getBoolean("diagnosa_pasien");surveilans_ranap=rs.getBoolean("surveilans_ranap");pny_takmenular_ranap=rs.getBoolean("pny_takmenular_ranap");pny_takmenular_ralan=rs.getBoolean("pny_takmenular_ralan");kunjungan_ralan=rs.getBoolean("kunjungan_ralan");rl32=rs.getBoolean("rl32");rl33=rs.getBoolean("rl33");rl37=rs.getBoolean("rl37");rl38=rs.getBoolean("rl38");harian_tindakan_dokter=rs.getBoolean("harian_tindakan_dokter");
                    sms=rs.getBoolean("sms");sidikjari=rs.getBoolean("sidikjari");jam_masuk=rs.getBoolean("jam_masuk");jadwal_pegawai=rs.getBoolean("jadwal_pegawai");parkir_barcode=rs.getBoolean("parkir_barcode");set_nota=rs.getBoolean("set_nota");dpjp_ranap=rs.getBoolean("dpjp_ranap");mutasi_barang=rs.getBoolean("mutasi_barang");rl34=rs.getBoolean("rl34");rl36=rs.getBoolean("rl36");fee_visit_dokter=rs.getBoolean("fee_visit_dokter");fee_bacaan_ekg=rs.getBoolean("fee_bacaan_ekg");fee_rujukan_rontgen=rs.getBoolean("fee_rujukan_rontgen");
                    fee_rujukan_ranap=rs.getBoolean("fee_rujukan_ranap");fee_ralan=rs.getBoolean("fee_ralan");akun_bayar=rs.getBoolean("akun_bayar");bayar_pemesanan_obat=rs.getBoolean("bayar_pemesanan_obat");obat_per_dokter_peresep=rs.getBoolean("obat_per_dokter_peresep");ipsrs_jenis_barang=rs.getBoolean("ipsrs_jenis_barang");pemasukan_lain=rs.getBoolean("pemasukan_lain");pengaturan_rekening=rs.getBoolean("pengaturan_rekening");closing_kasir=rs.getBoolean("closing_kasir");keterlambatan_presensi=rs.getBoolean("keterlambatan_presensi");
                    set_harga_kamar=rs.getBoolean("set_harga_kamar");rekap_per_shift=rs.getBoolean("rekap_per_shift");bpjs_cek_nik=rs.getBoolean("bpjs_cek_nik");bpjs_cek_kartu=rs.getBoolean("bpjs_cek_kartu");bpjs_cek_riwayat=rs.getBoolean("bpjs_cek_riwayat");obat_per_cara_bayar=rs.getBoolean("obat_per_cara_bayar");kunjungan_ranap=rs.getBoolean("kunjungan_ranap");bayar_piutang=rs.getBoolean("bayar_piutang");payment_point=rs.getBoolean("payment_point");bpjs_cek_nomor_rujukan=rs.getBoolean("bpjs_cek_nomor_rujukan");icd9=rs.getBoolean("icd9");
                    darurat_stok=rs.getBoolean("darurat_stok");retensi_rm=rs.getBoolean("retensi_rm");temporary_presensi=rs.getBoolean("temporary_presensi");jurnal_harian=rs.getBoolean("jurnal_harian");sirkulasi_obat2=rs.getBoolean("sirkulasi_obat2");edit_registrasi=rs.getBoolean("edit_registrasi");bpjs_referensi_diagnosa=rs.getBoolean("bpjs_referensi_diagnosa");bpjs_referensi_poli=rs.getBoolean("bpjs_referensi_poli");industrifarmasi=rs.getBoolean("industrifarmasi");harian_js=rs.getBoolean("harian_js");bulanan_js=rs.getBoolean("bulanan_js");
                    harian_paket_bhp=rs.getBoolean("harian_paket_bhp");bulanan_paket_bhp=rs.getBoolean("bulanan_paket_bhp");piutang_pasien2=rs.getBoolean("piutang_pasien2");bpjs_referensi_faskes=rs.getBoolean("bpjs_referensi_faskes");bpjs_sep=rs.getBoolean("bpjs_sep");pengambilan_utd=rs.getBoolean("pengambilan_utd");tarif_utd=rs.getBoolean("tarif_utd");pengambilan_utd2=rs.getBoolean("pengambilan_utd2");utd_medis_rusak=rs.getBoolean("utd_medis_rusak");pengambilan_penunjang_utd=rs.getBoolean("pengambilan_penunjang_utd");
                    pengambilan_penunjang_utd2=rs.getBoolean("pengambilan_penunjang_utd2");utd_penunjang_rusak=rs.getBoolean("utd_penunjang_rusak");suplier_penunjang=rs.getBoolean("suplier_penunjang");utd_donor=rs.getBoolean("utd_donor");bpjs_monitoring_klaim=rs.getBoolean("bpjs_monitoring_klaim");utd_cekal_darah=rs.getBoolean("utd_cekal_darah");utd_komponen_darah=rs.getBoolean("utd_komponen_darah");utd_stok_darah=rs.getBoolean("utd_stok_darah");utd_pemisahan_darah=rs.getBoolean("utd_pemisahan_darah");harian_kamar=rs.getBoolean("harian_kamar");
                    rincian_piutang_pasien=rs.getBoolean("rincian_piutang_pasien");keuntungan_beri_obat_nonpiutang=rs.getBoolean("keuntungan_beri_obat_nonpiutang");reklasifikasi_ralan=rs.getBoolean("reklasifikasi_ralan");reklasifikasi_ranap=rs.getBoolean("reklasifikasi_ranap");utd_penyerahan_darah=rs.getBoolean("utd_penyerahan_darah");hutang_obat=rs.getBoolean("hutang_obat");riwayat_obat_alkes_bhp=rs.getBoolean("riwayat_obat_alkes_bhp");sensus_harian_poli=rs.getBoolean("sensus_harian_poli");rl4a=rs.getBoolean("rl4a");
                    aplicare_referensi_kamar=rs.getBoolean("aplicare_referensi_kamar");aplicare_ketersediaan_kamar=rs.getBoolean("aplicare_ketersediaan_kamar");inacbg_klaim_baru_otomatis=rs.getBoolean("inacbg_klaim_baru_otomatis");inacbg_klaim_baru_manual=rs.getBoolean("inacbg_klaim_baru_manual");inacbg_coder_nik=rs.getBoolean("inacbg_coder_nik");mutasi_berkas=rs.getBoolean("mutasi_berkas");akun_piutang=rs.getBoolean("akun_piutang");harian_kso=rs.getBoolean("harian_kso");bulanan_kso=rs.getBoolean("bulanan_kso");
                    harian_menejemen=rs.getBoolean("harian_menejemen");bulanan_menejemen=rs.getBoolean("bulanan_menejemen");inhealth_cek_eligibilitas=rs.getBoolean("inhealth_cek_eligibilitas");inhealth_referensi_jenpel_ruang_rawat=rs.getBoolean("inhealth_referensi_jenpel_ruang_rawat");inhealth_referensi_poli=rs.getBoolean("inhealth_referensi_poli");inhealth_referensi_faskes=rs.getBoolean("inhealth_referensi_faskes");inhealth_sjp=rs.getBoolean("inhealth_sjp");piutang_ralan=rs.getBoolean("piutang_ralan");piutang_ranap=rs.getBoolean("piutang_ranap");
                    detail_piutang_penjab=rs.getBoolean("detail_piutang_penjab");lama_pelayanan_ralan=rs.getBoolean("lama_pelayanan_ralan");catatan_pasien=rs.getBoolean("catatan_pasien");rl4b=rs.getBoolean("rl4b");rl4asebab=rs.getBoolean("rl4asebab");rl4bsebab=rs.getBoolean("rl4bsebab");data_HAIs=rs.getBoolean("data_HAIs");harian_HAIs=rs.getBoolean("harian_HAIs");bulanan_HAIs=rs.getBoolean("bulanan_HAIs");hitung_bor=rs.getBoolean("hitung_bor");perusahaan_pasien=rs.getBoolean("perusahaan_pasien");resep_dokter=rs.getBoolean("resep_dokter");
                    lama_pelayanan_apotek=rs.getBoolean("lama_pelayanan_apotek");hitung_alos=rs.getBoolean("hitung_alos");detail_tindakan=rs.getBoolean("detail_tindakan");rujukan_poli_internal=rs.getBoolean("rujukan_poli_internal");rekap_poli_anak=rs.getBoolean("rekap_poli_anak");grafik_kunjungan_poli=rs.getBoolean("grafik_kunjungan_poli");grafik_kunjungan_perdokter=rs.getBoolean("grafik_kunjungan_perdokter");grafik_kunjungan_perpekerjaan=rs.getBoolean("grafik_kunjungan_perpekerjaan");grafik_kunjungan_perpendidikan=rs.getBoolean("grafik_kunjungan_perpendidikan");
                    grafik_kunjungan_pertahun=rs.getBoolean("grafik_kunjungan_pertahun");berkas_digital_perawatan=rs.getBoolean("berkas_digital_perawatan");penyakit_menular_ranap=rs.getBoolean("penyakit_menular_ranap");penyakit_menular_ralan=rs.getBoolean("penyakit_menular_ralan");grafik_kunjungan_perbulan=rs.getBoolean("grafik_kunjungan_perbulan");grafik_kunjungan_pertanggal=rs.getBoolean("grafik_kunjungan_pertanggal");grafik_kunjungan_demografi=rs.getBoolean("grafik_kunjungan_demografi");grafik_kunjungan_statusdaftartahun=rs.getBoolean("grafik_kunjungan_statusdaftartahun");
                    grafik_kunjungan_statusdaftartahun2=rs.getBoolean("grafik_kunjungan_statusdaftartahun2");grafik_kunjungan_statusdaftarbulan=rs.getBoolean("grafik_kunjungan_statusdaftarbulan");grafik_kunjungan_statusdaftarbulan2=rs.getBoolean("grafik_kunjungan_statusdaftarbulan2");grafik_kunjungan_statusdaftartanggal=rs.getBoolean("grafik_kunjungan_statusdaftartanggal");grafik_kunjungan_statusdaftartanggal2=rs.getBoolean("grafik_kunjungan_statusdaftartanggal2");grafik_kunjungan_statusbataltahun=rs.getBoolean("grafik_kunjungan_statusbataltahun");
                    grafik_kunjungan_statusbatalbulan=rs.getBoolean("grafik_kunjungan_statusbatalbulan");pcare_cek_penyakit=rs.getBoolean("pcare_cek_penyakit");grafik_kunjungan_statusbataltanggal=rs.getBoolean("grafik_kunjungan_statusbataltanggal");kategori_barang=rs.getBoolean("kategori_barang");golongan_barang=rs.getBoolean("golongan_barang");pemberian_obat_pertanggal=rs.getBoolean("pemberian_obat_pertanggal");penjualan_obat_pertanggal=rs.getBoolean("penjualan_obat_pertanggal");pcare_cek_kesadaran=rs.getBoolean("pcare_cek_kesadaran");
                    pembatalan_periksa_dokter=rs.getBoolean("pembatalan_periksa_dokter");pembayaran_per_unit=rs.getBoolean("pembayaran_per_unit");rekap_pembayaran_per_unit=rs.getBoolean("rekap_pembayaran_per_unit");grafik_kunjungan_percarabayar=rs.getBoolean("grafik_kunjungan_percarabayar");ipsrs_pengadaan_pertanggal=rs.getBoolean("ipsrs_pengadaan_pertanggal");ipsrs_stokkeluar_pertanggal=rs.getBoolean("ipsrs_stokkeluar_pertanggal");grafik_kunjungan_ranaptahun=rs.getBoolean("grafik_kunjungan_ranaptahun");pcare_cek_rujukan=rs.getBoolean("pcare_cek_rujukan");
                    grafik_lab_ralantahun=rs.getBoolean("grafik_lab_ralantahun");grafik_rad_ralantahun=rs.getBoolean("grafik_rad_ralantahun");cek_entry_ralan=rs.getBoolean("cek_entry_ralan");inacbg_klaim_baru_manual2=rs.getBoolean("inacbg_klaim_baru_manual2");permintaan_medis=rs.getBoolean("permintaan_medis");rekap_permintaan_medis=rs.getBoolean("rekap_permintaan_medis");surat_pemesanan_medis=rs.getBoolean("surat_pemesanan_medis");permintaan_non_medis=rs.getBoolean("permintaan_non_medis");rekap_permintaan_non_medis=rs.getBoolean("rekap_permintaan_non_medis");
                    surat_pemesanan_non_medis=rs.getBoolean("surat_pemesanan_non_medis");grafik_per_perujuk=rs.getBoolean("grafik_per_perujuk");bpjs_cek_prosedur=rs.getBoolean("bpjs_cek_prosedur");bpjs_cek_kelas_rawat=rs.getBoolean("bpjs_cek_kelas_rawat");bpjs_cek_dokter=rs.getBoolean("bpjs_cek_dokter");bpjs_cek_spesialistik=rs.getBoolean("bpjs_cek_spesialistik");bpjs_cek_ruangrawat=rs.getBoolean("bpjs_cek_ruangrawat");bpjs_cek_carakeluar=rs.getBoolean("bpjs_cek_carakeluar");bpjs_cek_pasca_pulang=rs.getBoolean("bpjs_cek_pasca_pulang");
                    detail_tindakan_okvk=rs.getBoolean("detail_tindakan_okvk");billing_parsial=rs.getBoolean("billing_parsial");bpjs_cek_nomor_rujukan_rs=rs.getBoolean("bpjs_cek_nomor_rujukan_rs");bpjs_cek_rujukan_kartu_pcare=rs.getBoolean("bpjs_cek_rujukan_kartu_pcare");bpjs_cek_rujukan_kartu_rs=rs.getBoolean("bpjs_cek_rujukan_kartu_rs");akses_depo_obat=rs.getBoolean("akses_depo_obat");bpjs_rujukan_keluar=rs.getBoolean("bpjs_rujukan_keluar");grafik_lab_ralanbulan=rs.getBoolean("grafik_lab_ralanbulan");pengeluaran_stok_apotek=rs.getBoolean("pengeluaran_stok_apotek");
                    grafik_rad_ralanbulan=rs.getBoolean("grafik_rad_ralanbulan");detailjmdokter2=rs.getBoolean("detailjmdokter2");pengaduan_pasien=rs.getBoolean("pengaduan_pasien");grafik_lab_ralanhari=rs.getBoolean("grafik_lab_ralanhari");grafik_rad_ralanhari=rs.getBoolean("grafik_rad_ralanhari");sensus_harian_ralan=rs.getBoolean("sensus_harian_ralan");metode_racik=rs.getBoolean("metode_racik");pembayaran_akun_bayar=rs.getBoolean("pembayaran_akun_bayar");pengguna_obat_resep=rs.getBoolean("pengguna_obat_resep");rekap_pemesanan=rs.getBoolean("rekap_pemesanan");
                    master_berkas_pegawai=rs.getBoolean("master_berkas_pegawai");berkas_kepegawaian=rs.getBoolean("berkas_kepegawaian");riwayat_jabatan=rs.getBoolean("riwayat_jabatan");riwayat_pendidikan=rs.getBoolean("riwayat_pendidikan");riwayat_naik_gaji=rs.getBoolean("riwayat_naik_gaji");kegiatan_ilmiah=rs.getBoolean("kegiatan_ilmiah");riwayat_penghargaan=rs.getBoolean("riwayat_penghargaan");riwayat_penelitian=rs.getBoolean("riwayat_penelitian");penerimaan_non_medis=rs.getBoolean("penerimaan_non_medis");bayar_pesan_non_medis=rs.getBoolean("bayar_pesan_non_medis");
                    hutang_barang_non_medis=rs.getBoolean("hutang_barang_non_medis");rekap_pemesanan_non_medis=rs.getBoolean("rekap_pemesanan_non_medis");insiden_keselamatan=rs.getBoolean("insiden_keselamatan");insiden_keselamatan_pasien=rs.getBoolean("insiden_keselamatan_pasien");grafik_ikp_pertahun=rs.getBoolean("grafik_ikp_pertahun");grafik_ikp_perbulan=rs.getBoolean("grafik_ikp_perbulan");grafik_ikp_pertanggal=rs.getBoolean("grafik_ikp_pertanggal");riwayat_data_batch=rs.getBoolean("riwayat_data_batch");grafik_ikp_jenis=rs.getBoolean("grafik_ikp_jenis");
                    grafik_ikp_dampak=rs.getBoolean("grafik_ikp_dampak");piutang_akun_piutang=rs.getBoolean("piutang_akun_piutang");grafik_kunjungan_per_agama=rs.getBoolean("grafik_kunjungan_per_agama");grafik_kunjungan_per_umur=rs.getBoolean("grafik_kunjungan_per_umur");suku_bangsa=rs.getBoolean("suku_bangsa");bahasa_pasien=rs.getBoolean("bahasa_pasien");golongan_tni=rs.getBoolean("golongan_tni");satuan_tni=rs.getBoolean("satuan_tni");jabatan_tni=rs.getBoolean("jabatan_tni");pangkat_tni=rs.getBoolean("pangkat_tni");golongan_polri=rs.getBoolean("golongan_polri");
                    satuan_polri=rs.getBoolean("satuan_polri");jabatan_polri=rs.getBoolean("jabatan_polri");pangkat_polri=rs.getBoolean("pangkat_polri");cacat_fisik=rs.getBoolean("cacat_fisik");grafik_kunjungan_suku=rs.getBoolean("grafik_kunjungan_suku");grafik_kunjungan_bahasa=rs.getBoolean("grafik_kunjungan_bahasa");booking_operasi=rs.getBoolean("booking_operasi");mapping_poli_bpjs=rs.getBoolean("mapping_poli_bpjs");grafik_kunjungan_per_cacat=rs.getBoolean("grafik_kunjungan_per_cacat");barang_cssd=rs.getBoolean("barang_cssd");skdp_bpjs=rs.getBoolean("skdp_bpjs");
                    booking_registrasi=rs.getBoolean("booking_registrasi");bpjs_cek_propinsi=rs.getBoolean("bpjs_cek_propinsi");bpjs_cek_kabupaten=rs.getBoolean("bpjs_cek_kabupaten");bpjs_cek_kecamatan=rs.getBoolean("bpjs_cek_kecamatan");bpjs_cek_dokterdpjp=rs.getBoolean("bpjs_cek_dokterdpjp");bpjs_cek_riwayat_rujukanrs=rs.getBoolean("bpjs_cek_riwayat_rujukanrs");bpjs_cek_tanggal_rujukan=rs.getBoolean("bpjs_cek_tanggal_rujukan");permintaan_lab=rs.getBoolean("permintaan_lab");permintaan_radiologi=rs.getBoolean("permintaan_radiologi");
                    surat_indeks=rs.getBoolean("surat_indeks");surat_map=rs.getBoolean("surat_map");surat_almari=rs.getBoolean("surat_almari");surat_rak=rs.getBoolean("surat_rak");surat_ruang=rs.getBoolean("surat_ruang");surat_klasifikasi=rs.getBoolean("surat_klasifikasi");surat_status=rs.getBoolean("surat_status");surat_sifat=rs.getBoolean("surat_sifat");surat_balas=rs.getBoolean("surat_balas");surat_masuk=rs.getBoolean("surat_masuk");pcare_cek_dokter=rs.getBoolean("pcare_cek_dokter");pcare_cek_poli=rs.getBoolean("pcare_cek_poli");
                    pcare_cek_provider=rs.getBoolean("pcare_cek_provider");pcare_cek_statuspulang=rs.getBoolean("pcare_cek_statuspulang");pcare_cek_spesialis=rs.getBoolean("pcare_cek_spesialis");pcare_cek_subspesialis=rs.getBoolean("pcare_cek_subspesialis");pcare_cek_sarana=rs.getBoolean("pcare_cek_sarana");pcare_cek_khusus=rs.getBoolean("pcare_cek_khusus");pcare_cek_obat=rs.getBoolean("pcare_cek_obat");pcare_cek_tindakan=rs.getBoolean("pcare_cek_tindakan");pcare_cek_faskessubspesialis=rs.getBoolean("pcare_cek_faskessubspesialis");
                    pcare_cek_faskesalihrawat=rs.getBoolean("pcare_cek_faskesalihrawat");pcare_cek_faskesthalasemia=rs.getBoolean("pcare_cek_faskesthalasemia");pcare_mapping_obat=rs.getBoolean("pcare_mapping_obat");pcare_mapping_tindakan=rs.getBoolean("pcare_mapping_tindakan");pcare_club_prolanis=rs.getBoolean("pcare_club_prolanis");pcare_mapping_poli=rs.getBoolean("pcare_mapping_poli");pcare_kegiatan_kelompok=rs.getBoolean("pcare_kegiatan_kelompok");pcare_mapping_tindakan_ranap=rs.getBoolean("pcare_mapping_tindakan_ranap");
                    pcare_peserta_kegiatan_kelompok=rs.getBoolean("pcare_peserta_kegiatan_kelompok");sirkulasi_obat3=rs.getBoolean("sirkulasi_obat3");bridging_pcare_daftar=rs.getBoolean("bridging_pcare_daftar");pcare_mapping_dokter=rs.getBoolean("pcare_mapping_dokter");ranap_per_ruang=rs.getBoolean("ranap_per_ruang");penyakit_ranap_cara_bayar=rs.getBoolean("penyakit_ranap_cara_bayar");anggota_militer_dirawat=rs.getBoolean("anggota_militer_dirawat");set_input_parsial=rs.getBoolean("set_input_parsial");lama_pelayanan_radiologi=rs.getBoolean("lama_pelayanan_radiologi");
                    lama_pelayanan_lab=rs.getBoolean("lama_pelayanan_lab");bpjs_cek_sep=rs.getBoolean("bpjs_cek_sep");catatan_perawatan=rs.getBoolean("catatan_perawatan");surat_keluar=rs.getBoolean("surat_keluar");kegiatan_farmasi=rs.getBoolean("kegiatan_farmasi");stok_opname_logistik=rs.getBoolean("stok_opname_logistik");sirkulasi_non_medis=rs.getBoolean("sirkulasi_non_medis");rekap_lab_pertahun=rs.getBoolean("rekap_lab_pertahun");perujuk_lab_pertahun=rs.getBoolean("perujuk_lab_pertahun");rekap_radiologi_pertahun=rs.getBoolean("rekap_radiologi_pertahun");
                    perujuk_radiologi_pertahun=rs.getBoolean("perujuk_radiologi_pertahun");jumlah_porsi_diet=rs.getBoolean("jumlah_porsi_diet");jumlah_macam_diet=rs.getBoolean("jumlah_macam_diet");payment_point2=rs.getBoolean("payment_point2");pembayaran_akun_bayar2=rs.getBoolean("pembayaran_akun_bayar2");hapus_nota_salah=rs.getBoolean("hapus_nota_salah");pengkajian_askep=rs.getBoolean("pengkajian_askep");hais_perbangsal=rs.getBoolean("hais_perbangsal");ppn_obat=rs.getBoolean("ppn_obat");saldo_akun_perbulan=rs.getBoolean("saldo_akun_perbulan");
                    display_apotek=rs.getBoolean("display_apotek");sisrute_referensi_faskes=rs.getBoolean("sisrute_referensi_faskes");sisrute_referensi_alasanrujuk=rs.getBoolean("sisrute_referensi_alasanrujuk");sisrute_referensi_diagnosa=rs.getBoolean("sisrute_referensi_diagnosa");sisrute_rujukan_masuk=rs.getBoolean("sisrute_rujukan_masuk");sisrute_rujukan_keluar=rs.getBoolean("sisrute_rujukan_keluar");bpjs_cek_skdp=rs.getBoolean("bpjs_cek_skdp");data_batch=rs.getBoolean("data_batch");kunjungan_permintaan_lab=rs.getBoolean("kunjungan_permintaan_lab");
                    kunjungan_permintaan_lab2=rs.getBoolean("kunjungan_permintaan_lab2");kunjungan_permintaan_radiologi=rs.getBoolean("kunjungan_permintaan_radiologi");kunjungan_permintaan_radiologi2=rs.getBoolean("kunjungan_permintaan_radiologi2");pcare_pemberian_obat=rs.getBoolean("pcare_pemberian_obat");pcare_pemberian_tindakan=rs.getBoolean("pcare_pemberian_tindakan");pembayaran_akun_bayar3=rs.getBoolean("pembayaran_akun_bayar3");password_asuransi=rs.getBoolean("password_asuransi");kemenkes_sitt=rs.getBoolean("kemenkes_sitt");
                    siranap_ketersediaan_kamar=rs.getBoolean("siranap_ketersediaan_kamar");grafik_tb_periodelaporan=rs.getBoolean("grafik_tb_periodelaporan");grafik_tb_rujukan=rs.getBoolean("grafik_tb_rujukan");grafik_tb_riwayat=rs.getBoolean("grafik_tb_riwayat");grafik_tb_tipediagnosis=rs.getBoolean("grafik_tb_tipediagnosis");grafik_tb_statushiv=rs.getBoolean("grafik_tb_statushiv");grafik_tb_skoringanak=rs.getBoolean("grafik_tb_skoringanak");grafik_tb_konfirmasiskoring5=rs.getBoolean("grafik_tb_konfirmasiskoring5");
                    grafik_tb_konfirmasiskoring6=rs.getBoolean("grafik_tb_konfirmasiskoring6");grafik_tb_sumberobat=rs.getBoolean("grafik_tb_sumberobat");grafik_tb_hasilakhirpengobatan=rs.getBoolean("grafik_tb_hasilakhirpengobatan");grafik_tb_hasilteshiv=rs.getBoolean("grafik_tb_hasilteshiv");kadaluarsa_batch=rs.getBoolean("kadaluarsa_batch");sisa_stok=rs.getBoolean("sisa_stok");obat_per_resep=rs.getBoolean("obat_per_resep");pemakaian_air_pdam=rs.getBoolean("pemakaian_air_pdam");limbah_b3_medis=rs.getBoolean("limbah_b3_medis");
                    grafik_air_pdam_pertanggal=rs.getBoolean("grafik_air_pdam_pertanggal");grafik_air_pdam_perbulan=rs.getBoolean("grafik_air_pdam_perbulan");grafik_limbahb3_pertanggal=rs.getBoolean("grafik_limbahb3_pertanggal");grafik_limbahb3_perbulan=rs.getBoolean("grafik_limbahb3_perbulan");limbah_domestik=rs.getBoolean("limbah_domestik");grafik_limbahdomestik_pertanggal=rs.getBoolean("grafik_limbahdomestik_pertanggal");grafik_limbahdomestik_perbulan=rs.getBoolean("grafik_limbahdomestik_perbulan");mutu_air_limbah=rs.getBoolean("mutu_air_limbah");
                    pest_control=rs.getBoolean("pest_control");ruang_perpustakaan=rs.getBoolean("ruang_perpustakaan");kategori_perpustakaan=rs.getBoolean("kategori_perpustakaan");jenis_perpustakaan=rs.getBoolean("jenis_perpustakaan");pengarang_perpustakaan=rs.getBoolean("pengarang_perpustakaan");penerbit_perpustakaan=rs.getBoolean("penerbit_perpustakaan");koleksi_perpustakaan=rs.getBoolean("koleksi_perpustakaan");inventaris_perpustakaan=rs.getBoolean("inventaris_perpustakaan");set_peminjaman_perpustakaan=rs.getBoolean("set_peminjaman_perpustakaan");
                    denda_perpustakaan=rs.getBoolean("denda_perpustakaan");anggota_perpustakaan=rs.getBoolean("anggota_perpustakaan");peminjaman_perpustakaan=rs.getBoolean("peminjaman_perpustakaan");bayar_denda_perpustakaan=rs.getBoolean("bayar_denda_perpustakaan");ebook_perpustakaan=rs.getBoolean("ebook_perpustakaan");jenis_cidera_k3rs=rs.getBoolean("jenis_cidera_k3rs");penyebab_k3rs=rs.getBoolean("penyebab_k3rs");jenis_luka_k3rs=rs.getBoolean("jenis_luka_k3rs");lokasi_kejadian_k3rs=rs.getBoolean("lokasi_kejadian_k3rs");
                    dampak_cidera_k3rs=rs.getBoolean("dampak_cidera_k3rs");jenis_pekerjaan_k3rs=rs.getBoolean("jenis_pekerjaan_k3rs");bagian_tubuh_k3rs=rs.getBoolean("bagian_tubuh_k3rs");peristiwa_k3rs=rs.getBoolean("peristiwa_k3rs");grafik_k3_pertahun=rs.getBoolean("grafik_k3_pertahun");grafik_k3_perbulan=rs.getBoolean("grafik_k3_perbulan");grafik_k3_pertanggal=rs.getBoolean("grafik_k3_pertanggal");grafik_k3_perjeniscidera=rs.getBoolean("grafik_k3_perjeniscidera");grafik_k3_perpenyebab=rs.getBoolean("grafik_k3_perpenyebab");
                    grafik_k3_perjenisluka=rs.getBoolean("grafik_k3_perjenisluka");grafik_k3_lokasikejadian=rs.getBoolean("grafik_k3_lokasikejadian");grafik_k3_dampakcidera=rs.getBoolean("grafik_k3_dampakcidera");grafik_k3_perjenispekerjaan=rs.getBoolean("grafik_k3_perjenispekerjaan");grafik_k3_perbagiantubuh=rs.getBoolean("grafik_k3_perbagiantubuh");jenis_cidera_k3rstahun=rs.getBoolean("jenis_cidera_k3rstahun");penyebab_k3rstahun=rs.getBoolean("penyebab_k3rstahun");jenis_luka_k3rstahun=rs.getBoolean("jenis_luka_k3rstahun");
                    lokasi_kejadian_k3rstahun=rs.getBoolean("lokasi_kejadian_k3rstahun");dampak_cidera_k3rstahun=rs.getBoolean("dampak_cidera_k3rstahun");jenis_pekerjaan_k3rstahun=rs.getBoolean("jenis_pekerjaan_k3rstahun");bagian_tubuh_k3rstahun=rs.getBoolean("bagian_tubuh_k3rstahun");sekrining_rawat_jalan=rs.getBoolean("sekrining_rawat_jalan");bpjs_histori_pelayanan=rs.getBoolean("bpjs_histori_pelayanan");rekap_mutasi_berkas=rs.getBoolean("rekap_mutasi_berkas");skrining_ralan_pernapasan_pertahun=rs.getBoolean("skrining_ralan_pernapasan_pertahun");
                    pengajuan_barang_medis=rs.getBoolean("pengajuan_barang_medis");pengajuan_barang_nonmedis=rs.getBoolean("pengajuan_barang_nonmedis");grafik_kunjungan_ranapbulan=rs.getBoolean("grafik_kunjungan_ranapbulan");grafik_kunjungan_ranaptanggal=rs.getBoolean("grafik_kunjungan_ranaptanggal");grafik_kunjungan_ranap_peruang=rs.getBoolean("grafik_kunjungan_ranap_peruang");kunjungan_bangsal_pertahun=rs.getBoolean("kunjungan_bangsal_pertahun");grafik_jenjang_jabatanpegawai=rs.getBoolean("grafik_jenjang_jabatanpegawai");
                    grafik_bidangpegawai=rs.getBoolean("grafik_bidangpegawai");grafik_departemenpegawai=rs.getBoolean("grafik_departemenpegawai");grafik_pendidikanpegawai=rs.getBoolean("grafik_pendidikanpegawai");grafik_sttswppegawai=rs.getBoolean("grafik_sttswppegawai");grafik_sttskerjapegawai=rs.getBoolean("grafik_sttskerjapegawai");grafik_sttspulangranap=rs.getBoolean("grafik_sttspulangranap");kip_pasien_ranap=rs.getBoolean("kip_pasien_ranap");kip_pasien_ralan=rs.getBoolean("kip_pasien_ralan");bpjs_mapping_dokterdpjp=rs.getBoolean("bpjs_mapping_dokterdpjp");
                    data_triase_igd=rs.getBoolean("data_triase_igd");master_triase_skala1=rs.getBoolean("master_triase_skala1");master_triase_skala2=rs.getBoolean("master_triase_skala2");master_triase_skala3=rs.getBoolean("master_triase_skala3");master_triase_skala4=rs.getBoolean("master_triase_skala4");master_triase_skala5=rs.getBoolean("master_triase_skala5");master_triase_pemeriksaan=rs.getBoolean("master_triase_pemeriksaan");master_triase_macamkasus=rs.getBoolean("master_triase_macamkasus");rekap_permintaan_diet=rs.getBoolean("rekap_permintaan_diet");
                    daftar_pasien_ranap=rs.getBoolean("daftar_pasien_ranap");daftar_pasien_ranaptni=rs.getBoolean("daftar_pasien_ranaptni");pengajuan_asetinventaris=rs.getBoolean("pengajuan_asetinventaris");item_apotek_jenis=rs.getBoolean("item_apotek_jenis");item_apotek_kategori=rs.getBoolean("item_apotek_kategori");item_apotek_golongan=rs.getBoolean("item_apotek_golongan");item_apotek_industrifarmasi=rs.getBoolean("item_apotek_industrifarmasi");sepuluh_obat_terbanyak_poli=rs.getBoolean("10_obat_terbanyak_poli");grafik_pengajuan_aset_urgensi=rs.getBoolean("grafik_pengajuan_aset_urgensi");
                    grafik_pengajuan_aset_status=rs.getBoolean("grafik_pengajuan_aset_status");grafik_pengajuan_aset_departemen=rs.getBoolean("grafik_pengajuan_aset_departemen");rekap_pengajuan_aset_departemen=rs.getBoolean("rekap_pengajuan_aset_departemen");grafik_kelompok_jabatanpegawai=rs.getBoolean("grafik_kelompok_jabatanpegawai");grafik_resiko_kerjapegawai=rs.getBoolean("grafik_resiko_kerjapegawai");grafik_emergency_indexpegawai=rs.getBoolean("grafik_emergency_indexpegawai");grafik_inventaris_ruang=rs.getBoolean("grafik_inventaris_ruang");harian_HAIs2=rs.getBoolean("harian_HAIs2");
                    grafik_inventaris_jenis=rs.getBoolean("grafik_inventaris_jenis");data_resume_pasien=rs.getBoolean("data_resume_pasien");perkiraan_biaya_ranap=rs.getBoolean("perkiraan_biaya_ranap");rekap_obat_poli=rs.getBoolean("rekap_obat_poli");rekap_obat_pasien=rs.getBoolean("rekap_obat_pasien");permintaan_perbaikan_inventaris=rs.getBoolean("permintaan_perbaikan_inventaris");grafik_HAIs_pasienbangsal=rs.getBoolean("grafik_HAIs_pasienbangsal");grafik_HAIs_pasienbulan=rs.getBoolean("grafik_HAIs_pasienbulan");grafik_HAIs_laju_vap=rs.getBoolean("grafik_HAIs_laju_vap");
                    grafik_HAIs_laju_iad=rs.getBoolean("grafik_HAIs_laju_iad");grafik_HAIs_laju_pleb=rs.getBoolean("grafik_HAIs_laju_pleb");grafik_HAIs_laju_isk=rs.getBoolean("grafik_HAIs_laju_isk");grafik_HAIs_laju_ilo=rs.getBoolean("grafik_HAIs_laju_ilo");grafik_HAIs_laju_hap=rs.getBoolean("grafik_HAIs_laju_hap");inhealth_mapping_poli=rs.getBoolean("inhealth_mapping_poli");inhealth_mapping_dokter=rs.getBoolean("inhealth_mapping_dokter");inhealth_mapping_tindakan_ralan=rs.getBoolean("inhealth_mapping_tindakan_ralan");inhealth_mapping_tindakan_ranap=rs.getBoolean("inhealth_mapping_tindakan_ranap");
                    inhealth_mapping_tindakan_radiologi=rs.getBoolean("inhealth_mapping_tindakan_radiologi");inhealth_mapping_tindakan_laborat=rs.getBoolean("inhealth_mapping_tindakan_laborat");inhealth_mapping_tindakan_operasi=rs.getBoolean("inhealth_mapping_tindakan_operasi");hibah_obat_bhp=rs.getBoolean("hibah_obat_bhp");asal_hibah=rs.getBoolean("asal_hibah");asuhan_gizi=rs.getBoolean("asuhan_gizi");inhealth_kirim_tagihan=rs.getBoolean("inhealth_kirim_tagihan");sirkulasi_obat4=rs.getBoolean("sirkulasi_obat4");sirkulasi_obat5=rs.getBoolean("sirkulasi_obat5");
                    sirkulasi_non_medis2=rs.getBoolean("sirkulasi_non_medis2");monitoring_asuhan_gizi=rs.getBoolean("monitoring_asuhan_gizi");penerimaan_obat_perbulan=rs.getBoolean("penerimaan_obat_perbulan");rekap_kunjungan=rs.getBoolean("rekap_kunjungan");surat_sakit=rs.getBoolean("surat_sakit");penilaian_awal_keperawatan_ralan=rs.getBoolean("penilaian_awal_keperawatan_ralan");permintaan_diet=rs.getBoolean("permintaan_diet");master_masalah_keperawatan=rs.getBoolean("master_masalah_keperawatan");pengajuan_cuti=rs.getBoolean("pengajuan_cuti");kedatangan_pasien=rs.getBoolean("kedatangan_pasien");
                    utd_pendonor=rs.getBoolean("utd_pendonor");toko_suplier=rs.getBoolean("toko_suplier");toko_jenis=rs.getBoolean("toko_jenis");toko_set_harga=rs.getBoolean("toko_set_harga");toko_barang=rs.getBoolean("toko_barang");penagihan_piutang_pasien=rs.getBoolean("penagihan_piutang_pasien");akun_penagihan_piutang=rs.getBoolean("akun_penagihan_piutang");stok_opname_toko=rs.getBoolean("stok_opname_toko");toko_riwayat_barang=rs.getBoolean("toko_riwayat_barang");toko_surat_pemesanan=rs.getBoolean("toko_surat_pemesanan");toko_pengajuan_barang=rs.getBoolean("toko_pengajuan_barang");
                    toko_penerimaan_barang=rs.getBoolean("toko_penerimaan_barang");toko_pengadaan_barang=rs.getBoolean("toko_pengadaan_barang");toko_hutang=rs.getBoolean("toko_hutang");toko_bayar_pemesanan=rs.getBoolean("toko_bayar_pemesanan");toko_member=rs.getBoolean("toko_member");toko_penjualan=rs.getBoolean("toko_penjualan");registrasi_poli_per_tanggal=rs.getBoolean("registrasi_poli_per_tanggal");toko_piutang=rs.getBoolean("toko_piutang");toko_retur_beli=rs.getBoolean("toko_retur_beli");ipsrs_returbeli=rs.getBoolean("ipsrs_returbeli");ipsrs_riwayat_barang=rs.getBoolean("ipsrs_riwayat_barang");
                    pasien_corona=rs.getBoolean("pasien_corona");toko_pendapatan_harian=rs.getBoolean("toko_pendapatan_harian");diagnosa_pasien_corona=rs.getBoolean("diagnosa_pasien_corona");perawatan_pasien_corona=rs.getBoolean("perawatan_pasien_corona");penilaian_awal_keperawatan_gigi=rs.getBoolean("penilaian_awal_keperawatan_gigi");master_masalah_keperawatan_gigi=rs.getBoolean("master_masalah_keperawatan_gigi");toko_bayar_piutang=rs.getBoolean("toko_bayar_piutang");toko_piutang_harian=rs.getBoolean("toko_piutang_harian");toko_penjualan_harian=rs.getBoolean("toko_penjualan_harian");
                    deteksi_corona=rs.getBoolean("deteksi_corona");penilaian_awal_keperawatan_kebidanan=rs.getBoolean("penilaian_awal_keperawatan_kebidanan");pengumuman_epasien=rs.getBoolean("pengumuman_epasien");surat_hamil=rs.getBoolean("surat_hamil");set_tarif_online=rs.getBoolean("set_tarif_online");booking_periksa=rs.getBoolean("booking_periksa");toko_sirkulasi=rs.getBoolean("toko_sirkulasi");toko_retur_jual=rs.getBoolean("toko_retur_jual");toko_retur_piutang=rs.getBoolean("toko_retur_piutang");toko_sirkulasi2=rs.getBoolean("toko_sirkulasi2");
                    toko_keuntungan_barang=rs.getBoolean("toko_keuntungan_barang");zis_pengeluaran_penerima_dankes=rs.getBoolean("zis_pengeluaran_penerima_dankes");zis_penghasilan_penerima_dankes=rs.getBoolean("zis_penghasilan_penerima_dankes");zis_ukuran_rumah_penerima_dankes=rs.getBoolean("zis_ukuran_rumah_penerima_dankes");zis_dinding_rumah_penerima_dankes=rs.getBoolean("zis_dinding_rumah_penerima_dankes");zis_lantai_rumah_penerima_dankes=rs.getBoolean("zis_lantai_rumah_penerima_dankes");zis_atap_rumah_penerima_dankes=rs.getBoolean("zis_atap_rumah_penerima_dankes");
                    zis_kepemilikan_rumah_penerima_dankes=rs.getBoolean("zis_kepemilikan_rumah_penerima_dankes");zis_kamar_mandi_penerima_dankes=rs.getBoolean("zis_kamar_mandi_penerima_dankes");zis_dapur_rumah_penerima_dankes=rs.getBoolean("zis_dapur_rumah_penerima_dankes");zis_kursi_rumah_penerima_dankes=rs.getBoolean("zis_kursi_rumah_penerima_dankes");zis_kategori_phbs_penerima_dankes=rs.getBoolean("zis_kategori_phbs_penerima_dankes");zis_elektronik_penerima_dankes=rs.getBoolean("zis_elektronik_penerima_dankes");zis_ternak_penerima_dankes=rs.getBoolean("zis_ternak_penerima_dankes");
                    zis_jenis_simpanan_penerima_dankes=rs.getBoolean("zis_jenis_simpanan_penerima_dankes");penilaian_awal_keperawatan_anak=rs.getBoolean("penilaian_awal_keperawatan_anak");zis_kategori_asnaf_penerima_dankes=rs.getBoolean("zis_kategori_asnaf_penerima_dankes");master_masalah_keperawatan_anak=rs.getBoolean("master_masalah_keperawatan_anak");master_imunisasi=rs.getBoolean("master_imunisasi");zis_patologis_penerima_dankes=rs.getBoolean("zis_patologis_penerima_dankes");pcare_cek_kartu=rs.getBoolean("pcare_cek_kartu");surat_bebas_narkoba=rs.getBoolean("surat_bebas_narkoba");
                    surat_keterangan_covid=rs.getBoolean("surat_keterangan_covid");pemakaian_air_tanah=rs.getBoolean("pemakaian_air_tanah");grafik_air_tanah_pertanggal=rs.getBoolean("grafik_air_tanah_pertanggal");grafik_air_tanah_perbulan=rs.getBoolean("grafik_air_tanah_perbulan");lama_pelayanan_poli=rs.getBoolean("lama_pelayanan_poli");hemodialisa=rs.getBoolean("hemodialisa");laporan_tahunan_irj=rs.getBoolean("laporan_tahunan_irj");grafik_harian_hemodialisa=rs.getBoolean("grafik_harian_hemodialisa");grafik_bulanan_hemodialisa=rs.getBoolean("grafik_bulanan_hemodialisa");
                    grafik_tahunan_hemodialisa=rs.getBoolean("grafik_tahunan_hemodialisa");grafik_bulanan_meninggal=rs.getBoolean("grafik_bulanan_meninggal");perbaikan_inventaris=rs.getBoolean("perbaikan_inventaris");surat_cuti_hamil=rs.getBoolean("surat_cuti_hamil");permintaan_stok_obat_pasien=rs.getBoolean("permintaan_stok_obat_pasien");pemeliharaan_inventaris=rs.getBoolean("pemeliharaan_inventaris");klasifikasi_pasien_ranap=rs.getBoolean("klasifikasi_pasien_ranap");bulanan_klasifikasi_pasien_ranap=rs.getBoolean("bulanan_klasifikasi_pasien_ranap");
                    harian_klasifikasi_pasien_ranap=rs.getBoolean("harian_klasifikasi_pasien_ranap");klasifikasi_pasien_perbangsal=rs.getBoolean("klasifikasi_pasien_perbangsal");soap_perawatan=rs.getBoolean("soap_perawatan");klaim_rawat_jalan=rs.getBoolean("klaim_rawat_jalan");skrining_gizi=rs.getBoolean("skrining_gizi");lama_penyiapan_rm=rs.getBoolean("lama_penyiapan_rm");dosis_radiologi=rs.getBoolean("dosis_radiologi");demografi_umur_kunjungan=rs.getBoolean("demografi_umur_kunjungan");jam_diet_pasien=rs.getBoolean("jam_diet_pasien");rvu_bpjs=rs.getBoolean("rvu_bpjs");
                    verifikasi_penerimaan_farmasi=rs.getBoolean("verifikasi_penerimaan_farmasi");verifikasi_penerimaan_logistik=rs.getBoolean("verifikasi_penerimaan_logistik");pemeriksaan_lab_pa=rs.getBoolean("pemeriksaan_lab_pa");ringkasan_pengajuan_obat=rs.getBoolean("ringkasan_pengajuan_obat");ringkasan_pemesanan_obat=rs.getBoolean("ringkasan_pemesanan_obat");ringkasan_pengadaan_obat=rs.getBoolean("ringkasan_pengadaan_obat");ringkasan_penerimaan_obat=rs.getBoolean("ringkasan_penerimaan_obat");ringkasan_hibah_obat=rs.getBoolean("ringkasan_hibah_obat");
                    ringkasan_penjualan_obat=rs.getBoolean("ringkasan_penjualan_obat");ringkasan_beri_obat=rs.getBoolean("ringkasan_beri_obat");ringkasan_piutang_obat=rs.getBoolean("ringkasan_piutang_obat");ringkasan_stok_keluar_obat=rs.getBoolean("ringkasan_stok_keluar_obat");ringkasan_retur_suplier_obat=rs.getBoolean("ringkasan_retur_suplier_obat");ringkasan_retur_pembeli_obat=rs.getBoolean("ringkasan_retur_pembeli_obat");penilaian_awal_keperawatan_ranapkebidanan=rs.getBoolean("penilaian_awal_keperawatan_ranapkebidanan");ringkasan_pengajuan_nonmedis=rs.getBoolean("ringkasan_pengajuan_nonmedis");
                    ringkasan_pemesanan_nonmedis=rs.getBoolean("ringkasan_pemesanan_nonmedis");ringkasan_pengadaan_nonmedis=rs.getBoolean("ringkasan_pengadaan_nonmedis");ringkasan_penerimaan_nonmedis=rs.getBoolean("ringkasan_penerimaan_nonmedis");ringkasan_stokkeluar_nonmedis=rs.getBoolean("ringkasan_stokkeluar_nonmedis");ringkasan_returbeli_nonmedis=rs.getBoolean("ringkasan_returbeli_nonmedis");omset_penerimaan=rs.getBoolean("omset_penerimaan");validasi_penagihan_piutang=rs.getBoolean("validasi_penagihan_piutang");permintaan_ranap=rs.getBoolean("permintaan_ranap");
                    bpjs_diagnosa_prb=rs.getBoolean("bpjs_diagnosa_prb");bpjs_obat_prb=rs.getBoolean("bpjs_obat_prb");bpjs_surat_kontrol=rs.getBoolean("bpjs_surat_kontrol");penggunaan_bhp_ok=rs.getBoolean("penggunaan_bhp_ok");surat_keterangan_rawat_inap=rs.getBoolean("surat_keterangan_rawat_inap");surat_keterangan_sehat=rs.getBoolean("surat_keterangan_sehat");pendapatan_per_carabayar=rs.getBoolean("pendapatan_per_carabayar");akun_host_to_host_bank_jateng=rs.getBoolean("akun_host_to_host_bank_jateng");pembayaran_bank_jateng=rs.getBoolean("pembayaran_bank_jateng");
                    bpjs_surat_pri=rs.getBoolean("bpjs_surat_pri");ringkasan_tindakan=rs.getBoolean("ringkasan_tindakan");lama_pelayanan_pasien=rs.getBoolean("lama_pelayanan_pasien");surat_sakit_pihak_2=rs.getBoolean("surat_sakit_pihak_2");tagihan_hutang_obat=rs.getBoolean("tagihan_hutang_obat");referensi_mobilejkn_bpjs=rs.getBoolean("referensi_mobilejkn_bpjs");batal_pendaftaran_mobilejkn_bpjs=rs.getBoolean("batal_pendaftaran_mobilejkn_bpjs");lama_operasi=rs.getBoolean("lama_operasi");grafik_inventaris_kategori=rs.getBoolean("grafik_inventaris_kategori");
                    grafik_inventaris_merk=rs.getBoolean("grafik_inventaris_merk");grafik_inventaris_produsen=rs.getBoolean("grafik_inventaris_produsen");pengembalian_deposit_pasien=rs.getBoolean("pengembalian_deposit_pasien");validasi_tagihan_hutang_obat=rs.getBoolean("validasi_tagihan_hutang_obat");piutang_obat_belum_lunas=rs.getBoolean("piutang_obat_belum_lunas");integrasi_briapi=rs.getBoolean("integrasi_briapi");pengadaan_aset_inventaris=rs.getBoolean("pengadaan_aset_inventaris");akun_aset_inventaris=rs.getBoolean("akun_aset_inventaris");suplier_inventaris=rs.getBoolean("suplier_inventaris");
                    penerimaan_aset_inventaris=rs.getBoolean("penerimaan_aset_inventaris");bayar_pemesanan_iventaris=rs.getBoolean("bayar_pemesanan_iventaris");hutang_aset_inventaris=rs.getBoolean("hutang_aset_inventaris");hibah_aset_inventaris=rs.getBoolean("hibah_aset_inventaris");titip_faktur_non_medis=rs.getBoolean("titip_faktur_non_medis");validasi_tagihan_non_medis=rs.getBoolean("validasi_tagihan_non_medis");titip_faktur_aset=rs.getBoolean("titip_faktur_aset");validasi_tagihan_aset=rs.getBoolean("validasi_tagihan_aset");hibah_non_medis=rs.getBoolean("hibah_non_medis");
                    pcare_alasan_tacc=rs.getBoolean("pcare_alasan_tacc");resep_luar=rs.getBoolean("resep_luar");surat_bebas_tbc=rs.getBoolean("surat_bebas_tbc");surat_buta_warna=rs.getBoolean("surat_buta_warna");surat_bebas_tato=rs.getBoolean("surat_bebas_tato");surat_kewaspadaan_kesehatan=rs.getBoolean("surat_kewaspadaan_kesehatan");grafik_porsidiet_pertanggal=rs.getBoolean("grafik_porsidiet_pertanggal");grafik_porsidiet_perbulan=rs.getBoolean("grafik_porsidiet_perbulan");grafik_porsidiet_pertahun=rs.getBoolean("grafik_porsidiet_pertahun");grafik_porsidiet_perbangsal=rs.getBoolean("grafik_porsidiet_perbangsal");
                    penilaian_awal_medis_ralan=rs.getBoolean("penilaian_awal_medis_ralan");master_masalah_keperawatan_mata=rs.getBoolean("master_masalah_keperawatan_mata");penilaian_awal_keperawatan_mata=rs.getBoolean("penilaian_awal_keperawatan_mata");penilaian_awal_medis_ranap=rs.getBoolean("penilaian_awal_medis_ranap");penilaian_awal_medis_ranap_kebidanan=rs.getBoolean("penilaian_awal_medis_ranap_kebidanan");penilaian_awal_medis_ralan_kebidanan=rs.getBoolean("penilaian_awal_medis_ralan_kebidanan");penilaian_awal_medis_igd=rs.getBoolean("penilaian_awal_medis_igd");
                    penilaian_awal_medis_ralan_anak=rs.getBoolean("penilaian_awal_medis_ralan_anak");bpjs_referensi_poli_hfis=rs.getBoolean("bpjs_referensi_poli_hfis");bpjs_referensi_dokter_hfis=rs.getBoolean("bpjs_referensi_dokter_hfis");bpjs_referensi_jadwal_hfis=rs.getBoolean("bpjs_referensi_jadwal_hfis");penilaian_fisioterapi=rs.getBoolean("penilaian_fisioterapi");bpjs_program_prb=rs.getBoolean("bpjs_program_prb");bpjs_suplesi_jasaraharja=rs.getBoolean("bpjs_suplesi_jasaraharja");bpjs_data_induk_kecelakaan=rs.getBoolean("bpjs_data_induk_kecelakaan");bpjs_sep_internal=rs.getBoolean("bpjs_sep_internal");
                    bpjs_klaim_jasa_raharja=rs.getBoolean("bpjs_klaim_jasa_raharja");bpjs_daftar_finger_print=rs.getBoolean("bpjs_daftar_finger_print");bpjs_rujukan_khusus=rs.getBoolean("bpjs_rujukan_khusus");pemeliharaan_gedung=rs.getBoolean("pemeliharaan_gedung");grafik_perbaikan_inventaris_pertanggal=rs.getBoolean("grafik_perbaikan_inventaris_pertanggal");grafik_perbaikan_inventaris_perbulan=rs.getBoolean("grafik_perbaikan_inventaris_perbulan");grafik_perbaikan_inventaris_pertahun=rs.getBoolean("grafik_perbaikan_inventaris_pertahun");
                    grafik_perbaikan_inventaris_perpelaksana_status=rs.getBoolean("grafik_perbaikan_inventaris_perpelaksana_status");penilaian_mcu=rs.getBoolean("penilaian_mcu");peminjam_piutang=rs.getBoolean("peminjam_piutang");piutang_lainlain=rs.getBoolean("piutang_lainlain");cara_bayar=rs.getBoolean("cara_bayar");audit_kepatuhan_apd=rs.getBoolean("audit_kepatuhan_apd");bpjs_task_id=rs.getBoolean("bpjs_task_id");bayar_piutang_lain=rs.getBoolean("bayar_piutang_lain");pembayaran_akun_bayar4=rs.getBoolean("pembayaran_akun_bayar4");stok_akhir_farmasi_pertanggal=rs.getBoolean("stok_akhir_farmasi_pertanggal");
                    riwayat_kamar_pasien=rs.getBoolean("riwayat_kamar_pasien");uji_fungsi_kfr=rs.getBoolean("uji_fungsi_kfr");hapus_berkas_digital_perawatan=rs.getBoolean("hapus_berkas_digital_perawatan");kategori_pengeluaran_harian=rs.getBoolean("kategori_pengeluaran_harian");kategori_pemasukan_lain=rs.getBoolean("kategori_pemasukan_lain");pembayaran_akun_bayar5=rs.getBoolean("pembayaran_akun_bayar5");ruang_ok=rs.getBoolean("ruang_ok");telaah_resep=rs.getBoolean("telaah_resep");jasa_tindakan_pasien=rs.getBoolean("jasa_tindakan_pasien");permintaan_resep_pulang=rs.getBoolean("permintaan_resep_pulang");
                    rekap_jm_dokter=rs.getBoolean("rekap_jm_dokter");status_data_rm=rs.getBoolean("status_data_rm");ubah_petugas_lab_pk=rs.getBoolean("ubah_petugas_lab_pk");ubah_petugas_lab_pa=rs.getBoolean("ubah_petugas_lab_pa");ubah_petugas_radiologi=rs.getBoolean("ubah_petugas_radiologi");gabung_norawat=rs.getBoolean("gabung_norawat");gabung_rm=rs.getBoolean("gabung_rm");ringkasan_biaya_obat_pasien_pertanggal=rs.getBoolean("ringkasan_biaya_obat_pasien_pertanggal");master_masalah_keperawatan_igd=rs.getBoolean("master_masalah_keperawatan_igd");penilaian_awal_keperawatan_igd=rs.getBoolean("penilaian_awal_keperawatan_igd");
                    bpjs_referensi_dpho_apotek=rs.getBoolean("bpjs_referensi_dpho_apotek");bpjs_referensi_poli_apotek=rs.getBoolean("bpjs_referensi_poli_apotek");bayar_jm_dokter=rs.getBoolean("bayar_jm_dokter");bpjs_referensi_faskes_apotek=rs.getBoolean("bpjs_referensi_faskes_apotek");bpjs_referensi_spesialistik_apotek=rs.getBoolean("bpjs_referensi_spesialistik_apotek");pembayaran_briva=rs.getBoolean("pembayaran_briva");penilaian_awal_keperawatan_ranap=rs.getBoolean("penilaian_awal_keperawatan_ranap");nilai_penerimaan_vendor_farmasi_perbulan=rs.getBoolean("nilai_penerimaan_vendor_farmasi_perbulan");
                    akun_bayar_hutang=rs.getBoolean("akun_bayar_hutang");master_rencana_keperawatan=rs.getBoolean("master_rencana_keperawatan");laporan_tahunan_igd=rs.getBoolean("laporan_tahunan_igd");obat_bhp_tidakbergerak=rs.getBoolean("obat_bhp_tidakbergerak");ringkasan_hutang_vendor_farmasi=rs.getBoolean("ringkasan_hutang_vendor_farmasi");nilai_penerimaan_vendor_nonmedis_perbulan=rs.getBoolean("nilai_penerimaan_vendor_nonmedis_perbulan");ringkasan_hutang_vendor_nonmedis=rs.getBoolean("ringkasan_hutang_vendor_nonmedis");master_rencana_keperawatan_anak=rs.getBoolean("master_rencana_keperawatan_anak");
                    anggota_polri_dirawat=rs.getBoolean("anggota_polri_dirawat");daftar_pasien_ranap_polri=rs.getBoolean("daftar_pasien_ranap_polri");soap_ralan_polri=rs.getBoolean("soap_ralan_polri");soap_ranap_polri=rs.getBoolean("soap_ranap_polri");laporan_penyakit_polri=rs.getBoolean("laporan_penyakit_polri");jumlah_pengunjung_ralan_polri=rs.getBoolean("jumlah_pengunjung_ralan_polri");catatan_observasi_igd=rs.getBoolean("catatan_observasi_igd");catatan_observasi_ranap=rs.getBoolean("catatan_observasi_ranap");catatan_observasi_ranap_kebidanan=rs.getBoolean("catatan_observasi_ranap_kebidanan");
                    catatan_observasi_ranap_postpartum=rs.getBoolean("catatan_observasi_ranap_postpartum");penilaian_awal_medis_ralan_tht=rs.getBoolean("penilaian_awal_medis_ralan_tht");penilaian_psikologi=rs.getBoolean("penilaian_psikologi");audit_cuci_tangan_medis=rs.getBoolean("audit_cuci_tangan_medis");audit_pembuangan_limbah=rs.getBoolean("audit_pembuangan_limbah");ruang_audit_kepatuhan=rs.getBoolean("ruang_audit_kepatuhan");audit_pembuangan_benda_tajam=rs.getBoolean("audit_pembuangan_benda_tajam");audit_penanganan_darah=rs.getBoolean("audit_penanganan_darah");
                    audit_pengelolaan_linen_kotor=rs.getBoolean("audit_pengelolaan_linen_kotor");audit_penempatan_pasien=rs.getBoolean("audit_penempatan_pasien");audit_kamar_jenazah=rs.getBoolean("audit_kamar_jenazah");audit_bundle_iadp=rs.getBoolean("audit_bundle_iadp");audit_bundle_ido=rs.getBoolean("audit_bundle_ido");audit_fasilitas_kebersihan_tangan=rs.getBoolean("audit_fasilitas_kebersihan_tangan");audit_fasilitas_apd=rs.getBoolean("audit_fasilitas_apd");audit_pembuangan_limbah_cair_infeksius=rs.getBoolean("audit_pembuangan_limbah_cair_infeksius");audit_sterilisasi_alat=rs.getBoolean("audit_sterilisasi_alat");
                    penilaian_awal_medis_ralan_psikiatri=rs.getBoolean("penilaian_awal_medis_ralan_psikiatri");persetujuan_penolakan_tindakan=rs.getBoolean("persetujuan_penolakan_tindakan");audit_bundle_isk=rs.getBoolean("audit_bundle_isk");audit_bundle_plabsi=rs.getBoolean("audit_bundle_plabsi");audit_bundle_vap=rs.getBoolean("audit_bundle_vap");akun_host_to_host_bank_papua=rs.getBoolean("akun_host_to_host_bank_papua");pembayaran_bank_papua=rs.getBoolean("pembayaran_bank_papua");penilaian_awal_medis_ralan_penyakit_dalam=rs.getBoolean("penilaian_awal_medis_ralan_penyakit_dalam");
                    penilaian_awal_medis_ralan_mata=rs.getBoolean("penilaian_awal_medis_ralan_mata");penilaian_awal_medis_ralan_neurologi=rs.getBoolean("penilaian_awal_medis_ralan_neurologi");sirkulasi_obat6=rs.getBoolean("sirkulasi_obat6");penilaian_awal_medis_ralan_orthopedi=rs.getBoolean("penilaian_awal_medis_ralan_orthopedi");penilaian_awal_medis_ralan_bedah=rs.getBoolean("penilaian_awal_medis_ralan_bedah");integrasi_khanza_health_services=rs.getBoolean("integrasi_khanza_health_services");soap_ralan_tni=rs.getBoolean("soap_ralan_tni");soap_ranap_tni=rs.getBoolean("soap_ranap_tni");
                    jumlah_pengunjung_ralan_tni=rs.getBoolean("jumlah_pengunjung_ralan_tni");laporan_penyakit_tni=rs.getBoolean("laporan_penyakit_tni");catatan_keperawatan_ranap=rs.getBoolean("catatan_keperawatan_ranap");master_rencana_keperawatan_gigi=rs.getBoolean("master_rencana_keperawatan_gigi");master_rencana_keperawatan_mata=rs.getBoolean("master_rencana_keperawatan_mata");master_rencana_keperawatan_igd=rs.getBoolean("master_rencana_keperawatan_igd");master_masalah_keperawatan_psikiatri=rs.getBoolean("master_masalah_keperawatan_psikiatri");
                    master_rencana_keperawatan_psikiatri=rs.getBoolean("master_rencana_keperawatan_psikiatri");penilaian_awal_keperawatan_psikiatri=rs.getBoolean("penilaian_awal_keperawatan_psikiatri");pemantauan_pews_anak=rs.getBoolean("pemantauan_pews_anak");surat_pulang_atas_permintaan_sendiri=rs.getBoolean("surat_pulang_atas_permintaan_sendiri");template_hasil_radiologi=rs.getBoolean("template_hasil_radiologi");laporan_bulanan_irj=rs.getBoolean("laporan_bulanan_irj");template_pemeriksaan=rs.getBoolean("template_pemeriksaan");pemeriksaan_lab_mb=rs.getBoolean("pemeriksaan_lab_mb");
                    ubah_petugas_lab_mb=rs.getBoolean("ubah_petugas_lab_mb");penilaian_pre_operasi=rs.getBoolean("penilaian_pre_operasi");penilaian_pre_anestesi=rs.getBoolean("penilaian_pre_anestesi");perencanaan_pemulangan=rs.getBoolean("perencanaan_pemulangan");penilaian_lanjutan_resiko_jatuh_dewasa=rs.getBoolean("penilaian_lanjutan_resiko_jatuh_dewasa");penilaian_lanjutan_resiko_jatuh_anak=rs.getBoolean("penilaian_lanjutan_resiko_jatuh_anak");penilaian_awal_medis_ralan_geriatri=rs.getBoolean("penilaian_awal_medis_ralan_geriatri");penilaian_tambahan_pasien_geriatri=rs.getBoolean("penilaian_tambahan_pasien_geriatri");
                    skrining_nutrisi_dewasa=rs.getBoolean("skrining_nutrisi_dewasa");skrining_nutrisi_lansia=rs.getBoolean("skrining_nutrisi_lansia");hasil_pemeriksaan_usg=rs.getBoolean("hasil_pemeriksaan_usg");skrining_nutrisi_anak=rs.getBoolean("skrining_nutrisi_anak");akun_host_to_host_bank_jabar=rs.getBoolean("akun_host_to_host_bank_jabar");pembayaran_bank_jabar=rs.getBoolean("pembayaran_bank_jabar");surat_pernyataan_pasien_umum=rs.getBoolean("surat_pernyataan_pasien_umum");konseling_farmasi=rs.getBoolean("konseling_farmasi");pelayanan_informasi_obat=rs.getBoolean("pelayanan_informasi_obat");
                    jawaban_pio_apoteker=rs.getBoolean("jawaban_pio_apoteker");surat_persetujuan_umum=rs.getBoolean("surat_persetujuan_umum");transfer_pasien_antar_ruang=rs.getBoolean("transfer_pasien_antar_ruang");satu_sehat_referensi_dokter=rs.getBoolean("satu_sehat_referensi_dokter");satu_sehat_referensi_pasien=rs.getBoolean("satu_sehat_referensi_pasien");satu_sehat_mapping_departemen=rs.getBoolean("satu_sehat_mapping_departemen");satu_sehat_mapping_lokasi=rs.getBoolean("satu_sehat_mapping_lokasi");satu_sehat_kirim_encounter=rs.getBoolean("satu_sehat_kirim_encounter");
                    catatan_cek_gds=rs.getBoolean("catatan_cek_gds");satu_sehat_kirim_condition=rs.getBoolean("satu_sehat_kirim_condition");checklist_pre_operasi=rs.getBoolean("checklist_pre_operasi");satu_sehat_kirim_observationttv=rs.getBoolean("satu_sehat_kirim_observationttv");signin_sebelum_anestesi=rs.getBoolean("signin_sebelum_anestesi");satu_sehat_kirim_procedure=rs.getBoolean("satu_sehat_kirim_procedure");operasi_per_bulan=rs.getBoolean("operasi_per_bulan");timeout_sebelum_insisi=rs.getBoolean("timeout_sebelum_insisi");signout_sebelum_menutup_luka=rs.getBoolean("signout_sebelum_menutup_luka");
                    dapur_barang=rs.getBoolean("dapur_barang");dapur_opname=rs.getBoolean("dapur_opname");satu_sehat_mapping_vaksin=rs.getBoolean("satu_sehat_mapping_vaksin");dapur_suplier=rs.getBoolean("dapur_suplier");satu_sehat_kirim_Immunization=rs.getBoolean("satu_sehat_kirim_Immunization");checklist_post_operasi=rs.getBoolean("checklist_post_operasi");dapur_pembelian=rs.getBoolean("dapur_pembelian");dapur_stok_keluar=rs.getBoolean("dapur_stok_keluar");dapur_riwayat_barang=rs.getBoolean("dapur_riwayat_barang");permintaan_dapur=rs.getBoolean("permintaan_dapur");rekonsiliasi_obat=rs.getBoolean("rekonsiliasi_obat");
                    biaya_pengadaan_dapur=rs.getBoolean("biaya_pengadaan_dapur");rekap_pengadaan_dapur=rs.getBoolean("rekap_pengadaan_dapur");kesling_limbah_b3medis_cair=rs.getBoolean("kesling_limbah_b3medis_cair");grafik_limbahb3cair_pertanggal=rs.getBoolean("grafik_limbahb3cair_pertanggal");grafik_limbahb3cair_perbulan=rs.getBoolean("grafik_limbahb3cair_perbulan");rekap_biaya_registrasi=rs.getBoolean("rekap_biaya_registrasi");konfirmasi_rekonsiliasi_obat=rs.getBoolean("konfirmasi_rekonsiliasi_obat");satu_sehat_kirim_clinicalimpression=rs.getBoolean("satu_sehat_kirim_clinicalimpression");
                    penilaian_pasien_terminal=rs.getBoolean("penilaian_pasien_terminal");surat_persetujuan_rawat_inap=rs.getBoolean("surat_persetujuan_rawat_inap");monitoring_reaksi_tranfusi=rs.getBoolean("monitoring_reaksi_tranfusi");penilaian_korban_kekerasan=rs.getBoolean("penilaian_korban_kekerasan");penilaian_lanjutan_resiko_jatuh_lansia=rs.getBoolean("penilaian_lanjutan_resiko_jatuh_lansia");penilaian_pasien_penyakit_menular=rs.getBoolean("penilaian_pasien_penyakit_menular");mpp_skrining=rs.getBoolean("mpp_skrining");edukasi_pasien_keluarga_rj=rs.getBoolean("edukasi_pasien_keluarga_rj");
                    pemantauan_pews_dewasa=rs.getBoolean("pemantauan_pews_dewasa");penilaian_tambahan_bunuh_diri=rs.getBoolean("penilaian_tambahan_bunuh_diri");bpjs_antrean_pertanggal=rs.getBoolean("bpjs_antrean_pertanggal");penilaian_tambahan_perilaku_kekerasan=rs.getBoolean("penilaian_tambahan_perilaku_kekerasan");penilaian_tambahan_beresiko_melarikan_diri=rs.getBoolean("penilaian_tambahan_beresiko_melarikan_diri");persetujuan_penundaan_pelayanan=rs.getBoolean("persetujuan_penundaan_pelayanan");sisa_diet_pasien=rs.getBoolean("sisa_diet_pasien");penilaian_awal_medis_ralan_bedah_mulut=rs.getBoolean("penilaian_awal_medis_ralan_bedah_mulut");
                    penilaian_pasien_keracunan=rs.getBoolean("penilaian_pasien_keracunan");pemantauan_meows_obstetri=rs.getBoolean("pemantauan_meows_obstetri");catatan_adime_gizi=rs.getBoolean("catatan_adime_gizi");pengajuan_biaya=rs.getBoolean("pengajuan_biaya");penilaian_awal_keperawatan_ralan_geriatri=rs.getBoolean("penilaian_awal_keperawatan_ralan_geriatri");master_masalah_keperawatan_geriatri=rs.getBoolean("master_masalah_keperawatan_geriatri");master_rencana_keperawatan_geriatri=rs.getBoolean("master_rencana_keperawatan_geriatri");checklist_kriteria_masuk_hcu=rs.getBoolean("checklist_kriteria_masuk_hcu");
                    checklist_kriteria_keluar_hcu=rs.getBoolean("checklist_kriteria_keluar_hcu");penilaian_risiko_dekubitus=rs.getBoolean("penilaian_risiko_dekubitus");master_menolak_anjuran_medis=rs.getBoolean("master_menolak_anjuran_medis");penolakan_anjuran_medis=rs.getBoolean("penolakan_anjuran_medis");laporan_tahunan_penolakan_anjuran_medis=rs.getBoolean("laporan_tahunan_penolakan_anjuran_medis");template_laporan_operasi=rs.getBoolean("template_laporan_operasi");hasil_tindakan_eswl=rs.getBoolean("hasil_tindakan_eswl");checklist_kriteria_masuk_icu=rs.getBoolean("checklist_kriteria_masuk_icu");
                    checklist_kriteria_keluar_icu=rs.getBoolean("checklist_kriteria_keluar_icu");akses_dokter_lain_rawat_jalan=rs.getBoolean("akses_dokter_lain_rawat_jalan");follow_up_dbd=rs.getBoolean("follow_up_dbd");penilaian_risiko_jatuh_neonatus=rs.getBoolean("penilaian_risiko_jatuh_neonatus");persetujuan_pengajuan_biaya=rs.getBoolean("persetujuan_pengajuan_biaya");pemeriksaan_fisik_ralan_per_penyakit=rs.getBoolean("pemeriksaan_fisik_ralan_per_penyakit");penilaian_lanjutan_resiko_jatuh_geriatri=rs.getBoolean("penilaian_lanjutan_resiko_jatuh_geriatri");pemantauan_ews_neonatus=rs.getBoolean("pemantauan_ews_neonatus");
                    validasi_persetujuan_pengajuan_biaya=rs.getBoolean("validasi_persetujuan_pengajuan_biaya");riwayat_perawatan_icare_bpjs=rs.getBoolean("riwayat_perawatan_icare_bpjs");rekap_pengajuan_biaya=rs.getBoolean("rekap_pengajuan_biaya");penilaian_awal_medis_ralan_kulit_kelamin=rs.getBoolean("penilaian_awal_medis_ralan_kulit_kelamin");akun_host_to_host_bank_mandiri=rs.getBoolean("akun_host_to_host_bank_mandiri");penilaian_medis_hemodialisa=rs.getBoolean("penilaian_medis_hemodialisa");penilaian_level_kecemasan_ranap_anak=rs.getBoolean("penilaian_level_kecemasan_ranap_anak");
                    penilaian_lanjutan_resiko_jatuh_psikiatri=rs.getBoolean("penilaian_lanjutan_resiko_jatuh_psikiatri");penilaian_lanjutan_skrining_fungsional=rs.getBoolean("penilaian_lanjutan_skrining_fungsional");penilaian_medis_ralan_rehab_medik=rs.getBoolean("penilaian_medis_ralan_rehab_medik");laporan_anestesi=rs.getBoolean("laporan_anestesi");template_persetujuan_penolakan_tindakan=rs.getBoolean("template_persetujuan_penolakan_tindakan");penilaian_medis_ralan_gawat_darurat_psikiatri=rs.getBoolean("penilaian_medis_ralan_gawat_darurat_psikiatri");bpjs_referensi_setting_apotek=rs.getBoolean("bpjs_referensi_setting_apotek");
                    bpjs_referensi_obat_apotek=rs.getBoolean("bpjs_referensi_obat_apotek");bpjs_mapping_obat_apotek=rs.getBoolean("bpjs_mapping_obat_apotek");pembayaran_bank_mandiri=rs.getBoolean("pembayaran_bank_mandiri");penilaian_ulang_nyeri=rs.getBoolean("penilaian_ulang_nyeri");penilaian_terapi_wicara=rs.getBoolean("penilaian_terapi_wicara");bpjs_obat_23hari_apotek=rs.getBoolean("bpjs_obat_23hari_apotek");pengkajian_restrain=rs.getBoolean("pengkajian_restrain");bpjs_kunjungan_sep_apotek=rs.getBoolean("bpjs_kunjungan_sep_apotek");bpjs_monitoring_klaim_apotek=rs.getBoolean("bpjs_monitoring_klaim_apotek");
                    bpjs_daftar_pelayanan_obat_apotek=rs.getBoolean("bpjs_daftar_pelayanan_obat_apotek");penilaian_awal_medis_ralan_paru=rs.getBoolean("penilaian_awal_medis_ralan_paru");catatan_keperawatan_ralan=rs.getBoolean("catatan_keperawatan_ralan");catatan_persalinan=rs.getBoolean("catatan_persalinan");skor_aldrette_pasca_anestesi=rs.getBoolean("skor_aldrette_pasca_anestesi");skor_steward_pasca_anestesi=rs.getBoolean("skor_steward_pasca_anestesi");skor_bromage_pasca_anestesi=rs.getBoolean("skor_bromage_pasca_anestesi");penilaian_pre_induksi=rs.getBoolean("penilaian_pre_induksi");hasil_usg_urologi=rs.getBoolean("hasil_usg_urologi");
                    hasil_usg_gynecologi=rs.getBoolean("hasil_usg_gynecologi");hasil_pemeriksaan_ekg=rs.getBoolean("hasil_pemeriksaan_ekg");hapus_edit_sep_bpjs=rs.getBoolean("hapus_edit_sep_bpjs");satu_sehat_kirim_diet=rs.getBoolean("satu_sehat_kirim_diet");satu_sehat_mapping_obat=rs.getBoolean("satu_sehat_mapping_obat");dapur_ringkasan_pembelian=rs.getBoolean("dapur_ringkasan_pembelian");satu_sehat_kirim_medication=rs.getBoolean("satu_sehat_kirim_medication");satu_sehat_kirim_medicationrequest=rs.getBoolean("satu_sehat_kirim_medicationrequest");penatalaksanaan_terapi_okupasi=rs.getBoolean("penatalaksanaan_terapi_okupasi");
                    satu_sehat_kirim_medicationdispense=rs.getBoolean("satu_sehat_kirim_medicationdispense");hasil_usg_neonatus=rs.getBoolean("hasil_usg_neonatus");hasil_endoskopi_faring_laring=rs.getBoolean("hasil_endoskopi_faring_laring");satu_sehat_mapping_radiologi=rs.getBoolean("satu_sehat_mapping_radiologi");satu_sehat_kirim_servicerequest_radiologi=rs.getBoolean("satu_sehat_kirim_servicerequest_radiologi");hasil_endoskopi_hidung=rs.getBoolean("hasil_endoskopi_hidung");satu_sehat_kirim_specimen_radiologi=rs.getBoolean("satu_sehat_kirim_specimen_radiologi");master_masalah_keperawatan_neonatus=rs.getBoolean("master_masalah_keperawatan_neonatus");
                    master_rencana_keperawatan_neonatus=rs.getBoolean("master_rencana_keperawatan_neonatus");penilaian_awal_keperawatan_ranap_neonatus=rs.getBoolean("penilaian_awal_keperawatan_ranap_neonatus");satu_sehat_kirim_observation_radiologi=rs.getBoolean("satu_sehat_kirim_observation_radiologi");satu_sehat_kirim_diagnosticreport_radiologi=rs.getBoolean("satu_sehat_kirim_diagnosticreport_radiologi");hasil_endoskopi_telinga=rs.getBoolean("hasil_endoskopi_telinga");satu_sehat_mapping_lab=rs.getBoolean("satu_sehat_mapping_lab");satu_sehat_kirim_servicerequest_lab=rs.getBoolean("satu_sehat_kirim_servicerequest_lab");
                    satu_sehat_kirim_servicerequest_labmb=rs.getBoolean("satu_sehat_kirim_servicerequest_labmb");satu_sehat_kirim_specimen_lab=rs.getBoolean("satu_sehat_kirim_specimen_lab");satu_sehat_kirim_specimen_labmb=rs.getBoolean("satu_sehat_kirim_specimen_labmb");satu_sehat_kirim_observation_lab=rs.getBoolean("satu_sehat_kirim_observation_lab");satu_sehat_kirim_observation_labmb=rs.getBoolean("satu_sehat_kirim_observation_labmb");satu_sehat_kirim_diagnosticreport_lab=rs.getBoolean("satu_sehat_kirim_diagnosticreport_lab");satu_sehat_kirim_diagnosticreport_labmb=rs.getBoolean("satu_sehat_kirim_diagnosticreport_labmb");
                    kepatuhan_kelengkapan_keselamatan_bedah=rs.getBoolean("kepatuhan_kelengkapan_keselamatan_bedah");nilai_piutang_perjenis_bayar_per_bulan=rs.getBoolean("nilai_piutang_perjenis_bayar_per_bulan");ringkasan_piutang_jenis_bayar=rs.getBoolean("ringkasan_piutang_jenis_bayar");penilaian_pasien_imunitas_rendah=rs.getBoolean("penilaian_pasien_imunitas_rendah");balance_cairan=rs.getBoolean("balance_cairan");catatan_observasi_chbp=rs.getBoolean("catatan_observasi_chbp");catatan_observasi_induksi_persalinan=rs.getBoolean("catatan_observasi_induksi_persalinan");skp_kategori_penilaian=rs.getBoolean("skp_kategori_penilaian");
                    skp_kriteria_penilaian=rs.getBoolean("skp_kriteria_penilaian");skp_penilaian=rs.getBoolean("skp_penilaian");referensi_poli_mobilejknfktp=rs.getBoolean("referensi_poli_mobilejknfktp");referensi_dokter_mobilejknfktp=rs.getBoolean("referensi_dokter_mobilejknfktp");skp_rekapitulasi_penilaian=rs.getBoolean("skp_rekapitulasi_penilaian");pembayaran_pihak_ke3_bankmandiri=rs.getBoolean("pembayaran_pihak_ke3_bankmandiri");metode_pembayaran_bankmandiri=rs.getBoolean("metode_pembayaran_bankmandiri");bank_tujuan_transfer_bankmandiri=rs.getBoolean("bank_tujuan_transfer_bankmandiri");
                    kodetransaksi_tujuan_transfer_bankmandiri=rs.getBoolean("kodetransaksi_tujuan_transfer_bankmandiri");konsultasi_medik=rs.getBoolean("konsultasi_medik");jawaban_konsultasi_medik=rs.getBoolean("jawaban_konsultasi_medik");pcare_cek_alergi=rs.getBoolean("pcare_cek_alergi");pcare_cek_prognosa=rs.getBoolean("pcare_cek_prognosa");data_sasaran_usiaproduktif=rs.getBoolean("data_sasaran_usiaproduktif");data_sasaran_usialansia=rs.getBoolean("data_sasaran_usialansia");skrining_perilaku_merokok_sekolah_remaja=rs.getBoolean("skrining_perilaku_merokok_sekolah_remaja");
                    skrining_kekerasan_pada_perempuan=rs.getBoolean("skrining_kekerasan_pada_perempuan");skrining_obesitas=rs.getBoolean("skrining_obesitas");skrining_risiko_kanker_payudara=rs.getBoolean("skrining_risiko_kanker_payudara");skrining_risiko_kanker_paru=rs.getBoolean("skrining_risiko_kanker_paru");skrining_tbc=rs.getBoolean("skrining_tbc");skrining_kesehatan_gigi_mulut_remaja=rs.getBoolean("skrining_kesehatan_gigi_mulut_remaja");penilaian_awal_keperawatan_ranap_bayi=rs.getBoolean("penilaian_awal_keperawatan_ranap_bayi");booking_mcu_perusahaan=rs.getBoolean("booking_mcu_perusahaan");
                    catatan_observasi_restrain_nonfarma=rs.getBoolean("catatan_observasi_restrain_nonfarma");catatan_observasi_ventilator=rs.getBoolean("catatan_observasi_ventilator");catatan_anestesi_sedasi=rs.getBoolean("catatan_anestesi_sedasi");skrining_puma=rs.getBoolean("skrining_puma");satu_sehat_kirim_careplan=rs.getBoolean("satu_sehat_kirim_careplan");satu_sehat_kirim_medicationstatement=rs.getBoolean("satu_sehat_kirim_medicationstatement");skrining_adiksi_nikotin=rs.getBoolean("skrining_adiksi_nikotin");skrining_thalassemia=rs.getBoolean("skrining_thalassemia");skrining_instrumen_sdq=rs.getBoolean("skrining_instrumen_sdq");
                    skrining_instrumen_srq=rs.getBoolean("skrining_instrumen_srq");checklist_pemberian_fibrinolitik=rs.getBoolean("checklist_pemberian_fibrinolitik");skrining_kanker_kolorektal=rs.getBoolean("skrining_kanker_kolorektal");dapur_pemesanan=rs.getBoolean("dapur_pemesanan");bayar_pesan_dapur=rs.getBoolean("bayar_pesan_dapur");hutang_dapur=rs.getBoolean("hutang_dapur");
                    setTampil();
                }       
                LCount.setText(""+tabMode.getRowCount());
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
    
    private void setTampil(){
        if("[A]Jadwal Praktek".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Jadwal Praktek",jadwal_praktek});
        }

        if("[A]Registrasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Registrasi",registrasi});
        }

        if("[A]Tindakan Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Tindakan Ralan",tindakan_ralan});
        }

        if("[A]Rawat Inap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Rawat Inap",kamar_inap});
        }

        if("[A]Tindakan Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Tindakan Ranap",tindakan_ranap});
        }

        if("[A]Operasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Operasi",operasi});
        }

        if("[A]Rujukan Keluar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Rujukan Keluar",rujukan_keluar});
        }

        if("[A]Rujukan Masuk".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Rujukan Masuk",rujukan_masuk});
        }

        if("[A]Beri Obat, Alkes & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Beri Obat, Alkes & BHP",beri_obat});
        }

        if("[A]Resep Pulang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Resep Pulang",resep_pulang});
        }

        if("[A]Diet Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Diet Pasien",diet_pasien});
        }

        if("[A]Periksa Lab PK".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Periksa Lab PK",periksa_lab});
        }

        if("[A]Periksa Radiologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Periksa Radiologi",periksa_radiologi});
        }

        if("[A]Rawat Jalan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Rawat Jalan",kasir_ralan});
        }

        if("[A]Informasi Kamar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Informasi Kamar",informasi_kamar});
        }

        if("[A]No.Resep".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]No.Resep",resep_obat});
        }

        if("[A]Billing Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Billing Ralan",billing_ralan});
        }

        if("[A]Billing Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Billing Ranap",billing_ranap});
        }

        if("[A]IGD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]IGD",igd});
        }

        if("[A]DPJP Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]DPJP Ranap",dpjp_ranap});
        }

        if("[A]Edit Registrasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Edit Registrasi",edit_registrasi});
        }

        if("[A]Rujukan Poli Internal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Rujukan Poli Internal",rujukan_poli_internal});
        }

        if("[A]Billing Parsial".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Billing Parsial",billing_parsial});
        }

        if("[A]Akses Depo Obat/BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Akses Depo Obat/BHP",akses_depo_obat});
        }

        if("[A]Jadwal Operasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Jadwal Operasi",booking_operasi});
        }

        if("[A]Booking Registrasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Booking Registrasi",booking_registrasi});
        }

        if("[A]Permintaan Lab".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Permintaan Lab",permintaan_lab});
        }

        if("[A]Permintaan Radiologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Permintaan Radiologi",permintaan_radiologi});
        }

        if("[A]Catatan Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Catatan Dokter",catatan_perawatan});
        }

        if("[A]Asesmen Awal Rawat Inap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Asesmen Awal Rawat Inap",pengkajian_askep});
        }

        if("[A]Skrining Rawat Jalan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Skrining Rawat Jalan",sekrining_rawat_jalan});
        }

        if("[A]Perkiraan Biaya Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Perkiraan Biaya Ranap",perkiraan_biaya_ranap});
        }

        if("[A]Permintaan Diet".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Permintaan Diet",permintaan_diet});
        }

        if("[A]Deteksi Dini Corona".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Deteksi Dini Corona",deteksi_corona});
        }

        if("[A]Booking Periksa".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Booking Periksa",booking_periksa});
        }

        if("[A]Periksa Lab PA".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Periksa Lab PA",pemeriksaan_lab_pa});
        }

        if("[A]Permintaan Rawat Inap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Permintaan Rawat Inap",permintaan_ranap});
        }

        if("[A]Ubah Petugas Lab PK".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Ubah Petugas Lab PK",ubah_petugas_lab_pk});
        }

        if("[A]Ubah Petugas Lab PA".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Ubah Petugas Lab PA",ubah_petugas_lab_pa});
        }

        if("[A]Ubah Petugas Radiologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Ubah Petugas Radiologi",ubah_petugas_radiologi});
        }

        if("[A]Gabung Nomor Rawat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Gabung Nomor Rawat",gabung_norawat});
        }

        if("[A]Periksa Lab MB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Periksa Lab MB",pemeriksaan_lab_mb});
        }

        if("[A]Ubah Petugas Lab MB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Ubah Petugas Lab MB",ubah_petugas_lab_mb});
        }
        
        if("[A]Akses Ke Dokter Lain Rawat Jalan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Akses Ke Dokter Lain Rawat Jalan",akses_dokter_lain_rawat_jalan});
        }
        
        if("[A]Booking MCU Perusahaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[A]Booking MCU Perusahaan",booking_mcu_perusahaan});
        }

        if("[B]Barcode Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[B]Barcode Ralan",barcoderalan});
        }

        if("[B]Barcode Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[B]Barcode Ranap",barcoderanap});
        }

        if("[C]Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Dokter",dokter});
        }

        if("[C]Petugas".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Petugas",petugas});
        }

        if("[C]Barcode Presensi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Barcode Presensi",barcode});
        }

        if("[C]Presensi Harian".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Presensi Harian",presensi_harian});
        }

        if("[C]Presensi Bulanan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Presensi Bulanan",presensi_bulanan});
        }

        if("[C]Pegawai Admin".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Pegawai Admin",pegawai_admin});
        }

        if("[C]Pegawai User".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Pegawai User",pegawai_user});
        }

        if("[C]SMS Gateway".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]SMS Gateway",sms});
        }

        if("[C]Sidik Jari".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Sidik Jari",sidikjari});
        }

        if("[C]Jam Presensi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Jam Presensi",jam_masuk});
        }

        if("[C]Jadwal Pegawai".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Jadwal Pegawai",jadwal_pegawai});
        }

        if("[C]Temporary Presensi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Temporary Presensi",temporary_presensi});
        }

        if("[C]Master Berkas Pegawai".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Master Berkas Pegawai",master_berkas_pegawai});
        }

        if("[C]Berkas Kepegawaian".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Berkas Kepegawaian",berkas_kepegawaian});
        }

        if("[C]Riwayat Jabatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Riwayat Jabatan",riwayat_jabatan});
        }

        if("[C]Riwayat Pendidikan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Riwayat Pendidikan",riwayat_pendidikan});
        }

        if("[C]Riwayat Naik Gaji".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Riwayat Naik Gaji",riwayat_naik_gaji});
        }

        if("[C]Kegiatan Ilmiah & Pelatihan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Kegiatan Ilmiah & Pelatihan",kegiatan_ilmiah});
        }

        if("[C]Riwayat Penghargaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Riwayat Penghargaan",riwayat_penghargaan});
        }

        if("[C]Riwayat Penelitian".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Riwayat Penelitian",riwayat_penelitian});
        }

        if("[C]Jenis Cidera K3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Jenis Cidera K3",jenis_cidera_k3rs});
        }

        if("[C]Penyebab Kecelakaan K3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Penyebab Kecelakaan K3",penyebab_k3rs});
        }

        if("[C]Jenis Luka K3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Jenis Luka K3",jenis_luka_k3rs});
        }

        if("[C]Lokasi Kejadian K3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Lokasi Kejadian K3",lokasi_kejadian_k3rs});
        }

        if("[C]Dampak Cidera K3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Dampak Cidera K3",dampak_cidera_k3rs});
        }

        if("[C]Jenis Pekerjaan K3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Jenis Pekerjaan K3",jenis_pekerjaan_k3rs});
        }

        if("[C]Bagian Tubuh K3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Bagian Tubuh K3",bagian_tubuh_k3rs});
        }

        if("[C]Peristiwa K3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Peristiwa K3",peristiwa_k3rs});
        }

        if("[C]Jenis Cidera K3 Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Jenis Cidera K3 Per Tahun",jenis_cidera_k3rstahun});
        }

        if("[C]Penyebab Kecelakaan K3 Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Penyebab Kecelakaan K3 Per Tahun",penyebab_k3rstahun});
        }

        if("[C]Jenis Luka K3 Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Jenis Luka K3 Per Tahun",jenis_luka_k3rstahun});
        }

        if("[C]Lokasi Kejadian K3 Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Lokasi Kejadian K3 Per Tahun",lokasi_kejadian_k3rstahun});
        }

        if("[C]Dampak Cidera K3 Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Dampak Cidera K3 Per Tahun",dampak_cidera_k3rstahun});
        }

        if("[C]Jenis Pekerjaan K3 Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Jenis Pekerjaan K3 Per Tahun",jenis_pekerjaan_k3rstahun});
        }

        if("[C]Bagian Tubuh K3 Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Bagian Tubuh K3 Per Tahun",bagian_tubuh_k3rstahun});
        }

        if("[C]Pengajuan Cuti".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Pengajuan Cuti",pengajuan_cuti});
        }

        if("[C]Audit Kepatuhan APD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Kepatuhan APD",audit_kepatuhan_apd});
        }

        if("[C]Audit Cuci Tangan Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Cuci Tangan Medis",audit_cuci_tangan_medis});
        }

        if("[C]Audit Pembuangan Limbah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Pembuangan Limbah",audit_pembuangan_limbah});
        }

        if("[C]Ruang/Unit Audit Kepatuhan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Ruang/Unit Audit Kepatuhan",ruang_audit_kepatuhan});
        }

        if("[C]Audit Pembuangan Benda Tajam & Jarum".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Pembuangan Benda Tajam & Jarum",audit_pembuangan_benda_tajam});
        }

        if("[C]Audit Penanganan Darah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Penanganan Darah",audit_penanganan_darah});
        }

        if("[C]Audit Pengelolaan Linen Kotor".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Pengelolaan Linen Kotor",audit_pengelolaan_linen_kotor});
        }

        if("[C]Audit Penempatan Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Penempatan Pasien",audit_penempatan_pasien});
        }

        if("[C]Audit Kamar Jenazah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Kamar Jenazah",audit_kamar_jenazah});
        }

        if("[C]Audit Bundle IADP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Bundle IADP",audit_bundle_iadp});
        }

        if("[C]Audit Bundle IDO".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Bundle IDO",audit_bundle_ido});
        }

        if("[C]Audit Fasilitas Kebersihan Tangan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Fasilitas Kebersihan Tangan",audit_fasilitas_kebersihan_tangan});
        }

        if("[C]Audit Fasilitas APD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Fasilitas APD",audit_fasilitas_apd});
        }

        if("[C]Audit Pembuangan Limbah Cair Infeksius".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Pembuangan Limbah Cair Infeksius",audit_pembuangan_limbah_cair_infeksius});
        }

        if("[C]Audit Sterilisasi Alat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Sterilisasi Alat",audit_sterilisasi_alat});
        }

        if("[C]Audit Bundle ISK".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Bundle ISK",audit_bundle_isk});
        }

        if("[C]Audit Bundle PLABSI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Bundle PLABSI",audit_bundle_plabsi});
        }

        if("[C]Audit Bundle VAP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Audit Bundle VAP",audit_bundle_vap});
        }
        
        if("[C]Kategori Penilaian SKP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Kategori Penilaian SKP",skp_kategori_penilaian});
        }
        
        if("[C]Kriteria Penilaian SKP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Kriteria Penilaian SKP",skp_kriteria_penilaian});
        }
        
        if("[C]Penilaian SKP Petugas/Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Penilaian SKP Petugas/Dokter",skp_penilaian});
        }
        
        if("[C]Rekapitulasi Penilaian SKP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[C]Rekapitulasi Penilaian SKP",skp_rekapitulasi_penilaian});
        }

        if("[D]Suplier Obat/Alkes/BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Suplier Obat/Alkes/BHP",suplier});
        }

        if("[D]Satuan Barang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Satuan Barang",satuan_barang});
        }

        if("[D]Konversi Satuan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Konversi Satuan",konversi_satuan});
        }

        if("[D]Jenis Obat/Alkes/BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Jenis Obat/Alkes/BHP",jenis_barang});
        }

        if("[D]Obat, Alkes & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Obat, Alkes & BHP",obat});
        }

        if("[D]Stok Opname Apotek".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Stok Opname Apotek",stok_opname_obat});
        }

        if("[D]Stok Obat Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Stok Obat Pasien",stok_obat_pasien});
        }

        if("[D]Pengadaan Obat, Alkes & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Pengadaan Obat, Alkes & BHP",pengadaan_obat});
        }

        if("[D]Penerimaan Obat, Alkes & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Penerimaan Obat, Alkes & BHP",pemesanan_obat});
        }

        if("[D]Penjualan Obat, Alkes & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Penjualan Obat, Alkes & BHP",penjualan_obat});
        }

        if("[D]Piutang Obat, Alkes & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Piutang Obat, Alkes & BHP",piutang_obat});
        }

        if("[D]Retur Ke Suplier".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Retur Ke Suplier",retur_ke_suplier});
        }

        if("[D]Retur Dari Pembeli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Retur Dari Pembeli",retur_dari_pembeli});
        }

        if("[D]Retur Obat, Alkes & BHP Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Retur Obat, Alkes & BHP Ranap",retur_obat_ranap});
        }

        if("[D]Retur Piutang Pembeli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Retur Piutang Pembeli",retur_piutang_pasien});
        }

        if("[D]Keuntungan Penjualan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Keuntungan Penjualan",keuntungan_penjualan});
        }

        if("[D]Keuntungan Beri Obat, Alkes & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Keuntungan Beri Obat, Alkes & BHP",keuntungan_beri_obat});
        }

        if("[D]Sirkulasi Obat, Alkes & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Sirkulasi Obat, Alkes & BHP",sirkulasi_obat});
        }

        if("[D]Mutasi Obat/Alkes/BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Mutasi Obat/Alkes/BHP",mutasi_barang});
        }

        if("[D]Darurat Stok".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Darurat Stok",darurat_stok});
        }

        if("[D]Sirkulasi Obat, Alkes & BHP 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Sirkulasi Obat, Alkes & BHP 2",sirkulasi_obat2});
        }

        if("[D]Industri Farmasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Industri Farmasi",industrifarmasi});
        }

        if("[D]Pengambilan BHP UTD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Pengambilan BHP UTD",pengambilan_utd});
        }

        if("[D]Keuntungan Beri Obat, Alkes & BHP 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Keuntungan Beri Obat, Alkes & BHP 2",keuntungan_beri_obat_nonpiutang});
        }

        if("[D]Riwayat Obat, Alkes & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Riwayat Obat, Alkes & BHP",riwayat_obat_alkes_bhp});
        }

        if("[D]Resep Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Resep Dokter",resep_dokter});
        }

        if("[D]Kategori Obat/Alkes/BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Kategori Obat/Alkes/BHP",kategori_barang});
        }

        if("[D]Golongan Obat/Alkes/BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Golongan Obat/Alkes/BHP",golongan_barang});
        }

        if("[D]Obat/Alkes/BHP Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Obat/Alkes/BHP Per Tanggal",pemberian_obat_pertanggal});
        }

        if("[D]Penjualan Bebas Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Penjualan Bebas Per Tanggal",penjualan_obat_pertanggal});
        }

        if("[D]Permintaan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Permintaan Obat & BHP",permintaan_medis});
        }

        if("[D]Ringkasan Permintaan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Permintaan Obat & BHP",rekap_permintaan_medis});
        }

        if("[D]Surat Pemesanan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Surat Pemesanan Obat & BHP",surat_pemesanan_medis});
        }

        if("[D]Stok Keluar Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Stok Keluar Medis",pengeluaran_stok_apotek});
        }

        if("[D]Metode Racik".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Metode Racik",metode_racik});
        }

        if("[D]Pengguna Obat/Alkes/BHP Resep".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Pengguna Obat/Alkes/BHP Resep",pengguna_obat_resep});
        }

        if("[D]Rekap Penerimaan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Rekap Penerimaan Obat & BHP",rekap_pemesanan});
        }

        if("[D]Riwayat Batch".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Riwayat Batch",riwayat_data_batch});
        }

        if("[D]Kegiatan Farmasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Kegiatan Farmasi",kegiatan_farmasi});
        }

        if("[D]Sirkulasi Obat, Alkes & BHP 3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Sirkulasi Obat, Alkes & BHP 3",sirkulasi_obat3});
        }

        if("[D]PPN Obat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]PPN Obat",ppn_obat});
        }

        if("[D]Data Batch".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Data Batch",data_batch});
        }

        if("[D]Kadaluarsa Batch".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Kadaluarsa Batch",kadaluarsa_batch});
        }

        if("[D]Sisa Stok".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Sisa Stok",sisa_stok});
        }

        if("[D]Obat Per Resep".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Obat Per Resep",obat_per_resep});
        }

        if("[D]Pengajuan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Pengajuan Obat & BHP",pengajuan_barang_medis});
        }

        if("[D]10 Obat Terbanyak Poli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]10 Obat Terbanyak Poli",sepuluh_obat_terbanyak_poli});
        }

        if("[D]Rekap Obat Per Poli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Rekap Obat Per Poli",rekap_obat_poli});
        }

        if("[D]Rekap Obat Per Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Rekap Obat Per Pasien",rekap_obat_pasien});
        }

        if("[D]Hibah Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Hibah Obat & BHP",hibah_obat_bhp});
        }

        if("[D]Sirkulasi Obat, Alkes & BHP 4".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Sirkulasi Obat, Alkes & BHP 4",sirkulasi_obat4});
        }

        if("[D]Sirkulasi Obat, Alkes & BHP 5".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Sirkulasi Obat, Alkes & BHP 5",sirkulasi_obat5});
        }

        if("[D]Permintaan Stok Obat Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Permintaan Stok Obat Pasien",permintaan_stok_obat_pasien});
        }

        if("[D]Verifikasi Penerimaan Obat/Alkes/BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Verifikasi Penerimaan Obat/Alkes/BHP",verifikasi_penerimaan_farmasi});
        }

        if("[D]Ringkasan Pengajuan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Pengajuan Obat & BHP",ringkasan_pengajuan_obat});
        }

        if("[D]Ringkasan Pemesanan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Pemesanan Obat & BHP",ringkasan_pemesanan_obat});
        }

        if("[D]Ringkasan Pengadaan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Pengadaan Obat & BHP",ringkasan_pengadaan_obat});
        }

        if("[D]Ringkasan Penerimaan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Penerimaan Obat & BHP",ringkasan_penerimaan_obat});
        }

        if("[D]Ringkasan Hibah Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Hibah Obat & BHP",ringkasan_hibah_obat});
        }

        if("[D]Ringkasan Penjualan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Penjualan Obat & BHP",ringkasan_penjualan_obat});
        }

        if("[D]Ringkasan Beri Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Beri Obat & BHP",ringkasan_beri_obat});
        }

        if("[D]Ringkasan Piutang Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Piutang Obat & BHP",ringkasan_piutang_obat});
        }

        if("[D]Ringkasan Stok Keluar Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Stok Keluar Obat & BHP",ringkasan_stok_keluar_obat});
        }

        if("[D]Ringkasan Retur Suplier Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Retur Suplier Obat & BHP",ringkasan_retur_suplier_obat});
        }

        if("[D]Ringkasan Retur Pembeli Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Retur Pembeli Obat & BHP",ringkasan_retur_pembeli_obat});
        }

        if("[D]Penggunaan BHP OK/VK".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Penggunaan BHP OK/VK",penggunaan_bhp_ok});
        }

        if("[D]Resep Luar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Resep Luar",resep_luar});
        }

        if("[D]Stok Akhir Farmasi Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Stok Akhir Farmasi Per Tanggal",stok_akhir_farmasi_pertanggal});
        }

        if("[D]Telaah Resep & Obat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Telaah Resep & Obat",telaah_resep});
        }

        if("[D]Permintaan Resep Pulang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Permintaan Resep Pulang",permintaan_resep_pulang});
        }

        if("[D]Ringkasan Biaya Obat Pasien Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Ringkasan Biaya Obat Pasien Per Tanggal",ringkasan_biaya_obat_pasien_pertanggal});
        }

        if("[D]Nilai Penerimaan Vendor Farmasi Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Nilai Penerimaan Vendor Farmasi Per Bulan",nilai_penerimaan_vendor_farmasi_perbulan});
        }

        if("[D]Obat/Alkes/BHP Tidak Bergerak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Obat/Alkes/BHP Tidak Bergerak",obat_bhp_tidakbergerak});
        }

        if("[D]Sirkulasi Obat, Alkes & BHP 6".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[D]Sirkulasi Obat, Alkes & BHP 6",sirkulasi_obat6});
        }

        if("[E]Barang Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Barang Non Medis",ipsrs_barang});
        }

        if("[E]Pengadaan Barang Nonmedis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Pengadaan Barang Nonmedis",ipsrs_pengadaan_barang});
        }

        if("[E]Stok Keluar Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Stok Keluar Non Medis",ipsrs_stok_keluar});
        }

        if("[E]Rekap Pengadaan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Rekap Pengadaan Non Medis",ipsrs_rekap_pengadaan});
        }

        if("[E]Rekap Stok Keluar Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Rekap Stok Keluar Non Medis",ipsrs_rekap_stok_keluar});
        }

        if("[E]Biaya Pengadaan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Biaya Pengadaan Non Medis",ipsrs_pengeluaran_harian});
        }

        if("[E]Jenis Barang Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Jenis Barang Non Medis",ipsrs_jenis_barang});
        }

        if("[E]Pengambilan UTD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Pengambilan UTD",pengambilan_penunjang_utd});
        }

        if("[E]Suplier Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Suplier Non Medis",suplier_penunjang});
        }

        if("[E]Pengadaan Non Medis Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Pengadaan Non Medis Per Tanggal",ipsrs_pengadaan_pertanggal});
        }

        if("[E]Stok Keluar Non Medis Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Stok Keluar Non Medis Per Tanggal",ipsrs_stokkeluar_pertanggal});
        }

        if("[E]Permintaan Barang Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Permintaan Barang Non Medis",permintaan_non_medis});
        }

        if("[E]Ringkasan Permintaan Barang Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Ringkasan Permintaan Barang Non Medis",rekap_permintaan_non_medis});
        }

        if("[E]Surat Pemesanan Barang Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Surat Pemesanan Barang Non Medis",surat_pemesanan_non_medis});
        }

        if("[E]Penerimaan Barang Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Penerimaan Barang Non Medis",penerimaan_non_medis});
        }

        if("[E]Rekap Penerimaan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Rekap Penerimaan Non Medis",rekap_pemesanan_non_medis});
        }

        if("[E]Stok Opname Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Stok Opname Non Medis",stok_opname_logistik});
        }

        if("[E]Sirkulasi Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Sirkulasi Non Medis",sirkulasi_non_medis});
        }

        if("[E]Pengajuan Barang Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Pengajuan Barang Non Medis",pengajuan_barang_nonmedis});
        }

        if("[E]Sirkulasi Non Medis 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Sirkulasi Non Medis 2",sirkulasi_non_medis});
        }

        if("[E]Retur Ke Suplier Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Retur Ke Suplier Non Medis",ipsrs_returbeli});
        }

        if("[E]Riwayat Barang Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Riwayat Barang Non Medis",ipsrs_riwayat_barang});
        }

        if("[E]Verifikasi Penerimaan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Verifikasi Penerimaan Non Medis",verifikasi_penerimaan_logistik});
        }

        if("[E]Ringkasan Pengajuan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Ringkasan Pengajuan Non Medis",ringkasan_pengajuan_nonmedis});
        }

        if("[E]Ringkasan Pemesanan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Ringkasan Pemesanan Non Medis",ringkasan_pemesanan_nonmedis});
        }

        if("[E]Ringkasan Pengadaan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Ringkasan Pengadaan Non Medis",ringkasan_pengadaan_nonmedis});
        }

        if("[E]Ringkasan Penerimaan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Ringkasan Penerimaan Non Medis",ringkasan_penerimaan_nonmedis});
        }

        if("[E]Ringkasan Stok Keluar Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Ringkasan Stok Keluar Non Medis",ringkasan_stokkeluar_nonmedis});
        }

        if("[E]Ringkasan Retur Suplier Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Ringkasan Retur Suplier Non Medis",ringkasan_returbeli_nonmedis});
        }

        if("[E]Hibah Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Hibah Non Medis",hibah_non_medis});
        }

        if("[E]Nilai Penerimaan Vendor Non Medis Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[E]Nilai Penerimaan Vendor Non Medis Per Bulan",nilai_penerimaan_vendor_nonmedis_perbulan});
        }
        
        if("[F]Barang Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Barang Dapur",dapur_barang});
        }
        
        if("[F]Stok Opname Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Stok Opname Dapur",dapur_opname});
        }
        
        if("[F]Suplier Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Suplier Dapur",dapur_suplier});
        }
        
        if("[F]Pengadaan Barang Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Pengadaan Barang Dapur",dapur_pembelian});
        }
        
        if("[F]Stok Keluar Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Stok Keluar Dapur",dapur_stok_keluar});
        }
        
        if("[F]Riwayat Barang Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Riwayat Barang Dapur",dapur_riwayat_barang});
        }
        
        if("[F]Permintaan Barang Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Permintaan Barang Dapur",permintaan_dapur});
        }
        
        if("[F]Biaya Pengadaan Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Biaya Pengadaan Dapur",biaya_pengadaan_dapur});
        }
        
        if("[F]Rekap Pengadaan Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Rekap Pengadaan Dapur",rekap_pengadaan_dapur});
        }
        
        if("[F]Ringkasan Pengadaan Barang Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Ringkasan Pengadaan Barang Dapur",dapur_ringkasan_pembelian});
        }
        
        if("[F]Penerimaan Barang Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[F]Penerimaan Barang Dapur",dapur_pemesanan});
        }

        if("[G]Jenis Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Jenis Inventaris",inventaris_jenis});
        }

        if("[G]Kategori Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Kategori Inventaris",inventaris_kategori});
        }

        if("[G]Merk Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Merk Inventaris",inventaris_merk});
        }

        if("[G]Ruang Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Ruang Inventaris",inventaris_ruang});
        }

        if("[G]Produsen Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Produsen Inventaris",inventaris_produsen});
        }

        if("[G]Koleksi Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Koleksi Inventaris",inventaris_koleksi});
        }

        if("[G]Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Inventaris",inventaris_inventaris});
        }

        if("[G]Sirkulasi Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Sirkulasi Inventaris",inventaris_sirkulasi});
        }

        if("[G]Barang CSSD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Barang CSSD",barang_cssd});
        }

        if("[G]Pemakaian Air PDAM".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Pemakaian Air PDAM",pemakaian_air_pdam});
        }

        if("[G]Limbah Padat B3 Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Limbah Padat B3 Medis",limbah_b3_medis});
        }

        if("[G]Limbah Padat Domestik".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Limbah Padat Domestik",limbah_domestik});
        }

        if("[G]Mutu Air Limbah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Mutu Air Limbah",mutu_air_limbah});
        }

        if("[G]Pest Control".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Pest Control",pest_control});
        }

        if("[G]Pengajuan Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Pengajuan Aset/Inventaris",pengajuan_asetinventaris});
        }

        if("[G]Rekap Pengajuan Aset Departemen".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Rekap Pengajuan Aset Departemen",rekap_pengajuan_aset_departemen});
        }

        if("[G]Permintaan Perbaikan Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Permintaan Perbaikan Inventaris",permintaan_perbaikan_inventaris});
        }

        if("[G]Asal Hibah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Asal Hibah",asal_hibah});
        }

        if("[G]Pemakaian Air Tanah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Pemakaian Air Tanah",pemakaian_air_tanah});
        }

        if("[G]Perbaikan Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Perbaikan Inventaris",perbaikan_inventaris});
        }

        if("[G]Pemeliharaan Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Pemeliharaan Inventaris",pemeliharaan_inventaris});
        }

        if("[G]Pengadaan Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Pengadaan Aset/Inventaris",pengadaan_aset_inventaris});
        }

        if("[G]Suplier Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Suplier Aset/Inventaris",suplier_inventaris});
        }

        if("[G]Penerimaan Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Penerimaan Aset/Inventaris",penerimaan_aset_inventaris});
        }

        if("[G]Hibah Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Hibah Aset/Inventaris",hibah_aset_inventaris});
        }

        if("[G]Pemeliharaan Gedung".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Pemeliharaan Gedung",pemeliharaan_gedung});
        }
        
        if("[G]Limbah Cair B3 Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[G]Limbah Cair B3 Medis",kesling_limbah_b3medis_cair});
        }

        if("[H]Jenis Parkir".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[H]Jenis Parkir",parkir_jenis});
        }

        if("[H]Parkir Masuk".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[H]Parkir Masuk",parkir_in});
        }

        if("[H]Parkir Keluar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[H]Parkir Keluar",parkir_out});
        }

        if("[H]Rekap Parkir Harian".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[H]Rekap Parkir Harian",parkir_rekap_harian});
        }

        if("[H]Rekap Parkir Bulanan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[H]Rekap Parkir Bulanan",parkir_rekap_bulanan});
        }

        if("[H]Barcode Parkir".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[H]Barcode Parkir",parkir_barcode});
        }

        if("[I]Harian Dokter Poli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Harian Dokter Poli",harian_tindakan_poli});
        }

        if("[I]Obat Per Poli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Obat Per Poli",obat_per_poli});
        }

        if("[I]Obat Per Kamar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Obat Per Kamar",obat_per_kamar});
        }

        if("[I]Obat Per Dokter Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Obat Per Dokter Ralan",obat_per_dokter_ralan});
        }

        if("[I]Obat Per Dokter Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Obat Per Dokter Ranap",obat_per_dokter_ranap});
        }

        if("[I]Harian Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Harian Dokter",harian_dokter});
        }

        if("[I]Bulanan Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Bulanan Dokter",bulanan_dokter});
        }

        if("[I]Harian Paramedis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Harian Paramedis",harian_paramedis});
        }

        if("[I]Bulanan Paramedis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Bulanan Paramedis",bulanan_paramedis});
        }

        if("[I]Pembayaran Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Pembayaran Ralan",pembayaran_ralan});
        }

        if("[I]Pembayaran Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Pembayaran Ranap",pembayaran_ranap});
        }

        if("[I]Rekap Pembayaran Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Rekap Pembayaran Ralan",rekap_pembayaran_ralan});
        }

        if("[I]Rekap Pembayaran Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Rekap Pembayaran Ranap",rekap_pembayaran_ranap});
        }

        if("[I]Tagihan Masuk".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Tagihan Masuk",tagihan_masuk});
        }

        if("[I]Tambahan Biaya".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Tambahan Biaya",tambahan_biaya});
        }

        if("[I]Potongan Biaya".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Potongan Biaya",potongan_biaya});
        }

        if("[I]Detail JM Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Detail JM Dokter",jm_ranap_dokter});
        }

        if("[I]Harian Dokter Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Harian Dokter Ralan",harian_tindakan_dokter});
        }

        if("[I]Fee Visit Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Fee Visit Dokter",fee_visit_dokter});
        }

        if("[I]Fee Bacaan EKG".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Fee Bacaan EKG",fee_bacaan_ekg});
        }

        if("[I]Fee Rujukan Rontgen".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Fee Rujukan Rontgen",fee_rujukan_rontgen});
        }

        if("[I]Fee Rujukan Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Fee Rujukan Ranap",fee_rujukan_ranap});
        }

        if("[I]Fee Periksa Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Fee Periksa Ralan",fee_ralan});
        }

        if("[I]Obat Per Dokter Peresep".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Obat Per Dokter Peresep",obat_per_dokter_peresep});
        }

        if("[I]Rekap Per Shift".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Rekap Per Shift",rekap_per_shift});
        }

        if("[I]Obat Per Cara Bayar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Obat Per Cara Bayar",obat_per_cara_bayar});
        }

        if("[I]Payment Point".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Payment Point",payment_point});
        }

        if("[I]Harian J.S.".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Harian J.S.",harian_js});
        }

        if("[I]Bulanan J.S.".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Bulanan J.S.",bulanan_js});
        }

        if("[I]Harian BHP Medis/Paket Obat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Harian BHP Medis/Paket Obat",harian_paket_bhp});
        }

        if("[I]Bulanan BHP Medis/Paket Obat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Bulanan BHP Medis/Paket Obat",bulanan_paket_bhp});
        }

        if("[I]Harian Kamar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Harian Kamar",harian_kamar});
        }

        if("[I]Harian KSO".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Harian KSO",harian_kso});
        }

        if("[I]Bulanan KSO".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Bulanan KSO",bulanan_kso});
        }

        if("[I]Harian Menejemen".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Harian Menejemen",harian_menejemen});
        }

        if("[I]Bulanan Menejemen".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Bulanan Menejemen",bulanan_menejemen});
        }

        if("[I]Piutang Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Piutang Ralan",piutang_ralan});
        }

        if("[I]Piutang Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Piutang Ranap",piutang_ranap});
        }

        if("[I]Detail Tindakan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Detail Tindakan",detail_tindakan});
        }

        if("[I]Rekap Poli Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Rekap Poli Anak",rekap_poli_anak});
        }

        if("[I]Pembayaran Per Unit".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Pembayaran Per Unit",pembayaran_per_unit});
        }

        if("[I]Rekap Pembayaran Per Unit".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Rekap Pembayaran Per Unit",rekap_pembayaran_per_unit});
        }

        if("[I]Detail VK/OK".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Detail VK/OK",detail_tindakan_okvk});
        }

        if("[I]Detail JM Dokter 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Detail JM Dokter 2",detailjmdokter2});
        }

        if("[I]Pembayaran Per Akun Bayar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Pembayaran Per Akun Bayar",pembayaran_akun_bayar});
        }

        if("[I]Piutang Per Akun Piutang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Piutang Per Akun Piutang",piutang_akun_piutang});
        }

        if("[I]Payment Point 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Payment Point 2",payment_point2});
        }

        if("[I]Pembayaran Per Akun Bayar 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Pembayaran Per Akun Bayar 2",pembayaran_akun_bayar2});
        }

        if("[I]Hapus Nota Salah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Hapus Nota Salah",hapus_nota_salah});
        }

        if("[I]Pembayaran Per Akun Bayar 3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Pembayaran Per Akun Bayar 3",pembayaran_akun_bayar3});
        }

        if("[I]Ringkasan Tindakan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Ringkasan Tindakan",ringkasan_tindakan});
        }

        if("[I]Pembayaran Per Akun Bayar 4".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Pembayaran Per Akun Bayar 4",pembayaran_akun_bayar4});
        }

        if("[I]Pembayaran Per Akun Bayar 5".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Pembayaran Per Akun Bayar 5",pembayaran_akun_bayar5});
        }

        if("[I]Jasa Tindakan Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Jasa Tindakan Pasien",jasa_tindakan_pasien});
        }

        if("[I]Rekap JM Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Rekap JM Dokter",rekap_jm_dokter});
        }
        
        if("[I]Rekap Biaya Registrasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[I]Rekap Biaya Registrasi",rekap_biaya_registrasi});
        }

        if("[J]ICD 10".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]ICD 10",penyakit});
        }

        if("[J]Obat Penyakit".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Obat Penyakit",obat_penyakit});
        }

        if("[J]Frekuensi Penyakit Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Frekuensi Penyakit Ralan",penyakit_ralan});
        }

        if("[J]Frekuensi Penyakit Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Frekuensi Penyakit Ranap",penyakit_ranap});
        }

        if("[J]Penyakit AFP & PD3I".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Penyakit AFP & PD3I",penyakit_pd3i});
        }

        if("[J]Surveilans AFP & PD3I".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Surveilans AFP & PD3I",surveilans_pd3i});
        }

        if("[J]Surveilans Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Surveilans Ralan",surveilans_ralan});
        }

        if("[J]Surveilans Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Surveilans Ranap",surveilans_ranap});
        }

        if("[J]Pny.Tdk Menular Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Pny.Tdk Menular Ranap",pny_takmenular_ranap});
        }

        if("[J]Pny.Tdk Menular Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Pny.Tdk Menular Ralan",pny_takmenular_ralan});
        }

        if("[J]Kunjungan Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Kunjungan Ralan",kunjungan_ralan});
        }

        if("[J]RL 3.2 Rawat Darurat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 3.2 Rawat Darurat",rl32});
        }

        if("[J]RL 3.3 Gigi dan Mulut".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 3.3 Gigi dan Mulut",rl33});
        }

        if("[J]RL 3.7 Radiologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 3.7 Radiologi",rl37});
        }

        if("[J]RL 3.8 Laboratorium".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 3.8 Laboratorium",rl38});
        }

        if("[J]RL 3.4 Kebidanan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 3.4 Kebidanan",rl34});
        }

        if("[J]RL 3.6 Pembedahan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 3.6 Pembedahan",rl36});
        }

        if("[J]Kunjungan Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Kunjungan Ranap",kunjungan_ranap});
        }

        if("[J]ICD 9".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]ICD 9",icd9});
        }

        if("[J]Sensus Harian Poli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Sensus Harian Poli",sensus_harian_poli});
        }

        if("[J]RL 4A Sebab Morbiditas Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 4A Sebab Morbiditas Ranap",rl4a});
        }

        if("[J]RL 4B Sebab Morbiditas Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 4B Sebab Morbiditas Ralan",rl4b});
        }

        if("[J]RL 4A Morbiditas Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 4A Morbiditas Ralan",rl4asebab});
        }

        if("[J]RL 4B Morbiditas Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]RL 4B Morbiditas Ralan",rl4bsebab});
        }

        if("[J]Lama Pelayanan Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Lama Pelayanan Ralan",lama_pelayanan_ralan});
        }

        if("[J]Harian HAIs".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Harian HAIs",harian_HAIs});
        }

        if("[J]Bulanan HAIs".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Bulanan HAIs",bulanan_HAIs});
        }

        if("[J]HAIs Per Kamar/Bangsal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]HAIs Per Kamar/Bangsal",hais_perbangsal});
        }

        if("[J]Hitung BOR".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Hitung BOR",hitung_bor});
        }

        if("[J]Lama Pelayanan Apotek".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Lama Pelayanan Apotek",lama_pelayanan_apotek});
        }

        if("[J]Hitung ALOS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Hitung ALOS",hitung_alos});
        }

        if("[J]Pny Menular Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Pny Menular Ranap",penyakit_menular_ranap});
        }

        if("[J]Pny Menular Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Pny Menular Ralan",penyakit_menular_ralan});
        }

        if("[J]Pembatalan Periksa Per Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Pembatalan Periksa Per Dokter",pembatalan_periksa_dokter});
        }

        if("[J]Cek Entry Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Cek Entry Ralan",cek_entry_ralan});
        }

        if("[J]Sensus Harian Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Sensus Harian Ralan",sensus_harian_ralan});
        }

        if("[J]Insiden Keselamatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Insiden Keselamatan",insiden_keselamatan});
        }

        if("[J]Ranap Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Ranap Per Ruang",ranap_per_ruang});
        }

        if("[J]Penyakit Ranap Per Cara Bayar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Penyakit Ranap Per Cara Bayar",penyakit_ranap_cara_bayar});
        }

        if("[J]Anggota TNI Dirawat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Anggota TNI Dirawat",anggota_militer_dirawat});
        }

        if("[J]Lama Pelayanan Radiologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Lama Pelayanan Radiologi",lama_pelayanan_radiologi});
        }

        if("[J]Lama Pelayanan Lab".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Lama Pelayanan Lab",lama_pelayanan_lab});
        }

        if("[J]Rekap Lab Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Rekap Lab Per Tahun",rekap_lab_pertahun});
        }

        if("[J]Perujuk Lab Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Perujuk Lab Per Tahun",perujuk_lab_pertahun});
        }

        if("[J]Rekap Radiologi Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Rekap Radiologi Per Tahun",rekap_radiologi_pertahun});
        }

        if("[J]Perujuk Radiologi Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Perujuk Radiologi Per Tahun",perujuk_radiologi_pertahun});
        }

        if("[J]Rekap Bulanan Porsi Diet".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Rekap Bulanan Porsi Diet",jumlah_porsi_diet});
        }

        if("[J]Rekap Bulanan Macam Diet".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Rekap Bulanan Macam Diet",jumlah_macam_diet});
        }

        if("[J]Kunjungan Lab Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Kunjungan Lab Ralan",kunjungan_permintaan_lab});
        }

        if("[J]Kunjungan Lab Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Kunjungan Lab Ranap",kunjungan_permintaan_lab2});
        }

        if("[J]Kunjungan Radiologi Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Kunjungan Radiologi Ralan",kunjungan_permintaan_radiologi});
        }

        if("[J]Kunjungan Radiologi Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Kunjungan Radiologi Ranap",kunjungan_permintaan_radiologi2});
        }

        if("[J]Data TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Data TB",kemenkes_sitt});
        }

        if("[J]Rekap Mutasi Berkas".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Rekap Mutasi Berkas",rekap_mutasi_berkas});
        }

        if("[J]Skrining Pernapasan Ralan Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Skrining Pernapasan Ralan Per Tahun",skrining_ralan_pernapasan_pertahun});
        }

        if("[J]Masuk Ruang Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Masuk Ruang Per Tahun",kunjungan_bangsal_pertahun});
        }

        if("[J]KIP Pasien Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]KIP Pasien Ranap",kip_pasien_ranap});
        }

        if("[J]KIP Pasien Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]KIP Pasien Ralan",kip_pasien_ralan});
        }

        if("[J]Rekap Permintaan Diet".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Rekap Permintaan Diet",rekap_permintaan_diet});
        }

        if("[J]Daftar Pasien Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Daftar Pasien Ranap",daftar_pasien_ranap});
        }

        if("[J]Daftar Pasien Ranap TNI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Daftar Pasien Ranap TNI",daftar_pasien_ranaptni});
        }

        if("[J]Harian HAIs 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Harian HAIs 2",harian_HAIs2});
        }

        if("[J]Rekap Kunjungan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Rekap Kunjungan",rekap_kunjungan});
        }

        if("[J]Kedatangan Pasien Per Jam".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Kedatangan Pasien Per Jam",kedatangan_pasien});
        }

        if("[J]Registrasi Poli Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Registrasi Poli Per Tanggal",registrasi_poli_per_tanggal});
        }

        if("[J]Lama Pelayanan Poli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Lama Pelayanan Poli",lama_pelayanan_poli});
        }

        if("[J]Laporan Tahunan IRJ".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Laporan Tahunan IRJ",laporan_tahunan_irj});
        }

        if("[J]Bulanan Klasifikasi Pasien Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Bulanan Klasifikasi Pasien Ranap",bulanan_klasifikasi_pasien_ranap});
        }

        if("[J]Harian Klasifikasi Pasien Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Harian Klasifikasi Pasien Ranap",harian_klasifikasi_pasien_ranap});
        }

        if("[J]Klasifikasi Pasien Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Klasifikasi Pasien Per Ruang",klasifikasi_pasien_perbangsal});
        }

        if("[J]Lama Penyiapan RM".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Lama Penyiapan RM",lama_penyiapan_rm});
        }

        if("[J]Dosis Radiologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Dosis Radiologi",dosis_radiologi});
        }

        if("[J]Demografi Umur Kunjungan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Demografi Umur Kunjungan",demografi_umur_kunjungan});
        }

        if("[J]Lama Pelayanan Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Lama Pelayanan Pasien",lama_pelayanan_pasien});
        }

        if("[J]Lama Operasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Lama Operasi",lama_operasi});
        }

        if("[J]Status Data RM".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Status Data RM",status_data_rm});
        }

        if("[J]Laporan Tahunan IGD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Laporan Tahunan IGD",laporan_tahunan_igd});
        }

        if("[J]Anggota POLRI Dirawat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Anggota POLRI Dirawat",anggota_polri_dirawat});
        }

        if("[J]Daftar Pasien Ranap POLRI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Daftar Pasien Ranap POLRI",daftar_pasien_ranap_polri});
        }

        if("[J]Laporan Penyakit POLRI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Laporan Penyakit POLRI",laporan_penyakit_polri});
        }

        if("[J]Jumlah Pengunjung Ralan POLRI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Jumlah Pengunjung Ralan POLRI",jumlah_pengunjung_ralan_polri});
        }

        if("[J]Jumlah Pengunjung Ralan TNI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Jumlah Pengunjung Ralan TNI",jumlah_pengunjung_ralan_tni});
        }

        if("[J]Laporan Penyakit TNI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Laporan Penyakit TNI",laporan_penyakit_tni});
        }

        if("[J]Laporan Bulanan IRJ".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Laporan Bulanan IRJ",laporan_bulanan_irj});
        }
        
        if("[J]Operasi Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Operasi Per Bulan",operasi_per_bulan});
        }
        
        if("[J]Sisa Diet Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Sisa Diet Pasien",sisa_diet_pasien});
        }
        
        if("[J]Laporan Tahunan Penolakan Anjuran Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Laporan Tahunan Penolakan Anjuran Medis",laporan_tahunan_penolakan_anjuran_medis});
        }
        
        if("[J]Pemeriksaan Fisik Ralan Per Penyakit".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Pemeriksaan Fisik Ralan Per Penyakit",pemeriksaan_fisik_ralan_per_penyakit});
        }
        
        if("[J]Kepatuhan Kelengkapan Keselamatan Bedah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Kepatuhan Kelengkapan Keselamatan Bedah",kepatuhan_kelengkapan_keselamatan_bedah});
        }
        
        if("[J]Data Sasaran Usia Produktif".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Data Sasaran Usia Produktif",data_sasaran_usiaproduktif});
        }
        
        if("[J]Data Sasaran Usia Lansia".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[J]Data Sasaran Usia Lansia",data_sasaran_usialansia});
        }

        if("[K]Deposit Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Deposit Pasien",deposit_pasien});
        }

        if("[K]Piutang Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Piutang Pasien",piutang_pasien});
        }

        if("[K]Kamar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Kamar",kamar});
        }

        if("[K]Tarif Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Tarif Ralan",tarif_ralan});
        }

        if("[K]Tarif Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Tarif Ranap",tarif_ranap});
        }

        if("[K]Tarif Lab".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Tarif Lab",tarif_lab});
        }

        if("[K]Tarif Radiologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Tarif Radiologi",tarif_radiologi});
        }

        if("[K]Tarif Operasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Tarif Operasi",tarif_operasi});
        }

        if("[K]Akun Rekening".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Akun Rekening",akun_rekening});
        }

        if("[K]Rekening Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Rekening Tahun",rekening_tahun});
        }

        if("[K]Posting Jurnal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Posting Jurnal",posting_jurnal});
        }

        if("[K]Buku Besar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Buku Besar",buku_besar});
        }

        if("[K]Cash Flow".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Cash Flow",cashflow});
        }

        if("[K]Keuangan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Keuangan",keuangan});
        }

        if("[K]Pengeluaran Harian".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pengeluaran Harian",pengeluaran});
        }

        if("[K]Akun Bayar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Akun Bayar",akun_bayar});
        }

        if("[K]Bayar Pesan Obat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Bayar Pesan Obat",bayar_pemesanan_obat});
        }

        if("[K]Pemasukkan Lain-Lain".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pemasukkan Lain-Lain",pemasukan_lain});
        }

        if("[K]Pengaturan Rekening".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pengaturan Rekening",pengaturan_rekening});
        }

        if("[K]Bayar Piutang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Bayar Piutang",bayar_piutang});
        }

        if("[K]Jurnal Harian".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Jurnal Harian",jurnal_harian});
        }

        if("[K]Piutang Belum Lunas".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Piutang Belum Lunas",piutang_pasien2});
        }

        if("[K]Tarif UTD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Tarif UTD",tarif_utd});
        }

        if("[K]Rincian Piutang Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Rincian Piutang Pasien",rincian_piutang_pasien});
        }

        if("[K]Hutang Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Hutang Obat & BHP",hutang_obat});
        }

        if("[K]Akun Piutang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Akun Piutang",akun_piutang});
        }

        if("[K]Piutang Per Cara Bayar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Piutang Per Cara Bayar",detail_piutang_penjab});
        }

        if("[K]Bayar Pesan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Bayar Pesan Non Medis",bayar_pesan_non_medis});
        }

        if("[K]Bayar Pesan Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Bayar Pesan Aset/Inventaris",bayar_pemesanan_iventaris});
        }

        if("[K]Hutang Barang Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Hutang Barang Non Medis",hutang_barang_non_medis});
        }

        if("[K]Saldo Akun Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Saldo Akun Per Bulan",saldo_akun_perbulan});
        }

        if("[K]Penagihan Piutang Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Penagihan Piutang Pasien",penagihan_piutang_pasien});
        }

        if("[K]Akun Penagihan Piutang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Akun Penagihan Piutang",akun_penagihan_piutang});
        }

        if("[K]Set Tarif Online".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Set Tarif Online",set_tarif_online});
        }

        if("[K]Klaim Rawat Jalan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Klaim Rawat Jalan",klaim_rawat_jalan});
        }

        if("[K]RVP Piutang BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]RVP Piutang BPJS",rvu_bpjs});
        }

        if("[K]Penerimaan/Omset/Kas Masuk".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Penerimaan/Omset/Kas Masuk",omset_penerimaan});
        }

        if("[K]Validasi Penagihan Piutang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Validasi Penagihan Piutang",validasi_penagihan_piutang});
        }

        if("[K]Pendapatan Per Cara Bayar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pendapatan Per Cara Bayar",pendapatan_per_carabayar});
        }

        if("[K]Pembayaran Bank Jateng".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pembayaran Bank Jateng",pembayaran_bank_jateng});
        }

        if("[K]Titip Faktur/Tagihan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Titip Faktur/Tagihan Obat & BHP",tagihan_hutang_obat});
        }

        if("[K]Pengembalian Deposit Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pengembalian Deposit Pasien",pengembalian_deposit_pasien});
        }

        if("[K]Validasi Titip Faktur/Tagihan Obat & BHP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Validasi Titip Faktur/Tagihan Obat & BHP",validasi_tagihan_hutang_obat});
        }

        if("[K]Piutang Obat & BHP Belum Lunas".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Piutang Obat & BHP Belum Lunas",piutang_obat_belum_lunas});
        }

        if("[K]Akun Jenis Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Akun Jenis Aset/Inventaris",akun_aset_inventaris});
        }

        if("[K]Hutang Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Hutang Aset/Inventaris",hutang_aset_inventaris});
        }

        if("[K]Titip Faktur/Tagihan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Titip Faktur/Tagihan Non Medis",titip_faktur_non_medis});
        }

        if("[K]Validasi Titip Faktur/Tagihan Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Validasi Titip Faktur/Tagihan Non Medis",validasi_tagihan_non_medis});
        }

        if("[K]Titip Faktur/Tagihan Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Titip Faktur/Tagihan Aset/Inventaris",titip_faktur_aset});
        }

        if("[K]Validasi Titip Faktur/Tagihan Aset/Inventaris".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Validasi Titip Faktur/Tagihan Aset/Inventaris",validasi_tagihan_aset});
        }

        if("[K]Peminjam Piutang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Peminjam Piutang",peminjam_piutang});
        }

        if("[K]Piutang Lain-lain".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Piutang Lain-lain",piutang_lainlain});
        }

        if("[K]Bayar Piutang Lain-lain".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Bayar Piutang Lain-lain",bayar_piutang_lain});
        }

        if("[K]Asuransi/Askes/Jenis Bayar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Asuransi/Askes/Jenis Bayar",cara_bayar});
        }

        if("[K]Kategori Pengeluaran Harian".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Kategori Pengeluaran Harian",kategori_pengeluaran_harian});
        }

        if("[K]Kategori Pemasukan Lain-lain".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Kategori Pemasukan Lain-lain",kategori_pemasukan_lain});
        }

        if("[K]Bayar JM Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Bayar JM Dokter",bayar_jm_dokter});
        }

        if("[K]Pembayaran BRIVA".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pembayaran BRIVA",pembayaran_briva});
        }

        if("[K]Akun Bayar Hutang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Akun Bayar Hutang",akun_bayar_hutang});
        }

        if("[K]Ringkasan Hutang Vendor Farmasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Ringkasan Hutang Vendor Farmasi",ringkasan_hutang_vendor_farmasi});
        }

        if("[K]Ringkasan Hutang Vendor Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Ringkasan Hutang Vendor Non Medis",ringkasan_hutang_vendor_nonmedis});
        }

        if("[K]Pembayaran Bank Papua".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pembayaran Bank Papua",pembayaran_bank_papua});
        }
        
        if("[K]Pembayaran Bank Jabar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pembayaran Bank Jabar",pembayaran_bank_jabar});
        }
        
        if("[K]Pengajuan Biaya".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pengajuan Biaya",pengajuan_biaya});
        }
        
        if("[K]Persetujuan Pengajuan Biaya".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Persetujuan Pengajuan Biaya",persetujuan_pengajuan_biaya});
        }
        
        if("[K]Validasi Persetujuan Pengajuan Biaya".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Validasi Persetujuan Pengajuan Biaya",validasi_persetujuan_pengajuan_biaya});
        }
        
        if("[K]Rekap Pengajuan Biaya".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Rekap Pengajuan Biaya",rekap_pengajuan_biaya});
        }
        
        if("[K]Pembayaran Bank Mandiri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pembayaran Bank Mandiri",pembayaran_bank_mandiri});
        }
        
        if("[K]Nilai Piutang Per Cara Bayar Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Nilai Piutang Per Cara Bayar Per Bulan",nilai_piutang_perjenis_bayar_per_bulan});
        }
        
        if("[K]Ringkasan Piutang Per Cara Bayar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Ringkasan Piutang Per Cara Bayar",ringkasan_piutang_jenis_bayar});
        }
        
        if("[K]Pembayaran Pihak Ke 3 Bank Mandiri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Pembayaran Pihak Ke 3 Bank Mandiri",pembayaran_pihak_ke3_bankmandiri});
        }
        
        if("[K]Bayar Pesan Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Bayar Pesan Dapur",bayar_pesan_dapur});
        }
        
        if("[K]Hutang Barang Dapur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[K]Hutang Barang Dapur",hutang_dapur});
        }

        if("[L]Cek NIK".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek NIK",bpjs_cek_nik});
        }

        if("[L]Cek No.Kartu".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek No.Kartu",bpjs_cek_kartu});
        }

        if("[L]Riwayat Rujukan PCare di VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Riwayat Rujukan PCare di VClaim",bpjs_cek_riwayat});
        }

        if("[L]Cek No.Rujukan PCare di VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek No.Rujukan PCare di VClaim",bpjs_cek_nomor_rujukan});
        }

        if("[L]Referensi Diagnosa VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Diagnosa VClaim",bpjs_referensi_diagnosa});
        }

        if("[L]Referensi Poli VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Poli VClaim",bpjs_referensi_poli});
        }

        if("[L]Referensi Faskes VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Faskes VClaim",bpjs_referensi_faskes});
        }

        if("[L]Data Bridging SEP VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Data Bridging SEP VClaim",bpjs_sep});
        }

        if("[L]Monitoring Verifikasi Klaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Monitoring Verifikasi Klaim",bpjs_monitoring_klaim});
        }

        if("[L]Reklasifikasi Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Reklasifikasi Ralan",reklasifikasi_ralan});
        }

        if("[L]Reklasifikasi Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Reklasifikasi Ranap",reklasifikasi_ranap});
        }

        if("[L]Referensi Kamar Aplicare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Kamar Aplicare",aplicare_referensi_kamar});
        }

        if("[L]Ketersediaan Kamar Aplicare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Ketersediaan Kamar Aplicare",aplicare_ketersediaan_kamar});
        }

        if("[L]Klaim Baru Otomatis INACBG".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Klaim Baru Otomatis INACBG",inacbg_klaim_baru_otomatis});
        }

        if("[L]Klaim Baru Manual INACBG".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Klaim Baru Manual INACBG",inacbg_klaim_baru_manual});
        }

        if("[L]Coder NIK INACBG".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Coder NIK INACBG",inacbg_coder_nik});
        }

        if("[L]Cek Eligibilitas Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek Eligibilitas Inhealth",inhealth_cek_eligibilitas});
        }

        if("[L]Referensi Ruang Rawat Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Ruang Rawat Inhealth",inhealth_referensi_jenpel_ruang_rawat});
        }

        if("[L]Referensi Poli Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Poli Inhealth",inhealth_referensi_poli});
        }

        if("[L]Referensi Faskes Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Faskes Inhealth",inhealth_referensi_faskes});
        }

        if("[L]Data Bridging SJP Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Data Bridging SJP Inhealth",inhealth_sjp});
        }

        if("[L]Referensi Diagnosa Pcare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Diagnosa Pcare",pcare_cek_penyakit});
        }

        if("[L]Referensi Kesadaran Pcare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Kesadaran Pcare",pcare_cek_kesadaran});
        }

        if("[L]Cek Rujukan PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek Rujukan PCare",pcare_cek_rujukan});
        }

        if("[L]Klaim Baru Manual INACBG 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Klaim Baru Manual INACBG 2",inacbg_klaim_baru_manual2});
        }

        if("[L]Referensi Prosedur VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Prosedur VClaim",bpjs_cek_prosedur});
        }

        if("[L]Referensi Kelas Rawat VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Kelas Rawat VClaim",bpjs_cek_kelas_rawat});
        }

        if("[L]Referensi Dokter VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Dokter VClaim",bpjs_cek_dokter});
        }

        if("[L]Referensi Spesialistik VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Spesialistik VClaim",bpjs_cek_spesialistik});
        }

        if("[L]Referensi Ruang Rawat VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Ruang Rawat VClaim",bpjs_cek_ruangrawat});
        }

        if("[L]Referensi Cara Keluar VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Cara Keluar VClaim",bpjs_cek_carakeluar});
        }

        if("[L]Referensi Pasca Pulang VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Pasca Pulang VClaim",bpjs_cek_pasca_pulang});
        }

        if("[L]Cek No.Rujukan RS di VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek No.Rujukan RS di VClaim",bpjs_cek_nomor_rujukan_rs});
        }

        if("[L]Cek Rujukan Kartu PCare di VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek Rujukan Kartu PCare di VClaim",bpjs_cek_rujukan_kartu_pcare});
        }

        if("[L]Cek Rujukan Kartu RS di VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek Rujukan Kartu RS di VClaim",bpjs_cek_rujukan_kartu_rs});
        }

        if("[L]Pembuatan Rujukan VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Pembuatan Rujukan VClaim",bpjs_rujukan_keluar});
        }

        if("[L]Mapping Poli VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Poli VClaim",mapping_poli_bpjs});
        }

        if("[L]Referensi Propinsi VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Propinsi VClaim",bpjs_cek_propinsi});
        }

        if("[L]Referensi Kabupaten VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Kabupaten VClaim",bpjs_cek_kabupaten});
        }

        if("[L]Referensi Kecamatan VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Kecamatan VClaim",bpjs_cek_kecamatan});
        }

        if("[L]Referensi Dokter DPJP VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Dokter DPJP VClaim",bpjs_cek_dokterdpjp});
        }

        if("[L]Riwayat Rujukan RS di VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Riwayat Rujukan RS di VClaim",bpjs_cek_riwayat_rujukanrs});
        }

        if("[L]Tanggal Rujukan di VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Tanggal Rujukan di VClaim",bpjs_cek_tanggal_rujukan});
        }

        if("[L]Histori Pelayanan BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Histori Pelayanan BPJS",bpjs_histori_pelayanan});
        }

        if("[L]Referensi Dokter PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Dokter PCare",pcare_cek_dokter});
        }

        if("[L]Referensi Poli PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Poli PCare",pcare_cek_poli});
        }

        if("[L]Referensi Provider PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Provider PCare",pcare_cek_provider});
        }

        if("[L]Referensi Stts Pulang PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Stts Pulang PCare",pcare_cek_statuspulang});
        }

        if("[L]Referensi Spesialis PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Spesialis PCare",pcare_cek_spesialis});
        }

        if("[L]Referensi Subspesialis PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Subspesialis PCare",pcare_cek_subspesialis});
        }

        if("[L]Referensi Sarana PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Sarana PCare",pcare_cek_sarana});
        }

        if("[L]Referensi Khusus PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Khusus PCare",pcare_cek_khusus});
        }

        if("[L]Referensi Obat PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Obat PCare",pcare_cek_obat});
        }

        if("[L]Referensi Tindakan PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Tindakan PCare",pcare_cek_tindakan});
        }

        if("[L]Faskes Subspesialis PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Faskes Subspesialis PCare",pcare_cek_faskessubspesialis});
        }

        if("[L]Faskes Alih Rawat PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Faskes Alih Rawat PCare",pcare_cek_faskesalihrawat});
        }

        if("[L]Faskes Thalasemia & Hemofili PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Faskes Thalasemia & Hemofili PCare",pcare_cek_faskesthalasemia});
        }

        if("[L]Mapping Obat PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Obat PCare",pcare_mapping_obat});
        }

        if("[L]Tarif Ralan RS & PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Tarif Ralan RS & PCare",pcare_mapping_tindakan});
        }

        if("[L]Club Prolanis PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Club Prolanis PCare",pcare_club_prolanis});
        }

        if("[L]Mapping Poli PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Poli PCare",pcare_mapping_poli});
        }

        if("[L]Kegiatan Kelompok PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kegiatan Kelompok PCare",pcare_kegiatan_kelompok});
        }

        if("[L]Tarif Ranap RS & PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Tarif Ranap RS & PCare",pcare_mapping_tindakan_ranap});
        }

        if("[L]Peserta Keg Kelompok PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Peserta Keg Kelompok PCare",pcare_peserta_kegiatan_kelompok});
        }

        if("[L]Data Pendafataran PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Data Pendafataran PCare",bridging_pcare_daftar});
        }

        if("[L]Mapping Dokter PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Dokter PCare",pcare_mapping_dokter});
        }

        if("[L]Cek Nomor SEP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek Nomor SEP",bpjs_cek_sep});
        }

        if("[L]Referensi Faskes Sisrute".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Faskes Sisrute",sisrute_referensi_faskes});
        }

        if("[L]Referensi Alasan Rujuk Sisrute".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Alasan Rujuk Sisrute",sisrute_referensi_alasanrujuk});
        }

        if("[L]Referensi Diagnosa Sisrute".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Diagnosa Sisrute",sisrute_referensi_diagnosa});
        }

        if("[L]Rujukan Masuk Sisrute".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Rujukan Masuk Sisrute",sisrute_rujukan_masuk});
        }

        if("[L]Rujukan Keluar Sisrute".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Rujukan Keluar Sisrute",sisrute_rujukan_keluar});
        }

        if("[L]Cek SKDP VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek SKDP VClaim",bpjs_cek_skdp});
        }

        if("[L]Pemberian Obat PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Pemberian Obat PCare",pcare_pemberian_obat});
        }

        if("[L]Pemberian Tindakan PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Pemberian Tindakan PCare",pcare_pemberian_tindakan});
        }

        if("[L]Ketersediaan Kamar SIRANAP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Ketersediaan Kamar SIRANAP",siranap_ketersediaan_kamar});
        }

        if("[L]Mapping Dokter DPJP VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Dokter DPJP VClaim",bpjs_mapping_dokterdpjp});
        }

        if("[L]Mapping Poli Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Poli Inhealth",inhealth_mapping_poli});
        }

        if("[L]Mapping Dokter Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Dokter Inhealth",inhealth_mapping_dokter});
        }

        if("[L]Tarif Ralan Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Tarif Ralan Inhealth",inhealth_mapping_tindakan_ralan});
        }

        if("[L]Tarif Ranap Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Tarif Ranap Inhealth",inhealth_mapping_tindakan_ranap});
        }

        if("[L]Tarif Radiologi Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Tarif Radiologi Inhealth",inhealth_mapping_tindakan_radiologi});
        }

        if("[L]Tarif Laborat Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Tarif Laborat Inhealth",inhealth_mapping_tindakan_laborat});
        }

        if("[L]Tarif Operasi Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Tarif Operasi Inhealth",inhealth_mapping_tindakan_operasi});
        }

        if("[L]Tagihan Inhealth".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Tagihan Inhealth",inhealth_kirim_tagihan});
        }

        if("[L]Pasien Corona".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Pasien Corona",pasien_corona});
        }

        if("[L]Diagnosa Pasien Corona".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Diagnosa Pasien Corona",diagnosa_pasien_corona});
        }

        if("[L]Perawatan Pasien Corona".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Perawatan Pasien Corona",perawatan_pasien_corona});
        }

        if("[L]Cek No.Kartu PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Cek No.Kartu PCare",pcare_cek_kartu});
        }

        if("[L]Referensi Diagnosa PRB VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Diagnosa PRB VClaim",bpjs_diagnosa_prb});
        }

        if("[L]Referensi Obat PRB VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Obat PRB VClaim",bpjs_obat_prb});
        }

        if("[L]Surat Kontrol VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Surat Kontrol VClaim",bpjs_surat_kontrol});
        }

        if("[L]Surat PRI VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Surat PRI VClaim",bpjs_surat_pri});
        }

        if("[L]Referensi Pendaftaran Mobile JKN".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Pendaftaran Mobile JKN",referensi_mobilejkn_bpjs});
        }

        if("[L]Batal Pendaftaran Mobile JKN".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Batal Pendaftaran Mobile JKN",batal_pendaftaran_mobilejkn_bpjs});
        }

        if("[L]Host To Host Bank Jateng".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Host To Host Bank Jateng",akun_host_to_host_bank_jateng});
        }

        if("[L]Integrasi BRI API".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Integrasi BRI API",integrasi_briapi});
        }

        if("[L]Referensi TACC PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi TACC PCare",pcare_alasan_tacc});
        }

        if("[L]Referensi Poli HFIS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Poli HFIS",bpjs_referensi_poli_hfis});
        }

        if("[L]Referensi Dokter HFIS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Dokter HFIS",bpjs_referensi_dokter_hfis});
        }

        if("[L]Referensi Jadwal HFIS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Jadwal HFIS",bpjs_referensi_jadwal_hfis});
        }

        if("[L]Program PRB di VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Program PRB di VClaim",bpjs_program_prb});
        }

        if("[L]Suplesi Jasa Raharja di VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Suplesi Jasa Raharja di VClaim",bpjs_suplesi_jasaraharja});
        }

        if("[L]Data Induk Kecelakaan VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Data Induk Kecelakaan VClaim",bpjs_data_induk_kecelakaan});
        }

        if("[L]Data SEP Internal VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Data SEP Internal VClaim",bpjs_sep_internal});
        }

        if("[L]Klaim Jaminan Jasa Raharja VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Klaim Jaminan Jasa Raharja VClaim",bpjs_klaim_jasa_raharja});
        }

        if("[L]Pasien Finger Print VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Pasien Finger Print VClaim",bpjs_daftar_finger_print});
        }

        if("[L]Rujukan Khusus VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Rujukan Khusus VClaim",bpjs_rujukan_khusus});
        }

        if("[L]Task ID Mobile JKN".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Task ID Mobile JKN",bpjs_task_id});
        }

        if("[L]Referensi DPHO Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi DPHO Apotek BPJS",bpjs_referensi_dpho_apotek});
        }

        if("[L]Referensi Poli Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Poli Apotek BPJS",bpjs_referensi_poli_apotek});
        }

        if("[L]Referensi Faskes Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Faskes Apotek BPJS",bpjs_referensi_faskes_apotek});
        }

        if("[L]Referensi Spesialistik Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Spesialistik Apotek BPJS",bpjs_referensi_spesialistik_apotek});
        }

        if("[L]Host To Host Bank Papua".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Host To Host Bank Papua",akun_host_to_host_bank_papua});
        }
        
        if("[L]Host To Host Bank Jabar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Host To Host Bank Jabar",akun_host_to_host_bank_jabar});
        }
        
        if("[L]Referensi Praktisi Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Praktisi Satu Sehat",satu_sehat_referensi_dokter});
        }
        
        if("[L]Referensi Pasien Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Pasien Satu Sehat",satu_sehat_referensi_pasien});
        }
        
        if("[L]Mapping Organisasi Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Organisasi Satu Sehat",satu_sehat_mapping_departemen});
        }
        
        if("[L]Mapping Lokasi Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Lokasi Satu Sehat",satu_sehat_mapping_lokasi});
        }
        
        if("[L]Mapping Vaksin Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Vaksin Satu Sehat",satu_sehat_mapping_vaksin});
        }
        
        if("[L]Kirim Encounter Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Encounter Satu Sehat",satu_sehat_kirim_encounter});
        }
        
        if("[L]Kirim Condition Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Condition Satu Sehat",satu_sehat_kirim_condition});
        }
        
        if("[L]Kirim Observation-TTV Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Observation-TTV Satu Sehat",satu_sehat_kirim_observationttv});
        }
        
        if("[L]Kirim Procedure Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Procedure Satu Sehat",satu_sehat_kirim_procedure});
        }
        
        if("[L]Kirim Vaksin Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Vaksin Satu Sehat",satu_sehat_kirim_Immunization});
        }
        
        if("[L]Kirim Clinical Impression Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Clinical Impression Satu Sehat",satu_sehat_kirim_clinicalimpression});
        }
        
        if("[L]Antrean Per Tanggal Mobile JKN".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Antrean Per Tanggal Mobile JKN",bpjs_antrean_pertanggal});
        }
        
        if("[L]Riwayat Perawatan ICare BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Riwayat Perawatan ICare BPJS",riwayat_perawatan_icare_bpjs});
        }
        
        if("[L]Host To Host Bank Mandiri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Host To Host Bank Mandiri",akun_host_to_host_bank_mandiri});
        }
        
        if("[L]Referensi Setting PPK Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Setting PPK Apotek BPJS",bpjs_referensi_setting_apotek});
        }
        
        if("[L]Referensi Obat Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Obat Apotek BPJS",bpjs_referensi_obat_apotek});
        }
        
        if("[L]Mapping Obat Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Obat Apotek BPJS",bpjs_mapping_obat_apotek});
        }
        
        if("[L]Obat 23 Hari Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Obat 23 Hari Apotek BPJS",bpjs_obat_23hari_apotek});
        }
        
        if("[L]Pencarian SEP Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Pencarian SEP Apotek BPJS",bpjs_kunjungan_sep_apotek});
        }
        
        if("[L]Monitoring Klaim Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Monitoring Klaim Apotek BPJS",bpjs_monitoring_klaim_apotek});
        }
        
        if("[L]Daftar Pelayanan Obat Apotek BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Daftar Pelayanan Obat Apotek BPJS",bpjs_daftar_pelayanan_obat_apotek});
        }
        
        if("[L]Hapus/Edit SEP VClaim".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Hapus/Edit SEP VClaim",hapus_edit_sep_bpjs});
        }
        
        if("[L]Kirim Diet Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Diet Satu Sehat",satu_sehat_kirim_diet});
        }
        
        if("[L]Mapping Obat/Alkes/BHP Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Obat/Alkes/BHP Satu Sehat",satu_sehat_mapping_obat});
        }
        
        if("[L]Kirim Medication Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Medication Satu Sehat",satu_sehat_kirim_medication});
        }
        
        if("[L]Kirim Medication Request Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Medication Request Satu Sehat",satu_sehat_kirim_medicationrequest});
        }
        
        if("[L]Kirim Medication Dispense Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Medication Dispense Satu Sehat",satu_sehat_kirim_medicationdispense});
        }
        
        if("[L]Mapping Tindakan Radiologi Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Tindakan Radiologi Satu Sehat",satu_sehat_mapping_radiologi});
        }
        
        if("[L]Kirim Service Request Radiologi Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Service Request Radiologi Satu Sehat",satu_sehat_kirim_servicerequest_radiologi});
        }
        
        if("[L]Kirim Specimen Radiologi Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Specimen Radiologi Satu Sehat",satu_sehat_kirim_specimen_radiologi});
        }
        
        if("[L]Kirim Observation Radiologi Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Observation Radiologi Satu Sehat",satu_sehat_kirim_observation_radiologi});
        }
        
        if("[L]Kirim Diagnostic Report Radiologi Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Diagnostic Report Radiologi Satu Sehat",satu_sehat_kirim_diagnosticreport_radiologi});
        }
        
        if("[L]Mapping Tindakan Lab PK & MB Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Mapping Tindakan Lab PK & MB Satu Sehat",satu_sehat_mapping_lab});
        }
        
        if("[L]Kirim Service Request Lab PK Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Service Request Lab PK Satu Sehat",satu_sehat_kirim_servicerequest_lab});
        }
        
        if("[L]Kirim Service Request Lab MB Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Service Request Lab MB Satu Sehat",satu_sehat_kirim_servicerequest_labmb});
        }
        
        if("[L]Kirim Specimen Lab PK Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Specimen Lab PK Satu Sehat",satu_sehat_kirim_specimen_lab});
        }
        
        if("[L]Kirim Specimen Lab MB Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Specimen Lab MB Satu Sehat",satu_sehat_kirim_specimen_labmb});
        }
        
        if("[L]Kirim Observation Lab PK Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Observation Lab PK Satu Sehat",satu_sehat_kirim_observation_lab});
        }
        
        if("[L]Kirim Observation Lab MB Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Observation Lab MB Satu Sehat",satu_sehat_kirim_observation_labmb});
        }
        
        if("[L]Kirim Diagnostic Report Lab PK Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Diagnostic Report Lab PK Satu Sehat",satu_sehat_kirim_diagnosticreport_lab});
        }
        
        if("[L]Kirim Diagnostic Report Lab MB Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Diagnostic Report Lab MB Satu Sehat",satu_sehat_kirim_diagnosticreport_labmb});
        }
        
        if("[L]Referensi Poli Mobile JKN FKTP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Poli Mobile JKN FKTP",referensi_poli_mobilejknfktp});
        }
        
        if("[L]Referensi Dokter Mobile JKN FKTP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Dokter Mobile JKN FKTP",referensi_dokter_mobilejknfktp});
        }
        
        if("[L]Metode Pembayaran Bank Mandiri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Metode Pembayaran Bank Mandiri",metode_pembayaran_bankmandiri});
        }
        
        if("[L]Bank Tujuan Transfer Bank Mandiri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Bank Tujuan Transfer Bank Mandiri",bank_tujuan_transfer_bankmandiri});
        }
        
        if("[L]Kode Transaksi Tujuan Transfer Bank Mandiri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kode Transaksi Tujuan Transfer Bank Mandiri",kodetransaksi_tujuan_transfer_bankmandiri});
        }
        
        if("[L]Referensi Alergi PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Alergi PCare",pcare_cek_alergi});
        }
        
        if("[L]Referensi Prognosa PCare".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Referensi Prognosa PCare",pcare_cek_prognosa});
        }
        
        if("[L]Kirim Care Plan Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Care Plan Satu Sehat",satu_sehat_kirim_careplan});
        }
        
        if("[L]Kirim Medication Statement Satu Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[L]Kirim Medication Statement Satu Sehat",satu_sehat_kirim_medicationstatement});
        }

        if("[M]Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pasien",pasien});
        }

        if("[M]Pasien Meninggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pasien Meninggal",pasien_meninggal});
        }

        if("[M]Kelahiran Bayi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Kelahiran Bayi",kelahiran_bayi});
        }

        if("[M]Peminjaman Berkas RM".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Peminjaman Berkas RM",peminjaman_berkas});
        }

        if("[M]Riwayat Perawatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Riwayat Perawatan",resume_pasien});
        }

        if("[M]Diagnosa Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Diagnosa Pasien",diagnosa_pasien});
        }

        if("[M]Retensi Data R.M.".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Retensi Data R.M.",retensi_rm});
        }

        if("[M]Mutasi Berkas RM".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Mutasi Berkas RM",mutasi_berkas});
        }

        if("[M]Catatan Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Pasien",catatan_pasien});
        }

        if("[M]Data HAIs".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Data HAIs",data_HAIs});
        }

        if("[M]Klasifikasi Pasien Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Klasifikasi Pasien Ranap",klasifikasi_pasien_ranap});
        }

        if("[M]Instansi/Perusahaan Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Instansi/Perusahaan Pasien",perusahaan_pasien});
        }

        if("[M]Berkas Digital Perawatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Berkas Digital Perawatan",berkas_digital_perawatan});
        }

        if("[M]Pengaduan/Chat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pengaduan/Chat",pengaduan_pasien});
        }

        if("[M]Insiden Keselamatan Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Insiden Keselamatan Pasien",insiden_keselamatan_pasien});
        }

        if("[M]Suku/Bangsa Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Suku/Bangsa Pasien",suku_bangsa});
        }

        if("[M]Bahasa Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Bahasa Pasien",bahasa_pasien});
        }

        if("[M]Golongan TNI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Golongan TNI",golongan_tni});
        }

        if("[M]Satuan TNI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Satuan TNI",satuan_tni});
        }

        if("[M]Jabatan TNI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Jabatan TNI",jabatan_tni});
        }

        if("[M]Pangkat TNI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pangkat TNI",pangkat_tni});
        }

        if("[M]Golongan POLRI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Golongan POLRI",golongan_polri});
        }

        if("[M]Satuan POLRI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Satuan POLRI",satuan_polri});
        }

        if("[M]Jabatan POLRI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Jabatan POLRI",jabatan_polri});
        }

        if("[M]Pangkat POLRI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pangkat POLRI",pangkat_polri});
        }

        if("[M]Cacat Fisik".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Cacat Fisik",cacat_fisik});
        }

        if("[M]Data Triase".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Data Triase",data_triase_igd});
        }

        if("[M]Master Triase Skala 1".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Triase Skala 1",master_triase_skala1});
        }

        if("[M]Master Triase Skala 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Triase Skala 2",master_triase_skala2});
        }

        if("[M]Master Triase Skala 3".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Triase Skala 3",master_triase_skala3});
        }

        if("[M]Master Triase Skala 4".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Triase Skala 4",master_triase_skala4});
        }

        if("[M]Master Triase Skala 5".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Triase Skala 5",master_triase_skala5});
        }

        if("[M]Master Triase Pemeriksaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Triase Pemeriksaan",master_triase_pemeriksaan});
        }

        if("[M]Master Triase Macam Kasus".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Triase Macam Kasus",master_triase_macamkasus});
        }

        if("[M]Resume Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Resume Pasien",data_resume_pasien});
        }

        if("[M]Asuhan Gizi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Asuhan Gizi",asuhan_gizi});
        }

        if("[M]Monitoring Asuhan Gizi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Monitoring Asuhan Gizi",monitoring_asuhan_gizi});
        }

        if("[M]Penilaian Awal Keperawatan Ralan Umum".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ralan Umum",penilaian_awal_keperawatan_ralan});
        }

        if("[M]Master Masalah Keperawatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Masalah Keperawatan",master_masalah_keperawatan});
        }

        if("[M]Penilaian Awal Keperawatan Gigi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Gigi",penilaian_awal_keperawatan_gigi});
        }

        if("[M]Master Masalah Keperawatan Gigi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Masalah Keperawatan Gigi",master_masalah_keperawatan_gigi});
        }

        if("[M]Penilaian Awal Keperawatan Ralan Kebidanan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ralan Kebidanan",penilaian_awal_keperawatan_kebidanan});
        }

        if("[M]Penilaian Awal Keperawatan Ralan Bayi/Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ralan Bayi/Anak",penilaian_awal_keperawatan_anak});
        }

        if("[M]Master Masalah Keperawatan Bayi/Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Masalah Keperawatan Bayi/Anak",master_masalah_keperawatan_anak});
        }

        if("[M]Master Imunisasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Imunisasi",master_imunisasi});
        }

        if("[M]Hemodialisa".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hemodialisa",hemodialisa});
        }

        if("[M]SOAP Perawatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]SOAP Perawatan",soap_perawatan});
        }

        if("[M]Skrining Gizi Lanjut".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Gizi Lanjut",skrining_gizi});
        }

        if("[M]Penilaian Awal Keperawatan Ranap Kebidanan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ranap Kebidanan",penilaian_awal_keperawatan_ranapkebidanan});
        }

        if("[M]Penilaian Awal Medis Ralan Umum".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Umum",penilaian_awal_medis_ralan});
        }

        if("[M]Master Masalah Keperawatan Mata".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Masalah Keperawatan Mata",master_masalah_keperawatan_mata});
        }

        if("[M]Penilaian Awal Keperawatan Ralan Mata".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ralan Mata",penilaian_awal_keperawatan_mata});
        }

        if("[M]Penilaian Awal Medis Ranap Umum".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ranap Umum",penilaian_awal_medis_ranap});
        }

        if("[M]Penilaian Awal Medis Ranap Kandungan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ranap Kandungan",penilaian_awal_medis_ranap_kebidanan});
        }

        if("[M]Penilaian Awal Medis Ralan Kandungan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Kandungan",penilaian_awal_medis_ralan_kebidanan});
        }

        if("[M]Penilaian Awal Medis IGD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis IGD",penilaian_awal_medis_igd});
        }

        if("[M]Penilaian Awal Medis Ralan Bayi/Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Bayi/Anak",penilaian_awal_medis_ralan_anak});
        }

        if("[M]Penilaian Awal Fisioterapi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Fisioterapi",penilaian_fisioterapi});
        }

        if("[M]Penilaian MCU".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian MCU",penilaian_mcu});
        }

        if("[M]Riwayat Kamar Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Riwayat Kamar Pasien",riwayat_kamar_pasien});
        }

        if("[M]Uji Fungsi/Prosedur KFR".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Uji Fungsi/Prosedur KFR",uji_fungsi_kfr});
        }

        if("[M]Hapus Berkas Digital Perawatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hapus Berkas Digital Perawatan",hapus_berkas_digital_perawatan});
        }

        if("[M]Gabungkan Data RM".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Gabungkan Data RM",gabung_rm});
        }

        if("[M]Master Masalah Keperawatan IGD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Masalah Keperawatan IGD",master_masalah_keperawatan_igd});
        }

        if("[M]Penilaian Awal Keperawatan IGD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan IGD",penilaian_awal_keperawatan_igd});
        }

        if("[M]Penilaian Awal Keperawatan Ranap Umum".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ranap Umum",penilaian_awal_keperawatan_ranap});
        }

        if("[M]Master Rencana Keperawatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Rencana Keperawatan",master_rencana_keperawatan});
        }

        if("[M]Master Rencana Keperawatan Bayi/Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Rencana Keperawatan Bayi/Anak",master_rencana_keperawatan_anak});
        }

        if("[M]SOAP Ralan Anggota POLRI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]SOAP Ralan Anggota POLRI",soap_ralan_polri});
        }

        if("[M]SOAP Ranap Anggota POLRI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]SOAP Ranap Anggota POLRI",soap_ranap_polri});
        }

        if("[M]Catatan Observasi IGD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Observasi IGD",catatan_observasi_igd});
        }

        if("[M]Catatan Observasi Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Observasi Ranap",catatan_observasi_ranap});
        }

        if("[M]Catatan Observasi Ranap Kebidanan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Observasi Ranap Kebidanan",catatan_observasi_ranap_kebidanan});
        }

        if("[M]Catatan Observasi Ranap Post Partum".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Observasi Ranap Post Partum",catatan_observasi_ranap_postpartum});
        }

        if("[M]Penilaian Awal Medis Ralan THT".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan THT",penilaian_awal_medis_ralan_tht});
        }

        if("[M]Penilaian Psikologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Psikologi",penilaian_psikologi});
        }

        if("[M]Penilaian Awal Medis Ralan Psikiatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Psikiatri",penilaian_awal_medis_ralan_psikiatri});
        }

        if("[M]Penilaian Awal Medis Ralan Penyakit Dalam".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Penyakit Dalam",penilaian_awal_medis_ralan_penyakit_dalam});
        }

        if("[M]Penilaian Awal Medis Ralan Mata".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Mata",penilaian_awal_medis_ralan_mata});
        }

        if("[M]Penilaian Awal Medis Ralan Neurologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Neurologi",penilaian_awal_medis_ralan_neurologi});
        }

        if("[M]Penilaian Awal Medis Ralan Orthopedi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Orthopedi",penilaian_awal_medis_ralan_orthopedi});
        }

        if("[M]Penilaian Awal Medis Ralan Bedah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Bedah",penilaian_awal_medis_ralan_bedah});
        }

        if("[M]SOAP Ralan Anggota TNI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]SOAP Ralan Anggota TNI",soap_ralan_tni});
        }

        if("[M]SOAP Ranap Anggota TNI".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]SOAP Ranap Anggota TNI",soap_ranap_tni});
        }

        if("[M]Catatan Keperawatan Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Keperawatan Ranap",catatan_keperawatan_ranap});
        }

        if("[M]Master Rencana Keperawatan Gigi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Rencana Keperawatan Gigi",master_rencana_keperawatan_gigi});
        }

        if("[M]Master Rencana Keperawatan Mata".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Rencana Keperawatan Mata",master_rencana_keperawatan_mata});
        }

        if("[M]Master Rencana Keperawatan IGD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Rencana Keperawatan IGD",master_rencana_keperawatan_igd});
        }

        if("[M]Master Masalah Keperawatan Psikiatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Masalah Keperawatan Psikiatri",master_masalah_keperawatan_psikiatri});
        }

        if("[M]Master Rencana Keperawatan Psikiatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Rencana Keperawatan Psikiatri",master_rencana_keperawatan_psikiatri});
        }

        if("[M]Penilaian Awal Keperawatan Ralan Psikiatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ralan Psikiatri",penilaian_awal_keperawatan_psikiatri});
        }

        if("[M]Pemantauan PEWS Pasien Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pemantauan PEWS Pasien Anak",pemantauan_pews_anak});
        }

        if("[M]Master Template Hasil Radiologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Template Hasil Radiologi",template_hasil_radiologi});
        }

        if("[M]Master Template Pemeriksaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Template Pemeriksaan",template_pemeriksaan});
        }

        if("[M]Penilaian Pre Operasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Pre Operasi",penilaian_pre_operasi});
        }

        if("[M]Penilaian Pre Anestesi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Pre Anestesi",penilaian_pre_anestesi});
        }

        if("[M]Perencanaan Pemulangan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Perencanaan Pemulangan",perencanaan_pemulangan});
        }

        if("[M]Penilaian Lanjutan Resiko Jatuh Dewasa".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Lanjutan Resiko Jatuh Dewasa",penilaian_lanjutan_resiko_jatuh_dewasa});
        }

        if("[M]Penilaian Lanjutan Risiko Jatuh Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Lanjutan Risiko Jatuh Anak",penilaian_lanjutan_resiko_jatuh_anak});
        }
        
        if("[M]Penilaian Awal Medis Ralan Geriatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Geriatri",penilaian_awal_medis_ralan_geriatri});
        }
        
        if("[M]Penilaian Tambahan Pasien Geriatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Tambahan Pasien Geriatri",penilaian_tambahan_pasien_geriatri});
        }
        
        if("[M]Skrining Nutrisi Pasien Dewasa".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Nutrisi Pasien Dewasa",skrining_nutrisi_dewasa});
        }
        
        if("[M]Skrining Nutrisi Pasien Lansia".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Nutrisi Pasien Lansia",skrining_nutrisi_lansia});
        }
        
        if("[M]Hasil USG Kandungan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hasil USG Kandungan",hasil_pemeriksaan_usg});
        }
        
        if("[M]Skrining Nutrisi Pasien Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Nutrisi Pasien Anak",skrining_nutrisi_anak});
        }
        
        if("[M]Konseling Farmasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Konseling Farmasi",konseling_farmasi});
        }
        
        if("[M]Pelayanan Informasi Obat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pelayanan Informasi Obat",pelayanan_informasi_obat});
        }
        
        if("[M]Jawaban PIO Apoteker".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Jawaban PIO Apoteker",jawaban_pio_apoteker});
        }
        
        if("[M]Transfer Pasien Antar Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Transfer Pasien Antar Ruang",transfer_pasien_antar_ruang});
        }
        
        if("[M]Catatan Cek GDS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Cek GDS",catatan_cek_gds});
        }
        
        if("[M]Check List Pre Operasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Check List Pre Operasi",checklist_pre_operasi});
        }
        
        if("[M]Sign-In Sebelum Anestesi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Sign-In Sebelum Anestesi",signin_sebelum_anestesi});
        }
        
        if("[M]Time-Out Sebelum Insisi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Time-Out Sebelum Insisi",timeout_sebelum_insisi});
        }
        
        if("[M]Sign-Out Sebelum Menutup Luka".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Sign-Out Sebelum Menutup Luka",signout_sebelum_menutup_luka});
        }
        
        if("[M]Check List Post Operasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Check List Post Operasi",checklist_post_operasi});
        }
        
        if("[M]Rekonsiliasi Obat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Rekonsiliasi Obat",rekonsiliasi_obat});
        }
        
        if("[M]Konfirmasi Rekonsiliasi Obat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Konfirmasi Rekonsiliasi Obat",konfirmasi_rekonsiliasi_obat});
        }
        
        if("[M]Penilaian Pasien Terminal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Pasien Terminal",penilaian_pasien_terminal});
        }
        
        if("[M]Monitoring Reaksi Tranfusi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Monitoring Reaksi Tranfusi",monitoring_reaksi_tranfusi});
        }
        
        if("[M]Penilaian Korban Kekerasan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Korban Kekerasan",penilaian_korban_kekerasan});
        }
        
        if("[M]Penilaian Lanjutan Risiko Jatuh Lansia".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Lanjutan Risiko Jatuh Lansia",penilaian_lanjutan_resiko_jatuh_lansia});
        }
        
        if("[M]Penilaian Pasien Penyakit Menular".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Pasien Penyakit Menular",penilaian_pasien_penyakit_menular});
        }
        
        if("[M]Skrining Manajer Pelayanan Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Manajer Pelayanan Pasien",mpp_skrining});
        }
        
        if("[M]Edukasi Pasien & Keluarga Rawat Jalan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Edukasi Pasien & Keluarga Rawat Jalan",edukasi_pasien_keluarga_rj});
        }
        
        if("[M]Pemantauan EWS Pasien Dewasa".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pemantauan EWS Pasien Dewasa",pemantauan_pews_dewasa});
        }
        
        if("[M]Penilaian Tambahan Bunuh Diri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Tambahan Bunuh Diri",penilaian_tambahan_bunuh_diri});
        }
        
        if("[M]Penilaian Tambahan Perilaku Kekerasan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Tambahan Perilaku Kekerasan",penilaian_tambahan_perilaku_kekerasan});
        }
        
        if("[M]Penilaian Tambahan Melarikan Diri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Tambahan Melarikan Diri",penilaian_tambahan_beresiko_melarikan_diri});
        }
        
        if("[M]Penilaian Awal Medis Ralan Bedah Mulut".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Bedah Mulut",penilaian_awal_medis_ralan_bedah_mulut});
        }

        if("[M]Penilaian Pasien Keracunan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Pasien Keracunan",penilaian_pasien_keracunan});
        }
        
        if("[M]Pemantauan MEOWS Pasien Obstetri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pemantauan MEOWS Pasien Obstetri",pemantauan_meows_obstetri});
        }
        
        if("[M]Catatan ADIME Gizi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan ADIME Gizi",catatan_adime_gizi});
        }
        
        if("[M]Penilaian Awal Keperawatan Ralan Geriatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ralan Geriatri",penilaian_awal_keperawatan_ralan_geriatri});
        }
        
        if("[M]Master Masalah Keperawatan Geriatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Masalah Keperawatan Geriatri",master_masalah_keperawatan_geriatri});
        }
        
        if("[M]Master Rencana Keperawatan Geriatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Rencana Keperawatan Geriatri",master_rencana_keperawatan_geriatri});
        }
        
        if("[M]Check List Kriteria Masuk HCU".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Check List Kriteria Masuk HCU",checklist_kriteria_masuk_hcu});
        }
        
        if("[M]Check List Kriteria Keluar HCU".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Check List Kriteria Keluar HCU",checklist_kriteria_keluar_hcu});
        }
        
        if("[M]Penilaian Risiko Dekubitus".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Risiko Dekubitus",penilaian_risiko_dekubitus});
        }
        
        if("[M]Master Template Laporan Operasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Template Laporan Operasi",template_laporan_operasi});
        }
        
        if("[M]Dokumentasi Tindakan ESWL".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Dokumentasi Tindakan ESWL",hasil_tindakan_eswl});
        }
        
        if("[M]Check List Kriteria Masuk ICU".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Check List Kriteria Masuk ICU",checklist_kriteria_masuk_icu});
        }
        
        if("[M]Check List Kriteria Keluar ICU".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Check List Kriteria Keluar ICU",checklist_kriteria_keluar_icu});
        }
        
        if("[M]Follow Up DBD".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Follow Up DBD",follow_up_dbd});
        }
        
        if("[M]Penilaian Lanjutan Risiko Jatuh Neonatus".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Lanjutan Risiko Jatuh Neonatus",penilaian_risiko_jatuh_neonatus});
        }
        
        if("[M]Penilaian Lanjutan Risiko Jatuh Geriatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Lanjutan Risiko Jatuh Geriatri",penilaian_lanjutan_resiko_jatuh_geriatri});
        }
        
        if("[M]Pemantauan EWS Pasien Neonatus".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pemantauan EWS Pasien Neonatus",pemantauan_ews_neonatus});
        }
        
        if("[M]Penilaian Awal Medis Ralan Kulit & Kelamin".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Kulit & Kelamin",penilaian_awal_medis_ralan_kulit_kelamin});
        }
        
        if("[M]Penilaian Awal Medis Pasien Hemodialisa".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Pasien Hemodialisa",penilaian_medis_hemodialisa});
        }
        
        if("[M]Penilaian Level Kecemasan Ranap Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Level Kecemasan Ranap Anak",penilaian_level_kecemasan_ranap_anak});
        }
        
        if("[M]Penilaian Lanjutan Risiko Jatuh Psikiatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Lanjutan Risiko Jatuh Psikiatri",penilaian_lanjutan_resiko_jatuh_psikiatri});
        }
        
        if("[M]Penilaian Lanjutan Skrining Fungsional".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Lanjutan Skrining Fungsional",penilaian_lanjutan_skrining_fungsional});
        }
        
        if("[M]Penilaian Awal Medis Ralan Fisik & Rehabilitasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Fisik & Rehabilitasi",penilaian_medis_ralan_rehab_medik});
        }
        
        if("[M]Laporan Anestesi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Laporan Anestesi",laporan_anestesi});
        }
        
        if("[M]Penilaian Awal Medis IGD Psikiatri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis IGD Psikiatri",penilaian_medis_ralan_gawat_darurat_psikiatri});
        }
        
        if("[M]Penilaian Ulang Nyeri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Ulang Nyeri",penilaian_ulang_nyeri});
        }
        
        if("[M]Penilaian Terapi Wicara".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Terapi Wicara",penilaian_terapi_wicara});
        }
        
        if("[M]Pengkajian Restrain".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Pengkajian Restrain",pengkajian_restrain});
        }
        
        if("[M]Penilaian Awal Medis Ralan Paru".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Medis Ralan Paru",penilaian_awal_medis_ralan_paru});
        }
        
        if("[M]Catatan Keperawatan Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Keperawatan Ralan",catatan_keperawatan_ralan});
        }
        
        if("[M]Catatan Persalinan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Persalinan",catatan_persalinan});
        }
        
        if("[M]Skor Aldrette Pasca Anestesi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skor Aldrette Pasca Anestesi",skor_aldrette_pasca_anestesi});
        }
        
        if("[M]Skor Steward Pasca Anestesi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skor Steward Pasca Anestesi",skor_steward_pasca_anestesi});
        }
        
        if("[M]Skor Bromage Pasca Anestesi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skor Bromage Pasca Anestesi",skor_bromage_pasca_anestesi});
        }
        
        if("[M]Penilaian Pre Induksi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Pre Induksi",penilaian_pre_induksi});
        }
        
        if("[M]Hasil USG Urologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hasil USG Urologi",hasil_usg_urologi});
        }
        
        if("[M]Hasil USG Gynecologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hasil USG Gynecologi",hasil_usg_gynecologi});
        }
        
        if("[M]Hasil USG Neonatus".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hasil USG Neonatus",hasil_usg_neonatus});
        }
        
        if("[M]Hasil Pemeriksaan EKG".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hasil Pemeriksaan EKG",hasil_pemeriksaan_ekg});
        }
        
        if("[M]Hasil Endoskopi Faring/Laring".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hasil Endoskopi Faring/Laring",hasil_endoskopi_faring_laring});
        }
        
        if("[M]Penatalaksanaan Terapi Okupasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penatalaksanaan Terapi Okupasi",penatalaksanaan_terapi_okupasi});
        }
        
        if("[M]Hasil Endoskopi Hidung".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hasil Endoskopi Hidung",hasil_endoskopi_hidung});
        }
        
        if("[M]Master Masalah Keperawatan Neonatus".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Masalah Keperawatan Neonatus",master_masalah_keperawatan_neonatus});
        }
        
        if("[M]Master Rencana Keperawatan Neonatus".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Master Rencana Keperawatan Neonatus",master_rencana_keperawatan_neonatus});
        }
        
        if("[M]Penilaian Awal Keperawatan Ranap Neonatus".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ranap Neonatus",penilaian_awal_keperawatan_ranap_neonatus});
        }
        
        if("[M]Hasil Endoskopi Telinga".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Hasil Endoskopi Telinga",hasil_endoskopi_telinga});
        }
        
        if("[M]Penilaian Pasien Imunitas Rendah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Pasien Imunitas Rendah",penilaian_pasien_imunitas_rendah});
        }
        
        if("[M]Keseimbangan Cairan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Keseimbangan Cairan",balance_cairan});
        }
        
        if("[M]Catatan Observasi CHBP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Observasi CHBP",catatan_observasi_chbp});
        }
        
        if("[M]Catatan Observasi Induksi Persalinan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Observasi Induksi Persalinan",catatan_observasi_induksi_persalinan});
        }
        
        if("[M]Konsultasi Medik".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Konsultasi Medik",konsultasi_medik});
        }
        
        if("[M]Jawaban Konsultasi Medik".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Jawaban Konsultasi Medik",jawaban_konsultasi_medik});
        }
        
        if("[M]Skrining Merokok Usia Sekolah & Remaja".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Merokok Usia Sekolah & Remaja",skrining_perilaku_merokok_sekolah_remaja});
        }
        
        if("[M]Skrining Kekerasan Pada Perempuan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Kekerasan Pada Perempuan",skrining_kekerasan_pada_perempuan});
        }
        
        if("[M]Skrining Obesitas".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Obesitas",skrining_obesitas});
        }
        
        if("[M]Skrining Risiko Kanker Payudara".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Risiko Kanker Payudara",skrining_risiko_kanker_payudara});
        }
        
        if("[M]Skrining Risiko Kanker Paru".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Risiko Kanker Paru",skrining_risiko_kanker_paru});
        }
        
        if("[M]Skrining TBC".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining TBC",skrining_tbc});
        }
        
        if("[M]Skrining Kesehatan Gigi & Mulut Remaja".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Kesehatan Gigi & Mulut Remaja",skrining_kesehatan_gigi_mulut_remaja});
        }
        
        if("[M]Penilaian Awal Keperawatan Ranap Bayi/Anak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Penilaian Awal Keperawatan Ranap Bayi/Anak",penilaian_awal_keperawatan_ranap_bayi});
        }
        
        if("[M]Catatan Observasi Restrain Nonfarmakologi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Observasi Restrain Nonfarmakologi",catatan_observasi_restrain_nonfarma});
        }
        
        if("[M]Catatan Observasi Ventilator".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Observasi Ventilator",catatan_observasi_ventilator});
        }
        
        if("[M]Catatan Anestesi-Sedasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Catatan Anestesi-Sedasi",catatan_anestesi_sedasi});
        }
        
        if("[M]Skrining PUMA".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining PUMA",skrining_puma});
        }
        
        if("[M]Skrining Adiksi Nikotin".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Adiksi Nikotin",skrining_adiksi_nikotin});
        }
        
        if("[M]Skrining Thalassemia".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Thalassemia",skrining_thalassemia});
        }
        
        if("[M]Skrining Instrumen SDQ".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Instrumen SDQ",skrining_instrumen_sdq});
        }
        
        if("[M]Skrining Instrumen SRQ".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Instrumen SRQ",skrining_instrumen_srq});
        }
        
        if("[M]Checklist Pemberian Fibrinolitik".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Checklist Pemberian Fibrinolitik",checklist_pemberian_fibrinolitik});
        }
        
        if("[M]Skrining Kanker Kolorektal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[M]Skrining Kanker Kolorektal",skrining_kanker_kolorektal});
        }
        
        if("[N]Pengambilan BHP Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]Pengambilan BHP Medis",pengambilan_utd2});
        }

        if("[N]BHP Medis Rusak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]BHP Medis Rusak",utd_medis_rusak});
        }

        if("[N]Pengambilan BHP Non Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]Pengambilan BHP Non Medis",pengambilan_penunjang_utd2});
        }

        if("[N]BHP Non Medis Rusak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]BHP Non Medis Rusak",utd_penunjang_rusak});
        }

        if("[N]Donor Darah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]Donor Darah",utd_donor});
        }

        if("[N]Cekal Darah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]Cekal Darah",utd_cekal_darah});
        }

        if("[N]Komponen Darah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]Komponen Darah",utd_komponen_darah});
        }

        if("[N]Stok Darah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]Stok Darah",utd_stok_darah});
        }

        if("[N]Pemisahan Darah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]Pemisahan Darah",utd_pemisahan_darah});
        }

        if("[N]Penyerahan Darah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]Penyerahan Darah",utd_penyerahan_darah});
        }

        if("[N]Data Pendonor".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[N]Data Pendonor",utd_pendonor});
        }

        if("[O]Registrasi Per Poli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Poli",grafik_kunjungan_poli});
        }

        if("[O]Registrasi Per Dokter".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Dokter",grafik_kunjungan_perdokter});
        }

        if("[O]Registrasi Per Pekerjaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Pekerjaan",grafik_kunjungan_perpekerjaan});
        }

        if("[O]Registrasi Per Pendidikan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Pendidikan",grafik_kunjungan_perpendidikan});
        }

        if("[O]Registrasi Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Tahun",grafik_kunjungan_pertahun});
        }

        if("[O]Registrasi Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Bulan",grafik_kunjungan_perbulan});
        }

        if("[O]Registrasi Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Tanggal",grafik_kunjungan_pertanggal});
        }

        if("[O]Demografi Registrasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Demografi Registrasi",grafik_kunjungan_demografi});
        }

        if("[O]Reg Lama Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Reg Lama Per Tahun",grafik_kunjungan_statusdaftartahun});
        }

        if("[O]Reg Baru Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Reg Baru Per Tahun",grafik_kunjungan_statusdaftartahun2});
        }

        if("[O]Reg Lama Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Reg Lama Per Bulan",grafik_kunjungan_statusdaftarbulan});
        }

        if("[O]Reg Baru Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Reg Baru Per Bulan",grafik_kunjungan_statusdaftarbulan2});
        }

        if("[O]Reg Lama Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Reg Lama Per Tanggal",grafik_kunjungan_statusdaftartanggal});
        }

        if("[O]Reg Baru Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Reg Baru Per Tanggal",grafik_kunjungan_statusdaftartanggal2});
        }

        if("[O]Batal Periksa Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Batal Periksa Per Tahun",grafik_kunjungan_statusbataltahun});
        }

        if("[O]Batal Periksa Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Batal Periksa Per Bulan",grafik_kunjungan_statusbatalbulan});
        }

        if("[O]Batal Periksa Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Batal Periksa Per Tanggal",grafik_kunjungan_statusbataltanggal});
        }

        if("[O]Registrasi Per Cara Bayar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Cara Bayar",grafik_kunjungan_percarabayar});
        }

        if("[O]Kunjungan Ranap Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Ranap Per Tahun",grafik_kunjungan_ranaptahun});
        }

        if("[O]Kunjungan Lab Ralan Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Lab Ralan Per Tahun",grafik_lab_ralantahun});
        }

        if("[O]Kunjungan Rad Ralan Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Rad Ralan Per Tahun",grafik_rad_ralantahun});
        }

        if("[O]Registrasi Per Perujuk".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Perujuk",grafik_per_perujuk});
        }

        if("[O]Kunjungan Lab Ralan Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Lab Ralan Per Bulan",grafik_lab_ralanbulan});
        }

        if("[O]Kunjungan Rad Ralan Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Rad Ralan Per Bulan",grafik_rad_ralanbulan});
        }

        if("[O]Kunjungan Lab Ralan Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Lab Ralan Per Tanggal",grafik_lab_ralanhari});
        }

        if("[O]Kunjungan Rad Ralan Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Rad Ralan Per Tanggal",grafik_rad_ralanhari});
        }

        if("[O]Kejadian IKP Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kejadian IKP Per Tahun",grafik_ikp_pertahun});
        }

        if("[O]Kejadian IKP Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kejadian IKP Per Bulan",grafik_ikp_perbulan});
        }

        if("[O]Kejadian IKP Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kejadian IKP Per Tanggal",grafik_ikp_pertanggal});
        }

        if("[O]Kejadian IKP Per Jenis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kejadian IKP Per Jenis",grafik_ikp_jenis});
        }

        if("[O]Kejadian IKP Per Dampak".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kejadian IKP Per Dampak",grafik_ikp_dampak});
        }

        if("[O]Registrasi Per Agama".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Agama",grafik_kunjungan_per_agama});
        }

        if("[O]Registrasi Per Umur".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Umur",grafik_kunjungan_per_umur});
        }

        if("[O]Registrasi Per Suku/Bangsa".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Suku/Bangsa",grafik_kunjungan_suku});
        }

        if("[O]Registrasi Per Bahasa".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Bahasa",grafik_kunjungan_bahasa});
        }

        if("[O]Registrasi Per Cacat Fisik".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Registrasi Per Cacat Fisik",grafik_kunjungan_per_cacat});
        }

        if("[O]Periode Laporan TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Periode Laporan TB",grafik_tb_periodelaporan});
        }

        if("[O]Rujukan TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Rujukan TB",grafik_tb_rujukan});
        }

        if("[O]Riwayat TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Riwayat TB",grafik_tb_riwayat});
        }

        if("[O]Tipe Diagnosis TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Tipe Diagnosis TB",grafik_tb_tipediagnosis});
        }

        if("[O]Status HIV TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Status HIV TB",grafik_tb_statushiv});
        }

        if("[O]Skoring Anak TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Skoring Anak TB",grafik_tb_skoringanak});
        }

        if("[O]Konfirmasi Skoring 5 TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Konfirmasi Skoring 5 TB",grafik_tb_konfirmasiskoring5});
        }

        if("[O]Konfirmasi Skoring 6 TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Konfirmasi Skoring 6 TB",grafik_tb_konfirmasiskoring6});
        }

        if("[O]Sumber Obat TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Sumber Obat TB",grafik_tb_sumberobat});
        }

        if("[O]Hasil Akhir Pengobatan TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Hasil Akhir Pengobatan TB",grafik_tb_hasilakhirpengobatan});
        }

        if("[O]Hasil Tes HIV TB".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Hasil Tes HIV TB",grafik_tb_hasilteshiv});
        }

        if("[O]Pemakaian Air PDAM Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pemakaian Air PDAM Per Tanggal",grafik_air_pdam_pertanggal});
        }

        if("[O]Pemakaian Air PDAM Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pemakaian Air PDAM Per Bulan",grafik_air_pdam_perbulan});
        }

        if("[O]Limbah B3 Padat Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Limbah B3 Padat Per Tanggal",grafik_limbahb3_pertanggal});
        }

        if("[O]Limbah B3 Padat Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Limbah B3 Padat Per Bulan",grafik_limbahb3_perbulan});
        }

        if("[O]Limbah Padat Domestik Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Limbah Padat Domestik Per Tanggal",grafik_limbahdomestik_pertanggal});
        }

        if("[O]Limbah Padat Domestik Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Limbah Padat Domestik Per Bulan",grafik_limbahdomestik_perbulan});
        }

        if("[O]K3 Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Tahun",grafik_k3_pertahun});
        }

        if("[O]K3 Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Bulan",grafik_k3_perbulan});
        }

        if("[O]K3 Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Tanggal",grafik_k3_pertanggal});
        }

        if("[O]K3 Per Jenis Cidera".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Jenis Cidera",grafik_k3_perjeniscidera});
        }

        if("[O]K3 Per Penyebab Kecelakaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Penyebab Kecelakaan",grafik_k3_perpenyebab});
        }

        if("[O]K3 Per Jenis Luka".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Jenis Luka",grafik_k3_perjenisluka});
        }

        if("[O]K3 Per Lokasi Kejadian".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Lokasi Kejadian",grafik_k3_lokasikejadian});
        }

        if("[O]K3 Per Dampak Cidera".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Dampak Cidera",grafik_k3_dampakcidera});
        }

        if("[O]K3 Per Jenis Pekerjaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Jenis Pekerjaan",grafik_k3_perjenispekerjaan});
        }

        if("[O]K3 Per Bagian Tubuh".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]K3 Per Bagian Tubuh",grafik_k3_perbagiantubuh});
        }

        if("[O]Kunjungan Ranap Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Ranap Per Bulan",grafik_kunjungan_ranapbulan});
        }

        if("[O]Kunjungan Ranap Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Ranap Per Tanggal",grafik_kunjungan_ranaptanggal});
        }

        if("[O]Kunjungan Ranap Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Kunjungan Ranap Per Ruang",grafik_kunjungan_ranap_peruang});
        }

        if("[O]Pegawai Per Jenjang Jabatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pegawai Per Jenjang Jabatan",grafik_jenjang_jabatanpegawai});
        }

        if("[O]Pegawai Per Bidang/Bagian".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pegawai Per Bidang/Bagian",grafik_bidangpegawai});
        }

        if("[O]Pegawai Per Departemen".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pegawai Per Departemen",grafik_departemenpegawai});
        }

        if("[O]Pegawai Per Pendidikan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pegawai Per Pendidikan",grafik_pendidikanpegawai});
        }

        if("[O]Pegawai Per Status WP".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pegawai Per Status WP",grafik_sttswppegawai});
        }

        if("[O]Pegawai Per Status Kerja".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pegawai Per Status Kerja",grafik_sttskerjapegawai});
        }

        if("[O]Status Pulang Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Status Pulang Ranap",grafik_sttspulangranap});
        }

        if("[O]Item Apotek Per Jenis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Item Apotek Per Jenis",item_apotek_jenis});
        }

        if("[O]Item Apotek Per Kategori".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Item Apotek Per Kategori",item_apotek_kategori});
        }

        if("[O]Item Apotek Per Golongan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Item Apotek Per Golongan",item_apotek_golongan});
        }

        if("[O]Item Apotek Per Industri Farmasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Item Apotek Per Industri Farmasi",item_apotek_industrifarmasi});
        }

        if("[O]Pengajuan Aset Per Urgensi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pengajuan Aset Per Urgensi",grafik_pengajuan_aset_urgensi});
        }

        if("[O]Pengajuan Aset Per Status".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pengajuan Aset Per Status",grafik_pengajuan_aset_status});
        }

        if("[O]Pengajuan Aset Per Departemen".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pengajuan Aset Per Departemen",grafik_pengajuan_aset_departemen});
        }

        if("[O]Pegawai Per Kelompok Jabatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pegawai Per Kelompok Jabatan",grafik_kelompok_jabatanpegawai});
        }

        if("[O]Pegawai Per Resiko Kerja".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pegawai Per Resiko Kerja",grafik_resiko_kerjapegawai});
        }

        if("[O]Pegawai Per Emergency Index".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pegawai Per Emergency Index",grafik_emergency_indexpegawai});
        }

        if("[O]Jumlah Inventaris Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Jumlah Inventaris Per Ruang",grafik_inventaris_ruang});
        }

        if("[O]Jumlah Inventaris Per Jenis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Jumlah Inventaris Per Jenis",grafik_inventaris_jenis});
        }

        if("[O]Pasien HAIs Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pasien HAIs Per Ruang",grafik_HAIs_pasienbangsal});
        }

        if("[O]Pasien HAIs Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pasien HAIs Per Bulan",grafik_HAIs_pasienbulan});
        }

        if("[O]Laju HAIs VAP Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Laju HAIs VAP Per Ruang",grafik_HAIs_laju_vap});
        }

        if("[O]Laju HAIs IAD Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Laju HAIs IAD Per Ruang",grafik_HAIs_laju_iad});
        }

        if("[O]Laju HAIs Plebitis Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Laju HAIs Plebitis Per Ruang",grafik_HAIs_laju_pleb});
        }

        if("[O]Laju HAIs ISK Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Laju HAIs ISK Per Ruang",grafik_HAIs_laju_isk});
        }

        if("[O]Laju HAIs ILO Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Laju HAIs ILO Per Ruang",grafik_HAIs_laju_ilo});
        }

        if("[O]Laju HAIs HAP Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Laju HAIs HAP Per Ruang",grafik_HAIs_laju_hap});
        }

        if("[O]Penerimaan Obat, Alkes & BHP Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Penerimaan Obat, Alkes & BHP Per Bulan",penerimaan_obat_perbulan});
        }

        if("[O]Pemakaian Air Tanah Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pemakaian Air Tanah Per Tanggal",grafik_air_tanah_pertanggal});
        }

        if("[O]Pemakaian Air Tanah Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pemakaian Air Tanah Per Bulan",grafik_air_tanah_perbulan});
        }

        if("[O]Hemodialisa Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Hemodialisa Per Tanggal",grafik_harian_hemodialisa});
        }

        if("[O]Hemodialisa Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Hemodialisa Per Bulan",grafik_bulanan_hemodialisa});
        }

        if("[O]Hemodialisa Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Hemodialisa Per Tahun",grafik_tahunan_hemodialisa});
        }

        if("[O]Pasien Meninggal Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Pasien Meninggal Per Bulan",grafik_bulanan_meninggal});
        }

        if("[O]Jumlah Inventaris Per Kategori".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Jumlah Inventaris Per Kategori",grafik_inventaris_kategori});
        }

        if("[O]Jumlah Inventaris Per Merk".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Jumlah Inventaris Per Merk",grafik_inventaris_merk});
        }

        if("[O]Jumlah Inventaris Per Produsen".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Jumlah Inventaris Per Produsen",grafik_inventaris_produsen});
        }

        if("[O]Porsi Diet Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Porsi Diet Per Tanggal",grafik_porsidiet_pertanggal});
        }

        if("[O]Porsi Diet Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Porsi Diet Per Bulan",grafik_porsidiet_perbulan});
        }

        if("[O]Porsi Diet Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Porsi Diet Per Tahun",grafik_porsidiet_pertahun});
        }

        if("[O]Porsi Diet Per Ruang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Porsi Diet Per Ruang",grafik_porsidiet_perbangsal});
        }

        if("[O]Perbaikan Inventaris Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Perbaikan Inventaris Per Tanggal",grafik_perbaikan_inventaris_pertanggal});
        }

        if("[O]Perbaikan Inventaris Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Perbaikan Inventaris Per Bulan",grafik_perbaikan_inventaris_perbulan});
        }

        if("[O]Perbaikan Inventaris Per Tahun".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Perbaikan Inventaris Per Tahun",grafik_perbaikan_inventaris_pertahun});
        }

        if("[O]Perbaikan Inventaris Per Pelaksana & Status".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Perbaikan Inventaris Per Pelaksana & Status",grafik_perbaikan_inventaris_perpelaksana_status});
        }
        
        if("[O]Limbah B3 Cair Per Tanggal".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Limbah B3 Cair Per Tanggal",grafik_limbahb3cair_pertanggal});
        }
        
        if("[O]Limbah B3 Cair Per Bulan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[O]Limbah B3 Cair Per Bulan",grafik_limbahb3cair_perbulan});
        }

        if("[P]Indeks Surat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Indeks Surat",surat_indeks});
        }

        if("[P]Map Surat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Map Surat",surat_map});
        }

        if("[P]Almari Surat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Almari Surat",surat_almari});
        }

        if("[P]Rak Surat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Rak Surat",surat_rak});
        }

        if("[P]Ruang Surat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Ruang Surat",surat_ruang});
        }

        if("[P]Klasifikasi Surat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Klasifikasi Surat",surat_klasifikasi});
        }

        if("[P]Status Surat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Status Surat",surat_status});
        }

        if("[P]Sifat Surat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Sifat Surat",surat_sifat});
        }

        if("[P]Stts Balas Surat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Stts Balas Surat",surat_balas});
        }

        if("[P]Surat Masuk".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Masuk",surat_masuk});
        }

        if("[P]Surat Keluar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Keluar",surat_keluar});
        }

        if("[P]Surat Keterangan Sakit".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Keterangan Sakit",surat_sakit});
        }

        if("[P]Pengumuman E-Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Pengumuman E-Pasien",pengumuman_epasien});
        }

        if("[P]Surat Hamil".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Hamil",surat_hamil});
        }

        if("[P]Surat Bebas Narkoba".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Bebas Narkoba",surat_bebas_narkoba});
        }

        if("[P]Surat Keterangan Covid".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Keterangan Covid",surat_keterangan_covid});
        }

        if("[P]Surat Cuti Hamil".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Cuti Hamil",surat_cuti_hamil});
        }

        if("[P]Surat Kontrol".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Kontrol",skdp_bpjs});
        }

        if("[P]Surat Keterangan Rawat Inap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Keterangan Rawat Inap",surat_keterangan_rawat_inap});
        }

        if("[P]Surat Keterangan Sehat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Keterangan Sehat",surat_keterangan_sehat});
        }

        if("[P]Surat Keterangan Sakit Pihak 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Keterangan Sakit Pihak 2",surat_sakit_pihak_2});
        }

        if("[P]Surat Bebas TBC".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Bebas TBC",surat_bebas_tbc});
        }

        if("[P]Surat Keterangan Buta Warna".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Keterangan Buta Warna",surat_buta_warna});
        }

        if("[P]Surat Bebas Tato".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Bebas Tato",surat_bebas_tato});
        }

        if("[P]Surat Kewaspadaan Kesehatan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Surat Kewaspadaan Kesehatan",surat_kewaspadaan_kesehatan});
        }

        if("[P]Persetujuan/Penolakan Tindakan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Persetujuan/Penolakan Tindakan",persetujuan_penolakan_tindakan});
        }

        if("[P]Pulang Atas Permintaan Sendiri".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Pulang Atas Permintaan Sendiri",surat_pulang_atas_permintaan_sendiri});
        }
        
        if("[P]Pernyataan Pasien Umum".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Pernyataan Pasien Umum",surat_pernyataan_pasien_umum});
        }
        
        if("[P]Persetujuan Umum".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Persetujuan Umum",surat_persetujuan_umum});
        }
        
        if("[P]Persetujuan Rawat Inap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Persetujuan Rawat Inap",surat_persetujuan_rawat_inap});
        }
        
        if("[P]Persetujuan Penundaan Pelayanan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Persetujuan Penundaan Pelayanan",persetujuan_penundaan_pelayanan});
        }
        
        if("[P]Master Menolak Anjuran Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Master Menolak Anjuran Medis",master_menolak_anjuran_medis});
        }
        
        if("[P]Penolakan Anjuran Medis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Penolakan Anjuran Medis",penolakan_anjuran_medis});
        }
        
        if("[P]Template Persetujuan Penolakan Tindakan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[P]Template Persetujuan Penolakan Tindakan",template_persetujuan_penolakan_tindakan});
        }

        if("[Q]Ruang Perpustakaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Ruang Perpustakaan",ruang_perpustakaan});
        }

        if("[Q]Kategori Koleksi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Kategori Koleksi",kategori_perpustakaan});
        }

        if("[Q]Jenis Koleksi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Jenis Koleksi",jenis_perpustakaan});
        }

        if("[Q]Pengarang/Penulis".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Pengarang/Penulis",pengarang_perpustakaan});
        }

        if("[Q]Penerbit Koleksi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Penerbit Koleksi",penerbit_perpustakaan});
        }

        if("[Q]Koleksi Perpustakaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Koleksi Perpustakaan",koleksi_perpustakaan});
        }

        if("[Q]Inventaris Perpustakaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Inventaris Perpustakaan",inventaris_perpustakaan});
        }

        if("[Q]Pengaturan Peminjaman".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Pengaturan Peminjaman",set_peminjaman_perpustakaan});
        }

        if("[Q]Denda Perpustakaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Denda Perpustakaan",denda_perpustakaan});
        }

        if("[Q]Anggota Perpustakaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Anggota Perpustakaan",anggota_perpustakaan});
        }

        if("[Q]Peminjaman Koleksi Perpustakaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Peminjaman Koleksi Perpustakaan",peminjaman_perpustakaan});
        }

        if("[Q]Bayar Denda Perpustakaan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Bayar Denda Perpustakaan",bayar_denda_perpustakaan});
        }

        if("[Q]Data Koleksi Ebook".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[Q]Data Koleksi Ebook",ebook_perpustakaan});
        }

        if("[R]Suplier Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Suplier Toko",toko_suplier});
        }

        if("[R]Jenis Barang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Jenis Barang Toko",toko_jenis});
        }

        if("[R]Barang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Barang Toko",toko_barang});
        }

        if("[R]Stok Opname Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Stok Opname Toko",stok_opname_toko});
        }

        if("[R]Riwayat Barang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Riwayat Barang Toko",toko_riwayat_barang});
        }

        if("[R]Surat Pemesanan Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Surat Pemesanan Toko",toko_surat_pemesanan});
        }

        if("[R]Pengajuan Barang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Pengajuan Barang Toko",toko_pengajuan_barang});
        }

        if("[R]Penerimaan Barang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Penerimaan Barang Toko",toko_penerimaan_barang});
        }

        if("[R]Pengadaan Barang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Pengadaan Barang Toko",toko_pengadaan_barang});
        }

        if("[R]Member Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Member Toko",toko_member});
        }

        if("[R]Penjualan Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Penjualan Toko",toko_penjualan});
        }

        if("[R]Piutang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Piutang Toko",toko_piutang});
        }

        if("[R]Retur Ke Suplier Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Retur Ke Suplier Toko",toko_retur_beli});
        }

        if("[R]Pendapatan Harian Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Pendapatan Harian Toko",toko_pendapatan_harian});
        }

        if("[R]Bayar Piutang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Bayar Piutang Toko",toko_bayar_piutang});
        }

        if("[R]Piutang Harian Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Piutang Harian Toko",toko_piutang_harian});
        }

        if("[R]Penjualan Harian Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Penjualan Harian Toko",toko_penjualan_harian});
        }

        if("[R]Hutang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Hutang Toko",toko_hutang});
        }

        if("[R]Bayar Pesan Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Bayar Pesan Toko",toko_bayar_pemesanan});
        }

        if("[R]Sirkulasi Barang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Sirkulasi Barang Toko",toko_sirkulasi});
        }

        if("[R]Retur Jual Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Retur Jual Toko",toko_retur_jual});
        }

        if("[R]Retur Jual Piutang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Retur Jual Piutang",toko_retur_piutang});
        }

        if("[R]Sirkulasi Barang Toko 2".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Sirkulasi Barang Toko 2",toko_sirkulasi2});
        }

        if("[R]Keuntungan Barang Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[R]Keuntungan Barang Toko",toko_keuntungan_barang});
        }

        if("[S]Ket Pengeluaran Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Ket Pengeluaran Penerima Dankes",zis_pengeluaran_penerima_dankes});
        }

        if("[S]Ket Penghasilan Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Ket Penghasilan Penerima Dankes",zis_penghasilan_penerima_dankes});
        }

        if("[S]Ukuran Rumah Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Ukuran Rumah Penerima Dankes",zis_ukuran_rumah_penerima_dankes});
        }

        if("[S]Dinding Rumah Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Dinding Rumah Penerima Dankes",zis_dinding_rumah_penerima_dankes});
        }

        if("[S]Lantai Rumah Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Lantai Rumah Penerima Dankes",zis_lantai_rumah_penerima_dankes});
        }

        if("[S]Atap Rumah Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Atap Rumah Penerima Dankes",zis_atap_rumah_penerima_dankes});
        }

        if("[S]Kepemilikan Rumah Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Kepemilikan Rumah Penerima Dankes",zis_kepemilikan_rumah_penerima_dankes});
        }

        if("[S]Kamar Mandi Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Kamar Mandi Penerima Dankes",zis_kamar_mandi_penerima_dankes});
        }

        if("[S]Dapur Rumah Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Dapur Rumah Penerima Dankes",zis_dapur_rumah_penerima_dankes});
        }

        if("[S]Kursi Rumah Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Kursi Rumah Penerima Dankes",zis_kursi_rumah_penerima_dankes});
        }

        if("[S]Kategori PHBS Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Kategori PHBS Penerima Dankes",zis_kategori_phbs_penerima_dankes});
        }

        if("[S]Elektronik Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Elektronik Penerima Dankes",zis_elektronik_penerima_dankes});
        }

        if("[S]Ternak Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Ternak Penerima Dankes",zis_ternak_penerima_dankes});
        }

        if("[S]Jenis Simpanan Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Jenis Simpanan Penerima Dankes",zis_jenis_simpanan_penerima_dankes});
        }

        if("[S]Kategori Asnaf Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Kategori Asnaf Penerima Dankes",zis_kategori_asnaf_penerima_dankes});
        }

        if("[S]Patologis Penerima Dankes".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[S]Patologis Penerima Dankes",zis_patologis_penerima_dankes});
        }

        if("[T]Set P.J. Unit Penunjang".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set P.J. Unit Penunjang",setup_pjlab});
        }

        if("[T]Set Oto Lokasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Oto Lokasi",setup_otolokasi});
        }

        if("[T]Set Kamar Inap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Kamar Inap",setup_jam_kamin});
        }

        if("[T]Set Embalase & Tuslah".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Embalase & Tuslah",setup_embalase});
        }

        if("[T]Tracer Login".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Tracer Login",tracer_login});
        }

        if("[T]Display Antrian Registrasi & Poli".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Display Antrian Registrasi & Poli",display});
        }

        if("[T]Set Harga Obat".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Harga Obat",set_harga_obat});
        }

        if("[T]Set Penggunaan Tarif".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Penggunaan Tarif",set_penggunaan_tarif});
        }

        if("[T]Set Oto Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Oto Ralan",set_oto_ralan});
        }

        if("[T]Biaya Harian Kamar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Biaya Harian Kamar",biaya_harian});
        }

        if("[T]Biaya Masuk Sekali".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Biaya Masuk Sekali",biaya_masuk_sekali});
        }

        if("[T]Set RM".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set RM",set_no_rm});
        }

        if("[T]Set Harga Obat Ralan".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Harga Obat Ralan",set_harga_obat_ralan});
        }

        if("[T]Set Harga Obat Ranap".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Harga Obat Ranap",set_harga_obat_ranap});
        }

        if("[T]Set Billing".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Billing",set_nota});
        }

        if("[T]Closing Kasir".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Closing Kasir",closing_kasir});
        }

        if("[T]Set Keterlambatan Presensi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Keterlambatan Presensi",keterlambatan_presensi});
        }

        if("[T]Set Harga Kamar".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Harga Kamar",set_harga_kamar});
        }

        if("[T]Set Input Parsial".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Input Parsial",set_input_parsial});
        }

        if("[T]Display Antrian Apotek".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Display Antrian Apotek",display_apotek});
        }

        if("[T]Password BPJS".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Password BPJS",password_asuransi});
        }

        if("[T]Set Harga Toko".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Set Harga Toko",toko_set_harga});
        }

        if("[T]Jam Diet Pasien".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Jam Diet Pasien",jam_diet_pasien});
        }

        if("[T]Ruang Operasi".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Ruang Operasi",ruang_ok});
        }

        if("[T]Integrasi Khanza Health Services".toLowerCase().contains(TCari.getText().toLowerCase())){
            tabMode.addRow(new Object[]{false,"[T]Integrasi Khanza Health Services",integrasi_khanza_health_services});
        }
    }
    
    public void isUser(String User,String Nama, String Password){
        TKd.setText(User);
        TNmUser.setText(Nama);
        TPass.setText(Password);
        tampil(User);
        TCari.requestFocus();
    }

    private void Simpan() {
        for(i=0;i<tbUser.getRowCount();i++){ 
            if("[A]Jadwal Praktek".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jadwal_praktek='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Registrasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","registrasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Tindakan Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tindakan_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Rawat Inap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kamar_inap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Tindakan Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tindakan_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Operasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","operasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Rujukan Keluar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rujukan_keluar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Rujukan Masuk".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rujukan_masuk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Beri Obat, Alkes & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","beri_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Resep Pulang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","resep_pulang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Diet Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","diet_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Periksa Lab PK".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","periksa_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Periksa Radiologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","periksa_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Rawat Jalan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kasir_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Informasi Kamar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","informasi_kamar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]No.Resep".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","resep_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Billing Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","billing_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Billing Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","billing_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]IGD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","igd='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]DPJP Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dpjp_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Edit Registrasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","edit_registrasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Rujukan Poli Internal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rujukan_poli_internal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Billing Parsial".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","billing_parsial='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Akses Depo Obat/BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akses_depo_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Jadwal Operasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","booking_operasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Booking Registrasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","booking_registrasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Permintaan Lab".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Permintaan Radiologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Catatan Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_perawatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Asesmen Awal Rawat Inap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengkajian_askep='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Skrining Rawat Jalan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sekrining_rawat_jalan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[A]Perkiraan Biaya Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","perkiraan_biaya_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Permintaan Diet".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_diet='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Deteksi Dini Corona".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","deteksi_corona='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Booking Periksa".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","booking_periksa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Periksa Lab PA".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemeriksaan_lab_pa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Permintaan Rawat Inap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Ubah Petugas Lab PK".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ubah_petugas_lab_pk='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Ubah Petugas Lab PA".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ubah_petugas_lab_pa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Ubah Petugas Radiologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ubah_petugas_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Gabung Nomor Rawat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","gabung_norawat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Periksa Lab MB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemeriksaan_lab_mb='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Ubah Petugas Lab MB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ubah_petugas_lab_mb='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Akses Ke Dokter Lain Rawat Jalan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akses_dokter_lain_rawat_jalan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[A]Booking MCU Perusahaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","booking_mcu_perusahaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[B]Barcode Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","barcoderalan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[B]Barcode Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","barcoderanap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Petugas".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","petugas='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Barcode Presensi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","barcode='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Presensi Harian".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","presensi_harian='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Presensi Bulanan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","presensi_bulanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Pegawai Admin".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pegawai_admin='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Pegawai User".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pegawai_user='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]SMS Gateway".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sms='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Sidik Jari".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sidikjari='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Jam Presensi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jam_masuk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Jadwal Pegawai".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jadwal_pegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Temporary Presensi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","temporary_presensi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Master Berkas Pegawai".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_berkas_pegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Berkas Kepegawaian".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","berkas_kepegawaian='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Riwayat Jabatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","riwayat_jabatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Riwayat Pendidikan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","riwayat_pendidikan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Riwayat Naik Gaji".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","riwayat_naik_gaji='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Kegiatan Ilmiah & Pelatihan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kegiatan_ilmiah='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Riwayat Penghargaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","riwayat_penghargaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Riwayat Penelitian".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","riwayat_penelitian='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Jenis Cidera K3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jenis_cidera_k3rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Penyebab Kecelakaan K3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penyebab_k3rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Jenis Luka K3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jenis_luka_k3rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Lokasi Kejadian K3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lokasi_kejadian_k3rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Dampak Cidera K3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dampak_cidera_k3rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Jenis Pekerjaan K3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jenis_pekerjaan_k3rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Bagian Tubuh K3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bagian_tubuh_k3rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Peristiwa K3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","peristiwa_k3rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Jenis Cidera K3 Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jenis_cidera_k3rstahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Penyebab Kecelakaan K3 Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penyebab_k3rstahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Jenis Luka K3 Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jenis_luka_k3rstahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Lokasi Kejadian K3 Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lokasi_kejadian_k3rstahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Dampak Cidera K3 Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dampak_cidera_k3rstahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Jenis Pekerjaan K3 Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jenis_pekerjaan_k3rstahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Bagian Tubuh K3 Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bagian_tubuh_k3rstahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Pengajuan Cuti".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengajuan_cuti='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Kepatuhan APD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_kepatuhan_apd='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Cuci Tangan Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_cuci_tangan_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Pembuangan Limbah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_pembuangan_limbah='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Ruang/Unit Audit Kepatuhan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ruang_audit_kepatuhan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Pembuangan Benda Tajam & Jarum".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_pembuangan_benda_tajam='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Penanganan Darah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_penanganan_darah='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Pengelolaan Linen Kotor".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_pengelolaan_linen_kotor='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Penempatan Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_penempatan_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Kamar Jenazah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_kamar_jenazah='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Bundle IADP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_bundle_iadp='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Bundle IDO".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_bundle_ido='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Fasilitas Kebersihan Tangan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_fasilitas_kebersihan_tangan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Fasilitas APD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_fasilitas_apd='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Pembuangan Limbah Cair Infeksius".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_pembuangan_limbah_cair_infeksius='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Sterilisasi Alat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_sterilisasi_alat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Bundle ISK".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_bundle_isk='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Audit Bundle PLABSI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_bundle_plabsi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[C]Audit Bundle VAP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","audit_bundle_vap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Kategori Penilaian SKP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skp_kategori_penilaian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Kriteria Penilaian SKP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skp_kriteria_penilaian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Penilaian SKP Petugas/Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skp_penilaian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[C]Rekapitulasi Penilaian SKP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skp_rekapitulasi_penilaian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Suplier Obat/Alkes/BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","suplier='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Satuan Barang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satuan_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Konversi Satuan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","konversi_satuan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Jenis Obat/Alkes/BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jenis_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Obat, Alkes & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Stok Opname Apotek".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","stok_opname_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Stok Obat Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","stok_obat_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Pengadaan Obat, Alkes & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengadaan_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Penerimaan Obat, Alkes & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemesanan_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Penjualan Obat, Alkes & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penjualan_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Piutang Obat, Alkes & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","piutang_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Retur Ke Suplier".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","retur_ke_suplier='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Retur Dari Pembeli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","retur_dari_pembeli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Retur Obat, Alkes & BHP Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","retur_obat_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Retur Piutang Pembeli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","retur_piutang_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Keuntungan Penjualan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","keuntungan_penjualan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Keuntungan Beri Obat, Alkes & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","keuntungan_beri_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Sirkulasi Obat, Alkes & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sirkulasi_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Mutasi Obat/Alkes/BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","mutasi_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Darurat Stok".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","darurat_stok='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Sirkulasi Obat, Alkes & BHP 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sirkulasi_obat2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Industri Farmasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","industrifarmasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Pengambilan BHP UTD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengambilan_utd='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Keuntungan Beri Obat, Alkes & BHP 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","keuntungan_beri_obat_nonpiutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Riwayat Obat, Alkes & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","riwayat_obat_alkes_bhp='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Resep Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","resep_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Kategori Obat/Alkes/BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kategori_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Golongan Obat/Alkes/BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","golongan_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Obat/Alkes/BHP Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemberian_obat_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Penjualan Bebas Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penjualan_obat_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Permintaan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Ringkasan Permintaan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_permintaan_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Surat Pemesanan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_pemesanan_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Stok Keluar Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengeluaran_stok_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Metode Racik".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","metode_racik='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Pengguna Obat/Alkes/BHP Resep".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengguna_obat_resep='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Rekap Penerimaan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_pemesanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Riwayat Batch".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","riwayat_data_batch='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Kegiatan Farmasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kegiatan_farmasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Sirkulasi Obat, Alkes & BHP 3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sirkulasi_obat3='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]PPN Obat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ppn_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Data Batch".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","data_batch='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Kadaluarsa Batch".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kadaluarsa_batch='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Sisa Stok".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sisa_stok='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Obat Per Resep".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat_per_resep='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Pengajuan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengajuan_barang_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]10 Obat Terbanyak Poli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","10_obat_terbanyak_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Rekap Obat Per Poli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_obat_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Rekap Obat Per Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_obat_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Hibah Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hibah_obat_bhp='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Sirkulasi Obat, Alkes & BHP 4".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sirkulasi_obat4='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Sirkulasi Obat, Alkes & BHP 5".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sirkulasi_obat5='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Permintaan Stok Obat Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_stok_obat_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Verifikasi Penerimaan Obat/Alkes/BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","verifikasi_penerimaan_farmasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Pengajuan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_pengajuan_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Pemesanan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_pemesanan_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Pengadaan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_pengadaan_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Penerimaan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_penerimaan_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Hibah Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_hibah_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Penjualan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_penjualan_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Beri Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_beri_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Piutang Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_piutang_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Stok Keluar Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_stok_keluar_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Retur Suplier Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_retur_suplier_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Retur Pembeli Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_retur_pembeli_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Penggunaan BHP OK/VK".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penggunaan_bhp_ok='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Resep Luar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","resep_luar='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Stok Akhir Farmasi Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","stok_akhir_farmasi_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Telaah Resep & Obat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","telaah_resep='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[D]Permintaan Resep Pulang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_resep_pulang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Ringkasan Biaya Obat Pasien Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_biaya_obat_pasien_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Nilai Penerimaan Vendor Farmasi Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","nilai_penerimaan_vendor_farmasi_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Obat/Alkes/BHP Tidak Bergerak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat_bhp_tidakbergerak='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[D]Sirkulasi Obat, Alkes & BHP 6".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sirkulasi_obat6='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Barang Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Pengadaan Barang Nonmedis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_pengadaan_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Stok Keluar Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_stok_keluar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Rekap Pengadaan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_rekap_pengadaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Rekap Stok Keluar Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_rekap_stok_keluar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Biaya Pengadaan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_pengeluaran_harian='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Jenis Barang Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_jenis_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Pengambilan UTD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengambilan_penunjang_utd='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Suplier Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","suplier_penunjang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Pengadaan Non Medis Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_pengadaan_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Stok Keluar Non Medis Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_stokkeluar_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Permintaan Barang Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Ringkasan Permintaan Barang Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_permintaan_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Surat Pemesanan Barang Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_pemesanan_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Penerimaan Barang Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penerimaan_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Rekap Penerimaan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_pemesanan_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Stok Opname Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","stok_opname_logistik='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Sirkulasi Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sirkulasi_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[E]Pengajuan Barang Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengajuan_barang_nonmedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Sirkulasi Non Medis 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sirkulasi_non_medis2='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Retur Ke Suplier Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_returbeli='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Riwayat Barang Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ipsrs_riwayat_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Verifikasi Penerimaan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","verifikasi_penerimaan_logistik='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Ringkasan Pengajuan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_pengajuan_nonmedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Ringkasan Pemesanan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_pemesanan_nonmedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Ringkasan Pengadaan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_pengadaan_nonmedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Ringkasan Penerimaan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_penerimaan_nonmedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Ringkasan Stok Keluar Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_stokkeluar_nonmedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Ringkasan Retur Suplier Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_returbeli_nonmedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Hibah Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hibah_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[E]Nilai Penerimaan Vendor Non Medis Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","nilai_penerimaan_vendor_nonmedis_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Barang Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dapur_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Stok Opname Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dapur_opname='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Suplier Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dapur_suplier='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Pengadaan Barang Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dapur_pembelian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Stok Keluar Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dapur_stok_keluar='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Riwayat Barang Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dapur_riwayat_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Permintaan Barang Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_dapur='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Biaya Pengadaan Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","biaya_pengadaan_dapur='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[F]Rekap Pengadaan Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_pengadaan_dapur='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Ringkasan Pengadaan Barang Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dapur_ringkasan_pembelian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[F]Penerimaan Barang Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dapur_pemesanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Jenis Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inventaris_jenis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Kategori Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inventaris_kategori='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Merk Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inventaris_merk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Ruang Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inventaris_ruang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Produsen Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inventaris_produsen='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Koleksi Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inventaris_koleksi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inventaris_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Sirkulasi Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inventaris_sirkulasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Barang CSSD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","barang_cssd='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Pemakaian Air PDAM".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemakaian_air_pdam='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Limbah Padat B3 Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","limbah_b3_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Limbah Padat Domestik".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","limbah_domestik='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Mutu Air Limbah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","mutu_air_limbah='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Pest Control".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pest_control='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Pengajuan Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengajuan_asetinventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Rekap Pengajuan Aset Departemen".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_pengajuan_aset_departemen='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[G]Permintaan Perbaikan Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","permintaan_perbaikan_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Asal Hibah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","asal_hibah='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Pemakaian Air Tanah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemakaian_air_tanah='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Perbaikan Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","perbaikan_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Pemeliharaan Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemeliharaan_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Pengadaan Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengadaan_aset_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Suplier Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","suplier_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Penerimaan Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penerimaan_aset_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Hibah Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hibah_aset_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Pemeliharaan Gedung".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemeliharaan_gedung='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[G]Limbah Cair B3 Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kesling_limbah_b3medis_cair='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[H]Jenis Parkir".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","parkir_jenis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[H]Parkir Masuk".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","parkir_in='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[H]Parkir Keluar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","parkir_out='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[H]Rekap Parkir Harian".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","parkir_rekap_harian='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[H]Rekap Parkir Bulanan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","parkir_rekap_bulanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[H]Barcode Parkir".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","parkir_barcode='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Harian Dokter Poli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_tindakan_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Obat Per Poli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat_per_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Obat Per Kamar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat_per_kamar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Obat Per Dokter Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat_per_dokter_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Obat Per Dokter Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat_per_dokter_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Harian Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Bulanan Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bulanan_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Harian Paramedis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_paramedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Bulanan Paramedis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bulanan_paramedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Pembayaran Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Pembayaran Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Rekap Pembayaran Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_pembayaran_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Rekap Pembayaran Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_pembayaran_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Tagihan Masuk".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tagihan_masuk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Tambahan Biaya".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tambahan_biaya='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Potongan Biaya".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","potongan_biaya='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Detail JM Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jm_ranap_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Harian Dokter Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_tindakan_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Fee Visit Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","fee_visit_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Fee Bacaan EKG".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","fee_bacaan_ekg='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Fee Rujukan Rontgen".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","fee_rujukan_rontgen='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Fee Rujukan Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","fee_rujukan_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Fee Periksa Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","fee_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Obat Per Dokter Peresep".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat_per_dokter_peresep='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Rekap Per Shift".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_per_shift='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Obat Per Cara Bayar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat_per_cara_bayar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Payment Point".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","payment_point='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Harian J.S.".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_js='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Bulanan J.S.".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bulanan_js='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Harian BHP Medis/Paket Obat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_paket_bhp='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Bulanan BHP Medis/Paket Obat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bulanan_paket_bhp='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Harian Kamar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_kamar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Harian KSO".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_kso='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Bulanan KSO".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bulanan_kso='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Harian Menejemen".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_menejemen='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Bulanan Menejemen".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bulanan_menejemen='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Piutang Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","piutang_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Piutang Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","piutang_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Detail Tindakan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","detail_tindakan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Rekap Poli Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_poli_anak='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Pembayaran Per Unit".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_per_unit='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Rekap Pembayaran Per Unit".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_pembayaran_per_unit='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Detail VK/OK".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","detail_tindakan_okvk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Detail JM Dokter 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","detailjmdokter2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Pembayaran Per Akun Bayar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_akun_bayar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Piutang Per Akun Piutang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","piutang_akun_piutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Payment Point 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","payment_point2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Pembayaran Per Akun Bayar 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_akun_bayar2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Hapus Nota Salah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hapus_nota_salah='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[I]Pembayaran Per Akun Bayar 3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_akun_bayar3='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[I]Ringkasan Tindakan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_tindakan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[I]Pembayaran Per Akun Bayar 4".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_akun_bayar4='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[I]Pembayaran Per Akun Bayar 5".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_akun_bayar5='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[I]Jasa Tindakan Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jasa_tindakan_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[I]Rekap JM Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_jm_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[I]Rekap Biaya Registrasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_biaya_registrasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]ICD 10".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penyakit='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Obat Penyakit".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","obat_penyakit='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Frekuensi Penyakit Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penyakit_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Frekuensi Penyakit Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penyakit_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Penyakit AFP & PD3I".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penyakit_pd3i='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Surveilans AFP & PD3I".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surveilans_pd3i='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Surveilans Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surveilans_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Surveilans Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surveilans_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Pny.Tdk Menular Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pny_takmenular_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Pny.Tdk Menular Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pny_takmenular_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Kunjungan Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kunjungan_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 3.2 Rawat Darurat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl32='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 3.3 Gigi dan Mulut".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl33='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 3.7 Radiologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl37='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 3.8 Laboratorium".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl38='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 3.4 Kebidanan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl34='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 3.6 Pembedahan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl36='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Kunjungan Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kunjungan_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]ICD 9".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","icd9='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Sensus Harian Poli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sensus_harian_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 4A Sebab Morbiditas Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl4a='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 4B Sebab Morbiditas Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl4b='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 4A Morbiditas Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl4asebab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]RL 4B Morbiditas Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rl4bsebab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Lama Pelayanan Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lama_pelayanan_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Harian HAIs".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_HAIs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Bulanan HAIs".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bulanan_HAIs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]HAIs Per Kamar/Bangsal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hais_perbangsal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Hitung BOR".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hitung_bor='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Lama Pelayanan Apotek".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lama_pelayanan_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Hitung ALOS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hitung_alos='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Pny Menular Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penyakit_menular_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Pny Menular Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penyakit_menular_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Pembatalan Periksa Per Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembatalan_periksa_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Cek Entry Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","cek_entry_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Sensus Harian Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sensus_harian_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Insiden Keselamatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","insiden_keselamatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Ranap Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ranap_per_ruang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Penyakit Ranap Per Cara Bayar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penyakit_ranap_cara_bayar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Anggota TNI Dirawat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","anggota_militer_dirawat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Lama Pelayanan Radiologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lama_pelayanan_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Lama Pelayanan Lab".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lama_pelayanan_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Rekap Lab Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_lab_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Perujuk Lab Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","perujuk_lab_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Rekap Radiologi Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_radiologi_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Perujuk Radiologi Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","perujuk_radiologi_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Rekap Bulanan Porsi Diet".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jumlah_porsi_diet='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Rekap Bulanan Macam Diet".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jumlah_macam_diet='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Kunjungan Lab Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kunjungan_permintaan_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Kunjungan Lab Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kunjungan_permintaan_lab2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Kunjungan Radiologi Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kunjungan_permintaan_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Kunjungan Radiologi Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kunjungan_permintaan_radiologi2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Data TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kemenkes_sitt='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Rekap Mutasi Berkas".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_mutasi_berkas='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Skrining Pernapasan Ralan Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_ralan_pernapasan_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Masuk Ruang Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kunjungan_bangsal_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]KIP Pasien Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kip_pasien_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]KIP Pasien Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kip_pasien_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Rekap Permintaan Diet".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_permintaan_diet='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Daftar Pasien Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","daftar_pasien_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Daftar Pasien Ranap TNI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","daftar_pasien_ranaptni='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Harian HAIs 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_HAIs2='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Rekap Kunjungan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_kunjungan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[J]Kedatangan Pasien Per Jam".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kedatangan_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Registrasi Poli Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","registrasi_poli_per_tanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Lama Pelayanan Poli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lama_pelayanan_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Laporan Tahunan IRJ".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","laporan_tahunan_irj='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Bulanan Klasifikasi Pasien Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bulanan_klasifikasi_pasien_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Harian Klasifikasi Pasien Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","harian_klasifikasi_pasien_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Klasifikasi Pasien Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","klasifikasi_pasien_perbangsal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Lama Penyiapan RM".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lama_penyiapan_rm='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Dosis Radiologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","dosis_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Demografi Umur Kunjungan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","demografi_umur_kunjungan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Lama Pelayanan Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lama_pelayanan_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Lama Operasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","lama_operasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Status Data RM".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","status_data_rm='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Laporan Tahunan IGD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","laporan_tahunan_igd='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Anggota POLRI Dirawat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","anggota_polri_dirawat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Daftar Pasien Ranap POLRI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","daftar_pasien_ranap_polri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Laporan Penyakit POLRI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","laporan_penyakit_polri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Jumlah Pengunjung Ralan POLRI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jumlah_pengunjung_ralan_polri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Jumlah Pengunjung Ralan TNI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jumlah_pengunjung_ralan_tni='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Laporan Penyakit TNI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","laporan_penyakit_tni='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Laporan Bulanan IRJ".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","laporan_bulanan_irj='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Operasi Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","operasi_per_bulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Sisa Diet Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sisa_diet_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Laporan Tahunan Penolakan Anjuran Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","laporan_tahunan_penolakan_anjuran_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Pemeriksaan Fisik Ralan Per Penyakit".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemeriksaan_fisik_ralan_per_penyakit='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Kepatuhan Kelengkapan Keselamatan Bedah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kepatuhan_kelengkapan_keselamatan_bedah='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Data Sasaran Usia Produktif".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","data_sasaran_usiaproduktif='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[J]Data Sasaran Usia Lansia".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","data_sasaran_usialansia='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Deposit Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","deposit_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Piutang Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","piutang_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Kamar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kamar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Tarif Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tarif_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Tarif Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tarif_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Tarif Lab".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tarif_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Tarif Radiologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tarif_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Tarif Operasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tarif_operasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Akun Rekening".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_rekening='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Rekening Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekening_tahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Posting Jurnal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","posting_jurnal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Buku Besar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","buku_besar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Cash Flow".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","cashflow='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Keuangan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","keuangan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Pengeluaran Harian".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengeluaran='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Akun Bayar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_bayar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Bayar Pesan Obat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bayar_pemesanan_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Pemasukkan Lain-Lain".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemasukan_lain='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Pengaturan Rekening".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengaturan_rekening='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Bayar Piutang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bayar_piutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Jurnal Harian".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jurnal_harian='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Piutang Belum Lunas".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","piutang_pasien2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Tarif UTD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tarif_utd='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Rincian Piutang Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rincian_piutang_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Hutang Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hutang_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Akun Piutang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_piutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Piutang Per Cara Bayar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","detail_piutang_penjab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Bayar Pesan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bayar_pesan_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Bayar Pesan Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bayar_pemesanan_iventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Hutang Barang Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hutang_barang_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[K]Saldo Akun Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","saldo_akun_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Penagihan Piutang Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penagihan_piutang_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Akun Penagihan Piutang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_penagihan_piutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Set Tarif Online".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_tarif_online='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Klaim Rawat Jalan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","klaim_rawat_jalan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]RVP Piutang BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rvu_bpjs='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Penerimaan/Omset/Kas Masuk".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","omset_penerimaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Validasi Penagihan Piutang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","validasi_penagihan_piutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Pendapatan Per Cara Bayar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pendapatan_per_carabayar='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Pembayaran Bank Jateng".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_bank_jateng='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Titip Faktur/Tagihan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tagihan_hutang_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Pengembalian Deposit Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengembalian_deposit_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Validasi Titip Faktur/Tagihan Obat & BHP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","validasi_tagihan_hutang_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Piutang Obat & BHP Belum Lunas".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","piutang_obat_belum_lunas='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Akun Jenis Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_aset_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Hutang Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hutang_aset_inventaris='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Titip Faktur/Tagihan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","titip_faktur_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Validasi Titip Faktur/Tagihan Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","validasi_tagihan_non_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Titip Faktur/Tagihan Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","titip_faktur_aset='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Validasi Titip Faktur/Tagihan Aset/Inventaris".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","validasi_tagihan_aset='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Peminjam Piutang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","peminjam_piutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Piutang Lain-lain".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","piutang_lainlain='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Bayar Piutang Lain-lain".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bayar_piutang_lain='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Asuransi/Askes/Jenis Bayar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","cara_bayar='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Kategori Pengeluaran Harian".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kategori_pengeluaran_harian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Kategori Pemasukan Lain-lain".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kategori_pemasukan_lain='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Bayar JM Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bayar_jm_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Pembayaran BRIVA".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_briva='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Akun Bayar Hutang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_bayar_hutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Ringkasan Hutang Vendor Farmasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_hutang_vendor_farmasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Ringkasan Hutang Vendor Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_hutang_vendor_nonmedis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Pembayaran Bank Papua".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_bank_papua='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Pembayaran Bank Jabar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_bank_jabar='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Pengajuan Biaya".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengajuan_biaya='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Persetujuan Pengajuan Biaya".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","persetujuan_pengajuan_biaya='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Validasi Persetujuan Pengajuan Biaya".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","validasi_persetujuan_pengajuan_biaya='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Rekap Pengajuan Biaya".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekap_pengajuan_biaya='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Pembayaran Bank Mandiri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_bank_mandiri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Nilai Piutang Per Cara Bayar Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","nilai_piutang_perjenis_bayar_per_bulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Ringkasan Piutang Per Cara Bayar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ringkasan_piutang_jenis_bayar='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Pembayaran Pihak Ke 3 Bank Mandiri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pembayaran_pihak_ke3_bankmandiri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Bayar Pesan Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bayar_pesan_dapur='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[K]Hutang Barang Dapur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hutang_dapur='"+tbUser.getValueAt(i,2).toString()+"'");
            }
        }
    }
    
    private void Simpan2() {
        for(i=0;i<tbUser.getRowCount();i++){ 
            if("[L]Cek NIK".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_nik='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Cek No.Kartu".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_kartu='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Riwayat Rujukan PCare di VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_riwayat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Cek No.Rujukan PCare di VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_nomor_rujukan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Diagnosa VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_diagnosa='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Poli VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Faskes VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_faskes='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Data Bridging SEP VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_sep='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Monitoring Verifikasi Klaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_monitoring_klaim='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Reklasifikasi Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","reklasifikasi_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Reklasifikasi Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","reklasifikasi_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Kamar Aplicare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","aplicare_referensi_kamar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Ketersediaan Kamar Aplicare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","aplicare_ketersediaan_kamar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Klaim Baru Otomatis INACBG".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inacbg_klaim_baru_otomatis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Klaim Baru Manual INACBG".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inacbg_klaim_baru_manual='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Coder NIK INACBG".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inacbg_coder_nik='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Cek Eligibilitas Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_cek_eligibilitas='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Ruang Rawat Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_referensi_jenpel_ruang_rawat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Poli Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_referensi_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Faskes Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_referensi_faskes='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Data Bridging SJP Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_sjp='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Diagnosa Pcare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_penyakit='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Kesadaran Pcare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_kesadaran='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Cek Rujukan PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_rujukan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Klaim Baru Manual INACBG 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inacbg_klaim_baru_manual2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Prosedur VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_prosedur='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Kelas Rawat VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_kelas_rawat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Dokter VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Spesialistik VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_spesialistik='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Ruang Rawat VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_ruangrawat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Cara Keluar VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_carakeluar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Pasca Pulang VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_pasca_pulang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Cek No.Rujukan RS di VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_nomor_rujukan_rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Cek Rujukan Kartu PCare di VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_rujukan_kartu_pcare='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Cek Rujukan Kartu RS di VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_rujukan_kartu_rs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Pembuatan Rujukan VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_rujukan_keluar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Mapping Poli VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","mapping_poli_bpjs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Propinsi VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_propinsi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Kabupaten VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_kabupaten='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Kecamatan VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_kecamatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Dokter DPJP VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_dokterdpjp='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Riwayat Rujukan RS di VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_riwayat_rujukanrs='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Tanggal Rujukan di VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_tanggal_rujukan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Histori Pelayanan BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_histori_pelayanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Dokter PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Poli PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Provider PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_provider='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Stts Pulang PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_statuspulang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Spesialis PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_spesialis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Subspesialis PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_subspesialis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Sarana PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_sarana='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Khusus PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_khusus='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Obat PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Tindakan PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_tindakan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Faskes Subspesialis PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_faskessubspesialis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Faskes Alih Rawat PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_faskesalihrawat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Faskes Thalasemia & Hemofili PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_faskesthalasemia='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Mapping Obat PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_mapping_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Tarif Ralan RS & PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_mapping_tindakan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Club Prolanis PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_club_prolanis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Mapping Poli PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_mapping_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Kegiatan Kelompok PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_kegiatan_kelompok='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Tarif Ranap RS & PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_mapping_tindakan_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Peserta Keg Kelompok PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_peserta_kegiatan_kelompok='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Data Pendafataran PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bridging_pcare_daftar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Mapping Dokter PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_mapping_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Cek Nomor SEP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_sep='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Faskes Sisrute".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sisrute_referensi_faskes='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Alasan Rujuk Sisrute".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sisrute_referensi_alasanrujuk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Referensi Diagnosa Sisrute".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sisrute_referensi_diagnosa='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Rujukan Masuk Sisrute".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sisrute_rujukan_masuk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Rujukan Keluar Sisrute".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","sisrute_rujukan_keluar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Cek SKDP VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_cek_skdp='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Pemberian Obat PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_pemberian_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Pemberian Tindakan PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_pemberian_tindakan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Ketersediaan Kamar SIRANAP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","siranap_ketersediaan_kamar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Mapping Dokter DPJP VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_mapping_dokterdpjp='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Mapping Poli Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_mapping_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[L]Mapping Dokter Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_mapping_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Tarif Ralan Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_mapping_tindakan_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Tarif Ranap Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_mapping_tindakan_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Tarif Radiologi Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_mapping_tindakan_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Tarif Laborat Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_mapping_tindakan_laborat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Tarif Operasi Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_mapping_tindakan_operasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Tagihan Inhealth".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inhealth_kirim_tagihan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Pasien Corona".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pasien_corona='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Diagnosa Pasien Corona".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","diagnosa_pasien_corona='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Perawatan Pasien Corona".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","perawatan_pasien_corona='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Cek No.Kartu PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_kartu='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Diagnosa PRB VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_diagnosa_prb='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Obat PRB VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_obat_prb='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Surat Kontrol VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_surat_kontrol='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Surat PRI VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_surat_pri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Pendaftaran Mobile JKN".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","referensi_mobilejkn_bpjs='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Batal Pendaftaran Mobile JKN".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","batal_pendaftaran_mobilejkn_bpjs='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Host To Host Bank Jateng".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_host_to_host_bank_jateng='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Integrasi BRI API".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","integrasi_briapi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi TACC PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_alasan_tacc='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Poli HFIS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_poli_hfis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Dokter HFIS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_dokter_hfis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Jadwal HFIS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_jadwal_hfis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Program PRB di VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_program_prb='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Suplesi Jasa Raharja di VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_suplesi_jasaraharja='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Data Induk Kecelakaan VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_data_induk_kecelakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Data SEP Internal VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_sep_internal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Klaim Jaminan Jasa Raharja VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_klaim_jasa_raharja='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Pasien Finger Print VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_daftar_finger_print='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Rujukan Khusus VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_rujukan_khusus='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Task ID Mobile JKN".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_task_id='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi DPHO Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_dpho_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Poli Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_poli_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Faskes Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_faskes_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Spesialistik Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_spesialistik_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Host To Host Bank Papua".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_host_to_host_bank_papua='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Host To Host Bank Jabar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_host_to_host_bank_jabar='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Praktisi Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_referensi_dokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Pasien Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_referensi_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Mapping Organisasi Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_mapping_departemen='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Mapping Lokasi Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_mapping_lokasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Mapping Vaksin Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_mapping_vaksin='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Encounter Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_encounter='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Condition Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_condition='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Observation-TTV Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_observationttv='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Procedure Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_procedure='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Vaksin Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_Immunization='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Clinical Impression Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_clinicalimpression='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Antrean Per Tanggal Mobile JKN".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_antrean_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Riwayat Perawatan ICare BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","riwayat_perawatan_icare_bpjs='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Host To Host Bank Mandiri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","akun_host_to_host_bank_mandiri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Setting PPK Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_setting_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Obat Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_referensi_obat_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Mapping Obat Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_mapping_obat_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Obat 23 Hari Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_obat_23hari_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Pencarian SEP Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_kunjungan_sep_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Monitoring Klaim Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_monitoring_klaim_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Daftar Pelayanan Obat Apotek BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bpjs_daftar_pelayanan_obat_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Hapus/Edit SEP VClaim".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hapus_edit_sep_bpjs='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Diet Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_diet='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Mapping Obat/Alkes/BHP Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_mapping_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Medication Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_medication='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Medication Request Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_medicationrequest='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Medication Dispense Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_medicationdispense='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Mapping Tindakan Radiologi Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_mapping_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Service Request Radiologi Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_servicerequest_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Specimen Radiologi Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_specimen_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Observation Radiologi Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_observation_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Diagnostic Report Radiologi Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_diagnosticreport_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Mapping Tindakan Lab PK & MB Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_mapping_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Service Request Lab PK Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_servicerequest_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Service Request Lab MB Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_servicerequest_labmb='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Specimen Lab PK Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_specimen_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Specimen Lab MB Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_specimen_labmb='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Observation Lab PK Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_observation_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Observation Lab MB Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_observation_labmb='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Diagnostic Report Lab PK Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_diagnosticreport_lab='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Diagnostic Report Lab MB Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_diagnosticreport_labmb='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Poli Mobile JKN FKTP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","referensi_poli_mobilejknfktp='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Dokter Mobile JKN FKTP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","referensi_dokter_mobilejknfktp='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Metode Pembayaran Bank Mandiri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","metode_pembayaran_bankmandiri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Bank Tujuan Transfer Bank Mandiri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bank_tujuan_transfer_bankmandiri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kode Transaksi Tujuan Transfer Bank Mandiri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kodetransaksi_tujuan_transfer_bankmandiri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Alergi PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_alergi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Referensi Prognosa PCare".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pcare_cek_prognosa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Care Plan Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_careplan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[L]Kirim Medication Statement Satu Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satu_sehat_kirim_medicationstatement='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Pasien Meninggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pasien_meninggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Kelahiran Bayi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kelahiran_bayi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Peminjaman Berkas RM".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","peminjaman_berkas='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Riwayat Perawatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","resume_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Diagnosa Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","diagnosa_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Retensi Data R.M.".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","retensi_rm='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Mutasi Berkas RM".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","mutasi_berkas='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Catatan Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Data HAIs".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","data_HAIs='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Klasifikasi Pasien Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","klasifikasi_pasien_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Instansi/Perusahaan Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","perusahaan_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Berkas Digital Perawatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","berkas_digital_perawatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Pengaduan/Chat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengaduan_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Insiden Keselamatan Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","insiden_keselamatan_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Suku/Bangsa Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","suku_bangsa='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Bahasa Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bahasa_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Golongan TNI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","golongan_tni='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Satuan TNI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satuan_tni='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Jabatan TNI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jabatan_tni='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Pangkat TNI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pangkat_tni='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Golongan POLRI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","golongan_polri='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Satuan POLRI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","satuan_polri='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Jabatan POLRI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jabatan_polri='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Pangkat POLRI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pangkat_polri='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Cacat Fisik".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","cacat_fisik='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Data Triase".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","data_triase_igd='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Master Triase Skala 1".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_triase_skala1='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Master Triase Skala 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_triase_skala2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Master Triase Skala 3".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_triase_skala3='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Master Triase Skala 4".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_triase_skala4='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Master Triase Skala 5".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_triase_skala5='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Master Triase Pemeriksaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_triase_pemeriksaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Master Triase Macam Kasus".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_triase_macamkasus='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Resume Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","data_resume_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Asuhan Gizi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","asuhan_gizi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Monitoring Asuhan Gizi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","monitoring_asuhan_gizi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ralan Umum".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Masalah Keperawatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_masalah_keperawatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Gigi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_gigi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Masalah Keperawatan Gigi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_masalah_keperawatan_gigi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ralan Kebidanan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_kebidanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ralan Bayi/Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_anak='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Masalah Keperawatan Bayi/Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_masalah_keperawatan_anak='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Imunisasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_imunisasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[M]Hemodialisa".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hemodialisa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]SOAP Perawatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","soap_perawatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Gizi Lanjut".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_gizi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ranap Kebidanan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_ranapkebidanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Umum".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Masalah Keperawatan Mata".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_masalah_keperawatan_mata='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ralan Mata".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_mata='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ranap Umum".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ranap Kandungan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ranap_kebidanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Kandungan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_kebidanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis IGD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_igd='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Bayi/Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_anak='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Fisioterapi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_fisioterapi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian MCU".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_mcu='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Riwayat Kamar Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","riwayat_kamar_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Uji Fungsi/Prosedur KFR".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","uji_fungsi_kfr='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Hapus Berkas Digital Perawatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hapus_berkas_digital_perawatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Gabungkan Data RM".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","gabung_rm='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Masalah Keperawatan IGD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_masalah_keperawatan_igd='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan IGD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_igd='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ranap Umum".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Rencana Keperawatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_rencana_keperawatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Rencana Keperawatan Bayi/Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_rencana_keperawatan_anak='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]SOAP Ralan Anggota POLRI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","soap_ralan_polri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]SOAP Ranap Anggota POLRI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","soap_ranap_polri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Observasi IGD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_observasi_igd='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Observasi Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_observasi_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Observasi Ranap Kebidanan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_observasi_ranap_kebidanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Observasi Ranap Post Partum".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_observasi_ranap_postpartum='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan THT".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_tht='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Psikologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_psikologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Psikiatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_psikiatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Penyakit Dalam".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_penyakit_dalam='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Mata".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_mata='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Neurologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_neurologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Orthopedi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_orthopedi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Bedah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_bedah='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]SOAP Ralan Anggota TNI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","soap_ralan_tni='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]SOAP Ranap Anggota TNI".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","soap_ranap_tni='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Keperawatan Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_keperawatan_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Rencana Keperawatan Gigi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_rencana_keperawatan_gigi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Rencana Keperawatan Mata".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_rencana_keperawatan_mata='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Rencana Keperawatan IGD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_rencana_keperawatan_igd='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Masalah Keperawatan Psikiatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_masalah_keperawatan_psikiatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Rencana Keperawatan Psikiatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_rencana_keperawatan_psikiatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ralan Psikiatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_psikiatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Pemantauan PEWS Pasien Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemantauan_pews_anak='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Template Hasil Radiologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","template_hasil_radiologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Template Pemeriksaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","template_pemeriksaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Pre Operasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_pre_operasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Pre Anestesi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_pre_anestesi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Perencanaan Pemulangan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","perencanaan_pemulangan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Lanjutan Resiko Jatuh Dewasa".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_lanjutan_resiko_jatuh_dewasa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Lanjutan Risiko Jatuh Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_lanjutan_resiko_jatuh_anak='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Geriatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_geriatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Tambahan Pasien Geriatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_tambahan_pasien_geriatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Nutrisi Pasien Dewasa".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_nutrisi_dewasa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Nutrisi Pasien Lansia".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_nutrisi_lansia='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Hasil USG Kandungan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hasil_pemeriksaan_usg='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Nutrisi Pasien Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_nutrisi_anak='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Konseling Farmasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","konseling_farmasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Pelayanan Informasi Obat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pelayanan_informasi_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Jawaban PIO Apoteker".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jawaban_pio_apoteker='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Transfer Pasien Antar Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","transfer_pasien_antar_ruang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Cek GDS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_cek_gds='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Check List Pre Operasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","checklist_pre_operasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Sign-In Sebelum Anestesi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","signin_sebelum_anestesi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Time-Out Sebelum Insisi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","timeout_sebelum_insisi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Sign-Out Sebelum Menutup Luka".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","signout_sebelum_menutup_luka='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Check List Post Operasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","checklist_post_operasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Rekonsiliasi Obat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","rekonsiliasi_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Konfirmasi Rekonsiliasi Obat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","konfirmasi_rekonsiliasi_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Pasien Terminal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_pasien_terminal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Monitoring Reaksi Tranfusi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","monitoring_reaksi_tranfusi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Korban Kekerasan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_korban_kekerasan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Lanjutan Risiko Jatuh Lansia".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_lanjutan_resiko_jatuh_lansia='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Pasien Penyakit Menular".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_pasien_penyakit_menular='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Manajer Pelayanan Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","mpp_skrining='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Edukasi Pasien & Keluarga Rawat Jalan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","edukasi_pasien_keluarga_rj='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Pemantauan EWS Pasien Dewasa".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemantauan_pews_dewasa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Tambahan Bunuh Diri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_tambahan_bunuh_diri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Tambahan Perilaku Kekerasan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_tambahan_perilaku_kekerasan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Tambahan Melarikan Diri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_tambahan_beresiko_melarikan_diri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Bedah Mulut".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_bedah_mulut='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Pasien Keracunan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_pasien_keracunan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Pemantauan MEOWS Pasien Obstetri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemantauan_meows_obstetri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan ADIME Gizi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_adime_gizi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ralan Geriatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_ralan_geriatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Masalah Keperawatan Geriatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_masalah_keperawatan_geriatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Rencana Keperawatan Geriatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_rencana_keperawatan_geriatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Check List Kriteria Masuk HCU".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","checklist_kriteria_masuk_hcu='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Check List Kriteria Keluar HCU".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","checklist_kriteria_keluar_hcu='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Risiko Dekubitus".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_risiko_dekubitus='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Template Laporan Operasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","template_laporan_operasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Dokumentasi Tindakan ESWL".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hasil_tindakan_eswl='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Check List Kriteria Masuk ICU".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","checklist_kriteria_masuk_icu='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Check List Kriteria Keluar ICU".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","checklist_kriteria_keluar_icu='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Follow Up DBD".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","follow_up_dbd='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Lanjutan Risiko Jatuh Neonatus".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_risiko_jatuh_neonatus='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Lanjutan Risiko Jatuh Geriatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_lanjutan_resiko_jatuh_geriatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Pemantauan EWS Pasien Neonatus".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pemantauan_ews_neonatus='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Kulit & Kelamin".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_kulit_kelamin='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Pasien Hemodialisa".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_medis_hemodialisa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Level Kecemasan Ranap Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_level_kecemasan_ranap_anak='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Lanjutan Risiko Jatuh Psikiatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_lanjutan_resiko_jatuh_psikiatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Lanjutan Skrining Fungsional".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_lanjutan_skrining_fungsional='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Fisik & Rehabilitasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_medis_ralan_rehab_medik='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Laporan Anestesi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","laporan_anestesi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis IGD Psikiatri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_medis_ralan_gawat_darurat_psikiatri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Ulang Nyeri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_ulang_nyeri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Terapi Wicara".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_terapi_wicara='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Pengkajian Restrain".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengkajian_restrain='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Medis Ralan Paru".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_medis_ralan_paru='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Keperawatan Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_keperawatan_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Persalinan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_persalinan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skor Aldrette Pasca Anestesi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skor_aldrette_pasca_anestesi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skor Steward Pasca Anestesi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skor_steward_pasca_anestesi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skor Bromage Pasca Anestesi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skor_bromage_pasca_anestesi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Pre Induksi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_pre_induksi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Hasil USG Urologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hasil_usg_urologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Hasil USG Gynecologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hasil_usg_gynecologi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Hasil USG Neonatus".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hasil_usg_neonatus='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Hasil Pemeriksaan EKG".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hasil_pemeriksaan_ekg='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Hasil Endoskopi Faring/Laring".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hasil_endoskopi_faring_laring='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penatalaksanaan Terapi Okupasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penatalaksanaan_terapi_okupasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Hasil Endoskopi Hidung".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hasil_endoskopi_hidung='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Masalah Keperawatan Neonatus".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_masalah_keperawatan_neonatus='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Master Rencana Keperawatan Neonatus".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_rencana_keperawatan_neonatus='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ranap Neonatus".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_ranap_neonatus='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Hasil Endoskopi Telinga".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","hasil_endoskopi_telinga='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Pasien Imunitas Rendah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_pasien_imunitas_rendah='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Keseimbangan Cairan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","balance_cairan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Observasi CHBP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_observasi_chbp='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Observasi Induksi Persalinan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_observasi_induksi_persalinan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Konsultasi Medik".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","konsultasi_medik='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Jawaban Konsultasi Medik".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jawaban_konsultasi_medik='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Merokok Usia Sekolah & Remaja".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_perilaku_merokok_sekolah_remaja='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Kekerasan Pada Perempuan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_kekerasan_pada_perempuan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Obesitas".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_obesitas='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Risiko Kanker Payudara".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_risiko_kanker_payudara='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Risiko Kanker Paru".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_risiko_kanker_paru='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining TBC".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_tbc='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Kesehatan Gigi & Mulut Remaja".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_kesehatan_gigi_mulut_remaja='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Penilaian Awal Keperawatan Ranap Bayi/Anak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penilaian_awal_keperawatan_ranap_bayi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Observasi Restrain Nonfarmakologi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_observasi_restrain_nonfarma='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Observasi Ventilator".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_observasi_ventilator='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Catatan Anestesi-Sedasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","catatan_anestesi_sedasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining PUMA".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_puma='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Adiksi Nikotin".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_adiksi_nikotin='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Thalassemia".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_thalassemia='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Instrumen SDQ".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_instrumen_sdq='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Instrumen SRQ".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_instrumen_srq='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Checklist Pemberian Fibrinolitik".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","checklist_pemberian_fibrinolitik='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[M]Skrining Kanker Kolorektal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skrining_kanker_kolorektal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[N]Pengambilan BHP Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengambilan_utd2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[N]BHP Medis Rusak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","utd_medis_rusak='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[N]Pengambilan BHP Non Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengambilan_penunjang_utd2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[N]BHP Non Medis Rusak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","utd_penunjang_rusak='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[N]Donor Darah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","utd_donor='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[N]Cekal Darah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","utd_cekal_darah='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[N]Komponen Darah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","utd_komponen_darah='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[N]Stok Darah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","utd_stok_darah='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[N]Pemisahan Darah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","utd_pemisahan_darah='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[N]Penyerahan Darah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","utd_penyerahan_darah='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[N]Data Pendonor".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","utd_pendonor='"+tbUser.getValueAt(i,2).toString()+"'");
            }
        }
    }
    
    private void Simpan3() {
        for(i=0;i<tbUser.getRowCount();i++){ 
            if("[O]Registrasi Per Poli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_poli='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Dokter".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_perdokter='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Pekerjaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_perpekerjaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Pendidikan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_perpendidikan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Demografi Registrasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_demografi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Reg Lama Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_statusdaftartahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Reg Baru Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_statusdaftartahun2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Reg Lama Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_statusdaftarbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Reg Baru Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_statusdaftarbulan2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Reg Lama Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_statusdaftartanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Reg Baru Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_statusdaftartanggal2='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Batal Periksa Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_statusbataltahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Batal Periksa Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_statusbatalbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Batal Periksa Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_statusbataltanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Cara Bayar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_percarabayar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Ranap Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_ranaptahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Lab Ralan Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_lab_ralantahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Rad Ralan Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_rad_ralantahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Perujuk".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_per_perujuk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Lab Ralan Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_lab_ralanbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Rad Ralan Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_rad_ralanbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Lab Ralan Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_lab_ralanhari='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Rad Ralan Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_rad_ralanhari='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kejadian IKP Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_ikp_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kejadian IKP Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_ikp_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kejadian IKP Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_ikp_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kejadian IKP Per Jenis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_ikp_jenis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kejadian IKP Per Dampak".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_ikp_dampak='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Agama".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_per_agama='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Umur".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_per_umur='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Suku/Bangsa".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_suku='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Bahasa".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_bahasa='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Registrasi Per Cacat Fisik".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_per_cacat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Periode Laporan TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_periodelaporan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Rujukan TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_rujukan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Riwayat TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_riwayat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Tipe Diagnosis TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_tipediagnosis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Status HIV TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_statushiv='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Skoring Anak TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_skoringanak='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Konfirmasi Skoring 5 TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_konfirmasiskoring5='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Konfirmasi Skoring 6 TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_konfirmasiskoring6='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Sumber Obat TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_sumberobat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Hasil Akhir Pengobatan TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_hasilakhirpengobatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Hasil Tes HIV TB".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tb_hasilteshiv='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pemakaian Air PDAM Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_air_pdam_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pemakaian Air PDAM Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_air_pdam_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Limbah B3 Padat Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_limbahb3_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Limbah B3 Padat Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_limbahb3_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Limbah Padat Domestik Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_limbahdomestik_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Limbah Padat Domestik Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_limbahdomestik_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Jenis Cidera".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_perjeniscidera='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Penyebab Kecelakaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_perpenyebab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Jenis Luka".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_perjenisluka='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Lokasi Kejadian".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_lokasikejadian='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Dampak Cidera".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_dampakcidera='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Jenis Pekerjaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_perjenispekerjaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]K3 Per Bagian Tubuh".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_k3_perbagiantubuh='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Ranap Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_ranapbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Ranap Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_ranaptanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Kunjungan Ranap Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kunjungan_ranap_peruang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pegawai Per Jenjang Jabatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_jenjang_jabatanpegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pegawai Per Bidang/Bagian".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_bidangpegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pegawai Per Departemen".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_departemenpegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pegawai Per Pendidikan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_pendidikanpegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pegawai Per Status WP".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_sttswppegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pegawai Per Status Kerja".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_sttskerjapegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Status Pulang Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_sttspulangranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Item Apotek Per Jenis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","item_apotek_jenis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Item Apotek Per Kategori".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","item_apotek_kategori='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Item Apotek Per Golongan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","item_apotek_golongan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Item Apotek Per Industri Farmasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","item_apotek_industrifarmasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pengajuan Aset Per Urgensi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_pengajuan_aset_urgensi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pengajuan Aset Per Status".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_pengajuan_aset_status='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pengajuan Aset Per Departemen".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_pengajuan_aset_departemen='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pegawai Per Kelompok Jabatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_kelompok_jabatanpegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pegawai Per Resiko Kerja".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_resiko_kerjapegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pegawai Per Emergency Index".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_emergency_indexpegawai='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Jumlah Inventaris Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_inventaris_ruang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Jumlah Inventaris Per Jenis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_inventaris_jenis='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pasien HAIs Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_HAIs_pasienbangsal='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Pasien HAIs Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_HAIs_pasienbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Laju HAIs VAP Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_HAIs_laju_vap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Laju HAIs IAD Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_HAIs_laju_iad='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Laju HAIs Plebitis Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_HAIs_laju_pleb='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Laju HAIs ISK Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_HAIs_laju_isk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Laju HAIs ILO Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_HAIs_laju_ilo='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Laju HAIs HAP Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_HAIs_laju_hap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Penerimaan Obat, Alkes & BHP Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penerimaan_obat_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Pemakaian Air Tanah Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_air_tanah_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Pemakaian Air Tanah Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_air_tanah_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[O]Hemodialisa Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_harian_hemodialisa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Hemodialisa Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_bulanan_hemodialisa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Hemodialisa Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_tahunan_hemodialisa='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Pasien Meninggal Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_bulanan_meninggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Jumlah Inventaris Per Kategori".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_inventaris_kategori='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Jumlah Inventaris Per Merk".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_inventaris_merk='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Jumlah Inventaris Per Produsen".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_inventaris_produsen='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Porsi Diet Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_porsidiet_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Porsi Diet Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_porsidiet_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Porsi Diet Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_porsidiet_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Porsi Diet Per Ruang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_porsidiet_perbangsal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Perbaikan Inventaris Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_perbaikan_inventaris_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Perbaikan Inventaris Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_perbaikan_inventaris_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Perbaikan Inventaris Per Tahun".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_perbaikan_inventaris_pertahun='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Perbaikan Inventaris Per Pelaksana & Status".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_perbaikan_inventaris_perpelaksana_status='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Limbah B3 Cair Per Tanggal".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_limbahb3cair_pertanggal='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[O]Limbah B3 Cair Per Bulan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","grafik_limbahb3cair_perbulan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Indeks Surat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_indeks='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Map Surat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_map='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Almari Surat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_almari='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Rak Surat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_rak='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Ruang Surat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_ruang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Klasifikasi Surat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_klasifikasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Status Surat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_status='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Sifat Surat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_sifat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Stts Balas Surat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_balas='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Surat Masuk".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_masuk='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[P]Surat Keluar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_keluar='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Keterangan Sakit".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_sakit='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Pengumuman E-Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengumuman_epasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Hamil".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_hamil='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Bebas Narkoba".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_bebas_narkoba='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Keterangan Covid".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_keterangan_covid='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Cuti Hamil".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_cuti_hamil='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Kontrol".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","skdp_bpjs='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Keterangan Rawat Inap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_keterangan_rawat_inap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Keterangan Sehat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_keterangan_sehat='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Keterangan Sakit Pihak 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_sakit_pihak_2='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Bebas TBC".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_bebas_tbc='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Keterangan Buta Warna".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_buta_warna='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Bebas Tato".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_bebas_tato='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Surat Kewaspadaan Kesehatan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_kewaspadaan_kesehatan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Persetujuan/Penolakan Tindakan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","persetujuan_penolakan_tindakan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Pulang Atas Permintaan Sendiri".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_pulang_atas_permintaan_sendiri='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Pernyataan Pasien Umum".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_pernyataan_pasien_umum='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Persetujuan Umum".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_persetujuan_umum='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Persetujuan Rawat Inap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","surat_persetujuan_rawat_inap='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Persetujuan Penundaan Pelayanan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","persetujuan_penundaan_pelayanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Master Menolak Anjuran Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","master_menolak_anjuran_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Penolakan Anjuran Medis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penolakan_anjuran_medis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[P]Template Persetujuan Penolakan Tindakan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","template_persetujuan_penolakan_tindakan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Ruang Perpustakaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ruang_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Kategori Koleksi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","kategori_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Jenis Koleksi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jenis_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Pengarang/Penulis".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","pengarang_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Penerbit Koleksi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","penerbit_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Koleksi Perpustakaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","koleksi_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Inventaris Perpustakaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","inventaris_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Pengaturan Peminjaman".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_peminjaman_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Denda Perpustakaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","denda_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Anggota Perpustakaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","anggota_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Peminjaman Koleksi Perpustakaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","peminjaman_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Bayar Denda Perpustakaan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","bayar_denda_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[Q]Data Koleksi Ebook".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ebook_perpustakaan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Suplier Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_suplier='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Jenis Barang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_jenis='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Barang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Stok Opname Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","stok_opname_toko='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Riwayat Barang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_riwayat_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Surat Pemesanan Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_surat_pemesanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Pengajuan Barang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_pengajuan_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Penerimaan Barang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_penerimaan_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Pengadaan Barang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_pengadaan_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Member Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_member='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Penjualan Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_penjualan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Piutang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_piutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Retur Ke Suplier Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_retur_beli='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Pendapatan Harian Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_pendapatan_harian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Bayar Piutang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_bayar_piutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Piutang Harian Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_piutang_harian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Penjualan Harian Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_penjualan_harian='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Hutang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_hutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Bayar Pesan Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_bayar_pemesanan='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Sirkulasi Barang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_sirkulasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Retur Jual Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_retur_jual='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Retur Jual Piutang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_retur_piutang='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Sirkulasi Barang Toko 2".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_sirkulasi2='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[R]Keuntungan Barang Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_keuntungan_barang='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[S]Ket Pengeluaran Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_pengeluaran_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Ket Penghasilan Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_penghasilan_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Ukuran Rumah Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_ukuran_rumah_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Dinding Rumah Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_dinding_rumah_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Lantai Rumah Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_lantai_rumah_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Atap Rumah Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_atap_rumah_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Kepemilikan Rumah Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_kepemilikan_rumah_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Kamar Mandi Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_kamar_mandi_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Dapur Rumah Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_dapur_rumah_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Kursi Rumah Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_kursi_rumah_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Kategori PHBS Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_kategori_phbs_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Elektronik Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_elektronik_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Ternak Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_ternak_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Jenis Simpanan Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_jenis_simpanan_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Kategori Asnaf Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_kategori_asnaf_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[S]Patologis Penerima Dankes".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","zis_patologis_penerima_dankes='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[T]Set P.J. Unit Penunjang".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","setup_pjlab='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Oto Lokasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","setup_otolokasi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Kamar Inap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","setup_jam_kamin='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Embalase & Tuslah".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","setup_embalase='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Tracer Login".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","tracer_login='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Display Antrian Registrasi & Poli".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","display='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Harga Obat".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_harga_obat='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Penggunaan Tarif".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_penggunaan_tarif='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Oto Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_oto_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Biaya Harian Kamar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","biaya_harian='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Biaya Masuk Sekali".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","biaya_masuk_sekali='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set RM".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_no_rm='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Harga Obat Ralan".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_harga_obat_ralan='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Harga Obat Ranap".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_harga_obat_ranap='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Billing".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_nota='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Closing Kasir".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","closing_kasir='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Keterlambatan Presensi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","keterlambatan_presensi='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Harga Kamar".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_harga_kamar='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Set Input Parsial".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","set_input_parsial='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Display Antrian Apotek".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","display_apotek='"+tbUser.getValueAt(i,2).toString()+"'");
            }

            if("[T]Password BPJS".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","password_asuransi='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[T]Set Harga Toko".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","toko_set_harga='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[T]Jam Diet Pasien".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","jam_diet_pasien='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[T]Ruang Operasi".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","ruang_ok='"+tbUser.getValueAt(i,2).toString()+"'");
            }
            
            if("[T]Integrasi Khanza Health Services".equals(tbUser.getValueAt(i,1).toString())){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')","integrasi_khanza_health_services='"+tbUser.getValueAt(i,2).toString()+"'");
            }
        }
        JOptionPane.showMessageDialog(null,"Proses update hak akses selesai..!!");
    }

}
