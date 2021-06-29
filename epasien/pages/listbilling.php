<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    $norawat = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $norawat = json_decode(encrypt_decrypt($norawat,"d"),true); 
    if (isset($norawat["norawat"])) {
        $norawat      = $norawat["norawat"];
        $querybilling = bukaquery("select no,nm_perawatan, if(biaya<>0,biaya,null) as satu, if(jumlah<>0,jumlah,null) as dua,
                        if(tambahan<>0,tambahan,null) as tiga, if(totalbiaya<>0,totalbiaya,null) as empat,pemisah,status 
                        from billing where no_rawat='$norawat' order by noindex");
        if(mysqli_num_rows($querybilling)!=0) {
            echo "<div class='block-header'>
                    <h2><center>BILLING PERAWATAN<br>NO.$norawat</center></h2>
                  </div>
                  <div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <div class='table-responsive'>
                                    <table class='table table-hover dataTable'>";
            
            $z=1;
            $total=0;
            while($rsquerybilling = mysqli_fetch_array($querybilling)) {
                if(($rsquerybilling["status"]!="Tagihan")&&($rsquerybilling["status"]!="TtlObat")){
                                        $total=$total+$rsquerybilling["empat"];
                                        if($z<=6){
                                            echo "<tr>
                                                    <td>".$rsquerybilling["no"]."</td>
                                                    <td colspan='4'>".$rsquerybilling["nm_perawatan"]."</td>
                                                  </tr>";
                                        }else{
                                            echo "<tr>
                                                    <td>".$rsquerybilling["no"]."</td>
                                                    <td>".$rsquerybilling["nm_perawatan"]."</td>
                                                    <td align='right'>".$rsquerybilling["pemisah"]."</td>
                                                    <td align='right'>".$rsquerybilling["dua"]."</td>
                                                    <td align='right'>".($rsquerybilling["empat"]>0?formatDuit($rsquerybilling["empat"]):"")."</td>
                                                  </tr>";
                                        }
                                        $z++;
                }
            }
                                        
            echo "                      <tr>
                                            <td><b>TOTAL BILLING</b></td>
                                            <td>:</td>
                                            <td align='right'></td>
                                            <td align='right'></td>
                                            <td align='right'><b>".formatDuit($total)."</b></td>
                                          </tr>
                                    </table>
                                </div>
                                <center><a href='index.php?act=RiwayatPeriksa&hal=RiwayatPeriksa' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                    <h2><center>BILLING PERAWATAN</center></h2>
                </div>
                <div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf billing perawatan dengan No.Rawat $norawat belum keluar, masih menunggu dikeluarkan oleh kasir.</center><br/>
                                <center><a href='index.php?act=RiwayatPeriksa&hal=RiwayatPeriksa' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                </div>";
            JSRedirect2("index.php?act=RiwayatPeriksa",9);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>BILLING PERAWATAN</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf billing perawatan tidak ditemukan</center><br/>
                            <center><a href='index.php?act=RiwayatPeriksa&hal=RiwayatPeriksa' class='btn btn-danger waves-effect'>Kembali</a></center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=RiwayatPeriksa",7);
    }
?>
