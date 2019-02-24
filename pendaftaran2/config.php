<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 06 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : config.php
* Description : Main config, function and helper
* Licence under GPL
***/

if (preg_match ('/config.php/', basename($_SERVER['PHP_SELF']))) die ('Unable to access this script directly from browser!');

define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', '');
define('DB_NAME', 'sik');
/*define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', '');
define('DB_NAME', 'sik');*/

$connection = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_NAME);

define('VERSION', '1.8 Beta');
define('URL', '');
define('DIR', '');
define('HARIDAFTAR', '08'); // Batasi hari pendaftaran 3 hari kedepan
define('LIMITJAM', '08:00:00'); // Batasi jam pendaftaran
define('SIGNUP', 'DISABLE'); // ENABLE atau DISABLE pendaftaran pasien baru
define('KODE_BERKAS', '002'); // Kode katergori berkas digital. Sesuaikan dengan kode yang ada di SIMRS.
define('UKURAN_BERKAS', '5000000'); // Ukuran berkas digital dalam byte
define('PESAN', 'ENABLE'); // ENABLE atau DISABLE fitur pengaduan pasien.
define('PENGADUAN', 'DISABLE'); // ENABLE atau DISABLE fitur pengaduan pasien.
define('PENDAFTARAN', 'DISABLE'); // ENABLE atau DISABLE fitur pengaduan pasien.
define('PRODUCTION', 'YES'); // YES to hide error page. NO to display error page.
define('URUTNOREG', 'DOKTER'); // DOKTER or POLI.
define('link_url', 'http://'.$_SERVER['HTTP_HOST']);
$limit      = array(10 => 10, 20 => 20, 50 => 50, 100 => 100, 150 => 150, 200 => 200, 500 => 500);

function escape($string) {
    global $connection;
    return mysqli_real_escape_string($connection, $string);
}

function query($sql) {
    global $connection;
    $query = mysqli_query($connection, $sql);
    confirm($query);
    return $query;
}

function confirm($query) {
    global $connection;
    if(!$query) {
        die("Query failed! " . mysqli_error($connection));
    }
}

function fetch_array($result) {
    return mysqli_fetch_array($result);
}

function fetch_assoc($result) {
    return mysqli_fetch_assoc($result);
}

function num_rows($result) {
    return mysqli_num_rows($result);
}

// Get date and time
date_default_timezone_set('Asia/Jakarta');
$month      = date('Y-m');
$date       = date('Y-m-d');
$time       = date('H:i:s');
$date_time  = date('Y-m-d H:i:s');

// Namahari
$hari = fetch_array(query("SELECT DAYNAME(current_date())"));

$namahari="";
if($hari[0]=="Sunday"){
    $namahari="AKHAD";
}else if($hari[0]=="Monday"){
    $namahari="SENIN";
}else if($hari[0]=="Tuesday"){
   	$namahari="SELASA";
}else if($hari[0]=="Wednesday"){
    $namahari="RABU";
}else if($hari[0]=="Thursday"){
    $namahari="KAMIS";
}else if($hari[0]=="Friday"){
    $namahari="JUMAT";
}else if($hari[0]=="Saturday"){
    $namahari="SABTU";
}

$day = date('D', strtotime($date));
$dayList = array(
	'Sun' => 'Minggu',
	'Mon' => 'Senin',
	'Tue' => 'Selasa',
	'Wed' => 'Rabu',
	'Thu' => 'Kamis',
	'Fri' => 'Jumat',
	'Sat' => 'Sabtu'
);
$bulan = date('m', strtotime($date));
$bulanList = array(
	'01' => 'Januari',
	'02' => 'Pebruari',
	'03' => 'Maret',
	'04' => 'April',
	'05' => 'Mei',
	'06' => 'Juni',
	'07' => 'Juli',
	'08' => 'Agustus',
	'09' => 'September',
	'10' => 'Oktober',
	'11' => 'November',
	'12' => 'Desember'
);

// Get settings
$getSettings = query("SELECT * FROM setting");
$dataSettings = fetch_assoc($getSettings);

// htmlentities remove #$%#$%@ values
function clean($string) {
    return htmlentities($string);
}

// redirect to another page
function redirect($location) {
    return header("Location: {$location}");
}

// add message to session
function set_message($message) {
    if(!empty($message)) {
        $_SESSION['message'] = $message;
    } else {
        $message = "";
    }
}

// display session message
function display_message() {
    if(isset($_SESSION['message'])) {
        echo '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'.$_SESSION['message'].'</div>';
        unset($_SESSION['message']);
    }
}

// show errors
function validation_errors($error) {
    $errors = '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'.$error.'</div>';
    return $errors;
}

