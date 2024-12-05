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
            "select periksa_lab.tgl_periksa,periksa_lab.jam from periksa_lab where periksa_lab.kategori='MB' and periksa_lab.no_rawat='".$norawat."' and periksa_lab.tgl_periksa='".$tglperiksa."' ".
            "and periksa_lab.jam='".$jam."' group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) order by periksa_lab.tgl_periksa,periksa_lab.jam" 
        );
        if(mysqli_num_rows($querytanggalperiksalab)!=0) {
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL PEMERIKSAAN LABORATORIUM MB</center></h2>
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
                    "where periksa_lab.kategori='MB' and periksa_lab.no_rawat='".$norawat."' ".
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
                        "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,".
                        "detail_periksa_lab.keterangan from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template ".
                        "where detail_periksa_lab.no_rawat='".$norawat."' and detail_periksa_lab.kd_jenis_prw='".$rsqueryperiksalab["kd_jenis_prw"]."' and ".
                        "detail_periksa_lab.tgl_periksa='".$tglperiksa."' and detail_periksa_lab.jam='".$jam."'".
                        "order by detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut"
                    );
                    if(mysqli_num_rows($querydetailperiksalab)!=0) {
                        echo "  <tr>
                                    <td valign='top' align='center'></td>
                                    <td valign='top' align='center' bgcolor='#FCFCFC'>Detail Pemeriksaan</td>
                                    <td valign='top' align='center' bgcolor='#FCFCFC'>Hasil</td>
                                    <td valign='top' align='center' bgcolor='#FCFCFC'>Nilai Rujukan</td>
                                </tr>";
                        while($rsquerydetailperiksalab = mysqli_fetch_array($querydetailperiksalab)){
                            switch (strtolower($rsquerydetailperiksalab["keterangan"])) {
                                case "l":
                                    echo "<tr>
                                            <td valign='top' align='center'></td>
                                            <td valign='top'>".$rsquerydetailperiksalab["Pemeriksaan"]."</td>
                                            <td valign='top' style='color:#0000FF'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai"])))." ".$rsquerydetailperiksalab["satuan"]."</td>
                                            <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai_rujukan"])))."</td>
                                          </tr>"; 
                                    break;
                                case "h":
                                    echo "<tr>
                                            <td valign='top' align='center'></td>
                                            <td valign='top'>".$rsquerydetailperiksalab["Pemeriksaan"]."</td>
                                            <td valign='top' style='color:#FF0000'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai"])))." ".$rsquerydetailperiksalab["satuan"]."</td>
                                            <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai_rujukan"])))."</td>
                                          </tr>"; 
                                    break;
                                case "t":
                                    echo "<tr>
                                            <td valign='top' align='center'></td>
                                            <td valign='top'>".$rsquerydetailperiksalab["Pemeriksaan"]."</td>
                                            <td valign='top'><b>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai"])))." ".$rsquerydetailperiksalab["satuan"]."</b></td>
                                            <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai_rujukan"])))."</td>
                                          </tr>"; 
                                    break;
                                default:
                                    echo "<tr>
                                            <td valign='top' align='center'></td>
                                            <td valign='top'>".$rsquerydetailperiksalab["Pemeriksaan"]."</td>
                                            <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai"])))." ".$rsquerydetailperiksalab["satuan"]."</td>
                                            <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai_rujukan"])))."</td>
                                          </tr>"; 
                            }
                        }
                    }
                    $s++;
                }
                $w++;
            }
            echo "          </table>";

            $querysarankesanperiksalab = bukaquery(
                "select saran_kesan_lab.saran,saran_kesan_lab.kesan from saran_kesan_lab where saran_kesan_lab.no_rawat='".$norawat."' and saran_kesan_lab.tgl_periksa='".$tglperiksa."' and saran_kesan_lab.jam='".$jam."'"
            );

            if($querysarankesanperiksalab = mysqli_fetch_array($querysarankesanperiksalab)){
                echo "      <table width='100%' border='0' align='center' class='table table-hover js-basic-example dataTable'>
                                <tr>
                                    <td valign='top' border='0' width='100%'>Kesan : ".$querysarankesanperiksalab["kesan"]."</td>
                                </tr>
                                <tr>
                                    <td valign='top' border='0' width='100%'>Saran : ".$querysarankesanperiksalab["saran"]."</td>
                                </tr>
                            </table>";
            }                 
            echo "          <center><a href='index.php?act=AntrianPemeriksaanLab&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL PEMERIKSAAN LABORATORIUM MB</center></h2>
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
            JSRedirect2("index.php?act=AntrianPemeriksaanLab&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL PEMERIKSAAN LABORATORIUM MB</center></h2>
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
        JSRedirect2("index.php?act=AntrianPemeriksaanLab&hal=AntrianTindakan",4);
    }
?>
