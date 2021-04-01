<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    $besok     = date("Y-m-d", strtotime("+1 day"));
    $thnbesok  = substr($besok,0,4);
    $blnbesok  = substr($besok,5,2);
    $tglbesok  = substr($besok,8,2);
    
    $thncari   = trim(isset($_POST['tgl_registrasi']))?substr($_POST['tgl_registrasi'],6,4):$thnbesok;
    $blncari   = trim(isset($_POST['tgl_registrasi']))?substr($_POST['tgl_registrasi'],3,2):$blnbesok;
    $tglcari   = trim(isset($_POST['tgl_registrasi']))?substr($_POST['tgl_registrasi'],0,2):$tglbesok;
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>BOOKING REGISTRASI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <ul class="nav nav-tabs tab-nav-right" role="tablist">
                    <li role="presentation" class="active"><a href="#home" data-toggle="tab">PILIH</a></li>
                    <li role="presentation"><a href="#riwayat" data-toggle="tab">RIWAYAT BOOKING</a></li>
                </ul>
                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane fade in active" id="home">
                        <form id="form_validation" action="" method="POST">
                            <label for="tgl_registrasi">Tanggal Rencana Periksa</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_registrasi" class="datepicker form-control" required autocomplete="off" value="<?=$tglcari."-".$blncari."-".$thncari;?>"/>
                                </div>
                            </div>
                            <center><button class="btn btn-danger waves-effect" type="submit" name="pilihpoli">TAMPILKAN JADWAL TERSEDIA</button></center>
                        </form><br/>
                        <?php 
                            $hari = getOne("select DAYNAME('$thncari-$blncari-$tglcari')");
                            echo "<center>".konversiHari($hari).", $tglcari ".konversiBulan($blncari)." $thncari</center>";
                        ?>
                        <br/>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="27%"><center>Nama Dokter</center></th>
                                        <th width="28%"><center>Poliklinik</center></th>
                                        <th width="15%"><center>Mulai</center></th>
                                        <th width="15%"><center>Selesai</center></th>
                                        <th width="5%"><center>Kuota</center></th>
                                        <th width="5%"><center>Daftar</center></th>
                                        <th width="5%"><center>Status</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php 
                                    $queryjadwal = bukaquery("Select dokter.nm_dokter,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai,poliklinik.kd_poli,jadwal.kuota,dokter.kd_dokter from jadwal inner join dokter on dokter.kd_dokter=jadwal.kd_dokter inner join poliklinik on jadwal.kd_poli=poliklinik.kd_poli where jadwal.hari_kerja like '%".konversiHari($hari)."%'");
                                    while($rsqueryjadwal = mysqli_fetch_array($queryjadwal)) {
                                        $daftar     = getOne("select count(no_rkm_medis) from booking_registrasi where tanggal_periksa='$thncari-$blncari-$tglcari' and kd_dokter='".$rsqueryjadwal["kd_dokter"]."' and kd_poli='".$rsqueryjadwal["kd_poli"]."'" ); 
                                        $terdaftar  = getOne("select count(no_rkm_medis) from booking_registrasi where tanggal_periksa='$thncari-$blncari-$tglcari' and kd_dokter='".$rsqueryjadwal["kd_dokter"]."' and kd_poli='".$rsqueryjadwal["kd_poli"]."' and no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'" );
                                        if(($rsqueryjadwal["kuota"]>0)&&($daftar>=$rsqueryjadwal["kuota"])){
                                            if($terdaftar>0){
                                                echo "<tr>
                                                        <td align='left'>".$rsqueryjadwal["nm_dokter"]."</td>
                                                        <td align='left'>".$rsqueryjadwal["nm_poli"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["jam_mulai"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["jam_selesai"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["kuota"]."</td>
                                                        <td align='center'>".$daftar."</td>
                                                        <td align='center' valign='middle'><p class='col-pink'>Terdaftar</p></td>
                                                      </tr>";
                                            }else{
                                                echo "<tr>
                                                        <td align='left'>".$rsqueryjadwal["nm_dokter"]."</td>
                                                        <td align='left'>".$rsqueryjadwal["nm_poli"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["jam_mulai"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["jam_selesai"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["kuota"]."</td>
                                                        <td align='center'>".$daftar."</td>
                                                        <td align='center' valign='middle'><p class='col-pink'>Penuh</p></td>
                                                      </tr>";
                                            }   
                                        }else{
                                            if($terdaftar>0){
                                                echo "<tr>
                                                        <td align='left'>".$rsqueryjadwal["nm_dokter"]."</td>
                                                        <td align='left'>".$rsqueryjadwal["nm_poli"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["jam_mulai"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["jam_selesai"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["kuota"]."</td>
                                                        <td align='center'>".$daftar."</td>
                                                        <td align='center' valign='middle'><p class='col-pink'>Terdaftar</p></td>
                                                      </tr>";
                                            }else{
                                                echo "<tr>
                                                        <td align='left'>".$rsqueryjadwal["nm_dokter"]."</td>
                                                        <td align='left'>".$rsqueryjadwal["nm_poli"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["jam_mulai"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["jam_selesai"]."</td>
                                                        <td align='center'>".$rsqueryjadwal["kuota"]."</td>
                                                        <td align='center'>".$daftar."</td>
                                                        <td align='center' valign='middle'><a href='index.php?act=SimpanBookingRegistrasi&iyem=".encrypt_decrypt("{\"kd_dokter\":\"".$rsqueryjadwal["kd_dokter"]."\",\"kd_poli\":\"".$rsqueryjadwal["kd_poli"]."\",\"tanggal\":\"$thncari-$blncari-$tglcari\",\"kuota\":\"".$rsqueryjadwal["kuota"]."\"}","e")."' class='btn btn-warning waves-effect'>Booking</a></td>
                                                      </tr>";
                                            }
                                        }

                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane fade" id="riwayat">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="150px"><center>Tgl.Booking</center></th>
                                        <th width="50px"><center>Tgl.Periksa</center></th>
                                        <th width="150px"><center>Dokter Dituju</center></th>
                                        <th width="150px"><center>Poli Dituju</center></th>
                                        <th width="30px"><center>No.Reg</center></th>
                                        <th width="100px"><center>Jenis Bayar</center></th>
                                        <th width="130px"><center>Status</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php 
                                   $queryriwayat = bukaquery("select booking_registrasi.tanggal_booking,booking_registrasi.jam_booking,booking_registrasi.tanggal_periksa,booking_registrasi.kd_dokter,dokter.nm_dokter,booking_registrasi.kd_poli,poliklinik.nm_poli,booking_registrasi.no_reg,booking_registrasi.kd_pj,penjab.png_jawab,booking_registrasi.status from booking_registrasi inner join dokter on booking_registrasi.kd_dokter=dokter.kd_dokter inner join poliklinik on booking_registrasi.kd_poli=poliklinik.kd_poli inner join penjab on booking_registrasi.kd_pj=penjab.kd_pj where booking_registrasi.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' order by booking_registrasi.tanggal_periksa desc");
                                   while($rsqueryriwayat = mysqli_fetch_array($queryriwayat)) {
                                       if($rsqueryriwayat["status"]=="Belum"){
                                           echo "<tr>
                                                     <td align='center' valign='middle'>".$rsqueryriwayat["tanggal_booking"]." ".$rsqueryriwayat["jam_booking"]." </td>
                                                     <td align='center' valign='middle'>".$rsqueryriwayat["tanggal_periksa"]."</td>
                                                     <td align='left' valign='middle'>".$rsqueryriwayat["nm_dokter"]."</td>
                                                     <td align='left' valign='middle'>".$rsqueryriwayat["nm_poli"]."</td>
                                                     <td align='center' valign='middle'>".$rsqueryriwayat["no_reg"]."</td>
                                                     <td align='left' valign='middle'>".$rsqueryriwayat["png_jawab"]."</td>
                                                     <td align='center' valign='middle'><a href='index.php?act=CekinRegistrasi&iyem=".encrypt_decrypt("{\"kd_dokter\":\"".$rsqueryriwayat["kd_dokter"]."\",\"kd_poli\":\"".$rsqueryriwayat["kd_poli"]."\",\"tanggal\":\"".$rsqueryriwayat["tanggal_periksa"]."\",\"kd_pj\":\"".$rsqueryriwayat["kd_pj"]."\",\"no_reg\":\"".$rsqueryriwayat["no_reg"]."\",\"status\":\"cekin\"}","e")."' class='btn btn-warning waves-effect'>Cekin</a><a href='index.php?act=CekinRegistrasi&iyem=".encrypt_decrypt("{\"kd_dokter\":\"".$rsqueryriwayat["kd_dokter"]."\",\"kd_poli\":\"".$rsqueryriwayat["kd_poli"]."\",\"tanggal\":\"".$rsqueryriwayat["tanggal_periksa"]."\",\"kd_pj\":\"".$rsqueryriwayat["kd_pj"]."\",\"no_reg\":\"".$rsqueryriwayat["no_reg"]."\",\"status\":\"batal\"}","e")."' class='btn btn-danger waves-effect'>Batal</a></td>
                                                  </tr>";
                                       }else if($rsqueryriwayat["status"]=="Terdaftar"){
                                           echo "<tr>
                                                     <td align='center' valign='middle'>".$rsqueryriwayat["tanggal_booking"]." ".$rsqueryriwayat["jam_booking"]." </td>
                                                     <td align='center' valign='middle'>".$rsqueryriwayat["tanggal_periksa"]."</td>
                                                     <td align='left' valign='middle'>".$rsqueryriwayat["nm_dokter"]."</td>
                                                     <td align='left' valign='middle'>".$rsqueryriwayat["nm_poli"]."</td>
                                                     <td align='center' valign='middle'>".$rsqueryriwayat["no_reg"]."</td>
                                                     <td align='left' valign='middle'>".$rsqueryriwayat["png_jawab"]."</td>
                                                     <td align='center' valign='middle'><a href='index.php?act=BuktiRegistrasi&iyem=".encrypt_decrypt("{\"kd_dokter\":\"".$rsqueryriwayat["kd_dokter"]."\",\"kd_poli\":\"".$rsqueryriwayat["kd_poli"]."\",\"tanggal\":\"".$rsqueryriwayat["tanggal_periksa"]."\"}","e")."' class='btn btn-success waves-effect'>Terdaftar</a></td>
                                                  </tr>";
                                       }else{
                                           echo "<tr>
                                                     <td align='center' valign='middle'>".$rsqueryriwayat["tanggal_booking"]." ".$rsqueryriwayat["jam_booking"]." </td>
                                                     <td align='center' valign='middle'>".$rsqueryriwayat["tanggal_periksa"]."</td>
                                                     <td align='left' valign='middle'>".$rsqueryriwayat["nm_dokter"]."</td>
                                                     <td align='left' valign='middle'>".$rsqueryriwayat["nm_poli"]."</td>
                                                     <td align='center' valign='middle'>".$rsqueryriwayat["no_reg"]."</td>
                                                     <td align='left' valign='middle'>".$rsqueryriwayat["png_jawab"]."</td>
                                                     <td align='center' valign='middle'><p class='col-pink'>".$rsqueryriwayat["status"]."<p></td>
                                                  </tr>";
                                       }
                                            
                                   }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                        
            </div>
        </div>
    </div>
</div>

<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
