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
            "select surat_persetujuan_rawat_inap.tanggal,surat_persetujuan_rawat_inap.nama_pj,surat_persetujuan_rawat_inap.no_ktppj,surat_persetujuan_rawat_inap.pendidikan_pj,surat_persetujuan_rawat_inap.alamatpj,".
            "surat_persetujuan_rawat_inap.no_telppj,surat_persetujuan_rawat_inap.ruang,surat_persetujuan_rawat_inap.kelas,surat_persetujuan_rawat_inap.hubungan,surat_persetujuan_rawat_inap.hak_kelas,".
            "surat_persetujuan_rawat_inap.nama_alamat_keluarga_terdekat,surat_persetujuan_rawat_inap.bayar_secara from surat_persetujuan_rawat_inap where surat_persetujuan_rawat_inap.no_surat='$nopersetujuan'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN PASIEN RAWAT INAP<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                    <h7>
                                        Saya yang membuat persetujuan di bawah ini :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Nama</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Alamat</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["alamatpj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>No.KTP/SIM</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Pendidikan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["pendidikan_pj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>No.Telp/HP</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_telppj"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Terhadap Pasien : 
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
                                            <td width='75%'>: ".$_SESSION["jk"]."</td>
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
                                            <td width='25%'>No.Telp/No.HP</td>
                                            <td width='75%'>: ".$_SESSION["no_tlp"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Dengan ini menyatakan dengan sesungguhnya bahwa saya setuju untuk dilakukan Rawat Inap di ".$_SESSION["nama_instansi"]." di ruang : ".$rsquerypersetujuan["ruang"]." Kelas : ".$rsquerypersetujuan["kelas"]." Terhadap ".$rsquerypersetujuan["hubungan"]."
                                        <br/>
                                        Hak kelas perawatan : ".$rsquerypersetujuan["hak_kelas"]." 
                                        <br/>
                                        Nama dan alamat keluarga terdekat : ".$rsquerypersetujuan["nama_alamat_keluarga_terdekat"]." 
                                        <br/>
                                        Demi kelancaran pelayanan perawatan, pengobatan dan administrasi, dengan ini juga menyatakan :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='5%' valign='top'>a.</td>
                                            <td width='95%' valign='top' align='justify'>Setuju dan memberi ijin kepada dokter yang bersangkutan untuk merawat saya/pasien tersebut diatas</td>
                                        </tr>
                                        <tr>
                                            <td width='5%' valign='top'>b.</td>
                                            <td width='95%' valign='top' align='justify'>Dengan ini menyatakan dengan sesungguhnya bahwa seluruh pembiayaan pelayanan di ".$_SESSION["nama_instansi"]." akan saya bayarkan secara ".$rsquerypersetujuan["bayar_secara"].", dan bersedia untuk melengkapi berkas kelengkapannya. Apabila dalam waktu 3 x 24 Jam tidak dapat menunjukkan kartu/kelengkapan lainnya, maka saya siap untuk membayarkan semua pelayanan dan tindakan di ".$_SESSION["nama_instansi"].".</td>
                                        </tr>
                                        <tr>
                                            <td width='5%' valign='top'>c.</td>
                                            <td width='95%' valign='top' align='justify'>Telah menyetujui dan bersedia mentaati segala peraturan yang berlaku di ".$_SESSION["nama_instansi"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='5%' valign='top'>d.</td>
                                            <td width='95%' valign='top' align='justify'>Memberi kuasa kepada Dokter untuk memberikan keterangan yang diperlukan oleh pihak penanggung biaya perawatan saya / pasien tersebut diatas</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <br/>
                                    <h7><center>Yang Membuat Persetujuan</center></h7>
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div></center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                            <br/>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat persetujuan' onClick='take_snapshot()'/>
                                            <a href='index.php?act=PersetujuanRawatInap&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                if(file_exists("../webapps/persetujuanrawatinap/pages/upload/".$nopersetujuan.".jpeg")){
                    @unlink("../webapps/persetujuanrawatinap/pages/upload/".$nopersetujuan.".jpeg");
                }
                $img               = $_POST["image"];
                $folderPath        = "../webapps/persetujuanrawatinap/pages/upload/";
                $image_parts       = explode(";base64,", $img);
                $image_type_aux    = explode("image/", $image_parts[0]);
                $image_type        = $image_type_aux[1];
                $image_base64      = base64_decode($image_parts[1]);
                $fileName          = $nopersetujuan.".jpeg";
                $file              = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/persetujuanrawatinap/pages/upload/".$nopersetujuan.".jpeg")){
                        if(Tambah3("surat_persetujuan_rawat_inap_pembuat_pernyataan","'".$nopersetujuan."','pages/upload/$fileName'")){
                            JSRedirect("index.php?act=PersetujuanRawatInap&hal=Persetujuan");
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
                        <h2><center>PERSETUJUAN PASIEN RAWAT INAP</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanRawatInap&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN PASIEN RAWAT INAP</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanRawatInap&hal=Persetujuan",4);
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