<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : layout/footer.php
* Description : Footer
* Licence under GPL
***/

?>

    <div class="modal fade" id="ICTRSHD" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="smallModalLabel">Instalasi ICT RSHD Barabai</h4>
                </div>
                <div class="modal-body">
                    Ditetapkan sebagai Instalasi ICT dengan Surat Keputusan Direktur Rumah Sakit Umum Daerah H. Damanhuri pada tanggal 1 November 2017.
                    <ul style="list-style:none;margin-left:0;padding-left:0;"><br>
                        <li><b>Kepala Instalasi : <br>MasBas (drg. Faisol Basoro)</b></li><br>
                        <li>Anggota :
                            <ul style="list-style:none;margin-left:0;padding-left:0;">
                                <li>- Amat (Muhammad Ma'ruf, S.Kom)</li>
                                <li>- Aruf (Ma'ruf, S.Kom)</li>
                                <li>- Didi (Didi Andriawan, S.Kom)</li>
                                <li>- Adly (M. Adly Hidayat, S.Kom)</li>
                                <li>- Ridho (M. Alfian Ridho, S.Kom)</li>
                                <li>- Ijai (Zailani)</li>
                                <li>- Ina (Inarotut Darojah)</li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">TUTUP</button>
                </div>
            </div>
        </div>
    </div>

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

        $(document).ready(function() {

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


          	var table = $('#antrian').DataTable( {
				"ajax": "includes/antrian-pasien.php",
				"bPaginate":true,
				"bProcessing": true,
				"pageLength": 10,
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
				"columns": [
					{ mData: 'Antrian' },
          { mData: 'Status' },
					{ mData: 'Pasien' },
					{ mData: 'Dokter' }

				]
			});

			setInterval( function () { table.ajax.reload(null, false); }, 5000 );

    		function getAntrian() {
      			$.ajax({
      				url: 'includes/get-antrian.php',
      				type: 'post',
      				success: function(data) {
        				$('.getantrian').html(data);
      				}
      			});
    		};

            setInterval(function(){ getAntrian(); }, 1000);

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
                                return '<h3>Antrian Poliklinik</h3><br><h4>No. RM: <?php echo $_SESSION['username']; ?></h4>';
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

                        <?php
						$jumlah=array();
						$poli=array();
						$date = date("Y-m-d");
						$sql = "select poliklinik.nm_poli, count(*) as jumlah from reg_periksa INNER JOIN poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli WHERE reg_periksa.tgl_registrasi='$date' and poliklinik.nm_poli !='-' group by reg_periksa.kd_poli  order by count(*) desc ";
						$hasil=query($sql);
						while ($data = fetch_array ($hasil)){
                            $jumlah[]=intval($data['jumlah']);
                            $poli[]= $data['nm_poli'];
                        }
						?>

	<script type="text/javascript">
        Highcharts.chart('kunjungan', {
		    chart: {
			    type: 'column'
			},
            exporting: {
                enabled: false
            },
		    title: {
			    text: 'Grafik Kunjungan'
			},
			subtitle: {
				text: <?=json_encode('Tanggal : '.date("d-m-Y "));?>
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
    <script type="text/javascript">
function admSelectCheck(nameSelect)
{
    if(nameSelect){
        admOptionValue = document.getElementById("BPJS").value;
        if(admOptionValue == nameSelect.value){
            document.getElementById("admDivCheck").style.display = "block";
        }
        else{
            document.getElementById("admDivCheck").style.display = "none";
        }
    }
    else{
        document.getElementById("admDivCheck").style.display = "none";
    }
}

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#image_upload_preview').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#inputFile").change(function () {
        readURL(this);
    });

    function upload_rujukan(){
    document.getElementById("inputFile").click();
    }

    </script>

<script type="text/javascript">

function update()
{
    $.post("includes/pengaduan.php", {}, function(data){ $("#screen").val(data);});

    setTimeout('update()', 1000);
}

$(document).ready(

    function()
    {
        update();

        $("#button").click(function()
        {
            $.post("includes/pengaduan.php",
            {
                message: $("#message").val()
            },
            function(data)
            {
                $("#screen").val(data);
                $("#message").val("");
            });
        });
    }
);

</script>

</body>

</html>
