		<!-- Content Header (Page header) -->
		<div class="padding">
			<section class="content">
				<div class="row">
    				<div class="col-md-12">
    					<div class="row">
    						<div class="col-md-12">
    							<div class="box" style="margin-top: 15px">
		    						<div class="table-responsive">
		    							<table class="table table-bordered m-0">
									        <thead>
									          <tr>
									            <th>Nik</th>
									            <th>Nama Suami</th>
									            <th>Nama Istri</th>
									            <th>Kategori</th>
									            <th>Tanggal Menikah</th>
									            <th>Status</th>
									          </tr>
									        </thead>
									        <tbody>
									        	<?php  
									        	foreach ($dpkh as $kl => $rl) {
									        		echo '<tr>';
									        			echo '<td>'.$rl->nik.'</td>';
									        			echo '<td>'.getNamaPerson($rl->id_l).'</td>';
									        			echo '<td>'.getNamaPerson($rl->id_p).'</td>';
									        			echo '<td>';
									        			$tgl_lahir = getFieldPerson('tgl_lahir',$rl->id_p);
									        			$wus = wus_tahun($tgl_lahir->tgl_lahir);
									        			if($wus > 13 && $wus < 45)
									        			{
									        				echo 'Pasangan Usia Subur (Pus) <br>' . $wus .' Tahun';
									        			}
									        			else{
									        				echo 'Sudah Tidak Termasuk (Pus)';
									        			}
									        			echo '</td>';
									        			echo '<td>'.tanggal_indo($rl->tgl_nikah, true).'</td>';
									        			echo '<td>'.status_nikah($rl->status).'</td>';
									        		echo '</tr>';
									        	}
									        	?>
									    	</tbody>
										</table>
		    						</div>
	    						</div>
	    					</div>

	    					<div class="col-md-12">
	    						<div class="box" style="margin-top: 15px">
	    							<div class="table-responsive">
	    								<table class="table table-bordered m-0">
									        <thead>
									          <tr>
									          	<th colspan="20" class="text-center text-primary"><strong><h5>HASIL PEMERIKSAAN IBU HAMIL</h5></strong></th>
									          </tr>
									          <tr>
									            <th>Nama Ibu Hamil</th>
									            <th>Tanggal Input</th>
									            <th>BKS I</th>
									            <th>BKS II</th>
									            <th>BKS III</th>
									            <th>T I</th>
									            <th>T II</th>
									            <th>Kapsul Yodium</th>
									            <th>Jan</th>
									            <th>Feb</th>
									            <th>Mar</th>
									            <th>Apr</th>
									            <th>Mei</th>
									            <th>Jun</th>
									            <th>Jul</th>
									            <th>Agu</th>
									            <th>Sep</th>
									            <th>Okt</th>
									            <th>Nov</th>
									            <th>Des</th>
									          </tr>
									        </thead>
									        <tbody>

									        </tbody>
									    </table>
	    							</div>
	    						</div>
	    					</div>

	    					<div class="col-md-12">
    							<div class="box" style="margin-top: 15px">
		    						<div class="table-responsive">
			    						<form method="post" action="<?php echo base_url('ad_ih_view/action/svp');?>">
			    							<input type="hidden" name="id_ih" value="<?php echo $id_ih; ?>">
			    							<table class="table table-bordered m-0">
			    								<tr>
			    									<th colspan="3" class="text-center text-primary"><strong><h5>FORMULIR PEMERIKSAAN IBU HAMIL</h5></strong></th>
			    								</tr>
			    								<tr>
			    									<th colspan="3" >PIL TAMBAH DARAH PADA KEHAMILAN</th>
			    								</tr>
										        <tr>
										        	<th>Bks I,II,III</th>
										        	<td style="width: 1rem;">:</td>
										        	<td>
										        		<div class="col-md-12">
										        			<div class="row">
										        				<input type="text" name="bks1" class="col-sm-2 form-control input-sm" placeholder="BKS I"> &nbsp; 
												        		<input type="text" name="bks2" class="col-sm-2 form-control input-sm" placeholder="BKS II"> &nbsp; 
												        		<input type="text" name="bks3" class="col-sm-2 form-control input-sm" placeholder="BKS III">
										        			</div>	
										        		</div>
										        		</td>
										        </tr>
										        <tr>
			    									<th colspan="3">IMUNISASI TT TGL/BLN</th>
			    								</tr>
										        <tr>
										        	<th>I,II</th>
										        	<td style="width: 1rem;">:</td>
										        	<td>
										        		<div class="col-md-12">
										        			<div class="row">
										        				<input type="date" class="form-control input-sm col-sm-3" name="t1" > &nbsp;
										        				<input type="date" class="form-control input-sm col-sm-3" name="t2">
										        			</div>
										        		</div>
										        	</td>
										        </tr>
										        <tr>
										        	<th>Kapsul Yodium</th>
										        	<td style="width: 1rem;">:</td>
										        	<td>
										        		<div class="col-md-12">
										        			<div class="row">
										        				<input type="text" name="yd" class="col-sm-6 form-control input-sm" placeholder="">
										        			</div>
										        		</div>
										        	</td>
										        </tr>
										        <tr>
			    									<th colspan="3">HASIL PENIMBANGAN</th>
			    								</tr>
			    								<tr>
										        	<th>Berat Badan/Umur</th>
										        	<td style="width: 1rem;">:</td>
										        	<td>
										        		<div class="col-md-12">
										        			<div class="row">
										        				<input type="text" name="bb" class="col-sm-3 form-control input-sm" placeholder="Berat Badan"> &nbsp;
										        				<input type="text" name="umr" class="col-sm-3 form-control input-sm" placeholder="Umur">
										        			</div>
										        		</div>
										        	</td>
										        </tr>

										        <tr>
										        	<th colspan="3">
										        		<div class="col-md-12">
										        			<div class="col-md-12 text-right">
										        				<button type="submit" class="btn primary btn-sm"><i class="fa fa-arrow-circle-right"></i> Save</button>
										        			</div>
										        		</div>
										        	</th>
										        </tr>
											</table>
										</form>
		    						</div>
	    						</div>
	    					</div>

	    					<div class="col-md-12">
    							<div class="box" style="margin-top: 15px">
		    						<div class="table-responsive">
			    						<form method="post" action="<?php echo base_url('ad_ih_view/action/svp');?>">
			    							<input type="hidden" name="id_ih" value="<?php echo $id_ih; ?>">
			    							<table class="table table-bordered m-0">
			    								<tr>
			    									<th colspan="3" class="text-center text-primary"><strong><h5>FORMULIR IBU MELAHIRKAN & BAYI LAHIR</h5></strong></th>
			    								</tr>
			    								<tr>
			    									<th colspan="3" >MELAHIRKAN</th>
			    								</tr>
										        <tr>
										        	<th>Tanggal</th>
										        	<td style="width: 1rem;">:</td>
										        	<td>
										        		<div class="col-md-12">
										        			<div class="row">
										        				<input type="date" class="form-control input-sm col-sm-3" name="t1" >
										        			</div>	
										        		</div>
										        		</td>
										        </tr>
										        <tr>
										        	<th>Ditolong Oleh</th>
										        	<td style="width: 1rem;">:</td>
										        	<td>
										        		<div class="col-md-12">
										        			<div class="row">
										        				<input type="text" name="yd" class="col-sm-3 form-control input-sm" placeholder="Tenaga Kesehatan"> &nbsp;
										        				<input type="text" name="yd" class="col-sm-3 form-control input-sm" placeholder="Tenaga Dukun">
										        			</div>
										        		</div>
										        	</td>
										        </tr>
										        <tr>
										        	<th colspan="3">
										        		<div class="col-md-12">
										        			<div class="col-md-12 text-right">
										        				<button type="submit" class="btn primary btn-sm"><i class="fa fa-arrow-circle-right"></i> Save</button>
										        			</div>
										        		</div>
										        	</th>
										        </tr>
											</table>
										</form>
		    						</div>
	    						</div>
	    					</div>

    					</div>                    
                    </div>
				</div>
			</section>

			<div class="modal fade animate" id="modalMod" style="display: none">
		        <div class="modal-dialog fade-left modal-lg">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <h4 class="modal-title">Pendataan Ibu Hamil</h4>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                </div>
		                <div class="modal-body">
		                    <form action="<?php echo base_url('ad_ih_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="id">
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Nama Ibu Hamil</label>
		                            <div class="col-sm-12">
		                                <?php
		                                echo htmlSelectFromArray($ih, 'name="id_ibu_hamil" required="true" style="width:100%" class="form-control input-sm select2"', true);
		                                ?>
		                            </div>
		                        </div>

		                        <div class="form-group">
		                        	<div class="col-sm-12" id="info_p">
		                        		
		                        	</div>
		                        </div>

		                        <div class="form-group">
		                            <label for="tun" class="col-sm-4 control-label">Usia Hamil</label>
		                            <div class="col-sm-12">
		                                <input type="text" class="form-control input-sm" name="usia_hamil" placeholder="Usia Hamil" required="true">
		                            </div>
		                        </div>
		                        
		                        <div class="form-group">
		                            <label for="tun" class="col-sm-3 control-label">Tanggal Mendaftar</label>
		                            <div class="col-sm-12">
		                                 <input type="date" class="form-control input-sm" name="tgl_daftar" required="true">
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
			var link_url = '<?php echo base_url('ad_nikah_view/action');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_ih_view');?>')) {
		        history.go(-1);
		    }

		    

		    function formAdd() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_ih_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="id_ibu_hamil"]').prop('required', true);

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

		</script>