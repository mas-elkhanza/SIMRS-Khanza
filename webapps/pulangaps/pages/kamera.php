<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $namars        = getOne("select setting.nama_instansi from setting");
    $nopernyataan  = "";
    $norawat       = "";
    
    $_sql          = "select * from antriaps" ;  
    $hasil         = bukaquery2($_sql);
    while ($data = mysqli_fetch_array ($hasil)){
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
    
    $tgl_pulang     = "";
    $rs_pilihan     = "";
    $nama_pj        = "";
    $lahir          = "";
    $umur           = "";
    $jkpj           = "";
    $alamat         = "";
    $hubungan       = "";
    $saksi_keluarga = "";
    $_sql2  = "select DATE_FORMAT(surat_pulang_atas_permintaan_sendiri.tgl_pulang,'%d-%m-%Y') as tgl_pulang,surat_pulang_atas_permintaan_sendiri.rs_pilihan,surat_pulang_atas_permintaan_sendiri.nama_pj,
               DATE_FORMAT(surat_pulang_atas_permintaan_sendiri.lahir,'%d-%m-%Y') as lahir,surat_pulang_atas_permintaan_sendiri.umur,if(surat_pulang_atas_permintaan_sendiri.jkpj='L','LAKI-LAKI','PEREMPUAN') as jkpj,
               surat_pulang_atas_permintaan_sendiri.alamat,surat_pulang_atas_permintaan_sendiri.hubungan,surat_pulang_atas_permintaan_sendiri.saksi_keluarga 
               from surat_pulang_atas_permintaan_sendiri where surat_pulang_atas_permintaan_sendiri.no_surat='$nopernyataan'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $tgl_pulang     = $data2['tgl_pulang'];
        $rs_pilihan     = $data2['rs_pilihan'];
        $nama_pj        = $data2['nama_pj'];
        $lahir          = $data2['lahir'];
        $umur           = $data2['umur'];
        $jkpj           = $data2['jkpj'];
        $alamat         = $data2['alamat'];
        $hubungan       = $data2['hubungan'];
        $saksi_keluarga = $data2['saksi_keluarga'];
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
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>Penyataan Pulang Atas Permintaan Sendiri No. <?=$nopernyataan;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tgl_pulang;?></center></h7><br/>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="nopernyataan" value="<?=$nopernyataan;?>">
            <h7 class="text-dark">
                Saya yang membuat pernyataan di bawah ini :
            </h7>
            <table class="default" width="100%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="25%">Nama</td>
                    <td width="70%">: <?=$nama_pj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Jenis Kelamin</td>
                    <td width="75%">: <?=$jkpj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Tanggal Lahir</td>
                    <td width="75%">: <?=$lahir;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Umur</td>
                    <td width="75%">: <?=$umur;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Alamat</td>
                    <td width="75%">: <?=$alamat;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Hubungan Dengan Pasien</td>
                    <td width="75%">: <?=$hubungan;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Terhadap Pasien : 
            </h7>
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
            </table>
            <br/>
            <h7 class="text-dark">
                Dengan ini menyatakan bahwa : 
            </h7>
            <br/>
            <table class="default" width="100%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="2%" valign="top">1.</td>
                    <td width="98%" valign="top" align="justify">Dengan sadar tanpa paksaan dari pihak manapun meminta kepada pihak <?=$namars?> untuk PULANG ATAS PERMINTAAN SENDIRI yang merupakan hak saya/pasien dengan Alasan/Pindah Rawat "<?=$rs_pilihan?>"</td>
                </tr>
                <tr class="text-dark">
                    <td width="2%" valign="top">2.</td>
                    <td width="98%" valign="top" align="justify">Saya telah memahami sepenuhnya penjelasan yang diberikan oleh pihak <?=$namars?> mengenai penyakit saya/pasien dan kemungkinan/konsekuensi terburuk atas keputusan yang saya ambil dan saya siap bertanggung jawab atas keputusan saya tersebut dan saya tidak akan menuntut pihak <?=$namars?>.</td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Demikian pernyataan ini saya buat dengan sesungguhnya untuk dipergunakan sebagaimana mestinya.
            </h7>
            <br/>
            <br/>
            <h7 class="text-dark"><center>Yang Membuat Pernyataan</center></h7>
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
                    <input type="button" class="btn btn-warning" value="Ya, Saya sebagai pembuat pernyataan" onClick="take_snapshot()">
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

