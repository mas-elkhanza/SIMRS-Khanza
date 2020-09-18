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


<?php
    $querybooking = bukaquery("select booking_registrasi.tanggal_booking,booking_registrasi.jam_booking,booking_registrasi.tanggal_periksa,booking_registrasi.kd_dokter,dokter.nm_dokter,booking_registrasi.kd_poli,poliklinik.nm_poli,booking_registrasi.no_reg,booking_registrasi.kd_pj,penjab.png_jawab,booking_registrasi.status from booking_registrasi inner join dokter on booking_registrasi.kd_dokter=dokter.kd_dokter inner join poliklinik on booking_registrasi.kd_poli=poliklinik.kd_poli inner join penjab on booking_registrasi.kd_pj=penjab.kd_pj where booking_registrasi.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' and booking_registrasi.status='Belum' and booking_registrasi.tanggal_periksa=current_date()");
    if(mysqli_num_rows($querybooking)!=0) {
        echo "<div class='block-header'>
                 <h2><center>BOOKING ANDA</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <div class='table-responsive'>
                                <table class='table table-hover dataTable'>
                                    <tbody>";
                                    while($rsquerybooking = mysqli_fetch_array($querybooking)) {
                                        echo "<tr>
                                                <td width='30%'>Tgl.Booking</td>
                                                <td width='70%'> : ".$rsquerybooking["tanggal_booking"]." ".$rsquerybooking["jam_booking"]."</td>
                                             </tr>
                                             <tr>
                                                <td>Dokter Dituju</td>
                                                <td> : ".$rsquerybooking["nm_dokter"]."</td>
                                             </tr>
                                             <tr>
                                                <td>Poli Dituju</td>
                                                <td> : ".$rsquerybooking["nm_poli"]."</td>
                                             </tr>
                                             <tr>
                                                <td>No.Reg</td>
                                                <td> : ".$rsquerybooking["no_reg"]."</td>
                                             </tr>
                                             <tr>
                                                <td>Jenis Bayar</td>
                                                <td> : ".$rsquerybooking["png_jawab"]."</td>
                                             </tr>
                                             <tr>
                                                <td colspan='2'><center><a href='index.php?act=CekinRegistrasi&iyem=".encrypt_decrypt("{\"kd_dokter\":\"".$rsquerybooking["kd_dokter"]."\",\"kd_poli\":\"".$rsquerybooking["kd_poli"]."\",\"tanggal\":\"".$rsquerybooking["tanggal_periksa"]."\",\"kd_pj\":\"".$rsquerybooking["kd_pj"]."\",\"no_reg\":\"".$rsquerybooking["no_reg"]."\",\"status\":\"cekin\"}","e")."' class='btn btn-warning waves-effect'>Cekin</a>&nbsp;&nbsp;<a href='index.php?act=CekinRegistrasi&iyem=".encrypt_decrypt("{\"kd_dokter\":\"".$rsquerybooking["kd_dokter"]."\",\"kd_poli\":\"".$rsquerybooking["kd_poli"]."\",\"tanggal\":\"".$rsquerybooking["tanggal_periksa"]."\",\"kd_pj\":\"".$rsquerybooking["kd_pj"]."\",\"no_reg\":\"".$rsquerybooking["no_reg"]."\",\"status\":\"batal\"}","e")."' class='btn btn-danger waves-effect'>Batal</a></center></td>
                                             </tr>";
                                    }
        echo"                       </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                 </div>
              </div>";
    }       
    
    $queryregistrasi = bukaquery("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.kd_poli,poliklinik.nm_poli,reg_periksa.stts_daftar,penjab.png_jawab from reg_periksa inner join dokter inner join poliklinik inner join penjab on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' and reg_periksa.tgl_registrasi=current_date()");
    if($rsqueryregistrasi = mysqli_fetch_array($queryregistrasi)) {
        $PNG_TEMP_DIR           = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
        $PNG_WEB_DIR            = 'temp/';
        include_once "plugins/phpqrcode/qrlib.php"; 
        if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
        $filename               = $PNG_TEMP_DIR.str_replace("/","",$rsqueryregistrasi["no_rawat"]).'.png';
        $errorCorrectionLevel   = 'L';
        $matrixPointSize        = 4;
        QRcode::png($rsqueryregistrasi["no_rawat"], $filename, $errorCorrectionLevel, $matrixPointSize, 2); 

        $_SESSION["kd_poli"]        = $rsqueryregistrasi["kd_poli"];
        $_SESSION["kd_dokter"]      = $rsqueryregistrasi["kd_dokter"];
        $_SESSION["tgl_registrasi"] = $rsqueryregistrasi["tgl_registrasi"];
        $_SESSION["no_reg"]         = $rsqueryregistrasi["no_reg"];
        echo "<div class='block-header'>
                <h2><center>PENDAFTARAN ANDA</center></h2>
            </div>
            <div class='row clearfix'>
                <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <div class='table-responsive'>
                                <table class='table table-hover dataTable'>
                                    <tr>
                                       <td width='30%'>Tanggal</td>
                                       <td width='70%'> : ".$rsqueryregistrasi["tgl_registrasi"]." ".$rsqueryregistrasi["jam_reg"]."</td>
                                    </tr>
                                    <tr>
                                       <td>No. Rawat</td>
                                       <td> : ".$rsqueryregistrasi["no_rawat"]."</td>
                                    </tr>
                                    <tr>
                                       <td colspan='2'>
                                            <table width='100%'>
                                                <tr>
                                                    <td width='40%' align='right'><img src='pages/".$PNG_WEB_DIR.basename($filename)."'/></td>
                                                    <td width='60%' align='left'>
                                                        <div class='font-50'>".$rsqueryregistrasi["no_reg"]."</div>
                                                        <br>
                                                        <div id='screen' />  Antrian Lagi
                                                    </td>
                                                </tr>
                                            </table>
                                       </td>
                                    </tr>
                                    <tr>
                                       <td>Jenis Bayar</td>
                                       <td> : ".$rsqueryregistrasi["png_jawab"]."</td>
                                    </tr>
                                    <tr>
                                       <td>Ruang/Unit/Poli</td>
                                       <td> : ".$rsqueryregistrasi["nm_poli"]."</td>
                                    </tr>
                                    <tr>
                                       <td>Dokter</td>
                                       <td> : ".$rsqueryregistrasi["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                       <td colspan='2'>
                                            <center>
                                                <a href='index.php?act=BuktiRegistrasi2&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryregistrasi["no_rawat"]."\"}","e")."' class='btn btn-success waves-effect'>Lapar?</a>
                                                <a href='index.php?act=Perpustakaan' class='btn btn-primary waves-effect'>Bosan?</a>
                                                <a href='index.php?act=BuktiRegistrasi2&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryregistrasi["no_rawat"]."\"}","e")."' class='btn btn-warning waves-effect'>Detail</a>
                                                <a href='index.php?act=CekBilling2&iyem=".encrypt_decrypt("{\"norawat\":\"".$rsqueryregistrasi["no_rawat"]."\"}","e")."' class='btn btn-danger waves-effect' >Billing</a>
                                            </center>
                                       </td>
                                    </tr>
                               </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>";
    }
?>

<script src="js/jquery.js"></script>
<script type="text/javascript">
    $(document).ready(my_function());
    
    setInterval("my_function();",6000); 
    
    function my_function(){
        $("#screen").load("pages/daftarantrian.php");
    }
</script>
