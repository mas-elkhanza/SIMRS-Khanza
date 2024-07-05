<?php
    header("X-Robots-Tag: noindex", true);
    header("Content-Type: application/json");
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Methods: POST, GET");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    ini_set('display_errors', 0);
    error_reporting(E_ERROR | E_WARNING | E_PARSE);

    require_once ('conf.php');
    $method             = $_SERVER['REQUEST_METHOD'];
    $action             = isset($_GET["act"]) ? $_GET["act"] : null;
    $bpj                = fetch_assoc("SELECT AES_DECRYPT(usere,'nur') as username, AES_DECRYPT(passworde,'windi') as password FROM set_akun_bankjateng");
    $header             = json_encode(['typ' => 'JWT', 'alg' => 'HS256']);
    $payload            = json_encode(['username' => @$bpj['username'], 'password' => @$bpj['password'], 'date' => strtotime(date('Y-m-d')) * 1000]);
    $base64UrlHeader    = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($header));
    $base64UrlPayload   = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($payload));
    $signature          = hash_hmac('sha256', $base64UrlHeader . "." . $base64UrlPayload, 'abC123!', true);
    $base64UrlSignature = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($signature));
    $token              = $base64UrlHeader . "." . $base64UrlPayload . "." . $base64UrlSignature;

    if ($method == 'POST') {
        switch ((isset($action) ? $action : "")) {
            case "token":
                    $header 	= apache_request_headers();
                    $konten 	= trim(file_get_contents("php://input"));
                    $decode 	= json_decode($konten, true);
                    $response = array();
                    if ((validTeks4($header['X-User'],20)==$bpj['username']) && (validTeks4($header['X-Pass'],20)==$bpj['password'])) {
                        $response = array(
                            'response' => array(
                                'token' => $token
                            ),
                            'metadata' => array(
                                'message' => 'OK',
                                'code' => 200
                            )
                        );
                    }else{
                        $response = array(
                            'metadata' => array(
                                'message' => 'Access denied',
                                'code' => 401
                            )
                        );
                    }
                        
                    echo json_encode($response);
                break;
            case "tagihan":
                    $header 	= apache_request_headers();
                    $konten 	= trim(file_get_contents("php://input"));
                    $decode 	= json_decode($konten, true);
                    $response 	= array();
                    if ($header['X-Token'] == $token) {
                        $data 	= array();  
                        
                        if (!preg_match("/^[0-9]{6}$/",$decode['no_rkm_medis'])) {
                           $errors[] = 'No RM Harus 6 digit';
                        }
                        
                        if(!empty($errors)) {
                            foreach($errors as $error) {
                                $response = array(
                                    'metadata' => array(
                                        'message' => $error,
                                        'code' => '01'
                                    )
                                );
                            }
                        } else {
                            $result = bukaquery("SELECT count(*) FROM pasien WHERE no_rkm_medis = '".validTeks4($decode['no_rkm_medis'],20)."'");
                            if (JumlahBaris($result) !== 0) {
                                $sql2 = "SELECT * FROM tagihan_bpd_jateng WHERE no_rkm_medis = '".validTeks4($decode['no_rkm_medis'],20)."' AND status_bayar = 'Pending'";
                                $result2 = bukaquery($sql2);
                                if (JumlahBaris($result2) !== 0) {
                                    while ($data = fetch_array($result2)) {
                                        $data_array[] = array(
                                            'no_rkm_medis' => $data['no_rkm_medis'],
                                            'nm_pasien' => $data['nm_pasien'],
                                            'alamat' => $data['alamat'],
                                            'jk' => $data['jk'],
                                            'tgl_lahir' => $data['tgl_lahir'],
                                            'umurdaftar' => $data['umurdaftar'],
                                            'tgl_registrasi' => $data['tgl_registrasi'],
                                            'no_nota' => $data['no_nota'],
                                            'keterangan' => $data['keterangan'],
                                            'besar_bayar' => $data['besar_bayar']
                                        );
                                    }    
                                    $response = array(
                                        'response' => array(
                                            'list' => (
                                                $data_array
                                            )
                                        ),
                                        'metadata' => array(
                                            'message' => 'Data Ditemukan',
                                            'code' => '00'
                                        )
                                    );
                                } else {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Tagihan Sudah Dibayar',
                                            'code' => '02'
                                        )   
                                    );
                                }
                            } else {
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'No Rekam Medis Tidak Ditemukan.',
                                        'code' => '01'
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
        case "bayar":
                    $header = apache_request_headers();
                    $konten = trim(file_get_contents("php://input"));
                    $decode = json_decode($konten, true);
                    $response = array();
                    if ($header['X-Token'] == $token) {
                        $data = array();                
                        if (!preg_match("/^[0-9]{6}$/",$decode['no_rkm_medis'])) {
                            $errors[] = 'Nomor RM Harus 6 digit';
                        }
                        
                        if(empty($decode['no_nota'])) {
                            $errors[] = 'Nomor Nota Tidak Boleh Kosong';
                        }else{
                            if(strpos($decode['no_nota'],"'")||strpos($decode['no_nota'],"\\")){
                                $errors[] = 'Nomor Nota Tidak Sesuai Format';
                            }
                        }
                        
                        if(empty($decode['referensi'])) {
                            $errors[] = 'Nomor Referensi Tidak Boleh Kosong';
                        }else{
                            if(strpos($decode['referensi'],"'")||strpos($decode['referensi'],"\\")){
                                $errors[] = 'Nomor Referensi Tidak Sesuai Format';
                            }
                        }
                        
                        if(!empty($errors)) {
                            foreach($errors as $error) {
                                $response = array(
                                    'metadata' => array(
                                        'message' => $error,
                                        'code' => '01'
                                    )
                                );
                            }
                        } else{
                            $sql = "UPDATE tagihan_bpd_jateng SET status_bayar='Sudah',diupdatebank=current_time(),referensi='".validTeks4($decode['referensi'],40)."' WHERE no_rkm_medis = '".validTeks4($decode['no_rkm_medis'],20)."' AND no_nota = '".validTeks4($decode['no_nota'],20)."'";
                            if(bukaquery($sql)){
                                $response = array(
                                    'metadata' => array(
                                    'message' => 'Update Berhasil',
                                    'code' => '04'
                                    )
                                );
                            } else{
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Proses Gagal',
                                        'code' => '05'
                                    )
                                );
                            }
                        }
                    } else {
                        $response = array(
                            'metadata' => array(
                                'message' => 'Time Out',
                                'code' => '05'
                            )
                        );
                    }
                    echo json_encode($response);
            break;
        }
    }else{
        $instansi=fetch_assoc("select nama_instansi from setting");
        echo "Selamat Datang di Web Service Host to Host Bank Jateng ".$instansi['nama_instansi']." ".date('Y');
        echo "\n\n";
        echo "Cara Menggunakan Web Service Host to Host Bank Jateng : \n";
        echo "1. Mengambil Token, gunakan URL http://ipserverws:port/bankjateng/index.php?act=token \n";
        echo "   Header gunakan X-User:user yang diberikan RS, X-Pass:pass yang diberikan RS\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '         "token": "xxxxxxxxxxxxxxxxx'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '         "message": "OK",'."\n";
        echo '         "code": 200'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "2. Memeriksa tagihan pasien, gunakan URL http://ipserverws:port/bankjateng/index.php?act=tagihan \n";
        echo "   Header gunakan X-Token:token yang diambil sebelumnya\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "no_rkm_medis":"xxxxxxxxxx"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "list": ['."\n";
        echo '              {'."\n";
        echo '                  "no_rkm_medis": "XXXXXX",'."\n";
        echo '                  "nm_pasien": "XXXXXXXXXXXXXXXXXXX",'."\n";
        echo '                  "alamat": "XXXXXXXXXXXXXXX",'."\n";
        echo '                  "jk": "X",'."\n";
        echo '                  "tgl_lahir": "xxxx-xx-xx",'."\n";
        echo '                  "umurdaftar": "XXX",'."\n";
        echo '                  "tgl_registrasi": "xxxx-xx-xx",'."\n";
        echo '                  "no_nota": "XXXXXXXXXXX",'."\n";
        echo '                  "keterangan": "XXXXXXXXX",'."\n";
        echo '                  "besar_bayar": "XXXXXX"'."\n";
        echo '              }'."\n";
        echo '          ]'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Data Ditemukan",'."\n";
        echo '          "code": "00"'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
        echo "3. Update tagihan pasien, gunakan URL http://ipserverws:port/bankjateng/index.php?act=bayar \n";
        echo "   Header gunakan X-Token:token yang diambil sebelumnya\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
        echo '      "no_rkm_medis":"xxxxxx"'."\n";
        echo '      "no_nota":"xxxxxxxxx"'."\n";
        echo '      "referensi":"xxxxxxxxx"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "Update Berhasil",'."\n";
        echo '          "code": "04"'."\n";
        echo '      }'."\n";
        echo '   }'."\n\n";
    }
?>
