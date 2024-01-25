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
                    <td><b>No.RM</b></td>
                    <td><b>No.Kartu</b></td>
                    <td><b>Dokter</b></td>
                    <td><b>Jam Praktek</b></td>
                    <td><b>Jenis Kunjungan</b></td>
                    <td><b>Nomor Referensi</b></td>
                    <td><b>Status</b></td>
                    <td><b>Validasi</b></td>
                    <td><b>No.Booking</b></td>
               </tr>
            </thead>
            <tbody>
            <?php  
              $_sql="SELECT referensi_mobilejkn_bpjs.norm,referensi_mobilejkn_bpjs.nomorkartu,referensi_mobilejkn_bpjs.kodedokter,referensi_mobilejkn_bpjs.jampraktek,
                    referensi_mobilejkn_bpjs.jeniskunjungan,referensi_mobilejkn_bpjs.nomorreferensi,referensi_mobilejkn_bpjs.status,referensi_mobilejkn_bpjs.validasi,
                    referensi_mobilejkn_bpjs.nobooking FROM referensi_mobilejkn_bpjs WHERE referensi_mobilejkn_bpjs.tanggalperiksa='".date("Y-m-d", $tanggal)."' 
                    order by referensi_mobilejkn_bpjs.validasi" ;  
              $hasil=bukaquery($_sql);

              while ($data = mysqli_fetch_array ($hasil)){
                echo "<tr class='isi7' >
                          <td>".$data['norm']."</td>
                          <td>".$data['nomorkartu']."</td>
                          <td>". getOne("select maping_dokter_dpjpvclaim.nm_dokter_bpjs from maping_dokter_dpjpvclaim where maping_dokter_dpjpvclaim.kd_dokter_bpjs='".$data['kodedokter']."'")."</td>
                          <td>".$data['jampraktek']."</td>
                          <td>".$data['jeniskunjungan']."</td>
                          <td>".$data['nomorreferensi']."</td>
                          <td>".$data['status']."</td>
                          <td>".$data['validasi']."</td>
                          <td>".$data['nobooking']."</td>
                      </tr> ";
                }
            ?>
            </tbody>
        </table>
    </div>
</div>