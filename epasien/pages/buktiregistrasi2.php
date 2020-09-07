<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }

    $norawat = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $norawat = json_decode(encrypt_decrypt($norawat,"d"),true); 
    $norawat = $norawat["norawat"];
    if (isset($norawat)) {
        $queryregistrasi = bukaquery2("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,
               reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur as umur,poliklinik.nm_poli,
               reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
               from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab on reg_periksa.kd_dokter=dokter.kd_dokter 
               and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli 
               where reg_periksa.no_rawat='$norawat'");
        if($rsqueryregistrasi = mysqli_fetch_array($queryregistrasi)) {
            echo "<div class='block-header'>
                    <h2><center>BUKTI REGISTRASI PENDAFTARAN</center></h2>
                </div>
                <div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <div class='table-responsive'>
                                    <table class='table table-hover dataTable'>
                                        <tr>
                                           <td width='30%'>Tanggal</td>
                                           <td width='70%'> : ".$rsqueryregistrasi["tgl_registrasi"]." ".$rsqueryregistrasi["jam_reg"]."</td>
                                        </tr>
                                        <tr>
                                           <td>No. Rawat</td>
                                           <td> : ".$rsqueryregistrasi["no_rawat"]."</td>
                                        </tr>
                                        <tr>
                                           <td>No. Antri Poli</td>
                                           <td> : ".$rsqueryregistrasi["no_reg"]."</td>
                                        </tr>
                                        <tr>
                                           <td>Nama</td>
                                           <td> : ".$rsqueryregistrasi["nm_pasien"]."</td>
                                        </tr>
                                        <tr>
                                           <td>No. RM</td>
                                           <td> : ".$rsqueryregistrasi["no_rkm_medis"]."</td>
                                        </tr>
                                        <tr>
                                           <td>Jenis Kelamin</td>
                                           <td> : ".($rsqueryregistrasi["jk"]=="L"?"Laki-Laki":"Perempuan")."</td>
                                        </tr>
                                        <tr>
                                           <td>Umur</td>
                                           <td> : ".$rsqueryregistrasi["umur"]."</td>
                                        </tr>
                                        <tr>
                                           <td>No.HP/Telp</td>
                                           <td> : ".$rsqueryregistrasi["no_tlp"]."</td>
                                        </tr>
                                        <tr>
                                           <td>Penanggung Jawab</td>
                                           <td> : ".$rsqueryregistrasi["p_jawab"]."</td>
                                        </tr>
                                        <tr>
                                           <td>Alamat P.J.</td>
                                           <td> : ".$rsqueryregistrasi["almt_pj"]."</td>
                                        </tr>
                                        <tr>
                                           <td>Jenis Bayar</td>
                                           <td> : ".$rsqueryregistrasi["png_jawab"]."</td>
                                        </tr>
                                        <tr>
                                           <td>Ruang/Unit/Poli</td>
                                           <td> : ".$rsqueryregistrasi["nm_poli"]."</td>
                                        </tr>
                                        <tr>
                                           <td>Dokter</td>
                                           <td> : ".$rsqueryregistrasi["nm_dokter"]."</td>
                                        </tr>
                                   </table>
                                </div>
                                <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>BUKTI PENDAFTARAN</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Kami tidak menemukan data pendaftaran Anda, kemungkinan ada perubahan pada data pendaftaran Anda</center><br/>
                                <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=HomeUser&hal=Beranda",10);
        }
    }else{
        echo "<div class='block-header'>
                        <h2><center>BUKTI PENDAFTARAN</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Kami tidak menemukan data pendaftaran anda</center><br/>
                                <center><a href='index.php?act=HomeUser&hal=Beranda' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=HomeUser&hal=Beranda",5);
    }
        
?>

