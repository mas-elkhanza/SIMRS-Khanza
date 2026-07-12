<?php
    $querycarabayar = bukaquery("select penjab.png_jawab,count(reg_periksa.no_rawat) as jumlah from reg_periksa inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.tgl_registrasi=current_date() group by penjab.kd_pj order by jumlah desc");
    $labelcarabayar = array();
    $datacarabayar  = array();
    while($rsquerycarabayar = mysqli_fetch_array($querycarabayar)) {
        $labelcarabayar[] = $rsquerycarabayar["png_jawab"];
        $datacarabayar[]  = $rsquerycarabayar["jumlah"];
    }

    $querydokter = bukaquery("select dokter.nm_dokter,count(reg_periksa.no_rawat) as jumlah from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.tgl_registrasi=current_date() group by dokter.kd_dokter order by jumlah desc");
    $labeldokter = array();
    $datadokter  = array();
    while($rsquerydokter = mysqli_fetch_array($querydokter)) {
        $labeldokter[] = $rsquerydokter["nm_dokter"];
        $datadokter[]  = $rsquerydokter["jumlah"];
    }

    $querypoli = bukaquery("select poliklinik.nm_poli,count(reg_periksa.no_rawat) as jumlah from reg_periksa inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.tgl_registrasi=current_date() group by poliklinik.kd_poli order by jumlah desc");
    $labelpoli = array();
    $datapoli  = array();
    while($rsquerypoli = mysqli_fetch_array($querypoli)) {
        $labelpoli[] = $rsquerypoli["nm_poli"];
        $datapoli[]  = $rsquerypoli["jumlah"];
    }

    $querybangsal = bukaquery("select bangsal.nm_bangsal,count(kamar_inap.no_rawat) as jumlah from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.tgl_keluar='0000-00-00' and kamar.statusdata='1' and bangsal.status='1' group by bangsal.kd_bangsal order by jumlah desc");
    $labelbangsal = array();
    $databangsal  = array();
    while($rsquerybangsal = mysqli_fetch_array($querybangsal)) {
        $labelbangsal[] = $rsquerybangsal["nm_bangsal"];
        $databangsal[]  = $rsquerybangsal["jumlah"];
    }

    $querykelas = bukaquery("select kamar.kelas,count(kamar_inap.no_rawat) as jumlah from kamar_inap inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar where kamar_inap.tgl_keluar='0000-00-00' and kamar.statusdata='1' group by kamar.kelas order by jumlah desc");
    $labelkelas = array();
    $datakelas  = array();
    while($rsquerykelas = mysqli_fetch_array($querykelas)) {
        $labelkelas[] = $rsquerykelas["kelas"];
        $datakelas[]  = $rsquerykelas["jumlah"];
    }

    $querykosongbangsal = bukaquery("select bangsal.nm_bangsal,count(kamar.kd_kamar) as jumlah from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.status='KOSONG' and kamar.statusdata='1' and bangsal.status='1' group by bangsal.kd_bangsal order by jumlah desc");
    $labelkosongbangsal = array();
    $datakosongbangsal  = array();
    while($rsquerykosongbangsal = mysqli_fetch_array($querykosongbangsal)) {
        $labelkosongbangsal[] = $rsquerykosongbangsal["nm_bangsal"];
        $datakosongbangsal[]  = $rsquerykosongbangsal["jumlah"];
    }

    $queryisibangsal = bukaquery("select bangsal.nm_bangsal,count(kamar.kd_kamar) as jumlah from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.status='ISI' and kamar.statusdata='1' and bangsal.status='1' group by bangsal.kd_bangsal order by jumlah desc");
    $labelisibangsal = array();
    $dataisibangsal  = array();
    while($rsqueryisibangsal = mysqli_fetch_array($queryisibangsal)) {
        $labelisibangsal[] = $rsqueryisibangsal["nm_bangsal"];
        $dataisibangsal[]  = $rsqueryisibangsal["jumlah"];
    }

    $querydibersihkanbangsal = bukaquery("select bangsal.nm_bangsal,count(kamar.kd_kamar) as jumlah from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.status='DIBERSIHKAN' and kamar.statusdata='1' and bangsal.status='1' group by bangsal.kd_bangsal order by jumlah desc");
    $labeldibersihkanbangsal = array();
    $datadibersihkanbangsal  = array();
    while($rsquerydibersihkanbangsal = mysqli_fetch_array($querydibersihkanbangsal)) {
        $labeldibersihkanbangsal[] = $rsquerydibersihkanbangsal["nm_bangsal"];
        $datadibersihkanbangsal[]  = $rsquerydibersihkanbangsal["jumlah"];
    }

    $querydibookingbangsal = bukaquery("select bangsal.nm_bangsal,count(kamar.kd_kamar) as jumlah from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.status='DIBOOKING' and kamar.statusdata='1' and bangsal.status='1' group by bangsal.kd_bangsal order by jumlah desc");
    $labeldibookingbangsal = array();
    $datadibookingbangsal  = array();
    while($rsquerydibookingbangsal = mysqli_fetch_array($querydibookingbangsal)) {
        $labeldibookingbangsal[] = $rsquerydibookingbangsal["nm_bangsal"];
        $datadibookingbangsal[]  = $rsquerydibookingbangsal["jumlah"];
    }

    $queryperbaikanbangsal = bukaquery("select bangsal.nm_bangsal,count(kamar.kd_kamar) as jumlah from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.status='PERBAIKAN' and kamar.statusdata='1' and bangsal.status='1' group by bangsal.kd_bangsal order by jumlah desc");
    $labelperbaikanbangsal = array();
    $dataperbaikanbangsal  = array();
    while($rsqueryperbaikanbangsal = mysqli_fetch_array($queryperbaikanbangsal)) {
        $labelperbaikanbangsal[] = $rsqueryperbaikanbangsal["nm_bangsal"];
        $dataperbaikanbangsal[]  = $rsqueryperbaikanbangsal["jumlah"];
    }

    $querykosongkelas = bukaquery("select kamar.kelas,count(kamar.kd_kamar) as jumlah from kamar where kamar.status='KOSONG' and kamar.statusdata='1' group by kamar.kelas order by jumlah desc");
    $labelkosongkelas = array();
    $datakosongkelas  = array();
    while($rsquerykosongkelas = mysqli_fetch_array($querykosongkelas)) {
        $labelkosongkelas[] = $rsquerykosongkelas["kelas"];
        $datakosongkelas[]  = $rsquerykosongkelas["jumlah"];
    }

    $queryisikelas = bukaquery("select kamar.kelas,count(kamar.kd_kamar) as jumlah from kamar where kamar.status='ISI' and kamar.statusdata='1' group by kamar.kelas order by jumlah desc");
    $labelisikelas = array();
    $dataisikelas  = array();
    while($rsqueryisikelas = mysqli_fetch_array($queryisikelas)) {
        $labelisikelas[] = $rsqueryisikelas["kelas"];
        $dataisikelas[]  = $rsqueryisikelas["jumlah"];
    }

    $querydibersihkankelas = bukaquery("select kamar.kelas,count(kamar.kd_kamar) as jumlah from kamar where kamar.status='DIBERSIHKAN' and kamar.statusdata='1' group by kamar.kelas order by jumlah desc");
    $labeldibersihkankelas = array();
    $datadibersihkankelas  = array();
    while($rsquerydibersihkankelas = mysqli_fetch_array($querydibersihkankelas)) {
        $labeldibersihkankelas[] = $rsquerydibersihkankelas["kelas"];
        $datadibersihkankelas[]  = $rsquerydibersihkankelas["jumlah"];
    }

    $querydibookingkelas = bukaquery("select kamar.kelas,count(kamar.kd_kamar) as jumlah from kamar where kamar.status='DIBOOKING' and kamar.statusdata='1' group by kamar.kelas order by jumlah desc");
    $labeldibookingkelas = array();
    $datadibookingkelas  = array();
    while($rsquerydibookingkelas = mysqli_fetch_array($querydibookingkelas)) {
        $labeldibookingkelas[] = $rsquerydibookingkelas["kelas"];
        $datadibookingkelas[]  = $rsquerydibookingkelas["jumlah"];
    }

    $queryperbaikankelas = bukaquery("select kamar.kelas,count(kamar.kd_kamar) as jumlah from kamar where kamar.status='PERBAIKAN' and kamar.statusdata='1' group by kamar.kelas order by jumlah desc");
    $labelperbaikankelas = array();
    $dataperbaikankelas  = array();
    while($rsqueryperbaikankelas = mysqli_fetch_array($queryperbaikankelas)) {
        $labelperbaikankelas[] = $rsqueryperbaikankelas["kelas"];
        $dataperbaikankelas[]  = $rsqueryperbaikankelas["jumlah"];
    }
