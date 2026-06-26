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
            "select surat_perlindungan_dari_kekerasan.no_surat,DATE_FORMAT(surat_perlindungan_dari_kekerasan.tanggal,'%d-%m-%Y') as tanggal,".
            "surat_perlindungan_dari_kekerasan.nama_pj,surat_perlindungan_dari_kekerasan.no_ktppj,surat_perlindungan_dari_kekerasan.tempat_lahirpj,".
            "DATE_FORMAT(surat_perlindungan_dari_kekerasan.lahirpj,'%d-%m-%Y') as lahirpj,surat_perlindungan_dari_kekerasan.alamatpj,".
            "surat_perlindungan_dari_kekerasan.hubungan,surat_perlindungan_dari_kekerasan.no_telp,if(surat_perlindungan_dari_kekerasan.jkpj='L','LAKI-LAKI','PEREMPUAN') as jkpj ".
            "from surat_perlindungan_dari_kekerasan where surat_perlindungan_dari_kekerasan.no_surat='$nopersetujuan'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERMINTAAN PERLINDUNGAN DARI KEKERASAN FISIK <br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                    <h7>
                                        Yang bertanda tangan di bawah ini :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Nama</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Tempat/Tanggal Lahir</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["tempat_lahirpj"]." ".$rsquerypersetujuan["lahirpj"]."</td>
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
                                            <td width='25%'>Hubungan Dengan Pasien</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["hubungan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor Telp / HP</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_telp"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor KTP</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Data pasien yang dimohonkan perlindungan :
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
                                    <h7 style='display:block;text-align:justify;'>
                                        Dengan ini mengajukan permohonan agar selama dilakukan perawatan di <b>".$_SESSION["nama_instansi"]."</b>,
                                        pasien tersebut di atas diberikan perlindungan dari tindakan kekerasan fisik maupun
                                        perlakuan yang membahayakan keselamatan pasien.
                                    </h7>
                                    <br/>
                                    <br/>
                                    <h7 style='display:block;text-align:justify;'>
                                        Saya menyatakan bahwa data yang saya sampaikan adalah benar dan dapat dipergunakan
                                        sebagaimana mestinya untuk kepentingan pelayanan dan perlindungan pasien selama dirawat.
                                    </h7>
                                    <br/>
                                    <br/>
                                    <h7 style='display:block;text-align:justify;'>
                                        Demikian surat permintaan ini saya buat dengan sebenar-benarnya, tanpa paksaan dari pihak manapun.
                                        Atas perhatian dan tindak lanjutnya saya ucapkan terima kasih.
                                    </h7>
                                    <br/>
                                    <br/>
                                    <h7><center>Pemohon / Penanggung Jawab</center></h7>
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div></center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                            <br/>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat permohonan' onClick='take_snapshot()'/>
                                            <a href='index.php?act=PermintaanPerlindunganKekerasan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                if(file_exists("../webapps/perlindungankekerasan/pages/upload/".$nopersetujuan.".jpeg")){
                    @unlink("../webapps/perlindungankekerasan/pages/upload/".$nopersetujuan.".jpeg");
                }
                $img               = $_POST["image"];
                $folderPath        = "../webapps/perlindungankekerasan/pages/upload/";
                $image_parts       = explode(";base64,", $img);
                $image_type_aux    = explode("image/", $image_parts[0]);
                $image_type        = $image_type_aux[1];
                $image_base64      = base64_decode($image_parts[1]);
                $fileName          = $nopersetujuan.".jpeg";
                $file              = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/perlindungankekerasan/pages/upload/".$nopersetujuan.".jpeg")){
                        try{
                            if(Tambah3("surat_perlindungan_dari_kekerasan_pembuat_permintaan","'".$nopersetujuan."','pages/upload/$fileName'")){
                                JSRedirect("index.php?act=PermintaanPerlindunganKekerasan&hal=Persetujuan");
                            }
                        } catch(mysqli_sql_exception $e) {
                            if($e->getCode()==1062){
                                echo "<div class='row clearfix'>
                                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                           <div class='card'>
                                               <div class='body'>
                                                   <center>Data bukti permohonan sudah ada</center>
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
                        <h2><center>PERMINTAAN PERLINDUNGAN DARI KEKERASAN FISIK </center></h2>
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
            JSRedirect2("index.php?act=PermintaanPerlindunganKekerasan&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERMINTAAN PERLINDUNGAN DARI KEKERASAN FISIK </center></h2>
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
        JSRedirect2("index.php?act=PermintaanPerlindunganKekerasan&hal=Persetujuan",4);
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