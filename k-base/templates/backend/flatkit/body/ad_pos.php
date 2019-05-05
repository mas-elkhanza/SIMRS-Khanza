		<!-- Content Header (Page header) -->
		<div class="padding">
			<section class="content">
				<div class="row">
    				<div class="col-md-12">

                        <div class="row">
                            <div class="col-md-2">
                                <button class="btn btn-info btn-block btn-xs dark" onclick="formAdd()"><i class="fa fa-plus"></i> Tambah Data</button>
                            </div>
                            <div class="col-md-2">
                                <button class="btn btn-danger btn-block btn-xs" disabled="true" id="btnDelete" onclick="formDelete()"><i class="fa fa-trash-o"></i> Hapus Data</button>
                            </div>
                            <div class="col-md-5">

                            </div>
                            <div class="col-md-3">
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ad_pos_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th style="width: 1rem;" class="text-center"></th>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th>Nama Posyandu</th>
		                                <th>Provinsi</th>
		                                <th>Kabupaten</th>
		                                <th>Kecamatan</th>
		                                <th>Desa</th>
		                                <th style="width: 30px;" class="text-center">Rw</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		//print_r($_SESSION);
		                        		foreach ($r as $idrw => $rw) {
			                                echo '<tr style="cursor:pointer" ' .showValRec($rw) . '>';
			                                echo '<td class="text-center chk"><input type="checkbox" name="id[]" value="' . $rw->id . '"></td>';
			                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
			                                echo '<td class="tr_mod">' . $rw->nama. '</td>';
			                                echo '<td class="tr_mod">' . $rw->provinsi. '</td>';
			                                echo '<td class="tr_mod">' . $rw->kabupaten. '</td>';
			                                echo '<td class="tr_mod">' . $rw->kecamatan. '</td>';
			                                echo '<td class="tr_mod">' . $rw->desa. '</td>';
			                                echo '<td class="tr_mod">' . $rw->rw. '</td>';
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
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ad_pos_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ad_pos_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
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
		                    <h4 class="modal-title">Manajemen Posyandu</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ad_pos_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="id">
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Nama Posyandu</label>

		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="nama" placeholder="nama" required="true">
		                            </div>
		                        </div>
		                        
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Rw</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="rw" value="" placeholder="Rw" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">No Posyandu</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="no_posyandu" value="" placeholder="No Posyandu" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Strata Posyandu</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="strata_posyandu" value="" placeholder="Strata Posyandu" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Penanggung Jawab Umum</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="penanggung_jawab_umum" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Penanggung Jawab Operasional</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="penanggung_jawab_operasional" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Ketua Pelaksana</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="ketua_pelaksana" value="">
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Sekretaris</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="sekretaris" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Paud</label>
		                            <div class="col-sm-12">
		                                 <input type="text" class="form-control input-sm" name="paud" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Kb</label>
		                            <div class="col-sm-12">
		                            	 <input type="text" class="form-control input-sm" name="kb" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Terpadu</label>
		                            <div class="col-sm-12">
		                            	<input type="text" class="form-control input-sm" name="terpadu" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Jumlah Kader Aktif</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="jumlah_kader_posyandu_aktif" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Jumlah Kader Tidak Aktif</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="jumlah_kader_posyandu_tidak_aktif" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Petugas Kb</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="petugas_kb" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Medis Pra Medis</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="medis_pra_medis" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Bidan Desa</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="bidan_desa" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Keterangan</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="keterangan" value="" >
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Alamat</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="alamat" value="" >
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
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_pos_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_pos_edit');?>')) {
		                var tr = $(this).parent();

		                $('#modalMod :input').val('');
		                $('#modalMod [name="nama"]').val(tr.data('nama'));
		                $('#modalMod [name="rw"]').val(tr.data('rw'));
		                $('#modalMod [name="no_posyandu"]').val(tr.data('no_posyandu'));
		                $('#modalMod [name="strata_posyandu"]').val(tr.data('strata_posyandu'));
		                $('#modalMod [name="alamat"]').val(tr.data('alamat'));
		                $('#modalMod [name="penanggung_jawab_umum"]').val(tr.data('penanggung_jawab_umum'));
		                $('#modalMod [name="penanggung_jawab_operasional"]').val(tr.data('penanggung_jawab_operasional'));
		                $('#modalMod [name="ketua_pelaksana"]').val(tr.data('ketua_pelaksana'));
		                $('#modalMod [name="sekretaris"]').val(tr.data('sekretaris'));
		                $('#modalMod [name="paud"]').val(tr.data('paud'));
		                $('#modalMod [name="kb"]').val(tr.data('kb'));
		                $('#modalMod [name="terpadu"]').val(tr.data('terpadu'));
		                $('#modalMod [name="jumlah_kader_posyandu_aktif"]').val(tr.data('jumlah_kader_posyandu_aktif'));
		                $('#modalMod [name="jumlah_kader_posyandu_tidak_aktif"]').val(tr.data('jumlah_kader_posyandu_tidak_aktif'));
		                $('#modalMod [name="petugas_kb"]').val(tr.data('petugas_kb'));
		                $('#modalMod [name="medis_pra_medis"]').val(tr.data('medis_pra_medis'));
		                $('#modalMod [name="bidan_desa"]').val(tr.data('bidan_desa'));
		                $('#modalMod [name="keterangan"]').val(tr.data('keterangan'));
		                $('#modalMod [name="id"]').val(tr.data('id'));

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
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_pos_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="nama"]').prop('required', true);
		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_pos_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ad_pos_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }

		    function openLog(idne) {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_pos_log');?>')) {
		            window.location = '<?php echo base_url('ad_pos_view/log/')?>' + idne;
		        }
		    }
		</script>