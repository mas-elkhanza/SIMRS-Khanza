<?php

require_once('config.php');

//$_REQUEST['action'] = "settings";
$action = trim(isset($_REQUEST['action'])?$_REQUEST['action']:null);

if($action == "signin") {
  $data = array();
  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $no_ktp = trim($_REQUEST['no_ktp']);
  $sql = "SELECT no_rkm_medis, no_ktp FROM pasien WHERE no_rkm_medis = '$no_rkm_medis'";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }

  if(num_rows($result) == 0) {
    $data['state'] = 'invalid';
    echo json_encode($data);
  } else {
    if($results[0]["no_ktp"] == $no_ktp) {
      $data['state'] = 'valid';
      $data['no_rkm_medis'] = $results[0]["no_rkm_medis"];
      echo json_encode($data);
    } else {
      $data['state'] = 'invalid';
      echo json_encode($data);
    }
  }
}

if($action == "antrian") {
  $results = array();
  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $sql = "SELECT a.tgl_registrasi, a.no_reg, a.stts, b.nm_poli, c.nm_dokter, d.png_jawab FROM reg_periksa a LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter LEFT JOIN penjab d ON a.kd_pj = d.kd_pj WHERE a.no_rkm_medis = '$no_rkm_medis' AND a.tgl_registrasi = '$date' ORDER BY a.tgl_registrasi ASC LIMIT 1";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "booking") {
  $results = array();
  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $sql = "SELECT a.tanggal_booking, a.tanggal_periksa, a.no_reg, a.status, b.nm_poli, c.nm_dokter, d.png_jawab FROM booking_registrasi a LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter LEFT JOIN penjab d ON a.kd_pj = d.kd_pj WHERE a.no_rkm_medis = '$no_rkm_medis' ORDER BY a.tanggal_periksa DESC";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "lastbooking") {
  $results = array();
  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $sql = "SELECT a.tanggal_booking, a.tanggal_periksa, a.no_reg, a.status, b.nm_poli, c.nm_dokter, d.png_jawab FROM booking_registrasi a LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter LEFT JOIN penjab d ON a.kd_pj = d.kd_pj WHERE a.no_rkm_medis = '$no_rkm_medis' ORDER BY a.tanggal_periksa DESC LIMIT 3";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "bookingdetail") {
  $results = array();
  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $tanggal_periksa = trim($_REQUEST['tanggal_periksa']);
  $no_reg = trim($_REQUEST['no_reg']);
  $sql = "SELECT a.tanggal_booking, a.tanggal_periksa, a.no_reg, a.status, b.nm_poli, c.nm_dokter, d.png_jawab FROM booking_registrasi a LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter LEFT JOIN penjab d ON a.kd_pj = d.kd_pj WHERE a.no_rkm_medis = '$no_rkm_medis' AND a.tanggal_periksa = '$tanggal_periksa' AND a.no_reg = '$no_reg'";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "kamar") {
  $results = array();
  $sql = "SELECT nama.kelas, (SELECT COUNT(*) FROM kamar WHERE kelas=nama.kelas AND statusdata='1') AS total, (SELECT COUNT(*) FROM kamar WHERE  kelas=nama.kelas AND statusdata='1' AND status='ISI') AS isi, (SELECT COUNT(*) FROM kamar WHERE  kelas=nama.kelas AND statusdata='1' AND status='KOSONG') AS kosong FROM (SELECT DISTINCT kelas FROM kamar WHERE statusdata='1') AS nama ORDER BY nama.kelas ASC";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "dokter") {
  $results = array();

  $hari = fetch_array(query("SELECT DAYNAME(current_date())"));
  $namahari = "";
  if($hari[0] == "Sunday"){
      $namahari = "AKHAD";
  }else if($hari[0] == "Monday"){
      $namahari = "SENIN";
  }else if($hari[0] == "Tuesday"){
     	$namahari = "SELASA";
  }else if($hari[0] == "Wednesday"){
      $namahari = "RABU";
  }else if($hari[0] == "Thursday"){
      $namahari = "KAMIS";
  }else if($hari[0] == "Friday"){
      $namahari = "JUMAT";
  }else if($hari[0] == "Saturday"){
      $namahari = "SABTU";
  }

  $sql = "SELECT dokter.nm_dokter, dokter.jk, poliklinik.nm_poli, DATE_FORMAT(jadwal.jam_mulai, '%H:%i') AS jam_mulai, DATE_FORMAT(jadwal.jam_selesai, '%H:%i') AS jam_selesai, dokter.kd_dokter FROM jadwal INNER JOIN dokter INNER JOIN poliklinik on dokter.kd_dokter=jadwal.kd_dokter AND jadwal.kd_poli=poliklinik.kd_poli WHERE jadwal.hari_kerja='$namahari'";
  $result = query($sql);
  if(num_rows($result) == 0){
    $send_data['state'] = 'notfound';
    echo json_encode($send_data);
  } else {
    while ($row = fetch_array($result)) {
      $results[] = $row;
    }
    echo json_encode($results);
  }

}

