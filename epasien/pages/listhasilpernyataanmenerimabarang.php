<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["nopernyataan"])) {
        $nopernyataan    = validTeks3($iyem["nopernyataan"],20);
        $photo            = validTeks3($iyem["photo"],50);
        $querypernyataan = bukaquery(
            "select DATE_FORMAT(surat_serah_terima_barang_anggota_tubuh.tanggal,'%d-%m-%Y') as tanggal,surat_serah_terima_barang_anggota_tubuh.jenis_barang,".
            "surat_serah_terima_barang_anggota_tubuh.uraian_barang,surat_serah_terima_barang_anggota_tubuh.jumlah_barang,surat_serah_terima_barang_anggota_tubuh.kondisi_barang,".
            "surat_serah_terima_barang_anggota_tubuh.wadah_label,surat_serah_terima_barang_anggota_tubuh.nama_pj,surat_serah_terima_barang_anggota_tubuh.no_ktppj,".
            "surat_serah_terima_barang_anggota_tubuh.alamatpj,surat_serah_terima_barang_anggota_tubuh.no_telppj,surat_serah_terima_barang_anggota_tubuh.hubungan ".
            "from surat_serah_terima_barang_anggota_tubuh where surat_serah_terima_barang_anggota_tubuh.no_pernyataan='$nopernyataan'"
        );
        if($rsquerypernyataan= mysqli_fetch_array($querypernyataan)){
            @$src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".$_SERVER['HTTP_HOST']."/webapps/pernyataanmenerimabarang/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>FORMULIR SERAH TERIMA BARANG / ANGGOTA TUBUH PASIEN<br/>No. $nopernyataan Tanggal ".$rsquerypernyataan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    DATA PASIEN :
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
                                    DATA YANG DISERAHKAN :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Jenis</td>
                                        <td width='75%'>: ".$rsquerypernyataan["jenis_barang"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Uraian</td>
                                        <td width='75%'>: ".$rsquerypernyataan["uraian_barang"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jumlah</td>
                                        <td width='75%'>: ".$rsquerypernyataan["jumlah_barang"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Kondisi</td>
                                        <td width='75%'>: ".$rsquerypernyataan["kondisi_barang"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Wadah / Label</td>
                                        <td width='75%'>: ".$rsquerypernyataan["wadah_label"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    DATA PENERIMA :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama</td>
                                        <td width='75%'>: ".$rsquerypernyataan["nama_pj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Alamat</td>
                                        <td width='75%'>: ".$rsquerypernyataan["alamatpj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>No.KTP/SIM</td>
                                        <td width='75%'>: ".$rsquerypernyataan["no_ktppj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Hubungan</td>
                                        <td width='75%'>: ".$rsquerypernyataan["hubungan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>No.Telp/HP</td>
                                        <td width='75%'>: ".$rsquerypernyataan["no_telppj"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    PERNYATAAN :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='100%' valign='top' align='justify'>Barang / anggota tubuh tersebut telah diterima dalam kondisi sesuai keterangan di atas, dan tanggung jawab beralih kepada penerima setelah serah terima dilakukan.</td>
                                    </tr>
                                </table>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Pernyataan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Pernyataan' src='$src' width='490px' height='420px'/><br/><br/>".$rsquerypernyataan["nama_pj"]."<br/><br/>
                                        <a href='index.php?act=PernyataanMenerimaBarang&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>FORMULIR SERAH TERIMA BARANG / ANGGOTA TUBUH PASIEN</center></h2>
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
            JSRedirect2("index.php?act=PernyataanMenerimaBarang&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>FORMULIR SERAH TERIMA BARANG / ANGGOTA TUBUH PASIEN</center></h2>
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
        JSRedirect2("index.php?act=PernyataanMenerimaBarang&hal=Persetujuan",4);
    }
?>