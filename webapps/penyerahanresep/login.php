<?php
    include_once "conf/command.php";
    session_start();
    $_SESSION['ses_admin']="admin";
    $url = "index.php?act=Kamera";	
    header("Location:".$url);
?>
