<?php 

/***
* e-Dokter from version 0.1 Beta
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
*
* File : layout/header.php
* Description : Header layout
* Licence under GPL
***/

ob_start();
session_start();

require_once('config.php');

$data=fetch_array(query("SELECT AES_DECRYPT(a.id_user,'nur') as id_user, AES_DECRYPT(a.password,'windi') as password,  b.kd_poli as kd_poli from user a, jadwal b where a.id_user = AES_ENCRYPT('{$_COOKIE[username]}','nur') and b.kd_dokter = '$_COOKIE[username]' and a.password = AES_ENCRYPT('{$_COOKIE[password]}','windi')")); 

$user = $data[0];
$pass = $data[1];

if (!isset($_COOKIE['username']) && !isset($_COOKIE['password'])) { 
    redirect('login.php'); 
} else if (($_COOKIE['username'] != $user) || ($_COOKIE['password'] != $pass)) { 
    redirect('login.php?action=logout'); 
} else { 
    $_SESSION['username'] = $_COOKIE['username']; 
    $_SESSION['jenis_poli'] = $data[2];    
}

?>
﻿<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title><?php echo $dataSettings['nama_instansi']; ?></title>
    <!-- Favicon-->
    <link rel="icon" href="favicon.ico" type="image/x-icon">

    <!-- Google Fonts -->
    <link href="css/roboto.css" rel="stylesheet">

    <!-- Material Icon Css -->
    <link href="css/material-icon.css" rel="stylesheet">

    <!-- Bootstrap Core Css -->
    <link href="plugins/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Waves Effect Css -->
    <link href="plugins/node-waves/waves.css" rel="stylesheet" />

    <!-- Animation Css -->
    <link href="plugins/animate-css/animate.css" rel="stylesheet" />

    <!-- Sweet Alert Css -->
    <link href="plugins/sweetalert/sweetalert.css" rel="stylesheet" />

    <!-- JQuery DataTable Css -->
    <link href="plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.css" rel="stylesheet">
    <link href="plugins/jquery-datatable/extensions/responsive/css/responsive.dataTables.min.css" rel="stylesheet">

    <!-- Bootstrap Material Datetime Picker Css -->
    <link href="plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />

    <!-- Bootstrap Select Css -->
    <link href="plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />

    <!-- Wait Me Css -->
    <link href="plugins/waitme/waitMe.css" rel="stylesheet" />

    <link rel="stylesheet" href="css/jquery-ui.min.css">
    <link rel="stylesheet" href="css/select2.min.css">

    <!-- Custom Css -->
    <link href="css/style.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="css/all-themes.min.css" rel="stylesheet" />

<script src="js/jquery.min.js" defer></script>
<script src="js/bootstrap-select.js" defer></script>
<script src="js/select2.min.js" defer></script>


 

</head>

<body class="theme-green">
    <!-- Page Loader 
    <div class="page-loader-wrapper">
        <div class="loader">
            <div class="preloader">
                <div class="spinner-layer pl-green">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div>
                    <div class="circle-clipper right">
                        <div class="circle"></div>
                    </div>
                </div>
            </div>
            <p>Please wait...</p>
        </div>
    </div>-->
    <!-- #END# Page Loader -->
    <!-- Overlay For Sidebars -->
    <div class="overlay"></div>
    <!-- #END# Overlay For Sidebars -->
    <!-- Top Bar -->
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="javascript:void(0);" class="bars"></a>
                <a class="navbar-brand" href="index.php"><?php echo $dataSettings['nama_instansi']; ?></a>
            </div>
        </div>
    </nav>
    <!-- #Top Bar -->

<?php include_once ('sidebar.php'); ?>
