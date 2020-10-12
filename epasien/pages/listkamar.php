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
                          <h2>Kamar</h2>
                     </div>
                </div>
                       
                <div class="col-md-12 col-sm-12">
                     <div class="news-thumb wow fadeInUp" data-wow-delay="0.3s">
                         <form id="cariKamar" name="frmCariKamar" method="post" action="" enctype=multipart/form-data>
                           <table width="100%" border='0' align="center">
                               <tr class="head">
                                  <td width="20%" align="right"><label for="kamar">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="60%"><input name="kamar" type="text" id="kamar" pattern="[a-zA-Z0-9, ./@_]{1,65}" title=" a-zA-Z0-9, ./@_ (Maksimal 65 karakter)" class="form-control" value="" size="65" maxlength="250" autocomplete="off" autofocus/></td>
                                  <td width="19%" align="left">&nbsp;<input name="BtnKamar" type=submit class="btn btn-warning" value="Cari" /></td>
                               </tr>
                           </table>
                         </form>
                         <div class="table-responsive">
                            <table class="table table-hover" >
                               <tr>
                                   <th width="15%"><center>Nomer Bed</center></th>
                                   <th width="40%"><center>Nama Kamar</center></th>
                                   <th width="15%"><center>Kelas</center></th>
                                   <th width="15%"><center>Tarif Kamar</center></th>
                                   <th width="15%"><center>Status Kamar</center></th>
                               </tr>
                               <?php 
                                  $kamar      = trim(isset($_POST['kamar']))?trim($_POST['kamar']):NULL;
                                  $kamar      = cleankar($kamar);
                                  $querykamar = bukaquery("select kamar.kd_kamar,bangsal.nm_bangsal,kamar.kelas,kamar.trf_kamar,kamar.status from bangsal inner join kamar on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.statusdata='1' ".(isset($kamar)?" and (kamar.kd_kamar like '%$kamar%' or bangsal.nm_bangsal like '%$kamar%' or kamar.kelas like '%$kamar%' or kamar.status like '%$kamar%')":"")." order by kamar.kelas");
                                  while($rsquerykamar = mysqli_fetch_array($querykamar)) {
                                      echo "<tr>
                                              <td align='left'>".$rsquerykamar["kd_kamar"]."</td>
                                              <td align='left'>".$rsquerykamar["nm_bangsal"]."</td>
                                              <td align='center'>".$rsquerykamar["kelas"]."</td>
                                              <td align='center'>".formatDuit($rsquerykamar["trf_kamar"])."</td>
                                              <td align='center'>".$rsquerykamar["status"]."</td>
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