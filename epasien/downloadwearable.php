<?php
    $file = __DIR__ . "/wearable.shortcut";

    if (!file_exists($file)) {
        http_response_code(404);
        exit("File tidak ditemukan");
    }
    
    while (ob_get_level()) {
        ob_end_clean();
    }

    header("Content-Description: File Transfer");
    header("Content-Type: application/octet-stream");
    header("Content-Disposition: attachment; filename=\"wearable.shortcut\"");
    header("Content-Length: " . filesize($file));
    header("Cache-Control: no-store, no-cache, must-revalidate");
    header("Pragma: no-cache");
    header("Expires: 0");

    readfile($file);
    exit;
?>