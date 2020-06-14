<div class="block-header">
    <h2>DASHBOARD PASIEN</h2>
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
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(no_rkm_medis) FROM reg_periksa WHERE no_rkm_medis = '".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");?>" data-speed="3000" data-fresh-interval="20"></div>
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
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(no_rkm_medis) FROM reg_periksa WHERE no_rkm_medis = '".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' AND status_lanjut = 'Ralan'");?>" data-speed="2000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-light-green hover-expand-effect">
            <div class="icon">
                <i class="material-icons">local_hotel</i>
            </div>
            <div class="content">
                <div class="text">RAWAT INAP</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(no_rkm_medis) FROM reg_periksa WHERE no_rkm_medis = '".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' AND status_lanjut = 'Ranap'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-orange hover-expand-effect">
            <div class="icon">
                <i class="material-icons">today</i>
            </div>
            <div class="content">
                <div class="text">BULAN INI</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(no_rkm_medis) FROM reg_periksa WHERE tgl_registrasi LIKE '%".date('Y-m')."%' AND no_rkm_medis = '".encrypt_decrypt($_SESSION["ses_pasien"],"d")."'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
</div>
