<section id="team" data-stellar-background-ratio="1">
      <div class="container">
           <div class="row">
                <div class="col-md-12 col-sm-12">
                     <div class="about-info">
                          <h2 class="wow fadeInUp" data-wow-delay="0.1s"><center>Dokter Kami</center></h2>
                     </div>
                </div>
                       
                <div class="clearfix"></div>
                <?php
                    $querydokter=bukaquery("select dokter.kd_dokter,left(dokter.nm_dokter,20) as dokter,spesialis.nm_sps,dokter.no_ijn_praktek,pegawai.photo,dokter.no_telp from dokter inner join spesialis on dokter.kd_sps=spesialis.kd_sps inner join pegawai on dokter.kd_dokter=pegawai.nik where dokter.status='1' and dokter.kd_dokter<>'-' order by spesialis.nm_sps");
                    while($rsquerydokter = mysqli_fetch_array($querydokter)) {
                        echo "<div class='col-md-4 col-sm-6'>
                                <div class='team-thumb wow fadeInUp' data-wow-delay='0.2s'>
                                     <img alt='Photo' src='http://".host()."/webapps/penggajian/$rsquerydokter[4]' class='img-responsive' />
                                      <div class='team-info'>
                                           <h3>$rsquerydokter[1]</h3>
                                           <p>$rsquerydokter[2]</p>
                                           <div class='team-contact-info'>
                                                <p><i class='fa fa-phone'></i> HP/Telp. $rsquerydokter[5] </p>
                                                <p><i class='fa fa-envelope-o'></i> No.SIP. $rsquerydokter[3] </p>
                                           </div>
                                           <ul class='social-icon'>
                                                <li><a href='#' class='fa fa-linkedin-square'></a></li>
                                                <li><a href='#' class='fa fa-envelope-o'></a></li>
                                           </ul>
                                      </div>
                                </div>
                                <br/>
                           </div>";
                    }
                ?>
           </div>
      </div>
 </section>