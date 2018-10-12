		<?php 
		global $SConfig;
		?>
		<!-- Content Header (Page header) -->
		<div class="padding">
			<section class="content">
				<button class="btn info" data-toggle="modal" data-target="#bottom"><i class="fa fa-filter"></i> Filter</button>
				<div class="row">
    				<div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-responsive table-striped table-hover table-bordered dark">
                        		<thead>
		                            <tr>
		                                <th style="width: 1rem;" class="text-center"></th>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th style="width: 50px;">No Reg</th>
		                                <th style="width: 100px;">No Rm</th>
		                                <th style="width: 175px;">Nama Pasien</th>
		                                <th style="width: 175px;">Dokter</th>
		                                <th style="width: 175px;">Poli</th>
		                                <th style="width: 175px;">Tgl Registrasi</th>
		                                <th style="width: 175px;">Cara Bayar</th>
		                                <th style="width: 175px;">Tgl Booking</th>
		                                <th style="width: 175px;">Waktu Datang</th>
		                                <th style="width: 100px;">Status</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		//print_r($_SESSION);
		                        		foreach ($r as $idrw => $rw) {
			                                echo '<tr style="cursor:pointer" ' .showValRec($rw) . '>';
			                                echo '<td class="text-center chk"><input type="checkbox" name="id[]" value="' . $rw->no_rkm_medis . '"></td>';
			                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
			                                echo '<td class="tr_mod">' . $rw->no_reg. '</td>';
			                                echo '<td class="tr_mod">' . $rw->no_rkm_medis. '</td>';
			                                echo '<td class="tr_mod">' . $rw->no_rkm_medis. '</td>';
			                                echo '<td class="tr_mod">' . $nm_dokter[$rw->kd_dokter]. '</td>';
			                                echo '<td class="tr_mod">' . $nm_poli[$rw->kd_poli]. '</td>';
			                                echo '<td class="tr_mod">' . $rw->tanggal_periksa. '</td>';
			                                echo '<td class="tr_mod">' . $nm_cabar[$rw->kd_pj]. '</td>';
			                                echo '<td class="tr_mod">' . tanggal_indo($rw->tanggal_booking,true). ' - '.$rw->jam_booking.'</td>';
			                                echo '<td class="tr_mod">' . $rw->waktu_kunjungan. '</td>';
			                                echo '<td class="tr_mod">';
			                                if ($rw->status == 'belum') {
			                                    echo '<label class="btn btn-success btn-xs btn-block"><i class="fa fa-check"></i> booking</label>';
			                                }else if($rw->status == 'cuti') {
			                                    echo '<label class="btn btn-danger btn-xs btn-block"><i class="fa fa-times"></i> Batal Cuti Dokter</label>';
			                                }else if($rw->status == 'batal') {
			                                    echo '<label class="btn btn-danger btn-xs btn-block"><i class="fa fa-times"></i> Batal</label>';
			                                }else if($rw->status == 'terdaftar') {
			                                    echo '<label class="btn btn-danger btn-xs btn-block"><i class="fa fa-times"></i> Terdaftar</label>';
			                                }
			                                echo '</td>';
			                                echo '</tr>';
			                            }

			                            if (count($r) == 0) {
			                                echo '<tr><td colspan="100%" style="text-align:center">There\'s no login log</td></tr>';
			                            }
		                        	?>
		                        </tbody>
                        	</table>
                        </div>

                        <div class="row">
	                    	<?php  
	                    	$p = $p ? $p + 1 : 1;

		                    $paging = array();
		                    for ($i = 1; $i <= ceil($jum / $l); $i++) {
		                        $paging[$i] = $i;
		                    }
	                    	?>

	                    	<div class="col-md-3 text-left">
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ad_pb_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ad_pb_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
		                    </div>
		                    <div class="col-md-2 text-center">
		                        Total Data : <?php echo $jum; ?>
		                    </div>
                        </div>
                    
                    </div>
				</div>
			</section>

			<div class="modal fade animate" id="modalMod" style="display: none">
		        <div class="modal-dialog fade-left">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <h4 class="modal-title">Manajemen Tag</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ad_pb_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="id">
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-4 control-label">Nama Tag</label>

		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="nm_tag" placeholder="Nama tag" required="true">
		                            </div>
		                        </div>
		                    </form>
		                </div>
		                <div class="modal-footer">
		                    <button type="submit" form="modForm" class="btn primary btn-sm"><i class="fa fa-arrow-circle-right"></i> Save</button>
		                </div>
		                </form>
		            </div>
		            <!-- /.modal-content -->
		        </div>
		        <!-- /.modal-dialog -->
		    </div>

		    <div class="modal" id="bottom">
				<div class="bottom white b-b" style="height: auto;">
				<div class="row">
	            	<div class="col-md-12 col-xs-12 col-sm-12">
	            		<div class="box">
	                		<div class="box-header dark">
	                			<h2><a data-dismiss="modal" class="pull-right text-muted text-lg p-a-sm m-r-sm">&times;</a> Filter Pasien Booking</h2>
	                			<small>Silahkan gunakan fitur untuk memfilter data pada booking pasien...</small>
	                		</div>
	                		<div class="box-divider m-0"></div>
	                		<div class="box-body light-green-100">
	                			<form method="post" action="<?php echo base_url('Ad_pb_view/action/view');?>" enctype="multipart/form-data">
	                			<div class="col-md-12 col-sm-12 col-xs-12">
	                    			<div class="row">
	                    				<div class="col-md-6 col-sm-12 col-xs-12">
	                        				<div class="row">
	                        					<div class="col-md-12  col-sm-12 col-xs-12">
					                            	<label>Pilih Poli</label>
					                            	<?php
					                                echo htmlSelectFromArray($nm_poli, 'name="kd_poli" id="kd_poli" style="width:100%; line-height:1.25;" class="form-control select2"', true);
					                              	?>
					                            </div>
					                            <div class="col-md-12  col-sm-12 col-xs-12">
													<label>Pilih Dokter</label>
													<select id="kd_dokter" class="form-control select2" style="width:100%;" name="kd_dokter">
													</select>
					                            </div>
		                        				<div class="col-md-12  col-sm-12 col-xs-12">
					                            	<label>Status</label>
					                            	<?php
					                                echo htmlSelectFromArray($SConfig->_status_booking, 'name="sts_booking" id="sts_booking" style="width:100%;" class="form-control select2"', true);
					                              	?>
					                            </div>
					                        </div>
					                    </div>
					                    <div class="col-md-6 col-sm-12 col-xs-12">
					                        <div class="row">
					                        	<div class="col-md-12 col-sm-12 col-xs-12">
													<label>Tanggal Awal</label>
													<input type="date" name="tgl_awal_periksa" id="tgl_awal_periksa" class="form-control input-sm" >
					                            </div>
					                            <div class="col-md-12  col-sm-12 col-xs-12">
					                            	<label>Tanggal Akhir</label>
					                            	<input type="date" name="tgl_akhir_periksa" id="tgl_akhir_periksa" class="form-control input-sm" >
					                            </div>
					                            <div class="col-md-12 col-sm-12 col-xs-12">
													<label>Pencarian</label>
													<input type="text" name="cari" id="cari" class="form-control input-sm" >
					                            </div>
	                        				</div>
	                        			</div>
	                    			</div>
	                    		</div>
	                    			<div class="col-md-12 col-sm-12 col-xs-12">
	                    				<div class="row">
				                            <div class="col-md-1 col-sm-12 col-xs-12 ">
				                            	<button type="submit" value="proses" name="tombol" class="btn light-blue btn-sm form-control"><i class="fa fa-arrow-circle-right"></i> Proses</button>
				                            </div>
				                            <div class="col-md-1 col-sm-12 col-xs-12 ">
				                            	<button type="submit" value="excel" name="tombol" class="btn light-blue btn-sm form-control"><i class="fa fa-file-excel-o"></i> Unduh</button>
				                            </div>
				                            <div class="col-md-1 col-sm-12 col-xs-12 ">
				                            	<a data-dismiss="modal" class="btn btn-fw danger btn-sm form-control"><i class="fa fa-close"></i> Tutup</a>
				                            </div>
	                    				</div>
	                    			</div>
	                			</form>
	                		</div>
	                	</div>
	            	</div>
	            </div>
			</div>
		</div>

		</div>
		<!-- end/Content Header (Page Header) -->
		<script>
			var link_url = '<?php echo base_url('ad_pb_view/action');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_pb_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	$('#kd_poli').change(function(){
			    	var kd_poli = $(this).val();
			    	$.ajax({
			    		type : 'post',
			    		url : link_url+'/get_dokter',
			    		data : 'kd_poli='+kd_poli,
			    		success : function(data)
			    		{	
						    $('#kd_dokter').html(data);
			    		}
			    	});
			    });

		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>','<?php echo aksesName('ad_pb_edit');?>')) {
		                var tr = $(this).parent();

		                $('#modalMod :input').val('');
		                $('#modalMod [name="id"]').val(tr.data('id'));
		                $('#modalMod [name="nm_tag"]').val(tr.data('nm_tag'));

		                $('.sm-war-kosong').show();

		                $('#modalMod').modal();
		            }
		        });

		        $('[name="id[]"]').click(function () {
		            //alert('dfds');
		            if ($('[name="id[]"]:checked').length > 0) {
		                $('#btnDelete').removeAttr('disabled');
		            } else {
		                $('#btnDelete').attr('disabled', 'true');
		            }
		        });
		    });

		    function formAdd() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_pb_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="nm_tag"]').prop('required', true);
		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_pb_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ad_pb_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }
		</script>