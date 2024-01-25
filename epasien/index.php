<?php
    ob_start();
    session_start();
    date_default_timezone_set('Asia/Jakarta');
    require_once('conf/command.php');
    require_once('conf/conf.php');
    header("Expires: Tue, 01 Jan 2000 00:00:00 GMT");
    header("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT");
    header("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
    header("Cache-Control: post-check=0, pre-check=0", false);
    header("Pragma: no-cache");
    
    if(!isset($_SESSION["nama_instansi"])){
        $querypengaturan                    = bukaquery("select * from setting");
        while($pengaturan = mysqli_fetch_array($querypengaturan)) {
            $_SESSION["nama_instansi"]      = $pengaturan["nama_instansi"];
            $_SESSION["alamat_instansi"]    = $pengaturan["alamat_instansi"];
            $_SESSION["kabupaten"]          = $pengaturan["kabupaten"];
            $_SESSION["propinsi"]           = $pengaturan["propinsi"];
            $_SESSION["kontak"]             = $pengaturan["kontak"];
            $_SESSION["email"]              = $pengaturan["email"];
            $_SESSION["kode_ppk"]           = $pengaturan["kode_ppk"];
            $_SESSION["kode_ppkinhealth"]   = $pengaturan["kode_ppkinhealth"];
            $_SESSION["kode_ppkkemenkes"]   = $pengaturan["kode_ppkkemenkes"];
        }
    }
    
    if(!isset($_SESSION["ses_pasien"])){
        include_once "indexpengunjung.php";
    }else{
        include_once "indexuser.php";
    }
?>
