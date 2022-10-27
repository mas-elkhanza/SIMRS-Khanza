<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
    $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
    $url        = "index.php?act=HomeAdmin";
    if ($_GET['act']=="login"){
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
            session_start();
            $_SESSION['ses_admin_casemix']="admin";
            $codernik= validTeks($_GET['codernik']);
            if(($_GET['page']=="KlaimBaruOtomatis")){
                $url = "index.php?act=KlaimBaruOtomatis&codernik=".$codernik;	
            }else if(($_GET['page']=="KlaimBaruManual")){
                $url = "index.php?act=KlaimBaruManual&action=no&codernik=".$codernik;	
            }else if(($_GET['page']=="KlaimBaruManual2")){
                $url = "index.php?act=KlaimBaruManual2&action=no&codernik=".$codernik;	
            }                 		
        }else{
            session_start();
            session_destroy();
            if (cekSessiAdmin()){
                session_unregister("ses_admin_casemix");
            }
            $url = "index.php?act=HomeAdmin";
        }
    }
    header("Location:".$url);
?>
