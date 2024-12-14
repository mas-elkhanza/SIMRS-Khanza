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
        $querytanggalperiksalab = bukaquery(
            "select periksa_lab.tgl_periksa,periksa_lab.jam from periksa_lab where periksa_lab.kategori='PA' and periksa_lab.no_rawat='".$norawat."' and periksa_lab.tgl_periksa='".$tglperiksa."' ".
            "and periksa_lab.jam='".$jam."' group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) order by periksa_lab.tgl_periksa,periksa_lab.jam" 
        );
        if(mysqli_num_rows($querytanggalperiksalab)!=0) {
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL PEMERIKSAAN LABORATORIUM PA</center></h2>
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
            while($rsquerytanggalperiksalab= mysqli_fetch_array($querytanggalperiksalab)){
                $queryperiksalab = bukaquery(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,petugas.nama,periksa_lab.dokter_perujuk,dokter.nm_dokter ".
                    "from periksa_lab inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw ".
                    "inner join petugas on periksa_lab.nip=petugas.nip inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter ".
                    "where periksa_lab.kategori='PA' and periksa_lab.no_rawat='".$norawat."' ".
                    "and periksa_lab.tgl_periksa='".$tglperiksa."' and periksa_lab.jam='".$jam."'"
                );
                $s=1;
                while($rsqueryperiksalab = mysqli_fetch_array($queryperiksalab)){
                    if($s==1){
                        echo"   <tr>
                                    <td valign='top' align='center'>".$w."</td>
                                    <td valign='top'>".$rsqueryperiksalab["nm_perawatan"]."</td>
                                    <td valign='top'>".$rsqueryperiksalab["nm_dokter"]."</td>
                                    <td valign='top'>".$rsqueryperiksalab["nama"]."</td>
                                </tr>"; 
                    }else{
                        echo"   <tr>
                                    <td valign='top' align='center'></td>
                                    <td valign='top'>".$rsqueryperiksalab["nm_perawatan"]."</td>
                                    <td valign='top'>".$rsqueryperiksalab["nm_dokter"]."</td>
                                    <td valign='top'>".$rsqueryperiksalab["nama"]."</td>
                                </tr>"; 
                    }

                    $querydetailperiksalab = bukaquery(
                        "select detail_periksa_labpa.diagnosa_klinik, detail_periksa_labpa.makroskopik, detail_periksa_labpa.mikroskopik, ".
                        "detail_periksa_labpa.kesimpulan,detail_periksa_labpa.kesan from detail_periksa_labpa where detail_periksa_labpa.no_rawat='".$norawat."' ".
                        "and detail_periksa_labpa.kd_jenis_prw='".$rsqueryperiksalab["kd_jenis_prw"]."' and detail_periksa_labpa.tgl_periksa='".$tglperiksa."' ".
                        "and detail_periksa_labpa.jam='".$jam."'"
                    );
                    if(mysqli_num_rows($querydetailperiksalab)!=0) {
                        echo "  <tr>
                                    <td valign='top' align='center'></td>
                                    <td valign='top' align='center' bgcolor='#FCFCFC' colspan='3'>Hasil Pemeriksaan</td>
                                </tr>";
                        while($rsquerydetailperiksalab = mysqli_fetch_array($querydetailperiksalab)){
                            echo "<tr>
                                    <td valign='top' align='center'></td>
                                    <td valign='top' colspan='3'>Diagnosa Klinis : ".$rsquerydetailperiksalab["diagnosa_klinik"]."</td>
                                  </tr>
                                  <tr>
                                    <td valign='top' align='center'></td>
                                    <td valign='top' colspan='3'>Makroskopik : ".$rsquerydetailperiksalab["makroskopik"]."</td>
                                  </tr>
                                  <tr>
                                    <td valign='top' align='center'></td>
                                    <td valign='top' colspan='3'>Mikroskopik : ".$rsquerydetailperiksalab["mikroskopik"]."</td>
                                  </tr>
                                  <tr>
                                    <td valign='top' align='center'></td>
                                    <td valign='top' colspan='3'>Kesimpulan : ".$rsquerydetailperiksalab["kesimpulan"]."</td>
                                  </tr>
                                  <tr>
                                    <td valign='top' align='center'></td>
                                    <td valign='top' colspan='3'>Kesan : ".$rsquerydetailperiksalab["kesan"]."</td>
                                  </tr>"; 
                            $querygambarlabpa = bukaquery(
                                "select detail_periksa_labpa_gambar.tgl_periksa,detail_periksa_labpa_gambar.jam,detail_periksa_labpa_gambar.photo from detail_periksa_labpa_gambar ".
                                "where detail_periksa_labpa_gambar.no_rawat='".$norawat."' and detail_periksa_labpa_gambar.kd_jenis_prw='".$rsqueryperiksalab["kd_jenis_prw"]."' and ".
                                "detail_periksa_labpa_gambar.tgl_periksa='".$tglperiksa."' and detail_periksa_labpa_gambar.jam='".$jam."' order by detail_periksa_labpa_gambar.tgl_periksa,detail_periksa_labpa_gambar.jam"
                            );
                            if($rsquerygambarlabpa = mysqli_fetch_array($querygambarlabpa)){
                                $src = 'data: '.@mime_content_type("http://".host()."/webapps/labpa/".$rsquerygambarlabpa["photo"]).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/labpa/".$rsquerygambarlabpa["photo"]));
                                echo "<tr>
                                         <td valign='top' align='center'></td>
                                         <td valign='top' align='center' bgcolor='#FCFCFC' colspan='3'>Gambar Patologi Anatomi</td>
                                      </tr>
                                      <tr>
                                         <td valign='top' align='center'></td>
                                         <td valign='top' colspan='3' align='center'><img alt='Gambar PA' src='$src' width='100%' height='500px'/></td>
                                      </tr>";
                            }
                        }
                    }
                    $s++;
                }
                $w++;
            }
            echo "              </table>
                                <center><a href='index.php?act=AntrianPemeriksaanLabPA&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL PEMERIKSAAN LABORATORIUM PA</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu hasil pemeriksaan laboratorium</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=AntrianPemeriksaanLabPA&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL PEMERIKSAAN LABORATORIUM PA</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf hasil pemeriksaan laboratorium tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=AntrianPemeriksaanLabPA&hal=AntrianTindakan",4);
    }
?>
