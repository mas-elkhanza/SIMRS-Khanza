<?php
 include '../conf/conf.php';
?>
<html>
    <head>
        <link href="style.css" rel="stylesheet" type="text/css" media="screen" />
    </head>
    <body>

    <?php
    reportsqlinjection();      
        $norm       =$_GET['norm'];
        $pasien     =$_GET['pasien'];
        $tanggal    =$_GET['tanggal'];
        $jam        =$_GET['jam'];
        $pjlab      =$_GET['pjlab'];
        $petugas    =$_GET['petugas'];
        $kasir      =$_GET['kasir'];

        $_sql = "select temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_radiologi order by no asc";   
        $hasil=bukaquery($_sql);
        
        if(mysqli_num_rows($hasil)!=0) { 
            $setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
            echo "   
            <table width='".getOne("select notalabrad from set_nota")."' bgcolor='#ffffff' align='left' border='0' padding='0' class='tbl_form' >
            <tr class='isi12' padding='0'>
				<td colspan='7' padding='0'>
					   <table width='100%' bgcolor='#ffffff' align='left' border='0' class='tbl_form' border=0>
							<tr>
								<td  width='20%'>
									<img width='60' height='60' src='data:image/jpeg;base64,". base64_encode($setting['logo']). "'/>
								</td>
								<td>
								<center>
										<font color='000000' size='3'  face='Tahoma'>".$setting["nama_instansi"]."</font><br>
										<font color='000000' size='1'  face='Tahoma'>
										".$setting["alamat_instansi"].", ".$setting["kabupaten"].", ".$setting["propinsi"]."<br/>
										".$setting["kontak"].", E-mail : ".$setting["email"]."
										</font> <br>
										<center><font color='000000' size='1'  face='Tahoma'>BILLING</font> </center>
								</center>
								</td>
								<td  width='20%'>&nbsp;
								</td>
							</tr>
					  </table>
				</td>
            </tr>
            <tr class='isi12' padding='0'>
               <td>
                  <table width='100%' bgcolor='#ffffff' align='left' border='0' class='tbl_form'>
                     <tr>
                         <td width='35%'><font color='000000' size='1'  face='Tahoma'>No.RM</font></td>
                         <td><font color='000000' size='1'  face='Tahoma'>:</font></td>
                         <td width='65%'><font color='000000' size='1'  face='Tahoma'>$norm</font></td>
                     </tr>
                     <tr>
                         <td width='35%'><font color='000000' size='1'  face='Tahoma'>Nama Pasien</font></td>
                         <td><font color='000000' size='1'  face='Tahoma'>:</font></td>
                         <td width='65%'><font color='000000' size='1'  face='Tahoma'>".str_replace("_"," ", $pasien)."</font></td>
                     </tr>                 
                     <tr>
                         <td width='35%'><font color='000000' size='1'  face='Tahoma'>Tanggal</font></td>
                         <td><font color='000000' size='1'  face='Tahoma'>:</font></td>
                         <td width='65%'><font color='000000' size='1'  face='Tahoma'>$tanggal $jam</font></td>
                     </tr>               
                     <tr>
                         <td width='35%'><font color='000000' size='1'  face='Tahoma'>Dokter Penanggung Jawab</font></td>
                         <td><font color='000000' size='1'  face='Tahoma'>:</font></td>
                         <td width='65%'><font color='000000' size='1'  face='Tahoma'>".str_replace("_"," ", $pjlab)."</font></td>
                     </tr>                                 
                     <tr>
                         <td width='35%'><font color='000000' size='1'  face='Tahoma'>Petugas</font></td>
                         <td><font color='000000' size='1'  face='Tahoma'>:</font></td>
                         <td width='65%'><font color='000000' size='1'  face='Tahoma'>".str_replace("_"," ", $petugas)."</font></td>
                     </tr>
                  </table>
               </td>
            </tr>
            <tr class='isi12' padding='0'>
               <td>
                  <table width='100%' bgcolor='#ffffff' align='left' border='0' cellpadding='0' cellspacing='0' >
                     <tr class='head'>
                         <td width='5%'><font color='000000' size='1'  face='Tahoma'>&nbsp;No</font></td>
                         <td width='65%'><font color='000000' size='1'  face='Tahoma'>&nbsp;Nama Pemeriksaan</font></td>
                         <td width='30%'><font color='000000' size='1'  face='Tahoma'>&nbsp;Biaya</font></td>
                     </tr>
                     "; 
                        $z=1;
                        while($item = mysqli_fetch_array($hasil)) {
                           if($item[0]<>"Total Biaya Pemeriksaan Radiologi"){
                              echo "<tr class='isi'>
                                        <td><font color='000000' size='1'  face='Tahoma'>&nbsp;$z</font></td>
                                        <td><font color='000000' size='1'  face='Tahoma'>&nbsp;$item[0]</font></td>
                                        <td><font color='000000' size='1'  face='Tahoma'>&nbsp;".formatDuit($item[1])."</font></td>
                                    </tr>";  
                           }else if($item[0]=="Total Biaya Pemeriksaan Radiologi"){
                               echo "<tr>
                                        <td><font color='000000' size='1'  face='Tahoma'></font></td>
                                        <td><font color='000000' size='1'  face='Tahoma'><b>&nbsp;$item[0]<b></font></td>
                                        <td><font color='000000' size='1'  face='Tahoma'><b>&nbsp;".formatDuit($item[1])."<b></font></td>
                                    </tr>";  
                           }
                           $z++;                                   
                        } 
            
            echo "
                  </table>
               </td>
            </tr>
            <tr>
               <td>
                  <table width='100%' bgcolor='#ffffff' align='left' border='0' class='tbl_form'>
                     <tr>
                         <td width='50%' align='center'><font color='000000' size='1'  face='Tahoma'></font></td>
                         <td width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>Petugas Kasir</font></td>
                     </tr>
                     <tr>
                         <td width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>&nbsp</font></td>
                         <td width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>&nbsp</font></td>
                     </tr>
                     <tr>
                         <td width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>&nbsp</font></td>
                         <td width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>&nbsp</font></td>
                     </tr>
                     <tr>
                         <td width='50%' align='center'><font color='000000' size='1'  face='Tahoma'></font></td>
                         <td width='50%' align='center'><font color='000000' size='1'  face='Tahoma'>( $kasir )</font></td>
                     </tr>
                  </table>
               </td>
            </tr>
            </table>
            ";  
        } else {echo "<font color='000000' size='1'  face='Times New Roman'><b>Data Pemeriksaan Radiologi masih kosong !</b>";}

    ?>  

    </body>
</html>
