<?php
if (strpos($_SERVER['REQUEST_URI'], "pages")) {
    exit(header("Location:../index.php"));
}
?>
<div id="post">
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <div class="entry">
            <?php
            echo "";
            $codernik       = validTeks(isset($_GET['codernik']) ? $_GET['codernik'] : NULL);
            $norawat        = validTeks(isset($_GET['norawat']) ? $_GET['norawat'] : NULL);
            $carabayar      = validTeks(str_replace("_", " ", isset($_GET['carabayar'])) ? str_replace("_", " ", $_GET['carabayar']) : NULL);
            $_sql         = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                            reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,
                            pasien.umur,pasien.tgl_lahir,poliklinik.nm_poli,reg_periksa.status_lanjut,reg_periksa.umurdaftar,reg_periksa.sttsumur,
                            reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
                            from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab 
                            on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                            and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='$norawat' and reg_periksa.status_bayar='Sudah Bayar' ";
            $hasil        = bukaquery($_sql);
            $baris        = mysqli_fetch_array($hasil);
            $no_rkm_medis = $baris["no_rkm_medis"];
            $nm_pasien    = $baris["nm_pasien"];
            $umurdaftar   = $baris["umurdaftar"];
            $sttsumur     = $baris["sttsumur"];
            $tgl_lahir    = $baris["tgl_lahir"];
            $jk           = $baris["jk"];
            $almt_pj      = $baris["almt_pj"];
            $norawat      = $baris["no_rawat"];
            $tgl_registrasi = $baris["tgl_registrasi"];
            $jam_reg      = $baris["jam_reg"];
            $nm_poli      = $baris["nm_poli"];
            $nm_dokter2 = "";
            $a = 1;
            $hasildokter = bukaquery("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='" . $baris["no_rawat"] . "'");
            while ($barisdokter = mysqli_fetch_array($hasildokter)) {
                if ($a == 1) {
                    $nm_dokter2 = $barisdokter["nm_dokter"];
                } else {
                    $nm_dokter2 = $nm_dokter2 . "#" . $barisdokter["nm_dokter"];
                }
                $a++;
            }

            $nm_dokter    = "";
            if (!empty($nm_dokter2)) {
                $nm_dokter = $nm_dokter2;
            } else {
                $nm_dokter = $baris["nm_dokter"];
            }
            $status_lanjut  = $baris["status_lanjut"];
            $png_jawab    = $baris["png_jawab"];
            $jnsrawat = "1";
            if ($status_lanjut == "Ranap") {
                $jnsrawat = "1";
            } else {
                $jnsrawat = "2";
            }

            $nosep = "";
            $nosep = getOne("select bridging_sep.no_sep from bridging_sep where no_rawat='$norawat' and jnspelayanan='$jnsrawat' order by MAX(CONVERT(RIGHT(bridging_sep.no_sep,6),signed)) desc limit 1");
            if (empty($nosep)) {
                $nosep = getOne("select bridging_sep_internal.no_sep from bridging_sep_internal where no_rawat='$norawat' order by MAX(CONVERT(RIGHT(bridging_sep_internal.no_sep,6),signed)) desc limit 1");
            }

            $naikkelas = getOne("select bridging_sep.klsnaik from bridging_sep where bridging_sep.no_sep='$nosep'");

            $hakkelasrawat = getOne("select bridging_sep.klsrawat from bridging_sep where bridging_sep.no_sep='$nosep'");
            if (empty($naikkelas)) {
                $naikkelas = getOne("select bridging_sep_internal.klsnaik from bridging_sep_internal where bridging_sep_internal.no_rawat='$norawat'");
            }

            $upgrade_class_ind = "0";
            if (!empty($naikkelas)) {
                $upgrade_class_ind = "1";
                if ($naikkelas == "1") {
                    $naikkelas = "Kelas VVIP";
                } else if ($naikkelas == "2") {
                    $naikkelas = "Kelas VIP";
                } else if ($naikkelas == "3") {
                    $naikkelas = "Kelas 1";
                } else if ($naikkelas == "4") {
                    $naikkelas = "Kelas 2";
                } else {
                    $naikkelas = "";
                }
            } else {
                $naikkelas = "";
            }

            $upgrade_class_payor = getOne("select bridging_sep.pembiayaan from bridging_sep where bridging_sep.no_sep='" . $nosep . "' and bridging_sep.no_rawat='" . $norawat . "' LIMIT 1 ");
            if ($upgrade_class_payor == "1") {
                $upgrade_class_payor == "peserta";
            } elseif ($upgrade_class_payor == "2") {
                $upgrade_class_payor == "pemberi_kerja";
            } elseif ($upgrade_class_payor == "3") {
                $upgrade_class_payor == "asuransi_tambahan";
            } else {
                $upgrade_class_payor == "";
            }

            $sistolediastole = getOne("select pemeriksaan_ralan.tensi from pemeriksaan_ralan where pemeriksaan_ralan.tensi!='' and pemeriksaan_ralan.no_rawat='" . $norawat . "' LIMIT 1 ");
            $tgl_registrasi = getOne("select bridging_sep.tglsep from bridging_sep where bridging_sep.no_sep='$nosep'");
            echo "<input type=hidden name=no_rawat value='$norawat'>
                  <input type=hidden name=tgl_registrasi value='$tgl_registrasi'>
                  <input type=hidden name=tgl_lahir value='$tgl_lahir'>
                  <input type=hidden name=nm_pasien value='$nm_pasien'>
                  <input type=hidden name=jnsrawat value='$jnsrawat'>
                  <input type=hidden name=jk value='$jk'>
                  <input type=hidden name=codernik value='$codernik'>";
            ?>
            <?php
            if (getOne("select count(inacbg_grouping_stage12.no_sep) from inacbg_grouping_stage12 where inacbg_grouping_stage12.no_sep='" . $nosep . "'") > 0) { ?>
                <table class="table table-bordered table-hover table-responsive" width="100%" align="center">

                    <tr class="head">
                        <td width="25%">No SEP</td>
                        <td width="">:</td>
                        <td width="75%"><?php echo getOne("select inacbg_grouping_stage12.no_sep from inacbg_grouping_stage12 where inacbg_grouping_stage12.no_sep='" . $nosep . "'"); ?></td>
                    </tr>
                    <tr class="head">
                        <td width="25%">Code CBG</td>
                        <td width="">:</td>
                        <td width="75%"><?php echo getOne("select inacbg_grouping_stage12.code_cbg from inacbg_grouping_stage12 where inacbg_grouping_stage12.no_sep='" . $nosep . "'"); ?></td>
                    </tr>
                    <tr class="head">
                        <td width="25%">DESKRIPSI</td>
                        <td width="">:</td>
                        <td width="75%"><?php echo getOne("select inacbg_grouping_stage12.deskripsi from inacbg_grouping_stage12 where inacbg_grouping_stage12.no_sep='" . $nosep . "'"); ?></td>
                    </tr>
                    <tr class="head">
                        <td width="25%">TARIF CBG</td>
                        <td width="">:</td>
                        <td width="75%"><?php echo getOne("select inacbg_grouping_stage12.tarif from inacbg_grouping_stage12 where inacbg_grouping_stage12.no_sep='" . $nosep . "'"); ?></td>
                    </tr>
                    <tr>
                        <h3>Lakukan Finalisasi di INACBG langsung</h3>
                    </tr>
                </table>
                <?php } else {
                if (getOne("select count(inacbg_data_terkirim2.no_sep) from inacbg_data_terkirim2 where inacbg_data_terkirim2.no_sep='" . $nosep . "'") > 0) { ?>
                    <div align="center"><input name=BtnGrouping type=submit class="btn btn-success" value="&nbsp;&nbsp;Grouping&nbsp;&nbsp;"></div>
                    <hr>
                <?php } else { ?>
                    <div style="width: 100%; height: 87%; overflow: auto;">
                        <table class="table table-bordered table-hover table-responsive" width="100%" align="center">
                            <tr class="head">
                                <td width="25%">Petugas Coder</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $codernik; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="25%">No.Rawat</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $norawat; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="25%">No.RM</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $no_rkm_medis; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="25%">Nama</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $nm_pasien . ", " . $umurdaftar . " " . $sttsumur; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="25%">JK</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $jk; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="25%">Alamat</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $almt_pj; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="25%">Tgl.SEP</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $tgl_registrasi . " " . $jam_reg; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="25%">Poliklinik</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $nm_poli; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="25%">Dokter</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $nm_dokter; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="25%">Status</td>
                                <td width="">:</td>
                                <td width="75%"><?php echo $status_lanjut . " (" . $png_jawab . ")"; ?></td>
                            </tr>
                            <tr class="head">
                                <td width="41%">No.SEP</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="nosep" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $nosep; ?>" size="40" maxlength="40" pattern="[A-Z0-9-]{1,40}" title=" A-Z0-9- (Maksimal 40 karakter)" autocomplete="off" required>
                                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">No.Kartu</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="nokartu" class="text" type=text class="inputbox" value="<?php echo getOne("select no_kartu from bridging_sep where no_rawat='$norawat'"); ?>" size="40" maxlength="40" pattern="[A-Z0-9-]{1,40}" title=" A-Z0-9- (Maksimal 40 karakter)" autocomplete="off" required>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Tgl.Keluar</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="keluar" class="text" type=text class="inputbox" value="<?php if ($status_lanjut == "Ralan") {
                                                                                                            echo $tgl_registrasi;
                                                                                                        } else {
                                                                                                            echo getOne("select kamar_inap.tgl_keluar from kamar_inap where kamar_inap.no_rawat='" . $norawat . "'order by kamar_inap.tgl_keluar desc limit 1");
                                                                                                        }
                                                                                                        ?>" size="15" maxlength="10">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Kelas Rawat </td>
                                <td width="">:</td>
                                <td width="57%">
                                    <select name="kelas_rawat" class="text3">
                                        <?php if ($status_lanjut == "Ralan") {

                                            echo "<option value='3'>Kelas Reguler</option>";
                                        } else {
                                            if ($hakkelasrawat == "1") {

                                                echo  " <option value='1'>Kelas 1</option>";
                                            } else if ($hakkelasrawat == "2") {
                                                echo "<option value='2'>Kelas 2</option>";
                                            } else if ($hakkelasrawat == "3") {
                                                echo "<option value='3'>Kelas 3</option>";
                                            }
                                        }
                                        ?>
                                    </select>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Cara Masuk </td>
                                <td width="">:</td>
                                <td width="57%">
                                    <select name="cara_masuk" class="text3">
                                        <?php if ($status_lanjut == "Ralan") {
                                            $tujuankunjungan = getOne("select bridging_sep.tujuankunjungan from bridging_sep where bridging_sep.no_sep='$nosep'");
                                            $asspel = getOne("select bridging_sep.asesmenpelayanan from bridging_sep where bridging_sep.no_sep='$nosep'");
                                            $kodepolitujuan = getOne("select bridging_sep. kdpolitujuan from bridging_sep where bridging_sep.no_sep='$nosep'");
                                            if ($tujuankunjungan == '0' && $asspel == '') {
                                                echo  " <option value='gp'>Rujukan FKTP</option>";
                                            } else if ($tujuankunjungan == '2' && $asspel == '5') {
                                                echo  " <option value='outp'>Dari Rawat Jalan</option>";
                                            } else if ($tujuankunjungan == '0' && $asspel == '5') {
                                                echo  " <option value='mp'>Rujukan Dokter Spesialis</option>";
                                            } else if ($tujuankunjungan == '0' && $kodepolitujuan == 'IGD') {
                                                echo  " <option value='emd'>Dari IGD</option>";
                                            } else {
                                                echo  " <option value='hosp-trans'>Rujukan FKRTL</option>";
                                                echo  " <option value='inp'>Dari Rawat Inap</option>";
                                                echo  " <option value='born'>Lahir di RS</option>";
                                                echo  " <option value='other'>Lainnya</option>";
                                            }
                                        } else {
                                            $tujuankunjungan = getOne("select bridging_sep.tujuankunjungan from bridging_sep where bridging_sep.no_sep='$nosep'");
                                            $asspel = getOne("select bridging_sep.asesmenpelayanan from bridging_sep where bridging_sep.no_sep='$nosep'");
                                            $kodepolitujuan = getOne("select bridging_sep. kdpolitujuan from bridging_sep where bridging_sep.no_sep='$nosep'");
                                            if ($tujuankunjungan == '0' && $kodepolitujuan == 'IGD') {
                                                echo  " <option value='emd'>Dari IGD</option>";
                                            } else {
                                                echo  " <option value='hosp-trans'>Rujukan FKRTL</option>";
                                                echo  " <option value='mp'>Rujukan Dokter Spesialis</option>";
                                                echo  " <option value='outp'>Dari Rawat Jalan</option>";
                                                echo  " <option value='emd'>Dari IGD</option>";
                                                echo  " <option value='born'>Lahir di RS</option>";
                                                echo  " <option value='other'>Lainnya</option>";
                                            }
                                        }
                                        ?>
                                    </select>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">ADL Sub Acute</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <select name="adl_sub_acute" class="text3">
                                        <option value=""></option>
                                        <?php
                                        for ($i = 12; $i <= 60; $i++) {
                                            echo "<option value=$i>$i</option>";
                                        }
                                        ?>
                                    </select>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">ADL Chronic</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <select name="adl_chronic" class="text3">
                                        <option value=""></option>
                                        <?php
                                        for ($i = 12; $i <= 60; $i++) {
                                            echo "<option value=$i>$i</option>";
                                        }
                                        ?>
                                    </select>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">ICU Indikator</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <select name="icu_indikator" class="text3">
                                        <option value="0">0</option>
                                        <option value="1">1</option>
                                    </select>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">ICU Los</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="icu_los" class="text" type="text" class="inputbox" value="0" size="5" maxlength="5" pattern="[0-9]{1,5}" title=" 0-9 (Maksimal 5 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Jumlah Jam Penggunaan Ventilator di ICU</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="ventilator_hour" class="text" type="text" class="inputbox" value="0" size="5" maxlength="5" pattern="[0-9]{1,5}" title=" 0-9 (Maksimal 5 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Indikator Upgrade Kelas</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <select name="upgrade_class_ind" class="text3">
                                        <option value="<?php echo $upgrade_class_ind; ?>"><?php echo $upgrade_class_ind; ?></option>
                                        <option value="0">0</option>
                                        <option value="1">1</option>
                                    </select>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Naik ke Kelas</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <select name="upgrade_class_class" class="text2">
                                        <option value="<?php echo $naikkelas; ?>"><?php echo $naikkelas; ?></option>
                                        <option value="kelas_1">Kelas 1</option>
                                        <option value="kelas_2">Kelas 2</option>
                                        <option value="vip">Kelas VIP</option>
                                        <option value="vvip">Kelas VVIP</option>
                                    </select>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Lama Hari Naik Kelas</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="upgrade_class_los" class="text" type="text" class="inputbox" value="0" size="5" maxlength="5" pattern="[0-9]{1,5}" title=" 0-9 (Maksimal 5 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Topup Oleh</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="upgrade_class_payor" class="text" type="text" class="inputbox" value="<?php echo $upgrade_class_payor; ?>" size="5" maxlength="5" pattern="[0-9]{1,5}" title=" 0-9 (Maksimal 5 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Tambahan</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="add_payment_pct" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Berat Saat Lahir</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="birth_weight" class="text" type="text" class="inputbox" value="" size="5" maxlength="5" pattern="[0-9]{1,5}" title=" 0-9 (Maksimal 5 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Sistole</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="sistole" class="text" type="text" class="inputbox" value="<?php echo substr($sistolediastole, 0, 3); ?>" size="5" maxlength="5" pattern="[0-9]{1,5}" title=" 0-9 (Maksimal 5 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Diastole</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="diastole" class="text" type="text" class="inputbox" value="<?php echo substr($sistolediastole, 4, 5); ?>" size="5" maxlength="5" pattern="[0-9]{1,5}" title=" 0-9 (Maksimal 5 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Status Pulang</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <select name="discharge_status" class="text2">
                                        <?php
                                        if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Sembuh' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='1'>Atas persetujuan dokter</option>";
                                        } else if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Sehat' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='1'>Atas persetujuan dokter</option>";
                                        } else if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Rujuk' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='2'>Dirujuk</option>";
                                        } else if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='APS' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='3'>Atas permintaan sendiri</option>";
                                        } else if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Pulang Paksa' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='3'>Atas permintaan sendiri</option>";
                                        } else if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Meninggal' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='4'>Meninggal</option>";
                                        } else if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='+' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='4'>Meninggal</option>";
                                        } else if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Atas Persetujuan Dokter' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='1'>Atas persetujuan dokter</option>";
                                        } else if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Atas Permintaan Sendiri' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='3'>Atas permintaan sendiri</option>";
                                        } else if (getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Lain-lain' and kamar_inap.no_rawat='" . $norawat . "'") > 0) {
                                            echo "<option value='5'>Lain-lain</option>";
                                        } else {
                                            echo "<option value='1'>Atas persetujuan dokter</option>";
                                        }
                                        ?>
                                        <option value="1">Atas persetujuan dokter</option>
                                        <option value="2">Dirujuk</option>
                                        <option value="3">Atas permintaan sendiri</option>
                                        <option value="4">Meninggal</option>
                                        <option value="5">Lain-lain</option>
                                    </select>
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Diagnosa</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="diagnosa" class="text" type=text class="inputbox" value="<?php
                                                                                                            $penyakit = "";
                                                                                                            $a = 1;
                                                                                                            $hasilpenyakit = bukaquery("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat='" . $norawat . "' order by diagnosa_pasien.prioritas asc");
                                                                                                            while ($barispenyakit = mysqli_fetch_array($hasilpenyakit)) {
                                                                                                                if ($a == 1) {
                                                                                                                    $penyakit = $barispenyakit["kd_penyakit"];
                                                                                                                } else {
                                                                                                                    $penyakit = $penyakit . "#" . $barispenyakit["kd_penyakit"];
                                                                                                                }
                                                                                                                $a++;
                                                                                                            }
                                                                                                            echo $penyakit;
                                                                                                            ?>" size="60" maxlength="100">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Prosedur</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="procedure" class="text" type=text class="inputbox" value="<?php
                                                                                                            $prosedur = "";
                                                                                                            $a = 1;
                                                                                                            $hasilprosedur = bukaquery("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat='" . $norawat . "' order by prosedur_pasien.prioritas asc");
                                                                                                            while ($barisprosedur = mysqli_fetch_array($hasilprosedur)) {
                                                                                                                if ($a == 1) {
                                                                                                                    $prosedur = $barisprosedur["kode"];
                                                                                                                } else {
                                                                                                                    $prosedur = $prosedur . "#" . $barisprosedur["kode"];
                                                                                                                }
                                                                                                                $a++;
                                                                                                            }
                                                                                                            echo $prosedur;
                                                                                                            ?>" size="60" maxlength="100">
                                </td>
                            </tr>
                            <?php

                            $prosedur_non_bedah = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ralan Dokter' and nm_perawatan not like '%Pemeriksaan Dr. Spesialis%' and nm_perawatan not like '%konsul%' and nm_perawatan not like '%Sewa Alat%'") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ranap Dokter' and nm_perawatan not like '%Visite%' and nm_perawatan not like '%konsul%' and nm_perawatan not like '%Sewa Alat%'");
                            if ($prosedur_non_bedah == "") {
                                $prosedur_non_bedah = "0";
                            }
                            $prosedur_bedah = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Operasi'");
                            if ($prosedur_bedah == "") {
                                $prosedur_bedah = "0";
                            }
                            $konsultasi = (getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ranap Dokter' and nm_perawatan like '%Visite%'") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ralan Dokter' and (nm_perawatan like '%Pemeriksaan Dr. Spesialis%' OR nm_perawatan like '%konsul%')"));
                            if ($konsultasi == "") {
                                $konsultasi = "0";
                            }
                            $tenaga_ahli = 0;
                            if ($tenaga_ahli == "") {
                                $tenaga_ahli = "0";
                            }
                            $keperawatan = (getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ranap Paramedis' and nm_perawatan not like '%kamar%' and nm_perawatan not like '%KAMAR%' and nm_perawatan not like '%Sewa Alat%'") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ralan Paramedis' and nm_perawatan not like '%Sewa Alat%'"));
                            if ($keperawatan == "") {
                                $keperawatan = "0";
                            }
                            $radiologi = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Radiologi'");
                            if ($radiologi == "") {
                                $radiologi = "0";
                            }
                            $laboratorium = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Laborat'");
                            if ($laboratorium == "") {
                                $laboratorium = "0";
                            }
                            $kamar = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ranap Paramedis' and (nm_perawatan like '%kamar%' OR nm_perawatan like '%KAMAR%'  )") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Service'") + getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Tambahan'") + getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where nm_perawatan not like '%FARMASI 23%' and no_rawat='" . $norawat . "' and status='Potongan'");
                            if ($kamar == "") {
                                $kamar = "0";
                            }
                            $obat_kronis = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where nm_perawatan like '%kronis%' and no_rawat='" . $norawat . "' and status='Obat'");
                            $farmasi23 = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where nm_perawatan like '%FARMASI 23%' and no_rawat='" . $norawat . "' and status='Potongan'");
                            $obat_kemoterapi = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where nm_perawatan like '%kemo%' and no_rawat='" . $norawat . "' and status='Obat'");
                            $obat = (getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Obat' and nm_perawatan not like '%BAHAN MEDIS HABIS PAKAI%' and nm_perawatan not like '%BHP%' and nm_perawatan not like '%Alkes%'") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Retur Obat' and nm_perawatan not like '%BAHAN MEDIS HABIS PAKAI%' and nm_perawatan not like '%BHP%' and nm_perawatan not like '%Alkes%'") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Resep Pulang' and nm_perawatan not like '%BAHAN MEDIS HABIS PAKAI%' and nm_perawatan not like '%BHP%' and nm_perawatan not like '%Alkes%'") - $obat_kronis - $obat_kemoterapi + $farmasi23);
                            if ($obat == "") {
                                $obat = "0";
                            }
                            if ($obat_kemoterapi == "") {
                                $obat_kemoterapi = "0";
                            }
                            if ($obat_kronis == "") {
                                $obat_kronis = "0";
                            }
                            $bmhp = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Obat' and nm_perawatan like '%BAHAN MEDIS HABIS PAKAI%' ");
                            if ($bmhp == "") {
                                $bmhp = "0";
                            }
                            $alkes = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Obat' and nm_perawatan like '%Alkes%' ");
                            if ($alkes == "") {
                                $alkes = "0";
                            }
                            $sewa_alat = (getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ranap Paramedis' and nm_perawatan like '%Sewa Alat%'") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ralan Paramedis' and nm_perawatan like '%Sewa Alat%'") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ranap Dokter' and nm_perawatan like '%Sewa Alat%'") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ralan Dokter' and nm_perawatan like '%Sewa Alat%'")
                            );
                            if ($sewa_alat == "") {
                                $sewa_alat = "0";
                            }
                            $rehabilitasi = getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ralan Dokter Paramedis' and nm_perawatan like '%(KRM)%'") +
                                getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='" . $norawat . "' and status='Ranap Dokter Paramedis' and nm_perawatan like '%(KRM)%'");
                            if ($rehabilitasi == "") {
                                $rehabilitasi = "0";
                            }

                            ?>
                            <tr class="head">
                                <td width="41%">Biaya Prosedur Non Bedah</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="prosedur_non_bedah" class="text" type="text" class="inputbox" value="<?php echo $prosedur_non_bedah; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Prosedur Bedah</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="prosedur_bedah" class="text" type="text" class="inputbox" value="<?php echo $prosedur_bedah; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Konsultasi</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="konsultasi" class="text" type="text" class="inputbox" value="<?php echo $konsultasi; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Tenaga Ahli</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="tenaga_ahli" class="text" type="text" class="inputbox" value="<?php echo $tenaga_ahli; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Keperawatan</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="keperawatan" class="text" type="text" class="inputbox" value="<?php echo $keperawatan; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Penunjang</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="penunjang" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Radiologi</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="radiologi" class="text" type="text" class="inputbox" value="<?php echo $radiologi; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Laboratorium</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="laboratorium" class="text" type="text" class="inputbox" value="<?php echo $laboratorium; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Pelayanan Darah</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="pelayanan_darah" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Rehabilitasi</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="rehabilitasi" class="text" type="text" class="inputbox" value="<?php echo $rehabilitasi; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Kamar</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="kamar" class="text" type="text" class="inputbox" value="<?php echo $kamar; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Rawat Intensif</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="rawat_intensif" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Obat</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="obat" class="text" type="text" class="inputbox" value="<?php echo $obat; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Obat Kronis</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="obat_kronis" class="text" type="text" class="inputbox" value="<?php echo $obat_kronis; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Obat Kemoterapi</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="obat_kemoterapi" class="text" type="text" class="inputbox" value="<?php echo $obat_kemoterapi; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Alkes</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="alkes" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya BMHP</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="bmhp" class="text" type="text" class="inputbox" value="<?php echo $bmhp; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Biaya Sewa Alat</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="sewa_alat" class="text" type="text" class="inputbox" value="<?php echo $sewa_alat; ?>" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Tarif Poli Eksekutif</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <input name="tarif_poli_eks" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15" pattern="[0-9]{1,15}" title=" 0-9 (Maksimal 15 karakter)" autocomplete="off">
                                </td>
                            </tr>
                            <tr class="head">
                                <td width="41%">Nama Dokter</td>
                                <td width="">:</td>
                                <td width="57%">
                                    <select name="nama_dokter" class="text2">
                                        <option value="<?php echo $nm_dokter; ?>"><?php echo $nm_dokter; ?></option>
                                        <?php
                                        $hasildokter = bukaquery("select dokter.nm_dokter from dokter where dokter.status='1' order by dokter.nm_dokter asc");
                                        while ($barisdokter = mysqli_fetch_array($hasildokter)) {
                                            echo "<option value=" . $barisdokter["nm_dokter"] . ">" . $barisdokter["nm_dokter"] . "</option>";
                                        }
                                        ?>
                                    </select>
                                </td>
                            </tr>

                        </table>
                    </div>

            <?php }
            } ?>




            <?php
            $BtnSimpan = isset($_POST['BtnSimpan']) ? $_POST['BtnSimpan'] : NULL;
            if (isset($BtnSimpan)) {
                $norawat           = validTeks(trim($_POST['no_rawat']));
                $tgl_registrasi    = validTeks(trim($_POST['tgl_registrasi']));
                $codernik          = validTeks(trim($_POST['codernik']));
                $nosep             = validTeks(trim($_POST['nosep']));
                $nokartu           = validTeks(trim($_POST['nokartu']));
                $nm_pasien         = validTeks(trim($_POST['nm_pasien']));
                $keluar            = validTeks(trim($_POST['keluar']));
                $kelas_rawat       = validTeks(trim($_POST['kelas_rawat']));
                $adl_sub_acute     = validTeks(trim($_POST['adl_sub_acute']));
                $adl_chronic       = validTeks(trim($_POST['adl_chronic']));
                $icu_indikator     = validTeks(trim($_POST['icu_indikator']));
                $icu_los           = validTeks(trim($_POST['icu_los']));
                $ventilator_hour   = validTeks(trim($_POST['ventilator_hour']));
                $upgrade_class_ind = validTeks(trim($_POST['upgrade_class_ind']));
                $upgrade_class_class = validTeks(trim($_POST['upgrade_class_class']));
                $upgrade_class_los = validTeks(trim($_POST['upgrade_class_los']));
                $upgrade_class_payor = validTeks(trim($_POST['upgrade_class_payor']));
                $add_payment_pct   = validTeks(trim($_POST['add_payment_pct']));
                $birth_weight      = validTeks(trim($_POST['birth_weight']));
                $discharge_status  = validTeks(trim($_POST['discharge_status']));
                $diagnosa          = validTeks2(trim($_POST['diagnosa']));
                $procedure         = validTeks2(trim($_POST['procedure']));
                $prosedur_non_bedah = validTeks(trim($_POST['prosedur_non_bedah']));
                $prosedur_bedah    = validTeks(trim($_POST['prosedur_bedah']));
                $konsultasi        = validTeks(trim($_POST['konsultasi']));
                $tenaga_ahli       = validTeks(trim($_POST['tenaga_ahli']));
                $keperawatan       = validTeks(trim($_POST['keperawatan']));
                $penunjang         = validTeks(trim($_POST['penunjang']));
                $radiologi         = validTeks(trim($_POST['radiologi']));
                $laboratorium      = validTeks(trim($_POST['laboratorium']));
                $pelayanan_darah   = validTeks(trim($_POST['pelayanan_darah']));
                $rehabilitasi      = validTeks(trim($_POST['rehabilitasi']));
                $kamar             = validTeks(trim($_POST['kamar']));
                $rawat_intensif    = validTeks(trim($_POST['rawat_intensif']));
                $obat              = validTeks(trim($_POST['obat']));
                $obat_kronis       = validTeks(trim($_POST['obat_kronis']));
                $obat_kemoterapi   = validTeks(trim($_POST['obat_kemoterapi']));
                $alkes             = validTeks(trim($_POST['alkes']));
                $bmhp              = validTeks(trim($_POST['bmhp']));
                $sewa_alat         = validTeks(trim($_POST['sewa_alat']));
                $tarif_poli_eks    = validTeks(trim($_POST['tarif_poli_eks']));
                $nama_dokter       = validTeks(trim($_POST['nama_dokter']));
                $jk                = validTeks(trim($_POST['jk']));
                $tgl_lahir         = validTeks(trim($_POST['tgl_lahir']));
                $jnsrawat          = validTeks(trim($_POST['jnsrawat']));
                $caramasuk          = validTeks(trim($_POST['cara_masuk']));
                $sistole          = validTeks(trim($_POST['sistole']));
                $diastole          = validTeks(trim($_POST['diastole']));
                $gender            = "";
                if ($jk == "L") {
                    $gender = "1";
                } else {
                    $gender = "2";
                }

                if ((!empty($norawat)) && (!empty($nosep)) && (!empty($nokartu))) {
                    BuatKlaimBaru2($nokartu, $nosep, $no_rkm_medis, $nm_pasien, $tgl_lahir . " 00:00:00", $gender, $norawat);
                    EditUlangKlaim($nosep);
                    UpdateDataKlaim2(
                        $nosep,
                        $nokartu,
                        $tgl_registrasi,
                        $keluar,
                        $jnsrawat,
                        $kelas_rawat,
                        $adl_sub_acute,
                        $adl_chronic,
                        $icu_indikator,
                        $icu_los,
                        $ventilator_hour,
                        $upgrade_class_ind,
                        $upgrade_class_class,
                        $upgrade_class_los,
                        $add_payment_pct,
                        $birth_weight,
                        $discharge_status,
                        $diagnosa,
                        $procedure,
                        $tarif_poli_eks,
                        $nama_dokter,
                        getKelasRS(),
                        "3",
                        "JKN",
                        "#",
                        $codernik,
                        $prosedur_non_bedah,
                        $prosedur_bedah,
                        $konsultasi,
                        $tenaga_ahli,
                        $keperawatan,
                        $penunjang,
                        $radiologi,
                        $laboratorium,
                        $pelayanan_darah,
                        $rehabilitasi,
                        $kamar,
                        $rawat_intensif,
                        $obat,
                        $obat_kronis,
                        $obat_kemoterapi,
                        $alkes,
                        $bmhp,
                        $sewa_alat,
                        $caramasuk,
                        $sistole,
                        $diastole
                    );
                    // echo "<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual2&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik'>";
                } else if ((empty($norawat)) || (empty($nosep)) || (empty($nokartu))) {
                    echo 'Semua field harus isi..!!!';
                }
            }

            $BtnGrouping = isset($_POST['BtnGrouping']) ? $_POST['BtnGrouping'] : NULL;
            if (isset($BtnGrouping)) {
                if ((!empty($nosep)) && (!empty($codernik))) {
                    GroupingStage12($nosep, $codernik);
                }
            }

            $BtnFinalClaim = isset($_POST['FinalClaim']) ? $_POST['FinalClaim'] : NULL;
            if (isset($BtnFinalClaim)) {
                if ((!empty($nosep)) && (!empty($codernik))) {
                    FinalisasiKlaim($nosep, $codernik);
                }
            }

            $btnHapusKlaim = isset($_POST['HapusKlaim']) ? $_POST['HapusKlaim'] : NULL;
            if (isset($btnHapusKlaim)) {
                if ((!empty($nosep)) && (!empty($codernik))) {
                    MenghapusKlaim($nosep, $codernik);
                }
            }

            $BtnKirimDCIndividual = isset($_POST['KirimDCIndividual']) ? $_POST['KirimDCIndividual'] : NULL;
            if (isset($BtnKirimDCIndividual)) {
                if ((!empty($nosep)) && (!empty($codernik))) {
                    KirimKlaimIndividualKeDC($nosep, $codernik);
                }
            }

            ?>
        </div>
        <?php

        if (getOne("select count(inacbg_grouping_stage12.no_sep) from inacbg_grouping_stage12 where inacbg_grouping_stage12.no_sep='" . $nosep . "'") > 0) { ?>
            <?php if (getOne("select count(inacbg_data_terkirim_final.no_sep) from inacbg_data_terkirim_final where inacbg_data_terkirim_final.no_sep='" . $nosep . "'") > 0) { ?>
                <!-- <div align="center"><input name=KirimDCIndividual type=submit class="btn btn-success" value="&nbsp;&nbsp;Kirim Ke Data Center&nbsp;&nbsp;"></div><br> -->
            <?php } else { ?>
                <div align="center"><input name=FinalClaim type=submit class="btn btn-success" value="&nbsp;&nbsp;Finalisasi Klaim&nbsp;&nbsp;"></div><br>
            <?php } ?>

            <div align="center"><input name=HapusKlaim type=submit class="btn btn-danger" value="&nbsp;&nbsp;Hapus Data Claim&nbsp;&nbsp;"></div><br>
            <br>
        <?php
            GetHistoryKlaim($no_rkm_medis);
        } else { ?>
            <div align="center"><input name=BtnSimpan type=submit class="btn btn-info" value="&nbsp;&nbsp;Simpan ke INACBG&nbsp;&nbsp;"></div><br>
        <?php } ?>
        <?php PrintClaimData($nosep); ?>
    </form>
</div>