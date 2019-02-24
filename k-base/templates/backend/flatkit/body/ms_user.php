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
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ms_user_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th style="width: 1rem;" class="text-center"></th>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th>Username</th>
		                                <th>Nama Lengkap</th>
		                                <th style="width: 20%">Posisi</th>
		                                <th style="width: 20%">Akses Level</th>
		                                <th style="width: 15%;">Status</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		//print_r($_SESSION);
		                        		foreach ($r as $idrw => $rw) {
			                                echo '<tr style="cursor:pointer" ' .showValRec($rw) . '>';
			                                echo '<td class="text-center chk"><input type="checkbox" name="ID[]" value="' . $rw->ID . '"></td>';
			                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
			                                echo '<td class="tr_mod">' . $rw->username. '</td>';
			                                echo '<td class="tr_mod">' . $rw->nm_lengkap. '</td>';
			                                echo '<td class="tr_mod">' . @$jabatan[$rw->id_jabatan]. '</td>';
			                                echo '<td class="tr_mod">' . $level[$rw->id_level]. '</td>';
			                                echo '<td>';
			                                if ($rw->status == 'Y') {
			                                    echo '<label class="btn btn-success btn-xs btn-block"><i class="fa fa-check"></i> Active</label>';
			                                } else {
			                                    echo '<label class="btn btn-danger btn-xs btn-block"><i class="fa fa-times"></i> Not Active</label>';
			                                }
			                                echo '<div><a href="javascript:void(0)" onclick="openLog(' . $rw->ID . ')" style="cursor:pointer" class="btn btn-warning btn-xs btn-block"><i class="fa fa-list"></i> Login Log</a></div>';
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
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ms_user_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ms_user_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
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
		                    <h4 class="modal-title">Manajemen User</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ms_user_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="ID">
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Username</label>

		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="username" placeholder="Username" required="true">
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <label for="tup" class="col-sm-3 control-label">Password</label>

		                            <div class="col-sm-12">
		                                <input type="password" class="form-control input-sm" id="tup" name="password" placeholder="Password" required="true">
		                                <small class="sm-war-kosong">Please fill empty if don't want to change password</small>
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">NIK</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="nik" value="" placeholder="NIK" required="true">
		                            </div>
		                        </div>
		                        
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Full Name</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="nm_lengkap" value="" placeholder="Full Name" required="true">
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Profile Pict</label>
		                            <div class="col-sm-12">
		                                <input type="file" class="form-control input-sm" name="foto">
		                                <small class="sm-war-kosong">Please fill empty if don't want to change data</small>
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Position</label>
		                            <div class="col-sm-12">
		                                <?php
		                                echo htmlSelectFromArray($level, 'name="id_level" required="true" class="form-control input-sm"', true);
		                                ?>
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Jabatan</label>
		                            <div class="col-sm-12">
		                                <?php
		                                echo htmlSelectFromArray($jabatan, 'name="id_jabatan" required="true" class="form-control input-sm"', true);
		                                ?>
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Status</label>
		                            <div class="col-sm-12">
		                                <?php
		                                $status = status();
		                                echo htmlSelectFromArray($status, 'name="status" required="true" class="form-control input-sm"', true);
		                                ?>
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
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ms_user_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ms_user_edit');?>')) {
		                var tr = $(this).parent();

		                $('#modalMod :input').val('');
		                $('#modalMod [name="username"]').val(tr.data('username'));
		                $('#modalMod [name="nik"]').val(tr.data('nik'));
		                //$('#modalMod [name="kode"]').val(tr.data('kode'));
		                $('#modalMod [name="nm_lengkap"]').val(tr.data('nm_lengkap'));
		                $('#modalMod [name="id_level"]').val(tr.data('id_level'));
		                $('#modalMod [name="id_jabatan"]').val(tr.data('id_jabatan'));
		                $('#modalMod [name="status"]').val(tr.data('status'));
		                /*var datan = tr.data('device');
		                if (datan.toString().indexOf(',') < 0) {
		                    datan = datan + ',';
		                }
		                $('#modalMod [name="device[]"]').select2('val', datan.split(','));*/
		                $('#modalMod [name="ID"]').val(tr.data('id'));
		                $('#modalMod [name="password"]').prop('required', false);

		                $('.sm-war-kosong').show();

		                $('#modalMod').modal();
		            }
		        });

		        $('[name="ID[]"]').click(function () {
		            //alert('dfds');
		            if ($('[name="ID[]"]:checked').length > 0) {
		                $('#btnDelete').removeAttr('disabled');
		            } else {
		                $('#btnDelete').attr('disabled', 'true');
		            }
		        });
		    });

		    function formAdd() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ms_user_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="password"]').prop('required', true);
		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ms_user_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="ID[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ms_user_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }

		    function openLog(idne) {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ms_user_log');?>')) {
		            window.location = '<?php echo base_url('ms_user_view/log/')?>' + idne;
		        }
		    }
		</script>