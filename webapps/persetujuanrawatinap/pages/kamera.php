<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $namars        = getOne("select setting.nama_instansi from setting");
    $nopersetujuan = "";
    $norawat       = "";
    
    $_sql          = "select * from antripersetujuanrawatinap" ;  
    $hasil         = bukaquery2($_sql);
    while ($data = mysqli_fetch_array ($hasil)){
        $nopersetujuan = $data['no_persetujuan'];
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
    $nama_pj                        = "";        
    $no_ktppj                       = "";        
    $pendidikan_pj                  = "";        
    $alamatpj                       = "";        
    $no_telppj                      = "";        
    $ruang                          = "";        
    $kelas                          = "";        
    $hubungan                       = "";        
    $hak_kelas                      = "";        
    $nama_alamat_keluarga_terdekat  = "";        
    $bayar_secara                   = "";  
    $_sql2  = "select DATE_FORMAT(surat_persetujuan_rawat_inap.tanggal,'%d-%m-%Y') as tanggal,surat_persetujuan_rawat_inap.nama_pj,
               surat_persetujuan_rawat_inap.no_ktppj,surat_persetujuan_rawat_inap.pendidikan_pj,surat_persetujuan_rawat_inap.alamatpj,
               surat_persetujuan_rawat_inap.no_telppj,surat_persetujuan_rawat_inap.ruang,surat_persetujuan_rawat_inap.kelas,
               surat_persetujuan_rawat_inap.hubungan,surat_persetujuan_rawat_inap.hak_kelas,surat_persetujuan_rawat_inap.nama_alamat_keluarga_terdekat,
               surat_persetujuan_rawat_inap.bayar_secara from surat_persetujuan_rawat_inap where surat_persetujuan_rawat_inap.no_surat='$nopersetujuan'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $tanggal                        = $data2['tanggal'];       
        $nama_pj                        = $data2['nama_pj'];        
        $no_ktppj                       = $data2['no_ktppj'];        
        $pendidikan_pj                  = $data2['pendidikan_pj'];        
        $alamatpj                       = $data2['alamatpj'];        
        $no_telppj                      = $data2['no_telppj'];        
        $ruang                          = $data2['ruang'];        
        $kelas                          = $data2['kelas'];        
        $hubungan                       = $data2['hubungan'];        
        $hak_kelas                      = $data2['hak_kelas'];        
        $nama_alamat_keluarga_terdekat  = $data2['nama_alamat_keluarga_terdekat'];        
        $bayar_secara                   = $data2['bayar_secara'];  
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
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>PERSETUJUAN PASIEN RAWAT INAP NO. <?=$nopersetujuan;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tanggal;?></center></h7><br/>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="nopersetujuan" value="<?=$nopersetujuan;?>">
            <table class="default" width="100%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="25%">Nama Pasien</td>
                    <td width="75%">: <?=$nm_pasien;?></td>
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
                    <td width="25%">Umur</td>
                    <td width="75%">: <?=$umur;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">No.Telp/No.HP</td>
                    <td width="75%">: <?=$no_tlp;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Saya yang membuat persetujuan di bawah ini :
            </h7>
            <table class="default" width="97%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="25%">Nama</td>
                    <td width="75%">: <?=$nama_pj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Alamat</td>
                    <td width="75%">: <?=$alamatpj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">No.KTP/SIM</td>
                    <td width="75%">: <?=$no_ktppj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Pendidikan</td>
                    <td width="75%">: <?=$pendidikan_pj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">No.Telp/HP</td>
                    <td width="75%">: <?=$no_telppj;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Dengan ini menyatakan dengan sesungguhnya bahwa saya setuju untuk dilakukan Rawat Inap di <?=$namars;?> di ruang : <?=$ruang;?> Kelas : <?=$kelas;?> Terhadap <?=$hubungan;?>
                <br/>
                Hak kelas perawatan : <?=$hak_kelas;?> 
                <br/>
                Nama dan alamat keluarga terdekat : <?=$nama_alamat_keluarga_terdekat;?> 
                <br/>
                Demi kelancaran pelayanan perawatan, pengobatan dan administrasi, dengan ini juga menyatakan :
            </h7>
            <table class="default" width="97%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="5%" valign='top'>a.</td>
                    <td width="95%" valign='top' align='justify'>Setuju dan memberi ijin kepada dokter yang bersangkutan untuk merawat saya/pasien tersebut diatas</td>
                </tr>
                <tr class="text-dark">
                    <td width="5%" valign='top'>b.</td>
                    <td width="95%" valign='top' align='justify'>Dengan ini menyatakan dengan sesungguhnya bahwa seluruh pembiayaan pelayanan di <?=$namars;?> akan saya bayarkan secara <?=$bayar_secara;?>, dan bersedia untuk melengkapi berkas kelengkapannya. Apabila dalam waktu 3 x 24 Jam tidak dapat menunjukkan kartu/kelengkapan lainnya, maka saya siap untuk membayarkan semua pelayanan dan tindakan di <?=$namars;?>.</td>
                </tr>
                <tr class="text-dark">
                    <td width="5%" valign='top'>c.</td>
                    <td width="95%" valign='top' align='justify'>Telah menyetujui dan bersedia mentaati segala peraturan yang berlaku di <?=$namars;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="5%" valign='top'>d.</td>
                    <td width="95%" valign='top' align='justify'>Memberi kuasa kepada Dokter untuk memberikan keterangan yang diperlukan oleh pihak penanggung biaya perawatan saya / pasien tersebut diatas</td>
                </tr>
            </table>
            <br/>
            <br/>
            <h7 class="text-dark"><center>Yang Membuat Persetujuan</center></h7>
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
                    <br/>
                    <input type="button" class="btn btn-warning" value="Ya, Saya sebagai pembuat persetujuan" onClick="take_snapshot()">
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

