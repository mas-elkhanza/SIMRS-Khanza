<?php
    function bacaRekening($kdRekParent, $isRoot, $tipe, $balanceFilter, $formula, $indent, &$totalAccum, &$daftarAnak, &$daftarRootByTipe, &$petaSaldoAwal, &$petaDebet, &$petaKredit) {
        $daftar = $isRoot
            ? (isset($daftarRootByTipe[$tipe]) ? $daftarRootByTipe[$tipe] : [])
            : (isset($daftarAnak[$kdRekParent]) ? $daftarAnak[$kdRekParent] : []);

        $html = '';
        foreach ($daftar as $rowRek) {
            if ($rowRek['tipe']!=$tipe) continue;
            if ($balanceFilter!=='' && $rowRek['balance']!=$balanceFilter) continue;

            $saldoAwal = isset($petaSaldoAwal[$rowRek['kd_rek']]) ? $petaSaldoAwal[$rowRek['kd_rek']] : 0;
            $debet     = isset($petaDebet[$rowRek['kd_rek']]) ? $petaDebet[$rowRek['kd_rek']] : 0;
            $kredit    = isset($petaKredit[$rowRek['kd_rek']]) ? $petaKredit[$rowRek['kd_rek']] : 0;
            $debkret   = ($formula === 'KD') ? ($kredit - $debet) : ($debet - $kredit);

            $saldoAkhir  = $saldoAwal + $debkret;
            $totalAccum += $saldoAkhir;
            $html .= "<tr>
                        <td></td>
                        <td style='padding-left:".($indent*20)."px;'>".$rowRek['kd_rek']." ".$rowRek['nm_rek']."</td>
                        <td align='right' style='white-space:nowrap;'>".number_format($saldoAkhir,0,',','.')."</td>
                      </tr>";
            $html .= bacaRekening($rowRek['kd_rek'], false, $tipe, $balanceFilter, $formula, $indent+1, $totalAccum, $daftarAnak, $daftarRootByTipe, $petaSaldoAwal, $petaDebet, $petaKredit);
        }
        return $html;
    }
    $tahunsekarang  = date("Y");
    $tahuncari      = $tahunsekarang;
    if(isset($_POST["BtnCari"])){
        $tahuncari = validTeks(trim(isset($_POST['tahun_cari']))?$_POST['tahun_cari']:$tahunsekarang);
    }

    $petaSaldoAwal  = [];
    $querySaldoAwal = bukaquery(
        "select rekeningtahun.kd_rek,sum(rekeningtahun.saldo_awal) as saldo from rekeningtahun ".
        "where rekeningtahun.thn between '".$tahuncari."' and '".$tahuncari."' group by rekeningtahun.kd_rek"
    );
    while($rowSaldo = mysqli_fetch_array($querySaldoAwal)) {
        $petaSaldoAwal[$rowSaldo['kd_rek']] = (float) $rowSaldo['saldo'];
    }

    $petaDebet          = [];
    $petaKredit         = [];
    $queryDebetKredit   = bukaquery(
        "select detailjurnal.kd_rek,sum(detailjurnal.debet) as totaldebet,sum(detailjurnal.kredit) as totalkredit ".
        "from jurnal inner join detailjurnal on detailjurnal.no_jurnal=jurnal.no_jurnal ".
        "where jurnal.tgl_jurnal between '".$tahuncari."-01-01' and '".$tahuncari."-12-31' group by detailjurnal.kd_rek"
    );
    while($rowDK = mysqli_fetch_array($queryDebetKredit)) {
        $petaDebet[$rowDK['kd_rek']]  = (float) $rowDK['totaldebet'];
        $petaKredit[$rowDK['kd_rek']] = (float) $rowDK['totalkredit'];
    }

    $daftarRootByTipe = [];
    $daftarAnak       = [];
    $queryRekening    = bukaquery(
        "select rekening.kd_rek,rekening.nm_rek,rekening.level,rekening.tipe,rekening.balance,subrekening.kd_rek as parent_kd_rek ".
        "from rekening left join subrekening on rekening.kd_rek=subrekening.kd_rek2 order by rekening.kd_rek asc"
    );
    while($rowR = mysqli_fetch_array($queryRekening)) {
        if ($rowR['level']=='0') {
            $daftarRootByTipe[$rowR['tipe']][] = $rowR;
        } else if ($rowR['level']=='1' && $rowR['parent_kd_rek']!==null) {
            $daftarAnak[$rowR['parent_kd_rek']][] = $rowR;
        }
    }

    $pendapatan     = 0;
    $pendapatanHtml = bacaRekening('', true, 'R', 'K', 'KD', 0, $pendapatan, $daftarAnak, $daftarRootByTipe, $petaSaldoAwal, $petaDebet, $petaKredit);
    $biaya          = 0;
    $biayaHtml      = bacaRekening('', true, 'R', 'D', 'DK', 0, $biaya, $daftarAnak, $daftarRootByTipe, $petaSaldoAwal, $petaDebet, $petaKredit);
    $labaBersih     = $pendapatan - $biaya;
    $modal          = 0;
    $modalHtml      = bacaRekening('', true, 'M', '', 'KD', 0, $modal, $daftarAnak, $daftarRootByTipe, $petaSaldoAwal, $petaDebet, $petaKredit);
    $modalAkhir     = $modal + $labaBersih;
    $aktiva         = 0;
    $aktivaHtml     = bacaRekening('', true, 'N', 'D', 'DK', 0, $aktiva, $daftarAnak, $daftarRootByTipe, $petaSaldoAwal, $petaDebet, $petaKredit);
    $pasiva         = 0;
    $pasivaHtml     = bacaRekening('', true, 'N', 'K', 'KD', 0, $pasiva, $daftarAnak, $daftarRootByTipe, $petaSaldoAwal, $petaDebet, $petaKredit);
    $totalPasiva    = $pasiva + $modalAkhir;
