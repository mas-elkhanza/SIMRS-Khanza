<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat          = validTeks3($iyem["norawat"],20);
        $tanggal          = validTeks4($iyem["tanggal"],20);
        $querypersetujuan = bukaquery(
            "select DATE_FORMAT(pelaksanaan_informasi_edukasi.tanggal,'%d-%m-%Y') as tanggaledukasi,pelaksanaan_informasi_edukasi.keterangan_diberikan_pada,pelaksanaan_informasi_edukasi.diberikan_pada,pelaksanaan_informasi_edukasi.materi_edukasi ".
            "from pelaksanaan_informasi_edukasi where pelaksanaan_informasi_edukasi.tanggal='$tanggal' and pelaksanaan_informasi_edukasi.no_rawat='$norawat'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            $photo = getOne("select bukti_pelaksanaan_informasi_edukasi.photo from bukti_pelaksanaan_informasi_edukasi where bukti_pelaksanaan_informasi_edukasi.no_rawat='".$norawat."' and bukti_pelaksanaan_informasi_edukasi.tanggal='".$tanggal."'");
            @$src   = 'data: '.@mime_content_type("http://".host()."/webapps/pelaksanaanedukasi/".$photo).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/pelaksanaanedukasi/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>BUKTI PELAKSANAAN INFORMASI & EDUKASI<br/>No.Rawat $norawat Tanggal ".$rsquerypersetujuan["tanggaledukasi"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Saya yang dibawah ini :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Penerima Edukasi/Informasi</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["keterangan_diberikan_pada"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Hubungan Dengan Pasien</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["diberikan_pada"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Dari pasien ".$_SESSION["nama_instansi"]." dengan : 
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
                                    Bahwa saya telah mendapatkan materi informasi & edukasi \"".$rsquerypersetujuan["materi_edukasi"]."\" di ".$_SESSION["nama_instansi"].". Demikian pernyataan ini dibuat dalam keadaan penuh kesadaran untuk digunakan sebagaimana mestinya
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Pernyataan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Pernyataan' src='$src' width='490px' height='420px'/><br/><br/> ".$rsquerypersetujuan["keterangan_diberikan_pada"]."<br/><br/>
                                        <a href='index.php?act=BuktiPelaksanaanEdukasi&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>BUKTI PELAKSANAAN INFORMASI & EDUKASI</center></h2>
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
            JSRedirect2("index.php?act=BuktiPelaksanaanEdukasi&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>BUKTI PELAKSANAAN INFORMASI & EDUKASI</center></h2>
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
        JSRedirect2("index.php?act=BuktiPelaksanaanEdukasi&hal=Persetujuan",4);
    }
?>