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
                "select surat_pulang_atas_permintaan_sendiri.tgl_pulang,surat_pulang_atas_permintaan_sendiri.rs_pilihan,surat_pulang_atas_permintaan_sendiri.nama_pj,".
                "surat_pulang_atas_permintaan_sendiri.lahir,surat_pulang_atas_permintaan_sendiri.umur,if(surat_pulang_atas_permintaan_sendiri.jkpj='L','LAKI-LAKI','PEREMPUAN') as jkpj,".
                "surat_pulang_atas_permintaan_sendiri.alamat,surat_pulang_atas_permintaan_sendiri.hubungan from surat_pulang_atas_permintaan_sendiri ".
                "where surat_pulang_atas_permintaan_sendiri.no_surat='$nopersetujuan'"
            );
            if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
                echo "<div class='row clearfix'>
                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='header'>
                                    <h2><center>PERNYATAAN PULANG ATAS PERMINTAAN SENDIRI<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tgl_pulang"]."</center></h2>
                                    <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                                </div>
                                <div class='body'>
                                    <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                        <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                        <input type='hidden' name='photo' value='$photo'>
                                        <h7>
                                            Saya yang membuat pernyataan di bawah ini :
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='25%'>Nama</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Jenis Kelamin</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["jkpj"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Tanggal Lahir</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["lahir"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Umur</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["umur"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Alamat</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["alamat"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Hubungan Dengan Pasien</td>
                                                <td width='75%'>: ".$rsquerypersetujuan["hubungan"]."</td>
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
                                                <td width='75%'>: ".($_SESSION["jk"]=="L"?"Laki-laki":"Perempuan")."</td>
                                            </tr>
                                            <tr>
                                                <td width='25%'>Tanggal Lahir</td>
                                                <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Dengan ini menyatakan bahwa : 
                                        </h7>
                                        <br/>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='2%' valign='top'>1.</td>
                                                <td width='98%' valign='top' align='justify'>Dengan sadar tanpa paksaan dari pihak manapun meminta kepada pihak ".$_SESSION["nama_instansi"]." untuk PULANG ATAS PERMINTAAN SENDIRI yang merupakan hak saya/pasien dengan Alasan/Pindah Rawat ".$rsquerypersetujuan["rs_pilihan"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='2%' valign='top'>2.</td>
                                                <td width='98%' valign='top' align='justify'>Saya telah memahami sepenuhnya penjelasan yang diberikan oleh pihak ".$_SESSION["nama_instansi"]." mengenai penyakit saya/pasien dan kemungkinan/konsekuensi terburuk atas keputusan yang saya ambil dan saya siap bertanggung jawab atas keputusan saya tersebut dan saya tidak akan menuntut pihak ".$_SESSION["nama_instansi"].".</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Demikian pernyataan ini saya buat dengan sesungguhnya untuk dipergunakan sebagaimana mestinya.
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
                                                <a href='index.php?act=PersetujuanPernyataanPulangAPS&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                    if(file_exists("../webapps/pulangaps/pages/upload/".$nopersetujuan."PP.jpeg")){
                        @unlink("../webapps/pulangaps/pages/upload/".$nopersetujuan."PP.jpeg");
                    }
                    $img               = $_POST["image"];
                    $folderPath        = "../webapps/pulangaps/pages/upload/";
                    $image_parts       = explode(";base64,", $img);
                    $image_type_aux    = explode("image/", $image_parts[0]);
                    $image_type        = $image_type_aux[1];
                    $image_base64      = base64_decode($image_parts[1]);
                    $fileName          = $nopersetujuan."PP.jpeg";
                    $file              = $folderPath . $fileName;

                    if(file_put_contents($file, $image_base64)){
                        if(file_exists("../webapps/pulangaps/pages/upload/".$nopersetujuan."PP.jpeg")){
                            if(Tambah3("surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan","'".$nopersetujuan."','pages/upload/$fileName'")){
                                JSRedirect("index.php?act=AmbilPersetujuanPernyataanPulangAPS&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$nopersetujuan."\",\"photo\":\"pages/upload/$fileName\",\"photo2\":\"\"}","e")."");
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
                            <h2><center>PERNYATAAN PULANG ATAS PERMINTAAN SENDIRI</center></h2>
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
                JSRedirect2("index.php?act=PersetujuanPernyataanPulangAPS&hal=Persetujuan",4);
            }
        }else{
            $querypersetujuan = bukaquery(
                "select surat_pulang_atas_permintaan_sendiri.saksi_keluarga,surat_pulang_atas_permintaan_sendiri.tgl_pulang from surat_pulang_atas_permintaan_sendiri where surat_pulang_atas_permintaan_sendiri.no_surat='$nopersetujuan'"
            );
            if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
                echo "<div class='row clearfix'>
                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='header'>
                                    <h2><center>PERNYATAAN PULANG ATAS PERMINTAAN SENDIRI<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tgl_pulang"]."</center></h2>
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
                                                <a href='index.php?act=PersetujuanPernyataanPulangAPS&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                    if(file_exists("../webapps/pulangaps/pages/upload/".$nopersetujuan."SK.jpeg")){
                        @unlink("../webapps/pulangaps/pages/upload/".$nopersetujuan."SK.jpeg");
                    }
                    $img               = $_POST["image"];
                    $folderPath        = "../webapps/pulangaps/pages/upload/";
                    $image_parts       = explode(";base64,", $img);
                    $image_type_aux    = explode("image/", $image_parts[0]);
                    $image_type        = $image_type_aux[1];
                    $image_base64      = base64_decode($image_parts[1]);
                    $fileName          = $nopersetujuan."SK.jpeg";
                    $file              = $folderPath . $fileName;

                    if(file_put_contents($file, $image_base64)){
                        if(file_exists("../webapps/pulangaps/pages/upload/".$nopersetujuan."SK.jpeg")){
                            if(Tambah3("surat_pulang_atas_permintaan_sendiri_saksi_keluarga","'".$nopersetujuan."','pages/upload/$fileName'")){
                                JSRedirect("index.php?act=PersetujuanPernyataanPulangAPS&hal=Persetujuan");
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
                            <h2><center>PERNYATAAN PULANG ATAS PERMINTAAN SENDIRI</center></h2>
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
                JSRedirect2("index.php?act=PersetujuanPernyataanPulangAPS&hal=Persetujuan",4);
            }
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERNYATAAN PULANG ATAS PERMINTAAN SENDIRI</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanPernyataanPulangAPS&hal=Persetujuan",4);
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