if($action == "riwayat") {
  $results = array();
  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $sql = "SELECT a.tgl_registrasi, a.no_rawat, a.no_reg, b.nm_poli, c.nm_dokter, d.png_jawab FROM reg_periksa a LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter LEFT JOIN penjab d ON a.kd_pj = d.kd_pj WHERE a.no_rkm_medis = '$no_rkm_medis' ORDER BY a.tgl_registrasi DESC";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "riwayatdetail") {
  $results = array();
  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $tgl_registrasi = trim($_REQUEST['tgl_registrasi']);
  $no_reg = trim($_REQUEST['no_reg']);
  $sql = "SELECT a.tgl_registrasi, a.no_rawat, a.no_reg, b.nm_poli, c.nm_dokter, d.png_jawab, e.keluhan, e.pemeriksaan, GROUP_CONCAT(DISTINCT g.nm_penyakit SEPARATOR '<br>') AS nm_penyakit, GROUP_CONCAT(DISTINCT i.nama_brng SEPARATOR '<br>') AS nama_brng FROM reg_periksa a LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter LEFT JOIN penjab d ON a.kd_pj = d.kd_pj LEFT JOIN pemeriksaan_ralan e ON a.no_rawat = e.no_rawat LEFT JOIN diagnosa_pasien f ON a.no_rawat = f.no_rawat LEFT JOIN penyakit g ON f.kd_penyakit = g.kd_penyakit LEFT JOIN detail_pemberian_obat h ON a.no_rawat = h.no_rawat LEFT JOIN databarang i ON h.kode_brng = i.kode_brng WHERE a.no_rkm_medis = '$no_rkm_medis' AND a.tgl_registrasi = '$tgl_registrasi' AND a.no_reg = '$no_reg' GROUP BY a.no_rawat";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "profil") {
  $results = array();
  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $sql = "SELECT * FROM pasien WHERE no_rkm_medis = '$no_rkm_medis'";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results[0]);
}

