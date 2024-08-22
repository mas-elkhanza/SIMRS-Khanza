<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>RIWAYAT MEDICAL CHECK UP</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th><center>No.Booking</center></th>
                                <th><center>Perusahaan</center></th>
                                <th><center>Tgl.MCU</center></th>
                                <th><center>No.Pelayanan</center></th>
                                <th><center>Penanggung Jawab MCU</center></th>
                                <th><center>Aksi</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $queryriwayat = bukaquery(
                                "select booking_mcu_perusahaan.no_mcu,booking_mcu_perusahaan.tanggal_mcu,booking_mcu_perusahaan_berhasil_registrasi.no_rawat,dokter.nm_dokter,perusahaan_pasien.nama_perusahaan ".
                                "from booking_mcu_perusahaan inner join booking_mcu_perusahaan_berhasil_registrasi on booking_mcu_perusahaan_berhasil_registrasi.no_mcu=booking_mcu_perusahaan.no_mcu ".
                                "inner join reg_periksa on reg_periksa.no_rawat=booking_mcu_perusahaan_berhasil_registrasi.no_rawat inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter ".
                                "inner join perusahaan_pasien on booking_mcu_perusahaan.kode_perusahaan=perusahaan_pasien.kode_perusahaan where booking_mcu_perusahaan.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."'"
                           );
                           while($rsqueryriwayat = mysqli_fetch_array($queryriwayat)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryriwayat["no_mcu"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryriwayat["nama_perusahaan"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryriwayat["tanggal_mcu"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryriwayat["no_rawat"]."</td>
                                        <td align='left' valign='middle'>".$rsqueryriwayat["nm_dokter"]."</td>
                                        <td align='center' valign='middle'>
                                           <a href='index.php?act=HasilMCU&iyem=".encrypt_decrypt("{\"nomcuhasil\":\"".$rsqueryriwayat["no_mcu"]."\"}","e")."' class='btn btn-success waves-effect'>Hasil MCU</a>
                                        </td>
                                     </tr>";
                           }
                        ?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
