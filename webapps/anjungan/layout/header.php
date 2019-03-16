<?php
ini_set('display_errors', 0);
error_reporting(E_ERROR | E_WARNING | E_PARSE); 
?>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title><?php echo $dataSettings['nama_instansi']; ?> &raquo; Anjungan Pasien Mandiri</title>
    <!-- Favicon-->
    <link rel="icon" href="<?php echo URL; ?>/favicon.ico" type="image/x-icon">

    <!-- Google Fonts -->
    <link href="<?php echo URL; ?>/css/roboto.css" rel="stylesheet">

    <!-- Material Icon Css -->
    <link href="<?php echo URL; ?>/css/material-icon.css" rel="stylesheet">

    <!-- Bootstrap Core Css -->
    <link href="<?php echo URL; ?>/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Waves Effect Css -->
    <link href="<?php echo URL; ?>/plugins/node-waves/waves.css" rel="stylesheet" />

    <!-- Animation Css -->
    <link href="<?php echo URL; ?>/plugins/animate-css/animate.css" rel="stylesheet" />

    <!-- JQuery DataTable Css -->
    <link href="<?php echo URL; ?>/plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.css" rel="stylesheet">
    <link href="<?php echo URL; ?>/plugins/jquery-datatable/extensions/responsive/css/responsive.dataTables.min.css" rel="stylesheet">

    <!-- Bootstrap Material Datetime Picker Css -->
    <link href="<?php echo URL; ?>/plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />

    <!-- Bootstrap Select Css -->
    <link href="<?php echo URL; ?>/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />

    <!-- Sweet Alert Css -->
    <link href="<?php echo URL; ?>/plugins/sweetalert/sweetalert.css" rel="stylesheet" />
  
    <!-- Jquery-UI Css -->
    <link href="<?php echo URL; ?>/css/jquery-ui.min.css" rel="stylesheet">

    <!-- Select2 Css -->
    <link href="<?php echo URL; ?>/css/select2.min.css" rel="stylesheet">

    <!-- Custom Css -->
    <link href="<?php echo URL; ?>/css/style.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="<?php echo URL; ?>/css/themes/all-themes.css" rel="stylesheet" />

</head>

<body class="theme-red ls-closed">
    <!-- Top Bar -->
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header" style="padding-left: 100px;">
                <a class="navbar-brand" href="<?php echo URL; ?>/index.php"><?php echo $dataSettings['nama_instansi']; ?></a>
            </div>
        </div>
    </nav>
    <!-- #Top Bar -->
