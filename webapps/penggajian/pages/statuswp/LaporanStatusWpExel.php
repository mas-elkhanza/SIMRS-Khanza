<?php
    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanStatusWp.xls");
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
        $keyword = $_GET['keyword'];
        $keyword = validTeks($keyword);
        $_sql    = "SELECT stts,ktg FROM stts_wp where stts like '%".$keyword."%' or ktg like '%".$keyword."%'  ORDER BY stts ASC ";
        $hasil   = bukaquery($_sql);
        $jumlah  = mysqli_num_rows($hasil);
        $no      = 1;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='1' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h3><font color='999999'>Laporan Master Status WP</font></h3></caption>
                    <tr class='head'>
                        <td width='10%'><div align='center'>No.</strong></div></td>
                        <td width='28%'><div align='center'>Status WP</div></td>
                        <td width='60%'><div align='center'>Keterangan</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
								<td>$no</td>  
                                <td>$baris[0]</td>
                                <td>$baris[1]</td> 
                             </tr>";$no++;
                    }
            echo "</table>";
        }else {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>					    
                        <td width='10%'><div align='center'>Proses</div></td>
                        <td width='28%'><div align='center'>Status WP</div></td>
                        <td width='60%'><div align='center'>Keterangan</div></td>
                    </tr>
                  </table>";
        } 
    ?>
    </body>
</html>
