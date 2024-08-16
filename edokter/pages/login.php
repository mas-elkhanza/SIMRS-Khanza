<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Selamat Datang di Aplikasi E-Pasien <?=$_SESSION["nama_instansi"];?></title>
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

<body class="login-page">
    <div class="login-box">
        <div class="logo">
            <a href="javascript:void(0);">E-Dokter <?=$_SESSION["nama_instansi"];?> </a>
        </div>
        <div class="card">
            <div class="body">
                <form id="sigin" role="form" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
                    <div class="msg">Silahkan Login</div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">person</i>
                        </span>
                        <div class="form-line">
                            <input type="password" class="form-control" name="username" placeholder="Username" pattern="[a-zA-Z0-9, ./@_]{1,65}" title=" a-zA-Z0-9, ./@_ (Maksimal 65 karakter)" required onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" autocomplete="off" autofocus>
                            <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                        <div class="form-line">
                            <input type="password" class="form-control" name="password" placeholder="Password" pattern="[a-zA-Z0-9, ./@_]{1,65}" title=" a-zA-Z0-9, ./@_ (Maksimal 65 karakter)" required onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2" autocomplete="off">
                            <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-4">
                            <button class="btn btn-block bg-pink waves-effect" name="BtnLogin" type="submit">LOG IN</button>
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-block bg-pink waves-effect" name="BtnReset" type="reset">Reset</button>
                        </div>
                    </div>
                </form>
                <?php 
                    $BtnLogin=isset($_POST['BtnLogin'])?$_POST['BtnLogin']:NULL;
                    if (isset($BtnLogin)) {
                         $username  = validTeks4($_POST['username'],20);
                         $password  = validTeks4($_POST['password'],40);
                         if(getOne2("select count(*) from user where user.id_user=AES_ENCRYPT('$username','nur') and user.password=AES_ENCRYPT('$password','windi')")>0){
                             if(getOne2("select count(*) from dokter where dokter.kd_dokter='$username'")>0){
                                $_SESSION["ses_dokter"]= encrypt_decrypt($username,"e");
                                exit(header("Location:index.php"));
                             }else{
                                echo "Username/Password ada yang salah. Silahkan ulangi...!";
                             }  
                         }else{
                             echo "Username/Password ada yang salah. Silahkan ulangi...!";
                         }
                    }
                ?>
            </div>
        </div>
    </div>
    
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