<?php
 include '../../../conf/conf.php';
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
   <?php
        $_sql      = "SELECT * FROM set_tahun";
	$hasil     = bukaquery($_sql);
	$baris     = mysqli_fetch_row($hasil);
	$tahun     = empty($baris[0])?date("Y"):$baris[0];
        $blnini    = empty($baris[1])?date("m"):$baris[1];
        $bln_leng  = strlen($blnini);
        $bulan     = "0";
        if ($bln_leng==1){
            $bulan= "0".$blnini;
        }else{
            $bulan= $blnini;
        }
	$id        = validTeks($_GET['id']);
        $_sql 	   = "SELECT pegawai.nik,pegawai.nama FROM pegawai where pegawai.id='$id'";
        $hasil	   = bukaquery($_sql);
        $baris 	   = mysqli_fetch_row($hasil); 
	$nik	   = $baris[0];
	$nama	   = $baris[1];
        $_sql      = "select tindakan.tgl,tindakan.id,tindakan.tnd,master_tindakan.nama,tindakan.jm,tindakan.nm_pasien,tindakan.kamar,tindakan.diagnosa,tindakan.jmlh from tindakan inner join master_tindakan on tindakan.tnd=master_tindakan.id where tindakan.id='$id' and tindakan.tgl like '%".$tahun."-".$bulan."%' ORDER BY tindakan.tgl ASC";
        $hasil     = bukaquery($_sql);
        $jumlah    = mysqli_num_rows($hasil);
	$ttljm     = 0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h1 class=title><font color='999999'>Laporan Detail Tindakan Paramedis ".$nik." ".$nama." Tahun ".$tahun." Bulan ".$bulan."</font></h1><br></caption>
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
            echo "</table>
                  <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td>Jumlah Total JM : ".formatDuit($ttljm)." </td>                        
                    </tr>     
                  </table>";
        } 
    ?>
    </body>
</html>