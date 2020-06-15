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
        $nonota    = validTeks(str_replace("_"," ",$_GET['nonota'])); 
        
        $_sql = "SELECT tgl_jual,nip,no_member,nm_member,keterangan,ongkir,ppn from tokopenjualan where nota_jual='$nonota'";  
        $hasil=mysqli_fetch_array(bukaquery($_sql));
        
        $tanggal   = $hasil["tgl_jual"]; 
        $catatan   = $hasil["keterangan"];
        $nomember  = $hasil["no_member"];
        $member    = $hasil["nm_member"]; 
        $ongkir    = $hasil["ongkir"];
        $besarppn  = $hasil["ppn"];
        $petugas   = getOne("select nama from petugas where nip='".$hasil["nip"]."'"); 

        $_sql = "select toko_detail_jual.kode_brng,tokobarang.nama_brng, toko_detail_jual.kode_sat,
                 kodesatuan.satuan,toko_detail_jual.h_jual, toko_detail_jual.jumlah, 
                 toko_detail_jual.subtotal, toko_detail_jual.dis, toko_detail_jual.bsr_dis,
                 toko_detail_jual.tambahan,toko_detail_jual.total from 
                 toko_detail_jual inner join tokobarang inner join kodesatuan 
                 on toko_detail_jual.kode_brng=tokobarang.kode_brng 
                 and toko_detail_jual.kode_sat=kodesatuan.kode_sat where 
                 toko_detail_jual.nota_jual='$nonota'";            
        $hasil=bukaquery($_sql);
        
        
          $setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
          echo "<table width='".getOne("select notatoko from set_nota")."'  border='0' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
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
                                   <font color='000000' size='2' face='Tahoma'>No.Member</font>
                                </td>
                                <td width='25%'>
                                   <font color='000000' size='2' face='Tahoma'>: $nomember</font>
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
                                   <font color='000000' size='2' face='Tahoma'>Nama Member</font>
                                </td>
                                <td width='25%'>
                                   <font color='000000' size='2' face='Tahoma'>: $member</font>
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
                                   <font color='000000' size='2' face='Tahoma'>Alamat Member</font>
                                </td>
                                <td width='25%'>
                                   <font color='000000' size='2' face='Tahoma'>: ".getOne(
                                           "select alamat from tokomember where no_member='$nomember'")."</font>
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
                           <tr class=isi>
                               <td width='5%' align=center><font color='000000' size='2'  face='Tahoma'>No</font></td>
                               <td width='20%' align=center><font color='000000' size='2'  face='Tahoma'>Jml</font></td>
                               <td width='55%' align=center><font color='000000' size='2'  face='Tahoma'>Nama Barang</font></td>
                               <td width='20%' align=center><font color='000000' size='2'  face='Tahoma'>Total</font></td>
                           </tr>";
                                  $ttlpesan=0;
                                  $i=1;
                                  while($barispesan = mysqli_fetch_array($hasil)) { 
                                      $ttlpesan=$ttlpesan+$barispesan["total"];
                                      echo "
                                        <tr class='isi'>
                                            <td><font color='000000' size='2'  face='Tahoma'>$i</font></td>
                                            <td><font color='000000' size='2'  face='Tahoma'>".$barispesan["jumlah"]." ".$barispesan["satuan"]."</font></td>
                                            <td><font color='000000' size='2'  face='Tahoma'>".$barispesan["nama_brng"]."</font></td>
                                            <td align=right><font color='000000' size='2'  face='Tahoma'>".formatDuit2($barispesan["total"])."</font></td>
                                       </tr>";$i++;
                                  }    
                         echo " <tr class='isi14'>
                                  <td colspan=2></td>
                                  <td align='right'><font color='000000' size='2'  face='Tahoma'>Jumlah Total</font></td>
                                  <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2($ttlpesan)."</font></td>
                                  <td></td>
                                </tr>  
                                <tr class='isi14'>
                                  <td colspan=2></td>
                                  <td align='right'><font color='000000' size='2'  face='Tahoma'>PPN</font></td>
                                  <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2($besarppn)."</font></td>
                                  <td></td>
                                </tr>  
                                <tr class='isi14'>
                                  <td colspan=2></td>
                                  <td align='right'><font color='000000' size='2'  face='Tahoma'>Ongkir</font></td>
                                  <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2($ongkir)."</font></td>
                                  <td></td>
                                </tr>  
                                <tr class='isi14'>
                                  <td colspan=2></td>
                                  <td align='right'><font color='000000' size='2'  face='Tahoma'>Jumlah Total+Ongkir+PPN</font></td>
                                  <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2($ttlpesan+$besarppn)."</font></td>
                                  <td></td>
                                </tr>  
                      </table>
                  </td>
                </tr>
             </table>";
    ?>
    </body>
</html>
