<?php
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
                $keyword = validTeks($cari["keyword"]);
                $_sql    = "SELECT kelompok_jabatan.kode_kelompok,kelompok_jabatan.nama_kelompok,kelompok_jabatan.indek FROM kelompok_jabatan where kelompok_jabatan.kode_kelompok like '%".$keyword."%' or kelompok_jabatan.nama_kelompok like '%".$keyword."%' ORDER BY kelompok_jabatan.indek desc";
                $hasil   = bukaquery($_sql);
                $jumlah  = mysqli_num_rows($hasil);
                $no=1;
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <caption><h3><font color='999999'>Laporan Master Kelompok Jabatan</font></h3></caption>
                            <tr class='head'>
                                <td width='10%'><div align='center'>No.</strong></div></td>
                                <td width='20%'><div align='center'>Kode</div></td>
                                <td width='48%'><div align='center'>Kelompok Jabatan</div></td>
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
                }else {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>No.</strong></div></td>
                                <td width='20%'><div align='center'>Kode</div></td>
                                <td width='48%'><div align='center'>Kelompok Jabatan</div></td>
                                <td width='20%'><div align='center'>Index</div></td>
                            </tr>
                          </table>";
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

