<?php

ob_start();
session_start();

//include ('../config.php');
$con = @mysqli_connect("localhost","root","", "sik"); 

$tgl_reg = date('Y-m-d', strtotime($_POST['tgl']));
$kd_dok = ($_POST['kd_dok']) ? $_POST['kd_dok'] : '';
$kd_poli = ($_POST['kd_poli']) ? $_POST['kd_poli'] : '';
$tgl_sekarang = date('Y-m-d');

/*
| Fungsi : Cek ke database apakah dokter sedang cuti
| Nama Singakatan variable : $qjdc -> Query Jadwal Dokter Cuti
| Tanggal : 2018-09-07
*/
$qjdc 		= @mysqli_query($con,'SELECT * FROM jadwal_libur WHERE kd_dokter=\''.$kd_dok.'\'');
$rjdc 		= @mysqli_fetch_assoc($qjdc);

$tgl_awal 	= date('Y-m-d',strtotime($rjdc['tgl_awal']));
$tgl_akhir 	= date('Y-m-d',strtotime($rjdc['tgl_akhir']));

/* AKHIR FUNGSI CEK CUTI DOKTER  */
//print_r($tgl_awal);

/*
| Fungsi : Ambil Nama Poli, Untuk Di tampilkan pada notifikasi.
| Nama Singakatan variable : $qnp -> Query Nama Poli
| Tanggal : 2018-09-05
*/
$qnp = @mysqli_query($con,'SELECT nm_poli FROM poliklinik WHERE kd_poli=\''.$kd_poli.'\'');
$rqnp = @mysqli_fetch_assoc($qnp);

if($rqnp)
{
	foreach ($rqnp as $key => $value) {
		$np = $value;
	}
}
else
{
	$np = '';
}

/* AKHIR FUNGSI AMBIL NAMA POLI  */


/*
| Fungsi : Ambil limit pendaftaran online berdasarkan dokter.
| Nama Singakatan variable : $qld -> Query Limit Dokter
| Tanggal : 2018-09-06
*/
$qld = @mysqli_query($con,'SELECT * FROM limit_pasien_online WHERE kd_dokter=\''.$kd_dok.'\'');
$rld = @mysqli_fetch_assoc($qld);
$ld	= ($rld['limit_reg']) ? $rld['limit_reg'] : 10;

/* AKHIR FUNGSI AMBIL LIMIT REG  */
if($kd_poli)
{
	/*
	| Fungsi : Hitung jumlah pasien yang sudah terdaftar online.
	| Nama Singakatan variable : $qclr -> Query Count Limit Reg
	| Tanggal : 2018-09-05
	*/
	$qclr	= @mysqli_query($con,'SELECT count(limit_reg) as total FROM reg_periksa WHERE tgl_registrasi=\''.$tgl.'\' and kd_dokter=\''.$kd_dok.'\' and kd_poli=\''.$kd_poli.'\'');
	$rclr	= @mysqli_fetch_assoc($qclr);
	$clr	= $rclr['total'];

	print_r($_POST);
	/* AKHIR FUNGSI AMBIL LIMIT REG PERIKSA  */

	if($tgl_reg <= $tgl_sekarang)
	{
		$d = array(
			'status' => 'kurang'
		);
	}
	else if($tgl_reg >= $tgl_sekarang)
	{
		if($tgl_reg >= $tgl_awal && $tgl_reg <= $tgl_akhir)
		{
			$d = array(
	    		'status' => 'cuti',
	    		'nm_poli' => $np,
	    		'dokter' => $rld['nm_dokter']
    		);
		}
		else
		{
			if($clr >= $ld)
		    {
		    	$d = array(
		    		'status' => 'penuh',
		    		'nm_poli' => $np,
		    		'ke' => $clr,
		    		'l_reg' => $ld
		    	);
		    }
		    else
		    {
		    	$d = array(
		    		'status' => 'ok',
		    		'nm_poli' => $np,
		    		'ke' => $clr,
		    		'l_reg' => $ld
		    	);
		    }
		}
	}
	
}
else
{
	echo '';
}

echo json_encode($d);
@mysqli_close($con);

?>