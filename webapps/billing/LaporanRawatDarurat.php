<?php
 include '../conf/conf.php';
    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanRL32RawatDarurat.xls");
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

        $_sql = "select kd_poli,nm_poli from poliklinik ";            
        $hasil=bukaquery($_sql);
        
        if(mysqli_num_rows($hasil)!=0) { 
          echo "<table width='100%'  border='1' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
                 <caption><br/><center><font color='000000' size='4' face='Arial'>Laporan RL32 Rawat Darurat<br> Periode $tanggal1 s.d. $tanggal2 </font></center><br/></caption>
                 <tr class=isi15>
                    <td width='5%' align=center>No</td>
                    <td width='35%' align=center>Jenis Pelayanan</td>
                    <td width='10%' align=center>Total Pasien Rujukan</td>
                    <td width='10%' align=center>Total Pasien Non Rujukan</td>
                    <td width='10%' align=center>Tindak Lanjut Pelayanan Dirawat</td>
                    <td width='10%' align=center>Tindak Lanjut Pelayanan Dirujuk</td>
                    <td width='10%' align=center>Tindak Lanjut Pelayanan Pulang</td>
                    <td width='10%' align=center>Pasien Meninggal</td>
                 </tr>";
                 $i=1;
                 $rujukan=0;
                 $nonrujukan=0;
                 $dirawat=0;
                 $dirujuk=0;
                 $pulang=0;
                 $meninggal=0;
                 $totalrujukan=0;
                 $totalnonrujukan=0;
                 $totaldirawat=0;
                 $totaldirujuk=0;
                 $totalpulang=0;
                 $totalmeninggal=0;
                 while($baris = mysqli_fetch_array($hasil)) { 
				   $rujukan=getOne("select count(rujuk_masuk.no_rawat) from reg_periksa inner join rujuk_masuk on rujuk_masuk.no_rawat=reg_periksa.no_rawat
                                        where kd_poli='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2'");
                   $nonrujukan=getOne("select count(reg_periksa.no_rawat) from reg_periksa  where kd_poli='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2' 
                                         and reg_periksa.no_rawat not in(select no_rawat from rujuk_masuk)");
                   $dirawat=getOne("select count(reg_periksa.no_rawat) from reg_periksa inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat
                                         where kd_poli='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2' ");
                   $dirujuk=getOne("select count(rujuk.no_rawat) from reg_periksa inner join rujuk on rujuk.no_rawat=reg_periksa.no_rawat
                                         where kd_poli='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2'");
                   $meninggal=getOne("select count(reg_periksa.no_rkm_medis) from pasien_mati inner join reg_periksa on reg_periksa.no_rkm_medis=pasien_mati.no_rkm_medis
                                         where kd_poli='$baris[0]' and tgl_registrasi between '$tanggal1' and '$tanggal2'");
                   $pulang=($rujukan+$nonrujukan)-($dirawat+$dirujuk+$meninggal);
				   $totalrujukan=$totalrujukan+$rujukan;
                   $totalnonrujukan=$totalnonrujukan+$nonrujukan;
                   $totaldirawat=$totaldirawat+$dirawat;
                   $totaldirujuk=$totaldirujuk+$dirujuk;                   
                   $totalpulang=$totalpulang+$pulang;
                   $totalmeninggal=$totalmeninggal+$meninggal;
                   echo "<tr class='isi15'>
                           <td>$i &nbsp;</td>
                           <td>".$baris[1]." &nbsp;</td>
                           <td>".$rujukan." &nbsp;</td>
                           <td>".$nonrujukan." &nbsp;</td>
                           <td>".$dirawat." &nbsp;</td>
                           <td>".$dirujuk." &nbsp;</td>
                           <td>".$pulang." &nbsp;</td>
                           <td>".$meninggal." &nbsp;</td>
                         </tr>";$i++;
                 }    
                   echo "
                   <tr class=isi15>
                     <td>&nbsp;</td>
                     <td>Total</td>
                     <td>$totalrujukan &nbsp;</td>
                     <td>$totalnonrujukan &nbsp;</td>
                     <td>$totaldirawat &nbsp;</td>
                     <td>$totaldirujuk &nbsp;</td>
                     <td>$totalpulang &nbsp;</tdtd>
                     <td>$totalmeninggal&nbsp;</tdtd>
                   </tr>    
                 </table>";
            
        } else {echo "<b>Data masih kosong !</b>";}
    ?>

    </body>
</html>
