<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : signup.php
* Description : Registration process
* Licence under GPL
***/

ob_start();
session_start();

require_once('config.php');

if(SIGNUP !== 'ENABLE') {
    redirect ('login.php');
}

if(PRODUCTION == 'YES') {
  ini_set('display_errors', 0);
  error_reporting(E_ERROR | E_WARNING | E_PARSE);
}

?>
ï»¿<!DOCTYPE html>
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

    <!-- Bootstrap Material Datetime Picker Css -->
    <link href="plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />

    <!-- Bootstrap Select Css -->
    <link href="plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />

    <!-- Jquery UI Css -->
    <link href="plugins/jquery-ui/jquery-ui.css" rel="stylesheet">

    <!-- Custom Css -->
    <link href="css/style.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="css/theme-blue.css" rel="stylesheet" />
</head>

<body class="login-page">
    <?php
    $get_rm = fetch_array(query("SELECT max(no_rkm_medis) FROM pasien"));
    $lastRM = substr($get_rm[0], 0, 6);
    $no_rm_next = sprintf('%06s', ($lastRM + 1));
    ?>
    <!-- Page Loader -->
    <div class="page-loader-wrapper">
        <div class="loader">
            <div class="preloader">
                <div class="spinner-layer pl-red">
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
    <div class="login-box" style="margin: 20px;">
        <div class="logo">
            <a href="index.php"><?php echo $dataSettings['nama_instansi']; ?></a>
            <small><?php echo $dataSettings['alamat_instansi']; ?> - <?php echo $dataSettings['kabupaten']; ?></small>
        </div>


<?php
if($_SERVER['REQUEST_METHOD'] == "POST") {

    if(empty($_POST['no_ktp'])) {
	$errors[] = 'Kolom No KTP tidak boleh kosong';
    }
    if(nik_exits($_POST['no_ktp'])) {
	$errors[] = 'No KTP telah terdaftar';
    }
    if(empty($_POST['nm_pasien'])) {
	$errors[] = 'Kolom nama tidak boleh kosong';
    }

    if(!empty($errors)) {
	foreach($errors as $error) {
	    echo validation_errors($error);
	}
    } else {
	$insert = query("INSERT INTO pasien VALUES(
        '$no_rm_next',
        '{$_POST['nm_pasien']}',
        '{$_POST['no_ktp']}',
        '{$_POST['jk']}',
        '-',
        '{$_POST['tgl_lahir']}',
        '-',
        '{$_POST['alamat']}',
        '-',
        '-',
        '-',
        '-',
        '$date',
        '{$_POST['no_tlp']}',
        '-',
        '-',
        '-',
        '-',
        'A01',
        '-',
        '{$_POST['kd_kel']}',
        '{$_POST['kd_kec']}',
        '{$_POST['kd_kab']}',
        '-',
        '-',
        '-',
        '-',
        '-'
    )");

	if($insert) {
	    set_message('Selamat..!! Anda telah melakukan pendaftaran On-Line.');
	    redirect('signup.php?action=success');
	}

    }

}
?>

