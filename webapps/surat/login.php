<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
    $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
    if ($_GET['act']=="login"){
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
            session_start();
            $_SESSION['ses_admin']="admin";
            $url = "index.php?act=List&tgl1=".$_GET['tgl1']."&tgl2=".$_GET['tgl2']."&ruang=".$_GET['ruang']."&sttssurat=".$_GET['sttssurat']."&sttsbalas=".$_GET['sttsbalas']."&keyword=".$_GET['keyword'];			
        }else{
                session_start();
                session_destroy();
                if (cekSessiAdmin()){
                    session_unregister("ses_admin");
                }
                $url = "index.php?act=HomeAdmin";
        }
        header("Location:".$url);            
    }
    
?>
