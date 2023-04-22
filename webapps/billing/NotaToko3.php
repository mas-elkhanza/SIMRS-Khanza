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
            $usere      = trim(isset($_GET['usere']))?trim($_GET['usere']):NULL;
            $passwordte = trim(isset($_GET['passwordte']))?trim($_GET['passwordte']):NULL;
            if((USERHYBRIDWEB==$usere)&&(PASHYBRIDWEB==$passwordte)){
                $nonota    = validTeks4(str_replace("_"," ",$_GET['nonota']),20);  
                $petugas   = validTeks4(str_replace("_"," ",$_GET['petugas']),70); 
                $muka      = validTeks4($_GET['muka'],20); 
                $ongkir    = validTeks4($_GET['ongkir'],20); 
                $tanggal   = validTeks4($_GET['tanggal'],20); 
                $nomember  = validTeks4(str_replace("_"," ",$_GET['nomember']),70); 
                $member    = validTeks4(str_replace("_"," ",$_GET['member']),70); 
                $tgltempo  = validTeks4($_GET['tgltempo'],20); 
                $catatan   = validTeks4(str_replace("_"," ",$_GET['catatan']),70); 

                $_sql = "SELECT temporary_toko.no,temporary_toko.temp1,temporary_toko.temp2,temporary_toko.temp3,temporary_toko.temp4,temporary_toko.temp5,temporary_toko.temp6,temporary_toko.temp7, temporary_toko.temp8, temporary_toko.temp9, temporary_toko.temp10, temporary_toko.temp11, temporary_toko.temp12, temporary_toko.temp13 from temporary_toko order by temporary_toko.no asc";            
                $hasil=bukaquery($_sql);

                $_sqlins = "select setting.nama_instansi,setting.alamat_instansi,setting.kabupaten,setting.propinsi,setting.kontak,setting.email,setting.logo from setting";            
                $hasilins=bukaquery($_sqlins);
                $setting = mysqli_fetch_array($hasilins);

                if(mysqli_num_rows($hasil)!=0) { 
                  echo "<table width='".getOne("select set_nota.notatoko from set_nota")."'  border='0' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
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
            }else {
                exit(header("Location:../index.php"));
            }
        ?>

    </body>
</html>
