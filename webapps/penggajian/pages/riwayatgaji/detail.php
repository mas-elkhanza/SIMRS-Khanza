
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysql_fetch_row($hasil);
   $tahun        = $baris[0];
   $bulan        = $baris[1];

?>
<div id="post">
    <div class="entry">        
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                $id                 =isset($_GET['id'])?$_GET['id']:NULL;
                $gapok              =isset($_GET['gapok'])?$_GET['gapok']:NULL;
                echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
		$_sql = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil=bukaquery($_sql);
                $baris = mysql_fetch_row($hasil);   

                 $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                    $hasilnext        	= bukaquery($_sqlnext);
                    $barisnext        	= mysql_fetch_row($hasilnext);
                    $next               = $barisnext[0];

                    $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                    $hasilprev        	= bukaquery($_sqlprev);
                    $barisprev        	= mysql_fetch_row($hasilprev);
                    $prev               = $barisprev[0];
                    
                    if(empty($prev)){
                        $prev=$next;
                    }
                    
                    if(empty($next)){
                        $next=$prev;
                    }

                    echo "<div align='center' class='link'>
                          <a href=?act=InputRiwayatGaji&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListRiwayatGaji&action=LIHAT>| List Riwayat Gaji |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputRiwayatGaji&action=TAMBAH&id=$next>-->></a>
                          </div>";
            ?>
            <div style="width: 100%; height: 40%; overflow: auto;">
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
                    <td width="31%" >Jabatan/Pangkat</td><td width="">:</td>
                    <td width="67%"><input name="jabatan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo isset($jabatan)?$jabatan:NULL;?>" size="50" maxlength="50">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Gaji Pokok Baru</td><td width="">:</td>
                    <td width="67%"><input name="gapok" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($gapok)?$gapok:NULL;?>" size="20" maxlength="20">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >TMT Berkala</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglBerkala" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi3' value=$TglBerkala>$TglBerkala</option>";
                                }
                                loadTgl();
                             ?>
                        </select>
			<select name="BlnBerkala" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi3' value=$BlnBerkala>$BlnBerkala</option>";
                                }
                                loadBln();
                             ?>
                        </select>
			<select name="ThnBerkala" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi3' value=$ThnBerkala>$ThnBerkala</option>";
                                }
                                loadThn();
                             ?>
                        </select>
                        <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >TMT Berkala YAD</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglBerkalaYad" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi4' value=$TglBerkalaYad>$TglBerkalaYad</option>";
                                }
                                loadTgl();
                             ?>
                        </select>
			<select name="BlnBerkalaYad" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi4' value=$BlnBerkalaYad>$BlnBerkalaYad</option>";
                                }
                                loadBln();
                             ?>
                        </select>
			<select name="ThnBerkalaYad" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi4' value=$ThnBerkalaYad>$ThnBerkalaYad</option>";
                                }
                                loadThn();
                             ?>
                        </select>
                        <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>                
                <tr class="head">
                    <td width="31%" >Nomor S.K.</td><td width="">:</td>
                    <td width="67%"><input name="no_sk" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" type=text id="TxtIsi5" class="inputbox" value="<?php echo isset($no_sk)?$no_sk:NULL;?>" size="25" maxlength="25">
                    <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Tanggal S.K.</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglSK" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi6' value=$TglSK>$TglSK</option>";
                                }
                                loadTgl();
                             ?>
                        </select>
			<select name="BlnSK" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi6' value=$BlnSK>$BlnSK</option>";
                                }
                                loadBln();
                             ?>
                        </select>
			<select name="ThnSK" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi6' value=$ThnSK>$ThnSK</option>";
                                }
                                loadThn();
                             ?>
                        </select>
                        <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr> 
                <tr class="head">
                    <td width="31%" >Masa Kerja</td><td width="">:</td>
                    <td width="67%">
                        <input name="masa_kerja" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" type=text id="TxtIsi7" class="inputbox" value="<?php echo isset($masa_kerja)?$masa_kerja:NULL;?>" size="10" maxlength="5">Tahun
                        <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                        <input name="bulan_kerja" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" type=text id="TxtIsi8" class="inputbox" value="<?php echo isset($bulan_kerja)?$bulan_kerja:NULL;?>" size="5" maxlength="2">Bulan
                        <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr> 
            </table>
            </div>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 =trim($_POST['id']);
                    $jabatan            =trim($_POST['jabatan']);                    
                    $gapok              =trim($_POST['gapok']);
                    $tmt_berkala        =trim($_POST['ThnBerkala'])."-".trim($_POST['BlnBerkala'])."-".trim($_POST['TglBerkala']);
                    $tmt_berkala_yad    =trim($_POST['ThnBerkalaYad'])."-".trim($_POST['BlnBerkalaYad'])."-".trim($_POST['TglBerkalaYad']);
                    $no_sk           =trim($_POST['no_sk']);
                    $tgl_sk             =trim($_POST['ThnSK'])."-".trim($_POST['BlnSK'])."-".trim($_POST['TglSK']);
                    $masa_kerja         =trim($_POST['masa_kerja']);
                    $bulan_kerja          =trim($_POST['bulan_kerja']);
                    if ((!empty($id))&&(!empty($gapok))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" riwayat_naik_gaji "," '$id','$jabatan','$gapok','$tmt_berkala','$tmt_berkala_yad',
                                        '$no_sk','$tgl_sk','$masa_kerja','$bulan_kerja'", " Riwayat Berkala " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputRiwayatGaji&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($gapok))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 42%; overflow: auto;">
            <?php
                $_sql = "SELECT pangkatjabatan,gapok,tmt_berkala,tmt_berkala_yad,no_sk,tgl_sk,masa_kerja,bulan_kerja 
                        from riwayat_naik_gaji where id='$id' ORDER BY tmt_berkala ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysql_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysql_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='30%'><div align='center'>Pangkat/Jabatan</div></td>
                                <td width='12%'><div align='center'>Gapok</div></td>
                                <td width='10%'><div align='center'>TMT Berkala</div></td>
                                <td width='10%'><div align='center'>TMT Berkala YAD</div></td>
                                <td width='13%'><div align='center'>Nomor S.K.</div></td>
                                <td width='10%'><div align='center'>Tanggal S.K.</div></td>
                                <td width='10%'><div align='center'>Masa Kerja</div></td>
                            </tr>";
                    while($baris = mysql_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=InputRiwayatGaji&action=HAPUS&gapok=<?php print $baris[1] ?>&id=<?php echo $id ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[0]</td>
                                <td align='right'>".formatDuit($baris[1])."</td>
                                <td>$baris[2]</td>
                                <td>$baris[3]</td>
                                <td>$baris[4]</td>
                                <td>$baris[5]</td>
                                <td>$baris[6] Tahun $baris[7] Bulan</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data riwayat gaji masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" riwayat_naik_gaji "," id ='".$_GET['id']."' and gapok ='".$_GET['gapok']."'","?act=InputRiwayatGaji&action=TAMBAH&id=$id");
            }
        
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        
        ?>
    </div>

</div>
