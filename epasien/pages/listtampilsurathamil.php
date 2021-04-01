<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $nosurat = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $nosurat = json_decode(encrypt_decrypt($nosurat,"d"),true); 
    $nosurat = $nosurat["nosurat"];
    if (isset($nosurat)) {
        $querysurathamil = bukaquery("select surat_hamil.no_surat,surat_hamil.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.pekerjaan,pasien.umur,date_format(surat_hamil.tanggalperiksa,'%d/%m/%Y') as tanggalperiksa,
                surat_hamil.hasilperiksa,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,reg_periksa.kd_dokter,perusahaan_pasien.nama_perusahaan from surat_hamil 
                inner join reg_periksa on surat_hamil.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel 
                inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter 
                inner join perusahaan_pasien on perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien where surat_hamil.no_surat='$nosurat'");
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
                                <h2><center>SURAT KETERANGAN<br>NO.$nosurat</center></h2>
                            </div>
                            <div class='body' align='justify'>
                                Yang bertanda tangan di bawah ini ".$rsquerysurathamil["nm_dokter"].", dokter di ".$_SESSION["nama_instansi"]." mengingat sumpah jabatan pada waktu menerima jabatannya, menerangkan bahwa :<br/><br/>
                                <table width='98%' class='table-hover' border='0' align='right' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='30%' valign='top'>Nama Pasien</td><td valign='top'>:&nbsp;</td><td width='69%' valign='top'>".$rsquerysurathamil["nm_pasien"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Umur</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysurathamil["umur"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Pekerjaan</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysurathamil["pekerjaan"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Instansi / Perusahaan</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysurathamil["nama_perusahaan"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Alamat</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysurathamil["alamat"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Diperiksa Tanggal</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysurathamil["tanggalperiksa"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Hasil Pemeriksaan</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysurathamil["hasilperiksa"]."</td>
                                    </tr>
                                </table>
                                <table width='100%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' align='justify' colspan='2'>Demikian surat keterangan ini dibuat dengan benar dan agar dapat digunakan sebagaimana mestinya.</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='50%' align='center'></td>
                                        <td width='50%' align='center'>".$_SESSION["kabupaten"].", ".$rsquerysurathamil["tanggalperiksa"]."<br/>".$_SESSION["nama_instansi"]."<br/>Dokter,<br/><img src='pages/".$PNG_WEB_DIR.basename($filename)."'/><br/>( <u>".$rsquerysurathamil["nm_dokter"]."</u> )</td>
                                    </tr>
                                </table><br>
                                <center><a href='index.php?act=SuratHamil&hal=Surat' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                    <h2><center>SURAT KETERANGAN</center></h2>
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
                    <h2><center>SURAT KETERANGAN</center></h2>
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
