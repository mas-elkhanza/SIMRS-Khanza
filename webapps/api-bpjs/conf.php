<?php

//user untuk bpjs
$username = "fauzan";
$password = "123";

date_default_timezone_set('Asia/Jakarta');
//koneksi database
$db_hostname = "localhost";
$db_username = "root";
$db_password = "";
$db_name = "sik";

//version php
$version_php = "7";

function php() {
    global $version_php;
    return $version_php;
}

function host() {
    global $db_hostname;
    return $db_hostname;
}

function bukakoneksi() {
    global $db_hostname, $db_username, $db_password, $db_name;
    if (php() <= 6) {
        $konektor = mysql_connect($db_hostname, $db_username, $db_password)
                or die("<font color=red><h3>Not Connected ..!!</h3></font>");
        $db_select = mysql_select_db($db_name)
                or die("<font color=red><h3>Cannot chose database..!!</h3></font>");
    } else {
        $konektor = mysqli_connect($db_hostname, $db_username, $db_password)
                or die("<font color=red><h3>Not Connected ..!!</h3></font>");
        $db_select = mysqli_select_db($konektor, $db_name)
                or die("<font color=red><h3>Cannot chose database..!!</h3></font>");
    }
    return $konektor;
}

function fetch_array($sql) {
    if (php() <= 6) {
        $while = mysql_fetch_array($sql);
    } else {
        $while = mysqli_fetch_array(bukakoneksi(), $sql);
    }
    return $while;
}

function noRegDokter($kd_dokter,$tanggal) {
    $no_reg_akhir = fetch_array(bukaquery("SELECT max(no_reg) FROM booking_registrasi WHERE kd_dokter='$kd_dokter' and tanggal_periksa='$tanggal'"));
    $no_urut_reg = substr($no_reg_akhir[0], 0, 3);
    $no_reg = sprintf('%03s', ($no_urut_reg + 1));
    return $no_reg;
}

function noRegPoli($kd_poli,$tanggal) {
    $no_reg_akhir = fetch_array(bukaquery("SELECT max(no_reg) FROM booking_registrasi WHERE kd_poli='$kd_poli' and tanggal_periksa='$tanggal'"));
    $no_urut_reg = substr($no_reg_akhir[0], 0, 3);
    $no_reg = sprintf('%03s', ($no_urut_reg + 1));
    return $no_reg;
}

function fetch_assoc($sql) {
    if (php() <= 6) {
        $while = mysql_fetch_assoc($sql);
    } else {
        $while = mysqli_fetch_assoc(bukakoneksi(), $sql);
    }
    return $while;
}

function num_rows($sql) {
    if (php() <= 6) {
        $while = mysql_num_rows($sql);
    } else {
        $while = mysqli_num_rows(bukakoneksi(), $sql);
    }
    return $while;
}

function tutupkoneksi() {
    global $konektor;
    if (php() <= 6) {
        mysql_close($konektor);
    } else {
        mysqli_close($konektor);
    }
}

function bukaquery($sql) {
    bukakoneksi();
    if (php() <= 6) {
        $result = mysql_query($sql)
                or die(mysql_error() . "<br/><font color=red><b>hmmmmmmm.....??????????</b>");
    } else {
        $result = mysqli_query(bukakoneksi(), $sql)
                or die(mysqli_error(bukakoneksi()) . "<br/><font color=red><b>hmmmmmmm.....??????????</b>");
    }
    return $result;
}

function bukainput($sql) {
    bukakoneksi();
    if (php() <= 6) {
        $result = mysql_query($sql)
//or die(mysql_error()."<br/><font color=red><b>Gagal</b>, Ada data dengan primary key yang sama !");
                or die("<br/><script>alert('Gagal, Ada data dengan primary key yang sama !');window.location = 'javascript:history.go(-1)'</script>");
    } else {
        $result = mysqli_query(bukakoneksi(), $sql)
//or die(mysql_error()."<br/><font color=red><b>Gagal</b>, Ada data dengan primary key yang sama !");
                or die("<br/><script>alert('Gagal, Ada data dengan primary key yang sama !');window.location = 'javascript:history.go(-1)'</script>");
    }
    return $result;
}

function FormatTgl($format, $tanggal) {
    return date($format, strtotime($tanggal));
}

function hariindo($x) {
    $hari = FormatTgl("D", $x);

    switch ($hari) {
        case 'Sun':
            $hari_ini = "Minggu";
            break;

        case 'Mon':
            $hari_ini = "Senin";
            break;

        case 'Tue':
            $hari_ini = "Selasa";
            break;

        case 'Wed':
            $hari_ini = "Rabu";
            break;

        case 'Thu':
            $hari_ini = "Kamis";
            break;

        case 'Fri':
            $hari_ini = "Jumat";
            break;

        case 'Sat':
            $hari_ini = "Sabtu";
            break;

        default:
            $hari_ini = "Tidak di ketahui";
            break;
    }

    return $hari_ini;
}

function getOne($sql) {
    $hasil = bukaquery($sql);
    list($result) = fetch_array($hasil);
    return $result;
}

function getToken() {
    $header = json_encode(['typ' => 'JWT', 'alg' => 'HS256']);

// Create token payload as a JSON string
    $payload = json_encode(['username' => 'fauzan', 'password' => 123]);

// Encode Header to Base64Url String
    $base64UrlHeader = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($header));

// Encode Payload to Base64Url String
    $base64UrlPayload = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($payload));

// Create Signature Hash
    $signature = hash_hmac('sha256', $base64UrlHeader . "." . $base64UrlPayload, 'abC123!', true);

// Encode Signature to Base64Url String
    $base64UrlSignature = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($signature));

// Create JWT
    $jwt = $base64UrlHeader . "." . $base64UrlPayload . "." . $base64UrlSignature;

    return $jwt;
}

function getUserIpAddr() {
    if (!empty($_SERVER['HTTP_CLIENT_IP'])) {
        //ip from share internet
        $ip = $_SERVER['HTTP_CLIENT_IP'];
    } elseif (!empty($_SERVER['HTTP_X_FORWARDED_FOR'])) {
        //ip pass from proxy
        $ip = $_SERVER['HTTP_X_FORWARDED_FOR'];
    } else {
        $ip = $_SERVER['REMOTE_ADDR'];
    }
    return $ip;
}

function RequestStatus($massage, $code) {
    $response = array(
        'message' => 'OK',
        'code' => 200
    );
    return $response;
}
?>

