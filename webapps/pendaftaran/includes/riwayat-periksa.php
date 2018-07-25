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
      a.tgl_registrasi,
      a.no_rawat,
      a.no_reg,
      b.nm_poli,
      c.nm_dokter,
      a.status_lanjut,
      d.png_jawab
    FROM reg_periksa a
    LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
    LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
    LEFT JOIN penjab d ON a.kd_pj = d.kd_pj
    WHERE a.no_rkm_medis = '{$_SESSION['username']}'
 ) temp
EOT;

$primaryKey = 'no_rawat';
$columns = array(
    array( 'db' => 'tgl_registrasi','dt' => 0 ),
    array( 'db' => 'no_rawat','dt' => 1 ),
    array( 'db' => 'no_reg','dt' => 2 ),
    array( 'db' => 'nm_poli','dt' => 3 ),
    array( 'db' => 'nm_dokter','dt' => 4 ),
    array( 'db' => 'status_lanjut','dt' => 5 ),
    array( 'db' => 'png_jawab','dt' => 6 ),
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