// check if nik exits
function nik_exits($no_ktp) {
    $sql = "SELECT no_ktp FROM pasien WHERE no_ktp = '$no_ktp' ";
    $result = query($sql);
    // check if we found something
    if(num_rows($result) == 1) {
        return true;
    } else {
        return false;
    }
}

function htmlSelectFromArray($array, $attribute, $blank = true, $selected = '', $showKey = false, $arrayNonActive = '') {
    //$html  = "\r\n";
    $html = '<select ' . $attribute . '>';
    if (!empty($blank)) {
        if ($blank === true) {
            $html .= '<option value="">-- -- --</option>';
        } else {
            $html .= '<option value="">' . $blank . '</option>';
        }
    }
    if (is_array($array)) {
        if (count($array) > 0) {
            foreach ($array as $k => $v) {
                $html .= '<option value="' . $k . '"';
                if (is_array($selected)) {
                    if (in_array($k, $selected)) {
                        $html .= ' selected="selected"';
                    }
                } else {
                    if ((string) $k == $selected && strlen($k) == strlen($selected)) {
                        $html .= ' selected="selected"';
                    }
                }
                if (is_array($arrayNonActive)) {
                    if (in_array($k, $arrayNonActive)) {
                        $html .= ' style="background-color:#eeeeee;color:#ff0000;"';
                    }
                }
                $html .= '>';
                if ($showKey == true) {
                    $html .= $k . ' - ';
                }
                $html .= $v . '</option>';
            }
        } else {
            $html .= '<option value="">-- --</option>';
        }
    } else {
        $html .= '<option value="">-- --</option>';
    }
    $html .= '</select>';
    return $html;
}

function ConDataToArray($data='',$key1='',$key2='',$key3='',$key4='',$key5='')
{
    $ls = array();
    if($data)
    {
        foreach ($data as $key => $value) {
            
            $ls[$value[$key1]] = trim($value[$key2]);
        }
    }

    return $ls;
}

function hari_indo($tgl){
    $tentukan_hari=date('D',strtotime($tgl));
     $day = array(
        'Sun' => 'AKHAD',
        'Mon' => 'SENIN',
        'Tue' => 'SELASA',
        'Wed' => 'RABU',
        'Thu' => 'KAMIS',
        'Fri' => 'JUMAT',
        'Sat' => 'SABTU'
        );
    
    return $hari=$day[$tentukan_hari];
}

