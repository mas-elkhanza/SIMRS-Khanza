<!DOCTYPE html>
<html>
<head>
    <script src="js/jquery.min.js"></script>
    <script src="js/webcam.min.js"></script>
</head>
<body>
<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["noresep"])) {
        $noresep    = validTeks3($iyem["noresep"],20);
        $norawat    = validTeks3($iyem["norawat"],20);
        echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PENYERAHAN RESEP OBAT RAWAT JALAN<br/>No. $noresep</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='15%'>Nomor Rawat</td>
                                        <td width='35%'>: ".$norawat."</td>
                                        <td width='15%'>Nomor R.M.</td>
                                        <td width='35%'>: ".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."</td>
                                    </tr>
                                    <tr>
                                        <td width='15%'>Tempat Lahir</td>
                                        <td width='35%'>: ".$_SESSION["tmp_lahir"]."</td>
                                        <td width='15%'>Tanggal Lahir</td>
                                        <td width='35%'>: ".$_SESSION["tgl_lahir"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='15%'>Nama Pasien</td>
                                        <td width='35%'>: ".$_SESSION["nm_pasien"]."</td>
                                        <td width='15%'>No.KTP / Lainnya</td>
                                        <td width='35%'>: ".$_SESSION["no_ktp"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='15%'>Jenis Kelamin</td>
                                        <td width='35%'>: ".($_SESSION["jk"]=="P"?"Perempuan":"Laki-laki")."</td>
                                        <td width='15%'>No.HP / Telp</td>
                                        <td width='35%'>: ".$_SESSION["no_tlp"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <table width='100%' align='center' class='table table-bordered table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='3%'><div align='center'>No.</div></td>
                                        <td width='45%'><div align='center'>Nama Obat</div></td>
                                        <td width='17%'><div align='center'>Jumlah</div></td>
                                        <td width='35%'><div align='center'>Aturan Pakai</div></td>
                                    </tr>";
        $queryresepnonracikan = bukaquery(
            "select databarang.nama_brng,aturan_pakai.aturan,detail_pemberian_obat.jml,kodesatuan.satuan ".
            "from resep_obat inner join reg_periksa inner join aturan_pakai inner join databarang inner join detail_pemberian_obat ".
            "inner join kodesatuan on resep_obat.no_rawat=reg_periksa.no_rawat and databarang.kode_brng=aturan_pakai.kode_brng and ".
            "detail_pemberian_obat.kode_brng=databarang.kode_brng and resep_obat.no_rawat=aturan_pakai.no_rawat and ".
            "resep_obat.tgl_perawatan=aturan_pakai.tgl_perawatan and resep_obat.jam=aturan_pakai.jam and ".
            "resep_obat.no_rawat=detail_pemberian_obat.no_rawat and resep_obat.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and ".
            "resep_obat.jam=detail_pemberian_obat.jam and kodesatuan.kode_sat=databarang.kode_sat where resep_obat.no_resep='$noresep'"
        );
        $i=1;
        while($rsqueryresepnonracikan= mysqli_fetch_array($queryresepnonracikan)){
            echo "                  <tr class='text-dark'>
                                        <td align='center'>".$i."</td>
                                        <td align='left'>".$rsqueryresepnonracikan["nama_brng"]."</td>
                                        <td align='center'>".$rsqueryresepnonracikan["jml"]." ".$rsqueryresepnonracikan["satuan"]."</td>
                                        <td align='center'>".$rsqueryresepnonracikan["aturan"]."</td>
                                    </tr>";
            $i++;
        }
        $queryresepracikan=bukaquery(
            "select obat_racikan.no_racik,obat_racikan.nama_racik,obat_racikan.tgl_perawatan,obat_racikan.jam,".
            "obat_racikan.no_rawat,obat_racikan.aturan_pakai,obat_racikan.jml_dr,metode_racik.nm_racik from resep_obat inner join ".
            "reg_periksa inner join obat_racikan inner join metode_racik on resep_obat.no_rawat=reg_periksa.no_rawat ".
            "and obat_racikan.kd_racik=metode_racik.kd_racik and resep_obat.no_rawat=obat_racikan.no_rawat and ".
            "resep_obat.tgl_perawatan=obat_racikan.tgl_perawatan and resep_obat.jam=obat_racikan.jam and ".
            "resep_obat.no_rawat=obat_racikan.no_rawat where resep_obat.no_resep='$noresep'"
        );
        while($rsqueryresepracikan = mysqli_fetch_array($queryresepracikan)) {
            echo "                  <tr class='text-dark'>
                                        <td align='center'>".$i."</td>
                                        <td align='left'>".$rsqueryresepracikan["no_racik"]." ".$rsqueryresepracikan["nama_racik"]." (";
            $queryresepdetailracikan=bukaquery(
                "select databarang.nama_brng,detail_pemberian_obat.jml from detail_pemberian_obat inner join databarang inner join detail_obat_racikan ".
                "on detail_pemberian_obat.kode_brng=databarang.kode_brng and detail_pemberian_obat.kode_brng=detail_obat_racikan.kode_brng and ".
                "detail_pemberian_obat.tgl_perawatan=detail_obat_racikan.tgl_perawatan and detail_pemberian_obat.jam=detail_obat_racikan.jam and ".
                "detail_pemberian_obat.no_rawat=detail_obat_racikan.no_rawat where detail_pemberian_obat.tgl_perawatan='".$rsqueryresepracikan["tgl_perawatan"]."' ".
                "and detail_pemberian_obat.jam='".$rsqueryresepracikan["jam"]."' and detail_pemberian_obat.no_rawat='".$rsqueryresepracikan["no_rawat"]."' and ".
                "detail_obat_racikan.no_racik='".$rsqueryresepracikan["no_racik"]."' order by databarang.kode_brng"
            );
            while($rsqueryresepdetailracikan = mysqli_fetch_array($queryresepdetailracikan)) {
                echo                        $rsqueryresepdetailracikan["nama_brng"]." ".$rsqueryresepdetailracikan["jml"].", ";
            }
            echo ")
                                        </td>
                                        <td align='center'>".$rsqueryresepracikan["jml_dr"]." ".$rsqueryresepracikan["nm_racik"]."</td>
                                        <td align='center'>".$rsqueryresepracikan["aturan_pakai"]."</td>
                                    </tr>";
            $i++;
        }
        echo "                  </table>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='noresep' value='$noresep'>
                                    <h7><center>Pasien/Yang Mewakili<br/></h7>    
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div><center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi1'));' id='TxtIsi1'>
                                            <br/>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya mengerti' onClick='take_snapshot()'>
                                            <a href='index.php?act=PersetujuanPenyerahanResepRalan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                  </div>";

        $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
        if (isset($BtnSimpan)) {
            $noresep     = validTeks4($_POST["noresep"],20);
            if(file_exists("../webapps/penyerahanresep/pages/upload/".$noresep.".jpeg")){
                @unlink("../webapps/penyerahanresep/pages/upload/".$noresep.".jpeg");
            }
            $img               = $_POST["image"];
            $folderPath        = "../webapps/penyerahanresep/pages/upload/";
            $image_parts       = explode(";base64,", $img);
            $image_type_aux    = explode("image/", $image_parts[0]);
            $image_type        = $image_type_aux[1];
            $image_base64      = base64_decode($image_parts[1]);
            $fileName          = $noresep.".jpeg";
            $file              = $folderPath . $fileName;

            if(file_put_contents($file, $image_base64)){
                if(file_exists("../webapps/penyerahanresep/pages/upload/".$noresep.".jpeg")){
                    if(Tambah3("bukti_penyerahan_resep_obat","'".$noresep."','pages/upload/$fileName'")){
                        Ubah2("resep_obat","tgl_penyerahan=current_date(),jam_penyerahan=current_time() where no_resep='$noresep'");
                        JSRedirect("index.php?act=PersetujuanPenyerahanResepRalan&hal=Persetujuan");
                    }
                }else{
                    echo "<div class='row clearfix'>
                            <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                               <div class='card'>
                                   <div class='body'>
                                       <center>Gagal melakukan persetujuan</center>
                                   </div>
                               </div>
                            </div>
                         </div>";
                }
            }else{
                echo "<div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Gagal melakukan persetujuan</center>
                                </div>
                            </div>
                         </div>
                      </div>";
            }
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PENYERAHAN RESEP OBAT RAWAT JALAN</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf persetujuan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=PersetujuanPenyerahanResepRalan&hal=Persetujuan",4);
    }
?>
<script language="JavaScript">
    Webcam.set({
        width: 490,
        height: 440,
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