<?php
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
            $form = array ('HomeAdmin','Pengguna');
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
        switch ($aksi) {
            case "Home"                                    : include_once("page/home.php"); break;
            case "LoginPasien"                             : include_once("page/login.php"); break;
            case "DokterKami"                              : include_once("page/listsemuadokter.php"); break;
            case "FasilitasKamar"                          : include_once("page/listkamar.php"); break;
            case "FasilitasRadiologi"                      : include_once("page/listradiologi.php"); break;
            case "FasilitasLaborat"                        : include_once("page/listlaborat.php"); break;
            case "FasilitasOperasi"                        : include_once("page/listoperasi.php"); break;
            default                                        : include_once("page/home.php");
        }
    }
	
    function actionMenu() {
        $aksi=isset($_REQUEST['act'])?$_REQUEST['act']:NULL;
        if($aksi=="Home"){
            echo "<li><a href='#top' class='smoothScroll'>Home</a></li>
                 <li><a href='#about' class='smoothScroll'>Tentang Kami</a></li>
                 <li><a href='#team' class='smoothScroll'>Dokter Kami</a></li>
                 <li><a href='#news' class='smoothScroll'>Jadwal Praktek</a></li>
                 <li><a href='index.php?act=FasilitasKamar' class='smoothScroll'>Fasilitas & Tarif</a></li>
                 <li class='appointment-btn'><a href='#appointment'>Buat Janji/Booking</a></li>";
        }else if(($aksi=="LoginPasien")||($aksi=="DokterKami")){
            echo "<li><a href='index.php?act=Home'>Home</a></li>
                 <li><a href='index.php?act=FasilitasKamar' class='smoothScroll'>Fasilitas & Tarif</a></li>
                 <li class='appointment-btn'><a href='index.php?act=LoginPasien' class='smoothScroll'>Log In Pasien</a></li>";
        }else if(($aksi=="FasilitasKamar")||($aksi=="FasilitasRadiologi")||($aksi=="FasilitasLaborat")||($aksi=="FasilitasOperasi")){
            echo "<li><a href='index.php?act=Home'>Home</a></li>
                 <li><a href='index.php?act=FasilitasKamar' class='smoothScroll'>Kamar</a></li>
                 <li><a href='index.php?act=FasilitasRadiologi' class='smoothScroll'>Radiologi</a></li>
                 <li><a href='index.php?act=FasilitasLaborat' class='smoothScroll'>Laborat</a></li>
                 <li><a href='index.php?act=FasilitasOperasi' class='smoothScroll'>Operasi</a></li>
                 <li><a href='index.php?act=FasilitasOnline' class='smoothScroll'>Konsultasi Online</a></li>
                 <li class='appointment-btn'><a href='index.php?act=LoginPasien' class='smoothScroll'>Log In Pasien</a></li>";
        }else{
            echo "<li><a href='#top' class='smoothScroll'>Home</a></li>
                 <li><a href='#about' class='smoothScroll'>Tentang Kami</a></li>
                 <li><a href='#team' class='smoothScroll'>Dokter Kami</a></li>
                 <li><a href='#news' class='smoothScroll'>Jadwal Praktek</a></li>
                 <li><a href='index.php?act=FasilitasKamar' class='smoothScroll'>Fasilitas & Tarif</a></li>
                 <li class='appointment-btn'><a href='#appointment'>Buat Janji/Booking</a></li>";
        }
    }
 
?>
