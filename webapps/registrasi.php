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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Informasi Jadwal Dokter</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="css/default.css" rel="stylesheet" type="text/css" />
<!--
	body {
		background-image: url();
		background-repeat: no-repeat;
		background-color: #693;
	}
	-->
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

    <form name="frmregistrasi" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <div id="post">
            <table width="100%">
                <caption><h1>Anjungan Registrasi Mandiri Pasien</h1></caption>
                <tr>
                    <td align="center">
                        <table align="center">                        
                            <tr class="head">
                                <td>No.RM Pasien</td>
                                <td>:</td>
                                <td>
                                    <input type="text" class="text" name="norm" value="" size="25" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" autofocus/>
                                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                                </td>
                                <td><a href=?page=PasienBaru><font size="3" color="#ff5555">[Pasien Baru]</font></a></td>
                            </tr>                              
                        </table>                        
                        <?php
                            $norm   = trim(isset($_POST['norm']))?trim($_POST['norm']):NULL;
                            if (!empty($norm)) {
                                $norm2=getOne("select * from pasien where no_rkm_medis='$norm'");
                                if (empty($norm2)) {
                                    echo "Maaf, data pasien dengan No.RM tersebut tidak ditemukan.<br>
                                    Bagi pasien yang baru pertama kali periksa, silahkan klik pada \"Pasien Baru\"  ";
                                }else{
                                    echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?page=PilihPoli&Pasien=$norm2'></head><body></body></html>";
                                }
                            }     
                        ?>
                    </td>        
                </tr>
            </table>   
        </div>
    </form>        
