<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $PNG_TEMP_DIR           = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
    $PNG_WEB_DIR            = 'temp/';
    include "plugins/phpqrcode/qrlib.php"; 
    if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
    $filename               = $PNG_TEMP_DIR.encrypt_decrypt($_SESSION["ses_pasien"],"d").'.png';
    $errorCorrectionLevel   = 'L';
    $matrixPointSize        = 4;
    QRcode::png(encrypt_decrypt($_SESSION["ses_pasien"],"d"), $filename, $errorCorrectionLevel, $matrixPointSize, 2); 
    echo "<div class='block-header'>
            <h2><center>KARTU PERIKSA PASIEN</center></h2>
        </div>
        <div class='row clearfix'>
            <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                <div class='card'>
                    <div class='body'>
                        <div class='table-responsive'>
                            <table class='table dataTable'>
                                <tr>
                                   <td width='30%'>Nama Pasien</td>
                                   <td width='70%'> : ".$_SESSION["nm_pasien"]."</td>
                                </tr>
                                <tr>
                                   <td width='30%'>Tempat Lahir</td>
                                   <td width='70%'> : ".$_SESSION["tmp_lahir"]."</td>
                                </tr>
                                <tr>
                                   <td width='30%'>Tanggal Lahir</td>
                                   <td width='70%'> : ".$_SESSION["tgl_lahir"]."</td>
                                </tr>
                                <tr>
                                   <td width='30%'>Jenis Kelamin</td>
                                   <td width='70%'> : ".($_SESSION["jk"]=="L"?"Laki-Laki":"Perempuan")."</td>
                                </tr>
                                <tr>
                                   <td width='30%'>Email</td>
                                   <td width='70%'> : ".$_SESSION["email"]."</td>
                                </tr>
                                <tr>
                                   <td width='30%'>No. HP/Telp</td>
                                   <td width='70%'> : ".$_SESSION["no_tlp"]."</td>
                                </tr>
                                <tr>
                                   <td width='30%'>No. KTP</td>
                                   <td width='70%'> : ".$_SESSION["no_ktp"]."</td>
                                </tr>
                                <tr>
                                   <td width='30%'>No. Asuransi/JKN</td>
                                   <td width='70%'> : ".$_SESSION["no_peserta"]."</td>
                                </tr>
                                <tr>
                                   <td width='30%'>No. RM</td>
                                   <td width='70%'> : ".encrypt_decrypt($_SESSION["ses_pasien"],"d")."</td>
                                </tr>
                                <tr>
                                   <td width='30%' align='right'><img src=".$_SESSION["photo"]." width='150' height='150' alt='Photo' /></td>
                                   <td width='70%'><img src='pages/".$PNG_WEB_DIR.basename($filename)."' width='150' height='150' alt='QR Code' /></td>
                                </tr>
                           </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>";
?>
