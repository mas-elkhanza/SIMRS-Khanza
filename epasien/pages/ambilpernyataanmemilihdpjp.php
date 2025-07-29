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
    if (isset($iyem["nopernyataan"])) {
        $nopernyataan    = validTeks3($iyem["nopernyataan"],20);
        $photo            = validTeks3($iyem["photo"],50);
        $photo2           = validTeks3($iyem["photo2"],50);
        if($photo==""){
            $querypernyataan = bukaquery(
                "select surat_pernyataan_memilih_dpjp.tanggal,dokter.nm_dokter,surat_pernyataan_memilih_dpjp.pembuat_pernyataan,surat_pernyataan_memilih_dpjp.alamat_pembuat_pernyataan,".
                "DATE_FORMAT(surat_pernyataan_memilih_dpjp.tgl_lahir_pembuat_pernyataan,'%d-%m-%Y') as tgl_lahir_pembuat_pernyataan,if(surat_pernyataan_memilih_dpjp.jk_pembuat_pernyataan='L','LAKI-LAKI','PEREMPUAN') as jk_pembuat_pernyataan,".
                "surat_pernyataan_memilih_dpjp.hubungan_pembuat_pernyataan from surat_pernyataan_memilih_dpjp inner join dokter on surat_pernyataan_memilih_dpjp.kd_dokter=dokter.kd_dokter ".
                "where surat_pernyataan_memilih_dpjp.no_pernyataan='$nopernyataan'" 
            );
            if($rsquerypernyataan= mysqli_fetch_array($querypernyataan)){
                echo "<div class='row clearfix'>
                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='header'>
                                    <h2><center>PERNYATAAN KEINGINAN MEMILIH DPJP RAWAT INAP<br/>No. $nopernyataan Tanggal ".$rsquerypernyataan["tanggal"]."</center></h2>
                                    <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                                </div>
                                <div class='body'>
                                    <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                        <input type='hidden' name='nopernyataan' value='$nopernyataan'>
                                        <input type='hidden' name='photo' value='$photo'>
                                        <h7>
                                            Saya yang bertanda tangan di bawah ini :
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='30%'>Nama</td>
                                                <td width='75%'>: ".$rsquerypernyataan["pembuat_pernyataan"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Alamat</td>
                                                <td width='70%'>: ".$rsquerypernyataan["alamat_pembuat_pernyataan"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Tanggal Lahir</td>
                                                <td width='70%'>: ".$rsquerypernyataan["tgl_lahir_pembuat_pernyataan"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Jenis Kelamin</td>
                                                <td width='70%'>: ".$rsquerypernyataan["jk_pembuat_pernyataan"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Hubungan Dengan Pasien</td>
                                                <td width='70%'>: ".$rsquerypernyataan["hubungan_pembuat_pernyataan"]."</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Atas pasien dengan identitas sebagai berikut :  
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='30%'>Nama Pasien</td>
                                                <td width='70%'>: ".$_SESSION["nm_pasien"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Nomor Rekam Medis</td>
                                                <td width='70%'>: ".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."</td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Jenis Kelamin</td>
                                                <td width='70%'>: ".($_SESSION["jk"]=="L"?"Laki-laki":"Perempuan")."</td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Tempat Lahir</td>
                                                <td width='70%'>: ".$_SESSION["tmp_lahir"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Tanggal Lahir</td>
                                                <td width='70%'>: ".$_SESSION["tgl_lahir"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='30%'>Nomor Identitas</td>
                                                <td width='70%'>: ".$_SESSION["no_ktp"]."</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Dengan ini menyatakan bahwa :  
                                        </h7>
                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                            <tr>
                                                <td width='5%'>1.</td>
                                                <td width='95%'>Telah menerima dan memahami informasi mengenai dokter penanggung jawab pasien selama dirawat di ".$_SESSION["nama_instansi"]."</td>
                                            </tr>
                                            <tr>
                                                <td width='5%'>2.</td>
                                                <td width='95%'>Berdasarkan hal tersebut diatas saya memilih ".$rsquerypernyataan["nm_dokter"]." sebagai dokter penanggung jawab</td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <h7>
                                            Demikian pernyataan ini saya buat dengan sesungguhnya untuk diketahui dan digunakan sebagaimana mestinya.
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
                                                <a href='index.php?act=PernyataanMemilihDPJP&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                      </div>";

                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $nopernyataan          = validTeks4($_POST["nopernyataan"],20);
                    if(file_exists("../webapps/pernyataanmemilihdpjp/pages/upload/".$nopernyataan."PP.jpeg")){
                        @unlink("../webapps/pernyataanmemilihdpjp/pages/upload/".$nopernyataan."PP.jpeg");
                    }
                    $img               = $_POST["image"];
                    $folderPath        = "../webapps/pernyataanmemilihdpjp/pages/upload/";
                    $image_parts       = explode(";base64,", $img);
                    $image_type_aux    = explode("image/", $image_parts[0]);
                    $image_type        = $image_type_aux[1];
                    $image_base64      = base64_decode($image_parts[1]);
                    $fileName          = $nopernyataan."PP.jpeg";
                    $file              = $folderPath . $fileName;

                    if(file_put_contents($file, $image_base64)){
                        if(file_exists("../webapps/pernyataanmemilihdpjp/pages/upload/".$nopernyataan."PP.jpeg")){
                            if(Tambah3("bukti_surat_pernyataan_memilih_dpjp","'".$nopernyataan."','pages/upload/$fileName'")){
                                JSRedirect("index.php?act=AmbilPernyataanMemilihDPJP&iyem=".encrypt_decrypt("{\"nopernyataan\":\"".$nopernyataan."\",\"photo\":\"pages/upload/$fileName\",\"photo2\":\"\"}","e")."");
                            }
                        }else{
                            echo "<div class='row clearfix'>
                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                       <div class='card'>
                                           <div class='body'>
                                               <center>Gagal melakukan pernyataan</center>
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
                                            <center>Gagal melakukan pernyataan</center>
                                        </div>
                                    </div>
                                 </div>
                              </div>";
                    }
                }
            }else{
                echo "<div class='block-header'>
                            <h2><center>PERNYATAAN KEINGINAN MEMILIH DPJP RAWAT INAP</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Maaf masih menunggu pernyataan</center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=PernyataanMemilihDPJP&hal=Persetujuan",4);
            }
        }else{
            $querypernyataan = bukaquery(
                "select surat_pernyataan_memilih_dpjp.saksi_keluarga,surat_pernyataan_memilih_dpjp.tanggal from surat_pernyataan_memilih_dpjp where surat_pernyataan_memilih_dpjp.no_pernyataan='$nopernyataan'"
            );
            if($rsquerypernyataan= mysqli_fetch_array($querypernyataan)){
                echo "<div class='row clearfix'>
                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='header'>
                                    <h2><center>PERNYATAAN KEINGINAN MEMILIH DPJP RAWAT INAP<br/>No. $nopernyataan Tanggal ".$rsquerypernyataan["tanggal"]."</center></h2>
                                    <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                                </div>
                                <div class='body'>
                                    <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                        <input type='hidden' name='nopernyataan' value='$nopernyataan'>
                                        <h7>
                                            <center>
                                                Saksi 1 Keluarga<br/>".$rsquerypernyataan["saksi_keluarga"]."
                                            </center>
                                        </h7>
                                        <div class='row'>
                                            <div class='col-md-12 text-center'>
                                                <center><div id='my_camera'></div></center>
                                                <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                                <br/>
                                                <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai saksi 1 keluarga' onClick='take_snapshot()'/>
                                                <a href='index.php?act=PernyataanMemilihDPJP&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                      </div>";

                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $nopernyataan     = validTeks4($_POST["nopernyataan"],20);
                    if(file_exists("../webapps/pernyataanmemilihdpjp/pages/upload/".$nopernyataan."SK.jpeg")){
                        @unlink("../webapps/pernyataanmemilihdpjp/pages/upload/".$nopernyataan."SK.jpeg");
                    }
                    $img               = $_POST["image"];
                    $folderPath        = "../webapps/pernyataanmemilihdpjp/pages/upload/";
                    $image_parts       = explode(";base64,", $img);
                    $image_type_aux    = explode("image/", $image_parts[0]);
                    $image_type        = $image_type_aux[1];
                    $image_base64      = base64_decode($image_parts[1]);
                    $fileName          = $nopernyataan."SK.jpeg";
                    $file              = $folderPath . $fileName;

                    if(file_put_contents($file, $image_base64)){
                        if(file_exists("../webapps/pernyataanmemilihdpjp/pages/upload/".$nopernyataan."SK.jpeg")){
                            if(Tambah3("bukti_surat_pernyataan_memilih_dpjp_saksikeluarga","'".$nopernyataan."','pages/upload/$fileName'")){
                                JSRedirect("index.php?act=PernyataanMemilihDPJP&hal=Persetujuan");
                            }
                        }else{
                            echo "<div class='row clearfix'>
                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                       <div class='card'>
                                           <div class='body'>
                                               <center>Gagal melakukan pernyataan</center>
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
                                            <center>Gagal melakukan pernyataan</center>
                                        </div>
                                    </div>
                                 </div>
                              </div>";
                    }
                }
            }else{
                echo "<div class='block-header'>
                            <h2><center>PERNYATAAN KEINGINAN MEMILIH DPJP RAWAT INAP</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Maaf masih menunggu pernyataan</center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=PernyataanMemilihDPJP&hal=Persetujuan",4);
            }
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERNYATAAN KEINGINAN MEMILIH DPJP RAWAT INAP</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf pernyataan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=PernyataanMemilihDPJP&hal=Persetujuan",4);
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