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
            "select DATE_FORMAT(surat_pengajuan_cuti_pasien.tanggal,'%d-%m-%Y %H:%i:%s') as tanggal,surat_pengajuan_cuti_pasien.no_surat,surat_pengajuan_cuti_pasien.no_rawat,surat_pengajuan_cuti_pasien.nip,pegawai.nama,".
            "surat_pengajuan_cuti_pasien.kd_dokter,dokter.nm_dokter,surat_pengajuan_cuti_pasien.kd_bangsal,bangsal.nm_bangsal,surat_pengajuan_cuti_pasien.pembuat_pengajuan,surat_pengajuan_cuti_pasien.alamat_pembuat_pengajuan,".
            "surat_pengajuan_cuti_pasien.tgl_lahir_pembuat_pengajuan,surat_pengajuan_cuti_pasien.jk_pembuat_pengajuan,surat_pengajuan_cuti_pasien.hubungan_pembuat_pengajuan,surat_pengajuan_cuti_pasien.notelp,".
            "surat_pengajuan_cuti_pasien.alasan_cuti,surat_pengajuan_cuti_pasien.mulai_cuti,surat_pengajuan_cuti_pasien.alamat_selama_cuti,surat_pengajuan_cuti_pasien.kembali_dari_cuti from surat_pengajuan_cuti_pasien ".
            "inner join reg_periksa on surat_pengajuan_cuti_pasien.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on surat_pengajuan_cuti_pasien.nip=pegawai.nik ".
            "inner join dokter on surat_pengajuan_cuti_pasien.kd_dokter=dokter.kd_dokter inner join bangsal on surat_pengajuan_cuti_pasien.kd_bangsal=bangsal.kd_bangsal where surat_pengajuan_cuti_pasien.no_surat='$nopersetujuan'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PENGAJUAN CUTI PERAWATAN<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                    <h7>
                                        Saya yang bertanda tangan di bawah ini : 
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Nama</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["pembuat_pengajuan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Alamat</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["alamat_pembuat_pengajuan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Tanggal Lahir</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["tgl_lahir_pembuat_pengajuan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Jenis Kelamin</td>
                                            <td width='75%'>: ".($rsquerypersetujuan["jk_pembuat_pengajuan"]=="L"?"LAKI-LAKI":"PEREMPUAN")."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Hubungan Dengan Pasien</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["hubungan_pembuat_pengajuan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor Telp/HP</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["notelp"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Bermaksud mengajukan cuti perawatan untuk pasien :
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
                                            <td width='25%'>No.Rawat</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_rawat"]."</td>
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
                                        <tr>
                                            <td width='25%'>Ruang / Bangsal</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["kd_bangsal"]."-".$rsquerypersetujuan["nm_bangsal"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Dokter Penanggung Jawab</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["nm_dokter"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7 style='display:block;text-align:justify;'>
                                        Dengan alasan <b>".$rsquerypersetujuan["alasan_cuti"]."</b>. Mulai cuti ".$rsquerypersetujuan["mulai_cuti"]." dan akan kembali pada tanggal ".$rsquerypersetujuan["kembali_dari_cuti"].". Alamat selama cuti ".$rsquerypersetujuan["alamat_selama_cuti"].". Dan saya akan mengikuti peraturan sesuai dengan ketentuan yang berlaku selama di rumah sakit.
                                        Bila terjadi sewaktu hal selama saya cuti, maka tidak menjadi tanggung jawab pihak ".$_SESSION["nama_instansi"].".<br><br>Demikian surat permintaan cuti ini dan atas kerjasamanya saya ucapkan terima kasih.
                                    </h7>
                                    <br/>
                                    <br/>
                                    <h7><center>Yang Membuat Pengajuan</center></h7>
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div></center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                            <br/>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat pengajuan' onClick='take_snapshot()'/>
                                            <a href='index.php?act=PengajuanCutiPerawatan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                if(file_exists("../webapps/pengajuancutiperawatan/pages/upload/".$nopersetujuan.".jpeg")){
                    @unlink("../webapps/pengajuancutiperawatan/pages/upload/".$nopersetujuan.".jpeg");
                }
                $img               = $_POST["image"];
                $folderPath        = "../webapps/pengajuancutiperawatan/pages/upload/";
                $image_parts       = explode(";base64,", $img);
                $image_type_aux    = explode("image/", $image_parts[0]);
                $image_type        = $image_type_aux[1];
                $image_base64      = base64_decode($image_parts[1]);
                $fileName          = $nopersetujuan.".jpeg";
                $file              = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/pengajuancutiperawatan/pages/upload/".$nopersetujuan.".jpeg")){
                        try{
                            if(Tambah3("bukti_surat_pengajuan_cuti_pasien","'".$nopersetujuan."','pages/upload/$fileName'")){
                                JSRedirect("index.php?act=PengajuanCutiPerawatan&hal=Persetujuan");
                            }
                        } catch(mysqli_sql_exception $e) {
                            if($e->getCode()==1062){
                                echo "<div class='row clearfix'>
                                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                           <div class='card'>
                                               <div class='body'>
                                                   <center>Data bukti pengajuan sudah ada</center>
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
                                           <center>Gagal melakukan pengajuan</center>
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
                                        <center>Gagal melakukan pengajuan</center>
                                    </div>
                                </div>
                             </div>
                          </div>";
                }
            }
        }else{
            echo "<div class='block-header'>
                        <h2><center>PENGAJUAN CUTI PERAWATAN</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu pengajuan</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=PengajuanCutiPerawatan&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PENGAJUAN CUTI PERAWATAN</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf pengajuan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=PengajuanCutiPerawatan&hal=Persetujuan",4);
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