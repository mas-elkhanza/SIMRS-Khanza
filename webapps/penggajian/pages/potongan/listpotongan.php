
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun         = $baris[0];
   $bulan          = $baris[1];
?>

<div id="post">
    <div class="entry">   

    <div align="center" class="link">
        <a href=?act=InputDansos&action=TAMBAH>| Set Dana Sosial |</a>
        <a href=?act=ListPotongan>| List Potongan |</a>
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
        $_sqlcari="select pegawai.id from pegawai";
        $hasilcari=bukaquery($_sqlcari);
        while($bariscari = mysqli_fetch_array($hasilcari)) {
            $_sqlkon = "SELECT stts_kerja FROM pegawai WHERE id='$bariscari[0]'";
            $hasilkon=bukaquery($_sqlkon);
            $bariskon= mysqli_fetch_row($hasilkon);

            $_sqldansos       = "SELECT dana FROM dansos";
	    $hasildansos      = bukaquery($_sqldansos);
	    $barisdansos      = mysqli_fetch_row($hasildansos);
            
            $dansos=0;
            if($bariskon[0]!="Poc"){
                  $dansos      = $barisdansos[0];
            }else if($bariskon[0]=="Poc"){
                  $dansos      = 0;
            }//selain pocokan dipotong dansos tanggal 03-03012

            $_sqlpot = "SELECT koperasi.wajib,jamsostek.biaya,bpjs.biaya
		     from keanggotaan,jamsostek,koperasi,bpjs
		     where keanggotaan.koperasi=koperasi.stts
	             and keanggotaan.jamsostek=jamsostek.stts
	             and keanggotaan.bpjs=bpjs.stts
		     and keanggotaan.id='$bariscari[0]'";
	    $hasilpot      =bukaquery($_sqlpot);
	    $barispot      = mysqli_fetch_row($hasilpot);
	    $simwajib	 = $barispot[0];
            $jamsostek   = $barispot[1];
            $bpjs        = $barispot[2];

            if(mysqli_num_rows($hasilpot)!=0) {  
                $_sqlcari2="select potongan.id from potongan where 
                       potongan.tahun='$tahun'  and
                       potongan.bulan='$bulan' and id='$bariscari[0]'";
                $hasilvalidasi  =bukaquery($_sqlcari2);
                if(mysqli_num_rows($hasilvalidasi)!=0) { 
                    EditData(" potongan ","bpjs='$bpjs',jamsostek='$jamsostek',dansos='$dansos',simwajib='$simwajib' where 
                       potongan.tahun='$tahun'  and
                       potongan.bulan='$bulan' and id='$bariscari[0]'  ");
                    
                }elseif(mysqli_num_rows($hasilvalidasi)==0) { 
                    InsertData(" potongan ","'$tahun','$bulan','$bariscari[0]','$bpjs','$jamsostek','$dansos','$simwajib',
               				     '0','0','0','0','0','0','-' ");
                }   
            } 
        }


        $keyword=trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;  
        $keyword= validTeks($keyword);

        $_sql = "SELECT pegawai.id, 
                    pegawai.nik,
                    pegawai.nama,
                    pegawai.departemen,
                    keanggotaan.koperasi, 
                    keanggotaan.jamsostek, 				 
                    keanggotaan.bpjs,
                    potongan.bpjs, 
                    potongan.jamsostek, 
                    potongan.dansos, 
                    potongan.simwajib, 
                    potongan.angkop, 
                    potongan.angla, 
                    potongan.telpri, 
                    potongan.pajak, 
                    potongan.pribadi, 
                    potongan.lain, 
                    potongan.ktg
                    FROM keanggotaan, potongan
                    RIGHT OUTER JOIN pegawai ON potongan.id = pegawai.id
                    AND tahun like '%".$tahun."%'  and bulan like '%".$bulan."%' 
                    WHERE pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and pegawai.nik like '%".$keyword."%' or
                    pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and pegawai.nama like '%".$keyword."%' or
                    pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and pegawai.departemen like '%".$keyword."%' or
                    pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and keanggotaan.koperasi like '%".$keyword."%' or
                    pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and keanggotaan.bpjs like '%".$keyword."%' or
                    pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and keanggotaan.jamsostek like '%".$keyword."%'
                    order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        $jml=0;
        
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='2100px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='70px'><div align='center'>Proses</div></td>
                        <td width='100px'><div align='center'>NIP</div></td>
                        <td width='250px'><div align='center'>Nama</div></td>
			<td width='100px'><div align='center'>Departemen</div></td>
                        <td width='80px'><div align='center'>Anggota Koperasi</div></td>
                        <td width='80px'><div align='center'>Anggota Jamsostek</div></td>
                        <td width='80px'><div align='center'>Anggota BPJS</div></td>
                        <td width='100px'><div align='center'>BPJS</div></td>
                        <td width='100px'><div align='center'>Jamsostek</div></td>
                        <td width='100px'><div align='center'>Dana Sosial</div></td>
                        <td width='100px'><div align='center'>Simpanan Wajib</div></td>
                        <td width='100px'><div align='center'>Angsuran Koperasi</div></td>
                        <td width='100px'><div align='center'>Angsuran Lain</div></td>
                        <td width='100px'><div align='center'>Telepon Pribadi</div></td>
                        <td width='100px'><div align='center'>Pajak</div></td>
                        <td width='100px'><div align='center'>Pribadi</div></td>
                        <td width='100px'><div align='center'>Lain-Lain</div></td>
                        <td width='100px'><div align='center'>Total Potongan</div></td>
                        <td width='200px'><div align='center'>Keterangan</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
			$ttl=$baris[7]+$baris[8]+$baris[9]+$baris[10]+$baris[11]+$baris[12]+$baris[13]+$baris[14]+$baris[15]+$baris[16];
                        $jml=$jml+$ttl;
                        echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                <td>
                                    <center>
                                        <a href=?act=InputPotongan&action=UBAH&id=$baris[0]>[Update]</a>
                                    </center>
                               </td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>$baris[1]</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>$baris[2]</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>$baris[3]</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>$baris[4]</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>$baris[5]</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>$baris[6]</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[7])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[8])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[9])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[10])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[11])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[12])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[13])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[14])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[15])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($baris[16])."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>".formatDuit($ttl)."</a></td>
                                <td><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>$baris[17]</a></td>
                             </tr>";
                    }
            echo "</table>";           
        } else {echo "Data potongan masih kosong !";}

    ?>
    </div>
	</form>
         <?php
        if(mysqli_num_rows($hasil)!=0) {
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, Jml.Ptg : ".formatDuit($jml)." <a target=_blank href=../penggajian/pages/potongan/LaporanPotongan.php?&keyword=$keyword>| Laporan |</a> </div></td>                        
                    </tr>     
                 </table>");
        }
    ?>
    </div>
</div>
