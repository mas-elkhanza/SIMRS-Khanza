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
        if (!empty($url[0])) {
            $referensiid = validTeks3($url[0],20);
            if (!preg_match("/^[0-9]{16}$/",$referensiid)){ 
                $response = array(
                    'reff_id' => $referensiid,
                    'reff_num' => '',
                    'response' => '-1',
                    'msg' => 'Format Referensi ID tidak sesuai..!!'
                );
                http_response_code(200);
            }else{
                $sql = "SELECT * FROM tagihan_bpd_jabar WHERE tagihan_bpd_jabar.keterangan = '$referensiid'";
                $result = bukaquery($sql);
                if (JumlahBaris($result) !== 0) {
                    if ($data = fetch_array($result)) {
                        if($data['status_bayar']=="Sudah"){
                            $response = array(
                                'reff_id' => $referensiid,
                                'reff_num' => $data['referensi'],
                                'response' => '-3',
                                'msg' => 'Referensi ID sudah terbayar'
                            );
                            http_response_code(200);
                        }else{
                            if(empty($url[1])){
                                if(getOne2("SELECT TIMESTAMPDIFF(MINUTE,tagihan_sadewa.tgl_bayar,now()) from tagihan_sadewa where tagihan_sadewa.no_nota='".$data['no_rawat']."'")>60){
                                    $response = array(
                                        'reff_id' => $referensiid,
                                        'reff_num' => '',
                                        'response' => '-5',
                                        'msg' => 'Referensi ID sudah Expired'
                                    );
                                    http_response_code(200);
                                }else{
                                    $response = array(
                                        'response' => '0',
                                        'msg' => 'Sukses',
                                        'reff_id' => $referensiid,
                                        'trx_id' => $data['no_nota'],
                                        'norm' => $data['no_rkm_medis'],
                                        'nama' => $data['nm_pasien'],
                                        'carabayar' => 'Umum/Pribadi',
                                        'total_biaya' => $data['besar_bayar'],
                                        'bayar' => $data['besar_bayar'],
                                        'tgl_id' => $data['tgl_closing']
                                    );
                                    http_response_code(200);
                                }
                            }else if (!empty($url[1])) {
                                if(getOne2("SELECT TIMESTAMPDIFF(MINUTE,tagihan_sadewa.tgl_bayar,now()) from tagihan_sadewa where tagihan_sadewa.no_nota='".$data['no_rawat']."'")>60){
                                    $response = array(
                                        'reff_id' => $referensiid,
                                        'reff_num' => '',
                                        'response' => '-5',
                                        'msg' => 'Referensi ID sudah Expired'
                                    );
                                    http_response_code(200);
                                }else{
                                    $referensinum = validTeks3($url[1],20);
                                    if (!preg_match("/^[0-9]{16}$/",$referensinum)){ 
                                        $response = array(
                                            'reff_id' => $referensiid,
                                            'reff_num' => $referensinum,
                                            'response' => '-1',
                                            'msg' => 'Format Referensi Number tidak sesuai..!!'
                                        );
                                        http_response_code(200);
                                    }else{
                                        $sql = "UPDATE tagihan_bpd_jabar SET status_bayar='Sudah',diupdatebank=current_time(),referensi='$referensinum' WHERE keterangan = '$referensiid'";
                                        if(bukaquery($sql)){
                                            $response = array(
                                                'reff_id' => $referensiid,
                                                'reff_num' => $referensinum,
                                                'response' => '0',
                                                'msg' => 'Sukses'
                                            );
                                            http_response_code(200);
                                        } else{
                                            $response = array(
                                                'reff_id' => $referensiid,
                                                'reff_num' => $referensinum,
                                                'response' => '-1',
                                                'msg' => 'Gagal melakukan update referensi..!!'
                                            );
                                            http_response_code(200);
                                        }
                                    }
                                }
                            }   
                        }
                    } else{
                        $response = array(
                            'reff_id' => $referensiid,
                            'reff_num' => '',
                            'response' => '-4',
                            'msg' => 'Link Down'
                        );
                        http_response_code(200);
                    }  
                } else {
                    $response = array(
                        'reff_id' => $referensiid,
                        'reff_num' => '',
                        'response' => '-2',
                        'msg' => 'Data tidak ditemukan (Referensi ID tidak terdaftar)'
                    );
                    http_response_code(200);
                }
            }
        }
    }else{
        $response = array(
            'response' => '-1',
            'msg' => 'Methode tidak tersedia..!!'
        );
        http_response_code(200);
    }
    
    if (!empty($response)) {
        echo json_encode($response);
    } else {
        $instansi=fetch_assoc(bukaquery("select nama_instansi from setting"));
        echo "Selamat Datang di Web Service BJB ".$instansi['nama_instansi']." ".date('Y');
        echo "\n\n";
        echo "Cara Menggunakan Web Service BJB : \n";
        echo "1. Melihat referensi, methode GET \n";
        echo "   gunakan URL http://ipserverws:port/bjb/referensi_id \n";
        echo "   referensi_id 16 karakter angka";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '       "response": 0,'."\n";
        echo '       "msg": "Sukses",'."\n";
        echo '       "reff_id": "1231011312220001",'."\n";
        echo '       "trx_id": "20221121RJ0001",'."\n";
        echo '       "norm": "000002",'."\n";
        echo '       "nama": "DEWI EKAWATI",'."\n";
        echo '       "carabayar": "Umum/Pribadi",'."\n";
        echo '       "total_biaya": "325000",'."\n";
        echo '       "bayar": "325000",'."\n";
        echo '       "tgl_id": "2022-11-21"'."\n";
        echo '   }'."\n";
        echo "2. Megupdate pembayaran, methode GET\n";
        echo "   gunakan URL http://ipserverws:port/bjb/referensi_id/referensi_number \n";
        echo "   referensi_id 16 karakter angka, referensi_number 15 karakter angka";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "reff_id": "1231011312220001",'."\n";
        echo '      "reff_num": "000000000000001",'."\n";
        echo '      "response": 0,'."\n";
        echo '      "msg": "Sukses"'."\n";
        echo '   }'."\n\n";
    }
?>
