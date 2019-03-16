		
		<!-- Content Header (Page header) -->
		<div class="padding">
			<div class="row">
				<div class="col-md-12">
					<?php  
		        	echo view_relasi($silsilah);
		        	?>	
				</div>
			</div>
		</div>

		<div class="modal fade animate" id="modalMod" style="display: none">
	        <div class="modal-dialog fade-left modal-lg">
	        	<div class="modal-content">
	        		<div class="modal-header">
	                    <h4 class="modal-title">Data Detail Penduduk</h4>
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	                </div>
	                <div class="modal-body" id="data_penduduk">
	                	
	                </div>
	        	</div>
	        </div>
		</div>
		<!-- end/Content Header (Page Header) -->
		<script>
			var link_url = '<?php echo base_url('ad_person_view/action/');?>';
			if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>', '<?php echo aksesName('ad_relasi_view');?>')) {
		        history.go(-1);
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

		            $('#modalMod').modal();
		        }
		    }
		</script>