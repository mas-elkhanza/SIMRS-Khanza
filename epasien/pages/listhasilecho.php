<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat  = validTeks3($iyem["norawat"],20);
        $queryecho = bukaquery(
            "select dokter.nm_dokter,hasil_pemeriksaan_echo.sistolik,hasil_pemeriksaan_echo.diastolic,hasil_pemeriksaan_echo.kontraktilitas,".
            "hasil_pemeriksaan_echo.dimensi_ruang,hasil_pemeriksaan_echo.katup,hasil_pemeriksaan_echo.analisa_segmental,hasil_pemeriksaan_echo.erap,".
            "hasil_pemeriksaan_echo.lain_lain,hasil_pemeriksaan_echo.kesimpulan from hasil_pemeriksaan_echo inner join dokter ".
            "on hasil_pemeriksaan_echo.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_echo.no_rawat='".$norawat."'"
        );
        if($rsqueryecho= mysqli_fetch_array($queryecho)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL ECHO</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Dokter Yang Memeriksa</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryecho["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Fungsi Sistolik LV</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryecho["sistolik"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Fungsi Diastolik LV</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryecho["diastolic"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kontaktilitas RV</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryecho["kontraktilitas"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Dimensi Ruang Jantung</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryecho["dimensi_ruang"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Katup-Katup</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryecho["katup"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Analisa Segmental</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryecho["analisa_segmental"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>eRAP</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryecho["erap"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Lain-lain</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryecho["lain_lain"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kesimpulan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryecho["kesimpulan"]."</td>
                                    </tr>";
            $querygambarecho= bukaquery(
                "select hasil_pemeriksaan_echo_gambar.photo from hasil_pemeriksaan_echo_gambar where hasil_pemeriksaan_echo_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarecho= mysqli_fetch_array($querygambarecho)){
                $src = 'data: '.@mime_content_type("http://".host()."/webapps/hasilpemeriksaanecho/".$rsquerygambarecho["photo"]).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanecho/".$rsquerygambarecho["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianEcho&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL ECHO</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu hasil pemeriksaan echo</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=AntrianEcho&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL ECHO</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf hasil pemeriksaan echo tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=AntrianEcho&hal=AntrianTindakan",4);
    }
?>
