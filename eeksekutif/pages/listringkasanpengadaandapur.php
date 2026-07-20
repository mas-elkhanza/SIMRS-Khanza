<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncaripengadaan  = $thnsekarang;
    $blncaripengadaan  = $blnsekarang;
    $tglcaripengadaan  = $tglsekarang;
    $thncaripengadaan2 = $thnsekarang;
    $blncaripengadaan2 = $blnsekarang;
    $tglcaripengadaan2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncaripengadaan  = validTeks(trim(isset($_POST['tgl_cari_pengadaan']))?substr($_POST['tgl_cari_pengadaan'],6,4):$thnsekarang);
        $blncaripengadaan  = validTeks(trim(isset($_POST['tgl_cari_pengadaan']))?substr($_POST['tgl_cari_pengadaan'],3,2):$blnsekarang);
        $tglcaripengadaan  = validTeks(trim(isset($_POST['tgl_cari_pengadaan']))?substr($_POST['tgl_cari_pengadaan'],0,2):$tglsekarang);
        $thncaripengadaan2 = validTeks(trim(isset($_POST['tgl_cari_pengadaan2']))?substr($_POST['tgl_cari_pengadaan2'],6,4):$thnsekarang);
        $blncaripengadaan2 = validTeks(trim(isset($_POST['tgl_cari_pengadaan2']))?substr($_POST['tgl_cari_pengadaan2'],3,2):$blnsekarang);
        $tglcaripengadaan2 = validTeks(trim(isset($_POST['tgl_cari_pengadaan2']))?substr($_POST['tgl_cari_pengadaan2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>RINGKASAN PENGADAAN DAPUR</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_pengadaan">Periode</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_pengadaan" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripengadaan."-".$blncaripengadaan."-".$thncaripengadaan;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_pengadaan2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_pengadaan2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripengadaan2."-".$blncaripengadaan2."-".$thncaripengadaan2;?>"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Tampilkan Data & Grafik</button></center>
                </form>
            </div>
            <div class="body" style="padding-top:0;">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:100px;white-space:nowrap;"><center>Kode Barang</center></th>
                                <th style="min-width:220px;"><center>Nama Barang</center></th>
                                <th style="min-width:80px;white-space:nowrap;"><center>Satuan</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Jenis</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>Jumlah</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $totalTagihan   = 0;
                            $rankingJumlah  = [];
                            $rankingTotal   = [];
                            $queryPengadaan = bukaquery(
                                "select dapurdetailbeli.kode_brng,dapurbarang.nama_brng,dapurbarang.jenis,kodesatuan.satuan,sum(dapurdetailbeli.jumlah) as jumlah,sum(dapurdetailbeli.total) as total from dapurpembelian inner join dapurdetailbeli on dapurpembelian.no_faktur=dapurdetailbeli.no_faktur ".
                                "inner join dapurbarang on dapurdetailbeli.kode_brng=dapurbarang.kode_brng inner join kodesatuan on dapurbarang.kode_sat=kodesatuan.kode_sat where dapurpembelian.tgl_beli between '$thncaripengadaan-$blncaripengadaan-$tglcaripengadaan' and '$thncaripengadaan2-$blncaripengadaan2-$tglcaripengadaan2' ".
                                "group by dapurdetailbeli.kode_brng order by dapurbarang.nama_brng asc"
                            );
                            while($rspengadaan = mysqli_fetch_array($queryPengadaan)) {
                                $totalTagihan += $rspengadaan["total"];
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$rspengadaan["kode_brng"]."</td>
                                        <td align='left'>".$rspengadaan["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rspengadaan["satuan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rspengadaan["jenis"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rspengadaan["jumlah"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rspengadaan["total"],0,',','.')."</td>
                                      </tr>";
                                $rankingJumlah[$rspengadaan["nama_brng"]] = (float)$rspengadaan["jumlah"];
                                $rankingTotal[$rspengadaan["nama_brng"]]  = (float)$rspengadaan["total"];
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="5" style="text-align:right;">Total</th>
                                <th style="text-align:right;"><?=number_format($totalTagihan,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">10 Besar Jumlah</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="10%"><center>No</center></th>
                                        <th width="60%"><center>Nama Barang</center></th>
                                        <th width="30%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    arsort($rankingJumlah);
                                    $rankingJumlah = array_slice($rankingJumlah,0,10,true);
                                    $noJumlah      = 1;
                                    $dataPieJumlah = [];
                                    foreach($rankingJumlah as $namaBrg => $jml) {
                                        if($jml>0) {
                                            echo "<tr>
                                                    <td align='center'>".$noJumlah++."</td>
                                                    <td align='left'>".$namaBrg."</td>
                                                    <td align='right'>".number_format($jml,0,',','.')."</td>
                                                  </tr>";
                                            $dataPieJumlah[] = [
                                                'label' => $namaBrg." (".number_format($jml,0,',','.').")",
                                                'data'  => $jml
                                            ];
                                        }
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_jumlah" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">10 Besar Biaya</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="10%"><center>No</center></th>
                                        <th width="60%"><center>Nama Barang</center></th>
                                        <th width="30%"><center>Total</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    arsort($rankingTotal);
                                    $rankingTotal = array_slice($rankingTotal,0,10,true);
                                    $noTotal      = 1;
                                    $dataPieTotal = [];
                                    foreach($rankingTotal as $namaBrg => $nilaiTotal) {
                                        if($nilaiTotal>0) {
                                            echo "<tr>
                                                    <td align='center'>".$noTotal++."</td>
                                                    <td align='left'>".$namaBrg."</td>
                                                    <td align='right'>".number_format($nilaiTotal,0,',','.')."</td>
                                                  </tr>";
                                            $dataPieTotal[] = [
                                                'label' => $namaBrg." (".number_format($nilaiTotal,0,',','.').")",
                                                'data'  => $nilaiTotal
                                            ];
                                        }
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_total" class="flot-chart" style="height: 400px;"></div>
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
    var dataPieJumlah = <?= json_encode($dataPieJumlah) ?>;
    if (dataPieJumlah.length > 0) {
        $.plot("#pie_chart_jumlah", dataPieJumlah, {
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
        $("#pie_chart_jumlah").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }

    var dataPieTotal = <?= json_encode($dataPieTotal) ?>;
    if (dataPieTotal.length > 0) {
        $.plot("#pie_chart_total", dataPieTotal, {
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
        $("#pie_chart_total").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>