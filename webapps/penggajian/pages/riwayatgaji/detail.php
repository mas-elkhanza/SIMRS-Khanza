<div id="entry">       
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            echo "";
            $action             =isset($_GET['action'])?$_GET['action']:NULL;
            $id                 =isset($_GET['id'])?$_GET['id']:NULL;
            $gapok              =isset($_GET['gapok'])?$_GET['gapok']:NULL;
            echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
            $_sql = "SELECT nik,nama FROM pegawai where id='$id'";
            $hasil=bukaquery($_sql);
            $baris = mysqli_fetch_row($hasil);   

            $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
            $hasilnext        	= bukaquery($_sqlnext);
            $barisnext        	= mysqli_fetch_row($hasilnext);
            $next               = $barisnext[0];

            $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
            $hasilprev        	= bukaquery($_sqlprev);
            $barisprev        	= mysqli_fetch_row($hasilprev);
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
                  <a href=?act=InputRiwayatGaji&action=TAMBAH&id=$next>-->></a>
                  </div>";
        ?>
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" class="tbl_form">
            <tr class="isi2">
                <td width="17%" >NIP</td><td width="">:</td>
                <td width="31%"><?php echo $baris[0];?></td>
                <td width="17%" >TMT Berkala YAD</td><td width="">:</td>
                <td width="31%">
                    <select name="TglBerkalaYad" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                         <?php
                            loadTglnow();
                         ?>
                    </select>
                    <select name="BlnBerkalaYad" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                         <?php
                            loadBlnnow();
                         ?>
                    </select>
                    <select name="ThnBerkalaYad" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%">Nama</td><td width="">:</td>
                <td width="31%"><?php echo $baris[1];?></td>
                <td width="17%" >Nomor S.K.</td><td width="">:</td>
                <td width="31%"><input name="no_sk" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" type=text id="TxtIsi5" class="inputbox" value="<?php echo isset($no_sk)?$no_sk:NULL;?>" size="25" maxlength="25">
                <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Jabatan/Pangkat</td><td width="">:</td>
                <td width="31%"><input name="jabatan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo isset($jabatan)?$jabatan:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Tanggal S.K.</td><td width="">:</td>
                <td width="31%">
                    <select name="TglSK" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                         <?php
                            loadTglnow();
                         ?>
                    </select>
                    <select name="BlnSK" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                         <?php
                            loadBlnnow();
                         ?>
                    </select>
                    <select name="ThnSK" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Gaji Pokok Baru</td><td width="">:</td>
                <td width="31%"><input name="gapok" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($gapok)?$gapok:NULL;?>" size="20" maxlength="20">
                <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Masa Kerja</td><td width="">:</td>
                <td width="31%">
                    <input name="masa_kerja" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" type=text id="TxtIsi7" class="inputbox" value="<?php echo isset($masa_kerja)?$masa_kerja:NULL;?>" size="10" maxlength="5">Tahun
                    <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                    <input name="bulan_kerja" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" type=text id="TxtIsi8" class="inputbox" value="<?php echo isset($bulan_kerja)?$bulan_kerja:NULL;?>" size="5" maxlength="2">Bulan
                    <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >TMT Berkala</td><td width="">:</td>
                <td width="31%">
                    <select name="TglBerkala" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                         <?php
                            loadTglnow();
                         ?>
                    </select>
                    <select name="BlnBerkala" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                         <?php
                            loadBlnnow();
                         ?>
                    </select>
                    <select name="ThnBerkala" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Berkas Naik Gaji</td><td width="">:</td>
                <td width="31%">
                    <input name="dokumen" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" type=file id="TxtIsi9" value="<?php echo $dokumen;?>" size="40" maxlength="255" />
                    <span id="MsgIsi9" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>            
        </table>
        <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;SIMPAN&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;KOSONG&nbsp;&nbsp;"></div><br>
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
                $dokumen            = str_replace(" ","_","pages/riwayatgaji/berkas/".$_FILES['dokumen']['name']);
                if ((!empty($id))&&(!empty($gapok))) {
                    switch($action) {
                        case "TAMBAH":
                            if((strtolower(substr($dokumen,-3))=="jpg")||(strtolower(substr($dokumen,-3))=="jpeg")){
                                move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                                Tambah(" riwayat_naik_gaji "," '$id','$jabatan','$gapok','$tmt_berkala','$tmt_berkala_yad',
                                        '$no_sk','$tgl_sk','$masa_kerja','$bulan_kerja','$dokumen'", " Riwayat Gaji Berkala " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputRiwayatGaji&action=TAMBAH&id=$id'>";
                            }else{
                                echo "Berkas harus JPEG/JPG";
                            } 
                            break;
                    }
                }else if ((empty($id))||(empty($gapok))){
                    echo 'Semua field harus isi..!!!';
                }
            }
            
            $_sql = "SELECT pangkatjabatan,gapok,tmt_berkala,tmt_berkala_yad,no_sk,tgl_sk,masa_kerja,bulan_kerja, 
                    berkas from riwayat_naik_gaji where id='$id' ORDER BY tmt_berkala ASC ";
            $hasil=bukaquery($_sql);
            $jumlah=mysqli_num_rows($hasil);
            $ttllembur=0;
            $ttlhr=0;

            if(mysqli_num_rows($hasil)!=0) {
                echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
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
                while($baris = mysqli_fetch_array($hasil)) {  
                    $gb="-";
                    if($baris["berkas"]=="pages/riwayatgaji/berkas"){
                        $gb="-";                            
                    }else{
                        $gb="<img src='".$baris["berkas"]."' width='850px' height='950px'>";
                    }
                  echo "<tr class='isi'>
                            <td width='70'>
                                <center>"; ?>
                                <a href="?act=InputRiwayatGaji&action=HAPUS&gapok=<?php print $baris[1] ?>&id=<?php echo $id ?>&berkas=<?php print $baris["berkas"];?>" >[hapus]</a>
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
                        echo "<tr class='isi'>
                            <td width='70'></td>
                            <td valign='top' align='center' colspan='9'><a target=_blank href=../penggajian/".$baris["berkas"].">".$gb."</a></td>
                       </tr>";    
                }
            echo "</table>";

        } else {
            echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head3'>
                            <td width='5%'><div align='center'>Proses</div></td>
                            <td width='30%'><div align='center'>Pangkat/Jabatan</div></td>
                            <td width='12%'><div align='center'>Gapok</div></td>
                            <td width='10%'><div align='center'>TMT Berkala</div></td>
                            <td width='10%'><div align='center'>TMT Berkala YAD</div></td>
                            <td width='13%'><div align='center'>Nomor S.K.</div></td>
                            <td width='10%'><div align='center'>Tanggal S.K.</div></td>
                            <td width='10%'><div align='center'>Masa Kerja</div></td>
                        </tr>
                    </table>";
        }
    ?>
    </form>
    <?php
        if ($action=="HAPUS") {
            unlink($_GET['berkas']);
            Hapus(" riwayat_naik_gaji "," id ='".$_GET['id']."' and gapok ='".$_GET['gapok']."'","?act=InputRiwayatGaji&action=TAMBAH&id=$id");
        }       
    ?>
</div>
