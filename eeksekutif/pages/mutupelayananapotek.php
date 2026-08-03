<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncariapt  = $thnsekarang;
    $blncariapt  = $blnsekarang;
    $tglcariapt  = $tglsekarang;
    $thncariapt2 = $thnsekarang;
    $blncariapt2 = $blnsekarang;
    $tglcariapt2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncariapt  = validTeks(trim(isset($_POST['tgl_cari_apt']))?substr($_POST['tgl_cari_apt'],6,4):$thnsekarang);
        $blncariapt  = validTeks(trim(isset($_POST['tgl_cari_apt']))?substr($_POST['tgl_cari_apt'],3,2):$blnsekarang);
        $tglcariapt  = validTeks(trim(isset($_POST['tgl_cari_apt']))?substr($_POST['tgl_cari_apt'],0,2):$tglsekarang);
        $thncariapt2 = validTeks(trim(isset($_POST['tgl_cari_apt2']))?substr($_POST['tgl_cari_apt2'],6,4):$thnsekarang);
        $blncariapt2 = validTeks(trim(isset($_POST['tgl_cari_apt2']))?substr($_POST['tgl_cari_apt2'],3,2):$blnsekarang);
        $tglcariapt2 = validTeks(trim(isset($_POST['tgl_cari_apt2']))?substr($_POST['tgl_cari_apt2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>LAMA PELAYANAN APOTEK</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_apt">Tanggal</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_apt" class="datepicker form-control" required autocomplete="off" value="<?=$tglcariapt."-".$blncariapt."-".$thncariapt;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_apt2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_apt2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcariapt2."-".$blncariapt2."-".$thncariapt2;?>"/>
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
                                <th><center>Peresepan-Validasi</center></th>
                                <th><center>Validasi-Penyerahan</center></th>
                                <th><center>Peresepan-Penyerahan</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $lamajam        = 0;
                            $limabelas      = 0;
                            $tigapuluh      = 0;
                            $satujam        = 0;
                            $lebihsatujam   = 0;
                            $lamajam2       = 0;
                            $limabelas2     = 0;
                            $tigapuluh2     = 0;
                            $satujam2       = 0;
                            $lebihsatujam2  = 0;
                            $lamajam3       = 0;
                            $limabelas3     = 0;
                            $tigapuluh3     = 0;
                            $satujam3       = 0;
                            $lebihsatujam3  = 0;
                            $jumlahData     = 0;
                            $queryMutuApt   = bukaquery(
                                "select reg_periksa.no_rkm_medis,dokter.nm_dokter,poliklinik.nm_poli,".
                                "round((TIME_TO_SEC(concat(resep_obat.tgl_perawatan,' ',resep_obat.jam))-TIME_TO_SEC(concat(resep_obat.tgl_peresepan,' ',resep_obat.jam_peresepan)))/60,2) as durasivalidasi,".
                                "round((TIME_TO_SEC(concat(resep_obat.tgl_penyerahan,' ',resep_obat.jam_penyerahan))-TIME_TO_SEC(concat(resep_obat.tgl_perawatan,' ',resep_obat.jam)))/60,2) as durasipenyerahan,".
                                "round((TIME_TO_SEC(concat(resep_obat.tgl_penyerahan,' ',resep_obat.jam_penyerahan))-TIME_TO_SEC(concat(resep_obat.tgl_peresepan,' ',resep_obat.jam_peresepan)))/60,2) as durasipelayanan ".
                                "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter ".
                                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis ".
                                "inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli ".
                                "inner join resep_obat on reg_periksa.no_rawat=resep_obat.no_rawat ".
                                "where resep_obat.tgl_peresepan<>'0000-00-00' and resep_obat.tgl_penyerahan<>'0000-00-00' and resep_obat.tgl_perawatan<>'0000-00-00' ".
                                "and resep_obat.tgl_peresepan between '$thncariapt-$blncariapt-$tglcariapt' and '$thncariapt2-$blncariapt2-$tglcariapt2' ".
                                "order by resep_obat.tgl_peresepan,resep_obat.jam_peresepan"
                            );
                            while($rsqueryMutuApt = mysqli_fetch_array($queryMutuApt)) {
                                $durasivalidasi   = $rsqueryMutuApt["durasivalidasi"];
                                $durasipenyerahan = $rsqueryMutuApt["durasipenyerahan"];
                                $durasipelayanan  = $rsqueryMutuApt["durasipelayanan"];
                                $jumlahData++;

                                $lamajam += $durasivalidasi;
                                if($durasivalidasi<=15){
                                    $limabelas++;
                                }elseif(($durasivalidasi>15)&&($durasivalidasi<=30)){
                                    $tigapuluh++;
                                }elseif(($durasivalidasi>30)&&($durasivalidasi<=60)){
                                    $satujam++;
                                }elseif($durasivalidasi>60){
                                    $lebihsatujam++;
                                }

                                $lamajam2 += $durasipenyerahan;
                                if($durasipenyerahan<=15){
                                    $limabelas2++;
                                }elseif(($durasipenyerahan>15)&&($durasipenyerahan<=30)){
                                    $tigapuluh2++;
                                }elseif(($durasipenyerahan>30)&&($durasipenyerahan<=60)){
                                    $satujam2++;
                                }elseif($durasipenyerahan>60){
                                    $lebihsatujam2++;
                                }

                                $lamajam3 += $durasipelayanan;
                                if($durasipelayanan<=15){
                                    $limabelas3++;
                                }elseif(($durasipelayanan>15)&&($durasipelayanan<=30)){
                                    $tigapuluh3++;
                                }elseif(($durasipelayanan>30)&&($durasipelayanan<=60)){
                                    $satujam3++;
                                }elseif($durasipelayanan>60){
                                    $lebihsatujam3++;
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
                                <td style="text-align:left;">&gt;60 Menit</td>
                                <td style="text-align:right;"><?=$lebihsatujam;?></td>
                                <td style="text-align:right;"><?=$lebihsatujam2;?></td>
                                <td style="text-align:right;"><?=$lebihsatujam3;?></td>
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