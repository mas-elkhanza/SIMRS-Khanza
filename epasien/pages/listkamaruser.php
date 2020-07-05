<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>TARIF KAMAR</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="15%"><center>Nomer Bed</center></th>
                                <th width="40%"><center>Nama Kamar</center></th>
                                <th width="15%"><center>Kelas</center></th>
                                <th width="15%"><center>Tarif Kamar</center></th>
                                <th width="15%"><center>Status Kamar</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querykamar = bukaquery("select kamar.kd_kamar,bangsal.nm_bangsal,kamar.kelas,kamar.trf_kamar,kamar.status from bangsal inner join kamar on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.statusdata='1' order by kamar.kelas");
                           while($rsquerykamar = mysqli_fetch_array($querykamar)) {
                               echo "<tr>
                                       <td align='left'>".$rsquerykamar["kd_kamar"]."</td>
                                       <td align='left'>".$rsquerykamar["nm_bangsal"]."</td>
                                       <td align='center'>".$rsquerykamar["kelas"]."</td>
                                       <td align='center'>".formatDuit($rsquerykamar["trf_kamar"])."</td>
                                       <td align='center'>".$rsquerykamar["status"]."</td>
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