function select_jadwal_poli($hari='')
{
    if($hari)
    {
        $d = array();

        $q = query("
            SELECT
                jadwal.kd_poli,poliklinik.nm_poli,
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

        //$r = fetch_array($q);

        while($data = fetch_array($q))
        {
            $d[] = array('kd_poli' => $data['kd_poli'],
                        'nm_poli' => $data['nm_poli']. ' ['. $data['jam_mulai'] .' - '. $data['jam_selesai'] . ']'
                );
        }

        return $d;
    }
}

function select_cara_bayar($kd='',$kd_dokter='')
{
    $d = array();
    if($kd)
    {
        $q = query('
            SELECT
                png_jawab
            FROM
                penjab
            WHERE
              kd_pj =\''.$kd.'\'  
        ');
        $d = fetch_array($q);
        return $d;
    }
    else
    {
        $get_limit = fetch_assoc(query('SELECT * FROM limit_pasien_online WHERE kd_dokter=\''.$kd_dokter.'\''));

        if(!empty($get_limit['limit_reg']) && !empty($get_limit['umum']) && !empty($get_limit['bpjs']))
        {
            $wh = query('SELECT * FROM penjab WHERE png_jawab LIKE "%UMUM%" OR png_jawab LIKE "%BPJS%" AND kd_pj="BPJ" AND kd_pj!="A65"');
        }else if(!empty($get_limit['limit_reg']) && !empty($get_limit['umum']) && empty($get_limit['bpjs']))
        {
            $wh = query('SELECT * FROM penjab WHERE png_jawab LIKE "%UMUM%" AND kd_pj!="A65"');
        }
        else if(!empty($get_limit['limit_reg']) && empty($get_limit['umum']) && !empty($get_limit['bpjs']))
        {
             $wh = query('SELECT * FROM penjab WHERE png_jawab LIKE "%BPJS%" AND kd_pj="BPJ" AND kd_pj!="A65"');
        }else if(!empty($get_limit['limit_reg']) && empty($get_limit['umum']) && empty($get_limit['bpjs']))
        {
            $wh = query('SELECT * FROM penjab WHERE png_jawab LIKE "%UMUM%" OR png_jawab LIKE "%BPJS%" AND kd_pj="BPJ" AND kd_pj!="A65"');
        }
        else
        {
            $wh = query('SELECT * FROM penjab WHERE png_jawab LIKE "%UMUM%" OR png_jawab LIKE "%BPJS%" AND kd_pj="BPJ" AND kd_pj!="A65"');
        }

        if($wh)
        {
            while($data = fetch_array($wh))
            {
                $d[] = array('kd_pj' => $data['kd_pj'],
                            'png_jawab' => $data['png_jawab']
                    );
            }
            return $d;
        }
    }
    
}

function select_tb_limit_dokter($kd_dokter='')
{
    $q = query('SELECT limit_kedatangan, waktu_praktek FROM limit_pasien_online WHERE kd_dokter=\''.$kd_dokter.'\'');
    $r = fetch_array($q);
    return $r;
}

function select_jam_praktek($kd_dokter='',$hari='')
{
    $q = query('SELECT jam_mulai FROM jadwal WHERE kd_dokter=\''.$kd_dokter.'\' and hari_kerja=\''.$hari.'\'');
    $r = fetch_assoc($q);
    return $r['jam_mulai'];
}

function select_pasien($no_rm='')
{
    $get_pasien = fetch_array(query('SELECT nm_pasien FROM pasien WHERE no_rkm_medis =\''.$no_rm.'\''));
    return $get_pasien;
}

function select_nm_poli($id_poli='')
{
    $qnmp = query('SELECT nm_poli FROM poliklinik WHERE kd_poli=\''.$id_poli.'\'');
    $rnmp = fetch_assoc($qnmp);
    return $rnmp;
}

function select_nm_dokter($kd_dokter='')
{
    $qlmbo = query('SELECT nm_dokter FROM dokter WHERE kd_dokter=\''.$kd_dokter.'\'');
    return $rlmbo = fetch_assoc($qlmbo);
}

function count_percakapan($idpesan='')
{
    $qnmp = query('SELECT count(*) as total FROM pesan WHERE idpesan=\''.$idpesan.'\'');
    $rnmp = fetch_assoc($qnmp);
    return $rnmp['total'];
}

function tanggal_indo($tanggal, $cetak_hari = false)
{
    $hari = array ( 1 =>    'Senin',
                'Selasa',
                'Rabu',
                'Kamis',
                'Jumat',
                'Sabtu',
                'Minggu'
            );
            
    $bulan = array (1 =>   'Januari',
                'Februari',
                'Maret',
                'April',
                'Mei',
                'Juni',
                'Juli',
                'Agustus',
                'September',
                'Oktober',
                'November',
                'Desember'
            );
    $split    = explode('-', $tanggal);
    
    $tgl_indo = $split[2] . ' ' . $bulan[ (int)$split[1] ] . ' ' . $split[0];
    
    if ($cetak_hari) {
        $num = date('N', strtotime($tanggal));
        return $hari[$num] . ', ' . $tgl_indo;
    }
    return $tgl_indo;
}

function brodcast()
{
    $qcn = query('SELECT 
                    a.judul, 
                    a.keterangan,
                    a.masuk_tgl,
                    a.tgl_periksa,
                    a.status,
                    b.nm_poli,
                    c.nm_dokter,
                    g.tgl_awal,
                    g.tgl_akhir
                    FROM brodcast as a
                    LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
                    LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
                    LEFT JOIN booking_registrasi e ON a.kd_dokter = e.kd_dokter and a.kd_poli=e.kd_poli and a.tgl_periksa= e.tanggal_periksa
                    LEFT JOIN bc_info_cuti f ON a.kd_dokter=f.kd_dokter and a.kd_poli=f.kd_poli
                    LEFT JOIN  jadwal_libur g ON f.id=g.id_bc_info_cuti
                    WHERE e.no_rkm_medis=\''.$_SESSION['username'].'\' and a.status="Y" and e.status="Dokter Berhalangan"
            ');

    $r = fetch_assoc($qcn);
    
    $tgl_now = date('Y-m-d');
    $tgl_periksa = $r['tgl_periksa'];

    if($tgl_now > $tgl_periksa)
    {
        echo '';
    }
    else
    {
        return $r;
    }

}

function bc_dokter_datang()
{
    $tgl_now = date('Y-m-d');
    $qcn = query('SELECT 
                    a.judul, 
                    a.keterangan,
                    a.masuk_tgl,
                    a.tgl_periksa,
                    a.waktu_masuk,
                    b.nm_poli,
                    c.nm_dokter,
                    d.jam_mulai
                    FROM brodcast as a
                    LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
                    LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
                    LEFT JOIN  jadwal d ON a.kd_dokter = d.kd_dokter and a.kd_poli=d.kd_poli
                    WHERE a.tgl_periksa=\''.$tgl_now.'\' and a.status="Y" and a.kategori=1
            ');

    $r = fetch_assoc($qcn);
    
    
    $tgl_periksa = $r['tgl_periksa'];

    if($tgl_now > $tgl_periksa)
    {
        echo '';
    }
    else
    {
        return $r;
    }

}
