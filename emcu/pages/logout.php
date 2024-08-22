<?php	
     session_start();
     $_SESSION["ses_emcu"]=null;
     unset($_SESSION["ses_emcu"]); 
     session_destroy();
     exit(header("Location:../index.php?act=LoginPasien"));
?>
