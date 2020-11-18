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
		$tahun     = empty($baristhn[0])?date("Y"):$baristhn[0];
        $blnini    = empty($baristhn[1])?date("m"):$baristhn[1];
        $bln_leng  = strlen($blnini);
        $bulan     = "0";
        if ($bln_leng==1){
            $bulan="0".$blnini;
        }else{
            $bulan=$blnini;
        }
		
        $keyword=isset($_GET['keyword'])?$_GET['keyword']:NULL;
        $keyword= validTeks($keyword);
        $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama,
		        pegawai.departemen,sum(tindakan.jmlh),sum(tindakan.jm)
                FROM tindakan right OUTER JOIN pegawai
				ON tindakan.id=pegawai.id and tgl like '%".$tahun."-".$bulan."%'
				where pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' and pegawai.jbtn like '%dokter umum%' or
				pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%' and pegawai.jbtn like '%dokter umum%' or
				pegawai.stts_aktif<>'KELUAR' and pegawai.departemen like '%".$keyword."%' and pegawai.jbtn like '%dokter umum%'
				group by pegawai.id order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
		$ttljm=0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h1 class=title><font color='999999'>Laporan Tindakan Dokter Tahun ".$tahun." Bulan ".$bulan."</font></h1><br></caption>
                    <tr class='head'>
                        <td width='100px'><div align='center'>NIP</div></td>
                        <td width='250px'><div align='center'>Nama</div></td>
                        <td width='100px'><div align='center'>Departemen</div></td>
                        <td width='100px'><div align='center'>Jumlah Tindakan</div></td>
                        <td width='150px'><div align='center'>Ttl.JM Tindakan</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
					    $ttljm=$ttljm+$baris[5];
                        echo "<tr class='isi'>
                                <td>$baris[1]&nbsp;</td>
                                <td>$baris[2]&nbsp;</td>
                                <td>$baris[3]&nbsp;</td>
                                <td>";$_sql2="select master_tindakan.nama,sum(tindakan.jmlh)
                                              from master_tindakan,tindakan
                                              where tindakan.tnd=master_tindakan.id and
                                              tindakan.id='$baris[0]'
                                              and tgl like '%".$tahun."-".$bulan."%'
                                              group by tindakan.tnd ";
				      $hasil2=bukaquery($_sql2);
				     while($baris2 = mysqli_fetch_array($hasil2)) {
					  echo "<table width='300px'><tr class='isi3'><td width='200px'>$baris2[0]&nbsp;</td><td>: $baris2[1]&nbsp;</td></tr></table>";
				     }
				    echo"&nbsp;
				</td>
                                <td>".formatDuit($baris[5])."&nbsp;</td>
                             </tr>";
                    }
            echo "</table>";
            echo("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Jumlah Total JM : ".formatDuit($ttljm)."</div></td>                        
                    </tr>     
                 </table>");
        } 
    ?>
    </body>
</html>