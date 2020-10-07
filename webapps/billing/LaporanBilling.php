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
        $petugas = str_replace("_"," ",$_GET['petugas']); 
        $tanggal = str_replace("_"," ",$_GET['tanggal']); 
        reportsqlinjection();   
        $nonota= str_replace(": ","",getOne("select temp2 from temporary_bayar_ralan where temp9='$petugas' and temp1='No.Nota'"));
        $norawat=getOne("select no_rawat from nota_jalan where no_nota='$nonota'");
        $kodecarabayar=getOne("select kd_pj from reg_periksa where no_rawat='$norawat'");
        $carabayar=getOne("select png_jawab from penjab where kd_pj='$kodecarabayar'");
         
         
        $_sql = "select temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_bayar_ralan where temp9='$petugas' order by no asc";   
        $hasil=bukaquery($_sql);
        
        if(mysqli_num_rows($hasil)!=0) { 
            $setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
            echo "   
            <table width='".getOne("select notaralan from set_nota")."' bgcolor='#ffffff' align='left' border='0' padding='0' class='tbl_form' cellspacing='0' cellpadding='0'>
            <tr class='isi12' padding='0'>
				<td colspan='7' padding='0'>
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
            </tr>
            ";  $z=1;
                while($inapdrpasien = mysqli_fetch_array($hasil)) {
                   if($z<=6){
                      echo "<tr class='isi12' padding='0'>
                                <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>".str_replace("  ","&nbsp;&nbsp;",$inapdrpasien[0])."</td> 
                                <td padding='0' width='40%' colspan='6'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</td>              
                             </tr>";  
                   }
                   $z++;                                   
                }  
                
                $_sql = "select temp2 from temporary_bayar_ralan where temp9='$petugas' and temp8='Dokter' group by temp2 order by no asc";   
                $hasil=bukaquery($_sql);
                echo "<tr class='isi12' padding='0'>
                       <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>Dokter</td> 
                       <td padding='0' width='40%' colspan='6'>:";
                       while($inapdrpasien = mysqli_fetch_array($hasil)) {
			  echo "<font color='000000' size='1'  face='Tahoma'>&nbsp;$inapdrpasien[0]</font></br>";				                    
                       }
                  echo "</td>              
                      </tr>";   
                   
                $hasil2=bukaquery("select temp1,temp2,temp3,temp7 from temporary_bayar_ralan where temp9='$petugas' and temp8='Registrasi' order by no asc");
                while($inapdrpasien = mysqli_fetch_array($hasil2)) {
                    echo "<tr class='isi12' padding='0'>
                       <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>Administrasi Rekam Medik</td> 
                       <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                       <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>     
                       <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                      </tr>"; 			                    
                } 
                
                $hasil3=bukaquery("select temp1,temp2,temp3,temp7,temp5 from temporary_bayar_ralan where temp9='$petugas' and (temp8='Ralan Dokter' or temp8='Ralan Dokter Paramedis' or temp8='Ralan Paramedis' or temp8='Laborat' or temp8='Radiologi') order by no asc");
                echo "<tr class='isi12' padding='0'>
                       <td padding='0' width='30%' valign='top'><font color='000000' size='1'  face='Tahoma'>Tindakan</td> 
                       <td padding='0' width='40%' colspan='6'>
                       <table border='0' width='100%' padding='0' cellspacing='0' cellpadding='0'>
                             ";                      
                       while($inapdrpasien = mysqli_fetch_array($hasil3)) {
                           if(!empty($inapdrpasien[3])){
                                echo "<tr class='isi12' padding='0'> 
                                         <td padding='0' width='80%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                                         <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[4]</font></td>   
                                         <td padding='0' width='19%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                                     </tr>";                            
                           }
                       }
                   echo"</table>
                        </td>               
                      </tr>"; 
                
                $hasil3=bukaquery("select temp1,temp2,temp3,temp7,temp5 from temporary_bayar_ralan where temp9='$petugas' and temp8='Operasi' order by no asc");
                if(mysqli_num_rows($hasil3)!=0) { 
                    echo "<tr class='isi12' padding='0'>
                           <td padding='0' width='30%' valign='top'><font color='000000' size='1'  face='Tahoma'>Operasi / VK</td> 
                           <td padding='0' width='40%' colspan='6'>
                           <table border='0' width='100%' padding='0' cellspacing='0' cellpadding='0'>
                                 ";                      
                           while($inapdrpasien = mysqli_fetch_array($hasil3)) {
                               if(!empty($inapdrpasien[3])){
                                    echo "<tr class='isi12' padding='0'> 
                                             <td padding='0' width='80%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                                             <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[4]</font></td>   
                                             <td padding='0' width='19%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                                         </tr>";                            
                               }
                           }
                       echo"</table>
                            </td>               
                          </tr>"; 
                }  
                   
                $hasil4=bukaquery("select temp1,temp2,temp3,temp7,temp8,temp5 from temporary_bayar_ralan where temp9='$petugas' and (temp8='Obat' or temp8='TtlObat') group by temp2 order by no asc");
                $inapdrpasien = mysqli_fetch_array($hasil4);
                if(!empty($inapdrpasien[1])){
                    echo "<tr class='isi12' padding='0'>
                            <td padding='0' width='30%' valign='top'><font color='000000' size='1'  face='Tahoma'>Obat & BHP</td> 
                            <td padding='0' width='40%' colspan='6'>
                            <table border='0' width='100%' padding='0' cellspacing='0' cellpadding='0'>";
                            while($inapdrpasien = mysqli_fetch_array($hasil4)) {
                                if(!empty($inapdrpasien[3])){
                                     echo "<tr class='isi12' padding='0'> 
                                              <td padding='0' width='80%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                                              <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[5]</font></td>   
                                              <td padding='0' width='19%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                                          </tr>";                            
                                }else if($inapdrpasien["temp8"]=="TtlObat"){
                                    echo "<tr class='isi12' padding='0'> 
                                              <td padding='0' width='80%'><font color='000000' size='1'  face='Tahoma'></font></td>   
                                              <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>   
                                              <td padding='0' width='19%' align='right'><font color='000000' size='1'  face='Tahoma'><b>".$inapdrpasien["temp2"]."<b></font></td>              
                                          </tr>";                                    
                                }
                            }
                        echo"</table>
                             </td>               
                           </tr>"; 
                } 
                
                $hasil5=bukaquery("select temp1,temp2,temp3,temp7,temp5 from temporary_bayar_ralan where temp9='$petugas' and temp8='Potongan'  order by no asc");
                while($inapdrpasien = mysqli_fetch_array($hasil5)) {
                    echo "<tr class='isi12' padding='0'>
                       <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                       <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                       <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[4]</font></td>     
                       <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                      </tr>"; 			                    
                } 
                
                $hasil6=bukaquery("select temp1,temp2,temp3,temp7,temp5 from temporary_bayar_ralan where temp9='$petugas' and temp8='Tambahan'  order by no asc");
                while($inapdrpasien = mysqli_fetch_array($hasil6)) {
                    echo "<tr class='isi12' padding='0'>
                       <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                       <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                       <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[4]</font></td>     
                       <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                      </tr>"; 			                    
                } 
                
                $hasil7=bukaquery("select temp1,temp2,temp3,temp7,temp5 from temporary_bayar_ralan where temp9='$petugas' and temp8='-' and temp7<>'' group by temp2 order by no asc");
                while($inapdrpasien = mysqli_fetch_array($hasil7)) {
                    echo "<tr class='isi12' padding='0'>
                       <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                       <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[1]</font></td>   
                       <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>     
                       <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[3]</font></td>              
                      </tr>"; 			                    
                } 
                
                $hasil7=bukaquery("select temp1,temp2,temp3,temp7 from temporary_bayar_ralan where temp9='$petugas' and temp8='Tagihan' and temp7<>'' order by no asc");
                while($inapdrpasien = mysqli_fetch_array($hasil7)) {
                    if($inapdrpasien["temp1"]=="TOTAL BAYAR"){
                        echo "<tr class='isi12' padding='0'>
                                <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                                <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'>".  Terbilang(str_replace(",","",str_replace(".","",$inapdrpasien[3])))." rupiah</font></td>   
                                <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>     
                                <td padding='0' width='14%' align='right'><font color='000000' size='2'  face='Tahoma'><b>$inapdrpasien[3]</b></font></td>              
                             </tr>"; 
                    }else{
                        echo "<tr class='isi12' padding='0'>
                                <td padding='0' width='30%'><font color='000000' size='1'  face='Tahoma'>$inapdrpasien[0]</td> 
                                <td padding='0' width='55%' colspan='4'><font color='000000' size='1'  face='Tahoma'></font></td>   
                                <td padding='0' width='1%'><font color='000000' size='1'  face='Tahoma'></font></td>     
                                <td padding='0' width='14%' align='right'><font color='000000' size='1'  face='Tahoma'><b>$inapdrpasien[3]</b></font></td>              
                             </tr>";                         
                    }                        
                } 
                   
            echo "
                        <tr class='isi12' padding='0'>
			    <td colspan='7' padding='0'>
                                <table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>".getOne("select kabupaten from setting").", ".$tanggal."</font></td>              
                                    </tr>  
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>Petugas</td> 
                                     <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>Penanggung Jawab Pasien</font></td>              
                                    </tr>  
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%'><font color='000000' size='1'  face='Tahoma'>&nbsp;</td> 
                                     <td padding='0' width='50%' align='right'><font color='000000' size='1'  face='Tahoma'></font></td>              
                                    </tr> 
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%'><font color='000000' size='1'  face='Tahoma'>&nbsp;</td> 
                                     <td padding='0' width='50%' align='right'><font color='000000' size='1'  face='Tahoma'></font></td>              
                                    </tr> 
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%' align=center><font color='000000' size='1'  face='Tahoma'>( ";
                                        if(getOne("select count(nama) from petugas where nip='$petugas'")>=1){
                                            echo getOne("select nama from petugas where nip='$petugas'");                                            
                                        }else{
                                            echo " .............................. ";
                                        }
                                        echo " )</td>     
                                     <td padding='0' width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>(.............)</font></td>              
                                    </tr>   
                              </table>
                            </td>
                        </tr>
                  </table>                                
            "; 
        } else {echo "<font color='000000' size='1'  face='Times New Roman'><b>Data  Billing masih kosong !</b>";}

    ?>  

    </body>
</html>
