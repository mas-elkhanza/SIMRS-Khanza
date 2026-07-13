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
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-deep-orange hover-expand-effect">
            <div class="icon">
                <i class="material-icons">send</i>
            </div>
            <div class="content">
                <div class="text">DIRUJUK</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts = 'Dirujuk'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-grey hover-expand-effect">
            <div class="icon">
                <i class="material-icons">warning</i>
            </div>
            <div class="content">
                <div class="text">PULANG PAKSA</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rkm_medis) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts = 'Pulang Paksa'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-deep-purple hover-expand-effect">
            <div class="icon">
                <i class="material-icons">history</i>
            </div>
            <div class="content">
                <div class="text">DAFTAR LAMA</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rawat) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts_daftar='Lama'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-amber hover-expand-effect">
            <div class="icon">
                <i class="material-icons">fiber_new</i>
            </div>
            <div class="content">
                <div class="text">DAFTAR BARU</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rawat) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.stts_daftar='Baru'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-brown hover-expand-effect">
            <div class="icon">
                <i class="material-icons">assignment_turned_in</i>
            </div>
            <div class="content">
                <div class="text">POLI LAMA</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rawat) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.status_poli='Lama'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-blue hover-expand-effect">
            <div class="icon">
                <i class="material-icons">assignment</i>
            </div>
            <div class="content">
                <div class="text">POLI BARU</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rawat) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.status_poli='Baru'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-green hover-expand-effect">
            <div class="icon">
                <i class="material-icons">check_circle</i>
            </div>
            <div class="content">
                <div class="text">SUDAH BAYAR</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rawat) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.status_lanjut='Ralan' AND reg_periksa.status_bayar='Sudah Bayar'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-red hover-expand-effect">
            <div class="icon">
                <i class="material-icons">money_off</i>
            </div>
            <div class="content">
                <div class="text">BELUM BAYAR</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(reg_periksa.no_rawat) FROM reg_periksa WHERE reg_periksa.tgl_registrasi=current_date() AND reg_periksa.status_lanjut='Ralan' AND reg_periksa.status_bayar='Belum Bayar'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
