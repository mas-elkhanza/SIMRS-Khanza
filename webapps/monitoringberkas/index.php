<?php
 session_start();
 require_once('conf/command.php');
 require_once('../conf/conf.php');
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
?>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><?php title();?></title>
    <link href="css/default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="library/tiny_mce/tiny_mce.js"></script>
    <script type="text/javascript" src="conf/validator.js"></script>
    <script type="text/javascript" src="js/jquery/jquery.js"></script>
    <script type="text/javascript" src="js/jquery/jquery.dropdown.js"></script>
    <script>
        function PopupCenter(pageURL, title,w,h) {
            var left = (screen.width/2)-(w/2);
            var top = (screen.height/2)-(h/2);
            var targetWin = window.open (pageURL, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
            
        }
    </script>
</head>
<body>
    <div id="mainContent">
        <?php actionPages();?>
    </div>    
</body>
</html>

