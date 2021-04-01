<?php 
    require_once('../conf/conf.php');
    $keyword    = trim(isset($_POST['value']))?trim($_POST['value']):NULL;
    $keyword    = cleankar($keyword);
    echo "<div class='table-responsive'>
            <table class='table table-hover' >
                <tr>
                    <th width='10%'><center>Hari Kerja</center></th>
                    <th width='30%'><center>Nama Dokter</center></th>
                    <th width='30%'><center>Poliklinik</center></th>
                    <th width='15%'><center>Jam Mulai</center></th>
                    <th width='15%'><center>Jam Selesai</center></th>
                </tr>";
    $queryjadwal = bukaquery("select dokter.nm_dokter,jadwal.hari_kerja,jadwal.jam_mulai,jadwal.jam_selesai,poliklinik.nm_poli from jadwal inner join poliklinik inner join dokter on jadwal.kd_dokter=dokter.kd_dokter and jadwal.kd_poli=poliklinik.kd_poli where dokter.status='1' ".(isset($keyword)?" and (dokter.nm_dokter like '%$keyword%' or jadwal.hari_kerja like '%$keyword%' or poliklinik.nm_poli like '%$keyword%')":"")." order by jadwal.hari_kerja,jadwal.kd_dokter");
    while($rsqueryjadwal = mysqli_fetch_array($queryjadwal)) {
        echo "<tr>
                <td align='center'>".$rsqueryjadwal["hari_kerja"]."</td>
                <td align='left'>".$rsqueryjadwal["nm_dokter"]."</td>
                <td align='center'>".$rsqueryjadwal["nm_poli"]."</td>
                <td align='center'>".$rsqueryjadwal["jam_mulai"]."</td>
                <td align='center'>".$rsqueryjadwal["jam_selesai"]."</td>
              </tr>";
    }
    echo "</table>
        </div>";
?>