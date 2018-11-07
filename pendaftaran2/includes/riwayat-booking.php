<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : includes/riwayat-periksa.php
* Description : Datatable ajax file for riwayat periksa
* Licence under GPL
***/

ob_start();
session_start();

include ('../config.php');

$table = <<<EOT
 (
    SELECT
      a.tanggal_booking,
      a.jam_booking,
      a.no_rkm_medis,
      a.tanggal_periksa,
      a.no_reg,
      a.waktu_kunjungan,
      b.nm_poli,
      c.nm_dokter,
      d.png_jawab,
      a.status
    FROM booking_registrasi a
    LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
    LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
    LEFT JOIN penjab d ON a.kd_pj = d.kd_pj
    WHERE a.no_rkm_medis='{$_SESSION['username']}'
 ) temp
EOT;

$primaryKey = 'tanggal_periksa';
$columns = array(
    array( 'db' => 'no_reg','dt' => 0 ),
    array( 'db' => 'waktu_kunjungan','dt' => 1 ),
    array( 'db' => 'no_rkm_medis','dt' => 2 ),
    array( 'db' => 'nm_poli','dt' => 3 ),
    array( 'db' => 'nm_dokter','dt' => 4 ),
    array( 'db' => 'tanggal_periksa','dt' => 5 ),
    array( 'db' => 'png_jawab','dt' => 6 ),
    array( 'db' => 'tanggal_booking','dt' => 7 ),
    array( 'db' => 'jam_booking','dt' => 8 ),
    array( 'db' => 'status','dt' => 9 ),
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
