<?php
   include '../conf/conf.php';
   include '../phpqrcode/qrlib.php'; 
?>
<html>
    <head>
        <link href="style.css" rel="stylesheet" type="text/css" media="screen" />
    </head>
    <body bgcolor='#ffffff'>
        <script type="text/javascript">
            window.onload = function() { window.print(); }
        </script>
    <?php
        reportsqlinjection(); 
        $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
        $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
            $petugas        = validTeks4(str_replace("_"," ",$_GET['petugas']),20); 
            $tanggal        = validTeks4(str_replace("_"," ",$_GET['tanggal']),20); 
            $nonota         = str_replace(": ","",getOne("select temporary_bayar_ralan.temp2 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and temporary_bayar_ralan.temp1='No.Nota'"));
            $norawat        = getOne("select nota_jalan.no_rawat from nota_jalan where nota_jalan.no_nota='$nonota'");
            $kodecarabayar  = getOne("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat='$norawat'");
            $carabayar      = getOne("select penjab.png_jawab from penjab where penjab.kd_pj='$kodecarabayar'");
            $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
            $PNG_WEB_DIR    = 'temp/';
            if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
            $_sql           = "select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp4,temporary_bayar_ralan.temp5,temporary_bayar_ralan.temp6,temporary_bayar_ralan.temp7,temporary_bayar_ralan.temp8,temporary_bayar_ralan.temp9,temporary_bayar_ralan.temp10,temporary_bayar_ralan.temp11,temporary_bayar_ralan.temp12,temporary_bayar_ralan.temp13,temporary_bayar_ralan.temp14 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' order by temporary_bayar_ralan.no asc";   
            $hasil          = bukaquery($_sql);

            if(mysqli_num_rows($hasil)!=0) { 
                $setting=  mysqli_fetch_array(bukaquery("select setting.nama_instansi,setting.alamat_instansi,setting.kabupaten,setting.propinsi,setting.kontak,setting.email,setting.logo from setting"));
                echo "   
                <table width='".getOne("select set_nota.notaralan from set_nota")."' bgcolor='#ffffff' align='left' border='0' padding='0' class='tbl_form' cellspacing='0' cellpadding='0'>
                <tr class='isi12' padding='0'>
                    <td colspan='7' padding='0'>
                        <table width='100%' bgcolor='#ffffff' align='left' border='0' class='tbl_form' cellspacing='0' cellpadding='0'>
                            <tr>
                                <td  width='20%'>
                                    <img width='45' height='45' src='data:image/jpeg;base64,". base64_encode($setting['logo']). "'/>
                                </td>
                                <td>
                                    <center>
                                        <font color='000000' size='3'  face='Tahoma'>".$setting["nama_instansi"]."</font><br>
                                        <font color='000000' size='1'  face='Tahoma'>
                                            ".$setting["alamat_instansi"].", ".$setting["kabupaten"].", ".$setting["propinsi"]."<br/>
                                            ".$setting["kontak"].", E-mail : ".$setting["email"]."
                                             <br>BILLING
                                        </font> 
                                    </center>
                                </td>
                                <td  width='20%'><font color='000000' size='2'  face='Tahoma' align='right'>$carabayar</font></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                ";  $z=1;
                    while($inapdrpasien = mysqli_fetch_array($hasil)) {
                       if($z<=6){
                          echo "<tr class='isi12' padding='0'>
                                    <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>".str_replace("  ","&nbsp;&nbsp;",$inapdrpasien[0])."</td> 
                                    <td padding='0' width='40%' colspan='6'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</td>              
                                 </tr>";  
                       }
                       $z++;                                   
                    }  

                    $_sql = "select temporary_bayar_ralan.temp2 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and temporary_bayar_ralan.temp8='Dokter' group by temporary_bayar_ralan.temp2 order by temporary_bayar_ralan.no asc";   
                    $hasil=bukaquery($_sql);
                    echo "<tr class='isi12' padding='0'>
                           <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>Dokter</td> 
                           <td padding='0' width='40%' colspan='6'>:";
                           while($inapdrpasien = mysqli_fetch_array($hasil)) {
                              echo "<font color='000000' size='1'  face='Tahoma'>&nbsp;$inapdrpasien[0]</font></br>";				                    
                           }
                      echo "</td>              
                          </tr>";   

                    $hasil2=bukaquery("select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp7 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and temporary_bayar_ralan.temp8='Registrasi' order by temporary_bayar_ralan.no asc");
                    while($inapdrpasien = mysqli_fetch_array($hasil2)) {
                        echo "<tr class='isi12' padding='0'>
                           <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>Administrasi Rekam Medik</td> 
                           <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                           <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>     
                           <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                          </tr>"; 			                    
                    } 

                    $hasil3=bukaquery("select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp7,temporary_bayar_ralan.temp5 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and (temporary_bayar_ralan.temp8='Ralan Dokter' or temporary_bayar_ralan.temp8='Ralan Dokter Paramedis' or temporary_bayar_ralan.temp8='Ralan Paramedis' or temporary_bayar_ralan.temp8='Laborat' or temporary_bayar_ralan.temp8='Radiologi') order by temporary_bayar_ralan.no asc");
                    echo "<tr class='isi12' padding='0'>
                           <td padding='0' width='30%' valign='top'><font color='000000' size='1'  face='Tahoma'>Tindakan</td> 
                           <td padding='0' width='40%' colspan='6'>
                           <table border='0' width='100%' padding='0' cellspacing='0' cellpadding='0'>
                                 ";                      
                           while($inapdrpasien = mysqli_fetch_array($hasil3)) {
                               if(!empty($inapdrpasien[3])){
                                    echo "<tr class='isi12' padding='0'> 
                                             <td padding='0' width='80%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                                             <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[4]</font></td>   
                                             <td padding='0' width='19%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                                         </tr>";                            
                               }
                           }
                       echo"</table>
                            </td>               
                          </tr>"; 

                    $hasil3=bukaquery("select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp7,temporary_bayar_ralan.temp5 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and temporary_bayar_ralan.temp8='Operasi' order by temporary_bayar_ralan.no asc");
                    if(mysqli_num_rows($hasil3)!=0) { 
                        echo "<tr class='isi12' padding='0'>
                               <td padding='0' width='30%' valign='top'><font color='000000' size='1'  face='Tahoma'>Operasi / VK</td> 
                               <td padding='0' width='40%' colspan='6'>
                               <table border='0' width='100%' padding='0' cellspacing='0' cellpadding='0'>";                      
                               while($inapdrpasien = mysqli_fetch_array($hasil3)) {
                                   if(!empty($inapdrpasien[3])){
                                        echo "<tr class='isi12' padding='0'> 
                                                 <td padding='0' width='80%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                                                 <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[4]</font></td>   
                                                 <td padding='0' width='19%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                                             </tr>";                            
                                   }
                               }
                           echo"</table>
                                </td>               
                              </tr>"; 
                    }  

                    $hasil4=bukaquery("select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp7,temporary_bayar_ralan.temp8,temporary_bayar_ralan.temp5 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and (temporary_bayar_ralan.temp8='Obat' or temporary_bayar_ralan.temp8='TtlObat') group by temporary_bayar_ralan.temp2 order by temporary_bayar_ralan.no asc");
                    $inapdrpasien = mysqli_fetch_array($hasil4);
                    if(!empty($inapdrpasien[1])){
                        echo "<tr class='isi12' padding='0'>
                                <td padding='0' width='30%' valign='top'><font color='000000' size='1'  face='Tahoma'>Obat & BHP</td> 
                                <td padding='0' width='40%' colspan='6'>
                                <table border='0' width='100%' padding='0' cellspacing='0' cellpadding='0'>";
                                while($inapdrpasien = mysqli_fetch_array($hasil4)) {
                                    if(!empty($inapdrpasien[3])){
                                         echo "<tr class='isi12' padding='0'> 
                                                  <td padding='0' width='80%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                                                  <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[5]</font></td>   
                                                  <td padding='0' width='19%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                                              </tr>";                            
                                    }else if($inapdrpasien["temp8"]=="TtlObat"){
                                        echo "<tr class='isi12' padding='0'> 
                                                  <td padding='0' width='80%'><font color='000000' size='1'  face='Tahoma'></font></td>   
                                                  <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>   
                                                  <td padding='0' width='19%' align='right'><font color='000000' size='1'  face='Tahoma'><b>".$inapdrpasien["temp2"]."<b></font></td>              
                                              </tr>";                                    
                                    }
                                }
                            echo"</table>
                                 </td>               
                               </tr>"; 
                    } 

                    $hasil5=bukaquery("select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp7,temporary_bayar_ralan.temp5 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and temporary_bayar_ralan.temp8='Potongan'  order by temporary_bayar_ralan.no asc");
                    while($inapdrpasien = mysqli_fetch_array($hasil5)) {
                        echo "<tr class='isi12' padding='0'>
                           <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                           <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                           <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[4]</font></td>     
                           <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                          </tr>"; 			                    
                    } 

                    $hasil6=bukaquery("select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp7,temporary_bayar_ralan.temp5 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and temporary_bayar_ralan.temp8='Tambahan'  order by temporary_bayar_ralan.no asc");
                    while($inapdrpasien = mysqli_fetch_array($hasil6)) {
                        echo "<tr class='isi12' padding='0'>
                                <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                                <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                                <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[4]</font></td>     
                                <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                              </tr>"; 			                    
                    } 

                    $hasil7=bukaquery("select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp7,temporary_bayar_ralan.temp5 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and temporary_bayar_ralan.temp8='-' and temporary_bayar_ralan.temp7<>'' group by temporary_bayar_ralan.temp2 order by temporary_bayar_ralan.no asc");
                    while($inapdrpasien = mysqli_fetch_array($hasil7)) {
                        echo "<tr class='isi12' padding='0'>
                                <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                                <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                                <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>     
                                <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                              </tr>"; 			                    
                    } 

                    $hasil7=bukaquery("select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp7 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and temporary_bayar_ralan.temp8='Tagihan' and temporary_bayar_ralan.temp7<>'' order by temporary_bayar_ralan.no asc");
                    while($inapdrpasien = mysqli_fetch_array($hasil7)) {
                        if($inapdrpasien["temp1"]=="TOTAL BAYAR"){
                            echo "<tr class='isi12' padding='0'>
                                    <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                                    <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'></font></td>   
                                    <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>     
                                    <td padding='0' width='14%' align='right'><font color='000000' size='2'  face='Tahoma'><b>$inapdrpasien[3]</b></font></td>              
                                 </tr>"; 
                        }else{
                            echo "<tr class='isi12' padding='0'>
                                    <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                                    <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'></font></td>   
                                    <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>     
                                    <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'><b>$inapdrpasien[3]</b></font></td>              
                                 </tr>";                         
                        }                        
                    } 

                echo "
                            <tr class='isi12' padding='0'>
                                <td colspan='7' padding='0'>
                                    <table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
                                        <tr class='isi12' padding='0'>
                                         <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                         <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>".getOne("select setting.kabupaten from setting").", ".$tanggal."</font></td>              
                                        </tr>  
                                        <tr class='isi12' padding='0'>
                                         <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>Petugas</td> 
                                         <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>Penanggung Jawab Pasien</font></td>              
                                        </tr>  
                                        <tr class='isi12' padding='0'>
                                         <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>";
                                            if(getOne("select count(petugas.nama) from petugas where petugas.nip='$petugas'")>=1){
                                                $filename               = $PNG_TEMP_DIR.$petugas.'.png';
                                                $errorCorrectionLevel   = 'L';
                                                $matrixPointSize        = 4;
                                                QRcode::png("Dikeluarkan di ".$setting["nama_instansi"].", Kabupaten/Kota ".$setting["kabupaten"]."\nDitandatangani secara elektronik oleh ".getOne("select petugas.nama from petugas where petugas.nip='$petugas'")."\nID  ".getOne3("select ifnull(sha1(sidikjari.sidikjari),'".$petugas."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$petugas."'",$petugas)."\n".$tanggal, $filename, $errorCorrectionLevel, $matrixPointSize, 2);
                                                echo "<img width='50' height='50' src='".$PNG_WEB_DIR.basename($filename)."'/><br>( ".getOne("select petugas.nama from petugas where petugas.nip='$petugas'")." )";    
                                            }else{
                                                $filename               = $PNG_TEMP_DIR.$petugas.'.png';
                                                $errorCorrectionLevel   = 'L';
                                                $matrixPointSize        = 4;
                                                QRcode::png("Dikeluarkan di ".$setting["nama_instansi"].", Kabupaten/Kota ".$setting["kabupaten"]."\nDitandatangani secara elektronik oleh Admin Utama\nID ADMIN\n".$tanggal, $filename, $errorCorrectionLevel, $matrixPointSize, 2);
                                                echo "<img width='45' height='45' src='".$PNG_WEB_DIR.basename($filename)."'/><br>( Admin Utama )";
                                            }
                                            echo "</td>     
                                         <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>(.............)</font></td>              
                                        </tr>   
                                  </table>
                                </td>
                            </tr>
                      </table>                                
                "; 
            } else {echo "<font color='000000' size='1'  face='Times New Roman'><b>Data  Billing masih kosong !</b>";}
        }else{
            exit(header("Location:../index.php"));
        }
    ?>  
    </body>
</html>
