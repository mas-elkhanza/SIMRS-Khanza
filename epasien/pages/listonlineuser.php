<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>TARIF KONSULTASI ONLINE</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="50%"><center>Nama Tarif</center></th>
                                <th width="30%"><center>Unit/Poliklinik</center></th>
                                <th width="20%"><center>Tarif</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $queryonline = bukaquery("select jns_perawatan.nm_perawatan,jns_perawatan.total_byrdr,poliklinik.nm_poli from jns_perawatan inner join penjab on penjab.kd_pj=jns_perawatan.kd_pj inner join poliklinik on poliklinik.kd_poli=jns_perawatan.kd_poli inner join set_tarif_online on set_tarif_online.kd_jenis_prw=jns_perawatan.kd_jenis_prw where jns_perawatan.status='1' and penjab.png_jawab like '%umum%' order by jns_perawatan.nm_perawatan");
                           while($rsqueryonline = mysqli_fetch_array($queryonline)) {
                               echo "<tr>
                                       <td align='left'>".$rsqueryonline["nm_perawatan"]."</td>
                                       <td align='center'>".$rsqueryonline["nm_poli"]."</td>
                                       <td align='center'>".formatDuit($rsqueryonline["total_byrdr"])."</td>
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