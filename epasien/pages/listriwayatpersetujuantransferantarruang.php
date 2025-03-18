<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PERSETUJUAN TRANSFER ANTAR RUANG</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="15%"><center>Pindah</center></th>
                                <th width="17%"><center>No.Rawat</center></th>
                                <th width="15%"><center>Indikasi Pindah</center></th>
                                <th width="23%"><center>P.J. Pasien</center></th>
                                <th width="20%"><center>Diagnosa Utama</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select transfer_pasien_antar_ruang.no_rawat,date_format(transfer_pasien_antar_ruang.tanggal_pindah,'%d/%m/%Y %H:%m:%s') as tanggal,transfer_pasien_antar_ruang.tanggal_masuk,transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.indikasi_pindah_ruang,".
                                "transfer_pasien_antar_ruang.diagnosa_utama from transfer_pasien_antar_ruang inner join reg_periksa on transfer_pasien_antar_ruang.no_rawat=reg_periksa.no_rawat where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' ".
                                "order by transfer_pasien_antar_ruang.tanggal_masuk desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggal"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["indikasi_pindah_ruang"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nama_menyetujui"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["diagnosa_utama"]."</td>".
                                        (getOne2("select count(bukti_persetujuan_transfer_pasien_antar_ruang.no_rawat) from bukti_persetujuan_transfer_pasien_antar_ruang where bukti_persetujuan_transfer_pasien_antar_ruang.no_rawat='".$rsqueryperiksa["no_rawat"]."' and bukti_persetujuan_transfer_pasien_antar_ruang.tanggal_masuk='".$rsqueryperiksa["tanggal_masuk"]."'")==0?
                                        "<td align='center' valign='middle'><a href='index.php?act=AmbilPersetujuanTransferAntarRuang&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\",\"tanggal\":\"".$rsqueryperiksa["tanggal_masuk"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilPersetujuanTransferAntarRuang&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\",\"tanggal\":\"".$rsqueryperiksa["tanggal_masuk"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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