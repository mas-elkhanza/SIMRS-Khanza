<?php
    function title(){
        $judul ="Aplikasi E-Eksekutif --)(*!!@#$%";
        $judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
        $judul = str_replace(array('.','-','/',',')," ",$judul);
        $judul = trim($judul);
        echo "$judul";	
    }

    function cekSessiAdmin() {
        if (isset($_SESSION['ses_eksekutif'])) {
            return true;
        } else {
            return false;
        }
    }

    function PasienAktif() {
        if (cekSessiAdmin()) {
            return $_SESSION['ses_eksekutif'];
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
            $form = array (
                'HomeUser','PelayananRawatJalan','PelayananIGDK','PelayananRawatInap','PelayananLaborat' ,'PelayananRadiologi',
                'SisaStokFarmasi','DaruratStok','KadaluarsaBatch','RingkasanPengadaanFarmasi','StokTidakBergerak'
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
        switch ($aksi) {
            case "HomeUser"                     : include_once("pages/listhome.php"); break;
            case "PelayananRawatJalan"          : include_once("pages/listpelayananrawatjalan.php"); break;
            case "PelayananIGDK"                : include_once("pages/listpelayananigd.php"); break;
            case "PelayananRawatInap"           : include_once("pages/listpelayananrawatinap.php"); break;
            case "PelayananLaborat"             : include_once("pages/listpelayananlab.php"); break;
            case "PelayananRadiologi"           : include_once("pages/listpelayananradiologi.php"); break;
            case "SisaStokFarmasi"              : include_once("pages/listsisastokfarmasi.php"); break;
            case "DaruratStok"                  : include_once("pages/listdaruratstok.php"); break;
            case "KadaluarsaBatch"              : include_once("pages/listkadaluarsabatch.php"); break;
            case "RingkasanPengadaanFarmasi"    : include_once("pages/listringkasanpengadaanfarmasi.php"); break;
            case "StokTidakBergerak"            : include_once("pages/liststoktidakbergerak.php"); break;
            default                             : include_once("pages/listhome.php");
        }   
    }
?>
