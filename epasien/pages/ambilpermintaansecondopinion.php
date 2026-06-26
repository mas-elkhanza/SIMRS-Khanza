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
                "select surat_permintaan_second_opinion.tanggal,dokter.nm_dokter,surat_permintaan_second_opinion.pembuat_pernyataan,surat_permintaan_second_opinion.alamat_pembuat_pernyataan,".
                "DATE_FORMAT(surat_permintaan_second_opinion.tgl_lahir_pembuat_pernyataan,'%d-%m-%Y') as tgl_lahir_pembuat_pernyataan,if(surat_permintaan_second_opinion.jk_pembuat_pernyataan='L','LAKI-LAKI','PEREMPUAN') as jk_pembuat_pernyataan,".
                "surat_permintaan_second_opinion.hubungan_pembuat_pernyataan from surat_permintaan_second_opinion inner join dokter on surat_permintaan_second_opinion.kd_dokter=dokter.kd_dokter ".
                "where surat_permintaan_second_opinion.no_pernyataan='$nopersetujuan'"
            );
            if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
                echo "<div class='row clearfix'>
                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='header'>
                                    <h2><center>PERMINTAAN SECOND OPINION<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                    <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                                </div>
                                <div class='body'>
                                    <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                        <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                        <input type='hidden' name='photo' value='$photo'>
                                        <h7>
                                            Yang bertanda tangan di bawah ini :
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='25%'>Nama</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["pembuat_pernyataan"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Alamat</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["alamat_pembuat_pernyataan"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Tanggal Lahir</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["tgl_lahir_pembuat_pernyataan"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Jenis Kelamin</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["jk_pembuat_pernyataan"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Hubungan Dengan Pasien</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["hubungan_pembuat_pernyataan"]."</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Dengan data pasien sebagai berikut :
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='25%'>Nama Pasien</td>
                                                <td width='75%'>: ".$_SESSION["nm_pasien"]."</td>
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
                                                <td width='25%'>Tempat Lahir</td>
                                                <td width='75%'>: ".$_SESSION["tmp_lahir"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Tanggal Lahir</td>
                                                <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>No.KTP/NIK/Pengenal</td>
                                                <td width='75%'>: ".$_SESSION["no_ktp"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>No.Telp</td>
                                                <td width='75%'>: ".$_SESSION["no_tlp"]."</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Dengan ini mengajukan permintaan second opinion atas kondisi pasien tersebut :
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='5%'>1.</td>
                                                <td width='95%'>Permintaan second opinion ini diajukan untuk memperoleh pendapat medis lain atas kondisi pasien di ".$_SESSION["nm_pasien"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='5%'>2.</td>
                                                <td width='95%'>Dokter tujuan second opinion yang diajukan adalah ".$rsquerypersetujuan["nm_dokter"]."</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Demikian surat permintaan second opinion ini dibuat untuk dipergunakan sebagaimana mestinya.
                                        </h7>
                                        <br/>
                                        <br/>
                                        <h7><center>Pembuat Permintaan</center></h7>
                                        <div class='row'>
                                            <div class='col-md-12 text-center'>
                                                <center><div id='my_camera'></div></center>
                                                <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                                <br/>
                                                <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat permintaan' onClick='take_snapshot()'/>
                                                <a href='index.php?act=PermintaanSecondOpinion&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                    if(file_exists("../webapps/secondopinion/pages/upload/".$nopersetujuan."PP.jpeg")){
                        @unlink("../webapps/secondopinion/pages/upload/".$nopersetujuan."PP.jpeg");
                    }
                    $img               = $_POST["image"];
                    $folderPath        = "../webapps/secondopinion/pages/upload/";
                    $image_parts       = explode(";base64,", $img);
                    $image_type_aux    = explode("image/", $image_parts[0]);
                    $image_type        = $image_type_aux[1];
                    $image_base64      = base64_decode($image_parts[1]);
                    $fileName          = $nopersetujuan."PP.jpeg";
                    $file              = $folderPath . $fileName;

                    if(file_put_contents($file, $image_base64)){
                        if(file_exists("../webapps/secondopinion/pages/upload/".$nopersetujuan."PP.jpeg")){
                            try{
                                if(Tambah3("bukti_surat_permintaan_second_opinion","'".$nopersetujuan."','pages/upload/$fileName'")){
                                    JSRedirect("index.php?act=AmbilPermintaanSecondOpinion&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$nopersetujuan."\",\"photo\":\"pages/upload/$fileName\",\"photo2\":\"\"}","e")."");
                                }
                            } catch(mysqli_sql_exception $e) {
                                if($e->getCode()==1062){
                                    echo "<div class='row clearfix'>
                                            <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                               <div class='card'>
                                                   <div class='body'>
                                                       <center>Data bukti permintaan sudah ada</center>
                                                   </div>
                                               </div>
                                            </div>
                                          </div>";
                                }else{
                                    echo "<div class='row clearfix'>
                                            <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                               <div class='card'>
                                                   <div class='body'>
                                                       <center>Gagal menyimpan</center>
                                                   </div>
                                               </div>
                                            </div>
                                          </div>";
                                }
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
                            <h2><center>PERMINTAAN SECOND OPINION</center></h2>
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
                JSRedirect2("index.php?act=PermintaanSecondOpinion&hal=Persetujuan",4);
            }
        }else{
            $querypersetujuan = bukaquery(
                "select surat_permintaan_second_opinion.saksi_keluarga,surat_permintaan_second_opinion.tanggal from surat_permintaan_second_opinion where surat_permintaan_second_opinion.no_pernyataan='$nopersetujuan'"
            );
            if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
                echo "<div class='row clearfix'>
                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='header'>
                                    <h2><center>PERMINTAAN SECOND OPINION<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
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
                                                <a href='index.php?act=PermintaanSecondOpinion&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                    if(file_exists("../webapps/secondopinion/pages/upload/".$nopersetujuan."SK.jpeg")){
                        @unlink("../webapps/secondopinion/pages/upload/".$nopersetujuan."SK.jpeg");
                    }
                    $img               = $_POST["image"];
                    $folderPath        = "../webapps/secondopinion/pages/upload/";
                    $image_parts       = explode(";base64,", $img);
                    $image_type_aux    = explode("image/", $image_parts[0]);
                    $image_type        = $image_type_aux[1];
                    $image_base64      = base64_decode($image_parts[1]);
                    $fileName          = $nopersetujuan."SK.jpeg";
                    $file              = $folderPath . $fileName;

                    if(file_put_contents($file, $image_base64)){
                        if(file_exists("../webapps/secondopinion/pages/upload/".$nopersetujuan."SK.jpeg")){
                            try{
                                if(Tambah3("bukti_surat_permintaan_second_opinion_saksikeluarga","'".$nopersetujuan."','pages/upload/$fileName'")){
                                    JSRedirect("index.php?act=PermintaanSecondOpinion&hal=Persetujuan");
                                }
                            } catch(mysqli_sql_exception $e) {
                                if($e->getCode()==1062){
                                    echo "<div class='row clearfix'>
                                            <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                               <div class='card'>
                                                   <div class='body'>
                                                       <center>Data bukti persetujuan sudah ada</center>
                                                   </div>
                                               </div>
                                            </div>
                                          </div>";
                                }else{
                                    echo "<div class='row clearfix'>
                                            <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                               <div class='card'>
                                                   <div class='body'>
                                                       <center>Gagal menyimpan</center>
                                                   </div>
                                               </div>
                                            </div>
                                          </div>";
                                }
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
                            <h2><center>PERMINTAAN SECOND OPINION</center></h2>
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
                JSRedirect2("index.php?act=PermintaanSecondOpinion&hal=Persetujuan",4);
            }
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERMINTAAN SECOND OPINION</center></h2>
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
        JSRedirect2("index.php?act=PermintaanSecondOpinion&hal=Persetujuan",4);
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