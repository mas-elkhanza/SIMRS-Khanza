<?php 
error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
	if($_GET['module']=="1.1"){
		include "rl_1.1.php";
		} 
	elseif($_GET['module']=="1.2"){
		include "rl_1.2.php";
		} 
	elseif($_GET['module']=="1.3"){
		include "rl_1.3.php";
		} 
	elseif($_GET['module']=="1.3"){
		include "agent/rl_1.3.php";
		} 
	elseif($_GET['module']=="2"){
		include "rl_2.php";
		} 
	elseif($_GET['module']=="3.1"){
		include "rl_3.1.php";
		} 
	elseif($_GET['module']=="3.2"){
		include "rl_3.2.php";
		} 
	elseif($_GET['module']=="3.3"){
		include "rl_3.3.php";
		} 
	elseif($_GET['module']=="3.4"){
		include "rl_3.4.php";
		}
	elseif($_GET['module']=="3.5"){
		include "rl_3.5.php";
		} 
	elseif($_GET['module']=="3.6"){
		include "rl_3.6.php";
		} 
	elseif($_GET['module']=="3.7"){
		include "rl_3.7.php";
		} 
	elseif($_GET['module']=="3.8"){
		include "rl_3.8.php";
		}
	elseif($_GET['module']=="3.9"){
		include "rl_3.9.php";
		}
	elseif($_GET['module']=="3.10-"){
		include "rl_3.10.php";
		}
	elseif($_GET['module']=="3.11"){
		include "rl_3.11.php";
		} 
	elseif($_GET['module']=="3.12"){
		include "rl_3.12.php";
		} 
	elseif($_GET['module']=="3.13"){
		include "rl_3.13.php";
		}
	elseif($_GET['module']=="3.14"){
		include "rl_3.14.php";
		} 
	elseif($_GET['module']=="3.15"){
		include "rl_3.15.php";
		}
	elseif($_GET['module']=="4a"){
		include "rl_4a.php";
		} 
	elseif($_GET['module']=="4b"){
		include "4b.php";
		}
	elseif($_GET['module']=="5.1"){
		include "rl_5.1.php";
		} 
	elseif($_GET['module']=="5.2"){
		include "rl_5.2.php";
		} 
	elseif($_GET['module']=="5.3"){
		include "rl_5.3.php";
		} 
	else {
		?>
		<div class="panel-heading">
		<h4>Selamat Datang di menu Laporan</h4></div>
			<div class="panel-body">
			<p>data ni akan di kirimkan ke dashboard dinkes setiap periodenyaaa
		<?
	}
?>

		
