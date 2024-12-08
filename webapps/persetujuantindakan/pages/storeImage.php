<?php
    require_once('../../conf/conf.php');
    $nopernyataan           = validTeks4($_POST["nopernyataan"],20);
    if(file_exists(host()."/webapps/persetujuantindakan/pages/upload/".$nopernyataan."PP.jpeg")){
        @unlink(host()."/webapps/persetujuantindakan/pages/upload/".$nopernyataan."PP.jpeg");
    }
    
    $tindakan_konfirmasi    = "false";
    $diagnosa_konfirmasi    = "false";
    $indikasi_tindakan_konfirmasi ="false";
    $tata_cara_konfirmasi   = "false";
    $tujuan_konfirmasi      = "false";
    $risiko_konfirmasi      = "false";
    $komplikasi_konfirmasi  = "false";
    $prognosis_konfirmasi   = "false";
    $alternatif_konfirmasi  = "false";
    $lain_lain_konfirmasi   = "false";
    $biaya_konfirmasi       = "false";
    
    if(isset($_POST["tindakan_konfirmasi"])) {
        $tindakan_konfirmasi = "true";
    }
    if(isset($_POST["diagnosa_konfirmasi"])) {
        $diagnosa_konfirmasi = "true";
    }
    if(isset($_POST["indikasi_tindakan_konfirmasi"])) {
        $indikasi_tindakan_konfirmasi = "true";
    }
    if(isset($_POST["tata_cara_konfirmasi"])) {
        $tata_cara_konfirmasi = "true";
    }
    if(isset($_POST["tujuan_konfirmasi"])) {
        $tujuan_konfirmasi = "true";
    }
    if(isset($_POST["risiko_konfirmasi"])) {
        $risiko_konfirmasi = "true";
    }
    if(isset($_POST["komplikasi_konfirmasi"])) {
        $komplikasi_konfirmasi = "true";
    }
    if(isset($_POST["prognosis_konfirmasi"])) {
        $prognosis_konfirmasi = "true";
    }
    if(isset($_POST["alternatif_konfirmasi"])) {
        $alternatif_konfirmasi = "true";
    }
    if(isset($_POST["lain_lain_konfirmasi"])) {
        $lain_lain_konfirmasi = "true";
    }
    if(isset($_POST["biaya_konfirmasi"])) {
        $biaya_konfirmasi = "true";
    }
    
    $pilihansetuju          = validTeks4($_POST["pilihansetuju"],20);
    $img                    = $_POST["image"];
    $folderPath             = "upload/";
    $image_parts            = explode(";base64,", $img);
    $image_type_aux         = explode("image/", $image_parts[0]);
    $image_type             = $image_type_aux[1];
    $image_base64           = base64_decode($image_parts[1]);
    $fileName               = $nopernyataan."PP.jpeg";
    $file                   = $folderPath . $fileName;
    file_put_contents($file, $image_base64);
    
    Tambah3("bukti_persetujuan_penolakan_tindakan_penerimainformasi","'".$nopernyataan."','pages/upload/$fileName'");
    Ubah2("persetujuan_penolakan_tindakan","diagnosa_konfirmasi='$diagnosa_konfirmasi',tindakan_konfirmasi='$tindakan_konfirmasi',indikasi_tindakan_konfirmasi='$indikasi_tindakan_konfirmasi',
           tata_cara_konfirmasi='$tata_cara_konfirmasi',tujuan_konfirmasi='$tujuan_konfirmasi',risiko_konfirmasi='$risiko_konfirmasi',komplikasi_konfirmasi='$komplikasi_konfirmasi',
           prognosis_konfirmasi='$prognosis_konfirmasi',alternatif_konfirmasi='$alternatif_konfirmasi',biaya_konfirmasi='$biaya_konfirmasi',lain_lain_konfirmasi='$lain_lain_konfirmasi',
           pernyataan='$pilihansetuju' where no_pernyataan='$nopernyataan'");
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
