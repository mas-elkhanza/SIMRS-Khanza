<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $token      = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $token      = json_decode(encrypt_decrypt($token,"d"),true); 
    $noantrian  = $token["noantrian"];
    if (isset($noantrian)) {
        $tahun  = $token["tahun"];
        $querysuratkontrol = bukaquery("select skdp_bpjs.tahun,skdp_bpjs.no_rkm_medis,pasien.nm_pasien,skdp_bpjs.diagnosa,skdp_bpjs.terapi,skdp_bpjs.alasan1,skdp_bpjs.alasan2,skdp_bpjs.rtl1,skdp_bpjs.rtl2,skdp_bpjs.tanggal_datang,skdp_bpjs.tanggal_rujukan,skdp_bpjs.no_antrian,skdp_bpjs.kd_dokter,dokter.nm_dokter,skdp_bpjs.status from skdp_bpjs inner join pasien inner join dokter on skdp_bpjs.no_rkm_medis=pasien.no_rkm_medis and skdp_bpjs.kd_dokter=dokter.kd_dokter where skdp_bpjs.no_antrian='$noantrian' and skdp_bpjs.tahun='$tahun'");
        if($rsquerysuratkontrol = mysqli_fetch_array($querysuratkontrol)) {
            $PNG_TEMP_DIR           = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
            $PNG_WEB_DIR            = 'temp/';
            include "plugins/phpqrcode/qrlib.php"; 
            if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
            $filename               = $PNG_TEMP_DIR.$rsquerysuratkontrol["kd_dokter"].'.png';
            $filename2              = $PNG_TEMP_DIR.$rsquerysuratkontrol["no_antrian"].'.png';
            $errorCorrectionLevel   = 'L';
            $matrixPointSize        = 4;
            QRcode::png(getOne3("select ifnull(sha1(sidikjari),'".$rsquerysuratkontrol["kd_dokter"]."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$rsquerysuratkontrol["kd_dokter"]."'",$rsquerysuratkontrol["kd_dokter"]), $filename, $errorCorrectionLevel, $matrixPointSize, 2); 
            QRcode::png($rsquerysuratkontrol["no_antrian"], $filename2, $errorCorrectionLevel, $matrixPointSize, 2); 
            
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>SURAT KETERANGAN</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' valign='top' align='right'>
                                            <table width='100%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                                <tr> 
                                                    <td width='29%' valign='top'>NO. SURAT</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$rsquerysuratkontrol["no_antrian"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='29%' valign='top'>NO. RM</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$rsquerysuratkontrol["no_rkm_medis"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='29%' valign='top'>DIAGNOSA</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$rsquerysuratkontrol["diagnosa"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='29%' valign='top'>TERAPI</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$rsquerysuratkontrol["terapi"]."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='justify'>
                                            Tanggal surat rujukan ".$rsquerysuratkontrol["tanggal_rujukan"]."
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='justify'>
                                            Belum dapat dikembalikan ke Fasilitas Perujuk dengan alasan :
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            1. ".$rsquerysuratkontrol["alasan1"]."
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            2. ".$rsquerysuratkontrol["alasan2"]."
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='justify'>
                                            Rencana tindak lanjut yang akan diberikan pada kunjungan berikutnya :
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            1. ".$rsquerysuratkontrol["rtl1"]."
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            2. ".$rsquerysuratkontrol["rtl2"]."
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='justify'>
                                            Surat keterangan ini digunakan untuk 1 (satu) kali kunjungan dengan diagnosa di atas pada :
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            Tanggal ".$rsquerysuratkontrol["tanggal_datang"]."
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            No. Antrian ". getOne2("select no_reg from booking_registrasi where kd_dokter='".$rsquerysuratkontrol["kd_dokter"]."' and tanggal_periksa='".$rsquerysuratkontrol["tanggal_datang"]."' and no_rkm_medis='".$rsquerysuratkontrol["no_rkm_medis"]."'")."
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='right'>
                                            <table width='100%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                                <tr> 
                                                    <td width='50%' align='center'><img src='pages/".$PNG_WEB_DIR.basename($filename2)."'/></td>
                                                    <td width='50%' align='center'>".$_SESSION["kabupaten"].", <br>Dokter<br/><img src='pages/".$PNG_WEB_DIR.basename($filename)."'/><br/> <u>".$rsquerysuratkontrol["nm_dokter"]."</u> </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table><br>
                                <center><a href='index.php?act=SuratKontrol&hal=Surat' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                    <h2><center>SURAT KONTROL</center></h2>
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
            JSRedirect2("index.php?act=SuratKontrol&hal=Surat",7);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>SURAT KONTROL</center></h2>
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
        JSRedirect2("index.php?act=SuratKontrol&hal=Surat",4);
    }
?>
