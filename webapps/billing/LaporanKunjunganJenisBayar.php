<?php
 include '../conf/conf.php';
   /* header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanRekapKunjuangnJenisBayar.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
    print "$header\n$data";*/
?>
<html>
    <body>
        
    <?php
    reportsqlinjection();      
        $tanggal1      = $_GET['tanggal1']; 
        $tanggal2      = $_GET['tanggal2']; 

        $_sql = "select kd_pj,png_jawab from penjab ";            
        $hasil=bukaquery($_sql);
        
        if(mysqli_num_rows($hasil)!=0) { 
          echo "<table width='100%'  border='1' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
                 <caption><br/><center><font color='000000' size='4' face='Arial'>Laporan Kunjuangan Rawat Jalan Per Jenis Bayar<br> Periode $tanggal1 s.d. $tanggal2 </font></center><br/></caption>
                 <tr class=isi15>
                    <td width='5%' align=center>No</td>
                    <td width='35%' align=center>Jenis Bayar</td>
                    <td width='20%' align=center>Pasien Lama</td>
                    <td width='20%' align=center>Pasien Baru</td>
                    <td width='20%' align=center>Jumlah Pasien</td>
                 </tr>";
                 $i=1;
                 $lama=0;
                 $baru=0;
                 $total=0;
                 while($baris = mysqli_fetch_array($hasil)) { 
				   $lama=$lama+getOne("select count(no_rawat) from reg_periksa where kd_pj='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2' and stts_daftar='Lama'");
				   $baru=$baru+getOne("select count(no_rawat) from reg_periksa where kd_pj='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2' and stts_daftar='Baru'");
				   $total=$total+getOne("select count(no_rawat) from reg_periksa where kd_pj='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2'");
                   echo "<tr class='isi15'>
                           <td>$i &nbsp;</td>
                           <td>".$baris[1]." &nbsp;</td>
                           <td>".getOne("select count(no_rawat) from reg_periksa where kd_pj='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2' and stts_daftar='Lama'")." &nbsp;</td>
                           <td>".getOne("select count(no_rawat) from reg_periksa where kd_pj='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2' and stts_daftar='Baru'")." &nbsp;</td>
                           <td>".getOne("select count(no_rawat) from reg_periksa where kd_pj='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2'")." &nbsp;</td>
                         </tr>";$i++;
                                      }    
                   echo "
                   <tr class=isi15>
                     <td>&nbsp;</td>
                     <td>Total</td>
                     <td>$lama &nbsp;</td>
                     <td>$baru &nbsp;</td>
                     <td>$total &nbsp;</td>
                   </tr>    
                 </table>";
            
        } else {echo "<b>Data masih kosong !</b>";}
    ?>

    </body>
</html>
