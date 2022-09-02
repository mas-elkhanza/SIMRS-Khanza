<?php	
    session_start();
    require_once "conf/command.php";
    if (cekSessiAdmin()){
        $_SESSION["ses_kunjung"]="ses_kunjung";
    }
    session_destroy();
    header("Location:index.php");
?>
