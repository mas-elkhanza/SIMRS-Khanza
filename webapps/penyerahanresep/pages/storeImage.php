<?php
    require_once('../../conf/conf.php');
    $noresep = validTeks4($_POST['noresep'],20);
    if(file_exists(host()."/webapps/penyerahanresep/pages/upload/".$noresep.".jpeg")){
        @unlink(host()."/webapps/penyerahanresep/pages/upload/".$noresep.".jpeg");
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

    $urlKembali = '../login.php?iyem='.encrypt_decrypt('{"usere":"'.USERHYBRIDWEB.'","passwordte":"'.PASHYBRIDWEB.'"}','e');
    $htmlHead   = "<head><title>SIMKES Khanza</title><link rel='stylesheet' href='../css/bootstrap.min.css'/><style>#results { padding:0px; background:#EEFFEE; width:490; height:390 }</style></head>";

    try {
        Tambah3("bukti_penyerahan_resep_obat","'".$noresep."','pages/upload/$fileName'");
        Ubah2("resep_obat","tgl_penyerahan=current_date(),jam_penyerahan=current_time() where no_resep='$noresep'");
        echo $htmlHead."<body><center>Proses Penyerahan Resep Selesai ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
    } catch (mysqli_sql_exception $e) {
        if($e->getCode() == 1062){
            echo $htmlHead."<body><center>Gagal, kemungkinan sudah dilakukan penyerahan resep sebelumnya ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
        } else {
            echo $htmlHead."<body><center>Gagal, silahkan ulangi ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
        }
    }
?>