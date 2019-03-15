<!--
	
	connect with me
	==================
	Facebook : http://facebook.com/pradeepdkhodke
	Twitter  : http://twitter.com/pradeepkhodke
	Google+  : http://plus.google.com/+pradeepkhodked
	==================
	
	Get updates via 
	==================
	Facebook : http://facebook.com/CodingCage
	Twitter  : http://twitter.com/CodingCage
	Google+  : http://plus.google.com/+CodingCagecom
	
-->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Bootstrap Modal with Dynamic MySQL Data using Ajax & PHP</title>
<link rel="stylesheet" href="../assets/css/bootstrap.min.css">

</head>

<body>

	<div class="container">
    
    	<div class="page-header">
        <h3><a href="http://www.codingcage.com/2016/09/bootstrap-modal-with-dynamic-mysql-data.html" target="_blank">Modal with Dynamic MySQL Data using Ajax & PHP</a></h3>
        </div>
        
        <div class="row">
        
        	<div class="col-lg-12">
            	
            		<table class="table table-striped table-bordered">
            		
            		<thead>
            			<tr>
            				<th>Full Name</th>
            				<th>View Profile</th>
            			</tr>
            		</thead>
            		
            		<tbody>
            		
            		<?php
            		
            		require_once 'dbconfig.php';
            		
            		$query = "SELECT * FROM pegawai";
            		$stmt = $DBcon->prepare( $query );
            		$stmt->execute();
            		while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
						?>
						<tr>
						<td><?php echo $row['nip']."&nbsp;".$row['nama']; ?></td>
						<td>
						<button data-toggle="modal" data-target="#view-modal" data-id="<?php echo $row['nip']; ?>" id="getUser" class="btn btn-sm btn-info"><i class="glyphicon glyphicon-eye-open"></i> View</button>
						</td>
						</tr>
						<?php
					}
            		?>
            		
            		</tbody>
            		</table>      
                
            </div>
        
        </div>
        
        
        
        
        <div id="view-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
             <div class="modal-dialog"> 
                  <div class="modal-content"> 
                  
                       <div class="modal-header"> 
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> 
                            <h4 class="modal-title">
                            	<i class="glyphicon glyphicon-user"></i> User Profile
                            </h4> 
                       </div> 
                       <div class="modal-body"> 
                       
                       	   <div id="modal-loader" style="display: none; text-align: center;">
                       	   	<img src="ajax-loader.gif">
                       	   </div>
                            
                           <!-- content will be load here -->                          
                           <div id="dynamic-content"></div>
                             
                        </div> 
                        <div class="modal-footer"> 
                              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>  
                        </div> 
                        
                 </div> 
              </div>
       </div><!-- /.modal -->    
    
    </div>


<script src="../assets/jquery-1.12.4.min.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>


<script>
$(document).ready(function(){
	
	$(document).on('click', '#getUser', function(e){
		
		e.preventDefault();
		
		var uid = $(this).data('id');   // it will get id of clicked row
		
		$('#dynamic-content').html(''); // leave it blank before ajax call
		$('#modal-loader').show();      // load ajax loader
		
		$.ajax({
			url: 'getuser.php',
			type: 'POST',
			data: 'id='+uid,
			dataType: 'html'
		})
		.done(function(data){
			console.log(data);	
			$('#dynamic-content').html('');    
			$('#dynamic-content').html(data); // load response 
			$('#modal-loader').hide();		  // hide ajax loader	
		})
		.fail(function(){
			$('#dynamic-content').html('<i class="glyphicon glyphicon-info-sign"></i> Something went wrong, Please try again...');
			$('#modal-loader').hide();
		});
		
	});
	
});

</script>

</body>
</html>