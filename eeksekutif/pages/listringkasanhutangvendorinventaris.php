<div class="block-header">
    <h2><center>RINGKASAN HUTANG ASET/INVENTARIS</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th style="min-width:100px;white-space:nowrap;"><center>Kode Suplier</center></th>
                                        <th style="min-width:220px;"><center>Nama Suplier</center></th>
                                        <th style="min-width:150px;white-space:nowrap;"><center>Sisa Hutang</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $totalHutang   = 0;
                                    $dataPieHutang = [];
                                    $queryHutang = bukaquery(
                                        "select inventaris_pemesanan.kode_suplier,inventaris_suplier.nama_suplier, ".
                                        "(inventaris_pemesanan.tagihan-(select ifnull(sum(besar_bayar),0) from bayar_pemesanan_inventaris where bayar_pemesanan_inventaris.no_faktur=inventaris_pemesanan.no_faktur)) as sisahutang ".
                                        "from inventaris_pemesanan inner join inventaris_suplier on inventaris_pemesanan.kode_suplier=inventaris_suplier.kode_suplier ".
                                        "where (inventaris_pemesanan.status='Belum Dibayar' or inventaris_pemesanan.status='Belum Lunas') ".
                                        "group by inventaris_pemesanan.kode_suplier order by inventaris_pemesanan.kode_suplier asc"
                                    );
                                    while($rshutang = mysqli_fetch_array($queryHutang)) {
                                        $totalHutang += $rshutang["sisahutang"];
                                        echo "<tr>
                                                <td align='left' style='white-space:nowrap;'>".$rshutang["kode_suplier"]."</td>
                                                <td align='left'>".$rshutang["nama_suplier"]."</td>
                                                <td align='right' style='white-space:nowrap;'>".number_format($rshutang["sisahutang"],0,',','.')."</td>
                                              </tr>";
                                        if($rshutang["sisahutang"]>0) {
                                            $dataPieHutang[] = [
                                                'label' => $rshutang["nama_suplier"]." (".number_format($rshutang["sisahutang"],0,',','.').")",
                                                'data'  => (float)$rshutang["sisahutang"]
                                            ];
                                        }
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right;">Total</th>
                                        <th style="text-align:right;"><?=number_format($totalHutang,0,',','.');?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_hutang" class="flot-chart" style="height: 400px;"></div>
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
    var dataPieHutang = <?= json_encode($dataPieHutang) ?>;
    if (dataPieHutang.length > 0) {
        $.plot("#pie_chart_hutang", dataPieHutang, {
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
        $("#pie_chart_hutang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>