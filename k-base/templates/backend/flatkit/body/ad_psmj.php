		<div class="row-col">
		  <div class="col-sm w w-auto-xs light lt bg-auto">
		    <div class="padding pos-rlt">
		      <div>
		        <button class="btn btn-sm white pull-right hidden-sm-up" ui-toggle-class="show" target="#inbox-menu"><i class="fa fa-bars"></i></button>
		        <a href class="btn btn-sm white w-xs">Tulis Pesan</a>
		      </div>
		      <div class="hidden-xs-down m-t" id="inbox-menu">
		        <div class="nav-active-primary">
		          <div class="nav nav-pills nav-sm flex-column">
		              <a class="nav-link">
		                Kotak Masuk
		              </a>
		              <a class="nav-link active">
		                Dijawab
		              </a>
		          </div>
		        </div>        
		      </div>
		    </div>
		  </div>
		  <div class="col-sm">
		    <div ui-view class="padding pos-rlt">
		      <div>
		        	<div class="b-b">
		        		<h2 class="_300"><?php echo $judul[0]->judul; ?></h2>
		        	</div>
		        	<?php  
		        	foreach ($r as $idrw => $rw) {
		        		echo '<div class="p-y">';
		        			echo '<img class="img-circle w-32 m-r-sm" src="'.base_url('assets/assets_flatkit/images/a0.jpg').'">';
		        			
		        				if($rw->pengirim)
			        			{
			        				echo '<a href="" class="ng-binding"> Pasien : ';
			        					echo $nm_pasien[$rw->pengirim];
			        				echo '</a> | ';
			        			}
			        			else
			        			{
			        				echo '<a href="" class="ng-binding"> Admin : ';
			        					echo $nm_admin[$rw->penjawab];
			        				echo '</a> | ';
			        			}
		        			echo '<span class="text-xs ng-binding">On '.$rw->masuk_tgl.'</span>';
		        		echo '</div>';

		        		echo '<div class="p-y ng-binding">';
		        			echo $rw->isi;
		        		echo '</div>';
		        	}
		        	?>
		        	<form action="<?php echo base_url('ps_psm_view/action/save'); ?>" method="post" id="modForm" class="form-horizontal" enctype="multipart/form-data">
		        		<input type="hidden" name="id">
		        		<input type="hidden" name="idpesan" value="<?php echo $idpesan; ?>">
			        	<div>
			        		<div class="box">
			        			<textarea class="form-control" name="jawab"></textarea>
			        		</div>
			        		<div class="pull-right">
			        			<button class="btn btn-info" id="Kirim">Kirim</button>
			        		</div>
			        	</div>
		        	</form>
		    </div>
		  </div>
		</div>
		<script>
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ps_psm_view');?>')) {
		        history.go(-1);
		    }
		</script>