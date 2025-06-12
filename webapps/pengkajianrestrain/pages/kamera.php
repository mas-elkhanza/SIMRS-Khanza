<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $namars        = getOne("select setting.nama_instansi from setting");
    $norawat       = "";
    
    $_sql          = "select * from antripengkajianrestrain" ;  
    $hasil         = bukaquery2($_sql);
    while ($data = mysqli_fetch_array ($hasil)){
        $norawat      = $data['no_rawat'];
    }
    
    $no_rkm_medis = "";
    $nm_pasien    = "";
    $jk           = "";
    $umur         = "";
    $tgl_lahir    = "";
    $alamat       = "";
    $no_tlp       = "";
    
    $_sql2  = "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,
               pasien.umur,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, 
               pasien.no_tlp from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
               inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel
               inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec 
               inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab
               where reg_periksa.no_rawat='".$norawat."'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $no_rkm_medis = $data2['no_rkm_medis'];
        $nm_pasien    = $data2['nm_pasien'];
        $jk           = $data2['jk'];
        $umur         = $data2['umur'];
        $tgl_lahir    = $data2['tgl_lahir'];
        $alamat       = $data2['alamat'];
        $no_tlp       = $data2['no_tlp'];
    }
    
    $tanggal                    = "";
    $gcs                        = "";    
    $refleka_cahaya_ka          = "";    
    $refleka_cahaya_ki          = "";    
    $ukuran_pupil_ka            = "";    
    $ukuran_pupil_ki            = "";    
    $td                         = "";    
    $suhu                       = "";    
    $rr                         = "";    
    $nadi                       = "";    
    $hasil_observasi            = "";    
    $pertimbangan_klinis        = "";    
    $restrain_non_farmakologi   = "";    
    $restrain_non_farmakologi_keterangan = "";    
    $restrain_farmakologi       = "";    
    $sudah_dijelaskan_keluarga  = "";    
    $keluarga_yang_menyetujui   ="";
    
    $_sql2  = "select DATE_FORMAT(pengkajian_restrain.tanggal,'%d-%m-%Y %H:%i:%s') as tanggal,pengkajian_restrain.gcs,pengkajian_restrain.refleka_cahaya_ka,
               pengkajian_restrain.refleka_cahaya_ki,pengkajian_restrain.ukuran_pupil_ka,pengkajian_restrain.ukuran_pupil_ki,pengkajian_restrain.td,
               pengkajian_restrain.suhu,pengkajian_restrain.rr,pengkajian_restrain.nadi,pengkajian_restrain.hasil_observasi,
               pengkajian_restrain.pertimbangan_klinis,pengkajian_restrain.restrain_non_farmakologi,pengkajian_restrain.restrain_non_farmakologi_keterangan,
               pengkajian_restrain.restrain_farmakologi,pengkajian_restrain.sudah_dijelaskan_keluarga,pengkajian_restrain.keluarga_yang_menyetujui
               from pengkajian_restrain where pengkajian_restrain.no_rawat='$norawat'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $tanggal                    = $data2['tanggal'];
        $gcs                        = $data2['gcs'];    
        $refleka_cahaya_ka          = $data2['refleka_cahaya_ka'];    
        $refleka_cahaya_ki          = $data2['refleka_cahaya_ki'];    
        $ukuran_pupil_ka            = $data2['ukuran_pupil_ka'];    
        $ukuran_pupil_ki            = $data2['ukuran_pupil_ki'];    
        $td                         = $data2['td'];    
        $suhu                       = $data2['suhu'];    
        $rr                         = $data2['rr'];    
        $nadi                       = $data2['nadi'];    
        $hasil_observasi            = $data2['hasil_observasi'];    
        $pertimbangan_klinis        = $data2['pertimbangan_klinis'];    
        $restrain_non_farmakologi   = $data2['restrain_non_farmakologi'];    
        $restrain_non_farmakologi_keterangan = $data2['restrain_non_farmakologi_keterangan'];    
        $restrain_farmakologi       = $data2['restrain_farmakologi'];    
        $sudah_dijelaskan_keluarga  = $data2['sudah_dijelaskan_keluarga'];    
        $keluarga_yang_menyetujui   = $data2['keluarga_yang_menyetujui'];
    }
?>

<!DOCTYPE html>
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
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>PERSETUJUAN RESTRAIN NO.RAWAT <?=$norawat;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tanggal;?></center></h7><br/>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="norawat" value="<?=$norawat;?>">
            <h7 class="text-dark">
                Terhadap Pasien : 
            </h7>
            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
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
            </table>
            <br/>
            <h7 class="text-dark">
                Pengkajian Restrain : 
            </h7>
            <table class="default" width="98%" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="100%">
                        Pengkajian Fisik & Mental :
                        <table class="default" width="98%" align="center" cellpadding="3px" cellspacing="0px">
                            <tr class="text-dark">
                                <td width="25%">a. Kesadaran </td>
                                <td width="75%">
                                    GCS : <?=$gcs;?>&nbsp;&nbsp;&nbsp;
                                    Refleka Cahaya Kanan : <?=$refleka_cahaya_ka;?>&nbsp;&nbsp;&nbsp;
                                    Refleka Cahaya Kiri : <?=$refleka_cahaya_ki;?>&nbsp;&nbsp;&nbsp;
                                    Ukuran Pupil Kanan : <?=$ukuran_pupil_ka;?>&nbsp;&nbsp;&nbsp;
                                    Ukuran Pupil Kiri : <?=$ukuran_pupil_ki;?>
                                </td>
                            </tr>
                            <tr class="text-dark">
                                <td width="25%">b. Tanda-tanda Vital</td>
                                <td width="75%">
                                    Tensi Darah <?=$td;?> mmHg&nbsp;&nbsp;&nbsp;
                                    Suhu : <?=$suhu;?> °C&nbsp;&nbsp;&nbsp;
                                    Nadi : <?=$nadi;?> x/menit&nbsp;&nbsp;&nbsp;
                                    RR : <?=$rr;?> x/menit
                                </td>
                            </tr>
                            <tr class="text-dark">
                                <td width="25%">c. Hasil Observasi</td>
                                <td width="75%"><?=$hasil_observasi;?></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr class="text-dark">
                    <td width="100%">
                        Penilaian & Order Dokter :
                        <table class="default" width="98%" align="center" cellpadding="3px" cellspacing="0px">
                            <tr class="text-dark">
                                <td width="25%">Restrain Non Farmakologi</td>
                                <td width="75%">: <?=$restrain_non_farmakologi.($restrain_non_farmakologi_keterangan==""?"":", ".$restrain_non_farmakologi_keterangan);?></td>
                            </tr>
                            <tr class="text-dark">
                                <td width="25%">Restrain Farmakologi</td>
                                <td width="75%">: <?=$restrain_farmakologi;?></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr class="text-dark">
                    <td width="100%">Pertimbangan Klinis : <?=$pertimbangan_klinis;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Pendidikan Restrain Pada Keluarga :
            </h7>
            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="40%">Keluarga Yang Menyetujui</td>
                    <td width="60%">: <?=$keluarga_yang_menyetujui;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="40%">Keluarga Sudah Dijelaskan Tentang Restrain</td>
                    <td width="50%">: <?=$sudah_dijelaskan_keluarga;?></td>
                </tr>
            </table>
            <br/>
            <br/>
            <h7 class="text-dark"><center>Keluarga/Saudara Yang Menyetujui</center></h7>
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
                    <input type="button" class="btn btn-warning" value="Ya, Saya sebagai yang menyetujui" onClick="take_snapshot()">
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

