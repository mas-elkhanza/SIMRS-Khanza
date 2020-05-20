 <section id="appointment" data-stellar-background-ratio="3">
      <div class="container">
           <div class="row">
                <div class="col-md-12 col-sm-12">
                     <form id="appointment-form" role="form" onsubmit="return validasiIsi();" method="post" action="#">
                          <div class="section-title wow fadeInUp" data-wow-delay="0.4s">
                               <h2><center>Cek Status Booking</center></h2>
                          </div>
                          <div class="wow fadeInUp" data-wow-delay="0.8s">
                               <div class="col-md-12 col-sm-12">
                                  <label for="nobooking">Nomor Booking</label>
                                  <input type="text" class="form-control" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" name="nobooking" placeholder="Nomor Booking" autocomplete="off">
                                  <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                               </div>
                               <div class="col-md-12 col-sm-12">
                                  <label for="nohp">Nomor HP/Telp</label>
                                  <input type="tel" class="form-control" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2" name="nohp" placeholder="Nomor HP/Telp" autocomplete="off">
                                  <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                               </div>     
                               <div class="col-md-12 col-sm-12">
                                   <button type="submit" class="form-control" id="cf-submit" name="submit">Cek Status</button>
                               </div>
                          </div>
                    </form>
                </div>
           </div>
      </div>
 </section>