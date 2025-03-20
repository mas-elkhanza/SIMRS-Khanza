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
    if (isset($iyem["norawat"])) {
        $norawat          = validTeks3($iyem["norawat"],20);
        $querypersetujuan = bukaquery(
            "select DATE_FORMAT(layanan_program_kfr.tanggal,'%d-%m-%Y %H:%i:%s') as tanggalpelayanan,layanan_program_kfr.program from layanan_program_kfr where layanan_program_kfr.no_rawat='$norawat'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PENGAMBILAN BUKTI LAYANAN PROGRAM KFR<br/>No.Rawat $norawat Tanggal ".$rsquerypersetujuan["tanggalpelayanan"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='norawat' value='$norawat'>
                                    <h7>
                                        Saya yang dibawah ini, pasien di ".$_SESSION["nama_instansi"]." dengan :
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
                                        Bahwa saya telah mendapatkan pelayanan Program Kedokteran Fisik & Rehabilitasi \"".$rsquerypersetujuan["program"]."\" di ".$_SESSION["nama_instansi"].". Demikian pernyataan ini dibuat dalam keadaan penuh kesadaran untuk digunakan sebagaimana mestinya.
                                    </h7>
                                    <br/>
                                    <br/>
                                    <h7><center>Yang Membuat Pernyataan</center></h7>
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div><center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi1'));' id='TxtIsi1'>
                                            <br/>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat pernyataan' onClick='take_snapshot()'>
                                            <a href='index.php?act=BuktiPelayananProgramKFR&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                  </div>";
            
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $norawat           = validTeks4($_POST["norawat"],20);
                $tanggal           = validTeks4($_POST["tanggal"],20);
                if(file_exists("../webapps/layananprogramkfr/pages/upload/".str_replace("/","",$norawat).".jpeg")){
                    @unlink("../webapps/layananprogramkfr/pages/upload/".str_replace("/","",$norawat).".jpeg");
                }
                $img                    = $_POST["image"];
                $folderPath             = "../webapps/layananprogramkfr/pages/upload/";
                $image_parts            = explode(";base64,", $img);
                $image_type_aux         = explode("image/", $image_parts[0]);
                $image_type             = $image_type_aux[1];
                $image_base64           = base64_decode($image_parts[1]);
                $fileName               = str_replace("/","",$norawat).".jpeg";
                $file                   = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/layananprogramkfr/pages/upload/".str_replace("/","",$norawat).".jpeg")){
                        if(Tambah3("bukti_layanan_program_kfr","'".$norawat."','pages/upload/$fileName'")){
                            JSRedirect("index.php?act=BuktiPelayananProgramKFR&hal=Persetujuan");
                        }
                    }
                }else{
                    echo "<div class='row clearfix'>
                             <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                <div class='card'>
                                    <div class='body'>
                                        <center>Gagal mengambil penyataan</center>
                                    </div>
                                </div>
                             </div>
                          </div>";
                }
            }
        }else{
            echo "<div class='block-header'>
                        <h2><center>PENGAMBILAN BUKTI LAYANAN PROGRAM KFR</center></h2>
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
            JSRedirect2("index.php?act=BuktiPelayananProgramKFR&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PENGAMBILAN BUKTI LAYANAN PROGRAM KFR</center></h2>
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
        JSRedirect2("index.php?act=BuktiPelayananProgramKFR&hal=Persetujuan",4);
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