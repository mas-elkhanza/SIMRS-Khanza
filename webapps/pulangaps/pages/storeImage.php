<?php
    require_once('../../conf/conf.php');
    $nopernyataan           = validTeks4($_POST["nopernyataan"],20);
    if(file_exists(host()."/webapps/pulangaps/pages/upload/".$nopernyataan."PP.jpeg")){
        @unlink(host()."/webapps/pulangaps/pages/upload/".$nopernyataan."PP.jpeg");
    }
    
    $img                    = $_POST["image"];
    $folderPath             = "upload/";
    $image_parts            = explode(";base64,", $img);
    $image_type_aux         = explode("image/", $image_parts[0]);
    $image_type             = $image_type_aux[1];
    $image_base64           = base64_decode($image_parts[1]);
    $fileName               = $nopernyataan."PP.jpeg";
    $file                   = $folderPath . $fileName;
    file_put_contents($file, $image_base64);
    
    Tambah3("surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan","'".$nopernyataan."','pages/upload/$fileName'");
?>
<head>
    <title>SIMKES Khanza</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css" />
    <style type="text/css">
        #results { padding: 0px; background:#EEFFEE; width: 490; height: 390 }
    </style>
</head>
<html xmlns="http://www.w3.org/1999/xhtml">
    <body>
        <center>
            Proses pengambilan persetujuan Pembuat Pernyataan/Penerima Informasi sudah selesai ..!! <br/>
            Silahkan lanjutkan untuk Pengambilan Saksi 1 Keluarga<br/>
            <a href='../login2.php?iyem=<?=encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\",\"nopernyataan\":\"".$nopernyataan."\"}","e")?>' class='btn btn-secondary' >Lanjutkan</a>
        </center>
    </body>
</html>
