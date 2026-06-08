<?php
    session_start();
    require_once "conf/command.php";
    if (cekSessiAdmin()){
        unset($_SESSION["ses_admin_secondopinion"]);
    }
    session_destroy();
    header("Location:index.php");
?>
