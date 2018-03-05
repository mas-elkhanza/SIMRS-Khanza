<?php
 session_start();
 require_once('conf/conf.php');
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
    <meta http-equiv="refresh" content="100"/>
    <title>Jadwal Praktek Dokter</title>
    <script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
    <script src="Scripts/AC_ActiveX.js" type="text/javascript"></script>
	<style type="text/css">
	<!--
	body {
		background-image: url();
		background-repeat: no-repeat;
		background-color: #FFFFCC;
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
					<img width='90' height='90' src='data:image/jpeg;base64,". base64_encode($setting['logo']). "'/>
				</td>
				<td>
				   <center>
					  <font size='7' color='#AA00AA' face='Tahoma'>".$setting["nama_instansi"]."</font><br>
					  <font size='5' color='#AA00AA' face='Tahoma'>
						  ".$setting["alamat_instansi"].", ".$setting["kabupaten"].", ".$setting["propinsi"]."<br>   
					  </font> 
					  <font size='5' color='#AAAA00' face='Tahoma' >".date("d-M-Y", $tanggal)."  ". $jam."</font>
					  <br><br>
				   </center>
				</td>   
				<td  width='10%' align='left'>
					&nbsp;
				</td>  
				<td  width='10%' align='left' valign='top'>
					<img width='180' height='130' src='header-kanan.jpg'/>
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
</body>
