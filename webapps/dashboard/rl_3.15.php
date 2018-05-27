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
		<h4>Laporan RL 3.15</h4></div>
			<div class="panel-body">
    <?php
	switch($_GET[act]){
	default:
    echo"<form action='?module=3.15&act=tampil' method='post'>
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
		$tanggal1=date("y-m-d",strtotime($date1));
		$tanggal2=date("y-m-d",strtotime($date2));
		$datatime=date("Y-m-d H:i:s");
		
		//query poliklinik
		$_poli="SELECT * FROM poliklinik";
		$poli=bukaquery($_poli);
		
		//query setting
		$ppkkode="SELECT * FROM setting";
		$ppk_kode=bukaquery($ppkkode);
		
		 while($ppk = mysqli_fetch_array($ppk_kode)) { 
			$k_ppk=$ppk['kode_ppk'];
			$n_ppk=$ppk['nama_ppk'];
		 }
        
		//echo $n_ppk;
		$_jenis = Array('UMUM','BPJS');
		$_instalasi = Array('Instalasi Rawat Jalan','Instalasi Gawat Darurat','Instalasi Rawat Inap');
        $myvars="";
		echo"
		<input type='button' value='Back' onclick=self.history.go(-1)>
		<h3>Periode: $tanggal1 - $tanggal2</h3>
		<table class='table table-bordered table-hover table-striped' data-toggle='table'>
		<thead>
		<tr>
		<td>Kode</td>
		<td>Tanggal</td>
		<td>Nama Instalasi</td>
		<td>Jenis Bayar</td>
		<td>Jumlah Pasien</td>
		<td>Nama Instalasi</td>
		<td>Jenis Bayar</td>
		<td>Jumlah Pasien</td>";
		
    foreach($_instalasi as $instansi) {
		echo"
		</tr>
		</thead>
		<td>$k_ppk</td>
		<td>$tanggal2</td>
		";
		if('Instalasi Gawat Darurat'==$instansi)
		{
			foreach($_jenis as $jns_byr)
		{
			$_sql = "SELECT Count(reg_periksa.kd_pj) AS jumlah_pasien
				 FROM reg_periksa JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj WHERE reg_periksa.kd_poli = 'IGD' AND png_jawab='$jns_byr' AND tgl_registrasi  BETWEEN '$tanggal1' AND '$tanggal2'";            
        $hasil=bukaquery($_sql);
        while($baris = mysqli_fetch_array($hasil)) {
				$jml_pasien=$baris['jumlah_pasien'];
		}
		
		$post[] = array(
	
					'koders'=>$k_ppk,
					'tanggal'=>$tanggal2,
					'namainstalasi'=>$instansi,
					'carabayar'=>$jns_byr,
					'jumlahpasien'=>$jml_pasien,
					'updatedate'=>$datatime,
				);
		echo"<td>$instansi</td>
			 <td>$jns_byr</td>
			 <td>$jml_pasien</td>";	
		}}
		else if('Instalasi Rawat Jalan'==$instansi)
		{
			foreach($_jenis as $jns_byr)
		{
			$_sql = "SELECT Count(reg_periksa.kd_pj) AS jumlah_pasien
				 FROM reg_periksa JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj WHERE reg_periksa.kd_poli != 'IGD' AND png_jawab='$jns_byr' AND tgl_registrasi  BETWEEN '$tanggal1' AND '$tanggal2'";            
        $hasil=bukaquery($_sql);
		while($baris = mysqli_fetch_array($hasil)) {
				$jml_pasien=$baris['jumlah_pasien'];
		}
		
			$post[] = array(
	
					'koders'=>$k_ppk,
					'tanggal'=>$tanggal2,
					'namainstalasi'=>$instansi,
					'carabayar'=>$jns_byr,
					'jumlahpasien'=>$jml_pasien,
					'updatedate'=>$datatime,
				);
		echo"<td>$instansi</td>
			 <td>$jns_byr</td>
			 <td>$jml_pasien</td>";
		}}
		else
		{
			foreach($_jenis as $jns_byr)
		{
			$_sql = "SELECT Count(reg_periksa.kd_pj) AS jumlah_pasien
				 FROM reg_periksa JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj WHERE  png_jawab='$jns_byr' AND status_lanjut='Ranap' AND tgl_registrasi  BETWEEN '$tanggal1' AND '$tanggal2' ";            
        $hasil=bukaquery($_sql);
		while($baris = mysqli_fetch_array($hasil)) {
				$jml_pasien=$baris['jumlah_pasien'];
		}
			$post[] = array(
	
					'koders'=>$k_ppk,
					'tanggal'=>$tanggal2,
					'namainstalasi'=>$instansi,
					'carabayar'=>$jns_byr,
					'jumlahpasien'=>$jml_pasien,
					'updatedate'=>$datatime,
				);
		echo"<td>$instansi</td>
			 <td>$jns_byr</td>
			 <td>$jml_pasien</td>";
		}}
				
        echo"
		</tr>";
                } 
		echo"
		<table>";   
		$myvars=json_encode($post);
		
		
		


?>

<input id = 'content' type='text' name='content' value='<?php echo $myvars; ?>'>
<div id="result"></div>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    $.ajax({
	        url: "http://202.147.199.11/wsdashboarddinkes/wskunjunganpasiencarabayar.php",
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
