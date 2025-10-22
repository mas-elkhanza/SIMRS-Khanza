<?php
    function title(){
        $judul ="Aplikasi E-MCU --)(*!!@#$%";
        $judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
        $judul = str_replace(array('.','-','/',',')," ",$judul);
        $judul = trim($judul);
        echo "$judul";	
    }

    function cekSessiAdmin() {
        if (isset($_SESSION['ses_emcu'])) {
            return true;
        } else {
            return false;
        }
     }

    function PasienAktif() {
        if (cekSessiAdmin()) {
            return $_SESSION['ses_emcu'];
        }
     }

    function isPengunjung() {
        if (cekSessiAdmin()) {
            return false;
        } else {
            return true;
        }
     }	


    function formProtek() {
        $aksi=isset($_GET['act'])?$_GET['act']:NULL;
        if (!cekSessiAdmin()) {
            $form = array ('HomeUser','Pasien','UpdatePassword','BookingMCU','HasilMCU','RiwayatMCU','PasienBaru','ResumeHasilMCU','ResumePenyakit','GrafikPemeriksaan');
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
            case "Pasien"                  : include_once("pages/listpasien.php"); break;
            case "UpdatePassword"          : include_once("pages/updatepassword.php"); break;
            case "HomeUser"                : include_once("pages/listhome.php"); break;
            case "BookingMCU"              : include_once("pages/listbookingmcu.php"); break;
            case "HasilMCU"                : include_once("pages/listhasilmcu.php"); break;
            case "RiwayatMCU"              : include_once("pages/listriwayatmcu.php"); break;
            case "PasienBaru"              : include_once("pages/listpasienbaru.php"); break;
            case "ResumeHasilMCU"          : include_once("pages/listresumehasilmcu.php"); break;
            case "ResumePenyakit"          : include_once("pages/listresumepenyakit.php"); break;
            case "GrafikPemeriksaan"       : include_once("pages/listgrafikpemeriksaan.php"); break;
            default                        : include_once("pages/listhome.php");
        }   
    }
?>
