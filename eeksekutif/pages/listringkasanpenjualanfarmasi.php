<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncarijual  = $thnsekarang;
    $blncarijual  = $blnsekarang;
    $tglcarijual  = $tglsekarang;
    $thncarijual2 = $thnsekarang;
    $blncarijual2 = $blnsekarang;
    $tglcarijual2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncarijual  = validTeks(trim(isset($_POST['tgl_cari_jual']))?substr($_POST['tgl_cari_jual'],6,4):$thnsekarang);
        $blncarijual  = validTeks(trim(isset($_POST['tgl_cari_jual']))?substr($_POST['tgl_cari_jual'],3,2):$blnsekarang);
        $tglcarijual  = validTeks(trim(isset($_POST['tgl_cari_jual']))?substr($_POST['tgl_cari_jual'],0,2):$tglsekarang);
        $thncarijual2 = validTeks(trim(isset($_POST['tgl_cari_jual2']))?substr($_POST['tgl_cari_jual2'],6,4):$thnsekarang);
        $blncarijual2 = validTeks(trim(isset($_POST['tgl_cari_jual2']))?substr($_POST['tgl_cari_jual2'],3,2):$blnsekarang);
        $tglcarijual2 = validTeks(trim(isset($_POST['tgl_cari_jual2']))?substr($_POST['tgl_cari_jual2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>RINGKASAN PENJUALAN BEBAS OBAT, ALKES & BHP MEDIS</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_jual">Tanggal</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_jual" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarijual."-".$blncarijual."-".$thncarijual;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_jual2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_jual2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarijual2."-".$blncarijual2."-".$thncarijual2;?>"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Tampilkan Data</button></center>
                </form>
            </div>
            <div class="body" style="padding-top:0;">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:100px;white-space:nowrap;"><center>Kode Barang</center></th>
                                <th style="min-width:220px;"><center>Nama Barang</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>Satuan</center></th>
                                <th style="min-width:150px;white-space:nowrap;"><center>Jenis</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Golongan</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Kategori</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>Jumlah</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $totalTagihan        = 0;
                            $queryRingkasanJual  = bukaquery(
                                "select detailjual.kode_brng,databarang.nama_brng,kodesatuan.satuan,jenis.nama as namajenis,golongan_barang.nama as namagolongan,".
                                "kategori_barang.nama as namakategori,sum(detailjual.jumlah) as jumlah,sum(detailjual.total) as total from penjualan ".
                                "inner join detailjual on penjualan.nota_jual=detailjual.nota_jual inner join databarang on detailjual.kode_brng=databarang.kode_brng ".
                                "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat inner join jenis on databarang.kdjns=jenis.kdjns ".
                                "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode ".
                                "where penjualan.status='Sudah Dibayar' and penjualan.tgl_jual between '$thncarijual-$blncarijual-$tglcarijual' and '$thncarijual2-$blncarijual2-$tglcarijual2' ".
                                "group by detailjual.kode_brng order by databarang.nama_brng asc"
                            );
                            while($rsqueryRingkasanJual = mysqli_fetch_array($queryRingkasanJual)) {
                                $totalTagihan += $rsqueryRingkasanJual["total"];
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanJual["kode_brng"]."</td>
                                        <td align='left'>".$rsqueryRingkasanJual["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanJual["satuan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanJual["namajenis"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanJual["namagolongan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanJual["namakategori"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryRingkasanJual["jumlah"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryRingkasanJual["total"],0,',','.')."</td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="7" style="text-align:right">Total</th>
                                <th style="text-align:right"><?=number_format($totalTagihan,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">10 Besar Barang Terjual</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Nama Barang</center></th>
                                        <th width="25%"><center>Jumlah</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                    = 1;
                                    $dataTerbanyakTerjual  = [];
                                    $queryterbanyakterjual = bukaquery(
                                        "select databarang.nama_brng,sum(detailjual.jumlah) as jumlah from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual inner join databarang on detailjual.kode_brng=databarang.kode_brng ".
                                        "where penjualan.status='Sudah Dibayar' and penjualan.tgl_jual between '$thncarijual-$blncarijual-$tglcarijual' and '$thncarijual2-$blncarijual2-$tglcarijual2' group by detailjual.kode_brng order by jumlah desc limit 10"
                                    );
                                    while($rsqueryterbanyakterjual = mysqli_fetch_array($queryterbanyakterjual)) {
                                        $dataTerbanyakTerjual[] = [
                                            'label' => $rsqueryterbanyakterjual["nama_brng"]." (".number_format($rsqueryterbanyakterjual["jumlah"],0,',','.').")",
                                            'data'  => (float)$rsqueryterbanyakterjual["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsqueryterbanyakterjual["nama_brng"]."</td>
                                                <td align='right'>".number_format($rsqueryterbanyakterjual["jumlah"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_terbanyakterjual" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">10 Besar Nilai Penjualan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Nama Barang</center></th>
                                        <th width="25%"><center>Total</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no                  = 1;
                                    $dataNilaiPenjualan  = [];
                                    $querynilaipenjualan = bukaquery(
                                        "select databarang.nama_brng,sum(detailjual.total) as total from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual inner join databarang on detailjual.kode_brng=databarang.kode_brng ".
                                        "where penjualan.status='Sudah Dibayar' and penjualan.tgl_jual between '$thncarijual-$blncarijual-$tglcarijual' and '$thncarijual2-$blncarijual2-$tglcarijual2' group by detailjual.kode_brng order by total desc limit 10"
                                    );
                                    while($rsquerynilaipenjualan = mysqli_fetch_array($querynilaipenjualan)) {
                                        $dataNilaiPenjualan[] = [
                                            'label' => $rsquerynilaipenjualan["nama_brng"]." (".number_format($rsquerynilaipenjualan["total"],0,',','.').")",
                                            'data'  => (float)$rsquerynilaipenjualan["total"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerynilaipenjualan["nama_brng"]."</td>
                                                <td align='right'>".number_format($rsquerynilaipenjualan["total"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_nilaipenjualan" class="flot-chart" style="height: 400px;"></div>
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
    var dataTerbanyakTerjual = <?= json_encode($dataTerbanyakTerjual) ?>;
    if (dataTerbanyakTerjual.length > 0) {
        $.plot("#pie_chart_terbanyakterjual", dataTerbanyakTerjual, {
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
        $("#pie_chart_terbanyakterjual").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataNilaiPenjualan = <?= json_encode($dataNilaiPenjualan) ?>;
    if (dataNilaiPenjualan.length > 0) {
        $.plot("#pie_chart_nilaipenjualan", dataNilaiPenjualan, {
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
        $("#pie_chart_nilaipenjualan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>