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
        $petugas = str_replace("_"," ",$_GET['petugas']); 
        $tanggal = str_replace("_"," ",$_GET['tanggal']);
        
        $nonota= str_replace(": ","",getOne("select temp2 from temporary_bayar_ranap where temp1='No.Nota'"));
        $norawat=getOne("select no_rawat from nota_inap where no_nota='$nonota'");
        $kodecarabayar=getOne("select kd_pj from reg_periksa where no_rawat='$norawat'");
        $carabayar=getOne("select png_jawab from penjab where kd_pj='$kodecarabayar'");
         
        $_sql = "select temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_bayar_ranap order by no asc";   
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
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>".getOne("select kabupaten from setting").", ".$tanggal."</font></td>              
                                    </tr>  
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>Kabid Umum & Keuangan</td> 
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
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>(";
                                        if(getOne("select count(nama) from petugas where nip='$petugas'")>=1){
											echo getOne("select nama from petugas where nip='$petugas'");                                            
                                        }else{
                                            echo " .............................. ";
                                        }
                                        echo ")</font></td>              
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

    ?>  

    </body>
</html>
