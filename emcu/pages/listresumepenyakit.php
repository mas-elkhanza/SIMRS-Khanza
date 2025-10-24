<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncarimcu  = $thnsekarang;
    $blncarimcu  = $blnsekarang;
    $tglcarimcu  = $tglsekarang;
    $thncarimcu2 = $thnsekarang;
    $blncarimcu2 = $blnsekarang;
    $tglcarimcu2 = $tglsekarang;
    $perusahaan  = validTeks4(encrypt_decrypt($_SESSION["ses_emcu"],"d"),20);
    if(isset($_POST["BtnCari"])){
        $thncarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu']))?substr($_POST['tgl_cari_mcu'],6,4):$thnsekarang);
        $blncarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu']))?substr($_POST['tgl_cari_mcu'],3,2):$blnsekarang);
        $tglcarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu']))?substr($_POST['tgl_cari_mcu'],0,2):$tglsekarang);
        $thncarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2']))?substr($_POST['tgl_cari_mcu2'],6,4):$thnsekarang);
        $blncarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2']))?substr($_POST['tgl_cari_mcu2'],3,2):$blnsekarang);
        $tglcarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2']))?substr($_POST['tgl_cari_mcu2'],0,2):$tglsekarang);
    }
    
    if(isset($_GET['iyem'])){
        $json = json_decode(encrypt_decrypt($_GET['iyem'],"d"),true);
        if (isset($json["tgl_cari_mcu"])) {
            $thncarimcu  = validTeks(trim(isset($json['tgl_cari_mcu']))?substr($json['tgl_cari_mcu'],6,4):$thnsekarang);
            $blncarimcu  = validTeks(trim(isset($json['tgl_cari_mcu']))?substr($json['tgl_cari_mcu'],3,2):$blnsekarang);
            $tglcarimcu  = validTeks(trim(isset($json['tgl_cari_mcu']))?substr($json['tgl_cari_mcu'],0,2):$tglsekarang);
        }

        if (isset($json["tgl_cari_mcu2"])) {
            $thncarimcu2 = validTeks(trim(isset($json['tgl_cari_mcu2']))?substr($json['tgl_cari_mcu2'],6,4):$thnsekarang);
            $blncarimcu2 = validTeks(trim(isset($json['tgl_cari_mcu2']))?substr($json['tgl_cari_mcu2'],3,2):$blnsekarang);
            $tglcarimcu2 = validTeks(trim(isset($json['tgl_cari_mcu2']))?substr($json['tgl_cari_mcu2'],0,2):$tglsekarang);
        }
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>RESUME PENYAKIT</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_mcu">Tanggal MCU</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_mcu" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarimcu."-".$blncarimcu."-".$thncarimcu;?>"/>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="tgl_cari_mcu2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_mcu2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarimcu2."-".$blncarimcu2."-".$thncarimcu2;?>"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Cari Data Resume</button></center>
                </form>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable" width="1650px">
                        <thead>
                            <tr>
                                <th valign='middle'><center>No.RM</center></th>
                                <th valign='middle'><center>Nama Pasien</center></th>
                                <th valign='middle'><center>JK</center></th>
                                <th valign='middle'><center>Usia</center></th>
                                <th valign='middle'><center>No.KTP</center></th>
                                <th valign='middle'><center>Tgl.Lahir</center></th>
                                <th valign='middle'><center>No.Pegawai</center></th>
                                <th valign='middle'><center>Tgl.MCU</center></th>
                                <th valign='middle'><center>TD</center></th>
                                <th valign='middle'><center>BB</center></th>
                                <th valign='middle'><center>TB</center></th>
                                <th valign='middle'><center>Penyakit</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $querypasiencari = bukaquery(
                               "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,reg_periksa.umurdaftar,pasien.no_ktp,pasien.tgl_lahir,pasien.nip,penilaian_mcu.tanggal,penilaian_mcu.td,penilaian_mcu.bb,penilaian_mcu.tb,reg_periksa.no_rawat from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis ".
                               "inner join penilaian_mcu on reg_periksa.no_rawat=penilaian_mcu.no_rawat inner join booking_mcu_perusahaan_berhasil_registrasi on reg_periksa.no_rawat=booking_mcu_perusahaan_berhasil_registrasi.no_rawat inner join booking_mcu_perusahaan on booking_mcu_perusahaan_berhasil_registrasi.no_mcu=booking_mcu_perusahaan.no_mcu ".
                               "where booking_mcu_perusahaan.kode_perusahaan='$perusahaan' and booking_mcu_perusahaan.tanggal_mcu between '$thncarimcu-$blncarimcu-$tglcarimcu' and '$thncarimcu2-$blncarimcu2-$tglcarimcu2'"
                            );
                            while($rsquerypasiencari = mysqli_fetch_array($querypasiencari)) {
                                echo "<tr>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["no_rkm_medis"]."</td>
                                         <td align='left' valign='middle'>".$rsquerypasiencari["nm_pasien"]."</td>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["jk"]."</td>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["umurdaftar"]."</td>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["no_ktp"]."</td>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["tgl_lahir"]."</td>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["nip"]."</td>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["tanggal"]."</td>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["td"]."</td>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["bb"]."</td>
                                         <td align='center' valign='middle'>".$rsquerypasiencari["tb"]."</td>
                                         <td align='left' valign='middle'>".getOne("select penyakit.nm_penyakit from penyakit inner join diagnosa_pasien on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.prioritas='1' and diagnosa_pasien.no_rawat='".$rsquerypasiencari["no_rawat"]."'")."</td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
