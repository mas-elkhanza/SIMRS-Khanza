<?php
    $namaBulan = ["01"=>"Januari","02"=>"Februari","03"=>"Maret","04"=>"April","05"=>"Mei","06"=>"Juni","07"=>"Juli","08"=>"Agustus","09"=>"September","10"=>"Oktober","11"=>"November","12"=>"Desember"];

    function bacaSaldoBulan($kdRekParent, $isRoot, $indent, $tahuncari, $namaBulan) {
        if ($isRoot) {
            $query = "select rekening.kd_rek,rekening.nm_rek from rekening where rekening.level='0' order by rekening.kd_rek asc";
        } else {
            $query = "select rekening.kd_rek,rekening.nm_rek from rekening inner join subrekening on rekening.kd_rek=subrekening.kd_rek2 where subrekening.kd_rek='".$kdRekParent."' and rekening.level='1' order by rekening.kd_rek asc";
        }
        $html     = '';
        $queryRek = bukaquery($query);
        while ($rowRek = mysqli_fetch_array($queryRek)) {
            $saldoAwal = (float) getOne("select saldo_awal from rekeningtahun where thn='".$tahuncari."' and kd_rek='".$rowRek['kd_rek']."'");
            $indentPad = str_repeat("&nbsp;",$indent*3);
            $html .= "<tr><td style='white-space:nowrap;'>".$indentPad.$rowRek['kd_rek']."</td><td style='white-space:nowrap;'>".$indentPad.$rowRek['nm_rek']."</td>";
            foreach ($namaBulan as $bulanNum => $bulanNama) {
                $debet      = (float) getOne("select sum(detailjurnal.debet) from jurnal inner join detailjurnal on detailjurnal.no_jurnal=jurnal.no_jurnal where jurnal.tgl_jurnal like '%".$tahuncari."-".$bulanNum."%' and detailjurnal.kd_rek='".$rowRek['kd_rek']."'");
                $kredit     = (float) getOne("select sum(detailjurnal.kredit) from jurnal inner join detailjurnal on detailjurnal.no_jurnal=jurnal.no_jurnal where jurnal.tgl_jurnal like '%".$tahuncari."-".$bulanNum."%' and detailjurnal.kd_rek='".$rowRek['kd_rek']."'");
                $saldoAkhir = $saldoAwal + ($debet - $kredit);
                $html .= "<td align='right' style='white-space:nowrap;'>".number_format($saldoAwal,0,',','.')."</td>";
                $html .= "<td align='right' style='white-space:nowrap;'>".number_format($debet,0,',','.')."</td>";
                $html .= "<td align='right' style='white-space:nowrap;'>".number_format($kredit,0,',','.')."</td>";
                $html .= "<td align='right' style='white-space:nowrap;'>".number_format($saldoAkhir,0,',','.')."</td>";
                $saldoAwal  = $saldoAkhir;
            }
            $html .= "</tr>";
            $html .= bacaSaldoBulan($rowRek['kd_rek'], false, $indent+1, $tahuncari, $namaBulan);
        }
        return $html;
    }

    $tahunsekarang = date("Y");
    $tahuncari     = $tahunsekarang;
    if(isset($_POST["BtnCari"])){
        $tahuncari = validTeks(trim(isset($_POST['tahun_cari']))?$_POST['tahun_cari']:$tahunsekarang);
    }

    $isiTabel      = bacaSaldoBulan('', true, 0, $tahuncari, $namaBulan);
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