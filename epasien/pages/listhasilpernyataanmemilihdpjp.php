<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["nopernyataan"])) {
        $nopernyataan    = validTeks3($iyem["nopernyataan"],20);
        $photo            = validTeks3($iyem["photo"],50);
        $photo2           = validTeks3($iyem["photo2"],50);
        $querypernyataan = bukaquery(
            "select surat_pernyataan_memilih_dpjp.tanggal,dokter.nm_dokter,surat_pernyataan_memilih_dpjp.pembuat_pernyataan,surat_pernyataan_memilih_dpjp.alamat_pembuat_pernyataan,".
            "DATE_FORMAT(surat_pernyataan_memilih_dpjp.tgl_lahir_pembuat_pernyataan,'%d-%m-%Y') as tgl_lahir_pembuat_pernyataan,if(surat_pernyataan_memilih_dpjp.jk_pembuat_pernyataan='L','LAKI-LAKI','PEREMPUAN') as jk_pembuat_pernyataan,".
            "surat_pernyataan_memilih_dpjp.hubungan_pembuat_pernyataan,surat_pernyataan_memilih_dpjp.saksi_keluarga from surat_pernyataan_memilih_dpjp inner join dokter on surat_pernyataan_memilih_dpjp.kd_dokter=dokter.kd_dokter ".
            "where surat_pernyataan_memilih_dpjp.no_pernyataan='$nopernyataan'"
        );
        if($rsquerypernyataan= mysqli_fetch_array($querypernyataan)){
            @$src  = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/pernyataanmemilihdpjp/".$photo));
            @$src2 = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/pernyataanmemilihdpjp/".$photo2));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERNYATAAN KEINGINAN MEMILIH DPJP RAWAT INAP<br/>No. $nopernyataan Tanggal ".$rsquerypernyataan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Saya yang bertanda tangan di bawah ini :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='30%'>Nama</td>
                                        <td width='75%'>: ".$rsquerypernyataan["pembuat_pernyataan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Alamat</td>
                                        <td width='70%'>: ".$rsquerypernyataan["alamat_pembuat_pernyataan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Tanggal Lahir</td>
                                        <td width='70%'>: ".$rsquerypernyataan["tgl_lahir_pembuat_pernyataan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Jenis Kelamin</td>
                                        <td width='70%'>: ".$rsquerypernyataan["jk_pembuat_pernyataan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Hubungan Dengan Pasien</td>
                                        <td width='70%'>: ".$rsquerypernyataan["hubungan_pembuat_pernyataan"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Atas pasien dengan identitas sebagai berikut :  
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='30%'>Nama Pasien</td>
                                        <td width='70%'>: ".$_SESSION["nm_pasien"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Nomor Rekam Medis</td>
                                        <td width='70%'>: ".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."</td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Jenis Kelamin</td>
                                        <td width='70%'>: ".($_SESSION["jk"]=="L"?"Laki-laki":"Perempuan")."</td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Tempat Lahir</td>
                                        <td width='70%'>: ".$_SESSION["tmp_lahir"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Tanggal Lahir</td>
                                        <td width='70%'>: ".$_SESSION["tgl_lahir"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Nomor Identitas</td>
                                        <td width='70%'>: ".$_SESSION["no_ktp"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Dengan ini menyatakan bahwa :  
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='5%'>1.</td>
                                        <td width='95%'>Telah menerima dan memahami informasi mengenai dokter penanggung jawab pasien selama dirawat di ".$_SESSION["nama_instansi"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='5%'>2.</td>
                                        <td width='95%'>Berdasarkan hal tersebut diatas saya memilih ".$rsquerypernyataan["nm_dokter"]." sebagai dokter penanggung jawab</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Demikian pernyataan ini saya buat dengan sesungguhnya untuk diketahui dan digunakan sebagaimana mestinya.
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Pernyataan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Pernyataan' src='$src' width='490px' height='420px'/><br/><br/>
                                        ".$rsquerypernyataan["pembuat_pernyataan"]."
                                    </div>
                                </div>
                                <h7><center>Saksi 1 Keluarga</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Pernyataan' src='$src2' width='490px' height='420px'/><br/><br/>
                                        ".$rsquerypernyataan["saksi_keluarga"]."<br/><br/>
                                        <a href='index.php?act=PernyataanMemilihDPJP&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERNYATAAN KEINGINAN MEMILIH DPJP RAWAT INAP</center></h2>
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
            JSRedirect2("index.php?act=PernyataanMemilihDPJP&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERNYATAAN KEINGINAN MEMILIH DPJP RAWAT INAP</center></h2>
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
        JSRedirect2("index.php?act=PernyataanMemilihDPJP&hal=Persetujuan",4);
    }
?>