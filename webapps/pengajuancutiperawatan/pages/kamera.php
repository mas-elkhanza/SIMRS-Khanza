<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $namars        = getOne("select setting.nama_instansi from setting");
    $nopernyataan  = "";
    $norawat       = "";
    $_sql          = "select * from antripengajuancutiperawatan" ;  
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
    $pembuat_pengajuan              = "";
    $alamat_pembuat_pengajuan       = "";
    $tgl_lahir_pembuat_pengajuan    = "";
    $jk_pembuat_pengajuan           = "";
    $hubungan_pembuat_pengajuan     = "";
    $notelp                         = "";
    $alasan_cuti                    = "";
    $mulai_cuti                     = "";
    $alamat_selama_cuti             = "";
    $kembali_dari_cuti              = "";
    $_sql2  = "select DATE_FORMAT(surat_pengajuan_cuti_pasien.tanggal,'%d-%m-%Y %H:%i:%s') as tanggal,
                surat_pengajuan_cuti_pasien.no_surat,surat_pengajuan_cuti_pasien.no_rawat,surat_pengajuan_cuti_pasien.nip,pegawai.nama,
                surat_pengajuan_cuti_pasien.kd_dokter,dokter.nm_dokter,surat_pengajuan_cuti_pasien.kd_bangsal,bangsal.nm_bangsal,
                surat_pengajuan_cuti_pasien.pembuat_pengajuan,surat_pengajuan_cuti_pasien.alamat_pembuat_pengajuan,
                surat_pengajuan_cuti_pasien.tgl_lahir_pembuat_pengajuan,surat_pengajuan_cuti_pasien.jk_pembuat_pengajuan,
                surat_pengajuan_cuti_pasien.hubungan_pembuat_pengajuan,surat_pengajuan_cuti_pasien.notelp,
                surat_pengajuan_cuti_pasien.alasan_cuti,surat_pengajuan_cuti_pasien.mulai_cuti,
                surat_pengajuan_cuti_pasien.alamat_selama_cuti,surat_pengajuan_cuti_pasien.kembali_dari_cuti
                from surat_pengajuan_cuti_pasien
                inner join reg_periksa on surat_pengajuan_cuti_pasien.no_rawat=reg_periksa.no_rawat
                inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis
                inner join pegawai on surat_pengajuan_cuti_pasien.nip=pegawai.nik
                inner join dokter on surat_pengajuan_cuti_pasien.kd_dokter=dokter.kd_dokter
                inner join bangsal on surat_pengajuan_cuti_pasien.kd_bangsal=bangsal.kd_bangsal
                where surat_pengajuan_cuti_pasien.no_surat='$nopernyataan'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $tanggal                        = $data2['tanggal'];
        $nip_petugas                    = $data2['nip'];
        $nama_petugas                   = $data2['nama'];
        $kd_dokter                      = $data2['kd_dokter'];
        $nm_dokter                      = $data2['nm_dokter'];
        $kd_bangsal                     = $data2['kd_bangsal'];
        $nm_bangsal                     = $data2['nm_bangsal'];
        $pembuat_pengajuan              = $data2['pembuat_pengajuan'];
        $alamat_pembuat_pengajuan       = $data2['alamat_pembuat_pengajuan'];
        $tgl_lahir_pembuat_pengajuan    = $data2['tgl_lahir_pembuat_pengajuan'];
        $jk_pembuat_pengajuan           = $data2['jk_pembuat_pengajuan'];
        $hubungan_pembuat_pengajuan     = $data2['hubungan_pembuat_pengajuan'];
        $notelp                         = $data2['notelp'];
        $alasan_cuti                    = $data2['alasan_cuti'];
        $mulai_cuti                     = $data2['mulai_cuti'];
        $alamat_selama_cuti             = $data2['alamat_selama_cuti'];
        $kembali_dari_cuti              = $data2['kembali_dari_cuti'];
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

        /* Tambah ini */
        .paragraf-pernyataan {
            text-align: justify;
            margin-bottom: 12px;
            display: block;
        }
        .subjudul-pernyataan {
            display: block;
            margin-bottom: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>PENGAJUAN CUTI PERAWATAN PASIEN NO. <?=$nopernyataan;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tanggal;?></center></h7><br/>
        <br>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="nopernyataan" value="<?=$nopernyataan;?>">
            <h7 class="text-dark">
                Saya yang bertanda tangan di bawah ini : 
            </h7>
            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="25%">Nama</td>
                    <td width="75%">: <?=$pembuat_pengajuan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Alamat</td>
                    <td width="75%">: <?=$alamat_pembuat_pengajuan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Tanggal Lahir</td>
                    <td width="75%">: <?=$tgl_lahir_pembuat_pengajuan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Jenis Kelamin</td>
                    <td width="75%">: <?=($jk_pembuat_pengajuan=="L"?"LAKI-LAKI":"PEREMPUAN");?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Hubungan Dengan Pasien</td>
                    <td width="75%">: <?=$hubungan_pembuat_pengajuan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Telp/HP</td>
                    <td width="75%">: <?=$notelp;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Bermaksud mengajukan cuti perawatan untuk pasien :
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
                    <td width="25%">Ruang / Bangsal</td>
                    <td width="75%">: <?=$kd_bangsal." - ".$nm_bangsal;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Dokter Penanggung Jawab</td>
                    <td width="75%">: <?=$nm_dokter;?></td>
                </tr>
            </table>
            <br/>
            <span class="paragraf-pernyataan text-dark">
                Dengan alasan <b><?=$alasan_cuti;?></b>. Mulai cuti <?=$mulai_cuti;?> dan akan kembali pada tanggal <?=$kembali_dari_cuti;?>. Alamat selama cuti <?=$alamat_selama_cuti;?>. Dan saya akan mengikuti peraturan sesuai dengan ketentuan yang berlaku selama di rumah sakit.
                Bila terjadi sewaktu hal selama saya cuti, maka tidak menjadi tanggung jawab pihak <?=$namars;?>.<br><br>Demikian surat permintaan cuti ini dan atas kerjasamanya saya ucapkan terima kasih.
            </span>
            <br/>
            <br/>
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
                            <h7 class="text-success text-center d-block">Gambar akan diambil jika anda sudah mengeklik tombol ambil</h7>
                        </div>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </div>
                </div>
                <div class="col-12 text-center">
                    <div class="button-container">
                        <input type="button" class="btn btn-warning" value="Ya, Saya sebagai pembuat Permohonan" onClick="take_snapshot()">
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

