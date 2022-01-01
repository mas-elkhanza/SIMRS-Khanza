<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $nosurat = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $nosurat = json_decode(encrypt_decrypt($nosurat,"d"),true); 
    if (isset($nosurat["nosurat"])) {
        $nosurat = $nosurat["nosurat"];
        $querysurathamil = bukaquery("select surat_skbn.no_surat,surat_skbn.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.pekerjaan,pasien.umur,DAYNAME(surat_skbn.tanggalsurat) as hari,date_format(surat_skbn.tanggalsurat,'%d/%m/%Y') as tanggalsurat,
                date_format(surat_skbn.tanggalsurat,'%d') as tanggal,date_format(surat_skbn.tanggalsurat,'%m') as bulan,date_format(surat_skbn.tanggalsurat,'%Y') as tahun,surat_skbn.keperluan,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,
                concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,reg_periksa.kd_dokter,surat_skbn.kategori,pasien.tmp_lahir,suku_bangsa.nama_suku_bangsa,pasien.pekerjaan,
                date_format(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir,pasien.agama,surat_skbn.opiat,surat_skbn.ganja,surat_skbn.amphetamin,surat_skbn.methamphetamin,surat_skbn.benzodiazepin,surat_skbn.cocain from surat_skbn 
                inner join reg_periksa on surat_skbn.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel 
                inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join suku_bangsa on pasien.suku_bangsa=suku_bangsa.id
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
                                        <td width='4%' valign='top'>1.</td>
                                        <td width='96%' valign='top' align='justify'>Yang bertanda tangan dibawah ini saya ".$rsquerysurathamil["nm_dokter"].", pada ".$_SESSION["nama_instansi"]." menerangkan bahwa telah melakukan wawancara, pemeriksaan fisik dan pemeriksaan laboratorium terhadap pasien dengan keterangan sebagai berikut :
                                            <table width='100%' class='table-hover' border='0' align='right' cellpadding='5' cellspacing='5' class='tbl_form'>
                                                <tr> 
                                                    <td width='3%' valign='top'>&nbsp;</td><td width='28%' valign='top'>&nbsp;</td><td width='1%' valign='top'>&nbsp;</td><td width='68%' valign='top'>&nbsp;</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>a.</td><td width='28%' valign='top'>Nama</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["nm_pasien"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>b.</td><td width='28%' valign='top'>Tempat, Tgl. lahir</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["tmp_lahir"].", ".$rsquerysurathamil["tgl_lahir"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>c.</td><td width='28%' valign='top'>Agama</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["agama"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>d.</td><td width='28%' valign='top'>Jenis Kelamin</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["jk"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>e.</td><td width='28%' valign='top'>Suku Bangsa</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["nama_suku_bangsa"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>f.</td><td width='28%' valign='top'>Pekerjaan</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["pekerjaan"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>g.</td><td width='28%' valign='top'>Alamat</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["alamat"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>h.</td><td width='28%' valign='top'>Dipergunakan</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["keperluan"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>&nbsp;</td><td width='28%' valign='top'>&nbsp;</td><td width='1%' valign='top'>&nbsp;</td><td width='68%' valign='top'>&nbsp;</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='4%' valign='top'>2.</td>
                                        <td width='96%' valign='top' align='justify'>Berdasarkan pemeriksaan Fisik dan pemeriksaan Urine dengan menggunakan alat ANSWER yang dilaksanakan pada hari/tanggal ".konversiHari($rsquerysurathamil["hari"]).", ".$rsquerysurathamil["tanggal"]." ". konversiBulan($rsquerysurathamil["bulan"])." ".$rsquerysurathamil["tahun"].", bahwa pada saat pemeriksaaan didapatkan hasil sebagai berikut  :
                                            <table width='100%' class='table-hover' border='0' align='right' cellpadding='5' cellspacing='5' class='tbl_form'>
                                                <tr> 
                                                    <td width='3%' valign='top'>&nbsp;</td><td width='28%' valign='top'>&nbsp;</td><td width='1%' valign='top'>&nbsp;</td><td width='68%' valign='top'>&nbsp;</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>a.</td><td width='28%' valign='top'>Opiat / Morphin</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["opiat"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>b.</td><td width='28%' valign='top'>Ganja / Canabis</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["ganja"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>c.</td><td width='28%' valign='top'>Amphetamin</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["amphetamin"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>d.</td><td width='28%' valign='top'>Methamphetamin</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["methamphetamin"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>e.</td><td width='28%' valign='top'>Benzodiazepin</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["benzodiazepin"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>f.</td><td width='28%' valign='top'>Cocain</td><td width='1%' valign='top'>:&nbsp;</td><td width='68%' valign='top'>".$rsquerysurathamil["cocain"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='3%' valign='top'>&nbsp;</td><td width='28%' valign='top'>&nbsp;</td><td width='1%' valign='top'>&nbsp;</td><td width='68%' valign='top'>&nbsp;</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='4%' valign='top'>3.</td><td width='96%' valign='top' align='justify'>KESIMPULAN :<br/>Pada saat pemeriksaan orang ini yang berdasarkan wawancara, pemeriksaan fisik dan pemeriksaan laboratorium urinnya, tidak ditemukan tanda – tanda dari intoksikasi dan atau ketergantungan penggunaan narkoba. Demikian surat keterangan ini dibuat agar yang berkepentingan menjadi maklum.</td>
                                    </tr>
                                </table>
                                <table width='100%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='50%' align='center'><br/>Pemohon<br/><br/><br/><br/><br/>".$rsquerysurathamil["nm_pasien"]."</td>
                                        <td width='50%' align='center'>".$_SESSION["kabupaten"].", ".$rsquerysurathamil["tanggal"]."<br/>".$_SESSION["nama_instansi"]."<br/>Dokter,<br/><img src='pages/".$PNG_WEB_DIR.basename($filename)."'/><br/>( <u>".$rsquerysurathamil["nm_dokter"]."</u> )</td>
                                    </tr>
                                </table><br>
                                <center><a href='index.php?act=SuratBebasNarkoba&hal=Surat' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                    <h2><center>SURAT KETERANGAN<br><u>HASIL PEMERIKSAAN URINE PENGGUNA NARKOBA</u></center></h2>
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
            JSRedirect2("index.php?act=SuratBebasNarkoba",7);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>SURAT KETERANGAN<br><u>HASIL PEMERIKSAAN URINE PENGGUNA NARKOBA</u></center></h2>
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
        JSRedirect2("index.php?act=SuratBebasNarkoba",4);
    }
?>
