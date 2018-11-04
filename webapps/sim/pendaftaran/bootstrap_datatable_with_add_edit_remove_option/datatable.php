<?php

//For more Info: Please visit: http://www.discussdesk.com/bootstrap-datatable-with-add-edit-remove-option-in-php-mysql-ajax.htm

	// VARIABLES
	$aColumns = array('id', 'name', 'email', 'mobile', 'start_date');
	$sIndexColumn = "id";
	$sTable = "TABLE_NAME";
	$gaSql['user'] = "USER_NAME";
	$gaSql['password'] = "PASSWORD";
	$gaSql['db'] = "DATABASE_NAME";
	$gaSql['server'] = "localhost";


	// DATABASE CONNECTION
	function dbinit(&$gaSql) {
		// ERROR HANDLING
		function fatal_error($sErrorMessage = '') {
			header($_SERVER['SERVER_PROTOCOL'] .' 500 Internal Server Error');
			die($sErrorMessage);
		}

		// MYSQL CONNECT
		if ( !$gaSql['link'] = @mysql_connect($gaSql['server'], $gaSql['user'], $gaSql['password']) ) {
			fatal_error('Could not open connection to server');
		}

		// MYSQL DATABASE SELECT
		if ( !mysql_select_db($gaSql['db'], $gaSql['link']) ) {
			fatal_error('Could not select database');
		}
	}

	// AJAX EDIT FROM JQUERY
	if ( isset($_GET['edit']) && 0 < intval($_GET['edit']) ) {
		dbinit($gaSql);

		// SAVE DATA
		if ( isset($_POST) ) {
			$p = $_POST;
			foreach ( $p as &$val ) $val = mysql_real_escape_string($val);
			if ( !empty($p['firstname']) && !empty($p['email']) && !empty($p['mobile']) )
				@mysql_query(" UPDATE $sTable SET name = '" . $p['firstname'] . "', email = '" . $p['email'] . "', mobile = '" . $p['mobile'] . "' WHERE id = " . intval($_GET['edit']));
		}

		// GET DATA
		$query = mysql_query(" SELECT * FROM $sTable WHERE $sIndexColumn = " . intval($_GET['edit']), $gaSql['link']);
		die(json_encode(mysqli_fetch_assoc($query)));
	}

	// AJAX ADD FROM JQUERY
	if ( isset($_GET['add']) && isset($_POST) ) {
		dbinit($gaSql);

		$p = $_POST;
		foreach ( $p as &$val ) $val = mysql_real_escape_string($val);
		if ( !empty($p['firstname']) && !empty($p['email']) && !empty($p['mobile']) ) {
			@mysql_query(" INSERT INTO $sTable (name, email, mobile) VALUES ('" . $p['firstname'] . "', '" . $p['email'] . "', '" . $p['mobile'] . "')");
			$id = mysql_insert_id();
			$query = mysql_query(" SELECT * FROM $sTable WHERE $sIndexColumn = " . $id, $gaSql['link']);
			die(json_encode(mysqli_fetch_assoc($query)));
		}
	}

	// AJAX REMOVE FROM JQUERY
	if ( isset($_GET['remove']) && 0 < intval($_GET['remove']) ) {
		dbinit($gaSql);

		// REMOVE DATA
		@mysql_query(" DELETE FROM $sTable WHERE id = " . intval($_GET['remove']));
	}


	// AJAX FROM JQUERY
	if ( isset($_GET['ajax']) ) {
		dbinit($gaSql);

		// QUERY LIMIT
		$sLimit = "";
		if ( isset($_GET['iDisplayStart']) && $_GET['iDisplayLength'] != '-1' ) {
			$sLimit = "LIMIT " . intval($_GET['iDisplayStart']) . ", " . intval($_GET['iDisplayLength']);
		}

		// QUERY ORDER
		$sOrder = "";
		if ( isset($_GET['iSortCol_0']) ) {
			$sOrder = "ORDER BY ";
			for ( $i = 0; $i < intval($_GET['iSortingCols']); $i++ ) {
				if ( $_GET['bSortable_' . intval($_GET['iSortCol_' . $i])] == "true" ) {
					$sOrder .= $aColumns[intval($_GET['iSortCol_' . $i])] . " " . ( $_GET['sSortDir_' . $i] === 'asc' ? 'asc' : 'desc' ) . ", ";
				}
			}
			$sOrder = substr_replace($sOrder, "", -2);
			if ( $sOrder == "ORDER BY" ) $sOrder = "";
		}

		// QUERY SEARCH
		$sWhere = "";
		if ( isset($_GET['sSearch']) && $_GET['sSearch'] != "" ) {
			$sWhere = "WHERE (";
			for ( $i = 0; $i < count($aColumns); $i++ ) {
				if ( isset($_GET['bSearchable_' . $i]) && $_GET['bSearchable_' . $i] == "true" ) {
					$sWhere .= $aColumns[$i] . " LIKE '%" . mysql_real_escape_string($_GET['sSearch']) . "%' OR ";
				}
			}
			$sWhere = substr_replace($sWhere, "", -3);
			$sWhere .= ')';
		}

		// BUILD QUERY
		for ( $i = 0; $i < count($aColumns); $i++ ) {
			if ( isset($_GET['bSearchable_' . $i]) && $_GET['bSearchable_' . $i] == "true" && $_GET['sSearch_' . $i] != '' ) {
				if ( $sWhere == "" ) $sWhere = "WHERE ";
				else $sWhere .= " AND ";
				$sWhere .= $aColumns[$i] . " LIKE '%" . mysql_real_escape_string($_GET['sSearch_' . $i]) . "%' ";
			}
		}

		// FETCH
		$sQuery = " SELECT SQL_CALC_FOUND_ROWS " . str_replace(" , ", " ", implode(", ", $aColumns)) . " FROM $sTable $sWhere $sOrder $sLimit ";
		$rResult = mysql_query($sQuery, $gaSql['link']) or fatal_error('MySQL Error: ' . mysql_errno());
		$sQuery = " SELECT FOUND_ROWS() ";
		$rResultFilterTotal = mysql_query($sQuery, $gaSql['link']) or fatal_error('MySQL Error: ' . mysql_errno());
		$aResultFilterTotal = mysqli_fetch_array($rResultFilterTotal);
		$iFilteredTotal = $aResultFilterTotal[0];
		$sQuery = " SELECT COUNT(" . $sIndexColumn . ") FROM $sTable ";
		$rResultTotal = mysql_query($sQuery, $gaSql['link']) or fatal_error('MySQL Error: ' . mysql_errno());
		$aResultTotal = mysqli_fetch_array($rResultTotal);
		$iTotal = $aResultTotal[0];
		while ( $aRow = mysqli_fetch_array($rResult) ) {
			$row = array();
			for ( $i = 0 ; $i < count($aColumns); $i++ ) {
				if ( $aColumns[$i] == "version" ) $row[] = ( $aRow[$aColumns[$i]] == "0" ) ? '-' : $aRow[$aColumns[$i]];
				else if ( $aColumns[$i] != ' ' ) $row[] = $aRow[$aColumns[$i]];
			}
			$output['aaData'][] = array_merge($row, array('<a data-id="row-' . $row[0] . '" href="javascript:editRow(' . $row[0] . ');" class="btn btn-md btn-success">edit</a>&nbsp;<a href="javascript:removeRow(' . $row[0] . ');" class="btn btn-default btn-md" style="background-color: #c83a2a;border-color: #b33426; color: #ffffff;">remove</a>'));
		}

		// RETURN IN JSON
		die(json_encode($output));
	}

