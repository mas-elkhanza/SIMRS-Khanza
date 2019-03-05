<?php

/***
* SIMRS Khanza Lite from version 0.1 Beta
* About : Porting of SIMRS Khanza by Windiarto a.k.a Mas Elkhanza as web and mobile app.
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
* Licence under GPL
***/

$title = 'Data ICD-10';
include_once('config.php');
include_once('layout/header.php');
include_once('layout/sidebar.php');
?>

    <section class="content">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                DATA ICD-10
                            </h2>
                        </div>
                        <div class="body">
                            <table id="icd10" class="table table-bordered table-striped table-hover display wrap js-exportable" width="100%">
                                <thead>
                                    <tr>
                                        <th>Kode / Deskripsi</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

<?php
include_once('layout/footer.php');
?>

<script>
  $('#icd10').dataTable( {
      "scrollX": true,
        "processing": true,
        "serverSide": true,
        "order": [[ 0, "asc" ]],
        "ajax": "includes/icd-10.php",
        "columnDefs": [
            {
                "render": function ( data, type, row ) {
                    return data +' ('+ row[1]+')';
                },
                "targets": 0
            },
            { "visible": false,  "targets": [ 1 ] }
        ]
    } );
  </script>