<?php
        $action      =isset($_GET['action'])?$_GET['action']:null;
        if(!$action){
?>
        <div class="card">
            <div class="body">
                <form id="sign_up" method="POST">
                    <div class="msg">Pendaftaran Pasien Baru</div>
                    <h3>No. RM : <?php echo $no_rm_next; ?></h3>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" name="no_ktp" placeholder="Nomor Induk Kependudukan" minlength="16" maxlength="16" required autofocus>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">person</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" name="nm_pasien" placeholder="Nama Lengkap" required autofocus>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">event</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control datepicker" name="tgl_lahir" placeholder="Tanggal Lahir" required autofocus>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">email</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" name="alamat" placeholder="Alamat Sesuai KTP" required>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons" style="color:transparent;">done</i>
                        </span>
                        <div class="form-line">
                            <input type="hidden" id="kd_kel" name="kd_kel">
                            <input type="text" id="nm_kel" class="form-control" name="nm_kel" placeholder="Kelurahan / Desa" required>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons" style="color:transparent;">done</i>
                        </span>
                        <div class="form-line">
                            <input type="hidden" id="kd_kec" name="kd_kec">
                            <input type="text" id="nm_kec" class="form-control" name="nm_kec" placeholder="Kecamatan" required>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons" style="color:transparent;">done</i>
                        </span>
                        <div class="form-line">
                            <input type="hidden" id="kd_kab" name="kd_kab">
                            <input type="text" id="nm_kab" class="form-control" name="nm_kab" placeholder="Kabupaten" required>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">phone</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" name="no_tlp" placeholder="Nomor Telepon" required autofocus>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">group</i>
                        </span>
                        <div class="">
                            <input name="jk" value="L" type="radio" id="radio_1" checked />
                            <label for="radio_1">Laki-Laki</label>
                            <input name="jk" value="P" type="radio" id="radio_2" />
                            <label for="radio_2">Perempuan</label>
                        </div>
                    </div>
                    <div class="form-group"><br>
                    </div>
                    <div class="form-group">
                        <input type="checkbox" name="terms" id="terms" class="filled-in chk-col-pink" required>
                        <label for="terms">Saya setuju dengan <a href="javascript:void(0);">syarat dan ketentuan</a>.</label>
                    </div>

                    <button class="btn btn-block btn-lg bg-pink waves-effect" type="submit">DAFTARKAN SAYA</button>

                    <div class="m-t-25 m-b--5 align-center">
                        <a href="login.php">Sudah terdaftar sebagai pasien?</a>
                    </div>
                </form>
            </div>
        </div>

    <?php } ?>

    <?php
    //edit
    if($action == "success"){
    ?>

        <?php display_message(); ?>

        <div class="card">
            <div class="body text-center">
            <h5>Username (No.RM):</h5><h2><?php echo $no_rm_next; ?></h2><br>
            <p>Gunakan No. KTP anda sebagai password.</p>
            <p>Silahkan klik tombol login dibawah</p><br>
            <a href="login.php"><button type="button" class="btn bg-green btn-lg waves-effect">LOGIN</button></a>
            </div>
        </div>

    <?php } ?>

    </div>

    <!-- Jquery Core Js -->
    <script src="plugins/jquery/jquery.min.js"></script>

    <script src="plugins/jquery-ui/jquery-ui.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Select Plugin Js -->
    <script src="plugins/bootstrap-select/js/bootstrap-select.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="plugins/node-waves/waves.js"></script>

    <!-- Validation Plugin Js -->
    <script src="plugins/jquery-validation/jquery.validate.js"></script>

    <!-- Autosize Plugin Js -->
    <script src="plugins/autosize/autosize.js"></script>

    <!-- Moment Plugin Js -->
    <script src="plugins/momentjs/moment.js"></script>

    <!-- Bootstrap Material Datetime Picker Plugin Js -->
    <script src="plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>

    <!-- Custom Js -->
    <script src="js/admin.js"></script>
    <script src="js/pages/examples/sign-in.js"></script>

	<script>

        $(document).ready(function() {

            //Textare auto growth
            autosize($('textarea.auto-growth'));

            $('.datepicker').bootstrapMaterialDatePicker({
                format: 'YYYY-MM-DD',
                clearButton: true,
                weekStart: 1,
                time: false
            });

        } );
	</script>

    <script>
        $(function() {
            var kelurahan =
                <?php
                $result = query("SELECT * FROM kelurahan");
                while ($row = fetch_array($result)) {
                    $kd_kel = $row['kd_kel'];
                    $nm_kel = $row['nm_kel'];
                    $kelurahan[] = array( 'key' => "$kd_kel", 'value' => "$nm_kel" );
                }
                echo json_encode($kelurahan);
                ?>
            ;

            var kecamatan =
                <?php
                $result = query("SELECT * FROM kecamatan");
                while ($row = fetch_array($result)) {
                    $kd_kec = $row['kd_kec'];
                    $nm_kec = $row['nm_kec'];
                    $kecamatan[] = array( 'key' => "$kd_kec", 'value' => "$nm_kec" );
                }
                echo json_encode($kecamatan);
                ?>
            ;

            var kabupaten =
                <?php
                $result = query("SELECT * FROM kabupaten");
                while ($row = fetch_array($result)) {
                    $kd_kab = $row['kd_kab'];
                    $nm_kab = $row['nm_kab'];
                    $kabupaten[] = array( 'key' => "$kd_kab", 'value' => "$nm_kab" );
                }
                echo json_encode($kabupaten);
                ?>
            ;

            $("#nm_kel").autocomplete({
                source: kelurahan,
                autoFocus:true,
                minLength:2,
                select: function(event, ui) {
                    $("#nm_kel").val(ui.item.value);
                    $("#kd_kel").val(ui.item.key);
                }
            });
            $("#nm_kec").autocomplete({
                source: kecamatan,
                autoFocus:true,
                minLength:2,
                select: function(event, ui) {
                    $("#nm_kec").val(ui.item.value);
                    $("#kd_kec").val(ui.item.key);
                }
            });
            $("#nm_kab").autocomplete({
                source: kabupaten,
                autoFocus:true,
                minLength:2,
                select: function(event, ui) {
                    $("#nm_kab").val(ui.item.value);
                    $("#kd_kab").val(ui.item.key);
                }
            });
        });
    </script>

</body>

</html>
