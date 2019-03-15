    <!-- ############ PAGE END-->

    </div>
  </div>
  <!-- / content -->
</div>
<style>
    #tblHist td, #tblHist th{
        font-size:0.8rem;
        padding:0.4rem;
    }
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$('#foto').html('<img src="<?php echo getFotoVal($this->session->userdata('ID'));?>" alt="" class="w-40 r">');
		$('#foto_head').html('<img src="<?php echo getFotoVal($this->session->userdata('ID'));?>"><i class="on b-white bottom"></i>');
		$('#online').html('<?php echo getInfoUser($this->session->userdata('ID'),'online')?>');
	});
</script>
</body>
</html>