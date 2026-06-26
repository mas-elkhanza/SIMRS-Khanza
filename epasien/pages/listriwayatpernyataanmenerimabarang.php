<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT SERAH TERIMA BARANG / ANGGOTA TUBUH</center></h2>
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
                                <th width="63%"><center>Petugas/Pengambil Pernyataan</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select surat_serah_terima_barang_anggota_tubuh.no_pernyataan,date_format(surat_serah_terima_barang_anggota_tubuh.tanggal,'%d/%m/%Y') as tanggalpernyataan,surat_serah_terima_barang_anggota_tubuh.nip,pegawai.nama,ifnull(surat_serah_terima_barang_anggota_tubuh_bukti.photo,'') as photo from surat_serah_terima_barang_anggota_tubuh ".
                                "inner join reg_periksa on surat_serah_terima_barang_anggota_tubuh.no_rawat=reg_periksa.no_rawat inner join pegawai on surat_serah_terima_barang_anggota_tubuh.nip=pegawai.nik left join surat_serah_terima_barang_anggota_tubuh_bukti on surat_serah_terima_barang_anggota_tubuh_bukti.no_pernyataan=surat_serah_terima_barang_anggota_tubuh.no_pernyataan where ".
                                "reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' ".
                                "order by surat_serah_terima_barang_anggota_tubuh.tanggal desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggalpernyataan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_pernyataan"]."</td>
                                        <td align='left' valign='middle'>".$rsqueryperiksa["nip"]." ".$rsqueryperiksa["nama"]."</td>".
                                        ($rsqueryperiksa["photo"]==""?"<td align='center' valign='middle'><a href='index.php?act=AmbilPernyataanMenerimaBarang&iyem=".encrypt_decrypt("{\"nopernyataan\":\"".$rsqueryperiksa["no_pernyataan"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilPernyataanMenerimaBarang&iyem=".encrypt_decrypt("{\"nopernyataan\":\"".$rsqueryperiksa["no_pernyataan"]."\",\"photo\":\"".$rsqueryperiksa["photo"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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