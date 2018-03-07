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

<html lang="en">

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
						
						
							<div class="col-lg-12">
							<div id="kunjungan" >
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
						
						$jumlah=array();
						$poli=array();
						$date = Date("Y-m-d "); 
						$sql = "select poliklinik.nm_poli, count(*) as jumlah from reg_periksa INNER JOIN poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli WHERE reg_periksa.tgl_registrasi='$date' group by reg_periksa.kd_poli  order by count(*) desc ";
						$hasil=bukaquery($sql);

						while ($data = mysqli_fetch_array ($hasil)){
                        
                            $jumlah[]=intval($data['jumlah']);
                            $poli[]= $data['nm_poli'];
                        }
							updateAplicare();				
						?>
						
							<script src="js/highcharts.js"></script>
                            <script src="modules/exporting.js"></script>
							<script type="text/javascript">
							Highcharts.chart('kunjungan', {
								chart: {
									type: 'area'
									},
								title: {
									text: 'Grafik Kunjungan Pasien Hari Ini'
								},
								subtitle: {
									text: <?=json_encode($dates);?>
								},
								xAxis: {
								categories: <?=json_encode($poli);?> ,
								
								title: {
									enabled: false
									}
								},
								yAxis: {
									title: {
									text: 'Jumlah Pasien'
									},
									labels: {
										formatter: function () {
											return this.value;
										}
									}
								},
								tooltip: {
									split: true,
									valueSuffix: ''
								},
								plotOptions: {
									area: {
								stacking: 'normal',
									lineColor: '#666666',
									lineWidth: 1,
								marker: {
									lineWidth: 1,
									lineColor: '#666666'
									}
								}
							},
							series: [{
								name: 'Poliklinik dan IRNA',
								data: <?=json_encode($jumlah);?>
								}]
							});		
							</script>
							
							
							</div>
								
						
							
							
							</div>
						</div>
                    
                <!-- /.row -->

            </div>
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
