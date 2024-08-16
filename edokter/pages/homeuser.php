<?php 
    if(isset($_SESSION["ses_dokter"])){
        $halaman = isset($_GET["act"])?$_GET["act"]:NULL;
        if(!isset($_SESSION["nm_dokter"])){
            $queryuser                      = @bukaquery2("select dokter.nm_dokter from dokter where dokter.kd_dokter='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."'");
            while($rsqueryuser = mysqli_fetch_array($queryuser)) {
                $_SESSION["nm_dokter"]      = $rsqueryuser["nm_dokter"];
            }
        }
    }else{
        JSRedirect("index.php?act=Home");
    }
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="robots" content="noindex,nofollow">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Selamat Datang di Aplikasi E-Dokter <?=$_SESSION["nama_instansi"];?></title>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
    <link href="css/style3.css" rel="stylesheet" type="text/css">
    <link href="css/style4.css" rel="stylesheet" type="text/css">
    <link href="plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="plugins/node-waves/waves.css" rel="stylesheet" />
    <link href="plugins/animate-css/animate.css" rel="stylesheet" />
    <link href="plugins/morrisjs/morris.css" rel="stylesheet" />
    <link href="plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.css" rel="stylesheet">
    <link href="css/style2.css" rel="stylesheet">
    <link href="css/themes/all-themes.css" rel="stylesheet" />
</head>
<body class="theme-pink">
    <div class="overlay"></div>
    <div class="search-bar">
        <div class="search-icon">
            <i class="material-icons">search</i>
        </div>
        <input type="text" placeholder="Mulai Menulis...">
        <div class="close-search">
            <i class="material-icons">close</i>
        </div>
    </div>
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="javascript:void(0);" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false"></a>
                <a href="javascript:void(0);" class="bars"></a>
                <a class="navbar-brand" href="index.php?act=HomeUser"><b>Aplikasi E-Dokter <?=$_SESSION["nama_instansi"];?></b></a>
            </div>
        </div>
    </nav>
    <section>
        <!-- Left Sidebar -->
        <aside id="leftsidebar" class="sidebar">
            <div class="user-info">
                <div class="info-container">
                    <div class="name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?=$_SESSION["nm_dokter"];?></div>
                    <div class="btn-group user-helper-dropdown">
                        <i class="material-icons" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">keyboard_arrow_down</i>
                        <ul class="dropdown-menu pull-right">
                            <li><a href="pages/logout.php"><i class="material-icons">input</i>Log Out</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="menu">
                <ul class="list">
                    <li class="header">MENU UTAMA</li>
                    <li <?=$halaman=="HomeUser"?"class='active'":""?>>
                        <a href="index.php?act=HomeUser">
                            <i class="material-icons">home</i>
                            <span>Dashboard</span>
                        </a>
                    </li>
                    <li <?=$halaman=="Admin"?"class='active'":""?>>
                        <a href="index.php?act=Pasien">
                            <i class="material-icons">assignment_ind</i>
                            <span>Daftar Pasien</span>
                        </a>
                    </li>
                </ul>
            </div>
            <!-- #Menu -->
            <!-- Footer -->
            <div class="legal">
                <div class="copyright">
                    &copy; 2010 - 2023 <a href="javascript:void(0);"><?=$_SESSION["nama_instansi"];?></a>.
                </div>
            </div>
            <!-- #Footer -->
        </aside>
    </section>

    <section class="content">
        <div class="container-fluid">
            <?php actionPages();?>
        </div>
    </section>
    <script src="plugins/jquery/jquery.min.js"></script>
    <script src="plugins/bootstrap/js/bootstrap.js"></script>
    <script src="plugins/bootstrap-select/js/bootstrap-select.js"></script>
    <script src="plugins/jquery-slimscroll/jquery.slimscroll.js"></script>
    <script src="plugins/node-waves/waves.js"></script>
    <script src="plugins/jquery-countto/jquery.countTo.js"></script>
    <script src="plugins/raphael/raphael.min.js"></script>
    <script src="plugins/morrisjs/morris.js"></script>
    <script src="plugins/chartjs/Chart.bundle.js"></script>
    <script src="plugins/flot-charts/jquery.flot.js"></script>
    <script src="plugins/flot-charts/jquery.flot.resize.js"></script>
    <script src="plugins/flot-charts/jquery.flot.pie.js"></script>
    <script src="plugins/flot-charts/jquery.flot.categories.js"></script>
    <script src="plugins/flot-charts/jquery.flot.time.js"></script>
    <script src="plugins/jquery-datatable/jquery.dataTables.js"></script>
    <script src="plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/dataTables.buttons.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/buttons.flash.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/jszip.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/pdfmake.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/vfs_fonts.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/buttons.html5.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/buttons.print.min.js"></script>
    <script src="plugins/jquery-sparkline/jquery.sparkline.js"></script>
    <script src="js/admin.js"></script>
    <script src="js/pages/tables/jquery-datatable.js"></script>
    <script src="js/pages/index.js"></script>
    <script src="js/demo.js"></script>
    <script src="conf/validator.js" type="text/javascript"></script>
</body>
</html>

