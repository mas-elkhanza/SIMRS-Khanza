<?php
		 
	require_once 'dbconfig.php';
	
	if (isset($_REQUEST['id'])) {
			
		$id = intval($_REQUEST['id']);
		$query = "SELECT * FROM tbl_members WHERE user_id=:id";
		$stmt = $DBcon->prepare( $query );
		$stmt->execute(array(':id'=>$id));
		$row=$stmt->fetch(PDO::FETCH_ASSOC);
		extract($row);
			
		?>
			
		<div class="table-responsive">
		
		<table class="table table-striped table-bordered">
		    <tr>
			    <th>First Name</th>
				<td><?php echo $first_name; ?></td>
			</tr>
			<tr>
				<th>Last Name</th>
				<td><?php echo $last_name; ?></td>
			</tr>
			<tr>
				<th>Email ID</th>
				<td><?php echo $email; ?></td>
			</tr>
			<tr>
				<th>Position</th>
				<td><?php echo $position; ?></td>
			</tr>
			<tr>
				<th>Office</th>
				<td><?php echo $office; ?></td>
			</tr>
		</table>
			
		</div>
			
		<?php				
	}