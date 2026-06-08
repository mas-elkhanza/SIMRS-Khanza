<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>DATA PERMINTAAN KONSULTASI DOKTER</center></h2>
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
                                "SELECT date_format(konsultasi_medik.tanggal,'%d/%m/%Y %H:%i:%s') as tanggalperiksa,konsultasi_medik.no_permintaan,reg_periksa.no_rkm_medis,pasien.nm_pasien,dokterkonsul.nm_dokter as dokterkonsul FROM konsultasi_medik ".
                                "inner join reg_periksa on konsultasi_medik.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter as dokterkonsul on konsultasi_medik.kd_dokter=dokterkonsul.kd_dokter ".
                                "WHERE NOT EXISTS (SELECT 1 FROM jawaban_konsultasi_medik WHERE jawaban_konsultasi_medik.no_permintaan = konsultasi_medik.no_permintaan) AND konsultasi_medik.kd_dokter_dikonsuli='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."'"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggalperiksa"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_permintaan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rkm_medis"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nm_pasien"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["dokterkonsul"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=BalasKonsultasiDokter&iyem=".encrypt_decrypt("{\"nopermintaan\":\"".$rsqueryperiksa["no_permintaan"]."\"}","e")."' class='btn btn-warning waves-effect'>Lihat & Jawab</a></td>
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