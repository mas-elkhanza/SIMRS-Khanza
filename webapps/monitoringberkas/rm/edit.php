<!DOCTYPE html>
<html>
<head>
	<title>RM</title>
</head>
<body>
	<h2>Edit Data RM</h2>
	<p><a href="rm_kstatus.php">Home</a></p>
	<?php
	include("../koneksi.php");
	$id = $_GET['id'];
	$show = mysql_query("SELECT
            sik.reg_periksa.no_rawat,
            sik.reg_periksa.tgl_registrasi,
            sik.reg_periksa.jam_reg,
            sik.reg_periksa.no_rkm_medis,
            sik.pasien.nm_pasien,
            sik.reg_periksa.no_reg,
            sik.poliklinik.nm_poli,
            sik.reg_periksa.stts,
            sik.reg_periksa.stts_daftar,
            sik.reg_periksa.status_lanjut,
            sik.penjab.png_jawab,
            sik.rm.stts_rm
          FROM
            sik.reg_periksa,
            sik.rm,
            sik.pasien,
            sik.poliklinik,
            sik.penjab
          WHERE
            sik.rm.no_rkm_medis = sik.reg_periksa.no_rkm_medis AND
            sik.pasien.no_rkm_medis = sik.reg_periksa.no_rkm_medis AND
            sik.poliklinik.kd_poli = sik.reg_periksa.kd_poli AND
            sik.penjab.kd_pj = sik.reg_periksa.kd_pj AND sik.reg_periksa.no_rkm_medis='$id'
            ORDER BY
            reg_periksa.tgl_registrasi DESC,
            reg_periksa.jam_reg DESC");

	if(mysql_num_rows($show) == 0){
	    echo "<script>window.history.back()</sript>";
	}
	else{
		$row = mysql_fetch_assoc($show);
	}
	?>
	<form action="edit-proses.php" method="post">
		<table cellpadding="15" cellspacing="0">
			<tr>
				<td>No Rawat</td>
				<td>:</td>
				<td><input type="text" value=<?php echo $row['no_rkm_medis']; ?> name="no_rkm_medis" readonly></td>
			</tr>
			
			<tr>
                <td>Stts RM</td>
                <td>:</td>
                <td>
                <input name="stts_rm" type="radio" value="kembali" <? if (($row['stts_rm'])=='kembali'){echo 'checked';}?>/>kembali  
                <input name="stts_rm" type="radio" value="terkirim" <? if (($row['stts_rm'])=='terkirim'){echo 'checked';}?>/>terkirim
                <input name="stts_rm" type="radio" value="terima" <? if (($row['stts_rm'])=='terima'){echo 'checked';}?>/>terima
                <input name="stts_rm" type="radio" value="pinjam" <? if (($row['stts_rm'])=='pinjam'){echo 'checked';}?>/>pinjam
                <input name="stts_rm" type="radio" value="hilang" <? if (($row['stts_rm'])=='hilang'){echo 'checked';}?>/>hilang
                </td>
            </tr>

			<tr>
				<td colspan="2"></td>
				<td>
					<input type="submit" value="Simpan" name="simpan">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>