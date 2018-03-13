<?php
 include '../conf/conf.php';
    $line1="ID\tProduct\tColor\tSales\t";
    $line2="1\tPrinter\tGrey\t13\t";
    $line3="2\tCD\tBlue\t15\t";
    $line4="3\tDVD\tRed\t7\t";
    $line5="4\tMonitor\tGreen\t4\t";
    $line6="5\tTelephone\tBlack\t2\t";

    $data="$line1\n$line2\n$line3\n$line4\n$line5\n$line6\n";

    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanRL52.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
    print "$header\n$data";
?>
<html>
    <body>

    <?php
    reportsqlinjection();      
        $tanggal1      = $_GET['tanggal1']; 
        $tanggal2      = $_GET['tanggal2']; 

        $_sql = "select left(tgl_registrasi,7) from reg_periksa where tgl_registrasi between '$tanggal1' and '$tanggal2' group by left(tgl_registrasi,7)  order by tgl_registrasi";            
        $hasil=bukaquery($_sql);
        
        if(mysqli_num_rows($hasil)!=0) { 
          echo "<table width='100%'  border='1' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
                 <caption><br/><center><font color='000000' size='4' face='Arial'>Laporan RL 52<br> Periode $tanggal1 s.d. $tanggal2 </font></center><br/></caption>
                 <tr class=isi15>
                    <td width='5%' align=center>No</td>
                    <td width='20%' align=center>Bulan</td>
                    <td width='15%' align=center>Tahun</td>
                    <td width='25%' align=center>Poli</td>
                    <td width='10%' align=center>Pasien Lama</td>
                    <td width='10%' align=center>Pasien Baru</td>
                    <td width='15%' align=center>Jumlah Pasien</td>
                 </tr>";
                 $i=1;
                 $lama=0;
                 $baru=0;
                 $total=0;
                 while($baris = mysqli_fetch_array($hasil)) { 
				     $hasil2=bukaquery("select kd_poli, nm_poli from poliklinik");
				     while($baris2 = mysqli_fetch_array($hasil2)) { 
						 $lama=$lama+getOne("select count(no_rawat) from reg_periksa where tgl_registrasi like '%$baris[0]%' and kd_poli='$baris2[0]' and stts_daftar='Lama'");
						 $baru=$baru+getOne("select count(no_rawat) from reg_periksa where tgl_registrasi like '%$baris[0]%' and kd_poli='$baris2[0]' and stts_daftar='Baru'");
						 $total=$total+getOne("select count(no_rawat) from reg_periksa where tgl_registrasi like '%$baris[0]%' and kd_poli='$baris2[0]'");
						 echo "<tr class='isi15'>
								   <td>$i &nbsp;</td>
								   <td>".konversiBulan(substr($baris[0],5,2))."&nbsp;</td>
								   <td>".substr($baris[0],0,4)." &nbsp;</td>
								   <td>".$baris2[1]." &nbsp;</td>
								   <td>".getOne("select count(no_rawat) from reg_periksa where tgl_registrasi like '%$baris[0]%' and kd_poli='$baris2[0]' and stts_daftar='Lama'")." &nbsp;</td>
								   <td>".getOne("select count(no_rawat) from reg_periksa where tgl_registrasi like '%$baris[0]%' and kd_poli='$baris2[0]' and stts_daftar='Baru'")." &nbsp;</td>
								   <td>".getOne("select count(no_rawat) from reg_periksa where tgl_registrasi like '%$baris[0]%' and kd_poli='$baris2[0]'")." &nbsp;</td>
						      </tr>";$i++;
					 }
                 }    
                   echo "
                   <tr class=isi15>
                     <td>&nbsp;</td>
                     <td>Total</td>
                     <td>&nbsp;</td>
                     <td>&nbsp;</td>
                     <td>$lama &nbsp;</td>
                     <td>$baru &nbsp;</td>
                     <td>$total &nbsp;</td>
                   </tr>    
                 </table>";
            
        } else {echo "<b>Data masih kosong !</b>";}
    ?>

    </body>
</html>
