<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>DAFTAR KARYAWAN/PEGAWAI YANG TERDAFTAR DI <?=$_SESSION["nama_instansi"];?></center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th><center>No.RM</center></th>
                                <th><center>Nama Pasien</center></th>
                                <th><center>JK</center></th>
                                <th><center>Umur</center></th>
                                <th><center>No.KTP</center></th>
                                <th><center>No.Pegawai</center></th>
                                <th><center>Alamat</center></th>
                                <th><center>Aksi</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $querypasien = bukaquery("select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,pasien.no_ktp,pasien.nip,pasien.alamat from pasien where pasien.perusahaan_pasien='".validTeks4(encrypt_decrypt($_SESSION["ses_emcu"],"d"),20)."'");
                           while($rsquerypasien = mysqli_fetch_array($querypasien)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsquerypasien["no_rkm_medis"]."</td>
                                        <td align='left' valign='middle'>".$rsquerypasien["nm_pasien"]."</td>
                                        <td align='center' valign='middle'>".$rsquerypasien["jk"]."</td>
                                        <td align='center' valign='middle'>".$rsquerypasien["umur"]."</td>
                                        <td align='center' valign='middle'>".$rsquerypasien["no_ktp"]."</td>
                                        <td align='center' valign='middle'>".$rsquerypasien["nip"]."</td>
                                        <td align='left' valign='middle'>".$rsquerypasien["alamat"]."</td>
                                        <td align='center' valign='middle'>
                                           <a href='index.php?act=RiwayatMCU&iyem=".encrypt_decrypt("{\"nm_pasien\":\"".$rsquerypasien["nm_pasien"]."\",\"no_rkm_medis\":\"".$rsquerypasien["no_rkm_medis"]."\"}","e")."' class='btn btn-success waves-effect'>Riwayat MCU</a>
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
