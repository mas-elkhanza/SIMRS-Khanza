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
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ad_nikah_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th style="width: 1rem;" class="text-center"></th>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th>Nik</th>
		                                <th>Nama Pasangan Laki-laki</th>
		                                <th>Nama Pasangan Perempuan</th>
		                                <th>Tanggal Pernikahan</th>
		                                <th>Jumlah Anak</th>
		                                <th style="width: 20%">Status</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		//print_r($_SESSION);
		                        		foreach ($r as $idrw => $rw) {
			                                echo '<tr style="cursor:pointer" ' .showValRec($rw) . '>';
			                                echo '<td class="text-center chk"><input type="checkbox" name="id[]" value="' . $rw->id . '"></td>';
			                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
			                                echo '<td class="tr_mod">' . $rw->nik. '</td>';
			                                echo '<td class="tr_mod">' . getNamaPerson($rw->id_l). '</td>';
			                                echo '<td class="tr_mod">' . getNamaPerson($rw->id_p). '</td>';
			                                echo '<td class="tr_mod">' . tanggal_indo($rw->tgl_nikah,true). '</td>';
			                                echo '<td class="tr_mod">' . count($rw->jml_hamil). '</td>';
			                                echo '<td class="tr_mod">' . status_nikah($rw->status). '</td>';
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
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ad_nikah_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ad_nikah_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
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
		                    <h4 class="modal-title">Administrasi Pernikahan</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ad_nikah_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="id">
		                        <input type="hidden" name="idl">
		                        <input type="hidden" name="idp">
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Nik</label>

		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="nik" placeholder="Nomor Induk Pernikahan">
		                            </div>
		                        </div>
		                        
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Tanggal Pernikahan</label>
		                            <div class="col-sm-12">
		                                 <input type="date" class="form-control input-sm" name="tgl_nikah" required="true">
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Pasangan Laki-laki</label>
		                            <div class="col-sm-12">
		                                <?php
		                                echo htmlSelectFromArray($lk, 'name="id_l" style="width:100%" class="form-control input-sm select2"', true);
		                                ?>
		                            </div>
		                        </div>

		                        <div class="form-group">
		                        	<div id="info_l" class="col-sm-12">
		                            	
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Pasangan Perempuan</label>
		                            <div class="col-sm-12">
		                                <?php
		                                echo htmlSelectFromArray($lp, 'name="id_p"  style="width:100%" class="form-control input-sm select2"', true);
		                                ?>
		                            </div>
		                        </div>

		                        <div class="form-group">
		                        	<div id="info_p" class="col-sm-12">
		                            	
		                            </div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Status</label>
		                            <div class="col-sm-12">
		                                <?php
		                                $status = status_nikah();
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
			var link_url = '<?php echo base_url('ad_person_view/action');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_nikah_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_nikah_edit');?>')) {
		                var tr = $(this).parent();

		                $('#modalMod :input').val('');
		                $('#modalMod [name="nik"]').val(tr.data('nik'));
		                $('#modalMod [name="id_l"]').select2('val',tr.data('id_l'));
		                $('#modalMod [name="id_p"]').select2('val',tr.data('id_p'));
		                $('#modalMod [name="tgl_nikah"]').val(tr.data('tgl_nikah'));
		                $('#modalMod [name="status"]').val(tr.data('status'));
		                /*var datan = tr.data('device');
		                if (datan.toString().indexOf(',') < 0) {
		                    datan = datan + ',';
		                }
		                $('#modalMod [name="device[]"]').select2('val', datan.split(','));*/
		                $('#modalMod [name="id"]').val(tr.data('id'));
		                $('#modalMod [name="idl"]').val(tr.data('id_l'));
		                $('#modalMod [name="idp"]').val(tr.data('id_p'));

		                var id_l = tr.data('id_l');
		                var id_p = tr.data('id_p');
		                
		                $.ajax({
			            type  : 'post',
			            data  : 'id='+id_l,
			            url   : link_url+'/info_l',
				            success : function(hasil){
				              $('#info_l').html(hasil);
				            
				            } 
			          	});

			          	$.ajax({
			            type  : 'post',
			            data  : 'id='+id_p,
			            url   : link_url+'/info_p',
				            success : function(hasil){
				              $('#info_p').html(hasil);
				            
				            } 
			          	});

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
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_nikah_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="id_l"]').prop('required', true);
		            $('#modalMod [name="id_p"]').prop('required', true);

		            $(document).on('change','select[name="id_l"]',function(){
			          var id_l = jQuery(this).val();
			          //alert(category_parent);
			          $.ajax({
			            type  : 'post',
			            data  : 'id='+id_l,
			            url   : link_url+'/info_l',
			            success : function(hasil){
			              $('#info_l').html(hasil);
			            
			            } 
			          });
			        });

			        $(document).on('change','select[name="id_p"]',function(){
			          var id_p = jQuery(this).val();
			          //alert(category_parent);
			          $.ajax({
			            type  : 'post',
			            data  : 'id='+id_p,
			            url   : link_url+'/info_p',
			            success : function(hasil){
			              $('#info_p').html(hasil);
			            
			            } 
			          });
			        });

		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_nikah_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ad_nikah_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }

		    function openLog(idne) {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_nikah_log');?>')) {
		            window.location = '<?php echo base_url('ad_nikah_view/log/')?>' + idne;
		        }
		    }
		</script>