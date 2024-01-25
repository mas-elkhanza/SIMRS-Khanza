<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
    $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
    $url        = "index.php?act=HomeAdmin";
    if ($_GET['act']=="login"){
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
            session_start();
            $_SESSION['ses_admin_suratsurat']="admin";
            $url = "index.php?act=List&tgl1=".validTeks($_GET['tgl1'])."&tgl2=".validTeks($_GET['tgl2'])."&ruang=".validTeks($_GET['ruang'])."&sttssurat=".validTeks($_GET['sttssurat'])."&sttsbalas=".validTeks($_GET['sttsbalas'])."&keyword=".validTeks($_GET['keyword']);			
        }else{
                session_start();
                session_destroy();
                if (cekSessiAdmin()){
                    session_unregister("ses_admin_suratsurat");
                }
                $url = "index.php?act=HomeAdmin";
        }          
    }
    header("Location:".$url);  
?>
