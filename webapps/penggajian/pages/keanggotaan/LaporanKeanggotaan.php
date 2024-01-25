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
                $_sql    = "select pegawai.id,pegawai.nik,pegawai.nama,keanggotaan.koperasi,keanggotaan.jamsostek,keanggotaan.bpjs from keanggotaan right OUTER JOIN pegawai on keanggotaan.id=pegawai.id where pegawai.stts_aktif<>'KELUAR' and 
                            (pegawai.nik like '%".$keyword."%' or pegawai.nama like '%".$keyword."%' or keanggotaan.koperasi like '%".$keyword."%' or keanggotaan.bpjs like '%".$keyword."%' or keanggotaan.jamsostek like '%".$keyword."%') order by pegawai.id ASC ";
                $hasil   = bukaquery($_sql);
                $jumlah  = mysqli_num_rows($hasil);
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <caption><h3><font color='999999'>Laporan Keanggotaan Koperasi & Jamsostek</font></h3></caption>
                            <tr class='head'>
                                <td width='100px'><div align='center'>NIP</div></td>
                                <td width='250px'><div align='center'>Nama</div></td>
                                <td width='100px'><div align='center'>Anggota Koperasi</div></td>
                                <td width='100px'><div align='center'>Anggota Jamsostek</div></td>
                                <td width='100px'><div align='center'>Anggota BPJS</div></td>
                            </tr>";
                            while($baris = mysqli_fetch_array($hasil)) {
                                echo "<tr class='isi'>
                                        <td>$baris[1]&nbsp;</td>
                                        <td>$baris[2]&nbsp;</td>
                                        <td>$baris[3]&nbsp;</td>
                                        <td>$baris[4]&nbsp;</td>
                                        <td>$baris[5]&nbsp;</td>
                                     </tr>";
                            }
                    echo "</table>";
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