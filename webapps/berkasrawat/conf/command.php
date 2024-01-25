<?php
    if(strpos($_SERVER['REQUEST_URI'],"conf")){
        exit(header("Location:../index.php"));
    }
    
    function title(){
            $judul ="Vedika SIMKES Khanza--)(*!!@#$%";
            $judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
            $judul = str_replace(array('.','-','/',',')," ",$judul);
            $judul = trim($judul);
            echo "$judul";	
    }

    function cekSessiAdmin() {
        if (isset($_SESSION['ses_admin_berkas_rawat'])) {
            return true;
        } else {
            return false;
        }
    }
    
    function cekSessiVedika() {
        if (isset($_SESSION['ses_vedika'])) {
            return true;
        } else {
            return false;
        }
    }

    function cekUser() {
        if (isset($_SESSION['ses_admin_berkas_rawat'])) {
            return true;
        } else if (isset($_SESSION['ses_vedika'])) {
            return true;
        } else {
            return false;
        }
    }

    function adminAktif() {
        if (cekSessiAdmin()) {
            return $_SESSION['ses_admin_berkas_rawat'];
        }
    }

    function isGuest() {
        if (cekSessiAdmin()) {
            return false;
        } else {
            return true;
        }
    }	

    function formProtek() {
        $aksi=isset($_GET['act'])?$_GET['act']:NULL;
        if (!cekUser()) {
            $form = array ('List','ListNonHapus','Detail','Detail2','DetailNonHapus','Detail2NonHapus','MasterBerkas','ListVedika');
            foreach ($form as $page) {
                if ($aksi==$page) {
                    echo "<META HTTP-EQUIV = 'Refresh' Content = '0; URL = ?act=HomeAdmin'>";
                    exit;
                    break;
                }
            }
        }		

    }

    function actionPages() {
        $aksi=isset($_REQUEST['act'])?$_REQUEST['act']:NULL;
        formProtek();
        if(cekSessiAdmin()) {
            switch ($aksi) {
                case 'HomeAdmin'            : include_once('index.php'); break;
                case 'List'                 : include_once('pages/list.php'); break;
                case 'ListNonHapus'         : include_once('pages/listnonhapus.php'); break;
                case 'Detail'               : include_once('pages/detail.php'); break;
                case 'DetailNonHapus'       : include_once('pages/detailnonhapus.php'); break;
                case 'Detail2'              : include_once('pages/detail2.php'); break;
                case 'Detail2NonHapus'      : include_once('pages/detail2nonhapus.php'); break;
                case 'MasterBerkas'         : include_once('pages/master.php'); break;
                default			    : include_once('index.php');
            }
        }else if(cekSessiVedika()) {
            switch ($aksi) {
                case 'HomeAdmin'            : include_once('homevedika.php'); break;
                case 'ListVedika'           : include_once('pages/listvedika.php'); break;
                default			    : include_once('homevedika.php');
            }
        }else{
            switch ($aksi) {
                case 'HomeAdmin'            : include_once('homevedika.php'); break;
                default			    : include_once('homevedika.php');
            }
        }   
    }
?>
