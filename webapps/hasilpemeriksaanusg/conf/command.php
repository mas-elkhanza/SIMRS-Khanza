<?php
    if(strpos($_SERVER['REQUEST_URI'],"conf")){
        exit(header("Location:../index.php"));
    }
    
    function title(){
            $judul ="Hasil USG SIMRS Khanza --)(*!!@#$%";
            $judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
            $judul = str_replace(array('.','-','/',',')," ",$judul);
            $judul = trim($judul);
            echo "$judul";	
    }

    function cekSessiAdmin() {
        if (isset($_SESSION['ses_admin_gambarpemeriksaanusg'])) {
            return true;
        } else {
            return false;
        }
    }


    function cekUser() {
        if (isset($_SESSION['ses_admin_gambarpemeriksaanusg'])) {
            return true;
        } else {
            return false;
        }
    }

    function adminAktif() {
        if (cekSessiAdmin()) {
            return $_SESSION['ses_admin_gambarpemeriksaanusg'];
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
                $form = array ('HomeAdmin','List');
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
                    case 'HomeAdmin'	  	: include_once('pages/kontak.php'); break;
                    case 'List'                 : include_once('pages/list.php'); break;
                    default			: include_once('pages/kontak.php');

            }
    }
?>
