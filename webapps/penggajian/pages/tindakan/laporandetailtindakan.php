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
		$id     =$_GET['id'];
		
        $_sql 	= "SELECT nik,nama FROM pegawai where id='$id'";
        $hasil	=bukaquery($_sql);
        $baris 	= mysqli_fetch_row($hasil); 
		$nik	=$baris[0];
		$nama	=$baris[1];
		
        $_sql = "select tindakan.tgl,
                        tindakan.id,
                        tindakan.tnd,
                        master_tindakan.nama,
                        tindakan.jm,
                        tindakan.nm_pasien,
                        tindakan.kamar,
                        tindakan.diagnosa,
                        tindakan.jmlh
                        from tindakan inner join master_tindakan
                        where tindakan.tnd=master_tindakan.id and tindakan.id='$id'
			and tgl like '%".$tahun."-".$bulan."%' ORDER BY tgl ASC";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
		$ttljm=0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h1 class=title><font color='999999'>Laporan Detail Tindakan Paramedis ".$nik." ".$nama." Tahun ".$tahun." Bulan ".$bulan."</font></h1><br>
					</caption>
                    <tr class='head'>
                                <td width='200px'><div align='center'>Nama Tindakan</strong></td>
                                <td width='150px'><div align='center'>JM Tindakan</strong></td>
                                <td width='100px'><div align='center'>Jml.Tindakan</strong></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
					   $ttljm=$ttljm+$baris[4];
                        echo "<tr class='isi'>
                                <td>$baris[3]</td>
                                <td>".formatDuit($baris[4])."</td>
                                <td>$baris[8]</td>
                             </tr>";
                    }
            echo "</table>";
            echo("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td>Jumlah Total JM : ".formatDuit($ttljm)." </td>                        
                    </tr>     
                 </table>");
                
        } 
    ?>
    </body>
</html>