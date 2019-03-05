<?php include_once ('layout/header.php'); ?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>DASHBOARD DOKTER</h2>
            </div>

            <!-- Widgets -->
            <div class="row clearfix">
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-pink hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">enhanced_encryption</i>
                        </div>
                        <div class="content">
                            <div class="text">TOTAL PASIEN</div>
                            <div class="number count-to" data-from="0" data-to="<?php echo num_rows(query("SELECT no_rkm_medis FROM pasien"));?>" data-speed="5000" data-fresh-interval="20"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-cyan hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">group_add</i>
                        </div>
                        <div class="content">
                            <div class="text">PASIEN BULAN INI</div>
                            <div class="number count-to" data-from="0" data-to="<?php echo num_rows(query("SELECT no_rkm_medis FROM pasien WHERE tgl_daftar LIKE '%$month%'"));?>" data-speed="2000" data-fresh-interval="20"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-light-green hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">people</i>
                        </div>
                        <div class="content">
                            <div class="text">PASIEN POLI BULAN INI</div>
                            <div class="number count-to" data-from="0" data-to="<?php echo num_rows(query("SELECT no_rawat FROM reg_periksa WHERE kd_poli = '{$_SESSION['jenis_poli']}' AND stts !='Belum' AND tgl_registrasi LIKE '%$month%'"));?>" data-speed="1000" data-fresh-interval="20"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-orange hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">person</i>
                        </div>
                        <div class="content">
                            <div class="text">PASIEN POLI HARI INI</div>
                            <div class="number count-to" data-from="0" data-to="<?php echo num_rows(query("SELECT no_rawat FROM reg_periksa WHERE kd_poli = '{$_SESSION['jenis_poli']}' AND tgl_registrasi LIKE '%$date%'"));?>" data-speed="1000" data-fresh-interval="20"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Widgets -->
            <!-- CPU Usage -->
            <div class="row clearfix">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <div class="card">
                        <div class="header">
                            <div class="row clearfix">
                                <div class="col-xs-12 col-sm-6">
                                    <h2>POLIKLINIK HARI INI</h2>
                                </div>
                            </div>
                        </div>
                        <div class="body">
						<?php $dates = 'Tanggal : '.date("d-m-Y "); ?>
                        <?php
						$jumlah=array();
						$poli=array();
						$date = date("Y-m-d"); 
						$sql = "select poliklinik.nm_poli, count(*) as jumlah from reg_periksa INNER JOIN poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli WHERE reg_periksa.tgl_registrasi='$date' and poliklinik.nm_poli !='-' group by reg_periksa.kd_poli  order by count(*) desc ";
						$hasil=query($sql);
						while ($data = fetch_array ($hasil)){
                            $jumlah[]=intval($data['jumlah']);
                            $poli[]= $data['nm_poli'];
                        }
						?>
                                <div id="kunjungan">
                                </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# CPU Usage -->
            <?php 
    $get_poli = fetch_array(query("SELECT a.nm_poli, b.kd_poli FROM poliklinik a, reg_periksa b WHERE a.kd_poli = b.kd_poli AND b.no_rkm_medis = '$_SESSION[username]' AND b.tgl_registrasi='$date'"));
            ?>
            <div class="row clearfix">
                <!-- Task Info -->
                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                    <div class="card">
                        <div class="header">
                            <h2>Pasien <?php echo ucwords(strtolower($nmpoli)); ?> Paling Aktif</h2>
                        </div>
                        <div class="body">
                            <table id="antrian_pasien" class="table table-bordered table-striped table-hover display nowrap dashboard-task-infos" width="100%">
	    <thead>
		<tr>
		    <th>No</th>
		    <th>Nama Lengkap</th>
		    <th>Kunj</th>
		</tr>
	    </thead>
	    <tbody> 
	    <?php
		$sql = query("SELECT no_rkm_medis, count(no_rkm_medis) jumlah FROM reg_periksa WHERE kd_dokter = '{$_SESSION['username']}' GROUP BY no_rkm_medis ORDER BY jumlah DESC LIMIT 10"); 
		$no=1;
		while($row = fetch_array($sql)){ 
		    $getNama = fetch_assoc(query("SELECT nm_pasien FROM pasien WHERE no_rkm_medis = '{$row['no_rkm_medis']}'"));
		    echo '<tr>';
		    echo '<td>'.$no.'</td>';
		    echo '<td>'.ucwords(strtolower($getNama['nm_pasien'])).'</td>';
		    echo '<td>'.$row['jumlah'].'</td>';
		    echo '</tr>';
		$no++;
	    }
	    ?>
	    </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- #END# Task Info -->
                <!-- Browser Usage -->
                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                    <div class="card">
                        <div class="header">
                            <h2>Antrian 10 Pasien Terakhir <?php echo ucwords(strtolower($nmpoli)); ?></h2>
                        </div>
                        <div class="body">
                            <table id="antrian_pasien" class="table table-bordered table-striped table-hover display nowrap dashboard-task-infos" width="100%">
            <thead>
                <tr>
        		    <th>No</th>
                    <th>Nama Lengkap</th>
                     <th>Status</th>
                </tr>
            </thead>
            <tbody>
            <?php

            $sql = "SELECT a.no_rawat, b.no_rkm_medis, b.nm_pasien, a.stts FROM reg_periksa a, pasien b WHERE a.kd_poli = '{$_SESSION['jenis_poli']}' AND a.no_rkm_medis = b.no_rkm_medis AND a.tgl_registrasi = '$date' ORDER BY a.jam_reg DESC LIMIT 10";
        $result = query($sql);
		$no=1;
                while($row = fetch_array($result)){
                    echo '<tr>';
        		    echo '<td>'.$no.'</td>';
                    echo '<td>';
                    echo '<a href="pasien-ralan.php?action=view&no_rawat='.$row['0'].'" class="title">'.ucwords(strtolower($row['2'])).'</a>';
                    echo '</td>';
                    echo '<td>'.$row['3'].'</td>';
                    echo '</tr>';
		$no++;
                }
            ?>
            </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- #END# Browser Usage -->
            </div>
        </div>
    </section>

<?php include_once ('layout/footer.php'); ?>
