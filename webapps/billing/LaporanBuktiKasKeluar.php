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
                $nobukti                = validTeks4($_GET['kode'],20); 
                $tanggal                = validTeks4($_GET['tanggal'],16);
                $akunbayar              = validTeks4($_GET['akunbayar'],50);
                $petugas                = validTeks4($_GET['petugas'],50);
                $dibayarkankepada       = validTeks4($_GET['dibayarkankepada'],50);
                $keterangan             = validTeks4($_GET['keterangan'],100);
                $notagihan              = validTeks4($_GET['notagihan'],100);
                $nominal                = validTeks4($_GET['nominal'],20);
                $setting                = mysqli_fetch_array(bukaquery("select setting.nama_instansi,setting.alamat_instansi,setting.kabupaten,setting.propinsi,setting.kontak,setting.email,setting.logo from setting"));
                $PNG_TEMP_DIR           = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
                $PNG_WEB_DIR            = 'temp/';
                if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
                $filename               = $PNG_TEMP_DIR.str_replace("/","",str_replace("_"," ",$petugas)).'.png';
                $errorCorrectionLevel   = 'L';
                $matrixPointSize        = 4;
                QRcode::png("Dikeluarkan di ".$setting["nama_instansi"].", Kabupaten/Kota ".$setting["kabupaten"]."\nDitandatangani secara elektronik oleh ".str_replace("/","",str_replace("_"," ",$petugas))."\n".$tanggal, $filename, $errorCorrectionLevel, $matrixPointSize, 2);
                echo "<table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' class='tbl_form' cellspacing='0' cellpadding='0'>
                        <tr>
                            <td width='100%' align='center'>
                                <table width='100%' bgcolor='#ffffff' padding='0' align='left' border='0' class='tbl_form'>
                                    <tr>
                                        <td width=5%>
                                            <img width='60' height='60' src='data:image/jpeg;base64,". base64_encode($setting['logo']). "'/>
                                        </td>
                                        <td width=90%>
                                            <center>
                                                <font color='000000' size='3'  face='Tahoma'>".$setting["nama_instansi"]."</font><br>
                                                <font color='000000' size='1'  face='Tahoma'>
                                                   ".$setting["alamat_instansi"].", ".$setting["kabupaten"].", ".$setting["propinsi"]."<br/>
                                                   ".$setting["kontak"].", E-mail : ".$setting["email"]."
                                                </font> 
                                                <br><br>
                                                <font color='000000' size='3'  face='Tahoma'>BUKTI KAS KELUAR</font><br><br>
                                            </center>
                                        </td>
                                        <td width=5%>
                                        </td>
                                    </tr>
                                </table><hr>
                            </td>
                        </tr>
                        <tr>
                            <td width='100%' align='center'>
                                <table width=100%>
                                    <tr>								
                                        <td width='15%'>
                                            <font color='000000' size='2' face='Tahoma'>No.Voucher/Bukti</font>
                                        </td>
                                        <td width='35%'>
                                            <font color='000000' size='2' face='Tahoma'>: $nobukti</font>
                                        </td>           							
                                        <td width='15%'>
                                            <font color='000000' size='2' face='Tahoma'>Tanggal</font>
                                        </td>
                                        <td width='35%'>
                                            <font color='000000' size='2' face='Tahoma'>: $tanggal</font>
                                        </td>                                                                
                                    </tr> 
                                    <tr>
                                        <td width='15%'>
                                            <font color='000000' size='2' face='Tahoma'>Akun Bayar</font>
                                        </td>
                                        <td width='35%'>
                                            <font color='000000' size='2' face='Tahoma'>: ".str_replace("_"," ",$akunbayar)."</font>
                                        </td>
                                        <td width='15%'>
                                            <font color='000000' size='2' face='Tahoma'>Dibayarkan Kepada</font>
                                        </td>
                                        <td width='35%'>
                                            <font color='000000' size='2' face='Tahoma'>: ".str_replace("_"," ",$dibayarkankepada)."</font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='15%'>
                                            <font color='000000' size='2' face='Tahoma'>No.Invoice/Ref</font>
                                        </td>
                                        <td width='85%' colspan='3'>
                                            <font color='000000' size='2' face='Tahoma'>: ".str_replace("_"," ",$notagihan)."</font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan='4'>
                                            <hr>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='15%'>
                                            <font color='000000' size='2' face='Tahoma'>JUMLAH</font>
                                        </td>
                                        <td width='85%' colspan='3'>
                                            <font color='000000' size='2' face='Tahoma'>: <b>Rp.  "; $num = $nominal + 0; echo number_format($num,0,',','.'); echo",-<b></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='15%'>
                                            <font color='000000' size='2' face='Tahoma'>TERBILANG</font>
                                        </td>
                                        <td width='85%' colspan='3'>
                                            <font color='000000' size='2' face='Tahoma'>: ".Terbilang($nominal)." rupiah</font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan='4'>
                                            <hr>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='15%'>
                                            <font color='000000' size='2' face='Tahoma'>KETERANGAN</font>
                                        </td>
                                        <td width='85%' colspan='3'>
                                            <font color='000000' size='2' face='Tahoma'>: ".str_replace("_"," ",$keterangan)."</font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan='4'>
                                            <hr>
                                            <center><font color='000000' size='2' face='Tahoma'>LEMBAR OTORISASI PEMBAYARAN</font></center>
                                            <table width='100%' cellpadding='0' cellspacing='0' class='tbl_form'>
                                                <tr class=isi15>							
                                                    <td width='20%' align='center'>
                                                        <font color='000000' size='2' face='Tahoma'>Dibuat Oleh</font>
                                                    </td>
                                                    <td width='20%' align='center'>
                                                        <font color='000000' size='2' face='Tahoma'>Diperiksa Oleh</font>
                                                    </td>           							
                                                    <td width='40%' colspan='2' align='center'>
                                                        <font color='000000' size='2' face='Tahoma'><center>Disetujui Oleh</center></font>
                                                    </td>
                                                    <td width='20%' align='center'>
                                                        <font color='000000' size='2' face='Tahoma'>Diterima Oleh</font>
                                                    </td>                                                                
                                                </tr> 
                                                <tr class=isi15>								
                                                    <td width='20%' align='center'>
                                                        <img width='60' height='60' src='".$PNG_WEB_DIR.basename($filename)."'/><br>
                                                        <font color='000000' size='2' face='Tahoma'>".str_replace("_"," ",$petugas)."</font>
                                                    </td>
                                                    <td width='20%'>
                                                        &nbsp;
                                                    </td>           							
                                                    <td width='20%'>
                                                        &nbsp;
                                                    </td>
                                                    <td width='20%'>
                                                        &nbsp;
                                                    </td>   
                                                    <td width='20%'>
                                                        &nbsp;
                                                    </td>  
                                                </tr> 
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                      </table>";
            }else {
                exit(header("Location:../index.php"));
            }
        ?>

    </body>
</html>
