<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncarihibah  = $thnsekarang;
    $blncarihibah  = $blnsekarang;
    $tglcarihibah  = $tglsekarang;
    $thncarihibah2 = $thnsekarang;
    $blncarihibah2 = $blnsekarang;
    $tglcarihibah2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncarihibah  = validTeks(trim(isset($_POST['tgl_cari_hibah']))?substr($_POST['tgl_cari_hibah'],6,4):$thnsekarang);
        $blncarihibah  = validTeks(trim(isset($_POST['tgl_cari_hibah']))?substr($_POST['tgl_cari_hibah'],3,2):$blnsekarang);
        $tglcarihibah  = validTeks(trim(isset($_POST['tgl_cari_hibah']))?substr($_POST['tgl_cari_hibah'],0,2):$tglsekarang);
        $thncarihibah2 = validTeks(trim(isset($_POST['tgl_cari_hibah2']))?substr($_POST['tgl_cari_hibah2'],6,4):$thnsekarang);
        $blncarihibah2 = validTeks(trim(isset($_POST['tgl_cari_hibah2']))?substr($_POST['tgl_cari_hibah2'],3,2):$blnsekarang);
        $tglcarihibah2 = validTeks(trim(isset($_POST['tgl_cari_hibah2']))?substr($_POST['tgl_cari_hibah2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>RINGKASAN HIBAH OBAT, ALKES & BHP MEDIS</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_hibah">Tanggal Hibah</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_hibah" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarihibah."-".$blncarihibah."-".$thncarihibah;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_hibah2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_hibah2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarihibah2."-".$blncarihibah2."-".$thncarihibah2;?>"/>
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
                            $totalTagihan       = 0;
                            $queryRingkasanHibah = bukaquery(
                                "select detailhibah_obat_bhp.kode_brng,databarang.nama_brng,kodesatuan.satuan,jenis.nama as namajenis,golongan_barang.nama as namagolongan,".
                                "kategori_barang.nama as namakategori,sum(detailhibah_obat_bhp.jumlah2) as jumlah,sum(detailhibah_obat_bhp.subtotaldiakui) as total from hibah_obat_bhp ".
                                "inner join detailhibah_obat_bhp on hibah_obat_bhp.no_hibah=detailhibah_obat_bhp.no_hibah inner join databarang on detailhibah_obat_bhp.kode_brng=databarang.kode_brng ".
                                "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat inner join jenis on databarang.kdjns=jenis.kdjns ".
                                "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode ".
                                "where hibah_obat_bhp.tgl_hibah between '$thncarihibah-$blncarihibah-$tglcarihibah' and '$thncarihibah2-$blncarihibah2-$tglcarihibah2' ".
                                "group by detailhibah_obat_bhp.kode_brng order by databarang.nama_brng asc"
                            );
                            while($rsqueryRingkasanHibah = mysqli_fetch_array($queryRingkasanHibah)) {
                                $totalTagihan += $rsqueryRingkasanHibah["total"];
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanHibah["kode_brng"]."</td>
                                        <td align='left'>".$rsqueryRingkasanHibah["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanHibah["satuan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanHibah["namajenis"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanHibah["namagolongan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanHibah["namakategori"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryRingkasanHibah["jumlah"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryRingkasanHibah["total"],0,',','.')."</td>
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
                                    $no                    = 1;
                                    $dataTerbanyakDihibah  = [];
                                    $queryterbanyakdihibah = bukaquery(
                                        "select databarang.nama_brng,sum(detailhibah_obat_bhp.jumlah2) as jumlah from hibah_obat_bhp inner join detailhibah_obat_bhp on hibah_obat_bhp.no_hibah=detailhibah_obat_bhp.no_hibah inner join databarang on detailhibah_obat_bhp.kode_brng=databarang.kode_brng ".
                                        "where hibah_obat_bhp.tgl_hibah between '$thncarihibah-$blncarihibah-$tglcarihibah' and '$thncarihibah2-$blncarihibah2-$tglcarihibah2' group by detailhibah_obat_bhp.kode_brng order by jumlah desc limit 10"
                                    );
                                    while($rsqueryterbanyakdihibah = mysqli_fetch_array($queryterbanyakdihibah)) {
                                        $dataTerbanyakDihibah[] = [
                                            'label' => $rsqueryterbanyakdihibah["nama_brng"]." (".number_format($rsqueryterbanyakdihibah["jumlah"],0,',','.').")",
                                            'data'  => (float)$rsqueryterbanyakdihibah["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsqueryterbanyakdihibah["nama_brng"]."</td>
                                                <td align='right'>".number_format($rsqueryterbanyakdihibah["jumlah"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_terbanyakdihibah" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">10 Besar Nilai Hibah</div>
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
                                    $no                = 1;
                                    $dataNilaiHibah    = [];
                                    $querynilaihibah   = bukaquery(
                                        "select databarang.nama_brng,sum(detailhibah_obat_bhp.subtotaldiakui) as total from hibah_obat_bhp inner join detailhibah_obat_bhp on hibah_obat_bhp.no_hibah=detailhibah_obat_bhp.no_hibah inner join databarang on detailhibah_obat_bhp.kode_brng=databarang.kode_brng ".
                                        "where hibah_obat_bhp.tgl_hibah between '$thncarihibah-$blncarihibah-$tglcarihibah' and '$thncarihibah2-$blncarihibah2-$tglcarihibah2' group by detailhibah_obat_bhp.kode_brng order by total desc limit 10"
                                    );
                                    while($rsquerynilaihibah = mysqli_fetch_array($querynilaihibah)) {
                                        $dataNilaiHibah[] = [
                                            'label' => $rsquerynilaihibah["nama_brng"]." (".number_format($rsquerynilaihibah["total"],0,',','.').")",
                                            'data'  => (float)$rsquerynilaihibah["total"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerynilaihibah["nama_brng"]."</td>
                                                <td align='right'>".number_format($rsquerynilaihibah["total"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_nilaihibah" class="flot-chart" style="height: 400px;"></div>
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
    var dataTerbanyakDihibah = <?= json_encode($dataTerbanyakDihibah) ?>;
    if (dataTerbanyakDihibah.length > 0) {
        $.plot("#pie_chart_terbanyakdihibah", dataTerbanyakDihibah, {
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
        $("#pie_chart_terbanyakdihibah").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataNilaiHibah = <?= json_encode($dataNilaiHibah) ?>;
    if (dataNilaiHibah.length > 0) {
        $.plot("#pie_chart_nilaihibah", dataNilaiHibah, {
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
        $("#pie_chart_nilaihibah").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>