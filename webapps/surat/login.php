<?php
	include_once "conf/command.php";
  	if ($_GET['act']=="login"){
            if(($_GET['usere']=="admin")&&($_GET['passwordte']=="akusayangsamakamu")) {
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
