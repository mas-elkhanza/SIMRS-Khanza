<?php
    require_once '../conf/wsinacbg2.php';
    require_once '../../conf/conf.php';
    $prosedur           = "";
    $penyakit           = isset($_GET["penyakit"])?$_GET["penyakit"]:NULL;
    $discharge_status   = "5";               
    $norawat            = isset($_GET["norawat"])?$_GET["norawat"]:NULL;
    $_sql               = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                            reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.no_peserta,
                            pasien.umur,pasien.tgl_lahir,poliklinik.nm_poli,reg_periksa.status_lanjut,reg_periksa.umurdaftar,reg_periksa.sttsumur,
                            reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
                            from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab 
                            on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                            and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='$norawat' ";
    $hasil              = bukaquery($_sql);
    $baris              = mysqli_fetch_array($hasil);
    $no_rkm_medis       = $baris["no_rkm_medis"];
    $nm_pasien          = $baris["nm_pasien"];
    $umurdaftar         = $baris["umurdaftar"];
    $sttsumur           = $baris["sttsumur"];
    $tgl_lahir          = $baris["tgl_lahir"];
    $jk                 = $baris["jk"];
    $almt_pj            = $baris["almt_pj"];
    $tgl_registrasi     = $baris["tgl_registrasi"];
    $jam_reg            = $baris["jam_reg"];
    $nm_poli            = $baris["nm_poli"];
    $no_peserta         = $baris["no_peserta"];
    $nm_dokter          = getOne("select dokter.nm_dokter from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='".$norawat."'");
    $nm_dokter2         = getOne("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='".$norawat."'");
    if(!empty($nm_dokter2)){
        $nm_dokter=$nm_dokter2;
    }
    $gender            = "";
    if($jk == "L"){
        $gender        = "1";
    }else{
        $gender        = "2";
    }
    BuatKlaimBaru($no_peserta,$norawat,$no_rkm_medis,$nm_pasien,$tgl_lahir." 00:00:00", $gender);
    EditUlangKlaim($norawat);
    UpdateDataKlaim($norawat,$no_peserta,$tgl_registrasi,$tgl_registrasi,"1","Kelas 2","","","","","","","","","","0",$discharge_status,$penyakit,$prosedur,getOne("select biaya_reg from reg_periksa where no_rawat='".$norawat."'"), $nm_dokter,getKelasRS(),"","","#",getOne("select no_ik from inacbg_coder_nik"),$norawat);
?>
