<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncaripoli  = $thnsekarang;
    $blncaripoli  = $blnsekarang;
    $tglcaripoli  = $tglsekarang;
    $thncaripoli2 = $thnsekarang;
    $blncaripoli2 = $blnsekarang;
    $tglcaripoli2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncaripoli  = validTeks(trim(isset($_POST['tgl_cari_poli']))?substr($_POST['tgl_cari_poli'],6,4):$thnsekarang);
        $blncaripoli  = validTeks(trim(isset($_POST['tgl_cari_poli']))?substr($_POST['tgl_cari_poli'],3,2):$blnsekarang);
        $tglcaripoli  = validTeks(trim(isset($_POST['tgl_cari_poli']))?substr($_POST['tgl_cari_poli'],0,2):$tglsekarang);
        $thncaripoli2 = validTeks(trim(isset($_POST['tgl_cari_poli2']))?substr($_POST['tgl_cari_poli2'],6,4):$thnsekarang);
        $blncaripoli2 = validTeks(trim(isset($_POST['tgl_cari_poli2']))?substr($_POST['tgl_cari_poli2'],3,2):$blnsekarang);
        $tglcaripoli2 = validTeks(trim(isset($_POST['tgl_cari_poli2']))?substr($_POST['tgl_cari_poli2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>LAMA PELAYANAN POLI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_poli">Tanggal</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_poli" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripoli."-".$blncaripoli."-".$thncaripoli;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_poli2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_poli2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripoli2."-".$blncaripoli2."-".$thncaripoli2;?>"/>
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
                                <th style="width:70%;"><center>Mutu Layanan</center></th>
                                <th><center>Durasi</center></th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                                $totaljam       = 0;
                                $limabelas      = 0;
                                $tigapuluh      = 0;
                                $satujam        = 0;
                                $lebihsatujam   = 0;
                                $jumlahData     = 0;
                                $rekapDokter    = [];
                                $rekapPoli      = [];
                                $queryMutuPoli  = bukaquery(
                                    "select distinct reg_periksa.no_rkm_medis,dokter.nm_dokter,poliklinik.nm_poli,round((TIME_TO_SEC(concat(pemeriksaan_ralan.tgl_perawatan,' ',pemeriksaan_ralan.jam_rawat))-TIME_TO_SEC(concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg)))/60,2) as durasi ".
                                    "from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join pemeriksaan_ralan on reg_periksa.no_rawat=pemeriksaan_ralan.no_rawat ".
                                    "where reg_periksa.tgl_registrasi between '$thncaripoli-$blncaripoli-$tglcaripoli' and '$thncaripoli2-$blncaripoli2-$tglcaripoli2' group by pemeriksaan_ralan.no_rawat"
                                );
                                while($rsqueryMutuPoli = mysqli_fetch_array($queryMutuPoli)) {
                                    $durasi     = $rsqueryMutuPoli["durasi"];
                                    $namaDokter = $rsqueryMutuPoli["nm_dokter"];
                                    $namaPoli   = $rsqueryMutuPoli["nm_poli"];
                                    $totaljam += $durasi;
                                    $jumlahData++;
                                    if($durasi<=15){
                                        $limabelas++;
                                    }elseif(($durasi>15)&&($durasi<=30)){
                                        $tigapuluh++;
                                    }elseif(($durasi>30)&&($durasi<=60)){
                                        $satujam++;
                                    }elseif($durasi>60){
                                        $lebihsatujam++;
                                    }
                                    if(!isset($rekapDokter[$namaDokter])){
                                        $rekapDokter[$namaDokter] = [
                                            'totaljam'     => 0,
                                            'jumlah'       => 0,
                                            'limabelas'    => 0,
                                            'tigapuluh'    => 0,
                                            'satujam'      => 0,
                                            'lebihsatujam' => 0
                                        ];
                                    }
                                    $rekapDokter[$namaDokter]['totaljam'] += $durasi;
                                    $rekapDokter[$namaDokter]['jumlah']++;
                                    if($durasi<=15){
                                        $rekapDokter[$namaDokter]['limabelas']++;
                                    }elseif(($durasi>15)&&($durasi<=30)){
                                        $rekapDokter[$namaDokter]['tigapuluh']++;
                                    }elseif(($durasi>30)&&($durasi<=60)){
                                        $rekapDokter[$namaDokter]['satujam']++;
                                    }elseif($durasi>60){
                                        $rekapDokter[$namaDokter]['lebihsatujam']++;
                                    }
                                    if(!isset($rekapPoli[$namaPoli])){
                                        $rekapPoli[$namaPoli] = [
                                            'totaljam'     => 0,
                                            'jumlah'       => 0,
                                            'limabelas'    => 0,
                                            'tigapuluh'    => 0,
                                            'satujam'      => 0,
                                            'lebihsatujam' => 0
                                        ];
                                    }
                                    $rekapPoli[$namaPoli]['totaljam'] += $durasi;
                                    $rekapPoli[$namaPoli]['jumlah']++;
                                    if($durasi<=15){
                                        $rekapPoli[$namaPoli]['limabelas']++;
                                    }elseif(($durasi>15)&&($durasi<=30)){
                                        $rekapPoli[$namaPoli]['tigapuluh']++;
                                    }elseif(($durasi>30)&&($durasi<=60)){
                                        $rekapPoli[$namaPoli]['satujam']++;
                                    }elseif($durasi>60){
                                        $rekapPoli[$namaPoli]['lebihsatujam']++;
                                    }
                                }
                                ksort($rekapDokter);
                                ksort($rekapPoli);
                            ?>
                            <tr>
                                <td style="width:70%;text-align:left;">Rata-rata (Menit)</td>
                                <td style="text-align:right;"><?=$jumlahData>0?number_format($totaljam/$jumlahData,2,',','.'):"0";?></td>
                            </tr>
                            <tr>
                                <td style="text-align:left;">0 - 15 Menit</td>
                                <td style="text-align:right;"><?=$limabelas;?></td>
                            </tr>
                            <tr>
                                <td style="text-align:left;">&gt;15 - &lt;=30 Menit</td>
                                <td style="text-align:right;"><?=$tigapuluh;?></td>
                            </tr>
                            <tr>
                                <td style="text-align:left;">&gt;30 - &lt;=60 Menit</td>
                                <td style="text-align:right;"><?=$satujam;?></td>
                            </tr>
                            <tr>
                                <td style="text-align:left;">&gt;60 Menit</td>
                                <td style="text-align:right;"><?=$lebihsatujam;?></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <hr style="margin:0 0 20px 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Per Dokter</div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:180px;"><center>Nama Dokter</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Rata-rata (Menit)</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>0 - 15 Menit</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>&gt;15 - &lt;=30 Menit</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>&gt;30 - &lt;=60 Menit</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>&gt;60 Menit</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            foreach($rekapDokter as $namaDokter => $rekap) {
                                $rataDokter = $rekap['jumlah']>0?number_format($rekap['totaljam']/$rekap['jumlah'],2,',','.'):"0";
                                echo "<tr>
                                        <td align='left'>".$namaDokter."</td>
                                        <td align='right'>".$rataDokter."</td>
                                        <td align='right'>".$rekap['limabelas']."</td>
                                        <td align='right'>".$rekap['tigapuluh']."</td>
                                        <td align='right'>".$rekap['satujam']."</td>
                                        <td align='right'>".$rekap['lebihsatujam']."</td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                    </table>
                </div>
                <hr style="margin:0 0 20px 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Per Poli</div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:150px;"><center>Nama Poli</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Rata-rata (Menit)</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>0 - 15 Menit</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>&gt;15 - &lt;=30 Menit</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>&gt;30 - &lt;=60 Menit</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>&gt;60 Menit</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            foreach($rekapPoli as $namaPoli => $rekap) {
                                $rataPoli = $rekap['jumlah']>0?number_format($rekap['totaljam']/$rekap['jumlah'],2,',','.'):"0";
                                echo "<tr>
                                        <td align='left'>".$namaPoli."</td>
                                        <td align='right'>".$rataPoli."</td>
                                        <td align='right'>".$rekap['limabelas']."</td>
                                        <td align='right'>".$rekap['tigapuluh']."</td>
                                        <td align='right'>".$rekap['satujam']."</td>
                                        <td align='right'>".$rekap['lebihsatujam']."</td>
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