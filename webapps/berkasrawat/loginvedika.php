<?php
    include_once "../conf/conf.php";
    include_once "conf/command.php";
    $act=isset($_GET['act'])?$_GET['act']:NULL;
    if ($act=="login"){
        $usere      = validTeks(trim($_POST['usere'])?$_POST['usere']:NULL);
        $passwordte = validTeks(trim($_POST['passwordte'])?$_POST['passwordte']:NULL);
        
        $sql = "SELECT password_asuransi.kd_pj FROM password_asuransi WHERE password_asuransi.usere=aes_encrypt('".$usere."','nur') AND password_asuransi.passworde=aes_encrypt('".$passwordte."','windi')";
        $hasil=bukaquery($sql);
        if($baris = mysqli_fetch_array($hasil)) {
            session_start();
            $_SESSION["ses_admin_berkas_rawat"]="admin";
            header("Location:index.php?act=ListVedika&carabayar=".str_replace("_"," ",$baris[0]));
        }else{
            session_start();
            session_destroy();
            if (cekSessiAdmin()){
                session_unregister("ses_admin_berkas_rawat");
            }
            header("Location:index.php");
        }
    }
    
?>
