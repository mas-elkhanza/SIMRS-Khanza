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
            "select DATE_FORMAT(permintaan_binrohtal.tanggal,'%d-%m-%Y %H:%i:%s') as tanggal,permintaan_binrohtal.no_surat,permintaan_binrohtal.no_rawat,permintaan_binrohtal.nip,pegawai.nama,".
            "permintaan_binrohtal.kd_dokter,dokter.nm_dokter,permintaan_binrohtal.kd_bangsal,bangsal.nm_bangsal,permintaan_binrohtal.jns_permintaan,ifnull(nullif(permintaan_binrohtal.agama,''),pasien.agama) as agama,".
            "permintaan_binrohtal.jns_pelayanan,permintaan_binrohtal.ket_pelayanan,permintaan_binrohtal.respon,permintaan_binrohtal.ket_respon,permintaan_binrohtal.keterangan from permintaan_binrohtal ".
            "inner join reg_periksa on permintaan_binrohtal.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on permintaan_binrohtal.nip=pegawai.nik ".
            "left join dokter on permintaan_binrohtal.kd_dokter=dokter.kd_dokter inner join bangsal on permintaan_binrohtal.kd_bangsal=bangsal.kd_bangsal where permintaan_binrohtal.no_surat='$nopersetujuan'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN BIMBINGAN ROHANI DAN MENTAL<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                    <h7>
                                        Data pasien yang akan mendapatkan pelayanan bimbingan rohani dan mental :
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
                                        <tr>
                                            <td width='25%'>Agama</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["agama"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Detail permintaan pelayanan bimbingan rohani dan mental :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='25%'>Nomor Surat</td>
                                            <td width='75%'>: ".$nopersetujuan."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Ruang / Bangsal</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["kd_bangsal"]."-".$rsquerypersetujuan["nm_bangsal"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Jenis Permintaan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["jns_permintaan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Jenis Pelayanan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["jns_pelayanan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Keterangan Pelayanan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["ket_pelayanan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Respon</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["respon"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Keterangan Respon</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["ket_respon"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Keterangan Tambahan</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["keterangan"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Pelayanan bimbingan rohani dan mental dilaksanakan oleh <b>".$rsquerypersetujuan["nama"]."</b> (NIP ".$rsquerypersetujuan["nip"].") dengan dokter penanggung jawab pasien <b>".$rsquerypersetujuan["nm_dokter"]."</b>.
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
                                            <a href='index.php?act=PersetujuanBimbinganRohani&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
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
                if(file_exists("../webapps/permintaanbinrohtal/pages/upload/".$nopersetujuan.".jpeg")){
                    @unlink("../webapps/permintaanbinrohtal/pages/upload/".$nopersetujuan.".jpeg");
                }
                $img               = $_POST["image"];
                $folderPath        = "../webapps/permintaanbinrohtal/pages/upload/";
                $image_parts       = explode(";base64,", $img);
                $image_type_aux    = explode("image/", $image_parts[0]);
                $image_type        = $image_type_aux[1];
                $image_base64      = base64_decode($image_parts[1]);
                $fileName          = $nopersetujuan.".jpeg";
                $file              = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/permintaanbinrohtal/pages/upload/".$nopersetujuan.".jpeg")){
                        try{
                            if(Tambah3("bukti_permintaan_binrohtal","'".$nopersetujuan."','pages/upload/$fileName'")){
                                JSRedirect("index.php?act=PersetujuanBimbinganRohani&hal=Persetujuan");
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
                        <h2><center>PERSETUJUAN BIMBINGAN ROHANI DAN MENTAL</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanBimbinganRohani&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN BIMBINGAN ROHANI DAN MENTAL</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanBimbinganRohani&hal=Persetujuan",4);
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