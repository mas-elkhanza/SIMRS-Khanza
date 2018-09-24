<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : includes/antrian-pasien.php
* Description : Datatable ajax file for antrian pasien
* Licence under GPL
***/

ob_start();
session_start();
include ('../config.php');
$get_id_poli = fetch_array(query("SELECT a.nm_poli, b.kd_poli FROM poliklinik a, reg_periksa b WHERE a.kd_poli = b.kd_poli AND b.no_rkm_medis = '$_SESSION[username]' AND b.tgl_registrasi='$date'"));
$sql = "SELECT
      a.no_reg AS Antrian,
      a.stts AS Status,
      f.nm_pasien AS Pasien,
      c.nm_dokter AS Dokter
    FROM reg_periksa a
    LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
    LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
    LEFT JOIN penjab d ON a.kd_pj = d.kd_pj
    LEFT JOIN pasien f ON a.no_rkm_medis = f.no_rkm_medis
    WHERE a.tgl_registrasi='$date' AND a.kd_poli = '$get_id_poli[1]'
    ORDER BY a.no_reg DESC
 ";
$resultset = query($sql);
$data = array();
while( $rows = fetch_assoc($resultset) ) {
	$data[] = $rows;
}

$results = array(
	"sEcho" => 1,
	"iTotalRecords" => count($data),
	"iTotalDisplayRecords" => count($data),
  	"aaData"=>$data);

echo json_encode($results);

?>
