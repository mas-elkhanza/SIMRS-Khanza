<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT RENCANA PEMULANGAN</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="10%"><center>Tanggal</center></th>
                                <th width="17%"><center>No.Rawat</center></th>
                                <th width="20%"><center>Alasan Masuk</center></th>
                                <th width="23%"><center>P.J. Pasien</center></th>
                                <th width="20%"><center>Diagnosa Medis</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select perencanaan_pemulangan.no_rawat,date_format(perencanaan_pemulangan.rencana_pulang,'%d/%m/%Y') as rencana_pulang,perencanaan_pemulangan.nama_pasien_keluarga,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis from perencanaan_pemulangan ".
                                "inner join reg_periksa on perencanaan_pemulangan.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' order by perencanaan_pemulangan.rencana_pulang desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["rencana_pulang"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["alasan_masuk"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nama_pasien_keluarga"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["diagnosa_medis"]."</td>".
                                        (getOne2("select count(bukti_perencanaan_pemulangan_saksikeluarga.no_rawat) from bukti_perencanaan_pemulangan_saksikeluarga where bukti_perencanaan_pemulangan_saksikeluarga.no_rawat='".$rsqueryperiksa["no_rawat"]."'")==0?
                                        "<td align='center' valign='middle'><a href='index.php?act=AmbilPersetujuanRencanaPemulangan&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilPersetujuanRencanaPemulangan&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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