<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
    $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
    $url        = "index.php?act=Home";
    if ($_GET['act']=="login"){
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
            session_start();
            $_SESSION['ses_admin_dokumenasuransi']="admin";
            $_SESSION['kdpj']=validTeks4(str_replace("_"," ",$_GET['kdpj']),3);
            $url = "index.php?act=Kamera";			
        }else{
            session_start();
            session_destroy();
            if (cekSessiAdmin()){
                session_unregister("ses_admin_dokumenasuransi");
            }
            $url = "index.php?act=Home";
        }
    }
    header("Location:".$url);
    
?>
