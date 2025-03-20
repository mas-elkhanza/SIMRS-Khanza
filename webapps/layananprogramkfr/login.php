<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $cari    = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $cari    = json_decode(encrypt_decrypt($cari,"d"),true);
    $url     = "index.php?act=Home";
    if (isset($cari["usere"])) {
        if(($cari["usere"]==USERHYBRIDWEB)&&($cari["passwordte"]==PASHYBRIDWEB)){
            session_start();
            $_SESSION['ses_admin_layananprogramkfr']="admin";
            $url = "index.php?act=Kamera";
        }
    }
    header("Location:".$url);
?>
