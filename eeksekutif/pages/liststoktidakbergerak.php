<div class="block-header">
    <h2><center>DEAD STOK/STOK MATI/BARANG TIDAK BERGERAK</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <ul class="nav nav-tabs tab-nav-right" role="tablist">
                <?php
                    $periodeList = [1,3,6,9,12];
                    $no          = 0;
                    foreach($periodeList as $periode) {
                        $no++;
                        $activeTab = ($no==1) ? " class='active'" : "";
                        echo "<li role='presentation'".$activeTab."><a href='#periode".$periode."' data-toggle='tab'>".$periode." Bulan</a></li>";
                    }
                ?>
                </ul>
                <div class="tab-content">
                <?php
                    $no             = 0;
                    $chartDataSemua = [];
                    foreach($periodeList as $periode) {
                        $no++;
                        $activeClass = ($no==1) ? " in active" : "";
                        echo "<div role='tabpanel' class='tab-pane fade".$activeClass."' id='periode".$periode."'>";
                        echo "<div class='body'>
                                <div class='table-responsive'>
                                <table class='table table-bordered table-striped table-hover js-basic-example dataTable'>
                                    <thead>
                                        <tr>
                                            <th style='min-width:100px;white-space:nowrap;'><center>Kode Barang</center></th>
                                            <th style='min-width:220px;'><center>Nama Barang</center></th>
                                            <th style='min-width:90px;white-space:nowrap;'><center>Satuan</center></th>
                                            <th style='min-width:150px;white-space:nowrap;'><center>Jenis</center></th>
                                            <th style='min-width:120px;white-space:nowrap;'><center>Kategori</center></th>
                                            <th style='min-width:120px;white-space:nowrap;'><center>Golongan</center></th>
                                            <th style='min-width:90px;white-space:nowrap;'><center>Stok</center></th>
                                            <th style='min-width:100px;white-space:nowrap;'><center>Harga</center></th>
                                            <th style='min-width:130px;white-space:nowrap;'><center>Nilai Aset</center></th>
                                        </tr>
                                    </thead>
                                    <tbody>";
                        $totalAset      = 0;
                        $nilaiPerJenis    = [];
                        $nilaiPerKategori = [];
                        $nilaiPerGolongan = [];
                        $queryStokMati  = bukaquery(
                            "select databarang.kode_brng,databarang.nama_brng,kodesatuan.satuan,jenis.nama as nama_jenis,kategori_barang.nama as nama_kategori,golongan_barang.nama as nama_golongan,databarang.dasar as harga,IFNULL(stok.stoksaatini,0) as stoksaatini from databarang ".
                            "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat inner join jenis on databarang.kdjns=jenis.kdjns inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode ".
                            "left join (".
                                "select gudangbarang.kode_brng,sum(gudangbarang.stok) as stoksaatini from gudangbarang inner join bangsal on gudangbarang.kd_bangsal=bangsal.kd_bangsal where bangsal.status='1' and gudangbarang.no_batch='' and gudangbarang.no_faktur='' group by gudangbarang.kode_brng".
                            ") as stok on databarang.kode_brng=stok.kode_brng ".
                            "where databarang.status='1' and databarang.kode_brng not in (select riwayat_barang_medis.kode_brng from riwayat_barang_medis where riwayat_barang_medis.posisi<>'Opname' and riwayat_barang_medis.tanggal between SUBDATE(current_date(), INTERVAL ".$periode." MONTH) and current_date()) ".
                            "and IFNULL(stok.stoksaatini,0)>0 order by databarang.nama_brng asc"
                        );
                        while($rsqueryStokMati = mysqli_fetch_array($queryStokMati)) {
                            $stok       = (float) $rsqueryStokMati["stoksaatini"];
                            $nilaiAset  = $stok * $rsqueryStokMati["harga"];
                            $totalAset += $nilaiAset;
                            echo "<tr>
                                    <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["kode_brng"]."</td>
                                    <td align='left'>".$rsqueryStokMati["nama_brng"]."</td>
                                    <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["satuan"]."</td>
                                    <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["nama_jenis"]."</td>
                                    <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["nama_kategori"]."</td>
                                    <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["nama_golongan"]."</td>
                                    <td align='right' style='white-space:nowrap;'>".number_format($stok,1,',','.')."</td>
                                    <td align='right' style='white-space:nowrap;'>".number_format($rsqueryStokMati["harga"],0,',','.')."</td>
                                    <td align='right' style='white-space:nowrap;'>".number_format($nilaiAset,0,',','.')."</td>
                                  </tr>";
                            if(!isset($nilaiPerJenis[$rsqueryStokMati["nama_jenis"]])) {
                                $nilaiPerJenis[$rsqueryStokMati["nama_jenis"]] = 0;
                            }
                            $nilaiPerJenis[$rsqueryStokMati["nama_jenis"]] += $nilaiAset;
                            if(!isset($nilaiPerKategori[$rsqueryStokMati["nama_kategori"]])) {
                                $nilaiPerKategori[$rsqueryStokMati["nama_kategori"]] = 0;
                            }
                            $nilaiPerKategori[$rsqueryStokMati["nama_kategori"]] += $nilaiAset;
                            if(!isset($nilaiPerGolongan[$rsqueryStokMati["nama_golongan"]])) {
                                $nilaiPerGolongan[$rsqueryStokMati["nama_golongan"]] = 0;
                            }
                            $nilaiPerGolongan[$rsqueryStokMati["nama_golongan"]] += $nilaiAset;
                        }
                        echo "</tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan='8' style='text-align:right'>Total</th>
                                        <th style='text-align:right'>".number_format($totalAset,0,',','.')."</th>
                                    </tr>
                                </tfoot>
                            </table>
                                </div>
                              </div>";
                        arsort($nilaiPerJenis);
                        arsort($nilaiPerKategori);
                        arsort($nilaiPerGolongan);
                        $rekapGrup = [
                            'jenis'    => ['label'=>'Jenis',    'data'=>$nilaiPerJenis],
                            'kategori' => ['label'=>'Kategori', 'data'=>$nilaiPerKategori],
                            'golongan' => ['label'=>'Golongan', 'data'=>$nilaiPerGolongan]
                        ];
                        $dataPieSemua = [];
                        foreach($rekapGrup as $kunciGrup => $grup) {
                            echo "<hr style='margin:0 0 20px 0;'>
                            <div class='body' style='padding-top:0;'>
                                <div class='header bg-white' style='border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;'>
                                    <div class='text-center' style='font-size:16px;color:#777777;'>Nilai Aset Berdasarkan ".$grup["label"]."</div>
                                </div>
                                <div class='row clearfix'>
                                    <div class='col-md-6'>
                                        <div class='table-responsive'>
                                            <table class='table table-bordered table-striped table-hover js-basic-example dataTable'>
                                                <thead>
                                                    <tr>
                                                        <th width='70%'><center>".$grup["label"]."</center></th>
                                                        <th width='30%'><center>Nilai Aset</center></th>
                                                    </tr>
                                                </thead>
                                                <tbody>";
                            $dataPieGrup = [];
                            foreach($grup["data"] as $namaGrup => $nilaiGrup) {
                                echo "<tr>
                                        <td align='left'>".$namaGrup."</td>
                                        <td align='right'>".number_format($nilaiGrup,0,',','.')."</td>
                                      </tr>";
                                $dataPieGrup[] = [
                                    'label' => $namaGrup." (".number_format($nilaiGrup,0,',','.').")",
                                    'data'  => (float)$nilaiGrup
                                ];
                            }
                            echo "  </tbody>
                                                <tfoot>
                                                    <tr>
                                                        <th style='text-align:left;'>Jumlah Total</th>
                                                        <th style='text-align:right;'>".number_format($totalAset,0,',','.')."</th>
                                                    </tr>
                                                </tfoot>
                                            </table>
                                        </div>
                                    </div>
                                    <div class='col-md-6'>
                                        <div id='pie_chart_".$kunciGrup."_".$periode."' class='flot-chart' style='height:400px;'></div>
                                    </div>
                                </div>
                            </div>";
                            $dataPieSemua[$kunciGrup] = $dataPieGrup;
                        }
                        $chartDataSemua[$periode] = $dataPieSemua;
                        echo "</div>";
                    }
                ?>
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
    var chartDataSemua = <?= json_encode($chartDataSemua) ?>;
    Object.keys(chartDataSemua).forEach(function(periode) {
        ['jenis','kategori','golongan'].forEach(function(kunciGrup) {
            var dataPie = chartDataSemua[periode][kunciGrup];
            var chartId = '#pie_chart_' + kunciGrup + '_' + periode;
            if (dataPie.length > 0) {
                $.plot(chartId, dataPie, {
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
                $(chartId).html("<div class='text-center text-muted mt-5'>Kosong</div>");
            }
        });
    });
});
</script>