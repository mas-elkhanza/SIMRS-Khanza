<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    $querypengumuman = bukaquery("select pegawai.nama,date_format(pengumuman_epasien.tanggal,'%d/%m/%Y')as tanggal,pengumuman_epasien.pengumuman from pengumuman_epasien inner join pegawai on pengumuman_epasien.nik=pegawai.nik order by pengumuman_epasien.tanggal desc limit 1");
    if($pengumuman = mysqli_fetch_array($querypengumuman)) {
        echo "<div class='block-header'>
                 <h2><center>PENGUMUMAN</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='header'>
                            <h2>".$pengumuman["tanggal"].", oleh ".$pengumuman["nama"]."</h2>
                        </div>
                        <div class='body'>
                            ".$pengumuman["pengumuman"]."
                        </div>
                    </div>
                 </div>
              </div>";
    }
?>

<div class="block-header">
    <h2><center>DASHBOARD PASIEN</center></h2>
</div>

<!-- Widgets -->
<div class="row clearfix">
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-pink hover-expand-effect">
            <div class="icon">
                <i class="material-icons">enhanced_encryption</i>
            </div>
            <div class="content">
                <div class="text">KUNJUNGAN</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(no_rkm_medis) FROM reg_periksa WHERE no_rkm_medis = '".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");?>" data-speed="3000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-cyan hover-expand-effect">
            <div class="icon">
                <i class="material-icons">airline_seat_recline_normal</i>
            </div>
            <div class="content">
                <div class="text">RAWAT JALAN</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(no_rkm_medis) FROM reg_periksa WHERE no_rkm_medis = '".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' AND status_lanjut = 'Ralan'");?>" data-speed="2000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-light-green hover-expand-effect">
            <div class="icon">
                <i class="material-icons">local_hotel</i>
            </div>
            <div class="content">
                <div class="text">RAWAT INAP</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(no_rkm_medis) FROM reg_periksa WHERE no_rkm_medis = '".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' AND status_lanjut = 'Ranap'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-orange hover-expand-effect">
            <div class="icon">
                <i class="material-icons">today</i>
            </div>
            <div class="content">
                <div class="text">BULAN INI</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(no_rkm_medis) FROM reg_periksa WHERE tgl_registrasi LIKE '%".date('Y-m')."%' AND no_rkm_medis = '".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
</div>

<div class="block-header">
    <h2><center>JADWAL POLI HARI INI</center></h2>
</div>

<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <?php
                    $hari=getOne("select DAYNAME(current_date())");
                    echo "<h2>".konversiHari($hari).", ".date('d')." ". konversiBulan(date('m'))." ".date('Y')."</h2>";
                ?>
            </div>
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="30%"><center>Nama Dokter</center></th>
                                <th width="24%"><center>Poliklinik</center></th>
                                <th width="15%"><center>Jam Mulai</center></th>
                                <th width="15%"><center>Jam Selesai</center></th>
                                <th width="8%"><center>Kuota</center></th>
                                <th width="8%"><center>Cek&nbsp;In</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                            $queryjadwal = bukaquery("Select dokter.nm_dokter,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai,poliklinik.kd_poli,jadwal.kuota,dokter.kd_dokter from jadwal inner join dokter on dokter.kd_dokter=jadwal.kd_dokter inner join poliklinik on jadwal.kd_poli=poliklinik.kd_poli where jadwal.hari_kerja like '%".konversiHari($hari)."%'");
                            while($rsqueryjadwal = mysqli_fetch_array($queryjadwal)) {
                                echo "<tr>
                                        <td align='left'>".$rsqueryjadwal["nm_dokter"]."</td>
                                        <td align='left'>".$rsqueryjadwal["nm_poli"]."</td>
                                        <td align='center'>".$rsqueryjadwal["jam_mulai"]."</td>
                                        <td align='center'>".$rsqueryjadwal["jam_selesai"]."</td>
                                        <td align='center'>".$rsqueryjadwal["kuota"]."</td>
                                        <td align='center'>".getOne("select count(*) from reg_periksa where kd_poli='".$rsqueryjadwal["kd_poli"]."' and kd_dokter='".$rsqueryjadwal["kd_dokter"]."' and tgl_registrasi='".date("Y-m-d")."'")."</td>
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
