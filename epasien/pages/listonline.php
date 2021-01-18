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
                          <h2>Konsultasi Online</h2>
                     </div>
                </div>
                       
                <div class="col-md-12 col-sm-12">
                     <div class="news-thumb wow fadeInUp" data-wow-delay="0.3s">
                         <form id="cariKamar" name="frmCariOnline" method="post" action="" enctype=multipart/form-data>
                           <table width="100%" border='0' align="center">
                               <tr class="head">
                                  <td width="20%" align="right"><label for="online">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="60%"><input name="online" type="text" id="online" pattern="[a-zA-Z0-9, ./@_]{1,65}" title=" a-zA-Z0-9, ./@_ (Maksimal 65 karakter)" class="form-control" value="" size="65" maxlength="250" autocomplete="off" autofocus/></td>
                                  <td width="19%" align="left">&nbsp;<input name="BtnOnline" type=submit class="btn btn-warning" value="Cari" /></td>
                               </tr>
                           </table>
                         </form>
                         <div class="table-responsive">
                            <table class="table table-hover" >
                               <tr>
                                   <th width="50%"><center>Nama Tarif</center></th>
                                   <th width="30%"><center>Unit/Poliklinik</center></th>
                                   <th width="20%"><center>Tarif</center></th>
                               </tr>
                               <?php 
                                  if(!isset($_SESSION["online"])){
                                      $dataonline  = "";
                                      $queryonline = bukaquery("select jns_perawatan.nm_perawatan,jns_perawatan.total_byrdr,poliklinik.nm_poli from jns_perawatan inner join penjab on penjab.kd_pj=jns_perawatan.kd_pj inner join poliklinik on poliklinik.kd_poli=jns_perawatan.kd_poli inner join set_tarif_online on set_tarif_online.kd_jenis_prw=jns_perawatan.kd_jenis_prw where jns_perawatan.status='1' and penjab.png_jawab like '%umum%' order by jns_perawatan.nm_perawatan");
                                      while($rsqueryonline = mysqli_fetch_array($queryonline)) {
                                           $dataonline=$dataonline.
                                                 "<tr>
                                                   <td align='left'>".$rsqueryonline["nm_perawatan"]."</td>
                                                   <td align='center'>".$rsqueryonline["nm_poli"]."</td>
                                                   <td align='center'>".formatDuit($rsqueryonline["total_byrdr"])."</td>
                                                 </tr>";
                                      }
                                      $_SESSION["online"]=$dataonline;
                                  }

                                  $online  = trim(isset($_POST['online']))?trim($_POST['online']):NULL;
                                  $online  = cleankar($online);
                                  if(!empty($online)){
                                      $queryonline = bukaquery("select jns_perawatan.nm_perawatan,jns_perawatan.total_byrdr,poliklinik.nm_poli from jns_perawatan inner join penjab on penjab.kd_pj=jns_perawatan.kd_pj inner join poliklinik on poliklinik.kd_poli=jns_perawatan.kd_poli inner join set_tarif_online on set_tarif_online.kd_jenis_prw=jns_perawatan.kd_jenis_prw where jns_perawatan.status='1' and penjab.png_jawab like '%umum%' and (jns_perawatan.nm_perawatan like '%$online%' or poliklinik.nm_poli like '%$online%') order by jns_perawatan.nm_perawatan");
                                      while($rsqueryonline = mysqli_fetch_array($queryonline)) {
                                           echo "<tr>
                                                   <td align='left'>".$rsqueryonline["nm_perawatan"]."</td>
                                                   <td align='center'>".$rsqueryonline["nm_poli"]."</td>
                                                   <td align='center'>".formatDuit($rsqueryonline["total_byrdr"])."</td>
                                                 </tr>";
                                      }
                                  }else{
                                      echo $_SESSION["online"];
                                  }    
                              ?>
                           </table> 
                         </div>
                     </div>
                </div>
           </div>
      </div>
 </section>