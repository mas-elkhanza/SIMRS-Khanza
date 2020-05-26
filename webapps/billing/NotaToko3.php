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
        $petugas   =str_replace("_"," ",$_GET['petugas']); 
        $muka      = $_GET['muka']; 
        $ongkir    = $_GET['ongkir']; 
        $tanggal   = $_GET['tanggal']; 
        $nomember  = str_replace("_"," ",$_GET['nomember']); 
        $member    = str_replace("_"," ",$_GET['member']); 
        $tgltempo  = $_GET['tgltempo']; 
        $catatan = str_replace("_"," ",$_GET['catatan']); 

        $_sql = "SELECT no,temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13 from temporary_toko order by no asc";            
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
                                                <td>$barispesan[3]</td>
                                                <td>$barispesan[4]</td>
                                                <td align=right>".formatDuit2($barispesan[6])."</td>
                                                <td align=right>".$barispesan[1]."</td>
                                                <td align=right>".formatDuit2($barispesan[8])."</td>
                                                <td align=right>".formatDuit2($barispesan[9])."</td>
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
