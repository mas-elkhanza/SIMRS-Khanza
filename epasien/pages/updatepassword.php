<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>Ubah Password</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST">
                    <label for="passwordlama">Password Lama</label>
                    <div class="form-group">
                        <div class="form-line">
                            <input name="passwordlama" type="password" id="passwordlama" class="form-control" placeholder="Masukkan password lama..." pattern="[a-zA-Z0-9, ./@_]{1,25}" title=" a-zA-Z0-9, ./@_ (Maksimal 25 karakter)" maxlength="25" autocomplete="off" autofocus required/>
                        </div>
                    </div>
                    <label for="passwordbaru">Password Baru</label>
                    <div class="form-group">
                        <div class="form-line">
                            <input name="passwordbaru" type="password" id="passwordbaru" class="form-control" placeholder="Masukkan password baru..." pattern="[a-zA-Z0-9, ./@_]{1,25}" title=" a-zA-Z0-9, ./@_ (Maksimal 25 karakter)" maxlength="25" autocomplete="off" required/>
                        </div>
                    </div>
                    <label for="passwordbaruulangi">Ulangi Password Baru</label>
                    <div class="form-group">
                        <div class="form-line">
                            <input name="passwordbaruulangi" type="password" id="passwordbaruulangi" class="form-control" placeholder="Masukkan password baru sekali lagi..." pattern="[a-zA-Z0-9, ./@_]{1,25}" title=" a-zA-Z0-9, ./@_ (Maksimal 25 karakter)" maxlength="25" autocomplete="off" required/>
                        </div>
                    </div>
                    <center><button class="btn btn-danger waves-effect" type="submit" name="btnSimpan">Simpan</button></center>
                </form>
                <?php 
                    $btnSimpan=isset($_POST['btnSimpan'])?$_POST['btnSimpan']:NULL;
                    if (isset($btnSimpan)) {
                         $passwordlama        = validTeks4($_POST['passwordlama'],25);
                         $passwordbaru        = validTeks4($_POST['passwordbaru'],25);
                         $passwordbaruulangi  = validTeks4($_POST['passwordbaruulangi'],25);
                         if(getOne2("select AES_DECRYPT(personal_pasien.password,'windi') from personal_pasien where personal_pasien.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."'")==$passwordlama){
                             if($passwordbaru==$passwordbaruulangi){
                                 if(strlen($passwordbaru)>5){
                                     if(bukaquery2("update personal_pasien set password=AES_ENCRYPT('$passwordbaru','windi') where personal_pasien.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."'")){
                                         echo "<div class='row clearfix'>
                                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                        <div class='card'>
                                                            <div class='body bg-danger'>
                                                                <center><h4>Ubah password berhasil...</h4></center>
                                                            </div>
                                                        </div>
                                                    </div>
                                               </div>";
                                     }else{
                                         echo "<div class='row clearfix'>
                                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                        <div class='card'>
                                                            <div class='body bg-success'>
                                                                <center><h4>Gagal ubah password. Silahkan ulangi...!</h4></center>
                                                            </div>
                                                        </div>
                                                    </div>
                                               </div>";
                                     }
                                 }else{
                                     echo "<div class='row clearfix'>
                                                <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                    <div class='card'>
                                                        <div class='body bg-success'>
                                                            <center><h4>Gagal mengubah password.<br/>Password minimal 6 karakter. Silahkan ulangi...!</h4></center>
                                                        </div>
                                                    </div>
                                                </div>
                                           </div>";
                                 }
                             }else{
                                 echo "<div class='row clearfix'>
                                            <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                <div class='card'>
                                                    <div class='body bg-success'>
                                                        <center><h4>Gagal mengubah password.<br/>Password baru harus cocok. Silahkan ulangi...!</h4></center>
                                                    </div>
                                                </div>
                                            </div>
                                       </div>";
                             }
                         }else{
                             echo "<div class='row clearfix'>
                                        <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                            <div class='card'>
                                                <div class='body bg-success'>
                                                    <center><h4>Gagal mengubah password.<br/>Password lama salah. Silahkan ulangi...!</h4></center>
                                                </div>
                                            </div>
                                        </div>
                                   </div>";
                         }
                    }
                ?>
            </div>
        </div>
    </div>
</div>

<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
