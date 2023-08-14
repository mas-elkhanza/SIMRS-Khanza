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
        $cari    = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
        $cari    = json_decode(encrypt_decrypt($cari,"d"),true);
        $keyword = "";
        if (isset($cari["usere"])) {
            if(($cari["usere"]==USERHYBRIDWEB)&&($cari["passwordte"]==PASHYBRIDWEB)){
                $keyword = validTeks($cari["keyword"]);
                $_sql    = "SELECT jnj_jabatan.kode,jnj_jabatan.nama,jnj_jabatan.tnj,jnj_jabatan.indek FROM jnj_jabatan where jnj_jabatan.kode like '%".$keyword."%' or jnj_jabatan.nama like '%".$keyword."%' ORDER BY jnj_jabatan.tnj DESC";
                $hasil   = bukaquery($_sql);
                $jumlah  = mysqli_num_rows($hasil);
                $no      = 1;
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
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
                }else {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='15%'><div align='center'>Kode Jenjang</div></td>
                                <td width='43%'><div align='center'>Nama Jenjang</div></td>
                                <td width='20%'><div align='center'>Tunjangan Jabatan</div></td>
                                <td width='10%'><div align='center'>Index</div></td>
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

