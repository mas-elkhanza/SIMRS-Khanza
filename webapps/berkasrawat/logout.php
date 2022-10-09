<?php	
    session_start();
    require_once "conf/command.php";
    if (cekSessiAdmin()){
        session_unregister("ses_admin_berkas_rawat");
    }
    session_start();
    session_destroy();
    header("Location:index.php");
?>
