<?php

ob_start();
session_start();

include ('../config.php');

$table = <<<EOT
 (
    select * from utd_stok_darah WHERE status='ADA'
 ) temp
EOT;

$primaryKey = 'no_kantong';
$columns = array(
    array( 'db' => 'no_kantong','dt' => 0 ),
    array( 'db' => 'kode_komponen','dt' => 1 ),
    array( 'db' => 'golongan_darah','dt' => 2 ),
    array( 'db' => 'tanggal_kadaluarsa','dt' => 3 ),
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
