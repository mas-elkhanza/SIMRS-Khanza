		<div class="row-col">
		  
		  <div class="col-sm">
		    <div ui-view class="padding pos-rlt">
		      
		      <div>
		        <!-- header -->
		        <div class="m-b">
		          <div class="btn-group pull-right">
		            <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url($this->uri->segment(1).'/action/brodcast/'); ?>' + this.value : '';">
		          </div>
		          <div class="btn-toolbar">
		            <div class="btn-group dropdown">
		              <button class="btn white btn-sm dropdown-toggle" data-toggle="dropdown">
		                <span class="dropdown-label">Filter</span>                    
		                <span class="caret"></span>
		              </button>
		              <div class="dropdown-menu text-left text-sm">
		                <a class="dropdown-item" href="<?php echo base_url($this->uri->segment(1).'/action/brodcast/f/0/0/Y');?>">Dibaca</a>
		                <a class="dropdown-item" href="<?php echo base_url($this->uri->segment(1).'/action/brodcast/f/0/0/T');?>">Belum Dibaca</a>
		              </div>
		            </div>
		            &nbsp;
		            <div class="btn-group">
                    	<button class="btn btn-info btn-block btn-xs hidden-folded" onclick="formAdd()"><i class="fa fa-plus"></i> Tulis Pesan</button>
		            </div>
		          </div>
		        </div>
		        <!-- / header -->

		        <!-- list -->
		        <div class="list white">
		          <table class="table table-hover table-bordered">
                		<thead>
                            <tr>
                                <th style="width: 1rem;" class="text-center"></th>
                                <!-- <th style="width: 30px;" class="text-center">No.</th> -->
                                <th>Judul</th>
                                <th style="width: 250px;">Masuk Oleh</th>
                                <th style="width: 250px;">Tanggal</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<?php 
                        		//print_r($_SESSION);
                        		foreach ($r as $idrw => $rw) {
                        			$ex_tgl = explode(" ", $rw->masuk_tgl);
	                                echo '<tr style="cursor:pointer" ' .showValRec($rw) . '>';
	                                //echo '<td class="text-center chk"><input type="checkbox" name="id[]" value="' . $rw->id . '"></td>';
	                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
	                                echo '<td class="tr_mod">' . $rw->judul. '</td>';
	                                echo '<td class="tr_mod">' . $nm_admin[$rw->masuk_oleh]. '</td>';
	                                echo '<td class="tr_mod">' . tanggal_indo($ex_tgl[0],true) . ' <br>Jam : ' . $ex_tgl[1] . '</td>';
	                                echo '</tr>';
	                            }

	                            if (count($r) == 0) {
	                                echo '<tr><td colspan="100%" style="text-align:center">There\'s no login log</td></tr>';
	                            }
                        	?>
                        </tbody>
                	</table>
		        <!-- / list -->
		      </div>

		      <div class="m-b" style="margin-top:10px;">
	        	<?php  
            	$p = $p ? $p + 1 : 1;

                $paging = array();
                for ($i = 1; $i <= ceil($jum / $l); $i++) {
                    $paging[$i] = $i;
                }
            	?>
	            	<div class="btn-group pull-right">
	            		halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ps_bc_view/action/brodcast/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
	            	</div>

	            	<div class="btn-toolbar">
	            		<?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ps_bc_view/action/brodcast/') . $f . '/\'+this.value"/0', true, $l); ?>
	            	</div>
		        </div>
		    </div>
		  </div>
		</div>

		<div class="modal fade animate" id="FormBc" style="display: none">
	        <div class="modal-dialog modal-lg fade-left">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <h4 class="modal-title">Formulir Pesan Brodcast</h4>
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                </div>
	                <div class="modal-body">
	                    <form action="<?php echo base_url('ps_bc_view/action/savebc') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
	                        <input type="hidden" name="id">
	                        <div class="col-md-12">
	                        	<div class="row">
	                        		<div class="col-sm-6">
				                      <label>Pilih Nama Dokter</label>
				                      <?php
		                                echo htmlSelectFromArray($dokter, 'name="kd_dokter" id="kd_dokter" required="true" style="width:100%;" class="form-control input-sm select2"', true);
		                              ?>
				                    </div>
				                    <div class="col-sm-6">
				                      <label>Pilih Poli</label>
				                      <select id="poliklinik" class="form-control select2" style="width:100%;" name="kd_poli">
				                      	<option>Tidak ada poli</option>
				                      </select>
				                    </div>  
	                        	</div>
	                        </div>

	                        <div class="col-md-12" style="margin-top: 10px;">
	                        	<div class="row" id="tanggal_periksa">
	                        		
	                        	</div>
	                        </div>

	                        <div class="col-md-12">
	                        	<div class="row">
                        			<div class="col-sm-6">
				                      <label>Tanggal Cuti Awal</label>
				                      <input type="date" name="tgl_awal" id="tgl_awal" class="form-control" >   
				                    </div> 
				                    <div class="col-sm-6">
				                      <label>Tanggal Cuti Akhir</label>
				                      <input type="date" name="tgl_akhir" id="tgl_akhir" class="form-control" >   
				                    </div> 
	                        	</div>
	                        </div>
	                        <div class="col-md-12">
	                        	<div class="row">
                        			<div class="col-sm-12">
				                      <label>Judul Pesan</label>
				                      <input type="text" name="judul" id="judul" class="form-control">        
				                    </div> 
	                        	</div>
	                        </div>
	                        <div class="col-md-12">
	                        	<div class="row">
                        			<div class="col-sm-12">
				                      <label>Keterangan</label>
				                      <textarea class="form-control" id="keterangan" rows="10" name="keterangan"></textarea>           
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

		<div class="modal fade animate" id="info" style="display: none">
		        <div class="modal-dialog modal-lg fade-left">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <h4 class="modal-title">Informasi Sukses</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body" id="msg">
		                    
		                </div>
		                <div class="modal-footer">
		                    <button type="submit" form="modForm" class="btn primary btn-sm" data-dismiss="modal"><i class="fa fa-arrow-circle-right"></i> Tutup</button>
		                </div>
		                </form>
		            </div>
		            <!-- /.modal-content -->
		        </div>
		        <!-- /.modal-dialog -->
		</div>

		<div class="alert alert-success " style="cursor:pointer;z-index:10000;height: 40px;margin-top: 30px;margin-bottom: 10px;margin-left:20px;margin-right:20px;background-color:rgb(210, 220, 228);padding:10px; display: none;" onclick="$(this).slideUp(\'fast\',function(){$(this).remove()});" id="msg"></div>

		<script>
			var link_url = '<?php echo base_url('ps_bc_view/action');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ps_bc_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function(){
		    	
		    	$('#kd_dokter').change(function(){
			    	var kd_dokter = $(this).val();
			    	$.ajax({
			    		type : 'post',
			    		url : link_url+'/get_poli',
			    		data : 'kd_dokter='+kd_dokter,
			    		success : function(data)
			    		{	
						    $('#poliklinik').html(data);
			    		}
			    	});
			    });

			    $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>','<?php echo aksesName('ps_psm_edit');?>')) {
		                var tr = $(this).parent();
		                var kd_poli = tr.data('kd_poli');
			        	var kd_dokter = tr.data('kd_dokter');

		                //alert(tr.data('kd_poli'));
		                $('#FormBc :input').val('');
		                $('#FormBc [name="id"]').val(tr.data('id'));
		                $('#FormBc [name="kd_dokter"]').select2('val',(tr.data('kd_dokter')));
		                $('#FormBc [name="tgl_periksa"]').val(tr.data('tgl_periksa'));
		                $('#FormBc [name="tgl_awal"]').val(tr.data('tgl_awal'));
		                $('#FormBc [name="tgl_akhir"]').val(tr.data('tgl_akhir'));
		                $('#FormBc [name="judul"]').val(tr.data('judul'));
		                $('#FormBc [name="keterangan"]').val(tr.data('keterangan'));
		                $('#FormBc [name="kd_poli"]').select2('val',(tr.data('kd_poli')));

		                $.ajax({
				    		type : 'post',
				    		url : link_url+'/get_poli',
				    		data : 'kd_dokter='+kd_dokter,
				    		success : function(data)
				    		{	
							    $('#poliklinik').html(data);
							    $('#poliklinik').select2('val',(tr.data('kd_poli')));
				    		}
				    	});

				    	$.ajax({
			        		type : 'post',
			        		url : link_url+'/get_data_booking_pasien',
			        		data : 'kd_poli='+kd_poli+'&kd_dokter='+kd_dokter,
			        		success : function(r)
			        		{
			        			$('#tanggal_periksa').html(r);
			        		}
			        	});

		                $('.sm-war-kosong').show();

		                $('#FormBc').modal();
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

		        $('#poliklinik').change(function(){
		        	var kd_poli = $(this).val();
		        	var kd_dokter = $('#kd_dokter').val();

		        	$.ajax({
		        		type : 'post',
		        		url : link_url+'/get_data_booking_pasien',
		        		data : 'kd_poli='+kd_poli+'&kd_dokter='+kd_dokter,
		        		success : function(r)
		        		{
		        			$('#tanggal_periksa').html(r);
		        		}
		        	});
		        });

		    });

		    function formAdd() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ps_bc_add');?>')) {
		            $('.sm-war-kosong').hide();

		            $('#FormBc').modal();
		        }
		    }
		</script>