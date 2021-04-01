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
                          <h2>Operasi</h2>
                     </div>
                </div>
                       
                <div class="col-md-12 col-sm-12">
                     <div class="news-thumb wow fadeInUp" data-wow-delay="0.3s">
                         <form id="cariKamar" name="frmCariOperasi" method="post" action="" enctype=multipart/form-data>
                           <table width="100%" border='0' align="center">
                               <tr class="head">
                                  <td width="20%" align="right"><label for="operasi">Keyword</label></td>
                                  <td width="1%"><label for=":">&nbsp;:&nbsp;</label></td>
                                  <td width="60%"><input name="operasi" type="text" id="operasi" pattern="[a-zA-Z0-9, ./@_]{1,65}" title=" a-zA-Z0-9, ./@_ (Maksimal 65 karakter)" class="form-control" value="" size="65" maxlength="250" autocomplete="off" autofocus/></td>
                                  <td width="19%" align="left">&nbsp;<input name="BtnOperasi" type=submit class="btn btn-warning" value="Cari" /></td>
                               </tr>
                           </table>
                         </form>
                         <div class="table-responsive">
                            <table class="table table-hover" >
                               <tr>
                                   <th width="60%"><center>Paket Operasi</center></th>
                                   <th width="20%"><center>Kelas</center></th>
                                   <th width="20%"><center>Tarif Operasi</center></th>
                               </tr>
                               <?php 
                                  if(!isset($_SESSION["operasi"])){
                                      $dataoperasi  = "";
                                      $queryoperasi = bukaquery("select paket_operasi.nm_perawatan,(paket_operasi.operator1+paket_operasi.operator2+paket_operasi.operator3+paket_operasi.asisten_operator1+paket_operasi.asisten_operator2+paket_operasi.asisten_operator3+paket_operasi.instrumen+paket_operasi.dokter_anak+paket_operasi.perawaat_resusitas+paket_operasi.alat+paket_operasi.dokter_anestesi+paket_operasi.asisten_anestesi+paket_operasi.asisten_anestesi2+paket_operasi.bidan+paket_operasi.bidan2+paket_operasi.bidan3+paket_operasi.perawat_luar+paket_operasi.sewa_ok+paket_operasi.akomodasi+paket_operasi.bagian_rs+paket_operasi.omloop+paket_operasi.omloop2+paket_operasi.omloop3+paket_operasi.omloop4+paket_operasi.omloop5+paket_operasi.sarpras+paket_operasi.dokter_pjanak+paket_operasi.dokter_umum) as total,paket_operasi.kelas from paket_operasi inner join penjab on penjab.kd_pj=paket_operasi.kd_pj where paket_operasi.status='1' and penjab.png_jawab like '%umum%' order by paket_operasi.kelas");
                                      while($rsqueryoperasi = mysqli_fetch_array($queryoperasi)) {
                                           $dataoperasi=$dataoperasi.
                                               "<tr>
                                                  <td align='left'>".$rsqueryoperasi["nm_perawatan"]."</td>
                                                  <td align='center'>".$rsqueryoperasi["kelas"]."</td>
                                                  <td align='center'>".formatDuit($rsqueryoperasi["total"])."</td>
                                               </tr>";
                                      }
                                      $_SESSION["operasi"]=$dataoperasi;
                                  }

                                  $operasi      = trim(isset($_POST['operasi']))?trim($_POST['operasi']):NULL;
                                  $operasi      = cleankar($operasi);
                                  if(!empty($operasi)){
                                      $queryoperasi = bukaquery("select paket_operasi.nm_perawatan,(paket_operasi.operator1+paket_operasi.operator2+paket_operasi.operator3+paket_operasi.asisten_operator1+paket_operasi.asisten_operator2+paket_operasi.asisten_operator3+paket_operasi.instrumen+paket_operasi.dokter_anak+paket_operasi.perawaat_resusitas+paket_operasi.alat+paket_operasi.dokter_anestesi+paket_operasi.asisten_anestesi+paket_operasi.asisten_anestesi2+paket_operasi.bidan+paket_operasi.bidan2+paket_operasi.bidan3+paket_operasi.perawat_luar+paket_operasi.sewa_ok+paket_operasi.akomodasi+paket_operasi.bagian_rs+paket_operasi.omloop+paket_operasi.omloop2+paket_operasi.omloop3+paket_operasi.omloop4+paket_operasi.omloop5+paket_operasi.sarpras+paket_operasi.dokter_pjanak+paket_operasi.dokter_umum) as total,paket_operasi.kelas from paket_operasi inner join penjab on penjab.kd_pj=paket_operasi.kd_pj where paket_operasi.status='1' and penjab.png_jawab like '%umum%' and (paket_operasi.nm_perawatan like '%$operasi%' or paket_operasi.kelas like '%$operasi%') order by paket_operasi.kelas");
                                      while($rsqueryoperasi = mysqli_fetch_array($queryoperasi)) {
                                           echo "<tr>
                                                   <td align='left'>".$rsqueryoperasi["nm_perawatan"]."</td>
                                                   <td align='center'>".$rsqueryoperasi["kelas"]."</td>
                                                   <td align='center'>".formatDuit($rsqueryoperasi["total"])."</td>
                                                 </tr>";
                                      }
                                  }else{
                                      echo $_SESSION["operasi"];
                                  }    
                              ?>
                           </table> 
                         </div>
                     </div>
                </div>
           </div>
      </div>
 </section>