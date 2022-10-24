<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    $btnCekBooking=isset($_POST['btnCekBooking'])?$_POST['btnCekBooking']:NULL;
    if (isset($btnCekBooking)) {
        $nohp               = trim(isset($_POST['nohp']))?trim($_POST['nohp']):NULL;
        $nohp               = cleankar($nohp);
        $nobooking          = trim(isset($_POST['nobooking']))?trim($_POST['nobooking']):NULL;
        $nobooking          = cleankar($nobooking);
        $querycekbooking    = bukaquery("select count(no_booking) as noboking,if(tanggal>current_date,'aman','kadaluarsa') as tanggal,status from booking_periksa where no_booking='$nobooking' and no_telp='$nohp'");
        if($rsquerycekbooking = mysqli_fetch_array($querycekbooking)) {
            if($rsquerycekbooking["noboking"]==0){
                echo "<section id='news' data-stellar-background-ratio='2.5'>
                        <div class='container'>
                            <div class='row'>
                                <div class='col-md-12 col-sm-12'>
                                    <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                        <h3>Booking tidak ditemukan</h3><br>
                                        Silahkan masukkan No. Booking dan No. Hp/Telp dengan benar... !!! 
                                    </div>
                                </div>
                            </div>
                        </div>
                      </section>";
                JSRedirect2("index.php?act=CekBooking",4);
            }else if($rsquerycekbooking["noboking"]>0){
                if($rsquerycekbooking["tanggal"]=="kadaluarsa"){
                    echo "<section id='news' data-stellar-background-ratio='2.5'>
                            <div class='container'>
                                <div class='row'>
                                    <div class='col-md-12 col-sm-12'>
                                        <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                            <h3>Booking kadaluarsa</h3><br>
                                            Silahkan melakukan booking ulang... !!! 
                                        </div>
                                    </div>
                                </div>
                            </div>
                          </section>";
                    JSRedirect2("index.php?act=Home",4);
                }else{
                    if($rsquerycekbooking["status"]=="Belum Dibalas"){
                        echo "<section id='news' data-stellar-background-ratio='2.5'>
                                <div class='container'>
                                    <div class='row'>
                                        <div class='col-md-12 col-sm-12'>
                                            <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                                <h3>No. Booking $nobooking</h3><br>
                                                Mohon maaf, booking Anda masih menunggu peninjauan dari admin Kami. Silahkan cek kembali beberapa saat lagi   
                                            </div>
                                        </div>
                                    </div>
                                </div>
                              </section>";
                        JSRedirect2("index.php?act=CekBooking",7);
                    }else if($rsquerycekbooking["status"]=="Ditolak"){
                        $balasan = getOne2("select balasan from booking_periksa_balasan where no_booking='$nobooking'");
                        echo "<section id='news' data-stellar-background-ratio='2.5'>
                                <div class='container'>
                                    <div class='row'>
                                        <div class='col-md-12 col-sm-12'>
                                            <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                                <h3>No. Booking $nobooking</h3><br>
                                                Mohon maaf, booking Anda ditolak".($balasan==""?"":". $balasan").". Silahkan buat janji/booking kembali untuk tanggal/poli yang lain
                                            </div>
                                        </div>
                                    </div>
                                </div>
                              </section>";
                        JSRedirect2("index.php?act=Home#appointment",14);
                    }else if($rsquerycekbooking["status"]=="Diterima"){
                        $querycekbookingperiksa = bukaquery("select booking_registrasi.tanggal_booking,booking_registrasi.jam_booking,booking_registrasi.no_rkm_medis,booking_periksa.nama,booking_periksa.alamat,booking_periksa.no_telp,booking_periksa.email,booking_registrasi.tanggal_periksa,dokter.nm_dokter,poliklinik.nm_poli,booking_registrasi.no_reg,aes_decrypt(personal_pasien.password,'windi') as pass from booking_registrasi inner join dokter on booking_registrasi.kd_dokter=dokter.kd_dokter inner join poliklinik on booking_registrasi.kd_poli=poliklinik.kd_poli inner join booking_periksa_diterima on booking_periksa_diterima.no_rkm_medis=booking_registrasi.no_rkm_medis inner join booking_periksa on booking_periksa_diterima.no_booking=booking_periksa.no_booking inner join personal_pasien on booking_registrasi.no_rkm_medis=personal_pasien.no_rkm_medis where booking_periksa.no_booking='$nobooking'"); 
                        if($rsquerycekbookingperiksa = mysqli_fetch_array($querycekbookingperiksa)) {
                            $balasan = getOne2("select balasan from booking_periksa_balasan where no_booking='$nobooking'");
                            echo "<section id='news' data-stellar-background-ratio='2.5'>
                                    <div class='container'>
                                        <div class='row'>
                                            <div class='col-md-12 col-sm-12'>
                                                <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                                    <h3>No. Booking $nobooking</h3><br>
                                                    Booking Anda diterima, admin Kami sudah melakukan verifikasi data Anda".($balasan==""?"":".<br>$balasan").".<br><br>
                                                    <div class='news-thumb wow fadeInUp' data-wow-delay='0.2s'>
                                                        <br>
                                                        <b>
                                                           <table width='95%' border='0' align='center'>
                                                             <tr><td width='29%' align='left' valign='top'>Tgl. Booking</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["tanggal_booking"]." ".$rsquerycekbookingperiksa["jam_booking"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>Tgl. Periksa</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["tanggal_periksa"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>No.Rekam Medis</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["no_rkm_medis"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>Nama Pasien</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["nama"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>No. Hp/Telp</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["no_telp"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>E-Mail</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["email"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>Alamat</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["alamat"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>Unit/Poliklinik</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["nm_poli"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>Dokter</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["nm_dokter"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>No.Antri Poli</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["no_reg"]."</td></tr>
                                                             <tr><td width='29%' align='left' valign='top'>Password Login</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".$rsquerycekbookingperiksa["pass"]."</td></tr>
                                                           </table>
                                                        </b>
                                                        <br>
                                                    </div>                
                                                    <br>Silahkan hapalkan nomor rekam medis dan password Anda, dan Anda wajib menjaga kerahasiaannya. Klik <a href='index.php?act=LoginPasien' class='btn btn-success' >Log In</a> dan gunakan nomor rekam medis serta password Anda untuk masuk ke aplikasi EPasien kami. Untuk menjaga keamanan data, silahkan ubah password default yang sudah kami berikan di aplikasi EPasien setelah anda login.                     
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                  </section>";
                        }else{
                            echo "<section id='news' data-stellar-background-ratio='2.5'>
                                    <div class='container'>
                                        <div class='row'>
                                            <div class='col-md-12 col-sm-12'>
                                                <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                                    <h3>Gagal</h3><br> 
                                                    Terjadi kesalahan saat pengecekan booking
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                  </section>";
                            JSRedirect2("index.php?act=CekBooking",4);
                        }
                    }
                }
            }
        }else{
            echo "<section id='news' data-stellar-background-ratio='2.5'>
                    <div class='container'>
                        <div class='row'>
                            <div class='col-md-12 col-sm-12'>
                                <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                    <h3>Gagal</h3><br> 
                                    Terjadi kesalahan saat pengecekan booking
                                </div>
                            </div>
                        </div>
                    </div>
                  </section>";
            JSRedirect2("index.php?act=CekBooking",4);
        }
            
    }else{
        echo "<section id=\"appointment\" data-stellar-background-ratio=\"3\">
                <div class=\"container\">
                    <div class=\"row\">
                        <div class=\"col-md-12 col-sm-12\">
                            <form id=\"appointment-form\" role=\"form\" onsubmit=\"return validasiIsi();\" method=\"post\" action=\"\">
                                <div class=\"section-title wow fadeInUp\" data-wow-delay=\"0.4s\">
                                    <h2><center>Cek Status Booking</center></h2>
                                </div>
                                <div class=\"wow fadeInUp\" data-wow-delay=\"0.8s\">
                                    <div class=\"col-md-12 col-sm-12\">
                                        <label for=\"nobooking\">Nomor Booking</label>
                                        <input type=\"text\" class=\"form-control\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi1'));\" id=\"TxtIsi1\" pattern=\"[A-Z0-9]{1,65}\" title=\" A-Z0-9 (Maksimal 65 karakter)\" required name=\"nobooking\" placeholder=\"Nomor Booking\" autocomplete=\"off\" autofocus/>
                                        <span id=\"MsgIsi1\" style=\"color:#CC0000; font-size:10px;\"></span>
                                    </div>
                                    <div class=\"col-md-12 col-sm-12\">
                                        <label for=\"nohp\">Nomor HP/Telp</label>
                                        <input type=\"tel\" class=\"form-control\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi2'));\" id=\"TxtIsi2\" pattern=\"[0-9]{1,65}\" title=\" 0-9 (Maksimal 65 karakter)\" required name=\"nohp\" placeholder=\"Nomor HP/Telp\" autocomplete=\"off\" />
                                        <span id=\"MsgIsi2\" style=\"color:#CC0000; font-size:10px;\"></span>
                                    </div>     
                                    <div class=\"col-md-12 col-sm-12\">
                                        <button type=\"submit\" class=\"form-control\" id=\"cf-submit\" name=\"btnCekBooking\">Cek Status</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
              </section>";
    }
?>

        