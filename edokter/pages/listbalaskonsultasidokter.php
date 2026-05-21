<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["nopermintaan"])) {
        $nopermintaan    = validTeks3($iyem["nopermintaan"],20);
        $querypermintaan = bukaquery(
            "SELECT date_format(konsultasi_medik.tanggal,'%d-%m-%Y %H:%i:%s') as tanggalkonsultasi,konsultasi_medik.no_permintaan,reg_periksa.no_rkm_medis,pasien.nm_pasien,".
            "konsultasi_medik.jenis_permintaan,dokter.nm_dokter,konsultasi_medik.diagnosa_kerja,konsultasi_medik.uraian_konsultasi FROM konsultasi_medik ".
            "inner join reg_periksa on konsultasi_medik.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis ".
            "inner join dokter on konsultasi_medik.kd_dokter=dokter.kd_dokter WHERE konsultasi_medik.no_permintaan='$nopermintaan'"
        );
        if($rsquerypermintaan= mysqli_fetch_array($querypermintaan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>JAWAB KONSULTASI DOKTER</center></h2>
                            </div>
                            <div class='body'>
                                <form method='POST' onsubmit='return validasiIsi();' enctype=multipart/form-data>
                                    <input type='hidden' name='nopermintaan' value='$nopermintaan'>
                                    <h7>
                                        Data Konsultasi :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='30%'>No.Permintaan</td>
                                            <td width='75%'>: ".$rsquerypermintaan["no_permintaan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Tanggal</td>
                                            <td width='75%'>: ".$rsquerypermintaan["tanggalkonsultasi"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>No.RM</td>
                                            <td width='75%'>: ".$rsquerypermintaan["no_rkm_medis"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Nama Pasien</td>
                                            <td width='75%'>: ".$rsquerypermintaan["nm_pasien"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Dokter Konsul</td>
                                            <td width='75%'>: ".$rsquerypermintaan["nm_dokter"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Jenis Permintaan</td>
                                            <td width='75%'>: ".$rsquerypermintaan["jenis_permintaan"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Diagnosa Kerja</td>
                                            <td width='75%'>: ".$rsquerypermintaan["diagnosa_kerja"]."</td>
                                        </tr>
                                        <tr>
                                            <td width='30%'>Uraian Konsultasi</td>
                                            <td width='75%'>: ".nl2br($rsquerypermintaan["uraian_konsultasi"])."</td>
                                        </tr>
                                    </table>
                                    <h7>
                                        Jawaban Konsultasi :
                                    </h7>
                                    <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                        <tr>
                                            <td width='100%'>
                                                Diagnosa Kerja
                                                <div class=\"form-group\">
                                                    <div class=\"form-line\">
                                                        <input name=\"diagnosa_kerja\" type=\"text\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi1'));\" id=\"TxtIsi1\" pattern=\"[a-zA-Z0-9 -]{1,200}\" title=\" a-z A-Z 0-9 (Maksimal 200 karakter)\" placeholder=\"a-z A-Z 0-9 (Maksimal 200 karakter)\" class=\"form-control\" size=\"60\" maxlength=\"200\" autocomplete=\"off\" required autofocus/>
                                                        <span id=\"MsgIsi1\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width='100%'>
                                                Jawaban
                                                <div class=\"form-group\">
                                                    <div class=\"form-line\">
                                                        <textarea name=\"uraian_jawaban\" oninput=\"this.value=this.value.replace(/[^a-zA-Z0-9 \\-\\n]/g,''); setDefault(this, document.getElementById('MsgIsi2'));\" id=\"TxtIsi2\" class=\"form-control\" rows=\"5\" maxlength=\"800\" placeholder=\"a-z A-Z 0-9 (Maksimal 800 karakter)\" required></textarea>
                                                        <span id=\"MsgIsi2\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                    <center><button class='btn btn-success waves-effect' type='submit' name='BtnSimpan'>Simpan</button>&nbsp;&nbsp;<button class='btn btn-warning waves-effect' type='reset' name='BtnReset'>Reset</button>&nbsp;&nbsp;<a href='index.php?act=KonsultasiDokter' class='btn btn-danger waves-effect'>Kembali</a></center>
                                </form>
                            </div>
                        </div>
                    </div>
                  </div>";
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $nopermintaan   = validTeks4($_POST["nopermintaan"],20);
                $diagnosa_kerja = validTeks4($_POST["diagnosa_kerja"],200);
                $uraian_jawaban = validTeks4($_POST["uraian_jawaban"],800);
                $tgl            = date("Y-m-d H:i:s");
                if ((!empty($nopermintaan))&&(!empty($diagnosa_kerja))&&(!empty($uraian_jawaban))) {
                    try {
                        Tambah3("jawaban_konsultasi_medik","'$nopermintaan','$tgl','$diagnosa_kerja','$uraian_jawaban'");
                        JSRedirect("index.php?act=KonsultasiDokter");
                    } catch(mysqli_sql_exception $e){
                        if($e->getCode()==1062){
                            echo "<div class='row clearfix'>
                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                        <div class='card'>
                                            <div class='body bg-success'>
                                                <center><h4>Konsultasi ini sudah dijawab...!</h4></center>
                                            </div>
                                        </div>
                                    </div>
                                  </div>";
                        }else{
                            echo "<div class='row clearfix'>
                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                        <div class='card'>
                                            <div class='body bg-success'>
                                                <center><h4>Gagal menyimpan jawaban...!</h4></center>
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
                    <h2><center>JAWAB KONSULTASI DOKTER</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf permintaan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=KonsultasiDokter",4);
    }
?>