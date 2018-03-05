<!DOCTYPE html>

<?php
 //fitur update kamar aplicare ini adalah penyempurnaan dari kontribusi Mas Tirta dari RSUK Ciracas Jakarta Timur
 session_start();
 require_once('../conf/conf.php');
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

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="css/default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="conf/validator.js"></script>
    <meta http-equiv="refresh" content="100"/>

    <title>Dashboard SIMRS KHANZA HMS</title>

    <!-- Bootstrap Core CSS -->
    <link href="asset/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="asset/css/sb-admin.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="asset/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    
</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.php">SIMRS KhanzaHMS DASHBOARD V.1</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    
                    <ul class="dropdown-menu message-dropdown">
                                             
                    </ul>
                </li>
                
                
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li>
                        <a href="index.php"><i class="fa fa-fw fa-dashboard"></i> Kunjungan Pasien</a>
                    </li>
					<li>
                        <a href="jadwaldokter.php"><i class="fa fa-fw fa-bar-chart-o"></i> TT / Jadwal</a>
                    </li>
                    
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 align="middle" class="page-header">
                            Dashboard Informasi Layanan RSUD MUKOMUKO
                        </h1>
						<?php $date = Date("d-m-Y ");  echo date('d F Y', strtotime($date));?>
						</div>
            <!-- /.container-fluid -->
<section id="content">
      <div class="container-fluid">
          <div class="row">
            <div class="col-lg-6">
              <div class="text-center">
                <h2><img src="assets/spesialis.png" alt="" style="width: 30px; height: 28px;">&nbsp;Jadwal Dokter Spesialis</h2>
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
                      $_sql="SELECT dokter.nm_dokter,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai 
                          FROM jadwal INNER JOIN dokter INNER JOIN poliklinik ON dokter.kd_dokter=jadwal.kd_dokter 
                          AND jadwal.kd_poli=poliklinik.kd_poli WHERE hari_kerja = '$namahari'" ;  
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
            <div class="col-lg-6">
              <div class="text-center">
                <h2><img src="assets/ranap.png" alt="" style="width: 30px; height: 28px;">&nbsp;Informasi Ruang Rawat Inap</h2>
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
                  $_sql="Select * From bangsal where status='1' and kd_bangsal in(select kd_bangsal from kamar)" ;  
                  $hasil=bukaquery($_sql);

                  while ($data = mysqli_fetch_array ($hasil)){
                    echo "<tr class='isi7' >
                        <td align='left'><b>".$data['nm_bangsal']."</b></td>
                        <td align='center'>
                             <font color='gren'>
                              <b>";
                               $data2=mysqli_fetch_array(bukaquery("select count(kd_bangsal) from kamar where kamar.statusdata='1' and kd_bangsal='".$data['kd_bangsal']."'"));
					       echo $data2[0];
                        echo "</b>
                              </font>
                        </td>
                        <td align='center'>
                             <font color='red'>
                              <b>";
                             $data2=mysqli_fetch_array(bukaquery("select count(kd_bangsal) from kamar where kamar.statusdata='1' and kd_bangsal='".$data['kd_bangsal']."' and status='ISI'"));
						   echo $data2[0];
                        echo "</b>
                              </font>
                        </td>
                        <td align='center'>
                              <font color='#FF8C00'>
                              <b>";
                             $data2=mysqli_fetch_array(bukaquery("select count(kd_bangsal) from kamar where kamar.statusdata='1' and kd_bangsal='".$data['kd_bangsal']."' and status='KOSONG'"));
						   echo $data2[0];
                        echo "</b>
                             </font>
                        </td>
                      </tr> ";
                  }
                  updateAplicare();
                ?>
              </table>
             </div>
            </div>

          </div>
      </div>
    </section>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="asset/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="asset/js/bootstrap.min.js"></script>

</body>

</html>
