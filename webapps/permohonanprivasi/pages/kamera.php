<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    $namars   = getOne("select setting.nama_instansi from setting");
    $no_surat = "";
    $no_rawat = "";

    $antrian = bukaquery2("select no_surat,no_rawat from antripermintaanprivasi");
    if ($antrian) {
        $dataAntrian = mysqli_fetch_assoc($antrian);
        if ($dataAntrian) {
            $no_surat = isset($dataAntrian['no_surat']) ? trim($dataAntrian['no_surat']) : "";
            $no_rawat = isset($dataAntrian['no_rawat']) ? trim($dataAntrian['no_rawat']) : "";
        }
    }

    $no_rkm_medis = "";
    $nm_pasien    = "";
    $jk_pasien    = "";
    $umur_pasien  = "";
    $tgl_lahir    = "";
    $alamat       = "";
    $no_tlp       = "";

    if ($no_rawat !== "") {
        $sqlPasien = "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk_pasien,
                      pasien.umur as umur_pasien,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir,
                      concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,
                      pasien.no_tlp
                      from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis
                      inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel
                      inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec
                      inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab
                      where reg_periksa.no_rawat='".$no_rawat."'";
        $hasilPasien = bukaquery2($sqlPasien);
        if ($hasilPasien) {
            $dataPasien = mysqli_fetch_assoc($hasilPasien);
            if ($dataPasien) {
                $no_rkm_medis = $dataPasien['no_rkm_medis'];
                $nm_pasien    = $dataPasien['nm_pasien'];
                $jk_pasien    = $dataPasien['jk_pasien'];
                $umur_pasien  = $dataPasien['umur_pasien'];
                $tgl_lahir    = $dataPasien['tgl_lahir'];
                $alamat       = $dataPasien['alamat'];
                $no_tlp       = $dataPasien['no_tlp'];
            }
        }
    }

    $tanggal                = "";
    $nama_pemohon           = "";
    $umur_pemohon           = "";
    $no_identitas_pemohon   = "";
    $jk_pemohon             = "";
    $alamat_pemohon         = "";
    $hubungan_dengan_pasien = "";
    $no_telp_pemohon        = "";
    $kategori_privasi       = "";
    $alasan_permohonan      = "";

    if ($no_surat !== "") {
        $hasilPrivasi = bukaquery2(
            "select no_surat,tanggal,nama_pj,umur_pj,no_ktppj,jkpj,alamatpj,bertindak_atas,no_telp,kategori_privasi,alasan ".
            "from surat_permohonan_privasi where no_surat='".$no_surat."'"
        );
        if ($hasilPrivasi) {
            $dataPrivasi = mysqli_fetch_assoc($hasilPrivasi);
            if ($dataPrivasi) {
                $tanggal              = $dataPrivasi['tanggal'] ? date('d-m-Y', strtotime($dataPrivasi['tanggal'])) : "";
                $nama_pemohon         = $dataPrivasi['nama_pj'];
                $umur_pemohon         = $dataPrivasi['umur_pj'];
                $no_identitas_pemohon = $dataPrivasi['no_ktppj'];
                $jk_pemohon           = $dataPrivasi['jkpj'];
                $alamat_pemohon       = $dataPrivasi['alamatpj'];
                $hubungan_dengan_pasien = $dataPrivasi['bertindak_atas'];
                $no_telp_pemohon      = $dataPrivasi['no_telp'];
                $kategori_privasi     = $dataPrivasi['kategori_privasi'];
                $alasan_permohonan    = $dataPrivasi['alasan'];

                if ($jk_pemohon === 'L') {
                    $jk_pemohon = 'LAKI-LAKI';
                } elseif ($jk_pemohon === 'P') {
                    $jk_pemohon = 'PEREMPUAN';
                }
            }
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
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>PERMOHONAN PRIVASI PASIEN<br/>No. <?=$no_surat;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tanggal;?></center></h7><br/>
        <br>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="no_surat" value="<?=$no_surat;?>">
            <h7 class="text-dark">Yang bertanda tangan di bawah ini, selanjutnya disebut sebagai pemohon :</h7>
            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark"><td width="30%">Nama</td><td width="70%">: <?=$nama_pemohon;?></td></tr>
                <tr class="text-dark"><td width="30%">Umur</td><td width="70%">: <?=$umur_pemohon;?></td></tr>
                <tr class="text-dark"><td width="30%">Nomor Identitas</td><td width="70%">: <?=$no_identitas_pemohon;?></td></tr>
                <tr class="text-dark"><td width="30%">Jenis Kelamin</td><td width="70%">: <?=$jk_pemohon;?></td></tr>
                <tr class="text-dark"><td width="30%">Alamat</td><td width="70%">: <?=$alamat_pemohon;?></td></tr>
                <tr class="text-dark"><td width="30%">No. Telp</td><td width="70%">: <?=$no_telp_pemohon;?></td></tr>
                <tr class="text-dark"><td width="30%">Hubungan Dengan Pasien</td><td width="70%">: <?=$hubungan_dengan_pasien;?></td></tr>
            </table>
            <br/>
            <h7 class="text-dark">Data pasien yang diajukan permohonan privasinya :</h7>
            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark"><td width="30%">Nama Pasien</td><td width="70%">: <?=$nm_pasien;?></td></tr>
                <tr class="text-dark"><td width="30%">Nomor Rekam Medis</td><td width="70%">: <?=$no_rkm_medis;?></td></tr>
                <tr class="text-dark"><td width="30%">Jenis Kelamin</td><td width="70%">: <?=$jk_pasien;?></td></tr>
                <tr class="text-dark"><td width="30%">Umur</td><td width="70%">: <?=$umur_pasien;?></td></tr>
                <tr class="text-dark"><td width="30%">Tanggal Lahir</td><td width="70%">: <?=$tgl_lahir;?></td></tr>
                <tr class="text-dark"><td width="30%">Alamat</td><td width="70%">: <?=$alamat;?></td></tr>
                <tr class="text-dark"><td width="30%">No. Telp</td><td width="70%">: <?=$no_tlp;?></td></tr>
            </table>
            <br/>
            <h7 class="text-dark">Dengan ini mengajukan permohonan privasi pasien dengan rincian sebagai berikut :</h7>
            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark"><td width="30%">Kategori Privasi</td><td width="70%">: <?=$kategori_privasi;?></td></tr>
                <tr class="text-dark"><td width="30%">Alasan Permohonan</td><td width="70%">: <?=$alasan_permohonan;?></td></tr>
            </table>
            <br/><br/>
            <h7 class="text-dark"><center>Yang Membuat Permohonan</center></h7>
            <div class="row">
                <div class="col-12 col-md-6 mb-3">
                    <div class="camera-container">
                        <div id="my_camera"></div>
                        <input type="hidden" name="image" class="image-tag" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                    </div>
                </div>
                <div class="col-12 col-md-6 mb-3">
                    <div class="results-container">
                        <div id="results">
                            <h7 class="text-success text-center d-block">Gambar akan diambil jika anda sudah mengeklik tombol ambil dokumentasi</h7>
                        </div>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </div>
                </div>
                <div class="col-12 text-center">
                    <div class="button-container">
                        <input type="button" class="btn btn-warning" value="Ya, Saya sebagai pembuat permohonan" onClick="take_snapshot()">
                        <button class="btn btn-danger mb-2">Simpan</button>
                    </div>
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
