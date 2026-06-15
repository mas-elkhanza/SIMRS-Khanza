<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat          = validTeks3($iyem["norawat"],20);
        $querypersetujuan = bukaquery(
            "select DATE_FORMAT(layanan_kedokteran_fisik_rehabilitasi.tanggal,'%d-%m-%Y %H:%i:%s') as tanggalpelayanan,layanan_kedokteran_fisik_rehabilitasi.pemeriksaan_fisik,layanan_kedokteran_fisik_rehabilitasi.anamnesa,layanan_kedokteran_fisik_rehabilitasi.diagnosa_medis from layanan_kedokteran_fisik_rehabilitasi where layanan_kedokteran_fisik_rehabilitasi.no_rawat='$norawat'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            $photo = getOne("select bukti_layanan_kedokteran_fisik_rehabilitasi.photo from bukti_layanan_kedokteran_fisik_rehabilitasi where bukti_layanan_kedokteran_fisik_rehabilitasi.no_rawat='".$norawat."'");
            @$src   = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/layanankedokteranfisikrehabilitasi/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>BUKTI LAYANAN KEDOKTERAN FISIK & REHABILITASI<br/>No.Rawat $norawat Tanggal ".$rsquerypersetujuan["tanggalpelayanan"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Saya yang dibawah ini, pasien di ".$_SESSION["nama_instansi"]." dengan :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
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
                                        <td width='75%'>: ".($_SESSION["jk"]=="L"?"Laki-laki":"Perempuan")."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tanggal Lahir</td>
                                        <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Bahwa saya telah mendapatkan pelayanan rehabilitasi medik di ".$_SESSION["nama_instansi"]." dengan : 
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Anamnesa</td>
                                        <td width='70%'>: ".$rsquerypersetujuan["anamnesa"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Pemeriksaan Fisik</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["pemeriksaan_fisik"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Diagnosa Medis</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["diagnosa_medis"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Demikian pernyataan ini dibuat dalam keadaan penuh kesadaran untuk digunakan sebagaimana mestinya.
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Pernyataan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Pernyataan' src='$src' width='490px' height='420px'/><br/><br/> ".$_SESSION["nm_pasien"]."<br/><br/>
                                        <a href='index.php?act=BuktiPelayananRehabilitasi&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>BUKTI LAYANAN KEDOKTERAN FISIK & REHABILITASI</center></h2>
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
            JSRedirect2("index.php?act=BuktiPelayananRehabilitasi&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>BUKTI LAYANAN KEDOKTERAN FISIK & REHABILITASI</center></h2>
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
        JSRedirect2("index.php?act=BuktiPelayananRehabilitasi&hal=Persetujuan",4);
    }
?>