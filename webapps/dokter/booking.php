<?php

/***
* SIMRS Khanza Lite from version 0.1 Beta
* About : Porting of SIMRS Khanza by Windiarto a.k.a Mas Elkhanza as web and mobile app.
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
* Licence under GPL
***/

$title = 'Booking Pendaftaran';
//include_once('config.php');
include_once('layout/header.php');
//include_once('layout/sidebar.php');
?>

    <section class="content">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                BOOKING <?php echo $nmpoli; ?> 
                                <small><?php if(isset($_POST['tgl_awal']) && isset($_POST['tgl_akhir'])) { echo "Periode ".date("d-m-Y",strtotime($_POST['tgl_awal']))." s/d ".date("d-m-Y",strtotime($_POST['tgl_akhir'])); } ?></small>
                            </h2>
                        </div>
                        <div class="body table-responsive">
                            <div id="buttons" class="align-center m-l-10 m-b-15 export-hidden"></div>
                            <table id="datatable_booking" class="table table-bordered table-striped table-hover display nowrap">
                                <thead>
                                    <tr>
                                        <th>Nama Pasien</th>
                                        <th>Antrian</th>
                                        <th>Dokter</th>
                                        <th>No. RM</th>
                                        <th>Alamat</th>
                                        <th>Tgl. Periksa</th>
                                        <th>Jenis Bayar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php
                                //$sql = "SELECT a.nm_pasien, b.no_rkm_medis, a.alamat, c.png_jawab, d.nm_poli, b.no_reg, b.tanggal_booking, b.jam_booking FROM pasien a, booking_registrasi b, penjab c, poliklinik d WHERE a.no_rkm_medis = b.no_rkm_medis AND b.kd_pj = c.kd_pj AND b.kd_poli = '{$_SESSION['jenis_poli']}'";
                                $sql = "SELECT a.nm_pasien, b.no_reg, d.nm_dokter, b.no_rkm_medis, a.alamat, b.tanggal_periksa, c.png_jawab FROM pasien a, booking_registrasi b, penjab c, dokter d WHERE a.no_rkm_medis = b.no_rkm_medis AND b.kd_poli = '{$_SESSION['jenis_poli']}' AND b.kd_pj = c.kd_pj AND b.kd_dokter = d.kd_dokter";
                                if(isset($_POST['tgl_awal']) && isset($_POST['tgl_akhir'])) {
                                	$sql .= " AND b.tanggal_periksa BETWEEN '$_POST[tgl_awal]' AND '$_POST[tgl_akhir]'";
                                } else {
                                  	$sql .= " AND b.tanggal_periksa = '$date'";
                                }
                                $sql .= " ORDER BY b.no_reg ASC";
                                $query = query($sql);
                                while($row = fetch_array($query)) {
                                ?>
                                    <tr>
                                        <td><?php echo SUBSTR($row['0'], 0, 15).' ...'; ?></td>
                                        <td><?php echo $row['1']; ?></td>
                                        <td><?php echo $row['2']; ?></td>
                                        <td><?php echo $row['3']; ?></td>
                                        <td><?php echo $row['4']; ?></td>
                                        <td><?php echo $row['5']; ?></td>
                                        <td><?php echo $row['6']; ?></td>
                                    </tr>
                                <?php
                                }
                                ?>
                                </tbody>
                            </table>
                            <div class="row clearfix">
                                <form method="post" action="">
                                <div class="col-sm-5">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" name="tgl_awal" class="datepicker form-control" placeholder="Pilih tanggal awal...">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-5">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" name="tgl_akhir" class="datepicker form-control" placeholder="Pilih tanggal akhir...">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="submit" class="btn bg-blue btn-block btn-lg waves-effect">
                                        </div>
                                    </div>
                                </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

<?php
include_once('layout/footer.php');
?>
