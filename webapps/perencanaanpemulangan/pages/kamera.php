<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $norawat       = "";
    $_sql          = "select * from antripemulangan" ;  
    $hasil         = bukaquery2($_sql);
    while ($data = mysqli_fetch_array ($hasil)){
        $norawat   = $data['no_rawat'];
    }
    
    $no_rkm_medis = "";
    $nm_pasien    = "";
    $jk           = "";
    $umur         = "";
    $tgl_lahir    = "";
    $tgl_masuk    = "";
    $alamat       = "";
    $no_tlp       = "";
    $_sql2  = "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') as tgl_registrasi,
               reg_periksa.jam_reg,pasien.umur,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, 
               pasien.no_tlp from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel
               inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where reg_periksa.no_rawat='".$norawat."'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $no_rkm_medis = $data2['no_rkm_medis'];
        $nm_pasien    = $data2['nm_pasien'];
        $jk           = $data2['jk'];
        $umur         = $data2['umur'];
        $tgl_lahir    = $data2['tgl_lahir'];
        $alamat       = $data2['alamat'];
        $no_tlp       = $data2['no_tlp'];
        $tgl_masuk    = $data2['tgl_registrasi']." ".$data2['jam_reg'];
    }
    
    $rencana_pulang                                 = "";
    $alasan_masuk                                   = "";
    $diagnosa_medis                                 = "";
    $pengaruh_ri_pasien_dan_keluarga                = "";
    $keterangan_pengaruh_ri_pasien_dan_keluarga     = "";
    $pengaruh_ri_pekerjaan_sekolah                  = "";
    $keterangan_pengaruh_ri_pekerjaan_sekolah       = "";
    $pengaruh_ri_keuangan                           = "";
    $keterangan_pengaruh_ri_keuangan                = "";
    $antisipasi_masalah_saat_pulang                 = "";
    $keterangan_antisipasi_masalah_saat_pulang      = "";
    $bantuan_diperlukan_dalam                       = "";
    $keterangan_bantuan_diperlukan_dalam            = "";
    $adakah_yang_membantu_keperluan                 = "";
    $keterangan_adakah_yang_membantu_keperluan      = "";
    $pasien_tinggal_sendiri                         = "";
    $keterangan_pasien_tinggal_sendiri              = "";
    $pasien_menggunakan_peralatan_medis             = "";
    $keterangan_pasien_menggunakan_peralatan_medis  = "";
    $pasien_memerlukan_alat_bantu                   = "";
    $keterangan_pasien_memerlukan_alat_bantu        = "";
    $memerlukan_perawatan_khusus                    = "";
    $keterangan_memerlukan_perawatan_khusus         = "";
    $bermasalah_memenuhi_kebutuhan                  = "";
    $keterangan_bermasalah_memenuhi_kebutuhan       = "";
    $memiliki_nyeri_kronis                          = "";
    $keterangan_memiliki_nyeri_kronis               = "";
    $memerlukan_edukasi_kesehatan                   = "";
    $keterangan_memerlukan_edukasi_kesehatan        = "";
    $memerlukan_keterampilkan_khusus                = "";
    $keterangan_memerlukan_keterampilkan_khusus     = "";
    $nama_pasien_keluarga                           = "";
    $_sql2  = "select DATE_FORMAT(perencanaan_pemulangan.rencana_pulang,'%d-%m-%Y') as rencana_pulang,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis,
               perencanaan_pemulangan.pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.keterangan_pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.pengaruh_ri_pekerjaan_sekolah,
               perencanaan_pemulangan.keterangan_pengaruh_ri_pekerjaan_sekolah,perencanaan_pemulangan.pengaruh_ri_keuangan,perencanaan_pemulangan.keterangan_pengaruh_ri_keuangan,
               perencanaan_pemulangan.antisipasi_masalah_saat_pulang,perencanaan_pemulangan.keterangan_antisipasi_masalah_saat_pulang,perencanaan_pemulangan.bantuan_diperlukan_dalam,
               perencanaan_pemulangan.keterangan_bantuan_diperlukan_dalam,perencanaan_pemulangan.adakah_yang_membantu_keperluan,perencanaan_pemulangan.keterangan_adakah_yang_membantu_keperluan,
               perencanaan_pemulangan.pasien_tinggal_sendiri,perencanaan_pemulangan.keterangan_pasien_tinggal_sendiri,perencanaan_pemulangan.pasien_menggunakan_peralatan_medis,
               perencanaan_pemulangan.keterangan_pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.pasien_memerlukan_alat_bantu,perencanaan_pemulangan.keterangan_pasien_memerlukan_alat_bantu,
               perencanaan_pemulangan.memerlukan_perawatan_khusus,perencanaan_pemulangan.keterangan_memerlukan_perawatan_khusus,perencanaan_pemulangan.bermasalah_memenuhi_kebutuhan,
               perencanaan_pemulangan.keterangan_bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.memiliki_nyeri_kronis,perencanaan_pemulangan.keterangan_memiliki_nyeri_kronis,
               perencanaan_pemulangan.memerlukan_edukasi_kesehatan,perencanaan_pemulangan.keterangan_memerlukan_edukasi_kesehatan,perencanaan_pemulangan.memerlukan_keterampilkan_khusus,
               perencanaan_pemulangan.keterangan_memerlukan_keterampilkan_khusus,perencanaan_pemulangan.nama_pasien_keluarga from perencanaan_pemulangan where perencanaan_pemulangan.no_rawat='$norawat'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $rencana_pulang                                 = $data2["rencana_pulang"];
        $alasan_masuk                                   = $data2["alasan_masuk"];
        $diagnosa_medis                                 = $data2["diagnosa_medis"];
        $pengaruh_ri_pasien_dan_keluarga                = $data2["pengaruh_ri_pasien_dan_keluarga"];
        $keterangan_pengaruh_ri_pasien_dan_keluarga     = $data2["keterangan_pengaruh_ri_pasien_dan_keluarga"];
        $pengaruh_ri_pekerjaan_sekolah                  = $data2["pengaruh_ri_pekerjaan_sekolah"];
        $keterangan_pengaruh_ri_pekerjaan_sekolah       = $data2["keterangan_pengaruh_ri_pekerjaan_sekolah"];
        $pengaruh_ri_keuangan                           = $data2["pengaruh_ri_keuangan"];
        $keterangan_pengaruh_ri_keuangan                = $data2["keterangan_pengaruh_ri_keuangan"];
        $antisipasi_masalah_saat_pulang                 = $data2["antisipasi_masalah_saat_pulang"];
        $keterangan_antisipasi_masalah_saat_pulang      = $data2["keterangan_antisipasi_masalah_saat_pulang"];
        $bantuan_diperlukan_dalam                       = $data2["bantuan_diperlukan_dalam"];
        $keterangan_bantuan_diperlukan_dalam            = $data2["keterangan_bantuan_diperlukan_dalam"];
        $adakah_yang_membantu_keperluan                 = $data2["adakah_yang_membantu_keperluan"];
        $keterangan_adakah_yang_membantu_keperluan      = $data2["keterangan_adakah_yang_membantu_keperluan"];
        $pasien_tinggal_sendiri                         = $data2["pasien_tinggal_sendiri"];
        $keterangan_pasien_tinggal_sendiri              = $data2["keterangan_pasien_tinggal_sendiri"];
        $pasien_menggunakan_peralatan_medis             = $data2["pasien_menggunakan_peralatan_medis"];
        $keterangan_pasien_menggunakan_peralatan_medis  = $data2["keterangan_pasien_menggunakan_peralatan_medis"];
        $pasien_memerlukan_alat_bantu                   = $data2["pasien_memerlukan_alat_bantu"];
        $keterangan_pasien_memerlukan_alat_bantu        = $data2["keterangan_pasien_memerlukan_alat_bantu"];
        $memerlukan_perawatan_khusus                    = $data2["memerlukan_perawatan_khusus"];
        $keterangan_memerlukan_perawatan_khusus         = $data2["keterangan_memerlukan_perawatan_khusus"];
        $bermasalah_memenuhi_kebutuhan                  = $data2["bermasalah_memenuhi_kebutuhan"];
        $keterangan_bermasalah_memenuhi_kebutuhan       = $data2["keterangan_bermasalah_memenuhi_kebutuhan"];
        $memiliki_nyeri_kronis                          = $data2["memiliki_nyeri_kronis"];
        $keterangan_memiliki_nyeri_kronis               = $data2["keterangan_memiliki_nyeri_kronis"];
        $memerlukan_edukasi_kesehatan                   = $data2["memerlukan_edukasi_kesehatan"];
        $keterangan_memerlukan_edukasi_kesehatan        = $data2["keterangan_memerlukan_edukasi_kesehatan"];
        $memerlukan_keterampilkan_khusus                = $data2["memerlukan_keterampilkan_khusus"];
        $keterangan_memerlukan_keterampilkan_khusus     = $data2["keterangan_memerlukan_keterampilkan_khusus"];
        $nama_pasien_keluarga                           = $data2["nama_pasien_keluarga"];
    }   
