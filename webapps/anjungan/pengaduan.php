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
      <source src="sound/pengaduan.mp3" type="audio/mpeg">
    </audio>

    <section class="content">
        <div class="container-fluid" style="margin: 120px;">
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                PENGADUAN
                            </h2>
                        </div>
                        <div class="body table-responsive">

                        Pengaduan Langsung	: Ruang Unit Pengaduan Masyarakat di dekat pintu masuk lobby Rumah Sakit<br/>
						SMS/Telepon	: <br/>
						Kotak Saran	: Kotak saran yang berada di informasi, ruang perawatan inap, dan poliklinik rawat jalan<br/>
						Email : rsud.humas@bekasikab.go.id<br/>
       
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