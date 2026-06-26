<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PENOLAKAN RESUSITASI</center></h2>
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
                                <th width="17%"><center>No.Penolakan</center></th>
                                <th width="25%"><center>P.J. Pasien</center></th>
                                <th width="13%"><center>Hubungan</center></th>
                                <th width="25%"><center>Saksi Keluarga</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select surat_penolakan_resusitasi.no_pernyataan,date_format(surat_penolakan_resusitasi.tanggal,'%d/%m/%Y') as tanggalperiksa,surat_penolakan_resusitasi.pembuat_pernyataan,surat_penolakan_resusitasi.hubungan_pembuat_pernyataan,surat_penolakan_resusitasi.saksi_keluarga,".
                                "ifnull(bukti_surat_penolakan_resusitasi.photo,'') as photo,ifnull(bukti_surat_penolakan_resusitasi_saksikeluarga.photo,'') as photo2 from surat_penolakan_resusitasi inner join reg_periksa on surat_penolakan_resusitasi.no_rawat=reg_periksa.no_rawat ".
                                "left join bukti_surat_penolakan_resusitasi on bukti_surat_penolakan_resusitasi.no_pernyataan=surat_penolakan_resusitasi.no_pernyataan ".
                                "left join bukti_surat_penolakan_resusitasi_saksikeluarga on bukti_surat_penolakan_resusitasi_saksikeluarga.no_pernyataan=surat_penolakan_resusitasi.no_pernyataan ".
                                "where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' order by surat_penolakan_resusitasi.tanggal desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggalperiksa"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_pernyataan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["pembuat_pernyataan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["hubungan_pembuat_pernyataan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["saksi_keluarga"]."</td>".
                                        (($rsqueryperiksa["photo"]=="")||(($rsqueryperiksa["photo2"]==""))?
                                        "<td align='center' valign='middle'><a href='index.php?act=AmbilPenolakanResusitasi&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$rsqueryperiksa["no_pernyataan"]."\",\"photo\":\"".$rsqueryperiksa["photo"]."\",\"photo2\":\"".$rsqueryperiksa["photo2"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilPenolakanResusitasi&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$rsqueryperiksa["no_pernyataan"]."\",\"photo\":\"".$rsqueryperiksa["photo"]."\",\"photo2\":\"".$rsqueryperiksa["photo2"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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