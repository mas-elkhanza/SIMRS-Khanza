<?php

ob_start();
session_start();

include ('../config.php');

$get_id_poli = fetch_array(query("SELECT a.nm_poli, b.kd_poli FROM poliklinik a, reg_periksa b WHERE a.kd_poli = b.kd_poli AND b.no_rkm_medis = '$_SESSION[username]' AND b.tgl_registrasi='$date'"));

$table = <<<EOT
 (
    SELECT 
      f.nm_pasien,   
      c.nm_dokter,   
      a.no_reg,   
      a.stts  
    FROM reg_periksa a
    LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli 
    LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter 
    LEFT JOIN penjab d ON a.kd_pj = d.kd_pj 
    LEFT JOIN pasien f ON a.no_rkm_medis = f.no_rkm_medis 
    WHERE a.tgl_registrasi='$date' AND a.kd_poli = '$get_id_poli[1]' 
 ) temp
EOT;

$primaryKey = 'no_reg';
$columns = array(
    array( 'db' => 'nm_pasien','dt' => 1 ),
    array( 'db' => 'nm_dokter','dt' => 2 ),
    array( 'db' => 'no_reg','dt' => 3 ),
    array( 'db' => 'stts','dt' => 4 ),
);
 
$sql_details = array(
    'user' => DB_USER,
    'pass' => DB_PASS,
    'db'   => DB_NAME,
    'host' => DB_HOST
);
require('ssp.class.php');
$result=SSP::simple( $_GET, $sql_details, $table, $primaryKey, $columns );
$start=$_REQUEST['start']+1;
foreach($result['data'] as &$res){
    $res[0]=(string)$start;
    $start++;
}
echo json_encode($result);
?>
