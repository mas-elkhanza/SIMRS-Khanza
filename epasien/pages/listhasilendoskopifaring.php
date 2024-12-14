<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat    = validTeks3($iyem["norawat"],20);
        $queryendoskopi = bukaquery(
            "select dokter.nm_dokter,hasil_endoskopi_faring_laring.diagnosa_klinis,hasil_endoskopi_faring_laring.faring_uvula,hasil_endoskopi_faring_laring.faring_arkus_faring,".
            "hasil_endoskopi_faring_laring.faring_dinding_posterior,hasil_endoskopi_faring_laring.faring_tonsil,hasil_endoskopi_faring_laring.laring_tonsil_lingual,".
            "hasil_endoskopi_faring_laring.laring_valekula,hasil_endoskopi_faring_laring.laring_sinus_piriformis,hasil_endoskopi_faring_laring.laring_epiglotis,".
            "hasil_endoskopi_faring_laring.laring_arytenoid,hasil_endoskopi_faring_laring.laring_plika_ventrikularis,hasil_endoskopi_faring_laring.laring_pita_suara,".
            "hasil_endoskopi_faring_laring.laring_rima_vocalis,hasil_endoskopi_faring_laring.laring_lainlain,hasil_endoskopi_faring_laring.kesan,hasil_endoskopi_faring_laring.saran ".
            "from hasil_endoskopi_faring_laring inner join dokter on hasil_endoskopi_faring_laring.kd_dokter=dokter.kd_dokter where hasil_endoskopi_faring_laring.no_rawat='".$norawat."'"
        );
        if($rsqueryendoskopi= mysqli_fetch_array($queryendoskopi)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL ENDOSKOPI FARING/LARINGOSKOPI</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Dokter Yang Memeriksa</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryendoskopi["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Diagnosa Klinis</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryendoskopi["diagnosa_klinis"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' colspan='3' align='left'>
                                            I. Pemeriksaan Faring
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr width='100%'>
                                                    <td valign='middle' width='30%'>Dinding Posterior</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["faring_dinding_posterior"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Arkus Faring</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["faring_arkus_faring"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Uvula</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["faring_uvula"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Tonsil</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["faring_tonsil"]."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' colspan='3' align='left'>
                                            II. Pemeriksaan Laring
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr width='100%'>
                                                    <td valign='middle' width='30%'>Tonsil Lingual</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["laring_tonsil_lingual"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Sinus Piriformis</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["laring_sinus_piriformis"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Plika Ventrikularis</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["laring_plika_ventrikularis"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Rima Vocalis</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["laring_rima_vocalis"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Valekula</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["laring_valekula"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Epiglotis</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["laring_epiglotis"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Arytenoid</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["laring_arytenoid"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Pita Suara</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["laring_pita_suara"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>Lain-lain</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["laring_lainlain"]."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' colspan='3' align='left'>
                                            III. Kesan
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr width='100%'>
                                                    <td valign='middle'>".$rsqueryendoskopi["kesan"]."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' colspan='3' align='left'>
                                            IV. Saran
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr width='100%'>
                                                    <td valign='middle'>".$rsqueryendoskopi["saran"]."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>";
            $querygambarendoskopi= bukaquery(
                "select hasil_endoskopi_faring_laring_gambar.photo from hasil_endoskopi_faring_laring_gambar where hasil_endoskopi_faring_laring_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarendoskopi= mysqli_fetch_array($querygambarendoskopi)){
                $src = 'data: '.@mime_content_type("http://".host()."/webapps/hasilpemeriksaanendoskopifaringlaring/".$rsquerygambarendoskopi["photo"]).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanendoskopifaringlaring/".$rsquerygambarendoskopi["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianEndoskopiFaring&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL ENDOSKOPI FARING/LARINGOSKOPI</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu hasil pemeriksaan endoskopi</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=AntrianEndoskopiFaring&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL ENDOSKOPI FARING/LARINGOSKOPI</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf hasil pemeriksaan endoskopi tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=AntrianEndoskopiFaring&hal=AntrianTindakan",4);
    }
?>
