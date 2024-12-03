<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat    = validTeks3($iyem["norawat"],20);
        $queryendoskopi = bukaquery(
            "select dokter.nm_dokter,hasil_endoskopi_hidung.diagnosa_klinis,hasil_endoskopi_hidung.kondisi_hidung_kanan,hasil_endoskopi_hidung.kondisi_hidung_kiri,".
            "hasil_endoskopi_hidung.kavum_nasi_kanan,hasil_endoskopi_hidung.kavum_nasi_kiri,hasil_endoskopi_hidung.konka_inferior_kanan,hasil_endoskopi_hidung.konka_inferior_kiri,".
            "hasil_endoskopi_hidung.meatus_medius_kanan,hasil_endoskopi_hidung.meatus_medius_kiri,hasil_endoskopi_hidung.septum_kanan,hasil_endoskopi_hidung.septum_kiri,".
            "hasil_endoskopi_hidung.nasofaring_kanan,hasil_endoskopi_hidung.nasofaring_kiri,hasil_endoskopi_hidung.lainlain_kanan,hasil_endoskopi_hidung.lainlain_kiri,hasil_endoskopi_hidung.kesimpulan ".
            "from hasil_endoskopi_hidung inner join dokter on hasil_endoskopi_hidung.kd_dokter=dokter.kd_dokter where hasil_endoskopi_hidung.no_rawat='".$norawat."'"
        );
        if($rsqueryendoskopi= mysqli_fetch_array($queryendoskopi)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL ENDOSKOPI HIDUNG</center></h2>
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
                                            Hidung Kanan : ".$rsqueryendoskopi["kondisi_hidung_kanan"]."
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr width='100%'>
                                                    <td valign='middle' width='30%'>- Kavum Nasi</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_nasi_kanan"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Konka Inferior</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["konka_inferior_kanan"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Meatus Medius</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["meatus_medius_kanan"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Septum</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["septum_kanan"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Nasofaring</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["nasofaring_kanan"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Lain-lain</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["lainlain_kanan"]."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' colspan='3' align='left'>
                                            Hidung Kiri : ".$rsqueryendoskopi["kondisi_hidung_kiri"]."
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr width='100%'>
                                                    <td valign='middle' width='30%'>- Kavum Nasi</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_nasi_kiri"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Konka Inferior</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["konka_inferior_kiri"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Meatus Medius</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["meatus_medius_kiri"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Septum</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["septum_kiri"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Nasofaring</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["nasofaring_kiri"]."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' width='30%'>- Lain-lain</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["lainlain_kiri"]."</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' colspan='3' align='left'>
                                            Kesimpulan : ".$rsqueryendoskopi["kesimpulan"]."
                                        </td>
                                    </tr>";
            $querygambarusg= bukaquery(
                "select hasil_endoskopi_hidung_gambar.photo from hasil_endoskopi_hidung_gambar where hasil_endoskopi_hidung_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarusg= mysqli_fetch_array($querygambarusg)){
                $src = 'data: '.mime_content_type("http://".host()."/webapps/hasilpemeriksaanendoskopihidung/".$rsquerygambarusg["photo"]).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanendoskopihidung/".$rsquerygambarusg["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianEndoskopiHidung&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL ENDOSKOPI HIDUNG</center></h2>
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
            JSRedirect2("index.php?act=AntrianEndoskopiHidung&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL ENDOSKOPI HIDUNG</center></h2>
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
        JSRedirect2("index.php?act=AntrianEndoskopiHidung&hal=AntrianTindakan",4);
    }
?>
