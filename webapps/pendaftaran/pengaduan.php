<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : index.php
* Description : Pengaduan page
* Licence under GPL
***/

include_once ('layout/header.php');

?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>PENGADUAN PASIEN<h2>
            </div>


            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="body">
                            <div class="row clearfix">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <textarea id="screen" rows="14" class="form-control no-resize" placeholder="Kosong..."></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="col-lg-10 col-md-8 col-sm-8 col-xs-8">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" id="message" class="form-control" placeholder="Ketik disini...">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
                                    <button type="button" id="button" class="btn btn-primary btn-lg m-l-15 waves-effect">SEND</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>


<?php include_once ('layout/footer.php'); ?>
