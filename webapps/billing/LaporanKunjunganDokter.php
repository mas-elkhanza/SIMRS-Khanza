<?php
    include '../conf/conf.php';
    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanRekapKunjuangnDokter.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
?>
<html>
    <body>
    <?php
        reportsqlinjection();  
        $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
        $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
            $tanggal1      = validTeks4($_GET['tanggal1'],20); 
            $tanggal2      = validTeks4($_GET['tanggal2'],20); 

            $_sql = "select dokter.kd_dokter,dokter.nm_dokter from dokter ";            
            $hasil=bukaquery($_sql);

            if(mysqli_num_rows($hasil)!=0) { 
              echo "<table width='100%'  border='1' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
                     <caption><br/><center><font color='000000' size='4' face='Arial'>Laporan Kunjuangan Rawat Jalan Per Dokter<br> Periode $tanggal1 s.d. $tanggal2 </font></center><br/></caption>
                     <tr class=isi15>
                        <td width='5%' align=center>No</td>
                        <td width='35%' align=center>Nama Dokter</td>
                        <td width='20%' align=center>Pasien Lama</td>
                        <td width='20%' align=center>Pasien Baru</td>
                        <td width='20%' align=center>Jumlah Pasien</td>
                     </tr>";
                     $i=1;
                     $lama=0;
                     $baru=0;
                     $total=0;
                     while($baris = mysqli_fetch_array($hasil)) { 
                                       $lama=$lama+getOne("select count(reg_periksa.no_rawat) from reg_periksa where reg_periksa.kd_dokter='$baris[0]' and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2' and reg_periksa.stts_daftar='Lama'");
                                       $baru=$baru+getOne("select count(reg_periksa.no_rawat) from reg_periksa where reg_periksa.kd_dokter='$baris[0]' and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2' and reg_periksa.stts_daftar='Baru'");
                                       $total=$total+getOne("select count(reg_periksa.no_rawat) from reg_periksa where reg_periksa.kd_dokter='$baris[0]' and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2'");
                       echo "<tr class='isi15'>
                               <td>$i &nbsp;</td>
                               <td>".$baris[1]." &nbsp;</td>
                               <td>".getOne("select count(reg_periksa.no_rawat) from reg_periksa where reg_periksa.kd_dokter='$baris[0]' and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2' and reg_periksa.stts_daftar='Lama'")." &nbsp;</td>
                               <td>".getOne("select count(reg_periksa.no_rawat) from reg_periksa where reg_periksa.kd_dokter='$baris[0]' and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2' and reg_periksa.stts_daftar='Baru'")." &nbsp;</td>
                               <td>".getOne("select count(reg_periksa.no_rawat) from reg_periksa where reg_periksa.kd_dokter='$baris[0]' and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2'")." &nbsp;</td>
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
        }else {
            exit(header("Location:../index.php"));
        }    
            
    ?>

    </body>
</html>
