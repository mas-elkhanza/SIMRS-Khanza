<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncaripesan  = $thnsekarang;
    $blncaripesan  = $blnsekarang;
    $tglcaripesan  = $tglsekarang;
    $thncaripesan2 = $thnsekarang;
    $blncaripesan2 = $blnsekarang;
    $tglcaripesan2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncaripesan  = validTeks(trim(isset($_POST['tgl_cari_pesan']))?substr($_POST['tgl_cari_pesan'],6,4):$thnsekarang);
        $blncaripesan  = validTeks(trim(isset($_POST['tgl_cari_pesan']))?substr($_POST['tgl_cari_pesan'],3,2):$blnsekarang);
        $tglcaripesan  = validTeks(trim(isset($_POST['tgl_cari_pesan']))?substr($_POST['tgl_cari_pesan'],0,2):$tglsekarang);
        $thncaripesan2 = validTeks(trim(isset($_POST['tgl_cari_pesan2']))?substr($_POST['tgl_cari_pesan2'],6,4):$thnsekarang);
        $blncaripesan2 = validTeks(trim(isset($_POST['tgl_cari_pesan2']))?substr($_POST['tgl_cari_pesan2'],3,2):$blnsekarang);
        $tglcaripesan2 = validTeks(trim(isset($_POST['tgl_cari_pesan2']))?substr($_POST['tgl_cari_pesan2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>RINGKASAN PENERIMAAN OBAT, ALKES & BHP MEDIS</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_pesan">Tanggal Pesan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_pesan" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripesan."-".$blncaripesan."-".$thncaripesan;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_pesan2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_pesan2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripesan2."-".$blncaripesan2."-".$thncaripesan2;?>"/>
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
                                <th style="min-width:90px;white-space:nowrap;"><center>Jumlah</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $totalTagihan          = 0;
                            $queryRingkasanPesan    = bukaquery(
                                "select detailpesan.kode_brng,databarang.nama_brng,kodesatuan.satuan,jenis.nama as namajenis,sum(detailpesan.jumlah2) as jumlah,sum(detailpesan.total) as total from pemesanan ".
                                "inner join detailpesan on pemesanan.no_faktur=detailpesan.no_faktur inner join databarang on detailpesan.kode_brng=databarang.kode_brng ".
                                "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat inner join jenis on databarang.kdjns=jenis.kdjns ".
                                "where pemesanan.tgl_pesan between '$thncaripesan-$blncaripesan-$tglcaripesan' and '$thncaripesan2-$blncaripesan2-$tglcaripesan2' ".
                                "group by detailpesan.kode_brng order by databarang.nama_brng asc"
                            );
                            while($rsqueryRingkasanPesan = mysqli_fetch_array($queryRingkasanPesan)) {
                                $totalTagihan += $rsqueryRingkasanPesan["total"];
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanPesan["kode_brng"]."</td>
                                        <td align='left'>".$rsqueryRingkasanPesan["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanPesan["satuan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryRingkasanPesan["namajenis"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryRingkasanPesan["jumlah"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryRingkasanPesan["total"],0,',','.')."</td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="5" style="text-align:right">Total</th>
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
                                    $no                     = 1;
                                    $dataTerbanyakDiterima  = [];
                                    $queryterbanyakditerima = bukaquery(
                                        "select databarang.nama_brng,sum(detailpesan.jumlah2) as jumlah from pemesanan inner join detailpesan on pemesanan.no_faktur=detailpesan.no_faktur inner join databarang on detailpesan.kode_brng=databarang.kode_brng ".
                                        "where pemesanan.tgl_pesan between '$thncaripesan-$blncaripesan-$tglcaripesan' and '$thncaripesan2-$blncaripesan2-$tglcaripesan2' group by detailpesan.kode_brng order by jumlah desc limit 10"
                                    );
                                    while($rsqueryterbanyakditerima = mysqli_fetch_array($queryterbanyakditerima)) {
                                        $dataTerbanyakDiterima[] = [
                                            'label' => $rsqueryterbanyakditerima["nama_brng"]." (".number_format($rsqueryterbanyakditerima["jumlah"],0,',','.').")",
                                            'data'  => (float)$rsqueryterbanyakditerima["jumlah"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsqueryterbanyakditerima["nama_brng"]."</td>
                                                <td align='right'>".number_format($rsqueryterbanyakditerima["jumlah"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_terbanyakditerima" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">10 Besar Biaya</div>
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
                                    $no                   = 1;
                                    $dataBiayaPenerimaan  = [];
                                    $querybiayapenerimaan = bukaquery(
                                        "select databarang.nama_brng,sum(detailpesan.total) as total from pemesanan inner join detailpesan on pemesanan.no_faktur=detailpesan.no_faktur inner join databarang on detailpesan.kode_brng=databarang.kode_brng ".
                                        "where pemesanan.tgl_pesan between '$thncaripesan-$blncaripesan-$tglcaripesan' and '$thncaripesan2-$blncaripesan2-$tglcaripesan2' group by detailpesan.kode_brng order by total desc limit 10"
                                    );
                                    while($rsquerybiayapenerimaan = mysqli_fetch_array($querybiayapenerimaan)) {
                                        $dataBiayaPenerimaan[] = [
                                            'label' => $rsquerybiayapenerimaan["nama_brng"]." (".number_format($rsquerybiayapenerimaan["total"],0,',','.').")",
                                            'data'  => (float)$rsquerybiayapenerimaan["total"]
                                        ];
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$rsquerybiayapenerimaan["nama_brng"]."</td>
                                                <td align='right'>".number_format($rsquerybiayapenerimaan["total"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_biayapenerimaan" class="flot-chart" style="height: 400px;"></div>
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
    var dataTerbanyakDiterima = <?= json_encode($dataTerbanyakDiterima) ?>;
    if (dataTerbanyakDiterima.length > 0) {
        $.plot("#pie_chart_terbanyakditerima", dataTerbanyakDiterima, {
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
        $("#pie_chart_terbanyakditerima").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBiayaPenerimaan = <?= json_encode($dataBiayaPenerimaan) ?>;
    if (dataBiayaPenerimaan.length > 0) {
        $.plot("#pie_chart_biayapenerimaan", dataBiayaPenerimaan, {
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
        $("#pie_chart_biayapenerimaan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>