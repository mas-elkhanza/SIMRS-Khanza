
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
    <div align="center" class="link">
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div> 
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
              
                $action      =$_GET['action'];
                //$keyword     =$_GET['keyword'];
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
    <div style="width: 100%; height: 500px; overflow: auto;">
	
    <?php
        $awal=$_GET['awal'];
	$keyword=trim($_POST['keyword']);
        if (empty($awal)) $awal=0;
        $keyword= validTeks($keyword);


        $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama,count(presensi.id) 
                FROM pegawai LEFT OUTER JOIN presensi
                ON pegawai.id=presensi.id and tgl like '%".$tahun."-".$bulan."%' and pegawai.nik like '%".$keyword."%' or
                pegawai.id=presensi.id and tgl like '%".$tahun."-".$bulan."%' and pegawai.nama like '%".$keyword."%'
                group by pegawai.id ORDER BY pegawai.nik ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);

        if(mysqli_num_rows($hasil)!=0) {

            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='20%'><div align='center'>NIP</div></td>
                        <td width='65%'><div align='center'>Nama</div></td>
                        <td width='65%'><div align='center'>Kehadiran</div></td>
                        <td width='15%'><div align='center'>Proses</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>
                                <td>$baris[3]</td>
                                <td>
                                    <center>
                                        <a href=?act=DetailPresensi&action=TAMBAH&id=$baris[0]>[Detail]</a>&nbsp;
                                    </center>
                               </td>
                             </tr>";
                    }
            echo "</table>";

        } else {echo "Data Presensi masih kosong !";}

    ?>
    </div>
	</form>
    <?php
        if(mysqli_num_rows($hasil)!=0) {
            $jumladiv=mysqli_num_rows($hasil);
            $i=$jumladiv/19;
            $i=ceil($i);
            echo("Data : $jumlah <a target=_blank href=../penggajian/pages/presensi/LaporanPresensi.php?&keyword=$keyword>| Laporan |</a> ");
        }
    ?>
    </div>
</div>
