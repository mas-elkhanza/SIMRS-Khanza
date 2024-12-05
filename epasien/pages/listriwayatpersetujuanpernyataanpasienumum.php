<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PERNYATAAN PASIEN UMUM</center></h2>
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
                                <th width="17%"><center>Nomor KTP P.J.</center></th>
                                <th width="30%"><center>P.J. Pasien</center></th>
                                <th width="16%"><center>No.Telp P.J.</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select surat_pernyataan_pasien_umum.no_surat,date_format(surat_pernyataan_pasien_umum.tanggal,'%d/%m/%Y') as tanggalperiksa,surat_pernyataan_pasien_umum.nama_pj,surat_pernyataan_pasien_umum.no_ktppj,surat_pernyataan_pasien_umum.no_telp from surat_pernyataan_pasien_umum ".
                                "inner join reg_periksa on surat_pernyataan_pasien_umum.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' order by surat_pernyataan_pasien_umum.tanggal desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggalperiksa"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_surat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_ktppj"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nama_pj"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_telp"]."</td>".
                                        (getOne2("select count(surat_pernyataan_pasien_umum_pembuat_pernyataan.no_surat) from surat_pernyataan_pasien_umum_pembuat_pernyataan where surat_pernyataan_pasien_umum_pembuat_pernyataan.no_surat='".$rsqueryperiksa["no_surat"]."'")==0?
                                        "<td align='center' valign='middle'><a href='index.php?act=AmbilPersetujuanPernyataanPasienUmum&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$rsqueryperiksa["no_surat"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilPersetujuanPernyataanPasienUmum&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$rsqueryperiksa["no_surat"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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