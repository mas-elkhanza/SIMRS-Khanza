<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : index.php
* Description : Main page
* Licence under GPL
***/

include_once ('layout/header.php');
?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>DASHBOARD PASIEN</h2>
            </div>

            <div class="row clearfix">
                <?php
                $dokter_hadir = bc_dokter_datang();
                $ex_tgl_msk = explode(" ", $dokter_hadir['waktu_masuk']);
                //print_r($bc);
                @$bc = brodcast();
                $nm_pasien = select_pasien($_SESSION['username']);
                $ex_tgl_masuk = explode(" ", $bc['masuk_tgl']);
                    if($bc)
                    {
                        echo '<div class="col-lg-12 col-md-3 col-sm-6 col-xs-12">';
                            echo '<div class="card">';
                                echo '<div class="row">';
                                    echo '<div class="col-lg-12 col-md-3 col-sm-6 col-xs-12">';
                                        echo '<div class="body">';
                                            echo '<div class="text"><h4 class="blink">';
                                            echo '<span>';
                                            echo '<b><mark>INFORMASI PENTING MOHON DIBACA!</mark></b>';
                                            echo '</h4>';
                                            echo '</span>';
                                            echo '<pre>';
                                            echo '<span>';
                                            echo 'Kepada YTH Bapak/Ibu : '. $nm_pasien['nm_pasien']; 
                                            echo '<br><br>';
                                            echo '<b>Nama Dokter</b>     : '.$bc['nm_dokter'].'<br>'; 
                                            echo '<b>Poli tujuan</b>     : '.$bc['nm_poli'].'<br>';
                                            echo '<b>Tanggal Praktek</b> : '.tanggal_indo($bc['tgl_periksa']).'<br>';
                                            echo '<b>Cuti</b>            : '.tanggal_indo($bc['tgl_awal']). ' - '. tanggal_indo($bc['tgl_akhir']);
                                            echo '</span><br><br>';
                                            echo '<h5>'.strtoupper($bc['judul']).'</h5>';
                                            echo '<p>'.$bc['keterangan'].'</p>';
                                            echo '<b>Dibuat Oleh</b>     : Admin Informasi Rsud Kota Depok.<br>';
                                            echo '<b>Tanggal Dibuat</b>  : '. tanggal_indo($ex_tgl_masuk[0], true) .' - '. $ex_tgl_masuk[1];
                                            echo '</pre>';
                                            echo '</div>';
                                        echo '</div>';
                                    echo '</div>';
                                echo '</div>';  
                            echo '</div>';  
                        echo '</div>';
                    }

                    if($dokter_hadir)
                    {
                        echo '<div class="col-lg-12 col-md-3 col-sm-6 col-xs-12">';
                            echo '<div class="card">';
                                echo '<div class="row">';
                                    echo '<div class="col-lg-12 col-md-3 col-sm-6 col-xs-12">';
                                        echo '<div class="body">';
                                            echo '<div class="text"><h4 class="blink">';
                                            echo '<span>';
                                            echo '<b><mark>INFORMASI DOKTER HADIR</mark></b>';
                                            echo '</h4>';
                                            echo '</span>';
                                            echo '<pre>';
                                            echo '<span>';
                                            echo 'Kepada YTH Bapak/Ibu : '. $nm_pasien['nm_pasien']; 
                                            echo '<br><br>';
                                            echo '<b>Nama Dokter</b>     : '.$dokter_hadir['nm_dokter'].'<br>'; 
                                            echo '<b>Poli tujuan</b>     : '.$dokter_hadir['nm_poli'].'<br>';
                                            echo '<b>Tanggal Praktek</b> : '.tanggal_indo($dokter_hadir['tgl_periksa'], true) . ' - ' . $dokter_hadir['jam_mulai'] .'<br>';
                                            echo '<h5>'.strtoupper($dokter_hadir['judul']).'</h5>';
                                            echo '<p>'.$dokter_hadir['keterangan'].'</p>';
                                            echo '<b>Dibuat Oleh</b>     : Admin Informasi Rsud Kota Depok.<br>';
                                            echo '<b>Tanggal Dibuat</b>  : '. tanggal_indo($ex_tgl_msk[0], true) .' - '. $ex_tgl_msk[1];
                                            echo '</pre>';
                                            echo '</div>';
                                        echo '</div>';
                                    echo '</div>';
                                echo '</div>';  
                            echo '</div>';  
                        echo '</div>';
                    }
                ?>  
            </div>

            <!-- Widgets -->
            <div class="row clearfix">
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                    <div class="info-box bg-pink hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">enhanced_encryption</i>
                        </div>
                        <div class="content">
                            <div class="text">TOTAL KUNJUNGAN</div>
                            <div class="number count-to" data-from="0" data-to="<?php echo num_rows(query("SELECT no_rkm_medis FROM reg_periksa WHERE no_rkm_medis = '{$_SESSION['username']}'"));?>" data-speed="3000" data-fresh-interval="20"></div>
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
                            <div class="number count-to" data-from="0" data-to="<?php echo num_rows(query("SELECT no_rkm_medis FROM reg_periksa WHERE no_rkm_medis = '{$_SESSION['username']}' AND status_lanjut = 'Ralan'"));?>" data-speed="2000" data-fresh-interval="20"></div>
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
                            <div class="number count-to" data-from="0" data-to="<?php echo num_rows(query("SELECT no_rkm_medis FROM reg_periksa WHERE no_rkm_medis = '{$_SESSION['username']}' AND status_lanjut = 'Ranap'"));?>" data-speed="1000" data-fresh-interval="20"></div>
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
                            <div class="number count-to" data-from="0" data-to="<?php echo num_rows(query("SELECT no_rkm_medis FROM reg_periksa WHERE tgl_registrasi LIKE '%$month%' AND no_rkm_medis = '{$_SESSION['username']}'"));?>" data-speed="1000" data-fresh-interval="20"></div>
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
                                <div id="kunjungan">
                                </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# CPU Usage -->

            <?php
    		    $get_poli = fetch_array(query("SELECT a.nm_poli, b.kd_poli FROM poliklinik a, booking_registrasi b WHERE a.kd_poli = b.kd_poli AND b.no_rkm_medis = '$_SESSION[username]' AND b.tanggal_periksa='$date'"));
            ?>
            <div class="row clearfix">
                <!-- Task Info -->
                <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
                    <div class="card">
                        <div class="header">
                            <h2>ANTRIAN <?php echo $get_poli['0']; ?></h2>
                        </div>
                        <div class="body table-responsive">
                            <table id="antrian" class="table table-bordered table-striped table-hover display nowrap dashboard-task-infos" width="100%">
                                <thead>
                                    <tr>
                                        <th>Antrian</th>
                                        <th>Status</th>
                                        <th>Pasien</th>
                                        <th>Dokter</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- #END# Task Info -->
                <!-- Browser Usage -->
                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                    <div class="card">
                        <div class="header bg-indigo">
                            <h2>ANTRIAN ANDA</h2>
                        </div>
                        <?php
                        $antri=fetch_array(query("
                            SELECT
                                a.tanggal_periksa,
                                b.nm_poli,
                                f.nm_pasien,
                                c.nm_dokter,
                                a.no_reg,
                                a.status
                            FROM
                                booking_registrasi a
                            LEFT JOIN
                                poliklinik b ON a.kd_poli = b.kd_poli
                            LEFT JOIN
                                dokter c ON a.kd_dokter = c.kd_dokter
                            LEFT JOIN
                                penjab d ON a.kd_pj = d.kd_pj
                            LEFT JOIN
                                pasien f ON a.no_rkm_medis = f.no_rkm_medis
                            WHERE
                                a.no_rkm_medis = '$_SESSION[username]'
                            AND 
                                a.status='belum'
                            HAVING
                                tanggal_periksa >= '$date'
                        "));
                        //print_r($antri);
                        ?>
                        <div class="body align-center">
                        <?php
                        if($antri == '') {
                            echo '<div class="font-20">Anda belum terdaftar dalam antrian Poliklinik</div>';
                            echo '<br><a href="regis_booking.php"><button type="button" class="btn bg-green btn-lg waves-effect">Daftar</button></a><br><br>';
                            echo '<div class="font-15">Silahkan klik tombol diatas untuk mendaftar antrian On-Line</div>';
                        } else {
                        ?>
                        <div class="font-20"><?php echo $antri[3]; ?></div>
                        <div class="font-50"><?php echo ($antri[5] == 'belum') ? 'Booking' : ''; ?></div>
                        <div class="font-15"><?php echo tanggal_indo($antri[0],true); ?></div>
                        <div class="font-20"><?php echo $antri[2]; ?></div>
                        <div class="font-20"><?php echo $antri[4]; ?></div>
                        <div class="font-24"><?php echo $antri[6]; ?></div>
                        <hr>
                        <div class="font-24"><span class="getantrian"></span> Antrian Lagi</div>
                        <?php } ?>
                        </div>
                    </div>
                </div>		   
                <!-- #END# Browser Usage -->
            </div>
        </div>
    </section>

<?php include_once ('layout/footer.php'); ?>
