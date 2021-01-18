<?php
   include '../../../conf/conf.php';
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun        = empty($baris[0])?date("Y"):$baris[0];
   $bulan        = empty($baris[1])?date("m"):$baris[1];
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
	<center><h1><font color='999999'>Laporan Data Pendapatan Tuslah</font></h1></center>
	&nbsp;Pendapatan Tuslah :
    <?php
        $_sql 		= "SELECT pendapatan_tuslah, persen_rs, bagian_rs, persen_kry,bagian_kry
                      FROM set_tuslah WHERE tahun='$tahun' and bulan='$bulan' ORDER BY bagian_kry";
        $hasil		= bukaquery($_sql);
        $jumlah		= mysqli_num_rows($hasil);
        $pendapatan_tuslah= "0";
        $no			= 1;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='10%'><div align='center'>No.</strong></div></td>                    
                        <td width='25%'><div align='center'>Pendapatan Tuslah</div></td>
                        <td width='6%'><div align='center'>% RS</div></td>
						<td width='25%'><div align='center'>Bagian RS</div></td>
                        <td width='7%'><div align='center'>% Kry</div></td>
                        <td width='25%'><div align='center'>Bagian Kry</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        $pendapatan_tuslah=$baris[4];
                        echo "<tr class='isi'>
								<td>$no</td>  
                                <td>".formatDuit($baris[0])."</td>
                                <td>$baris[1]%</td>
                                <td>".formatDuit($baris[2])."</td>
                                <td>$baris[3]%</td>
                                <td>".formatDuit($baris[4])."</td>                          
                             </tr>";$no++;
                    }
            echo "</table>";
        }else{
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='10%'><div align='center'>No.</strong></div></td>                    
                        <td width='25%'><div align='center'>Pendapatan Tuslah</div></td>
                        <td width='6%'><div align='center'>% RS</div></td>
						<td width='25%'><div align='center'>Bagian RS</div></td>
                        <td width='7%'><div align='center'>% Kry</div></td>
                        <td width='25%'><div align='center'>Bagian Kry</div></td>
                    </tr>
                  </table>";
        }
    ?>
    <br>&nbsp;Pembagian Tuslah :
    <?php
		$keyword 	= trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
                $keyword= validTeks($keyword);
        $_sql 		= "SELECT pembagian_tuslah.id,pegawai.nama,pembagian_tuslah.persen FROM pembagian_tuslah inner join pegawai
					  on pembagian_tuslah.id=pegawai.id where pegawai.nama like '%".$keyword."%' ORDER BY persen desc";
        $hasil		= bukaquery($_sql);
        $jumlah		= mysqli_num_rows($hasil);
		$ttl		= 0;
		$prosen		= 0;
        $no			= 1;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='10%'><div align='center'>No.</strong></div></td>
                        <td width='38%'><div align='center'>Nama Karyawan</div></td>
                        <td width='25%'><div align='center'>Porsi Bagian</div></td>
						<td width='25%'><div align='center'>Bagian Karyawan</div></td>
                    </tr>";                    
					$bagiankry=0;
                    while($baris = mysqli_fetch_array($hasil)) {
						$bagiankry=($baris[2]/100)*$pendapatan_tuslah;
						$ttl=$ttl+$bagiankry;
						$prosen=$prosen+$baris[2];
                        echo "<tr class='isi'>
								<td>$no</td>  
                                <td>$baris[1]</td>
                                <td>$baris[2]%</td>
                                <td>".formatDuit($bagiankry)."</td>                            
                             </tr>";$no++;
                    }
            echo "</table>
				<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, Ttl Prosen : ".$prosen."%, Ttl Bagian : ".formatDuit($ttl)." </div></td>                        
                    </tr>     
                 </table>";
        }else{
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='10%'><div align='center'>No.</strong></div></td>
                        <td width='38%'><div align='center'>Nama Karyawan</div></td>
                        <td width='25%'><div align='center'>Porsi Bagian</div></td>
						<td width='25%'><div align='center'>Bagian Karyawan</div></td>
                    </tr>
                  </table>"; 
        } 
    ?>
    </body>
</html>
