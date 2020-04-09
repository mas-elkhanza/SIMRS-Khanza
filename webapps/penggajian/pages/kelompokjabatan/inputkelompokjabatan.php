

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputKelompokJabatan&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListKelompokJabatan>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action   =isset($_GET['action'])?$_GET['action']:NULL;
                $kode_kelompok     =str_replace("_"," ",isset($_GET['kode_kelompok'])?$_GET['kode_kelompok']:NULL);
                if($action == "TAMBAH"){
                    $kode_kelompok       = str_replace("_"," ",isset($_GET['kode_kelompok']))?str_replace("_"," ",$_GET['kode_kelompok']):NULL;
                    $nama_kelompok        = "";
                    $indek      ="";
                }else if($action == "UBAH"){
                    $_sql           = "SELECT * FROM kelompok_jabatan WHERE kode_kelompok='$kode_kelompok'";
                    $hasil          = bukaquery($_sql);
                    $baris          = mysqli_fetch_row($hasil);
                    $kode_kelompok  = $baris[0];
                    $nama_kelompok  = $baris[1];
                    $indek          = $baris[2];
                }
                echo"<input type=hidden name=kode_kelompok value=$kode_kelompok><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Kode</td><td width="">:</td>
                    <td width="67%"><input name="kode_kelompok" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $kode_kelompok;?>" size="10" maxlength="3">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Kelompok Jabatan</td><td width="">:</td>
                    <td width="67%"><input name="nama_kelompok" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $nama_kelompok;?>" size="40" maxlength="100" />
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
                    $kode_kelompok    = validTeks(trim($_POST['kode_kelompok']));
                    $nama_kelompok    = validTeks(trim($_POST['nama_kelompok']));
                    $indek            = validTeks(trim($_POST['indek']));
                    if ((!empty($kode_kelompok))&&(!empty($nama_kelompok))&&(!empty($indek))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" kelompok_jabatan "," '$kode_kelompok','$nama_kelompok','$indek' ", " kelompok jabatan " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputKelompokJabatan&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" kelompok_jabatan "," nama_kelompok='$nama_kelompok',indek='$indek' WHERE kode_kelompok='$kode_kelompok' ", " kelompok jabatan ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListKelompokJabatan'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($kode_kelompok))||(empty($nama_kelompok))||(empty($indek))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>

