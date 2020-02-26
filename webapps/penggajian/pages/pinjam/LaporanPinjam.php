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
        $say=" pegawai.id=keanggotaan.id and keanggotaan.koperasi='Y'  ";
        $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama
                FROM pegawai,keanggotaan
                where $say and pegawai.nik like '%".$keyword."%' or
                $say and pegawai.nama like '%".$keyword."%'
                ORDER BY pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h1 class=title><font color='999999'>Laporan Peminjaman</font></h1></caption>
                    <tr class='head'>
                        <td width='100px'><div align='center'>NIP</div></td>
                        <td width='200px'><div align='center'>Nama</div></td>
                        <td width='200px'><div align='center'>Keterangan Pinjam</div></td>
                    </tr>";                        
                    while($baris = mysqli_fetch_array($hasil)) {
                        $_sql2="select status from peminjaman_koperasi where
                               status='Belum Lunas' and id='$baris[0]' ";
			$hasil2=bukaquery($_sql2);
                        $jumlah2=mysqli_num_rows($hasil2);
                        $status="Tidak Ada Pinjaman";
                        if($jumlah2!=0){
                           $status="Ada Pinjaman";
                        }
                        echo "<tr class='isi'>
                                <td>$baris[1] &nbsp;</td>
                                <td>$baris[2] &nbsp;</td>
                                <td>$status &nbsp;</td>
                             </tr>";
                    }
            echo "</table>";
            echo("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
  
        } 
    ?>
    </body>
</html>