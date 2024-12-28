<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $namars       = getOne("select setting.nama_instansi from setting");
    $nosurat      = "";
    $norawat      = "";
    
    $_sql         = "select * from antripersetujuanumum" ;  
    $hasil        = bukaquery2($_sql);
    while ($data = mysqli_fetch_array ($hasil)){
        $nosurat  = $data['no_surat'];
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
    
    $tanggal        = "";
    $nama_pj        = "";
    $umur_pj        = "";
    $no_ktppj       = "";
    $jkpj           = "";
    $bertindak_atas = "";
    $no_telp        = "";
    $_sql2  = "select DATE_FORMAT(surat_persetujuan_umum.tanggal,'%d-%m-%Y') as tanggal,surat_persetujuan_umum.nama_pj,surat_persetujuan_umum.umur_pj,
               surat_persetujuan_umum.no_ktppj,if(surat_persetujuan_umum.jkpj='L','LAKI-LAKI','PEREMPUAN') as jkpj,surat_persetujuan_umum.bertindak_atas,
               surat_persetujuan_umum.no_telp from surat_persetujuan_umum where surat_persetujuan_umum.no_surat='$nosurat'" ;  
    $hasil2 = bukaquery2($_sql2);
    while ($data2  = mysqli_fetch_array ($hasil2)){
        $tanggal        = $data2['tanggal'];
        $nama_pj        = $data2['nama_pj'];
        $umur_pj        = $data2['umur_pj'];
        $no_ktppj       = $data2['no_ktppj'];
        $jkpj           = $data2['jkpj'];
        $bertindak_atas = $data2['bertindak_atas'];
        $no_telp        = $data2['no_telp'];
    }
?>

<!DOCTYPE html>
<html>
<head>
    <title>SIMKES Khanza</title>
    <script src="js/jquery.min.js"></script>
    <script src="js/webcam.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <style type="text/css">
        #results { padding: 0px; background:#EEFFEE; width: 490; height: 390 }
		
		
		
/* Tambahan heading style */
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        li {
            margin-left: 20px; /* Adjust the indentation as needed */
        }
		
		ol {
			margin-bottom:0;
			
		}
    
    </style>
</head>
<body>
    <div class="container">
        <h5 class="text-dark"><center><button class="btn btn-secondary" onclick="window.location.reload();">Refresh</button><br/><br/>KETENTUAN RAWAT INAP DAN PERSETUJUAN UMUM (GENERAL CONSENT)<br>NO. <?=$nosurat;?></center></h5>
        <h7 class="text-dark"><center>Tanggal <?=$tanggal;?></center></h7><br/>
        <form method="POST" action="pages/storeImage.php" onsubmit="return validasiIsi();" enctype=multipart/form-data>
            <input type="hidden" name="nosurat" value="<?=$nosurat;?>">
            <h7 class="text-dark">
                <b>I. KETENTUAN RAWAT INAP</b>
            </h7>
            <table class="default" width="99%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="100%">
                        <b>A. Tarif Ruang Perawatan</b><br>
                        <table class="default" width="99%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                            <tr class="text-dark">
                                <td width="30%">Kelas 3</td><td width="20%">Rp. 130.000</td><td width="30%">VIP B</td><td width="20%">Rp. 390.000</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="30%">Kelas 2</td><td width="20%">Rp. 195.000</td><td width="30%">VIP A</td><td width="20%">Rp. 520.000</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="30%">Kelas 1</td><td width="20%">Rp. 260.000</td><td width="30%">VVIP</td><td width="20%">Rp. 750.000</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <!--
				<tr class="text-dark">
                    <td width="100%">
                        <b>B. Jumlah Tempat Tidur</b><br>
                        <table class="default" width="99%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                        <?php
                            $_sql="Select kamar.kelas,count(kamar.kelas) as jumlah from kamar group by kamar.kelas" ;  
                            $hasil=bukaquery($_sql);
                            while ($data = mysqli_fetch_array ($hasil)){
                                echo "<tr class='text-dark'>
                                        <td width='50%'>Ruang Perawatan ".$data['kelas']."</td><td width='50%' align='center'>Jumlah Bed ".$data['jumlah']."</td>
                                      </tr>";
                            }
                        ?>
                        </table>
                    </td>
                </tr>
				-->
                <tr class="text-dark">
                    <td width="100%">
                        <b>B. Angsuran Awal</b><br>
                        <table class="default" width="99%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                            <tr class="text-dark">
                                <td width="30%">Kelas 3</td><td width="20%">Rp. 1.000.000</td><td width="30%">VIP B</td><td width="20%">Rp. 2.000.000</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="30%">Kelas 2</td><td width="20%">Rp. 1.250.000</td><td width="30%">VVP A</td><td width="20%">Rp. 2.500.000</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="30%">Kelas 1</td><td width="20%">Rp. 1.500.000</td><td width="30%">VVIP</td><td width="20%">Rp. 3.000.000</td>
                            </tr>
                        </table>
                    </td>
                </tr>
				
				<tr class="text-dark">
                    <td width="100%">
                        <b>C. Peraturan Pembayaran Angsuran Rawat Inap</b><br>
                         <table class="default" width="99%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                            <tr class="text-dark">
                                <td width="2%" valign="top">1.</td><td width="98%">Pembayaran angsuran awal maksimal 1 x 24 Jam</td>
                            </tr>                            
                        </table>
                    </td>
                </tr>
                
            </table>
            <h7 class="text-dark">
                <b>II. INFORMASI HAK DAN KEWAJIBAN PASIEN</b>
            </h7>
            <table class="default" width="99%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="100%">
                        A. Hak-hak Pasien yang dimaksud adalah hak-hak pasien sebagaimana yang diatur di dalam Undang-undang Nomor 44 Tahun 2009 tentang Rumah Sakit (Pasal 32 UU 44/2009), menebutkan bahwa setiap pasien mempunyai hak sebagai berikut :<br>
                        <table class="default" width="99%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                            <tr class="text-dark">
                                <td width="2%" valign="top">1.</td><td width="98%">Memperoleh informasi mengenai tata tertib dan peraturan yang berlaku di Rumah Sakit;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">2.</td><td width="98%">Memperoleh informasi tentang hak dan kewajiban pasien;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">3.</td><td width="98%">Memperoleh layanan yang manusiawi, adil, jujur, dan tanpa diskriminasi;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">4.</td><td width="98%">Memperoleh layanan kesehatan yang bermutu sesuai dengan kebutuhan medis, standar profesi dan standar prosedur operasional;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">5.</td><td width="98%">Memperoleh layanan yang efektif dan efisien sehingga pasien terhindar dari kerugian fisik dan materi;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">6.</td><td width="98%">Mengajukan pengaduan atas kualitas pelayanan yang didapatkan;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">7.</td><td width="98%">Memilih dokter dan kelas perawatan sesuai dengan keinginannya dan peraturan yang berlaku di Rumah Sakit;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">8.</td><td width="98%">Meminta konsultasi tentang penyakit yang dideritanya kepada dokter lain (second opinion) yang mempunyai Surat Izin Praktik (SIP) baik di dalam maupun di luar Rumah Sakit;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">9.</td><td width="98%">Mendapatkan privasi dan kerahasiaan penyakit yang diderita termasuk data-data medisnya (isi rekam medis);</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">10.</td><td width="98%">Mendapat informasi yang meliputi diagnosis dan tata cara tindakan medis, tujuan tindakan medis, alternarif tindakan, risiko dan komplikasi yang mungkin terjadi, dan prognosis terhadap tindakan yang dilakukan serta perkiraan biaya pengobatan/tindakan medis yang akan dilakukan terhadap dirinya;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">11.</td><td width="98%">Memberikan persetujuan atau menolak sebagian atau seluruh tindakan yang akan diberikan ole tenaga kesehatan terhadap penyakit yang dideritanya setelah menerima dan memahami informasi mengenai tindakan tersebut secara lengkap dengan pengecualian yang diatur dalam ketentuan peraturan perundang-undangan;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">12.</td><td width="98%">Didampingi keluarganya dan atau penasehatnya dalam keadaan kritis atau menjelang kematian;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">13.</td><td width="98%">Menjalankan ibadah sesuai agama atau kepercayaan yang dianutnya selama hal itu tidak mengganggu pasien lainnya;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">14.</td><td width="98%">Memperoleh keamanan dan keselamatan dirinya selama dalam perawatan di Rumah Sakit;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">15.</td><td width="98%">Mengajukan usul, saran, perbaikan atas perlakuan Rumah Sakit terhadap dirinya;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">16.</td><td width="98%">Menolak pelayanan bimbingan rohani yang tidak sesuai dengan agama dan kepercayaan yang dianutnya;</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">17.</td><td width="98%">Menggugat dan/atau menuntut Rumah Sakit apabila Rumah Sakit diduga memberikan pelayanan yang tidak sesual dengan standar baik secara perdata ataupun pidana; dan</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">18.</td><td width="98%">Mengeluhkan pelayanan Rumah Sakit yang tidak sesuai dengan standar pelayanan melalui media cetak dan elektronik sesuai dengan ketentuan peraturan perundang-undangan.</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr class="text-dark">
                    <td width="100%">
                        B. Kewajiban Pasien :<br>
                        <table class="default" width="99%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                            <tr class="text-dark">
                                <td width="2%" valign="top">1.</td><td width="98%">Mematuhi peraturan yang berlaku di <?=$namars?></td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">2.</td><td width="98%">Menggunakan fasilitas Rumah Sakit secara bertanggung jawab</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">3.</td><td width="98%">Menghormati hak-hak pasien, pengunjung dan tenaga kesehatan serta petugas lainnya yang bekerja di Rumah Sakit</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">4.</td><td width="98%">Memberikan informasi yang jujur, lengkap dan akurat sesuai kemampuan dan pengetahuannya tentang masalah kesehatan</td>
                            </tr>
                            <tr class="text-dark">
                                <td width="2%" valign="top">5.</td><td width="98%">Memberikan informasi mengenai kemampuan finansial dan jaminan kesehatan yang dimilikinya</td>
                            </tr>
							<tr class="text-dark">
                                <td width="2%" valign="top">6.</td><td width="98%">Mematuhi rencana terapi yang direkomendasikan oleh tenaga kesehatan di Rumah Sakit dan disetujui oleh pasien yang bersangkutan setelah mendapatkan penjelasan sesuai peraturan perundang-undangan</td>
                            </tr>
							<tr class="text-dark">
                                <td width="2%" valign="top">7.</td><td width="98%">Menerima segala konsekuensi atas keputusan pribadinya untuk menolak rencana terapi yang direkomendasikan oleh tenaga kesehatan dan / atau tidak mematuhi petunjuk yang diberikan oleh tenaga kesehatan dalam rangka penyembuhan penyakit atau masalah kesehatannya</td>
                            </tr>							
							<tr class="text-dark">
                                <td width="2%" valign="top">8.</td><td width="98%">Memberikan imbalan jasa atas pelayanan yang diterima</td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <h7 class="text-dark">
                <b>III. PERSETUJUAN UMUM</b>
            </h7>
			<table class="default" width="99%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="2%" valign="top">1.</td><td width="98%">Persetujuan untuk perawatan dan pengobatan <br>																		
    																	<ol type="a">
      																		<li>Saya mengetahui bahwa saya memiliki kondisi yang membutuhkan perawatan medis, saya memberi izin kepada dokter dan profesi kesehatan lainnya untuk melakukan prosedur diagnostik dan untuk memberikan pengobatan medis seperti yang diperlukan untuk penilaian secara profesional, Prosedur diagnostik dan perawatan terapi tidak terbatas pada ECG, X-Ray, test darah dan pemberian obat</li>
																			<li>Saya sadar bahwa praktek kedokteran dan ilmu bedah bukanlah ilmu pasti dan saya mengakui bahwa tidak ada jaminan atas hasil apapun terhadap prosedur atau pemeriksaan apapun yang dilakukan kepada saya</li>
																			<li>Saya mengerti dan memahami bahwa :</li>
																				<ol type="1">
																					<li>Saya memiliki hak utnuk menanyakan tentang pengobatan yagn diusulkan termasuk identitas setiap orang yang memberikan atau mengamati pengobatan setiap saat</li>        																		
																					<li>Saya memiliki hak untuk persetujuan / menolak persetujuan untuk prosedur / terapi</li>
																					<li>Banyak dokter pada staff medis rumah sakit yang bukan karyawan tetapi sebagai staff tamu yang telah diberikan hak untuk menggunakan fasilitas perawatan dan pengobatan pasien mereka</li>
																				</ol>
    																	</ol>
													   </td>
                </tr>
                <tr class="text-dark">
                    <td width="2%" valign="top">2.</td><td width="98%">Barang-barang milik pasien <br>
																	<ol type="a"> 
																		<li>Saya telah memahami bahwa rumah sakit tidak bertanggung jawab atas kehilangan barang-barang milik saya, dan saya secara pribadi bertanggung jawab terhadap barang-barang berharga yang saya milikik di antaranya uang, perhiasan, buku, cek, handhone, kartu kredit, serta barang lainnya. Dan apabila saya membutuhkan maka saya dapat menitipkan barang-barang saya kepada rumah sakit.</li>
																		<li>Saya juga mengerti bahwa saya harus memberitahu / menitipkan pada rumah sakit jikas aya memiliki gigi palsu, kaca mata, lensa kontak, prosthetik, atau barang lainnya yang perlu diamankan.</li>
																	</ol>
													   </td>
                </tr>
                <tr class="text-dark">
                    <td width="2%" valign="top">3.</td><td width="98%">Persetujuan pelepasan informasi medis <br>
																	<ol type="a"> 
																		<li>Saya memahami informasi kesehatan yang ada dalam diri saya, termasuk diagnosa, hasil laboratorium dan hasil test diagnostik lainnya yang akan digunakan untuk perawatan medis saya dan rumah sakit akan menjamin kerahasiaannya.</li>
																		<li>Saya memberi wewenang kepada rumah sakit untuk memberikan informasi tentang diagnosa, hasil pelayanan nkesehatan dan pengobtaan saya bila diperlukan untuk memproses klaim Asuransi/BPJS/Perusahaan/Perorangan atau lembaga lain yang bertanggung jawab atas biaya pelayanan kesehatan saya atau lembaga pemerintah yang berwenang</li>
																	</ol>
													   </td>
                </tr>
                <tr class="text-dark">
                    <td width="2%" valign="top">4.</td><td width="98%">Informasi Rawat Inap <br>
																	<ol type="a"> 
																		<li>Saya telah menerima informasi tentang peraturan yang diberlakukan di <?=$namars?> dan saya beserta keluarga bersedia untuk mematuhi jam berkunjung pasien sesuai dengan aturan <?=$namars?></li>
																		<li>Saya telah menerima informasi mengenai cara pembayaran pelayanan kesehatan dan ketentuan yang berlaku dalam rawat inap terkait observasi pasien di IGD bila ruangan penuh atau karena kondisi penyakit pasien, titipan kelas rawat dan pulang atas permintaan sendiri.</li>
																	</ol>
												  </td>
                </tr>                
                <tr class="text-dark">
                    <td width="2%" valign="top">5.</td><td width="98%">Saya memberikan persetujuan dan kuasa kepada <?=$namars?> untuk memberikan informasi tentang diagnosis, hasil pelayanan kesehatan dan pengobatan kepada <select name="pengobatan_kepada" class="text-dark"><option value='Suami'>Suami</option><option value='Istri'>Istri</option><option value='Anak'>Anak</option><option value='Ayah'>Ayah</option><option value='Ibu'>Ibu</option><option value='Saudara'>Saudara</option><option value='Keponakan'>Keponakan</option><option value='Adik'>Adik</option><option value='Kakak'>Kakak</option><option value='Orang Tua'>Orang Tua</option><option value='Diri Sendiri'>Diri Sendiri</option></select></td>
                </tr>
                <tr class="text-dark">
                    <td width="2%" valign="top">6.</td><td width="98%">Melalui dokumen ini saya sampaikan bahwa saya atau keluarga saya memiliki nilai kepercayaan dalam pengobatan atau perawatan yang antara lain adalah <input name="nilai_kepercayaan" type="text" id="TxtIsi1" size="40" maxlength="50" pattern="[a-zA-Z0-9, ./@_-]{1,50}" title=" a-zA-Z0-9, ./@_- (Maksimal 50 karakter)" autocomplete="off"></td>
                </tr>
                <tr class="text-dark">
                    <td width="2%" valign="top">7.</td><td width="98%">Melalui dokumen ini saya menegaskan kembali bahwa saya mempercayakan kepada semua tenaga kesehatan yang ada di <?=$namars?> untuk memberikan perawatan diagnostik dan terapi kepada saya sebagai pasien rawat inap, rawat jalan, maupun gawat darurat termasuk pemeriksaan penunjang yang dibutuhkan untuk pengobatan dan tindakan.</td>
                </tr>
            </table>
			
            <br/>
            <h7 class="text-dark">
                <b>IV. PERNYATAAN</b>
            </h7>
            <table class="default" width="98%" border="0" align="center" cellpadding="3px" cellspacing="0px">
                <tr class="text-dark">
                    <td width="100%" colspan='2'>Saya yang bertanda tangan dibawah ini :</td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nama</td>
                    <td width="70%">: <?=$nama_pj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Umur</td>
                    <td width="75%">: <?=$umur_pj." &nbsp;&nbsp;Jenis Kelamin : ".$jkpj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Identitas :</td>
                    <td width="75%">: <?=$no_ktppj;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Telp/Nomor HP</td>
                    <td width="75%">: <?=$no_telp;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Bertindak untuk dan atas nama</td>
                    <td width="75%">: <?=$bertindak_atas;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan='2'>&nbsp;</td>
                </tr>
                <tr class="text-dark">
                    <td width="100%" colspan='2'>Dari pasien <?=$namars?> dengan : </td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nama Pasien</td>
                    <td width="70%">: <?=$nm_pasien;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Nomor Rekam Medis</td>
                    <td width="75%">: <?=$no_rkm_medis;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Jenis Kelamin</td>
                    <td width="75%">: <?=$jk;?></td>
                </tr>
                <tr class="text-dark">
                    <td width="25%">Tanggal Lahir</td>
                    <td width="75%">: <?=$tgl_lahir;?></td>
                </tr>
            </table>
            <br/>
            <h7 class="text-dark">
                Bahwa saya telah membaca, memahami dan mendapatkan penjelasan tentang ketentuan yang berlaku di <?=$namars?>. Demikian pernyataan ini dibuat dalam keadaan penuh kesadaran dan tanpa paksaan dari pihak manapun, untuk digunakan sebagaimana mestinya
            </h7>
            <br/>
            <br/>
            <h7 class="text-dark"><center>Yang Membuat Pernyataan</center></h7>
            <div class="row">
                <div class="col-md-6">
                    <div id="my_camera"></div>
                    <input type="hidden" name="image" class="image-tag" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                </div>
                <div class="col-md-6">
                    <div id="results"><h7 class="text-success"><center>Gambar akan diambil jika anda sudah mengeklik ya</center></h7></div>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </div>
                <div class="col-md-12 text-center">
                    <br>
                    <input type="button" class="btn btn-warning" value="Ya, Saya sebagai pembuat pernyataan" onClick="take_snapshot()">
                    <button class="btn btn-danger">Simpan</button>
                </div>
            </div>
        </form>
    </div>
    
    <script language="JavaScript">
        Webcam.set({
            width: 490,
            height: 390,
            image_format: 'jpeg',
            jpeg_quality: 90
        });

        Webcam.attach( '#my_camera' );

        function take_snapshot() {
            Webcam.snap( function(data_uri) {
                $(".image-tag").val(data_uri);
                document.getElementById('results').innerHTML = '<img src="'+data_uri+'"/>';
            } );
        }
    </script>
</body>
</html>

