<?php
    if(!defined('ABSPATH')){ 
        require_once(__DIR__.'/../conf/conf.php');
    }
    
    if(session_status() == PHP_SESSION_NONE){
        session_start();
    }
    
    $halaman    = isset($_GET["act"])?$_GET["act"]:NULL;
    $kdDokter   = validTeks4(encrypt_decrypt($_SESSION["ses_dokter"], "d"), 20);
    $activeMenuDokter  = ($halaman == "KonsultasiDokter") ? "class='active'" : "";
    $activeMenuPerawat = ($halaman == "KonsultasiPerawat") ? "class='active'" : "";
    $activeMenuRadiologi = ($halaman == "HasilRadiologi") ? "class='active'" : "";
    $datakonsul=0;
    if (getOne("SELECT count(konsultasi_medik.no_permintaan) FROM konsultasi_medik WHERE NOT EXISTS (SELECT 1 FROM jawaban_konsultasi_medik WHERE jawaban_konsultasi_medik.no_permintaan = konsultasi_medik.no_permintaan) AND konsultasi_medik.kd_dokter_dikonsuli='".$kdDokter."'")>0){
        echo "<li $activeMenuDokter>
                <a href=\"index.php?act=KonsultasiDokter\">
                    <i class=\"material-icons\">medical_services</i>
                    <span>Konsultasi Dokter</span>
                </a>
              </li>";
        $datakonsul++;
    }
    
    if (getOne("SELECT count(konsultasi_perawat.no_permintaan) FROM konsultasi_perawat WHERE NOT EXISTS (SELECT 1 FROM jawaban_konsultasi_perawat WHERE jawaban_konsultasi_perawat.no_permintaan = konsultasi_perawat.no_permintaan) AND konsultasi_perawat.kd_dokter_dikonsuli='".$kdDokter."'")>0){
        echo "<li $activeMenuPerawat>
                <a href=\"index.php?act=KonsultasiPerawat\">
                    <i class=\"material-icons\">book</i>
                    <span>Konsultasi Perawat</span>
                </a>
              </li>";
        $datakonsul++;
    }

    if (getOne("SELECT count(periksa_radiologi.no_rawat) FROM periksa_radiologi WHERE NOT EXISTS (SELECT 1 FROM hasil_radiologi WHERE hasil_radiologi.no_rawat=periksa_radiologi.no_rawat AND hasil_radiologi.tgl_periksa=periksa_radiologi.tgl_periksa AND hasil_radiologi.jam=periksa_radiologi.jam) AND periksa_radiologi.kd_dokter='".$kdDokter."'")>0){
        echo "<li $activeMenuRadiologi>
                <a href=\"index.php?act=HasilRadiologi\">
                    <i class=\"material-icons\">collections_bookmark</i>
                    <span>Bacaan Radiologi</span>
                </a>
              </li>";
        $datakonsul++;
    }
    
    if($datakonsul>0){
        echo "<audio autoplay='true' src='pages/bell.wav'></audio>";
    }
?>