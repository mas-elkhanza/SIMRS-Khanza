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
    if (isset($iyem["nopersetujuan"])) {
        $nopersetujuan    = validTeks3($iyem["nopersetujuan"],20);
        $photo            = validTeks3($iyem["photo"],50);
        $photo2           = validTeks3($iyem["photo2"],50);
        if($photo==""){
            $querypersetujuan = bukaquery(
                "select persetujuan_penolakan_tindakan.tanggal,persetujuan_penolakan_tindakan.diagnosa,persetujuan_penolakan_tindakan.tindakan,persetujuan_penolakan_tindakan.indikasi_tindakan,".
                "persetujuan_penolakan_tindakan.tata_cara,persetujuan_penolakan_tindakan.tujuan,persetujuan_penolakan_tindakan.risiko,persetujuan_penolakan_tindakan.komplikasi,".
                "persetujuan_penolakan_tindakan.prognosis,persetujuan_penolakan_tindakan.alternatif_dan_risikonya,persetujuan_penolakan_tindakan.biaya,persetujuan_penolakan_tindakan.lain_lain,".
                "persetujuan_penolakan_tindakan.kd_dokter,persetujuan_penolakan_tindakan.nip,persetujuan_penolakan_tindakan.penerima_informasi,persetujuan_penolakan_tindakan.alasan_diwakilkan_penerima_informasi,".
                "if(persetujuan_penolakan_tindakan.jk_penerima_informasi='L','LAKI-LAKI','PEREMPUAN') as jk_penerima_informasi,persetujuan_penolakan_tindakan.tanggal_lahir_penerima_informasi,".
                "persetujuan_penolakan_tindakan.umur_penerima_informasi,persetujuan_penolakan_tindakan.alamat_penerima_informasi,persetujuan_penolakan_tindakan.no_hp,".
                "persetujuan_penolakan_tindakan.hubungan_penerima_informasi,persetujuan_penolakan_tindakan.saksi_keluarga from persetujuan_penolakan_tindakan ".
                "where persetujuan_penolakan_tindakan.no_pernyataan='$nopersetujuan'"
            );
            if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
                echo "<div class='row clearfix'>
                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='header'>
                                    <h2><center>PERNYATAAN PERSETUJUAN/PENOLAKAN TINDAKAN<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                    <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                                </div>
                                <div class='body'>
                                    <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                        <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                        <input type='hidden' name='photo' value='$photo'>
                                        <h7>
                                            Apabila pasien berusia dibawah 18 tahun atau tidak dapat memberikan persetujuan karena alasan lain (**) tidak dapat menandatangani surat pernyataan ini, 
                                            Pihak rumah sakit dapat mengambil kebijakan dengan memperoleh tanda tangan dari orang tua, pasangan, anggota keluarga terdekat atau wali pasien.<br/> 
                                            (**) Sebutkan alasan lainnya : ".$rsquerypersetujuan["alasan_diwakilkan_penerima_informasi"]."<br/><br/>
                                            Yang bertanda tangan di bawah ini saya :
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='30%'>Nama</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["penerima_informasi"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Jenis Kelamin</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["jk_penerima_informasi"]." &nbsp;&nbsp;&nbsp;&nbsp;Tanggal Lahir : ".$rsquerypersetujuan["tanggal_lahir_penerima_informasi"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Alamat</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["alamat_penerima_informasi"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Hubungan dengan pasien</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["hubungan_penerima_informasi"]."</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Dengan ini menyatakan <select name='pilihansetuju'><option value='Persetujuan'>Persetujuan</option><option value='Penolakan'>Penolakan</option></select> untuk dapat dilakukan tindakan kedokteran berupa : 
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='30%'>Tindakan Kedokteran</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["tindakan"]."</td>
                                                <td width='10%'><input type='checkbox' name='tindakan_konfirmasi'></td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Diagnosa</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["diagnosa"]."</td>
                                                <td width='10%'><input type='checkbox' name='diagnosa_konfirmasi'></td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Indikasi Tindakan</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["indikasi_tindakan"]."</td>
                                                <td width='10%'><input type='checkbox' name='indikasi_tindakan_konfirmasi'></td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Tata Cara</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["tata_cara"]."</td>
                                                <td width='10%'><input type='checkbox' name='tata_cara_konfirmasi'></td>
                                            </tr> 
                                            <tr>
                                                <td width='30%'>Tujuan</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["tujuan"]."</td>
                                                <td width='10%'><input type='checkbox' name='tujuan_konfirmasi'></td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Risiko</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["risiko"]."</td>
                                                <td width='10%'><input type='checkbox' name='risiko_konfirmasi'></td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Komplikasi</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["komplikasi"]."</td>
                                                <td width='10%'><input type='checkbox' name='komplikasi_konfirmasi'></td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Prognosis</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["prognosis"]."</td>
                                                <td width='10%'><input type='checkbox' name='prognosis_konfirmasi'></td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Alternatif & Resikonya</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["alternatif_dan_risikonya"]."</td>
                                                <td width='10%'><input type='checkbox' name='alternatif_konfirmasi'></td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Lain-lain</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["lain_lain"]."</td>
                                                <td width='10%'><input type='checkbox' name='lain_lain_konfirmasi'></td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Biaya</td>
                                                <td width='60%'>: ".$rsquerypersetujuan["biaya"]."</td>
                                                <td width='10%'><input type='checkbox' name='biaya_konfirmasi'></td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Terhadap Pasien : 
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='30%'>Nama Pasien</td>
                                                <td width='70%'>: ".$_SESSION["nm_pasien"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Nomor Rekam Medis</td>
                                                <td width='75%'>: ".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Jenis Kelamin</td>
                                                <td width='75%'>: ".($_SESSION["jk"]=="L"?"Laki-laki":"Perempuan")."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Tanggal Lahir</td>
                                                <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Melalui penyataan ini segala resiko dan yang kemungkinan terjadi sebagai akibat dari pengambilan keputusan ini menjadi tanggung jawab saya pribadi dan keluarga
                                        </h7>
                                        <br/>
                                        <br/>
                                        <h7><center>Yang Membuat Pernyataan</center></h7>
                                        <div class='row'>
                                            <div class='col-md-12 text-center'>
                                                <center><div id='my_camera'></div></center>
                                                <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                                <br/>
                                                <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat pernyataan' onClick='take_snapshot()'/>
                                                <a href='index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                      </div>";

                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $nopersetujuan          = validTeks4($_POST["nopersetujuan"],20);
                    $pilihansetuju          = validTeks4($_POST["pilihansetuju"],20);
                    $tindakan_konfirmasi    = "false";
                    $diagnosa_konfirmasi    = "false";
                    $indikasi_tindakan_konfirmasi ="false";
                    $tata_cara_konfirmasi   = "false";
                    $tujuan_konfirmasi      = "false";
                    $risiko_konfirmasi      = "false";
                    $komplikasi_konfirmasi  = "false";
                    $prognosis_konfirmasi   = "false";
                    $alternatif_konfirmasi  = "false";
                    $lain_lain_konfirmasi   = "false";
                    $biaya_konfirmasi       = "false";

                    if(isset($_POST["tindakan_konfirmasi"])) {
                        $tindakan_konfirmasi = "true";
                    }
                    if(isset($_POST["diagnosa_konfirmasi"])) {
                        $diagnosa_konfirmasi = "true";
                    }
                    if(isset($_POST["indikasi_tindakan_konfirmasi"])) {
                        $indikasi_tindakan_konfirmasi = "true";
                    }
                    if(isset($_POST["tata_cara_konfirmasi"])) {
                        $tata_cara_konfirmasi = "true";
                    }
                    if(isset($_POST["tujuan_konfirmasi"])) {
                        $tujuan_konfirmasi = "true";
                    }
                    if(isset($_POST["risiko_konfirmasi"])) {
                        $risiko_konfirmasi = "true";
                    }
                    if(isset($_POST["komplikasi_konfirmasi"])) {
                        $komplikasi_konfirmasi = "true";
                    }
                    if(isset($_POST["prognosis_konfirmasi"])) {
                        $prognosis_konfirmasi = "true";
                    }
                    if(isset($_POST["alternatif_konfirmasi"])) {
                        $alternatif_konfirmasi = "true";
                    }
                    if(isset($_POST["lain_lain_konfirmasi"])) {
                        $lain_lain_konfirmasi = "true";
                    }
                    if(isset($_POST["biaya_konfirmasi"])) {
                        $biaya_konfirmasi = "true";
                    }
                    if(file_exists("../webapps/persetujuantindakan/pages/upload/".$nopersetujuan."PP.jpeg")){
                        @unlink("../webapps/persetujuantindakan/pages/upload/".$nopersetujuan."PP.jpeg");
                    }
                    $img               = $_POST["image"];
                    $folderPath        = "../webapps/persetujuantindakan/pages/upload/";
                    $image_parts       = explode(";base64,", $img);
                    $image_type_aux    = explode("image/", $image_parts[0]);
                    $image_type        = $image_type_aux[1];
                    $image_base64      = base64_decode($image_parts[1]);
                    $fileName          = $nopersetujuan."PP.jpeg";
                    $file              = $folderPath . $fileName;

                    if(file_put_contents($file, $image_base64)){
                        if(file_exists("../webapps/persetujuantindakan/pages/upload/".$nopersetujuan."PP.jpeg")){
                            if(Tambah3("bukti_persetujuan_penolakan_tindakan_penerimainformasi","'".$nopersetujuan."','pages/upload/$fileName'")){
                                Ubah2(
                                    "persetujuan_penolakan_tindakan","diagnosa_konfirmasi='$diagnosa_konfirmasi',tindakan_konfirmasi='$tindakan_konfirmasi',indikasi_tindakan_konfirmasi='$indikasi_tindakan_konfirmasi',".
                                    "tata_cara_konfirmasi='$tata_cara_konfirmasi',tujuan_konfirmasi='$tujuan_konfirmasi',risiko_konfirmasi='$risiko_konfirmasi',komplikasi_konfirmasi='$komplikasi_konfirmasi',".
                                    "prognosis_konfirmasi='$prognosis_konfirmasi',alternatif_konfirmasi='$alternatif_konfirmasi',biaya_konfirmasi='$biaya_konfirmasi',lain_lain_konfirmasi='$lain_lain_konfirmasi',".
                                    "pernyataan='$pilihansetuju' where no_pernyataan='$nopersetujuan'"
                                );
                                JSRedirect("index.php?act=AmbilPersetujuanPenolakanTindakan&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$nopersetujuan."\",\"photo\":\"pages/upload/$fileName\",\"photo2\":\"\"}","e")."");
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
                            <h2><center>PERNYATAAN PERSETUJUAN/PENOLAKAN TINDAKAN</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Maaf masih menunggu persetujuan</center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan",4);
            }
        }else{
            $querypersetujuan = bukaquery(
                "select persetujuan_penolakan_tindakan.saksi_keluarga,persetujuan_penolakan_tindakan.tanggal from persetujuan_penolakan_tindakan where persetujuan_penolakan_tindakan.no_pernyataan='$nopersetujuan'"
            );
            if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
                echo "<div class='row clearfix'>
                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='header'>
                                    <h2><center>PERNYATAAN PERSETUJUAN/PENOLAKAN TINDAKAN<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                    <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                                </div>
                                <div class='body'>
                                    <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                        <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                        <h7>
                                            <center>
                                                Saksi 1 Keluarga<br/>".$rsquerypersetujuan["saksi_keluarga"]."
                                            </center>
                                        </h7>
                                        <div class='row'>
                                            <div class='col-md-12 text-center'>
                                                <center><div id='my_camera'></div></center>
                                                <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                                <br/>
                                                <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai saksi 1 keluarga' onClick='take_snapshot()'/>
                                                <a href='index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                      </div>";

                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $nopersetujuan     = validTeks4($_POST["nopersetujuan"],20);
                    if(file_exists("../webapps/persetujuantindakan/pages/upload/".$nopersetujuan."SK.jpeg")){
                        @unlink("../webapps/persetujuantindakan/pages/upload/".$nopersetujuan."SK.jpeg");
                    }
                    $img               = $_POST["image"];
                    $folderPath        = "../webapps/persetujuantindakan/pages/upload/";
                    $image_parts       = explode(";base64,", $img);
                    $image_type_aux    = explode("image/", $image_parts[0]);
                    $image_type        = $image_type_aux[1];
                    $image_base64      = base64_decode($image_parts[1]);
                    $fileName          = $nopersetujuan."SK.jpeg";
                    $file              = $folderPath . $fileName;

                    if(file_put_contents($file, $image_base64)){
                        if(file_exists("../webapps/persetujuantindakan/pages/upload/".$nopersetujuan."SK.jpeg")){
                            if(Tambah3("bukti_persetujuan_penolakan_tindakan_saksikeluarga","'".$nopersetujuan."','pages/upload/$fileName'")){
                                JSRedirect("index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan");
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
                            <h2><center>PERNYATAAN PERSETUJUAN/PENOLAKAN TINDAKAN</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Maaf masih menunggu persetujuan</center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan",4);
            }
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERNYATAAN PERSETUJUAN/PENOLAKAN TINDAKAN</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan",4);
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