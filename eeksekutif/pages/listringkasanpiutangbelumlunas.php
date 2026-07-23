<div class="block-header">
    <h2><center>RINGKASAN PIUTANG BELUM LUNAS</center></h2>
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
                                        <th style="min-width:220px;"><center>Cara Bayar</center></th>
                                        <th style="min-width:150px;white-space:nowrap;"><center>Sisa Piutang</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $totalPiutang   = 0;
                                    $dataPiePiutang = [];
                                    $queryPiutang = bukaquery(
                                        "select gabungan.png_jawab,sum(gabungan.sisapiutang) as sisapiutang from ( ".
                                        "select penjab.png_jawab,detail_piutang_pasien.sisapiutang ".
                                        "from piutang_pasien ".
                                        "inner join detail_piutang_pasien on piutang_pasien.no_rawat=detail_piutang_pasien.no_rawat ".
                                        "inner join penjab on detail_piutang_pasien.kd_pj=penjab.kd_pj ".
                                        "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat ".
                                        "inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat ".
                                        "where detail_piutang_pasien.sisapiutang>=1 ".
                                        "union all ".
                                        "select penjab.png_jawab,detail_piutang_pasien.sisapiutang ".
                                        "from piutang_pasien ".
                                        "inner join detail_piutang_pasien on piutang_pasien.no_rawat=detail_piutang_pasien.no_rawat ".
                                        "inner join penjab on detail_piutang_pasien.kd_pj=penjab.kd_pj ".
                                        "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat ".
                                        "inner join nota_inap on nota_inap.no_rawat=reg_periksa.no_rawat ".
                                        "where detail_piutang_pasien.sisapiutang>=1 ".
                                        ") as gabungan group by gabungan.png_jawab order by gabungan.png_jawab asc"
                                    );
                                    while($rspiutang = mysqli_fetch_array($queryPiutang)) {
                                        $totalPiutang += $rspiutang["sisapiutang"];
                                        echo "<tr>
                                                <td align='left'>".$rspiutang["png_jawab"]."</td>
                                                <td align='right' style='white-space:nowrap;'>".number_format($rspiutang["sisapiutang"],0,',','.')."</td>
                                              </tr>";
                                        if($rspiutang["sisapiutang"]>0) {
                                            $dataPiePiutang[] = [
                                                'label' => $rspiutang["png_jawab"]." (".number_format($rspiutang["sisapiutang"],0,',','.').")",
                                                'data'  => (float)$rspiutang["sisapiutang"]
                                            ];
                                        }
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th style="text-align:right;">Total</th>
                                        <th style="text-align:right;"><?=number_format($totalPiutang,0,',','.');?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_piutang" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Rawat Jalan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th style="min-width:220px;"><center>Cara Bayar</center></th>
                                        <th style="min-width:150px;white-space:nowrap;"><center>Sisa Piutang</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $totalPiutangJalan   = 0;
                                    $dataPiePiutangJalan = [];
                                    $queryPiutangJalan = bukaquery(
                                        "select penjab.png_jawab,sum(detail_piutang_pasien.sisapiutang) as sisapiutang ".
                                        "from piutang_pasien ".
                                        "inner join detail_piutang_pasien on piutang_pasien.no_rawat=detail_piutang_pasien.no_rawat ".
                                        "inner join penjab on detail_piutang_pasien.kd_pj=penjab.kd_pj ".
                                        "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat ".
                                        "inner join nota_jalan on nota_jalan.no_rawat=reg_periksa.no_rawat ".
                                        "where detail_piutang_pasien.sisapiutang>=1 ".
                                        "group by penjab.png_jawab order by penjab.png_jawab asc"
                                    );
                                    while($rsjalan = mysqli_fetch_array($queryPiutangJalan)) {
                                        $totalPiutangJalan += $rsjalan["sisapiutang"];
                                        echo "<tr>
                                                <td align='left'>".$rsjalan["png_jawab"]."</td>
                                                <td align='right' style='white-space:nowrap;'>".number_format($rsjalan["sisapiutang"],0,',','.')."</td>
                                              </tr>";
                                        if($rsjalan["sisapiutang"]>0) {
                                            $dataPiePiutangJalan[] = [
                                                'label' => $rsjalan["png_jawab"]." (".number_format($rsjalan["sisapiutang"],0,',','.').")",
                                                'data'  => (float)$rsjalan["sisapiutang"]
                                            ];
                                        }
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th style="text-align:right;">Total</th>
                                        <th style="text-align:right;"><?=number_format($totalPiutangJalan,0,',','.');?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_piutang_jalan" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Rawat Inap</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th style="min-width:220px;"><center>Cara Bayar</center></th>
                                        <th style="min-width:150px;white-space:nowrap;"><center>Sisa Piutang</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $totalPiutangInap   = 0;
                                    $dataPiePiutangInap = [];
                                    $queryPiutangInap = bukaquery(
                                        "select penjab.png_jawab,sum(detail_piutang_pasien.sisapiutang) as sisapiutang ".
                                        "from piutang_pasien ".
                                        "inner join detail_piutang_pasien on piutang_pasien.no_rawat=detail_piutang_pasien.no_rawat ".
                                        "inner join penjab on detail_piutang_pasien.kd_pj=penjab.kd_pj ".
                                        "inner join reg_periksa on piutang_pasien.no_rawat=reg_periksa.no_rawat ".
                                        "inner join nota_inap on nota_inap.no_rawat=reg_periksa.no_rawat ".
                                        "where detail_piutang_pasien.sisapiutang>=1 ".
                                        "group by penjab.png_jawab order by penjab.png_jawab asc"
                                    );
                                    while($rsinap = mysqli_fetch_array($queryPiutangInap)) {
                                        $totalPiutangInap += $rsinap["sisapiutang"];
                                        echo "<tr>
                                                <td align='left'>".$rsinap["png_jawab"]."</td>
                                                <td align='right' style='white-space:nowrap;'>".number_format($rsinap["sisapiutang"],0,',','.')."</td>
                                              </tr>";
                                        if($rsinap["sisapiutang"]>0) {
                                            $dataPiePiutangInap[] = [
                                                'label' => $rsinap["png_jawab"]." (".number_format($rsinap["sisapiutang"],0,',','.').")",
                                                'data'  => (float)$rsinap["sisapiutang"]
                                            ];
                                        }
                                    }
                                ?>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th style="text-align:right;">Total</th>
                                        <th style="text-align:right;"><?=number_format($totalPiutangInap,0,',','.');?></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_piutang_inap" class="flot-chart" style="height: 400px;"></div>
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
    var dataPiePiutang = <?= json_encode($dataPiePiutang) ?>;
    if (dataPiePiutang.length > 0) {
        $.plot("#pie_chart_piutang", dataPiePiutang, {
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
        $("#pie_chart_piutang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }

    var dataPiePiutangJalan = <?= json_encode($dataPiePiutangJalan) ?>;
    if (dataPiePiutangJalan.length > 0) {
        $.plot("#pie_chart_piutang_jalan", dataPiePiutangJalan, {
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
        $("#pie_chart_piutang_jalan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }

    var dataPiePiutangInap = <?= json_encode($dataPiePiutangInap) ?>;
    if (dataPiePiutangInap.length > 0) {
        $.plot("#pie_chart_piutang_inap", dataPiePiutangInap, {
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
        $("#pie_chart_piutang_inap").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>