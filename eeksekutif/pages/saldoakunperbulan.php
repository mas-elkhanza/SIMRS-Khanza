<?php
    $namaBulan = ["01"=>"Januari","02"=>"Februari","03"=>"Maret","04"=>"April","05"=>"Mei","06"=>"Juni","07"=>"Juli","08"=>"Agustus","09"=>"September","10"=>"Oktober","11"=>"November","12"=>"Desember"];

    function bacaSaldoBulan($kdRekParent, $isRoot, $indent, $tahuncari, $namaBulan, &$daftarRoot, &$daftarAnak, &$petaSaldoAwal, &$petaDebetKredit) {
        $daftar = $isRoot ? $daftarRoot : (isset($daftarAnak[$kdRekParent]) ? $daftarAnak[$kdRekParent] : []);

        $html = '';
        foreach ($daftar as $rowRek) {
            $saldoAwal = isset($petaSaldoAwal[$rowRek['kd_rek']]) ? $petaSaldoAwal[$rowRek['kd_rek']] : 0;
            $indentPad = str_repeat("&nbsp;",$indent*3);
            $html .= "<tr><td style='white-space:nowrap;'>".$indentPad.$rowRek['kd_rek']."</td><td style='white-space:nowrap;'>".$indentPad.$rowRek['nm_rek']."</td>";
            foreach ($namaBulan as $bulanNum => $bulanNama) {
                $kunciBulan = $tahuncari."-".$bulanNum;
                $debet      = isset($petaDebetKredit[$rowRek['kd_rek']][$kunciBulan]['debet']) ? $petaDebetKredit[$rowRek['kd_rek']][$kunciBulan]['debet'] : 0;
                $kredit     = isset($petaDebetKredit[$rowRek['kd_rek']][$kunciBulan]['kredit']) ? $petaDebetKredit[$rowRek['kd_rek']][$kunciBulan]['kredit'] : 0;
                $saldoAkhir = $saldoAwal + ($debet - $kredit);
                $html .= "<td align='right' style='white-space:nowrap;'>".number_format($saldoAwal,0,',','.')."</td>";
                $html .= "<td align='right' style='white-space:nowrap;'>".number_format($debet,0,',','.')."</td>";
                $html .= "<td align='right' style='white-space:nowrap;'>".number_format($kredit,0,',','.')."</td>";
                $html .= "<td align='right' style='white-space:nowrap;'>".number_format($saldoAkhir,0,',','.')."</td>";
                $saldoAwal = $saldoAkhir;
            }
            $html .= "</tr>";
            $html .= bacaSaldoBulan($rowRek['kd_rek'], false, $indent+1, $tahuncari, $namaBulan, $daftarRoot, $daftarAnak, $petaSaldoAwal, $petaDebetKredit);
        }
        return $html;
    }

    $tahunsekarang = date("Y");
    $tahuncari     = $tahunsekarang;
    if(isset($_POST["BtnCari"])){
        $tahuncari = validTeks(trim(isset($_POST['tahun_cari']))?$_POST['tahun_cari']:$tahunsekarang);
    }

    $petaSaldoAwal  = [];
    $querySaldoAwal = bukaquery("select rekeningtahun.kd_rek,rekeningtahun.saldo_awal from rekeningtahun where rekeningtahun.thn='".$tahuncari."'");
    while($rowSaldo = mysqli_fetch_array($querySaldoAwal)) {
        $petaSaldoAwal[$rowSaldo['kd_rek']] = (float) $rowSaldo['saldo_awal'];
    }

    $petaDebetKredit  = [];
    $queryDebetKredit = bukaquery(
        "select detailjurnal.kd_rek,left(jurnal.tgl_jurnal,7) as bulantahun,sum(detailjurnal.debet) as totaldebet,sum(detailjurnal.kredit) as totalkredit ".
        "from jurnal inner join detailjurnal on detailjurnal.no_jurnal=jurnal.no_jurnal where left(jurnal.tgl_jurnal,4)='".$tahuncari."' ".
        "group by detailjurnal.kd_rek,left(jurnal.tgl_jurnal,7)"
    );
    while($rowDK = mysqli_fetch_array($queryDebetKredit)) {
        $petaDebetKredit[$rowDK['kd_rek']][$rowDK['bulantahun']] = ['debet'=>(float)$rowDK['totaldebet'],'kredit'=>(float)$rowDK['totalkredit']];
    }

    $daftarRoot    = [];
    $daftarAnak    = [];
    $queryRekening = bukaquery("select rekening.kd_rek,rekening.nm_rek,rekening.level,subrekening.kd_rek as parent_kd_rek from rekening left join subrekening on rekening.kd_rek=subrekening.kd_rek2 order by rekening.kd_rek asc");
    while($rowR = mysqli_fetch_array($queryRekening)) {
        if ($rowR['level']=='0') {
            $daftarRoot[] = $rowR;
        } else if ($rowR['level']=='1' && $rowR['parent_kd_rek']!==null) {
            $daftarAnak[$rowR['parent_kd_rek']][] = $rowR;
        }
    }

    $isiTabel = bacaSaldoBulan('', true, 0, $tahuncari, $namaBulan, $daftarRoot, $daftarAnak, $petaSaldoAwal, $petaDebetKredit);
?>
<div class="block-header">
    <h2><center>SALDO AKUN PER BULAN</center></h2>
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
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th rowspan="2" style="min-width:100px;white-space:nowrap;"><center>Kode Akun</center></th>
                                <th rowspan="2" style="min-width:280px;white-space:nowrap;"><center>Akun Rekening</center></th>
                                <?php
                                    foreach ($namaBulan as $bulanNum => $bulanNama) {
                                        echo "<th colspan='4'><center>".$bulanNama."</center></th>";
                                    }
                                ?>
                            </tr>
                            <tr>
                                <?php
                                    foreach ($namaBulan as $bulanNum => $bulanNama) {
                                        echo "<th style='min-width:110px;white-space:nowrap;'><center>Saldo Awal</center></th>";
                                        echo "<th style='min-width:110px;white-space:nowrap;'><center>Debet</center></th>";
                                        echo "<th style='min-width:110px;white-space:nowrap;'><center>Kredit</center></th>";
                                        echo "<th style='min-width:110px;white-space:nowrap;'><center>Saldo Akhir</center></th>";
                                    }
                                ?>
                            </tr>
                        </thead>
                        <tbody>
                            <?=$isiTabel;?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>