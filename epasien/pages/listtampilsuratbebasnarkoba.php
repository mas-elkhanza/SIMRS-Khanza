<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $nosurat = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $nosurat = json_decode(encrypt_decrypt($nosurat,"d"),true); 
    $nosurat = $nosurat["nosurat"];
    if (isset($nosurat)) {
        $querysurathamil = bukaquery("select surat_skbn.no_surat,surat_skbn.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.pekerjaan,pasien.umur,DAYNAME(surat_skbn.tanggalsurat) as hari,date_format(surat_skbn.tanggalsurat,'%d/%m/%Y') as tanggalsurat,
                date_format(surat_skbn.tanggalsurat,'%d') as tanggal,date_format(surat_skbn.tanggalsurat,'%m') as bulan,date_format(surat_skbn.tanggalsurat,'%Y') as tahun,surat_skbn.keperluan,
                concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,reg_periksa.kd_dokter,surat_skbn.kategori,
                surat_skbn.opiat,surat_skbn.ganja,surat_skbn.amphetamin,surat_skbn.methamphetamin,surat_skbn.benzodiazepin,surat_skbn.cocain from surat_skbn 
                inner join reg_periksa on surat_skbn.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel 
                inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter 
                where surat_skbn.no_surat='$nosurat'");
        if($rsquerysurathamil = mysqli_fetch_array($querysurathamil)) {
            $PNG_TEMP_DIR           = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
            $PNG_WEB_DIR            = 'temp/';
            include "plugins/phpqrcode/qrlib.php"; 
            if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
            $filename               = $PNG_TEMP_DIR.$rsquerysurathamil["kd_dokter"].'.png';
            $errorCorrectionLevel   = 'L';
            $matrixPointSize        = 4;
            QRcode::png(getOne3("select ifnull(sha1(sidikjari),'".$rsquerysurathamil["kd_dokter"]."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$rsquerysurathamil["kd_dokter"]."'",$rsquerysurathamil["kd_dokter"]), $filename, $errorCorrectionLevel, $matrixPointSize, 2); 
            
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>SURAT KETERANGAN<br><u>HASIL PEMERIKSAAN URINE PENGGUNA NARKOBA</u><br>NOMOR : $nosurat</center></h2>
                            </div>
                            <div class='body' align='justify'>
                                <table width='100%' class='table-hover' border='0' align='right' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='4%' valign='top'>1.</td><td width='96%' valign='top' align='justify'>Yang bertanda tangan dibawah ini saya ".$rsquerysurathamil["nm_dokter"].", pada ".$_SESSION["nama_instansi"]." menerangkan bahwa telah melakukan wawancara, pemeriksaan fisik dan pemeriksaan laboratorium terhadap pasien dengan keterangan sebagai berikut :</td>
                                    </tr>
                                    <tr>
                                        <td width='4%' valign='top'>2.</td><td width='96%' valign='top' align='justify'>Berdasarkan pemeriksaan Fisik dan pemeriksaan Urine dengan menggunakan alat ANSWER yang dilaksanakan pada hari/tanggal ".konversiHari($rsquerysurathamil["hari"]).", ".$rsquerysurathamil["tanggal"]." ". konversiBulan($rsquerysurathamil["bulan"])." ".$rsquerysurathamil["tahun"].", bahwa pada saat pemeriksaaan didapatkan hasil sebagai berikut  :</td>
                                    </tr>
                                    <tr>
                                        <td width='4%' valign='top'>3.</td><td width='96%' valign='top' align='justify'>KESIMPULAN :<br/>Pada saat pemeriksaan orang ini yang berdasarkan wawancara, pemeriksaan fisik dan pemeriksaan laboratorium urinnya, tidak ditemukan tanda – tanda dari intoksikasi dan atau ketergantungan penggunaan narkoba. Demikian surat keterangan ini dibuat agar yang berkepentingan menjadi maklum.</td>
                                    </tr>
                                </table><br>
                                <center><a href='index.php?act=SuratHamil&hal=Surat' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                    <h2><center>SURAT KETERANGAN SAKIT</center></h2>
                </div>
                <div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf surat tidak ditemukan</center>
                            </div>
                        </div>
                    </div>
                </div>";
            JSRedirect2("index.php?act=SuratHamil",7);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>SURAT KETERANGAN SAKIT</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf surat tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=SuratHamil",4);
    }
?>
