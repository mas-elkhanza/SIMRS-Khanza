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
        $tanggal          = validTeks4($iyem["tanggal"],20);
        $querypersetujuan = bukaquery(
            "select transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,transfer_pasien_antar_ruang.pasien_keluarga_menyetujui ".
            "from transfer_pasien_antar_ruang where transfer_pasien_antar_ruang.tanggal_masuk='$tanggal' and transfer_pasien_antar_ruang.no_rawat='$norawat'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN PEMINDAHAN/TRANSFER RUANG PASIEN<br/>No.Rawat $norawat Tanggal $tanggal</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='norawat' value='$norawat'>
                                    <input type='hidden' name='tanggal' value='$tanggal'>
                                    <h7>
                                        Saya yang membuat persetujuan ini menerangkan bahwa :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Nama</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["nama_menyetujui"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Hubungan Dengan Pasien</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["hubungan_menyetujui"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Menyatakan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["pasien_keluarga_menyetujui"]."&nbsp;Menyetujui Pemindahan Pasien</td>
                                        </tr>
                                        <tr>
                                            <td width='100%' colspan='2'>Dari pasien ".$_SESSION["nama_instansi"]." dengan : </td>
                                        </tr>
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
                                        Bahwa saya telah membaca, memahami dan mendapatkan penjelasan tentang kondisi pasien di ".$_SESSION["nama_instansi"].". Demikian persetujuan ini dibuat dalam keadaan penuh kesadaran dan tanpa paksaan dari pihak manapun, untuk digunakan sebagaimana mestinya
                                    </h7>
                                    <br/>
                                    <br/>
                                    <h7><center>Yang Membuat Persetujuan</center></h7>
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div><center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi1'));' id='TxtIsi1'>
                                            <br/>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat pernyataan' onClick='take_snapshot()'>
                                            <a href='index.php?act=PersetujuanTransferAntarRuang&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                if(file_exists("../webapps/persetujuantransferruang/pages/upload/".str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg")){
                    @unlink("../webapps/persetujuantransferruang/pages/upload/".str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg");
                }
                $img                    = $_POST["image"];
                $folderPath             = "../webapps/persetujuantransferruang/pages/upload/";
                $image_parts            = explode(";base64,", $img);
                $image_type_aux         = explode("image/", $image_parts[0]);
                $image_type             = $image_type_aux[1];
                $image_base64           = base64_decode($image_parts[1]);
                $fileName               = str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg";
                $file                   = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/persetujuantransferruang/pages/upload/".str_replace("/","",$norawat).str_replace(":","",str_replace("-","",str_replace(" ","",$tanggal))).".jpeg")){
                        if(Tambah3("bukti_persetujuan_transfer_pasien_antar_ruang","'".$norawat."','".$tanggal."','pages/upload/$fileName'")){
                            JSRedirect("index.php?act=PersetujuanTransferAntarRuang&hal=Persetujuan");
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
            }
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERSETUJUAN PEMINDAHAN/TRANSFER RUANG PASIEN</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanTransferAntarRuang&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN PEMINDAHAN/TRANSFER RUANG PASIEN</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanTransferAntarRuang&hal=Persetujuan",4);
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