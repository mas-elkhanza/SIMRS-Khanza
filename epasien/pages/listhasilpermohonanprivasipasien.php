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
            "select surat_permohonan_privasi.no_surat,surat_permohonan_privasi.tanggal,surat_permohonan_privasi.nama_pj,surat_permohonan_privasi.umur_pj,".
            "surat_permohonan_privasi.no_ktppj,surat_permohonan_privasi.jkpj,surat_permohonan_privasi.alamatpj,surat_permohonan_privasi.bertindak_atas,".
            "surat_permohonan_privasi.no_telp,surat_permohonan_privasi.kategori_privasi,surat_permohonan_privasi.alasan from surat_permohonan_privasi ".
            "where surat_permohonan_privasi.no_surat='".$nopersetujuan."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".$_SERVER['HTTP_HOST']."/webapps/permohonanprivasi/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERMOHONAN PRIVASI PASIEN<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Yang bertanda tangan di bawah ini, selanjutnya disebut sebagai pemohon :
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
                                        <td width='25%'>Nomor Identitas</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
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
                                        <td width='25%'>No. Telp</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_telp"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Hubungan Dengan Pasien</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["bertindak_atas"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Data pasien yang diajukan permohonan privasinya :
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
                                <h7>
                                    Dengan ini mengajukan permohonan privasi pasien dengan rincian sebagai berikut :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Kategori Privasi</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["kategori_privasi"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Alasan Permohonan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["alasan"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Permohonan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>".$_SESSION["nm_pasien"]."<br/><br/>
                                        <a href='index.php?act=PermohonanPrivasiPasien&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERMOHONAN PRIVASI PASIEN</center></h2>
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
            JSRedirect2("index.php?act=PermohonanPrivasiPasien&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERMOHONAN PRIVASI PASIEN</center></h2>
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
        JSRedirect2("index.php?act=PermohonanPrivasiPasien&hal=Persetujuan",4);
    }
?>