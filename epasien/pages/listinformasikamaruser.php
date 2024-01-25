<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>KETERSEDIAAN KAMAR</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <table class="table table-bordered table-striped table-hover display nowrap dataTables" width="100%">
                    <thead>
                        <tr>
                            <th width="40%"><center>Kelas Kamar</center></th>
                            <th width="20%"><center>Jumlah Bed</center></th>
                            <th width="20%"><center>Bed Terisi</center></th>
                            <th width="20%"><center>Bed Kosong</center></th>
                        </tr>
                    </thead>
                    <tbody>
                    <?php 
                       $queryinformasikamar = bukaquery(
                               "SELECT  nama.kelas,(SELECT COUNT(*) FROM kamar WHERE kelas = nama.kelas AND statusdata='1') AS total,
                                   (SELECT COUNT(*) FROM kamar WHERE  kelas = nama.kelas AND statusdata='1' AND status='ISI') AS isi,
                                   (SELECT COUNT(*) FROM kamar WHERE  kelas = nama.kelas AND statusdata='1' AND status='KOSONG') AS kosong
                                FROM (SELECT DISTINCT kelas FROM kamar WHERE statusdata='1') AS nama");
                       while($rsqueryinformasikamar= mysqli_fetch_array($queryinformasikamar)) {
                           echo "<tr>
                                   <td align='left'>".$rsqueryinformasikamar["kelas"]."</td>
                                   <td align='center'>".$rsqueryinformasikamar["total"]."</td>
                                   <td align='center'>".$rsqueryinformasikamar["isi"]."</td>
                                   <td align='center'>".$rsqueryinformasikamar["kosong"]."</td>
                                 </tr>";
                       }
                    ?>
                    </tbody>
                </table>
                <table class="table table-bordered table-striped table-hover display nowrap dataTable" width="100%">
                    <thead>
                        <tr>
                            <th width="40%"><center>Nama Kamar</center></th>
                            <th width="20%"><center>Jumlah Bed</center></th>
                            <th width="20%"><center>Bed Terisi</center></th>
                            <th width="20%"><center>Bed Kosong</center></th>
                        </tr>
                    </thead>
                    <tbody>
                    <?php 
                       $queryinformasibangsal = bukaquery(
                               "SELECT  nama.nm_bangsal, nama.kd_bangsal,
                                   (SELECT COUNT(*) FROM kamar WHERE kd_bangsal = nama.kd_bangsal AND statusdata='1') AS total,
                                   (SELECT COUNT(*) FROM kamar WHERE kd_bangsal = nama.kd_bangsal AND statusdata='1' AND status='ISI') AS isi,
                                   (SELECT COUNT(*) FROM kamar WHERE kd_bangsal = nama.kd_bangsal AND statusdata='1' AND status='KOSONG') AS kosong 
                                FROM (SELECT DISTINCT nm_bangsal, kd_bangsal FROM bangsal WHERE status='1' AND kd_bangsal IN(SELECT kd_bangsal FROM kamar)) AS nama");
                       while($rsqueryinformasibangsal= mysqli_fetch_array($queryinformasibangsal)) {
                           echo "<tr>
                                   <td align='left'>".$rsqueryinformasibangsal["nm_bangsal"]."</td>
                                   <td align='center'>".$rsqueryinformasibangsal["total"]."</td>
                                   <td align='center'>".$rsqueryinformasibangsal["isi"]."</td>
                                   <td align='center'>".$rsqueryinformasibangsal["kosong"]."</td>
                                 </tr>";
                       }
                    ?>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>