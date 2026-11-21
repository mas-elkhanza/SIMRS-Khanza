<?php
    session_start();
    require_once('../../conf/conf.php');

    // hanya boleh dipanggil dari halaman kamera setelah login lewat portal
    if(!isset($_SESSION['ses_admin_penandaanlokasiluka']) || $_SERVER['REQUEST_METHOD']!=='POST'){
        exit(header("Location:../index.php"));
    }

    $urlKembali = '../login.php?iyem='.encrypt_decrypt('{"usere":"'.USERHYBRIDWEB.'","passwordte":"'.PASHYBRIDWEB.'"}','e');
    $htmlHead   = "<head><title>SIMKES Khanza</title><meta name='viewport' content='width=device-width, initial-scale=1'/><link rel='stylesheet' href='../css/bootstrap.min.css'/><style>#results { padding:0px; background:#EEFFEE; width:490; height:390 }</style></head>";
    $htmlFoot   = "<br><a href='".$urlKembali."' class='btn btn-secondary'>Kembali</a></center></body>";

    $norawat = validTeks4(isset($_POST["norawat"])?$_POST["norawat"]:"",20);
    $tanggal = validTeks5(isset($_POST["tanggal"])?$_POST["tanggal"]:"",20);

    // format harus no.rawat Khanza (yyyy/mm/dd/nnnnnn) dan datetime (yyyy-mm-dd hh:mm:ss)
    if(!preg_match('~^\d{4}/\d{2}/\d{2}/\d{6}$~',$norawat) || !preg_match('~^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$~',$tanggal)){
        exit($htmlHead."<body><center>Gagal, data tidak valid ..!!".$htmlFoot);
    }

    // data harus sama dengan yang sedang diantrikan dari SIMRS
    if(getOne("select count(*) from antripenandaanlokasiluka where antripenandaanlokasiluka.no_rawat='".$norawat."' and antripenandaanlokasiluka.tanggal='".$tanggal."'")<1){
        exit($htmlHead."<body><center>Gagal, data tidak sesuai dengan antrian dari SIMRS. Silahkan petugas klik Ambil kembali di SIMRS ..!!".$htmlFoot);
    }

    // jangan menimpa bukti yang sudah ada
    if(getOne("select count(*) from bukti_penandaan_lokasi_luka where bukti_penandaan_lokasi_luka.no_rawat='".$norawat."' and bukti_penandaan_lokasi_luka.tanggal='".$tanggal."'")>0){
        exit($htmlHead."<body><center>Gagal, kemungkinan sudah dilakukan pengambilan bukti persetujuan sebelumnya ..!!".$htmlFoot);
    }

    // gambar harus berupa JPEG dari kamera (data URI base64), ukuran wajar
    $img    = isset($_POST["image"])?$_POST["image"]:"";
    $prefix = "data:image/jpeg;base64,";
    $biner  = false;
    if(strncmp($img,$prefix,strlen($prefix))===0){
        $biner = base64_decode(substr($img,strlen($prefix)),true);
    }
    $valid = ($biner!==false && strlen($biner)>1000 && strlen($biner)<=3145728 && substr($biner,0,3)==="\xFF\xD8\xFF");
    if($valid && function_exists('getimagesizefromstring')){
        $info  = @getimagesizefromstring($biner);
        $valid = ($info!==false && $info[2]===IMAGETYPE_JPEG);
    }
    if(!$valid){
        exit($htmlHead."<body><center>Gagal, gambar dari kamera tidak valid. Silahkan ambil gambar kembali ..!!".$htmlFoot);
    }

    $fileName = str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg";
    $file     = __DIR__."/upload/".$fileName;
    if(file_put_contents($file,$biner,LOCK_EX)===false){
        exit($htmlHead."<body><center>Gagal menyimpan gambar, periksa izin tulis folder pages/upload ..!!".$htmlFoot);
    }

    try {
        Tambah3("bukti_penandaan_lokasi_luka","'".$norawat."','".$tanggal."','pages/upload/$fileName'");
        echo $htmlHead."<body><center>Proses Pengambilan Bukti Persetujuan Penandaan Lokasi Luka Sudah Selesai ..!!".$htmlFoot;
    } catch (mysqli_sql_exception $e) {
        @unlink($file);
        if($e->getCode() == 1062){
            echo $htmlHead."<body><center>Gagal, kemungkinan sudah dilakukan pengambilan bukti persetujuan sebelumnya ..!!".$htmlFoot;
        } else {
            echo $htmlHead."<body><center>Gagal, silahkan ulangi ..!!".$htmlFoot;
        }
    }
?>
