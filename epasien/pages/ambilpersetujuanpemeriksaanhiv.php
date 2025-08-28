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
            "select surat_persetujuan_pemeriksaan_hiv.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,pasien.tgl_lahir,surat_persetujuan_pemeriksaan_hiv.tanggal,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,".
            "pasien.no_tlp,pasien.tmp_lahir,surat_persetujuan_pemeriksaan_hiv.nik,pegawai.nama,reg_periksa.umurdaftar,reg_periksa.sttsumur from surat_persetujuan_pemeriksaan_hiv inner join reg_periksa on surat_persetujuan_pemeriksaan_hiv.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis ".
            "inner join pegawai on surat_persetujuan_pemeriksaan_hiv.nik=pegawai.nik inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where surat_persetujuan_pemeriksaan_hiv.no_surat='".$nopersetujuan."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN PEMERIKSAAN HIV<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                    <h7>
                                        Yth. Laboratorium ".$_SESSION["nama_instansi"]."<br>
                                        di ".$_SESSION["kabupaten"]."<br><br>    
                                        Saya yang membuat persetujuan di bawah ini, menyatakan bahwa saya :
                                    </h7>
                                    <br/>
                                    <br/>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Nama Pasien</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["nm_pasien"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor Rekam Medis</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_rkm_medis"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Jenis Kelamin</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["jk"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Tanggal Lahir</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["tgl_lahir"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Umur</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["umurdaftar"]." ".$rsquerypersetujuan["sttsumur"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>No.Telp</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_tlp"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Alamat</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["alamat"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Telah mendapatkan informasi dan konseling dari Tim VCT untuk saya melakukan pemeriksaan lanjutan. Saya mengerti dan bersedia untuk melakukan pemeriksaan HIV terhadap diri saya. Persetujuan ini saya ambil dalam kondisi sadar tanpa pengaruh atau ancaman dari pihak lain.
                                        <br><br>
                                        Demikian Surat Persetujuan ini saya buat untuk ditindak lanjuti ke tahap pemeriksaan terhadap diri saya.
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
                                            <a href='index.php?act=PersetujuanPemeriksaanHIV&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                if(file_exists("../webapps/persetujuanpemeriksaanhiv/pages/upload/".$nopersetujuan.".jpeg")){
                    @unlink("../webapps/persetujuanpemeriksaanhiv/pages/upload/".$nopersetujuan.".jpeg");
                }
                $img               = $_POST["image"];
                $folderPath        = "../webapps/persetujuanpemeriksaanhiv/pages/upload/";
                $image_parts       = explode(";base64,", $img);
                $image_type_aux    = explode("image/", $image_parts[0]);
                $image_type        = $image_type_aux[1];
                $image_base64      = base64_decode($image_parts[1]);
                $fileName          = $nopersetujuan.".jpeg";
                $file              = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/persetujuanpemeriksaanhiv/pages/upload/".$nopersetujuan.".jpeg")){
                        if(Tambah3("surat_persetujuan_pemeriksaan_hiv_pembuat_persetujuan","'".$nopersetujuan."','pages/upload/$fileName'")){
                            JSRedirect("index.php?act=PersetujuanPemeriksaanHIV&hal=Persetujuan");
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
                        <h2><center>PERSETUJUAN PEMERIKSAAN HIV</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanPemeriksaanHIV&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN PEMERIKSAAN HIV</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanPemeriksaanHIV&hal=Persetujuan",4);
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