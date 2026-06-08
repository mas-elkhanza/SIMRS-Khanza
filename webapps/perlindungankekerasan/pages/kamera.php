<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    $namars         = getOne("select setting.nama_instansi from setting");
    $no_permintaan  = "";
    $norawat        = "";

    $_sql          = "select * from antriperlindungandarikekerasan";
    $hasil         = bukaquery2($_sql);
    while ($data = mysqli_fetch_array($hasil)){
        $no_permintaan = isset($data['no_permintaan']) ? $data['no_permintaan'] : "";
        $norawat      = isset($data['no_rawat']) ? $data['no_rawat'] : "";
    }
    $no_rkm_medis = "";
    $nm_pasien    = "";
    $jk           = "";
    $umur         = "";
    $tgl_lahir    = "";
    $alamat       = "";
    $no_tlp       = "";

    $_sql2  = "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,
               concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir,
               concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,
               pasien.no_tlp from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis
               inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec
               inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where reg_periksa.no_rawat='".$norawat."'" ;
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array($hasil2)){
        $no_rkm_medis = $data2['no_rkm_medis'];
        $nm_pasien    = $data2['nm_pasien'];
        $jk           = $data2['jk'];
        $umur         = $data2['umur'];
        $tgl_lahir    = $data2['tgl_lahir'];
        $alamat       = $data2['alamat'];
        $no_tlp       = $data2['no_tlp'];
    }

    $tanggal        = "";
    $nama_pj        = "";
    $no_ktppj       = "";
    $tempat_lahirpj = "";
    $lahirpj        = "";
    $jkpj           = "";
    $alamatpj       = "";
    $hubungan       = "";
    $no_telppj      = "";
    $_sql2  = "select surat_perlindungan_dari_kekerasan.no_surat,DATE_FORMAT(surat_perlindungan_dari_kekerasan.tanggal,'%d-%m-%Y') as tanggal,
               surat_perlindungan_dari_kekerasan.nama_pj,surat_perlindungan_dari_kekerasan.no_ktppj,surat_perlindungan_dari_kekerasan.tempat_lahirpj,
               DATE_FORMAT(surat_perlindungan_dari_kekerasan.lahirpj,'%d-%m-%Y') as lahirpj,surat_perlindungan_dari_kekerasan.alamatpj,
               surat_perlindungan_dari_kekerasan.hubungan,surat_perlindungan_dari_kekerasan.no_telp,if(surat_perlindungan_dari_kekerasan.jkpj='L','LAKI-LAKI','PEREMPUAN') as jkpj
               from surat_perlindungan_dari_kekerasan where surat_perlindungan_dari_kekerasan.no_surat='$no_permintaan'" ;
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array($hasil2)){
        $tanggal        = $data2['tanggal'];
        $nama_pj        = $data2['nama_pj'];
        $no_ktppj       = $data2['no_ktppj'];
        $tempat_lahirpj = $data2['tempat_lahirpj'];
        $lahirpj        = $data2['lahirpj'];
        $jkpj           = $data2['jkpj'];
        $alamatpj       = $data2['alamatpj'];
        $hubungan       = $data2['hubungan'];
        $no_telppj      = $data2['no_telp'];
    }
    
    if(($nama_pj=="")&&($norawat!="")){
        $_sql2  = "select surat_perlindungan_dari_kekerasan.no_surat,DATE_FORMAT(surat_perlindungan_dari_kekerasan.tanggal,'%d-%m-%Y') as tanggal,
                   surat_perlindungan_dari_kekerasan.nama_pj,surat_perlindungan_dari_kekerasan.no_ktppj,surat_perlindungan_dari_kekerasan.tempat_lahirpj,
                   DATE_FORMAT(surat_perlindungan_dari_kekerasan.lahirpj,'%d-%m-%Y') as lahirpj,surat_perlindungan_dari_kekerasan.alamatpj,
                   surat_perlindungan_dari_kekerasan.hubungan,surat_perlindungan_dari_kekerasan.no_telp,
                   if(surat_perlindungan_dari_kekerasan.jkpj='L','LAKI-LAKI','PEREMPUAN') as jkpj from surat_perlindungan_dari_kekerasan
                   where surat_perlindungan_dari_kekerasan.no_rawat='$norawat' order by surat_perlindungan_dari_kekerasan.tanggal desc limit 1" ;
        $hasil2 = bukaquery2($_sql2);
        while ($data2  = mysqli_fetch_array($hasil2)){
            $tanggal        = $data2['tanggal'];
            $nama_pj        = $data2['nama_pj'];
            $no_ktppj       = $data2['no_ktppj'];
            $tempat_lahirpj = $data2['tempat_lahirpj'];
            $lahirpj        = $data2['lahirpj'];
            $jkpj           = $data2['jkpj'];
            $alamatpj       = $data2['alamatpj'];
            $hubungan       = $data2['hubungan'];
            $no_telppj      = $data2['no_telp'];
        }
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
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>FORMULIR PERMINTAAN PERLINDUNGAN DARI KEKERASAN FISIK NO. <?=$no_permintaan;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tanggal;?></center></h7><br/>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype="multipart/form-data">
            <input type="hidden" name="nopernyataan" value="<?=$no_permintaan;?>">
            <div class="isi-surat">
                <div>Yang bertanda tangan di bawah ini :</div>
                <table class="identitas" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr class="text-dark">
                        <td width="25%">Nama</td>
                        <td width="2%">:</td>
                        <td><?=$nama_pj;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Tempat/Tanggal Lahir</td>
                        <td>:</td>
                        <td><?=$tempat_lahirpj." ".$lahirpj;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Jenis Kelamin</td>
                        <td>:</td>
                        <td><?=$jkpj;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Alamat</td>
                        <td>:</td>
                        <td><?=$alamatpj;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Hubungan Dengan Pasien</td>
                        <td>:</td>
                        <td><?=$hubungan;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Nomor Telp / HP</td>
                        <td>:</td>
                        <td><?=$no_telppj;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Nomor KTP</td>
                        <td>:</td>
                        <td><?=$no_ktppj;?></td>
                    </tr>
                </table>
                <br/>
                <div>Data pasien yang dimohonkan perlindungan :</div>
                <table class="identitas" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr class="text-dark">
                        <td width="25%">Nama Pasien</td>
                        <td width="2%">:</td>
                        <td><?=$nm_pasien;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>No. Rekam Medis</td>
                        <td>:</td>
                        <td><?=$no_rkm_medis;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>No. Rawat</td>
                        <td>:</td>
                        <td><?=$norawat;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Jenis Kelamin</td>
                        <td>:</td>
                        <td><?=$jk;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Umur</td>
                        <td>:</td>
                        <td><?=$umur;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Tanggal Lahir</td>
                        <td>:</td>
                        <td><?=$tgl_lahir;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Alamat</td>
                        <td>:</td>
                        <td><?=$alamat;?></td>
                    </tr>
                    <tr class="text-dark">
                        <td>Nomor Telp</td>
                        <td>:</td>
                        <td><?=$no_tlp;?></td>
                    </tr>
                </table>
                <br/>
                <div>
                    Dengan ini mengajukan permohonan agar selama dilakukan perawatan di <b><?=$namars;?></b>,
                    pasien tersebut di atas diberikan perlindungan dari tindakan kekerasan fisik maupun
                    perlakuan yang membahayakan keselamatan pasien.
                </div>
                <br/>
                <div>
                    Saya menyatakan bahwa data yang saya sampaikan adalah benar dan dapat dipergunakan
                    sebagaimana mestinya untuk kepentingan pelayanan dan perlindungan pasien selama dirawat.
                </div>
                <br/>
                <div>
                    Demikian surat permintaan ini saya buat dengan sebenar-benarnya, tanpa paksaan dari pihak manapun.
                    Atas perhatian dan tindak lanjutnya saya ucapkan terima kasih.
                </div>
            </div>

            <br/>
            <h6 class="text-dark">Pemohon / Penanggung Jawab</h6>
            <div class="row">
                <div class="col-md-6">
                    <div id="my_camera"></div>
                    <input type="hidden" name="image" class="image-tag" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                </div>
                <div class="col-md-6">
                    <div id="results"><h7 class="text-success"><center>Gambar akan diambil jika anda sudah mengeklik tombol ambil tanda tangan</center></h7></div>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </div>
                <div class="col-md-12 text-center">
                    <br>
                    <input type="button" class="btn btn-warning" value="Ambil Tanda Tangan Pemohon" onClick="take_snapshot()">
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
