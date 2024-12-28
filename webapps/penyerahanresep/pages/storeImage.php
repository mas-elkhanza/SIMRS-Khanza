<?php
    require_once('../../conf/conf.php');
    $noresep = validTeks(trim($_POST['noresep']));
    if(file_exists(host()."webapps/penyerahanresep/pages/upload/".$noresep.".jpeg")){
        @unlink(host()."webapps/penyerahanresep/pages/upload/".$noresep.".jpeg");
    }
    
    $img            = $_POST["image"];
    $folderPath     = "upload/";
    $image_parts    = explode(";base64,", $img);
    $image_type_aux = explode("image/", $image_parts[0]);
    $image_type     = $image_type_aux[1];
    $image_base64   = base64_decode($image_parts[1]);
    $fileName       = $noresep.".jpeg";
    $file           = $folderPath . $fileName;
    file_put_contents($file, $image_base64);
    
    Tambah3("bukti_penyerahan_resep_obat","'".$noresep."','pages/upload/$fileName'");
    Ubah2("resep_obat","tgl_penyerahan=current_date(),jam_penyerahan=current_time() where no_resep='$noresep'");
?>
<head>
    <title>SIMKES Khanza</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css" />
    <style type="text/css">
        #results { padding: 0px; background:#EEFFEE; width: 490; height: 390 }
    </style>
</head>
<html xmlns="http://www.w3.org/1999/xhtml">
    <body><center>Proses Penyerahan Resep Selesai ..!! <br><a href='../login.php?usere=<?=USERHYBRIDWEB?>&passwordte=<?=PASHYBRIDWEB?>' class='btn btn-secondary' >Kembali</a></center></body>
</html>
