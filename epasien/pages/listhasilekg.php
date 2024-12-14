<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat  = validTeks3($iyem["norawat"],20);
        $queryekg = bukaquery(
            "select dokter.nm_dokter,hasil_pemeriksaan_ekg.diagnosa_klinis,hasil_pemeriksaan_ekg.irama,hasil_pemeriksaan_ekg.laju_jantung,".
            "hasil_pemeriksaan_ekg.gelombangp,hasil_pemeriksaan_ekg.intervalpr,hasil_pemeriksaan_ekg.axis,hasil_pemeriksaan_ekg.kompleksqrs,".
            "hasil_pemeriksaan_ekg.segmenst,hasil_pemeriksaan_ekg.gelombangt,hasil_pemeriksaan_ekg.kesimpulan from hasil_pemeriksaan_ekg ".
            "inner join dokter on hasil_pemeriksaan_ekg.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_ekg.no_rawat='".$norawat."'"
        );
        if($rsqueryekg= mysqli_fetch_array($queryekg)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL EKG</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Dokter Yang Memeriksa</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryekg["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Diagnosa Klinis</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryekg["diagnosa_klinis"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Irama</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryekg["irama"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Laju Jantung</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryekg["laju_jantung"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Gelombang P</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryekg["gelombangp"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Interval PR</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryekg["intervalpr"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Axis</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryekg["axis"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kompleks QRS</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryekg["kompleksqrs"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Segmen ST</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryekg["segmenst"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Gelombang T</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryekg["gelombangt"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kesimpulan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryekg["kesimpulan"]."</td>
                                    </tr>";
            $querygambarekg= bukaquery(
                "select hasil_pemeriksaan_ekg_gambar.photo from hasil_pemeriksaan_ekg_gambar where hasil_pemeriksaan_ekg_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarekg= mysqli_fetch_array($querygambarekg)){
                $src = 'data: '.@mime_content_type("http://".host()."/webapps/hasilpemeriksaanekg/".$rsquerygambarekg["photo"]).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanekg/".$rsquerygambarekg["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianEKG&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL EKG</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu hasil pemeriksaan ekg</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=AntrianEKG&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL EKG</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf hasil pemeriksaan ekg tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=AntrianEKG&hal=AntrianTindakan",4);
    }
?>
