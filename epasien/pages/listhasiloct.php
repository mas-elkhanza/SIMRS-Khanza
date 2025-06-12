<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat  = validTeks3($iyem["norawat"],20);
        $queryoct = bukaquery(
            "select dokter.nm_dokter,hasil_pemeriksaan_oct.diagnosa_klinis,hasil_pemeriksaan_oct.hasil_pemeriksaan from hasil_pemeriksaan_oct ".
            "inner join dokter on hasil_pemeriksaan_oct.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_oct.no_rawat='".$norawat."'"
        );
        if($rsqueryoct= mysqli_fetch_array($queryoct)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL PEMERIKSAAN OCT</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Dokter Yang Memeriksa</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryoct["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Diagnosa Klinis</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryoct["diagnosa_klinis"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Hasil Pemeriksaan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryoct["hasil_pemeriksaan"]."</td>
                                    </tr>";
            $querygambaroct= bukaquery(
                "select hasil_pemeriksaan_oct_gambar.photo from hasil_pemeriksaan_oct_gambar where hasil_pemeriksaan_oct_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambaroct= mysqli_fetch_array($querygambaroct)){
                $src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanoct/".$rsquerygambaroct["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar Slit Lamp' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianOCT&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL OCT</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu hasil pemeriksaan oct</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=AntrianOCT&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL OCT</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf hasil pemeriksaan oct tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=AntrianOCT&hal=AntrianTindakan",4);
    }
?>
