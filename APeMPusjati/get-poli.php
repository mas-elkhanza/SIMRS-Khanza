<?php
include ('config.php');
if(!empty($_POST['tgl_registrasi'])){
    $data = array();

    $cek = fetch_array(query("SELECT no_rawat FROM reg_periksa WHERE no_rkm_medis='$_POST[no_rkm_medis]' AND tgl_registrasi='$_POST[tgl_registrasi]'"));

    if($cek > "1") {

      $data['status'] = 'exist';
      $data['result'] = '';
      echo json_encode($data);

    } else {

      $tanggal = $_POST['tgl_registrasi'];
      $tentukan_hari = date('D',strtotime($tanggal));
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

      //get data from the database
      $query = $db->query("
        SELECT
          jadwal.kd_poli AS kd_poli,
          poliklinik.nm_poli AS nm_poli,
          DATE_FORMAT(jadwal.jam_mulai, '%H:%i') AS jam_mulai,
          DATE_FORMAT(jadwal.jam_selesai, '%H:%i') AS jam_selesai
        FROM
          jadwal,
          poliklinik,
          dokter
        WHERE
          jadwal.kd_poli = poliklinik.kd_poli
        AND
          jadwal.kd_dokter = dokter.kd_dokter
        AND
          hari_kerja LIKE '%$hari%'
        GROUP BY
          poliklinik.kd_poli
      ");

      if($query->num_rows > 0){
          while ($userData = $query->fetch_assoc()) {
            $data['status'] = 'ok';
            $data['result'][] = $userData;
          }
      }else{
          $data['status'] = 'err';
          $data['result'] = '';
      }

      echo json_encode($data);

    }

}
?>
