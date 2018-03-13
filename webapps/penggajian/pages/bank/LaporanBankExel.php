<?php
    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanBank.xls");
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
        $_sql = "SELECT namabank FROM bank where namabank like '%".$keyword."%' ORDER BY namabank ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        $no=1;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='1' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h3><font color='999999'>Laporan Master Bank</font></h3></caption>
                    <tr class='head'>
                        <td width='10%'><div align='center'>No.</strong></div></td>
                        <td width='88%'><div align='center'>Nama Bank</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
				<td>$no</td>  
                                <td>$baris[0] &nbsp;</td>
                             </tr>";$no++;
                    }
            echo "</table>";
        } 
    ?>
    </body>
</html>
