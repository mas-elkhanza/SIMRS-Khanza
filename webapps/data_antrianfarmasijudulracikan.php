 <?php
 require_once('conf/conf.php');
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
 date_default_timezone_set("Asia/Bangkok");
 $tanggal= mktime(date("m"),date("d"),date("Y"));
 $jam=date("H:i");
?>
 
<div class="row">
    <div class="col s12" id="header-instansi">
        <div class="card deep-orange accent-3 white-text">
            <div class="card-content">
                <h5>
                <table border='0' witdh='100%'>
                    <tr border='0'>
                        <td>Panggilan Validasi Resep</td><td>:</td>
                        <td>
                        <?php 
                            $_sql="select * from antriapotek2 where antriapotek2.no_resep in(select distinct resep_dokter_racikan.no_resep from resep_dokter_racikan)" ;  
                            $hasil=bukaquery($_sql);
                            while ($data = mysqli_fetch_array ($hasil)){
                                echo $data['no_resep']." ".getOne("select concat(reg_periksa.no_rawat,' ',pasien.nm_pasien) from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat='".$data['no_rawat']."'");
                                if($data['status']=="1"){
                                    echo "<audio autoplay='true' src='bell.wav'>";
                                    bukaquery2("update antriapotek2 set antriapotek2.status='0'");
                                }   
                            }
                        ?>
                        </td>
                    </tr>
                    <tr border='0'>
                        <td>Panggilan Penyerahan Resep</td><td>:</td>
                        <td>
                        <?php 
                            $_sql="select * from antriapotek3 where antriapotek3.no_resep in(select distinct resep_dokter_racikan.no_resep from resep_dokter_racikan)" ;  
                            $hasil=bukaquery($_sql);
                            while ($data = mysqli_fetch_array ($hasil)){
                                echo $data['no_resep']." ".getOne("select concat(reg_periksa.no_rawat,' ',pasien.nm_pasien) from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat='".$data['no_rawat']."'");
                                if($data['status']=="1"){
                                    echo "<audio autoplay='true' src='bell2.wav'>";
                                    bukaquery2("update antriapotek3 set antriapotek3.status='0'");
                                }   
                            }
                        ?>
                        </td>
                    </tr>
                </table>    
                </h5>
            </div>
        </div>
    </div>
</div>