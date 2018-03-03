<?php
 include '../../../conf/conf.php';
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
   <?php
                $id         = isset($_GET['id'])?$_GET['id']:NULL;
                $tahun      = isset($_GET['tahun'])?$_GET['tahun']:date('y');
                $bulan      = isset($_GET['bulan'])?$_GET['bulan']:date('m');
		$_sql       = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil      = bukaquery($_sql);
                $baris      = mysqli_fetch_row($hasil);

            ?>
            <table width="100%" align="center">
                <caption><h1 class=title><font color='999999'>Laporan Tambahan Jaga Tahun <?php echo $tahun?> Bulan <?php echo $bulan?></font></h1></caption>
                <tr class="head">
                    <td width="31%" >NIP</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[0];?></td>
                </tr>
		<tr class="head">
                    <td width="31%">Nama</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[1];?></td>
                </tr>
            </table>
            <br>
            <?php
                $_sql = "SELECT tgl,id,jml
                        from tambahjaga  where id='$id'
			and tgl like '%".$tahun."-".$bulan."%' ORDER BY tgl ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='90%'><div align='center'>Jml.Tambahan Jaga</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td>$baris[2]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data tambahan jaga masih kosong !";}
        ?>
    </body>
</html>