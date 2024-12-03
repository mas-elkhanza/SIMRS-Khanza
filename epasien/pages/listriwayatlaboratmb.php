<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PEMERIKSAAN LAB MB</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="20%"><center>Tanggal & Jam</center></th>
                                <th width="17%"><center>No.Rawat</center></th>
                                <th width="20%"><center>Cara Bayar</center></th>
                                <th width="33%"><center>Dokter Perujuk</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select periksa_lab.no_rawat,date_format(periksa_lab.tgl_periksa,'%d/%m/%Y') as tanggal,periksa_lab.tgl_periksa,periksa_lab.jam,dokter.nm_dokter,penjab.png_jawab from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj ".
                                "inner join dokter on periksa_lab.dokter_perujuk=dokter.kd_dokter where periksa_lab.kategori='MB' and reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) order by periksa_lab.tgl_periksa desc,periksa_lab.jam desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggal"]." ".$rsqueryperiksa["jam"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["png_jawab"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nm_dokter"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=HasilLabMB&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\",\"tglperiksa\":\"".$rsqueryperiksa["tgl_periksa"]."\",\"jam\":\"".$rsqueryperiksa["jam"]."\"}","e")."' class='btn btn-danger waves-effect'>Hasil</a></td>
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