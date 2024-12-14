<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat    = validTeks3($iyem["norawat"],20);
        $tglperiksa = validTeks3($iyem["tglperiksa"],10);
        $jam        = validTeks4($iyem["jam"],10);
        $querytanggalradiologi = bukaquery(
            "select periksa_radiologi.tgl_periksa,periksa_radiologi.jam from periksa_radiologi where periksa_radiologi.no_rawat='".$norawat."' and periksa_radiologi.tgl_periksa='".$tglperiksa."' ".
            "and periksa_radiologi.jam='".$jam."' group by concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam) order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam" 
        );
        if(mysqli_num_rows($querytanggalradiologi)!=0) {
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL PEMERIKSAAN RADIOLOGI</center></h2>
                            </div>
                            <div class='body' align='justify'>
                                <table width='100%' align='center' class='table table-bordered table-hover js-basic-example dataTable'>
                                    <tr align='center'>
                                        <td valign='top' width='4%' bgcolor='#FCFCFC'>No.</td>
                                        <td valign='top' width='46%' bgcolor='#FCFCFC'>Nama Pemeriksaan</td>
                                        <td valign='top' width='25%' bgcolor='#FCFCFC'>Dokter PJ</td>
                                        <td valign='top' width='25%' bgcolor='#FCFCFC'>Petugas</td>
                                    </tr>";
            $w=1;
            while($rsquerytanggalradiologi= mysqli_fetch_array($querytanggalradiologi)){
                $queryradiologi = bukaquery(
                    "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,petugas.nama,periksa_radiologi.dokter_perujuk,dokter.nm_dokter ".
                    "from periksa_radiologi inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw ".
                    "inner join petugas on periksa_radiologi.nip=petugas.nip inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter ".
                    "where periksa_radiologi.no_rawat='".$norawat."' and periksa_radiologi.tgl_periksa='".$tglperiksa."' and periksa_radiologi.jam='".$jam."'"
                );
                $s=1;
                while($rsqueryradiologi = mysqli_fetch_array($queryradiologi)){
                    if($s==1){
                        echo"       <tr>
                                        <td valign='top' align='center'>".$w."</td>
                                        <td valign='top'>".$rsqueryradiologi["nm_perawatan"]."</td>
                                        <td valign='top'>".$rsqueryradiologi["nm_dokter"]."</td>
                                        <td valign='top'>".$rsqueryradiologi["nama"]."</td>
                                    </tr>"; 
                    }else{
                        echo"       <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top'>".$rsqueryradiologi["nm_perawatan"]."</td>
                                        <td valign='top'>".$rsqueryradiologi["nm_dokter"]."</td>
                                        <td valign='top'>".$rsqueryradiologi["nama"]."</td>
                                    </tr>"; 
                    }

                    $querydetailradiologi = bukaquery(
                        "select hasil_radiologi.hasil from hasil_radiologi where hasil_radiologi.no_rawat='".$norawat."' and hasil_radiologi.tgl_periksa='".$tglperiksa."' and hasil_radiologi.jam='".$jam."'"
                    );
                    if(mysqli_num_rows($querydetailradiologi)!=0) {
                        echo "      <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center' bgcolor='#FCFCFC' colspan='3'>Hasil Pemeriksaan</td>
                                    </tr>";
                        while($rsquerydetailradiologi = mysqli_fetch_array($querydetailradiologi)){
                            echo "  <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' colspan='3'>".$rsquerydetailradiologi["hasil"]."</td>
                                    </tr>"; 
                            $querygambarradiologi = bukaquery(
                                "select gambar_radiologi.tgl_periksa,gambar_radiologi.jam,gambar_radiologi.lokasi_gambar from gambar_radiologi where gambar_radiologi.no_rawat='".$norawat."' ".
                                "and gambar_radiologi.tgl_periksa='".$tglperiksa."' and gambar_radiologi.jam='".$jam."' order by gambar_radiologi.tgl_periksa,gambar_radiologi.jam"
                            );
                            if(mysqli_num_rows($querygambarradiologi)!=0) {
                                echo "<tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center' bgcolor='#FCFCFC' colspan='3'>Gambar Radiologi</td>
                                      </tr>";
                                while($rsquerygambarradiologi= mysqli_fetch_array($querygambarradiologi)){
                                    $src = 'data: '.@mime_content_type("http://".host()."/webapps/radiologi/".$rsquerygambarradiologi["lokasi_gambar"]).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/radiologi/".$rsquerygambarradiologi["lokasi_gambar"]));
                                    echo "<tr>
                                            <td valign='top' align='center'></td>
                                            <td valign='top' colspan='3' align='center'><img alt='Gambar PA' src='$src' width='100%' height='500px'/></td>
                                          </tr>";
                                }
                            }
                                
                        }
                    }
                    $s++;
                }
                $w++;
            }
            echo "              </table>
                                <center><a href='index.php?act=AntrianPemeriksaanRad&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL PEMERIKSAAN RADIOLOGI</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu hasil pemeriksaan radiologi</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=AntrianPemeriksaanRad&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL PEMERIKSAAN RADIOLOGI</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf hasil pemeriksaan radiologi tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=AntrianPemeriksaanRad&hal=AntrianTindakan",4);
    }
?>
