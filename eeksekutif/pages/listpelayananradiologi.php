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
    <h2><center>DATA PELAYANAN RADIOLOGI</center></h2>
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
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Pemeriksaan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Nama Pemeriksaan</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no             = 1;
                                    $totalPemeriksaan  = 0;
                                    $dataPemeriksaan   = [];
                                    $querytindakan  = bukaquery("select jns_perawatan_radiologi.nm_perawatan,count(periksa_radiologi.no_rawat) as jumlah from periksa_radiologi inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.tgl_periksa between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by jns_perawatan_radiologi.nm_perawatan order by jumlah desc");
                                    while($rsquerytindakan = mysqli_fetch_array($querytindakan)) {
                                        $totalPemeriksaan += $rsquerytindakan["jumlah"];
                                        $dataPemeriksaan[] = [
                                            'label' => $rsquerytindakan["nm_perawatan"]." (".$rsquerytindakan["jumlah"].")",
                                            'data'  => (int)$rsquerytindakan["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerytindakan["nm_perawatan"]."</td>
                                                <td align='center'>".$rsquerytindakan["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalPemeriksaan;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_tindakan" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Pemeriksaan Rawat Jalan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Nama Pemeriksaan</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                  = 1;
                                    $totalPemeriksaanRalan  = 0;
                                    $dataPemeriksaanRalan   = [];
                                    $querytindakanralan  = bukaquery("select jns_perawatan_radiologi.nm_perawatan,count(periksa_radiologi.no_rawat) as jumlah from periksa_radiologi inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.status='Ralan' and periksa_radiologi.tgl_periksa between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by jns_perawatan_radiologi.nm_perawatan order by jumlah desc");
                                    while($rsquerytindakanralan = mysqli_fetch_array($querytindakanralan)) {
                                        $totalPemeriksaanRalan += $rsquerytindakanralan["jumlah"];
                                        $dataPemeriksaanRalan[] = [
                                            'label' => $rsquerytindakanralan["nm_perawatan"]." (".$rsquerytindakanralan["jumlah"].")",
                                            'data'  => (int)$rsquerytindakanralan["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerytindakanralan["nm_perawatan"]."</td>
                                                <td align='center'>".$rsquerytindakanralan["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalPemeriksaanRalan;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_tindakan_ralan" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Pemeriksaan Rawat Inap</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Nama Pemeriksaan</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                  = 1;
                                    $totalPemeriksaanRanap  = 0;
                                    $dataPemeriksaanRanap   = [];
                                    $querytindakanranap  = bukaquery("select jns_perawatan_radiologi.nm_perawatan,count(periksa_radiologi.no_rawat) as jumlah from periksa_radiologi inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.status='Ranap' and periksa_radiologi.tgl_periksa between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by jns_perawatan_radiologi.nm_perawatan order by jumlah desc");
                                    while($rsquerytindakanranap = mysqli_fetch_array($querytindakanranap)) {
                                        $totalPemeriksaanRanap += $rsquerytindakanranap["jumlah"];
                                        $dataPemeriksaanRanap[] = [
                                            'label' => $rsquerytindakanranap["nm_perawatan"]." (".$rsquerytindakanranap["jumlah"].")",
                                            'data'  => (int)$rsquerytindakanranap["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerytindakanranap["nm_perawatan"]."</td>
                                                <td align='center'>".$rsquerytindakanranap["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalPemeriksaanRanap;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_tindakan_ranap" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Dokter Perujuk</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Dokter Perujuk</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                   = 1;
                                    $totalDokterPerujuk   = 0;
                                    $dataDokterPerujuk    = [];
                                    $querydokterperujuk   = bukaquery("select dokter.nm_dokter,count(periksa_radiologi.no_rawat) as jumlah from periksa_radiologi inner join dokter on periksa_radiologi.dokter_perujuk=dokter.kd_dokter where periksa_radiologi.tgl_periksa between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by dokter.kd_dokter order by jumlah desc");
                                    while($rsquerydokterperujuk = mysqli_fetch_array($querydokterperujuk)) {
                                        $totalDokterPerujuk += $rsquerydokterperujuk["jumlah"];
                                        $dataDokterPerujuk[] = [
                                            'label' => $rsquerydokterperujuk["nm_dokter"]." (".$rsquerydokterperujuk["jumlah"].")",
                                            'data'  => (int)$rsquerydokterperujuk["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerydokterperujuk["nm_dokter"]."</td>
                                                <td align='center'>".$rsquerydokterperujuk["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalDokterPerujuk;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_dokterperujuk" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Dokter Perujuk Rawat Inap</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Dokter Perujuk</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                        = 1;
                                    $totalDokterPerujukRanap   = 0;
                                    $dataDokterPerujukRanap    = [];
                                    $querydokterperujukranap   = bukaquery("select dokter.nm_dokter,count(periksa_radiologi.no_rawat) as jumlah from periksa_radiologi inner join dokter on periksa_radiologi.dokter_perujuk=dokter.kd_dokter where periksa_radiologi.status='Ranap' and periksa_radiologi.tgl_periksa between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by dokter.kd_dokter order by jumlah desc");
                                    while($rsquerydokterperujukranap = mysqli_fetch_array($querydokterperujukranap)) {
                                        $totalDokterPerujukRanap += $rsquerydokterperujukranap["jumlah"];
                                        $dataDokterPerujukRanap[] = [
                                            'label' => $rsquerydokterperujukranap["nm_dokter"]." (".$rsquerydokterperujukranap["jumlah"].")",
                                            'data'  => (int)$rsquerydokterperujukranap["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerydokterperujukranap["nm_dokter"]."</td>
                                                <td align='center'>".$rsquerydokterperujukranap["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalDokterPerujukRanap;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_dokterperujuk_ranap" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Dokter Perujuk Rawat Jalan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Dokter Perujuk</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                        = 1;
                                    $totalDokterPerujukRalan   = 0;
                                    $dataDokterPerujukRalan    = [];
                                    $querydokterperujukralan   = bukaquery("select dokter.nm_dokter,count(periksa_radiologi.no_rawat) as jumlah from periksa_radiologi inner join dokter on periksa_radiologi.dokter_perujuk=dokter.kd_dokter where periksa_radiologi.status='Ralan' and periksa_radiologi.tgl_periksa between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by dokter.kd_dokter order by jumlah desc");
                                    while($rsquerydokterperujukralan = mysqli_fetch_array($querydokterperujukralan)) {
                                        $totalDokterPerujukRalan += $rsquerydokterperujukralan["jumlah"];
                                        $dataDokterPerujukRalan[] = [
                                            'label' => $rsquerydokterperujukralan["nm_dokter"]." (".$rsquerydokterperujukralan["jumlah"].")",
                                            'data'  => (int)$rsquerydokterperujukralan["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerydokterperujukralan["nm_dokter"]."</td>
                                                <td align='center'>".$rsquerydokterperujukralan["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalDokterPerujukRalan;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_dokterperujuk_ralan" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
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
                                    $no              = 1;
                                    $totalCaraBayar  = 0;
                                    $dataCaraBayar   = [];
                                    $querycarabayar  = bukaquery("select penjab.png_jawab,count(periksa_radiologi.no_rawat) as jumlah from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where periksa_radiologi.tgl_periksa between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by penjab.kd_pj order by jumlah desc");
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
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Cara Bayar Rawat Inap</div>
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
                                    $no                   = 1;
                                    $totalCaraBayarRanap  = 0;
                                    $dataCaraBayarRanap   = [];
                                    $querycarabayarranap  = bukaquery("select penjab.png_jawab,count(periksa_radiologi.no_rawat) as jumlah from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where periksa_radiologi.status='Ranap' and periksa_radiologi.tgl_periksa between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by penjab.kd_pj order by jumlah desc");
                                    while($rsquerycarabayarranap = mysqli_fetch_array($querycarabayarranap)) {
                                        $totalCaraBayarRanap += $rsquerycarabayarranap["jumlah"];
                                        $dataCaraBayarRanap[] = [
                                            'label' => $rsquerycarabayarranap["png_jawab"]." (".$rsquerycarabayarranap["jumlah"].")",
                                            'data'  => (int)$rsquerycarabayarranap["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerycarabayarranap["png_jawab"]."</td>
                                                <td align='center'>".$rsquerycarabayarranap["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalCaraBayarRanap;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_carabayar_ranap" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Cara Bayar Rawat Jalan</div>
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
                                    $no                   = 1;
                                    $totalCaraBayarRalan  = 0;
                                    $dataCaraBayarRalan   = [];
                                    $querycarabayarralan  = bukaquery("select penjab.png_jawab,count(periksa_radiologi.no_rawat) as jumlah from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where periksa_radiologi.status='Ralan' and periksa_radiologi.tgl_periksa between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by penjab.kd_pj order by jumlah desc");
                                    while($rsquerycarabayarralan = mysqli_fetch_array($querycarabayarralan)) {
                                        $totalCaraBayarRalan += $rsquerycarabayarralan["jumlah"];
                                        $dataCaraBayarRalan[] = [
                                            'label' => $rsquerycarabayarralan["png_jawab"]." (".$rsquerycarabayarralan["jumlah"].")",
                                            'data'  => (int)$rsquerycarabayarralan["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerycarabayarralan["png_jawab"]."</td>
                                                <td align='center'>".$rsquerycarabayarralan["jumlah"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right">Total</th>
                                        <th style="text-align:center"><?=$totalCaraBayarRalan;?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_carabayar_ralan" class="flot-chart" style="height: 400px;"></div>
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
    var dataPemeriksaan = <?= json_encode($dataPemeriksaan) ?>;
    if (dataPemeriksaan.length > 0) {
        $.plot("#pie_chart_tindakan", dataPemeriksaan, {
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
        $("#pie_chart_tindakan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataPemeriksaanRalan = <?= json_encode($dataPemeriksaanRalan) ?>;
    if (dataPemeriksaanRalan.length > 0) {
        $.plot("#pie_chart_tindakan_ralan", dataPemeriksaanRalan, {
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
        $("#pie_chart_tindakan_ralan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataPemeriksaanRanap = <?= json_encode($dataPemeriksaanRanap) ?>;
    if (dataPemeriksaanRanap.length > 0) {
        $.plot("#pie_chart_tindakan_ranap", dataPemeriksaanRanap, {
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
        $("#pie_chart_tindakan_ranap").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataDokterPerujuk = <?= json_encode($dataDokterPerujuk) ?>;
    if (dataDokterPerujuk.length > 0) {
        $.plot("#pie_chart_dokterperujuk", dataDokterPerujuk, {
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
        $("#pie_chart_dokterperujuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataDokterPerujukRanap = <?= json_encode($dataDokterPerujukRanap) ?>;
    if (dataDokterPerujukRanap.length > 0) {
        $.plot("#pie_chart_dokterperujuk_ranap", dataDokterPerujukRanap, {
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
        $("#pie_chart_dokterperujuk_ranap").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataDokterPerujukRalan = <?= json_encode($dataDokterPerujukRalan) ?>;
    if (dataDokterPerujukRalan.length > 0) {
        $.plot("#pie_chart_dokterperujuk_ralan", dataDokterPerujukRalan, {
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
        $("#pie_chart_dokterperujuk_ralan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
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
    var dataCaraBayarRanap = <?= json_encode($dataCaraBayarRanap) ?>;
    if (dataCaraBayarRanap.length > 0) {
        $.plot("#pie_chart_carabayar_ranap", dataCaraBayarRanap, {
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
        $("#pie_chart_carabayar_ranap").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataCaraBayarRalan = <?= json_encode($dataCaraBayarRalan) ?>;
    if (dataCaraBayarRalan.length > 0) {
        $.plot("#pie_chart_carabayar_ralan", dataCaraBayarRalan, {
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
        $("#pie_chart_carabayar_ralan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>