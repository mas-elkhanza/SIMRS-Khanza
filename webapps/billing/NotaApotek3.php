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
        $nonota    =str_replace("_"," ",$_GET['nonota']);  
        $kdptg     =str_replace("_"," ",$_GET['kdptg']); 
        $muka      = $_GET['muka']; 
        $ongkir    = $_GET['ongkir']; 
        $tanggal   = $_GET['tanggal']; 
        $nm_member = str_replace("_"," ",$_GET['nm_member']); 
        $tgltempo  = $_GET['tgltempo']; 
        $catatan = str_replace("_"," ",$_GET['catatan']); 

        $_sql = "SELECT kode_brng, nama_brng, satuan, h_jual,jumlah, subtotal, dis, bsr_dis, total from tamppiutang";            
        $hasil=bukaquery($_sql);
        
        $_sqluser = "SELECT nama from petugas where  nip='$kdptg'";            
        $hasiluser=bukaquery($_sqluser);
        $barisuser = mysqli_fetch_array($hasiluser);
        
        $_sqlins = "SELECT nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting";            
        $hasilins=bukaquery($_sqlins);
        $setting = mysqli_fetch_array($hasilins);
        
        if(mysqli_num_rows($hasil)!=0) { 
          echo "<table width='".getOne("select notaapotek from set_nota")."'  border='0' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
                 <tr class='isi14'>
                       <td width=50% colspan=4 align=left>
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
								</tr>
						   </table>
                       </td>
                 </tr>
                 <tr>
                 <td colspan='6'>
                 <table width=100%>
                 <tr class='isi14'>
                    <td width='20%'>
                       <font color='000000' size='2' face='Tahoma'>Faktur No</font>
                    </td>
                    <td width='40%'>: $nonota</td>
                    <td width='40%' colspan=2>$setting[2], ".substr($tanggal,8,2)."-".substr($tanggal,5,2)."-".substr($tanggal,0,4)."</td>
                 </tr> 
                 <tr class='isi14'>
                    <td width='20%'>
                       <font color='000000' size='2' face='Tahoma'>Sales</font>
                    </td>
                    <td width='40%'>: $barisuser[0]</td>
                    <td width='40%'>Nama Pasien : $nm_member</td>
                 </tr> 
                 <tr class='isi14'>
                    <td width='20%'>
                       <font color='000000' size='2' face='Tahoma'></font>
                    </td>
                    <td width='40%'></td>
                    <td width='40%' colspan=2>Jatuh Tempo : ".substr($tgltempo,8,2)."-".substr($tgltempo,5,2)."-".substr($tgltempo,0,4)."</td>
                 </tr>
                 </table>
                 </td>
                 </tr>
                 <tr class='isi13'>
                     <td colspan=5>&nbsp;</td>
                 </tr>
                 <tr class='isi14'>
                      <td colspan=5 height='310px' valign=top>
                            <table width=100% cellpadding='0' cellspacing='0' >
                               <tr class=isi15>
                                   <td width='5%' align=center>No</td>
                                   <td width='50%' align=center>Barang</td>
                                   <td width='10%' align=center>Satuan</td>
                                   <td width='10%' align=center>Harga</td>
                                   <td width='5%' align=center>Jml</td>
                                   <td width='10%' align=center>Diskon</td>
                                        <td width='110px' align=center>Total</td>
                               </tr>";
                                      $ttlpesan=0;
                                      $i=1;
                                      while($barispesan = mysqli_fetch_array($hasil)) { 
                                          $ttlpesan=$ttlpesan+$barispesan[8];
                                          echo "
                                            <tr class='isi15'>
                                                <td>$i</td>
                                                <td>$barispesan[1]</td>
                                                <td>$barispesan[2]</td>
                                                <td align=right>".formatDuit2($barispesan[3])."</td>
                                                <td align=right>".formatDuit2($barispesan[4])."</td>
                                                <td align=right>".formatDuit2($barispesan[7])."</td>
                                                <td align=right>".formatDuit2($barispesan[8])."</td>
                                           </tr>";$i++;
                                      }    
                             echo " <tr class='isi14'>
                                      <td colspan=5></td>
                                      <td>Ttl.Beli</td>
                                      <td align='right'>".formatDuit2($ttlpesan)."</td>
                                    </tr> 
                                    <tr class='isi14'>
                                      <td colspan=5></td>
                                      <td>Ongkir</td>
                                      <td align='right'>".formatDuit2($ongkir)."<br/><hr/></td>
                                    </tr>   
                                    <tr class='isi14'>
                                      <td colspan=5></td>
                                      <td>Uang Muka</td>
                                      <td align='right'>".formatDuit2($muka)."<br/><hr/></td>
                                    </tr>                                    
                                    <tr class='isi14'>
                                      <td colspan=5></td>
                                      <td><b>Total</b></td>
                                      <td align='right'><b>".formatDuit2($ttlpesan+$ongkir-$muka)."</b></td>
                                    </tr>  
                          </table>
                      </td>
                    </tr>
                 </table>";
            
        } else {echo "<b>Data pesan masih kosong !</b>";}
    ?>

    </body>
</html>
