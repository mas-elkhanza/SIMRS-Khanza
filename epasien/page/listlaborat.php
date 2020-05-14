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
                           <table width="100%" border='0' align="center">
                               <tr class="head">
                                  <td width="15%" align="right"><label for="laborat">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="69%"><input name="laborat" id="laborat" class="form-control" value="" size="65" maxlength="250" /></td>
                                  <td width="15%" align="left">&nbsp;<input name="BtnKamar" type=submit class="btn btn-warning" value="Cari"></td>
                               </tr>
                           </table>
                         </form>
                      </div>
                      <div class="news-thumb wow fadeInUp" data-wow-delay="0.5s">
                         <table class="table table-hover" >
                            <tr>
                                <th width="60%"><center>Nama Pemeriksaan</center></th>
                                <th width="20%"><center>Kelas</center></th>
                                <th width="20%"><center>Tarif Laborat</center></th>
                            </tr>
                            <?php 
                               $laborat      = trim(isset($_POST['laborat']))?trim($_POST['laborat']):NULL;
                               $laborat      = validTeks($laborat);
                               $querylaborat = bukaquery("select jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,jns_perawatan_lab.kelas from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where jns_perawatan_lab.status='1' and penjab.png_jawab like '%umum%' ".(isset($laborat)?" and (jns_perawatan_lab.nm_perawatan like '%$laborat%' or jns_perawatan_lab.kelas like '%$laborat%')":"")." order by jns_perawatan_lab.nm_perawatan");
                               while($rsquerylaborat = mysqli_fetch_array($querylaborat)) {
                                   echo "<tr>
                                           <td align='left'>".$rsquerylaborat["nm_perawatan"]."</td>
                                           <td align='center'>".$rsquerylaborat["kelas"]."</td>
                                           <td align='center'>".$rsquerylaborat["total_byr"]."</td>
                                         </tr>";
                               }
                           ?>
                        </table>
                     </div>
                </div>
           </div>
      </div>
 </section>