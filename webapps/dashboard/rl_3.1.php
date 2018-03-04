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
		<h4>Laporan RL 3.1</h4></div>
			<div class="panel-body">
    <?php
	switch($_GET[act]){
	default:
    echo"<form action='?module=3.1&act=tampil' method='post'>
	<table>
	<tr>
	<td>Periode</td><td>:</td><td><input type='text' id='calender1' name='tanggal1' >-S/D-<input type='text' id='calender' name='tanggal2' ></td>
	<td align='right'><input type='submit' value='tampilkan' ></td>
	</tr>
	</table>
	</form>";
	break;
	
	case "tampil":
    reportsqlinjection();       
		date_default_timezone_set("Asia/Jakarta");
		$datatime=date("Y-m-d H:i:s");
		$date1      = $_POST['tanggal1']; 
        $date2      = $_POST['tanggal2'];
		$tanggal1=date("Y-m-d",strtotime($date1));
		$tanggal2=date("Y-m-d",strtotime($date2));
		$datatime=date("Y-m-d H:i:s");
		echo"
		<input type='button' value='Back' onclick=self.history.go(-1)>
		<h3>Periode: $tanggal1 - $tanggal2</h3>
		<table class='table table-bordered table-hover table-striped' data-toggle='table'>
		<thead>
		<tr>
		<td>Kode</td>
		<td>Tanggal</td>
		<td>Nama Instalasi</td>
		<td>Nama Ruangan</td>
		<td>Jumlah Pasien</td>";
        $_sql = "SELECT setting.kode_ppk, poliklinik.nm_poli, Count(poliklinik.kd_poli) AS jumlah_pasien
				 FROM reg_periksa , poliklinik , setting
				 WHERE reg_periksa.kd_poli = poliklinik.kd_poli AND reg_periksa.kd_poli != 'IGDK' AND reg_periksa.tgl_registrasi BETWEEN '$tanggal1' AND '$tanggal2'
				 GROUP BY poliklinik.nm_poli";            
        $hasil=bukaquery($_sql);
                 while($baris = mysqli_fetch_array($hasil)) { 
				//simpan data untuk kirim ke dinkes
				$myvars="";
				$post[] = array(
	
					'koders'=>$baris['kode_ppk'],
					'tanggal'=>$tanggal2,
					'namainstalasi'=>'Instalasi Rawat Jalan',
					'namaruangan'=>$baris['nm_poli'],
					'kelas'=>'',
					'jumlahpasien'=>$baris['jumlah_pasien'],
					'updatedate'=>$datatime,
					);
		echo"
		</tr>
		</thead>
		<td>$baris[kode_ppk]</td>
		<td>$tanggal2</td>
		<td>Instalasi Rawat Jalan</td>
		<td>$baris[nm_poli]</td>
		<td>$baris[jumlah_pasien]</td>
		</tr>";
                } 
		echo"
		<table>";   
		//keep data dengan JSON
		$myvars=json_encode($post);
		echo json_encode($post);


?>

<input id = 'content' type='text' name='content' value='<?php echo $myvars; ?>'>
<div id="result"></div>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    $.ajax({
	        url: "http://202.147.199.11/wsdashboarddinkes/wskunjunganpasienruangan.php",
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
