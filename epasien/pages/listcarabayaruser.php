<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>ASURANSI YANG BEKERJA SAMA</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="25%"><center>Nama Asuransi</center></th>
                                <th width="30%"><center>Perusahaan</center></th>
                                <th width="35%"><center>Alamat Perusahaan</center></th>
                                <th width="10%"><center>No.Telp</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $queryasuransi = bukaquery("select png_jawab, nama_perusahaan, alamat_asuransi, no_telp from penjab where png_jawab <>'-' and png_jawab not like '%umum%' order by png_jawab");
                           while($rsqueryasuransi = mysqli_fetch_array($queryasuransi)) {
                               echo "<tr>
                                       <td align='left'>".$rsqueryasuransi["png_jawab"]."</td>
                                       <td align='left'>".$rsqueryasuransi["nama_perusahaan"]."</td>
                                       <td align='left'>".$rsqueryasuransi["alamat_asuransi"]."</td>
                                       <td align='center'>".$rsqueryasuransi["no_telp"]."</td>
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