if($action == "jadwalklinik") {
  $results = array();
  $tanggal = trim($_REQUEST['tanggal']);

  $tentukan_hari=date('D',strtotime($tanggal));
  $day = array(
    'Sun' => 'AKHAD',
    'Mon' => 'SENIN',
    'Tue' => 'SELASA',
    'Wed' => 'RABU',
    'Thu' => 'KAMIS',
    'Fri' => 'JUMAT',
    'Sat' => 'SABTU'
  );
  $hari=$day[$tentukan_hari];

  $sql = "SELECT a.kd_poli, b.nm_poli, DATE_FORMAT(a.jam_mulai, '%H:%i') AS jam_mulai, DATE_FORMAT(a.jam_selesai, '%H:%i') AS jam_selesai FROM jadwal a, poliklinik b, dokter c WHERE a.kd_poli = b.kd_poli AND a.kd_dokter = c.kd_dokter AND a.hari_kerja LIKE '%$hari%' GROUP BY b.kd_poli";

  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "jadwaldokter") {
  $results = array();
  $tanggal = trim($_REQUEST['tanggal']);
  $kd_poli = trim($_REQUEST['kd_poli']);

  $tentukan_hari=date('D',strtotime($tanggal));
  $day = array(
    'Sun' => 'AKHAD',
    'Mon' => 'SENIN',
    'Tue' => 'SELASA',
    'Wed' => 'RABU',
    'Thu' => 'KAMIS',
    'Fri' => 'JUMAT',
    'Sat' => 'SABTU'
  );
  $hari=$day[$tentukan_hari];

  $sql = "SELECT a.kd_dokter, c.nm_dokter FROM jadwal a, poliklinik b, dokter c WHERE a.kd_poli = b.kd_poli AND a.kd_dokter = c.kd_dokter AND a.kd_poli = '$kd_poli' AND a.hari_kerja LIKE '%$hari%'";

  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "carabayar") {
  $results = array();
  $sql = "SELECT * FROM penjab WHERE png_jawab !='-'";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

if($action == "daftar") {
  $send_data = array();

  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $tanggal = trim($_REQUEST['tanggal']);
  $kd_poli = trim($_REQUEST['kd_poli']);
  $kd_dokter = trim($_REQUEST['kd_dokter']);
  $kd_pj = trim($_REQUEST['kd_pj']);

  $check = fetch_assoc(query("SELECT * FROM booking_registrasi WHERE no_rkm_medis = '$no_rkm_medis' AND tanggal_periksa = '$tanggal'"));
  if(count($check) == 0) {
      $mysql_date = date( 'Y-m-d' );
      $mysql_time = date( 'H:m:s' );
      $waktu_kunjungan = $tanggal . ' ' . $mysql_time;

      $no_reg_akhir = fetch_array(query("SELECT max(no_reg) FROM booking_registrasi WHERE kd_dokter='$kd_dokter' and tanggal_periksa='$tanggal'"));
      $no_urut_reg = substr($no_reg_akhir[0], 0, 3);
      $no_reg = sprintf('%03s', ($no_urut_reg + 1));

      $list['no_rkm_medis'] = escape($no_rkm_medis);
      $list['tanggal_periksa'] = escape($tanggal);
      $list['kd_poli'] = escape($kd_poli);
      $list['kd_dokter'] = escape($kd_dokter);
      $list['kd_pj'] = escape($kd_pj);
      $list['no_reg'] = escape($no_reg);
      $list['tanggal_booking'] = escape($mysql_date);
      $list['jam_booking'] = escape($mysql_time);
      $list['waktu_kunjungan'] = escape($waktu_kunjungan);
      $list['limit_reg'] = '1';
      $list['status'] = 'Belum';
      insertTable('booking_registrasi', $list);

      $send_data['state'] = 'success';
      $send_data['userid'] = mysqli_insert_id($connection);
      echo json_encode($send_data);
  }
  else{
      $send_data['state'] = 'duplication';
      echo json_encode($send_data);
  }
}

if($action == "sukses") {
  $results = array();
  $no_rkm_medis = trim($_REQUEST['no_rkm_medis']);
  $sql = "SELECT a.tanggal_booking, a.tanggal_periksa, a.no_reg, a.status, b.nm_poli, c.nm_dokter, d.png_jawab FROM booking_registrasi a LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter LEFT JOIN penjab d ON a.kd_pj = d.kd_pj WHERE a.no_rkm_medis = '$no_rkm_medis' AND a.tanggal_booking = '$date' AND a.jam_booking = (SELECT MAX(ax.jam_booking) FROM booking_registrasi ax WHERE ax.tanggal_booking = a.tanggal_booking) ORDER BY a.tanggal_booking ASC LIMIT 1";
  $result = query($sql);
  while ($row = fetch_array($result)) {
    $results[] = $row;
  }
  echo json_encode($results);
}

?>
