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
      <source src="sound/kamar.mp3" type="audio/mpeg">
    </audio>


    <section class="content">
        <div class="container-fluid" style="margin: 120px;">
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                INFORMASI KAMAR INAP HARI INI
                                <small><?php echo $dayList[$day].", ".date(d)." ".$bulanList[$bulan]." ".date(Y); ?></small>
                            </h2>
                        </div>
                        <div class="body table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Nama Kelas</th>
                                        <th>Bed Tersedia</th>
                                        <th>Bed Terisi</th>
                                        <th>Bed Kosong</th>
                                        <th>Tarif Kamar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                $sql = query("SELECT nama.kelas, (SELECT trf_kamar FROM kamar WHERE kelas = nama.kelas AND statusdata='1' GROUP BY kelas) AS tarif, (SELECT COUNT(*) FROM kamar WHERE  kelas = nama.kelas AND statusdata='1') AS total, (SELECT COUNT(*) FROM kamar WHERE kelas = nama.kelas AND statusdata='1' AND status='ISI') AS isi, (SELECT COUNT(*) FROM kamar WHERE  kelas = nama.kelas AND statusdata='1' AND status='KOSONG') AS kosong FROM (SELECT DISTINCT kelas FROM kamar WHERE statusdata='1') AS nama ORDER BY nama.kelas ASC");
                                $no = 1;
                                while($row = fetch_array($sql)) {
                                ?>
                                    <tr>
                                        <th scope="row"><?php echo $no; ?></th>
                                        <td><?php echo $row['0']; ?></td>
                                        <td><?php echo $row['2']; ?></td>
                                        <td><?php echo $row['3']; ?></td>
                                        <td><?php echo $row['4']; ?></td>
                                        <td>Rp. <?php echo number_format($row['1'], 0, ".",","); ?></td>
                                    </tr>
                                <?php 
                                $no++;
                                } 
                                ?>
                                </tbody>
                            </table>
                        </div>

                        <div class="body table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Nama Kamar</th>
                                        <th>Bed Tersedia</th>
                                        <th>Bed Terisi</th>
                                        <th>Bed Kosong</th>
                                        <th>Tarif Kamar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                $sql = query("SELECT nama.nm_bangsal, nama.kd_bangsal, (SELECT COUNT(*) FROM kamar WHERE  kd_bangsal = nama.kd_bangsal AND statusdata='1') AS total, (SELECT COUNT(*) FROM kamar WHERE kd_bangsal = nama.kd_bangsal AND statusdata='1' AND status='ISI') AS isi, (SELECT COUNT(*) FROM kamar WHERE  kd_bangsal = nama.kd_bangsal AND statusdata='1' AND status='KOSONG') AS kosong, (SELECT trf_kamar FROM kamar WHERE kd_bangsal = nama.kd_bangsal GROUP BY kd_bangsal) AS tarif FROM (SELECT DISTINCT nm_bangsal, kd_bangsal FROM bangsal WHERE status='1' AND kd_bangsal IN(SELECT kd_bangsal FROM kamar)) AS nama ORDER BY nama.nm_bangsal ASC");

                                $no = 1;
                                while($row = fetch_array($sql)) {
                                ?>
                                    <tr>
                                        <th scope="row"><?php echo $no; ?></th>
                                        <td><?php echo $row['0']; ?></td>
                                        <td><?php echo $row['2']; ?></td>
                                        <td><?php echo $row['3']; ?></td>
                                        <td><?php echo $row['4']; ?></td>
                                        <td>Rp. <?php echo number_format($row['5'], 0, ".","."); ?></td>
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