<?php
 include '../conf/conf.php';
?>
<html>
    <head>
        <link href="css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <script type="text/javascript">
            window.onload = function() { window.print(); }
        </script>
    <?php
    reportsqlinjection();      
        $noretur     = str_replace("_"," ",isset($_GET['noretur'])?$_GET['noretur']:NULL);  
        $petugas     = str_replace("_"," ",isset($_GET['petugas'])?$_GET['petugas']:NULL); 
        $tanggal     = isset($_GET['tanggal'])?$_GET['tanggal']:"00-00-0000"; 
        $nm_member   = str_replace("_"," ",isset($_GET['nm_member'])?$_GET['nm_member']:NULL); 
        $catatan     = str_replace("_"," ",isset($_GET['catatan'])?$_GET['catatan']:NULL);

        $_sql = "select  nama_brng, h_retur, jml_retur, subtotal from  tampreturjual ";            
        $hasil=bukaquery($_sql);
        
        $_sqlins = "select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting";            
        $hasilins=bukaquery($_sqlins);
        $setting = mysqli_fetch_array($hasilins);
        
        if(mysqli_num_rows($hasil)!=0) { 
          echo "<table width='".getOne("select notaapotek from set_nota")."'  border='0' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
                 <tr class='isi14'>
                       <td colspan=6 align=center>
                           <table width='100%' bgcolor='#ffffff' padding='0' align='left' border='0' class='tbl_form'>
								<tr>
									<td padding='0'>
										<img width='60' height='60' src='data:image/jpeg;base64,". base64_encode($setting['logo']). "'/>
									</td>
									<td>
									<center>
											<font color='000000' size='3'  face='Tahoma'>".$setting["nama_instansi"]."</font><br>
                                                                                        <font color='000000' size='2'  face='Tahoma'>
                                                                                            ".$setting["alamat_instansi"].", ".$setting["kabupaten"].", ".$setting["propinsi"]."<br/>
                                                                                            ".$setting["kontak"].", E-mail : ".$setting["email"]."
                                                                                        </font> 
									</center>
									</td>
                                                                        <td width='60px' padding='0'>
										&nbsp;
									</td>
								</tr>
						   </table>
                       </td>
                 </tr>
                 <tr class='isi14'>
                    <td colspan='6' align='center'>
                       <font color='000000' size='3' face='Tahoma'>TANDA TERIMA RETUR </font>
                    </td>
                 </tr> 
                 <tr class='isi14'>
                    <td colspan='6' align='center'>
                       <font color='000000' size='2' face='Tahoma'>&nbsp;</font>
                    </td>
                 </tr> 
                 <tr>
                 <td colspan='6'>
                 <table width=100%>
                 <tr class='isi14'>
                    <td width='20%'>
                       <font color='000000' size='2' face='Tahoma'>No.Retur</font>
                    </td>
                    <td width='40%'>: $noretur</td>
                    <td width='40%' colspan=2>$setting[2], $tanggal</td>
                 </tr> 
                 <tr class='isi14'>
                    <td width='20%'>
                       <font color='000000' size='2' face='Tahoma'>Sales</font>
                    </td>
                    <td width='40%'>: $petugas</td>
                    <td width='40%' colspan=2>Nama Pasien : $nm_member</td>
                 </tr> 
                 </table>
                 </td>
                 </tr>
                 <tr class='isi13'>
                     <td colspan=4>&nbsp;</td>
                 </tr>
                 <tr class='isi14'>
                       <td colspan=4 height='310px' valign=top>
                            <table width=100% cellpadding='0' cellspacing='0'>
                               <tr class=isi15>
                                   <td width='5%' align=center>No</td>
                                   <td width='60%' align=center>Barang</td>
                                   <td width='10%' align=center>Harga</td>
                                   <td width='5%' align=center>Jml</td>
                                   <td width='10%' align=center>Total</td>
                               </tr>";
                                      $ttlpesan=0;
                                      $i=1;
                                      while($barispesan = mysqli_fetch_array($hasil)) { 
                                          $ttlpesan=$ttlpesan+$barispesan[3];
                                          echo "
                                            <tr class='isi15'>
                                                <td>$i</td>
                                                <td>$barispesan[0]</td>
                                                <td align=right>".formatDuit2($barispesan[1])."</td>
                                                <td align=right>".formatDuit2($barispesan[2])."</td>
                                                <td align=right>".formatDuit2($barispesan[3])."</td>
                                           </tr>";$i++;
                                      }    
                             echo " <tr class='isi14'>
                                      <td colspan=4 align=right><b>Total Retur</b></td>
                                      <td align='right'><b>".formatDuit2($ttlpesan)."</b></td>
                                    </tr> 
                          </table>
                      </td>
                    </tr>
                    
                 </table>";
            
        } else {echo "<b>Data pesan masih kosong !</b>";}
    ?>

    </body>
</html>
