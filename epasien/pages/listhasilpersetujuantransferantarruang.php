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
            "select transfer_pasien_antar_ruang.nama_menyetujui,transfer_pasien_antar_ruang.hubungan_menyetujui,transfer_pasien_antar_ruang.pasien_keluarga_menyetujui ".
            "from transfer_pasien_antar_ruang where transfer_pasien_antar_ruang.tanggal_masuk='$tanggal' and transfer_pasien_antar_ruang.no_rawat='$norawat'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            $photo = getOne("select bukti_persetujuan_transfer_pasien_antar_ruang.photo from bukti_persetujuan_transfer_pasien_antar_ruang where bukti_persetujuan_transfer_pasien_antar_ruang.no_rawat='".$norawat."' and bukti_persetujuan_transfer_pasien_antar_ruang.tanggal_masuk='".$tanggal."'");
            @$src   = 'data: '.@mime_content_type("http://".host()."/webapps/persetujuantransferruang/".$photo).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/persetujuantransferruang/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN PEMINDAHAN/TRANSFER RUANG PASIEN<br/>No.Rawat $norawat Tanggal $tanggal</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Saya yang membuat persetujuan ini menerangkan bahwa :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["nama_menyetujui"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Hubungan Dengan Pasien</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["hubungan_menyetujui"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Menyatakan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["pasien_keluarga_menyetujui"]."&nbsp;Menyetujui Pemindahan Pasien</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2'>Dari pasien ".$_SESSION["nama_instansi"]." dengan : </td>
                                    </tr>
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
                                    Bahwa saya telah membaca, memahami dan mendapatkan penjelasan tentang kondisi pasien di ".$_SESSION["nama_instansi"].". Demikian persetujuan ini dibuat dalam keadaan penuh kesadaran dan tanpa paksaan dari pihak manapun, untuk digunakan sebagaimana mestinya
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Persetujuan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>".$rsquerypersetujuan["nama_menyetujui"]."<br/><br/>
                                        <a href='index.php?act=PersetujuanTransferAntarRuang&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERSETUJUAN PEMINDAHAN/TRANSFER RUANG PASIEN</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanTransferAntarRuang&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN PEMINDAHAN/TRANSFER RUANG PASIEN</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanTransferAntarRuang&hal=Persetujuan",4);
    }
?>