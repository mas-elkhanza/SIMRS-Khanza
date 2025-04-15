<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat    = validTeks3($iyem["norawat"],20);
        $queryusgneonatus = bukaquery(
            "select dokter.nm_dokter,hasil_pemeriksaan_usg_neonatus.diagnosa_klinis,hasil_pemeriksaan_usg_neonatus.ventrikal_sinistra,hasil_pemeriksaan_usg_neonatus.ventrikal_dextra,hasil_pemeriksaan_usg_neonatus.kesan,hasil_pemeriksaan_usg_neonatus.kesimpulan,".
            "hasil_pemeriksaan_usg_neonatus.saran from hasil_pemeriksaan_usg_neonatus inner join dokter on hasil_pemeriksaan_usg_neonatus.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_usg_neonatus.no_rawat='".$norawat."'"
        );
        if($rsqueryusgneonatus= mysqli_fetch_array($queryusgneonatus)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL USG NEONATUS</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Ventrikel Sinistra</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusgneonatus["ventrikal_sinistra"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Ventrikel Dextra</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusgneonatus["ventrikal_dextra"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kesan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgneonatus["kesan"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kesimpulan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgneonatus["kesimpulan"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Saran</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgneonatus["saran"]."</td>
                                    </tr>";
            $querygambarusg= bukaquery(
                "select hasil_pemeriksaan_usg_neonatus_gambar.photo from hasil_pemeriksaan_usg_neonatus_gambar where hasil_pemeriksaan_usg_neonatus_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarusg= mysqli_fetch_array($querygambarusg)){
                $src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanusgneonatus/".$rsquerygambarusg["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianUSGNeonatus&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL USG NEONATUS</center></h2>
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
            JSRedirect2("index.php?act=AntrianUSGNeonatus&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL USG NEONATUS</center></h2>
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
        JSRedirect2("index.php?act=AntrianUSGNeonatus&hal=AntrianTindakan",4);
    }
?>
