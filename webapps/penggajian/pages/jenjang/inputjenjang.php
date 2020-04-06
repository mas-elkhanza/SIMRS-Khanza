

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputJenjang&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListJenjang>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $kode        =str_replace("_"," ",isset($_GET['kode']))?str_replace("_"," ",$_GET['kode']):NULL;
                if($action == "TAMBAH"){
                    $kode      = str_replace("_"," ",isset($_GET['kode']))?str_replace("_"," ",$_GET['kode']):NULL;
                    $nama      = "";
                    $tnj       = "";
                    $indek     = "";
                }else if($action == "UBAH"){
                    $_sql         = "SELECT * FROM jnj_jabatan WHERE kode='$kode'";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $kode         = $baris[0];
                    $nama         = $baris[1];
                    $tnj          = $baris[2];
                    $indek        = $baris[3];
                }
                echo"<input type=hidden name=kode value=$kode><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Kode Jenjang</td><td width="">:</td>
                    <td width="67%"><input name="kode" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $kode;?>" size="10" maxlength="5">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Nama Jenjang</td><td width="">:</td>
                    <td width="67%"><input name="nama" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $nama;?>" size="40" maxlength="30" />
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Tnj.Jabatan</td><td width="">:</td>
                    <td width="67%">Rp.<input name="tnj" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $tnj;?>" size="20" maxlength="15" />
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Index</td><td width="">:</td>
                    <td width="67%"><input name="indek" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo $indek;?>" size="10" maxlength="3" />
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $kode    = validTeks(trim($_POST['kode']));
                    $nama    = validTeks(trim($_POST['nama']));
                    $tnj     = validTeks(trim($_POST['tnj']));
                    $indek   = validTeks(trim($_POST['indek']));
                    if ((!empty($kode))&&(!empty($nama))&&(!empty($tnj))&&(!empty($indek))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" jnj_jabatan "," '$kode','$nama','$tnj','$indek' ", " Tunjangan Jabatan " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputJenjang&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" jnj_jabatan "," nama='$nama',tnj='$tnj',indek='$indek' WHERE kode='$kode' ", " Tunjangan Jabatan ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListJenjang'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($kode))||(empty($nama))||(empty($tnj))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>
