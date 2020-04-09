<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : includes/informasi-bangsal.php
* Description : Datatable ajax file for room infromation
* Licence under GPL
***/

ob_start();
session_start();

include ('../config.php');

$table = <<<EOT
 (
    SELECT  nama.nm_bangsal, nama.kd_bangsal
    ,   (
            SELECT COUNT(*)
            FROM   kamar
            WHERE  kd_bangsal = nama.kd_bangsal
            AND statusdata='1'
        ) AS total
    ,   (
            SELECT COUNT(*)
            FROM   kamar
            WHERE  kd_bangsal = nama.kd_bangsal
            AND statusdata='1'
            AND status='ISI'
        ) AS isi
    ,   (
            SELECT COUNT(*)
            FROM   kamar
            WHERE  kd_bangsal = nama.kd_bangsal
            AND statusdata='1'
            AND status='KOSONG'
        ) AS kosong
    FROM    (
            SELECT DISTINCT nm_bangsal, kd_bangsal
            FROM   bangsal
            WHERE status='1'
            AND kd_bangsal IN(SELECT kd_bangsal FROM kamar)
        ) AS nama
 ) temp
EOT;

$primaryKey = 'nm_bangsal';

$columns = array(
    array( 'db' => 'nm_bangsal','dt' => 0 ),
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
