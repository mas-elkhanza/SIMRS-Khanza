<?php  
/*
| Waktu Pembuatan : 2018-09-06
|
*/

// Koneksi Database
$con = @mysqli_connect("localhost","root","", "sik"); 

// Array Hari
$day = array(
	'Sun' => 'AKHAD',
	'Mon' => 'SENIN',
	'Tue' => 'SELASA',
	'Wed' => 'RABU',
	'Thu' => 'KAMIS',
	'Fri' => 'JUMAT',
	'Sat' => 'SABTU'
);

// Tanggal
$kd_poli 	= $_POST['kd_polis'];
$kd_dokter	= $_POST['kd_dokters'];
$tgl_registrasi = $_POST['tgl_registrasis'];
$no_rawat = $_POST['no_rawats'];

$con_tgl = date('D',strtotime($tgl_registrasi));
$hari = $day[$con_tgl];

// Ambil waktu pengurangan jam prakter dari tabel limit_pasien_online
$qlmtp = mysqli_query($con, 'SELECT limit_kedatangan, waktu_praktek FROM limit_pasien_online WHERE kd_dokter=\''.$kd_dokter.'\'');
$rlmtp = mysqli_fetch_assoc($qlmtp);
//print_r($rlmtp);
//$jm_praktek = 30;

// Ambil jam praktek dokter
$qjm = mysqli_query($con, 'SELECT jam_mulai FROM jadwal WHERE kd_dokter=\''.$kd_dokter.'\' and hari_kerja=\''.$hari.'\'');
$rjm = mysqli_fetch_assoc($qjm);
$jmp = date('H:i:s',strtotime($rjm['jam_mulai']));

// Ambil jumlah data seluruh pasien pada kd poli, kd dokter dan tanggal registrasi
$qpo = mysqli_query($con,'SELECT no_reg FROM reg_periksa WHERE kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$kd_poli.'\' and tgl_registrasi=\''.$tgl_registrasi.'\'');
$rpo = mysqli_fetch_assoc($qpo);
$no_urut_reg = ($rpo['no_reg']) ? $rpo['no_reg'] : 30;


// Buat Fungsi penghitungan waktu kunjungan pasien berdasarkan jumlah pasien dan waktu prakter dokter di kurang 30 menit

$wk_kun = $rlmtp['limit_kedatangan'] * $no_urut_reg;
$wkt_regist = date('Y-m-d H:i:s', strtotime($tgl_registrasi . $jmp));

// Pengurangan 30 menit sebelum praktek
$dates = date_create($wkt_regist);
date_add($dates, date_interval_create_from_date_string('-'. $rlmtp['waktu_praktek'] .' minutes'));
$waktu_praktek =  date_format($dates, 'Y-m-d H:i:s');

// Penambahan 5 menit setiap pasien
$dates2 = date_create($waktu_praktek);
date_add($dates2, date_interval_create_from_date_string('+'. $wk_kun . ' minutes'));
$waktu_datang = date_format($dates2, 'Y-m-d H:i:s');

$wd = date('Y-m-d H:i:s',strtotime($waktu_datang));

//echo $waktu_praktek . '<br>';
//echo $wk_kun . '<br>';
//echo $waktu_datang;
$sql = mysqli_query($con ,'UPDATE reg_periksa SET waktu_kunjungan=\''.$waktu_datang.'\' WHERE no_rawat=\''.$no_rawat.'\' and kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$kd_poli.'\'');

if($sql)
{
	echo $waktu_datang;
}
else
{
	echo 'gagal';
}
@mysqli_close($con);

?>