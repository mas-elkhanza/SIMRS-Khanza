<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<!DOCTYPE html>
<html>
<head>
    <title>SIMKES Khanza</title>
    <script src="js/jquery.min.js"></script>
    <script src="js/webcam.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <style type="text/css">
        #results { padding: 0px; background:#EEFFEE; width: 490; height: 390 }
    </style>
</head>
<body>
    <div class="container">
        <h5 class="text-danger"><center>Pengambilan photo Dokumen Asuransi : <?=getOne("select penjab.png_jawab from penjab where penjab.kd_pj='".$_SESSION['kdpj']."'");?></center></h5>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <div class="row">
                <div class="col-md-6">
                    <div>
                        <h7 class="text-success">
                            <center>
                                Kerjasama Berakhir Pada : 
                                <select name="TglBerakhir" class="text-success">
                                    <?php
                                       loadTglnow();
                                    ?>
                                </select>
                                <select name="BlnBerakhir" class="text-success">
                                    <?php
                                        loadBlnnow();
                                    ?>
                                </select>
                                <select name="ThnBerakhir" class="text-success">
                                    <?php
                                        loadThn5();
                                    ?>
                                </select>
                            </center>
                        </h7>
                    </div>
                    <div id="my_camera"></div>
                    <input type="hidden" name="image" class="image-tag" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                </div>
                <div class="col-md-6">
                    <div id="results"><h7 class="text-success"><center>Gambar yang diambil akan tampil disini</center></h7></div>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </div>
                <div class="col-md-12 text-center">
                    <br>
                    <input type="button" class="btn btn-warning" value="Ambil" onClick="take_snapshot()">
                    <button class="btn btn-danger">Simpan</button>
                </div>
            </div>
        </form>
    </div>
    <script language="JavaScript">
        Webcam.set({
            width: 490,
            height: 390,
            image_format: 'jpeg',
            jpeg_quality: 90
        });

        Webcam.attach( '#my_camera' );

        function take_snapshot() {
            Webcam.snap( function(data_uri) {
                $(".image-tag").val(data_uri);
                document.getElementById('results').innerHTML = '<img src="'+data_uri+'"/>';
            } );
        }
    </script>
</body>
</html>

