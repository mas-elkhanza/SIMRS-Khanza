<div id="entry">       
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
                      <a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$prev><<--</a>
                      <a href=?act=ListRiwayatSeminar&action=LIHAT>| List Riwayat Kegiatan Ilmiah & Pelatihan |</a>
                      <a href=?act=InputRiwayatSeminar&action=TAMBAH&id=$next>-->></a>
                      </div>";
        ?>
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" class="tbl_form">
            <tr class="isi2">
                <td width="17%" >NIP</td><td width="">:</td>
                <td width="31%"><?php echo $baris[0];?></td>
                <td width="17%" >Tanggal Mulai</td><td width="">:</td>
                <td width="31%">
                    <select name="TglMulai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                         <?php
                            loadTglnow();
                         ?>
                    </select>
                    <select name="BlnMulai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                         <?php
                            loadBlnnow();
                         ?>
                    </select>
                    <select name="ThnMulai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%">Nama</td><td width="">:</td>
                <td width="31%"><?php echo $baris[1];?></td>
                <td width="17%" >Sampai Dengan</td><td width="">:</td>
                <td width="31%">
                    <select name="TglSelesai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                         <?php
                            loadTglnow();
                         ?>
                    </select>
                    <select name="BlnSelesai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                         <?php
                            loadBlnnow();
                         ?>
                    </select>
                    <select name="ThnSelesai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Level</td><td width="">:</td>
                <td width="31%">
                    <select name="tingkat" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                        <option id='TxtIsi1' value='Local'>Local</option>
                        <option id='TxtIsi1' value='Regional'>Regional</option>
                        <option id='TxtIsi1' value='Nasional'>Nasional</option>
                        <option id='TxtIsi1' value='Internasional'>Internasional</option>
                    </select>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Penyelenggara</td><td width="">:</td>
                <td width="31%"><input name="penyelengara" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" type=text id="TxtIsi7" class="inputbox" value="<?php echo isset($penyelengara)?$penyelengara:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Jenis Kegiatan</td><td width="">:</td>
                <td width="31%">
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
                <td width="17%" >Tempat</td><td width="">:</td>
                <td width="31%"><input name="tempat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" type=text id="TxtIsi8" class="inputbox" value="<?php echo isset($tempat)?$tempat:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Nama Kegiatan</td><td width="">:</td>
                <td width="31%"><input name="nama_seminar" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo isset($nama_seminar)?$nama_seminar:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Sertifikat</td><td width="">:</td>
                <td width="31%">
                    <input name="dokumen" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" type=file id="TxtIsi9" value="<?php echo $dokumen;?>" size="40" maxlength="255" />
                    <span id="MsgIsi9" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>  
            <tr class="isi2">
                <td width="17%" >Peranan</td><td width="">:</td>
                <td width="31%" colspan="4"><input name="peranan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo isset($peranan)?$peranan:NULL;?>" size="40" maxlength="40">
                <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>            
        </table>
        <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;SIMPAN&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;KOSONG&nbsp;&nbsp;"></div><br>
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
                $dokumen            = str_replace(" ","_","pages/riwayatseminar/berkas/".$_FILES['dokumen']['name']);
                if ((!empty($id))&&(!empty($nama_seminar))&&(!empty($mulai))) {
                    switch($action) {
                        case "TAMBAH":
                            if((strtolower(substr($dokumen,-3))=="jpg")||(strtolower(substr($dokumen,-3))=="jpeg")){
                                move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                                Tambah(" riwayat_seminar "," '$id','$tingkat','$jenis','$nama_seminar','$peranan','$mulai','$selesai','$penyelengara','$tempat','$dokumen'", " Riwayat Kegiatan Ilmiah " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputRiwayatSeminar&action=TAMBAH&id=$id'>";
                            }else{
                                echo "Berkas harus JPEG/JPG";
                            } 
                            break;
                    }
                }else if ((empty($id))||(empty($nama_seminar))){
                    echo 'Semua field harus isi..!!!';
                }
            }
            
            $_sql = "SELECT tingkat, jenis, nama_seminar, peranan, mulai, selesai, penyelengara, tempat,
                    berkas from riwayat_seminar where id='$id' ORDER BY mulai ASC ";
            $hasil=bukaquery($_sql);
            $jumlah=mysqli_num_rows($hasil);
            $ttllembur=0;
            $ttlhr=0;

            if(mysqli_num_rows($hasil)!=0) {
                echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head3'>
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
                while($baris = mysqli_fetch_array($hasil)) {   
                    $gb="-";
                    if($baris["berkas"]=="pages/riwayatseminar/berkas"){
                        $gb="-";                            
                    }else{
                        $gb="<img src='".$baris["berkas"]."' width='850px' height='950px'>";
                    }
                  echo "<tr class='isi'>
                            <td width='70'>
                                <center>"; ?>
                                <a href="?act=InputRiwayatSeminar&action=HAPUS&nama_seminar=<?php print $baris[2] ?>&mulai=<?php print $baris[4] ?>&id=<?php echo $id ?>&berkas=<?php print $baris["berkas"];?>" >[hapus]</a>
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
                            <td width='10%'><div align='center'>Level</div></td>
                            <td width='10%'><div align='center'>Jenis Kegiatan</div></td>
                            <td width='25%'><div align='center'>Nama Kegiatan</div></td>
                            <td width='10%'><div align='center'>Peranan</div></td>
                            <td width='5%'><div align='center'>Mulai</div></td>
                            <td width='5%'><div align='center'>Selesai</div></td>
                            <td width='15%'><div align='center'>Penyelanggara</div></td>
                            <td width='15%'><div align='center'>Tempat</div></td>
                        </tr>
                   </table>";            
        }
    ?>
    </form>
    <?php
        if ($action=="HAPUS") {
            unlink($_GET['berkas']);
            Hapus(" riwayat_seminar "," id ='".$_GET['id']."' and mulai ='".$_GET['mulai']."' and nama_seminar ='".$_GET['nama_seminar']."'","?act=InputRiwayatSeminar&action=TAMBAH&id=$id");
        }
    ?>
</div>
