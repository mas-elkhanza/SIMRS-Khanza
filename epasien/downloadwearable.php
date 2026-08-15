<?php
    if(isset($_GET['jenis']) && $_GET['jenis']=="apk"){
        $file = __DIR__ . "/wearable.apk";
        $nama = "wearable.apk";
        $tipe = "application/vnd.android.package-archive";
    }else{
        $file = __DIR__ . "/wearable.shortcut";
        $nama = "wearable.shortcut";
        $tipe = "application/octet-stream";
    }

    if (!file_exists($file)) {
        http_response_code(404);
        exit("File tidak ditemukan");
    }
    
    while (ob_get_level()) {
        ob_end_clean();
    }
    header("Content-Description: File Transfer");
    header("Content-Type: " . $tipe);
    header("Content-Disposition: attachment; filename=\"" . $nama . "\"");
    header("Content-Length: " . filesize($file));
    header("Cache-Control: no-store, no-cache, must-revalidate");
    header("Pragma: no-cache");
    header("Expires: 0");
    readfile($file);
    exit;
?>