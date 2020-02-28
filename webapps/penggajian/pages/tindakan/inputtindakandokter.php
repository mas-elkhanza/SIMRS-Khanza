
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
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                $id                 =isset($_GET['id'])?$_GET['id']:NULL;
                $TglPres            =isset($_GET['TglPres'])?$_GET['TglPres']:date('d');
                $JamDatang          =isset($_GET['JamDatang'])?$_GET['JamDatang']:"00";
                $MenitDatang        =isset($_GET['MenitDatang'])?$_GET['MenitDatang']:"00";
                $DetikDatang        =isset($_GET['DetikDatang'])?$_GET['DetikDatang']:"00";
                $tgl                =$tahun."-".$bulan."-".$TglPres." ".$JamDatang.":".$MenitDatang.":".$DetikDatang;
                $tnd                =isset($_GET['tnd'])?$_GET['tnd']:NULL;
                $jm                 =isset($_GET['jm'])?$_GET['jm']:NULL;
                $nm_pasien          =isset($_GET['nm_pasien'])?$_GET['nm_pasien']:NULL;
                $kamar              =isset($_GET['kamar'])?$_GET['kamar']:NULL;
                $diagnosa           =isset($_GET['diagnosa'])?$_GET['diagnosa']:NULL;
                $jmlh               =isset($_GET['jmlh'])?$_GET['jmlh']:NULL;
                echo "<input type=hidden name=id  value=$id><input type=hidden name=tgl value=$tgl><input type=hidden name=action value=$action>";
		        $_sql = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil=bukaquery($_sql);
                $baris = mysqli_fetch_row($hasil);

                $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' and pegawai.jbtn like '%dokter umum%' order by id asc limit 1";
                    $hasilnext        	= bukaquery($_sqlnext);
                    $barisnext        	= mysqli_fetch_row($hasilnext);
                    $next               = $barisnext[0];

                    $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' and pegawai.jbtn like '%dokter umum%' order by id desc limit 1";
                    $hasilprev        	= bukaquery($_sqlprev);
                    $barisprev        	= mysqli_fetch_row($hasilprev);
                    $prev               = $barisprev[0];
                    
                    if(empty($next)){
                        $next=$prev;
                    }
                    
                    if(empty($prev)){
                        $prev=$next;
                    }

                    echo "<div align='center' class='link'>
                          <a href=?act=InputTindakanDokter&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListTindakanDokter>| Tindakan Dokter |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputTindakanDokter&action=TAMBAH&id=$next>-->></a>
                          </div>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >NIP</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[0];?></td>
                </tr>
				<tr class="head">
                    <td width="31%">Nama</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[1];?></td>
                </tr>                
		<tr class="head">
                    <td width="25%" >Tindakan</td><td width="">:</td>
                    <td width="75%">
                        <select name="tnd" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <?php
                                $_sql = "SELECT id,nama FROM master_tindakan where jns='dr Umum' ORDER BY nama";
                                $hasil=bukaquery($_sql);
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi1' value='$baris[0]'>$baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>                
                <tr class="head">
                    <td width="31%" >Jumlah Tindakan</td><td width="">:</td>
                    <td width="67%"><input name="jmlh" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $jmlh;?>" size="10" maxlength="10" />
                    
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 =trim(isset($_POST['id']))?trim($_POST['id']):NULL;
                    $JamDatang          =isset($_POST['JamDatang'])?$_POST['JamDatang']:"00";
                    $MenitDatang        =isset($_POST['MenitDatang'])?$_POST['MenitDatang']:"00";
                    $DetikDatang        =isset($_POST['DetikDatang'])?$_POST['DetikDatang']:"00";
                    $tgl                =$tahun."-".$bulan."-01 ".$JamDatang.":".$MenitDatang.":".$DetikDatang;
                    $tnd                =trim(isset($_POST['tnd']))?trim($_POST['tnd']):NULL;
                    $tnd                =validTeks($tnd);
                    $_sql = "SELECT jm FROM master_tindakan where id='$tnd'";
                    $hasil=bukaquery($_sql);
                    $baris = mysqli_fetch_array($hasil);
                    $jm                 =$baris[0];
                    $nm_pasien          =trim(isset($_POST['nm_pasien']))?trim($_POST['nm_pasien']):NULL;
                    $nm_pasien          =validTeks($nm_pasien);
                    $kamar              =trim(isset($_POST['kamar']))?trim($_POST['kamar']):NULL;
                    $kamar              =validTeks($kamar);
                    $diagnosa           =trim(isset($_POST['diagnosa']))?trim($_POST['diagnosa']):NULL;
                    $diagnosa           =validTeks($diagnosa);
                    $jmlh               =trim(isset($_POST['jmlh']))?trim($_POST['jmlh']):NULL;
                    $jmlh               =validangka($jmlh);
                    $ttljm              =$jm*$jmlh;
                    if ((!empty($id))&&(!empty($tgl))&&(!empty($tnd))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" tindakan "," '$tgl','$id','$tnd','$ttljm','-',
                                        '-','-','$jmlh'", " detail tindakan " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputTindakanDokter&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($tgl))||(empty($tnd))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 59%; overflow: auto;">
            <?php
                
                $_sql = "select tindakan.tgl,
                        tindakan.id,
                        tindakan.tnd,
                        master_tindakan.nama,
                        tindakan.jm,
                        tindakan.nm_pasien,
                        tindakan.kamar,
                        tindakan.diagnosa,
                        tindakan.jmlh
                        from tindakan inner join master_tindakan
                        where tindakan.tnd=master_tindakan.id and tindakan.id='$id'
			and tgl like '%".$tahun."-".$bulan."%' ORDER BY tgl ASC";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttljm=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='7%'><div align='center'>Proses</div></td>
                                <td width='43%'><div align='center'>Nama Tindakan</div></td>
                                <td width='25%'><div align='center'>JM Tindakan</div></td>
                                <td width='25%'><div align='center'>Jml.Tindakan</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        $ttljm=$ttljm+$baris[4];
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=InputTindakanDokter&action=HAPUS&tgl=<?php print $baris[0] ?>&tnd=<?php print $baris[2] ?>&id=<?php print $baris[1] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[3]</td>
                                <td>".formatDuit($baris[4])."</td>
                                <td>$baris[8]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data Detail Tindakan Dokter masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" tindakan "," id ='".$_GET['id']."' and tgl ='".$_GET['tgl']."' and tnd ='".$_GET['tnd']."'","?act=InputTindakanDokter&action=TAMBAH&id=$id");
            }

                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, Ttl.JM : ".formatDuit($ttljm)." <a target=_blank href=../penggajian/pages/tindakan/laporandetailtindakandokter.php?&id=$id>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
        ?>
    </div>

</div>

