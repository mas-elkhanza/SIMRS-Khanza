<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    $besok                  = date("Y-m-d", strtotime("+1 day"));
    $thnbesok               = substr($besok,0,4);
    $blnbesok               = substr($besok,5,2);
    $tglbesok               = substr($besok,8,2);
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>BOOKING REGISTRASI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <label for="tgl_registrasi">Tanggal Rencana Periksa</label>
                    <div class="form-group">
                        <div class="form-line">
                            <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_registrasi" class="datepicker form-control" required autocomplete="off" value="<?=$tglbesok."-".$blnbesok."-".$thnbesok;?>"/>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="pilihpoli">TAMPILKAN JADWAL TERSEDIA</button></center>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
