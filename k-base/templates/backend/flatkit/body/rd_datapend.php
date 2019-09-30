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
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('rd_datapen_view/action/view/'); ?>' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th style="width: 30px;" class="text-center">No.</th>
		                                <th>Lansia (L)</th>
		                                <th>Lansia (P)</th>
		                                <th>Pus</th>
		                                <th>Wus</th>
		                                <th>Balita (L)</th>
		                                <th>Balita (P)</th>
		                                <th>Bayi (L)</th>
		                                <th>Bayi (P)</th>
		                                <th>JK (L)</th>
		                                <th>JK (P)</th>
		                                <th>Gol Darah (A)</th>
		                                <th>Gol Darah (Ab)</th>
		                                <th>Gol Darah (B)</th>
		                                <th>Gol Darah (O)</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php 
		                        		foreach ($lsd as $idrw => $rw) {
		                        			echo '<pre>';
		                        			print_r($rw);
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
	                    	/*$p = $p ? $p + 1 : 1;

		                    $paging = array();
		                    for ($i = 1; $i <= ceil($jum / $l); $i++) {
		                        $paging[$i] = $i;
		                    }*/
	                    	?>

	                    	<div class="col-md-3 text-left">
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('rd_datapen_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <!-- <div class="col-md-7 text-right">
		                        halaman : <?php //echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('rd_datapen_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
		                    </div> -->
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
		                    <form action="<?php echo base_url('rd_datapen_view/action/save') ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		                        <input type="hidden" name="id">
		                        <input type="hidden" name="kabu">
		                        <input type="hidden" name="kec">
		                        <input type="hidden" name="des">

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
										<div class="col-sm-6">
											<div class="form-group">
					                            <label for="tun" class="col-sm-12 control-label">Golongan Darah</label>
					                            <div class="col-sm-12">
					                               <?php
					                                echo htmlSelectFromArray($gol_darah, 'name="gol_darah" required="true" class="form-control input-sm"', true);
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
			var link_url = '<?php echo base_url('rd_datapen_view/action/');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('rd_datapen_view');?>')) {
		        history.go(-1);
		    }
		</script>