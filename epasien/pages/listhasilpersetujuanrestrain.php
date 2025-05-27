<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat          = validTeks3($iyem["norawat"],20);
        $photo            = validTeks3($iyem["photo"],50);
        $querypersetujuan = bukaquery(
            "select pengkajian_restrain.tanggal,pengkajian_restrain.gcs,pengkajian_restrain.refleka_cahaya_ka,pengkajian_restrain.refleka_cahaya_ki,pengkajian_restrain.ukuran_pupil_ka,".
            "pengkajian_restrain.ukuran_pupil_ki,pengkajian_restrain.td,pengkajian_restrain.suhu,pengkajian_restrain.rr,pengkajian_restrain.nadi,pengkajian_restrain.hasil_observasi,".
            "pengkajian_restrain.pertimbangan_klinis,pengkajian_restrain.restrain_non_farmakologi,pengkajian_restrain.restrain_non_farmakologi_keterangan,pengkajian_restrain.restrain_farmakologi,".
            "pengkajian_restrain.sudah_dijelaskan_keluarga,pengkajian_restrain.keluarga_yang_menyetujui from pengkajian_restrain where pengkajian_restrain.no_rawat='$norawat'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/pengkajianrestrain/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN RESTRAIN<br/>No.Rawat $norawat Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Terhadap Pasien : 
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama Pasien</td>
                                        <td width='75%'>: ".$_SESSION["nm_pasien"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>No.R.M.</td>
                                        <td width='75%'>: ".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jenis Kelamin</td>
                                        <td width='75%'>: ".($_SESSION["jk"]=="L"?"Laki-laki":"Perempuan")."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tanggal Lahir</td>
                                        <td width='75%'>: ".$_SESSION["tmp_lahir"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Pengkajian Restrain : 
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='100%'>
                                            Pengkajian Fisik & Mental :
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr>
                                                    <td width='25%'>a. Kesadaran </td>
                                                    <td width='75%'>
                                                        GCS : ".$rsquerypersetujuan["gcs"]."&nbsp;&nbsp;&nbsp;
                                                        Refleka Cahaya Kanan : ".$rsquerypersetujuan["refleka_cahaya_ka"]."&nbsp;&nbsp;&nbsp;
                                                        Refleka Cahaya Kiri : ".$rsquerypersetujuan["refleka_cahaya_ki"]."&nbsp;&nbsp;&nbsp;
                                                        Ukuran Pupil Kanan : ".$rsquerypersetujuan["ukuran_pupil_ka"]."&nbsp;&nbsp;&nbsp;
                                                        Ukuran Pupil Kiri : ".$rsquerypersetujuan["ukuran_pupil_ki"]."
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width='25%'>b. Tanda Vital</td>
                                                    <td width='75%'>
                                                        Tensi Darah : ".$rsquerypersetujuan["td"]." mmHg&nbsp;&nbsp;&nbsp;
                                                        Suhu : ".$rsquerypersetujuan["suhu"]." °C&nbsp;&nbsp;&nbsp;
                                                        Nadi : ".$rsquerypersetujuan["nadi"]." x/menit&nbsp;&nbsp;&nbsp;
                                                        RR : ".$rsquerypersetujuan["rr"]." x/menit
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width='25%'>c. Hasil Observasi</td>
                                                    <td width='75%'>".$rsquerypersetujuan["hasil_observasi"]."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%'>
                                            Pengkajian & Order Dokter :
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr>
                                                    <td width='20%'>Restrain Non Farmakologi</td>
                                                    <td width='80%'>: ".$rsquerypersetujuan["restrain_non_farmakologi"].($rsquerypersetujuan["restrain_non_farmakologi_keterangan"]==""?"":", ".$rsquerypersetujuan["restrain_non_farmakologi_keterangan"])."</td>
                                                </tr>
                                                <tr>
                                                    <td width='20%'>Restrain Farmakologi</td>
                                                    <td width='80%'>: ".$rsquerypersetujuan["restrain_farmakologi"]."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%'>Pertimbangan Klinis : ".$rsquerypersetujuan["pertimbangan_klinis"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Pendidikan Restrain Pada Keluarga :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='40%'>Keluarga Yang Menyetujui</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["keluarga_yang_menyetujui"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='40%'>Keluarga Sudah Dijelaskan Tentang Restrain</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["sudah_dijelaskan_keluarga"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <br/>
                                <h7><center>Keluarga/Saudara Yang Menyetujui</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>
                                        <a href='index.php?act=PersetujuanRestrain&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERSETUJUAN RESTRAIN</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanRestrain&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN RESTRAIN</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanRestrain&hal=Persetujuan",4);
    }
?>