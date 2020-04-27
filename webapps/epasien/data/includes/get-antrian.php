<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 06 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : includes/get-antrian.php
* Description : Count antrian pasien
* Licence under GPL
***/

ob_start();
session_start();
include_once('../config.php');
$result = fetch_array(query("SELECT no_reg, kd_poli, kd_dokter FROM reg_periksa WHERE no_rkm_medis = '" . $_SESSION['username'] . "' AND tgl_registrasi = '$date'"));
$sql = "SELECT no_rawat FROM reg_periksa WHERE stts = 'Belum' AND tgl_registrasi = '$date' AND no_reg < '" . $result['no_reg'] . "'";
if(URUTNOREG == 'DOKTER') {
  $sql .= " AND kd_dokter = '" . $result['kd_dokter'] . "'";
} else {
  $sql .= " AND kd_poli = '" . $result['kd_poli'] . "'";
}
echo num_rows(query($sql));
?>
