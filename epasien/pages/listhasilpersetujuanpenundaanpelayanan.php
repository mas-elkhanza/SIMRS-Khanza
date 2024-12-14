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
            "select persetujuan_penundaan_pelayanan.tanggal,persetujuan_penundaan_pelayanan.nama_pj,persetujuan_penundaan_pelayanan.umur_pj,persetujuan_penundaan_pelayanan.no_ktppj,persetujuan_penundaan_pelayanan.alamatpj,".
            "persetujuan_penundaan_pelayanan.no_telppj,persetujuan_penundaan_pelayanan.hubungan,persetujuan_penundaan_pelayanan.ruang,persetujuan_penundaan_pelayanan.dokter_pengirim,persetujuan_penundaan_pelayanan.pelayanan_dilakukan,".
            "persetujuan_penundaan_pelayanan.ditunda_karena,persetujuan_penundaan_pelayanan.keterangan_ditunda,persetujuan_penundaan_pelayanan.alternatif_diberikan,persetujuan_penundaan_pelayanan.keterangan_alternatif_diberikan ".
            "from persetujuan_penundaan_pelayanan where persetujuan_penundaan_pelayanan.no_surat='".$nopersetujuan."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src = 'data: '.@mime_content_type("http://".host()."/webapps/penundaanpelayanan/".$photo).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/penundaanpelayanan/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN PENUNDAAN PELAYANAN<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Saya yang membuat persetujuan di bawah ini yang menerima informasi (Pasien Sendiri / Keluarga Pasien / Penanggung Jawab Pasien) :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama</td>
                                        <td width='70%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Umur</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["umur_pj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Nomor KTP</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Alamat Tinggal</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["alamatpj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Nomor Telp/HP</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_telppj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Hubungan Dengan Pasien</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["hubungan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Poli / Unit / Ruangan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["ruang"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Dokter Pengirim</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["dokter_pengirim"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Pelayanan Yang Akan Dilakukan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["pelayanan_dilakukan"]."</td>
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
                                        <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Dengan ini menyatakan bahwa saya telah menerima informasi terhadap penundaan pelayanan dikarenakan : ".$rsquerypersetujuan["ditunda_karena"].($rsquerypersetujuan["keterangan_ditunda"]==""?"":", ".$rsquerypersetujuan["keterangan_ditunda"]).". Maka dengan ini saya <b>SETUJU</b> untuk dilakukan penundaan pelayanan dengan alternatif yang diberikan : ".$rsquerypersetujuan["alternatif_diberikan"].($rsquerypersetujuan["keterangan_alternatif_diberikan"]==""?"":", ".$rsquerypersetujuan["keterangan_alternatif_diberikan"])."
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Persetujuan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>".$rsquerypersetujuan["nama_pj"]."<br/><br/>
                                        <a href='index.php?act=PersetujuanPenundaanPelayanan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERSETUJUAN PENUNDAAN PELAYANAN</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanPenundaanPelayanan&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN PENUNDAAN PELAYANAN</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanPenundaanPelayanan&hal=Persetujuan",4);
    }
?>