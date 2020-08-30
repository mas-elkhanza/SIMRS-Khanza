<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $nosurat = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $nosurat = json_decode(encrypt_decrypt($nosurat,"d"),true); 
    $nosurat = $nosurat["nosurat"];
    if (isset($nosurat)) {
        $querysuratrujuk = bukaquery("select rujuk.no_rujuk,rujuk.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,
                rujuk.rujuk_ke,date_format(rujuk.tgl_rujuk,'%d/%m/%Y') as tgl_rujuk,rujuk.jam,rujuk.keterangan_diagnosa,
                rujuk.kd_dokter,dokter.nm_dokter,rujuk.kat_rujuk,rujuk.ambulance,rujuk.keterangan,reg_periksa.tgl_registrasi,
                concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat
                from rujuk inner join reg_periksa on rujuk.no_rawat=reg_periksa.no_rawat
                inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on rujuk.kd_dokter=dokter.kd_dokter
                inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec 
                inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where rujuk.no_rujuk='$nosurat'");
        if($rsquerysuratrujuk = mysqli_fetch_array($querysuratrujuk)) {
            $PNG_TEMP_DIR           = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
            $PNG_WEB_DIR            = 'temp/';
            include "plugins/phpqrcode/qrlib.php"; 
            if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
            $filename               = $PNG_TEMP_DIR.$rsquerysuratrujuk["kd_dokter"].'.png';
            $errorCorrectionLevel   = 'L';
            $matrixPointSize        = 4;
            QRcode::png(getOne3("select ifnull(sha1(sidikjari),'".$rsquerysuratrujuk["kd_dokter"]."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$rsquerysuratrujuk["kd_dokter"]."'",$rsquerysuratrujuk["kd_dokter"]), $filename, $errorCorrectionLevel, $matrixPointSize, 2); 
            
            $obat                   = "";
            $queryobat = bukaquery("select databarang.nama_brng from detail_pemberian_obat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat='".$rsquerysuratrujuk["no_rawat"]."' group by databarang.nama_brng");
            while($rsqueryobat = mysqli_fetch_array($queryobat)) {
                $obat = $rsqueryobat["nama_brng"].", ".$obat;
            }
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>SURAT KETERANGAN RUJUKAN<br>Nomor : ".$rsquerysuratrujuk["no_rujuk"]."</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' valign='top' align='right'>".$_SESSION["kabupaten"].", ".$rsquerysuratrujuk["tgl_rujuk"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='left'>Kepada Yth.<br/>".$rsquerysuratrujuk["rujuk_ke"]."<br/>Di Tempat</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='right'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='justify'>Bersama ini kami beritahukan bahwa kami telah merawat / memeriksa pasien berikut ini. Mohon pemeriksaan dan penanganan lebih lanjut penderita :</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='right'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='right'>
                                            <table width='94%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                                <tr> 
                                                    <td width='29%' valign='top'>Tanggal Rawat</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$rsquerysuratrujuk["tgl_registrasi"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='29%' valign='top'>Nama Pasien</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$rsquerysuratrujuk["nm_pasien"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='29%' valign='top'>No.RM</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$rsquerysuratrujuk["no_rkm_medis"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='29%' valign='top'>Alamat</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$rsquerysuratrujuk["alamat"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='29%' valign='top'>Diagnosa</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$rsquerysuratrujuk["keterangan_diagnosa"]."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='29%' valign='top'>Tindakan</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".(getOne("select count(no_rawat) from rawat_inap_dr where no_rawat='".$rsquerysuratrujuk["no_rawat"]."'")>0?"rawat inap, ":"").(getOne("select count(no_rawat) from periksa_lab where no_rawat='".$rsquerysuratrujuk["no_rawat"]."'")>0?"pemeriksaan laboratorium, ":"").(getOne("select count(no_rawat) from periksa_radiologi where no_rawat='".$rsquerysuratrujuk["no_rawat"]."'")>0?"pemeriksaan radiologi, ":"").(getOne("select count(no_rawat) from operasi where no_rawat='".$rsquerysuratrujuk["no_rawat"]."'")>0?"operasi, ":"")."</td>
                                                </tr>
                                                <tr> 
                                                    <td width='29%' valign='top'>Terapi</td><td width='1%' valign='top'>:&nbsp;</td><td width='70%' valign='top'>".$obat."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='right'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' valign='top' align='justify'>Demikianlah riwayat perawatan selama di ".$_SESSION["nama_instansi"]." dengan diagnosa akhir ".$rsquerysuratrujuk["keterangan_diagnosa"].". Atas kerjasamanya kami ucapkan terima kasih</td>
                                    </tr>
                                </table>
                                <table width='100%' class='table-hover' border='0' align='center' cellpadding='5' cellspacing='5' class='tbl_form'>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td width='50%' align='center'></td>
                                        <td width='50%' align='center'>".$_SESSION["nama_instansi"].",<br/>Dokter yang merawat<br/><img src='pages/".$PNG_WEB_DIR.basename($filename)."'/><br/>( <u>".$rsquerysuratrujuk["nm_dokter"]."</u> )</td>
                                    </tr>
                                </table><br>
                                <center><a href='index.php?act=SuratRujuk&hal=Surat' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                    <h2><center>SURAT KETERANGAN RUJUK</center></h2>
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
            JSRedirect2("index.php?act=SuratRujuk",7);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>SURAT KETERANGAN RUJUK</center></h2>
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
        JSRedirect2("index.php?act=SuratRujuk",4);
    }
?>
