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
            "select persetujuan_penolakan_tindakan.tanggal,persetujuan_penolakan_tindakan.diagnosa,persetujuan_penolakan_tindakan.tindakan,persetujuan_penolakan_tindakan.indikasi_tindakan,".
            "persetujuan_penolakan_tindakan.tata_cara,persetujuan_penolakan_tindakan.tujuan,persetujuan_penolakan_tindakan.risiko,persetujuan_penolakan_tindakan.komplikasi,".
            "persetujuan_penolakan_tindakan.prognosis,persetujuan_penolakan_tindakan.alternatif_dan_risikonya,persetujuan_penolakan_tindakan.biaya,persetujuan_penolakan_tindakan.lain_lain,".
            "persetujuan_penolakan_tindakan.kd_dokter,persetujuan_penolakan_tindakan.nip,persetujuan_penolakan_tindakan.penerima_informasi,persetujuan_penolakan_tindakan.alasan_diwakilkan_penerima_informasi,".
            "if(persetujuan_penolakan_tindakan.jk_penerima_informasi='L','LAKI-LAKI','PEREMPUAN') as jk_penerima_informasi,persetujuan_penolakan_tindakan.tanggal_lahir_penerima_informasi,".
            "persetujuan_penolakan_tindakan.umur_penerima_informasi,persetujuan_penolakan_tindakan.alamat_penerima_informasi,persetujuan_penolakan_tindakan.no_hp,".
            "persetujuan_penolakan_tindakan.hubungan_penerima_informasi,persetujuan_penolakan_tindakan.saksi_keluarga,persetujuan_penolakan_tindakan.tindakan_konfirmasi,".
            "persetujuan_penolakan_tindakan.diagnosa_konfirmasi,persetujuan_penolakan_tindakan.indikasi_tindakan_konfirmasi,persetujuan_penolakan_tindakan.tata_cara_konfirmasi,".
            "persetujuan_penolakan_tindakan.tujuan_konfirmasi,persetujuan_penolakan_tindakan.risiko_konfirmasi,persetujuan_penolakan_tindakan.komplikasi_konfirmasi,".
            "persetujuan_penolakan_tindakan.prognosis_konfirmasi,persetujuan_penolakan_tindakan.alternatif_konfirmasi,persetujuan_penolakan_tindakan.lain_lain_konfirmasi,".
            "persetujuan_penolakan_tindakan.biaya_konfirmasi,persetujuan_penolakan_tindakan.pernyataan from persetujuan_penolakan_tindakan ".
            "where persetujuan_penolakan_tindakan.no_pernyataan='$nopersetujuan'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src  = 'data: '.@mime_content_type("http://".host()."/webapps/persetujuantindakan/".$photo).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/persetujuantindakan/".$photo));
            @$src2 = 'data: '.@mime_content_type("http://".host()."/webapps/persetujuantindakan/".$photo2).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/persetujuantindakan/".$photo2));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERNYATAAN PERSETUJUAN/PENOLAKAN TINDAKAN<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Apabila pasien berusia dibawah 18 tahun atau tidak dapat memberikan persetujuan karena alasan lain (**) tidak dapat menandatangani surat pernyataan ini, 
                                    Pihak rumah sakit dapat mengambil kebijakan dengan memperoleh tanda tangan dari orang tua, pasangan, anggota keluarga terdekat atau wali pasien.<br/> 
                                    (**) Sebutkan alasan lainnya : ".$rsquerypersetujuan["alasan_diwakilkan_penerima_informasi"]."<br/><br/>
                                    Yang bertanda tangan di bawah ini saya :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='30%'>Nama</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["penerima_informasi"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jenis Kelamin</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["jk_penerima_informasi"]." &nbsp;&nbsp;&nbsp;&nbsp;Tanggal Lahir : ".$rsquerypersetujuan["tanggal_lahir_penerima_informasi"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Alamat</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["alamat_penerima_informasi"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Hubungan dengan pasien</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["hubungan_penerima_informasi"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Dengan ini menyatakan ".$rsquerypersetujuan["pernyataan"]." untuk dapat dilakukan tindakan kedokteran berupa : 
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='30%'>Tindakan Kedokteran</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["tindakan"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["tindakan_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Diagnosa</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["diagnosa"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["diagnosa_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Indikasi Tindakan</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["indikasi_tindakan"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["indikasi_tindakan_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Tata Cara</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["tata_cara"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["tata_cara_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr> 
                                    <tr>
                                        <td width='30%'>Tujuan</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["tujuan"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["tujuan_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Risiko</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["risiko"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["risiko_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Komplikasi</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["komplikasi"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["komplikasi_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Prognosis</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["prognosis"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["prognosis_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Alternatif & Resikonya</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["alternatif_dan_risikonya"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["alternatif_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Lain-lain</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["lain_lain"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["lain_lain_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                    <tr>
                                        <td width='30%'>Biaya</td>
                                        <td width='60%'>: ".$rsquerypersetujuan["biaya"]."</td>
                                        <td width='10%'><input type='checkbox' disabled ".($rsquerypersetujuan["biaya_konfirmasi"]=="true"?"checked":"")."></td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Terhadap Pasien : 
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='30%'>Nama Pasien</td>
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
                                    Melalui penyataan ini segala resiko dan yang kemungkinan terjadi sebagai akibat dari pengambilan keputusan ini menjadi tanggung jawab saya pribadi dan keluarga
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Pernyataan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>
                                        ".$rsquerypersetujuan["penerima_informasi"]."
                                    </div>
                                </div>
                                <h7><center>Saksi 1 Keluarga</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src2' width='490px' height='420px'/><br/><br/>
                                        ".$rsquerypersetujuan["saksi_keluarga"]."<br/><br/>
                                        <a href='index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERNYATAAN PERSETUJUAN/PENOLAKAN TINDAKAN</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERNYATAAN PERSETUJUAN/PENOLAKAN TINDAKAN</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan",4);
    }
?>