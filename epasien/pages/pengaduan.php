<?php
    ob_start();
    session_start();
    require_once('../conf/conf.php');
    $pesan      = trim(isset($_POST['pesan']))?trim($_POST['pesan']):NULL;
    $pesan      = cleankar($pesan);
    $sekarang   = date("Y-m-d");
    $max        = getOne("select ifnull(MAX(CONVERT(RIGHT(id,6),signed)),0)+1 from pengaduan where tanggal like '%$sekarang%'");
    $no_urut    = str_replace("-","",$sekarang).sprintf("%06s", $max);
   
    if($pesan != "") {
        Tambah4("pengaduan","'$no_urut','".date("Y-m-d H:i:s")."','".encrypt_decrypt($_SESSION["ses_pasien"],"d")."','$pesan'");
    }
?>
