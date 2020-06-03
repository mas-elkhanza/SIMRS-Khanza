<?php
    session_start();
    header("Content-type: image/png");
    $_SESSION["Captcha"]="";

    // membuat gambar dengan menentukan ukuran
    $gbr	= imagecreate(200, 50);

    //warna background kotak captcha biru
    imagecolorallocate($gbr, 30, 144, 255);

    // pengaturan font captcha
    $color	= imagecolorallocate($gbr, 253, 252, 252);
    $font	= "blackjack.otf"; 
    $ukuran_font = 20;
    $posisi = 32;

    // membuat nomor random acak dan ditampilkan pada gambar
    for($i=0;$i<=5;$i++) {
            // jumlah karakter
            $angka=rand(0, 9);
            $_SESSION["Captcha"].=$angka;
            $kemiringan= rand(20, 30);
            imagettftext($gbr, $ukuran_font, $kemiringan, 8+15*$i, $posisi, $color, $font, $angka);	
    }

    //untuk membuat atau generate gambar 
    imagepng($gbr); 
    imagedestroy($gbr);
?>