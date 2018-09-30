<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : includes/informasi-kamar.php
* Description : Datatable ajax file for room information
* Licence under GPL
***/

ob_start();
session_start();

include ('../config.php');

$table = <<<EOT
 (
    SELECT  nama.kelas
    ,   (
            SELECT COUNT(*)
            FROM   kamar
            WHERE  kelas = nama.kelas
            AND statusdata='1'
        ) AS total
    ,   (
            SELECT COUNT(*)
            FROM   kamar
            WHERE  kelas = nama.kelas
            AND statusdata='1'
            AND status='ISI'
        ) AS isi
    ,   (
            SELECT COUNT(*)
            FROM   kamar
            WHERE  kelas = nama.kelas
            AND statusdata='1'
            AND status='KOSONG'
        ) AS kosong
    FROM    (
            SELECT DISTINCT kelas
            FROM   kamar
            WHERE statusdata='1'
        ) AS nama
 ) temp
EOT;

$primaryKey = 'total';
$columns = array(
    array( 'db' => 'kelas','dt' => 0 ),
    array( 'db' => 'total','dt' => 1 ),
    array( 'db' => 'isi','dt' => 2 ),
    array( 'db' => 'kosong','dt' => 3 ),
);

$sql_details = array(
    'user' => DB_USER,
    'pass' => DB_PASS,
    'db'   => DB_NAME,
    'host' => DB_HOST
);
require('ssp.class.php');
echo json_encode(
    SSP::simple( $_GET, $sql_details, $table, $primaryKey, $columns )
);
?>
