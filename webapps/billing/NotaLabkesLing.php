<?php
   include '../conf/conf.php';
   include '../phpqrcode/qrlib.php'; 
?>
<html>
    <head>
        <link href="css/default.css" rel="stylesheet" type="text/css" />
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
            $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
            $PNG_WEB_DIR    = 'temp/';
            if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
            
            $setting =  mysqli_fetch_array(bukaquery("select setting.nama_instansi,setting.alamat_instansi,setting.kabupaten,setting.propinsi,setting.kontak,setting.email,setting.logo from setting"));
            echo "<table width='".getOne("select set_nota.notalabkesling from set_nota")."' bgcolor='#ffffff' align='left' border='0' padding='0' class='tbl_form' cellspacing='0' cellpadding='0'>
                    <tr class='isi14' padding='0'>
                        <td padding='0'>
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
                                                 <br>RINCIAN BIAYA UJI LABORATORIUM
                                            </font> 
                                        </center>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr class='isi14' padding='0'>
                        <td padding='0'>&nbsp;<br></td>
                    </tr>"; 
            
            $_sql  = "select temporary_bayar_labkesling.temp1,temporary_bayar_labkesling.temp2,temporary_bayar_labkesling.temp3,temporary_bayar_labkesling.temp4,temporary_bayar_labkesling.temp5,temporary_bayar_labkesling.temp6,temporary_bayar_labkesling.temp7,temporary_bayar_labkesling.temp8,temporary_bayar_labkesling.temp9,temporary_bayar_labkesling.temp10,temporary_bayar_labkesling.temp11,temporary_bayar_labkesling.temp12,temporary_bayar_labkesling.temp13,temporary_bayar_labkesling.temp14 from temporary_bayar_labkesling where temporary_bayar_labkesling.temp17='$petugas' and temporary_bayar_labkesling.temp16='Header' order by temporary_bayar_labkesling.no asc";   
            $hasil = bukaquery($_sql);
            if(mysqli_num_rows($hasil)!=0) {
                echo "<tr class='isi14' padding='0'>
                        <td padding='0'>
                            <table width='100%' bgcolor='#ffffff' align='left' border='0' class='tbl_form' cellspacing='0' cellpadding='0'>";
                while($baris = mysqli_fetch_array($hasil)) { 
                        echo "<tr class='isi14'>
                                 <td width='30%'><font color='000000' size='2'  face='Tahoma'>$baris[0]</font></td>
                                 <td width='70%'><font color='000000' size='2'  face='Tahoma'>$baris[1]</font></td>
                              </tr>";
                } 
                echo "      </table>
                        </td>
                      </tr>
                      <tr class='isi14' padding='0'>
                        <td padding='0'>&nbsp;<br></td>
                      </tr>";
            }
            
            $_sql  = "select temporary_bayar_labkesling.temp1,temporary_bayar_labkesling.temp2,temporary_bayar_labkesling.temp3,temporary_bayar_labkesling.temp4,temporary_bayar_labkesling.temp5,temporary_bayar_labkesling.temp6,temporary_bayar_labkesling.temp7,temporary_bayar_labkesling.temp8,temporary_bayar_labkesling.temp9,temporary_bayar_labkesling.temp10,temporary_bayar_labkesling.temp11,temporary_bayar_labkesling.temp12,temporary_bayar_labkesling.temp13,temporary_bayar_labkesling.temp14,temporary_bayar_labkesling.temp16 from temporary_bayar_labkesling where temporary_bayar_labkesling.temp17='$petugas' and temporary_bayar_labkesling.temp16='Tagihan' order by temporary_bayar_labkesling.no asc";   
            $hasil = bukaquery($_sql);
            if(mysqli_num_rows($hasil)!=0) {
                echo "<tr class='isi14' padding='0'>
                        <td padding='0'>
                        <table width='100%' cellpadding='0' cellspacing='0'>
                        <tr class='isi15'>
                           <td width='5%' align=center><font color='000000' size='2'  face='Tahoma'>No</font></td>
                           <td width='50%' align=center><font color='000000' size='2'  face='Tahoma'>Parameter</font></td>
                           <td width='10%' align=center><font color='000000' size='2'  face='Tahoma'>Jumlah</font></td>
                           <td width='15%' align=center><font color='000000' size='2'  face='Tahoma'>Biaya(Rp)</font></td>
                           <td width='20%' align=center><font color='000000' size='2'  face='Tahoma'>Total Biaya(Rp)</font></td>
                        </tr>";
                while($baris = mysqli_fetch_array($hasil)) { 
                    echo "<tr class='isi17'>
                             <td align=center><font color='000000' size='2'  face='Tahoma'>$baris[0]</font></td>
                             <td><font color='000000' size='2'  face='Tahoma'>$baris[1]</font></td>
                             <td align=center><font color='000000' size='2'  face='Tahoma'>$baris[2]</font></td>
                             <td align=right><font color='000000' size='2'  face='Tahoma'>$baris[3]</font></td>
                             <td align=right><font color='000000' size='2'  face='Tahoma'>$baris[4]</font></td>
                          </tr>";
                        
                } 
                echo "  </table>
                        </td>
                        </tr>";
            }
            
            $_sql  = "select temporary_bayar_labkesling.temp1,temporary_bayar_labkesling.temp2,temporary_bayar_labkesling.temp3,temporary_bayar_labkesling.temp4,temporary_bayar_labkesling.temp5,temporary_bayar_labkesling.temp6,temporary_bayar_labkesling.temp7,temporary_bayar_labkesling.temp8,temporary_bayar_labkesling.temp9,temporary_bayar_labkesling.temp10,temporary_bayar_labkesling.temp11,temporary_bayar_labkesling.temp12,temporary_bayar_labkesling.temp13,temporary_bayar_labkesling.temp14,temporary_bayar_labkesling.temp16 from temporary_bayar_labkesling where temporary_bayar_labkesling.temp17='$petugas' and temporary_bayar_labkesling.temp16='Total Tagihan' order by temporary_bayar_labkesling.no asc";   
            $hasil = bukaquery($_sql);
            if(mysqli_num_rows($hasil)!=0) {
                echo "<tr class='isi14' padding='0'>
                        <td padding='0'>
                        <table width='100%' cellpadding='0' cellspacing='0'>";
                while($baris = mysqli_fetch_array($hasil)) { 
                    echo "<tr class='isi14'>
                             <td align='left' width='79%'><font color='000000' size='2'  face='Tahoma'>$baris[0]</font></td>
                             <td align='right' width='1%'><font color='000000' size='2'  face='Tahoma'>:</font></td>
                             <td align='right' width='20%'><font color='000000' size='2'  face='Tahoma'>$baris[4]</font></td>
                          </tr>";
                } 
                echo "  </table>
                        </td>
                      </tr>";
            }
            
            $_sql  = "select temporary_bayar_labkesling.temp1,temporary_bayar_labkesling.temp2,temporary_bayar_labkesling.temp3,temporary_bayar_labkesling.temp4,temporary_bayar_labkesling.temp5,temporary_bayar_labkesling.temp6,temporary_bayar_labkesling.temp7,temporary_bayar_labkesling.temp8,temporary_bayar_labkesling.temp9,temporary_bayar_labkesling.temp10,temporary_bayar_labkesling.temp11,temporary_bayar_labkesling.temp12,temporary_bayar_labkesling.temp13,temporary_bayar_labkesling.temp14,temporary_bayar_labkesling.temp16 from temporary_bayar_labkesling where temporary_bayar_labkesling.temp17='$petugas' and temporary_bayar_labkesling.temp16='Dibayar' order by temporary_bayar_labkesling.no asc";   
            $hasil = bukaquery($_sql);
            if(mysqli_num_rows($hasil)!=0) {
                echo "<tr class='isi14' padding='0'>
                        <td padding='0'>&nbsp;<br></td>
                      </tr>";
                while($baris = mysqli_fetch_array($hasil)) { 
                    echo " <tr class='isi14' padding='0'>
                                <td padding='0'>
                                    <table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
                                        <tr class='isi14' padding='0'>
                                            <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                            <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>".getOne("select setting.kabupaten from setting").", ".$tanggal."</font></td>              
                                        </tr>  
                                        <tr class='isi14' padding='0'>
                                            <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>Petugas Penerima</td> 
                                            <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>Dibayar Lunas Oleh</font></td>              
                                        </tr>  
                                        <tr class='isi14' padding='0'>
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
                                            <td padding='0' width='50%' align='center' valign='bottom'><font color='000000' size='1'  face='Tahoma'>( $baris[1] )</font></td>              
                                        </tr>   
                                  </table>
                                </td>
                            </tr>";
                } 
            }
                
            echo "</table>";
        }else{
            exit(header("Location:../index.php"));
        }
    ?>  
    </body>
</html>
