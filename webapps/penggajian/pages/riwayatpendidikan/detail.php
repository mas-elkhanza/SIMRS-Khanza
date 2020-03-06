<div id="entry">        
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            echo "";
            $action             =isset($_GET['action'])?$_GET['action']:NULL;
            $id                 =isset($_GET['id'])?$_GET['id']:NULL;
            $pendidikan         =isset($_GET['pendidikan'])?$_GET['pendidikan']:NULL;
            $sekolah            =isset($_GET['sekolah'])?$_GET['sekolah']:NULL;
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
                      <a href=?act=InputRiwayatPendidikan&action=TAMBAH&id=$prev><<--</a>
                      <a href=?act=ListRiwayatPendidikan&action=LIHAT>| List Riwayat Pendidikan |</a>
                      <a href=?act=InputRiwayatPendidikan&action=TAMBAH&id=$next>-->></a>
                      </div>";
        ?>
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" class="tbl_form">
            <tr class="isi2">
                <td width="17%" >NIP</td><td width="">:</td>
                <td width="31%"><?php echo $baris[0];?></td>
                <td width="17%" >Kepala/Rektor</td><td width="">:</td>
                <td width="31%"><input name="kepala" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" type=text id="TxtIsi5" class="inputbox" value="<?php echo isset($kepala)?$kepala:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%">Nama</td><td width="">:</td>
                <td width="31%"><?php echo $baris[1];?></td>
                <td width="17%" >Asal Pendanaan</td><td width="">:</td>
                <td width="31%">
                    <select name="pendanaan" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                        <option id='TxtIsi6' value='Biaya Sendiri'>Biaya Sendiri</option>
                        <option id='TxtIsi6' value='Biaya Instansi Sendiri'>Biaya Instansi Sendiri</option>
                        <option id='TxtIsi6' value='Lembaga Swasta Kerjasama'>Lembaga Swasta Kerjasama</option>
                        <option id='TxtIsi6' value='Lembaga Swasta Kompetisi'>Lembaga Swasta Kompetisi</option>
                        <option id='TxtIsi6' value='Lembaga Pemerintah Kerjasama'>Lembaga Pemerintah Kerjasama</option>
                        <option id='TxtIsi6' value='Lembaga Pemerintah Kompetisi'>Lembaga Pemerintah Kompetisi</option>
                        <option id='TxtIsi6' value='Lembaga Internasional'>Lembaga Internasional</option>
                    </select>
                    <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Tingkat Pendidikan</td><td width="">:</td>
                <td width="31%">
                    <select name="pendidikan" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                        <option id='TxtIsi1' value='SD'>SD</option>
                        <option id='TxtIsi1' value='SMP'>SMP</option>
                        <option id='TxtIsi1' value='SMA'>SMA</option>
                        <option id='TxtIsi1' value='SMK'>SMK</option>
                        <option id='TxtIsi1' value='D I'>D I</option>
                        <option id='TxtIsi1' value='D II'>D II</option>
                        <option id='TxtIsi1' value='D III'>D III</option>
                        <option id='TxtIsi1' value='D IV'>D IV</option>
                        <option id='TxtIsi1' value='S1'>S1</option>
                        <option id='TxtIsi1' value='S2'>S2</option>
                        <option id='TxtIsi1' value='S3'>S3</option>
                        <option id='TxtIsi1' value='Post Doctor'>Post Doctor</option>
                    </select>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Keterangan</td><td width="">:</td>
                <td width="31%"><input name="keterangan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" type=text id="TxtIsi7" class="inputbox" value="<?php echo isset($keterangan)?$keterangan:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Sekolah/Kampus</td><td width="">:</td>
                <td width="31%"><input name="sekolah" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($sekolah)?$sekolah:NULL;?>" size="40" maxlength="50">
                <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Status Pendidikan</td><td width="">:</td>
                <td width="31%"><input name="status" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" type=text id="TxtIsi8" class="inputbox" value="<?php echo isset($status)?$status:NULL;?>" size="40" maxlength="40">
                <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Jurusan</td><td width="">:</td>
                <td width="31%"><input name="jurusan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo isset($jurusan)?$jurusan:NULL;?>" size="40" maxlength="40">
                <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Berkas Ijazah</td><td width="">:</td>
                <td width="31%">
                    <input name="dokumen" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" type=file id="TxtIsi9" value="<?php echo $dokumen;?>" size="40" maxlength="255" />
                    <span id="MsgIsi9" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Tahun Lulus </td><td width="">:</td>
                <td width="31%" colspan="4">                        
                    <select name="thn_lulus" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                         <?php                               
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>            
        </table>
        <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;SIMPAN&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;KOSONG&nbsp;&nbsp;"></div><br>
        <?php
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $id                 =trim($_POST['id']);
                $pendidikan         =validTeks(trim($_POST['pendidikan']));
                $sekolah            =validTeks(trim($_POST['sekolah']));
                $jurusan            =validTeks(trim($_POST['jurusan']));
                $thn_lulus          =validTeks(trim($_POST['thn_lulus']));
                $kepala             =validTeks(trim($_POST['kepala']));
                $pendanaan          =validTeks(trim($_POST['pendanaan']));
                $keterangan         =validTeks(trim($_POST['keterangan']));
                $status             =validTeks(trim($_POST['status']));                    
                $dokumen            = str_replace(" ","_","pages/riwayatpendidikan/berkas/".$_FILES['dokumen']['name']);
                if ((!empty($id))&&(!empty($pendidikan))&&(!empty($sekolah))) {
                    switch($action) {
                        case "TAMBAH":
                            if((strtolower(substr($dokumen,-3))=="jpg")||(strtolower(substr($dokumen,-3))=="jpeg")){
                                move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                                Tambah(" riwayat_pendidikan "," '$id','$pendidikan','$sekolah','$jurusan','$thn_lulus','$kepala','$pendanaan','$keterangan','$status','$dokumen'", " Riwayat Pendidikan " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputRiwayatPendidikan&action=TAMBAH&id=$id'>";
                            }else{
                                echo "Berkas harus JPEG/JPG";
                            } 
                            break;
                    }
                }else if ((empty($id))||(empty($pendidikan))||(empty($sekolah))){
                    echo 'Semua field harus isi..!!!';
                }
            }

            $_sql = "SELECT pendidikan, sekolah, jurusan, thn_lulus, kepala, pendanaan, keterangan, status, 
                    berkas from riwayat_pendidikan where id='$id' ORDER BY thn_lulus ASC ";
            $hasil=bukaquery($_sql);
            $jumlah=mysqli_num_rows($hasil);
            $ttllembur=0;
            $ttlhr=0;

            if(mysqli_num_rows($hasil)!=0) {
                echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head3'>
                            <td width='5%'><div align='center'>Proses</div></td>
                            <td width='10%'><div align='center'>Pendidikan</div></td>
                            <td width='20%'><div align='center'>Sekolah/Kampus</div></td>
                            <td width='5%'><div align='center'>Jurusan</div></td>
                            <td width='5%'><div align='center'>Lulus</div></td>
                            <td width='15%'><div align='center'>Kepala/Rektor</div></td>
                            <td width='15%'><div align='center'>Asal Pendanaan</div></td>
                            <td width='15%'><div align='center'>Keterangan</div></td>
                            <td width='10%'><div align='center'>Status</div></td>
                        </tr>";
                while($baris = mysqli_fetch_array($hasil)) {   
                    $gb="-";
                    if($baris["berkas"]=="pages/riwayatpendidikan/berkas"){
                        $gb="-";                            
                    }else{
                        $gb="<img src='".$baris["berkas"]."' width='850px' height='950px'>";
                    }
                  echo "<tr class='isi'>
                            <td width='70'>
                                <center>"; ?>
                                <a href="?act=InputRiwayatPendidikan&action=HAPUS&pendidikan=<?php print $baris[0] ?>&sekolah=<?php print $baris[1] ?>&id=<?php echo $id ?>&berkas=<?php print $baris["berkas"];?>" >[hapus]</a>
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
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head3'>
                            <td width='5%'><div align='center'>Proses</div></td>
                            <td width='10%'><div align='center'>Pendidikan</div></td>
                            <td width='20%'><div align='center'>Sekolah/Kampus</div></td>
                            <td width='5%'><div align='center'>Jurusan</div></td>
                            <td width='5%'><div align='center'>Lulus</div></td>
                            <td width='15%'><div align='center'>Kepala/Rektor</div></td>
                            <td width='15%'><div align='center'>Asal Pendanaan</div></td>
                            <td width='15%'><div align='center'>Keterangan</div></td>
                            <td width='10%'><div align='center'>Status</div></td>
                        </tr>
                   </table>";                
        }
    ?>
    </form>
    <?php
        if ($action=="HAPUS") {
            unlink($_GET['berkas']);
            Hapus(" riwayat_pendidikan "," id ='".$_GET['id']."' and pendidikan ='".$_GET['pendidikan']."' and sekolah ='".$_GET['sekolah']."'","?act=InputRiwayatPendidikan&action=TAMBAH&id=$id");
        }        
    ?>
</div>

