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
                $nopenyerahan   = validTeks4(str_replace("_"," ",$_GET['nopenyerahan']),20); 
                $tanggal        = validTeks4($_GET['tanggal'],20); 
                $catatan        = validTeks4(str_replace("_"," ",$_GET['catatan']),70); 
                $petugaspj      = validTeks4(str_replace("_"," ",$_GET['petugaspj']),70); 
                $pasien         = validTeks4(str_replace("_"," ",$_GET['pasien']),70); 
                $besarppn       = validTeks4(str_replace("_"," ",$_GET['besarppn']),20); 
                $bayar          = validTeks4(str_replace("_"," ",$_GET['bayar']),20); 
                $alamatip       = validTeks4(str_replace("_"," ",$_GET['alamatip']),70); 

                $_sql = "SELECT temporary.no,temporary.temp1,temporary.temp2,temporary.temp3,temporary.temp4,temporary.temp5,temporary.temp6,temporary.temp7,temporary.temp8,temporary.temp9,temporary.temp10,temporary.temp11,temporary.temp12,temporary.temp13 from temporary where temporary.temp37='$alamatip' order by temporary.no";            
                $hasil=bukaquery($_sql);

                if(mysqli_num_rows($hasil)!=0) { 
                  $setting=  mysqli_fetch_array(bukaquery("select setting.nama_instansi,setting.alamat_instansi,setting.kabupaten,setting.propinsi,setting.kontak,setting.email,setting.logo from setting"));
                  echo "<table width='".getOne("select notaapotek  from set_nota")."'  border='0' align='left' cellpadding='0' cellspacing='0' class='tbl_form'>
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
                                               <font color='000000' size='2' face='Tahoma'>No.Penyerahan</font>
                                            </td>
                                            <td width='25%'>
                                               <font color='000000' size='2' face='Tahoma'>: $nopenyerahan</font>
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
                                               <font color='000000' size='2' face='Tahoma'>Petugas P.J.</font>
                                            </td>
                                            <td width='25%'>
                                               <font color='000000' size='2' face='Tahoma'>: $petugaspj</font>
                                            </td>
                                            <td width='25%'>
                                               <font color='000000' size='2' face='Tahoma'>Pengambil Darah</font>
                                            </td>
                                            <td width='25%'>
                                               <font color='000000' size='2' face='Tahoma'>: $pasien</font>
                                            </td>                                                                
                                        </tr> 
                                        <tr class='isi14'>
                                            <td width='25%'>
                                               <font color='000000' size='2' face='Tahoma'>Keterangan</font>
                                            </td>
                                            <td width='75%' colspan='3'>
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
                                           <td width='15%' align=center><font color='000000' size='2'  face='Tahoma'>No.Kantung</font></td>
                                           <td width='40%' align=center><font color='000000' size='2'  face='Tahoma'>Komponen</font></td>
                                           <td width='10%' align=center><font color='000000' size='2'  face='Tahoma'>G.D.</font></td>
                                           <td width='10%' align=center><font color='000000' size='2'  face='Tahoma'>Rhesus</font></td>
                                           <td width='20%' align=center><font color='000000' size='2'  face='Tahoma'>Biaya</font></td>
                                       </tr>";
                                              $ttlpesan=0;
                                              $i=1;
                                              while($barispesan = mysqli_fetch_array($hasil)) { 
                                                  $ttlpesan=$ttlpesan+$barispesan[5];
                                                  echo "
                                                    <tr class='isi15'>
                                                        <td><font color='000000' size='2'  face='Tahoma'>$i</font></td>
                                                        <td><font color='000000' size='2'  face='Tahoma'>$barispesan[1]</font></td>
                                                        <td><font color='000000' size='2'  face='Tahoma'>$barispesan[2] </font></td>
                                                        <td><font color='000000' size='2'  face='Tahoma'>$barispesan[3] </font></td>
                                                        <td><font color='000000' size='2'  face='Tahoma'>$barispesan[4] </font></td>
                                                        <td align=right><font color='000000' size='2'  face='Tahoma'>".formatDuit2($barispesan[5])."</font></td>
                                                   </tr>";$i++;
                                              }    
                                     echo " <tr class='isi14'>
                                              <td colspan=4></td>
                                              <td align='right'><font color='000000' size='2'  face='Tahoma'>Tagihan</font></td>
                                              <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2($ttlpesan)."</font></td>
                                            </tr>  
                                            <tr class='isi14'>
                                              <td colspan=4></td>
                                              <td align='right'><font color='000000' size='2'  face='Tahoma'>PPN</font></td>
                                              <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2($besarppn)."</font></td>
                                            </tr>   
                                            <tr class='isi14'>
                                              <td colspan=4></td>
                                              <td align='right'><font color='000000' size='2'  face='Tahoma'>Tagihan+PPN</font></td>
                                              <td align='right'><font color='000000' size='2'  face='Tahoma'>".formatDuit2($ttlpesan+$besarppn)."</font></td>
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
