<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $nopernyataan  = "";
    $norawat       = "";
    $_sql          = "select * from antripermintaanbinrohtal" ;  
    $hasil         = bukaquery2($_sql);
    while ($data = mysqli_fetch_array ($hasil)){
        $nopernyataan = $data['no_surat'];
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
    
    $tanggal                        = "";
    $nip_petugas                    = "";
    $nama_petugas                   = "";
    $kd_dokter                      = "";
    $nm_dokter                      = "";
    $kd_bangsal                     = "";
    $nm_bangsal                     = "";
    $jns_permintaan                 = "";
    $agama_permohonan               = "";
    $jns_pelayanan                  = "";
    $ket_pelayanan                  = "";
    $respon_pelayanan               = "";
    $ket_respon                     = "";
    $keterangan                     = "";
    $_sql2  = "select DATE_FORMAT(permintaan_binrohtal.tanggal,'%d-%m-%Y %H:%i:%s') as tanggal,
                permintaan_binrohtal.no_surat,permintaan_binrohtal.no_rawat,permintaan_binrohtal.nip,pegawai.nama,
                permintaan_binrohtal.kd_dokter,dokter.nm_dokter,permintaan_binrohtal.kd_bangsal,bangsal.nm_bangsal,
                permintaan_binrohtal.jns_permintaan,ifnull(nullif(permintaan_binrohtal.agama,''),pasien.agama) as agama,
                permintaan_binrohtal.jns_pelayanan,permintaan_binrohtal.ket_pelayanan,permintaan_binrohtal.respon,
                permintaan_binrohtal.ket_respon,permintaan_binrohtal.keterangan
                from permintaan_binrohtal
                inner join reg_periksa on permintaan_binrohtal.no_rawat=reg_periksa.no_rawat
                inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis
                inner join pegawai on permintaan_binrohtal.nip=pegawai.nik
                inner join dokter on permintaan_binrohtal.kd_dokter=dokter.kd_dokter
                inner join bangsal on permintaan_binrohtal.kd_bangsal=bangsal.kd_bangsal
                where permintaan_binrohtal.no_surat='$nopernyataan'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $tanggal                        = $data2['tanggal'];
        $nip_petugas                    = $data2['nip'];
        $nama_petugas                   = $data2['nama'];
        $kd_dokter                      = $data2['kd_dokter'];
        $nm_dokter                      = $data2['nm_dokter'];
        $kd_bangsal                     = $data2['kd_bangsal'];
        $nm_bangsal                     = $data2['nm_bangsal'];
        $jns_permintaan                 = $data2['jns_permintaan'];
        $agama_permohonan               = $data2['agama'];
        $jns_pelayanan                  = $data2['jns_pelayanan'];
        $ket_pelayanan                  = $data2['ket_pelayanan'];
        $respon_pelayanan               = $data2['respon'];
        $ket_respon                     = $data2['ket_respon'];
        $keterangan                     = $data2['keterangan'];
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
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>PERSETUJUAN BIMBINGAN ROHANI DAN MENTAL NO. <?=$nopernyataan;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tanggal;?></center></h7><br/>
        <br>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="nopernyataan" value="<?=$nopernyataan;?>">
            <h7 class="text-dark">
            Data pasien yang akan mendapatkan pelayanan bimbingan rohani dan mental :
            </h7>
            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="25%">Nama Pasien</td>
                    <td width="75%">: <?=$nm_pasien;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Rekam Medis</td>
                    <td width="75%">: <?=$no_rkm_medis;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">No. Rawat</td>
                    <td width="75%">: <?=$norawat;?></td>
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
                    <td width="25%">Alamat</td>
                    <td width="75%">: <?=$alamat;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Telp/HP</td>
                    <td width="75%">: <?=$no_tlp;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Agama</td>
                    <td width="75%">: <?=$agama_permohonan;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Detail permintaan pelayanan bimbingan rohani dan mental :
            </h7>
            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="25%">Nomor Surat</td>
                    <td width="75%">: <?=$nopernyataan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Ruang / Bangsal</td>
                    <td width="75%">: <?=$kd_bangsal."- ".$nm_bangsal;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Jenis Permintaan</td>
                    <td width="75%">: <?=$jns_permintaan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Jenis Pelayanan</td>
                    <td width="75%">: <?=$jns_pelayanan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Keterangan Pelayanan</td>
                    <td width="75%">: <?=$ket_pelayanan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Respon</td>
                    <td width="75%">: <?=$respon_pelayanan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Keterangan Respon</td>
                    <td width="75%">: <?=$ket_respon;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Keterangan Tambahan</td>
                    <td width="75%">: <?=$keterangan;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Pelayanan bimbingan rohani dan mental dilaksanakan oleh <b><?=$nama_petugas;?></b> (NIP <?=$nip_petugas;?>) dengan dokter penanggung jawab pasien <b><?=$nm_dokter;?></b>.
            </h7>
            <br/>
            <br/>
            <h7 class="text-dark"><center>Yang Membuat Persetujuan</center></h7>
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
                        <input type="button" class="btn btn-warning" value="Ya, Saya sebagai pembuat persetujuan" onClick="take_snapshot()">
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

