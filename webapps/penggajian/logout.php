<?php	
        session_start();
	session_destroy();
	require_once "conf/command.php";
	if (cekSessiAdmin())
	{
	    session_unregister("ses_admin");
	}
        if (cekSessiPegawai())
	{
	    session_unregister("ses_pegawai");
	}
	header("Location:index.php");
	
?>