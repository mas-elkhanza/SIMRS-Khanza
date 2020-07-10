<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>TARIF OPERASI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="60%"><center>Paket Operasi</center></th>
                                <th width="20%"><center>Kelas</center></th>
                                <th width="20%"><center>Tarif Operasi</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $queryoperasi = bukaquery("select paket_operasi.nm_perawatan,(paket_operasi.operator1+paket_operasi.operator2+paket_operasi.operator3+paket_operasi.asisten_operator1+paket_operasi.asisten_operator2+paket_operasi.asisten_operator3+paket_operasi.instrumen+paket_operasi.dokter_anak+paket_operasi.perawaat_resusitas+paket_operasi.alat+paket_operasi.dokter_anestesi+paket_operasi.asisten_anestesi+paket_operasi.asisten_anestesi2+paket_operasi.bidan+paket_operasi.bidan2+paket_operasi.bidan3+paket_operasi.perawat_luar+paket_operasi.sewa_ok+paket_operasi.akomodasi+paket_operasi.bagian_rs+paket_operasi.omloop+paket_operasi.omloop2+paket_operasi.omloop3+paket_operasi.omloop4+paket_operasi.omloop5+paket_operasi.sarpras+paket_operasi.dokter_pjanak+paket_operasi.dokter_umum) as total,paket_operasi.kelas from paket_operasi inner join penjab on penjab.kd_pj=paket_operasi.kd_pj where paket_operasi.status='1' and penjab.png_jawab like '%umum%' order by paket_operasi.kelas");
                           while($rsqueryoperasi = mysqli_fetch_array($queryoperasi)) {
                               echo "<tr>
                                       <td align='left'>".$rsqueryoperasi["nm_perawatan"]."</td>
                                       <td align='center'>".$rsqueryoperasi["kelas"]."</td>
                                       <td align='center'>".formatDuit($rsqueryoperasi["total"])."</td>
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