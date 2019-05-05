<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : riwayat-periksa.php
* Description : Riwayat periksa page
* Licence under GPL
***/

include_once ('layout/header.php');

?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>RIWAYAT BOOKING</h2>
            </div>
            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="body">
                            <table id="riwayat_booking" class="table table-bordered table-striped table-hover display nowrap" width="100%">
                                <thead>
                                    <tr>
                                        <th>No. Antrian</th>
                                        <th>Waktu Datang</th>
                                        <th>No Rm</th>
                                        <th>Poliklinik</th>
                                        <th>Dokter</th>
                                        <th>Tanggal Periksa</th>
                                        <th>Cara Bayar</th>
                                        <th>Tanggal Booking</th>
                                        <th>Jam Booking</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Basic Examples -->
        </div>
    </section>

<?php include_once ('layout/footer.php'); ?>
