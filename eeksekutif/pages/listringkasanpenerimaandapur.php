<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncaripenerimaan  = $thnsekarang;
    $blncaripenerimaan  = $blnsekarang;
    $tglcaripenerimaan  = $tglsekarang;
    $thncaripenerimaan2 = $thnsekarang;
    $blncaripenerimaan2 = $blnsekarang;
    $tglcaripenerimaan2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncaripenerimaan  = validTeks(trim(isset($_POST['tgl_cari_penerimaan']))?substr($_POST['tgl_cari_penerimaan'],6,4):$thnsekarang);
        $blncaripenerimaan  = validTeks(trim(isset($_POST['tgl_cari_penerimaan']))?substr($_POST['tgl_cari_penerimaan'],3,2):$blnsekarang);
        $tglcaripenerimaan  = validTeks(trim(isset($_POST['tgl_cari_penerimaan']))?substr($_POST['tgl_cari_penerimaan'],0,2):$tglsekarang);
        $thncaripenerimaan2 = validTeks(trim(isset($_POST['tgl_cari_penerimaan2']))?substr($_POST['tgl_cari_penerimaan2'],6,4):$thnsekarang);
        $blncaripenerimaan2 = validTeks(trim(isset($_POST['tgl_cari_penerimaan2']))?substr($_POST['tgl_cari_penerimaan2'],3,2):$blnsekarang);
        $tglcaripenerimaan2 = validTeks(trim(isset($_POST['tgl_cari_penerimaan2']))?substr($_POST['tgl_cari_penerimaan2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>RINGKASAN PENERIMAAN DAPUR</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_penerimaan">Periode</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_penerimaan" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripenerimaan."-".$blncaripenerimaan."-".$thncaripenerimaan;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_penerimaan2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_penerimaan2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripenerimaan2."-".$blncaripenerimaan2."-".$thncaripenerimaan2;?>"/>
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
                            $totalTagihan    = 0;
                            $rankingJumlah   = [];
                            $rankingTotal    = [];
                            $queryPenerimaan = bukaquery(
                                "select dapurdetailpesan.kode_brng,dapurbarang.nama_brng,dapurbarang.jenis,kodesatuan.satuan,sum(dapurdetailpesan.jumlah) as jumlah,sum(dapurdetailpesan.total) as total from dapurpemesanan ".
                                "inner join dapurdetailpesan on dapurpemesanan.no_faktur=dapurdetailpesan.no_faktur inner join dapurbarang on dapurdetailpesan.kode_brng=dapurbarang.kode_brng inner join kodesatuan on dapurdetailpesan.kode_sat=kodesatuan.kode_sat ".
                                "where dapurpemesanan.tgl_pesan between '$thncaripenerimaan-$blncaripenerimaan-$tglcaripenerimaan' and '$thncaripenerimaan2-$blncaripenerimaan2-$tglcaripenerimaan2' ".
                                "group by dapurdetailpesan.kode_brng order by dapurbarang.nama_brng asc"
                            );
                            while($rspenerimaan = mysqli_fetch_array($queryPenerimaan)) {
                                $totalTagihan += $rspenerimaan["total"];
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$rspenerimaan["kode_brng"]."</td>
                                        <td align='left'>".$rspenerimaan["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rspenerimaan["satuan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rspenerimaan["jenis"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rspenerimaan["jumlah"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rspenerimaan["total"],0,',','.')."</td>
                                      </tr>";
                                $rankingJumlah[$rspenerimaan["nama_brng"]] = (float)$rspenerimaan["jumlah"];
                                $rankingTotal[$rspenerimaan["nama_brng"]]  = (float)$rspenerimaan["total"];
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