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
                          <h2>Poli/Unit Yang Kami Layani</h2>
                     </div>
                </div>
                       
                <div class="col-md-12 col-sm-12">
                     <div class="news-thumb wow fadeInUp" data-wow-delay="0.3s">
                         <form id="cariKamar" name="frmCariPoli" method="post" action="" enctype=multipart/form-data>
                           <table width="100%" border='0' align="center">
                               <tr class="head">
                                  <td width="20%" align="right"><label for="poli">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="60%"><input name="poli" type="text" id="poli" class="form-control" value="" size="65" maxlength="250" autocomplete="off" autofocus/></td>
                                  <td width="19%" align="left">&nbsp;<input name="BtnPoli" type=submit class="btn btn-warning" value="Cari" /></td>
                               </tr>
                           </table>
                         </form>
                         <div class="table-responsive">
                            <table class="table table-hover" >
                               <tr>
                                   <th width="50%"><center>Nama Poliklinik/Unit</center></th>
                                   <th width="25%"><center>Registrasi Pasien Baru</center></th>
                                   <th width="25%"><center>Registrasi Pasien Lama</center></th>
                               </tr>
                               <?php 
                                  $poli      = trim(isset($_POST['poli']))?trim($_POST['poli']):NULL;
                                  $poli      = cleankar($poli);
                                  $querypoli = bukaquery("select nm_poli,registrasi,registrasilama from poliklinik where status='1' ".(isset($poli)?" and (nm_poli like '%$poli%')":"")." order by nm_poli");
                                  while($rsquerypoli = mysqli_fetch_array($querypoli)) {
                                      echo "<tr>
                                              <td align='left'>".$rsquerypoli["nm_poli"]."</td>
                                              <td align='center'>".formatDuit($rsquerypoli["registrasi"])."</td>
                                              <td align='center'>".formatDuit($rsquerypoli["registrasilama"])."</td>
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