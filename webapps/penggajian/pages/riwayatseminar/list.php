<?php
    $action      =isset($_GET['action'])?$_GET['action']:NULL;
    $keyword      = str_replace("_"," ",isset($_GET['keyword']))?str_replace("_"," ",$_GET['keyword']):NULL;
    $keyword= validTeks($keyword);
    echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
?>
<div style="width: 100%; height: 99%; overflow: auto;">
    <?php
        $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama FROM  pegawai
		 where  pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' or 
		 pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%'
		 order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='5%'><div align='center'>Proses</div></td>
                        <td width='11%'><div align='center'>NIP</div></td>
                        <td width='25%'><div align='center'>Nama</div></td>
                        <td width='59%'><div align='center'>Riwayat Kegiatan Ilmiah & Pelatihan Pegawai</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                <td valign='top'>
                                    <center>
                                        <a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$baris[0]>[Detail]</a>
                                    </center>
                               </td>
                                <td valign='top'><a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$baris[0]>$baris[1]</a></td>
                                <td valign='top'><a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$baris[0]>$baris[2]</a></td>
                                <td valign='top'>
                                   <table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>";
                                        $_sql2 = "SELECT nama_seminar, mulai, penyelengara, tempat from riwayat_seminar where id='$baris[0]' ORDER BY mulai ASC ";
                                        $hasil2=bukaquery($_sql2);
                                        if(mysqli_num_rows($hasil2)!=0) {
                                            echo "<tr class='isi7'>
                                                    <td width='5%'><div align='center'>NO.</div></td>
                                                    <td width='35%'><div align='center'>Nama Kegiatan</div></td>
                                                    <td width='20%'><div align='center'>Tanggal</div></td>
                                                    <td width='20%'><div align='center'>Penyelenggara</div></td>
                                                    <td width='20%'><div align='center'>Tempat</div></td>
                                                  </tr>";
                                        }
                                        $no=1;
                                        while($baris2 = mysqli_fetch_array($hasil2)) { 
                                            echo "<tr> 
                                                    <td>$no</td>
                                                    <td>$baris2[0]</td>
                                                    <td>$baris2[1]</td>
                                                    <td>$baris2[2]</td>
                                                    <td>$baris2[3]</td>
                                                  </tr>";$no++;
                                        }
                             echo "</table>
                                </td>
                             </tr>";
                    }
            echo "</table>";           
        } else {
            echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='5%'><div align='center'>Proses</div></td>
                        <td width='11%'><div align='center'>NIP</div></td>
                        <td width='25%'><div align='center'>Nama</div></td>
                        <td width='59%'><div align='center'>Riwayat Kegiatan Ilmiah & Pelatihan Pegawai</div></td>
                    </tr>
                  </table>";
        }
    ?>
</div>
