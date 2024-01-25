<?php
    session_start();
    require_once("../conf/conf.php");
    header("Content-type: image/png");
    $_SESSION["Capcay"]     = "";
    $gbr                    = imagecreate(200, 50);
    imagecolorallocate($gbr, 30, 144, 255);
    $color                  = imagecolorallocate($gbr, 253, 252, 252);
    $font                   = "blackjack.otf"; 
    $ukuran_font            = 20;
    $posisi                 = 32;

    for($i=0;$i<=5;$i++) {
        $angka              = rand(0, 9);
        $_SESSION["Capcay"].= $angka;
        $kemiringan         = rand(20, 30);
        imagettftext($gbr, $ukuran_font, $kemiringan, 8+15*$i, $posisi, $color, $font, $angka);	
    }
    
    $_SESSION["Capcay"]     = getOne2("select aes_encrypt(".$_SESSION["Capcay"].",'windi')");

    imagepng($gbr); 
    imagedestroy($gbr);
?>