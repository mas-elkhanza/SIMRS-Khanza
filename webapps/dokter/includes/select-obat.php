<?php

/***
* e-Dokter from version 0.1 Beta
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
*
* File : includes/select-obat.php
* Description : Get databarang data from json encode by select2
* Licence under GPL
***/

ob_start();
session_start();

include_once('../config.php');
 
$q = $_GET['q'];
 
$sql = query("SELECT kode_brng AS id, nama_brng AS text FROM databarang WHERE (kode_brng LIKE '%".$q."%' OR nama_brng LIKE '%".$q."%')");
$json = [];

while($row = fetch_assoc($sql)){
     $json[] = ['id'=>$row['id'], 'text'=>$row['text']];
}
echo json_encode($json);


?>
