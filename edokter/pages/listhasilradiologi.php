<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>DATA PEMERIKSAAN RADIOLOGI</center></h2>
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
                                <th width="15%"><center>No.Rawat</center></th>
                                <th width="12%"><center>No.RM</center></th>
                                <th width="24%"><center>Nama Pasien</center></th>
                                <th width="24%"><center>Dokter Perujuk</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $queryradiologi = bukaquery(
                                "SELECT date_format(periksa_radiologi.tgl_periksa,'%d/%m/%Y') as tanggalperiksa, periksa_radiologi.jam, ".
                                "periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,dokter.nm_dokter,periksa_radiologi.tgl_periksa ".
                                "FROM periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat ".
                                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis ".
                                "inner join dokter as dokter on periksa_radiologi.dokter_perujuk=dokter.kd_dokter ".
                                "WHERE NOT EXISTS (SELECT 1 FROM hasil_radiologi WHERE hasil_radiologi.no_rawat=periksa_radiologi.no_rawat ".
                                "AND hasil_radiologi.tgl_periksa=periksa_radiologi.tgl_periksa AND hasil_radiologi.jam=periksa_radiologi.jam) ".
                                "AND periksa_radiologi.kd_dokter='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."'"
                            );
                            while($rsradiologi = mysqli_fetch_array($queryradiologi)){
                                echo "<tr>
                                        <td align='center' valign='middle'>".$rsradiologi["tanggalperiksa"]." ".$rsradiologi["jam"]."</td>
                                        <td align='center' valign='middle'>".$rsradiologi["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsradiologi["no_rkm_medis"]."</td>
                                        <td align='center' valign='middle'>".$rsradiologi["nm_pasien"]."</td>
                                        <td align='center' valign='middle'>".$rsradiologi["nm_dokter"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=BacaanRadiologi&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsradiologi["no_rawat"]."\",\"tglperiksa\":\"".$rsradiologi["tgl_periksa"]."\",\"jam\":\"".$rsradiologi["jam"]."\"}","e")."' class='btn btn-warning waves-effect'>Lihat & Isi</a></td>
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