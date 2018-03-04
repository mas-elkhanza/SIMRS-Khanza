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

        $_sql = "select piutang.nota_piutang, piutang.tgl_piutang, 
                    piutang.nip,petugas.nama, 
                    piutang.no_rkm_medis,piutang.nm_pasien, 
                    piutang.catatan,piutang.jns_jual,piutang.ongkir,piutang.uangmuka,piutang.sisapiutang,
                    detailpiutang.kode_brng,databarang.nama_brng, detailpiutang.kode_sat,
                    kodesatuan.satuan,detailpiutang.h_jual, detailpiutang.jumlah, 
                    detailpiutang.subtotal, detailpiutang.dis, detailpiutang.bsr_dis, detailpiutang.total,piutang.tgltempo                        
                    from piutang inner join petugas  
                    inner join detailpiutang inner join databarang inner join kodesatuan 
                    on detailpiutang.kode_brng=databarang.kode_brng 
                    and detailpiutang.kode_sat=kodesatuan.kode_sat 
                    and piutang.nip=petugas.nip
                    and piutang.nota_piutang=detailpiutang.nota_piutang where piutang.nota_piutang='$nonota' ";            
        $hasil=bukaquery($_sql);
        $barisdata=  mysqli_fetch_array($hasil);
        
        $nota_piutang   =$barisdata["nota_piutang"];
        $tgl_piutang    =$barisdata["tgl_piutang"]; 
        $nip     =$barisdata["nip"];
        $nama     =$barisdata["nama"];
        $ongkir         =$barisdata["ongkir"];
        $uangmuka       =$barisdata["uangmuka"];
        $sisapiutang    =$barisdata["sisapiutang"];
        $nm_pasien    =$barisdata["nm_pasien"];
        $tgltempo       =$barisdata["tgltempo"];
        $catatan        =$barisdata["catatan"];
        
        
        $hasil2=bukaquery($_sql);
        
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
                    <td width='30%'>: $nota_piutang</td>
                    <td width='50%' colspan=2>$setting[2], ".substr($tgl_piutang,8,2)."-".substr($tgl_piutang,5,2)."-".substr($tgl_piutang,0,4)."</td>
                 </tr> 
                 <tr class='isi14'>
                    <td width='20%'>
                       <font color='000000' size='2' face='Tahoma'>Sales</font>
                    </td>
                    <td width='30%'>: $nama</td>
                    <td width='50%' colspan=2>Nama Pasien : $nm_pasien</td>
                 </tr> 
                 <tr class='isi14'>
                    <td width='20%'>
                       <font color='000000' size='2' face='Tahoma'></font>
                    </td>
                    <td width='30%'></td>
                    <td width='50%' colspan=2>Jatuh Tempo : ".substr($tgltempo,8,2)."-".substr($tgltempo,5,2)."-".substr($tgltempo,0,4)."</td>
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
                                   <td width='10%' align=center>Diskon</td>
                                        <td width='110px' align=center>Total</td>
                               </tr>";
                                      $ttlpesan=0;
                                      $i=1;
                                      while($barisdata = mysqli_fetch_array($hasil2)) { 
                                          $ttlpesan=$ttlpesan+$barisdata["total"];
                                          echo "
                                            <tr class='isi15'>
                                                <td>$i</td>
                                                <td>".$barisdata["nama_brng"]."</td>
                                                <td align=right>".formatDuit2($barisdata["h_jual"])."</td>
                                                <td align=right>".formatDuit2($barisdata["jumlah"])."</td>
                                                <td align=right>".formatDuit2($barisdata["bsr_dis"])."</td>
                                                <td align=right>".formatDuit2($barisdata["total"])."</td>
                                           </tr>";$i++;
                                      }    
                             echo " <tr class='isi14'>
                                      <td colspan=4></td>
                                      <td>Ttl.Beli</td>
                                      <td align='right'>".formatDuit2($ttlpesan)."</td>
                                    </tr> 
                                    <tr class='isi14'>
                                      <td colspan=4></td>
                                      <td>Ongkir</td>
                                      <td align='right'>".formatDuit2($ongkir)."<br/><hr/></td>
                                    </tr> 

                                    <tr class='isi14'>
                                      <td colspan=4></td>
                                      <td>Uang Muka</td>
                                      <td align='right'>".formatDuit2($uangmuka)."<br/><hr/></td>
                                    </tr> 
                                     
                                    <tr class='isi14'>
                                      <td colspan=4></td>
                                      <td><b>Total</b></td>
                                      <td align='right'><b>".formatDuit2($ttlpesan+$ongkir-$uangmuka)."</b></td>
                                    </tr>    
                          </table>
                          <br/>
                      </td>
                    </tr>

                 </table>";
            
        } else {echo "<b>Data pesan masih kosong !</b>";}
    ?>

    </body>
</html>
