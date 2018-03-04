<script type="text/javascript">
    function validasi_input(form) {
        if (form.no_ktp.value == "") {
            alert("No KTP tidak Boleh Kosong!");
            form.no_ktp.focus();
            return (false);
        }
		if (form.no_rkm_medis.value == "") {
            alert("N0 Rekam Medis Tidak Boleh Kosong!");
            form.no_rkm_medis.focus();
            return (false);
        }
		if (form.tanggal.value == "") {
            alert("Tanggal Kunjungan Tidak Boleh Kosong!");
            form.tanggal.focus();
            return (false);
        }
        return (true);
    }
	function validasi_lama(form) {
		if (form.kd_poli.value == "") {
            alert("Poliklinik Belum Di Pilih!");
            form.kd_poli.focus();
            return (false);
        }
		if (form.kd_dokter.value == "") {
            alert("Dokter Tidak Boleh Kosong!");
            form.kd_penjab.focus();
            return (false);
        }
		if (form.kd_penjab.value == "") {
            alert("ASKES Tidak Boleh Kosong!");
            form.kd_penjab.focus();
            return (false);
        }
		if (form.no_tlp.value == "") {
            alert("No TLP Tidak Boleh Kosong!");
            form.no_tlp.focus();
            return (false);
        }
        return (true);
    }
	
</script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/datepicker.css" rel="stylesheet">
<link href="css/bootstrap-table.css" rel="stylesheet">
<?php
 include '../conf/conf.php';
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
 $tanggal= mktime(date("m"),date("d"),date("Y"));
 date_default_timezone_set('Asia/Jakarta');
 $tentukan_hari=date('D',strtotime($tanggal));
		 $day = array(
			'Sun' => 'MINGGU',
			'Mon' => 'SENIN',
			'Tue' => 'SELASA',
			'Wed' => 'RABU',
			'Thu' => 'KAMIS',
			'Fri' => 'JUMAT',
			'Sat' => 'SABTU'
			);
			$hari=$day[$tentukan_hari];
?>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.10/css/jquery.dataTables.css">

<!-- Start: Google analytics code-->

<script language="JavaScript" type="text/JavaScript">

 function validAngka(a)
{
	if(!/^[0-9.]+$/.test(a.value))
	{
	a.value = a.value.substring(0,a.value.length-1000);
	}
}
 function showDokter()
 {
 <?php

 // membaca jadwal poliklinik
 $query = "select jadwal.kd_poli from jadwal,poliklinik,dokter where jadwal.kd_poli=poliklinik.kd_poli and
		   jadwal.kd_dokter=dokter.kd_dokter and hari_kerja LIKE '%$hari%'";
 $hasil = bukaquery($query);

 // membuat if untuk masing-masing pilihan poliklinik beserta isi option untuk combobox dokter
 while ($data = mysqli_fetch_array($hasil))
 {
   $idPoli = $data['kd_poli'];

   // membuat IF untuk masing-masing poliklinik terjadwal
   echo "if (document.pilihan.kd_poli.value == \"".$idPoli."\")";
   echo "{";

   // membuat option dokter untuk masing-masing poliklinik yg terjadwal
   $query2 = "select jadwal.kd_dokter, dokter.nm_dokter from jadwal,poliklinik,dokter where jadwal.kd_poli=poliklinik.kd_poli and
		   jadwal.kd_dokter=dokter.kd_dokter and jadwal.kd_poli='$idPoli' and jadwal.hari_kerja LIKE '%$hari%'";
   $hasil2 = mysql_query($query2);
   $content = "document.getElementById('dokter').innerHTML = \"<select name='kd_dokter' class='form-control'>";
   while ($data2 = mysqli_fetch_array($hasil2))
   {
       $content .= "<option value='".$data2['kd_dokter']."'>".$data2['nm_dokter']."</option>";
   }
   $content .= "</select>\";";
   echo $content;
   echo "}\n";
 }

 ?>
 }