</div>
<div class="block-header">
    <h2><center>GRAFIK PENDAFTARAN HARI INI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-md-6">
        <?php
            $dataCaraBayar = [];
            $query = bukaquery("SELECT penjab.png_jawab, COUNT(reg_periksa.no_rawat) AS jumlah FROM reg_periksa INNER JOIN penjab ON reg_periksa.kd_pj=penjab.kd_pj WHERE reg_periksa.tgl_registrasi=current_date() GROUP BY penjab.kd_pj");
            while ($row = mysqli_fetch_array($query)) {
                $dataCaraBayar[] = [
                    'label' => $row['png_jawab']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Per Cara Bayar</div>
                </div>
                <div class="body">
                    <div id="pie_chart_carabayar" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataDokter = [];
            $query = bukaquery("SELECT dokter.nm_dokter, COUNT(reg_periksa.no_rawat) AS jumlah FROM reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter WHERE reg_periksa.tgl_registrasi=current_date() GROUP BY dokter.kd_dokter");
            while ($row = mysqli_fetch_array($query)) {
                $dataDokter[] = [
                    'label' => $row['nm_dokter']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Per Dokter</div>
                </div>
                <div class="body">
                    <div id="pie_chart_dokter" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataPoli = [];
            $query = bukaquery("SELECT poliklinik.nm_poli, COUNT(reg_periksa.no_rawat) AS jumlah FROM reg_periksa INNER JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE reg_periksa.tgl_registrasi=current_date() GROUP BY poliklinik.kd_poli");
            while ($row = mysqli_fetch_array($query)) {
                $dataPoli[] = [
                    'label' => $row['nm_poli']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Per Poli</div>
                </div>
                <div class="body">
                    <div id="pie_chart_poli" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>
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
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(kamar_inap.no_rawat) FROM kamar_inap WHERE kamar_inap.tgl_keluar = current_date() AND kamar_inap.stts_pulang NOT IN ('Meninggal','Pindah Kamar','+','Rujuk','Pulang Paksa','APS','Atas Permintaan Sendiri')");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-deep-orange hover-expand-effect">
            <div class="icon">
                <i class="material-icons">send</i>
            </div>
            <div class="content">
                <div class="text">DIRUJUK</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(kamar_inap.no_rawat) FROM kamar_inap WHERE kamar_inap.tgl_keluar = current_date() AND kamar_inap.stts_pulang = 'Rujuk'");?>" data-speed="1000" data-fresh-interval="20"></div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-brown hover-expand-effect">
            <div class="icon">
                <i class="material-icons">warning</i>
            </div>
            <div class="content">
                <div class="text">PULANG PAKSA</div>
                <div class="number count-to" data-from="0" data-to="<?=getOne("SELECT count(kamar_inap.no_rawat) FROM kamar_inap WHERE kamar_inap.tgl_keluar = current_date() AND kamar_inap.stts_pulang IN ('Pulang Paksa','Atas Permintaan Sendiri','APS')");?>" data-speed="1000" data-fresh-interval="20"></div>
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
    <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
        <div class="info-box bg-cyan hover-expand-effect">
            <div class="icon">
                <i class="material-icons">donut_large</i>
            </div>
            <div class="content">
                <div class="text">TINGKAT OKUPANSI</div>
                <div class="number"><?=getOne("SELECT ROUND((SELECT COUNT(kamar.kd_kamar) FROM kamar WHERE kamar.status='ISI' AND kamar.statusdata='1') / NULLIF((SELECT COUNT(kamar.kd_kamar) FROM kamar WHERE kamar.statusdata='1'),0) * 100, 2)");?>%</div>
            </div>
        </div>
    </div>
</div>
<div class="block-header">
    <h2><center>GRAFIK RAWAT INAP HARI INI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-md-6">
        <?php
            $dataBangsal = [];
            $query = bukaquery("SELECT bangsal.nm_bangsal, COUNT(kamar_inap.no_rawat) AS jumlah FROM kamar_inap INNER JOIN kamar ON kamar_inap.kd_kamar=kamar.kd_kamar INNER JOIN bangsal ON kamar.kd_bangsal=bangsal.kd_bangsal WHERE kamar_inap.tgl_keluar='0000-00-00' AND kamar.statusdata='1' AND bangsal.status='1' GROUP BY bangsal.kd_bangsal");
            while ($row = mysqli_fetch_array($query)) {
                $dataBangsal[] = [
                    'label' => $row['nm_bangsal']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Per Bangsal</div>
                </div>
                <div class="body">
                    <div id="pie_chart_bangsal" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataKelas = [];
            $query = bukaquery("SELECT kamar.kelas, COUNT(kamar_inap.no_rawat) AS jumlah FROM kamar_inap INNER JOIN kamar ON kamar_inap.kd_kamar=kamar.kd_kamar WHERE kamar_inap.tgl_keluar='0000-00-00' AND kamar.statusdata='1' GROUP BY kamar.kelas");
            while ($row = mysqli_fetch_array($query)) {
                $dataKelas[] = [
                    'label' => $row['kelas']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Per Kelas Kamar</div>
                </div>
                <div class="body">
                    <div id="pie_chart_kelas" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataAsalPoli = [];
            $query = bukaquery("SELECT poliklinik.nm_poli, COUNT(kamar_inap.no_rawat) AS jumlah FROM kamar_inap INNER JOIN reg_periksa ON kamar_inap.no_rawat=reg_periksa.no_rawat INNER JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli WHERE kamar_inap.tgl_keluar='0000-00-00' GROUP BY poliklinik.kd_poli");
            while ($row = mysqli_fetch_array($query)) {
                $dataAsalPoli[] = [
                    'label' => $row['nm_poli']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Asal Poli Pasien</div>
                </div>
                <div class="body">
                    <div id="pie_chart_asalpoli" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataAsalDokter = [];
            $query = bukaquery("SELECT dokter.nm_dokter, COUNT(kamar_inap.no_rawat) AS jumlah FROM kamar_inap INNER JOIN reg_periksa ON kamar_inap.no_rawat=reg_periksa.no_rawat INNER JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter WHERE kamar_inap.tgl_keluar='0000-00-00' GROUP BY dokter.kd_dokter");
            while ($row = mysqli_fetch_array($query)) {
                $dataAsalDokter[] = [
                    'label' => $row['nm_dokter']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Asal Dokter Pasien</div>
                </div>
                <div class="body">
                    <div id="pie_chart_asaldokter" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="block-header">
    <h2><center>GRAFIK KETERSEDIAAN KAMAR PER BANGSAL</center></h2>
</div>
<div class="row clearfix">
    <div class="col-md-6">
        <?php
            $dataKosongBangsal = [];
            $query = bukaquery("SELECT bangsal.nm_bangsal, COUNT(kamar.kd_kamar) AS jumlah FROM kamar INNER JOIN bangsal ON kamar.kd_bangsal=bangsal.kd_bangsal WHERE kamar.status='KOSONG' AND kamar.statusdata='1' AND bangsal.status='1' GROUP BY bangsal.kd_bangsal");
            while ($row = mysqli_fetch_array($query)) {
                $dataKosongBangsal[] = [
                    'label' => $row['nm_bangsal']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Kosong Per Bangsal</div>
                </div>
                <div class="body">
                    <div id="pie_chart_kosongbangsal" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataIsiBangsal = [];
            $query = bukaquery("SELECT bangsal.nm_bangsal, COUNT(kamar.kd_kamar) AS jumlah FROM kamar INNER JOIN bangsal ON kamar.kd_bangsal=bangsal.kd_bangsal WHERE kamar.status='ISI' AND kamar.statusdata='1' AND bangsal.status='1' GROUP BY bangsal.kd_bangsal");
            while ($row = mysqli_fetch_array($query)) {
                $dataIsiBangsal[] = [
                    'label' => $row['nm_bangsal']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Isi Per Bangsal</div>
                </div>
                <div class="body">
                    <div id="pie_chart_isibangsal" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataDibersihkanBangsal = [];
            $query = bukaquery("SELECT bangsal.nm_bangsal, COUNT(kamar.kd_kamar) AS jumlah FROM kamar INNER JOIN bangsal ON kamar.kd_bangsal=bangsal.kd_bangsal WHERE kamar.status='DIBERSIHKAN' AND kamar.statusdata='1' AND bangsal.status='1' GROUP BY bangsal.kd_bangsal");
            while ($row = mysqli_fetch_array($query)) {
                $dataDibersihkanBangsal[] = [
                    'label' => $row['nm_bangsal']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Dibersihkan Per Bangsal</div>
                </div>
                <div class="body">
                    <div id="pie_chart_dibersihkanbangsal" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataDibookingBangsal = [];
            $query = bukaquery("SELECT bangsal.nm_bangsal, COUNT(kamar.kd_kamar) AS jumlah FROM kamar INNER JOIN bangsal ON kamar.kd_bangsal=bangsal.kd_bangsal WHERE kamar.status='DIBOOKING' AND kamar.statusdata='1' AND bangsal.status='1' GROUP BY bangsal.kd_bangsal");
            while ($row = mysqli_fetch_array($query)) {
                $dataDibookingBangsal[] = [
                    'label' => $row['nm_bangsal']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Dibooking Per Bangsal</div>
                </div>
                <div class="body">
                    <div id="pie_chart_dibookingbangsal" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataPerbaikanBangsal = [];
            $query = bukaquery("SELECT bangsal.nm_bangsal, COUNT(kamar.kd_kamar) AS jumlah FROM kamar INNER JOIN bangsal ON kamar.kd_bangsal=bangsal.kd_bangsal WHERE kamar.status='PERBAIKAN' AND kamar.statusdata='1' AND bangsal.status='1' GROUP BY bangsal.kd_bangsal");
            while ($row = mysqli_fetch_array($query)) {
                $dataPerbaikanBangsal[] = [
                    'label' => $row['nm_bangsal']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Perbaikan Per Bangsal</div>
                </div>
                <div class="body">
                    <div id="pie_chart_perbaikanbangsal" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="block-header">
    <h2><center>GRAFIK KETERSEDIAAN KAMAR PER KELAS</center></h2>
</div>
<div class="row clearfix">
    <div class="col-md-6">
        <?php
            $dataKosongKelas = [];
            $query = bukaquery("SELECT kamar.kelas, COUNT(kamar.kd_kamar) AS jumlah FROM kamar WHERE kamar.status='KOSONG' AND kamar.statusdata='1' GROUP BY kamar.kelas");
            while ($row = mysqli_fetch_array($query)) {
                $dataKosongKelas[] = [
                    'label' => $row['kelas']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Kosong Per Kelas</div>
                </div>
                <div class="body">
                    <div id="pie_chart_kosongkelas" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataIsiKelas = [];
            $query = bukaquery("SELECT kamar.kelas, COUNT(kamar.kd_kamar) AS jumlah FROM kamar WHERE kamar.status='ISI' AND kamar.statusdata='1' GROUP BY kamar.kelas");
            while ($row = mysqli_fetch_array($query)) {
                $dataIsiKelas[] = [
                    'label' => $row['kelas']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Isi Per Kelas</div>
                </div>
                <div class="body">
                    <div id="pie_chart_isikelas" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataDibersihkanKelas = [];
            $query = bukaquery("SELECT kamar.kelas, COUNT(kamar.kd_kamar) AS jumlah FROM kamar WHERE kamar.status='DIBERSIHKAN' AND kamar.statusdata='1' GROUP BY kamar.kelas");
            while ($row = mysqli_fetch_array($query)) {
                $dataDibersihkanKelas[] = [
                    'label' => $row['kelas']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Dibersihkan Per Kelas</div>
                </div>
                <div class="body">
                    <div id="pie_chart_dibersihkankelas" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataDibookingKelas = [];
            $query = bukaquery("SELECT kamar.kelas, COUNT(kamar.kd_kamar) AS jumlah FROM kamar WHERE kamar.status='DIBOOKING' AND kamar.statusdata='1' GROUP BY kamar.kelas");
            while ($row = mysqli_fetch_array($query)) {
                $dataDibookingKelas[] = [
                    'label' => $row['kelas']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Dibooking Per Kelas</div>
                </div>
                <div class="body">
                    <div id="pie_chart_dibookingkelas" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <?php
            $dataPerbaikanKelas = [];
            $query = bukaquery("SELECT kamar.kelas, COUNT(kamar.kd_kamar) AS jumlah FROM kamar WHERE kamar.status='PERBAIKAN' AND kamar.statusdata='1' GROUP BY kamar.kelas");
            while ($row = mysqli_fetch_array($query)) {
                $dataPerbaikanKelas[] = [
                    'label' => $row['kelas']." (".$row['jumlah'].")",
                    'data'  => (int)$row['jumlah']
                ];
            }
        ?>
        <div class="container-fluid">
            <div class="card">
                <div class="header bg-white">
                    <div class="text-center">Kamar Perbaikan Per Kelas</div>
                </div>
                <div class="body">
                    <div id="pie_chart_perbaikankelas" class="flot-chart" style="height: 400px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- JS -->
<script src="plugins/jquery/jquery.min.js"></script>
<script src="plugins/flot-charts/jquery.flot.js"></script>
<script src="plugins/flot-charts/jquery.flot.resize.js"></script>
<script src="plugins/flot-charts/jquery.flot.pie.js"></script>
<script>
$(function() {
    var dataCaraBayar = <?= json_encode($dataCaraBayar) ?>;
    if (dataCaraBayar.length > 0) {
        $.plot("#pie_chart_carabayar", dataCaraBayar, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_carabayar").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataDokter = <?= json_encode($dataDokter) ?>;
    if (dataDokter.length > 0) {
        $.plot("#pie_chart_dokter", dataDokter, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_dokter").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataPoli = <?= json_encode($dataPoli) ?>;
    if (dataPoli.length > 0) {
        $.plot("#pie_chart_poli", dataPoli, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_poli").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataBangsal = <?= json_encode($dataBangsal) ?>;
    if (dataBangsal.length > 0) {
        $.plot("#pie_chart_bangsal", dataBangsal, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_bangsal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataKelas = <?= json_encode($dataKelas) ?>;
    if (dataKelas.length > 0) {
        $.plot("#pie_chart_kelas", dataKelas, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_kelas").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataAsalPoli = <?= json_encode($dataAsalPoli) ?>;
    if (dataAsalPoli.length > 0) {
        $.plot("#pie_chart_asalpoli", dataAsalPoli, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_asalpoli").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataAsalDokter = <?= json_encode($dataAsalDokter) ?>;
    if (dataAsalDokter.length > 0) {
        $.plot("#pie_chart_asaldokter", dataAsalDokter, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_asaldokter").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataKosongBangsal = <?= json_encode($dataKosongBangsal) ?>;
    if (dataKosongBangsal.length > 0) {
        $.plot("#pie_chart_kosongbangsal", dataKosongBangsal, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_kosongbangsal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataIsiBangsal = <?= json_encode($dataIsiBangsal) ?>;
    if (dataIsiBangsal.length > 0) {
        $.plot("#pie_chart_isibangsal", dataIsiBangsal, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_isibangsal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataDibersihkanBangsal = <?= json_encode($dataDibersihkanBangsal) ?>;
    if (dataDibersihkanBangsal.length > 0) {
        $.plot("#pie_chart_dibersihkanbangsal", dataDibersihkanBangsal, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_dibersihkanbangsal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataDibookingBangsal = <?= json_encode($dataDibookingBangsal) ?>;
    if (dataDibookingBangsal.length > 0) {
        $.plot("#pie_chart_dibookingbangsal", dataDibookingBangsal, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_dibookingbangsal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataPerbaikanBangsal = <?= json_encode($dataPerbaikanBangsal) ?>;
    if (dataPerbaikanBangsal.length > 0) {
        $.plot("#pie_chart_perbaikanbangsal", dataPerbaikanBangsal, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_perbaikanbangsal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataKosongKelas = <?= json_encode($dataKosongKelas) ?>;
    if (dataKosongKelas.length > 0) {
        $.plot("#pie_chart_kosongkelas", dataKosongKelas, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_kosongkelas").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataIsiKelas = <?= json_encode($dataIsiKelas) ?>;
    if (dataIsiKelas.length > 0) {
        $.plot("#pie_chart_isikelas", dataIsiKelas, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_isikelas").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataDibersihkanKelas = <?= json_encode($dataDibersihkanKelas) ?>;
    if (dataDibersihkanKelas.length > 0) {
        $.plot("#pie_chart_dibersihkankelas", dataDibersihkanKelas, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_dibersihkankelas").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataDibookingKelas = <?= json_encode($dataDibookingKelas) ?>;
    if (dataDibookingKelas.length > 0) {
        $.plot("#pie_chart_dibookingkelas", dataDibookingKelas, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_dibookingkelas").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    var dataPerbaikanKelas = <?= json_encode($dataPerbaikanKelas) ?>;
    if (dataPerbaikanKelas.length > 0) {
        $.plot("#pie_chart_perbaikankelas", dataPerbaikanKelas, {
            series: {
                pie: {
                    show: true,
                    radius: 1,
                    label: {
                        show: true,
                        radius: 0.75,
                        formatter: function(label, series) {
                            return '<div style="font-size:12px;text-align:center;padding:2px;color:white;">'
                                + label + '<br/>' + Math.round(series.percent) + '%</div>';
                        },
                        background: { opacity: 0.6 }
                    }
                }
            },
            legend: { show: true }
        });
    } else {
        $("#pie_chart_perbaikankelas").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>