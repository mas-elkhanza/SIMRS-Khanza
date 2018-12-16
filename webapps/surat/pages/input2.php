<div id="post">
    <div class="entry">        
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                $action =isset($_GET['action'])?$_GET['action']:NULL;
                $max    = getOne("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0)+1 from surat_keluar where tgl_kirim=current_date()");
                $no_urut= "SK".str_replace("-","",getOne("select current_date()")).sprintf("%03s", $max);
                echo "<input type=hidden name=action value=$action>";
            ?>
            <div style="width: 100%; height: 90%; overflow: auto;">
                <div style="width: 835px; height: 100%; overflow: auto;">
                <table width="100%" align="center">
                    <tr class="isi2">
                        <td width="15%" >Nomor Keluar</td>
                        <td width="35%">
                            :&nbsp;<input name="no_urut" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $no_urut;?>" size="20" maxlength="15">
                            <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Ruang Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_ruang" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi10'));" id="TxtIsi10">
                                <?php
                                    $_sql = "SELECT kd,ruang FROM surat_ruang ORDER BY ruang";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi10' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi10" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Nomor Surat</td>
                        <td width="35%">
                            :&nbsp;<input name="no_surat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="" size="30" maxlength="35">
                            <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Sifat Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_sifat" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi11'));" id="TxtIsi11">
                                <?php
                                    $_sql = "SELECT kd,sifat FROM surat_sifat ORDER BY sifat";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi11' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi11" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Tujuan</td>
                        <td width="35%">
                            :&nbsp;<input name="tujuan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="" size="35" maxlength="300">
                            <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                        </td>                        
                        <td width="15%" >Lampiran</td>
                        <td width="35%">
                            :&nbsp;<input name="lampiran" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi12'));" type=text id="TxtIsi12" class="inputbox" value="" size="35" maxlength="300">
                            <span id="MsgIsi12" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Tanggal Surat</td>
                        <td width="35%">
                           :&nbsp;<select name="TglSurat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                                 <?php
                                    loadTglnow();
                                 ?>
                            </select>
                            <select name="BlnSurat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                                 <?php
                                    loadBlnnow();
                                 ?>
                            </select>
                            <select name="ThnSurat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                                 <?php
                                    loadThnnow();
                                 ?>
                            </select>
                            <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                        </td>                        
                        <td width="15%" >Tembusan</td>
                        <td width="35%">
                            :&nbsp;<input name="tembusan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi13'));" type=text id="TxtIsi13" class="inputbox" value="" size="35" maxlength="300">
                            <span id="MsgIsi13" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Perihal</td>
                        <td width="35%">
                            :&nbsp;<input name="perihal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" type=text id="TxtIsi5" class="inputbox" value="" size="35" maxlength="300">
                            <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                        </td> 
                        <td width="15%" >Deadline Balas</td>
                        <td width="35%">
                           :&nbsp;<select name="TglDeadlineBalas" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi14'));" id="TxtIsi14">
                                 <?php
                                    loadTglnow();
                                 ?>
                            </select>
                            <select name="BlnDeadlineBalas" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi14'));" id="TxtIsi14">
                                 <?php
                                    loadBlnnow();
                                 ?>
                            </select>
                            <select name="ThnDeadlineBalas" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi14'));" id="TxtIsi14">
                                 <?php
                                    loadThnnow();
                                 ?>
                            </select>
                            <span id="MsgIsi14" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Tanggal Kirim</td>
                        <td width="35%">
                           :&nbsp;<select name="TglKirim" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                                 <?php
                                    loadTglnow();
                                 ?>
                            </select>
                            <select name="BlnKirim" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                                 <?php
                                    loadBlnnow();
                                 ?>
                            </select>
                            <select name="ThnKirim" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                                 <?php
                                    loadThnnow();
                                 ?>
                            </select>
                            <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Status Balas</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_balas" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi15'));" id="TxtIsi15">
                                <?php
                                    $_sql = "SELECT kd,balas FROM surat_balas ORDER BY balas";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi15' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi15" style="color:#CC0000; font-size:10px;"></span>
                        </td>                 
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Almari Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_lemari" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" id="TxtIsi7">
                                <?php
                                    $_sql = "SELECT kd,lemari FROM surat_lemari ORDER BY lemari";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi7' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Keterangan</td>
                        <td width="35%">
                            :&nbsp;<input name="keterangan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi16'));" type=text id="TxtIsi16" class="inputbox" value="" size="35" maxlength="300">
                            <span id="MsgIsi16" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Rak Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_rak" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" id="TxtIsi8">
                                <?php
                                    $_sql = "SELECT kd,rak FROM surat_rak ORDER BY rak";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi8' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Status Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_status" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi17'));" id="TxtIsi17">
                                <?php
                                    $_sql = "SELECT kd,status FROM surat_status ORDER BY status";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi17' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi17" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Map Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_map" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" id="TxtIsi9">
                                <?php
                                    $_sql = "SELECT kd,map FROM surat_map ORDER BY map";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi9' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi9" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Klasifikasi Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_klasifikasi" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi18'));" id="TxtIsi18">
                                <?php
                                    $_sql = "SELECT kd,klasifikasi FROM surat_klasifikasi ORDER BY klasifikasi";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi18' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi18" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >File Berkas(PDF/JPG)</td>
                        <td width="35%">
                            :&nbsp;<input name="dokumen" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi19'));" type=file id="TxtIsi19" value="<?php echo $dokumen;?>" size="30" maxlength="255" />
                            <span id="MsgIsi19" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                </table>                
                <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div><br>
                </div>
            </div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $no_surat   =trim($_POST['no_surat']);
                    $tujuan     =trim($_POST['tujuan']);
                    $tgl_surat  =trim($_POST['ThnSurat'])."-".trim($_POST['BlnSurat'])."-".trim($_POST['TglSurat']);
                    $perihal    =trim($_POST['perihal']);
                    $tgl_kirim =trim($_POST['ThnKirim'])."-".trim($_POST['BlnKirim'])."-".trim($_POST['TglKirim']);
                    $kd_lemari  =trim($_POST['kd_lemari']);
                    $kd_rak     =trim($_POST['kd_rak']);
                    $kd_map     =trim($_POST['kd_map']);
                    $kd_ruang   =trim($_POST['kd_ruang']);
                    $kd_sifat   =trim($_POST['kd_sifat']);
                    $lampiran   =trim($_POST['lampiran']);
                    $tembusan   =trim($_POST['tembusan']);
                    $tgl_deadline_balas=trim($_POST['ThnDeadlineBalas'])."-".trim($_POST['BlnDeadlineBalas'])."-".trim($_POST['TglDeadlineBalas']);
                    $kd_balas   =trim($_POST['kd_balas']);
                    $keterangan =trim($_POST['keterangan']);
                    $kd_status  =trim($_POST['kd_status']);
                    $kd_klasifikasi=trim($_POST['kd_klasifikasi']);
                    $max        =getOne("select ifnull(MAX(CONVERT(RIGHT(no_surat,3),signed)),0)+1 from surat_keluar where tgl_kirim='$tgl_kirim'");
                    $no_urut    ="SK".str_replace("-","",$tgl_kirim).sprintf("%03s", $max);
                    $dokumen    =str_replace(" ","_","pages/upload/".$_FILES['dokumen']['name']);
                    move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                    
                    if ((!empty($no_urut))&&(!empty($no_surat))&&(!empty($tujuan))&&(!empty($tgl_surat))&&(!empty($perihal))&&(!empty($tgl_kirim))&&(!empty($kd_lemari))&&(!empty($kd_rak))&&(!empty($kd_map))&&(!empty($kd_ruang))&&(!empty($kd_sifat))&&(!empty($lampiran))&&(!empty($tembusan))&&(!empty($tgl_deadline_balas))&&(!empty($kd_balas))&&(!empty($keterangan))&&(!empty($kd_status))&&(!empty($kd_klasifikasi))&&(!empty($dokumen))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" surat_keluar "," '$no_urut','$no_surat','$tujuan','$tgl_surat','$perihal','$tgl_kirim','$kd_lemari','$kd_rak','$kd_map','$kd_ruang','$kd_sifat','$lampiran','$tembusan','$tgl_deadline_balas','$kd_balas','$keterangan','$kd_status','$kd_klasifikasi','$dokumen'", " Surat Masuk " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=Input&action=TAMBAH'>";
                                break;
                        }
                    }else{
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
        </form>        
    </div>
</div>
