<?php
    $sekarang        = date("Y-m-d");
    $thnsekarang     = substr($sekarang,0,4);
    $blnsekarang     = substr($sekarang,5,2);
    $tglsekarang     = substr($sekarang,8,2);
    $thncaripiutang  = $thnsekarang;
    $blncaripiutang  = $blnsekarang;
    $tglcaripiutang  = $tglsekarang;
    $thncaripiutang2 = $thnsekarang;
    $blncaripiutang2 = $blnsekarang;
    $tglcaripiutang2 = $tglsekarang;
    if(isset($_POST["BtnCari"])){
        $thncaripiutang  = validTeks(trim(isset($_POST['tgl_cari_piutang']))?substr($_POST['tgl_cari_piutang'],6,4):$thnsekarang);
        $blncaripiutang  = validTeks(trim(isset($_POST['tgl_cari_piutang']))?substr($_POST['tgl_cari_piutang'],3,2):$blnsekarang);
        $tglcaripiutang  = validTeks(trim(isset($_POST['tgl_cari_piutang']))?substr($_POST['tgl_cari_piutang'],0,2):$tglsekarang);
        $thncaripiutang2 = validTeks(trim(isset($_POST['tgl_cari_piutang2']))?substr($_POST['tgl_cari_piutang2'],6,4):$thnsekarang);
        $blncaripiutang2 = validTeks(trim(isset($_POST['tgl_cari_piutang2']))?substr($_POST['tgl_cari_piutang2'],3,2):$blnsekarang);
        $tglcaripiutang2 = validTeks(trim(isset($_POST['tgl_cari_piutang2']))?substr($_POST['tgl_cari_piutang2'],0,2):$tglsekarang);
    }
    
    $tglAwalPiutang  = $thncaripiutang."-".$blncaripiutang."-".$tglcaripiutang;
    $tglAkhirPiutang = $thncaripiutang2."-".$blncaripiutang2."-".$tglcaripiutang2;
    $akunList        = [];
    $queryAkun       = bukaquery("select akun_piutang.nama_bayar from akun_piutang order by akun_piutang.nama_bayar asc");
    while($rsakun = mysqli_fetch_array($queryAkun)) {
        $akunList[] = [
            'label'      => $rsakun["nama_bayar"],
            'total'      => 0,
            'rawatjalan' => 0,
            'rawatinap'  => 0
        ];
    }
    
    $tanggalAkun     = [];
    $bulanAkun       = [];
    $tanggalKategori = [];
    $bulanKategori   = [];
    $kategoriKosong  = ['rawatjalan'=>0,'rawatinap'=>0];
    $queryJalan = bukaquery(
        "select nota_jalan.no_rawat,nota_jalan.tanggal ".
        "from piutang_pasien inner join nota_jalan on piutang_pasien.no_rawat=nota_jalan.no_rawat ".
        "where nota_jalan.tanggal between '$tglAwalPiutang' and '$tglAkhirPiutang' ".
        "order by nota_jalan.tanggal,nota_jalan.no_nota"
    );
    while($rsjalan = mysqli_fetch_array($queryJalan)) {
        $tglP = $rsjalan["tanggal"];
        $blnP = substr($tglP,0,7);
        if(!isset($tanggalAkun[$tglP])) {
            $tanggalAkun[$tglP] = array_fill(0,count($akunList),0);
        }
        if(!isset($bulanAkun[$blnP])) {
            $bulanAkun[$blnP] = array_fill(0,count($akunList),0);
        }
        if(!isset($tanggalKategori[$tglP])) {
            $tanggalKategori[$tglP] = $kategoriKosong;
        }
        if(!isset($bulanKategori[$blnP])) {
            $bulanKategori[$blnP] = $kategoriKosong;
        }
        foreach($akunList as $idx => $akun) {
            $bayar = (float) getOne("select sum(detail_piutang_pasien.totalpiutang) from detail_piutang_pasien where detail_piutang_pasien.no_rawat='".$rsjalan["no_rawat"]."' and detail_piutang_pasien.nama_bayar='".$akun["label"]."'");
            $akunList[$idx]["rawatjalan"] += $bayar;
            $akunList[$idx]["total"]      += $bayar;
            $tanggalAkun[$tglP][$idx]     += $bayar;
            $bulanAkun[$blnP][$idx]       += $bayar;
            $tanggalKategori[$tglP]["rawatjalan"] += $bayar;
            $bulanKategori[$blnP]["rawatjalan"]   += $bayar;
        }
    }
    
    $queryInap = bukaquery(
        "select nota_inap.no_rawat,nota_inap.tanggal ".
        "from piutang_pasien inner join nota_inap on piutang_pasien.no_rawat=nota_inap.no_rawat ".
        "where nota_inap.tanggal between '$tglAwalPiutang' and '$tglAkhirPiutang' ".
        "order by nota_inap.tanggal,nota_inap.no_nota"
    );
    while($rsinap = mysqli_fetch_array($queryInap)) {
        $tglP = $rsinap["tanggal"];
        $blnP = substr($tglP,0,7);
        if(!isset($tanggalAkun[$tglP])) {
            $tanggalAkun[$tglP] = array_fill(0,count($akunList),0);
        }
        if(!isset($bulanAkun[$blnP])) {
            $bulanAkun[$blnP] = array_fill(0,count($akunList),0);
        }
        if(!isset($tanggalKategori[$tglP])) {
            $tanggalKategori[$tglP] = $kategoriKosong;
        }
        if(!isset($bulanKategori[$blnP])) {
            $bulanKategori[$blnP] = $kategoriKosong;
        }
        foreach($akunList as $idx => $akun) {
            $bayar = (float) getOne("select sum(detail_piutang_pasien.totalpiutang) from detail_piutang_pasien where detail_piutang_pasien.no_rawat='".$rsinap["no_rawat"]."' and detail_piutang_pasien.nama_bayar='".$akun["label"]."'");
            $akunList[$idx]["rawatinap"] += $bayar;
            $akunList[$idx]["total"]     += $bayar;
            $tanggalAkun[$tglP][$idx]    += $bayar;
            $bulanAkun[$blnP][$idx]      += $bayar;
            $tanggalKategori[$tglP]["rawatinap"] += $bayar;
            $bulanKategori[$blnP]["rawatinap"]   += $bayar;
        }
    }
    
    ksort($tanggalAkun);
    ksort($bulanAkun);
    ksort($tanggalKategori);
    ksort($bulanKategori);
    $kategoriLabel = [
        'rawatjalan' => 'Rawat Jalan',
        'rawatinap'  => 'Rawat Inap'
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
    <h2><center>PIUTANG PER AKUN PIUTANG</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_piutang">Periode</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_piutang" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripiutang."-".$blncaripiutang."-".$thncaripiutang;?>"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="tgl_cari_piutang2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_piutang2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcaripiutang2."-".$blncaripiutang2."-".$thncaripiutang2;?>"/>
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
                                <th style="min-width:150px;white-space:nowrap;"><center>Akun Piutang</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Rawat Jalan</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Rawat Inap</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Total</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $grandRawatJalan = 0;
                            $grandRawatInap  = 0;
                            foreach($akunList as $akun) {
                                if($akun["total"]>0) {
                                    echo "<tr>
                                            <td align='left' style='white-space:nowrap;'>".$akun["label"]."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($akun["rawatjalan"],0,',','.')."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($akun["rawatinap"],0,',','.')."</td>
                                            <td align='right' style='white-space:nowrap;'><b>".number_format($akun["total"],0,',','.')."</b></td>
                                          </tr>";
                                    $grandRawatJalan += $akun["rawatjalan"];
                                    $grandRawatInap  += $akun["rawatinap"];
                                }
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th style="text-align:left;">Jumlah Total</th>
                                <th style="text-align:right;"><?=number_format($grandRawatJalan,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($grandRawatInap,0,',','.');?></th>
                                <th style="text-align:right;"><?=number_format($grandRawatJalan+$grandRawatInap,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Berdasarkan Akun Piutang</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="70%"><center>Akun Piutang</center></th>
                                        <th width="30%"><center>Total</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $jumlahAkunPiutang = 0;
                                    foreach($akunList as $akun) {
                                        if($akun["total"]>0) {
                                            echo "<tr>
                                                    <td align='left'>Total ".$akun["label"]."</td>
                                                    <td align='right'>".number_format($akun["total"],0,',','.')."</td>
                                                  </tr>";
                                            $jumlahAkunPiutang += $akun["total"];
                                        }
                                    }
                                    if($jumlahAkunPiutang>0) {
                                        echo "<tr>
                                                <td align='left'><b>Jumlah Total</b></td>
                                                <td align='right'><b>".number_format($jumlahAkunPiutang,0,',','.')."</b></td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_akunpiutang" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Berdasarkan Kategori Piutang</div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th width="70%"><center>Kategori</center></th>
                                        <th width="30%"><center>Total</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                    $dataPieKategori = [];
                                    $jumlahKategori  = 0;
                                    foreach($kategoriLabel as $kk => $lbl) {
                                        if($kategoriTotal[$kk]>0) {
                                            echo "<tr>
                                                    <td align='left'>".$lbl."</td>
                                                    <td align='right'>".number_format($kategoriTotal[$kk],0,',','.')."</td>
                                                  </tr>";
                                            $dataPieKategori[] = [
                                                'label' => $lbl." (".number_format($kategoriTotal[$kk],0,',','.').")",
                                                'data'  => (float)$kategoriTotal[$kk]
                                            ];
                                            $jumlahKategori += $kategoriTotal[$kk];
                                        }
                                    }
                                    if($jumlahKategori>0) {
                                        echo "<tr>
                                                <td align='left'><b>Jumlah Total</b></td>
                                                <td align='right'><b>".number_format($jumlahKategori,0,',','.')."</b></td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="pie_chart_kategoripiutang" class="flot-chart" style="height: 400px;"></div>
                    </div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Akun Piutang Per Tanggal</div>
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
                    <div id="chart_akun_tanggal" style="height:400px;width:<?=max($t*90,500);?>px;"></div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Akun Piutang Per Bulan</div>
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
                    <div id="chart_akun_bulan" style="height:400px;width:<?=max($bl*90,500);?>px;"></div>
                </div>
                <hr style="margin:6px 0 0 0;">
            </div>
            <div class="body" style="padding-top:0;">
                <div class="header bg-white" style="border-bottom:none;box-shadow:none;padding:0 20px;margin-bottom:6px;">
                    <div class="text-center" style="font-size:16px;color:#777777;">Kategori Piutang Per Tanggal</div>
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
                    <div class="text-center" style="font-size:16px;color:#777777;">Kategori Piutang Per Bulan</div>
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
        $.plot("#pie_chart_akunpiutang", dataPieAkun, {
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
        $("#pie_chart_akunpiutang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataPieKategori = <?= json_encode($dataPieKategori) ?>;
    if (dataPieKategori.length > 0) {
        $.plot("#pie_chart_kategoripiutang", dataPieKategori, {
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
        $("#pie_chart_kategoripiutang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
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
        $.plot("#chart_akun_tanggal", seriesTanggal, {
            xaxis: { ticks: tickTanggal },
            yaxis: { min: 0 },
            legend: { show: true, position: "nw" }
        });
    } else {
        $("#chart_akun_tanggal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
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
        $.plot("#chart_akun_bulan", seriesBulan, {
            xaxis: { ticks: tickBulan },
            yaxis: { min: 0 },
            legend: { show: true, position: "nw" }
        });
    } else {
        $("#chart_akun_bulan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
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