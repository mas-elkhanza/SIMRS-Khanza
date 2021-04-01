<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>SURAT KONTROL/SKDP</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="10%"><center>No.Surat</center></th>
                                <th width="23%"><center>Dokter</center></th>
                                <th width="22%"><center>Diagnosa</center></th>
                                <th width="30%"><center>Rencana Tindak Lanjut</center></th>
                                <th width="10%"><center>Kembali</center></th>
                                <th width="5%"><center>Surat</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querysuratsakit = bukaquery("select skdp_bpjs.no_rkm_medis,skdp_bpjs.diagnosa,skdp_bpjs.rtl1,skdp_bpjs.tanggal_datang,skdp_bpjs.no_antrian,dokter.nm_dokter,skdp_bpjs.tahun,(TO_DAYS(skdp_bpjs.tanggal_datang)-TO_DAYS(current_date())) as kadaluarsa from skdp_bpjs inner join dokter on skdp_bpjs.kd_dokter=dokter.kd_dokter where skdp_bpjs.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");
                           while($rsquerysuratsakit = mysqli_fetch_array($querysuratsakit)) {
                               if($rsquerysuratsakit["kadaluarsa"]>=0){
                                   echo "<tr>
                                             <td align='center' valign='middle'>".$rsquerysuratsakit["no_antrian"]."</td>
                                             <td align='left' valign='middle'>".$rsquerysuratsakit["nm_dokter"]."</td>
                                             <td align='left' valign='middle'>".$rsquerysuratsakit["diagnosa"]."</td>
                                             <td align='left' valign='middle'>".$rsquerysuratsakit["rtl1"]."</td>
                                             <td align='center' valign='middle'>".$rsquerysuratsakit["tanggal_datang"]."</td>
                                             <td align='center' valign='middle'><a href='index.php?act=TampilSuratKontrol&iyem=".encrypt_decrypt("{\"noantrian\":\"".$rsquerysuratsakit["no_antrian"]."\",\"tahun\":\"".$rsquerysuratsakit["tahun"]."\"}","e")."' class='btn btn-warning waves-effect'>Tampilkan</a></td>
                                          </tr>";
                               }else{
                                   echo "<tr>
                                             <td align='center' valign='middle'>".$rsquerysuratsakit["no_antrian"]."</td>
                                             <td align='left' valign='middle'>".$rsquerysuratsakit["nm_dokter"]."</td>
                                             <td align='left' valign='middle'>".$rsquerysuratsakit["diagnosa"]."</td>
                                             <td align='left' valign='middle'>".$rsquerysuratsakit["rtl1"]."</td>
                                             <td align='center' valign='middle'>".$rsquerysuratsakit["tanggal_datang"]."</td>
                                             <td align='center' valign='middle'><p class='col-pink'>Kadaluarsa</p></td>
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