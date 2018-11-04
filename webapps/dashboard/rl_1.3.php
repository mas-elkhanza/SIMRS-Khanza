<?php
 include '../conf/conf.php';
   /* header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanRekapKunjuangnJenisBayar.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
    print "$header\n$data";*/
?>
<html>
    <body>
	<div class="panel-heading">
		<h4>Laporan RL 1.3</h4></div>
			<div class="panel-body">
    <?php
switch($_GET[act]){
	default:
  reportsqlinjection();      		
		date_default_timezone_set("Asia/Jakarta");
		$datatime=date("Y-m-d H:i:s");
		$date1      = $_POST['tanggal1']; 
        $date2      = $_POST['tanggal2'];
		$date=$_POST['tanggal'];
		$tanggal1=date("y-m-d",strtotime($date1));
		$tanggal2=date("y-m-d",strtotime($date2));
		//tampilkan dalam tabel
		echo"
		<table class='table table-bordered table-hover table-striped'' data-toggle='table'>
		<tr>
		<thead>
		<td>Kode</td>
		<td>Nama Ruang Rawat</td>
		<td>Jenis Ruang Rawat</td>
		<td>Jumlah Tempat Tidur</td>";
        $_sql = "SELECT setting.kode_ppk, bangsal.nm_bangsal, aplicare_ketersediaan_kamar.kode_kelas_aplicare, Count(kamar.kd_kamar) AS jumlah
				 FROM kamar , bangsal , setting , aplicare_ketersediaan_kamar
				 WHERE kamar.kd_bangsal = bangsal.kd_bangsal AND kamar.kd_bangsal = aplicare_ketersediaan_kamar.kd_bangsal 
				 GROUP BY kamar.kd_bangsal ORDER BY bangsal.nm_bangsal ASC";            
        $hasil=bukaquery($_sql);
                 while($baris = mysqli_fetch_array($hasil)) { 
				 $ppk=$baris['kode_ppk'];
				 $ruang=$baris['nm_bangsal'];
				 $jenis=$baris['kode_kelas_aplicare'];
				 $jml=$baris['jumlah'];
				//simpan data untuk kirim ke dinkes
				$myvars="";
				$post[] = array(
	
					'koders'=>$baris['kode_ppk'],
					'namaruangrawat'=>$baris['nm_bangsal'],
					'jenisruangrawat'=>$baris['kode_kelas_aplicare'],
					'jumlahtt'=>$baris['jumlah'],
					);
		echo"
		</tr>
		</thead>
		<td>$ppk</td>
		<td>$ruang</td>
		<td>$jenis</td>
		<td>$jml</td>
		</tr>";
                }  
		echo"
		<table>";
		//keep data dengan JSON
		$myvars=json_encode($post);
?>

<input id = 'content' type='text' name='content' value='<?php echo $myvars; ?>'>
<div id="result"></div>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    $.ajax({
	        url: "http://202.147.199.11/wsdashboarddinkes/wsrl13.php",
	        type: "POST",
	        data: {
				data: $("#content").val()
			},
	        dataType: "JSON",
	        success: function (jsonStr) {
	            $("#result").text(JSON.stringify(jsonStr));
	        }
	    });
	});
</script>

	
	
<!-- <button id="submit" name="submit" type="submit">Send</buttton> -->
<?php break ; 
}
?>




  </body>
</html>
