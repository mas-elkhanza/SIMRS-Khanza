

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputPencapaianKinerja&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListPencapaianKinerja>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action   =isset($_GET['action'])?$_GET['action']:NULL;
                $kode_pencapaian     =str_replace("_"," ",isset($_GET['kode_pencapaian'])?$_GET['kode_pencapaian']:NULL);
                if($action == "TAMBAH"){
                    $kode_pencapaian       = str_replace("_"," ",isset($_GET['kode_pencapaian']))?str_replace("_"," ",$_GET['kode_pencapaian']):NULL;
                    $nama_pencapaian        = "";
                    $indek      ="";
                }else if($action == "UBAH"){
                    $_sql           = "SELECT * FROM pencapaian_kinerja WHERE kode_pencapaian='$kode_pencapaian'";
                    $hasil          = bukaquery($_sql);
                    $baris          = mysqli_fetch_row($hasil);
                    $kode_pencapaian  = $baris[0];
                    $nama_pencapaian  = $baris[1];
                    $indek          = $baris[2];
                }
                echo"<input type=hidden name=kode_pencapaian value=$kode_pencapaian><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Kode</td><td width="">:</td>
                    <td width="67%"><input name="kode_pencapaian" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $kode_pencapaian;?>" size="10" maxlength="3">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Pencapaian Kinerja</td><td width="">:</td>
                    <td width="67%"><input name="nama_pencapaian" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $nama_pencapaian;?>" size="40" maxlength="100" />
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
                    $kode_pencapaian    = validTeks(trim($_POST['kode_pencapaian']));
                    $nama_pencapaian    = validTeks(trim($_POST['nama_pencapaian']));
                    $indek              = validTeks(trim($_POST['indek']));
                    if ((!empty($kode_pencapaian))&&(!empty($nama_pencapaian))&&(!empty($indek))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" pencapaian_kinerja "," '$kode_pencapaian','$nama_pencapaian','$indek' ", " pencapaian index " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputPencapaianKinerja&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" pencapaian_kinerja "," nama_pencapaian='$nama_pencapaian',indek='$indek' WHERE kode_pencapaian='$kode_pencapaian' ", " pencapaian index ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListPencapaianKinerja'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($kode_pencapaian))||(empty($nama_pencapaian))||(empty($indek))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>

