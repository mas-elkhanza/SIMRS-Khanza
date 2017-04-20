<?php
	if(isset($_POST['no_rkm_medis']) && isset($_POST['stts_rm']) && isset($_POST['simpan']))
	{
		include('../koneksi.php');
		
		$id = $_POST['no_rkm_medis'];
		$stts_rm=$_POST['stts_rm'];

		$input=mysql_query("UPDATE rm SET  stts_rm = '$stts_rm' WHERE no_rkm_medis='$id'") or die(mysql_error());

		if($input){

			echo "DATA BERHASIL DIUPDATE"; 
			echo "&nbsp;&nbsp;<a href='rm_kstatus.php'>Home</a>";
		}
		else{
			echo "GAGAL MENGUPDATE DATA";
			echo "<a href='rm_kstatus.php'>Back</a>";
		}
	}
		else
	{
	}
?>
