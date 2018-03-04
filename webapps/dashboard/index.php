<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>Bootstrap 3 Admin</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/datepicker.css" rel="stylesheet">
		<link href="css/bootstrap-table.css" rel="stylesheet">
        <link href="css/styles.css" rel="stylesheet">
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="css/styles.css" rel="stylesheet">
	</head>
	<body>
<!-- header -->
<div id="top-nav" class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
    </div>
    <!-- /container -->
</div>
<!-- /Header -->

<!-- Main -->
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3">
            <!-- Left column -->
            <a href="#"><strong><i class="glyphicon glyphicon-wrench"></i> LAPORAN RL</strong></a>

            <hr>

            <ul class="nav nav-stacked">
            
                <li class="nav-header"> <a href="#" data-toggle="collapse" data-target="#menu1"> Laporan RL 1 - Data Dasar Rumah Sakit <i class="glyphicon glyphicon-chevron-right"></i></a>

                    <ul class="nav nav-stacked collapse" id="menu1">
                        <li><a href="?module=1.1">laporan RL 1.1</a>
                        </li>
                        <li><a href="?module=1.2">Laporan RL 1.2</a>
                        </li>
                        <li><a href="?module=1.3">Laporan RL 1.3</a>
                        </li>
                    </ul>
                </li>
				
				<li class="nav-header"> <a href="#" data-toggle="collapse" data-target="#menu2"> Laporan RL 2 - Ketenagaan <i class="glyphicon glyphicon-chevron-right"></i></a>

                    <ul class="nav nav-stacked collapse" id="menu2">
                        <li><a href="#">laporan RL 2</a>
                        </li>
                    </ul>
                </li>
				
				<li class="nav-header"> <a href="#" data-toggle="collapse" data-target="#menu3"> Laporan RL 3 - Pelayanan <i class="glyphicon glyphicon-chevron-right"></i></a>

                    <ul class="nav nav-stacked collapse" id="menu3">
                        <li><a href="?module=3.1">laporan RL 3.1</a>
                        </li>
                        <li><a href="?module=3.2">Laporan RL 3.2</a>
                        </li>
                        <li><a href="#">Laporan RL 3.3</a>
                        </li>
						<li><a href="#">Laporan RL 3.4</a>
                        </li>
						<li><a href="#">Laporan RL 3.5</a>
                        </li>
						<li><a href="#">Laporan RL 3.6</a>
                        </li>
						<li><a href="?module=3.7">Laporan RL 3.7</a>
                        </li>
						<li><a href="#">Laporan RL 3.8</a>
                        </li>
						<li><a href="#">Laporan RL 3.9</a>
                        </li>
						<li><a href="#">Laporan RL 3.10</a>
                        </li>
						<li><a href="#">Laporan RL 3.11</a>
                        </li>
						<li><a href="#">Laporan RL 3.12</a>
                        </li>
						<li><a href="#">Laporan RL 3.13</a>
                        </li>
						<li><a href="#">Laporan RL 3.14</a>
                        </li>
						<li><a href="?module=3.15">Laporan RL 3.15</a>
                        </li>
                    </ul>
                </li>
				
				<li class="nav-header"> <a href="#" data-toggle="collapse" data-target="#menu4"> Laporan RL 4 - Morbiditas dan Mortalitas <i class="glyphicon glyphicon-chevron-right"></i></a>

                    <ul class="nav nav-stacked collapse" id="menu4">
                        <li><a href="#">laporan RL 4.a</a>
                        </li>
						<li><a href="#">laporan RL 4.b</a>
                        </li>
                    </ul>
                </li>
				
				<li class="nav-header"> <a href="#" data-toggle="collapse" data-target="#menu5"> Laporan RL 5 - Pengunjung Rumah Sakit <i class="glyphicon glyphicon-chevron-right"></i></a>

                    <ul class="nav nav-stacked collapse" id="menu5">
                        <li><a href="?module=5.1">laporan RL 5.1</a>
                        </li>
						<li><a href="?module=5.2">laporan RL 5.2</a>
                        </li>
						<li><a href="?module=5.3">laporan RL 5.3</a>
                        </li>
						<li><a href="#">laporan RL 5.4</a>
                        </li>
                    </ul>
                </li>
				
                
        </div>
        <!-- /col-3 -->
        <div class="col-sm-9">
		<a href="#"><strong><i class="glyphicon glyphicon-dashboard"></i> My Dashboard</strong></a>
            <hr>
			<div class="panel panel-default">
                        <?php include"module.php"; ?>
                    </div>
            <!-- column 2 -->
            
            

            
<!-- /Main -->

<footer class="text-center">This Bootstrap 3 dashboard layout is compliments of <a href="http://www.bootply.com/85850"><strong>Bootply.com</strong></a></footer>

<!-- /.modal -->
	<!-- script references -->
		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/scripts.js"></script>
		<script src="js/jquery.maskedinput.js"></script>
		<script src="js/bootstrap-datepicker.js"></script>
		<script src="js/bootstrap-table.js"></script>
		<script>
						    $(function () {
						        $('#hover, #striped, #condensed').click(function () {
						            var classes = 'table';
						
						            if ($('#hover').prop('checked')) {
						                classes += ' table-hover';
						            }
						            if ($('#condensed').prop('checked')) {
						                classes += ' table-condensed';
						            }
						            $('#table-style').bootstrapTable('destroy')
						                .bootstrapTable({
						                    classes: classes,
						                    striped: $('#striped').prop('checked')
						                });
						        });
						    });
						
						    function rowStyle(row, index) {
						        var classes = ['active', 'success', 'info', 'warning', 'danger'];
						
						        if (index % 2 === 0 && index / 2 < classes.length) {
						            return {
						                classes: classes[index / 2]
						            };
						        }
						        return {};
						    }
						</script>
			<script>
		jQuery(function($){
            $("#tgl1").mask("99/99/9999",{placeholder:"dd/mm/yyyy"});
			$("#tgl2").mask("99/99/9999",{placeholder:"dd/mm/yyyy"});
            $("#npwp").mask("99-999-999-9-999-999");
			$("#jam").mask("99:99");
			$("#jam1").mask("99:99");
        });
        $('#calender').datepicker({
        autoclose:true, orientation:'top right',
                'default': 'now'
        });
		$('#calender1').datepicker({
        autoclose:true, orientation:'top right',
                'default': 'now'
         });
        !function ($) {
        $(document).on("click", "ul.nav li.parent > a > span.icon", function(){
        $(this).find('em:first').toggleClass("glyphicon-minus");
        });
        $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
        }(window.jQuery);
        $(window).on('resize', function () {
        if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
        })
                $(window).on('resize', function () {
        if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
        })
        </script>
						
						
	</body>
</html>