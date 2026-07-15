<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncaripelayanan  = $thnsekarang;
    $blncaripelayanan  = $blnsekarang;
    $tglcaripelayanan  = $tglsekarang;
    $thncaripelayanan2 = $thnsekarang;
    $blncaripelayanan2 = $blnsekarang;
    $tglcaripelayanan2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncaripelayanan  = validTeks(trim(isset($_POST['tgl_cari_pelayanan']))?substr($_POST['tgl_cari_pelayanan'],6,4):$thnsekarang);
        $blncaripelayanan  = validTeks(trim(isset($_POST['tgl_cari_pelayanan']))?substr($_POST['tgl_cari_pelayanan'],3,2):$blnsekarang);
        $tglcaripelayanan  = validTeks(trim(isset($_POST['tgl_cari_pelayanan']))?substr($_POST['tgl_cari_pelayanan'],0,2):$tglsekarang);
        $thncaripelayanan2 = validTeks(trim(isset($_POST['tgl_cari_pelayanan2']))?substr($_POST['tgl_cari_pelayanan2'],6,4):$thnsekarang);
        $blncaripelayanan2 = validTeks(trim(isset($_POST['tgl_cari_pelayanan2']))?substr($_POST['tgl_cari_pelayanan2'],3,2):$blnsekarang);
        $tglcaripelayanan2 = validTeks(trim(isset($_POST['tgl_cari_pelayanan2']))?substr($_POST['tgl_cari_pelayanan2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>DATA PELAYANAN GAWAT DARURAT</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_pelayanan">Tanggal Pelayanan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_pelayanan" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripelayanan."-".$blncaripelayanan."-".$thncaripelayanan;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_pelayanan2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_pelayanan2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripelayanan2."-".$blncaripelayanan2."-".$thncaripelayanan2;?>"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Tampilkan Data & Grafik</button></center>
                </form>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Cara Bayar</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Cara Bayar</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no             = 1;
                                    $totalCaraBayar = 0;
                                    $dataCaraBayar  = [];
                                    $querycarabayar = bukaquery("select penjab.png_jawab,count(reg_periksa.no_rawat) as jumlah from reg_periksa inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.kd_poli='IGDK' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by penjab.kd_pj order by jumlah desc");
                                    while($rsquerycarabayar = mysqli_fetch_array($querycarabayar)) {
                                        $totalCaraBayar += $rsquerycarabayar["jumlah"];
                                        $dataCaraBayar[] = [
                                            'label' => $rsquerycarabayar["png_jawab"]." (".$rsquerycarabayar["jumlah"].")",
                                            'data'  => (int)$rsquerycarabayar["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerycarabayar["png_jawab"]."</td>
                                                <td align='center'>".$rsquerycarabayar["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalCaraBayar;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_carabayar" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Dokter</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Dokter</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no          = 1;
                                    $totalDokter = 0;
                                    $dataDokter  = [];
                                    $querydokter = bukaquery("select dokter.nm_dokter,count(reg_periksa.no_rawat) as jumlah from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.kd_poli='IGDK' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by dokter.kd_dokter order by jumlah desc");
                                    while($rsquerydokter = mysqli_fetch_array($querydokter)) {
                                        $totalDokter += $rsquerydokter["jumlah"];
                                        $dataDokter[] = [
                                            'label' => $rsquerydokter["nm_dokter"]." (".$rsquerydokter["jumlah"].")",
                                            'data'  => (int)$rsquerydokter["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerydokter["nm_dokter"]."</td>
                                                <td align='center'>".$rsquerydokter["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalDokter;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_dokter" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Perujuk</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Perujuk</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no          = 1;
                                    $totalPerujuk = 0;
                                    $dataPerujuk  = [];
                                    $queryperujuk = bukaquery("select ifnull(rujuk_masuk.perujuk,'-') as perujuk,count(rujuk_masuk.no_rawat) as jumlah from rujuk_masuk inner join reg_periksa on rujuk_masuk.no_rawat=reg_periksa.no_rawat where reg_periksa.kd_poli='IGDK' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by rujuk_masuk.perujuk order by jumlah desc");
                                    while($rsqueryperujuk = mysqli_fetch_array($queryperujuk)) {
                                        $totalPerujuk += $rsqueryperujuk["jumlah"];
                                        $dataPerujuk[] = [
                                            'label' => $rsqueryperujuk["perujuk"]." (".$rsqueryperujuk["jumlah"].")",
                                            'data'  => (int)$rsqueryperujuk["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsqueryperujuk["perujuk"]."</td>
                                                <td align='center'>".$rsqueryperujuk["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalPerujuk;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_perujuk" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Tanggal</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Tanggal</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no             = 1;
                                    $indexTanggal   = 0;
                                    $totalTanggal   = 0;
                                    $dataTanggal    = [];
                                    $tickTanggal    = [];
                                    $querytanggal = bukaquery("select date_format(reg_periksa.tgl_registrasi,'%d-%m-%Y') as tanggal,count(reg_periksa.no_rawat) as jumlah from reg_periksa where reg_periksa.kd_poli='IGDK' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by reg_periksa.tgl_registrasi order by reg_periksa.tgl_registrasi asc");
                                    while($rsquerytanggal = mysqli_fetch_array($querytanggal)) {
                                        $totalTanggal += $rsquerytanggal["jumlah"];
                                        $dataTanggal[] = [$indexTanggal, (int)$rsquerytanggal["jumlah"]];
                                        $tickTanggal[] = [$indexTanggal, $rsquerytanggal["tanggal"]];
                                        $indexTanggal++;
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerytanggal["tanggal"]."</td>
                                                <td align='center'>".$rsquerytanggal["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalTanggal;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div style="overflow-x:auto;">
                            <div id="chart_tanggal" class="flot-chart" style="height: 400px;"></div>
                        </div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Bulan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Bulan</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no          = 1;
                                    $indexBulan  = 0;
                                    $totalBulan  = 0;
                                    $dataBulan   = [];
                                    $tickBulan   = [];
                                    $querybulan = bukaquery("select date_format(reg_periksa.tgl_registrasi,'%m-%Y') as bulan,count(reg_periksa.no_rawat) as jumlah from reg_periksa where reg_periksa.kd_poli='IGDK' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date_format(reg_periksa.tgl_registrasi,'%Y-%m') order by date_format(reg_periksa.tgl_registrasi,'%Y-%m') asc");
                                    while($rsquerybulan = mysqli_fetch_array($querybulan)) {
                                        $totalBulan += $rsquerybulan["jumlah"];
                                        $dataBulan[] = [$indexBulan, (int)$rsquerybulan["jumlah"]];
                                        $tickBulan[] = [$indexBulan, $rsquerybulan["bulan"]];
                                        $indexBulan++;
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerybulan["bulan"]."</td>
                                                <td align='center'>".$rsquerybulan["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalBulan;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div style="overflow-x:auto;">
                            <div id="chart_bulan" class="flot-chart" style="height: 400px;"></div>
                        </div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pasien Baru Per Tanggal</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Tanggal</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                = 1;
                                    $indexTanggalBaru  = 0;
                                    $totalTanggalBaru  = 0;
                                    $dataTanggalBaru   = [];
                                    $tickTanggalBaru   = [];
                                    $querytanggalbaru = bukaquery("select date_format(reg_periksa.tgl_registrasi,'%d-%m-%Y') as tanggal,count(reg_periksa.no_rawat) as jumlah from reg_periksa where reg_periksa.kd_poli='IGDK' and reg_periksa.stts_daftar='Baru' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by reg_periksa.tgl_registrasi order by reg_periksa.tgl_registrasi asc");
                                    while($rsquerytanggalbaru = mysqli_fetch_array($querytanggalbaru)) {
                                        $totalTanggalBaru += $rsquerytanggalbaru["jumlah"];
                                        $dataTanggalBaru[] = [$indexTanggalBaru, (int)$rsquerytanggalbaru["jumlah"]];
                                        $tickTanggalBaru[] = [$indexTanggalBaru, $rsquerytanggalbaru["tanggal"]];
                                        $indexTanggalBaru++;
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerytanggalbaru["tanggal"]."</td>
                                                <td align='center'>".$rsquerytanggalbaru["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalTanggalBaru;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div style="overflow-x:auto;">
                            <div id="chart_tanggal_baru" class="flot-chart" style="height: 400px;"></div>
                        </div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pasien Baru Per Bulan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Bulan</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no              = 1;
                                    $indexBulanBaru  = 0;
                                    $totalBulanBaru  = 0;
                                    $dataBulanBaru   = [];
                                    $tickBulanBaru   = [];
                                    $querybulanbaru = bukaquery("select date_format(reg_periksa.tgl_registrasi,'%m-%Y') as bulan,count(reg_periksa.no_rawat) as jumlah from reg_periksa where reg_periksa.kd_poli='IGDK' and reg_periksa.stts_daftar='Baru' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date_format(reg_periksa.tgl_registrasi,'%Y-%m') order by date_format(reg_periksa.tgl_registrasi,'%Y-%m') asc");
                                    while($rsquerybulanbaru = mysqli_fetch_array($querybulanbaru)) {
                                        $totalBulanBaru += $rsquerybulanbaru["jumlah"];
                                        $dataBulanBaru[] = [$indexBulanBaru, (int)$rsquerybulanbaru["jumlah"]];
                                        $tickBulanBaru[] = [$indexBulanBaru, $rsquerybulanbaru["bulan"]];
                                        $indexBulanBaru++;
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerybulanbaru["bulan"]."</td>
                                                <td align='center'>".$rsquerybulanbaru["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalBulanBaru;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div style="overflow-x:auto;">
                            <div id="chart_bulan_baru" class="flot-chart" style="height: 400px;"></div>
                        </div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pasien Lama Per Tanggal</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Tanggal</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                = 1;
                                    $indexTanggalLama  = 0;
                                    $totalTanggalLama  = 0;
                                    $dataTanggalLama   = [];
                                    $tickTanggalLama   = [];
                                    $querytanggallama = bukaquery("select date_format(reg_periksa.tgl_registrasi,'%d-%m-%Y') as tanggal,count(reg_periksa.no_rawat) as jumlah from reg_periksa where reg_periksa.kd_poli='IGDK' and reg_periksa.stts_daftar='Lama' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by reg_periksa.tgl_registrasi order by reg_periksa.tgl_registrasi asc");
                                    while($rsquerytanggallama = mysqli_fetch_array($querytanggallama)) {
                                        $totalTanggalLama += $rsquerytanggallama["jumlah"];
                                        $dataTanggalLama[] = [$indexTanggalLama, (int)$rsquerytanggallama["jumlah"]];
                                        $tickTanggalLama[] = [$indexTanggalLama, $rsquerytanggallama["tanggal"]];
                                        $indexTanggalLama++;
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerytanggallama["tanggal"]."</td>
                                                <td align='center'>".$rsquerytanggallama["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalTanggalLama;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div style="overflow-x:auto;">
                            <div id="chart_tanggal_lama" class="flot-chart" style="height: 400px;"></div>
                        </div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pasien Lama Per Bulan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Bulan</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no              = 1;
                                    $indexBulanLama  = 0;
                                    $totalBulanLama  = 0;
                                    $dataBulanLama   = [];
                                    $tickBulanLama   = [];
                                    $querybulanlama = bukaquery("select date_format(reg_periksa.tgl_registrasi,'%m-%Y') as bulan,count(reg_periksa.no_rawat) as jumlah from reg_periksa where reg_periksa.kd_poli='IGDK' and reg_periksa.stts_daftar='Lama' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date_format(reg_periksa.tgl_registrasi,'%Y-%m') order by date_format(reg_periksa.tgl_registrasi,'%Y-%m') asc");
                                    while($rsquerybulanlama = mysqli_fetch_array($querybulanlama)) {
                                        $totalBulanLama += $rsquerybulanlama["jumlah"];
                                        $dataBulanLama[] = [$indexBulanLama, (int)$rsquerybulanlama["jumlah"]];
                                        $tickBulanLama[] = [$indexBulanLama, $rsquerybulanlama["bulan"]];
                                        $indexBulanLama++;
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerybulanlama["bulan"]."</td>
                                                <td align='center'>".$rsquerybulanlama["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalBulanLama;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div style="overflow-x:auto;">
                            <div id="chart_bulan_lama" class="flot-chart" style="height: 400px;"></div>
                        </div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Status Pelayanan Pasien</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Status</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                    = 1;
                                    $totalStatusPelayanan  = 0;
                                    $dataStatusPelayanan   = [];
                                    $querystatuspelayanan  = bukaquery("select case when reg_periksa.stts='Batal' then 'Batal' when reg_periksa.stts='Dirujuk' then 'Dirujuk' when reg_periksa.stts='Meninggal' then 'Meninggal' when reg_periksa.stts='Pulang Paksa' then 'Pulang Paksa' when reg_periksa.stts in ('Belum','Sudah','Berkas Diterima','Dirawat') then 'Terlayani' end as status_pelayanan,count(reg_periksa.no_rawat) as jumlah from reg_periksa where reg_periksa.kd_poli='IGDK' and reg_periksa.tgl_registrasi between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by status_pelayanan order by field(status_pelayanan,'Batal','Dirujuk','Meninggal','Pulang Paksa','Terlayani')");
                                    while($rsquerystatuspelayanan = mysqli_fetch_array($querystatuspelayanan)) {
                                        $totalStatusPelayanan += $rsquerystatuspelayanan["jumlah"];
                                        $dataStatusPelayanan[] = [
                                            'label' => $rsquerystatuspelayanan["status_pelayanan"]." (".$rsquerystatuspelayanan["jumlah"].")",
                                            'data'  => (int)$rsquerystatuspelayanan["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerystatuspelayanan["status_pelayanan"]."</td>
                                                <td align='center'>".$rsquerystatuspelayanan["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Jumlah :</th>
                                        <th style="text-align:center"><?=$totalStatusPelayanan;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_statuspelayanan" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="plugins/flot-charts/jquery.flot.js"></script>
<script src="plugins/flot-charts/jquery.flot.resize.js"></script>
<script src="plugins/flot-charts/jquery.flot.pie.js"></script>
<script>
$(function() {
    var dataCaraBayar = <?= json_encode($dataCaraBayar) ?>;
    if (dataCaraBayar.length > 0) {
        $.plot("#pie_chart_carabayar", dataCaraBayar, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_carabayar").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataDokter = <?= json_encode($dataDokter) ?>;
    if (dataDokter.length > 0) {
        $.plot("#pie_chart_dokter", dataDokter, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_dokter").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataPerujuk = <?= json_encode($dataPerujuk) ?>;
    if (dataPerujuk.length > 0) {
        $.plot("#pie_chart_perujuk", dataPerujuk, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_perujuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataTanggal = <?= json_encode($dataTanggal) ?>;
    var tickTanggal = <?= json_encode($tickTanggal) ?>;
    if (dataTanggal.length > 0) {
        $("#chart_tanggal").css({ width: Math.max(dataTanggal.length * 90, 500) + "px", minWidth: Math.max(dataTanggal.length * 90, 500) + "px" });
        $.plot("#chart_tanggal", [{
            data: dataTanggal,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#e91e63'
        }], {
            xaxis: {
                ticks: tickTanggal,
                min: -0.5,
                max: dataTanggal.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_tanggal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBulan = <?= json_encode($dataBulan) ?>;
    var tickBulan = <?= json_encode($tickBulan) ?>;
    if (dataBulan.length > 0) {
        $("#chart_bulan").css({ width: Math.max(dataBulan.length * 90, 500) + "px", minWidth: Math.max(dataBulan.length * 90, 500) + "px" });
        $.plot("#chart_bulan", [{
            data: dataBulan,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#3f51b5'
        }], {
            xaxis: {
                ticks: tickBulan,
                min: -0.5,
                max: dataBulan.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_bulan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataTanggalBaru = <?= json_encode($dataTanggalBaru) ?>;
    var tickTanggalBaru = <?= json_encode($tickTanggalBaru) ?>;
    if (dataTanggalBaru.length > 0) {
        $("#chart_tanggal_baru").css({ width: Math.max(dataTanggalBaru.length * 90, 500) + "px", minWidth: Math.max(dataTanggalBaru.length * 90, 500) + "px" });
        $.plot("#chart_tanggal_baru", [{
            data: dataTanggalBaru,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#e91e63'
        }], {
            xaxis: {
                ticks: tickTanggalBaru,
                min: -0.5,
                max: dataTanggalBaru.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_tanggal_baru").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBulanBaru = <?= json_encode($dataBulanBaru) ?>;
    var tickBulanBaru = <?= json_encode($tickBulanBaru) ?>;
    if (dataBulanBaru.length > 0) {
        $("#chart_bulan_baru").css({ width: Math.max(dataBulanBaru.length * 90, 500) + "px", minWidth: Math.max(dataBulanBaru.length * 90, 500) + "px" });
        $.plot("#chart_bulan_baru", [{
            data: dataBulanBaru,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#3f51b5'
        }], {
            xaxis: {
                ticks: tickBulanBaru,
                min: -0.5,
                max: dataBulanBaru.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_bulan_baru").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataTanggalLama = <?= json_encode($dataTanggalLama) ?>;
    var tickTanggalLama = <?= json_encode($tickTanggalLama) ?>;
    if (dataTanggalLama.length > 0) {
        $("#chart_tanggal_lama").css({ width: Math.max(dataTanggalLama.length * 90, 500) + "px", minWidth: Math.max(dataTanggalLama.length * 90, 500) + "px" });
        $.plot("#chart_tanggal_lama", [{
            data: dataTanggalLama,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#e91e63'
        }], {
            xaxis: {
                ticks: tickTanggalLama,
                min: -0.5,
                max: dataTanggalLama.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_tanggal_lama").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBulanLama = <?= json_encode($dataBulanLama) ?>;
    var tickBulanLama = <?= json_encode($tickBulanLama) ?>;
    if (dataBulanLama.length > 0) {
        $("#chart_bulan_lama").css({ width: Math.max(dataBulanLama.length * 90, 500) + "px", minWidth: Math.max(dataBulanLama.length * 90, 500) + "px" });
        $.plot("#chart_bulan_lama", [{
            data: dataBulanLama,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#3f51b5'
        }], {
            xaxis: {
                ticks: tickBulanLama,
                min: -0.5,
                max: dataBulanLama.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_bulan_lama").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataStatusPelayanan = <?= json_encode($dataStatusPelayanan) ?>;
    if (dataStatusPelayanan.length > 0) {
        $.plot("#pie_chart_statuspelayanan", dataStatusPelayanan, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_statuspelayanan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>