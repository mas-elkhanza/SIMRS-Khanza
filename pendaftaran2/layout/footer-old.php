    <?php  
    echo $linkss = 'http://'.$_SERVER['HTTP_HOST']; 
    ?>
    <!-- Jquery Core Js -->
    <script src="plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Select Plugin Js -->
    <script src="plugins/bootstrap-select/js/bootstrap-select.js"></script>

    <!-- Slimscroll Plugin Js -->
    <script src="plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

    <!-- Jquery Validation Plugin Css -->
    <script src="plugins/jquery-validation/jquery.validate.js"></script>

    <!-- JQuery Steps Plugin Js -->
    <script src="plugins/jquery-steps/jquery.steps.js"></script>

    <!-- Sweet Alert Plugin Js -->
    <script src="plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="plugins/node-waves/waves.js"></script>

    <!-- Jquery DataTable Plugin Js -->
    <script src="plugins/jquery-datatable/jquery.dataTables.js"></script>
    <script src="plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>
    <script src="plugins/jquery-datatable/extensions/responsive/js/dataTables.responsive.min.js"></script>

    <!-- Jquery CountTo Plugin Js -->
    <script src="plugins/jquery-countto/jquery.countTo.js"></script>

    <!-- Highcharts Plugin Js -->
	<script src="plugins/highcharts/highcharts.js"></script>
    <script src="plugins/highcharts/exporting.js"></script>

    <!-- Autosize Plugin Js -->
    <script src="plugins/autosize/autosize.js"></script>

    <!-- Moment Plugin Js -->
    <script src="plugins/momentjs/moment.js"></script>

    <!-- Bootstrap Material Datetime Picker Plugin Js -->
    <script src="plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>

    <!-- Custom Js -->
    <script src="js/admin.js"></script>


	<script>
        var links = '<?php echo $linkss;?>/webapps/pendaftaran-ori/';

        /*$(document).on('change','select[name="kd_dokter"]', function(){
            var kd_dok = $(this).val();
            var tgl = $('#tgl_reg').val();
            var kd_poli = $('#limit_poli').val();
            
            
            if(kd_dok)
            {
                $.ajax({
                    type : 'post',
                    url : link + 'includes/cek_limit.php',
                    data : 'kd_poli='+kd_poli+'&kd_dok='+kd_dok+'&tgl='+tgl,
                    success : function(r)
                    {
                        json_data = JSON.parse(r);
                        
                        if(json_data.status == 'penuh')
                        {
                            $('#sbm').html('<a href="<?php //echo $linkss; ?>/webapps/pendaftaran-ori" class="btn btn-primary" >Kembali</a>');
                            $('#nmp').html(json_data.nm_poli);
                            $('#myModal').modal();
                        }
                        else if(json_data.status == 'cuti')
                        {
                            $('#sbm').html('<a href="<?php //echo $linkss; ?>/webapps/pendaftaran-ori" class="btn btn-primary" >Kembali</a>');
                            $('#nm_dokter').html(json_data.dokter);
                            $('#Cuti').modal();
                        }
                    }
                });
            }
        });*/

        $(document).ready(function() {

            // 2018-09-06
            // Insert Waktu Kunjungan pada file waktu-kunjungan.php
            var aks = $('#aksis').val();
            var no_rawats = $('#no_rawats').val();
            var kd_polis = $('#kd_polis').val();
            var kd_dokters = $('#kd_dokters').val();
            var tgl_registrasis = $('#tgl_registrasis').val();

            if(aks == 'selesai')
            {
                //alert(aks);
                $.ajax({
                    type : 'post',
                    url : link+'includes/waktu-kunjungan.php',
                    data : 'kd_polis='+kd_polis+'&no_rawats='+no_rawats+'&kd_dokters='+kd_dokters+'&tgl_registrasis='+tgl_registrasis,
                    success : function(r){
                        $('#waktu_kunjungan').html(r);
                    }
                });
            }

            var url = window.location.pathname; //sets the variable "url" to the pathname of the current window
            var activePage = url.substring(url.lastIndexOf('/') + 1); //sets the variable "activePage" as the substring after the last "/" in the "url" variable
            if($('.active').length > 0){
                $('.active').removeClass('active');//remove current active element if there's
            }

            $('.menu li a').each(function () { //looks in each link item within the primary-nav list
                var linkPage = this.href.substring(this.href.lastIndexOf('/') + 1); //sets the variable "linkPage" as the substring of the url path in each &lt;a&gt;
 
                if (activePage == linkPage) { //compares the path of the current window to the path of the linked page in the nav item
                    $(this).parent().addClass('active'); //if the above is true, add the "active" class to the parent of the &lt;a&gt; which is the &lt;li&gt; in the nav list
                }
            });


            $('#antrian_pasien').dataTable( {
        		"scrollX": true,
        		"pagingType": "full",
                "processing": true,
                "serverSide": true,
                "ordering": false,
                "responsive": {
                   "details": {
                       "display": $.fn.dataTable.Responsive.display.modal( {
                            "header": function ( row ) {
                                var data = row.data();
                                return '<h3>Antrian Poliklinik</h3><br>';
                            }
                        } ),
                        "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
                    }
                },
                "order": [[ 0, "desc" ]],
                "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
                "ajax": "includes/antrian-pasien.php?start=0",
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
                } 
            } );

            $('#riwayat_periksa').dataTable( {
        		"scrollX": true,
        		"pagingType": "full",
                "processing": true,
                "serverSide": true,
                "responsive": {
                   "details": {
                       "display": $.fn.dataTable.Responsive.display.modal( {
                            "header": function ( row ) {
                                var data = row.data();
                                return '<h3>Antrian Poliklinik</h3><br>';
                            }
                        } ),
                        "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
                    }
                },
                "order": [[ 0, "desc" ]],
                "ajax": "includes/riwayat-periksa.php",
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
                } 
            } );


            $('#jadwal_dokter').dataTable( {
        		"scrollX": true,
        		"pagingType": "full",
                "processing": true,
                "serverSide": true,
                "bFilter": false,
                "paging":   false,
                "ordering": false,
                "info":     false,
                "responsive": {
                   "details": {
                       "display": $.fn.dataTable.Responsive.display.modal( {
                            "header": function ( row ) {
                                var data = row.data();
                                return '<h3>Jadwal Dokter</h3><br>';
                            }
                        } ),
                        "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
                    }
                },
                "ajax": "includes/jadwal-dokter.php",
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
                } 
            } );

            $('#informasi_kamar').dataTable( {
        		"scrollX": true,
        		"pagingType": "full",
                "processing": true,
                "serverSide": true,
                "bFilter": false,
                "paging":   false,
                "ordering": false,
                "info":     false,
                "responsive": {
                   "details": {
                       "display": $.fn.dataTable.Responsive.display.modal( {
                            "header": function ( row ) {
                                var data = row.data();
                                return '<h3>Informasi Kamar</h3><br>';
                            }
                        } ),
                        "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
                    }
                },
                "ajax": "includes/informasi-kamar.php",
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
                } 
            } );

            $('#informasi_bangsal').dataTable( {
        		"scrollX": true,
        		"pagingType": "full",
                "processing": true,
                "serverSide": true,
                "bFilter": false,
                "paging":   false,
                "ordering": false,
                "info":     false,
                "responsive": {
                   "details": {
                       "display": $.fn.dataTable.Responsive.display.modal( {
                            "header": function ( row ) {
                                var data = row.data();
                                return '<h3>Informasi Bangsal</h3><br>';
                            }
                        } ),
                        "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
                    }
                },
                "ajax": "includes/informasi-bangsal.php",
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
                } 
            } );

            //Textare auto growth
            autosize($('textarea.auto-growth'));

            $('.datepicker').bootstrapMaterialDatePicker({
                format: 'YYYY-MM-DD',
                clearButton: true,
                weekStart: 1,
                time: false
            });

            $('.count-to').countTo();

        } );
	</script>

	<script type="text/javascript">
        Highcharts.chart('kunjungan', {
		    chart: {
			    type: 'area'
			},
		    title: {
			    text: 'Grafik Kunjungan'
			},
			subtitle: {
				text: <?=json_encode($dates);?>
			},
		    xAxis: {
		        categories: <?=json_encode($poli);?> ,
								
				title: {
				    enabled: false
				}
			},
			yAxis: {
				title: {
					text: 'Jumlah Pasien'
				},
				labels: {
					formatter: function () {
						return this.value;
					}
				}
			},
			tooltip: {
				split: true,
				valueSuffix: ''
			},
			plotOptions: {
				area: {
				stacking: 'normal',
				lineColor: '#666666',
				lineWidth: 1,
			    	marker: {
						lineWidth: 1,
						lineColor: '#666666'
					}
				}
			},
			series: [{
				name: 'Poliklinik dan Rawat Jalan',
				data: <?=json_encode($jumlah);?>
			}]
		});		
	</script>

</body>

</html>
