

<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun        = $baris[0];
   $bulan        = $baris[1];
?>

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputInsentif&action=TAMBAH>| Input Pendapatan |</a>
        <a href=?act=InputIndex&action=TAMBAH>| Input Insentif |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    &nbsp;&nbsp;Pendapatan :
    <div style="width: 100%; height: 65px;overflow: auto;">
    <?php
        $_sql   = "SELECT pendapatan,persen,total_insentif FROM set_insentif WHERE tahun='$tahun' and bulan='$bulan' ORDER BY pendapatan";
        $hasil   =bukaquery($_sql);
        $jumlah  =mysqli_num_rows($hasil);
        $total_insentif="0";
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='34%'><div align='center'>Pendapatan</div></td>
                        <td width='20%'><div align='center'>Prosentase</div></td>
                        <td width='34%'><div align='center'>Total Insentif</div></td>
                    </tr>";					
                    while($baris = mysqli_fetch_array($hasil)) {
                        $total_insentif=$baris[2];						
                        echo "<tr class='isi'>
				<td>
                                 <center>
				   <a href=?act=InputInsentif&action=UBAH>[edit]</a>";?>
                                   <a href="?act=ListInsentif&action=HAPUSINSENTIF&pendapatan=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                               </td>
                                <td>".formatDuit($baris[0])."</td>
                                <td>$baris[1]%</td>
                                <td>".formatDuit($baris[2])."</td>                                
                             </tr>";
                    }
            echo "</table>";
            
        } else {echo "Data Pendapatan  masih kosong !";}

    ?>      
    </div>

    &nbsp;&nbsp;Insentif :
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
        $keyword = validTeks($keyword);
        $_sql    = "SELECT dep_id,persen FROM indexins where dep_id like '%".$keyword."%' ORDER BY persen desc";
        $hasil   =bukaquery($_sql);
        $jumlah  =mysqli_num_rows($hasil);
		$ttl=0;
		$prosen=0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='20%'><div align='center'>Kode Index</div></td>
                        <td width='30%'><div align='center'>Porsi Insentif</div></td>
			<td width='38%'><div align='center'>Total Insentif</div></td>
                    </tr>";
		    $insentifindex=0;
                    while($baris = mysqli_fetch_array($hasil)) {
                        $insentifindex=($baris[1]/100)*$total_insentif;
			$ttl=$ttl+$insentifindex;
			$prosen=$prosen+$baris[1];
                        echo "<tr class='isi'>
                                <td>
                                    <center>
					<a href=?act=InputIndex&action=UBAH&dep_id=".str_replace(" ","_",$baris[0]).">[edit]</a>";?>
                                        <a href="?act=ListInsentif&action=HAPUSINDEX&dep_id=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                               </td>
                                <td>$baris[0]</td>
                                <td>$baris[1]%</td>
                                <td>".formatDuit($insentifindex)."</td>
                             </tr>";
                    }
            echo "</table>";

        } else {echo "Data Insentif  masih kosong !";}
    ?>
    
    </div>
    <?php
       $aksi=isset($_GET['action']);
       if ($aksi=="HAPUSINSENTIF") {
            Hapus(" set_insentif  "," pendapatan ='".$_GET['pendapatan']."' and tahun='$tahun' and bulan='$bulan' ","?act=ListInsentif");
       }
       if ($aksi=="HAPUSINDEX") {
            Hapus(" indexins "," dep_id ='".$_GET['dep_id']."'","?act=ListInsentif");
       }
        if(mysqli_num_rows($hasil)!=0) {
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, Ttl Prosen : ".$prosen."%, Ttl Insentif : ".formatDuit($ttl)." | <a target=_blank href=../penggajian/pages/insentif/LaporanInsentif.php?&keyword=$keyword>Laporan</a> | <a target=_blank href=../penggajian/pages/insentif/LaporanInsentifExel.php?&keyword=$keyword>Excel</a> |</div></td>                        
                    </tr>     
                 </table>");
        }
    ?>
</div>
