<!DOCTYPE html>
<html>
<head>
    <script src="js/jquery.min.js"></script>
    <script src="js/webcam.min.js"></script>
</head>
<body>
<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["nopersetujuan"])) {
        $nopersetujuan    = validTeks3($iyem["nopersetujuan"],20);
        $querypersetujuan = bukaquery(
            "select reg_periksa.umurdaftar,reg_periksa.sttsumur,surat_persetujuan_umum.tanggal,surat_persetujuan_umum.pengobatan_kepada,".
            "surat_persetujuan_umum.nilai_kepercayaan,surat_persetujuan_umum.nama_pj,surat_persetujuan_umum.umur_pj,surat_persetujuan_umum.no_ktppj,".
            "surat_persetujuan_umum.jkpj,surat_persetujuan_umum.bertindak_atas,surat_persetujuan_umum.no_telp from surat_persetujuan_umum ".
            "inner join reg_periksa on surat_persetujuan_umum.no_rawat=reg_periksa.no_rawat where surat_persetujuan_umum.no_surat='".$nopersetujuan."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>KETENTUAN RAWAT INAP DAN PERSETUJUAN UMUM<br>NO. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                                <h7><center>( Hanya bisa dilakukan di jaringan lokal ".$_SESSION["nama_instansi"]." )</center></h7>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopersetujuan' value='$nopersetujuan'>
                                    <h7>
                                        <b>I. KETENTUAN RAWAT INAP</b>
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='100%'>
                                                <b>A. Tarif Ruang Perawatan</b><br>
                                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                    <tr>
                                                        <td width='30%'>Kelas 3</td><td width='20%'>Rp. 200.000</td><td width='30%'>VIP</td><td width='20%'>Rp. 600.000</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='30%'>Kelas 2</td><td width='20%'>Rp. 300.000</td><td width='30%'>VVIP</td><td width='20%'>Rp. 800.000</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='30%'>Kelas 1</td><td width='20%'>Rp. 400.000</td><td width='30%'>Isolasi</td><td width='20%'>Rp. 500.000</td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width='100%'>
                                                <b>B. Jumlah Tempat Tidur</b><br>
                                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>";
            $_sql="Select kamar.kelas,count(kamar.kelas) as jumlah from kamar group by kamar.kelas" ;  
            $hasil=bukaquery($_sql);
            while ($data = mysqli_fetch_array ($hasil)){
                echo "                             <tr>
                                                        <td width='50%'>Ruang Perawatan ".$data['kelas']."</td><td width='50%' align='center'>Jumlah Bed ".$data['jumlah']."</td>
                                                    </tr>";
            }
            echo"                               </table>
                                         </td>
                                        </tr>
                                        <tr>
                                            <td width='100%'>
                                                <b>C. Angsuran Awal</b><br>
                                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                    <tr>
                                                        <td width='30%'>Kelas 3</td><td width='20%'>Rp. 600.000</td><td width='30%'>VIP</td><td width='20%'>Rp. 1.800.000</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='30%'>Kelas 2</td><td width='20%'>Rp. 900.000</td><td width='30%'>VVIP</td><td width='20%'>Rp. 2.400.000</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='30%'>Kelas 1</td><td width='20%'>Rp. 1.200.000</td><td width='30%'>Isolasi</td><td width='20%'>Rp. 1.500.000</td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width='100%'>
                                                <b>D. Peraturan Pembayaran Angsuran Awal Rawat Inap</b><br>
                                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                    <tr>
                                                        <td width='2%' valign='top'>1.</td><td width='98%'>Pembayaran Angsuran Awal besarnya nominal 50% dari biaya perawatan</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>2.</td><td width='98%'>Angsuran Awal Masuk dibayarkan saat pasien akan dirawat</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>3.</td><td width='98%'>Untuk biaya operasi sedang angsuran awal minimum adalah Rp. 5.000.000</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>4.</td><td width='98%'>Untuk biaya operasi besar angsuran awal minimum Rp. 8.000.000</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>5.</td><td width='98%'>Untuk operasi bedah syaraf angsuran awal Rp. 25.000.000</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>6.</td><td width='98%'>Bila dana yang didepositkan sudah habis maka pasien harus menambah angsuran kembali</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>7.</td><td width='98%'>Bila ada kelebihan angsuran awal di akhir perawatan kurang dari Rp. 250.000 maka bisa diambil secara tunai dibagian kasir saat pasien akan meninggalkan rumah sakit, jika lebih dari Rp. 250.000 maka pengembalian dana dilakukan dengan sistem transfer</td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width='100%'>
                                                <b>E. Penghitungan Hari Perawatan</b><br>
                                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                    <tr>
                                                        <td width='2%' valign='top'>1.</td><td width='98%'>Perhitungan hari perawatan dimulai pada saat pasien dinyatakan masuk rawat inap</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>2.</td><td width='98%'>Pasien masuk dan keluar rawat inap kurang dari 24 jam harga kamar dihitung 1 (satu) hari perawatan, dan jika lebih dari 24 jam maka akan dihitung jumlah hari berikutnya</td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width='100%'>
                                                <b>F. Tata Tertib Rawat Inap</b><br>
                                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                    <tr>
                                                        <td width='2%' valign='top'>1.</td><td width='98%'>
                                                            Jam Berkunjung Pasien<br>
                                                            &nbsp;- Rawat Inap : Siang Jam 11.00 - 13.00, Sore Jam 17.00 - 20.00<br>
                                                            &nbsp;- ICU/PICU/NICU : Siang Jam 11.00 - 12.00, Sore Jam 17.00 - 18.00
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>2.</td><td width='98%'>Pasien Rawat Inap <?=$namars?> hanya dapat ditunggu oleh 1 (satu) orang yang ditunjuk oleh pasien atau keluarga</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>3.</td><td width='98%'>Anak-anak dibawah usia 12 tahun tidak diperkenankan masuk area ruang perawatan untuk menghindari resiko yang mungkin terjadi karena daya tubuh yang masih rendah</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>4.</td><td width='98%'>Penunggu Pasien Rawat Inap wajib mengenakan kartu tunggu selama pasien dirawat ditukar dengan kartu identitas penunggu pasien. Kartu tunggu pasien tidak boleh hilang, jika terjadi kehilangan maka akan dikenakan denda sebesar Rp. 50.000</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>5.</td><td width='98%'>Pasien, penunggu dan pengunjung Pasien Rawat Inap dilarang membawa peralatan tidur, peralatan memasak, peralatan elektronik, senjata tajam, senjata api dan sejenisnya</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>6.</td><td width='98%'>Pengunjung harus ikut menjaga ketenangan, kenyamanan dan keamanan pasien selama berada di lingkungan <?=$namars?></td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>7.</td><td width='98%'>Pengunjung diwajibkan untuk membuang sampah pada tempat yang telah disediakan oleh <?=$namars?></td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>8.</td><td width='98%'>Pasien, Penunggu Pasien dan Pengunjung dianjurkan untuk tidak membawa / menyimpan uang dalam jumlah yang besar, perhiasan dan barang berharga lainnya. <?=$namars?> tidak bertanggung jawab untuk segala kerusakan atau kehilangan yang teriadi diseluruh area <?=$namars?></td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>9.</td><td width='98%'>Pengunjung dilarang merusak dan atau membawa pulang fasilitas atau peralatan milik <?=$namars?>. Segala jenis kerusakan yang timbul akibat dari kelalaian dan atau unsur kesengajaan yang mengakibatkan kerugian bagi pihak <?=$namars?> akan menjadi tanggung jawab pengunjung</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>10.</td><td width='98%'>Pengunjung dilarang merokok, minum-minuman keras dan berbuat asusila selama berada dilingkungan</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>11.</td><td width='98%'>Pengunjung Rawat Inap dilarang mengambil gambar / foto dengan kamera handphone / pocket / digital single lens reflex (DSLR) selama berada dilingkungan</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>12.</td><td width='98%'>Segera meninggalkan ruang perawatan bila jam kunjung telah berakhir.</td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width='100%'>
                                                <b>G. Tata Tertib Pasien Pulang</b><br>
                                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                    <tr>
                                                        <td width='2%' valign='top'>1.</td><td width='98%'>Pasien pulang harus atas sepengetahuan atau seijin dokter yang merawat</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>2.</td><td width='98%'>Di luar kotentuan tersebut dinyatakan pulang atas permintaan sendiri, jika demikian pasien atau Keluarga pasien diminta untuk menandatangani formulir pulang atas permintaan sendiri</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>3.</td><td width='98%'>Kartu tunggu pasien harap dikembalikan kepada petugas administrasi</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>4.</td><td width='98%'>
                                                            Jadwal pelayanan pasien pulang<br>
                                                            &nbsp;- Hari Senin - Jumat : 08.00 - 20.30 WIB<br>
                                                            &nbsp;- Hari Sabtu - Minggu : 08.00 - 16.30 WIB
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>5.</td><td width='98%'>Kwitansi pulang dapat diperoleh setelah pasien/keluarganya melunasi seluruh biaya perawatan <?=$namars?>. Pasien dikenakan biaya leges 5% dari total biaya</td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                    <h7>
                                        <b>II. INFORMASI HAK DAN KEWAJIBAN PASIEN</b>
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='100%'>
                                                A. Hak-hak Pasien yang dimaksud adalah hak-hak pasien sebagaimana yang diatur di dalam Undang-undang Nomor 44 Tahun 2009 tentang Rumah Sakit (Pasal 32 UU 44/2009), menebutkan bahwa setiap pasien mempunyai hak sebagai berikut :<br>
                                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                    <tr>
                                                        <td width='2%' valign='top'>1.</td><td width='98%'>Memperoleh informasi mengenai tata tertib dan peraturan yang berlaku di Rumah Sakit;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>2.</td><td width='98%'>Memperoleh informasi tentang hak dan kewajiban pasien;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>3.</td><td width='98%'>Memperoleh layanan yang manusiawi, adil, jujur, dan tanpa diskriminasi;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>4.</td><td width='98%'>Memperoleh layanan kesehatan yang bermutu sesuai dengan kebutuhan medis, standar profesi dan standar prosedur operasional;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>5.</td><td width='98%'>Memperoleh layanan yang efektif dan efisien sehingga pasien terhindar dari kerugian fisik dan materi;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>6.</td><td width='98%'>Mengajukan pengaduan atas kualitas pelayanan yang didapatkan;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>7.</td><td width='98%'>Memilih dokter dan kelas perawatan sesuai dengan keinginannya dan peraturan yang berlaku di Rumah Sakit;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>8.</td><td width='98%'>Meminta konsultasi tentang penyakit yang dideritanya kepada dokter lain (second opinion) yang mempunyai Surat Izin Praktik (SIP) baik di dalam maupun di luar Rumah Sakit;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>9.</td><td width='98%'>Mendapatkan privasi dan kerahasiaan penyakit yang diderita termasuk data-data medisnya (isi rekam medis);</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>10.</td><td width='98%'>Mendapat informasi yang meliputi diagnosis dan tata cara tindakan medis, tujuan tindakan medis, alternarif tindakan, risiko dan komplikasi yang mungkin terjadi, dan prognosis terhadap tindakan yang dilakukan serta perkiraan biaya pengobatan/tindakan medis yang akan dilakukan terhadap dirinya;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>11.</td><td width='98%'>Memberikan persetujuan atau menolak sebagian atau seluruh tindakan yang akan diberikan ole tenaga kesehatan terhadap penyakit yang dideritanya setelah menerima dan memahami informasi mengenai tindakan tersebut secara lengkap dengan pengecualian yang diatur dalam ketentuan peraturan perundang-undangan;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>12.</td><td width='98%'>Didampingi keluarganya dan atau penasehatnya dalam keadaan kritis atau menjelang kematian;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>13.</td><td width='98%'>Menjalankan ibadah sesuai agama atau kepercayaan yang dianutnya selama hal itu tidak mengganggu pasien lainnya;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>14.</td><td width='98%'>Memperoleh keamanan dan keselamatan dirinya selama dalam perawatan di Rumah Sakit;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>15.</td><td width='98%'>Mengajukan usul, saran, perbaikan atas perlakuan Rumah Sakit terhadap dirinya;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>16.</td><td width='98%'>Menolak pelayanan bimbingan rohani yang tidak sesuai dengan agama dan kepercayaan yang dianutnya;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>17.</td><td width='98%'>Menggugat dan/atau menuntut Rumah Sakit apabila Rumah Sakit diduga memberikan pelayanan yang tidak sesual dengan standar baik secara perdata ataupun pidana; dan</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>18.</td><td width='98%'>Mengeluhkan pelayanan Rumah Sakit yang tidak sesuai dengan standar pelayanan melalui media cetak dan elektronik sesuai dengan ketentuan peraturan perundang-undangan.</td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width='100%'>
                                                B. Kewajiban Pasien menurut UU No. 29 tahun 2004 pasal 53 adalah sebagai berikut :<br>
                                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                    <tr>
                                                        <td width='2%' valign='top'>1.</td><td width='98%'>Mentaati segala peraturan dan tata tertib di <?=$namars?></td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>2.</td><td width='98%'>Mematuhi segala instruksi Dokter dan Perawat dalam pengobatannya;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>3.</td><td width='98%'>Memberikan informasi dengan jujur dan selengkapnya tentang penyakit yang diderita kepada Dokter yang merawat;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>4.</td><td width='98%'>Melunasi semua imbalan atas jasa pelayanan Rumah Sakit dan/atau Dokter;</td>
                                                    </tr>
                                                    <tr>
                                                        <td width='2%' valign='top'>5.</td><td width='98%'>Mematuhi hal-hal yang telah disepakati/diperjanjikan.</td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                    <h7>
                                        <b>III. PERSETUJUAN UMUM</b>
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='2%' valign='top'>1.</td><td width='98%'>Saya menyetujui untuk mendapatkan pelayanan kesehatan di <?=$namars?> dan memberikan kuasa kepada setiap dokter dan perawat untuk memberikan asuhan keperawatan, pemeriksaan fisik, prosedur diagnostik rutin, radiologi, pemberian dan atau penyuntikan produk farmasi dan obat-obatan, pemasangan alat kesehatan (kecuali yang membutuhkan persetujuan khusus) dan pengambilan sampel darah untuk pemeriksaan laboratorium atau pemeriksaan patologi. Dan apabila say memutuskan untuk menghentikan pelayanan kesehatan untuk diri saya sendiri, saya memahami dan menyadari bahwa <?=$namars?> tidak bertanggungjawab terhadap sesuatu yang terjadi terhadap diri saya.</td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>2.</td><td width='98%'>Saya memberikan kuasa kepada setiap pemberi pelayanan kesehatan yang merawat saya untuk memeriksa dan atau memberitahukan informasi mengenai kesehatan saya kepada pemberi pelayanan kesehatan lainnya yang turut merawat saya selama di <?=$namars?>.</td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>3.</td><td width='98%'>Saya mengerti bahwa tidak diperbolehkan untuk membawa benda tajam, barang-barang berharga selama mendapatkan pelayanan kesehatan di <?=$namars?> dan saya memahami bahwa <?=$namars?>s tidak bertanggungjawab terhadap segala bentuk kehilangan, kerusakan dan pencurian terhadap barang-barang milik saya dan secara pribadi bertanggungjawab atas barang-barang yang saya miliki.</td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>4.</td><td width='98%'>Saya mengerti dan memahami bahwa segala kerusakan fasilitas <?=$namars?> yang diakibatkan oleh kelalaian pasien atau penunggu pasien dapat dikenakan sanksi sesuai dengan peraturan yang berlaku di <?=$namars?> dan saya bersedia untuk menerima sanksi tersebut tapa paksaan dari pihak manapun.</td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>5.</td><td width='98%'>Saya mengerti dan memahami bahwa saya, keluarga atau pihak lainnya tidak diperbolehkan untuk mendokumentasikan dalam bentuk apapun (foto, rekaman video, dan lain-lain) terhadap seluruh proses pelayanan kesehatan yang saya atau keluarga sayajalani di <?=$namars?> tanpa seizin dari pihak <?=$namars?>.</td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>6.</td><td width='98%'>Saya telah menerima informasi tentang peraturan yang diberlakukan oleh <?=$namars?> dan saya beserta keluarga bersedia untuk mematuhi jam berkunjung pasien sesuai dengan peraturan rumah sakit dan anggota keluarga saya yang menunggu saya bersedia untuk memakai tanda pengenal khusus penunggu pasien.</td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>7.</td><td width='98%'>Saya memberikan persetujuan dan kewenangan kepada <?=$namars?> untuk memberikan informasi tentang diagnosis, hasil pelayanan kesehatan dan pengobatan terhadap diri saya apabila diperlukan termasuk hasil medical check up yang saya jalani untuk keperluan penjaminan biaya pelayanan kesehatan terhadap saya kepada pihak luar seperti: perusahaan, asuransi, BPJS, Dinas Kesehatan.</td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>8.</td><td width='98%'>Saya memberikan persetujuan dan kuasa kepada <?=$namars?> untuk memberikan informasi tentang diagnosis, hasil pelayanan kesehatan dan pengobatan kepada <select name='pengobatan_kepada'><option value='Suami'>Suami</option><option value='Istri'>Istri</option><option value='Anak'>Anak</option><option value='Ayah'>Ayah</option><option value='Ibu'>Ibu</option><option value='Saudara'>Saudara</option><option value='Keponakan'>Keponakan</option><option value='Adik'>Adik</option><option value='Kakak'>Kakak</option><option value='Orang Tua'>Orang Tua</option><option value='Diri Sendiri'>Diri Sendiri</option></select></td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>9.</td><td width='98%'>Saya menyatakan bahwa say telah menerima infiormasi tentang adanya tatacara mengajukan keluhan terhadap pelayanan kesehatan terhadap diri say dan say setuju untuk mengikuti tata cara mengajukan keluhan tersebut sesuai dengan prosedur yang ada di <?=$namars?>.</td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>10.</td><td width='98%'>Melalui dokumen ini saya sampaikan bahwa saya atau keluarga saya memiliki nilai kepercayaan dalam pengobatan atau perawatan yang antara lain adalah <input name='nilai_kepercayaan' type='text' id='TxtIsi1' size='40' maxlength='50' pattern='[a-zA-Z0-9, ./@_-]{1,50}' title=' a-zA-Z0-9, ./@_- (Maksimal 50 karakter)' autocomplete='off'><span id='MsgIsi1' style='color:#CC0000; font-size:10px;'></span></td>
                                        </tr>
                                        <tr>
                                            <td width='2%' valign='top'>11.</td><td width='98%'>Melalui dokumen ini saya menegaskan kembali bahwa saya mempercayakan kepada semua tenaga kesehatan yang ada di <?=$namars?> untuk memberikan perawatan diagnostik dan terapi kepada saya sebagai pasien rawat inap, rawat jalan, maupun gawat darurat termasuk pemeriksaan penunjang yang dibutuhkan untuk pengobatan dan tindakan.</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        <b>IV. PERNYATAAN</b>
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='100%' colspan='2'>Saya yang bertanda tangan dibawah ini</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nama</td>
                                            <td width='70%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Umur</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["umur_pj"]." &nbsp;&nbsp;Jenis Kelamin : ".$rsquerypersetujuan["jkpj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor Identitas :</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor Telp/Nomor HP</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["no_telp"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Bertindak untuk dan atas nama</td>
                                            <td width='75%'>: ".$rsquerypersetujuan["bertindak_atas"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='100%' colspan='2'>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td width='100%' colspan='2'>Dari pasien ".$_SESSION["nama_instansi"]." dengan </td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nama Pasien</td>
                                            <td width='70%'>: ".$_SESSION["nm_pasien"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Nomor Rekam Medis</td>
                                            <td width='75%'>: ".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Jenis Kelamin</td>
                                            <td width='75%'>: ".$_SESSION["jk"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='25%'>Tanggal Lahir</td>
                                            <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                        </tr>
                                    </table>
                                    <br/>
                                    <h7>
                                        Bahwa saya telah membaca, memahami dan mendapatkan penjelasan tentang ketentuan yang berlaku di <?=$namars?>. Demikian pernyataan ini dibuat dalam keadaan penuh kesadaran dan tanpa paksaan dari pihak manapun, untuk digunakan sebagaimana mestinya
                                    </h7>
                                    <br/>
                                    <br/>
                                    <h7><center>Yang Membuat Pernyataan</center></h7>
                                    <div class='row'>
                                        <div class='col-md-12 text-center'>
                                            <center><div id='my_camera'></div></center>
                                            <input type='hidden' name='image' class='image-tag' onkeydown='setDefault(this, document.getElementById('MsgIsi2'));' id='TxtIsi2'>
                                            <br>
                                            <input type='submit' name='BtnSimpan' class='btn btn-warning' value='Ya, Saya sebagai pembuat pernyataan' onClick='take_snapshot()'/>
                                            <a href='index.php?act=PersetujuanUmum&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                  </div>";
            
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $nopersetujuan     = validTeks4($_POST["nopersetujuan"],20);
                $pengobatan_kepada = validTeks4($_POST["pengobatan_kepada"],20);
                $nilai_kepercayaan = validTeks4($_POST["nilai_kepercayaan"],50);
                if(file_exists("../webapps/persetujuanumum/pages/upload/".$nopersetujuan."PSU.jpeg")){
                    @unlink("../webapps/persetujuanumum/pages/upload/".$nopersetujuan."PSU.jpeg");
                }
                $img               = $_POST["image"];
                $folderPath        = "../webapps/persetujuanumum/pages/upload/";
                $image_parts       = explode(";base64,", $img);
                $image_type_aux    = explode("image/", $image_parts[0]);
                $image_type        = $image_type_aux[1];
                $image_base64      = base64_decode($image_parts[1]);
                $fileName          = $nopersetujuan."PSU.jpeg";
                $file              = $folderPath . $fileName;

                if(file_put_contents($file, $image_base64)){
                    if(file_exists("../webapps/persetujuanumum/pages/upload/".$nopersetujuan."PSU.jpeg")){
                        if(Tambah3("surat_persetujuan_umum_pembuat_pernyataan","'".$nopersetujuan."','pages/upload/$fileName'")){
                            Ubah2("surat_persetujuan_umum","pengobatan_kepada='$pengobatan_kepada',nilai_kepercayaan='$nilai_kepercayaan' where no_surat='$nopersetujuan'");
                            JSRedirect2("index.php?act=PersetujuanUmum&hal=Persetujuan",4);
                        }
                    }else{
                        echo "<div class='row clearfix'>
                                <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                   <div class='card'>
                                       <div class='body'>
                                           <center>Gagal melakukan persetujuan</center>
                                       </div>
                                   </div>
                                </div>
                             </div>";
                    }
                }else{
                    echo "<div class='row clearfix'>
                             <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                <div class='card'>
                                    <div class='body'>
                                        <center>Gagal melakukan persetujuan</center>
                                    </div>
                                </div>
                             </div>
                          </div>";
                }
            }
        }else{
            echo "<div class='block-header'>
                        <h2><center>KETENTUAN RAWAT INAP DAN PERSETUJUAN UMUM</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu persetujuan</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=PersetujuanUmum&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>KETENTUAN RAWAT INAP DAN PERSETUJUAN UMUM</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf persetujuan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect("index.php?act=PersetujuanUmum&hal=Persetujuan");
    }
?>
<script language="JavaScript">
    Webcam.set({
        width: 490,
        height: 440,
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