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
      <source src="sound/welcome.mp3" type="audio/mpeg">
    </audio>

    <section class="content">
        <div class="container-fluid" style="margin: 120px;">
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="">
                    	<div class="block-header align-center" style="margin-top: 50px; margin-bottom: 100px;">
                			<p class="col-orange font-36">ANJUNGAN PASIEN MANDIRI</p>
            			</div>
                        <div class="body">
                          
                      		<div class="row">
                				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                    				<a href="pendaftaran.php" style="text-decoration: none;">
                    					<div class="info-box-2 bg-red hover-zoom-effect">
                        					<div class="icon">
                            					<i class="material-icons">assignment</i>
                        					</div>
                        					<div class="content">
                            					<div class="text">PENDAFTARAN</div>
                            					<div class="number">POLIKLINIK</div>
                                            </div>
                    					</div>
                  	 				</a>
                				</div>
                				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                    				<a href="jadwal-dokter.php" style="text-decoration: none;">
                    					<div class="info-box-2 bg-blue hover-zoom-effect">
                        					<div class="icon">
                            					<i class="material-icons">access_alarm</i>
                        					</div>
                        					<div class="content">
                            					<div class="text">JADWAL</div>
                            					<div class="number">DOKTER</div>
                        					</div>
                    					</div>
                   					</a>
                				</div>
                				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                    				<a href="informasi-kamar.php" style="text-decoration: none;">
                    					<div class="info-box-2 bg-green hover-zoom-effect">
                        					<div class="icon">
                            					<i class="material-icons">home</i>
                        					</div>
                        					<div class="content">
                            					<div class="text">INFORMASI</div>
                            					<div class="number">KAMAR</div>
                        					</div>
                    					</div>
                    				</a>
                				</div>
            				</div>
                          
            				<div class="row">
                				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                    				<a href="denah.php" style="text-decoration: none;">
                    					<div class="info-box-2 bg-indigo hover-zoom-effect">
                        					<div class="icon">
                            					<i class="material-icons">map</i>
                        					</div>
                        					<div class="content">
                            					<div class="text">DENAH</div>
                            					<div class="number">RUMAH SAKIT</div>
                        					</div>
                    					</div>
                                  	</a>
                				</div>
                				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                    				<a href="pengaduan.php" style="text-decoration: none;">
                    					<div class="info-box-2 bg-purple hover-zoom-effect">
                        					<div class="icon">
                            					<i class="material-icons">face</i>
                        					</div>
                        					<div class="content">
                            					<div class="text">FASILITAS</div>
                            					<div class="number">PENGADUAN</div>
                        					</div>
                    					</div>
                    				</a>
                				</div>
                				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                    				<a href="bantuan.php" style="text-decoration: none;">
                    					<div class="info-box-2 bg-pink hover-zoom-effect">
                        					<div class="icon">
                            					<i class="material-icons">help</i>
                        					</div>
                        					<div class="content">
                            					<div class="text">LAYANAN</div>
                            					<div class="number">BANTUAN</div>
                        					</div>
                    					</div>
                    				</a>
                				</div>
            				</div>
                          
            				<div class="block-header align-center" style="margin-top: 80px;">
                				<p class="col-red font-18">Sentuh tombol diatas untuk memilih layanan dan informasi.</p>
                				<p class="col-red font-18">Silahkan hubungi petugas jika anda mengalami kesulitan.</p>
            				</div>                          
                          
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

<?php
include_once('layout/footer.php');
?>
