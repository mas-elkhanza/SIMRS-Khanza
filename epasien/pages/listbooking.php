<?php
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
                                                 <h3>Booking tidak ditemukan</h3>
                                                 <br>
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
                                                    <h3>Booking kadaluarsa</h3>
                                                    <br>
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
                                                        <h3>No. Booking $nobooking</h3>
                                                        <br>
                                                        Mohon maaf, booking Anda masih menunggu peninjauan dari admin Kami. Silahkan cek kembali beberapa saat lagi   
                                                   </div>
                                              </div>
                                         </div>
                                    </div>
                              </section>";
                        JSRedirect2("index.php?act=Home",7);
                    }
                }
            }
        }else{
            echo "<section id='news' data-stellar-background-ratio='2.5'>
                        <div class='container'>
                             <div class='row'>
                                  <div class='col-md-12 col-sm-12'>
                                       <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                            <h3>Gagal</h3>
                                            <br> 
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
                                            <input type=\"text\" class=\"form-control\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi1'));\" id=\"TxtIsi1\" name=\"nobooking\" placeholder=\"Nomor Booking\" autocomplete=\"off\">
                                            <span id=\"MsgIsi1\" style=\"color:#CC0000; font-size:10px;\"></span>
                                         </div>
                                         <div class=\"col-md-12 col-sm-12\">
                                            <label for=\"nohp\">Nomor HP/Telp</label>
                                            <input type=\"tel\" class=\"form-control\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi2'));\" id=\"TxtIsi2\" name=\"nohp\" placeholder=\"Nomor HP/Telp\" autocomplete=\"off\">
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

        