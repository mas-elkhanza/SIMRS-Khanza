<?php
    $btnBooking=isset($_POST['btnBooking'])?$_POST['btnBooking']:NULL;
    if (isset($btnBooking)) {
        $nama         = trim(isset($_POST['nama']))?trim($_POST['nama']):NULL;
        $nama         = strtoupper(cleankar($nama));
        $alamat       = trim(isset($_POST['alamat']))?trim($_POST['alamat']):NULL;
        $alamat       = strtoupper(cleankar($alamat));
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
            $insert=Tambah4(" booking_periksa "," '$no_urut','$ThnDaftar-$BlnDaftar-$TglDaftar','$nama','$alamat','$nohp','$email','$poli','$pesan','Belum Dibalas'");
            if($insert){
                echo "<section id='news' data-stellar-background-ratio='2.5'>
                            <div class='container'>
                                 <div class='row'>
                                      <div class='col-md-12 col-sm-12'>
                                         <div class='section-title wow fadeInUp' data-wow-delay='0.1s'>
                                            <h3>Booking berhasil</h3>
                                         </div>
                                         <div class='news-thumb wow fadeInUp' data-wow-delay='0.2s'>
                                            <br><br>
                                            <b>
                                                <table width='95%' border='0' align='center'>
                                                    <tr><td width='30%' align='left'>No. Booking</td><td width='70%' align='left'>:&nbsp;&nbsp;$no_urut</td></tr>
                                                    <tr><td width='30%' align='left'>Tgl. Booking</td><td width='70%' align='left'>:&nbsp;&nbsp;$TglDaftar-$BlnDaftar-$ThnDaftar</td></tr>
                                                    <tr><td width='30%' align='left'>Nama</td><td width='70%' align='left'>:&nbsp;&nbsp;$nama</td></tr>
                                                    <tr><td width='30%' align='left'>No. Hp/Telp</td><td width='70%' align='left'>:&nbsp;&nbsp;$nohp</td></tr>
                                                    <tr><td width='30%' align='left'>E-Mail</td><td width='70%' align='left'>:&nbsp;&nbsp;$email</td></tr>
                                                    <tr><td width='30%' align='left'>Alamat</td><td width='70%' align='left'>:&nbsp;&nbsp;$alamat</td></tr>
                                                    <tr><td width='30%' align='left'>Unit/Poliklinik</td><td width='70%' align='left'>:&nbsp;&nbsp;".getOne("select nm_poli from poliklinik where kd_poli='$poli'")."</td></tr>
                                                    <tr><td colspan='2'>&nbsp;&nbsp;</td></tr>
                                                    <tr><td colspan='2' align='center'><a href='pages/CetakBooking.php?&nobooking=$no_urut' target=_blank class='btn btn-warning'> Cetak </a></td></tr>
                                                </table>
                                            </b>
                                            <br>
                                         </div>
                                         <br>
                                         Catatan : Nomor Booking bukan merupakan nomor pendaftaran poliklinik/unit. Kami akan melakukan pengecekan terhadap jadwal & kuota dokter yang tersedia berdasarkan booking Anda. Konfirmasi booking periksa akan Kami sampaikan melalui E-Mail atau Nomor HP/Telp Anda. Atau <a href='index.php?act=CekBooking' class='btn btn-danger'>Cek Booking</a> untuk melihat status booking Anda
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
                                                <h3>Gagal melakukan booking.</h3>
                                                <br>
                                                Nomor HP/Telp yang Anda gunakan untuk melakukan booking terdeteksi sudah digunakan sebelumnya pada tanggal yang sama. <a href='index.php?act=CekBooking' class='btn btn-danger'>Cek Booking</a> jika anda sudah melakukan booking sebelumnya 
                                           </div>
                                      </div>
                                 </div>
                            </div>
                      </section>";
            }
                
        }else{
            echo 'Semua field harus isi..!!!';
        }
    }else{
        JSRedirect("index.php?act=Home");
    }
?>