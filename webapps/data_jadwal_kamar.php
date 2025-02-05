 <?php
 require_once('conf/conf.php');
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
 date_default_timezone_set("Asia/Bangkok");
 $tanggal= mktime(date("m"),date("d"),date("Y"));
 $jam=date("H:i");
?>
 <div class="col s12 row" style="font-size:25px;">
            <div class="col s7">
                <h5 style="font-size:45px;" class="center"><i class="material-icons md-36">group</i> Jadwal Praktek Dokter</h5>
                <table class="default">
                    <thead>
                      <tr>
                          <th><b>Nama Dokter</b></th>
                          <th><b>Poliklinik</b></th>
                          <th><b>Mulai</b></th>
                          <th><b>Selesai</b></th>
                      </tr>
                    </thead>
                    <tbody>
                      <?php  
                          $hari=getOne("select DAYNAME(current_date())");
                            $namahari="";
                            if($hari=="Sunday"){
                            $namahari="AKHAD";
                          }else if($hari=="Monday"){
                            $namahari="SENIN";
                          }else if($hari=="Tuesday"){
                            $namahari="SELASA";
                          }else if($hari=="Wednesday"){
                            $namahari="RABU";
                          }else if($hari=="Thursday"){
                            $namahari="KAMIS";
                          }else if($hari=="Friday"){
                            $namahari="JUMAT";
                          }else if($hari=="Saturday"){
                            $namahari="SABTU";
                          }
                          $_sql="Select dokter.nm_dokter,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai 
                              from jadwal inner join dokter inner join poliklinik on dokter.kd_dokter=jadwal.kd_dokter 
                              and jadwal.kd_poli=poliklinik.kd_poli where jadwal.hari_kerja='$namahari'" ;  
                          $hasil=bukaquery($_sql);

                          while ($data = mysqli_fetch_array ($hasil)){
                            echo "<tr>
                                <td><b>".$data['nm_dokter']."</b></td>
                                <td><b>".$data['nm_poli']."</b></td>
                                <td align='center'><b>".$data['jam_mulai']."</b></td>
                                <td align='center'><b>".$data['jam_selesai']."</b></td>
                                </tr> ";
                           }
                        ?>
                    </tbody>
                </table>
            </div>
            <div class="col s5">
                <h5 style="font-size:45px;" class="center"><i class="material-icons md-36">hotel</i> Daftar Ruang Rawat Inap</h5>
                <table class="default">
                    <thead>
                      <tr>
                        <td align='left'><b>Kelas Kamar</b></td>
                        <td align='center'><b>Jumlah Bed</b></td>
                        <td align='center'><b>Bed Terisi</b></td>
                        <td align='center'><b>Bed Kosong</b></td>
                      </tr>
                    </thead>
                    <tbody>
                      <?php  
                          $_sql="Select kelas from kamar where statusdata='1' group by kelas" ;  
                          $hasil=bukaquery($_sql);

                          while ($data = mysqli_fetch_array ($hasil)){
                            echo "<tr>
                                <td align='left'><b>".$data['kelas']."</b></td>
                                <td align='center'><b>
                                     ";
                                       $data2=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kelas='".$data['kelas']."' and kd_bangsal  NOT LIKE '%ICU%' and kd_bangsal  NOT LIKE '%HCU%' and kd_bangsal  NOT LIKE '%PRN%'and kd_bangsal  NOT LIKE '%ISO%'"));
                                       echo $data2[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                     ";
                                     $data2=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kelas='".$data['kelas']."' and status='ISI' and kd_bangsal and kd_bangsal  NOT LIKE '%ICU%' and kd_bangsal  NOT LIKE '%HCU%' and kd_bangsal  NOT LIKE '%PRN%'and kd_bangsal  NOT LIKE '%ISO%'"));
                                     echo $data2[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                      ";
                                     $data2=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kelas='".$data['kelas']."' and status='KOSONG' and kd_bangsal  NOT LIKE '%ICU%' and kd_bangsal  NOT LIKE '%HCU%' and kd_bangsal  NOT LIKE '%PRN%'and kd_bangsal  NOT LIKE '%ISO%'"));
                                     echo $data2[0];
                                echo "
                                </b></td>
                              </tr>							  
							  ";
                          }
                        ?>
						
<!-- PENAMBAHAN ROW ICU-->
<!--
						<?php  
                          $_sqlA="Select kelas from kamar where statusdata='1' and kd_bangsal LIKE '%ICU%' group by kelas" ;  
                          $hasilA=bukaquery($_sqlA);

                          while ($dataA = mysqli_fetch_array ($hasilA)){
                            echo "<tr>
                                <td align='left'><b>ICU</b></td>
                                <td align='center'><b>
                                     ";
                                       $data3=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kd_bangsal LIKE '%ICU%'"));
                                       echo $data3[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                     ";
                                     $data3=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1'  and status='ISI' and kd_bangsal = 'ICU'"));
                                     echo $data3[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                      ";
                                     $data3=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1'  and status='KOSONG' and kd_bangsal LIKE '%ICU%'"));
                                     echo $data3[0];
                                echo "
                                </b></td>
                              </tr>							  
							  ";
                          }
                        ?>
-->
<!-- PENAMBAHAN ROW HCU-->
						
						<?php  
                          $_sqlB="Select kelas from kamar where statusdata='1' and kd_bangsal LIKE '%HCU%' group by kelas" ;  
                          $hasilB=bukaquery($_sqlB);

                          while ($dataB = mysqli_fetch_array ($hasilB)){
                            echo "<tr>
                                <td align='left'><b>HCU</b></td>
                                <td align='center'><b>
                                     ";
                                       $data4=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kelas='Kelas 2' and kd_bangsal LIKE '%HCU%'"));
                                       echo $data4[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                     ";
                                     $data4=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kelas='Kelas 2' and status='ISI' and kd_bangsal like '%HCU%'"));
                                     echo $data4[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                      ";
                                     $data4=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1' and kelas='Kelas 2' and status='KOSONG' and kd_bangsal LIKE '%HCU%'"));
                                     echo $data4[0];
                                echo "
                                </b></td>
                              </tr>							  
							  ";
                          }
                        ?>
						
<!-- PENAMBAHAN ROW PERINATOLOGI-->						
						<?php  
                          $_sqlC="Select kelas from kamar where statusdata='1' and kd_bangsal LIKE '%PRN%' group by kelas" ;  
                          $hasilC=bukaquery($_sqlC);

                          while ($dataC = mysqli_fetch_array ($hasilC)){
                            echo "<tr>
                                <td align='left'><b>PERINATOLOGI</b></td>
                                <td align='center'><b>
                                     ";
                                       $data5=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1'  and kd_bangsal LIKE '%PRN%'"));
                                       echo $data5[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                     ";
                                     $data5=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1'  and status='ISI' and kd_bangsal like '%PRN%'"));
                                     echo $data5[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                      ";
                                     $data5=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1'  and status='KOSONG' and kd_bangsal LIKE '%PRN%'"));
                                     echo $data5[0];
                                echo "
                                </b></td>
                              </tr>							  
							  ";
                          }
                        ?>
						
<!-- PENAMBAHAN ROW ISOLASI-->						
						<?php  
                          $_sqlD="Select kelas from kamar where statusdata='1' and kd_bangsal LIKE '%ISO%' group by kelas" ;  
                          $hasilD=bukaquery($_sqlD);

                          while ($dataD = mysqli_fetch_array ($hasilD)){
                            echo "<tr>
                                <td align='left'><b>ISOLASI</b></td>
                                <td align='center'><b>
                                     ";
                                       $data6=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1'  and kd_bangsal LIKE '%ISO%'"));
                                       echo $data6[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                     ";
                                     $data6=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1'  and status='ISI' and kd_bangsal like '%ISO%'"));
                                     echo $data6[0];
                                echo "
                                </b></td>
                                <td align='center'><b>
                                      ";
                                     $data6=mysqli_fetch_array(bukaquery("select count(kelas) from kamar where statusdata='1'  and status='KOSONG' and kd_bangsal LIKE '%ISO%'"));
                                     echo $data6[0];
                                echo "
                                </b></td>
                              </tr>							  
							  ";
                          }
                        ?>
						
                    </tbody>
                </table>
            </div>
        </div>
