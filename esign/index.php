<?php
    header("X-Robots-Tag: noindex", true);
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
    
    if(!empty($url[0])){
        if ($method == 'POST') {
            if ((!empty($header['username'])) && (!empty($header['password'])) && (!empty($header['url']))) {
                if(strpos($header['username'],"'")||strpos($header['username'],"\\")){
                    $response = array(
                        'metadata' => array(
                            'message' => 'username salah..!!',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }else if(strpos($header['password'],"'")||strpos($header['password'],"\\")){
                    $response = array(
                        'metadata' => array(
                            'message' => 'password salah..!!',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }else if(strpos($header['url'],"'")||strpos($header['url'],"\\")){
                    $response = array(
                        'metadata' => array(
                            'message' => 'url salah..!!',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }else{
                    if($url[0]=="jadikanpdf"){
                        $konten = trim(file_get_contents("php://input"));
                        $decode = json_decode($konten, true);
                        if(empty($decode['file'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'file tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }elseif (empty($decode['nik'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'nik tidak boleh kosong ',
                                    'code' => 201
                                )
                            ); 
                            http_response_code(201);
                        }elseif (strlen($decode['nik']) <> 16) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'nik harus 16 digit ',
                                    'code' => 201
                                )
                            ); 
                            http_response_code(201);
                        }else if (!preg_match("/^[0-9]{16}$/",$decode['nik'])){ 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format nik tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($decode['passphrase'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'passphrase tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(strpos($decode['passphrase'],"'")||strpos($decode['passphrase'],"\\")){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format passphrase tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($decode['tampilan'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'tampilan tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(strpos($decode['tampilan'],"'")||strpos($decode['tampilan'],"\\")){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format tampilan tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(!(($decode['tampilan']=="visible")||($decode['tampilan']=="invisible"))){ 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format tampilan tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($decode['image'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'image tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(strpos($decode['image'],"'")||strpos($decode['image'],"\\")){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format image tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(!(($decode['image']=="true")||($decode['image']=="false"))){ 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format image tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($decode['linkQR'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'linkQR tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(strpos($decode['linkQR'],"'")||strpos($decode['linkQR'],"\\")){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format linkQR tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($decode['width'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'width tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(strpos($decode['width'],"'")||strpos($decode['width'],"\\")){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format width tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($decode['height'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'height tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(strpos($decode['height'],"'")||strpos($decode['height'],"\\")){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format height tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(empty($decode['tag_koordinat'])) { 
                            $response = array(
                                'metadata' => array(
                                    'message' => 'tag_koordinat tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(strpos($decode['tag_koordinat'],"'")||strpos($decode['tag_koordinat'],"\\")){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'format tag_koordinat tidak sesuai',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else{
                            $base64_string = $decode['file'];
                            if (strpos($base64_string, ',') !== false) {
                                $base64_string = explode(',', $base64_string)[1];
                            }

                            $fileData      = base64_decode($base64_string);
                            $filename      = 'uploaded_file.pdf';
                            $tmp_file      = tmpfile();
                            $tmp_file_path = stream_get_meta_data($tmp_file)['uri'];
                            file_put_contents($tmp_file_path, $fileData);
                            $_FILES['file'] = [
                                'name' => $filename,
                                'type' => "pdf",
                                'tmp_name' => $tmp_file_path,
                                'error' => UPLOAD_ERR_OK,
                                'size' => strlen($fileData),
                            ];
                            if ($_FILES['file']['error'] === UPLOAD_ERR_OK) {
                                $file          = $_FILES['file']['tmp_name'];
                                $nik           = validTeks($decode['nik']);
                                $passphrase    = validTeks($decode['passphrase']);
                                $tampilan      = validTeks($decode['tampilan']);
                                $image         = validTeks($decode['image']);
                                $linkQR        = validTeks($decode['linkQR']);
                                $width         = validTeks($decode['width']);
                                $height        = validTeks($decode['height']);
                                $tag_koordinat = validTeks($decode['tag_koordinat']);
                                $username      = validTeks($header['username']);
                                $password      = validTeks($header['password']);
                                $url           = validTeks($header['url']);
                                $data = array(
                                    'file' => curl_file_create($file, 'application/pdf', $_FILES['file']['name']),
                                    'nik' => $nik,
                                    'passphrase' => $passphrase,
                                    'tampilan' => $tampilan,
                                    'image' => $image,
                                    'linkQR' => $linkQR,
                                    'width' => $width,
                                    'height' => $height,
                                    'tag_koordinat' => $tag_koordinat
                                );

                                $ch = curl_init();
                                curl_setopt($ch, CURLOPT_URL, $url);
                                curl_setopt($ch, CURLOPT_POST, true);
                                curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
                                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                                curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
                                curl_setopt($ch, CURLOPT_USERPWD, "$username:$password");

                                $response = curl_exec($ch);
                                $http_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);

                                if ($http_code == 200) {
                                    $base64Content = base64_encode($response);
                                    $response = array(
                                        'response' => $base64Content,
                                        'metadata' => array(
                                            'message' => 'Dokumen berhasil ditandatangani',
                                            'code' => 200
                                        )
                                    );
                                    http_response_code(200);
                                } else {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => htmlspecialchars($response),
                                            'code' => $http_code
                                        )
                                    );
                                    http_response_code($http_code);
                                }

                                curl_close($ch);
                            }else{
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'gagal mengenali file ..!!',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }
                        }
                    }else{
                        $response = array(
                            'metadata' => array(
                                'message' => 'Service tidak ditemukan ..!!',
                                'code' => 201
                            )
                        );
                        http_response_code(201);
                    }
                }
            }else{
                $response = array(
                    'metadata' => array(
                        'message' => 'username, password & url wajib diisi..!!',
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
        echo "Selamat Datang di Web Service E-Sign";
        echo "\n\n";
        echo "Cara Menggunakan Web Service Antrean BPJS Mobile JKN FKTL : \n";
        echo "Malakukan proses tanda tangan digital status atrean poli, methode POST\n";
        echo "gunakan URL http://ipserverws:port/esign/jadikanpdf \n";
        echo "Header gunakan username : username dari esign, password : password dari esign, url : url dari esign";
        echo "Body berisi : \n";
        echo '{'."\n";
	echo '    "file":"base64file",'."\n";
	echo '    "nik":"XXXXXXXXXXX",'."\n";
	echo '    "passphrase":"XXXXXXXXXXX",'."\n";
	echo '    "tampilan":"visible/invisible",'."\n";
	echo '    "image":"true/false",'."\n";
	echo '    "linkQR":"XXXXXXXXXXX",'."\n";
	echo '    "width":"xx",'."\n";
	echo '    "height":"xx",'."\n";
	echo '    "tag_koordinat":"x"'."\n";
        echo '}'."\n\n";
        echo "Hasilnya : \n";
        echo '{'."\n";
        echo '   "response": base64file,'."\n";
        echo '   "metadata": {'."\n";
        echo '       "message": "Dokumen berhasil ditandatangani...",'."\n";
        echo '       "code": 200'."\n";
        echo '   }'."\n";
        echo '}'."\n\n";
    }
    
    function validTeks($data){
        $save=str_replace("'","",$data);
        $save=str_replace("\\","",$save);
        $save=str_replace(";","",$save);
        $save=str_replace("`","",$save);
        $save=str_replace("--","",$save);
        $save=str_replace("/*","",$save);
        $save=str_replace("*/","",$save);
        $save=str_replace("text/html","",$save);
        $save=str_replace("<script>","",$save);
        $save=str_replace("</script>","",$save);
        $save=str_replace("<noscript>","",$save);
        $save=str_replace("</noscript>","",$save);
        $save=str_replace("<img","",$save);
        $save=str_replace("document","",$save);
        $save=str_replace(" from ","",$save);
        $save=str_replace("concat","",$save);
        $save=str_replace("union","",$save);
        $save=str_replace("}","",$save);
        $save=str_replace("{","",$save);
        $save=str_replace("[","",$save);
        $save=str_replace("]","",$save);
        $save=str_replace("(","",$save);
        $save=str_replace(")","",$save);
        $save=str_replace("|","",$save);
        $save=str_replace(",","",$save);
        $save=str_replace("<","",$save);
        $save=str_replace(">","",$save);
        $save=str_replace("^","",$save);
        $save=str_replace("='","",$save);
        $save=str_replace("=/","",$save);
        $save=str_replace("password","",$save);
        $save=str_replace("submit","",$save);
        $save=str_replace("input","",$save);
        $save=str_replace("meta","",$save);
        $save=str_replace("md5","",$save);
        $save=str_replace("pass","",$save);
        $save=str_replace("SESSION","",$save);
        $save=str_replace("login_shell","",$save);
        $save=str_replace("value","",$save);
        return $save;
    }
?>
