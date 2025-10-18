<?php
    include '../conf/conf.php';
    include '../phpqrcode/qrlib.php'; 
    
    $barcode = validTeks8($_GET['barcode'],50);
    if(isset($barcode)){
        $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
        $PNG_WEB_DIR    = 'temp/';
        if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
        $filename              = $PNG_TEMP_DIR.$barcode.'.png';
        $errorCorrectionLevel  = 'L';
        $matrixPointSize       = 4;
        $barcode=str_replace("_"," ",$barcode);
        $barcode=str_replace("garing","/",$barcode);
        $barcode=str_replace("cross","#",$barcode);
        QRcode::png($barcode, $filename, $errorCorrectionLevel, $matrixPointSize, 2);
    }   
?>  
