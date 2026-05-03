<?php
    require_once('../../conf/conf.php');
    $nopersetujuan = validTeks4($_POST["nopersetujuan"],20);
    if(file_exists(host()."/webapps/persetujuanrawatinap/pages/upload/".$nopersetujuan.".jpeg")){
        @unlink(host()."/webapps/persetujuanrawatinap/pages/upload/".$nopersetujuan.".jpeg");
    }

    $img            = $_POST["image"];
    $folderPath     = "upload/";
    $image_parts    = explode(";base64,", $img);
    $image_type_aux = explode("image/", $image_parts[0]);
    $image_type     = $image_type_aux[1];
    $image_base64   = base64_decode($image_parts[1]);
    $fileName       = $nopersetujuan.".jpeg";
    $file           = $folderPath . $fileName;
    file_put_contents($file, $image_base64);

    $urlKembali = '../login.php?iyem='.encrypt_decrypt('{"usere":"'.USERHYBRIDWEB.'","passwordte":"'.PASHYBRIDWEB.'"}','e');
    $htmlHead   = "<head><title>SIMKES Khanza</title><link rel='stylesheet' href='../css/bootstrap.min.css'/><style>#results { padding:0px; background:#EEFFEE; width:490; height:390 }</style></head>";

    try {
        Tambah3("surat_persetujuan_rawat_inap_pembuat_pernyataan","'".$nopersetujuan."','pages/upload/$fileName'");
        echo $htmlHead."<body><center>Proses Pengambilan Persetujuan Pasien Rawat Inap Sudah Selesai ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
    } catch (mysqli_sql_exception $e) {
        if($e->getCode() == 1062){
            echo $htmlHead."<body><center>Gagal, kemungkinan sudah dilakukan pengambilan persetujuan sebelumnya ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
        } else {
            echo $htmlHead."<body><center>Gagal, silahkan ulangi ..!! <br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";
        }
    }
?>