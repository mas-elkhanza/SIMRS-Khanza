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
    <title>Jadwal Operasi Pasien</title>
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
              <td width='24%'><div align='center'><font size='5'><b>Pasien</b></font></div></td>
              <td width='5%'><div align='center'><font size='5'><b>Umur</b></font></div></td>
              <td width='3%'><div align='center'><font size='5'><b>J.K.</b></font></div></td>
              <td width='8%'><div align='center'><font size='5'><b>Mulai</b></font></div></td>
              <td width='8%'><div align='center'><font size='5'><b>Selesai</b></font></div></td>
              <td width='10%'><div align='center'><font size='5'><b>Status</b></font></div></td>
              <td width='22%'><div align='center'><font size='5'><b>Operasi</b></font></div></td>
              <td width='20%'><div align='center'><font size='5'><b>Operator</b></font></div></td>
         </tr>

	<?php  
	    
		$_sql="select booking_operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,booking_operasi.tanggal,
                     booking_operasi.jam_mulai,booking_operasi.jam_selesai,booking_operasi.status,booking_operasi.kd_dokter,
                     dokter.nm_dokter,booking_operasi.kode_paket,paket_operasi.nm_perawatan,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.jk
                     from booking_operasi inner join reg_periksa inner join pasien inner join paket_operasi inner join dokter 
                     on booking_operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                     and booking_operasi.kd_dokter=dokter.kd_dokter and booking_operasi.kode_paket=paket_operasi.kode_paket 
                     where tanggal='".date("Y-m-d", $tanggal)."' order by booking_operasi.tanggal,booking_operasi.jam_mulai" ;  
		$hasil=bukaquery($_sql);

		while ($data = mysqli_fetch_array ($hasil)){
			echo "<tr class='isi7' >
                                <td align='left'><font size='5' color='#BB00BB' face='Tahoma'>".$data['no_rkm_medis']." ".$data['nm_pasien']."</font></td>
                                <td align='center'><font size='5' color='gray' face='Tahoma'>".$data['umur']."</font></td>
                                <td align='center'><font color='#DDDD00' size='5'  face='Tahoma'>".$data['jk']."</font></td>
                                <td align='center'><font color='gren' size='5'  face='Tahoma'>".$data['jam_mulai']."</font></td>
                                <td align='center'><font color='red' size='5'  face='Tahoma'>".$data['jam_selesai']."</font></td>
                                <td align='center'><font color='#56789A' size='5'  face='Tahoma'>".$data['status']."</font></td>
                                <td align='center'><font color='#A98765' size='5'  face='Tahoma'>".$data['nm_perawatan']."</font></td>
                                <td align='center'><font color='blue' size='5'  face='Tahoma'>".$data['nm_dokter']."</font></td>
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
