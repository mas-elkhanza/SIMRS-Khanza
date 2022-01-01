
<div id="post">
    <div class="entry">        
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             = isset($_GET['action'])?$_GET['action']:NULL;
                $no_rawat           = isset($_GET['no_rawat'])?$_GET['no_rawat']:NULL;
                $tanggal            = isset($_GET['tanggal'])?$_GET['tanggal']:NULL;
                $jam                = isset($_GET['jam'])?$_GET['jam']:NULL;
                $kd_jenis_prw       = isset($_GET['kd_jenis_prw'])?$_GET['kd_jenis_prw']:NULL;
                $no_rm              = getOne("select no_rkm_medis from reg_periksa where no_rawat='$no_rawat'");
                $nama_pasien        = getOne("select nm_pasien from pasien where no_rkm_medis='$no_rm'");
                $nm_perawatan       = getOne("select nm_perawatan from jns_perawatan_lab where kd_jenis_prw='$kd_jenis_prw'");
                echo "<input type=hidden name=no_rawat value=$no_rawat>
                      <input type=hidden name=tanggal value=$tanggal>
                      <input type=hidden name=jam  value=$jam>
                      <input type=hidden name=kd_jenis_prw  value=$kd_jenis_prw>
                      <input type=hidden name=action value=$action>";
            ?>
            <div style="width: 100%; height: 22%; overflow: auto;">
                <table width="100%" align="center">
                    <tr class="head">
                        <td width="31%" >No.Rawat</td><td width="">:</td>
                        <td width="67%"><?php echo $no_rawat;?></td>
                    </tr>
                    <tr class="head">
                        <td width="31%">Pasien</td><td width="">:</td>
                        <td width="67%"><?php echo $no_rm." ".$nama_pasien;?></td>
                    </tr>  
                    <tr class="head">
                        <td width="31%">Tanggal & Jam</td><td width="">:</td>
                        <td width="67%"><?php echo $tanggal." ".$jam;?></td>
                    </tr>
                    <tr class="head">
                        <td width="31%">Pemeriksaan</td><td width="">:</td>
                        <td width="67%"><?php echo $nm_perawatan;?></td>
                    </tr>
                    <tr class="head">
                        <td width="31%" >File/Gambar</td><td width="">:</td>
                        <td width="67%">
                            <input name="gambar" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=file id="TxtIsi1" value="<?php echo $gambar;?>" size="50" maxlength="500" />
                            <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                        </td>
                    </tr>        
                </table>
            </div>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $no_rawat     = trim($_POST['no_rawat']);
                    $tanggal      = trim($_POST['tanggal']);
                    $jam          = trim($_POST['jam']);
                    $kd_jenis_prw = trim($_POST['kd_jenis_prw']);
                    $gambar       = str_replace(" ","_","pages/upload/".$_FILES['gambar']['name']);
                    move_uploaded_file($_FILES['gambar']['tmp_name'],$gambar);
                    
                    if ((!empty($no_rawat))&&(!empty($kd_jenis_prw))&&(!empty($gambar))) {
                        Tambah(" detail_periksa_labpa_gambar "," '$no_rawat','$kd_jenis_prw','$tanggal','$jam','$gambar'", " Gambar Lab PA " );
                        echo"<meta http-equiv='refresh' content='1;URL=?act=List&no_rawat=$no_rawat&tanggal=$tanggal&jam=$jam&kd_jenis_prw=$kd_jenis_prw'>";                              
                    }else if ((empty($no_rawat))||(empty($gambar))||(empty($kd_jenis_prw))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 78%; overflow: auto;">
            <?php
                $_sql = "SELECT * from detail_periksa_labpa_gambar where no_rawat='$no_rawat' and tgl_periksa='$tanggal' and jam='$jam' and kd_jenis_prw='$kd_jenis_prw'";
                $hasil=bukaquery($_sql);

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='95%'><div align='center'>File/Gambar</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td valign='Top' align='center'>
                                    <center>"; ?>
                                    <a href="?act=List&action=HAPUS&no_rawat=<?php print $baris["no_rawat"] ?>&tanggal=<?php print $baris["tgl_periksa"] ?>&jam=<?php print $baris["jam"] ?>&gambar=<?php print $baris["photo"] ?>&kd_jenis_prw=<?php print $baris["kd_jenis_prw"] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td valign='Top' align='center'><img src='$baris[4]' width='99%' height='99%'></td>
                           </tr>";
                    }
                    echo "</table>";

                }
            ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                unlink($_GET['gambar']);
                Hapus(" detail_periksa_labpa_gambar "," no_rawat ='".$_GET['no_rawat']."' and tgl_periksa ='".$_GET['tanggal']."' and jam ='".$_GET['jam']."' and photo ='".$_GET['gambar']."' and kd_jenis_prw ='".$_GET['kd_jenis_prw']."'","?act=List&action=TAMBAH&no_rawat=$no_rawat&tanggal=$tanggal&jam=$jam&kd_jenis_prw=$kd_jenis_prw");
            }
        
        ?>
    </div>

</div>
