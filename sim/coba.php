

<html lang="en">

<head>

    
    
</head>

<body>

<div id="smallRight"><h3 style="background-color:#A6D44D">Jadwal Dokter</h3>
<table style="border: none;font-size: 12px;color: #5b5b5b;width: 100%;margin: 10px 0 10px 0;">
<tr>
<td colspan="5">
<form method="post" action="">
<select name="kd_poli">
<option value="" disabled="disabled">--informasi--</option>
 <?php
 require_once('conf/conf.php');
 $a="SELECT * FROM poliklinik";
 $sql=mysql_query($a);
 while($data=mysqli_fetch_array($sql)){
 ?>
 <option value="<?php echo $data['kd_poli']?>"><?php echo $data['nm_poli']?></option>
 <?php
 }
 ?>
 </select>
 <input type="submit" value="cari"/>
 </form>
 </td>
 </tr>
 </table>
 <table style="border: none;font-size: 12px;color: #5b5b5b;width: 100%;margin: 10px 0 10px 0;">
 <tr>
 <td style="border: none;padding: 4px;"><b>Hari</b></td>
 <td style="border: none;padding: 4px;"><b>Jam</b></td>
 <td style="border: none;padding: 4px;"><b>Dokter</b></td>
 <td style="border: none;padding: 4px;"><b>Keterangan</b></td>
 </tr>
 <?php
 if(isset($_POST['kd_poli'])){
 $sql = "select * from reg_periksa WHERE kd_poli = ".$_POST['kd_poli'];
 $q = mysql_query($sql);
 while($data = mysqli_fetch_array($q)){
 ?>
 <tr>
 <td style="border: none;padding: 4px;"><?php echo $data['no_reg'];?></td>
 <td style="border: none;padding: 4px;"><?php echo $data['kd_poli'];?></td>
 <td style="border: none;padding: 4px;"><?php echo $data['nm_poli'];?></td>
 <td style="border: none;padding: 4px;"><?php echo $data['no_rkm_medis'];?></td>
 </tr>
 <?php
 }
 }
 ?>
  </table>
 </div>
     <script src="asset/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="asset/js/bootstrap.min.js"></script>

</body>

</html>