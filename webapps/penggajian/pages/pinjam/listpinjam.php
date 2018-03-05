
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun         = $baris[0];
   $bln_leng=strlen($baris[1]);
   $bulan="0";
   if ($bln_leng==1){
    	$bulan="0".$baris[1];
   }else{
		$bulan=$baris[1];
   }
?>

<div id="post">
    <div class="entry">
    <div align="center" class="link">
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
	$keyword=trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
        $say=" pegawai.id=keanggotaan.id and keanggotaan.koperasi='Y'  ";
        $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama
                FROM pegawai,keanggotaan 
                where $say and pegawai.nik like '%".$keyword."%' or
                $say and pegawai.nama like '%".$keyword."%'
                ORDER BY pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);

        if(mysqli_num_rows($hasil)!=0) {

            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='10%'><div align='center'>Proses</div></td>
                        <td width='12%'><div align='center'>NIP</div></td>
                        <td width='48%'><div align='center'>Nama</div></td>
                        <td width='30%'><div align='center'>Keterangan Pinjam</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        $_sql2="select status from peminjaman_koperasi where
                               status='Belum Lunas' and id='$baris[0]' ";
			$hasil2=bukaquery($_sql2);
                        $jumlah2=mysqli_num_rows($hasil2);
                        $status="Tidak Ada Pinjaman";
                        if($jumlah2!=0){
                           $status="Ada Pinjaman";
                        }
                        echo "<tr class='isi'>
                                <td>
                                    <center>
                                        <a href=?act=DetailPinjam&action=TAMBAH&id=$baris[0]>[Detail]</a>&nbsp;
                                    </center>
                               </td>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>
                                <td>$status</td>
                             </tr>";
                    }
            echo "</table>";

        } else {echo "Data Pinjam masih kosong !";}

    ?>
    </div>
	</form>
    <?php
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah <a target=_blank href=../penggajian/pages/pinjam/LaporanPinjam.php?&keyword=$keyword>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
    ?>
    </div>
</div>
