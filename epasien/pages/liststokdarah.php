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
                          <h2>Ketersediaan Stok Darah</h2>
                     </div>
                </div>
                       
                <div class="col-md-12 col-sm-12">
                     <div class="news-thumb wow fadeInUp" data-wow-delay="0.3s">
                         <form id="cariKamar" name="frmCariDarah" method="post" action="" enctype=multipart/form-data>
                           <table width="100%" border='0' align="center">
                               <tr class="head">
                                  <td width="20%" align="right"><label for="darah">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="60%"><input name="darah" type="text" id="darah" class="form-control" value="" size="65" maxlength="250" autocomplete="off" autofocus/></td>
                                  <td width="19%" align="left">&nbsp;<input name="BtnDarah" type=submit class="btn btn-warning" value="Cari" /></td>
                               </tr>
                           </table>
                         </form>
                         <div class="table-responsive">
                            <table class="table table-hover" >
                               <tr>
                                   <th width="40%"><center>Nama Komponen Darah</center></th>
                                   <th width="20%"><center>G.D.</center></th>
                                   <th width="20%"><center>Resus</center></th>
                                   <th width="20%"><center>Jumlah</center></th>
                               </tr>
                               <?php 
                                  $darah      = trim(isset($_POST['darah']))?trim($_POST['darah']):NULL;
                                  $darah      = cleankar($darah);
                                  $querydarah = bukaquery("select utd_komponen_darah.nama,utd_stok_darah.golongan_darah,utd_stok_darah.resus,count(utd_stok_darah.kode_komponen) as jumlah from utd_komponen_darah inner join utd_stok_darah on utd_stok_darah.kode_komponen=utd_komponen_darah.kode where utd_stok_darah.status='Ada' ".(isset($darah)?" and (utd_komponen_darah.nama like '%$darah%')":"")." group by utd_stok_darah.kode_komponen,utd_stok_darah.golongan_darah,utd_stok_darah.resus order by utd_stok_darah.golongan_darah");
                                  while($rsquerydarah = mysqli_fetch_array($querydarah)) {
                                      echo "<tr>
                                              <td align='left'>".$rsquerydarah["nama"]."</td>
                                              <td align='center'>".$rsquerydarah["golongan_darah"]."</td>
                                              <td align='center'>".$rsquerydarah["resus"]."</td>
                                              <td align='center'>".$rsquerydarah["jumlah"]."</td>
                                            </tr>";
                                  }
                              ?>
                           </table> 
                         </div>
                     </div>
                </div>
           </div>
      </div>
 </section>