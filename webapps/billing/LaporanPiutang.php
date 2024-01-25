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
            $petugas      = validTeks4(str_replace("_"," ",$_GET['petugas']),20); 
            $tanggal      = validTeks4(str_replace("_"," ",$_GET['tanggal']),20); 
            $nonota       = str_replace(": ","",getOne("select temporary.temp2 from temporary where temporary.temp1='No.Nota'"));
            $norawat      = getOne("select nota_jalan.no_rawat from nota_jalan where nota_jalan.no_nota='$nonota'");
            $kodecarabayar = getOne("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat='$norawat'");
            $carabayar    = getOne("select penjab.png_jawab from penjab where penjab.kd_pj='$kodecarabayar'");
            $alamatip     = str_replace("_"," ", validTeks($_GET['alamatip'])); 
            $PNG_TEMP_DIR = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
            $PNG_WEB_DIR  = 'temp/';
            if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR); 

            $_sql = "SELECT temporary.temp1,temporary.temp2,temporary.temp3,temporary.temp4,temporary.temp5,temporary.temp6,temporary.temp7,temporary.temp8,temporary.temp9,temporary.temp10,temporary.temp11,temporary.temp12,temporary.temp13,temporary.temp14 from temporary where temporary.temp37='$alamatip' order by temporary.no";  
            $hasil=bukaquery($_sql);

            if(mysqli_num_rows($hasil)!=0) { 
                $setting=  mysqli_fetch_array(bukaquery("select setting.nama_instansi,setting.alamat_instansi,setting.kabupaten,setting.propinsi,setting.kontak,setting.email,setting.logo from setting"));
                echo "   
                <table width='".getOne("select set_nota.notaralan from set_nota")."' bgcolor='#ffffff' align='left' border='0' padding='0' class='tbl_form' cellspacing='0' cellpadding='0'>
                <tr class='isi12' padding='0'>
                    <td colspan='2' padding='0'>
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
                </tr>";  
                $z=1;
                while($inapdrpasien = mysqli_fetch_array($hasil)) {
                    echo "<tr class='isi12' padding='0'>
                              <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>".str_replace("  ","&nbsp;&nbsp;",$inapdrpasien[0])."</td> 
                              <td padding='0' width='70%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</td>              
                          </tr>";  
                    $z++;                                   
                }  
                echo "
                    <tr class='isi12' padding='0'>
                        <td colspan='2' padding='0'>
                            <table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
                                <tr class='isi12' padding='0'>
                                 <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                 <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>".getOne("select setting.kabupaten from setting").", ".$tanggal."</font></td>              
                                </tr>  
                                <tr class='isi12' padding='0'>
                                 <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>Petugas</td> 
                                 <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>Penanggung Jawab</font></td>              
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
                </table> "; 
            } else {echo "<font color='000000' size='1'  face='Times New Roman'><b>Data  Billing masih kosong !</b>";}
        }else {
            exit(header("Location:../index.php"));
        } 
    ?>  

    </body>
</html>
