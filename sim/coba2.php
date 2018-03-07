<?php
include "koneksi.php"; // menghubungkan ke file koneksi.php agar terhubung dengan database
?>

<!DOCTYPE html>
<html>
<head>
 <title>Lihat Data</title>
<!-- CSS untuk mempercantik tampilan-->
 <style type="text/css">
td{
 text-align: center;
}
</style>

</head>

<body>
<fieldset>
<legend><h1>Antrian </h1></legend>
 <div style="margin-bottom:15px;" align="right">
  <form action="" method="post">
   <input type="text" name="input_cari" placeholder="Cari Berdasar Nama Negara" class="css-input" style="width:250px;" />
   <input type="submit" name="cari" value="Cari" class="btn" style="padding:3px;" margin="6px;" width="50px;"  />
  </form>
 </div>

 <table width="100%" border="1px solid #000" style="border-collapse:collapse;">
  <tr style="background-color:#fc0;">
   <th>No Antrian</th>
   <th>No RM</th>
   <th>Nama Pasien</th>
   <th>Alamat</th>
   <th>Poliklinik</th>
  </tr>
   <?php 

   $input_cari = @$_POST['input_cari']; 
   $cari = @$_POST['cari'];

   // jika tombol cari di klik
   if($cari) {

    // jika kotak pencarian tidak sama dengan kosong
    if($input_cari != "") {
    // query mysql untuk mencari berdasarkan nama negara. .
    $sql = mysql_query("SELECT reg.no_reg, reg.no_rkm_medis, pas.nm_pasien, pas.alamat, reg.kd_poli, pol.nm_poli, reg.stts from 
						(reg_periksa reg left JOIN pasien pas on reg.no_rkm_medis = pas.no_rkm_medis ) 
						LEFT JOIN poliklinik pol on reg.kd_poli = pol.kd_poli where reg.tgl_registrasi='2018-02-06' and reg.stts='Belum' and nm_poli like '%$input_cari%'") or die (mysql_error());   
    } else {
    $sql = mysql_query("SELECT reg.no_reg, reg.no_rkm_medis, pas.nm_pasien, pas.alamat, reg.kd_poli, pol.nm_poli, reg.stts from 
						(reg_periksa reg left JOIN pasien pas on reg.no_rkm_medis = pas.no_rkm_medis ) 
						LEFT JOIN poliklinik pol on reg.kd_poli = pol.kd_poli where reg.tgl_registrasi='2018-02-06' and reg.stts='Belum'") or die (mysql_error());
    }
    } else {
    $sql = mysql_query("SELECT reg.no_reg, reg.no_rkm_medis, pas.nm_pasien, pas.alamat, reg.kd_poli, pol.nm_poli, reg.stts from 
						(reg_periksa reg left JOIN pasien pas on reg.no_rkm_medis = pas.no_rkm_medis ) 
						LEFT JOIN poliklinik pol on reg.kd_poli = pol.kd_poli where reg.tgl_registrasi='2018-02-06' and reg.stts='Belum'") or die (mysql_error());
    }

   // mengecek data
   $cek = mysqli_num_rows($sql);
   // jika data kurang dari 1
   if($cek < 1) {
    ?>
     <tr> <!--muncul peringata bahwa data tidak di temukan-->
      <td colspan="7" align="center style="padding:10px;""> Data Tidak Ditemukan</td>
     </tr>
    <?php
   } else {

   // mengulangi data agar tidak hanya 1 yang tampil
   while($data = mysqli_fetch_array($sql)) {

   ?>
   <tr>
	<td><?php echo $data['no_reg'] ?></td>
    <td><?php echo $data['no_rkm_medis'] ?></td>
    <td><?php echo $data['nm_pasien'] ?></td>
	<td><?php echo $data['alamat'] ?></td>
	<td><?php echo $data['nm_poli'] ?></td>
    
    <!--Hanya pemanis tampilan-->
    
   </tr>
  <?php 

  } 
 }
?>
 </table>
</fieldset>
</body>
</html>