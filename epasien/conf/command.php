<?php
    if(strpos($_SERVER['REQUEST_URI'],"conf")){
        exit(header("Location:../index.php"));
    }
    
    function title(){
        $judul ="E-Pasien SIMKES Khanza --)(*!!@#$%";
        $judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
        $judul = str_replace(array('.','-','/',',')," ",$judul);
        $judul = trim($judul);
        echo "$judul";	
    }

    function cekSessiPasien() {
        if (isset($_SESSION['ses_pasien'])) {
            return true;
        } else {
            return false;
        }
     }

    function PasienAktif() {
        if (cekSessiPasien()) {
            return $_SESSION['ses_pasien'];
        }
     }

    function isPengunjung() {
        if (cekSessiPasien()) {
            return false;
        } else {
            return true;
        }
     }	


    function formProtek() {
        $aksi=isset($_GET['act'])?$_GET['act']:NULL;
        if (!cekSessiPasien()) {
            $form = array ('HomeUser','FasilitasKamarUser','InformasiKamarUser','FasilitasRadiologiUser','FasilitasLaboratUser',
                'FasilitasOperasiUser','FasilitasOnlineUser','JadwalDokterUser','CekPoliUser','CekAsuransiUser','RiwayatPeriksa',
                'CekResume','CekBilling','CekBilling2','Pengaduan','BookingRegistrasi','SimpanBookingRegistrasi','CekinRegistrasi',
                'CekinRegistrasi2','BuktiRegistrasi','BuktiRegistrasi2','SuratSakit','TampilSuratSakit','SuratHamil','TampilSuratHamil',
                'SuratBebasNarkoba','TampilSuratBebasNarkoba','SuratRujuk','TampilSuratRujuk','SuratCovid','TampilSuratCovid','SuratKontrol',
                'TampilSuratKontrol','Perpustakaan','AntrianPemeriksaanLab','AntrianPemeriksaanLabMB','AntrianPemeriksaanLabPA',
                'AntrianPemeriksaanRad','AntrianUSGKandungan','AntrianUSGUrologi','AntrianUSGGynecologi','AntrianUSGNeonatus','AntrianEKG',
                'AntrianEcho','AntrianEndoskopiFaring','AntrianEndoskopiHidung','AntrianEndoskopiTelinga','PersetujuanUmum',
                'PersetujuanRencanaPemulangan','PersetujuanPenyerahanResepRalan','PersetujuanPernyataanPasienUmum','PersetujuanPernyataanPulangAPS',
                'PersetujuanTransferAntarRuang','PersetujuanRawatInap','PersetujuanPenundaanPelayanan','PersetujuanPenolakanAnjuranMedis',
                'PersetujuanRestrain','PersetujuanPenolakanTindakan','BuktiPelaksanaanEdukasi','BuktiPelayananRehabilitasi','KartuPasien',
                'TampilPermintaanLab','TampilHasilLab','UpdatePassword','RiwayatMCU','HasilMCU','HasilLabPK','HasilLabMB','HasilLabPA',
                'HasilRad','HasilUSGKandungan','HasilUSGUrologi','HasilUSGGynecologi','HasilUSGNeonatus','HasilEKG','HasilEcho','HasilSlitLamp',
                'HasilEndoskopiFaring','HasilEndoskopiHidung','HasilEndoskopiTelinga','AmbilPersetujuanUmum','AmbilPersetujuanRencanaPemulangan',
                'AmbilPenyerahanResepRalan','AmbilPersetujuanPernyataanPasienUmum','AmbilPersetujuanRawatInap','AmbilPersetujuanTransferAntarRuang',
                'AmbilPersetujuanPenundaanPelayanan','AmbilPersetujuanPenolakanAnjuranMedis','AmbilPersetujuanRestrain','HasilOCT',
                'AmbilPersetujuanPernyataanPulangAPS','AmbilPersetujuanPenolakanTindakan','AmbilBuktiPelaksanaanEdukasi','AmbilBuktiPelayananRehabilitasi',
                'HasilPersetujuanUmum','HasilPersetujuanRencanaPemulangan','HasilPenyerahanResepRalan','HasilPersetujuanPernyataanPasienUmum',
                'HasilPersetujuanTransferAntarRuang','HasilPersetujuanRawatInap','HasilPersetujuanPenundaanPelayanan','HasilPersetujuanPenolakanAnjuranMedis',
                'HasilPersetujuanRestrain','HasilPersetujuanPernyataanPulangAPS','HasilPersetujuanPenolakanTindakan','HasilBuktiPelaksanaanEdukasi',
                'HasilBuktiPelayananRehabilitasi','BuktiPelayananProgramKFR','AmbilBuktiPelayananProgramKFR','HasilBuktiPelayananProgramKFR','AntrianSlitLamp',
                'AntrianOCT','PersetujuanPemeriksaanHIV','AmbilPersetujuanPemeriksaanHIV','HasilPersetujuanPemeriksaanHIV','AmbilPernyataanMemilihDPJP',
                'PernyataanMemilihDPJP','HasilPernyataanMemilihDPJP'
            );
            foreach ($form as $page) {
                if ($aksi==$page) {
                    echo "<META HTTP-EQUIV = 'Refresh' Content = '0; URL = ?act=Home'>";
                    exit;
                    break;
                }
            }
        }	
    }

    function actionPages() {
        $aksi=isset($_REQUEST['act'])?$_REQUEST['act']:NULL;
        formProtek();
        if (!cekSessiPasien()) {
            switch ($aksi) {
                case "Home"                                    : include_once("pages/home.php"); break;
                case "HomePasien"                              : include_once("pages/index.php"); break;
                case "LoginPasien"                             : include_once("pages/login.php"); break;
                case "DokterKami"                              : include_once("pages/listsemuadokter.php"); break;
                case "FasilitasKamar"                          : include_once("pages/listkamar.php"); break;
                case "FasilitasRadiologi"                      : include_once("pages/listradiologi.php"); break;
                case "FasilitasLaborat"                        : include_once("pages/listlaborat.php"); break;
                case "FasilitasOperasi"                        : include_once("pages/listoperasi.php"); break;
                case "FasilitasOnline"                         : include_once("pages/listonline.php"); break;
                case "PendaftaranPeriksa"                      : include_once("pages/listperiksa.php"); break;
                case "CekPoli"                                 : include_once("pages/listpoli.php"); break;
                case "CekAsuransi"                             : include_once("pages/listcarabayar.php"); break;
                case "CekBooking"                              : include_once("pages/listbooking.php"); break;
                case "CekStokDarah"                            : include_once("pages/liststokdarah.php"); break;
                default                                        : include_once("pages/home.php");
            }
        }else{
            switch ($aksi) {
                case "HomeUser"                                : include_once("pages/homeuser.php"); break;
                case "FasilitasKamarUser"                      : include_once("pages/listkamaruser.php"); break;
                case "InformasiKamarUser"                      : include_once("pages/listinformasikamaruser.php"); break;
                case "FasilitasRadiologiUser"                  : include_once("pages/listradiologiuser.php"); break;
                case "FasilitasLaboratUser"                    : include_once("pages/listlaboratuser.php"); break;
                case "FasilitasOperasiUser"                    : include_once("pages/listoperasiuser.php"); break;
                case "FasilitasOnlineUser"                     : include_once("pages/listonlineuser.php"); break;
                case "JadwalDokterUser"                        : include_once("pages/listjadwaldokteruser.php"); break;
                case "CekPoliUser"                             : include_once("pages/listpoliuser.php"); break;
                case "CekAsuransiUser"                         : include_once("pages/listcarabayaruser.php"); break;
                case "RiwayatPeriksa"                          : include_once("pages/listriwayatperiksa.php"); break;
                case "CekResume"                               : include_once("pages/listresume.php"); break;
                case "CekBilling"                              : include_once("pages/listbilling.php"); break;
                case "CekBilling2"                             : include_once("pages/listbilling2.php"); break;
                case "Pengaduan"                               : include_once("pages/listpengaduan.php"); break;
                case "BookingRegistrasi"                       : include_once("pages/listbookingregistrasi.php"); break;
                case "SimpanBookingRegistrasi"                 : include_once("pages/simpanbookingregistrasi.php"); break;
                case "CekinRegistrasi"                         : include_once("pages/cekinregistrasi.php"); break;
                case "CekinRegistrasi2"                        : include_once("pages/cekinregistrasi2.php"); break;
                case "BuktiRegistrasi"                         : include_once("pages/buktiregistrasi.php"); break;
                case "BuktiRegistrasi2"                        : include_once("pages/buktiregistrasi2.php"); break;
                case "SuratSakit"                              : include_once("pages/listsuratsakit.php"); break;
                case "TampilSuratSakit"                        : include_once("pages/listtampilsuratsakit.php"); break;
                case "SuratHamil"                              : include_once("pages/listsurathamil.php"); break;
                case "TampilSuratHamil"                        : include_once("pages/listtampilsurathamil.php"); break;
                case "SuratBebasNarkoba"                       : include_once("pages/listsuratbebasnarkoba.php"); break;
                case "TampilSuratBebasNarkoba"                 : include_once("pages/listtampilsuratbebasnarkoba.php"); break;
                case "SuratRujuk"                              : include_once("pages/listsuratrujuk.php"); break;
                case "TampilSuratRujuk"                        : include_once("pages/listtampilsuratrujuk.php"); break;
                case "SuratCovid"                              : include_once("pages/listsuratcovid.php"); break;
                case "TampilSuratCovid"                        : include_once("pages/listtampilsuratcovid.php"); break;
                case "SuratKontrol"                            : include_once("pages/listsuratkontrol.php"); break;
                case "TampilSuratKontrol"                      : include_once("pages/listtampilsuratkontrol.php"); break;
                case "Perpustakaan"                            : include_once("pages/listperpustakaan.php"); break;
                case "AntrianPemeriksaanLab"                   : include_once("pages/listriwayatlaborat.php"); break;
                case "AntrianPemeriksaanLabMB"                 : include_once("pages/listriwayatlaboratmb.php"); break;
                case "AntrianPemeriksaanLabPA"                 : include_once("pages/listriwayatlaboratpa.php"); break;
                case "AntrianPemeriksaanRad"                   : include_once("pages/listriwayatradiologi.php"); break;
                case "AntrianUSGKandungan"                     : include_once("pages/listriwayatusgkandungan.php"); break;
                case "AntrianUSGUrologi"                       : include_once("pages/listriwayatusgurologi.php"); break;
                case "AntrianUSGGynecologi"                    : include_once("pages/listriwayatusgynecologi.php"); break;
                case "AntrianUSGNeonatus"                      : include_once("pages/listriwayatusgneonatus.php"); break;
                case "AntrianEKG"                              : include_once("pages/listriwayatekg.php"); break;
                case "AntrianSlitLamp"                         : include_once("pages/listriwayatslitlamp.php"); break;
                case "AntrianOCT"                              : include_once("pages/listriwayatoct.php"); break;
                case "AntrianEcho"                             : include_once("pages/listriwayatecho.php"); break;
                case "AntrianEndoskopiFaring"                  : include_once("pages/listriwayatendoskopifaring.php"); break;
                case "AntrianEndoskopiHidung"                  : include_once("pages/listriwayatendoskopihidung.php"); break;
                case "AntrianEndoskopiTelinga"                 : include_once("pages/listriwayatendoskopitelinga.php"); break;
                case "PersetujuanUmum"                         : include_once("pages/listriwayatpersetujuanumum.php"); break;
                case "PersetujuanRencanaPemulangan"            : include_once("pages/listriwayatpersetujuanrencanapemulangan.php"); break;
                case "PersetujuanPenyerahanResepRalan"         : include_once("pages/listriwayatpersetujuanpenyerahanresepralan.php"); break;
                case "PersetujuanPernyataanPasienUmum"         : include_once("pages/listriwayatpersetujuanpernyataanpasienumum.php"); break;
                case "PersetujuanPernyataanPulangAPS"          : include_once("pages/listriwayatpersetujuanpernyataanpulangaps.php"); break;
                case "PersetujuanTransferAntarRuang"           : include_once("pages/listriwayatpersetujuantransferantarruang.php"); break;
                case "PersetujuanRawatInap"                    : include_once("pages/listriwayatpersetujuanrawatinap.php"); break;
                case "PersetujuanPenundaanPelayanan"           : include_once("pages/listriwayatpersetujuanpenundaanpelayanan.php"); break;
                case "PersetujuanPenolakanAnjuranMedis"        : include_once("pages/listriwayatpersetujuanpenolakananjuranmedis.php"); break;
                case "PersetujuanRestrain"                     : include_once("pages/listriwayatpersetujuanrestrain.php"); break;
                case "PersetujuanPenolakanTindakan"            : include_once("pages/listriwayatpersetujuanpenolakantindakan.php"); break;
                case "PernyataanMemilihDPJP"                   : include_once("pages/listriwayatpernyataanmemilihdpjp.php"); break;
                case "PersetujuanPemeriksaanHIV"               : include_once("pages/listriwayatpersetujuanpemeriksaanhiv.php"); break;
                case "BuktiPelaksanaanEdukasi"                 : include_once("pages/listriwayatbuktipelaksanaanedukasi.php"); break;
                case "BuktiPelayananRehabilitasi"              : include_once("pages/listriwayatbuktipelayananrehabilitasi.php"); break;
                case "BuktiPelayananProgramKFR"                : include_once("pages/listriwayatbuktipelayananprogramkfr.php"); break;
                case "KartuPasien"                             : include_once("pages/listkartu.php"); break;
                case "TampilPermintaanLab"                     : include_once("pages/listtampilpermintaanlab.php"); break;
                case "TampilHasilLab"                          : include_once("pages/listtampilhasillab.php"); break;
                case "UpdatePassword"                          : include_once("pages/updatepassword.php"); break;
                case "RiwayatMCU"                              : include_once("pages/listriwayatmcu.php"); break;
                case "HasilMCU"                                : include_once("pages/listhasilmcu.php"); break;
                case "HasilLabPK"                              : include_once("pages/listhasillabpk.php"); break;
                case "HasilLabMB"                              : include_once("pages/listhasillabmb.php"); break;
                case "HasilLabPA"                              : include_once("pages/listhasillabpa.php"); break;
                case "HasilRad"                                : include_once("pages/listhasilradiologi.php"); break;
                case "HasilUSGKandungan"                       : include_once("pages/listhasilusgkandungan.php"); break;
                case "HasilUSGUrologi"                         : include_once("pages/listhasilusgurologi.php"); break;
                case "HasilUSGGynecologi"                      : include_once("pages/listhasilusgynecologi.php"); break;
                case "HasilUSGNeonatus"                        : include_once("pages/listhasilusgneonatus.php"); break;
                case "HasilEKG"                                : include_once("pages/listhasilekg.php"); break;
                case "HasilSlitLamp"                           : include_once("pages/listhasilslitlamp.php"); break;
                case "HasilOCT"                                : include_once("pages/listhasiloct.php"); break;
                case "HasilEcho"                               : include_once("pages/listhasilecho.php"); break;
                case "HasilEndoskopiFaring"                    : include_once("pages/listhasilendoskopifaring.php"); break;
                case "HasilEndoskopiHidung"                    : include_once("pages/listhasilendoskopihidung.php"); break;
                case "HasilEndoskopiTelinga"                   : include_once("pages/listhasilendoskopitelinga.php"); break;
                case "AmbilPersetujuanUmum"                    : include_once("pages/ambilpersetujuanumum.php"); break;
                case "AmbilPersetujuanRencanaPemulangan"       : include_once("pages/ambilpersetujuanrencanapemulangan.php"); break;
                case "AmbilPenyerahanResepRalan"               : include_once("pages/ambilpersetujuanpenyerahanresepralan.php"); break;
                case "AmbilPersetujuanPernyataanPasienUmum"    : include_once("pages/ambilpersetujuanpernyataanpasienumum.php"); break;
                case "AmbilPersetujuanPemeriksaanHIV"          : include_once("pages/ambilpersetujuanpemeriksaanhiv.php"); break;
                case "AmbilPersetujuanRawatInap"               : include_once("pages/ambilpersetujuanrawatinap.php"); break;
                case "AmbilPersetujuanTransferAntarRuang"      : include_once("pages/ambilpersetujuantransferantarruang.php"); break;
                case "AmbilPersetujuanPenundaanPelayanan"      : include_once("pages/ambilpersetujuanpenundaanpelayanan.php"); break;
                case "AmbilPersetujuanPenolakanAnjuranMedis"   : include_once("pages/ambilpersetujuanpenolakananjuranmedis.php"); break;
                case "AmbilPersetujuanRestrain"                : include_once("pages/ambilpersetujuanrestrain.php"); break;
                case "AmbilPersetujuanPernyataanPulangAPS"     : include_once("pages/ambilpersetujuanpernyataanpulangaps.php"); break;
                case "AmbilPersetujuanPenolakanTindakan"       : include_once("pages/ambilpersetujuanpenolakantindakan.php"); break;
                case "AmbilPernyataanMemilihDPJP"              : include_once("pages/ambilpernyataanmemilihdpjp.php"); break;
                case "AmbilBuktiPelaksanaanEdukasi"            : include_once("pages/ambilbuktipelaksanaanedukasi.php"); break;
                case "AmbilBuktiPelayananRehabilitasi"         : include_once("pages/ambilbuktipelayananrehabilitasi.php"); break;
                case "AmbilBuktiPelayananProgramKFR"           : include_once("pages/ambilbuktipelayananprogramkfr.php"); break;
                case "HasilPersetujuanUmum"                    : include_once("pages/listhasilpersetujuanumum.php"); break;
                case "HasilPersetujuanRencanaPemulangan"       : include_once("pages/listhasilpersetujuanrencanapemulangan.php"); break;
                case "HasilPenyerahanResepRalan"               : include_once("pages/listhasilpersetujuanpenyerahanresepralan.php"); break;
                case "HasilPersetujuanPernyataanPasienUmum"    : include_once("pages/listhasilpersetujuanpernyataanpasienumum.php"); break;
                case "HasilPersetujuanPemeriksaanHIV"          : include_once("pages/listhasilpersetujuanpemeriksaanhiv.php"); break;
                case "HasilPersetujuanTransferAntarRuang"      : include_once("pages/listhasilpersetujuantransferantarruang.php"); break;
                case "HasilPersetujuanRawatInap"               : include_once("pages/listhasilpersetujuanrawatinap.php"); break;
                case "HasilPersetujuanPenundaanPelayanan"      : include_once("pages/listhasilpersetujuanpenundaanpelayanan.php"); break;
                case "HasilPersetujuanPenolakanAnjuranMedis"   : include_once("pages/listhasilpersetujuanpenolakananjuranmedis.php"); break;
                case "HasilPersetujuanRestrain"                : include_once("pages/listhasilpersetujuanrestrain.php"); break;
                case "HasilPersetujuanPernyataanPulangAPS"     : include_once("pages/listhasilpersetujuanpernyataanpulangaps.php"); break;
                case "HasilPersetujuanPenolakanTindakan"       : include_once("pages/listhasilpersetujuanpenolakantindakan.php"); break;
                case "HasilBuktiPelaksanaanEdukasi"            : include_once("pages/listhasilbuktipelaksanaanedukasi.php"); break;
                case "HasilBuktiPelayananRehabilitasi"         : include_once("pages/listhasilbuktipelayananrehabilitasi.php"); break;
                case "HasilBuktiPelayananProgramKFR"           : include_once("pages/listhasilbuktipelayananprogramkfr.php"); break;
                case "HasilPernyataanMemilihDPJP"              : include_once("pages/listhasilpernyataanmemilihdpjp.php"); break;
                default                                        : include_once("pages/homeuser.php");
            }
        }
            
    }
	
    function actionMenu() {
        $aksi=isset($_REQUEST['act'])?$_REQUEST['act']:"Home";
        if (!cekSessiPasien()) {
            if($aksi=="Home"){
                echo "<li><a href='#top' class='smoothScroll'>Home</a></li>
                     <li><a href='#about' class='smoothScroll'>Tentang Kami</a></li>
                     <li><a href='#team' class='smoothScroll'>Dokter Kami</a></li>
                     <li><a href='#news' class='smoothScroll'>Jadwal Praktek</a></li>
                     <li><a href='index.php?act=FasilitasKamar' class='smoothScroll'>Fasilitas & Tarif</a></li>
                     <li class='appointment-btn'><a href='#appointment'>Buat Janji/Booking</a></li>";
            }else if(($aksi=="FasilitasKamar")||($aksi=="FasilitasRadiologi")||($aksi=="FasilitasLaborat")||($aksi=="FasilitasOperasi")||($aksi=="FasilitasOnline")){
                echo "<li><a href='index.php?act=Home'>Home</a></li>
                     <li><a href='index.php?act=FasilitasKamar' class='smoothScroll'>Kamar</a></li>
                     <li><a href='index.php?act=FasilitasRadiologi' class='smoothScroll'>Radiologi</a></li>
                     <li><a href='index.php?act=FasilitasLaborat' class='smoothScroll'>Laborat</a></li>
                     <li><a href='index.php?act=FasilitasOperasi' class='smoothScroll'>Operasi</a></li>
                     <li><a href='index.php?act=FasilitasOnline' class='smoothScroll'>Konsultasi Online</a></li>
                     <li class='appointment-btn'><a href='index.php?act=Home#appointment'>Buat Janji/Booking</a></li>";
            }else{
                echo "<li><a href='index.php?act=Home#top' class='smoothScroll'>Home</a></li>
                     <li><a href='index.php?act=Home#about' class='smoothScroll'>Tentang Kami</a></li>
                     <li><a href='index.php?act=Home#team' class='smoothScroll'>Dokter Kami</a></li>
                     <li><a href='index.php?act=Home#news' class='smoothScroll'>Jadwal Praktek</a></li>
                     <li><a href='index.php?act=FasilitasKamar' class='smoothScroll'>Fasilitas & Tarif</a></li>
                     <li class='appointment-btn'><a href='index.php?act=Home#appointment'>Buat Janji/Booking</a></li>";
            }
        }
    }
 
?>
