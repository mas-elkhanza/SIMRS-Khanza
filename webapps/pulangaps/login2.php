<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $cari    = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $cari    = json_decode(encrypt_decrypt($cari,"d"),true);
    $url     = "index.php?act=Home";
    if (isset($cari["usere"])) {
        if(($cari["usere"]==USERHYBRIDWEB)&&($cari["passwordte"]==PASHYBRIDWEB)){
            session_start();
            $nopernyataan=validTeks($cari['nopernyataan']); 
            $_SESSION['ses_admin_pulangaps']="admin";
            $url = "index.php?act=Kamera2&iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\",\"nopernyataan\":\"".$nopernyataan."\"}","e");
        }
    }
    header("Location:".$url);
?>
