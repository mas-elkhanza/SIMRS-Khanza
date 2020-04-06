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
                keanggotaan.koperasi, keanggotaan.jamsostek
                from keanggotaan right OUTER JOIN pegawai
                on keanggotaan.id=pegawai.id
				where pegawai.nik like '%".$keyword."%' or 
				pegawai.nama like '%".$keyword."%' or
				keanggotaan.koperasi like '%".$keyword."%' or
				keanggotaan.jamsostek like '%".$keyword."%'
				order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h1 class=title><font color='999999'>Laporan Keanggotaan Koperasi & Jamsostek</font></h1></caption>
                    <tr class='head'>
                        <td width='100px'><div align='center'>NIP</div></td>
                        <td width='250px'><div align='center'>Nama</div></td>
                        <td width='100px'><div align='center'>Anggota Koperasi</div></td>
                        <td width='100px'><div align='center'>Anggota Jamsostek</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
                                <td>$baris[1]&nbsp;</td>
                                <td>$baris[2]&nbsp;</td>
                                <td>$baris[3]&nbsp;</td>
                                <td>$baris[4]&nbsp;</td>
                             </tr>";
                    }
            echo "</table>";
        } 
    ?>
    </body>
</html>