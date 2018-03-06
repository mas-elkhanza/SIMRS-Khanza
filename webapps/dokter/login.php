<?php 

/***
* e-Dokter from version 0.1 Beta
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
*
* File : login.php
* Description : Login, cookie and session process
* Licence under GPL
***/ 

ob_start();
session_start();

require_once('config.php');

if (isset($_COOKIE['username']) && isset($_COOKIE['password'])) { redirect('index.php'); }

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

    <!-- Custom Css -->
    <link href="css/style.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="css/theme-all.css" rel="stylesheet" />
</head>

<body class="login-page">
    <!-- Page Loader -->
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
            <p>Memproses data ke server.....</p>
        </div>
    </div>
    <!-- #END# Page Loader -->
    <div class="login-box">
        <div class="logo">
            <a href="index.php"><?php echo $dataSettings['nama_instansi']; ?></a>
            <small><?php echo $dataSettings['alamat_instansi']; ?> - <?php echo $dataSettings['kabupaten']; ?></small>
        </div>

    <?php if(!isset($_GET['action'])){ 

        if($_SERVER['REQUEST_METHOD'] == "POST") { 

            if (empty ($_POST['username']) || empty ($_POST['password'])) {
                $errors[] = 'Username or password empty'; 
            }

            if ($_POST['username'] !=="" || $_POST['password'] !=="") { 

                $sql = "SELECT * FROM user WHERE AES_DECRYPT(id_user','nur') = '".$_POST["username"]."' and AES_DECRYPT(password,'windi')='".$_POST["password"]."'";
                $found = query($sql);
            
                echo "user : ".$_POST['username'] ;
                echo "password : ".$_POST['password'] ;
                echo "Data : ".num_rows($found);
                if(num_rows($found) !== 1) {
                    $errors[] = 'Kode dokter tidak terdaftar atau tidak aktif.';
                }       

            }

            if(!empty($errors)) { 

                foreach($errors as $error) {
                    echo validation_errors($error);
                }

            } else { 

                if (isset($_POST['remember'])) {
                    /* Set cookie to last 1 year */
                    setcookie('username', $_POST['username'], time()+60*60*24*365);
                    setcookie('password', $_POST['password'], time()+60*60*24*365);
        
                } else {
                    /* Cookie expires when browser closes */
                    setcookie('username', $_POST['username'], false);
                    setcookie('password', $_POST['password'], false);
                }

                redirect('index.php');

            }
        
        }
        ?>

        <div class="card">        
            <div class="body">
                <form id="sign_in" method="POST">
                    <div class="msg">Silahkan login dulu untuk memulai</div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">person</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" name="username" placeholder="Kode Dokter" required autofocus>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                        <div class="form-line">
                            <input type="password" class="form-control" name="password" placeholder="Kata Kunci" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-8 p-t-5">
                            <input type="checkbox" name="rememberme" id="rememberme" class="filled-in chk-col-pink">
                            <label for="rememberme">Ingat saya</label>
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-block bg-pink waves-effect" type="submit">Log Masuk</button>
                        </div>
                    </div>
                </form>
            </div>

    <?php } ?>

    <?php
    //logout
    if(isset($_GET['action']) == "logout"){ 

        setcookie('username', '', time()-60*60*24*365);
        setcookie('password', '', time()-60*60*24*365);

        unset($_SESSION['username']); 
        unset($_SESSION['level']);
        unset($_SESSION['jenis_poli']);
        $_SESSION = array(); 
        session_destroy(); 

        redirect('login.php'); 

    } 
    ?>
        </div>
    </div>

    <!-- Jquery Core Js -->
    <script src="plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="plugins/node-waves/waves.js"></script>

    <!-- Validation Plugin Js -->
    <script src="plugins/jquery-validation/jquery.validate.js"></script>

    <!-- Custom Js -->
    <script src="js/admin.js"></script>
</body>

</html>
