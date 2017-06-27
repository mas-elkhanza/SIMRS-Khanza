
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
                $nama_seminar       =isset($_GET['nama_seminar'])?$_GET['nama_seminar']:NULL;
                $mulai              =isset($_GET['mulai'])?$_GET['mulai']:NULL;
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
                          <a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListRiwayatSeminar&action=LIHAT>| List Riwayat Pelatihan |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$next>-->></a>
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
                    <td width="31%" >Level</td><td width="">:</td>
                    <td width="67%">
                        <select name="tingkat" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <option id='TxtIsi1' value='Local'>Local</option>
                            <option id='TxtIsi1' value='Regional'>Regional</option>
                            <option id='TxtIsi1' value='Nasional'>Nasional</option>
                            <option id='TxtIsi1' value='Internasional'>Internasional</option>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jenis Kegiatan</td><td width="">:</td>
                    <td width="67%">
                        <select name="jenis" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                            <option id='TxtIsi2' value='WORKSHOP'>WORKSHOP</option>
                            <option id='TxtIsi2' value='SIMPOSIUM'>SIMPOSIUM</option>
                            <option id='TxtIsi2' value='SEMINAR'>SEMINAR</option>
                            <option id='TxtIsi2' value='FGD'>FGD</option>
                            <option id='TxtIsi2' value='PELATIHAN'>PELATIHAN</option>
                            <option id='TxtIsi2' value='LAINNYA'>LAINNYA</option>
                        </select>
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Nama Kegiatan</td><td width="">:</td>
                    <td width="67%"><input name="nama_seminar" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo isset($nama_seminar)?$nama_seminar:NULL;?>" size="50" maxlength="50">
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>  
                <tr class="head">
                    <td width="31%" >Peranan</td><td width="">:</td>
                    <td width="67%"><input name="peranan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo isset($peranan)?$peranan:NULL;?>" size="50" maxlength="40">
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Tanggal Mulai</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglMulai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi5' value=$TglMulai>$TglMulai</option>";
                                }
                                loadTgl();
                             ?>
                        </select>
			<select name="BlnMulai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi5' value=$BlnMulai>$BlnMulai</option>";
                                }
                                loadBln();
                             ?>
                        </select>
			<select name="ThnMulai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi5' value=$ThnMulai>$ThnMulai</option>";
                                }
                                loadThn();
                             ?>
                        </select>
                        <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Sampai Dengan</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglSelesai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi6' value=$TglSelesai>$TglSelesai</option>";
                                }
                                loadTgl();
                             ?>
                        </select>
			<select name="BlnSelesai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi6' value=$BlnSelesai>$BlnSelesai</option>";
                                }
                                loadBln();
                             ?>
                        </select>
			<select name="ThnSelesai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi6' value=$ThnSelesai>$ThnSelesai</option>";
                                }
                                loadThn();
                             ?>
                        </select>
                        <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>                
                <tr class="head">
                    <td width="31%" >Penyelenggara</td><td width="">:</td>
                    <td width="67%"><input name="penyelengara" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" type=text id="TxtIsi7" class="inputbox" value="<?php echo isset($penyelengara)?$penyelengara:NULL;?>" size="50" maxlength="50">
                    <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>             
                <tr class="head">
                    <td width="31%" >Tempat</td><td width="">:</td>
                    <td width="67%"><input name="tempat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" type=text id="TxtIsi8" class="inputbox" value="<?php echo isset($tempat)?$tempat:NULL;?>" size="50" maxlength="50">
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
                    $tingkat            =trim($_POST['tingkat']);
                    $jenis              =trim($_POST['jenis']);
                    $nama_seminar       =trim($_POST['nama_seminar']);
                    $peranan            =trim($_POST['peranan']);
                    $mulai              =trim($_POST['ThnMulai'])."-".trim($_POST['BlnMulai'])."-".trim($_POST['TglMulai']);
                    $selesai            =trim($_POST['ThnSelesai'])."-".trim($_POST['BlnSelesai'])."-".trim($_POST['TglSelesai']);
                    $penyelengara       =trim($_POST['penyelengara']);
                    $tempat             =trim($_POST['tempat']);
                    
                    if ((!empty($id))&&(!empty($nama_seminar))&&(!empty($mulai))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" riwayat_seminar "," '$id','$tingkat','$jenis','$nama_seminar','$peranan','$mulai','$selesai','$penyelengara','$tempat'", " Riwayat Kegiatan Ilmiah " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputRiwayatSeminar&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($jabatan))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 42%; overflow: auto;">
            <?php
                $_sql = "SELECT tingkat, jenis, nama_seminar, peranan, mulai, selesai, penyelengara, tempat
                        from riwayat_seminar where id='$id' ORDER BY mulai ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysql_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysql_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='10%'><div align='center'>Level</div></td>
                                <td width='10%'><div align='center'>Jenis Kegiatan</div></td>
                                <td width='25%'><div align='center'>Nama Kegiatan</div></td>
                                <td width='10%'><div align='center'>Peranan</div></td>
                                <td width='5%'><div align='center'>Mulai</div></td>
                                <td width='5%'><div align='center'>Selesai</div></td>
                                <td width='15%'><div align='center'>Penyelanggara</div></td>
                                <td width='15%'><div align='center'>Tempat</div></td>
                            </tr>";
                    while($baris = mysql_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=InputRiwayatSeminar&action=HAPUS&nama_seminar=<?php print $baris[2] ?>&mulai=<?php print $baris[4] ?>&id=<?php echo $id ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>
                                <td>$baris[3]</td>
                                <td>$baris[4]</td>
                                <td>$baris[5]</td>
                                <td>$baris[6]</td>
                                <td>$baris[7]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data riwayat kegiatan ilmiah masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" riwayat_seminar "," id ='".$_GET['id']."' and mulai ='".$_GET['mulai']."' and nama_seminar ='".$_GET['nama_seminar']."'","?act=InputRiwayatSeminar&action=TAMBAH&id=$id");
            }

        
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        
        ?>
    </div>

</div>
