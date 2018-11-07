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
                                <button class="btn btn-info btn-block btn-xs btn-sm dark" onclick="formAdd()"><i class="fa fa-plus"></i> Tambah Data</button>
                            </div>
                            <div class="col-md-2">
                                <button class="btn btn-danger btn-block btn-xs btn-sm danger" disabled="true" id="btnDelete" onclick="formDelete()"><i class="fa fa-trash-o"></i> Hapus Data</button>
                            </div>
                            <div class="col-md-2">

                            </div>
                            <div class="col-md-3">
                        	<?php 
                        	echo '' . htmlSelectFromArray($SConfig->_filter_peserta, 'name="fw" class="form-control input-sm" style="" onchange="window.location=\'' . base_url('ad_peserta_view/action/view/') . $f . '/0/0/\'+this.value"', true, $fw);
                        	?>
                            </div>
                            <div class="col-md-3">
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ad_peserta_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th style="width: 1rem;" class="text-center"></th>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th>Nama Lengkap</th>
		                                <th style="width: 15%;">Tanggal Lahir</th>
		                                <th style="width: 15%;">Usia</th>
		                                <th style="width: 10%">Jenis Kelamin</th>
		                                <th style="width: 17%">Kategori</th>
		                                <th>Aksi</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		//print_r($_SESSION);
		                        		foreach ($r as $idrw => $rw) {
			                                echo '<tr style="cursor:pointer" ' .showValRec($rw) . '>';
			                                echo '<td class="text-center chk"><input type="checkbox" name="id[]" value="' . $rw->id . '"></td>';
			                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
			                                echo '<td class="tr_mod">'.$rw->nm_lengkap.'</td>';
			                                echo '<td class="tr_mod">'.tanggal_indo($rw->tgl_lahir,true).'</td>';
			                                echo '<td class="tr_mod">'.ConTanggal($rw->tgl_lahir).'</td>';
			                                echo '<td class="tr_mod">'.$SConfig->_jk[$rw->jk].'</td>';
			                                echo '<td class="tr_mod">';
			                                /*if($rw->jk == 1)
			                                {
			                                	echo $rw->bulan;
			                                }*/
			                                echo @cekKategoriPeserta($rw->bulan,$rw->status_nikah,$rw->jk);
			                                echo '</td>';
			                                echo '<td class="tr_mod">';
			                                	echo "string";
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

                        <div class="row" style="margin-top: 15px; ">
	                    	<?php  
	                    	$p = $p ? $p + 1 : 1;

		                    $paging = array();
		                    for ($i = 1; $i <= ceil($jum / $l); $i++) {
		                        $paging[$i] = $i;
		                    }
	                    	?>

	                    	<div class="col-md-3 text-left">
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ad_peserta_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ad_peserta_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
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
		                    <h4 class="modal-title">Administrasi Relasi</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ad_peserta_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="id">

		                        <div class="col-sm-12">
		                        	<div class="row">

		                        		<div class="col-sm-6">
		                        			<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Nik Induk</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($lsrp, 'name="id_penduduk" required="true" class="form-control input-sm select2" style="width:100%;" ', true);
					                                ?>
					                            </div>
					                        </div>
		                        		</div>

		                        		<div class="col-sm-6">
		                        			<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Nik Relasi</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($lsrl, 'name="relasi_id" class="form-control input-sm select2" style="width:100%;" ', true);
					                                ?>
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
			var link_url = '<?php echo base_url('ad_peserta_view/action/');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_peserta_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_peserta_kaji');?>')) {
		                var tr = $(this).parent();
		                
		                $('#modalMod :input').val('');
		                $('#modalMod [name="id_penduduk"]').select2('val',tr.data('id_penduduk'));
		                $('#modalMod [name="relasi_id"]').select2('val',tr.data('relasi_id'));
		                /*$('#modalMod [name="nik"]').val(tr.data('nik'));
		                $('#modalMod [name="nm_lengkap"]').val(tr.data('nm_lengkap'));*/

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
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_peserta_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="id_penduduk"]').prop('required', true);

		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_peserta_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ad_peserta_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }

		    function viewDetail(idne) {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_peserta_view');?>')) {
		            window.location = '<?php echo base_url('ad_peserta_view/action/detail/')?>' + idne;
		        }
		    }
		</script>