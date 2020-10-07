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
        $_sql = "SELECT tgl_piutang,nip,no_member,nm_member,catatan,ongkir,uangmuka,tgltempo from tokopiutang where nota_piutang='$nonota'";  
        $hasil=mysqli_fetch_array(bukaquery($_sql));
        $tanggal   = $hasil["tgl_piutang"]; 
        $catatan   = $hasil["catatan"];
        $nomember  = $hasil["no_member"];
        $member    = $hasil["nm_member"]; 
        $ongkir    = $hasil["ongkir"];
        $muka      = $hasil["uangmuka"];
        $tgltempo  = $hasil["tgltempo"]; 
        $petugas   = getOne("select nama from petugas where nip='".$hasil["nip"]."'"); 

        $_sql = "select toko_detail_piutang.kode_brng,tokobarang.nama_brng, toko_detail_piutang.kode_sat,
                 kodesatuan.satuan,toko_detail_piutang.h_jual, toko_detail_piutang.jumlah, 
                 toko_detail_piutang.subtotal, toko_detail_piutang.dis, toko_detail_piutang.bsr_dis,
                 toko_detail_piutang.total from 
                 toko_detail_piutang inner join tokobarang inner join kodesatuan 
                 on toko_detail_piutang.kode_brng=tokobarang.kode_brng 
                 and toko_detail_piutang.kode_sat=kodesatuan.kode_sat where 
                 toko_detail_piutang.nota_piutang='$nonota'";            
        $hasil=bukaquery($_sql);
        
        $_sqlins = "SELECT nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting";            
        $hasilins=bukaquery($_sqlins);
        $setting = mysqli_fetch_array($hasilins);
        
        if(mysqli_num_rows($hasil)!=0) { 
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
                    <td colspan='6'>
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
                                   <font color='000000' size='2' face='Tahoma'>: ".$tanggal."</font>
                                </td>                                                                
                             </tr> 
                             <tr class='isi14'>
                                <td width='25%'>
                                   <font color='000000' size='2' face='Tahoma'>Alamat Member</font>
                                </td>
                                <td width='25%'>
                                   <font color='000000' size='2' face='Tahoma'>: ".getOne("select alamat from tokomember where no_member='$nomember'")."</font>
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
                                <td width='25%'>
                                   <font color='000000' size='2' face='Tahoma'>: $catatan</font>
                                </td>  
                                <td width='25%'>
                                   <font color='000000' size='2' face='Tahoma'>Tanggal Tempo</font>
                                </td>
                                <td width='25%'>
                                   <font color='000000' size='2' face='Tahoma'>: ".$tgltempo."</font>
                                </td>                                                             
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
                               <tr class=isi>
                                   <td width='4%' align=center>No</td>
                                   <td width='50%' align=center>Barang</td>
                                   <td width='10%' align=center>Satuan</td>
                                   <td width='10%' align=center>Harga</td>
                                   <td width='4%' align=center>Jml</td>
                                   <td width='9%' align=center>Diskon</td>
                                   <td width='13%' align=center>Total</td>
                               </tr>";
                                      $ttlpesan=0;
                                      $i=1;
                                      while($barispesan = mysqli_fetch_array($hasil)) { 
                                          $ttlpesan=$ttlpesan+$barispesan[9];
                                          echo "
                                            <tr class='isi'>
                                                <td>$i</td>
                                                <td>".$barispesan["nama_brng"]."</td>
                                                <td>".$barispesan["satuan"]."</td>
                                                <td align=right>".formatDuit2($barispesan["h_jual"])."</td>
                                                <td align=right>".$barispesan["jumlah"]."</td>
                                                <td align=right>".formatDuit2($barispesan["bsr_dis"])."</td>
                                                <td align=right>".formatDuit2($barispesan["total"])."</td>
                                           </tr>";$i++;
                                      }    
                             echo " <tr class='isi14'>
                                      <td colspan=5></td>
                                      <td>Ttl.Piutang</td>
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
