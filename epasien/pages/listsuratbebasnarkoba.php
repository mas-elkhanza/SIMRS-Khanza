<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>SURAT KETERANGAN BEBAS NARKOBA</center></h2>
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
                                <th width="28%"><center>Dokter Yang Memeriksa</center></th>
                                <th width="10%"><center>Tanggal</center></th>
                                <th width="10%"><center>Kategori</center></th>
                                <th width="32%"><center>Keperluan</center></th>
                                <th width="5%"><center>Surat</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querysuratbebasnarkoba = bukaquery("select surat_skbn.no_surat,surat_skbn.no_rawat,date_format(surat_skbn.tanggalsurat,'%d/%m/%Y') as tanggalsurat,surat_skbn.kategori,surat_skbn.keperluan,dokter.nm_dokter from surat_skbn inner join reg_periksa on surat_skbn.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on dokter.kd_dokter=reg_periksa.kd_dokter where reg_periksa.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");
                           while($rsquerysuratbebasnarkoba = mysqli_fetch_array($querysuratbebasnarkoba)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsquerysuratbebasnarkoba["no_surat"]."</td>
                                        <td align='left' valign='middle'>".$rsquerysuratbebasnarkoba["nm_dokter"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratbebasnarkoba["tanggalsurat"]."</td>
                                        <td align='center' valign='middle'>".$rsquerysuratbebasnarkoba["kategori"]."</td>
                                        <td align='left' valign='middle'>".$rsquerysuratbebasnarkoba["keperluan"]."</td>
                                        <td align='center' valign='middle'><a href='index.php?act=TampilSuratBebasNarkoba&iyem=".encrypt_decrypt("{\"nosurat\":\"".$rsquerysuratbebasnarkoba["no_surat"]."\"}","e")."' class='btn btn-warning waves-effect'>Tampilkan</a></td>
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