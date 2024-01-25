<?php
    include '../conf/conf.php';
    include '../phpqrcode/qrlib.php'; 
    
    $kodedokter = validTeks(str_replace("_"," ",$_GET['kodedokter']));
    if(isset($kodedokter)){
        $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
        $PNG_WEB_DIR    = 'temp/';
        if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
        $filename              = $PNG_TEMP_DIR.$kodedokter.'.png';
        $errorCorrectionLevel  = 'L';
        $matrixPointSize       = 4;
        $setting               = mysqli_fetch_array(bukaquery("select nama_instansi,kabupaten from setting"));
        QRcode::png("Dikeluarkan di ".$setting["nama_instansi"].", Kabupaten/Kota ".$setting["kabupaten"]."\nDitandatangani secara elektronik oleh ".getOne("select nm_dokter from dokter where kd_dokter='$kodedokter'")."\nID ".getOne3("select ifnull(sha1(sidikjari.sidikjari),'".$kodedokter."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$kodedokter."'",$kodedokter)."\n".date('d-m-Y'), $filename, $errorCorrectionLevel, $matrixPointSize, 2);
    }   
?>  
