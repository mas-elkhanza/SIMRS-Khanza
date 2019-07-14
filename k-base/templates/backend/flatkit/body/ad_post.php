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
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ad_post_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th style="width: 1rem;" class="text-center"></th>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th>Judul</th>
		                                <th style="width: 20%">Oleh</th>
		                                <th style="width: 15%;">Status</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		//print_r($_SESSION);
		                        		foreach ($r as $idrw => $rw) {
			                                echo '<tr style="cursor:pointer" data-id="'.$rw->id.'" data-id_kat_post="'.$rw->id_kat_post.'" data-status="'.$rw->status.'" data-masuk_oleh="'.$rw->masuk_oleh.'">';
			                                echo '<td class="text-center chk"><input type="checkbox" name="id[]" value="' . $rw->id . '"></td>';
			                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
			                                echo '<td class="tr_mod">' . $rw->judul. '</td>';
			                                echo '<td class="tr_mod">' . $rw->masuk_oleh. '</td>';
			                                echo '<td>';
			                                if ($rw->status == 'Y') {
			                                    echo '<label class="btn btn-success btn-xs btn-block"><i class="fa fa-check"></i> Active</label>';
			                                } else {
			                                    echo '<label class="btn btn-danger btn-xs btn-block"><i class="fa fa-times"></i> Not Active</label>';
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
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ad_post_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ad_post_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
		                    </div>
		                    <div class="col-md-2 text-center">
		                        Total Data : <?php echo $jum; ?>
		                    </div>
                        </div>
                    
                    </div>
				</div>
			</section>

			<div class="modal fade animate" id="edit" style="display: none;">
				<div class="modal-dialog modal-lg fade-left">
			            <div class="modal-content">
			                <div class="modal-header">
			                    <h4 class="modal-title">Edit Artikel Post</h4>
			                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
			                </div>
			                <div class="modal-body" id="ajaxEdit">                       
			                    
			                </div>
			                <div class="modal-footer">
			                    <button type="submit" form="modFormEdit" class="btn primary btn-sm"><i class="fa fa-arrow-circle-right"></i> Update</button>
			                </div>
			                
			            </div>
				</div>
			</div>

			<div class="modal fade animate" id="modalMod" style="display: none">
		        <div class="modal-dialog modal-lg fade-left">
			            <div class="modal-content">
			                <div class="modal-header">
			                    <h4 class="modal-title">Artikel Post</h4>
			                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
			                </div>
			                <div class="modal-body">
			                <form action="<?php echo base_url('ad_post_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">    
			                        <input type="hidden" name="id">
			                        <div class="col-md-12 col-xs-12">
			                        	<div class="row">
			                        		<div class="col-md-12 col-xs-12">
			                        			<div class="form-group">
						                            <label for="tun" class="col-sm-12 control-label">Judul</label>
						                            <div class="col-md-12 col-xs-12">
						                                <input type="text" class="form-control input-sm" name="judul" placeholder="judul" required="true">
						                            </div>
						                        </div>
			                        		</div>

			                        		<div class="col-md-12 col-xs-12">
			                        			<div class="row">
											    	<div class="col-md-12 col-xs-12">
												    	<div class="form-group">
													    	<div class="col-md-12 col-xs-12">
													    		<div class="b-b b-primary nav-active-primary col-xs-12">
																	<ul class="nav nav-tabs">
																		<li class="nav-item">
																			<a class="nav-link active" href data-toggle="tab" data-target="#tab1"><i class="fa fa-th"></i> Posisi</a>
																		</li>
																		<li class="nav-item">
																			<a class="nav-link" href data-toggle="tab" data-target="#tab2"><i class="fa fa-check"></i> Kategori Post</a>
																		</li>
																		<li class="nav-item">
																			<a class="nav-link" href data-toggle="tab" data-target="#tab3"><i class="fa fa-clock-o"></i> Waktu</a>
																		</li>
																		<li class="nav-item">
																			<a class="nav-link" href data-toggle="tab" data-target="#tab4"><i class="fa fa-user"></i> Penulis</a>
																		</li>
																		<li class="nav-item">
																			<a class="nav-link" href data-toggle="tab" data-target="#tab5"><i class="fa fa-search"></i> Seo</a>
																		</li>
																		<li class="nav-item">
																			<a class="nav-link" href data-toggle="tab" data-target="#tab6"><i class="fa fa-file-image-o"></i> Upload</a>
																		</li>
																		<li class="nav-item">
																			<a class="nav-link" href data-toggle="tab" data-target="#tab7"><i class="fa  fa-power-off"></i> Status</a>
																		</li>
																	</ul>
																</div>
																<div class="tab-content p-a m-b-md" style="border-bottom: 1px solid #0cc2aa; border-left: 1px solid #0cc2aa;border-right: 1px solid #0cc2aa;">
																	<div class="tab-pane animated fadeIn text-muted active" id="tab1">
																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<div class="col-md-12 col-xs-12">
																					<?php 
																					foreach ($kat_post as $k => $v) {
																						echo '<label class="md-check">';
												                                        echo '<input type="checkbox" name="'.$k.'"  data-val="'.$v.'"> <i class="indigo"></i> '. $v .'&nbsp;';
												                                        echo '</label><br>';
																					}
																					?>
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="tab-pane animated fadeIn text-muted" id="tab2">
																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<div class="col-sm-12 col-xs-12">
																					<?php  
																					echo $id_kat_post;
																					?>
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="tab-pane animated fadeIn text-muted" id="tab3">
																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<div class="col-sm-12 col-xs-12">
													                                 <input type="datetime-local" class="form-control input-sm" name="masuk_tgl">
													                            </div>
																			</div>
																		</div>
																	</div>

																	<div class="tab-pane animated fadeIn text-muted" id="tab4">
																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<div class="col-sm-12 col-xs-12">
													                                <?php
														                                echo htmlSelectFromArray($penulis, 'name="masuk_oleh" class="form-control input-sm select2" style="width:100%;"', true);
														                            ?>
													                            </div>
																			</div>
																		</div>
																	</div>

																	<div class="tab-pane animated fadeIn text-muted" id="tab5">
																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<label for="tun" class="col-sm-12 control-label"><b>Judul</b></label>
																				<div class="col-sm-12 col-xs-12">
													                                <input type="text" name="meta_title" class="form-control">
													                            </div>
													                            <div class="col-sm-12 col-xs-12">
													                            	<p class="table-bordered">Title ini adalah meta title yang berfungsi untuk SEO Maksimal, karena search engine sangat mengedepankan meta title ini
													                            	</p>
													                            </div>
																			</div>
																		</div>

																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Meta Kata Kunci</b></label>
																				<div class="col-sm-12 col-xs-12">
													                                <textarea name="meta_keyword" class="form-control"></textarea>
													                            </div>
													                            <div class="col-sm-12 col-xs-12">
													                            	<p class="table-bordered">Keyword (kata kunci) apa yang akan Anda bidik, agar website Anda muncul di search engine setiap kali orang mencari kata <br>tersebut di Search Engine
													                            	</p>
													                            </div>
																			</div>
																		</div>

																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Meta Deskripsi</b></label>
																				<div class="col-sm-12 col-xs-12">
													                                <textarea name="meta_description" class="form-control"></textarea>
													                            </div>
													                            <div class="col-sm-12 col-xs-12">
													                            	<p class="table-bordered">Silahkan isi, Meta Description agar artikel ini memiliki SEO Score yang bagus dalam Search Engine. Dan website Anda memiliki peringkat yang bagus dalam search Engine, seperti Google maupun Bing. 
													                            	</p>
													                            </div>
																			</div>
																		</div>
																	</div>

																	<div class="tab-pane animated fadeIn text-muted" id="tab6">
																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Upload gambar</b></label>
																				<div class="col-sm-12 col-xs-12">
													                                <input type="file" name="gambar" id="gambar"  />
													                            </div>
																			</div>
																		</div>
																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Link Url Vidieo</b></label>
																				<div class="col-sm-12 col-xs-12">
													                                <input type="text" name="file_vidio" class="form-control">
													                            </div>
																			</div>
																		</div>
																	</div>

																	<div class="tab-pane animated fadeIn text-muted" id="tab7">
																		<div class="col-md-12 col-xs-12">
																			<div class="form-group">
																				<div class="col-sm-12 col-xs-12">
													                                <?php
														                                echo htmlSelectFromArray($status, 'name="status" class="form-control input-sm select2" style="width:100%;"', true);
														                            ?>
													                            </div>
																			</div>
																		</div>
																	</div>
																</div>
													    	</div>
														</div>
											    	</div>
											    </div>
			                        		</div>

			                        		<div class="col-md-12 col-xs-12">
			                        			<div class="row">
			                        				<div class="col-md-12 col-xs-12">
					                        			<div class="form-group">
					                        				<div class="col-md-12 col-xs-12">
					                        					<textarea class="form-control" rows="5" cols="9" id="editor1" name="konten"></textarea>
					                        				</div>
					                        			</div>
				                        			</div>
			                        			</div>
			                        		</div>

			                        		<div class="col-md-12 col-xs-12">
			                        			<div class="row">
			                        				<div class="col-md-12 col-xs-12">
			                        					<div class="form-group">
			                        						<label for="tun" class="col-sm-12 col-xs-12 control-label"><b>Tag</b></label>
					                        				<div class="col-md-12 col-xs-12">
					                        					<?php
									                                echo htmlSelectFromArray($tag, 'name="tag" class="form-control input-sm select2" style="width:100%;"', true);
									                            ?>
					                        				</div>
					                        			</div>	
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
			                
			            </div>
		            <!-- /.modal-content -->
		        </div>
		        <!-- /.modal-dialog -->
		    </div>

		</div>
		<!-- end/Content Header (Page Header) -->
		<script>
			var link = '<?php echo base_url();?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_post_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function() {
		      $("#menu-tree").treeview();
		   });

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_post_edit');?>')) {
		                var tr = $(this).parent();
		               	var id = tr.data('id');
		               	
		               	$.ajax({
		               		type	: 'post',
		               		url 	: link + 'ad_post_view/action/edit',
		               		data 	: 'id='+id,
		               		success : function(r){
		               			$('.sm-war-kosong').show();
		               			$('#ajaxEdit').html(r);
		               			//$('#edit [name="id_post_kat"]').val(tr.data('id_post_kat'));
		               			$('#edit [name*="id_kat_post"]').each(function () {
				                    if (tr.data('id_kat_post').toString().indexOf($(this).data('val')) >= 0) 
				                    {//found
				                        $(this).prop('checked', true);
				                    }
				                });
				                $('#edit [name="masuk_oleh"]').val(tr.data('masuk_oleh'));
				                $('#edit [name="status"]').val(tr.data('status'));
		                		$('#edit').modal();
		                		CKEDITOR.replace('editor2' ,{
							      filebrowserImageBrowseUrl : '<?php echo base_url('assets/kcfinder'); ?>'
							    });
							    $("#menu-tree").treeview();
		               		} 
		               	});

		                
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
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_post_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="judul"]').prop('required', true);
		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_post_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ad_post_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }
		</script>