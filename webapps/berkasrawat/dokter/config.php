<?php

/***
* e-Dokter from version 0.1 Beta
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
*
* File : config.php
* Description : Main config, function and helper
* Licence under GPL
***/

if (preg_match ('/config.php/', basename($_SERVER['PHP_SELF']))) die ('Unable to access this script directly from browser!');

define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', '');
define('DB_NAME', 'sik');

$connection = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_NAME);

define('VERSION', '0.1 Beta');
define('URL', '');
define('DIR', '');
define('PRODUCTION', 'NO'); // YES to hide error page. NO to display error page.
define('SIMRSURL', 'http://localhost/webapps');

// INACBG's and VClaim Configurations
define('INACBG_KEYRS', '');
define('INACBG_URLWS', '');
define('NO_RM', ' ');
define('NO_PESERTA', '');
define('SEP', '');
define('NIK_KODER', '');
define('TIPE_RS', '');

function escape($string) {
    global $connection;
    return mysqli_real_escape_string($connection, $string);
}

function query($sql) {
    global $connection;
    $query = mysqli_query($connection, $sql);
    confirm($query);
    return $query;
}

function confirm($query) {
    global $connection;
    if(!$query) {
        die("Query failed! " . mysqli_error($connection));
    }
}

function fetch_array($result) {
    return mysqli_fetch_array($result);
}

function fetch_assoc($result) {
    return mysqli_fetch_assoc($result);
}

function num_rows($result) {
    return mysqli_num_rows($result);
}

// Get date and time

date_default_timezone_set('Asia/Makassar');
$month      = date('Y-m');
$date       = date('Y-m-d');
$time       = date('H:i:s');
$date_time  = date('Y-m-d H:i:s');

// Get settings
$getSettings = query("SELECT nama_instansi, alamat_instansi, kabupaten, propinsi FROM setting");
$dataSettings = fetch_assoc($getSettings);

// Get jenis poli
$jenispoli=isset($_SESSION['jenis_poli'])?$_SESSION['jenis_poli']:NULL;
$query_poli = query("SELECT * from poliklinik WHERE kd_poli = '".$jenispoli."'");
$data_poli = fetch_array($query_poli);
if ($jenispoli == $data_poli['0']) {
    $nmpoli = $data_poli['1'];
}

// htmlentities remove #$%#$%@ values
function clean($string) {
    return htmlentities($string);
}

// redirect to another page
function redirect($location) {
    return header("Location:".$location);
}

// add message to session
function set_message($message) {
    if(!empty($message)) {
        $_SESSION['message'] = $message;
    } else {
        $message = "";
    }
}

// display session message
function display_message() {
    if(isset($_SESSION['message'])) {
        echo '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'.$_SESSION['message'].'</div>';
        unset($_SESSION['message']);
    }
}

// show errors
function validation_errors($error) {
    $errors = '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'.$error.'</div>';
    return $errors;
}
