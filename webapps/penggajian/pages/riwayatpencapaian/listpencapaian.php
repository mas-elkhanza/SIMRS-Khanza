<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div id="post">
    <div align="center" class="link">
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <div class="entry">   
	<form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                $action  = isset($_GET['action'])?$_GET['action']:NULL;
                $keyword = trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;        
                $keyword = validTeks($keyword);
                echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Keyword</td><td width="">:</td>
                    <td width="82%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" autofocus/>
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">
                    </td>
                </tr>
            </table><br>
            <div style="width: 100%; height: 80%; overflow: auto;">
            <?php
                $_sql   = "SELECT pegawai.id,pegawai.nik,pegawai.nama,pegawai.departemen FROM pegawai where pegawai.stts_aktif='AKTIF' and (pegawai.nik like '%".$keyword."%' or pegawai.nama like '%".$keyword."%' or pegawai.departemen like '%".$keyword."%') group by pegawai.id order by pegawai.id ASC ";
                $hasil  = bukaquery($_sql);
                $jumlah = mysqli_num_rows($hasil);
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='9%'><div align='center'>Proses</div></td>
                                <td width='10%'><div align='center'>NIP</div></td>
                                <td width='25%'><div align='center'>Nama</div></td>
                                <td width='8%'><div align='center'>Depart</div></td>
                                <td width='48%'><div align='center'>Riwayat Evaluasi</div></td>
                            </tr>";
                            while($baris = mysqli_fetch_array($hasil)) {
                                echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                        <td>
                                            <center>
                                                <a href=?act=InputRiwayatPencapaian&action=TAMBAH&id=$baris[0]>[Detail]</a>
                                            </center>
                                        </td>
                                        <td><a href=?act=InputRiwayatPencapaian&action=TAMBAH&id=$baris[0]>$baris[1]</a></td>
                                        <td><a href=?act=InputRiwayatPencapaian&action=TAMBAH&id=$baris[0]>$baris[2]</a></td>
                                        <td><a href=?act=InputRiwayatPencapaian&action=TAMBAH&id=$baris[0]>$baris[3]</a></td>
                                        <td>
                                            <a href=?act=InputRiwayatPencapaian&action=TAMBAH&id=$baris[0]>";
                                $_sqlkinerja  = "Select pencapaian_kinerja_pegawai.tahun,pencapaian_kinerja_pegawai.bulan,pencapaian_kinerja.nama_pencapaian,pencapaian_kinerja_pegawai.keterangan 
                                                 from pencapaian_kinerja_pegawai inner join pencapaian_kinerja on pencapaian_kinerja_pegawai.kode_pencapaian=pencapaian_kinerja.kode_pencapaian 
                                                 where pencapaian_kinerja_pegawai.id='$baris[0]' order by pencapaian_kinerja_pegawai.tahun,pencapaian_kinerja_pegawai.bulan ASC ";
                                $hasilkinerja = bukaquery($_sqlkinerja);
                                if(mysqli_num_rows($hasilkinerja)!=0) {
                                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                                <tr class='head'>
                                                    <td width='5%' align='center'>Tahun</td>
                                                    <td width='5%' align='center'>Bulan</td>
                                                    <td width='50%' align='center'>Hasil Pencapaian</td>
                                                    <td width='40%' align='center'>Keterangan</td>
                                                </tr>";
                                    while($bariskinerja = mysqli_fetch_array($hasilkinerja)) {                        
                                        echo "  <tr class='isi'>
                                                    <td align='center'>".$bariskinerja["tahun"]."</td>
                                                    <td align='center'>".$bariskinerja["bulan"]."</td>
                                                    <td>".$bariskinerja["nama_pencapaian"]."</td>
                                                    <td>".$bariskinerja["keterangan"]."</td>
                                                </tr>";
                                    }
                                    echo "</table>";
                                }

                                echo "      </a>
                                        </td>
                                      </tr>";
                            }
                    echo "</table>";           
                } else {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='9%'><div align='center'>Proses</div></td>
                                <td width='10%'><div align='center'>NIP</div></td>
                                <td width='25%'><div align='center'>Nama</div></td>
                                <td width='8%'><div align='center'>Depart</div></td>
                                <td width='48%'><div align='center'>Riwayat Evaluasi</div></td>
                            </tr>
                          </table>";
                }
            ?>
            </div>
	</form>
        <?php
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");

        ?>
    </div>
</div>
