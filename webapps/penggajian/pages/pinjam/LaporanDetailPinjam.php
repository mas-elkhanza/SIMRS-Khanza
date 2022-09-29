<?php
 include '../../../conf/conf.php';
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
    <?php
        $keyword = validTeks(isset($_GET['keyword'])?$_GET['keyword']:NULL);
        $id      = validTeks(isset($_GET['id'])?$_GET['id']:NULL);
        $_sql2   = "SELECT pegawai.nik,pegawai.nama FROM pegawai where pegawai.id='$id'";
        $hasil2  = bukaquery($_sql2);
        $baris2  = mysqli_fetch_row($hasil2);
    ?>
    <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
       <caption><h1 class=title><font color='999999'>Laporan History Peminjaman</font></h1></caption>
        <tr class="head">
             <td width="31%" >NIP</td>
             <td width="">:</td>
             <td width="67%"><?php echo $baris2[0];?></td>
        </tr>
        <tr class="head">
             <td width="31%">Nama</td><td width="">:</td>
             <td width="67%"><?php echo $baris2[1];?></td>
        </tr>               
    </table>
    <br><br>
    <?php
        $_sql      = "select peminjaman_koperasi.id,peminjaman_koperasi.tanggal,peminjaman_koperasi.pinjaman,peminjaman_koperasi.banyak_angsur,peminjaman_koperasi.pokok,
                      peminjaman_koperasi.jasa,peminjaman_koperasi.status from peminjaman_koperasi where peminjaman_koperasi.id='$id' order by peminjaman_koperasi.tanggal ";
        $hasil     = bukaquery($_sql);
        $jumlah    = mysqli_num_rows($hasil);
        $ttllembur = 0;
        $ttlhr     = 0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='10%'><div align='center'>Tgl.Pinjam</div></td>
                        <td width='15%'><div align='center'>Pinjaman</div></td>
                        <td width='10%'><div align='center'>Jml.Angsur</div></td>
                        <td width='14%'><div align='center'>Pokok</div></td>
                        <td width='12%'><div align='center'>Jasa</div></td>
                        <td width='14%'><div align='center'>Angsuran</div></td>
                        <td width='13%'><div align='center'>Status</div></td>
                    </tr>";
            while($baris = mysqli_fetch_array($hasil)) {
              echo "<tr class='isi' title='$baris[1], $baris[2], $baris[3], $baris[4], $baris[5], $baris[6]'>
                        <td>$baris[1]</td>
                        <td>".formatDuit($baris[2])."</td>
                        <td>$baris[3]</td>
                        <td>".formatDuit($baris[4])."</td>
                        <td>".formatDuit($baris[5])."</td>
                        <td>".formatDuit($baris[4]+$baris[5])."</td>
                        <td>$baris[6]</td>
                   </tr>";
            }
            echo "</table>";
        }
    ?>
    </body>
</html>