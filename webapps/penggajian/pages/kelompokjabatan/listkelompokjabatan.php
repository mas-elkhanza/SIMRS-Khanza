

<div id="post">
	<div class="entry"> 
    <div align="center" class="link">
        <a href=?act=InputKelompokJabatan&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListKelompokJabatan>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $keyword     =isset($_GET['keyword'])?$_GET['keyword']:NULL;
                echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
        ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Keyword</td><td width="">:</td>
                    <td width="82%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" />
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">
                    </td>
                </tr>
            </table><br>
    </form>
    <div style="width: 100%; height: 78%; overflow: auto;">
    <?php
        $keyword=trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
        $_sql = "SELECT kode_kelompok,nama_kelompok,indek FROM kelompok_jabatan where kode_kelompok like '%".$keyword."%' or nama_kelompok like '%".$keyword."%' ORDER BY indek desc ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>					   
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='20%'><div align='center'>Kode</div></td>
                        <td width='48%'><div align='center'>Kelompok Jabatan</div></td>
                        <td width='20%'><div align='center'>Index</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
						       <td>
                                    <center>
                                        <a href=?act=InputKelompokJabatan&action=UBAH&kode_kelompok=".str_replace(" ","_",$baris[0]).">[edit]</a>";?>
                                        <a href="?act=ListKelompokJabatan&action=HAPUS&kode_kelompok=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                               </td>
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>                                
                             </tr>";
                    }
            echo "</table>";
            
        } else {echo "Data Kelompok Jabatan masih kosong !";}

    ?>
    
    <?php
       $aksi=isset($_GET['action'])?$_GET['action']:NULL;
       if ($aksi=="HAPUS") {
            Hapus(" kelompok_jabatan "," kode_kelompok ='".$_GET['kode_kelompok']."' ","?act=ListKelompokJabatan");
       }
    ?>
    </div>
    <?php
        if(mysqli_num_rows($hasil)!=0) {
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah | <a target=_blank href=../penggajian/pages/kelompokjabatan/LaporanKelompokJabatan.php?&keyword=$keyword>Laporan</a> | <a target=_blank href=../penggajian/pages/kelompokjabatan/LaporanKelompokJabatanExel.php?&keyword=$keyword>Excel</a> |</div></td>                        
                    </tr>     
                 </table>");
        }
    ?>
    </div>
</div>
