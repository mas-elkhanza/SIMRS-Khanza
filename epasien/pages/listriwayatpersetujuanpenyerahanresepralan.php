<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PENYERAHAN RESEP RAWAT JALAN</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="18%"><center>Waktu Peresepan</center></th>
                                <th width="15%"><center>No.Resep</center></th>
                                <th width="25%"><center>Dokter</center></th>
                                <th width="15%"><center>No.Rawat</center></th>
                                <th width="17%"><center>Waktu Validasi</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryresep = bukaquery(
                                "select resep_obat.no_rawat,date_format(resep_obat.tgl_perawatan,'%d/%m/%Y') as tgl_perawatan,resep_obat.jam,dokter.nm_dokter,resep_obat.no_resep,date_format(resep_obat.tgl_peresepan,'%d/%m/%Y') as tgl_peresepan,resep_obat.jam_peresepan,resep_obat.tgl_penyerahan ".
                                "from resep_obat inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter where resep_obat.status='ralan' and reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' ".
                                "order by resep_obat.tgl_perawatan desc"
                            );
                            while($rsqueryresep = mysqli_fetch_array($queryresep)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryresep["tgl_peresepan"]." ".$rsqueryresep["jam_peresepan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryresep["no_resep"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryresep["nm_dokter"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryresep["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryresep["tgl_perawatan"]." ".$rsqueryresep["jam"]."</td>".
                                        ($rsqueryresep["tgl_penyerahan"]=="0000-00-00"?
                                        "<td align='center' valign='middle'><a href='index.php?act=AmbilPenyerahanResepRalan&iyem=".encrypt_decrypt("{\"noresep\":\"".$rsqueryresep["no_resep"]."\",\"norawat\":\"".$rsqueryresep["no_rawat"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilPenyerahanResepRalan&iyem=".encrypt_decrypt("{\"noresep\":\"".$rsqueryresep["no_resep"]."\",\"norawat\":\"".$rsqueryresep["no_rawat"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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