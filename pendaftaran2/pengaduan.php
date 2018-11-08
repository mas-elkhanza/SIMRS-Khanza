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
                    <!-- <div class="card"> -->
                        <div class="body">
                            <div class="row clearfix">
                                <div class="col-sm-12 col-md-12 col-xs-12">
                                    <!-- <div class="chat_box col-lg-2 col-xs-12 col-md-3 col-sm-9">
                                        <div class="chat_head col-xs-12 col-md-12 col-sm-12">Chatbox</div>
                                        <div class="chat_body col-xs-12 col-md-12 col-sm-12">
                                            <div class="user">
                                                <ul>
                                                    <li id="adm1">Admin Rsud 1</li>
                                                    <li id="adm2">Admin Rsud 1</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div> -->

                                    <div class="msg_box col-xs-12 col-md-2 col-sm-12" style="">
                                        <div class="msg_head">Admin Rsud 1
                                            <div class="">-</div>
                                            <div class="close">x</div>
                                        </div>
                                        <div class="msg_wrap">
                                            <div class="msg_body">
                                                <div class="msg_a">This is from A</div>
                                                <div class="msg_b">This from B</div>
                                                <div class="msg_insert"></div>
                                            </div>
                                            <div class="msg_footer">
                                                <textarea rows="2" class="msg_input col-sm-12 col-md-12 col-xs-12" placeholder="Press Enter to send Message"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <!-- </div> -->
                </div>
            </div>
        </div>
    </section>


<?php include_once ('layout/footer.php'); ?>
