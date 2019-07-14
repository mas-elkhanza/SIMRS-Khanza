<?php
/***
* SIMRS Khanza Lite from version 0.1 Beta
* About : Porting of SIMRS Khanza by Windiarto a.k.a Mas Elkhanza as web and mobile app.
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
* Licence under GPL
***/

include_once('config.php');
include_once('layout/header.php');
?>

<audio autoplay>
      <source src="sound/bantuan.mp3" type="audio/mpeg">
    </audio>

    <section class="content">
        <div class="container-fluid" style="margin: 120px;">
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                PERTANYAAN YANG SERING DITANYAKAN
                            </h2>
                        </div>
                        <div class="body">
                            <div class="row clearfix">
                                <div class="col-xs-12 ol-sm-12 col-md-12 col-lg-12">
                                    <div class="panel-group" id="accordion_17" role="tablist" aria-multiselectable="true">
                                      
                                        <div class="panel">
                                            <div class="panel-heading" role="tab" id="headingOne_17">
                                                <h4 class="panel-title">
                                                    <a role="button" data-toggle="collapse" data-parent="#accordion_17" href="#collapseOne_17" aria-expanded="false" aria-controls="collapseOne_17">
                                                        Kapan waktu berkunjung dirawat inap ?
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseOne_17" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne_17">
                                                <div class="panel-body">
                                                  		-- Siang : pukul 13.00 – 15.00 Wita<br/>
														-- Malam : pukul 19.00 – 21.00 Wita
                                                </div>
                                            </div>
                                        </div>
                                      
                                        <div class="panel">
                                            <div class="panel-heading" role="tab" id="headingTwo_17">
                                                <h4 class="panel-title">
                                                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion_17" href="#collapseTwo_17" aria-expanded="false"
                                                       aria-controls="collapseTwo_17">
                                                        Apakah pendaftaran online bisa dilakukan dihari yang sama ?
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseTwo_17" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo_17">
                                                <div class="panel-body">
                                                    Bisa apabila sebelum jam 08.00 dihari yang sama.
                                                </div>
                                            </div>
                                        </div> 
                                  
                                     <div class="panel">
                                            <div class="panel-heading" role="tab" id="headingThree_17">
                                                <h4 class="panel-title">
                                                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion_17" href="#collapseThree_17" aria-expanded="false" aria-controls="collapseThree_17">
                                                        Berapa hari maksimal pendaftaran pendaftaran online bisa dilakukan sebelum hari berobat ?
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseThree_17" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree_17">
                                                <div class="panel-body">
                                                  		3 hari kedepan (misal mendaftar tanggal 1 Agustus 2018, bisa didaftarkan sampai tanggal 4 Agustus 2018)
                                                </div>
                                            </div>
                                        </div>
                        		                         	               
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                    <a href="javascript:history.go(-1)"><button type="button" class="btn bg-blue btn-block btn-lg waves-effect">KEMBALI KE DEPAN</button></a>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                </div>
            </div>

        </div>
    </section>

<?php
include_once('layout/footer.php');
?>