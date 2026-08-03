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
                    <label for="userlama">User Lama</label>
                    <div class="form-group">
                        <div class="form-line">
                            <input name="userlama" type="password" id="userlama" class="form-control" placeholder="Masukkan user lama..." pattern="[a-zA-Z0-9, ./@_]{1,25}" title=" a-zA-Z0-9, ./@_ (Maksimal 25 karakter)" maxlength="25" autocomplete="off" autofocus required/>
                        </div>
                    </div>
                    <label for="userbaru">User Baru</label>
                    <div class="form-group">
                        <div class="form-line">
                            <input name="userbaru" type="password" id="userbaru" class="form-control" placeholder="Masukkan user baru..." pattern="[a-zA-Z0-9, ./@_]{1,25}" title=" a-zA-Z0-9, ./@_ (Maksimal 25 karakter)" maxlength="25" autocomplete="off" required/>
                        </div>
                    </div>
                    <label for="userbaruulangi">Ulangi User Baru</label>
                    <div class="form-group">
                        <div class="form-line">
                            <input name="userbaruulangi" type="password" id="userbaruulangi" class="form-control" placeholder="Masukkan user baru sekali lagi..." pattern="[a-zA-Z0-9, ./@_]{1,25}" title=" a-zA-Z0-9, ./@_ (Maksimal 25 karakter)" maxlength="25" autocomplete="off" required/>
                        </div>
                    </div>
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
                         $userlama            = validTeks4($_POST['userlama'],25);
                         $userbaru            = validTeks4($_POST['userbaru'],25);
                         $userbaruulangi      = validTeks4($_POST['userbaruulangi'],25);
                         $passwordlama        = validTeks4($_POST['passwordlama'],25);
                         $passwordbaru        = validTeks4($_POST['passwordbaru'],25);
                         $passwordbaruulangi  = validTeks4($_POST['passwordbaruulangi'],25);
                         if(getOne2("select AES_DECRYPT(e_eksekutif.passworde,'windi') from e_eksekutif")==$passwordlama){
                             if($passwordbaru==$passwordbaruulangi){
                                 if(strlen($passwordbaru)>5){
                                     if(getOne2("select AES_DECRYPT(e_eksekutif.usere,'nur') from e_eksekutif")==$userlama){
                                        if($userbaru==$userbaruulangi){
                                            if(strlen($userbaru)>5){
                                                if(bukaquery2("update e_eksekutif set usere=AES_ENCRYPT('$userbaru','nur'),passworde=AES_ENCRYPT('$passwordbaru','windi') ")){
                                                    echo "<div class='row clearfix'>
                                                               <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                                   <div class='card'>
                                                                       <div class='body bg-danger'>
                                                                           <center><h4>Ubah user berhasil...</h4></center>
                                                                       </div>
                                                                   </div>
                                                               </div>
                                                          </div>";
                                                }else{
                                                    echo "<div class='row clearfix'>
                                                               <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                                   <div class='card'>
                                                                       <div class='body bg-success'>
                                                                           <center><h4>Gagal ubah user. Silahkan ulangi...!</h4></center>
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
                                                                       <center><h4>Gagal mengubah user.<br>User minimal 6 karakter. Silahkan ulangi...!</h4></center>
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
                                                                   <center><h4>Gagal mengubah user.<br>User baru harus cocok. Silahkan ulangi...!</h4></center>
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
                                                               <center><h4>Gagal mengubah user.<br>User lama salah. Silahkan ulangi...!</h4></center>
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
                                                            <center><h4>Gagal mengubah password.<br>Password minimal 6 karakter. Silahkan ulangi...!</h4></center>
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
                                                        <center><h4>Gagal mengubah password.<br>Password baru harus cocok. Silahkan ulangi...!</h4></center>
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
                                                    <center><h4>Gagal mengubah password.<br>Password lama salah. Silahkan ulangi...!</h4></center>
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
