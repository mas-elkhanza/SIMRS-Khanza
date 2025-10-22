<?php
    if (strpos($_SERVER['REQUEST_URI'], "pages")) {
        exit(header("Location:../index.php"));
    }

    $sekarang    = date("Y-m-d");
    $thnsekarang = substr($sekarang, 0, 4);
    $blnsekarang = substr($sekarang, 5, 2);
    $tglsekarang = substr($sekarang, 8, 2);
    $thncarimcu  = $thnsekarang;
    $blncarimcu  = $blnsekarang;
    $tglcarimcu  = $tglsekarang;
    $thncarimcu2 = $thnsekarang;
    $blncarimcu2 = $blnsekarang;
    $tglcarimcu2 = $tglsekarang;

    $perusahaan = validTeks4(encrypt_decrypt($_SESSION["ses_emcu"], "d"), 20);

    if (isset($_POST["BtnCari"])) {
        $thncarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu'])) ? substr($_POST['tgl_cari_mcu'], 6, 4) : $thnsekarang);
        $blncarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu'])) ? substr($_POST['tgl_cari_mcu'], 3, 2) : $blnsekarang);
        $tglcarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu'])) ? substr($_POST['tgl_cari_mcu'], 0, 2) : $tglsekarang);
        $thncarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2'])) ? substr($_POST['tgl_cari_mcu2'], 6, 4) : $thnsekarang);
        $blncarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2'])) ? substr($_POST['tgl_cari_mcu2'], 3, 2) : $blnsekarang);
        $tglcarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2'])) ? substr($_POST['tgl_cari_mcu2'], 0, 2) : $tglsekarang);
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center><b>GRAFIK PEMERIKSAAN MCU</b></center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <form id="form_validation" action="" method="POST" autocomplete="off">
                    <div class="row clearfix">
                        <div class="col-md-6">
                            <label for="tgl_cari_mcu">Tanggal MCU</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_mcu" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarimcu."-".$blncarimcu."-".$thncarimcu;?>"/>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="tgl_cari_mcu2">Sampai Dengan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_mcu2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarimcu2."-".$blncarimcu2."-".$thncarimcu2;?>"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <center>
                        <button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Tampilkan Grafik</button>
                    </center>
                </form>
                <br>
                <div class="row clearfix">
                    <div class="col-md-6">
                        <?php
                            $dataBMI = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.kasifikasi_bmi, COUNT(penilaian_mcu.kasifikasi_bmi) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.kasifikasi_bmi"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataBMI[] = [
                                    'label' => $row['kasifikasi_bmi'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Klasifikasi BMI</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_bmi" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <?php
                            $dataPinggang = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.risiko_lingkar_pinggang, COUNT(penilaian_mcu.risiko_lingkar_pinggang) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.risiko_lingkar_pinggang"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataPinggang[] = [
                                    'label' => $row['risiko_lingkar_pinggang'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Risiko Lingkar Perut</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_pinggang" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataSubmandibula = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.submandibula, COUNT(penilaian_mcu.submandibula) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.submandibula"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataSubmandibula[] = [
                                    'label' => $row['submandibula'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Submandibula Kelenjar Limfe</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_submandibula" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataAxilla = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.axilla, COUNT(penilaian_mcu.axilla) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.axilla"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataAxilla[] = [
                                    'label' => $row['axilla'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Axilla Kelenjar Limfe</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_axilla" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataSupraklavikula = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.supraklavikula, COUNT(penilaian_mcu.supraklavikula) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.supraklavikula"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataSupraklavikula[] = [
                                    'label' => $row['supraklavikula'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Supraklavikula Kelenjar Limfe</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_supraklavikula" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataLeher = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.leher, COUNT(penilaian_mcu.leher) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.leher"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataLeher[] = [
                                    'label' => $row['leher'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Leher Kelenjar Limfe</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_leher" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataInguinal = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.inguinal, COUNT(penilaian_mcu.inguinal) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.inguinal"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataInguinal[] = [
                                    'label' => $row['inguinal'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Inguinal Kelenjar Limfe</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_inguinal" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataOedema = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.oedema, COUNT(penilaian_mcu.oedema) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.oedema"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataOedema[] = [
                                    'label' => $row['oedema'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Oedema Muka</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_oedema" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataSinusFrontalis = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.sinus_frontalis, COUNT(penilaian_mcu.sinus_frontalis) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.sinus_frontalis"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataSinusFrontalis[] = [
                                    'label' => $row['sinus_frontalis'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Nyeri Tekan Sinus Frontalis Muka</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_sinus_frontalis" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataSinusMaxilaris = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.sinus_maxilaris, COUNT(penilaian_mcu.sinus_maxilaris) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.sinus_maxilaris"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataSinusMaxilaris[] = [
                                    'label' => $row['sinus_maxilaris'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Nyeri Tekanan Sinus Maxilaris Muka</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_sinus_maxilaris" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataPalpebra = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.palpebra, COUNT(penilaian_mcu.palpebra) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.palpebra"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataPalpebra[] = [
                                    'label' => $row['palpebra'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Palpebra Mata</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_palpebra" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataSklera = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.sklera, COUNT(penilaian_mcu.sklera) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.sklera"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataSklera[] = [
                                    'label' => $row['sklera'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Sklera Mata</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_sklera" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataCornea = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.cornea, COUNT(penilaian_mcu.cornea) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.cornea"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataCornea[] = [
                                    'label' => $row['cornea'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Cornea Mata</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_cornea" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataButaWarna = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.buta_warna, COUNT(penilaian_mcu.buta_warna) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.buta_warna"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataButaWarna[] = [
                                    'label' => $row['buta_warna'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Buta Warna Mata</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_buta_warna" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataKonjungtiva = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.konjungtiva, COUNT(penilaian_mcu.konjungtiva) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.konjungtiva"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataKonjungtiva[] = [
                                    'label' => $row['konjungtiva'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Konjungtiva Mata</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_konjungtiva" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataLensa = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.lensa, COUNT(penilaian_mcu.lensa) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.lensa"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataLensa[] = [
                                    'label' => $row['lensa'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Lensa Mata</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_lensa" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataPupil = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.pupil, COUNT(penilaian_mcu.pupil) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.pupil"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataPupil[] = [
                                    'label' => $row['pupil'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Pupil Mata</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_pupil" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataMenggunakanKacamata = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.menggunakan_kacamata, COUNT(penilaian_mcu.menggunakan_kacamata) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.menggunakan_kacamata"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataMenggunakanKacamata[] = [
                                    'label' => $row['menggunakan_kacamata'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Menggunakan Kacamata</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_menggunakan_kacamata" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataLuasLapangPandang = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.luas_lapang_pandang, COUNT(penilaian_mcu.luas_lapang_pandang) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.luas_lapang_pandang"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataLuasLapangPandang[] = [
                                    'label' => $row['luas_lapang_pandang'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Luas Lapang Pandang Mata</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_luas_lapang_pandang" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataLubangTelinga = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.lubang_telinga, COUNT(penilaian_mcu.lubang_telinga) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.lubang_telinga"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataLubangTelinga[] = [
                                    'label' => $row['lubang_telinga'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Lubang Telinga</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_lubang_telinga" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataDaunTelinga = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.daun_telinga, COUNT(penilaian_mcu.daun_telinga) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.daun_telinga"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataDaunTelinga[] = [
                                    'label' => $row['daun_telinga'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Daun Telinga</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_daun_telinga" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataSelaputPendengaran = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.selaput_pendengaran, COUNT(penilaian_mcu.selaput_pendengaran) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.selaput_pendengaran"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataSelaputPendengaran[] = [
                                    'label' => $row['selaput_pendengaran'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Selaput Dengar Telinga</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_selaput_pendengaran" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataProcMastoideus = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.proc_mastoideus, COUNT(penilaian_mcu.proc_mastoideus) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.proc_mastoideus"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataProcMastoideus[] = [
                                    'label' => $row['proc_mastoideus'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Proc. Mastoideus Telinga</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_proc_mastoideus" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataSeptumNasi = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.septum_nasi, COUNT(penilaian_mcu.septum_nasi) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.septum_nasi"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataSeptumNasi[] = [
                                    'label' => $row['septum_nasi'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Septum Nasi Hidung</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_septum_nasi" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataLubangHidung = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.lubang_hidung, COUNT(penilaian_mcu.lubang_hidung) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.lubang_hidung"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataLubangHidung[] = [
                                    'label' => $row['lubang_hidung'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Lubang Hidung</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_lubang_hidung" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataSinusHidung = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.sinus, COUNT(penilaian_mcu.sinus) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.sinus"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataSinusHidung[] = [
                                    'label' => $row['sinus'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Sinus Hidung</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_sinus" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataBibir = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.bibir, COUNT(penilaian_mcu.bibir) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.bibir"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataBibir[] = [
                                    'label' => $row['bibir'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Bibir</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_bibir" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataGusi = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.gusi, COUNT(penilaian_mcu.gusi) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.gusi"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataGusi[] = [
                                    'label' => $row['gusi'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Gusi</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_gusi" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataGigi = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.gigi, COUNT(penilaian_mcu.gigi) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.gigi"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataGigi[] = [
                                    'label' => $row['gigi'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Gigi</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_gigi" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataCaries = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.caries, COUNT(penilaian_mcu.caries) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.caries"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataCaries[] = [
                                    'label' => $row['caries'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Caries</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_caries" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataLidah = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.lidah, COUNT(penilaian_mcu.lidah) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.lidah"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataLidah[] = [
                                    'label' => $row['lidah'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Lidah</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_lidah" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataFaring = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.faring, COUNT(penilaian_mcu.faring) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.faring"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataFaring[] = [
                                    'label' => $row['faring'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Faring Mulut</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_faring" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataTonsil = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.tonsil, COUNT(penilaian_mcu.tonsil) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.tonsil"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataTonsil[] = [
                                    'label' => $row['tonsil'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Tonsil Mulut</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_tonsil" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataKelenjarLimfe = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.kelenjar_limfe, COUNT(penilaian_mcu.kelenjar_limfe) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.kelenjar_limfe"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataKelenjarLimfe[] = [
                                    'label' => $row['kelenjar_limfe'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Kelenjar Limfe</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_kelenjar_limfe" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataKelenjarGondok = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.kelenjar_gondok, COUNT(penilaian_mcu.kelenjar_gondok) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.kelenjar_gondok"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataKelenjarGondok[] = [
                                    'label' => $row['kelenjar_gondok'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Kelenjar Gondok</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_kelenjar_gondok" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataGerakanDada = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.gerakan_dada, COUNT(penilaian_mcu.gerakan_dada) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.gerakan_dada"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataGerakanDada[] = [
                                    'label' => $row['gerakan_dada'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Gerakan Dada Paru-paru</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_gerakan_dada" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataVocalFemitus = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.vocal_femitus, COUNT(penilaian_mcu.vocal_femitus) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.vocal_femitus"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataVocalFemitus[] = [
                                    'label' => $row['vocal_femitus'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Vocal Fremitus Paru-paru</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_vocal_femitus" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataPerkusiDada = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.perkusi_dada, COUNT(penilaian_mcu.perkusi_dada) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.perkusi_dada"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataPerkusiDada[] = [
                                    'label' => $row['perkusi_dada'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Perkusi Paru-paru</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_perkusi_dada" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataBunyiNapas = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.bunyi_napas, COUNT(penilaian_mcu.bunyi_napas) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.bunyi_napas"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataBunyiNapas[] = [
                                    'label' => $row['bunyi_napas'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Bunyi Napas Paru-paru</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_bunyi_napas" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataBunyiTambahan = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.bunyi_tambahan, COUNT(penilaian_mcu.bunyi_tambahan) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.bunyi_tambahan"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataBunyiTambahan[] = [
                                    'label' => $row['bunyi_tambahan'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Bunyi Tambahan Paru-paru</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_bunyi_tambahan" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataIctusCordis = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.ictus_cordis, COUNT(penilaian_mcu.ictus_cordis) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.ictus_cordis"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataIctusCordis[] = [
                                    'label' => $row['ictus_cordis'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Ictus Cordis Jantung</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_ictus_cordis" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataBunyiJantung = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.bunyi_jantung, COUNT(penilaian_mcu.bunyi_jantung) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.bunyi_jantung"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataBunyiJantung[] = [
                                    'label' => $row['bunyi_jantung'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Bunyi Jantung</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_bunyi_jantung" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataBatasJantung = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.batas, COUNT(penilaian_mcu.batas) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.batas"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataBatasJantung[] = [
                                    'label' => $row['batas'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Batas Jantung</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_batas" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataMamae = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.mamae, COUNT(penilaian_mcu.mamae) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.mamae"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataMamae[] = [
                                    'label' => $row['mamae'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Mamae</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_mamae" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataInspeksi = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.inspeksi, COUNT(penilaian_mcu.inspeksi) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.inspeksi"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataInspeksi[] = [
                                    'label' => $row['inspeksi'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Inspeksi Abdomen</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_inspeksi" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataPalpasi = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.palpasi, COUNT(penilaian_mcu.palpasi) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.palpasi"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataPalpasi[] = [
                                    'label' => $row['palpasi'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Palpasi Abdomen</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_palpasi" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataHepar = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.hepar, COUNT(penilaian_mcu.hepar) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.hepar"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataHepar[] = [
                                    'label' => $row['hepar'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Hepar Abdomen</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_hepar" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataPerkusiAbdomen = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.perkusi_abdomen, COUNT(penilaian_mcu.perkusi_abdomen) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.perkusi_abdomen"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataPerkusiAbdomen[] = [
                                    'label' => $row['perkusi_abdomen'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Perkusi Abdomen</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_perkusi_abdomen" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataAuskultasi = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.auskultasi, COUNT(penilaian_mcu.auskultasi) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.auskultasi"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataAuskultasi[] = [
                                    'label' => $row['auskultasi'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Auskultasi Abdomen</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_auskultasi" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataLimpa = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.limpa, COUNT(penilaian_mcu.limpa) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.limpa"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataLimpa[] = [
                                    'label' => $row['limpa'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Limpa Abdomen</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_limpa" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataCostovertebral = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.costovertebral, COUNT(penilaian_mcu.costovertebral) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.costovertebral"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataCostovertebral[] = [
                                    'label' => $row['costovertebral'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Nyeri Ketok CVA Punggung</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_costovertebral" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataScoliosis = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.scoliosis, COUNT(penilaian_mcu.scoliosis) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.scoliosis"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataScoliosis[] = [
                                    'label' => $row['scoliosis'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Scoliosis Punggung</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_scoliosis" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataKondisiKulit = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.kondisi_kulit, COUNT(penilaian_mcu.kondisi_kulit) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.kondisi_kulit"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataKondisiKulit[] = [
                                    'label' => $row['kondisi_kulit'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Kondisi Kulit</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_kondisi_kulit" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataEkstrimitasAtas = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.ekstrimitas_atas, COUNT(penilaian_mcu.ekstrimitas_atas) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.ekstrimitas_atas"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataEkstrimitasAtas[] = [
                                    'label' => $row['ekstrimitas_atas'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Ekstremitas Atas</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_ekstrimitas_atas" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataEkstrimitasBawah = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.ekstrimitas_bawah, COUNT(penilaian_mcu.ekstrimitas_bawah) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.ekstrimitas_bawah"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataEkstrimitasBawah[] = [
                                    'label' => $row['ekstrimitas_bawah'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Ekstremitas Bawah</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_ekstrimitas_bawah" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataAreaGenitalia = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.area_genitalia, COUNT(penilaian_mcu.area_genitalia) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.area_genitalia"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataAreaGenitalia[] = [
                                    'label' => $row['area_genitalia'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Area Genitalia</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_area_genitalia" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <?php
                            $dataAnusPerianal = [];
                            $query = bukaquery(
                                "SELECT penilaian_mcu.anus_perianal, COUNT(penilaian_mcu.anus_perianal) AS jumlah FROM pasien INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN penilaian_mcu ON reg_periksa.no_rawat = penilaian_mcu.no_rawat WHERE pasien.perusahaan_pasien = '$perusahaan' AND penilaian_mcu.tanggal BETWEEN '$thncarimcu-$blncarimcu-$tglcarimcu' AND '$thncarimcu2-$blncarimcu2-$tglcarimcu2' GROUP BY penilaian_mcu.anus_perianal"
                            );

                            while ($row = mysqli_fetch_array($query)) {
                                $dataAnusPerianal[] = [
                                    'label' => $row['anus_perianal'],
                                    'data'  => (int)$row['jumlah']
                                ];
                            }
                        ?>
                        <div class="container-fluid">
                            <div class="card">
                                <div class="header bg-white">
                                    <div class="text-center">Anus & Perianal</div>
                                </div>
                                <div class="body">
                                    <div id="pie_chart_anus_perianal" class="flot-chart" style="height: 400px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>  
            </div>
        </div>
    </div>
</div>

<!-- JS -->
<script src="plugins/jquery/jquery.min.js"></script>
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="plugins/flot-charts/jquery.flot.js"></script>
<script src="plugins/flot-charts/jquery.flot.resize.js"></script>
<script src="plugins/flot-charts/jquery.flot.pie.js"></script>

<script>
$(function() {
    var dataBMI = <?= json_encode($dataBMI) ?>;
    if (dataBMI.length > 0) {
        $.plot("#pie_chart_bmi", dataBMI, {
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
        $("#pie_chart_bmi").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataPinggang = <?= json_encode($dataPinggang) ?>;
    if (dataPinggang.length > 0) {
        $.plot("#pie_chart_pinggang", dataPinggang, {
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
        $("#pie_chart_pinggang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataSubmandibula = <?= json_encode($dataSubmandibula) ?>;
    if (dataSubmandibula.length > 0) {
        $.plot("#pie_chart_submandibula", dataSubmandibula, {
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
        $("#pie_chart_submandibula").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataAxilla = <?= json_encode($dataAxilla) ?>;
    if (dataAxilla.length > 0) {
        $.plot("#pie_chart_axilla", dataAxilla, {
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
        $("#pie_chart_axilla").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataSupraklavikula = <?= json_encode($dataSupraklavikula) ?>;
    if (dataSupraklavikula.length > 0) {
        $.plot("#pie_chart_supraklavikula", dataSupraklavikula, {
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
        $("#pie_chart_supraklavikula").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataLeher = <?= json_encode($dataLeher) ?>;
    if (dataLeher.length > 0) {
        $.plot("#pie_chart_leher", dataLeher, {
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
        $("#pie_chart_leher").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataInguinal = <?= json_encode($dataInguinal) ?>;
    if (dataInguinal.length > 0) {
        $.plot("#pie_chart_inguinal", dataInguinal, {
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
        $("#pie_chart_inguinal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataOedema = <?= json_encode($dataOedema) ?>;
    if (dataOedema.length > 0) {
        $.plot("#pie_chart_oedema", dataOedema, {
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
        $("#pie_chart_oedema").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataSinusFrontalis = <?= json_encode($dataSinusFrontalis) ?>;
    if (dataSinusFrontalis.length > 0) {
        $.plot("#pie_chart_sinus_frontalis", dataSinusFrontalis, {
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
        $("#pie_chart_sinus_frontalis").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataSinusMaxilaris = <?= json_encode($dataSinusMaxilaris) ?>;
    if (dataSinusMaxilaris.length > 0) {
        $.plot("#pie_chart_sinus_maxilaris", dataSinusMaxilaris, {
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
        $("#pie_chart_sinus_maxilaris").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataPalpebra = <?= json_encode($dataPalpebra) ?>;
    if (dataPalpebra.length > 0) {
        $.plot("#pie_chart_palpebra", dataPalpebra, {
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
        $("#pie_chart_palpebra").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataSklera = <?= json_encode($dataSklera) ?>;
    if (dataSklera.length > 0) {
        $.plot("#pie_chart_sklera", dataSklera, {
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
        $("#pie_chart_sklera").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataCornea = <?= json_encode($dataCornea) ?>;
    if (dataCornea.length > 0) {
        $.plot("#pie_chart_cornea", dataCornea, {
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
        $("#pie_chart_cornea").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataButaWarna = <?= json_encode($dataButaWarna) ?>;
    if (dataButaWarna.length > 0) {
        $.plot("#pie_chart_buta_warna", dataButaWarna, {
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
        $("#pie_chart_buta_warna").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataKonjungtiva = <?= json_encode($dataKonjungtiva) ?>;
    if (dataKonjungtiva.length > 0) {
        $.plot("#pie_chart_konjungtiva", dataKonjungtiva, {
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
        $("#pie_chart_konjungtiva").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataLensa = <?= json_encode($dataLensa) ?>;
    if (dataLensa.length > 0) {
        $.plot("#pie_chart_lensa", dataLensa, {
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
        $("#pie_chart_lensa").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataPupil = <?= json_encode($dataPupil) ?>;
    if (dataPupil.length > 0) {
        $.plot("#pie_chart_pupil", dataPupil, {
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
        $("#pie_chart_pupil").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataMenggunakanKacamata = <?= json_encode($dataMenggunakanKacamata) ?>;
    if (dataMenggunakanKacamata.length > 0) {
        $.plot("#pie_chart_menggunakan_kacamata", dataMenggunakanKacamata, {
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
        $("#pie_chart_menggunakan_kacamata").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataLuasLapangPandang = <?= json_encode($dataLuasLapangPandang) ?>;
    if (dataLuasLapangPandang.length > 0) {
        $.plot("#pie_chart_luas_lapang_pandang", dataLuasLapangPandang, {
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
        $("#pie_chart_luas_lapang_pandang").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataLubangTelinga = <?= json_encode($dataLubangTelinga) ?>;
    if (dataLubangTelinga.length > 0) {
        $.plot("#pie_chart_lubang_telinga", dataLubangTelinga, {
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
        $("#pie_chart_lubang_telinga").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataDaunTelinga = <?= json_encode($dataDaunTelinga) ?>;
    if (dataDaunTelinga.length > 0) {
        $.plot("#pie_chart_daun_telinga", dataDaunTelinga, {
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
        $("#pie_chart_daun_telinga").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataSelaputPendengaran = <?= json_encode($dataSelaputPendengaran) ?>;
    if (dataSelaputPendengaran.length > 0) {
        $.plot("#pie_chart_selaput_pendengaran", dataSelaputPendengaran, {
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
        $("#pie_chart_selaput_pendengaran").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataProcMastoideus = <?= json_encode($dataProcMastoideus) ?>;
    if (dataProcMastoideus.length > 0) {
        $.plot("#pie_chart_proc_mastoideus", dataProcMastoideus, {
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
        $("#pie_chart_proc_mastoideus").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataSeptumNasi = <?= json_encode($dataSeptumNasi) ?>;
    if (dataSeptumNasi.length > 0) {
        $.plot("#pie_chart_septum_nasi", dataSeptumNasi, {
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
        $("#pie_chart_septum_nasi").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataLubangHidung = <?= json_encode($dataLubangHidung) ?>;
    if (dataLubangHidung.length > 0) {
        $.plot("#pie_chart_lubang_hidung", dataLubangHidung, {
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
        $("#pie_chart_lubang_hidung").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataSinusHidung = <?= json_encode($dataSinusHidung) ?>;
    if (dataSinusHidung.length > 0) {
        $.plot("#pie_chart_sinus", dataSinusHidung, {
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
        $("#pie_chart_sinus").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataBibir = <?= json_encode($dataBibir) ?>;
    if (dataBibir.length > 0) {
        $.plot("#pie_chart_bibir", dataBibir, {
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
        $("#pie_chart_bibir").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataGusi = <?= json_encode($dataGusi) ?>;
    if (dataGusi.length > 0) {
        $.plot("#pie_chart_gusi", dataGusi, {
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
        $("#pie_chart_gusi").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataGigi = <?= json_encode($dataGigi) ?>;
    if (dataGigi.length > 0) {
        $.plot("#pie_chart_gigi", dataGigi, {
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
        $("#pie_chart_gigi").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataCaries = <?= json_encode($dataCaries) ?>;
    if (dataCaries.length > 0) {
        $.plot("#pie_chart_caries", dataCaries, {
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
        $("#pie_chart_caries").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataLidah = <?= json_encode($dataLidah) ?>;
    if (dataLidah.length > 0) {
        $.plot("#pie_chart_lidah", dataLidah, {
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
        $("#pie_chart_lidah").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataFaring = <?= json_encode($dataFaring) ?>;
    if (dataFaring.length > 0) {
        $.plot("#pie_chart_faring", dataFaring, {
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
        $("#pie_chart_faring").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataTonsil = <?= json_encode($dataTonsil) ?>;
    if (dataTonsil.length > 0) {
        $.plot("#pie_chart_tonsil", dataTonsil, {
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
        $("#pie_chart_tonsil").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataKelenjarLimfe = <?= json_encode($dataKelenjarLimfe) ?>;
    if (dataKelenjarLimfe.length > 0) {
        $.plot("#pie_chart_kelenjar_limfe", dataKelenjarLimfe, {
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
        $("#pie_chart_kelenjar_limfe").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataKelenjarGondok = <?= json_encode($dataKelenjarGondok) ?>;
    if (dataKelenjarGondok.length > 0) {
        $.plot("#pie_chart_kelenjar_gondok", dataKelenjarGondok, {
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
        $("#pie_chart_kelenjar_gondok").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataGerakanDada = <?= json_encode($dataGerakanDada) ?>;
    if (dataGerakanDada.length > 0) {
        $.plot("#pie_chart_gerakan_dada", dataGerakanDada, {
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
        $("#pie_chart_gerakan_dada").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataVocalFemitus = <?= json_encode($dataVocalFemitus) ?>;
    if (dataVocalFemitus.length > 0) {
        $.plot("#pie_chart_vocal_femitus", dataVocalFemitus, {
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
        $("#pie_chart_vocal_femitus").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataPerkusiDada = <?= json_encode($dataPerkusiDada) ?>;
    if (dataPerkusiDada.length > 0) {
        $.plot("#pie_chart_perkusi_dada", dataPerkusiDada, {
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
        $("#pie_chart_perkusi_dada").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataBunyiNapas = <?= json_encode($dataBunyiNapas) ?>;
    if (dataBunyiNapas.length > 0) {
        $.plot("#pie_chart_bunyi_napas", dataBunyiNapas, {
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
        $("#pie_chart_bunyi_napas").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataBunyiTambahan = <?= json_encode($dataBunyiTambahan) ?>;
    if (dataBunyiTambahan.length > 0) {
        $.plot("#pie_chart_bunyi_tambahan", dataBunyiTambahan, {
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
        $("#pie_chart_bunyi_tambahan").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataIctusCordis = <?= json_encode($dataIctusCordis) ?>;
    if (dataIctusCordis.length > 0) {
        $.plot("#pie_chart_ictus_cordis", dataIctusCordis, {
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
        $("#pie_chart_ictus_cordis").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataBunyiJantung = <?= json_encode($dataBunyiJantung) ?>;
    if (dataBunyiJantung.length > 0) {
        $.plot("#pie_chart_bunyi_jantung", dataBunyiJantung, {
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
        $("#pie_chart_bunyi_jantung").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataBatasJantung = <?= json_encode($dataBatasJantung) ?>;
    if (dataBatasJantung.length > 0) {
        $.plot("#pie_chart_batas", dataBatasJantung, {
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
        $("#pie_chart_batas").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataMamae = <?= json_encode($dataMamae) ?>;
    if (dataMamae.length > 0) {
        $.plot("#pie_chart_mamae", dataMamae, {
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
        $("#pie_chart_mamae").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataInspeksi = <?= json_encode($dataInspeksi) ?>;
    if (dataInspeksi.length > 0) {
        $.plot("#pie_chart_inspeksi", dataInspeksi, {
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
        $("#pie_chart_inspeksi").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataPalpasi = <?= json_encode($dataPalpasi) ?>;
    if (dataPalpasi.length > 0) {
        $.plot("#pie_chart_palpasi", dataPalpasi, {
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
        $("#pie_chart_palpasi").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataHepar = <?= json_encode($dataHepar) ?>;
    if (dataHepar.length > 0) {
        $.plot("#pie_chart_hepar", dataHepar, {
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
        $("#pie_chart_hepar").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataPerkusiAbdomen = <?= json_encode($dataPerkusiAbdomen) ?>;
    if (dataPerkusiAbdomen.length > 0) {
        $.plot("#pie_chart_perkusi_abdomen", dataPerkusiAbdomen, {
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
        $("#pie_chart_perkusi_abdomen").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataAuskultasi = <?= json_encode($dataAuskultasi) ?>;
    if (dataAuskultasi.length > 0) {
        $.plot("#pie_chart_auskultasi", dataAuskultasi, {
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
        $("#pie_chart_auskultasi").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataLimpa = <?= json_encode($dataLimpa) ?>;
    if (dataLimpa.length > 0) {
        $.plot("#pie_chart_limpa", dataLimpa, {
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
        $("#pie_chart_limpa").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataCostovertebral = <?= json_encode($dataCostovertebral) ?>;
    if (dataCostovertebral.length > 0) {
        $.plot("#pie_chart_costovertebral", dataCostovertebral, {
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
        $("#pie_chart_costovertebral").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataScoliosis = <?= json_encode($dataScoliosis) ?>;
    if (dataScoliosis.length > 0) {
        $.plot("#pie_chart_scoliosis", dataScoliosis, {
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
        $("#pie_chart_scoliosis").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataKondisiKulit = <?= json_encode($dataKondisiKulit) ?>;
    if (dataKondisiKulit.length > 0) {
        $.plot("#pie_chart_kondisi_kulit", dataKondisiKulit, {
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
        $("#pie_chart_kondisi_kulit").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataEkstrimitasAtas = <?= json_encode($dataEkstrimitasAtas) ?>;
    if (dataEkstrimitasAtas.length > 0) {
        $.plot("#pie_chart_ekstrimitas_atas", dataEkstrimitasAtas, {
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
        $("#pie_chart_ekstrimitas_atas").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataEkstrimitasBawah = <?= json_encode($dataEkstrimitasBawah) ?>;
    if (dataEkstrimitasBawah.length > 0) {
        $.plot("#pie_chart_ekstrimitas_bawah", dataEkstrimitasBawah, {
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
        $("#pie_chart_ekstrimitas_bawah").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataAreaGenitalia = <?= json_encode($dataAreaGenitalia) ?>;
    if (dataAreaGenitalia.length > 0) {
        $.plot("#pie_chart_area_genitalia", dataAreaGenitalia, {
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
        $("#pie_chart_area_genitalia").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
    
    var dataAnusPerianal = <?= json_encode($dataAnusPerianal) ?>;
    if (dataAnusPerianal.length > 0) {
        $.plot("#pie_chart_anus_perianal", dataAnusPerianal, {
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
        $("#pie_chart_anus_perianal").html("<div class='text-center text-muted mt-5'>Kosong</div>");
    }
});
</script>
