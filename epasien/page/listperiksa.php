<?php
    $btnBooking=isset($_POST['btnBooking'])?$_POST['btnBooking']:NULL;
    if (isset($btnBooking)) {
        $nama         = trim(isset($_POST['nama']))?trim($_POST['nama']):NULL;
        $nama         = cleankar($nama);
        $alamat       = trim(isset($_POST['alamat']))?trim($_POST['alamat']):NULL;
        $alamat       = cleankar($alamat);
        $nohp         = trim(isset($_POST['nohp']))?trim($_POST['nohp']):NULL;
        $nohp         = cleankar($nohp);
        $email        = trim(isset($_POST['email']))?trim($_POST['email']):NULL;
        $email        = validTeks($email);
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
        $max          = getOne("select ifnull(MAX(CONVERT(RIGHT(no_booking,4),signed)),0)+1 from booking_periksa where tanggal='$ThnDaftar-$BlnDaftar-$TglDaftar'");
        $no_urut      = "BP$ThnDaftar$BlnDaftar$TglDaftar".sprintf("%04s", $max);
        if ((!empty($nama))&&(!empty($alamat))&&(!empty($nohp))&&(!empty($email))&&(!empty($pesan))&&(!empty($poli))) {
            Tambah4(" booking_periksa "," '$no_urut','$ThnDaftar-$BlnDaftar-$TglDaftar','$nama','$alamat','$nohp','$email','$poli','$pesan'") or die (JSRedirect("index.php?act=home"));
            echo "<section id='news' data-stellar-background-ratio='2.5'>
                        <div class='container'>
                             <div class='row'>
                                  <div class='col-md-12 col-sm-12'>
                                       <div class='section-title wow fadeInUp' data-wow-delay='0.1s'>
                                            <h3>Booking berhasil dilakukan.</h3>
                                       </div>
                                       <div class='news-thumb wow fadeInUp' data-wow-delay='0.2s'>

                                        </div>
                                  </div>
                             </div>
                        </div>
                  </section>";
        }else{
            echo 'Semua field harus isi..!!!';
        }
    }
?>