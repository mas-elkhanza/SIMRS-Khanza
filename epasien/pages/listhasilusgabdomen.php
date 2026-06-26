<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat    = validTeks3($iyem["norawat"],20);
        $queryusgabdomen = bukaquery(
            "select dokter.nm_dokter,hasil_pemeriksaan_usg_abdomen.diagnosa_klinis,hasil_pemeriksaan_usg_abdomen.esofagus,hasil_pemeriksaan_usg_abdomen.colon,hasil_pemeriksaan_usg_abdomen.gaster,hasil_pemeriksaan_usg_abdomen.hepar,".
            "hasil_pemeriksaan_usg_abdomen.gall_blader,hasil_pemeriksaan_usg_abdomen.lien,hasil_pemeriksaan_usg_abdomen.pancreas,hasil_pemeriksaan_usg_abdomen.ginjal_dextra,hasil_pemeriksaan_usg_abdomen.ginjal_sinistra,".
            "hasil_pemeriksaan_usg_abdomen.kesimpulan from hasil_pemeriksaan_usg_abdomen inner join dokter on hasil_pemeriksaan_usg_abdomen.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_usg_abdomen.no_rawat='".$norawat."'"
        );
        if($rsqueryusgabdomen= mysqli_fetch_array($queryusgabdomen)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL PEMERIKSAAN USG ABDOMEN</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Dokter Yang Memeriksa</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Diagnosa Klinis</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["diagnosa_klinis"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Esofagus</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["esofagus"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Colon</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["colon"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Gaster</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["gaster"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Hepar</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["hepar"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Gall Blader</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["gall_blader"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Lien</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["lien"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Pancreas</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["pancreas"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Ginjal Dextra</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["ginjal_dextra"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Ginjal Sinistra</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["ginjal_sinistra"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kesimpulan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgabdomen["kesimpulan"]."</td>
                                    </tr>";
            $querygambarusg= bukaquery(
                "select hasil_pemeriksaan_usg_abdomen_gambar.photo from hasil_pemeriksaan_usg_abdomen_gambar where hasil_pemeriksaan_usg_abdomen_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarusg= mysqli_fetch_array($querygambarusg)){
                $src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".$_SERVER['HTTP_HOST']."/webapps/hasilpemeriksaanusgabdomen/".$rsquerygambarusg["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianUSGAbdomen&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL USG ABDOMEN</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu hasil pemeriksaan usg</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=AntrianUSGAbdomen&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL USG ABDOMEN</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf hasil pemeriksaan usg tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=AntrianUSGAbdomen&hal=AntrianTindakan",4);
    }
?>
