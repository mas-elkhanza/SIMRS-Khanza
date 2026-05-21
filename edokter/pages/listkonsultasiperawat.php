<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>DATA PERMINTAAN KONSULTASI PERAWAT</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="15%"><center>Tanggal</center></th>
                                <th width="15%"><center>No.Permintaan</center></th>
                                <th width="12%"><center>No.RM</center></th>
                                <th width="24%"><center>Nama Pasien</center></th>
                                <th width="24%"><center>Dokter Konsul</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "SELECT date_format(konsultasi_perawat.tanggal,'%d/%m/%Y %H:%i:%s') as tanggalperiksa,konsultasi_perawat.no_permintaan,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama FROM konsultasi_perawat ".
                                "inner join reg_periksa on konsultasi_perawat.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on konsultasi_perawat.nip=petugas.nip ".
                                "WHERE NOT EXISTS (SELECT 1 FROM jawaban_konsultasi_perawat WHERE jawaban_konsultasi_perawat.no_permintaan = konsultasi_perawat.no_permintaan) AND konsultasi_perawat.kd_dokter_dikonsuli='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."'"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggalperiksa"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_permintaan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rkm_medis"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nm_pasien"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nama"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=BalasKonsultasiPerawat&iyem=".encrypt_decrypt("{\"nopermintaan\":\"".$rsqueryperiksa["no_permintaan"]."\"}","e")."' class='btn btn-warning waves-effect'>Lihat & Jawab</a></td>
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