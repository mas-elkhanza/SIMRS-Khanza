<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT PELAKSANAAN INFORMASI & EDUKASI</center></h2>
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
                                <th width="35%"><center>Materi</center></th>
                                <th width="23%"><center>Pemberi Edukasi</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select pelaksanaan_informasi_edukasi.no_rawat,date_format(pelaksanaan_informasi_edukasi.tanggal,'%d/%m/%Y %H:%i:%s') as tanggaledukasi,pelaksanaan_informasi_edukasi.materi_edukasi,pelaksanaan_informasi_edukasi.nik,pegawai.nama,".
                                "pelaksanaan_informasi_edukasi.tanggal from pelaksanaan_informasi_edukasi inner join pegawai on pelaksanaan_informasi_edukasi.nik=pegawai.nik inner join reg_periksa on reg_periksa.no_rawat=pelaksanaan_informasi_edukasi.no_rawat ".
                                "where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' order by pelaksanaan_informasi_edukasi.tanggal desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggaledukasi"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["materi_edukasi"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nama"]."</td>".
                                        (getOne2("select count(bukti_pelaksanaan_informasi_edukasi.no_rawat) from bukti_pelaksanaan_informasi_edukasi where bukti_pelaksanaan_informasi_edukasi.no_rawat='".$rsqueryperiksa["no_rawat"]."' and bukti_pelaksanaan_informasi_edukasi.tanggal='".$rsqueryperiksa["tanggal"]."'")==0?
                                        "<td align='center' valign='middle'><a href='index.php?act=AmbilBuktiPelaksanaanEdukasi&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\",\"tanggal\":\"".$rsqueryperiksa["tanggal"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilBuktiPelaksanaanEdukasi&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\",\"tanggal\":\"".$rsqueryperiksa["tanggal"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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