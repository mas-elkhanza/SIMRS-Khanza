<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<head>
   <link rel="stylesheet" type="text/css" href="capca.css">
</head>
<body>
     <section id="appointment" data-stellar-background-ratio="3">
          <div class="container">
               <div class="row">
                    <div class="col-md-6 col-sm-6">
                         <img src="images/appointment-image.jpg" class="img-responsive" alt="">
                    </div>
                    <div class="col-md-6 col-sm-6">
                        <div class="section-title wow fadeInUp" data-wow-delay="0.4s">
                           <h2><center>Log In Pasien</center></h2>
                        </div>
                        <div class="wow fadeInUp" data-wow-delay="0.8s">
                            <div class="col-md-12 col-sm-12">
                                Jika anda pasien lama atau pernah berobat sebelumnya, untuk nomor rekam medis dan password login bisa Anda tanyakan kepada petugas Kami saat Anda melakukan registrasi secara offline. Dan password bisa Anda ubah setelah login di aplikasi EPasien. Jika Anda pasien baru dan belum pernah periksa sebelumnya, silahkan melakukan booking atau buat janji melalui menu utama EPasien ini. Setelah admin kami melakukan verifikasi data, Anda akan mendapat password login dan antrian periksa sesuai booking Anda.<br/><br/><br/>
                            </div>
                            <?php 
                                $BtnLogin=isset($_POST['BtnLogin'])?$_POST['BtnLogin']:NULL;
                                if (isset($BtnLogin)) {
                                    if(@$_SESSION["Capcay"]!= getOne2("select aes_encrypt(".cleankar($_POST["inputcaptcha"]).",'windi')")){
                                        echo "<form id=\"appointment-form\" role=\"form\" onsubmit=\"return validasiIsi();\" method=\"post\" action=\"\" enctype=multipart/form-data>
                                                    <div class=\"col-md-12 col-sm-12\">
                                                        <label for=\"norme\">Nomer Rekam Medis</label>
                                                        <input type=\"password\" class=\"form-control\" name=\"norme\" pattern=\"[A-Z0-9-]{1,65}\" title=\" A-Z0-9- (Maksimal 65 karakter)\" required placeholder=\"Masukkan Nomor Rekam Medis\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi1'));\" id=\"TxtIsi1\" autocomplete=\"off\" autofocus/>
                                                        <span id=\"MsgIsi1\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                    </div>
                                                    <div class=\"col-md-12 col-sm-12\">
                                                        <label for=\"passworde\">Password</label>
                                                        <input type=\"password\" class=\"form-control\" name=\"passworde\" required placeholder=\"Masukkan Password\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi2'));\" id=\"TxtIsi2\" autocomplete=\"off\" />
                                                        <span id=\"MsgIsi2\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                    </div>
                                                    <div class=\"col-md-12 col-sm-12\">
                                                        <label for=\"captcha\">Captcha</label>
                                                        <table width=\"100%\" border=\"0\">
                                                            <tr>
                                                                <td width=\"50%\" valign=\"top\">
                                                                    <img width=\"95%\" height=\"45px\" src=\"pages/captcha.php\" alt=\"gambar\" />
                                                                </td>
                                                                <td width=\"50%\">
                                                                    <input type=\"text\" class=\"form-control\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi3'));\" id=\"TxtIsi3\" name=\"inputcaptcha\" pattern=\"[0-9]{1,6}\" title=\" 0-9 (Maksimal 6 karakter)\" required placeholder=\"Masukkan Captcha\" autocomplete=\"off\" />
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <span id=\"MsgIsi3\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                    </div>
                                                    <div class=\"col-md-12 col-sm-12\">
                                                        <span style=\"color:#CC0000; font-size:12px;\">Captcha tidak sesuai, silahkan ulangi ...!</span><br/><br/>
                                                    </div>
                                                    <div class=\"col-md-12 col-sm-12\">
                                                        <button type=\"submit\" class=\"form-control\" id=\"cf-submit\" name=\"BtnLogin\">Log In</button>
                                                    </div>
                                               </form>";
                                    }else{
                                        unset($_SESSION['Capcay']);
                                        $usere      = cleankar($_POST['norme']);
                                        $passworde  = cleankar2($_POST['passworde']);
                                        if(getOne2("select count(*) from personal_pasien where no_rkm_medis='$usere' and password=aes_encrypt('$passworde','windi')")>0){
                                            $_SESSION["ses_pasien"]= encrypt_decrypt($usere,"e");
                                            exit(header("Location:index.php"));
                                        }else{
                                            echo "<form id=\"appointment-form\" role=\"form\" onsubmit=\"return validasiIsi();\" method=\"post\" action=\"\" enctype=multipart/form-data>
                                                        <div class=\"col-md-12 col-sm-12\">
                                                            <label for=\"norme\">Nomer Rekam Medis</label>
                                                            <input type=\"password\" class=\"form-control\" name=\"norme\" pattern=\"[A-Z0-9-]{1,65}\" title=\" A-Z0-9- (Maksimal 65 karakter)\" required placeholder=\"Masukkan Nomor Rekam Medis\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi1'));\" id=\"TxtIsi1\" autocomplete=\"off\" autofocus/>
                                                            <span id=\"MsgIsi1\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                        </div>
                                                        <div class=\"col-md-12 col-sm-12\">
                                                            <label for=\"passworde\">Password</label>
                                                            <input type=\"password\" class=\"form-control\" name=\"passworde\" required placeholder=\"Masukkan Password\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi2'));\" id=\"TxtIsi2\" autocomplete=\"off\" />
                                                            <span id=\"MsgIsi2\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                        </div>
                                                        <div class=\"col-md-12 col-sm-12\">
                                                            <span style=\"color:#CC0000; font-size:12px;\">Maaf, gagal login. Nomor rekam medis atau password ada yang salah ...!</span><br/><br/>
                                                        </div>
                                                        <div class=\"col-md-12 col-sm-12\">
                                                            <label for=\"captcha\">Captcha</label>
                                                            <table width=\"100%\" border=\"0\">
                                                                <tr>
                                                                    <td width=\"50%\" valign=\"top\">
                                                                        <img width=\"95%\" height=\"45px\" src=\"pages/captcha.php\" alt=\"gambar\" />
                                                                    </td>
                                                                    <td width=\"50%\">
                                                                        <input type=\"text\" class=\"form-control\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi3'));\" id=\"TxtIsi3\" name=\"inputcaptcha\" pattern=\"[0-9]{1,6}\" title=\" 0-9 (Maksimal 6 karakter)\" required placeholder=\"Masukkan Captcha\" autocomplete=\"off\" />
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <span id=\"MsgIsi3\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                        </div>
                                                        <div class=\"col-md-12 col-sm-12\">
                                                            <button type=\"submit\" class=\"form-control\" id=\"cf-submit\" name=\"BtnLogin\">Log In</button>
                                                        </div>
                                                   </form>";
                                        }
                                    }
                                }else{
                                    echo "<form id=\"appointment-form\" role=\"form\" onsubmit=\"return validasiIsi();\" method=\"post\" action=\"\" enctype=multipart/form-data>
                                                <div class=\"col-md-12 col-sm-12\">
                                                    <label for=\"norme\">Nomer Rekam Medis</label>
                                                    <input type=\"password\" class=\"form-control\" name=\"norme\" pattern=\"[A-Z0-9-]{1,65}\" title=\" A-Z0-9- (Maksimal 65 karakter)\" required placeholder=\"Masukkan Nomor Rekam Medis\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi1'));\" id=\"TxtIsi1\" autocomplete=\"off\" autofocus/>
                                                    <span id=\"MsgIsi1\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                </div>
                                                <div class=\"col-md-12 col-sm-12\">
                                                    <label for=\"passworde\">Password</label>
                                                    <input type=\"password\" class=\"form-control\" name=\"passworde\" required placeholder=\"Masukkan Password\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi2'));\" id=\"TxtIsi2\" autocomplete=\"off\" />
                                                    <span id=\"MsgIsi2\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                </div>
                                                <div class=\"col-md-12 col-sm-12\">
                                                    <label for=\"captcha\">Captcha</label>
                                                    <table width=\"100%\" border=\"0\">
                                                        <tr>
                                                            <td width=\"50%\" valign=\"top\">
                                                                <img width=\"95%\" height=\"45px\" src=\"pages/captcha.php\" alt=\"gambar\" />
                                                            </td>
                                                            <td width=\"50%\">
                                                                <input type=\"text\" class=\"form-control\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi3'));\" id=\"TxtIsi3\" name=\"inputcaptcha\" pattern=\"[0-9]{1,6}\" title=\" 0-9 (Maksimal 6 karakter)\" required placeholder=\"Masukkan Captcha\" autocomplete=\"off\" />
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <span id=\"MsgIsi3\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                </div>
                                                <div class=\"col-md-12 col-sm-12\">
                                                    <button type=\"submit\" class=\"form-control\" id=\"cf-submit\" name=\"BtnLogin\">Log In</button>
                                                </div>
                                           </form>";
                                }
                            ?>
                        </div>
                    </div>
               </div>
          </div>
     </section>
</body>

    