

<div id="post">
    <div class="entry"> 
    <div align="center" class="link">
        <a href=?act=DetailBpjs&action=TAMBAH>| Stts BPJS |</a>
        <a href=?act=DetailJamsostek&action=TAMBAH>| Stts Jamsostek |</a>
        <a href=?act=DetailKoperasi&action=TAMBAH>| Stts Koperasi |</a>
        <a href=?act=ListKeanggotaan>| List Keanggotaan |</a>
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
    <div style="width: 100%; height: 78%; overflow: auto;">
    <?php
        $keyword = trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
        $keyword = validTeks($keyword);
        $_sql = "select pegawai.id,pegawai.nik,pegawai.nama,
                keanggotaan.koperasi, keanggotaan.jamsostek, keanggotaan.bpjs
                from keanggotaan right OUTER JOIN pegawai
                on keanggotaan.id=pegawai.id
                where pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' or 
                pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%' or
                pegawai.stts_aktif<>'KELUAR' and keanggotaan.koperasi like '%".$keyword."%' or
                pegawai.stts_aktif<>'KELUAR' and keanggotaan.bpjs like '%".$keyword."%' or
                pegawai.stts_aktif<>'KELUAR' and keanggotaan.jamsostek like '%".$keyword."%'
                order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='10%'><div align='center'>Proses</div></td>
                        <td width='10%'><div align='center'>NIP</div></td>
                        <td width='35%'><div align='center'>Nama</div></td>
                        <td width='15%'><div align='center'>Anggota Koperasi</div></td>
                        <td width='15%'><div align='center'>Anggota Jamsostek</div></td>
                        <td width='15%'><div align='center'>Anggota BPJS</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
                                <td>
                                    <center>
                                        <a href=?act=InputKeanggotaan&action=UBAH&id=$baris[0]>[Update]</a>
                                    </center>
                               </td>
                                <td>$baris[1] &nbsp;</td>
                                <td>$baris[2] &nbsp;</td>
                                <td>$baris[3] &nbsp;</td>
                                <td>$baris[4] &nbsp;</td>
                                <td>$baris[5] &nbsp;</td>
                             </tr>";
                    }
            echo "</table>";           
        } else {echo "Data keanggotaan masih kosong !";}

    ?>
    </div>
	</form>
    <?php
        if(mysqli_num_rows($hasil)!=0) {        
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah <a target=_blank href=../penggajian/pages/keanggotaan/LaporanKeanggotaan.php?&keyword=$keyword>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
        }
    ?>
    </div>
</div>
