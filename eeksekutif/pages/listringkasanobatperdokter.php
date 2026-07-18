<?php
    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang,0,4);
    $blnsekarang = substr($sekarang,5,2);
    $tglsekarang = substr($sekarang,8,2);
    $thncaridokter  = $thnsekarang;
    $blncaridokter  = $blnsekarang;
    $tglcaridokter  = $tglsekarang;
    $thncaridokter2 = $thnsekarang;
    $blncaridokter2 = $blnsekarang;
    $tglcaridokter2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncaridokter  = validTeks(trim(isset($_POST['tgl_cari_dokter']))?substr($_POST['tgl_cari_dokter'],6,4):$thnsekarang);
        $blncaridokter  = validTeks(trim(isset($_POST['tgl_cari_dokter']))?substr($_POST['tgl_cari_dokter'],3,2):$blnsekarang);
        $tglcaridokter  = validTeks(trim(isset($_POST['tgl_cari_dokter']))?substr($_POST['tgl_cari_dokter'],0,2):$tglsekarang);
        $thncaridokter2 = validTeks(trim(isset($_POST['tgl_cari_dokter2']))?substr($_POST['tgl_cari_dokter2'],6,4):$thnsekarang);
        $blncaridokter2 = validTeks(trim(isset($_POST['tgl_cari_dokter2']))?substr($_POST['tgl_cari_dokter2'],3,2):$blnsekarang);
        $tglcaridokter2 = validTeks(trim(isset($_POST['tgl_cari_dokter2']))?substr($_POST['tgl_cari_dokter2'],0,2):$tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>REKAP PENGGUNAAN OBAT PER DOKTER</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_dokter">Tgl.Beri Obat</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_dokter" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaridokter."-".$blncaridokter."-".$thncaridokter;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_dokter2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_dokter2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaridokter2."-".$blncaridokter2."-".$thncaridokter2;?>"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Tampilkan Data & Grafik</button></center>
                </form>
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Rekap Obat Per Dokter - Semua</div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th style="min-width:50px;white-space:nowrap;"><center>No.</center></th>
                                <th style="min-width:180px;white-space:nowrap;"><center>Dokter</center></th>
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
                            $i                        = 1;
                            $totalBiayaSemua          = 0;
                            $totalEmbalaseSemua       = 0;
                            $totalTuslahSemua         = 0;
                            $totalTotalSemua          = 0;
                            $dataBiayaPerDokterSemua  = [];
                            $tablePerDokterSemua      = [];
                            $queryDokterSemua = bukaquery("select dokter.kd_dokter,dokter.nm_dokter from dokter order by dokter.nm_dokter asc");
                            while($rsdokter = mysqli_fetch_array($queryDokterSemua)) {
                                $queryObat = bukaquery(
                                    "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,".
                                    "sum(detail_pemberian_obat.embalase) as embalase,sum(detail_pemberian_obat.tuslah) as tuslah,sum(detail_pemberian_obat.total) as total ".
                                    "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng ".
                                    "where reg_periksa.tgl_registrasi between '$thncaridokter-$blncaridokter-$tglcaridokter' and '$thncaridokter2-$blncaridokter2-$tglcaridokter2' ".
                                    "and reg_periksa.kd_dokter='".$rsdokter["kd_dokter"]."' group by detail_pemberian_obat.kode_brng order by databarang.nama_brng asc"
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
                                                        <td align='left' style='white-space:nowrap;'>".$rsdokter["nm_dokter"]."</td>
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
                                    $totalBiayaSemua    += $subBiaya;
                                    $totalEmbalaseSemua += $subEmbalase;
                                    $totalTuslahSemua   += $subTuslah;
                                    $totalTotalSemua    += $subTotal;
                                    $dataBiayaPerDokterSemua[] = [
                                        'label' => $rsdokter["nm_dokter"]." (".number_format($subBiaya,0,',','.').")",
                                        'data'  => (float)$subBiaya
                                    ];
                                    $tablePerDokterSemua[] = [
                                        'dokter' => $rsdokter["nm_dokter"],
                                        'biaya'  => $subBiaya
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
                                <th style="text-align:right;"><?=number_format($totalBiayaSemua,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalEmbalaseSemua,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalTuslahSemua,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalTotalSemua,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Nilai Biaya Obat Per Dokter - Semua</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Dokter</center></th>
                                        <th width="25%"><center>Biaya Obat</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no = 1;
                                    foreach($tablePerDokterSemua as $baris) {
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$baris["dokter"]."</td>
                                                <td align='right'>".number_format($baris["biaya"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_biayaperdokter_semua" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Rekap Obat Per Dokter - Rawat Jalan</div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th style="min-width:50px;white-space:nowrap;"><center>No.</center></th>
                                <th style="min-width:180px;white-space:nowrap;"><center>Dokter</center></th>
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
                            $i                        = 1;
                            $totalBiayaRalan          = 0;
                            $totalEmbalaseRalan       = 0;
                            $totalTuslahRalan         = 0;
                            $totalTotalRalan          = 0;
                            $dataBiayaPerDokterRalan  = [];
                            $tablePerDokterRalan      = [];
                            $queryDokterRalan = bukaquery("select dokter.kd_dokter,dokter.nm_dokter from dokter order by dokter.nm_dokter asc");
                            while($rsdokter = mysqli_fetch_array($queryDokterRalan)) {
                                $queryObat = bukaquery(
                                    "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,".
                                    "sum(detail_pemberian_obat.embalase) as embalase,sum(detail_pemberian_obat.tuslah) as tuslah,sum(detail_pemberian_obat.total) as total ".
                                    "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng ".
                                    "where detail_pemberian_obat.status='Ralan' and reg_periksa.tgl_registrasi between '$thncaridokter-$blncaridokter-$tglcaridokter' and '$thncaridokter2-$blncaridokter2-$tglcaridokter2' ".
                                    "and reg_periksa.kd_dokter='".$rsdokter["kd_dokter"]."' group by detail_pemberian_obat.kode_brng order by databarang.nama_brng asc"
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
                                                        <td align='left' style='white-space:nowrap;'>".$rsdokter["nm_dokter"]."</td>
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
                                    $totalBiayaRalan    += $subBiaya;
                                    $totalEmbalaseRalan += $subEmbalase;
                                    $totalTuslahRalan   += $subTuslah;
                                    $totalTotalRalan    += $subTotal;
                                    $dataBiayaPerDokterRalan[] = [
                                        'label' => $rsdokter["nm_dokter"]." (".number_format($subBiaya,0,',','.').")",
                                        'data'  => (float)$subBiaya
                                    ];
                                    $tablePerDokterRalan[] = [
                                        'dokter' => $rsdokter["nm_dokter"],
                                        'biaya'  => $subBiaya
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
                                <th style="text-align:right;"><?=number_format($totalBiayaRalan,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalEmbalaseRalan,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalTuslahRalan,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalTotalRalan,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Nilai Biaya Obat Per Dokter - Rawat Jalan</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Dokter</center></th>
                                        <th width="25%"><center>Biaya Obat</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no = 1;
                                    foreach($tablePerDokterRalan as $baris) {
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$baris["dokter"]."</td>
                                                <td align='right'>".number_format($baris["biaya"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_biayaperdokter_ralan" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Rekap Obat Per Dokter - Rawat Inap</div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th style="min-width:50px;white-space:nowrap;"><center>No.</center></th>
                                <th style="min-width:180px;white-space:nowrap;"><center>Dokter</center></th>
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
                            $i                        = 1;
                            $totalBiayaRanap          = 0;
                            $totalEmbalaseRanap       = 0;
                            $totalTuslahRanap         = 0;
                            $totalTotalRanap          = 0;
                            $dataBiayaPerDokterRanap  = [];
                            $tablePerDokterRanap      = [];
                            $queryDokterRanap = bukaquery("select dokter.kd_dokter,dokter.nm_dokter from dokter order by dokter.nm_dokter asc");
                            while($rsdokter = mysqli_fetch_array($queryDokterRanap)) {
                                $queryObat = bukaquery(
                                    "select detail_pemberian_obat.kode_brng,databarang.nama_brng,sum(detail_pemberian_obat.jml) as jml,(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as biaya,".
                                    "sum(detail_pemberian_obat.embalase) as embalase,sum(detail_pemberian_obat.tuslah) as tuslah,sum(detail_pemberian_obat.total) as total ".
                                    "from detail_pemberian_obat inner join reg_periksa on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng ".
                                    "where detail_pemberian_obat.status='Ranap' and reg_periksa.tgl_registrasi between '$thncaridokter-$blncaridokter-$tglcaridokter' and '$thncaridokter2-$blncaridokter2-$tglcaridokter2' ".
                                    "and reg_periksa.kd_dokter='".$rsdokter["kd_dokter"]."' group by detail_pemberian_obat.kode_brng order by databarang.nama_brng asc"
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
                                                        <td align='left' style='white-space:nowrap;'>".$rsdokter["nm_dokter"]."</td>
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
                                    $totalBiayaRanap    += $subBiaya;
                                    $totalEmbalaseRanap += $subEmbalase;
                                    $totalTuslahRanap   += $subTuslah;
                                    $totalTotalRanap    += $subTotal;
                                    $dataBiayaPerDokterRanap[] = [
                                        'label' => $rsdokter["nm_dokter"]." (".number_format($subBiaya,0,',','.').")",
                                        'data'  => (float)$subBiaya
                                    ];
                                    $tablePerDokterRanap[] = [
                                        'dokter' => $rsdokter["nm_dokter"],
                                        'biaya'  => $subBiaya
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
                                <th style="text-align:right;"><?=number_format($totalBiayaRanap,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalEmbalaseRanap,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalTuslahRanap,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($totalTotalRanap,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Nilai Biaya Obat Per Dokter - Rawat Inap</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="5%"><center>No</center></th>
                                        <th width="70%"><center>Dokter</center></th>
                                        <th width="25%"><center>Biaya Obat</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $no = 1;
                                    foreach($tablePerDokterRanap as $baris) {
                                        echo "<tr>
                                                <td align='center'>".$no++."</td>
                                                <td align='left'>".$baris["dokter"]."</td>
                                                <td align='right'>".number_format($baris["biaya"],0,',','.')."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_biayaperdokter_ranap" class="flot-chart" style="height: 400px;"></div>
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
    var dataBiayaPerDokterSemua = <?= json_encode($dataBiayaPerDokterSemua) ?>;
    if (dataBiayaPerDokterSemua.length > 0) {
        $.plot("#pie_chart_biayaperdokter_semua", dataBiayaPerDokterSemua, {
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
        $("#pie_chart_biayaperdokter_semua").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBiayaPerDokterRalan = <?= json_encode($dataBiayaPerDokterRalan) ?>;
    if (dataBiayaPerDokterRalan.length > 0) {
        $.plot("#pie_chart_biayaperdokter_ralan", dataBiayaPerDokterRalan, {
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
        $("#pie_chart_biayaperdokter_ralan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBiayaPerDokterRanap = <?= json_encode($dataBiayaPerDokterRanap) ?>;
    if (dataBiayaPerDokterRanap.length > 0) {
        $.plot("#pie_chart_biayaperdokter_ranap", dataBiayaPerDokterRanap, {
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
        $("#pie_chart_biayaperdokter_ranap").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>