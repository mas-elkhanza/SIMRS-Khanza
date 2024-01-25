<?php
    require_once('../conf/conf.php');
    
    if(file_exists($_POST["nik"].".jpeg")){
        @unlink($_POST["nik"].".jpeg");
    }
    
    $img            = $_POST["image"];
    $image_parts    = explode(";base64,", $img);
    $image_type_aux = explode("image/", $image_parts[0]);
    $image_type     = $image_type_aux[1];
    $image_base64   = base64_decode($image_parts[1]);
    $fileName       = $_POST["nik"].".jpeg";
    file_put_contents($fileName, $image_base64);
    Ubah2("daftarteman","photo='pages/$fileName' where nik='".$_POST["nik"]."'");
    exit(header("Location:index.php"));
?>

