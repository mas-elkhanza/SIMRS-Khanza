<?php
    if(strpos(parse_url($_SERVER['REQUEST_URI'],PHP_URL_PATH),"/conf/")!==false){
        exit(header("Location:../index.php"));
    }

    function title(){
        echo "Persetujuan Penandaan Lokasi Luka";
    }

    function cekSessiAdmin() {
        if (isset($_SESSION['ses_admin_penandaanlokasiluka'])) {
            return true;
        } else {
            return false;
        }
    }

    function cekUser() {
        if (isset($_SESSION['ses_admin_penandaanlokasiluka'])) {
            return true;
        } else {
            return false;
        }
    }

    function adminAktif() {
        if (cekSessiAdmin()) {
            return $_SESSION['ses_admin_penandaanlokasiluka'];
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
            $form = array ('Kamera');
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
              default                       : include_once('pages/index.php');
        }
    }
?>
