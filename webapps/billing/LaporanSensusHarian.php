<?php
 include '../conf/conf.php';
    /*$line1="ID\tProduct\tColor\tSales\t";
    $line2="1\tPrinter\tGrey\t13\t";
    $line3="2\tCD\tBlue\t15\t";
    $line4="3\tDVD\tRed\t7\t";
    $line5="4\tMonitor\tGreen\t4\t";
    $line6="5\tTelephone\tBlack\t2\t";

    $data="$line1\n$line2\n$line3\n$line4\n$line5\n$line6\n";

    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanRL53penyakitralan.xls");
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
        $kamar         = $_GET['kamar']; 


          echo "<table width='100%'  border='1' align='left' cellpadding='0' cellspacing='0'>
                 <caption><center><font color='000000' size='4' face='Arial'>Sensus Harian Ranap Inap<br> Periode $tanggal1 s.d. $tanggal2 </font></center><br/></caption>
                 <tr class=isi15>
                    <td width='50%' align='center' valign='top'>
                       <table width='100%' border='1' cellpadding='0' cellspacing='0'>
                          <caption><center><font color='000000' size='2' face='Arial'>Pasien Masuk</font></center></caption>
                          <tr>
                             <td width='10%' rowspan='2'><center><font color='000000' size='2' face='Arial'>No.RM</font></center></td>
                             <td width='20%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Nama Pasien</font></center></td>
                             <td width='20%' colspan='2'><center><font color='000000' size='2' face='Arial'>Rujukan</font></center></td>
                             <td width='10%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Kls</font></center></td>
                             <td width='10%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Km</font></center></td>
                             <td width='10%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Kjp</font></center></td>
                             <td width='20%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Dokter</font></center></td>
                          </tr>
                          <tr>
                             <td width='10%'><center><font color='000000' size='2' face='Arial'>Ya</font></center></td>
                             <td width='10%'><center><font color='000000' size='2' face='Arial'>Tdk</font></center></td>
                          </tr>";
                          $_sql = "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,penjab.png_jawab,kamar.kelas,
                                   kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.tgl_masuk,
                                   kamar_inap.tgl_keluar,kamar_inap.stts_pulang,dokter.nm_dokter from kamar_inap inner join reg_periksa 
                                   inner join pasien inner join kamar inner join bangsal inner join dokter inner join penjab 
                                   on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                                   and reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj 
                                   and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal where 
                                   kamar_inap.tgl_masuk between '$tanggal1' and '$tanggal2' and bangsal.nm_bangsal like '%$kamar%' group by kamar_inap.no_rawat";            
                          $hasil=bukaquery($_sql);
                          while($baris = mysqli_fetch_array($hasil)) { 							  
							  $ya="&nbsp;";
							  $tdk="&nbsp;";
							  if(getOne("select count(no_rawat) from rujuk_masuk where no_rawat='".$baris["no_rawat"]."'")>0){
								  $ya="Ya";
							  }else{
								  $tdk="Tdk";
							  }
							  echo "<tr>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>".$baris["no_rkm_medis"]."</font></center></td>
									 <td width='20%'><center><font color='000000' size='2' face='Arial'>".$baris["nm_pasien"]."</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>$ya</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>$tdk</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>".$baris["kelas"]."</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>".$baris["kd_kamar"]."</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>".$baris["png_jawab"]."</font></center></td>
									 <td width='20%'><center><font color='000000' size='2' face='Arial'>".$baris["nm_dokter"]."</font></center></td>
								  </tr>";
						  }
            echo "
                       </table>
                    </td>
                    <td width='50%' align=center valign='top'>
                       <table width='100%' border='1' cellpadding='0' cellspacing='0'>
                          <caption><center><font color='000000' size='2' face='Arial'>Pasien Keluar</font></center></caption>
                          <tr>
                             <td width='10%' rowspan='2'><center><font color='000000' size='2' face='Arial'>No.RM</font></center></td>
                             <td width='20%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Nama Pasien</font></center></td>
                             <td width='10%' colspan='2'><center><font color='000000' size='2' face='Arial'>Rujukan</font></center></td>
                             <td width='10%' rowspan='2'><center><font color='000000' size='2' face='Arial'>KPK</font></center></td>
                             <td width='10%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Kls</font></center></td>
                             <td width='10%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Km</font></center></td>
                             <td width='10%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Kjp</font></center></td>
                             <td width='20%' rowspan='2'><center><font color='000000' size='2' face='Arial'>Dokter</font></center></td>
                          </tr>
                          <tr>
                             <td width='10%'><center><font color='000000' size='2' face='Arial'>Ya</font></center></td>
                             <td width='10%'><center><font color='000000' size='2' face='Arial'>Tdk</font></center></td>
                          </tr>";
                          $_sql = "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,penjab.png_jawab,kamar.kelas,
                                   kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.stts_pulang,
                                   kamar_inap.tgl_keluar,kamar_inap.stts_pulang,dokter.nm_dokter from kamar_inap inner join reg_periksa 
                                   inner join pasien inner join kamar inner join bangsal inner join dokter inner join penjab 
                                   on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                                   and reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj 
                                   and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal where 
                                   kamar_inap.tgl_masuk between '$tanggal1' and '$tanggal2' and bangsal.nm_bangsal like '%$kamar%' 
                                   and kamar_inap.tgl_keluar<>'0000-00-00' group by kamar_inap.no_rawat";            
                          $hasil=bukaquery($_sql);
                          while($baris = mysqli_fetch_array($hasil)) { 							  
							  $ya="&nbsp;";
							  $tdk="&nbsp;";
							  if(getOne("select count(no_rawat) from rujuk_masuk where no_rawat='".$baris["no_rawat"]."'")>0){
								  $ya="Ya";
							  }else{
								  $tdk="Tdk";
							  }
							  echo "<tr>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>".$baris["no_rkm_medis"]."</font></center></td>
									 <td width='20%'><center><font color='000000' size='2' face='Arial'>".$baris["nm_pasien"]."</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>$ya</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>$tdk</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>".$baris["stts_pulang"]."</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>".$baris["kelas"]."</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>".$baris["kd_kamar"]."</font></center></td>
									 <td width='10%'><center><font color='000000' size='2' face='Arial'>".$baris["png_jawab"]."</font></center></td>
									 <td width='20%'><center><font color='000000' size='2' face='Arial'>".$baris["nm_dokter"]."</font></center></td>
								  </tr>";
						  }
            echo "
                       </table>
                    </td>
                 </tr>";
                 
                 
                 echo "</table>";            
    ?>

    </body>
</html>
