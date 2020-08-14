<?php
    require_once('conf/conf.php');
    $nopermintaan = isset($_GET["nopermintaan"])?$_GET["nopermintaan"]:NULL;
    $session      = curl_init ( "api.terassekawanbersama.co.id/add_order/" );			
    $arrheader    = array (
        'x-cid:mintavendor',
        'x-user:mintavendor',
        'x-secret:mintavendor',
        'x-token:mintavendor',
        'Content-Type:text/html; charset=UTF-8'
    );
  
    $json         = "";
    $json2        = "";
    $qrypermintaan  = bukaquery("select permintaan_lab.noorder,permintaan_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,permintaan_lab.tgl_permintaan,
        if(permintaan_lab.jam_permintaan='00:00:00','',permintaan_lab.jam_permintaan) as jam_permintaan,pasien.tgl_lahir,pasien.jk,pasien.alamat,pasien.no_ktp,
        if(permintaan_lab.tgl_sampel='0000-00-00','',permintaan_lab.tgl_sampel) as tgl_sampel,if(permintaan_lab.jam_sampel='00:00:00','',permintaan_lab.jam_sampel) as jam_sampel,
        if(permintaan_lab.tgl_hasil='0000-00-00','',permintaan_lab.tgl_hasil) as tgl_hasil,if(permintaan_lab.jam_hasil='00:00:00','',permintaan_lab.jam_hasil) as jam_hasil,
        permintaan_lab.dokter_perujuk,dokter.nm_dokter,poliklinik.nm_poli,pasien.no_tlp,penjab.png_jawab,pasien.no_peserta,pasien.jk,pasien.tgl_lahir,
        YEAR(FROM_DAYS(DATEDIFF(NOW(),pasien.tgl_lahir))) as tahun,MONTH(FROM_DAYS(DATEDIFF(NOW(),pasien.tgl_lahir))) as bulan,DAY(FROM_DAYS(DATEDIFF(NOW(),pasien.tgl_lahir))) as hari, 
        pasien.alamat,pasien.no_tlp,permintaan_lab.status,reg_periksa.kd_poli,permintaan_lab.diagnosa_klinis,permintaan_lab.informasi_tambahan 
        from permintaan_lab inner join reg_periksa inner join pasien inner join dokter inner join poliklinik inner join penjab
        on permintaan_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj 
        and permintaan_lab.dokter_perujuk=dokter.kd_dokter and reg_periksa.kd_poli=poliklinik.kd_poli where permintaan_lab.noorder='$nopermintaan'");
    while ($rsqrypermintaan = mysqli_fetch_array ($qrypermintaan)){
        $qrydetailpermintaan = bukaquery("select permintaan_detail_permintaan_lab.id_template,template_laboratorium.Pemeriksaan,
            template_laboratorium.urut from permintaan_detail_permintaan_lab 
            inner join template_laboratorium on permintaan_detail_permintaan_lab.id_template=template_laboratorium.id_template 
            where permintaan_detail_permintaan_lab.noorder='$nopermintaan' order by template_laboratorium.kd_jenis_prw,template_laboratorium.urut desc");
        while ($rsqrydetailpermintaan = mysqli_fetch_array ($qrydetailpermintaan)){
            $json2 = '{"id_pemeriksaan": '.$rsqrydetailpermintaan['id_template'].',"status": "add"},'.$json2;
        }
        $json2 = substr_replace($json2 ,"",-1);
        $json  = '{
                    "data_pasien": {
                        "no_rekam": "'.$rsqrypermintaan['no_rkm_medis'].'",
                        "no_ref": "'.$rsqrypermintaan['no_ktp'].'",
                        "no_bpjs": "'.$rsqrypermintaan['no_peserta'].'",
                        "sebutan": "'.($rsqrypermintaan['jk']=="L"?"Tn":"Ny").'",
                        "nama_pasien": "'.$rsqrypermintaan['nm_pasien'].'",
                        "jenis_kelamin": "'.($rsqrypermintaan['jk']=="L"?"0":"1").'",
                        "tgl_lahir": "'.$rsqrypermintaan['tgl_lahir'].'",
                        "y": '.$rsqrypermintaan['tahun'].',
                        "m": '.$rsqrypermintaan['bulan'].',
                        "d": '.$rsqrypermintaan['hari'].',
                        "jam": 0,
                        "alamat": "'.$rsqrypermintaan['alamat'].'",
                        "telp": "'.$rsqrypermintaan['no_tlp'].'"
                    },
                    "data_order": {
                        "status_pasien": "'.($rsqrypermintaan['status']=="ralan"?"0":"1").'",
                        "ruang": "'.$rsqrypermintaan['kd_poli'].'",
                        "dokter_pengirim": "'.$rsqrypermintaan['dokter_perujuk'].'",
                        "dokter_pk": "'.getOne("select kd_dokterlab from set_pjlab").'",
                        "bahasa": "id",
                        "diagnosa": "'.$rsqrypermintaan['diagnosa_klinis'].'",
                        "cito": '.(strpos(strtolower($rsqrypermintaan['informasi_tambahan']),'cito')===false?"0":"1").'
                    },
                    "pemeriksaan":['.$json2.'],
                    "no_lab": "'.str_replace("PL","",$rsqrypermintaan['noorder']).'"
                }';
    }
    
    echo $json;
    curl_setopt ( $session, CURLOPT_URL, "api.terassekawanbersama.co.id/add_order/");
    curl_setopt ( $session, CURLOPT_HTTPHEADER, $arrheader );
    curl_setopt ( $session, CURLOPT_VERBOSE, true );
    curl_setopt ( $session, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt ( $session, CURLOPT_SSL_VERIFYHOST, false);
    curl_setopt ( $session, CURLOPT_POST, true );
    curl_setopt ( $session, CURLOPT_POSTFIELDS, $json);
    curl_setopt ( $session, CURLOPT_HTTPAUTH, CURLAUTH_BASIC); 
    curl_setopt ( $session, CURLOPT_RETURNTRANSFER, TRUE );
    $response = curl_exec ( $session );
    if(strpos(strtolower($response),'created')===true){
        echo"<meta http-equiv='refresh' content='1;URL=?aksi=SuksesKirim'>";
    }
 ?>