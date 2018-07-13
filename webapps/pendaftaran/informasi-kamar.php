<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : informasi-kamar.php
* Description : Room information
* Licence under GPL
***/

include_once ('layout/header.php');

?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>INFO KAMAR & BANGSAL</h2>
            </div>

            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                <?php echo $dayList[$day].", ".date('d')." ".$bulanList[$bulan]." ".date('Y'); ?>
                            </h2>
                        </div>
                        <div class="body">
                            <table id="informasi_kamar" class="table table-bordered table-striped table-hover display nowrap" width="100%">
                                <thead>
                                    <tr>
                                        <th>Kelas Kamar</th>
                                        <th>Jumlah Bed</th>
                                        <th>Bed Terisi</th>
                                        <th>Bed Kosong</th>
                                    </tr>
                                </thead>
                            </table>
                            <table id="informasi_bangsal" class="table table-bordered table-striped table-hover display nowrap" width="100%">
                                <thead>
                                    <tr>
                                        <th>Nama Kamar</th>
                                        <th>Jumlah Bed</th>
                                        <th>Bed Terisi</th>
                                        <th>Bed Kosong</th>
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
