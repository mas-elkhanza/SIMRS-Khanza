

<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun        = $baris[0];
   $bulan        = $baris[1];
?>

<div id="post">    
    <div align="center" class="link">
        <a href=?act=InputAkte&action=TAMBAH>| Input Pendapatan Akte |</a>
        <a href=?act=InputPenerimaAkte&action=TAMBAH>| Input Bagian Akte |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    &nbsp;&nbsp;Pendapatan Akte :
    <div style="width: 100%; height: 65px;overflow: auto;">
    <?php
        $_sql   = "SELECT pendapatan_akte,persen_rs,
                   bagian_rs,persen_kry,bagian_kry
                   FROM set_akte WHERE tahun='$tahun' and bulan='$bulan' ORDER BY pendapatan_akte";
        $hasil   =bukaquery($_sql);
        $jumlah  =mysqli_num_rows($hasil);
        $total_akte=0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='25%'><div align='center'>Pendapatan Akte</div></td>
                        <td width='6%'><div align='center'>% RS</div></td>
			<td width='25%'><div align='center'>Bagian RS</div></td>
                        <td width='7%'><div align='center'>% Kry</div></td>
                        <td width='25%'><div align='center'>Bagian Kry</div></td>
                    </tr>";					
                    while($baris = mysqli_fetch_array($hasil)) {
                        $total_akte=$baris[4];
                        echo "<tr class='isi'>
				<td>
                                 <center>
				   <a href=?act=InputAkte&action=UBAH>[edit]</a>";?>
                                   <a href="?act=ListAkte&action=HAPUSAKTE&pendapatan_akte=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                               </td>
                                <td>".formatDuit($baris[0])."</td>
                                <td>$baris[1]%</td>
                                <td>".formatDuit($baris[2])."</td>
                                <td>$baris[3]%</td>
                                <td>".formatDuit($baris[4])."</td>
                             </tr>";
                    }
            echo "</table>";
            
        } else {echo "Data Pendapatan Akte masih kosong !";}

    ?>      
    </div>
    &nbsp;&nbsp;Pembagian Akte :
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
    <div style="width: 100%; height: 64%; overflow: auto;">
    <?php
		$keyword = trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
        $_sql    = "SELECT pembagian_akte.id,pegawai.nama,persen FROM pembagian_akte inner join pegawai
                   on pembagian_akte.id=pegawai.id where pegawai.nama like '%".$keyword."%' ORDER BY persen desc";
        $hasil   = bukaquery($_sql);
        $jumlah  = mysqli_num_rows($hasil);
	$ttl=0;
	$prosen=0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='38%'><div align='center'>Nama Karyawan</div></td>
                        <td width='25%'><div align='center'>Porsi Bagian</div></td>
						<td width='25%'><div align='center'>Bagian Karyawan</div></td>
                    </tr>";
					$bagiankry=0;
                    while($baris = mysqli_fetch_array($hasil)) {
                        $bagiankry=($baris[2]/100)*$total_akte;
						$ttl=$ttl+$bagiankry;
						$prosen=$prosen+$baris[2];
                        echo "<tr class='isi'>
                                <td>
                                    <center>
					<a href=?act=InputPenerimaAkte&action=UBAH&id=".str_replace(" ","_",$baris[0]).">[edit]</a>";?>
                                        <a href="?act=ListAkte&action=HAPUSPENERIMA&id=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                               </td>
                                <td>$baris[1]</td>
                                <td>$baris[2]%</td>
                                <td>".formatDuit($bagiankry)."</td>
                             </tr>";
                    }
            echo "</table>";

        } else {echo "Data Bagian Karyawan masih kosong !";}
    ?>    
    </div>
    <?php
       $aksi=isset($_GET['action'])?$_GET['action']:NULL;
       if ($aksi=="HAPUSAKTE") {
            Hapus(" set_akte  "," pendapatan_akte ='".$_GET['pendapatan_akte']."' and tahun='$tahun' and bulan='$bulan' ","?act=ListAkte");
       }
       if ($aksi=="HAPUSPENERIMA") {
            Hapus(" pembagian_akte "," id ='".$_GET['id']."'","?act=ListAkte");
       }
       if(mysqli_num_rows($hasil)!=0) {
           echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, Ttl Prosen : ".$prosen."%, Ttl Bagian : ".formatDuit($ttl)." | <a target=_blank href=../penggajian/pages/akte/LaporanAkte.php?&keyword=$keyword>Laporan</a> | <a target=_blank href=../penggajian/pages/akte/LaporanAkteExel.php?&keyword=$keyword>Excel</a> |</div></td>                        
                    </tr>     
                 </table>");
       }
    ?>
</div>