?>

<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <title>SIMKES Khanza</title>
    <script src="js/jquery.min.js"></script>
    <script src="js/webcam.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <style type="text/css">
        #results { padding: 0px; background:#EEFFEE; width: 490; height: 390 }
    </style>
</head>
<body>
    <div class="container">
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>Rencana Pemulangan Pasien No.Rawat <?=$norawat;?></center></h5>
        <br/>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="norawat" value="<?=$norawat;?>">
            <table class="default" width="100%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="25%">Nama Pasien</td>
                    <td width="70%">: <?=$nm_pasien;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Rekam Medis</td>
                    <td width="75%">: <?=$no_rkm_medis;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Jenis Kelamin</td>
                    <td width="75%">: <?=$jk;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Tanggal Lahir</td>
                    <td width="75%">: <?=$tgl_lahir;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Masuk Dirawat</td>
                    <td width="75%">: <?=$tgl_masuk;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Diagnosa Medis</td>
                    <td width="75%">: <?=$diagnosa_medis;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Alasan Masuk / Dirawat</td>
                    <td width="75%">: <?=$alasan_masuk;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Rencana Pemulangan</td>
                    <td width="75%">: <?=$rencana_pulang;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2">&nbsp;</td>
                </tr>
                <tr class="text-dark" align="justify">
                    <td width="100%" colspan="2" align="justify">1. Pengaruh Rawat Inap Terhadap :</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;">Pasien & Keluarga Pasien  : <?=$pengaruh_ri_pasien_dan_keluarga.($keterangan_pengaruh_ri_pasien_dan_keluarga==""?"":", ".$keterangan_pengaruh_ri_pasien_dan_keluarga)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;">Pekerjaan / Sekolah : <?=$pengaruh_ri_pekerjaan_sekolah.($keterangan_pengaruh_ri_pekerjaan_sekolah==""?"":", ".$keterangan_pengaruh_ri_pekerjaan_sekolah)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;">Keuangan : <?=$pengaruh_ri_keuangan.($keterangan_pengaruh_ri_keuangan==""?"":", ".$keterangan_pengaruh_ri_keuangan)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">2. Antisipasi Terhadap Masalah Saat Pulang ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$antisipasi_masalah_saat_pulang.($keterangan_antisipasi_masalah_saat_pulang==""?"":", ".$keterangan_antisipasi_masalah_saat_pulang)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">3. Bantuan Diperlukan Dalam Hal ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$bantuan_diperlukan_dalam.($keterangan_bantuan_diperlukan_dalam==""?"":", ".$keterangan_bantuan_diperlukan_dalam)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">4. Adakah Yang Membantu Keperluan Di Atas ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$adakah_yang_membantu_keperluan.($keterangan_adakah_yang_membantu_keperluan==""?"":", ".$keterangan_adakah_yang_membantu_keperluan)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">5. Apakah Pasien Tinggal Sendiri Setelah Keluar Dari Rumah Sakit ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$pasien_tinggal_sendiri.($keterangan_pasien_tinggal_sendiri==""?"":", ".$keterangan_pasien_tinggal_sendiri)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">6. Apakah Pasien Menggunakan Peralatan Medis (Kateter, NGT, Oksigen, Dll) Di Rumah Setelah Keluar / Pulang ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$pasien_menggunakan_peralatan_medis.($keterangan_pasien_menggunakan_peralatan_medis==""?"":", ".$keterangan_pasien_menggunakan_peralatan_medis)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">7. Apakah Pasien Memerlukan Alat Bantu (Tongkat, Kursi Roda, Walker, Dll) Setelah Keluar Keluar / Pulang ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$pasien_memerlukan_alat_bantu.($keterangan_pasien_memerlukan_alat_bantu==""?"":", ".$keterangan_pasien_memerlukan_alat_bantu)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">8. Apakah Memerlukan Bantuan / Perawatan Khusus (Homecare, Home Visit) Di Rumah Setelah Keluar / Pulang ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$memerlukan_perawatan_khusus.($keterangan_memerlukan_perawatan_khusus==""?"":", ".$keterangan_memerlukan_perawatan_khusus)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">9. Apakah Pasien Bermasalah Dalam Memenuhi Kebutuhan Pribadinya (Makan, Minum, BAK, BAB, Dll) Setelah Keluar / Pulang ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$bermasalah_memenuhi_kebutuhan.($keterangan_bermasalah_memenuhi_kebutuhan==""?"":", ".$keterangan_bermasalah_memenuhi_kebutuhan)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">10. Apakah Pasien Memiliki Nyeri Kronis Dan Kelelahan Setelah Keluar / Pulang ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$memiliki_nyeri_kronis.($keterangan_memiliki_nyeri_kronis==""?"":", ".$keterangan_memiliki_nyeri_kronis)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">11. Apakah Pasien & Keluarga Memerlukan Edukasi Kesehatan (Obatan-obatan, Efek Samping Obat, Nyeri Diit, Mencari Pertolongan, Follow Up, Dll) Setelah Keluar / Pulang ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$memerlukan_edukasi_kesehatan.($keterangan_memerlukan_edukasi_kesehatan==""?"":", ".$keterangan_memerlukan_edukasi_kesehatan)?></p></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify">12. Apakah Pasien Dan Keluarga Memerlukan Keterampilan Khusus (Perawatan Luka, Injeksi, Perawatan Bayi, Dll) Setelah Keluar / Pulang ?</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan="2" align="justify"><p style = "margin-left: 15px;height: 4px;"><?=$memerlukan_keterampilkan_khusus.($keterangan_memerlukan_keterampilkan_khusus==""?"":", ".$keterangan_memerlukan_keterampilkan_khusus)?></p></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark"><center>Pasien/Keluarga<br/><?=$nama_pasien_keluarga?></center></h7>
            <div class="row">
                <div class="col-md-6">
                    <div id="my_camera"></div>
                    <input type="hidden" name="image" class="image-tag" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                </div>
                <div class="col-md-6">
                    <div id="results"><h7 class="text-success"><center>Gambar akan diambil jika anda sudah mengeklik ya</center></h7></div>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </div>
                <div class="col-md-12 text-center">
                    <br>
                    <input type="button" class="btn btn-warning" value="Ya, Saya sebagai Pasien/Keluarga Pasien" onClick="take_snapshot()">
                    <button class="btn btn-danger">Simpan</button>
                </div>
            </div>
        </form>
    </div>
    
    <script language="JavaScript">
        Webcam.set({
            width: 490,
            height: 390,
            image_format: 'jpeg',
            jpeg_quality: 90
        });

        Webcam.attach( '#my_camera' );

        function take_snapshot() {
            Webcam.snap( function(data_uri) {
                $(".image-tag").val(data_uri);
                document.getElementById('results').innerHTML = '<img src="'+data_uri+'"/>';
            } );
        }
    </script>
</body>
</html>

