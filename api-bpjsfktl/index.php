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
    $waktutunggu=5;
    
    if(!empty($url[0])){
        if($method == 'GET'){
            if ((!empty($header['x-username'])) && (!empty($header['x-password']))) {
                $hash_user = hash_pass($header['x-username'], 12);
                $hash_pass = hash_pass($header['x-password'], 12);
                if($url[0]=="auth"){
                    $response=createtoken($header['x-username'],$header['x-password']);
                }else{
                    $response = array(
                        'metadata' => array(
                            'message' => 'Service tidak terdaftar',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }
            }else{
                $response = array(
                    'metadata' => array(
                        'message' => 'Username dan Password wajib diisi..!!',
                        'code' => 201
                    )
                );
                http_response_code(201);
            }
        }else if ($method == 'POST') {
            if ((!empty($header['x-username'])) && (!empty($header['x-token']))) {
                $hash_user = hash_pass($header['x-username'], 12);
                if(!(USERNAME==$header['x-username'])){
                    $response = array(
                        'metadata' => array(
                            'message' => 'Username salah..!!',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }else{
                    if(!(cektoken($header['x-token'])=='true')){
                        $response = array(
                            'metadata' => array(
                                'message' => 'Token salah/expired..!!',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }else{
                        switch ($url[0]) {
                            case "statusantrean":
                                $konten = trim(file_get_contents("php://input"));
                                $decode = json_decode($konten, true);
                                if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
                                    if(empty($decode['kodepoli'])) { 
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
                                    }else if(empty($decode['tanggalperiksa'])) { 
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
                                                'message' => 'Tanggal Periksa tidak berlaku',
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
                                    }else{
                                        $jammulai   = validTeks4(substr($decode['jampraktek'],0,5),20);
                                        $jamselesai = validTeks4(substr($decode['jampraktek'],6,5),20);
                                        $kddokter   = getOne2("SELECT maping_dokter_dpjpvclaim.kd_dokter FROM maping_dokter_dpjpvclaim WHERE maping_dokter_dpjpvclaim.kd_dokter_bpjs='".validTeks4($decode['kodedokter'],20)."'");
                                        $hari       = strtoupper(hariindo(validTeks4($decode['tanggalperiksa'],20)));
                                        //single poli
                                        //$kdpoli     = getOne2("SELECT kd_poli_rs FROM maping_poli_bpjs WHERE kd_poli_bpjs='$decode[kodepoli]'");
                                        //double poli
                                        $kdpoli     = getOne2("SELECT maping_poli_bpjs.kd_poli_rs FROM maping_poli_bpjs inner join jadwal on maping_poli_bpjs.kd_poli_rs=jadwal.kd_poli WHERE maping_poli_bpjs.kd_poli_bpjs='".validTeks4($decode['kodepoli'],20)."' and jadwal.kd_dokter='$kddokter' and jadwal.hari_kerja='$hari' and jadwal.jam_mulai='$jammulai:00' and jadwal.jam_selesai='$jamselesai:00' ");
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
                                            $kuota      = getOne2("select jadwal.kuota from jadwal where jadwal.hari_kerja='$hari' and jadwal.kd_dokter='$kddokter' and jadwal.kd_poli='$kdpoli' and jadwal.jam_mulai='$jammulai:00' and jadwal.jam_selesai='$jamselesai:00'");

                                            if(empty($kuota)) {
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Pendaftaran ke Poli ini tidak tersedia',
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else{
                                                $data = fetch_array(bukaquery2("SELECT poliklinik.nm_poli,COUNT(reg_periksa.kd_poli) as total_antrean,dokter.nm_dokter,
                                                    IFNULL(SUM(CASE WHEN reg_periksa.stts ='Belum' THEN 1 ELSE 0 END),0) as sisa_antrean,
                                                    ('Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih ..') as keterangan
                                                    FROM reg_periksa INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli INNER JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter
                                                    WHERE reg_periksa.tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' AND reg_periksa.kd_poli='$kdpoli' and reg_periksa.kd_dokter='$kddokter'"));

                                                if ($data['sisa_antrean'] >=0) {
                                                    $response = array(
                                                        'response' => array(
                                                            'namapoli' => $data['nm_poli'],
                                                            'namadokter' => $data['nm_dokter'],
                                                            'totalantrean' => intval($data['total_antrean']),
                                                            'sisaantrean' => intval(validangka($data['sisa_antrean'])>=0?($data['sisa_antrean']):0),
                                                            'antreanpanggil' =>$kdpoli."-".getOne2("select reg_periksa.no_reg from reg_periksa where reg_periksa.stts='Belum' and reg_periksa.kd_dokter='$kddokter' and reg_periksa.kd_poli='$kdpoli' and reg_periksa.tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' order by CONVERT(RIGHT(reg_periksa.no_reg,3),signed) limit 1 "),
                                                            'sisakuotajkn' => intval($kuota-$data['total_antrean']),
                                                            'kuotajkn' => intval($kuota),
                                                            'sisakuotanonjkn' => intval($kuota-$data['total_antrean']),
                                                            'kuotanonjkn' => intval($kuota),
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
                                                            'message' => 'Maaf belum ada antrian ditanggal ' . FormatTgl(("d-m-Y"),$decode['tanggalperiksa']),
                                                             'code' => 201
                                                        )
                                                    );
                                                    http_response_code(201);
                                                }
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
                                break;
                            case "ambilantrean":
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
                                    }else if(empty($decode['jeniskunjungan'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Jenis Kunjungan tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(strpos($decode['jeniskunjungan'],"'")||strpos($decode['jeniskunjungan'],"\\")){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Jenis Kunjungan tidak ditemukan',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(!preg_match("/^[0-9]{1}$/",$decode['jeniskunjungan'])){ 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Jenis Kunjungan tidak sesuai',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(!(($decode['jeniskunjungan']=="1")||($decode['jeniskunjungan']=="2")||($decode['jeniskunjungan']=="3")||($decode['jeniskunjungan']=="4"))){ 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Jenis Kunjungan tidak ditemukan',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(empty($decode['nomorreferensi'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Nomor Referensi tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(strpos($decode['nomorreferensi'],"'")||strpos($decode['nomorreferensi'],"\\")){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Nomor Referensi tidak sesuai format',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(getOne2("select count(nomorreferensi) from referensi_mobilejkn_bpjs where (status='Belum' or status='Checkin') and nomorreferensi='".validTeks4($decode["nomorreferensi"],30)."'")>0){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Anda sudah terdaftar dalam antrian menggunakan nomor referensi yang sama',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else {
                                        $jammulai   = validTeks4(substr($decode['jampraktek'],0,5),20);
                                        $jamselesai = validTeks4(substr($decode['jampraktek'],6,5),20);
                                        $kddokter   = getOne2("SELECT kd_dokter FROM maping_dokter_dpjpvclaim WHERE kd_dokter_bpjs='".validTeks4($decode['kodedokter'],20)."'");
                                        $hari       = strtoupper(hariindo(validTeks4($decode['tanggalperiksa'],20)));
                                        //single poli
                                        //$kdpoli     = getOne2("SELECT kd_poli_rs FROM maping_poli_bpjs WHERE kd_poli_bpjs='".validTeks($decode["kodepoli"])."'");
                                        //double poli
                                        $kdpoli     = getOne2("SELECT maping_poli_bpjs.kd_poli_rs FROM maping_poli_bpjs inner join jadwal on maping_poli_bpjs.kd_poli_rs=jadwal.kd_poli WHERE maping_poli_bpjs.kd_poli_bpjs='".validTeks4($decode['kodepoli'],20)."' and jadwal.kd_dokter='$kddokter' and jadwal.hari_kerja='$hari' and jadwal.jam_mulai='$jammulai:00' and jadwal.jam_selesai='$jamselesai:00' ");
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
                                                        $sudahdaftar=getOne2("select count(reg_periksa.no_rawat) from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.kd_poli='$kdpoli' and reg_periksa.kd_dokter='$kddokter' and reg_periksa.tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' and pasien.no_peserta='".validTeks4($decode['nomorkartu'],20)."' ");
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
                                                                $sisakuota=getOne2("select count(no_rawat) from reg_periksa where kd_poli='$kdpoli' and kd_dokter='$kddokter' and tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."' ");
                                                                if ($sisakuota < $jadwal['kuota']) {
                                                                    $datapeserta     = cekpasien(validTeks4($decode['nik'],20),validTeks4($decode['nomorkartu'],20));
                                                                    $noReg           = noRegPoli($kdpoli,$kddokter,validTeks4($decode['tanggalperiksa'],20));
                                                                    $max             = getOne2("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0)+1 from reg_periksa where tgl_registrasi='".validTeks4($decode['tanggalperiksa'],20)."'");
                                                                    $no_rawat        = str_replace("-","/",validTeks4($decode['tanggalperiksa'],20)."/").sprintf("%06s", $max);
                                                                    $maxbooking      = getOne2("select ifnull(MAX(CONVERT(RIGHT(nobooking,6),signed)),0)+1 from referensi_mobilejkn_bpjs where tanggalperiksa='".validTeks4($decode['tanggalperiksa'],20)."'");
                                                                    $nobooking       = str_replace("-","",validTeks4($decode['tanggalperiksa'],20)."").sprintf("%06s", $maxbooking);
                                                                    $statuspoli      = getOne2("select if((select count(no_rkm_medis) from reg_periksa where no_rkm_medis='$datapeserta[no_rkm_medis]' and kd_poli='$kdpoli')>0,'Lama','Baru' )");
                                                                    $dilayani        = $noReg*$waktutunggu;
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

                                                                    $jeniskunjungan = "1 (Rujukan FKTP)";
                                                                    $kunjungan      = validTeks4($decode['jeniskunjungan'],20);
                                                                    if($kunjungan=="1"){
                                                                        $jeniskunjungan = "1 (Rujukan FKTP)";
                                                                    }else if($kunjungan=="2"){
                                                                        $jeniskunjungan = "2 (Rujukan Internal)";
                                                                    }else if($kunjungan=="3"){
                                                                        $jeniskunjungan = "3 (Kontrol)";
                                                                    }else if($kunjungan=="4"){
                                                                        $jeniskunjungan = "4 (Rujukan Antar RS)";
                                                                    }

                                                                    $querybooking = bukaquery2("insert into referensi_mobilejkn_bpjs values('$nobooking','$no_rawat', '".validTeks4($decode['nomorkartu'],20)."', '".validTeks4($decode['nik'],20)."','".validTeks4($decode['nohp'],20)."','".validTeks4($decode['kodepoli'],20)."','$statusdaftar','$datapeserta[no_rkm_medis]','".validTeks4($decode['tanggalperiksa'],20)."','".validTeks4($decode['kodedokter'],20)."','".validTeks4($decode['jampraktek'],20)."','".$jeniskunjungan."','".validTeks4($decode['nomorreferensi'],30)."','".$kdpoli."-".$noReg."','$noReg','".(strtotime(validTeks4($decode['tanggalperiksa'],20).' '.$jadwal['jam_mulai'].'+'.$dilayani.' minute')* 1000)."','".($jadwal['kuota']-$sisakuota-1)."','$jadwal[kuota]','".($jadwal['kuota']-$sisakuota-1)."','$jadwal[kuota]','Belum','0000-00-00 00:00:00','Belum')");
                                                                    if ($querybooking) {
                                                                        $query = bukaquery2("insert into reg_periksa values('$noReg', '$no_rawat', '".validTeks4($decode['tanggalperiksa'],20)."','".$jadwal['jam_mulai']."', '$kddokter', '$datapeserta[no_rkm_medis]', '$kdpoli', '$datapeserta[namakeluarga]', '$datapeserta[alamatpj], $datapeserta[kelurahanpj], $datapeserta[kecamatanpj], $datapeserta[kabupatenpj], $datapeserta[propinsipj]', '$datapeserta[keluarga]', '".getOne2("select registrasilama from poliklinik where kd_poli='$kdpoli'")."', 'Belum','".str_replace("0","Lama",str_replace("1","Baru",$statusdaftar))."','Ralan', '".CARABAYAR."', '$umur','$sttsumur','Belum Bayar', '$statuspoli')");
                                                                        if ($query) {
                                                                            $response = array(
                                                                                'response' => array(
                                                                                    'nomorantrean' => $kdpoli."-".$noReg,
                                                                                    'angkaantrean' => intval($noReg),
                                                                                    'kodebooking'=> $nobooking,
                                                                                    'pasienbaru'=>0,
                                                                                    'norm'=> $datapeserta['no_rkm_medis'],
                                                                                    'namapoli' => $jadwal['nm_poli'],
                                                                                    'namadokter' => $jadwal['nm_dokter'],
                                                                                    'estimasidilayani' => strtotime($decode['tanggalperiksa']." ".$jadwal['jam_mulai'].'+'.$dilayani.' minute')* 1000,
                                                                                    'sisakuotajkn'=>intval($jadwal['kuota']-$sisakuota-1),
                                                                                    'kuotajkn'=> intval($jadwal['kuota']),
                                                                                    'sisakuotanonjkn'=>intval($jadwal['kuota']-$sisakuota-1),
                                                                                    'kuotanonjkn'=> intval($jadwal['kuota']),
                                                                                    'keterangan'=> 'Peserta harap 30 menit lebih awal guna pencatatan administrasi.'
                                                                                ),
                                                                                'metadata' => array(
                                                                                    'message' => 'Ok',
                                                                                    'code' => 200
                                                                                )
                                                                            );

                                                                            http_response_code(200);
                                                                        } else {
                                                                            $update=bukaquery2("update referensi_mobilejkn_bpjs set status='Gagal',validasi=now() where nobooking='$nobooking'");
                                                                            $response = array(
                                                                                'metadata' => array(
                                                                                    'message' => "Maaf terjadi kesalahan, hubungi Admnistrator..",
                                                                                    'code' => 401
                                                                                )
                                                                            );
                                                                            http_response_code(401);
                                                                        }  
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
                            case "checkinantrean":
                                $konten = trim(file_get_contents("php://input"));
                                $decode = json_decode($konten, true);
                                if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
                                    @$tanggal=date("Y-m-d", ($decode['waktu']/1000));
                                    @$tanggalchekcin=date("Y-m-d H:i:s", ($decode['waktu']/1000));
                                    if(empty($decode['kodebooking'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Kode Booking tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(strpos($decode['kodebooking'],"'")||strpos($decode['kodebooking'],"\\")){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Kode Booking salah',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }elseif(empty($decode['waktu'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Waktu tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$tanggal)){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Tanggal Checkin tidak sesuai, format yang benar adalah yyyy-mm-dd',
                                                'code' => 201
                                            )
                                        );  
                                        http_response_code(201);
                                    }else if(date("Y-m-d")>$tanggal){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Waktu Checkin tidak berlaku mundur',
                                                'code' => 201
                                            )
                                        );  
                                        http_response_code(201);
                                    }else{
                                        $booking = fetch_array(bukaquery2("select referensi_mobilejkn_bpjs.nobooking,referensi_mobilejkn_bpjs.tanggalperiksa,referensi_mobilejkn_bpjs.status,referensi_mobilejkn_bpjs.validasi,left(referensi_mobilejkn_bpjs.jampraktek,5) as jampraktek,referensi_mobilejkn_bpjs.no_rawat from referensi_mobilejkn_bpjs where referensi_mobilejkn_bpjs.nobooking='".validTeks4($decode['kodebooking'],25)."'"));
                                        if(empty($booking['status'])) {
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Booking tidak ditemukan',
                                                    'code' => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            if($booking['status']=='Batal'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Booking Anda Sudah Dibatalkan pada tanggal '.$booking['validasi'],
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else if($booking['status']=='Checkin'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Anda Sudah Checkin pada tanggal '.$booking['validasi'],
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else if($booking['status']=='Belum'){
                                                $interval  = getOne2("select TIMESTAMPDIFF(MINUTE,'$booking[tanggalperiksa] $booking[jampraktek]:00','$tanggalchekcin') AS difference");
                                                if($interval>=60){
                                                    $response = array(
                                                        'metadata' => array(
                                                            'message' => 'Chekin Anda sudah expired. Silahkan konfirmasi ke loket pendaftaran',
                                                            'code' => 201
                                                        )
                                                    );  
                                                    http_response_code(201);
                                                }else if($interval<=-60){
                                                    $response = array(
                                                        'metadata' => array(
                                                            'message' => 'Chekin Anda masih harus menunggu lagi. Silahkan konfirmasi ke loket pendaftaran',
                                                            'code' => 201
                                                        )
                                                    );  
                                                    http_response_code(201);
                                                }else{
                                                    $update=bukaquery2("update referensi_mobilejkn_bpjs set status='Checkin',validasi=now() where nobooking='".validTeks4($decode['kodebooking'],25)."'");
                                                    if($update){
                                                        bukaquery2("update reg_periksa set jam_reg=current_time() where no_rawat='".$booking['no_rawat']."'");
                                                        $response = array(
                                                            'metadata' => array(
                                                                'message' => 'Ok',
                                                                'code' => 200
                                                            )
                                                        );
                                                        http_response_code(200);
                                                    }else{
                                                        $response = array(
                                                            'metadata' => array(
                                                                'message' => "Maaf terjadi kesalahan, hubungi Admnistrator..",
                                                                'code' => 401
                                                            )
                                                        );
                                                        http_response_code(401);
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
                            case "batalantrean":
                                $konten = trim(file_get_contents("php://input"));
                                $decode = json_decode($konten, true);
                                if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
                                    if(empty($decode['kodebooking'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Kode Booking tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(strpos($decode['kodebooking'],"'")||strpos($decode['kodebooking'],"\\")){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Kode Booking salah',
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
                                                'message' => 'Format Keterangan salah',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else{
                                        $booking = fetch_array(bukaquery2("select referensi_mobilejkn_bpjs.nobooking,referensi_mobilejkn_bpjs.no_rawat,referensi_mobilejkn_bpjs.tanggalperiksa,referensi_mobilejkn_bpjs.status,referensi_mobilejkn_bpjs.validasi,referensi_mobilejkn_bpjs.nomorreferensi,referensi_mobilejkn_bpjs.norm from referensi_mobilejkn_bpjs where referensi_mobilejkn_bpjs.nobooking='".validTeks4($decode['kodebooking'],25)."'"));
                                        if(empty($booking['status'])) {
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Booking tidak ditemukan',
                                                    'code' => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            if($booking['status']=='Batal'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Booking Anda Sudah Dibatalkan pada tanggal '.$booking['validasi'],
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else if(date("Y-m-d")>$booking['tanggalperiksa']){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Pembatalan Antrean tidak berlaku mundur',
                                                        'code' => 201
                                                    )
                                                );  
                                                http_response_code(201);
                                            }else if($booking['status']=='Checkin'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Anda Sudah Checkin Pada Tanggal '.$booking['validasi'].', Pendaftaran Tidak Bisa Dibatalkan',
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else if($booking['status']=='Belum'){
                                                $update        = bukaquery2("update referensi_mobilejkn_bpjs set status='Batal',validasi=now() where nobooking='".validTeks4($decode['kodebooking'],25)."'");
                                                $batal         = bukaquery2("delete from reg_periksa where no_rawat='".$booking['no_rawat']."'");
                                                if($batal){
                                                    $response = array(
                                                        'metadata' => array(
                                                            'message' => 'Ok',
                                                            'code' => 200
                                                        )
                                                    );
                                                    bukaquery2("insert into referensi_mobilejkn_bpjs_batal values('$booking[norm]','$booking[no_rawat]','$booking[nomorreferensi]',now(),'".validTeks4($decode['keterangan'],50)."','Belum','$booking[nobooking]')");
                                                    http_response_code(200);
                                                }else{
                                                    $response = array(
                                                        'metadata' => array(
                                                            'message' => "Maaf Terjadi Kesalahan, Hubungi Admnistrator..",
                                                            'code' => 201
                                                        )
                                                    );
                                                    http_response_code(201);
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
                            case "sisaantrean":
                                $konten = trim(file_get_contents("php://input"));
                                $decode = json_decode($konten, true);
                                if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
                                    if(empty($decode['kodebooking'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Kode Booking tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(strpos($decode['kodebooking'],"'")||strpos($decode['kodebooking'],"\\")){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Kode Booking salah',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else{
                                        $booking = fetch_array(bukaquery2("select referensi_mobilejkn_bpjs.nobooking,referensi_mobilejkn_bpjs.no_rawat,referensi_mobilejkn_bpjs.tanggalperiksa,referensi_mobilejkn_bpjs.status,referensi_mobilejkn_bpjs.validasi,referensi_mobilejkn_bpjs.nomorreferensi,referensi_mobilejkn_bpjs.kodedokter,referensi_mobilejkn_bpjs.kodepoli,referensi_mobilejkn_bpjs.jampraktek from referensi_mobilejkn_bpjs where referensi_mobilejkn_bpjs.nobooking='".validTeks4($decode['kodebooking'],25)."'"));
                                        if(empty($booking['status'])) {
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Booking tidak ditemukan',
                                                    'code' => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            if($booking['status']=='Batal'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Data booking sudah dibatalkan',
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else {
                                                /*single poli
                                                $kodedokter = getOne2("select kd_dokter from maping_dokter_dpjpvclaim where kd_dokter_bpjs='$booking[kodedokter]'");
                                                $kodepoli   = getOne2("select kd_poli_rs from maping_poli_bpjs where kd_poli_bpjs='$booking[kodepoli]'");
                                                $noreg      = getOne2("select no_reg from reg_periksa where no_rawat='$booking[no_rawat]'");
                                                $data = fetch_array(bukaquery("SELECT reg_periksa.kd_poli,poliklinik.nm_poli,dokter.nm_dokter,
                                                    reg_periksa.no_reg,COUNT(reg_periksa.no_rawat) as total_antrean,
                                                    IFNULL(SUM(CASE WHEN reg_periksa.stts ='Belum' THEN 1 ELSE 0 END),0) as sisa_antrean
                                                    FROM reg_periksa INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli
                                                    INNER JOIN dokter ON dokter.kd_dokter=reg_periksa.kd_dokter
                                                    WHERE reg_periksa.kd_dokter='$kodedokter' and reg_periksa.kd_poli='$kodepoli'and reg_periksa.tgl_registrasi='$booking[tanggalperiksa]' 
                                                    and CONVERT(RIGHT(reg_periksa.no_reg,3),signed)<CONVERT(RIGHT($noreg,3),signed)"));
                                                */
                                                //double poli
                                                $jammulai   = substr($booking['jampraktek'],0,5);
                                                $jamselesai = substr($booking['jampraktek'],6,5);
                                                $hari       = strtoupper(hariindo($booking['tanggalperiksa']));
                                                $kodedokter = getOne2("select kd_dokter from maping_dokter_dpjpvclaim where kd_dokter_bpjs='$booking[kodedokter]'");
                                                $kodepoli   = getOne2("SELECT maping_poli_bpjs.kd_poli_rs FROM maping_poli_bpjs inner join jadwal on maping_poli_bpjs.kd_poli_rs=jadwal.kd_poli WHERE maping_poli_bpjs.kd_poli_bpjs='$booking[kodepoli]' and jadwal.kd_dokter='$kodedokter' and jadwal.hari_kerja='$hari' and jadwal.jam_mulai='$jammulai:00' and jadwal.jam_selesai='$jamselesai:00' ");
                                                $noreg      = getOne2("select no_reg from reg_periksa where no_rawat='$booking[no_rawat]'");
                                                $data = fetch_array(bukaquery("SELECT reg_periksa.kd_poli,poliklinik.nm_poli,dokter.nm_dokter,
                                                    reg_periksa.no_reg,COUNT(reg_periksa.no_rawat) as total_antrean,
                                                    IFNULL(SUM(CASE WHEN reg_periksa.stts ='Belum' THEN 1 ELSE 0 END),0) as sisa_antrean
                                                    FROM reg_periksa INNER JOIN poliklinik ON poliklinik.kd_poli=reg_periksa.kd_poli
                                                    INNER JOIN dokter ON dokter.kd_dokter=reg_periksa.kd_dokter
                                                    WHERE reg_periksa.kd_dokter='$kodedokter' and reg_periksa.kd_poli='$kodepoli'and reg_periksa.tgl_registrasi='$booking[tanggalperiksa]' 
                                                    and CONVERT(RIGHT(reg_periksa.no_reg,3),signed)<CONVERT(RIGHT($noreg,3),signed)"));
                                                
                                                if ($data['nm_poli'] != '') {
                                                    $response = array(
                                                        'response' => array(
                                                            'nomorantrean' => $data['kd_poli']."-".$noreg,
                                                            'namapoli' => $data['nm_poli'],
                                                            'namadokter' => $data['nm_dokter'],
                                                            'sisaantrean' => intval(validangka($data['sisa_antrean'])>=0?($data['sisa_antrean']):0),
                                                            'antreanpanggil' => $data['kd_poli']."-".getOne2("select reg_periksa.no_reg from reg_periksa where reg_periksa.stts='Belum' and reg_periksa.kd_dokter='$kodedokter' and reg_periksa.kd_poli='$kodepoli' and reg_periksa.tgl_registrasi='$booking[tanggalperiksa]' and CONVERT(RIGHT(reg_periksa.no_reg,3),signed)<=CONVERT(RIGHT($noreg,3),signed) order by CONVERT(RIGHT(reg_periksa.no_reg,3),signed) limit 1 "),
                                                            'waktutunggu' => (($data['sisa_antrean']*$waktutunggu)*1000),
                                                            'keterangan' => "Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih ..Datanglah Minimal 30 Menit, jika no antrian anda terlewat, silakan konfirmasi ke bagian Pendaftaran atau Perawat Poli, Terima Kasih .."
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
                            case "jadwaloperasirs":
                                $konten = trim(file_get_contents("php://input"));
                                $decode = json_decode($konten, true);
                                if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
                                    if(empty($decode['tanggalawal'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Tanggal Awal tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggalawal'])){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Tanggal Awal tidak sesuai, format yang benar adalah yyyy-mm-dd',
                                                'code' => 201
                                            )
                                        );  
                                        http_response_code(201);
                                    }else if(date("Y-m-d")>$decode['tanggalawal']){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Tanggal Awal tidak berlaku mundur',
                                                'code' => 201
                                            )
                                        );  
                                        http_response_code(201);
                                    }else if(empty($decode['tanggalakhir'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Tanggal Akhir tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['tanggalakhir'])){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Tanggal Akhir tidak sesuai, format yang benar adalah yyyy-mm-dd',
                                                'code' => 201
                                            )
                                        );  
                                        http_response_code(201);
                                    }else if(date("Y-m-d")>$decode['tanggalakhir']){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Tanggal Akhir tidak berlaku mundur',
                                                'code' => 201
                                            )
                                        );  
                                        http_response_code(201);
                                    }else if ($decode['tanggalawal'] > $decode['tanggalakhir']) {
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format tanggal awal harus lebih kecil dari tanggal akhir',
                                                'code' => 201
                                            )
                                        );  
                                        http_response_code(201);
                                    }else{
                                        $queryoperasirs = bukaquery2("SELECT booking_operasi.no_rawat,booking_operasi.tanggal,paket_operasi.nm_perawatan,maping_poli_bpjs.kd_poli_bpjs,maping_poli_bpjs.nm_poli_bpjs,booking_operasi.status,pasien.no_peserta 
                                                            FROM booking_operasi INNER JOIN reg_periksa ON booking_operasi.no_rawat = reg_periksa.no_rawat INNER JOIN pasien ON pasien.no_rkm_medis = reg_periksa.no_rkm_medis INNER JOIN paket_operasi ON booking_operasi.kode_paket = paket_operasi.kode_paket 
                                                            INNER JOIN maping_poli_bpjs ON maping_poli_bpjs.kd_poli_rs=reg_periksa.kd_poli WHERE booking_operasi.tanggal BETWEEN '".validTeks4($decode['tanggalawal'],20)."' AND '".validTeks4($decode['tanggalakhir'],20)."' ORDER BY booking_operasi.tanggal,booking_operasi.jam_mulai");
                                        if(num_rows($queryoperasirs)>0) {
                                            while($rsqueryoperasirs = mysqli_fetch_array($queryoperasirs)) {
                                                $status=0;
                                                if($rsqueryoperasirs['status'] == 'Menunggu') {
                                                    $status = 0;
                                                } else {
                                                    $status = 1;
                                                }
                                                $data_array[] = array(
                                                    'kodebooking' => $rsqueryoperasirs['no_rawat'],
                                                    'tanggaloperasi' => $rsqueryoperasirs['tanggal'],
                                                    'jenistindakan' => $rsqueryoperasirs['nm_perawatan'],
                                                    'kodepoli' => $rsqueryoperasirs['kd_poli_bpjs'],
                                                    'namapoli' => $rsqueryoperasirs['nm_poli_bpjs'],
                                                    'terlaksana' => $status,
                                                    'nopeserta' => $rsqueryoperasirs['no_peserta'],
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
                                            http_response_code(200);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Maaf tidak ada Jadwal Operasi pada tanggal tersebut',
                                                    'code' => 201
                                                )
                                            );
                                            http_response_code(201);
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
                            case "jadwaloperasipasien":
                                $konten = trim(file_get_contents("php://input"));
                                $decode = json_decode($konten, true);
                                if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
                                    if (empty($decode['nopeserta'])){ 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Nomor Peserta tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if (mb_strlen($decode['nopeserta'], 'UTF-8') <> 13){ 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Nomor Peserta harus 13 digit',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if (!preg_match("/^[0-9]{13}$/",$decode['nopeserta'])){ 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Nomor Peserta tidak sesuai',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else{
                                        $queryoperasipasien = bukaquery2("SELECT booking_operasi.no_rawat,booking_operasi.tanggal,paket_operasi.nm_perawatan,maping_poli_bpjs.kd_poli_bpjs,maping_poli_bpjs.nm_poli_bpjs,booking_operasi.status,pasien.no_peserta 
                                                            FROM booking_operasi INNER JOIN reg_periksa ON booking_operasi.no_rawat = reg_periksa.no_rawat INNER JOIN pasien ON pasien.no_rkm_medis = reg_periksa.no_rkm_medis INNER JOIN paket_operasi ON booking_operasi.kode_paket = paket_operasi.kode_paket 
                                                            INNER JOIN maping_poli_bpjs ON maping_poli_bpjs.kd_poli_rs=reg_periksa.kd_poli WHERE pasien.no_peserta='".validTeks4($decode['nopeserta'],20)."' ORDER BY booking_operasi.tanggal,booking_operasi.jam_mulai");
                                        if(num_rows($queryoperasipasien)>0) {
                                            while($rsqueryoperasipasien = mysqli_fetch_array($queryoperasipasien)) {
                                                $status=0;
                                                if($rsqueryoperasipasien['status'] == 'Menunggu') {
                                                    $status = 0;
                                                } else {
                                                    $status = 1;
                                                }
                                                $data_array[] = array(
                                                    'kodebooking' => $rsqueryoperasipasien['no_rawat'],
                                                    'tanggaloperasi' => $rsqueryoperasipasien['tanggal'],
                                                    'jenistindakan' => $rsqueryoperasipasien['nm_perawatan'],
                                                    'kodepoli' => $rsqueryoperasipasien['kd_poli_bpjs'],
                                                    'namapoli' => $rsqueryoperasipasien['nm_poli_bpjs'],
                                                    'terlaksana' => $status
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
                                            http_response_code(200);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Maaf anda tidak memiliki jadwal operasi',
                                                    'code' => 201
                                                )
                                            );
                                            http_response_code(201);
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
                                    }else if(empty($decode['nohp'])) { 
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

                                            $query = bukaquery2("insert into pasien values('$norm','".validTeks4($decode['nama'],60)."','".validTeks4($decode['nik'],20)."','".validTeks4($decode['jeniskelamin'],20)."','-','".validTeks4($decode['tanggallahir'],20)."','-','".validTeks4($decode['alamat'],100)."','-','-','JOMBLO','-',current_date(),'".validTeks4($decode['nohp'],20)."','0','-','SAUDARA','-','".CARABAYAR."','".validTeks4($decode['nomorkartu'],20)."','".getOne2("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel='".validTeks4($decode['namakel'],30)."'")."','".getOne2("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec='".validTeks4($decode['namakec'],30)."'")."','".getOne2("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab='".validTeks4($decode['namadati2'],30)."'")."','-','".validTeks4($decode['alamat'],100)."','".validTeks4($decode['namakel'],30)."','".validTeks4($decode['namakec'],30)."','".validTeks4($decode['namadati2'],30)."','-','1','1','1','-','-','".getOne2("select propinsi.kd_prop from propinsi where propinsi.nm_prop='".validTeks4($decode['namaprop'],30)."'")."','".validTeks4($decode['namaprop'],30)."')");
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
                           case "ambilantreanfarmasi":
                                $konten = trim(file_get_contents("php://input"));
                                $decode = json_decode($konten, true);
                                if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
                                    if(empty($decode['kodebooking'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Kode Booking tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(strpos($decode['kodebooking'],"'")||strpos($decode['kodebooking'],"\\")){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Kode Booking salah',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else{
                                        $booking = fetch_array(bukaquery2("select referensi_mobilejkn_bpjs.nobooking,referensi_mobilejkn_bpjs.no_rawat,referensi_mobilejkn_bpjs.status,referensi_mobilejkn_bpjs.validasi from referensi_mobilejkn_bpjs where referensi_mobilejkn_bpjs.nobooking='".validTeks4($decode['kodebooking'],25)."'"));
                                        if(empty($booking['status'])) {
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Booking tidak ditemukan',
                                                    'code' => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            if($booking['status']=='Batal'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Booking Anda Sudah Dibatalkan pada tanggal '.$booking['validasi'],
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else if($booking['status']=='Gagal'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'No.Booking Anda Bermasalah, Hubungi Admnistrator..',
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else if($booking['status']=='Belum'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Anda Belum Melakukan Checkin',
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else{
                                                $resep = fetch_array(bukaquery2("select resep_obat.no_resep,CONVERT(RIGHT(resep_obat.no_resep,4),signed) as urut from resep_obat where resep_obat.no_rawat='".$booking['no_rawat']."'"));
                                                if(empty($resep['no_resep'])) {
                                                    $response = array(
                                                        'metadata' => array(
                                                            'message' => 'Anda tidak memiliki resep dari dokter yang anda tuju, silahkan konfirmasi petugas poli',
                                                            'code' => 201
                                                        )
                                                    );
                                                    http_response_code(201);
                                                }else{
                                                    $response = array(
                                                        'response' => array(
                                                            'jenisresep' => (getOne("select count(resep_dokter_racikan.no_resep) from resep_dokter_racikan where resep_dokter_racikan.no_resep='".$resep['no_resep']."'")>0?"Racikan":"Non Racikan"),
                                                            'nomorantrean' => intval($resep['urut']),
                                                            'keterangan' => "Resep dibuat secara elektronik di poli"
                                                        ),
                                                        'metadata' => array(
                                                            'message' => 'Ok',
                                                            'code' => 200
                                                        )
                                                    );
                                                    http_response_code(200);
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
                           case "statusantreanfarmasi":
                                $konten = trim(file_get_contents("php://input"));
                                $decode = json_decode($konten, true);
                                if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
                                    if(empty($decode['kodebooking'])) { 
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Kode Booking tidak boleh kosong',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else if(strpos($decode['kodebooking'],"'")||strpos($decode['kodebooking'],"\\")){
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Format Kode Booking salah',
                                                'code' => 201
                                            )
                                        );
                                        http_response_code(201);
                                    }else{
                                        $booking = fetch_array(bukaquery2("select referensi_mobilejkn_bpjs.nobooking,referensi_mobilejkn_bpjs.no_rawat,referensi_mobilejkn_bpjs.status,referensi_mobilejkn_bpjs.validasi from referensi_mobilejkn_bpjs where referensi_mobilejkn_bpjs.nobooking='".validTeks4($decode['kodebooking'],25)."'"));
                                        if(empty($booking['status'])) {
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Booking tidak ditemukan',
                                                    'code' => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            if($booking['status']=='Batal'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Booking Anda Sudah Dibatalkan pada tanggal '.$booking['validasi'],
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else if($booking['status']=='Gagal'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'No.Booking Anda Bermasalah, Hubungi Admnistrator..',
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else if($booking['status']=='Belum'){
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Anda Belum Melakukan Checkin',
                                                        'code' => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            }else{
                                                $resep = fetch_array(bukaquery2("select resep_obat.no_resep,resep_obat.tgl_peresepan,left(resep_obat.no_resep,8) as marking from resep_obat where resep_obat.no_rawat='".$booking['no_rawat']."'"));
                                                if(empty($resep['no_resep'])) {
                                                    $response = array(
                                                        'metadata' => array(
                                                            'message' => 'Anda tidak memiliki resep dari dokter yang anda tuju, silahkan konfirmasi petugas poli',
                                                            'code' => 201
                                                        )
                                                    );
                                                    http_response_code(201);
                                                }else{
                                                    $response = array(
                                                        'response' => array(
                                                            'jenisresep' => (getOne("select count(resep_dokter_racikan.no_resep) from resep_dokter_racikan where resep_dokter_racikan.no_resep='".$resep['no_resep']."'")>0?"Racikan":"Non Racikan"),
                                                            'totalantrean' => intval(getOne("select count(resep_obat.no_resep) from resep_obat where resep_obat.tgl_peresepan='".$resep['tgl_peresepan']."'")),
                                                            'sisaantrean' => intval(getOne("select count(resep_obat.no_resep) from resep_obat where resep_obat.tgl_perawatan='0000-00-00' and resep_obat.tgl_peresepan='".$resep['tgl_peresepan']."'")),
                                                            'antreanpanggil' => intval(getOne("select ifnull(CONVERT(RIGHT(antriapotek2.no_resep,4),signed),0) from antriapotek2 where left(antriapotek2.no_resep,8)='".$resep['marking']."'")),
                                                            'keterangan' => ""
                                                        ),
                                                        'metadata' => array(
                                                            'message' => 'Ok',
                                                            'code' => 200
                                                        )
                                                    );
                                                    http_response_code(200);
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
                        }
                    }
                }
            }else{
                $response = array(
                    'metadata' => array(
                        'message' => 'Username dan Token wajib diisi..!!',
                        'code' => 201
                    )
                );
                http_response_code(201);
            }
        }else{
            $response = array(
                'metadata' => array(
                    'message' => 'Methode tersebut tidak bisa digunakan...!!!',
                    'code' => 201
                )
            );
            http_response_code(201);
        }
        
        if (!empty($response)) {
            echo json_encode($response);
        } else {
            tampil();
        }
    }else{
        tampil();
    }
    
    function tampil(){
        $instansi=fetch_assoc(bukaquery2("select nama_instansi from setting"));
        echo "Selamat Datang di Web Service Antrean BPJS Mobile JKN FKTL ".$instansi['nama_instansi']." ".date('Y');
        echo "\n\n";
        echo "Cara Menggunakan Web Service Antrean BPJS Mobile JKN FKTL : \n";
        echo "1. Mengambil Token, methode GET \n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/auth \n";
        echo "   Header gunakan x-username:user yang diberikan RS, x-password:pass yang diberikan RS\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '         "token": "xxxxxxxxxxxxxxxxx"'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '         "message": "Ok",'."\n";
        echo '         "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "2. Menampilkan status atrean poli, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/statusantrean \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "kodepoli":"XXX",'."\n";
	echo '      "kodedokter":"XXXXX",'."\n";
	echo '      "tanggalperiksa":"XXXX-XX-XX",'."\n";
	echo '      "jampraktek":"XX:XX-XX:XX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "namapoli": "XXXXXXXXXXXXXX",'."\n";
        echo '          "namadokter": "XXXXXXXXXXXXXX",'."\n";
        echo '          "totalantrean": "X",'."\n";
        echo '          "sisaantrean": "X",'."\n";
        echo '          "antreanpanggil": "X-XX",'."\n";
        echo '          "sisakuotajkn": "XX",'."\n";
        echo '          "kuotajkn": "XX",'."\n";
        echo '          "sisakuotanonjkn": "XX",'."\n";
        echo '          "kuotanonjkn": "XX",'."\n";
        echo '          "keterangan": "XXXXXXXXXXXXXX"'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "3. Mengambil atrean poli, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/ambilantrean \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "nomorkartu": "XXXXXXXXXXXXXX",'."\n";
        echo '      "nik": "XXXXXXXXXXXXXXXXX",'."\n";
        echo '      "nohp": "XXXXXXXX",'."\n";
        echo '      "kodepoli": "XXX",'."\n";
        echo '      "norm": "XXXXX",'."\n";
        echo '      "tanggalperiksa": "XXXX-XX-XX",'."\n";
        echo '      "kodedokter": "XXXXX",'."\n";
        echo '      "jampraktek": "XX:XX-XX:XX",'."\n";
        echo '      "jeniskunjungan": "x",'."\n";
        echo '      "nomorreferensi": "XXXXXXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "nomorantrean": "X-XXX",'."\n";
        echo '          "angkaantrean": "XXX",'."\n";
        echo '          "kodebooking": "XXXXXXXXXXXXX",'."\n";
        echo '          "pasienbaru": X,'."\n";
        echo '          "norm": "XXXXXX",'."\n";
        echo '          "namapoli": "XXXXXXXXXXXXXXX",'."\n";
        echo '          "namadokter": "XXXXXXXXXXXXXXX",'."\n";
        echo '          "estimasidilayani": XXXXXXX,'."\n";
        echo '          "sisakuotajkn": "XX",'."\n";
        echo '          "kuotajkn": "XX",'."\n";
        echo '          "sisakuotanonjkn": "XXX",'."\n";
        echo '          "kuotanonjkn": "XXX",'."\n";
        echo '          "keterangan": "XXXXXXXXXXXXXXXX"'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "4. Melakukan checkin poli, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/checkinantrean \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "kodebooking": "XXXXXXXXXXXXXX",'."\n";
        echo '      "waktu": XXXXXXXXXXX(timestamp milliseconds)'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "5. Membatalkan antrean poli dan hanya bisa dilakukan sebelum pasien checkin, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/batalantrean \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "kodebooking": "XXXXXXXXXXXXXX",'."\n";
        echo '      "keterangan": "XXXXXXXXXXXXXXXXXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "6. Melihat sisa antrean poli dan hanya bisa dilakukan setelah pasien checkin, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/sisaantrean \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "kodebooking": "XXXXXXXXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "nomorantrean": "XXXX",'."\n";
        echo '          "namapoli": "XXXXXXXXXXXX",'."\n";
        echo '          "namadokter": "XXXXXXXXXXX",'."\n";
        echo '          "sisaantrean": XX,'."\n";
        echo '          "antreanpanggil": "XXXX",'."\n";
        echo '          "waktutunggu": XXXX,'."\n";
        echo '          "keterangan": "XXXXX"'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "7. Melihat Jadwal Operasi RS, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/jadwaloperasirs \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "tanggalawal": "XXXX-XX-XX",'."\n";
        echo '      "tanggalakhir": "XXXX-XX-XX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "list": ['."\n";
        echo '              {'."\n";
        echo '                  "kodebooking": "XXXXXXXXX",'."\n";
        echo '                  "tanggaloperasi": "XXXX-XX-XX",'."\n";
        echo '                  "jenistindakan": "XXXXXXXXXXXXXXXXX",'."\n";
        echo '                  "kodepoli": "XXX",'."\n";
        echo '                  "namapoli": "XXXXXXXXXXXXX",'."\n";
        echo '                  "terlaksana": X,'."\n";
        echo '                  "nopeserta": "XXXXXXXXXX",'."\n";
        echo '                  "lastupdate": XXXXXXXX'."\n";
        echo '              },'."\n";
        echo '           ]'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "8. Melihat Jadwal Operasi Pasien, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/jadwaloperasipasien \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "nopeserta": "XXXXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "list": ['."\n";
        echo '              {'."\n";
        echo '                  "kodebooking": "XXXXXXXXX",'."\n";
        echo '                  "tanggaloperasi": "XXXX-XX-XX",'."\n";
        echo '                  "jenistindakan": "XXXXXXXXXXXXXXXXX",'."\n";
        echo '                  "kodepoli": "XXX",'."\n";
        echo '                  "namapoli": "XXXXXXXXXXXXX",'."\n";
        echo '                  "terlaksana": X'."\n";
        echo '              },'."\n";
        echo '           ]'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "9. Pasien Baru, methode POST\n";
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
        echo '      "nohp": "XXXXXXXXXXXX",'."\n";
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
        echo "10. Ambil Antrean Farmasi, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/ambilantreanfarmasi \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "kodebooking": "XXXXXXXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "jenisresep": "Racikan/Non Racikan",'."\n";
        echo '          "nomorantrean": X,'."\n";
        echo '          "keterangan": "XXXXXXXXXXXXXX",'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "11. Status Antrean Farmasi, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bpjsfktl/statusantreanfarmasi \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "kodebooking": "XXXXXXXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "jenisresep": "Racikan/Non Racikan",'."\n";
        echo '          "totalantrean": XXx,'."\n";
        echo '          "sisaantrean": xxx,'."\n";
        echo '          "antreanpanggil": xxx,'."\n";
        echo '          "keterangan": "XXXXXX",'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Ok",'."\n";
        echo '          "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
    }
?>
