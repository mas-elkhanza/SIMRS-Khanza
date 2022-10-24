<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>SURAT CUTI SAKIT</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="15%"><center>No.Surat</center></th>
                                <th width="40%"><center>Dokter Yang Merawat</center></th>
                                <th width="15%"><center>Tgl.Mulai</center></th>
                                <th width="15%"><center>Tgl.Selesai</center></th>
                                <th width="10%"><center>Lamanya</center></th>
                                <th width="5%"><center>Surat</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querysuratsakit = bukaquery("select suratsakit.no_surat,suratsakit.no_rawat,date_format(suratsakit.tanggalawal,'%d/%m/%Y') as tanggalawal,date_format(suratsakit.tanggalakhir,'%d/%m/%Y') as tanggalakhir,suratsakit.lamasakit,dokter.nm_dokter from suratsakit inner join reg_periksa on suratsakit.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on dokter.kd_dokter=reg_periksa.kd_dokter where reg_periksa.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");
                           while($rsquerysuratsakit = mysqli_fetch_array($querysuratsakit)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsquerysuratsakit["no_surat"]."</td>
                                        <td align='left' valign='middle'>".$rsquerysuratsakit["nm_dokter"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratsakit["tanggalawal"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratsakit["tanggalakhir"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratsakit["lamasakit"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=TampilSuratSakit&iyem=".encrypt_decrypt("{\"nosurat\":\"".$rsquerysuratsakit["no_surat"]."\"}","e")."' class='btn btn-warning waves-effect'>Tampilkan</a></td>
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