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
        $KdKategori   =$_GET['kode']; 
		$petugas   =$_GET['petugas']; 
		$tanggal   =$_GET['Tanggal']; 
		$keterangan   =$_GET['keterangan']; 
		$kategori   =$_GET['kategori']; 
		$keperluan   =$_GET['keperluan']; 
		$nominal   =$_GET['nom']; 
       
        
          $setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
          echo "<table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' class='tbl_form' cellspacing='0' cellpadding='0'>
                 <tr class='isi12'>
                       <td width=50% colspan=4 align=left>
                           <table width='100%' bgcolor='#ffffff' padding='0' align='left' border='0' class='tbl_form'>
								<tr>
									<td padding='0'>
										<img width='60' height='60' src='data:image/jpeg;base64,". base64_encode($setting['logo']). "'/>
									</td>
									<td>
									<center>
											<font color='000000' size='3'  face='Tahoma'>".$setting["nama_instansi"]."</font><br>
                                                                                        <font color='000000' size='3'  face='Tahoma'>
                                                                                            ".$setting["alamat_instansi"].", ".$setting["kabupaten"].", ".$setting["propinsi"]."<br/>
                                                                                            ".$setting["kontak"].", E-mail : ".$setting["email"]."
                                                                                        </font> 
									</center>
									</td>
								</tr>
						   </table><hr>
                       </td>
                 </tr>
                 <tr>
					<td colspan='6'>
						 <table width=100%>
							 <tr class='isi12'>								
                                <td width='25%'>
								   <font color='000000' size='3' face='Tahoma'>No.Nota</font>
								</td>
                                                                <td width='90%'>
								   <font color='000000' size='3' face='Tahoma'>: $KdKategori</font>
								</td>                                                                
							 </tr> 
                                                         <tr class='isi12'>								
                                                                <td width='25%'>
								   <font color='000000' size='3' face='Tahoma'>Tanggal</font>
								</td>
                                                                <td width='90%'>
								   <font color='000000' size='3' face='Tahoma'>: $tanggal</font>
								</td>                                                                
							 </tr>                                       
                                                                                                                  
														 <tr class='isi12'>
								<td width='25%'>
								   <font color='000000' size='3' face='Tahoma'>Sudah Terima Dari</font>
								</td>
                                                                <td width='90%'>
								   <font color='000000' size='3' face='Tahoma'>: ".str_replace("_"," ",$keterangan)."</font>
								</td>
														 </tr>
														  <tr class='isi12'>
								<td width='25%'>
								   <font color='000000' size='3' face='Tahoma'>Unit</font>
								</td>
                                                                <td width='90%'>
								   <font color='000000' size='3' face='Tahoma'>: ".str_replace("_"," ",$kategori)."</font>
								</td>
														 </tr>
                                                         <tr class='isi12'>
								<td width='25%'>
								   <font color='000000' size='3' face='Tahoma'>Keperluan</font>
								</td>
                                                                <td width='90%' colspan='3'>
								   <font color='000000' size='3' face='Tahoma'>: ".str_replace("_"," ",$keperluan)."</font>								   
								</td> 															
							 </tr> 
							 <tr><td>&nbsp;</td>
							 </tr>
							 <tr class='isi12'>
							 <td width='25%'>
								   <font color='000000' size='5' face='Tahoma'>Nominal</font>
								</td>
                                                                <td width='90%' colspan='3'>
																
								   <font color='000000' size='5' face='Tahoma'>: Rp.  "; $num = $nominal + 0; echo number_format($num,0,',','.'); echo",-</font>
								</td> 	
							 </tr>
						 </table>
						 
					 </td>
                 </tr>
                 <tr class='isi12' padding='0'>
			    <td colspan='7' padding='0'>
                                <table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%' align=center><font color='000000' size='3'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='50%' align='center'><font color='000000' size='3'  face='Tahoma'>".getOne("select kabupaten from setting").", ".$tanggal."</font></td>              
                                    </tr>  
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%' align=center><font color='000000' size='3'  face='Tahoma'></td> 
                                     <td padding='0' width='50%' align='center'><font color='000000' size='3'  face='Tahoma'>Petugas</font></td>              
                                    </tr>  
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%'><font color='000000' size='3'  face='Tahoma'>&nbsp;</td> 
                                     <td padding='0' width='50%' align='right'><font color='000000' size='3'  face='Tahoma'></font></td>              
                                    </tr> 
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%'><font color='000000' size='3'  face='Tahoma'>&nbsp;</td> 
                                     <td padding='0' width='50%' align='right'><font color='000000' size='3'  face='Tahoma'></font></td>              
                                    </tr> 
									 <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%'><font color='000000' size='3'  face='Tahoma'>&nbsp;</td> 
                                     <td padding='0' width='50%' align='right'><font color='000000' size='3'  face='Tahoma'></font></td>              
                                    </tr> 
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='50%' align=center><font color='000000' size='3'  face='Tahoma'></td>     
                                     <td padding='0' width='50%' align='center'><font color='000000' size='3'  face='Tahoma'>(".str_replace("_"," ",$petugas).")</font></td>              
                                    </tr>   
                              </table>
                            </td>
                        </tr>
                 </table>";
            
        
    ?>

    </body>
</html>
