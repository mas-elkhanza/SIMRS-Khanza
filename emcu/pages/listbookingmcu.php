<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $besok       = date("Y-m-d", strtotime("+1 day"));
    $thnbesok    = substr($besok,0,4);
    $blnbesok    = substr($besok,5,2);
    $tglbesok    = substr($besok,8,2);
    $thnmcu      = validTeks(trim(isset($_POST['tgl_mcu']))?substr($_POST['tgl_mcu'],6,4):$thnbesok);
    $blnmcu      = validTeks(trim(isset($_POST['tgl_mcu']))?substr($_POST['tgl_mcu'],3,2):$blnbesok);
    $tglmcu      = validTeks(trim(isset($_POST['tgl_mcu']))?substr($_POST['tgl_mcu'],0,2):$tglbesok);
    $interval    = getOne2("select (TO_DAYS('$thnmcu-$blnmcu-$tglmcu')-TO_DAYS('$sekarang'))");
    $perusahaan  = validTeks4(encrypt_decrypt($_SESSION["ses_emcu"],"d"),20);
    $sekarang    = date("Y-m-d");
    $status      = "";
    if(isset($_POST["BtnCari"])||isset($_GET['iyem'])){
        $status="Aktif";
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>BOOKING MEDICAL CHECK UP</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <ul class="nav nav-tabs tab-nav-right" role="tablist">
                    <li role="presentation" <?=($status=="Aktif"?"":"class='active'")?>><a href="#home" data-toggle="tab">BOOKING PESERTA</a></li>
                    <li role="presentation" <?=($status=="Aktif"?"class='active'":"")?>><a href="#riwayat" data-toggle="tab">DATA BOOKING</a></li>
                </ul>
                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" <?=($status=="Aktif"?"class='tab-pane fade'":"class='tab-pane fade in active'")?> id="home">
                        <form id="form_validation" action="" method="POST">
                            <label for="tgl_mcu">Tanggal Rencana MCU</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_mcu" class="datepicker form-control" required autocomplete="off" value="<?=$tglmcu."-".$blnmcu."-".$thnmcu;?>"/>
                                </div>
                            </div>
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th valign='middle'><center>No.RM</center></th>
                                        <th valign='middle'><center>Nama Pasien</center></th>
                                        <th valign='middle'><center>JK</center></th>
                                        <th valign='middle'><center>Umur</center></th>
                                        <th valign='middle'><center>No.KTP</center></th>
                                        <th valign='middle'><center>No.Pegawai</center></th>
                                        <th valign='middle'><center>Alamat</center></th>
                                        <th valign='middle'><center>Pilih</center></th>
                                    </tr>
                                </thead>
                                <?php
                                    if(isset($_POST["BtnSimpan"])){
                                        if($interval<0){
                                            echo "<div class='row clearfix'>
                                                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                            <div class='card'>
                                                                <div class='body bg-success'>
                                                                    <center><h4>Booking MCU tidak berlaku mundur...!</h4></center>
                                                                </div>
                                                            </div>
                                                        </div>
                                                   </div>";
                                        }else{
                                            if(!empty($_POST["nomorrm"])){
                                                foreach($_POST["nomorrm"] as $nomorrm){
                                                    $max       = getOne2("select ifnull(MAX(CONVERT(RIGHT(booking_mcu_perusahaan.no_mcu,4),signed)),0)+1 from booking_mcu_perusahaan where booking_mcu_perusahaan.tanggal_mcu='$thnmcu-$blnmcu-$tglmcu'");
                                                    $nobooking = "MCU"."$thnmcu$blnmcu$tglmcu".sprintf("%04s", $max);
                                                    $insert    = Tambah4("booking_mcu_perusahaan","'$sekarang',current_time(),'$nomorrm','$thnmcu-$blnmcu-$tglmcu','$nobooking','Terdaftar','$perusahaan'");
                                                    if(!$insert){
                                                        echo "<div class='row clearfix'>
                                                                  <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                                      <div class='card'>
                                                                          <div class='body bg-success'>
                                                                               <center><h4>Karyawan/Pegawai dengan No.RM $nomorrm gagal dibookingkan MCU...!</h4></center>
                                                                          </div>
                                                                      </div>
                                                                  </div>
                                                              </div>";
                                                    }
                                                }
                                            }else{
                                                echo "<div class='row clearfix'>
                                                          <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                              <div class='card'>
                                                                  <div class='body bg-success'>
                                                                      <center><h4>Silahkan pilih karyawan/pegawai yang mau dibookingkan MCU...!</h4></center>
                                                                  </div>
                                                              </div>
                                                          </div>
                                                      </div>";
                                            }
                                        }
                                    }
                                ?>
                                <tbody>
                                <?php 
                                    $querypasien = bukaquery("select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,pasien.no_ktp,pasien.nip,pasien.alamat from pasien where pasien.perusahaan_pasien='$perusahaan' and pasien.no_rkm_medis not in (select distinct booking_mcu_perusahaan.no_rkm_medis from booking_mcu_perusahaan where booking_mcu_perusahaan.status<>'Selesai')");
                                    while($rsquerypasien = mysqli_fetch_array($querypasien)) {
                                        echo "<tr>
                                                 <td align='center' valign='middle'>".$rsquerypasien["no_rkm_medis"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasien["nm_pasien"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasien["jk"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasien["umur"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasien["no_ktp"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasien["nip"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasien["alamat"]."</td>
                                                 <td align='center' valign='middle'>
                                                    <p class='col-pink'><input type='checkbox' name='nomorrm[]' value='".$rsquerypasien["no_rkm_medis"]."' checked/></p>
                                                 </td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                            <center><button class="btn btn-danger waves-effect" type="submit" name="BtnSimpan">Simpan Booking MCU</button></center>
                        </form>
                    </div>
                    <div role="tabpanel" <?=($status=="Aktif"?"class='tab-pane fade in active'":"class='tab-pane fade'")?> id="riwayat">
                        <div class="table-responsive">
                            <?php
                                $thnsekarang = substr($sekarang,0,4);
                                $blnsekarang = substr($sekarang,5,2);
                                $tglsekarang = substr($sekarang,8,2);
                                $thncarimcu  = $thnsekarang;
                                $blncarimcu  = $blnsekarang;
                                $tglcarimcu  = $tglsekarang;
                                $thncarimcu2 = $thnsekarang;
                                $blncarimcu2 = $blnsekarang;
                                $tglcarimcu2 = $tglsekarang;
                                if(isset($_POST["BtnCari"])){
                                    $thncarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu']))?substr($_POST['tgl_cari_mcu'],6,4):$thnsekarang);
                                    $blncarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu']))?substr($_POST['tgl_cari_mcu'],3,2):$blnsekarang);
                                    $tglcarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu']))?substr($_POST['tgl_cari_mcu'],0,2):$tglsekarang);
                                    $thncarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2']))?substr($_POST['tgl_cari_mcu2'],6,4):$thnsekarang);
                                    $blncarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2']))?substr($_POST['tgl_cari_mcu2'],3,2):$blnsekarang);
                                    $tglcarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2']))?substr($_POST['tgl_cari_mcu2'],0,2):$tglsekarang);
                                }
                                if(isset($_GET['iyem'])){
                                    $json = json_decode(encrypt_decrypt($_GET['iyem'],"d"),true);
                                    if (isset($json["tgl_cari_mcu"])) {
                                        $thncarimcu  = validTeks(trim(isset($json['tgl_cari_mcu']))?substr($json['tgl_cari_mcu'],6,4):$thnsekarang);
                                        $blncarimcu  = validTeks(trim(isset($json['tgl_cari_mcu']))?substr($json['tgl_cari_mcu'],3,2):$blnsekarang);
                                        $tglcarimcu  = validTeks(trim(isset($json['tgl_cari_mcu']))?substr($json['tgl_cari_mcu'],0,2):$tglsekarang);
                                    }
                                    
                                    if (isset($json["tgl_cari_mcu2"])) {
                                        $thncarimcu2 = validTeks(trim(isset($json['tgl_cari_mcu2']))?substr($json['tgl_cari_mcu2'],6,4):$thnsekarang);
                                        $blncarimcu2 = validTeks(trim(isset($json['tgl_cari_mcu2']))?substr($json['tgl_cari_mcu2'],3,2):$blnsekarang);
                                        $tglcarimcu2 = validTeks(trim(isset($json['tgl_cari_mcu2']))?substr($json['tgl_cari_mcu2'],0,2):$tglsekarang);
                                    }
                                    
                                    if (isset($json["nomcuhapus"])) {
                                        $hapus=Hapus2("booking_mcu_perusahaan","no_mcu='".validTeks($json["nomcuhapus"])."'");
                                        if(!$hapus){
                                            echo "<div class='row clearfix'>
                                                      <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                          <div class='card'>
                                                              <div class='body bg-success'>
                                                                   <center><h4>No.Booking MCU ".validTeks($json["nomcuhapus"])." gagal dihapus, silahkan hubungi bagian administrasi kami...!</h4></center>
                                                              </div>
                                                          </div>
                                                      </div>
                                                  </div>";
                                        }
                                    }
                                }
                            ?>
                            <form id="form_validation" action="" method="POST">
                                <label for="tgl_cari_mcu">Tanggal MCU</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_mcu" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarimcu."-".$blncarimcu."-".$thncarimcu;?>"/>
                                    </div>
                                </div>
                                <label for="tgl_cari_mcu2">Sampai Dengan</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_mcu2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarimcu2."-".$blncarimcu2."-".$thncarimcu2;?>"/>
                                    </div>
                                </div>
                                <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Cari Data MCU</button></center>
                            </form>
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th valign='middle'><center>No.RM</center></th>
                                        <th valign='middle'><center>Nama Pasien</center></th>
                                        <th valign='middle'><center>JK</center></th>
                                        <th valign='middle'><center>No.KTP</center></th>
                                        <th valign='middle'><center>No.Pegawai</center></th>
                                        <th valign='middle'><center>No.Booking</center></th>
                                        <th valign='middle'><center>Status</center></th>
                                        <th valign='middle'><center>Tgl.Booking</center></th>
                                        <th valign='middle'><center>Jam Booking</center></th>
                                        <th valign='middle'><center>Tgl.MCU</center></th>
                                        <th valign='middle'><center>Aksi</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php 
                                    $querypasiencari = bukaquery(
                                       "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.no_ktp,pasien.nip,booking_mcu_perusahaan.tanggal_booking,booking_mcu_perusahaan.jam_booking,booking_mcu_perusahaan.tanggal_mcu,
                                        booking_mcu_perusahaan.no_mcu,booking_mcu_perusahaan.status from pasien inner join booking_mcu_perusahaan on booking_mcu_perusahaan.no_rkm_medis=pasien.no_rkm_medis 
                                        where booking_mcu_perusahaan.kode_perusahaan='$perusahaan' and booking_mcu_perusahaan.tanggal_mcu between '$thncarimcu-$blncarimcu-$tglcarimcu' and '$thncarimcu2-$blncarimcu2-$tglcarimcu2'"
                                    );
                                    while($rsquerypasiencari = mysqli_fetch_array($querypasiencari)) {
                                        echo "<tr>
                                                 <td align='center' valign='middle'>".$rsquerypasiencari["no_rkm_medis"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["nm_pasien"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasiencari["jk"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasiencari["no_ktp"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasiencari["nip"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasiencari["no_mcu"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasiencari["status"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasiencari["tanggal_booking"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasiencari["jam_booking"]."</td>
                                                 <td align='center' valign='middle'>".$rsquerypasiencari["tanggal_mcu"]."</td>
                                                 <td align='center' valign='middle'>".
                                                    ($rsquerypasiencari["status"]!="Selesai"?
                                                        "<a href='index.php?act=BookingMCU&iyem=".encrypt_decrypt("{\"nomcuhapus\":\"".$rsquerypasiencari["no_mcu"]."\",\"tgl_cari_mcu\":\"$tglcarimcu-$blncarimcu-$thncarimcu\",\"tgl_cari_mcu2\":\"$tglcarimcu2-$blncarimcu2-$thncarimcu2\"}","e")."' class='btn btn-warning waves-effect'>Hapus</a>":
                                                        "<a href='index.php?act=HasilMCU&halaman=BookingMCU&iyem=".encrypt_decrypt("{\"nomcuhasil\":\"".$rsquerypasiencari["no_mcu"]."\",\"tgl_cari_mcu\":\"$tglcarimcu-$blncarimcu-$thncarimcu\",\"tgl_cari_mcu2\":\"$tglcarimcu2-$blncarimcu2-$thncarimcu2\"}","e")."' class='btn btn-success waves-effect'>Hasil MCU</a>"
                                                    ).
                                                "</td>
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
    </div>
</div>

<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
