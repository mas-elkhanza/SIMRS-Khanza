<?php	
        session_start();
	session_destroy();
	require_once "conf/command.php";
	if (cekSessiAdmin())
	{
	    session_unregister("ses_admin_gambarpemeriksaanendoskopifaringlaring");
	}
       
	header("Location:index.php");
	
?>
