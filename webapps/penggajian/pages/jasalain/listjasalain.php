
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
        
	$keyword = trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;        
         $keyword = validTeks($keyword);
         $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama,
                pegawai.departemen,sum(bsr_jasa)
                FROM jasa_lain right OUTER JOIN pegawai
                ON jasa_lain.id=pegawai.id and thn='".$tahun."'
                and bln='".$bulan."'
                where  pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' or 
                pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%' or
                pegawai.stts_aktif<>'KELUAR' and pegawai.departemen like '%".$keyword."%' 
                group by pegawai.id order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        $ttljm=0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='9%'><div align='center'>Proses</div></td>
                        <td width='11%'><div align='center'>NIP</div></td>
                        <td width='45%'><div align='center'>Nama</div></td>
                        <td width='15%'><div align='center'>Depart</div></td>
                        <td width='20%'><div align='center'>Total Jasa lain</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        $ttljm=$ttljm+$baris[4];
                        echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                <td>
                                    <center>
                                        <a href=?act=InputJasLa&action=TAMBAH&id=$baris[0]>[Detail]</a>
                                    </center>
                               </td>
                                <td><a href=?act=InputJasLa&action=TAMBAH&id=$baris[0]>$baris[1]</a></td>
                                <td><a href=?act=InputJasLa&action=TAMBAH&id=$baris[0]>$baris[2]</a></td>
                                <td><a href=?act=InputJasLa&action=TAMBAH&id=$baris[0]>$baris[3]</a></td>
                                <td><a href=?act=InputJasLa&action=TAMBAH&id=$baris[0]>".formatDuit($baris[4])."</a></td>
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
                        <td><div align='left'>Data : $jumlah, Ttl.Jasa lain : ".formatDuit($ttljm)."</div></td>                        
                    </tr>     
                 </table>");
        
    ?>
    </div>
</div>
