<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        if(!strpos($_SERVER['REQUEST_URI'],"pages/upload/")){
            exit(header("Location:../index.php"));
        }
    }
?>
<div id="post">
    <div class="entry">        
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                $action =isset($_GET['action'])?$_GET['action']:NULL;
                $max    = getOne("select ifnull(MAX(CONVERT(RIGHT(surat_masuk.no_urut,3),signed)),0)+1 from surat_masuk where surat_masuk.tgl_terima=current_date()");
                $no_urut= "SM".str_replace("-","",getOne("select current_date()")).sprintf("%03s", $max);
                echo "<input type=hidden name=action value=$action>";
            ?>
            <div style="width: 100%; height: 90%; overflow: auto;">
                <div style="width: 835px; height: 100%; overflow: auto;">
                <table width="100%" align="center">
                    <tr class="isi2">
                        <td width="15%" >Nomor Masuk</td>
                        <td width="35%">
                            :&nbsp;<input name="no_urut" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $no_urut;?>" size="20" maxlength="15" pattern="[A-Z0-9-]{1,15}" title=" A-Z0-9- (Maksimal 15 karakter)" autocomplete="off" required>
                            <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Ruang Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_ruang" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi11'));" id="TxtIsi11">
                                <?php
                                    $_sql = "SELECT surat_ruang.kd,surat_ruang.ruang FROM surat_ruang ORDER BY surat_ruang.ruang";
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
                        <td width="15%" >Nomor Surat</td>
                        <td width="35%">
                            :&nbsp;<input name="no_surat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="" size="30" maxlength="35" pattern="[a-zA-Z0-9, ./@_]{1,35}" title=" a-zA-Z0-9, ./@_ (Maksimal 35 karakter)" autocomplete="off" required>
                            <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Sifat Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_sifat" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi12'));" id="TxtIsi12">
                                <?php
                                    $_sql = "SELECT surat_sifat.kd,surat_sifat.sifat FROM surat_sifat ORDER BY surat_sifat.sifat";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi12' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi12" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Asal</td>
                        <td width="35%">
                            :&nbsp;<input name="asal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="" size="35" maxlength="300" pattern="[a-zA-Z 0-9-]{1,300}" title=" a-zA-Z 0-9- (Maksimal 300 karakter)" autocomplete="off" required>
                            <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Lampiran</td>
                        <td width="35%">
                            :&nbsp;<input name="lampiran" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi13'));" type=text id="TxtIsi13" class="inputbox" value="" size="35" maxlength="300" pattern="[a-zA-Z 0-9-]{1,300}" title=" a-zA-Z 0-9- (Maksimal 300 karakter)" autocomplete="off" required>
                            <span id="MsgIsi13" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Tujuan</td>
                        <td width="35%">
                            :&nbsp;<input name="tujuan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="" size="35" maxlength="300" pattern="[a-zA-Z 0-9-]{1,300}" title=" a-zA-Z 0-9- (Maksimal 300 karakter)" autocomplete="off" required>
                            <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Tembusan</td>
                        <td width="35%">
                            :&nbsp;<input name="tembusan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi14'));" type=text id="TxtIsi14" class="inputbox" value="" size="35" maxlength="300" pattern="[a-zA-Z 0-9-]{1,300}" title=" a-zA-Z 0-9- (Maksimal 300 karakter)" autocomplete="off" required>
                            <span id="MsgIsi14" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Tanggal Surat</td>
                        <td width="35%">
                           :&nbsp;<select name="TglSurat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                                 <?php
                                    loadTglnow();
                                 ?>
                            </select>
                            <select name="BlnSurat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                                 <?php
                                    loadBlnnow();
                                 ?>
                            </select>
                            <select name="ThnSurat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                                 <?php
                                    loadThnnow();
                                 ?>
                            </select>
                            <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Deadline Balas</td>
                        <td width="35%">
                           :&nbsp;<select name="TglDeadlineBalas" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi15'));" id="TxtIsi15">
                                 <?php
                                    loadTglnow();
                                 ?>
                            </select>
                            <select name="BlnDeadlineBalas" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi15'));" id="TxtIsi15">
                                 <?php
                                    loadBlnnow();
                                 ?>
                            </select>
                            <select name="ThnDeadlineBalas" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi15'));" id="TxtIsi15">
                                 <?php
                                    loadThnnow();
                                 ?>
                            </select>
                            <span id="MsgIsi15" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Perihal</td>
                        <td width="35%">
                            :&nbsp;<input name="perihal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" type=text id="TxtIsi6" class="inputbox" value="" size="35" maxlength="300" pattern="[a-zA-Z 0-9-]{1,300}" title=" a-zA-Z 0-9- (Maksimal 300 karakter)" autocomplete="off" required>
                            <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                        </td>                        
                        <td width="15%" >Status Balas</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_balas" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi16'));" id="TxtIsi16">
                                <?php
                                    $_sql = "SELECT surat_balas.kd,surat_balas.balas FROM surat_balas ORDER BY surat_balas.balas";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi16' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi16" style="color:#CC0000; font-size:10px;"></span>
                        </td>                 
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Tanggal Terima</td>
                        <td width="35%">
                           :&nbsp;<select name="TglTerima" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" id="TxtIsi7">
                                 <?php
                                    loadTglnow();
                                 ?>
                            </select>
                            <select name="BlnTerima" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" id="TxtIsi7">
                                 <?php
                                    loadBlnnow();
                                 ?>
                            </select>
                            <select name="ThnTerima" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" id="TxtIsi7">
                                 <?php
                                    loadThnnow();
                                 ?>
                            </select>
                            <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >Keterangan</td>
                        <td width="35%">
                            :&nbsp;<input name="keterangan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi17'));" type=text id="TxtIsi17" class="inputbox" value="" size="35" maxlength="300" pattern="[a-zA-Z 0-9-]{1,300}" title=" a-zA-Z 0-9- (Maksimal 300 karakter)" autocomplete="off" required>
                            <span id="MsgIsi17" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Almari Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_lemari" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" id="TxtIsi8">
                                <?php
                                    $_sql = "SELECT surat_lemari.kd,surat_lemari.lemari FROM surat_lemari ORDER BY surat_lemari.lemari";
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
                            :&nbsp;<select name="kd_status" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi18'));" id="TxtIsi18">
                                <?php
                                    $_sql = "SELECT surat_status.kd,surat_status.status FROM surat_status ORDER BY surat_status.status";
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
                        <td width="15%" >Rak Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_rak" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" id="TxtIsi9">
                                <?php
                                    $_sql = "SELECT surat_rak.kd,surat_rak.rak FROM surat_rak ORDER BY surat_rak.rak";
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
                            :&nbsp;<select name="kd_klasifikasi" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi19'));" id="TxtIsi19">
                                <?php
                                    $_sql = "SELECT surat_klasifikasi.kd,surat_klasifikasi.klasifikasi FROM surat_klasifikasi ORDER BY surat_klasifikasi.klasifikasi";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi19' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi19" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                    <tr class="isi2">
                        <td width="15%" >Map Surat</td>
                        <td width="35%">
                            :&nbsp;<select name="kd_map" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi10'));" id="TxtIsi10">
                                <?php
                                    $_sql = "SELECT surat_map.kd,surat_map.map FROM surat_map ORDER BY surat_map.map";
                                    $hasil=bukaquery($_sql);
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option id='TxtIsi10' value='$baris[0]'>$baris[1]</option>";
                                    }
                                ?>
                            </select>
                            <span id="MsgIsi10" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                        <td width="15%" >File Berkas(PDF/JPG)</td>
                        <td width="35%">
                            :&nbsp;<input name="dokumen" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi20'));" type=file id="TxtIsi20" value="<?php echo $dokumen;?>" size="30" maxlength="255" accept="application/pdf,image/jpeg,image/jpg"/>
                            <span id="MsgIsi20" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>
                </table>                
                <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div><br>
                </div>
            </div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $no_surat   = validTeks(trim($_POST['no_surat']));
                    $asal       = validTeks(trim($_POST['asal']));
                    $tujuan     = validTeks(trim($_POST['tujuan']));
                    $tgl_surat  = validTeks(trim($_POST['ThnSurat'])."-".trim($_POST['BlnSurat'])."-".trim($_POST['TglSurat']));
                    $perihal    = validTeks(trim($_POST['perihal']));
                    $tgl_terima = validTeks(trim($_POST['ThnTerima'])."-".trim($_POST['BlnTerima'])."-".trim($_POST['TglTerima']));
                    $kd_lemari  = validTeks(trim($_POST['kd_lemari']));
                    $kd_rak     = validTeks(trim($_POST['kd_rak']));
                    $kd_map     = validTeks(trim($_POST['kd_map']));
                    $kd_ruang   = validTeks(trim($_POST['kd_ruang']));
                    $kd_sifat   = validTeks(trim($_POST['kd_sifat']));
                    $lampiran   = validTeks(trim($_POST['lampiran']));
                    $tembusan   = validTeks(trim($_POST['tembusan']));
                    $tgl_deadline_balas= validTeks(trim($_POST['ThnDeadlineBalas'])."-".trim($_POST['BlnDeadlineBalas'])."-".trim($_POST['TglDeadlineBalas']));
                    $kd_balas   = validTeks(trim($_POST['kd_balas']));
                    $keterangan = validTeks(trim($_POST['keterangan']));
                    $kd_status  = validTeks(trim($_POST['kd_status']));
                    $kd_klasifikasi= validTeks(trim($_POST['kd_klasifikasi']));
                    $max        = getOne("select ifnull(MAX(CONVERT(RIGHT(no_urut,3),signed)),0)+1 from surat_masuk where tgl_terima='$tgl_terima'");
                    $no_urut    = "SM".str_replace("-","",$tgl_terima).sprintf("%03s", $max);
                    $dokumen    = validTeks(str_replace(" ","_","pages/upload/".$_FILES['dokumen']['name']));
                    if((strtolower(substr($dokumen,-3))=="jpg")||(strtolower(substr($dokumen,-3))=="pdf")||(strtolower(substr($dokumen,-4))=="jpeg")){
                        move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                        if ((!empty($no_urut))&&(!empty($no_surat))&&(!empty($asal))&&(!empty($tujuan))&&(!empty($tgl_surat))&&(!empty($perihal))&&(!empty($tgl_terima))&&(!empty($kd_lemari))&&(!empty($kd_rak))&&(!empty($kd_map))&&(!empty($kd_ruang))&&(!empty($kd_sifat))&&(!empty($lampiran))&&(!empty($tembusan))&&(!empty($tgl_deadline_balas))&&(!empty($kd_balas))&&(!empty($keterangan))&&(!empty($kd_status))&&(!empty($kd_klasifikasi))&&(!empty($dokumen))) {
                            switch($action) {
                                case "TAMBAH":
                                    Tambah(" surat_masuk "," '$no_urut','$no_surat','$asal','$tujuan','$tgl_surat','$perihal','$tgl_terima','$kd_lemari','$kd_rak','$kd_map','$kd_ruang','$kd_sifat','$lampiran','$tembusan','$tgl_deadline_balas','$kd_balas','$keterangan','$kd_status','$kd_klasifikasi','$dokumen'", " Surat Masuk " );
                                    echo"<meta http-equiv='refresh' content='1;URL=?act=Input&action=TAMBAH'>";
                                    break;
                            }
                        }else{
                            echo 'Semua field harus isi..!!!';
                        }
                    }else{
                        echo "Berkas harus JPEG/JPG";
                    }
                }
            ?>
        </form>        
    </div>
</div>
