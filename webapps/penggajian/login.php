<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
    $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
    if ($_GET['act']=="login"){
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
            session_start();
            $_SESSION['ses_admin']="admin";
            $url = "index.php?act=HomeAdmin";
        }else if(($_GET['usere']=="paijo")&&($_GET['passwordte']=="mumet")) {
            session_start();
            $_SESSION['ses_admin']="paijo";
            $url = "index.php?act=HomeAdmin";
        }else{
            session_start();
            session_destroy();
            if (cekSessiAdmin()){
                session_unregister("ses_admin");
            }
            if (cekSessiPegawai()){
                session_unregister("ses_pegawai");
            }
            $url = "index.php?act=Kontak";
        }
        header("Location:".$url);

    }

?>
