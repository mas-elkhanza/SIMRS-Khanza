<?php

require_once 'conf.php';
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Methods: POST, GET");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

$method = $_SERVER['REQUEST_METHOD'];
$action = isset($_GET["act"]) ? $_GET["act"] : null;

if ($method == 'POST') {
    switch ((isset($action) ? $action : "")) {

        case"token":
            $konten = trim(file_get_contents("php://input"));
            $decode = json_decode($konten, true);
            $response = array();
            if ($decode['username'] == $username && $decode['password'] == $password) {
                $response = array(
                    'response' => array(
                        'token' => getToken()
                    ),
                    'metadata' => array(
                        'message' => 'Ok',
                        'code' => 200
                    )
                );
            } else {
                $response = array(
                    'metadata' => array(
                        'message' => 'Access denied',
                        'code' => 401
                    )
                );
            }
            echo json_encode(array("response" => $response));

            break;

        case"no-antrian":
            $header = apache_request_headers();
            $konten = trim(file_get_contents("php://input"));
            $decode = json_decode($konten, true);
            $response = array();
            if ($header['x-token'] == getToken()) {
                $hari = hariindo($decode['tanggalperiksa']);
                echo  ""+hariindo($decode['tanggalperiksa']);
                $data = fetch_array(bukaquery("SELECT pasien.no_rkm_medis, pasien.no_ktp, pasien.no_peserta FROM pasien where pasien.no_ktp='$decode[nik]' and pasien.no_peserta='$decode[nomorkartu]'"));
                $cek_kouta = fetch_array(bukaquery("SELECT jadwal.kuota - (select COUNT(booking_registrasi.tanggal_periksa) FROM booking_registrasi 
                                WHERE booking_registrasi.tanggal_periksa='$decode[tanggalperiksa]' AND booking_registrasi.kd_dokter=jadwal.kd_dokter ) as sisa_kouta, jadwal.kd_dokter, jadwal.kd_poli, 
                                jadwal.jam_mulai + INTERVAL '10' MINUTE as jam_mulai, poliklinik.nm_poli,dokter.nm_dokter
                                FROM jadwal
                                INNER JOIN maping_poli_bpjs ON maping_poli_bpjs.kd_poli_rs=jadwal.kd_poli
                                INNER JOIN poliklinik ON poliklinik.kd_poli=jadwal.kd_poli
                                INNER JOIN dokter ON dokter.kd_dokter=jadwal.kd_dokter
                                WHERE jadwal.hari_kerja='$hari' AND  maping_poli_bpjs.kd_poli_bpjs='$decode[kodepoli]'
                                GROUP BY jadwal.kd_dokter
                                HAVING sisa_kouta > 0
                                ORDER BY sisa_kouta DESC LIMIT 1"));
                if ($cek_kouta['sisa_kouta'] > 0) {
                    if ($data['no_ktp'] != '') {
                        $noReg = noRegPoli($cek_kouta['kd_poli'], $decode['tanggalperiksa']);
                        $query = bukainput("insert into booking_registrasi set tanggal_booking=CURDATE(),jam_booking=CURTIME(), no_rkm_medis='$data[no_rkm_medis]',tanggal_periksa='$decode[tanggalperiksa]',"
                                . "kd_dokter='$cek_kouta[kd_dokter]',kd_poli='$cek_kouta[kd_poli]',no_reg='$noReg',kd_pj='BPJ',limit_reg='1',waktu_kunjungan='$cek_kouta[jam_mulai]',status='Belum'");
                        if ($query) {
                            $response = array(
                                'response' => array(
                                    'nomorantrean' => $noReg,
                                    'kodebooking' => $noReg,
                                    'jenisantrean' => 1,
                                    'estimasidilayani' => strtotime($cek_kouta['jam_mulai']) * 1000,
                                    'namapoli' => $cek_kouta['nm_poli'],
                                    'namadokter' => $cek_kouta['nm_dokter']
                                ),
                                'metadata' => array(
                                    'message' => 'Ok',
                                    'code' => 200
                                )
                            );
                        } else {
                            $response = array(
                                'metadata' => array(
                                    'message' => "Maaf Terjadi Kesalahan, Hubungi Admnistrator..",
                                    'code' => 401
                                )
                            );
                        }
                    } elseif ($data['no_ktp'] == '') {
                        $response = array(
                            'metadata' => array(
                                'message' => "Pasien tidak ditemukan/belum terdaftar di RSUD Kemayoran",
                                'code' => 401
                            )
                        );
                    }
                } else {
                    $response = array(
                        'metadata' => array(
                            'message' => "Jadwal tidak tersedia! Mohon pilih tanggal lain!",
                            'code' => 401
                        )
                    );
                }
            } else {
                $response = array(
                    'metadata' => array(
                        'message' => 'Access denied',
                        'code' => 401
                    )
                );
            }
            echo json_encode($response);
            break;

        case"rekap-antrian":
            $header = apache_request_headers();
            $konten = trim(file_get_contents("php://input"));
            $decode = json_decode($konten, true);
            $response = array();
            if ($header['x-token'] == getToken()) {
                $data = fetch_array(bukaquery("SELECT poliklinik.nm_poli, count(reg_periksa.kd_poli) as jumlah, 
                (select count(*) from reg_periksa WHERE reg_periksa.stts='Sudah' AND reg_periksa.kd_poli=poliklinik.kd_poli AND reg_periksa.tgl_registrasi='$decode[tanggalperiksa]') as terlayani
                FROM reg_periksa
                INNER JOIN maping_poli_bpjs ON maping_poli_bpjs.kd_poli_rs=reg_periksa.kd_poli
                INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli
                WHERE reg_periksa.tgl_registrasi='$decode[tanggalperiksa]' and maping_poli_bpjs.kd_poli_bpjs='$decode[kodepoli]'
                GROUP BY reg_periksa.kd_poli"));
                if ($data['nm_poli'] != '') {
                    $response = array(
                        'response' => array(
                            'namapoli' => $data['nm_poli'],
                            'totalantrean' => $data['jumlah'],
                            'jumlahterlayani' => $data['terlayani'],
                            'lastupdate' => strtotime(date('H:i:s')) * 1000
                        ),
                        'metadata' => array(
                            'message' => 'Ok',
                            'code' => 200
                        )
                    );
                } else {
                    $response = array(
                        'metadata' => array(
                            'message' => 'Maaf tidak Ada Jadwal ditanggal ' . $decode['tanggalperiksa'],
                            'code' => 401
                        )
                    );
                }
            } else {
                $response = array(
                    'metadata' => array(
                        'message' => 'Access denied',
                        'code' => 401
                    )
                );
            }
            echo json_encode($response);
            break;
    }
} else {
    $instansi=getOne("select nama_instansi from setting");
    echo "Selamat Datang di API ".$instansi." Antrean BPJS Mobile JKN..";
    echo "\n\n\n";
    echo "@".$instansi." - 2020";
}
?>
