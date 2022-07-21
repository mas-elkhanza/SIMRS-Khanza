<?php
    $noresep  = "";
    $norawat  = "";
    $_sql     = "select * from antriapotek3" ;  
    $hasil    = bukaquery2($_sql);
    while ($data = mysqli_fetch_array ($hasil)){
        $noresep  = $data['no_resep'];
        $norawat  = $data['no_rawat'];
    }
    
    $no_rkm_medis = "";
    $nm_pasien    = "";
    $jk           = "";
    $umur         = "";
    $tgl_lahir    = "";
    $alamat       = "";
    $no_tlp       = "";
    $_sql2  = "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,
               pasien.umur,pasien.tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, 
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
        <h5 class="text-danger"><center>Penyerahan Resep Obat Rawat Jalan <?=$noresep;?></center></h5>
        <table class="default" width="100%">
            <tr class="text-danger">
                <td width="15%">Nomor Rawat</td>
                <td width="35%">: <?=$norawat;?></td>
                <td width="15%">Umur Pasien</td>
                <td width="35%">: <?=$umur;?></td>
            </tr>
            <tr class="text-danger">
                <td width="15%">Nomor R.M.</td>
                <td width="35%">: <?=$no_rkm_medis;?></td>
                <td width="15%">Tanggal Lahir</td>
                <td width="35%">: <?=$tgl_lahir;?></td>
            </tr>
            <tr class="text-danger">
                <td width="15%">Nama Pasien</td>
                <td width="35%">: <?=$nm_pasien;?></td>
                <td width="15%">Alamat</td>
                <td width="35%">: <?=$alamat;?></td>
            </tr>
            <tr class="text-danger">
                <td width="15%">Jenis Kelamin</td>
                <td width="35%">: <?=$jk;?></td>
                <td width="15%">No.HP/Telp</td>
                <td width="35%">: <?=$no_tlp;?></td>
            </tr>
        </table>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <div class="row">
                <div class="col-md-6">
                    <div id="my_camera"></div>
                    <input type="hidden" name="image" class="image-tag" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                </div>
                <div class="col-md-6">
                    <div id="results"><h7 class="text-success"><center>Gambar akan diambil jika anda sudah mengerti</center></h7></div>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </div>
                <div class="col-md-12 text-center">
                    <br>
                    <input type="button" class="btn btn-warning" value="Ya, Saya mengerti" onClick="take_snapshot()">
                    <button class="btn btn-danger">Simpan</button>
                    <button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button>
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

