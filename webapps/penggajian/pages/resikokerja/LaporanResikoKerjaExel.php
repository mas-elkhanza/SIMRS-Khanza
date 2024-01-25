<?php
    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanResikoKerja.xls");
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
        $cari    = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
        $cari    = json_decode(encrypt_decrypt($cari,"d"),true);
        $keyword = "";
        if (isset($cari["usere"])) {
            if(($cari["usere"]==USERHYBRIDWEB)&&($cari["passwordte"]==PASHYBRIDWEB)){
                $keyword = validTeks4($cari["keyword"],20);
                $_sql    = "SELECT resiko_kerja.kode_resiko,resiko_kerja.nama_resiko,resiko_kerja.indek FROM resiko_kerja where resiko_kerja.kode_resiko like '%".$keyword."%' or resiko_kerja.nama_resiko like '%".$keyword."%' ORDER BY resiko_kerja.indek desc";
                $hasil   = bukaquery($_sql);
                $jumlah  = mysqli_num_rows($hasil);
                $no      = 1;
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <caption><h3><font color='999999'>Laporan Master Resiko Kerja</font></h3></caption>
                            <tr class='head'>
                                <td width='10%'><div align='center'>No.</strong></div></td>
                                <td width='20%'><div align='center'>Kode</div></td>
                                <td width='48%'><div align='center'>Resiko Kerja</div></td>
                                <td width='20%'><div align='center'>Index</div></td>
                            </tr>";
                            while($baris = mysqli_fetch_array($hasil)) {
                                echo "<tr class='isi'>
                                        <td>$no</td>  
                                        <td>$baris[0]</td>
                                        <td>$baris[1]</td>
                                        <td>$baris[2]</td>   
                                     </tr>";$no++;
                            }
                    echo "</table>";
                } else {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>					   
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='20%'><div align='center'>Kode</div></td>
                                <td width='48%'><div align='center'>Resiko Kerja</div></td>
                                <td width='20%'><div align='center'>Index</div></td>
                            </tr>
                          </table> ";
                } 
            }else{
                exit(header("Location:../index.php"));
            }
        }else{
            exit(header("Location:../index.php"));
        }     
    ?>
    </body>
</html>
