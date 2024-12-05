<div class="block-header">
    <h2><center>DASHBOARD DOKTER HARI INI</center></h2>
</div>

<!-- Widgets -->
<div class="row clearfix">
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-pink hover-expand-effect">
            <div class="icon">
                <i class="material-icons">enhanced_encryption</i>
            </div>
            <div class="content">
                <div class="text">KUNJUNGAN</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.kd_dokter='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."' and reg_periksa.tgl_registrasi=current_date()");?>" data-speed="3000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-cyan hover-expand-effect">
            <div class="icon">
                <i class="material-icons">airline_seat_recline_normal</i>
            </div>
            <div class="content">
                <div class="text">RAWAT JALAN</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.kd_dokter='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."' and reg_periksa.tgl_registrasi=current_date() AND reg_periksa.status_lanjut = 'Ralan'");?>" data-speed="2000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-light-green hover-expand-effect">
            <div class="icon">
                <i class="material-icons">local_hotel</i>
            </div>
            <div class="content">
                <div class="text">LANJUT RANAP</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.kd_dokter='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."' and reg_periksa.tgl_registrasi=current_date() AND reg_periksa.status_lanjut = 'Ranap'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-light-blue hover-expand-effect">
            <div class="icon">
                <i class="material-icons">bookmark</i>
            </div>
            <div class="content">
                <div class="text">BELUM DILAYANI</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.kd_dokter='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."' and reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts = 'Belum'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-teal hover-expand-effect">
            <div class="icon">
                <i class="material-icons">local_pharmacy</i>
            </div>
            <div class="content">
                <div class="text">SUDAH DILAYANI</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.kd_dokter='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."' and reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts = 'Sudah'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-orange hover-expand-effect">
            <div class="icon">
                <i class="material-icons">assignment_late</i>
            </div>
            <div class="content">
                <div class="text">BATAL</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.kd_dokter='".validTeks4(encrypt_decrypt($_SESSION["ses_dokter"],"d"),20)."' and reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts = 'Batal'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
</div>