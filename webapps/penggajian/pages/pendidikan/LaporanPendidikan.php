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
        $_sql = "SELECT tingkat,indek,gapok1,kenaikan,maksimal FROM pendidikan where tingkat like '%".$keyword."%' ORDER BY indek DESC,tingkat ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        $no=1;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h3><font color='999999'>Laporan Master Pendidikan</font></h3></caption>
                    <tr class='head'>
                        <td width='5%'><div align='center'>No.</strong></div></td>
                        <td width='40%'><div align='center'>Tingkat Pendidikan</div></td>
                        <td width='10%'><div align='center'>Index Pendidikan</div></td>
                        <td width='15%'><div align='center'>Gaji Pokok</div></td>
                        <td width='15%'><div align='center'>Kenaikan Per Tahun</div></td>
                        <td width='15%'><div align='center'>Jml Tahun Maksimal</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
								<td>$no</td>  
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                                <td>".formatDuit($baris[2])."</td>
                                <td>".formatDuit($baris[3])."</td>
                                <td>$baris[4]</td> 
                             </tr>";$no++;
                    }
            echo "</table>";
        } 
    ?>
    </body>
</html>
