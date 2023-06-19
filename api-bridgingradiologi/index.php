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
                }else if(!(cektoken($header['x-token'])=='true')){
                    $response = array(
                        'metadata' => array(
                            'message' => 'Token salah/expired..!!',
                            'code' => 201
                        )
                    );
                    http_response_code(201);
                }else if((!empty($header['x-token'])) && (USERNAME==$header['x-username']) && (cektoken($header['x-token'])=='true')){
                    $konten = trim(file_get_contents("php://input"));
                    $decode = json_decode($konten, true);
                    switch ($url[0]) {
                        case "daftarhasilpemeriksaan":
                            if(empty($decode['periodeawal'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Tanggal periode awal pencarian tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['periodeawal'])){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format tanggal awal periode pencarian tidak sesuai, format yang benar adalah yyyy-mm-dd',
                                        'code' => 201
                                    )
                                );  
                                http_response_code(201);
                            }else if(empty($decode['periodeakhir'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Tanggal periode akhir pencarian tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",$decode['periodeakhir'])){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format tanggal akhir periode pencarian tidak sesuai, format yang benar adalah yyyy-mm-dd',
                                        'code' => 201
                                    )
                                );  
                                http_response_code(201);
                            }else if(strpos($decode['keyword'],"'")||strpos($decode['keyword'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format keyword salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else{
                                $sql2 = "SELECT order_out.tanggal_order,order_out.no_rm,order_out.no_rontgen,order_out.no_register,order_out.nama_pasien,
                                        order_out.expertise_finding,order_out.expertise_conclusion,order_out.expertise_bookmark,order_out.dokter_radiolog,
                                        order_out.link_ris,order_out.link_synapse,order_out.link_mobility,order_out.kode_tindakan,order_out.tindakan_radiologi,
                                        order_out.proyeksi,order_out.kV,order_out.mAS,order_out.FFD,order_out.BSF,order_out.inak,order_out.jml_penyinaran,
                                        order_out.dosis,order_out.statusupdate FROM order_out 
                                        WHERE order_out.tanggal_order between '".validTeks4($decode['periodeawal'],20)." 00:00:00' and '".validTeks4($decode['periodeakhir'],20)." 23:59:59' 
                                        and (order_out.no_rm like '%".validTeks4($decode['keyword'],20)."%' or order_out.no_rontgen like '%".validTeks4($decode['keyword'],20)."%' or 
                                        order_out.no_register like '%".validTeks4($decode['keyword'],20)."%' or order_out.nama_pasien like '%".validTeks4($decode['keyword'],20)."%' or 
                                        order_out.expertise_finding like '%".validTeks4($decode['keyword'],20)."%' or order_out.expertise_conclusion like '%".validTeks4($decode['keyword'],20)."%' or 
                                        order_out.dokter_radiolog like '%".validTeks4($decode['keyword'],20)."%' or order_out.kode_tindakan like '%".validTeks4($decode['keyword'],20)."%' or 
                                        order_out.tindakan_radiologi like '%".validTeks4($decode['keyword'],20)."%') order by order_out.tanggal_order";
                                $result2 = bukaquery($sql2);
                                if (mysqli_num_rows($result2) !== 0) {
                                    while ($data = fetch_array($result2)) {
                                        $data_array[] = array(
                                            'tanggal_order'=>$data['tanggal_order'], 
                                            'no_rm'=>$data['no_rm'], 
                                            'no_rontgen'=>$data['no_rontgen'], 
                                            'no_register'=>$data['no_register'], 
                                            'nama_pasien'=>$data['nama_pasien'], 
                                            'expertise_finding'=>$data['expertise_finding'], 
                                            'expertise_conclusion'=>$data['expertise_conclusion'], 
                                            'expertise_bookmark'=>$data['expertise_bookmark'], 
                                            'dokter_radiolog'=>$data['dokter_radiolog'],
                                            'link_ris'=>$data['link_ris'], 
                                            'link_synapse'=>$data['link_synapse'], 
                                            'link_mobility'=>$data['link_mobility'],
                                            'kode_tindakan'=>$data['kode_tindakan'],
                                            'tindakan_radiologi'=>$data['tindakan_radiologi'],
                                            'proyeksi'=>$data['proyeksi'],
                                            'kV'=>$data['kV'], 
                                            'mAS'=>$data['mAS'],
                                            'FFD'=>$data['FFD'],
                                            'BSF'=>$data['BSF'],
                                            'inak'=>$data['inak'],
                                            'jml_penyinaran'=>$data['jml_penyinaran'],
                                            'dosis'=>$data['dosis'],
                                            'statusupdate'=>$data['statusupdate']
                                        );
                                    }    
                                    $response = array(
                                        'response' => array(
                                            'list' => (
                                                $data_array
                                            )
                                        ),
                                        'metadata' => array(
                                            'message' => 'OK',
                                            'code' => '200'
                                        )
                                    );
                                    http_response_code(200);
                                } else {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Data pencarian tidak ditemukan',
                                            'code' => '201'
                                        )   
                                    );
                                    http_response_code(201);
                                }
                            }
                            break;
                        case "carinomororder":
                            if(empty($decode['no_rontgen'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nomor rontgen tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['no_rontgen'],"'")||strpos($decode['no_rontgen'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format nomor rontgen salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else{
                                $sql2 = "SELECT order_out.tanggal_order,order_out.no_rm,order_out.no_rontgen,order_out.no_register,order_out.nama_pasien,
                                        order_out.expertise_finding,order_out.expertise_conclusion,order_out.expertise_bookmark,order_out.dokter_radiolog,
                                        order_out.link_ris,order_out.link_synapse,order_out.link_mobility,order_out.kode_tindakan,order_out.tindakan_radiologi,
                                        order_out.proyeksi,order_out.kV,order_out.mAS,order_out.FFD,order_out.BSF,order_out.inak,order_out.jml_penyinaran,
                                        order_out.dosis,order_out.statusupdate FROM order_out 
                                        WHERE order_out.no_rontgen='".validTeks($decode['no_rontgen'])."'";
                                $result2 = bukaquery($sql2);
                                if ($data = fetch_array($result2)) {
                                    $response = array(
                                        'response' => array(
                                            'tanggal_order'=>$data['tanggal_order'], 
                                            'no_rm'=>$data['no_rm'], 
                                            'no_rontgen'=>$data['no_rontgen'], 
                                            'no_register'=>$data['no_register'], 
                                            'nama_pasien'=>$data['nama_pasien'], 
                                            'expertise_finding'=>$data['expertise_finding'], 
                                            'expertise_conclusion'=>$data['expertise_conclusion'], 
                                            'expertise_bookmark'=>$data['expertise_bookmark'], 
                                            'dokter_radiolog'=>$data['dokter_radiolog'],
                                            'link_ris'=>$data['link_ris'], 
                                            'link_synapse'=>$data['link_synapse'], 
                                            'link_mobility'=>$data['link_mobility'],
                                            'kode_tindakan'=>$data['kode_tindakan'],
                                            'tindakan_radiologi'=>$data['tindakan_radiologi'],
                                            'proyeksi'=>$data['proyeksi'],
                                            'kV'=>$data['kV'], 
                                            'mAS'=>$data['mAS'],
                                            'FFD'=>$data['FFD'],
                                            'BSF'=>$data['BSF'],
                                            'inak'=>$data['inak'],
                                            'jml_penyinaran'=>$data['jml_penyinaran'],
                                            'dosis'=>$data['dosis'],
                                            'statusupdate'=>$data['statusupdate']
                                        ),
                                        'metadata' => array(
                                            'message' => 'OK',
                                            'code' => 200
                                        )
                                    );
                                    http_response_code(200);
                                } else {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Data pencarian tidak ditemukan',
                                            'code' => '201'
                                        )   
                                    );
                                    http_response_code(201);
                                }
                            }
                            break;
                        case "hapushasilpemeriksaan":
                            if(empty($decode['no_rontgen'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nomor order tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['no_rontgen'],"'")||strpos($decode['no_rontgen'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format nomor salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else{
                                $query = bukaquery2("delete from order_out where order_out.no_rontgen='".validTeks($decode['no_rontgen'])."'");
                                if ($query) {
                                    $response = array(
                                        'response' => array(
                                            'no_rontgen'=>$decode['no_rontgen']
                                        ),
                                        'metadata' => array(
                                            'message' => 'OK',
                                            'code' => 200
                                        )
                                    );
                                    http_response_code(200);
                                } else {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Gagal menghapus',
                                            'code' => '201'
                                        )   
                                    );
                                    http_response_code(201);
                                }
                            }
                            break;
                        case "tambahhasilpemeriksaan":
                            if(empty($decode['tanggal_order'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Tanggal tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(!preg_match("/^[0-9]{4}(-|\/)([1-9]|0[1-9]|1[0-2])(-|\/)([1-9]|0[1-9]|[1-2][0-9]|3[0-1])\s(0|[0-1][0-9]|2[0-4]):?((0|[0-5][0-9]):?(0|[0-5][0-9])|6000|60:00)$/",$decode['tanggal_order'])){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format Tanggal tidak sesuai, format yang benar adalah yyyy-mm-dd HH:ii:ss',
                                        'code' => 201
                                    )
                                );  
                                http_response_code(201);
                            }else if(empty($decode['no_rm'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'No.RM tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['no_rm'],"'")||strpos($decode['no_rm'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format No.RM salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['no_rontgen'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nomor rontgen/order tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['no_rontgen'],"'")||strpos($decode['no_rontgen'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format nomor rontgen/order salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['no_register'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nomor register/pendaftaran tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['no_register'],"'")||strpos($decode['no_register'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format nomor register/pendaftaran salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['nama_pasien'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nama pasien tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['nama_pasien'],"'")||strpos($decode['nama_pasien'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format nama pasien salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['expertise_finding'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Expertise finding tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['expertise_finding'],"'")||strpos($decode['expertise_finding'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format expertise finding salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['expertise_conclusion'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Expertise conclusion tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['expertise_conclusion'],"'")||strpos($decode['expertise_conclusion'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format expertise conclusion salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['expertise_bookmark'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Expertise bookmark tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['expertise_bookmark'],"'")||strpos($decode['expertise_bookmark'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format expertise bookmark salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['dokter_radiolog'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'dokter radiolog tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['dokter_radiolog'],"'")||strpos($decode['dokter_radiolog'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format dokter radiolog salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['link_ris'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Link RIS tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['link_ris'],"'")||strpos($decode['link_ris'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format link RIS salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['link_synapse'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Link synapse tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['link_synapse'],"'")||strpos($decode['link_synapse'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format link synapse salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['link_mobility'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Link mobility tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['link_mobility'],"'")||strpos($decode['link_mobility'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format link mobility salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['kode_tindakan'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Kode tindakan tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['kode_tindakan'],"'")||strpos($decode['kode_tindakan'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format kode tindakan salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['tindakan_radiologi'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Tindakan radiologi tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['tindakan_radiologi'],"'")||strpos($decode['tindakan_radiologi'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format tindakan radiologi salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['proyeksi'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Proyeksi tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['proyeksi'],"'")||strpos($decode['proyeksi'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format proyeksi salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['kV'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'kV tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['kV'],"'")||strpos($decode['kV'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format kV salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['mAS'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'mAS tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['mAS'],"'")||strpos($decode['mAS'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format mAS salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['FFD'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'FFD tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['FFD'],"'")||strpos($decode['FFD'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format FFD salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['BSF'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'BSF tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['BSF'],"'")||strpos($decode['BSF'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format BSF salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['inak'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'BSF tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['inak'],"'")||strpos($decode['inak'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format inak salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['jml_penyinaran'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Jumlah penyinaran tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['jml_penyinaran'],"'")||strpos($decode['jml_penyinaran'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format Jumlah penyinaran salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['dosis'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Dosis tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['dosis'],"'")||strpos($decode['dosis'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format dosis salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else{
                                $query = bukaquery2("insert into order_out values('".validTeks($decode['tanggal_order'])."','".validTeks($decode['no_rm'])."','".validTeks($decode['no_rontgen'])."','".validTeks($decode['no_register'])."','".validTeks($decode['nama_pasien'])."','".validTeks($decode['expertise_finding'])."','".validTeks($decode['expertise_conclusion'])."','".validTeks($decode['expertise_bookmark'])."','".validTeks($decode['dokter_radiolog'])."','".validTeks($decode['link_ris'])."','".validTeks($decode['link_synapse'])."','".validTeks($decode['link_mobility'])."','".validTeks($decode['kode_tindakan'])."','".validTeks($decode['tindakan_radiologi'])."','".validTeks($decode['proyeksi'])."','".validTeks($decode['kV'])."','".validTeks($decode['mAS'])."','".validTeks($decode['FFD'])."','".validTeks($decode['BSF'])."','".validTeks($decode['inak'])."','".validTeks($decode['jml_penyinaran'])."','".validTeks($decode['dosis'])."','0')");
                                if ($query) {
                                    $response = array(
                                        'response' => array(
                                            'tanggal_order'=>$decode['tanggal_order'], 
                                            'no_rm'=>$decode['no_rm'], 
                                            'no_rontgen'=>$decode['no_rontgen'], 
                                            'no_register'=>$decode['no_register'], 
                                            'nama_pasien'=>$decode['nama_pasien'], 
                                            'expertise_finding'=>$decode['expertise_finding'], 
                                            'expertise_conclusion'=>$decode['expertise_conclusion'], 
                                            'expertise_bookmark'=>$decode['expertise_bookmark'], 
                                            'dokter_radiolog'=>$decode['dokter_radiolog'],
                                            'link_ris'=>$decode['link_ris'], 
                                            'link_synapse'=>$decode['link_synapse'], 
                                            'link_mobility'=>$decode['link_mobility'],
                                            'kode_tindakan'=>$decode['kode_tindakan'],
                                            'tindakan_radiologi'=>$decode['tindakan_radiologi'],
                                            'proyeksi'=>$decode['proyeksi'],
                                            'kV'=>$decode['kV'], 
                                            'mAS'=>$decode['mAS'],
                                            'FFD'=>$decode['FFD'],
                                            'BSF'=>$decode['BSF'],
                                            'inak'=>$decode['inak'],
                                            'jml_penyinaran'=>$decode['jml_penyinaran'],
                                            'dosis'=>$decode['dosis']
                                        ),
                                        'metadata' => array(
                                            'message' => 'OK',
                                            'code' => 200
                                        )
                                    );
                                    http_response_code(200);
                                }else {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Gagal menyimpan',
                                            'code' => '201'
                                        )   
                                    );
                                    http_response_code(201);
                                }
                            }
                            break;
                        case "ubahhasilpemeriksaan":
                            if(empty($decode['tanggal_order'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Tanggal tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(!preg_match("/^[0-9]{4}(-|\/)([1-9]|0[1-9]|1[0-2])(-|\/)([1-9]|0[1-9]|[1-2][0-9]|3[0-1])\s(0|[0-1][0-9]|2[0-4]):?((0|[0-5][0-9]):?(0|[0-5][0-9])|6000|60:00)$/",$decode['tanggal_order'])){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format Tanggal tidak sesuai, format yang benar adalah yyyy-mm-dd',
                                        'code' => 201
                                    )
                                );  
                                http_response_code(201);
                            }else if(empty($decode['no_rm'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'No.RM tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['no_rm'],"'")||strpos($decode['no_rm'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format No.RM salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['no_rontgen'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nomor rontgen/order tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['no_rontgen'],"'")||strpos($decode['no_rontgen'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format nomor rontgen/order salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['no_register'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nomor register/pendaftaran tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['no_register'],"'")||strpos($decode['no_register'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format nomor register/pendaftaran salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['nama_pasien'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Nama pasien tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['nama_pasien'],"'")||strpos($decode['nama_pasien'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format nama pasien salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['expertise_finding'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Expertise finding tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['expertise_finding'],"'")||strpos($decode['expertise_finding'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format expertise finding salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['expertise_conclusion'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Expertise conclusion tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['expertise_conclusion'],"'")||strpos($decode['expertise_conclusion'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format expertise conclusion salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['expertise_bookmark'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Expertise bookmark tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['expertise_bookmark'],"'")||strpos($decode['expertise_bookmark'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format expertise bookmark salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['dokter_radiolog'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'dokter radiolog tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['dokter_radiolog'],"'")||strpos($decode['dokter_radiolog'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format dokter radiolog salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['link_ris'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Link RIS tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['link_ris'],"'")||strpos($decode['link_ris'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format link RIS salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['link_synapse'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Link synapse tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['link_synapse'],"'")||strpos($decode['link_synapse'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format link synapse salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['link_mobility'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Link mobility tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['link_mobility'],"'")||strpos($decode['link_mobility'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format link mobility salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['kode_tindakan'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Kode tindakan tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['kode_tindakan'],"'")||strpos($decode['kode_tindakan'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format kode tindakan salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['tindakan_radiologi'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Tindakan radiologi tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['tindakan_radiologi'],"'")||strpos($decode['tindakan_radiologi'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format tindakan radiologi salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['proyeksi'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Proyeksi tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['proyeksi'],"'")||strpos($decode['proyeksi'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format proyeksi salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['kV'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'kV tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['kV'],"'")||strpos($decode['kV'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format kV salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['mAS'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'mAS tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['mAS'],"'")||strpos($decode['mAS'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format mAS salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['FFD'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'FFD tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['FFD'],"'")||strpos($decode['FFD'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format FFD salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['BSF'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'BSF tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['BSF'],"'")||strpos($decode['BSF'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format BSF salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['inak'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'BSF tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['inak'],"'")||strpos($decode['inak'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format inak salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['jml_penyinaran'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Jumlah penyinaran tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['jml_penyinaran'],"'")||strpos($decode['jml_penyinaran'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format Jumlah penyinaran salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(empty($decode['dosis'])) { 
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Dosis tidak boleh kosong',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else if(strpos($decode['dosis'],"'")||strpos($decode['dosis'],"\\")){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Format dosis salah',
                                        'code' => 201
                                    )
                                );
                                http_response_code(201);
                            }else{
                                if(getOne2("select count(order_out.no_rontgen) from order_out where order_out.no_rontgen='".validTeks($decode['no_rontgen'])."'")>0){
                                    $query = bukaquery2("update order_out set tanggal_order='".validTeks($decode['tanggal_order'])."',no_rm='".validTeks($decode['no_rm'])."',no_register='".validTeks($decode['no_register'])."',
                                            nama_pasien='".validTeks($decode['nama_pasien'])."',expertise_finding='".validTeks($decode['expertise_finding'])."',expertise_conclusion='".validTeks($decode['expertise_conclusion'])."',
                                            expertise_bookmark='".validTeks($decode['expertise_bookmark'])."',dokter_radiolog='".validTeks($decode['dokter_radiolog'])."',link_ris='".validTeks($decode['link_ris'])."',
                                            link_synapse='".validTeks($decode['link_synapse'])."',link_mobility='".validTeks($decode['link_mobility'])."',kode_tindakan='".validTeks($decode['kode_tindakan'])."',
                                            tindakan_radiologi='".validTeks($decode['tindakan_radiologi'])."',proyeksi='".validTeks($decode['proyeksi'])."',kV='".validTeks($decode['kV'])."',mAS='".validTeks($decode['mAS'])."',FFD='".validTeks($decode['FFD'])."',
                                            BSF='".validTeks($decode['BSF'])."',inak='".validTeks($decode['inak'])."',jml_penyinaran='".validTeks($decode['jml_penyinaran'])."',dosis='".validTeks($decode['dosis'])."',statusupdate='1' 
                                            where no_rontgen='".validTeks($decode['no_rontgen'])."'");
                                    if ($query) {
                                        $response = array(
                                            'response' => array(
                                                'tanggal_order'=>$decode['tanggal_order'], 
                                                'no_rm'=>$decode['no_rm'], 
                                                'no_rontgen'=>$decode['no_rontgen'], 
                                                'no_register'=>$decode['no_register'], 
                                                'nama_pasien'=>$decode['nama_pasien'], 
                                                'expertise_finding'=>$decode['expertise_finding'], 
                                                'expertise_conclusion'=>$decode['expertise_conclusion'], 
                                                'expertise_bookmark'=>$decode['expertise_bookmark'], 
                                                'dokter_radiolog'=>$decode['dokter_radiolog'],
                                                'link_ris'=>$decode['link_ris'], 
                                                'link_synapse'=>$decode['link_synapse'], 
                                                'link_mobility'=>$decode['link_mobility'],
                                                'kode_tindakan'=>$decode['kode_tindakan'],
                                                'tindakan_radiologi'=>$decode['tindakan_radiologi'],
                                                'proyeksi'=>$decode['proyeksi'],
                                                'kV'=>$decode['kV'], 
                                                'mAS'=>$decode['mAS'],
                                                'FFD'=>$decode['FFD'],
                                                'BSF'=>$decode['BSF'],
                                                'inak'=>$decode['inak'],
                                                'jml_penyinaran'=>$decode['jml_penyinaran'],
                                                'dosis'=>$decode['dosis']
                                            ),
                                            'metadata' => array(
                                                'message' => 'OK',
                                                'code' => 200
                                            )
                                        );
                                        http_response_code(200);
                                    }else {
                                        $response = array(
                                            'metadata' => array(
                                                'message' => 'Gagal menyimpan',
                                                'code' => '201'
                                            )   
                                        );
                                        http_response_code(201);
                                    }
                                }else {
                                    $response = array(
                                        'metadata' => array(
                                            'message' => 'Nomor rontgen tidak ditemukan',
                                            'code' => '201'
                                        )   
                                    );
                                    http_response_code(201);
                                }
                                    
                            }
                            break;
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
        echo "Selamat Datang di Web Service Bridging Radiologi ";
        echo "\n\n";
        echo "Cara Menggunakan Web Service Bridging Radiologi : \n";
        echo "1. Mengambil Token, methode GET \n";
        echo "   gunakan URL http://ipserverws:port/api-bridgingradiologi/auth \n";
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
        echo "2. Menampilkan daftar hasil pemeriksaan, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bridgingradiologi/daftarhasilpemeriksaan \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "periodeawal":"XXXX-XX-XX",'."\n";
	echo '      "periodeakhir":"XXXX-XX-XX",'."\n";
	echo '      "keyword":"XXXXXXXXXXXXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "list": ['."\n";
        echo '              {'."\n";
        echo '                  "tanggal_order": "xxxx-xx-xx xx:xx:xx",'."\n";
        echo '                  "no_rm": "xxxxxx",'."\n";
        echo '                  "no_rontgen": "XXXXXXXXXX",'."\n";
        echo '                  "no_register": "XXXXXXXXXX",'."\n";
        echo '                  "nama_pasien": "XXXXXXXXX",'."\n";
        echo '                  "expertise_finding": "XXXXXXXX",'."\n";
        echo '                  "expertise_conclusion": "XXXXXXXXXX",'."\n";
        echo '                  "expertise_bookmark": "XXXXXXX",'."\n";
        echo '                  "dokter_radiolog": "XXXXXXXX",'."\n";
        echo '                  "link_ris": "XXXXXXXXXX",'."\n";
        echo '                  "link_synapse": "XXXXXXXX",'."\n";
        echo '                  "link_mobility": "XXXXXXXXXX",'."\n";
        echo '                  "kode_tindakan": "XXXXXXXXXX",'."\n";
        echo '                  "tindakan_radiologi": "XXXXXXXX",'."\n";
        echo '                  "proyeksi": "XXXXXXXXX",'."\n";
        echo '                  "kV": "XXXXXXXXXX",'."\n";
        echo '                  "mAS": "XXXX",'."\n";
        echo '                  "FFD": "XXXX",'."\n";
        echo '                  "BSF": "XXXX",'."\n";
        echo '                  "inak": "XXX",'."\n";
        echo '                  "jml_penyinaran": "XXX",'."\n";
        echo '                  "dosis": "XXXX",'."\n";
        echo '                  "statusupdate": "0"'."\n";
        echo '              }'."\n";
        echo '          ]'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "OK",'."\n";
        echo '          "code": "200"'."\n";
        echo '      }'."\n";
        echo '  }'."\n\n";
        echo "3. Menampilkan data nomor order/nomor rontgen, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bridgingradiologi/carinomororder \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "no_rontgen":"XXXXXXXXXXXXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "tanggal_order": "xxxx-xx-xx xx:xx:xx",'."\n";
        echo '          "no_rm": "xxxxxx",'."\n";
        echo '          "no_rontgen": "XXXXXXXXXX",'."\n";
        echo '          "no_register": "XXXXXXXXXX",'."\n";
        echo '          "nama_pasien": "XXXXXXXXX",'."\n";
        echo '          "expertise_finding": "XXXXXXXX",'."\n";
        echo '          "expertise_conclusion": "XXXXXXXXXX",'."\n";
        echo '          "expertise_bookmark": "XXXXXXX",'."\n";
        echo '          "dokter_radiolog": "XXXXXXXX",'."\n";
        echo '          "link_ris": "XXXXXXXXXX",'."\n";
        echo '          "link_synapse": "XXXXXXXX",'."\n";
        echo '          "link_mobility": "XXXXXXXXXX",'."\n";
        echo '          "kode_tindakan": "XXXXXXXXXX",'."\n";
        echo '          "tindakan_radiologi": "XXXXXXXX",'."\n";
        echo '          "proyeksi": "XXXXXXXXX",'."\n";
        echo '          "kV": "XXXXXXXXXX",'."\n";
        echo '          "mAS": "XXXX",'."\n";
        echo '          "FFD": "XXXX",'."\n";
        echo '          "BSF": "XXXX",'."\n";
        echo '          "inak": "XXX",'."\n";
        echo '          "jml_penyinaran": "XXX",'."\n";
        echo '          "dosis": "XXXX",'."\n";
        echo '          "statusupdate": "0"'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "OK",'."\n";
        echo '          "code": "200"'."\n";
        echo '      }'."\n";
        echo '  }'."\n\n";
        echo "4. Menghapus hasilpemeriksaan, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bridgingradiologi/hapushasilpemeriksaan \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '      "no_rontgen":"XXXXXXXXXXXXXXXXXX"'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "no_rontgen":"XXXXXXXXXXXXXXXXXX"'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "OK",'."\n";
        echo '          "code": "200"'."\n";
        echo '      }'."\n";
        echo '  }'."\n\n";
        echo "5. Menambahkan hasil pemeriksaan, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bridgingradiologi/tambahhasilpemeriksaan \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '          "tanggal_order": "xxxx-xx-xx xx:xx:xx",'."\n";
        echo '          "no_rm": "xxxxxx",'."\n";
        echo '          "no_rontgen": "XXXXXXXXXX",'."\n";
        echo '          "no_register": "XXXXXXXXXX",'."\n";
        echo '          "nama_pasien": "XXXXXXXXX",'."\n";
        echo '          "expertise_finding": "XXXXXXXX",'."\n";
        echo '          "expertise_conclusion": "XXXXXXXXXX",'."\n";
        echo '          "expertise_bookmark": "XXXXXXX",'."\n";
        echo '          "dokter_radiolog": "XXXXXXXX",'."\n";
        echo '          "link_ris": "XXXXXXXXXX",'."\n";
        echo '          "link_synapse": "XXXXXXXX",'."\n";
        echo '          "link_mobility": "XXXXXXXXXX",'."\n";
        echo '          "kode_tindakan": "XXXXXXXXXX",'."\n";
        echo '          "tindakan_radiologi": "XXXXXXXX",'."\n";
        echo '          "proyeksi": "XXXXXXXXX",'."\n";
        echo '          "kV": "XXXXXXXXXX",'."\n";
        echo '          "mAS": "XXXX",'."\n";
        echo '          "FFD": "XXXX",'."\n";
        echo '          "BSF": "XXXX",'."\n";
        echo '          "inak": "XXX",'."\n";
        echo '          "jml_penyinaran": "XXX",'."\n";
        echo '          "dosis": "XXXX",'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "tanggal_order": "xxxx-xx-xx xx:xx:xx",'."\n";
        echo '          "no_rm": "xxxxxx",'."\n";
        echo '          "no_rontgen": "XXXXXXXXXX",'."\n";
        echo '          "no_register": "XXXXXXXXXX",'."\n";
        echo '          "nama_pasien": "XXXXXXXXX",'."\n";
        echo '          "expertise_finding": "XXXXXXXX",'."\n";
        echo '          "expertise_conclusion": "XXXXXXXXXX",'."\n";
        echo '          "expertise_bookmark": "XXXXXXX",'."\n";
        echo '          "dokter_radiolog": "XXXXXXXX",'."\n";
        echo '          "link_ris": "XXXXXXXXXX",'."\n";
        echo '          "link_synapse": "XXXXXXXX",'."\n";
        echo '          "link_mobility": "XXXXXXXXXX",'."\n";
        echo '          "kode_tindakan": "XXXXXXXXXX",'."\n";
        echo '          "tindakan_radiologi": "XXXXXXXX",'."\n";
        echo '          "proyeksi": "XXXXXXXXX",'."\n";
        echo '          "kV": "XXXXXXXXXX",'."\n";
        echo '          "mAS": "XXXX",'."\n";
        echo '          "FFD": "XXXX",'."\n";
        echo '          "BSF": "XXXX",'."\n";
        echo '          "inak": "XXX",'."\n";
        echo '          "jml_penyinaran": "XXX",'."\n";
        echo '          "dosis": "XXXX",'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "OK",'."\n";
        echo '          "code": "200"'."\n";
        echo '      }'."\n";
        echo '  }'."\n\n";
        echo "6. Menambahkan hasil pemeriksaan, methode POST\n";
        echo "   gunakan URL http://ipserverws:port/api-bridgingradiologi/ubahhasilpemeriksaan \n";
        echo "   Header gunakan x-token:token yang diambil sebelumnya, x-username:user yang diberikan RS\n";
        echo "   Body berisi : \n";
        echo '   {'."\n";
	echo '          "tanggal_order": "xxxx-xx-xx xx:xx:xx",'."\n";
        echo '          "no_rm": "xxxxxx",'."\n";
        echo '          "no_rontgen": "XXXXXXXXXX",'."\n";
        echo '          "no_register": "XXXXXXXXXX",'."\n";
        echo '          "nama_pasien": "XXXXXXXXX",'."\n";
        echo '          "expertise_finding": "XXXXXXXX",'."\n";
        echo '          "expertise_conclusion": "XXXXXXXXXX",'."\n";
        echo '          "expertise_bookmark": "XXXXXXX",'."\n";
        echo '          "dokter_radiolog": "XXXXXXXX",'."\n";
        echo '          "link_ris": "XXXXXXXXXX",'."\n";
        echo '          "link_synapse": "XXXXXXXX",'."\n";
        echo '          "link_mobility": "XXXXXXXXXX",'."\n";
        echo '          "kode_tindakan": "XXXXXXXXXX",'."\n";
        echo '          "tindakan_radiologi": "XXXXXXXX",'."\n";
        echo '          "proyeksi": "XXXXXXXXX",'."\n";
        echo '          "kV": "XXXXXXXXXX",'."\n";
        echo '          "mAS": "XXXX",'."\n";
        echo '          "FFD": "XXXX",'."\n";
        echo '          "BSF": "XXXX",'."\n";
        echo '          "inak": "XXX",'."\n";
        echo '          "jml_penyinaran": "XXX",'."\n";
        echo '          "dosis": "XXXX",'."\n";
        echo '   }'."\n\n";
        echo "   Hasilnya : \n";
        echo '   {'."\n";
        echo '      "response": {'."\n";
        echo '          "tanggal_order": "xxxx-xx-xx xx:xx:xx",'."\n";
        echo '          "no_rm": "xxxxxx",'."\n";
        echo '          "no_rontgen": "XXXXXXXXXX",'."\n";
        echo '          "no_register": "XXXXXXXXXX",'."\n";
        echo '          "nama_pasien": "XXXXXXXXX",'."\n";
        echo '          "expertise_finding": "XXXXXXXX",'."\n";
        echo '          "expertise_conclusion": "XXXXXXXXXX",'."\n";
        echo '          "expertise_bookmark": "XXXXXXX",'."\n";
        echo '          "dokter_radiolog": "XXXXXXXX",'."\n";
        echo '          "link_ris": "XXXXXXXXXX",'."\n";
        echo '          "link_synapse": "XXXXXXXX",'."\n";
        echo '          "link_mobility": "XXXXXXXXXX",'."\n";
        echo '          "kode_tindakan": "XXXXXXXXXX",'."\n";
        echo '          "tindakan_radiologi": "XXXXXXXX",'."\n";
        echo '          "proyeksi": "XXXXXXXXX",'."\n";
        echo '          "kV": "XXXXXXXXXX",'."\n";
        echo '          "mAS": "XXXX",'."\n";
        echo '          "FFD": "XXXX",'."\n";
        echo '          "BSF": "XXXX",'."\n";
        echo '          "inak": "XXX",'."\n";
        echo '          "jml_penyinaran": "XXX",'."\n";
        echo '          "dosis": "XXXX",'."\n";
        echo '      },'."\n";
        echo '      "metadata": {'."\n";
        echo '          "message": "OK",'."\n";
        echo '          "code": "200"'."\n";
        echo '      }'."\n";
        echo '  }'."\n\n";
    }
?>
