<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat    = validTeks3($iyem["norawat"],20);
        $queryusgurologi = bukaquery(
            "select dokter.nm_dokter,hasil_pemeriksaan_usg_urologi.diagnosa_klinis,hasil_pemeriksaan_usg_urologi.ginjal_kanan,hasil_pemeriksaan_usg_urologi.ginjal_kiri,hasil_pemeriksaan_usg_urologi.vesica_urinaria,".
            "hasil_pemeriksaan_usg_urologi.tambahan from hasil_pemeriksaan_usg_urologi inner join dokter on hasil_pemeriksaan_usg_urologi.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_usg_urologi.no_rawat='".$norawat."'"
        );
        if($rsqueryusgurologi= mysqli_fetch_array($queryusgurologi)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL PEMERIKSAAN USG UROLOGI</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Dokter Yang Memeriksa</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusgurologi["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Diagnosa Klinis</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusgurologi["diagnosa_klinis"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Ginjal Kanan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgurologi["ginjal_kanan"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Ginjal Kiri</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgurologi["ginjal_kiri"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Vesica Urinaria</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgurologi["vesica_urinaria"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Tambahan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgurologi["tambahan"]."</td>
                                    </tr>";
            $querygambarusg= bukaquery(
                "select hasil_pemeriksaan_usg_urologi_gambar.photo from hasil_pemeriksaan_usg_urologi_gambar where hasil_pemeriksaan_usg_urologi_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarusg= mysqli_fetch_array($querygambarusg)){
                $src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanusgurologi/".$rsquerygambarusg["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianUSGUrologi&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL USG UROLOGI</center></h2>
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
            JSRedirect2("index.php?act=AntrianUSGUrologi&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL USG UROLOGI</center></h2>
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
        JSRedirect2("index.php?act=AntrianUSGUrologi&hal=AntrianTindakan",4);
    }
?>
