<?php
     require_once("../../conf/conf.php");
     header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
     header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
     header("Cache-Control: no-store, no-cache, must-revalidate"); 
     header("Cache-Control: post-check=0, pre-check=0", false);
     header("Pragma: no-cache"); // HTTP/1.0
?>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body bgcolor='#ffffff'>

    <?php
        reportsqlinjection();    
        $no_rawat=isset($_GET['norawat'])?$_GET['norawat']:NULL;
         
        $_sql = "select no,nm_perawatan,pemisah,if(biaya=0,'',biaya),if(jumlah=0,'',jumlah),if(tambahan=0,'',tambahan),if(totalbiaya=0,'',totalbiaya),totalbiaya from billing where no_rawat='$no_rawat' ";   
        $hasil=bukaquery($_sql);
        
        if(mysqli_num_rows($hasil)!=0) { 
            $setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
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
                            <td  width='20%'>
				&nbsp;
			    </td>
                        </tr>
                  </table>
            </td>
            </tr>
            <tr>
              <td colspan='7' padding='0'>
               <hr/>
                <center><font color='333333' size='1'  face='Tahoma'>BILLING</font> </center>
              </td>
            </tr>";  
            $total=0;
            while($inapdrpasien = mysqli_fetch_array($hasil)) {
                $total=$total+$inapdrpasien[7];
                echo    "<tr class='isi12' padding='0'>
                            <td padding='0' width='18%'><font color='111111' size='1'  face='Tahoma'>".$inapdrpasien[0]."</td> 
                            <td padding='0' width='40%'><font color='111111' size='1'  face='Tahoma'>".$inapdrpasien[1]."</td> 
                            <td padding='0' width='2%'><font color='111111' size='1'  face='Tahoma'>".$inapdrpasien[2]."</td> 
                            <td padding='0' width='10%' align='right'><font color='111111' size='1'  face='Tahoma'>".formatDuit2($inapdrpasien[3])."</td> 
                            <td padding='0' width='5%' align='right'><font color='111111' size='1'  face='Tahoma'>".formatDuit2($inapdrpasien[4])."</td> 
                            <td padding='0' width='10%' align='right'><font color='111111' size='1'  face='Tahoma'>".formatDuit2($inapdrpasien[5])."</td> 
                            <td padding='0' width='15%' align='right'><font color='111111' size='1'  face='Tahoma'>".formatDuit2($inapdrpasien[6])."</td> 
                        </tr>";  
            }            
                echo "  <tr class='isi12' padding='0'>
                            <td padding='0' width='18%'><font color='111111' size='1'  face='Tahoma'><b>TOTAL BIAYA</b></td> 
                            <td padding='0' width='40%'><font color='111111' size='1'  face='Tahoma'><b>:</b></td> 
                            <td padding='0' width='2%'><font color='111111' size='1'  face='Tahoma'></td> 
                            <td padding='0' width='10%' align='right'><font color='111111' size='1'  face='Tahoma'></td> 
                            <td padding='0' width='5%' align='right'><font color='111111' size='1'  face='Tahoma'></td> 
                            <td padding='0' width='10%' align='right'><font color='111111' size='1'  face='Tahoma'></td> 
                            <td padding='0' width='15%' align='right'><font color='111111' size='1'  face='Tahoma'><b>".formatDuit2($total)."</b></td> 
                        </tr>
                        <tr class='isi12' padding='0'>
			    <td colspan='7' padding='0'>
                                <table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>&nbsp;</font></td>              
                                    </tr>
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>Keluarga Pasien</td> 
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>     
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>".getOne("select kabupaten from setting")."</font></td>              
                                    </tr>  
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'></td> 
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>Kasir</font></td>              
                                    </tr>  
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%'><font color='000000' size='1'  face='Tahoma'>&nbsp;</td> 
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='right'><font color='000000' size='1'  face='Tahoma'></font></td>              
                                    </tr> 
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%'><font color='000000' size='1'  face='Tahoma'>&nbsp;</td> 
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='right'><font color='000000' size='1'  face='Tahoma'></font></td>              
                                    </tr> 
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>( ............................. )</td>     
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>( ............................. )</font></td>              
                                    </tr>   
                              </table>
                            </td>
                        </tr>
                        <tr class='isi12' padding='0'>
			    <td colspan='7' padding='0'>&nbsp</td>
                        </tr>
                </table>"; 
            
        } else {echo "<font color='333333' size='1'  face='Times New Roman'><b>Data  Billing masih kosong !</b>";}

    ?>  

    </body>
</html>
