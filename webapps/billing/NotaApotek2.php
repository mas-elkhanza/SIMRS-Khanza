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
        
        $_sql = "SELECT tgl_jual,nip,no_rkm_medis,nm_pasien,keterangan,ongkir from penjualan where nota_jual='$nonota'";            
        $hasil=mysqli_fetch_array(bukaquery($_sql));
        
        $tanggal   =$hasil["tgl_jual"]; 
        $catatan   = $hasil["keterangan"];
        $petugas   = getOne("select nama from petugas where nip='".$hasil["nip"]."'"); 
        $norm      = $hasil["no_rkm_medis"];
        $pasien    = $hasil["nm_pasien"]; 
        $ongkir    = $hasil["ongkir"];

        $_sql = "select detailjual.kode_brng,databarang.nama_brng, detailjual.kode_sat,
                 kodesatuan.satuan,detailjual.h_jual, detailjual.jumlah, 
                 detailjual.subtotal, detailjual.dis, detailjual.bsr_dis,detailjual.tambahan,detailjual.total,detailjual.aturan_pakai from 
                 detailjual inner join databarang inner join kodesatuan inner join jenis 
                 on detailjual.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns 
                 and detailjual.kode_sat=kodesatuan.kode_sat where 
                 detailjual.nota_jual='$nonota'";            
        $hasil=bukaquery($_sql);
        
        
          $setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
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
					 <td colspan='6'><hr/>
						 <table width=100%>
							 <tr class='isi14'>
								<td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>No.RM</font>
								</td>
                                                                <td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>: $norm</font>
								</td>
                                                                <td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>No.Nota</font>
								</td>
                                                                <td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>: $nonota</font>
								</td>                                                                
							 </tr> 
                                                         <tr class='isi14'>
								<td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>Nama Pasien</font>
								</td>
                                                                <td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>: $pasien</font>
								</td>
                                                                <td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>Tanggal</font>
								</td>
                                                                <td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>: $tanggal</font>
								</td>                                                                
							 </tr> 
                                                         <tr class='isi14'>
								<td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>Alamat Pasien</font>
								</td>
                                                                <td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>: ".getOne(
                                                                           "select alamat from pasien where no_rkm_medis='$norm'")."</font>
								</td>
                                                                <td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>Petugas</font>
								</td>
                                                                <td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>: $petugas</font>
								</td>                                                                
							 </tr> 
                                                         
                                                         <tr class='isi14'>
								<td width='25%'>
								   <font color='000000' size='2' face='Tahoma'>Catatan</font>
								</td>
                                                                <td width='25%' colspan='3'>
								   <font color='000000' size='2' face='Tahoma'>: $catatan</font>
								</td>                                                              
							 </tr> 
						 </table>
					 </td>
                 </tr>
                 <tr class='isi14'>
                       <td colspan=4 height='100%' valign=top>
                            <table width=100% cellpadding='0' cellspacing='0'>
                               <tr class=isi15>
                                   <td width='5%' align=center><font color='000000' size='2'  face='Tahoma'>No</font></td>
                                   <td width='20%' align=center><font color='000000' size='2'  face='Tahoma'>Jml</font></td>
                                   <td width='35%' align=center><font color='000000' size='2'  face='Tahoma'>Nama Barang</font></td>
                                   <td width='20%' align=center><font color='000000' size='2'  face='Tahoma'>Total</font></td>
                                   <td width='20%' align=center><font color='000000' size='2'  face='Tahoma'>Aturan Pakai</font></td>
                               </tr>";
                                      $ttlpesan=0;
                                      $i=1;
                                      while($barispesan = mysqli_fetch_array($hasil)) { 
                                          $ttlpesan=$ttlpesan+$barispesan["total"];
                                          echo "
                                            <tr class='isi15'>
                                                <td><font color='000000' size='2'  face='Tahoma'>$i</font></td>
                                                <td><font color='000000' size='2'  face='Tahoma'>".$barispesan["jumlah"]." ".$barispesan["satuan"]."</font></td>
                                                <td><font color='000000' size='2'  face='Tahoma'>".$barispesan["nama_brng"]."</font></td>
                                                <td align=right><font color='000000' size='2'  face='Tahoma'>".formatDuit2($barispesan["total"])."</font></td>
                                                <td><font color='000000' size='2'  face='Tahoma'>".$barispesan["aturan_pakai"]."</font></td>
                                           </tr>";$i++;
                                      }    
                             echo " <tr class='isi14'>
                                      <td colspan=2></td>
                                      <td align='right'><font color='000000' size='2'  face='Tahoma'>Tagihan</font></td>
                                      <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2($ttlpesan)."</font></td>
                                      <td></td>
                                    </tr>     
                                    <tr class='isi14'>
                                      <td colspan=2></td>
                                      <td align='right'><font color='000000' size='2'  face='Tahoma'>PPN</font></td>
                                      <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2($ongkir)."</font></td>
                                      <td></td>
                                    </tr>    
                                    <tr class='isi14'>
                                      <td colspan=2></td>
                                      <td align='right'><font color='000000' size='2'  face='Tahoma'>Tagihan+PPN</font></td>
                                      <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2(($ongkir+$ttlpesan))."</font></td>
                                      <td></td>
                                    </tr> 
                          </table>
                      </td>
                    </tr>
                 </table>";
            
        
    ?>

    </body>
</html>
