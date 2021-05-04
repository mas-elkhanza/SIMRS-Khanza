<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

//ini_set('display_errors', 0);
//error_reporting(E_ERROR | E_WARNING | E_PARSE);

require_once('config.php');

echo $kd_pj;
$method = $_SERVER['REQUEST_METHOD'];
$action = isset($_GET["act"]) ? $_GET["act"] : null;

$bpj = fetch_assoc(query("SELECT AES_DECRYPT(usere,'nur') as username, AES_DECRYPT(passworde,'windi') as password FROM password_asuransi WHERE kd_pj = '$kd_pj'"));

$header = json_encode(['typ' => 'JWT', 'alg' => 'HS256']);
$payload = json_encode(['username' => $bpj['username'], 'password' => $bpj['password'], 'date' => strtotime(date('Y-m-d')) * 1000]);
$base64UrlHeader = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($header));
$base64UrlPayload = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($payload));
$signature = hash_hmac('sha256', $base64UrlHeader . "." . $base64UrlPayload, 'abC123!', true);
$base64UrlSignature = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($signature));
$toket = $base64UrlHeader . "." . $base64UrlPayload . "." . $base64UrlSignature;

if ($method == 'POST') {
    switch ((isset($action) ? $action : "")) {
        case"token":
            $konten = trim(file_get_contents("php://input"));
            $decode = json_decode($konten, true);
            $response = array();
            if ($decode['username'] == $bpj['username'] && $decode['password'] == $bpj['password']) {
                $response = array(
                    'response' => array(
                        'token' => $toket
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
            echo json_encode($response);

            break;

        case"antrian":
            $header = apache_request_headers();
            $konten = trim(file_get_contents("php://input"));
            $decode = json_decode($konten, true);
            $response = array();
            if ($header['x-token'] == $toket) {
                $tanggal=$decode['tanggalperiksa'];
                $tentukan_hari=date('D',strtotime($tanggal));
                $day = array('Sun' => 'AKHAD', 'Mon' => 'SENIN', 'Tue' => 'SELASA', 'Wed' => 'RABU', 'Thu' => 'KAMIS', 'Fri' => 'JUMAT', 'Sat' => 'SABTU');
                $hari=$day[$tentukan_hari];

                // Cek Rujukan
                $cek_rujukan = query("SELECT tglrujukan FROM bridging_sep WHERE no_rujukan = '$decode[nomorreferensi]' GROUP BY tglrujukan");
                $tglrujukan = fetch_assoc($cek_rujukan);

                $h1 = strtotime('+1 days' , strtotime($date)) ;
                $h1 = date('Y-m-d', $h1);
                $_h1 = date('d-m-Y', strtotime($h1));
                if(num_rows($cek_rujukan) > 0) {
                  $h7 = strtotime('+82 days', strtotime($tglrujukan['tglrujukan']));
                } else {
                  $h7 = strtotime('+7 days', strtotime($date)) ;
                }
                $h7 = date('Y-m-d', $h7);
                $_h7 = date('d-m-Y', strtotime($h7));

                $data_pasien = query("SELECT no_ktp, no_peserta, no_rkm_medis FROM pasien where no_peserta='$decode[nomorkartu]'");
                $data = fetch_array($data_pasien);
                $poli = query("SELECT kd_poli_bpjs, kd_poli_rs FROM maping_poli_bpjs WHERE kd_poli_bpjs='$decode[kodepoli]'");
                $cek_kouta = fetch_array(query("SELECT jadwal.kuota - (SELECT COUNT(booking_registrasi.tanggal_periksa) FROM booking_registrasi WHERE booking_registrasi.tanggal_periksa='$decode[tanggalperiksa]' AND booking_registrasi.kd_dokter=jadwal.kd_dokter) as sisa_kouta, jadwal.kd_dokter, jadwal.kd_poli, jadwal.jam_mulai as jam_mulai, poliklinik.nm_poli, dokter.nm_dokter FROM jadwal INNER JOIN maping_poli_bpjs ON maping_poli_bpjs.kd_poli_rs=jadwal.kd_poli INNER JOIN poliklinik ON poliklinik.kd_poli=jadwal.kd_poli INNER JOIN dokter ON dokter.kd_dokter=jadwal.kd_dokter WHERE jadwal.hari_kerja='$hari' AND maping_poli_bpjs.kd_poli_bpjs='$decode[kodepoli]' GROUP BY jadwal.kd_dokter HAVING sisa_kouta > 0 ORDER BY sisa_kouta DESC LIMIT 1"));
                $cek_referensi = query("SELECT * FROM antrian_referensi WHERE nomor_referensi = '$decode[nomorreferensi]'");
                $antrian_referensi = fetch_array($cek_referensi);

                if(num_rows($cek_referensi) > 0) {
        	         $errors[] = 'Anda sudah terdaftar dalam antrian menggunakan nomor rujukan yang sama.';
                }
                if(empty($decode['nomorkartu'])) {
                   $errors[] = 'Nomor kartu tidak boleh kosong';
                }
                if (!empty($decode['nomorkartu']) && mb_strlen($decode['nomorkartu'], 'UTF-8') < 13){
        	         $errors[] = 'Nomor kartu harus 13 digit';
                }
                if (!empty($decode['nomorkartu']) && !ctype_digit($decode['nomorkartu']) ){
                   $errors[] = 'Nomor kartu harus mengandung angka saja!!';
                }
                if(empty($decode['nik'])) {
                   $errors[] = 'Nomor kartu tidak boleh kosong';
                }
                if(!empty($decode['nik']) && mb_strlen($decode['nik'], 'UTF-8') < 16){
        	         $errors[] = 'Nomor KTP harus 16 digiti atau format tidak sesuai';
                }
                if (!empty($decode['nik']) && !ctype_digit($decode['nik']) ){
                   $errors[] = 'Nomor kartu harus mengandung angka saja!!';
                }
                if(empty($decode['tanggalperiksa'])) {
                   $errors[] = 'Anda belum memilih tanggal periksa';
                }
                if (!empty($decode['tanggalperiksa']) && !preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggalperiksa'])) {
                   $errors[] = 'Format tanggal periksa tidak sesuai';
                }
                if(!empty($decode['tanggalperiksa']) && $decode['tanggalperiksa'] < $h1 || $decode['tanggalperiksa'] > $h7) {
                   $errors[] = 'Tanggal periksa bisa dilakukan tanggal '.$_h1.' hingga tanggal '.$_h7;
                }
                if(!empty($decode['tanggalperiksa']) && $decode['tanggalperiksa'] == $antrian_referensi['tanggal_periksa']) {
                   $errors[] = 'Anda sudah terdaftar dalam antrian ditanggal '.$decode['tanggalperiksa'];
                }
                if(empty($decode['kodepoli'])) {
                   $errors[] = 'Kode poli tidak boleh kosong';
                }
                if(!empty($decode['kodepoli']) && num_rows($poli) == 0) {
                   $errors[] = 'Kode poli tidak ditemukan';
                }
                if(empty($decode['nomorreferensi'])) {
                   $errors[] = 'Nomor rujukan kosong atau tidak ditemukan';
                }
                if(empty($decode['nomorreferensi'])) {
                   $errors[] = 'Nomor rujukan kosong atau tidak ditemukan';
                }
                if(empty($decode['jenisreferensi'])) {
                   $errors[] = 'Jenis referensi tidak boleh kosong';
                }
                if(!empty($decode['jenisreferensi']) && $decode['jenisreferensi'] < 1 || $decode['jenisreferensi'] > 2) {
                   $errors[] = 'Jenis referensi tidak ditemukan';
                }
                if(empty($decode['jenisrequest'])) {
                   $errors[] = 'Jenis request tidak boleh kosong';
                }
                if(!empty($decode['jenisrequest']) && $decode['jenisrequest'] < 1 || $decode['jenisrequest'] > 2) {
                   $errors[] = 'Jenis request tidak ditemukan';
                }
                if($decode['polieksekutif'] >= 1) {
                   $errors[] = 'Maaf tidak ada jadwal Poli Eksekutif ditanggal ' . $decode['tanggalperiksa'];
                }
                if(!empty($errors)) {
          	        foreach($errors as $error) {
                        $response = array(
                            'metadata' => array(
                                'message' => validation_errors($error),
                                'code' => 401
                            )
                        );
          	        }
                } else {
                    if ($cek_kouta['sisa_kouta'] > 0) {
                        if(num_rows($data_pasien) == 0){
                            // Get antrian loket
                            $no_reg_akhir = fetch_array(query("SELECT max(noantrian) FROM antrian_loket WHERE postdate='$decode[tanggalperiksa]'"));
                            $no_urut_reg = substr($no_reg_akhir['0'], 0, 3);
                            $no_reg = sprintf('%03s', ($no_urut_reg + 1));
                            $jenisantrean = 1;
                            $minutes = $no_urut_reg * 10;
                            $cek_kouta['jam_mulai'] = date('H:i:s',strtotime('+'.$minutes.' minutes',strtotime($cek_kouta['jam_mulai'])));
                            $query = query("INSERT INTO antrian_loket(kd, noantrian, postdate) VALUES (NULL, '$no_reg', '$decode[tanggalperiksa]')");
                            
                        } else {
                          // Get antrian poli
                          $poli = fetch_array($poli);
                          $no_reg_akhir = fetch_array(query("SELECT max(no_reg) FROM booking_registrasi WHERE kd_poli='$poli[kd_poli_rs]' and tanggal_periksa='$decode[tanggalperiksa]'"));
                          $no_urut_reg = substr($no_reg_akhir['0'], 0, 3);
                          $no_reg = sprintf('%03s', ($no_urut_reg + 1));
                          $jenisantrean = 2;
                          $minutes = $no_urut_reg * 10;
                          $cek_kouta['jam_mulai'] = date('H:i:s',strtotime('+'.$minutes.' minutes',strtotime($cek_kouta['jam_mulai'])));
                          $query = query("insert into booking_registrasi set tanggal_booking=CURDATE(), jam_booking=CURTIME(), no_rkm_medis='$data[no_rkm_medis]', tanggal_periksa='$decode[tanggalperiksa]', kd_dokter='$cek_kouta[kd_dokter]', kd_poli='$cek_kouta[kd_poli]', no_reg='$no_reg', kd_pj='$kd_pj', limit_reg='1', waktu_kunjungan='$decode[tanggalperiksa] $cek_kouta[jam_mulai]',status='Belum'");
                        }
                        if ($query) {
                            $response = array(
                                'response' => array(
                                    'nomorantrean' => $no_reg,
                                    'kodebooking' => $no_reg,
                                    'jenisantrean' => $jenisantrean,
                                    'estimasidilayani' => strtotime($decode['tanggalperiksa'].' '.$cek_kouta['jam_mulai']) * 1000,
                                    'namapoli' => $cek_kouta['nm_poli'],
                                    'namadokter' => $cek_kouta['nm_dokter']
                                ),
                                'metadata' => array(
                                    'message' => 'Ok',
                                    'code' => 200
                                )
                            );
                            if(!empty($decode['nomorreferensi'])) {
                              query("INSERT INTO antrian_referensi(tanggal_periksa, nomor_referensi) VALUES ('$decode[tanggalperiksa]','$decode[nomorreferensi]')");
                            }
                            // debug only
                            //query("DELETE FROM booking_registrasi WHERE no_rkm_medis = '$data[no_rkm_medis]'");
                        } else {
                            $response = array(
                                'metadata' => array(
                                    'message' => "Maaf Terjadi Kesalahan, Hubungi layanan pelanggang Rumah Sakit..",
                                    'code' => 401
                                )
                            );
                        }
                    } else {
                        $response = array(
                            'metadata' => array(
                                'message' => "Jadwal tidak tersedia atau kuota penuh! Silahkan pilih tanggal lain!",
                                'code' => 401
                            )
                        );
                    }
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

        case"rekap_antrian":
            $header = apache_request_headers();
            $konten = trim(file_get_contents("php://input"));
            $decode = json_decode($konten, true);
            $response = array();
            if ($header['x-token'] == $toket) {
                $poli = query("SELECT kd_poli_bpjs FROM maping_poli_bpjs WHERE kd_poli_bpjs='$decode[kodepoli]'");
                $data = fetch_array(query("SELECT poliklinik.nm_poli, count(reg_periksa.kd_poli) as jumlah,
                (select count(*) from reg_periksa WHERE reg_periksa.stts='Sudah' AND reg_periksa.kd_poli=poliklinik.kd_poli AND reg_periksa.tgl_registrasi='$decode[tanggalperiksa]') as terlayani
                FROM reg_periksa
                INNER JOIN maping_poli_bpjs ON maping_poli_bpjs.kd_poli_rs=reg_periksa.kd_poli
                INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli
                WHERE reg_periksa.tgl_registrasi='$decode[tanggalperiksa]' and maping_poli_bpjs.kd_poli_bpjs='$decode[kodepoli]'
                GROUP BY reg_periksa.kd_poli"));

                if (!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggalperiksa'])) {
                   $errors[] = 'Format tanggal periksa tidak sesuai';
                }
                if(num_rows($poli) == 0) {
                   $errors[] = 'Kode poli tidak ditemukan';
                }
                if($decode['polieksekutif'] >= 1) {
                   $errors[] = 'Maaf tidak ada jadwal Poli Eksekutif ditanggal ' . $decode['tanggalperiksa'];
                }

                if(!empty($errors)) {
          	        foreach($errors as $error) {
                        $response = array(
                            'metadata' => array(
                                'message' => validation_errors($error),
                                'code' => 401
                            )
                        );
          	        }
                } else {
                    if ($data['nm_poli'] != '') {
                        $response = array(
                            'response' => array(
                                'namapoli' => $data['nm_poli'],
                                'totalantrean' => $data['jumlah'],
                                'jumlahterlayani' => $data['terlayani'],
                                'lastupdate' => strtotime(date('Y-m-d H:i:s')) * 1000
                            ),
                            'metadata' => array(
                                'message' => 'Ok',
                                'code' => 200
                            )
                        );
                    } else {
                        $response = array(
                            'metadata' => array(
                                'message' => 'Maaf tidak ada jadwal ditanggal ' . $decode['tanggalperiksa'],
                                'code' => 401
                            )
                        );
                    }
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

        case"operasi":
            $header = apache_request_headers();
            $konten = trim(file_get_contents("php://input"));
            $decode = json_decode($konten, true);
            $response = array();
            if ($header['x-token'] == $toket) {
                $data = array();
                $cek_nopeserta = query("SELECT no_peserta FROM pasien WHERE no_peserta = '$decode[nopeserta]'");
                $sql = "SELECT booking_operasi.no_rawat AS kodebooking, booking_operasi.tanggal AS tanggaloperasi, paket_operasi.nm_perawatan AS jenistindakan, maping_poli_bpjs.kd_poli_bpjs AS kodepoli, poliklinik.nm_poli AS namapoli, booking_operasi.status AS terlaksana FROM pasien, booking_operasi, paket_operasi, reg_periksa, jadwal, poliklinik, maping_poli_bpjs WHERE booking_operasi.no_rawat = reg_periksa.no_rawat AND pasien.no_rkm_medis = reg_periksa.no_rkm_medis AND booking_operasi.kode_paket = paket_operasi.kode_paket AND booking_operasi.kd_dokter = jadwal.kd_dokter AND jadwal.kd_poli = poliklinik.kd_poli AND jadwal.kd_poli=maping_poli_bpjs.kd_poli_rs AND pasien.no_peserta = '$decode[nopeserta]'  GROUP BY booking_operasi.no_rawat ";
                $result = query($sql);
                if(num_rows($cek_nopeserta) == 0) {
                   $errors[] = 'Nomor peserta tidak ditemukan';
                }
                if(!empty($errors)) {
                    foreach($errors as $error) {
                        $response = array(
                            'metadata' => array(
                                'message' => validation_errors($error),
                                'code' => 401
                            )
                        );
                    }
                } else {
                    if ($decode['nopeserta'] != '') {
                        while ($data = fetch_array($result)) {
                            if($data['terlaksana'] == 'Menunggu') {
                              $data['terlaksana'] = '0';
                            } else {
                              $data['terlaksana'] = '1';
                            }
                            $data_array[] = array(
                                    'kodebooking' => $data['kodebooking'],
                                    'tanggaloperasi' => $data['tanggaloperasi'],
                                    'jenistindakan' => $data['jenistindakan'],
                                    'kodepoli' => $data['kodepoli'],
                                    'namapoli' => $data['namapoli'],
                                    'terlaksana' => $data['terlaksana']
                            );
                        }
                        $response = array(
                            'response' => array(
                                'list' => (
                                    $data_array
                                )
                            ),
                            'metadata' => array(
                                'message' => 'Ok',
                                'code' => 200
                            )
                        );
                    } else {
                        $response = array(
                            'metadata' => array(
                                'message' => 'Maaf tidak ada daftar booking operasi.',
                                'code' => 401
                            )
                        );
                    }
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

        case"jadwal_operasi":
            $header = apache_request_headers();
            $konten = trim(file_get_contents("php://input"));
            $decode = json_decode($konten, true);
            $response = array();
            if ($header['x-token'] == $toket) {
                $data = array();
                $sql = "SELECT booking_operasi.no_rawat AS kodebooking, booking_operasi.tanggal AS tanggaloperasi, paket_operasi.nm_perawatan AS jenistindakan,  maping_poli_bpjs.kd_poli_bpjs AS kodepoli, poliklinik.nm_poli AS namapoli, booking_operasi.status AS terlaksana, pasien.no_peserta AS nopeserta FROM pasien, booking_operasi, paket_operasi, reg_periksa, jadwal, poliklinik, maping_poli_bpjs WHERE booking_operasi.no_rawat = reg_periksa.no_rawat AND pasien.no_rkm_medis = reg_periksa.no_rkm_medis AND booking_operasi.kode_paket = paket_operasi.kode_paket AND booking_operasi.kd_dokter = jadwal.kd_dokter AND jadwal.kd_poli = poliklinik.kd_poli AND jadwal.kd_poli=maping_poli_bpjs.kd_poli_rs AND booking_operasi.tanggal BETWEEN '$decode[tanggalawal]' AND '$decode[tanggalakhir]' GROUP BY booking_operasi.no_rawat";
                $result = query($sql);
                if (!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggalawal'])) {
                   $errors[] = 'Format tanggal awal jadwal operasi tidak sesuai';
                }
                if (!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggalakhir'])) {
                   $errors[] = 'Format tanggal akhir jadwal operasi tidak sesuai';
                }
                if ($decode['tanggalawal'] > $decode['tanggalakhir']) {
                  $errors[] = 'Format tanggal awal harus lebih kecil dari tanggal akhir';
                }
                if(!empty($errors)) {
                    foreach($errors as $error) {
                        $response = array(
                            'metadata' => array(
                                'message' => validation_errors($error),
                                'code' => 401
                            )
                        );
                    }
                } else {
                    if (num_rows($result) != '') {
                        while ($data = fetch_array($result)) {
                            if($data['terlaksana'] == 'Menunggu') {
                              $data['terlaksana'] = '0';
                            } else {
                              $data['terlaksana'] = '1';
                            }
                            $data_array[] = array(
                                    'kodebooking' => $data['kodebooking'],
                                    'tanggaloperasi' => $data['tanggaloperasi'],
                                    'jenistindakan' => $data['jenistindakan'],
                                    'kodepoli' => $data['kodepoli'],
                                    'namapoli' => $data['namapoli'],
                                    'terlaksana' => $data['terlaksana'],
                                    'nopeserta' => $data['nopeserta'],
                                    'lastupdate' => strtotime(date('Y-m-d H:i:s')) * 1000
                            );
                        }
                        $response = array(
                            'response' => array(
                                'list' => (
                                    $data_array
                                )
                            ),
                            'metadata' => array(
                                'message' => 'Ok',
                                'code' => 200
                            )
                        );
                    } else {
                        $response = array(
                            'metadata' => array(
                                'message' => 'Maaf tidak ada daftar booking operasi.',
                                'code' => 401
                            )
                        );
                    }
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
    header('Content-Type: text/html; charset=utf-8');
    $instansi=fetch_assoc(query("select nama_instansi from setting"));
    echo "Selamat Datang di API ".$instansi['nama_instansi']." Antrean BPJS Mobile JKN..";
    echo "\n\n";
    echo "&copy; ".date('Y')." ".$instansi['nama_instansi'];
}
?>
