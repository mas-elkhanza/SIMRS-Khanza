		<?php  
		$l = isset($_GET['l']) && $_GET['l'] ? $_GET['l'] : 20;
		$p = isset($_GET['p']) && $_GET['p'] ? $_GET['p'] - 1 : 0;
		$f = isset($_GET['f']) ? $_GET['f'] : '';
		$m = isset($_GET['m']) ? $_GET['m'] : '';
		?>
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
                        <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo $f; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo $m; ?>?f=' + this.value : '';">
                    </div>
                </div>

                <div class="box" style="margin-top: 15px">
                    <table class="table table-hover table-bordered">
                        <thead>
                            <tr>
                                <th style="width: 1rem;" class="text-center"></th>
                                <th style="width: 30px;" class="text-center">No.</th>
                                <th style="width: 50px">Code</th>
                                <th>Name</th>
                                <th style="width: 20%">Position</th>
                                <th style="width: 15%;">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                            /*$pq         = $p * $l;

                            $where = '';

                            if ($f) {
                                $where = 'nama like \'%' . $f . '%\' or username like \'%' . $f . '%\' or nik like \'%' . $f . '%\'';
                            }

                            $jum = $C->getRecordCount('user_account', $where);
                            $r   = $C->getRecordList('user_account', '*', '', '', $where, '', 'nama asc', $l . ' OFFSET ' . $pq);

                            $no = ($p * $l) + 1;

                            foreach ($r as $idrw => $rw) {
                                echo '<tr style="cursor:pointer" ' . $C->showValRec($rw) . '>';
                                echo '<td class="text-center chk"><input type="checkbox" name="id[]" value="' . $rw['id'] . '"></td>';
                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
                                echo '<td class="tr_mod">' . $rw['kode'] . '</td>';
                                echo '<td class="tr_mod">' . $rw['nama'] . '</td>';
                                echo '<td class="tr_mod">' . $jabatan[$rw['jabatan']] . '</td>';
                                echo '<td>';
                                if ($rw['status'] == 'Y') {
                                    echo '<label class="btn btn-success btn-xs btn-block"><i class="fa fa-check"></i> Active</label>';
                                } else {
                                    echo '<label class="btn btn-danger btn-xs btn-block"><i class="fa fa-times"></i> Not Active</label>';
                                }
                                echo '<div><a href="javascript:void(0)" onclick="openLog(' . $rw['id'] . ')" style="cursor:pointer" class="btn btn-warning btn-xs btn-block"><i class="fa fa-list"></i> Login Log</a></div>';
                                echo '</td>';
                                echo '</tr>';
                            }*/
                            ?>
                        </tbody>
                    </table>
                </div>

                <div class="row">
                    <?php
                    /*$p = $p ? $p + 1 : 1;

                    $paging = array();
                    for ($i = 1; $i <= ceil($jum / $l); $i++) {
                        $paging[$i] = $i;
                    }*/
                    ?>
                    <div class="col-md-3 text-left">
                        <?php //echo 'Limit : ' . $C->htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . $m . '&p=0&f=' . $f . '&l=\'+this.value"', true, $l); ?>
                    </div>
                    <div class="col-md-7 text-right">
                        halaman : <?php //echo $C->htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . $m . '?l=' . $l . '&f=' . $f . '&p=\'+this.value"', false, $p); ?>
                    </div>
                    <div class="col-md-2 text-center">
                        Total Data : <?php //echo $jum; ?>
                    </div>
                </div>
            </div>
				</div>
			</section>

			<div class="modal fade animate" id="modalMod" style="display: none">
		        <div class="modal-dialog fade-left modal-lg">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <h4 class="modal-title"><?php echo $this->uri->segment(1); ?></h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="account.act?a=true&t=ac" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <div class="form-group row">
		                        	<label class="col-sm-2 form-control-label">Nama Module</label>
		                        	<div class="col-sm-10">
						                <input type="text" name="nm_modules" class="form-control" placeholder="Nama Module">
						            </div>
		                        </div>
		                        <div class="form-group row">
		                        	<label class="col-sm-2 form-control-label">Nama Url</label>
		                        	<div class="col-sm-10">
						                <input type="text" name="link_modules" class="form-control" placeholder="Nama Module">
						            </div>
		                        </div>		                        
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
			if (!checkPrivilege(<?php echo $this->session->userdata('id_level');?>, 'ms_module_view')) {
		        history.go(-1);
		    }

		    function formAdd() {
		        if (checkPrivilege(<?php echo $this->session->userdata('id_level');?>, 'ms_module_add')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="password"]').prop('required', true);
		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege(<?php echo $this->session->userdata('id_level');?>, 'ms_module_delete')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = 'module/action/delete/' + (idC.join(','));
		            }
		        }
		    }
		</script>