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
            "select surat_permohonan_privasi.no_surat,surat_permohonan_privasi.tanggal,surat_permohonan_privasi.nama_pj,surat_permohonan_privasi.umur_pj,".
            "surat_permohonan_privasi.no_ktppj,surat_permohonan_privasi.jkpj,surat_permohonan_privasi.alamatpj,surat_permohonan_privasi.bertindak_atas,".
            "surat_permohonan_privasi.no_telp,surat_permohonan_privasi.kategori_privasi,surat_permohonan_privasi.alasan from surat_permohonan_privasi ".
            "where surat_permohonan_privasi.no_surat='".$nopersetujuan."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERMOHONAN PRIVASI PASIEN<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                    <h7>
                                        Yang bertanda tangan di bawah ini, selanjutnya disebut sebagai pemohon :
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
                                            <td width='25%'>Nomor Identitas</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Jenis Kelamin</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["jkpj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Alamat</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["alamatpj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>No. Telp</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_telp"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Hubungan Dengan Pasien</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["bertindak_atas"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Data pasien yang diajukan permohonan privasinya :
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
                                            <td width='25%'>Tanggal Lahir</td>
                                            <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>No.Telp/No.HP</td>
                                            <td width='75%'>: ".$_SESSION["no_tlp"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Dengan ini mengajukan permohonan privasi pasien dengan rincian sebagai berikut :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Kategori Privasi</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["kategori_privasi"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Alasan Permohonan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["alasan"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <br/>
                                    <h7><center>Yang Membuat Permohonan</center></h7>
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div></center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                            <br/>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat permohonan' onClick='take_snapshot()'/>
                                            <a href='index.php?act=PermohonanPrivasiPasien&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                if(file_exists("../webapps/permohonanprivasi/pages/upload/".$nopersetujuan.".jpeg")){
                    @unlink("../webapps/permohonanprivasi/pages/upload/".$nopersetujuan.".jpeg");
                }
                $img               = $_POST["image"];
                $folderPath        = "../webapps/permohonanprivasi/pages/upload/";
                $image_parts       = explode(";base64,", $img);
                $image_type_aux    = explode("image/", $image_parts[0]);
                $image_type        = $image_type_aux[1];
                $image_base64      = base64_decode($image_parts[1]);
                $fileName          = $nopersetujuan.".jpeg";
                $file              = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/permohonanprivasi/pages/upload/".$nopersetujuan.".jpeg")){
                        try{
                            if(Tambah3("surat_permohonan_privasi_pembuat_permohonan","'".$nopersetujuan."','pages/upload/$fileName'")){
                                JSRedirect("index.php?act=PermohonanPrivasiPasien&hal=Persetujuan");
                            }
                        } catch(mysqli_sql_exception $e) {
                            if($e->getCode()==1062){
                                echo "<div class='row clearfix'>
                                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                           <div class='card'>
                                               <div class='body'>
                                                   <center>Data bukti pelayanan sudah ada</center>
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
                                           <center>Gagal melakukan permohonan</center>
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
                                        <center>Gagal melakukan permohonan</center>
                                    </div>
                                </div>
                             </div>
                          </div>";
                }
            }
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERMOHONAN PRIVASI PASIEN</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu permohonan</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=PermohonanPrivasiPasien&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERMOHONAN PRIVASI PASIEN</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf permohonan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=PermohonanPrivasiPasien&hal=Persetujuan",4);
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