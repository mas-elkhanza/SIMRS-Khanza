<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncaribayar  = $thnsekarang;
    $blncaribayar  = $blnsekarang;
    $tglcaribayar  = $tglsekarang;
    $thncaribayar2 = $thnsekarang;
    $blncaribayar2 = $blnsekarang;
    $tglcaribayar2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncaribayar  = validTeks(trim(isset($_POST['tgl_cari_bayar']))?substr($_POST['tgl_cari_bayar'],6,4):$thnsekarang);
        $blncaribayar  = validTeks(trim(isset($_POST['tgl_cari_bayar']))?substr($_POST['tgl_cari_bayar'],3,2):$blnsekarang);
        $tglcaribayar  = validTeks(trim(isset($_POST['tgl_cari_bayar']))?substr($_POST['tgl_cari_bayar'],0,2):$tglsekarang);
        $thncaribayar2 = validTeks(trim(isset($_POST['tgl_cari_bayar2']))?substr($_POST['tgl_cari_bayar2'],6,4):$thnsekarang);
        $blncaribayar2 = validTeks(trim(isset($_POST['tgl_cari_bayar2']))?substr($_POST['tgl_cari_bayar2'],3,2):$blnsekarang);
        $tglcaribayar2 = validTeks(trim(isset($_POST['tgl_cari_bayar2']))?substr($_POST['tgl_cari_bayar2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>PEMBAYARAN PER AKUN BAYAR</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_bayar">Periode</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_bayar" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaribayar."-".$blncaribayar."-".$thncaribayar;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_bayar2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_bayar2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaribayar2."-".$blncaribayar2."-".$thncaribayar2;?>"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Tampilkan Data & Grafik</button></center>
                </form>
            </div>
            <div class="body" style="padding-top:0;">
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="70%"><center>Akun Bayar</center></th>
                                        <th width="30%"><center>Total</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $akunList = [];
                                    $queryAkun = bukaquery(
                                        "select rekening.kd_rek,rekening.nm_rek from rekening where ".
                                        "(rekening.kd_rek in (select akun_bayar.kd_rek from akun_bayar group by akun_bayar.kd_rek)) ".
                                        "or (rekening.kd_rek in (select kategori_pemasukan_lain.kd_rek2 from kategori_pemasukan_lain group by kategori_pemasukan_lain.kd_rek2)) ".
                                        "order by rekening.nm_rek asc"
                                    );
                                    while($rsakun = mysqli_fetch_array($queryAkun)) {
                                        $akunList[] = [
                                            'kd_rek' => $rsakun["kd_rek"],
                                            'nm_rek' => $rsakun["nm_rek"],
                                            'total'  => 0
                                        ];
                                    }

                                    $allTotal = 0;
                                    $queryTagihan = bukaquery(
                                        "select tagihan_sadewa.no_nota,tagihan_sadewa.tgl_bayar,tagihan_sadewa.jumlah_bayar from tagihan_sadewa ".
                                        "where tagihan_sadewa.tgl_bayar between '$thncaribayar-$blncaribayar-$tglcaribayar 00:00:00' and '$thncaribayar2-$blncaribayar2-$tglcaribayar2 23:59:59' ".
                                        "order by tagihan_sadewa.tgl_bayar,tagihan_sadewa.no_nota"
                                    );
                                    while($rstagihan = mysqli_fetch_array($queryTagihan)) {
                                        $norawatinap     = "";
                                        $norawatjalan    = "";
                                        $notajual        = "";
                                        $nodeposit       = "";
                                        $nopemasukanlain = "";
                                        $notakesling     = "";
                                        $tidakditemukan  = false;

                                        $nonota = getOne("select nota_inap.no_nota from nota_inap where nota_inap.no_rawat='".$rstagihan["no_nota"]."'");
                                        if($nonota!="" && $nonota!=null) {
                                            $norawatinap = $rstagihan["no_nota"];
                                        } else {
                                            $nonota = getOne("select nota_jalan.no_nota from nota_jalan where nota_jalan.no_rawat='".$rstagihan["no_nota"]."'");
                                            if($nonota!="" && $nonota!=null) {
                                                $norawatjalan = $rstagihan["no_nota"];
                                            } else {
                                                $nonota = getOne("select penjualan.nota_jual from penjualan where penjualan.nota_jual='".$rstagihan["no_nota"]."'");
                                                if($nonota!="" && $nonota!=null) {
                                                    $notajual = $rstagihan["no_nota"];
                                                } else {
                                                    $nonota = getOne("select deposit.no_deposit from deposit where deposit.no_deposit='".$rstagihan["no_nota"]."'");
                                                    if($nonota!="" && $nonota!=null) {
                                                        $nodeposit = $rstagihan["no_nota"];
                                                    } else {
                                                        $nonota = getOne("select pemasukan_lain.no_masuk from pemasukan_lain where pemasukan_lain.no_masuk='".$rstagihan["no_nota"]."'");
                                                        if($nonota!="" && $nonota!=null) {
                                                            $nopemasukanlain = $rstagihan["no_nota"];
                                                        } else {
                                                            $nonota = getOne("select labkesling_pembayaran_pengujian_sampel.no_pembayaran from labkesling_pembayaran_pengujian_sampel where labkesling_pembayaran_pengujian_sampel.no_pembayaran='".$rstagihan["no_nota"]."'");
                                                            if($nonota!="" && $nonota!=null) {
                                                                $notakesling = $rstagihan["no_nota"];
                                                            } else {
                                                                $tidakditemukan = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if(!$tidakditemukan) {
                                            $allTotal += $rstagihan["jumlah_bayar"];
                                            foreach($akunList as $idx => $akun) {
                                                $bayar = 0;
                                                if($norawatinap!="") {
                                                    $bayar = (float) getOne("select sum(detail_nota_inap.besar_bayar) from detail_nota_inap inner join akun_bayar on detail_nota_inap.nama_bayar=akun_bayar.nama_bayar where detail_nota_inap.no_rawat='".$norawatinap."' and akun_bayar.kd_rek='".$akun["kd_rek"]."'");
                                                } elseif($norawatjalan!="") {
                                                    $bayar = (float) getOne("select sum(detail_nota_jalan.besar_bayar) from detail_nota_jalan inner join akun_bayar on detail_nota_jalan.nama_bayar=akun_bayar.nama_bayar where detail_nota_jalan.no_rawat='".$norawatjalan."' and akun_bayar.kd_rek='".$akun["kd_rek"]."'");
                                                } elseif($notajual!="") {
                                                    $bayar = (float) getOne("select (sum(detailjual.total)+penjualan.ongkir+penjualan.ppn) from detailjual inner join penjualan on penjualan.nota_jual=detailjual.nota_jual inner join akun_bayar on penjualan.nama_bayar=akun_bayar.nama_bayar where penjualan.nota_jual='".$notajual."' and akun_bayar.kd_rek='".$akun["kd_rek"]."'");
                                                } elseif($nodeposit!="") {
                                                    $bayar = (float) getOne("select sum(deposit.besar_deposit) from deposit inner join akun_bayar on deposit.nama_bayar=akun_bayar.nama_bayar where deposit.no_deposit='".$nodeposit."' and akun_bayar.kd_rek='".$akun["kd_rek"]."'");
                                                } elseif($nopemasukanlain!="") {
                                                    $bayar = (float) getOne("select sum(pemasukan_lain.besar) from pemasukan_lain inner join kategori_pemasukan_lain on kategori_pemasukan_lain.kode_kategori=pemasukan_lain.kode_kategori where pemasukan_lain.no_masuk='".$nopemasukanlain."' and kategori_pemasukan_lain.kd_rek2='".$akun["kd_rek"]."'");
                                                } elseif($notakesling!="") {
                                                    $bayar = (float) getOne("select sum(labkesling_detail_pembayaran_pengujian_sampel.besar_bayar) from labkesling_detail_pembayaran_pengujian_sampel inner join akun_bayar on labkesling_detail_pembayaran_pengujian_sampel.nama_bayar=akun_bayar.nama_bayar where labkesling_detail_pembayaran_pengujian_sampel.no_pembayaran='".$notakesling."' and akun_bayar.kd_rek='".$akun["kd_rek"]."'");
                                                }
                                                $akunList[$idx]["total"] += $bayar;
                                            }
                                        }
                                    }

                                    $dataPieAkun = [];
                                    foreach($akunList as $akun) {
                                        if($akun["total"]>0) {
                                            echo "<tr>
                                                    <td align='left'>Total ".$akun["nm_rek"]."</td>
                                                    <td align='right'>".number_format($akun["total"],0,',','.')."</td>
                                                  </tr>";
                                            $dataPieAkun[] = [
                                                'label' => $akun["nm_rek"]." (".number_format($akun["total"],0,',','.').")",
                                                'data'  => (float)$akun["total"]
                                            ];
                                        }
                                    }
                                    if($allTotal>0) {
                                        echo "<tr>
                                                <td align='left'><b>Jumlah Total</b></td>
                                                <td align='right'><b>".number_format($allTotal,0,',','.')."</b></td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_akunbayar" class="flot-chart" style="height: 400px;"></div>
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
    var dataPieAkun = <?= json_encode($dataPieAkun) ?>;
    if (dataPieAkun.length > 0) {
        $.plot("#pie_chart_akunbayar", dataPieAkun, {
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
        $("#pie_chart_akunbayar").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>