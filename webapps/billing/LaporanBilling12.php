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
                $norawat        = validTeks4(str_replace("_"," ",$_GET['norawat']),20); 
                $bayar          = validangka(str_replace("_"," ",$_GET['bayar']));
                $pasien         = validTeks4(str_replace("_"," ",$_GET['pasien']),50);
                $keterangan     = validTeks4(str_replace("_"," ",$_GET['keterangan']),100);
                $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
                $PNG_WEB_DIR    = 'temp/';
                if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);

                $setting=  mysqli_fetch_array(bukaquery("select setting.nama_instansi,setting.alamat_instansi,setting.kabupaten,setting.propinsi,setting.kontak,setting.email,setting.logo from setting"));
                //cari biling
                echo "   
                <table width='100%' bgcolor='#ffffff' align='left' border='1' padding='0' class='tbl_form' cellspacing='0' cellpadding='0'>
                        <tr padding='0' width='100%' > 
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
                                                        </font> 
                                                </center>
                                            </td>
                                            <td  width='20%'><font color='000000' size='2'  face='Tahoma' align='right'>&nbsp;</font></td>
                                    </tr>
                                </table>
                            </td>
                         </tr>
                        <tr class='isi12' padding='0' width='100%' height='350px' >                    
                        <td padding='0'  width='90%' valign='top'>
                            <table width='100%' cellspacing='0' cellpadding='0'>
                                <tr>
                                   <td width='25%' colspan='3' align='center'><font color='333333' size='3'  face='Tahoma'>KWITANSI BRI VIRTUAL ACCOUNT<br><br></font></td>
                                </tr>
                                <tr>
                                   <td width='25%'><font color='333333' size='3'  face='Tahoma'>No. BRIVA</font></td>
                                   <td width='1%'><font color='333333' size='3'  face='Tahoma'>:</font></td>
                                   <td width='74%'><font color='333333' size='3'  face='Tahoma'>$norawat</font></td>
                                </tr>
                                <tr>
                                   <td><font color='333333' size='3'  face='Tahoma'>Telah terima dari</font></td>
                                   <td><font color='333333' size='3'  face='Tahoma'>:</font></td>
                                   <td><font color='333333' size='3'  face='Tahoma'>Atas Nama Pasien $pasien</font></td>
                                </tr>
                                <tr valign='top'>
                                   <td><font color='333333' size='3'  face='Tahoma'>Uang Sebanyak</font></td>
                                   <td><font color='333333' size='3'  face='Tahoma'>:</font></td>
                                   <td><font color='333333' size='3'  face='Tahoma'>".Terbilang($bayar)." rupiah</font></td>
                                </tr>
                                <tr valign='top'>
                                   <td><font color='333333' size='3'  face='Tahoma'>Untuk Pembayaran</font></td>
                                   <td><font color='333333' size='3'  face='Tahoma'>:</font></td>
                                   <td><font color='333333' size='3'  face='Tahoma'>Nomor Tagihan $keterangan</font></td>
                                </tr>                           
                                <tr>
                                   <td>&nbsp;</td>
                                   <td>&nbsp;</td>
                                   <td>&nbsp;</td>
                                </tr>               
                                <tr>
                                   <td>&nbsp;</td>
                                   <td>&nbsp;</td>
                                   <td>&nbsp;</td>
                                </tr>                            
                                <tr>
                                   <td align='right'><font color='333333' size='3'  face='Tahoma'>Terbilang</font></td>
                                   <td><font color='333333' size='3'  face='Tahoma'></font></td>
                                   <td><font color='333333' size='3'  face='Tahoma'>Rp. ".formatDuit2($bayar)."</font></td>
                                </tr>  
                                <tr>
                                   <td></td>
                                   <td></td>
                                   <td align='center'><font color='333333' size='3'  face='Tahoma'>".getOne("select setting.kabupaten from setting").", ".date('d-m-Y')."</font></td>
                                </tr>                                         
                                <tr>
                                   <td align='center' valign='bottom'><font color='333333' size='3'  face='Tahoma'>(____________________)</font></td>
                                   <td>&nbsp;</td>
                                   <td align='center'><font color='333333' size='3'  face='Tahoma'>";
                                    if(getOne("select count(petugas.nama) from petugas where petugas.nip='$petugas'")>=1){
                                        $filename               = $PNG_TEMP_DIR.$petugas.'.png';
                                        $errorCorrectionLevel   = 'L';
                                        $matrixPointSize        = 4;
                                        QRcode::png("Dikeluarkan di ".$setting["nama_instansi"].", Kabupaten/Kota ".$setting["kabupaten"]."\nDitandatangani secara elektronik oleh ".getOne("select petugas.nama from petugas where petugas.nip='$petugas'")."\nID  ".getOne3("select ifnull(sha1(sidikjari.sidikjari),'".$petugas."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$petugas."'",$petugas)."\n".date('d-m-Y'), $filename, $errorCorrectionLevel, $matrixPointSize, 2);
                                        echo "<img width='50' height='50' src='".$PNG_WEB_DIR.basename($filename)."'/><br>( ".getOne("select petugas.nama from petugas where petugas.nip='$petugas'")." )";    
                                    }else{
                                        $filename               = $PNG_TEMP_DIR.$petugas.'.png';
                                        $errorCorrectionLevel   = 'L';
                                        $matrixPointSize        = 4;
                                        QRcode::png("Dikeluarkan di ".$setting["nama_instansi"].", Kabupaten/Kota ".$setting["kabupaten"]."\nDitandatangani secara elektronik oleh Admin Utama\nID ADMIN\n".date('d-m-Y'), $filename, $errorCorrectionLevel, $matrixPointSize, 2);
                                        echo "<img width='45' height='45' src='".$PNG_WEB_DIR.basename($filename)."'/><br>( Admin Utama )";
                                    }
                                    echo "</font></td>
                                </tr> 
                                <tr>
                                   <td align='center'><font color='333333' size='3'  face='Tahoma'>Pasien/Keluarga Pasien </font></td>
                                   <td>&nbsp;</td>
                                   <td align='center'><font color='333333' size='3'  face='Tahoma'>Adm. Keuangan</font></td>
                                </tr> 
                            </table>
                        </td>
                    </tr>
                </table>"; 
            }else{
                exit(header("Location:../index.php"));
            } 
        ?>  
    </body>
</html>
