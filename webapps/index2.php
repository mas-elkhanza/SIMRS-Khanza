<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Website Resmi RSUD Mukomuko</title>
<link rel="stylesheet" type="text/css" href="style.css"/>
<link href="logo.gif" rel="shortcut icon">

</head>

<body onload="MM_preloadImages('images/menu1.jpg')">
	
	<div id="main">
	<div id="head"> <img src="images/header.jpg" /> </div>
    
    <div id="menutop">
    
     
     <!-- Menu Dropdown -->
   <div>
   
   <ul id="menu-bar">
 <li class="active"><a href="#">Home</a></li>
 <li><a href="#">Info Layanan</a>
  <ul>
   <li><a href="/webapps/jadwaldokter.php">Informasi Jadwal Dokter</a></li>
   <li><a href="/webapps/bed.php">Ketersediaan Kamar</a></li>            
   <li><a href="/webapps/bed2.php">Ketersediaan Kamar/Kelas</a></li>
   <li><a href="/webapps/registrasi.php">Registrasi Pasien </a></li>
   <li><a href="#">Products Sub Menu 4</a></li>
  </ul>
 </li>
 <li><a href="#">Services</a>
  <ul>
   <li><a href="#">Services Sub Menu 1</a></li>
   <li><a href="#">Services Sub Menu 2</a></li>
   <li><a href="#">Services Sub Menu 3</a></li>
   <li><a href="#">Services Sub Menu 4</a></li>
  </ul>
 </li>
 <li><a href="#">About</a></li>
 <li><a href="#">Contact Us</a></li>
</ul>
   </div>
   </div>
    <!-- Menu Dropdown -->
    <!-- Slider -->
    <div id="container">
		<img class ="slides" src="images/6.jpg"/>
    	<img class ="slides" src="images/2.jpg"/>
   		<img class ="slides" src="images/3.jpg"/>
        <button class="btn" onclick="plusindex(-1)" id="btn1">&#10094;</button>
        <button class="btn" onclick="plusindex(1)" id="btn2">&#10095;</button>
    
    </div>
    <!-- akhir slider -->
    
    <div  id="menutop"><li><marquee behavior="alternate">Selamat Datang di Website Layanan Kesehatan Rumah Sakit</marquee></li></div>
    
	 
     <div id="left" >
     <!-- Slider -->
    <div id="containeri">
		<img class ="slidesi" src="images/Idul Fitri 1438 H.jpg"/>
    	<img class ="slidesi" src="images/1.jpg"/>
   		<img class ="slidesi" src="images/4.jpg"/>
      
    </div>
    <!-- akhir slider -->
    
</div>

    <div id="middle">
    <div id="content_menu" align="justify"><img src="images/jadwal.jpg" width="250" height="100" >Setelah Jadwal Praktek Dokter dalam bentuk Web rilis, sekarang Tim SIMRS RSUD Mukomuko kembali merilis Informasi     Jadwal Dokter dalam versi    Android.  Yang merupakan salah satu informasi yang disajikan dalam aplikasi gadget. Melihat banyaknya           pengguna Handphone Android, dengan     alasan tersebut Tim SIMRS RSUD Mukomuko merilis dalam bentuk APK (Aplikasi yang berjalan di           Android) untuk menciptakan Layanan Publik di RSUD Mukomuko.</div>
    
    </div>
    
    
    <div id="right">
    <div id="bg_menu">Link Instansi</div>
    
    <div id="content_menu">BAPPEDA KAB. Mukomuko</div>
    <div id="bg_menu">Link eksternal</div>
    
    <div id="content_menu">Kementerian Kesehatan</div>
    </div> 
    
     <div id="footer">
	<div id="head"> <img src="images/footer.jpg" /> </div>
    
    </div>
    
	<div id="clear"> </div>
 <h5 align="center">Created by Team Sistem Informasi Rumah Sakit (SIMRS)- 2017</h3>
</div>


</body>
<script>
	var index=1;
	
	
	function plusindex(n) {
		index =index + n;
		showImage(index);
	}
	function plusindexy(j) {
		indexy =indexy + j;
		showImage1(indexy);
	}
	
	showImage(1);
	
	
	function showImage(n){
		var i;
		var x = document.getElementsByClassName("slides");
		if (n > x.length){index = 1 };
		if (n < 1 ){index = x.length };
		for (i=0;i<x.length;i++) 
			{
				x[i].style.display = "none";
			}
		x[index-1].style.display = "block";
	}
	autoSlide();
	function autoSlide(){
		var i;
		var x = document.getElementsByClassName("slides");
		for (i=0;i<x.length;i++) 
			{
				x[i].style.display = "none";
			}
		if (index > x.length){index = 1}
		x[index-1].style.display = "block";
		index++;
		setTimeout(autoSlide,5000)
	}
	var indexy=1;
	showImagey(1);
	function showImagey(j){
		var a;
		var b = document.getElementsByClassName("slidesi");
		if (j > b.length){indexy = 1 };
		if (j < 1 ){indexy = b.length };
		for (a=0;a<b.length;a++) 
			{
				b[a].style.display = "none";
			}
		b[indexy-1].style.display = "block";
	}
	autoSlidey();
	function autoSlidey(){
		var a;
		var b = document.getElementsByClassName("slidesi");
		for (a=0;a<b.length;a++) 
			{
				b[a].style.display = "none";
			}
		if (indexy > b.length){indexy = 1}
		b[indexy-1].style.display = "block";
		indexy++;
		setTimeout(autoSlidey,1000)
	}
	
</script>	
	
</html>
