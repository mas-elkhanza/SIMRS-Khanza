<?php
    $tahunsekarang = date("Y");
    $tahuncari     = $tahunsekarang;
    if(isset($_POST["BtnCari"])){
        $tahuncari = validTeks(trim(isset($_POST['tahun_cari']))?$_POST['tahun_cari']:$tahunsekarang);
    }
?>
<div class="block-header">
    <h2><center>NILAI PENERIMAAN VENDOR FARMASI PER BULAN</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <label for="tahun_cari">Tahun</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="tahun_list" type="text" id="tahun_cari" name="tahun_cari" pattern="[0-9]{4}" title="YYYY" placeholder="Pilih Tahun" value="" size="60" maxlength="4" autocomplete="off" required/>
                                    <datalist id="tahun_list">
                                        <?php
                                            for($thn=$tahunsekarang;$thn>=2010;$thn--) {
                                                echo "<option>".$thn."</option>";
                                            }
                                        ?>
                                    </datalist>
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
                                <th style="min-width:100px;white-space:nowrap;"><center>Kode Suplier</center></th>
                                <th style="min-width:180px;"><center>Nama Suplier</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Januari</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Februari</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Maret</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>April</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Mei</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Juni</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Juli</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Agustus</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>September</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Oktober</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>November</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Desember</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $namaBulan       = ["Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"];
                            $totalBulan      = array_fill(0,12,0);
                            $totalTagihanAll = 0;
                            $dataPerVendor   = [];
                            $querySuplier = bukaquery("select datasuplier.kode_suplier,datasuplier.nama_suplier from datasuplier order by datasuplier.nama_suplier asc");
                            while($rssuplier = mysqli_fetch_array($querySuplier)) {
                                $nilaiBulan     = [];
                                $tagihanSuplier = 0;
                                for($b=1;$b<=12;$b++) {
                                    $bulanStr = str_pad($b,2,'0',STR_PAD_LEFT);
                                    $nilai = (float) getOne(
                                        "select sum(detailpesan.total) from pemesanan inner join detailpesan on pemesanan.no_faktur=detailpesan.no_faktur ".
                                        "inner join databarang on detailpesan.kode_brng=databarang.kode_brng where pemesanan.kode_suplier='".$rssuplier["kode_suplier"]."' ".
                                        "and left(pemesanan.tgl_pesan,7)='".$tahuncari."-".$bulanStr."'"
                                    );
                                    $nilaiBulan[]           = $nilai;
                                    $totalBulan[$b-1]      += $nilai;
                                    $tagihanSuplier         += $nilai;
                                }
                                $totalTagihanAll += $tagihanSuplier;
                                if($tagihanSuplier>0) {
                                    $dataPerVendor[] = [
                                        'label' => $rssuplier["nama_suplier"]." (".number_format($tagihanSuplier,0,',','.').")",
                                        'data'  => (float)$tagihanSuplier
                                    ];
                                }
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$rssuplier["kode_suplier"]."</td>
                                        <td align='left'>".$rssuplier["nama_suplier"]."</td>";
                                foreach($nilaiBulan as $nilaiSatuBulan) {
                                    echo "<td align='right' style='white-space:nowrap;'>".number_format($nilaiSatuBulan,0,',','.')."</td>";
                                }
                                echo "<td align='right' style='white-space:nowrap;'><b>".number_format($tagihanSuplier,0,',','.')."</b></td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th style="text-align:left;">Total :</th>
                                <th></th>
                                <?php
                                    foreach($totalBulan as $totalSatuBulan) {
                                        echo "<th style='text-align:right;'>".number_format($totalSatuBulan,0,',','.')."</th>";
                                    }
                                ?>
                                <th style="text-align:right;"><?=number_format($totalTagihanAll,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Total Nilai Penerimaan Per Vendor</div>
                        </div>
                        <div id="pie_chart_pervendor" class="flot-chart" style="height: 400px;"></div>
                    </div>
                    <div class="col-md-6">
                        <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                            <div class="text-center" style="font-size:16px;color:#777777;">Nilai Penerimaan Per Bulan</div>
                        </div>
                        <div style="overflow-x:auto;">
                            <div id="chart_perbulan" style="height:400px;width:<?=max(count($namaBulan)*90,500);?>px;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="plugins/flot-charts/jquery.flot.js"></script>
<script src="plugins/flot-charts/jquery.flot.resize.js"></script>
<script src="plugins/flot-charts/jquery.flot.pie.js"></script>
<script>
$(function() {
    var dataPerVendor = <?= json_encode($dataPerVendor) ?>;
    if (dataPerVendor.length > 0) {
        $.plot("#pie_chart_pervendor", dataPerVendor, {
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
        $("#pie_chart_pervendor").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }

    var dataBulan = <?php
        $pointsBulan = [];
        foreach ($totalBulan as $idx => $val) {
            $pointsBulan[] = "[".$idx.",".$val."]";
        }
        echo "[".implode(",", $pointsBulan)."]";
    ?>;
    var ticksBulan = <?php
        $ticksBulan = [];
        foreach ($namaBulan as $idx => $nm) {
            $ticksBulan[] = "[".$idx.",'".$nm."']";
        }
        echo "[".implode(",", $ticksBulan)."]";
    ?>;
    $.plot("#chart_perbulan", [{ data: dataBulan, lines: { show: true }, points: { show: true } }], {
        xaxis: { ticks: ticksBulan },
        yaxis: { min: 0 }
    });
});
</script>