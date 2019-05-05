<?php  
global $SConfig;
?>
<div class="padding">
	<div class="row">
		<div class="col-xs-12 col-sm-6">
	        <div class="box p-a">
	          <div class="pull-left m-r">
	            <span class="w-48 rounded  accent">
	              2
	            </span>
	          </div>
	          <div class="clear">
	            <h4 class="m-0 text-lg _300"><a href>Jumlah Mendaftar</a></h4>
	            <small class="text-muted">e-sip.com</small>
	          </div>
	        </div>
	    </div>
	    <div class="col-xs-12 col-sm-6">
	        <div class="box p-a">
	          <div class="pull-left m-r">
	            <span class="w-48 rounded primary">
	              18
	            </span>
	          </div>
	          <div class="clear">
	            <h4 class="m-0 text-lg _300"><a href>Jumlah Kunjungan</a></h4>
	            <small class="text-muted">e-sip.com</small>
	          </div>
	        </div>
	    </div>
	</div>
	<section class="content">
		<div class="row">
			<div class="col-md-12">

	            <div class="row">
	            	<div class="col-md-7">
	            		<a class="btn btn-fw warn" href="<?php echo base_url('ad_ih_view/action/view'); ?>">
					        <span>Kembali</span>
					    </a>
	            		&nbsp;
	            		<a class="btn btn-fw dark" href="<?php echo base_url('ad_ih_view/action/view/'.$idp); ?>">
					        <span class="text-white"><i class="fa fa-file-excel-o"></i> Download</span>
					    </a>
					    &nbsp;
	            		<a class="btn btn-fw black" href="<?php echo base_url('ad_ih_view/action/view/'.$idp); ?>">
					        <span class="text-white"><i class="fa fa-line-chart"></i> Statistik</span>
					    </a>
	            	</div>
	            	<div class="col-md-2">
	            		
	            	</div>
	                <div class="col-md-3">
	                    <input type="text" class="form-control input-sm right" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ad_ih_view/action/detail/'); ?>' + this.value +'/0/0/<?php echo $idp .'/'. $idr;?>' : '' ; ">
	                </div>
	            </div>

	            <div class="box" style="overflow-y:auto; overflow-x: scroll; margin-top: 10px;">
	            	<table class="table table-hover table-bordered">
                		<thead>
                          <tr>
                            <th width="10" rowspan="3" class="text-center">No</th>
                            <th rowspan="3" class="text-center">Nama Ibu Hamil</th>
                            <th rowspan="3" class="text-center">Umur</th>
                            <th rowspan="3" class="text-center">KLP Dasa Wisma</th>
                            <th colspan="2" class="text-center">Registrasi</th>
                            <th rowspan="3" class="text-center">Hamil Ke</th>
                            <th colspan="3" class="text-center">Pil Tambah Darah Pada Kehamilan</th>
                            <th colspan="2" class="text-center">Imunisasi TT TGL/Bln</th>
                            <th rowspan="3" class="text-center">Kapsul Yodium</th>
                            <th colspan="12" class="text-center">Hasil Penimbangan</th>
                            <th rowspan="3" class="text-center">Resiko</th>
                            <th rowspan="3" class="text-center">Masuk Oleh</th>
                          </tr>
                          <tr>
                            <th rowspan="2" class="text-center">Tgl</th>
                            <th rowspan="2" class="text-center">Usia Hamil</th>
                            <th rowspan="2" class="text-center">BKS I</th>
                            <th rowspan="2" class="text-center">BKS II</th>
                            <th rowspan="2" class="text-center">BKS III</th>
                            <th rowspan="2" class="text-center">I</th>
                            <th rowspan="2" class="text-center">II</th>
                            <th rowspan="2" class="text-center">Jan</th>
                            <th rowspan="2" class="text-center">Feb</th>
                            <th rowspan="2" class="text-center">Mar</th>
                            <th rowspan="2" class="text-center">Apr</th>
                            <th rowspan="2" class="text-center">Mei</th>
                            <th rowspan="2" class="text-center">Jun</th>
                            <th rowspan="2" class="text-center">Jul</th>
                            <th rowspan="2" class="text-center">Agu</th>
                            <th rowspan="2" class="text-center">Sep</th>
                            <th rowspan="2" class="text-center">Okt</th>
                            <th rowspan="2" class="text-center">Nov</th>
                            <th rowspan="2" class="text-center">Des</th>
                          </tr>
                         
                        </thead>
                        <tbody>
                        	<?php 
                        		//print_r($_SESSION);
                        		foreach ($r as $idrw => $rw) {
	                                echo '<tr>';
	                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
	                                echo '<td class="tr_mod">'.$rw->nm_lengkap.'</td>';
	                                echo '<td class="tr_mod">'.UsiaTahun($rw->tgl_lahir).'</td>';
	                                echo '<td class="tr_mod">'.getNamaPos($rw->id_posyandu).'</td>';
	                                echo '<td class="tr_mod">'.tanggal_indo($rw->masuk_tgl).'</td>';
	                                echo '<td class="tr_mod">'.$rw->u_hamil.'</td>';
	                                echo '<td class="tr_mod">'.$rw->hamil_ke.'</td>';
	                                echo '<td class="tr_mod">'.$rw->bks1.'</td>';
	                                echo '<td class="tr_mod">'.$rw->bks2.'</td>';
	                                echo '<td class="tr_mod">'.$rw->bks3.'</td>';
	                                echo '<td class="tr_mod">'.@tanggal_indo($rw->imunisasi1).'</td>';
	                                echo '<td class="tr_mod">'.@tanggal_indo($rw->imunisasi2).'</td>';
	                                echo '<td class="tr_mod">'.$rw->kyd.'</td>';
	                                echo '<td class="tr_mod">'.$rw->jan_bb.' /Kg <br>'.$rw->jan_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->feb_bb.' /Kg <br>'.$rw->feb_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->mar_bb.' /Kg <br>'.$rw->mar_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->apr_bb.' /Kg <br>'.$rw->apr_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->may_bb.' /Kg <br>'.$rw->may_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->jun_bb.' /Kg <br>'.$rw->jun_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->jul_bb.' /Kg <br>'.$rw->jul_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->aug_bb.' /Kg <br>'.$rw->aug_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->sep_bb.' /Kg <br>'.$rw->sep_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->oct_bb.' /Kg <br>'.$rw->oct_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->nov_bb.' /Kg <br>'.$rw->nov_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$rw->dec_bb.' /Kg <br>'.$rw->dec_um.' /Th</td>';
	                                echo '<td class="tr_mod">'.$SConfig->_resiko[$rw->resiko].'</td>';
	                                echo '<td class="tr_mod">'.getInfoUser($rw->masuk_oleh,'username')[0]->username.'</td>';
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
	                    <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ad_asuransi_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
	                </div>
	                <div class="col-md-7 text-right">
	                    halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ad_asuransi_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
	                </div>
	                <div class="col-md-2 text-center">
	                    Total Data : <?php echo $jum; ?>
	                </div>
	            </div>
	        
	        </div>
		</div>
	</section>
</div>