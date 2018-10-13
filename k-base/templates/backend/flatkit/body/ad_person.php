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
                            <div class="col-md-5">

                            </div>
                            <div class="col-md-3">
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ad_person_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th style="width: 1rem;" class="text-center"></th>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th>Nik KK</th>
		                                <th>Nik</th>
		                                <th>Nama Lengkap</th>
		                                <th>Tempat Lahir</th>
		                                <th>Tanggal Lahir</th>
		                                <th>Usia</th>
		                                <th>Agama</th>
		                                <th>Jenis Kelamin</th>
		                                <th>status</th>
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
			                                echo '<td class="tr_mod">' . $rw->nik_kk. '</td>';
			                                echo '<td class="tr_mod">' . $rw->nik. '</td>';
			                                echo '<td class="tr_mod">' . $rw->nm_lengkap. '</td>';
			                                echo '<td class="tr_mod">' . $rw->tempat_lahir. '</td>';
			                                echo '<td class="tr_mod">' . tanggal_indo($rw->tgl_lahir, true). '</td>';
			                                echo '<td class="tr_mod">' . ConTanggal($rw->tgl_lahir). '</td>';
			                                echo '<td class="tr_mod">' . $agama[$rw->agama]. '</td>';
			                                echo '<td class="tr_mod">' . $jk[$rw->jk]. '</td>';
			                                echo '<td class="tr_mod">' . $st_nikah[$rw->status_nikah]. '</td>';
			                                echo '<td>';
			                                	echo '<div><a href="javascript:void(0)" onclick="viewDetail(' . $rw->id . ')" style="cursor:pointer" class="btn btn-primary btn-xs btn-sm dark"><i class="fa fa-list"></i> Lihat Relasi</a></div>';
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
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ad_person_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ad_person_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
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
		                    <h4 class="modal-title">Administrasi Penduduk</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ad_person_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="id">
		                        <input type="hidden" name="kabu">
		                        <input type="hidden" name="kec">
		                        <input type="hidden" name="des">

		                        <div class="col-sm-12 col-md-12">
		                        	<div class="row">
		                        		<div class="col-sm-12">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Nik KK</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($nik_kk, 'name="nik_kk" required="true" style="width:100%;" class="form-control input-sm select2"', true);
					                                ?>
					                            </div>
					                        </div>
										</div>
		                        	</div>
		                        </div>

		                        <div class="col-sm-12">
		                        	<div class="row">
		                        		<div class="col-sm-6">
		                        			<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Nik</label>
					                            <div class="col-sm-12">
					                                <input type="text" class="form-control input-sm" name="nik" placeholder="Nomor Induk Kependudukan">
					                            </div>
					                        </div>
		                        		</div>

		                        		<div class="col-sm-6">
		                        			<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Nama Lengkap</label>
					                            <div class="col-sm-12">
					                                <input type="text" class="form-control input-sm" name="nm_lengkap" value="" placeholder="Nama Lengkap" required="true">
					                            </div>
					                        </div>
		                        		</div>
		                        	</div>
		                        </div>
		                        

		                        <div class="col-sm-12">
		                        	<div class="row">
		                        		
		                        		<div class="col-sm-6">
		                        			<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Tempat Lahir</label>
					                            <div class="col-sm-12">
					                                <input type="text" class="form-control input-sm" name="tempat_lahir" value="" placeholder="Tempat Lahir">
					                            </div>
					                        </div>
		                        		</div>

		                        		<div class="col-sm-6">
		                        			<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Tanggal Lahir</label>
					                            <div class="col-sm-12">
					                                 <input type="date" class="form-control input-sm" name="tgl_lahir" required="true">
					                            </div>
					                        </div>
		                        		</div>
		                        		
		                        	</div>
		                        </div>

		                        <div class="col-sm-12">	
									<div class="row">
		                        		<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Jenis Kelamin</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($jk, 'name="jk" required="true" class="form-control input-sm"', true);
					                                ?>
					                            </div>
					                        </div>
										</div>
										
										<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Agama</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($agama, 'name="agama" required="true" class="form-control input-sm"', true);
					                                ?>
					                            </div>
					                        </div>
										</div>
									</div>
								</div>

								<div class="col-sm-12">	
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Pekerjaan</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($pekerjaan, 'name="pekerjaan" required="true" class="form-control input-sm"', true);
					                                ?>
					                            </div>
					                        </div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Status Kawin</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($st_nikah, 'name="status_nikah" required="true" class="form-control input-sm"', true);
					                                ?>
					                            </div>
					                        </div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">WNI</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($wni, 'name="wni" class="form-control input-sm"', true);
					                                ?>
					                            </div>
					                        </div>	
										</div>
									</div>
								</div>

								<div class="col-sm-12">	
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Provinsi</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($pv, 'name="provinsi" class="form-control input-sm select2" style="width:100%;" id="provinsi"', true);
					                                ?>
					                            </div>
					                        </div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Kabupaten</label>
					                            <div class="col-sm-12" id="kabupaten">
					                            	
					                                <!-- <?php
					                                //echo htmlSelectFromArray($kb, 'name="kabupaten" class="form-control input-sm"', true);
					                                ?> -->
					                            </div>
					                        </div>
										</div>
									</div>
								</div>

		                        <div class="col-sm-12">	
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Kecamatan</label>
					                            <div class="col-sm-12" id="kecamatan">
					                                <!-- <?php
					                                //echo htmlSelectFromArray($kc, 'name="kecamatan" class="form-control input-sm"', true);
					                                ?> -->
					                            </div>
					                        </div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-3 control-label">Desa</label>
					                            <div class="col-sm-12" id="desa">
					                                <!-- <?php
					                                //echo htmlSelectFromArray($ds, 'name="desa" class="form-control input-sm"', true);
					                                ?> -->
					                            </div>
					                        </div>
										</div>
									</div>
								</div>

								<div class="col-sm-12">	
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Alamat</label>
					                            <div class="col-sm-12">
					                                <input type="text" class="form-control input-sm" name="alamat" value="" placeholder="Alamat">
					                            </div>
					                        </div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
					                            <label for="tun" class="col-sm-3 control-label">RW</label>
					                            <div class="col-sm-12">
					                                <input type="text" class="form-control input-sm" name="rw" value="" placeholder="rw">
					                            </div>
					                        </div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
					                            <label for="tun" class="col-sm-3 control-label">RT</label>
					                            <div class="col-sm-12">
					                                <input type="text" class="form-control input-sm" name="rt" value="" placeholder="RT">
					                            </div>
					                        </div>
										</div>
									</div>
								</div>

								<div class="col-sm-12">	
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Pendidikan</label>
					                            <div class="col-sm-12">
					                                <?php
					                                echo htmlSelectFromArray($pendidikan, 'name="pendidikan" class="form-control input-sm"', true);
					                                ?>
					                            </div>
					                        </div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Golongan Darah</label>
					                            <div class="col-sm-12">
					                               <?php
					                                echo htmlSelectFromArray($gol_darah, 'name="gol_darah" required="true" class="form-control input-sm"', true);
					                                ?>
					                            </div>
					                        </div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Tinggi Badan</label>
					                            <div class="col-sm-12">
												<input type="text" name="tinggi_badan" placeholder="">
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

		    <div class="modal fade animate" id="modalRelasi" style="display: none">
		        <div class="modal-dialog fade-left modal-lg">
		        	<div class="modal-content">
		        		<div class="modal-header">
		        			<h4 class="modal-title">Silsilah Keluarga</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		        		</div>

		        		<div class="modal-body" id="silsilah_kel">
		        			
		        		</div>
		        	</div>
		        </div>
		    </div>

		    <div class="modal fade animate" id="modalView" style="display: none">
		        <div class="modal-dialog fade-left modal-lg">
		        	<div class="modal-content">
		        		<div class="modal-header">
		        			<h4 class="modal-title">Data User</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		        		</div>

		        		<div class="modal-body" id="data_penduduk">
		        			
		        		</div>
		        	</div>
		        </div>
		    </div> 

		</div>
		<!-- end/Content Header (Page Header) -->
		<script>
			var link_url = '<?php echo base_url('ad_person_view/action/');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_person_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_person_edit');?>')) {
		                var tr = $(this).parent();
		                
		                $('#modalMod :input').val('');
		                $('#modalMod [name="nik"]').val(tr.data('nik'));
		                $('#modalMod [name="nik_kk"]').select2('val',tr.data('nik_kk'));
		                $('#modalMod [name="nm_lengkap"]').val(tr.data('nm_lengkap'));
		                $('#modalMod [name="tempat_lahir"]').val(tr.data('tempat_lahir'));
		                $('#modalMod [name="tgl_lahir"]').val(tr.data('tgl_lahir'));
		                $('#modalMod [name="agama"]').val(tr.data('agama'));
		                $('#modalMod [name="gol_darah"]').val(tr.data('gol_darah'));
		                $('#modalMod [name="tinggi_badan"]').val(tr.data('tinggi_badan'));
		                $('#modalMod [name="jk"]').val(tr.data('jk'));
		                $('#modalMod [name="pekerjaan"]').val(tr.data('pekerjaan'));
		                $('#modalMod [name="alamat"]').val(tr.data('alamat'));
		                $('#modalMod [name="wni"]').val(tr.data('wni'));
		                $('#modalMod [name="rw"]').val(tr.data('rw'));
		                $('#modalMod [name="rt"]').val(tr.data('rt'));
		                $('#modalMod [name="pendidikan"]').val(tr.data('pendidikan'));
		                $('#modalMod [name="status_nikah"]').val(tr.data('status_nikah'));
		                $('#modalMod [name="provinsi"]').select2('val',tr.data('provinsi'));
		                $('#modalMod [name="kabu"]').val(tr.data('kabupaten'));
		                $('#modalMod [name="kec"]').val(tr.data('kecamatan'));
		                $('#modalMod [name="des"]').val(tr.data('desa'));
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
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_person_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="nm_lengkap"]').prop('required', true);
		            $('#modalMod [name="nik_kk"]').prop('required', true);

		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_person_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ad_person_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }

		    function viewDetail(idne) {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_person_view');?>')) {
		            window.location = '<?php echo base_url('ad_person_view/action/detail/')?>' + idne;
		        }
		    }

		    $(document).on('change','select[name="provinsi"]',function(){
	          var idpv = jQuery(this).val();
	          $.ajax({
	            type  : 'post',
	            data  : 'id='+idpv,
	            url   : link_url+'get_kab',
	            success : function(hasil){
	              $('#kabupaten').html(hasil);
	            } 
	          });
	        });

	        $(document).on('change','select[name="kabupaten"]', function(){
	          var idkb = $(this).val();

	          $.ajax({
	            type : 'post',
	            data : 'id='+idkb,
	            url : link_url+'get_kec',
	            success : function(hasil)
	            {
	              $('#kecamatan').html(hasil);
	            }
	          });
	        });

	        $(document).on('change','select[name="kecamatan"]', function(){
	          var idds = $(this).val();

	          $.ajax({
	            type : 'post',
	            data : 'id='+idds,
	            url : link_url+'get_desa',
	            success : function(hasil)
	            {
	              $('#desa').html(hasil);
	            }
	          });
	        });

	        function viewDetail(idne) {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_relasi_view');?>')) {
		        	var id = idne;

		        	$.ajax({
		    		type : 'post',
		    		data : 'id='+id,
		    		url  : link_url+'get_relasi',
		    			success : function(hasil){
	              			$('#silsilah_kel').html(hasil);
	            		} 
		    		});

		            $('.sm-war-kosong').hide();

		            $('#modalRelasi').modal();
		        }
		    }

		    function DetailUser(idne) {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_relasi_add');?>')) {
		            $('#modalMod :input').val('');
		           	
		           	$.ajax({
		           		type  : 'post',
			            data  : 'id='+idne,
			            url   : link_url+'get_info_user',
			            success : function(hasil){
			              $('#data_penduduk').html(hasil);
			            } 
		           	});

		            $('.sm-war-kosong').hide();

		            $('#modalView').modal();
		        }
		    }
		</script>