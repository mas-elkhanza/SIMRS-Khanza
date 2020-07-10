<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $nosurat = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $nosurat = json_decode(encrypt_decrypt($nosurat,"d"),true); 
    $nosurat = $nosurat["nosurat"];
    if (isset($nosurat)) {
        $querysuratsakit = bukaquery("select suratsakit.no_surat,suratsakit.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-laki','Perempuan') as jk,
                pasien.pekerjaan,pasien.umur,date_format(suratsakit.tanggalawal,'%d/%m/%Y') as tanggalawal,date_format(suratsakit.tanggalakhir,'%d/%m/%Y') as tanggalakhir,suratsakit.lamasakit,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,
                dokter.nm_dokter,reg_periksa.kd_dokter from suratsakit inner join reg_periksa on suratsakit.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan 
                on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join dokter 
                on reg_periksa.kd_dokter=dokter.kd_dokter where suratsakit.no_surat='$nosurat'");
        if($rsquerysuratsakit = mysqli_fetch_array($querysuratsakit)) {
            $PNG_TEMP_DIR           = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
            $PNG_WEB_DIR            = 'temp/';
            include "plugins/phpqrcode/qrlib.php"; 
            if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
            $filename               = $PNG_TEMP_DIR.$rsquerysuratsakit["kd_dokter"].'.png';
            $errorCorrectionLevel   = 'L';
            $matrixPointSize        = 4;
            QRcode::png(getOne3("select ifnull(sha1(sidikjari),'".$rsquerysuratsakit["kd_dokter"]."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$rsquerysuratsakit["kd_dokter"]."'",$rsquerysuratsakit["kd_dokter"]), $filename, $errorCorrectionLevel, $matrixPointSize, 2); 
            
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>SURAT KETERANGAN SAKIT<br>NO.$nosurat</center></h2>
                            </div>
                            <div class='body' align='justify'>
                                Yang bertanda tangan di bawah ini ".$rsquerysuratsakit["nm_dokter"].", dokter pada ".$_SESSION["nama_instansi"]." menerangkan bahwa :<br/><br/>
                                <table width='98%' class='table-hover' border='0' align='right' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='27%' valign='top'>Nama Pasien</td><td valign='top'>:&nbsp;</td><td width='72%' valign='top'>".$rsquerysuratsakit["nm_pasien"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Umur</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysuratsakit["umur"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Jenis Kelamin</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysuratsakit["jk"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Pekerjaan</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysuratsakit["pekerjaan"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Alamat</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysuratsakit["alamat"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Diagnosa</td><td valign='top'>:&nbsp;</td><td valign='top'>".getOne("select concat(diagnosa_pasien.kd_penyakit,' ',penyakit.nm_penyakit) from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.no_rawat='".$rsquerysuratsakit["no_rawat"]."' and diagnosa_pasien.prioritas='1'")."</td>
                                    </tr>
                                </table>
                                <table width='100%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' align='justify' colspan='2'>Telah diperiksa kesehatan badannya dan sekarang dalam keadaan sakit dan perlu istirahat ".$rsquerysuratsakit["lamasakit"]." hari dari tanggal ".$rsquerysuratsakit["tanggalawal"]." sampai dengan ".$rsquerysuratsakit["tanggalakhir"].". Demikian surat keterangan ini dibuat dengan benar dan untuk dapat digunakan sebagaimana mestinya.</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='50%' align='center'></td>
                                        <td width='50%' align='center'>".$_SESSION["kabupaten"].", ".$rsquerysuratsakit["tanggalawal"]."<br/>".$_SESSION["nama_instansi"]."<br/>Dokter,<br/><img src='pages/".$PNG_WEB_DIR.basename($filename)."'/><br/>( <u>".$rsquerysuratsakit["nm_dokter"]."</u> )</td>
                                    </tr>
                                </table><br>
                                <center><a href='index.php?act=SuratSakit&hal=Surat' class='btn btn-danger waves-effect'>Kembali</a></center>
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
            JSRedirect2("index.php?act=SuratSakit",7);
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
        JSRedirect2("index.php?act=SuratSakit",4);
    }
?>
