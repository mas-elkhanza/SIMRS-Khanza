<?php
 //fitur update kamar aplicare ini adalah penyempurnaan dari kontribusi Mas Tirta dari RSUK Ciracas Jakarta Timur
 session_start();
 require_once('conf/conf.php');
 require_once('updateaplicare.php');
 require_once('./updatesiranap.php');
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
 date_default_timezone_set("Asia/Bangkok");
 $tanggal= mktime(date("m"),date("d"),date("Y"));
 $jam=date("H:i");
 $setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
?>
<!DOCTYPE html>
<html>
<head>
  <title><?php echo $setting["nama_instansi"];?></title>
  <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="assets/css/portfolio-item.css" rel="stylesheet">

    <!-- Owl carousel -->
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="assets/marquee.css" />
    <link rel="stylesheet" href="assets/example.css" />

    
</head>
<body>

  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="#"><img src="assets/rs.png" alt="Swimming fish" style="width: 30px; height: 35px;">&nbsp;<?php echo $setting["nama_instansi"];?></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="pull-right"> 
          <a href="" class="navbar-brand">
            <?php $date = Date("d-m-Y H:i:s"); echo $date; ?>
          </a>
        </div>
      </div>
  </nav>

  <div class="wrapper">
    <div class="baner" style="padding-top: 6px;"> 
        <marquee scrollamount="3" direction="left" behavior="alternate">
          <img src="assets/images/galery.jpg" alt="Swimming fish">
          <img src="assets/images/galery.jpg" alt="Swimming fish">
        </marquee>
    </div>

    <section id="content">
      <div class="container-fluid">
          <div class="row">
            <div class="col-lg-7">
              <div class="text-center">
                <h2><img src="assets/spesialis.png" alt="Swimming fish" style="width: 30px; height: 28px;">&nbsp;Jadwal Dokter Spesialis</h2>
              </div>
              <div class="table-responsive">          
                <table class="table">
                  <thead>
                    <tr style="border-top: solid 3px; border-bottom: solid 3px;">
                          <td><b>Nama Dokter</b></td>
                          <td><b>Poliklinik</b></td>
                          <td><b><center>Mulai</center></b></td>
                          <td><b><center>Selesai</center></b></td>
                     </tr>
                  </thead>
                  <tbody>
                    <?php  
                      $hari=getOne("select DAYNAME(current_date())");
                        $namahari="";
                        if($hari=="Sunday"){
                        $namahari="AKHAD";
                      }else if($hari=="Monday"){
                        $namahari="SENIN";
                      }else if($hari=="Tuesday"){
                        $namahari="SELASA";
                      }else if($hari=="Wednesday"){
                        $namahari="RABU";
                      }else if($hari=="Thursday"){
                        $namahari="KAMIS";
                      }else if($hari=="Friday"){
                        $namahari="JUMAT";
                      }else if($hari=="Saturday"){
                        $namahari="SABTU";
                      }
                      $_sql="Select dokter.nm_dokter,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai 
                          from jadwal inner join dokter inner join poliklinik on dokter.kd_dokter=jadwal.kd_dokter 
                          and jadwal.kd_poli=poliklinik.kd_poli where jadwal.hari_kerja='$namahari'" ;  
                      $hasil=bukaquery($_sql);

                      while ($data = mysqli_fetch_array ($hasil)){
                        echo "<tr>
                            <td><b>".$data['nm_dokter']."</b></td>
                            <td><b>".$data['nm_poli']."</b></td>
                            <td align='center'><font color='gren'><b>".$data['jam_mulai']."</b></font></td>
                            <td align='center'><font color='red'><b>".$data['jam_selesai']."</b></font></td>
                            </tr> ";
                       }
                    ?>
                  </tbody>
                </table>
             </div>
            </div>
            <div class="col-lg-5">
              <div class="text-center">
                <h2><img src="assets/ranap.png" alt="Swimming fish" style="width: 30px; height: 28px;">&nbsp;Informasi Ruang Rawat Inap</h2>
              </div>
             <div class="table-responsive">          
              <table class="table">
                <thead>
                  <tr style="border-top: solid 3px; border-bottom: solid 3px;">
                        <td align='left'><b>Kelas Kamar</b></td>
                        <td align='center'><b>Jumlah Bed</b></td>
                        <td align='center'><b>Bed Terisi</b></td>
                        <td align='center'><b>Bed Kosong</b></td>
                   </tr>
                </thead>
                <?php  
                  $_sql="Select kelas from kamar where statusdata='1' group by kelas" ;  
                  $hasil=bukaquery($_sql);

                  while ($data = mysqli_fetch_array ($hasil)){
                    echo "<tr class='isi7' >
                        <td align='left'><b>".$data['kelas']."</b></td>
                        <td align='center'>
                             <font color='gren'>
                              <b>";
                               $data2=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kelas='".$data['kelas']."'"));
                               echo $data2[0];
                        echo "</b>
                              </font>
                        </td>
                        <td align='center'>
                             <font color='red'>
                              <b>";
                             $data2=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kelas='".$data['kelas']."' and status='ISI'"));
                             echo $data2[0];
                        echo "</b>
                              </font>
                        </td>
                        <td align='center'>
                              <font color='#FF8C00'>
                              <b>";
                             $data2=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kelas='".$data['kelas']."' and status='KOSONG'"));
                             echo $data2[0];
                        echo "</b>
                             </font>
                        </td>
                      </tr> ";
                  }
                  updateAplicare();
                  //updateSiranap();
                ?>
              </table>
             </div>
            </div>

          </div>
      </div>
    </section>

    <footer class="footer">
      <div class="container-fluid">
          <div class="simple-marquee-container">
            <div class="marquee-sibling">
              Tarif Kamar Umum
            </div>
            <marquee class="marquee" scrollamount="4">
                  <?php 
                    $sql ="SELECT kelas, trf_kamar FROM kamar WHERE statusdata='1' GROUP BY kelas";
                    $hasil=bukaquery($sql);
                    while ($data = mysqli_fetch_array ($hasil)){
                  ?>
                   <span class="marquee-content-items">| <?= $data['kelas'];?> Rp <?= number_format($data['trf_kamar'], 0, ".",",");?></span>
                  <?php } ?>
            </marquee>
          </div>
      </div>
    </footer>

    



  
<script type="text/javascript" src="conf/validator.js"></script>
<meta http-equiv="refresh" content="32"/>
<script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
<script src="Scripts/AC_ActiveX.js" type="text/javascript"></script>
<!-- Footer -->

<!-- Bootstrap core JavaScript -->
<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="assets/vendor/jquery/jquery.min.js"></script>


 </body>

</html>
