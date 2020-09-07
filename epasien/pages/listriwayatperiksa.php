<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT KUNJUNGAN/PEMERIKSAAN</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="9%"><center>Tanggal</center></th>
                                <th width="20%"><center>No.Rawat</center></th>
                                <th width="22%"><center>Poliklinik</center></th>
                                <th width="26%"><center>Dokter</center></th>
                                <th width="5%"><center>Status</center></th>
                                <th width="18%"><center>Ringkasan</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $queryperiksa = bukaquery("select reg_periksa.no_rawat,date_format(reg_periksa.tgl_registrasi,'%d/%m/%Y') as tgl_registrasi,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.status_lanjut from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");
                           while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tgl_registrasi"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rawat"]."</td>
                                        <td align='left' valign='middle'>".$rsqueryperiksa["nm_poli"]."</td>
                                        <td align='left' valign='middle'>".$rsqueryperiksa["nm_dokter"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["status_lanjut"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=CekResume&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-warning waves-effect'>Resume</a><a href='index.php?act=CekBilling&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-danger waves-effect' >Billing</a></td>
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