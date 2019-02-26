		<!-- Content Header (Page header) -->
		<div class="padding">
			<section class="content">
				<div class="row">
    				<div class="col-md-12">

                        <div class="row">
                            <div class="col-md-2">
                                <button class="btn btn-info btn-block btn-xs" onclick="formAdd()"><i class="fa fa-plus"></i> Tambah Data</button>
                            </div>
                            <div class="col-md-2">
                                <button class="btn btn-danger btn-block btn-xs" disabled="true" id="btnDelete" onclick="formDelete()"><i class="fa fa-trash-o"></i> Hapus Data</button>
                            </div>
                            <div class="col-md-5">

                            </div>
                            <div class="col-md-3">
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ms_level_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th width="10" class="text-center"></th>
		                                <th width="10" class="text-center">No.</th>
		                                <th class="col-md-12">Nama Level</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		foreach ($r as $idrw => $rw) {
			                                echo '<tr style="cursor:pointer" ' .showValRec($rw) . '>';
			                                echo '<td class="text-center chk"><input type="checkbox" name="id_level[]" value="' . $rw->id_level . '"></td>';
			                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
			                                echo '<td class="tr_mod">' . $rw->nm_level. '</td>';
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
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ms_level_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ms_level_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
		                    </div>
		                    <div class="col-md-2 text-center">
		                        Total Data : <?php echo $jum; ?>
		                    </div>
                        </div>
                    
                    </div>
				</div>
			</section>

			<div class="modal fade animate" id="modalMod" style="display: none">
		        <div class="modal-dialog fade-left modal-lg">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <h4 class="modal-title">Manajemen Level</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ms_level_view/action/save'); ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
                                <input type="hidden" name="id_level">
                                <div class="form-group row">
                                    <label class="col-sm-2"><strong>Nama Level</strong></label>
                                    <div class="col-sm-10">
                                        <input type="text" name="nm_level" placeholder="Nama Level" class="form-control" required="">
                                    </div>
                                </div>
                                <?php  
                                foreach (akses_level() as $key => $value) {
                                   echo '<div class="form-group row">';
                                   echo '<label class="col-sm-2"><strong>'.$in[$key].'</strong></label>';
                                   echo '<div class="col-sm-10">';
                                    foreach ($value as $key1 => $value1) {
                                    	 echo '<label class="col-sm-12"><strong>'.$key1.'</strong></label><br>';
                                        foreach ($value1 as $key2 => $value2) {
                                        	echo '<label class="md-check">';
	                                        echo '<input type="checkbox" name="akses['.$key.']['.$key2.']" value="'.$key2.'" data-val="'.$key2.'"> <i class="indigo"></i> '. $value2 .'&nbsp;';
	                                        echo '</label><br>';
                                        }
                                    }
                                   echo '</div>';
                                   echo '</div>';
                                }
                                ?>                           
		                    </form>
		                </div>
		                <div class="modal-footer">
		                    <button type="submit" form="modForm" data-dismiss="modal" class="btn primary btn-sm"><i class="fa fa-arrow-circle-right"></i> Cancel</button>&nbsp;
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
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ms_level_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ms_level_edit');?>')) {
		                var tr = $(this).parent();

		                $('#modalMod :input').val('');
		                $('#modalMod input[type="checkbox"]').prop('checked', false);
		                $('#modalMod [name="nm_level"]').val(tr.data('nm_level'));

		                $('#modalMod [name*="akses"]').each(function () {
		                    if (tr.data('akses').toString().indexOf($(this).data('val')) >= 0) {//found
		                        $(this).prop('checked', true);
		                    }
		                });

		                $('#modalMod [name="id_level"]').val(tr.data('id_level'));

		                $('#modalMod').modal();
		            }
		        });

		        $('[name="id_level[]"]').click(function () {
		            //alert('dfds');
		            if ($('[name="id_level[]"]:checked').length > 0) {
		                $('#btnDelete').removeAttr('disabled');
		            } else {
		                $('#btnDelete').attr('disabled', 'true');
		            }
		        });
		    });

		    function formAdd() {
		        if (checkPrivilege(<?php echo $this->session->userdata('id_level');?>, '<?php echo aksesName('ms_level_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="nm_level"]').prop('required', true);
		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege(<?php echo $this->session->userdata('id_level');?>, '<?php echo aksesName('ms_level_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id_level[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ms_level_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }
		</script>