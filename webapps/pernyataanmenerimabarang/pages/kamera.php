<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $namars        = getOne("select setting.nama_instansi from setting");
    $nopernyataan  = "";
    $norawat       = "";
    
    $_sql          = "select * from antriserahterimabarang" ;  
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
    
    $tanggal          = "";
    $jenis_barang     = "";
    $uraian_barang    = "";
    $jumlah_barang    = "";
    $kondisi_barang   = "";
    $wadah_label      = "";
    $nama_pj          = "";
    $no_ktppj         = "";
    $alamatpj         = "";
    $no_telppj        = "";
    $hubungan         = "";  
    $_sql2  = "select DATE_FORMAT(surat_serah_terima_barang_anggota_tubuh.tanggal,'%d-%m-%Y') as tanggal,surat_serah_terima_barang_anggota_tubuh.jenis_barang,".
              "surat_serah_terima_barang_anggota_tubuh.uraian_barang,surat_serah_terima_barang_anggota_tubuh.jumlah_barang,surat_serah_terima_barang_anggota_tubuh.kondisi_barang,".
              "surat_serah_terima_barang_anggota_tubuh.wadah_label,surat_serah_terima_barang_anggota_tubuh.nama_pj,surat_serah_terima_barang_anggota_tubuh.no_ktppj,".
              "surat_serah_terima_barang_anggota_tubuh.alamatpj,surat_serah_terima_barang_anggota_tubuh.no_telppj,surat_serah_terima_barang_anggota_tubuh.hubungan ".
              "from surat_serah_terima_barang_anggota_tubuh where surat_serah_terima_barang_anggota_tubuh.no_surat='$nopernyataan'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $tanggal          = $data2['tanggal'];       
        $jenis_barang     = $data2['jenis_barang']; 
        $uraian_barang    = $data2['uraian_barang']; 
        $jumlah_barang    = $data2['jumlah_barang']; 
        $kondisi_barang   = $data2['kondisi_barang']; 
        $wadah_label      = $data2['wadah_label']; 
        $nama_pj          = $data2['nama_pj']; 
        $no_ktppj         = $data2['no_ktppj']; 
        $alamatpj         = $data2['alamatpj']; 
        $no_telppj        = $data2['no_telppj']; 
        $hubungan         = $data2['hubungan'];  
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
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>FORMULIR SERAH TERIMA BARANG / ANGGOTA TUBUH PASIEN NO. <?=$nopernyataan;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tanggal;?></center></h7><br/>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="nopernyataan" value="<?=$nopernyataan;?>">
            <h7 class="text-dark">
                DATA PASIEN :
            </h7>
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
                DATA YANG DISERAHKAN :
            </h7>
            <table class="default" width="100%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="25%">Jenis</td>
                    <td width="75%">: <?=$jenis_barang;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Uraian</td>
                    <td width="75%">: <?=$uraian_barang;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Jumlah</td>
                    <td width="75%">: <?=$jumlah_barang;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Kondisi</td>
                    <td width="75%">: <?=$kondisi_barang;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Wadah / Label</td>
                    <td width="75%">: <?=$wadah_label;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                DATA PENERIMA :
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
                    <td width="25%">Hubungan</td>
                    <td width="75%">: <?=$hubungan;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">No.Telp/HP</td>
                    <td width="75%">: <?=$no_telppj;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                PERNYATAAN :
            </h7>
            <table class="default" width="97%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="100%" valign='top' align='justify'>Barang / anggota tubuh tersebut telah diterima dalam kondisi sesuai keterangan di atas, dan tanggung jawab beralih kepada penerima setelah serah terima dilakukan.</td>
                </tr>
            </table>
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
                    <br/>
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

