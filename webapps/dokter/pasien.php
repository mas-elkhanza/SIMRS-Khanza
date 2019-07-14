<?php

/***
* SIMRS Khanza Lite from version 0.1 Beta
* About : Porting of SIMRS Khanza by Windiarto a.k.a Mas Elkhanza as web and mobile app.
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
* Licence under GPL
***/

$title = 'Data Pasien';
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
                                DATA PASIEN
                            </h2>
                        </div>
                        <div class="body responsive">
                            <table id="pasien" class="table table-bordered table-striped table-hover display nowrap js-exportable" width="100%">
                                <thead>
                                    <tr>
                                        <th>No. RM</th>
                                        <th>Nama Pasien</th>
                                        <th>No KTP/SIM</th>
                                        <th>J.K</th>
                                        <th>Tmp. Lahir</th>
                                        <th>Tgl. Lahir</th>
                                        <th>Alamat</th>
                                        <th>No. Tlp</th>
                                        <th>Pekerjaan</th>
                                        <th>Stts. Nikah</th>
                                        <th>Tgl. Daftar</th>
                                        <th>Umur</th>
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
        </div>
    </section>

<?php
include_once('layout/footer.php');
?>

<script>
  $('#pasien').dataTable( {
        "bInfo" : false,
      	"scrollX": true,
        "processing": true,
        "serverSide": true,
        "responsive": true,
        //"responsive": {
        //    "details": {
        //        "display": $.fn.dataTable.Responsive.display.modal( {
        //            "header": function ( row ) {
        //                var data = row.data();
        //                return '<h3>Detail Pasien</h3>';
        //            }
        //        } ),
        //        "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
        //    }
        //},
        "oLanguage": {
            "sProcessing":   "Sedang memproses...",
            "sLengthMenu":   "Tampilkan _MENU_ entri",
            "sZeroRecords":  "Tidak ditemukan data yang sesuai",
            "sInfo":         "Menampilkan _START_ sampai _END_ dari _TOTAL_ entri",
            "sInfoEmpty":    "Menampilkan 0 sampai 0 dari 0 entri",
            "sInfoFiltered": "(disaring dari _MAX_ entri keseluruhan)",
            "sInfoPostFix":  "",
            "sSearch":       "Cari:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "«",
                "sPrevious": "‹",
                "sNext":     "›",
                "sLast":     "»"
            }
        },
        "order": [[ 0, "asc" ]],
        "ajax": "includes/pasien.php"
    } );
  </script>

