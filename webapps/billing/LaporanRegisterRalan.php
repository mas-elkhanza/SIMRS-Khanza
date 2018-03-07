<?php
    include '../conf/conf.php';
    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanHarianRalan.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
    print "$header\n$data";
?>
<html>
    <body>

    <?php
    reportsqlinjection();      
        $petugasp   =str_replace("_"," ",$_GET['petugasp']); 
        $petugass   =str_replace("_"," ",$_GET['petugass']); 
        $petugasm   =str_replace("_"," ",$_GET['petugasm']); 
        $cari       =str_replace("_"," ",$_GET['cari']); 
        $jamp1      = $_GET['jamp1']; 
        $jamp2      = $_GET['jamp2']; 
        $jams1      = $_GET['jams1']; 
        $jams2      = $_GET['jams2']; 
        $jamm1      = $_GET['jamm1']; 
        $jamm2      = $_GET['jamm2']; 
        $tanggal   = $_GET['tanggal']; 

        $_sql = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                   reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,pasien.alamat,
                   reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,pasien.jk,pasien.umur,pasien.tgl_daftar 
                   from reg_periksa inner join dokter inner join pasien inner join poliklinik 
                   on reg_periksa.kd_dokter=dokter.kd_dokter 
                   and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                   and reg_periksa.kd_poli=poliklinik.kd_poli 
                   where reg_periksa.tgl_registrasi='$tanggal' and poliklinik.nm_poli like '%$cari%' or
                   reg_periksa.tgl_registrasi='$tanggal' and dokter.nm_dokter like '%$cari%' or
                   reg_periksa.tgl_registrasi='$tanggal' and reg_periksa.almt_pj like '%$cari%' ";            
        $hasil=bukaquery($_sql);
        
        if(mysqli_num_rows($hasil)!=0) { 
          echo "<table width='100%'  border='0' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
            <tr>
              <td colspan='6'>
                 <table width=100% border='0'>
                    <tr class='isi14'>
                       <td width='17%'>
                          <font color='000000' size='2' face='Tahoma'>Hari / Tanggal</font>
                       </td>
                       <td width='17%'>
                           <font color='000000' size='2' face='Tahoma'>: ".substr($tanggal,8,2)."-".substr($tanggal,5,2)."-".substr($tanggal,0,4)."</font>
                       </td>
                       <td width='17%'></td>
                       <td width='17%'></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>Petugas Jaga</font></td>
                       <td width='17%'>:</td>
                    </tr> 
                    <tr class='isi14'>
                       <td width='17%'>
                          <font color='000000' size='2' face='Tahoma'>P</font>
                       </td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>: ".
                             getOne("select count(no_rawat) from reg_periksa where tgl_registrasi='$tanggal' and jam_reg between '$jamp1' and '$jamp2'")."</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>Kunjungan Lama</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>: ".
                             getOne("select count(reg_periksa.no_rawat) from reg_periksa inner join pasien
                                   on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where 
                                   reg_periksa.tgl_registrasi='$tanggal' and pasien.tgl_daftar<>'$tanggal'")."</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>P</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>: $petugasp</font></td>
                    </tr> 
                    <tr class='isi14'>
                       <td width='17%'>
                          <font color='000000' size='2' face='Tahoma'>S</font>
                       </td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>: ".
                             getOne("select count(no_rawat) from reg_periksa where tgl_registrasi='$tanggal' and jam_reg between '$jams1' and '$jams2'")."</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>Kunjungan Baru</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>: ".
                             getOne("select count(reg_periksa.no_rawat) from reg_periksa inner join pasien
                                   on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where 
                                   reg_periksa.tgl_registrasi='$tanggal' and pasien.tgl_daftar='$tanggal'")."</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>S</font></td>
                       <td width='17%'>: $petugass</td>
                    </tr> 
                    <tr class='isi14'>
                       <td width='17%'>
                          <font color='000000' size='2' face='Tahoma'>M</font>
                       </td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>: ".
                             getOne("select count(no_rawat) from reg_periksa where tgl_registrasi='$tanggal' and jam_reg between '$jamm1' and '$jamm2'")."</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>Jumlah </font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>: ".
                             (getOne("select count(reg_periksa.no_rawat) from reg_periksa inner join pasien
                                   on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where 
                                   reg_periksa.tgl_registrasi='$tanggal' and pasien.tgl_daftar='$tanggal'")+
                             getOne("select count(reg_periksa.no_rawat) from reg_periksa inner join pasien
                                   on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where 
                                   reg_periksa.tgl_registrasi='$tanggal' and pasien.tgl_daftar<>'$tanggal'"))."</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>M</font></td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>: $petugasm</font></td>
                    </tr> 
                    <tr class='isi14'>
                       <td width='17%'>
                          <font color='000000' size='2' face='Tahoma'>Jmlh</font>
                       </td>
                       <td width='17%'><font color='000000' size='2' face='Tahoma'>: ".
                             (getOne("select count(no_rawat) from reg_periksa where tgl_registrasi='$tanggal' and jam_reg between '$jamp1' and '$jamp2'")+
                              getOne("select count(no_rawat) from reg_periksa where tgl_registrasi='$tanggal' and jam_reg between '$jams1' and '$jams2'")+
                              getOne("select count(no_rawat) from reg_periksa where tgl_registrasi='$tanggal' and jam_reg between '$jamm1' and '$jamm2'"))."</font></td>
                       <td width='17%'></td>
                       <td width='17%'></td>
                       <td width='17%'></td>
                       <td width='17%'></td>
                    </tr> 
                 </table>
              </td>
             </tr>
             <tr class='isi13'>
                <td colspan=6>&nbsp;</td>
             </tr>
             <tr class='isi14'>
                <td colspan=6 height='310px' valign=top>
                  <table width=100% cellpadding='0' cellspacing='0' border='1'>
                     <tr class=isi15>
                          <td width='5%' align=center rowspan='2'>No</td>
                          <td width='16%' align=center colspan='2'>No.RM</td>
                          <td width='25%' align=center rowspan='2'>Nama Pasien</td>
                          <td width='10%' align=center colspan='2'>Umur</td>
                          <td width='20%' align=center rowspan='2'>Alamat</td>
                          <td width='14%' align=center rowspan='2'>Diagnosa</td>
                          <td width='10%' align=center rowspan='2'>Dokter Jaga</td>
                     </tr>
                     <tr class=isi15>
                          <td align=center>Lama</td>
                          <td align=center>Baru</td>
                          <td align=center>L</td>
                          <td align=center>P</td>
                     </tr>
                     ";
                                      $i=1;
                                      while($barispesan = mysqli_fetch_array($hasil)) { 
                                          $baru="&nbsp;";
                                          if($barispesan["tgl_registrasi"]==$barispesan["tgl_daftar"]){
                                              $baru=$barispesan["no_rkm_medis"];
                                          }
                                          $lama="&nbsp;";
                                          if($barispesan["tgl_registrasi"]!=$barispesan["tgl_daftar"]){
                                              $lama=$barispesan["no_rkm_medis"];
                                          }
                                          $umurlk="&nbsp;";
                                          $umurpr="&nbsp;";
                                          if($barispesan["jk"]=="L"){
                                              $umurlk=$barispesan["umur"];
                                          }
                                          if($barispesan["jk"]=="P"){
                                              $umurpr=$barispesan["umur"];
                                          }
                                          echo "
                                            <tr class='isi15'>
                                                <td>$i</td>
                                                <td>".$lama." &nbsp;</td>
                                                <td>".$baru." &nbsp;</td>
                                                <td>".$barispesan["nm_pasien"]." &nbsp;</td>
                                                <td>".$umurlk." &nbsp;</td>
                                                <td>".$umurpr." &nbsp;</td>
                                                <td>".$barispesan["almt_pj"]." &nbsp;</td>
                                                <td>".
                                                getOne("select penyakit.nm_penyakit from penyakit inner join diagnosa_pasien 
                                                        on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit
                                                        where diagnosa_pasien.no_rawat='".$barispesan["no_rawat"]."' ")." &nbsp;</td>
                                                <td>".$barispesan["nm_dokter"]." &nbsp;</td>
                                           </tr>";$i++;
                                      }    
                             echo " 
                          </table>
                      </td>
                    </tr>                   

                 </table>";
            
        } else {echo "<b>Data pesan masih kosong !</b>";}
    ?>

    </body>
</html>
