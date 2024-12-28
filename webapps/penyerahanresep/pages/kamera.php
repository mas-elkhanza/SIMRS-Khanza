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
        <br>
        <table class="default" width='100%' border='1' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
            <tr class="text-danger">
                <td width='3%'><div align='center'>No.</div></td>
                <td width='40%'><div align='center'>Nama Obat</div></td>
                <td width='17%'><div align='center'>Jumlah</div></td>
                <td width='40%'><div align='center'>Aturan Pakai</div></td>
            </tr>
            <?php
                $i=1;
                $resepnonracikan=bukaquery("select databarang.nama_brng,aturan_pakai.aturan,detail_pemberian_obat.jml,kodesatuan.satuan
                        from resep_obat inner join reg_periksa inner join aturan_pakai inner join databarang inner join detail_pemberian_obat 
                        inner join kodesatuan on resep_obat.no_rawat=reg_periksa.no_rawat and databarang.kode_brng=aturan_pakai.kode_brng and 
                        detail_pemberian_obat.kode_brng=databarang.kode_brng and resep_obat.no_rawat=aturan_pakai.no_rawat and 
                        resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and resep_obat.jam=aturan_pakai.jam and 
                        resep_obat.no_rawat=detail_pemberian_obat.no_rawat and resep_obat.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and
                        resep_obat.jam=detail_pemberian_obat.jam and kodesatuan.kode_sat=databarang.kode_sat where resep_obat.no_resep='$noresep'");
                while($barisresepnonracikan = mysqli_fetch_array($resepnonracikan)) {
                    echo "<tr class='text-danger'>
                            <td align='center'>$i</td>
                            <td align='center'>$barisresepnonracikan[nama_brng]</td>
                            <td align='center'>$barisresepnonracikan[jml] $barisresepnonracikan[satuan]</td>
                            <td align='center'>$barisresepnonracikan[aturan]</td>
                          </tr>";
                    $i++;
                }
                $resepracikan=bukaquery("select obat_racikan.no_racik,obat_racikan.nama_racik,obat_racikan.tgl_perawatan,obat_racikan.jam,
                        obat_racikan.no_rawat,obat_racikan.aturan_pakai,obat_racikan.jml_dr,metode_racik.nm_racik from resep_obat inner join 
                        reg_periksa inner join obat_racikan inner join metode_racik on resep_obat.no_rawat=reg_periksa.no_rawat 
                        and obat_racikan.kd_racik=metode_racik.kd_racik and resep_obat.no_rawat=obat_racikan.no_rawat and 
                        resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and resep_obat.jam=obat_racikan.jam and 
                        resep_obat.no_rawat=obat_racikan.no_rawat where resep_obat.no_resep='$noresep'");
                while($barisresepracikan = mysqli_fetch_array($resepracikan)) {
                    echo "<tr class='text-danger'>
                            <td align='center'>$i</td>
                            <td align='center'>$barisresepracikan[no_racik] $barisresepracikan[nama_racik] (";
                        $resepdetailracikan=bukaquery("select databarang.nama_brng,detail_pemberian_obat.jml from
                            detail_pemberian_obat inner join databarang inner join detail_obat_racikan 
                            on detail_pemberian_obat.kode_brng=databarang.kode_brng and 
                            detail_pemberian_obat.kode_brng=detail_obat_racikan.kode_brng and 
                            detail_pemberian_obat.tgl_perawatan=detail_obat_racikan.tgl_perawatan and 
                            detail_pemberian_obat.jam=detail_obat_racikan.jam and 
                            detail_pemberian_obat.no_rawat=detail_obat_racikan.no_rawat 
                            where detail_pemberian_obat.tgl_perawatan='$barisresepracikan[tgl_perawatan]' 
                            and detail_pemberian_obat.jam='$barisresepracikan[jam]' and 
                            detail_pemberian_obat.no_rawat='$barisresepracikan[no_rawat]' and 
                            detail_obat_racikan.no_racik='$barisresepracikan[no_racik]' order by databarang.kode_brng");
                        while($barisresepdetailracikan = mysqli_fetch_array($resepdetailracikan)) {
                            echo "$barisresepdetailracikan[nama_brng] $barisresepdetailracikan[jml], ";
                        }
                    echo "  )
                            </td>
                            <td align='center'>$barisresepracikan[jml_dr] $barisresepracikan[nm_racik]</td>
                            <td align='center'>$barisresepracikan[aturan_pakai]</td>
                          </tr>";
                    $i++;
                }
            ?>
        </table>
        <br>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="noresep" value="<?=$noresep;?>">
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

