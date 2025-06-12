<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat  = validTeks3($iyem["norawat"],20);
        $queryslitlamp = bukaquery(
            "select dokter.nm_dokter,hasil_pemeriksaan_slit_lamp.diagnosa_klinis,hasil_pemeriksaan_slit_lamp.hasil_pemeriksaan from hasil_pemeriksaan_slit_lamp ".
            "inner join dokter on hasil_pemeriksaan_slit_lamp.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_slit_lamp.no_rawat='".$norawat."'"
        );
        if($rsqueryslitlamp= mysqli_fetch_array($queryslitlamp)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL PEMERIKSAAN SLIT LAMP</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Dokter Yang Memeriksa</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryslitlamp["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Diagnosa Klinis</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryslitlamp["diagnosa_klinis"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Hasil Pemeriksaan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryslitlamp["hasil_pemeriksaan"]."</td>
                                    </tr>";
            $querygambarslitlamp= bukaquery(
                "select hasil_pemeriksaan_slit_lamp_gambar.photo from hasil_pemeriksaan_slit_lamp_gambar where hasil_pemeriksaan_slit_lamp_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarslitlamp= mysqli_fetch_array($querygambarslitlamp)){
                $src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanslitlamp/".$rsquerygambarslitlamp["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar Slit Lamp' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianSlitLamp&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL SLIT LAMP</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu hasil pemeriksaan slit lamp</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=AntrianSlitLamp&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL SLIT LAMP</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf hasil pemeriksaan slit lamp tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=AntrianSlitLamp&hal=AntrianTindakan",4);
    }
?>
