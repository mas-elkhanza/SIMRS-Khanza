<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    if(isset($_GET['iyem'])){
        $json = json_decode(encrypt_decrypt($_GET['iyem'],"d"),true);
        if(isset($json["tanggalhapus"]) && isset($json["itemhapus"])){
            $normhapus = cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"));
            Hapus2("pasien_wearable","no_rkm_medis='$normhapus' and tanggal='".validTeks4($json["tanggalhapus"],20)."' and item='".validTeks4($json["itemhapus"],40)."'");
        }
    }

    $PNG_TEMP_DIR           = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
    $PNG_WEB_DIR            = 'temp/';
    include "plugins/phpqrcode/qrlib.php"; 
    if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
    $filename               = $PNG_TEMP_DIR.encrypt_decrypt($_SESSION["ses_pasien"],"d").'.png';
    $errorCorrectionLevel   = 'L';
    $matrixPointSize        = 4;
    QRcode::png(encrypt_decrypt($_SESSION["ses_pasien"],"d"), $filename, $errorCorrectionLevel, $matrixPointSize, 2); 
?>
<div class="block-header">
    <h2><center>KONEKSI WEARABLE</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <ul class="nav nav-tabs tab-nav-right" role="tablist">
                    <li role="presentation" <?=(isset($_GET['iyem'])?"":"class='active'")?>><a href="#panduan" data-toggle="tab">PANDUAN</a></li>
                    <li role="presentation" <?=(isset($_GET['iyem'])?"class='active'":"")?>><a href="#riwayat" data-toggle="tab">RIWAYAT DATA</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" <?=(isset($_GET['iyem'])?"class='tab-pane fade'":"class='tab-pane fade in active'")?> id="panduan">
                        <br/>
                        <div class="alert alert-info">
                            <small><b>Untuk pengguna iPhone (Apple Health).</b> Data dikirim dari aplikasi Health melalui Shortcut.</small>
                        </div>
                        <div class="table-responsive">
                            <table class="table dataTable">
                                <tr>
                                    <td><b>Token</b></td><td>: <?=encrypt_decrypt("{\"norm\":\"".encrypt_decrypt($_SESSION["ses_pasien"],"d")."\",\"password\":\"".getOne2("select AES_DECRYPT(personal_pasien.password,'windi') from personal_pasien where personal_pasien.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."'")."\"}","e");?></td>
                                </tr>
                                <tr>
                                    <td><b>URL</b></td><td>: <?="http://".$_SERVER['HTTP_HOST']."/epasien/service.php";?></td>
                                </tr>
                                <tr>
                                    <td colspan="2"><small>Salin <b>kedua</b> baris di atas. Saat memasang Shortcut, Anda perlu memasukkan <b>Token</b> dan <b>URL</b> ini.</small></td>
                                </tr>
                            </table>
                        </div>
                        <div class="card">
                            <div class="header">
                                <h2>1. Pasang Shortcut <small>Cukup unduh sekali, lalu masukkan Token dan URL Anda</small></h2>
                            </div>
                            <div class="body">
                                <p>
                                    <a href="<?="http://".$_SERVER['HTTP_HOST']."/epasien/downloadwearable.php";?>" class="btn btn-primary waves-effect">
                                         <i class="material-icons">watch</i> UNDUH SHORTCUT
                                    </a>
                                </p>
                                <ol>
                                    <li>Buka tautan di atas dari <b>iPhone Anda</b>, lalu tekan <b>Add Shortcut</b> (Tambah Pintasan).</li>
                                    <li>Jalankan Shortcut sekali. Saat diminta, <b>izinkan akses Health</b> (Allow) untuk tiap data.</li>
                                    <li>Buka Shortcut &rarr; cari kotak teks berisi <code>PASTE_TOKEN_DISINI</code> &rarr; ganti dengan <b>Token</b> Anda di atas.</li>
                                    <li>Pada kotak teks berisi <code>PASTE_URL_DISINI</code> &rarr; ganti dengan <b>URL</b> di atas. Langkah ini penting karena alamat server tiap rumah sakit berbeda.</li>
                                    <li>Jalankan lagi. Jika muncul pesan <b>'Data wearable berhasil disimpan'</b>, koneksi sudah aktif.</li>
                                </ol>
                                <p><small><b>Token bersifat rahasia dan milik Anda pribadi.</b> Jangan bagikan ke orang lain, karena data akan tercatat atas nama Anda.</small></p>
                            </div>
                        </div>
                        <div class="card">
                            <div class="header">
                                <h2>2. Kirim Otomatis Tiap Hari <small>Opsional, agar tidak perlu menekan manual</small></h2>
                            </div>
                            <div class="body">
                                <ol>
                                    <li>Buka app <b>Shortcuts</b> &rarr; tab <b>Automation</b> (Otomasi).</li>
                                    <li>Tekan <b>+</b> &rarr; <b>Create Personal Automation</b>.</li>
                                    <li>Pilih <b>Time of Day</b>, atur jam (misalnya 07.00 pagi), set <b>Daily</b> (Harian).</li>
                                    <li>Tekan <b>Next</b> &rarr; <b>Add Action</b> &rarr; pilih <b>Run Shortcut</b> &rarr; pilih Shortcut wearable Anda.</li>
                                    <li>Matikan opsi <b>Ask Before Running</b> agar berjalan otomatis tanpa konfirmasi.</li>
                                </ol>
                                <p><small>Pastikan iPhone dalam keadaan menyala dan terhubung internet saat jadwal berjalan.</small></p>
                            </div>
                        </div>
                        <div class="card">
                            <div class="header">
                                <h2>3. Format Data <small>Untuk pengguna lanjutan / pengujian via Postman</small></h2>
                            </div>
                            <div class="body">
                                <ol>
                                    <li>Method <b>POST</b>, URL arahkan ke: <code><?="http://".$_SERVER['HTTP_HOST']."/epasien/service.php";?></code></li>
                                    <li>Tambah <b>Header</b> dengan key <code>token</code> berisi Token di atas.</li>
                                    <li>Request Body pilih <b>JSON</b>, isi dengan field <code>tanggal</code> dan variabel kesehatan.</li>
                                    <li>Field <code>tanggal</code> <b>wajib</b> dengan format <code>yyyy-mm-dd H:i:s</code>. Variabel lain opsional, kirim hanya yang tersedia.</li>
                                </ol>
                                <p><b>Contoh isi Body (JSON):</b></p>
<pre style="text-align:left;">
{
    "tanggal": "2026-06-13 06:30:00",
    "heartRate": 72,
    "oxygenSaturation": 98,
    "stepCount": 8543,
    "bodyMass": 68.5
}
</pre>
                                <p><small><b>Catatan:</b> nilai SpO2 (oxygenSaturation) kirim dalam persen (contoh 98), bukan pecahan 0&ndash;1.</small></p>
                            </div>
                        </div>
                        <div class="card">
                            <div class="header">
                                <h2>4. Daftar Variabel yang Bisa Dikirim <small>Gunakan nama key persis seperti kolom 'Key JSON'</small></h2>
                            </div>
                            <div class="body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped table-hover dataTable">
                                        <thead>
                                            <tr>
                                                <th width="5%">No</th>
                                                <th>Variabel</th>
                                                <th>Key JSON</th>
                                                <th width="15%">Satuan</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr><td>1</td><td>Detak Jantung</td><td>heartRate</td><td>/min</td></tr>
                                            <tr><td>2</td><td>Detak Jantung Istirahat</td><td>restingHeartRate</td><td>/min</td></tr>
                                            <tr><td>3</td><td>Rata-rata HR Berjalan</td><td>walkingHeartRateAverage</td><td>/min</td></tr>
                                            <tr><td>4</td><td>Variabilitas Detak Jantung (HRV)</td><td>heartRateVariabilitySDNN</td><td>ms</td></tr>
                                            <tr><td>5</td><td>Pemulihan HR 1 Menit</td><td>heartRateRecoveryOneMinute</td><td>/min</td></tr>
                                            <tr><td>6</td><td>Beban Atrial Fibrillation</td><td>atrialFibrillationBurden</td><td>%</td></tr>
                                            <tr><td>7</td><td>Indeks Perfusi Perifer</td><td>peripheralPerfusionIndex</td><td>%</td></tr>
                                            <tr><td>8</td><td>Saturasi Oksigen (SpO2)</td><td>oxygenSaturation</td><td>%</td></tr>
                                            <tr><td>9</td><td>Hasil EKG</td><td>electrocardiogramType</td><td>mV</td></tr>
                                            <tr><td>10</td><td>VO2 Max</td><td>vo2Max</td><td>mL/min/kg</td></tr>
                                            <tr><td>11</td><td>Tekanan Darah Sistolik</td><td>bloodPressureSystolic</td><td>mm[Hg]</td></tr>
                                            <tr><td>12</td><td>Tekanan Darah Diastolik</td><td>bloodPressureDiastolic</td><td>mm[Hg]</td></tr>
                                            <tr><td>13</td><td>Laju Napas</td><td>respiratoryRate</td><td>/min</td></tr>
                                            <tr><td>14</td><td>Kapasitas Vital Paksa (FVC)</td><td>forcedVitalCapacity</td><td>L</td></tr>
                                            <tr><td>15</td><td>Volume Ekspirasi Paksa 1 Detik (FEV1)</td><td>forcedExpiratoryVolume1Second</td><td>L</td></tr>
                                            <tr><td>16</td><td>Arus Puncak Ekspirasi (PEF)</td><td>peakExpiratoryFlowRate</td><td>L/min</td></tr>
                                            <tr><td>17</td><td>Penggunaan Inhaler</td><td>inhalerUsage</td><td>{puff}</td></tr>
                                            <tr><td>18</td><td>Suhu Tubuh</td><td>bodyTemperature</td><td>Cel</td></tr>
                                            <tr><td>19</td><td>Suhu Basal Tubuh</td><td>basalBodyTemperature</td><td>Cel</td></tr>
                                            <tr><td>20</td><td>Suhu Pergelangan Saat Tidur</td><td>appleSleepingWristTemperature</td><td>Cel</td></tr>
                                            <tr><td>21</td><td>Berat Badan</td><td>bodyMass</td><td>kg</td></tr>
                                            <tr><td>22</td><td>Tinggi Badan</td><td>height</td><td>cm</td></tr>
                                            <tr><td>23</td><td>Indeks Massa Tubuh (BMI)</td><td>bodyMassIndex</td><td>kg/m2</td></tr>
                                            <tr><td>24</td><td>Persentase Lemak Tubuh</td><td>bodyFatPercentage</td><td>%</td></tr>
                                            <tr><td>25</td><td>Massa Tubuh Tanpa Lemak</td><td>leanBodyMass</td><td>kg</td></tr>
                                            <tr><td>26</td><td>Lingkar Pinggang</td><td>waistCircumference</td><td>cm</td></tr>
                                            <tr><td>27</td><td>Jumlah Langkah</td><td>stepCount</td><td>{steps}</td></tr>
                                            <tr><td>28</td><td>Jarak Jalan/Lari</td><td>distanceWalkingRunning</td><td>m</td></tr>
                                            <tr><td>29</td><td>Jarak Bersepeda</td><td>distanceCycling</td><td>m</td></tr>
                                            <tr><td>30</td><td>Jarak Berenang</td><td>distanceSwimming</td><td>m</td></tr>
                                            <tr><td>31</td><td>Kalori Aktif</td><td>activeEnergyBurned</td><td>kcal</td></tr>
                                            <tr><td>32</td><td>Kalori Basal</td><td>basalEnergyBurned</td><td>kcal</td></tr>
                                            <tr><td>33</td><td>Lantai Dinaiki</td><td>flightsClimbed</td><td>{flights}</td></tr>
                                            <tr><td>34</td><td>Waktu Olahraga</td><td>appleExerciseTime</td><td>min</td></tr>
                                            <tr><td>35</td><td>Waktu Berdiri</td><td>appleStandTime</td><td>min</td></tr>
                                            <tr><td>36</td><td>Jumlah Strok Renang</td><td>swimmingStrokeCount</td><td>{strokes}</td></tr>
                                            <tr><td>37</td><td>Jumlah Dorongan Kursi Roda</td><td>pushCount</td><td>{pushes}</td></tr>
                                            <tr><td>38</td><td>Kecepatan Lari</td><td>runningSpeed</td><td>m/s</td></tr>
                                            <tr><td>39</td><td>Daya Lari</td><td>runningPower</td><td>W</td></tr>
                                            <tr><td>40</td><td>Panjang Langkah Lari</td><td>runningStrideLength</td><td>m</td></tr>
                                            <tr><td>41</td><td>Osilasi Vertikal Lari</td><td>runningVerticalOscillation</td><td>cm</td></tr>
                                            <tr><td>42</td><td>Waktu Kontak Tanah</td><td>runningGroundContactTime</td><td>ms</td></tr>
                                            <tr><td>43</td><td>Glukosa Darah</td><td>bloodGlucose</td><td>mg/dL</td></tr>
                                            <tr><td>44</td><td>Pemberian Insulin</td><td>insulinDelivery</td><td>[iU]</td></tr>
                                            <tr><td>45</td><td>Kadar Alkohol Darah</td><td>bloodAlcoholContent</td><td>%</td></tr>
                                            <tr><td>46</td><td>Analisis Tidur</td><td>sleepAnalysis</td><td>-</td></tr>
                                            <tr><td>47</td><td>Sesi Mindful</td><td>mindfulSession</td><td>s</td></tr>
                                            <tr><td>48</td><td>Jumlah Jatuh</td><td>numberOfTimesFallen</td><td>{falls}</td></tr>
                                            <tr><td>49</td><td>Notifikasi HR Tinggi</td><td>highHeartRateEvent</td><td>-</td></tr>
                                            <tr><td>50</td><td>Notifikasi HR Rendah</td><td>lowHeartRateEvent</td><td>-</td></tr>
                                            <tr><td>51</td><td>Notifikasi Irama Tidak Teratur</td><td>irregularHeartRhythmEvent</td><td>-</td></tr>
                                            <tr><td>52</td><td>Paparan Suara Lingkungan</td><td>environmentalAudioExposure</td><td>dB[SPL]</td></tr>
                                            <tr><td>53</td><td>Paparan Suara Headphone</td><td>headphoneAudioExposure</td><td>dB[SPL]</td></tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div role="tabpanel" <?=(isset($_GET['iyem'])?"class='tab-pane fade in active'":"class='tab-pane fade'")?> id="riwayat">
                        <br/>
                        <div class="alert alert-info">
                            <small>Kolom Keterangan hanyalah indikasi dari perangkat pribadi, bukan diagnosis medis. Nilai dari smartwatch atau wearable tidak setara dengan alat medis. Bila ada keterangan yang menyarankan ke faskes, tetap konsultasikan ke tenaga kesehatan dan jangan panik atas satu kali pembacaan.</small>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th width="14%"><center>Tanggal</center></th>
                                        <th width="22%"><center>Variabel</center></th>
                                        <th width="9%"><center>Nilai</center></th>
                                        <th width="9%"><center>Satuan</center></th>
                                        <th width="39%"><center>Keterangan</center></th>
                                        <th width="7%"><center>Hapus</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php 
                                    $queryriwayat = bukaquery("select pasien_wearable.tanggal,pasien_wearable.item,pasien_wearable.nilai,pasien_wearable.satuan,pasien_wearable.status from pasien_wearable where pasien_wearable.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."' order by pasien_wearable.tanggal desc limit 1000");
                                    while($rsqueryriwayat = mysqli_fetch_array($queryriwayat)) {
                                        $keterangan = $rsqueryriwayat["status"];
                                        if($keterangan===null){
                                            $keterangan = "";
                                        }
                                        if(strpos($keterangan,"faskes")!==false || strpos($keterangan,"konsultasikan")!==false || strpos($keterangan,"Konsultasikan")!==false){
                                            $keterangan = "<span class='col-red'><b>".$keterangan."</b></span>";
                                        }else if($keterangan==""){
                                            $keterangan = "-";
                                        }
                                        echo "<tr>
                                                <td align='center' valign='middle'>".$rsqueryriwayat["tanggal"]."</td>
                                                <td align='left' valign='middle'>".$rsqueryriwayat["item"]."</td>
                                                <td align='center' valign='middle'>".$rsqueryriwayat["nilai"]."</td>
                                                <td align='center' valign='middle'>".$rsqueryriwayat["satuan"]."</td>
                                                <td align='left' valign='middle'>".$keterangan."</td>
                                                <td align='center' valign='middle'><a href='index.php?act=KoneksiWearable&iyem=".encrypt_decrypt("{\"tanggalhapus\":\"".$rsqueryriwayat["tanggal"]."\",\"itemhapus\":\"".$rsqueryriwayat["item"]."\"}","e")."' onclick='return confirm(\"Yakin menghapus data ini?\")' class='btn btn-warning waves-effect'>Hapus</a></td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>