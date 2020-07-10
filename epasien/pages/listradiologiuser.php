<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>TARIF RADIOLOGI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="60%"><center>Nama Pemeriksaan</center></th>
                                <th width="20%"><center>Kelas</center></th>
                                <th width="20%"><center>Tarif Radiologi</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $queryradiologi = bukaquery("select jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,jns_perawatan_radiologi.kelas from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where jns_perawatan_radiologi.status='1' and penjab.png_jawab like '%umum%' order by jns_perawatan_radiologi.kelas");
                           while($rsqueryradiologi = mysqli_fetch_array($queryradiologi)) {
                               echo "<tr>
                                       <td align='left'>".$rsqueryradiologi["nm_perawatan"]."</td>
                                       <td align='center'>".$rsqueryradiologi["kelas"]."</td>
                                       <td align='center'>".formatDuit($rsqueryradiologi["total_byr"])."</td>
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