<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $nosurat = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $nosurat = json_decode(encrypt_decrypt($nosurat,"d"),true); 
    if (isset($nosurat["nosurat"])) {
        $nosurat = $nosurat["nosurat"];
        $querysuratcovid = bukaquery("select surat_keterangan_covid.no_surat,surat_keterangan_covid.no_rawat,surat_keterangan_covid.kd_dokter,dokter.nm_dokter,
                reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-laki','Perempuan') as jk,pasien.pekerjaan,
                date_format(pasien.tgl_lahir,'%d/%m/%Y') as tanggallahir,spesialis.nm_sps,
                concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,surat_keterangan_covid.nip,
                petugas.nama,surat_keterangan_covid.igm,surat_keterangan_covid.igg,surat_keterangan_covid.sehat,surat_keterangan_covid.tidaksehat,
                date_format(surat_keterangan_covid.berlakumulai,'%d/%m/%Y') as berlakumulai,date_format(surat_keterangan_covid.berlakuselsai,'%d/%m/%Y') as berlakuselsai 
                from surat_keterangan_covid inner join reg_periksa on surat_keterangan_covid.no_rawat=reg_periksa.no_rawat 
                inner join dokter on surat_keterangan_covid.kd_dokter=dokter.kd_dokter inner join petugas on surat_keterangan_covid.nip=petugas.nip
                inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel 
                inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab
                inner join spesialis on dokter.kd_sps=spesialis.kd_sps where surat_keterangan_covid.no_surat='$nosurat'");
        if($rsquerysuratcovid = mysqli_fetch_array($querysuratcovid)) {
            $PNG_TEMP_DIR           = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
            $PNG_WEB_DIR            = 'temp/';
            include "plugins/phpqrcode/qrlib.php"; 
            if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
            $filename               = $PNG_TEMP_DIR.$rsquerysuratcovid["kd_dokter"].'.png';
            $errorCorrectionLevel   = 'L';
            $matrixPointSize        = 4;
            QRcode::png(getOne3("select ifnull(sha1(sidikjari),'".$rsquerysuratcovid["kd_dokter"]."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$rsquerysuratcovid["kd_dokter"]."'",$rsquerysuratcovid["kd_dokter"]), $filename, $errorCorrectionLevel, $matrixPointSize, 2); 
            $filename2               = $PNG_TEMP_DIR.$rsquerysuratcovid["nip"].'.png';
            QRcode::png(getOne3("select ifnull(sha1(sidikjari),'".$rsquerysuratcovid["nip"]."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$rsquerysuratcovid["nip"]."'",$rsquerysuratcovid["nip"]), $filename2, $errorCorrectionLevel, $matrixPointSize, 2); 
            
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>SURAT KETERANGAN RAPID TEST<br>NO.$nosurat</center></h2>
                            </div>
                            <div class='body' align='justify'>
                                Saya yang bertanda tangan di bawah ini <br/>
                                <table width='98%' class='table-hover' border='0' align='right' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' colspan='3'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='27%' valign='top'>Nama</td><td valign='top'>:&nbsp;</td><td width='72%' valign='top'>".$rsquerysuratcovid["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='27%' valign='top'>Jabatan</td><td valign='top'>:&nbsp;</td><td width='72%' valign='top'>".$rsquerysuratcovid["nm_sps"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='3'>&nbsp;</td>
                                    </tr>
                                </table>
                                Menerangkan bahwa :<br/>
                                <table width='98%' class='table-hover' border='0' align='right' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' colspan='3'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='27%' valign='top'>Nama Pasien</td><td valign='top'>:&nbsp;</td><td width='72%' valign='top'>".$rsquerysuratcovid["nm_pasien"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Tanggal Lahir</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysuratcovid["tanggallahir"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Jenis Kelamin</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysuratcovid["jk"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Pekerjaan</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysuratcovid["pekerjaan"]."</td>
                                    </tr>
                                    <tr>  
                                        <td valign='top'>Alamat</td><td valign='top'>:&nbsp;</td><td valign='top'>".$rsquerysuratcovid["alamat"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='3'>&nbsp;</td>
                                    </tr>
                                </table>
                                Dari hasil pemeriksaan Repid Test Anti SARS-Cov-19, saya menyatakan : <br/>
                                <table width='98%' class='table-hover' border='0' align='right' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='50%'>-Rapid Test IgM SARS-Cov-19</td>
                                        <td width='50%'>: ".$rsquerysuratcovid["igm"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='50%'>-Rapid Test IgG SARS-Cov-19</td>
                                        <td width='50%'>: ".$rsquerysuratcovid["igg"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>";
            
                            if($rsquerysuratcovid["sehat"]=="V"){
                                echo "<tr>
                                          <td width='100%' colspan='2'>Keterangan : <b>Sehat Dan Tidak Ada Tanda Dan Gejala Tertular Covid-19</b></td>
                                     </tr>";
                            }
                            
                            if($rsquerysuratcovid["tidaksehat"]=="V"){
                                echo "<tr>
                                          <td width='100%' colspan='2'>Keterangan : <b>Tidak Sehat Dan Ada Tanda Dan Gejala Tertular Covid-19</b></td>
                                     </tr>";
                            }
                            
                            echo "
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                </table><br>
                                Demikian surat keterangan ini dibuat sesuai dengan kondisi sebenarnya dan untuk dipergunakan sebagaimana mestinya. Hasil rapid test tersebut berlaku sejak tanggal ".$rsquerysuratcovid["berlakumulai"]." sampai dengan ".$rsquerysuratcovid["berlakuselsai"].".
                                <table width='100%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='50%' align='center'></td>
                                        <td width='50%' align='center'>".$_SESSION["kabupaten"].", ".$rsquerysuratcovid["berlakumulai"]."<br/></td>
                                    </tr>
                                    <tr>
                                        <td width='50%' align='center'>Dokter Penanggung Jawab,<br/><img src='pages/".$PNG_WEB_DIR.basename($filename)."'/><br/>( <u>".$rsquerysuratcovid["nm_dokter"]."</u> )</td>
                                        <td width='50%' align='center'>Analis Laboratorium,<br/><img src='pages/".$PNG_WEB_DIR.basename($filename2)."'/><br/>( <u>".$rsquerysuratcovid["nama"]."</u> )</td>
                                    </tr>
                                </table><br>
                                <center><a href='index.php?act=SuratCovid&hal=Surat' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                    <h2><center>SURAT KETERANGAN RAPID TEST</center></h2>
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
            JSRedirect2("index.php?act=SuratCovid",7);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>SURAT KETERANGAN RAPID TEST</center></h2>
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
        JSRedirect2("index.php?act=SuratCovid",4);
    }
?>
