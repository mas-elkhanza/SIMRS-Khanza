<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncariretur  = $thnsekarang;
    $blncariretur  = $blnsekarang;
    $tglcariretur  = $tglsekarang;
    $thncariretur2 = $thnsekarang;
    $blncariretur2 = $blnsekarang;
    $tglcariretur2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncariretur  = validTeks(trim(isset($_POST['tgl_cari_retur']))?substr($_POST['tgl_cari_retur'],6,4):$thnsekarang);
        $blncariretur  = validTeks(trim(isset($_POST['tgl_cari_retur']))?substr($_POST['tgl_cari_retur'],3,2):$blnsekarang);
        $tglcariretur  = validTeks(trim(isset($_POST['tgl_cari_retur']))?substr($_POST['tgl_cari_retur'],0,2):$tglsekarang);
        $thncariretur2 = validTeks(trim(isset($_POST['tgl_cari_retur2']))?substr($_POST['tgl_cari_retur2'],6,4):$thnsekarang);
        $blncariretur2 = validTeks(trim(isset($_POST['tgl_cari_retur2']))?substr($_POST['tgl_cari_retur2'],3,2):$blnsekarang);
        $tglcariretur2 = validTeks(trim(isset($_POST['tgl_cari_retur2']))?substr($_POST['tgl_cari_retur2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>RINGKASAN RETUR KE SUPLIER OBAT, ALKES & BHP MEDIS</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_retur">Tanggal Retur</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_retur" class="datepicker form-control" required autocomplete="off" value="<?=$tglcariretur."-".$blncariretur."-".$thncariretur;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_retur2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_retur2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcariretur2."-".$blncariretur2."-".$thncariretur2;?>"/>
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
                            $totalTagihan          = 0;
                            $queryRingkasanRetur    = bukaquery(
                                "select detreturbeli.kode_brng,databarang.nama_brng,kodesatuan.satuan,jenis.nama as namajenis,golongan_barang.nama as namagolongan,".
                                "kategori_barang.nama as namakategori,sum(detreturbeli.jml_retur2) as jumlah,sum(detreturbeli.total) as total from returbeli ".
                                "inner join detreturbeli on returbeli.no_retur_beli=detreturbeli.no_retur_beli inner join databarang on detreturbeli.kode_brng=databarang.kode_brng ".
                                "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat inner join jenis on databarang.kdjns=jenis.kdjns ".
                                "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode ".
                                "where returbeli.tgl_retur between '$thncariretur-$blncariretur-$tglcariretur' and '$thncariretur2-$blncariretur2-$tglcariretur2' ".
                                "group by detreturbeli.kode_brng order by databarang.nama_brng asc"
                            );
                            while($rsqueryRingkasanRetur = mysqli_fetch_array($queryRingkasanRetur)) {
                                $totalTagihan += $rsqueryRingkasanRetur["total"];
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanRetur["kode_brng"]."</td>
                                        <td align='left'>".$rsqueryRingkasanRetur["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanRetur["satuan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanRetur["namajenis"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanRetur["namagolongan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanRetur["namakategori"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryRingkasanRetur["jumlah"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryRingkasanRetur["total"],0,',','.')."</td>
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
                    <div class="text-center" style="font-size:16px;color:#777777;">10 Besar Jumlah</div>
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
                                    $no                   = 1;
                                    $dataTerbanyakRetur   = [];
                                    $queryterbanyakretur  = bukaquery(
                                        "select databarang.nama_brng,sum(detreturbeli.jml_retur2) as jumlah from returbeli inner join detreturbeli on returbeli.no_retur_beli=detreturbeli.no_retur_beli inner join databarang on detreturbeli.kode_brng=databarang.kode_brng ".
                                        "where returbeli.tgl_retur between '$thncariretur-$blncariretur-$tglcariretur' and '$thncariretur2-$blncariretur2-$tglcariretur2' group by detreturbeli.kode_brng order by jumlah desc limit 10"
                                    );
                                    while($rsqueryterbanyakretur = mysqli_fetch_array($queryterbanyakretur)) {
                                        $dataTerbanyakRetur[] = [
                                            'label' => $rsqueryterbanyakretur["nama_brng"]." (".number_format($rsqueryterbanyakretur["jumlah"],0,',','.').")",
                                            'data'  => (float)$rsqueryterbanyakretur["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsqueryterbanyakretur["nama_brng"]."</td>
                                                <td align='right'>".number_format($rsqueryterbanyakretur["jumlah"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_terbanyakretur" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">10 Besar Nilai</div>
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
                                    $no              = 1;
                                    $dataNilaiRetur  = [];
                                    $querynilairetur = bukaquery(
                                        "select databarang.nama_brng,sum(detreturbeli.total) as total from returbeli inner join detreturbeli on returbeli.no_retur_beli=detreturbeli.no_retur_beli inner join databarang on detreturbeli.kode_brng=databarang.kode_brng ".
                                        "where returbeli.tgl_retur between '$thncariretur-$blncariretur-$tglcariretur' and '$thncariretur2-$blncariretur2-$tglcariretur2' group by detreturbeli.kode_brng order by total desc limit 10"
                                    );
                                    while($rsquerynilairetur = mysqli_fetch_array($querynilairetur)) {
                                        $dataNilaiRetur[] = [
                                            'label' => $rsquerynilairetur["nama_brng"]." (".number_format($rsquerynilairetur["total"],0,',','.').")",
                                            'data'  => (float)$rsquerynilairetur["total"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerynilairetur["nama_brng"]."</td>
                                                <td align='right'>".number_format($rsquerynilairetur["total"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_nilairetur" class="flot-chart" style="height: 400px;"></div>
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
    var dataTerbanyakRetur = <?= json_encode($dataTerbanyakRetur) ?>;
    if (dataTerbanyakRetur.length > 0) {
        $.plot("#pie_chart_terbanyakretur", dataTerbanyakRetur, {
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
        $("#pie_chart_terbanyakretur").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataNilaiRetur = <?= json_encode($dataNilaiRetur) ?>;
    if (dataNilaiRetur.length > 0) {
        $.plot("#pie_chart_nilairetur", dataNilaiRetur, {
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
        $("#pie_chart_nilairetur").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>