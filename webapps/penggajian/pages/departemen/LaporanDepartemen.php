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
        $_sql = "SELECT dep_id,nama FROM departemen where dep_id like '%".$keyword."%' or nama like '%".$keyword."%' ORDER BY dep_id ASC";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        $no=1;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h3><font color='999999'>Laporan Master Departemen</font></h3></caption>
                    <tr class='head'>
                        <td width='10%'><div align='center'>No.</strong></div></td>
                        <td width='23%'><div align='center'>Dep ID</div></td>
                        <td width='65%'><div align='center'>Nama Departemen</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
								<td>$no</td>  
                                <td>$baris[0] &nbsp;</td>
                                <td>$baris[1] &nbsp;</td>        
                             </tr>";$no++;
                    }
            echo "</table>";
        } 
    ?>
    </body>
</html>
