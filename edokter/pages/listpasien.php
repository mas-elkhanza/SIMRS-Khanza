<div class="block-header">
    <h2><center>DAFTAR PASIEN HARI INI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="5%"><center>No.Poli</center></th>
                                <th width="15%"><center>No.Rawat</center></th>
                                <th width="10%"><center>No.RM</center></th>
                                <th width="50%"><center>Nama Pasien</center></th>
                                <th width="5%"><center>JK</center></th>
                                <th width="5%"><center>Umur</center></th>
                                <th width="10%"><center>Status</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querypasien = bukaquery("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.stts 
                                                     from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.kd_dokter='".validTeks4(encrypt_decrypt($_SESSION["ses_admin"],"d"),20)."' and reg_periksa.tgl_registrasi=current_date()");
                           while($rsquerypasien = mysqli_fetch_array($querypasien)) {
                               echo "<tr>
                                       <td align='left'>".$rsquerypasien["no_reg"]."</td>
                                       <td align='left'>".$rsquerypasien["no_rawat"]."</td>
                                       <td align='left'>".$rsquerypasien["no_rkm_medis"]."</td>
                                       <td align='left'>".$rsquerypasien["nm_pasien"]."</td>
                                       <td align='left'>".$rsquerypasien["jk"]."</td>
                                       <td align='left'>".$rsquerypasien["umur"]."</td>
                                       <td align='left'>".$rsquerypasien["stts"]."</td>
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