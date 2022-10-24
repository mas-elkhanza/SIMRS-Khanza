<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>SURAT KETERANGAN RUJUK</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="15%"><center>No.Rujuk</center></th>
                                <th width="25%"><center>Dokter Perujuk</center></th>
                                <th width="23%"><center>Tujuan Rujuk</center></th>
                                <th width="22%"><center>Diagnosa</center></th>
                                <th width="10%"><center>Tgl.Rujuk</center></th>
                                <th width="5%"><center>Surat</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querysuratsakit = bukaquery("select rujuk.no_rujuk,rujuk.rujuk_ke,date_format(rujuk.tgl_rujuk,'%d/%m/%Y') as tgl_rujuk,rujuk.keterangan_diagnosa,dokter.nm_dokter from rujuk inner join dokter on rujuk.kd_dokter=dokter.kd_dokter inner join reg_periksa on reg_periksa.no_rawat=rujuk.no_rawat where reg_periksa.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");
                           while($rsquerysuratsakit = mysqli_fetch_array($querysuratsakit)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsquerysuratsakit["no_rujuk"]."</td>
                                        <td align='left' valign='middle'>".$rsquerysuratsakit["nm_dokter"]."</td>
                                        <td align='left' valign='middle'>".$rsquerysuratsakit["rujuk_ke"]."</td>
                                        <td align='left' valign='middle'>".$rsquerysuratsakit["keterangan_diagnosa"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratsakit["tgl_rujuk"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=TampilSuratRujuk&iyem=".encrypt_decrypt("{\"nosurat\":\"".$rsquerysuratsakit["no_rujuk"]."\"}","e")."' class='btn btn-warning waves-effect'>Tampilkan</a></td>
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