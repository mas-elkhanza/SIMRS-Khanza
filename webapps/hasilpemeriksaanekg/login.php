<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
    $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
    $url        = "index.php?act=HomeAdmin";
    if ($_GET['act']=="login"){
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
            session_start();
            $_SESSION['ses_admin_gambarpemeriksaanekg']="admin";
            $url = "index.php?act=List&no_rawat=".validTeks4($_GET['no_rawat'],20);			
        }else{
            session_start();
            session_destroy();
            if (cekSessiAdmin()){
                session_unregister("ses_admin_gambarpemeriksaanekg");
            }
            $url = "index.php?act=HomeAdmin";
        }
    }
    header("Location:".$url);
    
?>
