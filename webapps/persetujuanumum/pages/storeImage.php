<?php
    require_once('../../conf/conf.php');
    $nosurat           = validTeks4($_POST["nosurat"],20);
    $pengobatan_kepada = validTeks4($_POST["pengobatan_kepada"],20);
    $nilai_kepercayaan = validTeks4($_POST["nilai_kepercayaan"],50);

    if(file_exists(host()."/webapps/persetujuanumum/pages/upload/".$nosurat.".jpeg")){
        @unlink(host()."/webapps/persetujuanumum/pages/upload/".$nosurat.".jpeg");
    }
    
    $img        = $_POST["image"];
    $folderPath = "upload/";
    $image_parts     = explode(";base64,", $img);
    $image_type_aux  = explode("image/", $image_parts[0]);
    $image_type      = $image_type_aux[1];
    $image_base64    = base64_decode($image_parts[1]);
    $fileName        = $nosurat.".jpeg";
    $file            = $folderPath . $fileName;
    file_put_contents($file, $image_base64);

    $urlKembali = '../login.php?iyem='.encrypt_decrypt('{"usere":"'.USERHYBRIDWEB.'","passwordte":"'.PASHYBRIDWEB.'"}','e');
    $htmlHead = "<head><title>SIMKES Khanza</title><link rel='stylesheet' href='../css/bootstrap.min.css'/><style>#results { padding:0px; background:#EEFFEE; width:490; height:390 }</style></head>";
    try {
        Tambah3("surat_persetujuan_umum_pembuat_pernyataan","'".$nosurat."','pages/upload/$fileName'");
        Ubah2("surat_persetujuan_umum","pengobatan_kepada='$pengobatan_kepada',nilai_kepercayaan='$nilai_kepercayaan' where no_surat='$nosurat'");
        echo $htmlHead."<body><center>Proses Pengambilan Persetujuan Umum Pasien/Keluarga Pasien Sudah Selesai ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
    } catch (mysqli_sql_exception $e) {
        if($e->getCode() == 1062){
            echo $htmlHead."<body><center>Gagal, kemungkinan sudah dilakukan pengambilan persetujuan sebelumnya ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
        } else {
            echo $htmlHead."<body><center>Gagal, silahkan ulangi ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
        }
    }
?>