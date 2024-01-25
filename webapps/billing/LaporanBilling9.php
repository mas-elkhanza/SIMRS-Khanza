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
                $petugas       = validTeks4(str_replace("_"," ",$_GET['petugas']),20); 
                $tanggal       = validTeks4(str_replace("_"," ",$_GET['tanggal']),20);
                $nonota        = str_replace(": ","",getOne("select temporary_bayar_ralan.temp2 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' and temporary_bayar_ralan.temp1='No.Nota'"));
                $norawat       = getOne("select nota_inap.no_rawat from nota_inap where nota_inap.no_nota='$nonota'");
                $kodecarabayar = getOne("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat='$norawat'");
                $carabayar     = getOne("select penjab.png_jawab from penjab where penjab.kd_pj='$kodecarabayar'");
                $_sql          = "select temporary_bayar_ralan.temp1,temporary_bayar_ralan.temp2,temporary_bayar_ralan.temp3,temporary_bayar_ralan.temp4,temporary_bayar_ralan.temp5,temporary_bayar_ralan.temp6,temporary_bayar_ralan.temp7,temporary_bayar_ralan.temp8,temporary_bayar_ralan.temp9,temporary_bayar_ralan.temp10,temporary_bayar_ralan.temp11,temporary_bayar_ralan.temp12,temporary_bayar_ralan.temp13,temporary_bayar_ralan.temp14 from temporary_bayar_ralan where temporary_bayar_ralan.temp9='$petugas' order by temporary_bayar_ralan.no asc";   
                $hasil         = bukaquery($_sql);
                $PNG_TEMP_DIR  = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
                $PNG_WEB_DIR   = 'temp/';
                if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);

                if(mysqli_num_rows($hasil)!=0) { 
                    $setting=  mysqli_fetch_array(bukaquery("select setting.nama_instansi,setting.alamat_instansi,setting.kabupaten,setting.propinsi,setting.kontak,setting.email,setting.logo from setting"));
                    echo "   
                    <table width='".getOne("select set_nota.notaralan from set_nota")."' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
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
                        <center><font color='333333' size='1'  face='Tahoma'>BILLING</font> </center>
                      </td>
                    </tr>
                    ";  $z=1;
                        while($inapdrpasien = mysqli_fetch_array($hasil)) {
                           if($z<=8){
                              echo "<tr class='isi12' padding='0'>
                                        <td padding='0' width='20%'><font color='111111' size='1'  face='Tahoma'>".str_replace("  ","&nbsp;&nbsp;",$inapdrpasien[0])."</td> 
                                        <td padding='0' width='58%' colspan='6'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[1]</td>              
                                     </tr>";  
                           }else if($z>8){			
                                echo "<tr class='isi12' padding='0'>
                                        <td padding='0' width='20%'><font color='111111' size='1'  face='Tahoma'>".str_replace("   ","&nbsp;&nbsp;",$inapdrpasien[0])."</td> 
                                        <td padding='0' width='58%'><font color='111111' size='1'  face='Tahoma'>".str_replace("  ","&nbsp;&nbsp;",$inapdrpasien[1])."</td> 
                                        <td padding='0' width='2%' colspan='2'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[2]</td>   
                                        <td padding='0' width='5%' align='right'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[4]</td>  
                                        <td padding='0' width='15%' align='right'><font color='111111' size='1'  face='Tahoma'>$inapdrpasien[6]</td>                
                                      </tr>";  

                           }
                          $z++; 

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
                } else {
                    echo "<font color='333333' size='1'  face='Times New Roman'><b>Data  Billing masih kosong !</b>";
                }
            }else{
                exit(header("Location:../index.php"));
            }
        ?>  
    </body>
</html>
