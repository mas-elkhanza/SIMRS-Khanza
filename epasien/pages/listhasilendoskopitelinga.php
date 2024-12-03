<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat    = validTeks3($iyem["norawat"],20);
        $queryendoskopi = bukaquery(
            "select dokter.nm_dokter,hasil_endoskopi_telinga.diagnosa_klinis,hasil_endoskopi_telinga.bentuk_liang_telinga_kanan,hasil_endoskopi_telinga.bentuk_liang_telinga_kiri,".
            "hasil_endoskopi_telinga.kondisi_liang_telinga_kanan,hasil_endoskopi_telinga.keterangan_kondisi_liang_telinga_kanan,hasil_endoskopi_telinga.kondisi_liang_telinga_kiri,".
            "hasil_endoskopi_telinga.keterangan_kondisi_liang_telinga_kiri,hasil_endoskopi_telinga.membran_timpani_intak_kanan,hasil_endoskopi_telinga.membran_timpani_intak_kiri,".
            "hasil_endoskopi_telinga.membran_timpani_perforasi_kanan,hasil_endoskopi_telinga.keterangan_membran_timpani_perforasi_kanan,hasil_endoskopi_telinga.membran_timpani_perforasi_kiri,".
            "hasil_endoskopi_telinga.keterangan_membran_timpani_perforasi_kiri,hasil_endoskopi_telinga.kavum_timpani_mukosa_kanan,hasil_endoskopi_telinga.kavum_timpani_mukosa_kiri,".
            "hasil_endoskopi_telinga.kavum_timpani_osikel_kanan,hasil_endoskopi_telinga.kavum_timpani_osikel_kiri,hasil_endoskopi_telinga.kavum_timpani_isthmus_kanan,hasil_endoskopi_telinga.kavum_timpani_isthmus_kiri,".
            "hasil_endoskopi_telinga.kavum_timpani_anterior_kanan,hasil_endoskopi_telinga.kavum_timpani_anterior_kiri,hasil_endoskopi_telinga.kavum_timpani_posterior_kanan,hasil_endoskopi_telinga.kavum_timpani_posterior_kiri,".
            "hasil_endoskopi_telinga.lainlain_kanan,hasil_endoskopi_telinga.lainlain_kiri,hasil_endoskopi_telinga.kesimpulan,hasil_endoskopi_telinga.anjuran ".
            "from hasil_endoskopi_telinga inner join dokter on hasil_endoskopi_telinga.kd_dokter=dokter.kd_dokter where hasil_endoskopi_telinga.no_rawat='".$norawat."'"
        );
        if($rsqueryendoskopi= mysqli_fetch_array($queryendoskopi)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL ENDOSKOPI TELINGA</center></h2>
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
                                            Telinga Kanan : ".$rsqueryendoskopi["bentuk_liang_telinga_kanan"]."
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr width='100%'>
                                                    <td valign='middle' width='30%'>- Liang Telinga</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["kondisi_liang_telinga_kanan"].($rsqueryendoskopi["keterangan_kondisi_liang_telinga_kanan"]?"":",".$rsqueryendoskopi["keterangan_kondisi_liang_telinga_kanan"])."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' colspan='3'>
                                                        - Membran Timpani 
                                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Perforasi</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["membran_timpani_perforasi_kanan"].($rsqueryendoskopi["keterangan_membran_timpani_perforasi_kanan"]?"":",".$rsqueryendoskopi["keterangan_membran_timpani_perforasi_kanan"])."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Intak</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["membran_timpani_intak_kanan"]."</td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' colspan='3'>
                                                        - Kavum Timpani
                                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Mukosa</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_mukosa_kanan"]."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Osikel</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_osikel_kanan"]."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Isthmus</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_isthmus_kanan"]."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Anterior</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_anterior_kanan"]."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Posterior</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_posterior_kanan"]."</td>
                                                            </tr>
                                                        </table>
                                                    </td>
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
                                            Telinga Kiri : ".$rsqueryendoskopi["bentuk_liang_telinga_kiri"]."
                                            <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                <tr width='100%'>
                                                    <td valign='middle' width='30%'>- Liang Telinga</td>
                                                    <td valign='middle' width='1%'>:</td>                                        
                                                    <td valign='middle' width='69%'>".$rsqueryendoskopi["kondisi_liang_telinga_kiri"].($rsqueryendoskopi["keterangan_kondisi_liang_telinga_kiri"]?"":",".$rsqueryendoskopi["keterangan_kondisi_liang_telinga_kiri"])."</td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' colspan='3'>
                                                        - Membran Timpani
                                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Perforasi</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["membran_timpani_perforasi_kiri"].($rsqueryendoskopi["keterangan_membran_timpani_perforasi_kiri"]?"":",".$rsqueryendoskopi["keterangan_membran_timpani_perforasi_kiri"])."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Intak</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["membran_timpani_intak_kiri"]."</td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td valign='middle' colspan='3'>
                                                        - Kavum Timpani
                                                        <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Mukosa</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_mukosa_kiri"]."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Osikel</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_osikel_kiri"]."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Isthmus</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_isthmus_kiri"]."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Anterior</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_anterior_kiri"]."</td>
                                                            </tr>
                                                            <tr>
                                                                <td valign='middle' width='30%'>Posterior</td>
                                                                <td valign='middle' width='1%'>:</td>                                        
                                                                <td valign='middle' width='69%'>".$rsqueryendoskopi["kavum_timpani_posterior_kiri"]."</td>
                                                            </tr>
                                                        </table>
                                                    </td>
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
                                    </tr>
                                    <tr>
                                        <td valign='middle' colspan='3' align='left'>
                                            Anjuran : ".$rsqueryendoskopi["anjuran"]."
                                        </td>
                                    </tr>";
            $querygambarusg= bukaquery(
                "select hasil_endoskopi_telinga_gambar.photo from hasil_endoskopi_telinga_gambar where hasil_endoskopi_telinga_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarusg= mysqli_fetch_array($querygambarusg)){
                $src = 'data: '.mime_content_type("http://".host()."/webapps/hasilpemeriksaanendoskopitelinga/".$rsquerygambarusg["photo"]).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanendoskopitelinga/".$rsquerygambarusg["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianEndoskopiTelinga&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL ENDOSKOPI TELINGA</center></h2>
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
            JSRedirect2("index.php?act=AntrianEndoskopiTelinga&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL ENDOSKOPI TELINGA</center></h2>
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
        JSRedirect2("index.php?act=AntrianEndoskopiTelinga&hal=AntrianTindakan",4);
    }
?>
