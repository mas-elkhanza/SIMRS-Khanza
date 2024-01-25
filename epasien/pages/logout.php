<?php	
     session_start();
     $_SESSION["ses_pasien"]=null;
     unset($_SESSION["ses_pasien"]); 
     session_destroy();
     exit(header("Location:../index.php?act=LoginPasien"));
?>
