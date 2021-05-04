<?php

if (preg_match ('/config.php/', basename($_SERVER['PHP_SELF']))) die ('Unable to access this script directly from browser!');

define ("DB_HOST","localhost");
define ("DB_USER","root");
define ("DB_PASS","");
define ("DB_NAME","sik");

$kd_pj = 'BPJ'; //Sesuaikan dengan kd_pj di tabel password_asuransi

// Get date and time
date_default_timezone_set('Asia/Jakarta');
$month      = date('Y-m');
$date       = date('Y-m-d');
$time       = date('H:i:s');
$date_time  = date('Y-m-d H:i:s');

$connection = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_NAME);

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
        //die("Query failed! " . mysqli_error($connection));
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

// For SLEMP user only.... hehehe....
if (!function_exists('apache_request_headers')) {
    function apache_request_headers() {
        $return = array();
        foreach($_SERVER as $key=>$value) {
            if (substr($key,0,5)=="HTTP_") {
                $key=str_replace(" ","-",ucwords(strtolower(str_replace("_"," ",substr($key,5)))));
                $return[$key]=$value;
            }else{
                $return[$key]=$value;
	        }
        }
        return $return;
    }
}

function validation_errors($error) {
    $errors = $error;
    return $errors;
}

?>
