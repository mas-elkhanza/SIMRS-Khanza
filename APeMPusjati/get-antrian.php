<?php
include ('config.php');
$aksi = $_REQUEST['aksi'];
if ($aksi == "tampilloket") {
  //ketahui jumlah total sehari...
	$sql = query("SELECT * FROM antrian_biasa WHERE round(DATE_FORMAT(postdate, '%d')) = '$tanggal' AND round(DATE_FORMAT(postdate, '%m')) = '$bulan' AND round(DATE_FORMAT(postdate, '%Y')) = '$tahun' ORDER BY round(noantrian) DESC");
	$result = fetch_assoc($sql);
	$noantrian = $result['noantrian'];
	//nomor antrian, total yang ada + 1
	if($noantrian > 0) {
		$next_antrian = $noantrian + 1;
	} else {
		$next_antrian = 1;
	}
	echo '<div id="nomernya" align="center">';
  echo '<h1 class="display-1">';
  echo 'A'.$next_antrian;
  echo '</h1>';
  echo '['.$tanggal.'-'.$bulan.'-'.$tahun.']';
  echo '</div>';
  echo '<br>';

	?>

	<script>
	$(document).ready(function(){
		$("#btnKRM").on('click', function(){
			$("#formloket").submit(function(){
				$.ajax({
					url: "get-antrian.php?aksi=simpanloket&noantrian=<?php echo $next_antrian;?>",
					type:"POST",
					data:$(this).serialize(),
					success:function(data){
						setTimeout('$("#loading").hide()',1000);
						window.location.href = "index.php";
						}
					});
				return false;
			});
		});
	})
	</script>
	<?php
	exit();
}

//jika simpan
if ($aksi == "simpanloket") {
	//ambil nilai
	$noantrian = $_GET['noantrian'];
	//cek
	$sql = query("SELECT * FROM antrian_biasa WHERE round(DATE_FORMAT(postdate, '%d')) = '$tanggal' AND round(DATE_FORMAT(postdate, '%m')) = '$bulan' AND round(DATE_FORMAT(postdate, '%Y')) = '$tahun' AND noantrian = '$noantrian'");
	if (empty(num_rows($sql))) {
		query("INSERT INTO antrian_biasa(kd, noantrian, postdate) VALUES (NULL, '$noantrian', '$date_time')");
	}
	?>
	<?php
	exit();
}

if ($aksi == "tampilcs") {
	  //ketahui jumlah total sehari...
		$sql = query("SELECT * FROM antrian_prio WHERE round(DATE_FORMAT(postdate, '%d')) = '$tanggal' AND round(DATE_FORMAT(postdate, '%m')) = '$bulan' AND round(DATE_FORMAT(postdate, '%Y')) = '$tahun' ORDER BY round(noantrian) DESC");
		$result = fetch_assoc($sql);
		$noantrian = $result['noantrian'];
		//nomor antrian, total yang ada + 1
		if($noantrian > 0) {
			$next_antrian = $noantrian + 1;
		} else {
			$next_antrian = 1;
		}
		echo '<div id="nomernya" align="center">';
	  echo '<h1 class="display-1">';
	  echo 'B'.$next_antrian;
	  echo '</h1>';
	  echo '['.$tanggal.'-'.$bulan.'-'.$tahun.']';
	  echo '</div>';
	  echo '<br>';

		?>

		<script>
		$(document).ready(function(){
			$("#btnKRMCS").on('click', function(){
				$("#formcs").submit(function(){
					$.ajax({
						url: "get-antrian.php?aksi=simpancs&noantrian=<?php echo $next_antrian;?>",
						type:"POST",
						data:$(this).serialize(),
						success:function(data){
							setTimeout('$("#loading").hide()',1000);
							window.location.href = "index.php";
							}
						});
					return false;
				});
			});
		})
		</script>
		<?php
		exit();
}

//jika simpan
if ($aksi == "simpancs") {
	//ambil nilai
	$noantrian = $_GET['noantrian'];
	//cek
	$sql = query("SELECT * FROM antrian_prio WHERE round(DATE_FORMAT(postdate, '%d')) = '$tanggal' AND round(DATE_FORMAT(postdate, '%m')) = '$bulan' AND round(DATE_FORMAT(postdate, '%Y')) = '$tahun' AND noantrian = '$noantrian'");
	if (empty(num_rows($sql))) {
		query("INSERT INTO antrian_prio(kd, noantrian, postdate) VALUES (NULL, '$noantrian', '$date_time')");
	}
	?>
	<?php
	exit();
}



if ($aksi == "tampilpr") {
	  //ketahui jumlah total sehari...
		$sql = query("SELECT * FROM antrian_pr WHERE round(DATE_FORMAT(postdate, '%d')) = '$tanggal' AND round(DATE_FORMAT(postdate, '%m')) = '$bulan' AND round(DATE_FORMAT(postdate, '%Y')) = '$tahun' ORDER BY round(noantrian) DESC");
		$result = fetch_assoc($sql);
		$noantrian = $result['noantrian'];
		//nomor antrian, total yang ada + 1
		if($noantrian > 0) {
			$next_antrian = $noantrian + 1;
		} else {
			$next_antrian = 1;
		}
		echo '<div id="nomernya" align="center">';
	  echo '<h1 class="display-1">';
	  echo 'P'.$next_antrian;
	  echo '</h1>';
	  echo '['.$tanggal.'-'.$bulan.'-'.$tahun.']';
	  echo '</div>';
	  echo '<br>';

		?>

		<script>
		$(document).ready(function(){
			$("#btnKRMpr").on('click', function(){
				$("#formpr").submit(function(){
					$.ajax({
						url: "get-antrian.php?aksi=simpanpr&noantrian=<?php echo $next_antrian;?>",
						type:"POST",
						data:$(this).serialize(),
						success:function(data){
							setTimeout('$("#loading").hide()',1000);
							window.location.href = "index.php";
							}
						});
					return false;
				});
			});
		})
		</script>
		<?php
		exit();
}

//jika simpan
if ($aksi == "simpanpr") {
	//ambil nilai
	$noantrian = $_GET['noantrian'];
	//cek
	$sql = query("SELECT * FROM antrian_pr WHERE round(DATE_FORMAT(postdate, '%d')) = '$tanggal' AND round(DATE_FORMAT(postdate, '%m')) = '$bulan' AND round(DATE_FORMAT(postdate, '%Y')) = '$tahun' AND noantrian = '$noantrian'");
	if (empty(num_rows($sql))) {
		query("INSERT INTO antrian_pr(kd, noantrian, postdate) VALUES (NULL, '$noantrian', '$date_time')");
	}
	?>
	<?php
	exit();
}


?>
