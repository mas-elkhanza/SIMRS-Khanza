<?php
 session_start();
 require_once('conf/command.php');
 require_once('../../conf/conf.php');
 require_once('conf/paging.php');
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); // date in the past
 header("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT"); // always modified
 header("Cache-Control: no-store, no-cache, must-revalidate"); // HTTP/1.1
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><?php title();?></title>
    <link href="css/default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="library/tiny_mce/tiny_mce.js"></script>
    <script type="text/javascript" src="conf/validator.js"></script>
    <script type="text/javascript" src="js/jquery/jquery.js"></script>
    <script type="text/javascript" src="js/jquery/jquery.dropdown.js"></script>
    <script type="text/javascript" src="cam/webcam.js"></script>
</head>
<!--This template was created by www.flash-templates-today.com
Flash-Templates-Today.com - Gives a possibility to obtain a ready free flash template, free css template and other kind of website template!-->
<body>
<!-- begin #container -->
<div id="container">
    <!-- begin #header -->
    <div id="header">
    	<div class="headerBackground">
            <h1>LABORATORIUM <br/>KLINIK INFERTILITAS PROGRAM PERMATA HATI FK-UGM/RS. DR. SARDJITO</h1>
            <h3>Jl.KESEHATAN NO.1, SEKIP YOGYAKARTA 55284, TELP. (0274) 518684, 587333, Psw. 373 Fax. (0274)553575</h3>
        </div>
        <div id="navcontainer">
            <?php tampilMenu();?>
         </div>
    </div>
    <!-- end #header -->
    <!-- begin #sidebar1 -->
    <div id="sidebar1">
        <div class="n_t">
        <div class="n_b">
        <div class="n_l">
        <div class="n_r">
        <div class="n_bl">
        <div class="n_br">
        <div class="n_tl">
        <div class="n_tr">
            <?php samping();?> 
        </div>
        </div>
        </div>
        </div>
        </div>
        </div>
        </div>
        </div>
        <br />
    </div>
    <!-- end #sidebar1 -->
    <!-- begin #mainContent -->
    <div id="mainContent">
        <?php actionPages();?>
    </div>
    <!-- end #mainContent -->
    <!-- This clearing element should immediately follow the #mainContent div in order to force the #container div to contain all child floats --><br class="clearfloat" />
    <!-- begin #footer -->
    <div id="footer">
        <p>
        <?php bawah();?>
        </p>
    </div>
    <!-- end #footer -->
</div>
<!-- end #container -->
</body>
</html>

