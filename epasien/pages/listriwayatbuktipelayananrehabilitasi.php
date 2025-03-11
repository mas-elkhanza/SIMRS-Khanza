<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT LAYANAN KEDOKTERAN FISIK & REHABILITASI</center></h2>
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
                                <th width="17%"><center>No.Rawat</center></th>
                                <th width="35%"><center>Diagnosa Medis</center></th>
                                <th width="23%"><center>Pemberi Pelayanan</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select layanan_kedokteran_fisik_rehabilitasi.no_rawat,date_format(layanan_kedokteran_fisik_rehabilitasi.tanggal,'%d/%m/%Y %H:%i:%s') as tanggal,layanan_kedokteran_fisik_rehabilitasi.diagnosa_medis,layanan_kedokteran_fisik_rehabilitasi.kd_dokter,".
                                "dokter.nm_dokter from layanan_kedokteran_fisik_rehabilitasi inner join dokter on layanan_kedokteran_fisik_rehabilitasi.kd_dokter=dokter.kd_dokter inner join reg_periksa on reg_periksa.no_rawat=layanan_kedokteran_fisik_rehabilitasi.no_rawat ".
                                "where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' order by layanan_kedokteran_fisik_rehabilitasi.tanggal desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggal"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["diagnosa_medis"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nm_dokter"]."</td>".
                                        (getOne2("select count(bukti_layanan_kedokteran_fisik_rehabilitasi.no_rawat) from bukti_layanan_kedokteran_fisik_rehabilitasi where bukti_layanan_kedokteran_fisik_rehabilitasi.no_rawat='".$rsqueryperiksa["no_rawat"]."'")==0?
                                        "<td align='center' valign='middle'><a href='index.php?act=AmbilBuktiPelayananRehabilitasi&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilBuktiPelayananRehabilitasi&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
                                    "</tr>";
                            }
                        ?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>