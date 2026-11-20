<?php
    if(strpos(parse_url($_SERVER['REQUEST_URI'],PHP_URL_PATH),"/pages/")!==false){
        exit(header("Location:../index.php"));
    }

    // pelindung output HTML
    $e = function($v){
        return htmlspecialchars((string)$v, ENT_QUOTES|ENT_SUBSTITUTE, 'UTF-8');
    };

    $namars        = getOne("select setting.nama_instansi from setting");
    $tanggal       = "";
    $norawat       = "";

    // antrian : diisi dari SIMRS (form Penandaan Lokasi Luka -> tab Persetujuan -> tombol Ambil)
    $_sql          = "select antripenandaanlokasiluka.no_rawat,antripenandaanlokasiluka.tanggal from antripenandaanlokasiluka limit 1";
    $hasil         = bukaquery2($_sql);
    while ($data = mysqli_fetch_array ($hasil)){
        $tanggal  = $data['tanggal'];
        $norawat  = $data['no_rawat'];
    }

    $no_rkm_medis = "";
    $nm_pasien    = "";
    $jk           = "";
    $umur         = "";
    $tgl_lahir    = "";
    $alamat       = "";
    $no_tlp       = "";

    $_sql2  = "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','LAKI-LAKI','PEREMPUAN') as jk,
               pasien.umur,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as tgl_lahir,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,
               pasien.no_tlp from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis
               inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel
               inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec
               inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab
               where reg_periksa.no_rawat='".$norawat."'" ;
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $no_rkm_medis = $data2['no_rkm_medis'];
        $nm_pasien    = $data2['nm_pasien'];
        $jk           = $data2['jk'];
        $umur         = $data2['umur'];
        $tgl_lahir    = $data2['tgl_lahir'];
        $alamat       = $data2['alamat'];
        $no_tlp       = $data2['no_tlp'];
    }

    // data formulir penandaan lokasi luka
    $formAda       = false;
    $tanggalform   = "";
    $tindakan      = "";
    $nama_wali     = "";
    $nm_dokter     = "";
    $_sql3  = "select DATE_FORMAT(penandaan_lokasi_luka.tanggal,'%d-%m-%Y') as tanggalform,penandaan_lokasi_luka.tindakan_bedah,penandaan_lokasi_luka.nama_wali,dokter.nm_dokter ".
              "from penandaan_lokasi_luka inner join dokter on penandaan_lokasi_luka.kd_dokter=dokter.kd_dokter ".
              "where penandaan_lokasi_luka.tanggal='$tanggal' and penandaan_lokasi_luka.no_rawat='$norawat'" ;
    $hasil3 = bukaquery2($_sql3);
    while ($data3  = mysqli_fetch_array ($hasil3)){
        $formAda     = true;
        $tanggalform = $data3['tanggalform'];
        $tindakan    = $data3['tindakan_bedah'];
        $nama_wali   = $data3['nama_wali'];
        $nm_dokter   = $data3['nm_dokter'];
    }

    // coretan spidol per gambar (hanya kode gambar yang dikenal yang diteruskan ke halaman)
    $daftarGambar = array(
        'TUBUH_PRIA'            => array('Tubuh Laki-laki',        'lokasi_luka_tubuh_pria.png'),
        'TUBUH_WANITA'          => array('Tubuh Perempuan',        'lokasi_luka_tubuh_wanita.png'),
        'KEPALA_DEPAN_BELAKANG' => array('Kepala Depan/Belakang',  'lokasi_luka_kepala_depan_belakang.png'),
        'KEPALA_SAMPING'        => array('Kepala Samping',         'lokasi_luka_kepala_samping.png'),
        'TELAPAK_TANGAN'        => array('Telapak Tangan',         'lokasi_luka_telapak_tangan.png'),
        'PUNGGUNG_TANGAN'       => array('Punggung Tangan',        'lokasi_luka_punggung_tangan.png'),
        'TELAPAK_KAKI'          => array('Telapak Kaki',           'lokasi_luka_telapak_kaki.png'),
        'PUNGGUNG_KAKI'         => array('Punggung Kaki',          'lokasi_luka_punggung_kaki.png')
    );
    $dataGoresan = array();
    if($formAda){
        $_sql4  = "select penandaan_lokasi_luka_goresan.gambar,penandaan_lokasi_luka_goresan.goresan from penandaan_lokasi_luka_goresan ".
                  "where penandaan_lokasi_luka_goresan.tanggal='$tanggal' and penandaan_lokasi_luka_goresan.no_rawat='$norawat' order by penandaan_lokasi_luka_goresan.gambar" ;
        $hasil4 = bukaquery2($_sql4);
        while ($data4  = mysqli_fetch_array ($hasil4)){
            if(isset($daftarGambar[$data4['gambar']]) && trim($data4['goresan'])!=""){
                $dataGoresan[] = array(
                    'nama'    => $daftarGambar[$data4['gambar']][0],
                    'file'    => $daftarGambar[$data4['gambar']][1],
                    'goresan' => $data4['goresan']
                );
            }
        }
    }

    $jsonGoresan = json_encode($dataGoresan, JSON_HEX_TAG|JSON_HEX_AMP|JSON_HEX_APOS|JSON_HEX_QUOT);
    if($jsonGoresan===false){
        $jsonGoresan = "[]";
    }

    // bukti yang sudah pernah diambil untuk data ini
    $sudahAda = false;
    if($formAda){
        $sudahAda = (getOne("select count(*) from bukti_penandaan_lokasi_luka where bukti_penandaan_lokasi_luka.tanggal='$tanggal' and bukti_penandaan_lokasi_luka.no_rawat='$norawat'")>0);
    }
