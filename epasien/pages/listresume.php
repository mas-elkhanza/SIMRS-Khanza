<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    $norawat = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $norawat = json_decode(encrypt_decrypt($norawat,"d"),true); 
    if (isset($norawat["norawat"])) {
        $norawat = $norawat["norawat"];
        $queryresume = bukaquery("select resume_pasien.kd_dokter,dokter.nm_dokter,resume_pasien.kondisi_pulang,resume_pasien.keluhan_utama, 
            resume_pasien.jalannya_penyakit,resume_pasien.pemeriksaan_penunjang,resume_pasien.hasil_laborat,resume_pasien.diagnosa_utama,resume_pasien.kd_diagnosa_utama, 
            resume_pasien.diagnosa_sekunder,resume_pasien.kd_diagnosa_sekunder,resume_pasien.diagnosa_sekunder2,resume_pasien.kd_diagnosa_sekunder2, 
            resume_pasien.diagnosa_sekunder3,resume_pasien.kd_diagnosa_sekunder3,resume_pasien.diagnosa_sekunder4,resume_pasien.kd_diagnosa_sekunder4, 
            resume_pasien.prosedur_utama,resume_pasien.kd_prosedur_utama,resume_pasien.prosedur_sekunder,resume_pasien.kd_prosedur_sekunder, 
            resume_pasien.prosedur_sekunder2,resume_pasien.kd_prosedur_sekunder2,resume_pasien.prosedur_sekunder3,resume_pasien.kd_prosedur_sekunder3, 
            resume_pasien.obat_pulang from resume_pasien inner join dokter on resume_pasien.kd_dokter=dokter.kd_dokter 
            where resume_pasien.no_rawat='$norawat'");
        if($rsqueryresume = mysqli_fetch_array($queryresume)) {
           echo "<div class='block-header'>
                    <h2><center>RESUME PERAWATAN<br>NO.$norawat</center></h2>
                </div>
                <div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <div class='table-responsive'>
                                    <table class='table table-hover dataTable'>
                                        <tr>
                                           <td width='30%'>Dokter DPJP</td>
                                           <td width='70%'> : ".$rsqueryresume["nm_dokter"]."</td>
                                        </tr>
                                        <tr>
                                           <td>Kondisi Pulang</td>
                                           <td> : ".$rsqueryresume["kondisi_pulang"]."</td>
                                        </tr>
                                   </table>
                                </div>
                                <div class='panel-group' id='accordion_4' role='tablist' aria-multiselectable='true'>
                                    <div class='panel panel-col-pink'>
                                        <div class='panel-heading' role='tab' id='headingTwo_4'>
                                            <h4 class='panel-title'>
                                                <a class='collapsed' role='button' data-toggle='collapse' data-parent='#accordion_4' href='#collapseOne_4' aria-expanded='false' aria-controls='collapseOne_4'>
                                                    Keluhan Utama Riwayat Penyakit Yang Positif
                                                </a>
                                            </h4>
                                        </div>
                                        <div id='collapseOne_4' class='panel-collapse collapse' role='tabpanel' aria-labelledby='headingOne_4'>
                                            <div class='panel-body'>
                                                ".str_replace("\n","<br>",$rsqueryresume["keluhan_utama"])."
                                            </div>
                                        </div>
                                    </div>
                                    <div class='panel panel-col-pink'>
                                        <div class='panel-heading' role='tab' id='headingTwo_4'>
                                            <h4 class='panel-title'>
                                                <a class='collapsed' role='button' data-toggle='collapse' data-parent='#accordion_4' href='#collapseTwo_4' aria-expanded='false' aria-controls='collapseTwo_4'>
                                                    Jalannya Penyakit Selama Perawatan
                                                </a>
                                            </h4>
                                        </div>
                                        <div id='collapseTwo_4' class='panel-collapse collapse' role='tabpanel' aria-labelledby='headingTwo_4'>
                                            <div class='panel-body'>
                                                ".str_replace("\n","<br>",$rsqueryresume["jalannya_penyakit"])."
                                            </div>
                                        </div>
                                    </div>
                                    <div class='panel panel-col-pink'>
                                        <div class='panel-heading' role='tab' id='headingThree_4'>
                                            <h4 class='panel-title'>
                                                <a class='collapsed' role='button' data-toggle='collapse' data-parent='#accordion_4' href='#collapseThree_4' aria-expanded='false' aria-controls='collapseThree_4'>
                                                    Pemeriksaan Penunjang Yang Positif
                                                </a>
                                            </h4>
                                        </div>
                                        <div id='collapseThree_4' class='panel-collapse collapse' role='tabpanel' aria-labelledby='headingThree_4'>
                                            <div class='panel-body'>
                                                ".str_replace("\n","<br>",$rsqueryresume["pemeriksaan_penunjang"])."
                                            </div>
                                        </div>
                                    </div>
                                    <div class='panel panel-col-pink'>
                                        <div class='panel-heading' role='tab' id='headingFour_4'>
                                            <h4 class='panel-title'>
                                                <a class='collapsed' role='button' data-toggle='collapse' data-parent='#accordion_4' href='#collapseFour_4' aria-expanded='false' aria-controls='collapseFour_4'>
                                                    Hasil Laboratorium Yang Positif
                                                </a>
                                            </h4>
                                        </div>
                                        <div id='collapseFour_4' class='panel-collapse collapse' role='tabpanel' aria-labelledby='headingFour_4'>
                                            <div class='panel-body'>
                                                ".str_replace("\n","<br>",$rsqueryresume["hasil_laborat"])."
                                            </div>
                                        </div>
                                    </div>
                                    <div class='panel panel-col-pink'>
                                        <div class='panel-heading' role='tab' id='headingFive_4'>
                                            <h4 class='panel-title'>
                                                <a class='collapsed' role='button' data-toggle='collapse' data-parent='#accordion_4' href='#collapseFive_4' aria-expanded='false' aria-controls='collapseFive_4'>
                                                    Diagnosa Akhir
                                                </a>
                                            </h4>
                                        </div>
                                        <div id='collapseFive_4' class='panel-collapse collapse' role='tabpanel' aria-labelledby='headingFive_4'>
                                            <div class='panel-body'>
                                                <div class='table-responsive'>
                                                    <table width='100%'  class='table table-hover dataTable'>
                                                       <tr align='left'>
                                                           <td width='20%'>Diagnosa Utama</td><td width='70%'>:&nbsp;".$rsqueryresume["diagnosa_utama"]."</td><td width='10%'>&nbsp;".$rsqueryresume["kd_diagnosa_utama"]."</td>
                                                       </tr>
                                                       <tr align='left'>
                                                           <td width='20%'>Diagnosa Sekunder 1</td><td width='70%'>:&nbsp;".$rsqueryresume["diagnosa_sekunder"]."</td><td width='10%'>&nbsp;".$rsqueryresume["kd_diagnosa_sekunder"]."</td>
                                                       </tr>
                                                       <tr align='left'>
                                                           <td width='20%'>Diagnosa Sekunder 2</td><td width='70%'>:&nbsp;".$rsqueryresume["diagnosa_sekunder2"]."</td><td width='10%'>&nbsp;".$rsqueryresume["kd_diagnosa_sekunder2"]."</td>
                                                       </tr>
                                                       <tr align='left'>
                                                           <td width='20%'>Diagnosa Sekunder 3</td><td width='70%'>:&nbsp;".$rsqueryresume["diagnosa_sekunder3"]."</td><td width='10%'>&nbsp;".$rsqueryresume["kd_diagnosa_sekunder3"]."</td>
                                                       </tr>
                                                       <tr align='left'>
                                                           <td width='20%'>Diagnosa Sekunder 4</td><td width='70%'>:&nbsp;".$rsqueryresume["diagnosa_sekunder4"]."</td><td width='10%'>&nbsp;".$rsqueryresume["kd_diagnosa_sekunder4"]."</td>
                                                       </tr>
                                                       <tr align='left'>
                                                           <td width='20%'>Prosedur Utama</td><td width='70%'>:&nbsp;".$rsqueryresume["prosedur_utama"]."</td><td width='10%'>&nbsp;".$rsqueryresume["kd_prosedur_utama"]."</td>
                                                       </tr>
                                                       <tr align='left'>
                                                           <td width='20%'>Prosedur Sekunder 1</td><td width='70%'>:&nbsp;".$rsqueryresume["prosedur_sekunder"]."</td><td width='10%'>&nbsp;".$rsqueryresume["kd_prosedur_sekunder"]."</td>
                                                       </tr>
                                                       <tr align='left'>
                                                           <td width='20%'>Prosedur Sekunder 2</td><td width='70%'>:&nbsp;".$rsqueryresume["prosedur_sekunder2"]."</td><td width='10%'>&nbsp;".$rsqueryresume["kd_prosedur_sekunder2"]."</td>
                                                       </tr>
                                                       <tr align='left'>
                                                           <td width='20%'>Prosedur Sekunder 3</td><td width='70%'>:&nbsp;".$rsqueryresume["prosedur_sekunder3"]."</td><td width='10%'>&nbsp;".$rsqueryresume["kd_prosedur_sekunder3"]."</td>
                                                       </tr>
                                                   </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class='panel panel-col-pink'>
                                        <div class='panel-heading' role='tab' id='headingSix_4'>
                                            <h4 class='panel-title'>
                                                <a class='collapsed' role='button' data-toggle='collapse' data-parent='#accordion_4' href='#collapseSix_4' aria-expanded='false' aria-controls='collapseSix_4'>
                                                    Obat-obatan Waktu Pulang/Nasihat
                                                </a>
                                            </h4>
                                        </div>
                                        <div id='collapseSix_4' class='panel-collapse collapse' role='tabpanel' aria-labelledby='headingSix_4'>
                                            <div class='panel-body'>
                                                ".str_replace("\n","<br>",$rsqueryresume["obat_pulang"])."
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <center><a href='index.php?act=RiwayatPeriksa&hal=RiwayatPeriksa' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                </div>";
        }else{
            echo "<div class='block-header'>
                    <h2><center>RESUME PERAWATAN</center></h2>
                </div>
                <div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf resume perawatan dengan No.Rawat $norawat belum keluar, masih menunggu dikeluarkan oleh dokter DPJP.</center><br/>
                                <center><a href='index.php?act=RiwayatPeriksa&hal=RiwayatPeriksa' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                </div>";
            JSRedirect2("index.php?act=RiwayatPeriksa",7);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>RESUME PERAWATAN</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf resume perawatan tidak ditemukan</center><br/>
                            <center><a href='index.php?act=RiwayatPeriksa&hal=RiwayatPeriksa' class='btn btn-danger waves-effect'>Kembali</a></center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=RiwayatPeriksa",4);
    }
?>
