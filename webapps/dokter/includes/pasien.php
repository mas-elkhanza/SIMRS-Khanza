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
//include ('../init.php');

$table = <<<EOT
 (
    SELECT a.no_rkm_medis, SUBSTR(a.nm_pasien,1,20) AS nm_pasien, a.no_ktp, a.jk, a.tmp_lahir, a.tgl_lahir, a.alamat, a.no_tlp, a.pekerjaan, a.stts_nikah, a.tgl_daftar, a.umur FROM pasien AS a 
 ) temp
EOT;

$primaryKey = 'no_rkm_medis';
$columns = array(
    //array( 'db' => 'no_rkm_medis','dt' => 0, 
    //    'formatter' => function( $d ) { 
    //        if($_SESSION['username'] == '198502262010012005')  {  
    //            $q_ = $q;
    //            $_q = fetch_assoc(query("SELECT status FROM pasien_rm_status WHERE no_rkm_medis = '{$q_}'"));
    //            if($_q['status'] == '1') {
    //                $status = '<i class="material-icons">check</i>'; 
    //            } else if($_q['status'] == '0') {
    //                $status = '<i class="material-icons">close</i>'; 
    //            } else {
    //                $status = '<i class="material-icons">refresh</i>'; 
    //            }
    //            //return '' . $d . '<br><a href="edit.php?id=' . $d . '">Edit</a> | <a href="delete.php?id=' . $d . '">Delete</a>';
    //            return '<a href="edit.php?id=' . $d . '">' . $status . '</a> ' . $d . '' . $q_ . '';
    //        } else { 
    //            return $d;
    //        }
    //    }
    //),
    array( 'db' => 'no_rkm_medis','dt' => 0 ),
    array( 'db' => 'nm_pasien','dt' => 1 ),
    array( 'db' => 'no_ktp','dt' => 2 ),
    array( 'db' => 'jk','dt' => 3 ),
    array( 'db' => 'tmp_lahir','dt' => 4 ),
    array( 'db' => 'tgl_lahir','dt' => 5 ),
    array( 'db' => 'alamat','dt' => 6 ),
    array( 'db' => 'no_tlp','dt' => 7 ),
    array( 'db' => 'pekerjaan','dt' => 8 ),
    array( 'db' => 'stts_nikah','dt' => 9 ),
    array( 'db' => 'tgl_daftar','dt' => 10 ),
    array( 'db' => 'umur','dt' => 11 ),
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