<?php
    require_once ('conf.php');
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json");
    header("Access-Control-Allow-Methods: POST, GET");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    $url     = isset($_GET['url']) ? $_GET['url'] : '/';
    $url     = explode("/", $url);
    $header  = getallheaders();
    $method = $_SERVER['REQUEST_METHOD'];
    
    if ($method == 'POST') {
        if (!empty($url[0])) {
            if (!empty($url[1])) {
                if(($url[0]=="oauth")&&($url[1]=="token")){
                    $konten = trim(file_get_contents("php://input"));
                    $decode = json_decode($konten, true);
                    if(empty($decode['grant_type'])) { 
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => 'Bad client credentials'
                        );
                        http_response_code(401);
                    }else if(strpos($decode['grant_type'],"'")||strpos($decode['grant_type'],"\\")){
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => 'Bad client credentials'
                        );
                        http_response_code(401);
                    }else if(empty($decode['username'])) { 
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => 'Bad client credentials'
                        );
                        http_response_code(401);
                    }else if(strpos($decode['username'],"'")||strpos($decode['username'],"\\")){
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => 'Bad client credentials'
                        );
                        http_response_code(401);
                    }else if(empty($decode['password'])) { 
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => '401'
                        );
                        http_response_code(401);
                    }else if(strpos($decode['password'],"'")||strpos($decode['password'],"\\")){
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => 'Bad client credentials'
                        );
                        http_response_code(401);
                    }else if(empty($decode['client_id'])) { 
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => 'Bad client credentials'
                        );
                        http_response_code(401);
                    }else if(strpos($decode['client_id'],"'")||strpos($decode['client_id'],"\\")){
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => 'Bad client credentials'
                        );
                        http_response_code(401);
                    }else if(empty($decode['client_secret'])) { 
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => 'Bad client credentials'
                        );
                        http_response_code(401);
                    }else if(strpos($decode['client_secret'],"'")||strpos($decode['client_secret'],"\\")){
                        $response = array(
                            'error' => 'invalid_client',
                            'error_description' => 'Bad client credentials'
                        );
                        http_response_code(401);
                    }else{
                        if(($decode['grant_type']=="password")&&($decode['username']==USERNAME)&&($decode['password']==PASSWORD)&&($decode['client_id']==CLIENTID)&&($decode['client_secret']==CLIENTSECRET)){
                            $data_array[] = array(
                                'name' => 'role_user',
                                'permissions' => []
                            );
                            $response = array(
                                'access_token' => createtoken(),
                                'token_type' => 'bearer',
                                'refresh_token' => createtoken(),
                                'expires_in' => 899,
                                'scope' => 'role_user',
                                'email' => 'mhas@mandiri.co.id',
                                'username' => USERNAME,
                                'role' => (
                                    $data_array
                                ),
                                'jti' => generate_uuid()
                            );
                            http_response_code(200);
                        }else{
                            $response = array(
                                'error' => 'invalid_client',
                                'error_description' => 'Bad client credentials'
                            );
                            http_response_code(401);
                        }
                    }
                }else if(($url[0]=="api")&&($url[1]=="v1")){
                    if (!empty($url[2])) {
                        if (!empty($url[3])) {
                            if(($url[2]=="penerimaan")&&($url[3]=="inquirypenerimaan")){
                                if ((!empty($header['Authorization'])) && (!empty($header['rsId']))) {
                                    $idrs = getOne("select set_akun_mandiri.kode_rs from set_akun_mandiri");
                                    if($header['rsId']==$idrs){
                                        if(cektoken(str_replace("bearer ","",$header['Authorization']))=="true"){
                                            $konten = trim(file_get_contents("php://input"));
                                            $decode = json_decode($konten, true);
                                            if(!empty($decode['regNo'])){ 
                                                if(!preg_match("/^[0-9]{14}$/",$decode['regNo'])){ 
                                                    $response = array(
                                                        'error' => 'invalid_parameter',
                                                        'error_description' => 'Error regNo'
                                                    );
                                                    http_response_code(401);
                                                }else{
                                                    $query = bukaquery2("select tagihan_mandiri.no_rkm_medis,tagihan_mandiri.nm_pasien,tagihan_mandiri.jk,tagihan_mandiri.tgl_lahir,tagihan_mandiri.tgl_registrasi,
                                                                         tagihan_mandiri.no_nota,tagihan_mandiri.besar_bayar,tagihan_mandiri.no_rawat,tagihan_mandiri.status_lanjut,tagihan_mandiri.tgl_closing,
                                                                         tagihan_mandiri.status_bayar,tagihan_mandiri.pembatalan,tagihan_mandiri.dibatalkan_oleh,tagihan_mandiri.besar_batal,tagihan_mandiri.no_id 
                                                                         from tagihan_mandiri where tagihan_mandiri.no_id='".validTeks3($decode['regNo'],14)."' and tagihan_mandiri.status_bayar='Pending'");
                                                    if(num_rows($query)>0) {
                                                        if($rsquery = mysqli_fetch_array($query)) {
                                                            $kodelokasi = "";
                                                            $namalokasi = "";
                                                            $kodedokter = "";
                                                            $namadokter = "";
                                                            if($rsquery["status_lanjut"]=="Ralan"){
                                                                $queryralan = bukaquery2("select reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.kd_poli,poliklinik.nm_poli from reg_periksa 
                                                                                        inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli 
                                                                                        where reg_periksa.no_rawat='".$rsquery["no_rawat"]."'");
                                                                if($rsqueryralan = mysqli_fetch_array($queryralan)) {
                                                                    $kodelokasi = $rsqueryralan["kd_poli"];
                                                                    $namalokasi = $rsqueryralan["nm_poli"];
                                                                    $kodedokter = $rsqueryralan["kd_dokter"];
                                                                    $namadokter = $rsqueryralan["nm_dokter"];
                                                                }
                                                            }else if($rsquery["status_lanjut"]=="Ranap"){
                                                                $queryranap = bukaquery2("select kamar_inap.kd_kamar,kamar.kelas,bangsal.kd_bangsal,bangsal.nm_bangsal from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar
                                                                                          inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat='".$rsquery["no_rawat"]."' order by kamar_inap.tgl_masuk desc limit 1");
                                                                if($rsqueryranap = mysqli_fetch_array($queryranap)) {
                                                                    $kodelokasi = $rsqueryranap["kd_bangsal"];
                                                                    $namalokasi = $rsqueryranap["nm_bangsal"];
                                                                    $kodedokter = $rsqueryranap["kd_kamar"];
                                                                    $namadokter = $rsqueryranap["kelas"];
                                                                }
                                                            }
                                                            $dataarray[] = array(
                                                                'billCode' => strval('1'),
                                                                'regNo' => $rsquery['no_id'],
                                                                'regDate' => $rsquery["tgl_registrasi"],
                                                                'noKuitansi' => '',
                                                                'componentId' => $kodelokasi,
                                                                'kodeUnitPoli' => $kodedokter,
                                                                'namaDokter' => ($rsquery["status_lanjut"]=="Ralan"?$namadokter:"DPJP Ranap"),
                                                                'trxNo' => $rsquery["no_nota"],
                                                                'jenisPelayananId' => ($rsquery["status_lanjut"]=="Ralan"?"1":"2"),
                                                                'paymentTp' => '2',
                                                                'paidFlag' => ($rsquery["status_bayar"]=="Sudah"?"1":"0"),
                                                                'cancelFlag' => ($rsquery["pembatalan"]=="Sudah Dibatalkan"?"1":"0"),
                                                                'isCancel' => ($rsquery["dibatalkan_oleh"]=="Faskes"?"1":"0"),
                                                                'paymentBill' => $rsquery["besar_bayar"],
                                                                'cancelNominal' => $rsquery["besar_batal"],
                                                                'additional1' => $namalokasi,
                                                                'additional2' => ($rsquery["status_lanjut"]=="Ralan"?"":$namadokter),
                                                                'additional3' => ''
                                                            ); 
                                                            $response = array(
                                                                'code' => 200,
                                                                'message' => 'success',
                                                                'inquiryResponse' => array(
                                                                    'rsId' => $idrs,
                                                                    'rmNo' => $rsquery["no_rkm_medis"],
                                                                    'pasienName' => $rsquery["nm_pasien"],
                                                                    'dob' => $rsquery["tgl_lahir"],
                                                                    'gender' => $rsquery["jk"],
                                                                    'golDarah' => '-',
                                                                    'timeStamp' => $rsquery["tgl_closing"].'.000',
                                                                    'status' => array(
                                                                        'inquryCode' => $decode['regNo'],
                                                                        'statusCode' => '1',
                                                                        'statusDescription' => 'Sukses'
                                                                    ),
                                                                    'billDetails' => array(
                                                                        'billDetail' => (
                                                                            $dataarray
                                                                        )
                                                                    )
                                                                )
                                                            );
                                                            http_response_code(200);
                                                        }
                                                    }else{
                                                        $response = array(
                                                            'code' => 200,
                                                            'message' => 'success',
                                                            'inquiryResponse' => array(
                                                                'rsId' => $idrs,
                                                                'rmNo' => '',
                                                                'pasienName' => '',
                                                                'dob' => '',
                                                                'gender' => '',
                                                                'golDarah' => '',
                                                                'timeStamp' => date('Y-m-d H:i:s'),
                                                                'status' => array(
                                                                    'inquryCode' => $decode['regNo'],
                                                                    'statusCode' => '2',
                                                                    'statusDescription' => 'Data Tidak Ditemukan'
                                                                ),
                                                                'billDetails' => null
                                                            )
                                                        );
                                                        http_response_code(200);
                                                    }
                                                }
                                            }else{
                                                if(empty($decode['rmNo'])){ 
                                                    $response = array(
                                                        'error' => 'invalid_parameter',
                                                        'error_description' => 'Error rmNo'
                                                    );
                                                    http_response_code(401);
                                                }else if(strpos($decode['rmNo'],"'")||strpos($decode['rmNo'],"\\")){
                                                    $response = array(
                                                        'error' => 'invalid_parameter',
                                                        'error_description' => 'Error rmNo'
                                                    );
                                                    http_response_code(401);
                                                }else if(empty($decode['startDate'])){ 
                                                    $response = array(
                                                        'error' => 'invalid_parameter',
                                                        'error_description' => 'Error startDate'
                                                    );
                                                    http_response_code(401);
                                                }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['startDate'])){
                                                    $response = array(
                                                        'error' => 'invalid_parameter',
                                                        'error_description' => 'Error startDate'
                                                    );
                                                    http_response_code(401);
                                                }else if(empty($decode['endDate'])){ 
                                                    $response = array(
                                                        'error' => 'invalid_parameter',
                                                        'error_description' => 'Error endDate'
                                                    );
                                                    http_response_code(401);
                                                }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['endDate'])){
                                                    $response = array(
                                                        'error' => 'invalid_parameter',
                                                        'error_description' => 'Error endDate'
                                                    );
                                                    http_response_code(401);
                                                }else{
                                                    $query = bukaquery2("select tagihan_mandiri.no_rkm_medis,tagihan_mandiri.nm_pasien,tagihan_mandiri.jk,tagihan_mandiri.tgl_lahir from tagihan_mandiri 
                                                                         where tagihan_mandiri.no_rkm_medis='".validTeks3($decode['rmNo'],14)."' and tagihan_mandiri.tgl_closing between  
                                                                         '".validTeks3($decode['startDate'],10)." 00:00:01' and '".validTeks3($decode['endDate'],10)." 23:59:59'");
                                                    if(num_rows($query)>0) {
                                                        if($rsquery = mysqli_fetch_array($query)) {
                                                            $nomor=1;
                                                            $querycari=bukaquery2("select tagihan_mandiri.tgl_registrasi,tagihan_mandiri.no_nota,tagihan_mandiri.besar_bayar,tagihan_mandiri.no_rawat,tagihan_mandiri.no_id,
                                                                                   tagihan_mandiri.status_lanjut,tagihan_mandiri.status_bayar,tagihan_mandiri.pembatalan,tagihan_mandiri.dibatalkan_oleh,
                                                                                   tagihan_mandiri.besar_batal from tagihan_mandiri where tagihan_mandiri.no_rkm_medis='".validTeks3($decode['rmNo'],14)."' and 
                                                                                   tagihan_mandiri.tgl_closing between '".validTeks3($decode['startDate'],10)." 00:00:01' and '".validTeks3($decode['endDate'],10)." 23:59:59'");
                                                            while($rsquerycari = mysqli_fetch_array($querycari)) {
                                                                $kodelokasi = "";
                                                                $namalokasi = "";
                                                                $kodedokter = "";
                                                                $namadokter = "";
                                                                if($rsquerycari["status_lanjut"]=="Ralan"){
                                                                    $queryralan = bukaquery2("select reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.kd_poli,poliklinik.nm_poli from reg_periksa 
                                                                                            inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli 
                                                                                            where reg_periksa.no_rawat='".$rsquerycari["no_rawat"]."'");
                                                                    if($rsqueryralan = mysqli_fetch_array($queryralan)) {
                                                                        $kodelokasi = $rsqueryralan["kd_poli"];
                                                                        $namalokasi = $rsqueryralan["nm_poli"];
                                                                        $kodedokter = $rsqueryralan["kd_dokter"];
                                                                        $namadokter = $rsqueryralan["nm_dokter"];
                                                                    }
                                                                }else if($rsquerycari["status_lanjut"]=="Ranap"){
                                                                    $queryranap = bukaquery2("select kamar_inap.kd_kamar,kamar.kelas,bangsal.kd_bangsal,bangsal.nm_bangsal from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar
                                                                                              inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat='".$rsquerycari["no_rawat"]."' order by kamar_inap.tgl_masuk desc limit 1");
                                                                    if($rsqueryranap = mysqli_fetch_array($queryranap)) {
                                                                        $kodelokasi = $rsqueryranap["kd_bangsal"];
                                                                        $namalokasi = $rsqueryranap["nm_bangsal"];
                                                                        $kodedokter = $rsqueryranap["kd_kamar"];
                                                                        $namadokter = $rsqueryranap["kelas"];
                                                                    }
                                                                }
                                                                $dataarray[] = array(
                                                                    'billCode' => strval($nomor),
                                                                    'regNo' => $rsquerycari['no_id'],
                                                                    'regDate' => $rsquerycari["tgl_registrasi"],
                                                                    'noKuitansi' => '',
                                                                    'componentId' => $kodelokasi,
                                                                    'kodeUnitPoli' => $kodedokter,
                                                                    'namaDokter' => ($rsquerycari["status_lanjut"]=="Ralan"?$namadokter:"DPJP Ranap"),
                                                                    'trxNo' => $rsquerycari["no_nota"],
                                                                    'jenisPelayananId' => ($rsquerycari["status_lanjut"]=="Ralan"?"1":"2"),
                                                                    'paymentTp' => '2',
                                                                    'paidFlag' => ($rsquerycari["status_bayar"]=="Sudah"?"1":"0"),
                                                                    'cancelFlag' => ($rsquerycari["pembatalan"]=="Sudah Dibatalkan"?"1":"0"),
                                                                    'isCancel' => ($rsquerycari["dibatalkan_oleh"]=="Faskes"?"1":"0"),
                                                                    'paymentBill' => $rsquerycari["besar_bayar"],
                                                                    'cancelNominal' => $rsquerycari["besar_batal"],
                                                                    'additional1' => $namalokasi,
                                                                    'additional2' => ($rsquerycari["status_lanjut"]=="Ralan"?"":$namadokter),
                                                                    'additional3' => ''
                                                                ); 
                                                                $nomor++;
                                                            }
                                                            $response = array(
                                                                'code' => 200,
                                                                'message' => 'success',
                                                                'inquiryResponse' => array(
                                                                    'rsId' => $idrs,
                                                                    'rmNo' => $rsquery["no_rkm_medis"],
                                                                    'pasienName' => $rsquery["nm_pasien"],
                                                                    'dob' => $rsquery["tgl_lahir"],
                                                                    'gender' => $rsquery["jk"],
                                                                    'golDarah' => '-',
                                                                    'timeStamp' => date('Y-m-d H:i:s').'.000',
                                                                    'status' => array(
                                                                        'inquryCode' => $rsquery["no_rkm_medis"],
                                                                        'statusCode' => '1',
                                                                        'statusDescription' => 'Sukses'
                                                                    ),
                                                                    'billDetails' => array(
                                                                        'billDetail' => (
                                                                            $dataarray
                                                                        )
                                                                    )
                                                                )
                                                            );
                                                            http_response_code(200);
                                                        }
                                                    }else{
                                                        $response = array(
                                                            'code' => 200,
                                                            'message' => 'success',
                                                            'inquiryResponse' => array(
                                                                'rsId' => $idrs,
                                                                'rmNo' => '',
                                                                'pasienName' => '',
                                                                'dob' => '',
                                                                'gender' => '',
                                                                'golDarah' => '',
                                                                'timeStamp' => date('Y-m-d H:i:s'),
                                                                'status' => array(
                                                                    'inquryCode' => $decode['regNo'],
                                                                    'statusCode' => '2',
                                                                    'statusDescription' => 'Data Tidak Ditemukan'
                                                                ),
                                                                'billDetails' => null
                                                            )
                                                        );
                                                        http_response_code(200);
                                                    }
                                                }
                                            }
                                        }else{
                                            $response = array(
                                                'error' => 'invalid_authorization',
                                                'error_description' => 'Error Authorization or rsId'
                                            );
                                            http_response_code(401);
                                        }
                                    }else{
                                        $response = array(
                                            'error' => 'invalid_authorization',
                                            'error_description' => 'Error Authorization or rsId'
                                        );
                                        http_response_code(401);
                                    }
                                }else{
                                    $response = array(
                                        'error' => 'invalid_authorization',
                                        'error_description' => 'Error Authorization or rsId'
                                    );
                                    http_response_code(401);
                                }
                            }else{
                                $response = array(
                                    'error' => 'invalid_url',
                                    'error_description' => 'URL not found'
                                );
                                http_response_code(401);
                            }
                        }else{
                            $response = array(
                                'error' => 'invalid_url',
                                'error_description' => 'URL not found'
                            );
                            http_response_code(401);
                        }
                    }else {
                        $response = array(
                            'error' => 'invalid_url',
                            'error_description' => 'URL not found'
                        );
                        http_response_code(401);
                    }
                }else{
                    $response = array(
                        'error' => 'invalid_url',
                        'error_description' => 'URL not found'
                    );
                    http_response_code(401);
                }
            }else{
                $response = array(
                    'error' => 'invalid_url',
                    'error_description' => 'URL not found'
                );
                http_response_code(401);
            }
        }
    }
    
    if (!empty($response)) {
        echo json_encode($response);
    } else {
        $instansi=fetch_assoc(bukaquery("select setting.nama_instansi from setting"));
        echo "Selamat Datang di Web Service H2h Bank Mandiri ".$instansi['nama_instansi']." ".date('Y');
        echo "\n\n";
        echo "Cara Menggunakan Web Service H2H Bank Mandiri : \n";
        echo "1. Mengambil token, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/mandiri/oauth/token \n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "grant_type":"XXXXX",'."\n";
	echo '      "username":"XXXXX",'."\n";
	echo '      "password":"XXXXX",'."\n";
	echo '      "client_id":"XXXXX",'."\n";
	echo '      "client_secret":"XXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "access_token": "XXXXX",'."\n";
        echo '      "token_type": "XXXXX",'."\n";
        echo '      "refresh_token": "XXXXX",'."\n";
        echo '      "expires_in": 000,'."\n";
        echo '      "scope": "XXXXX",'."\n";
        echo '      "email": "XXXXX@XXXX",'."\n";
        echo '      "username": "XXXXX",'."\n";
        echo '      "role": ['."\n";
        echo '          {'."\n";
        echo '              "name": "XXXXX",'."\n";
        echo '              "permissions": [],'."\n";
        echo '          }'."\n";
        echo '      ],'."\n";
        echo '      "jti": "XXXXX"'."\n";
        echo '   }'."\n\n";
        echo "2. Pencarian data dengan nomor registrasi/rawat/id, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/mandiri/penerimaan/inquirypenerimaan \n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "regNo": "xxxxxxxxx",'."\n";
        echo '      "rmNo": "",'."\n";
        echo '      "startDate": "",'."\n";
        echo '      "endDate": "",'."\n";
        echo '      "timeStamp": "0000-00-00 00:00:00.000"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "code": 200,'."\n";
        echo '      "message": "success",'."\n";
        echo '      "inquiryResponse": {'."\n";
        echo '          "rsId": "xxxxxxxxx",'."\n";
        echo '          "rmNo": "xxxxxxxxx",'."\n";
        echo '          "pasienName": "xxxxxxxxx",'."\n";
        echo '          "dob": "0000-00-00",'."\n";
        echo '          "gender": "x",'."\n";
        echo '          "golDarah": "x",'."\n";
        echo '          "timeStamp": "0000-00-00 00:00:00.000",'."\n";
        echo '          "status": {'."\n";
        echo '              "inquryCode": "xxxxxxxxx",'."\n";
        echo '              "statusCode": "0",'."\n";
        echo '              "statusDescription": "xxxxxxxxx"'."\n";
        echo '          },'."\n";
        echo '          "billDetails": {'."\n";
        echo '              "billDetail": ['."\n";
        echo '                  {'."\n";
        echo '                      "billCode": "0",'."\n";
        echo '                      "regNo": "xxxxxxxxx",'."\n";
        echo '                      "regDate": "0000-00-00",'."\n";
        echo '                      "noKuitansi": "",'."\n";
        echo '                      "componentId": "xxxxxxxxx",'."\n";
        echo '                      "kodeUnitPoli": "xxxxxxxxx",'."\n";
        echo '                      "namaDokter": "xxxxxxxxx",'."\n";
        echo '                      "trxNo": "0000000000000",'."\n";
        echo '                      "jenisPelayananId": "0",'."\n";
        echo '                      "paymentTp": "0",'."\n";
        echo '                      "paidFlag": "0",'."\n";
        echo '                      "cancelFlag": "0",'."\n";
        echo '                      "isCancel": "0",'."\n";
        echo '                      "paymentBill": "000000000",'."\n";
        echo '                      "cancelNominal": "0",'."\n";
        echo '                      "additional1": "xxxxxxxxx",'."\n";
        echo '                      "additional2": "xxxxxxxxx",'."\n";
        echo '                      "additional3": "xxxxxxxxx"'."\n";
        echo '                  }'."\n";
        echo '              ]'."\n";
        echo '          }'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "3. Pencarian data dengan nomor rekam medis, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/mandiri/penerimaan/inquirypenerimaan \n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "regNo": "",'."\n";
        echo '      "rmNo": "xxxxxxxxx",'."\n";
        echo '      "startDate": "0000-00-00",'."\n";
        echo '      "endDate": "0000-00-00",'."\n";
        echo '      "timeStamp": "0000-00-00 00:00:00.000"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "code": 200,'."\n";
        echo '      "message": "success",'."\n";
        echo '      "inquiryResponse": {'."\n";
        echo '          "rsId": "xxxxxxxxx",'."\n";
        echo '          "rmNo": "xxxxxxxxx",'."\n";
        echo '          "pasienName": "xxxxxxxxx",'."\n";
        echo '          "dob": "0000-00-00",'."\n";
        echo '          "gender": "x",'."\n";
        echo '          "golDarah": "x",'."\n";
        echo '          "timeStamp": "0000-00-00 00:00:00.000",'."\n";
        echo '          "status": {'."\n";
        echo '              "inquryCode": "xxxxxxxxx",'."\n";
        echo '              "statusCode": "0",'."\n";
        echo '              "statusDescription": "xxxxxxxxx"'."\n";
        echo '          },'."\n";
        echo '          "billDetails": {'."\n";
        echo '              "billDetail": ['."\n";
        echo '                  {'."\n";
        echo '                      "billCode": "0",'."\n";
        echo '                      "regNo": "xxxxxxxxx",'."\n";
        echo '                      "regDate": "0000-00-00",'."\n";
        echo '                      "noKuitansi": "",'."\n";
        echo '                      "componentId": "xxxxxxxxx",'."\n";
        echo '                      "kodeUnitPoli": "xxxxxxxxx",'."\n";
        echo '                      "namaDokter": "xxxxxxxxx",'."\n";
        echo '                      "trxNo": "0000000000000",'."\n";
        echo '                      "jenisPelayananId": "0",'."\n";
        echo '                      "paymentTp": "0",'."\n";
        echo '                      "paidFlag": "0",'."\n";
        echo '                      "cancelFlag": "0",'."\n";
        echo '                      "isCancel": "0",'."\n";
        echo '                      "paymentBill": "000000000",'."\n";
        echo '                      "cancelNominal": "0",'."\n";
        echo '                      "additional1": "xxxxxxxxx",'."\n";
        echo '                      "additional2": "xxxxxxxxx",'."\n";
        echo '                      "additional3": "xxxxxxxxx"'."\n";
        echo '                  }'."\n";
        echo '              ]'."\n";
        echo '          }'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
    }
?>
