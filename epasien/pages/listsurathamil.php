<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>SURAT KETERANGAN HAMIL/TIDAK HAMIL</center></h2>
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
                                <th width="30%"><center>Dokter Yang Memeriksa</center></th>
                                <th width="15%"><center>Tgl.Periksa</center></th>
                                <th width="35%"><center>Keterangan</center></th>
                                <th width="5%"><center>Surat</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querysurathamil = bukaquery("select surat_hamil.no_surat,surat_hamil.no_rawat,date_format(surat_hamil.tanggalperiksa,'%d/%m/%Y') as tanggalperiksa,surat_hamil.hasilperiksa,dokter.nm_dokter from surat_hamil inner join reg_periksa on surat_hamil.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on dokter.kd_dokter=reg_periksa.kd_dokter where reg_periksa.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");
                           while($rsquerysurathamil = mysqli_fetch_array($querysurathamil)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsquerysurathamil["no_surat"]."</td>
                                        <td align='left' valign='middle'>".$rsquerysurathamil["nm_dokter"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysurathamil["tanggalperiksa"]."</td>
                                        <td align='left' valign='middle'>".$rsquerysurathamil["hasilperiksa"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=TampilSuratHamil&iyem=".encrypt_decrypt("{\"nosurat\":\"".$rsquerysurathamil["no_surat"]."\"}","e")."' class='btn btn-warning waves-effect'>Tampilkan</a></td>
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