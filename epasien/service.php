<?php
    require_once('conf/conf.php');
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
        if ((!empty($header['token']))) {
            $iyem = trim(isset($header['token']))?trim($header['token']):NULL;
            $iyem = json_decode(encrypt_decrypt($iyem,"d"),true);
            if (!isset($iyem["norm"])) {
                $response = array(
                    'metadata' => array(
                        'message' => 'token salah..!!',
                        'code'    => 201
                    )
                );
                http_response_code(201);
            }else if (!isset($iyem["password"])) {
                $response = array(
                    'metadata' => array(
                        'message' => 'token salah..!!',
                        'code'    => 201
                    )
                );
                http_response_code(201);
            }else {
                if (strpos($iyem['norm'], "'") || strpos($iyem['norm'], "\\")) {
                    $response = array(
                        'metadata' => array(
                            'message' => 'token salah..!!',
                            'code'    => 201
                        )
                    );
                    http_response_code(201);
                } else if (strpos($iyem['password'], "'") || strpos($iyem['password'], "\\")) {
                    $response = array(
                        'metadata' => array(
                            'message' => 'token salah..!!',
                            'code'    => 201
                        )
                    );
                    http_response_code(201);
                } else {
                    $norm       = validTeks4($iyem['norm'],15);
                    $password   = validTeks4($iyem['password'],25);
                    if(getOne2("select count(personal_pasien.no_rkm_medis) from personal_pasien where personal_pasien.no_rkm_medis='$norm' and personal_pasien.password=AES_ENCRYPT('$password','windi')")==1){
                        $konten = trim(file_get_contents("php://input"));
                        $decode = json_decode($konten, true);

                        if(empty($decode['tanggal'])) {
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Tanggal tidak boleh kosong',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else if(!preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) ([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$/", $decode['tanggal'])){
                            $response = array(
                                'metadata' => array(
                                    'message' => 'Format Tanggal tidak sesuai, format yang benar adalah yyyy-mm-dd H:i:s',
                                    'code' => 201
                                )
                            );
                            http_response_code(201);
                        }else{
                            $tanggal    = $decode['tanggal'];
                            $jk         = "";
                            $umur       = "";
                            $queryuser  = @bukaquery2("select pasien.jk,TIMESTAMPDIFF(YEAR,pasien.tgl_lahir,CURDATE()) as umur from pasien where pasien.no_rkm_medis='$norm'");
                            while($rsqueryuser = mysqli_fetch_array($queryuser)) {
                                $jk        = $rsqueryuser["jk"];
                                $umur      = $rsqueryuser["umur"];
                            }

                            $heartRate = "";
                            if (isset($decode["heartRate"])) {
                                $heartRate = validTeks4($decode['heartRate'],10);
                                if($heartRate>0){
                                    $status = "Detak jantung dalam batas normal.";
                                    if($decode["heartRate"]>120){
                                        $status = "Detak jantung sangat tinggi. Istirahat sejenak dan hindari kafein. Jika berlanjut atau disertai nyeri dada atau sesak, segera ke faskes.";
                                    }else if($decode["heartRate"]<50){
                                        $status = "Detak jantung sangat rendah. Jika disertai pusing, lemas, atau hampir pingsan, segera periksakan ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','heartRate','$heartRate','/min','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Heart Rate sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Heart Rate',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $restingHeartRate = "";
                            if (isset($decode["restingHeartRate"])) {
                                $restingHeartRate = validTeks4($decode['restingHeartRate'],10);
                                if($restingHeartRate>0){
                                    $status = "Detak jantung istirahat normal.";
                                    if($decode["restingHeartRate"]>100){
                                        $status = "Detak jantung istirahat tinggi. Perbanyak istirahat dan kelola stres. Bila menetap, konsultasikan ke tenaga kesehatan.";
                                    }else if($decode["restingHeartRate"]<50){
                                        $status = "Detak jantung istirahat sangat rendah. Bila disertai keluhan, periksakan ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','restingHeartRate','$restingHeartRate','/min','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Resting Heart Rate sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Resting Heart Rate',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $walkingHeartRateAverage = "";
                            if (isset($decode["walkingHeartRateAverage"])) {
                                $walkingHeartRateAverage = validTeks4($decode['walkingHeartRateAverage'],10);
                                if($walkingHeartRateAverage>0){
                                    $status = "Detak jantung saat berjalan normal.";
                                    if($decode["walkingHeartRateAverage"]>140){
                                        $status = "Rata-rata detak jantung saat berjalan tergolong tinggi. Bila sering terjadi tanpa aktivitas berat, konsultasikan ke tenaga kesehatan.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','walkingHeartRateAverage','$walkingHeartRateAverage','/min','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Walking Heart Rate Average sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Walking Heart Rate Average',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $heartRateVariabilitySDNN = "";
                            if (isset($decode["heartRateVariabilitySDNN"])) {
                                $heartRateVariabilitySDNN = validTeks4($decode['heartRateVariabilitySDNN'],10);
                                if($heartRateVariabilitySDNN>0){
                                    $status = "Variabilitas detak jantung dalam rentang wajar.";
                                    if($decode["heartRateVariabilitySDNN"]<20){
                                        $status = "Variabilitas detak jantung rendah, dapat berkaitan dengan kelelahan atau stres. Perbanyak istirahat; bila menetap, konsultasikan ke tenaga kesehatan.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','heartRateVariabilitySDNN','$heartRateVariabilitySDNN','ms','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Heart Rate Variability SDNN sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Heart Rate Variability SDNN',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $heartRateRecoveryOneMinute = "";
                            if (isset($decode["heartRateRecoveryOneMinute"])) {
                                $heartRateRecoveryOneMinute = validTeks4($decode['heartRateRecoveryOneMinute'],10);
                                if($heartRateRecoveryOneMinute>0){
                                    $status = "Pemulihan detak jantung baik.";
                                    if($decode["heartRateRecoveryOneMinute"]<12){
                                        $status = "Pemulihan detak jantung setelah aktivitas tergolong lambat. Sebaiknya konsultasikan ke dokter untuk evaluasi kebugaran jantung.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','heartRateRecoveryOneMinute','$heartRateRecoveryOneMinute','/min','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Heart Rate Recovery One Minute sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Heart Rate Recovery One Minute',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $atrialFibrillationBurden = "";
                            if (isset($decode["atrialFibrillationBurden"])) {
                                $atrialFibrillationBurden = validTeks4($decode['atrialFibrillationBurden'],10);
                                if($atrialFibrillationBurden>0){
                                    $status = "Tidak ada beban atrial fibrillation berarti.";
                                    if($decode["atrialFibrillationBurden"]>0){
                                        $status = "Terdeteksi beban atrial fibrillation dari perangkat. Sebaiknya konsultasikan hasil ini ke dokter.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','atrialFibrillationBurden','$atrialFibrillationBurden','%','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Atrial Fibrillation Burden sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Atrial Fibrillation Burden',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $peripheralPerfusionIndex = "";
                            if (isset($decode["peripheralPerfusionIndex"])) {
                                $peripheralPerfusionIndex = validTeks4($decode['peripheralPerfusionIndex'],10);
                                if($peripheralPerfusionIndex>0){
                                    $status = "Indeks perfusi dalam batas normal.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','peripheralPerfusionIndex','$peripheralPerfusionIndex','%','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Peripheral Perfusion Index sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Peripheral Perfusion Index',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $oxygenSaturation = "";
                            if (isset($decode["oxygenSaturation"])) {
                                $oxygenSaturation = validTeks4($decode['oxygenSaturation'],10);
                                if($oxygenSaturation>0){
                                    $status = "Saturasi oksigen normal.";
                                    if($decode["oxygenSaturation"]<90){
                                        $status = "Saturasi oksigen sangat rendah. Segera periksakan diri ke faskes terdekat.";
                                    }else if($decode["oxygenSaturation"]<95){
                                        $status = "Saturasi oksigen di bawah normal. Tarik napas dalam dan ulangi pengukuran. Bila tetap rendah, periksakan ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','oxygenSaturation','$oxygenSaturation','%','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Oxygen Saturation sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Oxygen Saturation',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $electrocardiogramType = "";
                            if (isset($decode["electrocardiogramType"])) {
                                $electrocardiogramType = validTeks4($decode['electrocardiogramType'],50);
                                if($electrocardiogramType!=""){
                                    $status = "Hasil rekaman EKG sebaiknya ditunjukkan ke dokter untuk dibaca dan dipastikan.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','electrocardiogramType','$electrocardiogramType','mV','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Electrocardiogram Type sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Electrocardiogram Type',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $vo2Max = "";
                            if (isset($decode["vo2Max"])) {
                                $vo2Max = validTeks4($decode['vo2Max'],10);
                                if($vo2Max>0){
                                    $status = "Tingkat kebugaran Anda baik. Pertahankan!";
                                    if($decode["vo2Max"]<20){
                                        $status = "Tingkat kebugaran kardiorespirasi tergolong rendah. Tingkatkan aktivitas fisik secara bertahap dan konsultasikan bila perlu.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','vo2Max','$vo2Max','mL/min/kg','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data VO2 Max sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan VO2 Max',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $bloodPressureSystolic = "";
                            if (isset($decode["bloodPressureSystolic"])) {
                                $bloodPressureSystolic = validTeks4($decode['bloodPressureSystolic'],10);
                                if($bloodPressureSystolic>0){
                                    $status = "Tekanan darah sistolik normal.";
                                    if($decode["bloodPressureSystolic"]>180){
                                        $status = "Tekanan darah sistolik sangat tinggi. Segera periksakan ke faskes.";
                                    }else if($decode["bloodPressureSystolic"]>140){
                                        $status = "Tekanan darah sistolik tinggi. Kurangi garam, kelola stres, dan konsultasikan ke tenaga kesehatan.";
                                    }else if($decode["bloodPressureSystolic"]<90){
                                        $status = "Tekanan darah sistolik rendah. Bila pusing atau lemas, istirahat dan periksakan ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','bloodPressureSystolic','$bloodPressureSystolic','mm[Hg]','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Blood Pressure Systolic sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Blood Pressure Systolic',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $bloodPressureDiastolic = "";
                            if (isset($decode["bloodPressureDiastolic"])) {
                                $bloodPressureDiastolic = validTeks4($decode['bloodPressureDiastolic'],10);
                                if($bloodPressureDiastolic>0){
                                    $status = "Tekanan darah diastolik normal.";
                                    if($decode["bloodPressureDiastolic"]>120){
                                        $status = "Tekanan darah diastolik sangat tinggi. Segera periksakan ke faskes.";
                                    }else if($decode["bloodPressureDiastolic"]>90){
                                        $status = "Tekanan darah diastolik tinggi. Konsultasikan ke tenaga kesehatan.";
                                    }else if($decode["bloodPressureDiastolic"]<60){
                                        $status = "Tekanan darah diastolik rendah. Bila disertai keluhan, periksakan ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','bloodPressureDiastolic','$bloodPressureDiastolic','mm[Hg]','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Blood Pressure Diastolic sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Blood Pressure Diastolic',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $respiratoryRate = "";
                            if (isset($decode["respiratoryRate"])) {
                                $respiratoryRate = validTeks4($decode['respiratoryRate'],10);
                                if($respiratoryRate>0){
                                    $status = "Laju napas normal.";
                                    if($decode["respiratoryRate"]>24){
                                        $status = "Laju napas cepat. Jika disertai sesak atau demam, segera periksakan ke faskes.";
                                    }else if($decode["respiratoryRate"]<10){
                                        $status = "Laju napas lambat. Bila disertai mengantuk berat atau sulit dibangunkan, segera ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','respiratoryRate','$respiratoryRate','/min','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Respiratory Rate sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Respiratory Rate',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $forcedVitalCapacity = "";
                            if (isset($decode["forcedVitalCapacity"])) {
                                $forcedVitalCapacity = validTeks4($decode['forcedVitalCapacity'],10);
                                if($forcedVitalCapacity>0){
                                    $status = "Tercatat. Untuk interpretasi, konsultasikan ke dokter.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','forcedVitalCapacity','$forcedVitalCapacity','L','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Forced Vital Capacity sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Forced Vital Capacity',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $forcedExpiratoryVolume1Second = "";
                            if (isset($decode["forcedExpiratoryVolume1Second"])) {
                                $forcedExpiratoryVolume1Second = validTeks4($decode['forcedExpiratoryVolume1Second'],10);
                                if($forcedExpiratoryVolume1Second>0){
                                    $status = "Tercatat. Untuk interpretasi, konsultasikan ke dokter.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','forcedExpiratoryVolume1Second','$forcedExpiratoryVolume1Second','L','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Forced Expiratory Volume 1 Second sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Forced Expiratory Volume 1 Second',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $peakExpiratoryFlowRate = "";
                            if (isset($decode["peakExpiratoryFlowRate"])) {
                                $peakExpiratoryFlowRate = validTeks4($decode['peakExpiratoryFlowRate'],10);
                                if($peakExpiratoryFlowRate>0){
                                    $status = "Tercatat. Untuk interpretasi, konsultasikan ke dokter.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','peakExpiratoryFlowRate','$peakExpiratoryFlowRate','L/min','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Peak Expiratory Flow Rate sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Peak Expiratory Flow Rate',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $inhalerUsage = "";
                            if (isset($decode["inhalerUsage"])) {
                                $inhalerUsage = validTeks4($decode['inhalerUsage'],10);
                                if($inhalerUsage>0){
                                    $status = "Penggunaan inhaler tercatat.";
                                    if($decode["inhalerUsage"]>8){
                                        $status = "Penggunaan inhaler cukup sering hari ini. Bila gejala sesak memburuk, konsultasikan ke dokter.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','inhalerUsage','$inhalerUsage','{puff}','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Inhaler Usage sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Inhaler Usage',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $bodyTemperature = "";
                            if (isset($decode["bodyTemperature"])) {
                                $bodyTemperature = validTeks4($decode['bodyTemperature'],10);
                                if($bodyTemperature>0){
                                    $status = "Suhu tubuh normal.";
                                    if($decode["bodyTemperature"]>39){
                                        $status = "Demam tinggi. Perbanyak minum, kompres, dan segera periksakan ke faskes.";
                                    }else if($decode["bodyTemperature"]>37.5){
                                        $status = "Suhu tubuh di atas normal (demam). Istirahat dan pantau. Bila naik atau menetap, periksakan ke faskes.";
                                    }else if($decode["bodyTemperature"]<35){
                                        $status = "Suhu tubuh sangat rendah. Hangatkan tubuh dan segera ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','bodyTemperature','$bodyTemperature','Cel','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Body Temperature sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Body Temperature',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $basalBodyTemperature = "";
                            if (isset($decode["basalBodyTemperature"])) {
                                $basalBodyTemperature = validTeks4($decode['basalBodyTemperature'],10);
                                if($basalBodyTemperature>0){
                                    $status = "Suhu basal normal.";
                                    if($decode["basalBodyTemperature"]>37.5){
                                        $status = "Suhu basal di atas normal. Bila disertai keluhan, konsultasikan ke tenaga kesehatan.";
                                    }else if($decode["basalBodyTemperature"]<35){
                                        $status = "Suhu basal sangat rendah. Hangatkan tubuh dan periksakan ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','basalBodyTemperature','$basalBodyTemperature','Cel','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Basal Body Temperature sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Basal Body Temperature',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $appleSleepingWristTemperature = "";
                            if (isset($decode["appleSleepingWristTemperature"])) {
                                $appleSleepingWristTemperature = validTeks4($decode['appleSleepingWristTemperature'],50);
                                if($appleSleepingWristTemperature!=""){
                                    $status = "Tercatat sebagai tren suhu kulit, bukan suhu tubuh klinis.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','appleSleepingWristTemperature','$appleSleepingWristTemperature','Cel','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Apple Sleeping Wrist Temperature sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Apple Sleeping Wrist Temperature',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $bodyMass = "";
                            if (isset($decode["bodyMass"])) {
                                $bodyMass = validTeks4($decode['bodyMass'],10);
                                if($bodyMass>0){
                                    $status = "Berat badan tercatat.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','bodyMass','$bodyMass','kg','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Body Mass sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Body Mass',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $height = "";
                            if (isset($decode["height"])) {
                                $height = validTeks4($decode['height'],10);
                                if($height>0){
                                    $status = "Tinggi badan tercatat.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','height','$height','cm','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Height sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Height',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $bodyMassIndex = "";
                            if (isset($decode["bodyMassIndex"])) {
                                $bodyMassIndex = validTeks4($decode['bodyMassIndex'],10);
                                if($bodyMassIndex>0){
                                    $status = "Indeks massa tubuh dalam rentang ideal.";
                                    if($umur<18){
                                        $status = "Interpretasi BMI untuk usia di bawah 18 tahun berbeda dengan dewasa. Konsultasikan ke tenaga kesehatan.";
                                    }else if($decode["bodyMassIndex"]>30){
                                        $status = "Indeks massa tubuh tergolong obesitas. Konsultasikan pola hidup sehat dengan tenaga kesehatan.";
                                    }else if($decode["bodyMassIndex"]>25){
                                        $status = "Indeks massa tubuh di atas ideal. Pertimbangkan konsultasi gaya hidup sehat dengan tenaga kesehatan.";
                                    }else if($decode["bodyMassIndex"]<18.5){
                                        $status = "Indeks massa tubuh di bawah ideal. Konsultasikan dengan tenaga kesehatan.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','bodyMassIndex','$bodyMassIndex','kg/m2','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Body Mass Index sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Body Mass Index',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $bodyFatPercentage = "";
                            if (isset($decode["bodyFatPercentage"])) {
                                $bodyFatPercentage = validTeks4($decode['bodyFatPercentage'],10);
                                if($bodyFatPercentage>0){
                                    $status = "Persentase lemak tubuh dalam rentang wajar.";
                                    if($jk=="L" && $decode["bodyFatPercentage"]>25){
                                        $status = "Persentase lemak tubuh tergolong tinggi untuk pria. Konsultasikan pola hidup sehat dengan tenaga kesehatan.";
                                    }else if($jk=="P" && $decode["bodyFatPercentage"]>32){
                                        $status = "Persentase lemak tubuh tergolong tinggi untuk wanita. Konsultasikan pola hidup sehat dengan tenaga kesehatan.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','bodyFatPercentage','$bodyFatPercentage','%','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Body Fat Percentage sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Body Fat Percentage',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $leanBodyMass = "";
                            if (isset($decode["leanBodyMass"])) {
                                $leanBodyMass = validTeks4($decode['leanBodyMass'],10);
                                if($leanBodyMass>0){
                                    $status = "Massa tubuh tanpa lemak tercatat.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','leanBodyMass','$leanBodyMass','kg','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Lean Body Mass sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Lean Body Mass',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $waistCircumference = "";
                            if (isset($decode["waistCircumference"])) {
                                $waistCircumference = validTeks4($decode['waistCircumference'],10);
                                if($waistCircumference>0){
                                    $status = "Lingkar pinggang dalam batas aman.";
                                    if($jk=="L" && $decode["waistCircumference"]>90){
                                        $status = "Lingkar pinggang di atas batas untuk pria dan berkaitan dengan risiko metabolik. Konsultasikan ke tenaga kesehatan.";
                                    }else if($jk=="P" && $decode["waistCircumference"]>80){
                                        $status = "Lingkar pinggang di atas batas untuk wanita dan berkaitan dengan risiko metabolik. Konsultasikan ke tenaga kesehatan.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','waistCircumference','$waistCircumference','cm','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Waist Circumference sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Waist Circumference',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $stepCount = "";
                            if (isset($decode["stepCount"])) {
                                $stepCount = validTeks4($decode['stepCount'],10);
                                if($stepCount>0){
                                    $status = "Bagus! Langkah Anda hari ini tercatat. Tetap aktif ya.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','stepCount','$stepCount','{steps}','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Step Count sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Step Count',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $distanceWalkingRunning = "";
                            if (isset($decode["distanceWalkingRunning"])) {
                                $distanceWalkingRunning = validTeks4($decode['distanceWalkingRunning'],10);
                                if($distanceWalkingRunning>0){
                                    $status = "Mantap, Anda sudah bergerak hari ini. Pertahankan!";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','distanceWalkingRunning','$distanceWalkingRunning','m','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Distance Walking Running sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Distance Walking Running',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $distanceCycling = "";
                            if (isset($decode["distanceCycling"])) {
                                $distanceCycling = validTeks4($decode['distanceCycling'],10);
                                if($distanceCycling>0){
                                    $status = "Keren, bersepeda baik untuk jantung. Lanjutkan!";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','distanceCycling','$distanceCycling','m','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Distance Cycling sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Distance Cycling',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $distanceSwimming = "";
                            if (isset($decode["distanceSwimming"])) {
                                $distanceSwimming = validTeks4($decode['distanceSwimming'],10);
                                if($distanceSwimming>0){
                                    $status = "Bagus, berenang olahraga yang menyehatkan. Pertahankan!";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','distanceSwimming','$distanceSwimming','m','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Distance Swimming sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Distance Swimming',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $activeEnergyBurned = "";
                            if (isset($decode["activeEnergyBurned"])) {
                                $activeEnergyBurned = validTeks4($decode['activeEnergyBurned'],10);
                                if($activeEnergyBurned>0){
                                    $status = "Hebat, Anda membakar kalori dengan beraktivitas. Terus semangat!";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','activeEnergyBurned','$activeEnergyBurned','kcal','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Active Energy Burned sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Active Energy Burned',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $basalEnergyBurned = "";
                            if (isset($decode["basalEnergyBurned"])) {
                                $basalEnergyBurned = validTeks4($decode['basalEnergyBurned'],10);
                                if($basalEnergyBurned>0){
                                    $status = "Tercatat. Metabolisme basal Anda berjalan normal.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','basalEnergyBurned','$basalEnergyBurned','kcal','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Basal Energy Burned sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Basal Energy Burned',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $flightsClimbed = "";
                            if (isset($decode["flightsClimbed"])) {
                                $flightsClimbed = validTeks4($decode['flightsClimbed'],10);
                                if($flightsClimbed>0){
                                    $status = "Bagus! Naik tangga olahraga ringan yang bermanfaat.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','flightsClimbed','$flightsClimbed','{flights}','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Flights Climbed sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Flights Climbed',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $appleExerciseTime = "";
                            if (isset($decode["appleExerciseTime"])) {
                                $appleExerciseTime = validTeks4($decode['appleExerciseTime'],10);
                                if($appleExerciseTime>0){
                                    $status = "Hebat, Anda meluangkan waktu berolahraga. Pertahankan!";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','appleExerciseTime','$appleExerciseTime','min','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Apple Exercise Time sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Apple Exercise Time',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $appleStandTime = "";
                            if (isset($decode["appleStandTime"])) {
                                $appleStandTime = validTeks4($decode['appleStandTime'],10);
                                if($appleStandTime>0){
                                    $status = "Bagus, Anda cukup sering bergerak. Hindari duduk terlalu lama.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','appleStandTime','$appleStandTime','min','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Apple Stand Time sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Apple Stand Time',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $swimmingStrokeCount = "";
                            if (isset($decode["swimmingStrokeCount"])) {
                                $swimmingStrokeCount = validTeks4($decode['swimmingStrokeCount'],10);
                                if($swimmingStrokeCount>0){
                                    $status = "Mantap, terus tingkatkan kemampuan renang Anda!";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','swimmingStrokeCount','$swimmingStrokeCount','{strokes}','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Swimming Stroke Count sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Swimming Stroke Count',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $pushCount = "";
                            if (isset($decode["pushCount"])) {
                                $pushCount = validTeks4($decode['pushCount'],10);
                                if($pushCount>0){
                                    $status = "Bagus, Anda tetap aktif. Pertahankan semangatnya!";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','pushCount','$pushCount','{pushes}','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Push Count sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Push Count',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $runningSpeed = "";
                            if (isset($decode["runningSpeed"])) {
                                $runningSpeed = validTeks4($decode['runningSpeed'],10);
                                if($runningSpeed>0){
                                    $status = "Keren, kecepatan lari Anda tercatat. Tetap semangat berlari!";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','runningSpeed','$runningSpeed','m/s','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Running Speed sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Running Speed',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $runningPower = "";
                            if (isset($decode["runningPower"])) {
                                $runningPower = validTeks4($decode['runningPower'],10);
                                if($runningPower>0){
                                    $status = "Bagus, performa lari Anda tercatat. Pertahankan!";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','runningPower','$runningPower','W','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Running Power sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Running Power',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $runningStrideLength = "";
                            if (isset($decode["runningStrideLength"])) {
                                $runningStrideLength = validTeks4($decode['runningStrideLength'],10);
                                if($runningStrideLength>0){
                                    $status = "Tercatat. Pertahankan teknik lari Anda.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','runningStrideLength','$runningStrideLength','m','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Running Stride Length sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Running Stride Length',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $runningVerticalOscillation = "";
                            if (isset($decode["runningVerticalOscillation"])) {
                                $runningVerticalOscillation = validTeks4($decode['runningVerticalOscillation'],10);
                                if($runningVerticalOscillation>0){
                                    $status = "Tercatat. Teruskan latihan lari Anda.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','runningVerticalOscillation','$runningVerticalOscillation','cm','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Running Vertical Oscillation sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Running Vertical Oscillation',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $runningGroundContactTime = "";
                            if (isset($decode["runningGroundContactTime"])) {
                                $runningGroundContactTime = validTeks4($decode['runningGroundContactTime'],10);
                                if($runningGroundContactTime>0){
                                    $status = "Tercatat. Pertahankan konsistensi latihan Anda.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','runningGroundContactTime','$runningGroundContactTime','ms','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Running Ground Contact Time sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Running Ground Contact Time',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $bloodGlucose = "";
                            if (isset($decode["bloodGlucose"])) {
                                $bloodGlucose = validTeks4($decode['bloodGlucose'],10);
                                if($bloodGlucose>0){
                                    $status = "Gula darah dalam batas normal.";
                                    if($decode["bloodGlucose"]>300){
                                        $status = "Gula darah sangat tinggi. Segera periksakan ke faskes.";
                                    }else if($decode["bloodGlucose"]>200){
                                        $status = "Gula darah tinggi. Perhatikan asupan makanan dan konsultasikan ke tenaga kesehatan.";
                                    }else if($decode["bloodGlucose"]<70){
                                        $status = "Gula darah rendah. Segera konsumsi sesuatu yang manis. Bila tidak membaik, ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','bloodGlucose','$bloodGlucose','mg/dL','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Blood Glucose sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Blood Glucose',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $insulinDelivery = "";
                            if (isset($decode["insulinDelivery"])) {
                                $insulinDelivery = validTeks4($decode['insulinDelivery'],10);
                                if($insulinDelivery>0){
                                    $status = "Pemberian insulin tercatat. Ikuti anjuran dokter Anda.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','insulinDelivery','$insulinDelivery','[iU]','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Insulin Delivery sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Insulin Delivery',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $bloodAlcoholContent = "";
                            if (isset($decode["bloodAlcoholContent"])) {
                                $bloodAlcoholContent = validTeks4($decode['bloodAlcoholContent'],10);
                                if($bloodAlcoholContent>0){
                                    $status = "Tidak terdeteksi alkohol.";
                                    if($decode["bloodAlcoholContent"]>0){
                                        $status = "Terdeteksi kadar alkohol dalam darah. Hindari berkendara dan aktivitas berisiko.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','bloodAlcoholContent','$bloodAlcoholContent','%','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Blood Alcohol Content sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Blood Alcohol Content',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $sleepAnalysis = "";
                            if (isset($decode["sleepAnalysis"])) {
                                $sleepAnalysis = validTeks4($decode['sleepAnalysis'],50);
                                if($sleepAnalysis!=""){
                                    $status = "Data tidur tercatat. Tidur cukup penting untuk kesehatan.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','sleepAnalysis','$sleepAnalysis','-','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Sleep Analysis sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Sleep Analysis',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $mindfulSession = "";
                            if (isset($decode["mindfulSession"])) {
                                $mindfulSession = validTeks4($decode['mindfulSession'],10);
                                if($mindfulSession>0){
                                    $status = "Bagus, Anda meluangkan waktu untuk relaksasi. Baik untuk kesehatan mental.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','mindfulSession','$mindfulSession','s','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Mindful Session sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Mindful Session',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $numberOfTimesFallen = "";
                            if (isset($decode["numberOfTimesFallen"])) {
                                $numberOfTimesFallen = validTeks4($decode['numberOfTimesFallen'],10);
                                if($numberOfTimesFallen>0){
                                    $status = "Tidak ada kejadian jatuh berarti.";
                                    if($decode["numberOfTimesFallen"]>0){
                                        $status = "Terdeteksi kejadian jatuh. Bila ada cedera, pusing, atau nyeri, periksakan ke faskes.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','numberOfTimesFallen','$numberOfTimesFallen','{falls}','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Number Of Times Fallen sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Number Of Times Fallen',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $highHeartRateEvent = "";
                            if (isset($decode["highHeartRateEvent"])) {
                                $highHeartRateEvent = validTeks4($decode['highHeartRateEvent'],50);
                                if($highHeartRateEvent!=""){
                                    $status = "Perangkat mendeteksi notifikasi detak jantung tinggi. Konsultasikan ke dokter bila berulang.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','highHeartRateEvent','$highHeartRateEvent','-','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data High Heart Rate Event sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan High Heart Rate Event',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $lowHeartRateEvent = "";
                            if (isset($decode["lowHeartRateEvent"])) {
                                $lowHeartRateEvent = validTeks4($decode['lowHeartRateEvent'],50);
                                if($lowHeartRateEvent!=""){
                                    $status = "Perangkat mendeteksi notifikasi detak jantung rendah. Konsultasikan ke dokter bila berulang.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','lowHeartRateEvent','$lowHeartRateEvent','-','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Low Heart Rate Event sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Low Heart Rate Event',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $irregularHeartRhythmEvent = "";
                            if (isset($decode["irregularHeartRhythmEvent"])) {
                                $irregularHeartRhythmEvent = validTeks4($decode['irregularHeartRhythmEvent'],50);
                                if($irregularHeartRhythmEvent!=""){
                                    $status = "Perangkat mendeteksi irama jantung tidak teratur. Sebaiknya konsultasikan ke dokter.";
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','irregularHeartRhythmEvent','$irregularHeartRhythmEvent','-','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Irregular Heart Rhythm Event sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Irregular Heart Rhythm Event',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $environmentalAudioExposure = "";
                            if (isset($decode["environmentalAudioExposure"])) {
                                $environmentalAudioExposure = validTeks4($decode['environmentalAudioExposure'],10);
                                if($environmentalAudioExposure>0){
                                    $status = "Paparan suara lingkungan dalam batas aman.";
                                    if($decode["environmentalAudioExposure"]>85){
                                        $status = "Paparan suara lingkungan cukup tinggi dan dapat memengaruhi pendengaran bila berlangsung lama. Kurangi paparan bising.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','environmentalAudioExposure','$environmentalAudioExposure','dB[SPL]','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Environmental Audio Exposure sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Environmental Audio Exposure',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            $headphoneAudioExposure = "";
                            if (isset($decode["headphoneAudioExposure"])) {
                                $headphoneAudioExposure = validTeks4($decode['headphoneAudioExposure'],10);
                                if($headphoneAudioExposure>0){
                                    $status = "Volume headphone dalam batas aman.";
                                    if($decode["headphoneAudioExposure"]>85){
                                        $status = "Volume headphone cukup tinggi dan berisiko bagi pendengaran bila berlangsung lama. Turunkan volume.";
                                    }
                                    try {
                                        Tambah4("pasien_wearable","'$norm','$tanggal','headphoneAudioExposure','$headphoneAudioExposure','dB[SPL]','$status'");
                                    } catch(mysqli_sql_exception $e) {
                                        if($e->getCode()==1062){
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Data Headphone Audio Exposure sudah ada',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }else{
                                            $response = array(
                                                'metadata' => array(
                                                    'message' => 'Gagal menyimpan Headphone Audio Exposure',
                                                    'code'    => 201
                                                )
                                            );
                                            http_response_code(201);
                                        }
                                    }
                                }
                            }

                            if(empty($response)){
                                $response = array(
                                    'metadata' => array(
                                        'message' => 'Data wearable berhasil disimpan',
                                        'code'    => 200
                                    )
                                );
                                http_response_code(200);
                            }
                        }
                    }else{
                        $response = array(
                            'metadata' => array(
                                'message' => 'token salah..!!',
                                'code'    => 201
                            )
                        );
                        http_response_code(201);
                    }
                }
            }
        } else {
            $response = array(
                'metadata' => array(
                    'message' => 'token wajib diisi',
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
                'message' => 'Selamat Datang di Service Wearable SIMKES Khanza',
                'code'    => 200
            )
        ));
    }
?>