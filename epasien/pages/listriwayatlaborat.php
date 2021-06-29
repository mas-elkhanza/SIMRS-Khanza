<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PERMINTAAN LAB</center></h2>
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
                                <th width="17%"><center>No. Permintaan</center></th>
                                <th width="20%"><center>Cara Bayar</center></th>
                                <th width="33%"><center>Dokter Perujuk</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $queryperiksa = bukaquery("select permintaan_lab.noorder,permintaan_lab.no_rawat,date_format(permintaan_lab.tgl_permintaan,'%d/%m/%Y') as tanggal,permintaan_lab.jam_permintaan,dokter.nm_dokter,permintaan_lab.diagnosa_klinis,penjab.png_jawab,permintaan_lab.tgl_hasil from permintaan_lab inner join dokter on permintaan_lab.dokter_perujuk=dokter.kd_dokter inner join reg_periksa on reg_periksa.no_rawat=permintaan_lab.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");
                           while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggal"]." ".$rsqueryperiksa["jam_permintaan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["noorder"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["png_jawab"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nm_dokter"]."</td>
                                        <td align='center' valign='middle'>";
                               if($rsqueryperiksa["tgl_hasil"]=="0000-00-00"){
                                   echo "<a href='index.php?act=CekRiwayatLab&iyem=".encrypt_decrypt("{\"noorder\":\"".$rsqueryperiksa["noorder"]."\"}","e")."' class='btn btn-warning waves-effect'>Permintaan</a>";
                               }else{
                                   echo "<a href='index.php?act=CekHasilLab&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\",\"noorder\":\"".$rsqueryperiksa["noorder"]."\"}","e")."' class='btn btn-danger waves-effect' >Hasil</a>";
                               }
                               echo "   </td>
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