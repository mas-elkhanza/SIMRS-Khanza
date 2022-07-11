<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>SURAT KETERANGAN RAPID TEST</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="100px"><center>No.Surat</center></th>
                                <th width="250px"><center>Dokter P.J.</center></th>
                                <th width="250px"><center>Petugas</center></th>
                                <th width="200px"><center>Hasil IgM</center></th>
                                <th width="200px"><center>Hasil IgG</center></th>
                                <th width="100px"><center>Berlaku</center></th>
                                <th width="100px"><center>Sampai</center></th>
                                <th width="50px"><center>Surat</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querysuratcovid = bukaquery("select surat_keterangan_covid.no_surat,surat_keterangan_covid.no_rawat,surat_keterangan_covid.kd_dokter,dokter.nm_dokter,surat_keterangan_covid.nip,petugas.nama,surat_keterangan_covid.igm,surat_keterangan_covid.igg,surat_keterangan_covid.sehat,surat_keterangan_covid.tidaksehat,date_format(surat_keterangan_covid.berlakumulai,'%d/%m/%Y') as berlakumulai,date_format(surat_keterangan_covid.berlakuselsai,'%d/%m/%Y') as berlakuselsai from surat_keterangan_covid inner join reg_periksa on surat_keterangan_covid.no_rawat=reg_periksa.no_rawat inner join dokter on surat_keterangan_covid.kd_dokter=dokter.kd_dokter inner join petugas on surat_keterangan_covid.nip=petugas.nip where reg_periksa.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");
                           while($rsquerysuratcovid = mysqli_fetch_array($querysuratcovid)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsquerysuratcovid["no_surat"]."</td>
                                        <td align='left' valign='middle'>".$rsquerysuratcovid["nm_dokter"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratcovid["nama"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratcovid["igm"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratcovid["igg"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratcovid["berlakumulai"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratcovid["berlakuselsai"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=TampilSuratCovid&iyem=".encrypt_decrypt("{\"nosurat\":\"".$rsquerysuratcovid["no_surat"]."\"}","e")."' class='btn btn-warning waves-effect'>Tampilkan</a></td>
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