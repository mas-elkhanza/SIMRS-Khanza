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
            "select surat_penolakan_anjuran_medis.tanggal,surat_penolakan_anjuran_medis.tanggal,surat_penolakan_anjuran_medis.hubungan,surat_penolakan_anjuran_medis.nama_pj,".
            "surat_penolakan_anjuran_medis.umur_pj,surat_penolakan_anjuran_medis.no_ktppj,surat_penolakan_anjuran_medis.jkpj,surat_penolakan_anjuran_medis.no_telp,".
            "surat_penolakan_anjuran_medis.kode_penolakan,surat_penolakan_anjuran_medis.alasan_penolakan,surat_penolakan_anjuran_medis.informasi_risiko_penolakan ".
            "from surat_penolakan_anjuran_medis where surat_penolakan_anjuran_medis.no_surat='".$nopersetujuan."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PENOLAKAN ANJURAN MEDIS<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                    <h7>
                                        Saya yang membuat penolakan di bawah ini, menyatakan bahwa :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Nama</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Umur</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["umur_pj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Jenis Kelamin</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["jkpj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor Telp/Nomor HP</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_telp"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor KTP</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
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
                                            <td width='75%'>: ".$_SESSION["tgl_lahir"] ."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Hubungan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["hubungan"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Menyatakan bahwa benar saya/pasien menolak <b>".$rsquerypersetujuan["nama_penolakan"]."</b>, dengan alasan <b>".$rsquerypersetujuan["alasan_penolakan"]."</b> serta sudah mendapatkan informasi mengenai resiko dari penolakan yang dilakukan yaitu <b>".$rsquerypersetujuan["informasi_risiko_penolakan"]."</b> dan tidak akan menuntut/menggugat pernyataaan ini dikemudian hari untuk alasan apapun.
                                        <br/><br/>
                                        Demikian peryataan ini saya buat dengan sebenar-benarnya. Atas perhatiannya saya ucapkan terima kasih.
                                    </h7>
                                    <br/>
                                    <br/>
                                    <h7><center>Yang Membuat Pernyataan</center></h7>
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div></center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                            <br/>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat persetujuan' onClick='take_snapshot()'/>
                                            <a href='index.php?act=PersetujuanPenolakanAnjuranMedis&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                if(file_exists("../webapps/penolakananjuranmedis/pages/upload/".$nopersetujuan.".jpeg")){
                    @unlink("../webapps/penolakananjuranmedis/pages/upload/".$nopersetujuan.".jpeg");
                }
                $img               = $_POST["image"];
                $folderPath        = "../webapps/penolakananjuranmedis/pages/upload/";
                $image_parts       = explode(";base64,", $img);
                $image_type_aux    = explode("image/", $image_parts[0]);
                $image_type        = $image_type_aux[1];
                $image_base64      = base64_decode($image_parts[1]);
                $fileName          = $nopersetujuan.".jpeg";
                $file              = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/penolakananjuranmedis/pages/upload/".$nopersetujuan.".jpeg")){
                        if(Tambah3("surat_penolakan_anjuran_medis_pembuat_pernyataan","'".$nopersetujuan."','pages/upload/$fileName'")){
                            JSRedirect("index.php?act=PersetujuanPenolakanAnjuranMedis&hal=Persetujuan");
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
                        <h2><center>PENOLAKAN ANJURAN MEDIS</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanPenolakanAnjuranMedis&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PENOLAKAN ANJURAN MEDIS</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanPenolakanAnjuranMedis&hal=Persetujuan",4);
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