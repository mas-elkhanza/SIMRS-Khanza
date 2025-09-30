<?php
    include '../conf/conf.php';
    include '../phpqrcode/qrlib.php'; 
    
    $barcode = validTeks6(str_replace("_"," ",$_GET['barcode']),50);
    if(isset($barcode)){
        $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
        $PNG_WEB_DIR    = 'temp/';
        if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
        $filename              = $PNG_TEMP_DIR.str_replace(" ","_",$barcode).'.png';
        $errorCorrectionLevel  = 'L';
        $matrixPointSize       = 4;
        QRcode::png($barcode, $filename, $errorCorrectionLevel, $matrixPointSize, 2);
    }   
?>  
