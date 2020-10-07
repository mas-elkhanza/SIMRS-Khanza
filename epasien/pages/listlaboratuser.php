<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>TARIF LABORATORIUM</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="70%"><center>Paket Pemeriksaan</center></th>
                                <th width="15%"><center>Kelas</center></th>
                                <th width="15%"><center>Tarif Laborat</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querylaborat = bukaquery("select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,jns_perawatan_lab.kelas from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where jns_perawatan_lab.status='1' and penjab.png_jawab like '%umum%' order by jns_perawatan_lab.kelas");
                           while($rsquerylaborat = mysqli_fetch_array($querylaborat)) {
                               if($rsquerylaborat["total_byr"]>0){
                                   echo "<tr>
                                            <td align='left'>".$rsquerylaborat["nm_perawatan"]."</td>
                                            <td align='center'>".$rsquerylaborat["kelas"]."</td>
                                            <td align='center'>".formatDuit($rsquerylaborat["total_byr"])."</td>
                                          </tr>";
                               }
                                    
                               $querydetaillaborat= bukaquery("select Pemeriksaan,biaya_item from template_laboratorium where kd_jenis_prw='$rsquerylaborat[0]' and biaya_item>0 order by urut");
                               while($rsquerydetaillaborat=mysqli_fetch_array($querydetaillaborat)){
                                    echo "<tr>
                                            <td align='left'>".$rsquerylaborat["nm_perawatan"]." - ".$rsquerydetaillaborat["Pemeriksaan"]."</td>
                                            <td align='center'>".$rsquerylaborat["kelas"]."</td>
                                            <td align='center'>".formatDuit($rsquerydetaillaborat["biaya_item"])."</td>
                                          </tr>";
                               }
                           }
                        ?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>