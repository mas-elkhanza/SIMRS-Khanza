<?php	
     session_start();
     $_SESSION["ses_dokter"]=null;
     unset($_SESSION["ses_dokter"]); 
     session_destroy();
     exit(header("Location:../index.php"));
?>
