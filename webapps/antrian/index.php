<?php
 require_once('conf/conf.php');
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
 date_default_timezone_set("Asia/Bangkok");
 $tanggal= mktime(date("m"),date("d"),date("Y"));
 $jam=date("H:i");
?>

<!DOCTYPE html>
<html lang="en"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
    <meta name="csrf-token" content="MNLaxGqlJ6dUvlDedNu7QMOXzGXMehHOX6nLA4mk">
        
    <!--<script src='https://code.responsivevoice.org/responsivevoice.js'></script>-->
        <script src='responsivevoice.js?key=3R3yY2gW'></script>
 <link href='https://fonts.googleapis.com/css?family=Orbitron' rel='stylesheet' type='text/css'>
    <!-- favicon --> 
 
    <!-- template bootstrap -->
    <link href="antrian_files/template.min.css" rel="stylesheet prefetch">
    <!-- roboto -->
    <link href="antrian_files/roboto.css" rel="stylesheet"> 
    <!-- font-awesome -->
    <link href="antrian_files/font-awesome.min.css" rel="stylesheet"> 
    <!-- custom style -->
    <link href="antrian_files/style.css" rel="stylesheet">
     <style>
body {
  overflow: hidden; /* Hide scrollbars */
}

     </style>
    <!-- Jquery  -->
    <script src="antrian_files/jquery.min.js"></script>
</head>
<body class="cm-no-transition cm-1-navbar loader-process" style="background:#fff;">
    
    <!-- Starts of Content -->
     
<div class=" ">

    <div>

 <div  style="height:65px; background:#3498db; width:100%;  padding-bottom: 10px;  padding-top: 5px;">
        
        <div  style="color:#ffffff">
          <h4   style="font-size:40px;  font-family:'Orbitron', sans-serif; "><center > KLINIK MILLA HUSADA</center></h4> 
        </div>
      </div>
        

    </div>



 <div   id="data">
        
    </div>





    <div>
    
      <div class="row">  
         <div style="padding:5px;">
         
         <!--
         <div class="col-sm-6" >
                    <div id="clock" class="well text-center" style="background-color:#000000;border-color:#3c8dbc;padding:25px 0;font-size:28px;margin-bottom:0">3 Oct, 2022 12:38:08</div>
                     
                    <div class="queue well text-center active " style="height:auto;padding:160px 0;margin:0;font-size:36px"> 
                        <h2 class="token" style="font-size:90px">VA302</h2>
                    </div>
                </div></div>
                
           -->     

     
         

 <div  style="height:55px; 
background: rgb(1,203,241);
background: linear-gradient(0deg, rgba(1,203,241,1) 0%, rgba(255,255,255,1) 70%);
 width:100%; position: absolute; bottom:0px;">
        
        <div  style="color:#f00; font-family:'Orbitron', sans-serif;" >
          <h4   style="font-size:30px; ">

<!--
<marquee direction="right">Tubuh Anda Adalah Dokter Yang terbaik</marquee>
--></h4> 
        </div>
      </div> 

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script> 
<script type="text/javascript"> 
    var auto_refresh = setInterval( function() { 
        $('#data').load('datakamar.php').fadeIn("slow"); }, 5000); 
</script>




</body></html>
