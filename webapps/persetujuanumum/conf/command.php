<?php
    if(strpos($_SERVER['REQUEST_URI'],"conf")){
        exit(header("Location:../index.php"));
    }
    
    function title(){
            $judul ="SIMKES Khanza --)(*!!@#$%";
            $judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
            $judul = str_replace(array('.','-','/',',')," ",$judul);
            $judul = trim($judul);
            echo "$judul";	
    }

    function cekSessiAdmin() {
        if (isset($_SESSION['ses_admin_persetujuanumum'])) {
            return true;
        } else {
            return false;
        }
    }


    function cekUser() {
        if (isset($_SESSION['ses_admin_persetujuanumum'])) {
            return true;
        } else {
            return false;
        }
    }

    function adminAktif() {
        if (cekSessiAdmin()) {
            return $_SESSION['ses_admin_persetujuanumum'];
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
            $form = array ('Kamera','Kamera2');
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
              case 'Home'                   : include_once('pages/index.php'); break;
              case 'Kamera'                 : include_once('pages/kamera.php'); break;
              case 'Kamera2'                : include_once('pages/kamera2.php'); break;
              default                       : include_once('pages/index.php');
        }
    }
?>
