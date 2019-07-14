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
    
    private static String kode="",kdbangsal="",namars="",alamatrs="",kabupatenrs="",propinsirs="",kontakrs="",emailrs="",form="",namauser=""; 
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
            data_triase=false,master_triase_skala1=false,master_triase_skala2=false,master_triase_skala3=false,master_triase_skala4=false,
            master_triase_skala5=false,master_triase_pemeriksaan=false,master_triase_macamkasus=false;
    
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
                        akses.data_triase=true;
                        akses.master_triase_skala1=true;
                        akses.master_triase_skala2=true;
                        akses.master_triase_skala3=true;
                        akses.master_triase_skala4=true;
                        akses.master_triase_skala5=true;
                        akses.master_triase_pemeriksaan=true;
                        akses.master_triase_macamkasus=true;
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
                        akses.data_triase=rs2.getBoolean("data_triase");
                        akses.master_triase_skala1=rs2.getBoolean("master_triase_skala1");
                        akses.master_triase_skala2=rs2.getBoolean("master_triase_skala2");
                        akses.master_triase_skala3=rs2.getBoolean("master_triase_skala3");
                        akses.master_triase_skala4=rs2.getBoolean("master_triase_skala4");
                        akses.master_triase_skala5=rs2.getBoolean("master_triase_skala5");
                        akses.master_triase_pemeriksaan=rs2.getBoolean("master_triase_pemeriksaan");
                        akses.master_triase_macamkasus=rs2.getBoolean("master_triase_macamkasus");
                    }else if((rs.getRow()==0)&&(rs2.getRow()==0)){
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
                        akses.data_triase=false;
                        akses.master_triase_skala1=false;
                        akses.master_triase_skala2=false;
                        akses.master_triase_skala3=false;
                        akses.master_triase_skala4=false;
                        akses.master_triase_skala5=false;
                        akses.master_triase_pemeriksaan=false;
                        akses.master_triase_macamkasus=false;
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
    public static String getnamars(){return akses.namars;}
    public static String getalamatrs(){return akses.alamatrs;}
    public static String getkabupatenrs(){return akses.kabupatenrs;}
    public static String getpropinsirs(){return akses.propinsirs;}
    public static String getkontakrs(){return akses.kontakrs;}
    public static String getemailrs(){return akses.emailrs;}
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
    public static boolean getdata_triase(){return akses.data_triase;}
    public static boolean getmaster_triase_skala1(){return akses.master_triase_skala1;}
    public static boolean getmaster_triase_skala2(){return akses.master_triase_skala2;}
    public static boolean getmaster_triase_skala3(){return akses.master_triase_skala3;}
    public static boolean getmaster_triase_skala4(){return akses.master_triase_skala4;}
    public static boolean getmaster_triase_skala5(){return akses.master_triase_skala5;}
    public static boolean getmaster_triase_pemeriksaan(){return akses.master_triase_pemeriksaan;}
    public static boolean getmaster_triase_macamkasus(){return akses.master_triase_macamkasus;}
}   
