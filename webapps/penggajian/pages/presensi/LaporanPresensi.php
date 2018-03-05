<?php
 include '../../../conf/conf.php';
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
   <?php
        $_sql         = "SELECT * FROM set_tahun";
        $hasil        = bukaquery($_sql);
        $baris        = mysqli_fetch_row($hasil);
        $tahun         = $baris[0];
        $bln_leng=strlen($baris[1]);
        $bulan="0";
        if ($bln_leng==1){
                $bulan="0".$baris[1];
        }else{
		$bulan=$baris[1];
        } 
		$keyword=$_GET['keyword'];
        $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama
                FROM pegawai where pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' or pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%'
                ORDER BY pegawai.nik ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h1 class=title><font color='999999'>Laporan Lembur Tahun ".$tahun." Bulan ".$bulan."</font></h1></caption>
                    <tr class='head'>
                        <td width='100px'><div align='center'>NIP</div></td>
                        <td width='200px'><div align='center'>Nama</div></td>
                        <td width='100px'><div align='center'>Hadir HB</div></td>
                        <td width='100px'><div align='center'>Index Lembur HB</div></td>
                        <td width='100px'><div align='center'>Hadir HR</div></td>
                        <td width='100px'><div align='center'>Index Lembur HR</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        $_sql2="select count(presensi.id),sum(presensi.lembur)
                                from presensi
                                where presensi.id='$baris[0]' and presensi.tgl like '%".$tahun."-".$bulan."%'
                                and presensi.jns='HB'
                                group by presensi.id";
			$hasil2=bukaquery($_sql2);
			$baris2 = mysqli_fetch_array($hasil2);
                        $_sql3="select count(presensi.id),sum(presensi.lembur)
                                from presensi
                                where presensi.id='$baris[0]' and presensi.tgl like '%".$tahun."-".$bulan."%'
                                and presensi.jns='HR'
                                group by presensi.id";
			$hasil3=bukaquery($_sql3);
			$baris3=mysqli_fetch_array($hasil3);
                        echo "<tr class='isi'>
                                <td>$baris[1]&nbsp;</td>
                                <td>$baris[2]&nbsp;</td>
                                <td>$baris2[0]&nbsp;</td>
                                <td>$baris2[1]&nbsp;</td>
                                <td>$baris3[0]&nbsp;</td>
                                <td>$baris3[1]&nbsp;</td>
                             </tr>";
                    }
            echo "</table>";
        }        
    ?>
    </body>
</html>