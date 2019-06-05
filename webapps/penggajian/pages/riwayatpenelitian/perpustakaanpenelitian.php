<?php
    $action      =isset($_GET['action'])?$_GET['action']:NULL;
    $keyword      = str_replace("_"," ",isset($_GET['keyword']))?str_replace("_"," ",$_GET['keyword']):NULL;
    echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
?>
<div style="width: 100%; height: 99%; overflow: auto;">
    <?php
        $_sql = "SELECT riwayat_penelitian.id,pegawai.nik,pegawai.nama,riwayat_penelitian.jenis_penelitian,riwayat_penelitian.peranan, 
                riwayat_penelitian.judul_penelitian,riwayat_penelitian.judul_jurnal,riwayat_penelitian.tahun, riwayat_penelitian.berkas 
                from pegawai inner join riwayat_penelitian on riwayat_penelitian.id=pegawai.id where 
                pegawai.nik like '%".$keyword."%' or pegawai.nama like '%".$keyword."%' or riwayat_penelitian.jenis_penelitian like '%".$keyword."%' or 
                riwayat_penelitian.peranan like '%".$keyword."%' or riwayat_penelitian.judul_penelitian like '%".$keyword."%' or 
                riwayat_penelitian.judul_jurnal like '%".$keyword."%' or riwayat_penelitian.tahun like '%".$keyword."%' order by riwayat_penelitian.tahun";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='7%'><div align='center'>NIP</div></td>
                        <td width='15%'><div align='center'>Nama</div></td>
                        <td width='10%'><div align='center'>Jenis Penelitian</div></td>
                        <td width='9%'><div align='center'>Peranan</div></td>
                        <td width='28%'><div align='center'>Judul Penelitian</div></td>
                        <td width='11%'><div align='center'>Diterbitkan di Jurnal</div></td>
                        <td width='4%'><div align='center'>Tahun</div></td>
                        <td width='16%'><div align='center'>Makalah/Berkas</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
                                <td valign='top'>$baris[1]</td>
                                <td valign='top'>$baris[2]</td>
                                <td valign='top'>$baris[3]</td>
                                <td valign='top'>$baris[4]</td>
                                <td valign='top'>$baris[5]</td>
                                <td valign='top'>$baris[6]</td>
                                <td valign='top'>$baris[7]</td>
                                <td valign='top'><a target=_blank href=../penggajian/pages/".$baris["berkas"].">".str_replace("pages/riwayatpenelitian/berkas/","",$baris["berkas"])."</a></td>
                              </tr>";
                    }
            echo "</table>";           
        } else {
            echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='7%'><div align='center'>NIP</div></td>
                        <td width='15%'><div align='center'>Nama</div></td>
                        <td width='10%'><div align='center'>Jenis Penelitian</div></td>
                        <td width='9%'><div align='center'>Peranan</div></td>
                        <td width='28%'><div align='center'>Judul Penelitian</div></td>
                        <td width='11%'><div align='center'>Diterbitkan di Jurnal</div></td>
                        <td width='4%'><div align='center'>Tahun</div></td>
                        <td width='16%'><div align='center'>Makalah/Berkas</div></td>
                    </tr>
                  </table>";
        }
    ?>
</div>
