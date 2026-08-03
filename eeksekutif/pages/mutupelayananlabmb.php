<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncarilabmb  = $thnsekarang;
    $blncarilabmb  = $blnsekarang;
    $tglcarilabmb  = $tglsekarang;
    $thncarilabmb2 = $thnsekarang;
    $blncarilabmb2 = $blnsekarang;
    $tglcarilabmb2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncarilabmb  = validTeks(trim(isset($_POST['tgl_cari_labmb']))?substr($_POST['tgl_cari_labmb'],6,4):$thnsekarang);
        $blncarilabmb  = validTeks(trim(isset($_POST['tgl_cari_labmb']))?substr($_POST['tgl_cari_labmb'],3,2):$blnsekarang);
        $tglcarilabmb  = validTeks(trim(isset($_POST['tgl_cari_labmb']))?substr($_POST['tgl_cari_labmb'],0,2):$tglsekarang);
        $thncarilabmb2 = validTeks(trim(isset($_POST['tgl_cari_labmb2']))?substr($_POST['tgl_cari_labmb2'],6,4):$thnsekarang);
        $blncarilabmb2 = validTeks(trim(isset($_POST['tgl_cari_labmb2']))?substr($_POST['tgl_cari_labmb2'],3,2):$blnsekarang);
        $tglcarilabmb2 = validTeks(trim(isset($_POST['tgl_cari_labmb2']))?substr($_POST['tgl_cari_labmb2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>LAMA PELAYANAN LABORATORIUM MIKROBIOLOGI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_labmb">Tanggal</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_labmb" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarilabmb."-".$blncarilabmb."-".$thncarilabmb;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_labmb2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_labmb2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarilabmb2."-".$blncarilabmb2."-".$thncarilabmb2;?>"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Tampilkan Data</button></center>
                </form>
            </div>
            <div class="body" style="padding-top:0;">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th style="width:40%;"><center>Mutu Layanan</center></th>
                                <th><center>Permintaan-Sampel</center></th>
                                <th><center>Sampel-Hasil</center></th>
                                <th><center>Permintaan-Hasil</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $lamajam        = 0;
                            $limabelas      = 0;
                            $tigapuluh      = 0;
                            $satujam        = 0;
                            $lebihsatujam   = 0;
                            $lebihduajam    = 0;
                            $lamajam2       = 0;
                            $limabelas2     = 0;
                            $tigapuluh2     = 0;
                            $satujam2       = 0;
                            $lebihsatujam2  = 0;
                            $lebihduajam2   = 0;
                            $lamajam3       = 0;
                            $limabelas3     = 0;
                            $tigapuluh3     = 0;
                            $satujam3       = 0;
                            $lebihsatujam3  = 0;
                            $lebihduajam3   = 0;
                            $jumlahData     = 0;
                            $queryMutuLabMb = bukaquery(
                                "select reg_periksa.no_rkm_medis,dokter.nm_dokter,permintaan_labmb.noorder,".
                                "round((TIME_TO_SEC(concat(permintaan_labmb.tgl_sampel,' ',permintaan_labmb.jam_sampel))-TIME_TO_SEC(concat(permintaan_labmb.tgl_permintaan,' ',permintaan_labmb.jam_permintaan)))/60,2) as permintaansampel,".
                                "round((TIME_TO_SEC(concat(permintaan_labmb.tgl_hasil,' ',permintaan_labmb.jam_hasil))-TIME_TO_SEC(concat(permintaan_labmb.tgl_sampel,' ',permintaan_labmb.jam_sampel)))/60,2) as sampelhasil,".
                                "round((TIME_TO_SEC(concat(permintaan_labmb.tgl_hasil,' ',permintaan_labmb.jam_hasil))-TIME_TO_SEC(concat(permintaan_labmb.tgl_permintaan,' ',permintaan_labmb.jam_permintaan)))/60,2) as permintaanhasil ".
                                "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter ".
                                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis ".
                                "inner join permintaan_labmb on reg_periksa.no_rawat=permintaan_labmb.no_rawat ".
                                "where permintaan_labmb.tgl_sampel<>'0000-00-00' and permintaan_labmb.tgl_hasil<>'0000-00-00' ".
                                "and permintaan_labmb.tgl_permintaan between '$thncarilabmb-$blncarilabmb-$tglcarilabmb' and '$thncarilabmb2-$blncarilabmb2-$tglcarilabmb2' ".
                                "order by permintaan_labmb.tgl_permintaan,permintaan_labmb.jam_permintaan"
                            );
                            while($rsqueryMutuLabMb = mysqli_fetch_array($queryMutuLabMb)) {
                                $permintaansampel = $rsqueryMutuLabMb["permintaansampel"];
                                $sampelhasil      = $rsqueryMutuLabMb["sampelhasil"];
                                $permintaanhasil  = $rsqueryMutuLabMb["permintaanhasil"];
                                $jumlahData++;

                                $lamajam += $permintaansampel;
                                if($permintaansampel<=15){
                                    $limabelas++;
                                }elseif(($permintaansampel>15)&&($permintaansampel<=30)){
                                    $tigapuluh++;
                                }elseif(($permintaansampel>30)&&($permintaansampel<=60)){
                                    $satujam++;
                                }elseif(($permintaansampel>60)&&($permintaansampel<=120)){
                                    $lebihsatujam++;
                                }elseif($permintaansampel>120){
                                    $lebihduajam++;
                                }

                                $lamajam2 += $sampelhasil;
                                if($sampelhasil<=15){
                                    $limabelas2++;
                                }elseif(($sampelhasil>15)&&($sampelhasil<=30)){
                                    $tigapuluh2++;
                                }elseif(($sampelhasil>30)&&($sampelhasil<=60)){
                                    $satujam2++;
                                }elseif(($sampelhasil>60)&&($sampelhasil<=120)){
                                    $lebihsatujam2++;
                                }elseif($sampelhasil>120){
                                    $lebihduajam2++;
                                }

                                $lamajam3 += $permintaanhasil;
                                if($permintaanhasil<=15){
                                    $limabelas3++;
                                }elseif(($permintaanhasil>15)&&($permintaanhasil<=30)){
                                    $tigapuluh3++;
                                }elseif(($permintaanhasil>30)&&($permintaanhasil<=60)){
                                    $satujam3++;
                                }elseif(($permintaanhasil>60)&&($permintaanhasil<=120)){
                                    $lebihsatujam3++;
                                }elseif($permintaanhasil>120){
                                    $lebihduajam3++;
                                }
                            }
                        ?>
                            <tr>
                                <td style="width:40%;text-align:left;">Rata-rata (Menit)</td>
                                <td style="text-align:right;"><?=$jumlahData>0?number_format($lamajam/$jumlahData,2,',','.'):"0";?></td>
                                <td style="text-align:right;"><?=$jumlahData>0?number_format($lamajam2/$jumlahData,2,',','.'):"0";?></td>
                                <td style="text-align:right;"><?=$jumlahData>0?number_format($lamajam3/$jumlahData,2,',','.'):"0";?></td>
                            </tr>
                            <tr>
                                <td style="text-align:left;">0 - 15 Menit</td>
                                <td style="text-align:right;"><?=$limabelas;?></td>
                                <td style="text-align:right;"><?=$limabelas2;?></td>
                                <td style="text-align:right;"><?=$limabelas3;?></td>
                            </tr>
                            <tr>
                                <td style="text-align:left;">&gt;15 - &lt;=30 Menit</td>
                                <td style="text-align:right;"><?=$tigapuluh;?></td>
                                <td style="text-align:right;"><?=$tigapuluh2;?></td>
                                <td style="text-align:right;"><?=$tigapuluh3;?></td>
                            </tr>
                            <tr>
                                <td style="text-align:left;">&gt;30 - &lt;=60 Menit</td>
                                <td style="text-align:right;"><?=$satujam;?></td>
                                <td style="text-align:right;"><?=$satujam2;?></td>
                                <td style="text-align:right;"><?=$satujam3;?></td>
                            </tr>
                            <tr>
                                <td style="text-align:left;">&gt;60 - &lt;=120 Menit</td>
                                <td style="text-align:right;"><?=$lebihsatujam;?></td>
                                <td style="text-align:right;"><?=$lebihsatujam2;?></td>
                                <td style="text-align:right;"><?=$lebihsatujam3;?></td>
                            </tr>
                            <tr>
                                <td style="text-align:left;">&gt;120 Menit</td>
                                <td style="text-align:right;"><?=$lebihduajam;?></td>
                                <td style="text-align:right;"><?=$lebihduajam2;?></td>
                                <td style="text-align:right;"><?=$lebihduajam3;?></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>