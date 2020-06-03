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
                            <form id="appointment-form" role="form" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
                               <div class="col-md-12 col-sm-12">
                                    <label for="norm">Nomer Rekam Medis</label>
                                    <input type="password" class="form-control" name="norm" placeholder="Nomor Rekam Medis" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" autocomplete="off">
                                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                               </div>
                               <div class="col-md-12 col-sm-12">
                                    <label for="password">Password</label>
                                    <input type="password" class="form-control" name="password" placeholder="Password Login" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2" autocomplete="off">
                                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                               </div>
                               <div class="col-md-12 col-sm-12">
                                   <label for="captcha">Captcha</label>
                                   <table width="100%" border="0">
                                        <tr>
                                            <td width="50%" valign="top">
                                                <img width="95%" height="45px" src="pages/captcha.php" alt="gambar" />
                                            </td>
                                            <td width="50%">
                                                <input type="text" class="form-control" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3" name="inputcaptcha" placeholder="Captcha" autocomplete="off">
                                                <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                                            </td>
                                        </tr>
                                    </table>
                               </div>
                               <div class="col-md-12 col-sm-12">
                                    <button type="submit" class="form-control" id="cf-submit" name="submit">Log In</button>
                               </div>
                            </form>
                        </div>
                    </div>
               </div>
          </div>
     </section>
</body>
    