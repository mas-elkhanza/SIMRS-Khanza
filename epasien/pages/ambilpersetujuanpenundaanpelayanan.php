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
        $querypersetujuan = bukaquery(
            "select persetujuan_penundaan_pelayanan.tanggal,persetujuan_penundaan_pelayanan.nama_pj,persetujuan_penundaan_pelayanan.umur_pj,persetujuan_penundaan_pelayanan.no_ktppj,persetujuan_penundaan_pelayanan.alamatpj,".
            "persetujuan_penundaan_pelayanan.no_telppj,persetujuan_penundaan_pelayanan.hubungan,persetujuan_penundaan_pelayanan.ruang,persetujuan_penundaan_pelayanan.dokter_pengirim,persetujuan_penundaan_pelayanan.pelayanan_dilakukan,".
            "persetujuan_penundaan_pelayanan.ditunda_karena,persetujuan_penundaan_pelayanan.keterangan_ditunda,persetujuan_penundaan_pelayanan.alternatif_diberikan,persetujuan_penundaan_pelayanan.keterangan_alternatif_diberikan ".
            "from persetujuan_penundaan_pelayanan where persetujuan_penundaan_pelayanan.no_surat='".$nopersetujuan."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN PENUNDAAN PELAYANAN<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                    <h7>
                                        Saya yang membuat persetujuan di bawah ini yang menerima informasi (Pasien Sendiri / Keluarga Pasien / Penanggung Jawab Pasien) :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Nama</td>
                                            <td width='70%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Umur</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["umur_pj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor KTP</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Alamat Tinggal</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["alamatpj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor Telp/HP</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_telppj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Hubungan Dengan Pasien</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["hubungan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Poli / Unit / Ruangan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["ruang"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Dokter Pengirim</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["dokter_pengirim"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Pelayanan Yang Akan Dilakukan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["pelayanan_dilakukan"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Terhadap Pasien : 
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Nama Pasien</td>
                                            <td width='70%'>: ".$_SESSION["nm_pasien"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor Rekam Medis</td>
                                            <td width='75%'>: ".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Jenis Kelamin</td>
                                            <td width='75%'>: ".$_SESSION["jk"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Tanggal Lahir</td>
                                            <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Dengan ini menyatakan bahwa saya telah menerima informasi terhadap penundaan pelayanan dikarenakan : ".$rsquerypersetujuan["ditunda_karena"].($rsquerypersetujuan["keterangan_ditunda"]==""?"":", ".$rsquerypersetujuan["keterangan_ditunda"]).". Maka dengan ini saya <b>SETUJU</b> untuk dilakukan penundaan pelayanan dengan alternatif yang diberikan : ".$rsquerypersetujuan["alternatif_diberikan"].($rsquerypersetujuan["keterangan_alternatif_diberikan"]==""?"":", ".$rsquerypersetujuan["keterangan_alternatif_diberikan"])."
                                    </h7>
                                    <br/>
                                    <br/>
                                    <h7><center>Yang Membuat Persetujuan</center></h7>
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div></center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                            <br/>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat persetujuan' onClick='take_snapshot()'/>
                                            <a href='index.php?act=PersetujuanPenundaanPelayanan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                if(file_exists("../webapps/penundaanpelayanan/pages/upload/".$nopersetujuan.".jpeg")){
                    @unlink("../webapps/penundaanpelayanan/pages/upload/".$nopersetujuan.".jpeg");
                }
                $img               = $_POST["image"];
                $folderPath        = "../webapps/penundaanpelayanan/pages/upload/";
                $image_parts       = explode(";base64,", $img);
                $image_type_aux    = explode("image/", $image_parts[0]);
                $image_type        = $image_type_aux[1];
                $image_base64      = base64_decode($image_parts[1]);
                $fileName          = $nopersetujuan.".jpeg";
                $file              = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/penundaanpelayanan/pages/upload/".$nopersetujuan.".jpeg")){
                        if(Tambah3("bukti_persetujuan_penundaan_pelayanan","'".$nopersetujuan."','pages/upload/$fileName'")){
                            JSRedirect("index.php?act=PersetujuanPenundaanPelayanan&hal=Persetujuan");
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
                        <h2><center>PERSETUJUAN PENUNDAAN PELAYANAN</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanPenundaanPelayanan&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN PENUNDAAN PELAYANAN</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanPenundaanPelayanan&hal=Persetujuan",4);
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