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
        $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
        $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
        if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
            $petugas        = validTeks4(str_replace("_"," ",$_GET['petugas']),20); 
            $tanggal        = validTeks4(str_replace("_"," ",$_GET['tanggal']),20);
            $nonota         = str_replace(": ","",getOne("select temp2 from temporary_bayar_ranap where temp1='No.Nota'"));
            $norawat        = getOne("select no_rawat from nota_inap where no_nota='$nonota'");
            $kodecarabayar  = getOne("select kd_pj from reg_periksa where no_rawat='$norawat'");
            $carabayar      = getOne("select png_jawab from penjab where kd_pj='$kodecarabayar'");
            $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
            $PNG_WEB_DIR    = 'temp/';
            if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
            $_sql           = "select temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_bayar_ranap order by no asc";   
            $hasil          = bukaquery($_sql);

            if(mysqli_num_rows($hasil)!=0) { 
                $setting=  mysqli_fetch_array(bukaquery("select setting.nama_instansi,setting.alamat_instansi,setting.kabupaten,setting.propinsi,setting.kontak,setting.email,setting.logo from setting"));
                echo "   
                <table width='".getOne("select nota1ranap from set_nota")."' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
                <tr class='isi12' padding='0'>
                <td colspan='7' padding='0'>
                       <table width='100%' bgcolor='#ffffff' align='left' border='0' cellspacing='0' cellpadding='0'>
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
                                        </font> 
                                    </center>
                                </td>
                                <td  width='20%'><font color='000000' size='2'  face='Tahoma'>$carabayar</font></td>
                            </tr>
                      </table>
                </td>
                </tr>
                <tr>
                  <td colspan='7' padding='0'>
                   <hr/>
                    <center><font color='333333' size='1'  face='Tahoma'>BILLING</font> </center>
                  </td>
                </tr>
                ";  $z=1;
                    while($inapdrpasien = mysqli_fetch_array($hasil)) {
                       if($z<=6){
                          echo "<tr class='isi12' padding='0'>
                                    <td padding='0' width='18%'><font color='111111' size='1'  face='Tahoma'>".str_replace("  ","&nbsp;&nbsp;",$inapdrpasien[0])."</td> 
                                    <td padding='0' width='40%' colspan='6'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[1]</td>              
                                 </tr>";  
                       }else if($z>6){
                                             if(empty($inapdrpasien[6])&&empty($inapdrpasien[0])){
                                                     echo "<tr class='isi12' padding='0'>
                                    <td padding='0' width='18%'><font color='111111' size='1'  face='Tahoma'>".str_replace("   ","&nbsp;&nbsp;",$inapdrpasien[0])."</td> 
                                    <td padding='0' width='40%' colspan='6' align='right'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[1]</td>                
                                 </tr>";  
                                             }else{
                                                     echo "<tr class='isi12' padding='0'>
                                    <td padding='0' width='18%'><font color='111111' size='1'  face='Tahoma'>".str_replace("   ","&nbsp;&nbsp;",$inapdrpasien[0])."</td> 
                                    <td padding='0' width='40%'><font color='111111' size='1'  face='Tahoma'>".str_replace("  ","&nbsp;&nbsp;",$inapdrpasien[1])."</td> 
                                    <td padding='0' width='2%'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[2]</td>  
                                    <td padding='0' width='10%' align='right'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[3]</td>  
                                    <td padding='0' width='5%' align='right'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[4]</td>  
                                    <td padding='0' width='10%' align='right'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[5]</td>   
                                    <td padding='0' width='15%' align='right'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[6]</td>                
                                  </tr>";  
                                             }                     
                       }
                      $z++; 

                    }            

                echo "  <tr class='isi12' padding='0'>
                                <td colspan='7' padding='0'>
                                    <table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
                                        <tr class='isi12' padding='0'>
                                         <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                         <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                         <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>&nbsp;</font></td>              
                                        </tr>
                                        <tr class='isi12' padding='0'>
                                         <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>Mengetahui,<br>a/n Direktur</td> 
                                         <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>     
                                         <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>".getOne("select setting.kabupaten from setting").", ".$tanggal."</font></td>              
                                        </tr>  
                                        <tr class='isi12' padding='0'>
                                         <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>Kabid Umum & Keuangan</td> 
                                         <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                         <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>Kasir</font></td>              
                                        </tr>  
                                        <tr class='isi12' padding='0'>
                                         <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>( ............................. )</td>     
                                         <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                         <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>";
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
                                            echo "</font></td>              
                                        </tr>   
                                  </table>
                                </td>
                            </tr>
                            <tr class='isi12' padding='0'>
                                <td colspan='7' padding='0'>&nbsp</td>
                            </tr>
                            <tr class='isi12' padding='0'>
                                <td colspan='7' padding='0'><font color='000000' size='1'  face='Tahoma'>NB : Mohon maaf apabila ada tagihan yang belum tertagihkan dalam perincian ini akan ditagihkan kemudian, dan apabila berlebih akan dikembalikan.</font></td>
                            </tr>
                    </table>"; 
            } else {echo "<font color='333333' size='1'  face='Times New Roman'><b>Data  Billing masih kosong !</b>";}
        }else{
            exit(header("Location:../index.php"));
        }  
    ?>  

    </body>
</html>
