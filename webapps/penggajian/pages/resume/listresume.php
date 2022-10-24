<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun        = empty($baris[0])?date("Y"):$baris[0];
   $bulan        = empty($baris[1])?date("m"):$baris[1];
?>
<div id="post">  
    <div align="center" class="link">
        <a href=?act=InputResume&action=TAMBAH>| Input Pendapatan Resume |</a>
        <a href=?act=InputPenerimaResume&action=TAMBAH>| Input Bagian Resume |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    &nbsp;Pendapatan Resume : 
    <div style="width: 100%; height: 65px;overflow: auto;">
    <?php
        $_sql         = "SELECT set_resume.pendapatan_resume,set_resume.persen_rs,set_resume.bagian_rs,set_resume.persen_kry,set_resume.bagian_kry FROM set_resume WHERE set_resume.tahun='$tahun' and set_resume.bulan='$bulan'";
        $hasil        = bukaquery($_sql);
        $jumlah       = mysqli_num_rows($hasil);
        $total_resume = 0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='25%'><div align='center'>Pendapatan Resume</div></td>
                        <td width='6%'><div align='center'>% RS</div></td>
			<td width='25%'><div align='center'>Bagian RS</div></td>
                        <td width='7%'><div align='center'>% Kry</div></td>
                        <td width='25%'><div align='center'>Bagian Kry</div></td>
                    </tr>";					
                    while($baris = mysqli_fetch_array($hasil)) {
                        $total_resume=$baris[0];
                        echo "<tr class='isi'>
				<td>
                                 <center>
				   <a href=?act=InputResume&action=UBAH>[edit]</a>
                                   <a href=?act=ListResume&action=HAPUSAKTE&pendapatan_resume=".$baris[0]." >[hapus]</a>
                                 </center>
                                </td>
                                <td>".formatDuit($baris[0])."</td>
                                <td>$baris[1]%</td>
                                <td>".formatDuit($baris[2])."</td>
                                <td>$baris[3]%</td>
                                <td>".formatDuit($baris[4])."</td>
                             </tr>";
                    }
            echo "</table>";    
        } else {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='25%'><div align='center'>Pendapatan Resume</div></td>
                        <td width='6%'><div align='center'>% RS</div></td>
			<td width='25%'><div align='center'>Bagian RS</div></td>
                        <td width='7%'><div align='center'>% Kry</div></td>
                        <td width='25%'><div align='center'>Bagian Kry</div></td>
                    </tr>
                </table>";	
        }
    ?>      
    </div>
    &nbsp;Pembagian Resume : 
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
    <div style="width: 100%; height: 64%; overflow: auto;">
    <?php
        $_sql    = "SELECT pembagian_resume.id,pegawai.nama,pembagian_resume.persen FROM pembagian_resume inner join pegawai on pembagian_resume.id=pegawai.id where pegawai.nama like '%".$keyword."%' ORDER BY pembagian_resume.persen desc";
        $hasil   = bukaquery($_sql);
        $jumlah  = mysqli_num_rows($hasil);
	$ttl     = 0;
	$prosen  = 0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='50%'><div align='center'>Nama Karyawan</div></td>
                        <td width='13%'><div align='center'>Porsi Bagian</div></td>
			<td width='25%'><div align='center'>Bagian Karyawan</div></td>
                    </tr>";
	    $bagiankry = 0;
            while($baris = mysqli_fetch_array($hasil)) {
                $bagiankry = ($baris[2]/100)*$total_resume;
                $ttl       = $ttl+$bagiankry;
                $prosen    = $prosen+$baris[2];
                echo "<tr class='isi'>
                        <td>
                            <center>
                             <a href=?act=InputPenerimaResume&action=UBAH&id=".str_replace(" ","_",$baris[0]).">[edit]</a>
                             <a href=?act=ListResume&action=HAPUSPENERIMA&id=".str_replace(" ","_",$baris[0]).">[hapus]</a>
                            </center>
                        </td>
                        <td>$baris[1]</td>
                        <td>$baris[2]%</td>
                        <td>".formatDuit($bagiankry)."</td>
                     </tr>";
            }
            echo "</table>";
        } else {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='50%'><div align='center'>Nama Karyawan</div></td>
                        <td width='13%'><div align='center'>Porsi Bagian</div></td>
		        <td width='25%'><div align='center'>Bagian Karyawan</div></td>
                    </tr>
                  </table>";
        }
    ?>    
    </div>
    <?php
        $aksi=isset($_GET['action'])?$_GET['action']:NULL;
        if ($aksi=="HAPUSAKTE") {
            Hapus(" set_resume  "," pendapatan_resume ='".validTeks($_GET['pendapatan_resume'])."' and tahun='$tahun' and bulan='$bulan' ","?act=ListResume");
        }
        if ($aksi=="HAPUSPENERIMA") {
            Hapus(" pembagian_resume "," id ='".validTeks($_GET['id'])."'","?act=ListResume");
        }
        echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                <tr class='head'>
                    <td><div align='left'>Data : $jumlah, Ttl Prosen : ".$prosen."%, Ttl Bagian : ".formatDuit($ttl)." | <a target=_blank href=../penggajian/pages/resume/LaporanResume.php?&keyword=$keyword>Laporan</a> | <a target=_blank href=../penggajian/pages/resume/LaporanResumeExel.php?&keyword=$keyword>Excel</a> |</div></td>                        
                </tr>     
             </table>");
    ?>
</div>
