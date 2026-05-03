<?php
    require_once('../../conf/conf.php');
    $norawat = validTeks4($_POST["norawat"],20);
    $tanggal = validTeks5($_POST["tanggal"],20);
    if(file_exists(host()."/webapps/pelaksanaanedukasi/pages/upload/".str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg")){
        @unlink(host()."/webapps/pelaksanaanedukasi/pages/upload/".str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg");
    }

    $img            = $_POST["image"];
    $folderPath     = "upload/";
    $image_parts    = explode(";base64,", $img);
    $image_type_aux = explode("image/", $image_parts[0]);
    $image_type     = $image_type_aux[1];
    $image_base64   = base64_decode($image_parts[1]);
    $fileName       = str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg";
    $file           = $folderPath.$fileName;
    file_put_contents($file, $image_base64);

    $urlKembali = '../login.php?iyem='.encrypt_decrypt('{"usere":"'.USERHYBRIDWEB.'","passwordte":"'.PASHYBRIDWEB.'"}','e');
    $htmlHead   = "<head><title>SIMKES Khanza</title><link rel='stylesheet' href='../css/bootstrap.min.css'/><style>#results { padding:0px; background:#EEFFEE; width:490; height:390 }</style></head>";

    try {
        Tambah3("bukti_pelaksanaan_informasi_edukasi","'".$norawat."','".$tanggal."','pages/upload/$fileName'");
        echo $htmlHead."<body><center>Proses Pengambilan Bukti Pemberian Informasi & Edukasi Pasien Sudah Selesai ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
    } catch (mysqli_sql_exception $e) {
        if($e->getCode() == 1062){
            echo $htmlHead."<body><center>Gagal, kemungkinan sudah dilakukan pengambilan bukti edukasi sebelumnya ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
        } else {
            echo $htmlHead."<body><center>Gagal, silahkan ulangi ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
        }
    }
?>