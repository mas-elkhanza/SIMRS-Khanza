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
    <h2><center>DATA PELAYANAN RAWAT INAP</center></h2>
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
                <ul class="nav nav-tabs tab-nav-right" role="tablist">
                    <li role="presentation" class="active"><a href="#masuk" data-toggle="tab">BERDASARKAN TANGGAL MASUK</a></li>
                    <li role="presentation"><a href="#pulang" data-toggle="tab">BERDASARKAN TANGGAL PULANG</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane fade in active" id="masuk">
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
                                            $no                   = 1;
                                            $totalCaraBayarMasuk  = 0;
                                            $dataCaraBayarMasuk   = [];
                                            $querycarabayarmasuk  = bukaquery("select penjab.png_jawab,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where kamar_inap.stts_pulang<>'Pindah Kamar' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by penjab.kd_pj order by jumlah desc");
                                            while($rsquerycarabayarmasuk = mysqli_fetch_array($querycarabayarmasuk)) {
                                                $totalCaraBayarMasuk += $rsquerycarabayarmasuk["jumlah"];
                                                $dataCaraBayarMasuk[] = [
                                                    'label' => $rsquerycarabayarmasuk["png_jawab"]." (".$rsquerycarabayarmasuk["jumlah"].")",
                                                    'data'  => (int)$rsquerycarabayarmasuk["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerycarabayarmasuk["png_jawab"]."</td>
                                                        <td align='center'>".$rsquerycarabayarmasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalCaraBayarMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_carabayar_masuk" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Bangsal</div>
                        </div>
                        <div class="row clearfix">
                            <div class="col-md-6">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                        <thead>
                                            <tr>
                                                <th width="5%"><center>No</center></th>
                                                <th width="70%"><center>Bangsal</center></th>
                                                <th width="25%"><center>Jumlah</center></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <?php
                                            $no                 = 1;
                                            $totalBangsalMasuk  = 0;
                                            $dataBangsalMasuk   = [];
                                            $querybangsalmasuk  = bukaquery("select bangsal.nm_bangsal,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.stts_pulang<>'Pindah Kamar' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by bangsal.kd_bangsal order by jumlah desc");
                                            while($rsquerybangsalmasuk = mysqli_fetch_array($querybangsalmasuk)) {
                                                $totalBangsalMasuk += $rsquerybangsalmasuk["jumlah"];
                                                $dataBangsalMasuk[] = [
                                                    'label' => $rsquerybangsalmasuk["nm_bangsal"]." (".$rsquerybangsalmasuk["jumlah"].")",
                                                    'data'  => (int)$rsquerybangsalmasuk["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerybangsalmasuk["nm_bangsal"]."</td>
                                                        <td align='center'>".$rsquerybangsalmasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalBangsalMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_bangsal_masuk" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Kelas</div>
                        </div>
                        <div class="row clearfix">
                            <div class="col-md-6">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                        <thead>
                                            <tr>
                                                <th width="5%"><center>No</center></th>
                                                <th width="70%"><center>Kelas</center></th>
                                                <th width="25%"><center>Jumlah</center></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <?php
                                            $no               = 1;
                                            $totalKelasMasuk  = 0;
                                            $dataKelasMasuk   = [];
                                            $querykelasmasuk  = bukaquery("select kamar.kelas,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar where kamar_inap.stts_pulang<>'Pindah Kamar' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by kamar.kelas order by jumlah desc");
                                            while($rsquerykelasmasuk = mysqli_fetch_array($querykelasmasuk)) {
                                                $totalKelasMasuk += $rsquerykelasmasuk["jumlah"];
                                                $dataKelasMasuk[] = [
                                                    'label' => $rsquerykelasmasuk["kelas"]." (".$rsquerykelasmasuk["jumlah"].")",
                                                    'data'  => (int)$rsquerykelasmasuk["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerykelasmasuk["kelas"]."</td>
                                                        <td align='center'>".$rsquerykelasmasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalKelasMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_kelas_masuk" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Asal Poli Pasien</div>
                        </div>
                        <div class="row clearfix">
                            <div class="col-md-6">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                        <thead>
                                            <tr>
                                                <th width="5%"><center>No</center></th>
                                                <th width="70%"><center>Poli</center></th>
                                                <th width="25%"><center>Jumlah</center></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <?php
                                            $no                  = 1;
                                            $totalAsalPoliMasuk  = 0;
                                            $dataAsalPoliMasuk   = [];
                                            $queryasalpolimasuk  = bukaquery("select poliklinik.nm_poli,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli where kamar_inap.stts_pulang<>'Pindah Kamar' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by poliklinik.kd_poli order by jumlah desc");
                                            while($rsqueryasalpolimasuk = mysqli_fetch_array($queryasalpolimasuk)) {
                                                $totalAsalPoliMasuk += $rsqueryasalpolimasuk["jumlah"];
                                                $dataAsalPoliMasuk[] = [
                                                    'label' => $rsqueryasalpolimasuk["nm_poli"]." (".$rsqueryasalpolimasuk["jumlah"].")",
                                                    'data'  => (int)$rsqueryasalpolimasuk["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsqueryasalpolimasuk["nm_poli"]."</td>
                                                        <td align='center'>".$rsqueryasalpolimasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalAsalPoliMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_asalpoli_masuk" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Asal Dokter Pasien</div>
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
                                            $no                    = 1;
                                            $totalAsalDokterMasuk  = 0;
                                            $dataAsalDokterMasuk   = [];
                                            $queryasaldoktermasuk  = bukaquery("select dokter.nm_dokter,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where kamar_inap.stts_pulang<>'Pindah Kamar' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by dokter.kd_dokter order by jumlah desc");
                                            while($rsqueryasaldoktermasuk = mysqli_fetch_array($queryasaldoktermasuk)) {
                                                $totalAsalDokterMasuk += $rsqueryasaldoktermasuk["jumlah"];
                                                $dataAsalDokterMasuk[] = [
                                                    'label' => $rsqueryasaldoktermasuk["nm_dokter"]." (".$rsqueryasaldoktermasuk["jumlah"].")",
                                                    'data'  => (int)$rsqueryasaldoktermasuk["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsqueryasaldoktermasuk["nm_dokter"]."</td>
                                                        <td align='center'>".$rsqueryasaldoktermasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalAsalDokterMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_asaldokter_masuk" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Status Pulang Pasien</div>
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
                                            $no                       = 1;
                                            $totalStatusPulangMasuk   = 0;
                                            $dataStatusPulangMasuk    = [];
                                            $querystatuspulangmasuk   = bukaquery("select kamar_inap.stts_pulang,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by kamar_inap.stts_pulang order by jumlah desc");
                                            while($rsquerystatuspulangmasuk = mysqli_fetch_array($querystatuspulangmasuk)) {
                                                $totalStatusPulangMasuk += $rsquerystatuspulangmasuk["jumlah"];
                                                $dataStatusPulangMasuk[] = [
                                                    'label' => $rsquerystatuspulangmasuk["stts_pulang"]." (".$rsquerystatuspulangmasuk["jumlah"].")",
                                                    'data'  => (int)$rsquerystatuspulangmasuk["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerystatuspulangmasuk["stts_pulang"]."</td>
                                                        <td align='center'>".$rsquerystatuspulangmasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Jumlah :</th>
                                                <th style="text-align:center"><?=$totalStatusPulangMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_statuspulang_masuk" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                 = 1;
                                            $indexTanggalMasuk  = 0;
                                            $totalTanggalMasuk  = 0;
                                            $dataTanggalMasuk   = [];
                                            $tickTanggalMasuk   = [];
                                            $querytanggalmasuk  = bukaquery("select date_format(kamar_inap.tgl_masuk,'%d-%m-%Y') as tanggal,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap where kamar_inap.stts_pulang<>'Pindah Kamar' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date(kamar_inap.tgl_masuk) order by date(kamar_inap.tgl_masuk) asc");
                                            while($rsquerytanggalmasuk = mysqli_fetch_array($querytanggalmasuk)) {
                                                $totalTanggalMasuk += $rsquerytanggalmasuk["jumlah"];
                                                $dataTanggalMasuk[] = [$indexTanggalMasuk, (int)$rsquerytanggalmasuk["jumlah"]];
                                                $tickTanggalMasuk[] = [$indexTanggalMasuk, $rsquerytanggalmasuk["tanggal"]];
                                                $indexTanggalMasuk++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerytanggalmasuk["tanggal"]."</td>
                                                        <td align='center'>".$rsquerytanggalmasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalTanggalMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_tanggal_masuk" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no               = 1;
                                            $indexBulanMasuk  = 0;
                                            $totalBulanMasuk  = 0;
                                            $dataBulanMasuk   = [];
                                            $tickBulanMasuk   = [];
                                            $querybulanmasuk  = bukaquery("select date_format(kamar_inap.tgl_masuk,'%m-%Y') as bulan,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap where kamar_inap.stts_pulang<>'Pindah Kamar' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date_format(kamar_inap.tgl_masuk,'%Y-%m') order by date_format(kamar_inap.tgl_masuk,'%Y-%m') asc");
                                            while($rsquerybulanmasuk = mysqli_fetch_array($querybulanmasuk)) {
                                                $totalBulanMasuk += $rsquerybulanmasuk["jumlah"];
                                                $dataBulanMasuk[] = [$indexBulanMasuk, (int)$rsquerybulanmasuk["jumlah"]];
                                                $tickBulanMasuk[] = [$indexBulanMasuk, $rsquerybulanmasuk["bulan"]];
                                                $indexBulanMasuk++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerybulanmasuk["bulan"]."</td>
                                                        <td align='center'>".$rsquerybulanmasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalBulanMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_bulan_masuk" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                     = 1;
                                            $indexTanggalBaruMasuk  = 0;
                                            $totalTanggalBaruMasuk  = 0;
                                            $dataTanggalBaruMasuk   = [];
                                            $tickTanggalBaruMasuk   = [];
                                            $querytanggalbarumasuk  = bukaquery("select date_format(kamar_inap.tgl_masuk,'%d-%m-%Y') as tanggal,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat where kamar_inap.stts_pulang<>'Pindah Kamar' and reg_periksa.stts_daftar='Baru' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date(kamar_inap.tgl_masuk) order by date(kamar_inap.tgl_masuk) asc");
                                            while($rsquerytanggalbarumasuk = mysqli_fetch_array($querytanggalbarumasuk)) {
                                                $totalTanggalBaruMasuk += $rsquerytanggalbarumasuk["jumlah"];
                                                $dataTanggalBaruMasuk[] = [$indexTanggalBaruMasuk, (int)$rsquerytanggalbarumasuk["jumlah"]];
                                                $tickTanggalBaruMasuk[] = [$indexTanggalBaruMasuk, $rsquerytanggalbarumasuk["tanggal"]];
                                                $indexTanggalBaruMasuk++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerytanggalbarumasuk["tanggal"]."</td>
                                                        <td align='center'>".$rsquerytanggalbarumasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalTanggalBaruMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_tanggal_baru_masuk" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                   = 1;
                                            $indexBulanBaruMasuk  = 0;
                                            $totalBulanBaruMasuk  = 0;
                                            $dataBulanBaruMasuk   = [];
                                            $tickBulanBaruMasuk   = [];
                                            $querybulanbarumasuk  = bukaquery("select date_format(kamar_inap.tgl_masuk,'%m-%Y') as bulan,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat where kamar_inap.stts_pulang<>'Pindah Kamar' and reg_periksa.stts_daftar='Baru' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date_format(kamar_inap.tgl_masuk,'%Y-%m') order by date_format(kamar_inap.tgl_masuk,'%Y-%m') asc");
                                            while($rsquerybulanbarumasuk = mysqli_fetch_array($querybulanbarumasuk)) {
                                                $totalBulanBaruMasuk += $rsquerybulanbarumasuk["jumlah"];
                                                $dataBulanBaruMasuk[] = [$indexBulanBaruMasuk, (int)$rsquerybulanbarumasuk["jumlah"]];
                                                $tickBulanBaruMasuk[] = [$indexBulanBaruMasuk, $rsquerybulanbarumasuk["bulan"]];
                                                $indexBulanBaruMasuk++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerybulanbarumasuk["bulan"]."</td>
                                                        <td align='center'>".$rsquerybulanbarumasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalBulanBaruMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_bulan_baru_masuk" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                     = 1;
                                            $indexTanggalLamaMasuk  = 0;
                                            $totalTanggalLamaMasuk  = 0;
                                            $dataTanggalLamaMasuk   = [];
                                            $tickTanggalLamaMasuk   = [];
                                            $querytanggallamamasuk  = bukaquery("select date_format(kamar_inap.tgl_masuk,'%d-%m-%Y') as tanggal,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat where kamar_inap.stts_pulang<>'Pindah Kamar' and reg_periksa.stts_daftar='Lama' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date(kamar_inap.tgl_masuk) order by date(kamar_inap.tgl_masuk) asc");
                                            while($rsquerytanggallamamasuk = mysqli_fetch_array($querytanggallamamasuk)) {
                                                $totalTanggalLamaMasuk += $rsquerytanggallamamasuk["jumlah"];
                                                $dataTanggalLamaMasuk[] = [$indexTanggalLamaMasuk, (int)$rsquerytanggallamamasuk["jumlah"]];
                                                $tickTanggalLamaMasuk[] = [$indexTanggalLamaMasuk, $rsquerytanggallamamasuk["tanggal"]];
                                                $indexTanggalLamaMasuk++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerytanggallamamasuk["tanggal"]."</td>
                                                        <td align='center'>".$rsquerytanggallamamasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalTanggalLamaMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_tanggal_lama_masuk" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                   = 1;
                                            $indexBulanLamaMasuk  = 0;
                                            $totalBulanLamaMasuk  = 0;
                                            $dataBulanLamaMasuk   = [];
                                            $tickBulanLamaMasuk   = [];
                                            $querybulanlamamasuk  = bukaquery("select date_format(kamar_inap.tgl_masuk,'%m-%Y') as bulan,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat where kamar_inap.stts_pulang<>'Pindah Kamar' and reg_periksa.stts_daftar='Lama' and date(kamar_inap.tgl_masuk) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date_format(kamar_inap.tgl_masuk,'%Y-%m') order by date_format(kamar_inap.tgl_masuk,'%Y-%m') asc");
                                            while($rsquerybulanlamamasuk = mysqli_fetch_array($querybulanlamamasuk)) {
                                                $totalBulanLamaMasuk += $rsquerybulanlamamasuk["jumlah"];
                                                $dataBulanLamaMasuk[] = [$indexBulanLamaMasuk, (int)$rsquerybulanlamamasuk["jumlah"]];
                                                $tickBulanLamaMasuk[] = [$indexBulanLamaMasuk, $rsquerybulanlamamasuk["bulan"]];
                                                $indexBulanLamaMasuk++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerybulanlamamasuk["bulan"]."</td>
                                                        <td align='center'>".$rsquerybulanlamamasuk["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalBulanLamaMasuk;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_bulan_lama_masuk" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane fade" id="pulang">
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
                                            $no                    = 1;
                                            $totalCaraBayarPulang  = 0;
                                            $dataCaraBayarPulang   = [];
                                            $querycarabayarpulang  = bukaquery("select penjab.png_jawab,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by penjab.kd_pj order by jumlah desc");
                                            while($rsquerycarabayarpulang = mysqli_fetch_array($querycarabayarpulang)) {
                                                $totalCaraBayarPulang += $rsquerycarabayarpulang["jumlah"];
                                                $dataCaraBayarPulang[] = [
                                                    'label' => $rsquerycarabayarpulang["png_jawab"]." (".$rsquerycarabayarpulang["jumlah"].")",
                                                    'data'  => (int)$rsquerycarabayarpulang["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerycarabayarpulang["png_jawab"]."</td>
                                                        <td align='center'>".$rsquerycarabayarpulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalCaraBayarPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_carabayar_pulang" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Bangsal</div>
                        </div>
                        <div class="row clearfix">
                            <div class="col-md-6">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                        <thead>
                                            <tr>
                                                <th width="5%"><center>No</center></th>
                                                <th width="70%"><center>Bangsal</center></th>
                                                <th width="25%"><center>Jumlah</center></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <?php
                                            $no                  = 1;
                                            $totalBangsalPulang  = 0;
                                            $dataBangsalPulang   = [];
                                            $querybangsalpulang  = bukaquery("select bangsal.nm_bangsal,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by bangsal.kd_bangsal order by jumlah desc");
                                            while($rsquerybangsalpulang = mysqli_fetch_array($querybangsalpulang)) {
                                                $totalBangsalPulang += $rsquerybangsalpulang["jumlah"];
                                                $dataBangsalPulang[] = [
                                                    'label' => $rsquerybangsalpulang["nm_bangsal"]." (".$rsquerybangsalpulang["jumlah"].")",
                                                    'data'  => (int)$rsquerybangsalpulang["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerybangsalpulang["nm_bangsal"]."</td>
                                                        <td align='center'>".$rsquerybangsalpulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalBangsalPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_bangsal_pulang" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Pelayanan Per Kelas</div>
                        </div>
                        <div class="row clearfix">
                            <div class="col-md-6">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                        <thead>
                                            <tr>
                                                <th width="5%"><center>No</center></th>
                                                <th width="70%"><center>Kelas</center></th>
                                                <th width="25%"><center>Jumlah</center></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <?php
                                            $no                = 1;
                                            $totalKelasPulang  = 0;
                                            $dataKelasPulang   = [];
                                            $querykelaspulang  = bukaquery("select kamar.kelas,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by kamar.kelas order by jumlah desc");
                                            while($rsquerykelaspulang = mysqli_fetch_array($querykelaspulang)) {
                                                $totalKelasPulang += $rsquerykelaspulang["jumlah"];
                                                $dataKelasPulang[] = [
                                                    'label' => $rsquerykelaspulang["kelas"]." (".$rsquerykelaspulang["jumlah"].")",
                                                    'data'  => (int)$rsquerykelaspulang["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerykelaspulang["kelas"]."</td>
                                                        <td align='center'>".$rsquerykelaspulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalKelasPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_kelas_pulang" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Asal Poli Pasien</div>
                        </div>
                        <div class="row clearfix">
                            <div class="col-md-6">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                        <thead>
                                            <tr>
                                                <th width="5%"><center>No</center></th>
                                                <th width="70%"><center>Poli</center></th>
                                                <th width="25%"><center>Jumlah</center></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <?php
                                            $no                   = 1;
                                            $totalAsalPoliPulang  = 0;
                                            $dataAsalPoliPulang   = [];
                                            $queryasalpolipulang  = bukaquery("select poliklinik.nm_poli,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by poliklinik.kd_poli order by jumlah desc");
                                            while($rsqueryasalpolipulang = mysqli_fetch_array($queryasalpolipulang)) {
                                                $totalAsalPoliPulang += $rsqueryasalpolipulang["jumlah"];
                                                $dataAsalPoliPulang[] = [
                                                    'label' => $rsqueryasalpolipulang["nm_poli"]." (".$rsqueryasalpolipulang["jumlah"].")",
                                                    'data'  => (int)$rsqueryasalpolipulang["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsqueryasalpolipulang["nm_poli"]."</td>
                                                        <td align='center'>".$rsqueryasalpolipulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalAsalPoliPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_asalpoli_pulang" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Asal Dokter Pasien</div>
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
                                            $no                      = 1;
                                            $totalAsalDokterPulang  = 0;
                                            $dataAsalDokterPulang   = [];
                                            $queryasaldokterpulang  = bukaquery("select dokter.nm_dokter,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by dokter.kd_dokter order by jumlah desc");
                                            while($rsqueryasaldokterpulang = mysqli_fetch_array($queryasaldokterpulang)) {
                                                $totalAsalDokterPulang += $rsqueryasaldokterpulang["jumlah"];
                                                $dataAsalDokterPulang[] = [
                                                    'label' => $rsqueryasaldokterpulang["nm_dokter"]." (".$rsqueryasaldokterpulang["jumlah"].")",
                                                    'data'  => (int)$rsqueryasaldokterpulang["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsqueryasaldokterpulang["nm_dokter"]."</td>
                                                        <td align='center'>".$rsqueryasaldokterpulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalAsalDokterPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_asaldokter_pulang" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Status Pulang Pasien</div>
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
                                            $no                     = 1;
                                            $totalStatusPulang      = 0;
                                            $dataStatusPulang       = [];
                                            $querystatuspulang      = bukaquery("select kamar_inap.stts_pulang,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by kamar_inap.stts_pulang order by jumlah desc");
                                            while($rsquerystatuspulang = mysqli_fetch_array($querystatuspulang)) {
                                                $totalStatusPulang += $rsquerystatuspulang["jumlah"];
                                                $dataStatusPulang[] = [
                                                    'label' => $rsquerystatuspulang["stts_pulang"]." (".$rsquerystatuspulang["jumlah"].")",
                                                    'data'  => (int)$rsquerystatuspulang["jumlah"]
                                                ];
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerystatuspulang["stts_pulang"]."</td>
                                                        <td align='center'>".$rsquerystatuspulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Jumlah :</th>
                                                <th style="text-align:center"><?=$totalStatusPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div id="pie_chart_statuspulang" class="flot-chart" style="height: 400px;"></div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                  = 1;
                                            $indexTanggalPulang  = 0;
                                            $totalTanggalPulang  = 0;
                                            $dataTanggalPulang   = [];
                                            $tickTanggalPulang   = [];
                                            $querytanggalpulang  = bukaquery("select date_format(kamar_inap.tgl_keluar,'%d-%m-%Y') as tanggal,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date(kamar_inap.tgl_keluar) order by date(kamar_inap.tgl_keluar) asc");
                                            while($rsquerytanggalpulang = mysqli_fetch_array($querytanggalpulang)) {
                                                $totalTanggalPulang += $rsquerytanggalpulang["jumlah"];
                                                $dataTanggalPulang[] = [$indexTanggalPulang, (int)$rsquerytanggalpulang["jumlah"]];
                                                $tickTanggalPulang[] = [$indexTanggalPulang, $rsquerytanggalpulang["tanggal"]];
                                                $indexTanggalPulang++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerytanggalpulang["tanggal"]."</td>
                                                        <td align='center'>".$rsquerytanggalpulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalTanggalPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_tanggal_pulang" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                = 1;
                                            $indexBulanPulang  = 0;
                                            $totalBulanPulang  = 0;
                                            $dataBulanPulang   = [];
                                            $tickBulanPulang   = [];
                                            $querybulanpulang  = bukaquery("select date_format(kamar_inap.tgl_keluar,'%m-%Y') as bulan,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date_format(kamar_inap.tgl_keluar,'%Y-%m') order by date_format(kamar_inap.tgl_keluar,'%Y-%m') asc");
                                            while($rsquerybulanpulang = mysqli_fetch_array($querybulanpulang)) {
                                                $totalBulanPulang += $rsquerybulanpulang["jumlah"];
                                                $dataBulanPulang[] = [$indexBulanPulang, (int)$rsquerybulanpulang["jumlah"]];
                                                $tickBulanPulang[] = [$indexBulanPulang, $rsquerybulanpulang["bulan"]];
                                                $indexBulanPulang++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerybulanpulang["bulan"]."</td>
                                                        <td align='center'>".$rsquerybulanpulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalBulanPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_bulan_pulang" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                      = 1;
                                            $indexTanggalBaruPulang  = 0;
                                            $totalTanggalBaruPulang  = 0;
                                            $dataTanggalBaruPulang   = [];
                                            $tickTanggalBaruPulang   = [];
                                            $querytanggalbarupulang  = bukaquery("select date_format(kamar_inap.tgl_keluar,'%d-%m-%Y') as tanggal,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and reg_periksa.stts_daftar='Baru' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date(kamar_inap.tgl_keluar) order by date(kamar_inap.tgl_keluar) asc");
                                            while($rsquerytanggalbarupulang = mysqli_fetch_array($querytanggalbarupulang)) {
                                                $totalTanggalBaruPulang += $rsquerytanggalbarupulang["jumlah"];
                                                $dataTanggalBaruPulang[] = [$indexTanggalBaruPulang, (int)$rsquerytanggalbarupulang["jumlah"]];
                                                $tickTanggalBaruPulang[] = [$indexTanggalBaruPulang, $rsquerytanggalbarupulang["tanggal"]];
                                                $indexTanggalBaruPulang++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerytanggalbarupulang["tanggal"]."</td>
                                                        <td align='center'>".$rsquerytanggalbarupulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalTanggalBaruPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_tanggal_baru_pulang" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                    = 1;
                                            $indexBulanBaruPulang  = 0;
                                            $totalBulanBaruPulang  = 0;
                                            $dataBulanBaruPulang   = [];
                                            $tickBulanBaruPulang   = [];
                                            $querybulanbarupulang  = bukaquery("select date_format(kamar_inap.tgl_keluar,'%m-%Y') as bulan,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and reg_periksa.stts_daftar='Baru' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date_format(kamar_inap.tgl_keluar,'%Y-%m') order by date_format(kamar_inap.tgl_keluar,'%Y-%m') asc");
                                            while($rsquerybulanbarupulang = mysqli_fetch_array($querybulanbarupulang)) {
                                                $totalBulanBaruPulang += $rsquerybulanbarupulang["jumlah"];
                                                $dataBulanBaruPulang[] = [$indexBulanBaruPulang, (int)$rsquerybulanbarupulang["jumlah"]];
                                                $tickBulanBaruPulang[] = [$indexBulanBaruPulang, $rsquerybulanbarupulang["bulan"]];
                                                $indexBulanBaruPulang++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerybulanbarupulang["bulan"]."</td>
                                                        <td align='center'>".$rsquerybulanbarupulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalBulanBaruPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_bulan_baru_pulang" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                      = 1;
                                            $indexTanggalLamaPulang  = 0;
                                            $totalTanggalLamaPulang  = 0;
                                            $dataTanggalLamaPulang   = [];
                                            $tickTanggalLamaPulang   = [];
                                            $querytanggallamapulang  = bukaquery("select date_format(kamar_inap.tgl_keluar,'%d-%m-%Y') as tanggal,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and reg_periksa.stts_daftar='Lama' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date(kamar_inap.tgl_keluar) order by date(kamar_inap.tgl_keluar) asc");
                                            while($rsquerytanggallamapulang = mysqli_fetch_array($querytanggallamapulang)) {
                                                $totalTanggalLamaPulang += $rsquerytanggallamapulang["jumlah"];
                                                $dataTanggalLamaPulang[] = [$indexTanggalLamaPulang, (int)$rsquerytanggallamapulang["jumlah"]];
                                                $tickTanggalLamaPulang[] = [$indexTanggalLamaPulang, $rsquerytanggallamapulang["tanggal"]];
                                                $indexTanggalLamaPulang++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerytanggallamapulang["tanggal"]."</td>
                                                        <td align='center'>".$rsquerytanggallamapulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalTanggalLamaPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_tanggal_lama_pulang" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                        <hr style="margin:20px 0 15px 0;">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:10px;">
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
                                            $no                    = 1;
                                            $indexBulanLamaPulang  = 0;
                                            $totalBulanLamaPulang  = 0;
                                            $dataBulanLamaPulang   = [];
                                            $tickBulanLamaPulang   = [];
                                            $querybulanlamapulang  = bukaquery("select date_format(kamar_inap.tgl_keluar,'%m-%Y') as bulan,count(distinct kamar_inap.no_rawat) as jumlah from kamar_inap inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat where kamar_inap.stts_pulang<>'Pindah Kamar' and kamar_inap.tgl_keluar<>'0000-00-00' and reg_periksa.stts_daftar='Lama' and date(kamar_inap.tgl_keluar) between '$thncaripelayanan-$blncaripelayanan-$tglcaripelayanan' and '$thncaripelayanan2-$blncaripelayanan2-$tglcaripelayanan2' group by date_format(kamar_inap.tgl_keluar,'%Y-%m') order by date_format(kamar_inap.tgl_keluar,'%Y-%m') asc");
                                            while($rsquerybulanlamapulang = mysqli_fetch_array($querybulanlamapulang)) {
                                                $totalBulanLamaPulang += $rsquerybulanlamapulang["jumlah"];
                                                $dataBulanLamaPulang[] = [$indexBulanLamaPulang, (int)$rsquerybulanlamapulang["jumlah"]];
                                                $tickBulanLamaPulang[] = [$indexBulanLamaPulang, $rsquerybulanlamapulang["bulan"]];
                                                $indexBulanLamaPulang++;
                                                echo "<tr>
                                                        <td align='center'>".$no++."</td>
                                                        <td align='left'>".$rsquerybulanlamapulang["bulan"]."</td>
                                                        <td align='center'>".$rsquerybulanlamapulang["jumlah"]."</td>
                                                      </tr>";
                                            }
                                        ?>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="2" style="text-align:right">Total</th>
                                                <th style="text-align:center"><?=$totalBulanLamaPulang;?></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div style="overflow-x:auto;">
                                    <div id="chart_bulan_lama_pulang" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
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
    var dataCaraBayarMasuk = <?= json_encode($dataCaraBayarMasuk) ?>;
    if (dataCaraBayarMasuk.length > 0) {
        $.plot("#pie_chart_carabayar_masuk", dataCaraBayarMasuk, {
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
        $("#pie_chart_carabayar_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataCaraBayarPulang = <?= json_encode($dataCaraBayarPulang) ?>;
    if (dataCaraBayarPulang.length > 0) {
        $.plot("#pie_chart_carabayar_pulang", dataCaraBayarPulang, {
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
        $("#pie_chart_carabayar_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBangsalMasuk = <?= json_encode($dataBangsalMasuk) ?>;
    if (dataBangsalMasuk.length > 0) {
        $.plot("#pie_chart_bangsal_masuk", dataBangsalMasuk, {
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
        $("#pie_chart_bangsal_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBangsalPulang = <?= json_encode($dataBangsalPulang) ?>;
    if (dataBangsalPulang.length > 0) {
        $.plot("#pie_chart_bangsal_pulang", dataBangsalPulang, {
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
        $("#pie_chart_bangsal_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataKelasMasuk = <?= json_encode($dataKelasMasuk) ?>;
    if (dataKelasMasuk.length > 0) {
        $.plot("#pie_chart_kelas_masuk", dataKelasMasuk, {
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
        $("#pie_chart_kelas_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataKelasPulang = <?= json_encode($dataKelasPulang) ?>;
    if (dataKelasPulang.length > 0) {
        $.plot("#pie_chart_kelas_pulang", dataKelasPulang, {
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
        $("#pie_chart_kelas_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataAsalPoliMasuk = <?= json_encode($dataAsalPoliMasuk) ?>;
    if (dataAsalPoliMasuk.length > 0) {
        $.plot("#pie_chart_asalpoli_masuk", dataAsalPoliMasuk, {
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
        $("#pie_chart_asalpoli_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataAsalDokterMasuk = <?= json_encode($dataAsalDokterMasuk) ?>;
    if (dataAsalDokterMasuk.length > 0) {
        $.plot("#pie_chart_asaldokter_masuk", dataAsalDokterMasuk, {
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
        $("#pie_chart_asaldokter_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataStatusPulangMasuk = <?= json_encode($dataStatusPulangMasuk) ?>;
    if (dataStatusPulangMasuk.length > 0) {
        $.plot("#pie_chart_statuspulang_masuk", dataStatusPulangMasuk, {
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
        $("#pie_chart_statuspulang_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataTanggalMasuk = <?= json_encode($dataTanggalMasuk) ?>;
    var tickTanggalMasuk = <?= json_encode($tickTanggalMasuk) ?>;
    if (dataTanggalMasuk.length > 0) {
        $("#chart_tanggal_masuk").css({ width: Math.max(dataTanggalMasuk.length * 90, 500) + "px", minWidth: Math.max(dataTanggalMasuk.length * 90, 500) + "px" });
        $.plot("#chart_tanggal_masuk", [{
            data: dataTanggalMasuk,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#e91e63'
        }], {
            xaxis: {
                ticks: tickTanggalMasuk,
                min: -0.5,
                max: dataTanggalMasuk.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_tanggal_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBulanMasuk = <?= json_encode($dataBulanMasuk) ?>;
    var tickBulanMasuk = <?= json_encode($tickBulanMasuk) ?>;
    if (dataBulanMasuk.length > 0) {
        $("#chart_bulan_masuk").css({ width: Math.max(dataBulanMasuk.length * 90, 500) + "px", minWidth: Math.max(dataBulanMasuk.length * 90, 500) + "px" });
        $.plot("#chart_bulan_masuk", [{
            data: dataBulanMasuk,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#3f51b5'
        }], {
            xaxis: {
                ticks: tickBulanMasuk,
                min: -0.5,
                max: dataBulanMasuk.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_bulan_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataTanggalBaruMasuk = <?= json_encode($dataTanggalBaruMasuk) ?>;
    var tickTanggalBaruMasuk = <?= json_encode($tickTanggalBaruMasuk) ?>;
    if (dataTanggalBaruMasuk.length > 0) {
        $("#chart_tanggal_baru_masuk").css({ width: Math.max(dataTanggalBaruMasuk.length * 90, 500) + "px", minWidth: Math.max(dataTanggalBaruMasuk.length * 90, 500) + "px" });
        $.plot("#chart_tanggal_baru_masuk", [{
            data: dataTanggalBaruMasuk,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#e91e63'
        }], {
            xaxis: {
                ticks: tickTanggalBaruMasuk,
                min: -0.5,
                max: dataTanggalBaruMasuk.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_tanggal_baru_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBulanBaruMasuk = <?= json_encode($dataBulanBaruMasuk) ?>;
    var tickBulanBaruMasuk = <?= json_encode($tickBulanBaruMasuk) ?>;
    if (dataBulanBaruMasuk.length > 0) {
        $("#chart_bulan_baru_masuk").css({ width: Math.max(dataBulanBaruMasuk.length * 90, 500) + "px", minWidth: Math.max(dataBulanBaruMasuk.length * 90, 500) + "px" });
        $.plot("#chart_bulan_baru_masuk", [{
            data: dataBulanBaruMasuk,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#3f51b5'
        }], {
            xaxis: {
                ticks: tickBulanBaruMasuk,
                min: -0.5,
                max: dataBulanBaruMasuk.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_bulan_baru_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataTanggalLamaMasuk = <?= json_encode($dataTanggalLamaMasuk) ?>;
    var tickTanggalLamaMasuk = <?= json_encode($tickTanggalLamaMasuk) ?>;
    if (dataTanggalLamaMasuk.length > 0) {
        $("#chart_tanggal_lama_masuk").css({ width: Math.max(dataTanggalLamaMasuk.length * 90, 500) + "px", minWidth: Math.max(dataTanggalLamaMasuk.length * 90, 500) + "px" });
        $.plot("#chart_tanggal_lama_masuk", [{
            data: dataTanggalLamaMasuk,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#e91e63'
        }], {
            xaxis: {
                ticks: tickTanggalLamaMasuk,
                min: -0.5,
                max: dataTanggalLamaMasuk.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_tanggal_lama_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBulanLamaMasuk = <?= json_encode($dataBulanLamaMasuk) ?>;
    var tickBulanLamaMasuk = <?= json_encode($tickBulanLamaMasuk) ?>;
    if (dataBulanLamaMasuk.length > 0) {
        $("#chart_bulan_lama_masuk").css({ width: Math.max(dataBulanLamaMasuk.length * 90, 500) + "px", minWidth: Math.max(dataBulanLamaMasuk.length * 90, 500) + "px" });
        $.plot("#chart_bulan_lama_masuk", [{
            data: dataBulanLamaMasuk,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#3f51b5'
        }], {
            xaxis: {
                ticks: tickBulanLamaMasuk,
                min: -0.5,
                max: dataBulanLamaMasuk.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_bulan_lama_masuk").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataAsalPoliPulang = <?= json_encode($dataAsalPoliPulang) ?>;
    if (dataAsalPoliPulang.length > 0) {
        $.plot("#pie_chart_asalpoli_pulang", dataAsalPoliPulang, {
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
        $("#pie_chart_asalpoli_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataAsalDokterPulang = <?= json_encode($dataAsalDokterPulang) ?>;
    if (dataAsalDokterPulang.length > 0) {
        $.plot("#pie_chart_asaldokter_pulang", dataAsalDokterPulang, {
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
        $("#pie_chart_asaldokter_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataStatusPulang = <?= json_encode($dataStatusPulang) ?>;
    if (dataStatusPulang.length > 0) {
        $.plot("#pie_chart_statuspulang", dataStatusPulang, {
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
        $("#pie_chart_statuspulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataTanggalPulang = <?= json_encode($dataTanggalPulang) ?>;
    var tickTanggalPulang = <?= json_encode($tickTanggalPulang) ?>;
    if (dataTanggalPulang.length > 0) {
        $("#chart_tanggal_pulang").css({ width: Math.max(dataTanggalPulang.length * 90, 500) + "px", minWidth: Math.max(dataTanggalPulang.length * 90, 500) + "px" });
        $.plot("#chart_tanggal_pulang", [{
            data: dataTanggalPulang,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#e91e63'
        }], {
            xaxis: {
                ticks: tickTanggalPulang,
                min: -0.5,
                max: dataTanggalPulang.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_tanggal_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBulanPulang = <?= json_encode($dataBulanPulang) ?>;
    var tickBulanPulang = <?= json_encode($tickBulanPulang) ?>;
    if (dataBulanPulang.length > 0) {
        $("#chart_bulan_pulang").css({ width: Math.max(dataBulanPulang.length * 90, 500) + "px", minWidth: Math.max(dataBulanPulang.length * 90, 500) + "px" });
        $.plot("#chart_bulan_pulang", [{
            data: dataBulanPulang,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#3f51b5'
        }], {
            xaxis: {
                ticks: tickBulanPulang,
                min: -0.5,
                max: dataBulanPulang.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_bulan_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataTanggalBaruPulang = <?= json_encode($dataTanggalBaruPulang) ?>;
    var tickTanggalBaruPulang = <?= json_encode($tickTanggalBaruPulang) ?>;
    if (dataTanggalBaruPulang.length > 0) {
        $("#chart_tanggal_baru_pulang").css({ width: Math.max(dataTanggalBaruPulang.length * 90, 500) + "px", minWidth: Math.max(dataTanggalBaruPulang.length * 90, 500) + "px" });
        $.plot("#chart_tanggal_baru_pulang", [{
            data: dataTanggalBaruPulang,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#e91e63'
        }], {
            xaxis: {
                ticks: tickTanggalBaruPulang,
                min: -0.5,
                max: dataTanggalBaruPulang.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_tanggal_baru_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBulanBaruPulang = <?= json_encode($dataBulanBaruPulang) ?>;
    var tickBulanBaruPulang = <?= json_encode($tickBulanBaruPulang) ?>;
    if (dataBulanBaruPulang.length > 0) {
        $("#chart_bulan_baru_pulang").css({ width: Math.max(dataBulanBaruPulang.length * 90, 500) + "px", minWidth: Math.max(dataBulanBaruPulang.length * 90, 500) + "px" });
        $.plot("#chart_bulan_baru_pulang", [{
            data: dataBulanBaruPulang,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#3f51b5'
        }], {
            xaxis: {
                ticks: tickBulanBaruPulang,
                min: -0.5,
                max: dataBulanBaruPulang.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_bulan_baru_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataTanggalLamaPulang = <?= json_encode($dataTanggalLamaPulang) ?>;
    var tickTanggalLamaPulang = <?= json_encode($tickTanggalLamaPulang) ?>;
    if (dataTanggalLamaPulang.length > 0) {
        $("#chart_tanggal_lama_pulang").css({ width: Math.max(dataTanggalLamaPulang.length * 90, 500) + "px", minWidth: Math.max(dataTanggalLamaPulang.length * 90, 500) + "px" });
        $.plot("#chart_tanggal_lama_pulang", [{
            data: dataTanggalLamaPulang,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#e91e63'
        }], {
            xaxis: {
                ticks: tickTanggalLamaPulang,
                min: -0.5,
                max: dataTanggalLamaPulang.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_tanggal_lama_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBulanLamaPulang = <?= json_encode($dataBulanLamaPulang) ?>;
    var tickBulanLamaPulang = <?= json_encode($tickBulanLamaPulang) ?>;
    if (dataBulanLamaPulang.length > 0) {
        $("#chart_bulan_lama_pulang").css({ width: Math.max(dataBulanLamaPulang.length * 90, 500) + "px", minWidth: Math.max(dataBulanLamaPulang.length * 90, 500) + "px" });
        $.plot("#chart_bulan_lama_pulang", [{
            data: dataBulanLamaPulang,
            lines: { show: true, fill: false },
            points: { show: true, radius: 4 },
            color: '#3f51b5'
        }], {
            xaxis: {
                ticks: tickBulanLamaPulang,
                min: -0.5,
                max: dataBulanLamaPulang.length - 0.5,
                tickLength: 0
            },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_bulan_lama_pulang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>