?>
<div class="block-header">
    <h2><center>LAPORAN KEUANGAN</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <div class="row clearfix">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <label for="tahun_cari">Tahun</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="tahun_list" type="text" id="tahun_cari" name="tahun_cari" pattern="[0-9]{4}" title="YYYY" placeholder="Pilih Tahun" value="" size="60" maxlength="4" autocomplete="off" required/>
                                    <datalist id="tahun_list">
                                        <?php
                                            for($thn=$tahunsekarang;$thn>=2010;$thn--) {
                                                echo "<option>".$thn."</option>";
                                            }
                                        ?>
                                    </datalist>
                                </div>
                            </div>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Tampilkan Data</button></center>
                </form>
            </div>
            <div class="body" style="padding-top:0;">
                <ul class="nav nav-tabs tab-nav-right" role="tablist">
                    <li role="presentation" class="active"><a href="#tab_labarugi" data-toggle="tab">Laba Rugi</a></li>
                    <li role="presentation"><a href="#tab_perubahanmodal" data-toggle="tab">Perubahan Modal</a></li>
                    <li role="presentation"><a href="#tab_neraca" data-toggle="tab">Neraca</a></li>
                </ul>
                <div class="tab-content" style="padding-top:15px;">
                    <div role="tabpanel" class="tab-pane fade in active" id="tab_labarugi">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <tbody>
                                    <tr>
                                        <td colspan="3"><b>Pendapatan :</b></td>
                                    </tr>
                                    <tr style="background-color:#f5f5f5;">
                                        <td></td>
                                        <td><b>Nama Rekening</b></td>
                                        <td style="text-align:right;min-width:150px;white-space:nowrap;"><b>Saldo Akhir</b></td>
                                    </tr>
                                    <?=$pendapatanHtml;?>
                                    <tr style="background-color:#f5f5f5;">
                                        <td></td>
                                        <td><b>Total Pendapatan</b></td>
                                        <td style="text-align:right;"><b><?=number_format($pendapatan,0,',','.');?></b></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3"><b>Biaya-Biaya :</b></td>
                                    </tr>
                                    <tr style="background-color:#f5f5f5;">
                                        <td></td>
                                        <td><b>Nama Rekening</b></td>
                                        <td style="text-align:right;"><b>Saldo Akhir</b></td>
                                    </tr>
                                    <?=$biayaHtml;?>
                                    <tr style="background-color:#f5f5f5;">
                                        <td></td>
                                        <td><b>Total Biaya-Biaya</b></td>
                                        <td style="text-align:right;"><b><?=number_format($biaya,0,',','.');?></b></td>
                                    </tr>
                                    <tr style="background-color:#fce4e4;">
                                        <td></td>
                                        <td><b>Laba Bersih : Total Pendapatan - Total Biaya-Biaya</b></td>
                                        <td style="text-align:right;"><b><?=number_format($labaBersih,0,',','.');?></b></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane fade" id="tab_perubahanmodal">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <tbody>
                                    <tr>
                                        <td colspan="3"><b>Modal Awal :</b></td>
                                    </tr>
                                    <tr style="background-color:#f5f5f5;">
                                        <td></td>
                                        <td><b>Nama Rekening</b></td>
                                        <td style="text-align:right;min-width:150px;white-space:nowrap;"><b>Saldo Akhir</b></td>
                                    </tr>
                                    <?=$modalHtml;?>
                                    <tr style="background-color:#f5f5f5;">
                                        <td></td>
                                        <td><b>Total Modal</b></td>
                                        <td style="text-align:right;"><b><?=number_format($modal,0,',','.');?></b></td>
                                    </tr>
                                    <tr style="background-color:#fce4e4;">
                                        <td></td>
                                        <td><b>Modal Akhir : Laba Bersih + Total Modal</b></td>
                                        <td style="text-align:right;"><b><?=number_format($modalAkhir,0,',','.');?></b></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane fade" id="tab_neraca">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover">
                                <tbody>
                                    <tr>
                                        <td colspan="3"><b>Aktiva :</b></td>
                                    </tr>
                                    <tr style="background-color:#f5f5f5;">
                                        <td></td>
                                        <td><b>Nama Rekening</b></td>
                                        <td style="text-align:right;min-width:150px;white-space:nowrap;"><b>Saldo Akhir</b></td>
                                    </tr>
                                    <?=$aktivaHtml;?>
                                    <tr style="background-color:#f5f5f5;">
                                        <td></td>
                                        <td><b>Total Aktiva</b></td>
                                        <td style="text-align:right;"><b><?=number_format($aktiva,0,',','.');?></b></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3"><b>Pasiva :</b></td>
                                    </tr>
                                    <tr style="background-color:#f5f5f5;">
                                        <td></td>
                                        <td><b>Nama Rekening</b></td>
                                        <td style="text-align:right;"><b>Saldo Akhir</b></td>
                                    </tr>
                                    <?=$pasivaHtml;?>
                                    <tr style="background-color:#fce4e4;">
                                        <td></td>
                                        <td><b>Total Pasiva : Pasiva + Modal Akhir</b></td>
                                        <td style="text-align:right;"><b><?=number_format($totalPasiva,0,',','.');?></b></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>