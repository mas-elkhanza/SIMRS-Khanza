<?php	
	session_start();
	unset($id);
	unset($nama);
	session_destroy();
	require_once "conf/command.php";
	if (cekSessiAdmin())
	{
		session_unregister("ses_admin");
	}
	header("Location:index.php");
	
?>