<?php
// === Konfigurasi dasar ===
// Jika script ini berada di webroot (mis: /var/www/html), file akan disimpan di /HasilLab
// Jika script ini berada di dalam /HasilLab, file akan disimpan di folder itu (tanpa dobel)
$auth_token     = "3abf9a5336514531bad62caf533c36c7"; // samakan dengan di Java
$max_file_count = 10;

// --- Helper: ambil header Authorization (case-insensitive) ---
function get_authorization_header() {
    if (function_exists('apache_request_headers')) {
        $headers = apache_request_headers();
        foreach ($headers as $k => $v) {
            if (strtolower($k) === 'authorization') return $v;
        }
    }
    if (isset($_SERVER['HTTP_AUTHORIZATION'])) return $_SERVER['HTTP_AUTHORIZATION'];
    if (isset($_SERVER['REDIRECT_HTTP_AUTHORIZATION'])) return $_SERVER['REDIRECT_HTTP_AUTHORIZATION'];
    return null;
}

// --- Force JSON response ---
header('Content-Type: application/json; charset=utf-8');

// --- Method check (optional, tapi bagus) ---
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    echo json_encode(['ok'=>false,'message'=>'Method Not Allowed']);
    exit;
}

// --- Auth check ---
$auth = get_authorization_header();
if (!$auth || $auth !== 'Bearer ' . $auth_token) {
    http_response_code(401);
    echo json_encode(['ok'=>false,'message'=>'Unauthorized']);
    exit;
}

// --- Validasi upload ---
if (!isset($_FILES['file']) || $_FILES['file']['error'] !== UPLOAD_ERR_OK) {
    http_response_code(400);
    echo json_encode(['ok'=>false,'message'=>'No file uploaded or upload error']);
    exit;
}

// --- Tentukan targetDir & publicDir tanpa dobel ---
// Jika folder saat ini namanya "HasilLab", simpan di folder ini.
// Jika tidak, simpan di subfolder "HasilLab" di bawah folder skrip.
$scriptDir  = __DIR__;
$publicDir  = '/HasilLab'; // path publik di URL
if (basename($scriptDir) === 'HasilLab') {
    $targetDir = $scriptDir; // sudah di HasilLab → jangan nambah HasilLab lagi
} else {
    $targetDir = $scriptDir . DIRECTORY_SEPARATOR . 'HasilLab';
}

if (!is_dir($targetDir)) {
    if (!mkdir($targetDir, 0755, true) && !is_dir($targetDir)) {
        http_response_code(500);
        echo json_encode(['ok'=>false,'message'=>'Failed to create target directory']);
        exit;
    }
}

// --- Sanitasi nama file ---
$origName = $_FILES['file']['name'];
$filename = basename($origName);                                      // buang path dari client
$filename = preg_replace('/[^A-Za-z0-9._-]/', '_', $filename);        // amankan
$target   = rtrim($targetDir, '/\\') . DIRECTORY_SEPARATOR . $filename;

// --- Replace jika sudah ada ---
if (file_exists($target)) {
    @unlink($target);
}

// --- Pindahkan file ---
if (!move_uploaded_file($_FILES['file']['tmp_name'], $target)) {
    http_response_code(500);
    echo json_encode(['ok'=>false,'message'=>'Failed to move uploaded file']);
    exit;
}

// --- Housekeeping: jaga maksimal N file .pdf ---
$pdf_files = glob(rtrim($targetDir, '/\\') . DIRECTORY_SEPARATOR . '*.pdf') ?: [];
if (count($pdf_files) > $max_file_count) {
    usort($pdf_files, function($a, $b){ return filemtime($a) <=> filemtime($b); });
    $to_delete = array_slice($pdf_files, 0, count($pdf_files) - $max_file_count);
    foreach ($to_delete as $old) { @unlink($old); }
}

// --- Bangun URL publik yang benar (tanpa dobel HasilLab) ---
$scheme  = (!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] !== 'off') ? 'https' : 'http';
$host    = $_SERVER['HTTP_HOST'] ?? 'localhost';
$fileUrl = $scheme . '://' . $host . rtrim($publicDir, '/') . '/' . rawurlencode($filename);

// --- Selesai ---
echo json_encode(['ok'=>true,'url'=>$fileUrl], JSON_UNESCAPED_SLASHES);