</script>
<html>
    <body>
	<?php 
	date_default_timezone_set("Asia/Jakarta");
	$datatime=date("Y-m-d H:i:s");
	error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
	switch($_GET[act]){
	default:
	?>
	<table align='center' border='0' width='1280' height='800'>
		<tr>
			<td align='center'><a href='' data-toggle="modal" data-target="#add-pasien-baru"><img src='../pendaftaran/img/baru.png' width='400' height='350'></a></td>
			<td align='center'><a href='?act=cek-pasien-lama' ><img src='../pendaftaran/img/lama.png' width='400' height='350'></a></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
		</tr>
	</table>
	<?php
	break;
	
	case"cek-pasien-lama":
	?>
	<form action='?act=regist-pasien-lama' name='pilihan' align='center' onsubmit='return validasi_input(this)' name='input' method='post' enctype='multipart/form-data' >
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="add-modal-label">Cek Data pasien</h4>
			      </div>
			      <div class="modal-body">
			        <table align='center'>
						<tr>
							<td>No KTP</td><td>:</td><td><input type="text" class="form-control" name="no_ktp" placeholder="No KTP" onkeyup="validAngka(this)" ></td>
						</tr>
						<tr>
							<td>No RM</td><td>:</td><td><input type="text" class="form-control" name="no_rkm_medis" placeholder="No Rekam Medis" onkeyup="validAngka(this)" style="width:400" ></td>
						</tr>
						<tr>
							<td>Tanggal</td><td>:</td><td><input type='text' class="form-control" id='calender' placeholder="Kunjungan" name='tanggal' ></td>
						</tr>
						<tr>
							<td align='right'><input type='submit' value='CEK'></td><td align='left'></td><td><input type='button' value='Batal' onclick='self.history.back()'></td>
						</tr>
					</table>
				  </div>
				  </form>
		<?php
		break;
		
case"regist-pasien-lama":
		$login=getone("SELECT * FROM pasien WHERE no_rkm_medis='$_POST[no_rkm_medis]' and no_ktp='$_POST[no_ktp]'");
		$no_rkm_medis=$_POST['no_rkm_medis'];
		$tanggal=$_POST['tanggal'];
			if($login==''){
				echo "<script>alert('Maaf NO RM dan NO KTP Anda Tidak Cocok!!'); window.location = 'javascript:history.go(-1)'</script>";
			}
			else{
				echo "<script>alert('Data Ditemukan.. ')</script>";
				$rm=mysql_query("select * from pasien where no_rkm_medis='$no_rkm_medis'");
				$b=mysqli_fetch_array($rm);
				$nm_pasien=$b['nm_pasien'];
		?>
		<form action='tes.php?act=registrasi' name='pilihan' align='center' onsubmit='return validasi_lama(this)' method='post' enctype='multipart/form-data' >
				<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="add-modal-label">REGISTRASI PASIEN LAMA</h4>
			      </div>
			      <div class="modal-body">
			        <table align='center'>
						<tr>
							<td>Nama Pasien</td><td>:</td><td><input type="text" class="form-control" name="nm_pasien" placeholder="No Rekam Medis" value="<?php echo $nm_pasien; ?>" style="width:400" readonly>
															  <input type="hidden" class="form-control" name="no_rkm_medis" placeholder="No Rekam Medis" value="<?php echo $no_rkm_medis; ?>" style="width:400" hidden></td>
						</tr>
						<tr>
							<td>Kunjungan</td><td>:</td><td><input type="text" class="form-control" name="tanggal" value="<?php echo $tanggal; ?>" style="width:400" readonly></td>
						</tr>
						<tr>
							<td>Hari</td><td>:</td><td><input type="text" class="form-control" name="hari" value="<?php echo $hari; ?>" style="width:400" readonly ></td>
						</tr>
						<tr>
							<td>Pilih Poli</td><td>:</td><td><select class='form-control' name='kd_poli' onchange='showDokter()'>
															 <option value='' selected >POLIKLINIK*</option>";
															 <?php $poliklinik="select jadwal.kd_poli,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai from jadwal,poliklinik,dokter where jadwal.kd_poli=poliklinik.kd_poli and
																				jadwal.kd_dokter=dokter.kd_dokter and hari_kerja LIKE '%$hari%' GROUP BY poliklinik.kd_poli";
															 $hasil=bukaquery($poliklinik);
															 while($baris=mysqli_fetch_array($hasil)){
															 echo "<option value='".$baris['kd_poli']."'>".$baris['nm_poli']." [".$baris['jam_mulai']."-".$baris['jam_selesai']."]</option>";
															 }?>
															 </select></td>
						</tr>
						<tr>
							<td>Pilih Dokter</td><td>:</td><td><select class="form-control" name="kd_dokter" id="dokter"></select></td>
						</tr>
						<tr>
							<td>ASKES</td><td>:</td><td><select class='form-control' name='kd_penjab'>
														<option value='' selected >ASKES*</option>";
														<?php $askes="select * from penjab where png_jawab LIKE '%umum%' or png_jawab LIKE '%bpjs%' and kd_pj!='2'  ";
														$hasil=bukaquery($askes);
														while($baris=mysqli_fetch_array($hasil)){
														echo "<option value='$baris[kd_pj]'>$baris[png_jawab]</option>";
														}?>
														</select></td>
						</tr>
						<tr>
							<td>No TLP/HP.</td><td>:</td><td><input type="text" class="form-control" name="no_tlp" maxlength="12" placeholder="021/08X" onkeyup="validAngka(this)" ></td>
						</tr>
						<tr>
							<td align='left'><input type='submit' value='Kirim'></td><td colspan='2' align='left'></td>
						</tr>
					</table>
				   </div>
				  </form>
		<?php
			}
		break;		
	}
	/*
	<!-- pasien baru -->
	<div class="modal fade" id="add-pasien-baru" tabindex="-1" role="dialog" aria-labelledby="add-modal-label">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		    	<form action='#' onsubmit='return validasi_input(this)' method='post' enctype='multipart/form-data' >
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="add-modal-label">Add Pasien Baru</h4>
			      </div>
			      <div class="modal-body">
			        <table>
						<tr>
							<td>Nama</td><td>:</td><td><input type="text" class="form-control" name="nama" placeholder="Nama" style="text-transform:uppercase;width:400" ></td>
						</tr>
						<tr>
							<td>No KTP</td><td>:</td><td><input type="text" class="form-control" name="no_ktp" placeholder="No KTP" onkeyup="validAngka(this)" ></td>
						</tr>
						<tr>
							<td>Jenis Kelamin</td><td>:</td><td><input  name='jk' type='radio' value='L' id="add-jk"/>PRIA <input name='jk'  type='radio' value='P' id="add-jk"/>WANITA</td>
						</tr>
						<tr>
							<td>Tempat Lahir</td><td>:</td><td><input type="text" class="form-control" name="temp_lahir" placeholder="Tempat Lahir" style="text-transform:uppercase" ></td>
						</tr>
						<tr>
							<td>Tanggal Lahir</td><td>:</td><td><input type="text" class="form-control" name="tgl_lahir" id='tgl' ></td>
						</tr>
						<tr>
							<td>Alamat</td><td>:</td><td><textarea class="form-control" name="alamat" style="text-transform:uppercase;height:100" ></textarea></td>
						</tr>
						<tr>
							<td>Gol Darah</td><td>:</td><td><select class="form-control"  name='gol_darah'>
															<option value='-' selected='selected'>GOLONGAN DARAH*</option>
															<option value='A'>A</option>
															<option value='B'>B</option>
															<option value='AB'>AB</option>
															<option value='O'>O</option>
															<option value='-'>-</option>
															</select></td>
						</tr>
						<tr>
							<td>Pendidikan</td><td>:</td><td><select class="form-control"  name='pnd'>
															<option value='-' selected='selected'>PENDIDIKAN*</option>
															<option value='TK'>TK</option>
															<option value='SD'>SD</option>
															<option value='SMP'>SMP</option>
															<option value='SMA'>SMA</option>
															<option value='D3'>D3</option>
															<option value='S1'>S1</option>
															<option value='S2'>S2</option>
															<option value='S3'>S3</option>
															<option value='-'>-</option>
															</select></td>
						</tr>
						<tr>
							<td>ASKES</td><td>:</td><td><select class='form-control' name='kd_penjab'>
														<option value='' selected >ASKES*</option>";
														<?php $askes="select * from penjab where png_jawab LIKE '%umum%' or png_jawab LIKE '%bpjs%' and kd_pj!='2'  ";
														$hasil=bukaquery($askes);
														while($baris=mysqli_fetch_array($hasil)){
														echo "<option value='$baris[kd_pj]'>$baris[png_jawab]</option>";
														}?>
														</select></td>
						</tr>
						<tr>
							<td>No TLP</td><td>:</td><td><input type="text" class="form-control" name="no_tlp" onkeyup="validAngka(this)" ></td>
						</tr>
						<tr>
							<td align='right'><input type='submit' value='Daftar'></td><td colspan='2' align='left'><input type='button' value='Batal' data-dismiss="modal"></td>
						</tr>
					</table>
				  </div>
				  </form>
				  </div>
				  </div>
				  </div>
				  
				  
		<!-- pasien lama -->
		<div class="modal fade" id="add-pasien-lama" tabindex="-1" role="dialog" aria-labelledby="add-modal-label">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		    	<form action='tes.php?act=registrasi' name='pilihan' onsubmit='return validasi_input(this)' method='post' enctype='multipart/form-data' >
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="add-modal-label">Add Pasien Baru</h4>
			      </div>
			      <div class="modal-body">
			        <table>
						<tr>
							<td>No KTP</td><td>:</td><td><input type="text" class="form-control" name="no_ktp" placeholder="No KTP" onkeyup="validAngka(this)" ></td>
						</tr>
						<tr>
							<td>No RM</td><td>:</td><td><input type="text" class="form-control" name="no_rkm_medis" placeholder="No Rekam Medis" onkeyup="validAngka(this)" style="width:400" ></td>
						</tr>
						<tr>
							<td>Pilih Poli</td><td>:</td><td><select class='form-control' name='kd_poli' onchange='showDokter()'>
															 <option value='' selected >POLIKLINIK*</option>";
															 <?php $poliklinik="select jadwal.kd_poli,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai from jadwal,poliklinik,dokter where jadwal.kd_poli=poliklinik.kd_poli and
																				jadwal.kd_dokter=dokter.kd_dokter and hari_kerja LIKE '%$hari%'";
															 $hasil=bukaquery($poliklinik);
															 while($baris=mysqli_fetch_array($hasil)){
															 echo "<option value='".$baris['kd_poli']."'>".$baris['nm_poli']." [".$baris['jam_mulai']."-".$baris['jam_selesai']."]</option>";
															 }?>
															 </select></td>
						</tr>
						<tr>
							<td>Pilih Dokter</td><td>:</td><td><select class='form-control' name="kd_dokter" id="dokter"></select></td>
						</tr>
						<tr>
							<td>ASKES</td><td>:</td><td><select class='form-control' name='kd_penjab'>
														<option value='' selected >ASKES*</option>";
														<?php $askes="select * from penjab where png_jawab LIKE '%umum%' or png_jawab LIKE '%bpjs%' and kd_pj!='2'  ";
														$hasil=bukaquery($askes);
														while($baris=mysqli_fetch_array($hasil)){
														echo "<option value='$baris[kd_pj]'>$baris[png_jawab]</option>";
														}?>
														</select></td>
						</tr>
						<tr>
							<td>No TLP</td><td>:</td><td><input type="text" class="form-control" name="no_tlp" placeholder="Isi Angka" onkeyup="validAngka(this)" ></td>
						</tr>
						<tr>
							<td align='right'><input type='submit' value='Daftar'></td><td colspan='2' align='left'><input type='button' value='Batal' data-dismiss="modal"></td>
						</tr>
					</table>
				  </div>
				  </form>
				  </div>
				  </div>
				  </div>
		*/
		?>
		<!-- script references -->
		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/scripts.js"></script>
		<script src="js/jquery.maskedinput.js"></script>
		<script src="js/bootstrap-datepicker.js"></script>
		<script src="js/bootstrap-table.js"></script>
		<script>
						    $(function () {
						        $('#hover, #striped, #condensed').click(function () {
						            var classes = 'table';
						
						            if ($('#hover').prop('checked')) {
						                classes += ' table-hover';
						            }
						            if ($('#condensed').prop('checked')) {
						                classes += ' table-condensed';
						            }
						            $('#table-style').bootstrapTable('destroy')
						                .bootstrapTable({
						                    classes: classes,
						                    striped: $('#striped').prop('checked')
						                });
						        });
						    });
						
						    function rowStyle(row, index) {
						        var classes = ['active', 'success', 'info', 'warning', 'danger'];
						
						        if (index % 2 === 0 && index / 2 < classes.length) {
						            return {
						                classes: classes[index / 2]
						            };
						        }
						        return {};
						    }
						</script>
			<script>
		jQuery(function($){
            $("#tgl1").mask("99/99/9999",{placeholder:"dd/mm/yyyy"});
			$("#tgl2").mask("99/99/9999",{placeholder:"dd/mm/yyyy"});
            $("#npwp").mask("99-999-999-9-999-999");
			$("#jam").mask("99:99");
			$("#jam1").mask("99:99");
        });
        $('#calender').datepicker({
        autoclose:true, orientation:'top right',
                'default': 'now'
        });
		$('#calender1').datepicker({
        autoclose:true, orientation:'top right',
                'default': 'now'
         });
        !function ($) {
        $(document).on("click", "ul.nav li.parent > a > span.icon", function(){
        $(this).find('em:first').toggleClass("glyphicon-minus");
        });
        $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
        }(window.jQuery);
        $(window).on('resize', function () {
        if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
        })
                $(window).on('resize', function () {
        if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
        })
        </script>
	
	</body>
</html>