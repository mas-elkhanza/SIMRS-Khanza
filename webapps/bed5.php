<?php
 //fitur update kamar aplicare ini adalah penyempurnaan dari kontribusi Mas Tirta dari RSUK Ciracas Jakarta Timur
 session_start();
 require_once('conf/conf.php');
 require_once('updateaplicare.php');
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
 date_default_timezone_set("Asia/Bangkok");
 $tanggal= mktime(date("m"),date("d"),date("Y"));
 $jam=date("H:i");
?>
<!doctype html>
<html lang="en">
<head>

    <title>Layar Informasi</title>

    <!-- Meta START -->
    <link rel="icon" href="asset/img/rs.png" type="image/x-icon">
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <link type="text/css" rel="stylesheet" href="asset/css/materialize.min.css"  media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="asset/css/jquery-ui.css"  media="screen,projection"/>
    <link rel="stylesheet" href="asset/css/marquee.css" />
    <link rel="stylesheet" href="asset/css/example.css" />
    <link rel="stylesheet" href="asset/css/ok.css" />
    <style type="text/css">
        .bg::before {
            content: '';
            background-image: url('./asset/img/background.jpg');
            background-size: cover;
            background-repeat: no-repeat;
            background-attachment: scroll;
            position: fixed;
            z-index: -1;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            opacity: 0.10;
            filter:alpha(opacity=10);
        }
    </style>
    <!-- Global style END -->

</head>

<!-- Body START -->
<body class="bg">

<!-- Header START -->
<header>

<nav class="deep-orange accent-3">
    <div class="nav-wrapper">
        <ul class="center hide-on-med-and-down" id="nv">
            <li>
                <a href="./" class="ams hide-on-med-and-down"><i class="material-icons md-36">local_hospital</i> Informasi</a>
            </li>
            <li class="right" style="margin-right: 10px;">
                <i class="material-icons">perm_contact_calendar</i>
                <a href="" class="white-text">
                    <?php
                      //menentukan hari
                      $a_hari = array(1=>"Senin","Selasa","Rabu","Kamis","Jumat", "Sabtu","Minggu");
                      $hari = $a_hari[date("N")];

                      //menentukan tanggal
                      $tanggal = date ("j");

                      //menentukan bulan
                      $a_bulan = array(1=>"Januari","Februari","Maret", "April", "Mei", "Juni","Juli","Agustus","September","Oktober", "November","Desember");
                      $bulan = $a_bulan[date("n")];

                      //menentukan tahun
                      $tahun = date("Y"); 

                      //dan untuk menampilkan nya dengan format contoh Jumat, 22 Februari 2013
                      echo $hari . ", " . $tanggal ." ". $bulan ." ". $tahun;

                    ?>
                </a>
                <i class="material-icons md-12">query_builder</i>  
                <a href="" class="white-text" id="jam"></a>
          </li>
        </ul>
    </div>
</nav>




</header>
<!-- Header END -->

<!-- Main START -->
<main>

    <!-- container START -->
    <div class="container-fluid">
        <!-- Row START -->
        <div class="row">
           <?php $setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
            ?>
            <div class="col s8" id="header-instansi">
                <div class="card deep-orange accent-3 white-text">
                    <div class="card-content">
                        <div class="left">
                            <img class="logo" src="data:image/jpeg;base64,<?php echo base64_encode($setting['logo']);?>"/>
                        </div>
                        <h5 class="ins"><?php echo $setting["nama_instansi"] ?></h5>
                        <p class="almt"><?php echo $setting["alamat_instansi"] ?>, <?php echo $setting["kabupaten"] ?>, <?php echo $setting["propinsi"] ?>, <?php echo $setting["kontak"] ?>, <?php echo $setting["email"] ?>
                            
                        </p>
                    </div>
                </div>
            </div>
            <div class="col s4">
                <video autoplay class="player">
                    <source src="asset/rsia.mp4" type="video/mp4">
                </video>
            </div>
        </div>
        <!-- Row END -->
    </div>
    <!-- container END -->
    <div class="container-fluid" id="data">
        
    </div>

</main>
<!-- Main END -->

<!-- Include Footer START -->

<!-- Footer START -->
<footer class="page-footer">
    <div class="footer-copyright deep-orange accent-3 white-text">
        <div class="container simple-marquee-container" id="footer">
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
<!-- Footer END -->

<!-- Javascript START -->
<script type="text/javascript" src="asset/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="asset/js/materialize.min.js"></script>
<script type="text/javascript" src="asset/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="asset/js/bootstrap.min.js"></script>
<script data-pace-options='{ "ajax": false }' src='asset/js/pace.min.js'></script>
<script type="text/javascript" src="asset/js/marquee.js"></script>
<script type="text/javascript">
   window.onload = function() { jam(); }

   function jam() {
    var e = document.getElementById('jam'),
    d = new Date(), h, m, s;
    h = d.getHours();
    m = set(d.getMinutes());
    s = set(d.getSeconds());

    e.innerHTML = h +':'+ m +':'+ s;

    setTimeout('jam()', 1000);
   }

   function set(e) {
    e = e < 10 ? '0'+ e : e;
    return e;
  }
</script>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script> 
<script type="text/javascript"> 
    var auto_refresh = setInterval( function() { 
        $('#data').load('data_jadwal_kamar.php').fadeIn("slow"); }, 5000); 
</script>

</body>
<!-- Body END -->

</html>
