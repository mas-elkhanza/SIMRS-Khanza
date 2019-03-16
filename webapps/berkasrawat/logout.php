<?php	
        session_start();
	session_destroy();
	require_once "conf/command.php";
	if (cekSessiAdmin())
	{
	    $_SESSION["ses_kunjung"]="ses_kunjung";
	}
       
	header("Location:index.php");
	
?>
