<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PERNYATAAN PULANG ATAS PERMINTAAN SENDIRI</center></h2>
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
                                <th width="17%"><center>No.Pernyataan</center></th>
                                <th width="25%"><center>P.J. Pasien</center></th>
                                <th width="13%"><center>Hubungan</center></th>
                                <th width="25%"><center>Saksi Keluarga</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select surat_pulang_atas_permintaan_sendiri.no_surat,date_format(surat_pulang_atas_permintaan_sendiri.tgl_pulang,'%d/%m/%Y') as tgl_pulangperiksa,surat_pulang_atas_permintaan_sendiri.nama_pj,surat_pulang_atas_permintaan_sendiri.hubungan,surat_pulang_atas_permintaan_sendiri.saksi_keluarga,".
                                "ifnull(surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan.photo,'') as photo,ifnull(surat_pulang_atas_permintaan_sendiri_saksi_keluarga.photo,'') as photo2 from surat_pulang_atas_permintaan_sendiri inner join reg_periksa on surat_pulang_atas_permintaan_sendiri.no_rawat=reg_periksa.no_rawat ".
                                "left join surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan on surat_pulang_atas_permintaan_sendiri_pembuat_pernyataan.no_surat=surat_pulang_atas_permintaan_sendiri.no_surat ".
                                "left join surat_pulang_atas_permintaan_sendiri_saksi_keluarga on surat_pulang_atas_permintaan_sendiri_saksi_keluarga.no_surat=surat_pulang_atas_permintaan_sendiri.no_surat ".
                                "where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' order by surat_pulang_atas_permintaan_sendiri.tgl_pulang desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tgl_pulangperiksa"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_surat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nama_pj"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["hubungan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["saksi_keluarga"]."</td>".
                                        (($rsqueryperiksa["photo"]=="")||(($rsqueryperiksa["photo2"]==""))?
                                        "<td align='center' valign='middle'><a href='index.php?act=AmbilPersetujuanPernyataanPulangAPS&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$rsqueryperiksa["no_surat"]."\",\"photo\":\"".$rsqueryperiksa["photo"]."\",\"photo2\":\"".$rsqueryperiksa["photo2"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilPersetujuanPernyataanPulangAPS&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$rsqueryperiksa["no_surat"]."\",\"photo\":\"".$rsqueryperiksa["photo"]."\",\"photo2\":\"".$rsqueryperiksa["photo2"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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