<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
    $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
    $level      = trim(isset($_GET['level']))?trim($_GET['level']):NULL;
    $url        = "index.php?act=Kontak";
    if ($_GET['act']=="login"){
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)&&($level=="admin")){
            session_start();
            $_SESSION['ses_admin_kepegawaian']="admin";
            $_SESSION["level"]=encrypt_decrypt($level,"e");
            $url = "index.php?act=HomeAdmin";
        }elseif((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)&&($level=="user")){
            session_start();
            $_SESSION['ses_admin_kepegawaian']="paijo";
            $_SESSION["level"]=encrypt_decrypt($level,"e");
            $url = "index.php?act=HomeAdmin";
        }else{
            session_start();
            session_destroy();
            if (cekSessiAdmin()){
                session_unregister("ses_admin_kepegawaian");
            }
            $url = "index.php?act=Kontak";
        }
    }
    header("Location:".$url);

?>
