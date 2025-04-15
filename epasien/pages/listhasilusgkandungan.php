<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["norawat"])) {
        $norawat    = validTeks3($iyem["norawat"],20);
        $queryusgkandungan = bukaquery(
            "select dokter.nm_dokter,hasil_pemeriksaan_usg.diagnosa_klinis,hasil_pemeriksaan_usg.hta,hasil_pemeriksaan_usg.kantong_gestasi,hasil_pemeriksaan_usg.ukuran_bokongkepala,".
            "hasil_pemeriksaan_usg.jenis_prestasi,hasil_pemeriksaan_usg.diameter_biparietal,hasil_pemeriksaan_usg.panjang_femur,hasil_pemeriksaan_usg.lingkar_abdomen,".
            "hasil_pemeriksaan_usg.tafsiran_berat_janin,hasil_pemeriksaan_usg.usia_kehamilan,hasil_pemeriksaan_usg.plasenta_berimplatansi,hasil_pemeriksaan_usg.derajat_maturitas,".
            "hasil_pemeriksaan_usg.jumlah_air_ketuban,hasil_pemeriksaan_usg.indek_cairan_ketuban,hasil_pemeriksaan_usg.kelainan_kongenital,hasil_pemeriksaan_usg.peluang_sex,".
            "hasil_pemeriksaan_usg.kesimpulan from hasil_pemeriksaan_usg inner join dokter on hasil_pemeriksaan_usg.kd_dokter=dokter.kd_dokter where hasil_pemeriksaan_usg.no_rawat='".$norawat."'"
        );
        if($rsqueryusgkandungan= mysqli_fetch_array($queryusgkandungan)){
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL USG KANDUNGAN</center></h2>
                            </div>
                            <div class='body' align='center'>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td valign='middle' width='30%'>Dokter Yang Memeriksa</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["nm_dokter"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Diagnosa Klinis</td>
                                        <td valign='middle' width='1%'>:</td>
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["diagnosa_klinis"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>HTA</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["hta"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Jenis Prestasi</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["jenis_prestasi"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Ukuran Kantong Gestasi (GS)</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["kantong_gestasi"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Ukuran Bokong - Kepala (CRL)</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["ukuran_bokongkepala"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Diameter Biparietal (DBP)</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["diameter_biparietal"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Panjang Femur (FL)</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["panjang_femur"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Lingkar Abdomen (AC)</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["lingkar_abdomen"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Tafsiran berat Janin (TBJ)</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["tafsiran_berat_janin"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Usia Kehamilan Sesuai</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["usia_kehamilan"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Plasenta Berimplatansi Di</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["plasenta_berimplatansi"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Derajat Maturitas Plasenta</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["derajat_maturitas"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Jumlah Air Ketuban</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["jumlah_air_ketuban"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Peluang Sex</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["peluang_sex"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Indeks Cairan Ketuban (ICK)</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["indek_cairan_ketuban"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kelainan Kongenital Mayor</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["kelainan_kongenital"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='middle' width='30%'>Kesimpulan</td>
                                        <td valign='middle' width='1%'>:</td>                                        
                                        <td valign='middle' width='69%'>".$rsqueryusgkandungan["kesimpulan"]."</td>
                                    </tr>";
            $querygambarusg= bukaquery(
                "select hasil_pemeriksaan_usg_gambar.photo from hasil_pemeriksaan_usg_gambar where hasil_pemeriksaan_usg_gambar.no_rawat='".$norawat."'"
            );
            if($rsquerygambarusg= mysqli_fetch_array($querygambarusg)){
                $src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".host()."/webapps/hasilpemeriksaanusg/".$rsquerygambarusg["photo"]));
                echo "              <tr>
                                        <td valign='middle' colspan='3'><img alt='Gambar USG' src='$src' width='100%' height='500px'/></td>
                                    </tr>";
            }
            echo "                  </table>
                                <center><a href='index.php?act=AntrianUSGKandungan&hal=AntrianTindakan' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL USG KANDUNGAN</center></h2>
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
            JSRedirect2("index.php?act=AntrianUSGKandungan&hal=AntrianTindakan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL USG KANDUNGAN</center></h2>
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
        JSRedirect2("index.php?act=AntrianUSGKandungan&hal=AntrianTindakan",4);
    }
?>
