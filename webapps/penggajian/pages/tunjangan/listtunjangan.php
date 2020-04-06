

<div id="post">
     <div class="entry">   

    <div align="center" class="link">
	<a href=?act=DetailTunjanganBulanan&action=TAMBAH>| Ms.Tunj Bulanan |</a>
        <a href=?act=DetailTunjanganHarian&action=TAMBAH>| Ms.Tunj Harian |</a>
        <a href=?act=DetailHarianBulanan&action=TAMBAH>| Harian-Bulanan |</a>
        <a href=?act=ListTunjangan>| List Penerima |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
	<form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $keyword=trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
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
        $keyword= validTeks($keyword);
        $_sql = "select pegawai.id,pegawai.nik,pegawai.nama,
		 pegawai.departemen from pegawai
		 where pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' or
		 pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%' or
		 pegawai.stts_aktif<>'KELUAR' and pegawai.departemen like '%".$keyword."%'
		 order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='8%'><div align='center'>NIP</div></td>
                        <td width='22%'><div align='center'>Nama</div></td>
                        <td width='10%'><div align='center'>Departemen</div></td>
                        <td width='30%'><div align='center'>Tnj. Bulanan Diterima</div></td>
			<td width='30%x'><div align='center'>Tnj. Harian Diterima</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>
                                <td>$baris[3]</td>
                                <td>";$_sql2="select master_tunjangan_bulanan.nama,
				             master_tunjangan_bulanan.tnj
					     from pnm_tnj_bulanan,master_tunjangan_bulanan
					     where pnm_tnj_bulanan.id_tnj=master_tunjangan_bulanan.id
					     and pnm_tnj_bulanan.id='$baris[0]'";
				      $hasil2=bukaquery($_sql2);
				     while($baris2 = mysqli_fetch_array($hasil2)) {
					  echo "<table width='100%'><tr class='isi3'><td width='65%'>$baris2[0]</td><td width='35%'>: ".formatDuit($baris2[1])."</td></tr></table>";
				     }
				    echo"<a href=?act=DetailPenerimaTunjanganBulanan&action=TAMBAH&id=$baris[0]>[Update]</a>
				</td>
				<td>";$_sql2="select master_tunjangan_harian.nama,
				             master_tunjangan_harian.tnj
					     from pnm_tnj_harian,master_tunjangan_harian
					     where pnm_tnj_harian.id_tnj=master_tunjangan_harian.id
					     and pnm_tnj_harian.id='$baris[0]'";
				      $hasil2=bukaquery($_sql2);
				     while($baris2 = mysqli_fetch_array($hasil2)) {
					  echo "<table width='100%'><tr class='isi3'><td width='65%'>$baris2[0]</td><td width='35%'>: ".formatDuit($baris2[1])."</td></tr></table>";
				     }
				    echo"<a href=?act=DetailPenerimaTunjanganHarian&action=TAMBAH&id=$baris[0]>[Update]</a>
				</td>
                             </tr>";
                    }
            echo "</table>";           
        } else {echo "Data penerima tunjangan masih kosong !";}

    ?>
    </div>
	</form>
    <?php
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah <a target=_blank href=../penggajian/pages/tunjangan/LaporanTunjangan.php?&keyword=$keyword>| Laporan |</a> </div></td>                        
                    </tr>     
                 </table>");
    ?>
    </div>
</div>