?>
<html>
	<head>
		<title>Demo of Bootstrap Datatable with AJAX CURD</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.10/css/jquery.dataTables.css">

<!-- Start: Google analytics code-->

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-38304687-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

<!-- End: Google analytics code-->

	</head>
	<body>
		<h1>
			AJAX CURD with Bootstrap Datatable and Modal
			
		</h1>
		<br>

		<div class="container-fluid">
		<button type="button" style="padding:10px; margin:0 50px 15px 0;" class="btn btn-primary btn-sm pull-right" data-toggle="modal" data-target="#add-modal"><b>Add More Rows</b></button>
		<div class="row">
<div class="col-md-12 marginT20">

		<div class="table-responsive demo-x content">
		<table id="example" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>#</th>
					<th>First name</th>
					<th>Email</th>
					<th>Mobile</th>
					<th>Start Date</th>
					<th style="background-image: none">Edit</th>
				</tr>
			</thead>
		</table>
		</div>

		</div>
		</div>
		</div>

		<div class="modal fade" id="edit-modal" tabindex="-1" role="dialog" aria-labelledby="edit-modal-label">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		    	<form class="form-horizontal" id="edit-form">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="edit-modal-label">Edit selected row</h4>
			      </div>
			      <div class="modal-body">
			      		<input type="hidden" id="edit-id" value="" class="hidden">
			        	<div class="form-group">
					    	<label for="firstname" class="col-sm-2 control-label">Firstname</label>
					    	<div class="col-sm-10">
					      		<input type="text" class="form-control" id="firstname" name="firstname" placeholder="Firstname" required>
					    	</div>
					  	</div>
					  	<div class="form-group">
					    	<label for="email" class="col-sm-2 control-label">E-mail</label>
					    	<div class="col-sm-10">
					      		<input type="email" class="form-control" id="email" name="email" placeholder="E-mail address" required>
					    	</div>
					  	</div>
					  	<div class="form-group">
					    	<label for="mobile" class="col-sm-2 control-label">Mobile</label>
					    	<div class="col-sm-10">
					      		<input type="text" class="form-control" id="mobile" name="mobile" placeholder="Mobile" required>
					    	</div>
					  	</div>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			        <button type="submit" class="btn btn-primary">Save changes</button>
			      </div>
		      	</form>
		    </div>
		  </div>
		</div>

		<div class="modal fade" id="add-modal" tabindex="-1" role="dialog" aria-labelledby="add-modal-label">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		    	<form class="form-horizontal" id="add-form">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="add-modal-label">Add new row</h4>
			      </div>
			      <div class="modal-body">
			        	<div class="form-group">
					    	<label for="add-firstname" class="col-sm-2 control-label">Firstname</label>
					    	<div class="col-sm-10">
					      		<input type="text" class="form-control" id="add-firstname" name="firstname" placeholder="Firstname" required>
					    	</div>
					  	</div>
					  	<div class="form-group">
					    	<label for="add-email" class="col-sm-2 control-label">E-mail</label>
					    	<div class="col-sm-10">
					      		<input type="email" class="form-control" id="add-email" name="email" placeholder="E-mail address" required>
					    	</div>
					  	</div>
					  	<div class="form-group">
					    	<label for="add-mobile" class="col-sm-2 control-label">Mobile</label>
					    	<div class="col-sm-10">
					      		<input type="text" class="form-control" id="add-mobile" name="mobile" placeholder="Mobile" required>
					    	</div>
					  	</div>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			        <button type="submit" class="btn btn-primary">Save changes</button>
			      </div>
		      	</form>
		    </div>
		  </div>
		</div>

		<script src="https://code.jquery.com/jquery-2.2.0.min.js" type="text/javascript"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
		<script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.10/js/jquery.dataTables.js"></script>
		<script type="text/javascript" language="javascript" class="init">
			$(document).ready(function() {

				// ATW
				if ( top.location.href != location.href ) top.location.href = location.href;

				// Initialize datatable
				$('#example').dataTable({
					"aProcessing": true,
					"aServerSide": true,
					"ajax": "datatable.php?ajax"
				});

				// Save edited row
				$("#edit-form").on("submit", function(event) {
					event.preventDefault();
					$.post("datatable.php?edit=" + $('#edit-id').val(), $(this).serialize(), function(data) {
						var obj = $.parseJSON(data);
						var tr = $('a[data-id="row-' + $('#edit-id').val() + '"]').parent().parent();
						$('td:eq(1)', tr).html(obj.name);
						$('td:eq(2)', tr).html(obj.email);
						$('td:eq(3)', tr).html(obj.mobile);
						$('#edit-modal').modal('hide');
					}).fail(function() { alert('Unable to save data, please try again later.'); });
				});

				// Add new row
				$("#add-form").on("submit", function(event) {
					event.preventDefault();
					$.post("datatable.php?add", $(this).serialize(), function(data) {
						var obj = $.parseJSON(data);
						$('#example tbody tr:last').after('<tr role="row"><td class="sorting_1">' + obj.id + '</td><td>' + obj.name + '</td><td>' + obj.email + '</td><td>' + obj.mobile + '</td><td>' + obj.start_date + '</td><td><a data-id="row-' + obj.id + '" href="javascript:editRow(' + obj.id + ');" class="btn btn-default btn-sm">edit</a>&nbsp;<a href="javascript:removeRow(' + obj.id + ');" class="btn btn-default btn-sm">remove</a></td></tr>');
						$('#add-modal').modal('hide');
					}).fail(function() { alert('Unable to save data, please try again later.'); });
				});

			});

			// Edit row
			function editRow(id) {
				if ( 'undefined' != typeof id ) {
					$.getJSON('datatable.php?edit=' + id, function(obj) {
						$('#edit-id').val(obj.id);
						$('#firstname').val(obj.name);
						$('#email').val(obj.email);
						$('#mobile').val(obj.mobile);
						$('#edit-modal').modal('show')
					}).fail(function() { alert('Unable to fetch data, please try again later.') });
				} else alert('Unknown row id.');
			}

			// Remove row
			function removeRow(id) {
				if ( 'undefined' != typeof id ) {
					$.get('datatable.php?remove=' + id, function() {
						$('a[data-id="row-' + id + '"]').parent().parent().remove();
					}).fail(function() { alert('Unable to fetch data, please try again later.') });
				} else alert('Unknown row id.');
			}
		</script>
	</body>
</html>