

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputResikoKerja&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListResikoKerja>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action   =isset($_GET['action'])?$_GET['action']:NULL;
                $kode_resiko     =str_replace("_"," ",isset($_GET['kode_resiko'])?$_GET['kode_resiko']:NULL);
                if($action == "TAMBAH"){
                    $kode_resiko       = str_replace("_"," ",isset($_GET['kode_resiko']))?str_replace("_"," ",$_GET['kode_resiko']):NULL;
                    $nama_resiko       = "";
                    $indek             = "";
                }else if($action == "UBAH"){
                    $_sql           = "SELECT * FROM resiko_kerja WHERE kode_resiko='$kode_resiko'";
                    $hasil          = bukaquery($_sql);
                    $baris          = mysqli_fetch_row($hasil);
                    $kode_resiko    = $baris[0];
                    $nama_resiko    = $baris[1];
                    $indek          = $baris[2];
                }
                echo"<input type=hidden name=kode_resiko value=$kode_resiko><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Kode</td><td width="">:</td>
                    <td width="67%"><input name="kode_resiko" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $kode_resiko;?>" size="10" maxlength="3">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Resiko Kerja</td><td width="">:</td>
                    <td width="67%"><input name="nama_resiko" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $nama_resiko;?>" size="40" maxlength="100" />
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Index</td><td width="">:</td>
                    <td width="67%"><input name="indek" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $indek;?>" size="10" maxlength="4" />
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $kode_resiko    = validTeks(trim($_POST['kode_resiko']));
                    $nama_resiko    = validTeks(trim($_POST['nama_resiko']));
                    $indek          = validTeks(trim($_POST['indek']));
                    if ((!empty($kode_resiko))&&(!empty($nama_resiko))&&(!empty($indek))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" resiko_kerja "," '$kode_resiko','$nama_resiko','$indek' ", " kelompok jabatan " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputResikoKerja&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" resiko_kerja "," nama_resiko='$nama_resiko',indek='$indek' WHERE kode_resiko='$kode_resiko' ", " kelompok jabatan ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListResikoKerja'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($kode_resiko))||(empty($nama_resiko))||(empty($indek))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>

