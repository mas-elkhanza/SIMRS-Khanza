<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["nopersetujuan"])) {
        $nopersetujuan    = validTeks3($iyem["nopersetujuan"],20);
        $photo            = validTeks3($iyem["photo"],50);
        $querypersetujuan = bukaquery(
            "select surat_penolakan_anjuran_medis.tanggal,surat_penolakan_anjuran_medis.tanggal,surat_penolakan_anjuran_medis.hubungan,surat_penolakan_anjuran_medis.nama_pj,surat_penolakan_anjuran_medis.umur_pj,surat_penolakan_anjuran_medis.no_ktppj,surat_penolakan_anjuran_medis.jkpj,".
            "surat_penolakan_anjuran_medis.no_telp,surat_penolakan_anjuran_medis.kode_penolakan,surat_penolakan_anjuran_medis.alasan_penolakan,surat_penolakan_anjuran_medis.informasi_risiko_penolakan,master_menolak_anjuran_medis.nama_penolakan from surat_penolakan_anjuran_medis ".
            "inner join master_menolak_anjuran_medis on master_menolak_anjuran_medis.kode_penolakan=surat_penolakan_anjuran_medis.kode_penolakan where surat_penolakan_anjuran_medis.no_surat='".$nopersetujuan."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/penolakananjuranmedis/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PENOLAKAN ANJURAN MEDIS<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Saya yang membuat penolakan di bawah ini, menyatakan bahwa :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Umur</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["umur_pj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jenis Kelamin</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["jkpj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Nomor Telp/Nomor HP</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_telp"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Nomor KTP</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Terhadap Pasien : 
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
                                        <td width='75%'>: ".$_SESSION["jk"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tanggal Lahir</td>
                                        <td width='75%'>: ".$_SESSION["tgl_lahir"] ."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Hubungan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["hubungan"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Menyatakan bahwa benar saya/pasien menolak <b>".$rsquerypersetujuan["nama_penolakan"]."</b>, dengan alasan <b>".$rsquerypersetujuan["alasan_penolakan"]."</b> serta sudah mendapatkan informasi mengenai resiko dari penolakan yang dilakukan yaitu <b>".$rsquerypersetujuan["informasi_risiko_penolakan"]."</b> dan tidak akan menuntut/menggugat pernyataaan ini dikemudian hari untuk alasan apapun.
                                    <br/><br/>
                                    Demikian peryataan ini saya buat dengan sebenar-benarnya. Atas perhatiannya saya ucapkan terima kasih.
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Pernyataan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>".$rsquerypersetujuan["nama_pj"]."<br/><br/>
                                        <a href='index.php?act=PersetujuanPenolakanAnjuranMedis&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PENOLAKAN ANJURAN MEDIS</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu persetujuan</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=PersetujuanPenolakanAnjuranMedis&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PENOLAKAN ANJURAN MEDIS</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf persetujuan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=PersetujuanPenolakanAnjuranMedis&hal=Persetujuan",4);
    }
?>