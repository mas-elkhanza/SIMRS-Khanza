<?php
    $tahunsekarang = date("Y");
    $tahuncari     = $tahunsekarang;
    if(isset($_POST["BtnCari"])){
        $tahuncari = validTeks(trim(isset($_POST['tahun_cari']))?$_POST['tahun_cari']:$tahunsekarang);
    }
?>
<div class="block-header">
    <h2><center>REKAPITULASI SALDO & MUTASI REKENING PER TAHUN</center></h2>
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
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:70px;white-space:nowrap;"><center>Tahun</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Kode Rekening</center></th>
                                <th style="min-width:220px;"><center>Nama Rekening</center></th>
                                <th style="min-width:60px;white-space:nowrap;"><center>Tipe</center></th>
                                <th style="min-width:70px;white-space:nowrap;"><center>Balance</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Saldo Awal</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Mutasi Debet</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Mutasi Kredit</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Saldo Akhir</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $queryRekening   = bukaquery("select rekeningtahun.thn,rekening.kd_rek,rekening.nm_rek,rekening.tipe,rekening.balance,rekeningtahun.saldo_awal from rekening inner join rekeningtahun on rekeningtahun.kd_rek=rekening.kd_rek where rekeningtahun.thn='".$tahuncari."' order by rekening.kd_rek asc");
                            while($rowRekening = mysqli_fetch_array($queryRekening)) {
                                $mutasi      = bukaquery("select sum(detailjurnal.debet) as totaldebet,sum(detailjurnal.kredit) as totalkredit from jurnal inner join detailjurnal on detailjurnal.no_jurnal=jurnal.no_jurnal where detailjurnal.kd_rek='".$rowRekening['kd_rek']."' and jurnal.tgl_jurnal like '%".$tahuncari."%'");
                                $rowMutasi   = mysqli_fetch_array($mutasi);
                                $totalDebet  = (float) $rowMutasi['totaldebet'];
                                $totalKredit = (float) $rowMutasi['totalkredit'];
                                if ($rowRekening['balance'] === 'D') {
                                    $md = $totalDebet;
                                    $mk = $totalKredit;
                                } else {
                                    $md = $totalKredit;
                                    $mk = $totalDebet;
                                }
                                $saldoAkhir       = $rowRekening['saldo_awal'] + ($md - $mk);
                                $saldoAkhirTampil = $saldoAkhir < 0 ? "(".number_format($saldoAkhir*-1,0,',','.').")" : number_format($saldoAkhir,0,',','.');
                                echo "<tr>
                                        <td align='center' style='white-space:nowrap;'>".$rowRekening['thn']."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rowRekening['kd_rek']."</td>
                                        <td align='left'>".$rowRekening['nm_rek']."</td>
                                        <td align='center' style='white-space:nowrap;'>".$rowRekening['tipe']."</td>
                                        <td align='center' style='white-space:nowrap;'>".$rowRekening['balance']."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rowRekening['saldo_awal'],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($md,0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($mk,0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".$saldoAkhirTampil."</td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>