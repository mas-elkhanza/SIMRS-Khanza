<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Informasi Jadwal Dokter</title>
	<link href="style.css" rel="stylesheet" type="text/css" />
	<link href="css/default.css" rel="stylesheet" type="text/css" />
</head>

<body onload="MM_preloadImages('images/menu1.jpg')">
	
	<div id="main">
	<div id="head"> <img src="images/header.jpg" /> </div>
    
    <div id="menutop">
    
     
     <!-- Menu Dropdown -->
   <div>
   
   <ul id="menu-bar">
 <li class="active"><a href="/webapps/index.php">Home</a></li>
 <li><a href="#">Info Layanan</a>
  <ul>
   <li><a href="#">Jadwal Dokter</a></li>
   <li><a href="/webapps/bed.php">Ketersediaan Kamar</a></li>
   <li><a href="/webapps/bed2.php">Ketersediaan Kamar/Kelas</a></li>
   <li><a href="/webapps/registrasi.php">Registrasi Pasien</a></li>
   <li><a href="#">Products Sub Menu 4</a></li>
  </ul>
 </li>
 <li><a href="#">Services</a>
  <ul>
   <li><a href="#">Services Sub Menu 1</a></li>
   <li><a href="#">Services Sub Menu 2</a></li>
   <li><a href="#">Services Sub Menu 3</a></li>
   <li><a href="#">Services Sub Menu 4</a></li>
  </ul>
 </li>
 <li><a href="#">About</a></li>
 <li><a href="#">Contact Us</a></li>
</ul>
   </div>
   </div>

<!-- intisari -->
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
 $tanggal= mktime(date("m"),date("d"),date("Y"));
 date_default_timezone_set('Asia/Jakarta');
 $jam=date("H:i");
?>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="css/default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="conf/validator.js"></script>
    <meta http-equiv="refresh" content="20"/>
    <title>Informasi Ketersediaan Kamar</title>
    <script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
    <script src="Scripts/AC_ActiveX.js" type="text/javascript"></script>
	<style type="text/css">
	<!--
	body {
		background-image: url();
		background-repeat: no-repeat;
		background-color: #693;
	}
	-->
	</style>
</head>
     <?php
		$setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
		echo "   
		   <table width='100%' align='center' border='0' class='tbl_form' cellspacing='0' cellpadding='0'>
			 <tr>
				<td>
				   <center>
					    
					  </font> 
					  <font size='5' color='Black' face='Tahoma' >".date("d-M-Y", $tanggal)."  ". $jam."</font>
					  <br><br>
				   </center>
				</td>   
				<td  width='10%' align='left'>
					&nbsp;
				</td>  
				<td  width='10%' align='left' valign='top'>
					
				</td>                                                          
			 </tr>
		  </table> "; 
	?>
	<table width='100%' bgcolor='FFFFFF' border='0' align='center' cellpadding='0' cellspacing='0'>
	     <tr class='head5'>
              <td width='100%'><div align='center'></div></td>
         </tr>
    </table>
	<table width='100%' bgcolor='FFFFFF' border='0' align='center' cellpadding='0' cellspacing='0'>
	     <tr class='head4'>
              <td width='35%'><div align='center'><font size='5'><b>NAMA DOKTER</b></font></div></td>
              <td width='35%'><div align='center'><font size='5'><b>POLIKLINIK</b></font></div></td>
              <td width='15%'><div align='center'><font size='5'><b>JAM MULAI</b></font></div></td>
              <td width='15%'><div align='center'><font size='5'><b>JAM SELESAI</b></font></div></td>
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
		$_sql="Select dokter.nm_dokter,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai 
				from jadwal inner join dokter inner join poliklinik on dokter.kd_dokter=jadwal.kd_dokter 
				and jadwal.kd_poli=poliklinik.kd_poli where jadwal.hari_kerja='$namahari'" ;  
		$hasil=bukaquery($_sql);

		while ($data = mysqli_fetch_array ($hasil)){
			echo "<tr class='isi7' >
					<td align='left'><font size='5' color='#BB00BB' face='Tahoma'>".$data['nm_dokter']."</font></td>
					<td align='center'><font size='5' color='gray' face='Tahoma'>".$data['nm_poli']."</font></td>
					<td align='center'><font color='#DDDD00' size='5'  face='Tahoma'>".$data['jam_mulai']."</font></td>
					<td align='center'><font color='gren' size='5'  face='Tahoma'>".$data['jam_selesai']."</font></td>
				</tr> ";
		}
	?>
	</table>
	<table width='100%' bgcolor='FFFFFF' border='0' align='center' cellpadding='0' cellspacing='0'>
	     <tr class='head5'>
              <td width='100%'><div align='center'></div></td>
         </tr>
    </table>
	<img src="ft-2.jpg" alt="bar-pic" width="100%" height="83">
<!-- intisari -->

<div id="clear"> </div>
 <h5 align="center">Created by Team Sistem Informasi Rumah Sakit (SIMRS)- 2017</h3>
</div>
</body>
</html>
