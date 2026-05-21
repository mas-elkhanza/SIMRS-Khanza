<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true);
    if(isset($iyem["norawat"])){
        $norawat    = validTeks3($iyem["norawat"],17);
        $tglperiksa = validTeks3($iyem["tglperiksa"],10);
        $jam        = validTeks4($iyem["jam"],8);
        
        $querydetail = bukaquery(
            "SELECT date_format(periksa_radiologi.tgl_periksa,'%d-%m-%Y') as tanggalperiksa, periksa_radiologi.jam, ".
            "periksa_radiologi.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, ".
            "dokterperujuk.nm_dokter as nm_dokter_perujuk, jns_perawatan_radiologi.nm_perawatan FROM periksa_radiologi ".
            "inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat ".
            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis ".
            "inner join dokter as dokterperujuk on periksa_radiologi.dokter_perujuk=dokterperujuk.kd_dokter ".
            "inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw ".
            "WHERE periksa_radiologi.no_rawat='$norawat' AND periksa_radiologi.tgl_periksa='$tglperiksa' AND periksa_radiologi.jam='$jam'"
        );
        if($rsdetail = mysqli_fetch_array($querydetail)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL BACAAN RADIOLOGI</center></h2>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='norawat' value='$norawat'>
                                    <input type='hidden' name='tglperiksa' value='$tglperiksa'>
                                    <input type='hidden' name='jam' value='$jam'>
                                    <h7>Data Pemeriksaan :</h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='30%'>No.Rawat</td>
                                            <td width='75%'>: ".$rsdetail["no_rawat"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Tanggal</td>
                                            <td width='75%'>: ".$rsdetail["tanggalperiksa"]." ".$rsdetail["jam"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>No.RM</td>
                                            <td width='75%'>: ".$rsdetail["no_rkm_medis"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Nama Pasien</td>
                                            <td width='75%'>: ".$rsdetail["nm_pasien"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Dokter Perujuk</td>
                                            <td width='75%'>: ".$rsdetail["nm_dokter_perujuk"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Jenis Pemeriksaan</td>
                                            <td width='75%'>: ".$rsdetail["nm_perawatan"]."</td>
                                        </tr>
                                    </table>
                                    <h7>Gambar Radiologi :</h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>";
            $lokasigambar=getOne("SELECT lokasi_gambar FROM gambar_radiologi WHERE no_rawat='$norawat' AND tgl_periksa='$tglperiksa' AND jam='$jam'");
            if(!empty($lokasigambar)){
                $src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/radiologi/".$lokasigambar));
                echo "<tr>
                        <td valign='top' align='center' width='100%'><img alt='Gambar Radiologi' src='$src' style='max-width:100%; height:auto;'/></td>
                      </tr>";
            }
            echo "                  </table>
                                    <h7>Hasil Bacaan :</h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='100%'>
                                                <div class=\"form-group\">
                                                    <div class=\"form-line\">
                                                        <textarea name=\"hasil\" oninput=\"this.value=this.value.replace(/[^a-zA-Z0-9 \\-\\n]/g,''); setDefault(this, document.getElementById('MsgIsi1'));\" id=\"TxtIsi1\" class=\"form-control\" rows=\"7\" maxlength=\"2000\" placeholder=\"a-z A-Z 0-9 (Maksimal 2000 karakter)\" required></textarea>
                                                        <span id=\"MsgIsi1\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                    <center><button class='btn btn-success waves-effect' type='submit' name='BtnSimpan'>Simpan</button>&nbsp;&nbsp;<button class='btn btn-warning waves-effect' type='reset' name='BtnReset'>Reset</button>&nbsp;&nbsp;<a href='index.php?act=HasilRadiologi' class='btn btn-danger waves-effect'>Kembali</a></center>
                                </form>
                            </div>
                        </div>
                    </div>
                  </div>";
            $BtnSimpan = isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if(isset($BtnSimpan)){
                $norawat    = validTeks4($_POST["norawat"],17);
                $tglperiksa = validTeks4($_POST["tglperiksa"],10);
                $jam        = validTeks4($_POST["jam"],8);
                $hasil      = validTeks4($_POST["hasil"],2000);
                if((!empty($norawat))&&(!empty($tglperiksa))&&(!empty($jam))&&(!empty($hasil))){
                    try {
                        Tambah3("hasil_radiologi","'$norawat','$tglperiksa','$jam','$hasil'");
                        JSRedirect("index.php?act=HasilRadiologi");
                    } catch(mysqli_sql_exception $e){
                        if($e->getCode()==1062){
                            echo "<div class='row clearfix'>
                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                        <div class='card'>
                                            <div class='body bg-success'>
                                                <center><h4>Hasil bacaan sudah diisi...!</h4></center>
                                            </div>
                                        </div>
                                    </div>
                                  </div>";
                        }else{
                            echo "<div class='row clearfix'>
                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                        <div class='card'>
                                            <div class='body bg-success'>
                                                <center><h4>Gagal menyimpan hasil bacaan...!</h4></center>
                                            </div>
                                        </div>
                                    </div>
                                  </div>";
                        }
                    }
                }else{
                    echo "<div class='row clearfix'>
                            <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                <div class='card'>
                                    <div class='body bg-success'>
                                        <center><h4>Semua field wajib diisi...!</h4></center>
                                    </div>
                                </div>
                            </div>
                          </div>";
                }
            }
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL BACAAN RADIOLOGI</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf data tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=HasilRadiologi",4);
    }
?>