<?php
/***
* SIMRS Khanza Lite from version 0.1 Beta
* About : Porting of SIMRS Khanza by Windiarto a.k.a Mas Elkhanza as web and mobile app.
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
* Licence under GPL
***/

    function mc_encrypt($data, $strkey) {
        $key = hex2bin($strkey);
        if (mb_strlen($key, "8bit") !== 32) {
                throw new Exception("Needs a 256-bit key!");
        }

        $iv_size = openssl_cipher_iv_length("aes-256-cbc");
        $iv = openssl_random_pseudo_bytes($iv_size);
        $encrypted = openssl_encrypt($data,"aes-256-cbc",$key,OPENSSL_RAW_DATA,$iv );
        $signature = mb_substr(hash_hmac("sha256",$encrypted,$key,true),0,10,"8bit");
        $encoded = chunk_split(base64_encode($signature.$iv.$encrypted));
        return $encoded;
    }

    function mc_decrypt($str, $strkey){
        $key = hex2bin($strkey);
        if (mb_strlen($key, "8bit") !== 32) {
            throw new Exception("Needs a 256-bit key!");
        }

        $iv_size = openssl_cipher_iv_length("aes-256-cbc");
        $decoded = base64_decode($str);
        $signature = mb_substr($decoded,0,10,"8bit");
        $iv = mb_substr($decoded,10,$iv_size,"8bit");
        $encrypted = mb_substr($decoded,$iv_size+10,NULL,"8bit");
        $calc_signature = mb_substr(hash_hmac("sha256",$encrypted,$key,true),0,10,"8bit");
        if(!mc_compare($signature,$calc_signature)) {
            return "SIGNATURE_NOT_MATCH";
        }

        $decrypted = openssl_decrypt($encrypted,"aes-256-cbc",$key,OPENSSL_RAW_DATA,$iv);
        return $decrypted;
    }

    function mc_compare($a, $b) {
        if (strlen($a) !== strlen($b)) {
            return false;
        }

        $result = 0;

        for($i = 0; $i < strlen($a); $i ++) {
            $result |= ord($a[$i]) ^ ord($b[$i]);
        }

        return $result == 0;
    }

    //Digunakan untuk update jenis kelamin pasien saat cek tarif Poli Obgyn
    function UpdateDataPasien($nomor_rmlama,$nomor_kartu,$nomor_rm,$nama_pasien,$tgl_lahir,$gender){
        $request ='{
                        "metadata": {
                            "method": "update_patient",
                            "nomor_rm": "'.$nomor_rmlama.'"
                        },
                        "data": {
                            "nomor_kartu": "'.$nomor_kartu.'",
                            "nomor_rm": "'.$nomor_rm.'",
                            "nama_pasien": "'.$nama_pasien.'",
                            "tgl_lahir": "'.$tgl_lahir.'",
                            "gender": "'.$gender.'"
                        }
                   }';
        $msg= Request($request);
        //echo $msg['metadata']['message']."";
    }

    function CekTarif($nomor_sep,$nomor_kartu,$tgl_masuk,$tgl_pulang,$jenis_rawat,$kelas_rawat,$adl_sub_acute,
                            $adl_chronic,$icu_indikator,$icu_los,$ventilator_hour,$upgrade_class_ind,$upgrade_class_class,
                            $upgrade_class_los,$add_payment_pct,$birth_weight,$discharge_status,$diagnosa,$procedure,
                            $tarif_poli_eks,$nama_dokter,$kode_tarif,$payor_id,$payor_cd,$cob_cd,$coder_nik,
                            $prosedur_non_bedah,$prosedur_bedah,$konsultasi,$tenaga_ahli,$keperawatan,$penunjang,
                            $radiologi,$laboratorium,$pelayanan_darah,$rehabilitasi,$kamar,$rawat_intensif,$obat,
                            $alkes,$bmhp,$sewa_alat){
        $request ='{
                        "metadata": {
                            "method": "set_claim_data",
                            "nomor_sep": "'.$nomor_sep.'"
                        },
                        "data": {
                            "nomor_sep": "'.$nomor_sep.'",
                            "nomor_kartu": "'.$nomor_kartu.'",
                            "tgl_masuk": "'.$tgl_masuk.'",
                            "tgl_pulang": "'.$tgl_pulang.'",
                            "jenis_rawat": "'.$jenis_rawat.'",
                            "kelas_rawat": "'.$kelas_rawat.'",
                            "adl_sub_acute": "'.$adl_sub_acute.'",
                            "adl_chronic": "'.$adl_chronic.'",
                            "icu_indikator": "'.$icu_indikator.'",
                            "icu_los": "'.$icu_los.'",
                            "ventilator_hour": "'.$ventilator_hour.'",
                            "upgrade_class_ind": "'.$upgrade_class_ind.'",
                            "upgrade_class_class": "'.$upgrade_class_class.'",
                            "upgrade_class_los": "'.$upgrade_class_los.'",
                            "add_payment_pct": "'.$add_payment_pct.'",
                            "birth_weight": "'.$birth_weight.'",
                            "discharge_status": "'.$discharge_status.'",
                            "diagnosa": "'.$diagnosa.'",
                            "procedure": "'.$procedure.'",
                            "tarif_rs": {
                                "prosedur_non_bedah": "'.$prosedur_non_bedah.'",
                                "prosedur_bedah": "'.$prosedur_bedah.'",
                                "konsultasi": "'.$konsultasi.'",
                                "tenaga_ahli": "'.$tenaga_ahli.'",
                                "keperawatan": "'.$keperawatan.'",
                                "penunjang": "'.$penunjang.'",
                                "radiologi": "'.$radiologi.'",
                                "laboratorium": "'.$laboratorium.'",
                                "pelayanan_darah": "'.$pelayanan_darah.'",
                                "rehabilitasi": "'.$rehabilitasi.'",
                                "kamar": "'.$kamar.'",
                                "rawat_intensif": "'.$rawat_intensif.'",
                                "obat": "'.$obat.'",
                                "alkes": "'.$alkes.'",
                                "bmhp": "'.$bmhp.'",
                                "sewa_alat": "'.$sewa_alat.'"
                             },
                            "tarif_poli_eks": "'.$tarif_poli_eks.'",
                            "nama_dokter": "'.$nama_dokter.'",
                            "kode_tarif": "'.$kode_tarif.'",
                            "payor_id": "'.$payor_id.'",
                            "payor_cd": "'.$payor_cd.'",
                            "cob_cd": "'.$cob_cd.'",
                            "coder_nik": "'.$coder_nik.'"
                        }
                   }';
        $msg= Request($request);
        if($msg['metadata']['message']=="Ok"){
            Grouper($nomor_sep,$coder_nik);
        }
    }

    function Grouper($nomor_sep,$coder_nik){
        $request ='{
                        "metadata": {
                            "method":"grouper",
                            "stage":"1"
                        },
                        "data": {
                            "nomor_sep":"'.$nomor_sep.'"
                        }
                   }';
        $msg= Request($request);
        if($msg['metadata']['message']=="Ok"){
            if($msg['response']['cbg']['tariff'] == '') {
              $tarif = '0';
            } else {
              $tarif = $msg['response']['cbg']['tariff'];
            }
            echo '<dt>Grouper</dt> <dd>'.$msg['response']['cbg']['code'].'</dd><br>';
            echo '<dt>Deskripsi</dt> <dd>'.$msg['response']['cbg']['description'].'</dd><br>';
            echo '<dt>Tarif INACBG\'s</dt> <dd>Rp. '.number_format($tarif,0,",",".").'</dd><br><br>';
        }
    }

    function Request($request){
        $json = mc_encrypt ($request, INACBG_KEYRS);
        $header = array("Content-Type: application/x-www-form-urlencoded");
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, INACBG_URLWS);
        curl_setopt($ch, CURLOPT_HEADER, 0);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_HTTPHEADER,$header);
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
        $response = curl_exec($ch);
        $first = strpos($response, "\n")+1;
        $last = strrpos($response, "\n")-1;
        $hasilresponse = substr($response,$first,strlen($response) - $first - $last);
        $hasildecrypt = mc_decrypt($hasilresponse, INACBG_KEYRS);
        //echo $hasildecrypt;
        $msg = json_decode($hasildecrypt,true);
        return $msg;
    }
?>
