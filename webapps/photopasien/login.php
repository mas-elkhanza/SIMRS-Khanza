<?php
    include_once "conf/command.php";
    if ($_GET['act']=="login"){
        if(($_GET['usere']=="admin")&&($_GET['passwordte']=="akusayangsamakamu")) {
            session_start();
            $_SESSION['ses_admin']="admin";
            $_SESSION['pasien']=$_GET['norm'];
            $url = "index.php?act=Kamera";			
        }else{
                session_start();
                session_destroy();
                if (cekSessiAdmin()){
                    session_unregister("ses_admin");
                }
                $url = "index.php?act=Home";
        }
        header("Location:".$url);
    }
    
?>
