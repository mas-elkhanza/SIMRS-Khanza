
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
                $nm_pasien          =str_replace("_"," ",isset($_GET['nm_pasien']))?str_replace("_"," ",$_GET['nm_pasien']):NULL;
                $kamar              =isset($_GET['kamar'])?$_GET['kamar']:NULL;
                $diagnosa           =isset($_GET['diagnosa'])?$_GET['diagnosa']:NULL;
                $jmlh               =isset($_GET['jmlh'])?$_GET['jmlh']:NULL;
                $TglPres            =substr(isset($_GET['tgl']),8,2)?substr($_GET['tgl'],8,2):NULL;
                echo "
                     <input type=hidden name=action value=$action>
                     <input type=hidden name=tgl value=$tgl>
                    <input type=hidden name=tnd value=$tnd>
                   <input type=hidden name=nm_pasien value=$nm_pasien>
                     <input type=hidden name=id  value=$id>";
                     
		        $_sql = "SELECT nik,nama FROM pegawai where id='$id'";
                   //     <input type=hidden name=id  value=$id>
                    // <input type=hidden name=tgl value=$tgl>
                   // <input type=hidden name=tnd value=$tnd>
                   // <input type=hidden name=nm_pasien value=$nm_pasien>
                $hasil=bukaquery($_sql);
                $baris = mysqli_fetch_row($hasil);

                 $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' and pegawai.jbtn like '%dokter spesialis%' order by id asc limit 1";
                    $hasilnext        	= bukaquery($_sqlnext);
                    $barisnext        	= mysqli_fetch_row($hasilnext);
                    $next               = $barisnext[0];

                    $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' and pegawai.jbtn like '%dokter spesialis%' order by id desc limit 1";
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
                          <a href=?act=InputTindakanSpesialis&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListTindakanSpesialis>| Tindakan Spesialis |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputTindakanSpesialis&action=TAMBAH&id=$next>-->></a>
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
                    <td width="31%" >Tanggal</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglPres" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                    if($action == "UBAH"){
                                        echo "<option id='TxtIsi13' value=$TglPres>$TglPres</option>";
                                    }
                                    loadTgl2();
                             ?>
                        </select>
                        Harus diisi!!
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="25%" >Tindakan</td><td width="">:</td>
                    <td width="75%">
                        <select name="tnd" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                            <?php
                                $_sql = "SELECT id,nama FROM master_tindakan where jns='dr Spesialis' ORDER BY nama";
                                $hasil=bukaquery($_sql);
                                
                                
                                if($action == "UBAH"){
                                    $_sql2 = "SELECT id,nama FROM master_tindakan where id='$tnd'";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi2' value='$baris2[0]'>$baris2[1]</option>";
                                    }
                                }
                                
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi2' value='$baris[0]'>$baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Nama Pasien</td><td width="">:</td>
                    <td width="67%"><input name="nm_pasien" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $nm_pasien;?>" size="35" maxlength="30" />
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jumlah Tindakan</td><td width="">:</td>
                    <td width="67%"><input name="jmlh" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo $jmlh;?>" size="10" maxlength="10" />  
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
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
                    $TglPres            =isset($_POST['TglPres'])?$_POST['TglPres']:date('d');
                    $tgl                =$tahun."-".$bulan."-".$TglPres." ".$JamDatang.":".$MenitDatang.":".$DetikDatang;
                    $tnd                =trim(isset($_POST['tnd']))?trim($_POST['tnd']):NULL;
                    $tnd                =validTeks($tnd);
                    $_sql = "SELECT jm FROM master_tindakan where id='$tnd'";
                    $hasil=bukaquery($_sql);
                    $baris = mysqli_fetch_array($hasil);
                    $jm                 =$baris[0];
                    $nm_pasien          =trim(isset($_POST['nm_pasien']))?trim($_POST['nm_pasien']):NULL;
                    $nm_pasien          =validTeks($nm_pasien);
                    $kamar              ="-";
                    $diagnosa           =trim(isset($_POST['diagnosa']))?trim($_POST['diagnosa']):NULL;
                    $diagnosa           =validTeks($diagnosa);
                    $jmlh               =trim(isset($_POST['jmlh']))?trim($_POST['jmlh']):NULL;
                    $jmlh               =validangka($jmlh);
                    $ttljm              =$jm*$jmlh;
                    if ((!empty($id))&&(!empty($tgl))&&(!empty($tnd))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" tindakan "," '$tgl','$id','$tnd','$ttljm','$nm_pasien',
                                        '$kamar','-','$jmlh'", " detail tindakan " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputTindakanSpesialis&action=TAMBAH&id=$id'>";
                                break;
                            case "UBAH":
                                Ubah(" tindakan ","jmlh='$jmlh',jm='$ttljm' WHERE id ='".$_GET['id']."' and tgl ='".$_GET['tgl']."' and tnd ='".$_GET['tnd']."' and nm_pasien ='".$_GET['nm_pasien']."' ", " detail tindakan ");
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputTindakanSpesialis&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($tgl))||(empty($tnd))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 47%; overflow: auto;">
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
			and tgl like '%".$tahun."-".$bulan."%' ORDER BY tindakan.nm_pasien,tindakan.tgl ASC";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                
		    $ttljms=0;
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='7%'><div align='center'>Proses</div></td>
                                <td width='10%'><div align='center'>Tgl.Tindakan</div></td>
                                <td width='33%'><div align='center'>Nama Pasien</div></td>
                                <td width='25%'><div align='center'>Nama Tindakan-Kelas</div></td>
                                <td width='25%'><div align='center'>JM Tindakan</div></td>
                            </tr>";
			  $ttljms=0;
                    while($baris = mysqli_fetch_array($hasil)) {
                        $ttljms=$ttljms+$baris[4];
                      echo "<tr class='isi' title='$baris[3], $baris[4], $baris[5]'>
                                <td width='70'>
                                    <center>
                                    <a href=?act=InputTindakanSpesialis&action=UBAH&nm_pasien=".str_replace(" ","_",$baris[5])."&tgl=".substr($baris[0],0,10)."&id=".$baris[1]."&tnd=".$baris[2]."&jmlh=".$baris[8].">[edit]</a>";?>
                                    <a href="?act=InputTindakanSpesialis&action=HAPUS&nm_pasien=<?php print str_replace(" ","_",$baris[5])?>&tgl=<?php print $baris[0] ?>&tnd=<?php print $baris[2] ?>&id=<?php print $baris[1] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>".substr($baris[0],0,10)."</td>
                                <td>$baris[5]</td>
                                <td>$baris[3]</td>
                                <td>".formatDuit($baris[4])."</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data Detail masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" tindakan "," id ='".$_GET['id']."' and tgl ='".$_GET['tgl']."' and tnd ='".$_GET['tnd']."' and nm_pasien ='".str_replace("_"," ",$_GET['nm_pasien'])."'","?act=InputTindakanSpesialis&action=TAMBAH&id=$id");
            }

                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, Ttl.JM : ".formatDuit($ttljms)." <a target=_blank href=../penggajian/pages/tindakan/laporandetailtindakandokter.php?&id=$id>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
                
        ?>

</div>
