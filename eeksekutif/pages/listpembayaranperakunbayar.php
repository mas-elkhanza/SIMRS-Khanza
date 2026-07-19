<?php
    $sekarang      = date("Y-m-d");
    $thnsekarang   = substr($sekarang,0,4);
    $blnsekarang   = substr($sekarang,5,2);
    $tglsekarang   = substr($sekarang,8,2);
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
    
    $akunList   = [];
    $queryBayar = bukaquery("select akun_bayar.nama_bayar from akun_bayar group by akun_bayar.nama_bayar order by akun_bayar.nama_bayar asc");
    while($rsbayar = mysqli_fetch_array($queryBayar)) {
        $akunList[] = [
            'label'         => $rsbayar["nama_bayar"],
            'type'          => 'bayar',
            'key'           => $rsbayar["nama_bayar"],
            'total'         => 0,
            'rawatinap'     => 0,
            'rawatjalan'    => 0,
            'apotek'        => 0,
            'deposit'       => 0,
            'pemasukanlain' => 0,
            'labkesling'    => 0
        ];
    }
    
    $queryRekLain = bukaquery(
        "select rekening.kd_rek,rekening.nm_rek from rekening where rekening.kd_rek in ".
        "(select kategori_pemasukan_lain.kd_rek2 from kategori_pemasukan_lain group by kategori_pemasukan_lain.kd_rek2) order by rekening.nm_rek asc"
    );
    while($rsreklain = mysqli_fetch_array($queryRekLain)) {
        $akunList[] = [
            'label'         => $rsreklain["nm_rek"],
            'type'          => 'rekening',
            'key'           => $rsreklain["kd_rek"],
            'total'         => 0,
            'rawatinap'     => 0,
            'rawatjalan'    => 0,
            'apotek'        => 0,
            'deposit'       => 0,
            'pemasukanlain' => 0,
            'labkesling'    => 0
        ];
    }
    
    $allTotal        = 0;
    $lainLainTotal   = 0;
    $tanggalAkun     = [];
    $bulanAkun       = [];
    $tanggalKategori = [];
    $bulanKategori   = [];
    $kategoriKosong  = ['rawatinap'=>0,'rawatjalan'=>0,'apotek'=>0,'deposit'=>0,'pemasukanlain'=>0,'labkesling'=>0,'lainlain'=>0];
    
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
        
        $tglBayar = substr($rstagihan["tgl_bayar"],0,10);
        $blnBayar = substr($rstagihan["tgl_bayar"],0,7);
        if(!isset($tanggalKategori[$tglBayar])) {
            $tanggalKategori[$tglBayar] = $kategoriKosong;
        }
        if(!isset($bulanKategori[$blnBayar])) {
            $bulanKategori[$blnBayar] = $kategoriKosong;
        }
        if(!$tidakditemukan) {
            $allTotal += $rstagihan["jumlah_bayar"];
            $kat = "";
            if($norawatinap!="") {
                $kat = "rawatinap";
            } elseif($norawatjalan!="") {
                $kat = "rawatjalan";
            } elseif($notajual!="") {
                $kat = "apotek";
            } elseif($nodeposit!="") {
                $kat = "deposit";
            } elseif($nopemasukanlain!="") {
                $kat = "pemasukanlain";
            } elseif($notakesling!="") {
                $kat = "labkesling";
            }
            if(!isset($tanggalAkun[$tglBayar])) {
                $tanggalAkun[$tglBayar] = array_fill(0,count($akunList),0);
            }
            if(!isset($bulanAkun[$blnBayar])) {
                $bulanAkun[$blnBayar] = array_fill(0,count($akunList),0);
            }
            foreach($akunList as $idx => $akun) {
                $bayar = 0;
                if($akun["type"]=="bayar") {
                    if($norawatinap!="") {
                        $bayar = (float) getOne("select sum(detail_nota_inap.besar_bayar) from detail_nota_inap where detail_nota_inap.no_rawat='".$norawatinap."' and detail_nota_inap.nama_bayar='".$akun["key"]."'");
                        $akunList[$idx]["rawatinap"] += $bayar;
                    } elseif($norawatjalan!="") {
                        $bayar = (float) getOne("select sum(detail_nota_jalan.besar_bayar) from detail_nota_jalan where detail_nota_jalan.no_rawat='".$norawatjalan."' and detail_nota_jalan.nama_bayar='".$akun["key"]."'");
                        $akunList[$idx]["rawatjalan"] += $bayar;
                    } elseif($notajual!="") {
                        $bayar = (float) getOne("select (sum(detailjual.total)+penjualan.ongkir+penjualan.ppn) from detailjual inner join penjualan on penjualan.nota_jual=detailjual.nota_jual where penjualan.nota_jual='".$notajual."' and penjualan.nama_bayar='".$akun["key"]."'");
                        $akunList[$idx]["apotek"] += $bayar;
                    } elseif($nodeposit!="") {
                        $bayar = (float) getOne("select sum(deposit.besar_deposit) from deposit where deposit.no_deposit='".$nodeposit."' and deposit.nama_bayar='".$akun["key"]."'");
                        $akunList[$idx]["deposit"] += $bayar;
                    } elseif($notakesling!="") {
                        $bayar = (float) getOne("select sum(labkesling_detail_pembayaran_pengujian_sampel.besar_bayar) from labkesling_detail_pembayaran_pengujian_sampel where labkesling_detail_pembayaran_pengujian_sampel.no_pembayaran='".$notakesling."' and labkesling_detail_pembayaran_pengujian_sampel.nama_bayar='".$akun["key"]."'");
                        $akunList[$idx]["labkesling"] += $bayar;
                    }
                } else {
                    if($nopemasukanlain!="") {
                        $bayar = (float) getOne("select sum(pemasukan_lain.besar) from pemasukan_lain inner join kategori_pemasukan_lain on kategori_pemasukan_lain.kode_kategori=pemasukan_lain.kode_kategori where pemasukan_lain.no_masuk='".$nopemasukanlain."' and kategori_pemasukan_lain.kd_rek2='".$akun["key"]."'");
                        $akunList[$idx]["pemasukanlain"] += $bayar;
                    }
                }
                $akunList[$idx]["total"] += $bayar;
                $tanggalAkun[$tglBayar][$idx] += $bayar;
                $bulanAkun[$blnBayar][$idx] += $bayar;
                if($kat!="") {
                    $tanggalKategori[$tglBayar][$kat] += $bayar;
                    $bulanKategori[$blnBayar][$kat] += $bayar;
                }
            }
        } else {
            $lainLainTotal += $rstagihan["jumlah_bayar"];
            $tanggalKategori[$tglBayar]["lainlain"] += $rstagihan["jumlah_bayar"];
            $bulanKategori[$blnBayar]["lainlain"] += $rstagihan["jumlah_bayar"];
        }
    }
    
    ksort($tanggalAkun);
    ksort($bulanAkun);
    ksort($tanggalKategori);
    ksort($bulanKategori);
    $kategoriLabel = [
        'rawatinap'     => 'Rawat Inap',
        'rawatjalan'    => 'Rawat Jalan',
        'apotek'        => 'Penjualan Apotek/OTC',
        'deposit'       => 'Deposit Pasien',
        'pemasukanlain' => 'Pemasukan Lain-lain',
        'labkesling'    => 'Lab Kesehatan Lingkungan',
        'lainlain'      => 'Transaksi Lain-lain'
    ];
    
    $kategoriTotal = $kategoriKosong;
    foreach($tanggalKategori as $nilaiPerKategori) {
        foreach($nilaiPerKategori as $kk => $vv) {
            $kategoriTotal[$kk] += $vv;
        }
    }
    
    $kategoriAktif = [];
    foreach($kategoriLabel as $kk => $lbl) {
        if($kategoriTotal[$kk]>0) {
            $kategoriAktif[] = $kk;
        }
    }
    
    $dataPieAkun = [];
    foreach($akunList as $akun) {
        if($akun["total"]>0) {
            $dataPieAkun[] = [
                'label' => $akun["label"]." (".number_format($akun["total"],0,',','.').")",
                'data'  => (float)$akun["total"]
            ];
        }
    }
    
    $akunAktifIdx = [];
    foreach($akunList as $idx => $akun) {
        if($akun["total"]>0) {
            $akunAktifIdx[] = $idx;
        }
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
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:150px;white-space:nowrap;"><center>Akun Bayar</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>Rawat Inap</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>Rawat Jalan</center></th>
                                <th style="min-width:140px;white-space:nowrap;"><center>Penjualan Apotek/OTC</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>Deposit Pasien</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Pemasukan Lain-lain</center></th>
                                <th style="min-width:150px;white-space:nowrap;"><center>Lab Kesehatan Lingkungan</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Transaksi Lain-lain</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $grandRawatInap     = 0;
                            $grandRawatJalan    = 0;
                            $grandApotek        = 0;
                            $grandDeposit       = 0;
                            $grandPemasukanLain = 0;
                            $grandLabkesling    = 0;
                            foreach($akunList as $akun) {
                                if($akun["total"]>0) {
                                    echo "<tr>
                                            <td align='left' style='white-space:nowrap;'>".$akun["label"]."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($akun["rawatinap"],0,',','.')."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($akun["rawatjalan"],0,',','.')."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($akun["apotek"],0,',','.')."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($akun["deposit"],0,',','.')."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($akun["pemasukanlain"],0,',','.')."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($akun["labkesling"],0,',','.')."</td>
                                            <td align='right' style='white-space:nowrap;'>0</td>
                                            <td align='right' style='white-space:nowrap;'><b>".number_format($akun["total"],0,',','.')."</b></td>
                                          </tr>";
                                    $grandRawatInap     += $akun["rawatinap"];
                                    $grandRawatJalan    += $akun["rawatjalan"];
                                    $grandApotek        += $akun["apotek"];
                                    $grandDeposit       += $akun["deposit"];
                                    $grandPemasukanLain += $akun["pemasukanlain"];
                                    $grandLabkesling    += $akun["labkesling"];
                                }
                            }
                            if($lainLainTotal>0) {
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>Lain-lain</td>
                                        <td align='right' style='white-space:nowrap;'>0</td>
                                        <td align='right' style='white-space:nowrap;'>0</td>
                                        <td align='right' style='white-space:nowrap;'>0</td>
                                        <td align='right' style='white-space:nowrap;'>0</td>
                                        <td align='right' style='white-space:nowrap;'>0</td>
                                        <td align='right' style='white-space:nowrap;'>0</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($lainLainTotal,0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'><b>".number_format($lainLainTotal,0,',','.')."</b></td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th style="text-align:left;">Jumlah Total</th>
                                <th style="text-align:right;"><?=number_format($grandRawatInap,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($grandRawatJalan,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($grandApotek,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($grandDeposit,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($grandPemasukanLain,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($grandLabkesling,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($lainLainTotal,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($allTotal+$lainLainTotal,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Berdasarkan Akun Bayar</div>
                </div>
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
                                    foreach($akunList as $akun) {
                                        if($akun["total"]>0) {
                                            echo "<tr>
                                                    <td align='left'>Total ".$akun["label"]."</td>
                                                    <td align='right'>".number_format($akun["total"],0,',','.')."</td>
                                                  </tr>";
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
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Berdasarkan Kategori Pembayaran</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="70%"><center>Asal Pendapatan</center></th>
                                        <th width="30%"><center>Total</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $dataPieAsal  = [];
                                    $asalPendapatan = [
                                        'Rawat Inap'               => $grandRawatInap,
                                        'Rawat Jalan'              => $grandRawatJalan,
                                        'Penjualan Apotek/OTC'     => $grandApotek,
                                        'Deposit Pasien'           => $grandDeposit,
                                        'Pemasukan Lain-lain'      => $grandPemasukanLain,
                                        'Lab Kesehatan Lingkungan' => $grandLabkesling,
                                        'Transaksi Lain-lain'      => $lainLainTotal
                                    ];
                                    foreach($asalPendapatan as $namaAsal => $nilaiAsal) {
                                        if($nilaiAsal>0) {
                                            echo "<tr>
                                                    <td align='left'>".$namaAsal."</td>
                                                    <td align='right'>".number_format($nilaiAsal,0,',','.')."</td>
                                                  </tr>";
                                            $dataPieAsal[] = [
                                                'label' => $namaAsal." (".number_format($nilaiAsal,0,',','.').")",
                                                'data'  => (float)$nilaiAsal
                                            ];
                                        }
                                    }
                                    $jumlahAsal = $allTotal+$lainLainTotal;
                                    if($jumlahAsal>0) {
                                        echo "<tr>
                                                <td align='left'><b>Jumlah Total</b></td>
                                                <td align='right'><b>".number_format($jumlahAsal,0,',','.')."</b></td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_asalpendapatan" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Akun Bayar Per Tanggal</div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:100px;white-space:nowrap;"><center>Tanggal</center></th>
                                <?php
                                    foreach($akunAktifIdx as $idx) {
                                        echo "<th style='min-width:120px;white-space:nowrap;'><center>".$akunList[$idx]["label"]."</center></th>";
                                    }
                                ?>
                                <th style="min-width:130px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $totalPerAkunTanggal = array_fill(0,count($akunAktifIdx),0);
                            $grandTotalTanggal   = 0;
                            $dataMultiLine       = [];
                            $tickTanggal         = [];
                            $t                   = 0;
                            foreach($tanggalAkun as $tgl => $nilaiPerAkun) {
                                $rowTotal = 0;
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".date("d-m-Y",strtotime($tgl))."</td>";
                                foreach($akunAktifIdx as $a => $idx) {
                                    $nilai = $nilaiPerAkun[$idx];
                                    echo "<td align='right' style='white-space:nowrap;'>".number_format($nilai,0,',','.')."</td>";
                                    $rowTotal                += $nilai;
                                    $totalPerAkunTanggal[$a] += $nilai;
                                    $dataMultiLine[$a][]      = [$t,$nilai];
                                }
                                echo "<td align='right' style='white-space:nowrap;'><b>".number_format($rowTotal,0,',','.')."</b></td>
                                      </tr>";
                                $grandTotalTanggal += $rowTotal;
                                $tickTanggal[]       = [$t,date("d-m-Y",strtotime($tgl))];
                                $t++;
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th style="text-align:left;">Total</th>
                                <?php
                                    foreach($akunAktifIdx as $a => $idx) {
                                        echo "<th style='text-align:right;'>".number_format($totalPerAkunTanggal[$a],0,',','.')."</th>";
                                    }
                                ?>
                                <th style="text-align:right;"><?=number_format($grandTotalTanggal,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <div style="overflow-x:auto;">
                    <div id="chart_pendapatan_tanggal" style="height:400px;width:<?=max($t*90,500);?>px;"></div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Akun Bayar Per Bulan</div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:100px;white-space:nowrap;"><center>Bulan</center></th>
                                <?php
                                    foreach($akunAktifIdx as $idx) {
                                        echo "<th style='min-width:120px;white-space:nowrap;'><center>".$akunList[$idx]["label"]."</center></th>";
                                    }
                                ?>
                                <th style="min-width:130px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $namaBulanFull = ["01"=>"Januari","02"=>"Februari","03"=>"Maret","04"=>"April","05"=>"Mei","06"=>"Juni","07"=>"Juli","08"=>"Agustus","09"=>"September","10"=>"Oktober","11"=>"November","12"=>"Desember"];
                            $totalPerAkunBulan = array_fill(0,count($akunAktifIdx),0);
                            $grandTotalBulan   = 0;
                            $dataMultiLineBulan = [];
                            $tickBulan          = [];
                            $bl                  = 0;
                            foreach($bulanAkun as $bulanKey => $nilaiPerAkunBulan) {
                                $labelBulan = $namaBulanFull[substr($bulanKey,5,2)]." ".substr($bulanKey,0,4);
                                $rowTotalBulan = 0;
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$labelBulan."</td>";
                                foreach($akunAktifIdx as $a => $idx) {
                                    $nilaiBulan = $nilaiPerAkunBulan[$idx];
                                    echo "<td align='right' style='white-space:nowrap;'>".number_format($nilaiBulan,0,',','.')."</td>";
                                    $rowTotalBulan            += $nilaiBulan;
                                    $totalPerAkunBulan[$a]    += $nilaiBulan;
                                    $dataMultiLineBulan[$a][]  = [$bl,$nilaiBulan];
                                }
                                echo "<td align='right' style='white-space:nowrap;'><b>".number_format($rowTotalBulan,0,',','.')."</b></td>
                                      </tr>";
                                $grandTotalBulan += $rowTotalBulan;
                                $tickBulan[]       = [$bl,$labelBulan];
                                $bl++;
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th style="text-align:left;">Total</th>
                                <?php
                                    foreach($akunAktifIdx as $a => $idx) {
                                        echo "<th style='text-align:right;'>".number_format($totalPerAkunBulan[$a],0,',','.')."</th>";
                                    }
                                ?>
                                <th style="text-align:right;"><?=number_format($grandTotalBulan,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <div style="overflow-x:auto;">
                    <div id="chart_pendapatan_bulan" style="height:400px;width:<?=max($bl*90,500);?>px;"></div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Kategori Pembayaran Per Tanggal</div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:100px;white-space:nowrap;"><center>Tanggal</center></th>
                                <?php
                                    foreach($kategoriAktif as $kk) {
                                        echo "<th style='min-width:130px;white-space:nowrap;'><center>".$kategoriLabel[$kk]."</center></th>";
                                    }
                                ?>
                                <th style="min-width:130px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $totalPerKategoriTanggal = array_fill(0,count($kategoriAktif),0);
                            $grandTotalKategoriTanggal = 0;
                            $dataMultiLineKategoriTanggal = [];
                            $tickKategoriTanggal          = [];
                            $tk                            = 0;
                            foreach($tanggalKategori as $tglk => $nilaiPerKategori) {
                                $rowTotalKategoriTanggal = 0;
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".date("d-m-Y",strtotime($tglk))."</td>";
                                foreach($kategoriAktif as $ik => $kk) {
                                    $nilaiKategori = $nilaiPerKategori[$kk];
                                    echo "<td align='right' style='white-space:nowrap;'>".number_format($nilaiKategori,0,',','.')."</td>";
                                    $rowTotalKategoriTanggal        += $nilaiKategori;
                                    $totalPerKategoriTanggal[$ik]   += $nilaiKategori;
                                    $dataMultiLineKategoriTanggal[$ik][] = [$tk,$nilaiKategori];
                                }
                                echo "<td align='right' style='white-space:nowrap;'><b>".number_format($rowTotalKategoriTanggal,0,',','.')."</b></td>
                                      </tr>";
                                $grandTotalKategoriTanggal += $rowTotalKategoriTanggal;
                                $tickKategoriTanggal[]       = [$tk,date("d-m-Y",strtotime($tglk))];
                                $tk++;
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th style="text-align:left;">Total</th>
                                <?php
                                    foreach($kategoriAktif as $ik => $kk) {
                                        echo "<th style='text-align:right;'>".number_format($totalPerKategoriTanggal[$ik],0,',','.')."</th>";
                                    }
                                ?>
                                <th style="text-align:right;"><?=number_format($grandTotalKategoriTanggal,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <div style="overflow-x:auto;">
                    <div id="chart_kategori_tanggal" style="height:400px;width:<?=max($tk*90,500);?>px;"></div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Kategori Pembayaran Per Bulan</div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:100px;white-space:nowrap;"><center>Bulan</center></th>
                                <?php
                                    foreach($kategoriAktif as $kk) {
                                        echo "<th style='min-width:130px;white-space:nowrap;'><center>".$kategoriLabel[$kk]."</center></th>";
                                    }
                                ?>
                                <th style="min-width:130px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $totalPerKategoriBulan = array_fill(0,count($kategoriAktif),0);
                            $grandTotalKategoriBulan = 0;
                            $dataMultiLineKategoriBulan = [];
                            $tickKategoriBulan          = [];
                            $bk                          = 0;
                            foreach($bulanKategori as $bulanKeyKat => $nilaiPerKategoriBulan) {
                                $labelBulanKat = $namaBulanFull[substr($bulanKeyKat,5,2)]." ".substr($bulanKeyKat,0,4);
                                $rowTotalKategoriBulan = 0;
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$labelBulanKat."</td>";
                                foreach($kategoriAktif as $ik => $kk) {
                                    $nilaiKategoriBulan = $nilaiPerKategoriBulan[$kk];
                                    echo "<td align='right' style='white-space:nowrap;'>".number_format($nilaiKategoriBulan,0,',','.')."</td>";
                                    $rowTotalKategoriBulan          += $nilaiKategoriBulan;
                                    $totalPerKategoriBulan[$ik]     += $nilaiKategoriBulan;
                                    $dataMultiLineKategoriBulan[$ik][] = [$bk,$nilaiKategoriBulan];
                                }
                                echo "<td align='right' style='white-space:nowrap;'><b>".number_format($rowTotalKategoriBulan,0,',','.')."</b></td>
                                      </tr>";
                                $grandTotalKategoriBulan += $rowTotalKategoriBulan;
                                $tickKategoriBulan[]       = [$bk,$labelBulanKat];
                                $bk++;
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th style="text-align:left;">Total</th>
                                <?php
                                    foreach($kategoriAktif as $ik => $kk) {
                                        echo "<th style='text-align:right;'>".number_format($totalPerKategoriBulan[$ik],0,',','.')."</th>";
                                    }
                                ?>
                                <th style="text-align:right;"><?=number_format($grandTotalKategoriBulan,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <div style="overflow-x:auto;">
                    <div id="chart_kategori_bulan" style="height:400px;width:<?=max($bk*90,500);?>px;"></div>
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
    var dataPieAsal = <?= json_encode($dataPieAsal) ?>;
    if (dataPieAsal.length > 0) {
        $.plot("#pie_chart_asalpendapatan", dataPieAsal, {
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
        $("#pie_chart_asalpendapatan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataMultiLine = <?= json_encode(array_values($dataMultiLine)) ?>;
    var namaAkunAktif = <?= json_encode(array_map(function($idx) use ($akunList){ return $akunList[$idx]["label"]; }, $akunAktifIdx)) ?>;
    var tickTanggal   = <?= json_encode($tickTanggal) ?>;
    if (dataMultiLine.length > 0 && tickTanggal.length > 0) {
        var seriesTanggal = [];
        for (var s = 0; s < dataMultiLine.length; s++) {
            seriesTanggal.push({
                label: namaAkunAktif[s],
                data: dataMultiLine[s],
                lines: { show: true },
                points: { show: true }
            });
        }
        $.plot("#chart_pendapatan_tanggal", seriesTanggal, {
            xaxis: { ticks: tickTanggal },
            yaxis: { min: 0 },
            legend: { show: true, position: "nw" }
        });
    } else {
        $("#chart_pendapatan_tanggal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataMultiLineBulan = <?= json_encode(array_values($dataMultiLineBulan)) ?>;
    var tickBulan          = <?= json_encode($tickBulan) ?>;
    if (dataMultiLineBulan.length > 0 && tickBulan.length > 0) {
        var seriesBulan = [];
        for (var sb = 0; sb < dataMultiLineBulan.length; sb++) {
            seriesBulan.push({
                label: namaAkunAktif[sb],
                data: dataMultiLineBulan[sb],
                lines: { show: true },
                points: { show: true }
            });
        }
        $.plot("#chart_pendapatan_bulan", seriesBulan, {
            xaxis: { ticks: tickBulan },
            yaxis: { min: 0 },
            legend: { show: true, position: "nw" }
        });
    } else {
        $("#chart_pendapatan_bulan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataMultiLineKategoriTanggal = <?= json_encode(array_values($dataMultiLineKategoriTanggal)) ?>;
    var namaKategoriAktif            = <?= json_encode(array_map(function($kk) use ($kategoriLabel){ return $kategoriLabel[$kk]; }, $kategoriAktif)) ?>;
    var tickKategoriTanggal           = <?= json_encode($tickKategoriTanggal) ?>;
    if (dataMultiLineKategoriTanggal.length > 0 && tickKategoriTanggal.length > 0) {
        var seriesKategoriTanggal = [];
        for (var kt = 0; kt < dataMultiLineKategoriTanggal.length; kt++) {
            seriesKategoriTanggal.push({
                label: namaKategoriAktif[kt],
                data: dataMultiLineKategoriTanggal[kt],
                lines: { show: true },
                points: { show: true }
            });
        }
        $.plot("#chart_kategori_tanggal", seriesKategoriTanggal, {
            xaxis: { ticks: tickKategoriTanggal },
            yaxis: { min: 0 },
            legend: { show: true, position: "nw" }
        });
    } else {
        $("#chart_kategori_tanggal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataMultiLineKategoriBulan = <?= json_encode(array_values($dataMultiLineKategoriBulan)) ?>;
    var tickKategoriBulan          = <?= json_encode($tickKategoriBulan) ?>;
    if (dataMultiLineKategoriBulan.length > 0 && tickKategoriBulan.length > 0) {
        var seriesKategoriBulan = [];
        for (var kb = 0; kb < dataMultiLineKategoriBulan.length; kb++) {
            seriesKategoriBulan.push({
                label: namaKategoriAktif[kb],
                data: dataMultiLineKategoriBulan[kb],
                lines: { show: true },
                points: { show: true }
            });
        }
        $.plot("#chart_kategori_bulan", seriesKategoriBulan, {
            xaxis: { ticks: tickKategoriBulan },
            yaxis: { min: 0 },
            legend: { show: true, position: "nw" }
        });
    } else {
        $("#chart_kategori_bulan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>