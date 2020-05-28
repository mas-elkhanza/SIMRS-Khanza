<?php

/***
* SIMRS Khanza Lite from version 0.1 Beta
* About : Porting of SIMRS Khanza by Windiarto a.k.a Mas Elkhanza as web and mobile app.
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
* Licence under GPL
***/

ob_start();
session_start();

include ('../config.php');

$table = <<<EOT
 (
    SELECT kd_penyakit, nm_penyakit FROM penyakit
 ) temp
EOT;

$primaryKey = 'kd_penyakit';
$columns = array(
    array( 'db' => 'kd_penyakit','dt' => 0 ),
    array( 'db' => 'nm_penyakit','dt' => 1 ),
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
