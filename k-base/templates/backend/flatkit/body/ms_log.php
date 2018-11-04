		<!-- Content Header (Page header) -->
		<div class="padding">
			<section class="content">
				<div class="row">
    				<div class="col-md-12">

                        <div class="row">

                            <div class="col-md-9">

                            </div>
                            <div class="col-md-3">
                                <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($f!='f') ? $f : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = '<?php echo base_url('ms_user_view/log/') . $id; ?>/' + this.value : '';">
                            </div>
                        </div>

                        <div class="box" style="margin-top: 15px">
                        	<table class="table table-hover table-bordered">
                        		<thead>
		                            <tr>
		                                <th style="width: 10px;" class="text-center">No.</th>
		                                <th style="width: 50px">IP</th>
		                                <th>Computer Name</th>
		                                <th style="width: 150px">Date</th>
		                                <th>User Agent</th>
		                                <th>Platform</th>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<?php  
		                        	foreach ($r as $idrw => $rw) {
		                                echo '<tr style="cursor:pointer" ' . showValRec($rw) . '>';
		                                echo '<td class="text-center tr_mod">' . ($no++) . '.</td>';
		                                echo '<td class="tr_mod">' . $rw->ip . '</td>';
		                                echo '<td class="tr_mod">' . $rw->dns . '</td>';
		                                echo '<td class="tr_mod">' . date('d F Y H:i:s', strtotime($rw->date)) . '</td>';
		                                echo '<td class="tr_mod">' . $rw->ua . '</td>';
		                                echo '<td class="tr_mod">' . (isMobile($rw->ua) ? 'Mobile Web' : 'Desktop') . '</td>';
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
		                        <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" style="width:100px;" onchange="window.location=\'' . base_url('ms_user_view/log/') . $id .'/'. $f . '/\'+this.value"/0', true, $l); ?>
		                    </div>
		                    <div class="col-md-7 text-right">
		                        halaman : <?php echo htmlSelectFromArray($paging, 'name="p" style="width:100px" onchange="window.location=\'' . base_url('ms_user_view/log/') . $id .'/'. $f . '/' . $l . '/\'+this.value"', false, $p); ?>
		                    </div>
		                    <div class="col-md-2 text-center">
		                        Total Data : <?php echo $jum; ?>
		                    </div>
                        </div>
                    
                    </div>
				</div>
			</section>

		</div>
		<!-- end/Content Header (Page Header) -->
		<script>
			if (!checkPrivilege(<?php echo $this->session->userdata('id_level');?>, '<?php echo aksesName('ms_user_log');?>')) {
		        history.go(-1);
		    }
		</script>