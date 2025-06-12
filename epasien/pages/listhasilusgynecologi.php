<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat    = validTeks3($iyem["norawat"],20);
        $queryusggynecologi = bukaquery(
            "select dokter.nm_dokter,hasil_pemeriksaan_usg_gynecologi.diagnosa_klinis,hasil_pemeriksaan_usg_gynecologi.uterus,hasil_pemeriksaan_usg_gynecologi.parametrium,hasil_pemeriksaan_usg_gynecologi.ovarium,hasil_pemeriksaan_usg_gynecologi.doppler,".
            "hasil_pemeriksaan_usg_gynecologi.kesimpulan from hasil_pemeriksaan_usg_gynecologi inner join dokter on hasil_pemeriksaan_usg_gynecologi.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_usg_gynecologi.no_rawat='".$norawat."'"
        );
        if($rsqueryusggynecologi= mysqli_fetch_array($queryusggynecologi)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL PEMERIKSAAN USG GYNECOLOGI</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Dokter Yang Memeriksa</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusggynecologi["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Diagnosa Klinis</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusggynecologi["diagnosa_klinis"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Uterus</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusggynecologi["uterus"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Parametrium</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusggynecologi["parametrium"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Ovarium</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusggynecologi["ovarium"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Doppler</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusggynecologi["doppler"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kesimpulan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusggynecologi["kesimpulan"]."</td>
                                    </tr>";
            $querygambarusg= bukaquery(
                "select hasil_pemeriksaan_usg_gynecologi_gambar.photo from hasil_pemeriksaan_usg_gynecologi_gambar where hasil_pemeriksaan_usg_gynecologi_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarusg= mysqli_fetch_array($querygambarusg)){
                $src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanusggynecologi/".$rsquerygambarusg["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianUSGGynecologi&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL USG GYNECOLOGI</center></h2>
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
            JSRedirect2("index.php?act=AntrianUSGGynecologi&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL USG GYNECOLOGI</center></h2>
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
        JSRedirect2("index.php?act=AntrianUSGGynecologi&hal=AntrianTindakan",4);
    }
?>
