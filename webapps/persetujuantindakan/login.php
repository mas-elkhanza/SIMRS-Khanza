<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
    $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
    $url        = "index.php?act=Home";
    if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
        session_start();
        $_SESSION['ses_admin']="admin";
        $url = "index.php?act=Kamera";
    }	
    header("Location:".$url);
?>
