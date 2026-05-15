<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    $nopernyataan  = "";
    $norawat       = "";
    $_sql          = "select * from antripermintaansecondopinion";
    $hasil         = bukaquery2($_sql);
    while ($data = mysqli_fetch_array($hasil)){
        $nopernyataan = $data['no_pernyataan'];
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
               where reg_periksa.no_rawat='".$norawat."'";
    $hasil2 = bukaquery2($_sql2);
    while ($data2 = mysqli_fetch_array($hasil2)){
        $no_rkm_medis = $data2['no_rkm_medis'];
        $nm_pasien    = $data2['nm_pasien'];
        $jk           = $data2['jk'];
        $umur         = $data2['umur'];
        $tgl_lahir    = $data2['tgl_lahir'];
        $alamat       = $data2['alamat'];
        $no_tlp       = $data2['no_tlp'];
    }

    $tanggal                      = "";
    $nm_dokter                    = "";
    $pembuat_pernyataan           = "";
    $alamat_pembuat_pernyataan    = "";
    $tgl_lahir_pembuat_pernyataan = "";
    $jk_pembuat_pernyataan        = "";
    $hubungan_pembuat_pernyataan  = "";
    $saksi_keluarga               = "";
    $_sql2  = "select surat_permintaan_second_opinion.tanggal,dokter.nm_dokter,surat_permintaan_second_opinion.pembuat_pernyataan,surat_permintaan_second_opinion.alamat_pembuat_pernyataan,
               DATE_FORMAT(surat_permintaan_second_opinion.tgl_lahir_pembuat_pernyataan,'%d-%m-%Y') as tgl_lahir_pembuat_pernyataan,if(surat_permintaan_second_opinion.jk_pembuat_pernyataan='L','LAKI-LAKI','PEREMPUAN') as jk_pembuat_pernyataan,
               surat_permintaan_second_opinion.hubungan_pembuat_pernyataan,surat_permintaan_second_opinion.saksi_keluarga from surat_permintaan_second_opinion inner join dokter on surat_permintaan_second_opinion.kd_dokter=dokter.kd_dokter
               where surat_permintaan_second_opinion.no_pernyataan='$nopernyataan'";
    $hasil2 = bukaquery2($_sql2);
    while ($data2 = mysqli_fetch_array($hasil2)){
        $tanggal                      = $data2['tanggal'];
        $nm_dokter                    = $data2['nm_dokter'];
        $pembuat_pernyataan           = $data2['pembuat_pernyataan'];
        $alamat_pembuat_pernyataan    = $data2['alamat_pembuat_pernyataan'];
        $tgl_lahir_pembuat_pernyataan = $data2['tgl_lahir_pembuat_pernyataan'];
        $jk_pembuat_pernyataan        = $data2['jk_pembuat_pernyataan'];
        $hubungan_pembuat_pernyataan  = $data2['hubungan_pembuat_pernyataan'];
        $saksi_keluarga               = $data2['saksi_keluarga'];
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
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>Surat Permintaan Second Opinion<br> No. <?=$nopernyataan;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tanggal?></center></h7><br/>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="nopernyataan" value="<?=$nopernyataan;?>">
            <h7 class="text-dark">
                Yang bertanda tangan di bawah ini :
            </h7>
            <table class="default" width="100%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="30%">Nama</td>
                    <td width="70%">: <?=$pembuat_pernyataan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">Alamat</td>
                    <td width="70%">: <?=$alamat_pembuat_pernyataan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">Tanggal Lahir</td>
                    <td width="70%">: <?=$tgl_lahir_pembuat_pernyataan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">Jenis Kelamin</td>
                    <td width="70%">: <?=$jk_pembuat_pernyataan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">Hubungan Dengan Pasien</td>
                    <td width="70%">: <?=$hubungan_pembuat_pernyataan;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Dengan data pasien sebagai berikut :
            </h7>
            <table class="default" width="100%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="30%">Nama Pasien</td>
                    <td width="70%">: <?=$nm_pasien;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">Nomor Rekam Medis</td>
                    <td width="70%">: <?=$no_rkm_medis;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">Jenis Kelamin</td>
                    <td width="70%">: <?=$jk;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">Umur</td>
                    <td width="70%">: <?=$umur;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">Tanggal Lahir</td>
                    <td width="70%">: <?=$tgl_lahir;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">Alamat</td>
                    <td width="70%">: <?=$alamat;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="30%">No.Telp</td>
                    <td width="70%">: <?=$no_tlp;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Dengan ini mengajukan permintaan second opinion atas kondisi pasien tersebut :
            </h7>
            <table class="default" width="100%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="5%">1.</td>
                    <td width="95%">Permintaan second opinion ini diajukan untuk memperoleh pendapat medis lain atas kondisi pasien di <?=getOne("select setting.nama_instansi from setting");?></td>
                </tr>
                <tr class="text-dark">
                    <td width="5%">2.</td>
                    <td width="95%">Dokter tujuan second opinion yang diajukan adalah <?=$nm_dokter?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Demikian surat permintaan second opinion ini dibuat untuk dipergunakan sebagaimana mestinya.
            </h7>
            <br/><br/>
            <h7 class="text-dark"><center>Pembuat Permintaan</center></h7>
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
                    <input type="button" class="btn btn-warning" value="Ya, Saya sebagai pemohon" onClick="take_snapshot()">
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
