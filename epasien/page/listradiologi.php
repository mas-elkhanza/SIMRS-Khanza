<section id="news" data-stellar-background-ratio="2.5">
      <div class="container">
           <div class="row">
                <div class="col-md-12 col-sm-12">
                     <div class="section-title wow fadeInUp" data-wow-delay="0.1s">
                          <h2>Radiologi</h2>
                     </div>
                </div>
                       
                <div class="col-md-12 col-sm-12">
                     <div class="news-thumb wow fadeInUp" data-wow-delay="0.3s">
                         <form id="cariKamar" name="frmCariLaborat" method="post" action="" enctype=multipart/form-data>
                           <table width="100%" border='0' align="center">
                               <tr class="head">
                                  <td width="15%" align="right"><label for="radiologi">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="69%"><input name="radiologi" id="radiologi" class="form-control" value="" size="65" maxlength="250" /></td>
                                  <td width="15%" align="left">&nbsp;<input name="BtnRadiologi" type=submit class="btn btn-warning" value="Cari"></td>
                               </tr>
                           </table>
                         </form>
                      </div>
                      <div class="news-thumb wow fadeInUp" data-wow-delay="0.5s">
                         <table class="table table-hover" >
                            <tr>
                                <th width="60%"><center>Nama Pemeriksaan</center></th>
                                <th width="20%"><center>Kelas</center></th>
                                <th width="20%"><center>Tarif Radiologi</center></th>
                            </tr>
                            <?php 
                               $radiologi      = trim(isset($_POST['radiologi']))?trim($_POST['radiologi']):NULL;
                               $radiologi      = validTeks($radiologi);
                               $queryradiologi = bukaquery("select jns_perawatan_radiologi.nm_perawatan,jns_perawatan_radiologi.total_byr,jns_perawatan_radiologi.kelas from jns_perawatan_radiologi inner join penjab on penjab.kd_pj=jns_perawatan_radiologi.kd_pj where jns_perawatan_radiologi.status='1' and penjab.png_jawab like '%umum%' ".(isset($radiologi)?" and (jns_perawatan_radiologi.nm_perawatan like '%$radiologi%' or jns_perawatan_radiologi.kelas like '%$radiologi%')":"")." order by jns_perawatan_radiologi.nm_perawatan");
                               while($rsqueryradiologi = mysqli_fetch_array($queryradiologi)) {
                                   echo "<tr>
                                           <td align='left'>".$rsqueryradiologi["nm_perawatan"]."</td>
                                           <td align='center'>".$rsqueryradiologi["kelas"]."</td>
                                           <td align='center'>".formatDuit($rsqueryradiologi["total_byr"])."</td>
                                         </tr>";
                               }
                           ?>
                        </table>
                     </div>
                </div>
           </div>
      </div>
 </section>