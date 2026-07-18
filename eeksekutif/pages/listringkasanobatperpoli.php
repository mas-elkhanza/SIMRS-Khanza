<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncaripoli  = $thnsekarang;
    $blncaripoli  = $blnsekarang;
    $tglcaripoli  = $tglsekarang;
    $thncaripoli2 = $thnsekarang;
    $blncaripoli2 = $blnsekarang;
    $tglcaripoli2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncaripoli  = validTeks(trim(isset($_POST['tgl_cari_poli']))?substr($_POST['tgl_cari_poli'],6,4):$thnsekarang);
        $blncaripoli  = validTeks(trim(isset($_POST['tgl_cari_poli']))?substr($_POST['tgl_cari_poli'],3,2):$blnsekarang);
        $tglcaripoli  = validTeks(trim(isset($_POST['tgl_cari_poli']))?substr($_POST['tgl_cari_poli'],0,2):$tglsekarang);
        $thncaripoli2 = validTeks(trim(isset($_POST['tgl_cari_poli2']))?substr($_POST['tgl_cari_poli2'],6,4):$thnsekarang);
        $blncaripoli2 = validTeks(trim(isset($_POST['tgl_cari_poli2']))?substr($_POST['tgl_cari_poli2'],3,2):$blnsekarang);
        $tglcaripoli2 = validTeks(trim(isset($_POST['tgl_cari_poli2']))?substr($_POST['tgl_cari_poli2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>RINGKASAN PENGGUNAAN OBAT PER POLI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_poli">Tgl.Beri Obat</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_poli" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripoli."-".$blncaripoli."-".$thncaripoli;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_poli2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_poli2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripoli2."-".$blncaripoli2."-".$thncaripoli2;?>"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Tampilkan Data & Grafik</button></center>
                </form>
            </div>
            <div class="body" style="padding-top:0;">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th style="min-width:50px;white-space:nowrap;"><center>No.</center></th>
                                <th style="min-width:150px;white-space:nowrap;"><center>Poliklinik</center></th>
                                <th style="min-width:80px;white-space:nowrap;"><center>Jml</center></th>
                                <th style="min-width:220px;"><center>Nama Obat</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>Biaya Obat</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Embalase</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>Tuslah</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $i                  = 1;
                            $totalBiaya         = 0;
                            $totalEmbalase      = 0;
                            $totalTuslah        = 0;
                            $totalTotal         = 0;
                            $dataBiayaPerPoli   = [];
                            $tablePerPoli       = [];
                            $queryPoli = bukaquery("select poliklinik.kd_poli,poliklinik.nm_poli from poliklinik order by poliklinik.nm_poli asc");
                            while($rspoli = mysqli_fetch_array($queryPoli)) {
                                $queryObat = bukaquery(
                                    "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,".
                                    "sum(detail_pemberian_obat.embalase) as embalase,sum(detail_pemberian_obat.tuslah) as tuslah,sum(detail_pemberian_obat.total) as total ".
                                    "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng ".
                                    "where detail_pemberian_obat.status='Ralan' and reg_periksa.tgl_registrasi between '$thncaripoli-$blncaripoli-$tglcaripoli' and '$thncaripoli2-$blncaripoli2-$tglcaripoli2' ".
                                    "and reg_periksa.kd_poli='".$rspoli["kd_poli"]."' group by detail_pemberian_obat.kode_brng order by databarang.nama_brng asc"
                                );
                                $a            = 0;
                                $subBiaya     = 0;
                                $subEmbalase  = 0;
                                $subTuslah    = 0;
                                $subTotal     = 0;
                                $rowsHtml     = "";
                                while($rsobat = mysqli_fetch_array($queryObat)) {
                                    $a++;
                                    if($a==1) {
                                        $rowsHtml .= "<tr>
                                                        <td align='center' style='white-space:nowrap;'>".$i.".</td>
                                                        <td align='left' style='white-space:nowrap;'>".$rspoli["nm_poli"]."</td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["jml"],1,',','.')."</td>
                                                        <td align='left'>".$rsobat["kode_brng"]." ".$rsobat["nama_brng"]."</td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["biaya"],0,',','.')."</td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["embalase"],0,',','.')."</td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["tuslah"],0,',','.')."</td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["total"],0,',','.')."</td>
                                                      </tr>";
                                    } else {
                                        $rowsHtml .= "<tr>
                                                        <td></td>
                                                        <td></td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["jml"],1,',','.')."</td>
                                                        <td align='left'>".$rsobat["kode_brng"]." ".$rsobat["nama_brng"]."</td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["biaya"],0,',','.')."</td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["embalase"],0,',','.')."</td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["tuslah"],0,',','.')."</td>
                                                        <td align='right' style='white-space:nowrap;'>".number_format($rsobat["total"],0,',','.')."</td>
                                                      </tr>";
                                    }
                                    $subBiaya    += $rsobat["biaya"];
                                    $subEmbalase += $rsobat["embalase"];
                                    $subTuslah   += $rsobat["tuslah"];
                                    $subTotal    += $rsobat["total"];
                                }
                                if($subTotal>0) {
                                    $rowsHtml .= "<tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td align='left' style='white-space:nowrap;'><b>Subtotal</b></td>
                                                    <td align='right' style='white-space:nowrap;'>".number_format($subBiaya,0,',','.')."</td>
                                                    <td align='right' style='white-space:nowrap;'>".number_format($subEmbalase,0,',','.')."</td>
                                                    <td align='right' style='white-space:nowrap;'>".number_format($subTuslah,0,',','.')."</td>
                                                    <td align='right' style='white-space:nowrap;'>".number_format($subTotal,0,',','.')."</td>
                                                  </tr>";
                                    echo $rowsHtml;
                                    $totalBiaya    += $subBiaya;
                                    $totalEmbalase += $subEmbalase;
                                    $totalTuslah   += $subTuslah;
                                    $totalTotal    += $subTotal;
                                    $dataBiayaPerPoli[] = [
                                        'label' => $rspoli["nm_poli"]." (".number_format($subBiaya,0,',','.').")",
                                        'data'  => (float)$subBiaya
                                    ];
                                    $tablePerPoli[] = [
                                        'poli'  => $rspoli["nm_poli"],
                                        'biaya' => $subBiaya
                                    ];
                                    $i++;
                                }
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th style="text-align:center;"></th>
                                <th style="text-align:left;">Total</th>
                                <th style="text-align:center;"></th>
                                <th></th>
                                <th style="text-align:right;"><?=number_format($totalBiaya,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalEmbalase,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalTuslah,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalTotal,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Nilai Biaya Obat Per Poli</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Poliklinik</center></th>
                                        <th width="25%"><center>Biaya Obat</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no = 1;
                                    foreach($tablePerPoli as $baris) {
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$baris["poli"]."</td>
                                                <td align='right'>".number_format($baris["biaya"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_biayaperpoli" class="flot-chart" style="height: 400px;"></div>
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
    var dataBiayaPerPoli = <?= json_encode($dataBiayaPerPoli) ?>;
    if (dataBiayaPerPoli.length > 0) {
        $.plot("#pie_chart_biayaperpoli", dataBiayaPerPoli, {
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
        $("#pie_chart_biayaperpoli").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>