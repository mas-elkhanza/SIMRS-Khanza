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
                          <h2>Laboratorium</h2>
                     </div>
                </div>
                       
                <div class="col-md-12 col-sm-12">
                     <div class="news-thumb wow fadeInUp" data-wow-delay="0.3s">
                         <form id="cariKamar" name="frmCariLaborat" method="post" action="" enctype=multipart/form-data>
                           <table width="100%" border="0" align="center">
                               <tr class="head">
                                  <td width="20%" align="right"><label for="laborat">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="60%"><input name="laborat" type="text" id="laborat" pattern="[a-zA-Z0-9, ./@_]{1,65}" title=" a-zA-Z0-9, ./@_ (Maksimal 65 karakter)" class="form-control" value="" size="65" maxlength="250" autocomplete="off" autofocus/></td>
                                  <td width="19%" align="left">&nbsp;<input name="BtnLaborat" type=submit class="btn btn-warning" value="Cari" /></td>
                               </tr>
                           </table>
                         </form>
                         <div class="table-responsive">
                            <table class="table table-hover" >
                               <tr>
                                   <th width="70%"><center>Paket Pemeriksaan</center></th>
                                   <th width="15%"><center>Kelas</center></th>
                                   <th width="15%"><center>Tarif Laborat</center></th>
                               </tr>
                               <?php 
                                  $laborat      = trim(isset($_POST['laborat']))?trim($_POST['laborat']):NULL;
                                  $laborat      = cleankar($laborat);
                                  $querylaborat = bukaquery("select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,jns_perawatan_lab.kelas from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where jns_perawatan_lab.status='1' and penjab.png_jawab like '%umum%' ".(isset($laborat)?" and (jns_perawatan_lab.nm_perawatan like '%$laborat%' or jns_perawatan_lab.kelas like '%$laborat%')":"")." order by jns_perawatan_lab.kelas");
                                  while($rsquerylaborat = mysqli_fetch_array($querylaborat)) {
                                      echo "<tr>
                                              <td align='left'>".$rsquerylaborat["nm_perawatan"]."</td>
                                              <td align='center'>".$rsquerylaborat["kelas"]."</td>
                                              <td align='center'>".($rsquerylaborat["total_byr"]>0?formatDuit($rsquerylaborat["total_byr"]):"")."</td>
                                            </tr>";
                                      $querydetaillaborat= bukaquery("select Pemeriksaan,biaya_item from template_laboratorium where kd_jenis_prw='$rsquerylaborat[0]' and biaya_item>0 order by urut");
                                      while($rsquerydetaillaborat=mysqli_fetch_array($querydetaillaborat)){
                                           echo "<tr>
                                                   <td align='left'>&nbsp;&nbsp;".$rsquerydetaillaborat["Pemeriksaan"]."</td>
                                                   <td align='center'>".$rsquerylaborat["kelas"]."</td>
                                                   <td align='center'>".formatDuit($rsquerydetaillaborat["biaya_item"])."</td>
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