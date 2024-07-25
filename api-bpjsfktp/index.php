<?php
    header("X-Robots-Tag: noindex", true);
    require_once ('conf.php');
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json");
    header("Access-Control-Allow-Methods: POST, GET");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    $url     = isset($_GET['url']) ? $_GET['url'] : '/';
    $url     = explode("/", $url);
    $header  = apache_request_headers();
    $newhead = array();
    if($header) foreach($header as $idx => $val) {
        $newhead[strtolower($idx)] = $val;
    }
    $header = $newhead;
    $method = $_SERVER['REQUEST_METHOD'];
    
    if ($method == 'GET') {
        $hash_user = hash_pass(validTeks4($header['x-username'],20), 12);
        switch ($url[0]) {
            case "auth":
                $hash_pass = hash_pass(validTeks4($header['x-username'],20), 12);
                if((!empty($header['x-username'])) && (!empty($header['x-password']))){
                    $response=createtoken(validTeks4($header['x-username'],20),validTeks4($header['x-password'],20));
                }else{
                    $response = array(
                        'metadata' => array(
                            'message' => 'Nama User / Password / Token ada yang salah ..!!',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }
                break;
            case "antrean":
                    if (!empty($url[1]) and $url[1] == "status") {
                        if((!empty($header['x-token'])) && (!empty($header['x-username'])) && (USERNAME==validTeks4($header['x-username'],20)) && (cektoken($header['x-token'])=='true')){
                            $kodepolipcare=isset($url[2])?$url[2]:null;
                            $tanggaldaftar=isset($url[3])?$url[3]:null;
                            if(strpos($kodepolipcare,"'")||strpos($kodepolipcare,"\\")){
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
                                if( getOne("SELECT nm_poli FROM maping_poliklinik_pcare inner join poliklinik ON maping_poliklinik_pcare.kd_poli_rs=poliklinik.kd_poli WHERE kd_poli_pcare='".validTeks4($kodepolipcare,20)."'")==""){
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Poli Tidak Ditemukan',
                                            'code' => 201
                                        )
                                    );
                                    http_response_code(201);
                                }else{
                                    $data = fetch_array(
                                        bukaquery(
                                            "SELECT maping_poliklinik_pcare.nm_poli_pcare,COUNT(reg_periksa.kd_poli) as total_antrean,
                                            CONCAT(00,COUNT(reg_periksa.kd_poli)) as antrean_panggil,SUM(CASE WHEN reg_periksa.stts='Belum' THEN 1 ELSE 0 END) as sisa_antrean,
                                            maping_dokter_pcare.kd_dokter_pcare,maping_dokter_pcare.nm_dokter_pcare,reg_periksa.kd_poli,reg_periksa.kd_dokter
                                            FROM reg_periksa INNER JOIN maping_poliklinik_pcare ON maping_poliklinik_pcare.kd_poli_rs=reg_periksa.kd_poli
                                            INNER JOIN maping_dokter_pcare ON maping_dokter_pcare.kd_dokter=reg_periksa.kd_dokter
                                            WHERE reg_periksa.tgl_registrasi='".validTeks4($tanggaldaftar,20)."' AND maping_poliklinik_pcare.kd_poli_pcare='".validTeks4($kodepolipcare,20)."'"
                                        )
                                    );
                                    
                                    if ($data['nm_poli_pcare'] != '') {
                                        $hari     = strtoupper(hariindo(validTeks4($tanggaldaftar,20)));
                                        $jampraktek = getOne2("SELECT concat(left(jadwal.jam_mulai,5),'-',left(jadwal.jam_selesai,5)) FROM jadwal WHERE jadwal.kd_poli='".$data['kd_poli']."' and jadwal.kd_dokter='".$data['kd_dokter']."' and jadwal.hari_kerja='$hari'");
                                        if($jampraktek==""){
                                            $jampraktek = "08:00-12:00";
                                        }
                                        $dataarray[] = array(
                                            'namapoli' => $data['nm_poli_pcare'],
                                            'totalantrean' => $data['total_antrean'],
                                            'sisaantrean' => $data['sisa_antrean'],
                                            'antreanpanggil' => $data['antrean_panggil'],
                                            'keterangan' => 'Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih ..',
                                            'kodedokter' => $data['kd_dokter_pcare'],
                                            'namadokter' => $data['nm_dokter_pcare'],
                                            'jampraktek' => $jampraktek
                                        ); 
                                        $response = array(
                                            'response' => $dataarray,
                                            'metadata' => array(
                                                'message' => 'Ok',
                                                'code' => 200
                                            )
                                        );
                                        http_response_code(200);
                                    } else {
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Maaf belum ada antrian di tanggal ' . FormatTgl(("d-m-Y"),$tanggaldaftar),
                                                 'code' => 200
                                            )
                                        );
                                        http_response_code(200);
                                    }
                                }
                            } 
                        }else{
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Nama User / Password / Token ada yang salah ..!!',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }
                    }

                    if (!empty($url[1]) and $url[1] == "sisapeserta") {
                        if((!empty($header['x-token'])) && (!empty($header['x-username'])) && (USERNAME==validTeks4($header['x-username'],20)) && (cektoken($header['x-token'])=='true')){
                            $no_peserta=isset($url[2])?$url[2]:null;
                            $kodepolipcare=isset($url[3])?$url[3]:null;
                            $tanggaldaftar=isset($url[4])?$url[4]:null;
                            
                            if(strpos($kodepolipcare,"'")||strpos($kodepolipcare,"\\")){
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
                                if( getOne("SELECT nm_poli FROM maping_poliklinik_pcare inner join poliklinik ON maping_poliklinik_pcare.kd_poli_rs=poliklinik.kd_poli WHERE kd_poli_pcare='".validTeks4($kodepolipcare,20)."'")==""){
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Poli Tidak Ditemukan',
                                            'code' => 201
                                        )
                                    );
                                    http_response_code(201);
                                }else{
                                    $data = fetch_array(bukaquery("SELECT poliklinik.nm_poli,
                                        reg_periksa.no_reg,COUNT(reg_periksa.kd_poli) as total_antrean,
                                        CONCAT(00,COUNT(reg_periksa.kd_poli)) as antrean_panggil,
                                        SUM(CASE WHEN reg_periksa.stts ='Belum' THEN 1 ELSE 0 END) as sisa_antrean,
                                        SUM(CASE WHEN reg_periksa.stts !='Belum' THEN 1 ELSE 0 END) as sudah_selesai,
                                        ('Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih ..') as keterangan
                                        FROM reg_periksa INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli
                                        INNER JOIN maping_poliklinik_pcare ON maping_poliklinik_pcare.kd_poli_rs=reg_periksa.kd_poli
                                        INNER JOIN pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis
                                        WHERE pasien.no_peserta='$no_peserta' and reg_periksa.tgl_registrasi='".validTeks4($tanggaldaftar,20)."' AND maping_poliklinik_pcare.kd_poli_pcare='".validTeks4($kodepolipcare,20)."'"));

                                    if ($data['nm_poli'] != '') {
                                        $response = array(
                                            'response' => array(
                                                'nomorantrean' => $data['no_reg'],
                                                'namapoli' => $data['nm_poli'],
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
                                                'message' => 'Antrean Tidak Ditemukan !',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    } 
                                }
                            }
                        }else{
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Nama User / Password / Token ada yang salah ..!!',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }
                    }
                break;
        }
    }
  
    if (($method == 'POST') && (!empty($header['x-username'])) && (!empty($header['x-token']))) {
        $hash_user = hash_pass(validTeks4($header['x-username'],20), 12);
        switch ($url[0]) {
            case "antrean":
                $konten = trim(file_get_contents("php://input"));
                $decode = json_decode($konten, true);
                
                if((!empty($header['x-token'])) && (!empty($header['x-username'])) && (USERNAME==validTeks4($header['x-username'],20)) && (cektoken($header['x-token'])=='true')){
                    if (empty($decode['nomorkartu'])){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nomor Kartu tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if (mb_strlen($decode['nomorkartu'], 'UTF-8') <> 13){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nomor Kartu harus 13 digit',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if (!preg_match("/^[0-9]{13}$/",$decode['nomorkartu'])){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Nomor Kartu tidak sesuai',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }elseif (empty($decode['nik'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'NIK tidak boleh kosong ',
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
                                'message' => 'Format NIK tidak sesuai',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }elseif(empty($decode['nohp'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'No.HP tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['nohp'],"'")||strpos($decode['nohp'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format No.HP salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                   }elseif(empty($decode['kodepoli'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Kode Poli tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['kodepoli'],"'")||strpos($decode['kodepoli'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Poli tidak ditemukan',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                   }else if(empty($decode['kodedokter'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Kode Dokter tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['kodedokter'],"'")||strpos($decode['kodedokter'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Dokter tidak ditemukan',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }elseif(empty($decode['tanggalperiksa'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Tanggal tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggalperiksa'])){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Tanggal tidak sesuai, format yang benar adalah yyyy-mm-dd',
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else if(date("Y-m-d")>$decode['tanggalperiksa']){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Tanggal Periksa tidak berlaku mundur',
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else if(empty($decode['jampraktek'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Jam Praktek tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['jampraktek'],"'")||strpos($decode['jampraktek'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Jam Praktek tidak ditemukan',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['keluhan'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Keluhan tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['keluhan'],"'")||strpos($decode['keluhan'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Keluhan tidak sesuai format',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else {
                        $jammulai   = validTeks4(substr($decode['jampraktek'],0,5),20);
                        $jamselesai = validTeks4(substr($decode['jampraktek'],6,5),20);
                        $kddokter   = getOne2("SELECT maping_dokter_pcare.kd_dokter FROM maping_dokter_pcare WHERE maping_dokter_pcare.kd_dokter_pcare='".validTeks4($decode['kodedokter'],20)."'");
                        $hari       = strtoupper(hariindo(validTeks4($decode['tanggalperiksa'],20)));
                        $kdpoli     = getOne2("SELECT maping_poliklinik_pcare.kd_poli_rs FROM maping_poliklinik_pcare WHERE maping_poliklinik_pcare.kd_poli_pcare='".validTeks($decode["kodepoli"])."'");
                        if(empty($kdpoli)) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Poli tidak ditemukan',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($kddokter)) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Dokter tidak ditemukan',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else{
                            $jadwal = fetch_array(bukaquery2("select jadwal.kd_dokter,dokter.nm_dokter,jadwal.hari_kerja,jadwal.jam_mulai,jadwal.jam_selesai,jadwal.kd_poli,poliklinik.nm_poli,jadwal.kuota 
                                            from jadwal inner join poliklinik ON poliklinik.kd_poli=jadwal.kd_poli inner join dokter ON jadwal.kd_dokter=dokter.kd_dokter 
                                            where jadwal.hari_kerja='$hari' and jadwal.kd_dokter='$kddokter' and jadwal.kd_poli='$kdpoli' and jadwal.jam_mulai='$jammulai:00' and jadwal.jam_selesai='$jamselesai:00'"));
                            if(empty($jadwal['kuota'])) {
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Pendaftaran ke Poli ini tidak tersedia',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else{
                                if(empty(cekpasien(validTeks4($decode['nik'],20),validTeks4($decode['nomorkartu'],20)))){ 
                                    /* Silahkan aktifkan ini jika tidak ingin BPJS bisa menginsert data pasien baru
                                     * $response = array(
                                        'metadata' => array(
                                            'message' =>  'Data pasien ini tidak ditemukan, silahkan melakukan registrasi pasien baru ke loket administrasi Kami',
                                            'code' => 201
                                        )
                                    ); 
                                    http_response_code(201);*/
                                    $response = array(
                                        'metadata' => array(
                                            'message' =>  'Data pasien ini tidak ditemukan',
                                            'code' => 202
                                        )
                                    ); 
                                    http_response_code(202);
                                }else{
                                    /*if(empty($decode['norm'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'No.RM tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else */
                                    if(strpos($decode['norm'],"'")||strpos($decode['norm'],"\\")){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format No.RM salah',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else{
                                        $sudahdaftar=getOne2("select count(reg_periksa.no_rawat) from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.kd_poli='$kdpoli' and reg_periksa.kd_dokter='$kddokter' and reg_periksa.tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' and pasien.no_peserta='".validTeks4($decode['nomorkartu'],20)."' and reg_periksa.stts<>'Batal'");
                                        if($sudahdaftar>0){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' =>  "Nomor Antrean hanya dapat diambil 1 kali pada Tanggal, Dokter dan Poli yang sama",
                                                    'code' => 201
                                                )
                                            ); 
                                        }else{
                                            $sekarang  = date("Y-m-d");
                                            $interval  = getOne2("select (TO_DAYS('".validTeks4($decode["tanggalperiksa"],20)."')-TO_DAYS('$sekarang'))");
                                            if($interval<0){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Pendaftaran ke Poli ini sudah tutup',
                                                        'code' => 201
                                                    )
                                                );  
                                                http_response_code(201);
                                            }else{
                                                $sisakuota=getOne2("select count(reg_periksa.no_rawat) from reg_periksa where reg_periksa.kd_poli='$kdpoli' and reg_periksa.kd_dokter='$kddokter' and reg_periksa.tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' and reg_periksa.stts<>'Batal'");
                                                if ($sisakuota < $jadwal['kuota']) {
                                                    $datapeserta     = cekpasien(validTeks4($decode['nik'],20),validTeks4($decode['nomorkartu'],20));
                                                    $noReg           = noRegPoli($kdpoli,$kddokter,validTeks4($decode['tanggalperiksa'],20));
                                                    $max             = getOne2("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0)+1 from reg_periksa where tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."'");
                                                    $no_rawat        = str_replace("-","/",validTeks4($decode['tanggalperiksa'],20)."/").sprintf("%06s", $max);
                                                    $statuspoli      = getOne2("select if((select count(no_rkm_medis) from reg_periksa where no_rkm_medis='$datapeserta[no_rkm_medis]' and kd_poli='$kdpoli')>0,'Lama','Baru' )");
                                                    $statusdaftar    = $datapeserta['tgl_daftar']==$decode['tanggalperiksa']?"1":"0";
                                                    
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
                                                    
                                                    $query = bukaquery2("insert into reg_periksa values('$noReg', '$no_rawat', '".validTeks4($decode['tanggalperiksa'],20)."',current_time(), '$kddokter', '$datapeserta[no_rkm_medis]', '$kdpoli', '$datapeserta[namakeluarga]', '$datapeserta[alamatpj], $datapeserta[kelurahanpj], $datapeserta[kecamatanpj], $datapeserta[kabupatenpj], $datapeserta[propinsipj]', '$datapeserta[keluarga]', '".getOne2("select poliklinik.registrasilama from poliklinik where poliklinik.kd_poli='$kdpoli'")."', 'Belum','".str_replace("0","Lama",str_replace("1","Baru",$statusdaftar))."','Ralan', '".CARABAYAR."', '$umur','$sttsumur','Belum Bayar', '$statuspoli')");
                                                    if ($query) {
                                                        $response = array(
                                                            'response' => array(
                                                                'nomorantrean' => $noReg,
                                                                'angkaantrean' => intval($noReg),
                                                                'namapoli' => $jadwal['nm_poli'],
                                                                'sisaantrean' => getOne2("SELECT IFNULL(SUM(CASE WHEN reg_periksa.stts ='Belum' THEN 1 ELSE 0 END),0) as sisa_antrean FROM reg_periksa WHERE reg_periksa.tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' AND reg_periksa.kd_poli='$kdpoli' AND reg_periksa.kd_dokter='$kddokter'"),
                                                                'antreanpanggil' => getOne2("select reg_periksa.no_reg from reg_periksa where reg_periksa.stts='Belum' and reg_periksa.kd_dokter='$kddokter' and reg_periksa.kd_poli='$kdpoli' and reg_periksa.tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' order by CONVERT(RIGHT(reg_periksa.no_reg,3),signed) limit 1 "),
                                                                'keterangan'=> 'Apabila antrean terlewat harap mengambil antrean kembali.'
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
                                                                'message' => "Maaf terjadi kesalahan, hubungi Admnistrator..",
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
                            }
                        }
                    }
                }else {
                    $response = array(
                        'metadata' => array(
                            'message' => 'Nama User / Password / Token ada yang salah ..!!',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }
                break;
            case "peserta":
                $konten = trim(file_get_contents("php://input"));
                $decode = json_decode($konten, true);
                if((!empty($header['x-token'])) && (USERNAME==validTeks4($header['x-username'],20)) && (cektoken($header['x-token'])=='true')){
                    if (empty($decode['nomorkartu'])){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nomor Kartu tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if (mb_strlen($decode['nomorkartu'], 'UTF-8') <> 13){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nomor Kartu harus 13 digit',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if (!preg_match("/^[0-9]{13}$/",$decode['nomorkartu'])){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Nomor Kartu tidak sesuai',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }elseif (empty($decode['nik'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'NIK tidak boleh kosong ',
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
                                'message' => 'Format NIK tidak sesuai',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }elseif (empty($decode['nomorkk'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nomor KK tidak boleh kosong ',
                                'code' => 201
                            )
                        ); 
                        http_response_code(201);
                    }elseif (strlen($decode['nomorkk']) <> 16) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nomor KK harus 16 digit ',
                                'code' => 201
                            )
                        ); 
                        http_response_code(201);
                    }else if (!preg_match("/^[0-9]{16}$/",$decode['nomorkk'])){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Nomor KK tidak sesuai',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['nama'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nama tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['nama'],"'")||strpos($decode['nama'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Nama salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['jeniskelamin'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Jenis Kelamin tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['jeniskelamin'],"'")||strpos($decode['jeniskelamin'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Jenis Kelamin tidak ditemukan',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(!(($decode['jeniskelamin']=="L")||($decode['jeniskelamin']=="P"))){ 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Jenis Kelmain tidak ditemukan',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['tanggallahir'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Tanggal Lahir tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggallahir'])){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Tanggal Lahir tidak sesuai, format yang benar adalah yyyy-mm-dd',
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else if($decode['tanggallahir']>date("Y-m-d")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Tanggal lahir pasien salah',
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else if(empty($decode['alamat'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Alamat tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['alamat'],"'")||strpos($decode['alamat'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Alamat salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['kodeprop'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Kode Propinsi tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['kodeprop'],"'")||strpos($decode['kodeprop'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Kode Propinsi salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['namaprop'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nama Propinsi tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['namaprop'],"'")||strpos($decode['namaprop'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Nama Propinsi salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['kodedati2'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Kode Dati II tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['kodedati2'],"'")||strpos($decode['kodedati2'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Kode Dati II salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['namadati2'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nama Dati II tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['namadati2'],"'")||strpos($decode['namadati2'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Nama Dati II salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['kodekec'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Kode Kecamatan tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['kodekec'],"'")||strpos($decode['kodekec'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Kode Kecamatan salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['namakec'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nama Kecamatan tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['namakec'],"'")||strpos($decode['namakec'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Nama Kecamatan salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['kodekel'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Kode Kelurahan tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['kodekel'],"'")||strpos($decode['kodekel'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Kode Kelurahan salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['namakel'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nama Kelurahan tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['namakel'],"'")||strpos($decode['namakel'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format Nama Kelurahan salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['rw'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'RW tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['rw'],"'")||strpos($decode['rw'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format RW salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(empty($decode['rt'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'RT tidak boleh kosong',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else if(strpos($decode['rt'],"'")||strpos($decode['rt'],"\\")){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Format RT salah',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else{
                        if(!empty(cekpasien($decode['nik'],$decode['nomorkartu']))){ 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Pasien dengan NIK dan No.Kartu tersebut sudah terdaftar',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else{
                            $setrm          = fetch_array(bukaquery2("select * from set_urut_no_rkm_medis"));
                            $awalantahun    = "";
                            $awalanbulan    = "";
                            $norm           = "";
                            $nourut         = "";

                            if($setrm["tahun"]=="Yes"){
                                $awalantahun=date("y");
                            }else{
                                $awalantahun="";
                            }

                            if($setrm["bulan"]=="Yes"){
                                $awalanbulan=date('m');
                            }else{
                                $awalanbulan="";
                            }

                           if($setrm["posisi_tahun_bulan"]=="Depan"){
                                switch ($setrm["urutan"]) {
                                    case "Straight":
                                        $max    = getOne2("select ifnull(MAX(CONVERT(RIGHT(no_rkm_medis,6),signed)),0)+1 from set_no_rkm_medis");
                                        $nourut = sprintf("%06s", $max);
                                        break;
                                    case "Terminal":
                                        $max    = getOne2("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(no_rkm_medis,6),5,2),SUBSTRING(RIGHT(no_rkm_medis,6),3,2),SUBSTRING(RIGHT(no_rkm_medis,6),1,2)),signed)),0)+1 from set_no_rkm_medis");
                                        $nourut = substr(sprintf("%06s", $max),4,2).substr(sprintf("%06s", $max),2,2).substr(sprintf("%06s", $max),0,2);
                                        break;
                                    case "Middle":
                                        $max    = getOne2("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(no_rkm_medis,6),3,2),SUBSTRING(RIGHT(no_rkm_medis,6),1,2),SUBSTRING(RIGHT(no_rkm_medis,6),5,2)),signed)),0)+1 from set_no_rkm_medis");
                                        $nourut = substr(sprintf("%06s", $max),2,2).substr(sprintf("%06s", $max),0,2).substr(sprintf("%06s", $max),4,2);
                                        break;
                                }
                            }else if($setrm["posisi_tahun_bulan"]=="Belakang"){
                                switch ($setrm["urutan"]) {
                                    case "Straight":
                                        $max    = getOne2("select ifnull(MAX(CONVERT(LEFT(no_rkm_medis,6),signed)),0)+1 from set_no_rkm_medis");
                                        $nourut = sprintf("%06s", $max);
                                        break;
                                    case "Terminal":
                                        $max    = getOne2("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(no_rkm_medis,6),5,2),SUBSTRING(LEFT(no_rkm_medis,6),3,2),SUBSTRING(LEFT(no_rkm_medis,6),1,2)),signed)),0)+1 from set_no_rkm_medis");
                                        $nourut = substr(sprintf("%06s", $max),4,2).substr(sprintf("%06s", $max),2,2).substr(sprintf("%06s", $max),0,2);
                                        break;
                                    case "Middle":
                                        $max    = getOne2("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(no_rkm_medis,6),3,2),SUBSTRING(LEFT(no_rkm_medis,6),1,2),SUBSTRING(LEFT(no_rkm_medis,6),5,2)),signed)),0)+1 from set_no_rkm_medis");
                                        $nourut = substr(sprintf("%06s", $max),2,2).substr(sprintf("%06s", $max),0,2).substr(sprintf("%06s", $max),4,2);
                                        break;
                                }            
                            }

                            if($setrm["posisi_tahun_bulan"]=="Depan"){
                                $norm=$awalantahun.$awalanbulan.$nourut;
                            }else if($setrm["posisi_tahun_bulan"]=="Belakang"){
                                if(strlen($awalanbulan.$awalantahun)>0){
                                    $norm=$nourut."-".$awalanbulan.$awalantahun;
                                }else{
                                    $norm=$nourut;
                                }            
                            }

                            bukaquery3("insert ignore into kelurahan values('0','".validTeks4($decode['namakel'],30)."')");
                            bukaquery3("insert ignore into kecamatan values('0','".validTeks4($decode['namakec'],30)."')");
                            bukaquery3("insert ignore into kabupaten values('0','".validTeks4($decode['namadati2'],30)."')");
                            bukaquery3("insert ignore into propinsi values('0','".validTeks4($decode['namaprop'],30)."')");

                            $query = bukaquery2("insert into pasien values('$norm','".validTeks4($decode['nama'],60)."','".validTeks4($decode['nik'],20)."','".validTeks4($decode['jeniskelamin'],20)."','-','".validTeks4($decode['tanggallahir'],20)."','-','".validTeks4($decode['alamat'],100)."','-','-','JOMBLO','-',current_date(),'0','0','-','SAUDARA','-','".CARABAYAR."','".validTeks4($decode['nomorkartu'],20)."','".getOne2("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel='".validTeks4($decode['namakel'],30)."'")."','".getOne2("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec='".validTeks4($decode['namakec'],30)."'")."','".getOne2("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab='".validTeks4($decode['namadati2'],30)."'")."','-','".validTeks4($decode['alamat'],100)."','".validTeks4($decode['namakel'],30)."','".validTeks4($decode['namakec'],30)."','".validTeks4($decode['namadati2'],30)."','-','1','1','1','-','-','".getOne2("select propinsi.kd_prop from propinsi where propinsi.nm_prop='".validTeks4($decode['namaprop'],30)."'")."','".validTeks4($decode['namaprop'],30)."')");
                            if ($query) {
                                $response = array(
                                    'response' => array(
                                        'norm' => $norm
                                    ),
                                    'metadata' => array(
                                        'message' => 'Pasien berhasil mendapatkann nomor RM, silahkan lanjutkan ke booking. Pasien tidak perlu ke admisi',
                                        'code' => 200
                                    )
                                );
                                http_response_code(200);
                                bukaquery2("delete from set_no_rkm_medis");
                                bukaquery2("insert into set_no_rkm_medis values('$norm')");
                            }else{
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Maaf Terjadi Kesalahan, Hubungi Admnistrator..',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }
                        }
                    }
                }else {
                    $response = array(
                        'metadata' => array(
                            'message' => 'Nama User / Password / Token ada yang salah ..!!',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }
                break;
        }
    }
    
    if (($method == 'PUT') && (!empty($header['x-username'])) && (!empty($header['x-token']))) {
        $hash_user = hash_pass(validTeks4($header['x-username'],20), 12);
        switch ($url[0]) {
            case "antrean":
                if (!empty($url[1]) and $url[1] == "batal") {
                    $konten = trim(file_get_contents("php://input"));
                    $decode = json_decode($konten, true);

                    if((!empty($header['x-token'])) && (!empty($header['x-username'])) && (USERNAME==validTeks4($header['x-username'],20)) && (cektoken($header['x-token'])=='true')){
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
                        }else if(strpos($decode['kodepoli'],"'")||strpos($decode['kodepoli'],"\\")){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Poli Tidak Ditemukan',
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
                        }else if(empty($decode['keterangan'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Keterangan tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(strpos($decode['keterangan'],"'")||strpos($decode['keterangan'],"\\")){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Keterangan tidak sesuai format',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else {
                            $cek = fetch_array(bukaquery("select reg_periksa.no_rawat,reg_periksa.stts from reg_periksa inner join maping_poliklinik_pcare on reg_periksa.kd_poli=maping_poliklinik_pcare.kd_poli_rs inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where maping_poliklinik_pcare.kd_poli_pcare='".validTeks4($decode["kodepoli"],20)."' and reg_periksa.tgl_registrasi='".validTeks4($decode["tanggalperiksa"],20)."' and pasien.no_peserta='".validTeks4($decode["nomorkartu"],20)."' order by reg_periksa.tgl_registrasi desc,reg_periksa.jam_reg desc limit 1"));
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
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nama User / Password / Token ada yang salah ..!!',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
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
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktp/auth \n";
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
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktp/antrean/status/KodePoliPCare/TanggalRegistrasi \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, TanggalRegistrasi gunakan format yyyy-mm-dd,\n";
        echo "   x-username:user yang diberikan RS yang diberikan RS\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": [{'."\n";
        echo '          "namapoli": "xxxxxx",'."\n";
        echo '          "totalantrean": "x",'."\n";
        echo '          "sisaantrean": x,'."\n";
        echo '          "antreanpanggil": "xx",'."\n";
        echo '          "keterangan": "xxxxxxxxxxx",'."\n";
        echo '          "kodedokter": "xxxxxx",'."\n";
        echo '          "namadokter": "xxxxxx",'."\n";
        echo '          "jampraktek": "xxxxxxxxxxx"'."\n";
        echo '      }],'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "3. Menampilkan sisa antrian berdasarkan tanggal pendaftaran pasien, methode GET\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktp/antrean/sisapeserta/NomorKartu/KodePoliPCare/TanggalRegistrasi \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, TanggalRegistrasi gunakan format yyyy-mm-dd\n";
        echo "   x-username:user yang diberikan RS yang diberikan RS\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "nomorantrean": "001",'."\n";
        echo '          "namapoli": "Poliklinik Kandungan",'."\n";
        echo '          "sisaantrean": "1",'."\n";
        echo '          "antreanpanggil": "001",'."\n";
        echo '          "keterangan": "xxxxxxx."'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "4. Mengambil antrian, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktp/antrean \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, TanggalRegistrasi gunakan format yyyy-mm-dd\n";
        echo "   x-username:user yang diberikan RS yang diberikan RS\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "nomorkartu":"XXXXXXXXX",'."\n";
	echo '      "nik":"XXXXXXX",'."\n";
	echo '      "kodepoli":"XXX",'."\n";
	echo '      "tanggalperiksa":"XXXX-XX-XX",'."\n";
	echo '      "keluhan":"XXXXXXXX",'."\n";
	echo '      "kodedokter":XXXXXXXX,'."\n";
	echo '      "jampraktek":"XXXX-XX-XX",'."\n";
	echo '      "norm":"XXXXXXXX",'."\n";
	echo '      "nohp":"XXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "nomorantrean": "002",'."\n";
        echo '          "angkaantrean": "002",'."\n";
        echo '          "namapoli": "Poliklinik Kandungan",'."\n";
        echo '          "sisaantrean": 38,'."\n";
        echo '          "antreanpanggil": "002",'."\n";
        echo '          "keterangan": "xxxxxxxx.."'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "5. Membatalkan antrian, methode PUT\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktp/antrean/batal \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, TanggalRegistrasi gunakan format yyyy-mm-dd\n";
        echo "   x-username:user yang diberikan RS yang diberikan RS\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "nomorkartu":"XXXXXXXXX",'."\n";
	echo '      "kodepoli":"XXXXXXX",'."\n";
	echo '      "tanggalperiksa":"XXXX-XX-XX",'."\n";
	echo '      "keterangan":"XXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "6. Pasien Baru, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/peserta \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "nomorkartu": "XXXXXXXXXXXXX",'."\n";
        echo '      "nik": "XXXXXXXXXXX",'."\n";
        echo '      "nomorkk": "XXXXXXXX",'."\n";
        echo '      "nama": "XXXXXXXXXXXXXXXXXXXXXXXXXXX",'."\n";
        echo '      "jeniskelamin": "L/P",'."\n";
        echo '      "tanggallahir": "XXXX-XX-XX",'."\n";
        echo '      "alamat": "XXXXXXXX",'."\n";
        echo '      "kodeprop": "XX",'."\n";
        echo '      "namaprop": "XXXXXXXXXXXXXXX",'."\n";
        echo '      "kodedati2": "XXXXXXX",'."\n";
        echo '      "namadati2": "XXXXXXXXXXXXXXX",'."\n";
        echo '      "kodekec": "XXXX",'."\n";
        echo '      "namakec": "XXXXXXXXXXXXXX",'."\n";
        echo '      "kodekel": "XXXX",'."\n";
        echo '      "namakel": "XXXXXXXXXXXXX",'."\n";
        echo '      "rw": "XXX",'."\n";
        echo '      "rt": "XXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "norm": "XXXXXX",'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
    }
?>
