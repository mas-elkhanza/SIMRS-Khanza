<?php
 include_once '../conf/conf.php';
?>
<html>
    <head>
        <link href="../css/style.css" rel="stylesheet" type="text/css" media="screen" />
    </head>
    <body bgcolor='#ffffff'>
    <?php
        $nobooking = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
        if(isset($nobooking)){
            $nobooking = json_decode(encrypt_decrypt($nobooking,"d"),true); 
            $nobooking = cleankar($nobooking["nobooking"]);
            $querybooking=bukaquery("select booking_periksa.no_booking,DATE_FORMAT(booking_periksa.tanggal,'%d-%m-%Y')as tanggal,booking_periksa.nama,booking_periksa.alamat,booking_periksa.no_telp,booking_periksa.email,poliklinik.nm_poli,booking_periksa.tambahan_pesan,DATE_FORMAT(booking_periksa.tanggal_booking,'%d-%m-%Y %H:%i:%s')as tanggal_booking from booking_periksa inner join poliklinik on booking_periksa.kd_poli=poliklinik.kd_poli where booking_periksa.no_booking='$nobooking'");
            if(mysqli_num_rows($querybooking)!=0) { 
                $setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
                echo "<table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' class='tbl_form' cellspacing='0' cellpadding='0'>
                        <tr class='isi12' padding='0'>
                            <td width='95%' align='center'>
                               <table width='100%' bgcolor='#ffffff' align='left' border='0' class='tbl_form' cellspacing='0' cellpadding='0'>
                                    <tr>
                                        <td width='20%'>
                                            <img width='45' height='45' src='data:image/jpeg;base64,". base64_encode($setting['logo']). "'/>
                                        </td>
                                        <td width='60%'>
                                            <center>
                                                <font size='3'  face='Tahoma'>".$setting["nama_instansi"]."</font><br>
                                                <font size='1'  face='Tahoma'>
                                                    ".$setting["alamat_instansi"].", ".$setting["kabupaten"].", ".$setting["propinsi"]."<br/>
                                                    ".$setting["kontak"].", E-mail : ".$setting["email"]."
                                                </font> 
                                            </center>
                                        </td>
                                        <td width='20%'>
                                            &nbsp;
                                        </td>
                                    </tr>
                              </table>
                              <hr>
                               <h3>Bukti Booking<h3>
                            </td>
                        </tr>";  
                while($rsquerybooking = mysqli_fetch_array($querybooking)) {
                   echo "<tr class='isi12'>
                            <td width='95%' align='center'>
                              <table width='100%' border='0' align='center'>
                                <tr><td colspan='3'>&nbsp;&nbsp;</td></tr>
                                <tr><td width='25%' valign='top' align='left'>No. Booking</td><td valign='top' width='3%' align='center'>:</td><td width='72%' valign='top' align='left'>".$rsquerybooking["no_booking"]."</td></tr>
                                <tr><td width='25%' valign='top' align='left'>Tgl. Booking</td><td valign='top' width='3%' align='center'>:</td><td width='72%' valign='top' align='left'>".$rsquerybooking["tanggal_booking"]."</td></tr>
                                <tr><td width='25%' valign='top' align='left'>Tgl. Periksa</td><td valign='top' width='3%' align='center'>:</td><td width='72%' valign='top' align='left'>".$rsquerybooking["tanggal"]."</td></tr>
                                <tr><td width='25%' valign='top' align='left'>Nama</td><td valign='top' width='3%' align='center'>:</td><td width='72%' valign='top' align='left'>".$rsquerybooking["nama"]."</td></tr>
                                <tr><td width='25%' valign='top' align='left'>No. Hp/Telp</td><td valign='top' width='3%' align='center'>:</td><td width='72%' valign='top' align='left'>".$rsquerybooking["no_telp"]."</td></tr>
                                <tr><td width='25%' valign='top' align='left'>E-Mail</td><td valign='top' width='3%' align='center'>:</td><td width='72%' valign='top' align='left'>".$rsquerybooking["email"]."</td></tr>
                                <tr><td width='25%' valign='top' align='left'>Alamat</td><td valign='top' width='3%' align='center'>:</td><td width='72%' valign='top' align='left'>".$rsquerybooking["alamat"]."</td></tr>
                                <tr><td width='25%' valign='top' align='left'>Unit/Poliklinik</td><td valign='top' width='3%' align='center'>:</td><td width='72%' valign='top' align='left'>".$rsquerybooking["nm_poli"]."</td></tr>
                                <tr><td colspan='3'>&nbsp;&nbsp;</td></tr>
                                <tr><td colspan='3'>&nbsp;&nbsp;</td></tr>
                              </table>
                              <hr>
                            </td>
                         </tr>";                                 
                }     
                echo "</table> 
                     <script type=\"text/javascript\">
                        window.onload = function() { window.print(); }
                     </script>";
            }else{
                echo "<br><br><br><br><center>Tidak ditemukan</center>";
            } 
        }else{
            echo "<br><br><br><br><center>Tidak ditemukan</center>";
        } 
    ?>  
    </body>
</html>
