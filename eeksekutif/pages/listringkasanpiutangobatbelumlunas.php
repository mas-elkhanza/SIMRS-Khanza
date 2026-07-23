<div class="block-header">
    <h2><center>PIUTANG OBAT BELUM LUNAS</center></h2>
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
                                        <th style="min-width:100px;white-space:nowrap;"><center>No RM</center></th>
                                        <th style="min-width:220px;"><center>Nama Pasien</center></th>
                                        <th style="min-width:150px;white-space:nowrap;"><center>Sisa Piutang</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $totalSisa      = 0;
                                    $ticksPasien    = [];
                                    $dataLinePasien = [];
                                    $indexPasien    = 0;
                                    $queryPiutangObat = bukaquery(
                                        "select t.no_rkm_medis,t.nm_pasien,sum(t.sisa_bersih) as sisa_bersih from ( ".
                                        "select piutang.no_rkm_medis,piutang.nm_pasien,piutang.nota_piutang, ".
                                        "(piutang.sisapiutang-(select ifnull(sum(bayar_piutang.besar_cicilan)+sum(bayar_piutang.diskon_piutang)+sum(bayar_piutang.tidak_terbayar),0) ".
                                        "from bayar_piutang where bayar_piutang.no_rawat=piutang.nota_piutang)) as sisa_bersih ".
                                        "from piutang ".
                                        ") as t where t.sisa_bersih>0 ".
                                        "group by t.no_rkm_medis,t.nm_pasien order by t.nm_pasien asc"
                                    );
                                    while($rspasien = mysqli_fetch_array($queryPiutangObat)) {
                                        $totalSisa += $rspasien["sisa_bersih"];
                                        echo "<tr>
                                                <td align='left' style='white-space:nowrap;'>".$rspasien["no_rkm_medis"]."</td>
                                                <td align='left'>".$rspasien["nm_pasien"]."</td>
                                                <td align='right' style='white-space:nowrap;'>".number_format($rspasien["sisa_bersih"],0,',','.')."</td>
                                              </tr>";
                                        $ticksPasien[]    = [$indexPasien,$rspasien["nm_pasien"]." (".$rspasien["no_rkm_medis"].")"];
                                        $dataLinePasien[] = [$indexPasien,(float)$rspasien["sisa_bersih"]];
                                        $indexPasien++;
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="2" style="text-align:right;">Total</th>
                                        <th style="text-align:right;"><?=number_format($totalSisa,0,',','.');?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div style="overflow-x:auto;">
                            <div id="chart_piutang_pasien" class="flot-chart" style="height:400px;width:<?=max(count($ticksPasien)*90,500);?>px;"></div>
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
<script>
$(function() {
    var dataLinePasien = <?= json_encode($dataLinePasien) ?>;
    var ticksPasien     = <?= json_encode($ticksPasien) ?>;
    if (dataLinePasien.length > 0) {
        $.plot("#chart_piutang_pasien", [{
            data: dataLinePasien,
            lines: { show: true },
            points: { show: true, radius: 5 }
        }], {
            xaxis: { ticks: ticksPasien, min: -0.5, max: ticksPasien.length - 0.5 },
            yaxis: { min: 0, tickDecimals: 0 },
            grid: { hoverable: true, borderWidth: 1 }
        });
    } else {
        $("#chart_piutang_pasien").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>