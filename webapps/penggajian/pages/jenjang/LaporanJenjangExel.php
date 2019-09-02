<?php
    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanJenjang.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
    print isset($header)?$header:NULL;
	include '../../../conf/conf.php';
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
   <?php
        $keyword=$_GET['keyword'];
        $_sql = "SELECT kode,nama,tnj,indek FROM jnj_jabatan where kode like '%".$keyword."%' or nama like '%".$keyword."%' ORDER BY tnj DESC";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        $no=1;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='1' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h3><font color='999999'>Laporan Master Jenjang</font></h3></caption>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='15%'><div align='center'>Kode Jenjang</div></td>
                        <td width='43%'><div align='center'>Nama Jenjang</div></td>
                        <td width='20%'><div align='center'>Tunjangan Jabatan</div></td>
                        <td width='10%'><div align='center'>Index</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
			       <td>$no</td>  
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                                <td>".formatDuit($baris[2])."</td>
                                <td align='center'>$baris[3]</td>
                             </tr>";$no++;
                    }
            echo "</table>";
        } 
    ?>
    </body>
</html>
