<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["nopersetujuan"])) {
        $nopersetujuan    = validTeks3($iyem["nopersetujuan"],20);
        $photo            = validTeks3($iyem["photo"],50);
        $photo2           = validTeks3($iyem["photo2"],50);
        $querypersetujuan = bukaquery(
            "select surat_penolakan_resusitasi.tanggal,surat_penolakan_resusitasi.pembuat_pernyataan,surat_penolakan_resusitasi.alamat_pembuat_pernyataan,".
            "DATE_FORMAT(surat_penolakan_resusitasi.tgl_lahir_pembuat_pernyataan,'%d-%m-%Y') as tgl_lahir_pembuat_pernyataan,if(surat_penolakan_resusitasi.jk_pembuat_pernyataan='L','LAKI-LAKI','PEREMPUAN') as jk_pembuat_pernyataan,".
            "surat_penolakan_resusitasi.hubungan_pembuat_pernyataan,surat_penolakan_resusitasi.saksi_keluarga from surat_penolakan_resusitasi inner join dokter on surat_penolakan_resusitasi.kd_dokter=dokter.kd_dokter ".
            "where surat_penolakan_resusitasi.no_pernyataan='$nopersetujuan'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src  = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".$_SERVER['HTTP_HOST']."/webapps/penolakanresusitasi/".$photo));
            @$src2 = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".$_SERVER['HTTP_HOST']."/webapps/penolakanresusitasi/".$photo2));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PENOLAKAN RESUSITASI<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Saya yang bertanda tangan di bawah ini :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["pembuat_pernyataan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Alamat</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["alamat_pembuat_pernyataan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tanggal Lahir</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["tgl_lahir_pembuat_pernyataan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jenis Kelamin</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["jk_pembuat_pernyataan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Hubungan Dengan Pasien</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["hubungan_pembuat_pernyataan"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Atas pasien dengan identitas sebagai berikut : :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama Pasien</td>
                                        <td width='75%'>: ".$_SESSION["nm_pasien"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Nomor Rekam Medis</td>
                                        <td width='75%'>: ".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jenis Kelamin</td>
                                        <td width='75%'>: ".($_SESSION["jk"]=="L"?"Laki-laki":"Perempuan")."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tempat Lahir</td>
                                        <td width='75%'>: ".$_SESSION["tmp_lahir"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tanggal Lahir</td>
                                        <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>No.KTP/NIK/Pengenal</td>
                                        <td width='75%'>: ".$_SESSION["no_ktp"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>No.Telp</td>
                                        <td width='75%'>: ".$_SESSION["no_tlp"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7 style='display:block;text-align:justify;'>
                                    Dengan ini saya menyatakan bahwa saya membuat keputusan dan menolak dilakukan resusitasi (do not resuscitate).
                                </h7>
                                <br/>
                                <h7 style='display:block;text-align:justify;'>
                                    Saya menyatakan bahwa jika jantung keluarga saya berhenti berdetak / berhenti bernapas, tidak ada prosedur medis untuk mengembalikan pernapasan atau jantung berfungsi kembali yang akan dilakukan oleh staf ".$_SESSION["nama_instansi"].".
                                </h7>
                                <br/>
                                <h7 style='display:block;text-align:justify;'>
                                    Saya memahami bahwa keputusan ini tidak akan mencegah keluarga saya menerima pelayanan kesehatan lainnya atau pemberian oksigen dan langkah-langkah perawatan untuk meningkatkan kenyamanan lainnya.
                                </h7>
                                <br/>
                                <h7 style='display:block;text-align:justify;'>
                                    Saya memberikan izin agar informasi ini diberikan kepada seluruh staf rumah sakit. Saya memahami bahwa saya dapat mencabut pernyataan ini setiap saat.
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Pembuat Pernyataan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>
                                        ".$rsquerypersetujuan["pembuat_pernyataan"]."
                                    </div>
                                </div>
                                <h7><center>Saksi 1 Keluarga</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src2' width='490px' height='420px'/><br/><br/>
                                        ".$rsquerypersetujuan["saksi_keluarga"]."<br/><br/>
                                        <a href='index.php?act=PenolakanResusitasi&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PENOLAKAN RESUSITASI</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu pernyataan</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=PenolakanResusitasi&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PENOLAKAN RESUSITASI</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf pernyataan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=PenolakanResusitasi&hal=Persetujuan",4);
    }
?>