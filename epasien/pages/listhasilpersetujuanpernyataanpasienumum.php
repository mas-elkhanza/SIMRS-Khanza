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
            "select surat_pernyataan_pasien_umum.tanggal,surat_pernyataan_pasien_umum.nama_pj,surat_pernyataan_pasien_umum.no_ktppj,surat_pernyataan_pasien_umum.tempat_lahirpj,surat_pernyataan_pasien_umum.lahirpj,".
            "surat_pernyataan_pasien_umum.jkpj,surat_pernyataan_pasien_umum.alamatpj,surat_pernyataan_pasien_umum.hubungan,surat_pernyataan_pasien_umum.no_telp from surat_pernyataan_pasien_umum ".
            "where surat_pernyataan_pasien_umum.no_surat='".$nopersetujuan."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src = 'data: '.@mime_content_type("http://".host()."/webapps/pernyataanumum/".$photo).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/pernyataanumum/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERNYATAAN PASIEN UMUM<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Saya yang membuat pernyataan di bawah ini, menyatakan bahwa :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tempat/Tanggal Lahir</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["tempat_lahirpj"]." / ".$rsquerypersetujuan["lahirpj"]."</td>
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
                                    <tr>
                                        <td width='25%'>Alamat</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["alamatpj"]."</td>
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
                                    <tr>
                                        <td width='25%'>Hubungan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["hubungan"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Menyatakan bahwa benar pasien tidak memiliki jaminan <b>ASURANSI/BPJS/TC/PT</b>, oleh karena itu saya bersedia bertanggung jawab dengan kewajiban administrasi rumah sakit sebagai <b>PASIEN UMUM (CASH)</b> dari awal sampai selesai perawatan.<br/><br/>
                                    Saya sudah diedukasi oleh pihak ADMINISTRASI dan sudah mengerti, memahami, serta menyetujui bahwa pasien dirawat dengan pembayaran <b>UMUM/CASH</b> atas permintaan sendiri dan tanpa paksaan dari pihak manapun dan tidak akan menuntut/menggugat pernyataaan ini dikemudian hari untuk alasan apapun.<br/><br/>
                                    Demikian surat ini saya buat dengan sebenar-benarnya agar dapat dipergunakan untuk tujuan diatas. Atas perhatiannya saya ucapkan terima kasih.
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Pernyataan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>".$rsquerypersetujuan["nama_pj"]."<br/><br/>
                                        <a href='index.php?act=PersetujuanPernyataanPasienUmum&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERNYATAAN PASIEN UMUM</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanPernyataanPasienUmum&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERNYATAAN PASIEN UMUM</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanPernyataanPasienUmum&hal=Persetujuan",4);
    }
?>