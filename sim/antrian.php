<!DOCTYPE html>

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

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="css/default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="conf/validator.js"></script>
    <meta http-equiv="refresh" content="10"/>

    <title>Dashboard SIMRS KHANZA HMS</title>

    <!-- Bootstrap Core CSS -->
    <link href="asset/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="asset/css/sb-admin.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="asset/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    
<!-- CSS untuk mempercantik tampilan-->
 <style type="text/css">
td{
 text-align: center;
}
</style>

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
                        <a href="index.php"><i class="fa fa-fw fa-dashboard"></i> Dashboard</a>
                    </li>
					<li>
                        <a href="kunjungan.php"><i class="fa fa-fw fa-dashboard"></i> Kunjungan Pasien</a>
                    </li>
					<li>
                        <a href="jadwaldokter.php"><i class="fa fa-fw fa-bar-chart-o"></i> TT / Jadwal</a>
                    </li>
					<li>
                        <a href="pendaftaran.php"><i class="fa fa-fw fa-dashboard"></i> Pendaftaran</a>
                    </li>
					<li>
                        <a href="antrian.php"><i class="fa fa-fw fa-dashboard"></i> Antrian</a>
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
						<?php $dates = Date("d-m-Y ");  echo date('d F Y', strtotime($dates));?>
						
						<div class "row">
						
						<section id="content">
      <div class="container-fluid">
          <div class="row">
            <div class="col-lg-12">
              <div class="text-center">
                <h2><img src="assets/spesialis.png" alt="" style="width: 30px; height: 28px;">&nbsp;Daftar Antrian Pasien Poliklinik</h2>
              </div>
			  <fieldset>

 <div style="margin-bottom:15px;" align="right">
  <form action="" method="post">
   <input type="text" name="input_cari" placeholder="Cari Berdasarkan Nama Poliklinik" class="css-input" style="width:250px;" />
   <input type="submit" name="cari" value="Cari" class="btn" style="padding:3px;" margin="6px;" width="50px;"  />
  </form>
 </div>

 <table width="100%" border="1px solid #000" style="border-collapse:collapse;">
  <tr style="background-color:#fc0;">
   <th><center>No Antrian</center></th>
   <th><center>No RM</center></th>
   <th><center>Nama Pasien</center></th>
   <th><center>Alamat</center></th>
   <th><center>Poliklinik</center></th>
  </tr>
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
   $input_cari = @$_POST['input_cari']; 
   $cari = @$_POST['cari'];
	$date = Date("Y-m-d "); 
   // jika tombol cari di klik
   if($cari) {

    // jika kotak pencarian tidak sama dengan kosong
    if($input_cari != "") {
    // query mysql untuk mencari berdasarkan nama negara. .
    $sql = mysql_query("SELECT reg.no_reg, reg.no_rkm_medis, pas.nm_pasien, pas.alamat, reg.kd_poli, pol.nm_poli, reg.stts from 
						(reg_periksa reg left JOIN pasien pas on reg.no_rkm_medis = pas.no_rkm_medis ) 
						LEFT JOIN poliklinik pol on reg.kd_poli = pol.kd_poli where reg.tgl_registrasi='$date' and reg.stts='Belum' and nm_poli like '%$input_cari%'") or die (mysql_error());   
    } else {
    $sql = mysql_query("SELECT reg.no_reg, reg.no_rkm_medis, pas.nm_pasien, pas.alamat, reg.kd_poli, pol.nm_poli, reg.stts from 
						(reg_periksa reg left JOIN pasien pas on reg.no_rkm_medis = pas.no_rkm_medis ) 
						LEFT JOIN poliklinik pol on reg.kd_poli = pol.kd_poli where reg.tgl_registrasi='$$date' and reg.stts='Belum'") or die (mysql_error());
    }
    } else {
    $sql = mysql_query("SELECT reg.no_reg, reg.no_rkm_medis, pas.nm_pasien, pas.alamat, reg.kd_poli, pol.nm_poli, reg.stts from 
						(reg_periksa reg left JOIN pasien pas on reg.no_rkm_medis = pas.no_rkm_medis ) 
						LEFT JOIN poliklinik pol on reg.kd_poli = pol.kd_poli where reg.tgl_registrasi='$date' and reg.stts='Belum'") or die (mysql_error());
    }
	updateAplicare();	
   // mengecek data
   $cek = mysqli_num_rows($sql);
   // jika data kurang dari 1
   if($cek < 1) {
    ?>
     <tr> <!--muncul peringata bahwa data tidak di temukan-->
      <td colspan="7" align="center style="padding:10px;""> Data Tidak Ditemukan</td>
     </tr>
    <?php
   } else {

   // mengulangi data agar tidak hanya 1 yang tampil
   while($data = mysqli_fetch_array($sql)) {

   ?>
   <tr>
	<td><?php echo $data['no_reg'] ?></td>
    <td><?php echo $data['no_rkm_medis'] ?></td>
    <td><?php echo $data['nm_pasien'] ?></td>
	<td><?php echo $data['alamat'] ?></td>
	<td><?php echo $data['nm_poli'] ?></td>
    
    <!--Hanya pemanis tampilan-->
    
   </tr>
  <?php 

  } 
 }
?>
 </table>
</fieldset>
			  
		
            <!-- /.container-fluid -->

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
