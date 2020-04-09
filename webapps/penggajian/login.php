<?php
    require_once('../conf/conf.php');
	include_once "conf/command.php";
        echo "User : ".$_GET['usere']."Password ".$_GET['passwordte'];
  	if (@$_GET['act']=="login"){
            if(($_GET['usere']=="admin")&&($_GET['passwordte']=="akusayangsamakamu")) {
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
