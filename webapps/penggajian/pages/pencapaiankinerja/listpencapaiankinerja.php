<div id="post">
	<div class="entry"> 
    <div align="center" class="link">
        <a href=?act=InputPencapaianKinerja&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListPencapaianKinerja>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
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
                <td width="82%">
                    <input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" autofocus/>
                    <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">
                </td>
            </tr>
        </table><br>
    </form>
    <div style="width: 100%; height: 78%; overflow: auto;">
    <?php
        $_sql = "SELECT pencapaian_kinerja.kode_pencapaian,pencapaian_kinerja.nama_pencapaian,pencapaian_kinerja.indek FROM pencapaian_kinerja where pencapaian_kinerja.kode_pencapaian like '%".$keyword."%' or pencapaian_kinerja.nama_pencapaian like '%".$keyword."%' ORDER BY pencapaian_kinerja.indek desc ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>					   
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='20%'><div align='center'>Kode</div></td>
                        <td width='48%'><div align='center'>Pencapaian Kinerja</div></td>
                        <td width='20%'><div align='center'>Index</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
				<td>
                                  <center>
                                        <a href=?act=InputPencapaianKinerja&action=UBAH&kode_pencapaian=".str_replace(" ","_",$baris[0]).">[edit]</a>";?>
                                        <a href="?act=ListPencapaianKinerja&action=HAPUS&kode_pencapaian=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>                                
                             </tr>";
                    }
            echo "</table>";
        } else {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>					   
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='20%'><div align='center'>Kode</div></td>
                        <td width='48%'><div align='center'>Pencapaian Kinerja</div></td>
                        <td width='20%'><div align='center'>Index</div></td>
                    </tr>
                  </table>";
        }
        
        $aksi=isset($_GET['action'])?$_GET['action']:NULL;
        if ($aksi=="HAPUS") {
            Hapus(" pencapaian_kinerja "," kode_pencapaian ='".validTeks($_GET['kode_pencapaian'])."' ","?act=ListPencapaianKinerja");
        }
    ?>
    </div>
    <?php
        echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                <tr class='head'>
                    <td><div align='left'>Data : $jumlah | <a target=_blank href=../penggajian/pages/pencapaiankinerja/LaporanPencapaianKinerja.php?&keyword=$keyword>Laporan</a> | <a target=_blank href=../penggajian/pages/pencapaiankinerja/LaporanPencapaianKinerjaExel.php?&keyword=$keyword>Excel</a> |</div></td>                        
                </tr>     
             </table>");
    ?>
    </div>
</div>
