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
 <div class="col s12 row">
    <div class="col s12">
        <table class="default">
            <thead>
               <tr class='head4'>
                    <td><b>No.Rawat</b></td>
                    <td><b>Nama Pasien</b></td>
                    <td><b>Perujuk</b></td>
                    <td><b>Jam Permintaan</b></td>
                    <td><b>Ambil Sampel</b></td>
                    <td><b>Keluar Hasil</b></td>
               </tr>
            </thead>
            <tbody>
            <?php  
              $_sql="select permintaan_radiologi.no_rawat,pasien.nm_pasien,
                    if(permintaan_radiologi.jam_permintaan='00:00:00','',permintaan_radiologi.jam_permintaan) as jam_permintaan,
                    if(permintaan_radiologi.jam_sampel='00:00:00','',permintaan_radiologi.jam_sampel) as jam_sampel,
                    if(permintaan_radiologi.jam_hasil='00:00:00','',permintaan_radiologi.jam_hasil) as jam_hasil,dokter.nm_dokter
                    from permintaan_radiologi inner join reg_periksa on permintaan_radiologi.no_rawat=reg_periksa.no_rawat 
                    inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                    inner join dokter on permintaan_radiologi.dokter_perujuk=dokter.kd_dokter
                    where permintaan_radiologi.status='ralan' and permintaan_radiologi.tgl_permintaan='".date("Y-m-d", $tanggal)."' order by permintaan_radiologi.jam_permintaan desc" ;  
              $hasil=bukaquery($_sql);

              while ($data = mysqli_fetch_array ($hasil)){
                echo "<tr class='isi7' >
                          <td>".$data['no_rawat']."</td>
                          <td>".$data['nm_pasien']."</td>
                          <td>".$data['nm_dokter']."</td>
                          <td>".$data['jam_permintaan']."</td>
                          <td>".$data['jam_sampel']."</td>
                          <td>".$data['jam_hasil']."</td>
                      </tr> ";
                }
            ?>
            </tbody>
        </table>
    </div>
</div>