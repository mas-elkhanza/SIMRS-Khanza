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
                <h5 class="center"><i class="material-icons md-36">group</i> Jadwal Operasi</h5>
                <table class="default">
                    <thead>
                       <tr class='head4'>
                            <td><div align='center'><b>Pasien</b></td>
                            <td><div align='center'><b>Umur</b></td>
                            <td><div align='center'><b>J.K.</b></td>
                            <td><div align='center'><b>Mulai</b></td>
                            <td><div align='center'><b>Selesai</b></td>
                            <td><div align='center'><b>Status</b></td>
                            <td><div align='center'><b>Operasi</b></td>
                            <td><div align='center'><b>Operator</b></td>
                       </tr>
                    </thead>
                    <tbody>
  <?php  
    $_sql="select booking_operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,booking_operasi.tanggal, booking_operasi.jam_mulai,booking_operasi.jam_selesai,booking_operasi.status,booking_operasi.kd_dokter, dokter.nm_dokter,booking_operasi.kode_paket,paket_operasi.nm_perawatan,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.jk from booking_operasi inner join reg_periksa inner join pasien inner join paket_operasi inner join dokter on booking_operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and booking_operasi.kd_dokter=dokter.kd_dokter and booking_operasi.kode_paket=paket_operasi.kode_paket where tanggal='".date("Y-m-d", $tanggal)."' order by booking_operasi.tanggal,booking_operasi.jam_mulai" ;  
    $hasil=bukaquery($_sql);

    while ($data = mysqli_fetch_array ($hasil)){
      echo "<tr class='isi7' >
                <td align='left'>".$data['no_rkm_medis']." ".$data['nm_pasien']."</td>
                <td align='center'>".$data['umur']."</td>
                <td align='center'>".$data['jk']."</td>
                <td align='center'>".$data['jam_mulai']."</td>
                <td align='center'>".$data['jam_selesai']."</td>
                <td align='center'>".$data['status']."</td>
                <td align='center'>".$data['nm_perawatan']."</td>
                <td align='center'>".$data['nm_dokter']."</td>
            </tr> ";
      }
  ?>
                    </tbody>
                </table>
            </div>
        </div>