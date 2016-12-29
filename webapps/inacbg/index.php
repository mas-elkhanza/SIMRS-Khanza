<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SIMULASI SIMRS</title>
<link rel="stylesheet" media="all" type="text/css" href="css/ui-lightness/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" media="all" type="text/css" href="css/jquery-ui-timepicker-addon.css" />

<!--loading jQuery and other library-->
<script type="text/javascript" src="js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript" src="js/script.js"></script>
<script>
/*function validateForm(){ 
var diag1 = document.getElementById('diag1').value;

if(diag1 == ""){
alert('tidak boleh kosong');
return false;
}
};*/
</script>
</head>

<body style="font-family:Arial, Helvetica, sans-serif; font-size:small;">
<h1> SIMRS RS SIMULASI </h1>
<?php if(!isset($_POST['send'])) { ?>
<form action="" method="post"  name="form_client">
<fieldset><legend>Login</legend>
    <table width="100%" border="0">
        <tr>
        <td width="30%">Nama User</td>
        <td><input type="text" name="user_nm" id="user_nm" /></td>
        </tr>
        <tr>
        <td>Password</td>
        <td><input type="password" name="user_pw" id="user_pw" /></td>
        </tr>
    </table>
</fieldset>
<fieldset><legend>Variabel</legend>
	<table width="100%" border="0">
        <tr>
        <td width="30%">No RM</td>
        <td><input type="text" name="norm" id="norm" /></td>
        </tr>
        <tr>
        <td>Nama Pasien</td>
        <td><input type="text" name="nm_pasien" id="nm_pasien" /></td>
        </tr>
        <tr>
        <td>Jenis Kelamin</td>
        <td><table width="200">
          <tr>
            <td><label>
              <input type="radio" name="jns_kelamin" value="1" id="jns_kelamin1" />
              Laki-laki</label></td>
            <td><label>
              <input type="radio" name="jns_kelamin" value="2" id="jns_kelamin2" />
              Perempuan</label></td>
          </tr>
        </table>
        </td>
        </tr>
        <tr>
        <td>Tanggal Lahir</td>
        <td><input type="text" name="tgl_lahir"  id="tgl_lahir" /></td>
        </tr>
        <tr>
        <td>Jenis Pembayaran</td>
        <td><select name="jns_pbyrn" id="jns_pbyrn">
        	<option value="0">--Pilih Jenis Pembayaran--</option>
            <option value="5">JKN</option>
            <option value="8">non jaminan</option>
            <option value="13">Jamkesda</option>
            <option value="23">KJS</option>
        	</select>
        </td>
        </tr>
        <tr>
        <td>No Peserta</td>
        <td><input type="text" name="no_peserta" id="no_peserta" /></td>
        </tr>
        <tr>
        <td>No SEP</td>
        <td><input type="text" name="no_sep" id="no_sep" /></td>
        </tr>
        <tr>
        <td>Jenis Pasien</td>
        <td>
        <label>
              <input type="radio" name="jns_perawatan" value="1" id="jns_perawatan1" />
              Rawat Inap</label><label>
              <input type="radio" name="jns_perawatan" value="0" id="jns_perawatan2" />
              Rawat Jalan</label>
        </td>
        </tr>
        <tr>
        <td>Kelas Perawatan</td>
        <td><input type="text" name="kls_perawatan" id="kls_perawatan" /></td>
        </tr>
        <tr>
        <td>Tanggal Masuk</td>
        <td><input type="text" name="tgl_masuk" id="tgl_masuk" /></td>
        </tr>
        <tr>
        <td>Tanggal Keluar</td>
        <td><input type="text" name="tgl_keluar" id="tgl_keluar" /></td>
        </tr>
        <tr>
        <td>Cara Keluar</td>
        <td>
        <select name="cara_keluar" id="cara_keluar">
        	<option value="1">Sembuh</option>
            <option value="2">Rujuk</option>
            <option value="3">Pulang Paksa</option>
            <option value="4">Meninggal Dunia</option>
             <option value="5">Tidak Tahu</option>
        	</select>
        </td>
        </tr>
        <tr>
        <td>Dokter Penanggungjawab</td>
        <td><input type="text" name="dpjp" id="dpjp" /></td>
        </tr>
        <tr>
        <td>Berat Lahir</td>
        <td><input type="text" name="berat_lahir" id="berat_lahir" /></td>
        </tr>
        <tr>
        <td>Total RS</td>
        <td><input type="text" name="tarif_rs" id="tarif_rs" /></td>
        </tr>
        <tr>
        <td>Surat Rujukan</td>
        <td>
         <select name="srt_rujukan" id="srt_rujukan">
        	<option value="0">Tidak Ada</option>
            <option value="1">Ada</option>
        	</select>
        </td>
        </tr>
        <tr>
        <td>BHP</td>
        <td><input type="text" name="bhp" id="bhp" /></td>
        </tr>
         <tr>
        <td>Severity 3</td>
        <td>
         <select name="severity3" id="severity3">
        	<option value="0">Tidak Ada</option>
            <option value="1">Ada</option>
        	</select>
      </td>
        </tr>
        <?php
		for($i=1; $i<=30;$i++){
		?>
        <tr>
        <td>Diagnosis <?php echo $i; ?></td>
        <td><input type="text" name="diag<?php echo $i; ?>" id="diag<?php echo $i; ?>" /></td>
        </tr>
        <?php
		}
		?>
        
        <?php
		for($i=1; $i<=30;$i++){
		?>
        <tr>
        <td>Procedure <?php echo $i; ?></td>
        <td><input type="text" name="proc<?php echo $i; ?>" id="proc<?php echo $i; ?>" /></td>
        </tr>
        <?php
		}
		?>
        <tr>
        <td>ADL</td>
        <td><input type="text" name="adl" id="adl" /></td>
        </tr>
        <tr>
        <td>Special Procedure</td>
        <td><input type="text" name="spec_proc" id="spec_proc" /></td>
        </tr>
         <tr>
        <td>Special Drugs</td>
        <td><input type="text" name="spec_dr" id="spec_dr" /></td>
        </tr>
         <tr>
        <td>Special Investigation</td>
        <td><input type="text" name="spec_inv" id="spec_inv" /></td>
        </tr>
         <tr>
        <td>Special Prosthesis</td>
        <td><input type="text" name="spec_prosth" id="spec_prosth" /></td>
        </tr>
        
	</table>
</fieldset>
<input name="send" type="submit" value="Send" />
</form>

<?php } else { 
$user_nm=$_POST['user_nm'];
$user_pw=$_POST['user_pw'];
$norm=$_POST['norm'];
$nm_pasien=$_POST['nm_pasien'];
$jns_kelamin=$_POST['jns_kelamin'];
$tgl_lahir=$_POST['tgl_lahir'];
$jns_pbyrn=$_POST['jns_pbyrn'];
$no_peserta=$_POST['no_peserta'];
$no_sep=$_POST['no_sep'];
$jns_perawatan=$_POST['jns_perawatan'];
$kls_perawatan=$_POST['kls_perawatan'];
$tgl_masuk=$_POST['tgl_masuk'];
$tgl_keluar=$_POST['tgl_keluar'];
$cara_keluar=$_POST['cara_keluar'];
$dpjp=$_POST['dpjp'];
$berat_lahir=$_POST['berat_lahir'];
$tarif_rs=$_POST['tarif_rs'];
$srt_rujukan=$_POST['srt_rujukan'];
$bhp=$_POST['bhp'];
$severity3=$_POST['severity3'];
$adl=$_POST['adl'];
$spec_proc=$_POST['spec_proc'];
$spec_dr=$_POST['spec_dr'];
$spec_inv=$_POST['spec_inv'];
$spec_prosth=$_POST['spec_prosth'];
$diag1=$_POST['diag1'];
$diag2=$_POST['diag2'];
$diag3=$_POST['diag3'];
$diag4=$_POST['diag4'];
$diag5=$_POST['diag5'];
$diag6=$_POST['diag6'];
$diag7=$_POST['diag7'];
$diag8=$_POST['diag8'];
$diag9=$_POST['diag9'];
$diag10=$_POST['diag10'];
$diag11=$_POST['diag11'];
$diag12=$_POST['diag12'];
$diag13=$_POST['diag13'];
$diag14=$_POST['diag14'];
$diag15=$_POST['diag15'];
$diag16=$_POST['diag16'];
$diag17=$_POST['diag17'];
$diag18=$_POST['diag18'];
$diag19=$_POST['diag19'];
$diag20=$_POST['diag20'];
$diag21=$_POST['diag21'];
$diag22=$_POST['diag22'];
$diag23=$_POST['diag23'];
$diag24=$_POST['diag24'];
$diag25=$_POST['diag25'];
$diag26=$_POST['diag26'];
$diag27=$_POST['diag27'];
$diag28=$_POST['diag28'];
$diag29=$_POST['diag29'];
$diag30=$_POST['diag30'];
$proc1=$_POST['proc1'];
$proc2=$_POST['proc2'];
$proc3=$_POST['proc3'];
$proc4=$_POST['proc4'];
$proc5=$_POST['proc5'];
$proc6=$_POST['proc6'];
$proc7=$_POST['proc7'];
$proc8=$_POST['proc8'];
$proc9=$_POST['proc9'];
$proc10=$_POST['proc10'];
$proc11=$_POST['proc11'];
$proc12=$_POST['proc12'];
$proc13=$_POST['proc13'];
$proc14=$_POST['proc14'];
$proc15=$_POST['proc15'];
$proc16=$_POST['proc16'];
$proc17=$_POST['proc17'];
$proc18=$_POST['proc18'];
$proc19=$_POST['proc19'];
$proc20=$_POST['proc20'];
$proc21=$_POST['proc21'];
$proc22=$_POST['proc22'];
$proc23=$_POST['proc23'];
$proc24=$_POST['proc24'];
$proc25=$_POST['proc25'];
$proc26=$_POST['proc26'];
$proc27=$_POST['proc27'];
$proc28=$_POST['proc28'];
$proc29=$_POST['proc29'];
$proc30=$_POST['proc30'];

echo 'RESULT';
?>
<script>
$.ajax({
	url: 'http://localhost/inacbg/get_cbg_t_simrs.php?user_nm=<?php echo $user_nm ?>&user_pw=<?php echo $user_pw ?>&norm=<?php echo $norm ?>&nm_pasien=<?php echo $nm_pasien ?>&jns_kelamin=<?php echo $jns_kelamin ?>&tgl_lahir=<?php echo $tgl_lahir ?>&jns_pbyrn=<?php echo $jns_pbyrn ?>&no_peserta=<?php echo $no_peserta ?>&no_sep=<?php echo $no_sep ?>&jns_perawatan=<?php echo $jns_perawatan ?>&kls_perawatan=<?php echo $kls_perawatan ?>&tgl_masuk=<?php echo $tgl_masuk ?>&tgl_keluar=<?php echo $tgl_keluar ?>&cara_keluar=<?php echo $cara_keluar ?>&dpjp=<?php echo $dpjp ?>&berat_lahir=<?php echo $berat_lahir ?>&tarif_rs=<?php echo $tarif_rs ?>&srt_rujukan=<?php echo $srt_rujukan ?>&bhp=<?php echo $bhp ?>&severity3=<?php echo $severity3 ?>&adl=<?php echo $adl ?>&spec_proc=<?php echo $spec_proc ?>&spec_dr=<?php echo $spec_dr ?>&spec_inv=<?php echo $spec_inv ?>&spec_prosth=<?php echo $spec_prosth ?>&diag1=<?php echo $diag1 ?>&diag2=<?php echo $diag2 ?>&diag3=<?php echo $diag3 ?>&diag4=<?php echo $diag4 ?>&diag5=<?php echo $diag5 ?>&diag6=<?php echo $diag6 ?>&diag7=<?php echo $diag7 ?>&diag8=<?php echo $diag8 ?>&diag9=<?php echo $diag9 ?>&diag10=<?php echo $diag10 ?>&diag11=<?php echo $diag11 ?>&diag12=<?php echo $diag12 ?>&diag13=<?php echo $diag13 ?>&diag14=<?php echo $diag14 ?>&diag15=<?php echo $diag15 ?>&diag16=<?php echo $diag16 ?>&diag17=<?php echo $diag17 ?>&diag18=<?php echo $diag18 ?>&diag19=<?php echo $diag19 ?>&diag20=<?php echo $diag20 ?>&diag21=<?php echo $diag21 ?>&diag22=<?php echo $diag22 ?>&diag23=<?php echo $diag23 ?>&diag24=<?php echo $diag24 ?>&diag25=<?php echo $diag25 ?>&diag26=<?php echo $diag26 ?>&diag27=<?php echo $diag27 ?>&diag28=<?php echo $diag28 ?>&diag29=<?php echo $diag29 ?>&diag30=<?php echo $diag30 ?>&proc1=<?php echo $proc1 ?>&proc2=<?php echo $proc2 ?>&proc3=<?php echo $proc3 ?>&proc4=<?php echo $proc4 ?>&proc5=<?php echo $proc5 ?>&proc6=<?php echo $proc6 ?>&proc7=<?php echo $proc7 ?>&proc8=<?php echo $proc8 ?>&proc9=<?php echo $proc9 ?>&proc10=<?php echo $proc10 ?>&proc11=<?php echo $proc11 ?>&proc12=<?php echo $proc12 ?>&proc13=<?php echo $proc13 ?>&proc14=<?php echo $proc14 ?>&proc15=<?php echo $proc15 ?>&proc16=<?php echo $proc16 ?>&proc17=<?php echo $proc17 ?>&proc18=<?php echo $proc18 ?>&proc19=<?php echo $proc19 ?>&proc20=<?php echo $proc20 ?>&proc21=<?php echo $proc21 ?>&proc22=<?php echo $proc22 ?>&proc23=<?php echo $proc23 ?>&proc24=<?php echo $proc24 ?>&proc25=<?php echo $proc25 ?>&proc26=<?php echo $proc26 ?>&proc27=<?php echo $proc27 ?>&proc28=<?php echo $proc28 ?>&proc29=<?php echo $proc29 ?>&proc30=<?php echo $proc30 ?>',
    type: 'GET',
    crossDomain: true,
    dataType: 'jsonp',
	success:function(data) {
    var res = data;
    $.ajax({
		type: "POST",
        url: "insert.php",
        data: { res: res },
		success:function(data){
			document.getElementById('getjson').innerHTML=data;
		}
	});
},
    error: function() {alert('gagal'); }
});
</script>
<div id="getjson"></div>
<?php } ?>
</body>
</html>
