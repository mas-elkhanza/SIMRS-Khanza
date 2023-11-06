<?php
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
        $hash_user = hash_pass($header['x-username'], 12);
        $hash_pass = hash_pass($header['x-password'], 12);
        switch ($url[0]) {
            case "auth":
                if((!empty($header['x-username'])) && (!empty($header['x-password']))){
                    $response=createtoken($header['x-username'],$header['x-password']);
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
                        if((!empty($header['x-token'])) && (!empty($header['x-username'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
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
                                    $data = fetch_array(bukaquery("SELECT maping_poliklinik_pcare.nm_poli_pcare,COUNT(reg_periksa.kd_poli) as total_antrean,
                                        CONCAT(00,COUNT(reg_periksa.kd_poli)) as antrean_panggil,SUM(CASE WHEN reg_periksa.stts!='Sudah' THEN 1 ELSE 0 END) as sisa_antrean,
                                        ('Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih ..') as keterangan
                                        FROM reg_periksa INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli
                                        INNER JOIN maping_poliklinik_pcare ON maping_poliklinik_pcare.kd_poli_rs=reg_periksa.kd_poli
                                        WHERE reg_periksa.tgl_registrasi='".validTeks4($tanggaldaftar,20)."' AND maping_poliklinik_pcare.kd_poli_pcare='".validTeks4($kodepolipcare,20)."'"));

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
                        if((!empty($header['x-token'])) && (!empty($header['x-username'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
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
                                        SUM(CASE WHEN reg_periksa.stts ='Sudah' THEN 1 ELSE 0 END) as sudah_selesai,
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
        $hash_user = hash_pass($header['x-username'], 12);
        switch ($url[0]) {
            case "antrean":
                $konten = trim(file_get_contents("php://input"));
                $decode = json_decode($konten, true);
                
                if((!empty($header['x-token'])) && (!empty($header['x-username'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
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
                    }else if (empty($decode['nik'])) { 
                        $response = array(
                            'metadata' => array(
                                'message' => 'NIK Tidak Boleh Kosong ',
                                'code' => 201
                            )
                        ); 
                        http_response_code(201);
                    }else if (strlen($decode['nik']) <> 16) { 
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
                    }else if(date("Y-m-d")>$decode['tanggalperiksa']){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Tanggal Periksa Tidak Berlaku Mundur',
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else if(cekJadwal(validTeks4($decode['tanggalperiksa'],20),validTeks4($decode['kodepoli'],20))['data']=='false'){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Pendaftaran ke Poli Ini Sedang Tutup',
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else if(($decode['tanggalperiksa']==date("Y-m-d"))&&(strtotime(cekJadwal(validTeks4($decode['tanggalperiksa'],20),validTeks4($decode['kodepoli'],20))['jam_tutup'])<strtotime(date("H:i:s")))){
                        $jadwal=cekJadwal(validTeks4($decode['tanggalperiksa'],20),validTeks4($decode['kodepoli'],20));
                        $response = array(
                            'metadata' => array(
                                'message' => 'Pendaftaran Ke Poli '.$jadwal['namapoli'].' Sudah Tutup Jam '.$jadwal['jam_tutup'],
                                'code' => 201
                            )
                        );  
                        http_response_code(201);
                    }else {
                        if(empty(cekpasien(validTeks4($decode['nik'],20),validTeks4($decode['nomorkartu'],20)))){ 
                            $response = array(
                                'metadata' => array(
                                    'message' =>  "Data pasien ini tidak ditemukan, silahkan Melakukan Registrasi Pasien Baru Ke loket Administrasi Kami",
                                    'code' => 201
                                )
                            ); 
                            http_response_code(201);
                        }else{
                            $sudahdaftar=getOne2("select count(reg_periksa.no_rawat) from reg_periksa inner join maping_poliklinik_pcare on reg_periksa.kd_poli=maping_poliklinik_pcare.kd_poli_rs inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where maping_poliklinik_pcare.kd_poli_pcare='".validTeks4($decode['kodepoli'],20)."' and reg_periksa.tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' and pasien.no_peserta='".validTeks4($decode['nomorkartu'],20)."' ");
                            if($sudahdaftar>0){
                                $response = array(
                                    'metadata' => array(
                                        'message' =>  "Nomor Antrean Hanya Dapat Diambil 1 Kali Pada Tanggal dan Poli Yang Sama",
                                        'code' => 201
                                    )
                                ); 
                            }else{
                                $sekarang  = date("Y-m-d");
                                $interval  = getOne2("select (TO_DAYS('".validTeks4($decode['tanggalperiksa'],20)."')-TO_DAYS('$sekarang'))");
                                if($interval>1){
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Pendaftaran ke Poli Ini Sedang Tutup',
                                            'code' => 201
                                        )
                                    );  
                                    http_response_code(201);
                                }else{
                                    $hari      = hariindo(validTeks4($decode['tanggalperiksa'],20));
                                    $cek_kouta = fetch_array(bukaquery("SELECT sum(jadwal.kuota) - COALESCE((select COUNT(reg_periksa.tgl_registrasi) FROM reg_periksa 
                                            WHERE reg_periksa.tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' AND reg_periksa.kd_poli=jadwal.kd_poli )) as sisa_kouta,jadwal.kd_poli, 
                                            jadwal.jam_mulai + INTERVAL '10' MINUTE as jam_waktu, poliklinik.nm_poli,jadwal.kd_dokter,
                                            ('Datang 30 Menit sebelum pelayanan, Konfirmasi kehadiran dibagian pendaftaran dengan menunjukan bukti pendaftaran melalui Mobile JKN, Terima Kasih..') as keterangan
                                            FROM jadwal
                                            INNER JOIN maping_poliklinik_pcare ON maping_poliklinik_pcare.kd_poli_rs=jadwal.kd_poli
                                            INNER JOIN poliklinik ON poliklinik.kd_poli=jadwal.kd_poli
                                            WHERE jadwal.hari_kerja='$hari' AND  maping_poliklinik_pcare.kd_poli_pcare='".validTeks4($decode['kodepoli'],20)."'
                                            GROUP BY jadwal.kd_poli
                                            HAVING sisa_kouta > 0
                                            ORDER BY sisa_kouta DESC LIMIT 1"));
                                    if (!empty($cek_kouta['sisa_kouta']) and $cek_kouta['sisa_kouta'] > 0) {
                                        $datapeserta     = cekpasien(validTeks4($decode['nik'],20), validTeks4($decode['nomorkartu'],20));
                                        $noReg           = noRegPoli($cek_kouta['kd_poli'], validTeks4($decode['tanggalperiksa'],20));
                                        $max             = getOne2("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0)+1 from reg_periksa where tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."'");
                                        $no_rawat        = str_replace("-","/",$decode['tanggalperiksa']."/").sprintf("%06s", $max);
                                        $statuspoli      = getOne2("select if((select count(no_rkm_medis) from reg_periksa where no_rkm_medis='".$datapeserta["no_rkm_medis"]."' and kd_poli='".$cek_kouta["kd_poli"]."')>0,'Lama','Baru' )");
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
                                        $query = bukaquery("insert into reg_periksa values('$noReg', '$no_rawat', '".validTeks4($decode['tanggalperiksa'],20)."',current_time(), '$cek_kouta[kd_dokter]', '$datapeserta[no_rkm_medis]', '$cek_kouta[kd_poli]', '$datapeserta[namakeluarga]', '$datapeserta[alamatpj], $datapeserta[kelurahanpj], $datapeserta[kecamatanpj], $datapeserta[kabupatenpj], $datapeserta[propinsipj]', '$datapeserta[keluarga]', '".getOne("select registrasilama from poliklinik where kd_poli='$cek_kouta[kd_poli]'")."', 'Belum','Lama','Ralan', '".CARABAYAR."', '$umur','$sttsumur','Belum Bayar', '$statuspoli')");

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
                    $response = array(
                        'metadata' => array(
                            'message' => 'Nama User / Password / Token ada yang salah ..!!',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }
                break;
            case "pasienbaru":
                $konten = trim(file_get_contents("php://input"));
                $decode = json_decode($konten, true);
                if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
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
        $hash_user = hash_pass($header['x-username'], 12);
        switch ($url[0]) {
            case "antrean":
                if (!empty($url[1]) and $url[1] == "batal") {
                    $konten = trim(file_get_contents("php://input"));
                    $decode = json_decode($konten, true);

                    if((!empty($header['x-token'])) && (!empty($header['x-username'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
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
                        }else {
                            $cek = fetch_array(bukaquery("select reg_periksa.no_rawat,reg_periksa.stts from reg_periksa inner join maping_poliklinik_pcare on reg_periksa.kd_poli=maping_poliklinik_pcare.kd_poli_rs inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where maping_poliklinik_pcare.kd_poli_pcare='".validTeks4($decode["kodepoli"],20)."' and reg_periksa.tgl_registrasi='".validTeks4($decode["tanggalperiksa"],20)."' and pasien.no_peserta='".validTeks4($decode["nomorkartu"],20)."'"));
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
        echo '          "keterangan": "Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih .."'."\n";
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
        echo "5. Membatalkan antrian, methode PUT\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktp/antrean/batal \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, TanggalRegistrasi gunakan format yyyy-mm-dd\n";
        echo "   x-username:user yang diberikan RS yang diberikan RS\n";
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
        echo "6. Pasien Baru, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/pasienbaru \n";
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
