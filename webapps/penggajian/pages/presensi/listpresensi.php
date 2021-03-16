
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun        = empty($baris[0])?date("Y"):$baris[0];
   $bulan        = empty($baris[1])?date("m"):$baris[1];
   $bln_leng     = strlen(empty($baris[1])?date("m"):$baris[1]);
   if ($bln_leng==1){
    	$bulan = "0".$bulan;
   }else{
		$bulan = $bulan;
   }
?>

<div id="post">
    <div align="center" class="link">
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div> 
    <div class="entry">
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
                    <td width="82%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" autofocus/>
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">
                    </td>
                </tr>
        </table> <br>
            
    <div style="width: 100%; height: 78%; overflow: auto;">	
    <?php
        $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama
                FROM pegawai where pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' or pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%'
                ORDER BY pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);

        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='7%'><div align='center'>Proses</div></td>
                        <td width='10%'><div align='center'>NIP</div></td>
                        <td width='33%x'><div align='center'>Nama</div></td>
                        <td width='10%'><div align='center'>Hadir HB</div></td>
                        <td width='15%'><div align='center'>Index Lembur HB</div></td>
                        <td width='10%'><div align='center'>Hadir HR</div></td>
                        <td width='15%'><div align='center'>Index Lembur HR</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        $_sql2="select count(presensi.id),sum(presensi.lembur)
                                from presensi
                                where presensi.id='$baris[0]' and presensi.tgl like '%".$tahun."-".$bulan."%'
                                and presensi.jns='HB'
                                group by presensi.id";
			            $hasil2=bukaquery($_sql2);
			            $baris2 = mysqli_fetch_array($hasil2);
                        $_sql3="select count(presensi.id),sum(presensi.lembur)
                                from presensi
                                where presensi.id='$baris[0]' and presensi.tgl like '%".$tahun."-".$bulan."%'
                                and presensi.jns='HR'
                                group by presensi.id";
			            $hasil3=bukaquery($_sql3);
			            $baris3=mysqli_fetch_array($hasil3);
                        
                        echo "<tr class='isi'>
                                <td>
                                    <center>
                                        <a href=?act=DetailPresensi&action=TAMBAH&id=$baris[0]>[Detail]</a>&nbsp;
                                    </center>
                               </td>
                                <td>".@$baris[1]."&nbsp;</td>
                                <td>".@$baris[2]."&nbsp;</td>
                                <td>".@$baris2[0]."&nbsp;</td>
                                <td>".@$baris2[1]."&nbsp;</td>
                                <td>".@$baris3[0]."&nbsp;</td>
                                <td>".@$baris3[1]."&nbsp;</td>
                             </tr>";
                    }
            echo "</table>";

        } else {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='7%'><div align='center'>Proses</div></td>
                        <td width='10%'><div align='center'>NIP</div></td>
                        <td width='33%x'><div align='center'>Nama</div></td>
                        <td width='10%'><div align='center'>Hadir HB</div></td>
                        <td width='15%'><div align='center'>Index Lembur HB</div></td>
                        <td width='10%'><div align='center'>Hadir HR</div></td>
                        <td width='15%'><div align='center'>Index Lembur HR</div></td>
                    </tr>
                  </table>";
        }

    ?>
    </div>
	</form>
    <?php
        if(mysqli_num_rows($hasil)!=0) {
            $hasil1=bukaquery("SELECT pegawai.id,pegawai.nik,pegawai.nama
                FROM pegawai where pegawai.nik like '%".$keyword."%' or pegawai.nama like '%".$keyword."%'
                ORDER BY pegawai.id ASC ");
            $jumladiv=mysqli_num_rows($hasil1);
            $i=$jumladiv/19;
            $i=ceil($i);
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah <a target=_blank href=../penggajian/pages/presensi/LaporanPresensi.php?&keyword=$keyword>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
        }
    ?>
    </div>
</div>
