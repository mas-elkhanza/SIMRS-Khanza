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
public final class akses {
    private static final Connection koneksi=koneksiDB.condb();
    private static PreparedStatement ps,ps2;
    private static ResultSet rs,rs2;
    
    private static String kode="",kdbangsal="",alamatip="",namars="",alamatrs="",kabupatenrs="",propinsirs="",kontakrs="",emailrs="",form="",namauser="",kode_ppk=""; 
    private static int jml1=0,jml2=0,lebar=0,tinggi=0;
    private static boolean aktif=false,admin=false,user=false,vakum=false,aplikasi=false,penyakit=false,obat_penyakit=false,dokter=false,jadwal_praktek=false,petugas=false,pasien=false,registrasi=false,
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
            payment_point=false,bpjs_cek_nomor_rujukan=false,icd9=false,darurat_stok=false,retensi_rm=false,temporary_presensi=false,jurnal_harian=false,
            sirkulasi_obat2=false,edit_registrasi=false,bpjs_referensi_diagnosa=false,bpjs_referensi_poli=false,industrifarmasi=false,harian_js=false,bulanan_js=false,
            harian_paket_bhp=false,bulanan_paket_bhp=false,piutang_pasien2=false,bpjs_referensi_faskes=false,bpjs_sep=false,pengambilan_utd=false,tarif_utd=false,
            pengambilan_utd2=false,utd_medis_rusak=false,pengambilan_penunjang_utd=false,pengambilan_penunjang_utd2=false,utd_penunjang_rusak=false,
            suplier_penunjang=false,utd_donor=false,bpjs_monitoring_klaim=false,utd_cekal_darah=false,utd_komponen_darah=false,utd_stok_darah=false,
            utd_pemisahan_darah=false,harian_kamar=false,rincian_piutang_pasien=false,keuntungan_beri_obat_nonpiutang=false,reklasifikasi_ralan=false,
            reklasifikasi_ranap=false,utd_penyerahan_darah=false,hutang_obat=false,riwayat_obat_alkes_bhp=false,sensus_harian_poli=false,rl4a=false,
            aplicare_referensi_kamar=false,aplicare_ketersediaan_kamar=false,inacbg_klaim_baru_otomatis=false,inacbg_klaim_baru_manual=false,inacbg_coder_nik=false,
            mutasi_berkas=false,akun_piutang=false,harian_kso=false,bulanan_kso=false,harian_menejemen=false,bulanan_menejemen=false,inhealth_cek_eligibilitas=false,
            inhealth_referensi_jenpel_ruang_rawat=false,inhealth_referensi_poli=false,inhealth_referensi_faskes=false,inhealth_sjp=false,piutang_ralan=false,
            piutang_ranap=false,detail_piutang_penjab=false,lama_pelayanan_ralan=false,catatan_pasien=false,rl4b=false,rl4asebab=false,rl4bsebab=false,
            data_HAIs=false,harian_HAIs=false,bulanan_HAIs=false,hitung_bor=false,perusahaan_pasien=false,resep_dokter=false,lama_pelayanan_apotek=false,
            hitung_alos=false,detail_tindakan=false,rujukan_poli_internal=false,rekap_poli_anak=false,grafik_kunjungan_poli=false,grafik_kunjungan_perdokter=false,
            grafik_kunjungan_perpekerjaan=false,grafik_kunjungan_perpendidikan=false,grafik_kunjungan_pertahun=false,berkas_digital_perawatan=false,
            penyakit_menular_ranap=false,penyakit_menular_ralan=false,grafik_kunjungan_perbulan=false,grafik_kunjungan_pertanggal=false,grafik_kunjungan_demografi=false,
            grafik_kunjungan_statusdaftartahun=false,grafik_kunjungan_statusdaftartahun2=false,grafik_kunjungan_statusdaftarbulan=false,grafik_kunjungan_statusdaftarbulan2=false,
            grafik_kunjungan_statusdaftartanggal=false,grafik_kunjungan_statusdaftartanggal2=false,grafik_kunjungan_statusbataltahun=false,grafik_kunjungan_statusbatalbulan=false,
            pcare_cek_penyakit=false,grafik_kunjungan_statusbataltanggal=false,kategori_barang=false,golongan_barang=false,pemberian_obat_pertanggal=false,
            penjualan_obat_pertanggal=false,pcare_cek_kesadaran=false,pembatalan_periksa_dokter=false,pembayaran_per_unit=false,rekap_pembayaran_per_unit=false,
            grafik_kunjungan_percarabayar=false,ipsrs_pengadaan_pertanggal=false,ipsrs_stokkeluar_pertanggal=false,grafik_kunjungan_ranaptahun=false,
            pcare_cek_rujukan=false,grafik_lab_ralantahun=false,grafik_rad_ralantahun=false,cek_entry_ralan=false,inacbg_klaim_baru_manual2=false,
            permintaan_medis=false,rekap_permintaan_medis=false,surat_pemesanan_medis=false,permintaan_non_medis=false,rekap_permintaan_non_medis=false,
            surat_pemesanan_non_medis=false,grafik_per_perujuk=false,bpjs_cek_prosedur=false,bpjs_cek_kelas_rawat=false,bpjs_cek_dokter=false,
            bpjs_cek_spesialistik=false,bpjs_cek_ruangrawat=false,bpjs_cek_carakeluar=false,bpjs_cek_pasca_pulang=false,detail_tindakan_okvk=false,
            billing_parsial=false,bpjs_cek_nomor_rujukan_rs=false,bpjs_cek_rujukan_kartu_pcare=false,bpjs_cek_rujukan_kartu_rs=false,akses_depo_obat=false,
            bpjs_rujukan_keluar=false,grafik_lab_ralanbulan=false,pengeluaran_stok_apotek=false,grafik_rad_ralanbulan=false,detailjmdokter2=false,
            pengaduan_pasien=false,grafik_lab_ralanhari=false,grafik_rad_ralanhari=false,sensus_harian_ralan=false,metode_racik=false,pembayaran_akun_bayar=false,
            pengguna_obat_resep=false,rekap_pemesanan=false,master_berkas_pegawai=false,berkas_kepegawaian=false,riwayat_jabatan=false,riwayat_pendidikan=false,
            riwayat_naik_gaji=false,kegiatan_ilmiah=false,riwayat_penghargaan=false,riwayat_penelitian=false,penerimaan_non_medis=false,bayar_pesan_non_medis=false,
            hutang_barang_non_medis=false,rekap_pemesanan_non_medis=false,insiden_keselamatan=false,insiden_keselamatan_pasien=false,grafik_ikp_pertahun=false,
            grafik_ikp_perbulan=false,grafik_ikp_pertanggal=false,riwayat_data_batch=false,grafik_ikp_jenis=false,grafik_ikp_dampak=false,piutang_akun_piutang=false,
            grafik_kunjungan_per_agama=false,grafik_kunjungan_per_umur=false,suku_bangsa=false,bahasa_pasien=false,golongan_tni=false,satuan_tni=false,
            jabatan_tni=false,pangkat_tni=false,golongan_polri=false,satuan_polri=false,jabatan_polri=false,pangkat_polri=false,cacat_fisik=false,
            grafik_kunjungan_suku=false,grafik_kunjungan_bahasa=false,booking_operasi=false,mapping_poli_bpjs=false,grafik_kunjungan_per_cacat=false,
            barang_cssd=false,skdp_bpjs=false,booking_registrasi=false,bpjs_cek_propinsi=false,bpjs_cek_kabupaten=false,bpjs_cek_kecamatan=false,
            bpjs_cek_dokterdpjp=false,bpjs_cek_riwayat_rujukanrs=false,bpjs_cek_tanggal_rujukan=false,permintaan_lab=false,permintaan_radiologi=false,
            surat_indeks=false,surat_map=false,surat_almari=false,surat_rak=false,surat_ruang=false,surat_klasifikasi=false,surat_status=false,
            surat_sifat=false,surat_balas=false,surat_masuk=false,pcare_cek_dokter=false,pcare_cek_poli=false,pcare_cek_provider=false,
            pcare_cek_statuspulang=false,pcare_cek_spesialis=false,pcare_cek_subspesialis=false,pcare_cek_sarana=false,pcare_cek_khusus=false,
            pcare_cek_obat=false,pcare_cek_tindakan=false,pcare_cek_faskessubspesialis=false,pcare_cek_faskesalihrawat=false,
            pcare_cek_faskesthalasemia=false,pcare_mapping_obat=false,pcare_mapping_tindakan=false,pcare_club_prolanis=false,
            pcare_mapping_poli=false,pcare_kegiatan_kelompok=false,pcare_mapping_tindakan_ranap=false,pcare_peserta_kegiatan_kelompok=false,
            sirkulasi_obat3=false,bridging_pcare_daftar=false,pcare_mapping_dokter=false,ranap_per_ruang=false,penyakit_ranap_cara_bayar=false,
            anggota_militer_dirawat=false,set_input_parsial=false,lama_pelayanan_radiologi=false,lama_pelayanan_lab=false,bpjs_cek_sep=false,
            catatan_perawatan=false,surat_keluar=false,kegiatan_farmasi=false,stok_opname_logistik=false,sirkulasi_non_medis=false,
            rekap_lab_pertahun=false,perujuk_lab_pertahun=false,rekap_radiologi_pertahun=false,perujuk_radiologi_pertahun=false,
            jumlah_porsi_diet=false,jumlah_macam_diet=false,payment_point2=false,pembayaran_akun_bayar2=false,hapus_nota_salah=false,
            hais_perbangsal=false,ppn_obat=false,saldo_akun_perbulan=false,display_apotek=false,sisrute_referensi_faskes=false,
            sisrute_referensi_alasanrujuk=false,sisrute_referensi_diagnosa=false,sisrute_rujukan_masuk=false,sisrute_rujukan_keluar=false,
            bpjs_cek_skdp=false,data_batch=false,kunjungan_permintaan_lab=false,kunjungan_permintaan_lab2=false,kunjungan_permintaan_radiologi=false,
            kunjungan_permintaan_radiologi2=false,pcare_pemberian_obat=false,pcare_pemberian_tindakan=false,pembayaran_akun_bayar3=false,
            password_asuransi=false,kemenkes_sitt=false,siranap_ketersediaan_kamar=false,grafik_tb_periodelaporan=false,grafik_tb_rujukan=false,
            grafik_tb_riwayat=false,grafik_tb_tipediagnosis=false,grafik_tb_statushiv=false,grafik_tb_skoringanak=false,grafik_tb_konfirmasiskoring5=false,
            grafik_tb_konfirmasiskoring6=false,grafik_tb_sumberobat=false,grafik_tb_hasilakhirpengobatan=false,grafik_tb_hasilteshiv=false,
            kadaluarsa_batch=false,sisa_stok=false,obat_per_resep=false,pemakaian_air_pdam=false,limbah_b3_medis=false,grafik_air_pdam_pertanggal=false,
            grafik_air_pdam_perbulan=false,grafik_limbahb3_pertanggal=false,grafik_limbahb3_perbulan=false,limbah_domestik=false,
            grafik_limbahdomestik_pertanggal=false,grafik_limbahdomestik_perbulan=false,mutu_air_limbah=false,pest_control=false,ruang_perpustakaan=false,
            kategori_perpustakaan=false,jenis_perpustakaan=false,pengarang_perpustakaan=false,penerbit_perpustakaan=false,koleksi_perpustakaan=false,
            inventaris_perpustakaan=false,set_peminjaman_perpustakaan=false,denda_perpustakaan=false,anggota_perpustakaan=false,
            peminjaman_perpustakaan=false,bayar_denda_perpustakaan=false,ebook_perpustakaan=false,jenis_cidera_k3rs=false,penyebab_k3rs=false,
            jenis_luka_k3rs=false,lokasi_kejadian_k3rs=false,dampak_cidera_k3rs=false,jenis_pekerjaan_k3rs=false,bagian_tubuh_k3rs=false,
            peristiwa_k3rs=false,grafik_k3_pertahun=false,grafik_k3_perbulan=false,grafik_k3_pertanggal=false,grafik_k3_perjeniscidera=false,
            grafik_k3_perpenyebab=false,grafik_k3_perjenisluka=false,grafik_k3_lokasikejadian=false,grafik_k3_dampakcidera=false,
            grafik_k3_perjenispekerjaan=false,grafik_k3_perbagiantubuh=false,jenis_cidera_k3rstahun=false,penyebab_k3rstahun=false,
            jenis_luka_k3rstahun=false,lokasi_kejadian_k3rstahun=false,dampak_cidera_k3rstahun=false,jenis_pekerjaan_k3rstahun=false,
            bagian_tubuh_k3rstahun=false,sekrining_rawat_jalan=false,bpjs_histori_pelayanan=false,rekap_mutasi_berkas=false,
            skrining_ralan_pernapasan_pertahun=false,pengajuan_barang_medis=false,pengajuan_barang_nonmedis=false,grafik_kunjungan_ranapbulan=false,
            grafik_kunjungan_ranaptanggal=false,grafik_kunjungan_ranap_peruang=false,kunjungan_bangsal_pertahun=false,grafik_jenjang_jabatanpegawai=false,
            grafik_bidangpegawai=false,grafik_departemenpegawai=false,grafik_pendidikanpegawai=false,grafik_sttswppegawai=false,
            grafik_sttskerjapegawai=false,grafik_sttspulangranap=false,kip_pasien_ranap=false,kip_pasien_ralan=false,bpjs_mapping_dokterdpjp=false,
            data_triase_igd=false,master_triase_skala1=false,master_triase_skala2=false,master_triase_skala3=false,master_triase_skala4=false,
            master_triase_skala5=false,master_triase_pemeriksaan=false,master_triase_macamkasus=false,rekap_permintaan_diet=false,daftar_pasien_ranap=false,
            daftar_pasien_ranaptni=false,pengajuan_asetinventaris=false,item_apotek_jenis=false,item_apotek_kategori=false,item_apotek_golongan=false,
            item_apotek_industrifarmasi=false,obat10_terbanyak_poli=false,grafik_pengajuan_aset_urgensi=false,grafik_pengajuan_aset_status=false,
            grafik_pengajuan_aset_departemen=false,rekap_pengajuan_aset_departemen=false,grafik_kelompok_jabatanpegawai=false,grafik_resiko_kerjapegawai=false,
            grafik_emergency_indexpegawai=false,grafik_inventaris_ruang=false,harian_HAIs2=false,grafik_inventaris_jenis=false,data_resume_pasien=false,
            perkiraan_biaya_ranap=false,rekap_obat_poli=false,rekap_obat_pasien=false,grafik_HAIs_pasienbangsal=false,grafik_HAIs_pasienbulan=false,
            permintaan_perbaikan_inventaris=false,grafik_HAIs_laju_vap=false,grafik_HAIs_laju_iad=false,grafik_HAIs_laju_pleb=false,grafik_HAIs_laju_isk=false,
            grafik_HAIs_laju_ilo=false,grafik_HAIs_laju_hap=false,inhealth_mapping_poli=false,inhealth_mapping_dokter=false,inhealth_mapping_tindakan_ralan=false,
            inhealth_mapping_tindakan_ranap=false,inhealth_mapping_tindakan_radiologi=false,inhealth_mapping_tindakan_laborat=false,
            inhealth_mapping_tindakan_operasi=false,hibah_obat_bhp=false,asal_hibah=false,asuhan_gizi=false,inhealth_kirim_tagihan=false,
            sirkulasi_obat4=false,sirkulasi_obat5=false,sirkulasi_non_medis2=false,monitoring_asuhan_gizi=false,penerimaan_obat_perbulan=false,
            rekap_kunjungan=false,surat_sakit=false,penilaian_awal_keperawatan_ralan=false,permintaan_diet=false,master_masalah_keperawatan=false,
            pengajuan_cuti=false,kedatangan_pasien=false,utd_pendonor=false,toko_suplier=false,toko_jenis=false,toko_set_harga=false,
            toko_barang=false,penagihan_piutang_pasien=false,akun_penagihan_piutang=false,stok_opname_toko=false,toko_riwayat_barang=false,
            toko_surat_pemesanan=false,toko_pengajuan_barang=false,toko_penerimaan_barang=false,toko_pengadaan_barang=false,toko_hutang=false,
            toko_bayar_pemesanan=false,toko_member=false,toko_penjualan=false,registrasi_poli_per_tanggal=false,toko_piutang=false,toko_retur_beli=false,
            ipsrs_returbeli=false,ipsrs_riwayat_barang=false,pasien_corona=false,toko_pendapatan_harian=false,diagnosa_pasien_corona=false,
            perawatan_pasien_corona=false,penilaian_awal_keperawatan_gigi=false,master_masalah_keperawatan_gigi=false,toko_bayar_piutang=false,
            toko_piutang_harian=false,toko_penjualan_harian=false,deteksi_corona=false,penilaian_awal_keperawatan_kebidanan=false,pengumuman_epasien=false,
            surat_hamil=false,set_tarif_online=false,booking_periksa=false,toko_sirkulasi=false,toko_retur_jual=false,toko_retur_piutang=false,
            toko_sirkulasi2=false,toko_keuntungan_barang=false,zis_pengeluaran_penerima_dankes=false,zis_penghasilan_penerima_dankes=false,
            zis_ukuran_rumah_penerima_dankes=false,zis_dinding_rumah_penerima_dankes=false,zis_lantai_rumah_penerima_dankes=false,
            zis_atap_rumah_penerima_dankes=false,zis_kepemilikan_rumah_penerima_dankes=false,zis_kamar_mandi_penerima_dankes=false,
            zis_dapur_rumah_penerima_dankes=false,zis_kursi_rumah_penerima_dankes=false,zis_kategori_phbs_penerima_dankes=false,
            zis_elektronik_penerima_dankes=false,zis_ternak_penerima_dankes=false,zis_jenis_simpanan_penerima_dankes=false,penilaian_awal_keperawatan_anak=false,
            zis_kategori_asnaf_penerima_dankes=false,master_masalah_keperawatan_anak=false,master_imunisasi=false,zis_patologis_penerima_dankes=false,
            pcare_cek_kartu=false,surat_bebas_narkoba=false,surat_keterangan_covid=false,pemakaian_air_tanah=false,grafik_air_tanah_pertanggal=false,
            grafik_air_tanah_perbulan=false,lama_pelayanan_poli=false,hemodialisa=false,grafik_harian_hemodialisa=false,grafik_bulanan_hemodialisa=false,
            grafik_tahunan_hemodialisa=false,grafik_bulanan_meninggal=false,laporan_tahunan_irj=false,perbaikan_inventaris=false,surat_cuti_hamil=false,
            permintaan_stok_obat_pasien=false,pemeliharaan_inventaris=false,klasifikasi_pasien_ranap=false,bulanan_klasifikasi_pasien_ranap,
            harian_klasifikasi_pasien_ranap=false,klasifikasi_pasien_perbangsal=false,soap_perawatan=false,klaim_rawat_jalan=false,
            skrining_gizi=false,lama_penyiapan_rm=false,dosis_radiologi=false,demografi_umur_kunjungan=false,jam_diet_pasien=false,
            rvu_bpjs=false,verifikasi_penerimaan_farmasi=false,verifikasi_penerimaan_logistik=false,pemeriksaan_lab_pa=false,ringkasan_pengajuan_obat=false,
            ringkasan_pemesanan_obat=false,ringkasan_pengadaan_obat=false,ringkasan_penerimaan_obat=false,ringkasan_hibah_obat=false,
            ringkasan_penjualan_obat=false,ringkasan_beri_obat=false,ringkasan_piutang_obat=false,ringkasan_stok_keluar_obat=false,
            ringkasan_retur_suplier_obat=false,ringkasan_retur_pembeli_obat=false,penilaian_awal_keperawatan_ranapkebidanan=false,
            ringkasan_pengajuan_nonmedis=false,ringkasan_pemesanan_nonmedis=false,ringkasan_pengadaan_nonmedis=false,ringkasan_penerimaan_nonmedis=false,
            ringkasan_stokkeluar_nonmedis=false,ringkasan_returbeli_nonmedis=false,omset_penerimaan=false,validasi_penagihan_piutang=false,
            permintaan_ranap=false,bpjs_diagnosa_prb=false,bpjs_obat_prb=false,bpjs_surat_kontrol=false,penggunaan_bhp_ok=false,surat_keterangan_rawat_inap=false,
            surat_keterangan_sehat=false,pendapatan_per_carabayar=false,akun_host_to_host_bank_jateng=false,pembayaran_bank_jateng=false,
            bpjs_surat_pri=false,ringkasan_tindakan=false,lama_pelayanan_pasien=false,surat_sakit_pihak_2=false,tagihan_hutang_obat=false,
            referensi_mobilejkn_bpjs=false,batal_pendaftaran_mobilejkn_bpjs=false,lama_operasi=false,grafik_inventaris_kategori=false,grafik_inventaris_merk=false,
            grafik_inventaris_produsen=false,pengembalian_deposit_pasien=false,validasi_tagihan_hutang_obat=false,piutang_obat_belum_lunas=false,
            integrasi_briapi=false,pengadaan_aset_inventaris=false,akun_aset_inventaris=false,suplier_inventaris=false,penerimaan_aset_inventaris=false,
            bayar_pemesanan_iventaris=false,hutang_aset_inventaris=false,hibah_aset_inventaris=false,titip_faktur_non_medis=false,validasi_tagihan_non_medis=false,
            titip_faktur_aset=false,validasi_tagihan_aset=false,hibah_non_medis=false,pcare_alasan_tacc=false,resep_luar=false,surat_bebas_tbc=false,
            surat_buta_warna=false,surat_bebas_tato=false,surat_kewaspadaan_kesehatan=false,grafik_porsidiet_pertanggal=false,grafik_porsidiet_perbulan=false,
            grafik_porsidiet_pertahun=false,grafik_porsidiet_perbangsal=false,penilaian_awal_medis_ralan=false,master_masalah_keperawatan_mata=false,
            penilaian_awal_keperawatan_mata=false,penilaian_awal_medis_ranap=false,penilaian_awal_medis_ranap_kebidanan=false,penilaian_awal_medis_ralan_kebidanan=false,
            penilaian_awal_medis_igd=false,penilaian_awal_medis_ralan_anak=false,bpjs_referensi_poli_hfis=false,bpjs_referensi_dokter_hfis=false,
            bpjs_referensi_jadwal_hfis=false,penilaian_fisioterapi=false,bpjs_program_prb=false,bpjs_suplesi_jasaraharja=false,bpjs_data_induk_kecelakaan=false,
            bpjs_sep_internal=false,bpjs_klaim_jasa_raharja=false,bpjs_daftar_finger_print=false,bpjs_rujukan_khusus=false,pemeliharaan_gedung=false,
            grafik_perbaikan_inventaris_pertanggal=false,grafik_perbaikan_inventaris_perbulan=false,grafik_perbaikan_inventaris_pertahun=false,
            grafik_perbaikan_inventaris_perpelaksana_status=false,penilaian_mcu=false,peminjam_piutang=false,piutang_lainlain=false,cara_bayar=false,
            audit_kepatuhan_apd=false,bpjs_task_id=false,bayar_piutang_lain=false,pembayaran_akun_bayar4=false,stok_akhir_farmasi_pertanggal=false,
            riwayat_kamar_pasien=false,uji_fungsi_kfr=false,hapus_berkas_digital_perawatan=false,kategori_pengeluaran_harian=false,kategori_pemasukan_lain=false,
            pembayaran_akun_bayar5=false,ruang_ok=false,jasa_tindakan_pasien=false,telaah_resep=false,permintaan_resep_pulang=false,rekap_jm_dokter=false,
            status_data_rm=false,ubah_petugas_lab_pk=false,ubah_petugas_lab_pa=false,ubah_petugas_radiologi=false,gabung_norawat=false,gabung_rm=false,
            ringkasan_biaya_obat_pasien_pertanggal=false,master_masalah_keperawatan_igd=false,penilaian_awal_keperawatan_igd=false,bpjs_referensi_dpho_apotek=false,
            bpjs_referensi_poli_apotek=false,bayar_jm_dokter=false,bpjs_referensi_faskes_apotek=false,bpjs_referensi_spesialistik_apotek=false,
            pembayaran_briva=false,penilaian_awal_keperawatan_ranap=false,nilai_penerimaan_vendor_farmasi_perbulan=false,akun_bayar_hutang=false,
            master_rencana_keperawatan=false,laporan_tahunan_igd=false,obat_bhp_tidakbergerak=false,ringkasan_hutang_vendor_farmasi=false,
            nilai_penerimaan_vendor_nonmedis_perbulan=false,ringkasan_hutang_vendor_nonmedis=false,anggota_polri_dirawat=false,daftar_pasien_ranap_polri=false,
            soap_ralan_polri=false,soap_ranap_polri=false,laporan_penyakit_polri=false,master_rencana_keperawatan_anak=false,jumlah_pengunjung_ralan_polri=false,
            catatan_observasi_igd=false,catatan_observasi_ranap=false,catatan_observasi_ranap_kebidanan=false,catatan_observasi_ranap_postpartum=false,
            penilaian_awal_medis_ralan_tht=false,penilaian_psikologi=false,audit_cuci_tangan_medis=false,audit_pembuangan_limbah=false,ruang_audit_kepatuhan=false,
            audit_pembuangan_benda_tajam=false,audit_penanganan_darah=false,audit_pengelolaan_linen_kotor=false,audit_penempatan_pasien=false,
            audit_kamar_jenazah=false,audit_bundle_iadp=false,audit_bundle_ido=false,audit_fasilitas_kebersihan_tangan=false,audit_fasilitas_apd=false,
            audit_pembuangan_limbah_cair_infeksius=false,audit_sterilisasi_alat=false,penilaian_awal_medis_ralan_psikiatri=false,persetujuan_penolakan_tindakan=false,
            audit_bundle_isk=false,audit_bundle_plabsi=false,audit_bundle_vap=false,akun_host_to_host_bank_papua=false,pembayaran_bank_papua=false,
            penilaian_awal_medis_ralan_penyakit_dalam=false,penilaian_awal_medis_ralan_mata=false,penilaian_awal_medis_ralan_neurologi=false,sirkulasi_obat6=false,
            penilaian_awal_medis_ralan_orthopedi=false,penilaian_awal_medis_ralan_bedah=false,integrasi_khanza_health_services=false,soap_ralan_tni=false,
            soap_ranap_tni=false,jumlah_pengunjung_ralan_tni=false,laporan_penyakit_tni=false,catatan_keperawatan_ranap=false,master_rencana_keperawatan_gigi=false,
            master_rencana_keperawatan_mata=false,master_rencana_keperawatan_igd=false,master_masalah_keperawatan_psikiatri=false,master_rencana_keperawatan_psikiatri=false,
            penilaian_awal_keperawatan_psikiatri=false,pemantauan_pews_anak=false,surat_pulang_atas_permintaan_sendiri=false,template_hasil_radiologi=false,
            laporan_bulanan_irj=false,template_pemeriksaan=false,pemeriksaan_lab_mb=false,ubah_petugas_lab_mb=false,penilaian_pre_operasi=false,penilaian_pre_anestesi=false,
            perencanaan_pemulangan=false,penilaian_lanjutan_resiko_jatuh_dewasa=false,penilaian_lanjutan_resiko_jatuh_anak=false,penilaian_awal_medis_ralan_geriatri=false,
            penilaian_tambahan_pasien_geriatri=false,skrining_nutrisi_dewasa=false,skrining_nutrisi_lansia=false,hasil_pemeriksaan_usg=false,skrining_nutrisi_anak=false,
            akun_host_to_host_bank_jabar=false,pembayaran_bank_jabar=false,surat_pernyataan_pasien_umum=false,konseling_farmasi=false,pelayanan_informasi_obat=false,
            jawaban_pio_apoteker=false,surat_persetujuan_umum=false,transfer_pasien_antar_ruang=false,satu_sehat_referensi_dokter=false,satu_sehat_referensi_pasien=false,
            satu_sehat_mapping_departemen=false,satu_sehat_mapping_lokasi=false,satu_sehat_kirim_encounter=false,catatan_cek_gds=false,satu_sehat_kirim_condition=false,
            checklist_pre_operasi=false,satu_sehat_kirim_observationttv=false,signin_sebelum_anestesi=false,satu_sehat_kirim_procedure=false,operasi_per_bulan=false,
            timeout_sebelum_insisi=false,signout_sebelum_menutup_luka=false,dapur_barang=false,dapur_opname=false,satu_sehat_mapping_vaksin=false,dapur_suplier=false,
            satu_sehat_kirim_Immunization=false,checklist_post_operasi=false,dapur_pembelian=false,dapur_stok_keluar=false,dapur_riwayat_barang=false,permintaan_dapur=false,
            rekonsiliasi_obat=false,biaya_pengadaan_dapur=false,rekap_pengadaan_dapur=false,kesling_limbah_b3medis_cair=false,grafik_limbahb3cair_pertanggal=false,
            grafik_limbahb3cair_perbulan=false,rekap_biaya_registrasi=false,konfirmasi_rekonsiliasi_obat=false,satu_sehat_kirim_clinicalimpression=false,
            penilaian_pasien_terminal=false,surat_persetujuan_rawat_inap=false,monitoring_reaksi_tranfusi=false,penilaian_korban_kekerasan=false,
            penilaian_lanjutan_resiko_jatuh_lansia=false,mpp_skrining=false,penilaian_pasien_penyakit_menular=false,edukasi_pasien_keluarga_rj=false,pemantauan_pews_dewasa=false,
            penilaian_tambahan_bunuh_diri=false,bpjs_antrean_pertanggal=false,penilaian_tambahan_perilaku_kekerasan=false,penilaian_tambahan_beresiko_melarikan_diri=false,
            persetujuan_penundaan_pelayanan=false,sisa_diet_pasien=false,penilaian_awal_medis_ralan_bedah_mulut=false,penilaian_pasien_keracunan=false,
            pemantauan_meows_obstetri=false,catatan_adime_gizi=false,pengajuan_biaya=false,penilaian_awal_keperawatan_ralan_geriatri=false,master_masalah_keperawatan_geriatri=false,
            master_rencana_keperawatan_geriatri=false,checklist_kriteria_masuk_hcu=false,checklist_kriteria_keluar_hcu=false,penilaian_risiko_dekubitus=false,
            master_menolak_anjuran_medis=false,penolakan_anjuran_medis=false,laporan_tahunan_penolakan_anjuran_medis=false,template_laporan_operasi=false,hasil_tindakan_eswl=false,
            checklist_kriteria_masuk_icu=false,checklist_kriteria_keluar_icu=false,akses_dokter_lain_rawat_jalan=false,follow_up_dbd=false,penilaian_risiko_jatuh_neonatus=false,
            persetujuan_pengajuan_biaya=false,pemeriksaan_fisik_ralan_per_penyakit=false,penilaian_lanjutan_resiko_jatuh_geriatri=false,pemantauan_ews_neonatus=false,
            validasi_persetujuan_pengajuan_biaya=false,riwayat_perawatan_icare_bpjs=false,rekap_pengajuan_biaya=false,penilaian_awal_medis_ralan_kulit_kelamin=false,
            akun_host_to_host_bank_mandiri=false,penilaian_medis_hemodialisa=false,penilaian_level_kecemasan_ranap_anak=false,penilaian_lanjutan_resiko_jatuh_psikiatri=false,
            penilaian_lanjutan_skrining_fungsional=false,penilaian_medis_ralan_rehab_medik=false,laporan_anestesi=false,template_persetujuan_penolakan_tindakan=false,
            penilaian_medis_ralan_gawat_darurat_psikiatri=false,bpjs_referensi_setting_apotek=false,bpjs_referensi_obat_apotek=false,bpjs_mapping_obat_apotek=false,
            pembayaran_bank_mandiri=false,penilaian_ulang_nyeri=false,penilaian_terapi_wicara=false,bpjs_obat_23hari_apotek=false,pengkajian_restrain=false,
            bpjs_kunjungan_sep_apotek=false,bpjs_monitoring_klaim_apotek=false,bpjs_daftar_pelayanan_obat_apotek=false,penilaian_awal_medis_ralan_paru=false,
            catatan_keperawatan_ralan=false,catatan_persalinan=false,skor_aldrette_pasca_anestesi=false,skor_steward_pasca_anestesi=false,skor_bromage_pasca_anestesi=false,
            penilaian_pre_induksi=false,hasil_usg_urologi=false,hasil_usg_gynecologi=false,hasil_pemeriksaan_ekg=false,hapus_edit_sep_bpjs=false,satu_sehat_kirim_diet=false,
            satu_sehat_mapping_obat=false,dapur_ringkasan_pembelian=false,satu_sehat_kirim_medication=false,satu_sehat_kirim_medicationrequest=false,
            penatalaksanaan_terapi_okupasi=false,satu_sehat_kirim_medicationdispense=false,hasil_usg_neonatus=false,hasil_endoskopi_faring_laring=false,
            satu_sehat_mapping_radiologi=false,satu_sehat_kirim_servicerequest_radiologi=false,hasil_endoskopi_hidung=false,satu_sehat_kirim_specimen_radiologi=false,
            master_masalah_keperawatan_neonatus=false,master_rencana_keperawatan_neonatus=false,penilaian_awal_keperawatan_ranap_neonatus=false,
            satu_sehat_kirim_observation_radiologi=false,satu_sehat_kirim_diagnosticreport_radiologi=false,hasil_endoskopi_telinga=false,satu_sehat_mapping_lab=false,
            satu_sehat_kirim_servicerequest_lab=false,satu_sehat_kirim_servicerequest_labmb=false,satu_sehat_kirim_specimen_lab=false,satu_sehat_kirim_specimen_labmb=false,
            satu_sehat_kirim_observation_lab=false,satu_sehat_kirim_observation_labmb=false,satu_sehat_kirim_diagnosticreport_lab=false,satu_sehat_kirim_diagnosticreport_labmb=false,
            kepatuhan_kelengkapan_keselamatan_bedah=false,nilai_piutang_perjenis_bayar_per_bulan=false,ringkasan_piutang_jenis_bayar=false,penilaian_pasien_imunitas_rendah=false,
            balance_cairan=false,catatan_observasi_chbp=false,catatan_observasi_induksi_persalinan=false,skp_kategori_penilaian=false,skp_kriteria_penilaian=false,
            skp_penilaian=false,referensi_poli_mobilejknfktp=false,referensi_dokter_mobilejknfktp=false,skp_rekapitulasi_penilaian=false,pembayaran_pihak_ke3_bankmandiri=false,
            metode_pembayaran_bankmandiri=false,bank_tujuan_transfer_bankmandiri=false,kodetransaksi_tujuan_transfer_bankmandiri=false,konsultasi_medik=false,jawaban_konsultasi_medik=false,
            pcare_cek_alergi=false,pcare_cek_prognosa=false,data_sasaran_usiaproduktif=false,data_sasaran_usialansia=false,skrining_perilaku_merokok_sekolah_remaja=false,
            skrining_kekerasan_pada_perempuan=false,skrining_obesitas=false,skrining_risiko_kanker_payudara=false,skrining_risiko_kanker_paru=false,skrining_tbc=false,
            skrining_kesehatan_gigi_mulut_remaja=false,penilaian_awal_keperawatan_ranap_bayi=false,booking_mcu_perusahaan=false,catatan_observasi_restrain_nonfarma=false,
            catatan_observasi_ventilator=false,catatan_anestesi_sedasi=false,skrining_puma=false,satu_sehat_kirim_careplan=false,satu_sehat_kirim_medicationstatement=false,
            skrining_adiksi_nikotin=false,skrining_thalassemia=false,skrining_instrumen_sdq=false,skrining_instrumen_srq=false,checklist_pemberian_fibrinolitik=false,
            skrining_kanker_kolorektal=false,dapur_pemesanan=false,bayar_pesan_dapur=false,hutang_dapur=false,titip_faktur_dapur=false,validasi_tagihan_dapur=false,
            surat_pemesanan_dapur=false,pengajuan_barang_dapur=false,dapur_returbeli=false,hibah_dapur=false,ringkasan_penerimaan_dapur=false,ringkasan_pengajuan_dapur=false,
            ringkasan_pemesanan_dapur=false,ringkasan_returbeli_dapur=false,ringkasan_stokkeluar_dapur=false,dapur_stokkeluar_pertanggal=false,sirkulasi_dapur=false,
            sirkulasi_dapur2=false,verifikasi_penerimaan_dapur=false,nilai_penerimaan_vendor_dapur_perbulan=false,ringkasan_hutang_vendor_dapur=false,penilaian_psikologi_klinis=false,
            penilaian_awal_medis_ranap_neonatus=false,penilaian_derajat_dehidrasi=false,ringkasan_jasa_tindakan_medis=false,pendapatan_per_akun=false,hasil_pemeriksaan_echo=false,
            penilaian_bayi_baru_lahir=false,rl1_3_ketersediaan_kamar=false,pendapatan_per_akun_closing=false,pengeluaran_pengeluaran=false,skrining_diabetes_melitus=false,
            laporan_tindakan=false,pelaksanaan_informasi_edukasi=false,layanan_kedokteran_fisik_rehabilitasi=false,skrining_kesehatan_gigi_mulut_balita=false,skrining_anemia=false,
            layanan_program_kfr=false,skrining_hipertensi=false,skrining_kesehatan_penglihatan=false,catatan_observasi_hemodialisa=false,skrining_kesehatan_gigi_mulut_dewasa=false,
            skrining_risiko_kanker_serviks=false,catatan_cairan_hemodialisa=false,skrining_kesehatan_gigi_mulut_lansia=false,skrining_indra_pendengaran=false,
            catatan_pengkajian_paska_operasi=false,skrining_frailty_syndrome=false,sirkulasi_cssd=false,lama_pelayanan_cssd=false,catatan_observasi_bayi=false,
            riwayat_surat_peringatan=false,master_kesimpulan_anjuran_mcu=false,kategori_piutang_jasa_perusahaan=false,piutang_jasa_perusahaan=false,bayar_piutang_jasa_perusahaan=false,
            piutang_jasa_perusahaan_belum_lunas=false,checklist_kesiapan_anestesi=false,piutang_peminjaman_uang_belum_lunas=false,hasil_pemeriksaan_slit_lamp=false,
            hasil_pemeriksaan_oct=false,beban_hutang_lain=false,poli_asal_pasien_ranap=false,pemberi_hutang_lain=false,dokter_asal_pasien_ranap=false,duta_parkir_rekap_keluar=false,
            surat_keterangan_layak_terbang=false,bayar_beban_hutang_lain=false,surat_persetujuan_pemeriksaan_hiv=false,skrining_instrumen_acrs=false,surat_pernyataan_memilih_dpjp=false,
            skrining_instrumen_mental_emosional=false,pelanggan_lab_kesehatan_lingkungan=false,kriteria_masuk_nicu=false,kriteria_keluar_nicu=false,penilaian_medis_ranap_psikiatrik=false,
            kriteria_masuk_picu=false,kriteria_keluar_picu=false,master_sampel_bakumutu=false,skrining_instrumen_amt=false,parameter_pengujian_lab_kesehatan_lingkungan=false,
            nilai_normal_baku_mutu_lab_kesehatan_lingkungan=false,skrining_pneumonia_severity_index=false,permintaan_pengujian_sampel_lab_kesehatan_lingkungan=false,
            penilaian_awal_medis_ralan_jantung=false,penilaian_awal_medis_ralan_urologi=false,hasil_pemeriksaan_treadmill=false,hasil_pemeriksaan_echo_pediatrik=false;
    
    public static void setData(String user, String pass) {
        try {        
                ps=koneksi.prepareStatement("select * from admin where admin.usere=AES_ENCRYPT(?,'nur') and admin.passworde=AES_ENCRYPT(?,'windi')");               
                ps2=koneksi.prepareStatement("select * from user where user.id_user=AES_ENCRYPT(?,'nur') and user.password=AES_ENCRYPT(?,'windi')");
                try {
                    ps.setString(1,user);
                    ps.setString(2,pass);
                    rs=ps.executeQuery();
                    rs.last();           

                    ps2.setString(1,user);
                    ps2.setString(2,pass);
                    rs2=ps2.executeQuery();
                    rs2.last();

                    akses.jml1=rs.getRow();
                    akses.jml2=rs2.getRow();               
                    if(rs.getRow()>=1){
                        akses.kode="Admin Utama";
                        akses.penyakit=true;
                        akses.obat_penyakit=true;
                        akses.dokter=true;
                        akses.jadwal_praktek=true;
                        akses.petugas=true;
                        akses.pasien=true;
                        akses.registrasi=true;
                        akses.tindakan_ralan=true;
                        akses.kamar_inap=true;
                        akses.tindakan_ranap=true;
                        akses.operasi=true;
                        akses.rujukan_keluar=true;
                        akses.rujukan_masuk=true;
                        akses.beri_obat=true;
                        akses.resep_pulang=true;
                        akses.pasien_meninggal=true;
                        akses.diet_pasien=true;
                        akses.kelahiran_bayi=true;
                        akses.periksa_lab=true;
                        akses.periksa_radiologi=true;
                        akses.kasir_ralan=true;
                        akses.deposit_pasien=true;
                        akses.piutang_pasien=true;
                        akses.peminjaman_berkas=true;
                        akses.barcode=true;
                        akses.presensi_harian=true;
                        akses.presensi_bulanan=true;
                        akses.pegawai_admin=true;
                        akses.pegawai_user=true;
                        akses.suplier=true;
                        akses.satuan_barang=true;
                        akses.konversi_satuan=true;
                        akses.jenis_barang=true;
                        akses.obat=true;
                        akses.stok_opname_obat=true;
                        akses.stok_obat_pasien=true;
                        akses.pengadaan_obat=true;
                        akses.pemesanan_obat=true;
                        akses.penjualan_obat=true;
                        akses.piutang_obat=true;
                        akses.retur_ke_suplier=true;
                        akses.retur_dari_pembeli=true;
                        akses.retur_obat_ranap=true;
                        akses.retur_piutang_pasien=true;
                        akses.keuntungan_penjualan=true;
                        akses.keuntungan_beri_obat=true;
                        akses.sirkulasi_obat=true;
                        akses.ipsrs_barang=true;
                        akses.ipsrs_pengadaan_barang=true;
                        akses.ipsrs_stok_keluar=true;
                        akses.ipsrs_rekap_pengadaan=true;
                        akses.ipsrs_rekap_stok_keluar=true;
                        akses.ipsrs_pengeluaran_harian=true;
                        akses.ipsrs_jenis_barang=true;
                        akses.inventaris_jenis=true;
                        akses.inventaris_kategori=true;
                        akses.inventaris_merk=true;
                        akses.inventaris_ruang=true;
                        akses.inventaris_produsen=true;
                        akses.inventaris_koleksi=true;
                        akses.inventaris_inventaris=true;
                        akses.inventaris_sirkulasi=true;
                        akses.parkir_jenis=true;
                        akses.parkir_in=true;
                        akses.parkir_out=true;
                        akses.parkir_rekap_harian=true;
                        akses.parkir_rekap_bulanan=true;
                        akses.informasi_kamar=true;
                        akses.harian_tindakan_poli=true;
                        akses.obat_per_poli=true;
                        akses.obat_per_kamar=true;
                        akses.obat_per_dokter_ralan=true;
                        akses.obat_per_dokter_ranap=true;
                        akses.harian_dokter=true;
                        akses.bulanan_dokter=true;
                        akses.harian_paramedis=true;
                        akses.bulanan_paramedis=true;
                        akses.pembayaran_ralan=true;
                        akses.pembayaran_ranap=true;
                        akses.rekap_pembayaran_ralan=true;
                        akses.rekap_pembayaran_ranap=true;
                        akses.tagihan_masuk=true;
                        akses.tambahan_biaya=true;
                        akses.potongan_biaya=true;
                        akses.resep_obat=true;
                        akses.resume_pasien=true;
                        akses.penyakit_ralan=true;
                        akses.penyakit_ranap=true;
                        akses.kamar=true;
                        akses.tarif_ralan=true;
                        akses.tarif_ranap=true;
                        akses.tarif_lab=true;
                        akses.tarif_radiologi=true;
                        akses.tarif_operasi=true;
                        akses.akun_rekening=true;
                        akses.rekening_tahun=true;
                        akses.posting_jurnal=true;
                        akses.buku_besar=true;
                        akses.cashflow=true;
                        akses.keuangan=true;
                        akses.pengeluaran=true;
                        akses.setup_pjlab=true;
                        akses.setup_otolokasi=true;
                        akses.setup_jam_kamin=true;
                        akses.setup_embalase=true;
                        akses.tracer_login=true;
                        akses.display=true;
                        akses.set_harga_obat=true;
                        akses.set_penggunaan_tarif=true;
                        akses.set_oto_ralan=true;
                        akses.biaya_harian=true;
                        akses.biaya_masuk_sekali=true;
                        akses.set_no_rm=true;
                        akses.billing_ralan=true;
                        akses.billing_ranap=true;
                        akses.jm_ranap_dokter=true;
                        akses.igd=true;                    
                        akses.barcoderalan=true;
                        akses.barcoderanap=true;
                        akses.set_harga_obat_ralan=true;
                        akses.set_harga_obat_ranap=true;
                        akses.penyakit_pd3i=true;
                        akses.surveilans_pd3i=true;
                        akses.surveilans_ralan=true;
                        akses.diagnosa_pasien=true;
                        akses.admin=true;
                        akses.user=true;
                        akses.vakum=true;
                        akses.aplikasi=true;
                        akses.surveilans_ranap=true;
                        akses.pny_takmenular_ranap=true;
                        akses.pny_takmenular_ralan=true;
                        akses.kunjungan_ralan=true;
                        akses.rl32=true;
                        akses.rl33=true;
                        akses.rl37=true;
                        akses.rl38=true;
                        akses.harian_tindakan_dokter=true;
                        akses.sms=true;
                        akses.sidikjari=true;
                        akses.jam_masuk=true;
                        akses.jadwal_pegawai=true;                    
                        akses.parkir_barcode=true;
                        akses.set_nota=true;
                        akses.dpjp_ranap=true;
                        akses.mutasi_barang=true;
                        akses.rl34=true;
                        akses.rl36=true;
                        akses.fee_visit_dokter=true;
                        akses.fee_bacaan_ekg=true;
                        akses.fee_rujukan_rontgen=true;
                        akses.fee_rujukan_ranap=true;
                        akses.fee_ralan=true;
                        akses.akun_bayar=true;
                        akses.bayar_pemesanan_obat=true;
                        akses.obat_per_dokter_peresep=true;
                        akses.pemasukan_lain=true;
                        akses.pengaturan_rekening=true;
                        akses.closing_kasir=true;
                        akses.keterlambatan_presensi=true; 
                        akses.set_harga_kamar=true;
                        akses.rekap_per_shift=true;
                        akses.bpjs_cek_nik=true;
                        akses.bpjs_cek_kartu=true;
                        akses.bpjs_cek_riwayat=true;
                        akses.obat_per_cara_bayar=true;
                        akses.kunjungan_ranap=true;
                        akses.bayar_piutang=true;
                        akses.payment_point=true;
                        akses.bpjs_cek_nomor_rujukan=true;
                        akses.icd9=true;
                        akses.darurat_stok=true;
                        akses.retensi_rm=true;
                        akses.temporary_presensi=true;
                        akses.jurnal_harian=true;
                        akses.sirkulasi_obat2=true;
                        akses.edit_registrasi=true;
                        akses.bpjs_referensi_diagnosa=true;
                        akses.bpjs_referensi_poli=true;
                        akses.industrifarmasi=true;
                        akses.harian_js=true;
                        akses.bulanan_js=true;
                        akses.harian_paket_bhp=true;
                        akses.bulanan_paket_bhp=true;
                        akses.piutang_pasien2=true;
                        akses.bpjs_referensi_faskes=true;
                        akses.bpjs_sep=true;
                        akses.pengambilan_utd=true;
                        akses.tarif_utd=true;
                        akses.pengambilan_utd2=true;
                        akses.utd_medis_rusak=true;
                        akses.pengambilan_penunjang_utd=true;
                        akses.pengambilan_penunjang_utd2=true;
                        akses.utd_penunjang_rusak=true;
                        akses.suplier_penunjang=true;
                        akses.utd_donor=true;
                        akses.bpjs_monitoring_klaim=true;
                        akses.utd_cekal_darah=true;
                        akses.utd_komponen_darah=true;
                        akses.utd_stok_darah=true;
                        akses.utd_pemisahan_darah=true;
                        akses.harian_kamar=true;
                        akses.rincian_piutang_pasien=true;
                        akses.keuntungan_beri_obat_nonpiutang=true;
                        akses.reklasifikasi_ralan=true;
                        akses.reklasifikasi_ranap=true;
                        akses.utd_penyerahan_darah=true;
                        akses.hutang_obat=true;
                        akses.riwayat_obat_alkes_bhp=true;
                        akses.sensus_harian_poli=true;
                        akses.rl4a=true;
                        akses.aplicare_referensi_kamar=true;
                        akses.aplicare_ketersediaan_kamar=true;
                        akses.inacbg_klaim_baru_otomatis=true;
                        akses.inacbg_klaim_baru_manual=true;
                        akses.inacbg_coder_nik=true;
                        akses.mutasi_berkas=true;
                        akses.akun_piutang=true;
                        akses.harian_kso=true;
                        akses.bulanan_kso=true;
                        akses.harian_menejemen=true;
                        akses.bulanan_menejemen=true;
                        akses.inhealth_cek_eligibilitas=true;
                        akses.inhealth_referensi_jenpel_ruang_rawat=true;
                        akses.inhealth_referensi_poli=true;
                        akses.inhealth_referensi_faskes=true;
                        akses.inhealth_sjp=true;
                        akses.piutang_ralan=true;
                        akses.piutang_ranap=true;
                        akses.detail_piutang_penjab=true;
                        akses.lama_pelayanan_ralan=true;
                        akses.catatan_pasien=true;
                        akses.rl4b=true;
                        akses.rl4asebab=true;
                        akses.rl4bsebab=true;
                        akses.data_HAIs=true;
                        akses.harian_HAIs=true;
                        akses.bulanan_HAIs=true;
                        akses.hitung_bor=true;
                        akses.perusahaan_pasien=true;
                        akses.resep_dokter=true;
                        akses.lama_pelayanan_apotek=true;
                        akses.hitung_alos=true;
                        akses.detail_tindakan=true;
                        akses.rujukan_poli_internal=true;
                        akses.rekap_poli_anak=true;
                        akses.grafik_kunjungan_poli=true;
                        akses.grafik_kunjungan_perdokter=true;
                        akses.grafik_kunjungan_perpekerjaan=true;
                        akses.grafik_kunjungan_perpendidikan=true;
                        akses.grafik_kunjungan_pertahun=true;
                        akses.berkas_digital_perawatan=true;
                        akses.penyakit_menular_ranap=true;
                        akses.penyakit_menular_ralan=true;
                        akses.grafik_kunjungan_perbulan=true;
                        akses.grafik_kunjungan_pertanggal=true;
                        akses.grafik_kunjungan_demografi=true;
                        akses.grafik_kunjungan_statusdaftartahun=true;
                        akses.grafik_kunjungan_statusdaftartahun2=true;
                        akses.grafik_kunjungan_statusdaftarbulan=true;
                        akses.grafik_kunjungan_statusdaftarbulan2=true;
                        akses.grafik_kunjungan_statusdaftartanggal=true;
                        akses.grafik_kunjungan_statusdaftartanggal2=true;
                        akses.grafik_kunjungan_statusbataltahun=true;
                        akses.grafik_kunjungan_statusbatalbulan=true;
                        akses.pcare_cek_penyakit=true;
                        akses.grafik_kunjungan_statusbataltanggal=true;
                        akses.kategori_barang=true;
                        akses.golongan_barang=true;
                        akses.pemberian_obat_pertanggal=true;                        
                        akses.penjualan_obat_pertanggal=true;
                        akses.pcare_cek_kesadaran=true;
                        akses.pembatalan_periksa_dokter=true;
                        akses.pembayaran_per_unit=true;
                        akses.rekap_pembayaran_per_unit=true;
                        akses.grafik_kunjungan_percarabayar=true;
                        akses.ipsrs_pengadaan_pertanggal=true;
                        akses.ipsrs_stokkeluar_pertanggal=true;
                        akses.grafik_kunjungan_ranaptahun=true;
                        akses.pcare_cek_rujukan=true;
                        akses.grafik_lab_ralantahun=true;
                        akses.grafik_rad_ralantahun=true;;
                        akses.cek_entry_ralan=true;
                        akses.inacbg_klaim_baru_manual2=true;
                        akses.permintaan_medis=true;
                        akses.rekap_permintaan_medis=true;
                        akses.surat_pemesanan_medis=true;
                        akses.permintaan_non_medis=true;
                        akses.rekap_permintaan_non_medis=true;
                        akses.surat_pemesanan_non_medis=true;
                        akses.grafik_per_perujuk=true;
                        akses.bpjs_cek_prosedur=true;
                        akses.bpjs_cek_kelas_rawat=true;
                        akses.bpjs_cek_dokter=true;
                        akses.bpjs_cek_spesialistik=true;
                        akses.bpjs_cek_ruangrawat=true;
                        akses.bpjs_cek_carakeluar=true;
                        akses.bpjs_cek_pasca_pulang=true;
                        akses.detail_tindakan_okvk=true;
                        akses.billing_parsial=true;
                        akses.bpjs_cek_nomor_rujukan_rs=true;
                        akses.bpjs_cek_rujukan_kartu_pcare=true;
                        akses.bpjs_cek_rujukan_kartu_rs=true;
                        akses.akses_depo_obat=true;
                        akses.bpjs_rujukan_keluar=true;
                        akses.grafik_lab_ralanbulan=true;
                        akses.pengeluaran_stok_apotek=true;
                        akses.grafik_rad_ralanbulan=true;
                        akses.detailjmdokter2=true;
                        akses.pengaduan_pasien=true;
                        akses.grafik_lab_ralanhari=true;
                        akses.grafik_rad_ralanhari=true;
                        akses.sensus_harian_ralan=true;
                        akses.metode_racik=true;
                        akses.pembayaran_akun_bayar=true;
                        akses.pengguna_obat_resep=true;
                        akses.rekap_pemesanan=true;
                        akses.master_berkas_pegawai=true;
                        akses.berkas_kepegawaian=true;
                        akses.riwayat_jabatan=true;
                        akses.riwayat_pendidikan=true;
                        akses.riwayat_naik_gaji=true;
                        akses.kegiatan_ilmiah=true;
                        akses.riwayat_penghargaan=true;
                        akses.riwayat_penelitian=true;
                        akses.penerimaan_non_medis=true;
                        akses.bayar_pesan_non_medis=true;
                        akses.hutang_barang_non_medis=true;
                        akses.rekap_pemesanan_non_medis=true;
                        akses.insiden_keselamatan=true;
                        akses.insiden_keselamatan_pasien=true;
                        akses.grafik_ikp_pertahun=true;
                        akses.grafik_ikp_perbulan=true;
                        akses.grafik_ikp_pertanggal=true;
                        akses.riwayat_data_batch=true;
                        akses.grafik_ikp_jenis=true;
                        akses.grafik_ikp_dampak=true;
                        akses.piutang_akun_piutang=true;
                        akses.grafik_kunjungan_per_agama=true;
                        akses.grafik_kunjungan_per_umur=true;
                        akses.suku_bangsa=true;
                        akses.bahasa_pasien=true;
                        akses.golongan_tni=true;
                        akses.satuan_tni=true;
                        akses.jabatan_tni=true;
                        akses.pangkat_tni=true;
                        akses.golongan_polri=true;
                        akses.satuan_polri=true;
                        akses.jabatan_polri=true;
                        akses.pangkat_polri=true;
                        akses.cacat_fisik=true;
                        akses.grafik_kunjungan_suku=true;
                        akses.grafik_kunjungan_bahasa=true;
                        akses.booking_operasi=true;
                        akses.mapping_poli_bpjs=true;
                        akses.grafik_kunjungan_per_cacat=true;
                        akses.barang_cssd=true;
                        akses.skdp_bpjs=true;
                        akses.booking_registrasi=true;
                        akses.bpjs_cek_propinsi=true;
                        akses.bpjs_cek_kabupaten=true;
                        akses.bpjs_cek_kecamatan=true;
                        akses.bpjs_cek_dokterdpjp=true;
                        akses.bpjs_cek_riwayat_rujukanrs=true;
                        akses.bpjs_cek_tanggal_rujukan=true;
                        akses.permintaan_lab=true;
                        akses.permintaan_radiologi=true;
                        akses.surat_indeks=true;
                        akses.surat_map=true;
                        akses.surat_almari=true;
                        akses.surat_rak=true;
                        akses.surat_ruang=true;
                        akses.surat_klasifikasi=true;
                        akses.surat_status=true; 
                        akses.surat_sifat=true;
                        akses.surat_balas=true;
                        akses.surat_masuk=true;
                        akses.pcare_cek_dokter=true;
                        akses.pcare_cek_poli=true;
                        akses.pcare_cek_provider=true;
                        akses.pcare_cek_statuspulang=true;
                        akses.pcare_cek_spesialis=true;
                        akses.pcare_cek_subspesialis=true;
                        akses.pcare_cek_sarana=true;
                        akses.pcare_cek_khusus=true;
                        akses.pcare_cek_obat=true;
                        akses.pcare_cek_tindakan=true;
                        akses.pcare_cek_faskessubspesialis=true;
                        akses.pcare_cek_faskesalihrawat=true;
                        akses.pcare_cek_faskesthalasemia=true;
                        akses.pcare_mapping_obat=true;
                        akses.pcare_mapping_tindakan=true;
                        akses.pcare_club_prolanis=true;
                        akses.pcare_mapping_poli=true;
                        akses.pcare_kegiatan_kelompok=true;
                        akses.pcare_mapping_tindakan_ranap=true;
                        akses.pcare_peserta_kegiatan_kelompok=true;
                        akses.sirkulasi_obat3=true;
                        akses.bridging_pcare_daftar=true;
                        akses.pcare_mapping_dokter=true;
                        akses.ranap_per_ruang=true;
                        akses.penyakit_ranap_cara_bayar=true;
                        akses.anggota_militer_dirawat=true;
                        akses.set_input_parsial=true;
                        akses.lama_pelayanan_radiologi=true;
                        akses.lama_pelayanan_lab=true;
                        akses.bpjs_cek_sep=true;
                        akses.catatan_perawatan=true;
                        akses.surat_keluar=true;
                        akses.kegiatan_farmasi=true;
                        akses.stok_opname_logistik=true;
                        akses.sirkulasi_non_medis=true;
                        akses.rekap_lab_pertahun=true;
                        akses.perujuk_lab_pertahun=true;
                        akses.rekap_radiologi_pertahun=true;
                        akses.perujuk_radiologi_pertahun=true;
                        akses.jumlah_porsi_diet=true;
                        akses.jumlah_macam_diet=true;
                        akses.payment_point2=true;
                        akses.pembayaran_akun_bayar2=true;
                        akses.hapus_nota_salah=true;
                        akses.hais_perbangsal=true;
                        akses.ppn_obat=true;
                        akses.saldo_akun_perbulan=true;
                        akses.display_apotek=true;
                        akses.sisrute_referensi_faskes=true;
                        akses.sisrute_referensi_alasanrujuk=true;
                        akses.sisrute_referensi_diagnosa=true;
                        akses.sisrute_rujukan_masuk=true;
                        akses.sisrute_rujukan_keluar=true;
                        akses.bpjs_cek_skdp=true;
                        akses.data_batch=true;
                        akses.kunjungan_permintaan_lab=true;
                        akses.kunjungan_permintaan_lab2=true;
                        akses.kunjungan_permintaan_radiologi=true;
                        akses.kunjungan_permintaan_radiologi2=true;
                        akses.pcare_pemberian_obat=true;
                        akses.pcare_pemberian_tindakan=true;
                        akses.pembayaran_akun_bayar3=true;
                        akses.password_asuransi=true;
                        akses.kemenkes_sitt=true;
                        akses.siranap_ketersediaan_kamar=true;
                        akses.grafik_tb_periodelaporan=true;
                        akses.grafik_tb_rujukan=true;
                        akses.grafik_tb_riwayat=true;
                        akses.grafik_tb_tipediagnosis=true;
                        akses.grafik_tb_statushiv=true;
                        akses.grafik_tb_skoringanak=true;
                        akses.grafik_tb_konfirmasiskoring5=true;
                        akses.grafik_tb_konfirmasiskoring6=true;
                        akses.grafik_tb_sumberobat=true;
                        akses.grafik_tb_hasilakhirpengobatan=true;
                        akses.grafik_tb_hasilteshiv=true;
                        akses.kadaluarsa_batch=true;
                        akses.sisa_stok=true;
                        akses.obat_per_resep=true;
                        akses.pemakaian_air_pdam=true;
                        akses.limbah_b3_medis=true;
                        akses.grafik_air_pdam_pertanggal=true;
                        akses.grafik_air_pdam_perbulan=true;
                        akses.grafik_limbahb3_pertanggal=true;
                        akses.grafik_limbahb3_perbulan=true;
                        akses.limbah_domestik=true;
                        akses.grafik_limbahdomestik_pertanggal=true;
                        akses.grafik_limbahdomestik_perbulan=true;
                        akses.mutu_air_limbah=true;
                        akses.pest_control=true;
                        akses.ruang_perpustakaan=true;
                        akses.kategori_perpustakaan=true;
                        akses.jenis_perpustakaan=true;
                        akses.pengarang_perpustakaan=true;
                        akses.penerbit_perpustakaan=true;
                        akses.koleksi_perpustakaan=true;
                        akses.inventaris_perpustakaan=true;
                        akses.set_peminjaman_perpustakaan=true;
                        akses.denda_perpustakaan=true;
                        akses.anggota_perpustakaan=true;
                        akses.peminjaman_perpustakaan=true;
                        akses.bayar_denda_perpustakaan=true;
                        akses.ebook_perpustakaan=true;
                        akses.jenis_cidera_k3rs=true;
                        akses.penyebab_k3rs=true;
                        akses.jenis_luka_k3rs=true;
                        akses.lokasi_kejadian_k3rs=true;
                        akses.dampak_cidera_k3rs=true;
                        akses.jenis_pekerjaan_k3rs=true;
                        akses.bagian_tubuh_k3rs=true;
                        akses.peristiwa_k3rs=true;
                        akses.grafik_k3_pertahun=true;
                        akses.grafik_k3_perbulan=true;
                        akses.grafik_k3_pertanggal=true;
                        akses.grafik_k3_perjeniscidera=true;
                        akses.grafik_k3_perpenyebab=true;
                        akses.grafik_k3_perjenisluka=true;
                        akses.grafik_k3_lokasikejadian=true;
                        akses.grafik_k3_dampakcidera=true;
                        akses.grafik_k3_perjenispekerjaan=true;
                        akses.grafik_k3_perbagiantubuh=true;
                        akses.jenis_cidera_k3rstahun=true;
                        akses.penyebab_k3rstahun=true;
                        akses.jenis_luka_k3rstahun=true;
                        akses.lokasi_kejadian_k3rstahun=true;
                        akses.dampak_cidera_k3rstahun=true;
                        akses.jenis_pekerjaan_k3rstahun=true;
                        akses.bagian_tubuh_k3rstahun=true;
                        akses.sekrining_rawat_jalan=true;
                        akses.bpjs_histori_pelayanan=true;
                        akses.rekap_mutasi_berkas=true;
                        akses.skrining_ralan_pernapasan_pertahun=true;
                        akses.pengajuan_barang_medis=true;
                        akses.pengajuan_barang_nonmedis=true;
                        akses.grafik_kunjungan_ranapbulan=true;
                        akses.grafik_kunjungan_ranaptanggal=true;
                        akses.grafik_kunjungan_ranap_peruang=true;
                        akses.kunjungan_bangsal_pertahun=true;
                        akses.grafik_jenjang_jabatanpegawai=true;
                        akses.grafik_bidangpegawai=true;
                        akses.grafik_departemenpegawai=true;
                        akses.grafik_pendidikanpegawai=true;
                        akses.grafik_sttswppegawai=true;
                        akses.grafik_sttskerjapegawai=true;
                        akses.grafik_sttspulangranap=true;
                        akses.kip_pasien_ranap=true;
                        akses.kip_pasien_ralan=true;
                        akses.bpjs_mapping_dokterdpjp=true;
                        akses.data_triase_igd=true;
                        akses.master_triase_skala1=true;
                        akses.master_triase_skala2=true;
                        akses.master_triase_skala3=true;
                        akses.master_triase_skala4=true;
                        akses.master_triase_skala5=true;
                        akses.master_triase_pemeriksaan=true;
                        akses.master_triase_macamkasus=true;
                        akses.rekap_permintaan_diet=true;
                        akses.daftar_pasien_ranap=true;
                        akses.daftar_pasien_ranaptni=true;
                        akses.pengajuan_asetinventaris=true;
                        akses.item_apotek_jenis=true;
                        akses.item_apotek_kategori=true;
                        akses.item_apotek_golongan=true;
                        akses.item_apotek_industrifarmasi=true;
                        akses.obat10_terbanyak_poli=true;
                        akses.grafik_pengajuan_aset_urgensi=true;
                        akses.grafik_pengajuan_aset_status=true;
                        akses.grafik_pengajuan_aset_departemen=true;
                        akses.rekap_pengajuan_aset_departemen=true;
                        akses.grafik_kelompok_jabatanpegawai=true;
                        akses.grafik_resiko_kerjapegawai=true;
                        akses.grafik_emergency_indexpegawai=true;
                        akses.grafik_inventaris_ruang=true;
                        akses.harian_HAIs2=true;
                        akses.grafik_inventaris_jenis=true;
                        akses.data_resume_pasien=true;
                        akses.perkiraan_biaya_ranap=true;
                        akses.rekap_obat_poli=true;
                        akses.rekap_obat_pasien=true;
                        akses.grafik_HAIs_pasienbangsal=true;
                        akses.grafik_HAIs_pasienbulan=true;
                        akses.permintaan_perbaikan_inventaris=true;
                        akses.grafik_HAIs_laju_vap=true;
                        akses.grafik_HAIs_laju_iad=true;
                        akses.grafik_HAIs_laju_pleb=true;
                        akses.grafik_HAIs_laju_isk=true;
                        akses.grafik_HAIs_laju_ilo=true;
                        akses.grafik_HAIs_laju_hap=true;
                        akses.inhealth_mapping_poli=true;
                        akses.inhealth_mapping_dokter=true;
                        akses.inhealth_mapping_tindakan_ralan=true;
                        akses.inhealth_mapping_tindakan_ranap=true;
                        akses.inhealth_mapping_tindakan_radiologi=true;
                        akses.inhealth_mapping_tindakan_laborat=true;
                        akses.inhealth_mapping_tindakan_operasi=true;
                        akses.hibah_obat_bhp=true;
                        akses.asal_hibah=true;
                        akses.asuhan_gizi=true;
                        akses.inhealth_kirim_tagihan=true;
                        akses.sirkulasi_obat4=true;
                        akses.sirkulasi_obat5=true;
                        akses.sirkulasi_non_medis2=true;
                        akses.monitoring_asuhan_gizi=true;
                        akses.penerimaan_obat_perbulan=true;
                        akses.rekap_kunjungan=true;
                        akses.surat_sakit=true;
                        akses.penilaian_awal_keperawatan_ralan=true;
                        akses.permintaan_diet=true;
                        akses.master_masalah_keperawatan=true;
                        akses.pengajuan_cuti=true;
                        akses.kedatangan_pasien=true;
                        akses.utd_pendonor=true;
                        akses.toko_suplier=true;
                        akses.toko_jenis=true;
                        akses.toko_set_harga=true;
                        akses.toko_barang=true;
                        akses.penagihan_piutang_pasien=true;
                        akses.akun_penagihan_piutang=true;
                        akses.stok_opname_toko=true;
                        akses.toko_riwayat_barang=true;
                        akses.toko_surat_pemesanan=true;
                        akses.toko_pengajuan_barang=true;
                        akses.toko_penerimaan_barang=true;
                        akses.toko_pengadaan_barang=true;
                        akses.toko_hutang=true;
                        akses.toko_bayar_pemesanan=true;
                        akses.toko_member=true;
                        akses.toko_penjualan=true;
                        akses.registrasi_poli_per_tanggal=true;
                        akses.toko_piutang=true;
                        akses.toko_retur_beli=true;
                        akses.ipsrs_returbeli=true;
                        akses.ipsrs_riwayat_barang=true;
                        akses.pasien_corona=true;
                        akses.toko_pendapatan_harian=true;
                        akses.diagnosa_pasien_corona=true;
                        akses.perawatan_pasien_corona=true;
                        akses.penilaian_awal_keperawatan_gigi=true;
                        akses.master_masalah_keperawatan_gigi=true;
                        akses.toko_bayar_piutang=true;
                        akses.toko_piutang_harian=true;
                        akses.toko_penjualan_harian=true;
                        akses.deteksi_corona=true;
                        akses.penilaian_awal_keperawatan_kebidanan=true;
                        akses.pengumuman_epasien=true;
                        akses.surat_hamil=true;
                        akses.set_tarif_online=true;
                        akses.booking_periksa=true;
                        akses.toko_sirkulasi=true;
                        akses.toko_retur_jual=true;
                        akses.toko_retur_piutang=true;
                        akses.toko_sirkulasi2=true;
                        akses.toko_keuntungan_barang=true;
                        akses.zis_pengeluaran_penerima_dankes=true;
                        akses.zis_penghasilan_penerima_dankes=true;
                        akses.zis_ukuran_rumah_penerima_dankes=true;
                        akses.zis_dinding_rumah_penerima_dankes=true;
                        akses.zis_lantai_rumah_penerima_dankes=true;
                        akses.zis_atap_rumah_penerima_dankes=true;
                        akses.zis_kepemilikan_rumah_penerima_dankes=true;
                        akses.zis_kamar_mandi_penerima_dankes=true;
                        akses.zis_dapur_rumah_penerima_dankes=true;
                        akses.zis_kursi_rumah_penerima_dankes=true;
                        akses.zis_kategori_phbs_penerima_dankes=true;
                        akses.zis_elektronik_penerima_dankes=true;
                        akses.zis_ternak_penerima_dankes=true;
                        akses.zis_jenis_simpanan_penerima_dankes=true;
                        akses.penilaian_awal_keperawatan_anak=true;
                        akses.zis_kategori_asnaf_penerima_dankes=true;
                        akses.master_masalah_keperawatan_anak=true;
                        akses.master_imunisasi=true;
                        akses.zis_patologis_penerima_dankes=true;
                        akses.pcare_cek_kartu=true;
                        akses.surat_bebas_narkoba=true;
                        akses.surat_keterangan_covid=true;
                        akses.pemakaian_air_tanah=true;
                        akses.grafik_air_tanah_pertanggal=true;
                        akses.grafik_air_tanah_perbulan=true;
                        akses.lama_pelayanan_poli=true;
                        akses.hemodialisa=true;
                        akses.grafik_harian_hemodialisa=true;
                        akses.grafik_bulanan_hemodialisa=true;
                        akses.grafik_tahunan_hemodialisa=true;
                        akses.grafik_bulanan_meninggal=true;
                        akses.laporan_tahunan_irj=true;
                        akses.perbaikan_inventaris=true;
                        akses.surat_cuti_hamil=true;
                        akses.permintaan_stok_obat_pasien=true;
                        akses.pemeliharaan_inventaris=true;
                        akses.klasifikasi_pasien_ranap=true;
                        akses.bulanan_klasifikasi_pasien_ranap=true;
                        akses.harian_klasifikasi_pasien_ranap=true;
                        akses.klasifikasi_pasien_perbangsal=true;
                        akses.soap_perawatan=true;
                        akses.klaim_rawat_jalan=true;
                        akses.skrining_gizi=true;
                        akses.lama_penyiapan_rm=true;
                        akses.dosis_radiologi=true;
                        akses.demografi_umur_kunjungan=true;
                        akses.jam_diet_pasien=true;
                        akses.rvu_bpjs=true;
                        akses.verifikasi_penerimaan_farmasi=true;
                        akses.verifikasi_penerimaan_logistik=true;
                        akses.pemeriksaan_lab_pa=true;
                        akses.ringkasan_pengajuan_obat=true;
                        akses.ringkasan_pemesanan_obat=true;
                        akses.ringkasan_pengadaan_obat=true;
                        akses.ringkasan_penerimaan_obat=true;
                        akses.ringkasan_hibah_obat=true;
                        akses.ringkasan_penjualan_obat=true;
                        akses.ringkasan_beri_obat=true;
                        akses.ringkasan_piutang_obat=true;
                        akses.ringkasan_stok_keluar_obat=true;
                        akses.ringkasan_retur_suplier_obat=true;
                        akses.ringkasan_retur_pembeli_obat=true;
                        akses.penilaian_awal_keperawatan_ranapkebidanan=true;
                        akses.ringkasan_pengajuan_nonmedis=true;
                        akses.ringkasan_pemesanan_nonmedis=true;
                        akses.ringkasan_pengadaan_nonmedis=true;
                        akses.ringkasan_penerimaan_nonmedis=true;
                        akses.ringkasan_stokkeluar_nonmedis=true;
                        akses.ringkasan_returbeli_nonmedis=true;
                        akses.omset_penerimaan=true;
                        akses.validasi_penagihan_piutang=true;
                        akses.permintaan_ranap=true;
                        akses.bpjs_diagnosa_prb=true;
                        akses.bpjs_obat_prb=true;
                        akses.bpjs_surat_kontrol=true;
                        akses.penggunaan_bhp_ok=true;
                        akses.surat_keterangan_rawat_inap=true;
                        akses.surat_keterangan_sehat=true;
                        akses.pendapatan_per_carabayar=true;
                        akses.akun_host_to_host_bank_jateng=true;
                        akses.pembayaran_bank_jateng=true;
                        akses.bpjs_surat_pri=true;
                        akses.ringkasan_tindakan=true;
                        akses.lama_pelayanan_pasien=true;
                        akses.surat_sakit_pihak_2=true;
                        akses.tagihan_hutang_obat=true;
                        akses.referensi_mobilejkn_bpjs=true;
                        akses.batal_pendaftaran_mobilejkn_bpjs=true;
                        akses.lama_operasi=true;
                        akses.grafik_inventaris_kategori=true;
                        akses.grafik_inventaris_merk=true;
                        akses.grafik_inventaris_produsen=true;
                        akses.pengembalian_deposit_pasien=true;
                        akses.validasi_tagihan_hutang_obat=true;
                        akses.piutang_obat_belum_lunas=true;
                        akses.integrasi_briapi=true;
                        akses.pengadaan_aset_inventaris=true;
                        akses.akun_aset_inventaris=true;
                        akses.suplier_inventaris=true;
                        akses.penerimaan_aset_inventaris=true;
                        akses.bayar_pemesanan_iventaris=true;
                        akses.hutang_aset_inventaris=true;
                        akses.hibah_aset_inventaris=true;
                        akses.titip_faktur_non_medis=true;
                        akses.validasi_tagihan_non_medis=true;
                        akses.titip_faktur_aset=true;
                        akses.validasi_tagihan_aset=true;
                        akses.hibah_non_medis=true;
                        akses.pcare_alasan_tacc=true;
                        akses.resep_luar=true;
                        akses.surat_bebas_tbc=true;
                        akses.surat_buta_warna=true;
                        akses.surat_bebas_tato=true;
                        akses.surat_kewaspadaan_kesehatan=true;
                        akses.grafik_porsidiet_pertanggal=true;
                        akses.grafik_porsidiet_perbulan=true;
                        akses.grafik_porsidiet_pertahun=true;
                        akses.grafik_porsidiet_perbangsal=true;
                        akses.penilaian_awal_medis_ralan=true;
                        akses.master_masalah_keperawatan_mata=true;
                        akses.penilaian_awal_keperawatan_mata=true;
                        akses.penilaian_awal_medis_ranap=true;
                        akses.penilaian_awal_medis_ranap_kebidanan=true;
                        akses.penilaian_awal_medis_ralan_kebidanan=true;
                        akses.penilaian_awal_medis_igd=true;
                        akses.penilaian_awal_medis_ralan_anak=true;
                        akses.bpjs_referensi_poli_hfis=true;
                        akses.bpjs_referensi_dokter_hfis=true;
                        akses.bpjs_referensi_jadwal_hfis=true;
                        akses.penilaian_fisioterapi=true;
                        akses.bpjs_program_prb=true;
                        akses.bpjs_suplesi_jasaraharja=true;
                        akses.bpjs_data_induk_kecelakaan=true;
                        akses.bpjs_sep_internal=true;
                        akses.bpjs_klaim_jasa_raharja=true;
                        akses.bpjs_daftar_finger_print=true;
                        akses.bpjs_rujukan_khusus=true;
                        akses.pemeliharaan_gedung=true;
                        akses.grafik_perbaikan_inventaris_pertanggal=true;
                        akses.grafik_perbaikan_inventaris_perbulan=true;
                        akses.grafik_perbaikan_inventaris_pertahun=true;
                        akses.grafik_perbaikan_inventaris_perpelaksana_status=true;
                        akses.penilaian_mcu=true;
                        akses.peminjam_piutang=true;
                        akses.piutang_lainlain=true;
                        akses.cara_bayar=true;
                        akses.audit_kepatuhan_apd=true;
                        akses.bpjs_task_id=true;
                        akses.bayar_piutang_lain=true;
                        akses.pembayaran_akun_bayar4=true;
                        akses.stok_akhir_farmasi_pertanggal=true;
                        akses.riwayat_kamar_pasien=true;
                        akses.uji_fungsi_kfr=true;
                        akses.hapus_berkas_digital_perawatan=true;
                        akses.kategori_pengeluaran_harian=true;
                        akses.kategori_pemasukan_lain=true;
                        akses.pembayaran_akun_bayar5=true;
                        akses.ruang_ok=true;
                        akses.jasa_tindakan_pasien=true;
                        akses.telaah_resep=true;
                        akses.permintaan_resep_pulang=true;
                        akses.rekap_jm_dokter=true;
                        akses.status_data_rm=true;
                        akses.ubah_petugas_lab_pk=true;
                        akses.ubah_petugas_lab_pa=true;
                        akses.ubah_petugas_radiologi=true;
                        akses.gabung_norawat=true;
                        akses.gabung_rm=true;
                        akses.ringkasan_biaya_obat_pasien_pertanggal=true;
                        akses.master_masalah_keperawatan_igd=true;
                        akses.penilaian_awal_keperawatan_igd=true;
                        akses.bpjs_referensi_dpho_apotek=true;
                        akses.bpjs_referensi_poli_apotek=true;
                        akses.bayar_jm_dokter=true;
                        akses.bpjs_referensi_faskes_apotek=true;
                        akses.bpjs_referensi_spesialistik_apotek=true;
                        akses.pembayaran_briva=true;
                        akses.penilaian_awal_keperawatan_ranap=true;
                        akses.nilai_penerimaan_vendor_farmasi_perbulan=true;
                        akses.akun_bayar_hutang=true;
                        akses.master_rencana_keperawatan=true;
                        akses.laporan_tahunan_igd=true;
                        akses.obat_bhp_tidakbergerak=true;
                        akses.ringkasan_hutang_vendor_farmasi=true;
                        akses.nilai_penerimaan_vendor_nonmedis_perbulan=true;
                        akses.ringkasan_hutang_vendor_nonmedis=true;
                        akses.anggota_polri_dirawat=true;
                        akses.daftar_pasien_ranap_polri=true;
                        akses.soap_ralan_polri=true;
                        akses.soap_ranap_polri=true;
                        akses.laporan_penyakit_polri=true;
                        akses.master_rencana_keperawatan_anak=true;
                        akses.jumlah_pengunjung_ralan_polri=true;
                        akses.catatan_observasi_igd=true;
                        akses.catatan_observasi_ranap=true;
                        akses.catatan_observasi_ranap_kebidanan=true;
                        akses.catatan_observasi_ranap_postpartum=true;
                        akses.penilaian_awal_medis_ralan_tht=true;
                        akses.penilaian_psikologi=true;
                        akses.audit_cuci_tangan_medis=true;
                        akses.audit_pembuangan_limbah=true;
                        akses.ruang_audit_kepatuhan=true;
                        akses.audit_pembuangan_benda_tajam=true;
                        akses.audit_penanganan_darah=true;
                        akses.audit_pengelolaan_linen_kotor=true;
                        akses.audit_penempatan_pasien=true;
                        akses.audit_kamar_jenazah=true;
                        akses.audit_bundle_iadp=true;
                        akses.audit_bundle_ido=true;
                        akses.audit_fasilitas_kebersihan_tangan=true;
                        akses.audit_fasilitas_apd=true;
                        akses.audit_pembuangan_limbah_cair_infeksius=true;
                        akses.audit_sterilisasi_alat=true;
                        akses.penilaian_awal_medis_ralan_psikiatri=true;
                        akses.persetujuan_penolakan_tindakan=true;
                        akses.audit_bundle_isk=true;
                        akses.audit_bundle_plabsi=true;
                        akses.audit_bundle_vap=true;
                        akses.akun_host_to_host_bank_papua=true;
                        akses.pembayaran_bank_papua=true;
                        akses.penilaian_awal_medis_ralan_penyakit_dalam=true;
                        akses.penilaian_awal_medis_ralan_mata=true;
                        akses.penilaian_awal_medis_ralan_neurologi=true;
                        akses.sirkulasi_obat6=true;
                        akses.penilaian_awal_medis_ralan_orthopedi=true;
                        akses.penilaian_awal_medis_ralan_bedah=true;
                        akses.integrasi_khanza_health_services=true;
                        akses.soap_ralan_tni=true;
                        akses.soap_ranap_tni=true;
                        akses.jumlah_pengunjung_ralan_tni=true;
                        akses.laporan_penyakit_tni=true;
                        akses.catatan_keperawatan_ranap=true;
                        akses.master_rencana_keperawatan_gigi=true;
                        akses.master_rencana_keperawatan_mata=true;
                        akses.master_rencana_keperawatan_igd=true;
                        akses.master_masalah_keperawatan_psikiatri=true;
                        akses.master_rencana_keperawatan_psikiatri=true;
                        akses.penilaian_awal_keperawatan_psikiatri=true;
                        akses.pemantauan_pews_anak=true;
                        akses.surat_pulang_atas_permintaan_sendiri=true;
                        akses.template_hasil_radiologi=true;
                        akses.laporan_bulanan_irj=true;
                        akses.template_pemeriksaan=true;
                        akses.pemeriksaan_lab_mb=true;
                        akses.ubah_petugas_lab_mb=true;
                        akses.penilaian_pre_operasi=true;
                        akses.penilaian_pre_anestesi=true;
                        akses.perencanaan_pemulangan=true;
                        akses.penilaian_lanjutan_resiko_jatuh_dewasa=true;
                        akses.penilaian_lanjutan_resiko_jatuh_anak=true;
                        akses.penilaian_awal_medis_ralan_geriatri=true;
                        akses.penilaian_tambahan_pasien_geriatri=true;
                        akses.skrining_nutrisi_dewasa=true;
                        akses.skrining_nutrisi_lansia=true;
                        akses.hasil_pemeriksaan_usg=true;
                        akses.skrining_nutrisi_anak=true;
                        akses.akun_host_to_host_bank_jabar=true;
                        akses.pembayaran_bank_jabar=true;
                        akses.surat_pernyataan_pasien_umum=true;
                        akses.konseling_farmasi=true;
                        akses.pelayanan_informasi_obat=true;
                        akses.jawaban_pio_apoteker=true;
                        akses.surat_persetujuan_umum=true;
                        akses.transfer_pasien_antar_ruang=true;
                        akses.satu_sehat_referensi_dokter=true;
                        akses.satu_sehat_referensi_pasien=true;
                        akses.satu_sehat_mapping_departemen=true;
                        akses.satu_sehat_mapping_lokasi=true;
                        akses.satu_sehat_kirim_encounter=true;
                        akses.catatan_cek_gds=true;
                        akses.satu_sehat_kirim_condition=true;
                        akses.checklist_pre_operasi=true;
                        akses.satu_sehat_kirim_observationttv=true;
                        akses.signin_sebelum_anestesi=true;
                        akses.satu_sehat_kirim_procedure=true;
                        akses.operasi_per_bulan=true;
                        akses.timeout_sebelum_insisi=true;
                        akses.signout_sebelum_menutup_luka=true;
                        akses.dapur_barang=true;
                        akses.dapur_opname=true;
                        akses.satu_sehat_mapping_vaksin=true;
                        akses.dapur_suplier=true;
                        akses.satu_sehat_kirim_Immunization=true;
                        akses.checklist_post_operasi=true;
                        akses.dapur_pembelian=true;
                        akses.dapur_stok_keluar=true;
                        akses.dapur_riwayat_barang=true;
                        akses.permintaan_dapur=true;
                        akses.rekonsiliasi_obat=true;
                        akses.biaya_pengadaan_dapur=true;
                        akses.rekap_pengadaan_dapur=true;
                        akses.kesling_limbah_b3medis_cair=true;
                        akses.grafik_limbahb3cair_pertanggal=true;
                        akses.grafik_limbahb3cair_perbulan=true;
                        akses.rekap_biaya_registrasi=true;
                        akses.konfirmasi_rekonsiliasi_obat=true;
                        akses.satu_sehat_kirim_clinicalimpression=true;
                        akses.penilaian_pasien_terminal=true;
                        akses.surat_persetujuan_rawat_inap=true;
                        akses.monitoring_reaksi_tranfusi=true;
                        akses.penilaian_korban_kekerasan=true;
                        akses.penilaian_lanjutan_resiko_jatuh_lansia=true;
                        akses.mpp_skrining=true;
                        akses.penilaian_pasien_penyakit_menular=true;
                        akses.edukasi_pasien_keluarga_rj=true;
                        akses.pemantauan_pews_dewasa=true;
                        akses.penilaian_tambahan_bunuh_diri=true;
                        akses.bpjs_antrean_pertanggal=true;
                        akses.penilaian_tambahan_perilaku_kekerasan=true;
                        akses.penilaian_tambahan_beresiko_melarikan_diri=true;
                        akses.persetujuan_penundaan_pelayanan=true;
                        akses.sisa_diet_pasien=true;
                        akses.penilaian_awal_medis_ralan_bedah_mulut=true;
                        akses.penilaian_pasien_keracunan=true;
                        akses.pemantauan_meows_obstetri=true;
                        akses.catatan_adime_gizi=true;
                        akses.pengajuan_biaya=true;
                        akses.penilaian_awal_keperawatan_ralan_geriatri=true;
                        akses.master_masalah_keperawatan_geriatri=true;
                        akses.master_rencana_keperawatan_geriatri=true;
                        akses.checklist_kriteria_masuk_hcu=true;
                        akses.checklist_kriteria_keluar_hcu=true;
                        akses.penilaian_risiko_dekubitus=true;
                        akses.master_menolak_anjuran_medis=true;
                        akses.penolakan_anjuran_medis=true;
                        akses.laporan_tahunan_penolakan_anjuran_medis=true;
                        akses.template_laporan_operasi=true;
                        akses.hasil_tindakan_eswl=true;
                        akses.checklist_kriteria_masuk_icu=true;
                        akses.checklist_kriteria_keluar_icu=true;
                        akses.akses_dokter_lain_rawat_jalan=true;
                        akses.follow_up_dbd=true;
                        akses.penilaian_risiko_jatuh_neonatus=true;
                        akses.persetujuan_pengajuan_biaya=true;
                        akses.pemeriksaan_fisik_ralan_per_penyakit=true;
                        akses.penilaian_lanjutan_resiko_jatuh_geriatri=true;
                        akses.pemantauan_ews_neonatus=true;
                        akses.validasi_persetujuan_pengajuan_biaya=true;
                        akses.riwayat_perawatan_icare_bpjs=true;
                        akses.rekap_pengajuan_biaya=true;
                        akses.penilaian_awal_medis_ralan_kulit_kelamin=true;
                        akses.akun_host_to_host_bank_mandiri=true;
                        akses.penilaian_medis_hemodialisa=true;
                        akses.penilaian_level_kecemasan_ranap_anak=true;
                        akses.penilaian_lanjutan_resiko_jatuh_psikiatri=true;
                        akses.penilaian_lanjutan_skrining_fungsional=true;
                        akses.penilaian_medis_ralan_rehab_medik=true;
                        akses.laporan_anestesi=true;
                        akses.template_persetujuan_penolakan_tindakan=true;
                        akses.penilaian_medis_ralan_gawat_darurat_psikiatri=true;
                        akses.bpjs_referensi_setting_apotek=true;
                        akses.bpjs_referensi_obat_apotek=true;
                        akses.bpjs_mapping_obat_apotek=true;
                        akses.pembayaran_bank_mandiri=true;
                        akses.penilaian_ulang_nyeri=true;
                        akses.penilaian_terapi_wicara=true;
                        akses.bpjs_obat_23hari_apotek=true;
                        akses.pengkajian_restrain=true;
                        akses.bpjs_kunjungan_sep_apotek=true;
                        akses.bpjs_monitoring_klaim_apotek=true;
                        akses.bpjs_daftar_pelayanan_obat_apotek=true;
                        akses.penilaian_awal_medis_ralan_paru=true;
                        akses.catatan_keperawatan_ralan=true;
                        akses.catatan_persalinan=true;
                        akses.skor_aldrette_pasca_anestesi=true;
                        akses.skor_steward_pasca_anestesi=true;
                        akses.skor_bromage_pasca_anestesi=true;
                        akses.penilaian_pre_induksi=true;
                        akses.hasil_usg_urologi=true;
                        akses.hasil_usg_gynecologi=true;
                        akses.hasil_pemeriksaan_ekg=true;
                        akses.hapus_edit_sep_bpjs=true;
                        akses.satu_sehat_kirim_diet=true;
                        akses.satu_sehat_mapping_obat=true;
                        akses.dapur_ringkasan_pembelian=true;
                        akses.satu_sehat_kirim_medication=true;
                        akses.satu_sehat_kirim_medicationrequest=true;
                        akses.penatalaksanaan_terapi_okupasi=true;
                        akses.satu_sehat_kirim_medicationdispense=true;
                        akses.hasil_usg_neonatus=true;
                        akses.hasil_endoskopi_faring_laring=true;
                        akses.satu_sehat_mapping_radiologi=true;
                        akses.satu_sehat_kirim_servicerequest_radiologi=true;
                        akses.hasil_endoskopi_hidung=true;
                        akses.satu_sehat_kirim_specimen_radiologi=true;
                        akses.master_masalah_keperawatan_neonatus=true;
                        akses.master_rencana_keperawatan_neonatus=true;
                        akses.penilaian_awal_keperawatan_ranap_neonatus=true;
                        akses.satu_sehat_kirim_observation_radiologi=true;
                        akses.satu_sehat_kirim_diagnosticreport_radiologi=true;
                        akses.hasil_endoskopi_telinga=true;
                        akses.satu_sehat_mapping_lab=true;
                        akses.satu_sehat_kirim_servicerequest_lab=true;
                        akses.satu_sehat_kirim_servicerequest_labmb=true;
                        akses.satu_sehat_kirim_specimen_lab=true;
                        akses.satu_sehat_kirim_specimen_labmb=true;
                        akses.satu_sehat_kirim_observation_lab=true;
                        akses.satu_sehat_kirim_observation_labmb=true;
                        akses.satu_sehat_kirim_diagnosticreport_lab=true;
                        akses.satu_sehat_kirim_diagnosticreport_labmb=true;
                        akses.kepatuhan_kelengkapan_keselamatan_bedah=true;
                        akses.nilai_piutang_perjenis_bayar_per_bulan=true;
                        akses.ringkasan_piutang_jenis_bayar=true;
                        akses.penilaian_pasien_imunitas_rendah=true;
                        akses.balance_cairan=true;
                        akses.catatan_observasi_chbp=true;
                        akses.catatan_observasi_induksi_persalinan=true;
                        akses.skp_kategori_penilaian=true;
                        akses.skp_kriteria_penilaian=true;
                        akses.skp_penilaian=true;
                        akses.referensi_poli_mobilejknfktp=true;
                        akses.referensi_dokter_mobilejknfktp=true;
                        akses.skp_rekapitulasi_penilaian=true;
                        akses.pembayaran_pihak_ke3_bankmandiri=true;
                        akses.metode_pembayaran_bankmandiri=true;
                        akses.bank_tujuan_transfer_bankmandiri=true;
                        akses.kodetransaksi_tujuan_transfer_bankmandiri=true;
                        akses.konsultasi_medik=true;
                        akses.jawaban_konsultasi_medik=true;
                        akses.pcare_cek_alergi=true;
                        akses.pcare_cek_prognosa=true;
                        akses.data_sasaran_usiaproduktif=true;
                        akses.data_sasaran_usialansia=true;
                        akses.skrining_perilaku_merokok_sekolah_remaja=true;
                        akses.skrining_kekerasan_pada_perempuan=true;
                        akses.skrining_obesitas=true;
                        akses.skrining_risiko_kanker_payudara=true;
                        akses.skrining_risiko_kanker_paru=true;
                        akses.skrining_tbc=true;
                        akses.skrining_kesehatan_gigi_mulut_remaja=true;
                        akses.penilaian_awal_keperawatan_ranap_bayi=true;
                        akses.booking_mcu_perusahaan=true;
                        akses.catatan_observasi_restrain_nonfarma=true;
                        akses.catatan_observasi_ventilator=true;
                        akses.catatan_anestesi_sedasi=true;
                        akses.skrining_puma=true;
                        akses.satu_sehat_kirim_careplan=true;
                        akses.satu_sehat_kirim_medicationstatement=true;
                        akses.skrining_adiksi_nikotin=true;
                        akses.skrining_thalassemia=true;
                        akses.skrining_instrumen_sdq=true;
                        akses.skrining_instrumen_srq=true;
                        akses.checklist_pemberian_fibrinolitik=true;
                        akses.skrining_kanker_kolorektal=true;
                        akses.dapur_pemesanan=true;
                        akses.bayar_pesan_dapur=true;
                        akses.hutang_dapur=true;
                        akses.titip_faktur_dapur=true;
                        akses.validasi_tagihan_dapur=true;
                        akses.surat_pemesanan_dapur=true;
                        akses.pengajuan_barang_dapur=true;
                        akses.dapur_returbeli=true;
                        akses.hibah_dapur=true;
                        akses.ringkasan_penerimaan_dapur=true;
                        akses.ringkasan_pengajuan_dapur=true;
                        akses.ringkasan_pemesanan_dapur=true;
                        akses.ringkasan_returbeli_dapur=true;
                        akses.ringkasan_stokkeluar_dapur=true;
                        akses.dapur_stokkeluar_pertanggal=true;
                        akses.sirkulasi_dapur=true;
                        akses.sirkulasi_dapur2=true;
                        akses.verifikasi_penerimaan_dapur=true;
                        akses.nilai_penerimaan_vendor_dapur_perbulan=true;
                        akses.ringkasan_hutang_vendor_dapur=true;
                        akses.penilaian_psikologi_klinis=true;
                        akses.penilaian_awal_medis_ranap_neonatus=true;
                        akses.penilaian_derajat_dehidrasi=true;
                        akses.ringkasan_jasa_tindakan_medis=true;
                        akses.pendapatan_per_akun=true;
                        akses.hasil_pemeriksaan_echo=true;
                        akses.penilaian_bayi_baru_lahir=true;
                        akses.rl1_3_ketersediaan_kamar=true;
                        akses.pendapatan_per_akun_closing=true;
                        akses.pengeluaran_pengeluaran=true;
                        akses.skrining_diabetes_melitus=true;
                        akses.laporan_tindakan=true;
                        akses.pelaksanaan_informasi_edukasi=true;
                        akses.layanan_kedokteran_fisik_rehabilitasi=true;
                        akses.skrining_kesehatan_gigi_mulut_balita=true;
                        akses.skrining_anemia=true;
                        akses.layanan_program_kfr=true;
                        akses.skrining_hipertensi=true;
                        akses.skrining_kesehatan_penglihatan=true;
                        akses.catatan_observasi_hemodialisa=true;
                        akses.skrining_kesehatan_gigi_mulut_dewasa=true;
                        akses.skrining_risiko_kanker_serviks=true;
                        akses.catatan_cairan_hemodialisa=true;
                        akses.skrining_kesehatan_gigi_mulut_lansia=true;
                        akses.skrining_indra_pendengaran=true;
                        akses.catatan_pengkajian_paska_operasi=true;
                        akses.skrining_frailty_syndrome=true;
                        akses.sirkulasi_cssd=true;
                        akses.lama_pelayanan_cssd=true;
                        akses.catatan_observasi_bayi=true;
                        akses.riwayat_surat_peringatan=true;
                        akses.master_kesimpulan_anjuran_mcu=true;
                        akses.kategori_piutang_jasa_perusahaan=true;
                        akses.piutang_jasa_perusahaan=true;
                        akses.bayar_piutang_jasa_perusahaan=true;
                        akses.piutang_jasa_perusahaan_belum_lunas=true;
                        akses.checklist_kesiapan_anestesi=true;
                        akses.piutang_peminjaman_uang_belum_lunas=true;
                        akses.hasil_pemeriksaan_slit_lamp=true;
                        akses.hasil_pemeriksaan_oct=true;
                        akses.beban_hutang_lain=true;
                        akses.poli_asal_pasien_ranap=true;
                        akses.pemberi_hutang_lain=true;
                        akses.dokter_asal_pasien_ranap=true;
                        akses.duta_parkir_rekap_keluar=true;
                        akses.surat_keterangan_layak_terbang=true;
                        akses.bayar_beban_hutang_lain=true;
                        akses.surat_persetujuan_pemeriksaan_hiv=true;
                        akses.skrining_instrumen_acrs=true;
                        akses.surat_pernyataan_memilih_dpjp=true;
                        akses.skrining_instrumen_mental_emosional=true;
                        akses.pelanggan_lab_kesehatan_lingkungan=true;
                        akses.kriteria_masuk_nicu=true;
                        akses.kriteria_keluar_nicu=true;
                        akses.penilaian_medis_ranap_psikiatrik=true;
                        akses.kriteria_masuk_picu=true;
                        akses.kriteria_keluar_picu=true;
                        akses.master_sampel_bakumutu=true;
                        akses.skrining_instrumen_amt=true;
                        akses.parameter_pengujian_lab_kesehatan_lingkungan=true;
                        akses.nilai_normal_baku_mutu_lab_kesehatan_lingkungan=true;
                        akses.skrining_pneumonia_severity_index=true;
                        akses.permintaan_pengujian_sampel_lab_kesehatan_lingkungan=true;
                        akses.penilaian_awal_medis_ralan_jantung=true;
                        akses.penilaian_awal_medis_ralan_urologi=true;
                        akses.hasil_pemeriksaan_treadmill=true;
                        akses.hasil_pemeriksaan_echo_pediatrik=true;
                    }else if(rs2.getRow()>=1){   
                        rs2.beforeFirst();
                        rs2.next();
                        akses.kode=user;
                        akses.penyakit=rs2.getBoolean("penyakit");
                        akses.obat_penyakit=rs2.getBoolean("obat_penyakit");
                        akses.dokter=rs2.getBoolean("dokter");
                        akses.jadwal_praktek=rs2.getBoolean("jadwal_praktek");
                        akses.petugas=rs2.getBoolean("petugas");
                        akses.pasien=rs2.getBoolean("pasien");
                        akses.registrasi=rs2.getBoolean("registrasi");
                        akses.tindakan_ralan=rs2.getBoolean("tindakan_ralan");
                        akses.kamar_inap=rs2.getBoolean("kamar_inap");
                        akses.tindakan_ranap=rs2.getBoolean("tindakan_ranap");
                        akses.operasi=rs2.getBoolean("operasi");
                        akses.rujukan_keluar=rs2.getBoolean("rujukan_keluar");
                        akses.rujukan_masuk=rs2.getBoolean("rujukan_masuk");
                        akses.beri_obat=rs2.getBoolean("beri_obat");
                        akses.resep_pulang=rs2.getBoolean("resep_pulang");
                        akses.pasien_meninggal=rs2.getBoolean("pasien_meninggal");
                        akses.diet_pasien=rs2.getBoolean("diet_pasien");
                        akses.kelahiran_bayi=rs2.getBoolean("kelahiran_bayi");
                        akses.periksa_lab=rs2.getBoolean("periksa_lab");
                        akses.periksa_radiologi=rs2.getBoolean("periksa_radiologi");
                        akses.kasir_ralan=rs2.getBoolean("kasir_ralan");
                        akses.deposit_pasien=rs2.getBoolean("deposit_pasien");
                        akses.piutang_pasien=rs2.getBoolean("piutang_pasien");
                        akses.peminjaman_berkas=rs2.getBoolean("peminjaman_berkas");
                        akses.barcode=rs2.getBoolean("barcode");
                        akses.presensi_harian=rs2.getBoolean("presensi_harian");
                        akses.presensi_bulanan=rs2.getBoolean("presensi_bulanan");
                        akses.pegawai_admin=rs2.getBoolean("pegawai_admin");
                        akses.pegawai_user=rs2.getBoolean("pegawai_user");
                        akses.suplier=rs2.getBoolean("suplier");
                        akses.satuan_barang=rs2.getBoolean("satuan_barang");
                        akses.konversi_satuan=rs2.getBoolean("konversi_satuan");
                        akses.jenis_barang=rs2.getBoolean("jenis_barang");
                        akses.obat=rs2.getBoolean("obat");
                        akses.stok_opname_obat=rs2.getBoolean("stok_opname_obat");
                        akses.stok_obat_pasien=rs2.getBoolean("stok_obat_pasien");
                        akses.pengadaan_obat=rs2.getBoolean("pengadaan_obat");
                        akses.pemesanan_obat=rs2.getBoolean("pemesanan_obat");
                        akses.penjualan_obat=rs2.getBoolean("penjualan_obat");
                        akses.piutang_obat=rs2.getBoolean("piutang_obat");
                        akses.retur_ke_suplier=rs2.getBoolean("retur_ke_suplier");
                        akses.retur_dari_pembeli=rs2.getBoolean("retur_dari_pembeli");
                        akses.retur_obat_ranap=rs2.getBoolean("retur_obat_ranap");
                        akses.retur_piutang_pasien=rs2.getBoolean("retur_piutang_pasien");
                        akses.keuntungan_penjualan=rs2.getBoolean("keuntungan_penjualan");
                        akses.keuntungan_beri_obat=rs2.getBoolean("keuntungan_beri_obat");
                        akses.sirkulasi_obat=rs2.getBoolean("sirkulasi_obat");
                        akses.ipsrs_barang=rs2.getBoolean("ipsrs_barang");
                        akses.ipsrs_pengadaan_barang=rs2.getBoolean("ipsrs_pengadaan_barang");
                        akses.ipsrs_stok_keluar=rs2.getBoolean("ipsrs_stok_keluar");
                        akses.ipsrs_jenis_barang=rs2.getBoolean("ipsrs_jenis_barang");
                        akses.ipsrs_rekap_pengadaan=rs2.getBoolean("ipsrs_rekap_pengadaan");
                        akses.ipsrs_rekap_stok_keluar=rs2.getBoolean("ipsrs_rekap_stok_keluar");
                        akses.ipsrs_pengeluaran_harian=rs2.getBoolean("ipsrs_pengeluaran_harian");
                        akses.inventaris_jenis=rs2.getBoolean("inventaris_jenis");
                        akses.inventaris_kategori=rs2.getBoolean("inventaris_kategori");
                        akses.inventaris_merk=rs2.getBoolean("inventaris_merk");
                        akses.inventaris_ruang=rs2.getBoolean("inventaris_ruang");
                        akses.inventaris_produsen=rs2.getBoolean("inventaris_produsen");
                        akses.inventaris_koleksi=rs2.getBoolean("inventaris_koleksi");
                        akses.inventaris_inventaris=rs2.getBoolean("inventaris_inventaris");
                        akses.inventaris_sirkulasi=rs2.getBoolean("inventaris_sirkulasi");
                        akses.parkir_jenis=rs2.getBoolean("parkir_jenis");
                        akses.parkir_in=rs2.getBoolean("parkir_in");
                        akses.parkir_out=rs2.getBoolean("parkir_out");
                        akses.parkir_rekap_harian=rs2.getBoolean("parkir_rekap_harian");
                        akses.parkir_rekap_bulanan=rs2.getBoolean("parkir_rekap_bulanan");
                        akses.informasi_kamar=rs2.getBoolean("informasi_kamar");
                        akses.harian_tindakan_poli=rs2.getBoolean("harian_tindakan_poli");
                        akses.obat_per_poli=rs2.getBoolean("obat_per_poli");
                        akses.obat_per_kamar=rs2.getBoolean("obat_per_kamar");
                        akses.obat_per_dokter_ralan=rs2.getBoolean("obat_per_dokter_ralan");
                        akses.obat_per_dokter_ranap=rs2.getBoolean("obat_per_dokter_ranap");
                        akses.harian_dokter=rs2.getBoolean("harian_dokter");
                        akses.bulanan_dokter=rs2.getBoolean("bulanan_dokter");
                        akses.harian_paramedis=rs2.getBoolean("harian_paramedis");
                        akses.bulanan_paramedis=rs2.getBoolean("bulanan_paramedis");
                        akses.pembayaran_ralan=rs2.getBoolean("pembayaran_ralan");
                        akses.pembayaran_ranap=rs2.getBoolean("pembayaran_ranap");
                        akses.rekap_pembayaran_ralan=rs2.getBoolean("rekap_pembayaran_ralan");
                        akses.rekap_pembayaran_ranap=rs2.getBoolean("rekap_pembayaran_ranap");
                        akses.tagihan_masuk=rs2.getBoolean("tagihan_masuk");
                        akses.tambahan_biaya=rs2.getBoolean("tambahan_biaya");
                        akses.potongan_biaya=rs2.getBoolean("potongan_biaya");
                        akses.resep_obat=rs2.getBoolean("resep_obat");
                        akses.resume_pasien=rs2.getBoolean("resume_pasien");
                        akses.penyakit_ralan=rs2.getBoolean("penyakit_ralan");
                        akses.penyakit_ranap=rs2.getBoolean("penyakit_ranap");
                        akses.kamar=rs2.getBoolean("kamar");
                        akses.tarif_ralan=rs2.getBoolean("tarif_ralan");
                        akses.tarif_ranap=rs2.getBoolean("tarif_ranap");
                        akses.tarif_lab=rs2.getBoolean("tarif_lab");
                        akses.tarif_radiologi=rs2.getBoolean("tarif_radiologi");
                        akses.tarif_operasi=rs2.getBoolean("tarif_operasi");
                        akses.akun_rekening=rs2.getBoolean("akun_rekening");
                        akses.rekening_tahun=rs2.getBoolean("rekening_tahun");
                        akses.posting_jurnal=rs2.getBoolean("posting_jurnal");
                        akses.buku_besar=rs2.getBoolean("buku_besar");
                        akses.cashflow=rs2.getBoolean("cashflow");
                        akses.keuangan=rs2.getBoolean("keuangan");
                        akses.pengeluaran=rs2.getBoolean("pengeluaran");
                        akses.setup_pjlab=rs2.getBoolean("setup_pjlab");
                        akses.setup_otolokasi=rs2.getBoolean("setup_otolokasi");
                        akses.setup_jam_kamin=rs2.getBoolean("setup_jam_kamin");
                        akses.setup_embalase=rs2.getBoolean("setup_embalase");
                        akses.tracer_login=rs2.getBoolean("tracer_login");
                        akses.display=rs2.getBoolean("display");
                        akses.set_harga_obat=rs2.getBoolean("set_harga_obat");
                        akses.set_penggunaan_tarif=rs2.getBoolean("set_penggunaan_tarif");
                        akses.set_oto_ralan=rs2.getBoolean("set_oto_ralan");
                        akses.biaya_harian=rs2.getBoolean("biaya_harian");
                        akses.biaya_masuk_sekali=rs2.getBoolean("biaya_masuk_sekali");
                        akses.set_no_rm=rs2.getBoolean("set_no_rm");                    
                        akses.billing_ralan=rs2.getBoolean("billing_ralan"); 
                        akses.billing_ranap=rs2.getBoolean("billing_ranap"); 
                        akses.jm_ranap_dokter=rs2.getBoolean("jm_ranap_dokter");   
                        akses.igd=rs2.getBoolean("igd");                    
                        akses.barcoderalan=rs2.getBoolean("barcoderalan"); 
                        akses.barcoderanap=rs2.getBoolean("barcoderanap"); 
                        akses.set_harga_obat_ralan=rs2.getBoolean("set_harga_obat_ralan"); 
                        akses.set_harga_obat_ranap=rs2.getBoolean("set_harga_obat_ranap");
                        akses.penyakit_pd3i=rs2.getBoolean("penyakit_pd3i");
                        akses.surveilans_pd3i=rs2.getBoolean("surveilans_pd3i");
                        akses.surveilans_ralan=rs2.getBoolean("surveilans_ralan");
                        akses.diagnosa_pasien=rs2.getBoolean("diagnosa_pasien");
                        akses.surveilans_ranap=rs2.getBoolean("surveilans_ranap");
                        akses.admin=false;
                        akses.user=false;
                        akses.vakum=false;
                        akses.aplikasi=false;
                        akses.pny_takmenular_ranap=rs2.getBoolean("pny_takmenular_ranap");
                        akses.pny_takmenular_ralan=rs2.getBoolean("pny_takmenular_ralan");
                        akses.kunjungan_ralan=rs2.getBoolean("kunjungan_ralan");
                        akses.rl32=rs2.getBoolean("rl32");
                        akses.rl33=rs2.getBoolean("rl33");
                        akses.rl37=rs2.getBoolean("rl37");
                        akses.rl38=rs2.getBoolean("rl38");
                        akses.harian_tindakan_dokter=rs2.getBoolean("harian_tindakan_dokter");
                        akses.sms=rs2.getBoolean("sms");                    
                        akses.sidikjari=rs2.getBoolean("sidikjari");  
                        akses.jam_masuk=rs2.getBoolean("jam_masuk");  
                        akses.jadwal_pegawai=rs2.getBoolean("jadwal_pegawai");  
                        akses.parkir_barcode=rs2.getBoolean("parkir_barcode"); 
                        akses.set_nota=rs2.getBoolean("set_nota");
                        akses.dpjp_ranap=rs2.getBoolean("dpjp_ranap");
                        akses.mutasi_barang=rs2.getBoolean("mutasi_barang");
                        akses.rl34=rs2.getBoolean("rl34");
                        akses.rl36=rs2.getBoolean("rl36");
                        akses.fee_visit_dokter=rs2.getBoolean("fee_visit_dokter");
                        akses.fee_bacaan_ekg=rs2.getBoolean("fee_bacaan_ekg");
                        akses.fee_rujukan_rontgen=rs2.getBoolean("fee_rujukan_rontgen");
                        akses.fee_rujukan_ranap=rs2.getBoolean("fee_rujukan_ranap");
                        akses.fee_ralan=rs2.getBoolean("fee_ralan");
                        akses.akun_bayar=rs2.getBoolean("akun_bayar");
                        akses.bayar_pemesanan_obat=rs2.getBoolean("bayar_pemesanan_obat");
                        akses.obat_per_dokter_peresep=rs2.getBoolean("obat_per_dokter_peresep");
                        akses.pemasukan_lain=rs2.getBoolean("pemasukan_lain");
                        akses.pengaturan_rekening=rs2.getBoolean("pengaturan_rekening");
                        akses.closing_kasir=rs2.getBoolean("closing_kasir");
                        akses.keterlambatan_presensi=rs2.getBoolean("keterlambatan_presensi");
                        akses.set_harga_kamar=rs2.getBoolean("set_harga_kamar");
                        akses.rekap_per_shift=rs2.getBoolean("rekap_per_shift");
                        akses.bpjs_cek_nik=rs2.getBoolean("bpjs_cek_nik");
                        akses.bpjs_cek_kartu=rs2.getBoolean("bpjs_cek_kartu");
                        akses.bpjs_cek_riwayat=rs2.getBoolean("bpjs_cek_riwayat");
                        akses.obat_per_cara_bayar=rs2.getBoolean("obat_per_cara_bayar");
                        akses.kunjungan_ranap=rs2.getBoolean("kunjungan_ranap");
                        akses.bayar_piutang=rs2.getBoolean("bayar_piutang");
                        akses.payment_point=rs2.getBoolean("payment_point");
                        akses.bpjs_cek_nomor_rujukan=rs2.getBoolean("bpjs_cek_nomor_rujukan");
                        akses.icd9=rs2.getBoolean("icd9");
                        akses.darurat_stok=rs2.getBoolean("darurat_stok");
                        akses.retensi_rm=rs2.getBoolean("retensi_rm");
                        akses.temporary_presensi=rs2.getBoolean("temporary_presensi");
                        akses.jurnal_harian=rs2.getBoolean("jurnal_harian");
                        akses.sirkulasi_obat2=rs2.getBoolean("sirkulasi_obat2");
                        akses.edit_registrasi=rs2.getBoolean("edit_registrasi");
                        akses.bpjs_referensi_diagnosa=rs2.getBoolean("bpjs_referensi_diagnosa");
                        akses.bpjs_referensi_poli=rs2.getBoolean("bpjs_referensi_poli");
                        akses.industrifarmasi=rs2.getBoolean("industrifarmasi");
                        akses.harian_js=rs2.getBoolean("harian_js");
                        akses.bulanan_js=rs2.getBoolean("bulanan_js");
                        akses.harian_paket_bhp=rs2.getBoolean("harian_paket_bhp");
                        akses.bulanan_paket_bhp=rs2.getBoolean("bulanan_paket_bhp");
                        akses.piutang_pasien2=rs2.getBoolean("piutang_pasien2");
                        akses.bpjs_referensi_faskes=rs2.getBoolean("bpjs_referensi_faskes");
                        akses.bpjs_sep=rs2.getBoolean("bpjs_sep");
                        akses.pengambilan_utd=rs2.getBoolean("pengambilan_utd");
                        akses.tarif_utd=rs2.getBoolean("tarif_utd");
                        akses.pengambilan_utd2=rs2.getBoolean("pengambilan_utd2");
                        akses.utd_medis_rusak=rs2.getBoolean("utd_medis_rusak");
                        akses.pengambilan_penunjang_utd=rs2.getBoolean("pengambilan_penunjang_utd");
                        akses.pengambilan_penunjang_utd2=rs2.getBoolean("pengambilan_penunjang_utd2");
                        akses.utd_penunjang_rusak=rs2.getBoolean("utd_penunjang_rusak");
                        akses.suplier_penunjang=rs2.getBoolean("suplier_penunjang");
                        akses.utd_donor=rs2.getBoolean("utd_donor");
                        akses.bpjs_monitoring_klaim=rs2.getBoolean("bpjs_monitoring_klaim");
                        akses.utd_cekal_darah=rs2.getBoolean("utd_cekal_darah");
                        akses.utd_komponen_darah=rs2.getBoolean("utd_komponen_darah");
                        akses.utd_stok_darah=rs2.getBoolean("utd_stok_darah");
                        akses.utd_pemisahan_darah=rs2.getBoolean("utd_pemisahan_darah");
                        akses.harian_kamar=rs2.getBoolean("harian_kamar");
                        akses.rincian_piutang_pasien=rs2.getBoolean("rincian_piutang_pasien");
                        akses.keuntungan_beri_obat_nonpiutang=rs2.getBoolean("keuntungan_beri_obat_nonpiutang");
                        akses.reklasifikasi_ralan=rs2.getBoolean("reklasifikasi_ralan");
                        akses.reklasifikasi_ranap=rs2.getBoolean("reklasifikasi_ranap");
                        akses.utd_penyerahan_darah=rs2.getBoolean("utd_penyerahan_darah");
                        akses.hutang_obat=rs2.getBoolean("hutang_obat");
                        akses.riwayat_obat_alkes_bhp=rs2.getBoolean("riwayat_obat_alkes_bhp");
                        akses.sensus_harian_poli=rs2.getBoolean("sensus_harian_poli");
                        akses.rl4a=rs2.getBoolean("rl4a");
                        akses.aplicare_referensi_kamar=rs2.getBoolean("aplicare_referensi_kamar");
                        akses.aplicare_ketersediaan_kamar=rs2.getBoolean("aplicare_ketersediaan_kamar");
                        akses.inacbg_klaim_baru_otomatis=rs2.getBoolean("inacbg_klaim_baru_otomatis");
                        akses.inacbg_klaim_baru_manual=rs2.getBoolean("inacbg_klaim_baru_manual");
                        akses.inacbg_coder_nik=rs2.getBoolean("inacbg_coder_nik");
                        akses.mutasi_berkas=rs2.getBoolean("mutasi_berkas");
                        akses.akun_piutang=rs2.getBoolean("akun_piutang");
                        akses.harian_kso=rs2.getBoolean("harian_kso");
                        akses.bulanan_kso=rs2.getBoolean("bulanan_kso");                        
                        akses.harian_menejemen=rs2.getBoolean("harian_menejemen");
                        akses.bulanan_menejemen=rs2.getBoolean("bulanan_menejemen");
                        akses.inhealth_cek_eligibilitas=rs2.getBoolean("inhealth_cek_eligibilitas");
                        akses.inhealth_referensi_jenpel_ruang_rawat=rs2.getBoolean("inhealth_referensi_jenpel_ruang_rawat");
                        akses.inhealth_referensi_poli=rs2.getBoolean("inhealth_referensi_poli");
                        akses.inhealth_referensi_faskes=rs2.getBoolean("inhealth_referensi_faskes");
                        akses.inhealth_sjp=rs2.getBoolean("inhealth_sjp");
                        akses.piutang_ralan=rs2.getBoolean("piutang_ralan");
                        akses.piutang_ranap=rs2.getBoolean("piutang_ranap");
                        akses.detail_piutang_penjab=rs2.getBoolean("detail_piutang_penjab");
                        akses.lama_pelayanan_ralan=rs2.getBoolean("lama_pelayanan_ralan");
                        akses.catatan_pasien=rs2.getBoolean("catatan_pasien");
                        akses.rl4b=rs2.getBoolean("rl4b");                        
                        akses.rl4asebab=rs2.getBoolean("rl4asebab"); 
                        akses.rl4bsebab=rs2.getBoolean("rl4bsebab"); 
                        akses.data_HAIs=rs2.getBoolean("data_HAIs");
                        akses.harian_HAIs=rs2.getBoolean("harian_HAIs");
                        akses.bulanan_HAIs=rs2.getBoolean("bulanan_HAIs");                        
                        akses.hitung_bor=rs2.getBoolean("hitung_bor");
                        akses.perusahaan_pasien=rs2.getBoolean("perusahaan_pasien");
                        akses.resep_dokter=rs2.getBoolean("resep_dokter");
                        akses.lama_pelayanan_apotek=rs2.getBoolean("lama_pelayanan_apotek");
                        akses.hitung_alos=rs2.getBoolean("hitung_alos");
                        akses.detail_tindakan=rs2.getBoolean("detail_tindakan");
                        akses.rujukan_poli_internal=rs2.getBoolean("rujukan_poli_internal");
                        akses.rekap_poli_anak=rs2.getBoolean("rekap_poli_anak");
                        akses.grafik_kunjungan_poli=rs2.getBoolean("grafik_kunjungan_poli");
                        akses.grafik_kunjungan_perdokter=rs2.getBoolean("grafik_kunjungan_perdokter");
                        akses.grafik_kunjungan_perpekerjaan=rs2.getBoolean("grafik_kunjungan_perpekerjaan");
                        akses.grafik_kunjungan_perpendidikan=rs2.getBoolean("grafik_kunjungan_perpendidikan");
                        akses.grafik_kunjungan_pertahun=rs2.getBoolean("grafik_kunjungan_pertahun");
                        akses.berkas_digital_perawatan=rs2.getBoolean("berkas_digital_perawatan");
                        akses.penyakit_menular_ranap=rs2.getBoolean("penyakit_menular_ranap");
                        akses.penyakit_menular_ralan=rs2.getBoolean("penyakit_menular_ralan");
                        akses.grafik_kunjungan_perbulan=rs2.getBoolean("grafik_kunjungan_perbulan");                        
                        akses.grafik_kunjungan_pertanggal=rs2.getBoolean("grafik_kunjungan_pertanggal");
                        akses.grafik_kunjungan_demografi=rs2.getBoolean("grafik_kunjungan_demografi");
                        akses.grafik_kunjungan_statusdaftartahun=rs2.getBoolean("grafik_kunjungan_statusdaftartahun");
                        akses.grafik_kunjungan_statusdaftartahun2=rs2.getBoolean("grafik_kunjungan_statusdaftartahun2");                        
                        akses.grafik_kunjungan_statusdaftarbulan=rs2.getBoolean("grafik_kunjungan_statusdaftarbulan");
                        akses.grafik_kunjungan_statusdaftarbulan2=rs2.getBoolean("grafik_kunjungan_statusdaftarbulan2");
                        akses.grafik_kunjungan_statusdaftartanggal=rs2.getBoolean("grafik_kunjungan_statusdaftartanggal");
                        akses.grafik_kunjungan_statusdaftartanggal2=rs2.getBoolean("grafik_kunjungan_statusdaftartanggal2");
                        akses.grafik_kunjungan_statusbataltahun=rs2.getBoolean("grafik_kunjungan_statusbataltahun");
                        akses.grafik_kunjungan_statusbatalbulan=rs2.getBoolean("grafik_kunjungan_statusbatalbulan");
                        akses.pcare_cek_penyakit=rs2.getBoolean("pcare_cek_penyakit");
                        akses.grafik_kunjungan_statusbataltanggal=rs2.getBoolean("grafik_kunjungan_statusbataltanggal");
                        akses.kategori_barang=rs2.getBoolean("kategori_barang");
                        akses.golongan_barang=rs2.getBoolean("golongan_barang");
                        akses.pemberian_obat_pertanggal=rs2.getBoolean("pemberian_obat_pertanggal");
                        akses.penjualan_obat_pertanggal=rs2.getBoolean("penjualan_obat_pertanggal");
                        akses.pcare_cek_kesadaran=rs2.getBoolean("pcare_cek_kesadaran");                        
                        akses.pembatalan_periksa_dokter=rs2.getBoolean("pembatalan_periksa_dokter");
                        akses.pembayaran_per_unit=rs2.getBoolean("pembayaran_per_unit");
                        akses.rekap_pembayaran_per_unit=rs2.getBoolean("rekap_pembayaran_per_unit");                        
                        akses.grafik_kunjungan_percarabayar=rs2.getBoolean("grafik_kunjungan_percarabayar");
                        akses.ipsrs_pengadaan_pertanggal=rs2.getBoolean("ipsrs_pengadaan_pertanggal");
                        akses.ipsrs_stokkeluar_pertanggal=rs2.getBoolean("ipsrs_stokkeluar_pertanggal");
                        akses.grafik_kunjungan_ranaptahun=rs2.getBoolean("grafik_kunjungan_ranaptahun");
                        akses.pcare_cek_rujukan=rs2.getBoolean("pcare_cek_rujukan");
                        akses.grafik_lab_ralantahun=rs2.getBoolean("grafik_lab_ralantahun");                        
                        akses.grafik_rad_ralantahun=rs2.getBoolean("grafik_rad_ralantahun");
                        akses.cek_entry_ralan=rs2.getBoolean("cek_entry_ralan");
                        akses.inacbg_klaim_baru_manual2=rs2.getBoolean("inacbg_klaim_baru_manual2");
                        akses.permintaan_medis=rs2.getBoolean("permintaan_medis");
                        akses.rekap_permintaan_medis=rs2.getBoolean("rekap_permintaan_medis");
                        akses.surat_pemesanan_medis=rs2.getBoolean("surat_pemesanan_medis");
                        akses.permintaan_non_medis=rs2.getBoolean("permintaan_non_medis");
                        akses.rekap_permintaan_non_medis=rs2.getBoolean("rekap_permintaan_non_medis");
                        akses.surat_pemesanan_non_medis=rs2.getBoolean("surat_pemesanan_non_medis");
                        akses.grafik_per_perujuk=rs2.getBoolean("grafik_per_perujuk");
                        akses.bpjs_cek_prosedur=rs2.getBoolean("bpjs_cek_prosedur");
                        akses.bpjs_cek_kelas_rawat=rs2.getBoolean("bpjs_cek_kelas_rawat");
                        akses.bpjs_cek_dokter=rs2.getBoolean("bpjs_cek_dokter");
                        akses.bpjs_cek_spesialistik=rs2.getBoolean("bpjs_cek_spesialistik");
                        akses.bpjs_cek_ruangrawat=rs2.getBoolean("bpjs_cek_ruangrawat");                        
                        akses.bpjs_cek_carakeluar=rs2.getBoolean("bpjs_cek_carakeluar");
                        akses.bpjs_cek_pasca_pulang=rs2.getBoolean("bpjs_cek_pasca_pulang");
                        akses.detail_tindakan_okvk=rs2.getBoolean("detail_tindakan_okvk");
                        akses.billing_parsial=rs2.getBoolean("billing_parsial");
                        akses.bpjs_cek_nomor_rujukan_rs=rs2.getBoolean("bpjs_cek_nomor_rujukan_rs");
                        akses.bpjs_cek_rujukan_kartu_pcare=rs2.getBoolean("bpjs_cek_rujukan_kartu_pcare");
                        akses.bpjs_cek_rujukan_kartu_rs=rs2.getBoolean("bpjs_cek_rujukan_kartu_rs");
                        akses.akses_depo_obat=rs2.getBoolean("akses_depo_obat");
                        akses.bpjs_rujukan_keluar=rs2.getBoolean("bpjs_rujukan_keluar");
                        akses.grafik_lab_ralanbulan=rs2.getBoolean("grafik_lab_ralanbulan");
                        akses.pengeluaran_stok_apotek=rs2.getBoolean("pengeluaran_stok_apotek");
                        akses.grafik_rad_ralanbulan=rs2.getBoolean("grafik_rad_ralanbulan");
                        akses.detailjmdokter2=rs2.getBoolean("detailjmdokter2");
                        akses.pengaduan_pasien=rs2.getBoolean("pengaduan_pasien");
                        akses.grafik_lab_ralanhari=rs2.getBoolean("grafik_lab_ralanhari");
                        akses.grafik_rad_ralanhari=rs2.getBoolean("grafik_rad_ralanhari");
                        akses.sensus_harian_ralan=rs2.getBoolean("sensus_harian_ralan");
                        akses.metode_racik=rs2.getBoolean("metode_racik");
                        akses.pembayaran_akun_bayar=rs2.getBoolean("pembayaran_akun_bayar");
                        akses.pengguna_obat_resep=rs2.getBoolean("pengguna_obat_resep");
                        akses.rekap_pemesanan=rs2.getBoolean("rekap_pemesanan");
                        akses.master_berkas_pegawai=rs2.getBoolean("master_berkas_pegawai");
                        akses.berkas_kepegawaian=rs2.getBoolean("berkas_kepegawaian");
                        akses.riwayat_jabatan=rs2.getBoolean("riwayat_jabatan");
                        akses.riwayat_pendidikan=rs2.getBoolean("riwayat_pendidikan");
                        akses.riwayat_naik_gaji=rs2.getBoolean("riwayat_naik_gaji");
                        akses.kegiatan_ilmiah=rs2.getBoolean("kegiatan_ilmiah");
                        akses.riwayat_penghargaan=rs2.getBoolean("riwayat_penghargaan");
                        akses.riwayat_penelitian=rs2.getBoolean("riwayat_penelitian");
                        akses.penerimaan_non_medis=rs2.getBoolean("penerimaan_non_medis");
                        akses.bayar_pesan_non_medis=rs2.getBoolean("bayar_pesan_non_medis");
                        akses.hutang_barang_non_medis=rs2.getBoolean("hutang_barang_non_medis");
                        akses.rekap_pemesanan_non_medis=rs2.getBoolean("rekap_pemesanan_non_medis");                        
                        akses.insiden_keselamatan=rs2.getBoolean("insiden_keselamatan");
                        akses.insiden_keselamatan_pasien=rs2.getBoolean("insiden_keselamatan_pasien");
                        akses.grafik_ikp_pertahun=rs2.getBoolean("grafik_ikp_pertahun");
                        akses.grafik_ikp_perbulan=rs2.getBoolean("grafik_ikp_perbulan");
                        akses.grafik_ikp_pertanggal=rs2.getBoolean("grafik_ikp_pertanggal");
                        akses.riwayat_data_batch=rs2.getBoolean("riwayat_data_batch");
                        akses.grafik_ikp_jenis=rs2.getBoolean("grafik_ikp_jenis");
                        akses.grafik_ikp_dampak=rs2.getBoolean("grafik_ikp_dampak");
                        akses.piutang_akun_piutang=rs2.getBoolean("piutang_akun_piutang");
                        akses.grafik_kunjungan_per_agama=rs2.getBoolean("grafik_kunjungan_per_agama");
                        akses.grafik_kunjungan_per_umur=rs2.getBoolean("grafik_kunjungan_per_umur");
                        akses.suku_bangsa=rs2.getBoolean("suku_bangsa");
                        akses.bahasa_pasien=rs2.getBoolean("bahasa_pasien");
                        akses.golongan_tni=rs2.getBoolean("golongan_tni");
                        akses.satuan_tni=rs2.getBoolean("satuan_tni");
                        akses.jabatan_tni=rs2.getBoolean("jabatan_tni");
                        akses.pangkat_tni=rs2.getBoolean("pangkat_tni");
                        akses.golongan_polri=rs2.getBoolean("golongan_polri");
                        akses.satuan_polri=rs2.getBoolean("satuan_polri");
                        akses.jabatan_polri=rs2.getBoolean("jabatan_polri");
                        akses.pangkat_polri=rs2.getBoolean("pangkat_polri");
                        akses.cacat_fisik=rs2.getBoolean("cacat_fisik");
                        akses.grafik_kunjungan_suku=rs2.getBoolean("grafik_kunjungan_suku");
                        akses.grafik_kunjungan_bahasa=rs2.getBoolean("grafik_kunjungan_bahasa");
                        akses.booking_operasi=rs2.getBoolean("booking_operasi");
                        akses.mapping_poli_bpjs=rs2.getBoolean("mapping_poli_bpjs");
                        akses.grafik_kunjungan_per_cacat=rs2.getBoolean("grafik_kunjungan_per_cacat");
                        akses.barang_cssd=rs2.getBoolean("barang_cssd");
                        akses.skdp_bpjs=rs2.getBoolean("skdp_bpjs");
                        akses.booking_registrasi=rs2.getBoolean("booking_registrasi");
                        akses.bpjs_cek_propinsi=rs2.getBoolean("bpjs_cek_propinsi");
                        akses.bpjs_cek_kabupaten=rs2.getBoolean("bpjs_cek_kabupaten");
                        akses.bpjs_cek_kecamatan=rs2.getBoolean("bpjs_cek_kecamatan");
                        akses.bpjs_cek_dokterdpjp=rs2.getBoolean("bpjs_cek_dokterdpjp");
                        akses.bpjs_cek_riwayat_rujukanrs=rs2.getBoolean("bpjs_cek_riwayat_rujukanrs");
                        akses.bpjs_cek_tanggal_rujukan=rs2.getBoolean("bpjs_cek_tanggal_rujukan");
                        akses.permintaan_lab=rs2.getBoolean("permintaan_lab");
                        akses.permintaan_radiologi=rs2.getBoolean("permintaan_radiologi");
                        akses.surat_indeks=rs2.getBoolean("surat_indeks");
                        akses.surat_map=rs2.getBoolean("surat_map");
                        akses.surat_almari=rs2.getBoolean("surat_almari");
                        akses.surat_rak=rs2.getBoolean("surat_rak");
                        akses.surat_ruang=rs2.getBoolean("surat_ruang");
                        akses.surat_klasifikasi=rs2.getBoolean("surat_klasifikasi");
                        akses.surat_status=rs2.getBoolean("surat_status");
                        akses.surat_sifat=rs2.getBoolean("surat_sifat");
                        akses.surat_balas=rs2.getBoolean("surat_balas");
                        akses.surat_masuk=rs2.getBoolean("surat_masuk");
                        akses.pcare_cek_dokter=rs2.getBoolean("pcare_cek_dokter");
                        akses.pcare_cek_poli=rs2.getBoolean("pcare_cek_poli");
                        akses.pcare_cek_provider=rs2.getBoolean("pcare_cek_provider");
                        akses.pcare_cek_statuspulang=rs2.getBoolean("pcare_cek_statuspulang");
                        akses.pcare_cek_spesialis=rs2.getBoolean("pcare_cek_spesialis");
                        akses.pcare_cek_subspesialis=rs2.getBoolean("pcare_cek_subspesialis");
                        akses.pcare_cek_sarana=rs2.getBoolean("pcare_cek_sarana");
                        akses.pcare_cek_khusus=rs2.getBoolean("pcare_cek_khusus");
                        akses.pcare_cek_obat=rs2.getBoolean("pcare_cek_obat");
                        akses.pcare_cek_tindakan=rs2.getBoolean("pcare_cek_tindakan");
                        akses.pcare_cek_faskessubspesialis=rs2.getBoolean("pcare_cek_faskessubspesialis");
                        akses.pcare_cek_faskesalihrawat=rs2.getBoolean("pcare_cek_faskesalihrawat");
                        akses.pcare_cek_faskesthalasemia=rs2.getBoolean("pcare_cek_faskesthalasemia");
                        akses.pcare_mapping_obat=rs2.getBoolean("pcare_mapping_obat");
                        akses.pcare_mapping_tindakan=rs2.getBoolean("pcare_mapping_tindakan");
                        akses.pcare_club_prolanis=rs2.getBoolean("pcare_club_prolanis");
                        akses.pcare_mapping_poli=rs2.getBoolean("pcare_mapping_poli");
                        akses.pcare_kegiatan_kelompok=rs2.getBoolean("pcare_kegiatan_kelompok");
                        akses.pcare_mapping_tindakan_ranap=rs2.getBoolean("pcare_mapping_tindakan_ranap");
                        akses.pcare_peserta_kegiatan_kelompok=rs2.getBoolean("pcare_peserta_kegiatan_kelompok");
                        akses.sirkulasi_obat3=rs2.getBoolean("sirkulasi_obat3");
                        akses.bridging_pcare_daftar=rs2.getBoolean("bridging_pcare_daftar");
                        akses.pcare_mapping_dokter=rs2.getBoolean("pcare_mapping_dokter");
                        akses.ranap_per_ruang=rs2.getBoolean("ranap_per_ruang");
                        akses.penyakit_ranap_cara_bayar=rs2.getBoolean("penyakit_ranap_cara_bayar");
                        akses.anggota_militer_dirawat=rs2.getBoolean("anggota_militer_dirawat");
                        akses.set_input_parsial=rs2.getBoolean("set_input_parsial");
                        akses.lama_pelayanan_radiologi=rs2.getBoolean("lama_pelayanan_radiologi");
                        akses.lama_pelayanan_lab=rs2.getBoolean("lama_pelayanan_lab");
                        akses.bpjs_cek_sep=rs2.getBoolean("bpjs_cek_sep");
                        akses.catatan_perawatan=rs2.getBoolean("catatan_perawatan");
                        akses.surat_keluar=rs2.getBoolean("surat_keluar");
                        akses.kegiatan_farmasi=rs2.getBoolean("kegiatan_farmasi");
                        akses.stok_opname_logistik=rs2.getBoolean("stok_opname_logistik");
                        akses.sirkulasi_non_medis=rs2.getBoolean("sirkulasi_non_medis");
                        akses.rekap_lab_pertahun=rs2.getBoolean("rekap_lab_pertahun");
                        akses.perujuk_lab_pertahun=rs2.getBoolean("perujuk_lab_pertahun");
                        akses.rekap_radiologi_pertahun=rs2.getBoolean("rekap_radiologi_pertahun");
                        akses.perujuk_radiologi_pertahun=rs2.getBoolean("perujuk_radiologi_pertahun");
                        akses.jumlah_porsi_diet=rs2.getBoolean("jumlah_porsi_diet");
                        akses.jumlah_macam_diet=rs2.getBoolean("jumlah_macam_diet");
                        akses.payment_point2=rs2.getBoolean("payment_point2");
                        akses.pembayaran_akun_bayar2=rs2.getBoolean("pembayaran_akun_bayar2");
                        akses.hapus_nota_salah=rs2.getBoolean("hapus_nota_salah");
                        akses.hais_perbangsal=rs2.getBoolean("hais_perbangsal");
                        akses.ppn_obat=rs2.getBoolean("ppn_obat");
                        akses.saldo_akun_perbulan=rs2.getBoolean("saldo_akun_perbulan");
                        akses.display_apotek=rs2.getBoolean("display_apotek");
                        akses.sisrute_referensi_faskes=rs2.getBoolean("sisrute_referensi_faskes");
                        akses.sisrute_referensi_alasanrujuk=rs2.getBoolean("sisrute_referensi_alasanrujuk");
                        akses.sisrute_referensi_diagnosa=rs2.getBoolean("sisrute_referensi_diagnosa");
                        akses.sisrute_rujukan_masuk=rs2.getBoolean("sisrute_rujukan_masuk");
                        akses.sisrute_rujukan_keluar=rs2.getBoolean("sisrute_rujukan_keluar");
                        akses.bpjs_cek_skdp=rs2.getBoolean("bpjs_cek_skdp");
                        akses.data_batch=rs2.getBoolean("data_batch");
                        akses.kunjungan_permintaan_lab=rs2.getBoolean("kunjungan_permintaan_lab");
                        akses.kunjungan_permintaan_lab2=rs2.getBoolean("kunjungan_permintaan_lab2");
                        akses.kunjungan_permintaan_radiologi=rs2.getBoolean("kunjungan_permintaan_radiologi");
                        akses.kunjungan_permintaan_radiologi2=rs2.getBoolean("kunjungan_permintaan_radiologi2");
                        akses.pcare_pemberian_obat=rs2.getBoolean("pcare_pemberian_obat");
                        akses.pcare_pemberian_tindakan=rs2.getBoolean("pcare_pemberian_tindakan");
                        akses.pembayaran_akun_bayar3=rs2.getBoolean("pembayaran_akun_bayar3");
                        akses.password_asuransi=rs2.getBoolean("password_asuransi");
                        akses.kemenkes_sitt=rs2.getBoolean("kemenkes_sitt");
                        akses.siranap_ketersediaan_kamar=rs2.getBoolean("siranap_ketersediaan_kamar");
                        akses.grafik_tb_periodelaporan=rs2.getBoolean("grafik_tb_periodelaporan");
                        akses.grafik_tb_rujukan=rs2.getBoolean("grafik_tb_rujukan");
                        akses.grafik_tb_riwayat=rs2.getBoolean("grafik_tb_riwayat");
                        akses.grafik_tb_tipediagnosis=rs2.getBoolean("grafik_tb_tipediagnosis");
                        akses.grafik_tb_statushiv=rs2.getBoolean("grafik_tb_statushiv");
                        akses.grafik_tb_skoringanak=rs2.getBoolean("grafik_tb_skoringanak");
                        akses.grafik_tb_konfirmasiskoring5=rs2.getBoolean("grafik_tb_konfirmasiskoring5");
                        akses.grafik_tb_konfirmasiskoring6=rs2.getBoolean("grafik_tb_konfirmasiskoring6");
                        akses.grafik_tb_sumberobat=rs2.getBoolean("grafik_tb_sumberobat");
                        akses.grafik_tb_hasilakhirpengobatan=rs2.getBoolean("grafik_tb_hasilakhirpengobatan");
                        akses.grafik_tb_hasilteshiv=rs2.getBoolean("grafik_tb_hasilteshiv");
                        akses.kadaluarsa_batch=rs2.getBoolean("kadaluarsa_batch");
                        akses.sisa_stok=rs2.getBoolean("sisa_stok");
                        akses.obat_per_resep=rs2.getBoolean("obat_per_resep");
                        akses.pemakaian_air_pdam=rs2.getBoolean("pemakaian_air_pdam");
                        akses.limbah_b3_medis=rs2.getBoolean("limbah_b3_medis");
                        akses.grafik_air_pdam_pertanggal=rs2.getBoolean("grafik_air_pdam_pertanggal");
                        akses.grafik_air_pdam_perbulan=rs2.getBoolean("grafik_air_pdam_perbulan");
                        akses.grafik_limbahb3_pertanggal=rs2.getBoolean("grafik_limbahb3_pertanggal");
                        akses.grafik_limbahb3_perbulan=rs2.getBoolean("grafik_limbahb3_perbulan");
                        akses.limbah_domestik=rs2.getBoolean("limbah_domestik");
                        akses.grafik_limbahdomestik_pertanggal=rs2.getBoolean("grafik_limbahdomestik_pertanggal");
                        akses.grafik_limbahdomestik_perbulan=rs2.getBoolean("grafik_limbahdomestik_perbulan");
                        akses.mutu_air_limbah=rs2.getBoolean("mutu_air_limbah");
                        akses.pest_control=rs2.getBoolean("pest_control");
                        akses.ruang_perpustakaan=rs2.getBoolean("ruang_perpustakaan");
                        akses.kategori_perpustakaan=rs2.getBoolean("kategori_perpustakaan");
                        akses.jenis_perpustakaan=rs2.getBoolean("jenis_perpustakaan");
                        akses.pengarang_perpustakaan=rs2.getBoolean("pengarang_perpustakaan");
                        akses.penerbit_perpustakaan=rs2.getBoolean("penerbit_perpustakaan");
                        akses.koleksi_perpustakaan=rs2.getBoolean("koleksi_perpustakaan");
                        akses.inventaris_perpustakaan=rs2.getBoolean("inventaris_perpustakaan");
                        akses.set_peminjaman_perpustakaan=rs2.getBoolean("set_peminjaman_perpustakaan");
                        akses.denda_perpustakaan=rs2.getBoolean("denda_perpustakaan");
                        akses.anggota_perpustakaan=rs2.getBoolean("anggota_perpustakaan");
                        akses.peminjaman_perpustakaan=rs2.getBoolean("peminjaman_perpustakaan");
                        akses.bayar_denda_perpustakaan=rs2.getBoolean("bayar_denda_perpustakaan");
                        akses.ebook_perpustakaan=rs2.getBoolean("ebook_perpustakaan");
                        akses.jenis_cidera_k3rs=rs2.getBoolean("jenis_cidera_k3rs");
                        akses.penyebab_k3rs=rs2.getBoolean("penyebab_k3rs");
                        akses.jenis_luka_k3rs=rs2.getBoolean("jenis_luka_k3rs");
                        akses.lokasi_kejadian_k3rs=rs2.getBoolean("lokasi_kejadian_k3rs");
                        akses.dampak_cidera_k3rs=rs2.getBoolean("dampak_cidera_k3rs");
                        akses.jenis_pekerjaan_k3rs=rs2.getBoolean("jenis_pekerjaan_k3rs");
                        akses.bagian_tubuh_k3rs=rs2.getBoolean("bagian_tubuh_k3rs");
                        akses.peristiwa_k3rs=rs2.getBoolean("peristiwa_k3rs");
                        akses.grafik_k3_pertahun=rs2.getBoolean("grafik_k3_pertahun");
                        akses.grafik_k3_perbulan=rs2.getBoolean("grafik_k3_perbulan");
                        akses.grafik_k3_pertanggal=rs2.getBoolean("grafik_k3_pertanggal");
                        akses.grafik_k3_perjeniscidera=rs2.getBoolean("grafik_k3_perjeniscidera");
                        akses.grafik_k3_perpenyebab=rs2.getBoolean("grafik_k3_perpenyebab");
                        akses.grafik_k3_perjenisluka=rs2.getBoolean("grafik_k3_perjenisluka");
                        akses.grafik_k3_lokasikejadian=rs2.getBoolean("grafik_k3_lokasikejadian");
                        akses.grafik_k3_dampakcidera=rs2.getBoolean("grafik_k3_dampakcidera");
                        akses.grafik_k3_perjenispekerjaan=rs2.getBoolean("grafik_k3_perjenispekerjaan");
                        akses.grafik_k3_perbagiantubuh=rs2.getBoolean("grafik_k3_perbagiantubuh");
                        akses.jenis_cidera_k3rstahun=rs2.getBoolean("jenis_cidera_k3rstahun");
                        akses.penyebab_k3rstahun=rs2.getBoolean("penyebab_k3rstahun");
                        akses.jenis_luka_k3rstahun=rs2.getBoolean("jenis_luka_k3rstahun");
                        akses.lokasi_kejadian_k3rstahun=rs2.getBoolean("lokasi_kejadian_k3rstahun");
                        akses.dampak_cidera_k3rstahun=rs2.getBoolean("dampak_cidera_k3rstahun");
                        akses.jenis_pekerjaan_k3rstahun=rs2.getBoolean("jenis_pekerjaan_k3rstahun");
                        akses.bagian_tubuh_k3rstahun=rs2.getBoolean("bagian_tubuh_k3rstahun");
                        akses.sekrining_rawat_jalan=rs2.getBoolean("sekrining_rawat_jalan");
                        akses.bpjs_histori_pelayanan=rs2.getBoolean("bpjs_histori_pelayanan");
                        akses.rekap_mutasi_berkas=rs2.getBoolean("rekap_mutasi_berkas");
                        akses.skrining_ralan_pernapasan_pertahun=rs2.getBoolean("skrining_ralan_pernapasan_pertahun");
                        akses.pengajuan_barang_medis=rs2.getBoolean("pengajuan_barang_medis");
                        akses.pengajuan_barang_nonmedis=rs2.getBoolean("pengajuan_barang_nonmedis");
                        akses.grafik_kunjungan_ranapbulan=rs2.getBoolean("grafik_kunjungan_ranapbulan");
                        akses.grafik_kunjungan_ranaptanggal=rs2.getBoolean("grafik_kunjungan_ranaptanggal");
                        akses.grafik_kunjungan_ranap_peruang=rs2.getBoolean("grafik_kunjungan_ranap_peruang");
                        akses.kunjungan_bangsal_pertahun=rs2.getBoolean("kunjungan_bangsal_pertahun");
                        akses.grafik_jenjang_jabatanpegawai=rs2.getBoolean("grafik_jenjang_jabatanpegawai");
                        akses.grafik_bidangpegawai=rs2.getBoolean("grafik_bidangpegawai");
                        akses.grafik_departemenpegawai=rs2.getBoolean("grafik_departemenpegawai");
                        akses.grafik_pendidikanpegawai=rs2.getBoolean("grafik_pendidikanpegawai");
                        akses.grafik_sttswppegawai=rs2.getBoolean("grafik_sttswppegawai");
                        akses.grafik_sttskerjapegawai=rs2.getBoolean("grafik_sttskerjapegawai");
                        akses.grafik_sttspulangranap=rs2.getBoolean("grafik_sttspulangranap");
                        akses.kip_pasien_ranap=rs2.getBoolean("kip_pasien_ranap");
                        akses.kip_pasien_ralan=rs2.getBoolean("kip_pasien_ralan");
                        akses.bpjs_mapping_dokterdpjp=rs2.getBoolean("bpjs_mapping_dokterdpjp");
                        akses.data_triase_igd=rs2.getBoolean("data_triase_igd");
                        akses.master_triase_skala1=rs2.getBoolean("master_triase_skala1");
                        akses.master_triase_skala2=rs2.getBoolean("master_triase_skala2");
                        akses.master_triase_skala3=rs2.getBoolean("master_triase_skala3");
                        akses.master_triase_skala4=rs2.getBoolean("master_triase_skala4");
                        akses.master_triase_skala5=rs2.getBoolean("master_triase_skala5");
                        akses.master_triase_pemeriksaan=rs2.getBoolean("master_triase_pemeriksaan");
                        akses.master_triase_macamkasus=rs2.getBoolean("master_triase_macamkasus");
                        akses.rekap_permintaan_diet=rs2.getBoolean("rekap_permintaan_diet");
                        akses.daftar_pasien_ranap=rs2.getBoolean("daftar_pasien_ranap");
                        akses.daftar_pasien_ranaptni=rs2.getBoolean("daftar_pasien_ranaptni");
                        akses.pengajuan_asetinventaris=rs2.getBoolean("pengajuan_asetinventaris");
                        akses.item_apotek_jenis=rs2.getBoolean("item_apotek_jenis");
                        akses.item_apotek_kategori=rs2.getBoolean("item_apotek_kategori");
                        akses.item_apotek_golongan=rs2.getBoolean("item_apotek_golongan");
                        akses.item_apotek_industrifarmasi=rs2.getBoolean("item_apotek_industrifarmasi");
                        akses.obat10_terbanyak_poli=rs2.getBoolean("10_obat_terbanyak_poli");
                        akses.grafik_pengajuan_aset_urgensi=rs2.getBoolean("grafik_pengajuan_aset_urgensi");
                        akses.grafik_pengajuan_aset_status=rs2.getBoolean("grafik_pengajuan_aset_status");
                        akses.grafik_pengajuan_aset_departemen=rs2.getBoolean("grafik_pengajuan_aset_departemen");
                        akses.rekap_pengajuan_aset_departemen=rs2.getBoolean("rekap_pengajuan_aset_departemen");
                        akses.grafik_kelompok_jabatanpegawai=rs2.getBoolean("grafik_kelompok_jabatanpegawai");
                        akses.grafik_resiko_kerjapegawai=rs2.getBoolean("grafik_resiko_kerjapegawai");
                        akses.grafik_emergency_indexpegawai=rs2.getBoolean("grafik_emergency_indexpegawai");
                        akses.grafik_inventaris_ruang=rs2.getBoolean("grafik_inventaris_ruang");
                        akses.harian_HAIs2=rs2.getBoolean("harian_HAIs2");
                        akses.grafik_inventaris_jenis=rs2.getBoolean("grafik_inventaris_jenis");
                        akses.data_resume_pasien=rs2.getBoolean("data_resume_pasien");
                        akses.perkiraan_biaya_ranap=rs2.getBoolean("perkiraan_biaya_ranap");
                        akses.rekap_obat_poli=rs2.getBoolean("rekap_obat_poli");
                        akses.rekap_obat_pasien=rs2.getBoolean("rekap_obat_pasien");
                        akses.grafik_HAIs_pasienbangsal=rs2.getBoolean("grafik_HAIs_pasienbangsal");
                        akses.grafik_HAIs_pasienbulan=rs2.getBoolean("grafik_HAIs_pasienbulan");
                        akses.permintaan_perbaikan_inventaris=rs2.getBoolean("permintaan_perbaikan_inventaris");
                        akses.grafik_HAIs_laju_vap=rs2.getBoolean("grafik_HAIs_laju_vap");
                        akses.grafik_HAIs_laju_iad=rs2.getBoolean("grafik_HAIs_laju_iad");
                        akses.grafik_HAIs_laju_pleb=rs2.getBoolean("grafik_HAIs_laju_pleb");
                        akses.grafik_HAIs_laju_isk=rs2.getBoolean("grafik_HAIs_laju_isk");
                        akses.grafik_HAIs_laju_ilo=rs2.getBoolean("grafik_HAIs_laju_ilo");
                        akses.grafik_HAIs_laju_hap=rs2.getBoolean("grafik_HAIs_laju_hap");
                        akses.inhealth_mapping_poli=rs2.getBoolean("inhealth_mapping_poli");
                        akses.inhealth_mapping_dokter=rs2.getBoolean("inhealth_mapping_dokter");
                        akses.inhealth_mapping_tindakan_ralan=rs2.getBoolean("inhealth_mapping_tindakan_ralan");
                        akses.inhealth_mapping_tindakan_ranap=rs2.getBoolean("inhealth_mapping_tindakan_ranap");
                        akses.inhealth_mapping_tindakan_radiologi=rs2.getBoolean("inhealth_mapping_tindakan_radiologi");
                        akses.inhealth_mapping_tindakan_laborat=rs2.getBoolean("inhealth_mapping_tindakan_laborat");
                        akses.inhealth_mapping_tindakan_operasi=rs2.getBoolean("inhealth_mapping_tindakan_operasi");
                        akses.hibah_obat_bhp=rs2.getBoolean("hibah_obat_bhp");
                        akses.asal_hibah=rs2.getBoolean("asal_hibah");
                        akses.asuhan_gizi=rs2.getBoolean("asuhan_gizi");
                        akses.inhealth_kirim_tagihan=rs2.getBoolean("inhealth_kirim_tagihan");
                        akses.sirkulasi_obat4=rs2.getBoolean("sirkulasi_obat4");
                        akses.sirkulasi_obat5=rs2.getBoolean("sirkulasi_obat5");
                        akses.sirkulasi_non_medis2=rs2.getBoolean("sirkulasi_non_medis2");
                        akses.monitoring_asuhan_gizi=rs2.getBoolean("monitoring_asuhan_gizi");
                        akses.penerimaan_obat_perbulan=rs2.getBoolean("penerimaan_obat_perbulan");
                        akses.rekap_kunjungan=rs2.getBoolean("rekap_kunjungan");
                        akses.surat_sakit=rs2.getBoolean("surat_sakit");
                        akses.penilaian_awal_keperawatan_ralan=rs2.getBoolean("penilaian_awal_keperawatan_ralan");
                        akses.permintaan_diet=rs2.getBoolean("permintaan_diet");
                        akses.master_masalah_keperawatan=rs2.getBoolean("master_masalah_keperawatan");
                        akses.pengajuan_cuti=rs2.getBoolean("pengajuan_cuti");
                        akses.kedatangan_pasien=rs2.getBoolean("kedatangan_pasien");
                        akses.utd_pendonor=rs2.getBoolean("utd_pendonor");
                        akses.toko_suplier=rs2.getBoolean("toko_suplier");
                        akses.toko_jenis=rs2.getBoolean("toko_jenis");
                        akses.toko_set_harga=rs2.getBoolean("toko_set_harga");
                        akses.toko_barang=rs2.getBoolean("toko_barang");
                        akses.penagihan_piutang_pasien=rs2.getBoolean("penagihan_piutang_pasien");
                        akses.akun_penagihan_piutang=rs2.getBoolean("akun_penagihan_piutang");
                        akses.stok_opname_toko=rs2.getBoolean("stok_opname_toko");
                        akses.toko_riwayat_barang=rs2.getBoolean("toko_riwayat_barang");
                        akses.toko_surat_pemesanan=rs2.getBoolean("toko_surat_pemesanan");
                        akses.toko_pengajuan_barang=rs2.getBoolean("toko_pengajuan_barang");
                        akses.toko_penerimaan_barang=rs2.getBoolean("toko_penerimaan_barang");
                        akses.toko_pengadaan_barang=rs2.getBoolean("toko_pengadaan_barang");
                        akses.toko_hutang=rs2.getBoolean("toko_hutang");
                        akses.toko_bayar_pemesanan=rs2.getBoolean("toko_bayar_pemesanan");
                        akses.toko_member=rs2.getBoolean("toko_member");
                        akses.toko_penjualan=rs2.getBoolean("toko_penjualan");
                        akses.registrasi_poli_per_tanggal=rs2.getBoolean("registrasi_poli_per_tanggal");
                        akses.toko_piutang=rs2.getBoolean("toko_piutang");
                        akses.toko_retur_beli=rs2.getBoolean("toko_retur_beli");
                        akses.ipsrs_returbeli=rs2.getBoolean("ipsrs_returbeli");
                        akses.ipsrs_riwayat_barang=rs2.getBoolean("ipsrs_riwayat_barang");
                        akses.pasien_corona=rs2.getBoolean("pasien_corona");
                        akses.toko_pendapatan_harian=rs2.getBoolean("toko_pendapatan_harian");
                        akses.diagnosa_pasien_corona=rs2.getBoolean("diagnosa_pasien_corona");
                        akses.perawatan_pasien_corona=rs2.getBoolean("perawatan_pasien_corona");
                        akses.penilaian_awal_keperawatan_gigi=rs2.getBoolean("penilaian_awal_keperawatan_gigi");
                        akses.master_masalah_keperawatan_gigi=rs2.getBoolean("master_masalah_keperawatan_gigi");
                        akses.toko_bayar_piutang=rs2.getBoolean("toko_bayar_piutang");
                        akses.toko_piutang_harian=rs2.getBoolean("toko_piutang_harian");
                        akses.toko_penjualan_harian=rs2.getBoolean("toko_penjualan_harian");
                        akses.deteksi_corona=rs2.getBoolean("deteksi_corona");
                        akses.penilaian_awal_keperawatan_kebidanan=rs2.getBoolean("penilaian_awal_keperawatan_kebidanan");
                        akses.pengumuman_epasien=rs2.getBoolean("pengumuman_epasien");
                        akses.surat_hamil=rs2.getBoolean("surat_hamil");
                        akses.set_tarif_online=rs2.getBoolean("set_tarif_online");
                        akses.booking_periksa=rs2.getBoolean("booking_periksa");
                        akses.toko_sirkulasi=rs2.getBoolean("toko_sirkulasi");
                        akses.toko_retur_jual=rs2.getBoolean("toko_retur_jual");
                        akses.toko_retur_piutang=rs2.getBoolean("toko_retur_piutang");
                        akses.toko_sirkulasi2=rs2.getBoolean("toko_sirkulasi2");
                        akses.toko_keuntungan_barang=rs2.getBoolean("toko_keuntungan_barang");
                        akses.zis_pengeluaran_penerima_dankes=rs2.getBoolean("zis_pengeluaran_penerima_dankes");
                        akses.zis_penghasilan_penerima_dankes=rs2.getBoolean("zis_penghasilan_penerima_dankes");
                        akses.zis_ukuran_rumah_penerima_dankes=rs2.getBoolean("zis_ukuran_rumah_penerima_dankes");
                        akses.zis_dinding_rumah_penerima_dankes=rs2.getBoolean("zis_dinding_rumah_penerima_dankes");
                        akses.zis_lantai_rumah_penerima_dankes=rs2.getBoolean("zis_lantai_rumah_penerima_dankes");
                        akses.zis_atap_rumah_penerima_dankes=rs2.getBoolean("zis_atap_rumah_penerima_dankes");
                        akses.zis_kepemilikan_rumah_penerima_dankes=rs2.getBoolean("zis_kepemilikan_rumah_penerima_dankes");
                        akses.zis_kamar_mandi_penerima_dankes=rs2.getBoolean("zis_kamar_mandi_penerima_dankes");
                        akses.zis_dapur_rumah_penerima_dankes=rs2.getBoolean("zis_dapur_rumah_penerima_dankes");
                        akses.zis_kursi_rumah_penerima_dankes=rs2.getBoolean("zis_kursi_rumah_penerima_dankes");
                        akses.zis_kategori_phbs_penerima_dankes=rs2.getBoolean("zis_kategori_phbs_penerima_dankes");
                        akses.zis_elektronik_penerima_dankes=rs2.getBoolean("zis_elektronik_penerima_dankes");
                        akses.zis_ternak_penerima_dankes=rs2.getBoolean("zis_ternak_penerima_dankes");
                        akses.zis_jenis_simpanan_penerima_dankes=rs2.getBoolean("zis_jenis_simpanan_penerima_dankes");
                        akses.penilaian_awal_keperawatan_anak=rs2.getBoolean("penilaian_awal_keperawatan_anak");
                        akses.zis_kategori_asnaf_penerima_dankes=rs2.getBoolean("zis_kategori_asnaf_penerima_dankes");
                        akses.master_masalah_keperawatan_anak=rs2.getBoolean("master_masalah_keperawatan_anak");
                        akses.master_imunisasi=rs2.getBoolean("master_imunisasi");
                        akses.zis_patologis_penerima_dankes=rs2.getBoolean("zis_patologis_penerima_dankes");
                        akses.pcare_cek_kartu=rs2.getBoolean("pcare_cek_kartu");
                        akses.surat_bebas_narkoba=rs2.getBoolean("surat_bebas_narkoba");
                        akses.surat_keterangan_covid=rs2.getBoolean("surat_keterangan_covid");
                        akses.pemakaian_air_tanah=rs2.getBoolean("pemakaian_air_tanah");
                        akses.grafik_air_tanah_pertanggal=rs2.getBoolean("grafik_air_tanah_pertanggal");
                        akses.grafik_air_tanah_perbulan=rs2.getBoolean("grafik_air_tanah_perbulan");
                        akses.lama_pelayanan_poli=rs2.getBoolean("lama_pelayanan_poli");
                        akses.hemodialisa=rs2.getBoolean("hemodialisa");
                        akses.grafik_harian_hemodialisa=rs2.getBoolean("grafik_harian_hemodialisa");
                        akses.grafik_bulanan_hemodialisa=rs2.getBoolean("grafik_bulanan_hemodialisa");
                        akses.grafik_tahunan_hemodialisa=rs2.getBoolean("grafik_tahunan_hemodialisa");
                        akses.grafik_bulanan_meninggal=rs2.getBoolean("grafik_bulanan_meninggal");
                        akses.laporan_tahunan_irj=rs2.getBoolean("laporan_tahunan_irj");
                        akses.perbaikan_inventaris=rs2.getBoolean("perbaikan_inventaris");
                        akses.surat_cuti_hamil=rs2.getBoolean("surat_cuti_hamil");
                        akses.permintaan_stok_obat_pasien=rs2.getBoolean("permintaan_stok_obat_pasien");
                        akses.pemeliharaan_inventaris=rs2.getBoolean("pemeliharaan_inventaris");
                        akses.klasifikasi_pasien_ranap=rs2.getBoolean("klasifikasi_pasien_ranap");
                        akses.bulanan_klasifikasi_pasien_ranap=rs2.getBoolean("bulanan_klasifikasi_pasien_ranap");
                        akses.harian_klasifikasi_pasien_ranap=rs2.getBoolean("harian_klasifikasi_pasien_ranap");
                        akses.klasifikasi_pasien_perbangsal=rs2.getBoolean("klasifikasi_pasien_perbangsal");
                        akses.soap_perawatan=rs2.getBoolean("soap_perawatan");
                        akses.klaim_rawat_jalan=rs2.getBoolean("klaim_rawat_jalan");
                        akses.skrining_gizi=rs2.getBoolean("skrining_gizi");
                        akses.lama_penyiapan_rm=rs2.getBoolean("lama_penyiapan_rm");
                        akses.dosis_radiologi=rs2.getBoolean("dosis_radiologi");
                        akses.demografi_umur_kunjungan=rs2.getBoolean("demografi_umur_kunjungan");
                        akses.jam_diet_pasien=rs2.getBoolean("jam_diet_pasien");
                        akses.rvu_bpjs=rs2.getBoolean("rvu_bpjs");
                        akses.verifikasi_penerimaan_farmasi=rs2.getBoolean("verifikasi_penerimaan_farmasi");
                        akses.verifikasi_penerimaan_logistik=rs2.getBoolean("verifikasi_penerimaan_logistik");
                        akses.pemeriksaan_lab_pa=rs2.getBoolean("pemeriksaan_lab_pa");
                        akses.ringkasan_pengajuan_obat=rs2.getBoolean("ringkasan_pengajuan_obat");
                        akses.ringkasan_pemesanan_obat=rs2.getBoolean("ringkasan_pemesanan_obat");
                        akses.ringkasan_pengadaan_obat=rs2.getBoolean("ringkasan_pengadaan_obat");
                        akses.ringkasan_penerimaan_obat=rs2.getBoolean("ringkasan_penerimaan_obat");
                        akses.ringkasan_hibah_obat=rs2.getBoolean("ringkasan_hibah_obat");
                        akses.ringkasan_penjualan_obat=rs2.getBoolean("ringkasan_penjualan_obat");
                        akses.ringkasan_beri_obat=rs2.getBoolean("ringkasan_beri_obat");
                        akses.ringkasan_piutang_obat=rs2.getBoolean("ringkasan_piutang_obat");
                        akses.ringkasan_stok_keluar_obat=rs2.getBoolean("ringkasan_stok_keluar_obat");
                        akses.ringkasan_retur_suplier_obat=rs2.getBoolean("ringkasan_retur_suplier_obat");
                        akses.ringkasan_retur_pembeli_obat=rs2.getBoolean("ringkasan_retur_pembeli_obat");
                        akses.penilaian_awal_keperawatan_ranapkebidanan=rs2.getBoolean("penilaian_awal_keperawatan_ranapkebidanan");
                        akses.ringkasan_pengajuan_nonmedis=rs2.getBoolean("ringkasan_pengajuan_nonmedis");
                        akses.ringkasan_pemesanan_nonmedis=rs2.getBoolean("ringkasan_pemesanan_nonmedis");
                        akses.ringkasan_pengadaan_nonmedis=rs2.getBoolean("ringkasan_pengadaan_nonmedis");
                        akses.ringkasan_penerimaan_nonmedis=rs2.getBoolean("ringkasan_penerimaan_nonmedis");
                        akses.ringkasan_stokkeluar_nonmedis=rs2.getBoolean("ringkasan_stokkeluar_nonmedis");
                        akses.ringkasan_returbeli_nonmedis=rs2.getBoolean("ringkasan_returbeli_nonmedis");
                        akses.omset_penerimaan=rs2.getBoolean("omset_penerimaan");
                        akses.validasi_penagihan_piutang=rs2.getBoolean("validasi_penagihan_piutang");
                        akses.permintaan_ranap=rs2.getBoolean("permintaan_ranap");
                        akses.bpjs_diagnosa_prb=rs2.getBoolean("bpjs_diagnosa_prb");
                        akses.bpjs_obat_prb=rs2.getBoolean("bpjs_obat_prb");
                        akses.bpjs_surat_kontrol=rs2.getBoolean("bpjs_surat_kontrol");
                        akses.penggunaan_bhp_ok=rs2.getBoolean("penggunaan_bhp_ok");
                        akses.surat_keterangan_rawat_inap=rs2.getBoolean("surat_keterangan_rawat_inap");
                        akses.surat_keterangan_sehat=rs2.getBoolean("surat_keterangan_sehat");
                        akses.pendapatan_per_carabayar=rs2.getBoolean("pendapatan_per_carabayar");
                        akses.akun_host_to_host_bank_jateng=rs2.getBoolean("akun_host_to_host_bank_jateng");
                        akses.pembayaran_bank_jateng=rs2.getBoolean("pembayaran_bank_jateng");
                        akses.bpjs_surat_pri=rs2.getBoolean("bpjs_surat_pri");
                        akses.ringkasan_tindakan=rs2.getBoolean("ringkasan_tindakan");
                        akses.lama_pelayanan_pasien=rs2.getBoolean("lama_pelayanan_pasien");
                        akses.surat_sakit_pihak_2=rs2.getBoolean("surat_sakit_pihak_2");
                        akses.tagihan_hutang_obat=rs2.getBoolean("tagihan_hutang_obat");
                        akses.referensi_mobilejkn_bpjs=rs2.getBoolean("referensi_mobilejkn_bpjs");
                        akses.batal_pendaftaran_mobilejkn_bpjs=rs2.getBoolean("batal_pendaftaran_mobilejkn_bpjs");
                        akses.lama_operasi=rs2.getBoolean("lama_operasi");
                        akses.grafik_inventaris_kategori=rs2.getBoolean("grafik_inventaris_kategori");
                        akses.grafik_inventaris_merk=rs2.getBoolean("grafik_inventaris_merk");
                        akses.grafik_inventaris_produsen=rs2.getBoolean("grafik_inventaris_produsen");
                        akses.pengembalian_deposit_pasien=rs2.getBoolean("pengembalian_deposit_pasien");
                        akses.validasi_tagihan_hutang_obat=rs2.getBoolean("validasi_tagihan_hutang_obat");
                        akses.piutang_obat_belum_lunas=rs2.getBoolean("piutang_obat_belum_lunas");
                        akses.integrasi_briapi=rs2.getBoolean("integrasi_briapi");
                        akses.pengadaan_aset_inventaris=rs2.getBoolean("pengadaan_aset_inventaris");
                        akses.akun_aset_inventaris=rs2.getBoolean("akun_aset_inventaris");
                        akses.suplier_inventaris=rs2.getBoolean("suplier_inventaris");
                        akses.penerimaan_aset_inventaris=rs2.getBoolean("penerimaan_aset_inventaris");
                        akses.bayar_pemesanan_iventaris=rs2.getBoolean("bayar_pemesanan_iventaris");
                        akses.hutang_aset_inventaris=rs2.getBoolean("hutang_aset_inventaris");
                        akses.hibah_aset_inventaris=rs2.getBoolean("hibah_aset_inventaris");
                        akses.titip_faktur_non_medis=rs2.getBoolean("titip_faktur_non_medis");
                        akses.validasi_tagihan_non_medis=rs2.getBoolean("validasi_tagihan_non_medis");
                        akses.titip_faktur_aset=rs2.getBoolean("titip_faktur_aset");
                        akses.validasi_tagihan_aset=rs2.getBoolean("validasi_tagihan_aset");
                        akses.hibah_non_medis=rs2.getBoolean("hibah_non_medis");
                        akses.pcare_alasan_tacc=rs2.getBoolean("pcare_alasan_tacc");
                        akses.resep_luar=rs2.getBoolean("resep_luar");
                        akses.surat_bebas_tbc=rs2.getBoolean("surat_bebas_tbc");
                        akses.surat_buta_warna=rs2.getBoolean("surat_buta_warna");
                        akses.surat_bebas_tato=rs2.getBoolean("surat_bebas_tato");
                        akses.surat_kewaspadaan_kesehatan=rs2.getBoolean("surat_kewaspadaan_kesehatan");
                        akses.grafik_porsidiet_pertanggal=rs2.getBoolean("grafik_porsidiet_pertanggal");
                        akses.grafik_porsidiet_perbulan=rs2.getBoolean("grafik_porsidiet_perbulan");
                        akses.grafik_porsidiet_pertahun=rs2.getBoolean("grafik_porsidiet_pertahun");
                        akses.grafik_porsidiet_perbangsal=rs2.getBoolean("grafik_porsidiet_perbangsal");
                        akses.penilaian_awal_medis_ralan=rs2.getBoolean("penilaian_awal_medis_ralan");
                        akses.master_masalah_keperawatan_mata=rs2.getBoolean("master_masalah_keperawatan_mata");
                        akses.penilaian_awal_keperawatan_mata=rs2.getBoolean("penilaian_awal_keperawatan_mata");
                        akses.penilaian_awal_medis_ranap=rs2.getBoolean("penilaian_awal_medis_ranap");
                        akses.penilaian_awal_medis_ranap_kebidanan=rs2.getBoolean("penilaian_awal_medis_ranap_kebidanan");
                        akses.penilaian_awal_medis_ralan_kebidanan=rs2.getBoolean("penilaian_awal_medis_ralan_kebidanan");
                        akses.penilaian_awal_medis_igd=rs2.getBoolean("penilaian_awal_medis_igd");
                        akses.penilaian_awal_medis_ralan_anak=rs2.getBoolean("penilaian_awal_medis_ralan_anak");
                        akses.bpjs_referensi_poli_hfis=rs2.getBoolean("bpjs_referensi_poli_hfis");
                        akses.bpjs_referensi_dokter_hfis=rs2.getBoolean("bpjs_referensi_dokter_hfis");
                        akses.bpjs_referensi_jadwal_hfis=rs2.getBoolean("bpjs_referensi_jadwal_hfis");
                        akses.penilaian_fisioterapi=rs2.getBoolean("penilaian_fisioterapi");
                        akses.bpjs_program_prb=rs2.getBoolean("bpjs_program_prb");
                        akses.bpjs_suplesi_jasaraharja=rs2.getBoolean("bpjs_suplesi_jasaraharja");
                        akses.bpjs_data_induk_kecelakaan=rs2.getBoolean("bpjs_data_induk_kecelakaan");
                        akses.bpjs_sep_internal=rs2.getBoolean("bpjs_sep_internal");
                        akses.bpjs_klaim_jasa_raharja=rs2.getBoolean("bpjs_klaim_jasa_raharja");
                        akses.bpjs_daftar_finger_print=rs2.getBoolean("bpjs_daftar_finger_print");
                        akses.bpjs_rujukan_khusus=rs2.getBoolean("bpjs_rujukan_khusus");
                        akses.pemeliharaan_gedung=rs2.getBoolean("pemeliharaan_gedung");
                        akses.grafik_perbaikan_inventaris_pertanggal=rs2.getBoolean("grafik_perbaikan_inventaris_pertanggal");
                        akses.grafik_perbaikan_inventaris_perbulan=rs2.getBoolean("grafik_perbaikan_inventaris_perbulan");
                        akses.grafik_perbaikan_inventaris_pertahun=rs2.getBoolean("grafik_perbaikan_inventaris_pertahun");
                        akses.grafik_perbaikan_inventaris_perpelaksana_status=rs2.getBoolean("grafik_perbaikan_inventaris_perpelaksana_status");
                        akses.penilaian_mcu=rs2.getBoolean("penilaian_mcu");
                        akses.peminjam_piutang=rs2.getBoolean("peminjam_piutang");
                        akses.piutang_lainlain=rs2.getBoolean("piutang_lainlain");
                        akses.cara_bayar=rs2.getBoolean("cara_bayar");
                        akses.audit_kepatuhan_apd=rs2.getBoolean("audit_kepatuhan_apd");
                        akses.bpjs_task_id=rs2.getBoolean("bpjs_task_id");
                        akses.bayar_piutang_lain=rs2.getBoolean("bayar_piutang_lain");
                        akses.pembayaran_akun_bayar4=rs2.getBoolean("pembayaran_akun_bayar4");
                        akses.stok_akhir_farmasi_pertanggal=rs2.getBoolean("stok_akhir_farmasi_pertanggal");
                        akses.riwayat_kamar_pasien=rs2.getBoolean("riwayat_kamar_pasien");
                        akses.uji_fungsi_kfr=rs2.getBoolean("uji_fungsi_kfr");
                        akses.hapus_berkas_digital_perawatan=rs2.getBoolean("hapus_berkas_digital_perawatan");
                        akses.kategori_pengeluaran_harian=rs2.getBoolean("kategori_pengeluaran_harian");
                        akses.kategori_pemasukan_lain=rs2.getBoolean("kategori_pemasukan_lain");
                        akses.pembayaran_akun_bayar5=rs2.getBoolean("pembayaran_akun_bayar5");
                        akses.ruang_ok=rs2.getBoolean("ruang_ok");
                        akses.jasa_tindakan_pasien=rs2.getBoolean("jasa_tindakan_pasien");
                        akses.telaah_resep=rs2.getBoolean("telaah_resep");
                        akses.permintaan_resep_pulang=rs2.getBoolean("permintaan_resep_pulang");
                        akses.rekap_jm_dokter=rs2.getBoolean("rekap_jm_dokter");
                        akses.status_data_rm=rs2.getBoolean("status_data_rm");
                        akses.ubah_petugas_lab_pk=rs2.getBoolean("ubah_petugas_lab_pk");
                        akses.ubah_petugas_lab_pa=rs2.getBoolean("ubah_petugas_lab_pa");
                        akses.ubah_petugas_radiologi=rs2.getBoolean("ubah_petugas_radiologi");
                        akses.gabung_norawat=rs2.getBoolean("gabung_norawat");
                        akses.gabung_rm=rs2.getBoolean("gabung_rm");
                        akses.ringkasan_biaya_obat_pasien_pertanggal=rs2.getBoolean("ringkasan_biaya_obat_pasien_pertanggal");
                        akses.master_masalah_keperawatan_igd=rs2.getBoolean("master_masalah_keperawatan_igd");
                        akses.penilaian_awal_keperawatan_igd=rs2.getBoolean("penilaian_awal_keperawatan_igd");
                        akses.bpjs_referensi_dpho_apotek=rs2.getBoolean("bpjs_referensi_dpho_apotek");
                        akses.bpjs_referensi_poli_apotek=rs2.getBoolean("bpjs_referensi_poli_apotek");
                        akses.bayar_jm_dokter=rs2.getBoolean("bayar_jm_dokter");
                        akses.bpjs_referensi_faskes_apotek=rs2.getBoolean("bpjs_referensi_faskes_apotek");
                        akses.bpjs_referensi_spesialistik_apotek=rs2.getBoolean("bpjs_referensi_spesialistik_apotek");
                        akses.pembayaran_briva=rs2.getBoolean("pembayaran_briva");
                        akses.penilaian_awal_keperawatan_ranap=rs2.getBoolean("penilaian_awal_keperawatan_ranap");
                        akses.nilai_penerimaan_vendor_farmasi_perbulan=rs2.getBoolean("nilai_penerimaan_vendor_farmasi_perbulan");
                        akses.akun_bayar_hutang=rs2.getBoolean("akun_bayar_hutang");
                        akses.master_rencana_keperawatan=rs2.getBoolean("master_rencana_keperawatan");
                        akses.laporan_tahunan_igd=rs2.getBoolean("laporan_tahunan_igd");
                        akses.obat_bhp_tidakbergerak=rs2.getBoolean("obat_bhp_tidakbergerak");
                        akses.ringkasan_hutang_vendor_farmasi=rs2.getBoolean("ringkasan_hutang_vendor_farmasi");
                        akses.nilai_penerimaan_vendor_nonmedis_perbulan=rs2.getBoolean("nilai_penerimaan_vendor_nonmedis_perbulan");
                        akses.ringkasan_hutang_vendor_nonmedis=rs2.getBoolean("ringkasan_hutang_vendor_nonmedis");
                        akses.anggota_polri_dirawat=rs2.getBoolean("anggota_polri_dirawat");
                        akses.daftar_pasien_ranap_polri=rs2.getBoolean("daftar_pasien_ranap_polri");
                        akses.soap_ralan_polri=rs2.getBoolean("soap_ralan_polri");
                        akses.soap_ranap_polri=rs2.getBoolean("soap_ranap_polri");
                        akses.laporan_penyakit_polri=rs2.getBoolean("laporan_penyakit_polri");
                        akses.master_rencana_keperawatan_anak=rs2.getBoolean("master_rencana_keperawatan_anak");
                        akses.jumlah_pengunjung_ralan_polri=rs2.getBoolean("jumlah_pengunjung_ralan_polri");
                        akses.catatan_observasi_igd=rs2.getBoolean("catatan_observasi_igd");
                        akses.catatan_observasi_ranap=rs2.getBoolean("catatan_observasi_ranap");
                        akses.catatan_observasi_ranap_kebidanan=rs2.getBoolean("catatan_observasi_ranap_kebidanan");
                        akses.catatan_observasi_ranap_postpartum=rs2.getBoolean("catatan_observasi_ranap_postpartum");
                        akses.penilaian_awal_medis_ralan_tht=rs2.getBoolean("penilaian_awal_medis_ralan_tht");
                        akses.penilaian_psikologi=rs2.getBoolean("penilaian_psikologi");
                        akses.audit_cuci_tangan_medis=rs2.getBoolean("audit_cuci_tangan_medis");
                        akses.audit_pembuangan_limbah=rs2.getBoolean("audit_pembuangan_limbah");
                        akses.ruang_audit_kepatuhan=rs2.getBoolean("ruang_audit_kepatuhan");
                        akses.audit_pembuangan_benda_tajam=rs2.getBoolean("audit_pembuangan_benda_tajam");
                        akses.audit_penanganan_darah=rs2.getBoolean("audit_penanganan_darah");
                        akses.audit_pengelolaan_linen_kotor=rs2.getBoolean("audit_pengelolaan_linen_kotor");
                        akses.audit_penempatan_pasien=rs2.getBoolean("audit_penempatan_pasien");
                        akses.audit_kamar_jenazah=rs2.getBoolean("audit_kamar_jenazah");
                        akses.audit_bundle_iadp=rs2.getBoolean("audit_bundle_iadp");
                        akses.audit_bundle_ido=rs2.getBoolean("audit_bundle_ido");
                        akses.audit_fasilitas_kebersihan_tangan=rs2.getBoolean("audit_fasilitas_kebersihan_tangan");
                        akses.audit_fasilitas_apd=rs2.getBoolean("audit_fasilitas_apd");
                        akses.audit_pembuangan_limbah_cair_infeksius=rs2.getBoolean("audit_pembuangan_limbah_cair_infeksius");
                        akses.audit_sterilisasi_alat=rs2.getBoolean("audit_sterilisasi_alat");
                        akses.penilaian_awal_medis_ralan_psikiatri=rs2.getBoolean("penilaian_awal_medis_ralan_psikiatri");
                        akses.persetujuan_penolakan_tindakan=rs2.getBoolean("persetujuan_penolakan_tindakan");
                        akses.audit_bundle_isk=rs2.getBoolean("audit_bundle_isk");
                        akses.audit_bundle_plabsi=rs2.getBoolean("audit_bundle_plabsi");
                        akses.audit_bundle_vap=rs2.getBoolean("audit_bundle_vap");
                        akses.akun_host_to_host_bank_papua=rs2.getBoolean("akun_host_to_host_bank_papua");
                        akses.pembayaran_bank_papua=rs2.getBoolean("pembayaran_bank_papua");
                        akses.penilaian_awal_medis_ralan_penyakit_dalam=rs2.getBoolean("penilaian_awal_medis_ralan_penyakit_dalam");
                        akses.penilaian_awal_medis_ralan_mata=rs2.getBoolean("penilaian_awal_medis_ralan_mata");
                        akses.penilaian_awal_medis_ralan_neurologi=rs2.getBoolean("penilaian_awal_medis_ralan_neurologi");
                        akses.sirkulasi_obat6=rs2.getBoolean("sirkulasi_obat6");
                        akses.penilaian_awal_medis_ralan_orthopedi=rs2.getBoolean("penilaian_awal_medis_ralan_orthopedi");
                        akses.penilaian_awal_medis_ralan_bedah=rs2.getBoolean("penilaian_awal_medis_ralan_bedah");
                        akses.integrasi_khanza_health_services=rs2.getBoolean("integrasi_khanza_health_services");
                        akses.soap_ralan_tni=rs2.getBoolean("soap_ralan_tni");
                        akses.soap_ranap_tni=rs2.getBoolean("soap_ranap_tni");
                        akses.jumlah_pengunjung_ralan_tni=rs2.getBoolean("jumlah_pengunjung_ralan_tni");
                        akses.laporan_penyakit_tni=rs2.getBoolean("laporan_penyakit_tni");
                        akses.catatan_keperawatan_ranap=rs2.getBoolean("catatan_keperawatan_ranap");
                        akses.master_rencana_keperawatan_gigi=rs2.getBoolean("master_rencana_keperawatan_gigi");
                        akses.master_rencana_keperawatan_mata=rs2.getBoolean("master_rencana_keperawatan_mata");
                        akses.master_rencana_keperawatan_igd=rs2.getBoolean("master_rencana_keperawatan_igd");
                        akses.master_masalah_keperawatan_psikiatri=rs2.getBoolean("master_masalah_keperawatan_psikiatri");
                        akses.master_rencana_keperawatan_psikiatri=rs2.getBoolean("master_rencana_keperawatan_psikiatri");
                        akses.penilaian_awal_keperawatan_psikiatri=rs2.getBoolean("penilaian_awal_keperawatan_psikiatri");
                        akses.pemantauan_pews_anak=rs2.getBoolean("pemantauan_pews_anak");
                        akses.surat_pulang_atas_permintaan_sendiri=rs2.getBoolean("surat_pulang_atas_permintaan_sendiri");
                        akses.template_hasil_radiologi=rs2.getBoolean("template_hasil_radiologi");
                        akses.laporan_bulanan_irj=rs2.getBoolean("laporan_bulanan_irj");
                        akses.template_pemeriksaan=rs2.getBoolean("template_pemeriksaan");
                        akses.pemeriksaan_lab_mb=rs2.getBoolean("pemeriksaan_lab_mb");
                        akses.ubah_petugas_lab_mb=rs2.getBoolean("ubah_petugas_lab_mb");
                        akses.penilaian_pre_operasi=rs2.getBoolean("penilaian_pre_operasi");
                        akses.penilaian_pre_anestesi=rs2.getBoolean("penilaian_pre_anestesi");
                        akses.perencanaan_pemulangan=rs2.getBoolean("perencanaan_pemulangan");
                        akses.penilaian_lanjutan_resiko_jatuh_dewasa=rs2.getBoolean("penilaian_lanjutan_resiko_jatuh_dewasa");
                        akses.penilaian_lanjutan_resiko_jatuh_anak=rs2.getBoolean("penilaian_lanjutan_resiko_jatuh_anak");
                        akses.penilaian_awal_medis_ralan_geriatri=rs2.getBoolean("penilaian_awal_medis_ralan_geriatri");
                        akses.penilaian_tambahan_pasien_geriatri=rs2.getBoolean("penilaian_tambahan_pasien_geriatri");
                        akses.skrining_nutrisi_dewasa=rs2.getBoolean("skrining_nutrisi_dewasa");
                        akses.skrining_nutrisi_lansia=rs2.getBoolean("skrining_nutrisi_lansia");
                        akses.hasil_pemeriksaan_usg=rs2.getBoolean("hasil_pemeriksaan_usg");
                        akses.skrining_nutrisi_anak=rs2.getBoolean("skrining_nutrisi_anak");
                        akses.akun_host_to_host_bank_jabar=rs2.getBoolean("akun_host_to_host_bank_jabar");
                        akses.pembayaran_bank_jabar=rs2.getBoolean("pembayaran_bank_jabar");
                        akses.surat_pernyataan_pasien_umum=rs2.getBoolean("surat_pernyataan_pasien_umum");
                        akses.konseling_farmasi=rs2.getBoolean("konseling_farmasi");
                        akses.pelayanan_informasi_obat=rs2.getBoolean("pelayanan_informasi_obat");
                        akses.jawaban_pio_apoteker=rs2.getBoolean("jawaban_pio_apoteker");
                        akses.surat_persetujuan_umum=rs2.getBoolean("surat_persetujuan_umum");
                        akses.transfer_pasien_antar_ruang=rs2.getBoolean("transfer_pasien_antar_ruang");
                        akses.satu_sehat_referensi_dokter=rs2.getBoolean("satu_sehat_referensi_dokter");
                        akses.satu_sehat_referensi_pasien=rs2.getBoolean("satu_sehat_referensi_pasien");
                        akses.satu_sehat_mapping_departemen=rs2.getBoolean("satu_sehat_mapping_departemen");
                        akses.satu_sehat_mapping_lokasi=rs2.getBoolean("satu_sehat_mapping_lokasi");
                        akses.satu_sehat_kirim_encounter=rs2.getBoolean("satu_sehat_kirim_encounter");
                        akses.catatan_cek_gds=rs2.getBoolean("catatan_cek_gds");
                        akses.satu_sehat_kirim_condition=rs2.getBoolean("satu_sehat_kirim_condition");
                        akses.checklist_pre_operasi=rs2.getBoolean("checklist_pre_operasi");
                        akses.satu_sehat_kirim_observationttv=rs2.getBoolean("satu_sehat_kirim_observationttv");
                        akses.signin_sebelum_anestesi=rs2.getBoolean("signin_sebelum_anestesi");
                        akses.satu_sehat_kirim_procedure=rs2.getBoolean("satu_sehat_kirim_procedure");
                        akses.operasi_per_bulan=rs2.getBoolean("operasi_per_bulan");
                        akses.timeout_sebelum_insisi=rs2.getBoolean("timeout_sebelum_insisi");
                        akses.signout_sebelum_menutup_luka=rs2.getBoolean("signout_sebelum_menutup_luka");
                        akses.dapur_barang=rs2.getBoolean("dapur_barang");
                        akses.dapur_opname=rs2.getBoolean("dapur_opname");
                        akses.satu_sehat_mapping_vaksin=rs2.getBoolean("satu_sehat_mapping_vaksin");
                        akses.dapur_suplier=rs2.getBoolean("dapur_suplier");
                        akses.satu_sehat_kirim_Immunization=rs2.getBoolean("satu_sehat_kirim_Immunization");
                        akses.checklist_post_operasi=rs2.getBoolean("checklist_post_operasi");
                        akses.dapur_pembelian=rs2.getBoolean("dapur_pembelian");
                        akses.dapur_stok_keluar=rs2.getBoolean("dapur_stok_keluar");
                        akses.dapur_riwayat_barang=rs2.getBoolean("dapur_riwayat_barang");
                        akses.permintaan_dapur=rs2.getBoolean("permintaan_dapur");
                        akses.rekonsiliasi_obat=rs2.getBoolean("rekonsiliasi_obat");
                        akses.biaya_pengadaan_dapur=rs2.getBoolean("biaya_pengadaan_dapur");
                        akses.rekap_pengadaan_dapur=rs2.getBoolean("rekap_pengadaan_dapur");
                        akses.kesling_limbah_b3medis_cair=rs2.getBoolean("kesling_limbah_b3medis_cair");
                        akses.grafik_limbahb3cair_pertanggal=rs2.getBoolean("grafik_limbahb3cair_pertanggal");
                        akses.grafik_limbahb3cair_perbulan=rs2.getBoolean("grafik_limbahb3cair_perbulan");
                        akses.rekap_biaya_registrasi=rs2.getBoolean("rekap_biaya_registrasi");
                        akses.konfirmasi_rekonsiliasi_obat=rs2.getBoolean("konfirmasi_rekonsiliasi_obat");
                        akses.satu_sehat_kirim_clinicalimpression=rs2.getBoolean("satu_sehat_kirim_clinicalimpression");
                        akses.penilaian_pasien_terminal=rs2.getBoolean("penilaian_pasien_terminal");
                        akses.surat_persetujuan_rawat_inap=rs2.getBoolean("surat_persetujuan_rawat_inap");
                        akses.monitoring_reaksi_tranfusi=rs2.getBoolean("monitoring_reaksi_tranfusi");
                        akses.penilaian_korban_kekerasan=rs2.getBoolean("penilaian_korban_kekerasan");
                        akses.penilaian_lanjutan_resiko_jatuh_lansia=rs2.getBoolean("penilaian_lanjutan_resiko_jatuh_lansia");
                        akses.mpp_skrining=rs2.getBoolean("mpp_skrining");
                        akses.penilaian_pasien_penyakit_menular=rs2.getBoolean("penilaian_pasien_penyakit_menular");
                        akses.edukasi_pasien_keluarga_rj=rs2.getBoolean("edukasi_pasien_keluarga_rj");
                        akses.pemantauan_pews_dewasa=rs2.getBoolean("pemantauan_pews_dewasa");
                        akses.penilaian_tambahan_bunuh_diri=rs2.getBoolean("penilaian_tambahan_bunuh_diri");
                        akses.bpjs_antrean_pertanggal=rs2.getBoolean("bpjs_antrean_pertanggal");
                        akses.penilaian_tambahan_perilaku_kekerasan=rs2.getBoolean("penilaian_tambahan_perilaku_kekerasan");
                        akses.penilaian_tambahan_beresiko_melarikan_diri=rs2.getBoolean("penilaian_tambahan_beresiko_melarikan_diri");
                        akses.persetujuan_penundaan_pelayanan=rs2.getBoolean("persetujuan_penundaan_pelayanan");
                        akses.sisa_diet_pasien=rs2.getBoolean("sisa_diet_pasien");
                        akses.penilaian_awal_medis_ralan_bedah_mulut=rs2.getBoolean("penilaian_awal_medis_ralan_bedah_mulut");
                        akses.penilaian_pasien_keracunan=rs2.getBoolean("penilaian_pasien_keracunan");
                        akses.pemantauan_meows_obstetri=rs2.getBoolean("pemantauan_meows_obstetri");
                        akses.catatan_adime_gizi=rs2.getBoolean("catatan_adime_gizi");
                        akses.pengajuan_biaya=rs2.getBoolean("pengajuan_biaya");
                        akses.penilaian_awal_keperawatan_ralan_geriatri=rs2.getBoolean("penilaian_awal_keperawatan_ralan_geriatri");
                        akses.master_masalah_keperawatan_geriatri=rs2.getBoolean("master_masalah_keperawatan_geriatri");
                        akses.master_rencana_keperawatan_geriatri=rs2.getBoolean("master_rencana_keperawatan_geriatri");
                        akses.checklist_kriteria_masuk_hcu=rs2.getBoolean("checklist_kriteria_masuk_hcu");
                        akses.checklist_kriteria_keluar_hcu=rs2.getBoolean("checklist_kriteria_keluar_hcu");
                        akses.penilaian_risiko_dekubitus=rs2.getBoolean("penilaian_risiko_dekubitus");
                        akses.master_menolak_anjuran_medis=rs2.getBoolean("master_menolak_anjuran_medis");
                        akses.penolakan_anjuran_medis=rs2.getBoolean("penolakan_anjuran_medis");
                        akses.laporan_tahunan_penolakan_anjuran_medis=rs2.getBoolean("laporan_tahunan_penolakan_anjuran_medis");
                        akses.template_laporan_operasi=rs2.getBoolean("template_laporan_operasi");
                        akses.hasil_tindakan_eswl=rs2.getBoolean("hasil_tindakan_eswl");
                        akses.checklist_kriteria_masuk_icu=rs2.getBoolean("checklist_kriteria_masuk_icu");
                        akses.checklist_kriteria_keluar_icu=rs2.getBoolean("checklist_kriteria_keluar_icu");
                        akses.akses_dokter_lain_rawat_jalan=rs2.getBoolean("akses_dokter_lain_rawat_jalan");
                        akses.follow_up_dbd=rs2.getBoolean("follow_up_dbd");
                        akses.penilaian_risiko_jatuh_neonatus=rs2.getBoolean("penilaian_risiko_jatuh_neonatus");
                        akses.persetujuan_pengajuan_biaya=rs2.getBoolean("persetujuan_pengajuan_biaya");
                        akses.pemeriksaan_fisik_ralan_per_penyakit=rs2.getBoolean("pemeriksaan_fisik_ralan_per_penyakit");
                        akses.penilaian_lanjutan_resiko_jatuh_geriatri=rs2.getBoolean("penilaian_lanjutan_resiko_jatuh_geriatri");
                        akses.pemantauan_ews_neonatus=rs2.getBoolean("pemantauan_ews_neonatus");
                        akses.validasi_persetujuan_pengajuan_biaya=rs2.getBoolean("validasi_persetujuan_pengajuan_biaya");
                        akses.riwayat_perawatan_icare_bpjs=rs2.getBoolean("riwayat_perawatan_icare_bpjs");
                        akses.rekap_pengajuan_biaya=rs2.getBoolean("rekap_pengajuan_biaya");
                        akses.penilaian_awal_medis_ralan_kulit_kelamin=rs2.getBoolean("penilaian_awal_medis_ralan_kulit_kelamin");
                        akses.akun_host_to_host_bank_mandiri=rs2.getBoolean("akun_host_to_host_bank_mandiri");
                        akses.penilaian_medis_hemodialisa=rs2.getBoolean("penilaian_medis_hemodialisa");
                        akses.penilaian_level_kecemasan_ranap_anak=rs2.getBoolean("penilaian_level_kecemasan_ranap_anak");
                        akses.penilaian_lanjutan_resiko_jatuh_psikiatri=rs2.getBoolean("penilaian_lanjutan_resiko_jatuh_psikiatri");
                        akses.penilaian_lanjutan_skrining_fungsional=rs2.getBoolean("penilaian_lanjutan_skrining_fungsional");
                        akses.penilaian_medis_ralan_rehab_medik=rs2.getBoolean("penilaian_medis_ralan_rehab_medik");
                        akses.laporan_anestesi=rs2.getBoolean("laporan_anestesi");
                        akses.template_persetujuan_penolakan_tindakan=rs2.getBoolean("template_persetujuan_penolakan_tindakan");
                        akses.penilaian_medis_ralan_gawat_darurat_psikiatri=rs2.getBoolean("penilaian_medis_ralan_gawat_darurat_psikiatri");
                        akses.bpjs_referensi_setting_apotek=rs2.getBoolean("bpjs_referensi_setting_apotek");
                        akses.bpjs_referensi_obat_apotek=rs2.getBoolean("bpjs_referensi_obat_apotek");
                        akses.bpjs_mapping_obat_apotek=rs2.getBoolean("bpjs_mapping_obat_apotek");
                        akses.pembayaran_bank_mandiri=rs2.getBoolean("pembayaran_bank_mandiri");
                        akses.penilaian_ulang_nyeri=rs2.getBoolean("penilaian_ulang_nyeri");
                        akses.penilaian_terapi_wicara=rs2.getBoolean("penilaian_terapi_wicara");
                        akses.bpjs_obat_23hari_apotek=rs2.getBoolean("bpjs_obat_23hari_apotek");
                        akses.pengkajian_restrain=rs2.getBoolean("pengkajian_restrain");
                        akses.bpjs_kunjungan_sep_apotek=rs2.getBoolean("bpjs_kunjungan_sep_apotek");
                        akses.bpjs_monitoring_klaim_apotek=rs2.getBoolean("bpjs_monitoring_klaim_apotek");
                        akses.bpjs_daftar_pelayanan_obat_apotek=rs2.getBoolean("bpjs_daftar_pelayanan_obat_apotek");
                        akses.penilaian_awal_medis_ralan_paru=rs2.getBoolean("penilaian_awal_medis_ralan_paru");
                        akses.catatan_keperawatan_ralan=rs2.getBoolean("catatan_keperawatan_ralan");
                        akses.catatan_persalinan=rs2.getBoolean("catatan_persalinan");
                        akses.skor_aldrette_pasca_anestesi=rs2.getBoolean("skor_aldrette_pasca_anestesi");
                        akses.skor_steward_pasca_anestesi=rs2.getBoolean("skor_steward_pasca_anestesi");
                        akses.skor_bromage_pasca_anestesi=rs2.getBoolean("skor_bromage_pasca_anestesi");
                        akses.penilaian_pre_induksi=rs2.getBoolean("penilaian_pre_induksi");
                        akses.hasil_usg_urologi=rs2.getBoolean("hasil_usg_urologi");
                        akses.hasil_usg_gynecologi=rs2.getBoolean("hasil_usg_gynecologi");
                        akses.hasil_pemeriksaan_ekg=rs2.getBoolean("hasil_pemeriksaan_ekg");
                        akses.hapus_edit_sep_bpjs=rs2.getBoolean("hapus_edit_sep_bpjs");
                        akses.satu_sehat_kirim_diet=rs2.getBoolean("satu_sehat_kirim_diet");
                        akses.satu_sehat_mapping_obat=rs2.getBoolean("satu_sehat_mapping_obat");
                        akses.dapur_ringkasan_pembelian=rs2.getBoolean("dapur_ringkasan_pembelian");
                        akses.satu_sehat_kirim_medication=rs2.getBoolean("satu_sehat_kirim_medication");
                        akses.satu_sehat_kirim_medicationrequest=rs2.getBoolean("satu_sehat_kirim_medicationrequest");
                        akses.penatalaksanaan_terapi_okupasi=rs2.getBoolean("penatalaksanaan_terapi_okupasi");
                        akses.satu_sehat_kirim_medicationdispense=rs2.getBoolean("satu_sehat_kirim_medicationdispense");
                        akses.hasil_usg_neonatus=rs2.getBoolean("hasil_usg_neonatus");
                        akses.hasil_endoskopi_faring_laring=rs2.getBoolean("hasil_endoskopi_faring_laring");
                        akses.satu_sehat_mapping_radiologi=rs2.getBoolean("satu_sehat_mapping_radiologi");
                        akses.satu_sehat_kirim_servicerequest_radiologi=rs2.getBoolean("satu_sehat_kirim_servicerequest_radiologi");
                        akses.hasil_endoskopi_hidung=rs2.getBoolean("hasil_endoskopi_hidung");
                        akses.satu_sehat_kirim_specimen_radiologi=rs2.getBoolean("satu_sehat_kirim_specimen_radiologi");
                        akses.master_masalah_keperawatan_neonatus=rs2.getBoolean("master_masalah_keperawatan_neonatus");
                        akses.master_rencana_keperawatan_neonatus=rs2.getBoolean("master_rencana_keperawatan_neonatus");
                        akses.penilaian_awal_keperawatan_ranap_neonatus=rs2.getBoolean("penilaian_awal_keperawatan_ranap_neonatus");
                        akses.satu_sehat_kirim_observation_radiologi=rs2.getBoolean("satu_sehat_kirim_observation_radiologi");
                        akses.satu_sehat_kirim_diagnosticreport_radiologi=rs2.getBoolean("satu_sehat_kirim_diagnosticreport_radiologi");
                        akses.hasil_endoskopi_telinga=rs2.getBoolean("hasil_endoskopi_telinga");
                        akses.satu_sehat_mapping_lab=rs2.getBoolean("satu_sehat_mapping_lab");
                        akses.satu_sehat_kirim_servicerequest_lab=rs2.getBoolean("satu_sehat_kirim_servicerequest_lab");
                        akses.satu_sehat_kirim_servicerequest_labmb=rs2.getBoolean("satu_sehat_kirim_servicerequest_labmb");
                        akses.satu_sehat_kirim_specimen_lab=rs2.getBoolean("satu_sehat_kirim_specimen_lab");
                        akses.satu_sehat_kirim_specimen_labmb=rs2.getBoolean("satu_sehat_kirim_specimen_labmb");
                        akses.satu_sehat_kirim_observation_lab=rs2.getBoolean("satu_sehat_kirim_observation_lab");
                        akses.satu_sehat_kirim_observation_labmb=rs2.getBoolean("satu_sehat_kirim_observation_labmb");
                        akses.satu_sehat_kirim_diagnosticreport_lab=rs2.getBoolean("satu_sehat_kirim_diagnosticreport_lab");
                        akses.satu_sehat_kirim_diagnosticreport_labmb=rs2.getBoolean("satu_sehat_kirim_diagnosticreport_labmb");
                        akses.kepatuhan_kelengkapan_keselamatan_bedah=rs2.getBoolean("kepatuhan_kelengkapan_keselamatan_bedah");
                        akses.nilai_piutang_perjenis_bayar_per_bulan=rs2.getBoolean("nilai_piutang_perjenis_bayar_per_bulan");
                        akses.ringkasan_piutang_jenis_bayar=rs2.getBoolean("ringkasan_piutang_jenis_bayar");
                        akses.penilaian_pasien_imunitas_rendah=rs2.getBoolean("penilaian_pasien_imunitas_rendah");
                        akses.balance_cairan=rs2.getBoolean("balance_cairan");
                        akses.catatan_observasi_chbp=rs2.getBoolean("catatan_observasi_chbp");
                        akses.catatan_observasi_induksi_persalinan=rs2.getBoolean("catatan_observasi_induksi_persalinan");
                        akses.skp_kategori_penilaian=rs2.getBoolean("skp_kategori_penilaian");
                        akses.skp_kriteria_penilaian=rs2.getBoolean("skp_kriteria_penilaian");
                        akses.skp_penilaian=rs2.getBoolean("skp_penilaian");
                        akses.referensi_poli_mobilejknfktp=rs2.getBoolean("referensi_poli_mobilejknfktp");
                        akses.referensi_dokter_mobilejknfktp=rs2.getBoolean("referensi_dokter_mobilejknfktp");
                        akses.skp_rekapitulasi_penilaian=rs2.getBoolean("skp_rekapitulasi_penilaian");
                        akses.pembayaran_pihak_ke3_bankmandiri=rs2.getBoolean("pembayaran_pihak_ke3_bankmandiri");
                        akses.metode_pembayaran_bankmandiri=rs2.getBoolean("metode_pembayaran_bankmandiri");
                        akses.bank_tujuan_transfer_bankmandiri=rs2.getBoolean("bank_tujuan_transfer_bankmandiri");
                        akses.kodetransaksi_tujuan_transfer_bankmandiri=rs2.getBoolean("kodetransaksi_tujuan_transfer_bankmandiri");
                        akses.konsultasi_medik=rs2.getBoolean("konsultasi_medik");
                        akses.jawaban_konsultasi_medik=rs2.getBoolean("jawaban_konsultasi_medik");
                        akses.pcare_cek_alergi=rs2.getBoolean("pcare_cek_alergi");
                        akses.pcare_cek_prognosa=rs2.getBoolean("pcare_cek_prognosa");
                        akses.data_sasaran_usiaproduktif=rs2.getBoolean("data_sasaran_usiaproduktif");
                        akses.data_sasaran_usialansia=rs2.getBoolean("data_sasaran_usialansia");
                        akses.skrining_perilaku_merokok_sekolah_remaja=rs2.getBoolean("skrining_perilaku_merokok_sekolah_remaja");
                        akses.skrining_kekerasan_pada_perempuan=rs2.getBoolean("skrining_kekerasan_pada_perempuan");
                        akses.skrining_obesitas=rs2.getBoolean("skrining_obesitas");
                        akses.skrining_risiko_kanker_payudara=rs2.getBoolean("skrining_risiko_kanker_payudara");
                        akses.skrining_risiko_kanker_paru=rs2.getBoolean("skrining_risiko_kanker_paru");
                        akses.skrining_tbc=rs2.getBoolean("skrining_tbc");
                        akses.skrining_kesehatan_gigi_mulut_remaja=rs2.getBoolean("skrining_kesehatan_gigi_mulut_remaja");
                        akses.penilaian_awal_keperawatan_ranap_bayi=rs2.getBoolean("penilaian_awal_keperawatan_ranap_bayi");
                        akses.booking_mcu_perusahaan=rs2.getBoolean("booking_mcu_perusahaan");
                        akses.catatan_observasi_restrain_nonfarma=rs2.getBoolean("catatan_observasi_restrain_nonfarma");
                        akses.catatan_observasi_ventilator=rs2.getBoolean("catatan_observasi_ventilator");
                        akses.catatan_anestesi_sedasi=rs2.getBoolean("catatan_anestesi_sedasi");
                        akses.skrining_puma=rs2.getBoolean("skrining_puma");
                        akses.satu_sehat_kirim_careplan=rs2.getBoolean("satu_sehat_kirim_careplan");
                        akses.satu_sehat_kirim_medicationstatement=rs2.getBoolean("satu_sehat_kirim_medicationstatement");
                        akses.skrining_adiksi_nikotin=rs2.getBoolean("skrining_adiksi_nikotin");
                        akses.skrining_thalassemia=rs2.getBoolean("skrining_thalassemia");
                        akses.skrining_instrumen_sdq=rs2.getBoolean("skrining_instrumen_sdq");
                        akses.skrining_instrumen_srq=rs2.getBoolean("skrining_instrumen_srq");
                        akses.checklist_pemberian_fibrinolitik=rs2.getBoolean("checklist_pemberian_fibrinolitik");
                        akses.skrining_kanker_kolorektal=rs2.getBoolean("skrining_kanker_kolorektal");
                        akses.dapur_pemesanan=rs2.getBoolean("dapur_pemesanan");
                        akses.bayar_pesan_dapur=rs2.getBoolean("bayar_pesan_dapur");
                        akses.hutang_dapur=rs2.getBoolean("hutang_dapur");
                        akses.titip_faktur_dapur=rs2.getBoolean("titip_faktur_dapur");
                        akses.validasi_tagihan_dapur=rs2.getBoolean("validasi_tagihan_dapur");
                        akses.surat_pemesanan_dapur=rs2.getBoolean("surat_pemesanan_dapur");
                        akses.pengajuan_barang_dapur=rs2.getBoolean("pengajuan_barang_dapur");
                        akses.dapur_returbeli=rs2.getBoolean("dapur_returbeli");
                        akses.hibah_dapur=rs2.getBoolean("hibah_dapur");
                        akses.ringkasan_penerimaan_dapur=rs2.getBoolean("ringkasan_penerimaan_dapur");
                        akses.ringkasan_pengajuan_dapur=rs2.getBoolean("ringkasan_pengajuan_dapur");
                        akses.ringkasan_pemesanan_dapur=rs2.getBoolean("ringkasan_pemesanan_dapur");
                        akses.ringkasan_returbeli_dapur=rs2.getBoolean("ringkasan_returbeli_dapur");
                        akses.ringkasan_stokkeluar_dapur=rs2.getBoolean("ringkasan_stokkeluar_dapur");
                        akses.dapur_stokkeluar_pertanggal=rs2.getBoolean("dapur_stokkeluar_pertanggal");
                        akses.sirkulasi_dapur=rs2.getBoolean("sirkulasi_dapur");
                        akses.sirkulasi_dapur2=rs2.getBoolean("sirkulasi_dapur2");
                        akses.verifikasi_penerimaan_dapur=rs2.getBoolean("verifikasi_penerimaan_dapur");
                        akses.nilai_penerimaan_vendor_dapur_perbulan=rs2.getBoolean("nilai_penerimaan_vendor_dapur_perbulan");
                        akses.ringkasan_hutang_vendor_dapur=rs2.getBoolean("ringkasan_hutang_vendor_dapur");
                        akses.penilaian_psikologi_klinis=rs2.getBoolean("penilaian_psikologi_klinis");
                        akses.penilaian_awal_medis_ranap_neonatus=rs2.getBoolean("penilaian_awal_medis_ranap_neonatus");
                        akses.penilaian_derajat_dehidrasi=rs2.getBoolean("penilaian_derajat_dehidrasi");
                        akses.ringkasan_jasa_tindakan_medis=rs2.getBoolean("ringkasan_jasa_tindakan_medis");
                        akses.pendapatan_per_akun=rs2.getBoolean("pendapatan_per_akun");
                        akses.hasil_pemeriksaan_echo=rs2.getBoolean("hasil_pemeriksaan_echo");
                        akses.penilaian_bayi_baru_lahir=rs2.getBoolean("penilaian_bayi_baru_lahir");
                        akses.rl1_3_ketersediaan_kamar=rs2.getBoolean("rl1_3_ketersediaan_kamar");
                        akses.pendapatan_per_akun_closing=rs2.getBoolean("pendapatan_per_akun_closing");
                        akses.pengeluaran_pengeluaran=rs2.getBoolean("pengeluaran_pengeluaran");
                        akses.skrining_diabetes_melitus=rs2.getBoolean("skrining_diabetes_melitus");
                        akses.laporan_tindakan=rs2.getBoolean("laporan_tindakan");
                        akses.pelaksanaan_informasi_edukasi=rs2.getBoolean("pelaksanaan_informasi_edukasi");
                        akses.layanan_kedokteran_fisik_rehabilitasi=rs2.getBoolean("layanan_kedokteran_fisik_rehabilitasi");
                        akses.skrining_kesehatan_gigi_mulut_balita=rs2.getBoolean("skrining_kesehatan_gigi_mulut_balita");
                        akses.skrining_anemia=rs2.getBoolean("skrining_anemia");
                        akses.layanan_program_kfr=rs2.getBoolean("layanan_program_kfr");
                        akses.skrining_hipertensi=rs2.getBoolean("skrining_hipertensi");
                        akses.skrining_kesehatan_penglihatan=rs2.getBoolean("skrining_kesehatan_penglihatan");
                        akses.catatan_observasi_hemodialisa=rs2.getBoolean("catatan_observasi_hemodialisa");
                        akses.skrining_kesehatan_gigi_mulut_dewasa=rs2.getBoolean("skrining_kesehatan_gigi_mulut_dewasa");
                        akses.skrining_risiko_kanker_serviks=rs2.getBoolean("skrining_risiko_kanker_serviks");
                        akses.catatan_cairan_hemodialisa=rs2.getBoolean("catatan_cairan_hemodialisa");
                        akses.skrining_kesehatan_gigi_mulut_lansia=rs2.getBoolean("skrining_kesehatan_gigi_mulut_lansia");
                        akses.skrining_indra_pendengaran=rs2.getBoolean("skrining_indra_pendengaran");
                        akses.catatan_pengkajian_paska_operasi=rs2.getBoolean("catatan_pengkajian_paska_operasi");
                        akses.skrining_frailty_syndrome=rs2.getBoolean("skrining_frailty_syndrome");
                        akses.sirkulasi_cssd=rs2.getBoolean("sirkulasi_cssd");
                        akses.lama_pelayanan_cssd=rs2.getBoolean("lama_pelayanan_cssd");
                        akses.catatan_observasi_bayi=rs2.getBoolean("catatan_observasi_bayi");
                        akses.riwayat_surat_peringatan=rs2.getBoolean("riwayat_surat_peringatan");
                        akses.master_kesimpulan_anjuran_mcu=rs2.getBoolean("master_kesimpulan_anjuran_mcu");
                        akses.kategori_piutang_jasa_perusahaan=rs2.getBoolean("kategori_piutang_jasa_perusahaan");
                        akses.piutang_jasa_perusahaan=rs2.getBoolean("piutang_jasa_perusahaan");
                        akses.bayar_piutang_jasa_perusahaan=rs2.getBoolean("bayar_piutang_jasa_perusahaan");
                        akses.piutang_jasa_perusahaan_belum_lunas=rs2.getBoolean("piutang_jasa_perusahaan_belum_lunas");
                        akses.checklist_kesiapan_anestesi=rs2.getBoolean("checklist_kesiapan_anestesi");
                        akses.piutang_peminjaman_uang_belum_lunas=rs2.getBoolean("piutang_peminjaman_uang_belum_lunas");
                        akses.hasil_pemeriksaan_slit_lamp=rs2.getBoolean("hasil_pemeriksaan_slit_lamp");
                        akses.hasil_pemeriksaan_oct=rs2.getBoolean("hasil_pemeriksaan_oct");
                        akses.beban_hutang_lain=rs2.getBoolean("beban_hutang_lain");
                        akses.poli_asal_pasien_ranap=rs2.getBoolean("poli_asal_pasien_ranap");
                        akses.pemberi_hutang_lain=rs2.getBoolean("pemberi_hutang_lain");
                        akses.dokter_asal_pasien_ranap=rs2.getBoolean("dokter_asal_pasien_ranap");
                        akses.duta_parkir_rekap_keluar=rs2.getBoolean("duta_parkir_rekap_keluar");
                        akses.surat_keterangan_layak_terbang=rs2.getBoolean("surat_keterangan_layak_terbang");
                        akses.bayar_beban_hutang_lain=rs2.getBoolean("bayar_beban_hutang_lain");
                        akses.surat_persetujuan_pemeriksaan_hiv=rs2.getBoolean("surat_persetujuan_pemeriksaan_hiv");
                        akses.skrining_instrumen_acrs=rs2.getBoolean("skrining_instrumen_acrs");
                        akses.surat_pernyataan_memilih_dpjp=rs2.getBoolean("surat_pernyataan_memilih_dpjp");
                        akses.skrining_instrumen_mental_emosional=rs2.getBoolean("skrining_instrumen_mental_emosional");
                        akses.pelanggan_lab_kesehatan_lingkungan=rs2.getBoolean("pelanggan_lab_kesehatan_lingkungan");
                        akses.kriteria_masuk_nicu=rs2.getBoolean("kriteria_masuk_nicu");
                        akses.kriteria_keluar_nicu=rs2.getBoolean("kriteria_keluar_nicu");
                        akses.penilaian_medis_ranap_psikiatrik=rs2.getBoolean("penilaian_medis_ranap_psikiatrik");
                        akses.kriteria_masuk_picu=rs2.getBoolean("kriteria_masuk_picu");
                        akses.kriteria_keluar_picu=rs2.getBoolean("kriteria_keluar_picu");
                        akses.master_sampel_bakumutu=rs2.getBoolean("master_sampel_bakumutu");
                        akses.skrining_instrumen_amt=rs2.getBoolean("skrining_instrumen_amt");
                        akses.parameter_pengujian_lab_kesehatan_lingkungan=rs2.getBoolean("parameter_pengujian_lab_kesehatan_lingkungan");
                        akses.nilai_normal_baku_mutu_lab_kesehatan_lingkungan=rs2.getBoolean("nilai_normal_baku_mutu_lab_kesehatan_lingkungan");
                        akses.skrining_pneumonia_severity_index=rs2.getBoolean("skrining_pneumonia_severity_index");
                        akses.permintaan_pengujian_sampel_lab_kesehatan_lingkungan=rs2.getBoolean("permintaan_pengujian_sampel_lab_kesehatan_lingkungan");
                        akses.penilaian_awal_medis_ralan_jantung=rs2.getBoolean("penilaian_awal_medis_ralan_jantung");
                        akses.penilaian_awal_medis_ralan_urologi=rs2.getBoolean("penilaian_awal_medis_ralan_urologi");
                        akses.hasil_pemeriksaan_treadmill=rs2.getBoolean("hasil_pemeriksaan_treadmill");
                        akses.hasil_pemeriksaan_echo_pediatrik=rs2.getBoolean("hasil_pemeriksaan_echo_pediatrik");
                    }else if((rs.getRow()==0)&&(rs2.getRow()==0)){
                        setLogOut();
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
    
    public static void setLogOut(){
        akses.kode="";                  
        akses.penyakit= false;
        akses.obat_penyakit= false;
        akses.dokter= false;
        akses.jadwal_praktek= false;
        akses.petugas= false;
        akses.pasien= false;
        akses.registrasi= false;
        akses.tindakan_ralan= false;
        akses.kamar_inap= false;
        akses.tindakan_ranap= false;
        akses.operasi= false;
        akses.rujukan_keluar= false;
        akses.rujukan_masuk= false;
        akses.beri_obat= false;
        akses.resep_pulang= false;
        akses.pasien_meninggal= false;
        akses.diet_pasien= false;
        akses.kelahiran_bayi= false;
        akses.periksa_lab= false;
        akses.periksa_radiologi= false;
        akses.kasir_ralan= false;
        akses.deposit_pasien= false;
        akses.piutang_pasien= false;
        akses.peminjaman_berkas= false;
        akses.barcode= false;
        akses.presensi_harian= false;
        akses.presensi_bulanan= false;
        akses.pegawai_admin= false;
        akses.pegawai_user= false;
        akses.suplier= false;
        akses.satuan_barang= false;
        akses.konversi_satuan= false;
        akses.jenis_barang= false;
        akses.obat= false;
        akses.stok_opname_obat= false;
        akses.stok_obat_pasien= false;
        akses.pengadaan_obat= false;
        akses.pemesanan_obat= false;
        akses.penjualan_obat= false;
        akses.piutang_obat= false;
        akses.retur_ke_suplier= false;
        akses.retur_dari_pembeli= false;
        akses.retur_obat_ranap= false;
        akses.retur_piutang_pasien= false;
        akses.keuntungan_penjualan= false;
        akses.keuntungan_beri_obat= false;
        akses.sirkulasi_obat= false;
        akses.ipsrs_barang= false;
        akses.ipsrs_pengadaan_barang= false;
        akses.ipsrs_stok_keluar= false;
        akses.ipsrs_rekap_pengadaan= false;
        akses.ipsrs_rekap_stok_keluar= false;
        akses.ipsrs_pengeluaran_harian= false;
        akses.ipsrs_jenis_barang=false;
        akses.inventaris_jenis= false;
        akses.inventaris_kategori= false;
        akses.inventaris_merk= false;
        akses.inventaris_ruang= false;
        akses.inventaris_produsen= false;
        akses.inventaris_koleksi= false;
        akses.inventaris_inventaris= false;
        akses.inventaris_sirkulasi= false;
        akses.parkir_jenis= false;
        akses.parkir_in= false;
        akses.parkir_out= false;
        akses.parkir_rekap_harian= false;
        akses.parkir_rekap_bulanan= false;
        akses.informasi_kamar= false;
        akses.harian_tindakan_poli= false;
        akses.obat_per_poli= false;
        akses.obat_per_kamar= false;
        akses.obat_per_dokter_ralan= false;
        akses.obat_per_dokter_ranap= false;
        akses.harian_dokter= false;
        akses.bulanan_dokter= false;
        akses.harian_paramedis= false;
        akses.bulanan_paramedis= false;
        akses.pembayaran_ralan= false;
        akses.pembayaran_ranap= false;
        akses.rekap_pembayaran_ralan= false;
        akses.rekap_pembayaran_ranap= false;
        akses.tagihan_masuk= false;
        akses.tambahan_biaya= false;
        akses.potongan_biaya= false;
        akses.resep_obat= false;
        akses.resume_pasien= false;
        akses.penyakit_ralan= false;
        akses.penyakit_ranap= false;
        akses.kamar= false;
        akses.tarif_ralan= false;
        akses.tarif_ranap= false;
        akses.tarif_lab= false;
        akses.tarif_radiologi= false;
        akses.tarif_operasi= false;
        akses.akun_rekening= false;
        akses.rekening_tahun= false;
        akses.posting_jurnal= false;
        akses.buku_besar= false;
        akses.cashflow= false;
        akses.keuangan= false;
        akses.pengeluaran= false;
        akses.setup_pjlab= false;
        akses.setup_otolokasi= false;
        akses.setup_jam_kamin= false;
        akses.setup_embalase= false;
        akses.tracer_login= false;
        akses.display= false;
        akses.set_harga_obat= false;
        akses.set_penggunaan_tarif= false;
        akses.set_oto_ralan= false;
        akses.biaya_harian= false;
        akses.biaya_masuk_sekali= false;
        akses.set_no_rm= false;
        akses.billing_ralan=false;
        akses.billing_ranap=false;
        akses.jm_ranap_dokter=false;   
        akses.igd=false;   
        akses.barcoderalan=false; 
        akses.barcoderanap=false;
        akses.set_harga_obat_ralan=false; 
        akses.set_harga_obat_ranap=false;
        akses.admin= false;
        akses.user= false;
        akses.vakum= false;
        akses.aplikasi= false;
        akses.penyakit_pd3i=false;
        akses.surveilans_pd3i=false;
        akses.surveilans_ralan=false;
        akses.diagnosa_pasien=false;
        akses.surveilans_ranap=false;
        akses.pny_takmenular_ranap=false;
        akses.pny_takmenular_ralan=false;
        akses.kunjungan_ralan=false;
        akses.rl32=false;
        akses.rl33=false;
        akses.rl37=false;
        akses.rl38=false;
        akses.harian_tindakan_dokter=false;
        akses.sms=false;             
        akses.sidikjari=false;  
        akses.jam_masuk=false;  
        akses.jadwal_pegawai=false;   
        akses.parkir_barcode=false;
        akses.set_nota=false;
        akses.dpjp_ranap=false;
        akses.mutasi_barang=false;
        akses.rl34=false;
        akses.rl36=false;
        akses.fee_visit_dokter=false;
        akses.fee_bacaan_ekg=false;
        akses.fee_rujukan_rontgen=false;
        akses.fee_rujukan_ranap=false;
        akses.fee_ralan=false;
        akses.akun_bayar=false;
        akses.bayar_pemesanan_obat=false;
        akses.obat_per_dokter_peresep=false;
        akses.pemasukan_lain=false;
        akses.pengaturan_rekening=false;
        akses.closing_kasir=false;
        akses.keterlambatan_presensi=false; 
        akses.set_harga_kamar=false;
        akses.rekap_per_shift=false;
        akses.bpjs_cek_nik=false;
        akses.bpjs_cek_kartu=false;
        akses.bpjs_cek_riwayat=false;
        akses.obat_per_cara_bayar=false;
        akses.kunjungan_ranap=false;
        akses.bayar_piutang=false;
        akses.payment_point=false;
        akses.bpjs_cek_nomor_rujukan=false;
        akses.icd9=false;
        akses.darurat_stok=false;
        akses.retensi_rm=false;
        akses.temporary_presensi=false;
        akses.jurnal_harian=false;
        akses.sirkulasi_obat2=false;
        akses.edit_registrasi=false;
        akses.bpjs_referensi_diagnosa=false;
        akses.bpjs_referensi_poli=false;
        akses.industrifarmasi=false;
        akses.harian_js=false;
        akses.bulanan_js=false;
        akses.harian_paket_bhp=false;
        akses.bulanan_paket_bhp=false;
        akses.piutang_pasien2=false;
        akses.bpjs_referensi_faskes=false;
        akses.bpjs_sep=false;
        akses.pengambilan_utd=false;
        akses.tarif_utd=false;
        akses.pengambilan_utd2=false;
        akses.utd_medis_rusak=false;
        akses.pengambilan_penunjang_utd=false;
        akses.pengambilan_penunjang_utd2=false;
        akses.utd_penunjang_rusak=false;
        akses.suplier_penunjang=false;
        akses.utd_donor=false;
        akses.bpjs_monitoring_klaim=false;
        akses.utd_cekal_darah=false;
        akses.utd_komponen_darah=false;
        akses.utd_stok_darah=false;
        akses.utd_pemisahan_darah=false;
        akses.harian_kamar=false;
        akses.rincian_piutang_pasien=false;
        akses.keuntungan_beri_obat_nonpiutang=false;
        akses.reklasifikasi_ralan=false;
        akses.reklasifikasi_ranap=false;
        akses.utd_penyerahan_darah=false;
        akses.hutang_obat=false;
        akses.riwayat_obat_alkes_bhp=false;
        akses.sensus_harian_poli=false;
        akses.rl4a=false;
        akses.aplicare_referensi_kamar=false;
        akses.aplicare_ketersediaan_kamar=false;
        akses.inacbg_klaim_baru_otomatis=false;
        akses.inacbg_klaim_baru_manual=false;
        akses.inacbg_coder_nik=false;
        akses.mutasi_berkas=false;
        akses.akun_piutang=false;
        akses.harian_kso=false;
        akses.bulanan_kso=false;                                    
        akses.harian_menejemen=false;
        akses.bulanan_menejemen=false;
        akses.inhealth_cek_eligibilitas=false;
        akses.inhealth_referensi_jenpel_ruang_rawat=false;
        akses.inhealth_referensi_poli=false;
        akses.inhealth_referensi_faskes=false;
        akses.inhealth_sjp=false;
        akses.piutang_ralan=false;
        akses.piutang_ranap=false;
        akses.detail_piutang_penjab=false;
        akses.lama_pelayanan_ralan=false;
        akses.catatan_pasien=false;
        akses.rl4b=false;              
        akses.rl4asebab=false;
        akses.rl4bsebab=false;
        akses.data_HAIs=false;
        akses.harian_HAIs=false;
        akses.bulanan_HAIs=false;
        akses.hitung_bor=false;
        akses.perusahaan_pasien=false;
        akses.resep_dokter=false;
        akses.lama_pelayanan_apotek=false;
        akses.hitung_alos=false;
        akses.detail_tindakan=false;
        akses.rujukan_poli_internal=false;
        akses.rekap_poli_anak=false;
        akses.grafik_kunjungan_poli=false;
        akses.grafik_kunjungan_perdokter=false;
        akses.grafik_kunjungan_perpekerjaan=false;
        akses.grafik_kunjungan_perpendidikan=false;
        akses.grafik_kunjungan_pertahun=false;
        akses.berkas_digital_perawatan=false;
        akses.penyakit_menular_ranap=false;
        akses.penyakit_menular_ralan=false;
        akses.grafik_kunjungan_perbulan=false;                    
        akses.grafik_kunjungan_pertanggal=false;
        akses.grafik_kunjungan_demografi=false;
        akses.grafik_kunjungan_statusdaftartahun=false;
        akses.grafik_kunjungan_statusdaftartahun2=false;                        
        akses.grafik_kunjungan_statusdaftarbulan=false;
        akses.grafik_kunjungan_statusdaftarbulan2=false;
        akses.grafik_kunjungan_statusdaftartanggal=false;
        akses.grafik_kunjungan_statusdaftartanggal2=false;
        akses.grafik_kunjungan_statusbataltahun=false;
        akses.grafik_kunjungan_statusbatalbulan=false;
        akses.pcare_cek_penyakit=false;
        akses.grafik_kunjungan_statusbataltanggal=false;
        akses.kategori_barang=false;
        akses.golongan_barang=false;
        akses.pemberian_obat_pertanggal=false;
        akses.penjualan_obat_pertanggal=false;
        akses.pcare_cek_kesadaran=false;
        akses.pembatalan_periksa_dokter=false;
        akses.pembayaran_per_unit=false;
        akses.rekap_pembayaran_per_unit=false;
        akses.grafik_kunjungan_percarabayar=false;
        akses.ipsrs_pengadaan_pertanggal=false;
        akses.ipsrs_stokkeluar_pertanggal=false;
        akses.grafik_kunjungan_ranaptahun=false;
        akses.pcare_cek_rujukan=false;
        akses.grafik_lab_ralantahun=false;
        akses.grafik_rad_ralantahun=false;
        akses.cek_entry_ralan=false;
        akses.inacbg_klaim_baru_manual2=false;
        akses.permintaan_medis=false;
        akses.rekap_permintaan_medis=false;
        akses.surat_pemesanan_medis=false;
        akses.permintaan_non_medis=false;
        akses.rekap_permintaan_non_medis=false;
        akses.surat_pemesanan_non_medis=false;
        akses.grafik_per_perujuk=false;
        akses.bpjs_cek_prosedur=false;
        akses.bpjs_cek_kelas_rawat=false;
        akses.bpjs_cek_dokter=false;
        akses.bpjs_cek_spesialistik=false;
        akses.bpjs_cek_ruangrawat=false;
        akses.bpjs_cek_carakeluar=false;
        akses.bpjs_cek_pasca_pulang=false;
        akses.detail_tindakan_okvk=false;
        akses.billing_parsial=false;
        akses.bpjs_cek_nomor_rujukan_rs=false;
        akses.bpjs_cek_rujukan_kartu_pcare=false;
        akses.bpjs_cek_rujukan_kartu_rs=false;
        akses.akses_depo_obat=false;
        akses.bpjs_rujukan_keluar=false;
        akses.grafik_lab_ralanbulan=false;
        akses.pengeluaran_stok_apotek=false;
        akses.grafik_rad_ralanbulan=false;
        akses.detailjmdokter2=false;
        akses.pengaduan_pasien=false;
        akses.grafik_lab_ralanhari=false;
        akses.grafik_rad_ralanhari=false;
        akses.sensus_harian_ralan=false;
        akses.metode_racik=false;
        akses.pembayaran_akun_bayar=false;
        akses.pengguna_obat_resep=false;
        akses.rekap_pemesanan=false;
        akses.master_berkas_pegawai=false;
        akses.berkas_kepegawaian=false;
        akses.riwayat_jabatan=false;
        akses.riwayat_pendidikan=false;
        akses.riwayat_naik_gaji=false;
        akses.kegiatan_ilmiah=false;
        akses.riwayat_penghargaan=false;
        akses.riwayat_penelitian=false;
        akses.penerimaan_non_medis=false;
        akses.bayar_pesan_non_medis=false;
        akses.hutang_barang_non_medis=false;
        akses.rekap_pemesanan_non_medis=false;                      
        akses.insiden_keselamatan=false;
        akses.insiden_keselamatan_pasien=false;
        akses.grafik_ikp_pertahun=false;
        akses.grafik_ikp_perbulan=false;
        akses.grafik_ikp_pertanggal=false;
        akses.riwayat_data_batch=false;
        akses.grafik_ikp_jenis=false;
        akses.grafik_ikp_dampak=false;
        akses.piutang_akun_piutang=false;
        akses.grafik_kunjungan_per_agama=false;
        akses.grafik_kunjungan_per_umur=false;
        akses.suku_bangsa=false;
        akses.bahasa_pasien=false;
        akses.golongan_tni=false;
        akses.satuan_tni=false;
        akses.jabatan_tni=false;
        akses.pangkat_tni=false;
        akses.golongan_polri=false;
        akses.satuan_polri=false;
        akses.jabatan_polri=false;
        akses.pangkat_polri=false;
        akses.cacat_fisik=false;
        akses.grafik_kunjungan_suku=false;
        akses.grafik_kunjungan_bahasa=false;
        akses.booking_operasi=false;
        akses.mapping_poli_bpjs=false;
        akses.grafik_kunjungan_per_cacat=false;
        akses.barang_cssd=false;
        akses.skdp_bpjs=false;
        akses.booking_registrasi=false;
        akses.bpjs_cek_propinsi=false;
        akses.bpjs_cek_kabupaten=false;
        akses.bpjs_cek_kecamatan=false;
        akses.bpjs_cek_dokterdpjp=false;
        akses.bpjs_cek_riwayat_rujukanrs=false;
        akses.bpjs_cek_tanggal_rujukan=false;
        akses.permintaan_lab=false;
        akses.permintaan_radiologi=false;
        akses.surat_indeks=false;
        akses.surat_map=false;
        akses.surat_almari=false;
        akses.surat_rak=false;
        akses.surat_ruang=false;
        akses.surat_klasifikasi=false;
        akses.surat_status=false;
        akses.surat_sifat=false;
        akses.surat_balas=false;
        akses.surat_masuk=false;
        akses.pcare_cek_dokter=false;
        akses.pcare_cek_poli=false;
        akses.pcare_cek_provider=false;
        akses.pcare_cek_statuspulang=false;
        akses.pcare_cek_spesialis=false;
        akses.pcare_cek_subspesialis=false;
        akses.pcare_cek_sarana=false;
        akses.pcare_cek_khusus=false;
        akses.pcare_cek_obat=false;
        akses.pcare_cek_tindakan=false;
        akses.pcare_cek_faskessubspesialis=false;
        akses.pcare_cek_faskesalihrawat=false;
        akses.pcare_cek_faskesthalasemia=false;
        akses.pcare_mapping_obat=false;
        akses.pcare_mapping_tindakan=false;
        akses.pcare_club_prolanis=false;
        akses.pcare_mapping_poli=false;
        akses.pcare_kegiatan_kelompok=false;
        akses.pcare_mapping_tindakan_ranap=false;
        akses.pcare_peserta_kegiatan_kelompok=false;
        akses.sirkulasi_obat3=false;
        akses.bridging_pcare_daftar=false;
        akses.pcare_mapping_dokter=false;
        akses.ranap_per_ruang=false;
        akses.penyakit_ranap_cara_bayar=false;
        akses.anggota_militer_dirawat=false;
        akses.set_input_parsial=false;
        akses.lama_pelayanan_radiologi=false;
        akses.lama_pelayanan_lab=false;
        akses.bpjs_cek_sep=false;
        akses.catatan_perawatan=false;
        akses.surat_keluar=false;
        akses.kegiatan_farmasi=false;
        akses.stok_opname_logistik=false;
        akses.sirkulasi_non_medis=false;
        akses.rekap_lab_pertahun=false;
        akses.perujuk_lab_pertahun=false;
        akses.rekap_radiologi_pertahun=false;
        akses.perujuk_radiologi_pertahun=false;
        akses.jumlah_porsi_diet=false;
        akses.jumlah_macam_diet=false;
        akses.payment_point2=false;
        akses.pembayaran_akun_bayar2=false;
        akses.hapus_nota_salah=false;
        akses.hais_perbangsal=false;
        akses.ppn_obat=false;
        akses.saldo_akun_perbulan=false;
        akses.display_apotek=false;
        akses.sisrute_referensi_faskes=false;
        akses.sisrute_referensi_alasanrujuk=false;
        akses.sisrute_referensi_diagnosa=false;
        akses.sisrute_rujukan_masuk=false;
        akses.sisrute_rujukan_keluar=false;
        akses.bpjs_cek_skdp=false;
        akses.data_batch=false;
        akses.kunjungan_permintaan_lab=false;
        akses.kunjungan_permintaan_lab2=false;
        akses.kunjungan_permintaan_radiologi=false;
        akses.kunjungan_permintaan_radiologi2=false;
        akses.pcare_pemberian_obat=false;
        akses.pcare_pemberian_tindakan=false;
        akses.pembayaran_akun_bayar3=false;
        akses.password_asuransi=false;
        akses.kemenkes_sitt=false;
        akses.siranap_ketersediaan_kamar=false;
        akses.grafik_tb_periodelaporan=false;
        akses.grafik_tb_rujukan=false;
        akses.grafik_tb_riwayat=false;
        akses.grafik_tb_tipediagnosis=false;
        akses.grafik_tb_statushiv=false;
        akses.grafik_tb_skoringanak=false;
        akses.grafik_tb_konfirmasiskoring5=false;
        akses.grafik_tb_konfirmasiskoring6=false;
        akses.grafik_tb_sumberobat=false;
        akses.grafik_tb_hasilakhirpengobatan=false;
        akses.grafik_tb_hasilteshiv=false;
        akses.kadaluarsa_batch=false;
        akses.sisa_stok=false;
        akses.obat_per_resep=false;
        akses.pemakaian_air_pdam=false;
        akses.limbah_b3_medis=false;
        akses.grafik_air_pdam_pertanggal=false;
        akses.grafik_air_pdam_perbulan=false;
        akses.grafik_limbahb3_pertanggal=false;
        akses.grafik_limbahb3_perbulan=false;
        akses.limbah_domestik=false;
        akses.grafik_limbahdomestik_pertanggal=false;
        akses.grafik_limbahdomestik_perbulan=false;
        akses.mutu_air_limbah=false;
        akses.pest_control=false;
        akses.ruang_perpustakaan=false;
        akses.kategori_perpustakaan=false;
        akses.jenis_perpustakaan=false;
        akses.pengarang_perpustakaan=false;
        akses.penerbit_perpustakaan=false;
        akses.koleksi_perpustakaan=false;
        akses.inventaris_perpustakaan=false;
        akses.set_peminjaman_perpustakaan=false;
        akses.denda_perpustakaan=false;
        akses.anggota_perpustakaan=false;
        akses.peminjaman_perpustakaan=false;
        akses.bayar_denda_perpustakaan=false;
        akses.ebook_perpustakaan=false;
        akses.jenis_cidera_k3rs=false;
        akses.penyebab_k3rs=false;
        akses.jenis_luka_k3rs=false;
        akses.lokasi_kejadian_k3rs=false;
        akses.dampak_cidera_k3rs=false;
        akses.jenis_pekerjaan_k3rs=false;
        akses.bagian_tubuh_k3rs=false;
        akses.peristiwa_k3rs=false;
        akses.grafik_k3_pertahun=false;
        akses.grafik_k3_perbulan=false;
        akses.grafik_k3_pertanggal=false;
        akses.grafik_k3_perjeniscidera=false;
        akses.grafik_k3_perpenyebab=false;
        akses.grafik_k3_perjenisluka=false;
        akses.grafik_k3_lokasikejadian=false;
        akses.grafik_k3_dampakcidera=false;
        akses.grafik_k3_perjenispekerjaan=false;
        akses.grafik_k3_perbagiantubuh=false;
        akses.jenis_cidera_k3rstahun=false;
        akses.penyebab_k3rstahun=false;
        akses.jenis_luka_k3rstahun=false;
        akses.lokasi_kejadian_k3rstahun=false;
        akses.dampak_cidera_k3rstahun=false;
        akses.jenis_pekerjaan_k3rstahun=false;
        akses.bagian_tubuh_k3rstahun=false;
        akses.sekrining_rawat_jalan=false;
        akses.bpjs_histori_pelayanan=false;
        akses.rekap_mutasi_berkas=false;
        akses.skrining_ralan_pernapasan_pertahun=false;
        akses.pengajuan_barang_medis=false;
        akses.pengajuan_barang_nonmedis=false;
        akses.grafik_kunjungan_ranapbulan=false;
        akses.grafik_kunjungan_ranaptanggal=false;
        akses.grafik_kunjungan_ranap_peruang=false;
        akses.kunjungan_bangsal_pertahun=false;
        akses.grafik_jenjang_jabatanpegawai=false;
        akses.grafik_bidangpegawai=false;
        akses.grafik_departemenpegawai=false;
        akses.grafik_pendidikanpegawai=false;
        akses.grafik_sttswppegawai=false;
        akses.grafik_sttskerjapegawai=false;
        akses.grafik_sttspulangranap=false;
        akses.kip_pasien_ranap=false;
        akses.kip_pasien_ralan=false;
        akses.bpjs_mapping_dokterdpjp=false;
        akses.data_triase_igd=false;
        akses.master_triase_skala1=false;
        akses.master_triase_skala2=false;
        akses.master_triase_skala3=false;
        akses.master_triase_skala4=false;
        akses.master_triase_skala5=false;
        akses.master_triase_pemeriksaan=false;
        akses.master_triase_macamkasus=false;
        akses.rekap_permintaan_diet=false;
        akses.daftar_pasien_ranap=false;
        akses.daftar_pasien_ranaptni=false;
        akses.pengajuan_asetinventaris=false;
        akses.item_apotek_jenis=false;
        akses.item_apotek_kategori=false;
        akses.item_apotek_golongan=false;
        akses.item_apotek_industrifarmasi=false;
        akses.obat10_terbanyak_poli=false;
        akses.grafik_pengajuan_aset_urgensi=false;
        akses.grafik_pengajuan_aset_status=false;
        akses.grafik_pengajuan_aset_departemen=false;
        akses.rekap_pengajuan_aset_departemen=false;
        akses.grafik_kelompok_jabatanpegawai=false;
        akses.grafik_resiko_kerjapegawai=false;
        akses.grafik_emergency_indexpegawai=false;
        akses.grafik_inventaris_ruang=false;
        akses.harian_HAIs2=false;
        akses.grafik_inventaris_jenis=false;
        akses.data_resume_pasien=false;
        akses.perkiraan_biaya_ranap=false;
        akses.rekap_obat_poli=false;
        akses.rekap_obat_pasien=false;
        akses.grafik_HAIs_pasienbangsal=false;
        akses.grafik_HAIs_pasienbulan=false;
        akses.permintaan_perbaikan_inventaris=false;
        akses.grafik_HAIs_laju_vap=false;
        akses.grafik_HAIs_laju_iad=false;
        akses.grafik_HAIs_laju_pleb=false;
        akses.grafik_HAIs_laju_isk=false;
        akses.grafik_HAIs_laju_ilo=false;
        akses.grafik_HAIs_laju_hap=false;
        akses.inhealth_mapping_poli=false;
        akses.inhealth_mapping_dokter=false;
        akses.inhealth_mapping_tindakan_ralan=false;
        akses.inhealth_mapping_tindakan_ranap=false;
        akses.inhealth_mapping_tindakan_radiologi=false;
        akses.inhealth_mapping_tindakan_laborat=false;
        akses.inhealth_mapping_tindakan_operasi=false;
        akses.hibah_obat_bhp=false;
        akses.asal_hibah=false;
        akses.asuhan_gizi=false;
        akses.inhealth_kirim_tagihan=false;
        akses.sirkulasi_obat4=false;
        akses.sirkulasi_obat5=false;
        akses.sirkulasi_non_medis2=false;
        akses.monitoring_asuhan_gizi=false;
        akses.penerimaan_obat_perbulan=false;
        akses.rekap_kunjungan=false;
        akses.surat_sakit=false;
        akses.penilaian_awal_keperawatan_ralan=false;
        akses.permintaan_diet=false;
        akses.master_masalah_keperawatan=false;
        akses.pengajuan_cuti=false;
        akses.kedatangan_pasien=false;
        akses.utd_pendonor=false;
        akses.toko_suplier=false;
        akses.toko_jenis=false;
        akses.toko_set_harga=false;
        akses.toko_barang=false;
        akses.penagihan_piutang_pasien=false;
        akses.akun_penagihan_piutang=false;
        akses.stok_opname_toko=false;
        akses.toko_riwayat_barang=false;
        akses.toko_surat_pemesanan=false;
        akses.toko_pengajuan_barang=false;
        akses.toko_penerimaan_barang=false;
        akses.toko_pengadaan_barang=false;
        akses.toko_hutang=false;
        akses.toko_bayar_pemesanan=false;
        akses.toko_member=false;
        akses.toko_penjualan=false;
        akses.registrasi_poli_per_tanggal=false;
        akses.toko_piutang=false;
        akses.toko_retur_beli=false;
        akses.ipsrs_returbeli=false;
        akses.ipsrs_riwayat_barang=false;
        akses.pasien_corona=false;
        akses.toko_pendapatan_harian=false;
        akses.diagnosa_pasien_corona=false;
        akses.perawatan_pasien_corona=false;
        akses.penilaian_awal_keperawatan_gigi=false;
        akses.master_masalah_keperawatan_gigi=false;
        akses.toko_bayar_piutang=false;
        akses.toko_piutang_harian=false;
        akses.toko_penjualan_harian=false;
        akses.deteksi_corona=false;
        akses.penilaian_awal_keperawatan_kebidanan=false;
        akses.pengumuman_epasien=false;
        akses.surat_hamil=false;
        akses.set_tarif_online=false;
        akses.booking_periksa=false;
        akses.toko_sirkulasi=false;
        akses.toko_retur_jual=false;
        akses.toko_retur_piutang=false;
        akses.toko_sirkulasi2=false;
        akses.toko_keuntungan_barang=false;
        akses.zis_pengeluaran_penerima_dankes=false;
        akses.zis_penghasilan_penerima_dankes=false;
        akses.zis_ukuran_rumah_penerima_dankes=false;
        akses.zis_dinding_rumah_penerima_dankes=false;
        akses.zis_lantai_rumah_penerima_dankes=false;
        akses.zis_atap_rumah_penerima_dankes=false;
        akses.zis_kepemilikan_rumah_penerima_dankes=false;
        akses.zis_kamar_mandi_penerima_dankes=false;
        akses.zis_dapur_rumah_penerima_dankes=false;
        akses.zis_kursi_rumah_penerima_dankes=false;
        akses.zis_kategori_phbs_penerima_dankes=false;
        akses.zis_elektronik_penerima_dankes=false;
        akses.zis_ternak_penerima_dankes=false;
        akses.zis_jenis_simpanan_penerima_dankes=false;
        akses.penilaian_awal_keperawatan_anak=false;
        akses.zis_kategori_asnaf_penerima_dankes=false;
        akses.master_masalah_keperawatan_anak=false;
        akses.master_imunisasi=false;
        akses.zis_patologis_penerima_dankes=false;
        akses.pcare_cek_kartu=false;
        akses.surat_bebas_narkoba=false;
        akses.surat_keterangan_covid=false;
        akses.pemakaian_air_tanah=false;
        akses.grafik_air_tanah_pertanggal=false;
        akses.grafik_air_tanah_perbulan=false;
        akses.lama_pelayanan_poli=false;
        akses.hemodialisa=false;
        akses.grafik_harian_hemodialisa=false;
        akses.grafik_bulanan_hemodialisa=false;
        akses.grafik_tahunan_hemodialisa=false;
        akses.grafik_bulanan_meninggal=false;
        akses.laporan_tahunan_irj=false;
        akses.perbaikan_inventaris=false;
        akses.surat_cuti_hamil=false;
        akses.permintaan_stok_obat_pasien=false;
        akses.pemeliharaan_inventaris=false;
        akses.klasifikasi_pasien_ranap=false;
        akses.bulanan_klasifikasi_pasien_ranap=false;
        akses.harian_klasifikasi_pasien_ranap=false;
        akses.klasifikasi_pasien_perbangsal=false;
        akses.soap_perawatan=false;
        akses.klaim_rawat_jalan=false;
        akses.skrining_gizi=false;
        akses.lama_penyiapan_rm=false;
        akses.dosis_radiologi=false;
        akses.demografi_umur_kunjungan=false;
        akses.jam_diet_pasien=false;
        akses.rvu_bpjs=false;
        akses.verifikasi_penerimaan_farmasi=false;
        akses.verifikasi_penerimaan_logistik=false;
        akses.pemeriksaan_lab_pa=false;
        akses.ringkasan_pengajuan_obat=false;
        akses.ringkasan_pemesanan_obat=false;
        akses.ringkasan_pengadaan_obat=false;
        akses.ringkasan_penerimaan_obat=false;
        akses.ringkasan_hibah_obat=false;
        akses.ringkasan_penjualan_obat=false;
        akses.ringkasan_beri_obat=false;
        akses.ringkasan_piutang_obat=false;
        akses.ringkasan_stok_keluar_obat=false;
        akses.ringkasan_retur_suplier_obat=false;
        akses.ringkasan_retur_pembeli_obat=false;
        akses.penilaian_awal_keperawatan_ranapkebidanan=false;
        akses.ringkasan_pengajuan_nonmedis=false;
        akses.ringkasan_pemesanan_nonmedis=false;
        akses.ringkasan_pengadaan_nonmedis=false;
        akses.ringkasan_penerimaan_nonmedis=false;
        akses.ringkasan_stokkeluar_nonmedis=false;
        akses.ringkasan_returbeli_nonmedis=false;
        akses.omset_penerimaan=false;
        akses.validasi_penagihan_piutang=false;
        akses.permintaan_ranap=false;
        akses.bpjs_diagnosa_prb=false;
        akses.bpjs_obat_prb=false;
        akses.bpjs_surat_kontrol=false;
        akses.penggunaan_bhp_ok=false;
        akses.surat_keterangan_rawat_inap=false;
        akses.surat_keterangan_sehat=false;
        akses.pendapatan_per_carabayar=false;
        akses.akun_host_to_host_bank_jateng=false;
        akses.pembayaran_bank_jateng=false;
        akses.bpjs_surat_pri=false;
        akses.ringkasan_tindakan=false;
        akses.lama_pelayanan_pasien=false;
        akses.surat_sakit_pihak_2=false;
        akses.tagihan_hutang_obat=false;
        akses.referensi_mobilejkn_bpjs=false;
        akses.batal_pendaftaran_mobilejkn_bpjs=false;
        akses.lama_operasi=false;
        akses.grafik_inventaris_kategori=false;
        akses.grafik_inventaris_merk=false;
        akses.grafik_inventaris_produsen=false;
        akses.pengembalian_deposit_pasien=false;
        akses.validasi_tagihan_hutang_obat=false;
        akses.piutang_obat_belum_lunas=false;
        akses.integrasi_briapi=false;
        akses.pengadaan_aset_inventaris=false;
        akses.akun_aset_inventaris=false;
        akses.suplier_inventaris=false;
        akses.penerimaan_aset_inventaris=false;
        akses.bayar_pemesanan_iventaris=false;
        akses.hutang_aset_inventaris=false;
        akses.hibah_aset_inventaris=false;
        akses.titip_faktur_non_medis=false;
        akses.validasi_tagihan_non_medis=false;
        akses.titip_faktur_aset=false;
        akses.validasi_tagihan_aset=false;
        akses.hibah_non_medis=false;
        akses.pcare_alasan_tacc=false;
        akses.resep_luar=false;
        akses.surat_bebas_tbc=false;
        akses.surat_buta_warna=false;
        akses.surat_bebas_tato=false;
        akses.surat_kewaspadaan_kesehatan=false;
        akses.grafik_porsidiet_pertanggal=false;
        akses.grafik_porsidiet_perbulan=false;
        akses.grafik_porsidiet_pertahun=false;
        akses.grafik_porsidiet_perbangsal=false;
        akses.penilaian_awal_medis_ralan=false;
        akses.master_masalah_keperawatan_mata=false;
        akses.penilaian_awal_keperawatan_mata=false;
        akses.penilaian_awal_medis_ranap=false;
        akses.penilaian_awal_medis_ranap_kebidanan=false;
        akses.penilaian_awal_medis_ralan_kebidanan=false;
        akses.penilaian_awal_medis_igd=false;
        akses.penilaian_awal_medis_ralan_anak=false;
        akses.bpjs_referensi_poli_hfis=false;
        akses.bpjs_referensi_dokter_hfis=false;
        akses.bpjs_referensi_jadwal_hfis=false;
        akses.penilaian_fisioterapi=false;
        akses.bpjs_program_prb=false;
        akses.bpjs_suplesi_jasaraharja=false;
        akses.bpjs_data_induk_kecelakaan=false;
        akses.bpjs_sep_internal=false;
        akses.bpjs_klaim_jasa_raharja=false;
        akses.bpjs_daftar_finger_print=false;
        akses.bpjs_rujukan_khusus=false;
        akses.pemeliharaan_gedung=false;
        akses.grafik_perbaikan_inventaris_pertanggal=false;
        akses.grafik_perbaikan_inventaris_perbulan=false;
        akses.grafik_perbaikan_inventaris_pertahun=false;
        akses.grafik_perbaikan_inventaris_perpelaksana_status=false;
        akses.penilaian_mcu=false;
        akses.peminjam_piutang=false;
        akses.piutang_lainlain=false;
        akses.cara_bayar=false;
        akses.audit_kepatuhan_apd=false;
        akses.bpjs_task_id=false;
        akses.bayar_piutang_lain=false;
        akses.pembayaran_akun_bayar4=false;
        akses.stok_akhir_farmasi_pertanggal=false;
        akses.riwayat_kamar_pasien=false;
        akses.uji_fungsi_kfr=false;
        akses.hapus_berkas_digital_perawatan=false;
        akses.kategori_pengeluaran_harian=false;
        akses.kategori_pemasukan_lain=false;
        akses.pembayaran_akun_bayar5=false;
        akses.ruang_ok=false;
        akses.jasa_tindakan_pasien=false;
        akses.telaah_resep=false;
        akses.permintaan_resep_pulang=false;
        akses.rekap_jm_dokter=false;
        akses.status_data_rm=false;
        akses.ubah_petugas_lab_pk=false;
        akses.ubah_petugas_lab_pa=false;
        akses.ubah_petugas_radiologi=false;
        akses.gabung_norawat=false;
        akses.gabung_rm=false;
        akses.ringkasan_biaya_obat_pasien_pertanggal=false;
        akses.master_masalah_keperawatan_igd=false;
        akses.penilaian_awal_keperawatan_igd=false;
        akses.bpjs_referensi_dpho_apotek=false;
        akses.bpjs_referensi_poli_apotek=false;
        akses.bayar_jm_dokter=false;
        akses.bpjs_referensi_faskes_apotek=false;
        akses.bpjs_referensi_spesialistik_apotek=false;
        akses.pembayaran_briva=false;
        akses.penilaian_awal_keperawatan_ranap=false;
        akses.nilai_penerimaan_vendor_farmasi_perbulan=false;
        akses.akun_bayar_hutang=false;
        akses.master_rencana_keperawatan=false;
        akses.laporan_tahunan_igd=false;
        akses.obat_bhp_tidakbergerak=false;
        akses.ringkasan_hutang_vendor_farmasi=false;
        akses.nilai_penerimaan_vendor_nonmedis_perbulan=false;
        akses.ringkasan_hutang_vendor_nonmedis=false;
        akses.anggota_polri_dirawat=false;
        akses.daftar_pasien_ranap_polri=false;
        akses.soap_ralan_polri=false;
        akses.soap_ranap_polri=false;
        akses.laporan_penyakit_polri=false;
        akses.master_rencana_keperawatan_anak=false;
        akses.jumlah_pengunjung_ralan_polri=false;
        akses.catatan_observasi_igd=false;
        akses.catatan_observasi_ranap=false;
        akses.catatan_observasi_ranap_kebidanan=false;
        akses.catatan_observasi_ranap_postpartum=false;
        akses.penilaian_awal_medis_ralan_tht=false;
        akses.penilaian_psikologi=false;
        akses.audit_cuci_tangan_medis=false;
        akses.audit_pembuangan_limbah=false;
        akses.ruang_audit_kepatuhan=false;
        akses.audit_pembuangan_benda_tajam=false;
        akses.audit_penanganan_darah=false;
        akses.audit_pengelolaan_linen_kotor=false;
        akses.audit_penempatan_pasien=false;
        akses.audit_kamar_jenazah=false;
        akses.audit_bundle_iadp=false;
        akses.audit_bundle_ido=false;
        akses.audit_fasilitas_kebersihan_tangan=false;
        akses.audit_fasilitas_apd=false;
        akses.audit_pembuangan_limbah_cair_infeksius=false;
        akses.audit_sterilisasi_alat=false;
        akses.penilaian_awal_medis_ralan_psikiatri=false;
        akses.persetujuan_penolakan_tindakan=false;
        akses.audit_bundle_isk=false;
        akses.audit_bundle_plabsi=false;
        akses.audit_bundle_vap=false;
        akses.akun_host_to_host_bank_papua=false;
        akses.pembayaran_bank_papua=false;
        akses.penilaian_awal_medis_ralan_penyakit_dalam=false;
        akses.penilaian_awal_medis_ralan_mata=false;
        akses.penilaian_awal_medis_ralan_neurologi=false;
        akses.sirkulasi_obat6=false;
        akses.penilaian_awal_medis_ralan_orthopedi=false;
        akses.penilaian_awal_medis_ralan_bedah=false;
        akses.integrasi_khanza_health_services=false;
        akses.soap_ralan_tni=false;
        akses.soap_ranap_tni=false;
        akses.jumlah_pengunjung_ralan_tni=false;
        akses.laporan_penyakit_tni=false;
        akses.catatan_keperawatan_ranap=false;
        akses.master_rencana_keperawatan_gigi=false;
        akses.master_rencana_keperawatan_mata=false;
        akses.master_rencana_keperawatan_igd=false;
        akses.master_masalah_keperawatan_psikiatri=false;
        akses.master_rencana_keperawatan_psikiatri=false;
        akses.penilaian_awal_keperawatan_psikiatri=false;
        akses.pemantauan_pews_anak=false;
        akses.surat_pulang_atas_permintaan_sendiri=false;
        akses.template_hasil_radiologi=false;
        akses.laporan_bulanan_irj=false;
        akses.template_pemeriksaan=false;
        akses.pemeriksaan_lab_mb=false;
        akses.ubah_petugas_lab_mb=false;
        akses.penilaian_pre_operasi=false;
        akses.penilaian_pre_anestesi=false;
        akses.perencanaan_pemulangan=false;
        akses.penilaian_lanjutan_resiko_jatuh_dewasa=false;
        akses.penilaian_lanjutan_resiko_jatuh_anak=false;
        akses.penilaian_awal_medis_ralan_geriatri=false;
        akses.penilaian_tambahan_pasien_geriatri=false;
        akses.skrining_nutrisi_dewasa=false;
        akses.skrining_nutrisi_lansia=false;
        akses.hasil_pemeriksaan_usg=false;
        akses.skrining_nutrisi_anak=false;
        akses.akun_host_to_host_bank_jabar=false;
        akses.pembayaran_bank_jabar=false;
        akses.surat_pernyataan_pasien_umum=false;
        akses.konseling_farmasi=false;
        akses.pelayanan_informasi_obat=false;
        akses.jawaban_pio_apoteker=false;
        akses.surat_persetujuan_umum=false;
        akses.transfer_pasien_antar_ruang=false;
        akses.satu_sehat_referensi_dokter=false;
        akses.satu_sehat_referensi_pasien=false;
        akses.satu_sehat_mapping_departemen=false;
        akses.satu_sehat_mapping_lokasi=false;
        akses.satu_sehat_kirim_encounter=false;
        akses.catatan_cek_gds=false;
        akses.satu_sehat_kirim_condition=false;
        akses.checklist_pre_operasi=false;
        akses.satu_sehat_kirim_observationttv=false;
        akses.signin_sebelum_anestesi=false;
        akses.satu_sehat_kirim_procedure=false;
        akses.operasi_per_bulan=false;
        akses.timeout_sebelum_insisi=false;
        akses.signout_sebelum_menutup_luka=false;
        akses.dapur_barang=false;
        akses.dapur_opname=false;
        akses.satu_sehat_mapping_vaksin=false;
        akses.dapur_suplier=false;
        akses.satu_sehat_kirim_Immunization=false;
        akses.checklist_post_operasi=false;
        akses.dapur_pembelian=false;
        akses.dapur_stok_keluar=false;
        akses.dapur_riwayat_barang=false;
        akses.permintaan_dapur=false;
        akses.rekonsiliasi_obat=false;
        akses.biaya_pengadaan_dapur=false;
        akses.rekap_pengadaan_dapur=false;
        akses.kesling_limbah_b3medis_cair=false;
        akses.grafik_limbahb3cair_pertanggal=false;
        akses.grafik_limbahb3cair_perbulan=false;
        akses.rekap_biaya_registrasi=false;
        akses.konfirmasi_rekonsiliasi_obat=false;
        akses.satu_sehat_kirim_clinicalimpression=false;
        akses.penilaian_pasien_terminal=false;
        akses.surat_persetujuan_rawat_inap=false;
        akses.monitoring_reaksi_tranfusi=false;
        akses.penilaian_korban_kekerasan=false;
        akses.penilaian_lanjutan_resiko_jatuh_lansia=false;
        akses.mpp_skrining=false;
        akses.penilaian_pasien_penyakit_menular=false;
        akses.edukasi_pasien_keluarga_rj=false;
        akses.pemantauan_pews_dewasa=false;
        akses.penilaian_tambahan_bunuh_diri=false;
        akses.bpjs_antrean_pertanggal=false;
        akses.penilaian_tambahan_perilaku_kekerasan=false;
        akses.penilaian_tambahan_beresiko_melarikan_diri=false;
        akses.persetujuan_penundaan_pelayanan=false;
        akses.sisa_diet_pasien=false;
        akses.penilaian_awal_medis_ralan_bedah_mulut=false;
        akses.penilaian_pasien_keracunan=false;
        akses.pemantauan_meows_obstetri=false;
        akses.catatan_adime_gizi=false;
        akses.pengajuan_biaya=false;
        akses.penilaian_awal_keperawatan_ralan_geriatri=false;
        akses.master_masalah_keperawatan_geriatri=false;
        akses.master_rencana_keperawatan_geriatri=false;
        akses.checklist_kriteria_masuk_hcu=false;
        akses.checklist_kriteria_keluar_hcu=false;
        akses.penilaian_risiko_dekubitus=false;
        akses.master_menolak_anjuran_medis=false;
        akses.penolakan_anjuran_medis=false;
        akses.laporan_tahunan_penolakan_anjuran_medis=false;
        akses.template_laporan_operasi=false;
        akses.hasil_tindakan_eswl=false;
        akses.checklist_kriteria_masuk_icu=false;
        akses.checklist_kriteria_keluar_icu=false;
        akses.akses_dokter_lain_rawat_jalan=false;
        akses.follow_up_dbd=false;
        akses.penilaian_risiko_jatuh_neonatus=false;
        akses.persetujuan_pengajuan_biaya=false;
        akses.pemeriksaan_fisik_ralan_per_penyakit=false;
        akses.penilaian_lanjutan_resiko_jatuh_geriatri=false;
        akses.pemantauan_ews_neonatus=false;
        akses.validasi_persetujuan_pengajuan_biaya=false;
        akses.riwayat_perawatan_icare_bpjs=false;
        akses.rekap_pengajuan_biaya=false;
        akses.penilaian_awal_medis_ralan_kulit_kelamin=false;
        akses.akun_host_to_host_bank_mandiri=false;
        akses.penilaian_medis_hemodialisa=false;
        akses.penilaian_level_kecemasan_ranap_anak=false;
        akses.penilaian_lanjutan_resiko_jatuh_psikiatri=false;
        akses.penilaian_lanjutan_skrining_fungsional=false;
        akses.penilaian_medis_ralan_rehab_medik=false;
        akses.laporan_anestesi=false;
        akses.template_persetujuan_penolakan_tindakan=false;
        akses.penilaian_medis_ralan_gawat_darurat_psikiatri=false;
        akses.bpjs_referensi_setting_apotek=false;
        akses.bpjs_referensi_obat_apotek=false;
        akses.bpjs_mapping_obat_apotek=false;
        akses.pembayaran_bank_mandiri=false;
        akses.penilaian_ulang_nyeri=false;
        akses.penilaian_terapi_wicara=false;
        akses.bpjs_obat_23hari_apotek=false;
        akses.pengkajian_restrain=false;
        akses.bpjs_kunjungan_sep_apotek=false;
        akses.bpjs_monitoring_klaim_apotek=false;
        akses.bpjs_daftar_pelayanan_obat_apotek=false;
        akses.penilaian_awal_medis_ralan_paru=false;
        akses.catatan_keperawatan_ralan=false;
        akses.catatan_persalinan=false;
        akses.skor_aldrette_pasca_anestesi=false;
        akses.skor_steward_pasca_anestesi=false;
        akses.skor_bromage_pasca_anestesi=false;
        akses.penilaian_pre_induksi=false;
        akses.hasil_usg_urologi=false;
        akses.hasil_usg_gynecologi=false;
        akses.hasil_pemeriksaan_ekg=false;
        akses.hapus_edit_sep_bpjs=false;
        akses.satu_sehat_kirim_diet=false;
        akses.satu_sehat_mapping_obat=false;
        akses.dapur_ringkasan_pembelian=false;
        akses.satu_sehat_kirim_medication=false;
        akses.satu_sehat_kirim_medicationrequest=false;
        akses.penatalaksanaan_terapi_okupasi=false;
        akses.satu_sehat_kirim_medicationdispense=false;
        akses.hasil_usg_neonatus=false;
        akses.hasil_endoskopi_faring_laring=false;
        akses.satu_sehat_mapping_radiologi=false;
        akses.satu_sehat_kirim_servicerequest_radiologi=false;
        akses.hasil_endoskopi_hidung=false;
        akses.satu_sehat_kirim_specimen_radiologi=false;
        akses.master_masalah_keperawatan_neonatus=false;
        akses.master_rencana_keperawatan_neonatus=false;
        akses.penilaian_awal_keperawatan_ranap_neonatus=false;
        akses.satu_sehat_kirim_observation_radiologi=false;
        akses.satu_sehat_kirim_diagnosticreport_radiologi=false;
        akses.hasil_endoskopi_telinga=false;
        akses.satu_sehat_mapping_lab=false;
        akses.satu_sehat_kirim_servicerequest_lab=false;
        akses.satu_sehat_kirim_servicerequest_labmb=false;
        akses.satu_sehat_kirim_specimen_lab=false;
        akses.satu_sehat_kirim_specimen_labmb=false;
        akses.satu_sehat_kirim_observation_lab=false;
        akses.satu_sehat_kirim_observation_labmb=false;
        akses.satu_sehat_kirim_diagnosticreport_lab=false;
        akses.satu_sehat_kirim_diagnosticreport_labmb=false;
        akses.kepatuhan_kelengkapan_keselamatan_bedah=false;
        akses.nilai_piutang_perjenis_bayar_per_bulan=false;
        akses.ringkasan_piutang_jenis_bayar=false;
        akses.penilaian_pasien_imunitas_rendah=false;
        akses.balance_cairan=false;
        akses.catatan_observasi_chbp=false;
        akses.catatan_observasi_induksi_persalinan=false;
        akses.skp_kategori_penilaian=false;
        akses.skp_kriteria_penilaian=false;
        akses.skp_penilaian=false;
        akses.referensi_poli_mobilejknfktp=false;
        akses.referensi_dokter_mobilejknfktp=false;
        akses.skp_rekapitulasi_penilaian=false;
        akses.pembayaran_pihak_ke3_bankmandiri=false;
        akses.metode_pembayaran_bankmandiri=false;
        akses.bank_tujuan_transfer_bankmandiri=false;
        akses.kodetransaksi_tujuan_transfer_bankmandiri=false;
        akses.konsultasi_medik=false;
        akses.jawaban_konsultasi_medik=false;
        akses.pcare_cek_alergi=false;
        akses.pcare_cek_prognosa=false;
        akses.data_sasaran_usiaproduktif=false;
        akses.data_sasaran_usialansia=false;
        akses.skrining_perilaku_merokok_sekolah_remaja=false;
        akses.skrining_kekerasan_pada_perempuan=false;
        akses.skrining_obesitas=false;
        akses.skrining_risiko_kanker_payudara=false;
        akses.skrining_risiko_kanker_paru=false;
        akses.skrining_tbc=false;
        akses.skrining_kesehatan_gigi_mulut_remaja=false;
        akses.penilaian_awal_keperawatan_ranap_bayi=false;
        akses.booking_mcu_perusahaan=false;
        akses.catatan_observasi_restrain_nonfarma=false;
        akses.catatan_observasi_ventilator=false;
        akses.catatan_anestesi_sedasi=false;
        akses.skrining_puma=false;
        akses.satu_sehat_kirim_careplan=false;
        akses.satu_sehat_kirim_medicationstatement=false;
        akses.skrining_adiksi_nikotin=false;
        akses.skrining_thalassemia=false;
        akses.skrining_instrumen_sdq=false;
        akses.skrining_instrumen_srq=false;
        akses.checklist_pemberian_fibrinolitik=false;
        akses.skrining_kanker_kolorektal=false;
        akses.dapur_pemesanan=false;
        akses.bayar_pesan_dapur=false;
        akses.hutang_dapur=false;
        akses.titip_faktur_dapur=false;
        akses.validasi_tagihan_dapur=false;
        akses.surat_pemesanan_dapur=false;
        akses.pengajuan_barang_dapur=false;
        akses.dapur_returbeli=false;
        akses.hibah_dapur=false;
        akses.ringkasan_penerimaan_dapur=false;
        akses.ringkasan_pengajuan_dapur=false;
        akses.ringkasan_pemesanan_dapur=false;
        akses.ringkasan_returbeli_dapur=false;
        akses.ringkasan_stokkeluar_dapur=false;
        akses.dapur_stokkeluar_pertanggal=false;
        akses.sirkulasi_dapur=false;
        akses.sirkulasi_dapur2=false;
        akses.verifikasi_penerimaan_dapur=false;
        akses.nilai_penerimaan_vendor_dapur_perbulan=false;
        akses.ringkasan_hutang_vendor_dapur=false;
        akses.penilaian_psikologi_klinis=false;
        akses.penilaian_awal_medis_ranap_neonatus=false;
        akses.penilaian_derajat_dehidrasi=false;
        akses.ringkasan_jasa_tindakan_medis=false;
        akses.pendapatan_per_akun=false;
        akses.hasil_pemeriksaan_echo=false;
        akses.penilaian_bayi_baru_lahir=false;
        akses.rl1_3_ketersediaan_kamar=false;
        akses.pendapatan_per_akun_closing=false;
        akses.pengeluaran_pengeluaran=false;
        akses.skrining_diabetes_melitus=false;
        akses.laporan_tindakan=false;
        akses.pelaksanaan_informasi_edukasi=false;
        akses.layanan_kedokteran_fisik_rehabilitasi=false;
        akses.skrining_kesehatan_gigi_mulut_balita=false;
        akses.skrining_anemia=false;
        akses.layanan_program_kfr=false;
        akses.skrining_hipertensi=false;
        akses.skrining_kesehatan_penglihatan=false;
        akses.catatan_observasi_hemodialisa=false;
        akses.skrining_kesehatan_gigi_mulut_dewasa=false;
        akses.skrining_risiko_kanker_serviks=false;
        akses.catatan_cairan_hemodialisa=false;
        akses.skrining_kesehatan_gigi_mulut_lansia=false;
        akses.skrining_indra_pendengaran=false;
        akses.catatan_pengkajian_paska_operasi=false;
        akses.skrining_frailty_syndrome=false;
        akses.sirkulasi_cssd=false;
        akses.lama_pelayanan_cssd=false;
        akses.catatan_observasi_bayi=false;
        akses.riwayat_surat_peringatan=false;
        akses.master_kesimpulan_anjuran_mcu=false;
        akses.kategori_piutang_jasa_perusahaan=false;
        akses.piutang_jasa_perusahaan=false;
        akses.bayar_piutang_jasa_perusahaan=false;
        akses.piutang_jasa_perusahaan_belum_lunas=false;
        akses.checklist_kesiapan_anestesi=false;
        akses.piutang_peminjaman_uang_belum_lunas=false;
        akses.hasil_pemeriksaan_slit_lamp=false;
        akses.hasil_pemeriksaan_oct=false;
        akses.beban_hutang_lain=false;
        akses.poli_asal_pasien_ranap=false;
        akses.pemberi_hutang_lain=false;
        akses.dokter_asal_pasien_ranap=false;
        akses.duta_parkir_rekap_keluar=false;
        akses.surat_keterangan_layak_terbang=false;
        akses.bayar_beban_hutang_lain=false;
        akses.surat_persetujuan_pemeriksaan_hiv=false;
        akses.skrining_instrumen_acrs=false;
        akses.surat_pernyataan_memilih_dpjp=false;
        akses.skrining_instrumen_mental_emosional=false;
        akses.pelanggan_lab_kesehatan_lingkungan=false;
        akses.kriteria_masuk_nicu=false;
        akses.kriteria_keluar_nicu=false;
        akses.penilaian_medis_ranap_psikiatrik=false;
        akses.kriteria_masuk_picu=false;
        akses.kriteria_keluar_picu=false;
        akses.master_sampel_bakumutu=false;
        akses.skrining_instrumen_amt=false;
        akses.parameter_pengujian_lab_kesehatan_lingkungan=false;
        akses.nilai_normal_baku_mutu_lab_kesehatan_lingkungan=false;
        akses.skrining_pneumonia_severity_index=false;
        akses.permintaan_pengujian_sampel_lab_kesehatan_lingkungan=false;
        akses.penilaian_awal_medis_ralan_jantung=false;
        akses.penilaian_awal_medis_ralan_urologi=false;
        akses.hasil_pemeriksaan_treadmill=false;
        akses.hasil_pemeriksaan_echo_pediatrik=false;
    }
    
    public static int getjml1() {return akses.jml1;}    
    public static int getjml2() {return akses.jml2;}    
    public static boolean getadmin(){return akses.admin;}        
    public static boolean getuser(){return akses.user;} 
    public static boolean getvakum(){return akses.vakum;} 
    public static boolean getaplikasi(){return akses.aplikasi;} 
    public static boolean getpenyakit(){return akses.penyakit;} 
    public static boolean getobat_penyakit(){return akses.obat_penyakit;} 
    public static boolean getdokter(){return akses.dokter;} 
    public static boolean getjadwal_praktek(){return akses.jadwal_praktek;} 
    public static boolean getpetugas(){return akses.petugas;} 
    public static boolean getpasien(){return akses.pasien;} 
    public static boolean getregistrasi(){return akses.registrasi;} 
    public static boolean gettindakan_ralan(){return akses.tindakan_ralan;} 
    public static boolean getkamar_inap(){return akses.kamar_inap;} 
    public static boolean gettindakan_ranap(){return akses.tindakan_ranap;} 
    public static boolean getoperasi(){return akses.operasi;} 
    public static boolean getrujukan_keluar(){return akses.rujukan_keluar;} 
    public static boolean getrujukan_masuk(){return akses.rujukan_masuk;} 
    public static boolean getberi_obat(){return akses.beri_obat;} 
    public static boolean getresep_pulang(){return akses.resep_pulang;} 
    public static boolean getpasien_meninggal(){return akses.pasien_meninggal;} 
    public static boolean getdiet_pasien(){return akses.diet_pasien;} 
    public static boolean getkelahiran_bayi(){return akses.kelahiran_bayi;} 
    public static boolean getperiksa_lab(){return akses.periksa_lab;} 
    public static boolean getperiksa_radiologi(){return akses.periksa_radiologi;} 
    public static boolean getkasir_ralan(){return akses.kasir_ralan;} 
    public static boolean getdeposit_pasien(){return akses.deposit_pasien;} 
    public static boolean getpiutang_pasien(){return akses.piutang_pasien;} 
    public static boolean getpeminjaman_berkas(){return akses.peminjaman_berkas;} 
    public static boolean getbarcode(){return akses.barcode;} 
    public static boolean getpresensi_harian(){return akses.presensi_harian;} 
    public static boolean getpresensi_bulanan(){return akses.presensi_bulanan;} 
    public static boolean getpegawai_admin(){return akses.pegawai_admin;} 
    public static boolean getpegawai_user(){return akses.pegawai_user;} 
    public static boolean getsuplier(){return akses.suplier;} 
    public static boolean getsatuan_barang(){return akses.satuan_barang;} 
    public static boolean getkonversi_satuan(){return akses.konversi_satuan;} 
    public static boolean getjenis_barang(){return akses.jenis_barang;} 
    public static boolean getobat(){return akses.obat;} 
    public static boolean getstok_opname_obat(){return akses.stok_opname_obat;} 
    public static boolean getstok_obat_pasien(){return akses.stok_obat_pasien;} 
    public static boolean getpengadaan_obat(){return akses.pengadaan_obat;} 
    public static boolean getpemesanan_obat(){return akses.pemesanan_obat;} 
    public static boolean getpenjualan_obat(){return akses.penjualan_obat;} 
    public static void setpenjualan_obatfalse(){akses.penjualan_obat=false;} 
    public static boolean getpiutang_obat(){return akses.piutang_obat;} 
    public static boolean getretur_ke_suplier(){return akses.retur_ke_suplier;} 
    public static boolean getretur_dari_pembeli(){return akses.retur_dari_pembeli;} 
    public static boolean getretur_obat_ranap(){return akses.retur_obat_ranap;} 
    public static boolean getretur_piutang_pasien(){return akses.retur_piutang_pasien;} 
    public static boolean getkeuntungan_penjualan(){return akses.keuntungan_penjualan;} 
    public static boolean getkeuntungan_beri_obat(){return akses.keuntungan_beri_obat;} 
    public static boolean getsirkulasi_obat(){return akses.sirkulasi_obat;} 
    public static boolean getipsrs_barang(){return akses.ipsrs_barang;} 
    public static boolean getipsrs_pengadaan_barang(){return akses.ipsrs_pengadaan_barang;} 
    public static boolean getipsrs_stok_keluar(){return akses.ipsrs_stok_keluar;} 
    public static boolean getipsrs_rekap_pengadaan(){return akses.ipsrs_rekap_pengadaan;} 
    public static boolean getipsrs_rekap_stok_keluar(){return akses.ipsrs_rekap_stok_keluar;} 
    public static boolean getipsrs_pengeluaran_harian(){return akses.ipsrs_pengeluaran_harian;} 
    public static boolean getipsrs_jenis_barang(){return akses.ipsrs_jenis_barang;} 
    public static boolean getinventaris_jenis(){return akses.inventaris_jenis;} 
    public static boolean getinventaris_kategori(){return akses.inventaris_kategori;} 
    public static boolean getinventaris_merk(){return akses.inventaris_merk;} 
    public static boolean getinventaris_ruang(){return akses.inventaris_ruang;} 
    public static boolean getinventaris_produsen(){return akses.inventaris_produsen;} 
    public static boolean getinventaris_koleksi(){return akses.inventaris_koleksi;} 
    public static boolean getinventaris_inventaris(){return akses.inventaris_inventaris;} 
    public static boolean getinventaris_sirkulasi(){return akses.inventaris_sirkulasi;} 
    public static boolean getparkir_jenis(){return akses.parkir_jenis;} 
    public static boolean getparkir_in(){return akses.parkir_in;} 
    public static boolean getparkir_out(){return akses.parkir_out;} 
    public static boolean getparkir_rekap_harian(){return akses.parkir_rekap_harian;} 
    public static boolean getparkir_rekap_bulanan(){return akses.parkir_rekap_bulanan;} 
    public static boolean getinformasi_kamar(){return akses.informasi_kamar;} 
    public static boolean getharian_tindakan_poli(){return akses.harian_tindakan_poli;} 
    public static boolean getobat_per_poli(){return akses.obat_per_poli;} 
    public static boolean getobat_per_kamar(){return akses.obat_per_kamar;} 
    public static boolean getobat_per_dokter_ralan(){return akses.obat_per_dokter_ralan;} 
    public static boolean getobat_per_dokter_ranap(){return akses.obat_per_dokter_ranap;} 
    public static boolean getharian_dokter(){return akses.harian_dokter;} 
    public static boolean getbulanan_dokter(){return akses.bulanan_dokter;} 
    public static boolean getharian_paramedis(){return akses.harian_paramedis;} 
    public static boolean getbulanan_paramedis(){return akses.bulanan_paramedis;} 
    public static boolean getpembayaran_ralan(){return akses.pembayaran_ralan;} 
    public static boolean getpembayaran_ranap(){return akses.pembayaran_ranap;} 
    public static boolean getrekap_pembayaran_ralan(){return akses.rekap_pembayaran_ralan;} 
    public static boolean getrekap_pembayaran_ranap(){return akses.rekap_pembayaran_ranap;} 
    public static boolean gettagihan_masuk(){return akses.tagihan_masuk;} 
    public static boolean gettambahan_biaya(){return akses.tambahan_biaya;} 
    public static boolean getpotongan_biaya(){return akses.potongan_biaya;} 
    public static boolean getresep_obat(){return akses.resep_obat;} 
    public static boolean getresume_pasien(){return akses.resume_pasien;} 
    public static boolean getpenyakit_ralan(){return akses.penyakit_ralan;} 
    public static boolean getpenyakit_ranap(){return akses.penyakit_ranap;} 
    public static boolean getkamar(){return akses.kamar;} 
    public static boolean gettarif_ralan(){return akses.tarif_ralan;} 
    public static boolean gettarif_ranap(){return akses.tarif_ranap;} 
    public static boolean gettarif_lab(){return akses.tarif_lab;} 
    public static boolean gettarif_radiologi(){return akses.tarif_radiologi;} 
    public static boolean gettarif_operasi(){return akses.tarif_operasi;} 
    public static boolean getakun_rekening(){return akses.akun_rekening;} 
    public static boolean getrekening_tahun(){return akses.rekening_tahun;} 
    public static boolean getposting_jurnal(){return akses.posting_jurnal;} 
    public static boolean getbuku_besar(){return akses.buku_besar;} 
    public static boolean getcashflow(){return akses.cashflow;} 
    public static boolean getkeuangan(){return akses.keuangan;} 
    public static boolean getpengeluaran(){return akses.pengeluaran;} 
    public static boolean getsetup_pjlab(){return akses.setup_pjlab;} 
    public static boolean getsetup_otolokasi(){return akses.setup_otolokasi;} 
    public static boolean getsetup_jam_kamin(){return akses.setup_jam_kamin;} 
    public static boolean getsetup_embalase(){return akses.setup_embalase;} 
    public static boolean gettracer_login(){return akses.tracer_login;} 
    public static boolean getdisplay(){return akses.display;} 
    public static boolean getset_harga_obat(){return akses.set_harga_obat;} 
    public static boolean getset_penggunaan_tarif(){return akses.set_penggunaan_tarif;} 
    public static boolean getset_oto_ralan(){return akses.set_oto_ralan;} 
    public static boolean getbiaya_harian(){return akses.biaya_harian;} 
    public static boolean getbiaya_masuk_sekali(){return akses.biaya_masuk_sekali;} 
    public static boolean getset_no_rm(){return akses.set_no_rm;} 
    public static boolean getbilling_ralan(){return akses.billing_ralan;} 
    public static boolean getbilling_ranap(){return akses.billing_ranap;}
    public static String getkode(){return akses.kode;}
    public static void setkdbangsal(String kdbangsal){akses.kdbangsal=kdbangsal;}
    public static String getkdbangsal(){return akses.kdbangsal;} 
    public static void setalamatip(String alamatip){akses.alamatip=alamatip;}
    public static String getalamatip(){return akses.alamatip;}     
    public static void setform(String form){akses.form=form;}
    public static String getform(){return akses.form;}   
    public static void setnamauser(String namauser){akses.namauser=namauser;}
    public static String getnamauser(){return akses.namauser;}   
    public static void setstatus(boolean status){akses.status=status;}
    public static boolean getstatus(){return akses.status;}
    public static boolean getjm_ranap_dokter(){return akses.jm_ranap_dokter;}     
    public static boolean getigd(){return akses.igd;}     
    public static boolean getbarcoderalan(){return akses.barcoderalan;}     
    public static boolean getbarcoderanap(){return akses.barcoderanap;}    
    public static boolean getset_harga_obat_ralan(){return akses.set_harga_obat_ralan;}  
    public static boolean getset_harga_obat_ranap(){return akses.set_harga_obat_ranap;}  
    public static boolean getpenyakit_pd3i(){return akses.penyakit_pd3i;}  
    public static boolean getsurveilans_pd3i(){return akses.surveilans_pd3i;}  
    public static boolean getsurveilans_ralan(){return akses.surveilans_ralan;}  
    public static boolean getdiagnosa_pasien(){return akses.diagnosa_pasien;}  
    public static boolean getsurveilans_ranap(){return akses.surveilans_ranap;}  
    public static boolean getpny_takmenular_ranap(){return akses.pny_takmenular_ranap;}  
    public static boolean getpny_takmenular_ralan(){return akses.pny_takmenular_ralan;}
    public static void setnamars(String namars){akses.namars=namars;}
    public static void setalamatrs(String alamatrs){akses.alamatrs=alamatrs;}
    public static void setkabupatenrs(String kabupatenrs){akses.kabupatenrs=kabupatenrs;}
    public static void setpropinsirs(String propinsirs){akses.propinsirs=propinsirs;}
    public static void setkontakrs(String kontakrs){akses.kontakrs=kontakrs;}
    public static void setemailrs(String emailrs){akses.emailrs=emailrs;}
    public static void setkodeppkbpjs(String kode_ppk){akses.kode_ppk=kode_ppk;}
    public static String getnamars(){return akses.namars;}
    public static String getalamatrs(){return akses.alamatrs;}
    public static String getkabupatenrs(){return akses.kabupatenrs;}
    public static String getpropinsirs(){return akses.propinsirs;}
    public static String getkontakrs(){return akses.kontakrs;}
    public static String getemailrs(){return akses.emailrs;}
    public static String getkodeppkbpjs(){return akses.kode_ppk;}
    public static boolean getkunjungan_ralan(){return akses.kunjungan_ralan;}
    public static boolean getrl32(){return akses.rl32;}
    public static boolean getrl33(){return akses.rl33;}
    public static boolean getrl37(){return akses.rl37;}
    public static boolean getrl38(){return akses.rl38;}
    public static boolean getharian_tindakan_dokter(){return akses.harian_tindakan_dokter;}
    public static boolean getsms(){return akses.sms;}
    public static boolean getsidikjari(){return akses.sidikjari;} 
    public static boolean getjam_masuk(){return akses.jam_masuk;}  
    public static boolean getjadwal_pegawai(){return akses.jadwal_pegawai;}
    public static boolean getparkir_barcode(){return akses.parkir_barcode;}
    public static boolean getset_nota(){return akses.set_nota;}
    public static boolean getdpjp_ranap(){return akses.dpjp_ranap;}
    public static boolean getmutasi_barang(){return akses.mutasi_barang;}
    public static boolean getrl34(){return akses.rl34;}
    public static boolean getrl36(){return akses.rl36;}
    public static boolean getfee_visit_dokter(){return akses.fee_visit_dokter;}
    public static boolean getfee_bacaan_ekg(){return akses.fee_bacaan_ekg;}
    public static boolean getfee_rujukan_rontgen(){return akses.fee_rujukan_rontgen;}
    public static boolean getfee_rujukan_ranap(){return akses.fee_rujukan_ranap;}
    public static boolean getfee_ralan(){return akses.fee_ralan;}
    public static boolean getakun_bayar(){return akses.akun_bayar;}
    public static boolean getbayar_pemesanan_obat(){return akses.bayar_pemesanan_obat;}
    public static boolean getobat_per_dokter_peresep(){return akses.obat_per_dokter_peresep;}
    public static boolean getpemasukan_lain(){return akses.pemasukan_lain;}
    public static boolean getpengaturan_rekening(){return akses.pengaturan_rekening;}
    public static boolean getclosing_kasir(){return akses.closing_kasir;}
    public static boolean getketerlambatan_presensi(){return akses.keterlambatan_presensi;} 
    public static boolean getset_harga_kamar(){return akses.set_harga_kamar;} 
    public static boolean getrekap_per_shift(){return akses.rekap_per_shift;}
    public static boolean getbpjs_cek_nik(){return akses.bpjs_cek_nik;} 
    public static boolean getbpjs_cek_kartu(){return akses.bpjs_cek_kartu;} 
    public static boolean getbpjs_cek_riwayat(){return akses.bpjs_cek_riwayat;} 
    public static boolean getobat_per_cara_bayar(){return akses.obat_per_cara_bayar;} 
    public static boolean getkunjungan_ranap(){return akses.kunjungan_ranap;} 
    public static boolean getbayar_piutang(){return akses.bayar_piutang;} 
    public static boolean getpayment_point(){return akses.payment_point;} 
    public static boolean getbpjs_cek_nomor_rujukan(){return akses.bpjs_cek_nomor_rujukan;}
    public static boolean geticd9(){return akses.icd9;}
    public static boolean getdarurat_stok(){return akses.darurat_stok;}
    public static boolean getretensi_rm(){return akses.retensi_rm;}
    public static boolean gettemporary_presensi(){return akses.temporary_presensi;}
    public static boolean getjurnal_harian(){return akses.jurnal_harian;}
    public static boolean getsirkulasi_obat2(){return akses.sirkulasi_obat2;}
    public static boolean getedit_registrasi(){return akses.edit_registrasi;}
    public static boolean getbpjs_referensi_diagnosa(){return akses.bpjs_referensi_diagnosa;}
    public static boolean getbpjs_referensi_poli(){return akses.bpjs_referensi_poli;}
    public static boolean getindustrifarmasi(){return akses.industrifarmasi;}
    public static boolean getharian_js(){return akses.harian_js;}
    public static boolean getbulanan_js(){return akses.bulanan_js;}
    public static boolean getharian_paket_bhp(){return akses.harian_paket_bhp;}
    public static boolean getbulanan_paket_bhp(){return akses.bulanan_paket_bhp;}
    public static boolean getpiutang_pasien2(){return akses.piutang_pasien2;}
    public static boolean getbpjs_referensi_faskes(){return akses.bpjs_referensi_faskes;} 
    public static boolean getbpjs_sep(){return akses.bpjs_sep;} 
    public static boolean getpengambilan_utd(){return akses.pengambilan_utd;} 
    public static boolean gettarif_utd(){return akses.tarif_utd;} 
    public static boolean getpengambilan_utd2(){return akses.pengambilan_utd2;}  
    public static boolean getutd_medis_rusak(){return akses.utd_medis_rusak;}  
    public static boolean getpengambilan_penunjang_utd(){return akses.pengambilan_penunjang_utd;}  
    public static boolean getpengambilan_penunjang_utd2(){return akses.pengambilan_penunjang_utd2;}  
    public static boolean getutd_penunjang_rusak(){return akses.utd_penunjang_rusak;}  
    public static boolean getsuplier_penunjang(){return akses.suplier_penunjang;}  
    public static boolean getutd_donor(){return akses.utd_donor;}  
    public static boolean getbpjs_monitoring_klaim(){return akses.bpjs_monitoring_klaim;}  
    public static boolean getutd_cekal_darah(){return akses.utd_cekal_darah;}  
    public static boolean getutd_komponen_darah(){return akses.utd_komponen_darah;}  
    public static boolean getutd_stok_darah(){return akses.utd_stok_darah;}  
    public static boolean getutd_pemisahan_darah(){return akses.utd_pemisahan_darah;}  
    public static boolean getharian_kamar(){return akses.harian_kamar;}  
    public static boolean getrincian_piutang_pasien(){return akses.rincian_piutang_pasien;}  
    public static boolean getkeuntungan_beri_obat_nonpiutang(){return akses.keuntungan_beri_obat_nonpiutang;}  
    public static boolean getreklasifikasi_ralan(){return akses.reklasifikasi_ralan;}  
    public static boolean getreklasifikasi_ranap(){return akses.reklasifikasi_ranap;}  
    public static boolean getutd_penyerahan_darah(){return akses.utd_penyerahan_darah;} 
    public static void setutd_penyerahan_darahfalse(){akses.utd_penyerahan_darah=false;} 
    public static boolean gethutang_obat(){return akses.hutang_obat;}  
    public static boolean getriwayat_obat_alkes_bhp(){return akses.riwayat_obat_alkes_bhp;}
    public static boolean getsensus_harian_poli(){return akses.sensus_harian_poli;}
    public static boolean getrl4a(){return akses.rl4a;}
    public static boolean getaplicare_referensi_kamar(){return akses.aplicare_referensi_kamar;}
    public static boolean getaplicare_ketersediaan_kamar(){return akses.aplicare_ketersediaan_kamar;}
    public static boolean getinacbg_klaim_baru_otomatis(){return akses.inacbg_klaim_baru_otomatis;}
    public static boolean getinacbg_klaim_baru_manual(){return akses.inacbg_klaim_baru_manual;}
    public static boolean getinacbg_coder_nik(){return akses.inacbg_coder_nik;}
    public static boolean getmutasi_berkas(){return akses.mutasi_berkas;}
    public static boolean getakun_piutang(){return akses.akun_piutang;}
    public static boolean getharian_kso(){return akses.harian_kso;}
    public static boolean getbulanan_kso(){return akses.bulanan_kso;}
    public static boolean getharian_menejemen(){return akses.harian_menejemen;}
    public static boolean getbulanan_menejemen(){return akses.bulanan_menejemen;}
    public static boolean getinhealth_cek_eligibilitas(){return akses.inhealth_cek_eligibilitas;}
    public static boolean getinhealth_referensi_jenpel_ruang_rawat(){return akses.inhealth_referensi_jenpel_ruang_rawat;}
    public static boolean getinhealth_referensi_poli(){return akses.inhealth_referensi_poli;}
    public static boolean getinhealth_referensi_faskes(){return akses.inhealth_referensi_faskes;}
    public static boolean getinhealth_sjp(){return akses.inhealth_sjp;}
    public static boolean getpiutang_ralan(){return akses.piutang_ralan;}
    public static boolean getpiutang_ranap(){return akses.piutang_ranap;}
    public static boolean getdetail_piutang_penjab(){return akses.detail_piutang_penjab;}
    public static boolean getlama_pelayanan_ralan(){return akses.lama_pelayanan_ralan;}
    public static boolean getcatatan_pasien(){return akses.catatan_pasien;}
    public static boolean getrl4b(){return akses.rl4b;}
    public static boolean getrl4asebab(){return akses.rl4asebab;}
    public static boolean getrl4bsebab(){return akses.rl4bsebab;}
    public static boolean getdata_HAIs(){return akses.data_HAIs;}
    public static boolean getharian_HAIs(){return akses.harian_HAIs;}
    public static boolean getbulanan_HAIs(){return akses.bulanan_HAIs;}
    public static boolean gethitung_bor(){return akses.hitung_bor;}
    public static boolean getperusahaan_pasien(){return akses.perusahaan_pasien;}
    public static boolean getresep_dokter(){return akses.resep_dokter;}
    public static void setresep_dokterfalse(){akses.resep_dokter=false;} 
    public static boolean getlama_pelayanan_apotek(){return akses.lama_pelayanan_apotek;}
    public static boolean gethitung_alos(){return akses.hitung_alos;}
    public static boolean getdetail_tindakan(){return akses.detail_tindakan;}
    public static boolean getrujukan_poli_internal(){return akses.rujukan_poli_internal;}
    public static boolean getrekap_poli_anak(){return akses.rekap_poli_anak;}
    public static boolean getgrafik_kunjungan_poli(){return akses.grafik_kunjungan_poli;}
    public static boolean getgrafik_kunjungan_perdokter(){return akses.grafik_kunjungan_perdokter;}
    public static boolean getgrafik_kunjungan_perpekerjaan(){return akses.grafik_kunjungan_perpekerjaan;}
    public static boolean getgrafik_kunjungan_perpendidikan(){return akses.grafik_kunjungan_perpendidikan;}
    public static boolean getgrafik_kunjungan_pertahun(){return akses.grafik_kunjungan_pertahun;}
    public static boolean getberkas_digital_perawatan(){return akses.berkas_digital_perawatan;}
    public static boolean getpenyakit_menular_ranap(){return akses.penyakit_menular_ranap;}
    public static boolean getpenyakit_menular_ralan(){return akses.penyakit_menular_ralan;}
    public static boolean getgrafik_kunjungan_perbulan(){return akses.grafik_kunjungan_perbulan;}
    public static boolean getgrafik_kunjungan_pertanggal(){return akses.grafik_kunjungan_pertanggal;}
    public static boolean getgrafik_kunjungan_demografi(){return akses.grafik_kunjungan_demografi;}
    public static boolean getgrafik_kunjungan_statusdaftartahun(){return akses.grafik_kunjungan_statusdaftartahun;}
    public static boolean getgrafik_kunjungan_statusdaftartahun2(){return akses.grafik_kunjungan_statusdaftartahun2;}                        
    public static boolean getgrafik_kunjungan_statusdaftarbulan(){return akses.grafik_kunjungan_statusdaftarbulan;}  
    public static boolean getgrafik_kunjungan_statusdaftarbulan2(){return akses.grafik_kunjungan_statusdaftarbulan2;} 
    public static boolean getgrafik_kunjungan_statusdaftartanggal(){return akses.grafik_kunjungan_statusdaftartanggal;} 
    public static boolean getgrafik_kunjungan_statusdaftartanggal2(){return akses.grafik_kunjungan_statusdaftartanggal2;} 
    public static boolean getgrafik_kunjungan_statusbataltahun(){return akses.grafik_kunjungan_statusbataltahun;} 
    public static boolean getgrafik_kunjungan_statusbatalbulan(){return akses.grafik_kunjungan_statusbatalbulan;}
    public static boolean getpcare_cek_penyakit(){return akses.pcare_cek_penyakit;}
    public static boolean getgrafik_kunjungan_statusbataltanggal(){return akses.grafik_kunjungan_statusbataltanggal;}
    public static boolean getkategori_barang(){return akses.kategori_barang;}
    public static boolean getgolongan_barang(){return akses.golongan_barang;}
    public static boolean getpemberian_obat_pertanggal(){return akses.pemberian_obat_pertanggal;}
    public static boolean getpenjualan_obat_pertanggal(){return akses.penjualan_obat_pertanggal;}
    public static boolean getpcare_cek_kesadaran(){return akses.pcare_cek_kesadaran;}
    public static boolean getpembatalan_periksa_dokter(){return akses.pembatalan_periksa_dokter;}
    public static boolean getpembayaran_per_unit(){return akses.pembayaran_per_unit;}
    public static boolean getrekap_pembayaran_per_unit(){return akses.rekap_pembayaran_per_unit;}
    public static boolean getgrafik_kunjungan_percarabayar(){return akses.grafik_kunjungan_percarabayar;}
    public static boolean getipsrs_pengadaan_pertanggal(){return akses.ipsrs_pengadaan_pertanggal;}
    public static boolean getipsrs_stokkeluar_pertanggal(){return akses.ipsrs_stokkeluar_pertanggal;}
    public static boolean getgrafik_kunjungan_ranaptahun(){return akses.grafik_kunjungan_ranaptahun;}
    public static boolean getpcare_cek_rujukan(){return akses.pcare_cek_rujukan;}
    public static boolean getgrafik_lab_ralantahun(){return akses.grafik_lab_ralantahun;}
    public static boolean getgrafik_rad_ralantahun(){return akses.grafik_rad_ralantahun;}
    public static boolean getcek_entry_ralan(){return akses.cek_entry_ralan;}
    public static boolean getinacbg_klaim_baru_manual2(){return akses.inacbg_klaim_baru_manual2;}
    public static boolean getpermintaan_medis(){return akses.permintaan_medis;}
    public static boolean getrekap_permintaan_medis(){return akses.rekap_permintaan_medis;}
    public static boolean getsurat_pemesanan_medis(){return akses.surat_pemesanan_medis;}
    public static boolean getpermintaan_non_medis(){return akses.permintaan_non_medis;}
    public static boolean getrekap_permintaan_non_medis(){return akses.rekap_permintaan_non_medis;}
    public static boolean getsurat_pemesanan_non_medis(){return akses.surat_pemesanan_non_medis;}
    public static boolean getgrafik_per_perujuk(){return akses.grafik_per_perujuk;}
    public static boolean getbpjs_cek_prosedur(){return akses.bpjs_cek_prosedur;}
    public static boolean getbpjs_cek_kelas_rawat(){return akses.bpjs_cek_kelas_rawat;}
    public static boolean getbpjs_cek_dokter(){return akses.bpjs_cek_dokter;}
    public static boolean getbpjs_cek_spesialistik(){return akses.bpjs_cek_spesialistik;}
    public static boolean getbpjs_cek_ruangrawat(){return akses.bpjs_cek_ruangrawat;}
    public static boolean getbpjs_cek_carakeluar(){return  akses.bpjs_cek_carakeluar;}
    public static boolean getbpjs_cek_pasca_pulang(){return akses.bpjs_cek_pasca_pulang;} 
    public static boolean getdetail_tindakan_okvk(){return akses.detail_tindakan_okvk;}
    public static boolean getbilling_parsial(){return akses.billing_parsial;}
    public static boolean getbpjs_cek_nomor_rujukan_rs(){return akses.bpjs_cek_nomor_rujukan_rs;}
    public static boolean getbpjs_cek_rujukan_kartu_pcare(){return akses.bpjs_cek_rujukan_kartu_pcare;}
    public static boolean getbpjs_cek_rujukan_kartu_rs(){return akses.bpjs_cek_rujukan_kartu_rs;}
    public static boolean getakses_depo_obat(){return akses.akses_depo_obat;}
    public static boolean getbpjs_rujukan_keluar(){return akses.bpjs_rujukan_keluar;}
    public static boolean getgrafik_lab_ralanbulan(){return akses.grafik_lab_ralanbulan;}
    public static boolean getpengeluaran_stok_apotek(){return akses.pengeluaran_stok_apotek;}
    public static boolean getgrafik_rad_ralanbulan(){return akses.grafik_rad_ralanbulan;}
    public static boolean getdetailjmdokter2(){return akses.detailjmdokter2;}
    public static boolean getpengaduan_pasien(){return akses.pengaduan_pasien;}
    public static boolean getgrafik_lab_ralanhari(){return akses.grafik_lab_ralanhari;}
    public static boolean getgrafik_rad_ralanhari(){return akses.grafik_rad_ralanhari;}
    public static boolean getsensus_harian_ralan(){return akses.sensus_harian_ralan;}
    public static boolean getmetode_racik(){return akses.metode_racik;}
    public static boolean getpembayaran_akun_bayar(){return akses.pembayaran_akun_bayar;}
    public static boolean getpengguna_obat_resep(){return akses.pengguna_obat_resep;}
    public static boolean getrekap_pemesanan(){return akses.rekap_pemesanan;}
    public static boolean getmaster_berkas_pegawai(){return akses.master_berkas_pegawai;}
    public static boolean getberkas_kepegawaian(){return akses.berkas_kepegawaian;}
    public static boolean getriwayat_jabatan(){return akses.riwayat_jabatan;}
    public static boolean getriwayat_pendidikan(){return akses.riwayat_pendidikan;}
    public static boolean getriwayat_naik_gaji(){return akses.riwayat_naik_gaji;}
    public static boolean getkegiatan_ilmiah(){return akses.kegiatan_ilmiah;}
    public static boolean getriwayat_penghargaan(){return akses.riwayat_penghargaan;}
    public static boolean getriwayat_penelitian(){return akses.riwayat_penelitian;}
    public static boolean getpenerimaan_non_medis(){return akses.penerimaan_non_medis;}
    public static boolean getbayar_pesan_non_medis(){return akses.bayar_pesan_non_medis;}
    public static boolean gethutang_barang_non_medis(){return akses.hutang_barang_non_medis;}
    public static boolean getrekap_pemesanan_non_medis(){return akses.rekap_pemesanan_non_medis;}
    public static boolean getinsiden_keselamatan(){return akses.insiden_keselamatan;}
    public static boolean getinsiden_keselamatan_pasien(){return akses.insiden_keselamatan_pasien;}
    public static boolean getgrafik_ikp_pertahun(){return akses.grafik_ikp_pertahun;}
    public static boolean getgrafik_ikp_perbulan(){return akses.grafik_ikp_perbulan;}
    public static boolean getgrafik_ikp_pertanggal(){return akses.grafik_ikp_pertanggal;}
    public static boolean getriwayat_data_batch(){return akses.riwayat_data_batch;}
    public static boolean getgrafik_ikp_jenis(){return akses.grafik_ikp_jenis;}
    public static boolean getgrafik_ikp_dampak(){return akses.grafik_ikp_dampak;}
    public static boolean getpiutang_akun_piutang(){return akses.piutang_akun_piutang;}
    public static void setresep_obatfalse(){akses.resep_obat=false;} 
    public static boolean getgrafik_kunjungan_per_agama(){return akses.grafik_kunjungan_per_agama;}
    public static boolean getgrafik_kunjungan_per_umur(){return akses.grafik_kunjungan_per_umur;}
    public static boolean getsuku_bangsa(){return akses.suku_bangsa;}
    public static boolean getbahasa_pasien(){return akses.bahasa_pasien;}
    public static boolean getgolongan_tni(){return akses.golongan_tni;}
    public static boolean getsatuan_tni(){return akses.satuan_tni;}
    public static boolean getjabatan_tni(){return akses.jabatan_tni;}
    public static boolean getpangkat_tni(){return akses.pangkat_tni;}
    public static boolean getgolongan_polri(){return akses.golongan_polri;}
    public static boolean getsatuan_polri(){return akses.satuan_polri;}
    public static boolean getjabatan_polri(){return akses.jabatan_polri;}
    public static boolean getpangkat_polri(){return akses.pangkat_polri;}
    public static boolean getcacat_fisik(){return akses.cacat_fisik;}
    public static boolean getgrafik_kunjungan_suku(){return akses.grafik_kunjungan_suku;}
    public static boolean getgrafik_kunjungan_bahasa(){return akses.grafik_kunjungan_bahasa;}
    public static boolean getbooking_operasi(){return akses.booking_operasi;}
    public static boolean getmapping_poli_bpjs(){return akses.mapping_poli_bpjs;}
    public static boolean getgrafik_kunjungan_per_cacat(){return akses.grafik_kunjungan_per_cacat;}
    public static boolean getbarang_cssd(){return akses.barang_cssd;}
    public static boolean getskdp_bpjs(){return akses.skdp_bpjs;}
    public static boolean getbooking_registrasi(){return akses.booking_registrasi;}
    public static boolean getbpjs_cek_propinsi(){return akses.bpjs_cek_propinsi;}
    public static boolean getbpjs_cek_kabupaten(){return akses.bpjs_cek_kabupaten;}
    public static boolean getbpjs_cek_kecamatan(){return akses.bpjs_cek_kecamatan;}
    public static boolean getbpjs_cek_dokterdpjp(){return akses.bpjs_cek_dokterdpjp;}
    public static boolean getbpjs_cek_riwayat_rujukanrs(){return akses.bpjs_cek_riwayat_rujukanrs;}
    public static boolean getbpjs_cek_tanggal_rujukan(){return akses.bpjs_cek_tanggal_rujukan;}
    public static boolean getpermintaan_lab(){return akses.permintaan_lab;}
    public static void setperiksalabfalse(){akses.periksa_lab=false;} 
    public static void setperiksalabpafalse(){akses.pemeriksaan_lab_pa=false;} 
    public static void setperiksalabmbfalse(){akses.pemeriksaan_lab_mb=false;} 
    public static void setpermintaanlabfalse(){akses.permintaan_lab=false;} 
    public static boolean getpermintaan_radiologi(){return akses.permintaan_radiologi;}
    public static void setperiksaradiologifalse(){akses.periksa_radiologi=false;} 
    public static void setpermintaanradiologifalse(){akses.permintaan_radiologi=false;} 
    public static boolean getsurat_indeks(){return akses.surat_indeks;}
    public static boolean getsurat_map(){return akses.surat_map;}
    public static boolean getsurat_almari(){return akses.surat_almari;}
    public static boolean getsurat_rak(){return akses.surat_rak;}
    public static boolean getsurat_ruang(){return akses.surat_ruang;}
    public static boolean getsurat_klasifikasi(){return akses.surat_klasifikasi;}
    public static boolean getsurat_status(){return akses.surat_status;}
    public static boolean getsurat_sifat(){return akses.surat_sifat;}
    public static boolean getsurat_balas(){return akses.surat_balas;}
    public static boolean getsurat_masuk(){return akses.surat_masuk;}
    public static boolean getpcare_cek_dokter(){return akses.pcare_cek_dokter;}
    public static boolean getpcare_cek_poli(){return akses.pcare_cek_poli;}
    public static boolean getpcare_cek_provider(){return akses.pcare_cek_provider;}
    public static boolean getpcare_cek_statuspulang(){return akses.pcare_cek_statuspulang;}
    public static boolean getpcare_cek_spesialis(){return akses.pcare_cek_spesialis;}
    public static boolean getpcare_cek_subspesialis(){return akses.pcare_cek_subspesialis;}
    public static boolean getpcare_cek_sarana(){return akses.pcare_cek_sarana;}
    public static boolean getpcare_cek_khusus(){return akses.pcare_cek_khusus;}
    public static boolean getpcare_cek_obat(){return akses.pcare_cek_obat;}
    public static boolean getpcare_cek_tindakan(){return akses.pcare_cek_tindakan;}
    public static boolean getpcare_cek_faskessubspesialis(){return akses.pcare_cek_faskessubspesialis;}
    public static boolean getpcare_cek_faskesalihrawat(){return akses.pcare_cek_faskesalihrawat;}
    public static boolean getpcare_cek_faskesthalasemia(){return akses.pcare_cek_faskesthalasemia;}
    public static boolean getpcare_mapping_obat(){return akses.pcare_mapping_obat;}
    public static boolean getpcare_mapping_tindakan(){return akses.pcare_mapping_tindakan;}
    public static boolean getpcare_club_prolanis(){return akses.pcare_club_prolanis;}
    public static boolean getpcare_mapping_poli(){return akses.pcare_mapping_poli;}
    public static boolean getpcare_kegiatan_kelompok(){return akses.pcare_kegiatan_kelompok;}
    public static boolean getpcare_mapping_tindakan_ranap(){return akses.pcare_mapping_tindakan_ranap;}
    public static boolean getpcare_peserta_kegiatan_kelompok(){return akses.pcare_peserta_kegiatan_kelompok;}
    public static boolean getsirkulasi_obat3(){return akses.sirkulasi_obat3;}
    public static boolean getbridging_pcare_daftar(){return akses.bridging_pcare_daftar;}
    public static boolean getpcare_mapping_dokter(){return akses.pcare_mapping_dokter;}
    public static boolean getranap_per_ruang(){return akses.ranap_per_ruang;}
    public static boolean getpenyakit_ranap_cara_bayar(){return akses.penyakit_ranap_cara_bayar;}
    public static boolean getanggota_militer_dirawat(){return akses.anggota_militer_dirawat;}
    public static boolean getset_input_parsial(){return akses.set_input_parsial;}
    public static boolean getlama_pelayanan_radiologi(){return akses.lama_pelayanan_radiologi;}
    public static boolean getlama_pelayanan_lab(){return akses.lama_pelayanan_lab;}
    public static boolean getbpjs_cek_sep(){return akses.bpjs_cek_sep;}
    public static boolean getcatatan_perawatan(){return akses.catatan_perawatan;}
    public static boolean getsurat_keluar(){return akses.surat_keluar;}
    public static boolean getkegiatan_farmasi(){return akses.kegiatan_farmasi;}
    public static boolean getstok_opname_logistik(){return akses.stok_opname_logistik;}
    public static boolean getsirkulasi_non_medis(){return akses.sirkulasi_non_medis;} 
    public static boolean getrekap_lab_pertahun(){return akses.rekap_lab_pertahun;} 
    public static boolean getperujuk_lab_pertahun(){return akses.perujuk_lab_pertahun;}
    public static boolean getrekap_radiologi_pertahun(){return akses.rekap_radiologi_pertahun;}
    public static boolean getperujuk_radiologi_pertahun(){return akses.perujuk_radiologi_pertahun;}
    public static boolean getjumlah_porsi_diet(){return akses.jumlah_porsi_diet;}
    public static boolean getjumlah_macam_diet(){return akses.jumlah_macam_diet;}
    public static boolean getpayment_point2(){return akses.payment_point2;}
    public static boolean getpembayaran_akun_bayar2(){return akses.pembayaran_akun_bayar2;}
    public static boolean gethapus_nota_salah(){return akses.hapus_nota_salah;}
    public static boolean gethais_perbangsal(){return akses.hais_perbangsal;}
    public static boolean getppn_obat(){return akses.ppn_obat;}
    public static boolean getsaldo_akun_perbulan(){return akses.saldo_akun_perbulan;}
    public static boolean getdisplay_apotek(){return akses.display_apotek;}
    public static boolean getsisrute_referensi_faskes(){return akses.sisrute_referensi_faskes;}
    public static boolean getsisrute_referensi_alasanrujuk(){return akses.sisrute_referensi_alasanrujuk;}
    public static boolean getsisrute_referensi_diagnosa(){return akses.sisrute_referensi_diagnosa;}
    public static boolean getsisrute_rujukan_masuk(){return akses.sisrute_rujukan_masuk;}
    public static boolean getAktif(){return akses.aktif;}
    public static void setAktif(boolean status){akses.aktif=status;} 
    public static boolean getsisrute_rujukan_keluar(){return akses.sisrute_rujukan_keluar;}
    public static boolean getbpjs_cek_skdp(){return akses.bpjs_cek_skdp;}
    public static boolean getdata_batch(){return akses.data_batch;}
    public static boolean getkunjungan_permintaan_lab(){return akses.kunjungan_permintaan_lab;}
    public static boolean getkunjungan_permintaan_lab2(){return akses.kunjungan_permintaan_lab2;}
    public static boolean getkunjungan_permintaan_radiologi(){return akses.kunjungan_permintaan_radiologi;}
    public static boolean getkunjungan_permintaan_radiologi2(){return akses.kunjungan_permintaan_radiologi2;}
    public static boolean getpcare_pemberian_obat(){return akses.pcare_pemberian_obat;}
    public static boolean getpcare_pemberian_tindakan(){return akses.pcare_pemberian_tindakan;}
    public static boolean getpembayaran_akun_bayar3(){return akses.pembayaran_akun_bayar3;}
    public static boolean getpassword_asuransi(){return akses.password_asuransi;}
    public static boolean getkemenkes_sitt(){return akses.kemenkes_sitt;}
    public static boolean getsiranap_ketersediaan_kamar(){return akses.siranap_ketersediaan_kamar;}
    public static boolean getgrafik_tb_periodelaporan(){return akses.grafik_tb_periodelaporan;}
    public static boolean getgrafik_tb_rujukan(){return akses.grafik_tb_rujukan;}
    public static boolean getgrafik_tb_riwayat(){return akses.grafik_tb_riwayat;}
    public static boolean getgrafik_tb_tipediagnosis(){return akses.grafik_tb_tipediagnosis;}
    public static boolean getgrafik_tb_statushiv(){return akses.grafik_tb_statushiv;}
    public static boolean getgrafik_tb_skoringanak(){return akses.grafik_tb_skoringanak;}
    public static boolean getgrafik_tb_konfirmasiskoring5(){return akses.grafik_tb_konfirmasiskoring5;}
    public static boolean getgrafik_tb_konfirmasiskoring6(){return akses.grafik_tb_konfirmasiskoring6;}
    public static boolean getgrafik_tb_sumberobat(){return akses.grafik_tb_sumberobat;}
    public static boolean getgrafik_tb_hasilakhirpengobatan(){return akses.grafik_tb_hasilakhirpengobatan;}
    public static boolean getgrafik_tb_hasilteshiv(){return akses.grafik_tb_hasilteshiv;}
    public static boolean getkadaluarsa_batch(){return akses.kadaluarsa_batch;}
    public static boolean getsisa_stok(){return akses.sisa_stok;}
    public static boolean getobat_per_resep(){return akses.obat_per_resep;}
    public static boolean getpemakaian_air_pdam(){return akses.pemakaian_air_pdam;}
    public static boolean getlimbah_b3_medis(){return akses.limbah_b3_medis;}
    public static boolean getgrafik_air_pdam_pertanggal(){return akses.grafik_air_pdam_pertanggal;}
    public static boolean getgrafik_air_pdam_perbulan(){return akses.grafik_air_pdam_perbulan;}
    public static boolean getgrafik_limbahb3_pertanggal(){return akses.grafik_limbahb3_pertanggal;}
    public static boolean getgrafik_limbahb3_perbulan(){return akses.grafik_limbahb3_perbulan;}
    public static boolean getlimbah_domestik(){return akses.limbah_domestik;}
    public static boolean getgrafik_limbahdomestik_pertanggal(){return akses.grafik_limbahdomestik_pertanggal;}
    public static boolean getgrafik_limbahdomestik_perbulan(){return akses.grafik_limbahdomestik_perbulan;}
    public static boolean getmutu_air_limbah(){return akses.mutu_air_limbah;}
    public static boolean getpest_control(){return akses.pest_control;}
    public static boolean getruang_perpustakaan(){return akses.ruang_perpustakaan;}
    public static boolean getkategori_perpustakaan(){return akses.kategori_perpustakaan;}
    public static boolean getjenis_perpustakaan(){return akses.jenis_perpustakaan;}
    public static boolean getpengarang_perpustakaan(){return akses.pengarang_perpustakaan;}
    public static boolean getpenerbit_perpustakaan(){return akses.penerbit_perpustakaan;}
    public static boolean getkoleksi_perpustakaan(){return akses.koleksi_perpustakaan;}
    public static boolean getinventaris_perpustakaan(){return akses.inventaris_perpustakaan;}
    public static boolean getset_peminjaman_perpustakaan(){return akses.set_peminjaman_perpustakaan;}
    public static boolean getdenda_perpustakaan(){return akses.denda_perpustakaan;}
    public static boolean getanggota_perpustakaan(){return akses.anggota_perpustakaan;}
    public static boolean getpeminjaman_perpustakaan(){return akses.peminjaman_perpustakaan;}
    public static boolean getbayar_denda_perpustakaan(){return akses.bayar_denda_perpustakaan;}
    public static boolean getebook_perpustakaan(){return akses.ebook_perpustakaan;}
    public static boolean getjenis_cidera_k3rs(){return akses.jenis_cidera_k3rs;}
    public static boolean getpenyebab_k3rs(){return akses.penyebab_k3rs;}
    public static boolean getjenis_luka_k3rs(){return akses.jenis_luka_k3rs;}
    public static boolean getlokasi_kejadian_k3rs(){return akses.lokasi_kejadian_k3rs;}
    public static boolean getdampak_cidera_k3rs(){return akses.dampak_cidera_k3rs;}
    public static boolean getjenis_pekerjaan_k3rs(){return akses.jenis_pekerjaan_k3rs;}
    public static boolean getbagian_tubuh_k3rs(){return akses.bagian_tubuh_k3rs;}
    public static boolean getperistiwa_k3rs(){return akses.peristiwa_k3rs;}
    public static boolean getgrafik_k3_pertahun(){return akses.grafik_k3_pertahun;}
    public static boolean getgrafik_k3_perbulan(){return akses.grafik_k3_perbulan;}
    public static boolean getgrafik_k3_pertanggal(){return akses.grafik_k3_pertanggal;}
    public static boolean getgrafik_k3_perjeniscidera(){return akses.grafik_k3_perjeniscidera;}
    public static boolean getgrafik_k3_perpenyebab(){return akses.grafik_k3_perpenyebab;}
    public static boolean getgrafik_k3_perjenisluka(){return akses.grafik_k3_perjenisluka;}
    public static boolean getgrafik_k3_lokasikejadian(){return akses.grafik_k3_lokasikejadian;}
    public static boolean getgrafik_k3_dampakcidera(){return akses.grafik_k3_dampakcidera;}
    public static boolean getgrafik_k3_perjenispekerjaan(){return akses.grafik_k3_perjenispekerjaan;}
    public static boolean getgrafik_k3_perbagiantubuh(){return akses.grafik_k3_perbagiantubuh;}
    public static boolean getjenis_cidera_k3rstahun(){return akses.jenis_cidera_k3rstahun;}
    public static boolean getpenyebab_k3rstahun(){return akses.penyebab_k3rstahun;}
    public static boolean getjenis_luka_k3rstahun(){return akses.jenis_luka_k3rstahun;}
    public static boolean getlokasi_kejadian_k3rstahun(){return akses.lokasi_kejadian_k3rstahun;}
    public static boolean getdampak_cidera_k3rstahun(){return akses.dampak_cidera_k3rstahun;}
    public static boolean getjenis_pekerjaan_k3rstahun(){return akses.jenis_pekerjaan_k3rstahun;}
    public static boolean getbagian_tubuh_k3rstahun(){return akses.bagian_tubuh_k3rstahun;}
    public static boolean getsekrining_rawat_jalan(){return akses.sekrining_rawat_jalan;}
    public static boolean getbpjs_histori_pelayanan(){return akses.bpjs_histori_pelayanan;}
    public static boolean getrekap_mutasi_berkas(){return akses.rekap_mutasi_berkas;}
    public static boolean getskrining_ralan_pernapasan_pertahun(){return akses.skrining_ralan_pernapasan_pertahun;}
    public static boolean getpengajuan_barang_medis(){return akses.pengajuan_barang_medis;}
    public static boolean getpengajuan_barang_nonmedis(){return akses.pengajuan_barang_nonmedis;}
    public static boolean getgrafik_kunjungan_ranapbulan(){return akses.grafik_kunjungan_ranapbulan;}
    public static boolean getgrafik_kunjungan_ranaptanggal(){return akses.grafik_kunjungan_ranaptanggal;}
    public static boolean getgrafik_kunjungan_ranap_peruang(){return akses.grafik_kunjungan_ranap_peruang;}
    public static boolean getkunjungan_bangsal_pertahun(){return akses.kunjungan_bangsal_pertahun;}
    public static boolean getgrafik_jenjang_jabatanpegawai(){return akses.grafik_jenjang_jabatanpegawai;}
    public static boolean getgrafik_bidangpegawai(){return akses.grafik_bidangpegawai;}
    public static boolean getgrafik_departemenpegawai(){return akses.grafik_departemenpegawai;}
    public static boolean getgrafik_pendidikanpegawai(){return akses.grafik_pendidikanpegawai;}
    public static boolean getgrafik_sttswppegawai(){return akses.grafik_sttswppegawai;}
    public static boolean getgrafik_sttskerjapegawai(){return akses.grafik_sttskerjapegawai;}
    public static boolean getgrafik_sttspulangranap(){return akses.grafik_sttspulangranap;}
    public static boolean getkip_pasien_ranap(){return akses.kip_pasien_ranap;}
    public static boolean getkip_pasien_ralan(){return akses.kip_pasien_ralan;}
    public static boolean getbpjs_mapping_dokterdpjp(){return akses.bpjs_mapping_dokterdpjp;}
    public static boolean getdata_triase_igd(){return akses.data_triase_igd;}
    public static boolean getmaster_triase_skala1(){return akses.master_triase_skala1;}
    public static boolean getmaster_triase_skala2(){return akses.master_triase_skala2;}
    public static boolean getmaster_triase_skala3(){return akses.master_triase_skala3;}
    public static boolean getmaster_triase_skala4(){return akses.master_triase_skala4;}
    public static boolean getmaster_triase_skala5(){return akses.master_triase_skala5;}
    public static boolean getmaster_triase_pemeriksaan(){return akses.master_triase_pemeriksaan;}
    public static boolean getmaster_triase_macamkasus(){return akses.master_triase_macamkasus;}
    public static boolean getrekap_permintaan_diet(){return akses.rekap_permintaan_diet;}
    public static boolean getdaftar_pasien_ranap(){return akses.daftar_pasien_ranap;}
    public static boolean getdaftar_pasien_ranaptni(){return akses.daftar_pasien_ranaptni;}
    public static boolean getpengajuan_asetinventaris(){return akses.pengajuan_asetinventaris;}
    public static boolean getitem_apotek_jenis(){return akses.item_apotek_jenis;}
    public static boolean getitem_apotek_kategori(){return akses.item_apotek_kategori;}
    public static boolean getitem_apotek_golongan(){return akses.item_apotek_golongan;}
    public static boolean getitem_apotek_industrifarmasi(){return akses.item_apotek_industrifarmasi;}
    public static boolean getobat10_terbanyak_poli(){return akses.obat10_terbanyak_poli;}
    public static boolean getgrafik_pengajuan_aset_urgensi(){return akses.grafik_pengajuan_aset_urgensi;}
    public static boolean getgrafik_pengajuan_aset_status(){return akses.grafik_pengajuan_aset_status;}
    public static boolean getgrafik_pengajuan_aset_departemen(){return akses.grafik_pengajuan_aset_departemen;}
    public static boolean getrekap_pengajuan_aset_departemen(){return akses.rekap_pengajuan_aset_departemen;}
    public static boolean getgrafik_kelompok_jabatanpegawai(){return akses.grafik_kelompok_jabatanpegawai;}
    public static boolean getgrafik_resiko_kerjapegawai(){return akses.grafik_resiko_kerjapegawai;}
    public static boolean getgrafik_emergency_indexpegawai(){return akses.grafik_emergency_indexpegawai;}
    public static boolean getgrafik_inventaris_ruang(){return akses.grafik_inventaris_ruang;}
    public static boolean getharian_HAIs2(){return akses.harian_HAIs2;}
    public static boolean getgrafik_inventaris_jenis(){return akses.grafik_inventaris_jenis;}
    public static boolean getdata_resume_pasien(){return akses.data_resume_pasien;}
    public static boolean getperkiraan_biaya_ranap(){return akses.perkiraan_biaya_ranap;}
    public static boolean getrekap_obat_poli(){return akses.rekap_obat_poli;}
    public static boolean getrekap_obat_pasien(){return akses.rekap_obat_pasien;}
    public static boolean getgrafik_HAIs_pasienbangsal(){return akses.grafik_HAIs_pasienbangsal;}
    public static boolean getgrafik_HAIs_pasienbulan(){return akses.grafik_HAIs_pasienbulan;}
    public static boolean getpermintaan_perbaikan_inventaris(){return akses.permintaan_perbaikan_inventaris;}
    public static boolean getgrafik_HAIs_laju_vap(){return akses.grafik_HAIs_laju_vap;} 
    public static boolean getgrafik_HAIs_laju_iad(){return akses.grafik_HAIs_laju_iad;}
    public static boolean getgrafik_HAIs_laju_pleb(){return akses.grafik_HAIs_laju_pleb;}
    public static boolean getgrafik_HAIs_laju_isk(){return akses.grafik_HAIs_laju_isk;}
    public static boolean getgrafik_HAIs_laju_ilo(){return akses.grafik_HAIs_laju_ilo;}
    public static boolean getgrafik_HAIs_laju_hap(){return akses.grafik_HAIs_laju_hap;}
    public static boolean getinhealth_mapping_poli(){return akses.inhealth_mapping_poli;}
    public static boolean getinhealth_mapping_dokter(){return akses.inhealth_mapping_dokter;}
    public static boolean getinhealth_mapping_tindakan_ralan(){return akses.inhealth_mapping_tindakan_ralan;}
    public static boolean getinhealth_mapping_tindakan_ranap(){return akses.inhealth_mapping_tindakan_ranap;}
    public static boolean getinhealth_mapping_tindakan_radiologi(){return akses.inhealth_mapping_tindakan_radiologi;}
    public static boolean getinhealth_mapping_tindakan_laborat(){return akses.inhealth_mapping_tindakan_laborat;}
    public static boolean getinhealth_mapping_tindakan_operasi(){return akses.inhealth_mapping_tindakan_operasi;}
    public static boolean gethibah_obat_bhp(){return akses.hibah_obat_bhp;}
    public static boolean getasal_hibah(){return akses.asal_hibah;}
    public static boolean getasuhan_gizi(){return akses.asuhan_gizi;}
    public static boolean getinhealth_kirim_tagihan(){return akses.inhealth_kirim_tagihan;}
    public static boolean getsirkulasi_obat4(){return akses.sirkulasi_obat4;}
    public static boolean getsirkulasi_obat5(){return akses.sirkulasi_obat5;}
    public static boolean getsirkulasi_non_medis2(){return akses.sirkulasi_non_medis2;}
    public static boolean getmonitoring_asuhan_gizi(){return akses.monitoring_asuhan_gizi;}
    public static boolean getpenerimaan_obat_perbulan(){return akses.penerimaan_obat_perbulan;}
    public static boolean getrekap_kunjungan(){return akses.rekap_kunjungan;}
    public static boolean getsurat_sakit(){return akses.surat_sakit;}
    public static boolean getpenilaian_awal_keperawatan_ralan(){return akses.penilaian_awal_keperawatan_ralan;}
    public static boolean getpermintaan_diet(){return akses.permintaan_diet;}
    public static boolean getmaster_masalah_keperawatan(){return akses.master_masalah_keperawatan;}
    public static boolean getpengajuan_cuti(){return akses.pengajuan_cuti;}
    public static boolean getkedatangan_pasien(){return akses.kedatangan_pasien;}
    public static boolean getutd_pendonor(){return akses.utd_pendonor;}
    public static boolean gettoko_suplier(){return akses.toko_suplier;}
    public static boolean gettoko_jenis(){return akses.toko_jenis;}
    public static boolean gettoko_set_harga(){return akses.toko_set_harga;}
    public static boolean gettoko_barang(){return akses.toko_barang;}
    public static boolean getpenagihan_piutang_pasien(){return akses.penagihan_piutang_pasien;}
    public static boolean getakun_penagihan_piutang(){return akses.akun_penagihan_piutang;}
    public static boolean getstok_opname_toko(){return akses.stok_opname_toko;}
    public static boolean gettoko_riwayat_barang(){return akses.toko_riwayat_barang;}
    public static boolean gettoko_surat_pemesanan(){return akses.toko_surat_pemesanan;}
    public static boolean gettoko_pengajuan_barang(){return akses.toko_pengajuan_barang;}
    public static boolean gettoko_penerimaan_barang(){return akses.toko_penerimaan_barang;}
    public static boolean gettoko_pengadaan_barang(){return akses.toko_pengadaan_barang;}
    public static boolean gettoko_hutang(){return akses.toko_hutang;}
    public static boolean gettoko_bayar_pemesanan(){return akses.toko_bayar_pemesanan;}
    public static boolean gettoko_member(){return akses.toko_member;}
    public static boolean gettoko_penjualan(){return akses.toko_penjualan;}
    public static boolean getregistrasi_poli_per_tanggal(){return akses.registrasi_poli_per_tanggal;}
    public static boolean gettoko_piutang(){return akses.toko_piutang;}
    public static boolean gettoko_retur_beli(){return akses.toko_retur_beli;}
    public static boolean getipsrs_returbeli(){return akses.ipsrs_returbeli;}
    public static boolean getipsrs_riwayat_barang(){return akses.ipsrs_riwayat_barang;}
    public static boolean getpasien_corona(){return akses.pasien_corona;}
    public static boolean gettoko_pendapatan_harian(){return akses.toko_pendapatan_harian;}
    public static boolean getdiagnosa_pasien_corona(){return akses.diagnosa_pasien_corona;}
    public static boolean getperawatan_pasien_corona(){return akses.perawatan_pasien_corona;}
    public static boolean getpenilaian_awal_keperawatan_gigi(){return akses.penilaian_awal_keperawatan_gigi;}
    public static boolean getmaster_masalah_keperawatan_gigi(){return akses.master_masalah_keperawatan_gigi;}
    public static boolean gettoko_bayar_piutang(){return akses.toko_bayar_piutang;}
    public static boolean gettoko_piutang_harian(){return akses.toko_piutang_harian;}
    public static boolean gettoko_penjualan_harian(){return akses.toko_penjualan_harian;}
    public static boolean getdeteksi_corona(){return akses.deteksi_corona;}
    public static boolean getpenilaian_awal_keperawatan_kebidanan(){return akses.penilaian_awal_keperawatan_kebidanan;}
    public static boolean getpengumuman_epasien(){return akses.pengumuman_epasien;}
    public static boolean getsurat_hamil(){return akses.surat_hamil;}
    public static boolean getset_tarif_online(){return akses.set_tarif_online;}
    public static boolean getbooking_periksa(){return akses.booking_periksa;}
    public static boolean gettoko_sirkulasi(){return akses.toko_sirkulasi;}
    public static boolean gettoko_retur_jual(){return akses.toko_retur_jual;}
    public static boolean gettoko_retur_piutang(){return akses.toko_retur_piutang;}
    public static boolean gettoko_sirkulasi2(){return akses.toko_sirkulasi2;}
    public static boolean gettoko_keuntungan_barang(){return akses.toko_keuntungan_barang;}
    public static boolean getzis_pengeluaran_penerima_dankes(){return akses.zis_pengeluaran_penerima_dankes;}
    public static boolean getzis_penghasilan_penerima_dankes(){return akses.zis_penghasilan_penerima_dankes;}
    public static boolean getzis_ukuran_rumah_penerima_dankes(){return akses.zis_ukuran_rumah_penerima_dankes;}
    public static boolean getzis_dinding_rumah_penerima_dankes(){return akses.zis_dinding_rumah_penerima_dankes;}
    public static boolean getzis_lantai_rumah_penerima_dankes(){return akses.zis_lantai_rumah_penerima_dankes;}
    public static boolean getzis_atap_rumah_penerima_dankes(){return akses.zis_atap_rumah_penerima_dankes;}
    public static boolean getzis_kepemilikan_rumah_penerima_dankes(){return akses.zis_kepemilikan_rumah_penerima_dankes;}
    public static boolean getzis_kamar_mandi_penerima_dankes(){return akses.zis_kamar_mandi_penerima_dankes;}
    public static boolean getzis_dapur_rumah_penerima_dankes(){return akses.zis_dapur_rumah_penerima_dankes;}
    public static boolean getzis_kursi_rumah_penerima_dankes(){return akses.zis_kursi_rumah_penerima_dankes;}
    public static boolean getzis_kategori_phbs_penerima_dankes(){return akses.zis_kategori_phbs_penerima_dankes;}
    public static boolean getzis_elektronik_penerima_dankes(){return akses.zis_elektronik_penerima_dankes;}
    public static boolean getzis_ternak_penerima_dankes(){return akses.zis_ternak_penerima_dankes;}
    public static boolean getzis_jenis_simpanan_penerima_dankes(){return akses.zis_jenis_simpanan_penerima_dankes;}
    public static boolean getpenilaian_awal_keperawatan_anak(){return akses.penilaian_awal_keperawatan_anak;}
    public static boolean getzis_kategori_asnaf_penerima_dankes(){return akses.zis_kategori_asnaf_penerima_dankes;}
    public static boolean getmaster_masalah_keperawatan_anak(){return akses.master_masalah_keperawatan_anak;}
    public static boolean getmaster_imunisasi(){return akses.master_imunisasi;}
    public static boolean getzis_patologis_penerima_dankes(){return akses.zis_patologis_penerima_dankes;}
    public static boolean getpcare_cek_kartu(){return akses.pcare_cek_kartu;}
    public static boolean getsurat_bebas_narkoba(){return akses.surat_bebas_narkoba;}
    public static boolean getsurat_keterangan_covid(){return akses.surat_keterangan_covid;}
    public static boolean getpemakaian_air_tanah(){return akses.pemakaian_air_tanah;}
    public static boolean getgrafik_air_tanah_pertanggal(){return akses.grafik_air_tanah_pertanggal;}
    public static boolean getgrafik_air_tanah_perbulan(){return akses.grafik_air_tanah_perbulan;}
    public static boolean getlama_pelayanan_poli(){return akses.lama_pelayanan_poli;}
    public static boolean gethemodialisa(){return akses.hemodialisa;}
    public static boolean getgrafik_harian_hemodialisa(){return akses.grafik_harian_hemodialisa;}
    public static boolean getgrafik_bulanan_hemodialisa(){return akses.grafik_bulanan_hemodialisa;}
    public static boolean getgrafik_tahunan_hemodialisa(){return akses.grafik_tahunan_hemodialisa;}
    public static boolean getgrafik_bulanan_meninggal(){return akses.grafik_bulanan_meninggal;}
    public static boolean getlaporan_tahunan_irj(){return akses.laporan_tahunan_irj;}
    public static boolean getperbaikan_inventaris(){return akses.perbaikan_inventaris;}
    public static boolean getsurat_cuti_hamil(){return akses.surat_cuti_hamil;}
    public static boolean getpermintaan_stok_obat_pasien(){return akses.permintaan_stok_obat_pasien;}
    public static boolean getpemeliharaan_inventaris(){return akses.pemeliharaan_inventaris;}
    public static boolean getklasifikasi_pasien_ranap(){return akses.klasifikasi_pasien_ranap;}
    public static boolean getbulanan_klasifikasi_pasien_ranap(){return akses.bulanan_klasifikasi_pasien_ranap;}
    public static boolean getharian_klasifikasi_pasien_ranap(){return akses.harian_klasifikasi_pasien_ranap;}
    public static boolean getklasifikasi_pasien_perbangsal(){return akses.klasifikasi_pasien_perbangsal;}
    public static boolean getsoap_perawatan(){return akses.soap_perawatan;}
    public static boolean getklaim_rawat_jalan(){return akses.klaim_rawat_jalan;}
    public static boolean getskrining_gizi(){return akses.skrining_gizi;}
    public static boolean getlama_penyiapan_rm(){return akses.lama_penyiapan_rm;}
    public static boolean getdosis_radiologi(){return akses.dosis_radiologi;}
    public static boolean getdemografi_umur_kunjungan(){return akses.demografi_umur_kunjungan;}
    public static boolean getjam_diet_pasien(){return akses.jam_diet_pasien;}
    public static boolean getrvu_bpjs(){return akses.rvu_bpjs;}
    public static boolean getverifikasi_penerimaan_farmasi(){return akses.verifikasi_penerimaan_farmasi;}
    public static boolean getverifikasi_penerimaan_logistik(){return akses.verifikasi_penerimaan_logistik;}
    public static boolean getpemeriksaan_lab_pa(){return akses.pemeriksaan_lab_pa;}
    public static boolean getringkasan_pengajuan_obat(){return akses.ringkasan_pengajuan_obat;}
    public static boolean getringkasan_pemesanan_obat(){return akses.ringkasan_pemesanan_obat;}
    public static boolean getringkasan_pengadaan_obat(){return akses.ringkasan_pengadaan_obat;}
    public static boolean getringkasan_penerimaan_obat(){return akses.ringkasan_penerimaan_obat;}
    public static boolean getringkasan_hibah_obat(){return akses.ringkasan_hibah_obat;}
    public static boolean getringkasan_penjualan_obat(){return akses.ringkasan_penjualan_obat;}
    public static boolean getringkasan_beri_obat(){return akses.ringkasan_beri_obat;}
    public static boolean getringkasan_piutang_obat(){return akses.ringkasan_piutang_obat;}
    public static boolean getringkasan_stok_keluar_obat(){return akses.ringkasan_stok_keluar_obat;}
    public static boolean getringkasan_retur_suplier_obat(){return akses.ringkasan_retur_suplier_obat;}
    public static boolean getringkasan_retur_pembeli_obat(){return akses.ringkasan_retur_pembeli_obat;}
    public static boolean getpenilaian_awal_keperawatan_ranapkebidanan(){return akses.penilaian_awal_keperawatan_ranapkebidanan;}
    public static boolean getringkasan_pengajuan_nonmedis(){return akses.ringkasan_pengajuan_nonmedis;}
    public static boolean getringkasan_pemesanan_nonmedis(){return akses.ringkasan_pemesanan_nonmedis;}
    public static boolean getringkasan_pengadaan_nonmedis(){return akses.ringkasan_pengadaan_nonmedis;}
    public static boolean getringkasan_penerimaan_nonmedis(){return akses.ringkasan_penerimaan_nonmedis;}
    public static boolean getringkasan_stokkeluar_nonmedis(){return akses.ringkasan_stokkeluar_nonmedis;}
    public static boolean getringkasan_returbeli_nonmedis(){return akses.ringkasan_returbeli_nonmedis;}
    public static boolean getomset_penerimaan(){return akses.omset_penerimaan;}
    public static boolean getvalidasi_penagihan_piutang(){return akses.validasi_penagihan_piutang;}
    public static boolean getpermintaan_ranap(){return akses.permintaan_ranap;}
    public static boolean getbpjs_diagnosa_prb(){return akses.bpjs_diagnosa_prb;}
    public static boolean getbpjs_obat_prb(){return akses.bpjs_obat_prb;}
    public static boolean getbpjs_surat_kontrol(){return akses.bpjs_surat_kontrol;}
    public static boolean getpenggunaan_bhp_ok(){return akses.penggunaan_bhp_ok;}
    public static boolean getsurat_keterangan_rawat_inap(){return akses.surat_keterangan_rawat_inap;}
    public static boolean getsurat_keterangan_sehat(){return akses.surat_keterangan_sehat;}
    public static boolean getpendapatan_per_carabayar(){return akses.pendapatan_per_carabayar;}
    public static boolean getakun_host_to_host_bank_jateng(){return akses.akun_host_to_host_bank_jateng;}
    public static boolean getpembayaran_bank_jateng(){return akses.pembayaran_bank_jateng;}
    public static boolean getbpjs_surat_pri(){return akses.bpjs_surat_pri;}
    public static boolean getringkasan_tindakan(){return akses.ringkasan_tindakan;}
    public static boolean getlama_pelayanan_pasien(){return akses.lama_pelayanan_pasien;}
    public static boolean getsurat_sakit_pihak_2(){return akses.surat_sakit_pihak_2;}
    public static boolean gettagihan_hutang_obat(){return akses.tagihan_hutang_obat;}
    public static boolean getreferensi_mobilejkn_bpjs(){return akses.referensi_mobilejkn_bpjs;}
    public static boolean getbatal_pendaftaran_mobilejkn_bpjs(){return akses.batal_pendaftaran_mobilejkn_bpjs;}
    public static boolean getlama_operasi(){return akses.lama_operasi;}
    public static boolean getgrafik_inventaris_kategori(){return akses.grafik_inventaris_kategori;}
    public static boolean getgrafik_inventaris_merk(){return akses.grafik_inventaris_merk;}
    public static boolean getgrafik_inventaris_produsen(){return akses.grafik_inventaris_produsen;}
    public static boolean getpengembalian_deposit_pasien(){return akses.pengembalian_deposit_pasien;}
    public static boolean getvalidasi_tagihan_hutang_obat(){return akses.validasi_tagihan_hutang_obat;}
    public static boolean getpiutang_obat_belum_lunas(){return akses.piutang_obat_belum_lunas;}
    public static boolean getintegrasi_briapi(){return akses.integrasi_briapi;}
    public static boolean getpengadaan_aset_inventaris(){return akses.pengadaan_aset_inventaris;}
    public static boolean getakun_aset_inventaris(){return akses.akun_aset_inventaris;}
    public static boolean getsuplier_inventaris(){return akses.suplier_inventaris;}
    public static boolean getpenerimaan_aset_inventaris(){return akses.penerimaan_aset_inventaris;}
    public static boolean getbayar_pemesanan_iventaris(){return akses.bayar_pemesanan_iventaris;}
    public static boolean gethutang_aset_inventaris(){return akses.hutang_aset_inventaris;}
    public static boolean gethibah_aset_inventaris(){return akses.hibah_aset_inventaris;}
    public static boolean gettitip_faktur_non_medis(){return akses.titip_faktur_non_medis;}
    public static boolean getvalidasi_tagihan_non_medis(){return akses.validasi_tagihan_non_medis;}
    public static boolean gettitip_faktur_aset(){return akses.titip_faktur_aset;}
    public static boolean getvalidasi_tagihan_aset(){return akses.validasi_tagihan_aset;}
    public static boolean gethibah_non_medis(){return akses.hibah_non_medis;}
    public static boolean getpcare_alasan_tacc(){return akses.pcare_alasan_tacc;}
    public static boolean getresep_luar(){return akses.resep_luar;}
    public static boolean getsurat_bebas_tbc(){return akses.surat_bebas_tbc;}
    public static boolean getsurat_buta_warna(){return akses.surat_buta_warna;}
    public static boolean getsurat_bebas_tato(){return akses.surat_bebas_tato;}
    public static boolean getsurat_kewaspadaan_kesehatan(){return akses.surat_kewaspadaan_kesehatan;}
    public static boolean getgrafik_porsidiet_pertanggal(){return akses.grafik_porsidiet_pertanggal;}
    public static boolean getgrafik_porsidiet_perbulan(){return akses.grafik_porsidiet_perbulan;}
    public static boolean getgrafik_porsidiet_pertahun(){return akses.grafik_porsidiet_pertahun;}
    public static boolean getgrafik_porsidiet_perbangsal(){return akses.grafik_porsidiet_perbangsal;}
    public static boolean getpenilaian_awal_medis_ralan(){return akses.penilaian_awal_medis_ralan;}
    public static boolean getmaster_masalah_keperawatan_mata(){return akses.master_masalah_keperawatan_mata;}
    public static boolean getpenilaian_awal_keperawatan_mata(){return akses.penilaian_awal_keperawatan_mata;}
    public static boolean getpenilaian_awal_medis_ranap(){return akses.penilaian_awal_medis_ranap;}
    public static boolean getpenilaian_awal_medis_ranap_kebidanan(){return akses.penilaian_awal_medis_ranap_kebidanan;}
    public static boolean getpenilaian_awal_medis_ralan_kebidanan(){return akses.penilaian_awal_medis_ralan_kebidanan;}
    public static boolean getpenilaian_awal_medis_igd(){return akses.penilaian_awal_medis_igd;}
    public static boolean getpenilaian_awal_medis_ralan_anak(){return akses.penilaian_awal_medis_ralan_anak;}
    public static boolean getbpjs_referensi_poli_hfis(){return akses.bpjs_referensi_poli_hfis;}
    public static boolean getbpjs_referensi_dokter_hfis(){return akses.bpjs_referensi_dokter_hfis;}
    public static boolean getbpjs_referensi_jadwal_hfis(){return akses.bpjs_referensi_jadwal_hfis;}
    public static boolean getpenilaian_fisioterapi(){return akses.penilaian_fisioterapi;}
    public static boolean getbpjs_program_prb(){return akses.bpjs_program_prb;}
    public static boolean getbpjs_suplesi_jasaraharja(){return akses.bpjs_suplesi_jasaraharja;}
    public static boolean getbpjs_data_induk_kecelakaan(){return akses.bpjs_data_induk_kecelakaan;}
    public static boolean getbpjs_sep_internal(){return akses.bpjs_sep_internal;}
    public static boolean getbpjs_klaim_jasa_raharja(){return akses.bpjs_klaim_jasa_raharja;}
    public static boolean getbpjs_daftar_finger_print(){return akses.bpjs_daftar_finger_print;}
    public static boolean getbpjs_rujukan_khusus(){return akses.bpjs_rujukan_khusus;}
    public static boolean getpemeliharaan_gedung(){return akses.pemeliharaan_gedung;}
    public static boolean getgrafik_perbaikan_inventaris_pertanggal(){return akses.grafik_perbaikan_inventaris_pertanggal;}
    public static boolean getgrafik_perbaikan_inventaris_perbulan(){return akses.grafik_perbaikan_inventaris_perbulan;}
    public static boolean getgrafik_perbaikan_inventaris_pertahun(){return akses.grafik_perbaikan_inventaris_pertahun;}
    public static boolean getgrafik_perbaikan_inventaris_perpelaksana_status(){return akses.grafik_perbaikan_inventaris_perpelaksana_status;}
    public static boolean getpenilaian_mcu(){return akses.penilaian_mcu;}
    public static boolean getpeminjam_piutang(){return akses.peminjam_piutang;}
    public static boolean getpiutang_lainlain(){return akses.piutang_lainlain;}
    public static boolean getcara_bayar(){return akses.cara_bayar;}
    public static boolean getaudit_kepatuhan_apd(){return akses.audit_kepatuhan_apd;}
    public static boolean getbpjs_task_id(){return akses.bpjs_task_id;}
    public static boolean getbayar_piutang_lain(){return akses.bayar_piutang_lain;}
    public static boolean getpembayaran_akun_bayar4(){return akses.pembayaran_akun_bayar4;}
    public static boolean getstok_akhir_farmasi_pertanggal(){return akses.stok_akhir_farmasi_pertanggal;}
    public static boolean getriwayat_kamar_pasien(){return akses.riwayat_kamar_pasien;}
    public static boolean getuji_fungsi_kfr(){return akses.uji_fungsi_kfr;}
    public static boolean gethapus_berkas_digital_perawatan(){return akses.hapus_berkas_digital_perawatan;}
    public static boolean getkategori_pengeluaran_harian(){return akses.kategori_pengeluaran_harian;}
    public static boolean getkategori_pemasukan_lain(){return akses.kategori_pemasukan_lain;}
    public static boolean getpembayaran_akun_bayar5(){return akses.pembayaran_akun_bayar5;}
    public static boolean getruang_ok(){return akses.ruang_ok;}
    public static boolean getjasa_tindakan_pasien(){return akses.jasa_tindakan_pasien;}
    public static boolean gettelaah_resep(){return akses.telaah_resep;}
    public static boolean getpermintaan_resep_pulang(){return akses.permintaan_resep_pulang;}
    public static boolean getrekap_jm_dokter(){return akses.rekap_jm_dokter;}
    public static boolean getstatus_data_rm(){return akses.status_data_rm;}
    public static boolean getubah_petugas_lab_pk(){return akses.ubah_petugas_lab_pk;}
    public static boolean getubah_petugas_lab_pa(){return akses.ubah_petugas_lab_pa;}
    public static boolean getubah_petugas_radiologi(){return akses.ubah_petugas_radiologi;}
    public static boolean getgabung_norawat(){return akses.gabung_norawat;}
    public static boolean getgabung_rm(){return akses.gabung_rm;}
    public static boolean getringkasan_biaya_obat_pasien_pertanggal(){return akses.ringkasan_biaya_obat_pasien_pertanggal;}
    public static boolean getmaster_masalah_keperawatan_igd(){return akses.master_masalah_keperawatan_igd;}
    public static boolean getpenilaian_awal_keperawatan_igd(){return akses.penilaian_awal_keperawatan_igd;}
    public static boolean getbpjs_referensi_dpho_apotek(){return akses.bpjs_referensi_dpho_apotek;}
    public static boolean getbpjs_referensi_poli_apotek(){return akses.bpjs_referensi_poli_apotek;}
    public static boolean getbayar_jm_dokter(){return akses.bayar_jm_dokter;}
    public static boolean getbpjs_referensi_faskes_apotek(){return akses.bpjs_referensi_faskes_apotek;}
    public static boolean getbpjs_referensi_spesialistik_apotek(){return akses.bpjs_referensi_spesialistik_apotek;}
    public static boolean getpembayaran_briva(){return akses.pembayaran_briva;}
    public static boolean getpenilaian_awal_keperawatan_ranap(){return akses.penilaian_awal_keperawatan_ranap;}
    public static boolean getnilai_penerimaan_vendor_farmasi_perbulan(){return akses.nilai_penerimaan_vendor_farmasi_perbulan;}
    public static boolean getakun_bayar_hutang(){return akses.akun_bayar_hutang;}
    public static boolean getmaster_rencana_keperawatan(){return akses.master_rencana_keperawatan;}
    public static boolean getlaporan_tahunan_igd(){return akses.laporan_tahunan_igd;}
    public static boolean getobat_bhp_tidakbergerak(){return akses.obat_bhp_tidakbergerak;}
    public static boolean getringkasan_hutang_vendor_farmasi(){return akses.ringkasan_hutang_vendor_farmasi;}
    public static boolean getnilai_penerimaan_vendor_nonmedis_perbulan(){return akses.nilai_penerimaan_vendor_nonmedis_perbulan;}
    public static boolean getringkasan_hutang_vendor_nonmedis(){return akses.ringkasan_hutang_vendor_nonmedis;}
    public static boolean getanggota_polri_dirawat(){return akses.anggota_polri_dirawat;}
    public static boolean getdaftar_pasien_ranap_polri(){return akses.daftar_pasien_ranap_polri;}
    public static boolean getsoap_ralan_polri(){return akses.soap_ralan_polri;}
    public static boolean getsoap_ranap_polri(){return akses.soap_ranap_polri;}
    public static boolean getlaporan_penyakit_polri(){return akses.laporan_penyakit_polri;}
    public static boolean getmaster_rencana_keperawatan_anak(){return akses.master_rencana_keperawatan_anak;}
    public static boolean getjumlah_pengunjung_ralan_polri(){return akses.jumlah_pengunjung_ralan_polri;}
    public static boolean getcatatan_observasi_igd(){return akses.catatan_observasi_igd;}
    public static boolean getcatatan_observasi_ranap(){return akses.catatan_observasi_ranap;}
    public static boolean getcatatan_observasi_ranap_kebidanan(){return akses.catatan_observasi_ranap_kebidanan;}
    public static boolean getcatatan_observasi_ranap_postpartum(){return akses.catatan_observasi_ranap_postpartum;}
    public static boolean getpenilaian_awal_medis_ralan_tht(){return akses.penilaian_awal_medis_ralan_tht;}
    public static boolean getpenilaian_psikologi(){return akses.penilaian_psikologi;}
    public static boolean getaudit_cuci_tangan_medis(){return akses.audit_cuci_tangan_medis;}
    public static boolean getaudit_pembuangan_limbah(){return akses.audit_pembuangan_limbah;}
    public static boolean getruang_audit_kepatuhan(){return akses.ruang_audit_kepatuhan;}
    public static boolean getaudit_pembuangan_benda_tajam(){return akses.audit_pembuangan_benda_tajam;}
    public static boolean getaudit_penanganan_darah(){return akses.audit_penanganan_darah;}
    public static boolean getaudit_pengelolaan_linen_kotor(){return akses.audit_pengelolaan_linen_kotor;}
    public static boolean getaudit_penempatan_pasien(){return akses.audit_penempatan_pasien;}
    public static boolean getaudit_kamar_jenazah(){return akses.audit_kamar_jenazah;}
    public static boolean getaudit_bundle_iadp(){return akses.audit_bundle_iadp;}
    public static boolean getaudit_bundle_ido(){return akses.audit_bundle_ido;}
    public static boolean getaudit_fasilitas_kebersihan_tangan(){return akses.audit_fasilitas_kebersihan_tangan;}
    public static boolean getaudit_fasilitas_apd(){return akses.audit_fasilitas_apd;}
    public static boolean getaudit_pembuangan_limbah_cair_infeksius(){return akses.audit_pembuangan_limbah_cair_infeksius;}
    public static boolean getaudit_sterilisasi_alat(){return akses.audit_sterilisasi_alat;}
    public static boolean getpenilaian_awal_medis_ralan_psikiatri(){return akses.penilaian_awal_medis_ralan_psikiatri;}
    public static boolean getpersetujuan_penolakan_tindakan(){return akses.persetujuan_penolakan_tindakan;}
    public static boolean getaudit_bundle_isk(){return akses.audit_bundle_isk;}
    public static boolean getaudit_bundle_plabsi(){return akses.audit_bundle_plabsi;}
    public static boolean getaudit_bundle_vap(){return akses.audit_bundle_vap;}
    public static boolean getakun_host_to_host_bank_papua(){return akses.akun_host_to_host_bank_papua;}
    public static boolean getpembayaran_bank_papua(){return akses.pembayaran_bank_papua;}
    public static boolean getpenilaian_awal_medis_ralan_penyakit_dalam(){return akses.penilaian_awal_medis_ralan_penyakit_dalam;}
    public static boolean getpenilaian_awal_medis_ralan_mata(){return akses.penilaian_awal_medis_ralan_mata;}
    public static boolean getpenilaian_awal_medis_ralan_neurologi(){return akses.penilaian_awal_medis_ralan_neurologi;}
    public static boolean getsirkulasi_obat6(){return akses.sirkulasi_obat6;}
    public static boolean getpenilaian_awal_medis_ralan_orthopedi(){return akses.penilaian_awal_medis_ralan_orthopedi;}
    public static boolean getpenilaian_awal_medis_ralan_bedah(){return akses.penilaian_awal_medis_ralan_bedah;}
    public static boolean getintegrasi_khanza_health_services(){return akses.integrasi_khanza_health_services;}
    public static boolean getsoap_ralan_tni(){return akses.soap_ralan_tni;}
    public static boolean getsoap_ranap_tni(){return akses.soap_ranap_tni;}
    public static boolean getjumlah_pengunjung_ralan_tni(){return akses.jumlah_pengunjung_ralan_tni;}
    public static boolean getlaporan_penyakit_tni(){return akses.laporan_penyakit_tni;}
    public static boolean getcatatan_keperawatan_ranap(){return akses.catatan_keperawatan_ranap;}
    public static boolean getmaster_rencana_keperawatan_gigi(){return akses.master_rencana_keperawatan_gigi;}
    public static boolean getmaster_rencana_keperawatan_mata(){return akses.master_rencana_keperawatan_mata;}
    public static boolean getmaster_rencana_keperawatan_igd(){return akses.master_rencana_keperawatan_igd;}
    public static boolean getmaster_masalah_keperawatan_psikiatri(){return akses.master_masalah_keperawatan_psikiatri;}
    public static boolean getmaster_rencana_keperawatan_psikiatri(){return akses.master_rencana_keperawatan_psikiatri;}
    public static boolean getpenilaian_awal_keperawatan_psikiatri(){return akses.penilaian_awal_keperawatan_psikiatri;}
    public static boolean getpemantauan_pews_anak(){return akses.pemantauan_pews_anak;}
    public static boolean getsurat_pulang_atas_permintaan_sendiri(){return akses.surat_pulang_atas_permintaan_sendiri;}
    public static boolean gettemplate_hasil_radiologi(){return akses.template_hasil_radiologi;}
    public static boolean getlaporan_bulanan_irj(){return akses.laporan_bulanan_irj;}
    public static boolean gettemplate_pemeriksaan(){return akses.template_pemeriksaan;}
    public static boolean getpemeriksaan_lab_mb(){return akses.pemeriksaan_lab_mb;}
    public static boolean getubah_petugas_lab_mb(){return akses.ubah_petugas_lab_mb;}
    public static boolean getpenilaian_pre_operasi(){return akses.penilaian_pre_operasi;}
    public static boolean getpenilaian_pre_anestesi(){return akses.penilaian_pre_anestesi;}
    public static boolean getperencanaan_pemulangan(){return akses.perencanaan_pemulangan;}
    public static boolean getpenilaian_lanjutan_resiko_jatuh_dewasa(){return akses.penilaian_lanjutan_resiko_jatuh_dewasa;}
    public static boolean getpenilaian_lanjutan_resiko_jatuh_anak(){return akses.penilaian_lanjutan_resiko_jatuh_anak;}
    public static boolean getpenilaian_awal_medis_ralan_geriatri(){return akses.penilaian_awal_medis_ralan_geriatri;}
    public static boolean getpenilaian_tambahan_pasien_geriatri(){return akses.penilaian_tambahan_pasien_geriatri;}
    public static boolean getskrining_nutrisi_dewasa(){return akses.skrining_nutrisi_dewasa;}
    public static boolean getskrining_nutrisi_lansia(){return akses.skrining_nutrisi_lansia;}
    public static boolean gethasil_pemeriksaan_usg(){return akses.hasil_pemeriksaan_usg;}
    public static boolean getskrining_nutrisi_anak(){return akses.skrining_nutrisi_anak;}
    public static boolean getakun_host_to_host_bank_jabar(){return akses.akun_host_to_host_bank_jabar;}
    public static boolean getpembayaran_bank_jabar(){return akses.pembayaran_bank_jabar;}
    public static boolean getsurat_pernyataan_pasien_umum(){return akses.surat_pernyataan_pasien_umum;}
    public static boolean getkonseling_farmasi(){return akses.konseling_farmasi;}
    public static boolean getpelayanan_informasi_obat(){return akses.pelayanan_informasi_obat;}
    public static boolean getjawaban_pio_apoteker(){return akses.jawaban_pio_apoteker;}
    public static boolean getsurat_persetujuan_umum(){return akses.surat_persetujuan_umum;}
    public static boolean gettransfer_pasien_antar_ruang(){return akses.transfer_pasien_antar_ruang;}
    public static boolean getsatu_sehat_referensi_dokter(){return akses.satu_sehat_referensi_dokter;}
    public static boolean getsatu_sehat_referensi_pasien(){return akses.satu_sehat_referensi_pasien;}
    public static boolean getsatu_sehat_mapping_departemen(){return akses.satu_sehat_mapping_departemen;}
    public static boolean getsatu_sehat_mapping_lokasi(){return akses.satu_sehat_mapping_lokasi;}
    public static boolean getsatu_sehat_kirim_encounter(){return akses.satu_sehat_kirim_encounter;}
    public static boolean getcatatan_cek_gds(){return akses.catatan_cek_gds;}
    public static boolean getsatu_sehat_kirim_condition(){return akses.satu_sehat_kirim_condition;}
    public static boolean getchecklist_pre_operasi(){return akses.checklist_pre_operasi;}
    public static boolean getsatu_sehat_kirim_observationttv(){return akses.satu_sehat_kirim_observationttv;}
    public static boolean getsignin_sebelum_anestesi(){return akses.signin_sebelum_anestesi;}
    public static boolean getsatu_sehat_kirim_procedure(){return akses.satu_sehat_kirim_procedure;}
    public static boolean getoperasi_per_bulan(){return akses.operasi_per_bulan;}
    public static boolean gettimeout_sebelum_insisi(){return akses.timeout_sebelum_insisi;}
    public static boolean getsignout_sebelum_menutup_luka(){return akses.signout_sebelum_menutup_luka;}
    public static boolean getdapur_barang(){return akses.dapur_barang;}
    public static boolean getdapur_opname(){return akses.dapur_opname;}
    public static boolean getsatu_sehat_mapping_vaksin(){return akses.satu_sehat_mapping_vaksin;}
    public static boolean getdapur_suplier(){return akses.dapur_suplier;}
    public static boolean getsatu_sehat_kirim_Immunization(){return akses.satu_sehat_kirim_Immunization;}
    public static boolean getchecklist_post_operasi(){return akses.checklist_post_operasi;}
    public static boolean getdapur_pembelian(){return akses.dapur_pembelian;}
    public static boolean getdapur_stok_keluar(){return akses.dapur_stok_keluar;}
    public static boolean getdapur_riwayat_barang(){return akses.dapur_riwayat_barang;}
    public static boolean getpermintaan_dapur(){return akses.permintaan_dapur;}
    public static boolean getrekonsiliasi_obat(){return akses.rekonsiliasi_obat;}
    public static boolean getbiaya_pengadaan_dapur(){return akses.biaya_pengadaan_dapur;}
    public static boolean getrekap_pengadaan_dapur(){return akses.rekap_pengadaan_dapur;}
    public static boolean getkesling_limbah_b3medis_cair(){return akses.kesling_limbah_b3medis_cair;}
    public static boolean getgrafik_limbahb3cair_pertanggal(){return akses.grafik_limbahb3cair_pertanggal;}
    public static boolean getgrafik_limbahb3cair_perbulan(){return akses.grafik_limbahb3cair_perbulan;}
    public static boolean getrekap_biaya_registrasi(){return akses.rekap_biaya_registrasi;}
    public static boolean getkonfirmasi_rekonsiliasi_obat(){return akses.konfirmasi_rekonsiliasi_obat;}
    public static boolean getsatu_sehat_kirim_clinicalimpression(){return akses.satu_sehat_kirim_clinicalimpression;}
    public static boolean getpenilaian_pasien_terminal(){return akses.penilaian_pasien_terminal;}
    public static boolean getsurat_persetujuan_rawat_inap(){return akses.surat_persetujuan_rawat_inap;}
    public static boolean getmonitoring_reaksi_tranfusi(){return akses.monitoring_reaksi_tranfusi;}
    public static boolean getpenilaian_korban_kekerasan(){return akses.penilaian_korban_kekerasan;}
    public static boolean getpenilaian_lanjutan_resiko_jatuh_lansia(){return akses.penilaian_lanjutan_resiko_jatuh_lansia;}
    public static boolean getmpp_skrining(){return akses.mpp_skrining;}
    public static boolean getpenilaian_pasien_penyakit_menular(){return akses.penilaian_pasien_penyakit_menular;}
    public static boolean getedukasi_pasien_keluarga_rj(){return akses.edukasi_pasien_keluarga_rj;}
    public static boolean getpemantauan_pews_dewasa(){return akses.pemantauan_pews_dewasa;}
    public static boolean getpenilaian_tambahan_bunuh_diri(){return akses.penilaian_tambahan_bunuh_diri;}
    public static boolean getbpjs_antrean_pertanggal(){return akses.bpjs_antrean_pertanggal;}
    public static boolean getpenilaian_tambahan_perilaku_kekerasan(){return akses.penilaian_tambahan_perilaku_kekerasan;}
    public static boolean getpenilaian_tambahan_beresiko_melarikan_diri(){return akses.penilaian_tambahan_beresiko_melarikan_diri;}
    public static boolean getpersetujuan_penundaan_pelayanan(){return akses.persetujuan_penundaan_pelayanan;}
    public static boolean getsisa_diet_pasien(){return akses.sisa_diet_pasien;}
    public static boolean getpenilaian_awal_medis_ralan_bedah_mulut(){return akses.penilaian_awal_medis_ralan_bedah_mulut;}
    public static boolean getpenilaian_pasien_keracunan(){return akses.penilaian_pasien_keracunan;}
    public static boolean getpemantauan_meows_obstetri(){return akses.pemantauan_meows_obstetri;}
    public static boolean getcatatan_adime_gizi(){return akses.catatan_adime_gizi;}
    public static boolean getpengajuan_biaya(){return akses.pengajuan_biaya;}
    public static boolean getpenilaian_awal_keperawatan_ralan_geriatri(){return akses.penilaian_awal_keperawatan_ralan_geriatri;}
    public static boolean getmaster_masalah_keperawatan_geriatri(){return akses.master_masalah_keperawatan_geriatri;}
    public static boolean getmaster_rencana_keperawatan_geriatri(){return akses.master_rencana_keperawatan_geriatri;}
    public static boolean getchecklist_kriteria_masuk_hcu(){return akses.checklist_kriteria_masuk_hcu;}
    public static boolean getchecklist_kriteria_keluar_hcu(){return akses.checklist_kriteria_keluar_hcu;}
    public static boolean getpenilaian_risiko_dekubitus(){return akses.penilaian_risiko_dekubitus;}
    public static boolean getmaster_menolak_anjuran_medis(){return akses.master_menolak_anjuran_medis;}
    public static boolean getpenolakan_anjuran_medis(){return akses.penolakan_anjuran_medis;}
    public static boolean getlaporan_tahunan_penolakan_anjuran_medis(){return akses.laporan_tahunan_penolakan_anjuran_medis;}
    public static boolean gettemplate_laporan_operasi(){return akses.template_laporan_operasi;}
    public static boolean gethasil_tindakan_eswl(){return akses.hasil_tindakan_eswl;}
    public static boolean getchecklist_kriteria_masuk_icu(){return akses.checklist_kriteria_masuk_icu;}
    public static boolean getchecklist_kriteria_keluar_icu(){return akses.checklist_kriteria_keluar_icu;}
    public static boolean getakses_dokter_lain_rawat_jalan(){return akses.akses_dokter_lain_rawat_jalan;}
    public static boolean getfollow_up_dbd(){return akses.follow_up_dbd;}
    public static boolean getpenilaian_risiko_jatuh_neonatus(){return akses.penilaian_risiko_jatuh_neonatus;}
    public static boolean getpersetujuan_pengajuan_biaya(){return akses.persetujuan_pengajuan_biaya;}
    public static boolean getpemeriksaan_fisik_ralan_per_penyakit(){return akses.pemeriksaan_fisik_ralan_per_penyakit;}
    public static boolean getpenilaian_lanjutan_resiko_jatuh_geriatri(){return akses.penilaian_lanjutan_resiko_jatuh_geriatri;}
    public static boolean getpemantauan_ews_neonatus(){return akses.pemantauan_ews_neonatus;}
    public static boolean getvalidasi_persetujuan_pengajuan_biaya(){return akses.validasi_persetujuan_pengajuan_biaya;}
    public static boolean getriwayat_perawatan_icare_bpjs(){return akses.riwayat_perawatan_icare_bpjs;}
    public static boolean getrekap_pengajuan_biaya(){return akses.rekap_pengajuan_biaya;}
    public static boolean getpenilaian_awal_medis_ralan_kulit_kelamin(){return akses.penilaian_awal_medis_ralan_kulit_kelamin;}
    public static boolean getakun_host_to_host_bank_mandiri(){return akses.akun_host_to_host_bank_mandiri;}
    public static boolean getpenilaian_medis_ralan_hemodialisa(){return akses.penilaian_medis_hemodialisa;}
    public static boolean getpenilaian_level_kecemasan_ranap_anak(){return akses.penilaian_level_kecemasan_ranap_anak;}
    public static boolean getpenilaian_lanjutan_resiko_jatuh_psikiatri(){return akses.penilaian_lanjutan_resiko_jatuh_psikiatri;}
    public static boolean getpenilaian_lanjutan_skrining_fungsional(){return akses.penilaian_lanjutan_skrining_fungsional;}
    public static boolean getpenilaian_medis_ralan_rehab_medik(){return akses.penilaian_medis_ralan_rehab_medik;}
    public static boolean getlaporan_anastesi(){return akses.laporan_anestesi;}
    public static boolean gettemplate_persetujuan_penolakan_tindakan(){return akses.template_persetujuan_penolakan_tindakan;}
    public static boolean getpenilaian_medis_ralan_gawat_darurat_psikiatri(){return akses.penilaian_medis_ralan_gawat_darurat_psikiatri;}
    public static boolean getbpjs_referensi_setting_apotek(){return akses.bpjs_referensi_setting_apotek;}
    public static boolean getbpjs_referensi_obat_apotek(){return akses.bpjs_referensi_obat_apotek;}
    public static boolean getbpjs_mapping_obat_apotek(){return akses.bpjs_mapping_obat_apotek;}
    public static boolean getpembayaran_bank_mandiri(){return akses.pembayaran_bank_mandiri;}
    public static boolean getpenilaian_ulang_nyeri(){return akses.penilaian_ulang_nyeri;}
    public static boolean getpenilaian_terapi_wicara(){return akses.penilaian_terapi_wicara;}
    public static boolean getbpjs_obat_23hari_apotek(){return akses.bpjs_obat_23hari_apotek;}
    public static boolean getpengkajian_restrain(){return akses.pengkajian_restrain;}
    public static boolean getbpjs_kunjungan_sep_apotek(){return akses.bpjs_kunjungan_sep_apotek;}
    public static boolean getbpjs_monitoring_klaim_apotek(){return akses.bpjs_monitoring_klaim_apotek;}
    public static boolean getbpjs_daftar_pelayanan_obat_apotek(){return akses.bpjs_daftar_pelayanan_obat_apotek;}
    public static boolean getpenilaian_awal_medis_ralan_paru(){return akses.penilaian_awal_medis_ralan_paru;}
    public static boolean getcatatan_keperawatan_ralan(){return akses.catatan_keperawatan_ralan;}
    public static boolean getcatatan_persalinan(){return akses.catatan_persalinan;}
    public static boolean getskor_aldrette_pasca_anestesi(){return akses.skor_aldrette_pasca_anestesi;}
    public static boolean getskor_steward_pasca_anestesi(){return akses.skor_steward_pasca_anestesi;}
    public static boolean getskor_bromage_pasca_anestesi(){return akses.skor_bromage_pasca_anestesi;}
    public static boolean getpenilaian_pre_induksi(){return akses.penilaian_pre_induksi;}
    public static boolean gethasil_usg_urologi(){return akses.hasil_usg_urologi;}
    public static boolean gethasil_usg_gynecologi(){return akses.hasil_usg_gynecologi;}
    public static boolean gethasil_pemeriksaan_ekg(){return akses.hasil_pemeriksaan_ekg;}
    public static boolean gethapus_edit_sep_bpjs(){return akses.hapus_edit_sep_bpjs;}
    public static boolean getsatu_sehat_kirim_diet(){return akses.satu_sehat_kirim_diet;}
    public static boolean getsatu_sehat_mapping_obat(){return akses.satu_sehat_mapping_obat;}
    public static boolean getdapur_ringkasan_pembelian(){return akses.dapur_ringkasan_pembelian;}
    public static boolean getsatu_sehat_kirim_medication(){return akses.satu_sehat_kirim_medication;}
    public static boolean getsatu_sehat_kirim_medicationrequest(){return akses.satu_sehat_kirim_medicationrequest;}
    public static boolean getpenatalaksanaan_terapi_okupasi(){return akses.penatalaksanaan_terapi_okupasi;}
    public static boolean getsatu_sehat_kirim_medicationdispense(){return akses.satu_sehat_kirim_medicationdispense;}
    public static boolean gethasil_usg_neonatus(){return akses.hasil_usg_neonatus;}
    public static boolean gethasil_endoskopi_faring_laring(){return akses.hasil_endoskopi_faring_laring;}
    public static boolean getsatu_sehat_mapping_radiologi(){return akses.satu_sehat_mapping_radiologi;}
    public static boolean getsatu_sehat_kirim_servicerequest_radiologi(){return akses.satu_sehat_kirim_servicerequest_radiologi;}
    public static boolean gethasil_endoskopi_hidung(){return akses.hasil_endoskopi_hidung;}
    public static boolean getsatu_sehat_kirim_specimen_radiologi(){return akses.satu_sehat_kirim_specimen_radiologi;}
    public static boolean getmaster_masalah_keperawatan_neonatus(){return akses.master_masalah_keperawatan_neonatus;}
    public static boolean getmaster_rencana_keperawatan_neonatus(){return akses.master_rencana_keperawatan_neonatus;}
    public static boolean getpenilaian_awal_keperawatan_ranap_neonatus(){return akses.penilaian_awal_keperawatan_ranap_neonatus;}
    public static boolean getsatu_sehat_kirim_observation_radiologi(){return akses.satu_sehat_kirim_observation_radiologi;}
    public static boolean getsatu_sehat_kirim_diagnosticreport_radiologi(){return akses.satu_sehat_kirim_diagnosticreport_radiologi;}
    public static boolean gethasil_endoskopi_telinga(){return akses.hasil_endoskopi_telinga;}
    public static boolean getsatu_sehat_mapping_lab(){return akses.satu_sehat_mapping_lab;}
    public static boolean getsatu_sehat_kirim_servicerequest_lab(){return akses.satu_sehat_kirim_servicerequest_lab;}
    public static boolean getsatu_sehat_kirim_servicerequest_labmb(){return akses.satu_sehat_kirim_servicerequest_labmb;}
    public static boolean getsatu_sehat_kirim_specimen_lab(){return akses.satu_sehat_kirim_specimen_lab;}
    public static boolean getsatu_sehat_kirim_specimen_labmb(){return akses.satu_sehat_kirim_specimen_labmb;}
    public static boolean getsatu_sehat_kirim_observation_lab(){return akses.satu_sehat_kirim_observation_lab;}
    public static boolean getsatu_sehat_kirim_observation_labmb(){return akses.satu_sehat_kirim_observation_labmb;}
    public static boolean getsatu_sehat_kirim_diagnosticreport_lab(){return akses.satu_sehat_kirim_diagnosticreport_lab;}
    public static boolean getsatu_sehat_kirim_diagnosticreport_labmb(){return akses.satu_sehat_kirim_diagnosticreport_labmb;}
    public static boolean getkepatuhan_kelengkapan_keselamatan_bedah(){return akses.kepatuhan_kelengkapan_keselamatan_bedah;}
    public static boolean getnilai_piutang_perjenis_bayar_per_bulan(){return akses.nilai_piutang_perjenis_bayar_per_bulan;}
    public static boolean getringkasan_piutang_jenis_bayar(){return akses.ringkasan_piutang_jenis_bayar;}
    public static boolean getpenilaian_pasien_imunitas_rendah(){return akses.penilaian_pasien_imunitas_rendah;}
    public static boolean getbalance_cairan(){return akses.balance_cairan;}
    public static boolean getcatatan_observasi_chbp(){return akses.catatan_observasi_chbp;}
    public static boolean getcatatan_observasi_induksi_persalinan(){return akses.catatan_observasi_induksi_persalinan;}
    public static boolean getskp_kategori_penilaian(){return akses.skp_kategori_penilaian;}
    public static boolean getskp_kriteria_penilaian(){return akses.skp_kriteria_penilaian;}
    public static boolean getskp_penilaian(){return akses.skp_penilaian;}
    public static boolean getreferensi_poli_mobilejknfktp(){return akses.referensi_poli_mobilejknfktp;}
    public static boolean getreferensi_dokter_mobilejknfktp(){return akses.referensi_dokter_mobilejknfktp;}
    public static boolean getskp_rekapitulasi_penilaian(){return akses.skp_rekapitulasi_penilaian;}
    public static boolean getpembayaran_pihak_ke3_bankmandiri(){return akses.pembayaran_pihak_ke3_bankmandiri;}
    public static boolean getmetode_pembayaran_bankmandiri(){return akses.metode_pembayaran_bankmandiri;}
    public static boolean getbank_tujuan_transfer_bankmandiri(){return akses.bank_tujuan_transfer_bankmandiri;}
    public static boolean getkodetransaksi_tujuan_transfer_bankmandiri(){return akses.kodetransaksi_tujuan_transfer_bankmandiri;}
    public static boolean getkonsultasi_medik(){return akses.konsultasi_medik;}
    public static boolean getjawaban_konsultasi_medik(){return akses.jawaban_konsultasi_medik;}
    public static boolean getpcare_cek_alergi(){return akses.pcare_cek_alergi;}
    public static boolean getpcare_cek_prognosa(){return akses.pcare_cek_prognosa;}
    public static boolean getdata_sasaran_usiaproduktif(){return akses.data_sasaran_usiaproduktif;}
    public static boolean getdata_sasaran_usialansia(){return akses.data_sasaran_usialansia;}
    public static boolean getskrining_perilaku_merokok_sekolah_remaja(){return akses.skrining_perilaku_merokok_sekolah_remaja;}
    public static boolean getskrining_kekerasan_pada_perempuan(){return akses.skrining_kekerasan_pada_perempuan;}
    public static boolean getskrining_obesitas(){return akses.skrining_obesitas;}
    public static boolean getskrining_risiko_kanker_payudara(){return akses.skrining_risiko_kanker_payudara;}
    public static boolean getskrining_risiko_kanker_paru(){return akses.skrining_risiko_kanker_paru;}
    public static boolean getskrining_tbc(){return akses.skrining_tbc;}
    public static boolean getskrining_kesehatan_gigi_mulut_remaja(){return akses.skrining_kesehatan_gigi_mulut_remaja;}
    public static boolean getpenilaian_awal_keperawatan_ranap_bayi(){return akses.penilaian_awal_keperawatan_ranap_bayi;}
    public static boolean getbooking_mcu_perusahaan(){return akses.booking_mcu_perusahaan;}
    public static boolean getcatatan_observasi_restrain_nonfarma(){return akses.catatan_observasi_restrain_nonfarma;}
    public static boolean getcatatan_observasi_ventilator(){return akses.catatan_observasi_ventilator;}
    public static boolean getcatatan_anestesi_sedasi(){return akses.catatan_anestesi_sedasi;}
    public static boolean getskrining_puma(){return akses.skrining_puma;}
    public static boolean getsatu_sehat_kirim_careplan(){return akses.satu_sehat_kirim_careplan;}
    public static boolean getsatu_sehat_kirim_medicationstatement(){return akses.satu_sehat_kirim_medicationstatement;}
    public static boolean getskrining_adiksi_nikotin(){return akses.skrining_adiksi_nikotin;}
    public static boolean getskrining_thalassemia(){return akses.skrining_thalassemia;}
    public static boolean getskrining_instrumen_sdq(){return akses.skrining_instrumen_sdq;}
    public static boolean getskrining_instrumen_srq(){return akses.skrining_instrumen_srq;}
    public static boolean getchecklist_pemberian_fibrinolitik(){return akses.checklist_pemberian_fibrinolitik;}
    public static boolean getskrining_kanker_kolorektal(){return akses.skrining_kanker_kolorektal;}
    public static boolean getdapur_pemesanan(){return akses.dapur_pemesanan;}
    public static boolean getbayar_pesan_dapur(){return akses.bayar_pesan_dapur;}
    public static boolean gethutang_dapur(){return akses.hutang_dapur;}
    public static boolean gettitip_faktur_dapur(){return akses.titip_faktur_dapur;}
    public static boolean getvalidasi_tagihan_dapur(){return akses.validasi_tagihan_dapur;}
    public static boolean getsurat_pemesanan_dapur(){return akses.surat_pemesanan_dapur;}
    public static boolean getpengajuan_barang_dapur(){return akses.pengajuan_barang_dapur;}
    public static boolean getdapur_returbeli(){return akses.dapur_returbeli;}
    public static boolean gethibah_dapur(){return akses.hibah_dapur;}
    public static boolean getringkasan_penerimaan_dapur(){return akses.ringkasan_penerimaan_dapur;}
    public static boolean getringkasan_pengajuan_dapur(){return akses.ringkasan_pengajuan_dapur;}
    public static boolean getringkasan_pemesanan_dapur(){return akses.ringkasan_pemesanan_dapur;}
    public static boolean getringkasan_returbeli_dapur(){return akses.ringkasan_returbeli_dapur;}
    public static boolean getringkasan_stokkeluar_dapur(){return akses.ringkasan_stokkeluar_dapur;}
    public static boolean getdapur_stokkeluar_pertanggal(){return akses.dapur_stokkeluar_pertanggal;}
    public static boolean getsirkulasi_dapur(){return akses.sirkulasi_dapur;}
    public static boolean getsirkulasi_dapur2(){return akses.sirkulasi_dapur2;}
    public static boolean getverifikasi_penerimaan_dapur(){return akses.verifikasi_penerimaan_dapur;}
    public static boolean getnilai_penerimaan_vendor_dapur_perbulan(){return akses.nilai_penerimaan_vendor_dapur_perbulan;}
    public static boolean getringkasan_hutang_vendor_dapur(){return akses.ringkasan_hutang_vendor_dapur;}
    public static boolean getpenilaian_psikologi_klinis(){return akses.penilaian_psikologi_klinis;}
    public static boolean getpenilaian_awal_medis_ranap_neonatus(){return akses.penilaian_awal_medis_ranap_neonatus;}
    public static boolean getpenilaian_derajat_dehidrasi(){return akses.penilaian_derajat_dehidrasi;}
    public static boolean getringkasan_jasa_tindakan_medis(){return akses.ringkasan_jasa_tindakan_medis;}
    public static boolean getpendapatan_per_akun(){return akses.pendapatan_per_akun;}
    public static boolean gethasil_pemeriksaan_echo(){return akses.hasil_pemeriksaan_echo;}
    public static boolean getpenilaian_bayi_baru_lahir(){return akses.penilaian_bayi_baru_lahir;}
    public static boolean getrl1_3_ketersediaan_kamar(){return akses.rl1_3_ketersediaan_kamar;}
    public static boolean getpendapatan_per_akun_closing(){return akses.pendapatan_per_akun_closing;}
    public static boolean getpengeluaran_pengeluaran(){return akses.pengeluaran_pengeluaran;}
    public static boolean getskrining_diabetes_melitus(){return akses.skrining_diabetes_melitus;}
    public static boolean getlaporan_tindakan(){return akses.laporan_tindakan;}
    public static boolean getpelaksanaan_informasi_edukasi(){return akses.pelaksanaan_informasi_edukasi;}
    public static boolean getlayanan_kedokteran_fisik_rehabilitasi(){return akses.layanan_kedokteran_fisik_rehabilitasi;}
    public static boolean getskrining_kesehatan_gigi_mulut_balita(){return akses.skrining_kesehatan_gigi_mulut_balita;}
    public static boolean getskrining_anemia(){return akses.skrining_anemia;}
    public static boolean getlayanan_program_kfr(){return akses.layanan_program_kfr;}
    public static boolean getskrining_hipertensi(){return akses.skrining_hipertensi;}
    public static boolean getskrining_kesehatan_penglihatan(){return akses.skrining_kesehatan_penglihatan;}
    public static boolean getcatatan_observasi_hemodialisa(){return akses.catatan_observasi_hemodialisa;}
    public static boolean getskrining_kesehatan_gigi_mulut_dewasa(){return akses.skrining_kesehatan_gigi_mulut_dewasa;}
    public static boolean getskrining_risiko_kanker_serviks(){return akses.skrining_risiko_kanker_serviks;}
    public static boolean getcatatan_cairan_hemodialisa(){return akses.catatan_cairan_hemodialisa;}
    public static boolean getskrining_kesehatan_gigi_mulut_lansia(){return akses.skrining_kesehatan_gigi_mulut_lansia;}
    public static boolean getskrining_indra_pendengaran(){return akses.skrining_indra_pendengaran;}
    public static boolean getcatatan_pengkajian_paska_operasi(){return akses.catatan_pengkajian_paska_operasi;}
    public static boolean getskrining_frailty_syndrome(){return akses.skrining_frailty_syndrome;}
    public static boolean getsirkulasi_cssd(){return akses.sirkulasi_cssd;}
    public static boolean getlama_pelayanan_cssd(){return akses.lama_pelayanan_cssd;}
    public static boolean getcatatan_observasi_bayi(){return akses.catatan_observasi_bayi;}
    public static boolean getriwayat_surat_peringatan(){return akses.riwayat_surat_peringatan;}
    public static boolean getmaster_kesimpulan_anjuran_mcu(){return akses.master_kesimpulan_anjuran_mcu;}
    public static boolean getkategori_piutang_jasa_perusahaan(){return akses.kategori_piutang_jasa_perusahaan;}
    public static boolean getpiutang_jasa_perusahaan(){return akses.piutang_jasa_perusahaan;}
    public static boolean getbayar_piutang_jasa_perusahaan(){return akses.bayar_piutang_jasa_perusahaan;}
    public static boolean getpiutang_jasa_perusahaan_belum_lunas(){return akses.piutang_jasa_perusahaan_belum_lunas;}
    public static boolean getchecklist_kesiapan_anestesi(){return akses.checklist_kesiapan_anestesi;}
    public static boolean getpiutang_peminjaman_uang_belum_lunas(){return akses.piutang_peminjaman_uang_belum_lunas;}
    public static boolean gethasil_pemeriksaan_slit_lamp(){return akses.hasil_pemeriksaan_slit_lamp;}
    public static boolean gethasil_pemeriksaan_oct(){return akses.hasil_pemeriksaan_oct;}
    public static boolean getbeban_hutang_lain(){return akses.beban_hutang_lain;}
    public static boolean getpoli_asal_pasien_ranap(){return akses.poli_asal_pasien_ranap;}
    public static boolean getpemberi_hutang_lain(){return akses.pemberi_hutang_lain;}
    public static boolean getdokter_asal_pasien_ranap(){return akses.dokter_asal_pasien_ranap;}
    public static boolean getduta_parkir_rekap_keluar(){return akses.duta_parkir_rekap_keluar;}
    public static boolean getsurat_keterangan_layak_terbang(){return akses.surat_keterangan_layak_terbang;}
    public static boolean getbayar_beban_hutang_lain(){return akses.bayar_beban_hutang_lain;}
    public static boolean getsurat_persetujuan_pemeriksaan_hiv(){return akses.surat_persetujuan_pemeriksaan_hiv;}
    public static boolean getskrining_instrumen_acrs(){return akses.skrining_instrumen_acrs;}
    public static boolean getsurat_pernyataan_memilih_dpjp(){return akses.surat_pernyataan_memilih_dpjp;}
    public static boolean getskrining_instrumen_mental_emosional(){return akses.skrining_instrumen_mental_emosional;}
    public static boolean getpelanggan_lab_kesehatan_lingkungan(){return akses.pelanggan_lab_kesehatan_lingkungan;}
    public static boolean getkriteria_masuk_nicu(){return akses.kriteria_masuk_nicu;}
    public static boolean getkriteria_keluar_nicu(){return akses.kriteria_keluar_nicu;}
    public static boolean getpenilaian_medis_ranap_psikiatrik(){return akses.penilaian_medis_ranap_psikiatrik;}
    public static boolean getkriteria_masuk_picu(){return akses.kriteria_masuk_picu;}
    public static boolean getkriteria_keluar_picu(){return akses.kriteria_keluar_picu;}
    public static boolean getmaster_sampel_bakumutu(){return akses.master_sampel_bakumutu;}
    public static boolean getskrining_instrumen_amt(){return akses.skrining_instrumen_amt;}
    public static boolean getparameter_pengujian_lab_kesehatan_lingkungan(){return akses.parameter_pengujian_lab_kesehatan_lingkungan;}
    public static boolean getnilai_normal_baku_mutu_lab_kesehatan_lingkungan(){return akses.nilai_normal_baku_mutu_lab_kesehatan_lingkungan;}
    public static boolean getskrining_pneumonia_severity_index(){return akses.skrining_pneumonia_severity_index;}
    public static boolean getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan(){return akses.permintaan_pengujian_sampel_lab_kesehatan_lingkungan;}
    public static boolean getpenilaian_awal_medis_ralan_jantung(){return akses.penilaian_awal_medis_ralan_jantung;}
    public static boolean getpenilaian_awal_medis_ralan_urologi(){return akses.penilaian_awal_medis_ralan_urologi;}
    public static boolean gethasil_pemeriksaan_treadmill(){return akses.hasil_pemeriksaan_treadmill;}
    public static boolean gethasil_pemeriksaan_echo_pediatrik(){return akses.hasil_pemeriksaan_echo_pediatrik;}
}   
