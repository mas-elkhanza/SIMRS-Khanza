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
});
</script>