
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun         = $baris[0];
   $bulan          = $baris[1];
?>

<div id="post">    
    <div align="center" class="link">
        <a href=?act=InputWarung&action=TAMBAH>| Input Pendapatan Warung |</a>
        <a href=?act=InputPenerimaWarung&action=TAMBAH>| Input Bagian Warung |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    &nbsp;&nbsp;Pendapatan Warung : 
    <div style="width: 100%; height: 65px;overflow: auto;">
    <?php
        $_sql   = "SELECT pendapatan_warung,persen_rs,
                   bagian_rs,persen_kry,bagian_kry
                   FROM set_warung WHERE tahun='$tahun' and bulan='$bulan' ORDER BY pendapatan_warung";
        $hasil   =bukaquery($_sql);
        $jumlah  =mysqli_num_rows($hasil);
        $total_warung="0";
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='25%'><div align='center'>Pendapatan Warung</div></td>
                        <td width='6%'><div align='center'>% RS</div></td>
			<td width='25%'><div align='center'>Bagian RS</div></td>
                        <td width='7%'><div align='center'>% Kry</div></td>
                        <td width='25%'><div align='center'>Bagian Kry</div></td>
                    </tr>";					
                    while($baris = mysqli_fetch_array($hasil)) {
                        $total_warung=$baris[0];
                        echo "<tr class='isi'>
				<td>
                                 <center>
				   <a href=?act=InputWarung&action=UBAH>[edit]</a>";?>
                                   <a href="?act=ListWarung&action=HAPUSAKTE&pendapatan_warung=<?php print $baris[0] ?>" >[hapus]</a>
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
            
        } else {echo "Data Pendapatan Warung masih kosong !";}

    ?>      
    </div>

    &nbsp;&nbsp;Penerima bagian Warung : 
    <div style="width: 100%; height: 75%; overflow: auto;">
    <?php
        $_sql   = "SELECT pembagian_warung.id,pegawai.nama,persen FROM pembagian_warung,pegawai
                 where pembagian_warung.id=pegawai.id ORDER BY persen desc";
        $hasil   =bukaquery($_sql);
        $jumlah  =mysqli_num_rows($hasil);
	$ttl=0;
	$prosen=0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='88%'><div align='center'>Nama Karyawan</div></td>
                    </tr>";
		    $bagiankry=0;
                    while($baris = mysqli_fetch_array($hasil)) {
                        $bagiankry=($baris[2]/100)*$total_warung;
			$ttl=$ttl+$bagiankry;
			$prosen=$prosen+$baris[2];
                        echo "<tr class='isi'>
                                <td>
                                    <center>";?>
                                        <a href="?act=ListWarung&action=HAPUSPENERIMA&id=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                               </td>
                                <td>$baris[1]</td>
                             </tr>";
                    }
            echo "</table>";

        } else {echo "Data Bagian warung Karyawan masih kosong !";}
    ?>
    
    </div>
    <?php
       $aksi=isset($_GET['action'])?$_GET['action']:NULL;
       if ($aksi=="HAPUSAKTE") {
            Hapus(" set_warung  "," pendapatan_warung ='".$_GET['pendapatan_warung']."' and tahun='$tahun' and bulan='$bulan' ","?act=ListWarung");
       }
       if ($aksi=="HAPUSPENERIMA") {
            Hapus(" pembagian_warung "," id ='".$_GET['id']."'","?act=ListWarung");
       }
        if(mysqli_num_rows($hasil)!=0) {
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah </div></td>                        
                    </tr>     
                 </table>");          
        }
    ?>
    </div>
</div>
