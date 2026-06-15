<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PERSETUJUAN RESTRAIN</center></h2>
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
                                <th width="19%"><center>Hasil Observasi</center></th>
                                <th width="20%"><center>P.J. Pasien</center></th>
                                <th width="19%"><center>Pertimbangan Klinis</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select pengkajian_restrain.no_rawat,date_format(pengkajian_restrain.tanggal,'%d/%m/%Y %H:%i:%s') as tanggalperiksa,pengkajian_restrain.keluarga_yang_menyetujui,pengkajian_restrain.hasil_observasi,pengkajian_restrain.pertimbangan_klinis,".
                                "ifnull(pengkajian_restrain_yang_menyetujui.photo,'') as photo from pengkajian_restrain inner join reg_periksa on pengkajian_restrain.no_rawat=reg_periksa.no_rawat ".
                                "left join pengkajian_restrain_yang_menyetujui on pengkajian_restrain.no_rawat=pengkajian_restrain_yang_menyetujui.no_rawat where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' ".
                                "order by pengkajian_restrain.tanggal desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggalperiksa"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["hasil_observasi"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["keluarga_yang_menyetujui"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["pertimbangan_klinis"]."</td>".
                                        ($rsqueryperiksa["photo"]==""?"<td align='center' valign='middle'><a href='index.php?act=AmbilPersetujuanRestrain&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilPersetujuanRestrain&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\",\"photo\":\"".$rsqueryperiksa["photo"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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