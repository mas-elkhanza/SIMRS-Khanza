<?php

include ('../config.php');

error_reporting(0);

	$idpoli = 'U0017';

    $dokter = query("select jadwal.kd_dokter, dokter.nm_dokter from jadwal,poliklinik,dokter where jadwal.kd_poli=poliklinik.kd_poli and
		   jadwal.kd_dokter=dokter.kd_dokter and jadwal.kd_poli='$idpoli' and jadwal.hari_kerja LIKE '%$namahari%'");

	echo'<option value="0">Pilih Kota</option>';
	while ($row = fetch_assoc($dokter)) {
	    
	    echo'<option value="'.$row['kd_dokter'].'">'.$row['nm_dokter'].'</option>';
	}

?>
