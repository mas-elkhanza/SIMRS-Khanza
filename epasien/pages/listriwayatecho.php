<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT ECHOCARDIOGRAFI</center></h2>
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
                                <th width="53%"><center>Cara Bayar</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select hasil_pemeriksaan_echo.no_rawat,date_format(hasil_pemeriksaan_echo.tanggal,'%d/%m/%Y %H:%i:%s') as tanggalperiksa,hasil_pemeriksaan_echo.tanggal,penjab.png_jawab from hasil_pemeriksaan_echo inner join reg_periksa ".
                                "on hasil_pemeriksaan_echo.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' order by hasil_pemeriksaan_echo.tanggal desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggalperiksa"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["png_jawab"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=HasilEcho&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-danger waves-effect'>Hasil</a></td>
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