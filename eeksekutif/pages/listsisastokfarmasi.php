<div class="block-header">
    <h2><center>SISA STOK & NILAI ASET FARMASI</center></h2>
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
                                <th style="min-width:120px;white-space:nowrap;"><center>Jenis</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Kategori</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Golongan</center></th>
                                <th style="min-width:80px;white-space:nowrap;"><center>Satuan</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>Harga Dasar</center></th>
                                <?php
                                    $bangsalList  = [];
                                    $bangsalNama  = [];
                                    $querybangsal = bukaquery("select bangsal.kd_bangsal,bangsal.nm_bangsal from bangsal where bangsal.status='1' and bangsal.kd_bangsal<>'-' order by bangsal.kd_bangsal asc");
                                    while($rsquerybangsal = mysqli_fetch_array($querybangsal)) {
                                        $bangsalList[] = $rsquerybangsal["kd_bangsal"];
                                        $bangsalNama[$rsquerybangsal["kd_bangsal"]] = $rsquerybangsal["nm_bangsal"];
                                        echo "<th style='min-width:110px;white-space:nowrap;'><center>".$rsquerybangsal["nm_bangsal"]."</center></th>";
                                    }
                                ?>
                                <th style="min-width:90px;white-space:nowrap;"><center>Total</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Nilai Aset</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $sqlkolomstok = "";
                            foreach($bangsalList as $kodebangsal) {
                                $sqlkolomstok .= ",sum(case when g.kd_bangsal='".$kodebangsal."' then g.total_stok else 0 end) as stok_".$kodebangsal;
                            }
                            $no        = 1;
                            $totalAset    = 0;
                            $jenisAset    = [];
                            $kategoriAset = [];
                            $golonganAset = [];
                            $depoAset     = [];
                            $queryStok = bukaquery(
                                "select databarang.kode_brng,databarang.nama_brng,databarang.kode_sat,databarang.dasar,jenis.nama as nama_jenis,kategori_barang.nama as nama_kategori,golongan_barang.nama as nama_golongan".$sqlkolomstok." from databarang ".
                                "inner join jenis on databarang.kdjns=jenis.kdjns inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode ".
                                "left join (select gudangbarang.kode_brng,gudangbarang.kd_bangsal,sum(gudangbarang.stok) as total_stok from gudangbarang where gudangbarang.no_batch='' and gudangbarang.no_faktur='' group by gudangbarang.kode_brng,gudangbarang.kd_bangsal) g on g.kode_brng=databarang.kode_brng ".
                                "where databarang.status='1' group by databarang.kode_brng,jenis.nama,kategori_barang.nama,golongan_barang.nama order by databarang.kode_brng asc"
                            );
                            while($rsqueryStok = mysqli_fetch_array($queryStok)) {
                                $totalBarang = 0;
                                echo "<tr>
                                        <td align='center' style='white-space:nowrap;'>".$no++."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["kode_brng"]."</td>
                                        <td align='left'>".$rsqueryStok["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["nama_jenis"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["nama_kategori"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["nama_golongan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["kode_sat"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryStok["dasar"],0,',','.')."</td>";
                                foreach($bangsalList as $kodebangsal) {
                                    $stokbangsal  = (int)$rsqueryStok["stok_".$kodebangsal];
                                    $totalBarang += $stokbangsal;
                                    if(!isset($depoAset[$kodebangsal])) {
                                        $depoAset[$kodebangsal] = 0;
                                    }
                                    $depoAset[$kodebangsal] += $rsqueryStok["dasar"] * $stokbangsal;
                                    echo "<td align='right' style='white-space:nowrap;'>".number_format($stokbangsal,0,',','.')."</td>";
                                }
                                $nilaiAsetBarang  = $rsqueryStok["dasar"] * $totalBarang;
                                $totalAset       += $nilaiAsetBarang;
                                if(!isset($jenisAset[$rsqueryStok["nama_jenis"]])) {
                                    $jenisAset[$rsqueryStok["nama_jenis"]] = 0;
                                }
                                $jenisAset[$rsqueryStok["nama_jenis"]] += $nilaiAsetBarang;
                                if(!isset($kategoriAset[$rsqueryStok["nama_kategori"]])) {
                                    $kategoriAset[$rsqueryStok["nama_kategori"]] = 0;
                                }
                                $kategoriAset[$rsqueryStok["nama_kategori"]] += $nilaiAsetBarang;
                                if(!isset($golonganAset[$rsqueryStok["nama_golongan"]])) {
                                    $golonganAset[$rsqueryStok["nama_golongan"]] = 0;
                                }
                                $golonganAset[$rsqueryStok["nama_golongan"]] += $nilaiAsetBarang;
                                echo "<td align='right' style='white-space:nowrap;'>".number_format($totalBarang,0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($nilaiAsetBarang,0,',','.')."</td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="<?=(8+count($bangsalList)+1);?>" style="text-align:right">Total</th>
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
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Nilai Aset Per Kategori</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="10%"><center>No</center></th>
                                        <th width="60%"><center>Kategori</center></th>
                                        <th width="30%"><center>Nilai Aset</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    arsort($kategoriAset);
                                    $noKategori      = 1;
                                    $dataPieKategori = [];
                                    foreach($kategoriAset as $namaKategori => $nilaiKategori) {
                                        if($nilaiKategori>0) {
                                            echo "<tr>
                                                    <td align='center'>".$noKategori++."</td>
                                                    <td align='left'>".$namaKategori."</td>
                                                    <td align='right'>".number_format($nilaiKategori,0,',','.')."</td>
                                                  </tr>";
                                            $dataPieKategori[] = [
                                                'label' => $namaKategori." (".number_format($nilaiKategori,0,',','.').")",
                                                'data'  => (float)$nilaiKategori
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
                        <div id="pie_chart_asetkategori" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Nilai Aset Per Golongan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="10%"><center>No</center></th>
                                        <th width="60%"><center>Golongan</center></th>
                                        <th width="30%"><center>Nilai Aset</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    arsort($golonganAset);
                                    $noGolongan      = 1;
                                    $dataPieGolongan = [];
                                    foreach($golonganAset as $namaGolongan => $nilaiGolongan) {
                                        if($nilaiGolongan>0) {
                                            echo "<tr>
                                                    <td align='center'>".$noGolongan++."</td>
                                                    <td align='left'>".$namaGolongan."</td>
                                                    <td align='right'>".number_format($nilaiGolongan,0,',','.')."</td>
                                                  </tr>";
                                            $dataPieGolongan[] = [
                                                'label' => $namaGolongan." (".number_format($nilaiGolongan,0,',','.').")",
                                                'data'  => (float)$nilaiGolongan
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
                        <div id="pie_chart_asetgolongan" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Nilai Aset Per Lokasi Depo</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="10%"><center>No</center></th>
                                        <th width="60%"><center>Lokasi Depo</center></th>
                                        <th width="30%"><center>Nilai Aset</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    arsort($depoAset);
                                    $noDepo      = 1;
                                    $dataPieDepo = [];
                                    foreach($depoAset as $kodeDepo => $nilaiDepo) {
                                        if($nilaiDepo>0) {
                                            echo "<tr>
                                                    <td align='center'>".$noDepo++."</td>
                                                    <td align='left'>".$bangsalNama[$kodeDepo]."</td>
                                                    <td align='right'>".number_format($nilaiDepo,0,',','.')."</td>
                                                  </tr>";
                                            $dataPieDepo[] = [
                                                'label' => $bangsalNama[$kodeDepo]." (".number_format($nilaiDepo,0,',','.').")",
                                                'data'  => (float)$nilaiDepo
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
                        <div id="pie_chart_asetdepo" class="flot-chart" style="height: 400px;"></div>
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

    var dataPieKategori = <?= json_encode($dataPieKategori) ?>;
    if (dataPieKategori.length > 0) {
        $.plot("#pie_chart_asetkategori", dataPieKategori, {
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
        $("#pie_chart_asetkategori").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }

    var dataPieGolongan = <?= json_encode($dataPieGolongan) ?>;
    if (dataPieGolongan.length > 0) {
        $.plot("#pie_chart_asetgolongan", dataPieGolongan, {
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
        $("#pie_chart_asetgolongan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }

    var dataPieDepo = <?= json_encode($dataPieDepo) ?>;
    if (dataPieDepo.length > 0) {
        $.plot("#pie_chart_asetdepo", dataPieDepo, {
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
        $("#pie_chart_asetdepo").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>