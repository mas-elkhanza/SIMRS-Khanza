
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysql_fetch_row($hasil);
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
    <div style="width: 100%; height: 80%; overflow: auto;">
    <?php
        
	$keyword=trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;        

        $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama FROM  pegawai
		 where  pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' or 
		 pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%'
		 order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysql_num_rows($hasil);
        if(mysql_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='9%'><div align='center'>Proses</div></td>
                        <td width='11%'><div align='center'>NIP</div></td>
                        <td width='25%'><div align='center'>Nama</div></td>
                        <td width='55%'><div align='center'>Riwayat Kegiatan Ilmiah Pegawai</div></td>
                    </tr>";
                    while($baris = mysql_fetch_array($hasil)) {
                        echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                <td>
                                    <center>
                                        <a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$baris[0]>[Detail]</a>
                                    </center>
                               </td>
                                <td><a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$baris[0]>$baris[1]</a></td>
                                <td><a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$baris[0]>$baris[2]</a></td>
                                <td>
                                   <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>";
                                        $_sql2 = "SELECT nama_seminar, mulai, penyelengara, tempat from riwayat_seminar where id='$baris[0]' ORDER BY mulai ASC ";
                                        $hasil2=bukaquery($_sql2);
                                        if(mysql_num_rows($hasil2)!=0) {
                                            echo "<tr class='isi4'>
                                                    <td width='5%'><div align='center'>NO.</div></td>
                                                    <td width='35%'><div align='center'>Nama Kegiatan</div></td>
                                                    <td width='20%'><div align='center'>Tanggal</div></td>
                                                    <td width='20%'><div align='center'>Penyelenggara</div></td>
                                                    <td width='20%'><div align='center'>Tempat</div></td>
                                                  </tr>";
                                        }
                                        $no=1;
                                        while($baris2 = mysql_fetch_array($hasil2)) { 
                                            echo "<tr> 
                                                    <td>$no</td>
                                                    <td>$baris2[0]</td>
                                                    <td>$baris2[1]</td>
                                                    <td>$baris2[2]</td>
                                                    <td>$baris2[3]</td>
                                                  </tr>";$no++;
                                        }
                             echo "</table>
                                </td>
                             </tr>";
                    }
            echo "</table>";           
        } else {echo "Data Jasa lain masih kosong !";}

    ?>
    </div>
	</form>
    <?php
        
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        
    ?>
    </div>
</div>