?>

<link rel="stylesheet" href="css/bootstrap.min.css" />
<style type="text/css">
    #results { padding: 0px; background:#EEFFEE; width: 490px; height: 390px; max-width: 100%; }
    #results img { max-width: 100%; height: auto; }
    #my_camera { max-width: 100%; }
    .kotak-gambar canvas { max-width: 100%; height: auto; background: #FFFFFF; }
</style>
<script src="js/jquery.min.js"></script>
<script src="js/webcam.min.js"></script>

<div class="container">
    <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>PERSETUJUAN PENANDAAN LOKASI LUKA / OPERASI<?php if($norawat!=""){ ?> <br>NO.RAWAT <?=$e($norawat);?><?php } ?></center></h5>

<?php if(!$formAda || $nm_pasien==""){ ?>
    <div class="alert alert-warning" role="alert">
        <center>Belum ada data penandaan lokasi luka yang dikirim dari SIMRS. Silahkan petugas memilih data pada form Penandaan Lokasi Luka (tab Persetujuan) lalu klik Ambil, kemudian klik Refresh di halaman ini.</center>
    </div>
<?php }elseif($sudahAda){ ?>
    <div class="alert alert-success" role="alert">
        <center>Bukti persetujuan untuk data ini sudah tersimpan. Terima kasih.<br>Jika perlu mengulang, petugas dapat klik Ambil kembali di SIMRS lalu klik Refresh di halaman ini.</center>
    </div>
<?php }else{ ?>
    <h7 class="text-dark"><center>Tanggal <?=$e($tanggalform);?></center></h7><br/>
    <form method="POST" action="pages/storeImage.php" onsubmit="return validasiFoto();" enctype=multipart/form-data>
        <input type="hidden" name="tanggal" value="<?=$e($tanggal);?>">
        <input type="hidden" name="norawat" value="<?=$e($norawat);?>">
        <h7 class="text-dark">
            Saya yang dibawah ini :
        </h7>
        <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
            <tr class="text-dark">
                <td width="25%">Pasien / Yang Mewakili</td>
                <td width="70%">: <?=$e($nama_wali!=""?$nama_wali:"-");?></td>
            </tr>
        </table>
        <br/>
        <h7 class="text-dark">
            Dari pasien <?=$e($namars)?> dengan :
        </h7>
        <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
            <tr class="text-dark">
                <td width="25%">Nama Pasien</td>
                <td width="70%">: <?=$e($nm_pasien);?></td>
            </tr>
            <tr class="text-dark">
                <td width="25%">Nomor Rekam Medis</td>
                <td width="75%">: <?=$e($no_rkm_medis);?></td>
            </tr>
            <tr class="text-dark">
                <td width="25%">Jenis Kelamin</td>
                <td width="75%">: <?=$e($jk);?></td>
            </tr>
            <tr class="text-dark">
                <td width="25%">Tanggal Lahir</td>
                <td width="75%">: <?=$e($tgl_lahir);?></td>
            </tr>
        </table>
        <br/>
        <h7 class="text-dark">
            Untuk tindakan medis/pembedahan :
        </h7>
        <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
            <tr class="text-dark">
                <td width="25%">Tindakan Bedah</td>
                <td width="70%">: <?=$e($tindakan);?></td>
            </tr>
            <tr class="text-dark">
                <td width="25%">Dokter Bedah</td>
                <td width="75%">: <?=$e($nm_dokter);?></td>
            </tr>
        </table>
        <br/>
        <h7 class="text-dark">
            Lokasi yang ditandai :
        </h7>
        <div class="row kotak-gambar" id="gambarTanda"></div>
        <?php if(count($dataGoresan)==0){ ?>
            <div class="text-danger"><center>Belum ada lokasi yang ditandai pada gambar.</center></div>
        <?php } ?>
        <br/>
        <h7 class="text-dark">
            Saya menyatakan bahwa tindakan medis/pembedahan yang dilakukan sesuai dengan gambar yang ditandai di atas. Demikian pernyataan ini dibuat dalam keadaan penuh kesadaran untuk digunakan sebagaimana mestinya
        </h7>
        <br/>
        <br/>
        <h7 class="text-dark"><center>Yang Membuat Pernyataan</center></h7>
        <div class="row">
            <div class="col-md-6">
                <div id="my_camera"></div>
                <input type="hidden" name="image" class="image-tag" id="TxtIsi1">
            </div>
            <div class="col-md-6">
                <div id="results"><h7 class="text-success"><center>Gambar akan diambil jika anda sudah mengeklik ya</center></h7></div>
                <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
            </div>
            <div class="col-md-12 text-center">
                <br>
                <span id="pesanKamera" class="text-danger"></span><br>
                <input type="button" class="btn btn-warning" value="Ya, Saya sebagai pembuat pernyataan" onClick="take_snapshot()">
                <button class="btn btn-danger" id="BtnSimpan">Simpan</button>
            </div>
        </div>
    </form>

    <script language="JavaScript">
        var DATA_GORESAN = <?=$jsonGoresan;?>;

        Webcam.set({
            width: 490,
            height: 390,
            image_format: 'jpeg',
            jpeg_quality: 90
        });

        Webcam.on('error', function(err) {
            document.getElementById('pesanKamera').innerHTML = 'Kamera tidak dapat dibuka. Pastikan izin kamera diberikan pada browser.';
        });

        Webcam.attach( '#my_camera' );

        function take_snapshot() {
            Webcam.snap( function(data_uri) {
                $(".image-tag").val(data_uri);
                document.getElementById('results').innerHTML = '<img src="'+data_uri+'"/>';
                document.getElementById('MsgIsi1').innerHTML = '';
            } );
        }

        // foto wajib diambil dulu; cegah klik Simpan dua kali
        function validasiFoto() {
            if ($("#TxtIsi1").val().length == 0) {
                document.getElementById('MsgIsi1').innerHTML = 'Silahkan klik tombol "Ya, Saya sebagai pembuat pernyataan" terlebih dahulu';
                return false;
            }
            document.getElementById('BtnSimpan').disabled = true;
            return true;
        }

        // pratinjau gambar yang ditandai (sama dengan yang digambar di SIMRS)
        (function() {
            var BANGUN = 380;                       // lebar acuan ketebalan spidol, sama dengan SIMRS
            var kotak  = document.getElementById('gambarTanda');
            if (!kotak) { return; }
            DATA_GORESAN.forEach(function(item) {
                var kol = document.createElement('div');
                kol.className = 'col-md-6 text-center mb-2';
                var cap = document.createElement('div');
                cap.className = 'text-dark';
                cap.style.fontSize = '12px';
                cap.textContent = item.nama;
                var cv = document.createElement('canvas');
                cv.style.border = '1px solid #999999';
                kol.appendChild(cap);
                kol.appendChild(cv);
                kotak.appendChild(kol);
                var im = new Image();
                im.onload = function() {
                    var w = BANGUN, h = Math.round(BANGUN * im.naturalHeight / im.naturalWidth);
                    cv.width = w; cv.height = h;
                    var g = cv.getContext('2d');
                    g.fillStyle = '#FFFFFF';
                    g.fillRect(0, 0, w, h);
                    g.drawImage(im, 0, 0, w, h);
                    g.lineCap = 'round';
                    g.lineJoin = 'round';
                    String(item.goresan).split('|').forEach(function(st) {
                        var c = st.indexOf(':');
                        if (c < 0) { return; }
                        var kepala = st.substring(0, c).split(',');
                        if (!/^[0-9A-Fa-f]{6}$/.test(kepala[0])) { return; }
                        var warna = '#' + kepala[0];
                        var tebal = Math.max(1.2, parseFloat(kepala[1]) * w / BANGUN);
                        if (isNaN(tebal)) { return; }
                        var titik = st.substring(c + 1).split(';').map(function(p) {
                            var xy = p.split(',');
                            return [parseFloat(xy[0]) * w, parseFloat(xy[1]) * h];
                        }).filter(function(p) { return !isNaN(p[0]) && !isNaN(p[1]); });
                        if (titik.length == 0) { return; }
                        g.strokeStyle = warna;
                        g.fillStyle = warna;
                        g.lineWidth = tebal;
                        if (titik.length == 1) {
                            g.beginPath();
                            g.arc(titik[0][0], titik[0][1], tebal / 2, 0, 2 * Math.PI);
                            g.fill();
                            return;
                        }
                        g.beginPath();
                        g.moveTo(titik[0][0], titik[0][1]);
                        for (var k = 1; k < titik.length; k++) { g.lineTo(titik[k][0], titik[k][1]); }
                        g.stroke();
                    });
                };
                im.src = 'images/' + item.file;
            });
        })();
    </script>
<?php } ?>
</div>
