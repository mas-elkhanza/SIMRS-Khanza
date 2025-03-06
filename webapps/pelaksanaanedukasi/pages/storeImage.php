<?php
    require_once('../../conf/conf.php');
    $norawat = validTeks4($_POST["norawat"],20);
    $tanggal = validTeks5($_POST["tanggal"],20);
    if(file_exists(host()."/webapps/pelaksanaanedukasi/pages/upload/".str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg")){
        @unlink(host()."/webapps/pelaksanaanedukasi/pages/upload/".str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg");
    }
    
    $img                    = $_POST["image"];
    $folderPath             = "upload/";
    $image_parts            = explode(";base64,", $img);
    $image_type_aux         = explode("image/", $image_parts[0]);
    $image_type             = $image_type_aux[1];
    $image_base64           = base64_decode($image_parts[1]);
    $fileName               = str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg";
    $file                   = $folderPath.$fileName;
    file_put_contents($file, $image_base64);
    
    Tambah3("bukti_pelaksanaan_informasi_edukasi","'".$norawat."','".$tanggal."','pages/upload/$fileName'");
?>
<head>
    <title>SIMKES Khanza</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css" />
    <style type="text/css">
        #results { padding: 0px; background:#EEFFEE; width: 490; height: 390 }
    </style>
</head>
<html xmlns="http://www.w3.org/1999/xhtml">
    <body><center>Proses Pengambilan Bukti Pemberian Informasi & Edukasi Pasien Sudah Selesai ..!! <br><a href='../login.php?iyem=<?=encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e")?>' class='btn btn-secondary' >Kembali</a></center></body>
</html>

