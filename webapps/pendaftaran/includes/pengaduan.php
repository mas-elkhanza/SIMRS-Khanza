<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : includes/pengaduan.php
* Description : Simple chat system for pengaduan
* Licence under GPL
***/

ob_start();
session_start();

include ('../config.php');

$message = $_POST['message'];

if($message != "") {
 $sql = "INSERT INTO pengaduan VALUES(NULL,current_time(), '$_SESSION[username]', '$message')";
 query($sql);
}

$sql = "SELECT a.no_rkm_medis, a.nm_pasien, b.username, b.message FROM pasien a, pengaduan b WHERE a.no_rkm_medis = b.username ORDER BY id ASC LIMIT 100";
$result = query($sql);

while($row = fetch_array($result)) {
 echo $row['1'].": ".$row['3']."\n";
}
?>
