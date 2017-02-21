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
    <meta http-equiv="refresh" content="5"/>
    <title>Informasi Ketersediaan Kamar</title>
</head>
<body bgcolor='#ffffff'>
	<?php
		$setting=  mysql_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
		echo "   
		   <table width='100%' align='center' border='0' class='tbl_form' cellspacing='0' cellpadding='0'>
			  <tr>
				<td  width='10%' align='right' valign='top'>
					<img width='70' height='70' src='data:image/jpeg;base64,". base64_encode($setting['logo']). "'/>
				</td>
				<td>
				   <center>
					  <font size='5' color='#AA00AA' face='Tahoma'>".$setting["nama_instansi"]."</font><br>
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
			 </tr>
		  </table> "; 
	?>
	
	<table width='100%' bgcolor='FFFFFF' border='0' align='center' cellpadding='0' cellspacing='0'>
        <tr class='head4'>
              <td width='40%'><div align='center'><font size='5'><b>NAMA KAMAR</b></font></div></td>
              <td width='20%'><div align='center'><font size='5'><b>JUMLAH BED</b></font></div></td>
              <td width='20%'><div align='center'><font size='5'><b>BED TERISI</b></font></div></td>
              <td width='20%'><div align='center'><font size='5'><b>BED KOSONG</b></font></div></td>
		</tr>

	<?php  
		$_sql="Select * From bangsal where kd_bangsal in(select kd_bangsal from kamar)" ;  
		$hasil=bukaquery($_sql);

		while ($data = mysql_fetch_array ($hasil)){
			echo "<tr class='isi7' >
					<td align='left'><font size='5' color='#BB00BB' face='Tahoma'><b>".$data['nm_bangsal']."</b></font></td>
					<td align='center'>
					     <font size='6' color='red' face='Tahoma'>
					      <b>";
					       $data2=mysql_fetch_array(bukaquery("select count(kd_bangsal) from kamar where kd_bangsal='".$data['kd_bangsal']."'"));
					       echo $data2[0];
					echo "</b>
					      </font>
					</td>
					<td align='center'>
					     <font color='#DDDD00' size='6'  face='Tahoma'>
					      <b>";
						   $data2=mysql_fetch_array(bukaquery("select count(kd_bangsal) from kamar where kd_bangsal='".$data['kd_bangsal']."' and status='ISI'"));
						   echo $data2[0];
					echo "</b>
					      </font>
					</td>
					<td align='center'>
					      <font color='gren' size='6'  face='Tahoma'>
					      <b>";
						   $data2=mysql_fetch_array(bukaquery("select count(kd_bangsal) from kamar where kd_bangsal='".$data['kd_bangsal']."' and status='KOSONG'"));
						   echo $data2[0];
					echo "</b>
					     </font>
					</td>
				</tr> ";
		}
	?>
    </table>
</body>
