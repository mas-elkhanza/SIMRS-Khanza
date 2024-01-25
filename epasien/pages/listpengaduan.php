<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>PENGADUAN PASIEN</center></h2>
</div>

<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="row clearfix">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <div class="form-line" style="width: 100%; height: 400px; overflow: auto;" id="screen" />
                        </div>
                    </div>
                </div>
                <div class="row clearfix">
                    <div class="col-sm-12">
                        <form id="frmPesan" name="frmPesan" method="post" action="" enctype=multipart/form-data>
                            <div class="col-lg-10 col-md-8 col-sm-8 col-xs-8">
                                <div class="form-group">
                                    <div class="form-line">
                                        <input name="pesan" type="text" id="pesan" class="form-control" placeholder="Ketik disini..." pattern="[a-zA-Z0-9, ./@_]{1,65}" title=" a-zA-Z0-9, ./@_ (Maksimal 65 karakter)" maxlength="255" autocomplete="off" autofocus required/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
                                <input name="BtnPesan" type="submit" class="btn btn-danger waves-effect" value="Kirim">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script type="text/javascript">
    $(document).ready(my_function());
    
    $(function() {
        $("#frmPesan").bind('submit',function() {
             $.post('pages/pengaduan.php',{pesan:$('#pesan').val()},null);
             $("#pesan").val("");
             my_function();
             return false;
        });
    });
    
    setInterval("my_function();",60000); 
    
    function my_function(){
        $('#screen').load('pages/daftarpengaduan.php');
    }
</script>