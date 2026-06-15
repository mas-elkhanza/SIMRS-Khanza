<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT LAYANAN PROGRAM KFR</center></h2>
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
                                <th width="35%"><center>Pogram</center></th>
                                <th width="23%"><center>Pemberi Pelayanan</center></th>
                                <th width="10%"><center>Detail</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryperiksa = bukaquery(
                                "select layanan_program_kfr.no_rawat,date_format(layanan_program_kfr.tanggal,'%d/%m/%Y %H:%i:%s') as tanggal,layanan_program_kfr.program,layanan_program_kfr.nip,".
                                "petugas.nama from layanan_program_kfr inner join petugas on layanan_program_kfr.nip=petugas.nip inner join reg_periksa on reg_periksa.no_rawat=layanan_program_kfr.no_rawat ".
                                "where reg_periksa.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' order by layanan_program_kfr.tanggal desc"
                            );
                            while($rsqueryperiksa = mysqli_fetch_array($queryperiksa)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["tanggal"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["no_rawat"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["program"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperiksa["nama"]."</td>".
                                        (getOne2("select count(bukti_layanan_program_kfr.no_rawat) from bukti_layanan_program_kfr where bukti_layanan_program_kfr.no_rawat='".$rsqueryperiksa["no_rawat"]."'")==0?
                                        "<td align='center' valign='middle'><a href='index.php?act=AmbilBuktiPelayananProgramKFR&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-warning waves-effect'>Ambil</a></td>":
                                        "<td align='center' valign='middle'><a href='index.php?act=HasilBuktiPelayananProgramKFR&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryperiksa["no_rawat"]."\"}","e")."' class='btn btn-danger waves-effect'>Lihat</a></td>").
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