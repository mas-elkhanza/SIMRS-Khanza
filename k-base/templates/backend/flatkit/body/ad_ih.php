		<?php  
		global $SConfig;
		?>
		<!-- Content Header (Page header) -->
		<div class="padding">
			<section class="content">
				<div class="row">
    				<div class="col-md-12 col-xs-12 col-sm-12">

                        <div class="row">
                            <div class="col-md-2 col-xs-12 col-sm-12">
                                <button class="btn btn-info btn-block btn-xs" onclick="formAdd()"><i class="fa fa-plus"></i> Tambah Data</button>
                            </div>
                            <div class="col-md-2 col-xs-12 col-sm-12">
                                <button class="btn btn-danger btn-block btn-xs" disabled="true" id="btnDelete" onclick="formDelete()"><i class="fa fa-trash-o"></i> Hapus Data</button>
                            </div>
                            <div class="col-md-5 col-xs-12 col-sm-12">

                            </div>
                            <div class="col-md-3 col-xs-12 col-sm-12">
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ad_ih_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered table-responsive">
                        		<thead>
		                            <tr>
		                                <th style="width: 1rem;" class="text-center"></th>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th class="text-center">Tanggal Registrasi</th>
		                                <th class="text-center">Nama Ibu Hamil</th>
		                                <th style="width: 30px;" class="text-center">Umur</th>
		                                <th class="text-center">Klp Dasa Wisma</th>
		                                <th style="width: 30px;" class="text-center">Usia Hamil</th>
		                                <th style="width: 30px;" class="text-center">Hamil Ke</th>
		                                <th style="width: 30px;" class="text-center">Resiko</th>
		                                <th style="width: 30px;" class="text-center">Registrasi Oleh</th>
		                                <th style="width: 50px;" class="text-center">Aksi</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		//print_r($_SESSION);
		                        		foreach ($r as $idrw => $rw) {
			                                echo '<tr style="cursor:pointer" ' .showValRec($rw) . '>';
			                                echo '<td class="text-center chk"><input type="checkbox" name="id[]" value="' . $rw->id . '"></td>';
			                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
			                                echo '<td class="tr_mod">' .tanggal_indo($rw->masuk_tgl,true). '</td>';
			                                echo '<td class="tr_mod">'.$rw->nm_lengkap.'</td>';
			                                echo '<td class="tr_mod">'.UsiaTahun($rw->tgl_lahir).'</td>';
			                                echo '<td class="tr_mod">'.getNamaPos($rw->id_posyandu).'</td>';
			                                echo '<td class="tr_mod">'.$rw->u_hamil.'</td>';
			                                echo '<td class="tr_mod">'.$rw->hamil_ke.'</td>';
			                                echo '<td class="tr_mod">'.@$SConfig->_resiko[$rw->resiko].'</td>';
			                                echo '<td class="tr_mod">'.getInfoUser($rw->masuk_oleh,'username')[0]->username.'</td>';
			                                echo '<td>';
			                                	echo '<div><a href="javascript:void(0)" onclick="viewDetail(' .$rw->id_penduduk.','.$rw->id. ')" style="cursor:pointer" class="btn btn-primary btn-xs btn-sm dark"><i class="fa fa-list"></i> View Detail</a></div>';
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

	                    	<div class="col-md-3 col-xs-12 col-sm-12 text-left">
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ad_ih_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 col-xs-12 col-sm-12 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ad_ih_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
		                    </div>
		                    <div class="col-md-2 col-xs-12 col-sm-12 text-center">
		                        Total Data : <?php echo $jum; ?>
		                    </div>
                        </div>
                    
                    </div>

                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="margin-top: 10px;">
                    	<div class="row">
                    		<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
                    			<div class="box-body grey-300">
                    				<div id="container" class="col-lg-12 col-md-12 col-xs-12 col-sm-12" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
                    			</div>
	                    	</div>
	                    	<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
	                    		<div class="box-body grey-300">
	                    			<div id="container2" class="col-lg-12 col-md-12 col-xs-12 col-sm-12" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
	                    		</div>
	                    	</div>
                    	</div>
                    </div>
				</div>
			</section>

			<div class="modal fade animate" id="modalMod" style="display: none">
		        <div class="modal-dialog fade-left modal-lg col-md-12 col-xs-12 col-sm-12">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <h4 class="modal-title">Registrasi Ibu Hamil</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ad_ih_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="id">
		                        
		                        <div class="col-md-12 col-xs-12 col-sm-12">
		                        	<div class="row">
		                        		<div class="col-md-12 col-xs-12 col-sm-12">
		                        			<div class="form-group">
					                        	<div class="col-sm-12" id="infoIh">
					                        	</div>
					                        </div>
		                        		</div>
		                        	</div>
		                        </div>

		                        <div class="col-md-12 col-xs-12 col-sm-12">
		                        	<div class="row">
		                        		<div class="col-md-6 col-xs-12 col-sm-12">
		                        			<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label"><strong>Nama Ibu Hamil</strong></label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($ih, 'name="id_penduduk" required="true" style="width:100%" class="form-control input-sm select2"', true);
					                                ?>
					                            </div>
					                        </div>
		                        		</div>

		                        		<div class="col-md-6 col-xs-12 col-sm-12">
		                        			 <div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label"><strong>Tanggal Registrasi</strong></label>
					                            <div class="col-sm-12">
					                                 <input type="date" id="masuk_tgl" class="form-control input-sm" name="masuk_tgl" value="<?php echo date('Y-m-d'); ?>">
					                            </div>
					                        </div>
		                        		</div>

		                        	</div>
		                        </div>

		                        <div class="col-md-12 col-xs-12 col-sm-12">
		                        	<div class="row">
		                        		<div class="col-md-6 col-xs-12 col-sm-12">
		                        			 <div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label"><strong>Tanggal Hamil</strong></label>
					                            <div class="col-sm-12">
					                                 <input type="date" id="masuk_tgl" class="form-control input-sm" name="tgl_hamil">
					                            </div>
					                        </div>
		                        		</div>

		                        		<div class="col-md-6 col-xs-12 col-sm-12">
		                        			 <div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label"><strong>Tgl Kunjungan</strong></label>
					                            <div class="col-sm-12">
					                                 <input type="date" class="form-control input-sm" name="tgl_pemeriksaan">
					                            </div>
					                        </div>
		                        		</div>
		                        	</div>
		                        </div>

		                        <div class="col-md-12 col-xs-12 col-sm-12">
		                        	<div class="row">
		                        		<div class="col-md-3 col-xs-12 col-sm-12">
		                        			<div class="form-group">
		                        				<label for="tun" class="col-sm-12 control-label"><strong>Usia hamil</strong></label>
					                            <div class="col-sm-12">
					                                 <input type="text" name="u_hamil" class="form-control input-sm" required="" placeholder="Usia Hamil">
					                            </div>
		                        			</div>
		                        		</div>
		                        		<div class="col-md-3 col-xs-12 col-sm-12">
		                        			<div class="form-group">
		                        				<label for="tun" class="col-sm-12 control-label"><strong>Hamil ke</strong></label>
					                            <div class="col-sm-12">
					                                 <input type="text" name="hamil_ke" class="form-control input-sm" placeholder="Hamil ke">
					                            </div>
		                        			</div>
		                        		</div>

		                        		<div class="col-md-3 col-xs-12 col-sm-12">
		                        			<div class="form-group">
		                        				<label for="tun" class="col-sm-12 control-label"><strong>Resiko Kehamilan</strong></label>
					                            <div class="col-sm-12">
					                            	<?php
					                                echo htmlSelectFromArray($SConfig->_resiko, 'name="resiko" class="form-control input-sm"', true);
					                                ?>
					                            </div>
		                        			</div>
		                        		</div>

		                        		<div class="col-md-3 col-xs-12 col-sm-12">
		                        			<div class="form-group">
		                        				<label for="tun" class="col-sm-12 control-label"><strong>BKS 1</strong></label>
					                            <div class="col-sm-12">
					                            	<input type="text" name="bks1" class="form-control input-sm" placeholder="BKS 1">
					                            </div>
		                        			</div>
		                        		</div>
		                        	</div>
		                        </div>

		                        <div class="col-md-12">
		                        	<div class="row">
		                        		<div class="col-md-3">
		                        			<div class="form-group">
		                        				<label for="tun" class="col-sm-12 control-label"><strong>BKS 2</strong></label>
					                            <div class="col-sm-12">
					                            	<input type="text" name="bks2" class="form-control input-sm" placeholder="BKS 2">
					                            </div>
		                        			</div>
		                        		</div>

		                        		<div class="col-md-3">
		                        			<div class="form-group">
		                        				<label for="tun" class="col-sm-12 control-label"><strong>BKS 3</strong></label>
					                            <div class="col-sm-12">
					                            	<input type="text" name="bks3" class="form-control input-sm" placeholder="BKS 3">
					                            </div>
		                        			</div>
		                        		</div>

		                        		<div class="col-md-6">
		                        			 <div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label"><strong>Imunisasi 1</strong></label>
					                            <div class="col-sm-12">
					                                 <input type="date" class="form-control input-sm" name="imunisasi1">
					                            </div>
					                        </div>
		                        		</div>

		                        	</div>
		                        </div>

		                        <div class="col-md-12">
		                        	<div class="row">
		                        		<div class="col-md-6">
		                        			 <div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label"><strong>Imunisasi 2</strong></label>
					                            <div class="col-sm-12">
					                                 <input type="date" class="form-control input-sm" name="imunisasi2">
					                            </div>
					                        </div>
		                        		</div>
		                        	</div>
		                        </div>

		                        <div class="col-md-12">
		                        	<div class="row">
		                        		<div class="col-md-12">
		                        			<label for="tun" class="col-sm-12 control-label"><strong>Hasil Penimbangan Berat Badan</strong></label>
		                        			<div class="row">
		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                            <label>Jan</label>
							                            <input type="text" class="form-control input-sm" name="jan_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Feb</label>
							                            <input type="text" class="form-control input-sm" name="feb_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Mar</label>
							                            <input type="text" class="form-control input-sm" name="mar_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Apr</label>
							                            <input type="text" class="form-control input-sm" name="apr_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Mei</label>
							                            <input type="text" class="form-control input-sm" name="may_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Jun</label>
							                            <input type="text" class="form-control input-sm" name="jun_bb">
							                        </div>
		                        				</div>
		                        			</div>

		                        			<div class="row">
		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Jul</label>
							                            <input type="text" class="form-control input-sm" name="jul_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Agu</label>
							                            <input type="text" class="form-control input-sm" name="aug_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Sep</label>
							                            <input type="text" class="form-control input-sm" name="sep_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Okt</label>
							                            <input type="text" class="form-control input-sm" name="oct_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Nov</label>
							                            <input type="text" class="form-control input-sm" name="nov_bb">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Des</label>
							                            <input type="text" class="form-control input-sm" name="dec_bb">
							                        </div>
		                        				</div>
		                        			</div>
		                        		</div>
		                        	</div>
		                        </div>

		                        <div class="col-md-12">
		                        	<div class="row">
		                        		<div class="col-md-12">
		                        			<label for="tun" class="col-sm-12 control-label"><strong>Usia Ibu Hamil</strong></label>
		                        			<div class="row">
		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                            <label>Jan</label>
							                            <input type="text" class="form-control input-sm" name="jan_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Feb</label>
							                            <input type="text" class="form-control input-sm" name="feb_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Mar</label>
							                            <input type="text" class="form-control input-sm" name="mar_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Apr</label>
							                            <input type="text" class="form-control input-sm" name="apr_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Mei</label>
							                            <input type="text" class="form-control input-sm" name="may_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Jun</label>
							                            <input type="text" class="form-control input-sm" name="jun_um">
							                        </div>
		                        				</div>
		                        			</div>

		                        			<div class="row">
		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Jul</label>
							                            <input type="text" class="form-control input-sm" name="jul_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Agu</label>
							                            <input type="text" class="form-control input-sm" name="aug_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Sep</label>
							                            <input type="text" class="form-control input-sm" name="sep_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Okt</label>
							                            <input type="text" class="form-control input-sm" name="oct_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Nov</label>
							                            <input type="text" class="form-control input-sm" name="nov_um">
							                        </div>
		                        				</div>

		                        				<div class="col-sm-2">
		                        					<div class="form-group">  
							                           	<label>Des</label>
							                            <input type="text" class="form-control input-sm" name="dec_um">
							                        </div>
		                        				</div>
		                        			</div>
		                        		</div>
		                        	</div>
		                        </div>

		                        <div class="col-md-12">
		                        	<div class="row">
		                        		<div class="col-md-12">
		                        			<div class="box black" id="statistik" style="display: none;">
		                        				<div class="box-body b-t" id="v_sts">
		                        					
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
			var link_url = '<?php echo base_url('ad_person_view/action');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_ih_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>','<?php echo aksesName('ad_ih_edit');?>')) {
		                var tr = $(this).parent();
		                
		                $('#modalMod :input').val('');
		                $('#modalMod [name="id"]').val(tr.data('id'));
		                //$('#modalMod input[type="checkbox"]').prop('checked', false);
		                $('#modalMod [name="id_penduduk"]').select2('val',tr.data('id_penduduk'));
		                $('#modalMod [name="masuk_tgl"]').val(tr.data('masuk_tgl'));
		                $('#modalMod [name="tgl_hamil"]').val(tr.data('tgl_hamil'));
		                $('#modalMod [name="hamil_ke"]').val(tr.data('hamil_ke'));
		                $('#modalMod [name="u_hamil"]').val(tr.data('u_hamil'));
		                $('#modalMod [name="resiko"]').val(tr.data('resiko'));
		                $('#modalMod [name="bks1"]').val(tr.data('bks1'));
		                $('#modalMod [name="bks2"]').val(tr.data('bks2'));
		                $('#modalMod [name="bks3"]').val(tr.data('bks3'));
		                $('#modalMod [name="imunisasi1"]').val(tr.data('imunisasi1'));
		                $('#modalMod [name="imunisasi2"]').val(tr.data('imunisasi2'));
		                $('#modalMod [name="jan_bb"]').val(tr.data('jan_bb'));
		                $('#modalMod [name="feb_bb"]').val(tr.data('feb_bb'));
		                $('#modalMod [name="mar_bb"]').val(tr.data('mar_bb'));
		                $('#modalMod [name="apr_bb"]').val(tr.data('apr_bb'));
		                $('#modalMod [name="may_bb"]').val(tr.data('may_bb'));
		                $('#modalMod [name="jun_bb"]').val(tr.data('jun_bb'));
		                $('#modalMod [name="jul_bb"]').val(tr.data('jul_bb'));
		                $('#modalMod [name="aug_bb"]').val(tr.data('aug_bb'));
		                $('#modalMod [name="sep_bb"]').val(tr.data('sep_bb'));
		                $('#modalMod [name="oct_bb"]').val(tr.data('oct_bb'));
		                $('#modalMod [name="nov_bb"]').val(tr.data('nov_bb'));
		                $('#modalMod [name="dec_bb"]').val(tr.data('dec_bb'));
		                $('#modalMod [name="jan_um"]').val(tr.data('jan_um'));
		                $('#modalMod [name="feb_um"]').val(tr.data('feb_um'));
		                $('#modalMod [name="mar_um"]').val(tr.data('mar_um'));
		                $('#modalMod [name="apr_um"]').val(tr.data('apr_um'));
		                $('#modalMod [name="may_um"]').val(tr.data('may_um'));
		                $('#modalMod [name="jun_um"]').val(tr.data('jun_um'));
		                $('#modalMod [name="jul_um"]').val(tr.data('jul_um'));
		                $('#modalMod [name="aug_um"]').val(tr.data('aug_um'));
		                $('#modalMod [name="sep_um"]').val(tr.data('sep_um'));
		                $('#modalMod [name="oct_um"]').val(tr.data('oct_um'));
		                $('#modalMod [name="nov_um"]').val(tr.data('nov_um'));
		                $('#modalMod [name="dec_um"]').val(tr.data('dec_um'));
		                
		                $.ajax({
				    		type : 'post',
				    		data : 'id='+tr.data('id'),
				    		url : '<?php echo base_url('ad_ih_view/action');?>'+'/get_ibu_hamil',
				    		success : function(hasil){
				    			view_data = JSON.parse(hasil);	
				    			//alert(view_data.bln_hamil);
				    			$('[name="'+view_data.bln_hamil+'"]').prop('disabled',true);		    			
				    			$('[name="'+view_data.bln_hamil+'"]').css({"background-color": "blue"});		    			
				    			$('#tks_lahir').text(view_data.tks_lahir);
				    			$('#usia_hamil').text(view_data.usia_hamil);
				    		}
				    	});

				    	$.ajax({
		    				type : 'post',
		    				data : 'id='+tr.data('id'),
		    				url : '<?php echo base_url('ad_ih_view/action');?>'+'/get_statistik',
		    				success : function(hst){
		    					$('#statistik').css('display','block');	
		    					$('#v_sts').html(hst);
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
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_ih_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="id_penduduk"]').prop('required', true);
		            $('#modalMod [name="masuk_tgl"]').prop('required', true);

		            $(document).on('change','[name="tgl_hamil"]',function(){
				    	var tgl_hamil = $(this).val();
				    	$.ajax({
				    		type : 'post',
				    		data : 'tgl_hamil='+tgl_hamil,
				    		url : '<?php echo base_url('ad_ih_view/action');?>'+'/get_ibu_hamil',
				    		success : function(hasil){
				    			view_data = JSON.parse(hasil);
						    	$('#tks_lahir').html(view_data.tks_lahir);
						    	$('#usia_hamil').html(view_data.usia_hamil);

				    		}
			    		});
				    });

		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_ih_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ad_ih_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }

		    function viewDetail(idne1,idne2) {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_ih_view');?>')) {
		            window.location = '<?php echo base_url('ad_ih_view/action/detail/f/0/0/')?>' + idne1 + '/'+ idne2;
		        }
		    }

		    $(document).on('change','[name="id_penduduk"]',function(){
		    	var id = $(this).val();
    			var d = 'id='+id;
    			$.ajax({
		    		type : 'post',
		    		data : d,
		    		url : link_url+'/get_info_ibu_hamil',
		    		success : function(hasil){
		    			$('#infoIh').html(hasil);
		    		}
	    		});
		    });

		    Highcharts.chart('container', {
			    chart: {
			        type: 'column'
			    },
			    title: {
			        text: 'Jumlah Ibu Hamil Yang Mendaftar Pada Tahun <?php echo date('Y');?>'
			    },
			    subtitle: {
			        text: 'e-sip'
			    },
			    xAxis: {
			        type: 'category',
			        labels: {
			            rotation: -45,
			            style: {
			                fontSize: '13px',
			                fontFamily: 'Verdana, sans-serif'
			            }
			        }
			    },
			    yAxis: {
			        min: 0,
			        title: {
			            text: 'Pertumbuhan ibu hamil mendaftar <?php echo date('Y');?> Tahun'
			        }
			    },
			    legend: {
			        enabled: false
			    },
			    tooltip: {
			        pointFormat: ''
			    },
			    series: [{
			        name: 'Population',
			        data: [<?php echo implode(',', $vsts_dftr);?>],
			        dataLabels: {
			            enabled: true,
			            rotation: -90,
			            color: '#FFFFFF',
			            align: 'right',
			            y: 10, // 10 pixels down from the top
			            style: {
			                fontSize: '13px',
			                fontFamily: 'Verdana, sans-serif'
			            }
			        }
			    }]
			});

			Highcharts.chart('container2', {
			    chart: {
			        type: 'column'
			    },
			    title: {
			        text: 'Jumlah Ibu Hamil Berkunjung Pada Tahun <?php echo date('Y');?>'
			    },
			    subtitle: {
			        text: 'e-sip'
			    },
			    xAxis: {
			        type: 'category',
			        labels: {
			            rotation: -45,
			            style: {
			                fontSize: '13px',
			                fontFamily: 'Verdana, sans-serif'
			            }
			        }
			    },
			    yAxis: {
			        min: 0,
			        title: {
			            text: 'Pertumbuhan ibu hamil berkunjung <?php echo date('Y');?> Tahun'
			        }
			    },
			    legend: {
			        enabled: false
			    },
			    tooltip: {
			        pointFormat: ''
			    },
			    series: [{
			        name: 'Population',
			        data: [<?php echo implode(',', $vsts_knj);?>],
			        dataLabels: {
			            enabled: true,
			            rotation: -90,
			            color: '#FFFFFF',
			            align: 'right',
			            y: 10, // 10 pixels down from the top
			            style: {
			                fontSize: '13px',
			                fontFamily: 'Verdana, sans-serif'
			            }
			        }
			    }]
			});
		</script>