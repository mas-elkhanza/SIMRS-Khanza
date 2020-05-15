<?php
    require_once('conf/conf.php');
    
    $nama_instansi          = "";
    $alamat_instansi        = "";
    $kabupaten              = "";
    $propinsi               = "";
    $kontak                 = "";
    $email                  = "";
    $kode_ppk               = "";
    $kode_ppkinhealth       = "";
    $kode_ppkkemenkes       = "";
    $querypengaturan        = bukaquery("select * from setting");
    while($pengaturan = mysqli_fetch_array($querypengaturan)) {
        $nama_instansi      = $pengaturan["nama_instansi"];
        $alamat_instansi    = $pengaturan["alamat_instansi"];
        $kabupaten          = $pengaturan["kabupaten"];
        $propinsi           = $pengaturan["propinsi"];
        $kontak             = $pengaturan["kontak"];
        $email              = $pengaturan["email"];
        $kode_ppk           = $pengaturan["kode_ppk"];
        $kode_ppkinhealth   = $pengaturan["kode_ppkinhealth"];
        $kode_ppkkemenkes   = $pengaturan["kode_ppkinhealth"];
    }
?>
<section id="home" class="slider" data-stellar-background-ratio="0.5">
      <div class="container">
           <div class="row">
                 <div class="owl-carousel owl-theme">
                      <div class="item item-first">
                           <div class="caption">
                                <div class="col-md-offset-1 col-md-10">
                                     <h3>Mari buat hidupmu lebih bahagia</h3>
                                     <h1>Hidup Sehat</h1>
                                     <a href="#team" class="section-btn btn btn-default smoothScroll">Temui Dokter Kami</a>
                                </div>
                           </div>
                      </div>
                      <div class="item item-second">
                           <div class="caption">
                                <div class="col-md-offset-1 col-md-10">
                                     <h3>Kami usahakan yang terbaik untuk Anda</h3>
                                     <h1>Jadikan Kami Sahabat Anda</h1>
                                     <a href="#about" class="section-btn btn btn-default btn-gray smoothScroll">Lebih Banyak Tentang Kami</a>
                                </div>
                           </div>
                      </div>
                      <div class="item item-third">
                           <div class="caption">
                                <div class="col-md-offset-1 col-md-10">
                                     <h3>Jangan sampai waktu anda bersama keluarga menjadi terganggu</h3>
                                     <h1>Manfaatkan Kesehatan Anda</h1>
                                     <a href="#news" class="section-btn btn btn-default btn-blue smoothScroll">Lihat Jadwal Dokter</a>
                                </div>
                           </div>
                      </div>
                 </div>
           </div>
      </div>
 </section>

 <!-- ABOUT -->
 <section id="about">
      <div class="container">
           <div class="row">
                <div class="col-md-6 col-sm-6">
                     <div class="about-info">
                          <h2 class="wow fadeInUp" data-wow-delay="0.6s">Selamat datang di Pusat Kesehatan Anda</h2>
                          <div class="wow fadeInUp" data-wow-delay="0.8s">
                              <p><?=$nama_instansi." merupakan salah satu rumah sakit umum di wilayah ".$kabupaten." yang berkedudukan di ".$alamat_instansi.". ".$nama_instansi." merupakan perkembangan dari balai pengobatan, klinik dan berada dibawah YASKI. ".$nama_instansi." mendapat izin operasional dengan Kode PPK ".$kode_ppkkemenkes." sejak bulan November 2009 dan diresmikan tanggal 21 februari 2010. ".$nama_instansi." dalam memberikan pelayanannya mengambil filosofi  dasar bahwa pelayanan kesehatan yang baik itu tidak harus mahal dan kalau bisa, harus tidak mahal. Filosofi dasar yang kedua adalah bersama yang tidak mampu kita harus maju. Hal ini memiliki arti bahwa ".$nama_instansi." harus mampu memajukan dirinya dan pihak-pihak yang berhubungan dengan dirinya menuju arah yang lebih baik."?></p>
                          </div>
                          <figure class="profile wow fadeInUp" data-wow-delay="1s">
                               <img src="images/author-image.jpg" class="img-responsive" alt=""/>
                               <figcaption>
                                    <h3>dr. Saiful Umam</h3>
                                    <p>Direktur Utama</p>
                               </figcaption>
                          </figure>
                     </div>
                </div>
           </div>
      </div>
 </section>

 <!-- TEAM -->
 <section id="team" data-stellar-background-ratio="1">
      <div class="container">
           <div class="row">
                <div class="col-md-6 col-sm-6">
                     <div class="about-info">
                          <h2 class="wow fadeInUp" data-wow-delay="0.1s">Dokter Kami</h2>
                     </div>
                </div>
                <div class="clearfix"></div>
                <?php
                    $delay=0.2;
                    $querydokter=bukaquery("select dokter.kd_dokter,left(dokter.nm_dokter,20) as dokter,spesialis.nm_sps,dokter.no_ijn_praktek,pegawai.photo,dokter.no_telp from dokter inner join spesialis on dokter.kd_sps=spesialis.kd_sps inner join pegawai on dokter.kd_dokter=pegawai.nik where dokter.status='1' and dokter.kd_dokter<>'-' group by spesialis.nm_sps limit 5");
                    while($rsquerydokter = mysqli_fetch_array($querydokter)) {
                        echo "<div class='col-md-4 col-sm-6'>
                                <div class='team-thumb wow fadeInUp' data-wow-delay='".$delay."s'>
                                     <img alt='Photo' src='http://".host()."/webapps/penggajian/$rsquerydokter[4]' class='img-responsive' />
                                      <div class='team-info'>
                                           <h3>$rsquerydokter[1]</h3>
                                           <p>$rsquerydokter[2]</p>
                                           <div class='team-contact-info'>
                                                <p><i class='fa fa-phone'></i> HP/Telp. $rsquerydokter[5] </p>
                                                <p><i class='fa fa-envelope-o'></i> No.SIP. $rsquerydokter[3] </p>
                                           </div>
                                           <ul class='social-icon'>
                                                <li><a href='#' class='fa fa-linkedin-square'></a></li>
                                                <li><a href='#' class='fa fa-envelope-o'></a></li>
                                           </ul>
                                      </div>
                                </div>
                                <br/>
                           </div>";
                        $delay=$delay+0.2;
                    }
                ?>
                <div class="col-md-4 col-sm-6">
                     <div class="wow fadeInUp" data-wow-delay="<?=$delay;?>s">
                         <br/><br/><br/><br/><center><a href='index.php?act=DokterKami' class="btn btn-warning" >Tampilkan Semua Dokter</a></center>
                     </div>
                </div>

           </div>
      </div>
 </section>

 <!-- Jadwal -->
 <section id="news" data-stellar-background-ratio="2.5">
    <div class="container">
         <div class="row">
              <div class="col-md-12 col-sm-12">
                   <div class="section-title wow fadeInUp" data-wow-delay="0.1s">
                        <h2>Jadwal Praktek Dokter</h2>
                   </div>
                   <div class="news-thumb wow fadeInUp" data-wow-delay="0.4s">
                       <form id="carikeyword" name="frmCariJadwal" method="post" action="" enctype=multipart/form-data>
                           <table width="100%" border='0' align="center">
                               <tr class="head">
                                  <td width="15%" align="right"><label for="keyword">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="69%"><input name="keyword" id="keyword" class="form-control" value="" size="65" maxlength="250" /></td>
                                  <td width="15%" align="left">&nbsp;<input name="BtnKeyword" type=submit class="btn btn-warning" value="Cari"></td>
                               </tr>
                           </table>
                       </form>
                       <div id='hasilcari'></div>
                   </div>
              </div>
         </div>
    </div>
 </section>


 <!-- MAKE AN APPOINTMENT -->
 <section id="appointment" data-stellar-background-ratio="3">
      <div class="container">
           <div class="row">
                <div class="col-md-6 col-sm-6">
                     <img src="images/appointment-image.jpg" class="img-responsive" alt="">
                </div>
                <div class="col-md-6 col-sm-6">
                     <form id="appointment-form" role="form" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
                          <div class="section-title wow fadeInUp" data-wow-delay="0.4s">
                               <h2><center>Buat Janji/Booking</center></h2>
                          </div>
                          <div class="wow fadeInUp" data-wow-delay="0.8s">
                               <div class="col-md-12 col-sm-12">
                                    <label for="nama">Nama</label>
                                    <input type="text" class="form-control" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" name="nama" maxlength="40" placeholder="Nama Anda">
                                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                               </div>
                               <div class="col-md-12 col-sm-12">
                                    <label for="alamat">Alamat</label>
                                    <input type="text" class="form-control" id="alamat" name="alamat" maxlength="200" placeholder="Alamat Anda">
                               </div>
                               <div class="col-md-6 col-sm-6">    
                                    <label for="nohp">Nomor HP/Telephone</label>
                                    <input type="tel" class="form-control" id="phone" name="nohp" maxlength="40" placeholder="Nomor HP/Telephone Anda">
                               </div>
                               <div class="col-md-6 col-sm-6">
                                    <label for="email">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" maxlength="50" placeholder="Email Anda">
                               </div>
                               <div class="col-md-6 col-sm-6">
                                    <label for="tanggal">Pilih Tanggal</label>
                                    <table width="100%">
                                        <tr>
                                            <td>
                                               <select name="TglDaftar" class="form-control">
                                                    <?php
                                                       loadTglnow();
                                                    ?>
                                               </select>
                                            </td>
                                            <td>
                                                <select name="BlnDaftar" class="form-control">
                                                     <?php
                                                        loadBlnnow();
                                                     ?>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="ThnDaftar" class="form-control">
                                                     <?php
                                                        loadThnnow();
                                                     ?>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                               </div>
                               <div class="col-md-6 col-sm-6">
                                    <label for="poli">Poliklinik/Unit Penunjang</label>
                                    <select name="poli" class="form-control">
                                         <?php
                                            $querypoli=bukaquery("SELECT * from poliklinik order by nm_poli");
                                            while($rsquerypoli = mysqli_fetch_array($querypoli)) {
                                                echo "<option value='$rsquerypoli[0]'>$rsquerypoli[1]</option>";
                                            }
                                        ?>
                                    </select>
                               </div>
                               <div class="col-md-12 col-sm-12">
                                    <label for="pesan">Tambahan Pesan</label>
                                    <textarea class="form-control" rows="3" maxlength="400" id="message" name="pesan" placeholder="Tambahan Pessan"></textarea>
                                    <button type="submit" class="form-control" id="cf-submit" name="btnBooking">Kirimkan</button>
                               </div>
                               <div class="col-md-12 col-sm-12">
                                   <label>Sudah pernah periksa sebelumnya? Klik <a href='index.php?act=LoginPasien' class="btn btn-warning" >Log In</a> jika pernah.</label><br/><br/>
                               </div>
                          </div>
                    </form>
                </div> 
           </div>
      </div>
 </section>

 <section id="google-map">
      <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3911.449032219332!2d110.30504256997102!3d-7.860565684814225!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x88ca51d8f3d715e5!2sWarung%20RERE!5e0!3m2!1sid!2sid!4v1588857905294!5m2!1sid!2sid" width="100%" height="350" frameborder="0" style="border:0" allowfullscreen="true"></iframe>
 </section>     

 