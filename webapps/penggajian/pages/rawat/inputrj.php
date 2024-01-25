<?php
    $_sql      = "SELECT * FROM set_tahun";
    $hasil     = bukaquery($_sql);
    $baris     = mysqli_fetch_row($hasil);
    $tahun     = empty($baris[0])?date("Y"):$baris[0];
    $blnini    = empty($baris[1])?date("m"):$baris[1];
    $bln_leng  = strlen($blnini);
    $bulan     = "0";
    if ($bln_leng==1){
        $bulan="0".$blnini;
    }else{
        $bulan=$blnini;
    }
?>
<div id="post">
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      = isset($_GET['action'])?$_GET['action']:NULL;
                $id          = validTeks(isset($_GET['id'])?$_GET['id']:NULL);
                $tgl         = $tahun."-".$bulan."-01 00:00:00";
                $tnd         = validTeks(isset($_GET['tnd'])?$_GET['tnd']:NULL);
                $jm          = validangka(isset($_GET['jm'])?$_GET['jm']:NULL);
                $nm_pasien   = validTeks(isset($_GET['nm_pasien'])?$_GET['nm_pasien']:NULL);
                $kamar       = validTeks(isset($_GET['kamar'])?$_GET['kamar']:NULL);
                $diagnosa    = validTeks(isset($_GET['diagnosa'])?$_GET['diagnosa']:NULL);
                $jmlh        = validangka(isset($_GET['jmlh'])?$_GET['jmlh']:NULL);
                echo "<input type=hidden name=id  value=$id><input type=hidden name=tgl value=$tgl><input type=hidden name=tgl value=$tnd><input type=hidden name=action value=$action>";
		$_sql        = "SELECT pegawai.nik,pegawai.nama FROM pegawai where pegawai.id='$id'";
                $hasil       = bukaquery($_sql);
                $baris       = mysqli_fetch_row($hasil);
                $_sqlnext    = "SELECT pegawai.id FROM pegawai WHERE pegawai.id>'$id' and pegawai.jbtn like '%dokter spesialis%' order by pegawai.id asc limit 1";
                $hasilnext   = bukaquery($_sqlnext);
                $barisnext   = mysqli_fetch_row($hasilnext);
                @$next       = $barisnext[0];
                $_sqlprev    = "SELECT pegawai.id FROM pegawai WHERE pegawai.id<'$id' and pegawai.jbtn like '%dokter spesialis%' order by pegawai.id desc limit 1";
                $hasilprev   = bukaquery($_sqlprev);
                $barisprev   = mysqli_fetch_row($hasilprev);
                @$prev       = $barisprev[0];
                if(empty($next)){
                    $next=$prev;
                }
                if(empty($prev)){
                    $prev=$next;
                }
                echo "<div align='center' class='link'>
                          <a href=?act=InputRj&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListRj>| Tindakan Spesialis |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputRj&action=TAMBAH&id=$next>-->></a>
                      </div>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >NIP</td><td width="">:</td>
                    <td width="67%"><?php echo @$baris[0];?></td>
                </tr>
                <tr class="head">
                    <td width="31%">Nama</td><td width="">:</td>
                    <td width="67%"><?php echo @$baris[1];?></td>
                </tr>                
		<tr class="head">
                    <td width="25%" >Tindakan</td><td width="">:</td>
                    <td width="75%">
                        <select name="tnd" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" autofocus>
                            <?php
                                $_sql  = "SELECT master_tindakan.id,master_tindakan.nama FROM master_tindakan where master_tindakan.jns='dr Spesialis' ORDER BY master_tindakan.nama";
                                $hasil = bukaquery($_sql);
                                if($action == "UBAH"){
                                    $_sql2  = "SELECT master_tindakan.id,master_tindakan.nama FROM master_tindakan where master_tindakan.id='$tnd'";
                                    $hasil2 = bukaquery($_sql2);
                                    if($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi2' value='$baris2[0]'>$baris2[1]</option>";
                                    }
                                }
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi2' value='$baris[0]'>$baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>                
                <tr class="head">
                    <td width="31%" >Jumlah Tindakan</td><td width="">:</td>
                    <td width="67%">
                        <input name="jmlh" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $jmlh;?>" size="10" maxlength="10" />
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 = validTeks(trim(isset($_POST['id']))?trim($_POST['id']):NULL);
                    $tgl                = $tahun."-".$bulan."-01 00:00:00";
                    $tnd                = validTeks(trim(isset($_POST['tnd']))?trim($_POST['tnd']):NULL);
                    $_sql               = "SELECT master_tindakan.jm FROM master_tindakan where master_tindakan.id='$tnd'";
                    $hasil              = bukaquery($_sql);
                    $baris              = mysqli_fetch_array($hasil);
                    $jm                 = $baris[0];
                    $nm_pasien          = trim(isset($_POST['nm_pasien']))?trim($_POST['nm_pasien']):NULL;
                    $nm_pasien          = validTeks($nm_pasien);
                    $kamar              = trim(isset($_POST['kamar']))?trim($_POST['kamar']):NULL;
                    $kamar              = validTeks($kamar);
                    $diagnosa           = trim(isset($_POST['diagnosa']))?trim($_POST['diagnosa']):NULL;
                    $diagnosa           = validTeks($diagnosa);
                    $jmlh               = trim(isset($_POST['jmlh']))?trim($_POST['jmlh']):NULL;
                    $jmlh               = validangka($jmlh);
                    $ttljm              = $jm*$jmlh;
                    if ((isset($id))&&(isset($tgl))&&(isset($tnd))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" rawatjalan  "," '$tgl','$id','$tnd','$ttljm','-','-','-','$jmlh'", " detail tindakan " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputRj&action=TAMBAH&id=$id'>";
                                break;
                            case "UBAH":
                                Ubah(" rawatjalan ","jmlh='$jmlh',jm='$ttljm' WHERE id ='".valid($_GET['id'])."' and tgl ='".valid($_GET['tgl'])."' and tnd ='".valid($_GET['tnd'])."' ", " rawat jalan ");
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputRj&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else{
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 59%; overflow: auto;">
            <?php
                $_sql   = "select rawatjalan.tgl,rawatjalan.id,rawatjalan.tnd,master_tindakan.nama,rawatjalan.jm,rawatjalan.nm_pasien,rawatjalan.kamar,rawatjalan.diagnosa,rawatjalan.jmlh 
                           from rawatjalan inner join master_tindakan on rawatjalan.tnd=master_tindakan.id where rawatjalan.id='$id' and rawatjalan.tgl like '%".$tahun."-".$bulan."%' ORDER BY rawatjalan.tgl ASC";
                $hasil  = bukaquery($_sql);
                $jumlah = mysqli_num_rows($hasil);
                $ttljm  = 0;
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='40%'><div align='center'>Nama Tindakan</div></td>
                                <td width='24%'><div align='center'>JM Tindakan</div></td>
                                <td width='24%'><div align='center'>Jml.Tindakan</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        $ttljm=$ttljm+$baris[4];
                        echo "<tr class='isi'>
                                <td>
                                    <center>
                                       <a href=?act=InputRj&action=UBAH&&tgl=".substr($baris[0],0,10)."&id=".$baris[1]."&tnd=".$baris[2]."&jmlh=".$baris[8].">[edit]</a>
                                       <a href=?act=InputRj&action=HAPUS&tgl=".substr($baris[0],0,10)."&tnd=".$baris[2]."&id=".$baris[1]." >[hapus]</a>
                                    </center>
                                </td>
                                <td>$baris[3]</td>
                                <td>".formatDuit($baris[4])."</td>
                                <td>$baris[8]</td>
                           </tr>";
                    }
                    echo "</table>";
                } else {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='40%'><div align='center'>Nama Tindakan</div></td>
                                <td width='24%'><div align='center'>JM Tindakan</div></td>
                                <td width='24%'><div align='center'>Jml.Tindakan</div></td>
                            </tr>
                        </table>";
                }
            ?>
            </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" rawatjalan "," id ='".validTeks($_GET['id'])."' and tgl ='".validTeks($_GET['tgl'])."' and tnd ='".validTeks($_GET['tnd'])."'","?act=InputRj&action=TAMBAH&id=$id");
            }
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, Ttl.JM : ".formatDuit($ttljm)." <a target=_blank href=../penggajian/pages/rawat/laporandetailrj.php?&id=$id>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
        ?>
    </div>
</div>
