<div class="block-header">
    <h2><center>DASHBOARD MEDICAL CHECK UP</center></h2>
</div>

<!-- Widgets -->
<div class="row clearfix">
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-pink hover-expand-effect">
            <div class="icon">
                <i class="material-icons">enhanced_encryption</i>
            </div>
            <div class="content">
                <div class="text">BOOKING</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(booking_mcu_perusahaan.no_rkm_medis) FROM booking_mcu_perusahaan WHERE booking_mcu_perusahaan.kode_perusahaan='".validTeks4(encrypt_decrypt($_SESSION["ses_emcu"],"d"),20)."'");?>" data-speed="3000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-orange hover-expand-effect">
            <div class="icon">
                <i class="material-icons">assignment_late</i>
            </div>
            <div class="content">
                <div class="text">TERDAFTAR</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(booking_mcu_perusahaan.no_rkm_medis) FROM booking_mcu_perusahaan inner join booking_mcu_perusahaan_berhasil_registrasi on booking_mcu_perusahaan_berhasil_registrasi.no_mcu=booking_mcu_perusahaan.no_mcu WHERE booking_mcu_perusahaan.kode_perusahaan='".validTeks4(encrypt_decrypt($_SESSION["ses_emcu"],"d"),20)."'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-cyan hover-expand-effect">
            <div class="icon">
                <i class="material-icons">airline_seat_recline_normal</i>
            </div>
            <div class="content">
                <div class="text">MENUNGGU HASIL</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(booking_mcu_perusahaan.no_rkm_medis) FROM booking_mcu_perusahaan WHERE booking_mcu_perusahaan.kode_perusahaan='".validTeks4(encrypt_decrypt($_SESSION["ses_emcu"],"d"),20)."' AND booking_mcu_perusahaan.status = 'Menunggu Hasil'");?>" data-speed="2000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-light-green hover-expand-effect">
            <div class="icon">
                <i class="material-icons">local_hotel</i>
            </div>
            <div class="content">
                <div class="text">SELESAI MCU</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(booking_mcu_perusahaan.no_rkm_medis) FROM booking_mcu_perusahaan WHERE booking_mcu_perusahaan.kode_perusahaan='".validTeks4(encrypt_decrypt($_SESSION["ses_emcu"],"d"),20)."' AND booking_mcu_perusahaan.status = 'Selesai'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
</div>