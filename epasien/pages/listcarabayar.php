<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<section id="news" data-stellar-background-ratio="2.5">
      <div class="container">
           <div class="row">
                <div class="col-md-12 col-sm-12">
                     <div class="section-title wow fadeInUp" data-wow-delay="0.1s">
                          <h2>Asuransi Yang Bekerja Sama</h2>
                     </div>
                </div>
                       
                <div class="col-md-12 col-sm-12">
                     <div class="news-thumb wow fadeInUp" data-wow-delay="0.3s">
                         <form id="cariKamar" name="frmCariAsuransi" method="post" action="" enctype=multipart/form-data>
                           <table width="100%" border='0' align="center">
                               <tr>
                                  <td width="20%" align="right"><label for="asuransi">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="60%"><input name="asuransi" type="text" id="asuransi" class="form-control" value="" size="65" maxlength="20" autocomplete="off" pattern="[a-zA-Z0-9, ./@_]{1,20}" title=" [a-zA-Z0-9, ./@_]{1,20} " autofocus/></td>
                                  <td width="19%" align="left">&nbsp;<input name="BtnAsuransi" type=submit class="btn btn-warning" value="Cari"/></td>
                               </tr>
                           </table>
                         </form>
                         <div class="table-responsive">
                            <table class="table table-hover" >
                               <tr>
                                   <th width="25%"><center>Nama Asuransi</center></th>
                                   <th width="30%"><center>Perusahaan</center></th>
                                   <th width="35%"><center>Alamat Perusahaan</center></th>
                                   <th width="10%"><center>No.Telp</center></th>
                               </tr>
                               <?php 
                                  $asuransi      = trim(isset($_POST['asuransi']))?trim($_POST['asuransi']):NULL;
                                  $asuransi      = cleankar($asuransi);
                                  if(strlen($asuransi)>20){
                                        header('Location: https://www.google.com');
                                  }else{
                                        $queryasuransi = bukaquery("select png_jawab, nama_perusahaan, alamat_asuransi, no_telp from penjab where status='1' and png_jawab <>'-' and png_jawab not like '%umum%' ".(isset($asuransi)?" and (png_jawab like '%$asuransi%' or nama_perusahaan like '%$asuransi%')":"")." order by png_jawab");
                                        while($rsqueryasuransi = mysqli_fetch_array($queryasuransi)) {
                                            echo "<tr>
                                                    <td align='left'>".$rsqueryasuransi["png_jawab"]."</td>
                                                    <td align='left'>".$rsqueryasuransi["nama_perusahaan"]."</td>
                                                    <td align='left'>".$rsqueryasuransi["alamat_asuransi"]."</td>
                                                    <td align='center'>".$rsqueryasuransi["no_telp"]."</td>
                                                  </tr>";
                                        }  
                                  } 
                              ?>
                           </table> 
                         </div>
                     </div>
                </div>
           </div>
      </div>
 </section>