<?php
    require_once('../conf/conf.php');

    function getKey() {
       $keyRS = "52eab0d187258e97219ae323c42f12e62e3eab204a6063fbfdce6ccd8b3b768f";   
       return $keyRS;
    }

    function getUrlWS() {
        $UrlWS = "http://192.168.62.3/E-Klaim/ws.php";
        return $UrlWS;
    }
    
    function getKelasRS() {
        $kelasRS = "BP";
        return $kelasRS;
    }

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
    
    function GenerateNomorCovid(){	
        $nomor="";
        $request ='{
                        "metadata": {
                            "method": "generate_claim_number"
                        }, 
                        "data": {
                            "payor_id": "71" 
                        }
                    }';
        $msg= Request($request);
        if($msg['metadata']['message']=="Ok"){
            $nomor=$msg['response']['claim_number'];
        }
        return $nomor;
    }
    
    function BuatKlaimBaru($nomor_kartu,$nomor_sep,$nomor_rm,$nama_pasien,$tgl_lahir,$gender){	
        $request ='{
                        "metadata":{
                            "method":"new_claim"
                        },
                        "data":{
                            "nomor_kartu":"'.$nomor_kartu.'",
                            "nomor_sep":"'.$nomor_sep.'",
                            "nomor_rm":"'.$nomor_rm.'",
                            "nama_pasien":"'.$nama_pasien.'",
                            "tgl_lahir":"'.$tgl_lahir.'",
                            "gender":"'.$gender.'"
                        }
                    }';
        $msg= Request($request);
        if($msg['metadata']['message']=="Ok"){
            InsertData2("inacbg_klaim_baru","'".$nomor_sep."','".$msg['response']['patient_id']."','".$msg['response']['admission_id']."','".$msg['response']['hospital_admission_id']."'");
        }
        return $msg['metadata']['message'];
    }
    
    function BuatKlaimBaru2($nomor_kartu,$nomor_sep,$nomor_rm,$nama_pasien,$tgl_lahir,$gender,$norawat){	
        $request ='{
                        "metadata":{
                            "method":"new_claim"
                        },
                        "data":{
                            "nomor_kartu":"'.$nomor_kartu.'",
                            "nomor_sep":"'.$nomor_sep.'",
                            "nomor_rm":"'.$nomor_rm.'",
                            "nama_pasien":"'.$nama_pasien.'",
                            "tgl_lahir":"'.$tgl_lahir.'",
                            "gender":"'.$gender.'"
                        }
                    }';
        $msg= Request($request);
        if($msg['metadata']['message']=="Ok"){
            InsertData2("inacbg_klaim_baru2","'".$norawat."','".$nomor_sep."','".$msg['response']['patient_id']."','".$msg['response']['admission_id']."','".$msg['response']['hospital_admission_id']."'");
        }
        return $msg['metadata']['message'];
    }
    
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
        echo $msg['metadata']['message']."";
    }
    
    function HapusDataPasien($nomor_rm,$coder_nik){	
        $request ='{
                        "metadata": {
                            "method": "delete_patient"
                        },
                        "data": {
                            "nomor_rm": "'.$nomor_rm.'",
                            "coder_nik": "'.$coder_nik.'"
                        }
                   }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
    function UpdateDataKlaim($nomor_sep,$nomor_kartu,$tgl_masuk,$tgl_pulang,$jenis_rawat,$kelas_rawat,$adl_sub_acute,
                            $adl_chronic,$icu_indikator,$icu_los,$ventilator_hour,$upgrade_class_ind,$upgrade_class_class,
                            $upgrade_class_los,$add_payment_pct,$birth_weight,$discharge_status,$diagnosa,$procedure,
                            $tarif_poli_eks,$nama_dokter,$kode_tarif,$payor_id,$payor_cd,$cob_cd,$coder_nik,$no_rawat){	
        
        $prosedur_non_bedah=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Ralan Dokter Paramedis'");
        if($prosedur_non_bedah==""){
            $prosedur_non_bedah="0";
        }
        $prosedur_bedah=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Operasi'");
        if($prosedur_bedah==""){
            $prosedur_bedah="0";
        }
        $konsultasi=(getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Ranap Dokter'")+
                     getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Ralan Dokter'"));
        if($konsultasi==""){
            $konsultasi="0";
        }
        $tenaga_ahli=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Ranap Dokter Paramedis'");
        if($tenaga_ahli==""){
            $tenaga_ahli="0";
        }
        $keperawatan=(getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Ranap Paramedis'")+
                      getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Ralan Paramedis'"));
        if($keperawatan==""){
            $keperawatan="0";
        }
        $radiologi=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Radiologi'");
        if($radiologi==""){
            $radiologi="0";
        }
        $laboratorium=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Laborat'");
        if($laboratorium==""){
            $laboratorium="0";
        }
        $kamar=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Kamar'");
        if($kamar==""){
            $kamar="0";
        }
        $obat_kronis=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where nm_perawatan like '%kronis%' and no_rawat='".$no_rawat."' and status='Obat'");
        $obat_kemoterapi=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where nm_perawatan like '%kemo%' and no_rawat='".$no_rawat."' and status='Obat'");
        $obat=(getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Obat'")+
               getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Retur Obat'")+
               getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Resep Pulang'")-$obat_kronis-$obat_kemoterapi);
        if($obat==""){
            $obat="0";
        }        
        if($obat_kemoterapi==""){
            $obat_kemoterapi="0";
        }        
        if($obat_kronis==""){
            $obat_kronis="0";
        }        
        $bmhp=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Tambahan'");
        if($bmhp==""){
            $bmhp="0";
        }
        $sewa_alat=(getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Harian'")+
                    getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$no_rawat."' and status='Service'"));
        if($sewa_alat==""){
            $sewa_alat="0";
        }
        
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
                                "penunjang": "0",
                                "radiologi": "'.$radiologi.'",
                                "laboratorium": "'.$laboratorium.'",
                                "pelayanan_darah": "0",
                                "rehabilitasi": "0",
                                "kamar": "'.($kamar+$tarif_poli_eks).'",
                                "rawat_intensif": "0",
                                "obat": "'.$obat.'",
                                "obat_kronis": "'.$obat_kronis.'",
                                "obat_kemoterapi": "'.$obat_kemoterapi.'",
                                "alkes": "0",
                                "bmhp": "'.$bmhp.'",
                                "sewa_alat": "'.$sewa_alat.'"
                             },
                            "tarif_poli_eks": "0",
                            "nama_dokter": "'.$nama_dokter.'",
                            "kode_tarif": "'.$kode_tarif.'",
                            "payor_id": "'.$payor_id.'",
                            "payor_cd": "'.$payor_cd.'",
                            "cob_cd": "'.$cob_cd.'",
                            "coder_nik": "'.$coder_nik.'"
                        }
                   }';
        //echo "Data : ".$request;
        $msg= Request($request);
        if($msg['metadata']['message']=="Ok"){
            Hapus2("inacbg_data_terkirim", "no_sep='".$nomor_sep."'");
            InsertData2("inacbg_data_terkirim","'".$nomor_sep."','".$coder_nik."'");
            GroupingStage1($nomor_sep,$coder_nik);
        }
    }
    
    function UpdateDataKlaim2($nomor_sep,$nomor_kartu,$tgl_masuk,$tgl_pulang,$jenis_rawat,$kelas_rawat,$adl_sub_acute,
                            $adl_chronic,$icu_indikator,$icu_los,$ventilator_hour,$upgrade_class_ind,$upgrade_class_class,
                            $upgrade_class_los,$add_payment_pct,$birth_weight,$discharge_status,$diagnosa,$procedure,
                            $tarif_poli_eks,$nama_dokter,$kode_tarif,$payor_id,$payor_cd,$cob_cd,$coder_nik,
                            $prosedur_non_bedah,$prosedur_bedah,$konsultasi,$tenaga_ahli,$keperawatan,$penunjang,
                            $radiologi,$laboratorium,$pelayanan_darah,$rehabilitasi,$kamar,$rawat_intensif,$obat,
                            $obat_kronis,$obat_kemoterapi,$alkes,$bmhp,$sewa_alat){	
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
                                "obat_kronis": "'.$obat_kronis.'",
                                "obat_kemoterapi": "'.$obat_kemoterapi.'",
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
        //echo "Data : ".$request;
        $msg= Request($request);
        if($msg['metadata']['message']=="Ok"){
            Hapus2("inacbg_data_terkirim2", "no_sep='".$nomor_sep."'");
            InsertData2("inacbg_data_terkirim2","'".$nomor_sep."','".$coder_nik."'");
            GroupingStage12($nomor_sep,$coder_nik);
        }
    }
    
    function UpdateDataKlaim3($nomor_sep,$nomor_kartu,$tgl_masuk,$tgl_pulang,$jenis_rawat,$kelas_rawat,$adl_sub_acute,
                            $adl_chronic,$icu_indikator,$icu_los,$ventilator_hour,$upgrade_class_ind,$upgrade_class_class,
                            $upgrade_class_los,$add_payment_pct,$birth_weight,$discharge_status,$diagnosa,$procedure,
                            $tarif_poli_eks,$nama_dokter,$kode_tarif,$payor_id,$payor_cd,$cob_cd,$coder_nik,
                            $prosedur_non_bedah,$prosedur_bedah,$konsultasi,$tenaga_ahli,$keperawatan,$penunjang,
                            $radiologi,$laboratorium,$pelayanan_darah,$rehabilitasi,$kamar,$rawat_intensif,$obat,
                            $obat_kronis,$obat_kemoterapi,$alkes,$bmhp,$sewa_alat,$pemulasaraan_jenazah,$kantong_jenazah, 
                            $peti_jenazah,$plastik_erat,$desinfektan_jenazah,$mobil_jenazah,$desinfektan_mobil_jenazah,
                            $covid19_status_cd,$nomor_kartu_t,$episodes,$covid19_cc_ind){	
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
                                "obat_kronis": "'.$obat_kronis.'",
                                "obat_kemoterapi": "'.$obat_kemoterapi.'",
                                "alkes": "'.$alkes.'",
                                "bmhp": "'.$bmhp.'",
                                "sewa_alat": "'.$sewa_alat.'"
                             },
                            "pemulasaraan_jenazah": "'.$pemulasaraan_jenazah.'", 
                            "kantong_jenazah": "'.$kantong_jenazah.'", 
                            "peti_jenazah": "'.$peti_jenazah.'", 
                            "plastik_erat": "'.$plastik_erat.'", 
                            "desinfektan_jenazah": "'.$desinfektan_jenazah.'", 
                            "mobil_jenazah": "'.$mobil_jenazah.'", 
                            "desinfektan_mobil_jenazah": "'.$desinfektan_mobil_jenazah.'", 
                            "covid19_status_cd": "'.$covid19_status_cd.'", 
                            "nomor_kartu_t": "'.$nomor_kartu_t.'", 
                            "episodes": "'.$episodes.'",
                            "covid19_cc_ind": "'.$covid19_cc_ind.'",
                            "tarif_poli_eks": "'.$tarif_poli_eks.'",
                            "nama_dokter": "'.$nama_dokter.'",
                            "kode_tarif": "'.$kode_tarif.'",
                            "payor_id": "'.$payor_id.'",
                            "payor_cd": "'.$payor_cd.'",
                            "cob_cd": "'.$cob_cd.'",
                            "coder_nik": "'.$coder_nik.'"
                        }
                   }';
        //echo "Data : ".$request;
        $msg= Request($request);
        if($msg['metadata']['message']=="Ok"){
            Hapus2("inacbg_data_terkirim2", "no_sep='".$nomor_sep."'");
            InsertData2("inacbg_data_terkirim2","'".$nomor_sep."','".$coder_nik."'");
            GroupingStage13($nomor_sep,$coder_nik);
        }
    }
    
    function UpdateDataProsedur($nomor_sep,$procedure,$coder_nik){	
        $request ='{
                        "metadata": {
                            "method": "set_claim_data",
                            "nomor_sep": "'.$nomor_sep.'",
                        },
                        "data": {
                            "procedure": "'.$procedure.'",
                            "coder_nik": "'.$coder_nik.'"
                        }
                   }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
    function HapusSemuaProsedur($nomor_sep,$coder_nik){	
        $request ='{
                        "metadata": {
                            "method": "set_claim_data",
                            "nomor_sep": "'.$nomor_sep.'",
                        },
                            "data": {
                            "procedure": "#",
                            "coder_nik": "'.$coder_nik.'"
                        }
                   }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
     function GroupingStage1($nomor_sep,$coder_nik){	
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
            Hapus2("inacbg_grouping_stage1", "no_sep='".$nomor_sep."'");
            $cbg                = validangka($msg['response']['cbg']['tariff']);
            $sub_acute          = validangka($msg['response']['sub_acute']['tariff']);
            $chronic            = validangka($msg['response']['chronic']['tariff']);
            $add_payment_amt    = validangka($msg['response']['add_payment_amt']);
            InsertData2("inacbg_grouping_stage1","'".$nomor_sep."','".$msg['response']['cbg']['code']."','".$msg['response']['cbg']['description']."','".($cbg+$sub_acute+$chronic+$add_payment_amt)."'");
            FinalisasiKlaim($nomor_sep,$coder_nik);
        }
    }
    
    function GroupingStage12($nomor_sep,$coder_nik){	
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
            Hapus2("inacbg_grouping_stage12", "no_sep='".$nomor_sep."'");
            $cbg                = validangka($msg['response']['cbg']['tariff']);
            $sub_acute          = validangka($msg['response']['sub_acute']['tariff']);
            $chronic            = validangka($msg['response']['chronic']['tariff']);
            $add_payment_amt    = validangka($msg['response']['add_payment_amt']);
            InsertData2("inacbg_grouping_stage12","'".$nomor_sep."','".$msg['response']['cbg']['code']."','".$msg['response']['cbg']['description']."','".($cbg+$sub_acute+$chronic+$add_payment_amt)."'");
            FinalisasiKlaim($nomor_sep,$coder_nik);
        }
    }
    
    function GroupingStage13($nomor_sep,$coder_nik){	
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
            Hapus2("inacbg_grouping_stage12", "no_sep='".$nomor_sep."'");
            $cbg                = validangka($msg['response']['cbg']['tariff']);
            $sub_acute          = validangka($msg['response']['sub_acute']['tariff']);
            $chronic            = validangka($msg['response']['chronic']['tariff']);
            $add_payment_amt    = validangka($msg['response']['add_payment_amt']);
            InsertData2("inacbg_grouping_stage12","'".$nomor_sep."','".$msg['response']['cbg']['code']."','".$msg['response']['cbg']['description']."','".($cbg+$sub_acute+$chronic+$add_payment_amt+$msg['response']['covid19_data']['episodes'][0]['tariff']+$msg['response']['covid19_data']['episodes'][1]['tariff']+$msg['response']['covid19_data']['episodes'][2]['tariff']+$msg['response']['covid19_data']['episodes'][3]['tariff']+$msg['response']['covid19_data']['episodes'][4]['tariff']+$msg['response']['covid19_data']['episodes'][5]['tariff']+$msg['response']['covid19_data']['pemulasaraan_jenazah']['pemulasaraan']+$msg['response']['covid19_data']['pemulasaraan_jenazah']['kantong']+$msg['response']['covid19_data']['pemulasaraan_jenazah']['peti']+$msg['response']['covid19_data']['pemulasaraan_jenazah']['plastik']+$msg['response']['covid19_data']['pemulasaraan_jenazah']['desinfektan_jenazah']+$msg['response']['covid19_data']['pemulasaraan_jenazah']['mobil']+$msg['response']['covid19_data']['pemulasaraan_jenazah']['desinfektan_mobil']+$msg['response']['covid19_data']['top_up_rawat_gross']+$msg['response']['covid19_data']['top_up_rawat']+$msg['response']['covid19_data']['top_up_jenazah'])."'");
            FinalisasiKlaim($nomor_sep,$coder_nik);
        }
    }
    
    function GroupingStage2($nomor_sep,$special_cmg){	
        $request ='{
                        "metadata": {
                            "method":"grouper",
                            "stage":"2"
                        },
                        "data": {
                            "nomor_sep":"'.$nomor_sep.'",
                            "special_cmg": "'.$special_cmg.'"
                        }
                   }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
    function FinalisasiKlaim($nomor_sep,$coder_nik){	
        $request ='{
                        "metadata": {
                            "method":"claim_final"
                        },
                        "data": {
                            "nomor_sep":"'.$nomor_sep.'",
                            "coder_nik": "'.$coder_nik.'"
                        }
                   }';
        $msg= Request($request);
        if($msg['metadata']['message']=="Ok"){
            //KirimKlaimIndividualKeDC($nomor_sep);
        }
    }
    
    function EditUlangKlaim($nomor_sep){	
        $request ='{
                        "metadata": {
                            "method":"reedit_claim"
                        },
                        "data": {
                            "nomor_sep":"'.$nomor_sep.'"
                        }
                   }';
        $msg= Request($request);
        //echo $msg['metadata']['message']."";
    }
    
    function KirimKlaimPeriodeKeDC($start_dt,$stop_dt,$jenis_rawat){	
        $request ='{
                        "metadata": {
                            "method":"send_claim"
                        },
                        "data": {
                            "start_dt":"'.$start_dt.'",
                            "stop_dt":"'.$stop_dt.'",
                            "jenis_rawat":"'.$jenis_rawat.'",
                            "date_type":"2"
                        }
                   }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
    function KirimKlaimIndividualKeDC($nomor_sep){	
        $request ='{
                        "metadata": {
                            "method":"send_claim_individual"
                        },
                        "data": {
                            "nomor_sep":"'.$nomor_sep.'"
                        }
                   }';
        $msg= Request($request);
        //echo $msg['metadata']['message']."";
    }
    
    function MenarikDataKlaimPeriode($start_dt,$stop_dt,$jenis_rawat){	
        $request ='{
                        "metadata": {
                            "method":"pull_claim"
                        },
                        "data": {
                            "start_dt":"'.$start_dt.'",
                            "stop_dt":"'.$stop_dt.'",
                            "jenis_rawat":"'.$jenis_rawat.'"
                        }
                   }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
    function MengambilDataDetailPerklaim($nomor_sep){	
        $request ='{
                        "metadata": {
                            "method":"get_claim_data"
                        },
                        "data": {
                            "nomor_sep":"'.$nomor_sep.'"
                        }
                   }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
    function MengambilSetatusPerklaim($nomor_sep){	
        $request ='{
                        "metadata": {
                            "method":"get_claim_status"
                        },
                        "data": {
                            "nomor_sep":"'.$nomor_sep.'"
                        }
                   }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
    function MenghapusKlaim($nomor_sep,$coder_nik){	
        $request ='{
                        "metadata": {
                            "method":"delete_claim"
                        },
                        "data": {
                            "nomor_sep":"'.$nomor_sep.'",
                            "coder_nik":"'.$coder_nik.'"
                        }
                  }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
    function CetakKlaim($nomor_sep){	
        $request ='{
                        "metadata": {
                            "method": "claim_print"
                        },
                        "data": {
                            "nomor_sep": "'.$nomor_sep.'"
                        }
                   }';
        $msg= Request($request);
        echo $msg['metadata']['message']."";
    }
    
    function Request($request){
        $json = mc_encrypt ($request, getKey());  
        $header = array("Content-Type: application/x-www-form-urlencoded");        
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, getUrlWS());
        curl_setopt($ch, CURLOPT_HEADER, 0);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($ch, CURLOPT_HTTPHEADER,$header);
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
        $response = curl_exec($ch);
        $first = strpos($response, "\n")+1;
        $last = strrpos($response, "\n")-1;
        $hasilresponse = substr($response,$first,strlen($response) - $first - $last);
        $hasildecrypt = mc_decrypt($hasilresponse, getKey());
        //echo $hasildecrypt;
        $msg = json_decode($hasildecrypt,true);
        return $msg;
    }
?>
