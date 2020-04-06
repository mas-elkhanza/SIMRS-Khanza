<?php
 include '../../../conf/conf.php';
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
   <?php
        $keyword=$_GET['keyword'];
        $keyword= validTeks($keyword);
        $_sql = "select pegawai.id,pegawai.nik,pegawai.nama,
		 pegawai.departemen from pegawai
		 where pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' or
		 pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%' or
		 pegawai.stts_aktif<>'KELUAR' and pegawai.departemen like '%".$keyword."%'
		 order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h1 class=title><font color='999999'>Laporan Penerima Tunjangan</font></h1></caption>
                    <tr class='head'>
                        <td width='100px'><div align='center'>NIP</div></td>
                        <td width='250px'><div align='center'>Nama</div></td>
                        <td width='100px'><div align='center'>Departemen</div></td>
                        <td width='300px'><div align='center'>Tnj. Bulanan Diterima</div></td>
			<td width='250px'><div align='center'>Tnj. Harian Diterima</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
                                <td>$baris[1]&nbsp;</td>
                                <td>$baris[2]&nbsp;</td>
                                <td>$baris[3]&nbsp;</td>
                                <td>";$_sql2="select master_tunjangan_bulanan.nama,
				             master_tunjangan_bulanan.tnj
					     from pnm_tnj_bulanan,master_tunjangan_bulanan
					     where pnm_tnj_bulanan.id_tnj=master_tunjangan_bulanan.id
					     and pnm_tnj_bulanan.id='$baris[0]'";
				      $hasil2=bukaquery($_sql2);
				     while($baris2 = mysqli_fetch_array($hasil2)) {
					  echo "<table width='300px'><tr class='isi3'><td width='150px'>$baris2[0]&nbsp;</td><td>: ".formatDuit($baris2[1])."&nbsp;</td></tr></table>";
				     }
				    echo"&nbsp;
				</td>
				<td>";$_sql2="select master_tunjangan_harian.nama,
				             master_tunjangan_harian.tnj
					     from pnm_tnj_harian,master_tunjangan_harian
					     where pnm_tnj_harian.id_tnj=master_tunjangan_harian.id
					     and pnm_tnj_harian.id='$baris[0]'";
				      $hasil2=bukaquery($_sql2);
				     while($baris2 = mysqli_fetch_array($hasil2)) {
					  echo "<table width='300px'><tr class='isi3'><td width='150px'>$baris2[0]&nbsp;</td><td>: ".formatDuit($baris2[1])."&nbsp;</td></tr></table>";
				     }
				    echo"&nbsp;
				</td>
                             </tr>";
                    }
            echo "</table>";
        } 
    ?>
    </body>
</html>