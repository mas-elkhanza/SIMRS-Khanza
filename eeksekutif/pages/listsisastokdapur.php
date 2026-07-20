<div class="block-header">
    <h2><center>SISA STOK & NILAI ASET DAPUR</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:40px;white-space:nowrap;"><center>No</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Kode Barang</center></th>
                                <th style="min-width:220px;"><center>Nama Barang</center></th>
                                <th style="min-width:150px;white-space:nowrap;"><center>Jenis</center></th>
                                <th style="min-width:80px;white-space:nowrap;"><center>Satuan</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>Stok</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>Harga</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Nilai Aset</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $no        = 1;
                            $totalAset = 0;
                            $jenisAset = [];
                            $queryStok = bukaquery("select dapurbarang.kode_brng,dapurbarang.nama_brng,dapurbarang.kode_sat,dapurbarang.jenis,dapurbarang.stok,dapurbarang.harga from dapurbarang where dapurbarang.status='1' order by dapurbarang.kode_brng asc");
                            while($rsqueryStok = mysqli_fetch_array($queryStok)) {
                                $nilaiAsetBarang = $rsqueryStok["stok"] * $rsqueryStok["harga"];
                                $totalAset      += $nilaiAsetBarang;
                                if(!isset($jenisAset[$rsqueryStok["jenis"]])) {
                                    $jenisAset[$rsqueryStok["jenis"]] = 0;
                                }
                                $jenisAset[$rsqueryStok["jenis"]] += $nilaiAsetBarang;
                                echo "<tr>
                                        <td align='center' style='white-space:nowrap;'>".$no++."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["kode_brng"]."</td>
                                        <td align='left'>".$rsqueryStok["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["jenis"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["kode_sat"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryStok["stok"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryStok["harga"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($nilaiAsetBarang,0,',','.')."</td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="7" style="text-align:right">Total</th>
                                <th style="text-align:right"><?=number_format($totalAset,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Nilai Aset Per Jenis</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="10%"><center>No</center></th>
                                        <th width="60%"><center>Jenis</center></th>
                                        <th width="30%"><center>Nilai Aset</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    arsort($jenisAset);
                                    $noJenis      = 1;
                                    $dataPieJenis = [];
                                    foreach($jenisAset as $namaJenis => $nilaiJenis) {
                                        if($nilaiJenis>0) {
                                            echo "<tr>
                                                    <td align='center'>".$noJenis++."</td>
                                                    <td align='left'>".$namaJenis."</td>
                                                    <td align='right'>".number_format($nilaiJenis,0,',','.')."</td>
                                                  </tr>";
                                            $dataPieJenis[] = [
                                                'label' => $namaJenis." (".number_format($nilaiJenis,0,',','.').")",
                                                'data'  => (float)$nilaiJenis
                                            ];
                                        }
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th></th>
                                        <th style="text-align:left;">Jumlah Total</th>
                                        <th style="text-align:right;"><?=number_format($totalAset,0,',','.');?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_asetjenis" class="flot-chart" style="height: 400px;"></div>
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
    var dataPieJenis = <?= json_encode($dataPieJenis) ?>;
    if (dataPieJenis.length > 0) {
        $.plot("#pie_chart_asetjenis", dataPieJenis, {
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
        $("#pie_chart_asetjenis").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>