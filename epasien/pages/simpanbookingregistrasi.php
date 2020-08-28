<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    function Proses(){
        if(getOne2("select count(pasien.no_rkm_medis) from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat where kamar_inap.stts_pulang='-' and pasien.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'")>0){
            echo "<div class='block-header'>
                        <h2><center>Gagal melakukan booking</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Pasien sedang dalam masa perawatan di kamar inap..!!</center><br/>
                                    <center><a href='index.php?act=BookingRegistrasi&hal=Booking' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=BookingRegistrasi&hal=Booking",5);
        }else{
            $json       = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
            $json       = json_decode(encrypt_decrypt($json,"d"),true);
            $kd_dokter  = $json["kd_dokter"];
            if (isset($kd_dokter)) {
                $kd_poli    = $json["kd_poli"];
                $tanggal    = $json["tanggal"];
                $thncari    = substr($tanggal,0,4);
                $blncari    = substr($tanggal,5,2);
                $tglcari    = substr($tanggal,8,2);
                $hari       = getOne("select DAYNAME('$thncari-$blncari-$tglcari')");
                $kuota      = $json["kuota"];
                $sekarang   = date("Y-m-d");
                $interval   = getOne2("select (TO_DAYS('$tanggal')-TO_DAYS('$sekarang'))");
                if($interval>0){
                    $daftar = getOne("select count(no_rkm_medis) from booking_registrasi where tanggal_periksa='$tanggal' and kd_dokter='$kd_dokter' and kd_poli='$kd_poli'" ); 
                    if(($kuota>0)&&($daftar>=$kuota)){
                        echo "<div class='block-header'>
                                    <h2><center>Gagal melakukan booking</center></h2>
                              </div>
                              <div class='row clearfix'>
                                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                    <div class='card'>
                                        <div class='body'>
                                            <center>Kuota sudah terpenuhi</center><br/>
                                            <center><a href='index.php?act=BookingRegistrasi&hal=Booking' class='btn btn-danger waves-effect'>Kembali</a></center>
                                        </div>
                                    </div>
                                 </div>
                              </div>";
                        JSRedirect2("index.php?act=BookingRegistrasi&hal=Booking",5);
                    }else{
                        if(!isset($_SESSION["penjab"])){
                            $datapenjab   = "";
                            $querypenjab  = bukaquery("SELECT * from penjab order by png_jawab");
                            while($rsquerypenjab = mysqli_fetch_array($querypenjab)) {
                                $datapenjab=$datapenjab."<option value='$rsquerypenjab[0]'>$rsquerypenjab[1]</option>";
                            }
                            $_SESSION["penjab"]=$datapenjab;
                        }
                        echo "<div class='block-header'>
                                    <h2><center>VERIFIKASI BOOKING</center></h2>
                              </div>
                              <div class='row clearfix'>
                                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                    <div class='card'>
                                        <div class='body'>
                                            <form id='form_validation' action='' method='POST'>
                                                <label for='tgl_registrasi'>Tanggal Rencana Periksa</label>
                                                <div class='form-group'>
                                                    <div class='form-line'>
                                                        <input type='text' readonly class='form-control' value='".konversiHari($hari).", $tglcari ".konversiBulan($blncari)." $thncari' />
                                                    </div>
                                                </div>
                                                <label for='dokter_dipilih'>Dokter Pilihan Anda</label>
                                                <div class='form-group'>
                                                    <div class='form-line'>
                                                        <input type='text' readonly class='form-control' value='".getOne2("select nm_dokter from dokter where kd_dokter='$kd_dokter'")."' />
                                                    </div>
                                                </div>
                                                <label for='poli_dipilih'>Poli/Unit Pilihan Anda</label>
                                                <div class='form-group'>
                                                    <div class='form-line'>
                                                        <input type='text' readonly class='form-control' value='".getOne2("select nm_poli from poliklinik where kd_poli='$kd_poli'")."' /> 
                                                    </div>
                                                </div>
                                                <label for='penjab_dipilih'>Pilihan Jenis Bayar</label>
                                                <div class='form-group'>
                                                    <div class='form-line'>
                                                        <select name='penjab' class='form-control'>
                                                            ".$_SESSION["penjab"]." 
                                                        </select>
                                                    </div>
                                                </div>
                                                <input type='hidden' name='kd_poli' value='$kd_poli' />
                                                <input type='hidden' name='tanggal' value='$tanggal' />
                                                <input type='hidden' name='kd_dokter' value='$kd_dokter' />
                                                <center><button class='btn btn-danger waves-effect' type='submit' name='BtnSimpan'>Sudah Benar</button>&nbsp;&nbsp;<a href='index.php?act=BookingRegistrasi&hal=Booking' class='btn btn-danger waves-effect'>Kembali</a></center>
                                            </form>
                                        </div>
                                    </div>
                                 </div>
                              </div>";
                    }
                }else{
                    echo "<div class='block-header'>
                                <h2><center>Gagal melakukan booking</center></h2>
                          </div>
                          <div class='row clearfix'>
                             <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                <div class='card'>
                                    <div class='body'>
                                        <center>Batas booking 1 hari sebelum periksa</center><br/>
                                        <center><a href='index.php?act=BookingRegistrasi&hal=Booking' class='btn btn-danger waves-effect'>Kembali</a></center>
                                    </div>
                                </div>
                             </div>
                          </div>";
                    JSRedirect2("index.php?act=BookingRegistrasi&hal=Booking",5);
                }
            }else{
                echo "<div class='block-header'>
                                <h2><center>Gagal melakukan booking</center></h2>
                          </div>
                          <div class='row clearfix'>
                             <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                <div class='card'>
                                    <div class='body'>
                                        <center>Kami tidak menemukan data booking Anda</center><br/>
                                        <center><a href='index.php?act=BookingRegistrasi&hal=Booking' class='btn btn-danger waves-effect'>Kembali</a></center>
                                    </div>
                                </div>
                             </div>
                          </div>";
                    JSRedirect2("index.php?act=BookingRegistrasi&hal=Booking",5);
            }
        }
    }
    
    $BtnSimpan = isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
    if (isset($BtnSimpan)) {
        $penjab     = isset($_POST['penjab'])?$_POST['penjab']:NULL;
        $kd_poli    = isset($_POST['kd_poli'])?$_POST['kd_poli']:NULL;
        $tanggal    = isset($_POST['tanggal'])?$_POST['tanggal']:NULL;
        $kd_dokter  = isset($_POST['kd_dokter'])?$_POST['kd_dokter']:NULL;
        if ((!empty($penjab))&&(!empty($kd_poli))&&(!empty($tanggal))&&(!empty($kd_dokter))) {
            $nourut = "";
            switch (URUTNOREG) {
                case "poli" : 
                    $max    = getOne("select ifnull(MAX(CONVERT(no_reg,signed)),0)+1 from booking_registrasi where kd_poli='$kd_poli' and tanggal_periksa='$tanggal'");
                    $nourut = sprintf("%03s", $max);
                    break;
                case "dokter" : 
                    $max    = getOne("select ifnull(MAX(CONVERT(no_reg,signed)),0)+1 from booking_registrasi where kd_dokter='$kd_dokter' and tanggal_periksa='$tanggal'");
                    $nourut = sprintf("%03s", $max);
                    break;
                case "dokter + poli" : 
                    $max    = getOne("select ifnull(MAX(CONVERT(no_reg,signed)),0)+1 from booking_registrasi where kd_poli='$kd_poli' and kd_dokter='$kd_dokter' and tanggal_periksa='$tanggal'");
                    $nourut = sprintf("%03s", $max);
                    break;
                default : 
                    $max    = getOne("select ifnull(MAX(CONVERT(no_reg,signed)),0)+1 from booking_registrasi where kd_dokter='$kd_dokter' and tanggal_periksa='$tanggal'");
                    $nourut = sprintf("%03s", $max);
                    break;
            }
            
            $insert = Tambah4("booking_registrasi","CURRENT_DATE(),CURRENT_TIME(),'".encrypt_decrypt($_SESSION["ses_pasien"],"d")."','$tanggal','$kd_dokter','$kd_poli','$nourut','$penjab','1','$tanggal 00:00:00','Belum'");
            if($insert){
                echo "<div class='block-header'>
                            <h2><center>Proses booking berhasil</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Anda dapat melakukan cekin 1 x 24 jam sebelum pemeriksaan. Silahkan buka riwayat booking untuk melakukan cekin </center><br/>
                                    <center><a href='index.php?act=BookingRegistrasi&hal=Booking' class='btn btn-danger waves-effect'>Kembali</a></center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=BookingRegistrasi&hal=Booking",7);
            }else{
                echo "<div class='block-header'>
                            <h2><center>Gagal melakukan booking</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Terjadi kesalahan, silahkan kontak admin. Pendaftaran booking registrasi hanya diijinkan satu kali per tanggal pemeriksaan</center><br/>
                                    <center><a href='index.php?act=BookingRegistrasi&hal=Booking' class='btn btn-danger waves-effect'>Kembali</a></center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=BookingRegistrasi&hal=Booking",7);
            }
        }else{
            echo "<div class='block-header'>
                        <h2><center>Gagal melakukan booking</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Semua field wajib diisi</center><br/>
                                <center><a href='index.php?act=BookingRegistrasi&hal=Booking' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=SimpanBookingRegistrasi",5);
        }
    }else{
        $validasiregistrasi = getOne2("select wajib_closing_kasir from set_validasi_registrasi");
        if($validasiregistrasi == "Yes"){
            if(getOne("select count(no_rkm_medis) from reg_periksa where no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' and status_bayar='Belum Bayar' and stts<>'Batal'" )>0){
                echo "<div class='block-header'>
                            <h2><center>Gagal melakukan booking</center></h2>
                      </div>
                      <div class='row clearfix'>
                         <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                            <div class='card'>
                                <div class='body'>
                                    <center>Maaf, No.RM Anda pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak admin.. !!</center><br/>
                                    <center><a href='index.php?act=BookingRegistrasi&hal=Booking' class='btn btn-danger waves-effect'>Kembali</a></center>
                                </div>
                            </div>
                         </div>
                      </div>";
                JSRedirect2("index.php?act=BookingRegistrasi&hal=Booking",9);
            }else{
                Proses();
            }
        }else{
            Proses();
        }
    }
        
?>

