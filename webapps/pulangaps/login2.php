<?php
    include_once "conf/command.php";
    require_once('../conf/conf.php');
    
    $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
    $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
    $url        = "index.php?act=Home";
    if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
        session_start();
        $nopernyataan=validTeks($_GET['nopernyataan']); 
        $_SESSION['ses_admin_pulangaps']="admin";
        $url = "index.php?act=Kamera2&nopernyataan=$nopernyataan";
    }	
    header("Location:".$url);
?>
