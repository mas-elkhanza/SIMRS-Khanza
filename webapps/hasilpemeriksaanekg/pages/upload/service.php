<?php
    require_once('../../../conf/conf.php');
    header("X-Robots-Tag: noindex", true);
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json");
    header("Access-Control-Allow-Methods: POST, GET");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    $url     = isset($_GET['url']) ? $_GET['url'] : '/';
    $url     = explode("/", $url);
    $header  = apache_request_headers();
    $newhead = array();
    if ($header) foreach ($header as $idx => $val) {
        $newhead[strtolower($idx)] = $val;
    }
    $header = $newhead;
    $method = $_SERVER['REQUEST_METHOD'];

    if ($method == 'POST') {
        if ((!empty($header['username'])) && (!empty($header['password']))) {
            if (strpos($header['username'], "'") || strpos($header['username'], "\\")) {
                $response = array(
                    'metadata' => array(
                        'message' => 'Username salah..!!',
                        'code'    => 201
                    )
                );
                http_response_code(201);
            } else if (strpos($header['password'], "'") || strpos($header['password'], "\\")) {
                $response = array(
                    'metadata' => array(
                        'message' => 'Password salah..!!',
                        'code'    => 201
                    )
                );
                http_response_code(201);
            } else {
                if ((USERHYBRIDWEB == validTeks3($header['username'])) && (PASHYBRIDWEB == validTeks3($header['password']))) {
                    $konten = trim(file_get_contents("php://input"));
                    $decode = json_decode($konten, true);

                    if (empty($decode['file'])) {
                        $response = array(
                            'metadata' => array(
                                'message' => 'File tidak boleh kosong',
                                'code'    => 201
                            )
                        );
                        http_response_code(201);
                    } else if (empty($decode['namafile'])) {
                        $response = array(
                            'metadata' => array(
                                'message' => 'Nama File tidak boleh kosong',
                                'code'    => 201
                            )
                        );
                        http_response_code(201);
                    } else if (empty($decode['norawat'])) {
                        $response = array(
                            'metadata' => array(
                                'message' => 'No.Rawat tidak boleh kosong',
                                'code'    => 201
                            )
                        );
                        http_response_code(201);
                    } else {
                        $namafileAsli       = validTeks3(basename($decode['namafile']));
                        $namafileBersih     = preg_replace('/[^a-zA-Z0-9_\-\.]/', '', $namafileAsli);
                        $namafileTanpaExt   = pathinfo($namafileBersih, PATHINFO_FILENAME);
                        $namafileFinal      = $namafileTanpaExt.'.jpg';

                        if (empty($namafileTanpaExt)) {
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Nama File tidak valid',
                                    'code'    => 201
                                )
                            );
                            http_response_code(201);
                        } else {
                            $base64_string = $decode['file'];
                            if (strpos($base64_string, ',') !== false) {
                                $base64_string = explode(',', $base64_string)[1];
                            }

                            $fileData = base64_decode($base64_string, true);
                            if ($fileData === false) {
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format base64 tidak valid',
                                        'code'    => 201
                                    )
                                );
                                http_response_code(201);
                            } else {
                                $hex = bin2hex(substr($fileData, 0, 3));
                                if ($hex !== 'ffd8ff') {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'File bukan gambar JPG yang valid',
                                            'code'    => 201
                                        )
                                    );
                                    http_response_code(201);
                                } else {
                                    $norawat = validTeks3($decode['norawat']);
                                    try {
                                        if(Tambah3(" hasil_pemeriksaan_ekg_gambar "," '$norawat','pages/upload/$namafileFinal' ")){
                                            $hasil   = file_put_contents($namafileFinal, $fileData);
                                            if ($hasil === false) {
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'Gagal menyimpan file, periksa permission direktori',
                                                        'code'    => 201
                                                    )
                                                );
                                                http_response_code(201);
                                            } else {
                                                $response = array(
                                                    'metadata' => array(
                                                        'message' => 'File berhasil diupload',
                                                        'code'    => 200
                                                    )
                                                );
                                                http_response_code(200);
                                            }
                                        }
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data gambar sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan file',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    $response = array(
                        'metadata' => array(
                            'message' => 'Username & Password salah..!!',
                            'code'    => 201
                        )
                    );
                    http_response_code(201);
                }
            }
        } else {
            $response = array(
                'metadata' => array(
                    'message' => 'Username dan Password wajib diisi',
                    'code'    => 201
                )
            );
            http_response_code(201);
        }
    } else {
        tampil();
    }

    if (!empty($response)) {
        echo json_encode($response);
    } else {
        tampil();
    }

    function tampil(){
        echo json_encode(array(
            'metadata' => array(
                'message' => 'Selamat Datang di Service Upload Gambar Hasil EKG',
                'code'    => 200
            )
        ));
    }
?>