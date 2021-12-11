<?php
    require_once ('conf.php');
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json");
    header("Access-Control-Allow-Methods: POST, GET");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    $url    = isset($_GET['url']) ? $_GET['url'] : '/';
    $url    = explode("/", $url);
    $header = apache_request_headers();
    $method = $_SERVER['REQUEST_METHOD'];
    $waktutunggu=10;
    
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
        echo "Selamat Datang di Web Service SIMKES Khanza ".$instansi['nama_instansi']." ".date('Y');
        echo "\n\n";
        echo "Cara Menggunakan Web Service Antrean BPJS Mobile JKN FKTL : \n";
        echo "1. Mengambil Token, methode GET \n";
        echo "   gunakan URL http://ipserverws:port/webapps/api-bpjsfktl/auth \n";
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
        echo "   gunakan URL http://ipserverws:port/webapps/api-bpjsfktl/statusantrean \n";
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
    }
?>
