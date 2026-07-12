<?php	
     session_start();
     $_SESSION["ses_eksekutif"]=null;
     unset($_SESSION["ses_eksekutif"]); 
     session_destroy();
     exit(header("Location:../index.php"));
?>
