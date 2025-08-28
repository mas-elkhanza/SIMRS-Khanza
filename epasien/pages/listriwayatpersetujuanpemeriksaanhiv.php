<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PERSETUJUAN PEMERIKSAAN HIV</center></h2>
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
                                <th width="17%"><center>No.Persetujuan</center></th>
                                <th width="63%"><center>Petugas/Pengambil Persetujuan</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select surat_persetujuan_pemeriksaan_hiv.no_surat,date_format(surat_persetujuan_pemeriksaan_hiv.tanggal,'%d/%m/%Y') as tanggalperiksa,surat_persetujuan_pemeriksaan_hiv.nik,pegawai.nama,ifnull(surat_persetujuan_pemeriksaan_hiv_pembuat_persetujuan.photo,'') as photo from surat_persetujuan_pemeriksaan_hiv ".
                                "inner join reg_periksa on surat_persetujuan_pemeriksaan_hiv.no_rawat=reg_periksa.no_rawat inner join pegawai on surat_persetujuan_pemeriksaan_hiv.nik=pegawai.nik left join surat_persetujuan_pemeriksaan_hiv_pembuat_persetujuan on surat_persetujuan_pemeriksaan_hiv_pembuat_persetujuan.no_surat=surat_persetujuan_pemeriksaan_hiv.no_surat where ".
                                "reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' ".
                                "order by surat_persetujuan_pemeriksaan_hiv.tanggal desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggalperiksa"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_surat"]."</td>
                                        <td align='left' valign='middle'>".$rsqueryperiksa["nik"]." ".$rsqueryperiksa["nama"]."</td>".
                                        ($rsqueryperiksa["photo"]==""?"<td align='center' valign='middle'><a href='index.php?act=AmbilPersetujuanPemeriksaanHIV&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$rsqueryperiksa["no_surat"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilPersetujuanPemeriksaanHIV&iyem=".encrypt_decrypt("{\"nopersetujuan\":\"".$rsqueryperiksa["no_surat"]."\",\"photo\":\"".$rsqueryperiksa["photo"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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