<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat          = validTeks3($iyem["norawat"],20);
        $photo            = validTeks3($iyem["photo"],50);
        $querypersetujuan = bukaquery(
            "select perencanaan_pemulangan.rencana_pulang,perencanaan_pemulangan.alasan_masuk,perencanaan_pemulangan.diagnosa_medis,perencanaan_pemulangan.pengaruh_ri_pasien_dan_keluarga,".
            "perencanaan_pemulangan.keterangan_pengaruh_ri_pasien_dan_keluarga,perencanaan_pemulangan.pengaruh_ri_pekerjaan_sekolah,perencanaan_pemulangan.keterangan_pengaruh_ri_pekerjaan_sekolah,".
            "perencanaan_pemulangan.pengaruh_ri_keuangan,perencanaan_pemulangan.keterangan_pengaruh_ri_keuangan,perencanaan_pemulangan.antisipasi_masalah_saat_pulang,".
            "perencanaan_pemulangan.keterangan_antisipasi_masalah_saat_pulang,perencanaan_pemulangan.bantuan_diperlukan_dalam,perencanaan_pemulangan.keterangan_bantuan_diperlukan_dalam,".
            "perencanaan_pemulangan.adakah_yang_membantu_keperluan,perencanaan_pemulangan.keterangan_adakah_yang_membantu_keperluan,perencanaan_pemulangan.pasien_tinggal_sendiri,".
            "perencanaan_pemulangan.keterangan_pasien_tinggal_sendiri,perencanaan_pemulangan.pasien_menggunakan_peralatan_medis,perencanaan_pemulangan.keterangan_pasien_menggunakan_peralatan_medis,".
            "perencanaan_pemulangan.pasien_memerlukan_alat_bantu,perencanaan_pemulangan.keterangan_pasien_memerlukan_alat_bantu,perencanaan_pemulangan.memerlukan_perawatan_khusus,".
            "perencanaan_pemulangan.keterangan_memerlukan_perawatan_khusus,perencanaan_pemulangan.bermasalah_memenuhi_kebutuhan,perencanaan_pemulangan.keterangan_bermasalah_memenuhi_kebutuhan,".
            "perencanaan_pemulangan.memiliki_nyeri_kronis,perencanaan_pemulangan.keterangan_memiliki_nyeri_kronis,perencanaan_pemulangan.memerlukan_edukasi_kesehatan,".
            "perencanaan_pemulangan.keterangan_memerlukan_edukasi_kesehatan,perencanaan_pemulangan.memerlukan_keterampilkan_khusus,perencanaan_pemulangan.keterangan_memerlukan_keterampilkan_khusus,".
            "perencanaan_pemulangan.nama_pasien_keluarga,reg_periksa.tgl_registrasi,reg_periksa.jam_reg from perencanaan_pemulangan ".
            "inner join reg_periksa on reg_periksa.no_rawat=perencanaan_pemulangan.no_rawat where perencanaan_pemulangan.no_rawat='".$norawat."'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src = 'data: '.mime_content_type("http://".host()."/webapps/perencanaanpemulangan/".$photo).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/perencanaanpemulangan/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>RENCANA PEMULANGAN PASIEN<br/>No.Rawat $norawat</center></h2>
                            </div>
                            <div class='body'>
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
                                        <td width='25%'>Masuk Dirawat</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["tgl_registrasi"]." ".$rsquerypersetujuan["jam_reg"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Diagnosa Medis</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["diagnosa_medis"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Alasan Masuk / Dirawat</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["alasan_masuk"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Rencana Pemulangan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["rencana_pulang"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2'>&nbsp;</td>
                                    </tr>
                                    <tr align='justify'>
                                        <td width='100%' colspan='2' align='justify'>1. Pengaruh Rawat Inap Terhadap :</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>Pasien & Keluarga Pasien  : ".$rsquerypersetujuan["pengaruh_ri_pasien_dan_keluarga"].($rsquerypersetujuan["keterangan_pengaruh_ri_pasien_dan_keluarga"]==""?"":", ".$rsquerypersetujuan["keterangan_pengaruh_ri_pasien_dan_keluarga"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>Pekerjaan / Sekolah : ".$rsquerypersetujuan["pengaruh_ri_pekerjaan_sekolah"].($rsquerypersetujuan["keterangan_pengaruh_ri_pekerjaan_sekolah"]==""?"":", ".$rsquerypersetujuan["keterangan_pengaruh_ri_pekerjaan_sekolah"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>Keuangan : ".$rsquerypersetujuan["pengaruh_ri_keuangan"].($rsquerypersetujuan["keterangan_pengaruh_ri_keuangan"]==""?"":", ".$rsquerypersetujuan["keterangan_pengaruh_ri_keuangan"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>2. Antisipasi Terhadap Masalah Saat Pulang ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["antisipasi_masalah_saat_pulang"].($rsquerypersetujuan["keterangan_antisipasi_masalah_saat_pulang"]==""?"":", ".$rsquerypersetujuan["keterangan_antisipasi_masalah_saat_pulang"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>3. Bantuan Diperlukan Dalam Hal ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["bantuan_diperlukan_dalam"].($rsquerypersetujuan["keterangan_bantuan_diperlukan_dalam"]==""?"":", ".$rsquerypersetujuan["keterangan_bantuan_diperlukan_dalam"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>4. Adakah Yang Membantu Keperluan Di Atas ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["adakah_yang_membantu_keperluan"].($rsquerypersetujuan["keterangan_adakah_yang_membantu_keperluan"]==""?"":", ".$rsquerypersetujuan["keterangan_adakah_yang_membantu_keperluan"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>5. Apakah Pasien Tinggal Sendiri Setelah Keluar Dari Rumah Sakit ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["pasien_tinggal_sendiri"].($rsquerypersetujuan["keterangan_pasien_tinggal_sendiri"]==""?"":", ".$rsquerypersetujuan["keterangan_pasien_tinggal_sendiri"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>6. Apakah Pasien Menggunakan Peralatan Medis (Kateter, NGT, Oksigen, Dll) Di Rumah Setelah Keluar / Pulang ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["pasien_menggunakan_peralatan_medis"].($rsquerypersetujuan["keterangan_pasien_menggunakan_peralatan_medis"]==""?"":", ".$rsquerypersetujuan["keterangan_pasien_menggunakan_peralatan_medis"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>7. Apakah Pasien Memerlukan Alat Bantu (Tongkat, Kursi Roda, Walker, Dll) Setelah Keluar Keluar / Pulang ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["pasien_memerlukan_alat_bantu"].($rsquerypersetujuan["keterangan_pasien_memerlukan_alat_bantu"]==""?"":", ".$rsquerypersetujuan["keterangan_pasien_memerlukan_alat_bantu"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>8. Apakah Memerlukan Bantuan / Perawatan Khusus (Homecare, Home Visit) Di Rumah Setelah Keluar / Pulang ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["memerlukan_perawatan_khusus"].($rsquerypersetujuan["keterangan_memerlukan_perawatan_khusus"]==""?"":", ".$rsquerypersetujuan["keterangan_memerlukan_perawatan_khusus"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>9. Apakah Pasien Bermasalah Dalam Memenuhi Kebutuhan Pribadinya (Makan, Minum, BAK, BAB, Dll) Setelah Keluar / Pulang ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["bermasalah_memenuhi_kebutuhan"].($rsquerypersetujuan["keterangan_bermasalah_memenuhi_kebutuhan"]==""?"":", ".$rsquerypersetujuan["keterangan_bermasalah_memenuhi_kebutuhan"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>10. Apakah Pasien Memiliki Nyeri Kronis Dan Kelelahan Setelah Keluar / Pulang ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["memiliki_nyeri_kronis"].($rsquerypersetujuan["keterangan_memiliki_nyeri_kronis"]==""?"":", ".$rsquerypersetujuan["keterangan_memiliki_nyeri_kronis"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>11. Apakah Pasien & Keluarga Memerlukan Edukasi Kesehatan (Obatan-obatan, Efek Samping Obat, Nyeri Diit, Mencari Pertolongan, Follow Up, Dll) Setelah Keluar / Pulang ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["memerlukan_edukasi_kesehatan"].($rsquerypersetujuan["keterangan_memerlukan_edukasi_kesehatan"]==""?"":", ".$rsquerypersetujuan["keterangan_memerlukan_edukasi_kesehatan"])."</p></td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'>12. Apakah Pasien Dan Keluarga Memerlukan Keterampilan Khusus (Perawatan Luka, Injeksi, Perawatan Bayi, Dll) Setelah Keluar / Pulang ?</td>
                                    </tr>
                                    <tr>
                                        <td width='100%' colspan='2' align='justify'><p style = 'margin-left: 15px;height: 4px;'>".$rsquerypersetujuan["memerlukan_keterampilkan_khusus"].($rsquerypersetujuan["keterangan_memerlukan_keterampilkan_khusus"]==""?"":", ".$rsquerypersetujuan["keterangan_memerlukan_keterampilkan_khusus"])."</p></td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Bahwa saya telah membaca, memahami dan mendapatkan penjelasan tentang ketentuan yang berlaku di ".$_SESSION["nama_instansi"].". Demikian pernyataan ini dibuat dalam keadaan penuh kesadaran dan tanpa paksaan dari pihak manapun, untuk digunakan sebagaimana mestinya
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Pasien/Keluarga</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>".$rsquerypersetujuan["nama_pasien_keluarga"]."<br/><br/>
                                        <a href='index.php?act=PersetujuanRencanaPemulangan&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>RENCANA PEMULANGAN PASIEN</center></h2>
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
            JSRedirect2("index.php?act=PersetujuanRencanaPemulangan&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>RENCANA PEMULANGAN PASIEN</center></h2>
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
        JSRedirect2("index.php?act=PersetujuanRencanaPemulangan&hal=Persetujuan",4);
    }
?>