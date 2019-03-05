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
            password_asuransi=false;
    
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
                    if(rs.getRow()>=1){
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
                        var.darurat_stok=true;
                        var.retensi_rm=true;
                        var.temporary_presensi=true;
                        var.jurnal_harian=true;
                        var.sirkulasi_obat2=true;
                        var.edit_registrasi=true;
                        var.bpjs_referensi_diagnosa=true;
                        var.bpjs_referensi_poli=true;
                        var.industrifarmasi=true;
                        var.harian_js=true;
                        var.bulanan_js=true;
                        var.harian_paket_bhp=true;
                        var.bulanan_paket_bhp=true;
                        var.piutang_pasien2=true;
                        var.bpjs_referensi_faskes=true;
                        var.bpjs_sep=true;
                        var.pengambilan_utd=true;
                        var.tarif_utd=true;
                        var.pengambilan_utd2=true;
                        var.utd_medis_rusak=true;
                        var.pengambilan_penunjang_utd=true;
                        var.pengambilan_penunjang_utd2=true;
                        var.utd_penunjang_rusak=true;
                        var.suplier_penunjang=true;
                        var.utd_donor=true;
                        var.bpjs_monitoring_klaim=true;
                        var.utd_cekal_darah=true;
                        var.utd_komponen_darah=true;
                        var.utd_stok_darah=true;
                        var.utd_pemisahan_darah=true;
                        var.harian_kamar=true;
                        var.rincian_piutang_pasien=true;
                        var.keuntungan_beri_obat_nonpiutang=true;
                        var.reklasifikasi_ralan=true;
                        var.reklasifikasi_ranap=true;
                        var.utd_penyerahan_darah=true;
                        var.hutang_obat=true;
                        var.riwayat_obat_alkes_bhp=true;
                        var.sensus_harian_poli=true;
                        var.rl4a=true;
                        var.aplicare_referensi_kamar=true;
                        var.aplicare_ketersediaan_kamar=true;
                        var.inacbg_klaim_baru_otomatis=true;
                        var.inacbg_klaim_baru_manual=true;
                        var.inacbg_coder_nik=true;
                        var.mutasi_berkas=true;
                        var.akun_piutang=true;
                        var.harian_kso=true;
                        var.bulanan_kso=true;
                        var.harian_menejemen=true;
                        var.bulanan_menejemen=true;
                        var.inhealth_cek_eligibilitas=true;
                        var.inhealth_referensi_jenpel_ruang_rawat=true;
                        var.inhealth_referensi_poli=true;
                        var.inhealth_referensi_faskes=true;
                        var.inhealth_sjp=true;
                        var.piutang_ralan=true;
                        var.piutang_ranap=true;
                        var.detail_piutang_penjab=true;
                        var.lama_pelayanan_ralan=true;
                        var.catatan_pasien=true;
                        var.rl4b=true;
                        var.rl4asebab=true;
                        var.rl4bsebab=true;
                        var.data_HAIs=true;
                        var.harian_HAIs=true;
                        var.bulanan_HAIs=true;
                        var.hitung_bor=true;
                        var.perusahaan_pasien=true;
                        var.resep_dokter=true;
                        var.lama_pelayanan_apotek=true;
                        var.hitung_alos=true;
                        var.detail_tindakan=true;
                        var.rujukan_poli_internal=true;
                        var.rekap_poli_anak=true;
                        var.grafik_kunjungan_poli=true;
                        var.grafik_kunjungan_perdokter=true;
                        var.grafik_kunjungan_perpekerjaan=true;
                        var.grafik_kunjungan_perpendidikan=true;
                        var.grafik_kunjungan_pertahun=true;
                        var.berkas_digital_perawatan=true;
                        var.penyakit_menular_ranap=true;
                        var.penyakit_menular_ralan=true;
                        var.grafik_kunjungan_perbulan=true;
                        var.grafik_kunjungan_pertanggal=true;
                        var.grafik_kunjungan_demografi=true;
                        var.grafik_kunjungan_statusdaftartahun=true;
                        var.grafik_kunjungan_statusdaftartahun2=true;
                        var.grafik_kunjungan_statusdaftarbulan=true;
                        var.grafik_kunjungan_statusdaftarbulan2=true;
                        var.grafik_kunjungan_statusdaftartanggal=true;
                        var.grafik_kunjungan_statusdaftartanggal2=true;
                        var.grafik_kunjungan_statusbataltahun=true;
                        var.grafik_kunjungan_statusbatalbulan=true;
                        var.pcare_cek_penyakit=true;
                        var.grafik_kunjungan_statusbataltanggal=true;
                        var.kategori_barang=true;
                        var.golongan_barang=true;
                        var.pemberian_obat_pertanggal=true;                        
                        var.penjualan_obat_pertanggal=true;
                        var.pcare_cek_kesadaran=true;
                        var.pembatalan_periksa_dokter=true;
                        var.pembayaran_per_unit=true;
                        var.rekap_pembayaran_per_unit=true;
                        var.grafik_kunjungan_percarabayar=true;
                        var.ipsrs_pengadaan_pertanggal=true;
                        var.ipsrs_stokkeluar_pertanggal=true;
                        var.grafik_kunjungan_ranaptahun=true;
                        var.pcare_cek_rujukan=true;
                        var.grafik_lab_ralantahun=true;
                        var.grafik_rad_ralantahun=true;;
                        var.cek_entry_ralan=true;
                        var.inacbg_klaim_baru_manual2=true;
                        var.permintaan_medis=true;
                        var.rekap_permintaan_medis=true;
                        var.surat_pemesanan_medis=true;
                        var.permintaan_non_medis=true;
                        var.rekap_permintaan_non_medis=true;
                        var.surat_pemesanan_non_medis=true;
                        var.grafik_per_perujuk=true;
                        var.bpjs_cek_prosedur=true;
                        var.bpjs_cek_kelas_rawat=true;
                        var.bpjs_cek_dokter=true;
                        var.bpjs_cek_spesialistik=true;
                        var.bpjs_cek_ruangrawat=true;
                        var.bpjs_cek_carakeluar=true;
                        var.bpjs_cek_pasca_pulang=true;
                        var.detail_tindakan_okvk=true;
                        var.billing_parsial=true;
                        var.bpjs_cek_nomor_rujukan_rs=true;
                        var.bpjs_cek_rujukan_kartu_pcare=true;
                        var.bpjs_cek_rujukan_kartu_rs=true;
                        var.akses_depo_obat=true;
                        var.bpjs_rujukan_keluar=true;
                        var.grafik_lab_ralanbulan=true;
                        var.pengeluaran_stok_apotek=true;
                        var.grafik_rad_ralanbulan=true;
                        var.detailjmdokter2=true;
                        var.pengaduan_pasien=true;
                        var.grafik_lab_ralanhari=true;
                        var.grafik_rad_ralanhari=true;
                        var.sensus_harian_ralan=true;
                        var.metode_racik=true;
                        var.pembayaran_akun_bayar=true;
                        var.pengguna_obat_resep=true;
                        var.rekap_pemesanan=true;
                        var.master_berkas_pegawai=true;
                        var.berkas_kepegawaian=true;
                        var.riwayat_jabatan=true;
                        var.riwayat_pendidikan=true;
                        var.riwayat_naik_gaji=true;
                        var.kegiatan_ilmiah=true;
                        var.riwayat_penghargaan=true;
                        var.riwayat_penelitian=true;
                        var.penerimaan_non_medis=true;
                        var.bayar_pesan_non_medis=true;
                        var.hutang_barang_non_medis=true;
                        var.rekap_pemesanan_non_medis=true;
                        var.insiden_keselamatan=true;
                        var.insiden_keselamatan_pasien=true;
                        var.grafik_ikp_pertahun=true;
                        var.grafik_ikp_perbulan=true;
                        var.grafik_ikp_pertanggal=true;
                        var.riwayat_data_batch=true;
                        var.grafik_ikp_jenis=true;
                        var.grafik_ikp_dampak=true;
                        var.piutang_akun_piutang=true;
                        var.grafik_kunjungan_per_agama=true;
                        var.grafik_kunjungan_per_umur=true;
                        var.suku_bangsa=true;
                        var.bahasa_pasien=true;
                        var.golongan_tni=true;
                        var.satuan_tni=true;
                        var.jabatan_tni=true;
                        var.pangkat_tni=true;
                        var.golongan_polri=true;
                        var.satuan_polri=true;
                        var.jabatan_polri=true;
                        var.pangkat_polri=true;
                        var.cacat_fisik=true;
                        var.grafik_kunjungan_suku=true;
                        var.grafik_kunjungan_bahasa=true;
                        var.booking_operasi=true;
                        var.mapping_poli_bpjs=true;
                        var.grafik_kunjungan_per_cacat=true;
                        var.barang_cssd=true;
                        var.skdp_bpjs=true;
                        var.booking_registrasi=true;
                        var.bpjs_cek_propinsi=true;
                        var.bpjs_cek_kabupaten=true;
                        var.bpjs_cek_kecamatan=true;
                        var.bpjs_cek_dokterdpjp=true;
                        var.bpjs_cek_riwayat_rujukanrs=true;
                        var.bpjs_cek_tanggal_rujukan=true;
                        var.permintaan_lab=true;
                        var.permintaan_radiologi=true;
                        var.surat_indeks=true;
                        var.surat_map=true;
                        var.surat_almari=true;
                        var.surat_rak=true;
                        var.surat_ruang=true;
                        var.surat_klasifikasi=true;
                        var.surat_status=true; 
                        var.surat_sifat=true;
                        var.surat_balas=true;
                        var.surat_masuk=true;
                        var.pcare_cek_dokter=true;
                        var.pcare_cek_poli=true;
                        var.pcare_cek_provider=true;
                        var.pcare_cek_statuspulang=true;
                        var.pcare_cek_spesialis=true;
                        var.pcare_cek_subspesialis=true;
                        var.pcare_cek_sarana=true;
                        var.pcare_cek_khusus=true;
                        var.pcare_cek_obat=true;
                        var.pcare_cek_tindakan=true;
                        var.pcare_cek_faskessubspesialis=true;
                        var.pcare_cek_faskesalihrawat=true;
                        var.pcare_cek_faskesthalasemia=true;
                        var.pcare_mapping_obat=true;
                        var.pcare_mapping_tindakan=true;
                        var.pcare_club_prolanis=true;
                        var.pcare_mapping_poli=true;
                        var.pcare_kegiatan_kelompok=true;
                        var.pcare_mapping_tindakan_ranap=true;
                        var.pcare_peserta_kegiatan_kelompok=true;
                        var.sirkulasi_obat3=true;
                        var.bridging_pcare_daftar=true;
                        var.pcare_mapping_dokter=true;
                        var.ranap_per_ruang=true;
                        var.penyakit_ranap_cara_bayar=true;
                        var.anggota_militer_dirawat=true;
                        var.set_input_parsial=true;
                        var.lama_pelayanan_radiologi=true;
                        var.lama_pelayanan_lab=true;
                        var.bpjs_cek_sep=true;
                        var.catatan_perawatan=true;
                        var.surat_keluar=true;
                        var.kegiatan_farmasi=true;
                        var.stok_opname_logistik=true;
                        var.sirkulasi_non_medis=true;
                        var.rekap_lab_pertahun=true;
                        var.perujuk_lab_pertahun=true;
                        var.rekap_radiologi_pertahun=true;
                        var.perujuk_radiologi_pertahun=true;
                        var.jumlah_porsi_diet=true;
                        var.jumlah_macam_diet=true;
                        var.payment_point2=true;
                        var.pembayaran_akun_bayar2=true;
                        var.hapus_nota_salah=true;
                        var.hais_perbangsal=true;
                        var.ppn_obat=true;
                        var.saldo_akun_perbulan=true;
                        var.display_apotek=true;
                        var.sisrute_referensi_faskes=true;
                        var.sisrute_referensi_alasanrujuk=true;
                        var.sisrute_referensi_diagnosa=true;
                        var.sisrute_rujukan_masuk=true;
                        var.sisrute_rujukan_keluar=true;
                        var.bpjs_cek_skdp=true;
                        var.data_batch=true;
                        var.kunjungan_permintaan_lab=true;
                        var.kunjungan_permintaan_lab2=true;
                        var.kunjungan_permintaan_radiologi=true;
                        var.kunjungan_permintaan_radiologi2=true;
                        var.pcare_pemberian_obat=true;
                        var.pcare_pemberian_tindakan=true;
                        var.pembayaran_akun_bayar3=true;
                        var.password_asuransi=true;
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
                        var.darurat_stok=rs2.getBoolean("darurat_stok");
                        var.retensi_rm=rs2.getBoolean("retensi_rm");
                        var.temporary_presensi=rs2.getBoolean("temporary_presensi");
                        var.jurnal_harian=rs2.getBoolean("jurnal_harian");
                        var.sirkulasi_obat2=rs2.getBoolean("sirkulasi_obat2");
                        var.edit_registrasi=rs2.getBoolean("edit_registrasi");
                        var.bpjs_referensi_diagnosa=rs2.getBoolean("bpjs_referensi_diagnosa");
                        var.bpjs_referensi_poli=rs2.getBoolean("bpjs_referensi_poli");
                        var.industrifarmasi=rs2.getBoolean("industrifarmasi");
                        var.harian_js=rs2.getBoolean("harian_js");
                        var.bulanan_js=rs2.getBoolean("bulanan_js");
                        var.harian_paket_bhp=rs2.getBoolean("harian_paket_bhp");
                        var.bulanan_paket_bhp=rs2.getBoolean("bulanan_paket_bhp");
                        var.piutang_pasien2=rs2.getBoolean("piutang_pasien2");
                        var.bpjs_referensi_faskes=rs2.getBoolean("bpjs_referensi_faskes");
                        var.bpjs_sep=rs2.getBoolean("bpjs_sep");
                        var.pengambilan_utd=rs2.getBoolean("pengambilan_utd");
                        var.tarif_utd=rs2.getBoolean("tarif_utd");
                        var.pengambilan_utd2=rs2.getBoolean("pengambilan_utd2");
                        var.utd_medis_rusak=rs2.getBoolean("utd_medis_rusak");
                        var.pengambilan_penunjang_utd=rs2.getBoolean("pengambilan_penunjang_utd");
                        var.pengambilan_penunjang_utd2=rs2.getBoolean("pengambilan_penunjang_utd2");
                        var.utd_penunjang_rusak=rs2.getBoolean("utd_penunjang_rusak");
                        var.suplier_penunjang=rs2.getBoolean("suplier_penunjang");
                        var.utd_donor=rs2.getBoolean("utd_donor");
                        var.bpjs_monitoring_klaim=rs2.getBoolean("bpjs_monitoring_klaim");
                        var.utd_cekal_darah=rs2.getBoolean("utd_cekal_darah");
                        var.utd_komponen_darah=rs2.getBoolean("utd_komponen_darah");
                        var.utd_stok_darah=rs2.getBoolean("utd_stok_darah");
                        var.utd_pemisahan_darah=rs2.getBoolean("utd_pemisahan_darah");
                        var.harian_kamar=rs2.getBoolean("harian_kamar");
                        var.rincian_piutang_pasien=rs2.getBoolean("rincian_piutang_pasien");
                        var.keuntungan_beri_obat_nonpiutang=rs2.getBoolean("keuntungan_beri_obat_nonpiutang");
                        var.reklasifikasi_ralan=rs2.getBoolean("reklasifikasi_ralan");
                        var.reklasifikasi_ranap=rs2.getBoolean("reklasifikasi_ranap");
                        var.utd_penyerahan_darah=rs2.getBoolean("utd_penyerahan_darah");
                        var.hutang_obat=rs2.getBoolean("hutang_obat");
                        var.riwayat_obat_alkes_bhp=rs2.getBoolean("riwayat_obat_alkes_bhp");
                        var.sensus_harian_poli=rs2.getBoolean("sensus_harian_poli");
                        var.rl4a=rs2.getBoolean("rl4a");
                        var.aplicare_referensi_kamar=rs2.getBoolean("aplicare_referensi_kamar");
                        var.aplicare_ketersediaan_kamar=rs2.getBoolean("aplicare_ketersediaan_kamar");
                        var.inacbg_klaim_baru_otomatis=rs2.getBoolean("inacbg_klaim_baru_otomatis");
                        var.inacbg_klaim_baru_manual=rs2.getBoolean("inacbg_klaim_baru_manual");
                        var.inacbg_coder_nik=rs2.getBoolean("inacbg_coder_nik");
                        var.mutasi_berkas=rs2.getBoolean("mutasi_berkas");
                        var.akun_piutang=rs2.getBoolean("akun_piutang");
                        var.harian_kso=rs2.getBoolean("harian_kso");
                        var.bulanan_kso=rs2.getBoolean("bulanan_kso");                        
                        var.harian_menejemen=rs2.getBoolean("harian_menejemen");
                        var.bulanan_menejemen=rs2.getBoolean("bulanan_menejemen");
                        var.inhealth_cek_eligibilitas=rs2.getBoolean("inhealth_cek_eligibilitas");
                        var.inhealth_referensi_jenpel_ruang_rawat=rs2.getBoolean("inhealth_referensi_jenpel_ruang_rawat");
                        var.inhealth_referensi_poli=rs2.getBoolean("inhealth_referensi_poli");
                        var.inhealth_referensi_faskes=rs2.getBoolean("inhealth_referensi_faskes");
                        var.inhealth_sjp=rs2.getBoolean("inhealth_sjp");
                        var.piutang_ralan=rs2.getBoolean("piutang_ralan");
                        var.piutang_ranap=rs2.getBoolean("piutang_ranap");
                        var.detail_piutang_penjab=rs2.getBoolean("detail_piutang_penjab");
                        var.lama_pelayanan_ralan=rs2.getBoolean("lama_pelayanan_ralan");
                        var.catatan_pasien=rs2.getBoolean("catatan_pasien");
                        var.rl4b=rs2.getBoolean("rl4b");                        
                        var.rl4asebab=rs2.getBoolean("rl4asebab"); 
                        var.rl4bsebab=rs2.getBoolean("rl4bsebab"); 
                        var.data_HAIs=rs2.getBoolean("data_HAIs");
                        var.harian_HAIs=rs2.getBoolean("harian_HAIs");
                        var.bulanan_HAIs=rs2.getBoolean("bulanan_HAIs");                        
                        var.hitung_bor=rs2.getBoolean("hitung_bor");
                        var.perusahaan_pasien=rs2.getBoolean("perusahaan_pasien");
                        var.resep_dokter=rs2.getBoolean("resep_dokter");
                        var.lama_pelayanan_apotek=rs2.getBoolean("lama_pelayanan_apotek");
                        var.hitung_alos=rs2.getBoolean("hitung_alos");
                        var.detail_tindakan=rs2.getBoolean("detail_tindakan");
                        var.rujukan_poli_internal=rs2.getBoolean("rujukan_poli_internal");
                        var.rekap_poli_anak=rs2.getBoolean("rekap_poli_anak");
                        var.grafik_kunjungan_poli=rs2.getBoolean("grafik_kunjungan_poli");
                        var.grafik_kunjungan_perdokter=rs2.getBoolean("grafik_kunjungan_perdokter");
                        var.grafik_kunjungan_perpekerjaan=rs2.getBoolean("grafik_kunjungan_perpekerjaan");
                        var.grafik_kunjungan_perpendidikan=rs2.getBoolean("grafik_kunjungan_perpendidikan");
                        var.grafik_kunjungan_pertahun=rs2.getBoolean("grafik_kunjungan_pertahun");
                        var.berkas_digital_perawatan=rs2.getBoolean("berkas_digital_perawatan");
                        var.penyakit_menular_ranap=rs2.getBoolean("penyakit_menular_ranap");
                        var.penyakit_menular_ralan=rs2.getBoolean("penyakit_menular_ralan");
                        var.grafik_kunjungan_perbulan=rs2.getBoolean("grafik_kunjungan_perbulan");                        
                        var.grafik_kunjungan_pertanggal=rs2.getBoolean("grafik_kunjungan_pertanggal");
                        var.grafik_kunjungan_demografi=rs2.getBoolean("grafik_kunjungan_demografi");
                        var.grafik_kunjungan_statusdaftartahun=rs2.getBoolean("grafik_kunjungan_statusdaftartahun");
                        var.grafik_kunjungan_statusdaftartahun2=rs2.getBoolean("grafik_kunjungan_statusdaftartahun2");                        
                        var.grafik_kunjungan_statusdaftarbulan=rs2.getBoolean("grafik_kunjungan_statusdaftarbulan");
                        var.grafik_kunjungan_statusdaftarbulan2=rs2.getBoolean("grafik_kunjungan_statusdaftarbulan2");
                        var.grafik_kunjungan_statusdaftartanggal=rs2.getBoolean("grafik_kunjungan_statusdaftartanggal");
                        var.grafik_kunjungan_statusdaftartanggal2=rs2.getBoolean("grafik_kunjungan_statusdaftartanggal2");
                        var.grafik_kunjungan_statusbataltahun=rs2.getBoolean("grafik_kunjungan_statusbataltahun");
                        var.grafik_kunjungan_statusbatalbulan=rs2.getBoolean("grafik_kunjungan_statusbatalbulan");
                        var.pcare_cek_penyakit=rs2.getBoolean("pcare_cek_penyakit");
                        var.grafik_kunjungan_statusbataltanggal=rs2.getBoolean("grafik_kunjungan_statusbataltanggal");
                        var.kategori_barang=rs2.getBoolean("kategori_barang");
                        var.golongan_barang=rs2.getBoolean("golongan_barang");
                        var.pemberian_obat_pertanggal=rs2.getBoolean("pemberian_obat_pertanggal");
                        var.penjualan_obat_pertanggal=rs2.getBoolean("penjualan_obat_pertanggal");
                        var.pcare_cek_kesadaran=rs2.getBoolean("pcare_cek_kesadaran");                        
                        var.pembatalan_periksa_dokter=rs2.getBoolean("pembatalan_periksa_dokter");
                        var.pembayaran_per_unit=rs2.getBoolean("pembayaran_per_unit");
                        var.rekap_pembayaran_per_unit=rs2.getBoolean("rekap_pembayaran_per_unit");                        
                        var.grafik_kunjungan_percarabayar=rs2.getBoolean("grafik_kunjungan_percarabayar");
                        var.ipsrs_pengadaan_pertanggal=rs2.getBoolean("ipsrs_pengadaan_pertanggal");
                        var.ipsrs_stokkeluar_pertanggal=rs2.getBoolean("ipsrs_stokkeluar_pertanggal");
                        var.grafik_kunjungan_ranaptahun=rs2.getBoolean("grafik_kunjungan_ranaptahun");
                        var.pcare_cek_rujukan=rs2.getBoolean("pcare_cek_rujukan");
                        var.grafik_lab_ralantahun=rs2.getBoolean("grafik_lab_ralantahun");                        
                        var.grafik_rad_ralantahun=rs2.getBoolean("grafik_rad_ralantahun");
                        var.cek_entry_ralan=rs2.getBoolean("cek_entry_ralan");
                        var.inacbg_klaim_baru_manual2=rs2.getBoolean("inacbg_klaim_baru_manual2");
                        var.permintaan_medis=rs2.getBoolean("permintaan_medis");
                        var.rekap_permintaan_medis=rs2.getBoolean("rekap_permintaan_medis");
                        var.surat_pemesanan_medis=rs2.getBoolean("surat_pemesanan_medis");
                        var.permintaan_non_medis=rs2.getBoolean("permintaan_non_medis");
                        var.rekap_permintaan_non_medis=rs2.getBoolean("rekap_permintaan_non_medis");
                        var.surat_pemesanan_non_medis=rs2.getBoolean("surat_pemesanan_non_medis");
                        var.grafik_per_perujuk=rs2.getBoolean("grafik_per_perujuk");
                        var.bpjs_cek_prosedur=rs2.getBoolean("bpjs_cek_prosedur");
                        var.bpjs_cek_kelas_rawat=rs2.getBoolean("bpjs_cek_kelas_rawat");
                        var.bpjs_cek_dokter=rs2.getBoolean("bpjs_cek_dokter");
                        var.bpjs_cek_spesialistik=rs2.getBoolean("bpjs_cek_spesialistik");
                        var.bpjs_cek_ruangrawat=rs2.getBoolean("bpjs_cek_ruangrawat");                        
                        var.bpjs_cek_carakeluar=rs2.getBoolean("bpjs_cek_carakeluar");
                        var.bpjs_cek_pasca_pulang=rs2.getBoolean("bpjs_cek_pasca_pulang");
                        var.detail_tindakan_okvk=rs2.getBoolean("detail_tindakan_okvk");
                        var.billing_parsial=rs2.getBoolean("billing_parsial");
                        var.bpjs_cek_nomor_rujukan_rs=rs2.getBoolean("bpjs_cek_nomor_rujukan_rs");
                        var.bpjs_cek_rujukan_kartu_pcare=rs2.getBoolean("bpjs_cek_rujukan_kartu_pcare");
                        var.bpjs_cek_rujukan_kartu_rs=rs2.getBoolean("bpjs_cek_rujukan_kartu_rs");
                        var.akses_depo_obat=rs2.getBoolean("akses_depo_obat");
                        var.bpjs_rujukan_keluar=rs2.getBoolean("bpjs_rujukan_keluar");
                        var.grafik_lab_ralanbulan=rs2.getBoolean("grafik_lab_ralanbulan");
                        var.pengeluaran_stok_apotek=rs2.getBoolean("pengeluaran_stok_apotek");
                        var.grafik_rad_ralanbulan=rs2.getBoolean("grafik_rad_ralanbulan");
                        var.detailjmdokter2=rs2.getBoolean("detailjmdokter2");
                        var.pengaduan_pasien=rs2.getBoolean("pengaduan_pasien");
                        var.grafik_lab_ralanhari=rs2.getBoolean("grafik_lab_ralanhari");
                        var.grafik_rad_ralanhari=rs2.getBoolean("grafik_rad_ralanhari");
                        var.sensus_harian_ralan=rs2.getBoolean("sensus_harian_ralan");
                        var.metode_racik=rs2.getBoolean("metode_racik");
                        var.pembayaran_akun_bayar=rs2.getBoolean("pembayaran_akun_bayar");
                        var.pengguna_obat_resep=rs2.getBoolean("pengguna_obat_resep");
                        var.rekap_pemesanan=rs2.getBoolean("rekap_pemesanan");
                        var.master_berkas_pegawai=rs2.getBoolean("master_berkas_pegawai");
                        var.berkas_kepegawaian=rs2.getBoolean("berkas_kepegawaian");
                        var.riwayat_jabatan=rs2.getBoolean("riwayat_jabatan");
                        var.riwayat_pendidikan=rs2.getBoolean("riwayat_pendidikan");
                        var.riwayat_naik_gaji=rs2.getBoolean("riwayat_naik_gaji");
                        var.kegiatan_ilmiah=rs2.getBoolean("kegiatan_ilmiah");
                        var.riwayat_penghargaan=rs2.getBoolean("riwayat_penghargaan");
                        var.riwayat_penelitian=rs2.getBoolean("riwayat_penelitian");
                        var.penerimaan_non_medis=rs2.getBoolean("penerimaan_non_medis");
                        var.bayar_pesan_non_medis=rs2.getBoolean("bayar_pesan_non_medis");
                        var.hutang_barang_non_medis=rs2.getBoolean("hutang_barang_non_medis");
                        var.rekap_pemesanan_non_medis=rs2.getBoolean("rekap_pemesanan_non_medis");                        
                        var.insiden_keselamatan=rs2.getBoolean("insiden_keselamatan");
                        var.insiden_keselamatan_pasien=rs2.getBoolean("insiden_keselamatan_pasien");
                        var.grafik_ikp_pertahun=rs2.getBoolean("grafik_ikp_pertahun");
                        var.grafik_ikp_perbulan=rs2.getBoolean("grafik_ikp_perbulan");
                        var.grafik_ikp_pertanggal=rs2.getBoolean("grafik_ikp_pertanggal");
                        var.riwayat_data_batch=rs2.getBoolean("riwayat_data_batch");
                        var.grafik_ikp_jenis=rs2.getBoolean("grafik_ikp_jenis");
                        var.grafik_ikp_dampak=rs2.getBoolean("grafik_ikp_dampak");
                        var.piutang_akun_piutang=rs2.getBoolean("piutang_akun_piutang");
                        var.grafik_kunjungan_per_agama=rs2.getBoolean("grafik_kunjungan_per_agama");
                        var.grafik_kunjungan_per_umur=rs2.getBoolean("grafik_kunjungan_per_umur");
                        var.suku_bangsa=rs2.getBoolean("suku_bangsa");
                        var.bahasa_pasien=rs2.getBoolean("bahasa_pasien");
                        var.golongan_tni=rs2.getBoolean("golongan_tni");
                        var.satuan_tni=rs2.getBoolean("satuan_tni");
                        var.jabatan_tni=rs2.getBoolean("jabatan_tni");
                        var.pangkat_tni=rs2.getBoolean("pangkat_tni");
                        var.golongan_polri=rs2.getBoolean("golongan_polri");
                        var.satuan_polri=rs2.getBoolean("satuan_polri");
                        var.jabatan_polri=rs2.getBoolean("jabatan_polri");
                        var.pangkat_polri=rs2.getBoolean("pangkat_polri");
                        var.cacat_fisik=rs2.getBoolean("cacat_fisik");
                        var.grafik_kunjungan_suku=rs2.getBoolean("grafik_kunjungan_suku");
                        var.grafik_kunjungan_bahasa=rs2.getBoolean("grafik_kunjungan_bahasa");
                        var.booking_operasi=rs2.getBoolean("booking_operasi");
                        var.mapping_poli_bpjs=rs2.getBoolean("mapping_poli_bpjs");
                        var.grafik_kunjungan_per_cacat=rs2.getBoolean("grafik_kunjungan_per_cacat");
                        var.barang_cssd=rs2.getBoolean("barang_cssd");
                        var.skdp_bpjs=rs2.getBoolean("skdp_bpjs");
                        var.booking_registrasi=rs2.getBoolean("booking_registrasi");
                        var.bpjs_cek_propinsi=rs2.getBoolean("bpjs_cek_propinsi");
                        var.bpjs_cek_kabupaten=rs2.getBoolean("bpjs_cek_kabupaten");
                        var.bpjs_cek_kecamatan=rs2.getBoolean("bpjs_cek_kecamatan");
                        var.bpjs_cek_dokterdpjp=rs2.getBoolean("bpjs_cek_dokterdpjp");
                        var.bpjs_cek_riwayat_rujukanrs=rs2.getBoolean("bpjs_cek_riwayat_rujukanrs");
                        var.bpjs_cek_tanggal_rujukan=rs2.getBoolean("bpjs_cek_tanggal_rujukan");
                        var.permintaan_lab=rs2.getBoolean("permintaan_lab");
                        var.permintaan_radiologi=rs2.getBoolean("permintaan_radiologi");
                        var.surat_indeks=rs2.getBoolean("surat_indeks");
                        var.surat_map=rs2.getBoolean("surat_map");
                        var.surat_almari=rs2.getBoolean("surat_almari");
                        var.surat_rak=rs2.getBoolean("surat_rak");
                        var.surat_ruang=rs2.getBoolean("surat_ruang");
                        var.surat_klasifikasi=rs2.getBoolean("surat_klasifikasi");
                        var.surat_status=rs2.getBoolean("surat_status");
                        var.surat_sifat=rs2.getBoolean("surat_sifat");
                        var.surat_balas=rs2.getBoolean("surat_balas");
                        var.surat_masuk=rs2.getBoolean("surat_masuk");
                        var.pcare_cek_dokter=rs2.getBoolean("pcare_cek_dokter");
                        var.pcare_cek_poli=rs2.getBoolean("pcare_cek_poli");
                        var.pcare_cek_provider=rs2.getBoolean("pcare_cek_provider");
                        var.pcare_cek_statuspulang=rs2.getBoolean("pcare_cek_statuspulang");
                        var.pcare_cek_spesialis=rs2.getBoolean("pcare_cek_spesialis");
                        var.pcare_cek_subspesialis=rs2.getBoolean("pcare_cek_subspesialis");
                        var.pcare_cek_sarana=rs2.getBoolean("pcare_cek_sarana");
                        var.pcare_cek_khusus=rs2.getBoolean("pcare_cek_khusus");
                        var.pcare_cek_obat=rs2.getBoolean("pcare_cek_obat");
                        var.pcare_cek_tindakan=rs2.getBoolean("pcare_cek_tindakan");
                        var.pcare_cek_faskessubspesialis=rs2.getBoolean("pcare_cek_faskessubspesialis");
                        var.pcare_cek_faskesalihrawat=rs2.getBoolean("pcare_cek_faskesalihrawat");
                        var.pcare_cek_faskesthalasemia=rs2.getBoolean("pcare_cek_faskesthalasemia");
                        var.pcare_mapping_obat=rs2.getBoolean("pcare_mapping_obat");
                        var.pcare_mapping_tindakan=rs2.getBoolean("pcare_mapping_tindakan");
                        var.pcare_club_prolanis=rs2.getBoolean("pcare_club_prolanis");
                        var.pcare_mapping_poli=rs2.getBoolean("pcare_mapping_poli");
                        var.pcare_kegiatan_kelompok=rs2.getBoolean("pcare_kegiatan_kelompok");
                        var.pcare_mapping_tindakan_ranap=rs2.getBoolean("pcare_mapping_tindakan_ranap");
                        var.pcare_peserta_kegiatan_kelompok=rs2.getBoolean("pcare_peserta_kegiatan_kelompok");
                        var.sirkulasi_obat3=rs2.getBoolean("sirkulasi_obat3");
                        var.bridging_pcare_daftar=rs2.getBoolean("bridging_pcare_daftar");
                        var.pcare_mapping_dokter=rs2.getBoolean("pcare_mapping_dokter");
                        var.ranap_per_ruang=rs2.getBoolean("ranap_per_ruang");
                        var.penyakit_ranap_cara_bayar=rs2.getBoolean("penyakit_ranap_cara_bayar");
                        var.anggota_militer_dirawat=rs2.getBoolean("anggota_militer_dirawat");
                        var.set_input_parsial=rs2.getBoolean("set_input_parsial");
                        var.lama_pelayanan_radiologi=rs2.getBoolean("lama_pelayanan_radiologi");
                        var.lama_pelayanan_lab=rs2.getBoolean("lama_pelayanan_lab");
                        var.bpjs_cek_sep=rs2.getBoolean("bpjs_cek_sep");
                        var.catatan_perawatan=rs2.getBoolean("catatan_perawatan");
                        var.surat_keluar=rs2.getBoolean("surat_keluar");
                        var.kegiatan_farmasi=rs2.getBoolean("kegiatan_farmasi");
                        var.stok_opname_logistik=rs2.getBoolean("stok_opname_logistik");
                        var.sirkulasi_non_medis=rs2.getBoolean("sirkulasi_non_medis");
                        var.rekap_lab_pertahun=rs2.getBoolean("rekap_lab_pertahun");
                        var.perujuk_lab_pertahun=rs2.getBoolean("perujuk_lab_pertahun");
                        var.rekap_radiologi_pertahun=rs2.getBoolean("rekap_radiologi_pertahun");
                        var.perujuk_radiologi_pertahun=rs2.getBoolean("perujuk_radiologi_pertahun");
                        var.jumlah_porsi_diet=rs2.getBoolean("jumlah_porsi_diet");
                        var.jumlah_macam_diet=rs2.getBoolean("jumlah_macam_diet");
                        var.payment_point2=rs2.getBoolean("payment_point2");
                        var.pembayaran_akun_bayar2=rs2.getBoolean("pembayaran_akun_bayar2");
                        var.hapus_nota_salah=rs2.getBoolean("hapus_nota_salah");
                        var.hais_perbangsal=rs2.getBoolean("hais_perbangsal");
                        var.ppn_obat=rs2.getBoolean("ppn_obat");
                        var.saldo_akun_perbulan=rs2.getBoolean("saldo_akun_perbulan");
                        var.display_apotek=rs2.getBoolean("display_apotek");
                        var.sisrute_referensi_faskes=rs2.getBoolean("sisrute_referensi_faskes");
                        var.sisrute_referensi_alasanrujuk=rs2.getBoolean("sisrute_referensi_alasanrujuk");
                        var.sisrute_referensi_diagnosa=rs2.getBoolean("sisrute_referensi_diagnosa");
                        var.sisrute_rujukan_masuk=rs2.getBoolean("sisrute_rujukan_masuk");
                        var.sisrute_rujukan_keluar=rs2.getBoolean("sisrute_rujukan_keluar");
                        var.bpjs_cek_skdp=rs2.getBoolean("bpjs_cek_skdp");
                        var.data_batch=rs2.getBoolean("data_batch");
                        var.kunjungan_permintaan_lab=rs2.getBoolean("kunjungan_permintaan_lab");
                        var.kunjungan_permintaan_lab2=rs2.getBoolean("kunjungan_permintaan_lab2");
                        var.kunjungan_permintaan_radiologi=rs2.getBoolean("kunjungan_permintaan_radiologi");
                        var.kunjungan_permintaan_radiologi2=rs2.getBoolean("kunjungan_permintaan_radiologi2");
                        var.pcare_pemberian_obat=rs2.getBoolean("pcare_pemberian_obat");
                        var.pcare_pemberian_tindakan=rs2.getBoolean("pcare_pemberian_tindakan");
                        var.pembayaran_akun_bayar3=rs2.getBoolean("pembayaran_akun_bayar3");
                        var.password_asuransi=rs2.getBoolean("password_asuransi");
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
                        var.darurat_stok=false;
                        var.retensi_rm=false;
                        var.temporary_presensi=false;
                        var.jurnal_harian=false;
                        var.sirkulasi_obat2=false;
                        var.edit_registrasi=false;
                        var.bpjs_referensi_diagnosa=false;
                        var.bpjs_referensi_poli=false;
                        var.industrifarmasi=false;
                        var.harian_js=false;
                        var.bulanan_js=false;
                        var.harian_paket_bhp=false;
                        var.bulanan_paket_bhp=false;
                        var.piutang_pasien2=false;
                        var.bpjs_referensi_faskes=false;
                        var.bpjs_sep=false;
                        var.pengambilan_utd=false;
                        var.tarif_utd=false;
                        var.pengambilan_utd2=false;
                        var.utd_medis_rusak=false;
                        var.pengambilan_penunjang_utd=false;
                        var.pengambilan_penunjang_utd2=false;
                        var.utd_penunjang_rusak=false;
                        var.suplier_penunjang=false;
                        var.utd_donor=false;
                        var.bpjs_monitoring_klaim=false;
                        var.utd_cekal_darah=false;
                        var.utd_komponen_darah=false;
                        var.utd_stok_darah=false;
                        var.utd_pemisahan_darah=false;
                        var.harian_kamar=false;
                        var.rincian_piutang_pasien=false;
                        var.keuntungan_beri_obat_nonpiutang=false;
                        var.reklasifikasi_ralan=false;
                        var.reklasifikasi_ranap=false;
                        var.utd_penyerahan_darah=false;
                        var.hutang_obat=false;
                        var.riwayat_obat_alkes_bhp=false;
                        var.sensus_harian_poli=false;
                        var.rl4a=false;
                        var.aplicare_referensi_kamar=false;
                        var.aplicare_ketersediaan_kamar=false;
                        var.inacbg_klaim_baru_otomatis=false;
                        var.inacbg_klaim_baru_manual=false;
                        var.inacbg_coder_nik=false;
                        var.mutasi_berkas=false;
                        var.akun_piutang=false;
                        var.harian_kso=false;
                        var.bulanan_kso=false;                                    
                        var.harian_menejemen=false;
                        var.bulanan_menejemen=false;
                        var.inhealth_cek_eligibilitas=false;
                        var.inhealth_referensi_jenpel_ruang_rawat=false;
                        var.inhealth_referensi_poli=false;
                        var.inhealth_referensi_faskes=false;
                        var.inhealth_sjp=false;
                        var.piutang_ralan=false;
                        var.piutang_ranap=false;
                        var.detail_piutang_penjab=false;
                        var.lama_pelayanan_ralan=false;
                        var.catatan_pasien=false;
                        var.rl4b=false;              
                        var.rl4asebab=false;
                        var.rl4bsebab=false;
                        var.data_HAIs=false;
                        var.harian_HAIs=false;
                        var.bulanan_HAIs=false;
                        var.hitung_bor=false;
                        var.perusahaan_pasien=false;
                        var.resep_dokter=false;
                        var.lama_pelayanan_apotek=false;
                        var.hitung_alos=false;
                        var.detail_tindakan=false;
                        var.rujukan_poli_internal=false;
                        var.rekap_poli_anak=false;
                        var.grafik_kunjungan_poli=false;
                        var.grafik_kunjungan_perdokter=false;
                        var.grafik_kunjungan_perpekerjaan=false;
                        var.grafik_kunjungan_perpendidikan=false;
                        var.grafik_kunjungan_pertahun=false;
                        var.berkas_digital_perawatan=false;
                        var.penyakit_menular_ranap=false;
                        var.penyakit_menular_ralan=false;
                        var.grafik_kunjungan_perbulan=false;                    
                        var.grafik_kunjungan_pertanggal=false;
                        var.grafik_kunjungan_demografi=false;
                        var.grafik_kunjungan_statusdaftartahun=false;
                        var.grafik_kunjungan_statusdaftartahun2=false;                        
                        var.grafik_kunjungan_statusdaftarbulan=false;
                        var.grafik_kunjungan_statusdaftarbulan2=false;
                        var.grafik_kunjungan_statusdaftartanggal=false;
                        var.grafik_kunjungan_statusdaftartanggal2=false;
                        var.grafik_kunjungan_statusbataltahun=false;
                        var.grafik_kunjungan_statusbatalbulan=false;
                        var.pcare_cek_penyakit=false;
                        var.grafik_kunjungan_statusbataltanggal=false;
                        var.kategori_barang=false;
                        var.golongan_barang=false;
                        var.pemberian_obat_pertanggal=false;
                        var.penjualan_obat_pertanggal=false;
                        var.pcare_cek_kesadaran=false;
                        var.pembatalan_periksa_dokter=false;
                        var.pembayaran_per_unit=false;
                        var.rekap_pembayaran_per_unit=false;
                        var.grafik_kunjungan_percarabayar=false;
                        var.ipsrs_pengadaan_pertanggal=false;
                        var.ipsrs_stokkeluar_pertanggal=false;
                        var.grafik_kunjungan_ranaptahun=false;
                        var.pcare_cek_rujukan=false;
                        var.grafik_lab_ralantahun=false;
                        var.grafik_rad_ralantahun=false;
                        var.cek_entry_ralan=false;
                        var.inacbg_klaim_baru_manual2=false;
                        var.permintaan_medis=false;
                        var.rekap_permintaan_medis=false;
                        var.surat_pemesanan_medis=false;
                        var.permintaan_non_medis=false;
                        var.rekap_permintaan_non_medis=false;
                        var.surat_pemesanan_non_medis=false;
                        var.grafik_per_perujuk=false;
                        var.bpjs_cek_prosedur=false;
                        var.bpjs_cek_kelas_rawat=false;
                        var.bpjs_cek_dokter=false;
                        var.bpjs_cek_spesialistik=false;
                        var.bpjs_cek_ruangrawat=false;
                        var.bpjs_cek_carakeluar=false;
                        var.bpjs_cek_pasca_pulang=false;
                        var.detail_tindakan_okvk=false;
                        var.billing_parsial=false;
                        var.bpjs_cek_nomor_rujukan_rs=false;
                        var.bpjs_cek_rujukan_kartu_pcare=false;
                        var.bpjs_cek_rujukan_kartu_rs=false;
                        var.akses_depo_obat=false;
                        var.bpjs_rujukan_keluar=false;
                        var.grafik_lab_ralanbulan=false;
                        var.pengeluaran_stok_apotek=false;
                        var.grafik_rad_ralanbulan=false;
                        var.detailjmdokter2=false;
                        var.pengaduan_pasien=false;
                        var.grafik_lab_ralanhari=false;
                        var.grafik_rad_ralanhari=false;
                        var.sensus_harian_ralan=false;
                        var.metode_racik=false;
                        var.pembayaran_akun_bayar=false;
                        var.pengguna_obat_resep=false;
                        var.rekap_pemesanan=false;
                        var.master_berkas_pegawai=false;
                        var.berkas_kepegawaian=false;
                        var.riwayat_jabatan=false;
                        var.riwayat_pendidikan=false;
                        var.riwayat_naik_gaji=false;
                        var.kegiatan_ilmiah=false;
                        var.riwayat_penghargaan=false;
                        var.riwayat_penelitian=false;
                        var.penerimaan_non_medis=false;
                        var.bayar_pesan_non_medis=false;
                        var.hutang_barang_non_medis=false;
                        var.rekap_pemesanan_non_medis=false;                      
                        var.insiden_keselamatan=false;
                        var.insiden_keselamatan_pasien=false;
                        var.grafik_ikp_pertahun=false;
                        var.grafik_ikp_perbulan=false;
                        var.grafik_ikp_pertanggal=false;
                        var.riwayat_data_batch=false;
                        var.grafik_ikp_jenis=false;
                        var.grafik_ikp_dampak=false;
                        var.piutang_akun_piutang=false;
                        var.grafik_kunjungan_per_agama=false;
                        var.grafik_kunjungan_per_umur=false;
                        var.suku_bangsa=false;
                        var.bahasa_pasien=false;
                        var.golongan_tni=false;
                        var.satuan_tni=false;
                        var.jabatan_tni=false;
                        var.pangkat_tni=false;
                        var.golongan_polri=false;
                        var.satuan_polri=false;
                        var.jabatan_polri=false;
                        var.pangkat_polri=false;
                        var.cacat_fisik=false;
                        var.grafik_kunjungan_suku=false;
                        var.grafik_kunjungan_bahasa=false;
                        var.booking_operasi=false;
                        var.mapping_poli_bpjs=false;
                        var.grafik_kunjungan_per_cacat=false;
                        var.barang_cssd=false;
                        var.skdp_bpjs=false;
                        var.booking_registrasi=false;
                        var.bpjs_cek_propinsi=false;
                        var.bpjs_cek_kabupaten=false;
                        var.bpjs_cek_kecamatan=false;
                        var.bpjs_cek_dokterdpjp=false;
                        var.bpjs_cek_riwayat_rujukanrs=false;
                        var.bpjs_cek_tanggal_rujukan=false;
                        var.permintaan_lab=false;
                        var.permintaan_radiologi=false;
                        var.surat_indeks=false;
                        var.surat_map=false;
                        var.surat_almari=false;
                        var.surat_rak=false;
                        var.surat_ruang=false;
                        var.surat_klasifikasi=false;
                        var.surat_status=false;
                        var.surat_sifat=false;
                        var.surat_balas=false;
                        var.surat_masuk=false;
                        var.pcare_cek_dokter=false;
                        var.pcare_cek_poli=false;
                        var.pcare_cek_provider=false;
                        var.pcare_cek_statuspulang=false;
                        var.pcare_cek_spesialis=false;
                        var.pcare_cek_subspesialis=false;
                        var.pcare_cek_sarana=false;
                        var.pcare_cek_khusus=false;
                        var.pcare_cek_obat=false;
                        var.pcare_cek_tindakan=false;
                        var.pcare_cek_faskessubspesialis=false;
                        var.pcare_cek_faskesalihrawat=false;
                        var.pcare_cek_faskesthalasemia=false;
                        var.pcare_mapping_obat=false;
                        var.pcare_mapping_tindakan=false;
                        var.pcare_club_prolanis=false;
                        var.pcare_mapping_poli=false;
                        var.pcare_kegiatan_kelompok=false;
                        var.pcare_mapping_tindakan_ranap=false;
                        var.pcare_peserta_kegiatan_kelompok=false;
                        var.sirkulasi_obat3=false;
                        var.bridging_pcare_daftar=false;
                        var.pcare_mapping_dokter=false;
                        var.ranap_per_ruang=false;
                        var.penyakit_ranap_cara_bayar=false;
                        var.anggota_militer_dirawat=false;
                        var.set_input_parsial=false;
                        var.lama_pelayanan_radiologi=false;
                        var.lama_pelayanan_lab=false;
                        var.bpjs_cek_sep=false;
                        var.catatan_perawatan=false;
                        var.surat_keluar=false;
                        var.kegiatan_farmasi=false;
                        var.stok_opname_logistik=false;
                        var.sirkulasi_non_medis=false;
                        var.rekap_lab_pertahun=false;
                        var.perujuk_lab_pertahun=false;
                        var.rekap_radiologi_pertahun=false;
                        var.perujuk_radiologi_pertahun=false;
                        var.jumlah_porsi_diet=false;
                        var.jumlah_macam_diet=false;
                        var.payment_point2=false;
                        var.pembayaran_akun_bayar2=false;
                        var.hapus_nota_salah=false;
                        var.hais_perbangsal=false;
                        var.ppn_obat=false;
                        var.saldo_akun_perbulan=false;
                        var.display_apotek=false;
                        var.sisrute_referensi_faskes=false;
                        var.sisrute_referensi_alasanrujuk=false;
                        var.sisrute_referensi_diagnosa=false;
                        var.sisrute_rujukan_masuk=false;
                        var.sisrute_rujukan_keluar=false;
                        var.bpjs_cek_skdp=false;
                        var.data_batch=false;
                        var.kunjungan_permintaan_lab=false;
                        var.kunjungan_permintaan_lab2=false;
                        var.kunjungan_permintaan_radiologi=false;
                        var.kunjungan_permintaan_radiologi2=false;
                        var.pcare_pemberian_obat=false;
                        var.pcare_pemberian_tindakan=false;
                        var.pembayaran_akun_bayar3=false;
                        var.password_asuransi=false;
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
    public static void setpenjualan_obatfalse(){var.penjualan_obat=false;} 
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
    public static void setnamauser(String namauser){var.namauser=namauser;}
    public static String getnamauser(){return var.namauser;}   
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
    public static boolean getdarurat_stok(){return var.darurat_stok;}
    public static boolean getretensi_rm(){return var.retensi_rm;}
    public static boolean gettemporary_presensi(){return var.temporary_presensi;}
    public static boolean getjurnal_harian(){return var.jurnal_harian;}
    public static boolean getsirkulasi_obat2(){return var.sirkulasi_obat2;}
    public static boolean getedit_registrasi(){return var.edit_registrasi;}
    public static boolean getbpjs_referensi_diagnosa(){return var.bpjs_referensi_diagnosa;}
    public static boolean getbpjs_referensi_poli(){return var.bpjs_referensi_poli;}
    public static boolean getindustrifarmasi(){return var.industrifarmasi;}
    public static boolean getharian_js(){return var.harian_js;}
    public static boolean getbulanan_js(){return var.bulanan_js;}
    public static boolean getharian_paket_bhp(){return var.harian_paket_bhp;}
    public static boolean getbulanan_paket_bhp(){return var.bulanan_paket_bhp;}
    public static boolean getpiutang_pasien2(){return var.piutang_pasien2;}
    public static boolean getbpjs_referensi_faskes(){return var.bpjs_referensi_faskes;} 
    public static boolean getbpjs_sep(){return var.bpjs_sep;} 
    public static boolean getpengambilan_utd(){return var.pengambilan_utd;} 
    public static boolean gettarif_utd(){return var.tarif_utd;} 
    public static boolean getpengambilan_utd2(){return var.pengambilan_utd2;}  
    public static boolean getutd_medis_rusak(){return var.utd_medis_rusak;}  
    public static boolean getpengambilan_penunjang_utd(){return var.pengambilan_penunjang_utd;}  
    public static boolean getpengambilan_penunjang_utd2(){return var.pengambilan_penunjang_utd2;}  
    public static boolean getutd_penunjang_rusak(){return var.utd_penunjang_rusak;}  
    public static boolean getsuplier_penunjang(){return var.suplier_penunjang;}  
    public static boolean getutd_donor(){return var.utd_donor;}  
    public static boolean getbpjs_monitoring_klaim(){return var.bpjs_monitoring_klaim;}  
    public static boolean getutd_cekal_darah(){return var.utd_cekal_darah;}  
    public static boolean getutd_komponen_darah(){return var.utd_komponen_darah;}  
    public static boolean getutd_stok_darah(){return var.utd_stok_darah;}  
    public static boolean getutd_pemisahan_darah(){return var.utd_pemisahan_darah;}  
    public static boolean getharian_kamar(){return var.harian_kamar;}  
    public static boolean getrincian_piutang_pasien(){return var.rincian_piutang_pasien;}  
    public static boolean getkeuntungan_beri_obat_nonpiutang(){return var.keuntungan_beri_obat_nonpiutang;}  
    public static boolean getreklasifikasi_ralan(){return var.reklasifikasi_ralan;}  
    public static boolean getreklasifikasi_ranap(){return var.reklasifikasi_ranap;}  
    public static boolean getutd_penyerahan_darah(){return var.utd_penyerahan_darah;} 
    public static void setutd_penyerahan_darahfalse(){var.utd_penyerahan_darah=false;} 
    public static boolean gethutang_obat(){return var.hutang_obat;}  
    public static boolean getriwayat_obat_alkes_bhp(){return var.riwayat_obat_alkes_bhp;}
    public static boolean getsensus_harian_poli(){return var.sensus_harian_poli;}
    public static boolean getrl4a(){return var.rl4a;}
    public static boolean getaplicare_referensi_kamar(){return var.aplicare_referensi_kamar;}
    public static boolean getaplicare_ketersediaan_kamar(){return var.aplicare_ketersediaan_kamar;}
    public static boolean getinacbg_klaim_baru_otomatis(){return var.inacbg_klaim_baru_otomatis;}
    public static boolean getinacbg_klaim_baru_manual(){return var.inacbg_klaim_baru_manual;}
    public static boolean getinacbg_coder_nik(){return var.inacbg_coder_nik;}
    public static boolean getmutasi_berkas(){return var.mutasi_berkas;}
    public static boolean getakun_piutang(){return var.akun_piutang;}
    public static boolean getharian_kso(){return var.harian_kso;}
    public static boolean getbulanan_kso(){return var.bulanan_kso;}
    public static boolean getharian_menejemen(){return var.harian_menejemen;}
    public static boolean getbulanan_menejemen(){return var.bulanan_menejemen;}
    public static boolean getinhealth_cek_eligibilitas(){return var.inhealth_cek_eligibilitas;}
    public static boolean getinhealth_referensi_jenpel_ruang_rawat(){return var.inhealth_referensi_jenpel_ruang_rawat;}
    public static boolean getinhealth_referensi_poli(){return var.inhealth_referensi_poli;}
    public static boolean getinhealth_referensi_faskes(){return var.inhealth_referensi_faskes;}
    public static boolean getinhealth_sjp(){return var.inhealth_sjp;}
    public static boolean getpiutang_ralan(){return var.piutang_ralan;}
    public static boolean getpiutang_ranap(){return var.piutang_ranap;}
    public static boolean getdetail_piutang_penjab(){return var.detail_piutang_penjab;}
    public static boolean getlama_pelayanan_ralan(){return var.lama_pelayanan_ralan;}
    public static boolean getcatatan_pasien(){return var.catatan_pasien;}
    public static boolean getrl4b(){return var.rl4b;}
    public static boolean getrl4asebab(){return var.rl4asebab;}
    public static boolean getrl4bsebab(){return var.rl4bsebab;}
    public static boolean getdata_HAIs(){return var.data_HAIs;}
    public static boolean getharian_HAIs(){return var.harian_HAIs;}
    public static boolean getbulanan_HAIs(){return var.bulanan_HAIs;}
    public static boolean gethitung_bor(){return var.hitung_bor;}
    public static boolean getperusahaan_pasien(){return var.perusahaan_pasien;}
    public static boolean getresep_dokter(){return var.resep_dokter;}
    public static void setresep_dokterfalse(){var.resep_dokter=false;} 
    public static boolean getlama_pelayanan_apotek(){return var.lama_pelayanan_apotek;}
    public static boolean gethitung_alos(){return var.hitung_alos;}
    public static boolean getdetail_tindakan(){return var.detail_tindakan;}
    public static boolean getrujukan_poli_internal(){return var.rujukan_poli_internal;}
    public static boolean getrekap_poli_anak(){return var.rekap_poli_anak;}
    public static boolean getgrafik_kunjungan_poli(){return var.grafik_kunjungan_poli;}
    public static boolean getgrafik_kunjungan_perdokter(){return var.grafik_kunjungan_perdokter;}
    public static boolean getgrafik_kunjungan_perpekerjaan(){return var.grafik_kunjungan_perpekerjaan;}
    public static boolean getgrafik_kunjungan_perpendidikan(){return var.grafik_kunjungan_perpendidikan;}
    public static boolean getgrafik_kunjungan_pertahun(){return var.grafik_kunjungan_pertahun;}
    public static boolean getberkas_digital_perawatan(){return var.berkas_digital_perawatan;}
    public static boolean getpenyakit_menular_ranap(){return var.penyakit_menular_ranap;}
    public static boolean getpenyakit_menular_ralan(){return var.penyakit_menular_ralan;}
    public static boolean getgrafik_kunjungan_perbulan(){return var.grafik_kunjungan_perbulan;}
    public static boolean getgrafik_kunjungan_pertanggal(){return var.grafik_kunjungan_pertanggal;}
    public static boolean getgrafik_kunjungan_demografi(){return var.grafik_kunjungan_demografi;}
    public static boolean getgrafik_kunjungan_statusdaftartahun(){return var.grafik_kunjungan_statusdaftartahun;}
    public static boolean getgrafik_kunjungan_statusdaftartahun2(){return var.grafik_kunjungan_statusdaftartahun2;}                        
    public static boolean getgrafik_kunjungan_statusdaftarbulan(){return var.grafik_kunjungan_statusdaftarbulan;}  
    public static boolean getgrafik_kunjungan_statusdaftarbulan2(){return var.grafik_kunjungan_statusdaftarbulan2;} 
    public static boolean getgrafik_kunjungan_statusdaftartanggal(){return var.grafik_kunjungan_statusdaftartanggal;} 
    public static boolean getgrafik_kunjungan_statusdaftartanggal2(){return var.grafik_kunjungan_statusdaftartanggal2;} 
    public static boolean getgrafik_kunjungan_statusbataltahun(){return var.grafik_kunjungan_statusbataltahun;} 
    public static boolean getgrafik_kunjungan_statusbatalbulan(){return var.grafik_kunjungan_statusbatalbulan;}
    public static boolean getpcare_cek_penyakit(){return var.pcare_cek_penyakit;}
    public static boolean getgrafik_kunjungan_statusbataltanggal(){return var.grafik_kunjungan_statusbataltanggal;}
    public static boolean getkategori_barang(){return var.kategori_barang;}
    public static boolean getgolongan_barang(){return var.golongan_barang;}
    public static boolean getpemberian_obat_pertanggal(){return var.pemberian_obat_pertanggal;}
    public static boolean getpenjualan_obat_pertanggal(){return var.penjualan_obat_pertanggal;}
    public static boolean getpcare_cek_kesadaran(){return var.pcare_cek_kesadaran;}
    public static boolean getpembatalan_periksa_dokter(){return var.pembatalan_periksa_dokter;}
    public static boolean getpembayaran_per_unit(){return var.pembayaran_per_unit;}
    public static boolean getrekap_pembayaran_per_unit(){return var.rekap_pembayaran_per_unit;}
    public static boolean getgrafik_kunjungan_percarabayar(){return var.grafik_kunjungan_percarabayar;}
    public static boolean getipsrs_pengadaan_pertanggal(){return var.ipsrs_pengadaan_pertanggal;}
    public static boolean getipsrs_stokkeluar_pertanggal(){return var.ipsrs_stokkeluar_pertanggal;}
    public static boolean getgrafik_kunjungan_ranaptahun(){return var.grafik_kunjungan_ranaptahun;}
    public static boolean getpcare_cek_rujukan(){return var.pcare_cek_rujukan;}
    public static boolean getgrafik_lab_ralantahun(){return var.grafik_lab_ralantahun;}
    public static boolean getgrafik_rad_ralantahun(){return var.grafik_rad_ralantahun;}
    public static boolean getcek_entry_ralan(){return var.cek_entry_ralan;}
    public static boolean getinacbg_klaim_baru_manual2(){return var.inacbg_klaim_baru_manual2;}
    public static boolean getpermintaan_medis(){return var.permintaan_medis;}
    public static boolean getrekap_permintaan_medis(){return var.rekap_permintaan_medis;}
    public static boolean getsurat_pemesanan_medis(){return var.surat_pemesanan_medis;}
    public static boolean getpermintaan_non_medis(){return var.permintaan_non_medis;}
    public static boolean getrekap_permintaan_non_medis(){return var.rekap_permintaan_non_medis;}
    public static boolean getsurat_pemesanan_non_medis(){return var.surat_pemesanan_non_medis;}
    public static boolean getgrafik_per_perujuk(){return var.grafik_per_perujuk;}
    public static boolean getbpjs_cek_prosedur(){return var.bpjs_cek_prosedur;}
    public static boolean getbpjs_cek_kelas_rawat(){return var.bpjs_cek_kelas_rawat;}
    public static boolean getbpjs_cek_dokter(){return var.bpjs_cek_dokter;}
    public static boolean getbpjs_cek_spesialistik(){return var.bpjs_cek_spesialistik;}
    public static boolean getbpjs_cek_ruangrawat(){return var.bpjs_cek_ruangrawat;}
    public static boolean getbpjs_cek_carakeluar(){return  var.bpjs_cek_carakeluar;}
    public static boolean getbpjs_cek_pasca_pulang(){return var.bpjs_cek_pasca_pulang;} 
    public static boolean getdetail_tindakan_okvk(){return var.detail_tindakan_okvk;}
    public static boolean getbilling_parsial(){return var.billing_parsial;}
    public static boolean getbpjs_cek_nomor_rujukan_rs(){return var.bpjs_cek_nomor_rujukan_rs;}
    public static boolean getbpjs_cek_rujukan_kartu_pcare(){return var.bpjs_cek_rujukan_kartu_pcare;}
    public static boolean getbpjs_cek_rujukan_kartu_rs(){return var.bpjs_cek_rujukan_kartu_rs;}
    public static boolean getakses_depo_obat(){return var.akses_depo_obat;}
    public static boolean getbpjs_rujukan_keluar(){return var.bpjs_rujukan_keluar;}
    public static boolean getgrafik_lab_ralanbulan(){return var.grafik_lab_ralanbulan;}
    public static boolean getpengeluaran_stok_apotek(){return var.pengeluaran_stok_apotek;}
    public static boolean getgrafik_rad_ralanbulan(){return var.grafik_rad_ralanbulan;}
    public static boolean getdetailjmdokter2(){return var.detailjmdokter2;}
    public static boolean getpengaduan_pasien(){return var.pengaduan_pasien;}
    public static boolean getgrafik_lab_ralanhari(){return var.grafik_lab_ralanhari;}
    public static boolean getgrafik_rad_ralanhari(){return var.grafik_rad_ralanhari;}
    public static boolean getsensus_harian_ralan(){return var.sensus_harian_ralan;}
    public static boolean getmetode_racik(){return var.metode_racik;}
    public static boolean getpembayaran_akun_bayar(){return var.pembayaran_akun_bayar;}
    public static boolean getpengguna_obat_resep(){return var.pengguna_obat_resep;}
    public static boolean getrekap_pemesanan(){return var.rekap_pemesanan;}
    public static boolean getmaster_berkas_pegawai(){return var.master_berkas_pegawai;}
    public static boolean getberkas_kepegawaian(){return var.berkas_kepegawaian;}
    public static boolean getriwayat_jabatan(){return var.riwayat_jabatan;}
    public static boolean getriwayat_pendidikan(){return var.riwayat_pendidikan;}
    public static boolean getriwayat_naik_gaji(){return var.riwayat_naik_gaji;}
    public static boolean getkegiatan_ilmiah(){return var.kegiatan_ilmiah;}
    public static boolean getriwayat_penghargaan(){return var.riwayat_penghargaan;}
    public static boolean getriwayat_penelitian(){return var.riwayat_penelitian;}
    public static boolean getpenerimaan_non_medis(){return var.penerimaan_non_medis;}
    public static boolean getbayar_pesan_non_medis(){return var.bayar_pesan_non_medis;}
    public static boolean gethutang_barang_non_medis(){return var.hutang_barang_non_medis;}
    public static boolean getrekap_pemesanan_non_medis(){return var.rekap_pemesanan_non_medis;}
    public static boolean getinsiden_keselamatan(){return var.insiden_keselamatan;}
    public static boolean getinsiden_keselamatan_pasien(){return var.insiden_keselamatan_pasien;}
    public static boolean getgrafik_ikp_pertahun(){return var.grafik_ikp_pertahun;}
    public static boolean getgrafik_ikp_perbulan(){return var.grafik_ikp_perbulan;}
    public static boolean getgrafik_ikp_pertanggal(){return var.grafik_ikp_pertanggal;}
    public static boolean getriwayat_data_batch(){return var.riwayat_data_batch;}
    public static boolean getgrafik_ikp_jenis(){return var.grafik_ikp_jenis;}
    public static boolean getgrafik_ikp_dampak(){return var.grafik_ikp_dampak;}
    public static boolean getpiutang_akun_piutang(){return var.piutang_akun_piutang;}
    public static void setresep_obatfalse(){var.resep_obat=false;} 
    public static boolean getgrafik_kunjungan_per_agama(){return var.grafik_kunjungan_per_agama;}
    public static boolean getgrafik_kunjungan_per_umur(){return var.grafik_kunjungan_per_umur;}
    public static boolean getsuku_bangsa(){return var.suku_bangsa;}
    public static boolean getbahasa_pasien(){return var.bahasa_pasien;}
    public static boolean getgolongan_tni(){return var.golongan_tni;}
    public static boolean getsatuan_tni(){return var.satuan_tni;}
    public static boolean getjabatan_tni(){return var.jabatan_tni;}
    public static boolean getpangkat_tni(){return var.pangkat_tni;}
    public static boolean getgolongan_polri(){return var.golongan_polri;}
    public static boolean getsatuan_polri(){return var.satuan_polri;}
    public static boolean getjabatan_polri(){return var.jabatan_polri;}
    public static boolean getpangkat_polri(){return var.pangkat_polri;}
    public static boolean getcacat_fisik(){return var.cacat_fisik;}
    public static boolean getgrafik_kunjungan_suku(){return var.grafik_kunjungan_suku;}
    public static boolean getgrafik_kunjungan_bahasa(){return var.grafik_kunjungan_bahasa;}
    public static boolean getbooking_operasi(){return var.booking_operasi;}
    public static boolean getmapping_poli_bpjs(){return var.mapping_poli_bpjs;}
    public static boolean getgrafik_kunjungan_per_cacat(){return var.grafik_kunjungan_per_cacat;}
    public static boolean getbarang_cssd(){return var.barang_cssd;}
    public static boolean getskdp_bpjs(){return var.skdp_bpjs;}
    public static boolean getbooking_registrasi(){return var.booking_registrasi;}
    public static boolean getbpjs_cek_propinsi(){return var.bpjs_cek_propinsi;}
    public static boolean getbpjs_cek_kabupaten(){return var.bpjs_cek_kabupaten;}
    public static boolean getbpjs_cek_kecamatan(){return var.bpjs_cek_kecamatan;}
    public static boolean getbpjs_cek_dokterdpjp(){return var.bpjs_cek_dokterdpjp;}
    public static boolean getbpjs_cek_riwayat_rujukanrs(){return var.bpjs_cek_riwayat_rujukanrs;}
    public static boolean getbpjs_cek_tanggal_rujukan(){return var.bpjs_cek_tanggal_rujukan;}
    public static boolean getpermintaan_lab(){return var.permintaan_lab;}
    public static void setperiksalabfalse(){var.periksa_lab=false;} 
    public static void setpermintaanlabfalse(){var.permintaan_lab=false;} 
    public static boolean getpermintaan_radiologi(){return var.permintaan_radiologi;}
    public static void setperiksaradiologifalse(){var.periksa_radiologi=false;} 
    public static void setpermintaanradiologifalse(){var.permintaan_radiologi=false;} 
    public static boolean getsurat_indeks(){return var.surat_indeks;}
    public static boolean getsurat_map(){return var.surat_map;}
    public static boolean getsurat_almari(){return var.surat_almari;}
    public static boolean getsurat_rak(){return var.surat_rak;}
    public static boolean getsurat_ruang(){return var.surat_ruang;}
    public static boolean getsurat_klasifikasi(){return var.surat_klasifikasi;}
    public static boolean getsurat_status(){return var.surat_status;}
    public static boolean getsurat_sifat(){return var.surat_sifat;}
    public static boolean getsurat_balas(){return var.surat_balas;}
    public static boolean getsurat_masuk(){return var.surat_masuk;}
    public static boolean getpcare_cek_dokter(){return var.pcare_cek_dokter;}
    public static boolean getpcare_cek_poli(){return var.pcare_cek_poli;}
    public static boolean getpcare_cek_provider(){return var.pcare_cek_provider;}
    public static boolean getpcare_cek_statuspulang(){return var.pcare_cek_statuspulang;}
    public static boolean getpcare_cek_spesialis(){return var.pcare_cek_spesialis;}
    public static boolean getpcare_cek_subspesialis(){return var.pcare_cek_subspesialis;}
    public static boolean getpcare_cek_sarana(){return var.pcare_cek_sarana;}
    public static boolean getpcare_cek_khusus(){return var.pcare_cek_khusus;}
    public static boolean getpcare_cek_obat(){return var.pcare_cek_obat;}
    public static boolean getpcare_cek_tindakan(){return var.pcare_cek_tindakan;}
    public static boolean getpcare_cek_faskessubspesialis(){return var.pcare_cek_faskessubspesialis;}
    public static boolean getpcare_cek_faskesalihrawat(){return var.pcare_cek_faskesalihrawat;}
    public static boolean getpcare_cek_faskesthalasemia(){return var.pcare_cek_faskesthalasemia;}
    public static boolean getpcare_mapping_obat(){return var.pcare_mapping_obat;}
    public static boolean getpcare_mapping_tindakan(){return var.pcare_mapping_tindakan;}
    public static boolean getpcare_club_prolanis(){return var.pcare_club_prolanis;}
    public static boolean getpcare_mapping_poli(){return var.pcare_mapping_poli;}
    public static boolean getpcare_kegiatan_kelompok(){return var.pcare_kegiatan_kelompok;}
    public static boolean getpcare_mapping_tindakan_ranap(){return var.pcare_mapping_tindakan_ranap;}
    public static boolean getpcare_peserta_kegiatan_kelompok(){return var.pcare_peserta_kegiatan_kelompok;}
    public static boolean getsirkulasi_obat3(){return var.sirkulasi_obat3;}
    public static boolean getbridging_pcare_daftar(){return var.bridging_pcare_daftar;}
    public static boolean getpcare_mapping_dokter(){return var.pcare_mapping_dokter;}
    public static boolean getranap_per_ruang(){return var.ranap_per_ruang;}
    public static boolean getpenyakit_ranap_cara_bayar(){return var.penyakit_ranap_cara_bayar;}
    public static boolean getanggota_militer_dirawat(){return var.anggota_militer_dirawat;}
    public static boolean getset_input_parsial(){return var.set_input_parsial;}
    public static boolean getlama_pelayanan_radiologi(){return var.lama_pelayanan_radiologi;}
    public static boolean getlama_pelayanan_lab(){return var.lama_pelayanan_lab;}
    public static boolean getbpjs_cek_sep(){return var.bpjs_cek_sep;}
    public static boolean getcatatan_perawatan(){return var.catatan_perawatan;}
    public static boolean getsurat_keluar(){return var.surat_keluar;}
    public static boolean getkegiatan_farmasi(){return var.kegiatan_farmasi;}
    public static boolean getstok_opname_logistik(){return var.stok_opname_logistik;}
    public static boolean getsirkulasi_non_medis(){return var.sirkulasi_non_medis;} 
    public static boolean getrekap_lab_pertahun(){return var.rekap_lab_pertahun;} 
    public static boolean getperujuk_lab_pertahun(){return var.perujuk_lab_pertahun;}
    public static boolean getrekap_radiologi_pertahun(){return var.rekap_radiologi_pertahun;}
    public static boolean getperujuk_radiologi_pertahun(){return var.perujuk_radiologi_pertahun;}
    public static boolean getjumlah_porsi_diet(){return var.jumlah_porsi_diet;}
    public static boolean getjumlah_macam_diet(){return var.jumlah_macam_diet;}
    public static boolean getpayment_point2(){return var.payment_point2;}
    public static boolean getpembayaran_akun_bayar2(){return var.pembayaran_akun_bayar2;}
    public static boolean gethapus_nota_salah(){return var.hapus_nota_salah;}
    public static boolean gethais_perbangsal(){return var.hais_perbangsal;}
    public static boolean getppn_obat(){return var.ppn_obat;}
    public static boolean getsaldo_akun_perbulan(){return var.saldo_akun_perbulan;}
    public static boolean getdisplay_apotek(){return var.display_apotek;}
    public static boolean getsisrute_referensi_faskes(){return var.sisrute_referensi_faskes;}
    public static boolean getsisrute_referensi_alasanrujuk(){return var.sisrute_referensi_alasanrujuk;}
    public static boolean getsisrute_referensi_diagnosa(){return var.sisrute_referensi_diagnosa;}
    public static boolean getsisrute_rujukan_masuk(){return var.sisrute_rujukan_masuk;}
    public static boolean getAktif(){return var.aktif;}
    public static void setAktif(boolean status){var.aktif=status;} 
    public static boolean getsisrute_rujukan_keluar(){return var.sisrute_rujukan_keluar;}
    public static boolean getbpjs_cek_skdp(){return var.bpjs_cek_skdp;}
    public static boolean getdata_batch(){return var.data_batch;}
    public static boolean getkunjungan_permintaan_lab(){return var.kunjungan_permintaan_lab;}
    public static boolean getkunjungan_permintaan_lab2(){return var.kunjungan_permintaan_lab2;}
    public static boolean getkunjungan_permintaan_radiologi(){return var.kunjungan_permintaan_radiologi;}
    public static boolean getkunjungan_permintaan_radiologi2(){return var.kunjungan_permintaan_radiologi2;}
    public static boolean getpcare_pemberian_obat(){return var.pcare_pemberian_obat;}
    public static boolean getpcare_pemberian_tindakan(){return var.pcare_pemberian_tindakan;}
    public static boolean getpembayaran_akun_bayar3(){return var.pembayaran_akun_bayar3;}
    public static boolean getpassword_asuransi(){return var.password_asuransi;}
}   
