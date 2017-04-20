<?php
	$Open = mysql_connect("localhost","root","");
		if (!$Open){
		die ("Koneksi ke Engine MySQL Gagal !<br>");
		}
	$Koneksi = mysql_select_db("sik");
		if (!$Koneksi){
		die ("Koneksi ke Database Gagal !");
		}
?>

