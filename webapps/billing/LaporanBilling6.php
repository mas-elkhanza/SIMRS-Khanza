<?php
    include '../conf/conf.php';
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
        $norawat  = "DP/".str_replace("_"," ",$_GET['norawat']); 
        $deposit  =str_replace("_"," ",$_GET['deposit']);
        $pasien   =str_replace("_"," ",$_GET['pasien']);
        $_sql = "select temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_bayar_ranap order by no asc";   
        $hasil=bukaquery($_sql);
        
        if(mysqli_num_rows($hasil)!=0) { 
			$setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
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
                                       <td width='25%' colspan='3' align='center'><font color='333333' size='3'  face='Tahoma'>KWITANSI<br><br></font></td>
                            </tr>
                            <tr>
                               <td width='25%'><font color='333333' size='3'  face='Tahoma'>No. Kwitansi</font></td>
                               <td width='1%'><font color='333333' size='3'  face='Tahoma'>:</font></td>
                               <td width='74%'><font color='333333' size='3'  face='Tahoma'>$norawat</font></td>
                            </tr>
                            <tr>
                               <td><font color='333333' size='3'  face='Tahoma'>Telah terima dari</font></td>
                               <td><font color='333333' size='3'  face='Tahoma'>:</font></td>
                               <td><font color='333333' size='3'  face='Tahoma'>.............................</font></td>
                            </tr>
                            <tr valign='top'>
                               <td><font color='333333' size='3'  face='Tahoma'>Uang Sebanyak</font></td>
                               <td><font color='333333' size='3'  face='Tahoma'>:</font></td>
                               <td><font color='333333' size='3'  face='Tahoma'>".Terbilang($deposit)." rupiah</font></td>
                            </tr>
                            <tr valign='top'>
                               <td><font color='333333' size='3'  face='Tahoma'>Untuk Pembayaran</font></td>
                               <td><font color='333333' size='3'  face='Tahoma'>:</font></td>
                               <td><font color='333333' size='3'  face='Tahoma'>Deposit pasien kamar inap a/n $pasien</font></td>
                            </tr>                           
                            <tr>
                               <td>&nbsp;</td>
                               <td>&nbsp;</td>
                               <td>&nbsp;</td>
                            </tr>
                            <tr>
                               <td></td>
                               <td></td>
                               <td align='right'><font color='333333' size='3'  face='Tahoma'>".getOne("select kabupaten from setting").", ".date('d-m-Y')." &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
                            </tr>               
                            <tr>
                               <td>&nbsp;</td>
                               <td>&nbsp;</td>
                               <td>&nbsp;</td>
                            </tr>                            
                            <tr>
                               <td align='right'><font color='333333' size='3'  face='Tahoma'>Terbilang</font></td>
                               <td><font color='333333' size='3'  face='Tahoma'></font></td>
                               <td><font color='333333' size='3'  face='Tahoma'>Rp. ".formatDuit2($deposit)."</font></td>
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
                               <td align='center'><font color='333333' size='3'  face='Tahoma'>(____________________)</font></td>
                               <td>&nbsp;</td>
                               <td align='right'><font color='333333' size='3'  face='Tahoma'>(____________________)&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
                            </tr> 
                            <tr>
                               <td align='center'><font color='333333' size='3'  face='Tahoma'>Pasien/Keluarga Pasien </font></td>
                               <td>&nbsp;</td>
                               <td align='right'><font color='333333' size='3'  face='Tahoma'>Adm. Keuangan&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
                            </tr> 
                        </table>
                    </td>
                </tr>
            </table>

            "; 
        } else {echo "<font color='333333' size='3'  face='Times New Roman'><b>Data  Billing masih kosong !</b>";}

    ?>  

    </body>
</html>
