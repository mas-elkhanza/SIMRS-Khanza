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
                        <td>Panggilan Pengambilan Sampel</td><td>:</td>
                        <td>
                        <?php 
                           echo getOne("select concat(permintaan_lab.no_rawat,' ',pasien.nm_pasien) from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where permintaan_lab.status='ralan' and permintaan_lab.tgl_sampel='".date("Y-m-d", $tanggal)."' order by permintaan_lab.jam_sampel desc limit 1"); 
                           if(getOne("select antrilabpk.status from antrilabpk")=="1"){
                                echo "<audio autoplay='true' src='bell.wav'>";
                                bukaquery2("delete from antrilabpk");
                                bukaquery2("insert into antrilabpk values('0')");
                            }
                        ?>
                        </td>
                    </tr>
                    <tr border='0'>
                        <td>Panggilan Hasil Laborat</td><td>:</td>
                        <td>
                        <?php
                           echo getOne("select concat(permintaan_lab.no_rawat,' ',pasien.nm_pasien) from permintaan_lab inner join reg_periksa on permintaan_lab.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where permintaan_lab.status='ralan' and permintaan_lab.tgl_hasil='".date("Y-m-d", $tanggal)."' order by permintaan_lab.jam_hasil desc limit 1"); 
                           if(getOne("select antrilabpk2.status from antrilabpk2")=="1"){
                                echo "<audio autoplay='true' src='bell2.wav'>";
                                bukaquery2("delete from antrilabpk2");
                                bukaquery2("insert into antrilabpk2 values('0')");
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