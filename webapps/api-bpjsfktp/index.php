<?php
    require_once ('conf.php');
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json");
    header("Access-Control-Allow-Methods: POST, GET");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    $url = isset($_GET['url']) ? $_GET['url'] : '/';
    $url = explode("/", $url);
    $header = apache_request_headers();
    $method = $_SERVER['REQUEST_METHOD'];
    
    if ($method == 'GET' && !empty($header['x-username'])) {
        $hash_user = hash_pass($header['x-username'], 12);
        $hash_pass = hash_pass($header['x-password'], 12);
        switch ($url[0]) {
            case "auth":
                    $response=createtoken($header['x-username'],$header['x-password']);
                break;
            case "antrean":
                    if (!empty($url[1]) and $url[1] == "status") {
                        if(cektoken($header['x-token'])=='true'){
                            $kodepolipcare=isset($url[2])?$url[2]:null;
                            $tanggaldaftar=isset($url[3])?$url[3]:null;
                            
                            if( getOne("SELECT nm_poli FROM maping_poliklinik_pcare inner join poliklinik ON maping_poliklinik_pcare.kd_poli_rs=poliklinik.kd_poli WHERE kd_poli_pcare='$kodepolipcare'")==""){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Poli Tidak Ditemukan',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$tanggaldaftar)){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format Tanggal Tidak Sesuai, format yang benar adalah yyyy-mm-dd',
                                        'code' => 201
                                    )
                                );  
                                http_response_code(201);
                            }else if(date("Y-m-d")>$tanggaldaftar){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Tanggal Periksa Tidak Berlaku',
                                        'code' => 201
                                    )
                                );  
                                http_response_code(201);
                            }else{
                                $data = fetch_array(bukaquery("SELECT maping_poliklinik_pcare.nm_poli_pcare,COUNT(reg_periksa.kd_poli) as total_antrean,
                                    CONCAT(00,COUNT(reg_periksa.kd_poli)) as antrean_panggil,SUM(CASE WHEN reg_periksa.stts!='Sudah' THEN 1 ELSE 0 END) as sisa_antrean,
                                    ('Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih ..') as keterangan
                                    FROM reg_periksa INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli
                                    INNER JOIN maping_poliklinik_pcare ON maping_poliklinik_pcare.kd_poli_rs=reg_periksa.kd_poli
                                    WHERE reg_periksa.tgl_registrasi='$tanggaldaftar' AND maping_poliklinik_pcare.kd_poli_pcare='$kodepolipcare'"));

                                if ($data['nm_poli_pcare'] != '') {
                                    $response = array(
                                        'response' => array(
                                            'namapoli' => $data['nm_poli_pcare'],
                                            'totalantrean' => $data['total_antrean'],
                                            'sisaantrean' => $data['sisa_antrean'],
                                            'antreanpanggil' => $data['antrean_panggil'],
                                            'keterangan' => $data['keterangan']
                                        ),
                                        'metadata' => array(
                                            'message' => 'Ok',
                                            'code' => 200
                                        )
                                    );
                                    http_response_code(200);
                                } else {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Maaf belum Ada Antrian ditanggal ' . FormatTgl(("d-m-Y"),$tanggaldaftar),
                                             'code' => 200
                                        )
                                    );
                                    http_response_code(200);
                                }
                            } 
                        }else{
                            $response=cektoken($header['x-token']);
                        }
                    }

                    if (!empty($url[1]) and $url[1] == "sisapeserta") {
                        if(cektoken($header['x-token'])=='true'){
                            $no_peserta=isset($url[2])?$url[2]:null;
                            $kodepolipcare=isset($url[3])?$url[3]:null;
                            $tanggaldaftar=isset($url[4])?$url[4]:null;
                            
                            if( getOne("SELECT nm_poli FROM maping_poliklinik_pcare inner join poliklinik ON maping_poliklinik_pcare.kd_poli_rs=poliklinik.kd_poli WHERE kd_poli_pcare='$kodepolipcare'")==""){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Poli Tidak Ditemukan',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$tanggaldaftar)){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format Tanggal Tidak Sesuai, format yang benar adalah yyyy-mm-dd',
                                        'code' => 201
                                    )
                                );  
                                http_response_code(201);
                            }else if(date("Y-m-d")>$tanggaldaftar){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Tanggal Periksa Tidak Berlaku',
                                        'code' => 201
                                    )
                                );  
                                http_response_code(201);
                            }else if (empty($no_peserta)){ 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nomor kartu Tidak Boleh Kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if (mb_strlen($no_peserta, 'UTF-8') <> 13){ 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nomor kartu harus 13 digit',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if (!preg_match("/^[0-9]{13}$/",$no_peserta)){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format Nomor Kartu Tidak Sesuai',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else{
                                $data = fetch_array(bukaquery("SELECT poliklinik.nm_poli,
                                    reg_periksa.no_reg,COUNT(reg_periksa.kd_poli) as total_antrean,
                                    CONCAT(00,COUNT(reg_periksa.kd_poli)) as antrean_panggil,
                                    SUM(CASE WHEN reg_periksa.stts ='Belum' THEN 1 ELSE 0 END) as sisa_antrean,
                                    SUM(CASE WHEN reg_periksa.stts ='Sudah' THEN 1 ELSE 0 END) as sudah_selesai,
                                    ('Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih ..') as keterangan
                                    FROM reg_periksa INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli
                                    INNER JOIN maping_poliklinik_pcare ON maping_poliklinik_pcare.kd_poli_rs=reg_periksa.kd_poli
                                    INNER JOIN pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis
                                    WHERE pasien.no_peserta='$no_peserta' and reg_periksa.tgl_registrasi='$tanggaldaftar' AND maping_poliklinik_pcare.kd_poli_pcare='$kodepolipcare'"));

                                if ($data['nm_poli'] != '') {
                                    $response = array(
                                        'response' => array(
                                            'nomorantrean' => $data['no_reg'],
                                            'namapoli' => $data['nm_poli'],
                                            'sisaantrean' => $data['sisa_antrean'],
                                            'antreanpanggil' => $data['no_reg'],
                                            'keterangan' => $data['keterangan']
                                        ),
                                        'metadata' => array(
                                            'message' => 'Ok',
                                            'code' => 200
                                        )
                                    );
                                    http_response_code(200);
                                } else {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Antrean Tidak Ditemukan !',
                                            'code' => 201
                                        )
                                    );
                                    http_response_code(201);
                                } 
                            }
                        }else{
                            $response=cektoken($header['x-token']);
                        }
                    }
                break;
        }
    }
  
    if ($method == 'POST' && !empty($header['x-username']) && !empty($header['x-token'])) {
        $hash_user = hash_pass($header['x-username'], 12);
        switch ($url[0]) {
            case "antrean":
                $header = apache_request_headers();
                $konten = trim(file_get_contents("php://input"));
                $decode = json_decode($konten, true);
                $h1     = strtotime('+1 days' , strtotime($date)) ;
                $h1     = date('Y-m-d', $h1);
                $_h1    = date('d-m-Y', strtotime($h1));
                $h7     = strtotime('+1 days', strtotime($date)) ;
                $poli   = bukaquery("SELECT kd_poli_pcare, kd_poli_rs FROM maping_poliklinik_pcare WHERE kd_poli_pcare='$decode[kodepoli]'");
                
                if(cektoken($header['x-token'])=='true'){
                    if (empty($decode['nomorkartu'])){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nomor kartu Tidak Boleh Kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if (mb_strlen($decode['nomorkartu'], 'UTF-8') <> 13){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nomor kartu harus 13 digit',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if (!preg_match("/^[0-9]{13}$/",$decode['nomorkartu'])){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Nomor Kartu Tidak Sesuai',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }elseif (empty($decode['nik'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'NIK Tidak Boleh Kosong ',
                                'code' => 201
                            )
                        ); 
                        http_response_code(201);
                    }elseif (strlen($decode['nik']) <> 16) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'NIK harus 16 digit ',
                                'code' => 201
                            )
                        ); 
                        http_response_code(201);
                    }else if (!preg_match("/^[0-9]{16}$/",$decode['nik'])){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format NIK Tidak Sesuai',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }elseif(empty($decode['kodepoli'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Kode Poli Tidak Boleh Kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }elseif(empty($decode['tanggalperiksa'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Tanggal Tidak Boleh Kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggalperiksa'])){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Tanggal Tidak Sesuai, format yang benar adalah yyyy-mm-dd',
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else if(date("Y-m-d")>$decode['tanggalperiksa']){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Tanggal Periksa Tidak Berlaku Mundur',
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else if(cekJadwal($decode['tanggalperiksa'],$decode['kodepoli'])['data']=='false'){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Pendaftaran ke Poli Ini Sedang Tutup',
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else if(($decode['tanggalperiksa']==date("Y-m-d"))&&(strtotime(cekJadwal($decode['tanggalperiksa'],$decode['kodepoli'])['jam_tutup'])<strtotime(date("H:i:s")))){
                        $jadwal=cekJadwal($decode['tanggalperiksa'],$decode['kodepoli']);
                        $response = array(
                            'metadata' => array(
                                'message' => 'Pendaftaran Ke Poli '.$jadwal['namapoli'].' Sudah Tutup Jam '.$jadwal['jam_tutup'],
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else {
                        $dateNow=date('Y-m-d', strtotime('+1 days', strtotime(date("Y-m-d"))));
                        $dateEnd=date('Y-m-d', strtotime('+2 days', strtotime(date("Y-m-d"))));
                        if(empty(cekpasien($decode['nik'],$decode['nomorkartu']))){ 
                            $response = array(
                                'metadata' => array(
                                    'message' =>  "Data pasien ini tidak ditemukan, silahkan Melakukan Registrasi Pasien Baru Ke loket Administrasi Kami",
                                    'code' => 201
                                )
                            ); 
                            http_response_code(201);
                        }else{
                            $sudahdaftar=getOne2("select count(reg_periksa.no_rawat) from reg_periksa inner join maping_poliklinik_pcare on reg_periksa.kd_poli=maping_poliklinik_pcare.kd_poli_rs inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where maping_poliklinik_pcare.kd_poli_pcare='$decode[kodepoli]' and reg_periksa.tgl_registrasi='$decode[tanggalperiksa]' and pasien.no_peserta='$decode[nomorkartu]' ");
                            if($sudahdaftar>0){
                                $response = array(
                                    'metadata' => array(
                                        'message' =>  "Nomor Antrean Hanya Dapat Diambil 1 Kali Pada Tanggal dan Poli Yang Sama",
                                        'code' => 201
                                    )
                                ); 
                            }else{
                                $sekarang  = date("Y-m-d");
                                $interval  = getOne2("select (TO_DAYS('$decode[tanggalperiksa]')-TO_DAYS('$sekarang'))");
                                if($interval>1){
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Pendaftaran ke Poli Ini Sedang Tutup',
                                            'code' => 201
                                        )
                                    );  
                                    http_response_code(201);
                                }else{
                                    $hari      = hariindo($decode['tanggalperiksa']);
                                    $cek_kouta = fetch_array(bukaquery("SELECT jadwal.kuota - COALESCE((select COUNT(reg_periksa.tgl_registrasi) FROM reg_periksa 
                                            WHERE reg_periksa.tgl_registrasi='$decode[tanggalperiksa]' AND reg_periksa.kd_dokter=jadwal.kd_dokter )) as sisa_kouta, jadwal.kd_dokter, jadwal.kd_poli, 
                                            jadwal.jam_mulai + INTERVAL '10' MINUTE as jam_waktu, poliklinik.nm_poli,dokter.nm_dokter,
                                            ('Datang 30 Menit sebelum pelayanan, Konfirmasi kehadiran dibagian pendaftaran dengan menunjukan bukti pendaftaran melalui Mobile JKN, Terima Kasih..') as keterangan
                                            FROM jadwal
                                            INNER JOIN maping_poliklinik_pcare ON maping_poliklinik_pcare.kd_poli_rs=jadwal.kd_poli
                                            INNER JOIN poliklinik ON poliklinik.kd_poli=jadwal.kd_poli
                                            INNER JOIN dokter ON dokter.kd_dokter=jadwal.kd_dokter
                                            WHERE jadwal.hari_kerja='$hari' AND  maping_poliklinik_pcare.kd_poli_pcare='$decode[kodepoli]'
                                            GROUP BY jadwal.kd_dokter
                                            HAVING sisa_kouta > 0
                                            ORDER BY sisa_kouta DESC LIMIT 1"));
                                    if (!empty($cek_kouta['sisa_kouta']) and $cek_kouta['sisa_kouta'] > 0) {
                                        $datapeserta     = cekpasien($decode['nik'],$decode['nomorkartu']);
                                        $waktu_kunjungan = $decode['tanggalperiksa']." ".$cek_kouta[3];
                                        $noReg           = noRegPoli($cek_kouta['kd_poli'], $decode['tanggalperiksa']);
                                        $max             = getOne2("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0)+1 from reg_periksa where tgl_registrasi='".$decode['tanggalperiksa']."'");
                                        $no_rawat        = str_replace("-","/",$decode['tanggalperiksa']."/").sprintf("%06s", $max);
                                        $statuspoli      = getOne2("select if((select count(no_rkm_medis) from reg_periksa where no_rkm_medis='$datapeserta[no_rkm_medis]' and kd_poli='$cek_kouta[kd_poli]')>0,'Lama','Baru' )");
                                        if($datapeserta["tahun"] > 0){
                                            $umur       = $datapeserta["tahun"];
                                            $sttsumur   = "Th";
                                        }else if($datapeserta["tahun"] == 0){
                                            if($datapeserta["bulan"] > 0){
                                                $umur       = $datapeserta["bulan"];
                                                $sttsumur   = "Bl";
                                            }else if($datapeserta["bulan"] == 0){
                                                $umur       = $datapeserta["hari"];
                                                $sttsumur   = "Hr";
                                            }
                                        }
                                        $query = bukaquery("insert into reg_periksa values('$noReg', '$no_rawat', '$decode[tanggalperiksa]',current_time(), '$cek_kouta[kd_dokter]', '$datapeserta[no_rkm_medis]', '$cek_kouta[kd_poli]', '$datapeserta[namakeluarga]', '$datapeserta[alamatpj], $datapeserta[kelurahanpj], $datapeserta[kecamatanpj], $datapeserta[kabupatenpj], $datapeserta[propinsipj]', '$datapeserta[keluarga]', '".getOne("select registrasilama from poliklinik where kd_poli='$cek_kouta[kd_poli]'")."', 'Belum','Lama','Ralan', 'BPJ', '$umur','$sttsumur','Belum Bayar', '$statuspoli')");

                                        if ($query) {
                                            $response = array(
                                                'response' => array(
                                                    'nomorantrean' => $noReg,
                                                    'angkaantrean' => $noReg,
                                                    'namapoli' => $cek_kouta['nm_poli'],
                                                    'sisaantrean' => $cek_kouta['sisa_kouta']-1,
                                                    'antreanpanggil' => $noReg,
                                                    'keterangan' => $cek_kouta['keterangan']
                                                ),
                                                'metadata' => array(
                                                    'message' => 'Ok',
                                                    'code' => 200
                                                )
                                            );
                                            http_response_code(200);
                                        } else {
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => "Maaf Terjadi Kesalahan, Hubungi Admnistrator..",
                                                    'code' => 401
                                                )
                                            );
                                            http_response_code(401);
                                        }  
                                    }else{
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Kuota penuuuh...!',
                                                'code' => 201
                                            )
                                        ); 
                                        http_response_code(201);
                                    }
                                }
                            }
                        }
                    }
                }else {
                    $response=cektoken($header['x-token']);
                }
                break;
            case "peserta":
                    $response = array(
                        'metadata' => array(
                            'message' => 'Cooming Soon',
                            'code' => 505
                        )
                    );
                http_response_code(505);
                break;
        }
    }
    
    if ($method == 'PUT' && !empty($header['x-username']) && !empty($header['x-token'])) {
        $hash_user = hash_pass($header['x-username'], 12);
        switch ($url[0]) {
            case "antrean":
                if (!empty($url[1]) and $url[1] == "batal") {
                    $header = apache_request_headers();
                    $konten = trim(file_get_contents("php://input"));
                    $decode = json_decode($konten, true);

                    if(cektoken($header['x-token'])=='true'){
                        if (empty($decode['nomorkartu'])){ 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Nomor kartu Tidak Boleh Kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if (mb_strlen($decode['nomorkartu'], 'UTF-8') <> 13){ 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Nomor kartu harus 13 digit',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if (!preg_match("/^[0-9]{13}$/",$decode['nomorkartu'])){ 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Format Nomor Kartu Tidak Sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($decode['kodepoli'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Kode Poli Tidak Boleh Kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($decode['tanggalperiksa'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Tanggal Tidak Boleh Kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggalperiksa'])){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Format Tanggal Tidak Sesuai, format yang benar adalah yyyy-mm-dd',
                                    'code' => 201
                                )
                            );  
                            http_response_code(201);
                        }else {
                            $cek = fetch_array(bukaquery("select reg_periksa.no_rawat,reg_periksa.stts from reg_periksa inner join maping_poliklinik_pcare on reg_periksa.kd_poli=maping_poliklinik_pcare.kd_poli_rs inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where maping_poliklinik_pcare.kd_poli_pcare='$decode[kodepoli]' and reg_periksa.tgl_registrasi='$decode[tanggalperiksa]' and pasien.no_peserta='$decode[nomorkartu]' "));
                            if (!empty($cek['no_rawat'])) {
                                if($cek['stts']=="Batal"){
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Antrean sudah dibatalkan !',
                                            'code' => 201
                                        )
                                    );
                                    http_response_code(201);
                                }else if($cek['stts']=="Belum"){
                                    $query = bukaquery("update reg_periksa set stts='Batal' where no_rawat='$cek[no_rawat]'");
                                    if ($query) {
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Ok',
                                                'code' => 200
                                            )
                                        );
                                        http_response_code(200);
                                    } else {
                                        $response = array(
                                            'metadata' => array(
                                                'message' => "Maaf Terjadi kesalahan, Silahkan hubungi Administrator!",
                                                'code' => 401
                                            )
                                        );
                                        http_response_code(401);
                                    }
                                }else{
                                    $response = array(
                                        'metadata' => array(
                                            'message' => "Pasien Sudah Dilayani, Antrean Tidak Dapat Dibatalkan!",
                                            'code' => 201
                                        )
                                    );
                                    http_response_code(201);
                                }
                            } else  {
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Antrean Tidak Ditemukan !',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }
                        }
                    }else{
                        $response=cektoken($header['x-token']);
                    }
                }
                break;
        }
    }
    
    if (!empty($response)) {
        echo json_encode($response);
    } else {
        $instansi=fetch_assoc(bukaquery("select nama_instansi from setting"));
        echo "Selamat Datang di Web Service Antrean BPJS Mobile JKN FKTP ".$instansi['nama_instansi']." ".date('Y');
        echo "\n\n";
        echo "Cara Menggunakan Web Service Antrean BPJS Mobile JKN FKTP : \n";
        echo "1. Mengambil Token, methode GET \n";
        echo "   gunakan URL http://ipserverws:port/webapps/api-bpjsfktp/auth \n";
        echo "   Header gunakan x-username:user yang diberikan RS, x-password:pass yang diberikan RS\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '         "token": "xxxxxxxxxxxxxxxxx'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '         "message": "Ok",'."\n";
        echo '         "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "2. Menampilkan status atrian poli, methode GET\n";
        echo "   gunakan URL http://ipserverws:port/webapps/api-bpjsfktp/antrean/status/KodePoliPCare/TanggalRegistrasi \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, TanggalRegistrasi gunakan format yyyy-mm-dd,\n";
        echo "   x-username:user yang diberikan RS, x-password:pass yang diberikan RS\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "namapoli": "xxxxxx",'."\n";
        echo '          "totalantrean": "x",'."\n";
        echo '          "sisaantrean": "x",'."\n";
        echo '          "antreanpanggil": "xx",'."\n";
        echo '          "keterangan": "Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih .."'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "3. Menampilkan sisa antrian berdasarkan tanggal pendaftaran pasien, methode GET\n";
        echo "   gunakan URL http://ipserverws:port/webapps/api-bpjsfktp/antrean/sisapeserta/NomorKartu/KodePoliPCare/TanggalRegistrasi \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, TanggalRegistrasi gunakan format yyyy-mm-dd\n";
        echo "   x-username:user yang diberikan RS, x-password:pass yang diberikan RS\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "nomorantrean": "001",'."\n";
        echo '          "namapoli": "Poliklinik Kandungan",'."\n";
        echo '          "sisaantrean": "1",'."\n";
        echo '          "antreanpanggil": "001",'."\n";
        echo '          "keterangan": "Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih .."'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "4. Mengambil antrian, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/webapps/api-bpjsfktp/antrean \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, TanggalRegistrasi gunakan format yyyy-mm-dd\n";
        echo "   x-username:user yang diberikan RS, x-password:pass yang diberikan RS\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "nomorkartu":"XXXXXXXXX",'."\n";
	echo '      "nik":"XXXXXXX",'."\n";
	echo '      "kodepoli":"XXX",'."\n";
	echo '      "tanggalperiksa":"XXXX-XX-XX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "nomorantrean": "002",'."\n";
        echo '          "angkaantrean": "002",'."\n";
        echo '          "namapoli": "Poliklinik Kandungan",'."\n";
        echo '          "sisaantrean": 38,'."\n";
        echo '          "antreanpanggil": "002",'."\n";
        echo '          "keterangan": "Datang 30 Menit sebelum pelayanan, Konfirmasi kehadiran dibagian pendaftaran dengan menunjukan bukti pendaftaran melalui Mobile JKN, Terima Kasih.."'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "4. Membatalkan antrian, methode PUT\n";
        echo "   gunakan URL http://ipserverws:port/webapps/api-bpjsfktp/antrean/batal \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, TanggalRegistrasi gunakan format yyyy-mm-dd\n";
        echo "   x-username:user yang diberikan RS, x-password:pass yang diberikan RS\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "nomorkartu":"XXXXXXXXX",'."\n";
	echo '      "nik":"XXXXXXX",'."\n";
	echo '      "tanggalperiksa":"XXXX-XX-XX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
    }
?>
