<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>JADWAL PRAKTEK DOKTER</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="30%"><center>Nama Dokter</center></th>
                                <th width="15%"><center>Hari Kerja</center></th>
                                <th width="25%"><center>Poliklinik</center></th>
                                <th width="15%"><center>Jam Mulai</center></th>
                                <th width="15%"><center>Jam Selesai</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryjadwal = bukaquery("select dokter.nm_dokter,jadwal.hari_kerja,jadwal.jam_mulai,jadwal.jam_selesai,poliklinik.nm_poli from jadwal inner join poliklinik inner join dokter on jadwal.kd_dokter=dokter.kd_dokter and jadwal.kd_poli=poliklinik.kd_poli where dokter.status='1' order by jadwal.hari_kerja,jadwal.kd_dokter");
                            while($rsqueryjadwal = mysqli_fetch_array($queryjadwal)) {
                                echo "<tr>
                                        <td align='left'>".$rsqueryjadwal["nm_dokter"]."</td>
                                        <td align='center'>".$rsqueryjadwal["hari_kerja"]."</td>
                                        <td align='center'>".$rsqueryjadwal["nm_poli"]."</td>
                                        <td align='center'>".$rsqueryjadwal["jam_mulai"]."</td>
                                        <td align='center'>".$rsqueryjadwal["jam_selesai"]."</td>
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