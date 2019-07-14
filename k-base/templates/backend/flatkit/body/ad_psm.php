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
		              <a class="nav-link active">
		                Kotak Masuk
		              </a>
		          </div>
		        </div>        
		      </div>
		    </div>
		  </div>
		  <div class="col-sm">
		    <div ui-view class="padding pos-rlt">
		      <!-- <a href class="md-btn md-fab md-fab-bottom-right pos-fix red">
		        <i class="material-icons i-24 text-white">&#xe150;</i>
		      </a> -->
		      <div>
		        <!-- header -->
		        <div class="m-b">
		          <div class="btn-group pull-right">
		            <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url($this->uri->segment(1).'/action/view/'); ?>' + this.value : '';">
		          </div>
		          <div class="btn-toolbar">
		            <div class="btn-group dropdown">
		              <button class="btn white btn-sm dropdown-toggle" data-toggle="dropdown">
		                <span class="dropdown-label">Filter</span>                    
		                <span class="caret"></span>
		              </button>
		              <div class="dropdown-menu text-left text-sm">
		                <a class="dropdown-item" href="<?php echo base_url($this->uri->segment(1).'/action/view/f/0/0/Y');?>">Dibaca</a>
		                <a class="dropdown-item" href="<?php echo base_url($this->uri->segment(1).'/action/view/f/0/0/T');?>">Belum Dibaca</a>
		              </div>
		            </div>
		            <div class="btn-group">
		              <!-- <button class="btn btn-sm white" data-toggle="tooltip" data-placement="bottom" data-title="Refresh" data-original-title="" title=""><i class="fa fa-refresh"></i></button> -->
		            </div>
		          </div>
		        </div>
		        <!-- / header -->

		        <!-- list -->
		        <div class="list white">
		          <?php  
		          foreach ($r as $idrw => $rw) {
		          	echo '<div class="list-item b-l b-l-2x b-info">';
			          	echo '<div class="list-left">';
			          		echo '<span class="w-40 avatar">';
			          			echo '<img src="'.base_url('assets/assets_flatkit/images/a0.jpg').'">';
			          		echo '</span>';
			          	echo '</div>';

			          	echo '<div class="list-body">';
			          		echo '<div class="pull-right text-muted text-xs">';
			          			echo '<span class="hidden-xs">'.tanggal_indo($rw->masuk_tgl,true).'</span>';
			          			//echo '<i class="fa fa-paperclip m-l-sm"></i>';
			          		echo '</div>';
			          		echo '<div>';
			          			echo '<a href="'.base_url($this->uri->segment(1).'/action/jawab/'.$rw->id.'/'.$rw->idpesan.'/'.$rw->pengirim).'" class="_500">'.$rw->judul.'</a>';
			          			if($rw->status == 1)
			          			{
			          				echo '<span class="label label-xs m-l-sm text-u-c">Terkirim</span>';
			          			}
			          		echo '</div>';
			          		echo '<div class="text-ellipsis text-muted text-sm">';
			          			echo substr($rw->isi,0,100);
			          		echo '</div>';
			          	echo '</div>';
		          	echo '</div>';
		          }

		          if (count($r) == 0)
		          {
		          	echo '<p class="text-center">Tidak ditemukan data.</p>';
		          }
		          ?>
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
	            		halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ps_psm_view/action/view/') . $f . '/' . $l . '/\'+this.value"', false, $p); ?>
	            	</div>

	            	<div class="btn-toolbar">
	            		<?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ps_psm_view/action/view/') . $f . '/\'+this.value"/0', true, $l); ?>
	            	</div>
		        </div>
		    </div>
		  </div>
		</div>
		<script>
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ps_psm_view');?>')) {
		        history.go(-1);
		    }

		    $(document).ready(function () {
		    	//alert('dsfdsf');
		        $('.tr_mod').click(function () {
		            if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>','<?php echo aksesName('ps_psm_edit');?>')) {
		                var tr = $(this).parent();

		                $('#modalMod :input').val('');
		                $('#modalMod [name="id"]').val(tr.data('id'));
		                $('#modalMod [name="idpesan"]').val(tr.data('idpesan'));
		                $('#modalMod .judul').html(tr.data('judul'));
		                $('#modalMod .isi').html(tr.data('isi'));
		                //$('#modalMod [name="jawaban"]').val(tr.data('jawaban'));

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
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ps_psm_add');?>')) {
		            $('#modalMod :input').val('');
		            $('#modalMod [name="isi"]').prop('required', true);
		            $('.sm-war-kosong').hide();

		            $('#modalMod').modal();
		        }
		    }

		    function formDelete() {
		        if (checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ps_psm_delete');?>')) {
		            if (confirm('Are you sure want to delete this data?')) {
		                var pkC = $('[name="id[]"]:checked'), idC = [];

		                for (var ip = 0; ip < pkC.length; ip++) {
		                    idC.push(pkC.eq(ip).val());
		                }

		                window.location = '<?php echo base_url('ps_psm_view/action/delete/')?>' + (idC.join('k'));
		            }
		        }
		    }
		</script>