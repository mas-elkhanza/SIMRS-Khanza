<div id="entry">        
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            echo "";
            $action             =isset($_GET['action'])?$_GET['action']:NULL;
            $id                 =isset($_GET['id'])?$_GET['id']:NULL;
            $jabatan            =isset($_GET['jabatan'])?$_GET['jabatan']:NULL;
            echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
            $_sql = "SELECT nik,nama FROM pegawai where id='$id'";
            $hasil=bukaquery($_sql);
            $baris = mysqli_fetch_row($hasil);   

             $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                $hasilnext        	= bukaquery($_sqlnext);
                $barisnext        	= mysqli_fetch_row($hasilnext);
                @$next               = $barisnext[0];

                $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                $hasilprev        	= bukaquery($_sqlprev);
                $barisprev        	= mysqli_fetch_row($hasilprev);
                @$prev               = $barisprev[0];

                if(empty($prev)){
                    $prev=$next;
                }

                if(empty($next)){
                    $next=$prev;
                }

                echo "<div align='center' class='link'>
                      <a href=?act=InputRiwayatPangkat&action=TAMBAH&id=$prev><<--</a>
                      <a href=?act=ListRiwayatPangkat&action=LIHAT>| List Riwayat Pangkat |</a>
                      <a href=?act=InputRiwayatPangkat&action=TAMBAH&id=$next>-->></a>
                      </div>";
        ?>            
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" class="tbl_form">
            <tr class="isi2">
                <td width="17%" >NIP</td><td width="">:</td>
                <td width="31%"><?php echo $baris[0];?></td>
                <td width="17%" >Nomor S.K.</td><td width="">:</td>
                <td width="31%"><input name="nomor_sk" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" type=text id="TxtIsi5" class="inputbox" value="<?php echo isset($nomor_sk)?$nomor_sk:NULL;?>" size="25" maxlength="25">
                <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%">Nama</td><td width="">:</td>
                <td width="31%"><?php echo $baris[1];?></td>
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
                <td width="17%" >Jabatan</td><td width="">:</td>
                <td width="31%"><input name="jabatan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo isset($jabatan)?$jabatan:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Dasar Peraturan</td><td width="">:</td>
                <td width="31%"><input name="dasar_peraturan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" type=text id="TxtIsi7" class="inputbox" value="<?php echo isset($dasar_peraturan)?$dasar_peraturan:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >TMT Jabatan</td><td width="">:</td>
                <td width="31%">
                    <select name="TglJabatan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                         <?php
                            loadTglnow();
                         ?>
                    </select>
                    <select name="BlnJabatan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                         <?php
                            loadBlnnow();
                         ?>
                    </select>
                    <select name="ThnJabatan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Masa Kerja</td><td width="">:</td>
                <td width="31%">
                    <input name="masa_kerja" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" type=text id="TxtIsi8" class="inputbox" value="<?php echo isset($masa_kerja)?$masa_kerja:NULL;?>" size="10" maxlength="5">Tahun
                    <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                    <input name="bln_kerja" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" type=text id="TxtIsi9" class="inputbox" value="<?php echo isset($bln_kerja)?$bln_kerja:NULL;?>" size="5" maxlength="2">Bulan
                    <span id="MsgIsi9" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >TMT Jabatan YAD</td><td width="">:</td>
                <td width="31%">
                    <select name="TglJabatanYad" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                         <?php
                            loadTglnow();
                         ?>
                    </select>
                    <select name="BlnJabatanYad" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                         <?php
                            loadBlnnow();
                         ?>
                    </select>
                    <select name="ThnJabatanYad" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Berkas Pengangkatan</td><td width="">:</td>
                <td width="31%">
                    <input name="dokumen" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi10'));" type=file id="TxtIsi10" value="<?php echo $dokumen;?>" size="40" maxlength="255" />
                    <span id="MsgIsi10" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>                
            <tr class="isi2">
                <td width="17%" >Pejabat Penetap</td><td width="">:</td>
                <td width="31%" colspan="4"><input name="pejabat_penetap" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo isset($pejabat_penetap)?$pejabat_penetap:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
        </table>           
        <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;SIMPAN&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;KOSONG&nbsp;&nbsp;"></div><br>
        <?php
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $id                 =trim($_POST['id']);
                $jabatan            =validTeks(trim($_POST['jabatan']));
                $tmt_pangkat        =trim($_POST['ThnJabatan'])."-".trim($_POST['BlnJabatan'])."-".trim($_POST['TglJabatan']);
                $tmt_pangkat_yad    =trim($_POST['ThnJabatanYad'])."-".trim($_POST['BlnJabatanYad'])."-".trim($_POST['TglJabatanYad']);
                $pejabat_penetap    =validTeks(trim($_POST['pejabat_penetap']));
                $nomor_sk           =validTeks(trim($_POST['nomor_sk']));
                $tgl_sk             =trim($_POST['ThnSK'])."-".trim($_POST['BlnSK'])."-".trim($_POST['TglSK']);
                $dasar_peraturan    =validTeks(trim($_POST['dasar_peraturan']));
                $masa_kerja         =validTeks(trim($_POST['masa_kerja']));
                $bln_kerja          =validTeks(trim($_POST['bln_kerja']));
                $dokumen            = str_replace(" ","_","pages/riwayatpangkat/berkas/".$_FILES['dokumen']['name']);
                if ((!empty($id))&&(!empty($jabatan))) {
                    switch($action) {
                        case "TAMBAH":
                            if((strtolower(substr($dokumen,-3))=="jpg")||(strtolower(substr($dokumen,-3))=="jpeg")){
                                move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                                Tambah(" riwayat_jabatan "," '$id','$jabatan','$tmt_pangkat','$tmt_pangkat_yad','$pejabat_penetap',
                                        '$nomor_sk','$tgl_sk','$dasar_peraturan','$masa_kerja','$bln_kerja','$dokumen'", " Riwayat Jabatan " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputRiwayatPangkat&action=TAMBAH&id=$id'>";
                            }else{
                                echo "Berkas harus JPEG/JPG";
                            }                                    
                            break;
                    }
                }else if ((empty($id))||(empty($jabatan))){
                    echo 'Semua field harus isi..!!!';
                }
            }

            $_sql = "SELECT jabatan,tmt_pangkat,tmt_pangkat_yad,pejabat_penetap,nomor_sk,tgl_sk,dasar_peraturan,masa_kerja,bln_kerja, 
                    berkas from riwayat_jabatan where id='$id' ORDER BY jabatan ASC ";
            $hasil=bukaquery($_sql);
            $jumlah=mysqli_num_rows($hasil);
            $ttllembur=0;
            $ttlhr=0;

            if(mysqli_num_rows($hasil)!=0) {
                echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head3'>
                            <td width='5%'><div align='center'>Proses</div></td>
                            <td width='15%'><div align='center'>Jabatan</div></td>
                            <td width='10%'><div align='center'>TMT Jabatan</div></td>
                            <td width='10%'><div align='center'>TMT Jabatan YAD</div></td>
                            <td width='15%'><div align='center'>Pejabat Penetap</div></td>
                            <td width='10%'><div align='center'>Nomor S.K.</div></td>
                            <td width='10%'><div align='center'>Tanggal S.K.</div></td>
                            <td width='15%'><div align='center'>Dasar Peraturan</div></td>
                            <td width='10%'><div align='center'>Masa Kerja</div></td>
                        </tr>";
                while($baris = mysqli_fetch_array($hasil)) {  
                    $gb="-";
                    if($baris["berkas"]=="pages/riwayatpangkat/berkas"){
                        $gb="-";                            
                    }else{
                        $gb="<img src='".$baris["berkas"]."' width='850px' height='950px'>";
                    }
                  echo "<tr class='isi'>
                            <td width='70'>
                                <center>"; ?>
                                <a href="?act=InputRiwayatPangkat&action=HAPUS&jabatan=<?php print $baris[0] ?>&id=<?php echo $id ?>&berkas=<?php print $baris["berkas"];?>" >[hapus]</a>
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
                            <td>$baris[7] Tahun $baris[8] Bulan</td>
                       </tr>";
                    echo "<tr class='isi'>
                            <td width='70'></td>
                            <td valign='top' align='center' colspan='10'><a target=_blank href=../penggajian/".$baris["berkas"].">".$gb."</a></td>
                       </tr>";
                }
            echo "</table>";

        } else {
            echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head3'>
                            <td width='5%'><div align='center'>Proses</div></td>
                            <td width='15%'><div align='center'>Jabatan</div></td>
                            <td width='10%'><div align='center'>TMT Jabatan</div></td>
                            <td width='10%'><div align='center'>TMT Jabatan YAD</div></td>
                            <td width='15%'><div align='center'>Pejabat Penetap</div></td>
                            <td width='10%'><div align='center'>Nomor S.K.</div></td>
                            <td width='10%'><div align='center'>Tanggal S.K.</div></td>
                            <td width='15%'><div align='center'>Dasar Peraturan</div></td>
                            <td width='10%'><div align='center'>Masa Kerja</div></td>
                        </tr>
                  </table>";

        }
    ?>     
    </form>
    <?php
        if ($action=="HAPUS") {
            unlink($_GET['berkas']);
            Hapus(" riwayat_jabatan "," id ='".$_GET['id']."' and jabatan ='".$_GET['jabatan']."'","?act=InputRiwayatPangkat&action=TAMBAH&id=$id");
        }
    ?>
</div>

