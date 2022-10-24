<?php
    $_sql         = "SELECT * FROM set_tahun";
    $hasil        = bukaquery($_sql);
    $baris        = mysqli_fetch_row($hasil);
    $tahun        = empty($baris[0])?date("Y"):$baris[0];
    $blnini       = empty($baris[1])?date("m"):$baris[1];
    $bln_leng     = strlen($blnini);
    $bulan        = "0";
    if ($bln_leng==1){
        $bulan="0".$blnini;
    }else{
        $bulan=$blnini;
    }
?>

<div id="post">
    <div class="entry">  
        <div align="center" class="link">
            <a href=?act=DetailTindakan&action=TAMBAH>| Master Tindakan |</a>
            <a href=?act=ListTindakan>| Tindakan Paramedis |</a>
            <a href=?act=ListTindakanDokter>| Tindakan Dokter |</a>
            <a href=?act=ListTindakanSpesialis>| Tindakan Spesialis |</a>
            <a href=?act=HomeAdmin>| Menu Utama |</a>
        </div>   
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                $action   = isset($_GET['action'])?$_GET['action']:NULL;
                $keyword  = trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
                $keyword  = validTeks($keyword);
                echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Keyword</td><td width="">:</td>
                    <td width="82%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" autofocus />
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">
                    </td>
                </tr>
            </table><br>
            <div style="width: 100%; height: 78%; overflow: auto;">
            <?php
                $_sql      = "SELECT pegawai.id,pegawai.nik,pegawai.nama,pegawai.departemen,sum(tindakan.jmlh),sum(tindakan.jm) FROM tindakan right OUTER JOIN pegawai ON tindakan.id=pegawai.id and tindakan.tgl like '%".$tahun."-".$bulan."%'
                              where pegawai.stts_aktif<>'KELUAR' and pegawai.jbtn like '%dokter umum%' and (pegawai.nik like '%".$keyword."%' or pegawai.nama like '%".$keyword."%' or pegawai.departemen like '%".$keyword."%') group by pegawai.id order by pegawai.id ASC ";
                $hasil     = bukaquery($_sql);
                $jumlah    = mysqli_num_rows($hasil);
                $ttljm     = 0;
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='8%'><div align='center'>Proses</div></td>
                                <td width='10%'><div align='center'>NIP</div></td>
                                <td width='42%'><div align='center'>Nama</div></td>
                                <td width='15%'><div align='center'>Jumlah Tindakan</div></td>
                                <td width='25%'><div align='center'>Ttl.JM Tindakan</div></td>
                            </tr>";
                            while($baris = mysqli_fetch_array($hasil)) {
                                $ttljm=$ttljm+$baris[5];
                                echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                        <td>
                                            <center>
                                                <a href=?act=InputTindakanDokter&action=TAMBAH&id=$baris[0]>[Detail]</a>
                                            </center>
                                        </td>
                                        <td><a href=?act=InputTindakanDokter&action=TAMBAH&id=$baris[0]>$baris[1]</a></td>
                                        <td><a href=?act=InputTindakanDokter&action=TAMBAH&id=$baris[0]>$baris[2]</a></td>
                                        <td><a href=?act=InputTindakanDokter&action=TAMBAH&id=$baris[0]>";
                                $_sql2  = "select master_tindakan.nama,sum(tindakan.jmlh) from master_tindakan inner join tindakan on tindakan.tnd=master_tindakan.id where tindakan.id='$baris[0]' and tindakan.tgl like '%".$tahun."-".$bulan."%' group by tindakan.tnd ";
                                $hasil2 = bukaquery($_sql2);
                                 while($baris2 = mysqli_fetch_array($hasil2)) {
                                      echo "<table width='300px'><tr class='isi3'><td width='200px'>$baris2[0]</td><td>: $baris2[1]</td></tr></table>";
                                 }
                                echo"&nbsp;</a>
                                        </td>
                                        <td><a href=?act=InputTindakanDokter&action=TAMBAH&id=$baris[0]>".formatDuit($baris[5])."</a></td>
                                     </tr>";
                            }
                    echo "</table>";           
                } else {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='8%'><div align='center'>Proses</div></td>
                                <td width='10%'><div align='center'>NIP</div></td>
                                <td width='42%'><div align='center'>Nama</div></td>
                                <td width='15%'><div align='center'>Jumlah Tindakan</div></td>
                                <td width='25%'><div align='center'>Ttl.JM Tindakan</div></td>
                            </tr>
                          </table>";
                }
            ?>
            </div>
	</form>
        <?php
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, Ttl.JM : ".formatDuit($ttljm)."  <a target=_blank href=../penggajian/pages/tindakan/laporantindakandokter.php?&keyword=$keyword>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
        ?>
    </div>
</div>