?>
<div class="block-header">
    <h2><center>PENDAFTARAN HARI INI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-pink hover-expand-effect">
            <div class="icon">
                <i class="material-icons">enhanced_encryption</i>
            </div>
            <div class="content">
                <div class="text">KUNJUNGAN</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date()");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-cyan hover-expand-effect">
            <div class="icon">
                <i class="material-icons">airline_seat_recline_normal</i>
            </div>
            <div class="content">
                <div class="text">RAWAT JALAN & IGD</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.status_lanjut = 'Ralan'");?>" data-speed="1000" data-fresh-interval="20"></div>
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
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.status_lanjut = 'Ranap'");?>" data-speed="1000" data-fresh-interval="20"></div>
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
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts = 'Belum'");?>" data-speed="1000" data-fresh-interval="20"></div>
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
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts = 'Sudah'");?>" data-speed="1000" data-fresh-interval="20"></div>
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
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts = 'Batal'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
</div>
<?php if(count($datacarabayar) > 0 || count($datadokter) > 0 || count($datapoli) > 0): ?>
<div class="block-header">
    <h2><center>GRAFIK PENDAFTARAN HARI INI</center></h2>
</div>
<div class="row clearfix">
    <?php if(count($datacarabayar) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>PER CARA BAYAR</h2>
            </div>
            <div class="body">
                <canvas id="chartcarabayar" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($datadokter) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>PER DOKTER</h2>
            </div>
            <div class="body">
                <canvas id="chartdokter" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($datapoli) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>PER POLI</h2>
            </div>
            <div class="body">
                <canvas id="chartpoli" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
</div>
<?php endif; ?>
<div class="block-header">
    <h2><center>RAWAT INAP HARI INI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-indigo hover-expand-effect">
            <div class="icon">
                <i class="material-icons">local_hotel</i>
            </div>
            <div class="content">
                <div class="text">MASUK RANAP</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(kamar_inap.no_rawat) FROM kamar_inap WHERE kamar_inap.tgl_masuk = current_date() and kamar_inap.stts_pulang<>'Pindah Kamar'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-purple hover-expand-effect">
            <div class="icon">
                <i class="material-icons">compare_arrows</i>
            </div>
            <div class="content">
                <div class="text">PINDAH KAMAR</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(kamar_inap.no_rawat) FROM kamar_inap WHERE kamar_inap.tgl_keluar = current_date() AND kamar_inap.stts_pulang = 'Pindah Kamar'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-light-green hover-expand-effect">
            <div class="icon">
                <i class="material-icons">exit_to_app</i>
            </div>
            <div class="content">
                <div class="text">PULANG</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(kamar_inap.no_rawat) FROM kamar_inap WHERE kamar_inap.tgl_keluar = current_date() AND kamar_inap.stts_pulang NOT IN ('Meninggal','Pindah Kamar','+')");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-blue-grey hover-expand-effect">
            <div class="icon">
                <i class="material-icons">highlight_off</i>
            </div>
            <div class="content">
                <div class="text">MENINGGAL</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(kamar_inap.no_rawat) FROM kamar_inap WHERE kamar_inap.tgl_keluar = current_date() AND kamar_inap.stts_pulang = 'Meninggal'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-teal hover-expand-effect">
            <div class="icon">
                <i class="material-icons">airline_seat_recline_normal</i>
            </div>
            <div class="content">
                <div class="text">MASIH DIRAWAT</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(kamar_inap.no_rawat) FROM kamar_inap WHERE kamar_inap.tgl_keluar = '0000-00-00'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
</div>
<?php if(count($databangsal) > 0 || count($datakelas) > 0): ?>
<div class="block-header">
    <h2><center>GRAFIK RAWAT INAP HARI INI</center></h2>
</div>
<div class="row clearfix">
    <?php if(count($databangsal) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>PER BANGSAL</h2>
            </div>
            <div class="body">
                <canvas id="chartbangsal" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($datakelas) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>PER KELAS KAMAR</h2>
            </div>
            <div class="body">
                <canvas id="chartkelas" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
</div>
<?php endif; ?>
<?php if(count($datakosongbangsal) > 0 || count($dataisibangsal) > 0 || count($datadibersihkanbangsal) > 0 || count($datadibookingbangsal) > 0 || count($dataperbaikanbangsal) > 0): ?>
<div class="block-header">
    <h2><center>GRAFIK KETERSEDIAAN KAMAR PER BANGSAL</center></h2>
</div>
<div class="row clearfix">
    <?php if(count($datakosongbangsal) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR KOSONG PER BANGSAL</h2>
            </div>
            <div class="body">
                <canvas id="chartkosongbangsal" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($dataisibangsal) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR ISI PER BANGSAL</h2>
            </div>
            <div class="body">
                <canvas id="chartisibangsal" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($datadibersihkanbangsal) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR DIBERSIHKAN PER BANGSAL</h2>
            </div>
            <div class="body">
                <canvas id="chartdibersihkanbangsal" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($datadibookingbangsal) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR DIBOOKING PER BANGSAL</h2>
            </div>
            <div class="body">
                <canvas id="chartdibookingbangsal" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($dataperbaikanbangsal) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR PERBAIKAN PER BANGSAL</h2>
            </div>
            <div class="body">
                <canvas id="chartperbaikanbangsal" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
</div>
<?php endif; ?>
<?php if(count($datakosongkelas) > 0 || count($dataisikelas) > 0 || count($datadibersihkankelas) > 0 || count($datadibookingkelas) > 0 || count($dataperbaikankelas) > 0): ?>
<div class="block-header">
    <h2><center>GRAFIK KETERSEDIAAN KAMAR PER KELAS</center></h2>
</div>
<div class="row clearfix">
    <?php if(count($datakosongkelas) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR KOSONG PER KELAS</h2>
            </div>
            <div class="body">
                <canvas id="chartkosongkelas" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($dataisikelas) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR ISI PER KELAS</h2>
            </div>
            <div class="body">
                <canvas id="chartisikelas" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($datadibersihkankelas) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR DIBERSIHKAN PER KELAS</h2>
            </div>
            <div class="body">
                <canvas id="chartdibersihkankelas" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($datadibookingkelas) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR DIBOOKING PER KELAS</h2>
            </div>
            <div class="body">
                <canvas id="chartdibookingkelas" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
    <?php if(count($dataperbaikankelas) > 0): ?>
    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
        <div class="card">
            <div class="header">
                <h2>KAMAR PERBAIKAN PER KELAS</h2>
            </div>
            <div class="body">
                <canvas id="chartperbaikankelas" height="250"></canvas>
            </div>
        </div>
    </div>
    <?php endif; ?>
</div>
<?php endif; ?>
<script>
window.addEventListener('load', function() {
    if(document.getElementById('chartcarabayar')) {
        new Chart(document.getElementById('chartcarabayar').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelcarabayar);?>,
                datasets: [{
                    data: <?=json_encode($datacarabayar);?>,
                    backgroundColor: ['#e91e63','#00bcd4','#8bc34a','#ff9800','#9c27b0','#3f51b5','#009688','#795548','#607d8b','#f44336']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartdokter')) {
        new Chart(document.getElementById('chartdokter').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labeldokter);?>,
                datasets: [{
                    data: <?=json_encode($datadokter);?>,
                    backgroundColor: ['#3f51b5','#e91e63','#00bcd4','#8bc34a','#ff9800','#9c27b0','#009688','#795548','#607d8b','#f44336']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartpoli')) {
        new Chart(document.getElementById('chartpoli').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelpoli);?>,
                datasets: [{
                    data: <?=json_encode($datapoli);?>,
                    backgroundColor: ['#009688','#e91e63','#00bcd4','#8bc34a','#ff9800','#9c27b0','#3f51b5','#795548','#607d8b','#f44336']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartbangsal')) {
        new Chart(document.getElementById('chartbangsal').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelbangsal);?>,
                datasets: [{
                    data: <?=json_encode($databangsal);?>,
                    backgroundColor: ['#3f51b5','#e91e63','#00bcd4','#8bc34a','#ff9800','#9c27b0','#009688','#795548','#607d8b','#f44336']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartkelas')) {
        new Chart(document.getElementById('chartkelas').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelkelas);?>,
                datasets: [{
                    data: <?=json_encode($datakelas);?>,
                    backgroundColor: ['#ff9800','#3f51b5','#e91e63','#00bcd4','#8bc34a','#9c27b0','#009688','#795548','#607d8b','#f44336']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartkosongbangsal')) {
        new Chart(document.getElementById('chartkosongbangsal').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelkosongbangsal);?>,
                datasets: [{
                    data: <?=json_encode($datakosongbangsal);?>,
                    backgroundColor: ['#4caf50','#8bc34a','#009688','#00bcd4','#3f51b5','#9c27b0','#e91e63','#ff9800','#795548','#607d8b']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartisibangsal')) {
        new Chart(document.getElementById('chartisibangsal').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelisibangsal);?>,
                datasets: [{
                    data: <?=json_encode($dataisibangsal);?>,
                    backgroundColor: ['#f44336','#ff9800','#e91e63','#9c27b0','#3f51b5','#00bcd4','#009688','#8bc34a','#795548','#607d8b']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartdibersihkanbangsal')) {
        new Chart(document.getElementById('chartdibersihkanbangsal').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labeldibersihkanbangsal);?>,
                datasets: [{
                    data: <?=json_encode($datadibersihkanbangsal);?>,
                    backgroundColor: ['#00bcd4','#3f51b5','#e91e63','#8bc34a','#ff9800','#9c27b0','#009688','#795548','#607d8b','#f44336']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartdibookingbangsal')) {
        new Chart(document.getElementById('chartdibookingbangsal').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labeldibookingbangsal);?>,
                datasets: [{
                    data: <?=json_encode($datadibookingbangsal);?>,
                    backgroundColor: ['#ff9800','#3f51b5','#e91e63','#8bc34a','#00bcd4','#9c27b0','#009688','#795548','#607d8b','#f44336']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartperbaikanbangsal')) {
        new Chart(document.getElementById('chartperbaikanbangsal').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelperbaikanbangsal);?>,
                datasets: [{
                    data: <?=json_encode($dataperbaikanbangsal);?>,
                    backgroundColor: ['#607d8b','#3f51b5','#e91e63','#8bc34a','#ff9800','#9c27b0','#009688','#795548','#00bcd4','#f44336']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartkosongkelas')) {
        new Chart(document.getElementById('chartkosongkelas').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelkosongkelas);?>,
                datasets: [{
                    data: <?=json_encode($datakosongkelas);?>,
                    backgroundColor: ['#4caf50','#8bc34a','#009688','#00bcd4','#3f51b5','#9c27b0']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartisikelas')) {
        new Chart(document.getElementById('chartisikelas').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelisikelas);?>,
                datasets: [{
                    data: <?=json_encode($dataisikelas);?>,
                    backgroundColor: ['#f44336','#ff9800','#e91e63','#9c27b0','#3f51b5','#00bcd4']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartdibersihkankelas')) {
        new Chart(document.getElementById('chartdibersihkankelas').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labeldibersihkankelas);?>,
                datasets: [{
                    data: <?=json_encode($datadibersihkankelas);?>,
                    backgroundColor: ['#00bcd4','#3f51b5','#e91e63','#8bc34a','#ff9800','#9c27b0']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartdibookingkelas')) {
        new Chart(document.getElementById('chartdibookingkelas').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labeldibookingkelas);?>,
                datasets: [{
                    data: <?=json_encode($datadibookingkelas);?>,
                    backgroundColor: ['#ff9800','#3f51b5','#e91e63','#8bc34a','#00bcd4','#9c27b0']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
    if(document.getElementById('chartperbaikankelas')) {
        new Chart(document.getElementById('chartperbaikankelas').getContext('2d'), {
            type: 'pie',
            data: {
                labels: <?=json_encode($labelperbaikankelas);?>,
                datasets: [{
                    data: <?=json_encode($dataperbaikankelas);?>,
                    backgroundColor: ['#607d8b','#3f51b5','#e91e63','#8bc34a','#ff9800','#9c27b0']
                }]
            },
            options: { legend: { position: 'bottom' } }
        });
    }
});
</script>