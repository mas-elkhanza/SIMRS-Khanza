<?php
    include_once "../conf/conf.php";
    include_once "conf/command.php";
    $act=isset($_GET['act'])?$_GET['act']:NULL;
    if ($act=="login"){
        $usere      =trim($_POST['usere'])?$_POST['usere']:NULL;
        $passwordte =trim($_POST['passwordte'])?$_POST['passwordte']:NULL;
        
        $sql = "SELECT kd_pj FROM password_asuransi WHERE usere=aes_encrypt('".$usere."','nur') AND passworde=aes_encrypt('".$passwordte."','windi')";
        $hasil=bukaquery($sql);
        if($baris = mysqli_fetch_array($hasil)) {
            session_start();
            $_SESSION["ses_admin"]="admin";
            header("Location:index.php?act=ListVedika&carabayar=".str_replace("_"," ",$baris[0]));
        }else{
            session_start();
            session_destroy();
            $_SESSION["ses_kunjung"]="ses_kunjung";
            header("Location:index.php");
        }
    }
    
?>
