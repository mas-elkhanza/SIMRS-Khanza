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
            "select surat_persetujuan_rawat_inap.tanggal,surat_persetujuan_rawat_inap.nama_pj,surat_persetujuan_rawat_inap.no_ktppj,surat_persetujuan_rawat_inap.pendidikan_pj,surat_persetujuan_rawat_inap.alamatpj,".
            "surat_persetujuan_rawat_inap.no_telppj,surat_persetujuan_rawat_inap.ruang,surat_persetujuan_rawat_inap.kelas,surat_persetujuan_rawat_inap.hubungan,surat_persetujuan_rawat_inap.hak_kelas,".
            "surat_persetujuan_rawat_inap.nama_alamat_keluarga_terdekat,surat_persetujuan_rawat_inap.bayar_secara from surat_persetujuan_rawat_inap where surat_persetujuan_rawat_inap.no_surat='$nopersetujuan'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src = 'data: '.@mime_content_type("http://".host()."/webapps/persetujuanrawatinap/".$photo).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/persetujuanrawatinap/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN PASIEN RAWAT INAP<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Saya yang membuat persetujuan di bawah ini :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["nama_pj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Alamat</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["alamatpj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>No.KTP/SIM</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_ktppj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Pendidikan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["pendidikan_pj"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>No.Telp/HP</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["no_telppj"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Terhadap Pasien : 
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
                                        <td width='75%'>: ".$_SESSION["jk"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tempat Lahir</td>
                                        <td width='75%'>: ".$_SESSION["tmp_lahir"]."</td>
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
                                    Dengan ini menyatakan dengan sesungguhnya bahwa saya setuju untuk dilakukan Rawat Inap di ".$_SESSION["nama_instansi"]." di ruang : ".$rsquerypersetujuan["ruang"]." Kelas : ".$rsquerypersetujuan["kelas"]." Terhadap ".$rsquerypersetujuan["hubungan"]."
                                    <br/>
                                    Hak kelas perawatan : ".$rsquerypersetujuan["hak_kelas"]." 
                                    <br/>
                                    Nama dan alamat keluarga terdekat : ".$rsquerypersetujuan["nama_alamat_keluarga_terdekat"]." 
                                    <br/>
                                    Demi kelancaran pelayanan perawatan, pengobatan dan administrasi, dengan ini juga menyatakan :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='5%' valign='top'>a.</td>
                                        <td width='95%' valign='top' align='justify'>Setuju dan memberi ijin kepada dokter yang bersangkutan untuk merawat saya/pasien tersebut diatas</td>
                                    </tr>
                                    <tr>
                                        <td width='5%' valign='top'>b.</td>
                                        <td width='95%' valign='top' align='justify'>Dengan ini menyatakan dengan sesungguhnya bahwa seluruh pembiayaan pelayanan di ".$_SESSION["nama_instansi"]." akan saya bayarkan secara ".$rsquerypersetujuan["bayar_secara"].", dan bersedia untuk melengkapi berkas kelengkapannya. Apabila dalam waktu 3 x 24 Jam tidak dapat menunjukkan kartu/kelengkapan lainnya, maka saya siap untuk membayarkan semua pelayanan dan tindakan di ".$_SESSION["nama_instansi"].".</td>
                                    </tr>
                                    <tr>
                                        <td width='5%' valign='top'>c.</td>
                                        <td width='95%' valign='top' align='justify'>Telah menyetujui dan bersedia mentaati segala peraturan yang berlaku di ".$_SESSION["nama_instansi"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='5%' valign='top'>d.</td>
                                        <td width='95%' valign='top' align='justify'>Memberi kuasa kepada Dokter untuk memberikan keterangan yang diperlukan oleh pihak penanggung biaya perawatan saya / pasien tersebut diatas</td>
                                    </tr>
                                </table>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Persetujuan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>".$rsquerypersetujuan["nama_pj"]."<br/><br/>
                                        <a href='index.php?act=PersetujuanRawatInap&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERSETUJUAN PASIEN RAWAT INAP</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanRawatInap&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN PASIEN RAWAT INAP</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanRawatInap&hal=Persetujuan",4);
    }
?>