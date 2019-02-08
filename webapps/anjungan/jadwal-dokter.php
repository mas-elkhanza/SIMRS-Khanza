<?php
/***
* SIMRS Khanza Lite from version 0.1 Beta
* About : Porting of SIMRS Khanza by Windiarto a.k.a Mas Elkhanza as web and mobile app.
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
* Licence under GPL
***/

include_once('config.php');
include_once('layout/header.php');
?>
<audio autoplay>
      <source src="sound/dokter.mp3" type="audio/mpeg">
    </audio>

    <section class="content">
        <div class="container-fluid" style="margin: 120px;">
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                JADWAL DOKTER HARI INI
                                <small><?php echo $dayList[$day].", ".date(d)." ".$bulanList[$bulan]." ".date(Y); ?></small>
                            </h2>
                        </div>
                        <div class="body table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Poliklinik</th>
                                        <th>Nama Dokter</th>
                                        <th>Jam Mulai</th>
                                        <th>Jam Selesai</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                $sql = query("SELECT poliklinik.nm_poli, dokter.nm_dokter, DATE_FORMAT(jadwal.jam_mulai, '%H:%i') AS jam_mulai, DATE_FORMAT(jadwal.jam_selesai, '%H:%i') AS jam_selesai FROM jadwal INNER JOIN dokter INNER JOIN poliklinik on dokter.kd_dokter=jadwal.kd_dokter AND jadwal.kd_poli=poliklinik.kd_poli WHERE jadwal.hari_kerja='$namahari' ORDER BY poliklinik.nm_poli ASC");
                                $no = 1;
                                while($row = fetch_array($sql)) {
                                ?>
                                    <tr>
                                        <th scope="row"><?php echo $no; ?></th>
                                        <td><?php echo $row['0']; ?></td>
                                        <td><?php echo $row['1']; ?></td>
                                        <td><?php echo $row['2']; ?></td>
                                        <td><?php echo $row['3']; ?></td>
                                    </tr>
                                <?php 
                                $no++;
                                } 
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                    <a href="javascript:history.go(-1)"><button type="button" class="btn bg-blue btn-block btn-lg waves-effect">KEMBALI KE DEPAN</button></a>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                </div>
            </div>
        </div>
    </section>

<?php
include_once('layout/footer.php');
?>