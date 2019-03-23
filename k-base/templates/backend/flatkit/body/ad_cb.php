		<?php  
		global $SConfig;
		?>
		<!-- Content Header (Page header) -->
		<div class="padding">
			<section class="content">
				<div class="row">
    				<div class="col-md-12">

                        <div class="row">
                            <div class="col-md-2">
                                <!-- <button class="btn btn-info btn-block btn-xs" onclick="formAdd()"><i class="fa fa-plus"></i> Tambah Data</button> -->
                            </div>
                            <div class="col-md-2">
                                <!-- <button class="btn btn-danger btn-block btn-xs" disabled="true" id="btnDelete" onclick="formDelete()"><i class="fa fa-trash-o"></i> Hapus Data</button> -->
                            </div>
                            <div class="col-md-5">

                            </div>
                            <div class="col-md-3">
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ad_cabar_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th style="width: 1rem;" class="text-center"></th>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th style="width: 200px;">Kode Pnj</th>
		                                <th>Nama Pnj</th>
		                                <th style="width: 100px;">Limit</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		//print_r($_SESSION);
		                        		foreach ($r as $idrw => $rw) {
			                                echo '<tr style="cursor:pointer" ' .showValRec($rw) . '>';
			                                echo '<td class="text-center chk"><input type="checkbox" name="id[]" value="' . $rw->kd_pj . '"></td>';
			                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
			                                echo '<td class="tr_mod">' . $rw->kd_pj. '</td>';
			                                echo '<td class="tr_mod">' . $rw->png_jawab. '</td>';
			                                echo '<td class="tr_mod">' . $rw->limit_reg. '</td>';
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
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ad_cabar_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ad_cabar_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
		                    </div>
		                    <div class="col-md-2 text-center">
		                        Total Data : <?php echo $jum; ?>
		                    </div>
                        </div>
                    </div>
				</div>
			</section>

			<div class="modal fade animate" id="modalMod" style="display: none">
		        <div class="modal-dialog modal-lg fade-left">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <h4 class="modal-title">Administrasi Limit Pasien Online</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ad_cabar_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="id">
		                        <div class="col-md-12">
		                        	<div class="row">
		                        		<div class="col-md-6">
		                        			<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Kode Penanggung Jawab</label>

					                            <div class="col-sm-12">
					                               <input type="text" class="form-control input-sm" name="kd_pj" placeholder="Kode Dokter" disabled=""> 
					                            </div>
					                        </div>
		                        		</div>
		                        		<div class="col-md-6">
		                        			<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Nama Penanggung Jawab</label>
		                        				
		                        				<div class="col-sm-12">
		                        					<input type="text" class="form-control input-sm" name="png_jawab" placeholder="" disabled=""> 
		                        				</div>
		                        			</div>
		                        		</div>
		                        	</div>
		                        </div>

		                        <div class="col-md-12">
		                        	<div class="row">
	                        			<div class="col-md-6">
	                        				<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Limit Pendaftaran Pasien Online</label>

					                            <div class="col-sm-12">
					                                <input type="text" class="form-control input-sm" name="limit_reg" placeholder="Limit Registrasi Online">
					                            </div>
		                        			</div>
	                        			</div>
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

		</div>
		<!-- end/Content Header (Page Header) -->
		<script>
			var link = '<?php echo base_url('ad_cabar_view/action/');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_cabar_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>','<?php echo aksesName('ad_ld_edit');?>')) {
		                var tr = $(this).parent();
		                var idp	= tr.data('jenis');

		                $('#modalMod :input').val('');
		                $('#modalMod [name="id"]').val(tr.data('kd_pj'));
		                $('#modalMod [name="kd_pj"]').val(tr.data('kd_pj'));
		                $('#modalMod [name="png_jawab"]').val(tr.data('png_jawab'));
		                $('#modalMod [name="limit_reg"]').val(tr.data('limit_reg'));

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
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_ld_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="limit_reg"]').prop('required', true);
		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_ld_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ad_cabar_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }
		</script>