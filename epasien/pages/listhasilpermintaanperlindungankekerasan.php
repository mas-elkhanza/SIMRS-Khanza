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
            "select surat_perlindungan_dari_kekerasan.no_surat,DATE_FORMAT(surat_perlindungan_dari_kekerasan.tanggal,'%d-%m-%Y') as tanggal,".
            "surat_perlindungan_dari_kekerasan.nama_pj,surat_perlindungan_dari_kekerasan.no_ktppj,surat_perlindungan_dari_kekerasan.tempat_lahirpj,".
            "DATE_FORMAT(surat_perlindungan_dari_kekerasan.lahirpj,'%d-%m-%Y') as lahirpj,surat_perlindungan_dari_kekerasan.alamatpj,".
            "surat_perlindungan_dari_kekerasan.hubungan,surat_perlindungan_dari_kekerasan.no_telp,if(surat_perlindungan_dari_kekerasan.jkpj='L','LAKI-LAKI','PEREMPUAN') as jkpj ".
            "from surat_perlindungan_dari_kekerasan where surat_perlindungan_dari_kekerasan.no_surat='$nopersetujuan'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".$_SERVER['HTTP_HOST']."/webapps/perlindungankekerasan/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERMINTAAN PERLINDUNGAN DARI KEKERASAN FISIK <br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Yang bertanda tangan di bawah ini :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tempat/Tanggal Lahir</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["tempat_lahirpj"]." ".$rsquerypersetujuan["lahirpj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jenis Kelamin</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["jkpj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Alamat</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["alamatpj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Hubungan Dengan Pasien</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["hubungan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Nomor Telp / HP</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_telp"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Nomor KTP</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Data pasien yang dimohonkan perlindungan :
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
                                        <td width='25%'>Tanggal Lahir</td>
                                        <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>No.Telp/No.HP</td>
                                        <td width='75%'>: ".$_SESSION["no_tlp"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7 style='display:block;text-align:justify;'>
                                    Dengan ini mengajukan permohonan agar selama dilakukan perawatan di <b>".$_SESSION["nama_instansi"]."</b>,
                                    pasien tersebut di atas diberikan perlindungan dari tindakan kekerasan fisik maupun
                                    perlakuan yang membahayakan keselamatan pasien.
                                </h7>
                                <br/>
                                <br/>
                                <h7 style='display:block;text-align:justify;'>
                                    Saya menyatakan bahwa data yang saya sampaikan adalah benar dan dapat dipergunakan
                                    sebagaimana mestinya untuk kepentingan pelayanan dan perlindungan pasien selama dirawat.
                                </h7>
                                <br/>
                                <br/>
                                <h7 style='display:block;text-align:justify;'>
                                    Demikian surat permintaan ini saya buat dengan sebenar-benarnya, tanpa paksaan dari pihak manapun.
                                    Atas perhatian dan tindak lanjutnya saya ucapkan terima kasih.
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Pemohon / Penanggung Jawab</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Permohonan' src='$src' width='490px' height='420px'/><br/><br/>".$rsquerypersetujuan["nama_pj"]."<br/><br/>
                                        <a href='index.php?act=PermintaanPerlindunganKekerasan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERMINTAAN PERLINDUNGAN DARI KEKERASAN FISIK </center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu permohonan</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=PermintaanPerlindunganKekerasan&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERMINTAAN PERLINDUNGAN DARI KEKERASAN FISIK </center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf permohonan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=PermintaanPerlindunganKekerasan&hal=Persetujuan",4);
    }
?>