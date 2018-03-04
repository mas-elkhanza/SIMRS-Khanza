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

    <?php
    reportsqlinjection();      
       $tanggal1      = '2017-01-01'; 
        $tanggal2      = '2017-01-28'; 
		date_default_timezone_set("Asia/Jakarta");
		$datatime=date("Y-m-d H:i:s");

      
		
		$_poli="SELECT * FROM poliklinik";
		$poli=bukaquery($_poli);
		
		
		
		$ppkkode="SELECT * FROM setting";
		$ppk_kode=bukaquery($ppkkode);
		
		 while($ppk = mysqli_fetch_array($ppk_kode)) { 
			$k_ppk=$ppk['kode_ppk'];
			$n_ppk=$ppk['nama_ppk'];
		 }
        
		//echo $n_ppk;
		$_stts_daftar = Array('Ralan','Ranap');
		$_instalasi = Array('Instalasi Rawat Jalan','Instalasi Gawat Darurat','Instalasi Rawat Inap');
        $myvars="";
		
    foreach($_instalasi as $instansi) {
		
		if('Instalasi Gawat Darurat'==$instansi)
		{
			foreach($_stts_daftar as $status_pasien)
		{
			$_sql = "SELECT diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, Count(diagnosa_pasien.kd_penyakit) AS jumlah_pasien
				 FROM diagnosa_pasien ,penyakit ,reg_periksa WHERE diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit AND diagnosa_pasien.no_rawat = reg_periksa.no_rawat AND reg_periksa.kd_poli = 'IGDK' AND 
				 diagnosa_pasien.status = 'Ralan' AND reg_periksa.tgl_registrasi BETWEEN '$tanggal1' AND '$tanggal2'
				 GROUP BY diagnosa_pasien.kd_penyakit";
		$hasil=bukaquery($_sql);
        while($baris = mysqli_fetch_array($hasil)) {
				$kode_penyakit=$baris['kd_penyakit'];
				$nama_penyakit=$baris['nm_penyakit'];
				$jml_pasien=$baris['jumlah_pasien'];
		
		
		$post[] = array(
	
					'koders'=>$k_ppk,
					'tanggal'=>$tanggal2,
					'namainstalasi'=>$instansi,
					'kodediagnosa'=>$kode_penyakit,
					'namadiagnosa'=>$nama_penyakit,
					'jumlahpasien'=>$jml_pasien,
					'updatedate'=>$datatime,
		);
		}
		}}
		else if('Instalasi Rawat Jalan'==$instansi)
		{
			foreach($_stts_daftar as $status_pasien)
		{
			$_sql = "SELECT diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, Count(diagnosa_pasien.kd_penyakit) AS jumlah_pasien
				 FROM diagnosa_pasien ,penyakit ,reg_periksa WHERE diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit AND 
				 diagnosa_pasien.no_rawat = reg_periksa.no_rawat AND reg_periksa.kd_poli != 'IGDK' AND
				 diagnosa_pasien.status = 'Ralan' AND reg_periksa.tgl_registrasi BETWEEN '$tanggal1' AND '$tanggal2' 
				 GROUP BY diagnosa_pasien.kd_penyakit";
		$hasil=bukaquery($_sql);
		while($baris = mysqli_fetch_array($hasil)) {
				$kode_penyakit=$baris['kd_penyakit'];
				$nama_penyakit=$baris['nm_penyakit'];
				$jml_pasien=$baris['jumlah_pasien'];
		
		
			$post[] = array(
	
					'koders'=>$k_ppk,
					'tanggal'=>$tanggal2,
					'namainstalasi'=>$instansi,
					'kodediagnosa'=>$kode_penyakit,
					'namadiagnosa'=>$nama_penyakit,
					'jumlahpasien'=>$jml_pasien,
					'updatedate'=>$datatime,
				);
		}
		}}
		else
		{
			foreach($_stts_daftar as $status_pasien)
		{
			$_sql = "SELECT diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, Count(diagnosa_pasien.kd_penyakit) AS jumlah_pasien
				 FROM diagnosa_pasien ,penyakit ,reg_periksa WHERE diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit AND 
				 diagnosa_pasien.no_rawat = reg_periksa.no_rawat AND diagnosa_pasien.status = 'Ranap' AND 
				 reg_periksa.tgl_registrasi BETWEEN '$tanggal1' AND '$tanggal2' 
				 GROUP BY diagnosa_pasien.kd_penyakit ";
		$hasil=bukaquery($_sql);
		while($baris = mysqli_fetch_array($hasil)) {
				$kode_penyakit=$baris['kd_penyakit'];
				$nama_penyakit=$baris['nm_penyakit'];
				$jml_pasien=$baris['jumlah_pasien'];
		
			$post[] = array(
	
					'koders'=>$k_ppk,
					'tanggal'=>$tanggal2,
					'namainstalasi'=>$instansi,
					'kodediagnosa'=>$kode_penyakit,
					'namadiagnosa'=>$nama_penyakit,
					'jumlahpasien'=>$jml_pasien,
					'updatedate'=>$datatime,
				);
		}
		}}
				
       
		}
		echo json_encode($post);
		$myvars=json_encode($post);
		
		
		


?>
<input id = 'content' type='text' name='content' value='<?php echo $myvars; ?>'>
<div id="result"></div>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    $.ajax({
	        url: "http://202.147.199.11/wsdashboarddinkes/wskunjunganpasiendiagnosa.php",
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
	
    </body>
</html>
