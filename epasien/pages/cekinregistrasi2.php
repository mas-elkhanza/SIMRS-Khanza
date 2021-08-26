<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    
    $json       = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $json       = json_decode(encrypt_decrypt($json,"d"),true);
    if (isset($json["kd_dokter"])) {
        $kd_dokter  = $json["kd_dokter"];
        $kd_poli    = $json["kd_poli"];
        $tanggal    = $json["tanggal"];
        $kd_pj      = $json["kd_pj"];
        $status     = $json["status"];
        $no_reg     = $json["no_reg"];
        $sekarang   = date("Y-m-d");
        $interval   = getOne2("select (TO_DAYS('$tanggal')-TO_DAYS('$sekarang'))");
        if($status == "batal"){
            $update = Ubah2("booking_registrasi","status='Batal' where no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' and tanggal_periksa='$tanggal' and kd_dokter='$kd_dokter' and kd_poli='$kd_poli' and kd_pj='$kd_pj'");
            if($update){
                echo "<div class='block-header'>
                            <h2><center>Pembatalan booking berhasil</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Riwayat booking Anda akan tetap tercatat dan statusnya menjadi batal</center><br/>
                                    <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=HomeUser&hal=Beranda",7);
            }else{
                echo "<div class='block-header'>
                            <h2><center>Pembatalan booking gagal</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Terjadi kesalahan, silahkan kontak admin</center><br/>
                                    <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=HomeUser&hal=Beranda",4);
            }
        }else{
            if($interval<0){
                echo "<div class='block-header'>
                            <h2><center>Gagal melakukan cekin</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Booking Anda sudah kadaluarsa</center><br/>
                                    <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=HomeUser&hal=Beranda",5);
            }else if($interval>1){
                echo "<div class='block-header'>
                            <h2><center>Gagal melakukan cekin</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Cekin hanya bisa dilakukan 24 jam sebelum pemeriksaan</center><br/>
                                    <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=HomeUser&hal=Beranda",7);
            }else{
                Ubah2("pasien","umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr')) where no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");
                $statuspoli  = getOne2("select if((select count(no_rkm_medis) from reg_periksa where no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' and kd_poli='$kd_poli')>0,'Lama','Baru' )");
                $max         = getOne2("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0)+1 from reg_periksa where tgl_registrasi='$tanggal'");
                $no_rawat    = str_replace("-","/",$tanggal."/").sprintf("%06s", $max);
                $sttsumur    = "Th";
                $umur        = 0;
                $querypasien = bukaquery2("select no_rkm_medis,namakeluarga,alamatpj,kelurahanpj,kecamatanpj,kabupatenpj,propinsipj,keluarga,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun,(TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12)) as bulan,
                                          TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(pasien.tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari
                                          from pasien where no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' ");
                if($rsquerypasien = mysqli_fetch_array($querypasien)) {
                    if($rsquerypasien["tahun"] > 0){
                        $umur       = $rsquerypasien["tahun"];
                        $sttsumur   = "Th";
                    }else if($rsquerypasien["tahun"] == 0){
                        if($rsquerypasien["bulan"] > 0){
                            $umur       = $rsquerypasien["bulan"];
                            $sttsumur   = "Bl";
                        }else if($rsquerypasien["bulan"] == 0){
                            $umur       = $rsquerypasien["hari"];
                            $sttsumur   = "Hr";
                        }
                    }

                    $insert     = Tambah4("reg_periksa","'$no_reg','$no_rawat','$tanggal',current_time(),'$kd_dokter','".encrypt_decrypt($_SESSION["ses_pasien"],"d")."','$kd_poli','".$rsquerypasien["namakeluarga"]."','".$rsquerypasien["alamatpj"]."','".$rsquerypasien["keluarga"]."','".getOne2("select registrasilama from poliklinik where kd_poli='$kd_poli'")."','Belum','Lama','Ralan','$kd_pj','$umur','$sttsumur','Belum Bayar','$statuspoli'");
                    if($insert){
                        Ubah3("skdp_bpjs","status='Sudah Periksa' where no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' and tanggal_datang='$tanggal'");
                        Ubah3("booking_registrasi","status='Terdaftar' where no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' and tanggal_periksa='$tanggal' and kd_dokter='$kd_dokter' and kd_poli='$kd_poli' and kd_pj='$kd_pj'");
                        
                        echo "<div class='block-header'>
                                    <h2><center>Proses cekin berhasil</center></h2>
                              </div>
                              <div class='row clearfix'>
                                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                    <div class='card'>
                                        <div class='body'>
                                            <center>Silahkan tunjukkan bukti registerasi/pendaftaran kepada petugas kami jika dibutuhkan</center><br/>
                                            <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                                        </div>
                                    </div>
                                 </div>
                              </div>";
                        JSRedirect2("index.php?act=HomeUser&hal=Beranda",7);
                    }else{
                        echo "<div class='block-header'>
                                    <h2><center>Proses cekin gagal</center></h2>
                              </div>
                              <div class='row clearfix'>
                                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                    <div class='card'>
                                        <div class='body'>
                                            <center>Terjadi kesalahan, silahkan kontak admin</center><br/>
                                            <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                                        </div>
                                    </div>
                                 </div>
                              </div>";
                        JSRedirect2("index.php?act=HomeUser&hal=Beranda",4); 
                    }
                }else{
                    echo "<div class='block-header'>
                                <h2><center>Gagal melakukan cekin</center></h2>
                          </div>
                          <div class='row clearfix'>
                             <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                <div class='card'>
                                    <div class='body'>
                                        <center>Pasien tidak ditemukan</center><br/>
                                        <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                                    </div>
                                </div>
                             </div>
                          </div>";
                    JSRedirect2("index.php?act=HomeUser&hal=Beranda",5);
                }   
            }
        }
    }else{
        echo "<div class='block-header'>
                        <h2><center>Gagal melakukan cekin</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Kami tidak menemukan data booking Anda</center><br/>
                                <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=HomeUser&hal=Beranda",5);
    }
        
?>

