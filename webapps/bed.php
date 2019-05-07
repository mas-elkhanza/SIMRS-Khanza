<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Informasi Ketersediaan Kamar</title>
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
   <li><a href="/webapps/jadwaldokter.php">Jadwal Dokter</a></li>
   <li><a href="#">Ketersediaan Kamar</a></li>
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

<?php
 //fitur update kamar aplicare ini adalah penyempurnaan dari kontribusi Mas Tirta dari RSUK Ciracas Jakarta Timur
 session_start();
 require_once('conf/conf.php');
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
 date_default_timezone_set("Asia/Bangkok");
 $tanggal= mktime(date("m"),date("d"),date("Y"));
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
<body>

<div align="left">
	<script type="text/javascript">
		AC_AX_RunContent( 'width','32','height','32' ); //end AC code
	</script>
	<noscript>
       <object width="32" height="32">
         <embed width="32" height="32"></embed>
       </object>
     </noscript>
     <?php
		$setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
		echo "   
		   <table width='100%' align='center' border='0' class='tbl_form' cellspacing='0' cellpadding='0'>
			  <tr>
				<td  width='10%' align='right' valign='center'>
					
				</td>
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
              <td width='40%'><div align='center'><font size='5'><b>NAMA KAMAR</b></font></div></td>
              <td width='20%'><div align='center'><font size='5'><b>JUMLAH BED</b></font></div></td>
              <td width='20%'><div align='center'><font size='5'><b>BED TERISI</b></font></div></td>
              <td width='20%'><div align='center'><font size='5'><b>BED KOSONG</b></font></div></td>
         </tr>

	<?php  
		$_sql="Select * From bangsal where status='1' and kd_bangsal in(select kd_bangsal from kamar)" ;  
		$hasil=bukaquery($_sql);

		while ($data = mysqli_fetch_array ($hasil)){
			echo "<tr class='isi7' >
					<td align='left'><font size='5' color='#BB00BB' face='Tahoma'><b>".$data['nm_bangsal']."</b></font></td>
					<td align='center'>
					     <font size='6' color='red' face='Tahoma'>
					      <b>";
					       $data2=mysqli_fetch_array(bukaquery("select count(kd_bangsal) from kamar where kamar.statusdata='1' and kd_bangsal='".$data['kd_bangsal']."'"));
					       echo $data2[0];
					echo "</b>
					      </font>
					</td>
					<td align='center'>
					     <font color='#DDDD00' size='6'  face='Tahoma'>
					      <b>";
						   $data2=mysqli_fetch_array(bukaquery("select count(kd_bangsal) from kamar where kamar.statusdata='1' and kd_bangsal='".$data['kd_bangsal']."' and status='ISI'"));
						   echo $data2[0];
					echo "</b>
					      </font>
					</td>
					<td align='center'>
					      <font color='gren' size='6'  face='Tahoma'>
					      <b>";
						   $data2=mysqli_fetch_array(bukaquery("select count(kd_bangsal) from kamar where kamar.statusdata='1' and kd_bangsal='".$data['kd_bangsal']."' and status='KOSONG'"));
						   echo $data2[0];
					echo "</b>
					     </font>
					</td>
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
	<div id="clear"> </div>
 <h5 align="center">Created by Team Sistem Informasi Rumah Sakit (SIMRS)- 2017</h3>
</body>
