<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    $btnBooking=isset($_POST['btnBooking'])?$_POST['btnBooking']:NULL;
    if (isset($btnBooking)) {
        $nama         = trim(isset($_POST['nama']))?trim($_POST['nama']):NULL;
        $nama         = strtoupper(cleankar($nama));
        $alamat       = trim(isset($_POST['alamat']))?trim($_POST['alamat']):NULL;
        $alamat       = strtoupper(cleankar($alamat));
        $nohp         = trim(isset($_POST['nohp']))?trim($_POST['nohp']):NULL;
        $nohp         = cleankar($nohp);
        $email        = trim(isset($_POST['email']))?trim($_POST['email']):NULL;
        $email        = cleankar($email);
        $pesan        = trim(isset($_POST['pesan']))?trim($_POST['pesan']):NULL;
        $pesan        = cleankar($pesan);
        $TglDaftar    = trim(isset($_POST['TglDaftar']))?trim($_POST['TglDaftar']):NULL;
        $TglDaftar    = cleankar($TglDaftar);
        $BlnDaftar    = trim(isset($_POST['BlnDaftar']))?trim($_POST['BlnDaftar']):NULL;
        $BlnDaftar    = cleankar($BlnDaftar);
        $ThnDaftar    = trim(isset($_POST['ThnDaftar']))?trim($_POST['ThnDaftar']):NULL;
        $ThnDaftar    = cleankar($ThnDaftar);
        $poli         = trim(isset($_POST['poli']))?trim($_POST['poli']):NULL;
        $poli         = cleankar($poli);
        $sekarang     = date("Y-m-d H:i:s");
        $interval     = getOne2("select (TO_DAYS('$ThnDaftar-$BlnDaftar-$TglDaftar')-TO_DAYS('$sekarang'))");
        if($interval>0){
            if ((!empty($nama))&&(!empty($alamat))&&(!empty($nohp))&&(!empty($email))&&(!empty($pesan))&&(!empty($poli))) {
                $max          = getOne2("select ifnull(MAX(CONVERT(RIGHT(no_booking,4),signed)),0)+1 from booking_periksa where tanggal='$ThnDaftar-$BlnDaftar-$TglDaftar'");
                $no_urut      = "BP$ThnDaftar$BlnDaftar$TglDaftar".sprintf("%04s", $max);
                $insert       = Tambah4("booking_periksa"," '$no_urut','$ThnDaftar-$BlnDaftar-$TglDaftar','$nama','$alamat','$nohp','$email','$poli','$pesan','Belum Dibalas','$sekarang'");
                if($insert){
                    echo "<section id='news' data-stellar-background-ratio='2.5'>
                             <div class='container'>
                                 <div class='row'>
                                     <div class='col-md-12 col-sm-12'>
                                         <div class='section-title wow fadeInUp' data-wow-delay='0.1s'>
                                            <h3>Booking berhasil</h3>
                                         </div>
                                         <div class='news-thumb wow fadeInUp' data-wow-delay='0.2s'>
                                            <br>
                                            <b>
                                            <table width='95%' border='0' align='center'>
                                                <tr><td width='29%' align='left' valign='top'>No. Booking</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>$no_urut</td></tr>
                                                <tr><td width='29%' align='left' valign='top'>Tgl. Booking</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>$sekarang</td></tr>
                                                <tr><td width='29%' align='left' valign='top'>Tgl. Periksa</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>$TglDaftar-$BlnDaftar-$ThnDaftar</td></tr>
                                                <tr><td width='29%' align='left' valign='top'>Nama</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>$nama</td></tr>
                                                <tr><td width='29%' align='left' valign='top'>No. Hp/Telp</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>$nohp</td></tr>
                                                <tr><td width='29%' align='left' valign='top'>E-Mail</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>$email</td></tr>
                                                <tr><td width='29%' align='left' valign='top'>Alamat</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>$alamat</td></tr>
                                                <tr><td width='29%' align='left' valign='top'>Unit/Poliklinik</td><td valign='top' width='3%' align='center'>:</td><td width='68%' align='left' valign='top'>".getOne("select nm_poli from poliklinik where kd_poli='$poli'")."</td></tr>
                                            </table>
                                            </b>
                                            <br>
                                         </div>
                                         <a href='pages/CetakBooking.php?iyem=".encrypt_decrypt("{\"nobooking\":\"$no_urut\"}","e")."' target=_blank class='form-control btn btn-success wow fadeInUp'> Cetak </a>
                                         <br><br>
                                         Catatan : Nomor booking wajib anda ingat. Nomor booking bukan merupakan nomor pendaftaran poliklinik/unit. Kami akan melakukan pengecekan terhadap jadwal & kuota dokter yang tersedia berdasarkan booking Anda. Konfirmasi booking periksa akan Kami sampaikan melalui E-Mail atau Nomor HP/Telp Anda. Atau <a href='index.php?act=CekBooking' class='btn btn-danger'>Cek Booking</a> untuk melihat status booking Anda
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
                                                    <h3>Gagal melakukan booking</h3><br>
                                                    Nomor HP/Telp yang Anda gunakan untuk melakukan booking terdeteksi sudah digunakan sebelumnya pada tanggal yang sama. <a href='index.php?act=CekBooking' class='btn btn-danger'>Cek Booking</a> jika anda sudah melakukan booking sebelumnya 
                                               </div>
                                          </div>
                                     </div>
                                </div>
                          </section>";
                }
            }else{
                echo "<section id='news' data-stellar-background-ratio='2.5'>
                            <div class='container'>
                                 <div class='row'>
                                      <div class='col-md-12 col-sm-12'>
                                           <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                                <h3>Gagal melakukan booking</h3><br>
                                                Semua field wajib diisi
                                           </div>
                                      </div>
                                 </div>
                            </div>
                      </section>";
                JSRedirect2("index.php?act=Home",4);
            }
        }else{
            echo "<section id='news' data-stellar-background-ratio='2.5'>
                        <div class='container'>
                             <div class='row'>
                                  <div class='col-md-12 col-sm-12'>
                                       <div class='about-info wow fadeInUp' data-wow-delay='0.1s'>
                                            <h3>Gagal melakukan booking</h3><br>
                                            Maksimal booking 1 hari sebelum periksa
                                       </div>
                                  </div>
                             </div>
                        </div>
                  </section>";
            JSRedirect2("index.php?act=Home",4);
        }
    }else{
        JSRedirect("index.php?act=Home");
    }
?>